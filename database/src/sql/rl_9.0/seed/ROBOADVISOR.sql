INSERT INTO `master_applicant_tb` (`id`, `registration_id`, `verification_code`, `is_mail_verified`, `email`, `advisor`, `linkedin_user`, `linkedin_password`, `linkedin_member_id`, `name`, `address1`, `address2`, `address_city`, `address_pincode`, `address_country`, `telephone`, `mobile`, `register_datetime`, `sebi_regno`, `status`, `linkedin_expire_in`, `middle_name`, `last_name`, `picture_url`, `dob`, `linkedin_expire_date`, `work_organization`, `job_title`, `is_active_user`, `father_spouse_name`, `nationality`, `residential_status`, `gender`, `marital_status`, `pan`, `uid_aadhar`, `proof_of_identity`, `address_state`, `address2_country`, `address2_state`, `address2_city`, `address2_pincode`, `telephone2`, `fax`, `proof_of_address`, `expiry_date`, `bank_name`, `bank_building`, `bank_street`, `bank_area`, `bank_city`, `bank_pincode`, `bank_subtype`, `account_number`, `ifcs_number`, `micr_number`, `account_type_open`, `client_id`, `dp_id`, `trading_account_type`, `demat_account_type`, `sms_facility`, `occupation`, `income_range`, `net_worth_date`, `amount`, `politicaly_exposed`, `politicaly_related`, `instruction1`, `instruction2`, `instruction3`, `instruction4`, `address_for_communication`, `permanent_address`, `telephone_isd`, `telephone_std`, `telephone2_isd`, `telephone2_std`, `fax_isd`, `fax_std`, `address2_proof`, `address2_validity`, `mail_send_success`, `user_activation_date`, `ecs_mandate_send`, `linkedIn_connected`, `second_holder_details_available`, `sms_facility_second_holder`, `name_second_holder`, `occupation_second_holder`, `income_range_second_holder`, `net_worth_date_second_holder`, `amount_second_holder`, `politicaly_exposed_second_holder`, `politicaly_related_second_holder`, `name_employer_second_holder`, `designation_second_holder`, `rbi_reference_number`, `rbi_approval_date`, `depository_participant_name`, `depository_name`, `beneficiary_name`, `dp_id_depository`, `beneficiary_id`, `trading_preference_exchange`, `trading_preference_segment`, `documentary_evedence_other`, `documentary_evedence`, `dealing_through_subbroker`, `subbroker_name`, `nse_sebi_registration_number`, `bse_sebi_registration_number`, `registered_address_subbroker`, `phone_subbroker`, `fax_subbroker`, `website_subbroker`, `dealing_through_broker`, `name_stock_broker`, `name_subbroker`, `client_code_subbroker`, `exchange_subbroker`, `Details_broker`, `type_electronic_contract`, `facility_internet_trading`, `trading_experince`, `address_firm_propritory`, `type_alert`, `relationSip_mobilenumber`, `pan_mobileNumber`, `other_informations`, `relative_geojit_employee`, `relationship_geojit_employee`, `geojit_employee_code`, `geojit_employee_name`, `nominee_exist`, `address2_email`, `address2_mobil`, `coresspondence_address`, `bank_Country`, `bank_state`, `linkedin_profile_url`, `correspondence_address_path`, `permanent_address_path`, `identity_proof_path`, `documentary_evidence_path`, `resCityOther`, `offCityOther`, `bnkCityOther`, `nomCityOther`, `minorCityother`, `indvOrCprt`, `usNational`, `usResident`, `usBorn`, `usAddress`, `usTelephone`, `usStandingInstruction`, `usPoa`, `usMailAddress`, `individualTaxIdntfcnNmbr`, `secondHldrPan`, `secondHldrDependentRelation`, `secondHldrDependentUsed`, `firstHldrDependentUsed`, `kit_number`) 
VALUES('1','150705111234',NULL,'1','roboadvisor@managemyfortune.com','1','mmfadvisor@outlook.com','AQVlfQaGhHNypIQSDDO5ww2YzcLsMkVFFSOF7c23X4nk60etwkZLGx4k-5v26adFX1iXRj57E088nN3cU0zNFemHzRGmYUagx6sucNKkImNc8J1UtN9sBBGkZ7QmZBAzHaf5eOBBqk72Q1fgvRmwKzNZFOdqCyj_xO35mCSnPo1Hs0X9gpc','HuFF_STd-_','Robo','C105, Kailash Complex,|Hiranandani Link Road, Vikhroli West,|Parksite','C105, Kailash Complex|Hiranandani Link Road, Vikhroli West,|Parksite','Mumbai','400079','IN','4022079','9930929392','2015-07-05 11:27:05','SBIN12121212121212','100','5183999','','Advisor','','1973-01-11','2015-09-02','MMF Financial Advisors Pvt Ltd','Robo Advisor','1',NULL,NULL,NULL,NULL,NULL,'AAHCM4761A',NULL,NULL,'Maharashtra','IN','Maharashtra','Mumbai','400079','4022079',NULL,NULL,NULL,'AXIS BANK','G17-18 Ground Floor, Ventura Building,','Hiranandani Business Park, ','Powai','Mumbai','400076','Savings Account','911020059974870','UTIB0000246','400211027',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','91','22','91','22',NULL,NULL,NULL,NULL,'1','2015-07-05','0','0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'mmfadvisor@outlook.com','9930929392','Office Address','IN','Maharashtra','https://www.linkedin.com/pub/investment-advisor-passive-allocation/95/117/672',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Self',NULL,'Both',NULL);

INSERT INTO `master_advisor_qualification_tb` (`adv_id`, `master_appl_id`, `registration_id`, `primary_qualification_degree`, `primary_qualification_institute`, `primary_qualification_year`, `primary_qualification_id`, `secondary_qualification_degree`, `secondary_qualification_institute`, `secondary_qualification_year`, `secondary_qualification_id`, `tertiary_qualification_degree`, `tertiary_qualification_institute`, `tertiary_qualification_year`, `tertiary _qualification_id`, `validity_sebi_certificate`, `sebi_certificate_path`) 
VALUES('1','1','150705111234','','','','','','',NULL,'','','',NULL,'','2018-07-01',NULL);

INSERT INTO `master_advisor_tb` (`advisor_id`, `master_appl_id`, `registration_id`, `email`, `status`, `status_date`, `first_name`, `middle_name`, `last_name`, `pan_no`, `home_address1`, `home_address2`, `home_address_city`, `home_address_pincode`, `home_address_country`, `home_telephone`, `mobile`, `work_organization`, `job_title`, `work_address1`, `work_address2`, `work_address_city`, `work_address_pincode`, `work_address_country`, `work_telephone`, `ifsc_code`, `bank_account_no`, `bank_balance`, `password`, `linkedin_user`, `linkedin_password`, `linkedin_member_id`, `SEBI_certification_no`, `document_status`, `init_login`, `logged_in`, `bank_name`, `broker_account_no`, `amount`, `work_mobile`, `dob`, `is_active_user`, `online_detailsubmites`, `sebi_certificate_validated`, `verification_Completed`, `account_activated`, `qualification_tb_id`, `onlineDetailsSubmitted`, `sebiCertificateValidated`, `verificationCcompleted`, `accountActivated`) 
VALUES('1','1','150705111234','roboadvisor@managemyfortune.com',NULL,NULL,'Robo','','Advisor',NULL,'C105, Kailash Complex,|Hiranandani Link Road, Vikhroli West,|Parksite','C105, Kailash Complex|Hiranandani Link Road, Vikhroli West,|Parksite','Mumbai','400079','IN','91224022079','9930929392','MMF Financial Advisors Pvt Ltd','Robo Advisor','C105, Kailash Complex|Hiranandani Link Road, Vikhroli West,|Parksite',NULL,'Mumbai','400079','IN','+91224022079','UTIB0000246','911020059974870',NULL,'62EsRzsmzEp0gQNF5QBTFVM3DM4=',NULL,NULL,NULL,'SBIN12121212121212',NULL,'2','0','AXIS BANK',NULL,NULL,NULL,'1973-01-11','1','1','1','1','1','1',NULL,NULL,NULL,NULL);

INSERT INTO `advisor_details_tb` (`advisor_id`, `registration_id`, `email`, `dominant_style`, `max_returns_90_days`, `max_returns_1_year`, `outperformance`, `outperformance_count_completed`, `outperformance_count_inprogress`, `outperformance_count`, `total_portfolios_managed`, `customer_rating`, `avg_client_rating`) VALUES
('1','150705111234','roboadvisor@managemyfortune.com',NULL,NULL,NULL,NULL,'0','0','0','0',NULL,NULL);