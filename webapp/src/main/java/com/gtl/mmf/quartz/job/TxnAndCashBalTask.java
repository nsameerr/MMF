/**
 *
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IBODataLoaderBAO;
import com.gtl.mmf.service.util.BeanLoader;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 08237
 *
 */
public class TxnAndCashBalTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.TxnAndCashBalTask");

    public boolean processTxn() {
        boolean status = false;
        IBODataLoaderBAO dataLoaderBAO = (IBODataLoaderBAO) BeanLoader.getBean("boDataLoaderBAO");
        int recCount = dataLoaderBAO.transferTxnData();
        LOGGER.log(Level.INFO, "{0} Transaction data transfered ", recCount);
        if (recCount > ZERO) {
            status = true;
        }
        return status;
    }

    public boolean processCash() {
        boolean status = false;
        IBODataLoaderBAO dataLoaderBAO = (IBODataLoaderBAO) BeanLoader.getBean("boDataLoaderBAO");
        int recCount = dataLoaderBAO.transferCashbalance();
        LOGGER.log(Level.INFO, "{0} Cash data transfered ", recCount);
        if (recCount > ZERO) {
            status = true;
        }
        return status;
    }

    public boolean isJobScheduled(String type, String status) {
        IBODataLoaderBAO dataLoaderBAO = (IBODataLoaderBAO) BeanLoader.getBean("boDataLoaderBAO");
        return dataLoaderBAO.isJobScheduled(type, status);
    }

    public boolean clearTxnAndCash(String type, String status) {
        IBODataLoaderBAO dataLoaderBAO = (IBODataLoaderBAO) BeanLoader.getBean("boDataLoaderBAO");
        return dataLoaderBAO.clearTxnAndCash(type, status);
    }

    public void transferDataTask() {
        IBODataLoaderBAO dataLoaderBAO = (IBODataLoaderBAO) BeanLoader.getBean("boDataLoaderBAO");
        dataLoaderBAO.transferDailyData();

    }

    /**
     * Method to clear 5 day older data from the database
     */
    public void renameTableAndClaer5DayOlderData() {
        IBODataLoaderBAO dataLoaderBAO = (IBODataLoaderBAO) BeanLoader.getBean("boDataLoaderBAO");
        dataLoaderBAO.renameAndClear5DayOlderData();
    }

    void clearBackOfficeData() {
        IBODataLoaderBAO dataLoaderBAO = (IBODataLoaderBAO) BeanLoader.getBean("boDataLoaderBAO");
        dataLoaderBAO.clearBackOfficeData();
    }
}
