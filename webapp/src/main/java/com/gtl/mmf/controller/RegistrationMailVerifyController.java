/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.VERIFICATION_MAIL_URL_PARAM_1;
import com.gtl.mmf.service.vo.VerifyMailVO;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("registrationMailVerifyController")
@Scope("view")
public class RegistrationMailVerifyController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.RegistrationMailVerifyController");
    private static final String RE_DIRECTION_INIT_SUCCESS = "/pages/init_reg_success?faces-redirect=true";
    private VerifyMailVO verifyMailVO;
    private String redirectionURL;

    @Autowired
    private IUserProfileBAO userProfileBAO;

    @PostConstruct
    public void afterinit() {
        verifyMailVO = new VerifyMailVO();
        String parameterUid = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get(VERIFICATION_MAIL_URL_PARAM_1);
        verifyMailVO.setParameterUid(parameterUid);
    }

    public void verifyEmail() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (userProfileBAO.isemailverified(verifyMailVO)) {
            redirectionURL = RE_DIRECTION_INIT_SUCCESS;
        } else {
            fc.addMessage("reg_check_valid", new FacesMessage(SEVERITY_ERROR, "E-mail verfification failed", null));
            redirectionURL = EMPTY_STRING;
        }
    }

    public String reDirectionURl() {
        return redirectionURL;
    }

    public VerifyMailVO getVerifyMailVO() {
        return verifyMailVO;
    }

    public void setVerifyMailVO(VerifyMailVO verifyMailVO) {
        this.verifyMailVO = verifyMailVO;
    }

}
