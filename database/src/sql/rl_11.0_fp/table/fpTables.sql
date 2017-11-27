
    
   CREATE TABLE fp_misc_assumptions_tb(
    id int AUTO_INCREMENT,
    assumption_id int,
   customer_id varchar(50),
    retirement_age int,
    life_expectancy int,
    amount_invested_to_MMF float,
    rate_of_growth_of_FD float,
    long_term_inflation_expectation float,
    post_retirement_recurring_expense float,
    PRIMARY KEY(id)
    );



  CREATE TABLE fp_salary_expense_increment_assumptions_tb(
  increment_assumption_id int AUTO_INCREMENT, 
   assumption_id int, 
    customer_id varchar(50),
    lower_limit_age int,
    upper_limit_age int,
    self_salary_increase_rate float,
    spouse_salary_increase_rate float,
    expense_increase_rate float,
  PRIMARY KEY(increment_assumption_id)
    );    



    CREATE TABLE fp_portfolio_returns_master_tb(
    portfolio_returns_reference_id int AUTO_INCREMENT,
    portfolio_type varchar(30),
    average_returns float,
    standard_deviation float,
    PRIMARY KEY(portfolio_returns_reference_id)
    );




     CREATE TABLE fp_loan_related_assumptions_tb(
         id int AUTO_INCREMENT,
         assumption_id int,
        customer_id varchar(50),
        loan_assumption_id int,
        loan_type_description varchar(25),
        loan_duration int,
        interest_rate float,
        down_payment_percent float,
        PRIMARY KEY(id)
	);




    CREATE TABLE fp_master_user_input_details_tb(
    user_detials_id int AUTO_INCREMENT,
    customer_id varchar(50),
    date_time datetime,
    self_age int,
    gender varchar(10),
    self_monthly_take_home_salary int,
    marrital_status varchar(10),
    spouse_age int,
    spouse_monthly_take_home_salary int,
    total_monthly_salary int,
    monthly_savings int,
    savings_rate float,
    total_current_financial_assets int,
    expected_risk_score_id int,
    PRIMARY KEY(user_detials_id)
    );




CREATE TABLE fp_life_goals_user_input_tb(
    id int AUTO_INCREMENT,
    customer_id varchar(50),
	goal_id int,
    goal_description varchar(40),
   year_of_realization int,
   recurring_frequency_id int,
   want_loan_yes_no varchar(5),
   estimated_amount_as_per_current_prices int,   
    PRIMARY KEY(id)
    );




CREATE TABLE fp_outstandingloans_user_inputs_tb(
id int AUTO_INCREMENT,
customer_id varchar(50),
loan_description varchar(25),
loan_amount int,
monthly_EMI_amount int,
final_year int,
PRIMARY KEY(id)
);



CREATE TABLE fp_insurance_details_user_inputs_tb (
id int AUTO_INCREMENT,
customer_id varchar(50),
insurance_type varchar(20),
insurance_name varchar(50),
insurance_cover int,
annual_premium int,
final_year_of_payment int,
PRIMARY KEY(id)
);  



    CREATE TABLE fp_mapping_goalsid_to_loansid_tb(
    mapping_id int AUTO_INCREMENT,
    life_goals_id int,
    life_goals_description varchar(40),
    loans_id int,
    loans_description varchar(20),
    PRIMARY KEY(mapping_id)
    );



    CREATE TABLE fp_frequencyid_master_tb(
    id int AUTO_INCREMENT,
    frequency_id int,
    frequency_description varchar(40),
    PRIMARY KEY(id)
    );



    CREATE TABLE fp_financial_asset_growth_assumption_tb(
    financial_asset_id int AUTO_INCREMENT,
    financial_asset_name varchar(150),
    growth_rate float,
    PRIMARY KEY(financial_asset_id)
    );
 

CREATE TABLE fp_financial_asset_user_input_details_tb(
    id int AUTO_INCREMENT,
    customer_id varchar(50),
    financial_asset_id int,
    financial_asset_name varchar(150),
    amount int,
    PRIMARY KEY(id)
    );




CREATE TABLE fp_temp_master_user_input_details_tb(
    id int AUTO_INCREMENT,
    customer_id varchar(50),
    date_time datetime,
    self_age int,
gender varchar(10),
    self_monthly_take_home_salary int,
    marrital_status varchar(10),
    spouse_age int,
    spouse_monthly_take_home_salary int,
    total_monthly_salary int,
    monthly_savings int,
    savings_rate float,
    total_current_financial_assets int,
    expected_risk_score_id int,
    PRIMARY KEY(id)
    );


   CREATE TABLE fp_temp_life_goals_user_input_tb(
    id int AUTO_INCREMENT,
    customer_id varchar(50),
     goal_id int,
    goal_description varchar(40),
   year_of_realization int,
   recurring_frequency_id int,
   want_loan_yes_no varchar(5),
   estimated_amount_as_per_current_prices int,   
    PRIMARY KEY(id)   
    );


CREATE TABLE fp_temp_outstandingloans_user_inputs_tb(
id int AUTO_INCREMENT,
customer_id varchar(50),
loan_description varchar(25),
loan_amount int,
monthly_EMI_amount int,
final_year int,
PRIMARY KEY(id)
);



CREATE TABLE fp_temp_insurance_details_user_inputs_tb (
id int AUTO_INCREMENT,
customer_id varchar(50),
insurance_type varchar(20),
insurance_name varchar(50),
insurance_cover int,
annual_premium int,
final_year_of_payment int,
PRIMARY KEY(id)
);  




CREATE TABLE fp_temp_financial_asset_user_input_details_tb(
    id int AUTO_INCREMENT,
    customer_id varchar(50),
    financial_asset_id int,
    financial_asset_name varchar(150),
    amount int,
    PRIMARY KEY(id)
    );
