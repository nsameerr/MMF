DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `delete_advisor_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `delete_advisor_proc`(
		IN P_User_ID 			INTEGER,
		IN P_Master_Applicant_RegId 	VARCHAR(50)
	)
BEGIN
	
		DECLARE v_portfolio_id 			INT;
		DECLARE v_customer_id 			INT;
		DECLARE v_reg_id 			VARCHAR(50);
		DECLARE v_cursor_finished INT DEFAULT FALSE;
				
		DECLARE cursor_delete_advisor_customer CURSOR FOR
		SELECT `customer_id` FROM `customer_advisor_mapping_tb` WHERE `advisor_id` = P_User_ID;
		
		DECLARE cursor_delete_advisor_portfolio CURSOR FOR
		SELECT `portfolio_id` FROM `portfolio_tb` WHERE `advisor_id` = P_User_ID;
		
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_cursor_finished = TRUE;
		
		OPEN cursor_delete_advisor_customer;
		
		label_exit_loop: LOOP
		
			FETCH cursor_delete_advisor_customer INTO v_customer_id;
			IF v_cursor_finished  THEN
			
				LEAVE label_exit_loop;
				
			END IF;
			
			SELECT `registration_id` INTO v_reg_id FROM `master_customer_tb` WHERE `customer_id` = v_customer_id;
			CALL delete_customer_proc(v_customer_id,v_reg_id,TRUE);
		
		END LOOP;
		
		CLOSE cursor_delete_advisor_customer;
		
				
		SET v_cursor_finished = FALSE;
		
		OPEN cursor_delete_advisor_portfolio;
		
		label_exit_loop: LOOP
		
			FETCH cursor_delete_advisor_portfolio INTO v_portfolio_id;
			IF v_cursor_finished  THEN
			
				LEAVE label_exit_loop;
				
			END IF;
			
			DELETE FROM `portfolio_securities_performance_tb` WHERE `portfolio_id` = v_portfolio_id;
			DELETE FROM `portfolio_details_performance_tb` WHERE `portfolio_id` = v_portfolio_id;
			DELETE FROM `portfolio_performance_tb` WHERE `portfolio_id` = v_portfolio_id;
			DELETE FROM `portfolio_securities_tb` WHERE `portfolio_id` = v_portfolio_id;
			DELETE FROM `portfolio_details_tb` WHERE `portfolio_id` = v_portfolio_id;
			DELETE FROM `portfolio_tb` WHERE `portfolio_id` = v_portfolio_id;
			
			
		END LOOP;
		
		CLOSE cursor_delete_advisor_portfolio;
		
		DELETE FROM `advisor_details_tb` WHERE `advisor_id` = P_User_ID;
		DELETE FROM `master_advisor_tb` WHERE `advisor_id` = P_User_ID;
		DELETE FROM `master_advisor_qualification_tb` WHERE `registration_id` = P_Master_Applicant_RegId;
		DELETE FROM `master_applicant_tb` WHERE `registration_id` = P_Master_Applicant_RegId;
		DELETE FROM `temp_adv` WHERE `registration_id` = P_Master_Applicant_RegId;		
	END$$

DELIMITER ;