CREATE TABLE `portfolio_tb` (
  `portfolio_id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `advisor_id` INTEGER NOT NULL,
  `portfolio_name` VARCHAR(100) NOT NULL,
  `benchmark` INTEGER NOT NULL,
  `portfolio_style` INTEGER NOT NULL,
  `std_dev` DECIMAL(5,2) NOT NULL,
  `sharpe_ratio` INTEGER NOT NULL,
  `exp_returns` DECIMAL(5,2) NOT NULL,
  `alpha` DECIMAL(5,2) NOT NULL,
  `beta` DECIMAL(5,2) NOT NULL,
  `info_ratio` DECIMAL(5,2) NOT NULL,
  `r_squared` TINYINT NOT NULL,
  `VaR` DECIMAL(12,2) NOT NULL,
  `portfolio_90_day_returns` DECIMAL(5,2) NOT NULL,
  `portfolio_1_year returns` DECIMAL(5,2) NOT NULL,
  `benchmark_90_days_returns` DECIMAL(5,2) NOT NULL,
  `benchmark_1_year_returns` DECIMAL(5,2) NOT NULL,
  `no_of_customers_assigned` INTEGER NOT NULL,
  `rebalance_required` BOOLEAN NOT NULL,
  `rebalance_reqd_date` DATETIME NOT NULL,
  `last_updated` DATETIME NOT NULL,
  `date_created` DATETIME NOT NULL,
  `start_current_period` DATETIME NOT NULL,
  `status` BOOLEAN NOT NULL,
  `portfolio_value` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`portfolio_id`),
  CONSTRAINT `FK_advisor_id` FOREIGN KEY `FK_advisor_id` (`advisor_id`)
    REFERENCES `master_advisor_tb` (`advisor_id`),
  CONSTRAINT `FK_portfolio_style` FOREIGN KEY `FK_portfolio_style` (`portfolio_style`)
    REFERENCES `master_portfolio_type_tb` (`id`)
);