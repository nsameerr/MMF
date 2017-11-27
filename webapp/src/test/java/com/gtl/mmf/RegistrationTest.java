package com.gtl.mmf;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.ICreateUserBAO;
import com.gtl.mmf.bao.IUserLoginBAO;
import com.gtl.mmf.bao.IUserLogoutBAO;
import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.vo.LoginVO;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.service.vo.UserProfileVO;

public class RegistrationTest extends AbstractTestCase implements IConstants{    
    private static final String DEFAULT_SERACH_TEXT = "";
    private static final String DEFAULT_USERTYPE_SELECTED = "IorA";
    private static final int CURRENT_LIST_INDEX = 0;
    private static final int MAX_ITEM_COUNT = 50;
    private static final String ADVISOR = "Advisor";
    private static final String INVESTOR = "Investor";
    private static final String SAVE_NEW_USER_MESSAGE = "saveNewUserDetails returned unexpted value"; 
     private static final int MINUS_ONE = -1;

    @Autowired
    private IUserLoginBAO userLoginBAO;
    @Autowired
    private IUserProfileBAO userProfileBAO;	
    @Autowired
    private ICreateUserBAO createUserBAO;
    @Autowired
    private IUserLogoutBAO userLogoutBAO;

	
    @Test
    @Transactional	
    public void emailNotExisstTestForInv(){
        Assert.assertEquals(false,userProfileBAO.isEmailExists("investor@testcase.com"));
    }
	
//<<<<<<< .mine
	@Test
	@Transactional
	public void inValidAdminLoginTest(){		
		LoginVO loginVO = new LoginVO();
		loginVO.setUsername("admin123");
		loginVO.setPassword("admin123");
		loginVO.setUsertype(ADMIN_USER);
		Assert.assertEquals(FAILURE,userLoginBAO.userLogin(loginVO).getRedirectPage());
	}
	
	@Test
	@Transactional
	public void inValidAdminUserLoginTest(){		
		LoginVO loginVO = new LoginVO();
		loginVO.setUsername("admin123");
		loginVO.setPassword("admin");
		loginVO.setUsertype(ADMIN_USER);
		Assert.assertEquals(FAILURE,userLoginBAO.userLogin(loginVO).getRedirectPage());
	}
	
	@Test
	@Transactional
	public void inValidAdminPasswordLoginTest(){		
		LoginVO loginVO = new LoginVO();
		loginVO.setUsername("admin");
		loginVO.setPassword("admin123");
		loginVO.setUsertype(ADMIN_USER);
		Assert.assertEquals(FAILURE,userLoginBAO.userLogin(loginVO).getRedirectPage());
	}
	
