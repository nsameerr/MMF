/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.ILookupDataLoaderDAO;
import com.gtl.mmf.entity.BankName;
import com.gtl.mmf.entity.BankSubTypeTb;
import com.gtl.mmf.entity.BenchmarkPerformanceTb;
import com.gtl.mmf.entity.BrockerTb;
import com.gtl.mmf.entity.CitiesTb;
import com.gtl.mmf.entity.ClientButtonsTb;
import com.gtl.mmf.entity.ContractFreqLookupTb;
import com.gtl.mmf.entity.ContractPercLookupTb;
import com.gtl.mmf.entity.CountryTb;
import com.gtl.mmf.entity.DemataccountTb;
import com.gtl.mmf.entity.DpidsTb;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.IncomerangeTb;
import com.gtl.mmf.entity.IndexBseTb;
import com.gtl.mmf.entity.IndexNseTb;
import com.gtl.mmf.entity.InstituteTb;
import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.MasterBenchmarkIndexTb;
import com.gtl.mmf.entity.MasterPortfolioSizeTb;
import com.gtl.mmf.entity.MasterPortfolioStyleTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.MasterSecurityClassificationTb;
import com.gtl.mmf.entity.MidcapBseTb;
import com.gtl.mmf.entity.MidcapNseTb;
import com.gtl.mmf.entity.MmfSettingsTb;
import com.gtl.mmf.entity.OccupationTb;
import com.gtl.mmf.entity.OpenningAccountTypeTb;
import com.gtl.mmf.entity.ProofofAddressTb;
import com.gtl.mmf.entity.ProofofIdentityTb;
import com.gtl.mmf.entity.QualificationTb;
import com.gtl.mmf.entity.QuestionOptionMappingTb;
import com.gtl.mmf.entity.QuestionoptionsmasterTb;
import com.gtl.mmf.entity.ResidentStatusTb;
import com.gtl.mmf.entity.StateTb;
import com.gtl.mmf.entity.TradingaccountTb;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 07958
 */
@Repository
public class LookupDataLoaderDAOImpl implements ILookupDataLoaderDAO {

    private static final int BATCH_SIZE = 20;
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<CountryTb> loadCountryList() {
        return sessionFactory.getCurrentSession().createQuery("FROM CountryTb ORDER BY countryName ASC").list();
    }

    public List<CitiesTb> loadCityList() {
        return sessionFactory.getCurrentSession().createQuery("FROM CitiesTb ORDER BY cityName ASC").list();
    }

    public List<QualificationTb> loadQualificationList() {
        return sessionFactory.getCurrentSession().createQuery("FROM QualificationTb ORDER BY qualification").list();
    }

    public List<InstituteTb> loadInstituteList() {
        return sessionFactory.getCurrentSession().createQuery("FROM InstituteTb ORDER BY institute").list();
    }

    public List<BrockerTb> loadBrockerList() {
        return sessionFactory.getCurrentSession().createQuery("FROM BrockerTb ORDER BY name").list();
    }

    public List loadConfigList() {
        return sessionFactory.getCurrentSession().createQuery("From SysConfParamTb").list();
    }

    public List<Object> getUserStatusList(String userType) {
        String hql = "SELECT DISTINCT statusCode FROM ClientButtonsTb WHERE userType=:UserType ORDER BY statusCode";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("UserType", userType);
        return query.list();
    }

    public List<ClientButtonsTb> getUserButtonsList(String userType, Integer statusCode) {
        String hql = "FROM ClientButtonsTb WHERE userType=:UserType AND statusCode =:StatusCode";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("UserType", userType);
        query.setInteger("StatusCode", statusCode);
        return query.list();
    }

    public List<ContractFreqLookupTb> loadAdvisoryContractFreqLookup(String fieldType) {
        String hql = "FROM ContractFreqLookupTb WHERE fieldType=:FieldType ORDER BY id ASC";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("FieldType", fieldType);
        return query.list();
    }

    public List<ContractPercLookupTb> loadAdvisoryContractPercLookup(String fieldType) {
        String hql = "FROM ContractPercLookupTb WHERE fieldType=:FieldType";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("FieldType", fieldType);
        return query.list();
    }

