-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 27, 2016 at 11:10 AM
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
-- Table structure for table `risk_profile_group_description_master_tb`
--

CREATE TABLE IF NOT EXISTS `risk_profile_group_description_master_tb` (
  `risk_profile_group_id` int(11) NOT NULL AUTO_INCREMENT,
  `risk_profile_group_name` varchar(30) DEFAULT NULL,
  `group_score_lower_limit` float DEFAULT NULL,
  `group_score_upper_limit` float DEFAULT NULL,
  `risk_profile_recommended_portfolio_id` int(11) DEFAULT NULL,
  `risk_profile_description` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`risk_profile_group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `risk_profile_group_description_master_tb`
--

INSERT INTO `risk_profile_group_description_master_tb` (`risk_profile_group_id`, `risk_profile_group_name`, `group_score_lower_limit`, `group_score_upper_limit`, `risk_profile_recommended_portfolio_id`, `risk_profile_description`) VALUES
(1, 'Conservative', 0, 20, 1, '<p>There are a total of five groups. Conservative Group is for people who are extremely risk-averse and Aggressive Group is for people who are extremely comfortable with risk. You are in the Conservative Group.</p><p>You have a low risk tolerance. You may understand that investing requires a longer term perspective but are averse to taking the risk. Investments can go up and down in value and you are very uncomfortable with this fact. Very low risk assets like cash are likely to be a significant part of your portfolio. </p><p>You are typically unwilling to hold on to your investments if they suffer a drop in value. You tend to be largely focused on investment losses rather than investment gains and can feel anxious when things are not going your way financially. </p><p>You either lack experience investing in the stock market and don''t understand financial language or have a good understanding of investment markets but prefer a very cautious approach. Volatility or periods of poor performance is something you are unprepared to deal with. You see investment markets as a sizeable risk rather than an opportunity. </p>'),
(2, 'Moderately Conservative', 20, 40, 2, '<p>There are a total of five groups. Conservative Group is for people who are extremely risk-averse and Aggressive Group is for people who are extremely comfortable with risk. You are in the Moderately Conservative Group. </p><p>You have a low to medium risk tolerance. You may understand that investing requires a longer term perspective but are reluctant to take the risk. Investments can go up and down in value and you are uncomfortable with this fact. Low risk assets like bonds are likely to be a significant part of your portfolio. </p><p>You are typically reluctant to hold on to your investments if they suffer a drop in value. You tend to focus on investment losses rather than investment gains and may feel anxious when things are not going your way financially. </p><p>You either lack experience investing in the stock market and don''t understand financial language or have a good understanding of investment markets but prefer a cautious approach. Volatility or periods of poor performance is something you are rarely prepared to deal with. You see investment markets as a risk rather than an opportunity. </p>'),
(3, 'Moderate', 40, 60, 3, '<p>There are a total of five groups. Conservative Group is for people who are extremely risk-averse and Aggressive Group is for people who are extremely comfortable with risk. You are in the Moderate Group. </p><p>You have a medium risk tolerance. You understand that investing requires a longer term perspective but prefer to judge risk on a case by case basis. Investments can go up and down in value and you feel neutral about this fact.  A mix of assets such as equities and bonds will likely make up your portfolio. </p><p>You are typically unsure whether to hold on to your investments if they suffer a drop in value. You tend to focus on investment gains and losses equally. You often have mixed feelings when things are not going their way financially because you understand successful investing requires a longer-term perspective. </p><p>You usually have modest experience investing in the stock market and a fair understanding of financial terms. Volatility or periods of poor performance is something you are occasionally prepared to deal with. You see investment markets as an equal mix of opportunity and risk. </p>'),
(4, 'Moderately Aggressive', 60, 80, 4, '<p>There are a total of five groups. Conservative Group is for people who are extremely risk-averse and Aggressive Group is for people who are extremely comfortable with risk. You are in the Moderately Aggressive Group. </p><p>You have a medium to high risk tolerance. You fully understand investing requires a longer term perspective and are willing to take the risk. Investments can go up and down in value and you are comfortable with this fact. High risk assets like equities are likely to be a significant part of your portfolio. </p><p>You are inclined to hold on to your investments if they suffer a drop in value. You tend to focus on investment gains rather than investment losses and remain calm when things are not going your way financially. </p><p>You are either experienced in stock market investing and/or knowledgeable about financial terms. Volatility or periods of poor performance is something you are often prepared to deal with. You see investment markets as an opportunity rather than a risk. </p>'),
(5, 'Aggressive', 80, 100, 5, '<p>There are a total of five groups. Conservative Group is for people who are extremely risk-averse and Aggressive Group is for people who are extremely comfortable with risk. You are in the Aggressive Group. </p><p>You have a high risk tolerance. You fully understand investing requires a longer term perspective and seek higher risk for increased rewards. Investments can go up and down in value and you are very comfortable with this fact. High risk assets like equities are likely to be the vast majority their portfolio. </p><p>You are prepared to hold on to your investments if they suffer a drop in value. You tend to be largely focused on investment gains rather than investment losses and remain assured when things are not going your way financially. </p><p>You are either experienced in stock market investing and/or knowledgeable about financial terms. Volatility or periods of poor performance is something you are readily prepared to deal with. You see investment markets as a sizeable opportunity rather than a risk. </p>');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
