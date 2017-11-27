CREATE TABLE `master_advisor_qualification_tb`(  
  `adv_id` INT NOT NULL AUTO_INCREMENT,
  `master_appl_id` INT NOT NULL,
  `registration_id` VARCHAR(50) NOT NULL,
 `primary_qualification_degree` VARCHAR(20),
 `primary_qualification_institute` VARCHAR(50),
 `primary_qualification_year` VARCHAR(5),
 `primary_qualification_id` VARCHAR(20),
 `secondary_qualification_degree` VARCHAR(20),
 `secondary_qualification_institute` VARCHAR(50),
 `secondary_qualification_year` VARCHAR(5),
 `secondary_qualification_id` VARCHAR(20),
 `tertiary_qualification_degree` VARCHAR(20),
 `tertiary_qualification_institute` VARCHAR(50),
 `tertiary_qualification_year` VARCHAR(5),
 `tertiary _qualification_id` VARCHAR(20),
 `validity_sebi_certificate` DATE,
 `sebi_certificate_path` VARCHAR(100),
  PRIMARY KEY (`adv_id`),
  CONSTRAINT `FK_adv_qual_id` FOREIGN KEY (`master_appl_id`) REFERENCES `mmfdb`.`master_applicant_tb`(`id`)
);

