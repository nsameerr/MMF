CREATE TABLE ecs_debt_payment_file_content_tb (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reg_id` VARCHAR (20) NOT NULL,
  `debt_amount` DECIMAL (10, 2),
  `status` VARCHAR (20) NULL,
  `failure_reason` VARCHAR (100) NULL,
  `due_date` DATETIME NOT NULL,
  `mgnt_row_id` INT NULL,
  `perf_row_id` INT NULL,
  PRIMARY KEY (`id`)
) ;
