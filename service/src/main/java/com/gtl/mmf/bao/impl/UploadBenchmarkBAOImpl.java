/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IUploadBenchmarkBAO;
import com.gtl.mmf.dao.IUploadBenchmarkDAO;
import com.gtl.mmf.entity.BankTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.vo.BankVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 09860
 */
public class UploadBenchmarkBAOImpl implements IUploadBenchmarkBAO, IConstants {
    
    @Autowired
    private IUploadBenchmarkDAO uploadBenchmarkDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveBankdetails(List<BankVo> bankVoList) {
        for (BankVo bankVo : bankVoList) {
            BankTb bankTb = new BankTb();
            bankTb.setBank(bankVo.getBank());
            bankTb.setIfsc(bankVo.getIfsc());
            bankTb.setBranch(bankVo.getBranch());
            bankTb.setAddress(bankVo.getAddress());
            bankTb.setContact(bankVo.getContact());
            bankTb.setCity(bankVo.getCity());
            bankTb.setDistrict(bankVo.getDistrict());
            bankTb.setState(bankVo.getState());
//            bankTb.setPincode(bankVo.getPincode());
            uploadBenchmarkDAO.saveBankDetails(bankTb);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveIfscMappingDetails(List<IfcMicrMappingTb> micrMappingTb) {
        uploadBenchmarkDAO.saveIfscMappingDetails(micrMappingTb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveBankTbList(List<BankTb> bankTbList) {
        uploadBenchmarkDAO.saveBankTbList(bankTbList);
    }

}
