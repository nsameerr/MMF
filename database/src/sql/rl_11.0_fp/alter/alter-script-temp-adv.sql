ALTER TABLE `temp_adv` 
	ADD `advPicPath` VARCHAR(300) CHARSET utf8 COLLATE utf8_general_ci NOT NULL ,
	ADD `oneLineDesc` VARCHAR(150) CHARSET utf8 COLLATE utf8_general_ci NOT NULL ,
	ADD `aboutMe` VARCHAR(300) CHARSET utf8 COLLATE utf8_general_ci NOT NULL ,
	ADD `myInvestmentStrategy` VARCHAR(300) CHARSET utf8 COLLATE utf8_general_ci  NOT NULL;

ALTER TABLE `temp_adv` 
	CHANGE `advPicPath` `advPicPath` VARCHAR(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	CHANGE `oneLineDesc` `oneLineDesc` VARCHAR(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	CHANGE `aboutMe` `aboutMe` VARCHAR(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	CHANGE `myInvestmentStrategy` `myInvestmentStrategy` VARCHAR(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL;