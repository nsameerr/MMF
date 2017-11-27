ALTER TABLE `customer_portfolio_tb` ADD COLUMN is_first_day_perfmance_calc BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE `customer_portfolio_tb`     ADD COLUMN `portfolio_modified` BOOLEAN NULL AFTER `cash_amount`;

ALTER TABLE `customer_portfolio_tb`     CHANGE `portfolio_modified` `portfolio_modified` TINYINT(1) DEFAULT '0' NULL ;

ALTER TABLE `customer_portfolio_securities_tb`     ADD COLUMN `status` BOOLEAN NULL AFTER `security_code`;

ALTER TABLE `portfolio_tb`     CHANGE `portfolio_1_year returns` `portfolio_1_year_returns` DECIMAL(5,2) NULL ;

ALTER TABLE `benchmark_performance_tb`     CHANGE `close` `close` DECIMAL(8,2) NULL ;

ALTER TABLE `customer_portfolio_tb`     CHANGE `portfolio_1_year returns` `portfolio_1_year_returns` FLOAT(5,2) NULL ;

ALTER TABLE `portfolio_securities_tb` ADD COLUMN instrument_type VARCHAR(50);

ALTER TABLE `portfolio_securities_tb` ADD COLUMN expiration_date DATETIME;

ALTER TABLE `portfolio_securities_tb` ADD COLUMN strike_price DECIMAL(12,2);

ALTER TABLE `customer_portfolio_securities_tb` ADD COLUMN instrument_type VARCHAR(50);

ALTER TABLE `customer_portfolio_securities_tb` ADD COLUMN expiration_date DATETIME;

ALTER TABLE `customer_portfolio_securities_tb` ADD COLUMN strike_price DECIMAL(12,2);

ALTER TABLE `master_portfolio_type_tb`     ADD COLUMN `mutual_funds` INT(11) DEFAULT '0' NULL AFTER `commodities`,
     ADD COLUMN `range_min_mutual_funds` INT(11) DEFAULT '0' NULL AFTER `range_min_fo`,
     ADD COLUMN `range_max_mutual_funds` INT(11) DEFAULT '0' NULL AFTER `range_max_international`;
	 
ALTER TABLE `customer_advisor_mapping_tb`
     ADD COLUMN `mgmt_fee_last_calculated` DATETIME NULL AFTER `review_freq`,
     ADD COLUMN `perf_fee_last_calculated` DATETIME NULL AFTER `mgmt_fee_last_calculated`,
     ADD COLUMN `mgmt_fee_amt` DECIMAL(12,2) NULL AFTER `perf_fee_last_calculated`,
     ADD COLUMN `perf_fee_amt` DECIMAL(12,2) NULL AFTER `mgmt_fee_amt`;

ALTER TABLE `portfolio_tb` CHANGE `no_of_customers_assigned` `no_of_customers_assigned` INT DEFAULT 0;

ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb`  ADD COLUMN `rate_advisor_date` DATETIME NULL AFTER `review_freq`;

ALTER TABLE `customer_portfolio_tb` DROP COLUMN `oms_password`;

ALTER TABLE `master_advisor_tb`     CHANGE `password` `password` VARCHAR(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ,
     CHANGE `primary_qualification_degree` `primary_qualification_degree` INT(11) NULL ,
     CHANGE `secondary_qualification_degree` `secondary_qualification_degree` INT(11) NULL ,
     CHANGE `tertiary_qualification_degree` `tertiary_qualification_degree` INT(11) NULL ;

ALTER TABLE `customer_advisor_mapping_tb` ADD `rate_advisor_last_date` DATETIME AFTER  `review_freq`;

ALTER TABLE `customer_advisor_mapping_tb` ADD `rate_advisor_flag` BOOLEAN DEFAULT FALSE;

ALTER TABLE `master_applicant_tb` ADD CONSTRAINT master_applicant_tb_uk_1 UNIQUE (`registration_id`);

ALTER TABLE `customer_portfolio_tb` ADD CONSTRAINT customer_portfolio_tb_uk_2 UNIQUE (`customer_id`);

ALTER TABLE `customer_portfolio_tb` CHANGE `advisor_portfolio_start_value` `advisor_portfolio_start_value` DECIMAL(12,2);

ALTER TABLE `customer_portfolio_tb` CHANGE `benchmark_start_value` `benchmark_start_value` DECIMAL(12,2);
