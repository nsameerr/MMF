/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.InvestorDetailsVO;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAdvisorNetworkViewBAO {

    public List<InvestorDetailsVO> populateInvestorNetwork(int advisorId, String accesToken, String linkedInId, boolean linkedInConnected);

    public boolean CheckStatusFlag(Integer customerId);

}
