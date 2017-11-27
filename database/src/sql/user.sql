CREATE DATABASE mmfdb;
SELECT "Database mmfdb created" as "";
SELECT "Creating user mmfuser" AS "";
CREATE USER 'mmfuser' IDENTIFIED BY 'mmfuser';
SELECT "Grant privilages" AS "";
GRANT ALL PRIVILEGES ON mmfdb.* TO 'mmfuser';
FLUSH PRIVILEGES;
-- Below statement should execute if MySSQL version is 5.6.3 or latter
SELECT "Set InnoDB file format" AS "";
SET GLOBAL INNODB_FILE_FORMAT = BARRACUDA;
SELECT "Set InnoDB large prefis as ON" AS "";
SET GLOBAL INNODB_LARGE_PREFIX = ON;