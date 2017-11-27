DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_client_portfolio_performance_calculation_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_client_portfolio_performance_calculation_sp`(
	IN p_CustomerPortfolioId INTEGER,
	IN p_StartDate DATETIME,
	IN p_EndDate DATETIME,
	OUT p_Return DECIMAL(12,5)	
    )
BEGIN
	DECLARE v_DoneCalculation INT DEFAULT FALSE;
    	DECLARE v_Return DECIMAL(12,5);
	DECLARE v_SPReturn DECIMAL(12,5);
	DECLARE v_SubPeriodReturns CURSOR FOR SELECT a.sub_period_return + 1 FROM customer_portfolio_performance_tb a
		WHERE a.customer_portfolio_id = p_CustomerPortfolioId AND DATE_FORMAT(a.datetime,'%Y-%m-%d') BETWEEN 
		DATE_FORMAT(p_StartDate,'%Y-%m-%d') AND DATE_FORMAT(p_EndDate,'%Y-%m-%d');
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_DoneCalculation = TRUE;
	
	SET v_Return = 1.0;
	OPEN v_SubPeriodReturns;
	readSubPeriodReturns: LOOP
		FETCH v_SubPeriodReturns INTO v_SPReturn;
		
		IF v_DoneCalculation THEN
			LEAVE readSubPeriodReturns;
		END IF;
			SET v_Return = v_Return * v_SPReturn;
	END LOOP;
	CLOSE v_SubPeriodReturns;
	SET p_Return = v_Return - 1;
END$$

DELIMITER ;