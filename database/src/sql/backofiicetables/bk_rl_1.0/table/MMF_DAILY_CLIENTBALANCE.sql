create table `mmf_daily_clientbalance` (
	`SlNo` int (11) NOT NULL AUTO_INCREMENT,
	`Trandate` datetime ,
	`Clientid` varchar (45),
	`TradeCode` varchar (48),
	`LedgerBalance` Decimal (12),
	`Euser` varchar (48),
	`LastUpdatedOn` datetime 
); 

