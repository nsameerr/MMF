DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_update_advisor_details_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_update_advisor_details_sp`(
	IN p_AdvisorId INTEGER,
	IN p_CurrentDate DATETIME
    )
BEGIN
	DECLARE v_CurrentPeriodStart DATETIME;
	DECLARE v_Benchmark INTEGER;
	DECLARE v_DoneCalculation INT DEFAULT FALSE;
	DECLARE v_OutPerformanceCountCompleted INTEGER;
	DECLARE v_OutPerformanceCountInProgress INTEGER;
	DECLARE v_OutPerformanceCount INTEGER;
	DECLARE v_OutPerformance DECIMAL(12,2);
	DECLARE v_TotalPortfoliosManaged INTEGER;
	DECLARE v_PortfolioId INTEGER;
	DECLARE v_PortfolioReturn DECIMAL(12,2);
	DECLARE v_BenchmarkReturn DECIMAL(12,2);
	DECLARE v_DominantStyle INTEGER;
	DECLARE v_CustomerRating INTEGER;
	DECLARE v_Max90DayReturns DECIMAL(12,2);
	DECLARE v_Max1YearReturns DECIMAL(12,2);

	DECLARE v_AdvisorPortfolios CURSOR FOR

        SELECT IFNULL(a.start_current_period, a.date_created), a.benchmark, a.portfolio_id
        FROM portfolio_tb a WHERE a.status = TRUE AND a.advisor_id = p_AdvisorId;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_DoneCalculation = TRUE;
	
	UPDATE advisor_details_tb c SET c.total_portfolios_managed = (SELECT COUNT(*) FROM customer_portfolio_tb a 
		WHERE a.status=TRUE AND a.advisor_id = p_AdvisorId)
		WHERE c.advisor_id = p_AdvisorId;
		
	SELECT c.outperformance,c.outperformance_count_completed,c.outperformance_count_inprogress, c.outperformance_count, c.total_portfolios_managed
	INTO v_OutPerformance,v_OutPerformanceCountCompleted,v_OutPerformanceCountInProgress, v_OutPerformanceCount, v_TotalPortfoliosManaged
	FROM portfolio_tb b, advisor_details_tb c
	WHERE b.advisor_id = c.advisor_id AND b.status = TRUE AND c.advisor_id = p_AdvisorId;
	
	OPEN v_AdvisorPortfolios;
	
	readAdvisorPortfolios: LOOP
	
		FETCH v_AdvisorPortfolios INTO v_CurrentPeriodStart, v_Benchmark, v_PortfolioId;
		
		IF v_DoneCalculation THEN
			LEAVE readAdvisorPortfolios;
		END IF;
		
		/* Calculate portfolio returns */
		CALL recmd_portfolio_performance_calculate_sp(v_PortfolioId, v_CurrentPeriodStart, p_CurrentDate, v_PortfolioReturn);
		/* Calculate benchmark returns */
		CALL benchmark_performance_sp(v_CurrentPeriodStart, p_CurrentDate, v_Benchmark, v_BenchmarkReturn);
		
		IF(v_PortfolioReturn > v_BenchmarkReturn) THEN
			IF DATE(DATE_ADD(v_CurrentPeriodStart, INTERVAL 1 YEAR)) = DATE(p_CurrentDate) THEN
				SET v_OutPerformanceCountCompleted = v_OutPerformanceCountCompleted + 1;
				UPDATE advisor_details_tb a SET a.outperformance_count_completed = v_OutPerformanceCountCompleted WHERE a.advisor_id = p_AdvisorId;
				UPDATE portfolio_tb a SET a.start_current_period = p_CurrentDate WHERE a.portfolio_id = v_PortfolioId;
			ELSEIF DATE(DATE_ADD(v_CurrentPeriodStart, INTERVAL 1 YEAR)) > DATE(p_CurrentDate) THEN
				SET v_OutPerformanceCountInProgress = v_OutPerformanceCountInProgress + 1; -- decrement on above?
				UPDATE advisor_details_tb a SET a.outperformance_count_inprogress = v_OutPerformanceCountInProgress WHERE a.advisor_id = p_AdvisorId;
			END IF;
		END IF;
	END LOOP;
	
	CLOSE v_AdvisorPortfolios;
	
	SET v_OutPerformanceCount = v_OutPerformanceCountCompleted + v_OutPerformanceCountInProgress;
	SET v_OutPerformance = v_OutPerformanceCount / (v_TotalPortfoliosManaged + v_OutPerformanceCountCompleted);
	
	/* Updating outperformance and outperformance count */
	UPDATE advisor_details_tb a SET a.outperformance_count = v_OutPerformanceCount, a.outperformance = v_OutPerformance
	WHERE a.advisor_id = p_AdvisorId;
		
	/* Advisor dominant style : no of out performing portfolios of a style/(no of total portfolios managed of that style) */
	DROP TEMPORARY TABLE IF EXISTS temp_outperform_portfolios_in_style;
	
	CREATE TEMPORARY TABLE temp_outperform_portfolios_in_style
		SELECT COUNT(*) portfolios_outperformed, a.`portfolio_type` FROM portfolio_tb a, customer_portfolio_tb b
			WHERE a.`portfolio_id` = b.portfolio_id AND b.outperformance = 1 AND  b.advisor_id = p_AdvisorId
			GROUP BY a.`portfolio_type`;
			
	DROP TEMPORARY TABLE IF EXISTS temp_portfolios_in_style;
	
	CREATE TEMPORARY TABLE temp_portfolios_in_style
		SELECT COUNT(*) portfolios, a.`portfolio_type` FROM portfolio_tb a, customer_portfolio_tb b
			WHERE a.`portfolio_id` = b.portfolio_id AND  b.advisor_id = p_AdvisorId
			GROUP BY a.`portfolio_type`;
			
	DROP TEMPORARY TABLE IF EXISTS temp_outperform_rate;
	
	CREATE TEMPORARY TABLE temp_outperform_rate
		SELECT a.portfolio_type,(a.portfolios_outperformed/b.portfolios) outperformance_rate
			FROM temp_outperform_portfolios_in_style a, temp_portfolios_in_style b
			WHERE a.portfolio_type = b.portfolio_type;
			
	SELECT d.portfolio_type INTO v_DominantStyle FROM(
		SELECT c.*, @curMax := IF(@curMax = -1, c.outperformance_rate, @curMax)
			FROM temp_outperform_rate c, (SELECT @curMax := -1) r
			HAVING @curMax = c.outperformance_rate OR @curMax = -1
			ORDER BY c.outperformance_rate DESC LIMIT 1) d;
		
	/* Customer rating */
	SELECT SUM(b.rating_overall) FROM customer_advisor_mapping_tb b WHERE b.advisor_id = p_AdvisorId;
	
	/* Updating dominant style and customer rating */
	UPDATE advisor_details_tb a
	SET a.customer_rating = (SELECT SUM(b.rating_overall)
	FROM customer_advisor_mapping_tb b WHERE b.advisor_id = p_AdvisorId),
	a.`avg_client_rating` = (SELECT AVG(b.rating_overall)
	FROM customer_advisor_mapping_tb b WHERE b.advisor_id = p_AdvisorId),
	a.`dominant_style` = v_DominantStyle
	WHERE a.advisor_id = p_AdvisorId;
		
	
    END$$

DELIMITER ;