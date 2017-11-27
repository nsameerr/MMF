/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.service.vo;

import java.util.List;

/**
 *
 * @author 09860
 */
public class NotificationAdminListVO {
    List<AdminNotificationVO> notificationsPopup;
    List<AdminNotificationVO> notifications;

    public List<AdminNotificationVO> getNotificationsPopup() {
        return notificationsPopup;
    }

    public void setNotificationsPopup(List<AdminNotificationVO> notificationsPopup) {
        this.notificationsPopup = notificationsPopup;
    }

    public List<AdminNotificationVO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<AdminNotificationVO> notifications) {
        this.notifications = notifications;
    }
    
    
}
