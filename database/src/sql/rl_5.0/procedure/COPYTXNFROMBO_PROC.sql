DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `copytxnfrombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `copytxnfrombo_proc`(IN p_schedule_date DATETIME)

BEGIN
	DECLARE STAGED_TXN INT;
	SELECT COUNT(*) REC_COUNT INTO STAGED_TXN FROM mmf_backoffice_db.job_schedule_tb WHERE JOB_TYPE='TXN' AND STATUS='STAGED';
	
	IF(STAGED_TXN > 0) THEN
	
		UPDATE mmf_backoffice_db.job_schedule_tb SET STATUS='INPROGRESS' WHERE job_type='TXN'
		AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');

		INSERT INTO mmfdb.`job_schedule_tb`(`job_type`,`status`,`scheduledate`,`lastupdatedon`)
		SELECT `job_type`,`status`,`scheduledate`,`lastupdatedon` 
		FROM mmf_backoffice_db.`job_schedule_tb` T1
		WHERE T1.job_type='TXN' AND T1.scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		INSERT INTO mmfdb.mmf_daily_txn_summary_tb(trndate,tradecode,orderno,product,`venue_script_code`,`security`,instrument,contract,buysell,quantity,price,units,volume,brokerage,othercharges,channel,euser,lastupdatedon) 
		SELECT T1.trndate,T1.tradecode,T1.orderno,T1.product,T1.`venue_script_code`,T1.security,T1.instrument,T1.contract,T1.buysell,
		T1.quantity,T1.price,T1.units,T1.volume,T1.brokerage,T1.othercharges,T1.channel,T1.euser,T1.lastupdatedon 
		FROM mmf_backoffice_db.mmf_daily_txn_summary_tb T1
		WHERE T1.`trndate` = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmf_backoffice_db.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='TXN'
		AND  STATUS='INPROGRESS' AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
		UPDATE mmfdb.job_schedule_tb SET STATUS='COMPLETED',lastupdatedon=NOW() WHERE job_type='TXN'
		AND  STATUS='INPROGRESS' AND scheduledate = DATE_FORMAT(p_schedule_date,'%Y-%m-%d');
		
	END IF;

END$$

DELIMITER ;