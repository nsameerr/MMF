DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `user_networkview_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `user_networkview_sp`(
    IN USER_ID INTEGER,
    IN ADVISOR_USER BOOLEAN,
    IN currentDate DATETIME
    )
BEGIN
	DECLARE max90DayReturns DECIMAL(4,2);
	DECLARE max365DayReturns DECIMAL(4,2);
	DECLARE portfolioId INTEGER;
	
	IF(ADVISOR_USER = TRUE) THEN
		SELECT "YES";
	ELSE
		
		SELECT MAX(a.sub_period_return) INTO max90DayReturns
		FROM customer_portfolio_performance_tb a, customer_portfolio_tb b 
		WHERE b.customer_id = USER_ID AND b.portfolio_status ='ACTIVE' AND a.customer_portfolio_id = b.customer_portfolio_id
			AND a.datetime BETWEEN DATE_SUB(currentDate, INTERVAL 90 DAY) AND currentDate;
		SELECT MAX(a.sub_period_return) INTO max365DayReturns
		FROM customer_portfolio_performance_tb a, customer_portfolio_tb b 
		WHERE b.customer_id = USER_ID AND b.portfolio_status ='ACTIVE' AND a.customer_portfolio_id = b.customer_portfolio_id
			AND a.datetime BETWEEN DATE_SUB(currentDate, INTERVAL 1 YEAR) AND currentDate;
		SELECT max90DayReturns, max365DayReturns;
	END IF;
    END$$

DELIMITER ;