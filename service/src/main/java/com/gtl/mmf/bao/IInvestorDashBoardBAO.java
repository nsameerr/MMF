/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.LineChartDataVO;
import com.gtl.mmf.service.vo.NotificationInvesterListVo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IInvestorDashBoardBAO {

    public NotificationInvesterListVo getInvestorNotifications(int customerId);

    public void getLineChartData(Integer customerId, Integer noOfDaysTobebacked, List<LineChartDataVO> benchMarkLineChartDataVOList,
            List<LineChartDataVO> recommendedLineChartDataVOList, List<LineChartDataVO> selfLineChartDataVOList);
    public Boolean getPortfolioAssigned();
    
    public Double getMaxYavalue();
    
    public String portfolioAssignedDate();
}
