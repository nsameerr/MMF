DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `copycashfrombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `copycashfrombo_proc`(IN p_schedule_date DATETIME)
BEGIN
	DECLARE STAGED_TXN INT;
	SELECT COUNT(*) REC_COUNT INTO STAGED_TXN FROM mmf_backoffice_db.`MMF_JobSchedule_Details` WHERE JobType='CASH' AND `Status`='STAGED';
	
	IF(STAGED_TXN > 0) THEN
	
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='INPROGRESS' WHERE JobType='CASH'
		AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.`job_schedule_tb`(`job_type`,`status`,`scheduledate`,`lastupdatedon`)
		SELECT `JobType`,`Status`,`ScheduledDate`,`LastUpdatedOn` 
		FROM mmf_backoffice_db.`MMF_JobSchedule_Details` T1
		WHERE T1.JobType='CASH' AND DATE_FORMAT(T1.ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.mmf_daily_client_balance_tb(trndate,tradecode,ledgerbalanec,euser,lastupdatedon) 
		SELECT T1.Trandate,T1.TradeCode,T1.LedgerBalance,T1.Euser,T1.LastUpdatedOn 
		FROM mmf_backoffice_db.`MMF_Daily_ClientBalance` T1 WHERE DATE_FORMAT(T1.`Trandate`,'%Y-%m-%d')= DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='COMPLETED',MMFTransferDate=NOW(),LastUpdatedOn=NOW() 
		WHERE JobType='CASH' AND `Status`='INPROGRESS' AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmfdb.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='CASH'
		AND STATUS='INPROGRESS' AND DATE_FORMAT(scheduledate,'%Y-%m-%d')  = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
	END IF;
		
END$$

DELIMITER ;
