/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.service.vo;

import java.util.List;

/**
 *
 * @author trainee8
 */
public class NotificationAdvisorListVO {
    List<AdvisorNotificationsVO> notificationsPopup;
    List<AdvisorNotificationsVO> notifications;

    public List<AdvisorNotificationsVO> getNotificationsPopup() {
        return notificationsPopup;
    }

    public void setNotificationsPopup(List<AdvisorNotificationsVO> notificationsPopup) {
        this.notificationsPopup = notificationsPopup;
    }

    public List<AdvisorNotificationsVO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<AdvisorNotificationsVO> notifications) {
        this.notifications = notifications;
    }
    
}
