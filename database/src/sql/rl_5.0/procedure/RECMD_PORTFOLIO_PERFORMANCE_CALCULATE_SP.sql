DELIMITER $$

USE `mmfdb`$$

DROP PROCEDURE IF EXISTS `recmd_portfolio_performance_calculate_sp`$$

CREATE DEFINER=`mmfuser`@`%` PROCEDURE `recmd_portfolio_performance_calculate_sp`(
	IN p_PortfolioId INTEGER,
	IN p_StartDate DATETIME,
	IN p_EndDate DATETIME,
	OUT p_Return DECIMAL(12,2)
)
BEGIN
	DECLARE v_DoneCalculation INT DEFAULT FALSE;
	DECLARE v_CloseValue DECIMAL(12,2);
	DECLARE v_StartValue DECIMAL(12,2);
	DECLARE v_Return DECIMAL(12,2);
	
	SELECT a.closing_value INTO v_CloseValue FROM `portfolio_performance_tb` a
		WHERE a.portfolio_id = p_PortfolioId AND a.datetime = (
			SELECT MAX(b.datetime) FROM portfolio_performance_tb b WHERE b.portfolio_id = p_PortfolioId
				AND b.datetime BETWEEN p_StartDate AND p_EndDate LIMIT 1) LIMIT 1;
	SELECT a.closing_value INTO v_StartValue FROM `portfolio_performance_tb` a
		WHERE a.portfolio_id = p_PortfolioId AND a.datetime = (
			SELECT MIN(b.datetime) FROM portfolio_performance_tb b WHERE b.portfolio_id = p_PortfolioId
				AND b.datetime BETWEEN p_StartDate AND p_EndDate LIMIT 1) LIMIT 1;
	SET v_Return = ((v_CloseValue/v_StartValue) - 1)*100;
	SET p_Return = v_Return;
END$$

DELIMITER ;