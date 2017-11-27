INSERT INTO `fp_financial_asset_growth_assumption_tb` (`financial_asset_id`,`financial_asset_name`,`growth_rate`) VALUES (1,'Cash, Bank Deposits & Liquid Debt Mutual Funds',0);
INSERT INTO `fp_financial_asset_growth_assumption_tb` (`financial_asset_id`,`financial_asset_name`,`growth_rate`) VALUES (2,'Fixed Income (Provident Funds, Debt Mutual Funds, Bonds, KVP, NSC, etc.)',8);
INSERT INTO `fp_financial_asset_growth_assumption_tb` (`financial_asset_id`,`financial_asset_name`,`growth_rate`) VALUES (3,'Gold (ETF, Physical)',5);
INSERT INTO `fp_financial_asset_growth_assumption_tb` (`financial_asset_id`,`financial_asset_name`,`growth_rate`) VALUES (4,'Equity (Mutual Funds, Shares)',12);
INSERT INTO `fp_financial_asset_growth_assumption_tb` (`financial_asset_id`,`financial_asset_name`,`growth_rate`) VALUES (5,'Total Real Estate',10);
INSERT INTO `fp_financial_asset_growth_assumption_tb` (`financial_asset_id`,`financial_asset_name`,`growth_rate`) VALUES (6,'Others',8);


INSERT INTO `fp_frequencyid_master_tb` (`id`,`frequency_id`,`frequency_description`) VALUES (1,0,'Only once');
INSERT INTO `fp_frequencyid_master_tb` (`id`,`frequency_id`,`frequency_description`) VALUES (2,1,'Once every year');
INSERT INTO `fp_frequencyid_master_tb` (`id`,`frequency_id`,`frequency_description`) VALUES (3,2,'Once in two years');
INSERT INTO `fp_frequencyid_master_tb` (`id`,`frequency_id`,`frequency_description`) VALUES (4,3,'Once in three years');
INSERT INTO `fp_frequencyid_master_tb` (`id`,`frequency_id`,`frequency_description`) VALUES (5,4,'Once in four years');
INSERT INTO `fp_frequencyid_master_tb` (`id`,`frequency_id`,`frequency_description`) VALUES (6,5,'Once in five years');


INSERT INTO `fp_loan_related_assumptions_tb` (`id`,`assumption_id`,`customer_id`,`loan_assumption_id`,`loan_type_description`,`loan_duration`,`interest_rate`,`down_payment_percent`) VALUES (1,0,'Default',1,'Education',10,12,10);
INSERT INTO `fp_loan_related_assumptions_tb` (`id`,`assumption_id`,`customer_id`,`loan_assumption_id`,`loan_type_description`,`loan_duration`,`interest_rate`,`down_payment_percent`) VALUES (2,0,'Default',2,'House',20,10,10);
INSERT INTO `fp_loan_related_assumptions_tb` (`id`,`assumption_id`,`customer_id`,`loan_assumption_id`,`loan_type_description`,`loan_duration`,`interest_rate`,`down_payment_percent`) VALUES (3,0,'Default',3,'Car',5,13,10);
INSERT INTO `fp_loan_related_assumptions_tb` (`id`,`assumption_id`,`customer_id`,`loan_assumption_id`,`loan_type_description`,`loan_duration`,`interest_rate`,`down_payment_percent`) VALUES (4,0,'Default',4,'Personal',5,15,10);


INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (1,0,'Others',4,'Personal ');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (2,1,'Self Marriage',4,'Personal');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (3,2,'Child Marriage',4,'Personal');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (4,3,'Two Wheeler',3,'Car');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (5,4,'Car',3,'Car');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (6,5,'House',2,'House ');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (7,6,'Domestic Vacation',4,'Personal');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (8,7,'International Vacation',4,'Personal');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (9,8,'Self Higher Education',1,'Education');
INSERT INTO `fp_mapping_goalsid_to_loansid_tb` (`mapping_id`,`life_goals_id`,`life_goals_description`,`loans_id`,`loans_description`) VALUES (10,9,'Child Education',1,'Education');


INSERT INTO `fp_misc_assumptions_tb` (`id`,`assumption_id`,`customer_id`,`retirement_age`,`life_expectancy`,`amount_invested_to_MMF`,`rate_of_growth_of_FD`,`long_term_inflation_expectation`,`post_retirement_recurring_expense`) VALUES (1,0,'Default',60,85,80,7,5,75);


INSERT INTO `fp_portfolio_returns_master_tb` (`portfolio_returns_reference_id`,`portfolio_type`,`average_returns`,`standard_deviation`) VALUES (1,'Conservative',11.2,13.3);
INSERT INTO `fp_portfolio_returns_master_tb` (`portfolio_returns_reference_id`,`portfolio_type`,`average_returns`,`standard_deviation`) VALUES (2,'Moderately Conservative',13.1,19.1);
INSERT INTO `fp_portfolio_returns_master_tb` (`portfolio_returns_reference_id`,`portfolio_type`,`average_returns`,`standard_deviation`) VALUES (3,'Moderate',14.1,24.3);
INSERT INTO `fp_portfolio_returns_master_tb` (`portfolio_returns_reference_id`,`portfolio_type`,`average_returns`,`standard_deviation`) VALUES (4,'Moderately Aggressive',15.1,32.8);
INSERT INTO `fp_portfolio_returns_master_tb` (`portfolio_returns_reference_id`,`portfolio_type`,`average_returns`,`standard_deviation`) VALUES (5,'Aggressive',16,37.3);


INSERT INTO `fp_salary_expense_increment_assumptions_tb` (`increment_assumption_id`,`assumption_id`,`customer_id`,`lower_limit_age`,`upper_limit_age`,`self_salary_increase_rate`,`spouse_salary_increase_rate`,`expense_increase_rate`) VALUES (1,0,'Default',0,28,10,10,12);
INSERT INTO `fp_salary_expense_increment_assumptions_tb` (`increment_assumption_id`,`assumption_id`,`customer_id`,`lower_limit_age`,`upper_limit_age`,`self_salary_increase_rate`,`spouse_salary_increase_rate`,`expense_increase_rate`) VALUES (2,0,'Default',28,35,8,8,9);
INSERT INTO `fp_salary_expense_increment_assumptions_tb` (`increment_assumption_id`,`assumption_id`,`customer_id`,`lower_limit_age`,`upper_limit_age`,`self_salary_increase_rate`,`spouse_salary_increase_rate`,`expense_increase_rate`) VALUES (3,0,'Default',35,50,8,8,8);
INSERT INTO `fp_salary_expense_increment_assumptions_tb` (`increment_assumption_id`,`assumption_id`,`customer_id`,`lower_limit_age`,`upper_limit_age`,`self_salary_increase_rate`,`spouse_salary_increase_rate`,`expense_increase_rate`) VALUES (4,0,'Default',50,60,5,5,8);
INSERT INTO `fp_salary_expense_increment_assumptions_tb` (`increment_assumption_id`,`assumption_id`,`customer_id`,`lower_limit_age`,`upper_limit_age`,`self_salary_increase_rate`,`spouse_salary_increase_rate`,`expense_increase_rate`) VALUES (5,0,'Default',60,80,0,0,5);
INSERT INTO `fp_salary_expense_increment_assumptions_tb` (`increment_assumption_id`,`assumption_id`,`customer_id`,`lower_limit_age`,`upper_limit_age`,`self_salary_increase_rate`,`spouse_salary_increase_rate`,`expense_increase_rate`) VALUES (6,0,'Default',80,150,0,0,5);
