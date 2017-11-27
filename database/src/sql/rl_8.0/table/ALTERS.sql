
/*Alter script for new UI?UX changes*/
ALTER TABLE `master_applicant_tb` ADD
 (
 address2_email VARCHAR(50),
 address2_mobil VARCHAR(12),
 coresspondence_address VARCHAR(25),
 bank_Country VARCHAR(50),
  bank_state VARCHAR(50)
  );
 ALTER TABLE `master_advisor_tb` DROP COLUMN `primary_qualification_degree`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `primary_qualification_institute`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `primary_qualification_year`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `primary_qualification_id`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `secondary_qualification_degree`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `secondary_qualification_institute`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `secondary_qualification_year`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `secondary_qualification_id`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `tertiary_qualification_degree`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `tertiary_qualification_institute`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `tertiary_qualification_year`;
 ALTER TABLE `master_advisor_tb` DROP COLUMN `tertiary _qualification_id`; 
 ALTER TABLE `master_advisor_tb` ADD
 (
    online_detailsubmites BOOLEAN,
    sebi_certificate_validated BOOLEAN,
    verification_Completed BOOLEAN,
    account_activated BOOLEAN,
    qualification_tb_id INT Not Null
 );
ALTER TABLE master_advisor_tb ADD CONSTRAINT fk_qualification FOREIGN KEY (qualification_tb_id)
REFERENCES `master_advisor_qualification_tb`(adv_id);

ALTER TABLE  `master_advisor_tb` ADD(onlineDetailsSubmitted BOOLEAN,
sebiCertificateValidated BOOLEAN,
verificationCcompleted BOOLEAN,
accountActivated BOOLEAN);

ALTER TABLE `master_advisor_tb` MODIFY COLUMN `work_telephone` VARCHAR(20) NULL;
ALTER TABLE `master_advisor_tb` MODIFY COLUMN `home_telephone` VARCHAR(20) NULL;


ALTER TABLE `master_applicant_tb`   
  ADD linkedin_profile_url VARCHAR(200) NULL;

ALTER TABLE `master_applicant_tb` ADD
 (
 correspondence_address_path VARCHAR(100),
 permanent_address_path VARCHAR(100),
 identity_proof_path VARCHAR(100)
  );

    ALTER TABLE `master_customer_tb` ADD
 (
    form_couriered_Client BOOLEAN DEFAULT FALSE,
    form_received_client BOOLEAN DEFAULT FALSE,
    form_Validated BOOLEAN DEFAULT FALSE,
    rejection_Reason VARCHAR(200),
    accepted BOOLEAN DEFAULT FALSE,
    rejected BOOLEAN,
    rejection_Resolved BOOLEAN DEFAULT FALSE,
    uCC_created BOOLEAN DEFAULT FALSE,
    online_detailsubmites BOOLEAN,
    account_activated BOOLEAN
 );
ALTER TABLE `investor_nominee_details_tb` MODIFY COLUMN `tel_office_nominee` VARCHAR(20) NULL;
ALTER TABLE `investor_nominee_details_tb` MODIFY COLUMN `tel_residence_nominee` VARCHAR(20) NULL;
ALTER TABLE `investor_nominee_details_tb` MODIFY COLUMN `fax_nominee` VARCHAR(20) NULL;
ALTER TABLE `investor_nominee_details_tb` MODIFY COLUMN `tel_office_nominee_minor` VARCHAR(20) NULL;
ALTER TABLE `investor_nominee_details_tb` MODIFY COLUMN `tel_residence_nominee_minor` VARCHAR(20) NULL;
ALTER TABLE `investor_nominee_details_tb` MODIFY COLUMN `fax_nominee_minor` VARCHAR(20) NULL;


ALTER TABLE `master_applicant_tb` MODIFY COLUMN `instruction1` VARCHAR(5);
ALTER TABLE `master_applicant_tb` MODIFY COLUMN `instruction2` VARCHAR(5);
ALTER TABLE `master_applicant_tb` MODIFY COLUMN `instruction3` VARCHAR(5);
ALTER TABLE `master_applicant_tb` MODIFY COLUMN `instruction4` VARCHAR(5);

ALTER TABLE mandate_form_tb
  CHANGE `ifsc_or_micr_number` `ifsc_number` VARCHAR(30) NULL,
  CHANGE `customer_phone` `customer_phone` VARCHAR(20) NULL,
  ADD COLUMN `micr_number` VARCHAR(30) NULL;

ALTER TABLE `master_applicant_tb` MODIFY COLUMN `trading_preference_segment` VARCHAR(100);

ALTER TABLE `mandate_form_tb`   
  CHANGE `frequency` `frequency` VARCHAR(25) NULL;

ALTER TABLE `mandate_form_tb` CHANGE  `customer_email` `email` VARCHAR(50);

