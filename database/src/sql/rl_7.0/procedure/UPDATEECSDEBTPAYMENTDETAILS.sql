DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `updateEcsDebtPaymentDetails`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `updateEcsDebtPaymentDetails`(
	IN p_RegId VARCHAR(20),
	IN p_Duedate DATETIME,
	IN p_debtAmount DECIMAL(12,2),
	IN p_Status VARCHAR(20),
	IN p_FailureReason VARCHAR(100),
 	IN p_NewDuedate DATETIME
    )
BEGIN
	DECLARE v_MgntFeeRow INTEGER;
	DECLARE v_PerfFeeRow INTEGER;
	DECLARE v_Status BOOLEAN;
        DECLARE v_Submitted BOOLEAN;
        DECLARE v_DueDate DATETIME;

	SELECT a.`mgnt_row_id`, a.`perf_row_id`
		INTO v_MgntFeeRow, v_PerfFeeRow
		FROM `ecs_debt_payment_file_content_tb` a WHERE a.`reg_id` = p_RegId 
		AND DATE_FORMAT(a.`due_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d');
	
	UPDATE `ecs_debt_payment_file_content_tb` a 
	SET a.`status` = p_Status, a.`failure_reason` = p_FailureReason, a.last_updated = NOW()
	WHERE a.`reg_id` = p_RegId 
		AND DATE_FORMAT(a.`due_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d');
	
	IF (p_Status = 'SUCCESS')THEN
             SET v_Status = TRUE;
             SET v_Submitted = TRUE;
             SET v_DueDate = p_Duedate;
	ELSE
            SET v_Status = FALSE;
            SET v_Submitted = FALSE;
            SET v_DueDate = p_NewDuedate;
	END IF;
	
	IF (v_MgntFeeRow IS NOT NULL) THEN
	UPDATE `customer_management_fee_tb` a SET `ecs_paid` = v_Status,submitted_ecs = v_Submitted,
        ecs_pay_date = v_DueDate WHERE a.`reg_id` = p_RegId 
		AND DATE_FORMAT(a.`ecs_pay_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d');
	END IF;
	
	IF (v_PerfFeeRow IS NOT NULL) THEN
	UPDATE `customer_performance_fee_tb` a SET `ecs_paid` = v_Status,submitted_ecs = v_Submitted,
        ecs_pay_date = v_DueDate  WHERE a.`reg_id` = p_RegId 
		AND DATE_FORMAT(a.`ecs_pay_date`,'%Y-%m-%d') = DATE_FORMAT(p_Duedate ,'%Y-%m-%d');
	END IF;
END$$

DELIMITER ;