CREATE TABLE `master_portfolio_style_tb`(  
  `portfolio_style_id` INT(11) NOT NULL AUTO_INCREMENT,
  `portfolio_style` VARCHAR(200),
  `portfolio_style_type` VARCHAR(200),
  `min_score` INT(11),
  `max_score` INT(11),
  PRIMARY KEY (`portfolio_style_id`)
);