DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `copy_offday_frombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `copy_offday_frombo_proc`(IN p_schedule_date DATETIME)
BEGIN
	
	DECLARE STAGED_TXN INT;
	DECLARE STAGED_CASH INT;
	SELECT COUNT(*) REC_COUNT INTO STAGED_TXN FROM mmf_backoffice_db.`MMF_JobSchedule_Details` WHERE JobType='TXN_DUMMY' AND `Status`='STAGED';
	
	IF(STAGED_TXN > 0) THEN
	
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='INPROGRESS' WHERE JobType='TXN_DUMMY'
		AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.`job_schedule_tb`(`job_type`,`status`,`scheduledate`,`lastupdatedon`)
		SELECT `JobType`,`Status`,`ScheduledDate`,`LastUpdatedOn` 
		FROM mmf_backoffice_db.`MMF_JobSchedule_Details` T1
		WHERE T1.JobType='TXN_DUMMY' AND DATE_FORMAT(T1.ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='COMPLETED',LastUpdatedOn=NOW(),MMFTransferDate=NOW()  
		WHERE JobType='TXN_DUMMY' AND `Status`='INPROGRESS' AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmfdb.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='TXN_DUMMY'
		AND STATUS='INPROGRESS' AND DATE_FORMAT(scheduledate,'%Y-%m-%d')  = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
	END IF;
	
	SELECT COUNT(*) REC_COUNT INTO STAGED_CASH FROM mmf_backoffice_db.`MMF_JobSchedule_Details` WHERE JobType='CASH_DUMMY' AND `Status`='STAGED';
	
	IF(STAGED_CASH > 0) THEN
	
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='INPROGRESS' WHERE JobType='CASH_DUMMY'
		AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.`job_schedule_tb`(`job_type`,`status`,`scheduledate`,`lastupdatedon`)
		SELECT `JobType`,`Status`,`ScheduledDate`,`LastUpdatedOn` 
		FROM mmf_backoffice_db.`MMF_JobSchedule_Details` T1
		WHERE T1.JobType='CASH_DUMMY' AND DATE_FORMAT(T1.ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='COMPLETED',LastUpdatedOn=NOW(),MMFTransferDate=NOW()  
		WHERE JobType='CASH_DUMMY' AND `Status`='INPROGRESS' AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmfdb.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='CASH_DUMMY'
		AND STATUS='INPROGRESS' AND DATE_FORMAT(scheduledate,'%Y-%m-%d')  = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
	END IF;
	
	END$$

DELIMITER ;
