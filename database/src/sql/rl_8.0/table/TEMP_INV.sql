CREATE TABLE `temp_inv`(  
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`registration_id` VARCHAR (50),
	`email` VARCHAR (100),
	`firstname` VARCHAR (50),
	`middlename` VARCHAR (50),
	`lastname` VARCHAR (50),
	`fatherSpouse` VARCHAR (50),
	`dob` DATE ,
	`nationality` VARCHAR (50),
	`status` VARCHAR (50),
	`gender` VARCHAR (50),
	`mstatus` VARCHAR (50),
	`uid` VARCHAR (20),
	`pan` VARCHAR (15),
	`caddress` VARCHAR (100),
	`cpincode` VARCHAR (10),
	`country` VARCHAR (50),
	`cstate` VARCHAR (50),
	`ccity` VARCHAR (50),
	`cproof` VARCHAR (100),
	`cvalidity` DATE ,
	`permenentAddress` BOOLEAN,
	`pAddress` VARCHAR (100),
	`ppin` VARCHAR (10),
	`pcountry` VARCHAR (50),
	`pstate` VARCHAR (50),
	`pcity` VARCHAR (50),
	`pproof` VARCHAR (100),
	`pvalidity` DATE ,
	`mobile` VARCHAR (15),
	`htelephone` VARCHAR (30),
	`rtelephone` VARCHAR (30),
	`ftelphone` VARCHAR (30),
	`bankname` VARCHAR (200),
	`accountType` VARCHAR (100),
	`accno` VARCHAR (30),
	`reAccno` VARCHAR (30),
	`bifsc` VARCHAR (15),
	`bmicr` VARCHAR (15),
	`baddress` VARCHAR (100),
	`bpincode` VARCHAR (10),
	`bcountry` VARCHAR (50),
	`bstate` VARCHAR (50),
	`bcity` VARCHAR (50),
	`openAccountType` VARCHAR (50),
	`dp` VARCHAR (50),
	`tradingtAccount` VARCHAR (50),
	`dematAccount` VARCHAR (50),
	`smsFacility` VARCHAR (10),
	`fstHldrOccup` VARCHAR (50),
	`fstHldrOrg` VARCHAR (50),
	`fstHldrDesig` VARCHAR (50),
	`fstHldrIncome` VARCHAR (50),
	`fstHldrNet` DATE ,
	`fstHldrAmt` VARCHAR (20),
	`pep` VARCHAR (10),
	`rpep` VARCHAR (10),
	`scndHldrExist` BOOLEAN,
	`scndHldrName` VARCHAR (50),
	`scndHldrOccup` VARCHAR (50),
	`scndHldrOrg` VARCHAR (50),
	`scndHldrDesig` VARCHAR (50),
	`scndHldrSms` VARCHAR (10),
	`scndHldrIncome` VARCHAR (50),
	`scndHldrNet` DATE ,
	`scndHldrAmt` VARCHAR (20),
	`scndPep` VARCHAR (10),
	`scndRpep` VARCHAR (10),
	`instrn1` VARCHAR (50),
	`instrn2` VARCHAR (50),
	`instrn3` VARCHAR (50),
	`instrn4` VARCHAR (50),
	`instrn5` VARCHAR (50),
	`depoPartcpnt` VARCHAR (50),
	`deponame` VARCHAR (50),
	`beneficiary` VARCHAR (50),
	`dpId` VARCHAR (50),
	`docEvdnc` VARCHAR (50),
	`other` VARCHAR (50),
	`experience` VARCHAR (50),
	`contractNote` VARCHAR (50),
	`intrntTrading` VARCHAR (50),
	`alert` VARCHAR (50),
	`relationship` VARCHAR (50),
	`panAddtnl` VARCHAR (50),
	`otherInformation` VARCHAR (100),
	`nomineeExist` BOOLEAN,
	`nameNominee` VARCHAR (50),
	`nomineeRelation` VARCHAR (50),
	`nomineeDob` DATE ,
	`nominePan` VARCHAR (50),
	`nomineeAddress` VARCHAR (100),
	`nomineePincode` VARCHAR (50),
	`nomCountry` VARCHAR (50),
	`nomState` VARCHAR (50),
	`nomCity` VARCHAR (50),
	`notelephone` VARCHAR (30),
	`nRtelephone` VARCHAR (30),
	`nomineeFax` VARCHAR (30),
	`nomMobile` VARCHAR (50),
	`nomEmail` VARCHAR (50),
	`minorExist` BOOLEAN,
	`minorGuard` VARCHAR (50),
	`mnrReltn` VARCHAR (50),
	`mnrDob` DATE ,
	`minorAddress` VARCHAR (50),
	`mnrCountry` VARCHAR (50),
	`mnrState` VARCHAR (50),
	`mnrCity` VARCHAR (50),
	`mnrPincode` VARCHAR (50),
	`minortel` VARCHAR (50),
	`minrRestel` VARCHAR (50),
	`minorfax` VARCHAR (50),
	`mnrMob` VARCHAR (50),
	`mnrEmail` VARCHAR (50),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_EMAIL` (`email`)
);