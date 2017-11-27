/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IKitAllocationBAO;
import static com.gtl.mmf.service.util.IConstants.ADMIN_NOTIFY_STATUS_CODE;
import static com.gtl.mmf.service.util.IConstants.COM_GTL_MMF_PAGE_LIST_SIZE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.NOTIFY_ADMIN;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.RegKitVo;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 09860
 */
@Named("KitAllocationController")
@Scope("view")
public class KitAllocationController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.KitAllocationController");
    private Integer startValue;
    private Integer endValue;
    private List<RegKitVo> allocationList;
    @Autowired
    IKitAllocationBAO iKitAllocationBAO;
    private String redirectPage = EMPTY_STRING;
    private static final int DEFAULT_PAGE_INDEX = 1;
    private static final String ERROR_MSG = "To value should be greater than From value";
    private static final String NUMBER_EXIST = "Allocated serial number already exist.";
    private static final String DATA_SAVED = "New kit series added successfully. ";
    private boolean status;

    // For implementing pagination
    private Integer totalCount;
    private int maximumItemsToDisplay;
    private boolean allowNext;
    private boolean allowPrevious;
    private int pageIndex;
    private int pages;
    private List<RegKitVo> regKitmModel;

    @PostConstruct
    public void init() {
        maximumItemsToDisplay = Integer.parseInt(LookupDataLoader.getConfigParamList().get(COM_GTL_MMF_PAGE_LIST_SIZE));
        allocationList = iKitAllocationBAO.getClientRegistrationKitDetails();
        this.pageIndex = DEFAULT_PAGE_INDEX;
        this.totalCount = this.allocationList.size();
        this.getPage();
        this.updateModel();
        this.checkButtonStatus();
    }

    public void saveNewAllocation(ActionEvent event) {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        status = false;
        if (endValue > startValue && startValue != endValue) {
            // Checking whether the newly allocated value already exists or not.
            boolean exist = iKitAllocationBAO.checkIfNewAllocation(startValue, endValue);
            if (!exist) {
                // if not exist new allocation saving to db.
                RegKitVo regKitVo = new RegKitVo();
                regKitVo = iKitAllocationBAO.saveAllocationRange(startValue, endValue);
                allocationList.add(regKitVo);
                status = true;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, DATA_SAVED, DATA_SAVED));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, NUMBER_EXIST, NUMBER_EXIST));
            }
            if (storedValues != null && (Boolean) storedValues.get(NOTIFY_ADMIN) != null
                    && (Boolean) storedValues.get(NOTIFY_ADMIN) && status) {
                Integer notificationStatusCode = (Integer) storedValues.get(ADMIN_NOTIFY_STATUS_CODE);
                iKitAllocationBAO.updateAdminNotificationStatus(notificationStatusCode);
                storedValues.remove(NOTIFY_ADMIN);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_MSG, ERROR_MSG));
        }
    }

    public Integer getStartValue() {
        return startValue;
    }

    public void setStartValue(Integer startValue) {
        this.startValue = startValue;
    }

    public Integer getEndValue() {
        return endValue;
    }

    public void setEndValue(Integer endValue) {
        this.endValue = endValue;
    }

    public String getredirectPage() {
        this.totalCount = this.allocationList.size();
        this.getPage();
        this.updateModel();
        this.checkButtonStatus();
        return EMPTY_STRING;
    }

    public void setRedirectPage(String redirectPage) {
        this.redirectPage = redirectPage;
    }

    public List<RegKitVo> getAllocationList() {
        if (allocationList == null) {
            updateModel();
        }
        return allocationList;
    }

    public void setAllocationList(List<RegKitVo> allocationList) {
        this.allocationList = allocationList;
    }

    public IKitAllocationBAO getiKitAllocationBAO() {
        return iKitAllocationBAO;
    }

    public void setiKitAllocationBAO(IKitAllocationBAO iKitAllocationBAO) {
        this.iKitAllocationBAO = iKitAllocationBAO;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

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

    public List<RegKitVo> getRegKitmModel() {
        return regKitmModel;
    }

    public void setRegKitmModel(List<RegKitVo> regKitmModel) {
        this.regKitmModel = regKitmModel;
    }

    public void getPage() {
        if (this.maximumItemsToDisplay > ZERO) {
            this.pages = this.maximumItemsToDisplay <= ZERO ? ONE : this.totalCount / this.maximumItemsToDisplay;

            if (this.totalCount % this.maximumItemsToDisplay > ZERO) {
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

    public int getFirst() {
        return (pageIndex * maximumItemsToDisplay) - maximumItemsToDisplay;
    }

    public List<RegKitVo> fetchCurrentList(int from, int to) {
        return allocationList.subList(from, to);
    }

    public void updateModel() {
        int fromIndex = getFirst();
        int toIndex = getFirst() + this.maximumItemsToDisplay;

        if (toIndex > this.totalCount) {
            toIndex = this.totalCount;
        }

        setRegKitmModel(fetchCurrentList(fromIndex, toIndex));
    }

    public void checkButtonStatus() {
        this.allowNext = this.regKitmModel.size() < totalCount
                && this.pageIndex != this.pages;
//        this.allowPrevious = !(this.pageIndex != this.pages) && !(this.regKitmModel.isEmpty())
//                && !(this.allocationList.size() < maximumItemsToDisplay);
        this.allowPrevious = this.pageIndex != DEFAULT_PAGE_INDEX && DEFAULT_PAGE_INDEX < this.pageIndex && this.pageIndex <= this.pages; 
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
}
