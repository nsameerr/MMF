CREATE TABLE `master_portfolio_size_tb`(  
  `portfolio_size_id` INT(11) NOT NULL AUTO_INCREMENT,
  `portfolio_size` VARCHAR(200),
  `min_aum` INT(11),
  `max_aum` INT(11),
  PRIMARY KEY (`portfolio_size_id`)
); 
 