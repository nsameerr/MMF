
-- verification_code is set to null
ALTER TABLE master_applicant_tb MODIFY COLUMN verification_code VARCHAR(100) NULL;

-- Altering tables for notification changes
ALTER TABLE `customer_advisor_mapping_tb` ADD (advisor_viewed BOOLEAN,investor_viewed BOOLEAN,rateAdvisor_viewed BOOLEAN);
ALTER TABLE `customer_portfolio_tb` ADD (rebalanceViewed BOOLEAN,portfolioChangeViewed BOOLEAN);
ALTER TABLE `portfolio_tb` ADD (rebalanceViewed BOOLEAN);

-- Altering tables for Performance Graph Changes
ALTER TABLE `customer_portfolio_performance_tb` ADD (benchmark_unit_value DECIMAL(12,2) DEFAULT '0.00');
ALTER TABLE `customer_portfolio_tb` ADD (benchmark_unit DECIMAL(12,2) DEFAULT '0.00');
ALTER TABLE `customer_portfolio_tb` ADD (allocatedFund  DOUBLE DEFAULT '0.00');

-- Altering tables for adding address propf and validity
ALTER TABLE `master_applicant_tb` ADD (address2_proof VARCHAR(50) NULL,address2_validity DATE);

-- Altering table customer_transaction_order_details_tb to add orderType
ALTER TABLE `customer_transaction_order_details_tb` ADD (ordertype BOOLEAN);

-- Adding mail send success status
ALTER TABLE `temp_registration_tb` ADD (mail_send_success BOOLEAN);
ALTER TABLE `master_applicant_tb` ADD (mail_send_success BOOLEAN);
ALTER TABLE `master_applicant_tb` ADD (user_activation_date DATE);
ALTER TABLE `master_applicant_tb` ADD (ecs_mandate_send BOOLEAN DEFAULT FALSE);

ALTER TABLE `customer_performance_fee_tb` ADD (reg_id VARCHAR(15));
ALTER TABLE `customer_management_fee_tb` ADD (reg_id VARCHAR(15));

ALTER TABLE ecs_registration_filedata_tb DROP INDEX `ecs_registration_filedata_tb_uk_1`;

ALTER TABLE `customer_performance_fee_tb` ADD (submitted_ecs BOOLEAN DEFAULT FALSE);
ALTER TABLE `customer_management_fee_tb` ADD (submitted_ecs BOOLEAN DEFAULT FALSE);

ALTER TABLE `master_applicant_tb` ADD (linkedIn_connected BOOLEAN DEFAULT TRUE);

ALTER TABLE `ecs_debt_payment_file_content_tb` ADD  (file_upload_date DATETIME NOT NULL,last_updated DATETIME NOT NULL);

ALTER TABLE `customer_portfolio_tb` 
 MODIFY COLUMN `advisor_portfolio_returns_from_start` DECIMAL(10,2) NULL,
 MODIFY COLUMN `benchmark_returns_from_start` DECIMAL(10,2) NULL;

ALTER TABLE `customer_portfolio_tb` 
 MODIFY COLUMN `portfolio_90_day_returns` FLOAT(10,2) NULL,
 MODIFY COLUMN `portfolio_1_year_returns` FLOAT(10,2) NULL;


ALTER TABLE `advisor_details_tb`
 MODIFY COLUMN `max_returns_90_days` DECIMAL(10,2) NULL,
 MODIFY COLUMN `max_returns_1_year` DECIMAL(10,2) NULL,
 MODIFY COLUMN `outperformance` DECIMAL(10,2) NULL;

ALTER TABLE `customer_portfolio_tb` 
 MODIFY COLUMN `portfolio_value` FLOAT(10,2) NULL;

ALTER TABLE `customer_portfolio_tb` ADD (venue_name VARCHAR(10));

ALTER TABLE `master_applicant_tb` ADD
 (
 second_holder_details_available BOOLEAN,
 sms_facility_second_holder BOOLEAN DEFAULT false,
 name_second_holder VARCHAR(100) NULL,
 occupation_second_holder VARCHAR(100) NULL,
 income_range_second_holder VARCHAR(50)NULL,
 net_worth_date_second_holder DATE,
 amount_second_holder VARCHAR(10) NULL,
 politicaly_exposed_second_holder BOOLEAN DEFAULT false,
 politicaly_related_second_holder BOOLEAN DEFAULT false,
 name_employer_second_holder VARCHAR(100) NULL,
 designation_second_holder VARCHAR(100) NULL,
 rbi_reference_number VARCHAR(50)NULL,
 rbi_approval_date DATE,
 depository_participant_name VARCHAR(50)NULL,
 depository_name  VARCHAR(50)NULL,
 beneficiary_name  VARCHAR(50)NULL,
 dp_id_depository  VARCHAR(50)NULL,
 beneficiary_id  VARCHAR(50)NULL,
 trading_preference_exchange VARCHAR(50)NULL,
 trading_preference_segment VARCHAR(50)NULL,
 documentary_evedence_other BOOLEAN,
 documentary_evedence VARCHAR(50)NULL,
 dealing_through_subbroker BOOLEAN,
 subbroker_name VARCHAR(50)NULL,
 nse_sebi_registration_number VARCHAR(50)NULL,
 bse_sebi_registration_number VARCHAR(50)NULL,
 registered_address_subbroker VARCHAR(50)NULL,
 phone_subbroker VARCHAR(50)NULL,
 fax_subbroker VARCHAR(50)NULL,
 website_subbroker VARCHAR(50)NULL,
 dealing_through_broker BOOLEAN,
 name_stock_broker  VARCHAR(50)NULL,
 name_subbroker  VARCHAR(50)NULL,
 client_code_subbroker  VARCHAR(50)NULL,
 exchange_subbroker  VARCHAR(50)NULL,
 Details_broker  VARCHAR(100)NULL,
 type_electronic_contract  VARCHAR(100)NULL,
 facility_internet_trading BOOLEAN DEFAULT true,
 trading_experince VARCHAR(20)NULL,
 address_firm_propritory  VARCHAR(100)NULL,
 type_alert VARCHAR(50)NULL,
 relationSip_mobilenumber VARCHAR(50)NULL,
 pan_mobileNumber VARCHAR(50)NULL,
 other_informations VARCHAR(100)NULL,
 relative_geojit_employee BOOLEAN,
 relationship_geojit_employee VARCHAR(100)NULL,
 geojit_employee_code VARCHAR(50)NULL,
 geojit_employee_name VARCHAR(50)NULL,
 nominee_exist BOOLEAN
 );

ALTER TABLE `master_applicant_tb`
 MODIFY COLUMN `nominee_exist` BOOLEAN DEFAULT FALSE;

ALTER TABLE `customer_portfolio_tb`
ALTER `portfolioChangeViewed` SET DEFAULT FALSE;
ALTER TABLE `customer_portfolio_tb`
ALTER `rebalanceViewed` SET DEFAULT FALSE;

ALTER TABLE `customer_portfolio_securities_tb` ADD (yesterDayUnitCount INT(11) DEFAULT '0');
ALTER TABLE `recomended_customer_portfolio_securities_tb` ADD (yesterDayUnitCount INT(11) DEFAULT '0');
ALTER TABLE `portfolio_securities_tb` ADD (yesterDayUnitCount INT(11) DEFAULT '0');

ALTER TABLE `customer_advisor_mapping_tb`
 MODIFY COLUMN `rate_advisor_flag` BOOLEAN DEFAULT FALSE;        

