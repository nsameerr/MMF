/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import com.gtl.linkedin.util.LinkedInVO;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import com.gtl.mmf.service.vo.UserProfileVO;
import com.gtl.linkedin.util.LinkedInUtil;
import com.gtl.linkedin.util.ServiceTypeEnum;
import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.controller.UserSessionBean;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.LINKEDIN_ADVISER_REDIRECT_URL;
import static com.gtl.mmf.service.util.IConstants.LINKEDIN_INVESTOR_REDIRECT_URL;
import static com.gtl.mmf.service.util.IConstants.REDIRECT_TO_AUTHENTICATE_SERVLET;
import com.gtl.mmf.service.util.BeanLoader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.gtl.mmf.service.util.IConstants.LOGIN_PAGE;
import static com.gtl.mmf.service.util.IConstants.EXPIRE_IN;
import static com.gtl.mmf.service.util.IConstants.LINKEDIN_FAIL;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.util.StackTraceWriter;
import java.text.ParseException;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 07960
 */
public class Authenticate extends HttpServlet implements IConstants {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.commonl.Authenticate");

    private static final String CODE = "code";
    private static final String NULL = "null";
    private static final String ONE = "1";
    private static final String MOBILE = "mobile";
    private static final String HOME = "home";
    private static final String EMAILID = "emailId";
    private static final String USER_PROFILE = "userProfile";
    private static final int ONE_INT = 1;
    String ADVISOR_DASHBOARD = "/faces/pages/advisordashboard.xhtml?faces-redirect=true";
    String INVESTOR_DASHBOARD = "/faces/pages/investordashboard.xhtml?faces-redirect=true";
//    private boolean linkedInUser;

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
        HttpServletResponse resp = (HttpServletResponse) response;
        String code = null;
        String DOB = null;
        Map<String, Object> storedValues = null;
        Calendar calendar = Calendar.getInstance();

//            ILookupDataLoaderBAO lookupDataLoaderBAO = (ILookupDataLoaderBAO) BeanLoader.getBean("lookupDataLoaderBAO");
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");