	@Test
	@Transactional
	public void listNewUsersTest(){
//		Assert.assertTrue(!userProfileBAO.listNewUsers(DEFAULT_USERTYPE_SELECTED, 
//				DEFAULT_SERACH_TEXT, CURRENT_LIST_INDEX, MAX_ITEM_COUNT).isEmpty());
	}
//*/
//=======
    /*@Test
    @Transactional
    public void saveInvestorProfileTest(){
        UserProfileVO userProfileVO = new UserProfileVO();
        userProfileVO.setAddress("Test Investor address");
        userProfileVO.setName("The Investor");
        userProfileVO.setTelephone("14523654123");
        userProfileVO.setMobile("9845311475");
        userProfileVO.setPincode("855642");
        userProfileVO.setCountry("IN");
        userProfileVO.setCity("Test City");
        userProfileVO.setEmail("investor@testcase.com");
        userProfileVO.setAdvisor(false);
        userProfileVO.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
        Assert.assertEquals(true,userProfileBAO.saveInvestorProfile(userProfileVO));
    }
>>>>>>> .r179253

    @Test
    @Transactional	
    public void emailExisstTestForInv(){
        Assert.assertEquals(true,userProfileBAO.isEmailExists("investor@testcase.com"));
    }	

    @Test
    @Transactional	
    public void emailNotExisstTestForAdv(){
        Assert.assertEquals(false,userProfileBAO.isEmailExists("adviser@testcase.com"));
    }

    @Test
    @Transactional
    public void saveAdvisorProfileTest(){
        UserProfileVO userProfileVO = new UserProfileVO();
        userProfileVO.setAddress("Test Adviser address");
        userProfileVO.setName("The Adviser");
        userProfileVO.setTelephone("14523654123");
        userProfileVO.setMobile("9845311475");
        userProfileVO.setPincode("855642");
        userProfileVO.setCountry("IN");
        userProfileVO.setCity("Test City");
        userProfileVO.setEmail("adviser@testcase.com");
        userProfileVO.setAdvisor(true);
        userProfileVO.setSebi_reg_no("SEBIADV123");
        userProfileVO.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
        Assert.assertEquals(true,userProfileBAO.saveAdvisorProfile(userProfileVO));	        
    }

    @Test
    @Transactional	
    public void emailExisstTestForAdv(){
        Assert.assertEquals(true,userProfileBAO.isEmailExists("adviser@testcase.com"));
    }	

    @Test
    @Transactional
    public void validAdminLoginTest(){		
        LoginVO loginVO = new LoginVO();
        loginVO.setUsername("admin");
        loginVO.setPassword("admin");
        loginVO.setUsertype(ADMIN_USER);
        Assert.assertEquals(ADMIN_LOGIN_REDIRECT,userLoginBAO.userLogin(loginVO).getRedirectPage());
    }

    @Test
    @Transactional
    public void inValidAdminLoginTest(){		
        LoginVO loginVO = new LoginVO();
        loginVO.setUsername("admin123");
        loginVO.setPassword("admin123");
        loginVO.setUsertype(ADMIN_USER);
        Assert.assertEquals(FAILURE,userLoginBAO.userLogin(loginVO).getRedirectPage());
    }

    @Test
    @Transactional
    public void inValidAdminUserLoginTest(){		
        LoginVO loginVO = new LoginVO();
        loginVO.setUsername("admin123");
        loginVO.setPassword("admin");
        loginVO.setUsertype(ADMIN_USER);
        Assert.assertEquals(FAILURE,userLoginBAO.userLogin(loginVO).getRedirectPage());
    }

    @Test
    @Transactional
    public void inValidAdminPasswordLoginTest(){		
        LoginVO loginVO = new LoginVO();
        loginVO.setUsername("admin");
        loginVO.setPassword("admin123");
        loginVO.setUsertype(ADMIN_USER);
        Assert.assertEquals(FAILURE,userLoginBAO.userLogin(loginVO).getRedirectPage());
    }

    @Test
    @Transactional
    public void listNewUsersTest(){
        Assert.assertTrue(userProfileBAO.listNewUsers(DEFAULT_USERTYPE_SELECTED, 
                        DEFAULT_SERACH_TEXT, CURRENT_LIST_INDEX, MAX_ITEM_COUNT,-1).isEmpty());
    }


    @Test
    @Transactional
    public void listNewUsersSortITest(){
        List<UserDetailsVO> newuserList = userProfileBAO.listNewUsers(INVESTOR, 
        DEFAULT_SERACH_TEXT, CURRENT_LIST_INDEX, MAX_ITEM_COUNT,EnumStatus.ACTIVE.getValue());  
        Assert.assertTrue(newuserList.isEmpty());
    }

    @Test
    @Transactional
    public void listNewUsersSortATest(){
        List<UserDetailsVO> newuserList = userProfileBAO.listNewUsers(ADVISOR, 
                        DEFAULT_SERACH_TEXT, CURRENT_LIST_INDEX, MAX_ITEM_COUNT,
                        EnumStatus.NEW_APPLICANT.getValue());
        Assert.assertNotNull(newuserList);
        for (UserDetailsVO userDetailsVO : newuserList) {
                Assert.assertEquals(YES, userDetailsVO.getUserType());
        }
    }

   @Test
    @Transactional
    public void createNewAdvTest(){
        UserDetailsVO userDetailsVO = null;
        List<UserDetailsVO> newuserList = userProfileBAO.listNewUsers(ADVISOR, 
                        DEFAULT_SERACH_TEXT, CURRENT_LIST_INDEX, MAX_ITEM_COUNT,100);

        userDetailsVO = newuserList.get(ZERO);                                    
        userDetailsVO.setUserType(YES);
        userDetailsVO.setPanNo("ADVPAN");
        userDetailsVO.setHomeAddress("Home address");
        userDetailsVO.setHomeCity("Home City");
        userDetailsVO.setHomePincode("988789");
        userDetailsVO.setHomeCountry("IN");
        userDetailsVO.setHomeTelephone("45321425632");
        userDetailsVO.setHomeMobile("4521355412");
        userDetailsVO.setOfficeOrganization("Office org");
        userDetailsVO.setOfficeJobTitle("Job Title");
        userDetailsVO.setOfficeAddress("Office Address");
        userDetailsVO.setOfficeCity("Office City");
        userDetailsVO.setOfficePincode("457853");
        userDetailsVO.setOfficeCountry("IN");
        userDetailsVO.setOfficeTelephone("45213245213");
        userDetailsVO.setOfficeMobile("78546233152");
        userDetailsVO.setTradingBrokerName("Trade Brkr Name");
        userDetailsVO.setTradingBrokerAccNo("452135");
        userDetailsVO.setTradingBank("Trading Bank");
        userDetailsVO.setTradingIFSCNo("IFS1234");
        userDetailsVO.setTradingAccNo("1345451");;
        userDetailsVO.setTradingAmount("100000");;
        userDetailsVO.setQualificationPrimary("CFP");
        userDetailsVO.setQualiPrimaryInstitute("FPSB");
        userDetailsVO.setQualiPrimaryYear("2008");;
        userDetailsVO.setQualiPrimaryId("123");

        userDetailsVO.setQualificationSecondary("CFP");
        userDetailsVO.setQualiSecondaryInstitute("FPSB");
        userDetailsVO.setQualiSecondaryYear("2008");;
        userDetailsVO.setQualiSecondaryId("123");

        userDetailsVO.setQualificationTertiary("CFP");
        userDetailsVO.setQualiTertiaryInstitute("FPSB");
        userDetailsVO.setQualiTertiaryYear("2008");;
        userDetailsVO.setQualiTertiaryId("123");

        userDetailsVO.setDocStatus("Documents Received");;
        userDetailsVO.setAmount("10000");

        int id = createUserBAO.saveNewUserDetails(userDetailsVO);
        //Assert.assertEquals(SAVE_NEW_USER_MESSAGE, id != ZERO, id != ZERO);
        Assert.assertEquals(1,1);
    }

    @Test
    @Transactional
    public void createNewInvTest(){
        UserDetailsVO userDetailsVO = null;
        List<UserDetailsVO> newuserList = userProfileBAO.listNewUsers(INVESTOR, 
                        DEFAULT_SERACH_TEXT, CURRENT_LIST_INDEX, MAX_ITEM_COUNT,
                        100);  
        Assert.assertNotNull(newuserList);
                            userDetailsVO = newuserList.get(ZERO);
        userDetailsVO.setPanNo("INVPAN");
        userDetailsVO.setHomeAddress("Home address");
        userDetailsVO.setHomeCity("Home City");
        userDetailsVO.setHomePincode("988789");
        userDetailsVO.setHomeCountry("IN");
        userDetailsVO.setHomeTelephone("45321425632");
        userDetailsVO.setHomeMobile("4521355412");
        userDetailsVO.setOfficeOrganization("Office org");
        userDetailsVO.setOfficeJobTitle("Job Title");
        userDetailsVO.setOfficeAddress("Office Address");
        userDetailsVO.setOfficeCity("Office City");
        userDetailsVO.setOfficePincode("457853");
        userDetailsVO.setOfficeCountry("IN");
        userDetailsVO.setOfficeTelephone("45213245213");
        userDetailsVO.setOfficeMobile("78546233152");
        userDetailsVO.setTradingBrokerName("Trade Brkr Name");
        userDetailsVO.setTradingBrokerAccNo("452135");
        userDetailsVO.setTradingBank("Trading Bank");
        userDetailsVO.setTradingIFSCNo("IFS1234");
        userDetailsVO.setTradingAccNo("1345451");
        userDetailsVO.setDocStatus("Documents Received");
        userDetailsVO.setAmount("10000");
        int id = createUserBAO.saveNewUserDetails(userDetailsVO);
        Assert.assertEquals(SAVE_NEW_USER_MESSAGE, id != ZERO, id != ZERO);
    }*/

//	@Test
//	@Transactional
//	public void invalidLoginInvTest(){
//		LoginVO loginVO = new LoginVO();
//		loginVO.setUsername("invaliduser@test.com");
//		loginVO.setPassword("invalidpassword");
//		loginVO.setUsertype("No");
//		Assert.assertEquals("FAILURE",userLoginBAO.userLogin(loginVO).getRedirectPage());		
//	}
//	
//	@Test
//	@Transactional
//	public void invalidLoginIValidUserTest(){
//		LoginVO loginVO = new LoginVO();
//		loginVO.setUsername("investor@testcase.com");
//		loginVO.setPassword("invalidpassword");
//		loginVO.setUsertype("No");
//		Assert.assertEquals("FAILURE",userLoginBAO.userLogin(loginVO).getRedirectPage());		
//	}
//	
//		
//	@Test
//	@Transactional
//	public void invalidLginAdvTest(){
//		LoginVO loginVO = new LoginVO();
//		loginVO.setUsername("invaliduser@test.com");
//		loginVO.setPassword("invalidpassword");
//		loginVO.setUsertype("Yes");
//		Assert.assertEquals("FAILURE",userLoginBAO.userLogin(loginVO).getRedirectPage());
//	}
//	
//	@Test
//	@Transactional
//	public void invalidLoginAValidUserTest(){
//		LoginVO loginVO = new LoginVO();
//		loginVO.setUsername("adviser@testcase.com");
//		loginVO.setPassword("invalidpassword");
//		loginVO.setUsertype("No");
//		Assert.assertEquals("FAILURE",userLoginBAO.userLogin(loginVO).getRedirectPage());		
//	}

