DELETE FROM `master_portfolio_type_tb`;

INSERT  INTO `master_portfolio_type_tb`(`id`,`investor_profile`,`portfolio_type`,`min_score`,`max_score`,`cash`,`gold`,`debt`,`equities_index`,`equities_bluechip`,`equities_midcap`,`equities_small_cap`,`fando`,`international`,`commodities`,`mutual_funds`,`range_min__equity`,`range_min_cash`,`range_min_gold`,`range_min_debt`,`range_min_international`,`range_min_fo`,`range_min_mutual_funds`,`range_max_equity`,`range_max_cash`,`range_max_gold`,`range_max_debt`,`range_max_fo`,`range_max_international`,`range_max_mutual_funds`,`equity_diverisied`,`range_min_equity_diverisied`,`range_max_equity_diverisied`,`midcap`,`range_min_micap`,`range_max_midcap`) 
VALUES (1,'Aggressive','Aggressive Growth',81,100,3,5,7,10,10,0,20,0,15,18,0,8,0,4,5,0,0,0,12,5,6,8,20,18,0,25,20,30,35,28,42),
(2,'Moderately Aggressive','Growth',61,80,3,5,12,15,20,0,20,0,10,16,5,12,0,4,10,0,0,0,18,5,6,14,10,12,6,20,16,24,30,24,36),
(3,'Moderate','GARP',41,60,3,5,22,20,30,0,10,0,5,14,10,16,0,4,20,0,0,0,24,5,6,25,5,6,12,15,12,18,20,16,24),
(4,'Moderately Conservative','Value',21,40,3,10,27,25,35,0,0,0,0,12,15,20,0,8,24,0,0,0,30,5,12,31,0,0,18,10,8,12,10,8,12),
(5,'Conservative','Income',0,20,3,10,37,30,30,0,0,0,0,10,20,24,0,8,32,0,0,0,36,5,12,43,0,0,24,0,0,0,0,0,0),
(6,'Custom','GARP',0,0,3,5,22,20,30,0,10,0,5,12,10,0,0,0,0,0,0,0,100,100,100,100,100,100,100,15,0,100,20,0,100);