        if (FacesContext.getCurrentInstance() == null) {
            code = request.getParameter(CODE);
            resp.sendRedirect(request.getContextPath() + REDIRECT_TO_AUTHENTICATE_SERVLET + code);
        } else {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = ec.getSessionMap();
            storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().
                    getSessionMap().get(STORED_VALUES);
            Boolean advisor = (Boolean) storedValues.get(IS_ADVISOR);
            code = request.getParameter(CODE);
            HttpSession session = (HttpSession) ec.getSession(true);
            if (code == null || code.equals(NULL)) {
                resp.sendRedirect(getRedirectionBasedOnLinkedInResponse(advisor));
            } else {

                try {
                    LinkedInVO linkedInVO = null;
                    //Retrieve Access Token
                    LOGGER.log(Level.INFO, "Retrieving access toekn....");
                    linkedInVO = LinkedInUtil.getInstance().getLinkedInResponseVO(code, null,
                            ServiceTypeEnum.ACCESS_TOKEN);

                    if (linkedInVO == null) {
                        resp.sendRedirect(getRedirectionBasedOnLinkedInResponse(advisor));
                    } else {
                        LOGGER.log(Level.INFO, "LinkedIn response success....");
                        LinkedInVO contactInfofVO = null;
                        //Retrieve ContactInfo
                        contactInfofVO = LinkedInUtil.getInstance().getLinkedInResponseVO(linkedInVO
                                .getAccess_token(), null, ServiceTypeEnum.FULL_PROFILE);
                        //Update expiry details
                        if (storedValues.get(EXPIRE_IN) != null && (Boolean) storedValues.get(EXPIRE_IN)) {
                            LOGGER.log(Level.INFO, "Access token expiry update....");
                            String email = (String) storedValues.get("username");
                            Object[] linkedinUserExists = userProfileBAO.isLinkedinUserExists(contactInfofVO
                                    .getId());
                            Long linkedinUserStatus = (Long) linkedinUserExists[ZERO];
                            String linkedinEmail = (String) linkedinUserExists[ONE_INT];
                            String linkedInIdMappedEmail = (String) linkedinUserExists[2];
                            if (!linkedinUserStatus.equals(0l) && !linkedInIdMappedEmail
                                    .equalsIgnoreCase(email)) {
                                storedValues.put(EMAIL_DIFF, true);
                                storedValues.put("linkedInIdMappedEmail", linkedInIdMappedEmail);
                                storedValues.put(FROM_DASHBOARD, true);
                                session.setAttribute(USER_EXPIRED, true);
                                resp.sendRedirect(getRedirectionPath(advisor));
                            } else {
                                UserProfileVO userProfileVO = this.getUpdateExpireInData(linkedInVO, email);
                                boolean status = userProfileBAO.updateExpireInDet(userProfileVO);
                                if (status) {
                                    LOGGER.log(Level.INFO, "Access token expiry update success. {0}", status);
                                    resp.sendRedirect(TWO_FACTOR_LOGIN_PAGE);
                                }
                            }

                        } else {
                            calendar.add(Calendar.DATE, 59);
                            String email = (String) storedValues.get(EMAILID);
                            UserProfileVO userProfile = new UserProfileVO();
                            if (contactInfofVO != null) {
                                if(contactInfofVO.getPhoneNumbers()!=null){
                                    if (contactInfofVO.getPhoneNumbers().getTotal().equals(ONE)) {
                                    if (contactInfofVO.getPhoneNumbers().getValues().get(0).getPhoneType()
                                            .equals(MOBILE)) {
                                        userProfile.setMobile(contactInfofVO.getPhoneNumbers().getValues()
                                                .get(0).getPhoneNumber());
                                    } else if (contactInfofVO.getPhoneNumbers().getValues().get(0)
                                            .getPhoneType().equals(HOME)) {
                                        userProfile.setTelephone(contactInfofVO.getPhoneNumbers()
                                                .getValues().get(0).getPhoneNumber());
                                    }
                                }
                                }
                                if (contactInfofVO.getDateOfBirth() != null) {
                                    int day = Integer.parseInt(contactInfofVO.getDateOfBirth().getDay());
                                    DOB = String.valueOf(day) + getMonth(contactInfofVO.getDateOfBirth()
                                            .getMonth())
                                            + contactInfofVO.getDateOfBirth().getYear();
                                    LOGGER.log(Level.INFO, "COnverted DOB {0}", DOB);
                                }

                                userProfile.setAddress(contactInfofVO.getMainAddress() == null?
                                        EMPTY_STRING : contactInfofVO.getMainAddress());
                                userProfile.setLinkedin_member_id(contactInfofVO.getId() == null?
                                        EMPTY_STRING : contactInfofVO.getId());
                                userProfile.setLinkedin_user(contactInfofVO.getEmailAddress() == null?
                                        EMPTY_STRING : contactInfofVO.getEmailAddress());
                                userProfile.setMiddleName(contactInfofVO.getMiddleName() == null?
                                        EMPTY_STRING : contactInfofVO.getMiddleName());
                                userProfile.setLastName(contactInfofVO.getLastName() == null?
                                        EMPTY_STRING : contactInfofVO.getLastName());
                                userProfile.setName(contactInfofVO.getFirstName() == null?
                                        EMPTY_STRING : contactInfofVO.getFirstName());
                                userProfile.setPictureUrl(contactInfofVO.getPictureUrl() == null?
                                        EMPTY_STRING : contactInfofVO.getPictureUrl());
                                userProfile.setProfileUrl(contactInfofVO.getPublicProfileUrl() == null?
                                        EMPTY_STRING : contactInfofVO.getPublicProfileUrl());
                                if (contactInfofVO.getPositions() != null &&
                                        !"0".equals(contactInfofVO.getPositions().getTotal())) {

                                    userProfile.setWorkOrganization(contactInfofVO.getPositions()
                                            .getValues().get(0).getCompany().getName());
                                    userProfile.setJobTitle(contactInfofVO.getPositions().getValues()
                                            .get(0).getTitle());
                                }
                                if (DOB != null) {
                                    userProfile.setDob(DateUtil.convertShortDate(DOB));
                                }
                            }
                            userProfile.setEmail(email);
                            userProfile.setAdvisor(advisor);
                            userProfile.setAccess_token(linkedInVO.getAccess_token());
                            userProfile.setExpire_in(linkedInVO.getExpires_in());
                            userProfile.setExpire_in_date(calendar.getTime());

                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                                    .put(STORED_VALUES, storedValues);
                            Object[] linkedinUserExists = userProfileBAO.isLinkedinUserExists(userProfile
                                    .getLinkedin_member_id());
                            Long linkedinUserStatus = (Long) linkedinUserExists[ZERO];
                            String linkedinEmail = (String) linkedinUserExists[ONE_INT];
                            String linkedInIdMappedEmail = (String) linkedinUserExists[2];
                            if (!linkedinUserStatus.equals(0l) && !linkedInIdMappedEmail.equalsIgnoreCase(email)) {
                                storedValues.put(EMAIL_DIFF, true);
                                storedValues.put("linkedInIdMappedEmail", linkedInIdMappedEmail);
                                resp.sendRedirect(getRedirectionPath(advisor));
                                return;
                            }
                            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
                            userSessionBean.setAccessToken(userProfile.getAccess_token());
                            userSessionBean.setLinkedInId(userProfile.getLinkedin_member_id());
                            userSessionBean.setLinkedInConnected(true);
                            session.setAttribute("userSession", userSessionBean);

                            if (storedValues.get(LINKEDIN_CONNECTED) != null && !(Boolean) storedValues.get(LINKEDIN_CONNECTED)) {
                                storedValues.put(FROM_LINKEDINCONNECTED, true);
                                userProfile.setLinkedInConnected(true);
                                userProfileBAO.updateLinkedINData(userProfile);
                                userProfile.setLinkedInConnected((Boolean) storedValues.get(LINKEDIN_CONNECTED));
                            } else {
                                userProfile.setLinkedInConnected(true);
                                userProfileBAO.updateLinkedINData(userProfile);
                            }
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
                            if (advisor && userProfile.isLinkedInConnected()) {
                                String page = getCustomerInitLoginStatus(userSessionBean.getInitLogin() == 1 ? true : false, YES);
                                resp.sendRedirect(request.getContextPath() + "/faces/pages/" + page + ".xhtml?faces-redirect=true");
                            } else if (!advisor && userProfile.isLinkedInConnected()) {
                                String page = getCustomerInitLoginStatus(userSessionBean.getInitLogin() == 1 ? true : false, NO);
                                resp.sendRedirect(request.getContextPath() + "/faces/pages/" + page + ".xhtml?faces-redirect=true");
                            } else if (advisor && !userProfile.isLinkedInConnected()) {
                                resp.sendRedirect(getRedirectionPathAfterConnect(advisor));
                            } else if (!advisor && !userProfile.isLinkedInConnected()) {
                                resp.sendRedirect(getRedirectionPathAfterConnect(advisor));
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null,
                            StackTraceWriter.getStackTrace(ex));
                } catch (NumberFormatException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null,
                            StackTraceWriter.getStackTrace(ex));
                } catch (ParseException ex) {
                    Logger.getLogger(Authenticate.class.getName()).log(Level.SEVERE, null,
                            StackTraceWriter.getStackTrace(ex));
                }
            }
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

    /*private boolean  isEmailsSame(String regEmail, String linkedinEmail) {
     return regEmail.equalsIgnoreCase(linkedinEmail);
     }*/
    private UserProfileVO getUpdateExpireInData(LinkedInVO linkedInVO, String linkedinEmail) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 59);
        UserProfileVO userProfileVO = new UserProfileVO();
        userProfileVO.setAccess_token(linkedInVO.getAccess_token());
        userProfileVO.setExpire_in(linkedInVO.getExpires_in());
        userProfileVO.setExpire_in_date(calendar.getTime());
        userProfileVO.setEmail(linkedinEmail);
        return userProfileVO;
    }

