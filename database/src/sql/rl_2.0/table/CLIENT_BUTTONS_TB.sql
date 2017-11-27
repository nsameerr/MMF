CREATE TABLE `mmfdb`.`client_buttons_tb` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_type` VARCHAR(45) NOT NULL,
  `status_code` INTEGER NOT NULL,
  `button_text` VARCHAR(45) NOT NULL,
  `style_class` VARCHAR(45),
  PRIMARY KEY (`id`)
);
