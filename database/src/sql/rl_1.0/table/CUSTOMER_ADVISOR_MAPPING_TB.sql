CREATE TABLE `mmfdb`.`customer_advisor_mapping_tb` (
  `relation_id` INT NOT NULL,
  `customer_id` INT(11) NOT NULL,
  `advisor_id` INT(11),
  `relation_status` SMALLINT NOT NULL,
  `status_date` DATETIME NOT NULL,
  `allocated_funds` DECIMAL,
  `pending_fees` DECIMAL,
  `contract_start` DATETIME,
  `contract_end` DATETIME,
  `mgmt_fee_type_variable` BOOLEAN,
  `mgmt_fee_freq` SMALLINT,
  `perf_fee_percent` DECIMAL,
  `perf_fee_threshold` DECIMAL,
  `perf_fee_freq` DECIMAL,
  `review_freq` SMALLINT,
  `rating_overall` SMALLINT,
  `rating_sub_quality` SMALLINT,
  `rating_sub_freq` SMALLINT,
  `rating_sub_response` SMALLINT,
  `advisor_request` BOOLEAN,
  PRIMARY KEY (`relation_id`),
  CONSTRAINT `FK_Rel_Customer_Id` FOREIGN KEY `FK_Rel_Customer_Id` (`customer_id`)
    REFERENCES `master_customer_tb` (`customer_id`),
  CONSTRAINT `FK_Rel_Advisor_Id` FOREIGN KEY `FK_Rel_Advisor_Id` (`advisor_id`)
    REFERENCES `master_advisor_tb` (`advisor_id`)    
);
