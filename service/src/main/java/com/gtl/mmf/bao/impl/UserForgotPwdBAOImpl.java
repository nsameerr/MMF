/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IUserForgotPwdBAO;
import com.gtl.mmf.dao.IUserForgotPwdDAO;
import com.gtl.mmf.dao.IUserProfileDAO;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.TempRegistrationTb;
import com.gtl.mmf.persist.vo.ForgotPwdVO;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.PasswordUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.SecurityUtility;
import com.gtl.mmf.service.util.TemplateUtil;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trainee3
 */
public class UserForgotPwdBAOImpl implements IUserForgotPwdBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.IUserForgotPwdBAOImpl");

    @Autowired
    private IUserForgotPwdDAO userForgotPwdDAO;
    @Autowired
    private IUserProfileDAO userProfileDAO;

    /**
     * Method for changing password when investor/advisor forget password
     *
     * @param username
     * @param regid
     * @param mobile
     * @param advisor
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public ForgotPwdVO changePasswordOnForgotPwd(String username, String regid, String mobile, boolean advisor) {
        List<MasterAdvisorTb> advDet = null;
        List<MasterCustomerTb> invDet = null;
        List<TempRegistrationTb> temp = null;
        List responseList = new ArrayList();
        boolean passwordChanged = false;
        ForgotPwdVO forgotPwdVO = null;
        String newpassword = null;

        //Creating new password and encrypting
        newpassword = PasswordUtil.getPassword();
        String encPassword = SecurityUtility.getInstance().encrypt(newpassword);

        if (advisor) {
            passwordChanged = changeAdvisorPwd(username, regid, mobile, encPassword);
        } else {
            passwordChanged = changeInvestorPwd(username, regid, mobile, encPassword);
        }

        if (passwordChanged && advisor) {
            responseList = userProfileDAO.getAdvisorDetails(regid, username,false);
            advDet = (List<MasterAdvisorTb>) responseList.get(ZERO);
            temp = (List<TempRegistrationTb>) responseList.get(1);

            StringBuffer name = new StringBuffer();
            if (!advDet.isEmpty()) {
                MasterAdvisorTb advDetails = advDet.get(ZERO);
                name.append(advDetails.getFirstName()).append(SPACE).append(advDetails.getMiddleName())
                        .append(SPACE).append(advDetails.getLastName());
                forgotPwdVO = createForgotPwdVO(advisor, advDetails.getFirstName(), username, newpassword,
                        passwordChanged, regid, mobile);
            } else {
                TempRegistrationTb tempDetails = temp.get(ZERO);
                name.append(tempDetails.getEmail());
                forgotPwdVO = createForgotPwdVO(advisor, tempDetails.getEmail(), username, newpassword,
                        passwordChanged, regid, mobile);
            }

            try {
                sendmail(name.toString(), username, newpassword);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        } else if (passwordChanged && !advisor) {
            responseList = userProfileDAO.getInvestorDetails(regid, username,false);
            invDet = (List<MasterCustomerTb>) responseList.get(ZERO);
            temp = (List<TempRegistrationTb>) responseList.get(1);

            StringBuffer name = new StringBuffer();
            if (!invDet.isEmpty()) {
                MasterCustomerTb invDetails =  invDet.get(ZERO);
                name.append(invDetails.getFirstName()).append(SPACE).append(invDetails.getMiddleName())
                        .append(SPACE).append(invDetails.getLastName());
                forgotPwdVO = createForgotPwdVO(advisor, invDetails.getFirstName(), username, newpassword,
                        passwordChanged, regid, mobile);
            } else {
                TempRegistrationTb tempDetails = temp.get(ZERO);
                name.append(tempDetails.getEmail());
                forgotPwdVO = createForgotPwdVO(advisor, tempDetails.getEmail(), username, newpassword,
                        passwordChanged, regid, mobile);
            }

            try {
                sendmail(name.toString(), username, newpassword);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        } else {
            forgotPwdVO = new ForgotPwdVO();
            forgotPwdVO.setPasswordChanged(false);
        }
        return forgotPwdVO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public ForgotPwdVO changePasswordAdminOnForgotPwd(String email, String mobile, String username) {
        int pwdUpdate;
        String newpassword = null;
        String firstname = ADMIN_USER;
        ForgotPwdVO forgotPwdVO = new ForgotPwdVO();
        newpassword = PasswordUtil.getPassword();
        pwdUpdate = userForgotPwdDAO.adminPwdUpdate(email, mobile, this.encryptPassword(newpassword), username);
        if (pwdUpdate != ZERO) {
            forgotPwdVO.setEmail(email);
            forgotPwdVO.setMobile(mobile);
            forgotPwdVO.setNewpassword(newpassword);
            forgotPwdVO.setPasswordChanged(true);
            try {
                sendmail(firstname, email, newpassword);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        } else {
            forgotPwdVO.setPasswordChanged(false);
        }
        return forgotPwdVO;
    }

    public IUserForgotPwdDAO getForgotPwdDAO() {
        return userForgotPwdDAO;
    }

    public void setForgotPwdDAO(IUserForgotPwdDAO userForgotPwdDAO) {
        this.userForgotPwdDAO = userForgotPwdDAO;
    }

    private ForgotPwdVO createForgotPwdVO(boolean advisor, String firstName, String username,
            String newpassword, boolean passwordChanged, String regid, String mobile) {
        ForgotPwdVO forgotPwdVO = new ForgotPwdVO();
        forgotPwdVO.setFirstname(firstName);
        forgotPwdVO.setAdvisor(advisor);
        forgotPwdVO.setMobile(mobile);
        forgotPwdVO.setRegid(regid);
        forgotPwdVO.setUsername(username);
        forgotPwdVO.setNewpassword(newpassword);
        forgotPwdVO.setPasswordChanged(passwordChanged);
        return forgotPwdVO;
    }

    private void sendmail(String firstName, String email, String newpassword) throws ClassNotFoundException {
        try {
            StringWriter sw = new StringWriter();
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();

            Template template = ve.getTemplate("ForgotPwdMail.vm");
            VelocityContext context = TemplateUtil.getInstance().getVelocityContext();
            context.put("newline", NEW_LINE);
            context.put("name", firstName);
            context.put("password", newpassword);
            context.put("email", email);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(email);
            MailUtil.getInstance().sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress,
                    PropertiesLoader.getPropertiesValue(MMF_FORGOT_PASSWORD_MAIL_SUBJECT), sw.toString());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    private boolean changeAdvisorPwd(String username, String regid, String mobile, String encPassword) {
        boolean status = false;
        int pwdUpdate = ZERO;
        pwdUpdate = userForgotPwdDAO.advisorPwdUpdate(username, mobile, regid, encPassword);
        if (pwdUpdate != ZERO) {
            status = true;
        }
        return status;
    }

    private boolean changeInvestorPwd(String username, String regid, String mobile, String encPassword) {
        boolean status = false;
        int pwdUpdate = ZERO;
        pwdUpdate = userForgotPwdDAO.investorPwdUpdate(username, mobile, regid, encPassword);
        if (pwdUpdate != ZERO) {
            status = true;
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
}
