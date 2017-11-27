/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.RegKitVo;
import java.util.List;

/**
 *
 * @author 09860
 */
public interface IKitAllocationBAO {

    public List<RegKitVo> getClientRegistrationKitDetails();

    public RegKitVo saveAllocationRange(Integer startValue, Integer endValue);

    public boolean checkIfNewAllocation(Integer startValue, Integer endValue);

    public void updateAdminNotificationStatus(Integer notificationStatusCode);
    
}
