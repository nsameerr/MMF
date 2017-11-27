/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.ClientDetailsVO;
import com.gtl.mmf.service.vo.ClientPerformanceVO;
import com.gtl.mmf.service.vo.NotificationAdvisorListVO;
import com.gtl.mmf.service.vo.PortfolioPerformanceVO;
import com.gtl.mmf.service.vo.PortfolioRebalanceTriggerVO;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAdvisorDashBoardBAO {

    public List<ClientDetailsVO> getClientStatusDetails(int advisorId, String accessToken);

    public NotificationAdvisorListVO getAdvisorNotifications(int advisorId);
    
    public void getDashboardDatas(int advisorId, List<ClientPerformanceVO> clientPerformanceVOs,
            List<PortfolioPerformanceVO> portfolioPerformanceVOs, List<PortfolioRebalanceTriggerVO> portfoliosForRebalance);
}
