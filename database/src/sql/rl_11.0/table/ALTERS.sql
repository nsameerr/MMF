ALTER TABLE `ecs_debt_payment_file_content_tb`   
  ADD COLUMN `debit_request_number` INT(12) NULL;

ALTER TABLE `index_nse_tb`   
  ADD COLUMN `isin` VARCHAR(20) NULL;

ALTER TABLE `index_bse_tb`   
  ADD COLUMN `isin` VARCHAR(20) NULL;

ALTER TABLE `midcap_bse_tb`   
  ADD COLUMN `isin` VARCHAR(20) NULL;
  
ALTER TABLE `customer_portfolio_tb`   
  ADD COLUMN `blocked_cash` FLOAT(15,4) DEFAULT 0.00  NULL,
  ADD COLUMN `temp_blocked_cash` FLOAT(15,4) DEFAULT 0.00  NULL;  
  
ALTER TABLE `mmf_daily_txn_summary_tb`   
  CHANGE `price` `price` DECIMAL(15,4) NOT NULL,
  CHANGE `units` `units` DECIMAL(15,4) NOT NULL,
  CHANGE `volume` `volume` DECIMAL(15,4) NOT NULL;
  
ALTER TABLE `customer_transaction_execution_details_tb`   
  CHANGE `security_units` `security_units` DECIMAL(15,4) DEFAULT 0.00  NOT NULL;
  
ALTER TABLE  `portfolio_tb`   
  ADD COLUMN `portfolio_5_day_return` DECIMAL(5,2)  DEFAULT 0.00  NULL ,
  ADD COLUMN `portfolio_1_month_return` DECIMAL(5,2) DEFAULT 0.00  NULL,
  ADD COLUMN `portfolio_5_year_return` DECIMAL(5,2) DEFAULT 0.00  NULL ,
  ADD COLUMN `portfolio_10_year_return` DECIMAL(5,2) DEFAULT 0.00  NULL,
  ADD COLUMN `portfolio_6_month_return` DECIMAL(5,2) DEFAULT 0.00  NULL,
  ADD COLUMN `portflio_ytd_return` DECIMAL(5,2) DEFAULT 0.00 NULL,
  ADD COLUMN `since_inception` DECIMAL(5,2) DEFAULT 0.00 NULL;

ALTER TABLE `temp_inv`   
  CHANGE `beneficiary` `beneficiary` VARCHAR(150) NULL;

ALTER TABLE `master_applicant_tb`   
  CHANGE `beneficiary_name` `beneficiary_name` VARCHAR(150) NULL;

ALTER TABLE `cash_flow_tb`   
  ADD COLUMN `processed` BOOLEAN DEFAULT 1 NULL;