    private String getRedirectionPath(Boolean advisor) {
        String path = null;
        if (!advisor.booleanValue()) {
            path = LINKEDIN_INVESTOR_REDIRECT_URL;
        } else {
            path = LINKEDIN_ADVISER_REDIRECT_URL;
        }
        return path;
    }

    private String getMonth(String monthNum) {
        String month = null;
        switch (Integer.parseInt(monthNum)) {
            case 1:
                month = "01";
                break;
            case 2:
                month = "02";
                break;
            case 3:
                month = "03";
                break;
            case 4:
                month = "04";
                break;
            case 5:
                month = "05";
                break;
            case 6:
                month = "06";
                break;
            case 7:
                month = "07";
                break;
            case 8:
                month = "08";
                break;
            case 9:
                month = "09";
                break;
            case 10:
                month = "10";
                break;
            case 11:
                month = "11";
                break;
            case 12:
                month = "12";
                break;
            default:
                month = "01";
                break;
        }
        return month;
    }

    private String getRedirectionPathSkipLinkedIn(Boolean advisor) {
        String path = null;
        if (!advisor.booleanValue()) {
            path = LookupDataLoader.getContextRoot() + LINKEDIN_INVESTOR_REDIRECT_URL;
        } else {
            path = LookupDataLoader.getContextRoot() + LINKEDIN_ADVISER_REDIRECT_URL;
        }
        return path;
    }

