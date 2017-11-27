/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import com.git.mds.client.ConfigKeys;
import com.git.mds.client.session.SessionManager;
import com.git.mds.common.DataDictionary;
import com.git.mds.requester.HubRequestManager;
import com.git.mds.requester.exception.ServiceException;
import com.gtl.mmf.bao.ILookupDataLoaderBAO;
import com.gtl.mmf.entity.CitiesTb;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.OMSAdapterRv;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 07958
 */
public class LookupDataLoaderServlet extends HttpServlet implements IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.common.LookupDataLoaderServlet");
    private static final String MDS_RV_SERVICENAME = "mdsrequester";
    private static final String MDS_RV_CALLTIMEOUT = "3";
    private static final String MDS_RV_INITIALIZE_RV_FRAMEWORK = "true";
    private static final String MDS_RV_DISPATCHERS = "1";
    private static final String MDS_SERVICE_IMPL = "com.git.mds.requester.rv.MDSRequesterRV";
    private static Properties mdsProperties;
    private static HubRequestManager hubRequestManager;
    private static String MDS_RV_SENDSUBJECT;
    private static final String FLAIR_SERVICE_CLASS = "com.git.mds.client.connections.tcp.TCPConnection";
    private static Properties flairProperties;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LookupDataLoaderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LookupDataLoaderServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        int counter = 0;
        ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        LookupDataLoader.setContextRoot(config.getServletContext().getContextPath());
        LookupDataLoader.setConfigOption(getServletContext().getInitParameter("ConfigurationOption"));
        if (LookupDataLoader.getConfigOption().equalsIgnoreCase(SYSTEM)) {
            LOGGER.info("loading mmf properties file for application..");
            String propertyfile = getServletContext().getInitParameter("mmfproperties");
            LookupDataLoader.setMmfProprty(propertyfile);
            LOGGER.info("mmf properties loaded.");
        }
        LOGGER.info("Setting allowed croos domain for webservice");
        LookupDataLoader.setAllowedOrigin(getServletContext().getInitParameter(ALLOWED_ORIGINS));
        LOGGER.info("Setting allowed croos domain completed");

        LOGGER.info("Reading key for captcha ");
        LookupDataLoader.setPublicCaptchaKey(getServletContext().getInitParameter(PUBLIC_CAPTCHA_KEY));
        LOGGER.info("captcha key load completed");

        Map<String, String> loadCountryList = lookupDataLoaderBAO.loadCountryList();
        LookupDataLoader.loadCountryListLookup(loadCountryList);
        LOGGER.log(Level.INFO, "Country list loaded.");

        List<Object> response = lookupDataLoaderBAO.loadAllCity();
        Map<String, String> loadCityList = (Map<String, String>) response.get(ZERO);
        LookupDataLoader.loadCityListLookup(loadCityList);
        LookupDataLoader.loadAllMasterCity((List<CitiesTb>) response.get(ONE));
        LOGGER.log(Level.INFO, "City list loaded.");

        Map<String, String> loadStateList = lookupDataLoaderBAO.loadStateList();
        LookupDataLoader.loadStateListLookup(loadStateList);
        LOGGER.log(Level.INFO, "State list loaded.");

//      ************   USED FOR QUALIFICATION INFORMATION LOOKUP/not using now   *****************************
//        Map<String, String> loadQualificationList = lookupDataLoaderBAO.loadQualificationList();
//        LookupDataLoader.loadQualificationList(loadQualificationList);
//        LOGGER.log(Level.INFO, "Qualification list loaded.");
//
//        Map<String, String> loadInstitueList = lookupDataLoaderBAO.loadInstitueList();
//        LookupDataLoader.loadInstitutionList(loadInstitueList);
//        LOGGER.log(Level.INFO, "Institution list loaded.");
//
//        Map<String, String> loadBrockerList = lookupDataLoaderBAO.loadBrockerList();
//        LookupDataLoader.loadBrockerListLookup(loadBrockerList);
//        LOGGER.log(Level.INFO, "Brocker list loaded.");
//        ************************** END OF COMMENT  *********************************************************      
        LookupDataLoader.loadYearList();
        LOGGER.log(Level.INFO, "Year list loaded.");

