ALTER TABLE `master_customer_tb` ADD CONSTRAINT master_customer_tb_uk_3 UNIQUE KEY(`registration_id`);

ALTER TABLE `master_advisor_tb` ADD CONSTRAINT master_advisor_tb_uk_3 UNIQUE KEY(`registration_id`);

ALTER TABLE master_applicant_tb ADD COLUMN verification_code VARCHAR(100) NOT NULL AFTER `registration_id`;

ALTER TABLE master_applicant_tb ADD COLUMN is_mail_verified BOOLEAN DEFAULT FALSE NOT NULL AFTER `verification_code`;

ALTER TABLE customer_risk_profile_tb ADD COLUMN initial_portfolio_style INT DEFAULT 0;

ALTER TABLE customer_risk_profile_tb ADD CONSTRAINT customer_risk_profile_tb_fk_3  FOREIGN KEY (`benchmark`) REFERENCES `master_benchmark_index_tb`(`id`);

ALTER TABLE customer_risk_profile_tb ADD CONSTRAINT customer_risk_profile_tb_fk_4  FOREIGN KEY (`initial_portfolio_style`) REFERENCES `master_portfolio_type_tb`(`id`);

ALTER TABLE `master_customer_tb` ADD COLUMN `is_active_user` BOOLEAN DEFAULT TRUE  NOT NULL  COMMENT 'Status for user deleted or not' AFTER `dob`;

ALTER TABLE `master_advisor_tb` ADD COLUMN `is_active_user` BOOLEAN DEFAULT TRUE  NOT NULL  COMMENT 'Status for user deleted or not' AFTER `dob`;

ALTER TABLE `master_applicant_tb` ADD COLUMN `is_active_user` BOOLEAN DEFAULT TRUE  NOT NULL  COMMENT 'Status for user deleted or not' AFTER `job_title`;

-- Modified customer_portfolio_tb current_value size
ALTER TABLE `customer_portfolio_tb` MODIFY COLUMN current_value FLOAT(10);

-- Modified questionmaster_tb to add image path

ALTER TABLE `questionmaster_tb` ADD img_path VARCHAR(250);

-- Modified master_portfolio_type_tb for New Asset Alloctaion

ALTER TABLE `master_portfolio_type_tb` ADD (equity_diverisied INT(11) DEFAULT '0',`range_min_equity_diverisied` INT(11) DEFAULT '0',`range_max_equity_diverisied` INT(11) DEFAULT '0',`midcap` INT(11) DEFAULT '0',`range_min_micap` INT(11) DEFAULT '0',`range_max_midcap` INT(11) DEFAULT '0');

ALTER TABLE `master_applicant_tb` MODIFY COLUMN job_title varchar(200);
ALTER TABLE `master_applicant_tb` MODIFY COLUMN work_organization varchar(200);

ALTER TABLE `master_advisor_tb` MODIFY COLUMN job_title varchar(200);
ALTER TABLE `master_advisor_tb` MODIFY COLUMN work_organization varchar(200);

ALTER TABLE `master_customer_tb` MODIFY COLUMN job_title varchar(200);
ALTER TABLE `master_customer_tb` MODIFY COLUMN work_organization varchar(200);

-- added portfolio assigned date to table
ALTER TABLE `customer_portfolio_tb` ADD COLUMN portfolio_assigned DATETIME;