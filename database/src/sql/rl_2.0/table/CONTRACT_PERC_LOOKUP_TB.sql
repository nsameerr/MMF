CREATE TABLE `mmfdb`.`contract_perc_lookup_tb` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `field_type` VARCHAR(45) NOT NULL,
  `field_label` VARCHAR(45) NOT NULL,
  `field_value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
);