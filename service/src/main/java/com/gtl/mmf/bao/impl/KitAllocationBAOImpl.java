/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IKitAllocationBAO;
import com.gtl.mmf.dao.IKitAllocationDAO;
import com.gtl.mmf.entity.KitAllocationTb;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.CLOSED;
import static com.gtl.mmf.service.util.IConstants.IN_USE;
import static com.gtl.mmf.service.util.IConstants.NOT_ALLOCATED;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.dd_MM_yyyy;
import com.gtl.mmf.service.vo.RegKitVo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 09860
 */
@Service
public class KitAllocationBAOImpl implements IKitAllocationBAO {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.KitAllocationBAOImpl");

    @Autowired
    IKitAllocationDAO iKitAllocationDAOImpl;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<RegKitVo> getClientRegistrationKitDetails() {
        List<RegKitVo> allocationList = new ArrayList<RegKitVo>();
        List<Object> responseList = iKitAllocationDAOImpl.getRegistrationKitDetails();
        List<KitAllocationTb> kitAllocationTbList = (List<KitAllocationTb>) responseList.get(ZERO);
        Integer lastKit = (Integer) responseList.get(ONE);
        RegKitVo regKitVo;
        for (KitAllocationTb item : kitAllocationTbList) {
            regKitVo = new RegKitVo();
            regKitVo.setFrom(item.getFromValue());
            regKitVo.setTo(item.getToValue());
            regKitVo.setAllocationDate(item.getAllocationDate().toString());
            regKitVo.setStatus(item.getKitStatus());
            if (item.getKitStatus().equalsIgnoreCase(IN_USE)) {
                regKitVo.setStyleClass("green align_center");
                regKitVo.setBalance(lastKit > ZERO ? item.getToValue() - lastKit :
                        item.getToValue() - item.getFromValue() + 1); 
            } else if (item.getKitStatus().equalsIgnoreCase(NOT_ALLOCATED)) {
                regKitVo.setStyleClass("grey align_center");
                regKitVo.setBalance(item.getToValue() - item.getFromValue() + 1); 
            } else if (item.getKitStatus().equalsIgnoreCase(CLOSED)) {
                regKitVo.setStyleClass("red align_center");
                regKitVo.setBalance(ZERO); 
            }
            allocationList.add(regKitVo);
        }
        return allocationList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public RegKitVo saveAllocationRange(Integer startValue, Integer endValue) {
        RegKitVo regKitVo = new RegKitVo();
        regKitVo.setFrom(startValue);
        regKitVo.setTo(endValue);
        regKitVo.setAllocationDate(DateUtil.dateToString(new Date(), dd_MM_yyyy));
        regKitVo.setBalance(endValue - startValue + 1); 
        regKitVo.setStatus(NOT_ALLOCATED);
        KitAllocationTb kitAllocationTb = regKitVo.toKitAllocationTb();
        int count = iKitAllocationDAOImpl.saveNewAllocation(kitAllocationTb);
        if (count == 0) {
            regKitVo.setStatus(IN_USE);
            regKitVo.setStyleClass("green align_center");
        } else {
            regKitVo.setStyleClass("grey align_center");
        }
        return regKitVo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean checkIfNewAllocation(Integer startValue, Integer endValue) {
        return iKitAllocationDAOImpl.checkNumberExistence(startValue, endValue);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateAdminNotificationStatus(Integer notificationStatusCode) {
        iKitAllocationDAOImpl.updateNotificationStatus(notificationStatusCode);
    }

}
