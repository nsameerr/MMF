/**
 *
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.JobScheduleTb;
import com.gtl.mmf.entity.MmfDailyClientBalanceTb;
import com.gtl.mmf.entity.MmfDailyTxnSummaryTb;
import com.gtl.mmf.entity.MmfRetPortfolioSplitup;
import java.util.Date;
import java.util.List;

/**
 * @author 08237
 *
 */
public interface IBODataLoaderDAO {

    int transferTxnData(Date scheduleDate);

    int transferCashbalance(Date scheduleDate);

    int transferOffDayDummydata(Date scheduleDate);

    int isJobScheduled(String type, String status);

    int clearTxnAndCash(String type, String status);

    List<Object> jobScheduleList();

    void renameTablesAndClear5DayOlderData(String reNameQuery1, String reNameQuery2, String reNameQuery3, String dropQuery, Date maxScheduledate);

    Object getDeletedTableNames(Date EODDate, String DbName);

    int transferCashFlowData();
    
    int transferCashbalance(List<MmfDailyClientBalanceTb> clientBalanceTbs);

    int transferTxnData(List<MmfDailyTxnSummaryTb> transactionTbs);

    int transferCashFlowData(List<CashFlowTb> cashFlow);

    public Integer transferOffDayDummydata(JobScheduleTb jobScheduleTb);

    public void transferPositionData(List<MmfRetPortfolioSplitup> posData);

    public int clearMmfCashFlow();
}
