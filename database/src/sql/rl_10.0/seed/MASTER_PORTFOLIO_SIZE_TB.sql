DELETE FROM `master_portfolio_size_tb`;
INSERT INTO `master_portfolio_size_tb` (`portfolio_size_id`, `portfolio_size`, `min_aum`, `max_aum`) VALUES
('1','Rs 1,00,000 Plus','100000',NULL),
('2','Rs 50,000 to Rs 99,999','50000','99999'),
('3','Rs 25,000 to Rs 49,999','25000','49999'),
('4','Rs 10,000 to Rs 24,999','10000','24999'),
('5','Rs 5,000 to Rs 9,999','5000','9999'),
('6','Rs 1,000 to Rs 4,999','1000','4999');