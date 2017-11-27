/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IAdvisorNetworkViewBAO;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_FRIENDS;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_FRIENDS_OF_FRIENDS;
import static com.gtl.mmf.service.util.IConstants.DATE_OF_JOINING;
import static com.gtl.mmf.service.util.IConstants.FRIENDS;
import static com.gtl.mmf.service.util.IConstants.FRIENDS_OF_FREINDS;
import static com.gtl.mmf.service.util.IConstants.INVESTORS;
import static com.gtl.mmf.service.util.IConstants.NETWORK_VIEW;
import static com.gtl.mmf.service.util.IConstants.STATUS;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.NO_LINKEDIN_CONNECTION;
import static com.gtl.mmf.service.util.IConstants.SHARED_CONNECTIONS;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.webapp.util.AdvisorRedirectionUtil;
import com.gtl.mmf.service.util.LookupDataLoader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Component("advisorNetworkViewController")
@Scope("view")
public class AdvisorNetworkViewController {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.AssignPortfolioController");
    private List<InvestorDetailsVO> allinvestorDetailsList;
    private List<InvestorDetailsVO> listInvestorDetails;
    @Autowired
    private IAdvisorNetworkViewBAO advisorNetworkViewBAO;
    private int advisorId;
    private String searchText;
    private String sortBySelected;
    private String filterSelected;
    private Map<String, String> sortByList;
    private Map<String, String> filter;
    private String redirectTo = EMPTY_STRING;
    private InvestorDetailsVO investorProfile;
    Boolean canDisableInvite = false;

    @PostConstruct
    public void afterinit() {
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        if (userSessionBean != null) {
            this.advisorId = userSessionBean.getUserId();
        }
        this.allinvestorDetailsList = advisorNetworkViewBAO.populateInvestorNetwork(this.advisorId, userSessionBean.getAccessToken(), userSessionBean.getLinkedInId(),
                userSessionBean.isLinkedInConnected());
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap();
        sessionMap.put(NETWORK_VIEW, allinvestorDetailsList);
        this.listInvestorDetails = allinvestorDetailsList;

        sortByList = LookupDataLoader.getSortByAdvisorNetwork();
        filter = LookupDataLoader.getAdvisorNetworkFilter();
        sortBySelected = CONNECTION_LEVEL;
        filterSelected = INVESTORS;
        searchText = EMPTY_STRING;
        populateNetworkView(searchText, filterSelected, sortBySelected);

    }

    public void searchTextChangeListener(ValueChangeEvent e) {
        populateNetworkView(e.getNewValue().toString(), filterSelected, sortBySelected);
    }

    public void filterChangeListener(ValueChangeEvent e) {
        populateNetworkView(searchText, e.getNewValue().toString(), sortBySelected);
    }

    public void sortByChangeListener(ValueChangeEvent e) {
        populateNetworkView(searchText, filterSelected, e.getNewValue().toString());
    }

    public void populateNetworkView(String searchText, String filterOn, String sortBy) {
        listInvestorDetails = new ArrayList<InvestorDetailsVO>();
        listInvestorDetails.addAll(allinvestorDetailsList);
        this.generateSearchResult(searchText, listInvestorDetails);
        this.filterList(filterOn, listInvestorDetails);
        this.sortNetworkView(sortBy, listInvestorDetails);
    }

