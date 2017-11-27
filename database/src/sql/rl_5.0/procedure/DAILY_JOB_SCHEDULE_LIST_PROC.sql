DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `daily_job_schedule_list_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `daily_job_schedule_list_proc`()

	/*	CREATED BY	: 07662
	|	CREATED ON	: 19-06-2014
	|	PURPOSE		: LOAD ALL STAGED STATUS DATA FROM BACK OFFICE DB
	|	EXECUTE CALL	: CALL daily_job_schedule_list();
	*/
	
	BEGIN
		    
	    SELECT * FROM `mmf_backoffice_db`.`job_schedule_tb` T1 WHERE T1.`status`='STAGED' ORDER BY T1.`scheduledate` ASC;
	    
	END$$

DELIMITER ;