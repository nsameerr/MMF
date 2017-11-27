/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IKitAllocationBAO;
import com.gtl.mmf.bao.IUserProfileBAO;
import static com.gtl.mmf.service.util.IConstants.ADMIN_NOTIFY_STATUS_CODE;
import static com.gtl.mmf.service.util.IConstants.COM_GTL_MMF_PAGE_LIST_SIZE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.NOTIFY_ADMIN;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.HolidayCalenderVo;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 09860
 */
@Named("holidayCalender")
@Scope("view")
public class HolidayCalenderController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.HolidayCalender");
    private List<HolidayCalenderVo> holidayList;
    @Autowired
    private IUserProfileBAO userProfileBAO;
    private Date holiday;
    private String event;
    private boolean editable;
    private boolean enableSave;
    private boolean status;
    // For implementing pagination
    private static final int DEFAULT_PAGE_INDEX = 1;
    private Integer totalCount;
    private int maximumItemsToDisplay;
    private boolean allowNext;
    private boolean allowPrevious;
    private int pageIndex;
    private int pages;
    private List<HolidayCalenderVo> holidayModel;
    @Autowired
    IKitAllocationBAO iKitAllocationBAO;

    @PostConstruct
    public void init() {
        maximumItemsToDisplay = Integer.parseInt(LookupDataLoader.getConfigParamList().get(COM_GTL_MMF_PAGE_LIST_SIZE));
        holidayList = userProfileBAO.getHolidayList();
        this.pageIndex = DEFAULT_PAGE_INDEX;
        this.totalCount = this.holidayList.size();
        this.getPage();
        this.updateModel();
        this.checkButtonStatus();
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

    public List<HolidayCalenderVo> fetchCurrentList(int from, int to) {
        return holidayList.subList(from, to);
    }

    public void updateModel() {
        int fromIndex = getFirst();
        int toIndex = getFirst() + this.maximumItemsToDisplay;

        if (toIndex > this.totalCount) {
            toIndex = this.totalCount;
        }

        setHolidayModel(fetchCurrentList(fromIndex, toIndex));
    }

    public void checkButtonStatus() {
        this.allowNext = this.holidayModel.size() < totalCount
                && this.pageIndex != this.pages;
//        this.allowPrevious = !(this.pageIndex != this.pages) && !(this.holidayModel.isEmpty())
//                && !(this.holidayList.size() <= maximumItemsToDisplay);
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

    public void checkForSave() {
        status = false;
        if (!holidayList.contains(getHoliday())) {
            status = true;
        } else {
            this.setHoliday(null);
            this.setEvent(EMPTY_STRING);
            this.setEnableSave(true);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Date already exist", "Date already exist"));
        }
    }

    public void saveHoliday() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        if (status) {
            HolidayCalenderVo calenderVo = new HolidayCalenderVo();
            calenderVo.setHoliday(holiday);
            calenderVo.setEvent(event);
            calenderVo.setEditable(false);
            userProfileBAO.saveHolidayList(calenderVo);
            this.holidayList.add(calenderVo);
            this.setEnableSave(false);
            this.totalCount = this.holidayList.size();
            this.getPage();
            this.updateModel();
            this.checkButtonStatus();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Data successfully saved", "Data successfully saved"));
        }
        if (storedValues != null && (Boolean) storedValues.get(NOTIFY_ADMIN) != null
                && (Boolean) storedValues.get(NOTIFY_ADMIN) && status) {
            Integer notificationStatusCode = (Integer) storedValues.get(ADMIN_NOTIFY_STATUS_CODE);
            iKitAllocationBAO.updateAdminNotificationStatus(notificationStatusCode);
            storedValues.remove(NOTIFY_ADMIN);
        }

    }

    public String editAction(HolidayCalenderVo calenderVo) {
        calenderVo.setEditable(true);
        return null;
    }

    public void update(HolidayCalenderVo editHoliday, int index) {
        userProfileBAO.saveHolidayList(editHoliday);
        HolidayCalenderVo selectedindex = this.holidayList.get(index);
        selectedindex.setEvent(editHoliday.getEvent());
        selectedindex.setHoliday(editHoliday.getHoliday());
        selectedindex.setEditable(false);
        this.getPage();
        this.updateModel();
        this.checkButtonStatus();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Data successfully saved", "Data successfully saved"));
    }

    public void delete(HolidayCalenderVo deleteHoliday, int index) {
        userProfileBAO.deleteHoliday(deleteHoliday);
        holidayList = userProfileBAO.getHolidayList();
        this.totalCount = this.holidayList.size();
        this.getPage();
        this.pageIndex = DEFAULT_PAGE_INDEX;
        this.updateModel();
        this.checkButtonStatus();
    }

    public List<HolidayCalenderVo> getHolidayList() {
        return holidayList;
    }

    public void setHolidayList(List<HolidayCalenderVo> holidayList) {
        this.holidayList = holidayList;
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

    public List<HolidayCalenderVo> getHolidayModel() {
        return holidayModel;
    }

    public void setHolidayModel(List<HolidayCalenderVo> holidayModel) {
        this.holidayModel = holidayModel;
    }

    public Date getHoliday() {
        return holiday;
    }

    public void setHoliday(Date holiday) {
        this.holiday = holiday;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEnableSave() {
        return enableSave;
    }

    public void setEnableSave(boolean enableSave) {
        this.enableSave = enableSave;
    }

    public String enableAddFlag() {
        this.setEnableSave(true);
        return null;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
