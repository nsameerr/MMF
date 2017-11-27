DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `client_twr_portfolio_returns_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `client_twr_portfolio_returns_sp`(  	
		IN P_CUST_PORTFOLIO_ID 		INT, 
		IN P_ASSET_ID 			INT, 
		IN P_CURRENT_DATE 		VARCHAR(30),
		IN P_NO_OF_BACK_DAYS		INT
	)
BEGIN
	
		DECLARE v_cursor_finished INT DEFAULT FALSE;
		DECLARE v_sub_return DECIMAL(7,2) DEFAULT 0;
		DECLARE v_TWR DECIMAL(7,2) DEFAULT 1;
		
		
		DECLARE cursor_client_portfolio_return CURSOR FOR 
			SELECT (1 + IFNULL(T1.sub_period_return,0)) FROM `customer_portfolio_details_performance_tb` T1
			WHERE T1.`customer_portfolio_id` = P_CUST_PORTFOLIO_ID AND T1.`asset_class_id` = P_ASSET_ID 
			AND T1.datetime BETWEEN DATE_SUB(DATE_FORMAT(P_CURRENT_DATE,'%Y-%m-%d'), INTERVAL P_NO_OF_BACK_DAYS DAY) 
			AND  DATE_ADD(DATE_FORMAT(P_CURRENT_DATE,'%Y-%m-%d'),INTERVAL 1 DAY); 
			
		DECLARE cursor_client_portfolio_return2 CURSOR FOR 
			SELECT (1 + IFNULL(T1.sub_period_return,0)) FROM `customer_portfolio_details_performance_tb` T1
			WHERE T1.`customer_portfolio_id` = P_CUST_PORTFOLIO_ID AND T1.`asset_class_id` = P_ASSET_ID ; 
			
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_cursor_finished = TRUE;
				
		IF(P_NO_OF_BACK_DAYS > 0) THEN
		
			OPEN cursor_client_portfolio_return;
			
			label_exit_loop: LOOP
			
				FETCH cursor_client_portfolio_return INTO v_sub_return;
				IF v_cursor_finished  THEN
				
					LEAVE label_exit_loop;
					
				END IF;
				
				SET v_TWR = v_TWR * v_sub_return;
			
			END LOOP;
			
			SET v_TWR = (v_TWR - 1) * 100;
			
			CLOSE cursor_client_portfolio_return;
			
			SELECT v_TWR AS TWR_Selected_Asset_Class,IFNULL(AVG(T1.`market_value`),0) FROM `customer_portfolio_details_performance_tb` T1
			WHERE T1.`customer_portfolio_id` = P_CUST_PORTFOLIO_ID AND T1.`asset_class_id` = P_ASSET_ID 
			AND T1.datetime BETWEEN DATE_SUB(DATE_FORMAT(P_CURRENT_DATE,'%Y-%m-%d'), INTERVAL P_NO_OF_BACK_DAYS DAY) 
			AND  DATE_ADD(DATE_FORMAT(P_CURRENT_DATE,'%Y-%m-%d'),INTERVAL 1 DAY); 
		
		ELSE
		
			OPEN cursor_client_portfolio_return2;
			
			label_exit_loop: LOOP
			
				FETCH cursor_client_portfolio_return2 INTO v_sub_return;
				IF v_cursor_finished  THEN
				
					LEAVE label_exit_loop;
					
				END IF;
				
				SET v_TWR = v_TWR * v_sub_return;
			
			END LOOP;
			
			SET v_TWR = (v_TWR - 1) * 100;
			
			CLOSE cursor_client_portfolio_return2;
			
			SELECT v_TWR AS TWR_Selected_Asset_Class,IFNULL(AVG(T1.`market_value`),0) FROM `customer_portfolio_details_performance_tb` T1
			WHERE T1.`customer_portfolio_id` = P_CUST_PORTFOLIO_ID AND T1.`asset_class_id` = P_ASSET_ID;
				
		END IF;
			
	END$$

DELIMITER ;