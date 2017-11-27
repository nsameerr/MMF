CREATE TABLE tieredPromoCommissionMatrix (
id INT AUTO_INCREMENT,
promoID INT,tierID INT,
mgnmntFeeCommission  DECIMAL(8,2),
perfFeeCommission  DECIMAL(8,2),
PRIMARY KEY(id),
CONSTRAINT `tieredPromoCommissionMatrix_tb_fk_1` FOREIGN KEY (`promoID`) REFERENCES `promoDefinitions` (`promoID`),
CONSTRAINT `tieredPromoCommissionMatrix_tb_fk_2` FOREIGN KEY (`tierID`) REFERENCES `tiers` (`tierID`)
);

