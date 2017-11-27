/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.persist.vo.ForgotPwdVO;

/**
 *
 * @author trainee3
 */
public interface IUserForgotPwdBAO {

    public ForgotPwdVO changePasswordOnForgotPwd(String username, String regid, String mobile, boolean advisor);

    public ForgotPwdVO changePasswordAdminOnForgotPwd(String email, String mobile, String username);
}
