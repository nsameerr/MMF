CREATE TABLE `job_schedule_tb` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `job_type` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `scheduledate` DATETIME NOT NULL,
  `lastupdatedon` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
);