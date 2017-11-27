/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.bao.RegistrationBAO;
import com.gtl.mmf.report.util.RegistrationPdfDataPreparation;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.ALL;
import static com.gtl.mmf.service.util.IConstants.COM_GTL_MMF_PAGE_LIST_SIZE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.MANDATEFORM;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.REGISTRATION_MAIL;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.FailedMailDetailsVO;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationDataProcessingVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author trainee7
 */
@Named("failedMailListController")
@Scope("view")
public class FailedMailListController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.FailedMailListController");
    private static final String MAIL_TYPE_SELECTED = "mailTypeSelected";
    private static String SEARCH_TEXT = "searchText";
    @Autowired
    private IUserProfileBAO userProfileBAO;

    @Autowired
    private RegistrationBAO registrationBAO;
    private List<FailedMailDetailsVO> failedMailDetails;
    private boolean selectAllFlag;
    private boolean multipleSelect;
    private Map<String, String> mailTypeList;
    private String mailTypeSelected;
    private String searchText;
    //for pagination
    private Integer totalUsers;
    private int maximumItemsToDisplay;
    private boolean allowNext;
    private boolean allowPrevious;
    private int pageIndex;
    private int pages;
    private static final int DEFAULT_PAGE_INDEX = 1;
    private List<FailedMailDetailsVO> failedMailmodel;
    private String redirectionUrl;

    @PostConstruct
    public void afterInit() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        maximumItemsToDisplay = Integer.parseInt(LookupDataLoader.getConfigParamList().get(COM_GTL_MMF_PAGE_LIST_SIZE));
        this.pageIndex = DEFAULT_PAGE_INDEX;
        if (storedValues != null) {
            if (storedValues.get(MAIL_TYPE_SELECTED) != null) {
                this.mailTypeSelected = (String) storedValues.get(MAIL_TYPE_SELECTED);
            } else {
                this.mailTypeSelected = ALL;
            }
        } else {
            this.mailTypeSelected = ALL;
        }
        this.searchText = EMPTY_STRING;
        this.mailTypeList = LookupDataLoader.getMailTypeList();
        this.failedMailDetails = userProfileBAO.getFailedMailList(this.mailTypeSelected, searchText, true);
        this.totalUsers = this.failedMailDetails.size();
        this.getPage();
        this.updateModel();
        this.checkButtonStatus();
    }

    public IUserProfileBAO getUserProfileBAO() {
        return userProfileBAO;
    }

    public void setUserProfileBAO(IUserProfileBAO userProfileBAO) {
        this.userProfileBAO = userProfileBAO;
    }

    public RegistrationBAO getRegistrationBAO() {
        return registrationBAO;
    }

    public void setRegistrationBAO(RegistrationBAO registrationBAO) {
        this.registrationBAO = registrationBAO;
    }

    public List<FailedMailDetailsVO> getFailedMailDetails() {
        if (failedMailDetails == null) {
            updateModel();
        }
        return failedMailDetails;
    }

    public void setFailedMailDetails(List<FailedMailDetailsVO> failedMailDetails) {
        this.failedMailDetails = failedMailDetails;
    }

    public boolean isSelectAllFlag() {
        return selectAllFlag;
    }

    public void setSelectAllFlag(boolean selectAllFlag) {
        this.selectAllFlag = selectAllFlag;
    }

    public boolean isMultipleSelect() {
        return multipleSelect;
    }

    public void setMultipleSelect(boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
    }

    public Map<String, String> getMailTypeList() {
        return mailTypeList;
    }

    public void setMailTypeList(Map<String, String> mailTypeList) {
        this.mailTypeList = mailTypeList;
    }

    public String getMailTypeSelected() {
        return mailTypeSelected;
    }

    public void setMailTypeSelected(String mailTypeSelected) {
        this.mailTypeSelected = mailTypeSelected;
    }

    /**
     * method to update failedMailDetails based on changed mailType value
     *
     * @param event object return void
     */
    public void mailTypeChangeListener(AjaxBehaviorEvent event) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.selectAllFlag = false;
        this.failedMailDetails = userProfileBAO.getFailedMailList(this.mailTypeSelected, searchText, true);
        this.multipleSelect = false;
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(MAIL_TYPE_SELECTED, this.mailTypeSelected);
        storedValues.put(SEARCH_TEXT, this.searchText);
        ec.getSessionMap().put(STORED_VALUES, storedValues);
        this.pageIndex = DEFAULT_PAGE_INDEX;
        this.totalUsers = this.failedMailDetails.size();
        getPage();
        updateModel();
        checkButtonStatus();
    }

    /**
     * method to update rendering flag of regenerateAll button based on
     * selectAll value return void
     */
    public void selectAllChangeListener() {
        this.multipleSelect = selectAllFlag;
        if (!this.failedMailDetails.isEmpty()) {
            for (FailedMailDetailsVO sendMailFailedListDetailsVO : this.failedMailDetails) {
                sendMailFailedListDetailsVO.setChecked(selectAllFlag);
            }
            this.totalUsers = this.failedMailDetails.size();
            updateModel();
            checkButtonStatus();
        }
    }

    /**
     * method to update regenerated mail list based on selected mail
     *
     * @param mailDetails - VO containing failed mail details\ return void
     */
    public void mailSelectChangeListener(FailedMailDetailsVO mailDetails) {
        mailDetails.setChecked(!mailDetails.isChecked());
        List<FailedMailDetailsVO> selectedList = filterSelectedList(this.failedMailDetails);
        this.multipleSelect = (!selectedList.isEmpty());
    }

    /**
     * method to filter selected mails only
     *
     * @param list- all failed mails list
     * @return List<FailedMailDetailsVO>
     */
    public List<FailedMailDetailsVO> filterSelectedList(List<FailedMailDetailsVO> list) {
        List<FailedMailDetailsVO> filterdList = new ArrayList<FailedMailDetailsVO>();
        for (FailedMailDetailsVO sendMailFailedListDetailsVO : list) {
            if (sendMailFailedListDetailsVO.isChecked()) {
                filterdList.add(sendMailFailedListDetailsVO);
            }
        }
        return filterdList;
    }

    /**
     * method for regenerating single selected mail
     *
     * @param mailDetails -details for regenerating mail
     * @return void
     */
    public void reSendFailedMail(FailedMailDetailsVO mailDetails) {
        List<FailedMailDetailsVO> reGenerteMailList = new ArrayList<FailedMailDetailsVO>();
        reGenerteMailList.add(mailDetails);
        List<FailedMailDetailsVO> failedMailRegistrationList = userProfileBAO.reSendMail(reGenerteMailList);
        createPdfForMailFailed(failedMailRegistrationList);
    }

    private void createPdfForMailFailed(List<FailedMailDetailsVO> failedMailRegistrationList) {

        if (failedMailRegistrationList != null) {
            if (!failedMailRegistrationList.isEmpty()) {
                RegistrationPdfDataPreparation pdfPreparation = new RegistrationPdfDataPreparation();
                for (FailedMailDetailsVO mailDetailsForPdf : failedMailRegistrationList) {
                    RegistrationDataProcessingVo pdfDataPreparation
                            = mailDetailsForPdf.getRegistrationDataProcessing();
                    MandateVo mandateVo = registrationBAO.createMandateFormData(pdfDataPreparation.
                            getMandateVo(),pdfDataPreparation.getRegistrationVo().getRegId());
                    pdfDataPreparation.setMandateVo(mandateVo);
                    if (!Boolean.valueOf(mailDetailsForPdf.getUserType())
                            && mailDetailsForPdf.getEmail_purpose().equalsIgnoreCase(REGISTRATION_MAIL)) {
                        RegistrationVo registrationVo = registrationBAO.createRegistrationPdfData(pdfDataPreparation.getRegistrationVo());
                        pdfDataPreparation.setRegistrationVo(registrationVo);
                        pdfPreparation.createRegistrationPdfData(pdfDataPreparation, true);
                    } else if (mailDetailsForPdf.getEmail_purpose().equalsIgnoreCase(MANDATEFORM)) {
                        pdfPreparation.createRegistrationPdfData(pdfDataPreparation, false);
                    }

//                    else {
//                        pdfPreparation.createAdvisorMandateData(mailDetailsForPdf.getRegId(), mandateVo);
//                    }
                    userProfileBAO.reSendMailForFaildMail(mailDetailsForPdf);
                }
            } else {
                LOGGER.info(" Mail list without pdf documents to be regenerated is empty .Nothing to process");
            }
        }
    }

    /**
     * method for regenerating all or multiple failed mail
     *
     * @return void
     */
    public void resendMultipleFailedMail() {
        List<FailedMailDetailsVO> selectedList = filterSelectedList(this.failedMailDetails);
        List<FailedMailDetailsVO> registrationVoList = userProfileBAO.reSendMail(selectedList);
        createPdfForMailFailed(registrationVoList);
    }

    /**
     * action method - for update failed mail list after sending mail on each
     * button click
     *
     * @return EMPTY_STRING
     */
    public String updateFailedMailDetails() {
        this.failedMailDetails = userProfileBAO.getFailedMailList(this.mailTypeSelected, searchText, true);
        this.selectAllFlag = false;
        this.multipleSelect = false;
        this.totalUsers = this.failedMailDetails.size();
        getPage();
        updateModel();
        checkButtonStatus();
        return EMPTY_STRING;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void searchTextChangeListener(ValueChangeEvent e) {
        this.pageIndex = DEFAULT_PAGE_INDEX;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String searchTextOnEnter = e.getNewValue().toString();
        this.failedMailDetails = userProfileBAO.getFailedMailList(ALL, searchTextOnEnter, true);
        this.totalUsers = this.failedMailDetails.size();
        getPage();
        updateModel();
        checkButtonStatus();
    }

    // code for pagination
    public int getMaximumItemsToDisplay() {
        return maximumItemsToDisplay;
    }

    public void setMaximumItemsToDisplay(int maximumItemsToDisplay) {
        this.maximumItemsToDisplay = maximumItemsToDisplay;
    }

    public boolean isAllowNext() {
        return allowNext;
    }

    public void setAllowNext(boolean allowNext) {
        this.allowNext = allowNext;
    }

    public boolean isAllowPrevious() {
        return allowPrevious;
    }

    public void setAllowPrevious(boolean allowPrevious) {
        this.allowPrevious = allowPrevious;
    }

    public void checkButtonStatus() {
        this.allowNext = this.failedMailmodel.size() < totalUsers
                && this.pageIndex != this.pages;
        this.allowPrevious = this.pageIndex != DEFAULT_PAGE_INDEX && DEFAULT_PAGE_INDEX < this.pageIndex && this.pageIndex <= this.pages; 
    }

    public List<FailedMailDetailsVO> getFailedMailmodel() {
        return failedMailmodel;
    }

    public void setFailedMailmodel(List<FailedMailDetailsVO> failedMailmodel) {
        this.failedMailmodel = failedMailmodel;
    }

    public List<FailedMailDetailsVO> fetchCurrentList(int from, int to) {
        return failedMailDetails.subList(from, to);
    }

    public int getListSize() {
        return this.failedMailDetails.size();
    }

    public void updateModel() {
        int fromIndex = getFirst();
        int toIndex = getFirst() + this.maximumItemsToDisplay;

        if (toIndex > this.totalUsers) {
            toIndex = this.totalUsers;
        }

        setFailedMailmodel(fetchCurrentList(fromIndex, toIndex));
    }

    public void next() {
        if (this.pageIndex < pages) {
            this.pageIndex++;
        }
        updateModel();
        checkButtonStatus();
    }

    public void prev() {
        if (this.pageIndex > 1) {
            this.pageIndex--;
        }
        updateModel();
        checkButtonStatus();
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getFirst() {
        return (pageIndex * maximumItemsToDisplay) - maximumItemsToDisplay;
    }

    public void getPage() {
        if (this.maximumItemsToDisplay > ZERO) {
            this.pages = this.maximumItemsToDisplay <= ZERO ? ONE : this.totalUsers / this.maximumItemsToDisplay;

            if (this.totalUsers % this.maximumItemsToDisplay > ZERO) {
                this.pages++;
            }

            if (this.pages == ZERO) {
                this.pages = ONE;
            }
        } else {
            this.maximumItemsToDisplay = ONE;
            this.pages = ONE;
        }
    }
    
    public void onUserSelect() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        int selectedUserIndex = Integer.valueOf(requestParameterMap.get("index")).intValue();
        FailedMailDetailsVO selectedUser = this.failedMailmodel.get(selectedUserIndex);
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put("selectedUser", selectedUser);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
        if(selectedUser.isSendMail()){
            FacesContext.getCurrentInstance().addMessage("frm_sendmail_failed_list", new FacesMessage("Mail not verified / Data already submitted."));
            redirectionUrl = EMPTY_STRING;
        } else if (selectedUser.getUserType().equalsIgnoreCase(ADVISOR)) {
            redirectionUrl = "/pages/admin/partialAdvisor?faces-redirect=true";
        } else {
            redirectionUrl = "/pages/admin/partialUser?faces-redirect=true";
        }
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }
    
}
