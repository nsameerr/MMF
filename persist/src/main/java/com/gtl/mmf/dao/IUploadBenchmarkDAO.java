/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.dao;

import com.gtl.mmf.entity.BankTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import java.util.List;

/**
 *
 * @author 09860
 */
public interface IUploadBenchmarkDAO {

    public void saveBankDetails(BankTb bankTb);

    public void saveIfscMappingDetails(List<IfcMicrMappingTb> micrMappingTb);

    public void saveBankTbList(List<BankTb> bankTbList);
    
}
