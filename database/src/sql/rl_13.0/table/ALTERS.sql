ALTER TABLE `customer_advisor_mapping_tb`   
  ADD COLUMN `contract_terminate_status` BOOLEAN DEFAULT 0  NULL;

ALTER TABLE `customer_advisor_mapping_tb`   
  ADD COLUMN `actual_contract_end_date` DATETIME NULL;

ALTER TABLE `master_applicant_tb`   
  ADD COLUMN `linkedInSkipped` BOOLEAN DEFAULT 0 NULL;

ALTER TABLE `customer_portfolio_tb`   
  DROP INDEX `customer_portfolio_tb_uk_2`;
  
ALTER TABLE `customer_portfolio_tb`   
  ADD COLUMN `portfolio_status` VARCHAR(50) NULL ;