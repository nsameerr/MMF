CREATE TABLE `cash_txn_for_the_day`(  
  `id_cash_txn` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `customer_portfolio_id` INT NOT NULL,
  `datetime` DATETIME NOT NULL,
  `security_name` VARCHAR(50),
  `venue_code` VARCHAR(20),
  `venue_scriptcode` VARCHAR(20),
  `units` FLOAT(15,4),
  `cash_flow` FLOAT(15,4),
  PRIMARY KEY (`id_cash_txn`)
 );
