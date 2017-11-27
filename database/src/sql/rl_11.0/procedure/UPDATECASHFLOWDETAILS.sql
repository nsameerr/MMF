DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `updateCashFlowDetails`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `updateCashFlowDetails`(
	IN OMS_ID  VARCHAR(40),
	IN PAY_IN  DECIMAL(15,2),
	IN PAY_OUT DECIMAL(15,2),
	IN trDate DATE
)
BEGIN
	DECLARE fund DECIMAL(15,2);
	DECLARE p_investorID INTEGER(11);
	DECLARE p_allocateFund DECIMAL(15,2);
	DECLARE p_relationID INTEGER(11);
	
	IF (DATE_FORMAT(trDate ,'%Y-%m-%d')= DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-%d'),INTERVAL 1 DAY)) THEN	
	    
		IF ((SELECT COUNT(*) FROM `customer_portfolio_tb` WHERE `oms_login_id`= OMS_ID ) > 0) THEN
		
			SET p_investorID = (SELECT customer_id FROM customer_portfolio_tb WHERE oms_login_id= OMS_ID);
			SET p_relationID = (SELECT `relation_id` FROM customer_portfolio_tb WHERE oms_login_id= OMS_ID);
			SET p_allocateFund = (SELECT `allocatedFund` FROM customer_portfolio_tb WHERE oms_login_id= OMS_ID); 
		
			UPDATE customer_portfolio_tb SET `allocatedFund`= p_allocateFund + IFNULL(PAY_IN,0) +IFNULL(PAY_OUT,0),
			`cashFlowDFlag` = CASE WHEN  IFNULL(PAY_IN,0) > 0 THEN TRUE ELSE FALSE END,
			`cashFlow` =  CASE WHEN IFNULL(PAY_IN,0) > 0 THEN IFNULL(`cashFlow`,0)+IFNULL(PAY_IN,0) ELSE 0 END
			WHERE oms_login_id= OMS_ID;
			
			UPDATE `customer_advisor_mapping_tb` SET `allocated_funds`= `allocated_funds`+ IFNULL(PAY_IN,0) +IFNULL(PAY_OUT,0)
			WHERE `customer_id`= p_investorID AND `relation_id`= p_relationID;
			
			UPDATE `master_customer_tb` SET `total_funds`= `total_funds`+IFNULL(PAY_IN,0) +IFNULL(PAY_OUT,0),
			`total_allocated_funds` = `total_funds`
			WHERE `oms_login_id`= OMS_ID;
			
			UPDATE `cash_flow_tb` SET `processed` = TRUE
			WHERE `trade_code` = OMS_ID;
			
		ELSEIF ((SELECT COUNT(*) FROM `master_customer_tb` WHERE `oms_login_id`= OMS_ID ) > 0) THEN
		
			UPDATE `master_customer_tb` SET `total_funds`= IFNULL(`total_funds`,0) +IFNULL(PAY_IN,0) +IFNULL(PAY_OUT,0),
			`total_allocated_funds` = `total_funds`
			WHERE `oms_login_id`= OMS_ID;
			
			UPDATE `cash_flow_tb` SET `processed` = TRUE
			WHERE `trade_code` = OMS_ID;
		
		END IF;
	
	END IF;
	END$$

DELIMITER ;