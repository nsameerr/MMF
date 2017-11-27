CREATE TABLE totalAdvisorPayout(
advisorID INT,
payDate DATE,
Payout DECIMAL(8,2),
PRIMARY KEY(advisorID,payDate),
CONSTRAINT `totalAdvisorPayout` FOREIGN KEY (`advisorID`) REFERENCES `master_advisor_tb` (`advisor_id`)
);