    /*@Test
    @Transactional
    public void selectedUserATest() {
        List<UserDetailsVO> newuserList = userProfileBAO.listNewUsers(ADVISOR, DEFAULT_SERACH_TEXT, 
                CURRENT_LIST_INDEX, MAX_ITEM_COUNT,100);
        Assert.assertNotNull(newuserList);
        UserDetailsVO selectuserList = userProfileBAO.getSelectedUser(newuserList.get(ZERO));
        Assert.assertEquals(YES, selectuserList.getUserType());
    }

    @Test
    @Transactional
    public void selectedUserITest() {
                       List<UserDetailsVO> newuserList = userProfileBAO.listNewUsers(INVESTOR,
                       DEFAULT_SERACH_TEXT, CURRENT_LIST_INDEX, MAX_ITEM_COUNT,100);
                       Assert.assertNotNull(newuserList);
                       UserDetailsVO selectuserList = userProfileBAO.getSelectedUser(newuserList.get(ZERO));
                       Assert.assertEquals(NO, selectuserList.getUserType());
     }

    @Test
    @Transactional
    public void userLogoutATest() {
        Integer userId = 1;    
        userLogoutBAO.userLogout(ADVISOR, userId);
        Assert.assertNotSame(new Integer(ZERO) ,userId);
    }

    @Test
    @Transactional
    public void userLogoutITest() {
        Integer userId = 1;    
        userLogoutBAO.userLogout(INVESTOR, userId);
        Assert.assertNotSame(new Integer(ZERO) ,userId);
    }*/
}
