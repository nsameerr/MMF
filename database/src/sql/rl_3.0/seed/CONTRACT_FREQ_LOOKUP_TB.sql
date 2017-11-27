DELETE FROM `contract_freq_lookup_tb` WHERE `field_type` = 'REVIEW_FEQ';
INSERT INTO `contract_freq_lookup_tb` (`field_type`, `field_label`, `field_value`) VALUES('REVIEW_FEQ','Quarterly','1');
INSERT INTO `contract_freq_lookup_tb` (`field_type`, `field_label`, `field_value`) VALUES('REVIEW_FEQ','Half Yearly','2');
INSERT INTO `contract_freq_lookup_tb` (`field_type`, `field_label`, `field_value`) VALUES('REVIEW_FEQ','Yearly','3');