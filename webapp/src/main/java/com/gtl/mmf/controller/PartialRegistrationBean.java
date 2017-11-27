/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.ICreateUserBAO;
import com.gtl.mmf.service.vo.FailedMailDetailsVO;
import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.bao.RegistrationBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.report.util.RegistrationPdfDataPreparation;
import com.gtl.mmf.service.util.AgeUtil;
import com.gtl.mmf.service.util.ConversionMethods;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.AS_AND_WHEN_PRESENTED;
import static com.gtl.mmf.service.util.IConstants.BOTH;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.CORRESPONDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.DEPOSITORY;
import static com.gtl.mmf.service.util.IConstants.DOCUMENTARY_EVIDENCE;
import static com.gtl.mmf.service.util.IConstants.DOC_EVIDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.ELECTRONIC_NOTE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.GBNPP;
import static com.gtl.mmf.service.util.IConstants.IDENTITY_NAME;
import static com.gtl.mmf.service.util.IConstants.IDENTITY_PROOF;
import static com.gtl.mmf.service.util.IConstants.IN;
import static com.gtl.mmf.service.util.IConstants.INVESTOR;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.I_DONT_WANT_TO_OPT_FOR_DERIVATIVE_TRADING;
import static com.gtl.mmf.service.util.IConstants.TENTHOUSAND;
import static com.gtl.mmf.service.util.IConstants.MAX_AMT;
import static com.gtl.mmf.service.util.IConstants.MMF_FINANCIAL_ADVISORS_PRIVATE_LIMITED;
import static com.gtl.mmf.service.util.IConstants.ORDINARY_RESIDENT;
import static com.gtl.mmf.service.util.IConstants.PERMENENT_NAME;
import static com.gtl.mmf.service.util.IConstants.RESIDENT_INDIVIDUAL;
import static com.gtl.mmf.service.util.IConstants.SB;
import static com.gtl.mmf.service.util.IConstants.SELF;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TRADING_ACCOUNT_AND_NSDL_DEMAT_ACCOUNT;
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
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationDataProcessingVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.util.StackTraceWriter;
import com.gtl.mmf.webapp.util.FileUploadUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author trainee2
 */
