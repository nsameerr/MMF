CREATE TABLE `customer_locked__securities_tb`(  
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT,
  `security_code` VARCHAR(50),
  `venue_script_code` VARCHAR(50),
  `trade_code` VARCHAR(50),
  `securitiy_units` DECIMAL(10,4),
  `security_price` DECIMAL(10,4),
  `processed` BOOLEAN DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=latin1 COLLATE=latin1_swedish_ci;
