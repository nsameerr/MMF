DELETE FROM `master_portfolio_style_tb`;
INSERT INTO `master_portfolio_style_tb` (`portfolio_style_id`, `portfolio_style`, `portfolio_style_type`, `min_score`, `max_score`) VALUES
('1','Aggressive','Aggressive','81','100'),
('2','Moderately Aggressive','Moderately Aggressive','61','80'),
('3','Moderate','Moderate','41','60'),
('4','Moderately Conservative','Moderately Conservative','21','40'),
('5','Conservative','Conservative','0','20'),
('6','Custom','Custom','0','0');