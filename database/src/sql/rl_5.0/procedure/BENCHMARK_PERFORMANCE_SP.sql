DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `benchmark_performance_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `benchmark_performance_sp`(		
		IN p_StartDate 		DATETIME, 
		IN p_CloseDate 		DATETIME,
		IN p_Benchmark		INTEGER,
		OUT p_Return		DECIMAL(12,2)
	)
BEGIN
		DECLARE closeValue DECIMAL(12,2);
		DECLARE startValue DECIMAL(12,2);
		DECLARE performanceValue DECIMAL(12,2);
		
		SELECT a.close INTO closeValue FROM benchmark_performance_tb a 
			WHERE a.datetime BETWEEN p_StartDate AND p_CloseDate AND a.datetime = (
				SELECT MAX(b.datetime) FROM benchmark_performance_tb b
					WHERE b.datetime BETWEEN p_StartDate AND p_CloseDate AND b.benchmark = p_Benchmark LIMIT 1) LIMIT 1;
		SELECT a.close INTO startValue FROM benchmark_performance_tb a 
			WHERE a.datetime BETWEEN p_StartDate AND p_CloseDate AND a.datetime = (
				SELECT MIN(b.datetime) FROM benchmark_performance_tb b
					WHERE b.datetime BETWEEN p_StartDate AND p_CloseDate AND b.benchmark = p_Benchmark LIMIT 1) LIMIT 1;
		SET performanceValue = ((closeValue/startValue) - 1) * 100;
		SET p_Return = performanceValue;
	END$$

DELIMITER ;