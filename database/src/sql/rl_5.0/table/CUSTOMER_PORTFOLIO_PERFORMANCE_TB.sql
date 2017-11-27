CREATE TABLE `customer_portfolio_performance_tb` 
(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    `customer_portfolio_allocation_id` int(11) NOT NULL AUTO_INCREMENT,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    `customer_portfolio_id` int(11) NOT NULL,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    `datetime` datetime NOT NULL,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    `closing_value` decimal(12,2) NOT NULL DEFAULT '0.00',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
    `market_value` decimal(12,2) DEFAULT '0.00',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
    `cash_flow` decimal(12,2) DEFAULT '0.00',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    `market_value_after_cash_flow` decimal(12,2) DEFAULT '0.00',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
    `sub_period_return` decimal(7,2) DEFAULT '0.00',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    PRIMARY KEY (`customer_portfolio_allocation_id`),                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    KEY `customer_portfolio_performance_tb_fk_1` (`customer_portfolio_id`),                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    CONSTRAINT `customer_portfolio_performance_tb_fk_1` FOREIGN KEY (`customer_portfolio_id`) REFERENCES `customer_portfolio_tb` (`customer_portfolio_id`)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
);