    private String getRedirectionPathAfterConnect(Boolean advisor) {
        String path = null;
        if (advisor.booleanValue()) {
            path = LookupDataLoader.getContextRoot() + ADVISOR_DASHBOARD;
        } else {
            path = LookupDataLoader.getContextRoot() + INVESTOR_DASHBOARD;
        }
        return path;
    }

    private String getCustomerInitLoginStatus(boolean initLogin, String usertype) {
        String redirecTo = "";
        if (usertype.equalsIgnoreCase(YES)) {
            if (initLogin) {
                redirecTo = EnumAdvLoginStatus.fromInt(1).toString();
            } else {
                redirecTo = EnumAdvLoginStatus.fromInt(2).toString();
            }

        } else {
            if (initLogin) {
                redirecTo = EnumCustLoginStatus.fromInt(1).toString();
            } else {
                redirecTo = EnumCustLoginStatus.fromInt(2).toString();
            }
        }
        return redirecTo;
    }

    private String getRedirectionBasedOnLinkedInResponse(boolean advisor) {
        String path = null;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get(STORED_VALUES);
        if (storedValues.get(EXPIRE_IN) != null && (Boolean) storedValues.get(EXPIRE_IN)) {
            sessionMap.put(LINKEDIN_FAIL, true);
            path = LOGIN_PAGE;
        } else {
            LOGGER.log(Level.INFO, "Response code return as null from linked in. Redirecting to regisrtaion page....");
            if (storedValues.get(LINKEDIN_CONNECTED) != null && !(Boolean) storedValues.get(LINKEDIN_CONNECTED)) {
                path = getRedirectionPathAfterConnect(advisor);
            } else {
                UserProfileVO userProfile = new UserProfileVO();
                userProfile.setAdvisor(advisor);
                userProfile.setLinkedInConnected(false);
                String email = (String) storedValues.get(EMAILID);
                userProfile.setEmail(email);
                storedValues.put("userProfile", userProfile);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
                path = getRedirectionPathSkipLinkedIn(advisor);
            }
        }
        return path;
    }
}
