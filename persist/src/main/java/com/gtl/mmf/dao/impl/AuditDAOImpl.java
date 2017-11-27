package com.gtl.mmf.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;

import com.gtl.mmf.dao.IAuditDAO;
import com.gtl.mmf.entity.PortfolioAuditTb;
import com.gtl.mmf.entity.PortfolioDetailsAuditTb;
import com.gtl.mmf.entity.PortfolioSecuritiesAuditTb;
import com.gtl.mmf.entity.PortfolioTb;

public class AuditDAOImpl implements IAuditDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private static final int BATCH_SIZE = 10;

    public PortfolioTb loadPortfolio(Integer portfolioId) {
        return (PortfolioTb) sessionFactory.openSession().load(PortfolioTb.class, portfolioId);
    }

    public List<Object[]> loadPortfolioDet(Integer portfolioId) {
        String hql = "select a.portfolio_details_id,a.portfolio_id,a.asset_class_id,a.range_from,a.range_to,"
                + "a.new_weight,a.current_weight,a.initial_weight,a.rebalance_required,"
                + "a.rebalance_reqd_date,a.current_value,a.new_allocation from portfolio_details_tb a where a.portfolio_id =:PortolioId";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
        query.addScalar("portfolio_details_id", IntegerType.INSTANCE)
                .addScalar("portfolio_id", IntegerType.INSTANCE)
                .addScalar("asset_class_id", ShortType.INSTANCE)
                .addScalar("range_from", BigDecimalType.INSTANCE)
                .addScalar("range_to", BigDecimalType.INSTANCE)
                .addScalar("new_weight", BigDecimalType.INSTANCE)
                .addScalar("current_weight", BigDecimalType.INSTANCE)
                .addScalar("initial_weight", BigDecimalType.INSTANCE)
                .addScalar("rebalance_required", BooleanType.INSTANCE)
                .addScalar("rebalance_reqd_date", TimestampType.INSTANCE)
                .addScalar("current_value", BigDecimalType.INSTANCE)
                .addScalar("new_allocation", BigDecimalType.INSTANCE)
                .setInteger("PortolioId", portfolioId);
        return query.list();
    }

    public Integer insertPortfolioTbAudit(PortfolioAuditTb portfolioAuditTb) {
        Integer pfauditId = (Integer) sessionFactory.getCurrentSession().save(portfolioAuditTb);
        sessionFactory.getCurrentSession().flush();
        return pfauditId;
    }

    public Integer insertPortfolioDetailTbAudit(PortfolioDetailsAuditTb portfolioDetailsAuditTb) {
        Integer pfDetAuditId = (Integer) sessionFactory.getCurrentSession().save(portfolioDetailsAuditTb);
        sessionFactory.getCurrentSession().flush();
        return pfDetAuditId;
    }

    public void insertPortfolioSecuritiesTbAudit(Set<PortfolioSecuritiesAuditTb> portfolioSecuritiesAuditTbs) {
        int count_mode = 0;
        for (PortfolioSecuritiesAuditTb portfolioSecuritiesAuditTb : portfolioSecuritiesAuditTbs) {
            count_mode++;
            sessionFactory.getCurrentSession().save(portfolioSecuritiesAuditTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        sessionFactory.getCurrentSession().flush();
    }

    public List<Object[]> loadPortfolioSec(Integer portfolioDetId) {
        String hql = "select a.portfolio_securities_id,a.portfolio_id,a.portfolio_details_id,a.asset_class_id,a.security_id,"
                + "a.exp_returns,a.std_dev,a.new_weight,a.current_price,"
                + "a.new_tolerance_negative_range,a.new_tolerance_positive_range,a.new_units,a.current_value,"
                + "a.current_weight,a.initial_weight,a.recommented_price,a.initial_tolerance_negative_range,"
                + "a.initial_tolerance_positive_range,a.exe_units,a.initial_value,"
                + "a.rebalance_required,a.rebalance_reqd_date,a.new_allocation,"
                + "a.security_description from portfolio_securities_tb a where a.portfolio_details_id =:portfolioDetId";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
        query.addScalar("portfolio_securities_id", IntegerType.INSTANCE)
                .addScalar("portfolio_id", IntegerType.INSTANCE)
                .addScalar("portfolio_details_id", IntegerType.INSTANCE)
                .addScalar("asset_class_id", ShortType.INSTANCE)
                .addScalar("security_id", StringType.INSTANCE)
                .addScalar("exp_returns", BigDecimalType.INSTANCE)
                .addScalar("std_dev", BigDecimalType.INSTANCE)
                .addScalar("new_weight", BigDecimalType.INSTANCE)
                .addScalar("current_price", BigDecimalType.INSTANCE)
                .addScalar("new_tolerance_negative_range", IntegerType.INSTANCE)
                .addScalar("new_tolerance_positive_range", IntegerType.INSTANCE)
                .addScalar("new_units", IntegerType.INSTANCE)
                .addScalar("current_value", BigDecimalType.INSTANCE)
                .addScalar("current_weight", BigDecimalType.INSTANCE)
                .addScalar("initial_weight", BigDecimalType.INSTANCE)
                .addScalar("recommented_price", BigDecimalType.INSTANCE)
                .addScalar("initial_tolerance_negative_range", IntegerType.INSTANCE)
                .addScalar("initial_tolerance_positive_range", IntegerType.INSTANCE)
                .addScalar("exe_units", BigDecimalType.INSTANCE)
                .addScalar("initial_value", BigDecimalType.INSTANCE)
                .addScalar("rebalance_required", BooleanType.INSTANCE)
                .addScalar("rebalance_reqd_date", TimestampType.INSTANCE)
                .addScalar("new_allocation", BigDecimalType.INSTANCE)
                .addScalar("security_description", StringType.INSTANCE)
                .setInteger("portfolioDetId", portfolioDetId);
        return query.list();
    }

}
