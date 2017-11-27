/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Created by 07662
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import static com.gtl.mmf.service.util.IConstants.YYYY_MM_DD_HH_MM_SS;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientPortfolioReturnsRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.ClientPortfolioReturnsRunnable");
    private List<Object> customerPortfolioTbList;
    private String threadname;
    private IEODPortfolioReturnsBAO eodPortfolioReturnsBAO;
    private Date eodDate;

    public ClientPortfolioReturnsRunnable() {
        eodPortfolioReturnsBAO = (IEODPortfolioReturnsBAO) BeanLoader.getBean("eodPortfolioReturnsBAO");
    }

    public void run() {
        LOGGER.log(Level.INFO, threadname);
        for (Iterator<Object> it = customerPortfolioTbList.iterator(); it.hasNext();) {
            String EODdate = DateUtil.dateToString(eodDate, YYYY_MM_DD_HH_MM_SS);
            CustomerPortfolioTb customerPortfolioTb = (CustomerPortfolioTb) it.next();
            eodPortfolioReturnsBAO.calculteClientPortfolioReturns(customerPortfolioTb, EODdate);
        }
    }

    public void setCustomerPortfolioTbList(List<Object> customerPortfolioTbList) {
        this.customerPortfolioTbList = customerPortfolioTbList;
    }

    public void setThreadname(String threadname) {
        this.threadname = threadname;
    }

    public void setEodDate(Date eodDate) {
        this.eodDate = eodDate;
    }
}
