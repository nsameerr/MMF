/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.bao.RegistrationBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.report.util.RegistrationPdfDataPreparation;
import com.gtl.mmf.service.util.AgeUtil;
import com.gtl.mmf.service.util.ConversionMethods;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.ADVISOR_DATA;
import static com.gtl.mmf.service.util.IConstants.AS_AND_WHEN_PRESENTED;
import static com.gtl.mmf.service.util.IConstants.BOTH;
import static com.gtl.mmf.service.util.IConstants.CORRESPONDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.DEPOSITORY;
import static com.gtl.mmf.service.util.IConstants.DOCUMENTARY_EVIDENCE;
import static com.gtl.mmf.service.util.IConstants.DOC_EVIDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.ELECTRONIC_NOTE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.FALSE;
import static com.gtl.mmf.service.util.IConstants.FIRSTPAGE;
import static com.gtl.mmf.service.util.IConstants.FROM_PAYMENT_RESPONSE;
import static com.gtl.mmf.service.util.IConstants.GBNPP;
import static com.gtl.mmf.service.util.IConstants.IDENTITY_PROOF;
import static com.gtl.mmf.service.util.IConstants.IN;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.I_DONT_WANT_TO_OPT_FOR_DERIVATIVE_TRADING;
import static com.gtl.mmf.service.util.IConstants.TENTHOUSAND;
import static com.gtl.mmf.service.util.IConstants.MANDATEFILE;
import static com.gtl.mmf.service.util.IConstants.MANDATE_DATA;
import static com.gtl.mmf.service.util.IConstants.NEW_APPLICANT;
import static com.gtl.mmf.service.util.IConstants.MAX_AMT;
import static com.gtl.mmf.service.util.IConstants.MMF_FINANCIAL_ADVISORS_PRIVATE_LIMITED;
import static com.gtl.mmf.service.util.IConstants.NO;
import static com.gtl.mmf.service.util.IConstants.ORDINARY_RESIDENT;
import static com.gtl.mmf.service.util.IConstants.OTHERS;
import static com.gtl.mmf.service.util.IConstants.PAN_SUBMITTED_NAME;
import static com.gtl.mmf.service.util.IConstants.PERMENENT_NAME;
import static com.gtl.mmf.service.util.IConstants.REGDATA;
import static com.gtl.mmf.service.util.IConstants.REG_PAGE_DATA;
import static com.gtl.mmf.service.util.IConstants.RESIDENT_INDIVIDUAL;
import static com.gtl.mmf.service.util.IConstants.SB;
import static com.gtl.mmf.service.util.IConstants.SEBI_FILE_PATH;
import static com.gtl.mmf.service.util.IConstants.SELF;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TENTHOUSAND;
import static com.gtl.mmf.service.util.IConstants.TRADING_ACCOUNT_AND_NSDL_DEMAT_ACCOUNT;
import static com.gtl.mmf.service.util.IConstants.TRUE;
import static com.gtl.mmf.service.util.IConstants.TWO_MB;
import static com.gtl.mmf.service.util.IConstants.VALIDITY_CORRESPONDENCE;
import static com.gtl.mmf.service.util.IConstants.VALIDITY_PERMENENT;
import static com.gtl.mmf.service.util.IConstants.V2_AFTER_REG_PDF;
import static com.gtl.mmf.service.util.IConstants.YES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_STRING;
import static com.gtl.mmf.service.util.IConstants.dd_MM_yyyy;
import com.gtl.mmf.service.util.IdGenarator;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.AdvisorRegistrationVo;
import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.service.vo.InvestorTempRegistrationVo;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationDataProcessingVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.TempAdvVo;
import com.gtl.mmf.service.vo.TempInvNextPageVo;
import com.gtl.mmf.service.vo.TempInvVo;
import com.gtl.mmf.service.vo.UserProfileVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.gtl.mmf.webapp.util.FileUploadUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.bao.RegistrationBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.report.util.RegistrationPdfDataPreparation;
import com.gtl.mmf.service.util.AgeUtil;
import com.gtl.mmf.service.util.ConversionMethods;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IdGenarator;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.AdvisorRegistrationVo;
import com.gtl.mmf.service.vo.CompleteTempInvVo;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationDataProcessingVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.TempInvNextPageVo;
import com.gtl.mmf.service.vo.TempInvVo;
import com.gtl.mmf.service.vo.UserProfileVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.gtl.mmf.webapp.util.FileUploadUtil;

@Named("userRegistrationBean2")
@Scope("application")
public class UserRegistrationBean2 {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.UserRegistrationBean2");
    private static final String USER_PROFILE = "userProfile";
    private UserProfileVO userProfile;
    private Map<String, String> countryList;
    private static Map<String, String> state;
    private Map<String, String> stateList;
    private Map<String, String> stateList2;
    private Map<String, String> stateListBank2;
//    private Map<String, List<String>> stateListBank2;
    private Map<String, String> stateListNominee;
    private Map<String, String> stateListNomineeMinor;

    private Map<String, String> cityList;
    private Map<String, String> cityList2;
    private Map<String, String> cityListBank2;
//    private List<String> cityListBank2;
    private Map<String, String> yearList;
    private Map<String, String> cityListNominee;
    private Map<String, String> cityListNomineeMinor;
    private List<BankVo> filteredBank = new ArrayList<BankVo>();

    private boolean status;
    private static final String EMAIL_ID = "emailId";
    @Autowired
    private IUserProfileBAO userProfileBAO;
    @Autowired
    private RegistrationBAO registrationBAO;

