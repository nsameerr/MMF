DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `update_customer_position_details`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `update_customer_position_details`(
	)
BEGIN
	UPDATE mmf_ret_portfolio_splitup SET processedQty = 0;
	
	UPDATE customer_portfolio_securities_tb SET exe_units = 0;
	
	UPDATE customer_portfolio_securities_tb ps
	INNER JOIN `customer_portfolio_tb` cp ON (ps.`customer_portfolio_id` = cp.customer_portfolio_id)
	INNER JOIN mmf_ret_portfolio_splitup H ON (H.`TradeCode` = cp.`oms_login_id`
		AND H.`NseToken` = ps.`venueScriptCode` AND H.`NseSymbol` = ps.`security_code`)
	SET ps.`exe_units` = H.`DpHolding` + H.`NsePool` + H.`NseUnAc` - H.processedQty,
	H.processedQty = H.processedQty + H.`DpHolding` + H.`NsePool` + H.`NseUnAc`,ps.`average_rate` = H.`AvgRate`
	WHERE ps.`venueCode` = 'NSE' AND H.`DpHolding` + H.`NsePool` + H.`NseUnAc` - H.processedQty > 0;
	
	UPDATE customer_portfolio_securities_tb ps
	INNER JOIN `customer_portfolio_tb` cp ON (ps.`customer_portfolio_id` = cp.customer_portfolio_id)
	INNER JOIN mmf_ret_portfolio_splitup H ON (H.`TradeCode` = cp.`oms_login_id`
		AND H.`Bsecode` = ps.`venueScriptCode` AND H.`BseSymbol` = ps.`security_code`)
	SET ps.`exe_units` = H.`DpHolding` + H.`BsePool` + H.`BseUnAc` - H.processedQty,
	H.processedQty = H.processedQty + H.`DpHolding` + H.`BsePool` + H.`BseUnAc`,ps.`average_rate` = H.`AvgRate`
	WHERE ps.`venueCode` = 'BSE' AND H.`DpHolding` + H.`BsePool` + H.`BseUnAc` - H.processedQty > 0;
	
	
	UPDATE customer_portfolio_securities_tb ps
	INNER JOIN `customer_portfolio_tb` cp ON (ps.`customer_portfolio_id` = cp.customer_portfolio_id)
	INNER JOIN `mmf_ret_portfolio_splitup` H ON (H.`TradeCode` = cp.`oms_login_id`
		AND H.`NseToken` = ps.`venueScriptCode` AND H.`NseSymbol` = ps.`security_code`)
	SET ps.`exe_units` = H.`DpHolding` - H.processedQty,
	H.processedQty = H.processedQty + H.`DpHolding`,ps.`average_rate` = H.`AvgRate`
	WHERE ps.`venueCode` = 'NSEMF' AND H.`DpHolding` + H.`NsePool` + H.`NseUnAc` - H.processedQty > 0;


	
	
	END$$

DELIMITER ;