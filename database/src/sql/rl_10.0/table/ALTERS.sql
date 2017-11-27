ALTER TABLE `customer_risk_profile_tb`   
  ADD COLUMN `portfolio_size` INT(11) DEFAULT 1 NULL,
  ADD COLUMN `initial_portfolio_size` INT(11) DEFAULT 1 NULL,
  ADD COLUMN `portfolio_type` INT(11) DEFAULT 1 NULL,
  ADD CONSTRAINT `customer_risk_profile_tb_fk_5` FOREIGN KEY (`portfolio_size`) REFERENCES `mmfdb`.`master_portfolio_size_tb`(`portfolio_size_id`),
  ADD CONSTRAINT `customer_risk_profile_tb_fk_6` FOREIGN KEY (`initial_portfolio_size`) REFERENCES `mmfdb`.`master_portfolio_size_tb`(`portfolio_size_id`),
  ADD CONSTRAINT `customer_risk_profile_tb_fk_7` FOREIGN KEY (`portfolio_type`) REFERENCES `mmfdb`.`master_portfolio_type_tb`(`id`),
  DROP FOREIGN KEY `customer_risk_profile_tb_fk_2`,
  DROP FOREIGN KEY `customer_risk_profile_tb_fk_4`;

ALTER TABLE `customer_risk_profile_tb` 
  ADD CONSTRAINT `customer_risk_profile_tb_fk_2` FOREIGN KEY (`portfolio_style`) REFERENCES `master_portfolio_style_tb`(`portfolio_style_id`),
  ADD CONSTRAINT `customer_risk_profile_tb_fk_4` FOREIGN KEY (`initial_portfolio_style`) REFERENCES `master_portfolio_style_tb`(`portfolio_style_id`); 

ALTER TABLE `master_asset_tb`   
  ADD COLUMN `asset_order` SMALLINT(6) NOT NULL; 

ALTER TABLE `portfolio_tb` 
  DROP FOREIGN KEY `FK_portfolio_style`,
  CHANGE `portfolio_style` `portfolio_type` INT(11) NULL;

ALTER TABLE `portfolio_tb` 
  ADD CONSTRAINT `FK_portfolio_style` FOREIGN KEY (`portfolio_type`) REFERENCES `master_portfolio_type_tb`(`id`);  
  
ALTER TABLE `master_portfolio_type_tb`   
  DROP COLUMN `investor_profile`, 
  DROP COLUMN `portfolio_type`, 
  CHANGE `min_score` `portfolio_size_id` INT(11) DEFAULT 1  NULL,
  CHANGE `max_score` `portfolio_style_id` INT(11) DEFAULT 1  NULL,
  CHANGE `balanced` `balanced` INT(11) DEFAULT 0  NULL,
  CHANGE `range_min_balanced` `range_min_balanced` INT(11) DEFAULT 0  NULL,
  CHANGE `range_max_balanced` `range_max_balanced` INT(11) DEFAULT 0  NULL,
  ADD COLUMN `debt_liquid` INT(11) DEFAULT 0,
  ADD COLUMN `range_min_debt_liquid` INT(11) DEFAULT 0,
  ADD COLUMN `range_max_debt_liquid` INT(11) DEFAULT 0;

DELETE FROM `master_portfolio_type_tb`;

ALTER TABLE `master_portfolio_type_tb`
  ADD CONSTRAINT `master_portfolio_type_tb_fk_1` FOREIGN KEY (`portfolio_size_id`) REFERENCES `master_portfolio_size_tb`(`portfolio_size_id`),
  ADD CONSTRAINT `master_portfolio_type_tb_fk_2` FOREIGN KEY (`portfolio_style_id`) REFERENCES `master_portfolio_style_tb`(`portfolio_style_id`);


ALTER TABLE temp_inv  
  ADD COLUMN nominee_proof VARCHAR(50) NULL,
  ADD COLUMN nominee_aadhar VARCHAR(20) NULL,
  ADD COLUMN minor_proof VARCHAR(50) NULL,
  ADD COLUMN minor_pan VARCHAR(20) NULL,
  ADD COLUMN minor_aadhar VARCHAR(20) NULL;

