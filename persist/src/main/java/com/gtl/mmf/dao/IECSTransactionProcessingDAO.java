/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.EcsDebtPaymentFileContentTb;
import com.gtl.mmf.entity.EcsPaymentStatusFileTb;
import com.gtl.mmf.entity.EcsRegistrationFiledataTb;
import com.gtl.mmf.entity.EcsTransmittalSheetTb;
import com.gtl.mmf.persist.vo.EcsDebtFileReplyVo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author trainee8
 */
public interface IECSTransactionProcessingDAO {

    public void createUserListforMandateEcs(String regNums);

    public List<Object> createUserListforDebtPay();

    public int insertNewEcsRegistrationFiledata(EcsRegistrationFiledataTb ecsRegistrationFiledata);

    public void deleteDataFromEcsRegistrationData();

    public void insertEcsDebtFiledata(List<EcsDebtPaymentFileContentTb> ecsDebtPaymentFileContent);

    public void updateEcsDebtFiledata(List<EcsDebtFileReplyVo> ecsDebtPaymentFileContent, Date dueDate);

    public List<EcsDebtPaymentFileContentTb> getDebtFileContent(String reg_id,Date dueDate);

    public List<Object[]> createMandateVerificationList(Date date);

    public void saveTransmittalData(List<EcsTransmittalSheetTb> sheetTbs);

    public void updateTransmittalSheetTableContent(String regnumRemarkList);

    public void updateDebitRequestNumber(Integer debtRqstNmbrLastUsed,String text);

    public void saveEcsPaymentStatusFileTbs(List<EcsPaymentStatusFileTb> ecsPaymentStatusFileTbs);
}
