CREATE TABLE advisorBucketwisePayouts(
id INT AUTO_INCREMENT,advID INT,
promoID INT,mgntfee DECIMAL(15,2),
perffee DECIMAL(15,2),
mfCommission DECIMAL(15,2),
pfCommission DECIMAL(15,2),
mfPayout DECIMAL(15,2),
pfPayout DECIMAL(15,2),
date_Added DATE,
PRIMARY KEY(id),CONSTRAINT `advBuckett_fk` FOREIGN KEY (`advID`) REFERENCES `master_advisor_tb` (`advisor_id`),
CONSTRAINT `promoBuckett_fk` FOREIGN KEY (`promoID`) REFERENCES `promoDefinitions` (`promoID`));

