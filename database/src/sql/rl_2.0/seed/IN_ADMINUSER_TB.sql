-- INSERT INTO `adminuser_tb`(`username`,`password`,`user_type`,`email`,`mobile`)VALUES('admin','admin','admin','admin@managemyfortune.com','1234567890');
UPDATE `adminuser_tb` SET `email`='admin@managemyfortune.com',`mobile`='1234567890' WHERE `username`='admin';
