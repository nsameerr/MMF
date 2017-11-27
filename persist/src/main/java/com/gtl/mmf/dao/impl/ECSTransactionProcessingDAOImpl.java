/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IECSTransactionProcessingDAO;
import com.gtl.mmf.entity.EcsDebtPaymentFileContentTb;
import com.gtl.mmf.entity.EcsPaymentStatusFileTb;
import com.gtl.mmf.entity.EcsRegistrationFiledataTb;
import com.gtl.mmf.entity.EcsTransmittalSheetTb;
import com.gtl.mmf.persist.vo.EcsDebtFileReplyVo;
import com.gtl.mmf.util.StackTraceWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author trainee8
 */
public class ECSTransactionProcessingDAOImpl implements IECSTransactionProcessingDAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.ECSTransactionProcessingDAOImpl");
    @Autowired
    private SessionFactory sessionFactory;
    private static final int BATCH_SIZE = 20;
    private static final Integer ONE = 1;
    private static final Integer TWO = 2;

    public void createUserListforMandateEcs(String regNums) {
        String hql = "UPDATE master_applicant_tb SET ecs_mandate_status = :statusVerified"
                + " WHERE registration_id " + regNums;
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
        query.setInteger("statusVerified", ONE);
        query.executeUpdate();

        String hql1 = "UPDATE ecs_transmittal_sheet_tb SET verified "
                + " = " + true + " WHERE registration_id " + regNums;
        SQLQuery query1 = sessionFactory.getCurrentSession().createSQLQuery(hql1);
        query1.executeUpdate();

        sessionFactory.getCurrentSession().flush();

    }

    public List<Object> createUserListforDebtPay() {

        List<Object> responeList = new ArrayList<Object>();
        try {
            String hql = "From CustomerManagementFeeTb WHERE ecsPaid =:Status AND "
                    + "submittedEcs =:Submitted ";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setBoolean("Status", false);
            query.setBoolean("Submitted", false);
            responeList.add(query.list());

            String sql = "From CustomerPerformanceFeeTb WHERE ecsPaid =:Status AND"
                    + " submittedEcs =:Submitted ";
            Query sQuery = sessionFactory.getCurrentSession().createQuery(sql);
            sQuery.setBoolean("Status", false);
            sQuery.setBoolean("Submitted", false);
            responeList.add(sQuery.list());
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        }
        return responeList;
    }

    public int insertNewEcsRegistrationFiledata(EcsRegistrationFiledataTb ecsRegistrationFiledata) {
        return (Integer) sessionFactory.getCurrentSession().save(ecsRegistrationFiledata);
    }

    public void deleteDataFromEcsRegistrationData() {
        String hql = "DELETE FROM ecs_registration_filedata_tb WHERE "
                + " DATE_FORMAT(ecsSendDate,'%Y-%m-%d') <= DATE_SUB(DATE_FORMAT"
                + "(CURRENT_DATE,'%Y-%m-%d'),INTERVAL 5 DAY)";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
        query.executeUpdate();
    }

    public void insertEcsDebtFiledata(List<EcsDebtPaymentFileContentTb> ecsDebtPaymentFileContent) {
        int count_mode = 0;
        for (EcsDebtPaymentFileContentTb ecsDebtPayment : ecsDebtPaymentFileContent) {
            count_mode++;
            Integer count = (Integer) sessionFactory.getCurrentSession().save(ecsDebtPayment);
            if (ecsDebtPayment.getMfee() != null && ecsDebtPayment.getMfee()) {
                String hql = "UPDATE customer_management_fee_tb SET submitted_ecs "
                        + " = " + true + " WHERE  DATE_FORMAT(fee_calculate_date,'%Y-%m-%d') =DATE_FORMAT(:feeDate,'%Y-%m-%d')"
                        + " and reg_id=:regId";
                Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
                query.setDate("feeDate", ecsDebtPayment.getFeeCalculatedate());
                query.setString("regId", ecsDebtPayment.getRegId());
                query.executeUpdate();
            }
            if (ecsDebtPayment.getPfee() != null && ecsDebtPayment.getPfee()) {
                String hql = "UPDATE customer_performance_fee_tb SET submitted_ecs "
                        + " = " + true + " WHERE  DATE_FORMAT(fee_calculate_date,'%Y-%m-%d') =DATE_FORMAT(:feeDate,'%Y-%m-%d')"
                        + " and reg_id=:regId";
                Query query = sessionFactory.getCurrentSession().createSQLQuery(hql);
                query.setDate("feeDate", ecsDebtPayment.getFeeCalculatedate());
                query.setString("regId", ecsDebtPayment.getRegId());
                query.executeUpdate();
            }
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        sessionFactory.getCurrentSession().flush();
    }

    public void updateEcsDebtFiledata(List<EcsDebtFileReplyVo> ecsDebtPaymentFileContent, Date dueDate) {

        for (EcsDebtFileReplyVo ecsDebtFileReplyVo : ecsDebtPaymentFileContent) {
            String sql = "CALL updateEcsDebtPaymentDetails(:RegId,:DueDate,:DebtAmount,:Status,:FailureReason,:NewDueDate,:feeCalculateDate)";
            SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
            sqlQuery.setParameter("RegId", ecsDebtFileReplyVo.getRegId());
            sqlQuery.setParameter("DueDate", ecsDebtFileReplyVo.getDueDate());
            sqlQuery.setParameter("DebtAmount", ecsDebtFileReplyVo.getDebtAmountFee());
            sqlQuery.setParameter("Status", ecsDebtFileReplyVo.getStatus());
            sqlQuery.setParameter("FailureReason", ecsDebtFileReplyVo.getFailureReason());
            sqlQuery.setParameter("NewDueDate", dueDate);
            sqlQuery.setParameter("feeCalculateDate", ecsDebtFileReplyVo.getFeeCalculateDate());
            sqlQuery.executeUpdate();
        }
    }

    public List<EcsDebtPaymentFileContentTb> getDebtFileContent(String reg_id, Date dueDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        String format = sdf.format(cal.getTime());
        String hql = "FROM EcsDebtPaymentFileContentTb WHERE regId = :customerId AND dueDate >'" + format + "'";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("customerId", reg_id);
        return query.list();
    }

    public List<Object[]> createMandateVerificationList(Date date) {
        String sql = "SELECT T1.*,T2.`beneficiary_name` FROM `mandate_form_tb` T1,`master_applicant_tb` T2,`master_customer_tb` T3"
                + " WHERE T1.`registration_id`= T2.`registration_id` AND T1.`registration_id` = T3.`registration_id` AND"
                + " (T3.`accepted` = " + true + " OR T3.`rejection_Resolved` = " + true + ") AND T3.`ecs_send_or_resend` = " + false;
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        return query.list();
    }

    public void saveTransmittalData(List<EcsTransmittalSheetTb> sheetTbs) {
        for (EcsTransmittalSheetTb ecsTransmittalSheetTb : sheetTbs) {

            String hql = "UPDATE master_customer_tb SET ecs_send_or_resend "
                    + " = " + true + " WHERE registration_id = :regId";
            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql);
            query.setString("regId", ecsTransmittalSheetTb.getRegistrationId());
            query.executeUpdate();

            sessionFactory.getCurrentSession().save(ecsTransmittalSheetTb);
        }
    }

    public void updateTransmittalSheetTableContent(String regnumRemarkList) {
        String[] list = regnumRemarkList.split(",");
        int count_mode = 0;
        for (String item : list) {
            count_mode++;
            String[] data = item.split(":");
            String hql1 = "UPDATE ecs_transmittal_sheet_tb SET rejected "
                    + " = " + true + ",remark = :remark "
                    + "WHERE registration_id = :regId";
            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql1);
            query.setString("regId", data[0]);
            query.setString("remark", data[1]);
            query.executeUpdate();

            String hql = "UPDATE master_applicant_tb SET ecs_mandate_status = :statusRejected"
                    + " WHERE registration_id = :regId";
            query = sessionFactory.getCurrentSession().createSQLQuery(hql);
            query.setInteger("statusRejected", TWO);
            query.setString("regId", data[0]);
            query.executeUpdate();
            
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        
    }

    public void updateDebitRequestNumber(Integer debtRqstNmbrLastUsed, String text) {
        String hql1 = "UPDATE `sys_conf_param_tb` SET `value` = :reqestNumber"
                + " WHERE `name` = :searchText";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(hql1);
        query.setString("reqestNumber", debtRqstNmbrLastUsed.toString());
        query.setString("searchText", text);
        query.executeUpdate();
    }

    public void saveEcsPaymentStatusFileTbs(List<EcsPaymentStatusFileTb> ecsPaymentStatusFileTbs) {
        int count_mode = 0;
        for (EcsPaymentStatusFileTb ecsPaymentStatusFileTb : ecsPaymentStatusFileTbs) {
            count_mode ++;
            sessionFactory.getCurrentSession().save(ecsPaymentStatusFileTb);
            
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
            }
        }
    }

}