@Named("partialRegistrationBean")
@Scope("view")
public class PartialRegistrationBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.PartialRegistrationBean");
    private FailedMailDetailsVO failedMailDetailsVO;
    private Map<String, String> countryList;
    private static Map<String, String> state;
    private Map<String, String> stateList;
    private Map<String, String> stateList2;
    private Map<String, String> stateListBank2;
    private Map<String, String> stateListNominee;
    private Map<String, String> stateListNomineeMinor;

    private Map<String, String> cityList;
    private Map<String, String> cityList2;
    private Map<String, String> cityListBank2;
    private Map<String, String> yearList;
    private Map<String, String> cityListNominee;
    private Map<String, String> cityListNomineeMinor;
    private List<BankVo> filteredBank = new ArrayList<BankVo>();
    @Autowired
    private IUserProfileBAO userProfileBAO;

    @Autowired
    private ICreateUserBAO createUserBAO;

    private RegistrationVo registrationVo;
    private boolean advisor;
    private MandateVo mandateVo;
    boolean enableToDate;
    private boolean showIfsc = true;
    private boolean showMicr;
    String raccontNumber;
    private Map<String, String> cityMap;
    boolean status;
    RegistrationDataProcessingVo regDataVo;
    @Autowired
    private RegistrationBAO registrationBAO;
    private static final String MSG_FROMDATE = "Past date cannot used as start date.";
    private String otherEvidence;
    private static final String CREATE = "Create";
    private AdvisorRegistrationVo advisorRegistrationVo;
    private Part filepart;
    private static final String PANNO_MESSAGE = "PAN number already registered";
    private boolean dontknowIfsc = false;
    private List<String> branchList;
    private Map<String, Integer> addresstypeData;
    private Part filepart1;
    private Part filepart2;
    private Part filepart3;
    private String panPath = PropertiesLoader.getPropertiesValue(IDENTITY_PROOF);
    private String contextRoot = LookupDataLoader.getContextRoot();

    @PostConstruct
    public void afterInit() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        this.failedMailDetailsVO = (FailedMailDetailsVO) storedValues.get("selectedUser");
        yearList = LookupDataLoader.getYearList();
        datalistLoading();
        if (failedMailDetailsVO.getUserType().equalsIgnoreCase(INVESTOR)) {
            this.advisor = false;
            registrationVo = userProfileBAO.getTempInvData(failedMailDetailsVO.getEmail());
            if (registrationVo.getRegId() == null) {
                registrationVo = new RegistrationVo();
                registrationVo.setRegId(IdGenarator.getUniqueId());
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
                registrationVo.setCcountry(IN);
                registrationVo.setEmail(failedMailDetailsVO.getEmail());
                registrationVo.setAdvisor(advisor);
                registrationVo.setPcountry(IN);
                registrationVo.setBank_country(IN);
                registrationVo.setNationality(IN);
                registrationVo.setCountryNominee(registrationVo.getCountryNominee() == null ? IN : registrationVo.getCountryNominee());
                registrationVo.setCountryNomineeMinor(registrationVo.getCountryNomineeMinor() == null ? IN : registrationVo.getCountryNomineeMinor());
                mandateVo = new MandateVo();
                this.mandateVo.setSelectToDate(false);
                this.mandateVo.setUtilityName(MMF_FINANCIAL_ADVISORS_PRIVATE_LIMITED);
                this.mandateVo.setCustomerEmail(failedMailDetailsVO.getEmail());
                this.mandateVo.setCustomerDebitType(MAX_AMT);
                this.mandateVo.setFrequency(AS_AND_WHEN_PRESENTED);
                this.mandateVo.setFromDate(new Date());
                this.mandateVo.setCustomerDebitAccount(SB);
                this.mandateVo.setAction(CREATE);
            } else {
                if (!StringUtils.hasText(registrationVo.getTypeAlert())) {
                    registrationVo.setTypeAlert(BOTH);
                }
                registrationVo.setCountryNominee(registrationVo.getCountryNominee() == null ? IN : registrationVo.getCountryNominee());
                registrationVo.setCountryNomineeMinor(registrationVo.getCountryNomineeMinor() == null ? IN : registrationVo.getCountryNomineeMinor());
                registrationVo.setNetWorthdate(registrationVo.getNetWorthdate() == null ? new Date() : registrationVo.getNetWorthdate());
                registrationVo.setNetWorthDateSecondHolder(registrationVo.getNetWorthDateSecondHolder() == null ? new Date() : registrationVo.getNetWorthDateSecondHolder());
                raccontNumber = (registrationVo.getBankAccountNumber() == null ? EMPTY_STRING : registrationVo.getBankAccountNumber());
                registrationVo.setDepositoryName("NSDL");
                onChnageCountry();
                onChnageCountry2();
                onChnageCountryBank();
                onChnageCountryNomine();
                onChnageCountryNomineMinor();
                this.setMandateData();
            }
        } else {
            this.advisor = true;
            advisorRegistrationVo = userProfileBAO.getTempAdvData(failedMailDetailsVO.getEmail());
            if (advisorRegistrationVo.getRegNO() == null) {
                advisorRegistrationVo.setRegNO(IdGenarator.getUniqueId());
                advisorRegistrationVo.setRemail(failedMailDetailsVO.getEmail());
                advisorRegistrationVo.setOemail(failedMailDetailsVO.getEmail());
                advisorRegistrationVo.setOcountry(IN);
                advisorRegistrationVo.setBcountry(IN);
            } else {
                onChnageCountry2();
                onChnageCountryBank();
            }
        }
        // Addresstype display in admin console 
        this.setAddresstypeData(LookupDataLoader.getAddressTypeList());
    }

    private void datalistLoading() {
        this.setCountryList(LookupDataLoader.getCountryList());
        this.setStateList(LookupDataLoader.getStateListLookup());
        this.setStateList2(this.getStateList());
        this.setStateListBank2(this.getStateList());
//        this.setStateListBank2(LookupDataLoader.getStatecitymap());
        this.setStateListNominee(this.getStateList());
        this.setStateListNomineeMinor(this.getStateList());

        this.setCityList(LookupDataLoader.getCityListLookup());
        this.setCityList2(this.getCityList());
        this.setCityListBank2(this.getCityList());
//        this.setCityListBank2(new ArrayList<String>());
        this.setCityListNominee(this.getCityList());
        this.setCityListNomineeMinor(this.getCityList());
    }

    public boolean isStateListEditable() {
        return stateList.isEmpty();
    }

    public void onChnageCountry() {
        if (!advisor) {
            if (registrationVo.getCcountry() != null) {
                if (!registrationVo.getCcountry().equalsIgnoreCase(IN)) {
                    stateList = new HashMap<String, String>();
                    cityList = new HashMap<String, String>();
                } else {
                    this.setStateList(LookupDataLoader.getStateListLookup());
                    this.setCityList(LookupDataLoader.getCityListLookup());
                    registrationVo.setCcountry(IN);
                    onChangeState(registrationVo.getCstate(), 1);
                }
            }
        }
    }

    public Map<String, Object> getRequestMap() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        return ec.getRequestMap();
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

    public Map<Integer, String> getBankList() {
        return LookupDataLoader.getBankListLookup();
    }

    public Map<String, String> getNomineePoi() {
        return LookupDataLoader.getNomineePoiMap();
    }

    public RegistrationVo getRegistrationVo() {
        return registrationVo;
    }

    public void setRegistrationVo(RegistrationVo registrationVo) {
        this.registrationVo = registrationVo;
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
            if (registrationVo.getPermenentAddress() != null) {
                if (registrationVo.getPermenentAddress()) {
                    if (!registrationVo.getPcountry().equalsIgnoreCase(IN)) {
                        stateList2 = new HashMap<String, String>();
                        cityList2 = new HashMap<String, String>();
                    } else {
                        this.setStateList2(LookupDataLoader.getStateListLookup());
                        this.setCityList2(LookupDataLoader.getCityListLookup());
                        registrationVo.setPcountry(IN);
                        onChangeState(registrationVo.getPstate(), 2);
                    }
                }
            }
        } else if (advisor) {
            if (!advisorRegistrationVo.isSamePermentaddr() && advisorRegistrationVo.getOcountry() != null) {
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
    }

    public void onChnageCountryBank() {
        if (advisor) {
            if (advisorRegistrationVo.getBcountry() != null) {
                if (!advisorRegistrationVo.getBcountry().equalsIgnoreCase(IN)) {
                    stateListBank2 = new HashMap<String, String>();
                    cityListBank2 = new HashMap<String, String>();
                } else {
                    this.setStateListBank2(LookupDataLoader.getStateListLookup());
                    this.setCityListBank2(LookupDataLoader.getCityListLookup());
//                this.setCityListBank2(LookupDataLoader.getCityListLookup());   For bank,citylist populated only according to state (in onChangeState())
                    advisorRegistrationVo.setBcountry(IN);
                    onChangeState(advisorRegistrationVo.getBstate(), 3);
//                onBankSelect(advisorRegistrationVo.getBankName(), advisorRegistrationVo.getBstate(), advisorRegistrationVo.getBcity(), 1);
                }
            }

        } else if (registrationVo.getBank_country() != null) {
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
        if (registrationVo.getCountryNominee() != null) {
            if (!registrationVo.getCountryNominee().equalsIgnoreCase(IN)) {
                stateListNominee = new HashMap<String, String>();
                cityListNominee = new HashMap<String, String>();
            } else {
                this.setStateListNominee(LookupDataLoader.getStateListLookup());
                this.setCityListNominee(LookupDataLoader.getCityListLookup());
                registrationVo.setCountryNominee(IN);
                onChangeState(registrationVo.getStateNominee(), 4);
            }
        }
    }

    public void onChnageCountryNomineMinor() {
        if (registrationVo.getCountryNomineeMinor() != null) {
            if (!registrationVo.getCountryNomineeMinor().equalsIgnoreCase(IN)) {
                stateListNomineeMinor = new HashMap<String, String>();
                cityListNomineeMinor = new HashMap<String, String>();
            } else {
                this.setStateListNomineeMinor(LookupDataLoader.getStateListLookup());
                this.setCityListNomineeMinor(LookupDataLoader.getCityListLookup());
                registrationVo.setCountryNomineeMinor(IN);
                onChangeState(registrationVo.getStateNomineeMinor(), 5);
            }
        }

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

    public boolean validateDate() {
        boolean valid = true;
        boolean validdob = true;
        boolean validproof1 = true;
        boolean validproof2 = true;
        if (registrationVo != null) {
            validdob = (AgeUtil.findAge(DateUtil.dateToString(registrationVo.getDob(), dd_MM_yyyy)) > 18);
            if (!validdob) {
                FacesContext.getCurrentInstance().addMessage("frm_temp_user:dob",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Trade holder cannot be minor", "Trade holder cannot be minor"));
            }
            valid = (validdob && validproof1 && validproof2);
            if (!registrationVo.getBankAccountNumber().equals(this.raccontNumber)) {
                valid = false;
                FacesContext.getCurrentInstance().addMessage("frm_temp_user:re-acno",
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

    public Map<String, String> getDependentUsed() {
        return LookupDataLoader.getDependentDataMap();
    }

    public String redirectBack() {
        return "/pages/admin/failed_mail_list?faces-redirect=true";
    }

    public void saveInvestor(ActionEvent event) {
        registrationVo.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
        int ticket = userProfileBAO.getKitAllocationData();
        registrationVo.setKitNumber(ticket == ZERO ? null : ticket);
        //to regenerate pdf with kitnumber
        generatePdf(event);
        status = userProfileBAO.saveInvestorProfile(this.registrationVo, false);
        if(filepart1 != null && filepart1.getSize() != ZERO){
              if (filepart1.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Identity Proof ********************************");
                        String identity = FileUploadUtil.UploadUserFile(registrationVo.getRegId(), filepart1, IDENTITY_PROOF, IDENTITY_NAME);
                        LOGGER.info("File name for Identity Proof :" + identity);
                        if (identity != null) {
                            registrationVo.setPanUploadFile(identity);
                            registrationVo.setPanAttestedFile(filepart1);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_temp_user:idProof",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id proof File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_temp_user:idProof",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }
        }
        if(filepart2 != null && filepart2.getSize() != ZERO){
              if (filepart2.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Address Proof ********************************");
                        String correspondence = FileUploadUtil.UploadUserFile(registrationVo.getRegId(), filepart2, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME);
                        LOGGER.info("File name for Corresspondence address:{0}" + correspondence);
                        if (correspondence != null) {
                            registrationVo.setCorespondenceAddressFile(correspondence);
                            registrationVo.setCorespondenceAddress(filepart2);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_temp_user:adrsvalidity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id proof File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_temp_user:adrsvalidity",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }
        }
         if (registrationVo.getPermenentAddress()) {
                    if (filepart3 != null && filepart3.getSize() != ZERO) {
                        if (filepart3.getSize() <= TWO_MB) {
                            LOGGER.info("************************File Uploading Permenent address proof ********************************");
                            String permenent = FileUploadUtil.UploadUserFile(registrationVo.getRegId(), filepart3, VALIDITY_PERMENENT, PERMENENT_NAME);
                            LOGGER.log(Level.INFO, "File name for Permenent Address :{0}", permenent);
                            if (permenent != null) {
                                registrationVo.setPermenentAddressFile(permenent);
                                registrationVo.setPermenentAddressValidity(filepart3);
                                status = true;
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("frm_temp_user:address2_validity",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Permenent Address File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_temp_user:address2_validity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }
                    }
                }
         
        userProfileBAO.saveInvestorDocumentDetails(registrationVo);
        if (status) {
            userProfileBAO.saveMandate(this.mandateVo, registrationVo.getRegId());
            Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
            storedValues.put("RegID", registrationVo.getRegId());
            storedValues.put(IS_ADVISOR, false);
            getSessionMap().put(STORED_VALUES, storedValues);
        }
    }

    public void generatePdf(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
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
            } else {
                FacesContext.getCurrentInstance().addMessage("frm_temp_user:cus_frm_date",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_FROMDATE, MSG_FROMDATE));
            }
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
            redirectionPage = "/pages/admin/failed_mail_list?faces-redirect=true";
        } else {
            redirectionPage = EMPTY_STRING;
        }
        return redirectionPage;
    }

    private void setMandateData() {
        mandateVo = new MandateVo();
        this.mandateVo.setSelectToDate(false);
        this.mandateVo.setUtilityName(MMF_FINANCIAL_ADVISORS_PRIVATE_LIMITED);
        this.mandateVo.setCustomerEmail(registrationVo.getEmail());
        this.mandateVo.setCustomerDebitType(MAX_AMT);
        this.mandateVo.setFrequency(AS_AND_WHEN_PRESENTED);
        this.mandateVo.setFromDate(new Date());
        this.mandateVo.setCustomerDebitAccount(SB);
        this.mandateVo.setAction(CREATE);
        this.mandateVo.setCustomerBankName(registrationVo.getBankname());
        this.mandateVo.setCustomerAccountNumber(registrationVo.getBankAccountNumber());
        this.mandateVo.setIfscNumber(registrationVo.getIfsc());
        this.mandateVo.setMicrNumber(registrationVo.getMicrCode());
        this.mandateVo.setCustomerDebitAccount(registrationVo.getBankSubType().equalsIgnoreCase("Current Account") ? "CA" : "SB");
        this.mandateVo.setCustomerName(registrationVo.getBeneficiaryName());
    }

    public AdvisorRegistrationVo getAdvisorRegistrationVo() {
        return advisorRegistrationVo;
    }

    public void setAdvisorRegistrationVo(AdvisorRegistrationVo advisorRegistrationVo) {
        this.advisorRegistrationVo = advisorRegistrationVo;
    }

    public void saveAdvisor(ActionEvent event) {
        String filename = null;
        if (advisorRegistrationVo.getAccountNumber().equals(advisorRegistrationVo.getRaccountNumber())) {
            boolean validdob = (AgeUtil.findAge(DateUtil.dateToString(advisorRegistrationVo.getDob(), dd_MM_yyyy)) > 18);
            if (validdob) {
                if (advisorRegistrationVo.getSebi_validity() != null && isStartDateValid(advisorRegistrationVo.getSebi_validity())) {
                    status = true;
                    if (advisorRegistrationVo.getPan().equalsIgnoreCase(EMPTY_STRING)
                            || !userProfileBAO.isPanNoExists(YES, advisorRegistrationVo.getPan(), advisorRegistrationVo.getRegNO())) {
                        this.advisorRegistrationVo.setUserStatus(EnumStatus.NEW_APPLICANT.getValue());
                        advisorRegistrationVo.setIsAdvisor(true);
                        if (StringUtils.hasText(advisorRegistrationVo.getSebiPath()) && !advisorRegistrationVo.getSebiPath().equalsIgnoreCase("No file chosen")) {
                            advisorRegistrationVo.setCertificateFilePath(advisorRegistrationVo.getSebiPath());
                            status = userProfileBAO.saveAdvisorProfile(advisorRegistrationVo, false);
                        } else {
                            FacesContext.getCurrentInstance().
                                    addMessage("partial_adv_form:file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "File not attched", "File not attched"));
                            status = false;
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("partial_adv_form:panno_in",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, PANNO_MESSAGE, PANNO_MESSAGE));
                    }
                } else {
                    status = false;
                    FacesContext.getCurrentInstance().
                            addMessage("partial_adv_form:validity",
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "validity expired", "validity expired"));
                }
            } else {
                status = false;
                FacesContext.getCurrentInstance().
                        addMessage("partial_adv_form:dob",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advisor cannot be minor", "Advisor cannot be minor"));
            }
        } else {
            status = false;
            FacesContext.getCurrentInstance().
                    addMessage("partial_adv_form:re-acno",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number mismatch", "Account Number mismatch"));
        }
    }

    public Part getFilepart() {
        return filepart;
    }

    public void setFilepart(Part filepart) {
        this.filepart = filepart;
    }

    public Map<String, Integer> getAddresstypeData() {
        return addresstypeData;
    }

    public void setAddresstypeData(Map<String, Integer> addresstypeData) {
        this.addresstypeData = addresstypeData;
    }

    public void autoGenerateBankDetails(String ifsc_code) {
        ArrayList<String> address = new ArrayList<String>();
        BankVo bankVo = new BankVo();
        bankVo = createUserBAO.getbankDetails(ifsc_code);
        registrationVo.setBankname(bankVo.getBank());
        registrationVo.setIfsc(bankVo.getIfsc());
        address = getBankAddress(bankVo.getAddress());
        registrationVo.setBuilding(address.get(0));
        registrationVo.setBstreet(address.get(1));
        registrationVo.setBank_country(IN);
        registrationVo.setBank_state(bankVo.getState());
        registrationVo.setBcity(bankVo.getCity());
        registrationVo.setMicrCode(bankVo.getMicr());
    }

    public ArrayList getBankAddress(String address) {
        ArrayList<String> ar = new ArrayList<String>();
        String[] bnkarray = address.split(COMA);
        String bnkbuilding = EMPTY_STRING;
        String bnkStreet = EMPTY_STRING;
        int len = bnkarray.length;
        int i = 0;
        int j = 0;
        for (String s : bnkarray) {
            if (i < len / 2) {
                bnkbuilding = i == 0 ? s : bnkbuilding + COMA + s;
                i++;
            } else {
                bnkStreet = j == 0 ? s : bnkStreet + COMA + s;
                j++;
            }

        }

        ar.add(bnkbuilding);
        ar.add(bnkStreet);
        return ar;
    }

    public Part getFilepart1() {
        return filepart1;
    }

    public void setFilepart1(Part filepart1) {
        this.filepart1 = filepart1;
    }

    public Part getFilepart2() {
        return filepart2;
    }

    public void setFilepart2(Part filepart2) {
        this.filepart2 = filepart2;
    }

    public Part getFilepart3() {
        return filepart3;
    }

    public void setFilepart3(Part filepart3) {
        this.filepart3 = filepart3;
    }

    public String getPanPath() {
        return panPath;
    }

    public void setPanPath(String panPath) {
        this.panPath = panPath;
    }
    public String getContextRoot() {
        return LookupDataLoader.getContextRoot();
    }

    public void setContextRoot(String contextRoot) {
        this.contextRoot = contextRoot;
    }
}
