DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `copycashfrombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `copycashfrombo_proc`(IN p_schedule_date DATETIME)
BEGIN
	DECLARE STAGED_TXN INT;
	SELECT COUNT(*) REC_COUNT INTO STAGED_TXN FROM mmf_backoffice_db.job_schedule_tb WHERE JOB_TYPE='CASH' AND STATUS='STAGED';
	
	IF(STAGED_TXN > 0) THEN
	
		UPDATE mmf_backoffice_db.job_schedule_tb SET STATUS='INPROGRESS' WHERE job_type='CASH'
		AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.`job_schedule_tb`(`job_type`,`status`,`scheduledate`,`lastupdatedon`)
		SELECT `job_type`,`status`,`scheduledate`,`lastupdatedon` 
		FROM mmf_backoffice_db.`job_schedule_tb` T1
		WHERE T1.job_type='CASH' AND T1.scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.mmf_daily_client_balance_tb(trndate,tradecode,ledgerbalanec,euser,lastupdatedon) 
		SELECT T1.trndate,T1.tradecode,T1.ledgerbalanec,T1.euser,T1.lastupdatedon 
		FROM mmf_backoffice_db.mmf_daily_client_balance_tb T1 WHERE T1.`trndate` = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmf_backoffice_db.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='CASH'
		AND  STATUS='INPROGRESS' AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmfdb.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='CASH'
		AND STATUS='INPROGRESS' AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
	END IF;
		
END$$

DELIMITER ;
