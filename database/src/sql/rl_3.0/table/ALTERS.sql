
ALTER TABLE `mmfdb`.`master_applicant_tb`  ADD COLUMN `work_organization` VARCHAR(50) NULL AFTER `linkedin_expire_date`;

ALTER TABLE `mmfdb`.`master_applicant_tb`  ADD COLUMN `job_title` VARCHAR(50) NULL AFTER `work_organization`;

ALTER TABLE `customer_advisor_mapping_tb` ADD `cooment_freequency` INT;

ALTER TABLE customer_advisor_mapping_tb CHANGE COLUMN review_freq comment_freq SMALLINT;

ALTER TABLE customer_advisor_mapping_tb ADD review_freq INT;

ALTER TABLE `customer_risk_profile_tb` ADD CONSTRAINT customer_risk_profile_tb_uk_1 UNIQUE (`relation_id`);

ALTER TABLE `customer_risk_profile_tb` CHANGE COLUMN `exp_return`  `exp_return` FLOAT(5,2);

ALTER TABLE `customer_risk_profile_tb` CHANGE COLUMN `liquidity_reqd` `liquidity_reqd` FLOAT(5,2);

ALTER TABLE `customer_risk_profile_tb` CHANGE COLUMN `income_reqd` `income_reqd` FLOAT(5,2);

ALTER TABLE `contract_freq_lookup_tb` ADD CONSTRAINT contract_freq_lookup_tb_uk_1 UNIQUE (`field_type`,`field_label`);

ALTER TABLE `mmfdb`.`portfolio_tb`     CHANGE `portfolio_name` `portfolio_name` VARCHAR(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ,     CHANGE `benchmark` `benchmark` INT(11) NULL ,     CHANGE `portfolio_style` `portfolio_style` INT(11) NULL ,     CHANGE `std_dev` `std_dev` DECIMAL(5,2) NULL ,     CHANGE `sharpe_ratio` `sharpe_ratio` INT(11) NULL ,     CHANGE `exp_returns` `exp_returns` DECIMAL(5,2) NULL ,     CHANGE `alpha` `alpha` DECIMAL(5,2) NULL ,     CHANGE `beta` `beta` DECIMAL(5,2) NULL ,     CHANGE `info_ratio` `info_ratio` DECIMAL(5,2) NULL ,     CHANGE `r_squared` `r_squared` TINYINT(4) NULL ,     CHANGE `VaR` `VaR` DECIMAL(12,2) NULL ,     CHANGE `portfolio_90_day_returns` `portfolio_90_day_returns` DECIMAL(5,2) NULL ,     CHANGE `portfolio_1_year returns` `portfolio_1_year returns` DECIMAL(5,2) NULL ,     CHANGE `benchmark_90_days_returns` `benchmark_90_days_returns` DECIMAL(5,2) NULL ,     CHANGE `benchmark_1_year_returns` `benchmark_1_year_returns` DECIMAL(5,2) NULL ,     CHANGE `no_of_customers_assigned` `no_of_customers_assigned` INT(11) NULL ,     CHANGE `rebalance_required` `rebalance_required` TINYINT(1) NULL ,     CHANGE `rebalance_reqd_date` `rebalance_reqd_date` DATETIME NULL ,     CHANGE `last_updated` `last_updated` DATETIME NULL ,     CHANGE `date_created` `date_created` DATETIME NULL ,     CHANGE `start_current_period` `start_current_period` DATETIME NULL ,     CHANGE `status` `status` TINYINT(1) NULL ,     CHANGE `portfolio_value` `portfolio_value` DECIMAL(12,2) NULL ;

ALTER TABLE `mmfdb`.`portfolio_securities_tb`     CHANGE `security_id` `security_id` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL ,     CHANGE `exp_returns` `exp_returns` DECIMAL(5,2) NULL ,     CHANGE `std_dev` `std_dev` DECIMAL(5,2) NULL ,     CHANGE `new_weight` `new_weight` DECIMAL(5,2) NULL ,     CHANGE `current_price` `current_price` DECIMAL(8,2) NULL ,     CHANGE `new_tolerance_negative_range` `new_tolerance_negative_range` DECIMAL(5,2) NULL ,     CHANGE `new_tolerance_positive_range` `new_tolerance_positive_range` DECIMAL(5,2) NULL ,     CHANGE `new_units` `new_units` INT(11) NULL ,     CHANGE `current_value` `current_value` DECIMAL(8,2) NULL ,     CHANGE `current_weight` `current_weight` DECIMAL(5,2) NULL ,     CHANGE `initial_weight` `initial_weight` DECIMAL(5,2) NULL ,     CHANGE `initial_price` `initial_price` DECIMAL(5,2) NULL ,     CHANGE `initial_tolerance_negative_range` `initial_tolerance_negative_range` DECIMAL(5,2) NULL ,     CHANGE `initial_tolerance_positive_range` `initial_tolerance_positive_range` DECIMAL(5,2) NULL ,     CHANGE `initial_units` `initial_units` INT(11) NULL ,     CHANGE `initial_value` `initial_value` DECIMAL(8,2) NULL ,     CHANGE `rebalance_required` `rebalance_required` TINYINT(1) NULL ,     CHANGE `rebalance_reqd_date` `rebalance_reqd_date` DATETIME NULL ;

ALTER TABLE portfolio_details_tb ADD new_allocation DECIMAL(5,2) DEFAULT 0.0;

ALTER TABLE `contract_freq_lookup_tb` ADD CONSTRAINT contract_freq_lookup_tb_uk_2 UNIQUE (`field_type`,`field_value`);

ALTER TABLE `mmfdb`.`master_customer_tb` CHANGE `amount` `amount` DOUBLE NULL;

ALTER TABLE `mmfdb`.`master_advisor_tb`  CHANGE `amount` `amount` DOUBLE NULL;

ALTER TABLE `mmfdb`.`portfolio_securities_tb`     ADD COLUMN `new_allocation` DECIMAL(5,2) NULL AFTER `rebalance_reqd_date`;

ALTER TABLE `portfolio_securities_tb` MODIFY COLUMN `new_tolerance_negative_range` INTEGER DEFAULT NULL,
 MODIFY COLUMN `new_tolerance_positive_range` INTEGER,
 MODIFY COLUMN `initial_tolerance_negative_range` INTEGER UNSIGNED DEFAULT NULL,
 MODIFY COLUMN `initial_tolerance_positive_range` INTEGER UNSIGNED DEFAULT NULL;

ALTER TABLE `portfolio_tb` ADD CONSTRAINT `portfolio_tb_fk_3` FOREIGN KEY `portfolio_tb_fk_3` (`benchmark`)
    REFERENCES `master_benchmark_index_tb` (`id`);

ALTER TABLE `master_asset_tb` ADD asset_color VARCHAR(20);

ALTER TABLE `master_asset_tb` DROP COLUMN `asset_class_id`;

ALTER TABLE `mmfdb`.`portfolio_securities_tb` ADD COLUMN `security_description` VARCHAR(100);

ALTER TABLE `portfolio_securities_tb` CHANGE `security_id` `security_id` VARCHAR(300) NULL ;