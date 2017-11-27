CREATE TABLE `master_asset_tb` (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `asset_class_id` smallint(6) NOT NULL,
  `asset_name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
);