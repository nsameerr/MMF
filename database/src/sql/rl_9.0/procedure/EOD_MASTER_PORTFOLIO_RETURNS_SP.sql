DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_master_portfolio_returns_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_master_portfolio_returns_sp`(
		IN P_EOD_DATE 	VARCHAR(30),
                IN P_CURRENT_DATE 	VARCHAR(30)
	)
BEGIN
	
	
	
	
	INSERT INTO `portfolio_performance_tb`
		(
		`portfolio_id`,`datetime`,`closing_value`,`market_value`,`market_value_after_cash_flow`,`sub_period_return`
		)
		SELECT T1.`portfolio_id`,P_EOD_DATE,
		SUM(T3.`exe_units` * T3.`current_price`) AS closing_value,
		SUM(T3.`yesterDayUnitCount` * T3.`current_price`) AS market_value,
		SUM(T3.`exe_units` * T3.`current_price`) AS market_value_after_cash_flow,
		IFNULL((SUM(T3.`yesterDayUnitCount` * T3.`current_price`) - T4.`market_value_after_cash_flow`) / T4.`market_value_after_cash_flow`,0) AS sub_period_return
		FROM `portfolio_tb` T1
		INNER JOIN `portfolio_details_tb` T2 ON ( T2.`portfolio_id` = T1.`portfolio_id`)
		INNER JOIN `portfolio_securities_tb` T3 ON (T3.`portfolio_id` = T1.`portfolio_id` AND T3.`portfolio_details_id` = T2.`portfolio_details_id`)
		LEFT JOIN `portfolio_performance_tb` T4 
			ON (T4.`portfolio_id` = T1.`portfolio_id` AND DATE_FORMAT(T4.`datetime`,'%Y-%m-%d')  = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
		GROUP BY T1.`portfolio_id`;
	
	
	
	INSERT INTO `portfolio_details_performance_tb`
		(
		`portfolio_details_id`,`portfolio_id`,`asset_class_id`,`datetime`,`closing_value`,
		`market_value`,`market_value_after_cash_flow`,`sub_period_return`
		)
		SELECT T2.`portfolio_details_id`,T2.`portfolio_id`,T2.`asset_class_id`,P_EOD_DATE,
		SUM(T3.`exe_units` * T3.`current_price`) AS closing_value,
		SUM(T3.`yesterDayUnitCount` * T3.`current_price`) AS market_value,
		SUM(T3.`exe_units` * T3.`current_price`) AS market_value_after_cash_flow,
		IFNULL((SUM(T3.`yesterDayUnitCount` * T3.`current_price`) - T4.`market_value_after_cash_flow`) / T4.`market_value_after_cash_flow`,0) AS sub_period_return
		FROM `portfolio_tb` T1
		INNER JOIN `portfolio_details_tb` T2 ON ( T2.`portfolio_id` = T1.`portfolio_id`)
		INNER JOIN `portfolio_securities_tb` T3 ON (T3.`portfolio_id` = T1.`portfolio_id` AND T3.`portfolio_details_id` = T2.`portfolio_details_id`)
		LEFT JOIN  `portfolio_details_performance_tb` T4 
			ON (T4.`portfolio_id` = T1.`portfolio_id` AND T4.`portfolio_details_id` = T3.`portfolio_details_id` AND
			DATE_FORMAT(T4.`datetime`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
		GROUP BY T1.`portfolio_id`,T2.`asset_class_id`;
	
	
	INSERT INTO `portfolio_securities_performance_tb`
		(
		`portfolio_id`,`portfolio_details_id`,`portfolio_securities_id`,`asset_class_id`,`security_id`,`security_code`,	`datetime`,
		`closing_value`,`existing_units`,`closing_price`,`market_value`,`market_value_after_cash_flow`,`sub_period_return`
		)
		SELECT T3.`portfolio_id`,T3.`portfolio_details_id`,T3.`portfolio_securities_id`,T3.`asset_class_id`,T3.`security_id`,T3.`security_code`,
		P_EOD_DATE,
		SUM(T3.`exe_units` * T3.`current_price`) AS closing_value,
		T3.`exe_units`,T3.`current_price`,
		SUM(T3.`yesterDayUnitCount` * T3.`current_price`) AS market_value,
		SUM(T3.`exe_units` * T3.`current_price`) AS market_value_after_cash_flow,
		IFNULL((SUM(T3.`yesterDayUnitCount` * T3.`current_price`) - T4.`market_value_after_cash_flow`) / T4.`market_value_after_cash_flow`,0) AS sub_period_return
		FROM `portfolio_tb` T1
		INNER JOIN `portfolio_details_tb` T2 ON ( T2.`portfolio_id` = T1.`portfolio_id`)
		INNER JOIN `portfolio_securities_tb` T3 ON (T3.`portfolio_id` = T1.`portfolio_id` AND T3.`portfolio_details_id` = T2.`portfolio_details_id`)
		LEFT JOIN `portfolio_securities_performance_tb` T4 
			ON (T4.`portfolio_id` = T1.`portfolio_id` AND T4.`portfolio_securities_id` = T3.`portfolio_securities_id` 
			AND DATE_FORMAT(T4.`datetime`,'%Y-%m-%d')  = DATE_SUB(DATE_FORMAT(P_EOD_DATE,'%Y-%m-%d'),INTERVAL 1 DAY))
		GROUP BY T1.`portfolio_id`,T2.`asset_class_id`,T3.`portfolio_securities_id`;
	
	END$$

DELIMITER ;