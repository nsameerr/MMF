/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.LoginVO;
import java.util.Date;

/**
 *
 * @author 07958
 */
public interface IUserLoginBAO {

    public LoginVO userLogin(LoginVO login);

    public boolean changePasswordOnLogin(String username, String password, String newPassword, String userType);

    public LoginVO userTwoFactorLogin(LoginVO login);

    public boolean isLinkedinExpireIn(String currentDateTime, String username);
    
    public LoginVO userRegistrationLogin(LoginVO login);
    
    public Integer getUserStatus(String email,String password,String type);

	public Integer getUserStatusV2(String username, String password, String userType);
	
	public String getUserPersonalDeatils(String email,boolean userType,String fieldToFetch);
	
	public Boolean getUserType(String email, String password);
}
