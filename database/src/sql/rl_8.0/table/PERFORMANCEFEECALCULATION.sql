 CREATE TABLE performanceFeeCalculation(
 `id` INT NOT NULL AUTO_INCREMENT,
  `relation_id` INT NOT NULL,
  `customer_id` INT(11) NOT NULL,
  `advisor_id` INT(11),
  feeCalculateDate DATE, 
  cashFlow DECIMAL(15,2),
  expectedReturn DECIMAL(15,2), 
  effectiveHWM DECIMAL(15,2),
  marketValue DECIMAL(15,2),
  billableAmount DECIMAL(15,2),
  isBillDate BOOLEAN,
  billAmount DECIMAL(15,2),
  sumPrevBillAmount DECIMAL(15,2),
  PRIMARY KEY(id),CONSTRAINT `relation_id` 
  FOREIGN KEY (`relation_id`) REFERENCES `customer_advisor_mapping_tb`(`relation_id`));
  
  