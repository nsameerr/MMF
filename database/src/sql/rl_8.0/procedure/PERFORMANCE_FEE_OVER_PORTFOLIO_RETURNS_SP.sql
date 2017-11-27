DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `performance_fee_over_portfolio_returns_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `performance_fee_over_portfolio_returns_sp`( 
        IN p_relationID	INTEGER,
        IN p_currentDate DATE
	)
BEGIN
DECLARE p_threshold DECIMAL(4,2);
DECLARE	p_dailyRate DECIMAL(13,12); 
DECLARE p_startDate DATE;
DECLARE p_investorPortfolioID INTEGER;
DECLARE p_expectedReturn DECIMAL(12,2);
DECLARE p_highWaterMark DECIMAL(12,2);
DECLARE p_billableAmount DECIMAL(12,2);
DECLARE p_currentFees DECIMAL(12,2);
DECLARE p_marketValue DECIMAL(12,2);
DECLARE p_date DATE;
DECLARE p_billChargedSofar DECIMAL(12,2);
DECLARE p_billDate DATE;
DECLARE v_Tax DECIMAL(5,2);
DECLARE v_taxCalculated DECIMAL(15,7);
DECLARE v_quarter BOOLEAN;
DECLARE p_investorID  INTEGER;
SET v_Tax = 0.15;
SELECT customer_id INTO p_investorID FROM customer_advisor_mapping_tb t WHERE t.relation_id = p_relationID;
SET p_investorPortfolioID = (SELECT customer_portfolio_id FROM customer_portfolio_tb WHERE relation_id = p_relationID);
				
                                    
SELECT perf_fee_threshold/100 INTO p_threshold FROM customer_advisor_mapping_tb WHERE relation_id = p_relationID;
SET p_dailyRate = POWER(1+p_threshold, 1/365.25);
SELECT DATE_FORMAT(contract_start,'%Y-%m-%d') INTO p_startDate FROM customer_advisor_mapping_tb WHERE relation_id = p_relationID;
SET p_date = p_startDate;
WHILE DATE_FORMAT(p_date,'%Y-%m-%d') < DATE_FORMAT(p_currentDate,'%Y-%m-%d') DO
SET p_marketValue = IFNULL(
  (SELECT 
    market_value_after_cash_flow 
  FROM
    customer_portfolio_performance_tb 
  WHERE customer_portfolio_id = p_investorPortfolioID 
    AND DATE_FORMAT(DATETIME,'%Y-%m-%d') = p_date),
  IFNULL(
    (SELECT 
      market_value 
    FROM
      performance_fee_over_portfolio_tb 
    WHERE investor_portfolio_id = p_investorPortfolioID 
    ORDER BY c_date DESC 
    LIMIT 1),
    0
  )
) ;
	IF NOT EXISTS (SELECT c_date FROM performance_fee_over_portfolio_tb WHERE investor_portfolio_id = p_investorPortfolioID AND DATE_FORMAT(c_date,'%Y-%m-%d')  = p_startDate) THEN
		INSERT INTO `performance_fee_over_portfolio_tb`(`investor_id`, `investor_portfolio_id`, `c_date`, `expected_return`, `high_water_mark`, `market_value`, `billable_amount`, `fees`)
		SELECT p_investorID AS investor_id,
		p_investorPortfolioID AS investor_portfolio_id,
		p_startDate AS c_date,
		p_marketValue AS expected_return,
		p_marketValue AS high_water_mark,
		p_marketValue AS market_value,
		0 AS billable_amount, 0 AS fees;
		
	
    ELSEIF NOT EXISTS (SELECT c_date FROM performance_fee_over_portfolio_tb WHERE investor_portfolio_id = p_investorPortfolioID AND DATE_FORMAT(c_date,'%Y-%m-%d')  = DATE_FORMAT(p_date,'%Y-%m-%d')) THEN
    
                            
		SET p_expectedReturn = (SELECT expected_return*POWER(p_dailyRate, DATEDIFF(p_date, c_date)) 
							FROM performance_fee_over_portfolio_tb 
                            WHERE investor_portfolio_id = p_investorPortfolioID
                            ORDER BY c_date DESC LIMIT 1) 
							+ IFNULL((SELECT cash_flow 
							FROM customer_portfolio_performance_tb 
							WHERE customer_portfolio_id = p_investorPortfolioID 
							AND DATE_FORMAT(DATETIME,'%Y-%m-%d') = p_date),0);
		IF (SELECT fees FROM performance_fee_over_portfolio_tb WHERE investor_portfolio_id = p_investorPortfolioID
                                ORDER BY c_date DESC LIMIT 1) > 0 THEN
			SET p_highWaterMark = (SELECT market_value 
								FROM  performance_fee_over_portfolio_tb 
								WHERE investor_portfolio_id = p_investorPortfolioID
                                ORDER BY c_date DESC LIMIT 1)
                                + IFNULL((SELECT cash_flow 
								FROM customer_portfolio_performance_tb 
								WHERE customer_portfolio_id = p_investorPortfolioID 
								AND DATE_FORMAT(DATETIME,'%Y-%m-%d') = p_date),0);
		ELSE 
			SET p_highWaterMark = (SELECT high_water_mark 
								FROM  performance_fee_over_portfolio_tb 
								WHERE investor_portfolio_id = p_investorPortfolioID
                                ORDER BY c_date DESC LIMIT 1)
                                + IFNULL((SELECT cash_flow 
								FROM customer_portfolio_performance_tb 
								WHERE customer_portfolio_id = p_investorPortfolioID 
								AND DATE_FORMAT(DATETIME,'%Y-%m-%d') = p_date),0);
		END IF;
        
        SET p_billDate = DATE_ADD(p_date, INTERVAL 1 DAY);
    
		IF DATE_FORMAT(p_billDate, '%m-%d') = '04-01' OR 
			DATE_FORMAT(p_billDate, '%m-%d') = '07-01' OR 
			DATE_FORMAT(p_billDate, '%m-%d') = '10-01' OR 
			DATE_FORMAT(p_billDate, '%m-%d') = '01-01' THEN
			IF p_marketValue > GREATEST(p_highWaterMark, p_expectedReturn) THEN
				SET p_billableAmount = p_marketValue - GREATEST(p_highWaterMark, p_expectedReturn);
			ELSE
				SET p_billableAmount = 0;
			END IF;
			
			SET v_quarter = TRUE;
		ELSE
			SET p_billableAmount = 0;
			SET v_quarter = FALSE;
		END IF;
		
        SET p_billChargedSoFar = (SELECT SUM(fees) FROM performance_fee_over_portfolio_tb 
										WHERE investor_portfolio_id = p_investorPortfolioID);
        
		SET p_currentFees = p_billableAmount * (SELECT perf_fee_percent FROM customer_advisor_mapping_tb 
												WHERE relation_id = p_relationID)/100; 
        
        IF p_currentFees > p_billChargedSoFar THEN
			SET p_currentFees = p_currentFees - p_billChargedSoFar;
			SET v_taxCalculated = (p_currentFees * v_Tax);
			SET p_currentFees = p_currentFees + v_taxCalculated;
		ELSE
			SET p_currentFees = 0;
		END IF;
    
		INSERT INTO `performance_fee_over_portfolio_tb`(`investor_id`, `investor_portfolio_id`, `c_date`, `expected_return`, `high_water_mark`, `market_value`, `billable_amount`, `fees`) 
		SELECT p_investorID AS investor_id,
		p_investorPortfolioID AS investor_portfolio_id,
		p_date AS c_date,
		p_expectedReturn AS expected_return,
		p_highWaterMark AS high_water_mark,
		p_marketValue AS market_value,
		p_billableAmount AS billable_amount, 
		p_currentFees AS fees; 
		
		IF(v_quarter) THEN
		INSERT  INTO customer_performance_fee_tb (relation_id,customer_id,advisor_id,fee_calculate_date,
				perf_fee,ecs_pay_date,ecs_paid,reg_id)
		SELECT p_relationID ,p_investorID,b.advisor_id,p_currentDate,p_currentFees,p_billDate ,FALSE,c.registration_id
		FROM customer_advisor_mapping_tb a, master_advisor_tb b, master_customer_tb c
				WHERE a.relation_id = p_RelationId AND b.advisor_id = a.advisor_id AND c.customer_id = a.customer_id
				AND b.`is_active_user` = TRUE AND c.`is_active_user` = TRUE;	
				
		UPDATE customer_advisor_mapping_tb a SET a.perf_fee_last_calculated = p_currentDate, a.perf_fee_amt = p_currentFees
		WHERE a.relation_id = p_relationID;
		
		END IF;    
		
    
    END IF;
    SET p_date = DATE_FORMAT(DATE_ADD(p_date, INTERVAL 1 DAY),'%Y-%m-%d');
    
END WHILE;
/*select p_threshold,p_dailyRate , p_startDate ,p_investorPortfolioID,p_expectedReturn,p_highWaterMark,p_billableAmount,p_currentFees, p_marketValue,p_date,p_billChargedSofar ,
p_billDate ,v_Tax ,v_taxCalculated, v_quarter,p_investorID;*/
	SELECT p_currentFees,p_RelationId,b.advisor_id advisorId,CONCAT(b.first_name,' ',b.`middle_name`,' ',b.`last_name`)
			advisorFirstName, b.email advisorEmail,c.customer_id customerId, c.first_name customerFirstName,
			c.email customerEmail,c.registration_id
		FROM customer_advisor_mapping_tb a, master_advisor_tb b, master_customer_tb c
		WHERE a.relation_id = p_RelationId AND b.advisor_id = a.advisor_id 
		AND c.customer_id = a.customer_id;
END$$

DELIMITER ;