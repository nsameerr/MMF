CREATE TABLE `mmfdb`.`kit_allocation_tb`(  
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `fromValue` INT(10) NOT NULL,
  `toValue` INT(10) NOT NULL,
  `allocation_date` DATE NULL,
  `kit_status` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;
