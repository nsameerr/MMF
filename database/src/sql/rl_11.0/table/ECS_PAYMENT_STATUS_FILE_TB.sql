CREATE TABLE ecs_payment_status_file_tb (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reg_id` VARCHAR (20) NOT NULL,
  `debt_amount_paid` FLOAT (10, 4),
  `due_date` DATETIME NOT NULL,
  `debit_request_nmbr` INT(11),
  `status` VARCHAR (20) NULL,
  `failure_reason` VARCHAR (100) NULL,
  PRIMARY KEY (`id`)
);