
CREATE TABLE `advisor_details_tb` (
  `advisor_id` int(11) NOT NULL,
  `registration_id` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `dominant_style` int(11) DEFAULT NULL,
  `max_returns_90_days` decimal(5,2) DEFAULT NULL,
  `max_returns_1_year` decimal(5,2) DEFAULT NULL,
  `outperformance` decimal(5,2) DEFAULT NULL,
  `outperformance_count_completed` int(11) DEFAULT '0',
  `outperformance_count_inprogress` int(11) DEFAULT '0',
  `outperformance_count` int(11) DEFAULT '0',
  `total_portfolios_managed` int(11) DEFAULT '0',
  `customer_rating` int(11) DEFAULT '0',
  `avg_client_rating` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`advisor_id`),
  KEY `FK_advisor_details_1` (`registration_id`),
  CONSTRAINT `FK_advisor_details_2` FOREIGN KEY (`advisor_id`) REFERENCES `master_advisor_tb` (`advisor_id`)
);