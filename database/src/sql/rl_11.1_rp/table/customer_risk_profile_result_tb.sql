-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 27, 2016 at 11:09 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mmfdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer_risk_profile_result_tb`
--

CREATE TABLE IF NOT EXISTS `customer_risk_profile_result_tb` (
  `response_set_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_registration_id` varchar(50) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `risk_score` int(11) DEFAULT NULL,
  `risk_profile_group_id` int(11) DEFAULT NULL,
  `risk_profile_recommemded_portfolio_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`response_set_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- Dumping data for table `customer_risk_profile_result_tb`
--

INSERT INTO `customer_risk_profile_result_tb` (`response_set_id`, `customer_registration_id`, `date_time`, `risk_score`, `risk_profile_group_id`, `risk_profile_recommemded_portfolio_id`) VALUES
(4, '11', '2016-07-05 17:02:04', 37, 2, 2),
(5, '11', '2016-07-05 17:08:15', 38, 2, 2),
(6, '11', '2016-07-05 17:08:38', 38, 2, 2),
(7, '11', '2016-07-05 17:16:13', 40, 3, 3),
(8, '11', '2016-07-05 17:19:27', 40, 3, 3),
(9, '11', '2016-07-05 17:19:32', 40, 3, 3),
(10, '11', '2016-07-05 17:26:25', 36, 2, 2),
(11, '11', '2016-07-05 17:34:20', 46, 3, 3),
(12, '11', '2016-07-05 17:38:42', 33, 2, 2),
(13, '11', '2016-07-05 17:41:19', 35, 2, 2),
(14, '11', '2016-07-05 17:43:59', 47, 3, 3),
(15, '11', '2016-07-05 18:05:48', 41, 3, 3),
(16, '11', '2016-07-06 12:37:23', 39, 2, 2),
(17, '11', '2016-07-06 16:02:00', 34, 2, 2),
(18, '11', '2016-07-06 16:22:06', 35, 2, 2),
(19, '11', '2016-07-06 16:49:56', 36, 2, 2),
(20, '11', '2016-07-06 18:38:17', 48, 3, 3),
(21, '11', '2016-07-06 18:48:55', 38, 2, 2),
(22, '11', '2016-07-07 13:22:59', 36, 2, 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
