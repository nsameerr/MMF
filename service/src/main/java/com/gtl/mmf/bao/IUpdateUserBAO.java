/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.UserDetailsVO;

/**
 *
 * @author 07958
 */
public interface IUpdateUserBAO {

    public void updateInvestor(UserDetailsVO userDetails);

    public void updateAdvisor(UserDetailsVO userDetails);

    public void updateUserDetails(UserDetailsVO userDetails);
    
    public void deleteTempUser(UserDetailsVO userDetails);
    
}
