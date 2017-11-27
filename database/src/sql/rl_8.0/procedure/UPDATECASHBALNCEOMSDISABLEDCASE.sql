DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `updateCashBalnceOmsDisabledCase`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `updateCashBalnceOmsDisabledCase`(
    IN OMS_ID  VARCHAR(40),
    IN trDate DATE
    )
BEGIN
        DECLARE p_cashFlowIn DECIMAL(12,2) DEFAULT 0.0;
        DECLARE p_cashFlowOut DECIMAL(12,2) DEFAULT 0.0;
        DECLARE p_CurrentBalance DECIMAL(12,2) DEFAULT 0.0;
    
        IF (SELECT COUNT(*) FROM `cash_flow_tb` WHERE `trade_code`= OMS_ID AND DATE_FORMAT(`tran_date`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d') > 0) THEN
	
	     SELECT SUM(`pay_in`),SUM(`pay_out`) INTO p_cashFlowIn,p_cashFlowOut FROM `cash_flow_tb`
	     WHERE `trade_code`= OMS_ID AND DATE_FORMAT(`tran_date`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d');
	     
	     IF (SELECT COUNT(*) FROM `mmf_daily_client_balance_tb` WHERE `tradecode`= OMS_ID AND DATE_FORMAT(`trndate`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d') > 0) THEN
			
		    SELECT `ledgerbalanec` INTO p_CurrentBalance FROM `mmf_daily_client_balance_tb`
		    WHERE `tradecode`= OMS_ID AND DATE_FORMAT(`trndate`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d');
		     
		    SET p_CurrentBalance = (p_CurrentBalance+p_cashFlowIn)-p_cashFlowOut;
	         
                    UPDATE mmf_daily_client_balance_tb SET `ledgerbalanec`= p_CurrentBalance WHERE `tradecode`= OMS_ID
                    AND DATE_FORMAT(`trndate`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d');
		     
	     ELSE
		     
		    SELECT `ledgerbalanec` INTO p_CurrentBalance FROM `customer_daily_cash_balance_tb`
		    WHERE `omsLoginId`= OMS_ID ;
		     
		    SET p_CurrentBalance = (p_CurrentBalance+p_cashFlowIn)-p_cashFlowOut;
	         
		    UPDATE customer_daily_cash_balance_tb SET `ledgerbalanec`= p_CurrentBalance WHERE `omsLoginId`= OMS_ID;
		     
	     END IF;
	     
	     
	END IF;
    END$$

DELIMITER ;