DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_recmd_portfolio_performance_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_recmd_portfolio_performance_sp`(
		IN P_PORTFOLIO_ID		INT
	)
BEGIN
		DECLARE startValue90Days DECIMAL(12,2);
		DECLARE returns90Days DECIMAL(12,2);
		DECLARE startValue1Year DECIMAL(12,2);
		DECLARE returns1Year DECIMAL(12,2);
		DECLARE closeValue DECIMAL(12,2);
                DECLARE eodDate DATE;
                
		SELECT MAX(DATE_FORMAT(scheduledate,'%Y-%m-%d')) FROM `job_schedule_tb` INTO eodDate;
		SELECT a.closing_value INTO closeValue FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND a.datetime = (
					SELECT MAX(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM portfolio_performance_tb b WHERE b.portfolio_id = P_PORTFOLIO_ID
						AND DATE_FORMAT(b.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
						LIMIT 1
					) LIMIT 1;
		/* Calculate 90 day returns */
		SELECT a.closing_value INTO startValue90Days FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND a.datetime = (
					SELECT MIN(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM portfolio_performance_tb b WHERE b.portfolio_id = P_PORTFOLIO_ID
						AND DATE_FORMAT(b.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
						LIMIT 1
					) LIMIT 1;
		SET returns90Days = ((closeValue/startValue90Days)-1)*100;
		/* Calculate 1 year returns */
		SELECT a.closing_value INTO startValue1Year FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND a.datetime = (
					SELECT MIN(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM portfolio_performance_tb b WHERE b.portfolio_id = P_PORTFOLIO_ID
						AND DATE_FORMAT(b.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 1 YEAR) AND eodDate
						LIMIT 1
					) LIMIT 1;
		SET returns1Year = ((closeValue/startValue1Year)-1)*100;
		UPDATE portfolio_tb SET portfolio_90_day_returns = returns90Days, portfolio_1_year_returns = returns1Year 
			WHERE `portfolio_id` = P_PORTFOLIO_ID;
			SELECT returns90Days,returns1Year, closeValue,startValue1Year,startValue90Days;
	END$$

DELIMITER ;