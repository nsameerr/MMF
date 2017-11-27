/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IInvestorNetworkViewBAO {

    public List<AdvisorDetailsVO> allAdvisors(int customerId, String accessToken,boolean linkedInConnected);
}
