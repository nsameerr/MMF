CREATE TABLE ecs_registration_filedata_tb(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
   ecsSendDate DATETIME,
  `fileContent` VARCHAR(500) NOT NULL,
  `fileName` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ecs_registration_filedata_tb_uk_1` (`ecsSendDate`)
);