/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import static com.gtl.mmf.service.util.IConstants.ACTIVE_VAL;
import static com.gtl.mmf.service.util.IConstants.COM_GTL_MMF_PAGE_LIST_SIZE;
import static com.gtl.mmf.service.util.IConstants.STATUS_VAL;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.YES;

import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.UserDetailsVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 07960
 */
@Named("newUserListBean")
@Scope("view")
public class NewUserListBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.NewUserListBean");
    private static String SEARCH_TEXT = "searchText";
    private static String USER_TYPE_SELECTED = "userTypeSelected";
    @Autowired
    private IUserProfileBAO userProfileBAO;
    private List<UserDetailsVO> newuserList;
    private Map<String, String> userTypeList;
    private Map<String, Integer> userStatus;
    private String userTypeSelected;
    private String searchText;
    private long totalUsers;
    private int currentListIndex;
    private int maximumItemsToDisplay;
    private boolean allowNext;
    private boolean allowPrevious;
    private Integer userSelected;
    private String redirectionUrl;

    @PostConstruct
    public void postConstMethod() {
        maximumItemsToDisplay = Integer.valueOf(LookupDataLoader.getConfigParamList().get(COM_GTL_MMF_PAGE_LIST_SIZE)).intValue();
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        this.userSelected = ACTIVE_VAL;
        if (storedValues != null) {
            if (storedValues.get("SelectedStatus") != null) {
                this.userSelected = (Integer) storedValues.get("SelectedStatus");
            } else {
                this.userSelected = STATUS_VAL;
            }
            if (storedValues.get(SEARCH_TEXT) != null) {
                this.searchText = (String) storedValues.get(SEARCH_TEXT);
            } else {
                this.searchText = EMPTY_STRING;
            }
            if (storedValues.get(USER_TYPE_SELECTED) != null) {
                this.userTypeSelected = (String) storedValues.get(USER_TYPE_SELECTED);
            } else {
                this.userTypeSelected = "IorA";
            }
        } else {
            this.searchText = EMPTY_STRING;
            this.userTypeSelected = "IorA";
        }
        this.currentListIndex = ZERO;
        this.userTypeList = LookupDataLoader.getUserTypeList();
        this.userStatus = LookupDataLoader.getUserStatusList();
        this.newuserList = userProfileBAO.listNewUsers(userTypeSelected, searchText, currentListIndex, maximumItemsToDisplay, userSelected);
        this.totalUsers = userProfileBAO.sizeOfUsersList(userTypeSelected, searchText);
        this.checkButtonStatus();
    }

    public void onClickNext(ActionEvent event) {
        if (this.allowNext) {
            this.currentListIndex = this.currentListIndex + this.maximumItemsToDisplay;
            this.newuserList = userProfileBAO.listNewUsers(userTypeSelected, searchText, currentListIndex, maximumItemsToDisplay, userSelected);
            this.checkButtonStatus();
        }
    }

    public void onClickPrevious(ActionEvent event) {
        if (this.allowPrevious) {
            this.currentListIndex = this.currentListIndex - this.maximumItemsToDisplay;
            this.newuserList = userProfileBAO.listNewUsers(userTypeSelected, searchText, currentListIndex, maximumItemsToDisplay, userSelected);
            this.checkButtonStatus();
        }
    }

    public void checkButtonStatus() {
        if ((this.currentListIndex + this.maximumItemsToDisplay) < totalUsers) {
            this.allowNext = true;
        } else {
            this.allowNext = false;
        }
        if (this.currentListIndex != ZERO) {
            this.allowPrevious = true;
        } else {
            this.allowPrevious = false;
        }
    }

    public void userTypeChangeListener(ValueChangeEvent e) {
        String userType = e.getNewValue().toString();
        this.currentListIndex = ZERO;
        this.newuserList = userProfileBAO.listNewUsers(userType, searchText, currentListIndex, maximumItemsToDisplay, userSelected);
        this.totalUsers = userProfileBAO.sizeOfUsersList(userType, searchText);
        this.checkButtonStatus();
    }

    public void userStatusChangeListener(ValueChangeEvent e) {
        int userSelectedStat = Integer.parseInt(e.getNewValue().toString());
        this.currentListIndex = ZERO;
        this.newuserList = userProfileBAO.listNewUsers(this.userTypeSelected, searchText, currentListIndex, maximumItemsToDisplay, userSelectedStat);
        this.totalUsers = userProfileBAO.sizeOfUsersList(this.userTypeSelected, searchText);
        this.checkButtonStatus();
    }

    public void searchTextChangeListener(ValueChangeEvent e) {
        String searchTextOnEnter = e.getNewValue().toString();
        this.currentListIndex = ZERO;
        this.newuserList = userProfileBAO.listNewUsers(this.userTypeSelected, searchTextOnEnter, currentListIndex, maximumItemsToDisplay, userSelected);
        this.totalUsers = userProfileBAO.sizeOfUsersList(userTypeSelected, searchTextOnEnter);
        this.checkButtonStatus();
    }

    public void onUserSelect() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        int selectedUserIndex = Integer.valueOf(requestParameterMap.get("index")).intValue();
        UserDetailsVO selectedUser = this.newuserList.get(selectedUserIndex);
        UserDetailsVO selectedUserFilled = (UserDetailsVO) userProfileBAO.getSelectedUser(selectedUser);
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put("selectedUser", selectedUserFilled);
        storedValues.put(USER_TYPE_SELECTED, this.userTypeSelected);
        storedValues.put(SEARCH_TEXT, this.searchText);
        storedValues.put("SelectedStatus", this.userSelected);
        ec.getSessionMap().put(STORED_VALUES, storedValues);
        LOGGER.log(Level.INFO, "Admin selected user from list with reg id {0}", selectedUser.getMasterApplicant().getRegistrationId());
        if (selectedUser.getUserType().equalsIgnoreCase(YES)) {
            redirectionUrl = "/pages/admin/createAdvisoruser?faces-redirect=true";
        } else {
            redirectionUrl = "/pages/admin/createuser?faces-redirect=true";
        }

        //}
    }

    public String getRedirectionURL() {
        return redirectionUrl;
    }

    public IUserProfileBAO getUserProfileBAO() {
        return userProfileBAO;
    }

    public void setUserProfileBAO(IUserProfileBAO userProfileBAO) {
        this.userProfileBAO = userProfileBAO;
    }

    public Map<String, String> getUserTypeList() {
        return userTypeList;
    }

    public void setUserTypeList(Map<String, String> userTypeList) {
        this.userTypeList = userTypeList;
    }

    public String getUserTypeSelected() {
        return userTypeSelected;
    }

    public void setUserTypeSelected(String userTypeSelected) {
        this.userTypeSelected = userTypeSelected;
    }

    public List<UserDetailsVO> getNewuserList() {
        return newuserList;
    }

    public void setNewuserList(List<UserDetailsVO> newuserList) {
        this.newuserList = newuserList;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
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

    public Map<String, Integer> getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Map<String, Integer> userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(Integer userSelected) {
        this.userSelected = userSelected;
    }

}
