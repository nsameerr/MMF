/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("sidebarAdminController")
@Scope("view")
public class SidebarAdminController {

    public String onClickHome() {
        return "/pages/admin/new_user_list?faces-redirect=true";
    }

    public String onClickReport() {
        return "/pages/admin/client_portfolio_summary_report?faces-redirect=true";
    }
    
    public String onClickFailedMails() {
        return "/pages/admin/failed_mail_list?faces-redirect=true";
    }
    
}
