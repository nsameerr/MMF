CREATE TABLE `customer_portfolio_log_tb` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `log_date` datetime NOT NULL,
  `customer_id` int(11) NOT NULL,
  `customer_portfolio_id` int(11) NOT NULL,
  `opening_portfolio_value` decimal(12,2) DEFAULT NULL,
  `closing_portfolio_value` decimal(12,2) DEFAULT NULL,
  `cash_on_closing` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_customer_portfolio_log_tb_1` (`customer_id`),
  KEY `FK_customer_portfolio_log_tb_2` (`customer_portfolio_id`),
  CONSTRAINT `FK_customer_portfolio_log_tb_1` FOREIGN KEY (`customer_id`) REFERENCES `master_customer_tb` (`customer_id`),
  CONSTRAINT `FK_customer_portfolio_log_tb_2` FOREIGN KEY (`customer_portfolio_id`) REFERENCES `customer_portfolio_tb` (`customer_portfolio_id`)
);