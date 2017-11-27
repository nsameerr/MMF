/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf;

import com.gtl.mmf.bao.ICustomerPortfolioAuditBAO;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerPortfolioAuditAspect {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.CustomerPortfolioAuditAspect");
    @Autowired
    ICustomerPortfolioAuditBAO customerPortfolioAuditBAO;

    /**
     * Method to execute before of action
     *
     * @param joinPoint
     */
    public void beforeAssign(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        PortfolioVO portfolioVO = (PortfolioVO) args[ZERO];
        Integer affectedRows = customerPortfolioAuditBAO.auditBeforeUpdatePortfolio(portfolioVO.getPortfolioId(), "MODIFY");
        if (affectedRows > ZERO) {
            LOGGER.log(Level.INFO, "Customer portfolio data successfully stored before updation");
        } else {
            LOGGER.log(Level.INFO, "Customer portfolio data not stored before updation");
        }
    }

}
