/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IInvestorPortfolioDAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestorPortfolioDAOImpl implements IInvestorPortfolioDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Object> getViewPortfolioPageDetails(CustomerPortfolioTb customerPortfolioTb) {
        List<Object> responseItems = new ArrayList<Object>();

        String hql = "SELECT T1 FROM CustomerPortfolioTb AS T1"
                + " WHERE T1.customerAdvisorMappingTb =:CustomerAdvisorMappingTb";
        Query hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
        hqlQuery.setEntity("CustomerAdvisorMappingTb", customerPortfolioTb.getCustomerAdvisorMappingTb());
        customerPortfolioTb = (CustomerPortfolioTb) hqlQuery.uniqueResult();
        responseItems.add(customerPortfolioTb);
		if (customerPortfolioTb != null) {
			hql = "SELECT T1 FROM CustomerPortfolioDetailsTb AS T1"
					+ " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb";
			hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
			hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioTb);
			responseItems.add(hqlQuery.list());

			hql = "SELECT T1 FROM CustomerPortfolioSecuritiesTb AS T1"
					+ " WHERE T1.customerPortfolioTb =:CustomerPortfolioTb";
			hqlQuery = sessionFactory.getCurrentSession().createQuery(hql);
			hqlQuery.setEntity("CustomerPortfolioTb", customerPortfolioTb);
			responseItems.add(hqlQuery.list());
		}
        return responseItems;
    }
}
