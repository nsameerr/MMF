/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IRateAdvisorBAO;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.RateAdvisorVO;
import com.gtl.mmf.service.util.LookupDataLoader;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import javax.faces.application.FacesMessage;

/**
 *
 * @author trainee3
 */
@Named("rateAdvisorContoller")
@Scope("view")
public class RateAdvisorController {

    private static final String INVALID_RATING_MSG = "Please rate your advisor.";
    private static final String INVESTOR_DASHBOARD_PAGE = "/pages/investordashboard?faces-redirect=true";

    private Map<String, Integer> rateAdvisorList;
    private RateAdvisorVO rateAdvisorVO;
    private AdvisorDetailsVO advisorDetailsVO;
    @Autowired
    IRateAdvisorBAO rateAdvisorBAO;
    private String redirectTo = EMPTY_STRING;

    @PostConstruct
    public void afterInit() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        if (storedValues != null) {
            advisorDetailsVO = (AdvisorDetailsVO) storedValues.get(SELECTED_ADVISOR);
            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            if (userSessionBean != null) {
                userSessionBean.setFromURL("/pages/rate_advisor?faces-redirect=true");
            }
        }
        setRateAdvisorList(LookupDataLoader.getRateAdvisorValueList());
        rateAdvisorVO = rateAdvisorBAO.getAdvisorDetails(advisorDetailsVO.getRelationId());
    }

    public void doActionRateListener(ActionEvent event) {
        if (this.isAdvisorRatingInvalid()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_RATING_MSG, INVALID_RATING_MSG));
            redirectTo = EMPTY_STRING;
        } else {
            Integer totalRate = Math.round((rateAdvisorVO.getRecommendationFrequency()
                    + rateAdvisorVO.getRecommendationQuality() + rateAdvisorVO.getResponsivenessQuality()) / 3);
            rateAdvisorVO.setTotalRate(totalRate.shortValue());
            rateAdvisorBAO.updateAdvisorRating(rateAdvisorVO);
            redirectTo = INVESTOR_DASHBOARD_PAGE;
        }
    }

    private boolean isAdvisorRatingInvalid() {
        return rateAdvisorVO.getRecommendationFrequency() == null
                || rateAdvisorVO.getRecommendationQuality() == null
                || rateAdvisorVO.getResponsivenessQuality() == null;
    }

    public String redirectRate() {
        return redirectTo;
    }

    public Map<String, Integer> getRateAdvisorList() {
        return rateAdvisorList;
    }

    public void setRateAdvisorList(Map<String, Integer> rateAdvisorList) {
        this.rateAdvisorList = rateAdvisorList;
    }

    public RateAdvisorVO getRateAdvisorVO() {
        return rateAdvisorVO;
    }

    public void setRateAdvisorVO(RateAdvisorVO rateAdvisorVO) {
        this.rateAdvisorVO = rateAdvisorVO;
    }

    public String getreDirectionUrl() {
        return INVESTOR_DASHBOARD_PAGE;
    }

}
