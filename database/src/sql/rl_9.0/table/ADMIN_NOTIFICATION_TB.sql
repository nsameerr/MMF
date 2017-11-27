CREATE TABLE admin_notification_tb(
    `id` INT NOT NULL AUTO_INCREMENT,
    `notification_status` VARCHAR(50) NOT NULL,
    `status_code` INT(20) DEFAULT 0  NOT NULL,
    `notification_date` DATETIME NULL,
    `notify_admin` BOOLEAN DEFAULT 0  NOT NULL,
    `admin_viewed` BOOLEAN DEFAULT 0  NOT NULL ,
     PRIMARY KEY (`id`)
);