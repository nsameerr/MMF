/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.webapp.util;

import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


/**
 *
 * @author trainee3
 */
public class AdvisorRedirectionUtil {
    
    public String advisorRredirectionPath(InvestorDetailsVO investorDetailsVO) {
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(SELECTED_INVESTOR, investorDetailsVO);
        sessionMap.put(STORED_VALUES, storedValues);
        EnumAdvisorMappingStatus relationStatus = EnumAdvisorMappingStatus.fromInt(investorDetailsVO.getStatus());
        String redirectTo = EMPTY_STRING;
        if (relationStatus == EnumAdvisorMappingStatus.NO_RELATION
                || relationStatus == EnumAdvisorMappingStatus.INVITE_SENT
                || relationStatus == EnumAdvisorMappingStatus.CONTRACT_SIGNED
                || relationStatus == EnumAdvisorMappingStatus.INVITE_RECIEVED
                || relationStatus == EnumAdvisorMappingStatus.INVITE_DECLINED
                || relationStatus == EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT
                || relationStatus == EnumAdvisorMappingStatus.IPS_ACCEPTED) {
            redirectTo = "/pages/investorprofile?faces-redirect=true";
        } else if (relationStatus == EnumAdvisorMappingStatus.INVITE_ACCEPTED
                || relationStatus == EnumAdvisorMappingStatus.CONTRACT_REVIEW
                || relationStatus == EnumAdvisorMappingStatus.CONTRACT_SENT) {
            redirectTo = "/pages/advisory_service_contract?faces-redirect=true";
        } else if(relationStatus == EnumAdvisorMappingStatus.RESPONSE_RECIEVED || relationStatus == EnumAdvisorMappingStatus.IPS_CREATED){
            redirectTo = "/pages/investment_policy_statement_create?faces-redirect=true";
        } else if(relationStatus == EnumAdvisorMappingStatus.IPS_SHARED || relationStatus == EnumAdvisorMappingStatus.IPS_REVIEWED){
            redirectTo = "/pages/assign_portfolio?faces-redirect=true";            
        } else {
            redirectTo = "/pages/investorprofile?faces-redirect=true";
        }
        return redirectTo;
    }
    
}
