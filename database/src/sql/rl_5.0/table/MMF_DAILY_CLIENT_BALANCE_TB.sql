CREATE TABLE `mmf_daily_client_balance_tb` (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `trndate` DATETIME,
  `tradecode` VARCHAR(16),
  `ledgerbalanec` DECIMAL(15,2),
  `euser` VARCHAR(16),
  `lastupdatedon` DATETIME,
  PRIMARY KEY (`id`)
);