ALTER TABLE `master_applicant_tb`   
  ADD COLUMN `documentary_evidence_path` VARCHAR(100);

ALTER TABLE `customer_advisor_mapping_tb`   
  ADD COLUMN `benchmark` VARCHAR(20) NULL;

ALTER TABLE `master_customer_tb`   
  ADD COLUMN `oms_login_id` VARCHAR(40) NULL;

/* Alter script for new Fee calculation  */
  
ALTER TABLE `master_customer_tb` ADD promoId INT;
ALTER TABLE master_customer_tb ADD 
CONSTRAINT fk_promoId FOREIGN KEY (promoId)REFERENCES `promoDefinitions`(promoID);

ALTER TABLE `customer_advisor_mapping_tb` ADD promoId INT;

ALTER TABLE customer_advisor_mapping_tb ADD 
CONSTRAINT fk_MappingPromoId FOREIGN KEY (promoId)REFERENCES `promoDefinitions`(promoID);


ALTER TABLE customer_management_fee_tb ADD 
CONSTRAINT fk_relation_id FOREIGN KEY (relation_id)REFERENCES `customer_advisor_mapping_tb`(relation_id);

ALTER TABLE `customer_performance_fee_tb` ADD 
CONSTRAINT fk_relation FOREIGN KEY (`relation_id`)REFERENCES `customer_advisor_mapping_tb`(`relation_id`);  

ALTER TABLE `customer_portfolio_tb` ADD(
cashFlowDFlag BOOLEAN DEFAULT FALSE,
cashFlow DECIMAL(15,2));

ALTER TABLE `customer_management_fee_tb` ADD(
isQuarterEnd BOOLEAN DEFAULT FALSE,
cashFlow DECIMAL(15,2),
 daysToQuarterEnd INT,
 MgtFeeonCrntCF DECIMAL(15,2),
 totalAllocatdFund DECIMAL(15,2));
  

ALTER TABLE `ecs_debt_payment_file_content_tb` DROP COLUMN `mgnt_row_id`;
ALTER TABLE `ecs_debt_payment_file_content_tb` DROP COLUMN `perf_row_id`;


ALTER TABLE `ecs_debt_payment_file_content_tb` ADD
 (
 mFee BOOLEAN,
 pFee BOOLEAN,
 feeCalculatedate DATETIME
  );

ALTER TABLE `temp_adv`   
  ADD COLUMN `resCityOther` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL,
  ADD COLUMN `offCityOther` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL,
  ADD COLUMN `bnkCityOther` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci NULL,
  ADD COLUMN `indvOrCprt` BOOLEAN DEFAULT TRUE;

ALTER TABLE `master_applicant_tb`   
  ADD COLUMN `resCityOther` VARCHAR(50) NULL,
  ADD COLUMN `offCityOther` VARCHAR(50) NULL,
  ADD COLUMN `bnkCityOther` VARCHAR(50) NULL,
  ADD COLUMN `nomCityOther` VARCHAR(50) NULL,
  ADD COLUMN `minorCityother` VARCHAR(50) NULL,
  ADD COLUMN `indvOrCprt` BOOLEAN DEFAULT TRUE;

ALTER TABLE `temp_inv`   
  ADD COLUMN `pCityOther` VARCHAR(50) NULL,
  ADD COLUMN `cCityOther` VARCHAR(50) NULL,
  ADD COLUMN `bnkCityOther` VARCHAR(50) NULL,
  ADD COLUMN `nomCityOther` VARCHAR(50) NULL,
  ADD COLUMN `minorCityother` VARCHAR(50) NULL,
  ADD COLUMN `panFilePath` VARCHAR(100) NULL,
  ADD COLUMN `coresFilePath` VARCHAR(100) NULL,
  ADD COLUMN `prmntFilePath` VARCHAR(100) NULL,
  ADD COLUMN `docFilePath` VARCHAR(100) NULL;

ALTER TABLE `cities_tb`   
  CHANGE `countryCode` `stateCode` VARCHAR(50) NOT NULL;

ALTER TABLE `mmfdb`.`master_applicant_tb`   
  ADD COLUMN `usNational` VARCHAR(5) DEFAULT '1',
  ADD COLUMN `usResident` VARCHAR(5) DEFAULT '3',
  ADD COLUMN `usBorn` VARCHAR(5) DEFAULT '5',
  ADD COLUMN `usAddress` VARCHAR(5) DEFAULT '7',
  ADD COLUMN `usTelephone` VARCHAR(5) DEFAULT '9',
  ADD COLUMN `usStandingInstruction` VARCHAR(5) DEFAULT '11',
  ADD COLUMN `usPoa` VARCHAR(5) DEFAULT '13',
  ADD COLUMN `usMailAddress` VARCHAR(5) DEFAULT '15',
  ADD COLUMN `individualTaxIdntfcnNmbr` VARCHAR(10) NULL,
  ADD COLUMN `secondHldrPan` VARCHAR(10) NULL,
  ADD COLUMN `secondHldrDependentRelation` VARCHAR(50) DEFAULT 'Self',
  ADD COLUMN `secondHldrDependentUsed` VARCHAR(15) NULL,
  ADD COLUMN `firstHldrDependentUsed` VARCHAR(50) DEFAULT 'Both';
  
  
