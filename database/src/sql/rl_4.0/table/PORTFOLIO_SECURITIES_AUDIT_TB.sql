CREATE TABLE `portfolio_securities_audit_tb` (
  `portfolio_securities_audit_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `portfolio_details_audit_id` INT(10) UNSIGNED DEFAULT NULL,
  `portfolio_securities_id` INT(10) DEFAULT NULL,
  `portfolio_id` INT(10) UNSIGNED NOT NULL,
  `portfolio_details_id` INT(10) UNSIGNED NOT NULL,
  `asset_class_id` SMALLINT(6) NOT NULL,
  `security_id` VARCHAR(45) DEFAULT NULL,
  `exp_returns` DECIMAL(5,2) DEFAULT NULL,
  `std_dev` DECIMAL(5,2) DEFAULT NULL,
  `new_weight` DECIMAL(5,2) DEFAULT NULL,
  `current_price` DECIMAL(8,2) DEFAULT NULL,
  `new_tolerance_negative_range` INT(11) DEFAULT NULL,
  `new_tolerance_positive_range` INT(11) DEFAULT NULL,
  `new_units` INT(11) DEFAULT NULL,
  `current_value` DECIMAL(8,2) DEFAULT NULL,
  `current_weight` DECIMAL(5,2) DEFAULT NULL,
  `initial_weight` DECIMAL(5,2) DEFAULT NULL,
  `initial_price` DECIMAL(5,2) DEFAULT NULL,
  `initial_tolerance_negative_range` INT(10) UNSIGNED DEFAULT NULL,
  `initial_tolerance_positive_range` INT(10) UNSIGNED DEFAULT NULL,
  `initial_units` INT(11) DEFAULT NULL,
  `initial_value` DECIMAL(8,2) DEFAULT NULL,
  `rebalance_required` TINYINT(1) DEFAULT NULL,
  `rebalance_reqd_date` DATETIME DEFAULT NULL,
  `new_allocation` DECIMAL(5,2) DEFAULT NULL,
  `security_description` VARCHAR(100) DEFAULT NULL,
  `last_updateon` DATETIME DEFAULT NULL,
  `activity_type` VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (`portfolio_securities_audit_id`),
  KEY `portfolio_securities_audit_tb_fk_1` (`portfolio_details_audit_id`),
  CONSTRAINT `portfolio_securities_audit_tb_fk_1` FOREIGN KEY (`portfolio_details_audit_id`) REFERENCES `portfolio_details_audit_tb` (`portfolio_details_audit_id`)
);