    private boolean emailExists;
    private RegistrationVo registrationVo;
    private AdvisorRegistrationVo advisorRegistrationVo;
    private boolean newApplicant;
    private boolean advisor;
    private static final String PDF = "pdf";
    boolean firstAddress;
    boolean firstAddress2;
    boolean nomineeCountry;
    boolean nomineeMinorCountry;
    private MandateVo mandateVo;
    boolean enableToDate;
    private boolean showIfsc = true;
    private boolean showMicr;
    private static final String SHOW_PDF = "ShowPDF";
    private static final String MSG_FROMDATE = "Past date cannot used as start date.";
    private static final String MSG_TODATE = "Past date cannot used as end date.";
    private static final String ERRMSG_TODATE = "Please select From date.";
    private static final String CREATE = "Create";
    private boolean endDateInvalid;
    private static final String PDF_PATH = "resources/registration/";
    private RegistrationDataProcessingVo regDataVo;
    private String otherEvidence;
    private static final String PANNO_FIELD = "frm_investor_profile:panno_in";
    private static final String PANNO_MESSAGE = "PAN number already registered";
    private Part filepart;
    public boolean firstpage;
    String raccontNumber;
    private static final String PAN = "PAN";
    private Map<String, String> cityMap;
    private TempInvVo tempInv;
    private static final String TEMP_INVESTOR_ONE = "TempInvVo";
    private TempInvNextPageVo tempInvNext;
    private static final String TEMP_INVESTOR_TWO = "TempInvNextPageVo";
    private CompleteTempInvVo completeTempInvVo;
    private InvestorTempRegistrationVo tempRegistrationVo;
    private String sebiPath = PropertiesLoader.getPropertiesValue(SEBI_FILE_PATH);
    private String panPath = PropertiesLoader.getPropertiesValue(IDENTITY_PROOF);
    private String coresProofPath = PropertiesLoader.getPropertiesValue(VALIDITY_CORRESPONDENCE);
    private String permProofPath = PropertiesLoader.getPropertiesValue(VALIDITY_PERMENENT);
    private String docEvdPath = PropertiesLoader.getPropertiesValue(DOCUMENTARY_EVIDENCE);
    private boolean dontknowIfsc = false;
    private List<String> branchList;
    private TempAdvVo tempAdvVo;
    private static final String TEMP_ADVISOR = "TempAdvVo";
    private static final String COMPLETE_INVESTOR = "CompleteTempInvVo";
    @Context
    private HttpServletRequest httpRequest;
    @Context
    private HttpServletResponse httpresponse;
    @Context
    private UriInfo context;

//    
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	mainMethod(request,response);
//    }
//    private void mainMethod(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		try {
//		
//
//	    	afterInit(session);
//	    	LOGGER.log(Level.INFO, "after init success");
//			//preprocess page1
//	    	doActionNextpageListener(session);
//	    	LOGGER.log(Level.INFO, "doActionNextpageListner success");
//	    	//post process page 1
//	    	doActionNextpage(session);
//	    	LOGGER.log(Level.INFO, "doActionNextpage success");
//	    	//generate pdf
//	    	generatePdf(session);
//	    	LOGGER.log(Level.INFO, "pdf generated success");
//	    	//userRegistrationBean2.showpdf();
//	    	LOGGER.log(Level.INFO, "call showpdf success");
//	    	saveInvestor(session);
//	    	LOGGER.log(Level.INFO, "call saveInvestor success");
//	    	save();
//	    	LOGGER.log(Level.INFO, "call save success");
//			
//		} catch (Exception e) {
//			LOGGER.log(Level.SEVERE, "error in main method", e);
//			LOGGER.log(Level.SEVERE, null, e);
//		}
//		
//    }
    /**
     *
     */
    @PostConstruct
    public void afterInit() {
        LOGGER.log(Level.INFO, "inside afterinit for userRegistartionBean2 new");
        /**
         * ***** CONTENT OF THIS METHOD HAS BEEN MOVED TO prepareBean()
         * -sumeet.pol *******
         */
    }

    private void datalistLoading() {
        this.setCountryList(LookupDataLoader.getCountryList());
        this.setStateList(LookupDataLoader.getStateListLookup());
        this.setStateList2(this.getStateList());
        this.setStateListBank2(LookupDataLoader.getStateListLookup());
//        this.setStateListBank2(LookupDataLoader.getStatecitymap());
        this.setStateListNominee(this.getStateList());
        this.setStateListNomineeMinor(this.getStateList());

        this.setCityList(LookupDataLoader.getCityListLookup());
        this.setCityList2(this.getCityList());
        this.setCityListBank2(LookupDataLoader.getCityListLookup());
//        this.setCityListBank2(new ArrayList<String>());
        this.setCityListNominee(this.getCityList());
        this.setCityListNomineeMinor(this.getCityList());
    }

    public void doActionNextpageListener() {
        HttpSession session = httpRequest.getSession();
        tempInv = new TempInvVo();
        completeTempInvVo = new CompleteTempInvVo();
        if (session.getAttribute(COMPLETE_INVESTOR) != null) {
            completeTempInvVo = (CompleteTempInvVo) session.getAttribute(COMPLETE_INVESTOR);
        }

        status = true;
        LOGGER.log(Level.INFO, "######COPY OF PAN File Checking#############");

        String panFile = completeTempInvVo.getPanFilePath();
        LOGGER.log(Level.INFO, "###### Uploading COPY OF PAN #############{0}", panFile);

        if (panFile != null) {
            status = true;
            registrationVo.setPanUploadFile(panFile);
            completeTempInvVo.setPanFilePath(panFile);
        }

        String corespondenceAddress = completeTempInvVo.getCorsFilePath();
        LOGGER.log(Level.INFO, "###### Uploading VALIDITY CORRESPONDENCE#############{0}", corespondenceAddress);

        if (corespondenceAddress != null) {
            status = true;
            registrationVo.setCorespondenceAddressFile(corespondenceAddress);
            completeTempInvVo.setCorsFilePath(corespondenceAddress);
        }

        LOGGER.log(Level.INFO, "######PERMENENT Address File Checking#############");

        String permenentAddressValidity = completeTempInvVo.getPrmntFilePath();
        LOGGER.log(Level.INFO, "######PERMENENT Address File Checking#############{0}", permenentAddressValidity);
        if (permenentAddressValidity != null) {
            status = true;
            registrationVo.setPermenentAddressFile(permenentAddressValidity);
            completeTempInvVo.setPrmntFilePath(permenentAddressValidity);
        }

        mandateVo.setContactNmbr(registrationVo.getMobileNumber() == null ? EMPTY_STRING : "091" + registrationVo.getMobileNumber());
        if (completeTempInvVo != null) {
            session.setAttribute(COMPLETE_INVESTOR, completeTempInvVo);
        }

    }

    public String doActionNextpage() {
        String redirectTo = "";
        status = true;
        if (status) {
            this.mandateVo.setCustomerBankName(registrationVo.getBankname());
            this.mandateVo.setCustomerAccountNumber(registrationVo.getBankAccountNumber());
            this.mandateVo.setIfscNumber(registrationVo.getIfsc());
            this.mandateVo.setMicrNumber(registrationVo.getMicrCode());
            this.mandateVo.setCustomerDebitAccount(registrationVo.getBankSubType().equalsIgnoreCase("Current Account") ? "CA" : "SB");
            this.mandateVo.setCustomerName(registrationVo.getBeneficiaryName());
            Map<String, Object> storedValues = (Map<String, Object>) httpRequest.getSession().getAttribute(STORED_VALUES);
            storedValues.put(REG_PAGE_DATA, registrationVo);
            storedValues.put(MANDATE_DATA, mandateVo);
            storedValues.put("RegData", true);
            //getSessionMap().put(STORED_VALUES, storedValues);
            httpRequest.getSession().setAttribute(STORED_VALUES, storedValues);
            redirectTo = "/pages/reg_investor_profile2?faces-redirect=true";
        }
        return redirectTo;
    }

