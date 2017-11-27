CREATE TABLE `performance_fee_over_portfolio_tb` (
  `investor_id` int(11) NOT NULL,
  `investor_portfolio_id` int(11) NOT NULL,
  `c_date` datetime NOT NULL,
  `expected_return` decimal(12,2) NOT NULL,
  `high_water_mark` decimal(12,2) NOT NULL,
  `market_value` decimal(12,2) NOT NULL,
  `billable_amount` decimal(12,2) DEFAULT '0.00',
  `fees` decimal(12,2) DEFAULT '0.00',
  PRIMARY KEY (`investor_id`,`investor_portfolio_id`,`c_date`),
  KEY `investor_portfolio_id` (`investor_portfolio_id`),
  CONSTRAINT `performance_fee_over_portfolio_tb_ibfk_1` FOREIGN KEY (`investor_id`) REFERENCES `master_customer_tb` (`customer_id`),
  CONSTRAINT `performance_fee_over_portfolio_tb_ibfk_2` FOREIGN KEY (`investor_portfolio_id`) REFERENCES `customer_portfolio_tb` (`customer_portfolio_id`)
  );
