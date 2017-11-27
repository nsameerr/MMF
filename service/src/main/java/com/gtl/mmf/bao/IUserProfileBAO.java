package com.gtl.mmf.bao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.gtl.mmf.entity.InvProofFileDetailsTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.entity.TempInv;
import com.gtl.mmf.entity.TempRegistrationTb;
import com.gtl.mmf.service.vo.AdvisorRegistrationVo;
import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.service.vo.BoRegDataServiceVo;
import com.gtl.mmf.service.vo.CompleteTempInvVo;
import com.gtl.mmf.service.vo.FailedMailDetailsVO;
import com.gtl.mmf.service.vo.HolidayCalenderVo;
import com.gtl.mmf.service.vo.InvestorTempRegistrationVo;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.TempAdvVo;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.service.vo.UserProfileVO;
import com.gtl.mmf.service.vo.UserRegStatusVO;
import com.gtl.mmf.service.vo.VerifyMailVO;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IUserProfileBAO {

    /**
     *
     * @param registrationVo
     * @param applicantStatus
     * @return
     */
    public boolean saveInvestorProfile(RegistrationVo registrationVo, Boolean applicantStatus);

    public boolean saveAdvisorProfile(AdvisorRegistrationVo advisorRegistrationVo, Boolean applicantStatus);

    public List<UserDetailsVO> listNewUsers(String userType, String searchText, int firstResultIndex, int maxResults, int userSelected);

    public UserDetailsVO getSelectedUser(UserDetailsVO selectedUser);

    public boolean isEmailExists(String mail);

    public boolean isEmailExists(String mail, String regId);

    public boolean isPanNoExists(String userType, String panNo, String regId);

    public long sizeOfUsersList(String userType, String searchText);

    public UserRegStatusVO isUserRegEmailExists(String mail);

    public Object[] isLinkedinUserExists(String linkedinMemberId);

    public boolean updateExpireInDet(UserProfileVO userProfileVO);

    List<UserDetailsVO> getAllUsers();

    public Map<String, String> getCityList(String countryCode);

    public boolean isemailverified(VerifyMailVO verifyMailVO);

    public UserDetailsVO getUserProfile(Integer userId, String userType);

    public Boolean deleteUser(UserDetailsVO userDetailsVO, Boolean isHardDelete) throws Exception;

    public Boolean saveTempUserProfile(String email, String password, boolean advisor);

    public boolean emailverification(String parameter, String emailEncrypted);

    public RegistrationVo getNewApplicantDetails(MasterApplicantTb masterApplicantTb);

    public UserProfileVO getNewAdvisorDetail(MasterApplicantTb masterApplicantTb);

    public void saveMandate(MandateVo mandateVo, String regid);

    public List<FailedMailDetailsVO> getFailedMailList(String mailTypeSelected,String searchText,boolean failedMail);

    public List<FailedMailDetailsVO> reSendMail(List<FailedMailDetailsVO> reGenerateMailList);

    public void reSendMailForFaildMail(FailedMailDetailsVO mailDetails);

    public void updateLinkedINData(UserProfileVO userProfileVO);

    public void updateLinkedIn(Object get, Boolean advisor);

    public String getCustomerInitLoginStatus(String username, String usertype);

    public void getInvestorNomineeDetails(UserDetailsVO userDetails);

    public void saveAdvTemp(TempAdvVo advisorRegistrationVo) throws ParseException;

    public AdvisorRegistrationVo getTempAdvData(String email);

    public void saveInvTemp(InvestorTempRegistrationVo tempRegistrationVo) throws ParseException;

    public RegistrationVo getTempInvData(String email);

    public Integer getKitAllocationData();

    public RegistrationVo getRegistraionData(Integer kitNumber);

    public BoRegDataServiceVo prepareForBackoffceTransfer(UserDetailsVO userdetails);

    public int updateSettings(String key, String value, boolean savenew);

    public List<HolidayCalenderVo> getHolidayList();

    public void saveHolidayList(HolidayCalenderVo calenderVo);

    public void deleteHoliday(HolidayCalenderVo deleteHoliday);

    public TempInv getTempInvDetails(String email);

    public void updateInvestor(TempInv investor);

    public TempRegistrationTb getTempRegistrationTableData(String email, boolean userType);

//    public List<BankVo> lookforBank(String searchText);
//    
//    public List<BankVo> onBankSelect(String bank, String state, String city);
    public void saveCompleteInvTemp(InvestorTempRegistrationVo tempRegistrationVo) throws ParseException;

    public String getInvAccountStatus(String email);

    public CompleteTempInvVo getCompleteInvData(String email);

    public boolean isPanNoExists(String pan);

    public boolean isEmailAlreadyRegistered(String email);

    public void resendVerificationEmail(String email, boolean userType);

    public boolean updateVerificationIfUserDetailsSubmitted(String email, String userType);

    public TempAdvVo getCompleteTempAdvData(String email);
    
    //For terminate contract
    public boolean terminateContract(UserDetailsVO userdetails);
    
    //For Display termination Eanble button
    public boolean getUserEnableterminationStatus(Integer customerId);
    
    public void saveInvestorDocumentDetails(RegistrationVo registrationVo);
    
    public void saveProofFileToDb(String panFile,String proofType,String email);

    public InvProofFileDetailsTb getInvProofDetils(String email);
}