    public void doActionBackpageListener(ActionEvent event) {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //tempInvNext = new TempInvNextPageVo();
        CompleteTempInvVo completeTempInvVo = new CompleteTempInvVo();
//        if (session.getAttribute(TEMP_INVESTOR_TWO) != null) {
//            tempInvNext = (TempInvNextPageVo) session.getAttribute(TEMP_INVESTOR_TWO);
//        }
        if (session.getAttribute(COMPLETE_INVESTOR) != null) {
            completeTempInvVo = (CompleteTempInvVo) session.getAttribute(COMPLETE_INVESTOR);
        }
//        if (registrationVo.getDocEvidenceFile() != null
//                && registrationVo.getDocEvidenceFile().getSize() != ZERO) {
        LOGGER.log(Level.INFO, "######DOCUMENTATRY EVIDENCE  File Checking#############");
//            String docFile = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
//                    registrationVo.getDocEvidenceFile(), DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
        String docFile = "Dummy-file-path-docFile-file";
        LOGGER.log(Level.INFO, "######Uploading DOCUMENTATRY EVIDENCE File #############{0}", docFile);
        if (docFile != null) {
            registrationVo.setDocumentaryFile(docFile);
            completeTempInvVo.setDocFilePath(docFile);
        }
//       }
        if (completeTempInvVo != null) {
            session.setAttribute(COMPLETE_INVESTOR, completeTempInvVo);
        }
        storedValues.put(REG_PAGE_DATA, registrationVo);
        storedValues.put(MANDATE_DATA, mandateVo);
        storedValues.put("RegData", true);
        storedValues.put("FirstPage", false);
        getSessionMap().put(STORED_VALUES, storedValues);
    }

    public String doActionBackpage() {
        return "/pages/reg_investor_profile?faces-redirect=true";
    }

    public void doActionReset(ActionEvent event) {
        StringBuilder logMessage = new StringBuilder("User registration form reset by user with email id ");
        logMessage.append(userProfile.getEmail()).append(".");
        LOGGER.info(logMessage.toString());
        this.userProfile.setName(EMPTY_STRING);
        this.userProfile.setLastName(EMPTY_STRING);
        this.userProfile.setAddress(EMPTY_STRING);
        this.userProfile.setMobile(EMPTY_STRING);
        this.userProfile.setPincode(EMPTY_STRING);
        this.userProfile.setCity(EMPTY_STRING);
        this.userProfile.setTelephone(EMPTY_STRING);
        this.userProfile.setMiddleName(EMPTY_STRING);
        this.userProfile.setEmail(EMPTY_STRING);
        this.userProfile.setWorkOrganization(EMPTY_STRING);
        this.userProfile.setJobTitle(EMPTY_STRING);
        this.userProfile.setDob(null);
        this.getRequestMap().put(USER_PROFILE, userProfile);
    }