ALTER TABLE `mmfdb`.`temp_inv`   
  ADD COLUMN `usNational` VARCHAR(5) DEFAULT '1',
  ADD COLUMN `usResident` VARCHAR(5) DEFAULT '3',
  ADD COLUMN `usBorn` VARCHAR(5) DEFAULT '5',
  ADD COLUMN `usAddress` VARCHAR(5) DEFAULT '7',
  ADD COLUMN `usTelephone` VARCHAR(5) DEFAULT '9',
  ADD COLUMN `usStandingInstruction` VARCHAR(5) DEFAULT '11',
  ADD COLUMN `usPoa` VARCHAR(5) DEFAULT '13',
  ADD COLUMN `usMailAddress` VARCHAR(5) DEFAULT '15',
  ADD COLUMN `individualTaxIdntfcnNmbr` VARCHAR(10) NULL,
  ADD COLUMN `secondHldrPan` VARCHAR(10) NULL,
  ADD COLUMN `secondHldrDependentRelation` VARCHAR(50) DEFAULT 'Self',
  ADD COLUMN `secondHldrDependentUsed` VARCHAR(15) NULL,
  ADD COLUMN `firstHldrDependentUsed` VARCHAR(50) DEFAULT 'Both';

ALTER TABLE `master_applicant_tb`   
  ADD COLUMN `kit_number` INT(10) NULL;

ALTER TABLE `mmfdb`.`adminuser_tb`   
  ADD COLUMN `notify_admin` BOOLEAN DEFAULT 0  NULL,
  ADD COLUMN `admin_viewed` BOOLEAN DEFAULT 0  NULL ,
  ADD COLUMN `notification_status` INT(20) DEFAULT 0  NULL,
  ADD COLUMN `notification_date` DATETIME NULL ;

  
/* ALTERS for units to two decimal places*/

ALTER TABLE `mmfdb`.`customer_portfolio_securities_audit_tb`   
  CHANGE `exe_units` `exe_units` DECIMAL(10,2) DEFAULT 0.00  NULL,
  CHANGE `required_units` `required_units` DECIMAL(10,2) DEFAULT 0.00  NULL;

  
ALTER TABLE `mmfdb`.`customer_portfolio_securities_performance_tb`   
  CHANGE `existing_units` `existing_units` DECIMAL(12,2) DEFAULT 0.00  NOT NULL;

  
ALTER TABLE `mmfdb`.`customer_portfolio_securities_tb`   
  CHANGE `exe_units` `exe_units` DECIMAL(11,2) DEFAULT 0.00  NULL,
  CHANGE `required_units` `required_units` DECIMAL(11,2) DEFAULT 0.00  NULL,
  CHANGE `yesterDayUnitCount` `yesterDayUnitCount` DECIMAL(11,2) DEFAULT 0.00  NULL;
  
ALTER TABLE `mmfdb`.`customer_transaction_execution_details_tb`   
  CHANGE `security_units` `security_units` DECIMAL(11,2) DEFAULT 0.00  NOT NULL;
  
  
ALTER TABLE `mmfdb`.`customer_transaction_order_details_tb`   
  CHANGE `security_units` `security_units` DECIMAL(11,2) DEFAULT 0.00  NOT NULL;
  
ALTER TABLE `mmfdb`.`portfolio_securities_audit_tb`   
  CHANGE `initial_units` `initial_units` DECIMAL(11,2) NULL;
  
ALTER TABLE `mmfdb`.`portfolio_securities_performance_tb`   
  CHANGE `existing_units` `existing_units` DECIMAL(11,2) DEFAULT 0.00  NULL;
  
ALTER TABLE `mmfdb`.`portfolio_securities_tb`   
  CHANGE `exe_units` `exe_units` DECIMAL(11,2) NULL,
  CHANGE `yesterDayUnitCount` `yesterDayUnitCount` DECIMAL(11,2) DEFAULT 0.00  NULL;
  
  
ALTER TABLE `mmfdb`.`recomended_customer_portfolio_securities_tb`   
  CHANGE `current_units` `current_units` DECIMAL(11,2) DEFAULT 0.00  NULL,
  CHANGE `exe_units` `exe_units` DECIMAL(11,2) DEFAULT 0.00  NULL,
  CHANGE `required_units` `required_units` DECIMAL(11,2) DEFAULT 0.00  NULL,
  CHANGE `yesterDayUnitCount` `yesterDayUnitCount` DECIMAL(11,2) DEFAULT 0.00  NULL;  
  
