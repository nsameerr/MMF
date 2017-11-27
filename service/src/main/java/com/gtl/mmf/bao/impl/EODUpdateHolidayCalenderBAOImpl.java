/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IEODUpdateHolidayCalenderBAO;
import com.gtl.mmf.common.EnumAdminNotificationStatus;
import com.gtl.mmf.dao.IEODUpdateHolidayCalenderDAO;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 09860
 */
public class EODUpdateHolidayCalenderBAOImpl implements IEODUpdateHolidayCalenderBAO{

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.EODUpdateHolidayCalenderBAOImpl");
    @Autowired
    private IEODUpdateHolidayCalenderDAO eodUpdateHolidayCalenderDAO;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void notifyAdmin() {
        eodUpdateHolidayCalenderDAO.notifyAdmin(EnumAdminNotificationStatus.UPDATE_HOLIDAY_CALENDER.getValue());
    }
    
}
