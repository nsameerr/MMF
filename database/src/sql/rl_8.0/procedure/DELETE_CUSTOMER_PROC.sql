DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `delete_customer_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `delete_customer_proc`(
		IN P_User_ID 			INTEGER,
		IN P_Master_Applicant_RegId	VARCHAR(50),
		IN P_FROM_ADVISOR_DELETE	BOOLEAN
	)
	BEGIN
	
		DECLARE v_customer_portfolio_id 	INT;
		DECLARE v_relationId 			INT;
		DECLARE v_TWR DECIMAL(7,2) DEFAULT 1;
		DECLARE v_cursor_finished INT DEFAULT FALSE;
		
		DECLARE cursor_customer_mapping_ids CURSOR FOR 
		
		SELECT `relation_id`  FROM `customer_advisor_mapping_tb` T1 WHERE T1.`customer_id` = P_User_ID;
		
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_cursor_finished = TRUE;
		
		
		
		OPEN cursor_customer_mapping_ids;
		
		label_exit_loop: LOOP
		
			FETCH cursor_customer_mapping_ids INTO v_relationId;
			IF v_cursor_finished  THEN
			
				LEAVE label_exit_loop;
				
			END IF;
				
							
				SELECT T1.`customer_portfolio_id` INTO v_customer_portfolio_id
				FROM customer_portfolio_tb T1 WHERE T1.`relation_id` = v_relationId;
		
				
				DELETE FROM `customer_portfolio_securities_performance_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				
				DELETE FROM `customer_portfolio_details_performance_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				DELETE FROM `customer_portfolio_performance_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				DELETE FROM `recomended_customer_portfolio_performance_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				DELETE FROM `customer_question_response_set_tb` WHERE `relation_id` = v_relationId;
				INSERT INTO `customer_risk_profile_audit_tb`(`risk_profile_id`,`relation_id`,`risk_score`,`exp_return`,`benchmark`,`invest_horizon`,
				`liquidity_reqd`,`income_reqd`,`portfolio_style`,`initial_portfolio_style`,`last_update_on`,`activity_type`)
				SELECT `risk_profile_id`,`relation_id`,`risk_score`,`exp_return`,`benchmark`,`invest_horizon`,
				`liquidity_reqd`,`income_reqd`,`portfolio_style`,`initial_portfolio_style`,CURRENT_DATE,'DELETE'
				FROM `customer_risk_profile_tb` WHERE `relation_id` = v_relationId;
				DELETE FROM `customer_risk_profile_tb` WHERE `relation_id` = v_relationId;
				DELETE FROM `add_redeem_log_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				DELETE FROM `customer_portfolio_securities_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				DELETE FROM `recomended_customer_portfolio_securities_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				DELETE FROM `customer_portfolio_details_tb` WHERE `customer_portfolio_id` = v_customer_portfolio_id;
				DELETE FROM `customer_daily_cash_balance_tb` WHERE `customer_id` = P_User_ID;
				DELETE FROM `performance_fee_over_portfolio_tb` WHERE `investor_id` = P_User_ID;
				DELETE FROM `customer_portfolio_log_tb` WHERE `customer_id` = P_User_ID;
				DELETE FROM `customer_portfolio_tb` WHERE `relation_id` = v_relationId;
				DELETE FROM `customer_transaction_order_details_tb` WHERE `customer_id` = P_User_ID;
				DELETE FROM `customer_transaction_execution_details_tb` WHERE `customer_id` = P_User_ID;
				DELETE FROM `customer_advisor_mapping_tb` WHERE `relation_id`= v_relationId;
			
				
			END LOOP;
		
		CLOSE cursor_customer_mapping_ids;
		
		IF (!P_FROM_ADVISOR_DELETE) THEN

			DELETE FROM `master_customer_tb` WHERE `customer_id` = P_User_ID;
			DELETE FROM `investor_nominee_details_tb` WHERE `registration_id` = P_Master_Applicant_RegId;
			DELETE FROM `mandate_form_tb` WHERE `registration_id` = P_Master_Applicant_RegId;
			DELETE FROM `master_applicant_tb` WHERE `registration_id` = P_Master_Applicant_RegId;		
			DELETE FROM `temp_inv` WHERE `registration_id` = P_Master_Applicant_RegId;	
		END IF;	
		
	END$$

DELIMITER ;