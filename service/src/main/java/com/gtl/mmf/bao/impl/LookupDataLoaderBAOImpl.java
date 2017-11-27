/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.ILookupDataLoaderBAO;
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
import com.gtl.mmf.entity.SysConfParamTb;
import com.gtl.mmf.entity.TradingaccountTb;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.vo.AssetClassVO;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.OptionValuesVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
@Service
public class LookupDataLoaderBAOImpl implements ILookupDataLoaderBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.LookupDataLoaderBAOImpl");
    private static final String OPTION = "option";
    private static final String GET = "get";
    private static boolean omsLogin = true;
    @Autowired
    private ILookupDataLoaderDAO lookupDataLoaderDAO;
    private Map<String, String> countryList;
    private Map<String, String> cityList;
    private Map<String, String> qualificationList;
    private Map<String, String> instituteList;
    private Map<String, String> brockerList;
    private Map<String, String> sysConfigList;
    private Map<String, Integer> advisoryServiceContractLookup;
    private Map<String, BigDecimal> advisoryServiceContractPercLookup;
    Map<Integer, List<ButtonDetailsVo>> userButtonsList;
    private Map<String, String> mfDataMap;
    private Map<String, String> indexBseMap;
    private Map<String, String> indexNseMap;
    private Map<String, String> midcapBseMap;
    private Map<String, String> midcapNseMap;
    private Map<String, String> stateList;
    private Map<Integer, String> bankList;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> loadBrockerList() {
        LOGGER.info("Loading brockers list.");
        brockerList = new LinkedHashMap<String, String>();
        List<BrockerTb> brockerListTb = lookupDataLoaderDAO.loadBrockerList();
        for (BrockerTb brocker : brockerListTb) {
            String brockerId = brocker.getId().toString();
            String brockerName = brocker.getName();
            brockerList.put(brockerName, brockerId);
        }
        return brockerList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> loadCountryList() {
        LOGGER.info("Loading countries list.");
        countryList = new LinkedHashMap<String, String>();
        List<CountryTb> countryTbList = lookupDataLoaderDAO.loadCountryList();
        for (CountryTb countryListItem : countryTbList) {
            String countryCode = countryListItem.getCountryCode();
            String countryName = countryListItem.getCountryName();
            countryList.put(countryCode, countryName);
        }
        return countryList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> loadCityList() {
        LOGGER.info("Loading Cites list.");
        cityList = new LinkedHashMap<String, String>();
        List<CitiesTb> cityTbList = lookupDataLoaderDAO.loadCityList();
        for (CitiesTb cityListItem : cityTbList) {
            String countryCode = cityListItem.getCityName();
            String countryName = cityListItem.getCityName();
            cityList.put(countryCode, countryName);
        }
        return cityList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> loadQualificationList() {
        LOGGER.info("Loading qualifications list.");
        qualificationList = new LinkedHashMap<String, String>();
        List<QualificationTb> qualificationTbList = lookupDataLoaderDAO.loadQualificationList();
        for (QualificationTb qualificationItem : qualificationTbList) {
            String id = Integer.valueOf((qualificationItem.getId())).toString();
            String qualification = qualificationItem.getQualification();
            qualificationList.put(qualification, id);
        }
        return qualificationList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> loadInstitueList() {
        LOGGER.info("Loading institutions list.");
        instituteList = new LinkedHashMap<String, String>();
        List<InstituteTb> instituteTbList = lookupDataLoaderDAO.loadInstituteList();
        for (InstituteTb instituteItem : instituteTbList) {
            String id = (instituteItem.getId()).toString();
            String qualification = instituteItem.getInstitute();
            instituteList.put(qualification, id);
        }
        return instituteList;
    }

    public ILookupDataLoaderDAO getLookupDataLoaderDAO() {
        return lookupDataLoaderDAO;
    }

    public void setLookupDataLoaderDAO(ILookupDataLoaderDAO lookupDataLoaderDAO) {
        this.lookupDataLoaderDAO = lookupDataLoaderDAO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> loadSysConfigParams() {
        LOGGER.info("Loading configuration list.");
        sysConfigList = new LinkedHashMap<String, String>();
        List<SysConfParamTb> configList = lookupDataLoaderDAO.loadConfigList();
        for (SysConfParamTb configparam : configList) {
            sysConfigList.put(configparam.getName(), configparam.getValue());
        }
        return sysConfigList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, List<ButtonDetailsVo>> loadUserButtons(String userType) {
        LOGGER.info("Loading advisor buttons list.");
        userButtonsList = new HashMap<Integer, List<ButtonDetailsVo>>();
        List<Object> userStatusList = lookupDataLoaderDAO.getUserStatusList(userType);
        for (Iterator<Object> it = userStatusList.iterator(); it.hasNext();) {
            List<ButtonDetailsVo> statusDetails = new ArrayList<ButtonDetailsVo>();
            Integer statusCode = (Integer) it.next();
            List<ClientButtonsTb> userButtonsListOnStatus = lookupDataLoaderDAO.getUserButtonsList(userType, statusCode);
            for (ClientButtonsTb buttonDetails : userButtonsListOnStatus) {
                ButtonDetailsVo buttonDetailsVO = new ButtonDetailsVo();
                buttonDetailsVO.setButtonText(buttonDetails.getButtonText());
                buttonDetailsVO.setButtonStyleClass(buttonDetails.getStyleClass());
                buttonDetailsVO.setImmediate(buttonDetails.getImmediate());
                buttonDetailsVO.setConfirm(buttonDetails.getConfirm());
                statusDetails.add(buttonDetailsVO);
            }
            userButtonsList.put(statusCode, statusDetails);
        }
        return userButtonsList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadAdvisoryContractFreqLookup(String fieldType) {
        LOGGER.log(Level.INFO, "Loading advisory service contract lookup {0}", fieldType);
        advisoryServiceContractLookup = new LinkedHashMap<String, Integer>();
        List<ContractFreqLookupTb> lookupList = lookupDataLoaderDAO.loadAdvisoryContractFreqLookup(fieldType);
        for (ContractFreqLookupTb lookupItem : lookupList) {
            advisoryServiceContractLookup.put(lookupItem.getFieldLabel(), lookupItem.getFieldValue());
        }
        return advisoryServiceContractLookup;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, BigDecimal> loadAdvisoryContractPercLookup(String fieldType) {
        LOGGER.log(Level.INFO, "Loading advisory service contract lookup {0}", fieldType);
        advisoryServiceContractPercLookup = new LinkedHashMap<String, BigDecimal>();
        List<ContractPercLookupTb> lookupList = lookupDataLoaderDAO.loadAdvisoryContractPercLookup(fieldType);
        for (ContractPercLookupTb lookupItem : lookupList) {
            advisoryServiceContractPercLookup.put(lookupItem.getFieldLabel(), lookupItem.getFieldValue());
        }
        return advisoryServiceContractPercLookup;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<String> loadLinkedInMemId(boolean advisor) {
        return lookupDataLoaderDAO.loadLinkedInMemId(advisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getAccessToken(String linkedinMemId) {
        return lookupDataLoaderDAO.getAccessToken(linkedinMemId);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, QuestionnaireVO> loadQuestionnaire() {
        List<QuestionOptionMappingTb> questionList = lookupDataLoaderDAO.loadQuestionnaire();
        List<QuestionoptionsmasterTb> optionList = lookupDataLoaderDAO.loadOptions();
        return generateQuestionnaire(questionList, optionList);
    }

    private Map<Integer, QuestionnaireVO> generateQuestionnaire(List<QuestionOptionMappingTb> questionList,
            List<QuestionoptionsmasterTb> optionList) {
        Map<Integer, QuestionnaireVO> questionnaire = new HashMap<Integer, QuestionnaireVO>();
        Map<Integer, OptionValuesVO> questionOptionsValue = new HashMap<Integer, OptionValuesVO>();
        questionOptionsValue = generateOptions(questionOptionsValue, optionList);

        for (QuestionOptionMappingTb optionMappingTb : questionList) {
            List<OptionValuesVO> options = new ArrayList<OptionValuesVO>();
            Field farr[] = optionMappingTb.getClass().getDeclaredFields();
            Method[] marr = optionMappingTb.getClass().getDeclaredMethods();
            for (Field field : farr) {
                String methodName = EMPTY_STRING;
                if (!field.getName().contains(OPTION)) {
                    continue;
                } else {
                    methodName = GET + field.getName();
                }
                for (Method method : marr) {
                    if (method.getName().toUpperCase().equals(methodName.toUpperCase())) {
                        try {
                            if (Integer.parseInt(method.invoke(optionMappingTb).toString()) > 0) {
                                options.add(questionOptionsValue.get(method.invoke(optionMappingTb)));
                            }
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(LookupDataLoaderBAOImpl.class.getName()).log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(LookupDataLoaderBAOImpl.class.getName()).log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(LookupDataLoaderBAOImpl.class.getName()).log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                        }
                        break;
                    }
                }
            }
            questionnaire.put(optionMappingTb.getQuestionmasterTb().getId(),
                    new QuestionnaireVO(optionMappingTb.getQuestionmasterTb().getId(),
                            optionMappingTb.getQuestionmasterTb().getQuestion(),
                            ZERO,
                            ZERO,
                            options, optionMappingTb.getQuestionmasterTb().getImgPath()));
        }
        return questionnaire;
    }

    private Map<Integer, OptionValuesVO> generateOptions(Map<Integer, OptionValuesVO> questionOptionsValue,
            List<QuestionoptionsmasterTb> optionList) {
        for (QuestionoptionsmasterTb options : optionList) {
            questionOptionsValue.put(options.getId(), new OptionValuesVO(
                    options.getId(), options.getQOption(), options.getQOptionvalue()));
        }
        return questionOptionsValue;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, PortfolioTypeVO> loadPortFolioTypes() {
        Map<Integer, PortfolioTypeVO> portfolioTypeMap = new HashMap<Integer, PortfolioTypeVO>();
        for (MasterPortfolioTypeTb portfolioTypeMasterTb : lookupDataLoaderDAO.loadPortfolioTypes()) {
            PortfolioTypeVO portfolioTypeVO = new PortfolioTypeVO();
            portfolioTypeVO.setId(portfolioTypeMasterTb.getId());
            portfolioTypeVO.setInvestorProfile(portfolioTypeMasterTb.getMasterPortfolioStyleTb().getPortfolioStyle());
            portfolioTypeVO.setProfileType(portfolioTypeMasterTb.getMasterPortfolioStyleTb().getPortfolioStyleType());
            portfolioTypeVO.setMinScore(portfolioTypeMasterTb.getMasterPortfolioStyleTb().getMinScore());
            portfolioTypeVO.setMaxScore(portfolioTypeMasterTb.getMasterPortfolioStyleTb().getMaxScore());
            portfolioTypeVO.setCash(portfolioTypeMasterTb.getCash());
            portfolioTypeVO.setGold(portfolioTypeMasterTb.getGold());
            portfolioTypeVO.setDebt(portfolioTypeMasterTb.getDebt());
            portfolioTypeVO.setEquitiesIndex(portfolioTypeMasterTb.getEquitiesIndex());
            portfolioTypeVO.setEquitiesBlueChip(portfolioTypeMasterTb.getEquitiesBluechip());
            portfolioTypeVO.setEquitiesMidCap(portfolioTypeMasterTb.getEquitiesMidcap());
            portfolioTypeVO.setEquitiesSmallCap(portfolioTypeMasterTb.getEquitiesSmallCap());
            portfolioTypeVO.setFando(portfolioTypeMasterTb.getFando());
            portfolioTypeVO.setInternational(portfolioTypeMasterTb.getInternational());
            portfolioTypeVO.setCommodities(portfolioTypeMasterTb.getCommodities());
            portfolioTypeVO.setMutualFund(portfolioTypeMasterTb.getMutualFunds());
            portfolioTypeVO.setRange_min_equity(portfolioTypeMasterTb.getRangeMinEquity());
            portfolioTypeVO.setRange_min_cash(portfolioTypeMasterTb.getRangeMinCash());
            portfolioTypeVO.setRange_min_gold(portfolioTypeMasterTb.getRangeMinGold());
            portfolioTypeVO.setRange_min_debt(portfolioTypeMasterTb.getRangeMinDebt());
            portfolioTypeVO.setRange_min_fo(portfolioTypeMasterTb.getRangeMinFo());
            portfolioTypeVO.setRange_min_international(portfolioTypeMasterTb.getRangeMinInternational());
            portfolioTypeVO.setRange_min_mutual_fund(portfolioTypeMasterTb.getRangeMinMutualFunds());
            portfolioTypeVO.setRange_max_equity(portfolioTypeMasterTb.getRangeMaxEquity());
            portfolioTypeVO.setRange_max_cash(portfolioTypeMasterTb.getRangeMaxCash());
            portfolioTypeVO.setRange_max_gold(portfolioTypeMasterTb.getRangeMaxGold());
            portfolioTypeVO.setRange_max_debt(portfolioTypeMasterTb.getRangeMaxDebt());
            portfolioTypeVO.setRange_max_fo(portfolioTypeMasterTb.getRangeMaxFo());
            portfolioTypeVO.setRange_max_international(portfolioTypeMasterTb.getRangeMaxInternational());
            portfolioTypeVO.setRange_max_mutual_fund(portfolioTypeMasterTb.getRangeMaxMutualFunds());
            portfolioTypeVO.setDiversified_equity(portfolioTypeMasterTb.getEquityDiverisied());
            portfolioTypeVO.setRange_max_diversifiedEquity(portfolioTypeMasterTb.getRangeMaxEquityDiverisied());
            portfolioTypeVO.setRange_min_diversifiedEquity(portfolioTypeMasterTb.getRangeMinEquityDiverisied());
            portfolioTypeVO.setRange_max_midcap(portfolioTypeMasterTb.getRangeMaxMidcap());
            portfolioTypeVO.setRange_min_midcap(portfolioTypeMasterTb.getRangeMinMicap());
            portfolioTypeVO.setMidCap(portfolioTypeMasterTb.getMidcap());
            portfolioTypeVO.setBalanced(portfolioTypeMasterTb.getBalanced());
            portfolioTypeVO.setRange_min_balanced(portfolioTypeMasterTb.getRangeMinBalanced());
            portfolioTypeVO.setRange_max_balanced(portfolioTypeMasterTb.getRangeMaxBalanced());
            portfolioTypeVO.setDebt_liquid(portfolioTypeMasterTb.getDebtLiquid());
            portfolioTypeVO.setRange_min_debt_liquid(portfolioTypeMasterTb.getRangeMinDebtLiquid());
            portfolioTypeVO.setRange_max_debt_liquid(portfolioTypeMasterTb.getRangeMaxDebtLiquid());
            portfolioTypeVO.setMasterPortfolioSizeTb(portfolioTypeMasterTb.getMasterPortfolioSizeTb());
            portfolioTypeVO.setMasterPortfolioStyleTb(portfolioTypeMasterTb.getMasterPortfolioStyleTb());
            portfolioTypeVO.setMaxAum(portfolioTypeMasterTb.getMasterPortfolioSizeTb().getMaxAum() == null
                    ? null : portfolioTypeMasterTb.getMasterPortfolioSizeTb().getMaxAum());
            portfolioTypeVO.setMinAum(portfolioTypeMasterTb.getMasterPortfolioSizeTb().getMinAum());
            portfolioTypeVO.setPortfolioSize(portfolioTypeMasterTb.getMasterPortfolioSizeTb().getPortfolioSize());
            portfolioTypeVO.setTax_planning(portfolioTypeMasterTb.getTax_planning());
            portfolioTypeVO.setMax_tax_planning(portfolioTypeMasterTb.getMax_tax_planning());
            portfolioTypeVO.setMin_tax_planning(portfolioTypeMasterTb.getMin_tax_planning());
            portfolioTypeMap.put(portfolioTypeMasterTb.getId(), portfolioTypeVO);
        }
        return portfolioTypeMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public LinkedHashMap<String, Short> loadAssetClasses() {
        LinkedHashMap<String, Short> assetClasses = new LinkedHashMap<String, Short>();
        for (MasterAssetTb masterAssetTb : lookupDataLoaderDAO.loadAssetClasses()) {
            assetClasses.put(masterAssetTb.getAssetName(), masterAssetTb.getId());
        }
        return assetClasses;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, String> loadBenchMArk() {
        Map<Integer, String> benchMark = new HashMap<Integer, String>();
        for (MasterBenchmarkIndexTb masterBenchmarkIndexTb : lookupDataLoaderDAO.loadBenchMArk()) {
            benchMark.put(masterBenchmarkIndexTb.getId(), masterBenchmarkIndexTb.getValue());
        }
        return benchMark;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveNSEBenchmark(List<BenchmarkPerformanceTb> dataList) {
        return lookupDataLoaderDAO.saveNSEBenchmark(dataList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<AssetClassVO> loadAssetClassVOs() {
        List<MasterAssetTb> loadAssetClasses = lookupDataLoaderDAO.loadAssetClasses();
        List<AssetClassVO> assetClassVOs = new ArrayList<AssetClassVO>();
        for (MasterAssetTb masterAssetTb : loadAssetClasses) {
            AssetClassVO assetClassVO = new AssetClassVO();
            assetClassVO.setAssetId(masterAssetTb.getId().intValue());
            assetClassVO.setAssetName(masterAssetTb.getAssetName());
            assetClassVO.setAssetColor(masterAssetTb.getAssetColor());
            assetClassVOs.add(assetClassVO);
        }
        return assetClassVOs;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveMasterSecurityList(List<MasterSecurityClassificationTb> dataList) {
        return lookupDataLoaderDAO.saveMasterSecurityList(dataList);
    }

    /**
     * Creating Classification Map
     *
     * @return List<Map<String, String>> listMap
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<Map<String, String>> loadMFMap() {

        LOGGER.info("Loading MF Map.");
        mfDataMap = new LinkedHashMap<String, String>();
        indexBseMap = new LinkedHashMap<String, String>();
        indexNseMap = new LinkedHashMap<String, String>();
        midcapBseMap = new LinkedHashMap<String, String>();
        midcapNseMap = new LinkedHashMap<String, String>();

        //Loading classification from database.
        List<Object> classificationTbList = lookupDataLoaderDAO.loadClassificationList();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        List<Object[]> amfiList = (List<Object[]>) classificationTbList.get(0);

        //Reading each classification and creating corresponding Map.
        for (Object[] objects : amfiList) {
            String isinNumber = (String) objects[ZERO];
            String mmfClassification = (String) objects[ONE];
            mfDataMap.put(isinNumber, mmfClassification);
        }
        listMap.add(mfDataMap);
        for (IndexNseTb indexNse : (List<IndexNseTb>) classificationTbList.get(ONE)) {
            indexNseMap.put(indexNse.getSymbol(), indexNse.getSymbol());
        }
        listMap.add(indexNseMap);
        for (IndexBseTb indexBse : (List<IndexBseTb>) classificationTbList.get(2)) {
            indexBseMap.put(indexBse.getScripCode(), indexBse.getScripCode());
        }
        listMap.add(indexBseMap);
        for (MidcapBseTb midcapBse : (List<MidcapBseTb>) classificationTbList.get(3)) {
            midcapBseMap.put(midcapBse.getIsin(), midcapBse.getScripCode());
        }
        listMap.add(midcapBseMap);
        for (MidcapNseTb midcapNse : (List<MidcapNseTb>) classificationTbList.get(4)) {
            midcapNseMap.put(midcapNse.getIsinCode(), midcapNse.getIsinCode());
        }
        listMap.add(midcapNseMap);
        return listMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveMasterMidCapBseList(List<MidcapBseTb> dataList) {

        return lookupDataLoaderDAO.saveMasterMidCapBseList(dataList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveMasterMidCapNseList(List<MidcapNseTb> dataList) {

        return lookupDataLoaderDAO.saveMastermidcapNseList(dataList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveMasterIndexNseList(List<IndexNseTb> dataList) {

        return lookupDataLoaderDAO.saveMasterindexNseList(dataList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveMasterIndexBseList(List<IndexBseTb> dataList) {

        return lookupDataLoaderDAO.saveMasterindexBseList(dataList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadResidentStatusList() {
        LOGGER.info("loading resident status list");
        HashMap<String, Integer> residentStatus = new HashMap<String, Integer>();
        List<ResidentStatusTb> residentStatuslist = lookupDataLoaderDAO.loadResidentStatusList();
        for (ResidentStatusTb residentStatusTb : residentStatuslist) {
            Integer key = residentStatusTb.getKey();
            String status = residentStatusTb.getStatus();
            residentStatus.put(status, key);
        }
        return residentStatus;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadProofOfIdentityList() {
        LOGGER.info("loading Proof Of Identity list");
        HashMap<String, Integer> proofOfIdentity = new HashMap<String, Integer>();
        List<ProofofIdentityTb> proofofIdentityList = lookupDataLoaderDAO.loadProofOfIdentityList();
        for (ProofofIdentityTb proofofIdentityListTb : proofofIdentityList) {
            Integer key = proofofIdentityListTb.getKey();
            String status = proofofIdentityListTb.getProofIdentity();
            proofOfIdentity.put(status, key);
        }
        return proofOfIdentity;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public LinkedHashMap<String, Integer> loadProofOfAddressList() {
        LOGGER.info("loading Proof Of Address list");
        LinkedHashMap<String, Integer> proofOfAddress = new LinkedHashMap<String, Integer>();
        List<ProofofAddressTb> proofOfAddressList = lookupDataLoaderDAO.loadProofOfAddressList();
        for (ProofofAddressTb proofOfAddressListTb : proofOfAddressList) {
            Integer key = proofOfAddressListTb.getKey();
            String address = proofOfAddressListTb.getProofofAddress();
            proofOfAddress.put(address, key);
        }
        return proofOfAddress;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadDpidsList() {
        LOGGER.info("loading Dpids list");
        HashMap<String, Integer> dpids = new HashMap<String, Integer>();
        List<DpidsTb> dpidsList = lookupDataLoaderDAO.loadDpidsList();
        for (DpidsTb dpidsListTb : dpidsList) {
            Integer key = dpidsListTb.getKey();
            String id = dpidsListTb.getDpid();
            dpids.put(id, key);
        }
        return dpids;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadOpenningAccountTypeList() {
        LOGGER.info("loading Openning AccountType List");
        HashMap<String, Integer> openningAccountType = new HashMap<String, Integer>();
        List<OpenningAccountTypeTb> openningAccountTypeList = lookupDataLoaderDAO.loadOpenningAccountTypeList();
        for (OpenningAccountTypeTb openningAccountTypeListTb : openningAccountTypeList) {
            Integer key = openningAccountTypeListTb.getKey();
            String account = openningAccountTypeListTb.getOpenningAccountType();
            openningAccountType.put(account, key);
        }
        return openningAccountType;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadDematAccountList() {
        LOGGER.info("loading Demat Account list");
        HashMap<String, Integer> dematAccount = new HashMap<String, Integer>();
        List<DemataccountTb> dematAccountList = lookupDataLoaderDAO.loadDematAccountList();
        for (DemataccountTb dematAccountListTb : dematAccountList) {
            Integer key = dematAccountListTb.getKey();
            String address = dematAccountListTb.getDematAccount();
            dematAccount.put(address, key);
        }
        return dematAccount;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadTradingAccountList() {
        LOGGER.info("loading Trading Account list");
        HashMap<String, Integer> tradingAccount = new HashMap<String, Integer>();
        List<TradingaccountTb> tradingAccountList = lookupDataLoaderDAO.loadTradingAccountList();
        for (TradingaccountTb tradingAccountListTb : tradingAccountList) {
            Integer key = tradingAccountListTb.getKey();
            String tradingaccount = tradingAccountListTb.getTradingAccount();
            tradingAccount.put(tradingaccount, key);
        }
        return tradingAccount;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public LinkedHashMap<String, Integer> loadIncomerangeList() {
        LOGGER.info("loading Income range list");
        LinkedHashMap<String, Integer> incomerange = new LinkedHashMap<String, Integer>();
        List<IncomerangeTb> incomerangeList = lookupDataLoaderDAO.loadIncomerangeList();
        for (IncomerangeTb incomerangeListTb : incomerangeList) {
            Integer key = incomerangeListTb.getKey();
            String income = incomerangeListTb.getIncomerange();
            incomerange.put(income, key);
        }
        return incomerange;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadOccupationList() {
        LOGGER.info("loading Occupation list");
        HashMap<String, Integer> occupation = new HashMap<String, Integer>();
        List<OccupationTb> occupationList = lookupDataLoaderDAO.loadOccupationList();
        for (OccupationTb occupationListTb : occupationList) {
            Integer key = occupationListTb.getKey();
            String job = occupationListTb.getOccupation();
            occupation.put(job, key);
        }
        return occupation;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, Integer> loadBankSubTypeList() {
        LOGGER.info("loading BankSubType list");
        HashMap<String, Integer> bankSubType = new HashMap<String, Integer>();
        List<BankSubTypeTb> bankSubTypeList = lookupDataLoaderDAO.loadBankSubTypeList();
        for (BankSubTypeTb bankSubTypeListTb : bankSubTypeList) {
            Integer key = bankSubTypeListTb.getKey();
            String subtype = bankSubTypeListTb.getBankSubType();
            bankSubType.put(subtype, key);
        }
        return bankSubType;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> loadStateList() {
        LOGGER.info("Loading State list.");
        stateList = new LinkedHashMap<String, String>();
        List<StateTb> stateTbList = lookupDataLoaderDAO.loadStateList();
        for (StateTb stateListItem : stateTbList) {
            String stateCode = stateListItem.getStateCode();
            String stateName = stateListItem.getStateName();
            stateList.put(stateCode, stateName);
        }
        return stateList;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, String> loadBankList() {
        LOGGER.info("Loading Bank list.");
        bankList = new LinkedHashMap<Integer, String>();
//        List<BankTb> bankTbList = lookupDataLoaderDAO.loadBankList();
//        for (BankTb bankListItem : bankTbList) {
//            Integer bankCode = bankListItem.getId();
//            String bankName = bankListItem.getBank();
//            bankList.put(bankCode, bankName);
//        }
        bankList = new HashMap<Integer, String>();
        List<BankName> bankTbList = lookupDataLoaderDAO.loadBankList();
        for (BankName bankListItem : bankTbList) {
            Integer bankCode = bankListItem.getBankId();
            String bankName = bankListItem.getBankName();
            bankList.put(bankCode, bankName);
        }
        return bankList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<Object> loadAllCity() {
        List<Object> responseList = new ArrayList<Object>();
        LOGGER.info("Loading Cites list.");
        cityList = new LinkedHashMap<String, String>();
        List<CitiesTb> cityTbList = lookupDataLoaderDAO.loadCityList();
        for (CitiesTb cityListItem : cityTbList) {
            String cityName = cityListItem.getCityName();
            String stateCode = cityListItem.getStateCode();
            cityList.put(cityName, cityName);
        }
        responseList.add(cityList);
        responseList.add(cityTbList);
        return responseList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<ExchangeHolidayTb> loadHolidayMap() {
        return lookupDataLoaderDAO.loadExchangeHoliday();

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public LinkedHashMap<String, String> loadAdvancedSettings() {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        List<MmfSettingsTb> mmfSettingsTbs = lookupDataLoaderDAO.loadAdvancedSettings();
        for (MmfSettingsTb mmfSettingsTb : mmfSettingsTbs) {
            String key = mmfSettingsTb.getFieldname();
            String value = mmfSettingsTb.getFieldvalue();
            hashMap.put(key, value);
        }
        return hashMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, List<String>> loadStateCityMap() {
        List<Object[]> cityStateMap = lookupDataLoaderDAO.loadStateCityMap();
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        List<String> cityList = null;
        for (Object[] bankTb : cityStateMap) {
            if (map.containsKey(bankTb[0])) {
                cityList.add((String) bankTb[1]);
            } else {
                cityList = new ArrayList<String>();
                cityList.add((String) bankTb[1]);
                map.put((String) bankTb[0], cityList);
            }
        }
        return map;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, PortfolioTypeVO> loadPortFolioStyle() {
        Map<Integer, PortfolioTypeVO> portfolioStyleMap = new HashMap<Integer, PortfolioTypeVO>();
        for (MasterPortfolioStyleTb portfolioStyleTb : lookupDataLoaderDAO.loadPortfolioStyle()) {
            PortfolioTypeVO portfolioTypeVO = new PortfolioTypeVO();
            portfolioTypeVO.setId(portfolioStyleTb.getPortfolioStyleId());
            portfolioTypeVO.setInvestorProfile(portfolioStyleTb.getPortfolioStyle());
            portfolioTypeVO.setProfileType(portfolioStyleTb.getPortfolioStyleType());
            portfolioTypeVO.setMinScore(portfolioStyleTb.getMinScore());
            portfolioTypeVO.setMaxScore(portfolioStyleTb.getMaxScore());
            portfolioStyleMap.put(portfolioStyleTb.getPortfolioStyleId(), portfolioTypeVO);
        }
        return portfolioStyleMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<Integer, PortfolioTypeVO> loadPortFolioSize() {
        Map<Integer, PortfolioTypeVO> portfolioSizeMap = new LinkedHashMap<Integer, PortfolioTypeVO>();
        for (MasterPortfolioSizeTb portfolioSizeTb : lookupDataLoaderDAO.loadPortfolioSize()) {
            PortfolioTypeVO portfolioTypeVO = new PortfolioTypeVO();
            portfolioTypeVO.setId(portfolioSizeTb.getPortfolioSizeId());
            portfolioTypeVO.setMaxAum(portfolioSizeTb.getMaxAum() == null ? null : portfolioSizeTb.getMaxAum());
            portfolioTypeVO.setMinAum(portfolioSizeTb.getMinAum());
            portfolioTypeVO.setPortfolioSize(portfolioSizeTb.getPortfolioSize());
            portfolioSizeMap.put(portfolioSizeTb.getPortfolioSizeId(), portfolioTypeVO);
        }
        return portfolioSizeMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveIfcMicrMappingTbList(List<IfcMicrMappingTb> dataList) {

        return lookupDataLoaderDAO.saveIfcMicrMappingTbList(dataList);
    }
}
