
ALTER TABLE `customer_portfolio_tb` ADD COLUMN no_rebalance_status BOOLEAN DEFAULT TRUE;

ALTER TABLE `portfolio_securities_tb` ADD COLUMN `venue_script_code` VARCHAR(100);

ALTER TABLE `portfolio_securities_tb` ADD COLUMN `venue_code` VARCHAR(100);

ALTER TABLE `portfolio_securities_tb` ADD CONSTRAINT portfolio_securities_tb_uk_1 UNIQUE (`venue_script_code`,`venue_code`,`portfolio_id`) ;

ALTER TABLE `portfolio_securities_tb` ADD COLUMN `security_code` VARCHAR(50) NULL;

ALTER TABLE `portfolio_securities_tb`     CHANGE `current_value` `current_value` DECIMAL(12,2) NULL ,     CHANGE `initial_value` `initial_value` DECIMAL(12,2) NULL ;

-- ALTER TABLE `portfolio_securities_tb` ADD CONSTRAINT portfolio_securities_tb_uk_1 UNIQUE (`venue_script_code`,`venue_code`,`portfolio_id`) ;

ALTER TABLE `customer_portfolio_tb` ADD COLUMN LastExecutionUpdate DATETIME;

ALTER TABLE `portfolio_securities_tb`     CHANGE `initial_price` `initial_price` DECIMAL(8,2) NULL ;

ALTER TABLE `customer_portfolio_securities_tb` ADD COLUMN `security_code` VARCHAR(50) NULL;

ALTER TABLE `customer_portfolio_tb`     ADD COLUMN `cash_amount` FLOAT NULL AFTER `no_rebalance_status`;

ALTER TABLE `portfolio_securities_audit_tb`     CHANGE `current_value` `current_value` DECIMAL(12,2) NULL ;

ALTER TABLE `portfolio_securities_audit_tb`     CHANGE `initial_units` `initial_units` INT(11) NULL ,     CHANGE `initial_value` `initial_value` DECIMAL(12,2) NULL ;

ALTER TABLE `portfolio_securities_audit_tb`     CHANGE `security_id` `security_id` VARCHAR(300) NULL ;

ALTER TABLE `portfolio_securities_audit_tb`     CHANGE `initial_price` `initial_price` DECIMAL(8,2) NULL ;

ALTER TABLE `portfolio_securities_tb`     ADD COLUMN `status` BOOLEAN NULL AFTER `security_code`;

ALTER TABLE `customer_portfolio_securities_tb` CHANGE COLUMN `initial_price` recommented_price FLOAT(10,2);

ALTER TABLE `customer_portfolio_securities_tb` CHANGE COLUMN `initial_units` exe_units INT DEFAULT 0;

ALTER TABLE customer_portfolio_tb ADD COLUMN oms_login_id VARCHAR(40) AFTER customer_id;

ALTER TABLE customer_portfolio_tb ADD COLUMN oms_password VARCHAR(40) AFTER oms_login_id;

ALTER TABLE `portfolio_securities_tb` CHANGE COLUMN `initial_units` `exe_units` INT;

ALTER TABLE `portfolio_securities_tb` CHANGE COLUMN `initial_price` recommented_price FLOAT(10,2);

ALTER TABLE customer_transaction_execution_details_tb CHANGE `security_price` `security_price` DECIMAL(7,2);

ALTER TABLE `customer_transaction_order_details_tb` CHANGE `security_price` `security_price` DECIMAL(7,2);

ALTER TABLE customer_transaction_execution_details_tb CHANGE securityid security_code VARCHAR(100);

ALTER TABLE `customer_transaction_order_details_tb` ADD COLUMN security_code VARCHAR(100) AFTER `securityid`;

ALTER TABLE `customer_transaction_execution_details_tb` CHANGE `trans_id` `trans_id` BIGINT NOT NULL;

ALTER TABLE `customer_transaction_execution_details_tb` CHANGE `order_id` `order_id` VARCHAR(100) NOT NULL;