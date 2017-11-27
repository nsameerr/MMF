ALTER TABLE `mmfdb`.`master_customer_tb`     ADD COLUMN `document_status` VARCHAR(50) NULL AFTER `total_available_funds`;

ALTER TABLE `mmfdb`.`master_advisor_tb`     ADD COLUMN `document_status` VARCHAR(50) NULL AFTER `tertiary _qualification_id`;

ALTER TABLE `master_customer_tb` ADD COLUMN password VARCHAR(50);

ALTER TABLE `master_advisor_tb` ADD COLUMN init_login int(1);

ALTER TABLE `master_customer_tb` ADD COLUMN init_login int(1);

ALTER TABLE `master_advisor_tb` ADD COLUMN logged_in BOOLEAN;

ALTER TABLE `master_customer_tb` ADD COLUMN logged_in BOOLEAN;

ALTER TABLE `mmfdb`.`master_advisor_tb` ADD COLUMN `bank_name` VARCHAR(50) NULL AFTER `logged_in`;

ALTER TABLE `mmfdb`.`master_advisor_tb` ADD COLUMN `broker_account_no` VARCHAR(20) NULL AFTER `bank_name`;

ALTER TABLE `mmfdb`.`master_customer_tb` ADD COLUMN `amount` VARCHAR(20) NULL AFTER `logged_in`;

ALTER TABLE `mmfdb`.`master_advisor_tb` ADD COLUMN `amount` VARCHAR(20) NULL AFTER `broker_account_no`;

ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb` MODIFY COLUMN `relation_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT;

-- ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb` MODIFY COLUMN `advisor_id` INT(11) NOT NULL;

ALTER TABLE `mmfdb`.`master_advisor_tb` ADD COLUMN `work_mobile` VARCHAR(15) AFTER `amount`;

ALTER TABLE `mmfdb`.`master_customer_tb` ADD COLUMN `work_mobile` VARCHAR(15) AFTER `amount`;

-- ALTER TABLE `mmfdb`.`contract_freq_lookup_tb` MODIFY COLUMN `field_value` INTEGER UNSIGNED NOT NULL;

-- ALTER TABLE `mmfdb`.`contract_perc_lookup_tb`     CHANGE `field_value` `field_value` DECIMAL(5,2) NOT NULL;

-- ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb` ADD COLUMN `management_fee_variable` DECIMAL(5,2) AFTER `advisor_request`,
                                                    -- ADD COLUMN `management_fee_fixed` INTEGER AFTER `management_fee_variable`,
                                                    -- ADD COLUMN `duration_count` INTEGER AFTER `management_fee_fixed`,
--                                                     ADD COLUMN `duration_frequency_month` INTEGER AFTER `duration_count`,
                                                    -- ADD COLUMN `no_relation_status` INTEGER AFTER `duration_frequency_month`;

-- ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb`     CHANGE `perf_fee_freq` `perf_fee_freq` INT NULL ;

-- ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb`     CHANGE `perf_fee_percent` `perf_fee_percent` DECIMAL(5,2) NULL ,     CHANGE `perf_fee_threshold` `perf_fee_threshold` DECIMAL(5,2) NULL ;

-- ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb`     ADD COLUMN `customer_review` TEXT NULL AFTER `no_relation_status`;

-- ALTER TABLE `mmfdb`.`client_buttons_tb`     ADD COLUMN `immediate` BOOLEAN NULL AFTER `style_class`;

-- ALTER TABLE `mmfdb`.`contract_perc_lookup_tb` CHANGE `field_value` `field_value` DECIMAL(5,2) NOT NULL;

-- ALTER TABLE `mmfdb`.`master_applicant_tb` CHANGE `linkedin_password` `linkedin_password` VARCHAR(200) CHARSET latin1 COLLATE latin1_swedish_ci NULL;
  
-- ALTER TABLE `mmfdb`.`master_applicant_tb` ADD COLUMN `linkedin_expire_in` VARCHAR(15) NULL AFTER `status`;

-- ALTER TABLE `mmfdb`.`master_applicant_tb` ADD COLUMN `middle_name` VARCHAR(100) NULL AFTER `linkedin_expire_in`,  ADD COLUMN `last_name` VARCHAR(100) NULL AFTER `middle_name`;

-- ALTER TABLE `mmfdb`.`master_applicant_tb` ADD COLUMN `picture_url` VARCHAR(200) NULL AFTER `last_name`;

-- ALTER TABLE `mmfdb`.`adminuser_tb` ADD COLUMN `email` VARCHAR(50) NULL AFTER `user_type`;

-- ALTER TABLE `mmfdb`.`adminuser_tb` ADD COLUMN `mobile` VARCHAR(15) NULL AFTER `email`;

-- ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb`     CHANGE `allocated_funds` `allocated_funds` DOUBLE NULL ;

-- ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb`     CHANGE `pending_fees` `pending_fees` DOUBLE NULL ;

-- ALTER TABLE `mmfdb`.`client_buttons_tb`     ADD COLUMN `confirm` BOOLEAN NULL AFTER `immediate`;

-- ALTER TABLE `mmfdb`.`master_applicant_tb` ADD COLUMN `dob` DATE  NULL AFTER `picture_url`;

-- ALTER TABLE `mmfdb`.`master_advisor_tb` ADD COLUMN `dob` DATE  NULL AFTER `work_mobile`;

-- ALTER TABLE `mmfdb`.`master_customer_tb`  ADD COLUMN `dob` DATE  NULL AFTER `work_mobile`;

-- ALTER TABLE `mmfdb`.`master_advisor_tb`   CHANGE `home_address1` `home_address1` VARCHAR(200) ,
  -- CHANGE `work_address1`   `work_address1` VARCHAR(200);

-- ALTER TABLE `mmfdb`.`master_customer_tb`   CHANGE `home_address1` `home_address1` VARCHAR(200) ,
  -- CHANGE `work_address1`   `work_address1` VARCHAR(200);


ALTER TABLE `mmfdb`.`customer_advisor_mapping_tb`   
  CHANGE `relation_id` `relation_id` INT(10) NOT NULL AUTO_INCREMENT;
  
  