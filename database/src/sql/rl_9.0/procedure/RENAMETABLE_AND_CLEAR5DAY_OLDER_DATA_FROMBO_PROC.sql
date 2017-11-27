DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `renametable_and_clear5day_older_data_frombo_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `renametable_and_clear5day_older_data_frombo_proc`(
		IN P_RENAME_QUERY_1 VARCHAR(100),
		IN P_RENAME_QUERY_2 VARCHAR(100),
		IN P_RENAME_QUERY_3 VARCHAR(100),
		IN P_DROP_QUERY VARCHAR(200),
		IN P_MAX_SHEDULE_DATE VARCHAR(30)
	)
BEGIN
		IF(LENGTH(P_RENAME_QUERY_1) > 0) THEN 
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
			`quantity` NUMERIC(15,4) NOT NULL,
			`price` NUMERIC(15,4) NOT NULL,
			`units` NUMERIC(15,4) NOT NULL,
			`volume` NUMERIC(15,2) NOT NULL,
			`brokerage` NUMERIC(15,2) NOT NULL,
			`othercharges` NUMERIC(15,2) NOT NULL,
			`channel` VARCHAR(32) NOT NULL,
			`euser` VARCHAR(16) NOT NULL,
			`lastupdatedon` DATETIME NOT NULL,
			PRIMARY KEY (`id`)
		);

		IF(LENGTH(P_RENAME_QUERY_2) > 0 ) THEN
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
		
		IF(LENGTH(P_RENAME_QUERY_3) > 0) THEN 
			SET @query := P_RENAME_QUERY_3;
			PREPARE STMT FROM @query;
			EXECUTE STMT;
			DEALLOCATE PREPARE STMT;
		END IF;
		
		CREATE TABLE IF NOT EXISTS `mmf_ret_portfolio_splitup` (
			`Id` INT NOT NULL AUTO_INCREMENT,
			`Clientid` INT ,
			`Isin` VARCHAR(20) ,
			`DpHolding` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`NsePool` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`BsePool` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`NseUnAc` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`BseUnAc` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`NsePend` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`BsePend` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`NseFinalQty` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`BseFinalQty` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`AvgRate` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`NseSymbol` VARCHAR(20) ,
			`NseSeries` VARCHAR(4) ,
			`BseSymbol` VARCHAR(20) ,
			`BseSeries` VARCHAR(4) ,
			`Bsecode` VARCHAR(20) ,
			`TradeCode` VARCHAR(20) ,
			`ItClient` VARCHAR(1) ,
			`Location` VARCHAR(20) ,
			`Finance_stock` INT NOT NULL DEFAULT 0,
			`Finance_AvgCost` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`PledgeQty` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`Mtf_DP_Qty` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`Weighted_AverageRate` NUMERIC(15, 4) NOT NULL DEFAULT 0,
			`Finance_AvgRate` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`Multiplier` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`McxPool` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`McxUnAc` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`McxPend` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`McxFinalQty` NUMERIC(15, 3) NOT NULL DEFAULT 0,
			`McxSymbol` VARCHAR(20) ,
			`McxSeries` VARCHAR(4) ,
			`IssueDate` VARCHAR(16) ,
			`ExpiryDate` DATETIME ,
			`NseToken` INT NOT NULL DEFAULT 0,
			`processedQty` DECIMAL(18,6) DEFAULT 0,
			PRIMARY KEY (`Id`)
		) ;
		
		DELETE FROM `mmfdb`.`job_schedule_tb` WHERE DATE_FORMAT(`scheduledate`,'%Y-%m-%d') = DATE_SUB(DATE_FORMAT(P_MAX_SHEDULE_DATE,'%Y-%m-%d'),INTERVAL 5 DAY);
	
		IF(LENGTH(P_DROP_QUERY) > 0) THEN
			SET @delStmt :=  CONCAT('DROP TABLE ',P_DROP_QUERY);
			PREPARE STMT FROM @delStmt;
			EXECUTE STMT;
			DEALLOCATE PREPARE STMT;
        END IF;
			

	END$$

DELIMITER ;