/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.bao;

import com.gtl.mmf.entity.BankTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.service.vo.BankVo;
import java.util.List;

/**
 *
 * @author 09860
 */
public interface IUploadBenchmarkBAO {
    
    public void saveBankdetails(List<BankVo> bankVoList);

    public void saveIfscMappingDetails(List<IfcMicrMappingTb> micrMappingTb);

    public void saveBankTbList(List<BankTb> bankTbList);
    
}
