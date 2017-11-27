/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IInvestorNetworkViewBAO;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import static com.gtl.mmf.service.util.IConstants.ADVISORS;
import static com.gtl.mmf.service.util.IConstants.AGGRESSSIVE;
import static com.gtl.mmf.service.util.IConstants.AVG_CLIENT_RATING;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.CONSERVATIVE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.INVESTOR_NETWORK_VIEW;
import static com.gtl.mmf.service.util.IConstants.MODERATE;
import static com.gtl.mmf.service.util.IConstants.MODERATELY_AGGRESSIVE;
import static com.gtl.mmf.service.util.IConstants.MODERATELY_CONSERVATIVE;
import static com.gtl.mmf.service.util.IConstants.NO_LINKEDIN_CONNECTION;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_OUT_PERFORMANCE;
import static com.gtl.mmf.service.util.IConstants.RETURN_ONE_YEAR;
import static com.gtl.mmf.service.util.IConstants.RETURN_THREE_MONTHS;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 07958
 */
@Component("investorNetworkViewController")
@Scope("view")
public class InvestorNetworkViewController {

    private List<AdvisorDetailsVO> allAdvisorDetails;
    private List<AdvisorDetailsVO> listAdvisorDetails;
    @Autowired
    private IInvestorNetworkViewBAO investorNetworkViewBAO;
    private int customerId;
    private String searchText;
    private String sortBySelected;
    private String filterSelected;
    private Map<String, String> sortByList;
    private Map<String, String> filter;
    Boolean canDisableInvite = false;

    @PostConstruct
    public void afterinit() {
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext
                .getCurrentInstance().getExternalContext().getSessionMap()
                .get(USER_SESSION);
        if (userSessionBean != null) {
            this.customerId = userSessionBean.getUserId();
            userSessionBean.setFromURL(INVESTOR_NETWORK_VIEW);
        }
        allAdvisorDetails = investorNetworkViewBAO.allAdvisors(customerId,
                userSessionBean.getAccessToken(), userSessionBean.isLinkedInConnected());
        sortByList = LookupDataLoader.getSortByInvestorNetwork();
        filter = LookupDataLoader.getInvestorNetworkFilter();
        sortBySelected = CONNECTION_LEVEL;
        filterSelected = ADVISORS;
        searchText = EMPTY_STRING;
        populateNetworkView(searchText, filterSelected, sortBySelected);
    }

    public void searchTextChangeListener(ValueChangeEvent e) {
        populateNetworkView(e.getNewValue().toString(), filterSelected,
                sortBySelected);
    }

    public void filterChangeListener(ValueChangeEvent e) {
        populateNetworkView(searchText, e.getNewValue().toString(),
                sortBySelected);
    }

    public void sortByChangeListener(ValueChangeEvent e) {
        populateNetworkView(searchText, filterSelected, e.getNewValue()
                .toString());
    }

    public void populateNetworkView(String searchText, String filterOn,
            String sortBy) {
        listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
        listAdvisorDetails.addAll(allAdvisorDetails);
        this.generateSearchResult(searchText, listAdvisorDetails);
        this.filterList(filterOn, listAdvisorDetails);
        this.sortNetworkView(sortBy, listAdvisorDetails);
    }

