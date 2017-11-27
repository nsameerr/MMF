create table `mmf_jobschedule_details` (
	`Id` int (11) NOT NULL AUTO_INCREMENT,
	`JobType` varchar (135),
	`Status` varchar (135),
	`ScheduledDate` datetime ,
	`MMFTransferDate` datetime ,
	`LastUpdatedOn` datetime 
); 

