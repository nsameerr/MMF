/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IUploadBenchmarkDAO;
import com.gtl.mmf.entity.BankTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 09860
 */
public class UploadBenchmarkDAOImpl implements IUploadBenchmarkDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveBankDetails(BankTb bankTb) {
        sessionFactory.getCurrentSession().saveOrUpdate(bankTb);
        sessionFactory.getCurrentSession().flush();
    }

    public void saveIfscMappingDetails(List<IfcMicrMappingTb> micrMappingTb) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int count = 0;
        for (IfcMicrMappingTb ifcMicrMappingTb : micrMappingTb) {
//            sessionFactory.getCurrentSession().save(ifcMicrMappingTb);
//            sessionFactory.getCurrentSession().flush();
            session.save(ifcMicrMappingTb);
            if (++count % 50 == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
    }

    public void saveBankTbList(List<BankTb> bankTbList) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int count = 0;
        for (BankTb bankTb : bankTbList) {
            session.save(bankTb);
            if (++count % 50 == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
    }

}
