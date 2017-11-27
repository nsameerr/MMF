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
import static com.gtl.mmf.service.util.IConstants.INDIA_ISD;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.I_DONT_WANT_TO_OPT_FOR_DERIVATIVE_TRADING;
import static com.gtl.mmf.service.util.IConstants.MANDATEFILE;
import static com.gtl.mmf.service.util.IConstants.MANDATE_DATA;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

/**
 *
 * @author 07958 This class represent Investor/Advisor registration details.
 */
@Named("userRegistrationBean")
@Scope("view")
public class UserRegistrationBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.UserRegistrationBean");
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
    private String contextRoot = LookupDataLoader.getContextRoot();

    /**
     *
     */
    @PostConstruct
    public void afterInit() {
        firstpage = true;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        yearList = LookupDataLoader.getYearList();
        firstAddress = false;
        firstAddress2 = false;
        newApplicant = false;
        nomineeCountry = false;
        nomineeMinorCountry = false;
        advisor = (Boolean) storedValues.get(IS_ADVISOR);
        if ((session.getAttribute(SHOW_PDF) != null && (Boolean) session.getAttribute(SHOW_PDF) == true)) {
            session.removeAttribute(SHOW_PDF);
            if (!(Boolean) session.getAttribute(IS_ADVISOR)) {
                registrationVo = (RegistrationVo) storedValues.get(REG_PAGE_DATA);
                raccontNumber = registrationVo.getBankAccountNumber();
                mandateVo = (MandateVo) storedValues.get(MANDATE_DATA);
            }

        } else {
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
                this.mandateVo.setUtilityName(MMF_FINANCIAL_ADVISORS_PRIVATE_LIMITED);
                this.mandateVo.setCustomerEmail(storedValues.get(EMAIL_ID).toString());
                this.mandateVo.setCustomerDebitType(MAX_AMT);
                this.mandateVo.setFrequency(AS_AND_WHEN_PRESENTED);
                this.mandateVo.setFromDate(new Date());
                this.mandateVo.setCustomerDebitAccount(SB);
                this.mandateVo.setAction(CREATE);

                registrationVo = userProfileBAO.getTempInvData((String) storedValues.get(EMAIL_ID));

                registrationVo.setStatus(RESIDENT_INDIVIDUAL);
                registrationVo.setDepositoryParticipantName(GBNPP);
                registrationVo.setDpId(PropertiesLoader.getPropertiesValue(DEPOSITORY));
                registrationVo.setOpenAccountType(TRADING_ACCOUNT_AND_NSDL_DEMAT_ACCOUNT);
                registrationVo.setTradingtAccount(ORDINARY_RESIDENT);
                registrationVo.setDematAccount(ORDINARY_RESIDENT);
                registrationVo.setTypeElectronicContract(ELECTRONIC_NOTE);
                registrationVo.setFacilityInternetTrading(ZERO_STRING);
                registrationVo.setTradingExperince(ZERO_STRING);
                registrationVo.setRelationSipMobilenumber(SELF);
                registrationVo.setDocumentaryEvedence(I_DONT_WANT_TO_OPT_FOR_DERIVATIVE_TRADING);
                if (!StringUtils.hasText(registrationVo.getTypeAlert())) {
                    registrationVo.setTypeAlert(BOTH);
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
                registrationVo.setStatus(RESIDENT_INDIVIDUAL);
                registrationVo.setDepositoryParticipantName(GBNPP);
                registrationVo.setDpId(PropertiesLoader.getPropertiesValue(DEPOSITORY));
                registrationVo.setOpenAccountType(TRADING_ACCOUNT_AND_NSDL_DEMAT_ACCOUNT);
                registrationVo.setTradingtAccount(ORDINARY_RESIDENT);
                registrationVo.setDematAccount(ORDINARY_RESIDENT);
                registrationVo.setTypeElectronicContract(ELECTRONIC_NOTE);
                registrationVo.setFacilityInternetTrading(ZERO_STRING);
                if (!StringUtils.hasText(registrationVo.getTypeAlert())) {
                    registrationVo.setTypeAlert(BOTH);
                }
                registrationVo.setCountryNominee(registrationVo.getCountryNominee() == null ? IN : registrationVo.getCountryNominee());
                registrationVo.setCountryNomineeMinor(registrationVo.getCountryNomineeMinor() == null ? IN : registrationVo.getCountryNomineeMinor());
                registrationVo.setNetWorthdate(registrationVo.getNetWorthdate() == null ? new Date() : registrationVo.getNetWorthdate());
                registrationVo.setNetWorthDateSecondHolder(registrationVo.getNetWorthDateSecondHolder() == null ? new Date() : registrationVo.getNetWorthDateSecondHolder());
                registrationVo.setTradingExperince(StringUtils.hasText(registrationVo.getTradingExperince()) ? registrationVo.getTradingExperince() : ZERO_STRING);
                registrationVo.setRelationSipMobilenumber(StringUtils.hasText(registrationVo.getRelationSipMobilenumber()) ? registrationVo.getRelationSipMobilenumber() : SELF);
                raccontNumber = registrationVo.getBankAccountNumber();
                otherEvidence = (registrationVo.getOtherEvidenceProvided() == null ? EMPTY_STRING : registrationVo.getOtherEvidenceProvided());
                firstpage = storedValues.get(FIRSTPAGE) != null ? (Boolean) storedValues.get(FIRSTPAGE) : false;
                Boolean regData = storedValues.get(REGDATA) != null ? (Boolean) storedValues.get(REGDATA) : false;
                mandateVo = storedValues.get(MANDATE_DATA) != null ? (MandateVo) storedValues.get(MANDATE_DATA) : new MandateVo();
                onChnageCountry();
                onChnageCountry2();
                onChnageCountryBank();
                onChnageCountryNomine();
                onChnageCountryNomineMinor();
                if (!regData) {
                    if (registrationVo.isDocumentaryEvedenceOther()) {
                        otherEvidence = registrationVo.getDocumentaryEvedence();
                        registrationVo.setDocumentaryEvedence(OTHERS);
                    }
                    registrationVo.setPep(StringUtils.hasText(registrationVo.getPep()) ? TRUE : FALSE);
                    registrationVo.setRpep(StringUtils.hasText(registrationVo.getRpep()) ? TRUE : FALSE);
                    registrationVo.setPoliticalyExposedSecondHolder(StringUtils.hasText(
                            registrationVo.getPoliticalyExposedSecondHolder()) ? TRUE : FALSE);
                    registrationVo.setPoliticalyRelatedSecondHolder(StringUtils.hasText(
                            registrationVo.getPoliticalyRelatedSecondHolder()) ? TRUE : FALSE);
                }
            }

        }
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

    public void doActionNextpageListener(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        tempInv = new TempInvVo();
        if (session.getAttribute(TEMP_INVESTOR_ONE) != null) {
            tempInv = (TempInvVo) session.getAttribute(TEMP_INVESTOR_ONE);
        }
        if (registrationVo.getPan().equalsIgnoreCase(EMPTY_STRING)
                || !userProfileBAO.isPanNoExists(NO, registrationVo.getPan(), registrationVo.getRegId())) {
            if (validateDate()) {
                status = true;
                LOGGER.log(Level.INFO, "######COPY OF PAN File Checking#############");
                if (registrationVo.getPanAttestedFile() != null
                        && registrationVo.getPanAttestedFile().getSize() != ZERO) {
                    if (registrationVo.getPanAttestedFile().getSize() <= TWO_MB) {
                        String panFile = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                registrationVo.getPanAttestedFile(), IDENTITY_PROOF, PAN_SUBMITTED_NAME);
                        LOGGER.log(Level.INFO, "###### Uploading COPY OF PAN #############{0}", panFile);

                        if (panFile != null) {
                            status = true;
                            registrationVo.setPanUploadFile(panFile);
                            tempInv.setPanFilePath(panFile);
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_investor_profile:pan_file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", "File Upload Failed"));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_investor_profile:pan_file",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", "Maximum file size allowable 2MB"));
                    }
                }
                if (registrationVo.getCorespondenceAddress() != null
                        && registrationVo.getCorespondenceAddress().getSize() != ZERO) {
                    if (registrationVo.getCorespondenceAddress().getSize() <= TWO_MB) {
                        String corespondenceAddress = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                registrationVo.getCorespondenceAddress(), VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME);
                        LOGGER.log(Level.INFO, "###### Uploading VALIDITY CORRESPONDENCE#############{0}", corespondenceAddress);

                        if (corespondenceAddress != null) {
                            status = true;
                            registrationVo.setCorespondenceAddressFile(corespondenceAddress);
                            tempInv.setCorsFilePath(corespondenceAddress);
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_investor_profile:cfile",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", "File Upload Failed"));
                        }

                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_investor_profile:cfile",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", "Maximum file size allowable 2MB"));
                    }
                }

                LOGGER.log(Level.INFO, "######PERMENENT Address File Checking#############");
                if (registrationVo.getPermenentAddressValidity() != null
                        && registrationVo.getPermenentAddressValidity().getSize() != ZERO) {
                    if (registrationVo.getPermenentAddressValidity().getSize() <= TWO_MB) {
                        String permenentAddressValidity = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                registrationVo.getPermenentAddressValidity(), VALIDITY_PERMENENT, PERMENENT_NAME);
                        LOGGER.log(Level.INFO, "######PERMENENT Address File Checking#############{0}", permenentAddressValidity);
                        if (permenentAddressValidity != null) {
                            status = true;
                            registrationVo.setPermenentAddressFile(permenentAddressValidity);
                            tempInv.setPrmntFilePath(permenentAddressValidity);
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_investor_profile:pfile",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", "File Upload Failed"));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_investor_profile:pfile",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", "Maximum file size allowable 2MB"));
                    }
                }
                mandateVo.setContactNmbr(registrationVo.getMobileNumber() == null ? EMPTY_STRING : INDIA_ISD + registrationVo.getMobileNumber());
                if (tempInv != null) {
                    session.setAttribute(TEMP_INVESTOR_ONE, tempInv);
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(PANNO_FIELD,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, PANNO_MESSAGE, PANNO_MESSAGE));
        }
    }

    public String doActionNextpage() {
        String redirectTo = "";
        if (status) {
            this.mandateVo.setCustomerBankName(registrationVo.getBankname());
            this.mandateVo.setCustomerAccountNumber(registrationVo.getBankAccountNumber());
            this.mandateVo.setIfscNumber(registrationVo.getIfsc());
            this.mandateVo.setMicrNumber(registrationVo.getMicrCode());
            this.mandateVo.setCustomerDebitAccount(registrationVo.getBankSubType().equalsIgnoreCase("Current Account") ? "CA" : "SB");
            this.mandateVo.setCustomerName(registrationVo.getBeneficiaryName());
            Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
            storedValues.put(REG_PAGE_DATA, registrationVo);
            storedValues.put(MANDATE_DATA, mandateVo);
            storedValues.put("RegData", true);
            getSessionMap().put(STORED_VALUES, storedValues);
            redirectTo = "/pages/reg_investor_profile2?faces-redirect=true";
        }
        return redirectTo;
    }

    public void doActionBackpageListener(ActionEvent event) {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        tempInvNext = new TempInvNextPageVo();
        if (session.getAttribute(TEMP_INVESTOR_TWO) != null) {
            tempInvNext = (TempInvNextPageVo) session.getAttribute(TEMP_INVESTOR_TWO);
        }
        if (registrationVo.getDocEvidenceFile() != null
                && registrationVo.getDocEvidenceFile().getSize() != ZERO) {
            LOGGER.log(Level.INFO, "######DOCUMENTATRY EVIDENCE  File Checking#############");
            String docFile = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                    registrationVo.getDocEvidenceFile(), DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
            LOGGER.log(Level.INFO, "######Uploading DOCUMENTATRY EVIDENCE File #############{0}", docFile);
            if (docFile != null) {
                registrationVo.setDocumentaryFile(docFile);
                tempInvNext.setDocFilePath(docFile);
            }
        }
        if (tempInvNext != null) {
            session.setAttribute(TEMP_INVESTOR_TWO, tempInvNext);
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

    public void saveInvestor(ActionEvent event) {
        if (!emailExists) {
            registrationVo.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
            int ticket = userProfileBAO.getKitAllocationData();
            registrationVo.setKitNumber(ticket == ZERO ? null : ticket);
            //to regenerate pdf with kitnumber
            generatePdf(event);
            status = userProfileBAO.saveInvestorProfile(this.registrationVo, this.newApplicant);
            if (status) {
                userProfileBAO.saveMandate(this.mandateVo, registrationVo.getRegId());

                Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
                storedValues.put("RegID", registrationVo.getRegId());
                storedValues.put(IS_ADVISOR, false);
                getSessionMap().put(STORED_VALUES, storedValues);
            }
        }
    }

    public void saveAdvisor(ActionEvent event) {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String filename = null;
        tempAdvVo = new TempAdvVo();
        if (session.getAttribute(TEMP_ADVISOR) != null) {
            tempAdvVo = (TempAdvVo) session.getAttribute(TEMP_ADVISOR);
        }
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
                            if (StringUtils.hasText(tempAdvVo.getSebiPath()) || StringUtils.hasText(advisorRegistrationVo.getSebiPath())) {
                                advisorRegistrationVo.setCertificateFilePath(tempAdvVo.getSebiPath()!=null ? tempAdvVo.getSebiPath()
                                        :advisorRegistrationVo.getSebiPath());
                                status = userProfileBAO.saveAdvisorProfile(advisorRegistrationVo, this.newApplicant);
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("adv_reg_form:file",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "File not attached", "File not attached"));
                            }
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
            redirectionPage = "/pages/init_reg_success?faces-redirect=true";
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

    public void generatePdf(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        tempRegistrationVo = new InvestorTempRegistrationVo();
        if (!emailExists) {
            status = false;
            if (mandateVo != null) {
                if (mandateVo.getFromDate() != null && isStartDateValid(mandateVo.getFromDate())) {
                    this.mandateVo.setAmount(TENTHOUSAND);
                    LOGGER.log(Level.INFO, "######DOCUMENTATRY EVIDENCE  File Checking#############");
                    tempInvNext = new TempInvNextPageVo();
                    if (session.getAttribute(TEMP_INVESTOR_TWO) != null) {
                        tempInvNext = (TempInvNextPageVo) session.getAttribute(TEMP_INVESTOR_TWO);
                    }
                    if (registrationVo.getDocEvidenceFile() != null
                            && registrationVo.getDocEvidenceFile().getSize() != ZERO) {
                        String docFile = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                registrationVo.getDocEvidenceFile(), DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
                        LOGGER.log(Level.INFO, "######Uploading DOCUMENTATRY EVIDENCE File #############{0}", docFile);
                        registrationVo.setDocumentaryFile(docFile);
                        tempInvNext.setDocFilePath(docFile);
                    }
                    status = true;
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
                    registrationPdf.createRegistrationPdfData(regDataVo, true);
                    registrationVo.setCountryNominee(registrationVo.getNomineCountryKey());
                    registrationVo.setCountryNomineeMinor(registrationVo.getNomineMinorCountryKey());
                    registrationVo.setNationality(natianality);
                    session.setAttribute(SHOW_PDF, true);
                    storedValues.put(REG_PAGE_DATA, registrationVo);
                    storedValues.remove("RegData");
                    storedValues.put(MANDATE_DATA, mandateVo);
                    getSessionMap().put(STORED_VALUES, storedValues);
                    if (tempInvNext != null) {
                        try {
                            session.setAttribute(TEMP_INVESTOR_TWO, tempInvNext);
                            tempRegistrationVo.setInvestorRegPage1((TempInvVo) session.getAttribute(TEMP_INVESTOR_ONE));
                            tempRegistrationVo.setInvestorRegPage2(tempInvNext);
                            userProfileBAO.saveInvTemp(tempRegistrationVo);
                        } catch (ParseException ex) {
                            Logger.getLogger(UserRegistrationBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
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
            redirectTo = "/pages/pdfView.xhtml?faces-redirect=true";
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
        UserRegistrationBean.state = state;
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
            }else if (id == 3) {
                cityListBank2 = cityMap;
            }else if (id == 4) {
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

    public Map<String, String> getDependentUsed() {
        return LookupDataLoader.getDependentDataMap();
    }

    public void fileUpload(Part part, Integer fileType, String fieldId) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String file = null;
        tempInv = new TempInvVo();
        if (session.getAttribute(TEMP_INVESTOR_ONE) != null) {
            tempInv = (TempInvVo) session.getAttribute(TEMP_INVESTOR_ONE);
        }
        if (part != null && part.getSize() != ZERO) {
            if (part.getSize() <= TWO_MB) {
                switch (fileType) {
                    case 1:
                        LOGGER.log(Level.INFO, "###### Uploading COPY OF PAN #############");
                        file = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                part, IDENTITY_PROOF, PAN_SUBMITTED_NAME);
                        if (file != null) {
                            registrationVo.setPanUploadFile(file);
                            tempInv.setPanFilePath(file);
                        }
                        break;
                    case 2:
                        LOGGER.log(Level.INFO, "###### Uploading VALIDITY CORRESPONDENCE#############");
                        file = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                part, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME);
                        break;
                    case 3:
                        LOGGER.log(Level.INFO, "######PERMENENT Address File Checking#############");
                        file = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                part, VALIDITY_PERMENENT, PERMENENT_NAME);
                        break;
                    case 4:
                        LOGGER.log(Level.INFO, "######Uploading DOCUMENTATRY EVIDENCE File #############");
                        file = FileUploadUtil.UploadUserFile(registrationVo.getRegId(),
                                part, DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
                        break;
                    default:
                        break;
                }

                if (file != null) {
                    FacesContext.getCurrentInstance().
                            addMessage("frm_investor_upload:" + fieldId,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO, "File Upload Succefully", "File Upload Succefully"));
                } else {
                    FacesContext.getCurrentInstance().
                            addMessage("frm_investor_upload:" + fieldId,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", "File Upload Failed"));
                }
            } else {
                FacesContext.getCurrentInstance().
                        addMessage("frm_investor_upload:" + fieldId,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", "Maximum file size allowable 2MB"));
            }
        } else {
            FacesContext.getCurrentInstance().
                    addMessage("frm_investor_upload:" + fieldId,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please attach file", "Please attach file"));
        }
        if (tempInv != null) {
            session.setAttribute(TEMP_INVESTOR_ONE, tempInv);
        }
    }

    public InvestorTempRegistrationVo getTempRegistrationVo() {
        return tempRegistrationVo;
    }

    public void setTempRegistrationVo(InvestorTempRegistrationVo tempRegistrationVo) {
        this.tempRegistrationVo = tempRegistrationVo;
    }

    public String getSebiPath() {
        return sebiPath;
    }

    public void setSebiPath(String sebiPath) {
        this.sebiPath = sebiPath;
    }

    public String getPanPath() {
        return panPath;
    }

    public void setPanPath(String panPath) {
        this.panPath = panPath;
    }

    public String getCoresProofPath() {
        return coresProofPath;
    }

    public void setCoresProofPath(String coresProofPath) {
        this.coresProofPath = coresProofPath;
    }

    public String getDocEvdPath() {
        return docEvdPath;
    }

    public void setDocEvdPath(String docEvdPath) {
        this.docEvdPath = docEvdPath;
    }

    public String getPermProofPath() {
        return permProofPath;
    }

    public void setPermProofPath(String permProofPath) {
        this.permProofPath = permProofPath;
    }
/*
    public List<String> autoPopulateBankDetails(String query) {
        List<String> filteredList = new ArrayList<String>();
        filteredBank = userProfileBAO.lookforBank(query);
        for (BankVo bankVo : filteredBank) {
            filteredList.add(bankVo.getIfsc());
        }
        return filteredList;
    }

    public void completeBankDetails(SelectEvent event) {
        String newScrip = event.getObject().toString();
        for (BankVo bankVo : filteredBank) {
            if (bankVo.getIfsc().equalsIgnoreCase(newScrip)) {
                if (advisor) {
                    advisorRegistrationVo.setBankName(bankVo.getBank());
                    advisorRegistrationVo.setIfscNo(bankVo.getIfsc());
                    advisorRegistrationVo.setBaddressLine1(bankVo.getAddress());
                    advisorRegistrationVo.setBcountry(IN);
                    advisorRegistrationVo.setBstate(bankVo.getState());
                    advisorRegistrationVo.setBcity(bankVo.getCity());
                    advisorRegistrationVo.setMicrNo(bankVo.getMicr());
                    advisorRegistrationVo.setBranch(bankVo.getBranch());
                    cityListBank2 = ConversionMethods.getCityForBankState(advisorRegistrationVo.getBstate());
                    onBankSelect(advisorRegistrationVo.getBankName(), advisorRegistrationVo.getBstate(), advisorRegistrationVo.getBcity(), 1);
                } else {
                    registrationVo.setBankname(bankVo.getBank());
                    registrationVo.setIfsc(bankVo.getIfsc());
                    registrationVo.setBuilding(bankVo.getAddress());
                    registrationVo.setBank_country(IN);
                    registrationVo.setBank_state(bankVo.getState());
                    registrationVo.setBcity(bankVo.getCity());
                    registrationVo.setMicrCode(bankVo.getMicr());
                    registrationVo.setBranch(bankVo.getBranch());
                    cityListBank2 = ConversionMethods.getCityForBankState(registrationVo.getBank_state());
                    onBankSelect(registrationVo.getBankname(), registrationVo.getBank_state(), registrationVo.getBcity(), 1);
                }
            }
        }
    }
*/
    public void saveDraft() {
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Data saved successfully", "Data saved successfully"));
    }
    
    public String getredirectPage() {
        return EMPTY_STRING;
    }

    public boolean isDontknowIfsc() {
        return dontknowIfsc;
    }

    public void setDontknowIfsc(boolean dontknowIfsc) {
        this.dontknowIfsc = dontknowIfsc;
    }

    public List<String> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<String> branchList) {
        this.branchList = branchList;
    }
    
    public void clearValidity(){
        registrationVo.setDobValidity(null); 
    }
    
    public void clearValidity2(){
        registrationVo.setAddress2_validity(null); 
    }
    
    /*
    //variable i used to identify calling from bank change(1 for usual,2 for inv bank change, 3 for adv bnk chng)
    public void onBankSelect(String bank, String state, String city,int i){
    List<String> branches = new ArrayList<String>();
    if(i == 2) {
    registrationVo.setBranch(EMPTY_STRING);
    } else if(i == 3) {
    advisorRegistrationVo.setBranch(EMPTY_STRING);
    }
    filteredBank = userProfileBAO.onBankSelect(bank, state, city);    
    for (BankVo bankVo : filteredBank) {
    branches.add(bankVo.getBranch());
    }
    Collections.sort(branches);
    this.setBranchList(branches);
    }
    public void onBranchSelect(){
    for (BankVo bankVo : filteredBank) {
    if(advisor) {
    if(bankVo.getBranch().equals(advisorRegistrationVo.getBranch())) {
    advisorRegistrationVo.setIfscNo(bankVo.getIfsc());
    advisorRegistrationVo.setBaddressLine1(bankVo.getAddress());
    advisorRegistrationVo.setBcity(bankVo.getCity());
    }
    } else {
    if(bankVo.getBranch().equals(registrationVo.getBranch())) {
    registrationVo.setIfsc(bankVo.getIfsc());
    registrationVo.setBuilding(bankVo.getAddress());
    registrationVo.setBcity(bankVo.getCity());
    }
    }
    }
    }*/
    public TempAdvVo getTempAdvVo() {
        return tempAdvVo;
    }

    public void setTempAdvVo(TempAdvVo tempAdvVo) {
        this.tempAdvVo = tempAdvVo;
    }

    public String getContextRoot() {
        return LookupDataLoader.getContextRoot();
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }

   

    
}