ALTER TABLE investor_nominee_details_tb
  ADD COLUMN nominee_proof VARCHAR(50) NULL,
  ADD COLUMN nominee_aadhar VARCHAR(20) NULL,
  ADD COLUMN minor_proof VARCHAR(50) NULL,
  ADD COLUMN minor_pan VARCHAR(20) NULL,
  ADD COLUMN minor_aadhar VARCHAR(20) NULL;

ALTER TABLE `customer_portfolio_securities_tb`   
  ADD COLUMN `lastBoughtPrice` FLOAT(10,2) DEFAULT 0.00  NULL;  

ALTER TABLE `customer_portfolio_tb`   
  ADD COLUMN `recomended_cash_bal` FLOAT(11,2) DEFAULT 0  NULL;

ALTER TABLE `master_applicant_tb`   
  CHANGE `usNational` `usNational` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usResident` `usResident` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usBorn` `usBorn` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usAddress` `usAddress` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usTelephone` `usTelephone` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usStandingInstruction` `usStandingInstruction` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usPoa` `usPoa` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usMailAddress` `usMailAddress` VARCHAR(5) DEFAULT '0' NULL;

ALTER TABLE `temp_inv`   
  CHANGE `usNational` `usNational` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usResident` `usResident` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usBorn` `usBorn` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usAddress` `usAddress` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usTelephone` `usTelephone` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usStandingInstruction` `usStandingInstruction` VARCHAR(5) DEFAULT '0' NULL,
  CHANGE `usPoa` `usPoa` VARCHAR(5)  DEFAULT '0'   NULL,
  CHANGE `usMailAddress` `usMailAddress` VARCHAR(5) DEFAULT '0' NULL;

ALTER TABLE `ecs_transmittal_sheet_tb`   
  ADD COLUMN `rejected` TINYINT(1) DEFAULT 0  NULL,
  ADD COLUMN `remark` VARCHAR(100) NULl;

ALTER TABLE `portfolio_tb`   
  ADD COLUMN `balance_cash` DOUBLE(11,2) DEFAULT 0  NULL;

ALTER TABLE `customer_portfolio_securities_tb`   
  CHANGE `exe_units` `exe_units` DECIMAL(10,4) DEFAULT 0.00  NULL,
  CHANGE `yesterDayUnitCount` `yesterDayUnitCount` DECIMAL(10,4) DEFAULT 0.00  NULL,
  CHANGE `current_price` `current_price` FLOAT(10,4) DEFAULT 0.0000  NULL,
  CHANGE `recommented_price` `recommented_price` FLOAT(10,4) NULL,
  CHANGE `lastBoughtPrice` `lastBoughtPrice` FLOAT(10,4) DEFAULT 0.0000  NULL;

ALTER TABLE `portfolio_securities_tb`   
  CHANGE `current_price` `current_price` DECIMAL(8,4) NULL,
  CHANGE `recommented_price` `recommented_price` FLOAT(10,4) NULL;

ALTER TABLE `recomended_customer_portfolio_securities_tb`   
  CHANGE `current_price` `current_price` FLOAT(10,4) DEFAULT 0.0000  NULL,
  CHANGE `recommented_price` `recommented_price` FLOAT(10,4) NULL;

ALTER TABLE `mmf_daily_txn_summary_tb`   
  CHANGE `quantity` `quantity` DECIMAL(15,4) NOT NULL,
  CHANGE `price` `price` DECIMAL(10,4) NOT NULL,
  CHANGE `units` `units` DECIMAL(10,4) NOT NULL;

ALTER TABLE `customer_transaction_order_details_tb`   
  CHANGE `security_price` `security_price` DECIMAL(10,4) NULL;

ALTER TABLE `customer_transaction_execution_details_tb`   
  CHANGE `security_price` `security_price` DECIMAL(10,4) NULL;

ALTER TABLE `master_applicant_tb`   
  CHANGE `ecs_mandate_send` `ecs_mandate_status` INT(11) DEFAULT 0  NULL;