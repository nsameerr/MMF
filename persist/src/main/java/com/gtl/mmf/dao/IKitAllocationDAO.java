/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.dao;

import com.gtl.mmf.entity.KitAllocationTb;
import java.util.List;

/**
 *
 * @author 09860
 */
public interface IKitAllocationDAO {

    public List<Object> getRegistrationKitDetails();

    public int saveNewAllocation(KitAllocationTb kitAllocationTb);

    public boolean checkNumberExistence(Integer startValue, Integer endValue);

    public void updateNotificationStatus(Integer notificationStatusCode);
    
}
