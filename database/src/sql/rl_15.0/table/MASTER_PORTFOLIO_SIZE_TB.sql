UPDATE `master_portfolio_size_tb` SET `portfolio_size`='Rs 1,00,000  to 4,99,999',`max_aum`=499999 WHERE `portfolio_size_id`=1
INSERT INTO `master_portfolio_size_tb`(`portfolio_size`,`min_aum`,`max_aum`) VALUES('Rs 5,00,000  to 9,99,999',500000,999999)
INSERT INTO `master_portfolio_size_tb`(`portfolio_size`,`min_aum`,`max_aum`) VALUES('Rs 10,00,000  to 24,99,999',1000000,2499999)
INSERT INTO `master_portfolio_size_tb`(`portfolio_size`,`min_aum`,`max_aum`) VALUES('Rs 25,00,000  to 49,99,999',2500000,4999999)
INSERT INTO `master_portfolio_size_tb`(`portfolio_size`,`min_aum`,`max_aum`) VALUES('Rs 50,00,000  to 99,99,999',5000000,9999999)
INSERT INTO `master_portfolio_size_tb`(`portfolio_size`,`min_aum`,`max_aum`) VALUES('Rs 10000000 Plus',10000000,NULL)