    public String onSelectInvestor() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        int selectedInvestorIndex = Integer.valueOf(requestParameterMap.get("index")).intValue();
        InvestorDetailsVO selectedInvestor = listInvestorDetails.get(selectedInvestorIndex);
        // checking wheather invester is signed contract with any advisor
        Integer customerId = selectedInvestor.getCustomerId();
        boolean statusflag = advisorNetworkViewBAO.CheckStatusFlag(customerId);
        if (statusflag) {
            selectedInvestor.setCanDisableInvite(true);
        } else {
            selectedInvestor.setCanDisableInvite(false);
        }
        AdvisorRedirectionUtil advisorRedirectionUtil = new AdvisorRedirectionUtil();
        return redirectTo = advisorRedirectionUtil.advisorRredirectionPath(selectedInvestor);
    }

    private void sortNetworkView(String sortBy, List<InvestorDetailsVO> listToSort) {
        if (sortBy.equalsIgnoreCase(STATUS)) {
            Collections.sort(listToSort,
                    new Comparator<InvestorDetailsVO>() {
                public int compare(InvestorDetailsVO o1, InvestorDetailsVO o2) {
                    return o1.getStatusValue().compareTo(o2.getStatusValue());
                }
            });
        } else if (sortBy.equalsIgnoreCase(CONNECTION_LEVEL)) {
            Collections.sort(listToSort,
                    new Comparator<InvestorDetailsVO>() {
                public int compare(InvestorDetailsVO o1, InvestorDetailsVO o2) {
                    int investorConnection1 = Integer.parseInt(o1.getConnectionLevel()
                            .equals(CONNECTION_LEVEL_DEFAULT) ? "99" : o1.getConnectionLevel());
                    int investorConnection2 = Integer.parseInt(o2.getConnectionLevel()
                            .equals(CONNECTION_LEVEL_DEFAULT) ? "99" : o2.getConnectionLevel());
                    return investorConnection1 - investorConnection2;
                }
            });
        } else if (sortBy.equalsIgnoreCase(DATE_OF_JOINING)) {

            Collections.sort(listToSort,
                    new Comparator<InvestorDetailsVO>() {
                public int compare(InvestorDetailsVO o1, InvestorDetailsVO o2) {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = dateFormatter.parse(o1.getStartDate());
                        date2 = dateFormatter.parse(o2.getStartDate());
                    } catch (ParseException ex) {
                        Logger.getLogger(AdvisorNetworkViewController.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                    return date2.compareTo(date1);
                }
            });
        } //        else if (sortBy.equalsIgnoreCase(NO_LINKEDIN_CONNECTION)) {
        //            Collections.sort(listToSort, new Comparator<InvestorDetailsVO>() {
        //                public int compare(InvestorDetailsVO o1, InvestorDetailsVO o2) {
        //                    Boolean b1 = o1.isLinkedInConnected();
        //                    Boolean b2 = o2.isLinkedInConnected();
        //                    return b1.compareTo(b2);
        //                }
        //            });
        //        }
        else if (sortBy.equalsIgnoreCase(SHARED_CONNECTIONS)) {
            Collections.sort(listToSort, new Comparator<InvestorDetailsVO>() {
                public int compare(InvestorDetailsVO o1, InvestorDetailsVO o2) {
                    String b1 = o1.getConnectionShared();
                    String b2 = o2.getConnectionShared();
                    return b1.compareTo(b2);
                }
            });
        }
    }

    private void generateSearchResult(String searchText, List<InvestorDetailsVO> listToSearch) {

        listInvestorDetails = new ArrayList<InvestorDetailsVO>();
        for (InvestorDetailsVO investorDetailsVO : listToSearch) {
            if (StringUtils.containsIgnoreCase(investorDetailsVO.getFirstName(), searchText)
                    || StringUtils.containsIgnoreCase(investorDetailsVO.getWorkOrganization(), searchText)
                    || StringUtils.containsIgnoreCase(investorDetailsVO.getJobTitle(), searchText)
                    || StringUtils.containsIgnoreCase(investorDetailsVO.getStatusValue(), searchText)) {
                listInvestorDetails.add(investorDetailsVO);
            }
        }
    }

    private void filterList(String filterOn, List<InvestorDetailsVO> listToFilter) {
        if (filterOn.equalsIgnoreCase(FRIENDS)) {
            listInvestorDetails = new ArrayList<InvestorDetailsVO>();
            for (InvestorDetailsVO investorDetailsVO : listToFilter) {
                if (investorDetailsVO.getConnectionLevel().equals(CONNECTION_LEVEL_FRIENDS)) {
                    listInvestorDetails.add(investorDetailsVO);
                }
            }
        } else if (filterOn.equalsIgnoreCase(FRIENDS_OF_FREINDS)) {
            listInvestorDetails = new ArrayList<InvestorDetailsVO>();
            for (InvestorDetailsVO investorDetailsVO : listToFilter) {
                if (investorDetailsVO.getConnectionLevel().equals(CONNECTION_LEVEL_FRIENDS_OF_FRIENDS)) {
                    listInvestorDetails.add(investorDetailsVO);
                }
            }
        } else if (filterOn.equalsIgnoreCase(INVESTORS)) {
            listInvestorDetails = listToFilter;
        } else if (filterOn.equalsIgnoreCase(NO_LINKEDIN_CONNECTION)) {
            listInvestorDetails = new ArrayList<InvestorDetailsVO>();
            for (InvestorDetailsVO investorDetailsVO : listToFilter) {
                if (!investorDetailsVO.isLinkedInConnected()) {
                    listInvestorDetails.add(investorDetailsVO);
                }
            }
        }
    }

    public String selectInvestor() {
        String redirectPage = null;
        return redirectPage;
    }

    public List<InvestorDetailsVO> getListInvestorDetails() {
        return listInvestorDetails;
    }

    public void setListInvestorDetails(List<InvestorDetailsVO> listInvestorDetails) {
        this.listInvestorDetails = listInvestorDetails;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Map<String, String> getSortByList() {
        return sortByList;
    }

    public void setSortByList(Map<String, String> sortByList) {
        this.sortByList = sortByList;
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

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }
}
