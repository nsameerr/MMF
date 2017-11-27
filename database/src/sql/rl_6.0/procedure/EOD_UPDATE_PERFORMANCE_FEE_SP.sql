DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_update_performance_fee_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_update_performance_fee_sp`(
	IN p_RelationId INTEGER,
	IN p_CurrentDatetime DATETIME
    )
BEGIN
	DECLARE v_StartDate DATETIME;
	DECLARE v_CustomerPortfolioId INTEGER;
	DECLARE v_PortfolioReturns DECIMAL(12,2);
	DECLARE v_CustomerPortfolioStartValue DECIMAL(12,2);
	DECLARE v_CustomerPortfolioCloseValue DECIMAL(12,2);
	DECLARE v_AmountToCalculateFee DECIMAL(12,2);
	DECLARE v_PerfFeeThreshold DECIMAL(12,2);
	DECLARE v_PerformanceFeeRate DECIMAL(12,2);
	DECLARE v_PerfAboveThreshold DECIMAL(12,2);
	DECLARE v_PerformanceFee DECIMAL(12,2);
	DECLARE v_FundChange DECIMAL(12,2);
	
	/* Select start date, rate of fee and threshold rate for calculate performance. */
	SELECT IFNULL(ca.perf_fee_last_calculated, ca.contract_start), ca.perf_fee_percent, ca.perf_fee_threshold
		INTO v_StartDate, v_PerformanceFeeRate, v_PerfFeeThreshold
		FROM customer_advisor_mapping_tb ca WHERE ca.relation_id = p_RelationId;
	/* Select customer_portfolio_id */
	SELECT b.customer_portfolio_id INTO v_CustomerPortfolioId FROM customer_portfolio_tb b WHERE b.relation_id = p_RelationId;
	/* Calculate customer portfolio returns (out of 1) and set as value of v_PortfolioReturns */
	CALL eod_client_portfolio_performance_calculation_sp(v_CustomerPortfolioId, v_StartDate, p_CurrentDatetime, v_PortfolioReturns);
	/* v_PerfAboveThreshold is % of customer portfolio value to calculate performance fee. Set its value to v_PerformanceFee. */
	SET v_PerfAboveThreshold = (v_PortfolioReturns * 100.0) - v_PerfFeeThreshold;
	IF v_PerfAboveThreshold > 0.0 THEN
		/* Select portfolio value on start and portfolio value on close. */
		SELECT a.opening_portfolio_value INTO v_CustomerPortfolioStartValue FROM customer_portfolio_log_tb a
			WHERE a.customer_portfolio_id = v_CustomerPortfolioId AND DATE(a.log_date) = DATE(v_StartDate);
		SELECT a.closing_portfolio_value INTO v_CustomerPortfolioCloseValue FROM customer_portfolio_log_tb a
			WHERE a.customer_portfolio_id = v_CustomerPortfolioId AND DATE(a.log_date) = DATE(p_CurrentDatetime);
		/* Get total amounts added/redeemed to/from portfolio. Add it with v_CustomerPortfolioStartValue results total input from customer. */
		SELECT SUM(IF(a.addfund, a.for_amount, -a.for_amount)) INTO v_FundChange FROM add_redeem_log_tb a
			WHERE a.customer_portfolio_id = v_CustomerPortfolioId AND a.date_of_entry BETWEEN v_StartDate AND p_CurrentDatetime;
		SET v_CustomerPortfolioStartValue = v_CustomerPortfolioStartValue + v_FundChange;
		SET v_AmountToCalculateFee = (v_CustomerPortfolioCloseValue - v_CustomerPortfolioStartValue) * v_PerfAboveThreshold / 100.0;
		SET v_PerformanceFee = v_AmountToCalculateFee * ( v_PerformanceFeeRate / 100.0);
	ELSE
		SET v_PerformanceFee = 0.0;
	END IF;
	/* Update performance fee and date in customer_advisor_mapping_tb */
	UPDATE customer_advisor_mapping_tb a SET a.perf_fee_last_calculated = p_CurrentDatetime, a.perf_fee_amt = v_PerformanceFee
		WHERE a.relation_id = p_RelationId;
		
	/* Print result data */
	SELECT v_PerformanceFee performanceFee, p_RelationId relatioId,
			b.advisor_id advisorId, b.first_name advisorFirstName, b.email advisorEmail,
			c.customer_id customerId, c.first_name customerFirstName, c.email customerEmail
		FROM customer_advisor_mapping_tb a, master_advisor_tb b, master_customer_tb c
		WHERE a.relation_id = p_RelationId AND b.advisor_id = a.advisor_id 
                AND c.customer_id = a.customer_id AND b.`is_active_user` = TRUE AND c.`is_active_user` = TRUE;
END$$

DELIMITER ;