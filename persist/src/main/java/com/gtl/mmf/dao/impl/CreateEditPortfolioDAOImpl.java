/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.ICreateEditPortfolioDAO;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.entity.RecomendedCustomerPortfolioSecuritiesTb;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class CreateEditPortfolioDAOImpl implements ICreateEditPortfolioDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private static final int BATCH_SIZE = 10;

    public Integer saveProtfolio(PortfolioTb portfolioTb) {
        return (Integer) sessionFactory.getCurrentSession().save(portfolioTb);
    }

    public Integer saveProtfolioDetails(PortfolioDetailsTb portfolioDetailsTb) {
        return (Integer) sessionFactory.getCurrentSession().save(portfolioDetailsTb);
    }

    public void savePortfolioSecurites(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs) {
        int count_mode = 0;
        for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().save(portfolioSecuritiesTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public List<Object[]> getPorfolioNames(int advisorId, int portfolioTypeId, int benchmark) {
        String hql = "SELECT p.portfolioId, p.portfolioName FROM PortfolioTb AS p WHERE masterAdvisorTb =:advisorId"
                + " AND masterPortfolioTypeTb =:portfolioTypeId AND masterBenchmarkIndexTb =:benchmark";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioTypeId", portfolioTypeId);
        query.setInteger("advisorId", advisorId);
        query.setInteger("benchmark", benchmark);
        return query.list();
    }

    public PortfolioTb getPorfolioById(int portfolioId) {
        String hql = "FROM PortfolioTb WHERE portfolioId =:portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioId", portfolioId);
        return (PortfolioTb) query.uniqueResult();
    }

    public void updatePortfolio(PortfolioTb portfolioTb) {
        sessionFactory.getCurrentSession().update(portfolioTb);
    }

    public void updatePortfolioDetails(PortfolioDetailsTb portfolioDetailsTb) {
        sessionFactory.getCurrentSession().update(portfolioDetailsTb);
    }

    public void savePortfolioSecurities(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs) {
        int count_mode = 0;
        for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().save(portfolioSecuritiesTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public void updatePortfolioSecurities(Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs) {
        int count_mode = 0;
        for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().update(portfolioSecuritiesTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public int updateCustomerPortfoliosOnEdit(int portfolioId) {
        String hql = "UPDATE CustomerPortfolioTb SET portfolioModified = :portfolioModified,"
                + "portfolioChangeViewed =:portfolioChangeViewed WHERE portfolioTb.portfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("portfolioModified", true).setInteger("portfolioId", portfolioId);
        query.setBoolean("portfolioChangeViewed", false);
        return query.executeUpdate();
    }

    public List<CustomerPortfolioTb> getCustomerPortfoliosAssigned(int portfolioId) {
        String hql = "FROM CustomerPortfolioTb WHERE portfolioTb.portfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioId", portfolioId);
        return query.list();
    }

    public void updateCustomerPortfolioDetails(CustomerPortfolioDetailsTb customerPortfolioDetailsTb) {
        sessionFactory.getCurrentSession().update(customerPortfolioDetailsTb);
    }

    public void updateCustomerPortfolioSecurities(Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs) {
        int count_mode = 0;
        for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : customerPortfolioSecuritiesTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().update(customerPortfolioSecuritiesTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public void saveCustomerPortfolioSecurities(Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs) {
        int count_mode = 0;
        for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : customerPortfolioSecuritiesTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().save(customerPortfolioSecuritiesTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    //Saving changes in the customer portfolio
    public void saveOrUpdateCustomerPortfolioSecurities(Set<CustomerPortfolioSecuritiesTb> customerPortfolioSecuritiesTbs) {
        int count_mode = 0;
        for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : customerPortfolioSecuritiesTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().saveOrUpdate(customerPortfolioSecuritiesTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

    public List<PortfolioDetailsTb> getPortfolioDetails(int portfolioId) {
        String hql = "FROM PortfolioDetailsTb WHERE portfolioTb.portfolioId = :portfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioId", portfolioId);
        return query.list();
    }

    public List<Object> getRecomendedCustomerPortfolioSecurities(int portfolioId, CustomerPortfolioDetailsTb customerPortfolioDetailsTb) {
        List<Object> responseList = new ArrayList<Object>();
        String hql = "FROM RecomendedCustomerPortfolioSecuritiesTb WHERE portfolioTb.portfolioId"
                + " = :portfolioId AND customerPortfolioTb.customerPortfolioId = :customerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("portfolioId", portfolioId);
        query.setInteger("customerPortfolioId", customerPortfolioDetailsTb.getCustomerPortfolioTb().getCustomerPortfolioId());
        responseList.add(query.list());

        /**
         * finding minimum AUM to calculate required_units required_units = PPV/
         * CMP PPV = (allocation for security * min AUM of allocated fund)/ 100
         */
//        String sql2 = "SELECT IFNULL((SELECT min_aum FROM master_portfolio_size_tb "
//                + " WHERE :allocatedfund BETWEEN min_aum AND max_aum) "
//                + " ,(SELECT MAX(min_aum) FROM master_portfolio_size_tb))";
//        SQLQuery sqlq = sessionFactory.getCurrentSession().createSQLQuery(sql2);
//        sqlq.setDouble("allocatedfund", customerPortfolioDetailsTb.getCustomerPortfolioTb().getAllocatedFund());
//        responseList.add(sqlq.uniqueResult());

        return responseList;
    }

    public void saveOrUpdateRecomendedCustomerPortfolioSecurities(Set<RecomendedCustomerPortfolioSecuritiesTb> recomendedCustomerPortfolioSecuritiesTbs,
            int customerPortfolioId) {
        int count_mode = 0;
        for (RecomendedCustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : recomendedCustomerPortfolioSecuritiesTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().saveOrUpdate(customerPortfolioSecuritiesTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }

        String hql = "UPDATE CustomerPortfolioTb T1"
                + " SET T1.recomendedCashBal = T1.allocatedFund - (SELECT SUM(T2.requiredUnits * T2.currentPrice) "
                + " FROM RecomendedCustomerPortfolioSecuritiesTb T2 "
                + " WHERE T2.customerPortfolioTb.customerPortfolioId = :CustomerPortfolioId)"
                + " WHERE T1.customerPortfolioId = :CustomerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("CustomerPortfolioId", customerPortfolioId);
        query.executeUpdate();
    }

    public MasterPortfolioTypeTb getPortfolioByStyleAndSize(int portfolioStyleId, int portfolioSizeId) {
        String hql = "FROM MasterPortfolioTypeTb WHERE masterPortfolioSizeTb = :masterPortfolioSizeTb AND"
                + " masterPortfolioStyleTb = :masterPortfolioStyleTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("masterPortfolioSizeTb", portfolioSizeId);
        query.setInteger("masterPortfolioStyleTb", portfolioStyleId);
        return (MasterPortfolioTypeTb) query.uniqueResult();
    }
    
    public List<PortfolioDetailsTb> getOldPortfolioAsset(int portfolioId){
         String hql = "FROM PortfolioDetailsTb WHERE portfolioTb =(FROM portfolioTb WHERE portfolioId=:portfolioId)";
         Query query = sessionFactory.getCurrentSession().createQuery(hql);
         query.setInteger("portfolioId", portfolioId);
         return query.list();
     }
    
    public Integer updatePortfolioTypeOfPortfolio(int portfolioId,int portfolioTypeId){
        String sql ="UPDATE `portfolio_tb` SET `portfolio_type`=:portfolioTypeId WHERE `portfolio_id`=:portfolioId";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("portfolioTypeId", portfolioTypeId);
        query.setInteger("portfolioId", portfolioId);
        return query.executeUpdate();
    }
    
    public void saveNewPortfolioDetails(PortfolioDetailsTb portfolioDetailsTb){
        sessionFactory.getCurrentSession().save(portfolioDetailsTb);
    }
}
