-- DELETE QUERRY TO CLEAR DATABASE EXCEPT CLIENT REGISTRATION DETAILS AND LOOKUP TABLE


DELETE FROM customer_portfolio_securities_performance_tb;
DELETE FROM customer_portfolio_details_performance_tb;
DELETE FROM customer_portfolio_performance_tb;
DELETE FROM recomended_customer_portfolio_performance_tb ;
DELETE FROM customer_question_response_set_tb ;
DELETE FROM customer_risk_profile_tb;
DELETE FROM add_redeem_log_tb;
DELETE FROM customer_twr_portfolio_return_tb;
DELETE FROM customer_portfolio_securities_tb;
DELETE FROM recomended_customer_portfolio_securities_tb;
DELETE FROM customer_portfolio_details_tb;
DELETE FROM customer_daily_cash_balance_tb;
DELETE FROM customer_portfolio_log_tb;
DELETE FROM performance_fee_over_portfolio_tb;
DELETE FROM customer_portfolio_tb ;
DELETE FROM customer_transaction_order_details_tb;
DELETE FROM customer_transaction_execution_details_tb;
DELETE FROM customer_management_fee_tb;
DELETE FROM customer_performance_fee_tb;
DELETE FROM customer_advisor_mapping_tb;
DELETE FROM customer_portfolio_securities_audit_tb;
DELETE FROM customer_portfolio_details_audit_tb;
DELETE FROM customer_portfolio_audit_tb;
DELETE FROM benchmark_performance_tb;
DELETE FROM portfolio_securities_performance_tb;
DELETE FROM portfolio_details_performance_tb;
DELETE FROM portfolio_performance_tb;
DELETE FROM portfolio_securities_tb;
DELETE FROM portfolio_details_tb; 
DELETE FROM portfolio_tb;
DELETE FROM portfolio_securities_audit_tb;
DELETE FROM portfolio_details_audit_tb;
DELETE FROM portfolio_audit_tb;
DELETE FROM ecs_debt_payment_file_content_tb;
DELETE FROM ecs_registration_filedata_tb;
DELETE FROM job_schedule_tb;
DELETE FROM mmf_exception_log_tb;
DELETE FROM `cash_flow_tb`;