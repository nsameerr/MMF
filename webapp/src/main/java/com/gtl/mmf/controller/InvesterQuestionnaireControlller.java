/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.gtl.mmf.bao.IAdvisoryServiceContractBAO;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.TWENTY;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.OptionValuesVO;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.util.StackTraceWriter;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

@Named("investerQuestionnaireControlller")
@Scope("view")
public class InvesterQuestionnaireControlller {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.InvesterQuestionnaireControlller");
    @Autowired
    private IAdvisoryServiceContractBAO advisoryServiceContractBAO;
    private Map<Integer, QuestionnaireVO> questionnnaireMap;
    private static final String PARAM_QUESTION_ID = "questionnaire_questionId";
    private static final String PARAM_OPTION_ID = "questionnaire_optionId";
    private static final String RE_DIRECT_PAGE = "/pages/investordashboard?faces-redirect=true";
    private static final String CURRENT_PAGE = "/pages/investor_questionnaire?faces-redirect=true";
    private String reDirectTo;
    private AdvisorDetailsVO advisorDetailsVO;
    private UserSessionBean userSessionBean;
    private Map<String, Object> storedValues;
    private Double allocatedFund;

    @PostConstruct
	public void afterinit() {
		questionnnaireMap = new LinkedHashMap<Integer, QuestionnaireVO>();
		QuestionnaireVO questionnaireVOforTwenty = null;
		for (Map.Entry<Integer, QuestionnaireVO> entry : LookupDataLoader
				.getQuestionnaire().entrySet()) {
			// putting question 20 to the end of the map
			
			if (entry.getValue().getQuestionId() != TWENTY) {
				QuestionnaireVO questionnaireVO = new QuestionnaireVO(entry
						.getValue().getQuestionId(), entry.getValue()
						.getQuestionName(), entry.getValue().getQstoptionId(),
						entry.getValue().getQstoptionValue(), entry.getValue()
								.getOptions(), entry.getValue().getImg_path());
				questionnnaireMap.put(entry.getKey(), questionnaireVO);
			} else {
				questionnaireVOforTwenty = new QuestionnaireVO(entry.getValue()
						.getQuestionId(), entry.getValue().getQuestionName(),
						entry.getValue().getQstoptionId(), entry.getValue()
								.getQstoptionValue(), entry.getValue()
								.getOptions(), entry.getValue().getImg_path());
			}

		}
        questionnnaireMap.put(TWENTY, questionnaireVOforTwenty);
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        userSessionBean.setFromURL("/pages/investor_questionnaire?faces-redirect=true");
        allocatedFund = advisoryServiceContractBAO.getInvestorFundDetail(userSessionBean.getUserId());
    }

    public String reDirectTo() {
        return reDirectTo;
    }

    public String currentPage() {
//        return CURRENT_PAGE;
        return RE_DIRECT_PAGE;
    }

    public Map<Integer, QuestionnaireVO> getQuestionnaireList() {
        return questionnnaireMap;
    }

    public void setQuestionnnaireMap(Map<Integer, QuestionnaireVO> questionnnaireMap) {
        this.questionnnaireMap = questionnnaireMap;
    }

    public void submitInvestorQuestionnaire() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String error;
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        advisorDetailsVO = ((AdvisorDetailsVO) storedValues.get(SELECTED_ADVISOR));
        boolean chkQusOptSet = Boolean.FALSE;
        for (Map.Entry<Integer, QuestionnaireVO> entry : questionnnaireMap.entrySet()) {
            if (entry.getValue().getQstoptionId() == ZERO) {
                chkQusOptSet = Boolean.TRUE;
                break;
            }
        }
        if (chkQusOptSet == Boolean.FALSE) {
            try {
                advisoryServiceContractBAO.submitQuestionnaire(questionnnaireMap, advisorDetailsVO,
                        LookupDataLoader.getPortfolioTypes(), userSessionBean.getFirstName() + SPACE + userSessionBean.getLastName(),allocatedFund);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InvesterQuestionnaireControlller.class.getName()).log(Level.SEVERE, null, ex);
            }
            reDirectTo = RE_DIRECT_PAGE;
        } else {
            error = "Select atleast one option for each question ";
            fc.addMessage("frm_questinnaire", new FacesMessage(SEVERITY_ERROR, error, null));
        }
    }

    public void processRadioValueChange() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        int questionId = params.get(PARAM_QUESTION_ID) == null ? ZERO : Integer.parseInt(params.get(PARAM_QUESTION_ID));
        int optionid = params.get(PARAM_OPTION_ID) == null ? ZERO : Integer.parseInt(params.get(PARAM_OPTION_ID));
        questionnnaireMap.get(questionId).setQstoptionId(optionid);
        for (OptionValuesVO optionValuesVO : questionnnaireMap.get(questionId).getOptions()) {
            if (optionValuesVO.getOptionId() == optionid) {
                questionnnaireMap.get(questionId).setQstoptionValue(optionValuesVO.getOptionvale());
                break;
            }
        }
    }
}
