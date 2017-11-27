/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

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
import com.gtl.mmf.entity.SysConfParamTb;
import com.gtl.mmf.entity.TradingaccountTb;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface ILookupDataLoaderDAO {

    public List<CountryTb> loadCountryList();

    public List<QualificationTb> loadQualificationList();

    public List<InstituteTb> loadInstituteList();

    public List<BrockerTb> loadBrockerList();

    public List<SysConfParamTb> loadConfigList();

    public List<Object> getUserStatusList(String userType);

    public List<ClientButtonsTb> getUserButtonsList(String userType, Integer statusCode);

    public List<ContractFreqLookupTb> loadAdvisoryContractFreqLookup(String fieldType);

    public List<ContractPercLookupTb> loadAdvisoryContractPercLookup(String fieldType);

    List<String> loadLinkedInMemId(boolean advisor);

    String getAccessToken(String linkedinMemId);

    List<QuestionOptionMappingTb> loadQuestionnaire();

    List<QuestionoptionsmasterTb> loadOptions();

    List<MasterPortfolioTypeTb> loadPortfolioTypes();

    List<MasterAssetTb> loadAssetClasses();

    List<MasterBenchmarkIndexTb> loadBenchMArk();

    int saveNSEBenchmark(List<BenchmarkPerformanceTb> dataList);
    
    public int saveMasterMidCapBseList(List<MidcapBseTb> dataList);
    
    public int saveMastermidcapNseList(List<MidcapNseTb> dataList);
    
    public int saveMasterSecurityList(List<MasterSecurityClassificationTb> dataList);
    
    public int saveMasterindexNseList(List<IndexNseTb> dataList);
    
    public int saveMasterindexBseList(List<IndexBseTb> dataList);
    
    public List<Object> loadClassificationList();
     
    public List<ResidentStatusTb> loadResidentStatusList();
     
    public List<ProofofIdentityTb> loadProofOfIdentityList();
     
    public List<ProofofAddressTb> loadProofOfAddressList();
     
    public List<DpidsTb> loadDpidsList();
     
    public List<OpenningAccountTypeTb> loadOpenningAccountTypeList();
     
    public List<DemataccountTb> loadDematAccountList();
      
    public List<TradingaccountTb> loadTradingAccountList();
     
    public List<IncomerangeTb> loadIncomerangeList();
     
    public List<OccupationTb> loadOccupationList();
     
    public List<BankSubTypeTb> loadBankSubTypeList();
     
    public List<StateTb> loadStateList();
     
    public List<CitiesTb> loadCityList();

    public List<BankName> loadBankList();

    public List<ExchangeHolidayTb> loadExchangeHoliday();

    public List<MmfSettingsTb> loadAdvancedSettings();

    public List<MasterPortfolioStyleTb> loadPortfolioStyle();
    
    public List<MasterPortfolioSizeTb> loadPortfolioSize();
    
    public List<Object[]> loadStateCityMap();
    
    public int saveIfcMicrMappingTbList(List<IfcMicrMappingTb> dataList);
}