    public String onSelectAdvisor() {
        String redirectTo = EMPTY_STRING;
        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        int selectedAdvisorIndex = Integer.valueOf(
                requestParameterMap.get("index")).intValue();
        AdvisorDetailsVO selectedAdvisor = listAdvisorDetails
                .get(selectedAdvisorIndex);

        // to allow investor to send invitation
        selectedAdvisor.setCanDisableInvite(canDisableInvite);
        Map<String, Object> sessionMap = ec.getSessionMap();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(SELECTED_ADVISOR, selectedAdvisor);
        sessionMap.put(STORED_VALUES, storedValues);
        EnumCustomerMappingStatus relationStatus = EnumCustomerMappingStatus
                .fromInt(selectedAdvisor.getStatus());
        if (relationStatus == EnumCustomerMappingStatus.NO_RELATION
                || relationStatus == EnumCustomerMappingStatus.INVITE_SENT
                || relationStatus == EnumCustomerMappingStatus.INVITE_RECIEVED
                || relationStatus == EnumCustomerMappingStatus.INVITE_ACCEPTED
                || relationStatus == EnumCustomerMappingStatus.INVITE_DECLINED
                || relationStatus == EnumCustomerMappingStatus.WITHDRAW
                || relationStatus == EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED
                || relationStatus == EnumCustomerMappingStatus.ADVISOR_WITHDRAW
                || relationStatus == EnumCustomerMappingStatus.CONTRACT_TERMINATED) {
            redirectTo = "/pages/investmentadvisorprofile?faces-redirect=true";
        } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_REVIEW
                || relationStatus == EnumCustomerMappingStatus.CONTRACT_RECIEVED) {
            redirectTo = "/pages/advisory_service_contract?faces-redirect=true";
        } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_SIGNED) {
            redirectTo = "/pages/investordashboard?faces-redirect=true";
        } else if (relationStatus == EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED) {
        	redirectTo = "/pages/v2/riskProfile.jsp?faces-redirect=true";
            //redirectTo = "/pages/investor_questionnaire?faces-redirect=true";//-SumeetChanges
        } else if (relationStatus == EnumCustomerMappingStatus.IPS_SHARED) {
            redirectTo = "/pages/investment_policy_statement_view?faces-redirect=true";
        } else if (relationStatus == EnumCustomerMappingStatus.REBALANCE_INITIATED
                || relationStatus == EnumCustomerMappingStatus.ORDER_PLACED_IN_OMS
                || relationStatus == EnumCustomerMappingStatus.IPS_ACCEPTED) {
            redirectTo = "/pages/investmentadvisorprofile?faces-redirect=true";
        }
        return redirectTo;
    }

    private void sortNetworkView(String sortBy,
            List<AdvisorDetailsVO> listToSort) {
        if (sortBy.equalsIgnoreCase(CONNECTION_LEVEL)) {
            Collections.sort(listToSort, new Comparator<AdvisorDetailsVO>() {
                public int compare(AdvisorDetailsVO o1, AdvisorDetailsVO o2) {
                    int advisorConnection1 = Integer.parseInt(o1
                            .getConnectionLevel().equals(
                                    CONNECTION_LEVEL_DEFAULT) ? "99" : o1
                            .getConnectionLevel());
                    int advisorConnection2 = Integer.parseInt(o2
                            .getConnectionLevel().equals(
                                    CONNECTION_LEVEL_DEFAULT) ? "99" : o2
                            .getConnectionLevel());
                    return advisorConnection1 - advisorConnection2;
                }
            });
        } else if (sortBy.equalsIgnoreCase(AVG_CLIENT_RATING)) {
            Collections.sort(listToSort, new Comparator<AdvisorDetailsVO>() {
                public int compare(AdvisorDetailsVO o1, AdvisorDetailsVO o2) {
                    return (o2.getAvgClientRating() - o1.getAvgClientRating() > ZERO ? ONE
                            : -1);
                }
            });
        } else if (sortBy.equalsIgnoreCase(PORTFOLIO_OUT_PERFORMANCE)) {
            Collections.sort(listToSort, new Comparator<AdvisorDetailsVO>() {
                public int compare(AdvisorDetailsVO o1, AdvisorDetailsVO o2) {
                    return (o2.getOutPerformance() - o1.getOutPerformance() > ZERO ? ONE
                            : -1);
                }
            });
        } else if (sortBy.equalsIgnoreCase(RETURN_ONE_YEAR)) {
            Collections.sort(listToSort, new Comparator<AdvisorDetailsVO>() {
                public int compare(AdvisorDetailsVO o1, AdvisorDetailsVO o2) {
                    return (o2.getMaxReturnsOneYear()
                            - o1.getMaxReturnsOneYear() > ZERO ? ONE : -1);
                }
            });
        } else if (sortBy.equalsIgnoreCase(NO_LINKEDIN_CONNECTION)) {
            Collections.sort(listToSort, new Comparator<AdvisorDetailsVO>() {
                public int compare(AdvisorDetailsVO o1, AdvisorDetailsVO o2) {
                    Boolean b1 = o1.isLinkedInConnected();
                    Boolean b2 = o2.isLinkedInConnected();
                    return  b1.compareTo(b2);
                }
            });
        } else if (sortBy.equalsIgnoreCase(RETURN_THREE_MONTHS)){
            Collections.sort(listToSort, new Comparator<AdvisorDetailsVO>() {
                public int compare(AdvisorDetailsVO o1, AdvisorDetailsVO o2) {
                    return (o2.getMaxReturnsNintyDays()
                            - o1.getMaxReturnsNintyDays() > ZERO ? ONE : -1);
                }
            });
            
        }
    }

    private void generateSearchResult(String searchText,
            List<AdvisorDetailsVO> listToSearch) {
        listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
        for (AdvisorDetailsVO advisorDetailsVO : listToSearch) {
            Integer relationStatusValue = advisorDetailsVO.getStatus();
            EnumCustomerMappingStatus relationStatus = EnumCustomerMappingStatus
                    .fromInt(relationStatusValue);

            // checking wheather invester is signed contract with any advisor
            if (relationStatus == EnumCustomerMappingStatus.CONTRACT_SIGNED
                    || relationStatus == EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED
                    || relationStatus == EnumCustomerMappingStatus.RESPONSE_REVIEW
                    || relationStatus == EnumCustomerMappingStatus.RESPONSE_SENT
                    || relationStatus == EnumCustomerMappingStatus.IPS_ACCEPTED
                    || relationStatus == EnumCustomerMappingStatus.IPS_SHARED
                    || relationStatus == EnumCustomerMappingStatus.IPS_CREATED
                    || relationStatus == EnumCustomerMappingStatus.IPS_REVIEWED
                    || relationStatus == EnumCustomerMappingStatus.REBALANCE_INITIATED
                    || relationStatus == EnumCustomerMappingStatus.ORDER_PLACED_IN_OMS) {
                canDisableInvite = true;
            }

            if (StringUtils.containsIgnoreCase(advisorDetailsVO.getFirstName(),
                    searchText)
                    || StringUtils.containsIgnoreCase(
                            advisorDetailsVO.getWorkOrganization(), searchText)
                    || StringUtils.containsIgnoreCase(
                            advisorDetailsVO.getJobTitle(), searchText)
                    || StringUtils.containsIgnoreCase(
                            advisorDetailsVO.getStatusValue(), searchText)) {
                listAdvisorDetails.add(advisorDetailsVO);
            }
        }
    }

    private void filterList(String filterOn, List<AdvisorDetailsVO> listToFilter) {
//        if (filterOn.equalsIgnoreCase(FRIENDS)) {
//            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
//            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
//                if (advisorDetailsVO.getConnectionLevel().equalsIgnoreCase(
//                        CONNECTION_LEVEL_FRIENDS)) {
//                    listAdvisorDetails.add(advisorDetailsVO);
//                }
//            }
//        } else if (filterOn.equalsIgnoreCase(FRIENDS_OF_FREINDS)) {
//            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
//            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
//                if (advisorDetailsVO.getConnectionLevel().equalsIgnoreCase(
//                        CONNECTION_LEVEL_FRIENDS_OF_FRIENDS)) {
//                    listAdvisorDetails.add(advisorDetailsVO);
//                }
//            }
//        } else if (filterOn.equalsIgnoreCase(ADVISORS)) {
//            listAdvisorDetails = listToFilter;
//        } else if (filterOn.equalsIgnoreCase(NO_LINKEDIN_CONNECTION)) {
//            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
//            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
//                if (!advisorDetailsVO.isLinkedInConnected()) {
//                    listAdvisorDetails.add(advisorDetailsVO);
//                }
//            }
//        }
        if (filterOn.equalsIgnoreCase(AGGRESSSIVE)) {
            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
                if (advisorDetailsVO.getPortfolioType()!= null
                        && advisorDetailsVO.getPortfolioType().equalsIgnoreCase(
                        AGGRESSSIVE)) {
                    listAdvisorDetails.add(advisorDetailsVO);
                }else{
                    listAdvisorDetails = listToFilter;
                }
            }
        } else if (filterOn.equalsIgnoreCase(MODERATELY_AGGRESSIVE)){
            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
                if (advisorDetailsVO.getPortfolioType()!= null
                        && advisorDetailsVO.getPortfolioType().equalsIgnoreCase(
                        MODERATELY_AGGRESSIVE)) {
                    listAdvisorDetails.add(advisorDetailsVO);
                }else{
                    listAdvisorDetails = listToFilter;
                }
            }
        } else if (filterOn.equalsIgnoreCase(MODERATE)){
            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
                if (advisorDetailsVO.getPortfolioType()!= null
                        && advisorDetailsVO.getPortfolioType().equalsIgnoreCase(
                        MODERATE)) {
                    listAdvisorDetails.add(advisorDetailsVO);
                }else{
                    listAdvisorDetails = listToFilter;
                }
            }
        } else if (filterOn.equalsIgnoreCase(MODERATELY_CONSERVATIVE)){
            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
                if (advisorDetailsVO.getPortfolioType()!= null
                        && advisorDetailsVO.getPortfolioType().equalsIgnoreCase(
                        MODERATELY_CONSERVATIVE)) {
                    listAdvisorDetails.add(advisorDetailsVO);
                }else{
                    listAdvisorDetails = listToFilter;
                }
            }
        } else if (filterOn.equalsIgnoreCase(CONSERVATIVE)){
            listAdvisorDetails = new ArrayList<AdvisorDetailsVO>();
            for (AdvisorDetailsVO advisorDetailsVO : listToFilter) {
                if (advisorDetailsVO.getPortfolioType()!= null
                        && advisorDetailsVO.getPortfolioType().equalsIgnoreCase(
                        CONSERVATIVE)) {
                    listAdvisorDetails.add(advisorDetailsVO);
                }else{
                    listAdvisorDetails = listToFilter;
                }
            }
        }
    }

    public List<AdvisorDetailsVO> getListAdvisorDetails() {
        return listAdvisorDetails;
    }

    public void setListAdvisorDetails(List<AdvisorDetailsVO> listAdvisorDetails) {
        this.listAdvisorDetails = listAdvisorDetails;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSortBySelected() {
        return sortBySelected;
    }

    public void setSortBySelected(String sortBySelected) {
        this.sortBySelected = sortBySelected;
    }

    public String getFilterSelected() {
        return filterSelected;
    }

    public void setFilterSelected(String filterSelected) {
        this.filterSelected = filterSelected;
    }

    public Map<String, String> getSortByList() {
        return sortByList;
    }

    public void setSortByList(Map<String, String> sortByList) {
        this.sortByList = sortByList;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
