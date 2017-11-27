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
				WHERE b.benchmark = benchmarkIndex AND b.datetime = (
					SELECT DISTINCT MAX(DATE_FORMAT(c.datetime,'%Y-%m-%d')) FROM benchmark_performance_tb c WHERE c.benchmark = benchmarkIndex
						AND DATE_FORMAT(c.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
					LIMIT 1
					) LIMIT 1;
			/* Calculate 90 day returns */
			SELECT b.close INTO startValue90Days FROM benchmark_performance_tb b
				WHERE b.benchmark = benchmarkIndex AND b.datetime = (
					SELECT DISTINCT MIN(DATE_FORMAT(c.datetime,'%Y-%m-%d')) FROM benchmark_performance_tb c WHERE c.benchmark = benchmarkIndex
						AND DATE_FORMAT(c.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
						LIMIT 1
					) LIMIT 1;
			SET returns90Days = ((closeValue/startValue90Days)-1)*100;
			/* Calculate 1 year returns */
			SELECT b.close INTO startValue1Year FROM benchmark_performance_tb b
				WHERE b.benchmark = benchmarkIndex AND b.datetime = (
					SELECT DISTINCT MIN(DATE_FORMAT(c.datetime,'%Y-%m-%d')) FROM benchmark_performance_tb c WHERE c.benchmark = benchmarkIndex
						AND DATE_FORMAT(c.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 1 YEAR) AND eodDate
						LIMIT 1
					) LIMIT 1;
			SET returns1Year = ((closeValue/startValue1Year)-1)*100;
			/* Update portfolio  benchmark 90 days returns and 1 year returns */
			SELECT returns90Days, returns1Year;
			UPDATE portfolio_tb SET benchmark_90_days_returns = returns90Days, benchmark_1_year_returns = returns1Year
				WHERE BENCHMARK = benchmarkIndex;
		END LOOP;
		CLOSE benchmarks;
	END$$

DELIMITER ;