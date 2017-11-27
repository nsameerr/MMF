package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CitiesTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import com.gtl.mmf.entity.InvestorNomineeDetailsTb;
import com.gtl.mmf.entity.MandateFormTb;
import com.gtl.mmf.entity.MasterAdvisorQualificationTb;
import com.gtl.mmf.entity.InvProofFileDetailsTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MmfSettingsTb;
import com.gtl.mmf.entity.TempAdv;
import com.gtl.mmf.entity.TempInv;
import com.gtl.mmf.entity.TempRegistrationTb;
import java.util.Date;
import java.util.List;

public interface IUserProfileDAO {

    public boolean saveInvestorProfile(MasterApplicantTb investor);

    public int saveAdvisorProfile(MasterApplicantTb advisor);

    public List getAdvisorDetails(String regid, String username, boolean fromAdmin);

    public List getInvestorDetails(String regid, String username, boolean fromAdmin);

    public List getEmailStatus(String mail);

    public List getEmailStatus(String mail, String regId);

    public List getPanNoStatusInvestor(String panNo, String regId);

    public List getPanNoStatusAdvisor(String panNo, String regId);

    public long sizeOfUsersList(String userType, String searchText);

    public List listNewUsers(String userType, String searchText, int firstResultIndex, int maxResults, int userSelected);

    public Object[] getLinkedinUserStatus(String linkedinMail);

    public int updateExpireInDet(String username, String expirein, Date linkedinExpireInDate, String accesstoken);

    List<MasterCustomerTb> getAllUsers();

    public List<CitiesTb> getCityList(String CountryCode);

    public Integer checkEmailVerified(MasterApplicantTb masterApplicantTb);

    public List<Object> getUserProfile(Integer userId, Boolean isAdvisor);

    public Integer deleteAdvisor(MasterAdvisorTb masterAdvisorTb, Boolean isHardDelete) throws Exception;

    public Integer deleteCustomer(MasterCustomerTb masterCustomerTb, Boolean isHardDelete) throws Exception;

    public boolean saveTempUserProfile(TempRegistrationTb tempRegistration);

    public Integer emailVerification(String verificationCode, String email);

    public List getEmailStatusTempUser(String mail);

    public Object getTemporaryUserDetails(String email);

    public boolean updateProfile(MasterApplicantTb master);

    public void saveMandateForm(MandateFormTb mandate);

    public List getSendMailFailedList(String tableName, String searchText);

    public List<Object> getMandateTbForRegId(String regId, boolean flag);

    public List<Object> getUserDetails(String email, Boolean isAdvisor);

    public void updateLinkedInData(List<Object> linkedIndata, boolean isAdvisor);

    public void updateLinkedInStatus(String email, Boolean advisor);

    public boolean saveInvestorNomineeProfile(InvestorNomineeDetailsTb nominee);

    public InvestorNomineeDetailsTb getNominationDeatails(String registrationId);

    public boolean saveAdvisorQualification(MasterAdvisorQualificationTb qualificationT);

    public MasterAdvisorQualificationTb getMasterAdvisorQualificationDeatails(String registrationId);

    public Object getMasterApplicant(String regNo);

    public void saveTempAdv(TempAdv tempmaster);

    public TempAdv getTempAdvDetails(String email);

    public TempInv getTempInvDetails(String email);

    public void saveTempInvestor(TempInv investor);

    public List<Object> getAllAllocation();

    public void updateKitAllocationStatus(Integer regKitId);

    public List<Object> getAllRegistrationData(Integer kitNumber);

    public void sendNotification(Integer status);

    public int updateSettings(MmfSettingsTb settingsTb, boolean save);

    public List<ExchangeHolidayTb> getHolidayCalender();

    public void saveHoliday(ExchangeHolidayTb holidayTb);

    public ExchangeHolidayTb CheckForUpdationHoliday(Date exchange);

    public void deleteHoliday(ExchangeHolidayTb holidayTb);

    public TempRegistrationTb getTempRegUserDetails(String email, String userType);

    //public List<Object[]> getBankDetails(String searchText);

    public List<Object[]> getCustomerPortfolioDetails(Integer customerid);

    public List getCustomerDetails(String registrationId);

    public List hasPortfolioMapped(String regid);

    public String getStateCode(String stateName);

    public boolean getPanNoStatus(String pan);

    public boolean isEmailAlreadyRegistered(String email);

    public boolean isApplicantDetailsSubmitted(String email, String userType);

    public void updateApplicantMailVerifed(String email, String userType);
    
    public Integer getRelationIdOfUser(int userId);
   
    public Integer updateContractTerminateStatus(int relationId);
    
    public CustomerAdvisorMappingTb getTerminationDetailsOfUser(int relationId);
    
    public void saveInvestorDocumentDetails(MasterApplicantTb master);
    
    public void saveProofFileToDb(InvProofFileDetailsTb ipfd);

    public InvProofFileDetailsTb getInvProofFileDetails(String email);
}
