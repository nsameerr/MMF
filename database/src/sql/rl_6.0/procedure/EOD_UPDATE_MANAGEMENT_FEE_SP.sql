DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_update_management_fee_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_update_management_fee_sp`(
	IN p_RelationId INTEGER,
	IN p_CurrentDatetime DATETIME
    )
BEGIN
	DECLARE v_VariableFeeType BOOLEAN;
	DECLARE v_VariableFeeRate DECIMAL(5,2);
	DECLARE v_CustomerPortfolioValue DECIMAL(10,2);
	DECLARE v_MgmtFee DECIMAL(12,2);
	
	SELECT a.mgmt_fee_type_variable INTO v_VariableFeeType FROM customer_advisor_mapping_tb a WHERE a.relation_id = p_RelationId;
	IF v_VariableFeeType THEN 
		/* Variable type management fee | x per quarter of AUM payable quarter. */
		SELECT a.management_fee_variable INTO v_VariableFeeRate FROM customer_advisor_mapping_tb a WHERE a.relation_id = p_RelationId;
		SELECT b.current_value INTO v_CustomerPortfolioValue FROM customer_portfolio_tb b WHERE b.relation_id = p_RelationId;
		SET v_MgmtFee = v_CustomerPortfolioValue * v_VariableFeeRate / 100.0;
		UPDATE customer_advisor_mapping_tb 
			SET mgmt_fee_last_calculated = p_CurrentDatetime, mgmt_fee_amt = v_MgmtFee
			WHERE relation_id = p_RelationId;
	ELSE 
		/* Fixed type management fee */
		SELECT a.management_fee_fixed INTO v_MgmtFee FROM customer_advisor_mapping_tb a WHERE a.relation_id = p_RelationId;
		UPDATE customer_advisor_mapping_tb 
			SET mgmt_fee_last_calculated = p_CurrentDatetime, mgmt_fee_amt = v_MgmtFee
			WHERE relation_id = p_RelationId;
	END IF;
	
	/* Print result data */
	SELECT v_MgmtFee mgmtFee,
			b.advisor_id advisorId, b.first_name advisorFirstName, b.email advisorEmail,
			c.customer_id customerId, c.first_name customerFirstName, c.email customerEmail
		FROM customer_advisor_mapping_tb a, master_advisor_tb b, master_customer_tb c
		WHERE a.relation_id = p_RelationId AND b.advisor_id = a.advisor_id 
                AND c.customer_id = a.customer_id AND b.`is_active_user` = TRUE AND c.`is_active_user` = TRUE;
END$$

DELIMITER ;