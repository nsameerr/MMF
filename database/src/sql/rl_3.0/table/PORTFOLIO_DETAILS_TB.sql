CREATE TABLE `portfolio_details_tb` (
  `portfolio_details_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `portfolio_id` INTEGER UNSIGNED NOT NULL,
  `asset_class_id` SMALLINT(6) NOT NULL,
  `range_from` DECIMAL(5,2),
  `range_to` DECIMAL(5,2),
  `new_weight` DECIMAL(5,2),
  `current_weight` DECIMAL(5,2),
  `initial_weight` DECIMAL(5,2),
  `rebalance_required` BOOLEAN,
  `rebalance_reqd_date` DATETIME,
  `current_value` DECIMAL(12,2),
  PRIMARY KEY (`portfolio_details_id`),
  CONSTRAINT `FK_portfolio` FOREIGN KEY `FK_portfolio` (`portfolio_id`)
    REFERENCES `portfolio_tb` (`portfolio_id`),
  CONSTRAINT `FK_asset_class` FOREIGN KEY `FK_asset_class` (`asset_class_id`)
    REFERENCES `master_asset_tb` (`id`)
);