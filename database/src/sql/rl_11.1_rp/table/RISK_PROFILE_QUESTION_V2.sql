
INSERT INTO `questionoptionsmaster_tb` (`id`, `q_option`, `q_optionvalue`) VALUES
(40,'Strongly Disagree',1),
(41,'Disagree',2),
(42,'No Strong Opinion',3),
(43,'Agree',4),
(44,'Strongly Agree',5),

(45,'Strongly Disagree',5),
(46,'Disagree',4),
(47,'No Strong Opinion',3),
(48,'Agree',2),
(49,'Strongly Agree',1),

(50,'More than 30 years',5),
(51,'21 to 30 years',4),
(52,'11 to 20 years',3),
(53,'Less than equal to 10 years',2),
(54,'Already Retired',1),

(55,'Very High',1),
(56,'High',2),
(57,'Moderate',3),
(58,'Low',4),
(59,'Almost Zero',5),

(60,'I sell immediately',1),
(61,'I don\'t know what to do',2),
(62,'I may hold itfor 1 more year',3),
(63,'I may hold itfor up to 3 years',4),
(64,'I may hold it up to 5 years',5),

(65,'5 year returns of 10% per year with interim loss of up to -8%',1),
(66,'5 year returns of 12% per year with interim loss of up to -18%',2),
(67,'5 year returns of 15% per year with interim loss of up to -25%',3),
(68,'5 year returns of 18% per year with interim loss of up to -30%',4),
(69,'5 year returns of 25% per year with interim loss of up to -40%',5);

INSERT INTO `questionmaster_tb` (`id`, `question`, `img_path`) VALUES
(21, 'I have knowledge of investing in the Stock market to generate adequate returns on my investments.', NULL),
(22, 'Number of years before I reach retirement age of 60', NULL),
(23, 'My expected income growth may be adequate to cover all my future expenses', NULL),
(24, 'I prefer investing in Fixed Deposit or Gold rather than invest in Stocks or Mutual Funds', NULL),
(25, 'What is the level of your borrowings (2e.g. loan or credit card bill outstanding) which cannot be repaid by your current savings or sale of assets you own?', NULL),
(26, 'My future expenses will be significantly higher than my future income', NULL),
(27, 'When an Investment in which I have strong long term belief drops in value by 30%', NULL),
(28, 'When the price of a Stock of a company with good prospects falls to its lowest levels, I buy more of that company\'s stock', NULL),
(29, 'I have adequate savings/assets to support my family\'s lifestyle in the future', NULL),
(30, 'Which investment portfolio matches your requirement', NULL);

INSERT INTO `question_option_mapping_tb` (`id`, `questionid`, `optionid_1`, `optionid_2`, `optionid_3`, `optionid_4`, `optionid_5`, `optionid_6`, `optionid_7`, `optionid_8`, `optionid_9`, `optionid_10`) VALUES
(21,21,40,41,42,43,44,0,0,0,0,0),
(22,22,50,51,52,53,54,0,0,0,0,0),
(23,23,40,41,42,43,44,0,0,0,0,0),
(24,24,45,46,47,48,49,0,0,0,0,0),
(25,25,55,56,57,58,59,0,0,0,0,0),
(26,26,45,46,47,48,49,0,0,0,0,0),
(27,27,60,61,62,63,64,0,0,0,0,0),
(28,28,40,41,42,43,44,0,0,0,0,0),
(29,29,40,41,42,43,44,0,0,0,0,0),
(30,30,65,66,67,68,69,0,0,0,0,0);