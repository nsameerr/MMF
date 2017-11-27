CREATE TABLE `master_security_classification_tb` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `amc` varchar(100) DEFAULT NULL,
  `code_no` varchar(10) DEFAULT NULL,
  `scheme_name` varchar(100) DEFAULT NULL,
  `scheme_type` varchar(100) DEFAULT NULL,
  `scheme_category` varchar(100) DEFAULT NULL,
  `scheme_nav_name` varchar(100) DEFAULT NULL,
  `scheme_minimum_amount` varchar(100) DEFAULT NULL,
  `launch_date` datetime DEFAULT NULL,
  `closure_dDate` datetime DEFAULT NULL,
  `closed_flag` varchar(1) DEFAULT NULL,
  `scheme_load` varchar(100) DEFAULT NULL,
  `isin_div_reinvestment` varchar(100) DEFAULT NULL,
  `isin_div_payout` varchar(100) DEFAULT NULL,
  `isin_growth` varchar(100) DEFAULT NULL,
  `mmf_classification` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);