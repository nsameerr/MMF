DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `renametable_and_clear5day_older_data_frombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `renametable_and_clear5day_older_data_frombo_proc`
	(
		IN P_RENAME_QUERY_1 VARCHAR(100),
		IN P_RENAME_QUERY_2 VARCHAR(100),
		IN P_DROP_QUERY VARCHAR(100),
		IN P_MAX_SHEDULE_DATE VARCHAR(30)
	)

	/*	CREATED BY	: 07662
	|	CREATED ON	: 19-06-2014
	|	PURPOSE		: DELETE % DAY OLDER  DATA FROM SCHEDULE JOB TABLES IN MMF DB
	|	EXECUTE CALL	: CALL renametable_and_clear5day_older_data_frombo_proc();
	*/

	BEGIN
		IF(P_RENAME_QUERY_1 != '') THEN 
			SET @query := P_RENAME_QUERY_1;
			PREPARE STMT FROM @query;
			EXECUTE STMT;
			DEALLOCATE PREPARE STMT;
                END IF;
		
		CREATE TABLE IF NOT EXISTS `mmf_daily_txn_summary_tb` (
			`id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
			`trndate` DATETIME NOT NULL,
			`tradecode` VARCHAR(16) NOT NULL,
			`orderno` VARCHAR(64) NOT NULL,
			`product` VARCHAR(32) NOT NULL,
                        `venue_script_code` VARCHAR(50) NOT NULL,  
			`security` VARCHAR(32) NOT NULL,
			`instrument` VARCHAR(32) NOT NULL,
			`contract` VARCHAR(32) NOT NULL,
			`buysell` VARCHAR(1) NOT NULL,
			`quantity` NUMERIC(15,2) NOT NULL,
			`price` NUMERIC(15,2) NOT NULL,
			`units` NUMERIC(15,2) NOT NULL,
			`volume` NUMERIC(15,2) NOT NULL,
			`brokerage` NUMERIC(15,2) NOT NULL,
			`othercharges` NUMERIC(15,2) NOT NULL,
			`channel` VARCHAR(32) NOT NULL,
			`euser` VARCHAR(16) NOT NULL,
			`lastupdatedon` DATETIME NOT NULL,
			PRIMARY KEY (`id`)
		);

		IF(P_RENAME_QUERY_2 != '') THEN
			SET @query := P_RENAME_QUERY_2;
			PREPARE STMT FROM @query;
			EXECUTE STMT;
			DEALLOCATE PREPARE STMT;
		END IF;
               
		
		CREATE TABLE IF NOT EXISTS `mmf_daily_client_balance_tb` (
			`id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
			`trndate` DATETIME,
			`tradecode` VARCHAR(16),
			`ledgerbalanec` DECIMAL(15,2),
			`euser` VARCHAR(16),
			`lastupdatedon` DATETIME,
			PRIMARY KEY (`id`)
		);

		-- delete 5 day older tables
		DELETE FROM `mmfdb`.`job_schedule_tb` WHERE DATE_FORMAT(`scheduledate`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_MAX_SHEDULE_DATE,'%Y-%m-%d'),INTERVAL 5 DAY);
	
		IF(P_DROP_QUERY != '') THEN
			SET @delStmt := P_DROP_QUERY;
			PREPARE STMT FROM @delStmt;
			EXECUTE STMT;
			DEALLOCATE PREPARE STMT;
                END IF;
			

	END$$

DELIMITER ;
