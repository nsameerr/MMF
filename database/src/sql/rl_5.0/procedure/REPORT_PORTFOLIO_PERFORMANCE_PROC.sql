DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `report_portfolio_performance_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `report_portfolio_performance_proc`

	( 
	  IN P_CLIENT_ID 	BIGINT,
	  IN P_START_DATE	VARCHAR(30),
	  IN P_END_DATE		VARCHAR(30),
	  IN P_NO_OF_BACK_DAYS 	INT
	)
	
	/*	CREATED BY	: 07662
	|	CREATED ON	: 18-06-2014
	|	PURPOSE		: PORTFOLIO RETURNS CALCULATION FOR CLIENT PORTFOLIO REPORT
	|	EXECUTE CALL	: CALL report_portfolio_performance_proc(140513020333,'2014-06-08','2014-06-13',30);
	*/
	
	BEGIN
		DECLARE v_cursor_finished INT DEFAULT FALSE;
		DECLARE v_sub_return DECIMAL(7,2) DEFAULT 0;
		DECLARE v_TWR DECIMAL(7,2) DEFAULT 1;
		DECLARE client_TWR DECIMAL(7,2) DEFAULT 1;
		
		
		DECLARE cursor_client_portfolio_return CURSOR FOR 
		
			SELECT (1 + IFNULL(T1.sub_period_return,0)) 
				FROM `customer_portfolio_performance_tb` T1
				INNER JOIN `customer_portfolio_tb` T2 ON (T2.`customer_portfolio_id` = T1.`customer_portfolio_id`)
				INNER JOIN  `master_customer_tb` T3 ON (T3.`customer_id` = T2.`customer_id`)
				WHERE T3.`registration_id` = P_CLIENT_ID  
				AND T1.datetime BETWEEN DATE_SUB(DATE_FORMAT(P_END_DATE,'%Y-%m-%d'), INTERVAL P_NO_OF_BACK_DAYS DAY) 
				AND  DATE_ADD(DATE_FORMAT(P_END_DATE,'%Y-%m-%d'),INTERVAL 1 DAY); 

		DECLARE cursor_master_portfolio_return CURSOR FOR 
		
			SELECT (1 + IFNULL(T1.sub_period_return,0)) 
				FROM `portfolio_performance_tb` T1
				INNER JOIN `customer_portfolio_tb` T2 ON (T2.`portfolio_id` = T1.`portfolio_id`)
				INNER JOIN  `master_customer_tb` T3 ON (T3.`customer_id` = T2.`customer_id`)
				WHERE T3.`registration_id` = P_CLIENT_ID  
				AND T1.datetime BETWEEN DATE_SUB(DATE_FORMAT(P_END_DATE,'%Y-%m-%d'), INTERVAL P_NO_OF_BACK_DAYS DAY) 
				AND  DATE_ADD(DATE_FORMAT(P_END_DATE,'%Y-%m-%d'),INTERVAL 1 DAY);		
		
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_cursor_finished = TRUE;
		
		OPEN cursor_client_portfolio_return;
		
		label_exit_loop: LOOP
		
			FETCH cursor_client_portfolio_return INTO v_sub_return;
			IF v_cursor_finished  THEN
			
				LEAVE label_exit_loop;
				
			END IF;
			
			SET client_TWR = client_TWR * v_sub_return;
		
		END LOOP;
		
		SET client_TWR = (client_TWR - 1) * 100;
		
		CLOSE cursor_client_portfolio_return;
		
		SET v_cursor_finished = TRUE;
		SET v_sub_return =0;
		SET v_TWR =1;
		
		OPEN cursor_master_portfolio_return;
		
		label_exit_loop: LOOP
		
			FETCH cursor_master_portfolio_return INTO v_sub_return;
			IF v_cursor_finished  THEN
			
				LEAVE label_exit_loop;
				
			END IF;
			
			SET v_TWR = v_TWR * v_sub_return;
		
		END LOOP;
		
		SET v_TWR = (v_TWR - 1) * 100;
		
		CLOSE cursor_master_portfolio_return;
		
		SELECT  client_TWR AS TWR_client_portfolio,v_TWR AS TWR_master_portfolio;
    
	END$$

DELIMITER ;

