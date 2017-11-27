/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * creted by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUpdateUserBAO;
import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.service.util.ConversionMethods;
import static com.gtl.mmf.service.util.IConstants.CHECKED;
import static com.gtl.mmf.service.util.IConstants.CORRESPONDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.DOCUMENTARY_EVIDENCE;
import static com.gtl.mmf.service.util.IConstants.DOC_EVIDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.IDENTITY_NAME;
import static com.gtl.mmf.service.util.IConstants.IDENTITY_PROOF;
import static com.gtl.mmf.service.util.IConstants.IN;
import static com.gtl.mmf.service.util.IConstants.NO;
import static com.gtl.mmf.service.util.IConstants.PAN_SUBMITTED_NAME;
import static com.gtl.mmf.service.util.IConstants.PERMENENT_NAME;
import static com.gtl.mmf.service.util.IConstants.SEBI_CERTIFICATE;
import static com.gtl.mmf.service.util.IConstants.SEBI_FILE_PATH;
import static com.gtl.mmf.service.util.IConstants.TWO_MB;
import static com.gtl.mmf.service.util.IConstants.UN_CHECKED;
import static com.gtl.mmf.service.util.IConstants.USER_PROFILE_PAGE;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.VALIDITY_CORRESPONDENCE;
import static com.gtl.mmf.service.util.IConstants.VALIDITY_PERMENENT;
import static com.gtl.mmf.service.util.IConstants.YES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.webapp.util.FileUploadUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("userprofilecontroller")
@Scope("view")
public class UserProfileController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.UserProfileController");
    private static final String INV_DASHBOARD = "/pages/investordashboard?faces-redirect=true\";";
    private static final String ADV_DASHBOARD = "/pages/advisordashboard?faces-redirect=true\";";
    private UserDetailsVO userDetailsVo;
    private Map<String, String> countryList;
    private Map<String, String> homeStateList;
    private Map<String, String> officeCityList;
    private Map<String, String> qualificationList;
    private Map<String, String> instituteList;
    private Map<String, String> yearList;
    private Map<String, String> brokerList;
    private String navigateURL;
    boolean samePermanentAddress;
    boolean firstAddress;
    boolean firstAddress2;

    @Autowired
    private IUserProfileBAO userProfileBAO;

    @Autowired
    private IUpdateUserBAO updateUserBAO;

    private Map<String, String> homeStateList2;
    private Map<String, String> cityList;
    private Map<String, String> cityListBank2;
    private Map<String, String> stateListBank2;
    private Map<String, String> nomineeStateList;
    private Map<String, String> minorStateList;
    private List<BankVo> filteredBank = new ArrayList<BankVo>();
    boolean nomineAddress;
    boolean nomineAddressMinor;
    String otherEvidence;
    private Part filepart;
    private Part filepart2;
    private Part filepart3;
    private Part filepart4;
    private Part filepart5;
    private Map<String, String> cityListNominee;
    private Map<String, String> cityListNomineeMinor;
    private Map<String, String> cityMap;
    private boolean status;
    private List<String> branchList;

    @PostConstruct
    public void afterinit() {
        FacesContext fct = FacesContext.getCurrentInstance();
        UserSessionBean userSessionBean = (UserSessionBean) fct.getExternalContext().getSessionMap().get(USER_SESSION);
        userSessionBean.setFromURL(USER_PROFILE_PAGE); 
        userDetailsVo = userProfileBAO.getUserProfile(userSessionBean.getUserId(), userSessionBean.getUserType());

        this.setYearList(LookupDataLoader.getYearList());
        this.setCountryList(LookupDataLoader.getCountryList());

        this.setHomeStateList(LookupDataLoader.getStateListLookup());
        this.setHomeStateList2(this.getHomeStateList());
        this.setStateListBank2(this.getHomeStateList());

        this.setCityList(LookupDataLoader.getCityListLookup());
        this.setOfficeCityList(this.getCityList());
        this.setCityListBank2(this.getCityList());

        if (!userDetailsVo.getMasterApplicant().getAdvisor()) {
            userDetailsVo.setHomeCountry(userDetailsVo.getMasterApplicant().getAddressCountry());
            if (userDetailsVo.getMasterApplicant().getDocumentaryEvedenceOther() != null
                    && userDetailsVo.getMasterApplicant().getDocumentaryEvedenceOther()) {
                otherEvidence = userDetailsVo.getMasterApplicant().getDocumentaryEvedence();
                userDetailsVo.getMasterApplicant().setDocumentaryEvedence("Others");
            }
            userDetailsVo.setOfficeCountry(userDetailsVo.getOfficeCountry() == null ? IN : userDetailsVo.getOfficeCountry());

            userDetailsVo.setRe_accountNumber(userDetailsVo.getTradingAccNo());
            setNomineeStateList(this.getHomeStateList());
            this.setMinorStateList(this.getHomeStateList());
            this.setCityListNominee(this.getCityList());
            this.setCityListNomineeMinor(this.getCityList());
            if (userDetailsVo.getMasterApplicant().getPermanentAddress() != null
                    && userDetailsVo.getMasterApplicant().getPermanentAddress()) {
                firstAddress2 = true;
                onChnageCountryHome2();
            }
        } else {
            if (userDetailsVo.getMasterApplicant().getPermanentAddress() != null
                    && !userDetailsVo.getMasterApplicant().getPermanentAddress()) {
                firstAddress2 = true;
                onChnageCountryHome2();
            }
        }
        firstAddress = true;
        //onSelectHomeCountry();
        //onChnageCountryHome();
        if (!userDetailsVo.getMasterApplicant().getAdvisor()) {
            nomineAddressMinor = true;
            nomineAddress = true;
            setCountryForNomine();
            onChnageCountryHome();
        }
        onChnageCountryBank();
    }

    public void upadtePersonalProfile() {
//        try {
//            reArrangeUserDetails(userDetailsVo);
//            updateUserBAO.updateUserDetails(userDetailsVo);
//            navigateURL = (userDetailsVo.getUserType().equals(YES)) ? ADV_DASHBOARD : INV_DASHBOARD;
//        } catch (ParseException ex) {
//            FacesContext.getCurrentInstance().addMessage("userProfile_form", new FacesMessage("An error has been occured.Please try later"));
//            navigateURL = EMPTY_STRING;
//            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
//        } catch (NullPointerException ex) {
//            FacesContext.getCurrentInstance().addMessage("userProfile_form", new FacesMessage("An error has been occured.Please try later"));
//            navigateURL = EMPTY_STRING;
//            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
//        }
        String fileName = null;
        status = false;
        StringBuilder logMessage = new StringBuilder();

        if (userDetailsVo.getUserType().equalsIgnoreCase(YES)) {
            if (userDetailsVo.getMasterApplicant().getAccountNumber().equalsIgnoreCase(userDetailsVo.getRe_accountNumber())) {
                if (filepart != null && filepart.getSize() != ZERO) {
                    if (filepart.getSize() <= TWO_MB) {
                        fileName = FileUploadUtil.UploadUserFile(userDetailsVo.getMasterApplicant().getRegistrationId(), filepart, SEBI_FILE_PATH, SEBI_CERTIFICATE);

                        if (fileName != null) {
                            userDetailsVo.getQualificationTb().setSebiCertificatePath(fileName);
                            updateUserBAO.updateUserDetails(userDetailsVo);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("adv_profile:file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("adv_profile:file",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }
                } else {
                    updateUserBAO.updateUserDetails(userDetailsVo);
                    status = true;
                    logMessage.append("User details with registration id ").append(userDetailsVo.getMasterApplicant().getRegistrationId())
                            .append(" updated.");
                }
            } else {
                status = false;
                FacesContext.getCurrentInstance().
                        addMessage("adv_profile:re-acno",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number miss Match", null));
            }
        } else {
            status = true;
            if (userDetailsVo.getMasterApplicant().getAccountNumber().equalsIgnoreCase(userDetailsVo.getRe_accountNumber())) {
                if (userDetailsVo.getUserType().equalsIgnoreCase(NO)
                        && userDetailsVo.getMasterApplicant().getDocumentaryEvedence() != null) {
                    userDetailsVo.getMasterApplicant().setDocumentaryEvedenceOther(
                            userDetailsVo.getMasterApplicant().getDocumentaryEvedence().equalsIgnoreCase("Others"));
                    userDetailsVo.getMasterApplicant().setDocumentaryEvedence(
                            userDetailsVo.getMasterApplicant().getDocumentaryEvedenceOther()
                            ? otherEvidence : userDetailsVo.getMasterApplicant().getDocumentaryEvedence());
                }
                LOGGER.info("************************Updating Investor data ********************************");
                if (filepart != null && filepart.getSize() != ZERO) {
                    if (filepart.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Identity Proof ********************************");
                        String identityProof = FileUploadUtil.UploadUserFile(userDetailsVo.getMasterApplicant().getRegistrationId(), filepart, IDENTITY_PROOF, IDENTITY_NAME);
                        LOGGER.log(Level.INFO, "File name for Identity proof :{0}", identityProof);
                        if (identityProof != null) {
                            userDetailsVo.getMasterApplicant().setIdentityProofPath(identityProof);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("userProfile_form:idProof",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id proof File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("userProfile_form:idProof",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (filepart2 != null && filepart2.getSize() != ZERO) {
                    if (filepart2.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Address Proof ********************************");
                        String correspondence = FileUploadUtil.UploadUserFile(userDetailsVo.getMasterApplicant().getRegistrationId(), filepart2, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Corresspondence address:{0}", correspondence);
                        if (correspondence != null) {
                            userDetailsVo.getMasterApplicant().setCorrespondenceAddressPath(correspondence);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("userProfile_form:adrsvalidity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Permanent Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("userProfile_form:adrsvalidity",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (userDetailsVo.getMasterApplicant().getPermanentAddress()) {
                    if (filepart3 != null && filepart3.getSize() != ZERO) {
                        if (filepart3.getSize() <= TWO_MB) {
                            LOGGER.info("************************File Uploading Permenent Address ********************************");
                            String permenent = FileUploadUtil.UploadUserFile(userDetailsVo.getMasterApplicant().getRegistrationId(), filepart3, VALIDITY_PERMENENT, PERMENENT_NAME);
                            LOGGER.log(Level.INFO, "File name for Permenet Address :{0}", permenent);
                            if (permenent != null) {
                                userDetailsVo.getMasterApplicant().setPermanentAddressPath(permenent);
                                status = true;
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("userProfile_form:address2_validity",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Idenity Address File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("userProfile_form:address2_validity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }

                    }
                }
                if (filepart4 != null && filepart4.getSize() != ZERO) {
                    if (filepart4.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading  DOCUMENTATRY EVIDENCE FileAddress Proof ********************************");
                        String documentary_evidence = FileUploadUtil.UploadUserFile(userDetailsVo.getMasterApplicant().getRegistrationId(), filepart4, DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Documentary Evidence Address Proof:{0}", documentary_evidence);
                        if (documentary_evidence != null) {
                            userDetailsVo.getMasterApplicant().setDocumentaryEvidencePath(documentary_evidence);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("userProfile_form:evidence_box",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Documentary Evidence Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("userProfile_form:evidence_box",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (filepart5 != null && filepart5.getSize() != ZERO) {
                    if (filepart5.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading PAN COPY ********************************");
                        String panCopy = FileUploadUtil.UploadUserFile(userDetailsVo.getMasterApplicant().getRegistrationId(), filepart5, IDENTITY_PROOF, PAN_SUBMITTED_NAME);
                        LOGGER.log(Level.INFO, "File name for PAN :{0}", panCopy);
                        if (panCopy != null) {
                            userDetailsVo.getMasterApplicant().setIdentityProofPath(panCopy);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("userProfile_form:pan_file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "PAN Copy Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("userProfile_form:pan_file",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (status) {
                    LOGGER.info("************************Updating data ********************************");
                    updateUserBAO.updateUserDetails(userDetailsVo);
                    //status = true;
                    logMessage.append("User details with registration id ").append(userDetailsVo.getMasterApplicant().getRegistrationId())
                            .append(" updated.");
                }
            } else {
                status = false;
                FacesContext.getCurrentInstance().
                        addMessage("userProfile_form:re-acno",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number miss Match", null));
            }

        }
    }

    public void cancelPersonalProfile() {
//        navigateURL = EMPTY_STRING;
        //redirect to dashboard without saving the changes.
        status = true;
    }

    private void reArrangeUserDetails(UserDetailsVO userDetailsVo) throws ParseException {
        userDetailsVo.getMasterApplicant().setAddressCity(userDetailsVo.getHomeCity());
        userDetailsVo.getMasterApplicant().setDob(userDetailsVo.getDob());
        userDetailsVo.getMasterApplicant().setAddressPincode(userDetailsVo.getHomePincode());
        userDetailsVo.getMasterApplicant().setTelephone(userDetailsVo.getMasterApplicant().getTelephone());
        userDetailsVo.getMasterApplicant().setMobile(userDetailsVo.getHomeMobile());
        userDetailsVo.getMasterApplicant().setWorkOrganization(userDetailsVo.getOfficeOrganization());
        userDetailsVo.getMasterApplicant().setJobTitle(userDetailsVo.getOfficeJobTitle());
        userDetailsVo.getMasterApplicant().setSebiRegno(userDetailsVo.getSebiCertificationNo());
        userDetailsVo.setInvestorTotalFund(userDetailsVo.getTradingAmount());
    }

    public String navigationURL() {
        if(status){
            navigateURL = (userDetailsVo.getUserType().equals(YES)) ? ADV_DASHBOARD : INV_DASHBOARD;
        }else{
            navigateURL = EMPTY_STRING;
        }
        return navigateURL;
    }

    public void onSelectHomeCountry() {

        if (!userDetailsVo.getHomeCountry().equalsIgnoreCase(IN)) {
            homeStateList = new HashMap<String, String>();
            if (!firstAddress) {
                userDetailsVo.getMasterApplicant().setAddressState(EMPTY_STRING);
            }

        } else {
            this.setHomeStateList(LookupDataLoader.getStateListLookup());
            userDetailsVo.setHomeCountry(IN);
        }
        firstAddress = false;
    }

    public void onSelectOfficeCountry() {
        officeCityList = userProfileBAO.getCityList(userDetailsVo.getOfficeCountry());
        userDetailsVo.setOfficeCity(EMPTY_STRING);
    }

    public boolean isHomeStateEditable() {
        return homeStateList.isEmpty();
    }

    public Boolean isOfficeCityEditable() {
        return officeCityList.isEmpty();
    }

    public Boolean isQualificationVisible() {
        return userDetailsVo.getUserType().equals(YES);
    }

//    public Boolean isPrimaryQualificationValid() {
//        return userDetailsVo.getQualificationTb().getQualificationPrimary() > ZERO;
//    }
//
//    public Boolean isSecondaryQualificationValid() {
//        return userDetailsVo.getQualificationSecondary() > ZERO;
//    }
//
//    public Boolean isTertirayQualificationValid() {
//        return userDetailsVo.getQualificationTertiary() > ZERO;
//    }
    public UserDetailsVO getUserDetails() {
        return userDetailsVo;
    }

    public void setUserDetails(UserDetailsVO userDetailsVo) {
        this.userDetailsVo = userDetailsVo;
    }

    public Map<String, String> getCountryList() {
        return countryList;
    }

    public Map<String, String> getOfficeCityList() {
        return officeCityList;
    }

    public Map<String, String> getQualificationList() {
        return qualificationList;
    }

    public Map<String, String> getInstituteList() {
        return instituteList;
    }

    public Map<String, String> getYearList() {
        return yearList;
    }

    public Map<String, String> getBrokerList() {
        return brokerList;
    }

    public Boolean isAdvisor() {
        return userDetailsVo.getUserType().equals(YES);
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

    public Map<String, Integer> getIncomerangedata() {
        return LookupDataLoader.getIncomerangeMap();
    }

    public Map<String, Integer> getOccupationData() {
        return LookupDataLoader.getOccupationMap();
    }

    public Map<String, Integer> getBankSubTypeData() {
        return LookupDataLoader.getBankSubTypeMap();
    }

    public HashMap<String, Integer> getGenderData() {
        return LookupDataLoader.getGenderDataMap();
    }

    public HashMap<String, Integer> getMaritalstatus() {
        return LookupDataLoader.getMaritalstatusMap();
    }
    
    public Map<String, String> getNomineePoi() {
        return LookupDataLoader.getNomineePoiMap();
    }

    public boolean isSamePermanentAddress() {
        return samePermanentAddress;
    }

    public void setSamePermanentAddress(boolean samePermanentAddress) {
        this.samePermanentAddress = samePermanentAddress;
    }

    public void onChangeValue() {
        String summary = samePermanentAddress ? CHECKED : UN_CHECKED;

        if (summary.equalsIgnoreCase(CHECKED)) {
            userDetailsVo.getMasterApplicant().setPermanentAddress(true);
            setSamePermanentAddress(true);
        } else {
            userDetailsVo.getMasterApplicant().setPermanentAddress(false);
            setSamePermanentAddress(false);
        }
    }

    public void onSelectHomeCountry2() {
        if (!userDetailsVo.getMasterApplicant().getAddress2Country().equalsIgnoreCase(IN)) {
            homeStateList2 = new HashMap<String, String>();
            if (!firstAddress2) {
                userDetailsVo.getMasterApplicant().setAddress2State(EMPTY_STRING);
            }
        } else {
            this.setHomeStateList2(LookupDataLoader.getStateListLookup());
            userDetailsVo.getMasterApplicant().setAddress2Country(IN);
        }
        firstAddress = false;
    }

    public boolean isHomeState2Editable() {
        return homeStateList2.isEmpty();
    }

    public void setCountryList(Map<String, String> countryList) {
        this.countryList = countryList;
    }

    public void setOfficeCityList(Map<String, String> officeCityList) {
        this.officeCityList = officeCityList;
    }

    public Map<String, String> getHomeStateList() {
        return homeStateList;
    }

    public void setHomeStateList(Map<String, String> homeStateList) {
        this.homeStateList = homeStateList;
    }

    public Map<String, String> getHomeStateList2() {
        return homeStateList2;
    }

    public void setHomeStateList2(Map<String, String> homeStateList2) {
        this.homeStateList2 = homeStateList2;
    }

    public Map<String, String> getCityList() {
        return cityList;
    }

    public void setCityList(Map<String, String> cityList) {
        this.cityList = cityList;
    }

    public Map<String, String> getCityListBank2() {
        return cityListBank2;
    }

    public void setCityListBank2(Map<String, String> cityListBank2) {
        this.cityListBank2 = cityListBank2;
    }

    public Map<String, String> getStateListBank2() {
        return stateListBank2;
    }

    public void setStateListBank2(Map<String, String> stateListBank2) {
        this.stateListBank2 = stateListBank2;
    }

    public Map<String, String> getNomineeStateList() {
        return nomineeStateList;
    }

    public void setNomineeStateList(Map<String, String> nomineeStateList) {
        this.nomineeStateList = nomineeStateList;
    }

    public Map<String, String> getMinorStateList() {
        return minorStateList;
    }

    public void setMinorStateList(Map<String, String> minorStateList) {
        this.minorStateList = minorStateList;
    }

    public String getOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(String otherEvidence) {
        this.otherEvidence = otherEvidence;
    }

    public void onChnageCountryHome() {
        if (!userDetailsVo.getMasterApplicant().getAddressCountry().equalsIgnoreCase(IN)) {
            homeStateList = new HashMap<String, String>();
            cityList = new HashMap<String, String>();
            if (!firstAddress) {
                userDetailsVo.getMasterApplicant().setAddressState(EMPTY_STRING);
                userDetailsVo.getMasterApplicant().setAddressCity(EMPTY_STRING);
            }
        } else {
            this.setHomeStateList(LookupDataLoader.getStateListLookup());
            this.setCityList(LookupDataLoader.getCityListLookup());
            userDetailsVo.getMasterApplicant().setAddressCountry(IN);
            onChangeState(userDetailsVo.getMasterApplicant().getAddressState(), 1);
        }
        firstAddress = false;
    }

    public Map<Integer, String> getBankList() {
        return LookupDataLoader.getBankListLookup();
    }

    public boolean isHomeStateListEditable() {
        return homeStateList.isEmpty();
    }

    public boolean isHomeStateList2Editable() {
        return homeStateList2.isEmpty();
    }

    public boolean isStateListBanktEditable() {
        return stateListBank2.isEmpty();
    }

    public boolean isCityListEditable() {
        return cityList.isEmpty();
    }

    public boolean isOfficeCityListEditable() {
        return officeCityList.isEmpty();
    }

    public boolean isCityListBank2Editable() {
        return cityListBank2.isEmpty();
    }

    public boolean isHomeStateListEditable2() {
        return homeStateList2.isEmpty();
    }

    public boolean isNomineeStateListEditable() {
        return nomineeStateList.isEmpty();
    }

    public boolean isMinorStateListEditable() {
        return minorStateList.isEmpty();
    }

    public boolean isCityListNomineeMinorEditable() {
        return cityListNomineeMinor.isEmpty();
    }

    private void setCountryForNomine() {
        userDetailsVo.getNomineeDetails().setCountryNominee(userDetailsVo
                .getNomineeDetails().getCountryNominee() == null ? IN
                : userDetailsVo.getNomineeDetails().getCountryNominee());
        userDetailsVo.getNomineeDetails().setCountryNomineeMinor(userDetailsVo
                .getNomineeDetails().getCountryNomineeMinor() == null ? IN
                : userDetailsVo.getNomineeDetails().getCountryNomineeMinor());
        onChnageNomineeCountry();
        onChnageMinorCountry();
    }

    public void onChnageNomineeCountry() {
        if (!userDetailsVo.getNomineeDetails().getCountryNominee().equalsIgnoreCase(IN)) {
            nomineeStateList = new HashMap<String, String>();
            cityListNominee = new HashMap<String, String>();
            if (!nomineAddress) {
                userDetailsVo.getNomineeDetails().setStateNominee(EMPTY_STRING);
                userDetailsVo.getNomineeDetails().setPlaceNominee(EMPTY_STRING);

            }
        } else {
            this.setNomineeStateList(LookupDataLoader.getStateListLookup());
            this.setCityListNominee(LookupDataLoader.getCityListLookup());
            userDetailsVo.getNomineeDetails().setCountryNominee(IN);
            onChangeState(userDetailsVo.getNomineeDetails().getStateNominee(), 4);
        }
        nomineAddress = false;
    }

    public void onChnageMinorCountry() {
        if (!userDetailsVo.getNomineeDetails().getCountryNomineeMinor().equalsIgnoreCase(IN)) {
            minorStateList = new HashMap<String, String>();
            cityListNomineeMinor = new HashMap<String, String>();
            if (!nomineAddressMinor) {
                userDetailsVo.getNomineeDetails().setStateNomineeMinor(EMPTY_STRING);
                userDetailsVo.getNomineeDetails().setPlaceNomineeMinor(EMPTY_STRING);
            }
        } else {
            this.setMinorStateList(LookupDataLoader.getStateListLookup());
            this.setCityListNomineeMinor(LookupDataLoader.getCityListLookup());
            userDetailsVo.getNomineeDetails().setCountryNomineeMinor(IN);
            onChangeState(userDetailsVo.getNomineeDetails().getStateNomineeMinor(), 5);
        }
        nomineAddressMinor = false;
    }

    public void onChnageCountryHome2() {
        if (!userDetailsVo.getMasterApplicant().getAddress2Country().equalsIgnoreCase(IN)) {
            homeStateList2 = new HashMap<String, String>();
            officeCityList = new HashMap<String, String>();
            if (!firstAddress2) {
                userDetailsVo.getMasterApplicant().setAddress2State(EMPTY_STRING);
                userDetailsVo.getMasterApplicant().setAddress2City(EMPTY_STRING);
            }
        } else {
            this.setHomeStateList2(LookupDataLoader.getStateListLookup());
            this.setOfficeCityList(LookupDataLoader.getCityListLookup());
            userDetailsVo.getMasterApplicant().setAddress2Country(IN);
            onChangeState(userDetailsVo.getMasterApplicant().getAddress2State(), 2);
        }
        firstAddress2 = false;
    }

    public void onChnageCountryOffice() {
        officeCityList = userProfileBAO.getCityList(userDetailsVo.getOfficeCountry());
        userDetailsVo.setOfficeCity(EMPTY_STRING);
    }

    public void onChnageCountryBank() {
        if (userDetailsVo.getMasterApplicant().getAdvisor()) {
            if (!userDetailsVo.getMasterApplicant().getBankCountry().equalsIgnoreCase(IN)) {
                stateListBank2 = new HashMap<String, String>();
                cityListBank2 = new HashMap<String, String>();
                userDetailsVo.getMasterApplicant().setBankState(EMPTY_STRING);
                userDetailsVo.getMasterApplicant().setBankCity(EMPTY_STRING);

            } else {
                this.setStateListBank2(LookupDataLoader.getStateListLookup());
                this.setCityListBank2(LookupDataLoader.getCityListLookup());
                userDetailsVo.getMasterApplicant().setBankCountry(IN);
                onChangeState(userDetailsVo.getMasterApplicant().getBankState(), 3);
            }
        }
                        


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

    public boolean isCityListNomineeEditable() {
        return cityListNominee.isEmpty();
    }

    public void setYearList(Map<String, String> yearList) {
        this.yearList = yearList;
    }

    public void onChangeState(String state, int id) {
        cityMap = ConversionMethods.getStateWiseCity(state);
        if (cityMap != null && !cityMap.isEmpty()) {
            if (id == 1) {
                cityList = cityMap;
            } else if (id == 2) {
                officeCityList = cityMap;
            } else if (id == 3) {
                cityListBank2 = cityMap;
            } else if (id == 4) {
                cityListNominee = cityMap;
            } else {
                cityListNomineeMinor = cityMap;
            }
        }
    }

    public Part getFilepart() {
        return filepart;
    }

    public void setFilepart(Part filepart) {
        this.filepart = filepart;
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

    public Part getFilepart4() {
        return filepart4;
    }

    public void setFilepart4(Part filepart4) {
        this.filepart4 = filepart4;
    }

    public Part getFilepart5() {
        return filepart5;
    }

    public void setFilepart5(Part filepart5) {
        this.filepart5 = filepart5;
    }

    public Map<String, String> getdependentUsed() {
        return LookupDataLoader.getDependentDataMap();
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
            if(bankVo.getIfsc().equalsIgnoreCase(newScrip)) {
                if(userDetailsVo.getMasterApplicant().getAdvisor()) {
                    userDetailsVo.getMasterApplicant().setBankName(bankVo.getBank());
                    userDetailsVo.getMasterApplicant().setIfcsNumber(bankVo.getIfsc());
                    userDetailsVo.getMasterApplicant().setBankBuilding(bankVo.getAddress());
                    userDetailsVo.getMasterApplicant().setBankCountry(IN);
                    userDetailsVo.getMasterApplicant().setBankState(bankVo.getState());
                    userDetailsVo.getMasterApplicant().setBankCity(bankVo.getCity());
                    userDetailsVo.getMasterApplicant().setMicrNumber(bankVo.getMicr());
                } else {
                    userDetailsVo.setTradingBank(bankVo.getBank());
                    userDetailsVo.setTradingIFSCNo(bankVo.getIfsc());
                    userDetailsVo.getMasterApplicant().setBankBuilding(bankVo.getAddress());
                    userDetailsVo.getMasterApplicant().setBankCountry(IN);
                    userDetailsVo.getMasterApplicant().setBankState(bankVo.getState());
                    userDetailsVo.getMasterApplicant().setBankCity(bankVo.getCity());
                    userDetailsVo.getMasterApplicant().setMicrNumber(bankVo.getMicr());
                }
            }
        }
    }
    

    public List<String> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<String> branchList) {
        this.branchList = branchList;
    }
    
    //variable i used to identify calling from bank change(1 for usual,2 for inv bank change, 3 for adv bnk chng)
    public void onBankSelect(String bank, String state, String city,int i){
        List<String> branches = new ArrayList<String>();
        filteredBank = userProfileBAO.onBankSelect(bank, state, city);
        for (BankVo bankVo : filteredBank) {
            branches.add(bankVo.getBranch());
        }
        Collections.sort(branches);
        this.setBranchList(branches);
    }
    
    public void onBranchSelect(){
        for (BankVo bankVo : filteredBank) {
            if(userDetailsVo.getMasterApplicant().getAdvisor()) {
                if(bankVo.getBranch().equals(userDetailsVo.getMasterApplicant().getBranch())) {
                    userDetailsVo.getMasterApplicant().setIfcsNumber(bankVo.getIfsc());
                    userDetailsVo.getMasterApplicant().setBankBuilding(bankVo.getAddress());
                    userDetailsVo.getMasterApplicant().setBankCity(bankVo.getCity());
                }
            } else {
                if(bankVo.getBranch().equals(userDetailsVo.getMasterApplicant().getBranch())) {
                    userDetailsVo.setTradingIFSCNo(bankVo.getIfsc());
                    userDetailsVo.getMasterApplicant().setBankBuilding(bankVo.getAddress());
                    userDetailsVo.getMasterApplicant().setBankCity(bankVo.getCity());
                }
            }
        }
    }*/
}
