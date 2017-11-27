DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `copy_cash_flow_frombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `copy_cash_flow_frombo_proc`()
BEGIN
	
		INSERT INTO cash_flow_tb(`trade_code` ,`pay_in` ,`pay_out` ,`tran_date` ) 
		SELECT DISTINCT(`TradeCode` ),`PayIn` ,`PayOut` ,`TranDate` FROM  `mmf_backoffice_db`.`mmf_cash_flow` 
		WHERE (DATE_FORMAT(TranDate,'%Y-%m-%d')) = DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-%d'),INTERVAL 1 DAY);
		
	END$$

DELIMITER ;