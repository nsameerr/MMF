/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.google.gson.Gson;
import com.gtl.mmf.bao.ICreateUserBAO;
import com.gtl.mmf.bao.IUpdateUserBAO;
import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.bao.RegistrationBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.report.util.RegistrationPdfDataPreparation;
import com.gtl.mmf.service.util.ConversionMethods;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.CORRESPONDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.DOCUMENTARY_EVIDENCE;
import static com.gtl.mmf.service.util.IConstants.DOC_EVIDENCE_NAME;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.FILE_PATH;
import static com.gtl.mmf.service.util.IConstants.IDENTITY_NAME;
import static com.gtl.mmf.service.util.IConstants.IDENTITY_PROOF;
import static com.gtl.mmf.service.util.IConstants.IN;
import static com.gtl.mmf.service.util.IConstants.NO;
import static com.gtl.mmf.service.util.IConstants.PAN_SUBMITTED_NAME;
import static com.gtl.mmf.service.util.IConstants.PERMENENT_NAME;
import static com.gtl.mmf.service.util.IConstants.REG_KIT_SERIAL;
import static com.gtl.mmf.service.util.IConstants.SEARCH_TEXT;
import static com.gtl.mmf.service.util.IConstants.SEBI_CERTIFICATE;
import static com.gtl.mmf.service.util.IConstants.SEBI_FILE_PATH;
import static com.gtl.mmf.service.util.IConstants.SERVICE_SAVE_REGISTRATION;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TWO_MB;
import static com.gtl.mmf.service.util.IConstants.VALIDITY_CORRESPONDENCE;
import static com.gtl.mmf.service.util.IConstants.VALIDITY_PERMENENT;
import static com.gtl.mmf.service.util.IConstants.YES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.RestClient;
import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.service.vo.BoRegDataServiceVo;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.FailedMailDetailsVO;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationDataProcessingVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.gtl.mmf.webapp.util.FileUploadUtil;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;

/**
 *
 * @author 07958
 */
@Named("createUserBean")
@Scope("view")
public class CreateUserBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.CreateUserBean");
    private static final String EMAIL_FIELD = "frm_create_user:email-in";
    private static final String EMAIL_MESSAGE = "Email address already exists";
    private static final String PANNO_FIELD = "frm_create_user:panno_in";
    private static final String PANNO_MESSAGE = "PAN number already registered";
    private static final String REDIRECT_NEW_USER_LIST = "/pages/admin/new_user_list?faces-redirect=true";
    private static final String REDIRECT_CREATE_USER_PAGE = "/pages/admin/createuser?faces-redirect=true";
    private static final String DOCUMENTS_PENDING = "documents pending";
    private static final String ACTIVATION_DENIED_MSG = "User can't activate document status as pending";
    private static final String ACTIVATION_DENIED_NO_SEBI_MSG = "User can't activate without SEBI registration";
    private static final String DEL_OPTION = "delOption";
    private static final String CREATE = "Create";
    private static final String REG_KIT_UNAVAILABLE = "No kits are available.please allocate registration kit under 'Allot kit' option and proceed.";

    @Autowired
    private ICreateUserBAO createUserBAO;
    @Autowired
    private IUpdateUserBAO updateUserBAO;
    @Autowired
    private IUserProfileBAO userProfileBAO;
    @Autowired
    private RegistrationBAO registrationBAO;
    private UserDetailsVO userDetails;

    private Map<String, String> countryList;

    private Map<String, String> homeStateList;
    private Map<String, String> homeStateList2;
    private Map<String, String> stateListBank2;
//    private Map<String, List<String>> stateListBank2;
    private Map<String, String> nomineeStateList;
    private Map<String, String> minorStateList;

    private List<BankVo> filteredBank = new ArrayList<BankVo>();
    private Map<String, String> cityList;
    private Map<String, String> officeCityList;
    private Map<String, String> cityListBank2;
//    private List<String> cityListBank2;
    private Map<String, String> yearList;
    private Map<String, Integer> userStatus;
    private String userTypeSelected;
    private String searchText;
    private boolean userExists;
    private boolean status;
    private boolean activeStatus = false;
    private String title = "Update";
    private String redirectPage;
    private Boolean hardDelete = Boolean.FALSE;
    boolean firstAddress;
    boolean firstAddress2;
    boolean nomineAddress;
    boolean nomineAddressMinor;
    private boolean activateAccount;
    private Map<String, String> cityListNominee;
    private Map<String, String> cityListNomineeMinor;
    String otherEvidence;
    private Part filepart;
    private Part filepart2;
    private Part filepart3;
    private boolean userActive;
    private Part filepart4;
    private Part filepart5;
    private Map<String, String> cityMap;
    private static final String kitSeries = PropertiesLoader.getPropertiesValue(REG_KIT_SERIAL);
    private String kitnumber;
    private boolean dontknowIfsc = false;
    private List<String> branchList;
    private Map<String, Integer> addresstypeData;
    private boolean isContract = false;
    private boolean enableterminationButton;

    @PostConstruct
    public void afterInit() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        this.userDetails = (UserDetailsVO) storedValues.get("selectedUser");
        this.userTypeSelected = (String) storedValues.get("userTypeSelected");
        this.searchText = (String) storedValues.get(SEARCH_TEXT);

        this.setYearList(LookupDataLoader.getYearList());
        this.setCountryList(LookupDataLoader.getCountryList());
        this.setUserStatus(LookupDataLoader.getUserStatusList());

        this.setHomeStateList(LookupDataLoader.getStateListLookup());
        this.setHomeStateList2(this.getHomeStateList());
        this.setStateListBank2(this.getHomeStateList());
