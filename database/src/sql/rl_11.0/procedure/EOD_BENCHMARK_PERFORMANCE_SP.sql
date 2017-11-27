DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_benchmark_performance_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_benchmark_performance_sp`()
BEGIN
		DECLARE startValue90Days DECIMAL(12,2);
		DECLARE returns90Days DECIMAL(12,2);
		DECLARE startValue1Year DECIMAL(12,2);
		DECLARE returns1Year DECIMAL(12,2);
		DECLARE closeValue DECIMAL(12,2);
		DECLARE eodDate DATE;
		DECLARE startValue5Days DECIMAL(12,2);
		DECLARE returns5Days DECIMAL(12,2);
		DECLARE startValue6Month DECIMAL(12,2);
		DECLARE returns6Month DECIMAL(12,2);
		DECLARE startValue1Month DECIMAL(12,2);
		DECLARE returns1Month DECIMAL(12,2);
		DECLARE startValue5Years DECIMAL(12,2);
		DECLARE returns5Years DECIMAL(12,2);
		DECLARE startValue10Years DECIMAL(12,2);
		DECLARE returns10Years DECIMAL(12,2);
		DECLARE startValueYtd DECIMAL(12,2);
		DECLARE returnsYtd DECIMAL(12,2);
		
		DECLARE done INT DEFAULT FALSE;
		DECLARE benchmarkIndex INT;
		DECLARE benchmarks CURSOR FOR SELECT a.id FROM master_benchmark_index_tb a;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		
		SELECT MAX(DATE_FORMAT(scheduledate,'%Y-%m-%d')) FROM `job_schedule_tb` INTO eodDate;
		
		OPEN benchmarks;
		read_benchmarks: LOOP
			FETCH benchmarks INTO benchmarkIndex;
			IF done THEN
				LEAVE read_benchmarks;
			END IF;
			SELECT b.close INTO closeValue FROM benchmark_performance_tb b
				WHERE b.benchmark = benchmarkIndex AND DATE_FORMAT(b.datetime,'%Y-%m-%d') = (
					SELECT DISTINCT MAX(DATE_FORMAT(c.datetime,'%Y-%m-%d')) FROM benchmark_performance_tb c WHERE c.benchmark = benchmarkIndex
						AND DATE_FORMAT(c.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
					LIMIT 1
					) LIMIT 1;
			
			/*SELECT b.close INTO startValue90Days FROM benchmark_performance_tb b
				WHERE b.benchmark = benchmarkIndex AND b.datetime = (
					SELECT DISTINCT MIN(DATE_FORMAT(c.datetime,'%Y-%m-%d')) FROM benchmark_performance_tb c WHERE c.benchmark = benchmarkIndex
						AND DATE_FORMAT(c.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
						LIMIT 1
					) LIMIT 1;*/
					
			SELECT b.close INTO startValue90Days  FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			b.datetime = DATE_FORMAT(DATE_SUB(eodDate, INTERVAL 90 DAY),'%Y-%m-%d');
			SET returns90Days = ((closeValue/startValue90Days)-1)*100;
			
			/*SELECT b.close INTO startValue1Year FROM benchmark_performance_tb b
				WHERE b.benchmark = benchmarkIndex AND b.datetime = (
					SELECT DISTINCT MIN(DATE_FORMAT(c.datetime,'%Y-%m-%d')) FROM benchmark_performance_tb c WHERE c.benchmark = benchmarkIndex
						AND DATE_FORMAT(c.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 1 YEAR) AND eodDate
						LIMIT 1
					) LIMIT 1;*/
					
			SELECT b.close INTO startValue90Days  FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			b.datetime = DATE_FORMAT(DATE_SUB(eodDate, INTERVAL 1 YEAR ),'%Y-%m-%d');
			SET returns1Year = ((closeValue/startValue1Year)-1)*100;
			
			/* Calculation 5 day return */
			SELECT b.close INTO startValue5Days FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			DATE_FORMAT(b.datetime,'%Y-%m-%d') = DATE_FORMAT(DATE_SUB(eodDate, INTERVAL 5 DAY),'%Y-%m-%d');
			SET returns5Days = ((closeValue/startValue5Days)-1)*100;
			/*Calculation 1 Month Return*/
			
                        SELECT b.close INTO startValue1Month FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			DATE_FORMAT(b.datetime,'%Y-%m-%d') = DATE_FORMAT(DATE_SUB(eodDate, INTERVAL 1 MONTH),'%Y-%m-%d');
			SET returns1Month = ((closeValue/startValue1Month)-1)*100;			
			
			/* Calculation 6 month return */
			SELECT b.close INTO startValue6Month FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			DATE_FORMAT(b.datetime,'%Y-%m-%d') = DATE_FORMAT(DATE_SUB(eodDate, INTERVAL 6 MONTH),'%Y-%m-%d');
			SET returns6Month = ((closeValue/startValue6Month)-1)*100;
			
			/* Calculation 5 year return */
			SELECT b.close INTO startValue5Years FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			DATE_FORMAT(b.datetime,'%Y-%m-%d') = DATE_FORMAT(DATE_SUB(eodDate, INTERVAL 5 YEAR),'%Y-%m-%d');
			SET returns5Years = ((closeValue/startValue5Years)-1)*100;
			
			/* Calculation 10 year return */
			SELECT b.close INTO startValue10Years FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			DATE_FORMAT(b.datetime,'%Y-%m-%d') = DATE_FORMAT(DATE_SUB(eodDate, INTERVAL 10 YEAR),'%Y-%m-%d');
			SET returns10Years = ((closeValue/startValue10Years)-1)*100;
			
			/* Calculation YearToDate return */
			
			SELECT b.close INTO startValueYtd FROM benchmark_performance_tb b WHERE b.benchmark = benchmarkIndex AND 
			DATE_FORMAT(b.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT MAKEDATE(YEAR(DATE_FORMAT(eodDate,'%Y-%m-%d')),1)),'%Y-%m-%d');
			SET returnsYtd = ((closeValue/startValueYtd)-1)*100;
			
						
			SELECT returns90Days, returns1Year;
			UPDATE portfolio_tb SET benchmark_90_days_returns = returns90Days, benchmark_1_year_returns = returns1Year
				WHERE BENCHMARK = benchmarkIndex;
				
		END LOOP;
		CLOSE benchmarks;
	END$$

DELIMITER ;