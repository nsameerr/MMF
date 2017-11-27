CREATE TABLE `cash_flow_tb`(  
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `trade_code` VARCHAR(16),
  `pay_in` DECIMAL(15,2),
  `pay_out` DECIMAL(15,2),
  `tran_date` DATETIME,
  PRIMARY KEY (`id`)
);

