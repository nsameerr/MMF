DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_client_portfolio_returns_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_client_portfolio_returns_sp`(	
		IN P_EOD_DATE 			VARCHAR(30), 
		IN P_CUSTOMER_PORTFOLIO_ID 	INT
	)
BEGIN
	
	DECLARE BUY VARCHAR(3) DEFAULT 'B';
	DECLARE p_OrderDatetime DATETIME;
	DECLARE ORDER_EXECUTED INT;
	DECLARE p_CloseValue DECIMAL(10,5);
	DECLARE p_prevBalance DECIMAL(10,5) DEFAULT 0.0;
        SET P_EOD_DATE = IF(P_EOD_DATE = '',NULL,P_EOD_DATE);
	
	
	
	
	SELECT ROUND(AVG(`close`),2) INTO p_CloseValue FROM `benchmark_performance_tb` WHERE `benchmark` = 
        (SELECT IF(`venue_name` = "NSE",'1','2') FROM customer_portfolio_tb WHERE customer_portfolio_id = P_CUSTOMER_PORTFOLIO_ID);
        
        SELECT SUM(`ledgerbalanec`) INTO p_prevBalance FROM `customer_daily_cash_balance_tb` WHERE `customer_portfolio_id` =P_CUSTOMER_PORTFOLIO_ID AND
         DATE_FORMAT(`tradedate`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY);
        
        SELECT MAX(order_date) INTO p_OrderDatetime FROM `customer_transaction_execution_details_tb` WHERE customer_id = 
        (SELECT customer_id FROM customer_portfolio_tb WHERE customer_portfolio_id = P_CUSTOMER_PORTFOLIO_ID);
        
	SET ORDER_EXECUTED = IF(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d') = DATE_FORMAT(p_OrderDatetime,'%Y-%m-%d'),1,0);
	INSERT INTO `customer_portfolio_performance_tb`
	(`customer_portfolio_id`,`datetime`,`closing_value`,`market_value`,`cash_flow`,`market_value_after_cash_flow`,`sub_period_return`,`benchmark_unit_value`)
	SELECT T1.`customer_portfolio_id`,P_EOD_DATE,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(ROUND(SUM(ROUND(T3.`current_price`,2) * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),2),0)
         ELSE 0.00 END AS closing_value,	
         SUM(T3.`current_price` * T3.`yesterDayUnitCount`)+p_prevBalance AS market_value,
	 T1.`cashFlow` AS cash_flow,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(IFNULL(SUM(T3.`current_price` * T3.`yesterDayUnitCount`),0) + SUM(T4.`security_price` * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`))+T1.`cash_amount`,SUM(T3.`current_price` * T3.`exe_units`)+T1.`cash_amount`)
         ELSE  SUM(T3.`current_price` * T3.`exe_units`)+T1.`cash_amount` END AS market_value_after_cashflow,
        IFNULL((IFNULL(SUM(T3.`current_price` * T3.`yesterDayUnitCount`)+p_prevBalance,0) - IFNULL(T5.`market_value_after_cash_flow`,0) ) / IFNULL(T5.`market_value_after_cash_flow`,0),0.0) 
	AS sub_period_return, 
	(T1.benchmark_unit*p_CloseValue) AS benchmark_unit_value
	FROM customer_portfolio_tb T1
	INNER JOIN customer_portfolio_details_tb T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)
	INNER JOIN customer_portfolio_securities_tb T3 ON (T3.`customer_portfolio_details_id` = T2.`customer_portfolio_details_id`)
	LEFT JOIN customer_transaction_execution_details_tb T4 
		ON(T4.`venue_code` = T3.`venueCode` AND T4.`venue_script_code` = T3.`venueScriptCode` AND T4.`security_code` = T3.`security_code`
		AND T4.`portfolio_id` = T1.`portfolio_id` AND T4.`customer_id` = T1.`customer_id`
		AND (DATE_FORMAT(T4.`order_date`,'%Y-%m-%d')= DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d') ) )
	LEFT JOIN `customer_portfolio_performance_tb` T5 
		ON (T5.`customer_portfolio_id` = T1.`customer_portfolio_id` 
		AND DATE_FORMAT(T5.`datetime`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
        WHERE T1.`customer_portfolio_id` = P_CUSTOMER_PORTFOLIO_ID GROUP BY T1.`customer_portfolio_id`;
	
	
	
	INSERT INTO `recomended_customer_portfolio_performance_tb`
		(
		`customer_portfolio_id`,`datetime`,`closing_value`,`market_value`,`market_value_after_cash_flow`,`sub_period_return`
		)
		SELECT T1.`customer_portfolio_id`,P_EOD_DATE,
		ROUND(SUM(IFNULL(T3.`required_units`,0) * IFNULL(ROUND(T3.`current_price`,2),0)),2) AS closing_value,
		SUM(IFNULL(T3.`yesterDayUnitCount`,0) * IFNULL(T3.`current_price`,0)) AS market_value,
		IFNULL(T4.`market_value`,SUM(IFNULL(T3.`required_units`,0) * IFNULL(T3.`current_price`,0))) AS market_value_after_cash_flow,
		IFNULL((T4.`market_value` - T4.`market_value_after_cash_flow`) / T4.`market_value_after_cash_flow`,0) AS sub_period_return
		FROM `customer_portfolio_tb` T1
		INNER JOIN `customer_portfolio_details_tb` T2 ON ( T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)
		INNER JOIN `recomended_customer_portfolio_securities_tb` T3 ON (T3.`customer_portfolio_id` = T1.`customer_portfolio_id` AND T3.`customer_portfolio_details_id` = T2.`customer_portfolio_details_id`)
		LEFT JOIN `recomended_customer_portfolio_performance_tb` T4 
			ON (T4.`customer_portfolio_id` = T1.`customer_portfolio_id` AND T4.`datetime`= DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
		WHERE T3.`status` =TRUE AND T1.`customer_portfolio_id` = P_CUSTOMER_PORTFOLIO_ID GROUP BY T1.`customer_portfolio_id`; 
	
	
	
	
	
	
	INSERT INTO `customer_portfolio_details_performance_tb`(`customer_portfolio_id`,`customer_portfolio_details_id`,`customer_portfolio_allocation_id`,
	`asset_class_id`,`datetime`,`closing_value`,`market_value`,`cash_flow`,`market_value_after_cash_flow`,`sub_period_return`)
	SELECT T1.`customer_portfolio_id`,T2.`customer_portfolio_details_id`,T6.`customer_portfolio_allocation_id`,T2.`asset_class_id`,P_EOD_DATE,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(SUM(ROUND(T3.`current_price`,2) * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),0) 
        ELSE 0.00 END AS closing_value,
        SUM(T3.`current_price` * T3.`yesterDayUnitCount`) AS market_value,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(SUM(T4.`security_price` * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),0)
        ELSE 0 END  AS cash_flow,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(IFNULL(SUM(T3.`current_price` * T3.`yesterDayUnitCount`),0) + SUM(T4.`security_price` * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),SUM(T3.`current_price` * T3.`exe_units`))
        ELSE SUM(T3.`current_price` * T3.`exe_units`) END AS market_value_after_cashflow,
       IFNULL((IFNULL(SUM(T3.`current_price` * T3.`yesterDayUnitCount`),0) - IFNULL(T5.`market_value_after_cash_flow`,0) ) / IFNULL(T5.`market_value_after_cash_flow`,0),0.0) 
	AS sub_period_return
	FROM customer_portfolio_tb T1
	INNER JOIN customer_portfolio_details_tb T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)
	INNER JOIN customer_portfolio_securities_tb T3 ON (T3.`customer_portfolio_details_id` = T2.`customer_portfolio_details_id`)
	LEFT JOIN customer_transaction_execution_details_tb T4 
		ON(T4.`venue_code` = T3.`venueCode` AND T4.`venue_script_code` = T3.`venueScriptCode` AND T4.`security_code` = T3.`security_code`
		AND T4.`portfolio_id` = T1.`portfolio_id` AND T4.`customer_id` = T1.`customer_id` AND
		(DATE_FORMAT(T4.`order_date`,'%Y-%m-%d')= DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d')))
	LEFT JOIN `customer_portfolio_details_performance_tb` T5
		ON(T5.`customer_portfolio_details_id` = T2.`customer_portfolio_details_id` 
		AND DATE_FORMAT(T5.`datetime`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
	LEFT JOIN `customer_portfolio_performance_tb` T6
		ON (T6.`customer_portfolio_id` = T1.`customer_portfolio_id` AND DATE_FORMAT(T6.`datetime`,'%Y-%m-%d') = DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'))
	WHERE T1.`customer_portfolio_id` = P_CUSTOMER_PORTFOLIO_ID
	GROUP BY T4.`customer_id`,T3.`asset_class_id`;
	
	
	
	INSERT INTO `customer_portfolio_securities_performance_tb`(`customer_portfolio_id`,`customer_portfolio_details_id`,`customer_portfolio_securities_id`,
	`customer_portfolio_allocation_id`,`customer_portfolio_details_allocation_id`,`asset_class_id`,`security_code`,`datetime`,`closing_value`,
	`existing_units`,`closing_price`,`market_value`,`cash_flow`,`market_value_after_cash_flow`,`sub_period_return`)
	SELECT T1.`customer_portfolio_id`,T2.`customer_portfolio_details_id`,T3.`customer_portfolio_securities_id`,
	T6.`customer_portfolio_allocation_id`,T7.`customer_portfolio_details_allocation_id`,
	T2.`asset_class_id`,IFNULL(T4.`security_code`,T3.security_code),P_EOD_DATE,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(SUM(ROUND(T3.`current_price`,2) * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),0)  
        ELSE 0.00 END AS closing_value,
	IFNULL(SUM(IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),0) AS net_units,
	T3.`current_price`,
    	SUM(T3.`current_price` * T3.`yesterDayUnitCount`) AS market_value,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(SUM(T4.`security_price` * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),0)
        ELSE 0 END AS cash_flow,
	CASE WHEN ORDER_EXECUTED = 1 THEN IFNULL(IFNULL(SUM(T3.`current_price` * T3.`yesterDayUnitCount`),0) + SUM(T4.`security_price` * IF(T4.`buy_sell`= BUY,T4.`security_units`,-T4.`security_units`)),SUM(T3.`current_price` * T3.`exe_units`))
        ELSE SUM(T3.`current_price` * T3.`exe_units`) END AS market_value_after_cashflow,
   	IFNULL((IFNULL(SUM(T3.`current_price` * T3.`yesterDayUnitCount`),0) - IFNULL(T5.`market_value_after_cash_flow`,0) ) / IFNULL(T5.`market_value_after_cash_flow`,0),0.0) AS sub_period_return 
	FROM customer_portfolio_tb T1
	INNER JOIN customer_portfolio_details_tb T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)
	INNER JOIN customer_portfolio_securities_tb T3 ON (T3.`customer_portfolio_details_id` = T2.`customer_portfolio_details_id`)
	LEFT JOIN customer_transaction_execution_details_tb T4 
		ON(T4.`venue_code` = T3.`venueCode` AND T4.`venue_script_code` = T3.`venueScriptCode` AND T4.`security_code` = T3.`security_code`
		AND T4.`portfolio_id` = T1.`portfolio_id` AND T4.`customer_id` = T1.`customer_id` AND
		(DATE_FORMAT(T4.`order_date`,'%Y-%m-%d')= DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d')))
	LEFT JOIN `customer_portfolio_securities_performance_tb` T5 
		ON(T5.`customer_portfolio_securities_id` = T3.`customer_portfolio_securities_id` 
		AND DATE_FORMAT(T5.`datetime`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
	LEFT JOIN `customer_portfolio_performance_tb` T6
		ON (T6.`customer_portfolio_id` = T1.`customer_portfolio_id` AND DATE_FORMAT(T6.`datetime`,'%Y-%m-%d') =DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'))
	LEFT JOIN `customer_portfolio_details_performance_tb` T7
		ON (T7.`customer_portfolio_id` = T1.`customer_portfolio_id`  AND T7.`customer_portfolio_details_id` = T2.`customer_portfolio_details_id`
		AND DATE_FORMAT(T7.`datetime`,'%Y-%m-%d') = DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'))
	WHERE T1.`customer_portfolio_id` = P_CUSTOMER_PORTFOLIO_ID
	GROUP BY T4.`customer_id`,IFNULL(T4.`security_code`,T3.security_code),T4.`venue_script_code`,T4.`security_code`;
	END$$

DELIMITER ;