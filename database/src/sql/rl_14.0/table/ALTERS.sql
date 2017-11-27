ALTER TABLE `master_portfolio_type_tb`   
  ADD COLUMN `tax_planning` INT(11) NULL ,
  ADD COLUMN `min_tax_planning` INT(11) NULL ,
  ADD COLUMN `max_tax_planning` INT(11) NULL;

ALTER TABLE `mmfdb`.`ifc_micr_mapping_tb`   
  CHANGE `ifsc` `bank` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL,
  CHANGE `micr` `ifsc` VARCHAR(50) CHARSET latin1 COLLATE latin1_swedish_ci NULL,
  ADD COLUMN `micr` VARCHAR(30) NULL AFTER `ifsc`,
  ADD COLUMN `branch` VARCHAR(50) NULL AFTER `micr`,
  ADD COLUMN `address` VARCHAR(200) NULL AFTER `branch`,
  ADD COLUMN `contact` VARCHAR(50) NULL AFTER `address`,
  ADD COLUMN `city` VARCHAR(50) NULL AFTER `contact`,
  ADD COLUMN `district` VARCHAR(50) NULL AFTER `city`,
  ADD COLUMN `state` VARCHAR(50) NULL AFTER `district`;

  
