/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IFpMainCalculationBAO;
import com.gtl.mmf.bao.IRiskProfileBAO;
import com.gtl.mmf.bao.IUserLoginBAO;
import com.gtl.mmf.common.EnumAdvLoginStatus;
import com.gtl.mmf.common.EnumCustLoginStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.dao.IUserLoginDAO;
import com.gtl.mmf.dao.IUserProfileDAO;
import com.gtl.mmf.entity.AdminuserTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.TempInv;
import com.gtl.mmf.entity.TempRegistrationTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.SecurityUtility;
import com.gtl.mmf.service.vo.LoginVO;
import java.util.Date;

/**
 *
 * @author 07958
 */
public class UserLoginBAOImpl implements IUserLoginBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.UserLoginBAOImpl");
    @Autowired
    private IUserLoginDAO userLoginDAO;

    @Autowired
    private IUserProfileDAO userProfileDAO;
    
    @Autowired
	private IRiskProfileBAO riskProfileBAO;
	@Autowired
	private IFpMainCalculationBAO fpBAO;
	private boolean riskProfileNotCompleted;
	private boolean fpNotCompleted;

    private LoginVO advisorLogin(LoginVO login) {
        List advisorList = userLoginDAO.advisorLogin(login.getUsername(), this.encryptPassword(login.getPassword()));
        if (advisorList.isEmpty()) {
            // login failed
            LOGGER.log(Level.INFO, "Advisor login failed with username {0}", login.getUsername());
        } else {
            MasterAdvisorTb masterAdvisor = (MasterAdvisorTb) advisorList.get(ZERO);
            LOGGER.log(Level.INFO, "Advisor login success with username {0}", login.getUsername());
            if (masterAdvisor.getInitLogin() != ZERO) {
                login.setRedirectPage(EnumAdvLoginStatus.fromInt(masterAdvisor.getInitLogin()).toString());
            } else {
                login.setRedirectPage(EnumAdvLoginStatus.fromInt(masterAdvisor.getInitLogin()).toString());
            }
            login.setAccessToken(masterAdvisor.getMasterApplicantTb().getLinkedinPassword() != null
                    ? masterAdvisor.getMasterApplicantTb().getLinkedinPassword() : EMPTY_STRING);
            login.setLinkedInId(masterAdvisor.getMasterApplicantTb().getLinkedinMemberId() != null
                    ? masterAdvisor.getMasterApplicantTb().getLinkedinMemberId() : EMPTY_STRING);
            login.setLinkedInConnected(masterAdvisor.getMasterApplicantTb().getLinkedInConnected());
            login.setMasterAdvisor(masterAdvisor);
            login.setInitStatus(masterAdvisor.getInitLogin());
        }
        LOGGER.log(Level.INFO, "Advisor login redirect to {0}", login.getRedirectPage());
        return login;
    }

    private LoginVO adminLogin(LoginVO login) {
        StringBuilder logMessage = new StringBuilder("Admin login with username ");
        logMessage.append(login.getUsername()).append(" and password").append(this.encryptPassword(login.getPassword()));
        LOGGER.info(logMessage.toString());
        AdminuserTb admin = new AdminuserTb();
        admin.setUsername(login.getUsername());
        admin.setPassword(this.encryptPassword(login.getPassword()));
        admin.setUserType(login.getUsertype());
        AdminuserTb adminLogin = userLoginDAO.adminLogin(admin);
        if (adminLogin == null) {
            login.setRedirectPage(FAILURE);
        } else {
            login.setRedirectPage(ADMIN_LOGIN_REDIRECT);
            login.setAdminuser(adminLogin);
        }
        return login;
    }

    private List<Integer> getStatusBeforeSignContract() {
        List<Integer> statusesAvoided = new ArrayList<Integer>();
        statusesAvoided.add(EnumCustomerMappingStatus.NO_RELATION.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_SENT.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_RECIEVED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_ACCEPTED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.CONTRACT_RECIEVED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.CONTRACT_REVIEW.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_DECLINED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.ADVISOR_WITHDRAW.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.WITHDRAW.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.CONTRACT_TERMINATED.getValue());
        return statusesAvoided;
    }

    private LoginVO investorLogin(LoginVO login) {
        List investorList = userLoginDAO.investorLogin(login.getUsername(), this.encryptPassword(login.getPassword()), getStatusBeforeSignContract());
        if (investorList.isEmpty()) {
            // login failed
            LOGGER.log(Level.INFO, "Investor login failed with username {0}", login.getUsername());
        } else {
            Object[] investorItem = (Object[]) investorList.get(ZERO);
            MasterCustomerTb masterCustomer = (MasterCustomerTb) investorItem[ZERO];
            LOGGER.log(Level.INFO, "Investor login success with username {0}", login.getUsername());
            if (masterCustomer.getInitLogin() != ZERO) {
                login.setRedirectPage(EnumCustLoginStatus.fromInt(masterCustomer.getInitLogin()).toString());
            } else {
                login.setRedirectPage(EnumCustLoginStatus.fromInt(masterCustomer.getInitLogin()).toString());
            }
            Integer relationId = (Integer) investorItem[ONE];
            if (relationId == null) {
                login.setInvestorWithAdvisor(Boolean.FALSE);
            } else {
                login.setInvestorWithAdvisor(Boolean.TRUE);
            }
            // login.setInvestorWithAdvisor(!(relationId == null ||(Integer) investorItem[TWO]!= EnumCustomerMappingStatus.CONTRACT_TERMINATED.getValue()));
            login.setMasterCustomer(masterCustomer);
            login.setAccessToken(masterCustomer.getMasterApplicantTb().getLinkedinPassword() != null
                    ? masterCustomer.getMasterApplicantTb().getLinkedinPassword() : EMPTY_STRING);
            login.setLinkedInId(masterCustomer.getMasterApplicantTb().getLinkedinMemberId() != null
                    ? masterCustomer.getMasterApplicantTb().getLinkedinMemberId() : EMPTY_STRING);
            login.setLinkedInConnected(masterCustomer.getMasterApplicantTb().getLinkedInConnected());
            login.setInitStatus(masterCustomer.getInitLogin());
        }
        LOGGER.log(Level.INFO, "Investor login redirect to {0}", login.getRedirectPage());
        return login;
    }

    /**
     * Method used for password change for advisor.
     *
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    private boolean changeAdvisorPassword(String userName, String password, String newPassword) {
        boolean status = false;
        List advisorList = userLoginDAO.advisorLogin(userName, password);
        if (advisorList.isEmpty()) {
            List tempList = userLoginDAO.tempUserlogin(userName, password, EnumStatus.RESET_PWD.getValue());
            if (!tempList.isEmpty()) {
                TempRegistrationTb tempRegistrationTb = (TempRegistrationTb) tempList.get(ZERO);
                if (tempRegistrationTb.getPassword().equalsIgnoreCase(password)) {
                    tempRegistrationTb.setPassword(newPassword);
                    tempRegistrationTb.setInitLogin(ONE);
                    userLoginDAO.changeTempDetails(tempRegistrationTb);
                    status = true;
                    LOGGER.log(Level.INFO, "Change password success for advisor {0}", userName);
                }
            } else {
                // Wrong password
                LOGGER.log(Level.INFO, "Change password faild - wrong credentials for advisor {0}", userName);
            }
        } else {
            MasterAdvisorTb masterAdvisor = (MasterAdvisorTb) advisorList.get(ZERO);
            if (masterAdvisor.getPassword().equalsIgnoreCase(password)) {
                masterAdvisor.setPassword(newPassword);
                if (masterAdvisor.getInitLogin() == ZERO) {
                    masterAdvisor.setInitLogin(masterAdvisor.getInitLogin() + ONE);
                }
                userLoginDAO.changeAdvisorDetails(masterAdvisor);
                status = true;
                LOGGER.log(Level.INFO, "Change password success for advisor {0}", userName);
            }
        }
        return status;
    }

    /**
     * Method for changing investor password
     *
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    private boolean changeInvestorPassword(String userName, String password, String newPassword) {
        boolean status = false;
        List investorList = userLoginDAO.investorLogin(userName, password, getStatusBeforeSignContract());
        if (investorList.isEmpty()) {
            List tempList = userLoginDAO.tempUserlogin(userName, password, EnumStatus.RESET_PWD.getValue());
            if (!tempList.isEmpty()) {
                TempRegistrationTb tempRegistrationTb = (TempRegistrationTb) tempList.get(ZERO);
                if (tempRegistrationTb.getPassword().equalsIgnoreCase(password)) {
                    tempRegistrationTb.setPassword(newPassword);
                    tempRegistrationTb.setInitLogin(ONE);
                    userLoginDAO.changeTempDetails(tempRegistrationTb);
                    status = true;
                    LOGGER.log(Level.INFO, "Change password success for investor {0}", userName);
                }
            } else {
                // Wrong password
                LOGGER.log(Level.INFO, "Change password faild - wrong credentials for investor {0}", userName);
            }
        } else {
            Object[] investorItem = (Object[]) investorList.get(ZERO);
            MasterCustomerTb masterCustomer = (MasterCustomerTb) investorItem[ZERO];
            if (masterCustomer.getPassword().equalsIgnoreCase(password)) {
                masterCustomer.setPassword(newPassword);
                if (masterCustomer.getInitLogin() == ZERO) {
                    masterCustomer.setInitLogin(masterCustomer.getInitLogin() + ONE);
                }
                userLoginDAO.changeInvestorDetails(masterCustomer);
                status = true;
                LOGGER.log(Level.INFO, "Change password success for investor {0}", userName);
            }
        }
        return status;
    }

    /**
     * Encrypting password
     *
     * @param password
     * @return
     */
    private String encryptPassword(String password) {
        SecurityUtility security = SecurityUtility.getInstance();
        return security.encrypt(password);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public LoginVO userLogin(LoginVO login) {
        login.setRedirectPage(FAILURE);
        String usertype = login.getUsertype();
        if (usertype.equalsIgnoreCase(YES)) {
            advisorLogin(login);
        } else if (usertype.equalsIgnoreCase(NO)) {
            investorLogin(login);
        } else if (login.getUsertype().equals(ADMIN_USER)) {
            adminLogin(login);
        }
        return login;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public LoginVO userTwoFactorLogin(LoginVO login) {
        String usertype = login.getUsertype();
        if (usertype.equalsIgnoreCase(YES)) {
            advisorTwoFactorLogin(login);
        } else if (usertype.equalsIgnoreCase(NO)) {
            investorTwoFactorLogin(login);
        } else if (login.getUsertype().equals(ADMIN_USER)) {
            adminLogin(login);
        }
        return login;

    }

    private LoginVO investorTwoFactorLogin(LoginVO login) {
        List investorList = userLoginDAO.investorTwoFactorLogin(login.getUsername(), this.encryptPassword(login.getPassword()),
                DateUtil.dateToString(login.getDob(), "yyyyMMdd"), getStatusBeforeSignContract());
        if (investorList.isEmpty()) {
            // login failed
            LOGGER.log(Level.INFO, "Investor login failed with username {0}", login.getUsername());
            login.setRedirectPage(FAILURE);
        } else {
            Object[] investorItem = (Object[]) investorList.get(ZERO);
            MasterCustomerTb masterCustomer = (MasterCustomerTb) investorItem[ZERO];
            login.setInitStatus(masterCustomer.getInitLogin());
            LOGGER.log(Level.INFO, "Investor login success with username {0}", login.getUsername());
            if (masterCustomer.getMasterApplicantTb().getLinkedInConnected()) {
                if (masterCustomer.getInitLogin() != ZERO) {
                    login.setRedirectPage(EnumCustLoginStatus.fromInt(masterCustomer.getInitLogin()).toString());
                    masterCustomer.setLoggedIn(Boolean.TRUE);
                    if (investorItem[2] != null && masterCustomer.getInitLogin() == 1) {
                        if (investorItem[2].equals(EnumCustomerMappingStatus.CONTRACT_SIGNED.getValue())
                                || investorItem[2].equals(EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED.getValue())) {
                            masterCustomer.setInitLogin(masterCustomer.getInitLogin() + 1);
                        }
                    }
                    userLoginDAO.changeInvestorDetails(masterCustomer);
                } else {
                    login.setRedirectPage(EnumCustLoginStatus.fromInt(masterCustomer.getInitLogin()).toString());
                }
            } else {
                masterCustomer.setLoggedIn(Boolean.TRUE);
                if (investorItem[2] != null && masterCustomer.getInitLogin() == ONE) {
                    if (investorItem[2].equals(EnumCustomerMappingStatus.CONTRACT_SIGNED.getValue())
                            || investorItem[2].equals(EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED.getValue())) {
                        masterCustomer.setInitLogin(masterCustomer.getInitLogin() + ONE);
                    }
                }
                userLoginDAO.changeInvestorDetails(masterCustomer);
                 if(masterCustomer.getMasterApplicantTb().getLinkedInSkipped()){
                    login.setRedirectPage("investordashboard");
                }else{
                    login.setRedirectPage("login_linkedin_investor"); 
                 }
                
            }

            Integer relationId = (Integer) investorItem[1];
            // Integer relationStatus = (Integer) investorItem[2];
            login.setRelationStatus((Integer) investorItem[2] == null ? 0 : (Integer) investorItem[2]);
            if (relationId == null || (Integer) investorItem[2] != EnumCustomerMappingStatus.CONTRACT_TERMINATED.getValue()) {
                login.setInvestorWithAdvisor(Boolean.FALSE);
            } else {
                login.setInvestorWithAdvisor(Boolean.TRUE);
            }
            login.setRelationId((Integer) investorItem[1] == null ? 0 : (Integer) investorItem[1]);
            login.setMasterCustomer(masterCustomer);
            login.setAccessToken(masterCustomer.getMasterApplicantTb().getLinkedinPassword() != null
                    ? masterCustomer.getMasterApplicantTb().getLinkedinPassword() : EMPTY_STRING);
            login.setLinkedInId(masterCustomer.getMasterApplicantTb().getLinkedinMemberId() != null
                    ? masterCustomer.getMasterApplicantTb().getLinkedinMemberId() : EMPTY_STRING);
            login.setLinkedInConnected(masterCustomer.getMasterApplicantTb().getLinkedInConnected());
            login.setMasterCustomer(masterCustomer);
            login.setFirstName(masterCustomer.getFirstName() == null ? EMPTY_STRING : masterCustomer.getFirstName());
            login.setMiddleName(masterCustomer.getMiddleName() == null ? EMPTY_STRING : masterCustomer.getMiddleName());
            login.setLastName(masterCustomer.getLastName() == null ? EMPTY_STRING : masterCustomer.getLastName());
        }
        LOGGER.log(Level.INFO, "Investor login redirect to {0}", login.getRedirectPage());
        return login;
    }

    private LoginVO advisorTwoFactorLogin(LoginVO login) {
        List advisorList = userLoginDAO.advisorTwoFactorLogin(login.getUsername(), this.encryptPassword(login.getPassword()), DateUtil.dateToString(login.getDob(), "yyyyMMdd"));
        if (advisorList.isEmpty()) {
            // login failed
            LOGGER.log(Level.INFO, "Advisor login failed with username {0}", login.getUsername());
            login.setRedirectPage(FAILURE);
        } else {
            MasterAdvisorTb masterAdvisor = (MasterAdvisorTb) advisorList.get(ZERO);
            LOGGER.log(Level.INFO, "Advisor login success with username {0}", login.getUsername());
            login.setInitStatus(masterAdvisor.getInitLogin());
            if (masterAdvisor.getMasterApplicantTb().getLinkedInConnected()) {
                if (masterAdvisor.getInitLogin() != ZERO) {
                    login.setRedirectPage(EnumAdvLoginStatus.fromInt(masterAdvisor.getInitLogin()).toString());
                    masterAdvisor.setLoggedIn(Boolean.TRUE);
                    if (masterAdvisor.getInitLogin() == ONE) {

                        masterAdvisor.setInitLogin(masterAdvisor.getInitLogin() + ONE);
                    }
                    userLoginDAO.changeAdvisorDetails(masterAdvisor);
                } else {
                    login.setRedirectPage(EnumAdvLoginStatus.fromInt(masterAdvisor.getInitLogin()).toString());
                }
            } else {
                masterAdvisor.setLoggedIn(Boolean.TRUE);
                if (masterAdvisor.getInitLogin() == ONE) {

                    masterAdvisor.setInitLogin(masterAdvisor.getInitLogin() + ONE);
                }
                userLoginDAO.changeAdvisorDetails(masterAdvisor);
                if(masterAdvisor.getMasterApplicantTb().getLinkedInSkipped()){
                    login.setRedirectPage("advisordashboard");
                }else{
                    login.setRedirectPage("login_linkedin_advisor");
                }
            }
            login.setAccessToken(masterAdvisor.getMasterApplicantTb().getLinkedinPassword() != null
                    ? masterAdvisor.getMasterApplicantTb().getLinkedinPassword() : EMPTY_STRING);
            login.setLinkedInId(masterAdvisor.getMasterApplicantTb().getLinkedinMemberId() != null
                    ? masterAdvisor.getMasterApplicantTb().getLinkedinMemberId() : EMPTY_STRING);
            login.setMasterAdvisor(masterAdvisor);
            login.setLinkedInConnected(masterAdvisor.getMasterApplicantTb().getLinkedInConnected());
            login.setFirstName(masterAdvisor.getFirstName() == null ? EMPTY_STRING : masterAdvisor.getFirstName());
            login.setMiddleName(masterAdvisor.getMiddleName() == null ? EMPTY_STRING : masterAdvisor.getMiddleName());
            login.setLastName(masterAdvisor.getLastName() == null ? EMPTY_STRING : masterAdvisor.getLastName());
        }
        LOGGER.log(Level.INFO, "Advisor login redirect to {0}", login.getRedirectPage());
        return login;
    }

    /**
     * Method used for password change for advisor/investor.
     *
     * @param username
     * @param password
     * @param newPassword
     * @param userType
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean changePasswordOnLogin(String username, String password, String newPassword, String userType) {
        boolean passwordChanged = false;

        //Encrypting new and old password
        String encPassword = this.encryptPassword(password);
        String encNewPassword = this.encryptPassword(newPassword);
        if (userType.equalsIgnoreCase(YES)) {
            passwordChanged = this.changeAdvisorPassword(username, encPassword, encNewPassword);
        } else if (userType.equalsIgnoreCase(NO)) {
            passwordChanged = this.changeInvestorPassword(username, encPassword, encNewPassword);
        }
        return passwordChanged;
    }

    public IUserLoginDAO getUserLoginDAO() {
        return userLoginDAO;
    }

    public void setUserLoginDAO(IUserLoginDAO userLoginDAO) {
        this.userLoginDAO = userLoginDAO;
    }

    /**
     * Checking linkedIn token expired or not
     *
     * @param currentDateTime
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isLinkedinExpireIn(String currentDateTime, String username) {
        boolean status = false;
        status = userLoginDAO.getLinkedinExpireIn(currentDateTime, username);
        return status;
    }

    /**
     * This method is used to for user to login after successfully verify mail
     * id.
     *
     * @param login
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public LoginVO userRegistrationLogin(LoginVO login) {

        //Retriving user with specified usernmae and password
        List advisorList = userLoginDAO.registrationLogin(login.getUsername(), this.encryptPassword(login.getPassword()), null);
        if (advisorList.isEmpty()) {
            // login failed
            login.setRedirectPage(FAILURE);
            LOGGER.log(Level.INFO, "Registration login failed with username {0}", login.getUsername());
        } else {
            login.setRedirectPage(SUCCESS);
            LOGGER.log(Level.INFO, "Registration login success with username {0}", login.getUsername());
        }
        LOGGER.log(Level.INFO, "Registration login redirect to {0}", login.getRedirectPage());
        return login;
    }

    /**
     * This method is used to get user status after verification
     *
     * @param email
     * @param password
     * @param type
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Integer getUserStatus(String email, String password, String type) {
        List responselist = new ArrayList();
        Integer status = userLoginDAO.customerStatus(email, this.encryptPassword(password), type);
        if (status == null || status == EnumStatus.NEW_APPLICANT.getValue()) {
            List advisorList = userLoginDAO.registrationLogin(email, this.encryptPassword(password), type);
            if (advisorList.isEmpty()) {
                status = EnumStatus.FAILED.getValue();
            } else {
                TempRegistrationTb temp = (TempRegistrationTb) advisorList.get(0);
                if (status == null) {
                    if (temp.getInitLogin() != ONE) {
                        status = EnumStatus.RESET_PWD.getValue();
                    } else if (temp.getMailVerified()) {
                        status = EnumStatus.MAIL_VERIFIED.getValue();
                    } else {
                        status = EnumStatus.MAIL_NOT_VERIFIED.getValue();
                    }
                } else {
                    if (status == EnumStatus.NEW_APPLICANT.getValue() && temp.getInitLogin() == EnumStatus.RESET_PWD.getValue()) {
                        status = EnumStatus.RESET_PWD.getValue();
                    }
                }

            }
        }
        return status;
    }

    /**
     * This method is used to get user status after verification for new code
     *
     * @author sumeet
     * @param email
     * @param password
     * @param type
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Integer getUserStatusV2(String email, String password, String type) {
        List responselist = new ArrayList();
        Integer status = userLoginDAO.customerStatus(email, this.encryptPassword(password), type);
        if (status == null || status == EnumStatus.NEW_APPLICANT.getValue()) {
            List advisorList = userLoginDAO.registrationLogin(email, this.encryptPassword(password), type);
            if (advisorList.isEmpty()) {
                status = EnumStatus.FAILED.getValue();
            } else {
                TempInv tempInv = userProfileDAO.getTempInvDetails(email);
                String regId = tempInv.getRegistrationId();
                if (regId != null && status == null) {
                    TempRegistrationTb temp = (TempRegistrationTb) advisorList.get(0);
                    boolean fpStatus = userLoginDAO.financialPlannerStatus(regId);
                    if (fpStatus) {
                        status = EnumStatus.FP_NOT_SUBMITTED.getValue();
                    } else if (temp.getInitLogin() != ONE) {
                        status = EnumStatus.RESET_PWD.getValue();
                    } else if (temp.getMailVerified()) {
                        status = EnumStatus.MAIL_VERIFIED.getValue();
                    } else {
                        status = EnumStatus.MAIL_NOT_VERIFIED.getValue();
                    }
                } else {
                    TempRegistrationTb temp = (TempRegistrationTb) advisorList.get(0);
                    if (status == EnumStatus.NEW_APPLICANT.getValue() && temp.getInitLogin() == EnumStatus.RESET_PWD.getValue()) {
                        status = EnumStatus.RESET_PWD.getValue();
                    }
                }

            }
        }
        return status;
    }

    public IUserProfileDAO getUserProfileDAO() {
        return userProfileDAO;
    }

    public void setUserProfileDAO(IUserProfileDAO userProfileDAO) {
        this.userProfileDAO = userProfileDAO;
    }

    /**
     * fetchs the required feild depending on usertype true is for advisor false
     * is for investor
     *
     * @see
     * com.gtl.mmf.bao.IUserLoginBAO#getUserPersonalDeatils(java.lang.String,
     * boolean, java.lang.String)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getUserPersonalDeatils(String email, boolean userType, String fieldToFetch) {

        return userLoginDAO.getUserPersonalDetails(email, userType, fieldToFetch);

    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Boolean getUserType(String email, String password) {
    	String encryptedPassword;
    	SecurityUtility security = SecurityUtility.getInstance();
		encryptedPassword = security.encrypt(password);
		//return userLoginDAO.getUserType(email, password);
		return userLoginDAO.getUserType(email, "");
    }

}
