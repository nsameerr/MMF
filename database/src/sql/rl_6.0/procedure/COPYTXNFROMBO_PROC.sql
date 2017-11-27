DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `copytxnfrombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `copytxnfrombo_proc`(IN p_schedule_date DATETIME)

BEGIN
	DECLARE STAGED_TXN INT;
	SELECT COUNT(*) REC_COUNT INTO STAGED_TXN FROM mmf_backoffice_db.`MMF_JobSchedule_Details` WHERE JobType='TXN' AND `Status`='STAGED';
	
	IF(STAGED_TXN > 0) THEN
	
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='INPROGRESS' WHERE JobType='TXN'
		AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');

		INSERT INTO mmfdb.`job_schedule_tb`(`job_type`,`status`,`scheduledate`,`lastupdatedon`)
		SELECT `JobType`,`Status`,`ScheduledDate`,`LastUpdatedOn` 
		FROM mmf_backoffice_db.`MMF_JobSchedule_Details` T1
		WHERE T1.JobType='TXN' AND DATE_FORMAT(T1.ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.mmf_daily_txn_summary_tb(trndate,tradecode,orderno,product,`venue_script_code`,`security`,instrument,contract,buysell,quantity,price,units,volume,brokerage,othercharges,channel,euser,lastupdatedon) 
		SELECT T1.TranDate,T1.TradeCode,T1.OrderNo,T1.Product,T1.`venue_script_code`,T1.Security,T1.Instrument,T1.Contract,T1.BuySell,
		T1.Quantity,T1.Price,T1.Units,T1.Volume,T1.Brokerage,T1.OtherCharges,T1.Channel,T1.Euser,T1.lastUpdatedOn 
		FROM mmf_backoffice_db.`MMF_Daily_TransactionSummary` T1
		WHERE DATE_FORMAT(T1.`TranDate`,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmf_backoffice_db.`MMF_JobSchedule_Details` SET `Status`='COMPLETED',LastUpdatedOn=NOW(),MMFTransferDate=NOW()  
		WHERE JobType='TXN' AND `Status`='INPROGRESS' AND DATE_FORMAT(ScheduledDate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmfdb.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='TXN'
		AND  STATUS='INPROGRESS' AND DATE_FORMAT(scheduledate,'%Y-%m-%d') = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
	END IF;

END$$

DELIMITER ;