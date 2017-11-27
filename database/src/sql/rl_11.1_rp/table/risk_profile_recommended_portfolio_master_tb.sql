-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 27, 2016 at 11:11 AM
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
-- Table structure for table `risk_profile_recommended_portfolio_master_tb`
--

CREATE TABLE IF NOT EXISTS `risk_profile_recommended_portfolio_master_tb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_allocation_id` int(11) DEFAULT NULL,
  `risk_profile_recommended_portfolio_id` int(11) DEFAULT NULL,
  `asset_class_id` int(11) DEFAULT NULL,
  `asset_class_allocation` float DEFAULT NULL,
  `allocation_min_limit` float DEFAULT NULL,
  `allocation_max_limit` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `risk_profile_recommended_portfolio_master_tb`
--

INSERT INTO `risk_profile_recommended_portfolio_master_tb` (`id`, `asset_allocation_id`, `risk_profile_recommended_portfolio_id`, `asset_class_id`, `asset_class_allocation`, `allocation_min_limit`, `allocation_max_limit`) VALUES
(1, 0, 1, 2, 50, 45, 55),
(2, 0, 1, 3, 50, 45, 55),
(3, NULL, 2, 2, 33, 30, 40),
(4, NULL, 2, 3, 33, 30, 40),
(5, NULL, 2, 4, 34, 30, 40),
(6, NULL, 3, 2, 25, 20, 30),
(7, NULL, 3, 3, 25, 20, 30),
(8, NULL, 3, 4, 25, 20, 30),
(9, NULL, 3, 6, 25, 20, 30),
(10, NULL, 4, 2, 20, 15, 25),
(11, NULL, 4, 3, 30, 25, 35),
(12, NULL, 4, 4, 30, 25, 35),
(13, NULL, 4, 6, 20, 15, 25),
(14, NULL, 5, 2, 20, 15, 25),
(15, NULL, 5, 3, 20, 15, 25),
(16, NULL, 5, 4, 20, 15, 25),
(17, NULL, 5, 6, 20, 15, 25),
(18, NULL, 5, 12, 20, 15, 25);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