    public void saveInvestor() {
        HttpSession session = httpRequest.getSession();
        if (!emailExists) {
            registrationVo.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
            int ticket = userProfileBAO.getKitAllocationData();
            registrationVo.setKitNumber(ticket == ZERO ? null : ticket);
            //to regenerate pdf with kitnumber
            generatePdf();
            status = userProfileBAO.saveInvestorProfile(this.registrationVo, this.newApplicant);
            if (status) {
                userProfileBAO.saveMandate(this.mandateVo, registrationVo.getRegId());

                Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);//(Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
                storedValues.put("RegID", registrationVo.getRegId());
                storedValues.put(IS_ADVISOR, false);
                session.setAttribute(STORED_VALUES, storedValues);
                //getSessionMap().put(STORED_VALUES, storedValues);
            }
        }
    }

    public void saveInvestor(ActionEvent event) {
        HttpSession session = httpRequest.getSession();
        if (!emailExists) {
            registrationVo.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
            int ticket = userProfileBAO.getKitAllocationData();
            registrationVo.setKitNumber(ticket == ZERO ? null : ticket);
            //to regenerate pdf with kitnumber
            generatePdf();
            status = userProfileBAO.saveInvestorProfile(this.registrationVo, this.newApplicant);
            if (status) {
                userProfileBAO.saveMandate(this.mandateVo, registrationVo.getRegId());

                Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);//(Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
                storedValues.put("RegID", registrationVo.getRegId());
                storedValues.put(IS_ADVISOR, false);
                session.setAttribute(STORED_VALUES, storedValues);
                //getSessionMap().put(STORED_VALUES, storedValues);
            }
        }
    }

    public void saveAdvisor() {
        String filename = null;
        if (advisorRegistrationVo.getAccountNumber().equals(advisorRegistrationVo.getRaccountNumber())) {
            if (!emailExists) {
                boolean validdob = (AgeUtil.findAge(DateUtil.dateToString(advisorRegistrationVo.getDob(), dd_MM_yyyy)) > 18);
                if (validdob) {
                    if (advisorRegistrationVo.getSebi_validity() != null && isStartDateValid(advisorRegistrationVo.getSebi_validity())) {
                        status = true;
                        if (advisorRegistrationVo.getPan().equalsIgnoreCase(EMPTY_STRING)
                                || !userProfileBAO.isPanNoExists(YES, advisorRegistrationVo.getPan(), advisorRegistrationVo.getRegNO())) {
                            this.advisorRegistrationVo.setUserStatus(EnumStatus.NEW_APPLICANT.getValue());
                            advisorRegistrationVo.setIsAdvisor(true);
                            /* if (filepart != null && filepart.getSize() != 0) {
                             if (filepart.getSize() <= TWO_MB) {*/
                            // filename = FileUploadUtil.UploadUserFile(advisorRegistrationVo.getRegNO(), filepart, SEBI_FILE_PATH, SEBI_CERTIFICATE);
                            filename = "dummy_sebi_file_path";
                            if (filename != null) {
                                //   advisorRegistrationVo.setCertificateFilePath(filename);
                                status = userProfileBAO.saveAdvisorProfile(advisorRegistrationVo, this.newApplicant);
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("adv_reg_form:file",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", "File Upload Failed"));
                            }
                            /*} else {
                             FacesContext.getCurrentInstance().
                             addMessage("adv_reg_form:file",
                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", "Maximum file size allowable 2MB"));
                             status = false;
                             }
                             } else {
                             FacesContext.getCurrentInstance().
                             addMessage("adv_reg_form:file",
                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "File not attched", "File not attched"));
                             status = false;
                             }*/
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("adv_reg_form:panno_in",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, PANNO_MESSAGE, PANNO_MESSAGE));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("adv_reg_form:validity",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "validity expired", "validity expired"));
                    }
                } else {
                    status = false;
                    FacesContext.getCurrentInstance().
                            addMessage("adv_reg_form:dob",
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advisor cannot be minor", "Advisor cannot be minor"));
                }
            }
        } else {
            status = false;
            FacesContext.getCurrentInstance().
                    addMessage("adv_reg_form:re-acno",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number mismatch", "Account Number mismatch"));
        }
    }

    public String save() {
        String redirectionPage;
        if (status) {
            StringBuilder logMessage = new StringBuilder("User with email id ");
            if (isAdvisor()) {
                logMessage.append(this.advisorRegistrationVo.getRemail()).append(" registered advisor as ")
                        .append(this.advisorRegistrationVo.isIsAdvisor()).append(DOT);
            } else {
                logMessage.append(this.registrationVo.getEmail()).append(" registered advisor as ")
                        .append(this.registrationVo.isAdvisor()).append(DOT);
            }
            LOGGER.info(logMessage.toString());
            redirectionPage = "/faces/pages/init_reg_success.xhtml";
        } else {
            redirectionPage = EMPTY_STRING;
        }
        return redirectionPage;
    }

    public String reset() {
        String redirctTo;
        if (advisor) {
            redirctTo = "/pages/reg_advisor_profile";
        } else {
            redirctTo = "/pages/reg_investor_profile";
        }
        return redirctTo;
    }

    public boolean isStateListEditable() {
        return stateList.isEmpty();
    }

    public void onChnageCountry() {
        if (!advisor) {
            if (!registrationVo.getCcountry().equalsIgnoreCase(IN)) {
                stateList = new HashMap<String, String>();
                cityList = new HashMap<String, String>();
                if (!firstAddress) {
                    registrationVo.setCstate(EMPTY_STRING);
                    registrationVo.setCcity(EMPTY_STRING);
                }
            } else {
                this.setStateList(LookupDataLoader.getStateListLookup());
                this.setCityList(LookupDataLoader.getCityListLookup());
                registrationVo.setCcountry(IN);
                onChangeState(registrationVo.getCstate(), 1);
            }
        } //else if (advisor) {
//            if (!advisorRegistrationVo.getRcountry().equalsIgnoreCase(IN)) {
//                stateList = new HashMap<String, String>();
//                cityList = new HashMap<String, String>();
//            } else {
//                this.setStateList(LookupDataLoader.getStateListLookup());
//                this.setCityList(LookupDataLoader.getCityListLookup());
//                advisorRegistrationVo.setRcountry(IN);
//                onChangeState(advisorRegistrationVo.getRstate(), 1);
//            }
//        }
        firstAddress = false;
    }

    public Map<String, Object> getRequestMap() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return ec.getRequestMap();
    }

    public UserProfileVO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileVO userProfile) {
        this.userProfile = userProfile;
    }

    public Map<String, String> getCountryList() {
        return countryList;
    }

    public void setCountryList(Map<String, String> countryList) {
        this.countryList = countryList;
    }

    public Map<String, Integer> getStatusData() {
        return LookupDataLoader.getResidentStatusMap();
    }

    public Map<String, Integer> getProofofIdentity() {
        return LookupDataLoader.getProofOfIdentityMap();
    }

    public LinkedHashMap<String, Integer> getProofofAddress() {
        return LookupDataLoader.getProofOfAddressMap();
    }

    public Map<String, String> getCountry() {
        return LookupDataLoader.getCountryList();
    }

    public Map<String, Integer> getDPids() {
        return LookupDataLoader.getDpidsMap();
    }

    public Map<String, Integer> getOpenningAccountType() {
        return LookupDataLoader.getOpenningAccountTypeMap();
    }

    public Map<String, Integer> getDematAccountData() {
        return LookupDataLoader.getDematAccountMap();
    }

    public Map<String, Integer> getTradingAccount() {
        return LookupDataLoader.getTradingAccountMap();
    }

    public LinkedHashMap<String, Integer> getIncomerangedata() {
        return LookupDataLoader.getIncomerangeMap();
    }

    public Map<String, Integer> getOccupationData() {
        return LookupDataLoader.getOccupationMap();
    }

    public Map<String, Integer> getBankSubTypeData() {
        return LookupDataLoader.getBankSubTypeMap();
    }

    public Map<String, Integer> getGenderData() {
        return LookupDataLoader.getGenderDataMap();
    }

    public Map<String, Integer> getMaritalstatus() {
        return LookupDataLoader.getMaritalstatusMap();
    }

    public Map<String, Integer> getNseSegments() {
        return LookupDataLoader.getNseSegments();
    }

    public Map<String, Integer> getBseSegments() {
        return LookupDataLoader.getBseSegments();
    }

    public Map<String, Integer> getDocumentoryEvidence() {
        return LookupDataLoader.getDocumentoryEvidence();
    }

    public Map<String, Integer> getElectronicContractType() {
        return LookupDataLoader.getElectronicContractType();
    }

    public Map<String, Integer> getAlertType() {
        return LookupDataLoader.getAlertType();
    }

    public Map<String, Integer> getRelationWithMobileNumber() {
        return LookupDataLoader.getRelationWithMobileNumber();
    }

    public RegistrationVo getRegistrationVo() {
        return registrationVo;
    }

    public void setRegistrationVo(RegistrationVo registrationVo) {
        this.registrationVo = registrationVo;
    }

    public boolean isNewApplicant() {
        return newApplicant;
    }

    public void setNewApplicant(boolean newApplicant) {
        this.newApplicant = newApplicant;
    }

    public boolean isAdvisor() {
        return advisor;
    }

    public void setAdvisor(boolean advisor) {
        this.advisor = advisor;
    }

    public String cancel() {
        return "/index?faces-redirect=true";
    }

    public void onChnageCountry2() {
        if (!advisor) {
            if (registrationVo.getPermenentAddress()) {
                if (!registrationVo.getPcountry().equalsIgnoreCase(IN)) {
                    stateList2 = new HashMap<String, String>();
                    cityList2 = new HashMap<String, String>();
                    if (!firstAddress2) {
                        registrationVo.setPstate(EMPTY_STRING);
                        registrationVo.setPcity(EMPTY_STRING);
                    }
                } else {
                    this.setStateList2(LookupDataLoader.getStateListLookup());
                    this.setCityList2(LookupDataLoader.getCityListLookup());
                    registrationVo.setPcountry(IN);
                    onChangeState(registrationVo.getPstate(), 2);
                }
            }
        } else if (advisor) {
            if (!advisorRegistrationVo.isSamePermentaddr()) {
                if (!advisorRegistrationVo.getOcountry().equalsIgnoreCase(IN)) {
                    stateList2 = new HashMap<String, String>();
                    cityList2 = new HashMap<String, String>();
                } else {
                    this.setStateList2(LookupDataLoader.getStateListLookup());
                    this.setCityList2(LookupDataLoader.getCityListLookup());
                    advisorRegistrationVo.setOcountry(IN);
                    onChangeState(advisorRegistrationVo.getOstate(), 2);
                }
            }
        }
        firstAddress2 = false;
    }

    public void onChnageCountryBank() {
        if (advisor) {
            if (!advisorRegistrationVo.getBcountry().equalsIgnoreCase(IN)) {
                stateListBank2 = new HashMap<String, String>();
                cityListBank2 = new HashMap<String, String>();
//                stateListBank2 = new HashMap<String, List<String>>();
//                cityListBank2 = new ArrayList<String>();
            } else {
                this.setStateListBank2(LookupDataLoader.getStateListLookup());
//                this.setStateListBank2(LookupDataLoader.getStatecitymap());
                this.setCityListBank2(LookupDataLoader.getCityListLookup());   //   For bank,citylist populated only according to state (in onChangeState())
                advisorRegistrationVo.setBcountry(IN);
                onChangeState(advisorRegistrationVo.getBstate(), 3);
//                onBankSelect(advisorRegistrationVo.getBankName(), advisorRegistrationVo.getBstate(), advisorRegistrationVo.getBcity(), 1);
            }
        } else {
            if (!registrationVo.getBank_country().equalsIgnoreCase(IN)) {
                stateListBank2 = new HashMap<String, String>();
                cityListBank2 = new HashMap<String, String>();
            } else {
                this.setStateListBank2(LookupDataLoader.getStateListLookup());
                this.setCityListBank2(LookupDataLoader.getCityListLookup());
                registrationVo.setBank_country(IN);
                onChangeState(registrationVo.getBank_state(), 3);
//                onBankSelect(registrationVo.getBankname(), registrationVo.getBank_state(), registrationVo.getBcity(), 1);
            }
        }

    }

    public void onChnageCountryNomine() {
        if (!registrationVo.getCountryNominee().equalsIgnoreCase(IN)) {
            stateListNominee = new HashMap<String, String>();
            cityListNominee = new HashMap<String, String>();
            if (!nomineeCountry) {
                registrationVo.setStateNominee(EMPTY_STRING);
                registrationVo.setPlaceNominee(EMPTY_STRING);
            }
        } else {
            this.setStateListNominee(LookupDataLoader.getStateListLookup());
            this.setCityListNominee(LookupDataLoader.getCityListLookup());
            registrationVo.setCountryNominee(IN);
            onChangeState(registrationVo.getStateNominee(), 4);
        }

        nomineeCountry = false;
    }

    public void onChnageCountryNomineMinor() {
        if (!registrationVo.getCountryNomineeMinor().equalsIgnoreCase(IN)) {
            stateListNomineeMinor = new HashMap<String, String>();
            cityListNomineeMinor = new HashMap<String, String>();
            if (!nomineeMinorCountry) {
                registrationVo.setStateNomineeMinor(EMPTY_STRING);
                registrationVo.setPlaceNomineeMinor(EMPTY_STRING);
            }
        } else {
            this.setStateListNomineeMinor(LookupDataLoader.getStateListLookup());
            this.setCityListNomineeMinor(LookupDataLoader.getCityListLookup());
            registrationVo.setCountryNomineeMinor(IN);
            onChangeState(registrationVo.getStateNomineeMinor(), 5);
        }
        nomineeMinorCountry = false;
    }

    public boolean isStateList2Editable() {
        return stateList2.isEmpty();
    }

    public boolean isCityListNomineEditable() {
        return cityListNominee.isEmpty();
    }

    public boolean isCityListNomineeMinorEditable() {
        return cityListNomineeMinor.isEmpty();
    }

    public void generatePdf() {
        HttpSession session = httpRequest.getSession();
        if (!emailExists) {
            status = false;
            if (mandateVo != null) {
                if (mandateVo.getFromDate() != null && isStartDateValid(mandateVo.getFromDate())) {
                    this.mandateVo.setAmount(TENTHOUSAND);
                    LOGGER.log(Level.INFO, "######DOCUMENTATRY EVIDENCE  File Checking#############");
                    if (registrationVo.getDocEvidenceFile() != null
                            && registrationVo.getDocEvidenceFile().getSize() != ZERO) {
                        String docFile = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                registrationVo.getDocEvidenceFile(), DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
                        LOGGER.log(Level.INFO, "######Uploading DOCUMENTATRY EVIDENCE File #############{0}", docFile);
                        registrationVo.setDocumentaryFile(docFile);
                    }
                    status = true;
                    // HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);//(Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
                    regDataVo = new RegistrationDataProcessingVo();
                    String natianality = registrationVo.getNationality();
                    String evidence = registrationVo.getDocumentaryEvedence();
                    registrationVo.setFromRegistration(true);
                    registrationVo = registrationBAO.createRegistrationPdfData(registrationVo);
                    mandateVo = registrationBAO.createMandateFormData(mandateVo, registrationVo.getRegId());
                    registrationVo.setDocumentaryEvedence(registrationVo.isDocumentaryEvedenceOther()
                            ? otherEvidence : evidence);
                    regDataVo.setRegistrationVo(registrationVo);
                    regDataVo.setMandateVo(mandateVo);
                    RegistrationPdfDataPreparation registrationPdf = new RegistrationPdfDataPreparation();
                    registrationPdf.setHttpRequest(httpRequest);
                    registrationPdf.createRegistrationPdfData(regDataVo, true);
                    registrationVo.setCountryNominee(registrationVo.getNomineCountryKey());
                    registrationVo.setCountryNomineeMinor(registrationVo.getNomineMinorCountryKey());
                    registrationVo.setNationality(natianality);
                    session.setAttribute(SHOW_PDF, true);
                    storedValues.put(REG_PAGE_DATA, registrationVo);
                    storedValues.remove("RegData");
                    storedValues.put(MANDATE_DATA, mandateVo);
                    session.setAttribute(STORED_VALUES, storedValues);
                    //getSessionMap().put(STORED_VALUES, storedValues);
                } else {
                    FacesContext.getCurrentInstance().addMessage("frm_investor_profile2:cus_frm_date",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_FROMDATE, MSG_FROMDATE));
                }
            }
        }
    }

    public String showpdf() {
        String redirectTo = "";
        if (status) {
            redirectTo = PropertiesLoader.getPropertiesValue(V2_AFTER_REG_PDF);
        }
        return redirectTo;
    }

    public String getPathOfPdf() {
        String path = null;
        if (registrationVo.getRegId() != null) {
            path = PDF_PATH + registrationVo.getRegId() + DOT + PDF;
        }
        return path;
    }

    public String callingBackAction() {
        String redirectTo = "";
        if (!advisor) {
            redirectTo = "/pages/reg_investor_profile?faces-redirect=true";
        } else {
            redirectTo = "/pages/reg_advisor_profile?faces-redirect=true";
        }
        return redirectTo;
    }

    public String onClickPayment() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        storedValues.put(FROM_PAYMENT_RESPONSE, false);
        getSessionMap().put(STORED_VALUES, storedValues);
        return "/pages/make_payment?faces-redirect=true";
    }

    public void storeAdvisorProfileListener() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        storedValues.put(ADVISOR_DATA, userProfile);
        getSessionMap().put(STORED_VALUES, storedValues);
    }

    private Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public Map<String, String> getStateList() {
        return stateList;
    }

    public void setStateList(Map<String, String> stateList) {
        this.stateList = stateList;
    }

    public Map<String, String> getStateList2() {
        return stateList2;
    }

    public void setStateList2(Map<String, String> stateList2) {
        this.stateList2 = stateList2;
    }

    public static Map<String, String> getState() {
        return state;
    }

    public static void setState(Map<String, String> state) {
        UserRegistrationBean2.state = state;
    }

    public MandateVo getMandateVo() {
        return mandateVo;
    }

    public void setMandateVo(MandateVo mandateVo) {
        this.mandateVo = mandateVo;
    }

    public boolean isEnableToDate() {
        return enableToDate;
    }

    public void setEnableToDate(boolean enableToDate) {
        this.enableToDate = enableToDate;
    }

    public Map<String, Integer> getDebitaccount() {
        return LookupDataLoader.getDebitAccountMap();
    }

    public Map<String, Integer> getDebitAccountType() {
        return LookupDataLoader.getDebitAccountTypemap();
    }

    public Map<String, Integer> getFrequency() {
        return LookupDataLoader.getFrequencyMap();
    }

    public Map<String, Integer> getAction() {
        return LookupDataLoader.getMandateActionMap();
    }

    public Map<String, String> getNomineePoi() {
        return LookupDataLoader.getNomineePoiMap();
    }

    public void onChangeToDate() {
        if (mandateVo.isSelectToDate()) {
            mandateVo.setCancelDate(EMPTY_STRING);
            enableToDate = false;
        } else {
            mandateVo.setSelectToDate(false);
            enableToDate = true;
            mandateVo.setCancelDate("true");
        }
    }

    public void onChangeUntill() {
        if (mandateVo.getCancelDate().equalsIgnoreCase("true")) {
            mandateVo.setSelectToDate(false);
            mandateVo.setToDate(null);
            enableToDate = true;
        } else {
            mandateVo.setCancelDate("false");
            enableToDate = false;
            mandateVo.setSelectToDate(true);
        }
    }

    public String getPathOfMandateForm() {
        String path = null;
        if (!advisor) {
            if (registrationVo.getRegId() != null) {
                path = PDF_PATH + registrationVo.getRegId() + MANDATEFILE + DOT + PDF;
            }
        } else {
            if (userProfile.getRegId() != null) {
                path = PDF_PATH + userProfile.getRegId() + MANDATEFILE + DOT + PDF;
            }
        }

        return path;
    }

    public void onSelectIfsc() {
        if (mandateVo.isCustomerIfsc()) {
            mandateVo.setCustomerMicr(false);
            showMicr = false;
            showIfsc = true;
        } else {
            mandateVo.setCustomerIfsc(false);
            showIfsc = false;
            mandateVo.setCustomerMicr(true);
            showMicr = true;
        }

    }

    public void onSelectMicr() {
        if (mandateVo.isCustomerMicr()) {
            mandateVo.setCustomerIfsc(false);
            showIfsc = false;
            showMicr = true;
        } else {
            mandateVo.setCustomerMicr(false);
            mandateVo.setCustomerIfsc(true);
            showMicr = false;
            showIfsc = true;
        }
    }

    public boolean isShowIfsc() {
        return showIfsc;
    }

    public void setShowIfsc(boolean showIfsc) {
        this.showIfsc = showIfsc;
    }

    public boolean isShowMicr() {
        return showMicr;
    }

    public void setShowMicr(boolean showMicr) {
        this.showMicr = showMicr;
    }

    private boolean isStartDateValid(Date startDate) {
        boolean valid = false;
        Date dateNow;
        try {
            startDate = DateUtil.stringToDate(DateUtil.dateToString(startDate, DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
            dateNow = DateUtil.stringToDate(DateUtil.dateToString(new Date(), DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
            valid = !(startDate.before(dateNow));
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return valid;
    }

    private boolean isEndDateValid(Date endDate) {
        boolean valid = false;
        endDateInvalid = false;
        Date fromdate;
        try {
            endDate = DateUtil.stringToDate(DateUtil.dateToString(endDate, DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
            if (mandateVo.getFromDate() != null) {
                fromdate = DateUtil.stringToDate(DateUtil.dateToString(mandateVo.getFromDate(), DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
                valid = !(endDate.before(fromdate));
            } else {
                valid = false;
                endDateInvalid = true;
            }
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return valid;
    }

    public RegistrationDataProcessingVo getRegDataVo() {
        return regDataVo;
    }

    public void setRegDataVo(RegistrationDataProcessingVo regDataVo) {
        this.regDataVo = regDataVo;
    }

    public Map<String, String> getStateListNominee() {
        return stateListNominee;
    }

    public void setStateListNominee(Map<String, String> stateListNominee) {
        this.stateListNominee = stateListNominee;
    }

    public Map<String, String> getStateListNomineeMinor() {
        return stateListNomineeMinor;
    }

    public void setStateListNomineeMinor(Map<String, String> stateListNomineeMinor) {
        this.stateListNomineeMinor = stateListNomineeMinor;
    }

    public boolean isStateListNomineEditable() {
        return stateListNominee.isEmpty();
    }

    public boolean isStateListNomineMinorEditable() {
        return stateListNomineeMinor.isEmpty();
    }

    public String getOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(String otherEvidence) {
        this.otherEvidence = otherEvidence;
    }

    public boolean validateDate() {
        boolean valid = true;
        boolean validdob = true;
        boolean validproof1 = true;
        boolean validproof2 = true;
        if (registrationVo != null) {
            validdob = (AgeUtil.findAge(DateUtil.dateToString(registrationVo.getDob(), dd_MM_yyyy)) > 18);
//            if (registrationVo.getDobValidity() != null) {
//                validproof1 = isStartDateValid(registrationVo.getDobValidity());
//                if (!validproof1) {
//                    FacesContext.getCurrentInstance().addMessage("frm_investor_profile:adrsvalidity",
//                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "validity expired", "validity expired"));
//                }
//            }
//            if (registrationVo.getAddress2_validity() != null) {
//                validproof2 = isStartDateValid(registrationVo.getAddress2_validity());
//                if (!validproof2) {
//                    FacesContext.getCurrentInstance().addMessage("frm_investor_profile:address2_validity",
//                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "validity expired", "validity expired"));
//                }
//            }
            if (!validdob) {
                FacesContext.getCurrentInstance().addMessage("frm_investor_profile:dob",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Trade holder cannot be minor", "Trade holder cannot be minor"));
            }
            valid = (validdob && validproof1 && validproof2);
            if (!registrationVo.getBankAccountNumber().equals(this.raccontNumber)) {
                valid = false;
                FacesContext.getCurrentInstance().addMessage("frm_investor_profile:re-acno",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number mismatch", "Account Number mismatch"));
            }
        }
        return valid;

    }

    public Map<String, String> getStateListBank2() {
        return stateListBank2;
    }

    public void setStateListBank2(Map<String, String> stateListBank2) {
        this.stateListBank2 = stateListBank2;
    }

    public Map<String, String> getCityList() {
        return cityList;
    }

    public void setCityList(Map<String, String> cityList) {
        this.cityList = cityList;
    }

    public Map<String, String> getCityList2() {
        return cityList2;
    }

    public void setCityList2(Map<String, String> cityList2) {
        this.cityList2 = cityList2;
    }

    public Map<String, String> getCityListBank2() {
        return cityListBank2;
    }

    public void setCityListBank2(Map<String, String> cityListBank2) {
        this.cityListBank2 = cityListBank2;
    }

    public AdvisorRegistrationVo getAdvisorRegistrationVo() {
        return advisorRegistrationVo;
    }

    public void setAdvisorRegistrationVo(AdvisorRegistrationVo advisorRegistrationVo) {
        this.advisorRegistrationVo = advisorRegistrationVo;
    }

    public boolean isStateListBank2Editable() {
        return stateListBank2.isEmpty();
    }

    public boolean isCityListEditable() {
        return cityList.isEmpty();
    }

    public boolean isCityList2Editable() {
        return cityList2.isEmpty();
    }

    public boolean isCityListBank2Editable() {
        return cityListBank2.isEmpty();
    }

    public Map<String, String> getYearList() {
        return yearList;
    }

    public void setYearList(Map<String, String> yearList) {
        this.yearList = yearList;
    }

    public Part getFilepart() {
        return filepart;
    }

    public void setFilepart(Part filepart) {
        this.filepart = filepart;
    }

    public Map<Integer, String> getBankList() {
        return LookupDataLoader.getBankListLookup();
    }

    public boolean isFirstpage() {
        return firstpage;
    }

    public void setFirstpage(boolean firstpage) {
        this.firstpage = firstpage;
    }

    public Map<String, String> getCityListNominee() {
        return cityListNominee;
    }

    public void setCityListNominee(Map<String, String> cityListNominee) {
        this.cityListNominee = cityListNominee;
    }

    public Map<String, String> getCityListNomineeMinor() {
        return cityListNomineeMinor;
    }

    public void setCityListNomineeMinor(Map<String, String> cityListNomineeMinor) {
        this.cityListNomineeMinor = cityListNomineeMinor;
    }

    public String getRaccontNumber() {
        return raccontNumber;
    }

    public void setRaccontNumber(String raccontNumber) {
        this.raccontNumber = raccontNumber;
    }

    public void onChangeState(String state, int id) {
        cityMap = ConversionMethods.getStateWiseCity(state);
        if (cityMap != null && !cityMap.isEmpty()) {
            if (id == 1) {
                cityList = cityMap;
            } else if (id == 2) {
                cityList2 = cityMap;
            } else if (id == 3) {
                cityListBank2 = cityMap;
            } else if (id == 4) {
                cityListNominee = cityMap;
            } else {
                cityListNomineeMinor = cityMap;
            }
        }
        // Id == 3 only for bank details
//        if (id == 3) {
//            cityListBank2 = ConversionMethods.getCityForBankState(state);
//        }
    }

    public Map<String, String> getdependentUsed() {
        return LookupDataLoader.getDependentDataMap();
    }

    public void sumeetDemoFunction() {
        LOGGER.log(Level.INFO, "inside sumeet Demo2 function");
    }

    /**
     * @author sumeet.pol this method set httpRequest globally for other methods
     * to access this method call all corresponding method which are use to
     * complete registration of user
     * @param httpRequest
     * @param httpresponse2
     *
     *
     */
    public void doInvRegistration(HttpServletRequest httpRequest, HttpServletResponse httpresponse2) {

        this.httpRequest = httpRequest;
//		HttpSession session = httpRequest.getSession();
        try {
            prepareBean();
            doActionNextpageListener();
            LOGGER.log(Level.INFO, "doActionNextpageListner success");
            //post process page 1
            doActionNextpage();
            LOGGER.log(Level.INFO, "doActionNextpage success");
            //generate pdf
            generatePdf();
            LOGGER.log(Level.INFO, "pdf generated success");
/*          comment for new v3 implementation for user registration
 * 			String redirectTo = showpdf();
            redirectTo = httpRequest.getContextPath() + redirectTo;
            //redirectTo = PropertiesLoader.getPropertiesValue(V2_AFTER_REG_PDF);
            httpresponse2.sendRedirect(redirectTo);
*///	    	LOGGER.log(Level.INFO, "call showpdf success");
//	    	saveInvestor();
//	    	LOGGER.log(Level.INFO, "call saveInvestor success");
//	    	save();
//	    	LOGGER.log(Level.INFO, "call save success");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "error in doUserRegistration", e);
        }

    }

    /**
     * @author sumeet.pol content of this method is from @postConstruct method
     * of this bean changes as per new model. angular compatible code.
     */
    public void prepareBean() {
        LOGGER.log(Level.INFO, "entering prepareBean()");
        firstpage = true;
        HttpSession session = httpRequest.getSession();
        Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
        yearList = LookupDataLoader.getYearList();
        if (session.getAttribute(NEW_APPLICANT) != null && (Boolean) session.getAttribute(NEW_APPLICANT)) {
            newApplicant = true;
            advisor = session.getAttribute(IS_ADVISOR) == null || (Boolean) session.getAttribute(IS_ADVISOR);
            storedValues.remove(EMAIL_ID);
        } else {
            firstAddress = false;
            firstAddress2 = false;
            newApplicant = false;
            nomineeCountry = false;
            nomineeMinorCountry = false;
            String country = IN;
            advisor = (Boolean) storedValues.get(IS_ADVISOR);
//            if ((session.getAttribute(SHOW_PDF) != null && (Boolean) session.getAttribute(SHOW_PDF) == true)) {
//                session.removeAttribute(SHOW_PDF);
//                if (!(Boolean) session.getAttribute(IS_ADVISOR)) {
//                    registrationVo = (RegistrationVo) storedValues.get(REG_PAGE_DATA);
//                    raccontNumber = registrationVo.getBankAccountNumber();
//                    mandateVo = (MandateVo) storedValues.get(MANDATE_DATA);
//                }
//
//            } else {
            datalistLoading();
            if (advisor) {
                advisorRegistrationVo = new AdvisorRegistrationVo();
                advisorRegistrationVo = userProfileBAO.getTempAdvData((String) storedValues.get(EMAIL_ID));
                if (advisorRegistrationVo.getRegNO() == null) {
                    advisorRegistrationVo.setRegNO(IdGenarator.getUniqueId());
                    advisorRegistrationVo.setRemail(storedValues.get(EMAIL_ID).toString());
                    advisorRegistrationVo.setOemail(storedValues.get(EMAIL_ID).toString());
//                        advisorRegistrationVo.setRcountry(IN);
                    advisorRegistrationVo.setOcountry(IN);
                    advisorRegistrationVo.setBcountry(IN);
                } else {
//                        onChnageCountry();
                    onChnageCountry2();
                    onChnageCountryBank();
                }
            }
            if (!advisor && (RegistrationVo) storedValues.get(REG_PAGE_DATA) == null) {
                registrationVo = new RegistrationVo();
                mandateVo = new MandateVo();
                this.mandateVo.setSelectToDate(false);
                this.mandateVo.setUtilityName("MMF Financial Advisors Private Limited");
                this.mandateVo.setCustomerEmail(storedValues.get(EMAIL_ID).toString());
                this.mandateVo.setCustomerDebitType("Maximum amount");
                this.mandateVo.setFrequency("As and when presented");
                this.mandateVo.setFromDate(new Date());
                this.mandateVo.setCustomerDebitAccount("SB");
                this.mandateVo.setAction(CREATE);

                registrationVo = userProfileBAO.getTempInvData((String) storedValues.get(EMAIL_ID));

                registrationVo.setStatus("Resident Individual");
                registrationVo.setDepositoryParticipantName(GBNPP);
                registrationVo.setDpId(PropertiesLoader.getPropertiesValue(DEPOSITORY));
                registrationVo.setOpenAccountType("Trading Account and NSDL Demat Account");
                registrationVo.setTradingtAccount("Ordinary Resident");
                registrationVo.setDematAccount("Ordinary Resident");
                registrationVo.setTypeElectronicContract("Electronic Contact Note");
                registrationVo.setFacilityInternetTrading("0");
                registrationVo.setAddressType("Residential/Business");
                if (!StringUtils.hasText(registrationVo.getTypeAlert())) {
                    registrationVo.setTypeAlert("Both");
                }
                registrationVo.setCountryNominee(registrationVo.getCountryNominee() == null ? IN : registrationVo.getCountryNominee());
                registrationVo.setCountryNomineeMinor(registrationVo.getCountryNomineeMinor() == null ? IN : registrationVo.getCountryNomineeMinor());
                registrationVo.setNetWorthdate(registrationVo.getNetWorthdate() == null ? new Date() : registrationVo.getNetWorthdate());
                registrationVo.setNetWorthDateSecondHolder(registrationVo.getNetWorthDateSecondHolder() == null ? new Date() : registrationVo.getNetWorthDateSecondHolder());
                if (registrationVo.getRegId() == null) {
                    registrationVo.setRegId(IdGenarator.getUniqueId());
                    registrationVo.setCcountry(IN);
                    registrationVo.setEmail(storedValues.get(EMAIL_ID).toString());
                    registrationVo.setAdvisor(advisor);
                    registrationVo.setPcountry(IN);
                    registrationVo.setBank_country(IN);
                    registrationVo.setNationality(IN);
                } else {
                    raccontNumber = (registrationVo.getBankAccountNumber() == null ? EMPTY_STRING : registrationVo.getBankAccountNumber());
                    otherEvidence = (registrationVo.getOtherEvidenceProvided() == null ? EMPTY_STRING : registrationVo.getOtherEvidenceProvided());
                    onChnageCountry();
                    onChnageCountry2();
                    onChnageCountryBank();
                    onChnageCountryNomine();
                    onChnageCountryNomineMinor();
                }
            } else if (!advisor && (RegistrationVo) storedValues.get(REG_PAGE_DATA) != null) {
                firstAddress = true;
                firstAddress2 = true;
                nomineeCountry = true;
                nomineeMinorCountry = true;
                registrationVo = (RegistrationVo) storedValues.get(REG_PAGE_DATA);
                registrationVo = userProfileBAO.getTempInvData((String) storedValues.get(EMAIL_ID));
                registrationVo.setStatus("Resident Individual");
                registrationVo.setDepositoryParticipantName(GBNPP);
                registrationVo.setDpId(PropertiesLoader.getPropertiesValue(DEPOSITORY));
                registrationVo.setOpenAccountType("Trading Account and NSDL Demat Account");
                registrationVo.setTradingtAccount("Ordinary Resident");
                registrationVo.setDematAccount("Ordinary Resident");
                registrationVo.setTypeElectronicContract("Electronic Contact Note");
                registrationVo.setFacilityInternetTrading("0");
                registrationVo.setAddressType("Residential/Business");
                if (!StringUtils.hasText(registrationVo.getTypeAlert())) {
                    registrationVo.setTypeAlert("Both");
                }
                registrationVo.setCountryNominee(registrationVo.getCountryNominee() == null ? IN : registrationVo.getCountryNominee());
                registrationVo.setCountryNomineeMinor(registrationVo.getCountryNomineeMinor() == null ? IN : registrationVo.getCountryNomineeMinor());
                registrationVo.setNetWorthdate(registrationVo.getNetWorthdate() == null ? new Date() : registrationVo.getNetWorthdate());
                registrationVo.setNetWorthDateSecondHolder(registrationVo.getNetWorthDateSecondHolder() == null ? new Date() : registrationVo.getNetWorthDateSecondHolder());

                raccontNumber = registrationVo.getBankAccountNumber();
                otherEvidence = (registrationVo.getOtherEvidenceProvided() == null ? EMPTY_STRING : registrationVo.getOtherEvidenceProvided());
                firstpage = storedValues.get("FirstPage") != null ? (Boolean) storedValues.get("FirstPage") : false;
                Boolean regData = storedValues.get("RegData") != null ? (Boolean) storedValues.get("RegData") : false;
                mandateVo = storedValues.get(MANDATE_DATA) != null ? (MandateVo) storedValues.get(MANDATE_DATA) : new MandateVo();
                onChnageCountry();
                onChnageCountry2();
                onChnageCountryBank();
                onChnageCountryNomine();
                onChnageCountryNomineMinor();
                if (!regData) {
                    if (registrationVo.isDocumentaryEvedenceOther()) {
                        otherEvidence = registrationVo.getDocumentaryEvedence();
                        registrationVo.setDocumentaryEvedence("Others");
                    }
                    registrationVo.setPep(StringUtils.hasText(registrationVo.getPep()) ? TRUE : FALSE);
                    registrationVo.setRpep(StringUtils.hasText(registrationVo.getRpep()) ? TRUE : FALSE);
                    registrationVo.setPoliticalyExposedSecondHolder(StringUtils.hasText(
                            registrationVo.getPoliticalyExposedSecondHolder()) ? TRUE : FALSE);
                    registrationVo.setPoliticalyRelatedSecondHolder(StringUtils.hasText(
                            registrationVo.getPoliticalyRelatedSecondHolder()) ? TRUE : FALSE);
                }
            }

//            }
        }
        LOGGER.log(Level.INFO, "exiting prepareBean()");
    }

    public void doAdvisorRegistration(HttpServletRequest httpRequest, HttpServletResponse httpresponse2) {
        this.httpRequest = httpRequest;
        try {
            prepareBean();
            saveAdvisor();
            LOGGER.log(Level.INFO, "saveAdvisor success");
            String redirect = save();
            LOGGER.log(Level.INFO, "save success");
            httpresponse2.sendRedirect(httpRequest.getContextPath() + redirect);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "error in doAdvisorRegistration", e);
        }
    }

    public void doInvRegistrationAfterShowPdf(HttpServletRequest httRequest, HttpServletResponse httpresponse2) {
        try {
            this.httpRequest = httRequest;
            prepareBean();
            doActionNextpageListener();
            LOGGER.log(Level.INFO, "doActionNextpageListner success");
            //post process page 1
            doActionNextpage();
            LOGGER.log(Level.INFO, "doActionNextpage success");
            //generate pdf
            generatePdf();
            LOGGER.log(Level.INFO, "pdf generated success");
            showpdf();
            saveInvestor();
            LOGGER.log(Level.INFO, "call saveInvestor success");
            String redirectTo = save();
            redirectTo = httRequest.getContextPath() + "/faces/pages/v3/account-10-status.jsp";
            httpresponse2.sendRedirect(redirectTo);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "error in doInvRegistrationAfterShowPdf", e);
        }
    }
}
