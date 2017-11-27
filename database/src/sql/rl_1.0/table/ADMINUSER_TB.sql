CREATE TABLE `mmfdb`.`adminuser_tb`(  
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `user_type` VARCHAR(20),
  PRIMARY KEY (`id`)
);
