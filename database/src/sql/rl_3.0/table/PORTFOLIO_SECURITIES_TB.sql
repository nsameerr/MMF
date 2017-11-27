CREATE TABLE `portfolio_securities_tb` (
  `portfolio_securities_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `portfolio_id` INTEGER UNSIGNED NOT NULL,
  `portfolio_details_id` INTEGER UNSIGNED NOT NULL,
  `asset_class_id` SMALLINT(6) NOT NULL,
  `security_id` VARCHAR(45) NOT NULL,
  `exp_returns` DECIMAL(5,2) NOT NULL,
  `std_dev` DECIMAL(5,2) NOT NULL,
  `new_weight` DECIMAL(5,2) NOT NULL,
  `current_price` DECIMAL(8,2) NOT NULL,
  `new_tolerance_negative_range` DECIMAL(5,2) NOT NULL,
  `new_tolerance_positive_range` DECIMAL(5,2) NOT NULL,
  `new_units` INTEGER NOT NULL,
  `current_value` DECIMAL(8,2) NOT NULL,
  `current_weight` DECIMAL(5,2) NOT NULL,
  `initial_weight` DECIMAL(5,2) NOT NULL,
  `initial_price` DECIMAL(5,2) NOT NULL,
  `initial_tolerance_negative_range` DECIMAL(5,2) NOT NULL,
  `initial_tolerance_positive_range` DECIMAL(5,2) NOT NULL,
  `initial_units` INTEGER NOT NULL,
  `initial_value` DECIMAL(8,2) NOT NULL,
  `rebalance_required` BOOLEAN NOT NULL,
  `rebalance_reqd_date` DATETIME NOT NULL,
  PRIMARY KEY (`portfolio_securities_id`),
  CONSTRAINT `FK_ps_portfolio` FOREIGN KEY `FK_ps_portfolio` (`portfolio_id`)
    REFERENCES `portfolio_tb` (`portfolio_id`),
  CONSTRAINT `FK_ps_portfolio_details` FOREIGN KEY `FK_ps_portfolio_details` (`portfolio_details_id`)
    REFERENCES `portfolio_details_tb` (`portfolio_details_id`),
  CONSTRAINT `FK_ps_asset_class` FOREIGN KEY `FK_ps_asset_class` (`asset_class_id`)
    REFERENCES `master_asset_tb` (`id`)
);