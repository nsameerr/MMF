/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.gtl.mmf.entity.BenchmarkPerformanceTb;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.IndexBseTb;
import com.gtl.mmf.entity.IndexNseTb;
import com.gtl.mmf.entity.MasterSecurityClassificationTb;
import com.gtl.mmf.entity.MidcapBseTb;
import com.gtl.mmf.entity.MidcapNseTb;
import com.gtl.mmf.service.vo.AssetClassVO;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import java.util.LinkedHashMap;

/**
 *
 * @author 07958
 */
public interface ILookupDataLoaderBAO {

    public Map<String, String> loadCountryList();

    public Map<String, String> loadQualificationList();

    public Map<String, String> loadInstitueList();

    public Map<String, String> loadBrockerList();

    public Map<String, String> loadSysConfigParams();

    public Map<Integer, List<ButtonDetailsVo>> loadUserButtons(String userType);

    public Map<String, Integer> loadAdvisoryContractFreqLookup(String fieldType);

    public Map<String, BigDecimal> loadAdvisoryContractPercLookup(String fieldType);

    List<String> loadLinkedInMemId(boolean advisor);

    String getAccessToken(String linkedinMemId);

    Map<Integer, QuestionnaireVO> loadQuestionnaire();

    Map<Integer, PortfolioTypeVO> loadPortFolioTypes();

    LinkedHashMap<String, Short> loadAssetClasses();

    Map<Integer, String> loadBenchMArk();

    //String sayHello();
    int saveNSEBenchmark(List<BenchmarkPerformanceTb> dataList);

    public int saveMasterSecurityList(List<MasterSecurityClassificationTb> dataList);

    public int saveMasterMidCapBseList(List<MidcapBseTb> dataList);

    public int saveMasterMidCapNseList(List<MidcapNseTb> dataList);

    public int saveMasterIndexNseList(List<IndexNseTb> dataList);

    public int saveMasterIndexBseList(List<IndexBseTb> dataList);

    List<AssetClassVO> loadAssetClassVOs();

    List<Map<String, String>> loadMFMap();

    public Map<String, Integer> loadResidentStatusList();

    public Map<String, Integer> loadProofOfIdentityList();

    public LinkedHashMap<String, Integer> loadProofOfAddressList();

    public Map<String, Integer> loadDpidsList();

    public Map<String, Integer> loadOpenningAccountTypeList();

    public Map<String, Integer> loadDematAccountList();

    public Map<String, Integer> loadTradingAccountList();

    public LinkedHashMap<String, Integer> loadIncomerangeList();

    public Map<String, Integer> loadOccupationList();

    public Map<String, Integer> loadBankSubTypeList();

    public Map<String, String> loadStateList();

    public Map<String, String> loadCityList();

    public Map<Integer, String> loadBankList();

    public List<Object> loadAllCity();

    public List<ExchangeHolidayTb> loadHolidayMap();

    public LinkedHashMap<String, String> loadAdvancedSettings();

    public Map<String, List<String>> loadStateCityMap();

    public Map<Integer, PortfolioTypeVO> loadPortFolioStyle();

    public Map<Integer, PortfolioTypeVO> loadPortFolioSize();
    
    public int saveIfcMicrMappingTbList(List<IfcMicrMappingTb> dataList);
}
