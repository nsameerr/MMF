CREATE TABLE `mmfdb`.`sys_conf_param_tb`(  
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100),
  `value` VARCHAR(50),
  `description` VARCHAR(100),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniqueName` (`name`)
);

