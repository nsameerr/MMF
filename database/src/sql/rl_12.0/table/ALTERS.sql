ALTER TABLE `master_applicant_tb` ADD `address_type` VARCHAR(100) NULL; 

ALTER TABLE `customer_portfolio_securities_tb`   
  ADD COLUMN `average_rate` FLOAT(10,4) DEFAULT 0.0000  NULL ;

ALTER TABLE `customer_transaction_execution_details_tb`   
  ADD  UNIQUE INDEX `customer_transaction_execution_details_tb_fk_3` (`order_id`),
  ADD COLUMN `processed` BOOLEAN DEFAULT 0  NOT NULL;
  
  ALTER TABLE `cash_txn_for_the_day`   
  ADD COLUMN `oms_userid` VARCHAR(50) NOT NULL ,
  ADD COLUMN `customer_transaction_exec_id` INT(11) NULL ,
  ADD COLUMN `buy_sell` VARCHAR(50) NULL,
  ADD CONSTRAINT `customer_transaction_exec_id` FOREIGN KEY (`customer_transaction_exec_id`) REFERENCES `customer_transaction_execution_details_tb`(`customer_transaction_exec_id`);

  ALTER TABLE `customer_portfolio_tb`   
  ADD COLUMN `blocked_count` FLOAT(15,4) DEFAULT 0.0000  NULL,
  ADD COLUMN `temp_blocked_count` FLOAT(15,4) DEFAULT 0.0000  NULL ;
  
ALTER TABLE `customer_portfolio_securities_tb`   
  ADD COLUMN `blocked_count` DECIMAL(10,4) DEFAULT 0.0000  NULL;

