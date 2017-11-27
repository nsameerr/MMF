DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `updateCashBalnceOmsDisabledCase`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `updateCashBalnceOmsDisabledCase`(
    IN OMS_ID  VARCHAR(40),
    IN PAY_IN  DECIMAL(15,2),
    IN PAY_OUT DECIMAL(15,2),
    IN trDate DATE
    )
BEGIN
        DECLARE p_CurrentBalance DECIMAL(12,2) DEFAULT 0.0;
        DECLARE p_maxDate DATE;
    
        IF (SELECT COUNT(*) FROM `cash_flow_tb` WHERE `trade_code`= OMS_ID AND DATE_FORMAT(`tran_date`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d') > 0) THEN
	     
	     IF (SELECT COUNT(*) FROM `mmf_daily_client_balance_tb` WHERE `tradecode`= OMS_ID AND DATE_FORMAT(`trndate`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d') > 0) THEN
			
		    SELECT `ledgerbalanec` INTO p_CurrentBalance FROM `mmf_daily_client_balance_tb`
		    WHERE `tradecode`= OMS_ID AND DATE_FORMAT(`trndate`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d');
		     
		    SET p_CurrentBalance = (p_CurrentBalance+IFNULL(PAY_IN,0))+IFNULL(PAY_OUT,0);
	            
		    UPDATE mmf_daily_client_balance_tb SET `ledgerbalanec`= p_CurrentBalance WHERE `tradecode`= OMS_ID
		    AND DATE_FORMAT(`trndate`,'%Y-%m-%d') = DATE_FORMAT(trDate,'%Y-%m-%d');
		     
	     ELSE
		    SELECT MAX(`tradedate`) INTO p_maxDate FROM `customer_daily_cash_balance_tb` WHERE `omsLoginId`= OMS_ID;
		    
		    SELECT `ledgerbalanec` INTO p_CurrentBalance FROM `customer_daily_cash_balance_tb`
		    WHERE `omsLoginId`= OMS_ID  AND DATE_FORMAT(`tradedate`,'%Y-%m-%d') = DATE_FORMAT(p_maxDate,'%Y-%m-%d');
		     
		    SET p_CurrentBalance = (p_CurrentBalance+IFNULL(PAY_IN,0))+IFNULL(PAY_OUT,0);
	            
		    UPDATE customer_daily_cash_balance_tb SET `ledgerbalanec`= p_CurrentBalance WHERE `omsLoginId`= OMS_ID
		    AND DATE_FORMAT(`tradedate`,'%Y-%m-%d') = DATE_FORMAT(p_maxDate,'%Y-%m-%d');
		     
	     END IF;
	     
	     
	END IF;
    END$$

DELIMITER ;