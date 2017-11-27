DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_rate_advisor_check_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_rate_advisor_check_sp`(
	IN p_RelationId INTEGER,
	IN p_CurrentDate DATETIME
    )
BEGIN
	DECLARE v_PortfolioId INTEGER;
	DECLARE v_PortfolioReturns DECIMAL(12,2);
	DECLARE v_BenchmarkReturns DECIMAL(12,2);
	DECLARE v_PortfolioStartValue DECIMAL(12,2);
	DECLARE v_BenchmarkStartValue DECIMAL(12,2);
	DECLARE v_PortfolioValue DECIMAL(12,2);
	DECLARE v_BenchmarkValue DECIMAL(12,2);
	DECLARE v_Benchmark INTEGER;
	
	UPDATE customer_advisor_mapping_tb a 
	SET a.`rate_advisor_last_date` = p_CurrentDate, a.`rate_advisor_flag` = TRUE, a.rateAdvisor_viewed = FALSE
	WHERE a.`relation_id` = p_RelationId;
	
	SELECT a.`portfolio_id`, a.`current_value`, a.`benchmark_start_value`,a.`portfolio_value`
		INTO v_PortfolioId,v_PortfolioValue, v_BenchmarkStartValue,v_PortfolioStartValue
		FROM customer_portfolio_tb a WHERE a.`relation_id` = p_RelationId;
	
	SELECT  a.`benchmark` INTO  v_Benchmark
	FROM portfolio_tb a WHERE a.`portfolio_id` = v_PortfolioId;
	
	SET v_BenchmarkValue = IFNULL((SELECT a.close  FROM `benchmark_performance_tb` a WHERE a.`benchmark` = v_Benchmark  
									AND DATE_FORMAT(a.`datetime`,'%Y-%m-%d')= DATE_FORMAT(p_CurrentDate,'%Y-%m-%d')),
			           (SELECT a.close  FROM `benchmark_performance_tb` a WHERE a.`benchmark` = v_Benchmark 
			            AND DATE_FORMAT(a.`datetime`,'%Y-%m-%d')= DATE_FORMAT((SELECT MAX(`datetime`) FROM `benchmark_performance_tb`),'%Y-%m-%d')));
	
	SET v_PortfolioReturns = ((v_PortfolioValue/v_PortfolioStartValue)-1)*100;
	SET v_BenchmarkReturns = ((v_BenchmarkValue/v_BenchmarkStartValue)-1)*100;
	
	SELECT v_PortfolioValue,v_PortfolioStartValue,v_BenchmarkValue,v_BenchmarkStartValue,v_PortfolioReturns,v_BenchmarkReturns;
	UPDATE customer_portfolio_tb a
	SET a.`advisor_portfolio_returns_from_start` = v_PortfolioReturns, a.`benchmark_returns_from_start` = v_BenchmarkReturns,
	a.`portfolio_value`= v_PortfolioValue
	WHERE a.`relation_id` = p_RelationId;
END$$

DELIMITER ;