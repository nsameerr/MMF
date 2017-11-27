package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.service.vo.UserDetailsVO;

/**
 *
 * @author 07958
 */
public interface ICreateUserBAO {

    public int createNewInvestor(UserDetailsVO userDetails);

//    public int createNewAdvisor(UserDetailsVO userDetails);
    public int saveNewUserDetails(UserDetailsVO userDeatails);
    
    public BankVo getbankDetails(String ifsc_code);
    
}
