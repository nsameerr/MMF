/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import com.gtl.mmf.service.util.LookupDataLoader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 09860
 */
@Named("adminSettingsController")
@Scope("view")
public class AdminSettingsController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.AdminSettingsController");
    private String key;
    private String value;
    private String redirectTo;
    private boolean enableUpdate = false;
    private LinkedHashMap<String, String> settingslist;
    @Autowired
    private IUserProfileBAO userProfileBAO;
    private static final String UPDATE_MSG = "Updated Successfully";
    private static final String UPDATE_FAILED_MSG = "Updation task failed.Please try again.";
    private String selectedKey;
    private boolean enableKey = false;
    private String type;

    @PostConstruct
    public void init() {
        settingslist = new LinkedHashMap<String, String>();
        settingslist = LookupDataLoader.getMmfsettings();
    }

    public void onClickUpdate() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        selectedKey = requestParameterMap.get("key");
        this.setEnableUpdate(true);
        this.setEnableKey(false);
        this.setKey(selectedKey);
        this.setValue(EMPTY_STRING);
    }

    public void updateSettings() {
        this.setEnableUpdate(false);
        int id = userProfileBAO.updateSettings(selectedKey, getValue(), false);
        if (id > 0) {
            LookupDataLoader.getMmfsettings().put(selectedKey, value);
            settingslist = LookupDataLoader.getMmfsettings();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, UPDATE_MSG, UPDATE_MSG));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, UPDATE_FAILED_MSG, UPDATE_FAILED_MSG));
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public boolean isEnableUpdate() {
        return enableUpdate;
    }

    public void setEnableUpdate(boolean enableUpdate) {
        this.enableUpdate = enableUpdate;
    }

    public LinkedHashMap<String, String> getSettingslist() {
        return settingslist;
    }

    public void setSettingslist(LinkedHashMap<String, String> settingslist) {
        this.settingslist = settingslist;
    }

    public String getSelectedKey() {
        return selectedKey;
    }

    public void setSelectedKey(String selectedKey) {
        this.selectedKey = selectedKey;
    }

    public boolean isEnableKey() {
        return enableKey;
    }

    public void setEnableKey(boolean enableKey) {
        this.enableKey = enableKey;
    }

    public void saveSettings() {
        this.setEnableUpdate(false);
        this.setEnableKey(false);
        if (!settingslist.isEmpty() && !settingslist.containsKey(getKey().toUpperCase())) {
            int id = userProfileBAO.updateSettings(getKey().toUpperCase(), getValue(), true);
            if (id > 0) {
                LookupDataLoader.getMmfsettings().put(key, value);
                settingslist = LookupDataLoader.getMmfsettings();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Data successfully saved", "Data successfully saved"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, UPDATE_FAILED_MSG, UPDATE_FAILED_MSG));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Key already exist", "Key already exist."));
        }
    }

    public void addNewSettings() {
        this.setEnableUpdate(true);
        this.setEnableKey(true);
        this.setKey(EMPTY_STRING);
        this.setValue(EMPTY_STRING);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
