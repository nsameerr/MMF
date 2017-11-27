DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `job_schedule_check_proc`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `job_schedule_check_proc`(IN type varchar(20),IN status varchar(20))
BEGIN
	SELECT COUNT(*) REC_COUNT FROM <txn_cash_db>.JOB_SCHEDULE_TB WHERE JOB_TYPE=type AND STATUS=status;
END$$

DELIMITER ;