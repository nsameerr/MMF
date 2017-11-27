DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_client_portfolio_performance_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_client_portfolio_performance_sp`(
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
		SELECT a.closing_value INTO closeValue FROM customer_portfolio_performance_tb a
				 WHERE a.customer_portfolio_id=P_PORTFOLIO_ID AND a.datetime = (
					SELECT MAX(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM customer_portfolio_performance_tb b WHERE b.customer_portfolio_id = P_PORTFOLIO_ID
						AND DATE_FORMAT(b.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
					LIMIT 1
					) LIMIT 1;
		
		/*SELECT a.closing_value INTO startValue90Days FROM customer_portfolio_performance_tb a
				 WHERE a.customer_portfolio_id=P_PORTFOLIO_ID AND a.datetime = (
					SELECT MIN(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM customer_portfolio_performance_tb b WHERE b.customer_portfolio_id = P_PORTFOLIO_ID
						AND DATE_FORMAT(b.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate
					LIMIT 1
					) LIMIT 1;*/
					
					
		SELECT a.closing_value INTO startValue90Days FROM customer_portfolio_performance_tb a
			WHERE a.customer_portfolio_id = P_PORTFOLIO_ID AND a.datetime = DATE_FORMAT((SELECT DATE_SUB(eodDate, INTERVAL 90 DAY)),'%Y-%m-%d') LIMIT 1;	
					
		SET returns90Days = ((closeValue/startValue90Days)-1)*100;
		
		/*SELECT a.closing_value INTO startValue1Year FROM customer_portfolio_performance_tb a
				 WHERE a.customer_portfolio_id=P_PORTFOLIO_ID AND a.datetime = (
					SELECT MIN(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM customer_portfolio_performance_tb b WHERE b.customer_portfolio_id = P_PORTFOLIO_ID
						AND DATE_FORMAT(b.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 1 YEAR) AND eodDate
					LIMIT 1
					) LIMIT 1;*/
					
		SELECT a.closing_value INTO startValue90Days FROM customer_portfolio_performance_tb a
			WHERE a.customer_portfolio_id = P_PORTFOLIO_ID AND a.datetime = DATE_FORMAT((SELECT DATE_SUB(eodDate,INTERVAL 1 YEAR)),'%Y-%m-%d') LIMIT 1;		
		
		SET returns1Year = ((closeValue/startValue1Year)-1)*100;
		
		UPDATE `customer_portfolio_tb` SET `portfolio_90_day_returns` = returns90Days, `portfolio_1_year_returns` = returns1Year
			WHERE `customer_portfolio_id` = P_PORTFOLIO_ID;
			
	END$$

DELIMITER ;