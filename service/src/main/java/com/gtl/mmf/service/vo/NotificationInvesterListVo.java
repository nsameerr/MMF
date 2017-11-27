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
public class NotificationInvesterListVo {
    List<InvestorNotificationsVO> notificationsPopup;
    List<InvestorNotificationsVO> notifications;

    public List<InvestorNotificationsVO> getNotificationsPopup() {
        return notificationsPopup;
    }

    public void setNotificationsPopup(List<InvestorNotificationsVO> notificationsPopup) {
        this.notificationsPopup = notificationsPopup;
    }

    public List<InvestorNotificationsVO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<InvestorNotificationsVO> notifications) {
        this.notifications = notifications;
    }
    
}
