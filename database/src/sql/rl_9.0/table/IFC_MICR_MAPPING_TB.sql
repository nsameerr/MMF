CREATE TABLE `ifc_micr_mapping_tb`(
`id` INT AUTO_INCREMENT,
`ifsc` VARCHAR(20) NOT NULL,
`micr` VARCHAR(10) NOT NULL,
PRIMARY KEY(id));