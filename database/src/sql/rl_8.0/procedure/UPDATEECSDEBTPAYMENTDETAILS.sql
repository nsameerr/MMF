DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `updateEcsDebtPaymentDetails`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `updateEcsDebtPaymentDetails`(
	IN p_RegId VARCHAR(20),
	IN p_Duedate DATETIME,
	IN p_debtAmount DECIMAL(12,2),
	IN p_Status VARCHAR(20),
	IN p_FailureReason VARCHAR(100),
 	IN p_NewDuedate DATETIME,
 	IN p_feeCalculatedate DATETIME
    )
BEGIN
	DECLARE v_MgntFee BOOLEAN;
	DECLARE v_PerfFee BOOLEAN;
	DECLARE v_Status BOOLEAN;
        DECLARE v_Submitted BOOLEAN;
        DECLARE v_DueDate DATETIME;
	SELECT a.`mFee`, a.`pFee`
		INTO v_MgntFee, v_PerfFee
		FROM `ecs_debt_payment_file_content_tb` a WHERE a.`reg_id` = p_RegId 
		AND DATE_FORMAT(a.`due_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d')
		AND DATE_FORMAT(a.`feeCalculatedate`,'%Y-%m-%d') = DATE_FORMAT(p_feeCalculatedate ,'%Y-%m-%d');
	
	UPDATE `ecs_debt_payment_file_content_tb` a 
	SET a.`status` = p_Status, a.`failure_reason` = p_FailureReason, a.last_updated = NOW()
	WHERE a.`reg_id` = p_RegId 
	AND DATE_FORMAT(a.`due_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d')
	AND DATE_FORMAT(a.`feeCalculatedate`,'%Y-%m-%d') = DATE_FORMAT(p_feeCalculatedate ,'%Y-%m-%d');
	
	IF (p_Status = 'SUCCESS')THEN
             SET v_Status = TRUE;
             SET v_Submitted = TRUE;
             SET v_DueDate = p_Duedate;
	ELSE
            SET v_Status = FALSE;
            SET v_Submitted = FALSE;
            SET v_DueDate = p_NewDuedate;
	END IF;
	
	IF (v_MgntFee) THEN
	UPDATE `customer_management_fee_tb` a SET `ecs_paid` = v_Status,submitted_ecs = v_Submitted,
        ecs_pay_date = v_DueDate WHERE a.`reg_id` = p_RegId 
		AND DATE_FORMAT(a.`ecs_pay_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d')
		AND DATE_FORMAT(a.`fee_calculate_date`,'%Y-%m-%d') = DATE_FORMAT(p_feeCalculatedate ,'%Y-%m-%d');
	END IF;
	
	IF (v_PerfFee) THEN
	UPDATE `customer_performance_fee_tb` a SET `ecs_paid` = v_Status,submitted_ecs = v_Submitted,
        ecs_pay_date = v_DueDate  WHERE a.`reg_id` = p_RegId 
		AND DATE_FORMAT(a.`ecs_pay_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d')
		AND DATE_FORMAT(a.`fee_calculate_date`,'%Y-%m-%d') = DATE_FORMAT(p_feeCalculatedate ,'%Y-%m-%d');
	END IF;
END$$

DELIMITER ;