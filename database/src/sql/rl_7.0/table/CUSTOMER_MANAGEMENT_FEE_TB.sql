CREATE TABLE customer_management_fee_tb (
  `id` INT NOT NULL AUTO_INCREMENT,
  `relation_id` INT NOT NULL,
  `customer_id` INT(11) NOT NULL,
  `advisor_id` INT(11),
  `fee_calculate_date` DATETIME NOT NULL,
  `mgmt_fee` DECIMAL(10,2),
  `mgmt_fee_freq` SMALLINT,
  `ecs_pay_date` DATETIME NOT NULL,
  `ecs_paid` BOOLEAN,
  PRIMARY KEY (`id`)
);

