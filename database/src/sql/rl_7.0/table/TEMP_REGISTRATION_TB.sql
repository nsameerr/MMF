CREATE TABLE temp_registration_tb(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `user_type` VARCHAR(20) NOT NULL,
   mail_verified BOOLEAN,
   verificationCode VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `temp_registration_tb_uk_1` (`verificationCode`,`email`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