//        LookupDataLoader.loaddocStatusList();
//        LOGGER.log(Level.INFO, "Document status list loaded.");
        LookupDataLoader.loadUserTypeList();
        LOGGER.log(Level.INFO, "User type list loaded.");

        Map<String, String> sysConfigList = lookupDataLoaderBAO.loadSysConfigParams();
        LookupDataLoader.loadConfigparams(sysConfigList);
        LOGGER.log(Level.INFO, "Configuration list loaded.");

        Map<Integer, List<ButtonDetailsVo>> advisorButtonsList = lookupDataLoaderBAO.loadUserButtons(ADVISOR);
        LookupDataLoader.loadAdvisorButtonsList(advisorButtonsList);
        LOGGER.log(Level.INFO, "Advisor Buttons list loaded.");

        Map<Integer, List<ButtonDetailsVo>> investorButtonsList = lookupDataLoaderBAO.loadUserButtons(INVESTOR);
        LookupDataLoader.loadInvestorButtonsList(investorButtonsList);
        LOGGER.log(Level.INFO, "Investor Buttons list loaded.");

        LookupDataLoader.loadUserActiveStatusList();
        LOGGER.log(Level.INFO, "UserStatus list loaded.");

        Map<String, Integer> aumPayableFrequencyList = lookupDataLoaderBAO.loadAdvisoryContractFreqLookup("AUM_PAYABLE");
        LookupDataLoader.loadAUMPayableFrequencyList(aumPayableFrequencyList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (AUM_PAYABLE)");

        Map<String, Integer> performanceFeeFreqList = lookupDataLoaderBAO.loadAdvisoryContractFreqLookup("PERFORMANCE_FEE");
        LookupDataLoader.loadPerformanceFeeFreqList(performanceFeeFreqList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (PERFORMANCE_FEE)");

        Map<String, Integer> contractDurationFreqList = lookupDataLoaderBAO.loadAdvisoryContractFreqLookup("DURATION_FREQ");
        LookupDataLoader.loadContractDurationFreqList(contractDurationFreqList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (DURATION_FREQ)");

        Map<String, Integer> contractDurationList = lookupDataLoaderBAO.loadAdvisoryContractFreqLookup("DURATION");
        LookupDataLoader.loadContractDurationList(contractDurationList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (DURATION)");

        Map<String, Integer> mgmtFixedAmountList = lookupDataLoaderBAO.loadAdvisoryContractFreqLookup("FIXED_AMOUNT");
        LookupDataLoader.loadContractFixedAmountList(mgmtFixedAmountList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (FIXED_AMOUNT)");

        Map<String, BigDecimal> mgmtFeeVariableAmountList = lookupDataLoaderBAO.loadAdvisoryContractPercLookup("MGMT_FEE_VARIABLE");
        LookupDataLoader.loadVariableAmountList(mgmtFeeVariableAmountList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (MGMT_FEE_VARIABLE)");

        Map<String, BigDecimal> performanceFeeList = lookupDataLoaderBAO.loadAdvisoryContractPercLookup("PERFORMANCE_FEE");
        LookupDataLoader.loadPerformanceFeeList(performanceFeeList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (PERFORMANCE_FEE)");

        Map<String, BigDecimal> performanceFeeThresholdList = lookupDataLoaderBAO.loadAdvisoryContractPercLookup("PERFORMANCE_FEE_THRESHOLD");
        LookupDataLoader.loadPerformanceFeeThresholdList(performanceFeeThresholdList);
        LOGGER.log(Level.INFO, "Advisory service contract look data loaded. (PERFORMANCE_FEE_THRESHOLD)");

        LookupDataLoader.loadSortByAdvisorNetwork();
        LOGGER.log(Level.INFO, "Sort by list advisor network loaded.");

        LookupDataLoader.loadSortByInvestorNetwork();
        LOGGER.log(Level.INFO, "Sort by list investor network loaded.");

        LookupDataLoader.loadAdvisorNetworkFilter();
        LOGGER.log(Level.INFO, "Investor network filter loaded.");

        LookupDataLoader.loadInvestorNetworkFilter();
        LOGGER.log(Level.INFO, "Advisor network filter loaded.");

        LOGGER.log(Level.INFO, "Questionnaire data loading... ");
        LookupDataLoader.loadQuestionnaireList(lookupDataLoaderBAO.loadQuestionnaire());
        LOGGER.log(Level.INFO, "Questionnaire data loaded");

        LOGGER.log(Level.INFO, "PortfolioTypes data loading... ");
        LookupDataLoader.loadPortFolioType(lookupDataLoaderBAO.loadPortFolioTypes());
        LOGGER.log(Level.INFO, "PortfolioTypes data loaded");

        List<Map<String, String>> loadMFClassification = lookupDataLoaderBAO.loadMFMap();
        LookupDataLoader.loadMFClassification(loadMFClassification);
        LOGGER.log(Level.INFO, "MF classification Map loaded.");

        //************************************************
        LOGGER.log(Level.INFO, "############---------Downloading symbol files...---------################");
        String[] symbolFiles = PropertiesLoader.getPropertiesValue("symbolfiles").split(COMA);
        String fileLocation = PropertiesLoader.getPropertiesValue("symbolfilepath");
        LOGGER.log(Level.INFO, "Symbol file download location:{0}", fileLocation);
        LOGGER.log(Level.INFO, "New Symbol MAP Preparation {0}", new Date().getTime() + " , ");
        for (String string : symbolFiles) {
            counter++;
            boolean downloadStatus = LookupDataLoader.downloadNSESymbol(fileLocation, string, string);
            LOGGER.log(Level.INFO, "[{0}]- downloaded status : [{1}]", new Object[]{string, downloadStatus});
            if (downloadStatus) {
                LookupDataLoader.prepareNewSymbolMap(fileLocation, string, counter);
            }
        }
        LOGGER.log(Level.INFO, "New Symbol MAP Preparation {0}", new Date().getTime() + " , ");
        LOGGER.log(Level.INFO, "Symbol files downloaded");

        LOGGER.log(Level.INFO, "Rate values loadeing...");
        LookupDataLoader.loadRateAdvisorValueList();
        LOGGER.log(Level.INFO, "Rate values loaded.");

        LOGGER.info("Asset classes loading... ");
        LookupDataLoader.loadAssetClasses(lookupDataLoaderBAO.loadAssetClasses());
        LOGGER.info("Asset classes loaded");

        LOGGER.log(Level.INFO, "ReviewFreequency data loading... ");
        LookupDataLoader.loadReviewFreequency(lookupDataLoaderBAO.loadAdvisoryContractFreqLookup("REVIEW_FEQ"));
        LOGGER.log(Level.INFO, "ReviewFreequency  data loaded");

        LOGGER.log(Level.INFO, "BenchMark data loading... ");
        LookupDataLoader.loadBenchMark(lookupDataLoaderBAO.loadBenchMArk());
        LOGGER.log(Level.INFO, "BenchMark  data loaded");

        LOGGER.log(Level.INFO, "Price tolerance list loading... ");
        LookupDataLoader.loadPriceToleranceList();
        LOGGER.log(Level.INFO, "Price tolerance list loaded");

        LOGGER.log(Level.INFO, "Feed Provider starting..");
        String fetchFrom = PropertiesLoader.getPropertiesValue(FETCH_MARKET_DATA_FROM).trim();
        if (fetchFrom.equalsIgnoreCase(MDS)) {
            LOGGER.log(Level.INFO, "HubRequestManager initialization starting..");
            this.startHubRequestManager();
            LOGGER.log(Level.INFO, "HubRequestManager initialization completed");
        } else {
            LOGGER.log(Level.INFO, "Flair initialization task starting..");
            this.startFlair();
            LOGGER.log(Level.INFO, "Flair initialization task completed");
        }
        LOGGER.log(Level.INFO, "Feed Provider initialization completed");

        LOGGER.log(Level.INFO, "OMS Service configuration starting..");
        LookupDataLoader.configOMSService();
        LOGGER.log(Level.INFO, "OMS Service configuration completed");

//        LOGGER.log(Level.INFO, "Initialixzing OMS RV transport");
//        this.initializeOmsRvTransport();
//        LOGGER.log(Level.INFO, "Initialixzing OMS RV transport completed");
        //LOGGER.log(Level.INFO, "Process benchmark data csv starting...");
        /*
         try {
         LookupDataLoader.processBenchMarkData(config.getServletContext().getRealPath("resources"), 1);
         } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         }
         LOGGER.log(Level.INFO, "Process benchmark data csv completed");
         */
        LOGGER.info("Asset class map loading.");
        LookupDataLoader.loadAssetClassMap(lookupDataLoaderBAO.loadAssetClassVOs());
        LOGGER.info("Asset class map loading completed.");
        
        LOGGER.info("Setting resourse path");
        LookupDataLoader.setResourcesPath(config.getServletContext().getRealPath("resources"));
        LOGGER.info("Setting resourse path completed");
        
        LOGGER.info("Setting image path");
        LookupDataLoader.setTemplatePath(config.getServletContext().getRealPath("img"));
        LOGGER.info("Setting image path completed");
        
        LOGGER.info("resident staus is loading..");
        Map<String, Integer> loadResidentStatusList = lookupDataLoaderBAO.loadResidentStatusList();
        LookupDataLoader.loadResidentStatusMap(loadResidentStatusList);
        LOGGER.info("resident staus  loading completed..");

        LOGGER.info("Proof Of Identity is loading..");
        Map<String, Integer> loadProofOfIdentityList = lookupDataLoaderBAO.loadProofOfIdentityList();
        LookupDataLoader.loadproofOfIdentityMap(loadProofOfIdentityList);
        LOGGER.info("ProofOfIdentity loading completed..");

        LOGGER.info("Proof Of Address is loading..");
        LinkedHashMap<String, Integer> loadProofOfAddressList = lookupDataLoaderBAO.loadProofOfAddressList();
        LookupDataLoader.loadproofOfAddressMap(loadProofOfAddressList);
        LOGGER.info("Proof Of Address loading completed..");
        
        LOGGER.info("Proof Of Address loading for displaying in Others..");
        LookupDataLoader.loadProofOfAddressForOthers();
        LOGGER.info("Proof Of Address loading for displaying in Others completed..");
        
        LOGGER.info("Dpids is loading..");
        Map<String, Integer> loadDpidsList = lookupDataLoaderBAO.loadDpidsList();
        LookupDataLoader.loaddpidsMap(loadDpidsList);
        LOGGER.info("Dpids loading completed..");

        LOGGER.info("Openning AccountType is loading..");
        Map<String, Integer> loadOpenningAccountTypeList = lookupDataLoaderBAO.loadOpenningAccountTypeList();
        LookupDataLoader.loadopenningAccountTypeMap(loadOpenningAccountTypeList);
        LOGGER.info("Openning AccountType loading completed..");

        LOGGER.info("DematAccount is loading..");
        Map<String, Integer> loadDematAccountList = lookupDataLoaderBAO.loadDematAccountList();
        LookupDataLoader.loaddematAccountMap(loadDematAccountList);
        LOGGER.info("DematAccount loading completed..");

        LOGGER.info("TradingAccount is loading..");
        Map<String, Integer> loadTradingAccountList = lookupDataLoaderBAO.loadTradingAccountList();
        LookupDataLoader.loadtradingAccountMap(loadTradingAccountList);
        LOGGER.info("TradingAccount loading completed..");

        LOGGER.info("Incomerange is loading..");
        LinkedHashMap<String, Integer> loadIncomerangeList = lookupDataLoaderBAO.loadIncomerangeList();
        LookupDataLoader.loadincomerangeMap(loadIncomerangeList);
        LOGGER.info("Incomerange loading completed..");

        LOGGER.info("Occupation is loading..");
        Map<String, Integer> loadOccupationList = lookupDataLoaderBAO.loadOccupationList();
        LookupDataLoader.loadoccupationMap(loadOccupationList);
        LOGGER.info("Occupation loading completed..");

        LOGGER.info("BankSubType is loading..");
        Map<String, Integer> loadBankSubTypeList = lookupDataLoaderBAO.loadBankSubTypeList();
        LookupDataLoader.loadbankSubTypeMap(loadBankSubTypeList);
        LOGGER.info("BankSubType loading completed..");

        LOGGER.info("genderData is loading..");
        LookupDataLoader.loadgenderDataMap();
        LOGGER.info("genderData loading completed..");

        LOGGER.info("maritalstatus is loading..");
        LookupDataLoader.loadmaritalstatusMap();
        LOGGER.info("maritalstatus loading completed..");

        LOGGER.info("mandatedebitaccount is loading..");
        LookupDataLoader.loadMandateDebitAccountMap();
        LOGGER.info("mandatedebitaccount loading completed..");

        LOGGER.info("mandatedebitaccounttype is loading..");
        LookupDataLoader.loadMandateDebitAccountTypeMap();
        LOGGER.info("mandatedebitaccounttype loading completed..");

        LOGGER.info("mandatefrequency is loading..");
        LookupDataLoader.loadMandateFrequencyMap();
        LOGGER.info("mandatefrequency loading completed..");

        LOGGER.info("mandateaction is loading..");
        LookupDataLoader.loadMandateActionMap();
        LOGGER.info("mandateaction loading completed..");

        Map<Integer, String> loadBankList = lookupDataLoaderBAO.loadBankList();
        LookupDataLoader.loadBankListLookup(loadBankList);
        LOGGER.log(Level.INFO, "Bank list loaded.");

        LOGGER.info("Checking Venue holiday..");
        boolean chekHolidayEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_VENUE_HOLIDAY_ENABLED));
        if (chekHolidayEnabled) {
            LookupDataLoader.loadHolidayStatusForVenues();
        }
        LOGGER.info("Checking venue holiday completed..");

        LOGGER.info("Loading Alert type l alerts to investors by stock exhcanges");
        LookupDataLoader.loadAlertTypeMap();
        LOGGER.info("Completed Loading Alert type l alerts to investors by stock exhcanges..");

        LOGGER.info("Loading documentory Evidence of financial details for derivative clients..");
        LookupDataLoader.loadDocumentoryEvidenceMap();
        LOGGER.info("Completed documentory Evidence of financial details for derivative clients..");

        LOGGER.info("Loading  Electronic Contract ..");
        LookupDataLoader.loadelEctronicContractTypeMap();
        LOGGER.info("Completed Electronic Contract..");

        LOGGER.info("Loading trading preferences in NSE exchange..");
        LookupDataLoader.loadNseSegmentsMap();
        LOGGER.info("Completed Loading  trading preferences in NSE exchange..");

        LOGGER.info("Loading  trading preferences in BSE exchange..");
        LookupDataLoader.loadbseSegmentsMap();
        LOGGER.info("Completed Loading  trading preferences in NSE exchange..");

        LOGGER.info("Loading relation With Mobile Number registerd ..");
        LookupDataLoader.loadRelationWithMobileNumberMap();
        LOGGER.info("Completed Loading relation With Mobile Number registerd ..");

        LOGGER.info("Loading Table names for resending failed mails is loading..");
        LookupDataLoader.loadMailTypeList();
        LOGGER.info("Completed Loading Table names for resending failed mails is loading..");

        LOGGER.info("dependent relation map is loading..");
        LookupDataLoader.loadDependentDataMap();
        LOGGER.info("dependent relation map loading completed..");

        LOGGER.info("Loading Venue holiday from database..");
        List<ExchangeHolidayTb> holidayMap = lookupDataLoaderBAO.loadHolidayMap();
        LookupDataLoader.loadExchangeHoliday(holidayMap);
        LOGGER.info("Loading Venue holiday completed..");

        LOGGER.info("Loading MMF Advanced Settings");
        LinkedHashMap<String, String> settings = lookupDataLoaderBAO.loadAdvancedSettings();
        LookupDataLoader.loadAdminAdvancedSettings(settings);
        LOGGER.info("Loading Venue holiday completed..");

        LOGGER.log(Level.INFO, "PortfolioStyle data loading... ");
        LookupDataLoader.loadPortFolioStyle(lookupDataLoaderBAO.loadPortFolioStyle());
        LOGGER.log(Level.INFO, "PortfolioStyle data loaded");

        LOGGER.log(Level.INFO, "PortfolioSize data loading... ");
        LookupDataLoader.loadPortFolioSize(lookupDataLoaderBAO.loadPortFolioSize());
        LOGGER.log(Level.INFO, "PortfolioSize data loaded");

        LOGGER.log(Level.INFO, "Nominee POI map loading... ");
        LookupDataLoader.loadNomineeProofMap();
        LOGGER.log(Level.INFO, "Nominee POI map loaded");
        
        //View and Edit Address type in admin console
        LOGGER.info("Address Type List is loading..");
        LookupDataLoader.loadaddressTypeList();
        LOGGER.info("Address Type List loading completed..");
    }

    /**
     * Connection to MDS
     */
    public void startHubRequestManager() {
        try {
            String realPath = getServletContext().getRealPath(DOUBLE_FORWARED_SLAH + "WEB-INF");
            MDS_RV_SENDSUBJECT = PropertiesLoader.getPropertiesValue(MDS_RV_TOPIC);

            String path = System.getProperty("java.class.path");
            String path1 = System.getProperty("java.library.path");

            mdsProperties = new Properties();
            DataDictionary.setDefFile(realPath + DOUBLE_FORWARED_SLAH + "classes" + DOUBLE_FORWARED_SLAH + "FIELDS.DEF");
            mdsProperties.setProperty("rvconfig", realPath + DOUBLE_FORWARED_SLAH + "classes" + DOUBLE_FORWARED_SLAH + "rvconfig.xml");
            mdsProperties.setProperty("rvDispatchers", MDS_RV_DISPATCHERS);
            mdsProperties.setProperty("sendsubject", MDS_RV_SENDSUBJECT);
            mdsProperties.setProperty("rv_service_name", MDS_RV_SERVICENAME);
            mdsProperties.setProperty("callTimeoutRV", MDS_RV_CALLTIMEOUT);
            mdsProperties.setProperty("initilizeRVFramework", MDS_RV_INITIALIZE_RV_FRAMEWORK);
            mdsProperties.setProperty("hubRequestService", MDS_SERVICE_IMPL);
            hubRequestManager = HubRequestManager.getInstance();
            hubRequestManager.init(mdsProperties);
            hubRequestManager.start();

        } catch (ServiceException ex) {
            LOGGER.log(Level.SEVERE, "HubRequestManager start failed. {0}", ex.getMessage());
        }
    }

    /**
     * Connection to Flair
     */
    public void startFlair() {
        try {
            String realPath = getServletContext().getRealPath(DOUBLE_FORWARED_SLAH + "WEB-INF");
            DataDictionary.setDefFile(realPath + DOUBLE_FORWARED_SLAH + "classes" + DOUBLE_FORWARED_SLAH + "FIELDS.DEF");
            flairProperties = new Properties();
            flairProperties.put("classname", FLAIR_SERVICE_CLASS);
            flairProperties.put("param", SPACE);
            flairProperties.put("address", PropertiesLoader.getPropertiesValue(FLAIR_CONNECTION_ADDRESS).trim());
            flairProperties.put("port", PropertiesLoader.getPropertiesValue(FLAIR_CONNECTION_PORT).trim());
            flairProperties.put("timeout", PropertiesLoader.getPropertiesValue(FLAIR_TIMEOUT).trim());
            flairProperties.put("session_name", PropertiesLoader.getPropertiesValue(FLAIR_SESSION_NAME).trim());
            flairProperties.put(ConfigKeys.CLIENTAPIVERSION, PropertiesLoader.getPropertiesValue(FLAIR_CLIENT_API_VERSION).trim());//"4.3.17"
            SessionManager.getInstance().setConfig(flairProperties);
            SessionManager.getInstance().init();
            SessionManager.getInstance().start();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Flair start failed. {0}", ex.getMessage());
        }
    }

    /**
     * Method to Initialize OMS Backend RV Transport
     */
    public void initializeOmsRvTransport() {
        try {
            OMSAdapterRv adapterRv = new OMSAdapterRv();
            adapterRv.initBackendRv();
            adapterRv.subscribeSubject(EMPTY_STRING, EMPTY_STRING, null);
            LookupDataLoader.setOmsAdapterRv(adapterRv);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "TibrvException occcured in connect of FrontEndRVTransporter", StackTraceWriter.getStackTrace(ex));
        }
    }
}
