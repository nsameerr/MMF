DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `sp_ManagementFeeCalculation`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `sp_ManagementFeeCalculation`(
	IN p_RelationId INTEGER,
	IN p_Quater BOOLEAN,
	IN p_FeeVariable DECIMAL(15,2),
	IN p_TotalAllocatedFund DECIMAL(15,2),
	IN p_FeeVariableType BOOLEAN,
	IN p_MgntFeeFixed DECIMAL(15,2),
	IN p_ContractDate DATE,
	IN p_EcsDueDate DATETIME
	)
BEGIN
	DECLARE v_daysLeftToQtr INT;
	DECLARE v_currentQtr INT;	
	DECLARE v_nxtQtr DATE;
	DECLARE v_date VARCHAR(10);
	DECLARE v_cashFlowFee DECIMAL(15,4) DEFAULT 0.0;
	DECLARE v_qtrBillAmt  DECIMAL(15,4) DEFAULT 0.0;
	DECLARE v_feeVariable DECIMAL(15,7);
	DECLARE v_Tax DECIMAL(5,2);
	DECLARE v_taxCalculated DECIMAL(15,7) DEFAULT 0.0;
	DECLARE v_CashFlow DECIMAL(15,2);
	
	SELECT `cashFlow` INTO v_CashFlow FROM `customer_portfolio_tb` WHERE `relation_id` = p_RelationId;
	SET v_currentQtr = QUARTER(NOW());
	SET v_Tax = 0.15;
		
	 
	IF v_currentQtr =2 THEN
		SET v_date = CONCAT('1,', '7,', YEAR(NOW()));
		SET v_nxtQtr = STR_TO_DATE(v_date,'%d,%m,%Y');
		
	ELSEIF v_currentQtr =3 THEN
		SET v_date = CONCAT('1,', '10,', YEAR(NOW()));
		SET v_nxtQtr = STR_TO_DATE(v_date,'%d,%m,%Y');
	ELSEIF v_currentQtr =4 THEN
		SET v_date = CONCAT('1,', '1,', YEAR(NOW())+1);
		SET v_nxtQtr = STR_TO_DATE(v_date,'%d,%m,%Y'); 
	ELSEIF v_currentQtr =1 THEN
		SET v_date = CONCAT('1,', '4,', YEAR(NOW()));
		SET v_nxtQtr = STR_TO_DATE(v_date,'%d,%m,%Y'); 
	END IF;
	
	SET v_daysLeftToQtr = DATEDIFF(v_nxtQtr,NOW());
	
	
	IF p_FeeVariableType THEN
		SET v_feeVariable = p_FeeVariable/(365 * 100);
		
		IF v_CashFlow > 0 THEN
			SET v_cashFlowFee = (v_CashFlow*v_daysLeftToQtr)*v_feeVariable;
		END IF;
		
		IF p_Quater THEN
			SET v_qtrBillAmt = (p_TotalAllocatedFund*v_daysLeftToQtr)*v_feeVariable;
		ELSEIF (DATE_FORMAT(p_ContractDate,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-%d'),INTERVAL 1 DAY)) THEN
			SET v_qtrBillAmt = (p_TotalAllocatedFund*v_daysLeftToQtr)*v_feeVariable;
		ELSE
			SET v_qtrBillAmt = v_cashFlowFee;
		END IF;
		SET v_taxCalculated = (v_qtrBillAmt * v_Tax);
		SET v_qtrBillAmt = v_qtrBillAmt+v_taxCalculated;
		UPDATE customer_advisor_mapping_tb 
			SET mgmt_fee_last_calculated = NOW(), mgmt_fee_amt = v_qtrBillAmt
			WHERE relation_id = p_RelationId;
	
	ELSE
	
	
		IF (DATE_FORMAT(p_ContractDate,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-%d'),INTERVAL 1 DAY)) THEN
			SET v_qtrBillAmt = (p_MgntFeeFixed*v_daysLeftToQtr)/90;
		ELSE
			SET v_qtrBillAmt = p_MgntFeeFixed;
		END IF;	
		
		SET v_taxCalculated = (v_qtrBillAmt * v_Tax);
		SET v_qtrBillAmt = v_qtrBillAmt+v_taxCalculated;
		UPDATE customer_advisor_mapping_tb 
			SET mgmt_fee_last_calculated = NOW(), mgmt_fee_amt = v_qtrBillAmt
			WHERE relation_id = p_RelationId;
	END IF;
	
INSERT  INTO customer_management_fee_tb (relation_id,customer_id,advisor_id,fee_calculate_date,mgmt_fee,ecs_pay_date,ecs_paid,reg_id,
		isQuarterEnd,cashFlow,daysToQuarterEnd,MgtFeeonCrntCF,totalAllocatdFund)
        SELECT  p_RelationId,c.customer_id,b.advisor_id,NOW(),v_qtrBillAmt,p_EcsDueDate AS ecsPayDate,FALSE,c.registration_id,
		p_Quater,v_CashFlow,v_daysLeftToQtr,v_cashFlowFee,p_TotalAllocatedFund
        FROM customer_advisor_mapping_tb a, master_advisor_tb b, master_customer_tb c
		WHERE a.relation_id = p_RelationId AND b.advisor_id = a.advisor_id AND c.customer_id = a.customer_id;	
	
	
SELECT  v_qtrBillAmt,b.advisor_id advisorId,CONCAT(b.first_name,' ',b.`middle_name`,' ',b.`last_name`)
	advisorFirstName, b.email advisorEmail,c.customer_id customerId, c.first_name customerFirstName,
        c.email customerEmail,c.registration_id
FROM customer_advisor_mapping_tb a, master_advisor_tb b, master_customer_tb c
WHERE a.relation_id = p_RelationId AND b.advisor_id = a.advisor_id 
AND c.customer_id = a.customer_id AND b.`is_active_user` = TRUE AND c.`is_active_user` = TRUE;	
	
	
SELECT p_ContractDate,p_FeeVariableType,v_feeVariable,v_daysLeftToQtr,v_cashFlowFee,p_Quater,v_qtrBillAmt,p_TotalAllocatedFund,v_CashFlow;
    END$$

DELIMITER ;