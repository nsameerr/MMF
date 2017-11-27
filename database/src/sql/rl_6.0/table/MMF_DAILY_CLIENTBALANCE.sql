CREATE TABLE `MMF_Daily_ClientBalance` (                                                                                                                                                                                                                                                                                                                                                   
    `SlNo` INT(11) NOT NULL AUTO_INCREMENT,                                                                                                                                                                                                                                                                                                                                                  
    `Trandate` DATETIME DEFAULT NULL,                                                                                                                                                                                                                                                                                                                                                        
    `Clientid` INT(11) DEFAULT NULL,                                                                                                                                                                                                                                                                                                                                                         
    `TradeCode` VARCHAR(16) DEFAULT NULL,                                                                                                                                                                                                                                                                                                                                                    
    `LedgerBalance` DECIMAL(10,0) DEFAULT NULL,                                                                                                                                                                                                                                                                                                                                              
    `Euser` VARCHAR(16) DEFAULT NULL,                                                                                                                                                                                                                                                                                                                                                        
    `LastUpdatedOn` DATETIME DEFAULT NULL,                                                                                                                                                                                                                                                                                                                                                   
    KEY `SlNo` (`SlNo`)                                                                                                                                                                                                                                                                                                                                                                      
);

