
ALTER TABLE `mmfdb`.`temp_inv`   
    ADD COLUMN `kitNumber` INT(10) NULL;  

ALTER TABLE `mmfdb`.`master_portfolio_type_tb`   
    ADD COLUMN `balanced` INT(11) NULL,
    ADD COLUMN `range_min_balanced` INT(11) NULL,
    ADD COLUMN `range_max_balanced` INT(11) NULL;

UPDATE master_portfolio_type_tb 
    SET balanced = `mutual_funds`,
    range_min_balanced = `range_min_mutual_funds`,
    range_max_balanced = `range_max_mutual_funds`;

ALTER TABLE master_customer_tb
    ADD COLUMN ecs_send_or_resend BOOLEAN DEFAULT FALSE NOT NULL;
	
ALTER TABLE adminuser_tb
	DROP COLUMN `notify_admin`, 
	DROP COLUMN `admin_viewed`, 
	DROP COLUMN `notification_status`, 
	DROP COLUMN `notification_date`;	

ALTER TABLE `add_redeem_log_tb`   
  CHANGE `addfund` `oms_id` VARCHAR(20) NOT NULL,
  CHANGE `for_amount` `assetclass` SMALLINT(11) NOT NULL,
  CHANGE `date_of_entry` `security_code` VARCHAR(50) NOT NULL,
  CHANGE `balance_before` `datetime` DATETIME NOT NULL,
  CHANGE `cash_before` `venue` VARCHAR(10) NOT NULL,
  CHANGE `portfolio_value_before` `price` DECIMAL(7,2) NOT NULL,
  ADD COLUMN `units` DECIMAL(11,2) NOT NULL AFTER `price`,
  ADD COLUMN `volume` DECIMAL(11,2) NOT NULL AFTER `units`;

ALTER TABLE `temp_registration_tb`   
  ADD COLUMN `initLogin` INT(1) DEFAULT 1  NOT NULL;

ALTER TABLE `master_applicant_tb`   
  CHANGE `proof_of_address` `proof_of_address` VARCHAR(100) NULL,
  CHANGE `address2_proof` `address2_proof` VARCHAR(100) NULL;

ALTER TABLE `temp_inv`
  CHANGE `pproof` `pproof` VARCHAR(100) NULL,
  CHANGE `baddress` `baddress` VARCHAR(200) CHARSET latin1 COLLATE latin1_swedish_ci NULL,
  CHANGE `minorAddress` `minorAddress` VARCHAR(100) CHARSET latin1 COLLATE latin1_swedish_ci NULL;

ALTER TABLE `customer_portfolio_tb`   
  CHANGE `cash_amount` `cash_amount` FLOAT(13,2) NULL,
  ADD COLUMN `buyingPower` FLOAT(11,2) NULL;

ALTER TABLE `temp_adv`   
  CHANGE `bank_addrs` `bank_addrs` VARCHAR(200) CHARSET utf8 COLLATE utf8_general_ci NULL;


  