//        this.setStateListBank2(LookupDataLoader.getStatecitymap());

        this.setCityList(LookupDataLoader.getCityListLookup());
        this.setOfficeCityList(this.getCityList());
        this.setCityListBank2(this.getCityList());
        // Addresstype display in admin console 
        this.setAddresstypeData(LookupDataLoader.getAddressTypeList());
        userDetails.setAdminUser(true);
        if (!userDetails.getMasterApplicant().getAdvisor()) {
            userDetails.setHomeCountry(userDetails.getMasterApplicant().getAddressCountry());
            if (userDetails.getMasterApplicant().getDocumentaryEvedenceOther() != null
                    && userDetails.getMasterApplicant().getDocumentaryEvedenceOther()) {
                otherEvidence = userDetails.getMasterApplicant().getDocumentaryEvedence();
                userDetails.getMasterApplicant().setDocumentaryEvedence("Others");
            }
            userDetails.setOfficeCountry(userDetails.getOfficeCountry() == null ? IN : userDetails.getOfficeCountry());

            userDetails.setRe_accountNumber(userDetails.getTradingAccNo());
            setNomineeStateList(this.getHomeStateList());
            this.setMinorStateList(this.getHomeStateList());
            this.setCityListNominee(this.getCityList());
            this.setCityListNomineeMinor(this.getCityList());
            if (userDetails.getMasterApplicant().getPermanentAddress() != null
                    && userDetails.getMasterApplicant().getPermanentAddress()) {
                firstAddress2 = true;
                onChnageCountryHome2();
            }
            if (userDetails.getMasterApplicant().getKitNumber() != null) {
                kitnumber = kitSeries.concat(userDetails.getMasterApplicant().getKitNumber().toString());
            }
        } else if (userDetails.getMasterApplicant().getPermanentAddress() != null
                && !userDetails.getMasterApplicant().getPermanentAddress()) {
            firstAddress2 = true;
            onChnageCountryHome2();
        }
        //Checking for activate button to enable by comparing process flow check boxes
        checkForAcoountActivateListener();

        firstAddress = true;
