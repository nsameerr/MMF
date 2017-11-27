/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IClientInvestmentPerformanceDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class ClientInvestmentPerformanceDAOImpl implements IClientInvestmentPerformanceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Object> getCustomerPortfolioTb(Integer customerportfolioId, Integer relationId) {
        List<Object> resultList = new ArrayList<Object>();
        String hql = "FROM CustomerPortfolioTb WHERE customerPortfolioId = :customerPortfolioId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("customerPortfolioId", customerportfolioId);
        resultList.add(query.uniqueResult());

        hql = "FROM CustomerRiskProfileTb "
                + "WHERE customerAdvisorMappingTb.relationId = :relationId";
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("relationId", relationId);
        resultList.add(query.uniqueResult());

        return resultList;
    }

}
