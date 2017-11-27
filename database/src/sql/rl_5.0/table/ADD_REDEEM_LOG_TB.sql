CREATE TABLE `add_redeem_log_tb` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `customer_portfolio_id` INT(11) NOT NULL,
  `addfund` TINYINT(1) NOT NULL,
  `for_amount` DECIMAL(14,2) NOT NULL,
  `date_of_entry` DATETIME NOT NULL,
  `balance_before` DECIMAL(14,2) NOT NULL,
  `cash_before` DECIMAL(14,2) NOT NULL,
  `portfolio_value_before` DECIMAL(14,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_add_redeem_log_tb_1` (`customer_portfolio_id`),
  CONSTRAINT `FK_add_redeem_log_tb_1` FOREIGN KEY (`customer_portfolio_id`) REFERENCES `customer_portfolio_tb` (`customer_portfolio_id`)
);