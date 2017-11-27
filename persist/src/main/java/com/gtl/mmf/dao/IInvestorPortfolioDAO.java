/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * creted by 07662
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.List;

public interface IInvestorPortfolioDAO {

    public List<Object> getViewPortfolioPageDetails(CustomerPortfolioTb customerPortfolioTb);

}