//        onChnageCountryHome();
        if (!userDetails.getMasterApplicant().getAdvisor()) {
            nomineAddressMinor = true;
            nomineAddress = true;
            setCountryForNomine();
            onChnageCountryHome();
        }
        onChnageCountryBank();

        if (userDetails.getMasterApplicant().getStatus() == 100) {
            activeStatus = false;
            activateAccount = false;
        } else if (userDetails.isExisitngUser()) {
            activeStatus = true;
        }
        if (!userDetails.isExisitngUser()) {
            this.setTitle(CREATE);
        }

        userActive = userDetails.getMasterApplicant().getStatus() == 100;

        // Enable Termination Button display or not
        enableterminationButton = userProfileBAO.getUserEnableterminationStatus(userDetails.getUserId());

    }

    private void setCountryForNomine() {
        userDetails.getNomineeDetails().setCountryNominee(userDetails
                .getNomineeDetails().getCountryNominee() == null ? IN
                        : userDetails.getNomineeDetails().getCountryNominee());
        userDetails.getNomineeDetails().setCountryNomineeMinor(userDetails
                .getNomineeDetails().getCountryNomineeMinor() == null ? IN
                        : userDetails.getNomineeDetails().getCountryNomineeMinor());
        onChnageNomineeCountry();
        onChnageMinorCountry();
    }

    public void doActionUpdateUser(ActionEvent event) {
        userExists = userProfileBAO.isEmailExists(userDetails.getMasterApplicant().getEmail(), userDetails.getMasterApplicant().getRegistrationId());
        StringBuilder logMessage = new StringBuilder();
        status = false;
        if (userExists) {
            FacesContext.getCurrentInstance().addMessage(EMAIL_FIELD,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_MESSAGE, EMAIL_MESSAGE));
            status = false;
            logMessage.append("User details updation failed - user already exists.");
        } else if (!userDetails.getMasterApplicant().getPan().equalsIgnoreCase("")
                && userProfileBAO.isPanNoExists(userDetails.getUserType(), userDetails.getMasterApplicant().getPan(), userDetails.getMasterApplicant().getRegistrationId())) {
            FacesContext.getCurrentInstance().addMessage(PANNO_FIELD,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, PANNO_MESSAGE, PANNO_MESSAGE));
            status = false;
            logMessage.append("User details updation failed - pan no already exists.");
        } else {
            String fileName = null;

            if (userDetails.getUserType().equalsIgnoreCase(YES)) {
                if (userDetails.getMasterApplicant().getAccountNumber().equalsIgnoreCase(userDetails.getRe_accountNumber())) {
                    if (filepart != null && filepart.getSize() != ZERO) {
                        if (filepart.getSize() <= TWO_MB) {
                            fileName = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, SEBI_FILE_PATH, SEBI_CERTIFICATE);

                            if (fileName != null) {
                                userDetails.getQualificationTb().setSebiCertificatePath(fileName);
                                updateUserBAO.updateUserDetails(userDetails);
                                status = true;
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("frm_create_user:file",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }
                    } else {
                        updateUserBAO.updateUserDetails(userDetails);
                        status = true;
                        logMessage.append("User details with registration id ").append(userDetails.getMasterApplicant().getRegistrationId())
                                .append(" updated.");
                    }
                } else {
                    status = false;
                    FacesContext.getCurrentInstance().
                            addMessage("frm_create_user:re-acno",
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number miss Match", null));
                }
            } else {
                status = true;
                if (userDetails.getUserType().equalsIgnoreCase(NO)
                        && userDetails.getMasterApplicant().getDocumentaryEvedence() != null) {
                    userDetails.getMasterApplicant().setDocumentaryEvedenceOther(
                            userDetails.getMasterApplicant().getDocumentaryEvedence().equalsIgnoreCase("Others"));
                    userDetails.getMasterApplicant().setDocumentaryEvedence(
                            userDetails.getMasterApplicant().getDocumentaryEvedenceOther()
                                    ? otherEvidence : userDetails.getMasterApplicant().getDocumentaryEvedence());
                }
                LOGGER.info("************************Updating Investor data ********************************");
                if (filepart != null && filepart.getSize() != ZERO) {
                    if (filepart.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Identity Proof ********************************");
                        String identityProof = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, IDENTITY_PROOF, IDENTITY_NAME);
                        LOGGER.log(Level.INFO, "File name for Identity proof :{0}", identityProof);
                        if (identityProof != null) {
                            userDetails.getMasterApplicant().setIdentityProofPath(identityProof);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:idProof",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id proof File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:idProof",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (filepart2 != null && filepart2.getSize() != ZERO) {
                    if (filepart2.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Address Proof ********************************");
                        String correspondence = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart2, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Corresspondence address:{0}", correspondence);
                        if (correspondence != null) {
                            userDetails.getMasterApplicant().setCorrespondenceAddressPath(correspondence);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:adrsvalidity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Permanent Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:adrsvalidity",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (userDetails.getMasterApplicant().getPermanentAddress()) {
                    if (filepart3 != null && filepart3.getSize() != ZERO) {
                        if (filepart3.getSize() <= TWO_MB) {
                            LOGGER.info("************************File Uploading Permenent Address ********************************");
                            String permenent = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart3, VALIDITY_PERMENENT, PERMENENT_NAME);
                            LOGGER.log(Level.INFO, "File name for Permenet Address :{0}", permenent);
                            if (permenent != null) {
                                userDetails.getMasterApplicant().setPermanentAddressPath(permenent);
                                status = true;
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("frm_create_user:address2_validity",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Idenity Address File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:address2_validity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }

                    }
                }
                if (filepart4 != null && filepart4.getSize() != ZERO) {
                    if (filepart4.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading  DOCUMENTATRY EVIDENCE FileAddress Proof ********************************");
                        String documentary_evidence = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart4, DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Documentary Evidence Address Proof:{0}", documentary_evidence);
                        if (documentary_evidence != null) {
                            userDetails.getMasterApplicant().setDocumentaryEvidencePath(documentary_evidence);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:evidence_box",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Documentary Evidence Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:evidence_box",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (filepart5 != null && filepart5.getSize() != ZERO) {
                    if (filepart5.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading PAN COPY ********************************");
                        String panCopy = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart5, IDENTITY_PROOF, PAN_SUBMITTED_NAME);
                        LOGGER.log(Level.INFO, "File name for PAN :{0}", panCopy);
                        if (panCopy != null) {
                            userDetails.getMasterApplicant().setIdentityProofPath(panCopy);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:pan_file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "PAN Copy Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:pan_file",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (status) {
                    LOGGER.info("************************Updating data ********************************");
                    updateUserBAO.updateUserDetails(userDetails);
                    //status = true;
                    logMessage.append("User details with registration id ").append(userDetails.getMasterApplicant().getRegistrationId())
                            .append(" updated.");
                }
            }
        }
        LOGGER.info(logMessage.toString());
    }

    public void doActionActivateUser(ActionEvent event) {
        if (isValidationFailed(userDetails)) {
            String msg = EMPTY_STRING;
            if (userDetails.getUserType().equals(YES)) {
                msg = "Please enter the SEBI NO. and change the document status to received state if not";
            } else if (userDetails.getUserType().equals(NO)) {
                msg = "Please change the documnet status to received state if not";
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("frm_create_user", new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            fc.renderResponse();
            redirectPage = EMPTY_STRING;
        } else {
            userDetails.getMasterApplicant().setStatus(EnumStatus.ACTIVE.getValue());
            userDetails.getMasterApplicant().setUserActivationDate(new Date());
            doActionUpdateUser(event);
            updateUserBAO.deleteTempUser(userDetails);
            redirectPage = REDIRECT_NEW_USER_LIST;
        }
    }

    public void doActionCreateUser(ActionEvent event) {
        userExists = userProfileBAO.isEmailExists(userDetails.getMasterApplicant().getEmail(), userDetails.getMasterApplicant().getRegistrationId());
        StringBuilder logMessage = new StringBuilder();
        int ticket = 0;
        boolean allocateKit = false;
        if (userExists) {
            FacesContext.getCurrentInstance().addMessage(EMAIL_FIELD,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_MESSAGE, EMAIL_MESSAGE));
            status = false;
            logMessage.append("New user creation failed - user already exists.");
        } else if (!userDetails.getMasterApplicant().getPan().equalsIgnoreCase("")
                && userProfileBAO.isPanNoExists(userDetails.getUserType(), userDetails.getMasterApplicant().getPan(), userDetails.getMasterApplicant().getRegistrationId())) {
            FacesContext.getCurrentInstance().addMessage(PANNO_FIELD,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, PANNO_MESSAGE, PANNO_MESSAGE));
            status = false;
            logMessage.append("New user creation failed - PAN number already registered.");
        } else {
            String fileName = null;
            if (userDetails.getUserType().equalsIgnoreCase(YES)) {
                status = true;
                if (userDetails.getMasterApplicant().getAccountNumber().equalsIgnoreCase(userDetails.getRe_accountNumber())) {
                    if (filepart != null && filepart.getSize() != ZERO) {
                        if (filepart.getSize() <= TWO_MB) {
                            fileName = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, SEBI_FILE_PATH, SEBI_CERTIFICATE);

                            if (fileName != null) {
                                status = true;
                                userDetails.getQualificationTb().setSebiCertificatePath(fileName);
                                int newUserId = createUserBAO.saveNewUserDetails(userDetails);
                                logMessage.append("New user created with custumer id ").append(newUserId);
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("frm_create_user:file",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }
                    } else if (status) {
                        int newUserId = createUserBAO.saveNewUserDetails(userDetails);
                        logMessage.append("New user created with custumer id ").append(newUserId);
                    }
                } else {
                    status = false;
                    FacesContext.getCurrentInstance().
                            addMessage("frm_create_user:re-acno",
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number miss Match", null));
                }
            } else {
                status = true;
                if (userDetails.getUserType().equalsIgnoreCase(NO)
                        && userDetails.getMasterApplicant().getDocumentaryEvedence() != null) {
                    userDetails.getMasterApplicant().setDocumentaryEvedenceOther(
                            userDetails.getMasterApplicant().getDocumentaryEvedence().equalsIgnoreCase("Others"));
                    userDetails.getMasterApplicant().setDocumentaryEvedence(
                            userDetails.getMasterApplicant().getDocumentaryEvedenceOther()
                                    ? otherEvidence : userDetails.getMasterApplicant().getDocumentaryEvedence());
                }

                if (filepart != null && filepart.getSize() != ZERO) {
                    if (filepart.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Identity Proof ********************************");
                        String identity = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, IDENTITY_PROOF, IDENTITY_NAME);
                        LOGGER.info("File name for Identity Proof :" + identity);
                        if (identity != null) {
                            userDetails.getMasterApplicant().setIdentityProofPath(identity);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:idProof",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id proof File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:idProof",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (filepart2 != null && filepart2.getSize() != ZERO) {
                    if (filepart2.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Address Proof ********************************");
                        String fileName2 = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Correspondence Address Proof:{0}", fileName2);
                        if (fileName2 != null) {
                            userDetails.getMasterApplicant().setCorrespondenceAddressPath(fileName2);
                            updateUserBAO.updateUserDetails(userDetails);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:adrsvalidity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Permanent Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:adrsvalidity",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }

                if (userDetails.getMasterApplicant().getPermanentAddress()) {
                    if (filepart3 != null && filepart3.getSize() != ZERO) {
                        if (filepart3.getSize() <= TWO_MB) {
                            LOGGER.info("************************File Uploading Permenent address proof ********************************");
                            String permenent = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, VALIDITY_PERMENENT, PERMENENT_NAME);
                            LOGGER.log(Level.INFO, "File name for Permenent Address :{0}", permenent);
                            if (permenent != null) {
                                userDetails.getMasterApplicant().setPermanentAddressPath(permenent);
                                status = true;
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("frm_create_user:address2_validity",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Idenity Address File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:address2_validity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }
                    }
                }
                if (filepart4 != null && filepart4.getSize() != ZERO) {
                    if (filepart4.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading  DOCUMENTATRY EVIDENCE FileAddress Proof ********************************");
                        String documentary_evidence = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Documentary Evidence Address Proof:{0}", documentary_evidence);
                        if (documentary_evidence != null) {
                            userDetails.getMasterApplicant().setDocumentaryEvidencePath(documentary_evidence);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:evidence_box",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Documentary Evidence Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:evidence_box",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }
                }
                if (filepart5 != null && filepart5.getSize() != ZERO) {
                    if (filepart5.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading PAN COPY ********************************");
                        String panCopy = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart5, IDENTITY_PROOF, PAN_SUBMITTED_NAME);
                        LOGGER.log(Level.INFO, "File name for PAN :{0}", panCopy);
                        if (panCopy != null) {
                            userDetails.getMasterApplicant().setIdentityProofPath(panCopy);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:pan_file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "PAN Copy Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:pan_file",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }
                }
                if (status && !userDetails.getMasterApplicant().getAdvisor()
                        && userDetails.getMasterApplicant().getKitNumber() == null) {
                    /**
                     * allocating registration kit for those investors who are
                     * registered to MMF without client registration kit number.
                     * investors cannot be made active users unless new kits are
                     * alloted by admin.
                     */
                    ticket = userProfileBAO.getKitAllocationData();
                    if (ticket == 0) {
                        FacesContext.getCurrentInstance().addMessage("frm_create_user", new FacesMessage(REG_KIT_UNAVAILABLE));
                        status = false;
                        logMessage.append("New user creation failed - insufficient client registration kit");
                    } else {
                        allocateKit = true;
                        userDetails.getMasterApplicant().setKitNumber(ticket);
                    }
                }

                if (status) {
                    LOGGER.info("************************Saving User Data ********************************");
                    int newUserId = createUserBAO.saveNewUserDetails(userDetails);
                    status = newUserId > 0;
                    logMessage.append("New user created with custumer id ").append(newUserId);
                }
            }
            this.setActiveStatus(true);

        }
        LOGGER.info(logMessage.toString());
    }

    public String createOrUpdateUser() {    // action on update or create
        String redirectTo;
        if (status) {
            Map<String, Object> storedValues = new HashMap<String, Object>();
            storedValues.put("userTypeSelected", this.userTypeSelected);
            storedValues.put(SEARCH_TEXT, this.searchText);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
            redirectTo = REDIRECT_NEW_USER_LIST;
        } else {
            redirectTo = EMPTY_STRING;
        }
        return redirectTo;
    }

    public String backToList() {    // click Cancel
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put("userTypeSelected", this.userTypeSelected);
        storedValues.put(SEARCH_TEXT, this.searchText);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
        return REDIRECT_NEW_USER_LIST;
    }

    public String activateRedirect() {
        String redirectTo = EMPTY_STRING;
        if (isUserAbleToActivate()) {
            redirectTo = REDIRECT_NEW_USER_LIST;
        } else {
            Map<String, Object> storedValues = new HashMap<String, Object>();
            storedValues.put("denyActivate", true);
            if (userDetails.getDocStatus().toLowerCase().equalsIgnoreCase(DOCUMENTS_PENDING)) {
                storedValues.put("docPending", true);
            }
            storedValues.put("selectedUser", userDetails);
            storedValues.put("userTypeSelected", userTypeSelected);
            storedValues.put(SEARCH_TEXT, searchText);
            if (userDetails.getUserType().toLowerCase().equalsIgnoreCase(YES)
                    && StringUtils.isEmpty(userDetails.getMasterApplicant().getSebiRegno())) {
                storedValues.put("sebiNotEntered", true);
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
            redirectTo = REDIRECT_CREATE_USER_PAGE;
        }
        return redirectTo;

    }

    private Boolean isValidationFailed(UserDetailsVO userDetails) {
        if (userDetails.getUserType().equals(YES)) {
            return !isActivateAccount() || StringUtils.isEmpty(userDetails.getMasterApplicant().getSebiRegno());
        } else if (userDetails.getUserType().equals(NO)) {
            return !isActivateAccount();
        } else {
            return Boolean.TRUE;
        }
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
    private CustomerPortfolioVO customerportfolio;

    public CustomerPortfolioVO getCustomerportfolio() {
        return customerportfolio;
    }

    public void setCustomerportfolio(CustomerPortfolioVO customerportfolio) {
        this.customerportfolio = customerportfolio;
    }

    public void onChnageCountryHome() {
        if (!userDetails.getMasterApplicant().getAddressCountry().equalsIgnoreCase(IN)) {
            homeStateList = new HashMap<String, String>();
            cityList = new HashMap<String, String>();
            if (!firstAddress) {
                userDetails.getMasterApplicant().setAddressState(EMPTY_STRING);
                userDetails.getMasterApplicant().setAddressCity(EMPTY_STRING);
            }
        } else {
            this.setHomeStateList(LookupDataLoader.getStateListLookup());
            this.setCityList(LookupDataLoader.getCityListLookup());
            userDetails.getMasterApplicant().setAddressCountry(IN);
            onChangeState(userDetails.getMasterApplicant().getAddressState(), 1);
        }
        firstAddress = false;
    }

    public void onChnageCountryBank() {

        if (!userDetails.getMasterApplicant().getBankCountry().equalsIgnoreCase(IN)) {
            stateListBank2 = new HashMap<String, String>();
            cityListBank2 = new HashMap<String, String>();
        } else {
            this.setStateListBank2(LookupDataLoader.getStateListLookup());
//        this.setStateListBank2(LookupDataLoader.getStatecitymap());
            this.setCityListBank2(LookupDataLoader.getCityListLookup());
            userDetails.getMasterApplicant().setBankCountry(IN);
            onChangeState(userDetails.getMasterApplicant().getBankState(), 3);
//            onBankSelect(userDetails.getMasterApplicant().getBankName(), userDetails.getMasterApplicant().getBankState(), userDetails.getMasterApplicant().getBankCity(), 1);
        }

    }

    public void onChnageCountryHome2() {
        if (!userDetails.getMasterApplicant().getAddress2Country().equalsIgnoreCase(IN)) {
            homeStateList2 = new HashMap<String, String>();
            officeCityList = new HashMap<String, String>();
            if (!firstAddress2) {
                userDetails.getMasterApplicant().setAddress2State(EMPTY_STRING);
                userDetails.getMasterApplicant().setAddress2City(EMPTY_STRING);
            }
        } else {
            this.setHomeStateList2(LookupDataLoader.getStateListLookup());
            this.setOfficeCityList(LookupDataLoader.getCityListLookup());
            userDetails.getMasterApplicant().setAddress2Country(IN);
            onChangeState(userDetails.getMasterApplicant().getAddress2State(), 2);
        }
        firstAddress2 = false;
    }

    public void onChnageMinorCountry() {
        if (!userDetails.getNomineeDetails().getCountryNomineeMinor().equalsIgnoreCase(IN)) {
            minorStateList = new HashMap<String, String>();
            cityListNomineeMinor = new HashMap<String, String>();
            if (!nomineAddressMinor) {
                userDetails.getNomineeDetails().setStateNomineeMinor(EMPTY_STRING);
                userDetails.getNomineeDetails().setPlaceNomineeMinor(EMPTY_STRING);
            }
        } else {
            this.setMinorStateList(LookupDataLoader.getStateListLookup());
            this.setCityListNomineeMinor(LookupDataLoader.getCityListLookup());
            userDetails.getNomineeDetails().setCountryNomineeMinor(IN);
            onChangeState(userDetails.getNomineeDetails().getStateNomineeMinor(), 5);
        }
        nomineAddressMinor = false;
    }

    public void onChnageCountryOffice() {
        officeCityList = userProfileBAO.getCityList(userDetails.getOfficeCountry());
        userDetails.setOfficeCity(EMPTY_STRING);
    }

    public void processRadioValueChange() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        hardDelete = Boolean.parseBoolean(params.get(DEL_OPTION));
    }

    public void onDeleteUser() {
        try {
            if (userProfileBAO.deleteUser(userDetails, isHardDelete())) {
                redirectPage = REDIRECT_NEW_USER_LIST;
            } else {
                FacesContext.getCurrentInstance().addMessage("frm_create_user", new FacesMessage("An error has been occured in the operation.Please try later."));
                redirectPage = EMPTY_STRING;
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("frm_create_user", new FacesMessage("An error has been occured in the operation.Please try later."));
            redirectPage = EMPTY_STRING;
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public void onTeminateContract() {
        isContract = userProfileBAO.terminateContract(userDetails);
        if (isContract) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contract Termination Initialized Successfully."));
            redirectPage = REDIRECT_NEW_USER_LIST;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is no contract to terminate for this user!!"));
            redirectPage = EMPTY_STRING;
        }
        System.out.println("onTeminateContract invoked with relation id");

    }

    public Boolean isNOTMailVerifiedUser() {
        return userDetails.getUserStatus() == EnumStatus.MAIL_NOT_VERIFIED.getValue();
    }

    public UserDetailsVO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsVO userDetails) {
        this.userDetails = userDetails;
    }

    public Map<String, String> getCountryList() {
        return countryList;
    }

    public void setCountryList(Map<String, String> countryList) {
        this.countryList = countryList;
    }

    public Map<String, String> getYearList() {
        return yearList;
    }

    public void setYearList(Map<String, String> yearList) {
        this.yearList = yearList;
    }

    public Map<String, Integer> getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Map<String, Integer> userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getredirectPage() {
        return redirectPage;
    }

    public void setredirectPage(String redirectPage) {
        this.redirectPage = redirectPage;
    }

    private boolean isUserAbleToActivate() {
        boolean status = true;
        if (userDetails.getDocStatus().toLowerCase().equalsIgnoreCase(DOCUMENTS_PENDING)) {
            status = false;
            return status;
        } else if (userDetails.getUserType().toLowerCase().equalsIgnoreCase(YES)) {
            status = StringUtils.hasText(userDetails.getMasterApplicant().getSebiRegno());
        }
        return status;
    }

    public Map<String, String> getOfficeCityList() {
        return officeCityList;
    }

    public void setOfficeCityList(Map<String, String> officeCityList) {
        this.officeCityList = officeCityList;
    }

    public Boolean isHardDelete() {
        return hardDelete;
    }

    public void setHardDelete(Boolean hardDelete) {
        this.hardDelete = hardDelete;
    }

    //map for filling selectbox create/edit user page
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

    public HashMap<String, Integer> getGenderData() {
        return LookupDataLoader.getGenderDataMap();
    }

    public HashMap<String, Integer> getMaritalstatus() {
        return LookupDataLoader.getMaritalstatusMap();
    }

    public boolean isHomeStateListEditable2() {
        return homeStateList2.isEmpty();
    }

    public Map<String, String> getNomineePoi() {
        return LookupDataLoader.getNomineePoiMap();
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

    public boolean isNomineeStateListEditable() {
        return nomineeStateList.isEmpty();
    }

    public boolean isMinorStateListEditable() {
        return minorStateList.isEmpty();
    }

    public boolean isCityListNomineeMinorEditable() {
        return cityListNomineeMinor.isEmpty();
    }

    public void onChnageNomineeCountry() {
        if (!userDetails.getNomineeDetails().getCountryNominee().equalsIgnoreCase(IN)) {
            nomineeStateList = new HashMap<String, String>();
            cityListNominee = new HashMap<String, String>();
            if (!nomineAddress) {
                userDetails.getNomineeDetails().setStateNominee(EMPTY_STRING);
                userDetails.getNomineeDetails().setPlaceNominee(EMPTY_STRING);

            }
        } else {
            this.setNomineeStateList(LookupDataLoader.getStateListLookup());
            this.setCityListNominee(LookupDataLoader.getCityListLookup());
            userDetails.getNomineeDetails().setCountryNominee(IN);
            onChangeState(userDetails.getNomineeDetails().getStateNominee(), 4);
        }
        nomineAddress = false;
    }

    public boolean isCityListNomineeEditable() {
        return cityListNominee.isEmpty();
    }

    public String getOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(String otherEvidence) {
        this.otherEvidence = otherEvidence;
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

    public Map<String, String> getCityListBank2() {
        return cityListBank2;
    }

    public void setCityListBank2(Map<String, String> cityListBank2) {
        this.cityListBank2 = cityListBank2;
    }

    public Part getFilepart() {
        return filepart;
    }

    public void setFilepart(Part filepart) {
        this.filepart = filepart;
    }

    public boolean isActivateAccount() {
        return activateAccount;
    }

    public void setActivateAccount(boolean activateAccount) {
        this.activateAccount = activateAccount;
    }

    public void checkForAcoountActivateListener() {

        LOGGER.info("Checking for user Activation");
        if (userDetails.getMasterApplicant().getAdvisor()) {
            if (userDetails.isOnlineDetailsSubmitted() && userDetails.isSebiCertificateValidated()
                    && userDetails.isVerificationCcompleted() && userDetails.isAccountActivated()) {
                this.setActivateAccount(true);
            } else {
                this.setActivateAccount(false);
            }
        } else {
            LOGGER.info("-----------------------------------------------------------------------------------------------");

            LOGGER.log(Level.INFO, "isOnlineDetailsSubmitted flag :[{0}]", userDetails.isOnlineDetailsSubmitted());
            LOGGER.log(Level.INFO, "isForm_couriered_Client flag :[{0}]", userDetails.isForm_couriered_Client());
            LOGGER.log(Level.INFO, "isForm_received_client flag :[{0}]", userDetails.isForm_received_client());
            LOGGER.log(Level.INFO, "isForm_Validated flag :[{0}]", userDetails.isForm_Validated());
            LOGGER.log(Level.INFO, "isAccepted flag :[{0}]", userDetails.isAccepted());
            LOGGER.log(Level.INFO, "isAccountActivated flag :[{0}]", userDetails.isAccountActivated());
            LOGGER.log(Level.INFO, "isuCC_created flag :[{0}]", userDetails.isuCC_created());
            LOGGER.log(Level.INFO, "isRejected flag :[{0}]", userDetails.isRejected());
            LOGGER.log(Level.INFO, "isRejection_Resolved flag :[{0}]", userDetails.isRejection_Resolved());
            LOGGER.info("-----------------------------------------------------------------------------------------------");
            boolean status = userDetails.isOnlineDetailsSubmitted() && userDetails.isForm_couriered_Client()
                    && userDetails.isForm_received_client() && userDetails.isForm_Validated()
                    && (userDetails.isAccepted() || userDetails.isRejection_Resolved()) && userDetails.isAccountActivated() && userDetails.isuCC_created();
            LOGGER.log(Level.INFO, "Status flag :[{0}]", status);
            boolean activate = userDetails.isRejected() ? (userDetails.isRejection_Resolved() && status) : status;
            LOGGER.log(Level.INFO, "Activate flag :[{0}]", activate);
            if (activate) {

                this.setActivateAccount(true);
            } else {
                this.setActivateAccount(false);
            }
        }
        LOGGER.info("Checking for user Activation Ended");
    }

    public void downloadFileListener(int id) {
        LOGGER.log(Level.INFO, "************************Downloading File no [{0}]", id);
        String filePath = null;
        if (id == 1) {
            filePath = userDetails.getQualificationTb().getSebiCertificatePath();
        } else if (id == 2) {
            filePath = userDetails.getMasterApplicant().getIdentityProofPath();
        } else if (id == 3) {
            filePath = userDetails.getMasterApplicant().getCorrespondenceAddressPath();
        } else if (id == 4) {
            filePath = userDetails.getMasterApplicant().getPermanentAddressPath();
        } else if (id == 5) {
            filePath = userDetails.getMasterApplicant().getDocumentaryEvidencePath();
        } else if (id == 6) {

            LOGGER.log(Level.INFO, "************************Downloading PDF File**********************");
            List<FailedMailDetailsVO> failedMailDetails = userProfileBAO.getFailedMailList("MasterApplicantTb", userDetails.getMasterApplicant().getEmail(), false);
            filePath = preparePdfDownloadData(failedMailDetails, filePath);
        }
        LOGGER.log(Level.INFO, "************************Downloading File Location [{0}]", filePath);
        boolean statusDownload = FileUploadUtil.downloadFile(filePath);
        if (!statusDownload) {
            FacesContext.getCurrentInstance().
                    addMessage("frm_create_user:download",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "No File To Download", null));
        }

    }

    private String preparePdfDownloadData(List<FailedMailDetailsVO> failedMailDetails, String filePath) {
        if (failedMailDetails != null) {
            if (!failedMailDetails.isEmpty()) {
                RegistrationPdfDataPreparation pdfPreparation = new RegistrationPdfDataPreparation();
                for (FailedMailDetailsVO mailDetailsForPdf : failedMailDetails) {
                    RegistrationDataProcessingVo pdfDataPreparation
                            = mailDetailsForPdf.getRegistrationDataProcessing();
                    MandateVo mandateVo = registrationBAO.createMandateFormData(pdfDataPreparation.
                            getMandateVo(), pdfDataPreparation.getRegistrationVo().getRegId());
                    pdfDataPreparation.setMandateVo(mandateVo);
                    if (!Boolean.valueOf(mailDetailsForPdf.getUserType())) {
                        RegistrationVo registrationVo = registrationBAO.createRegistrationPdfData(pdfDataPreparation.getRegistrationVo());
                        pdfDataPreparation.setRegistrationVo(registrationVo);
                        pdfPreparation.createRegistrationPdfData(pdfDataPreparation, true);
                    }
                    StringBuffer path = new StringBuffer();
                    path = path.append(LookupDataLoader.getResourcePath())
                            .append(FILE_PATH).append(userDetails.getMasterApplicant().getRegistrationId()).append(DOT).append("pdf");
                    filePath = path.toString();
                }
            } else {
                LOGGER.info(" Mail list without pdf documents to be regenerated is empty .Nothing to process");
            }
        }
        return filePath;
    }

    public Map<Integer, String> getBankList() {
        return LookupDataLoader.getBankListLookup();
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

    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
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
        // Id == 3 only for bank details
//        if (id == 3) {
//            cityListBank2 = ConversionMethods.getCityForBankState(state);
//        }
    }

    public Map<String, String> getDependentUsed() {
        return LookupDataLoader.getDependentDataMap();
    }

    public boolean saveUserDetails(StringBuilder logMessage) {
        if (!userDetails.getMasterApplicant().getPan().equalsIgnoreCase("")
                && userProfileBAO.isPanNoExists(userDetails.getUserType(), userDetails.getMasterApplicant().getPan(), userDetails.getMasterApplicant().getRegistrationId())) {
            FacesContext.getCurrentInstance().addMessage(PANNO_FIELD,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, PANNO_MESSAGE, PANNO_MESSAGE));
            status = false;
            logMessage.append("New user creation failed - PAN number already registered.");
        } else {
            String fileName = null;
            if (userDetails.getUserType().equalsIgnoreCase(YES)) {
                if (userDetails.getMasterApplicant().getAccountNumber().equalsIgnoreCase(userDetails.getRe_accountNumber())) {
                    if (filepart.getSize() != ZERO) {
                        if (filepart.getSize() <= TWO_MB) {
                            fileName = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, SEBI_FILE_PATH, SEBI_CERTIFICATE);

                            if (fileName != null) {
                                status = true;
                                userDetails.getQualificationTb().setSebiCertificatePath(fileName);
                                int newUserId = createUserBAO.saveNewUserDetails(userDetails);
                                logMessage.append("New user created with custumer id ").append(newUserId);
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("frm_create_user:file",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }
                    } else {
                        status = true;
                        int newUserId = createUserBAO.saveNewUserDetails(userDetails);
                        logMessage.append("New user created with custumer id ").append(newUserId);
                    }
                } else {
                    status = false;
                    FacesContext.getCurrentInstance().
                            addMessage("frm_create_user:re-acno",
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account Number miss Match", null));
                }
            } else {
                if (userDetails.getUserType().equalsIgnoreCase(NO)
                        && userDetails.getMasterApplicant().getDocumentaryEvedence() != null) {
                    userDetails.getMasterApplicant().setDocumentaryEvedenceOther(
                            userDetails.getMasterApplicant().getDocumentaryEvedence().equalsIgnoreCase("Others"));
                    userDetails.getMasterApplicant().setDocumentaryEvedence(
                            userDetails.getMasterApplicant().getDocumentaryEvedenceOther()
                                    ? otherEvidence : userDetails.getMasterApplicant().getDocumentaryEvedence());
                }

                if (filepart != null && filepart.getSize() != ZERO) {
                    if (filepart.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Identity Proof ********************************");
                        String identity = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, IDENTITY_PROOF, IDENTITY_NAME);
                        LOGGER.info("File name for Identity Proof :" + identity);
                        if (identity != null) {
                            userDetails.getMasterApplicant().setIdentityProofPath(identity);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:idProof",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id proof File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:idProof",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }
                if (filepart2 != null && filepart2.getSize() != ZERO) {
                    if (filepart2.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading Address Proof ********************************");
                        String fileName2 = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Correspondence Address Proof:{0}", fileName2);
                        if (fileName2 != null) {
                            userDetails.getMasterApplicant().setCorrespondenceAddressPath(fileName2);
                            // updateUserBAO.updateUserDetails(userDetails);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:adrsvalidity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Permanent Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:adrsvalidity",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }

                }

                if (userDetails.getMasterApplicant().getPermanentAddress()) {
                    if (filepart3 != null && filepart3.getSize() != ZERO) {
                        if (filepart3.getSize() <= TWO_MB) {
                            LOGGER.info("************************File Uploading Permenent address proof ********************************");
                            String permenent = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, VALIDITY_PERMENENT, PERMENENT_NAME);
                            LOGGER.log(Level.INFO, "File name for Permenent Address :{0}", permenent);
                            if (permenent != null) {
                                userDetails.getMasterApplicant().setPermanentAddressPath(permenent);
                                status = true;
                            } else {
                                status = false;
                                FacesContext.getCurrentInstance().
                                        addMessage("frm_create_user:address2_validity",
                                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Idenity Address File Upload Failed", null));
                            }
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:address2_validity",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                        }
                    }
                }
                if (filepart4 != null && filepart4.getSize() != ZERO) {
                    if (filepart4.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading  DOCUMENTATRY EVIDENCE FileAddress Proof ********************************");
                        String documentary_evidence = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart, DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME);
                        LOGGER.log(Level.INFO, "File name for Documentary Evidence Address Proof:{0}", documentary_evidence);
                        if (documentary_evidence != null) {
                            userDetails.getMasterApplicant().setDocumentaryEvidencePath(documentary_evidence);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:evidence_box",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Documentary Evidence Address File Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:evidence_box",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }
                }
                if (filepart5 != null && filepart5.getSize() != ZERO) {
                    if (filepart5.getSize() <= TWO_MB) {
                        LOGGER.info("************************File Uploading PAN COPY ********************************");
                        String panCopy = FileUploadUtil.UploadUserFile(userDetails.getMasterApplicant().getRegistrationId(), filepart5, IDENTITY_PROOF, PAN_SUBMITTED_NAME);
                        LOGGER.log(Level.INFO, "File name for PAN :{0}", panCopy);
                        if (panCopy != null) {
                            userDetails.getMasterApplicant().setIdentityProofPath(panCopy);
                            status = true;
                        } else {
                            status = false;
                            FacesContext.getCurrentInstance().
                                    addMessage("frm_create_user:pan_file",
                                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "PAN Copy Upload Failed", null));
                        }
                    } else {
                        status = false;
                        FacesContext.getCurrentInstance().
                                addMessage("frm_create_user:pan_file",
                                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum file size allowable 2MB", null));
                    }
                }
                if (status) {
                    LOGGER.info("************************Saving User Data ********************************");
                    int newUserId = createUserBAO.saveNewUserDetails(userDetails);
                    status = newUserId > 0;
                    logMessage.append("New user created with custumer id ").append(newUserId);
                    this.setActiveStatus(true);
                }
            }
        }
        return status;
    }

    public String getKitnumber() {
        return kitnumber;
    }

    public void setKitnumber(String kitnumber) {
        this.kitnumber = kitnumber;
    }

    public void onTransferToBackoffice(UserDetailsVO userdetails) {
        ClientResponse response = null;
        try {
            BoRegDataServiceVo temp = userProfileBAO.prepareForBackoffceTransfer(userdetails);
            String json = new Gson().toJson(temp);
            WebResource webResource = RestClient.getClient().resource(PropertiesLoader.getPropertiesValue(SERVICE_SAVE_REGISTRATION));
            response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
            redirectPage = REDIRECT_NEW_USER_LIST;
            if (response.getStatus() != 200) {
                redirectPage = EMPTY_STRING;
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
        } catch (RuntimeException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        }
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
     if(userDetails.getMasterApplicant().getAdvisor()) {
     userDetails.getMasterApplicant().setBankName(bankVo.getBank());
     userDetails.getMasterApplicant().setIfcsNumber(bankVo.getIfsc());
     userDetails.getMasterApplicant().setBankBuilding(bankVo.getAddress());
     userDetails.getMasterApplicant().setBankCountry(IN);
     userDetails.getMasterApplicant().setBankState(bankVo.getState());
     userDetails.getMasterApplicant().setBankCity(bankVo.getCity());
     userDetails.getMasterApplicant().setMicrNumber(bankVo.getMicr());
     userDetails.getMasterApplicant().setBranch(bankVo.getBranch());
     cityListBank2 = ConversionMethods.getCityForBankState(userDetails.getMasterApplicant().getBankState());
     onBankSelect(userDetails.getMasterApplicant().getBankName(), userDetails.getMasterApplicant().getBankState(), userDetails.getMasterApplicant().getBankCity(), 1);
     } else {
     userDetails.setTradingBank(bankVo.getBank());
     userDetails.setTradingIFSCNo(bankVo.getIfsc());
     userDetails.getMasterApplicant().setBankBuilding(bankVo.getAddress());
     userDetails.getMasterApplicant().setBankCountry(IN);
     userDetails.getMasterApplicant().setBankState(bankVo.getState());
     userDetails.getMasterApplicant().setBankCity(bankVo.getCity());
     userDetails.getMasterApplicant().setMicrNumber(bankVo.getMicr());
     userDetails.getMasterApplicant().setBranch(bankVo.getBranch());
     cityListBank2 = ConversionMethods.getCityForBankState(userDetails.getMasterApplicant().getBankState());
     onBankSelect(userDetails.getTradingBank(), userDetails.getMasterApplicant().getBankState(), userDetails.getMasterApplicant().getBankCity(), 1);
     }
     }
     }
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
    
     //variable i used to identify calling from bank change(1 for usual,2 for inv bank change, 3 for adv bnk chng)
     public void onBankSelect(String bank, String state, String city,int i){
     List<String> branches = new ArrayList<String>();
     if(i == 2 || i == 3) {
     userDetails.getMasterApplicant().setBranch(EMPTY_STRING);
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
     if(bankVo.getBranch().equals(userDetails.getMasterApplicant().getBranch())) {
     if(userDetails.getMasterApplicant().getAdvisor()) {
     userDetails.getMasterApplicant().setIfcsNumber(bankVo.getIfsc());
     userDetails.getMasterApplicant().setBankBuilding(bankVo.getAddress());
     userDetails.getMasterApplicant().setBankCity(bankVo.getCity());
     }
     else {
     userDetails.setTradingIFSCNo(bankVo.getIfsc());
     userDetails.getMasterApplicant().setBankBuilding(bankVo.getAddress());
     userDetails.getMasterApplicant().setBankCity(bankVo.getCity());
     }
     }
     }
     }*/
    public Map<String, Integer> getAddresstypeData() {
        return addresstypeData;
    }

    public void setAddresstypeData(Map<String, Integer> addresstypeData) {
        this.addresstypeData = addresstypeData;
    }

    public boolean isIsContract() {
        return isContract;
    }

    public void setIsContract(boolean isContract) {
        this.isContract = isContract;
    }

    public boolean isEnableterminationButton() {
        return enableterminationButton;
    }

    public void setEnableterminationButton(boolean enableterminationButton) {
        this.enableterminationButton = enableterminationButton;
    }

    public void autoGenerateBankDetails(String ifsc_code) {
        ArrayList<String> address = new ArrayList<String>();
        BankVo bankVo = new BankVo();
        bankVo = createUserBAO.getbankDetails(ifsc_code);
        userDetails.getMasterApplicant().setBankName(bankVo.getBank());
        userDetails.getMasterApplicant().setIfcsNumber(bankVo.getIfsc());
        address = getBankAddress(bankVo.getAddress());
        userDetails.getMasterApplicant().setBankBuilding(address.get(0));
        userDetails.getMasterApplicant().setBankStreet(address.get(1));
        userDetails.getMasterApplicant().setBankCountry(IN);
        userDetails.getMasterApplicant().setBankState(bankVo.getState());
        userDetails.getMasterApplicant().setBankCity(bankVo.getCity());
        userDetails.getMasterApplicant().setMicrNumber(bankVo.getMicr());
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

}
