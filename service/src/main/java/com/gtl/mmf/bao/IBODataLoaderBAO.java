/**
 *
 */
package com.gtl.mmf.bao;

/**
 * @author 08237
 *
 */
public interface IBODataLoaderBAO {

    int transferTxnData();

    int transferCashbalance();

    boolean isJobScheduled(String type, String status);

    boolean clearTxnAndCash(String type, String status);

    void transferDailyData();

    void renameAndClear5DayOlderData();

    public void logDb(String errorMessage, String exception);

    public void clearBackOfficeData();
}
