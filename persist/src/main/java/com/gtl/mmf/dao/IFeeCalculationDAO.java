/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.Advisorbucketwisepayouts;
import com.gtl.mmf.entity.Tieredpromocommissionmatrix;
import com.gtl.mmf.entity.Tiers;
import com.gtl.mmf.entity.Totaladvisorpayout;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author 09607
 */
public interface IFeeCalculationDAO {

    public List<Tiers> getTiersTB();

    public List<Tieredpromocommissionmatrix> getTieredpromocommissionmatrixTB();

    public List<Object> createUserListforDebtPay(String retions);

    public void saveAdvisorFeeDetails(HashMap<String, Advisorbucketwisepayouts> feeMap);

    public void saveAdvisorTotalPayOut(HashMap<Integer, Totaladvisorpayout> feeMap);
	

}
