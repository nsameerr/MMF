/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.git.oms.api.frontend.OMSAPI;
import com.git.oms.api.frontend.OMSConfig;
import com.git.oms.api.frontend.service.OMSServices;
import com.git.oms.api.frontend.transport.TransportTypes;
import com.gtl.mmf.entity.CitiesTb;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.springframework.util.StringUtils;
import com.gtl.mmf.service.vo.AssetClassVO;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.tibco.tibrv.TibrvTransport;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 07958
 */
public final class LookupDataLoader implements IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.common.LookupDataLoader");
    private static final List<String> FUTURE_INSTRUMENTTYPES_LIST = Arrays.asList("FUTSTK", "FUTIVX", "FUTIDX");
    private static Map<String, String> countryListLookup;
    private static Map<String, String> cityListLookup;
    private static Map<String, String> qualificationList;
    private static Map<String, String> institutionList;
    private static Map<String, String> yearList;
    private static Map<String, String> brockerList;
    private static Map<String, String> docStatusList;
    private static Map<String, Integer> userStatusList;
    private static Map<String, String> userTypeList;
    private static Map<String, String> configParamList;
    private static Map<Integer, List<ButtonDetailsVo>> advisorButtonsList;
    private static Map<Integer, List<ButtonDetailsVo>> investorButtonsList;
    private static Map<String, Integer> aumPayableFrequencyList;
    private static Map<String, Integer> performanceFeeFreqList;
    private static Map<String, Integer> contractDurationFreqList;
    private static Map<String, Integer> contractDurationList;
    private static Map<String, Integer> mgmtFeeFixedAmountList;
    private static Map<String, BigDecimal> mgmtFeeVariableAmountList;
    private static Map<String, BigDecimal> performanceFeeList;
    private static Map<String, BigDecimal> performanceFeeThresholdList;
    private static Map<String, String> sortByInvestorNetwork;
    private static Map<String, String> sortByAdvisorNetwork;
    private static Map<String, String> investorNetworkFilter;
    private static Map<String, String> advisorNetworkFilter;
    private static Map<Integer, QuestionnaireVO> questionnaireMap;
    private static Map<Integer, PortfolioTypeVO> portfolioTypesMap;
    private static LinkedHashMap<String, Short> assetClassesList;
    private static Map<String, Integer> rateAdvisorValueList;
    private static List<SymbolRecordVO> equitySymbolDataList;
    private static List<SymbolRecordVO> actualSymbolList;
    private static Map<String, Integer> reviewFreequency;
    private static Map<Integer, String> benchMark;
    private static List<Integer> priceToleranceList;
    private static List<SymbolRecordVO> debtSymbolDataList;
    private static List<SymbolRecordVO> actualDebtSymbolDataList;
    private static List<SymbolRecordVO> foSymbolDataList;
    private static List<SymbolRecordVO> actualFOSymbolDataList;
    private static OMSServices omsServices;
    private static String resourcePath;
    private static List<SymbolRecordVO> mfSymbolDataList;
    private static List<SymbolRecordVO> bseEquitySymbolDataList;
    private static List<SymbolRecordVO> bseFOSymbolDataList;
    private static List<SymbolRecordVO> actualMFSymbolDataList;
    private static Map<Integer, AssetClassVO> assetClassMap;
    private static Map<String, String> indexBseMap;
    private static Map<String, String> indexNseMap;
    private static Map<String, String> midcapBseMap;
    private static Map<String, String> midcapNseMap;
    private static Map<String, String> mfClassificationMap;
    private static Map<String, Integer> residentStatusMap;
    private static Map<String, Integer> bankSubTypeMap;
    private static Map<String, Integer> occupationMap;
    private static LinkedHashMap<String, Integer> incomerangeMap;
    private static Map<String, Integer> tradingAccountMap;
    private static Map<String, Integer> dematAccountMap;
    private static Map<String, Integer> openningAccountTypeMap;
    private static Map<String, Integer> dpidsMap;
    private static LinkedHashMap<String, Integer> proofOfAddressMap;
    private static Map<String, Integer> proofOfIdentityMap;
    public static HashMap<String, Integer> genderDataMap;
    public static HashMap<String, Integer> maritalstatusMap;
    public static String mmfProprty;
    public static String configOption;
    public static String sebiLocation;
    private static Map<String, String> stateListLookup;
    private static HashMap<String, Integer> debitAccountMap;
    private static HashMap<String, Integer> frequencyMap;
    private static HashMap<String, Integer> debitAccountTypemap;
    private static HashMap<String, Integer> mandateActionMap;
    private static HashMap<String, Integer> nseSegments;
    private static HashMap<String, Integer> bseSegments;
    private static HashMap<String, Integer> documentoryEvidence;
    private static HashMap<String, Integer> electronicContractType;
    private static HashMap<String, Integer> alertType;
    private static HashMap<String, Integer> relationWithMobileNumber;
    private static Map<Integer, String> bankListLookup;
    private static List masterCities;
    //List for new classification
    private static List<SymbolRecordVO> indexList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> diversifiedEquityList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> debtDataList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> goldDataList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> internationalDataList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> midcapDataList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> derivativesList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> debtLiquidDataList = new ArrayList<SymbolRecordVO>();
    private static List<SymbolRecordVO> taxPlanningDataList = new ArrayList<SymbolRecordVO>();
    private static OMSAdapterRv omsAdapterRv;
    private static TibrvTransport tibrvTransport;
    private static HashMap<String, String> mailTypeList;

    //Oms Login flag
    private static boolean omsLogin = false;
    private static boolean holyday_NSE = false;
    private static boolean holyday_BSE = false;

    private static HashMap<String, Boolean> venueStatusMap = new HashMap<String, Boolean>();
    public static HashMap<String, String> dependentDataMap;
    private static HashMap<String, String> venueTimeMap = new HashMap<String, String>();
    private static List<ExchangeHolidayTb> exchangeHoliday = new ArrayList<ExchangeHolidayTb>();
    private static LinkedHashMap<String, String> mmfsettings = new LinkedHashMap<String, String>();
    public static String allowedOrigin;
    private static Map<Integer, PortfolioTypeVO> portfolioStyleMap;
    private static Map<Integer, PortfolioTypeVO> portfolioSizeMap;
    public static HashMap<String, String> nomineePoiMap;
    public static String publicCaptchaKey;
    public static String contextRoot;
    private static String templatePath;
    public static HashMap<String, Integer> addressTypeList;
    private static LinkedHashMap<Integer, String> proofOfAddressForOthers;

    public static void loadInvestorNetworkFilter() {
        investorNetworkFilter = new LinkedHashMap<String, String>();
        investorNetworkFilter.put(AGGRESSSIVE, AGGRESSSIVE);
        investorNetworkFilter.put(MODERATELY_AGGRESSIVE, MODERATELY_AGGRESSIVE);
        investorNetworkFilter.put(MODERATE, MODERATE);
        investorNetworkFilter.put(MODERATELY_CONSERVATIVE, MODERATELY_CONSERVATIVE);
        investorNetworkFilter.put(CONSERVATIVE, CONSERVATIVE);
    }

    public static void loadAdvisorNetworkFilter() {
        advisorNetworkFilter = new LinkedHashMap<String, String>();
        advisorNetworkFilter.put(FRIENDS, FRIENDS);
        advisorNetworkFilter.put(FRIENDS_OF_FREINDS, FRIENDS_OF_FREINDS);
        advisorNetworkFilter.put(INVESTORS, INVESTORS);
        advisorNetworkFilter.put(NO_LINKEDIN_CONNECTION, NO_LINKEDIN_CONNECTION);

    }

    public static void loadSortByAdvisorNetwork() {
        sortByAdvisorNetwork = new LinkedHashMap<String, String>();
        sortByAdvisorNetwork.put(CONNECTION_LEVEL, CONNECTION_LEVEL);
        sortByAdvisorNetwork.put(SHARED_CONNECTIONS, SHARED_CONNECTIONS);
        sortByAdvisorNetwork.put(DATE_OF_JOINING, DATE_OF_JOINING);
        sortByAdvisorNetwork.put(STATUS, STATUS);

    }

    public static void loadSortByInvestorNetwork() {
        sortByInvestorNetwork = new LinkedHashMap<String, String>();
        sortByInvestorNetwork.put(RETURN_THREE_MONTHS, RETURN_THREE_MONTHS);
        sortByInvestorNetwork.put(RETURN_ONE_YEAR, RETURN_ONE_YEAR);
        sortByInvestorNetwork.put(PORTFOLIO_OUT_PERFORMANCE, PORTFOLIO_OUT_PERFORMANCE);
        sortByInvestorNetwork.put(AVG_CLIENT_RATING, AVG_CLIENT_RATING);
        sortByInvestorNetwork.put(CONNECTION_LEVEL, CONNECTION_LEVEL);
    }

    public static void loadContractFixedAmountList(Map<String, Integer> contractFixedAmountListLoaded) {
        mgmtFeeFixedAmountList = new LinkedHashMap<String, Integer>();
        mgmtFeeFixedAmountList.putAll(contractFixedAmountListLoaded);
    }

    public static void loadVariableAmountList(Map<String, BigDecimal> variableAmountListLoaded) {
        mgmtFeeVariableAmountList = new LinkedHashMap<String, BigDecimal>();
        mgmtFeeVariableAmountList.putAll(variableAmountListLoaded);
    }

    public static void loadPerformanceFeeList(Map<String, BigDecimal> performanceFeeListLoaded) {
        performanceFeeList = new LinkedHashMap<String, BigDecimal>();
        performanceFeeList.putAll(performanceFeeListLoaded);
    }

    public static void loadPerformanceFeeThresholdList(Map<String, BigDecimal> performanceFeeThresholdListLoaded) {
        performanceFeeThresholdList = new LinkedHashMap<String, BigDecimal>();
        performanceFeeThresholdList.putAll(performanceFeeThresholdListLoaded);
    }

    public static void loadContractDurationList(Map<String, Integer> contractDurationListLoaded) {
        contractDurationList = new LinkedHashMap<String, Integer>();
        contractDurationList.putAll(contractDurationListLoaded);
    }

    public static void loadContractDurationFreqList(Map<String, Integer> contractDurationListLoaded) {
        contractDurationFreqList = new LinkedHashMap<String, Integer>();
        contractDurationFreqList.putAll(contractDurationListLoaded);
    }

    public static void loadAUMPayableFrequencyList(Map<String, Integer> aumPayableFrequencyListLoaded) {
        aumPayableFrequencyList = new LinkedHashMap<String, Integer>();
        aumPayableFrequencyList.putAll(aumPayableFrequencyListLoaded);
    }

    public static void loadPerformanceFeeFreqList(Map<String, Integer> performanceFeeFreqListLoaded) {
        performanceFeeFreqList = new LinkedHashMap<String, Integer>();
        performanceFeeFreqList.putAll(performanceFeeFreqListLoaded);
    }

    public static void loadInvestorButtonsList(Map<Integer, List<ButtonDetailsVo>> investorButtonsListLoaded) {
        investorButtonsList = new HashMap<Integer, List<ButtonDetailsVo>>();
        investorButtonsList.putAll(investorButtonsListLoaded);
    }

    public static void loadAdvisorButtonsList(Map<Integer, List<ButtonDetailsVo>> advisorButtonsListLoaded) {
        advisorButtonsList = new HashMap<Integer, List<ButtonDetailsVo>>();
        advisorButtonsList.putAll(advisorButtonsListLoaded);
    }

    public static void loadBrockerListLookup(Map<String, String> brockerListLoaded) {
        brockerList = new LinkedHashMap<String, String>();
        brockerList.putAll(brockerListLoaded);
    }

    public static void loadCountryListLookup(Map<String, String> countryListLoaded) {
        countryListLookup = new LinkedHashMap<String, String>();
        countryListLookup.putAll(countryListLoaded);
    }

    public static void loadCityListLookup(Map<String, String> cityListLoaded) {
        cityListLookup = new LinkedHashMap<String, String>();
        cityListLookup.putAll(cityListLoaded);
    }

    public static void loadYearList() {
        yearList = new LinkedHashMap<String, String>();
        Calendar now = Calendar.getInstance();
        for (int i = 1920; i <= now.get(Calendar.YEAR); i++) {
            String year = (new Integer(i)).toString();
            yearList.put(year, year);
        }
    }

    public static void loadUserTypeList() {
        userTypeList = new HashMap<String, String>();
        userTypeList.put("Advisor", "Advisor");
        userTypeList.put("Investor", "Investor");
        userTypeList.put("Investor / Advisor", "IorA");
    }

    public static void loaddocStatusList() {
        docStatusList = new LinkedHashMap<String, String>();
        docStatusList.put("Documents Pending", "Documents Pending");
        docStatusList.put("Documents Received", "Documents Received");
    }

    public static void loadUserActiveStatusList() {
        userStatusList = new LinkedHashMap<String, Integer>();
        userStatusList.put("Date Of Registration", ACTIVE_VAL);
        userStatusList.put("Status", STATUS_VAL);
    }

    public static void loadQualificationList(Map<String, String> loadQualificationList) {
        qualificationList = new HashMap<String, String>();
        qualificationList.putAll(loadQualificationList);
    }

    public static void loadInstitutionList(Map<String, String> loadInstitueList) {
        institutionList = new HashMap<String, String>();
        institutionList.putAll(loadInstitueList);
    }

    public static void loadConfigparams(Map<String, String> sysConfigList) {
        configParamList = new HashMap<String, String>();
        configParamList.putAll(sysConfigList);
    }

    public static void loadAssetClassMap(List<AssetClassVO> assetClassVOs) {
        assetClassMap = new HashMap<Integer, AssetClassVO>();
        for (AssetClassVO assetClassVO : assetClassVOs) {
            assetClassMap.put(assetClassVO.getAssetId(), assetClassVO);
        }
    }

    public static Map<Integer, AssetClassVO> getAssetClassMap() {
        return assetClassMap;
    }

    public static Map<String, String> getBrockerList() {
        return brockerList;
    }

    public static Map<String, String> getYearList() {
        return yearList;
    }

    public static Map<String, String> getUserTypeList() {
        return userTypeList;
    }

    public static Map<String, String> getDocStatusList() {
        return docStatusList;
    }

    public static Map<String, String> getQualificationList() {
        return qualificationList;
    }

    public static Map<String, String> getInstitutionList() {
        return institutionList;
    }

    public static Map<String, String> getCountryList() {
        return countryListLookup;
    }

    public static Map<String, String> getConfigParamList() {
        return configParamList;
    }

    public static Map<String, Integer> getUserStatusList() {
        return userStatusList;
    }

    public static Map<Integer, List<ButtonDetailsVo>> getAdvisorButtonsList() {
        return advisorButtonsList;
    }

    public static Map<Integer, List<ButtonDetailsVo>> getInvestorButtonsList() {
        return investorButtonsList;
    }

    public static Map<String, Integer> getAUMPayableFrequencyList() {
        return aumPayableFrequencyList;
    }

    public static Map<String, Integer> getPerformanceFeeFreqList() {
        return performanceFeeFreqList;
    }

    public static Map<String, Integer> getDurationList() {
        return contractDurationList;
    }

    public static Map<String, Integer> getContractDurationFreqList() {
        return contractDurationFreqList;
    }

    public static Map<String, Integer> getMgmtFeeFixedAmountList() {
        return mgmtFeeFixedAmountList;
    }

    public static Map<String, BigDecimal> getMgmtFeeVariableAmountList() {
        return mgmtFeeVariableAmountList;
    }

    public static Map<String, BigDecimal> getPerformanceFeeList() {
        return performanceFeeList;
    }

    public static Map<String, String> getSortByInvestorNetwork() {
        return sortByInvestorNetwork;
    }

    public static Map<String, String> getSortByAdvisorNetwork() {
        return sortByAdvisorNetwork;
    }

    public static Map<String, BigDecimal> getPerformanceFeeThresholdList() {
        return performanceFeeThresholdList;
    }

    public static List<SymbolRecordVO> getIndexList() {
        return indexList;
    }

    public static List<SymbolRecordVO> getDiversifiedEquityList() {
        return diversifiedEquityList;
    }

    public static List<SymbolRecordVO> getDebtDataList() {
        return debtDataList;
    }

    public static List<SymbolRecordVO> getInternationalDataList() {
        return internationalDataList;
    }

    public static List<SymbolRecordVO> getMidcapDataList() {
        return midcapDataList;
    }

    public static List<SymbolRecordVO> getDerivativesList() {
        return derivativesList;
    }

    public static Map<Integer, QuestionnaireVO> getQuestionnaire() {
        Map<Integer, QuestionnaireVO> tempMap = new LinkedHashMap<Integer, QuestionnaireVO>();
        List<Integer> keys = new ArrayList<Integer>(questionnaireMap.keySet());
        Collections.shuffle(keys);
        for (Integer key : keys) {
            QuestionnaireVO qvo = questionnaireMap.get(key);
            tempMap.put(key, qvo);
        }
        return tempMap;
    }

    public static Map<String, String> getAdvisorNetworkFilter() {
        return advisorNetworkFilter;
    }

    public static Map<String, String> getInvestorNetworkFilter() {
        return investorNetworkFilter;
    }

    public static void loadAllMasterCity(List<CitiesTb> city) {
        masterCities = new ArrayList<CitiesTb>();
        masterCities.addAll(city);
    }

    public static void loadExchangeHoliday(List<ExchangeHolidayTb> holidayMap) {
        exchangeHoliday = new ArrayList<ExchangeHolidayTb>();
        exchangeHoliday.addAll(holidayMap);
    }

    public static void loadAdminAdvancedSettings(LinkedHashMap<String, String> advSettings) {
        mmfsettings = new LinkedHashMap<String, String>();
        mmfsettings.putAll(advSettings);
    }

    public static void loadPortFolioStyle(Map<Integer, PortfolioTypeVO> loadPortFolioStyle) {
        portfolioStyleMap = new LinkedHashMap<Integer, PortfolioTypeVO>();
        portfolioStyleMap.putAll(loadPortFolioStyle);
    }

    public static Map<Integer, PortfolioTypeVO> getPortfolioStyle() {
        return portfolioStyleMap;
    }

    public static void loadPortFolioSize(Map<Integer, PortfolioTypeVO> loadPortFolioSize) {
        portfolioSizeMap = new LinkedHashMap<Integer, PortfolioTypeVO>();
        portfolioSizeMap.putAll(loadPortFolioSize);
    }

    public static Map<Integer, PortfolioTypeVO> getPortfolioSize() {
        return portfolioSizeMap;
    }

    private LookupDataLoader() {
    }

    public static void loadQuestionnaireList(Map<Integer, QuestionnaireVO> questionnaireVOs) {
        questionnaireMap = new HashMap<Integer, QuestionnaireVO>();
        questionnaireMap.putAll(questionnaireVOs);
    }

    public static void loadPortFolioType(Map<Integer, PortfolioTypeVO> portfolioTypes) {
        portfolioTypesMap = new LinkedHashMap<Integer, PortfolioTypeVO>();
        portfolioTypesMap.putAll(portfolioTypes);
    }

    public static Map<Integer, PortfolioTypeVO> getPortfolioTypes() {
        return portfolioTypesMap;
    }

    public static void loadAssetClasses(LinkedHashMap<String, Short> assetClasses) {
        assetClassesList = new LinkedHashMap<String, Short>();
        assetClassesList.putAll(assetClasses);
    }

    public static LinkedHashMap<String, Short> getAssetClasses() {
        return assetClassesList;
    }

    public static Map<String, Integer> getRateAdvisorValueList() {
        return rateAdvisorValueList;
    }

    public static void loadRateAdvisorValueList() {
        rateAdvisorValueList = new LinkedHashMap<String, Integer>();
        rateAdvisorValueList.put("0", 0);
        rateAdvisorValueList.put("1", 1);
        rateAdvisorValueList.put("2", 2);
        rateAdvisorValueList.put("3", 3);
        rateAdvisorValueList.put("4", 4);
        rateAdvisorValueList.put("5", 5);
        rateAdvisorValueList.put("6", 6);
        rateAdvisorValueList.put("7", 7);
        rateAdvisorValueList.put("8", 8);
        rateAdvisorValueList.put("9", 9);
        rateAdvisorValueList.put("10", 10);
    }

    public static boolean downloadNSESymbol(String path, String fileName, String symbolPath) {
        boolean downloadStatus = false;
        HttpURLConnection connection = null;
        connection = getUrlConnection(symbolPath);
        try {
            //downloadStatus = doZipDecoding(path, fileName, connection);
            downloadStatus = doSevenZipDecoding(path, fileName, connection);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }

        return downloadStatus;
    }

    private static HttpURLConnection getUrlConnection(String symbolPath) {
        URL url = null;
        Boolean proxyEnabled = Boolean.parseBoolean(com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(PROXY_ENABLED));
        if (proxyEnabled) {
            System.setProperty(HTTP_PROXYHOST, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYHOST));
            System.setProperty(HTTP_PROXYPORT, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYPORT));
        }
        URLConnection urlConnection = null;

        try {
            url = new URL(PropertiesLoader.getPropertiesValue(symbolPath));
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        }

        try {
            urlConnection = url.openConnection();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        }
        HttpURLConnection httpConnection = (java.net.HttpURLConnection) urlConnection;
        httpConnection.setUseCaches(true);
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);
        httpConnection.setAllowUserInteraction(false);
        httpConnection.setRequestProperty("METHOD", "POST");
        httpConnection.setReadTimeout(5000);
        return httpConnection;
    }

    /**
     *
     * @param path
     */
    public static boolean prepareSymbolMap(String path, String zipFileName, int downloadOrder) {

        boolean symbolPrepareStatus = false;
        File file = null;
        BufferedReader bReader = null;
        equitySymbolDataList = new ArrayList<SymbolRecordVO>();
        debtSymbolDataList = new ArrayList<SymbolRecordVO>();
        foSymbolDataList = new ArrayList<SymbolRecordVO>();
        mfSymbolDataList = new ArrayList<SymbolRecordVO>();
        bseEquitySymbolDataList = new ArrayList<SymbolRecordVO>();
        bseFOSymbolDataList = new ArrayList<SymbolRecordVO>();
        file = new File(path + DOUBLE_FORWARED_SLAH + zipFileName);
        try {
            bReader = new BufferedReader(new FileReader(file));
            String data = bReader.readLine();
            while ((data = bReader.readLine()) != null) {
                String[] symbols = data.split(COMA);
                switch (downloadOrder) {
                    case 1:
                        equitySymbolDataList.add(prepareSRecVO(symbols));
                        break;
                    case 2:
                        debtSymbolDataList.add(prepareSRecVO(symbols));
                        break;
                    case 3:
                        SymbolRecordVO prepareSRecVO = prepareSRecVO(symbols);
                        if (!isFutureItem(prepareSRecVO)) {
                            foSymbolDataList.add(prepareSRecVO(symbols));
                        }
                        break;
                    case 4:
                        mfSymbolDataList.add(prepareSRecVOForMF(symbols));
                        break;
                    case 5:
                        bseEquitySymbolDataList.add(prepareSRecVO(symbols));
                        break;
                    case 6:
                        bseFOSymbolDataList.add(prepareSRecVO(symbols));
                        break;
                }
            }
            bReader.close();
            symbolPrepareStatus = true;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        }
        return symbolPrepareStatus;
    }

    private static boolean isFutureItem(SymbolRecordVO prepareSRecVO) {
        return FUTURE_INSTRUMENTTYPES_LIST.contains(prepareSRecVO.getInstrumentType());
    }

    private static String getStringVal(String[] symbols, int index) {
        String value = null;
        if (symbols.length >= index + ONE && StringUtils.hasText(symbols[index])) {
            value = symbols[index].trim();
        }
        return value;
    }

    private static double getDoubleVal(String[] symbols, int index) {
        double value = 0d;
        if (symbols.length >= index + ONE && StringUtils.hasText(symbols[index])) {
            value = Double.parseDouble(symbols[index].trim());
        }
        return value;
    }

    private static int getIntVal(String[] symbols, int index) {
        int value = 0;
        if (symbols.length >= index + 1 && StringUtils.hasText(symbols[index])) {
            value = Integer.parseInt(symbols[index].trim());
        }
        return value;
    }

    /**
     *
     * @param symbols
     * @return SymbolRecordVO Venuecode[0] VenueScripCode [1] InstrumentType [2]
     * SecurityCode [3] Series [4] SecurityName [5] ExpirationDate [6]
     * StrikePrice [7] MarketLot [8] OptionType [9] Active [10] Status [11]
     * SectorCode [12] SubSectorCode [13] CeilingPrice [14] FloorPrice [15]
     * FaceValue [16] FirstSupport [17] SecondSupport [18] Firstresistance [19]
     * Secondresistance [20] Announcement [21] ResearchDescBuyOrSell [22]
     * ReasearchdescNewsPath [23] 52WeekHigh [24] 52WeekLow [25] ExtScripcode3
     * [26] ClosingRate [27] DecimalLocator [28] AssetId [29] Units [30]
     * TickSize [31] UnderLyingScripcode [32] PivotPoint [33] Isin [34] MISC1
     * [35] MISC2 [36] MISC3 [37] MISC4 [38] MISC5 [39] MISC6 [40] MISC7 [41]
     * MISC8 [42] MISC9 [43] MISC10 [44] TradingUnitFactor [45] TradingUnit [46]
     * PriceQuoteFactor [47] PriceNumerator [48] PriceDenominator [49]
     * GeneralNumerator [50] GeneralDenominator [51] accruedinterest [52]
     */
    private static SymbolRecordVO prepareSRecVO(String[] symbols) {

        SymbolRecordVO sRecordVO = new SymbolRecordVO();
        sRecordVO.setVenue(getStringVal(symbols, 0));
        sRecordVO.setVenueScripCode(getStringVal(symbols, 1));
        sRecordVO.setInstrumentType(getStringVal(symbols, 2));
        sRecordVO.setScripName(getStringVal(symbols, 3));
        sRecordVO.setSeries(getStringVal(symbols, 4));
        sRecordVO.setScripDescription(getStringVal(symbols, 5));
        sRecordVO.setExpirationDate(getStringVal(symbols, 6));
        sRecordVO.setStrikePrice(getDoubleVal(symbols, 7));
        sRecordVO.setMarketLot(getIntVal(symbols, 8));
        sRecordVO.setOptionType(getStringVal(symbols, 9));
        sRecordVO.setCeilingPrice(getDoubleVal(symbols, 14));
        sRecordVO.setFloorPrice(getDoubleVal(symbols, 15));
        sRecordVO.setFaceValue(getDoubleVal(symbols, 16));
        sRecordVO.setAnnouncement(getStringVal(symbols, 21));
        sRecordVO.setExtScripcode3(getStringVal(symbols, 26));
        sRecordVO.setUnits(getIntVal(symbols, 30));
        sRecordVO.setTickSize(getDoubleVal(symbols, 31));
        sRecordVO.setPivotPoint(getDoubleVal(symbols, 33));
        sRecordVO.setIsin(getStringVal(symbols, 34));
        sRecordVO.setScripMgn(getDoubleVal(symbols, 35));
        sRecordVO.setMISC3(getStringVal(symbols, 37));
        sRecordVO.setMISC5(getStringVal(symbols, 39));
        sRecordVO.setMISC6(getStringVal(symbols, 40));
        sRecordVO.setMISC8(getStringVal(symbols, 42));
        sRecordVO.setMISC9(getStringVal(symbols, 43));
        sRecordVO.setMISC10(getStringVal(symbols, 44));
        sRecordVO.setTradingUnitFactor(getStringVal(symbols, 45));
        sRecordVO.setTradingUnit(getStringVal(symbols, 46));
        sRecordVO.setPriceQuoteFactor(getStringVal(symbols, 47));
        sRecordVO.setPriceNumerator(getDoubleVal(symbols, 48));
        sRecordVO.setPriceDenominator(getDoubleVal(symbols, 49));
        sRecordVO.setGeneralNumerator(getDoubleVal(symbols, 50));
        sRecordVO.setGeneralDenominator(getDoubleVal(symbols, 51));
        sRecordVO.setAccruedinterest(getStringVal(symbols, 52));
        sRecordVO.setTicker(LookupDataLoader.getTicker(sRecordVO));
        sRecordVO.setLabel(LookupDataLoader.getLabel(sRecordVO));
        return sRecordVO;
    }

    private static String getLabel(SymbolRecordVO sRecordVO) {
        String label = "";
        if (sRecordVO.getVenue().equalsIgnoreCase(NSE)
                || sRecordVO.getVenue().equalsIgnoreCase(NSEDEBT)
                || sRecordVO.getVenue().equalsIgnoreCase(BSE)) {
            label = sRecordVO.getScripDescription();
        } else if (sRecordVO.getVenue().equalsIgnoreCase(NSEMF)) {
            label = sRecordVO.getScripDescription() + " - "
                    + sRecordVO.getScripName();
        } else if (sRecordVO.getVenue().equalsIgnoreCase(NSEFO)
                || sRecordVO.getVenue().equalsIgnoreCase(BSEFO)) {
            label = sRecordVO.getScripName() + " - "
                    + sRecordVO.getExpirationDate() + " "
                    + sRecordVO.getStrikePrice() + " "
                    + sRecordVO.getOptionType() + " "
                    + sRecordVO.getVenue();
        }
        return label;
    }

    private static String getTicker(SymbolRecordVO sRecordVO) {
        String ticker = "";
        if (sRecordVO.getVenue().equalsIgnoreCase(NSE)
                || sRecordVO.getVenue().equalsIgnoreCase(BSE)) {
            ticker = sRecordVO.getScripDescription() + " - "
                    + sRecordVO.getScripName() + " | "
                    + sRecordVO.getSeries() + " | "
                    + sRecordVO.getVenue();
        } else if (sRecordVO.getVenue().equalsIgnoreCase(NSEDEBT)) {
            ticker = sRecordVO.getScripDescription() + " - "
                    + sRecordVO.getScripName() + " | "
                    + sRecordVO.getExpirationDate() + " | "
                    + sRecordVO.getVenue();
        } else if (sRecordVO.getVenue().equalsIgnoreCase(NSEFO)
                || sRecordVO.getVenue().equalsIgnoreCase(BSEFO)) {
            ticker = sRecordVO.getScripName() + " - "
                    + sRecordVO.getExpirationDate() + " | "
                    + sRecordVO.getStrikePrice() + " | "
                    + sRecordVO.getOptionType() + " | "
                    + sRecordVO.getVenue();
        } else if (sRecordVO.getVenue().equalsIgnoreCase(NSEMF)) {
            ticker = sRecordVO.getScripDescription() + " - "
                    + sRecordVO.getScripName() + " | "
                    + sRecordVO.getSeries() + " | "
                    + sRecordVO.getVenue();
        }
        return ticker;
    }

    private static List<SymbolRecordVO> getSymbolDataList() {
        if (!equitySymbolDataList.isEmpty()) {
            actualSymbolList = new ArrayList<SymbolRecordVO>();
            actualSymbolList.addAll(equitySymbolDataList);
        }
        return actualSymbolList;
    }

    private static List<SymbolRecordVO> getBseSymbolDataListFO() {
        if (!bseFOSymbolDataList.isEmpty()) {
            if (actualSymbolList == null) {
                actualSymbolList = new ArrayList<SymbolRecordVO>();
            }
            actualFOSymbolDataList.addAll(bseFOSymbolDataList);
        }
        return actualFOSymbolDataList;
    }

    private static List<SymbolRecordVO> getBseSymbolDataListEquity() {
        if (!bseEquitySymbolDataList.isEmpty()) {
            if (actualSymbolList == null) {
                actualSymbolList = new ArrayList<SymbolRecordVO>();
            }
            actualSymbolList.addAll(bseEquitySymbolDataList);
        }
        return actualSymbolList;
    }

    public static List<SymbolRecordVO> getActualSymbolList() {
        return actualSymbolList;
    }

    public static void loadReviewFreequency(Map<String, Integer> benchMarkMap) {
        reviewFreequency = benchMarkMap;
    }

    public static Map<String, Integer> getReviewFreequency() {
        return reviewFreequency;
    }

    public static void loadBenchMark(Map<Integer, String> benchMarkMap) {
        benchMark = benchMarkMap;
    }

    public static Map<Integer, String> getBenchMark() {
        return benchMark;
    }

    public static void loadPriceToleranceList() {
        priceToleranceList = new ArrayList<Integer>();
        priceToleranceList.add(5);
        priceToleranceList.add(10);
        priceToleranceList.add(15);
        priceToleranceList.add(20);
        priceToleranceList.add(25);
        priceToleranceList.add(30);
        priceToleranceList.add(35);
        priceToleranceList.add(40);
        priceToleranceList.add(45);
        priceToleranceList.add(50);
    }

    public static List<Integer> getPriceToleranceList() {
        return priceToleranceList;
    }

    private static List<SymbolRecordVO> getDebtSymbolDataList() {
        if (!debtSymbolDataList.isEmpty()) {
            actualDebtSymbolDataList = new ArrayList<SymbolRecordVO>();
            actualDebtSymbolDataList.addAll(debtSymbolDataList);
        }
        return actualDebtSymbolDataList;
    }

    private static List<SymbolRecordVO> getFOSymbolDataList() {
        if (!foSymbolDataList.isEmpty()) {
            actualFOSymbolDataList = new ArrayList<SymbolRecordVO>();
            actualFOSymbolDataList.addAll(foSymbolDataList);
            actualFOSymbolDataList.addAll(bseFOSymbolDataList);
        }
        return actualFOSymbolDataList;
    }

    public static List<SymbolRecordVO> getActualDebtSymbolDataList() {
        return actualDebtSymbolDataList;
    }

    public static List<SymbolRecordVO> getActualFOSymbolDataList() {
        return actualFOSymbolDataList;
    }

    public static List<SymbolRecordVO> loadSymbols(int downloadOrder) {
        List<SymbolRecordVO> list = null;
        switch (downloadOrder) {
            case 1:
                list = getSymbolDataList();
                break;
            case 2:
                list = getDebtSymbolDataList();
                break;
            case 3:
                list = getFOSymbolDataList();
                break;
            case 4:
                list = getMfSymbolDataList();
                break;
            case 5:
                list = getBseSymbolDataListEquity();
                break;
            case 6:
                list = getBseSymbolDataListFO();
                break;
        }
        return list;
    }

    private static boolean doSevenZipDecoding(String savePath, String fileName, HttpURLConnection httpConnection) throws Exception {

        java.io.BufferedInputStream inStream = null;
        java.io.BufferedOutputStream outStream = null;
        boolean downloadStatus;
        try {
            inStream = new java.io.BufferedInputStream(httpConnection.getInputStream());
            outStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(savePath + DOUBLE_FORWARED_SLAH + fileName));

            int propertiesSize = 5;
            byte[] properties = new byte[propertiesSize];
            if (inStream.read(properties, 0, propertiesSize) != propertiesSize) {
                throw new Exception("input .lzma file is too short");
            }

            SevenZip.Compression.LZMA.Decoder decoder = new SevenZip.Compression.LZMA.Decoder();
            if (!decoder.SetDecoderProperties(properties)) {
                throw new Exception("Incorrect stream properties");
            }
            long outSize = 0;
            for (int i = 0; i < 8; i++) {
                int v = inStream.read();
                if (v < 0) {
                    throw new Exception("Can't read stream size");
                }
                outSize |= ((long) v) << (8 * i);
            }
            if (!decoder.Code(inStream, outStream, outSize)) {
                throw new Exception("Error in data stream");
            }
            downloadStatus = true;

            outStream.flush();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        }
        return downloadStatus;
    }

    private static boolean doZipDecoding(String savePath, String fileName, HttpURLConnection httpConnection) {

        try {
            boolean downloadStatus;
            int count = ZERO;
            InputStream iStream = httpConnection.getInputStream();
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(iStream));
            ZipEntry entry = zipInputStream.getNextEntry();
            byte data[] = new byte[518];
            FileOutputStream fStream = new FileOutputStream(savePath + DOUBLE_FORWARED_SLAH + fileName);
            BufferedOutputStream bStream = new BufferedOutputStream(fStream, data.length);
            while ((count = zipInputStream.read(data, ZERO, data.length)) != MINUS_ONE) {
                bStream.write(data, ZERO, count);
            }

            bStream.close();
            fStream.flush();
            fStream.close();
            zipInputStream.close();
            downloadStatus = true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        } finally {
            httpConnection.disconnect();
        }
        return false;
    }

    public static void configOMSService() {
        try {
            OMSConfig omsConfig = new OMSConfig();
            omsConfig.setEncoding(true);
            omsConfig.setMultiThread(false);
            omsConfig.setRvService(PropertiesLoader.getPropertiesValue(OMS_RVSERVICE));
            omsConfig.setRvNetwork(PropertiesLoader.getPropertiesValue(OMS_RVNETWORK));
            omsConfig.setRvDeamon(PropertiesLoader.getPropertiesValue(OMS_RVDEAMON));
            omsConfig.setTimeout(60);
            omsConfig.setTransportType(TransportTypes.RV_TRANSPORT);

            OMSAPI omsAPI = new OMSAPI();
            omsAPI.init(omsConfig);
            omsAPI.start();
            omsServices = omsAPI.getOMSServices();
            tibrvTransport = (TibrvTransport) omsServices.getTransporter().getTransportImpl();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public static OMSServices getOmsServices() {
        return omsServices;
    }

    public static void processBenchMarkData(String path, Integer type) throws FileNotFoundException, IOException {
        String benchmark = getBenchMark().get(type);
        if ("NIFTY 50".equals(benchmark)) {
            processNSEBenchmark(path, type);
        } else {
            processBSEBenchmark(path);
        }
    }

    private static void processNSEBenchmark(String path, int type) throws FileNotFoundException, IOException {
        path = path + DOUBLE_FORWARED_SLAH + NIFTY;
        File file = new File(path);
        File[] files = file.listFiles();
        File file2 = null;
        if (files != null && files.length != ZERO) {
            file2 = files[ZERO];
            CSVParser.parseAndPersistCSV(new FileInputStream(file2), type);
        }
    }

    private static void processBSEBenchmark(String path) {
    }

    public static void setResourcesPath(String path) {
        resourcePath = path;
    }

    public static String getResourcePath() {
        return resourcePath;
    }

    //Added for mutual fund
    private static List<SymbolRecordVO> getMfSymbolDataList() {
        if (!mfSymbolDataList.isEmpty()) {
            actualMFSymbolDataList = new ArrayList<SymbolRecordVO>();
            actualMFSymbolDataList.addAll(mfSymbolDataList);
        }
        return actualMFSymbolDataList;
    }

    public static List<SymbolRecordVO> getActualMFSymbolDataList() {
        return actualMFSymbolDataList;
    }

    /**
     *
     * @param symbols
     * @return SymbolRecordVO Venuecode[0] VenueScripCode [1] InstrumentType [2]
     * SecurityCode [3] Series [4] SecurityName [5] AmcCode [6] AmcSchemeCode
     * [7] RtAgentCode [8] RtSchemeCode [9] Active [10] Status [11] CategoryCode
     * [12] MinSubscrFresh [13] MinSubscrAddl [14] DepositoryAllowed [15] Isin
     * [16] FolioLength [17] QuantityLimit [18] ValueLimit [19] SecDepMandatory
     * [20] SecAllowDP [21] SecDividend [22] SecAllowSell [23] SecModCxl [24]
     * SecAllowBuy [25] Nav [26] NavDate [27] MinRedValue [28] MinRedQty [29]
     * MISC1 [30] MISC2 [31] MISC3 [32] MISC4 [33] MISC5 [34] MISC6 [35] MISC7
     * [36] MISC8 [37] MISC9 [38] MISC10 [39]
     */
    private static SymbolRecordVO prepareSRecVOForMF(String[] symbols) {

        SymbolRecordVO sRecordVO = new SymbolRecordVO();
        sRecordVO.setVenue(getStringVal(symbols, 0));
        sRecordVO.setVenueScripCode(getStringVal(symbols, 1));
        sRecordVO.setInstrumentType(getStringVal(symbols, 2));
        sRecordVO.setScripName(getStringVal(symbols, 3));
        sRecordVO.setSeries(getStringVal(symbols, 4));
        sRecordVO.setScripDescription(getStringVal(symbols, 5));

        sRecordVO.setAmcCode(getStringVal(symbols, 6));
        sRecordVO.setAmcSchemeCode(getStringVal(symbols, 7));
        sRecordVO.setRtAgentCode(getStringVal(symbols, 8));
        sRecordVO.setRtSchemeCode(getStringVal(symbols, 9));
        sRecordVO.setActive(getStringVal(symbols, 10));
        sRecordVO.setStatus(getStringVal(symbols, 11));
        sRecordVO.setCategoryCode(getStringVal(symbols, 12));
        sRecordVO.setMinSubScrFresh(getDoubleVal(symbols, 13));
        sRecordVO.setMinSubscraddl(getDoubleVal(symbols, 14));
        sRecordVO.setDepositoryAllowed(getStringVal(symbols, 15));
        sRecordVO.setIsin(getStringVal(symbols, 16));
        sRecordVO.setFolioLength(getStringVal(symbols, 17));
        sRecordVO.setQuantityLimit(getStringVal(symbols, 18));
        sRecordVO.setValueLimit(getStringVal(symbols, 19));
        sRecordVO.setSecDepMandatory(getStringVal(symbols, 20));
        sRecordVO.setSecAllowDP(getIntVal(symbols, 21));
        sRecordVO.setSecDividend(getStringVal(symbols, 22));
        sRecordVO.setSecAllowSell(getStringVal(symbols, 23));
        sRecordVO.setSecModCxl(getStringVal(symbols, 24));
        sRecordVO.setSecAllowBuy(getStringVal(symbols, 25));
        sRecordVO.setNav(getStringVal(symbols, 26));
        sRecordVO.setNavDate(getStringVal(symbols, 27));
        sRecordVO.setMinRedValue(getDoubleVal(symbols, 28));
        sRecordVO.setMinRedQty(getDoubleVal(symbols, 29));

        sRecordVO.setMISC1(getStringVal(symbols, 30));
        sRecordVO.setMISC2(getStringVal(symbols, 31));
        sRecordVO.setMISC3(getStringVal(symbols, 32));
        sRecordVO.setMISC4(getStringVal(symbols, 33));
        sRecordVO.setMISC5(getStringVal(symbols, 34));
        sRecordVO.setMISC6(getStringVal(symbols, 35));
        sRecordVO.setMISC7(getStringVal(symbols, 36));
        sRecordVO.setMISC8(getStringVal(symbols, 37));
        sRecordVO.setMISC9(getStringVal(symbols, 38));
        sRecordVO.setMISC10(getStringVal(symbols, 39));
        sRecordVO.setTicker(LookupDataLoader.getTicker(sRecordVO));
        sRecordVO.setLabel(LookupDataLoader.getLabel(sRecordVO));
        return sRecordVO;
    }

    //Oms Login flag
    public static boolean isOmsLogin() {
        return omsLogin;
    }

    public static Map<String, String> loadMFClassification(List<Map<String, String>> loadMFClassification) {

        mfClassificationMap = new LinkedHashMap<String, String>();
        indexBseMap = new LinkedHashMap<String, String>();
        indexNseMap = new LinkedHashMap<String, String>();
        midcapBseMap = new LinkedHashMap<String, String>();
        midcapNseMap = new LinkedHashMap<String, String>();
        mfClassificationMap.putAll(loadMFClassification.get(ZERO));
        indexNseMap.putAll(loadMFClassification.get(ONE));
        indexBseMap.putAll(loadMFClassification.get(2));
        midcapBseMap.putAll(loadMFClassification.get(3));
        midcapNseMap.putAll(loadMFClassification.get(4));
        return mfClassificationMap;

    }

    public static Map<String, String> getMfClassificationMap() {
        return mfClassificationMap;
    }

    /**
     * Creating new classification map with securities from the downloaded
     * symbol file
     *
     * @param path
     * @param zipFileName
     * @param downloadOrder
     * @return
     */
    public static boolean prepareNewSymbolMap(String path, String zipFileName, int downloadOrder) {

        boolean symbolPrepareStatus = false;
        File file = null;
        BufferedReader bReader = null;
        file = new File(path + DOUBLE_FORWARED_SLAH + zipFileName);
        String isinValue;
        String scripCode;

        try {
            bReader = new BufferedReader(new FileReader(file));
            String data = bReader.readLine();
            SymbolRecordVO sRecord = null;

            while ((data = bReader.readLine()) != null) {
                String[] symbols = data.split(COMA);
                switch (downloadOrder) {
                    case 1:
                        // equity
                        // In equity file ISIN value position is at 34
                        // scrip code position is 3 getStringVal(symbols, 3)
                        isinValue = getStringVal(symbols, ISIN_POS);
                        sRecord = prepareSRecVO(symbols);
                        scripCode = getStringVal(symbols, SCRIP_CODE_POS);
                        if (indexNseMap.containsKey(scripCode)) {
                            if (indexList.isEmpty() || !indexList.contains(sRecord)) {
                                indexList.add(sRecord);
                            }
                        } else if (midcapNseMap.containsKey(isinValue) || midcapBseMap.containsKey(isinValue)) {
                            if (midcapDataList.isEmpty() || !midcapDataList.contains(sRecord)) {
                                midcapDataList.add(sRecord);
                            }
                        } else {
                            addDataTOList(isinValue, sRecord, scripCode);
                        }
                        break;
                    case 2:
                        // debt
                        debtDataList.add(prepareSRecVO(symbols));
                        debtLiquidDataList.add(prepareSRecVO(symbols));
                        break;
                    case 3:
                        // f&o
                        sRecord = prepareSRecVO(symbols);
                        if (!isFutureItem(sRecord)) {
                            derivativesList.add(sRecord);
                        }
                        break;
                    case 4:
                        // mf ISIN value position is at 16
                        isinValue = getStringVal(symbols, ISIN_POS_MF);
                        scripCode = getStringVal(symbols, SCRIP_CODE_POS);
                        String categoryCode = getStringVal(symbols, CATEGORY_CODE_POS_MF);
                        String security = getStringVal(symbols, 5);
                        if (!categoryCode.equalsIgnoreCase(CATEGORY_CODE_MF_DBTCR)
                                && !categoryCode.equalsIgnoreCase(CATEGORY_CODE_MF_HLIQD)
                                && !security.toUpperCase().contains("DIRECT")) {
//                            if(security.toUpperCase().contains("DIRECT")){
//                                System.out.println("isinValue[" + isinValue + "] ,scripcode["+ scripCode + "] ,security["+security+"]");
//                            }
                            sRecord = prepareSRecVOForMF(symbols);
                            addDataTOList(isinValue, sRecord, scripCode);
                        }
                        break;
                    case 5:
                        // BSE equity
                        isinValue = getStringVal(symbols, ISIN_POS);
                        scripCode = getStringVal(symbols, SCRIP_CODE_POS_BSE); // position is 26
                        sRecord = prepareSRecVO(symbols);
                        if (indexBseMap.containsKey(scripCode)) {
                            if (indexList.isEmpty() || !indexList.contains(sRecord)) {
                                indexList.add(sRecord);
                            }
                        } else if (midcapBseMap.containsKey(isinValue) || midcapNseMap.containsKey(isinValue)) {
                            if (midcapDataList.isEmpty() || !midcapDataList.contains(sRecord)) {
                                midcapDataList.add(sRecord);
                            }
                        } else {
                            addDataTOList(isinValue, sRecord, scripCode);
                            //diversifiedEquityList.add(sRecord);
                        }

                        break;
                    case 6:
                        sRecord = prepareSRecVO(symbols);
                        if (!isFutureItem(sRecord)) {
                            derivativesList.add(sRecord);
                        }
                        // bseFOSymbolDataList.add(prepareSRecVO(symbols));   
                        break;
                }
            }

            bReader.close();
            symbolPrepareStatus = true;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(e));
        }
        return symbolPrepareStatus;
    }

    private static void addDataTOList(String isinValue,
            SymbolRecordVO symbols, String scripCode) {
        String mmfClassification = mfClassificationMap.get(isinValue);

        if (mmfClassification != null) {
            if (mmfClassification.equalsIgnoreCase(INDEX)) {
                if (indexList.isEmpty() || !indexList.contains(symbols)) {
                    indexList.add(symbols);
                }
            } else if (mmfClassification.equalsIgnoreCase(EQUITY_DIVERSIFIED)) {
                if (diversifiedEquityList.isEmpty() || !diversifiedEquityList.contains(symbols)) {
                    diversifiedEquityList.add(symbols);
                }
            } else if (mmfClassification.equalsIgnoreCase(TEXT_DEBT)) {
                if (debtDataList.isEmpty() || !debtDataList.contains(symbols)) {
                    debtDataList.add(symbols);
                }
            } else if (mmfClassification.equalsIgnoreCase(MIDCAP)) {
                if (midcapDataList.isEmpty() || !midcapDataList.contains(symbols)) {
                    midcapDataList.add(symbols);
                }
            } else if (mmfClassification.equalsIgnoreCase(TEXT_INTERNATIONAL)) {
                if (internationalDataList.isEmpty() || !internationalDataList.contains(symbols)) {
                    internationalDataList.add(symbols);
                }
            } else if (mmfClassification.equalsIgnoreCase(TEXT_GOLD)) {
                if (goldDataList.isEmpty() || !goldDataList.contains(symbols)) {
                    goldDataList.add(symbols);
                }
            } else if (mmfClassification.equalsIgnoreCase(TEXT_DEBT_LIQUID)) {
                if (debtLiquidDataList.isEmpty() || !debtLiquidDataList.contains(symbols)) {
                    debtLiquidDataList.add(symbols);
                }
            } else if (mmfClassification.equalsIgnoreCase(TAX_PLANNING)) {
                if (taxPlanningDataList.isEmpty() || !taxPlanningDataList.contains(symbols)) {
                    taxPlanningDataList.add(symbols);
                }
            }
        } else if (diversifiedEquityList.isEmpty() || !diversifiedEquityList.contains(symbols)) {
            diversifiedEquityList.add(symbols);
        }
    }

    public static List<SymbolRecordVO> getGoldDataList() {
        return goldDataList;
    }

    public static Map<String, Integer> getResidentStatusMap() {
        return residentStatusMap;
    }

    public static Map<String, Integer> getBankSubTypeMap() {
        return bankSubTypeMap;
    }

    public static Map<String, Integer> getOccupationMap() {
        return occupationMap;
    }

    public static LinkedHashMap<String, Integer> getIncomerangeMap() {
        return incomerangeMap;
    }

    public static Map<String, Integer> getTradingAccountMap() {
        return tradingAccountMap;
    }

    public static Map<String, Integer> getDematAccountMap() {
        return dematAccountMap;
    }

    public static Map<String, Integer> getOpenningAccountTypeMap() {
        return openningAccountTypeMap;
    }

    public static Map<String, Integer> getDpidsMap() {
        return dpidsMap;
    }

    public static Map<String, Integer> getProofOfIdentityMap() {
        return proofOfIdentityMap;
    }

    public static LinkedHashMap<String, Integer> getProofOfAddressMap() {
        return proofOfAddressMap;
    }

    public static HashMap<String, Integer> getGenderDataMap() {
        return genderDataMap;
    }

    public static HashMap<String, Integer> getMaritalstatusMap() {
        return maritalstatusMap;
    }

    public static void loadResidentStatusMap(Map<String, Integer> residentStatus) {
        residentStatusMap = new HashMap<String, Integer>();
        residentStatusMap.putAll(residentStatus);
    }

    public static void loadproofOfIdentityMap(Map<String, Integer> proofOfIdentity) {
        proofOfIdentityMap = new HashMap<String, Integer>();
        proofOfIdentityMap.putAll(proofOfIdentity);
    }

    public static void loadproofOfAddressMap(LinkedHashMap<String, Integer> proofOfAddress) {
        proofOfAddressMap = new LinkedHashMap<String, Integer>();
        proofOfAddressMap.putAll(proofOfAddress);
    }

    public static void loaddpidsMap(Map<String, Integer> dpids) {
        dpidsMap = new HashMap<String, Integer>();
        dpidsMap.putAll(dpids);

    }

    public static void loadopenningAccountTypeMap(Map<String, Integer> openningAccountType) {
        openningAccountTypeMap = new HashMap<String, Integer>();
        openningAccountTypeMap.putAll(openningAccountType);
    }

    public static void loaddematAccountMap(Map<String, Integer> dematAccount) {
        dematAccountMap = new HashMap<String, Integer>();
        dematAccountMap.putAll(dematAccount);
    }

    public static void loadtradingAccountMap(Map<String, Integer> tradingAccount) {
        tradingAccountMap = new HashMap<String, Integer>();
        tradingAccountMap.putAll(tradingAccount);
    }

    public static void loadincomerangeMap(LinkedHashMap<String, Integer> incomerange) {
        incomerangeMap = new LinkedHashMap<String, Integer>();
        incomerangeMap.putAll(incomerange);
    }

    public static void loadoccupationMap(Map<String, Integer> occupation) {
        occupationMap = new HashMap<String, Integer>();
        occupationMap.putAll(occupation);
    }

    public static void loadbankSubTypeMap(Map<String, Integer> bankSubType) {
        bankSubTypeMap = new HashMap<String, Integer>();
        bankSubTypeMap.putAll(bankSubType);
    }

    public static void loadgenderDataMap() {
        genderDataMap = new HashMap<String, Integer>();
        genderDataMap.put("Male", 0);
        genderDataMap.put("Female", 1);
        genderDataMap.put("Transgender", 2);
    }

    public static void loadmaritalstatusMap() {
        maritalstatusMap = new HashMap<String, Integer>();
        maritalstatusMap.put("Single", 0);
        maritalstatusMap.put("Married", 1);
        maritalstatusMap.put("Other", 2);
    }

    public static String getMmfProprty() {
        return mmfProprty;
    }

    public static void setMmfProprty(String mmfProprty) {
        LookupDataLoader.mmfProprty = mmfProprty;
    }

    public static String getConfigOption() {
        return configOption;
    }

    public static void setConfigOption(String configOption) {
        LookupDataLoader.configOption = configOption;
    }

    public static void loadStateListLookup(Map<String, String> stateListLoaded) {
        stateListLookup = new LinkedHashMap<String, String>();
        stateListLookup.putAll(stateListLoaded);
    }

    public static Map<String, String> getStateListLookup() {
        return stateListLookup;
    }

    public static HashMap<String, Integer> getDebitAccountMap() {
        return debitAccountMap;
    }

    public static void setDebitAccountMap(HashMap<String, Integer> debitAccountMap) {
        LookupDataLoader.debitAccountMap = debitAccountMap;
    }

    public static HashMap<String, Integer> getFrequencyMap() {
        return frequencyMap;
    }

    public static void setFrequencyMap(HashMap<String, Integer> frequencyMap) {
        LookupDataLoader.frequencyMap = frequencyMap;
    }

    public static HashMap<String, Integer> getDebitAccountTypemap() {
        return debitAccountTypemap;
    }

    public static void setDebitAccountTypemap(HashMap<String, Integer> debitAccountTypemap) {
        LookupDataLoader.debitAccountTypemap = debitAccountTypemap;
    }

    public static HashMap<String, Integer> getMandateActionMap() {
        return mandateActionMap;
    }

    public static void setMandateActionMap(HashMap<String, Integer> mandateActionMap) {
        LookupDataLoader.mandateActionMap = mandateActionMap;
    }

    public static void loadMandateDebitAccountMap() {
        debitAccountMap = new HashMap<String, Integer>();
        String[] debitaccount = PropertiesLoader.getPropertiesValue(DEBITACCOUNTTYPE).split(COMA);
        for (String account : debitaccount) {
            String[] value = account.split(COLON.trim());
            //value[0] returns debitaccount and value[1] returns its keyvalue
            debitAccountMap.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadMandateDebitAccountTypeMap() {
        debitAccountTypemap = new HashMap<String, Integer>();
        String[] debit_type = PropertiesLoader.getPropertiesValue(DEBITTYPE).split(COMA);
        for (String account : debit_type) {
            String[] value = account.split(COLON.trim());
            //value[0] returns debittype and value[1] returns its keyvalue
            debitAccountTypemap.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadMandateFrequencyMap() {
        frequencyMap = new HashMap<String, Integer>();
        String[] frequency = PropertiesLoader.getPropertiesValue(FREQUENCY).split(COMA);
        for (String account : frequency) {
            String[] value = account.split(COLON.trim());
            //value[0] returns frequency and value[1] returns its keyvalue
            frequencyMap.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadMandateActionMap() {
        mandateActionMap = new HashMap<String, Integer>();
        String[] option = PropertiesLoader.getPropertiesValue(OPTION).split(COMA);
        for (String account : option) {
            String[] value = account.split(COLON.trim());
            //value[0] returns option and value[1] returns its keyvalue
            mandateActionMap.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadHolidayStatusForVenues() {
        setHolyday_NSE(HoliDayCalenderUtil.checkHoliday(NSE));
        setHolyday_BSE(HoliDayCalenderUtil.checkHoliday(BSE));
    }

    public static boolean isHolyday_NSE() {
        return holyday_NSE;
    }

    public static void setHolyday_NSE(boolean holyday_NSE) {
        LookupDataLoader.holyday_NSE = holyday_NSE;
    }

    public static boolean isHolyday_BSE() {
        return holyday_BSE;
    }

    public static void setHolyday_BSE(boolean holyday_BSE) {
        LookupDataLoader.holyday_BSE = holyday_BSE;
    }

    /**
     * method to generate map of tablename and its mailtype from property file
     *
     * @return Map<String, String> - tablename mailtype map
     */
    public static void loadMailTypeList() {
        mailTypeList = new HashMap<String, String>();
        String[] propertyPair = PropertiesLoader.getPropertiesValue(TABLESNAME_MAILTYPE_MAP).split(COMA);
        for (String pair : propertyPair) {
            String tableNameMailTypePair[] = pair.split(":");
            mailTypeList.put(tableNameMailTypePair[1], tableNameMailTypePair[0]);
        }
    }

    public static HashMap<String, String> getMailTypeList() {
        return mailTypeList;
    }

    public static void setMailTypeList(HashMap<String, String> mailTypeList) {
        LookupDataLoader.mailTypeList = mailTypeList;
    }

    public static void loadNseSegmentsMap() {
        nseSegments = new HashMap<String, Integer>();
        String[] option = PropertiesLoader.getPropertiesValue(NSESEGMENTS).split(COMA);
        for (String account : option) {
            String[] value = account.split(COLON.trim());
            //value[0] returns option and value[1] returns its keyvalue
            nseSegments.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadbseSegmentsMap() {
        bseSegments = new HashMap<String, Integer>();
        String[] option = PropertiesLoader.getPropertiesValue(BSESEGMENTS).split(COMA);
        for (String account : option) {
            String[] value = account.split(COLON.trim());
            //value[0] returns option and value[1] returns its keyvalue
            bseSegments.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadDocumentoryEvidenceMap() {
        documentoryEvidence = new HashMap<String, Integer>();
        String[] option = PropertiesLoader.getPropertiesValue(DOCUMENTORYEVIDENCE).split(COMA);
        for (String account : option) {
            String[] value = account.split(COLON.trim());
            //value[0] returns option and value[1] returns its keyvalue
            documentoryEvidence.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadelEctronicContractTypeMap() {
        electronicContractType = new HashMap<String, Integer>();
        String[] option = PropertiesLoader.getPropertiesValue(ELECTRONICCONTRACTTYPE).split(COMA);
        for (String account : option) {
            String[] value = account.split(COLON.trim());
            //value[0] returns option and value[1] returns its keyvalue
            electronicContractType.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadAlertTypeMap() {
        alertType = new HashMap<String, Integer>();
        String[] option = PropertiesLoader.getPropertiesValue(ALERTTYPE).split(COMA);
        for (String account : option) {
            String[] value = account.split(COLON.trim());
            //value[0] returns option and value[1] returns its keyvalue
            alertType.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static void loadRelationWithMobileNumberMap() {
        relationWithMobileNumber = new HashMap<String, Integer>();
        String[] option = PropertiesLoader.getPropertiesValue(RELATIONWITHMOBILENUMBER).split(COMA);
        for (String account : option) {
            String[] value = account.split(COLON.trim());
            //value[0] returns option and value[1] returns its keyvalue
            relationWithMobileNumber.put(value[0], Integer.parseInt(value[1]));
        }
    }

    public static HashMap<String, Integer> getNseSegments() {
        return nseSegments;
    }

    public static void setNseSegments(HashMap<String, Integer> nseSegments) {
        LookupDataLoader.nseSegments = nseSegments;
    }

    public static HashMap<String, Integer> getBseSegments() {
        return bseSegments;
    }

    public static void setBseSegments(HashMap<String, Integer> bseSegments) {
        LookupDataLoader.bseSegments = bseSegments;
    }

    public static HashMap<String, Integer> getDocumentoryEvidence() {
        return documentoryEvidence;
    }

    public static void setDocumentoryEvidence(HashMap<String, Integer> documentoryEvidence) {
        LookupDataLoader.documentoryEvidence = documentoryEvidence;
    }

    public static HashMap<String, Integer> getElectronicContractType() {
        return electronicContractType;
    }

    public static void setElectronicContractType(HashMap<String, Integer> electronicContractType) {
        LookupDataLoader.electronicContractType = electronicContractType;
    }

    public static HashMap<String, Integer> getAlertType() {
        return alertType;
    }

    public static void setAlertType(HashMap<String, Integer> alertType) {
        LookupDataLoader.alertType = alertType;
    }

    public static HashMap<String, Integer> getRelationWithMobileNumber() {
        return relationWithMobileNumber;
    }

    public static void setRelationWithMobileNumber(HashMap<String, Integer> relationWithMobileNumber) {
        LookupDataLoader.relationWithMobileNumber = relationWithMobileNumber;
    }

    public static HashMap<String, Boolean> getVenueStatusMap() {
        return venueStatusMap;
    }

    public static void setVenueStatusMap(HashMap<String, Boolean> venueStatusMap) {
        LookupDataLoader.venueStatusMap = venueStatusMap;
    }

    public static Map<String, String> getCityListLookup() {
        return cityListLookup;
    }

    public static void setCityListLookup(Map<String, String> cityListLookup) {
        LookupDataLoader.cityListLookup = cityListLookup;
    }

    public static String getSebiLocation() {
        return sebiLocation;
    }

    public static void setSebiLocation(String sebiLocation) {
        LookupDataLoader.sebiLocation = sebiLocation;
    }

    public static void loadBankListLookup(Map<Integer, String> loadBankList) {
        LookupDataLoader.bankListLookup = loadBankList;
    }

    public static Map<Integer, String> getBankListLookup() {
        return bankListLookup;
    }

    public static void clearClassificationList() {
        indexList.clear();
        diversifiedEquityList.clear();
        debtDataList.clear();
        goldDataList.clear();
        internationalDataList.clear();
        midcapDataList.clear();
        derivativesList.clear();
    }

    public static List getMasterCities() {
        return masterCities;
    }

    public static void setMasterCities(List masterCities) {
        LookupDataLoader.masterCities = masterCities;
    }

    public static HashMap<String, String> getDependentDataMap() {
        return dependentDataMap;
    }

    public static void loadDependentDataMap() {
        dependentDataMap = new HashMap<String, String>();
        dependentDataMap.put("Mobile", "Mobile");
        dependentDataMap.put("Email", "Email");
        dependentDataMap.put("Both", "Both");
    }

    public static HashMap<String, String> getVenueTimeMap() {
        return venueTimeMap;
    }

    public static void setVenueTimeMap(HashMap<String, String> venueTimeMap) {
        LookupDataLoader.venueTimeMap = venueTimeMap;
    }

    public static List<ExchangeHolidayTb> getExchangeHoliday() {
        return exchangeHoliday;
    }

    public static void setExchangeHoliday(List<ExchangeHolidayTb> exchangeHoliday) {
        LookupDataLoader.exchangeHoliday = exchangeHoliday;
    }

    public static LinkedHashMap<String, String> getMmfsettings() {
        return mmfsettings;
    }

    public static void setMmfsettings(LinkedHashMap<String, String> mmfsettings) {
        LookupDataLoader.mmfsettings = mmfsettings;
    }

    public static OMSAdapterRv getOmsAdapterRv() {
        return omsAdapterRv;
    }

    public static void setOmsAdapterRv(OMSAdapterRv omsAdapterRv) {
        LookupDataLoader.omsAdapterRv = omsAdapterRv;
    }

    public static TibrvTransport getTibrvTransport() {
        return tibrvTransport;
    }

    public static void setTibrvTransport(TibrvTransport tibrvTransport) {
        LookupDataLoader.tibrvTransport = tibrvTransport;
    }

    public static String getAllowedOrigin() {
        return allowedOrigin;
    }

    public static void setAllowedOrigin(String allowedOrigin) {
        LookupDataLoader.allowedOrigin = allowedOrigin;
    }

    public static List<SymbolRecordVO> getDebtLiquidDataList() {
        return debtLiquidDataList;
    }

    public static HashMap<String, String> getNomineePoiMap() {
        return nomineePoiMap;
    }

    public static void loadNomineeProofMap() {
        nomineePoiMap = new HashMap<String, String>();
        nomineePoiMap.put("Aadhar", "Aadhar");
        nomineePoiMap.put("PAN", "PAN");
        nomineePoiMap.put("Photograh & Signature", "Photograh & Signature");
    }

    public static String getPublicCaptchaKey() {
        return publicCaptchaKey;
    }

    public static void setPublicCaptchaKey(String publicCaptchaKey) {
        LookupDataLoader.publicCaptchaKey = publicCaptchaKey;
    }

    public static String getContextRoot() {
        return contextRoot;
    }

    public static void setContextRoot(String contextRoot) {
        LookupDataLoader.contextRoot = contextRoot;
    }

    public static String getTemplatePath() {
        return templatePath;
    }

    public static void setTemplatePath(String templatePath) {
        LookupDataLoader.templatePath = templatePath;
    }

    public static HashMap<String, Integer> getAddressTypeList() {
        return addressTypeList;
    }

    public static void setAddressTypeList(HashMap<String, Integer> addressTypeList) {
        LookupDataLoader.addressTypeList = addressTypeList;
    }

    //View and Edit Address type in admin console
    public static void loadaddressTypeList() {
        addressTypeList = new HashMap<String, Integer>();
        addressTypeList.put("Residential/Business", 0);
        addressTypeList.put("Residential", 1);
        addressTypeList.put("Business", 2);
        addressTypeList.put("Registered Office", 3);
        addressTypeList.put("Unspecified", 4);

    }

    public static LinkedHashMap<Integer, String> getProofOfAddressForOthers() {
        return proofOfAddressForOthers;
    }

    public static void setProofOfAddressForOthers(LinkedHashMap<Integer, String> proofOfAddressForOthers) {
        LookupDataLoader.proofOfAddressForOthers = proofOfAddressForOthers;
    }

    public static void loadProofOfAddressForOthers() {
        proofOfAddressForOthers = new LinkedHashMap<Integer, String>();
        proofOfAddressForOthers.put(5, "Ration Card");
        proofOfAddressForOthers.put(6, "Lease/Sale");
        proofOfAddressForOthers.put(7, "Bank Statement");
        proofOfAddressForOthers.put(8, "Telephone Bill");
        proofOfAddressForOthers.put(9, "Electricity Bill");
        proofOfAddressForOthers.put(10, "Gas Bill");
    }

    public static List<SymbolRecordVO> getTaxPlanningDataList() {
        return taxPlanningDataList;
    }

    public static void setTaxPlanningDataList(List<SymbolRecordVO> taxPlanningDataList) {
        LookupDataLoader.taxPlanningDataList = taxPlanningDataList;
    }

}
