/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IFeeCalculationDAO;
import com.gtl.mmf.entity.Advisorbucketwisepayouts;
import com.gtl.mmf.entity.Tieredpromocommissionmatrix;
import com.gtl.mmf.entity.Tiers;
import com.gtl.mmf.entity.Totaladvisorpayout;
import com.gtl.mmf.util.StackTraceWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09607
 */
public class FeeCalculationDAOImpl implements IFeeCalculationDAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.IFeeCalculationDAOImpl");
    @Autowired
    private SessionFactory sessionFactory;
    private static final int BATCH_SIZE = 20;

    public List<Tiers> getTiersTB() {
        return sessionFactory.getCurrentSession().createQuery("FROM Tiers ORDER BY tierId").list();
    }

    public List<Tieredpromocommissionmatrix> getTieredpromocommissionmatrixTB() {
        return sessionFactory.getCurrentSession().createQuery("FROM Tieredpromocommissionmatrix ORDER BY id").list();
    }

    public List<Object> createUserListforDebtPay(String retions) {

        List<Object> responeList = new ArrayList<Object>();
        try {
		
            SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            String format = sdf.format(cal.getTime());
            String hql = "From CustomerManagementFeeTb WHERE ecsPaid =:Status AND "
                    + "submittedEcs =:Submitted AND isQuarterEnd=:quarterEnd AND"
                    + " feeCalculateDate >='" + format + "'";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setBoolean("Status", false);
            query.setBoolean("Submitted", false);
            query.setBoolean("quarterEnd", true);
            responeList.add(query.list());
            LOGGER.log(Level.INFO, "-------Loading Management Feee data---------size[{0}]", query.list().size());

            String sql = "From CustomerPerformanceFeeTb WHERE ecsPaid =:Status AND"
                    + " submittedEcs =:Submitted AND feeCalculateDate >='" + format + "'";
            Query sQuery = sessionFactory.getCurrentSession().createQuery(sql);
            sQuery.setBoolean("Status", false);
            sQuery.setBoolean("Submitted", false);
            responeList.add(sQuery.list());
            LOGGER.log(Level.INFO, "-------Loading Performance Feee data---------size[{0}]", sQuery.list().size());

            String sqlQuery = "From CustomerAdvisorMappingTb";
            Query sQqluery = sessionFactory.getCurrentSession().createQuery(sqlQuery);
            responeList.add(sQqluery.list());
            LOGGER.log(Level.INFO, "-------Loading Customer with relation to calculate advisory Feee data---------size[{0}]", sQqluery.list().size());
        } catch (HibernateException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        }
        return responeList;
    }

    public void saveAdvisorFeeDetails(HashMap<String, Advisorbucketwisepayouts> feeMap) {
        int count_mode = 0;
        for (Map.Entry<String, Advisorbucketwisepayouts> entry : feeMap.entrySet()) {
            Advisorbucketwisepayouts bucketwisePayouts = entry.getValue();
            LOGGER.log(Level.INFO, "-------Saving Advisor FEE---------AdvisorId_PromoID[{0}] mfCommission [{1}] pfCommission [{2}] mfPayout [{3}]"
                    + " pfPayout [{4}]", new Object[]{entry.getKey(), bucketwisePayouts.getMfCommission(), bucketwisePayouts.getPfCommission(), bucketwisePayouts.getMfPayout(), bucketwisePayouts.getPfPayout()});
            count_mode++;
            sessionFactory.getCurrentSession().save(bucketwisePayouts);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
            LOGGER.log(Level.INFO, "-------Completed Saving Advisor FEE---------AdvisorId_PromoID[{0}]", entry.getKey());
        }
        sessionFactory.getCurrentSession().flush();
    }

    public void saveAdvisorTotalPayOut(HashMap<Integer, Totaladvisorpayout> feeMap) {
        int count_mode = 0;
        for (Map.Entry<Integer, Totaladvisorpayout> entry : feeMap.entrySet()) {
            Totaladvisorpayout payout = entry.getValue();
            LOGGER.log(Level.INFO, "-------Saving Advisor Totalpayout---------AdvisorId_PromoID[{0}] PayOut [{1}]", new Object[]{entry.getKey(), payout.getPayout()});
            count_mode++;
            sessionFactory.getCurrentSession().save(payout);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
            LOGGER.log(Level.INFO, "-------Completed Saving Advisor Totalpayout---------AdvisorId_PromoID[{0}]", entry.getKey());
        }
        sessionFactory.getCurrentSession().flush();
    }

}
