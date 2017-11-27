DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `eod_recmd_portfolio_performance_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `eod_recmd_portfolio_performance_sp`(
		IN P_PORTFOLIO_ID INT
	)
BEGIN
        DECLARE startValue90Days DECIMAL(12,2);
        DECLARE returns90Days DECIMAL(12,2);
        DECLARE startValue1Year DECIMAL(12,2);
        DECLARE returns1Year DECIMAL(12,2);
        DECLARE closeValue DECIMAL(12,2);
        DECLARE eodDate DATE;
        DECLARE v_MaxPF90DaysReturns DECIMAL(12,2);
        DECLARE v_MaxPF1YearReturns DECIMAL(12,2);
        DECLARE v_AdvisorId INTEGER;
        DECLARE startValue5Days DECIMAL(12,2);
        DECLARE startValue6Month DECIMAL(12,2);
        DECLARE startValue1Month DECIMAL(12,2);
        DECLARE startValue5Year DECIMAL(12,2);
        DECLARE startValue10Year DECIMAL(12,2);
        DECLARE startValueYtd DECIMAL(12,2);
        DECLARE startValueSinceInception DECIMAL(12,2);   
        DECLARE v_return6Month DECIMAL(12,2);	 
        DECLARE v_return5Day DECIMAL(12,2);
        DECLARE v_return1Month DECIMAL(12,2);
        DECLARE v_return5Year DECIMAL(12,2);
        DECLARE v_return10Year DECIMAL(12,2);
        DECLARE v_returnYearToDate DECIMAL(12,2);
        DECLARE v_returnSinceInception DECIMAL(12,2);

		SELECT MAX(DATE_FORMAT(scheduledate,'%Y-%m-%d')) FROM `job_schedule_tb` INTO eodDate;
		
		SELECT advisor_id FROM portfolio_tb WHERE portfolio_id = P_PORTFOLIO_ID INTO v_AdvisorId;
						
		SELECT a.closing_value INTO closeValue FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = (
					SELECT MAX(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM portfolio_performance_tb b WHERE b.portfolio_id = P_PORTFOLIO_ID
						AND DATE_FORMAT(b.datetime,'%Y-%m-%d') BETWEEN DATE_SUB(eodDate, INTERVAL 90 DAY) AND eodDate 
						LIMIT 1 
					)  LIMIT 1;
					
					
		/* Calculate 90 day returns */
		
							
		SELECT a.closing_value INTO startValue90Days FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT DATE_SUB(eodDate, INTERVAL 90 DAY)),'%Y-%m-%d') LIMIT 1;		
					
				
		SET returns90Days = ((closeValue/startValue90Days)-1)*100;
		/*Calculate 1 year returns */
		
		
		SELECT a.closing_value INTO startValue1Year FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT DATE_SUB(eodDate,INTERVAL 1 YEAR)),'%Y-%m-%d') LIMIT 1;				
					
		SET returns1Year = ((closeValue/startValue1Year)-1)*100;
		
		/* Calculate 5 day return */
		SELECT a.closing_value INTO startValue5Days FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT DATE_SUB(eodDate,INTERVAL 5 DAY)),'%Y-%m-%d') LIMIT 1;
		
		SET v_return5Day = ((closeValue/startValue5Days)-1)*100;
		/* Calculate 6 month return */
		
		SELECT a.closing_value INTO startValue6Month FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID  AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT DATE_SUB(eodDate,INTERVAL 6 MONTH)),'%Y-%m-%d') LIMIT 1;
			
		SET v_return6Month = ((closeValue/startValue6Month)-1)*100;
		
		/* Calculate 1 month return */
		SELECT a.closing_value INTO startValue1Month FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT DATE_SUB(eodDate,INTERVAL 1 MONTH)),'%Y-%m-%d') LIMIT 1;
			
		SET v_return1Month = ((closeValue/startValue1Month)-1)*100;
		
		/* Calculate 5 year return */
		
		SELECT a.closing_value INTO startValue5Year FROM `portfolio_performance_tb` a 
		        WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT DATE_SUB(eodDate,INTERVAL 5 YEAR)),'%Y-%m-%d') LIMIT 1;
                  	
                 SET v_return5Year = ((closeValue/startValue5Year)-1)*100;
                 
                /* Calculate 10 year return */
                 
		SELECT a.closing_value INTO startValue10Year FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT DATE_SUB(eodDate,INTERVAL 10 YEAR)),'%Y-%m-%d') LIMIT 1;		
		   
		   SET v_return10Year = ((closeValue/startValue10Year)-1)*100;
		 /* Calculate YearToDate return */   
		 
		SELECT a.closing_value  INTO startValueYtd FROM `portfolio_performance_tb` a
		WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = DATE_FORMAT((SELECT MAKEDATE(YEAR(DATE_FORMAT(eodDate,'%Y-%m-%d')),1)),'%Y-%m-%d');
		SET v_returnYearToDate = ((closeValue/startValueYtd)-1)*100;
		

                SELECT a.closing_value INTO startValueSinceInception FROM `portfolio_performance_tb` a
			WHERE a.portfolio_id = P_PORTFOLIO_ID AND DATE_FORMAT(a.datetime,'%Y-%m-%d') = (
					SELECT MIN(DATE_FORMAT(b.datetime,'%Y-%m-%d')) FROM portfolio_performance_tb b WHERE b.portfolio_id = P_PORTFOLIO_ID);

                SET v_returnSinceInception = ((closeValue/startValueSinceInception)-1)*100;
					
		UPDATE portfolio_tb 
		SET portfolio_90_day_returns = returns90Days, 
		portfolio_1_year_returns = returns1Year,portfolio_5_day_return = v_return5Day,
		portfolio_1_month_return = v_return1Month,portfolio_5_year_return = v_return5Year,portfolio_10_year_return = v_return10Year,
		`portfolio_6_month_return`= v_return6Month,portflio_ytd_return = v_returnYearToDate,`since_inception` = v_returnSinceInception
			WHERE `portfolio_id` = P_PORTFOLIO_ID;
               /* SELECT returns90Days,returns1Year, closeValue,startValue1Year,startValue90Days,v_return5Day,v_return1Month,v_return6Month,v_return5Year,v_return10Year,v_returnYearToDate; */
	   /* Update advisor details table */		
		SELECT MAX(b.portfolio_90_day_returns),MAX(b.portfolio_1_year_returns)
		INTO v_MaxPF90DaysReturns, v_MaxPF1YearReturns
		FROM portfolio_tb b, advisor_details_tb c
		WHERE b.advisor_id = c.advisor_id AND b.status = TRUE AND c.advisor_id = v_AdvisorId;
	
	UPDATE advisor_details_tb c SET c.max_returns_90_days = v_MaxPF90DaysReturns, c.max_returns_1_year = v_MaxPF1YearReturns
		WHERE c.advisor_id = v_AdvisorId; 	
    
    	END$$

DELIMITER ;