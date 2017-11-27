DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_client_portflio_daily_opening_closing_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_client_portflio_daily_opening_closing_proc`
	(
	  IN P_EOD_DATE 		VARCHAR(50),
	  IN P_CUSTOMER_PORTFOLIO_ID 	INT 
	)
	
	/*	CREATED BY	: 07662
	|	CREATED ON	: 24-06-2014
	|	PURPOSE		: EOD CALCULATION FOR CLIENT PORTFOLIO DAILY OPENING AND CLOSING VALUE
	|	EXECUTE CALL	: CALL eod_client_portflio_daily_opening_closing_proc('your EOD date',coustomer poertfolio id);
	*/
	
	
	BEGIN
	
		DECLARE v_no_of_rows INT;
		
		SELECT COUNT(*) INTO v_no_of_rows  FROM customer_portfolio_log_tb T1 WHERE T1.`customer_portfolio_id` = P_CUSTOMER_PORTFOLIO_ID;
	         
		INSERT INTO `customer_portfolio_log_tb`(`log_date`,`customer_id`,`customer_portfolio_id`,`opening_portfolio_value`,`closing_portfolio_value`,`cash_on_closing`)
		SELECT T2.datetime,T3.`customer_id`,T2.`customer_portfolio_id`,
		IF(v_no_of_rows = 0,T5.`allocated_funds`,T4.`closing_portfolio_value`),
		T2.market_value_after_cash_flow,T3.`ledgerbalanec` 
		FROM `customer_portfolio_performance_tb` T2 
		INNER JOIN customer_daily_cash_balance_tb T3 ON (T3.`customer_portfolio_id` = T2.`customer_portfolio_id`)
		LEFT JOIN `customer_portfolio_log_tb` T4 
			ON (T4.`customer_id` = T3.`customer_id` AND T4.`customer_portfolio_id` = T2.`customer_portfolio_id`
			AND DATE_FORMAT(T4.`log_date`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
		LEFT JOIN `customer_advisor_mapping_tb` T5 ON (T5.`customer_id` = T3.`customer_id`)
		WHERE DATE_FORMAT(T2.`datetime`,'%Y-%m-%d') = DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d') AND T2.`customer_portfolio_id` = P_CUSTOMER_PORTFOLIO_ID
                GROUP BY T2.`datetime`,T2.`customer_portfolio_id` ; 

	 
	END$$

DELIMITER ;