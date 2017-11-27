package com.gtl.mmf.dao;

import java.util.List;

/**
 *
 * @author trainee3
 */
public interface IUserForgotPwdDAO {

    public int advisorPwdUpdate(String username, String mobile, String regid, String password);

    public int investorPwdUpdate(String username, String mobile, String regid, String password);

    public int adminPwdUpdate(String email, String mobile, String password, String username);

}