    public List<String> loadLinkedInMemId(boolean advisor) {
        String hql = " SELECT DISTINCT linkedinMemberId FROM MasterApplicantTb"
                + " WHERE advisor=:advisor AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("advisor", advisor);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public String getAccessToken(String linkedinMemId) {
        String accessToken = null;
        String hql = " SELECT DISTINCT linkedinPassword  FROM MasterApplicantTb"
                + " WHERE linkedinMemberId=:linkedinMemberId AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("linkedinMemberId", linkedinMemId);
        query.setBoolean("Active", Boolean.TRUE);
        List<String> list = query.list();
        if (list != null) {
            accessToken = list.get(0);
        }
        return accessToken;
    }

    public List<QuestionOptionMappingTb> loadQuestionnaire() {
        String hql = "FROM QuestionOptionMappingTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    public List<QuestionoptionsmasterTb> loadOptions() {
        String hql = "FROM QuestionoptionsmasterTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    public List<MasterPortfolioTypeTb> loadPortfolioTypes() {
        String hql = "FROM MasterPortfolioTypeTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    public List<MasterAssetTb> loadAssetClasses() {
        String hql = "FROM MasterAssetTb ORDER BY assetOrder";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    public List<MasterBenchmarkIndexTb> loadBenchMArk() {
        String hql = "FROM MasterBenchmarkIndexTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    public int saveNSEBenchmark(List<BenchmarkPerformanceTb> dataList) {
        int count_mode = 0;
        sessionFactory.getCurrentSession().createSQLQuery("truncate table benchmark_performance_tb").executeUpdate();
        for (BenchmarkPerformanceTb performanceTb : dataList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(performanceTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
        sessionFactory.getCurrentSession().flush();
        return count_mode;
    }

    public int saveMasterSecurityList(List<MasterSecurityClassificationTb> dataList) {
        int count_mode = 0;
        sessionFactory.getCurrentSession().createSQLQuery("truncate table master_security_classification_tb").executeUpdate();
        for (MasterSecurityClassificationTb securityClassificationTb : dataList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(securityClassificationTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
        sessionFactory.getCurrentSession().flush();
        return count_mode;
    }

    public List<Object> loadClassificationList() {
        List<Object> responseItems = new ArrayList<Object>();
        String hql = "SELECT  isinDivPayout,mmfClassification FROM MasterSecurityClassificationTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        responseItems.add(query.list());

        String indexNsehql = "FROM IndexNseTb";
        Query indexNsequery = sessionFactory.getCurrentSession().createQuery(indexNsehql);
        responseItems.add(indexNsequery.list());

        String indexBsehql = "FROM IndexBseTb";
        Query indexBsequery = sessionFactory.getCurrentSession().createQuery(indexBsehql);
        responseItems.add(indexBsequery.list());

        String midCapBsehql = "FROM MidcapBseTb";
        Query midcapBsequery = sessionFactory.getCurrentSession().createQuery(midCapBsehql);
        responseItems.add(midcapBsequery.list());

        String midcapNsehql = "FROM MidcapNseTb";
        Query midcapNsequery = sessionFactory.getCurrentSession().createQuery(midcapNsehql);
        responseItems.add(midcapNsequery.list());
        return responseItems;
    }

    public int saveMasterMidCapBseList(List<MidcapBseTb> dataList) {
        int count_mode = 0;
        sessionFactory.getCurrentSession().createSQLQuery("truncate table midcap_bse_tb").executeUpdate();
        for (MidcapBseTb midcapBseTb : dataList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(midcapBseTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
        sessionFactory.getCurrentSession().flush();
        return count_mode;

    }

    public int saveMastermidcapNseList(List<MidcapNseTb> dataList) {
        int count_mode = 0;
        sessionFactory.getCurrentSession().createSQLQuery("truncate table midcap_nse_tb").executeUpdate();
        for (MidcapNseTb midcapNseTb : dataList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(midcapNseTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
        sessionFactory.getCurrentSession().flush();
        return count_mode;

    }

    public int saveMasterindexNseList(List<IndexNseTb> dataList) {
        int count_mode = 0;
        sessionFactory.getCurrentSession().createSQLQuery("truncate table index_nse_tb").executeUpdate();
        for (IndexNseTb indexNseTb : dataList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(indexNseTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
        sessionFactory.getCurrentSession().flush();
        return count_mode;
    }

    public int saveMasterindexBseList(List<IndexBseTb> dataList) {
        int count_mode = 0;
        sessionFactory.getCurrentSession().createSQLQuery("truncate table index_bse_tb").executeUpdate();
        for (IndexBseTb indexBseTb : dataList) {
            count_mode++;
            sessionFactory.getCurrentSession().save(indexBseTb);
            if (count_mode % BATCH_SIZE == 0) {
                sessionFactory.getCurrentSession().flush();
                sessionFactory.getCurrentSession().clear();
            }
        }
        sessionFactory.getCurrentSession().flush();
        return count_mode;
    }

    public List<ResidentStatusTb> loadResidentStatusList() {
        return sessionFactory.getCurrentSession().createQuery("FROM ResidentStatusTb ORDER BY key").list();
    }

    public List<ProofofIdentityTb> loadProofOfIdentityList() {
        return sessionFactory.getCurrentSession().createQuery("FROM ProofofIdentityTb ORDER BY key").list();
    }

    public List<ProofofAddressTb> loadProofOfAddressList() {
        return sessionFactory.getCurrentSession().createQuery("FROM ProofofAddressTb ORDER BY proofofAddress ASC").list();
    }

    public List<DpidsTb> loadDpidsList() {
        return sessionFactory.getCurrentSession().createQuery("FROM DpidsTb ORDER BY key").list();
    }

    public List<OpenningAccountTypeTb> loadOpenningAccountTypeList() {
        return sessionFactory.getCurrentSession().createQuery("FROM OpenningAccountTypeTb ORDER BY key").list();
    }

    public List<DemataccountTb> loadDematAccountList() {
        return sessionFactory.getCurrentSession().createQuery("FROM DemataccountTb ORDER BY key").list();
    }

    public List<TradingaccountTb> loadTradingAccountList() {
        return sessionFactory.getCurrentSession().createQuery("FROM TradingaccountTb ORDER BY key").list();
    }

    public List<IncomerangeTb> loadIncomerangeList() {
        return sessionFactory.getCurrentSession().createQuery("FROM IncomerangeTb ORDER BY key").list();
    }

    public List<OccupationTb> loadOccupationList() {
        return sessionFactory.getCurrentSession().createQuery("FROM OccupationTb ORDER BY key DESC").list();
    }

    public List<BankSubTypeTb> loadBankSubTypeList() {
        return sessionFactory.getCurrentSession().createQuery("FROM BankSubTypeTb ORDER BY key").list();
    }

    public List<StateTb> loadStateList() {
        return sessionFactory.getCurrentSession().createQuery("FROM StateTb ORDER BY stateName ASC").list();
    }

    public List<BankName> loadBankList() {
        return sessionFactory.getCurrentSession().createQuery("FROM BankName ORDER BY bankName ASC").list();
    }

    public List<ExchangeHolidayTb> loadExchangeHoliday() {
        return sessionFactory.getCurrentSession().createQuery("FROM ExchangeHolidayTb ORDER BY hdate ASC").list();
    }

    public List<MmfSettingsTb> loadAdvancedSettings() {
        return sessionFactory.getCurrentSession().createQuery("FROM MmfSettingsTb").list();
    }
    
    public List<Object[]> loadStateCityMap() {
        return sessionFactory.getCurrentSession().createSQLQuery("SELECT state,city FROM bank_tb GROUP BY state,city").list();
    }
	
	public List<MasterPortfolioStyleTb> loadPortfolioStyle() {
        String hql = "FROM MasterPortfolioStyleTb";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    public List<MasterPortfolioSizeTb> loadPortfolioSize() {
        String hql = "FROM MasterPortfolioSizeTb ORDER BY minAum DESC";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }
    
    public int saveIfcMicrMappingTbList(List<IfcMicrMappingTb> dataList){
         Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        int count = 0;
        for (IfcMicrMappingTb ifcMicrMappingTb : dataList) {
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
        return count;
    }
}
