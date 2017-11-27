-- Altering table mmf_daily_clientbalance to modify lenght of LedgerBalance for fraction
ALTER TABLE mmf_backoffice_db.mmf_daily_clientbalance
 MODIFY COLUMN `Clientid` VARCHAR(15) NULL,
 MODIFY COLUMN LedgerBalance DECIMAL(10,2) NULL;

-- Altering table mmf_daily_transactionsummary to modify lenght of fields for fraction
ALTER TABLE mmf_backoffice_db.`mmf_daily_transactionsummary`
 MODIFY COLUMN `ClientId` VARCHAR(15),
 MODIFY COLUMN `Price` DECIMAL(10,2) NULL,
 MODIFY COLUMN `Volume` DECIMAL(10,2) NULL,
 MODIFY COLUMN `Brokerage` DECIMAL(10,2) NULL,
 MODIFY COLUMN `OtherCharges` DECIMAL(10,2) NULL;