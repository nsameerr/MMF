CREATE TABLE `position_tb`(  
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `trade_code` VARCHAR(16),
  `security_name` VARCHAR(32),
  `security_code` VARCHAR(32),
  `venue` VARCHAR(32),
  `settled` DECIMAL(15,2),
  `unsettled` DECIMAL(15,2),
  `total` DECIMAL(15,2),
  `trandate` DATETIME,
  PRIMARY KEY (`id`)
);