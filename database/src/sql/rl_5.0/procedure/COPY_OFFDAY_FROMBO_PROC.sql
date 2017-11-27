
DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `copy_offday_frombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `copy_offday_frombo_proc`(IN p_schedule_date DATETIME)

	/*	CREATED BY	: 07662
	|	CREATED ON	: 19-06-2014
	|	PURPOSE		: TRADE OFF DAY DUMMY DATA TRANSFER FROM BACK OFFICE DB
	|	EXECUTE CALL	: CALL copy_offday_frombo_proc(NOW());
	*/
	BEGIN
	
	DECLARE STAGED_TXN INT;
	SELECT COUNT(*) REC_COUNT INTO STAGED_TXN FROM mmf_backoffice_db.job_schedule_tb WHERE JOB_TYPE='TXN_DUMMY' AND STATUS='STAGED';
	
	IF(STAGED_TXN > 0) THEN
	
		UPDATE mmf_backoffice_db.job_schedule_tb SET STATUS='INPROGRESS' WHERE job_type='TXN_DUMMY'
		AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.`job_schedule_tb`(`job_type`,`status`,`scheduledate`,`lastupdatedon`)
		SELECT `job_type`,`status`,`scheduledate`,`lastupdatedon` 
		FROM mmf_backoffice_db.`job_schedule_tb` T1
		WHERE T1.job_type='TXN_DUMMY' AND T1.scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmf_backoffice_db.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='TXN_DUMMY'
		AND  STATUS='INPROGRESS' AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmfdb.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='TXN_DUMMY'
		AND STATUS='INPROGRESS' AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
	END IF;

	END$$

DELIMITER ;