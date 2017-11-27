package com.gtl.mmf.bao.impl;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gtl.mmf.bao.IMailStatusBAO;
import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.bao.RegistrationBAO;
import com.gtl.mmf.common.EnumAdvLoginStatus;
import com.gtl.mmf.common.EnumCustLoginStatus;
import com.gtl.mmf.common.EnumMandate;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.dao.IUserProfileDAO;
import com.gtl.mmf.entity.BankTb;
import com.gtl.mmf.entity.CitiesTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.EcsTransmittalSheetTb;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.InvProofFileDetailsTb;
import com.gtl.mmf.entity.InvestorNomineeDetailsTb;
import com.gtl.mmf.entity.KitAllocationTb;
import com.gtl.mmf.entity.MandateFormTb;
import com.gtl.mmf.entity.MasterAdvisorQualificationTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MmfSettingsTb;
import com.gtl.mmf.entity.TempAdv;
import com.gtl.mmf.entity.TempInv;
import com.gtl.mmf.entity.TempRegistrationTb;
import com.gtl.mmf.service.util.BASE64Encrption;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.MAIL_SMTP_FROM;
import static com.gtl.mmf.service.util.IConstants.NEW_LINE;
import static com.gtl.mmf.service.util.IConstants.YES;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.SecurityUtility;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.util.TemplateUtil;
import com.gtl.mmf.service.util.UUIDUtil;
import com.gtl.mmf.service.vo.AdvisorRegistrationVo;
import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.service.vo.BoRegDataServiceVo;
import com.gtl.mmf.service.vo.CompleteTempInvVo;
import com.gtl.mmf.service.vo.FailedMailDetailsVO;
import com.gtl.mmf.service.vo.HolidayCalenderVo;
import com.gtl.mmf.service.vo.InvestorTempRegistrationVo;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegKitVo;
import com.gtl.mmf.service.vo.RegistrationDataProcessingVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.TempAdvVo;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.service.vo.UserProfileVO;
import com.gtl.mmf.service.vo.UserRegStatusVO;
import com.gtl.mmf.service.vo.VerifyMailVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserProfileBAOImpl implements IUserProfileBAO, IConstants {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.UserProfileBAOImpl");
    @Autowired
    private IUserProfileDAO userProfileDAO;
    private boolean saveInvestorProfile;
    private boolean saveAdvisorProfile;
    private String num;
    private String isd;
    private String std;
    private MasterApplicantTb master;
    private MandateFormTb mandate;
    private RegistrationBAO registrationBAO;

    public IUserProfileDAO getSpotfineDAO() {
        return userProfileDAO;
    }

    public void setSpotfineDAO(IUserProfileDAO spotfineDAO) {
        this.userProfileDAO = spotfineDAO;
    }

    /**
     * This method is used for Saving/updating investor registration data into
     * the table
     *
     * @param registrationVo
     * @param applicantStatus
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean saveInvestorProfile(RegistrationVo registrationVo, Boolean applicantStatus) {
        StringBuilder logMessage = new StringBuilder("Creating new investor profile with emailid ");
        logMessage.append(registrationVo.getEmail());
        LOGGER.info(logMessage.toString());
        //get email verified status
        TempRegistrationTb registrationTb = getTempRegistrationTableData(registrationVo.getEmail(), registrationVo.isAdvisor());
        boolean mailVerified = false;
        if (registrationTb != null) {
            mailVerified = (registrationTb.getMailVerified() == null) ? false : registrationTb.getMailVerified();
        }
        // Checking application status  to decide whather to update or save data.
        if (applicantStatus) {
            master = (MasterApplicantTb) userProfileDAO.getTemporaryUserDetails(registrationVo.getEmail());
        } else {
            master = new MasterApplicantTb();
        }
        master.setRegistrationId(registrationVo.getRegId());
        master.setAddress1(registrationVo.getCaddress());
        master.setName(registrationVo.getName());
        master.setTelephone(registrationVo.getRnumber() == null ? EMPTY_STRING : registrationVo.getRnumber());
        master.setTelephoneIsd(registrationVo.getRisd() == null ? EMPTY_STRING : registrationVo.getRisd());
        master.setTelephoneStd(registrationVo.getRstd() == null ? EMPTY_STRING : registrationVo.getRstd());
        master.setMobile(registrationVo.getMobileNumber());
        master.setAddressPincode(registrationVo.getCpinCode());
        master.setAddressCountry(registrationVo.getCcountry());
        master.setAddressCity(registrationVo.getCcity());
        master.setEmail(registrationVo.getEmail());
        master.setAdvisor(registrationVo.isAdvisor());
        master.setStatus(registrationVo.getUser_status());
        master.setRegisterDatetime(new Date());

        master.setMiddleName(registrationVo.getMiddleName());
        master.setLastName(registrationVo.getLastName());
        master.setPictureUrl(registrationVo.getPictureUrl() != null
                ? registrationVo.getPictureUrl() : EMPTY_STRING);
        master.setDob(registrationVo.getDob());

        master.setWorkOrganization(registrationVo.getNameOfEmployerOrCompany() != null
                ? registrationVo.getNameOfEmployerOrCompany() : EMPTY_STRING);
        master.setJobTitle(registrationVo.getDesignation() != null
                ? registrationVo.getDesignation() : EMPTY_STRING);
        master.setIsMailVerified(mailVerified);
        master.setIsActiveUser(Boolean.TRUE);
        master.setAddress2(registrationVo.getPaddress());
        master.setFatherSpouseName(registrationVo.getFatherOrSpouseName());
        master.setNationality(StringUtils.hasText(registrationVo.getNationality()) ? registrationVo.getNationality() : "indian");
        master.setResidentialStatus(registrationVo.getStatus());
        master.setGender(registrationVo.getGender());
        master.setMaritalStatus(registrationVo.getMaritalStatus());
        master.setPan(registrationVo.getPan());
        master.setUidAadhar(registrationVo.getUid() == null ? EMPTY_STRING : registrationVo.getUid());
        master.setProofOfIdentity(registrationVo.getIdProof());
        master.setAddressState(registrationVo.getCstate());
        master.setAddress2Country(registrationVo.getPcountry() == null ? EMPTY_STRING
                : registrationVo.getPcountry());
        master.setAddress2State(registrationVo.getPstate() == null ? EMPTY_STRING
                : registrationVo.getPstate());
        master.setAddress2City(registrationVo.getPcity() == null ? EMPTY_STRING
                : registrationVo.getPcity());
        master.setAddress2Pincode(registrationVo.getPpinCode() == null ? EMPTY_STRING
                : registrationVo.getPpinCode());
        master.setAddress2Proof(registrationVo.getAddress2_proof() == null ? EMPTY_STRING
                : registrationVo.getAddress2_proof());
        master.setAddress2Validity(registrationVo.getAddress2_validity());

        master.setTelephone2Isd(registrationVo.getHisd() == null ? EMPTY_STRING
                : registrationVo.getHisd());
        master.setTelephone2Std(registrationVo.getHstd() == null ? EMPTY_STRING
                : registrationVo.getHstd());
        master.setTelephone2(registrationVo.getHnumber() == null ? EMPTY_STRING
                : registrationVo.getHnumber());

        master.setFaxIsd(registrationVo.getFisd() == null ? EMPTY_STRING
                : registrationVo.getFisd());
        master.setFaxStd(registrationVo.getFstd() == null ? EMPTY_STRING
                : registrationVo.getFstd());
        master.setFax(registrationVo.getFnumber() == null ? EMPTY_STRING
                : registrationVo.getFnumber());

        master.setProofOfAddress(registrationVo.getAddressProof());
        master.setExpiryDate(registrationVo.getDobValidity());
        master.setBankCountry(registrationVo.getBank_country());
        master.setBankState(registrationVo.getBank_state());
        master.setBankArea(registrationVo.getBarea());
        master.setBankBuilding(registrationVo.getBuilding());
        master.setBankCity(registrationVo.getBcity());
        master.setBankName(registrationVo.getBankname());
//        master.setBranch(registrationVo.getBranch());
        master.setBankPincode(registrationVo.getBpincode());
        master.setBankStreet(registrationVo.getBstreet());
        master.setBankSubtype(registrationVo.getBankSubType());
        master.setAccountNumber(registrationVo.getBankAccountNumber());
        master.setIfcsNumber(registrationVo.getIfsc());
        master.setMicrNumber(registrationVo.getMicrCode());
        master.setAccountTypeOpen(registrationVo.getOpenAccountType());
        master.setClientId(registrationVo.getClientId());
        master.setDpId(registrationVo.getDpId());
        master.setTradingAccountType(registrationVo.getTradingtAccount());
        master.setDematAccountType(registrationVo.getDematAccount());
        if (registrationVo.getTypeAlert() != null) {
            master.setSmsFacility(registrationVo.getTypeAlert().equalsIgnoreCase("Both") || registrationVo.getTypeAlert().equalsIgnoreCase("SMS"));
        }
        master.setOccupation(registrationVo.getOccupation());
        master.setIncomeRange(registrationVo.getIncomerange());
        master.setNetWorthDate(registrationVo.getNetWorthdate());
        master.setAmount(registrationVo.getNetAmmount());
        master.setPoliticalyExposed((StringUtils.hasText(registrationVo.getPep()) && registrationVo.getPep().equalsIgnoreCase(TRUE)));
        master.setPoliticalyRelated((StringUtils.hasText(registrationVo.getRpep()) && registrationVo.getRpep().equalsIgnoreCase(TRUE)));
        if (registrationVo.getInstruction1() != null) {
            master.setInstruction1(registrationVo.getInstruction1());
        }
        if (registrationVo.getInstruction2() != null) {
            master.setInstruction2(registrationVo.getInstruction2());
        }
        if (registrationVo.getInstruction3() != null) {
            master.setInstruction3(registrationVo.getInstruction3());
        }
        if (registrationVo.getInstruction4() != null) {
            master.setInstruction4(registrationVo.getInstruction4());
        }
        if (registrationVo.getInstruction5() != null) {
            master.setAddressForCommunication(registrationVo.getInstruction5());
        }
        master.setPermanentAddress((registrationVo.getPermenentAddress() != null && registrationVo.getPermenentAddress().equals(true)));

        master.setSecondHolderDetailsAvailable(registrationVo.isSecondHolderDetailsAvailable());
        if (registrationVo.isSecondHolderDetailsAvailable()) {
            master.setSmsFacilitySecondHolder(!registrationVo.getSmsFacilitySecondHolder().equalsIgnoreCase(ONE.toString()));
            master.setNameSecondHolder(registrationVo.getNameSecondHolder());
            master.setIncomeRangeSecondHolder(registrationVo.getIncomeRangeSecondHolder());
            master.setNetWorthDateSecondHolder(registrationVo.getNetWorthDateSecondHolder());
            master.setAmountSecondHolder(registrationVo.getAmountSecondHolder());
            master.setOccupationSecondHolder(registrationVo.getOccupationSecondHolder());
            master.setPoliticalyExposedSecondHolder((StringUtils.hasText(registrationVo
                    .getPoliticalyExposedSecondHolder()) && registrationVo.getPoliticalyExposedSecondHolder()
                    .equalsIgnoreCase(TRUE)));
            master.setPoliticalyRelatedSecondHolder((StringUtils.hasText(registrationVo
                    .getPoliticalyRelatedSecondHolder()) && registrationVo.getPoliticalyRelatedSecondHolder()
                    .equalsIgnoreCase(TRUE)));
            master.setNameEmployerSecondHolder(registrationVo.getNameEmployerSecondHolder());
            master.setDesignationSecondHolder(registrationVo.getDesignationSecondHolder());
        }
        master.setRbiReferenceNumber(registrationVo.getRbiReferenceNumber());
        master.setRbiApprovalDate(registrationVo.getRbiApprovalDate());
        master.setDepositoryParticipantName(registrationVo.getDepositoryParticipantName());
        master.setDepositoryName(registrationVo.getDepositoryName());
        master.setBeneficiaryName(registrationVo.getBeneficiaryName());
        master.setBeneficiaryId(registrationVo.getBeneficiaryId());
        master.setDpIdDepository(registrationVo.getDpIdDepository());
        master.setTradingPreferenceExchange(registrationVo.getTradingPreferenceExchange());
        master.setTradingPreferenceSegment(registrationVo.getTradingPreferenceSegment());
        master.setDocumentaryEvedence(registrationVo.getDocumentaryEvedence());
        master.setDocumentaryEvedenceOther(registrationVo.isDocumentaryEvedenceOther());
        master.setDealingThroughSubbroker(registrationVo.isDealingThroughSubbroker());
        if (registrationVo.isDealingThroughSubbroker()) {
            master.setSubbrokerName(registrationVo.getSubbrokerName());
            master.setNseSebiRegistrationNumber(registrationVo.getNseSebiRegistrationNumber());
            master.setBseSebiRegistrationNumber(registrationVo.getBseSebiRegistrationNumber());
            master.setRegisteredAddressSubbroker(registrationVo.getRegisteredAddressSubbroker());
            master.setPhoneSubbroker(registrationVo.getPhoneSubbroker());
            master.setFaxSubbroker(registrationVo.getFaxSubbroker());
            master.setWebsiteSubbroker(registrationVo.getWebsiteSubbroker());
        }

        master.setDealingThroughBroker(registrationVo.isDealingThroughBroker());
        if (registrationVo.isDealingThroughBroker()) {
            master.setNameStockBroker(registrationVo.getNameStockBroker());
            master.setNameSubbroker(registrationVo.getNameSubbroker());
            master.setClientCodeSubbroker(registrationVo.getClientCodeSubbroker());
            master.setExchangeSubbroker(registrationVo.getExchangeSubbroker());
            master.setDetailsBroker(registrationVo.getDetailsBroker());
        }
        master.setTypeElectronicContract(registrationVo.getTypeElectronicContract());
        master.setFacilityInternetTrading(!registrationVo.getFacilityInternetTrading().equalsIgnoreCase(ONE.toString()));
        master.setTradingExperince(registrationVo.getTradingExperince());
        master.setAddressFirmPropritory(registrationVo.getAddressFirmPropritory());
        master.setTypeAlert(registrationVo.getTypeAlert());
        master.setRelationSipMobilenumber(registrationVo.getRelationSipMobilenumber());
        master.setPanMobileNumber(registrationVo.getPanMobileNumber());
        master.setOtherInformations(registrationVo.getOtherInformations());
        master.setRelativeGeojitEmployee(registrationVo.isRelativeGeojitEmployee());
        if (registrationVo.isRelativeGeojitEmployee()) {
            master.setRelationshipGeojitEmployee(registrationVo.getRelationshipGeojitEmployee());
            master.setGeojitEmployeeCode(registrationVo.getGeojitEmployeeCode());
            master.setGeojitEmployeeName(registrationVo.getGeojitEmployeeName());
        }
        master.setNomineeExist(registrationVo.isNomineeExist());
        master.setCorrespondenceAddressPath(registrationVo.getCorespondenceAddressFile());
        master.setPermanentAddressPath(registrationVo.getPermenentAddressFile());
//        master.setIdentityProofPath(registrationVo.getIdProofFileName());
        master.setIdentityProofPath(registrationVo.getPanUploadFile());
        master.setDocumentaryEvidencePath(registrationVo.getDocumentaryFile());
        master.setResCityOther(registrationVo.getCp_city_other());
        master.setOffCityOther(registrationVo.getPermanentCityOther());
        master.setBnkCityOther(registrationVo.getBnk_city_other());
        master.setNomCityOther(registrationVo.getNomCityOther());
        master.setMinorCityother(registrationVo.getMinorCityOther());
        master.setKitNumber(registrationVo.getKitNumber());

        master.setSecondHldrDependentRelation(registrationVo.getSecondHldrDependentRelation());
        master.setSecondHldrPan(registrationVo.getSecondHldrPan() == null
                ? EMPTY_STRING : registrationVo.getSecondHldrPan());
        master.setSecondHldrDependentUsed(registrationVo.getSecondHldrDependentUsed() == null
                ? EMPTY_STRING : registrationVo.getSecondHldrDependentUsed());
        master.setFirstHldrDependentUsed(registrationVo.getFirstHldrDependentUsed());
        //FATCA details
        master.setUsNational(registrationVo.getUsNational());
        master.setUsResident(registrationVo.getUsResident());
        master.setUsBorn(registrationVo.getUsBorn());
        master.setUsAddress(registrationVo.getUsAddress());
        master.setUsTelephone(registrationVo.getUsTelephone());
        master.setUsStandingInstruction(registrationVo.getUsStandingInstruction());
        master.setUsPoa(registrationVo.getUsPoa());
        master.setUsMailAddress(registrationVo.getUsMailAddress());
        master.setIndividualTaxIdntfcnNmbr(registrationVo.getIndividualTaxIdntfcnNmbr());
        master.setAddressType(registrationVo.getAddressType() == null ? EMPTY_STRING : registrationVo.getAddressType());
        master.setMotherName(registrationVo.getMotherName() == null ? EMPTY_STRING : registrationVo.getMotherName());
        master.setLinkedInSkipped(false);
        master.setIdentityProofPath(registrationVo.getPanUploadFile() == null ? EMPTY_STRING : registrationVo.getPanUploadFile());
        master.setCorrespondenceAddressPath(registrationVo.getCorespondenceAddressFile() == null ? EMPTY_STRING : registrationVo.getCorespondenceAddressFile());
        master.setPermanentAddressPath(registrationVo.getPermenentAddressFile() == null ? EMPTY_STRING : registrationVo.getPermenentAddressFile());
        master.setIsMailVerified(true);

        // Checking application status  to decide whather to update or save data.
        if (applicantStatus) {
            if (registrationVo.getId() != null) {
                master.setId(registrationVo.getId());
            }
            saveInvestorProfile = userProfileDAO.updateProfile(master);

        } else {
            if (registrationVo.isLinkedInConnected()) {
                master.setLinkedinPassword(registrationVo.getAccess_token());
                master.setLinkedinExpireIn(registrationVo.getExpire_in());
                master.setLinkedinMemberId(registrationVo.getLinkedin_member_id());
                master.setLinkedinUser(registrationVo.getLinkedin_user());
                master.setLinkedinExpireDate(registrationVo.getExpire_in_date());
            }
            master.setLinkedInConnected(registrationVo.isLinkedInConnected());
            master.setEcsMandateStatus(EnumMandate.NOT_VERIFIED.getValue());
            saveInvestorProfile = userProfileDAO.saveInvestorProfile(master);
            if (registrationVo.isNomineeExist()) {
                saveInvestorNominee(registrationVo);
            }
        }
        if (saveInvestorProfile && mailVerified) {
            try {
                String url = null;
                StringBuffer name = new StringBuffer();
                name.append(registrationVo.getName()).append(SPACE).append(registrationVo.getMiddleName())
                        .append(SPACE).append(registrationVo.getLastName());
                boolean mailSendStatus = sendmail(name.toString(), registrationVo.getEmail(), registrationVo.getRegId(), registrationVo.getMobileNumber(), true);
                IMailStatusBAO iMailStatusBAO = (IMailStatusBAO) BeanLoader.getBean("iMailStatusBAO");
                iMailStatusBAO.updateMailStatus(master.getClass().getName(),
                        master.getEmail(), mailSendStatus);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        }
        return saveInvestorProfile;
    }

    private boolean saveInvestorNominee(RegistrationVo registrationVo) {
        InvestorNomineeDetailsTb investorNominee = new InvestorNomineeDetailsTb();
        investorNominee.setRegistrationId(registrationVo.getRegId());
        investorNominee.setNameNominee(registrationVo.getNameNominee());
        investorNominee.setRelationshipApplicant(registrationVo.getRelationshipApplicant());
        investorNominee.setDobNominee(registrationVo.getDobNominee());
        investorNominee.setNomineeProof(registrationVo.getNominee_proof());
        investorNominee.setNomineeAadhar(registrationVo.getNominee_aadhar());

        StringBuffer nomineeAddress = new StringBuffer();
        nomineeAddress.append(registrationVo.getAddress1_Line1_Nominee()).append(PIPE_SEPERATOR).append(registrationVo.getAddress1_Street_Nominee())
                .append(PIPE_SEPERATOR).append(registrationVo.getAddress1_Landmark_Nominee()).append(PIPE_SEPERATOR)
                .append(registrationVo.getPincodeNominee());
        investorNominee.setAddressNominee(nomineeAddress.toString());
        investorNominee.setPlaceNominee(registrationVo.getPlaceNominee());
        investorNominee.setPincodeNominee(registrationVo.getPincodeNominee());
        investorNominee.setStateNominee(registrationVo.getStateNominee());
        investorNominee.setCountryNominee(registrationVo.getNomineCountryKey());
        investorNominee.setTelOfficeNominee(registrationVo.getTelOfficeNominee1());
        investorNominee.setTelResidenceNominee(registrationVo.getTelResidenceNominee1());
        investorNominee.setFaxNominee(registrationVo.getFaxNominee1());
        investorNominee.setMobileNominee(registrationVo.getMobileNominee());
        investorNominee.setEmailNominee(registrationVo.getEmailNominee());
        investorNominee.setPanNomine(registrationVo.getNominePan());
        investorNominee.setNomineeMinor(registrationVo.isNomineeMinor());
        if (registrationVo.isNomineeMinor()) {
            investorNominee.setDobNomineeMinor(registrationVo.getDobMinor());
            investorNominee.setNameGuardianNominee(registrationVo.getNameGuardianNominee());
            investorNominee.setMinorProof(registrationVo.getNomineeMinor_proof());
            investorNominee.setMinorAadhar(registrationVo.getNomineeMinor_aadhar());
            investorNominee.setMinorPan(registrationVo.getNomineeMinor_pan());

            StringBuffer nomineeAddressMinor = new StringBuffer();
            nomineeAddressMinor.append(registrationVo.getAddress1_Line1_Minor()).append(PIPE_SEPERATOR).append(registrationVo.getAddress1_Street_Minor())
                    .append(PIPE_SEPERATOR).append(registrationVo.getAddress1_Landmark_Minor()).append(PIPE_SEPERATOR)
                    .append(registrationVo.getPincodeNomineeMinor());
            investorNominee.setAddressNomineeMinor(nomineeAddressMinor.toString());
            investorNominee.setPlaceNomineeMinor(registrationVo.getPlaceNomineeMinor());

            investorNominee.setPincodeNomineeMinor(registrationVo.getPincodeNomineeMinor());
            investorNominee.setStateNomineeMinor(registrationVo.getStateNomineeMinor());
            investorNominee.setCountryNomineeMinor(registrationVo.getNomineMinorCountryKey());
            investorNominee.setTelOfficeNomineeMinor(registrationVo.getTelOfficeNomineeMinor1());
            investorNominee.setTelResidenceNomineeMinor(registrationVo.getTelResidenceNomineeMinor1());
            investorNominee.setFaxNomineeMinor(registrationVo.getFaxNomineeMinor1());
            investorNominee.setMobileNomineeMinor(registrationVo.getMobileNomineeMinor());
            investorNominee.setEmailNomineeMinor(registrationVo.getEmailNomineeMinor());
            investorNominee.setRelationshipGuardianMinor(registrationVo.getRelationshipGuardianMinor());
        }
        boolean status = userProfileDAO.saveInvestorNomineeProfile(investorNominee);
        return status;
    }

    /**
     * Saving Advisor registration data
     *
     * @param userProfile
     * @param applicantStatus
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean saveAdvisorProfile(AdvisorRegistrationVo advisorRegistrationVo, Boolean applicantStatus) {
        StringBuilder logMessage = new StringBuilder("Creating new advisor profile with emailid ");
        logMessage.append(advisorRegistrationVo.getRegEmail());
        LOGGER.info(logMessage.toString());
        master = new MasterApplicantTb();
        master.setRegistrationId(advisorRegistrationVo.getRegNO());
        StringBuffer name = new StringBuffer();
        name.append(advisorRegistrationVo.getFname()).append(SPACE).append(advisorRegistrationVo.getMiddle_name())
                .append(SPACE).append(advisorRegistrationVo.getLast_name());
        master.setName(advisorRegistrationVo.getFname());
        master.setMiddleName(advisorRegistrationVo.getMiddle_name());
        master.setLastName(advisorRegistrationVo.getLast_name());
        master.setSebiRegno(advisorRegistrationVo.getSebi_regno());
        //1. sebi validity
        //2.filepath
        master.setPan(advisorRegistrationVo.getPan());
        master.setDob(advisorRegistrationVo.getDob());
        StringBuffer adminAddress = new StringBuffer();
        adminAddress.append(advisorRegistrationVo.getRaddressLine1()).append(PIPE_SEPERATOR).append(advisorRegistrationVo.getRaddressLine2())
                .append(PIPE_SEPERATOR).append(advisorRegistrationVo.getRlandMark());
        master.setAddress1(adminAddress.toString());
        master.setAddressPincode(advisorRegistrationVo.getRpincode());
        master.setAddressCountry(advisorRegistrationVo.getRcountry());
        master.setAddressState(advisorRegistrationVo.getRstate());
        master.setAddressCity(advisorRegistrationVo.getRcity());
        master.setMobile(advisorRegistrationVo.getRmobile());
        master.setTelephone(advisorRegistrationVo.getRtnumber());
        String risd = null;
        if (advisorRegistrationVo.getRcountry() != null && advisorRegistrationVo.getRcountry().equalsIgnoreCase("IN")) {
            risd = INDIA_ISD;
        }
        master.setTelephoneIsd(risd != null ? risd : advisorRegistrationVo.getRisd());
        master.setTelephoneStd(StringCaseConverter.checkStartingZero(advisorRegistrationVo.getRstd()));
        master.setEmail(advisorRegistrationVo.getRemail());
        master.setAdvisor(advisorRegistrationVo.isIsAdvisor());
        master.setStatus(advisorRegistrationVo.getUserStatus());
        master.setRegisterDatetime(new Date());

        master.setWorkOrganization(advisorRegistrationVo.getOrganization() != null
                ? advisorRegistrationVo.getOrganization() : EMPTY_STRING);
        master.setJobTitle(advisorRegistrationVo.getJobTitle() != null
                ? advisorRegistrationVo.getJobTitle() : EMPTY_STRING);

        master.setPermanentAddress(false);

        if (!advisorRegistrationVo.isSamePermentaddr()) {
            StringBuffer officeAddress = new StringBuffer();
            officeAddress.append(advisorRegistrationVo.getOaddressLine1()).append(PIPE_SEPERATOR).append(advisorRegistrationVo.getOaddressLine2())
                    .append(PIPE_SEPERATOR).append(advisorRegistrationVo.getOlandMark());
            master.setAddress2(officeAddress.toString());

            master.setAddress2Pincode(advisorRegistrationVo.getOpincode());
            master.setAddress2Country(advisorRegistrationVo.getOcountry());
            master.setAddress2State(advisorRegistrationVo.getOstate());
            master.setAddress2City(advisorRegistrationVo.getOcity());
            master.setAddress2Mobil(advisorRegistrationVo.getOmobile());
            master.setTelephone2(advisorRegistrationVo.getOtnumber());

            String oisd = null;
            if (advisorRegistrationVo.getOcountry() != null && advisorRegistrationVo.getOcountry().equalsIgnoreCase("IN")) {
                risd = INDIA_ISD;
            }
            master.setTelephone2Isd(risd != null ? risd : advisorRegistrationVo.getOisd());
            master.setTelephone2Std(StringCaseConverter.checkStartingZero(advisorRegistrationVo.getOstd()));
            master.setAddress2Email(advisorRegistrationVo.getOemail());
        }
        master.setCoresspondenceAddress(advisorRegistrationVo.getCorrespondenceAddress());

        master.setBankName(advisorRegistrationVo.getBankName());
//        master.setBranch(advisorRegistrationVo.getBranch());
        master.setBankSubtype(advisorRegistrationVo.getAccountType());
        master.setAccountNumber(advisorRegistrationVo.getAccountNumber());
        master.setIfcsNumber(advisorRegistrationVo.getIfscNo());
        master.setMicrNumber(advisorRegistrationVo.getMicrNo());

        master.setBankBuilding(advisorRegistrationVo.getBaddressLine1());
        master.setBankStreet(advisorRegistrationVo.getBaddressLine2());
        master.setBankArea(advisorRegistrationVo.getBlandMark());
        master.setBankPincode(advisorRegistrationVo.getBpincode());
        master.setBankCountry(advisorRegistrationVo.getBcountry());
        master.setBankState(advisorRegistrationVo.getBstate());
        master.setBankCity(advisorRegistrationVo.getBcity());
        master.setIsMailVerified(Boolean.TRUE);
        master.setIsActiveUser(Boolean.TRUE);
        master.setLinkedInConnected(false);
        master.setEcsMandateStatus(EnumMandate.NOT_VERIFIED.getValue());
        master.setResCityOther(advisorRegistrationVo.getRes_city_other() == null ? EMPTY_STRING : advisorRegistrationVo.getRes_city_other());
        master.setOffCityOther(advisorRegistrationVo.getOff_city_other() == null ? EMPTY_STRING : advisorRegistrationVo.getOff_city_other());
        master.setBnkCityOther(advisorRegistrationVo.getBnk_city_other() == null ? EMPTY_STRING : advisorRegistrationVo.getBnk_city_other());
        master.setIndvOrCprt(advisorRegistrationVo.isIndividualOrCoprt());
        int id = userProfileDAO.saveAdvisorProfile(master);

        MasterAdvisorQualificationTb masterAdvisorQualification = new MasterAdvisorQualificationTb();

        master.setId(id);
        masterAdvisorQualification.setMasterApplicantTb(master);
        masterAdvisorQualification.setRegistrationId(advisorRegistrationVo.getRegNO());
        masterAdvisorQualification.setPrimaryQualificationDegree(advisorRegistrationVo.getPqualification());
// Qualification id is commented according to the client request
//        masterAdvisorQualification.setPrimaryQualificationId(advisorRegistrationVo.getPqualificationId());
        masterAdvisorQualification.setPrimaryQualificationInstitute(advisorRegistrationVo.getPinsititute());
        masterAdvisorQualification.setPrimaryQualificationYear(advisorRegistrationVo.getPyear());

        masterAdvisorQualification.setSecondaryQualificationDegree(advisorRegistrationVo.getSqualification());
//        masterAdvisorQualification.setSecondaryQualificationId(advisorRegistrationVo.getSqualificationId());
        masterAdvisorQualification.setSecondaryQualificationInstitute(advisorRegistrationVo.getSinsititute());
        masterAdvisorQualification.setSecondaryQualificationYear(advisorRegistrationVo.getSyear());

        masterAdvisorQualification.setTertiaryQualificationDegree(advisorRegistrationVo.getTqualification());
//        masterAdvisorQualification.setTertiaryQualificationId(advisorRegistrationVo.getTqualificationId());
        masterAdvisorQualification.setTertiaryQualificationInstitute(advisorRegistrationVo.getTinsititute());
        masterAdvisorQualification.setTertiaryQualificationYear(advisorRegistrationVo.getTyear());

        masterAdvisorQualification.setSebiCertificatePath(advisorRegistrationVo.getSebiPath());
        masterAdvisorQualification.setValiditySebiCertificate(advisorRegistrationVo.getSebi_validity());

        masterAdvisorQualification.setAdvPicPath(advisorRegistrationVo.getAdvPicPath());
        masterAdvisorQualification.setAboutMe(advisorRegistrationVo.getAboutMe());
        masterAdvisorQualification.setOneLineDesc(advisorRegistrationVo.getOneLineDesc());
        masterAdvisorQualification.setMyInvestmentStrategy(advisorRegistrationVo.getMyInvestmentStrategy());

        saveAdvisorProfile = userProfileDAO.saveAdvisorQualification(masterAdvisorQualification);
        if (saveAdvisorProfile) {
            try {
                boolean mailSendStatus = sendmail(name.toString(), advisorRegistrationVo.getRemail(), master.getRegistrationId(), advisorRegistrationVo.getOmobile(), false);
                IMailStatusBAO iMailStatusBAO = (IMailStatusBAO) BeanLoader.getBean("iMailStatusBAO");
                iMailStatusBAO.updateMailStatus(master.getClass().getName(),
                        master.getEmail(), mailSendStatus);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UserProfileBAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return saveAdvisorProfile;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<UserDetailsVO> listNewUsers(String userType, String searchText, int firstResultIndex, int maxResults, int userSelected) {
        List<MasterApplicantTb> usersList = userProfileDAO.listNewUsers(userType, searchText, firstResultIndex, maxResults, userSelected);
        UserDetailsVO userDetails;
        List<UserDetailsVO> userDetailsList = new ArrayList<UserDetailsVO>();
        for (MasterApplicantTb masterApplicantItem : usersList) {
            userDetails = new UserDetailsVO();
            userDetails.setMasterApplicant(masterApplicantItem);
            userDetails.setDateJoining(DateUtil.dateToString(masterApplicantItem.getRegisterDatetime(), DD_SLASH_MM_SLASH_YYYY));
            userDetails.setDob(masterApplicantItem.getDob());
            String address = masterApplicantItem.getAddress1() != null ? masterApplicantItem.getAddress1()
                    : EMPTY_STRING;
            String[] addtressArray = address.split("\\|");

            userDetails.setHomeAddress(addtressArray.length > 0 && !addtressArray[0].isEmpty()
                    ? addtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetails.setHomeAddress_Line1(addtressArray.length > 1 && !addtressArray[1].isEmpty()
                    ? addtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetails.setHomeAddress_Landmark(addtressArray.length > 2 && !addtressArray[2].isEmpty()
                    ? addtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            userDetails.setRisd(masterApplicantItem.getTelephoneIsd());
            userDetails.setRstd(masterApplicantItem.getTelephoneStd());

            if (masterApplicantItem.getAdvisor()) {
                userDetails.setUserType(YES);
                userDetails.setRe_accountNumber(masterApplicantItem.getAccountNumber());
                if (!masterApplicantItem.getPermanentAddress()) {
                    //Office address for advisor/permenemt address for investor
                    String address2 = masterApplicantItem.getAddress2() != null ? masterApplicantItem.getAddress2()
                            : EMPTY_STRING;
                    String[] addres2sArray = address2.split("\\|");

                    userDetails.setOfficeAddress(addres2sArray.length > 0 && !addres2sArray[0].isEmpty()
                            ? addres2sArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setAddress2_Line1(addres2sArray.length > 1 && !addres2sArray[1].isEmpty()
                            ? addres2sArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setAddress2_Landmark(addres2sArray.length > 2 && !addres2sArray[2].isEmpty()
                            ? addres2sArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                    userDetails.setOisd(masterApplicantItem.getTelephone2Isd());
                    userDetails.setOstd(masterApplicantItem.getTelephone2Std());
                }
            } else {
                userDetails.setUserType(NO);
                if (masterApplicantItem.getPermanentAddress()) {
                    //Office address for advisor/permenemt address for investor
                    String address2 = masterApplicantItem.getAddress2() != null ? masterApplicantItem.getAddress2()
                            : EMPTY_STRING;
                    String[] addres2sArray = address2.split("\\|");

                    userDetails.setOfficeAddress(addres2sArray.length > 0 && !addres2sArray[0].isEmpty()
                            ? addres2sArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setAddress2_Line1(addres2sArray.length > 1 && !addres2sArray[1].isEmpty()
                            ? addres2sArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setAddress2_Landmark(addres2sArray.length > 2 && !addres2sArray[2].isEmpty()
                            ? addres2sArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                    userDetails.setOisd(masterApplicantItem.getTelephone2Isd());
                    userDetails.setOstd(masterApplicantItem.getTelephone2Std());
                }
                String[] segmentArray = new String[3];
                if (masterApplicantItem.getTradingPreferenceSegment() != null) {
                    segmentArray = masterApplicantItem.getTradingPreferenceSegment().split("\\|");
                }
                userDetails.setSelectedSegments(segmentArray);
            }
            if (masterApplicantItem.getStatus() == HUNDRED) {
                userDetails.setStyleClass("green align_center");
            } else if (masterApplicantItem.getStatus() == 13) {
                userDetails.setStyleClass("grey align_center");
            } else if (masterApplicantItem.getStatus() == EnumStatus.MAIL_NOT_VERIFIED.getValue()) {
                userDetails.setStyleClass("red align_center");
            } else {
                userDetails.setStyleClass("align_center");
            }

            if (masterApplicantItem.getEcsMandateStatus() == EnumMandate.VERIFIED.getValue() && !masterApplicantItem.getAdvisor()) {
                userDetails.setMandateStatus(StringCaseConverter.toProperCase((EnumMandate.VERIFIED).toString()));
                userDetails.setMandatestyleClass("green align_center");
            } else if (!masterApplicantItem.getAdvisor()) {
                userDetails.setMandateStatus(StringCaseConverter.toProperCase(EnumMandate.fromInt(masterApplicantItem.getEcsMandateStatus()).toString()));
                userDetails.setMandatestyleClass(masterApplicantItem.getEcsMandateStatus() == EnumMandate.NOT_VERIFIED.getValue()
                        ? "grey align_center" : "red align_center");
            } else {
                userDetails.setMandateStatus(EMPTY_STRING);
                userDetails.setMandatestyleClass("green align_center");
            }
            userDetails.setStatus(StringCaseConverter.toProperCase(EnumStatus.fromInt(masterApplicantItem.getStatus()).toString()));
            userDetails.setPreviousStatus(EnumStatus.fromInt(masterApplicantItem.getStatus()).toString());
            userDetailsList.add(userDetails);

        }
        return userDetailsList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public UserDetailsVO getSelectedUser(UserDetailsVO selectedUser) {
        MandateFormTb manadtetb = null;
        EcsTransmittalSheetTb transmittalSheetTb = null;
        if (selectedUser.getUserType().equalsIgnoreCase(YES)) {
            List advList = userProfileDAO.getAdvisorDetails(selectedUser.getMasterApplicant().getRegistrationId(),
                    selectedUser.getMasterApplicant().getEmail(), true);
            selectedUser.setUserStatus(selectedUser.getMasterApplicant().getStatus());
            selectedUser.setHaveRealtions(Boolean.FALSE);
            MasterAdvisorQualificationTb masterAdvisorQualification
                    = userProfileDAO.getMasterAdvisorQualificationDeatails(selectedUser.getMasterApplicant()
                            .getRegistrationId());
            selectedUser.setQualificationTb(masterAdvisorQualification);
            if (masterAdvisorQualification != null
                    && masterAdvisorQualification.getSebiCertificatePath() != null) {
                selectedUser.setDownloadSebi(true);
            }
            if (!advList.isEmpty()) {
                MasterAdvisorTb advisorDetails = (MasterAdvisorTb) advList.get(ZERO);
                getSelectedAdvisor(advisorDetails, selectedUser);
                selectedUser.setOnlineDetailsSubmitted(advisorDetails.getOnlineDetailsubmites());
                selectedUser.setSebiCertificateValidated(advisorDetails.getSebiCertificateValidated());
                selectedUser.setVerificationCcompleted(advisorDetails.getVerificationCompleted());
                selectedUser.setAccountActivated(advisorDetails.getAccountActivated());
                selectedUser.setExisitngUser(true);
            } else {
                selectedUser.setExisitngUser(false);
            }
        } else {
            List investList = userProfileDAO.getInvestorDetails(selectedUser.getMasterApplicant().getRegistrationId(),
                    selectedUser.getMasterApplicant().getEmail(), true);
            selectedUser.setUserStatus(selectedUser.getMasterApplicant().getStatus());
            selectedUser.setHaveRealtions(Boolean.FALSE);

            //Chages to display ledger, PF and holding in Admin screen
            selectedUser.setPortfolioValue((float) (ZERO));
            selectedUser.setHoldingValue(ZEROD);
            selectedUser.setUnSettledValue(ZEROD);
            selectedUser.setLedgerValue((float) (ZERO));
            if (selectedUser.getMasterApplicant().getStatus() == EnumStatus.ACTIVE.getValue()) {//if active user
                //for active users 
                List customeridlist = userProfileDAO.hasPortfolioMapped(selectedUser.getMasterApplicant().getRegistrationId());
                if (customeridlist != null && !customeridlist.isEmpty()) {
                    List<Object[]> customerportfoliolist = userProfileDAO.getCustomerPortfolioDetails((Integer) customeridlist.get(0));
                    for (Object[] objects : customerportfoliolist) {
                        //Portfolio value
                        selectedUser.setPortfolioValue((Float) objects[0]);
                        //Holding value
                        selectedUser.setHoldingValue((Double) objects[2]);
                        selectedUser.setUnSettledValue(ZEROD);
                        //Ledger
                        selectedUser.setLedgerValue((Float) objects[1]);
                    }
                } else {
                    // For users who didin't have customerid in customerportfolio table
                    List customertablelist = userProfileDAO.getCustomerDetails(selectedUser.getMasterApplicant().getRegistrationId());
                    if (customertablelist != null && !customertablelist.isEmpty()) {

                        Float val = customertablelist.get(0) == null ? ZERO : ((Double) customertablelist.get(0)).floatValue();
                        //Portfolio value
                        selectedUser.setPortfolioValue((float) (ZERO));
                        //Holding value
                        selectedUser.setHoldingValue(ZEROD);
                        selectedUser.setUnSettledValue(ZEROD);
                        //Ledger
                        selectedUser.setLedgerValue(val);
                    }

                }
            }
            boolean fetchFlag = selectedUser.getMasterApplicant().getEcsMandateStatus() == EnumMandate.REJECTED.getValue();
            List<Object> resultSet = userProfileDAO.getMandateTbForRegId(selectedUser.getMasterApplicant().getRegistrationId(), fetchFlag);
            manadtetb = (MandateFormTb) resultSet.get(0);
            if (fetchFlag) {
                transmittalSheetTb = (EcsTransmittalSheetTb) resultSet.get(1);
                StringBuilder rejReason = new StringBuilder();
                rejReason.append(StringCaseConverter.toProperCase((EnumMandate.REJECTED).toString()))
                        .append(HYPHON).append(SPACE).append(transmittalSheetTb.getRemark());
                selectedUser.setMandateFailureReason(rejReason.toString());
            } else {
                selectedUser.setMandateFailureReason(StringCaseConverter.toProperCase(EnumMandate.fromInt(
                        selectedUser.getMasterApplicant().getEcsMandateStatus()).toString()));
            }

            selectedUser.setDownloadCorrespondence(StringUtils.hasText(selectedUser.getMasterApplicant().getCorrespondenceAddressPath()));
            selectedUser.setDownloadPermanentAddress(StringUtils.hasText(selectedUser.getMasterApplicant().getPermanentAddressPath()));
            selectedUser.setDownloadPan(StringUtils.hasText(selectedUser.getMasterApplicant().getIdentityProofPath()));
            selectedUser.setDownloadDocEvidence(StringUtils.hasText(selectedUser.getMasterApplicant().getDocumentaryEvidencePath()));

            if (manadtetb != null) {
                selectedUser.setMandateData(manadtetb);
                String mandatePhone = manadtetb.getCustomerPhone()
                        != null ? manadtetb.getCustomerPhone() : EMPTY_STRING;
                String phoneArray[] = mandatePhone.split("\\" + PIPE_SEPERATOR);
                selectedUser.setMandateisd(phoneArray.length > 0 && !phoneArray[0].isEmpty()
                        ? phoneArray[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                selectedUser.setMandatestd(phoneArray.length > 1 && !phoneArray[1].isEmpty()
                        ? phoneArray[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                selectedUser.setMandatePhone(phoneArray.length > 2 && !phoneArray[2].isEmpty()
                        ? phoneArray[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
            } else {
                manadtetb = new MandateFormTb();
                selectedUser.setMandateData(manadtetb);
            }
            if (selectedUser.getMasterApplicant().getPoliticalyExposed() != null) {
                selectedUser.setPep(selectedUser.getMasterApplicant().getPoliticalyExposed() ? "YES" : "NO");
            }
            if (selectedUser.getMasterApplicant().getPoliticalyRelated() != null) {
                selectedUser.setRpep(selectedUser.getMasterApplicant().getPoliticalyRelated() ? "YES" : "NO");
            }
            if (selectedUser.getMasterApplicant().getPoliticalyExposedSecondHolder() != null) {
                selectedUser.setPoliticalyExposedSecondHolder(selectedUser.getMasterApplicant().getPoliticalyExposedSecondHolder() ? "YES" : "NO");
            }
            if (selectedUser.getMasterApplicant().getPoliticalyRelatedSecondHolder() != null) {
                selectedUser.setPoliticalyRelatedSecondHolder(selectedUser.getMasterApplicant().getPoliticalyRelatedSecondHolder() ? "YES" : "NO");
            }

            if (selectedUser.getMasterApplicant().getFacilityInternetTrading() != null) {
                selectedUser.setFacilityInternetTrading(selectedUser.getMasterApplicant().getFacilityInternetTrading() ? "YES" : "NO");
            }
            if (selectedUser.getMasterApplicant().getSmsFacility() != null) {
                selectedUser.setSmsFacility(selectedUser.getMasterApplicant().getSmsFacility() ? "YES" : "NO");
            }
            if (selectedUser.getMasterApplicant().getSmsFacilitySecondHolder() != null) {
                selectedUser.setSmsFacilitySecondHolder(selectedUser.getMasterApplicant().getSmsFacilitySecondHolder() ? "YES" : "NO");
            }
            if (selectedUser.getMasterApplicant().getInstruction1() != null) {
                selectedUser.setInstruction1(selectedUser.getMasterApplicant().getInstruction1());
            }
            if (selectedUser.getMasterApplicant().getInstruction2() != null) {
                selectedUser.setInstruction2(selectedUser.getMasterApplicant().getInstruction2());
            }
            if (selectedUser.getMasterApplicant().getInstruction3() != null) {
                selectedUser.setInstruction3(selectedUser.getMasterApplicant().getInstruction3());
            }
            if (selectedUser.getMasterApplicant().getInstruction4() != null) {
                selectedUser.setInstruction4(selectedUser.getMasterApplicant().getInstruction4());
            }
            if (selectedUser.getMasterApplicant().getAddressForCommunication() != null) {
                selectedUser.setInstruction5(selectedUser.getMasterApplicant().getAddressForCommunication());
            }
            //for retrieving investor nomination Details
            getInvestorNomineeDetails(selectedUser);
            if (!investList.isEmpty()) {
                MasterCustomerTb investorDetails = (MasterCustomerTb) investList.get(0);
                getSelectedCustomer(investorDetails, selectedUser);
            } else {
                selectedUser.setPanNo(selectedUser.getMasterApplicant().getPan());
                selectedUser.setHomeCountry(selectedUser.getMasterApplicant().getAddressCountry());
                selectedUser.setOfficeTelephone(selectedUser.getMasterApplicant().getTelephone2());
                selectedUser.setTradingBank(selectedUser.getMasterApplicant().getBankName());
                selectedUser.setTradingIFSCNo(selectedUser.getMasterApplicant().getIfcsNumber());
                selectedUser.setTradingAccNo(selectedUser.getMasterApplicant().getAccountNumber());
                selectedUser.setExisitngUser(false);
            }
        }
        return selectedUser;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isEmailExists(String mail) {
        boolean stat = false;
        List mailList = userProfileDAO.getEmailStatus(mail);
        if (!mailList.isEmpty()) {
            stat = true;
        }
        return stat;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isEmailExists(String mail, String regId) {
        boolean stat = false;
        List mailList = userProfileDAO.getEmailStatus(mail, regId);
        if (!mailList.isEmpty()) {
            stat = true;
        }
        return stat;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isPanNoExists(String userType, String panNo, String regId) {
        boolean status = true;
        List advisorPanNoList = userProfileDAO.getPanNoStatusInvestor(panNo, regId);
        if (advisorPanNoList.isEmpty()) {
            status = false;
        }
        return status;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public long sizeOfUsersList(String userType, String searchText) {
        return userProfileDAO.sizeOfUsersList(userType, searchText);
    }

    private UserDetailsVO getSelectedCustomer(MasterCustomerTb investorDetails, UserDetailsVO selectedUser) {
        selectedUser.setUserId(investorDetails.getCustomerId());
        selectedUser.setPassword(investorDetails.getPassword());
        selectedUser.setPanNo(investorDetails.getPanNo());
        selectedUser.setHomeCity(investorDetails.getHomeAddressCity());
        selectedUser.setHomePincode(investorDetails.getHomeAddressPincode());
        selectedUser.setHomeCountry(investorDetails.getHomeAddressCountry());
        selectedUser.setHomeTelephone(investorDetails.getHomeTelephone());
        selectedUser.setOfficeOrganization(investorDetails.getWorkOrganization() != null
                ? investorDetails.getWorkOrganization() : EMPTY_STRING);
        selectedUser.setOfficeJobTitle(investorDetails.getJobTitle() != null
                ? investorDetails.getJobTitle() : EMPTY_STRING);
        selectedUser.setOfficeCity(investorDetails.getWorkAddressCity());
        selectedUser.setOfficePincode(investorDetails.getWorkAddressPincode());
        selectedUser.setOfficeTelephone(investorDetails.getWorkTelephone());
        selectedUser.setOfficeCountry(investorDetails.getWorkAddressCountry());
        selectedUser.setTradingBrokerName(investorDetails.getBrokerName());
        selectedUser.setTradingBrokerAccNo(investorDetails.getBrokerAccountNo());
        selectedUser.setTradingBank(investorDetails.getBankName());
        selectedUser.setTradingIFSCNo(investorDetails.getIfscCode());
        selectedUser.setTradingAccNo(investorDetails.getBankAccountNo());
        selectedUser.setDocStatus(investorDetails.getDocumentStatus());
        selectedUser.setTradingAmount(investorDetails.getAmount());
        selectedUser.setHomeMobile(investorDetails.getMobile());
        selectedUser.setOfficeMobile(investorDetails.getMobile());
        selectedUser.setUserStatus(investorDetails.getMasterApplicantTb().getStatus());
        selectedUser.setExisitngUser(true);
        selectedUser.setInvestorTotalFund(investorDetails.getTotalFunds());
        selectedUser.setInitLogin(investorDetails.getInitLogin());
        selectedUser.setLoggedIn(investorDetails.getLoggedIn());
        selectedUser.setHaveRealtions(!investorDetails.getCustomerAdvisorMappingTbs().isEmpty());
        selectedUser.setLinkedInConnected(investorDetails.getMasterApplicantTb().getLinkedInConnected());
        selectedUser.setOnlineDetailsSubmitted(investorDetails.getOnlineDetailsubmites());
        selectedUser.setForm_couriered_Client(investorDetails.getFormCourieredClient());
        selectedUser.setForm_received_client(investorDetails.getFormReceivedClient());
        selectedUser.setForm_Validated(investorDetails.getFormValidated());
        selectedUser.setAccepted(investorDetails.getAccepted());
        selectedUser.setRejected(investorDetails.getRejected());
        selectedUser.setRejection_Resolved(investorDetails.getRejectionResolved());
        selectedUser.setRejection_Reason(investorDetails.getRejectionReason());
        selectedUser.setAccountActivated(investorDetails.getAccountActivated());
        selectedUser.setuCC_created(investorDetails.getUccCreated());
        selectedUser.setOmsLoginId(investorDetails.getOmsLoginId() == null
                ? EMPTY_STRING : investorDetails.getOmsLoginId());

        return selectedUser;
    }

    private UserDetailsVO getSelectedAdvisor(MasterAdvisorTb advisorDetails, UserDetailsVO selectedUser) {
        selectedUser.setUserId(advisorDetails.getAdvisorId());
        selectedUser.setPassword(advisorDetails.getPassword());
        selectedUser.setPanNo(advisorDetails.getPanNo());
        selectedUser.setDocStatus(advisorDetails.getDocumentStatus());
        selectedUser.setInitLogin(advisorDetails.getInitLogin());
        selectedUser.setLoggedIn(advisorDetails.getLoggedIn());
        selectedUser.setExisitngUser(true);
        selectedUser.setUserStatus(advisorDetails.getMasterApplicantTb().getStatus());
        selectedUser.setHaveRealtions(!advisorDetails.getCustomerAdvisorMappingTbs().isEmpty());
        selectedUser.setLinkedInConnected(advisorDetails.getMasterApplicantTb().getLinkedInConnected());
        return selectedUser;
    }

    private boolean sendmail(String firstName, String email, String reg_id, String mobile, Boolean investor) throws ClassNotFoundException {
        boolean mailStatus = false;
        try {
            StringWriter sw = new StringWriter();
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();
            String url = PropertiesLoader.getPropertiesValue(MMF_DOMAIN_ADDRESS).concat(LookupDataLoader.getContextRoot());
            Template template = ve.getTemplate("RegisrationMail.vm");
            VelocityContext context = TemplateUtil.getInstance().getVelocityContext();
            context.put("newline", NEW_LINE);
            context.put("name", firstName);
            context.put("registrationNo", reg_id);
            context.put("mobile", mobile);
            context.put("url", url);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(email);
            if (investor) {
                mailStatus = MailUtil.getInstance().sendRegMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress, PropertiesLoader.getPropertiesValue(
                        MMF_REGISTRATION_MAIL_SUBJECT), sw.toString(), reg_id, investor);
            } else {
                mailStatus = MailUtil.getInstance().sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress, PropertiesLoader.getPropertiesValue(
                        MMF_REGISTRATION_MAIL_SUBJECT), sw.toString());
            }

        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return mailStatus;
    }

    private boolean sendVerificationMail(String firstName, String email, String url, boolean advisor) throws ClassNotFoundException {
        boolean mailStatus = false;
        try {
            StringWriter sw = new StringWriter();
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();
            Template template = null;
            if (advisor) {
                template = ve.getTemplate("AdvisorVerificationMail.vm");
            } else {
                template = ve.getTemplate("VerificationMail.vm");
            }
            VelocityContext context = TemplateUtil.getInstance().getVelocityContext();
            context.put("newline", NEW_LINE);
            context.put("name", firstName);
            context.put("url", url);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(email);
            mailStatus = MailUtil.getInstance().sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress, PropertiesLoader.getPropertiesValue(
                    MMF_REGISTRATION_MAIL_SUBJECT), sw.toString());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return mailStatus;
    }

    private boolean sendMandateFormmail(String firstName, String email, String reg_id, String mobile, Boolean investor) throws ClassNotFoundException {
        boolean mailStatus = false;
        try {
            StringWriter sw = new StringWriter();
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();

            Template template = ve.getTemplate("RegisrationMail.vm");
            VelocityContext context = TemplateUtil.getInstance().getVelocityContext();
            String url = PropertiesLoader.getPropertiesValue(MMF_DOMAIN_ADDRESS).concat(LookupDataLoader.getContextRoot());
            context.put("newline", NEW_LINE);
            context.put("name", firstName);
            context.put("registrationNo", reg_id);
            context.put("mobile", mobile);
            context.put("url", url);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(email);
            if (investor) {
                mailStatus = MailUtil.getInstance().sendMandateFormMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress, PropertiesLoader.getPropertiesValue(
                        MMF_MANDATEFORM), sw.toString(), reg_id, investor);
            }

        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return mailStatus;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public UserRegStatusVO isUserRegEmailExists(String mail) {
        UserRegStatusVO userRegStatusVO = new UserRegStatusVO();
        List mailList = userProfileDAO.getEmailStatus(mail);
        List tempMailList = userProfileDAO.getEmailStatusTempUser(mail);
        if (!mailList.isEmpty() || !tempMailList.isEmpty()) {
            if (!tempMailList.isEmpty()) {
                userRegStatusVO.setRegStatus(EnumStatus.NEW_APPLICANT.getValue());
            } else {
                userRegStatusVO.setRegStatus(EnumStatus.ACTIVE.getValue());
            }
            userRegStatusVO.setEmailExist(true);
        }
        return userRegStatusVO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Object[] isLinkedinUserExists(String linkedinMemberId) {
        return userProfileDAO.getLinkedinUserStatus(linkedinMemberId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean updateExpireInDet(UserProfileVO userProfileVO) {
        boolean status = false;
        int value = ZERO;
        String username = userProfileVO.getEmail();
        String expirein = userProfileVO.getExpire_in();
        String accesstoken = userProfileVO.getAccess_token();
        Date expireinDate = userProfileVO.getExpire_in_date();
        value = userProfileDAO.updateExpireInDet(username, expirein, expireinDate, accesstoken);
        if (value != ZERO) {
            status = true;
        }
        return status;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<UserDetailsVO> getAllUsers() {
        List<UserDetailsVO> userDetailsVOs = new ArrayList<UserDetailsVO>();
        List<MasterCustomerTb> customerTbs = userProfileDAO.getAllUsers();
        for (MasterCustomerTb masterCustomerTb : customerTbs) {
            UserDetailsVO uVo = new UserDetailsVO();
            uVo.setCin(masterCustomerTb.getRegistrationId());
            uVo.setPanNo(masterCustomerTb.getPanNo());
            uVo.setHomeAddress(masterCustomerTb.getHomeAddress1());
            userDetailsVOs.add(uVo);
        }
        return userDetailsVOs;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Map<String, String> getCityList(String countryCode) {
        Map<String, String> cityMap = new HashMap<String, String>();
        for (CitiesTb citiesTb : userProfileDAO.getCityList(countryCode)) {
            cityMap.put(citiesTb.getCityName(), citiesTb.getCityName());
        }
        return sortByKeys(cityMap);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isemailverified(VerifyMailVO verifyMailVO) {
        try {
            String verificationCode = BASE64Encrption.decrypt(verifyMailVO.getParameterUid());
            String email = BASE64Encrption.decrypt(verifyMailVO.getEmail());
            Integer count = userProfileDAO.emailVerification(verificationCode, email);
            return count.intValue() == ONE;
        } catch (NullPointerException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            return Boolean.FALSE;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public UserDetailsVO getUserProfile(Integer userId, String userType) {
        List<Object> responseList = userProfileDAO.getUserProfile(userId, userType.equals(ADVISOR));
        MasterAdvisorTb masterAdvisorTb = null;
        MasterCustomerTb masterCustomerTb = null;
        UserDetailsVO userDetailsVo = new UserDetailsVO();
        if (userType.equals(ADVISOR)) {
            masterAdvisorTb = (MasterAdvisorTb) responseList.get(ZERO);
            userDetailsVo.setUserId(masterAdvisorTb.getAdvisorId());
            userDetailsVo.setPassword(masterAdvisorTb.getPassword());
            userDetailsVo.setInitLogin(masterAdvisorTb.getInitLogin());
            userDetailsVo.setUserType(YES);//Advisor
            userDetailsVo.setPreviousStatus(EnumStatus.ACTIVE.toString());
            String address = masterAdvisorTb.getHomeAddress1() != null ? masterAdvisorTb.getHomeAddress1() : EMPTY_STRING;

            String[] addtressArray = address.split("\\|");
            userDetailsVo.setHomeAddress_Line1(addtressArray.length > 0 && !addtressArray[0].isEmpty()
                    ? addtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setHomeAddress_Street(addtressArray.length > 1 && !addtressArray[1].isEmpty()
                    ? addtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setHomeAddress_Landmark(addtressArray.length > 2 && !addtressArray[2].isEmpty()
                    ? addtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            String pAddress = masterAdvisorTb.getMasterApplicantTb().getAddress2() != null
                    ? masterAdvisorTb.getMasterApplicantTb().getAddress2() : EMPTY_STRING;

            String[] pAddtressArray = pAddress.split("\\|");
            userDetailsVo.setOfficeAddress(pAddtressArray.length > 0 && !pAddtressArray[0].isEmpty()
                    ? pAddtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setAddress2_Line1(pAddtressArray.length > 1 && !pAddtressArray[1].isEmpty()
                    ? pAddtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setAddress2_Landmark(pAddtressArray.length > 2 && !pAddtressArray[2].isEmpty()
                    ? pAddtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            userDetailsVo.setMasterApplicant(masterAdvisorTb.getMasterApplicantTb());
            userDetailsVo.setDob(masterAdvisorTb.getDob());
            userDetailsVo.setPanNo(masterAdvisorTb.getPanNo());
            userDetailsVo.setSebiCertificationNo(masterAdvisorTb.getMasterApplicantTb().getSebiRegno());
            userDetailsVo.setHomeCountry(masterAdvisorTb.getHomeAddressCountry());
            userDetailsVo.setHomeCity(masterAdvisorTb.getHomeAddressCity());
            userDetailsVo.setHomePincode(masterAdvisorTb.getHomeAddressPincode());
            userDetailsVo.setHomeTelephone(masterAdvisorTb.getHomeTelephone());
            userDetailsVo.setRisd(masterAdvisorTb.getMasterApplicantTb().getTelephoneIsd());
            userDetailsVo.setRstd(masterAdvisorTb.getMasterApplicantTb().getTelephoneStd());
            userDetailsVo.setHomeMobile(masterAdvisorTb.getMobile());
            userDetailsVo.setOfficeOrganization(masterAdvisorTb.getWorkOrganization() != null
                    ? masterAdvisorTb.getWorkOrganization() : EMPTY_STRING);
            userDetailsVo.setOfficeJobTitle(masterAdvisorTb.getJobTitle() != null
                    ? masterAdvisorTb.getJobTitle() : EMPTY_STRING);
            userDetailsVo.setOfficeCountry(masterAdvisorTb.getWorkAddressCountry());
            userDetailsVo.setOfficeCity(masterAdvisorTb.getWorkAddressCity());
            userDetailsVo.setOfficePincode(masterAdvisorTb.getWorkAddressPincode());
            userDetailsVo.setOfficeTelephone(masterAdvisorTb.getWorkTelephone());
            userDetailsVo.setOfficeMobile(masterAdvisorTb.getWorkMobile());
            MasterAdvisorQualificationTb masterAdvisorQualification
                    = userProfileDAO.getMasterAdvisorQualificationDeatails(masterAdvisorTb.getRegistrationId());
            userDetailsVo.setQualificationTb(masterAdvisorQualification);
            masterAdvisorTb.setMasterAdvisorQualificationTb(masterAdvisorQualification);
//    Qualification id is commented according to the client request
//            userDetailsVo.getQualificationTb().setPrimaryQualificationId(masterAdvisorTb.getMasterAdvisorQualificationTb()
//                    .getPrimaryQualificationId());
            userDetailsVo.getQualificationTb().setPrimaryQualificationInstitute(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getPrimaryQualificationInstitute());
            userDetailsVo.getQualificationTb().setPrimaryQualificationYear(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getPrimaryQualificationYear());
            userDetailsVo.getQualificationTb().setPrimaryQualificationDegree(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getPrimaryQualificationDegree());
//            userDetailsVo.getQualificationTb().setSecondaryQualificationId(masterAdvisorTb.getMasterAdvisorQualificationTb()
//                    .getSecondaryQualificationId());
            userDetailsVo.getQualificationTb().setSecondaryQualificationInstitute(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getSecondaryQualificationInstitute());
            userDetailsVo.getQualificationTb().setSecondaryQualificationYear(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getSecondaryQualificationYear());
            userDetailsVo.getQualificationTb().setSecondaryQualificationDegree(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getSecondaryQualificationDegree());
//            userDetailsVo.getQualificationTb().setTertiaryQualificationId(masterAdvisorTb.getMasterAdvisorQualificationTb()
//                    .getTertiaryQualificationId());
            userDetailsVo.getQualificationTb().setTertiaryQualificationInstitute(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getTertiaryQualificationInstitute());
            userDetailsVo.getQualificationTb().setTertiaryQualificationYear(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getTertiaryQualificationYear());
            userDetailsVo.getQualificationTb().setTertiaryQualificationDegree(masterAdvisorTb.getMasterAdvisorQualificationTb()
                    .getTertiaryQualificationDegree());
            userDetailsVo.setTradingBrokerName(EMPTY_STRING);
            userDetailsVo.setTradingBrokerAccNo(masterAdvisorTb.getBrokerAccountNo());
            userDetailsVo.setTradingBank(masterAdvisorTb.getBankName());
            userDetailsVo.setTradingIFSCNo(masterAdvisorTb.getIfscCode());
            userDetailsVo.setTradingAccNo(masterAdvisorTb.getBankAccountNo());
            userDetailsVo.setRe_accountNumber(masterAdvisorTb.getMasterApplicantTb().getAccountNumber());
            userDetailsVo.setTradingAmount(masterAdvisorTb.getAmount());
            userDetailsVo.setLinkedInConnected(masterAdvisorTb.getMasterApplicantTb().getLinkedInConnected());
        } else {
            masterCustomerTb = (MasterCustomerTb) responseList.get(ZERO);
            userDetailsVo.setUserId(masterCustomerTb.getCustomerId());
            userDetailsVo.setPassword(masterCustomerTb.getPassword());
            userDetailsVo.setInitLogin(masterCustomerTb.getInitLogin());
            userDetailsVo.setUserType(NO);//Investor
            userDetailsVo.setPreviousStatus(EnumStatus.ACTIVE.toString());
            userDetailsVo.setMasterApplicant(masterCustomerTb.getMasterApplicantTb());
            userDetailsVo.setDob(masterCustomerTb.getDob());
            userDetailsVo.setPanNo(masterCustomerTb.getPanNo());
            userDetailsVo.setSebiCertificationNo(masterCustomerTb.getMasterApplicantTb().getSebiRegno());
            String address = masterCustomerTb.getHomeAddress1() != null ? masterCustomerTb.getHomeAddress1() : EMPTY_STRING;

            String[] addtressArray = address.split("\\|");
            userDetailsVo.setHomeAddress(addtressArray.length > 0 && !addtressArray[0].isEmpty()
                    ? addtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setHomeAddress_Line1(addtressArray.length > 1 && !addtressArray[1].isEmpty()
                    ? addtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setHomeAddress_Landmark(addtressArray.length > 2 && !addtressArray[2].isEmpty()
                    ? addtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            String pAddress = masterCustomerTb.getMasterApplicantTb().getAddress2() != null
                    ? masterCustomerTb.getMasterApplicantTb().getAddress2() : EMPTY_STRING;

            String[] pAddtressArray = pAddress.split("\\|");
            userDetailsVo.setAddress2_Line1(pAddtressArray.length > 0 && !pAddtressArray[0].isEmpty()
                    ? pAddtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setAddress2_Street(pAddtressArray.length > 1 && !pAddtressArray[1].isEmpty()
                    ? pAddtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            userDetailsVo.setAddress2_Landmark(pAddtressArray.length > 2 && !pAddtressArray[2].isEmpty()
                    ? pAddtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            userDetailsVo.setHomeCountry(masterCustomerTb.getHomeAddressCountry());
            userDetailsVo.setHomeCity(masterCustomerTb.getHomeAddressCity());
            userDetailsVo.setHomePincode(masterCustomerTb.getHomeAddressPincode());
            userDetailsVo.setHomeTelephone(masterCustomerTb.getHomeTelephone());
            userDetailsVo.setHomeMobile(masterCustomerTb.getMobile());
            userDetailsVo.setOfficeOrganization(masterCustomerTb.getWorkOrganization() != null
                    ? masterCustomerTb.getWorkOrganization() : EMPTY_STRING);
            userDetailsVo.setOfficeJobTitle(masterCustomerTb.getJobTitle() != null
                    ? masterCustomerTb.getJobTitle() : EMPTY_STRING);
            userDetailsVo.setOfficeAddress(masterCustomerTb.getWorkAddress1());
            userDetailsVo.setOfficeCountry(masterCustomerTb.getWorkAddressCountry());
            userDetailsVo.setOfficeCity(masterCustomerTb.getWorkAddressCity());
            userDetailsVo.setOfficePincode(masterCustomerTb.getWorkAddressPincode());
            userDetailsVo.setOfficeTelephone(masterCustomerTb.getWorkTelephone());
            userDetailsVo.setOfficeMobile(masterCustomerTb.getWorkMobile());
            userDetailsVo.setTradingBrokerName(masterCustomerTb.getBrokerName());
            userDetailsVo.setTradingBrokerAccNo(masterCustomerTb.getBrokerAccountNo());
            userDetailsVo.setTradingBank(masterCustomerTb.getBankName());
            userDetailsVo.setTradingIFSCNo(masterCustomerTb.getIfscCode());
            userDetailsVo.setTradingAccNo(masterCustomerTb.getBankAccountNo());
            userDetailsVo.setTradingAmount(masterCustomerTb.getTotalFunds());
            userDetailsVo.setLinkedInConnected(masterCustomerTb.getMasterApplicantTb().getLinkedInConnected());
            userDetailsVo.setRe_accountNumber(masterCustomerTb.getBankAccountNo());

            if (userDetailsVo.getMasterApplicant().getInstruction1() != null) {
                userDetailsVo.setInstruction1(masterCustomerTb.getMasterApplicantTb().getInstruction1());
            }
            if (userDetailsVo.getMasterApplicant().getInstruction2() != null) {
                userDetailsVo.setInstruction2(masterCustomerTb.getMasterApplicantTb().getInstruction2());
            }
            if (userDetailsVo.getMasterApplicant().getInstruction3() != null) {
                userDetailsVo.setInstruction3(masterCustomerTb.getMasterApplicantTb().getInstruction3());
            }
            if (userDetailsVo.getMasterApplicant().getInstruction4() != null) {
                userDetailsVo.setInstruction4(masterCustomerTb.getMasterApplicantTb().getInstruction4());
            }
            if (userDetailsVo.getMasterApplicant().getAddressForCommunication() != null) {
                userDetailsVo.setInstruction5(masterCustomerTb.getMasterApplicantTb().getAddressForCommunication());
            }

            if (userDetailsVo.getMasterApplicant().getPoliticalyExposed() != null) {
                userDetailsVo.setPep(userDetailsVo.getMasterApplicant().getPoliticalyExposed() ? "YES" : "NO");
            }
            if (userDetailsVo.getMasterApplicant().getPoliticalyRelated() != null) {
                userDetailsVo.setRpep(userDetailsVo.getMasterApplicant().getPoliticalyRelated() ? "YES" : "NO");
            }
            if (userDetailsVo.getMasterApplicant().getPoliticalyExposedSecondHolder() != null) {
                userDetailsVo.setPoliticalyExposedSecondHolder(userDetailsVo.getMasterApplicant().getPoliticalyExposedSecondHolder() ? "YES" : "NO");
            }
            if (userDetailsVo.getMasterApplicant().getPoliticalyRelatedSecondHolder() != null) {
                userDetailsVo.setPoliticalyRelatedSecondHolder(userDetailsVo.getMasterApplicant().getPoliticalyRelatedSecondHolder() ? "YES" : "NO");
            }

            if (userDetailsVo.getMasterApplicant().getFacilityInternetTrading() != null) {
                userDetailsVo.setFacilityInternetTrading(userDetailsVo.getMasterApplicant().getFacilityInternetTrading() ? "YES" : "NO");
            }
            if (userDetailsVo.getMasterApplicant().getSmsFacility() != null) {
                userDetailsVo.setSmsFacility(userDetailsVo.getMasterApplicant().getSmsFacility() ? "YES" : "NO");
            }
            if (userDetailsVo.getMasterApplicant().getSmsFacilitySecondHolder() != null) {
                userDetailsVo.setSmsFacilitySecondHolder(userDetailsVo.getMasterApplicant().getSmsFacilitySecondHolder() ? "YES" : "NO");
            }
            if (userDetailsVo.getMasterApplicant().getInstruction1() != null) {
                userDetailsVo.setInstruction1(userDetailsVo.getMasterApplicant().getInstruction1());
            }
            if (userDetailsVo.getMasterApplicant().getInstruction2() != null) {
                userDetailsVo.setInstruction2(userDetailsVo.getMasterApplicant().getInstruction2());
            }
            if (userDetailsVo.getMasterApplicant().getInstruction3() != null) {
                userDetailsVo.setInstruction3(userDetailsVo.getMasterApplicant().getInstruction3());
            }
            if (userDetailsVo.getMasterApplicant().getInstruction4() != null) {
                userDetailsVo.setInstruction4(userDetailsVo.getMasterApplicant().getInstruction4());
            }
            if (userDetailsVo.getMasterApplicant().getAddressForCommunication() != null) {
                userDetailsVo.setInstruction5(userDetailsVo.getMasterApplicant().getAddressForCommunication());
            }
            //for retrieving investor nomination Details
            getInvestorNomineeDetails(userDetailsVo);

        }
        return userDetailsVo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Boolean deleteUser(UserDetailsVO userDetailsVO, Boolean isHardDelete) throws Exception {
        Boolean returnValue = Boolean.FALSE;
        try {

            MasterApplicantTb masterApplicantTb = new MasterApplicantTb();
            masterApplicantTb.setId(userDetailsVO.getMasterApplicant().getId());
            masterApplicantTb.setRegistrationId(userDetailsVO.getMasterApplicant().getRegistrationId());
            if (userDetailsVO.getUserType().equals(YES)) {
                MasterAdvisorTb masterAdvisorTb = new MasterAdvisorTb();
                masterAdvisorTb.setAdvisorId(userDetailsVO.getUserId());
                if (!isHardDelete) {
                    masterApplicantTb.setStatus(EnumStatus.INACTIVE.getValue());
                    masterAdvisorTb.setIsActiveUser(Boolean.FALSE);
                }
                masterAdvisorTb.setMasterApplicantTb(masterApplicantTb);
                returnValue = userProfileDAO.deleteAdvisor(masterAdvisorTb, isHardDelete) > ZERO
                        ? Boolean.TRUE : Boolean.FALSE;
            } else {
                MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
                masterCustomerTb.setCustomerId(userDetailsVO.getUserId());
                if (!isHardDelete) {
                    masterApplicantTb.setStatus(EnumStatus.INACTIVE.getValue());
                    masterCustomerTb.setIsActiveUser(Boolean.FALSE);
                }
                masterCustomerTb.setMasterApplicantTb(masterApplicantTb);
                returnValue = userProfileDAO.deleteCustomer(masterCustomerTb, isHardDelete) > ZERO
                        ? Boolean.TRUE : Boolean.FALSE;
            }
        } catch (Exception ex) {
            returnValue = Boolean.FALSE;
            throw new Exception(ex);
        }
        return returnValue;
    }

    private static <K extends Comparable, V extends Comparable> Map<K, V> sortByKeys(Map<K, V> map) {
        List<K> keys = new LinkedList<K>(map.keySet());
        Collections.sort(keys);
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        for (K key : keys) {
            sortedMap.put(key, map.get(key));
        }

        return sortedMap;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Boolean saveTempUserProfile(String email, String password, boolean advisor) {

        TempRegistrationTb tempRegistration = new TempRegistrationTb();
        tempRegistration.setEmail(email);
        tempRegistration.setUserType(advisor == true ? ADVISOR : INVESTOR);
        tempRegistration.setMailVerified(false);
        SecurityUtility sec = SecurityUtility.getInstance();
        tempRegistration.setPassword(sec.encrypt(password));
        String verfyCode = UUIDUtil.genrateUUID();
        tempRegistration.setVerificationCode(verfyCode);
        tempRegistration.setInitLogin(ONE);
        boolean status = userProfileDAO.saveTempUserProfile(tempRegistration);

        if (status) {
            try {
                String url = MailUtil.getInstance().generateVerificationURL(tempRegistration);
                boolean mailSendStatus = sendVerificationMail(tempRegistration.getEmail(), tempRegistration.getEmail(), url, advisor);
                IMailStatusBAO iMailStatusBAO = (IMailStatusBAO) BeanLoader.getBean("iMailStatusBAO");
                iMailStatusBAO.updateMailStatus(tempRegistration.getClass().getName(),
                        tempRegistration.getEmail(), mailSendStatus);
                System.out.println(url);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));

            } catch (Exception ex) {
                Logger.getLogger(UserProfileBAOImpl.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public RegistrationVo getNewApplicantDetails(MasterApplicantTb masterapplicantTb) {
        RegistrationVo regVo = new RegistrationVo();
        regVo.setRegId(masterapplicantTb.getRegistrationId());
        regVo.setId(masterapplicantTb.getId());
        regVo.setName(masterapplicantTb.getName() == null ? EMPTY_STRING : masterapplicantTb.getName());
        regVo.setMiddleName(masterapplicantTb.getMiddleName() == null ? EMPTY_STRING
                : masterapplicantTb.getMiddleName());
        regVo.setLastName(masterapplicantTb.getLastName() == null ? EMPTY_STRING
                : masterapplicantTb.getLastName());
        regVo.setFatherOrSpouseName(masterapplicantTb.getFatherSpouseName() == null ? EMPTY_STRING
                : masterapplicantTb.getFatherSpouseName());
        regVo.setDob(masterapplicantTb.getDob() == null ? null : masterapplicantTb.getDob());
        regVo.setNationality(masterapplicantTb.getNationality() == null ? EMPTY_STRING
                : masterapplicantTb.getNationality());
        regVo.setStatus(masterapplicantTb.getResidentialStatus() == null ? EMPTY_STRING
                : masterapplicantTb.getResidentialStatus());
        regVo.setGender(masterapplicantTb.getGender() == null ? EMPTY_STRING : masterapplicantTb.getGender());
        regVo.setMaritalStatus(masterapplicantTb.getMaritalStatus() == null ? EMPTY_STRING
                : masterapplicantTb.getMaritalStatus());
        regVo.setPan(masterapplicantTb.getPan() == null ? EMPTY_STRING : masterapplicantTb.getPan());
        regVo.setUid(masterapplicantTb.getUidAadhar() == null ? EMPTY_STRING
                : masterapplicantTb.getUidAadhar());
        regVo.setIdProof(masterapplicantTb.getProofOfIdentity() == null ? EMPTY_STRING
                : masterapplicantTb.getProofOfIdentity());

        String address = masterapplicantTb.getAddress1() != null ? masterapplicantTb.getAddress1()
                : EMPTY_STRING;
        String[] addtressArray = address.split("\\|");

        regVo.setAddress1_Line1(addtressArray.length > 0 && !addtressArray[0].isEmpty()
                ? addtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
        regVo.setAddress1_Street(addtressArray.length > 1 && !addtressArray[1].isEmpty()
                ? addtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
        regVo.setAddress1_Landmark(addtressArray.length > 2 && !addtressArray[2].isEmpty()
                ? addtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
        regVo.setAddress1_Zipcode(addtressArray.length > 3 && !addtressArray[3].isEmpty()
                ? addtressArray[3].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

        regVo.setCcountry(masterapplicantTb.getAddressCountry() == null ? EMPTY_STRING
                : masterapplicantTb.getAddressCountry());
        regVo.setCstate(masterapplicantTb.getAddressState() == null ? EMPTY_STRING
                : masterapplicantTb.getAddressState());
        regVo.setCcity(masterapplicantTb.getAddressCity() == null ? EMPTY_STRING
                : masterapplicantTb.getAddressCity());
        regVo.setCpinCode(masterapplicantTb.getAddressPincode() == null ? EMPTY_STRING
                : masterapplicantTb.getAddressPincode());

        String pAddress = masterapplicantTb.getAddress2() != null ? masterapplicantTb.getAddress2()
                : EMPTY_STRING;
        String[] pAddtressArray = pAddress.split("\\|");

        regVo.setAddress2_Line1(pAddtressArray.length > 0 && !pAddtressArray[0].isEmpty()
                ? pAddtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
        regVo.setAddress2_Street(pAddtressArray.length > 1 && !pAddtressArray[1].isEmpty()
                ? pAddtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
        regVo.setAddress2_Landmark(pAddtressArray.length > 2 && !pAddtressArray[2].isEmpty()
                ? pAddtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
        regVo.setAddress2_Zipcode(pAddtressArray.length > 3 && !pAddtressArray[3].isEmpty()
                ? pAddtressArray[3].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

        regVo.setPcountry(masterapplicantTb.getAddress2Country() == null ? EMPTY_STRING
                : masterapplicantTb.getAddress2Country());
        regVo.setPstate(masterapplicantTb.getAddress2State() == null ? EMPTY_STRING
                : masterapplicantTb.getAddress2State());
        regVo.setPcity(masterapplicantTb.getAddress2City() == null ? EMPTY_STRING
                : masterapplicantTb.getAddress2City());
        regVo.setPpinCode(masterapplicantTb.getAddress2Pincode() == null ? EMPTY_STRING
                : masterapplicantTb.getAddress2Pincode());
        regVo.setAddress2_proof(masterapplicantTb.getAddress2Proof() != null
                ? masterapplicantTb.getAddress2Proof() : EMPTY_STRING);
        String isd = masterapplicantTb.getAddressCountry().equalsIgnoreCase("IN") ? "+91" : null;
        if (StringUtils.hasText(masterapplicantTb.getTelephone())) {

            regVo.setRisd(isd != null ? isd : masterapplicantTb.getTelephoneIsd());
            regVo.setRstd(StringCaseConverter.checkStartingZero(masterapplicantTb.getTelephoneStd()));
            regVo.setRnumber(masterapplicantTb.getTelephone());
        }
        if (StringUtils.hasText(masterapplicantTb.getTelephone2())) {

            regVo.setHisd(isd != null ? isd : masterapplicantTb.getTelephone2Isd());
            regVo.setHstd(StringCaseConverter.checkStartingZero(masterapplicantTb.getTelephone2Std()));
            regVo.setHnumber(masterapplicantTb.getTelephone2());
        }

        if (StringUtils.hasText(masterapplicantTb.getFax())) {
            regVo.setFisd(isd != null ? isd : masterapplicantTb.getFaxIsd());
            regVo.setFstd(StringCaseConverter.checkStartingZero(masterapplicantTb.getFaxStd()));
            regVo.setFnumber(masterapplicantTb.getFax());
        }
        regVo.setMobileNumber(masterapplicantTb.getMobile() == null ? EMPTY_STRING
                : masterapplicantTb.getMobile());
        regVo.setEmail(masterapplicantTb.getEmail() == null ? EMPTY_STRING : masterapplicantTb.getEmail());
        regVo.setAddressProof(masterapplicantTb.getProofOfAddress() == null ? EMPTY_STRING
                : masterapplicantTb.getProofOfAddress());
        regVo.setDobValidity(masterapplicantTb.getExpiryDate() == null ? null
                : masterapplicantTb.getExpiryDate());
        regVo.setAddress2_validity(masterapplicantTb.getAddress2Validity() == null ? null
                : masterapplicantTb.getAddress2Validity());
        regVo.setBankname(masterapplicantTb.getBankName() == null ? EMPTY_STRING
                : masterapplicantTb.getBankName());
//        regVo.setBranch(masterapplicantTb.getBranch() == null ? EMPTY_STRING
//                : masterapplicantTb.getBranch());
        regVo.setBuilding(masterapplicantTb.getBankBuilding() == null ? EMPTY_STRING
                : masterapplicantTb.getBankBuilding());
        regVo.setBarea(masterapplicantTb.getBankArea() == null ? EMPTY_STRING
                : masterapplicantTb.getBankArea());
        regVo.setBstreet(masterapplicantTb.getBankStreet() == null ? EMPTY_STRING
                : masterapplicantTb.getBankStreet());
        regVo.setBcity(masterapplicantTb.getBankCity() == null ? EMPTY_STRING
                : masterapplicantTb.getBankCity());
        regVo.setBpincode(masterapplicantTb.getBankPincode() == null ? EMPTY_STRING
                : masterapplicantTb.getBankPincode());
        regVo.setBankSubType(masterapplicantTb.getBankSubtype() == null ? EMPTY_STRING
                : masterapplicantTb.getBankSubtype());
        regVo.setBankAccountNumber(masterapplicantTb.getAccountNumber() == null ? EMPTY_STRING
                : masterapplicantTb.getAccountNumber());
        regVo.setIfsc(masterapplicantTb.getIfcsNumber() == null ? EMPTY_STRING
                : masterapplicantTb.getIfcsNumber());
        regVo.setMicrCode(masterapplicantTb.getMicrNumber() == null ? EMPTY_STRING
                : masterapplicantTb.getMicrNumber());
        regVo.setBnk_city_other(masterapplicantTb.getBnkCityOther());
        regVo.setOpenAccountType(masterapplicantTb.getAccountTypeOpen() == null ? EMPTY_STRING
                : masterapplicantTb.getAccountTypeOpen());
        regVo.setClientId(masterapplicantTb.getClientId() == null ? EMPTY_STRING
                : masterapplicantTb.getClientId());
        regVo.setDpId(masterapplicantTb.getDpId() == null ? EMPTY_STRING
                : masterapplicantTb.getDpId());
        regVo.setTradingtAccount(masterapplicantTb.getTradingAccountType() == null ? EMPTY_STRING
                : masterapplicantTb.getTradingAccountType());
        regVo.setDematAccount(masterapplicantTb.getDematAccountType() == null ? EMPTY_STRING
                : masterapplicantTb.getDematAccountType());

        regVo.setSmsFacility(masterapplicantTb.getSmsFacility() != null
                && masterapplicantTb.getSmsFacility() == true
                        ? "0" : "1");

        regVo.setNameOfEmployerOrCompany(masterapplicantTb.getWorkOrganization() == null ? EMPTY_STRING
                : masterapplicantTb.getWorkOrganization());
        regVo.setDesignation(masterapplicantTb.getJobTitle() == null ? EMPTY_STRING
                : masterapplicantTb.getJobTitle());
        regVo.setOccupation(masterapplicantTb.getOccupation() == null ? EMPTY_STRING
                : masterapplicantTb.getOccupation());
        regVo.setIncomerange(masterapplicantTb.getIncomeRange() == null ? EMPTY_STRING
                : masterapplicantTb.getIncomeRange());
        regVo.setNetWorthdate(masterapplicantTb.getNetWorthDate() == null ? null
                : masterapplicantTb.getNetWorthDate());
        regVo.setNetAmmount(masterapplicantTb.getAmount() == null ? EMPTY_STRING
                : masterapplicantTb.getAmount());
        regVo.setPep(masterapplicantTb.getPoliticalyExposed() == null ? null
                : masterapplicantTb.getPoliticalyExposed().toString());
        regVo.setRpep(masterapplicantTb.getPoliticalyRelated() == null ? null
                : masterapplicantTb.getPoliticalyRelated().toString());

        regVo.setInstruction1(masterapplicantTb.getInstruction1() == null ? null
                : masterapplicantTb.getInstruction1().toString());
        regVo.setInstruction2(masterapplicantTb.getInstruction2() == null ? null
                : masterapplicantTb.getInstruction2().toString());
        regVo.setInstruction3(masterapplicantTb.getInstruction3() == null ? null
                : masterapplicantTb.getInstruction3().toString());
        regVo.setInstruction4(masterapplicantTb.getInstruction4() == null ? null
                : masterapplicantTb.getInstruction4().toString());
        regVo.setInstruction5(masterapplicantTb.getAddressForCommunication() == null ? null
                : masterapplicantTb.getAddressForCommunication());
        regVo.setPermenentAddress(masterapplicantTb.getPermanentAddress() == null ? false
                : masterapplicantTb.getPermanentAddress());
        regVo.setLinkedInConnected(masterapplicantTb.getLinkedInConnected());
        regVo.setAdvisor(false);
        regVo.setSecondHolderDetailsAvailable(masterapplicantTb.getSecondHolderDetailsAvailable() != null
                ? masterapplicantTb.getSecondHolderDetailsAvailable() : false);
        regVo.setSmsFacilitySecondHolder(masterapplicantTb.getSmsFacilitySecondHolder() != null
                && masterapplicantTb.getSmsFacilitySecondHolder() == true
                        ? "0" : "1");
        regVo.setNameSecondHolder(masterapplicantTb.getNameSecondHolder() != null
                ? masterapplicantTb.getNameSecondHolder() : EMPTY_STRING);
        regVo.setOccupationSecondHolder(masterapplicantTb.getOccupationSecondHolder() != null
                ? masterapplicantTb.getOccupationSecondHolder() : EMPTY_STRING);
        regVo.setIncomeRangeSecondHolder(masterapplicantTb.getIncomeRangeSecondHolder() != null
                ? masterapplicantTb.getIncomeRangeSecondHolder() : EMPTY_STRING);
        regVo.setNetWorthDateSecondHolder(masterapplicantTb.getNetWorthDateSecondHolder() != null
                ? masterapplicantTb.getNetWorthDateSecondHolder() : null);
        regVo.setAmountSecondHolder(masterapplicantTb.getAmountSecondHolder() != null
                ? masterapplicantTb.getAmountSecondHolder() : EMPTY_STRING);
        regVo.setPoliticalyExposedSecondHolder(masterapplicantTb.getPoliticalyExposedSecondHolder() == null
                ? null : masterapplicantTb.getPoliticalyExposedSecondHolder().toString());
        regVo.setPoliticalyRelatedSecondHolder(masterapplicantTb.getPoliticalyRelatedSecondHolder() == null
                ? null : masterapplicantTb.getPoliticalyRelatedSecondHolder().toString());
        regVo.setNameEmployerSecondHolder(masterapplicantTb.getNameEmployerSecondHolder() != null
                ? masterapplicantTb.getNameEmployerSecondHolder() : EMPTY_STRING);
        regVo.setDesignationSecondHolder(masterapplicantTb.getDesignationSecondHolder() != null
                ? masterapplicantTb.getDesignationSecondHolder() : EMPTY_STRING);
        regVo.setRbiReferenceNumber(masterapplicantTb.getRbiReferenceNumber() != null
                ? masterapplicantTb.getRbiReferenceNumber() : EMPTY_STRING);
        regVo.setRbiApprovalDate(masterapplicantTb.getRbiApprovalDate() != null
                ? masterapplicantTb.getRbiApprovalDate() : null);
        regVo.setDepositoryParticipantName(masterapplicantTb.getDepositoryParticipantName() != null
                ? masterapplicantTb.getDepositoryParticipantName() : EMPTY_STRING);
        regVo.setDepositoryName(masterapplicantTb.getDepositoryName() != null
                ? masterapplicantTb.getDepositoryName() : EMPTY_STRING);
        regVo.setBeneficiaryName(masterapplicantTb.getBeneficiaryName() != null
                ? masterapplicantTb.getBeneficiaryName() : EMPTY_STRING);
        regVo.setDpIdDepository(masterapplicantTb.getDpIdDepository() != null
                ? masterapplicantTb.getDpIdDepository() : EMPTY_STRING);
        regVo.setBeneficiaryId(masterapplicantTb.getBeneficiaryId() != null
                ? masterapplicantTb.getBeneficiaryId() : EMPTY_STRING);
        regVo.setTradingPreferenceExchange(masterapplicantTb.getTradingPreferenceExchange() != null
                ? masterapplicantTb.getTradingPreferenceExchange() : EMPTY_STRING);
        regVo.setTradingPreferenceSegment(masterapplicantTb.getTradingPreferenceSegment() != null
                ? masterapplicantTb.getTradingPreferenceSegment() : EMPTY_STRING);
        String[] segmentArray = new String[3];
        segmentArray = regVo.getTradingPreferenceSegment().split("\\|");
        regVo.setSelectedSegments(segmentArray);
        regVo.setDocumentaryEvedenceOther(masterapplicantTb.getDocumentaryEvedenceOther() != null
                ? masterapplicantTb.getDocumentaryEvedenceOther() : false);

        regVo.setDocumentaryEvedence(masterapplicantTb.getDocumentaryEvedence() != null
                ? masterapplicantTb.getDocumentaryEvedence() : EMPTY_STRING);
        regVo.setDealingThroughBroker((masterapplicantTb.getDealingThroughBroker() != null
                && masterapplicantTb.getDealingThroughBroker()));
        regVo.setSubbrokerName(masterapplicantTb.getSubbrokerName() != null
                ? masterapplicantTb.getSubbrokerName() : null);
        regVo.setNseSebiRegistrationNumber(masterapplicantTb.getNseSebiRegistrationNumber() != null
                ? masterapplicantTb.getNseSebiRegistrationNumber() : EMPTY_STRING);
        regVo.setBseSebiRegistrationNumber(masterapplicantTb.getBseSebiRegistrationNumber() != null
                ? masterapplicantTb.getBseSebiRegistrationNumber() : EMPTY_STRING);
        regVo.setRegisteredAddressSubbroker(masterapplicantTb.getRegisteredAddressSubbroker() != null
                ? masterapplicantTb.getRegisteredAddressSubbroker() : EMPTY_STRING);
        regVo.setPhoneSubbroker(masterapplicantTb.getPhoneSubbroker() != null
                ? masterapplicantTb.getPhoneSubbroker() : EMPTY_STRING);
        regVo.setFaxSubbroker(masterapplicantTb.getFaxSubbroker() != null
                ? masterapplicantTb.getFaxSubbroker() : EMPTY_STRING);
        regVo.setWebsiteSubbroker(masterapplicantTb.getWebsiteSubbroker() != null
                ? masterapplicantTb.getWebsiteSubbroker() : EMPTY_STRING);
        regVo.setNameStockBroker(masterapplicantTb.getNameStockBroker() != null
                ? masterapplicantTb.getNameStockBroker() : EMPTY_STRING);
        regVo.setNameSubbroker(masterapplicantTb.getNameSubbroker() != null
                ? masterapplicantTb.getNameSubbroker() : EMPTY_STRING);
        regVo.setClientCodeSubbroker(masterapplicantTb.getClientCodeSubbroker() != null
                ? masterapplicantTb.getClientCodeSubbroker() : EMPTY_STRING);
        regVo.setExchangeSubbroker(masterapplicantTb.getExchangeSubbroker() != null
                ? masterapplicantTb.getExchangeSubbroker() : EMPTY_STRING);
        regVo.setDetailsBroker(masterapplicantTb.getDetailsBroker() != null
                ? masterapplicantTb.getDetailsBroker() : EMPTY_STRING);
        regVo.setTypeElectronicContract(masterapplicantTb.getTypeElectronicContract() != null
                ? masterapplicantTb.getTypeElectronicContract() : EMPTY_STRING);
        regVo.setFacilityInternetTrading(masterapplicantTb.getFacilityInternetTrading() != null
                && masterapplicantTb.getFacilityInternetTrading() ? "0" : "1");
        regVo.setTradingExperince(masterapplicantTb.getTradingExperince() != null
                ? masterapplicantTb.getTradingExperince()
                : EMPTY_STRING);
        regVo.setAddressFirmPropritory(masterapplicantTb.getAddressFirmPropritory() != null
                ? masterapplicantTb.getAddressFirmPropritory() : EMPTY_STRING);
        regVo.setTypeAlert(masterapplicantTb.getTypeAlert() != null
                ? masterapplicantTb.getTypeAlert() : EMPTY_STRING);
        regVo.setRelationSipMobilenumber(masterapplicantTb.getRelationSipMobilenumber() != null
                ? masterapplicantTb.getRelationSipMobilenumber() : EMPTY_STRING);
        regVo.setPanMobileNumber(masterapplicantTb.getPanMobileNumber() != null
                ? masterapplicantTb.getPanMobileNumber() : EMPTY_STRING);
        regVo.setOtherInformations(masterapplicantTb.getOtherInformations() != null
                ? masterapplicantTb.getOtherInformations() : EMPTY_STRING);
        regVo.setRelativeGeojitEmployee(masterapplicantTb.getRelativeGeojitEmployee() != null
                ? masterapplicantTb.getRelativeGeojitEmployee() : false);
        regVo.setRelationshipGeojitEmployee(masterapplicantTb.getRelationshipGeojitEmployee() != null
                ? masterapplicantTb.getRelationshipGeojitEmployee() : EMPTY_STRING);
        regVo.setGeojitEmployeeCode(masterapplicantTb.getGeojitEmployeeCode() != null
                ? masterapplicantTb.getGeojitEmployeeCode() : EMPTY_STRING);
        regVo.setGeojitEmployeeName(masterapplicantTb.getGeojitEmployeeName() != null
                ? masterapplicantTb.getGeojitEmployeeName() : EMPTY_STRING);
        regVo.setNomineeExist(masterapplicantTb.getNomineeExist());
        if (masterapplicantTb.getNomineeExist()) {
            InvestorNomineeDetailsTb investorNomineeDetails
                    = userProfileDAO.getNominationDeatails(masterapplicantTb.getRegistrationId());
            regVo.setRegId(investorNomineeDetails.getRegistrationId());
            regVo.setNameNominee(investorNomineeDetails.getNameNominee() != null
                    ? investorNomineeDetails.getNameNominee() : EMPTY_STRING);
            regVo.setRelationshipApplicant(investorNomineeDetails.getRelationshipApplicant()
                    != null ? investorNomineeDetails.getRelationshipApplicant() : EMPTY_STRING);
            regVo.setDobNominee(investorNomineeDetails.getDobNominee() != null
                    ? investorNomineeDetails.getDobNominee() : null);

            String nAddress = investorNomineeDetails.getAddressNominee() != null
                    ? investorNomineeDetails.getAddressNominee()
                    : EMPTY_STRING;
            String[] nAddtressArray = nAddress.split("\\|");

            regVo.setAddress1_Line1_Nominee(nAddtressArray.length > 0 && !nAddtressArray[0].isEmpty()
                    ? nAddtressArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regVo.setAddress1_Street_Nominee(nAddtressArray.length > 1 && !nAddtressArray[1].isEmpty()
                    ? nAddtressArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regVo.setAddress1_Landmark_Nominee(nAddtressArray.length > 2 && !nAddtressArray[2].isEmpty()
                    ? nAddtressArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regVo.setAddress1_Zipcode_Nominee(nAddtressArray.length > 3 && !nAddtressArray[3].isEmpty()
                    ? nAddtressArray[3].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            regVo.setPlaceNominee(investorNomineeDetails.getPlaceNominee()
                    != null ? investorNomineeDetails.getPlaceNominee() : EMPTY_STRING);
            regVo.setPincodeNominee(investorNomineeDetails.getPincodeNominee()
                    != null ? investorNomineeDetails.getPincodeNominee() : EMPTY_STRING);
            regVo.setStateNominee(investorNomineeDetails.getStateNominee()
                    != null ? investorNomineeDetails.getStateNominee() : EMPTY_STRING);
            regVo.setCountryNominee(investorNomineeDetails.getCountryNominee()
                    != null ? investorNomineeDetails.getCountryNominee() : EMPTY_STRING);
            if (investorNomineeDetails.getTelOfficeNominee() != null) {
                String[] nomineeOffc = investorNomineeDetails.getTelOfficeNominee().split("\\|");
                regVo.setNoisd(nomineeOffc.length > 0 && !nomineeOffc[0].isEmpty()
                        ? nomineeOffc[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setNostd(nomineeOffc.length > 1 && !nomineeOffc[1].isEmpty()
                        ? nomineeOffc[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setTelOfficeNominee(nomineeOffc.length > 2 && !nomineeOffc[2].isEmpty()
                        ? nomineeOffc[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }
            regVo.setNominee_proof(investorNomineeDetails.getNomineeProof() != null
                    ? investorNomineeDetails.getNomineeProof() : EMPTY_STRING);
            regVo.setNominee_aadhar(investorNomineeDetails.getNomineeAadhar() != null
                    ? investorNomineeDetails.getNomineeAadhar() : EMPTY_STRING);
            if (investorNomineeDetails.getTelOfficeNominee() != null) {
                String[] nomRes = investorNomineeDetails.getTelResidenceNominee().split("\\|");
                regVo.setNrisd(nomRes.length > 0 && !nomRes[0].isEmpty()
                        ? nomRes[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setNrstd(nomRes.length > 1 && !nomRes[1].isEmpty()
                        ? nomRes[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setTelResidenceNominee(nomRes.length > 2 && !nomRes[2].isEmpty()
                        ? nomRes[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }

            if (investorNomineeDetails.getTelOfficeNominee() != null) {
                String[] nomFax = investorNomineeDetails.getFaxNominee().split("\\|");
                regVo.setNfisd(nomFax.length > 0 && !nomFax[0].isEmpty()
                        ? nomFax[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setNfstd(nomFax.length > 1 && !nomFax[1].isEmpty()
                        ? nomFax[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setFaxNominee(nomFax.length > 2 && !nomFax[2].isEmpty()
                        ? nomFax[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }

            regVo.setMobileNominee(investorNomineeDetails.getMobileNominee() != null
                    ? investorNomineeDetails.getMobileNominee() : EMPTY_STRING);
            regVo.setEmailNominee(investorNomineeDetails.getEmailNominee() != null
                    ? investorNomineeDetails.getEmailNominee() : EMPTY_STRING);
            regVo.setNominePan(investorNomineeDetails.getPanNomine() != null
                    ? investorNomineeDetails.getPanNomine() : EMPTY_STRING);
            regVo.setNomineeMinor(investorNomineeDetails.getNomineeMinor() != null
                    && investorNomineeDetails.getNomineeMinor() ? true : false);
            regVo.setDobMinor(investorNomineeDetails.getDobNomineeMinor() != null
                    ? investorNomineeDetails.getDobNomineeMinor() : null);
            regVo.setNameGuardianNominee(investorNomineeDetails.getNameGuardianNominee()
                    != null ? investorNomineeDetails.getNameGuardianNominee() : EMPTY_STRING);
            regVo.setAddressNomineeMinor(investorNomineeDetails.getAddressNomineeMinor()
                    != null ? investorNomineeDetails.getAddressNomineeMinor() : EMPTY_STRING);

            String nAddressMinor = investorNomineeDetails.getAddressNomineeMinor() != null
                    ? investorNomineeDetails.getAddressNomineeMinor()
                    : EMPTY_STRING;
            String[] nAddressMinorArray = nAddressMinor.split("\\|");

            regVo.setAddress1_Line1_Minor(nAddressMinorArray.length > 0 && !nAddressMinorArray[0].isEmpty()
                    ? nAddressMinorArray[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regVo.setAddress1_Street_Minor(nAddressMinorArray.length > 1 && !nAddressMinorArray[1].isEmpty()
                    ? nAddressMinorArray[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regVo.setAddress1_Landmark_Minor(nAddressMinorArray.length > 2 && !nAddressMinorArray[2].isEmpty()
                    ? nAddressMinorArray[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regVo.setAddress1_Zipcode_Minor(nAddressMinorArray.length > 3 && !nAddressMinorArray[3].isEmpty()
                    ? nAddressMinorArray[3].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regVo.setPlaceNomineeMinor(investorNomineeDetails.getPlaceNomineeMinor()
                    != null ? investorNomineeDetails.getPlaceNomineeMinor() : EMPTY_STRING);
            regVo.setNomineeMinor(investorNomineeDetails.getNomineeMinor());
            regVo.setPincodeNomineeMinor(investorNomineeDetails.getPincodeNomineeMinor()
                    != null ? investorNomineeDetails.getPincodeNomineeMinor() : EMPTY_STRING);
            regVo.setStateNomineeMinor(investorNomineeDetails.getStateNomineeMinor()
                    != null ? investorNomineeDetails.getStateNomineeMinor() : EMPTY_STRING);
            regVo.setCountryNomineeMinor(investorNomineeDetails.getCountryNomineeMinor()
                    != null ? investorNomineeDetails.getCountryNomineeMinor() : EMPTY_STRING);

            if (investorNomineeDetails.getTelOfficeNomineeMinor() != null) {
                String[] minorOffc = investorNomineeDetails.getTelOfficeNomineeMinor().split("\\|");
                regVo.setMoisd(minorOffc.length > 0 && !minorOffc[0].isEmpty()
                        ? minorOffc[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setMostd(minorOffc.length > 1 && !minorOffc[1].isEmpty()
                        ? minorOffc[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setTelOfficeNomineeMinor(minorOffc.length > 2 && !minorOffc[2].isEmpty()
                        ? minorOffc[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }

            if (investorNomineeDetails.getTelResidenceNomineeMinor() != null) {
                String[] minorRes = investorNomineeDetails.getTelResidenceNomineeMinor().split("\\|");
                regVo.setMrisd(minorRes.length > 0 && !minorRes[0].isEmpty()
                        ? minorRes[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setMrstd(minorRes.length > 1 && !minorRes[1].isEmpty()
                        ? minorRes[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setTelResidenceNomineeMinor(minorRes.length > 2 && !minorRes[2].isEmpty()
                        ? minorRes[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }

            if (investorNomineeDetails.getFaxNomineeMinor() != null) {
                String[] minorFax = investorNomineeDetails.getFaxNomineeMinor().split("\\|");
                regVo.setMfisd(minorFax.length > 0 && !minorFax[0].isEmpty()
                        ? minorFax[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setMfstd(minorFax.length > 1 && !minorFax[1].isEmpty()
                        ? minorFax[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regVo.setFaxNomineeMinor(minorFax.length > 2 && !minorFax[2].isEmpty()
                        ? minorFax[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }

            regVo.setNomineeMinor_proof(investorNomineeDetails.getMinorProof()
                    != null ? investorNomineeDetails.getMinorProof() : EMPTY_STRING);
            regVo.setNomineeMinor_pan(investorNomineeDetails.getMinorPan()
                    != null ? investorNomineeDetails.getMinorPan() : EMPTY_STRING);
            regVo.setNomineeMinor_aadhar(investorNomineeDetails.getMinorAadhar()
                    != null ? investorNomineeDetails.getMinorAadhar() : EMPTY_STRING);
            regVo.setMobileNomineeMinor(investorNomineeDetails.getMobileNomineeMinor()
                    != null ? investorNomineeDetails.getMobileNomineeMinor() : EMPTY_STRING);
            regVo.setEmailNomineeMinor(investorNomineeDetails.getEmailNomineeMinor()
                    != null ? investorNomineeDetails.getEmailNomineeMinor() : EMPTY_STRING);
            regVo.setRelationshipGuardianMinor(investorNomineeDetails.getRelationshipGuardianMinor()
                    != null ? investorNomineeDetails.getRelationshipGuardianMinor() : EMPTY_STRING);
            regVo.setNomCityOther(masterapplicantTb.getNomCityOther());
            regVo.setMinorCityOther(masterapplicantTb.getMinorCityother());
        }
        regVo.setKitNumber(masterapplicantTb.getKitNumber() == null ? null : masterapplicantTb.getKitNumber());
        regVo.setSecondHldrDependentRelation(masterapplicantTb.getSecondHldrDependentRelation());
        regVo.setSecondHldrPan(masterapplicantTb.getSecondHldrPan());
        regVo.setSecondHldrDependentUsed(masterapplicantTb.getSecondHldrDependentUsed());
        regVo.setFirstHldrDependentUsed(masterapplicantTb.getFirstHldrDependentUsed());
        //FATCA details
        regVo.setUsNational(masterapplicantTb.getUsNational());
        regVo.setUsResident(masterapplicantTb.getUsResident());
        regVo.setUsBorn(masterapplicantTb.getUsBorn());
        regVo.setUsAddress(masterapplicantTb.getUsAddress());
        regVo.setUsTelephone(masterapplicantTb.getUsTelephone());
        regVo.setUsStandingInstruction(masterapplicantTb.getUsStandingInstruction());
        regVo.setUsPoa(masterapplicantTb.getUsPoa());
        regVo.setUsMailAddress(masterapplicantTb.getUsMailAddress());
        regVo.setIndividualTaxIdntfcnNmbr(masterapplicantTb.getIndividualTaxIdntfcnNmbr());
        regVo.setMotherName(masterapplicantTb.getMotherName() == null ? EMPTY_STRING : masterapplicantTb.getMotherName());
        regVo.setAddressType(masterapplicantTb.getAddressType() == null ? EMPTY_STRING : masterapplicantTb.getAddressType());
        return regVo;
    }

    public boolean isSaveInvestorProfile() {
        return saveInvestorProfile;
    }

    public void setSaveInvestorProfile(boolean saveInvestorProfile) {
        this.saveInvestorProfile = saveInvestorProfile;
    }

    public boolean isSaveAdvisorProfile() {
        return saveAdvisorProfile;
    }

    public void setSaveAdvisorProfile(boolean saveAdvisorProfile) {
        this.saveAdvisorProfile = saveAdvisorProfile;
    }

    public void splitPhoneNumber(String number) {
        int len = number.length();
        if (len >= SEVEN) {
            num = number.substring((len - ONE) - INDEX_POS_SIX, len);
        }
        if (len >= ELEVEN) {
            std = number.substring((len - ONE) - INDEX_POS_TEN, (len - ONE) - INDEX_POS_SIX);
        }
        if (len == THIRTEEN) {
            isd = number.substring(ZERO, TWO);
        }

    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIsd() {
        return isd;
    }

    public void setIsd(String isd) {
        this.isd = isd;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean emailverification(String parameter, String email) {
        VerifyMailVO verifyMailVO = new VerifyMailVO();
        verifyMailVO.setParameterUid(parameter);
        verifyMailVO.setEmail(email);
        boolean status = isemailverified(verifyMailVO);
        return status;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public UserProfileVO getNewAdvisorDetail(MasterApplicantTb masterapplicantTb) {
        UserProfileVO userVo = new UserProfileVO();
        userVo.setId(masterapplicantTb.getId());
        userVo.setName(masterapplicantTb.getName());
        userVo.setMiddleName(masterapplicantTb.getMiddleName());
        userVo.setLastName(masterapplicantTb.getLastName());
        userVo.setDob(masterapplicantTb.getDob());
        userVo.setAddress(masterapplicantTb.getAddress1());
        userVo.setCountry(masterapplicantTb.getAddressCountry());
        userVo.setCity(masterapplicantTb.getAddressCity());
        userVo.setPincode(masterapplicantTb.getAddressPincode());
        userVo.setEmail(masterapplicantTb.getEmail());
        userVo.setAdvisor(true);
        if (StringUtils.hasText(masterapplicantTb.getTelephone())) {
            userVo.setTelephone(masterapplicantTb.getTelephone());
        }
        userVo.setMobile(masterapplicantTb.getMobile());
        userVo.setSebi_reg_no(masterapplicantTb.getSebiRegno());
        userVo.setLinkedInConnected(masterapplicantTb.getLinkedInConnected());
        return userVo;
    }

    /**
     * method for getting list of all failed mails
     *
     * @param searchText
     * @return List<FailedMailDetailsVO>
     * @param mailTypeSelected - value used for filter output list
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<FailedMailDetailsVO> getFailedMailList(String mailTypeSelected, String searchText, boolean mailFailed) {
        Map<String, String> tableNameMailTypeMap = LookupDataLoader.getMailTypeList();
        List result;
        FailedMailDetailsVO failedMailDetails;
        List<FailedMailDetailsVO> failedMailList = new ArrayList<FailedMailDetailsVO>();
        if (mailTypeSelected.equals(ALL)) {
        for (Map.Entry<String, String> pair : tableNameMailTypeMap.entrySet()) {
                /*if (!ALL.equals(pair.getValue()) && !(pair.getValue().equals("MandateFormTb"))) {
                    result = userProfileDAO.getSendMailFailedList(pair.getValue(), searchText);
                    for (Object obj : result) {
                        failedMailDetails = getMailDatails(obj);
                        if (!failedMailDetails.isMailStatus() && failedMailDetails.isSendMail()) {
                            failedMailList.add(failedMailDetails);
                        }
                    }
                } else */if (ALL.equals("All")) {
                    result = userProfileDAO.getSendMailFailedList("ALL", searchText);
                    for (Object obj : result) {
                        failedMailDetails = getMailDatails(obj);
                        failedMailList.add(failedMailDetails);
                    }
                }
            }
        } else if (mailTypeSelected.equals("MandateFormTb")) {
            result = userProfileDAO.getSendMailFailedList(mailTypeSelected, searchText);
            for (Object obj : result) {
                failedMailDetails = getMailDatails(obj);
                if (failedMailDetails.getRegId() != null) {
                    failedMailList.add(failedMailDetails);
                }
            }

        } else {
            result = userProfileDAO.getSendMailFailedList(mailTypeSelected, searchText);
            for (Object obj : result) {
                failedMailDetails = getMailDatails(obj);
                if (!failedMailDetails.isMailStatus() && failedMailDetails.isSendMail()) {
                    failedMailList.add(failedMailDetails);
                } //This else if,is used for regenerating PDF for ACTIVE users.
                else if (!mailFailed) {
                    failedMailList.add(failedMailDetails);
                }
            }
        }
        return failedMailList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveMandate(MandateVo mandateVo, String regid) {
        StringBuilder logMessage = new StringBuilder("Creating new mandatory form for ECS");
        LOGGER.info(logMessage.toString());
        mandate = new MandateFormTb();

        mandate.setRegistrationId(regid);
        //mandate.setUmrn(mandateVo.getUmrn() == null? EMPTY_STRING : mandateVo.getUmrn() );
        if (mandateVo.getAction() != null) {
            mandate.setActionType(mandateVo.getAction());
        }
        mandate.setSponserBankCode(mandateVo.getCustomerBankCode() == null ? EMPTY_STRING : mandateVo.getCustomerBankCode());
        mandate.setUtilityCode(mandateVo.getUtilityCode() == null ? EMPTY_STRING : mandateVo.getUtilityCode());
        mandate.setNameOfBiller(mandateVo.getUtilityName() == null ? EMPTY_STRING : mandateVo.getUtilityName());
        if (mandateVo.getCustomerDebitAccount() != null) {
            mandate.setDebtAccountType(mandateVo.getCustomerDebitAccount());
        }
        mandate.setAccountNo(mandateVo.getCustomerAccountNumber() == null ? EMPTY_STRING : mandateVo.getCustomerAccountNumber());
        mandate.setBankName(mandateVo.getCustomerBankName() == null ? EMPTY_STRING : mandateVo.getCustomerBankName());
        mandate.setIfscNumber(mandateVo.getIfscNumber() == null ? EMPTY_STRING : mandateVo.getIfscNumber());
        mandate.setMicrNumber(mandateVo.getMicrNumber() == null ? EMPTY_STRING : mandateVo.getMicrNumber());
        if (StringUtils.hasText(mandateVo.getAmount())) {
            mandate.setAmountWords(mandateVo.getAmountInWords());
            mandate.setAmount(BigDecimal.valueOf(Double.parseDouble(mandateVo.getAmount())));
        }
        if (mandateVo.getFrequency() != null) {
            mandate.setFrequency(mandateVo.getFrequency());
        }
        if (mandateVo.getCustomerDebitType() != null) {
            mandate.setDebtType(mandateVo.getCustomerDebitType());
        }
        StringBuffer contact = new StringBuffer();
        contact.append(mandateVo.getMandateisd() == null ? EMPTY_STRING : mandateVo.getMandateisd())
                .append(PIPE_SEPERATOR)
                .append(mandateVo.getMandatestd() == null ? EMPTY_STRING : mandateVo.getMandatestd())
                .append(PIPE_SEPERATOR)
                .append(mandateVo.getContactNmbr());
        mandate.setCustomerPhone(contact.toString());
        mandate.setEmail(mandateVo.getCustomerEmail());
        mandate.setMandateCreatedDate(new Date());
        mandate.setFromDate(mandateVo.getFromDate());
        mandate.setToDate(mandateVo.getToDate() == null ? null : mandateVo.getToDate());
        if (mandateVo.isSelectToDate()) {
            mandate.setToOrUntill(true);
        } else {
            mandate.setToOrUntill(false);
        }
        userProfileDAO.saveMandateForm(mandate);
    }

    /**
     * method to regenerate failed mails
     *
     * @param reGenerateMailList -list of mails to be regenerated
     * @return registration mails without pdf documents if any
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<FailedMailDetailsVO> reSendMail(List<FailedMailDetailsVO> reGenerateMailList) {
        List<FailedMailDetailsVO> registrationProcessingList = new ArrayList<FailedMailDetailsVO>();
        MasterApplicantTb masterApplicantTb = new MasterApplicantTb();
        TempRegistrationTb tempRegistrationTb;
        boolean sendPdf = true;
        LOGGER.info("--------------------Generating List for Sending Mail-----------------------");
        for (FailedMailDetailsVO mailDetails : reGenerateMailList) {
            if (mailDetails.getEmail_purpose().equals(VERIFICATION_MAIL)) {
                LOGGER.info("--------------------VERIFICATION_MAIL-----------------------");
                tempRegistrationTb = new TempRegistrationTb();
                tempRegistrationTb.setEmail(mailDetails.getEmail());
                tempRegistrationTb.setUserType(mailDetails.getUserType());
                tempRegistrationTb.setVerificationCode(mailDetails.getVerificationCode());
                String url = MailUtil.getInstance().generateVerificationURL(tempRegistrationTb);
                this.reSendMailForFaildMail(url, tempRegistrationTb.getClass().getName(),
                        mailDetails);
                System.out.println(url);
            } else if (mailDetails.getEmail_purpose().equals(REGISTRATION_MAIL)) {
                LOGGER.info("--------------------REGISTRATION_MAIL-----------------------");
                boolean isAdvisor = Boolean.valueOf(mailDetails.getUserType());
                if (isAdvisor) {
                    this.reSendMailForFaildMail(EMPTY_STRING, masterApplicantTb.getClass().getName(),
                            mailDetails);
                    sendPdf = false;

                } else {
                    registrationProcessingList.add(mailDetails);
                }
            } else if (mailDetails.getEmail_purpose().equals(MANDATEFORM)) {
                LOGGER.info("--------------------MANDATEFORM-----------------------");
                registrationProcessingList.add(mailDetails);
            }
        }
        return registrationProcessingList;
    }

    private void reSendMailForFaildMail(String url, String tableName,
            FailedMailDetailsVO mailDetails) {
        IMailStatusBAO iMailStatusBAO = (IMailStatusBAO) BeanLoader.getBean("iMailStatusBAO");
        boolean mailSendStatus = false;
        if (mailDetails.getEmail_purpose().equals(VERIFICATION_MAIL)) {
            boolean advisor = mailDetails.getUserType().equalsIgnoreCase("ADVISOR");
            try {
                mailSendStatus = sendVerificationMail(mailDetails.getEmail(),
                        mailDetails.getEmail(), url, advisor);
                iMailStatusBAO.updateMailStatus(tableName,
                        mailDetails.getEmail(), mailSendStatus);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        } else if (mailDetails.getEmail_purpose().equals(REGISTRATION_MAIL)) {
            try {
                boolean userType = Boolean.valueOf(mailDetails.getUserType());
                mailSendStatus = sendmail(mailDetails.getUserName(),
                        mailDetails.getEmail(), mailDetails.getRegId(), mailDetails.getMobile(), !userType);
                iMailStatusBAO.updateMailStatus(tableName,
                        mailDetails.getEmail(), mailSendStatus);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }

        } else if (mailDetails.getEmail_purpose().equals(MANDATEFORM)) {
            try {
                sendMandateFormmail(mailDetails.getUserName(), mailDetails.getEmail(), mailDetails.getRegId(), mailDetails.getMobile(), true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UserProfileBAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * generate VO using each table object
     *
     * @param obj - table obj
     * @return VO equivalent to query result
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    private FailedMailDetailsVO getMailDatails(Object obj) {
        TempRegistrationTb tempRegistrationTb;
        FailedMailDetailsVO sendMailFailedListDetails = new FailedMailDetailsVO();
        MasterApplicantTb masterApplicantTb;

        if (obj instanceof TempRegistrationTb) {
            tempRegistrationTb = (TempRegistrationTb) obj;
            sendMailFailedListDetails.setEmail(tempRegistrationTb.getEmail());
            sendMailFailedListDetails.setVerificationCode(tempRegistrationTb.getVerificationCode());
            sendMailFailedListDetails.setUserType(tempRegistrationTb.getUserType());
            sendMailFailedListDetails.setEmail_purpose(VERIFICATION_MAIL);
            sendMailFailedListDetails.setMailStatus(tempRegistrationTb.getMailSendSuccess() != null ? tempRegistrationTb.getMailSendSuccess()
                    : false);
            sendMailFailedListDetails.setMailStatusText(tempRegistrationTb.getMailSendSuccess()
                    ? StringCaseConverter.toProperCase((EnumStatus.SUCCESS).toString())
                    : StringCaseConverter.toProperCase((EnumStatus.FAILED).toString()));
            sendMailFailedListDetails.setMailStatusStyle(tempRegistrationTb.getMailSendSuccess()
                    ? "label label-success" : "label label-notverified");
            //sendMailFailedListDetails.setSendMail(!tempRegistrationTb.getMailVerified());
            sendMailFailedListDetails.setSendMail(false);
        } else if (obj instanceof MasterApplicantTb) {
            RegistrationDataProcessingVo registrationDataProcessing = new RegistrationDataProcessingVo();
            masterApplicantTb = (MasterApplicantTb) obj;
            createFailedMailListVo(sendMailFailedListDetails, masterApplicantTb);
            sendMailFailedListDetails.setEmail_purpose(REGISTRATION_MAIL);
            if (!masterApplicantTb.getAdvisor()) {
                RegistrationVo registration = this.getNewApplicantDetails(masterApplicantTb);
                registrationDataProcessing.setRegistrationVo(registration);
//                MandateFormTb mandateForm = userProfileDAO.getMandateTbForRegId(masterApplicantTb
//                        .getRegistrationId());
                List<Object> resultSet = userProfileDAO.getMandateTbForRegId(masterApplicantTb.getRegistrationId(), false);
                MandateFormTb mandateForm = (MandateFormTb) resultSet.get(0);
                MandateVo mandateVo = this.getMandateVoFormTB(mandateForm);
                mandateVo.setCustomerName(registration.getBeneficiaryName() == null ? EMPTY_STRING
                        : registration.getBeneficiaryName());
                registrationDataProcessing.setMandateVo(mandateVo);
            }
            sendMailFailedListDetails.setRegistrationDataProcessing(registrationDataProcessing);
        } else if (obj instanceof MandateFormTb) {

            RegistrationDataProcessingVo registrationDataProcessing = new RegistrationDataProcessingVo();
            mandate = (MandateFormTb) obj;
            MandateVo mandateVo = this.getMandateVoFormTB(mandate);
            registrationDataProcessing.setMandateVo(mandateVo);
            masterApplicantTb = (MasterApplicantTb) userProfileDAO.getMasterApplicant(mandate.getRegistrationId());
            if (masterApplicantTb.getRegistrationId() != null) {
                createFailedMailListVo(sendMailFailedListDetails, masterApplicantTb);
                sendMailFailedListDetails.setEmail_purpose(MANDATEFORM);
                sendMailFailedListDetails.setRegistrationDataProcessing(registrationDataProcessing);
            }
        }
        return sendMailFailedListDetails;
    }

    private void createFailedMailListVo(FailedMailDetailsVO sendMailFailedListDetails, MasterApplicantTb masterApplicantTb) {
        sendMailFailedListDetails.setEmail(masterApplicantTb.getEmail());
        sendMailFailedListDetails.setRegId(masterApplicantTb.getRegistrationId());
        sendMailFailedListDetails.setVerificationCode(masterApplicantTb.getVerificationCode());
        StringBuffer name = new StringBuffer();
        name.append(masterApplicantTb.getName()).append(EMPTY_STRING).append(masterApplicantTb.getMiddleName()).
                append(EMPTY_STRING).append(masterApplicantTb.getLastName());
        sendMailFailedListDetails.setUserName(masterApplicantTb.getName());
        sendMailFailedListDetails.setMobile(masterApplicantTb.getMobile());
        sendMailFailedListDetails.setUserType(masterApplicantTb.getAdvisor().toString());
        sendMailFailedListDetails.setMailStatus(masterApplicantTb.getMailSendSuccess() != null
                ? masterApplicantTb.getMailSendSuccess() : false);
        sendMailFailedListDetails.setMailStatusText(sendMailFailedListDetails.isMailStatus()
                ? StringCaseConverter.toProperCase((EnumStatus.SUCCESS).toString())
                : StringCaseConverter.toProperCase((EnumStatus.FAILED).toString()));
        sendMailFailedListDetails.setMailStatusStyle(sendMailFailedListDetails.isMailStatus()
                ? "label label-success" : "label label-notverified");
        sendMailFailedListDetails.setSendMail(!(masterApplicantTb.getStatus()
                == EnumStatus.ACTIVE.getValue()));
    }

    private MandateVo getMandateVoFormTB(MandateFormTb mandateForm) {
        MandateVo mandateVo = new MandateVo();
        mandateVo.setReg_id(mandateForm.getRegistrationId());
        mandateVo.setAction(mandateForm.getActionType() == null ? EMPTY_STRING
                : mandateForm.getActionType());
        mandateVo.setAmount(mandateForm.getAmount() == null ? EMPTY_STRING
                : mandateForm.getAmount().toString());
        mandateVo.setAmountInWords(mandateForm.getAmountWords() == null ? EMPTY_STRING
                : mandateForm.getAmountWords());
        mandateVo.setCustomerBankCode(mandateForm.getSponserBankCode() == null ? EMPTY_STRING
                : mandateForm.getSponserBankCode());
        mandateVo.setUtilityCode(mandateForm.getUtilityCode() == null ? EMPTY_STRING
                : mandateForm.getUtilityCode());
        mandateVo.setUtilityName(mandateForm.getNameOfBiller() == null ? EMPTY_STRING
                : mandateForm.getNameOfBiller());
        mandateVo.setCustomerAccountNumber(mandateForm.getAccountNo() == null ? EMPTY_STRING
                : mandateForm.getAccountNo());
        mandateVo.setCustomerBankName(mandateForm.getBankName() == null ? EMPTY_STRING
                : mandateForm.getBankName());
        mandateVo.setFrequency(mandateForm.getFrequency() == null ? EMPTY_STRING
                : mandateForm.getFrequency());
        mandateVo.setCustomerDebitType(mandateForm.getDebtType() == null ? EMPTY_STRING
                : mandateForm.getDebtType());
        mandateVo.setCustomerDebitAccount(mandateForm.getDebtAccountType() == null ? EMPTY_STRING
                : mandateForm.getDebtAccountType());
        mandateVo.setCustomerEmail(mandateForm.getEmail() == null ? EMPTY_STRING
                : mandateForm.getEmail());
        mandateVo.setContactNmbr(mandateForm.getCustomerPhone() == null ? EMPTY_STRING
                : mandateForm.getCustomerPhone().replaceAll("\\|", SPACE));
        mandateVo.setFromDate(mandateForm.getFromDate() == null ? null
                : mandateForm.getFromDate());
        mandateVo.setToDate(mandateForm.getToDate() == null ? null : mandateForm.getToDate());
        mandateVo.setIfscNumber(mandateForm.getIfscNumber() == null ? EMPTY_STRING
                : mandateForm.getIfscNumber());
        mandateVo.setMicrNumber(mandateForm.getMicrNumber() == null ? EMPTY_STRING
                : mandateForm.getMicrNumber());
        mandateVo.setDate(mandateForm.getMandateCreatedDate() == null ? null
                : mandateForm.getMandateCreatedDate());
        mandateVo.setCancelDate(mandateForm.getToOrUntill() == null ? "false"
                : mandateForm.getToOrUntill().toString());
        return mandateVo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void reSendMailForFaildMail(FailedMailDetailsVO mailDetails) {
        if (mailDetails.getEmail_purpose().equals(REGISTRATION_MAIL)) {
            LOGGER.info("----------------------Sending Registration Mail--------------------------------");
            MasterApplicantTb masterApplicantTb = new MasterApplicantTb();
            this.reSendMailForFaildMail(EMPTY_STRING, masterApplicantTb.getClass().
                    getName(), mailDetails);
        } else if (mailDetails.getEmail_purpose().equals(MANDATEFORM)) {
            LOGGER.info("----------------------Sending Mandate Form--------------------------------");
            this.reSendMailForFaildMail(EMPTY_STRING, null, mailDetails);

        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateLinkedINData(UserProfileVO userProfile) {
        List<Object> responseList = userProfileDAO.getUserDetails(userProfile
                .getEmail(), userProfile.isAdvisor());
        List<Object> updatedList = new ArrayList<Object>();
        MasterApplicantTb masterApplicant = (MasterApplicantTb) responseList.get(0);
        masterApplicant.setLinkedInConnected(true);
        masterApplicant.setLinkedinPassword(userProfile.getAccess_token());
        masterApplicant.setLinkedinExpireIn(userProfile.getExpire_in());
        masterApplicant.setLinkedinMemberId(userProfile.getLinkedin_member_id());
        masterApplicant.setLinkedinUser(userProfile.getLinkedin_user());
        masterApplicant.setLinkedinExpireDate(userProfile.getExpire_in_date());
        masterApplicant.setPictureUrl(userProfile.getPictureUrl() != null
                ? userProfile.getPictureUrl() : EMPTY_STRING);
        masterApplicant.setLinkedinProfileUrl(userProfile.getProfileUrl() != null
                ? userProfile.getProfileUrl() : EMPTY_STRING);

        if (userProfile.isAdvisor()) {
            masterApplicant.setWorkOrganization(userProfile.getWorkOrganization() != null
                    ? userProfile.getWorkOrganization() : EMPTY_STRING);
            masterApplicant.setJobTitle(userProfile.getJobTitle() != null
                    ? userProfile.getJobTitle() : EMPTY_STRING);
            updatedList.add(masterApplicant);
            MasterAdvisorTb masterAdvisor = (MasterAdvisorTb) responseList.get(1);
            masterAdvisor.setWorkOrganization(userProfile.getWorkOrganization() != null
                    ? userProfile.getWorkOrganization() : EMPTY_STRING);
            masterAdvisor.setJobTitle(userProfile.getJobTitle() != null
                    ? userProfile.getJobTitle() : EMPTY_STRING);
            updatedList.add(masterAdvisor);
        } else {
            updatedList.add(masterApplicant);
            MasterCustomerTb masterCustomer = (MasterCustomerTb) responseList.get(1);
            masterCustomer.setWorkOrganization(userProfile.getWorkOrganization() != null
                    ? userProfile.getWorkOrganization() : EMPTY_STRING);
            masterCustomer.setJobTitle(userProfile.getJobTitle() != null
                    ? userProfile.getJobTitle() : EMPTY_STRING);
            updatedList.add(masterCustomer);
        }
        userProfileDAO.updateLinkedInData(updatedList, userProfile.isAdvisor());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateLinkedIn(Object username, Boolean advisor) {
        String email = (String) username;
        userProfileDAO.updateLinkedInStatus(email, advisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getCustomerInitLoginStatus(String username, String usertype) {
        String redirecTo = "";
        if (usertype.equalsIgnoreCase(YES)) {
            List<Object> advisor = userProfileDAO.getUserDetails(username, true);
            MasterAdvisorTb masterAdvisor = (MasterAdvisorTb) advisor.get(1);
            if (masterAdvisor.getInitLogin() != ZERO) {
                redirecTo = EnumAdvLoginStatus.fromInt(masterAdvisor.getInitLogin()).toString();
            } else {
                redirecTo = EnumAdvLoginStatus.fromInt(masterAdvisor.getInitLogin()).toString();
            }

        } else {
            List<Object> investor = userProfileDAO.getUserDetails(username, false);
            MasterCustomerTb masterCustomer = (MasterCustomerTb) investor.get(1);
            if (masterCustomer.getInitLogin() != ZERO) {
                redirecTo = EnumCustLoginStatus.fromInt(masterCustomer.getInitLogin()).toString();
            } else {
                redirecTo = EnumCustLoginStatus.fromInt(masterCustomer.getInitLogin()).toString();
            }
        }
        return redirecTo;
    }

    public void getInvestorNomineeDetails(UserDetailsVO userDetails) {
        if (userDetails.getMasterApplicant().getNomineeExist() != null
                && userDetails.getMasterApplicant().getNomineeExist()) {
            InvestorNomineeDetailsTb nomineeDetails = userProfileDAO.getNominationDeatails(userDetails
                    .getMasterApplicant().getRegistrationId());
            if (nomineeDetails != null) {
                userDetails.setNomineeDetails(nomineeDetails);
                String addressNominee = nomineeDetails.getAddressNominee() != null
                        ? nomineeDetails.getAddressNominee() : EMPTY_STRING;
                String[] addressNomineeArray = addressNominee.split("\\" + PIPE_SEPERATOR);

                userDetails.setAddress1_Line1_Nominee(addressNomineeArray.length > 0 && !addressNomineeArray[0].isEmpty()
                        ? addressNomineeArray[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setAddress1_Street_Nominee(addressNomineeArray.length > 1 && !addressNomineeArray[1].isEmpty()
                        ? addressNomineeArray[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setAddress1_Landmark_Nominee(addressNomineeArray.length > 2 && !addressNomineeArray[2].isEmpty()
                        ? addressNomineeArray[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setAddress1_Zipcode_Nominee(nomineeDetails.getPincodeNominee() != null
                        ? nomineeDetails.getPincodeNominee() : EMPTY_STRING);

                String addressMinor = nomineeDetails.getAddressNomineeMinor() != null
                        ? nomineeDetails.getAddressNomineeMinor() : EMPTY_STRING;
                String[] addressMinorArray = addressMinor.split("\\" + PIPE_SEPERATOR);

                userDetails.setAddress1_Line1_Minor(addressMinorArray.length > 0 && !addressMinorArray[0].isEmpty()
                        ? addressMinorArray[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setAddress1_Street_Minor(addressMinorArray.length > 1 && !addressMinorArray[1].isEmpty()
                        ? addressMinorArray[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setAddress1_Landmark_Minor(addressMinorArray.length > 2 && !addressMinorArray[2].isEmpty()
                        ? addressMinorArray[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setAddress1_Zipcode_Minor(nomineeDetails.getPincodeNomineeMinor() != null
                        ? nomineeDetails.getPincodeNomineeMinor() : EMPTY_STRING);

                String resNo = nomineeDetails.getTelResidenceNominee()
                        != null ? nomineeDetails.getTelResidenceNominee() : EMPTY_STRING;
                String nomiResNo[] = resNo.split("\\" + PIPE_SEPERATOR);
                userDetails.setNrisd(nomiResNo.length > 0 && !nomiResNo[0].isEmpty()
                        ? nomiResNo[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setNrstd(nomiResNo.length > 1 && !nomiResNo[1].isEmpty()
                        ? nomiResNo[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setTelResidenceNominee(nomiResNo.length > 2 && !nomiResNo[2].isEmpty()
                        ? nomiResNo[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);

                String offNo = nomineeDetails.getTelOfficeNominee()
                        != null ? nomineeDetails.getTelOfficeNominee() : EMPTY_STRING;
                String nomiOffNo[] = offNo.split("\\" + PIPE_SEPERATOR);
                userDetails.setNoisd(nomiOffNo.length > 0 && !nomiOffNo[0].isEmpty()
                        ? nomiOffNo[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setNostd(nomiOffNo.length > 1 && !nomiOffNo[1].isEmpty()
                        ? nomiOffNo[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setTelOfficeNominee(nomiOffNo.length > 2 && !nomiOffNo[2].isEmpty()
                        ? nomiOffNo[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);

                String faxNo = nomineeDetails.getFaxNominee()
                        != null ? nomineeDetails.getFaxNominee() : EMPTY_STRING;
                String nomifaxNo[] = faxNo.split("\\" + PIPE_SEPERATOR);
                userDetails.setNfisd(nomifaxNo.length > 0 && !nomifaxNo[0].isEmpty()
                        ? nomifaxNo[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setNfstd(nomifaxNo.length > 1 && !nomifaxNo[1].isEmpty()
                        ? nomifaxNo[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                userDetails.setFaxNominee(nomifaxNo.length > 2 && !nomifaxNo[2].isEmpty()
                        ? nomifaxNo[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);

                if (nomineeDetails.getNomineeMinor()) {
                    String resMNo = nomineeDetails.getTelResidenceNomineeMinor()
                            != null ? nomineeDetails.getTelResidenceNomineeMinor() : EMPTY_STRING;
                    String nomiMResNo[] = resMNo.split("\\" + PIPE_SEPERATOR);
                    userDetails.setMrisd(nomiMResNo.length > 0 && !nomiMResNo[0].isEmpty()
                            ? nomiMResNo[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setMrstd(nomiMResNo.length > 1 && !nomiMResNo[1].isEmpty()
                            ? nomiMResNo[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setTelResidenceNomineeMinor(nomiMResNo.length > 2 && !nomiMResNo[2].isEmpty()
                            ? nomiMResNo[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);

                    String offMNo = nomineeDetails.getTelOfficeNomineeMinor()
                            != null ? nomineeDetails.getTelOfficeNomineeMinor() : EMPTY_STRING;
                    String nomiMOffNo[] = offMNo.split("\\" + PIPE_SEPERATOR);
                    userDetails.setMoisd(nomiMOffNo.length > 0 && !nomiMOffNo[0].isEmpty()
                            ? nomiMOffNo[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setMostd(nomiMOffNo.length > 1 && !nomiMOffNo[1].isEmpty()
                            ? nomiMOffNo[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setTelOfficeNomineeMinor(nomiMOffNo.length > 2 && !nomiMOffNo[2].isEmpty()
                            ? nomiMOffNo[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);

                    String faxMNo = nomineeDetails.getFaxNomineeMinor()
                            != null ? nomineeDetails.getFaxNomineeMinor() : EMPTY_STRING;
                    String nomiMfaxNo[] = faxMNo.split("\\" + PIPE_SEPERATOR);
                    userDetails.setMfisd(nomiMfaxNo.length > 0 && !nomiMfaxNo[0].isEmpty()
                            ? nomiMfaxNo[0].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setMfstd(nomiMfaxNo.length > 1 && !nomiMfaxNo[1].isEmpty()
                            ? nomiMfaxNo[1].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                    userDetails.setFaxNomineeMinor(nomiMfaxNo.length > 2 && !nomiMfaxNo[2].isEmpty()
                            ? nomiMfaxNo[2].replaceAll("\\" + PIPE_SEPERATOR, EMPTY_STRING) : EMPTY_STRING);
                }

            }
        } else {
            InvestorNomineeDetailsTb nomineeDetails = new InvestorNomineeDetailsTb();
            nomineeDetails.setRegistrationId(userDetails.getMasterApplicant().getRegistrationId());
            userDetails.setNomineeDetails(nomineeDetails);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveAdvTemp(TempAdvVo advisorRegistrationVo) throws ParseException {
        StringBuilder logMessage = new StringBuilder("Creating new advisor profile with emailid ");
        logMessage.append(advisorRegistrationVo.getRegEmail());
        LOGGER.info(logMessage.toString());
        TempAdv tempmaster = (TempAdv) userProfileDAO.getTempAdvDetails(advisorRegistrationVo.getRegEmail());
        if (tempmaster == null) {
            tempmaster = new TempAdv();
        }
        tempmaster.setRegistrationId(advisorRegistrationVo.getRegNO());
        tempmaster.setEmail(advisorRegistrationVo.getRemail());
        tempmaster.setFirstname(advisorRegistrationVo.getFname());
        tempmaster.setMiddlename(advisorRegistrationVo.getMiddle_name());
        tempmaster.setLastname(advisorRegistrationVo.getLast_name());
        tempmaster.setSebi(advisorRegistrationVo.getSebi_regno());
        if (StringUtils.hasText(advisorRegistrationVo.getSebi_validity()) && advisorRegistrationVo.getSebi_validity().length() == 10) {
            tempmaster.setValidityReg(DateUtil.stringToDate(advisorRegistrationVo.getSebi_validity(), dd_MM_yyyy));
        }
        tempmaster.setPan(advisorRegistrationVo.getPan());
        if (StringUtils.hasText(advisorRegistrationVo.getDob()) && advisorRegistrationVo.getDob().length() == 10) {
            tempmaster.setDob(DateUtil.stringToDate(advisorRegistrationVo.getDob(), dd_MM_yyyy));
        }

//        StringBuffer address = new StringBuffer();
//        address.append(advisorRegistrationVo.getRaddressLine1()).append(PIPE_SEPERATOR).append(advisorRegistrationVo.getRaddressLine2())
//                .append(PIPE_SEPERATOR).append(advisorRegistrationVo.getRlandMark());
//        tempmaster.setAddress(address.toString());
//        tempmaster.setPincode(advisorRegistrationVo.getRpincode());
//        tempmaster.setCountry(advisorRegistrationVo.getRcountry());
//        tempmaster.setState(advisorRegistrationVo.getRstate());
//        tempmaster.setCity(advisorRegistrationVo.getRcity());
//        tempmaster.setMobile(advisorRegistrationVo.getRmobile());
//
//        StringBuffer tel = new StringBuffer();
//        tel.append(advisorRegistrationVo.getRisd()).append(PIPE_SEPERATOR).append(advisorRegistrationVo.getRstd())
//                .append(PIPE_SEPERATOR).append(advisorRegistrationVo.getRtnumber());
//        tempmaster.setResidentialTel(tel.toString());
        tempmaster.setOrganisation(advisorRegistrationVo.getOrganization());
        tempmaster.setJobtitle(advisorRegistrationVo.getJobTitle());

        StringBuffer ofcaddress = new StringBuffer();
        ofcaddress.append(advisorRegistrationVo.getOaddressLine1()).append(PIPE_SEPERATOR).append(advisorRegistrationVo.getOaddressLine2())
                .append(PIPE_SEPERATOR).append(advisorRegistrationVo.getOlandMark());
        tempmaster.setOffcAddress(ofcaddress.toString());

        tempmaster.setOffcPincode(advisorRegistrationVo.getOpincode());
        tempmaster.setOffcCountry(advisorRegistrationVo.getOcountry());
        tempmaster.setOffcState(advisorRegistrationVo.getOstate());
        tempmaster.setOffcCity(advisorRegistrationVo.getOcity());
        tempmaster.setOffcMob(advisorRegistrationVo.getOmobile());
        tempmaster.setOffcEmail(advisorRegistrationVo.getOemail());
        StringBuffer ofctel = new StringBuffer();
        ofctel.append(advisorRegistrationVo.getOisd()).append(PIPE_SEPERATOR).append(advisorRegistrationVo.getOstd())
                .append(PIPE_SEPERATOR).append(advisorRegistrationVo.getOtnumber());
        tempmaster.setOffcTel(ofctel.toString());

//        tempmaster.setCorrespondenceAddrs(advisorRegistrationVo.getCorrespondenceAddress());
        tempmaster.setBankName(advisorRegistrationVo.getBankName());
//        tempmaster.setBranch(advisorRegistrationVo.getBranch());
        tempmaster.setAccountType(advisorRegistrationVo.getAccountType());
        tempmaster.setAccountNmbr(advisorRegistrationVo.getAccountNumber());
        tempmaster.setReAccno(advisorRegistrationVo.getRaccountNumber());
        tempmaster.setIfsc(advisorRegistrationVo.getIfscNo());
        tempmaster.setMicr(advisorRegistrationVo.getMicrNo());

        StringBuffer bnkaddress = new StringBuffer();
        bnkaddress.append(advisorRegistrationVo.getBaddressLine1()).append(PIPE_SEPERATOR).append(advisorRegistrationVo.getBaddressLine2())
                .append(PIPE_SEPERATOR).append(advisorRegistrationVo.getBlandMark());
        tempmaster.setBankAddrs(bnkaddress.toString());

        tempmaster.setBankPincode(advisorRegistrationVo.getBpincode());
        tempmaster.setBankCountry(advisorRegistrationVo.getBcountry());
        tempmaster.setBankState(advisorRegistrationVo.getBstate());
        tempmaster.setBankCity(advisorRegistrationVo.getBcity());
        tempmaster.setPrimaryQualificationDegree(advisorRegistrationVo.getPqualification());
//  Qualification id is commented according to the client request
//        tempmaster.setPrimaryQualificationId(advisorRegistrationVo.getPqualificationId());
        tempmaster.setPrimaryQualificationInstitute(advisorRegistrationVo.getPinsititute());
        tempmaster.setPrimaryQualificationYear(advisorRegistrationVo.getPyear());

        tempmaster.setSecondaryQualificationDegree(advisorRegistrationVo.getSqualification());
//        tempmaster.setSecondaryQualificationId(advisorRegistrationVo.getSqualificationId());
        tempmaster.setSecondaryQualificationInstitute(advisorRegistrationVo.getSinsititute());
        tempmaster.setSecondaryQualificationYear(advisorRegistrationVo.getSyear());

        tempmaster.setTertiaryQualificationDegree(advisorRegistrationVo.getTqualification());
//        tempmaster.setTertiaryQualificationId(advisorRegistrationVo.getTqualificationId());
        tempmaster.setTertiaryQualificationInstitute(advisorRegistrationVo.getTinsititute());
        tempmaster.setTertiaryQualificationYear(advisorRegistrationVo.getTyear());
        tempmaster.setResCityOther(advisorRegistrationVo.getResCity() == null ? EMPTY_STRING : advisorRegistrationVo.getResCity());
        tempmaster.setOffCityOther(advisorRegistrationVo.getOffCity() == null ? EMPTY_STRING : advisorRegistrationVo.getOffCity());
        tempmaster.setBnkCityOther(advisorRegistrationVo.getBnkCity() == null ? EMPTY_STRING : advisorRegistrationVo.getBnkCity());
        tempmaster.setIndvOrCprt(advisorRegistrationVo.getIndvOrCprt() == null ? false : Boolean.valueOf(advisorRegistrationVo.getIndvOrCprt()));
        tempmaster.setSebiPath(advisorRegistrationVo.getSebiPath() == null ? EMPTY_STRING : advisorRegistrationVo.getSebiPath());
        tempmaster.setAdvPicPath(advisorRegistrationVo.getAdvPicPath() == null ? EMPTY_STRING : advisorRegistrationVo.getAdvPicPath());
        tempmaster.setOneLineDesc(advisorRegistrationVo.getOneLineDesc() == null ? EMPTY_STRING : advisorRegistrationVo.getOneLineDesc());
        tempmaster.setAboutMe(advisorRegistrationVo.getAboutMe() == null ? EMPTY_STRING : advisorRegistrationVo.getAboutMe());
        tempmaster.setMyInvestmentStrategy(advisorRegistrationVo.getMyInvestmentStrategy() == null ? EMPTY_STRING : advisorRegistrationVo.getMyInvestmentStrategy());
        userProfileDAO.saveTempAdv(tempmaster);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public AdvisorRegistrationVo getTempAdvData(String email) {
        AdvisorRegistrationVo advisor = new AdvisorRegistrationVo();
        TempAdv tempmaster = (TempAdv) userProfileDAO.getTempAdvDetails(email);
        if (tempmaster != null) {
            advisor.setRegNO(tempmaster.getRegistrationId());
            advisor.setRemail(tempmaster.getEmail());
            advisor.setFname(tempmaster.getFirstname() == null ? EMPTY_STRING : tempmaster.getFirstname());
            advisor.setMiddle_name(tempmaster.getMiddlename() == null ? EMPTY_STRING : tempmaster.getMiddlename());
            advisor.setLast_name(tempmaster.getLastname() == null ? EMPTY_STRING : tempmaster.getLastname());
            advisor.setSebi_regno(tempmaster.getSebi() == null ? EMPTY_STRING : tempmaster.getSebi());
            advisor.setSebi_validity(tempmaster.getValidityReg() == null ? null : tempmaster.getValidityReg());
            advisor.setPan(tempmaster.getPan() == null ? EMPTY_STRING : tempmaster.getPan());
            advisor.setDob(tempmaster.getDob() == null ? null : tempmaster.getDob());

//            String[] address = tempmaster.getAddress().split("\\|");
//            advisor.setRaddressLine1(address.length > 0 && !address[0].isEmpty()
//                    ? address[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
//            advisor.setRaddressLine2(address.length > 1 && !address[1].isEmpty()
//                    ? address[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
//            advisor.setRlandMark(address.length > 2 && !address[2].isEmpty()
//                    ? address[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
//
//            advisor.setRpincode(tempmaster.getPincode() == null ? EMPTY_STRING : tempmaster.getPincode());
//            advisor.setRcountry(tempmaster.getCountry() == null ? EMPTY_STRING : tempmaster.getCountry());
//            advisor.setRstate(tempmaster.getState() == null ? EMPTY_STRING : tempmaster.getState());
//            advisor.setRcity(tempmaster.getCity() == null ? EMPTY_STRING : tempmaster.getCity());
//            advisor.setRmobile(tempmaster.getMobile() == null ? EMPTY_STRING : tempmaster.getMobile());
//
//            String[] tel = tempmaster.getResidentialTel().split("\\|");
//            advisor.setRisd(tel.length > 0 && !tel[0].isEmpty()
//                    ? tel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
//            advisor.setRstd(tel.length > 1 && !tel[1].isEmpty()
//                    ? tel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
//            advisor.setRtnumber(tel.length > 2 && !tel[2].isEmpty()
//                    ? tel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisor.setOrganization(tempmaster.getOrganisation() == null ? EMPTY_STRING : tempmaster.getOrganisation());
            advisor.setJobTitle(tempmaster.getJobtitle() == null ? EMPTY_STRING : tempmaster.getJobtitle());

            String[] offcaddress = tempmaster.getOffcAddress().split("\\|");
            advisor.setOaddressLine1(offcaddress.length > 0 && !offcaddress[0].isEmpty()
                    ? offcaddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisor.setOaddressLine2(offcaddress.length > 1 && !offcaddress[1].isEmpty()
                    ? offcaddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisor.setOlandMark(offcaddress.length > 2 && !offcaddress[2].isEmpty()
                    ? offcaddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            advisor.setOpincode(tempmaster.getOffcPincode() == null ? EMPTY_STRING : tempmaster.getOffcPincode());
            advisor.setOcountry(tempmaster.getOffcCountry() == null ? EMPTY_STRING : tempmaster.getOffcCountry());
            advisor.setOstate(tempmaster.getOffcState() == null ? EMPTY_STRING : tempmaster.getOffcState());
            advisor.setOcity(tempmaster.getOffcCity() == null ? EMPTY_STRING : tempmaster.getOffcCity());
            advisor.setOmobile(tempmaster.getOffcMob() == null ? EMPTY_STRING : tempmaster.getOffcMob());
            advisor.setOemail(tempmaster.getOffcEmail() == null ? EMPTY_STRING : tempmaster.getOffcEmail());

            String[] otel = tempmaster.getOffcTel().split("\\|");
            advisor.setOisd(otel.length > 0 && !otel[0].isEmpty()
                    ? otel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisor.setOstd(otel.length > 1 && !otel[1].isEmpty()
                    ? otel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisor.setOtnumber(otel.length > 2 && !otel[2].isEmpty()
                    ? otel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

//            advisor.setCorrespondenceAddress(tempmaster.getCorrespondenceAddrs() == null ? EMPTY_STRING : tempmaster.getCorrespondenceAddrs());
            advisor.setBankName(tempmaster.getBankName() == null ? EMPTY_STRING : tempmaster.getBankName());
//            advisor.setBranch(tempmaster.getBranch() == null ? EMPTY_STRING : tempmaster.getBranch());
            advisor.setAccountType(tempmaster.getAccountType() == null ? EMPTY_STRING : tempmaster.getAccountType());
            advisor.setAccountNumber(tempmaster.getAccountNmbr() == null ? EMPTY_STRING : tempmaster.getAccountNmbr());
            advisor.setRaccountNumber(tempmaster.getReAccno() == null ? EMPTY_STRING : tempmaster.getReAccno());
            advisor.setIfscNo(tempmaster.getIfsc() == null ? EMPTY_STRING : tempmaster.getIfsc());
            advisor.setMicrNo(tempmaster.getMicr() == null ? EMPTY_STRING : tempmaster.getMicr());

            String[] bnkaddrs = tempmaster.getBankAddrs().split("\\|");
            advisor.setBaddressLine1(bnkaddrs.length > 0 && !bnkaddrs[0].isEmpty()
                    ? bnkaddrs[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisor.setBaddressLine2(bnkaddrs.length > 1 && !bnkaddrs[1].isEmpty()
                    ? bnkaddrs[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisor.setBlandMark(bnkaddrs.length > 2 && !bnkaddrs[2].isEmpty()
                    ? bnkaddrs[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            advisor.setBpincode(tempmaster.getBankPincode() == null ? EMPTY_STRING : tempmaster.getBankPincode());
            advisor.setBcountry(tempmaster.getBankCountry() == null ? EMPTY_STRING : tempmaster.getBankCountry());
            advisor.setBstate(tempmaster.getBankState() == null ? EMPTY_STRING : tempmaster.getBankState());
            advisor.setBcity(tempmaster.getBankCity() == null ? EMPTY_STRING : tempmaster.getBankCity());
            advisor.setPqualification(tempmaster.getPrimaryQualificationDegree() == null ? EMPTY_STRING : tempmaster.getPrimaryQualificationDegree());
//    Qualification id is commented according to the client request
//            advisor.setPqualificationId(tempmaster.getPrimaryQualificationId() == null ? EMPTY_STRING : tempmaster.getPrimaryQualificationId());
            advisor.setPinsititute(tempmaster.getPrimaryQualificationInstitute() == null ? EMPTY_STRING : tempmaster.getPrimaryQualificationInstitute());
            advisor.setPyear(tempmaster.getPrimaryQualificationYear() == null ? EMPTY_STRING : tempmaster.getPrimaryQualificationYear());

            advisor.setSqualification(tempmaster.getSecondaryQualificationDegree() == null ? EMPTY_STRING : tempmaster.getSecondaryQualificationDegree());
//            advisor.setSqualificationId(tempmaster.getSecondaryQualificationId() == null ? EMPTY_STRING : tempmaster.getSecondaryQualificationId());
            advisor.setSinsititute(tempmaster.getSecondaryQualificationInstitute() == null ? EMPTY_STRING : tempmaster.getSecondaryQualificationInstitute());
            advisor.setSyear(tempmaster.getSecondaryQualificationYear() == null ? EMPTY_STRING : tempmaster.getSecondaryQualificationYear());

            advisor.setTqualification(tempmaster.getTertiaryQualificationDegree() == null ? EMPTY_STRING : tempmaster.getTertiaryQualificationDegree());
//            advisor.setTqualificationId(tempmaster.getTertiaryQualificationId() == null ? EMPTY_STRING : tempmaster.getTertiaryQualificationId());
            advisor.setTinsititute(tempmaster.getTertiaryQualificationInstitute() == null ? EMPTY_STRING : tempmaster.getTertiaryQualificationInstitute());
            advisor.setTyear(tempmaster.getTertiaryQualificationYear() == null ? EMPTY_STRING : tempmaster.getTertiaryQualificationYear());

            advisor.setRes_city_other(tempmaster.getResCityOther());
            advisor.setOff_city_other(tempmaster.getOffCityOther());
            advisor.setBnk_city_other(tempmaster.getBnkCityOther());
            advisor.setIndividualOrCoprt(tempmaster.getIndvOrCprt());
            advisor.setSebiPath(tempmaster.getSebiPath());
        }
        return advisor;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveInvTemp(InvestorTempRegistrationVo investorRegistrationVo) throws ParseException {
        StringBuilder logMessage = new StringBuilder("Creating new Investor profile with emailid ");
        logMessage.append(investorRegistrationVo.getInvestorRegPage1().getEmail());
        LOGGER.info(logMessage.toString());
        TempInv investor = (TempInv) userProfileDAO.getTempInvDetails(investorRegistrationVo.getInvestorRegPage1().getEmail());
        if (investor == null) {
            investor = new TempInv();
        }
        if (investorRegistrationVo.getInvestorRegPage1() != null) {
            investor.setRegistrationId(investorRegistrationVo.getInvestorRegPage1().getRegId());
            investor.setEmail(investorRegistrationVo.getInvestorRegPage1().getEmail());
            investor.setFirstname(investorRegistrationVo.getInvestorRegPage1().getFirstname());
            investor.setMiddlename(investorRegistrationVo.getInvestorRegPage1().getMiddlename());
            investor.setLastname(investorRegistrationVo.getInvestorRegPage1().getLastname());
            investor.setFatherSpouse(investorRegistrationVo.getInvestorRegPage1().getFatherSpouse());
            if (StringUtils.hasText(investorRegistrationVo.getInvestorRegPage1().getDob()) && investorRegistrationVo.getInvestorRegPage1().getDob().length() == 10) {
                investor.setDob(DateUtil.stringToDate(investorRegistrationVo.getInvestorRegPage1().getDob(), dd_MM_yyyy));
            }
            investor.setNationality(investorRegistrationVo.getInvestorRegPage1().getNationality());
            investor.setStatus(investorRegistrationVo.getInvestorRegPage1().getStatus());
            investor.setGender(investorRegistrationVo.getInvestorRegPage1().getGender());
            investor.setMstatus(investorRegistrationVo.getInvestorRegPage1().getMstatus());
            investor.setUid(investorRegistrationVo.getInvestorRegPage1().getUid());
            investor.setPan(investorRegistrationVo.getInvestorRegPage1().getPan());

            StringBuffer address = new StringBuffer();
            address.append(investorRegistrationVo.getInvestorRegPage1().getCaddressline1()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getCaddressline2())
                    .append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getClandmark());
            investor.setCaddress(address.toString());

            investor.setCpincode(investorRegistrationVo.getInvestorRegPage1().getCpincode());
            investor.setCountry(investorRegistrationVo.getInvestorRegPage1().getCountry());
            investor.setCstate(investorRegistrationVo.getInvestorRegPage1().getCstate());
            investor.setCcity(investorRegistrationVo.getInvestorRegPage1().getCcity());
            investor.setCproof(investorRegistrationVo.getInvestorRegPage1().getCproof());
            investor.setCcityOther(investorRegistrationVo.getInvestorRegPage1().getcCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage1().getcCityOther());
            if (StringUtils.hasText(investorRegistrationVo.getInvestorRegPage1().getCvalidity()) && investorRegistrationVo.getInvestorRegPage1().getCvalidity().length() == 10) {
                investor.setCvalidity(DateUtil.stringToDate(investorRegistrationVo.getInvestorRegPage1().getCvalidity(), dd_MM_yyyy));
            } else {
                investor.setCvalidity(null);
            }

            investor.setPermenentAddress(Boolean.valueOf(investorRegistrationVo.getInvestorRegPage1().getPermenentAddress()));
            if (investor.getPermenentAddress()) {
                StringBuffer paddress = new StringBuffer();
                paddress.append(investorRegistrationVo.getInvestorRegPage1().getPaddressline1()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getPaddressline2())
                        .append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getPlandmark());
                investor.setPaddress(paddress.toString());

                investor.setPpin(investorRegistrationVo.getInvestorRegPage1().getPpin());
                investor.setPcountry(investorRegistrationVo.getInvestorRegPage1().getPcountry());
                investor.setPstate(investorRegistrationVo.getInvestorRegPage1().getPstate());
                investor.setPcity(investorRegistrationVo.getInvestorRegPage1().getPcity());
                investor.setPproof(investorRegistrationVo.getInvestorRegPage1().getPproof());
                if (StringUtils.hasText(investorRegistrationVo.getInvestorRegPage1().getPvalidity()) && investorRegistrationVo.getInvestorRegPage1().getPvalidity().length() == 10) {
                    investor.setPvalidity(DateUtil.stringToDate(investorRegistrationVo.getInvestorRegPage1().getPvalidity(), dd_MM_yyyy));
                } else {
                    investor.setPvalidity(null);
                }
                investor.setPcityOther(investorRegistrationVo.getInvestorRegPage1().getpCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage1().getpCityOther());
            }

            investor.setMobile(investorRegistrationVo.getInvestorRegPage1().getMobile());
            StringBuffer tel = new StringBuffer();
            tel.append(investorRegistrationVo.getInvestorRegPage1().getHistd()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getHstd())
                    .append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getHtelephone());
            investor.setHtelephone(tel.toString());
            StringBuffer restel = new StringBuffer();
            restel.append(investorRegistrationVo.getInvestorRegPage1().getRisd()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getRstd())
                    .append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getRtelephone());
            investor.setRtelephone(restel.toString());
            StringBuffer offctel = new StringBuffer();
            offctel.append(investorRegistrationVo.getInvestorRegPage1().getFisd()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getFstd())
                    .append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getFtelphone());
            investor.setFtelphone(offctel.toString());

            investor.setBankname(investorRegistrationVo.getInvestorRegPage1().getBankname());
//            investor.setBranch(investorRegistrationVo.getInvestorRegPage1().getBranch());
            investor.setAccountType(investorRegistrationVo.getInvestorRegPage1().getAccountType());
            investor.setAccno(investorRegistrationVo.getInvestorRegPage1().getAccno());
            investor.setReAccno(investorRegistrationVo.getInvestorRegPage1().getReAccno());
            investor.setBifsc(investorRegistrationVo.getInvestorRegPage1().getBifsc());
            investor.setBmicr(investorRegistrationVo.getInvestorRegPage1().getBmicr());

            StringBuffer bnkaddress = new StringBuffer();
            bnkaddress.append(investorRegistrationVo.getInvestorRegPage1().getBaddressline1()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getBaddressline2())
                    .append(PIPE_SEPERATOR).append(investorRegistrationVo.getInvestorRegPage1().getBlandmark());
            investor.setBaddress(bnkaddress.toString());
            investor.setBpincode(investorRegistrationVo.getInvestorRegPage1().getBpincode());
            investor.setBcountry(investorRegistrationVo.getInvestorRegPage1().getBcountry());
            investor.setBstate(investorRegistrationVo.getInvestorRegPage1().getBstate());
            investor.setBcity(investorRegistrationVo.getInvestorRegPage1().getBcity());
            investor.setBnkCityOther(investorRegistrationVo.getInvestorRegPage1().getBnkCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage1().getBnkCityOther());

            StringBuffer name = new StringBuffer();
            name.append(investorRegistrationVo.getInvestorRegPage1().getFirstname())
                    .append(SPACE).append(investorRegistrationVo.getInvestorRegPage1().getMiddlename())
                    .append(SPACE).append(investorRegistrationVo.getInvestorRegPage1().getLastname());
            investor.setBeneficiary(name.toString());
            investor.setPanFilePath(investorRegistrationVo.getInvestorRegPage1().getPanFilePath());
            investor.setCoresFilePath(investorRegistrationVo.getInvestorRegPage1().getCorsFilePath());
            investor.setPrmntFilePath(investorRegistrationVo.getInvestorRegPage1().getPrmntFilePath());
        }

        //Next Page details
        if (investorRegistrationVo.getInvestorRegPage2() != null) {
            investor.setOpenAccountType(investorRegistrationVo.getInvestorRegPage2().getOpenAccountType());
            investor.setDp(investorRegistrationVo.getInvestorRegPage2().getDp());
            investor.setTradingtAccount(investorRegistrationVo.getInvestorRegPage2().getTradingtAccount());
            investor.setDematAccount(investorRegistrationVo.getInvestorRegPage2().getDematAccount());
            investor.setSmsFacility(investorRegistrationVo.getInvestorRegPage2().getSmsFacility());
            investor.setFstHldrOccup(investorRegistrationVo.getInvestorRegPage2().getFstHldrOccup());
            investor.setFstHldrOrg(investorRegistrationVo.getInvestorRegPage2().getFstHldrOrg());
            investor.setFstHldrDesig(investorRegistrationVo.getInvestorRegPage2().getFstHldrDesig());
            investor.setFstHldrIncome(investorRegistrationVo.getInvestorRegPage2().getFstHldrIncome());
            if (StringUtils.hasText(investorRegistrationVo.getInvestorRegPage2().getFstHldrNet())
                    && investorRegistrationVo.getInvestorRegPage2().getFstHldrNet().length() == 10) {
                investor.setFstHldrNet(DateUtil.stringToDate(investorRegistrationVo.getInvestorRegPage2().getFstHldrNet(), dd_MM_yyyy));
            }
            investor.setFstHldrAmt(investorRegistrationVo.getInvestorRegPage2().getFstHldrAmt());
            investor.setPep(investorRegistrationVo.getInvestorRegPage2().getPep());
            investor.setRpep(investorRegistrationVo.getInvestorRegPage2().getRpep());

            investor.setScndHldrExist(investorRegistrationVo.getInvestorRegPage2().isScndHldrExist());
            if (investor.getScndHldrExist()) {
                investor.setScndHldrName(investorRegistrationVo.getInvestorRegPage2().getScndHldrName());
                investor.setScndHldrOccup(investorRegistrationVo.getInvestorRegPage2().getScndHldrOccup());
                investor.setScndHldrOrg(investorRegistrationVo.getInvestorRegPage2().getScndHldrOrg());
                investor.setScndHldrDesig(investorRegistrationVo.getInvestorRegPage2().getScndHldrDesig());
                investor.setScndHldrSms(investorRegistrationVo.getInvestorRegPage2().getScndHldrSms());
                investor.setScndHldrIncome(investorRegistrationVo.getInvestorRegPage2().getScndHldrIncome());
                if (StringUtils.hasText(investorRegistrationVo.getInvestorRegPage2().getScndHldrNet()) && investorRegistrationVo.getInvestorRegPage2().getScndHldrNet().length() == 10) {
                    investor.setScndHldrNet(DateUtil.stringToDate(investorRegistrationVo.getInvestorRegPage2().getScndHldrNet(), dd_MM_yyyy));
                }
                investor.setScndHldrAmt(investorRegistrationVo.getInvestorRegPage2().getScndHldrAmt());
                investor.setScndPep(investorRegistrationVo.getInvestorRegPage2().getScndPep());
                investor.setScndRpep(investorRegistrationVo.getInvestorRegPage2().getScndRpep());
                investor.setSecondHldrDependentRelation(investorRegistrationVo.getInvestorRegPage2().getSecondHldrDependentRelation());
                investor.setSecondHldrPan(investorRegistrationVo.getInvestorRegPage2().getSecondHldrPan() == null
                        ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getSecondHldrPan());
                investor.setSecondHldrDependentUsed(investorRegistrationVo.getInvestorRegPage2().getSecondHldrDependentUsed() == null
                        ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getSecondHldrDependentUsed());
            }

            investor.setInstrn1(investorRegistrationVo.getInvestorRegPage2().getInstrn1());
            investor.setInstrn2(investorRegistrationVo.getInvestorRegPage2().getInstrn2());
            investor.setInstrn3(investorRegistrationVo.getInvestorRegPage2().getInstrn3());
            investor.setInstrn4(investorRegistrationVo.getInvestorRegPage2().getInstrn4());
            investor.setInstrn5(investorRegistrationVo.getInvestorRegPage2().getInstrn5());
            investor.setDepoPartcpnt(investorRegistrationVo.getInvestorRegPage2().getDepoPartcpnt());
            investor.setDeponame(investorRegistrationVo.getInvestorRegPage2().getDeponame());
            investor.setDpId(investorRegistrationVo.getInvestorRegPage2().getDpId());
            investor.setDocEvdnc(investorRegistrationVo.getInvestorRegPage2().getDocEvdnc());
            investor.setOther(investorRegistrationVo.getInvestorRegPage2().getOther());
            investor.setExperience(investorRegistrationVo.getInvestorRegPage2().getExperience());
            investor.setContractNote(investorRegistrationVo.getInvestorRegPage2().getContractNote());
            investor.setIntrntTrading(investorRegistrationVo.getInvestorRegPage2().getIntrntTrading());
            investor.setAlert(investorRegistrationVo.getInvestorRegPage2().getAlert());
            investor.setRelationship(investorRegistrationVo.getInvestorRegPage2().getRelationship());
            investor.setPanAddtnl(investorRegistrationVo.getInvestorRegPage2().getPanAddtnl());
            investor.setFirstHldrDependentUsed(investorRegistrationVo.getInvestorRegPage2().getFirstHldrDependentUsed());
            investor.setOtherInformation(investorRegistrationVo.getInvestorRegPage2().getOtherInformation());

            investor.setNomineeExist(investorRegistrationVo.getInvestorRegPage2().isNomineeExist());
            if (investor.getNomineeExist()) {
                investor.setNameNominee(investorRegistrationVo.getInvestorRegPage2().getNameNominee());
                investor.setNomineeRelation(investorRegistrationVo.getInvestorRegPage2().getNomineeRelation());
                if (StringUtils.hasText(investorRegistrationVo.getInvestorRegPage2().getNomineeDob()) && investorRegistrationVo.getInvestorRegPage2().getNomineeDob().length() == 10) {
                    investor.setNomineeDob(DateUtil.stringToDate(investorRegistrationVo.getInvestorRegPage2().getNomineeDob(), dd_MM_yyyy));
                }
                investor.setNomineeProof(investorRegistrationVo.getInvestorRegPage2().getNomineeProof() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNomineeProof());
                investor.setNomineeAadhar(investorRegistrationVo.getInvestorRegPage2().getNomineAadhar() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNomineAadhar());
                investor.setNominePan(investorRegistrationVo.getInvestorRegPage2().getNominePan());
                StringBuffer nomineeAddress = new StringBuffer();
                nomineeAddress.append(investorRegistrationVo.getInvestorRegPage2().getNomineeAdrs1() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNomineeAdrs1())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getNomineeAdrs2() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNomineeAdrs2())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getNomineeLnd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNomineeLnd());
                investor.setNomineeAddress(nomineeAddress.toString());
                investor.setNomineePincode(investorRegistrationVo.getInvestorRegPage2().getNomineePincode());
                investor.setNomCountry(investorRegistrationVo.getInvestorRegPage2().getNomCountry());
                investor.setNomState(investorRegistrationVo.getInvestorRegPage2().getNomState());
                investor.setNomCity(investorRegistrationVo.getInvestorRegPage2().getNomCity());
                StringBuffer nomineeTel = new StringBuffer();
                nomineeTel.append(investorRegistrationVo.getInvestorRegPage2().getNoisd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNoisd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getNostd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNostd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getNotelephone() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNotelephone());
                investor.setNotelephone(nomineeTel.toString());

                StringBuffer nomineRes = new StringBuffer();
                nomineRes.append(investorRegistrationVo.getInvestorRegPage2().getNrisd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNrisd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getNrstd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNrstd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getnRtelephone() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getnRtelephone());
                investor.setNrtelephone(nomineRes.toString());

                StringBuffer nomineFax = new StringBuffer();
                nomineFax.append(investorRegistrationVo.getInvestorRegPage2().getNfisd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNfisd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getNfstd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNfstd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getNomineeFax() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNomineeFax());
                investor.setNomineeFax(nomineFax.toString());

                investor.setNomMobile(investorRegistrationVo.getInvestorRegPage2().getNomMobile());
                investor.setNomEmail(investorRegistrationVo.getInvestorRegPage2().getNomEmail());
                investor.setNomCityOther(investorRegistrationVo.getInvestorRegPage2().getNomCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getNomCityOther());
            }

            investor.setMinorExist(investorRegistrationVo.getInvestorRegPage2().isMinorExist());

            if (investor.getMinorExist()) {
                investor.setMinorGuard(investorRegistrationVo.getInvestorRegPage2().getMinorGuard());
                investor.setMnrReltn(investorRegistrationVo.getInvestorRegPage2().getMnrReltn());
                if (StringUtils.hasText(investorRegistrationVo.getInvestorRegPage2().getMnrDob()) && investorRegistrationVo.getInvestorRegPage2().getMnrDob().length() == 10) {
                    investor.setMnrDob(DateUtil.stringToDate(investorRegistrationVo.getInvestorRegPage2().getMnrDob(), dd_MM_yyyy));
                }
                investor.setMinorProof(investorRegistrationVo.getInvestorRegPage2().getMnrProof() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMnrProof());
                investor.setMinorPan(investorRegistrationVo.getInvestorRegPage2().getMnrPan() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMnrPan());
                investor.setMinorAadhar(investorRegistrationVo.getInvestorRegPage2().getMnrAadhar() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMnrAadhar());
                StringBuffer minorAddress = new StringBuffer();
                minorAddress.append(investorRegistrationVo.getInvestorRegPage2().getMnrAdrs1() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMnrAdrs1())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMnrAdrs2() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMnrAdrs2())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMnrLnd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMnrLnd());
                investor.setMinorAddress(minorAddress.toString());

                investor.setMnrCountry(investorRegistrationVo.getInvestorRegPage2().getMnrCountry());
                investor.setMnrState(investorRegistrationVo.getInvestorRegPage2().getMnrState());
                investor.setMnrCity(investorRegistrationVo.getInvestorRegPage2().getMnrCity());
                investor.setMnrPincode(investorRegistrationVo.getInvestorRegPage2().getMnrPincode());

                StringBuffer minorTel = new StringBuffer();
                minorTel.append(investorRegistrationVo.getInvestorRegPage2().getMoisd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMoisd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMostd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMostd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMotel() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMotel());
                investor.setMinortel(minorTel.toString());

                StringBuffer minorRes = new StringBuffer();
                minorRes.append(investorRegistrationVo.getInvestorRegPage2().getMrisd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMrisd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMrstd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMrstd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMrtel() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMrtel());
                investor.setMinrRestel(minorRes.toString());

                StringBuffer minorFax = new StringBuffer();
                minorFax.append(investorRegistrationVo.getInvestorRegPage2().getMfisd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMfisd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMfstd() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMfstd())
                        .append(PIPE_SEPERATOR)
                        .append(investorRegistrationVo.getInvestorRegPage2().getMinorfax() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMinorfax());
                investor.setMinorfax(minorFax.toString());

                investor.setMnrMob(investorRegistrationVo.getInvestorRegPage2().getMnrMob());
                investor.setMnrEmail(investorRegistrationVo.getInvestorRegPage2().getMnrEmail());
                investor.setMinorCityother(investorRegistrationVo.getInvestorRegPage2().getMnrCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getInvestorRegPage2().getMnrCityOther());
            }
            investor.setDocFilePath(investorRegistrationVo.getInvestorRegPage2().getDocFilePath());
            //FATCA details
            investor.setUsNational(investorRegistrationVo.getInvestorRegPage2().getUsNational());
            investor.setUsResident(investorRegistrationVo.getInvestorRegPage2().getUsResident());
            investor.setUsBorn(investorRegistrationVo.getInvestorRegPage2().getUsBorn());
            investor.setUsAddress(investorRegistrationVo.getInvestorRegPage2().getUsAddress());
            investor.setUsTelephone(investorRegistrationVo.getInvestorRegPage2().getUsTelephone());
            investor.setUsStandingInstruction(investorRegistrationVo.getInvestorRegPage2().getUsStandingInstruction());
            investor.setUsPoa(investorRegistrationVo.getInvestorRegPage2().getUsPoa());
            investor.setUsMailAddress(investorRegistrationVo.getInvestorRegPage2().getUsMailAddress());
            investor.setIndividualTaxIdntfcnNmbr(investorRegistrationVo.getInvestorRegPage2().getIndividualTaxIdntfcnNmbr());

        }
        userProfileDAO.saveTempInvestor(investor);
    }

    /**
     *
     * @param email
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public RegistrationVo getTempInvData(String email) {
        RegistrationVo regvo = new RegistrationVo();
        TempInv investor = (TempInv) userProfileDAO.getTempInvDetails(email);
        if (investor != null) {
            regvo.setRegId(investor.getRegistrationId() == null ? EMPTY_STRING : investor.getRegistrationId());
            regvo.setEmail(investor.getEmail() == null ? EMPTY_STRING : (investor.getEmail().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getEmail()));
            regvo.setName(investor.getFirstname() == null ? EMPTY_STRING : (investor.getFirstname().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getFirstname()));
            regvo.setMiddleName(investor.getMiddlename() == null ? EMPTY_STRING : (investor.getMiddlename().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getMiddlename()));
            regvo.setLastName(investor.getLastname() == null ? EMPTY_STRING : (investor.getMiddlename().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getLastname()));
            regvo.setFatherOrSpouseName(investor.getFatherSpouse() == null ? EMPTY_STRING : (investor.getFatherSpouse().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getFatherSpouse()));
            regvo.setDob(investor.getDob());
            regvo.setNationality(investor.getNationality() == null ? EMPTY_STRING : (investor.getNationality().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getNationality()));
            regvo.setStatus(investor.getStatus() == null ? EMPTY_STRING : (investor.getStatus().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getStatus()));
            regvo.setGender(investor.getGender() == null ? EMPTY_STRING : (investor.getGender().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getGender()));
            regvo.setMaritalStatus(investor.getMstatus() == null ? EMPTY_STRING : (investor.getMstatus().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getMstatus()));
            regvo.setUid(investor.getUid() == null ? EMPTY_STRING : (investor.getUid().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getUid()));
            regvo.setPan(investor.getPan() == null ? EMPTY_STRING : (investor.getPan().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPan()));
            regvo.setMotherName(investor.getMotherName() == null ? EMPTY_STRING : (investor.getMotherName().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getMotherName()));
            String addressString = investor.getCaddress() == null ? EMPTY_STRING : (investor.getCaddress().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getCaddress());
            String[] address = addressString.split("\\|");
            //String[] address = investor.getCaddress().split("\\|");
            regvo.setAddress1_Line1(address.length > 0 && !address[0].isEmpty()
                    ? address[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regvo.setAddress1_Street(address.length > 1 && !address[1].isEmpty()
                    ? address[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            regvo.setAddress1_Landmark(address.length > 2 && !address[2].isEmpty()
                    ? address[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            regvo.setCpinCode(investor.getCpincode() == null ? EMPTY_STRING : (investor.getCpincode().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getCpincode()));
            regvo.setCcountry(StringUtils.hasText(investor.getCountry()) ? investor.getCountry() : IN);
            regvo.setCstate(investor.getCstate() == null ? EMPTY_STRING : (investor.getCstate().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getCstate()));
            regvo.setCcity(investor.getCcity() == null ? EMPTY_STRING : (investor.getCcity().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getCcity()));
            regvo.setAddressProof(investor.getCproof() == null ? EMPTY_STRING : (investor.getCproof().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getCproof()));
            regvo.setDobValidity(investor.getCvalidity());
            regvo.setCp_city_other(investor.getCcityOther() == null ? EMPTY_STRING : (investor.getCcityOther().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getCcityOther()));

            regvo.setPermenentAddress(investor.getPermenentAddress());
            regvo.setPcountry(StringUtils.hasText(investor.getPcountry()) ? investor.getPcountry() : IN);

            if (investor.getPermenentAddress() != null && investor.getPermenentAddress()) {
                String paddressString = investor.getPaddress() == null ? EMPTY_STRING : (investor.getPaddress().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPaddress());
                String[] paddress = paddressString.split("\\|");
                //String[] paddress = investor.getPaddress().split("\\|");
                regvo.setAddress2_Line1(paddress.length > 0 && !paddress[0].isEmpty()
                        ? paddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setAddress2_Street(paddress.length > 1 && !paddress[1].isEmpty()
                        ? paddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setAddress2_Landmark(paddress.length > 2 && !paddress[2].isEmpty()
                        ? paddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                regvo.setPpinCode(investor.getPpin() == null ? EMPTY_STRING : (investor.getPpin().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPpin()));
                regvo.setPstate(investor.getPstate() == null ? EMPTY_STRING : (investor.getPstate().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPstate()));
                regvo.setPcity(investor.getPcity() == null ? EMPTY_STRING : (investor.getPcity().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPcity()));
                regvo.setAddress2_proof(investor.getPproof() == null ? EMPTY_STRING : (investor.getPproof().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPproof()));
                regvo.setAddress2_validity(investor.getPvalidity());
                regvo.setPermanentCityOther(investor.getPcityOther() == null ? EMPTY_STRING : (investor.getPcityOther().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPcityOther()));
            }

            regvo.setMobileNumber(investor.getMobile() == null ? EMPTY_STRING : (investor.getMobile().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getMobile()));
            if (investor.getHtelephone() != null) {
                String[] htel = investor.getHtelephone().split("\\|");
                regvo.setHisd(htel.length > 0 && !htel[0].isEmpty()
                        ? htel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setHstd(htel.length > 1 && !htel[1].isEmpty()
                        ? htel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setHnumber(htel.length > 2 && !htel[2].isEmpty()
                        ? htel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }

            if (investor.getRtelephone() != null) {
                String[] restel = investor.getRtelephone().split("\\|");
                regvo.setRisd(restel.length > 0 && !restel[0].isEmpty()
                        ? restel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setRstd(restel.length > 1 && !restel[1].isEmpty()
                        ? restel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setRnumber(restel.length > 2 && !restel[2].isEmpty()
                        ? restel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }
            if (investor.getFtelphone() != null) {
                String[] offctel = investor.getFtelphone().split("\\|");
                regvo.setFisd(offctel.length > 0 && !offctel[0].isEmpty()
                        ? offctel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setFstd(offctel.length > 1 && !offctel[1].isEmpty()
                        ? offctel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setFnumber(offctel.length > 2 && !offctel[2].isEmpty()
                        ? offctel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }
            regvo.setBankname(investor.getBankname() == null ? EMPTY_STRING : (investor.getBankname().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBankname()));
//            regvo.setBranch(investor.getBranch());
            regvo.setBankSubType(investor.getAccountType() == null ? EMPTY_STRING : (investor.getAccountType().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getAccountType()));
            regvo.setBankAccountNumber(investor.getAccno() == null ? EMPTY_STRING : (investor.getAccno().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getAccno()));
            regvo.setIfsc(investor.getBifsc() == null ? EMPTY_STRING : (investor.getBifsc().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBifsc()));
            regvo.setMicrCode(investor.getBmicr() == null ? EMPTY_STRING : (investor.getBmicr().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBmicr()));
            if (investor.getBaddress() != null) {
                String[] bnkaddress = investor.getBaddress().split("\\|");
                regvo.setBuilding(bnkaddress.length > 0 && !bnkaddress[0].isEmpty()
                        ? bnkaddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setBstreet(bnkaddress.length > 1 && !bnkaddress[1].isEmpty()
                        ? bnkaddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                regvo.setBarea(bnkaddress.length > 2 && !bnkaddress[2].isEmpty()
                        ? bnkaddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            }
            regvo.setBpincode(investor.getBpincode() == null ? EMPTY_STRING : (investor.getBpincode().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBpincode()));
            regvo.setBank_country(investor.getBcountry() == null ? EMPTY_STRING : (investor.getBcountry().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBcountry()));
            regvo.setBank_state(investor.getBstate() == null ? EMPTY_STRING : (investor.getBstate().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBstate()));
            regvo.setBcity(investor.getBcity() == null ? EMPTY_STRING : (investor.getBcity().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBcity()));
            regvo.setBnk_city_other(investor.getBnkCityOther() == null ? EMPTY_STRING : (investor.getBnkCityOther().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getBnkCityOther()));

            //page 2 details
            regvo.setOpenAccountType(investor.getOpenAccountType() == null ? EMPTY_STRING : (investor.getOpenAccountType().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getOpenAccountType()));
            regvo.setDpId(investor.getDp() == null ? EMPTY_STRING : (investor.getDp().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getDp()));
            regvo.setTradingtAccount(investor.getTradingtAccount() == null ? EMPTY_STRING : (investor.getTradingtAccount().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getTradingtAccount()));
            regvo.setDematAccount(investor.getDematAccount() == null ? EMPTY_STRING : (investor.getDematAccount().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getDematAccount()));
            regvo.setSmsFacility(investor.getSmsFacility() == null ? EMPTY_STRING : (investor.getSmsFacility().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getSmsFacility()));
            regvo.setOccupation(investor.getFstHldrOccup() == null ? EMPTY_STRING : (investor.getFstHldrOccup().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getFstHldrOccup()));
            regvo.setNameOfEmployerOrCompany(investor.getFstHldrOrg() == null ? EMPTY_STRING : (investor.getFstHldrOrg().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getFstHldrOrg()));
            regvo.setDesignation(investor.getFstHldrDesig() == null ? EMPTY_STRING : (investor.getFstHldrDesig().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getFstHldrDesig()));
            regvo.setIncomerange(investor.getFstHldrIncome() == null ? EMPTY_STRING : (investor.getFstHldrIncome().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getFstHldrIncome()));
            regvo.setNetWorthdate(investor.getFstHldrNet());
            regvo.setNetAmmount(investor.getFstHldrAmt() == null ? EMPTY_STRING : (investor.getFstHldrAmt().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getFstHldrAmt()));
            regvo.setPep(investor.getPep() == null ? EMPTY_STRING : (investor.getPep().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getPep()));
            regvo.setRpep(investor.getRpep() == null ? EMPTY_STRING : (investor.getRpep().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getRpep()));

            regvo.setSecondHolderDetailsAvailable(investor.getScndHldrExist() == null ? false : investor.getScndHldrExist());
            if (regvo.isSecondHolderDetailsAvailable()) {
                regvo.setNameSecondHolder(investor.getScndHldrName() == null ? EMPTY_STRING : (investor.getScndHldrName().equalsIgnoreCase("null") ? EMPTY_STRING : investor.getScndHldrName()));
                regvo.setOccupationSecondHolder(investor.getScndHldrOccup());
                regvo.setNameEmployerSecondHolder(investor.getScndHldrOrg());
                regvo.setDesignationSecondHolder(investor.getScndHldrDesig());
                regvo.setSmsFacilitySecondHolder(investor.getScndHldrSms());
                regvo.setIncomeRangeSecondHolder(investor.getScndHldrIncome());
                regvo.setNetWorthDateSecondHolder(investor.getScndHldrNet());
                regvo.setAmountSecondHolder(investor.getScndHldrAmt());
                regvo.setPoliticalyExposedSecondHolder(investor.getScndPep());
                regvo.setPoliticalyRelatedSecondHolder(investor.getScndRpep());
                regvo.setSecondHldrDependentRelation(investor.getSecondHldrDependentRelation());
                regvo.setSecondHldrPan(investor.getSecondHldrPan() == null
                        ? EMPTY_STRING : investor.getSecondHldrPan());
                regvo.setSecondHldrDependentUsed(investor.getSecondHldrDependentUsed() == null
                        ? EMPTY_STRING : investor.getSecondHldrDependentUsed());
            }

            regvo.setInstruction1(investor.getInstrn1());
            regvo.setInstruction2(investor.getInstrn2());
            regvo.setInstruction3(investor.getInstrn3());
            regvo.setInstruction4(investor.getInstrn4());
            regvo.setInstruction5(investor.getInstrn5());
            regvo.setDepositoryParticipantName(investor.getDepoPartcpnt());
            regvo.setDepositoryName(investor.getDeponame());
            regvo.setBeneficiaryName(investor.getBeneficiary());
            regvo.setDpIdDepository(investor.getDpId());
            regvo.setDocumentaryEvedence(StringUtils.hasText(investor.getDocEvdnc()) ? investor.getDocEvdnc() : "I dont want to opt for Derivative Trading");
            regvo.setOtherEvidenceProvided(investor.getOther());
            regvo.setTradingExperince(investor.getExperience());
            regvo.setTypeElectronicContract(investor.getContractNote());
            regvo.setFacilityInternetTrading(investor.getIntrntTrading());
            regvo.setTypeAlert(investor.getAlert());
            regvo.setRelationSipMobilenumber(investor.getRelationship());
            regvo.setPanMobileNumber(investor.getPanAddtnl());
            regvo.setFirstHldrDependentUsed(investor.getFirstHldrDependentUsed() == null
                    ? EMPTY_STRING : investor.getFirstHldrDependentUsed());
            regvo.setOtherInformations(investor.getOtherInformation());

            regvo.setNomineeExist(investor.getNomineeExist() == null ? false : investor.getNomineeExist());
            regvo.setCountryNominee(investor.getNomCountry() == null ? IN : investor.getNomCountry());
            if (regvo.isNomineeExist()) {
                regvo.setNameNominee(investor.getNameNominee());
                regvo.setRelationshipApplicant(investor.getNomineeRelation());
                regvo.setDobNominee(investor.getNomineeDob());
                regvo.setNominee_proof(investor.getNomineeProof());
                regvo.setNominee_aadhar(investor.getNomineeAadhar());
                regvo.setNominePan(investor.getNominePan());
                if (investor.getNomineeAddress() != null) {
                    String[] nomineAddress = investor.getNomineeAddress().split("\\|");
                    regvo.setAddress1_Line1_Nominee(nomineAddress.length > 0 && !nomineAddress[0].isEmpty()
                            ? nomineAddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setAddress1_Street_Nominee(nomineAddress.length > 1 && !nomineAddress[1].isEmpty()
                            ? nomineAddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setAddress1_Landmark_Nominee(nomineAddress.length > 2 && !nomineAddress[2].isEmpty()
                            ? nomineAddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                }
                regvo.setPincodeNominee(investor.getNomineePincode());
                regvo.setStateNominee(investor.getNomState());
                regvo.setPlaceNominee(investor.getNomCity());

                if (investor.getNotelephone() != null) {
                    String[] nomtel = investor.getNotelephone().split("\\|");
                    regvo.setNoisd(nomtel.length > 0 && !nomtel[0].isEmpty()
                            ? nomtel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setNostd(nomtel.length > 1 && !nomtel[1].isEmpty()
                            ? nomtel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setTelOfficeNominee(nomtel.length > 2 && !nomtel[2].isEmpty()
                            ? nomtel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                }
                if (investor.getNrtelephone() != null) {
                    String[] nomRes = investor.getNrtelephone().split("\\|");
                    regvo.setNrisd(nomRes.length > 0 && !nomRes[0].isEmpty()
                            ? nomRes[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setNrstd(nomRes.length > 1 && !nomRes[1].isEmpty()
                            ? nomRes[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setTelResidenceNominee(nomRes.length > 2 && !nomRes[2].isEmpty()
                            ? nomRes[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                }
                if (investor.getNomineeFax() != null) {
                    String[] nomFax = investor.getNomineeFax().split("\\|");
                    regvo.setNfisd(nomFax.length > 0 && !nomFax[0].isEmpty()
                            ? nomFax[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setNfstd(nomFax.length > 1 && !nomFax[1].isEmpty()
                            ? nomFax[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setFaxNominee(nomFax.length > 2 && !nomFax[2].isEmpty()
                            ? nomFax[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                }

                regvo.setMobileNominee(investor.getNomMobile());
                regvo.setEmailNominee(investor.getNomEmail());
                regvo.setNomCityOther(investor.getNomCityOther());
            }

            regvo.setNomineeMinor(investor.getMinorExist() == null ? false : investor.getMinorExist());
            regvo.setCountryNomineeMinor(investor.getMnrCountry() == null ? IN : investor.getMnrCountry());
            if (regvo.isNomineeMinor()) {
                regvo.setNameGuardianNominee(investor.getMinorGuard());
                regvo.setRelationshipGuardianMinor(investor.getMnrReltn());
                regvo.setDobMinor(investor.getMnrDob());
                regvo.setNomineeMinor_proof(investor.getMinorProof());
                regvo.setNomineeMinor_pan(investor.getMinorPan());
                regvo.setNomineeMinor_aadhar(investor.getMinorAadhar());
                if (investor.getMinorAddress() != null) {
                    String[] minorAddress = investor.getMinorAddress().split("\\|");
                    regvo.setAddress1_Line1_Minor(minorAddress.length > 0 && !minorAddress[0].isEmpty()
                            ? minorAddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setAddress1_Street_Minor(minorAddress.length > 1 && !minorAddress[1].isEmpty()
                            ? minorAddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setAddress1_Landmark_Minor(minorAddress.length > 2 && !minorAddress[2].isEmpty()
                            ? minorAddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                }
                regvo.setStateNomineeMinor(investor.getMnrState());
                regvo.setPlaceNomineeMinor(investor.getMnrCity());
                regvo.setPincodeNomineeMinor(investor.getMnrPincode());

                if (investor.getMinortel() != null) {
                    String[] minorOffc = investor.getMinortel().split("\\|");
                    regvo.setMoisd(minorOffc.length > 0 && !minorOffc[0].isEmpty()
                            ? minorOffc[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setMostd(minorOffc.length > 1 && !minorOffc[1].isEmpty()
                            ? minorOffc[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setTelOfficeNomineeMinor(minorOffc.length > 2 && !minorOffc[2].isEmpty()
                            ? minorOffc[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                }
                if (investor.getMinrRestel() != null) {
                    String[] minorRes = investor.getMinrRestel().split("\\|");
                    regvo.setMrisd(minorRes.length > 0 && !minorRes[0].isEmpty()
                            ? minorRes[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setMrstd(minorRes.length > 1 && !minorRes[1].isEmpty()
                            ? minorRes[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setTelResidenceNomineeMinor(minorRes.length > 2 && !minorRes[2].isEmpty()
                            ? minorRes[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                }

                if (investor.getMinorfax() != null) {
                    String[] minorFax = investor.getMinorfax().split("\\|");
                    regvo.setMfisd(minorFax.length > 0 && !minorFax[0].isEmpty()
                            ? minorFax[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setMfstd(minorFax.length > 1 && !minorFax[1].isEmpty()
                            ? minorFax[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                    regvo.setFaxNomineeMinor(minorFax.length > 2 && !minorFax[2].isEmpty()
                            ? minorFax[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                }
                regvo.setMobileNomineeMinor(investor.getMnrMob());
                regvo.setEmailNomineeMinor(investor.getMnrEmail());
                regvo.setMinorCityOther(investor.getMinorCityother());
            }
            regvo.setPanUploadFile(investor.getPanFilePath() == null ? EMPTY_STRING : investor.getPanFilePath());
            regvo.setCorespondenceAddressFile(investor.getCoresFilePath() == null ? EMPTY_STRING : investor.getCoresFilePath());
            regvo.setPermenentAddressFile(investor.getPrmntFilePath() == null ? EMPTY_STRING : investor.getPrmntFilePath());
            regvo.setDocumentaryFile(investor.getDocFilePath() == null ? EMPTY_STRING : investor.getDocFilePath());
            //FATCA details
            regvo.setUsNational(investor.getUsNational());
            regvo.setUsResident(investor.getUsResident());
            regvo.setUsBorn(investor.getUsBorn());
            regvo.setUsAddress(investor.getUsAddress());
            regvo.setUsTelephone(investor.getUsTelephone());
            regvo.setUsStandingInstruction(investor.getUsStandingInstruction());
            regvo.setUsPoa(investor.getUsPoa());
            regvo.setUsMailAddress(investor.getUsMailAddress());
            regvo.setIndividualTaxIdntfcnNmbr(investor.getIndividualTaxIdntfcnNmbr());
        }
        return regvo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public Integer getKitAllocationData() {
        RegKitVo regKitVo = new RegKitVo();
        boolean status = false;
        Integer kitNumber = 0;
        List<Object> responseList = userProfileDAO.getAllAllocation();
        List<KitAllocationTb> aloctnTb = (List<KitAllocationTb>) responseList.get(ZERO);
        Integer lastKit = (Integer) responseList.get(ONE);
        for (KitAllocationTb tbList : aloctnTb) {
            if (tbList.getKitStatus().equalsIgnoreCase(IN_USE)) {
                regKitVo.setId(tbList.getId());
                regKitVo.setFrom(tbList.getFromValue());
                regKitVo.setTo(tbList.getToValue());
                if (lastKit == ZERO) {
                    regKitVo.setLastKitAllotted(tbList.getFromValue() - 1);
                } else {
                    regKitVo.setLastKitAllotted(lastKit);
                }

                kitNumber = regKitVo.getLastKitAllotted() + 1;
                if (kitNumber >= regKitVo.getFrom()
                        && kitNumber <= regKitVo.getTo()) {
                    status = true;
                    if (kitNumber.equals(regKitVo.getTo())) {
                        //as all kit are alloted completely, update status to CLOSED
                        LOGGER.log(Level.INFO, "Client registration kit reached end limit");
                        LOGGER.log(Level.INFO, "opening newly available Client registration kit series..");
                        userProfileDAO.updateKitAllocationStatus(regKitVo.getId());
                        //send mail to admin 
                        sendMailToAdmin(regKitVo, "0", CLOSED);
                    }
                    if (kitNumber.equals(regKitVo.getTo() - 50)) {
                        LOGGER.log(Level.INFO, "only few more client registration kits available...");
                        //send warning mail to admin
                        userProfileDAO.sendNotification(50);
                        sendMailToAdmin(regKitVo, "50", IN_USE);
                    }
                }
            }
        }
        return kitNumber;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public RegistrationVo getRegistraionData(Integer kitNumber) {
        RegistrationVo regDataVo = new RegistrationVo();
        List<Object> response = userProfileDAO.getAllRegistrationData(kitNumber);
        List<MasterApplicantTb> masterData = (List<MasterApplicantTb>) response.get(0);
        for (MasterApplicantTb data : masterData) {
            if (data.getKitNumber() != null && data.getKitNumber().equals(kitNumber)) {
                regDataVo = getNewApplicantDetails(data);
            }
        }
        return regDataVo;
    }

    public void sendMailToAdmin(RegKitVo regKitVo, String balance, String status) {
        //send mail to admin 
        boolean mailStatus;
        try {
            StringWriter sw = new StringWriter();
            String mail = PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM);
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();

            Template template = ve.getTemplate("AdminNotificationMail.vm");
            VelocityContext context = TemplateUtil.getInstance().getVelocityContext();
            context.put("newline", NEW_LINE);
            context.put("name", "admin");
            context.put("start", regKitVo.getFrom());
            context.put("end", regKitVo.getTo());
            context.put("balance", balance);
            context.put("status", status);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(mail);
            mailStatus = MailUtil.getInstance().sendMail(mail, toAddress,
                    PropertiesLoader.getPropertiesValue(MMF_NOTIFICATION), sw.toString());
            LOGGER.log(Level.INFO, "sending mail to admin - status : {0}", mailStatus);

        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public BoRegDataServiceVo prepareForBackoffceTransfer(UserDetailsVO userdetails) {
        BoRegDataServiceVo temp = new BoRegDataServiceVo();
        temp.setId(userdetails.getMasterApplicant().getId());
        temp.setRegistrationId(userdetails.getMasterApplicant().getRegistrationId());
        StringBuilder kit = new StringBuilder();
        kit.append("G").append(userdetails.getMasterApplicant().getKitNumber() == null ? EMPTY_STRING
                : userdetails.getMasterApplicant().getKitNumber());
        temp.setKitNumber(userdetails.getMasterApplicant().getKitNumber() == null ? null
                : kit.toString());
        temp.setEmail(userdetails.getMasterApplicant().getEmail());
        temp.setFirstname(userdetails.getMasterApplicant().getName());
        temp.setMiddlename(userdetails.getMasterApplicant().getMiddleName());
        temp.setLastname(userdetails.getMasterApplicant().getLastName());
        temp.setFatherSpouse(userdetails.getMasterApplicant().getFatherSpouseName());
        temp.setDob(userdetails.getMasterApplicant().getDob());
        temp.setNationality(userdetails.getMasterApplicant().getNationality());
        temp.setStatus(userdetails.getMasterApplicant().getResidentialStatus());
        temp.setGender(userdetails.getMasterApplicant().getGender());
        temp.setMstatus(userdetails.getMasterApplicant().getMaritalStatus());
        temp.setUid(userdetails.getMasterApplicant().getUidAadhar());
        temp.setPan(userdetails.getMasterApplicant().getPan());
        temp.setCaddress(userdetails.getMasterApplicant().getAddress1());
        temp.setCpincode(userdetails.getMasterApplicant().getAddressPincode());
        temp.setCountry(userdetails.getMasterApplicant().getAddressCountry());
        temp.setCstate(userdetails.getMasterApplicant().getAddressState());
        temp.setCcity(userdetails.getMasterApplicant().getAddressCity());
        temp.setCproof(userdetails.getMasterApplicant().getProofOfAddress());
        temp.setCvalidity(userdetails.getMasterApplicant().getExpiryDate() == null ? null
                : userdetails.getMasterApplicant().getExpiryDate());
        temp.setPermenentAddress(userdetails.getMasterApplicant().getPermanentAddress());
        if (userdetails.getMasterApplicant().getPermanentAddress()) {
            temp.setPaddress(userdetails.getMasterApplicant().getAddress2());
            temp.setPpin(userdetails.getMasterApplicant().getAddress2Pincode());
            temp.setPcountry(userdetails.getMasterApplicant().getAddress2Country());
            temp.setPstate(userdetails.getMasterApplicant().getAddress2State());
            temp.setPcity(userdetails.getMasterApplicant().getAddress2City());
            temp.setPproof(userdetails.getMasterApplicant().getAddress2Proof());
            temp.setPvalidity(userdetails.getMasterApplicant().getAddress2Validity() == null ? null
                    : userdetails.getMasterApplicant().getAddress2Validity());
        }

        temp.setMobile(userdetails.getMasterApplicant().getMobile());
        StringBuffer tel = new StringBuffer();
        tel.append(userdetails.getMasterApplicant().getTelephone2Isd()).append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getTelephone2Std())
                .append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getTelephone2());
        temp.setHtelephone(tel.toString());
        StringBuffer restel = new StringBuffer();
        restel.append(userdetails.getMasterApplicant().getTelephoneIsd()).append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getTelephoneStd())
                .append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getTelephone());
        temp.setRtelephone(restel.toString());
        StringBuffer offctel = new StringBuffer();
        offctel.append(userdetails.getMasterApplicant().getFaxIsd()).append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getFaxStd())
                .append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getFax());
        temp.setFtelphone(offctel.toString());
        temp.setBankname(userdetails.getMasterApplicant().getBankName());
//        temp.setBranch(userdetails.getMasterApplicant().getBranch());
        temp.setAccountType(userdetails.getMasterApplicant().getBankSubtype());
        temp.setAccno(userdetails.getMasterApplicant().getAccountNumber());
//        temp.setReAccno(userdetails.getMasterApplicant().getAccountNumber());
        temp.setBifsc(userdetails.getMasterApplicant().getIfcsNumber());
        temp.setBmicr(userdetails.getMasterApplicant().getMicrNumber());
        StringBuffer bnkaddress = new StringBuffer();
        bnkaddress.append(userdetails.getMasterApplicant().getBankBuilding()).append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getBankStreet())
                .append(PIPE_SEPERATOR).append(userdetails.getMasterApplicant().getBankArea());
        temp.setBaddress(bnkaddress.toString());
        temp.setBpincode(userdetails.getMasterApplicant().getBankPincode());
        temp.setBcountry(userdetails.getMasterApplicant().getBankCountry());
        temp.setBstate(userdetails.getMasterApplicant().getBankState());
        temp.setBcity(userdetails.getMasterApplicant().getBankCity());
        temp.setBeneficiary(userdetails.getMasterApplicant().getBeneficiaryName());
//        temp.setPanFilePath(userdetails.getMasterApplicant().getIdentityProofPath() == null ? null
//                : userdetails.getMasterApplicant().getIdentityProofPath());
//        temp.setCoresFilePath(userdetails.getMasterApplicant().getCorrespondenceAddressPath() == null ? null
//                : userdetails.getMasterApplicant().getCorrespondenceAddressPath());
//        temp.setPrmntFilePath(userdetails.getMasterApplicant().getPermanentAddressPath() == null ? null
//                : userdetails.getMasterApplicant().getPermanentAddressPath());

        temp.setOpenAccountType(userdetails.getMasterApplicant().getAccountTypeOpen());
        temp.setDp(userdetails.getMasterApplicant().getDpId());
        temp.setTradingtAccount(userdetails.getMasterApplicant().getTradingAccountType());
        temp.setDematAccount(userdetails.getMasterApplicant().getDematAccountType());
        temp.setSmsFacility(userdetails.getMasterApplicant().getSmsFacility() == true ? YES : NO);
        temp.setFstHldrOccup(userdetails.getMasterApplicant().getOccupation());
        temp.setFstHldrOrg(userdetails.getMasterApplicant().getWorkOrganization());
        temp.setFstHldrDesig(userdetails.getMasterApplicant().getJobTitle());
//        String[] income_ranges = new String[]{"25-50 Lac", "50 Lac - 1 Crore", "1-5 Crore", "5-10 Crore", "> 10 Crore"};
//        if (Arrays.asList(income_ranges).contains(userdetails.getMasterApplicant().getIncomeRange())) {
//            temp.setFstHldrIncome("Above 25 Lakhs");
//        } else if (userdetails.getMasterApplicant().getIncomeRange().equalsIgnoreCase("10-25 Lac")) {
//            temp.setFstHldrIncome("Rs 15-25 Lakhs");
//        } else {
//            temp.setFstHldrIncome(userdetails.getMasterApplicant().getIncomeRange());
//        }
        temp.setFstHldrIncome(userdetails.getMasterApplicant().getIncomeRange());
        temp.setFstHldrNet(userdetails.getMasterApplicant().getNetWorthDate() == null ? null
                : userdetails.getMasterApplicant().getNetWorthDate());

        temp.setFstHldrAmt(userdetails.getMasterApplicant().getAmount());
        temp.setPep(userdetails.getMasterApplicant().getPoliticalyExposed() == true ? YES : NO);
        temp.setRpep(userdetails.getMasterApplicant().getPoliticalyRelated() == true ? YES : NO);

        temp.setScndHldrExist(userdetails.getMasterApplicant().getSecondHolderDetailsAvailable());
        if (userdetails.getMasterApplicant().getSecondHolderDetailsAvailable()) {
            temp.setScndHldrName(userdetails.getMasterApplicant().getNameSecondHolder());
            temp.setScndHldrOccup(userdetails.getMasterApplicant().getOccupationSecondHolder());
            temp.setScndHldrOrg(userdetails.getMasterApplicant().getNameEmployerSecondHolder());
            temp.setScndHldrDesig(userdetails.getMasterApplicant().getDesignationSecondHolder());
            temp.setScndHldrSms(userdetails.getMasterApplicant().getSmsFacilitySecondHolder() == true ? YES : NO);
//            if (Arrays.asList(income_ranges).contains(userdetails.getMasterApplicant().getIncomeRangeSecondHolder())) {
//                temp.setScndHldrIncome("Above 25 Lakhs");
//            } else if (userdetails.getMasterApplicant().getIncomeRange().equalsIgnoreCase("10-25 Lac")) {
//                temp.setScndHldrIncome("Rs 15-25 Lakhs");
//            } else {
//                temp.setScndHldrIncome(userdetails.getMasterApplicant().getIncomeRange());
//            }
            temp.setScndHldrIncome(userdetails.getMasterApplicant().getIncomeRangeSecondHolder());
            temp.setScndHldrNet(userdetails.getMasterApplicant().getNetWorthDateSecondHolder() == null ? null
                    : userdetails.getMasterApplicant().getNetWorthDateSecondHolder());
            temp.setScndHldrAmt(userdetails.getMasterApplicant().getAmountSecondHolder());
            temp.setScndPep(userdetails.getMasterApplicant().getPoliticalyExposedSecondHolder() == true ? YES : NO);
            temp.setScndRpep(userdetails.getMasterApplicant().getPoliticalyRelatedSecondHolder() == true ? YES : NO);
            temp.setSecondHldrDependentRelation(userdetails.getMasterApplicant().getSecondHldrDependentRelation());
            temp.setSecondHldrPan(userdetails.getMasterApplicant().getSecondHldrPan() == null
                    ? EMPTY_STRING : userdetails.getMasterApplicant().getSecondHldrPan());
            temp.setSecondHldrDependentUsed(userdetails.getMasterApplicant().getSecondHldrDependentUsed() == null
                    ? EMPTY_STRING : userdetails.getMasterApplicant().getSecondHldrDependentUsed());
        }

        temp.setInstrn1(userdetails.getMasterApplicant().getInstruction1().equalsIgnoreCase("0") ? YES : NO);
        temp.setInstrn2(userdetails.getMasterApplicant().getInstruction2().equalsIgnoreCase("2") ? YES : NO);
        temp.setInstrn3(userdetails.getMasterApplicant().getInstruction3().equalsIgnoreCase("4") ? YES : NO);
        temp.setInstrn4(userdetails.getMasterApplicant().getInstruction4().equalsIgnoreCase("6") ? YES : NO);
        temp.setInstrn5(userdetails.getMasterApplicant().getAddressForCommunication().equalsIgnoreCase("8") ? "Local/Permanent Address" : "Correspondence Address");
        temp.setDepoPartcpnt(userdetails.getMasterApplicant().getDepositoryParticipantName());
        temp.setDeponame(userdetails.getMasterApplicant().getDepositoryName());
//        temp.setDpId(userdetails.getMasterApplicant().getDpId());
        temp.setDocEvdnc(userdetails.getMasterApplicant().getDocumentaryEvedence());
//        temp.setDocFilePath(userdetails.getMasterApplicant().getDocumentaryEvidencePath() == null ? null
//                : userdetails.getMasterApplicant().getDocumentaryEvidencePath());
        temp.setOther(userdetails.getMasterApplicant().getDocumentaryEvedenceOther() == true ? YES : NO);
        temp.setExperience(userdetails.getMasterApplicant().getTradingExperince());
        temp.setContractNote(userdetails.getMasterApplicant().getTypeElectronicContract());
        temp.setIntrntTrading(userdetails.getMasterApplicant().getFacilityInternetTrading() == true ? YES : NO);
        temp.setAlert(userdetails.getMasterApplicant().getTypeAlert());
        temp.setRelationship(userdetails.getMasterApplicant().getRelationSipMobilenumber());
        temp.setPanAddtnl(userdetails.getMasterApplicant().getPanMobileNumber());
        temp.setFirstHldrDependentUsed(userdetails.getMasterApplicant().getFirstHldrDependentUsed());
        temp.setOtherInformation(userdetails.getMasterApplicant().getOtherInformations());

        temp.setNomineeExist(userdetails.getMasterApplicant().getNomineeExist());
        if (userdetails.getMasterApplicant().getNomineeExist()) {
            temp.setNameNominee(userdetails.getNomineeDetails().getNameNominee());
            temp.setNomineeRelation(userdetails.getNomineeDetails().getRelationshipApplicant());
            temp.setNomineeDob(userdetails.getNomineeDetails().getDobNominee());
            temp.setNominePan(userdetails.getNomineeDetails().getPanNomine());
            temp.setNomineeAddress(userdetails.getNomineeDetails().getAddressNominee());
            temp.setNomineePincode(userdetails.getNomineeDetails().getPincodeNominee());
            temp.setNomCountry(userdetails.getNomineeDetails().getCountryNominee());
            temp.setNomState(userdetails.getNomineeDetails().getStateNominee());
            temp.setNomCity(userdetails.getNomineeDetails().getPlaceNominee());
            temp.setNotelephone(userdetails.getNomineeDetails().getTelOfficeNominee());
            temp.setNrtelephone(userdetails.getNomineeDetails().getTelResidenceNominee());
            temp.setNomineeFax(userdetails.getNomineeDetails().getFaxNominee());
            temp.setNomMobile(userdetails.getNomineeDetails().getMobileNominee());
            temp.setNomEmail(userdetails.getNomineeDetails().getEmailNominee());
            temp.setMinorExist(userdetails.getNomineeDetails().getNomineeMinor());
            temp.setNomineeProof(userdetails.getNomineeDetails().getNomineeProof());
            temp.setNomineeAadhar(userdetails.getNomineeDetails().getNomineeAadhar());

            if (userdetails.getNomineeDetails().getNomineeMinor()) {
                temp.setMinorGuard(userdetails.getNomineeDetails().getNameGuardianNominee());
                temp.setMnrReltn(userdetails.getNomineeDetails().getRelationshipGuardianMinor());
                temp.setMnrDob(userdetails.getNomineeDetails().getDobNomineeMinor());
                temp.setMinorAddress(userdetails.getNomineeDetails().getAddressNomineeMinor());

                temp.setMnrCountry(userdetails.getNomineeDetails().getCountryNomineeMinor());
                temp.setMnrState(userdetails.getNomineeDetails().getStateNomineeMinor());
                temp.setMnrCity(userdetails.getNomineeDetails().getPincodeNomineeMinor());
                temp.setMnrPincode(userdetails.getNomineeDetails().getPincodeNomineeMinor());
                temp.setMinortel(userdetails.getNomineeDetails().getTelOfficeNomineeMinor());
                temp.setMinrRestel(userdetails.getNomineeDetails().getTelResidenceNomineeMinor());
                temp.setMinorfax(userdetails.getNomineeDetails().getFaxNomineeMinor());

                temp.setMnrMob(userdetails.getNomineeDetails().getMobileNomineeMinor());
                temp.setMnrEmail(userdetails.getNomineeDetails().getEmailNomineeMinor());
                temp.setNomineeMinorProof(userdetails.getNomineeDetails().getMinorProof());
                temp.setNomineeMinorAadhar(userdetails.getNomineeDetails().getMinorAadhar());
                temp.setNomineeMinorPan(userdetails.getNomineeDetails().getMinorPan());
            }
        }

        //FATCA details
        temp.setUsNational(userdetails.getMasterApplicant().getUsNational().equalsIgnoreCase("0") ? YES : NO);
        temp.setUsResident(userdetails.getMasterApplicant().getUsResident().equalsIgnoreCase("2") ? YES : NO);
        temp.setUsBorn(userdetails.getMasterApplicant().getUsBorn().equalsIgnoreCase("4") ? YES : NO);
        temp.setUsAddress(userdetails.getMasterApplicant().getUsAddress().equalsIgnoreCase("6") ? YES : NO);
        temp.setUsTelephone(userdetails.getMasterApplicant().getUsTelephone().equalsIgnoreCase("8") ? YES : NO);
        temp.setUsStandingInstruction(userdetails.getMasterApplicant().getUsStandingInstruction().equalsIgnoreCase("10") ? YES : NO);
        temp.setUsPoa(userdetails.getMasterApplicant().getUsPoa().equalsIgnoreCase("12") ? YES : NO);
        temp.setUsMailAddress(userdetails.getMasterApplicant().getUsMailAddress().equalsIgnoreCase("14") ? YES : NO);
        temp.setIndividualTaxIdntfcnNmbr(userdetails.getMasterApplicant().getIndividualTaxIdntfcnNmbr());
        return temp;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int updateSettings(String key, String value, boolean savenew) {
        MmfSettingsTb mst = new MmfSettingsTb();
        mst.setFieldname(key);
        mst.setFieldvalue(value);
        return userProfileDAO.updateSettings(mst, savenew);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<HolidayCalenderVo> getHolidayList() {
        List<HolidayCalenderVo> calenderVo = new ArrayList<HolidayCalenderVo>();
        List<ExchangeHolidayTb> tbs = userProfileDAO.getHolidayCalender();
        if (!tbs.isEmpty()) {
            for (ExchangeHolidayTb exchangeHolidayTb : tbs) {
                HolidayCalenderVo hcv = new HolidayCalenderVo();
                hcv.setId(exchangeHolidayTb.getId());
                hcv.setHoliday(exchangeHolidayTb.getHdate());
                hcv.setEvent(exchangeHolidayTb.getHevent());
                hcv.setEditable(false);
                calenderVo.add(hcv);
            }
        }

        return calenderVo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveHolidayList(HolidayCalenderVo calenderVo) {
        ExchangeHolidayTb holidayTb = new ExchangeHolidayTb();
        holidayTb.setId(calenderVo.getId() == null ? null : calenderVo.getId());
        holidayTb.setHdate(calenderVo.getHoliday());
        holidayTb.setHevent(calenderVo.getEvent());
        userProfileDAO.saveHoliday(holidayTb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void deleteHoliday(HolidayCalenderVo deleteHoliday) {
        ExchangeHolidayTb holidayTb = new ExchangeHolidayTb();
        holidayTb.setId(deleteHoliday.getId());
        holidayTb.setHdate(deleteHoliday.getHoliday());
        holidayTb.setHevent(deleteHoliday.getEvent());
        userProfileDAO.deleteHoliday(holidayTb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public TempInv getTempInvDetails(String email) {
        TempInv investor = (TempInv) userProfileDAO.getTempInvDetails(email);
        return investor;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateInvestor(TempInv investor) {
        userProfileDAO.saveTempInvestor(investor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public TempRegistrationTb getTempRegistrationTableData(String email, boolean userType) {
        String user_type = userType ? ADVISOR : INVESTOR;
        return userProfileDAO.getTempRegUserDetails(email, user_type);
    }

    /*
     @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
     public List<BankVo> lookforBank(String searchText) {
     List<BankVo> bankList = new ArrayList<BankVo>();
     List<Object[]> obj = userProfileDAO.getBankDetails(searchText);
     for (Object[] bank : obj) {
     BankVo bankvo = new BankVo();
     bankvo.setBank((String) bank[1]);
     bankvo.setIfsc((String) bank[2]);
     bankvo.setBranch((String) bank[3]);
     bankvo.setAddress((String) bank[4]);
     bankvo.setContact((String) bank[5]);
     bankvo.setCity((String) bank[6]);
     bankvo.setDistrict((String) bank[7]);
     bankvo.setState((String) bank[8]);
     bankvo.setMicr((String) bank[9]);
     bankList.add(bankvo);
     }
     return bankList;
     }
    
     @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
     public List<BankVo> onBankSelect(String bank, String state, String city) {
     List<BankVo> bankList = new ArrayList<BankVo>();
     List<BankTb> bankTbs = userProfileDAO.onBankSelect(bank, state, city);
     for (BankTb bankTb : bankTbs) {
     BankVo bankvo = new BankVo();
     bankvo.setBranch(bankTb.getBranch());
     bankvo.setIfsc(bankTb.getIfsc());
     bankvo.setAddress(bankTb.getAddress());
     bankvo.setContact(bankTb.getContact());
     bankvo.setDistrict(bankTb.getDistrict());
     bankvo.setCity(bankTb.getCity());
     bankList.add(bankvo);
     }
     return bankList;
     }*/
    //sumeet code start 
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveCompleteInvTemp(InvestorTempRegistrationVo investorRegistrationVo) throws ParseException {

        StringBuilder logMessage = new StringBuilder("Creating new Investor profile with emailid ");
        //logMessage.append(investorRegistrationVo.getInvestorRegPage1().getEmail());
        logMessage.append(investorRegistrationVo.getCompleteRegPage().getEmail());
        LOGGER.info(logMessage.toString());
        TempInv investor = (TempInv) userProfileDAO.getTempInvDetails(investorRegistrationVo.getCompleteRegPage().getEmail());
        if (investor == null) {
            investor = new TempInv();
        }
        // if (investorRegistrationVo.getCompleteRegPage() != null) {
        investor.setRegistrationId(investorRegistrationVo.getCompleteRegPage().getRegId());
        investor.setEmail(investorRegistrationVo.getCompleteRegPage().getEmail());
        investor.setFirstname(investorRegistrationVo.getCompleteRegPage().getFirstname() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getFirstname());
        investor.setMiddlename(investorRegistrationVo.getCompleteRegPage().getMiddlename());
        investor.setLastname(investorRegistrationVo.getCompleteRegPage().getLastname());
        investor.setFatherSpouse(investorRegistrationVo.getCompleteRegPage().getFatherSpouse() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getFatherSpouse());
        investor.setMotherName(investorRegistrationVo.getCompleteRegPage().getMotherName());
        if (StringUtils.hasText(investorRegistrationVo.getCompleteRegPage().getDob()) && investorRegistrationVo.getCompleteRegPage().getDob().length() == 10) {
            investor.setDob(DateUtil.stringToDate(investorRegistrationVo.getCompleteRegPage().getDob(), dd_MM_yyyy));
        }
        investor.setNationality(investorRegistrationVo.getCompleteRegPage().getNationality());
        investor.setStatus(investorRegistrationVo.getCompleteRegPage().getStatus());
        investor.setGender(investorRegistrationVo.getCompleteRegPage().getGender());
        investor.setMstatus(investorRegistrationVo.getCompleteRegPage().getMstatus());
        investor.setUid(investorRegistrationVo.getCompleteRegPage().getUid());
        investor.setPan(investorRegistrationVo.getCompleteRegPage().getPan());

        StringBuffer address = new StringBuffer();
        address.append(investorRegistrationVo.getCompleteRegPage().getCaddressline1()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getCaddressline2())
                .append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getClandmark());
        investor.setCaddress(address.toString());

        investor.setCpincode(investorRegistrationVo.getCompleteRegPage().getCpincode());
        investor.setCountry(investorRegistrationVo.getCompleteRegPage().getCountry());
        investor.setCstate(investorRegistrationVo.getCompleteRegPage().getCstate());
        investor.setCcity(investorRegistrationVo.getCompleteRegPage().getCcity());
        investor.setCproof(investorRegistrationVo.getCompleteRegPage().getCproof());
        investor.setCcityOther(investorRegistrationVo.getCompleteRegPage().getcCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getcCityOther());
        if (StringUtils.hasText(investorRegistrationVo.getCompleteRegPage().getCvalidity()) && investorRegistrationVo.getCompleteRegPage().getCvalidity().length() == 10) {
            investor.setCvalidity(DateUtil.stringToDate(investorRegistrationVo.getCompleteRegPage().getCvalidity(), dd_MM_yyyy));
        } else {
            investor.setCvalidity(null);
        }

        investor.setPermenentAddress(Boolean.valueOf(investorRegistrationVo.getCompleteRegPage().getPermenentAddress()));
        if (investor.getPermenentAddress()) {
            StringBuffer paddress = new StringBuffer();
            paddress.append(investorRegistrationVo.getCompleteRegPage().getPaddressline1()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getPaddressline2())
                    .append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getPlandmark());
            investor.setPaddress(paddress.toString());

            investor.setPpin(investorRegistrationVo.getCompleteRegPage().getPpin());
            investor.setPcountry(investorRegistrationVo.getCompleteRegPage().getPcountry());
            investor.setPstate(investorRegistrationVo.getCompleteRegPage().getPstate());
            investor.setPcity(investorRegistrationVo.getCompleteRegPage().getPcity());
            investor.setPproof(investorRegistrationVo.getCompleteRegPage().getPproof());
            if (StringUtils.hasText(investorRegistrationVo.getCompleteRegPage().getPvalidity()) && investorRegistrationVo.getCompleteRegPage().getPvalidity().length() == 10) {
                investor.setPvalidity(DateUtil.stringToDate(investorRegistrationVo.getCompleteRegPage().getPvalidity(), dd_MM_yyyy));
            } else {
                investor.setPvalidity(null);
            }
            investor.setPcityOther(investorRegistrationVo.getCompleteRegPage().getpCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getpCityOther());
        }

        investor.setMobile(investorRegistrationVo.getCompleteRegPage().getMobile());
        StringBuffer tel = new StringBuffer();
        tel.append(investorRegistrationVo.getCompleteRegPage().getHistd()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getHstd())
                .append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getHtelephone());
        investor.setHtelephone(tel.toString());
        StringBuffer restel = new StringBuffer();
        restel.append(investorRegistrationVo.getCompleteRegPage().getRisd()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getRstd())
                .append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getRtelephone());
        investor.setRtelephone(restel.toString());
        StringBuffer offctel = new StringBuffer();
        offctel.append(investorRegistrationVo.getCompleteRegPage().getFisd()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getFstd())
                .append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getFtelphone());
        investor.setFtelphone(offctel.toString());

        investor.setBankname(investorRegistrationVo.getCompleteRegPage().getBankname());
//              investor.setBranch(investorRegistrationVo.getCompleteRegPage().getBranch());
        investor.setAccountType(investorRegistrationVo.getCompleteRegPage().getAccountType());
        investor.setAccno(investorRegistrationVo.getCompleteRegPage().getAccno());
        investor.setReAccno(investorRegistrationVo.getCompleteRegPage().getReAccno());
        investor.setBifsc(investorRegistrationVo.getCompleteRegPage().getBifsc());
        investor.setBmicr(investorRegistrationVo.getCompleteRegPage().getBmicr());

        StringBuffer bnkaddress = new StringBuffer();
        bnkaddress.append(investorRegistrationVo.getCompleteRegPage().getBaddressline1()).append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getBaddressline2())
                .append(PIPE_SEPERATOR).append(investorRegistrationVo.getCompleteRegPage().getBlandmark());
        investor.setBaddress(bnkaddress.toString());
        investor.setBpincode(investorRegistrationVo.getCompleteRegPage().getBpincode());
        investor.setBcountry(investorRegistrationVo.getCompleteRegPage().getBcountry());
        investor.setBstate(investorRegistrationVo.getCompleteRegPage().getBstate());
        investor.setBcity(investorRegistrationVo.getCompleteRegPage().getBcity());
        investor.setBnkCityOther(investorRegistrationVo.getCompleteRegPage().getBnkCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getBnkCityOther());

        StringBuffer name = new StringBuffer();
        name.append(investorRegistrationVo.getCompleteRegPage().getFirstname())
                .append(SPACE).append(investorRegistrationVo.getCompleteRegPage().getMiddlename())
                .append(SPACE).append(investorRegistrationVo.getCompleteRegPage().getLastname());
        investor.setBeneficiary(name.toString());
        investor.setPanFilePath(investorRegistrationVo.getCompleteRegPage().getPanFilePath());
        investor.setCoresFilePath(investorRegistrationVo.getCompleteRegPage().getCorsFilePath());
        investor.setPrmntFilePath(investorRegistrationVo.getCompleteRegPage().getPrmntFilePath());
        // }

        //Next Page details
        //  if (investorRegistrationVo.getCompleteRegPage() != null) {
        investor.setOpenAccountType(investorRegistrationVo.getCompleteRegPage().getOpenAccountType());
        investor.setDp(investorRegistrationVo.getCompleteRegPage().getDp());
        investor.setTradingtAccount(investorRegistrationVo.getCompleteRegPage().getTradingtAccount());
        investor.setDematAccount(investorRegistrationVo.getCompleteRegPage().getDematAccount());
        investor.setSmsFacility(investorRegistrationVo.getCompleteRegPage().getSmsFacility());
        investor.setFstHldrOccup(investorRegistrationVo.getCompleteRegPage().getFstHldrOccup());
        investor.setFstHldrOrg(investorRegistrationVo.getCompleteRegPage().getFstHldrOrg());
        investor.setFstHldrDesig(investorRegistrationVo.getCompleteRegPage().getFstHldrDesig());
        investor.setFstHldrIncome(investorRegistrationVo.getCompleteRegPage().getFstHldrIncome());
        if (StringUtils.hasText(investorRegistrationVo.getCompleteRegPage().getFstHldrNet())
                && investorRegistrationVo.getCompleteRegPage().getFstHldrNet().length() == 10) {
            investor.setFstHldrNet(DateUtil.stringToDate(investorRegistrationVo.getCompleteRegPage().getFstHldrNet(), dd_MM_yyyy));
        }
        investor.setFstHldrAmt(investorRegistrationVo.getCompleteRegPage().getFstHldrAmt());
        investor.setPep(investorRegistrationVo.getCompleteRegPage().getPep());
        investor.setRpep(investorRegistrationVo.getCompleteRegPage().getRpep());

        investor.setScndHldrExist(investorRegistrationVo.getCompleteRegPage().isScndHldrExist());
        if (investor.getScndHldrExist()) {
            investor.setScndHldrName(investorRegistrationVo.getCompleteRegPage().getScndHldrName());
            investor.setScndHldrOccup(investorRegistrationVo.getCompleteRegPage().getScndHldrOccup());
            investor.setScndHldrOrg(investorRegistrationVo.getCompleteRegPage().getScndHldrOrg());
            investor.setScndHldrDesig(investorRegistrationVo.getCompleteRegPage().getScndHldrDesig());
            investor.setScndHldrSms(investorRegistrationVo.getCompleteRegPage().getScndHldrSms());
            investor.setScndHldrIncome(investorRegistrationVo.getCompleteRegPage().getScndHldrIncome());
            if (StringUtils.hasText(investorRegistrationVo.getCompleteRegPage().getScndHldrNet()) && investorRegistrationVo.getCompleteRegPage().getScndHldrNet().length() == 10) {
                investor.setScndHldrNet(DateUtil.stringToDate(investorRegistrationVo.getCompleteRegPage().getScndHldrNet(), dd_MM_yyyy));
            }
            investor.setScndHldrAmt(investorRegistrationVo.getCompleteRegPage().getScndHldrAmt());
            investor.setScndPep(investorRegistrationVo.getCompleteRegPage().getScndPep());
            investor.setScndRpep(investorRegistrationVo.getCompleteRegPage().getScndRpep());
            investor.setSecondHldrDependentRelation(investorRegistrationVo.getCompleteRegPage().getSecondHldrDependentRelation());
            investor.setSecondHldrPan(investorRegistrationVo.getCompleteRegPage().getSecondHldrPan() == null
                    ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getSecondHldrPan());
            investor.setSecondHldrDependentUsed(investorRegistrationVo.getCompleteRegPage().getSecondHldrDependentUsed() == null
                    ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getSecondHldrDependentUsed());
        }

        investor.setInstrn1(investorRegistrationVo.getCompleteRegPage().getInstrn1());
        investor.setInstrn2(investorRegistrationVo.getCompleteRegPage().getInstrn2());
        investor.setInstrn3(investorRegistrationVo.getCompleteRegPage().getInstrn3());
        investor.setInstrn4(investorRegistrationVo.getCompleteRegPage().getInstrn4());
        investor.setInstrn5(investorRegistrationVo.getCompleteRegPage().getInstrn5());
        investor.setDepoPartcpnt(investorRegistrationVo.getCompleteRegPage().getDepoPartcpnt());
        investor.setDeponame(investorRegistrationVo.getCompleteRegPage().getDeponame());
        investor.setDpId(investorRegistrationVo.getCompleteRegPage().getDpId());
        investor.setDocEvdnc(investorRegistrationVo.getCompleteRegPage().getDocEvdnc());
        investor.setOther(investorRegistrationVo.getCompleteRegPage().getOther());
        investor.setExperience(investorRegistrationVo.getCompleteRegPage().getExperience());
        investor.setContractNote(investorRegistrationVo.getCompleteRegPage().getContractNote());
        investor.setIntrntTrading(investorRegistrationVo.getCompleteRegPage().getIntrntTrading());
        investor.setAlert(investorRegistrationVo.getCompleteRegPage().getAlert());
        investor.setRelationship(investorRegistrationVo.getCompleteRegPage().getRelationship());
        investor.setPanAddtnl(investorRegistrationVo.getCompleteRegPage().getPanAddtnl());
        investor.setFirstHldrDependentUsed(investorRegistrationVo.getCompleteRegPage().getFirstHldrDependentUsed());
        investor.setOtherInformation(investorRegistrationVo.getCompleteRegPage().getOtherInformation());

        investor.setNomineeExist(investorRegistrationVo.getCompleteRegPage().isNomineeExist());
        if (investor.getNomineeExist()) {
            investor.setNameNominee(investorRegistrationVo.getCompleteRegPage().getNameNominee());
            investor.setNomineeRelation(investorRegistrationVo.getCompleteRegPage().getNomineeRelation());
            if (StringUtils.hasText(investorRegistrationVo.getCompleteRegPage().getNomineeDob()) && investorRegistrationVo.getCompleteRegPage().getNomineeDob().length() == 10) {
                investor.setNomineeDob(DateUtil.stringToDate(investorRegistrationVo.getCompleteRegPage().getNomineeDob(), dd_MM_yyyy));
            }
            investor.setNomineeProof(investorRegistrationVo.getCompleteRegPage().getNomineeProof() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNomineeProof());
            investor.setNomineeAadhar(investorRegistrationVo.getCompleteRegPage().getNomineAadhar() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNomineAadhar());
            investor.setNominePan(investorRegistrationVo.getCompleteRegPage().getNominePan());
            StringBuffer nomineeAddress = new StringBuffer();
            nomineeAddress.append(investorRegistrationVo.getCompleteRegPage().getNomineeAdrs1() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNomineeAdrs1())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getNomineeAdrs2() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNomineeAdrs2())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getNomineeLnd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNomineeLnd());
            investor.setNomineeAddress(nomineeAddress.toString());
            investor.setNomineePincode(investorRegistrationVo.getCompleteRegPage().getNomineePincode());
            investor.setNomCountry(investorRegistrationVo.getCompleteRegPage().getNomCountry());
            investor.setNomState(investorRegistrationVo.getCompleteRegPage().getNomState());
            investor.setNomCity(investorRegistrationVo.getCompleteRegPage().getNomCity());
            StringBuffer nomineeTel = new StringBuffer();
            nomineeTel.append(investorRegistrationVo.getCompleteRegPage().getNoisd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNoisd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getNostd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNostd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getNotelephone() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNotelephone());
            investor.setNotelephone(nomineeTel.toString());

            StringBuffer nomineRes = new StringBuffer();
            nomineRes.append(investorRegistrationVo.getCompleteRegPage().getNrisd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNrisd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getNrstd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNrstd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getnRtelephone() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getnRtelephone());
            investor.setNrtelephone(nomineRes.toString());

            StringBuffer nomineFax = new StringBuffer();
            nomineFax.append(investorRegistrationVo.getCompleteRegPage().getNfisd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNfisd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getNfstd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNfstd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getNomineeFax() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNomineeFax());
            investor.setNomineeFax(nomineFax.toString());

            investor.setNomMobile(investorRegistrationVo.getCompleteRegPage().getNomMobile());
            investor.setNomEmail(investorRegistrationVo.getCompleteRegPage().getNomEmail());
            investor.setNomCityOther(investorRegistrationVo.getCompleteRegPage().getNomCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getNomCityOther());
        }

        investor.setMinorExist(investorRegistrationVo.getCompleteRegPage().isMinorExist());

        if (investor.getMinorExist()) {
            investor.setMinorGuard(investorRegistrationVo.getCompleteRegPage().getMinorGuard());
            investor.setMnrReltn(investorRegistrationVo.getCompleteRegPage().getMnrReltn());
            if (StringUtils.hasText(investorRegistrationVo.getCompleteRegPage().getMnrDob()) && investorRegistrationVo.getCompleteRegPage().getMnrDob().length() == 10) {
                investor.setMnrDob(DateUtil.stringToDate(investorRegistrationVo.getCompleteRegPage().getMnrDob(), dd_MM_yyyy));
            }
            investor.setMinorProof(investorRegistrationVo.getCompleteRegPage().getMnrProof() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMnrProof());
            investor.setMinorPan(investorRegistrationVo.getCompleteRegPage().getMnrPan() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMnrPan());
            investor.setMinorAadhar(investorRegistrationVo.getCompleteRegPage().getMnrAadhar() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMnrAadhar());
            StringBuffer minorAddress = new StringBuffer();
            minorAddress.append(investorRegistrationVo.getCompleteRegPage().getMnrAdrs1() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMnrAdrs1())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMnrAdrs2() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMnrAdrs2())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMnrLnd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMnrLnd());
            investor.setMinorAddress(minorAddress.toString());

            investor.setMnrCountry(investorRegistrationVo.getCompleteRegPage().getMnrCountry());
            investor.setMnrState(investorRegistrationVo.getCompleteRegPage().getMnrState());
            investor.setMnrCity(investorRegistrationVo.getCompleteRegPage().getMnrCity());
            investor.setMnrPincode(investorRegistrationVo.getCompleteRegPage().getMnrPincode());

            StringBuffer minorTel = new StringBuffer();
            minorTel.append(investorRegistrationVo.getCompleteRegPage().getMoisd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMoisd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMostd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMostd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMotel() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMotel());
            investor.setMinortel(minorTel.toString());

            StringBuffer minorRes = new StringBuffer();
            minorRes.append(investorRegistrationVo.getCompleteRegPage().getMrisd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMrisd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMrstd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMrstd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMrtel() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMrtel());
            investor.setMinrRestel(minorRes.toString());

            StringBuffer minorFax = new StringBuffer();
            minorFax.append(investorRegistrationVo.getCompleteRegPage().getMfisd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMfisd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMfstd() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMfstd())
                    .append(PIPE_SEPERATOR)
                    .append(investorRegistrationVo.getCompleteRegPage().getMinorfax() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMinorfax());
            investor.setMinorfax(minorFax.toString());

            investor.setMnrMob(investorRegistrationVo.getCompleteRegPage().getMnrMob());
            investor.setMnrEmail(investorRegistrationVo.getCompleteRegPage().getMnrEmail());
            investor.setMinorCityother(investorRegistrationVo.getCompleteRegPage().getMnrCityOther() == null ? EMPTY_STRING : investorRegistrationVo.getCompleteRegPage().getMnrCityOther());
        }
        investor.setDocFilePath(investorRegistrationVo.getCompleteRegPage().getDocFilePath());
        //FATCA details
        investor.setUsNational(investorRegistrationVo.getCompleteRegPage().getUsNational());
        investor.setUsResident(investorRegistrationVo.getCompleteRegPage().getUsResident());
        investor.setUsBorn(investorRegistrationVo.getCompleteRegPage().getUsBorn());
        investor.setUsAddress(investorRegistrationVo.getCompleteRegPage().getUsAddress());
        investor.setUsTelephone(investorRegistrationVo.getCompleteRegPage().getUsTelephone());
        investor.setUsStandingInstruction(investorRegistrationVo.getCompleteRegPage().getUsStandingInstruction());
        investor.setUsPoa(investorRegistrationVo.getCompleteRegPage().getUsPoa());
        investor.setUsMailAddress(investorRegistrationVo.getCompleteRegPage().getUsMailAddress());
        investor.setIndividualTaxIdntfcnNmbr(investorRegistrationVo.getCompleteRegPage().getIndividualTaxIdntfcnNmbr());

        // }
        userProfileDAO.saveTempInvestor(investor);

    }
    //sumeet code end

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getInvAccountStatus(String email) {
        LOGGER.log(Level.INFO, "input  :" + email);

        TempRegistrationTb tempReg = getTempRegistrationTableData(email, false);

        RegistrationVo regVo = getTempInvData(email);
        List investList = userProfileDAO.getInvestorDetails(regVo.getRegId(), email, true);
        MasterCustomerTb investorDetails;
        if (!investList.isEmpty()) {
            investorDetails = (MasterCustomerTb) investList.get(0);
            JSONArray processStatus = new JSONArray();
            JSONObject process = null;
            try {

                String PROCESS_LABEL = "process_label";
                String PROCESS_NAME = "process_name";
                String PROCESS_STATUS = "process_status";

                String PROCESS_LABEL_EMAIL_VERIFIED = "Email Verified.";
                String PROCESS_NAME_EMAIL_VERIFIED = "email_verified";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_EMAIL_VERIFIED);
                process.put(PROCESS_NAME, PROCESS_NAME_EMAIL_VERIFIED);
                process.put(PROCESS_STATUS, tempReg.getMailVerified());
                processStatus.put(process);

                String PROCESS_LABEL_ONLINE_DETAILS_SUBMITTED = "Online Details Submitted";
                String PROCESS_NAME_ONLINE_DETAILS_SUBMITTED = "online_detailsubmites";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_ONLINE_DETAILS_SUBMITTED);
                process.put(PROCESS_NAME, PROCESS_NAME_ONLINE_DETAILS_SUBMITTED);
                process.put(PROCESS_STATUS, investorDetails.getOnlineDetailsubmites());
                processStatus.put(process);

                String PROCESS_LABEL_FORM_COURIERED_CLIENT = "Form couriered to Client";
                String PROCESS_NAME_FORM_COURIERED_CLIENT = "form_couriered_Client";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_FORM_COURIERED_CLIENT);
                process.put(PROCESS_NAME, PROCESS_NAME_FORM_COURIERED_CLIENT);
                process.put(PROCESS_STATUS, investorDetails.getFormCourieredClient());
                processStatus.put(process);

                String PROCESS_LABEL_FORM_RECEIVED_FROM_CLIENT = "Form received from client";
                String PROCESS_NAME_FORM_RECEIVED_FROM_CLIENT = "form_received_client";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_FORM_RECEIVED_FROM_CLIENT);
                process.put(PROCESS_NAME, PROCESS_NAME_FORM_RECEIVED_FROM_CLIENT);
                process.put(PROCESS_STATUS, investorDetails.getFormReceivedClient());
                processStatus.put(process);

                String PROCESS_LABEL_FORM_VALIDATED = "Form Validated";
                String PROCESS_NAME_FORM_VALIDATED = "form_Validated";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_FORM_VALIDATED);
                process.put(PROCESS_NAME, PROCESS_NAME_FORM_VALIDATED);
                process.put(PROCESS_STATUS, investorDetails.getFormValidated());
                processStatus.put(process);

                String PROCESS_LABEL_ACCEPTED = "Accepted";
                String PROCESS_NAME_ACCEPTED = "accepted";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_ACCEPTED);
                process.put(PROCESS_NAME, PROCESS_NAME_ACCEPTED);
                process.put(PROCESS_STATUS, investorDetails.getAccepted());
                processStatus.put(process);

                String PROCESS_LABEL_REJECTED = "Rejected";
                String PROCESS_NAME_REJECTED = "rejected";
                String PROCESS_REJECTION_REASON = "rejection_Reason";
                String PROCESS_REJECTION_RESOLVED = "rejection_Resolved";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_REJECTED);
                process.put(PROCESS_NAME, PROCESS_NAME_REJECTED);
                process.put(PROCESS_STATUS, investorDetails.getRejected());
                process.put(PROCESS_REJECTION_REASON, investorDetails.getRejectionReason());
                process.put(PROCESS_REJECTION_RESOLVED, investorDetails.getRejectionResolved());
                processStatus.put(process);

                String PROCESS_LABEL_TRADING_DEMAT_ACTIVATED = "Trading & Demat Activated";
                String PROCESS_NAME_TRADING_DEMAT_ACTIVATED = "account_activated";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_TRADING_DEMAT_ACTIVATED);
                process.put(PROCESS_NAME, PROCESS_NAME_TRADING_DEMAT_ACTIVATED);
                process.put(PROCESS_STATUS, investorDetails.getAccountActivated());
                processStatus.put(process);

                String PROCESS_LABEL_UCC_CREATED = "UCC created";
                String PROCESS_NAME_UCC_CREATED = "uCC_created";
                process = new JSONObject();
                process.put(PROCESS_LABEL, PROCESS_LABEL_UCC_CREATED);
                process.put(PROCESS_NAME, PROCESS_NAME_UCC_CREATED);
                process.put(PROCESS_STATUS, investorDetails.getUccCreated());
                processStatus.put(process);

                return processStatus.toString();
            } catch (JSONException e) {
                LOGGER.log(Level.SEVERE, "error in json creation :", e);
                return processStatus.toString();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "ERROR:ERROR IN GETTING USER'S ACCOUNT STATUS", e);
                return processStatus.toString();
            }
        } else {
            LOGGER.log(Level.SEVERE, "ERROR:USER NOT FOUND!");
            return "ERROR:INVESTOR NOT FOUND!";
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public CompleteTempInvVo getCompleteInvData(String email) {
        CompleteTempInvVo completeTempInvVo = new CompleteTempInvVo();
        TempInv investor = (TempInv) userProfileDAO.getTempInvDetails(email);
        if (investor != null) {
            completeTempInvVo.setRegId(investor.getRegistrationId());
            completeTempInvVo.setEmail(investor.getEmail());
            completeTempInvVo.setFirstname(investor.getFirstname());
            completeTempInvVo.setMiddlename(investor.getMiddlename());
            completeTempInvVo.setLastname(investor.getLastname());
            completeTempInvVo.setFatherSpouse(investor.getFatherSpouse());
            completeTempInvVo.setMotherName(investor.getMotherName() == null ? "null" : investor.getMotherName());
            completeTempInvVo.setDob(DateUtil.dateToString(investor.getDob(), dd_MM_yyyy));
            completeTempInvVo.setNationality(investor.getNationality());
            completeTempInvVo.setStatus(investor.getStatus());
            completeTempInvVo.setGender(investor.getGender());
            completeTempInvVo.setMstatus(investor.getMstatus());
            completeTempInvVo.setUid(investor.getUid());
            completeTempInvVo.setPan(investor.getPan());

            String[] address = investor.getCaddress().split("\\|");
            completeTempInvVo.setCaddressline1(address.length > 0 && !address[0].isEmpty()
                    ? address[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setCaddressline2(address.length > 1 && !address[1].isEmpty()
                    ? address[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setClandmark(address.length > 2 && !address[2].isEmpty()
                    ? address[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            completeTempInvVo.setCpincode(investor.getCpincode());
            completeTempInvVo.setCountry(investor.getCountry());
            completeTempInvVo.setCstate(investor.getCstate());
            completeTempInvVo.setCcity(investor.getCcity());
            completeTempInvVo.setCproof(investor.getCproof());
            completeTempInvVo.setCvalidity(DateUtil.dateToString(investor.getCvalidity(), dd_MM_yyyy));
            completeTempInvVo.setcCityOther(investor.getCcityOther());
            completeTempInvVo.setPermenentAddress(String.valueOf(investor.getPermenentAddress()));
            if (investor.getPermenentAddress()) {
                String[] paddress = investor.getPaddress().split("\\|");
                completeTempInvVo.setPaddressline1(paddress.length > 0 && !paddress[0].isEmpty()
                        ? paddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setPaddressline2(paddress.length > 1 && !paddress[1].isEmpty()
                        ? paddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setPlandmark(paddress.length > 2 && !paddress[2].isEmpty()
                        ? paddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                completeTempInvVo.setPpin(investor.getPpin());
                completeTempInvVo.setPcountry(StringUtils.hasText(investor.getPcountry()) ? investor.getPcountry() : IN);
                completeTempInvVo.setPstate(investor.getPstate());
                completeTempInvVo.setPcity(investor.getPcity());
                completeTempInvVo.setPproof(investor.getPproof());
                completeTempInvVo.setPvalidity(DateUtil.dateToString(investor.getPvalidity(), dd_MM_yyyy));
                completeTempInvVo.setpCityOther(investor.getPcityOther());
            }
            completeTempInvVo.setMobile(investor.getMobile());
            String[] htel = investor.getHtelephone().split("\\|");
            completeTempInvVo.setHistd(htel.length > 0 && !htel[0].isEmpty()
                    ? htel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setHstd(htel.length > 1 && !htel[1].isEmpty()
                    ? htel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setHtelephone(htel.length > 2 && !htel[2].isEmpty()
                    ? htel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            String[] restel = investor.getRtelephone().split("\\|");
            completeTempInvVo.setRisd(restel.length > 0 && !restel[0].isEmpty()
                    ? restel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setRstd(restel.length > 1 && !restel[1].isEmpty()
                    ? restel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setRtelephone(restel.length > 2 && !restel[2].isEmpty()
                    ? restel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            String[] offctel = investor.getFtelphone().split("\\|");
            completeTempInvVo.setFisd(offctel.length > 0 && !offctel[0].isEmpty()
                    ? offctel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setFstd(offctel.length > 1 && !offctel[1].isEmpty()
                    ? offctel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setFtelphone(offctel.length > 2 && !offctel[2].isEmpty()
                    ? offctel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            completeTempInvVo.setBankname(investor.getBankname());
            completeTempInvVo.setAccountType(investor.getAccountType());
            completeTempInvVo.setReAccno(investor.getReAccno());
            completeTempInvVo.setAccno(investor.getAccno());
            completeTempInvVo.setBifsc(investor.getBifsc());

            completeTempInvVo.setBmicr(investor.getBmicr());

            String[] bnkaddress = investor.getBaddress().split("\\|");
            completeTempInvVo.setBaddressline1(bnkaddress.length > 0 && !bnkaddress[0].isEmpty()
                    ? bnkaddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setBaddressline2(bnkaddress.length > 1 && !bnkaddress[1].isEmpty()
                    ? bnkaddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            completeTempInvVo.setBlandmark(bnkaddress.length > 2 && !bnkaddress[2].isEmpty()
                    ? bnkaddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            completeTempInvVo.setBpincode(investor.getBpincode());
            completeTempInvVo.setBcountry(investor.getBcountry());
            completeTempInvVo.setBstate(investor.getBstate());
            completeTempInvVo.setBcity(investor.getBcity());
            completeTempInvVo.setBnkCityOther(investor.getBnkCityOther());

            //page 2 details
            completeTempInvVo.setOpenAccountType(investor.getOpenAccountType() == null ? EMPTY_STRING : investor.getOpenAccountType());
            completeTempInvVo.setDpId(investor.getDp() == null ? EMPTY_STRING : investor.getDp());
            completeTempInvVo.setTradingtAccount(investor.getTradingtAccount() == null ? EMPTY_STRING : investor.getTradingtAccount());
            completeTempInvVo.setDematAccount(investor.getDematAccount() == null ? EMPTY_STRING : investor.getDematAccount());
            completeTempInvVo.setSmsFacility(investor.getSmsFacility() == null ? EMPTY_STRING : investor.getSmsFacility());
            completeTempInvVo.setFstHldrOccup(investor.getFstHldrOccup() == null ? EMPTY_STRING : investor.getFstHldrOccup());
            completeTempInvVo.setFstHldrOrg(investor.getFstHldrOrg() == null ? EMPTY_STRING : investor.getFstHldrOrg());
            completeTempInvVo.setFstHldrDesig(investor.getFstHldrDesig() == null ? EMPTY_STRING : investor.getFstHldrDesig());
            completeTempInvVo.setFstHldrIncome(investor.getFstHldrIncome());
            completeTempInvVo.setFstHldrNet(DateUtil.dateToString(investor.getFstHldrNet(), dd_MM_yyyy));
            completeTempInvVo.setFstHldrAmt(investor.getFstHldrAmt());
            completeTempInvVo.setPep(investor.getPep());
            completeTempInvVo.setRpep(investor.getRpep());

            completeTempInvVo.setScndHldrExist(investor.getScndHldrExist() == null ? false : investor.getScndHldrExist());
            if (completeTempInvVo.isScndHldrExist()) {
                completeTempInvVo.setScndHldrName(investor.getScndHldrName());
                completeTempInvVo.setScndHldrOccup(investor.getScndHldrOccup());
                completeTempInvVo.setScndHldrOrg(investor.getScndHldrOrg());
                completeTempInvVo.setScndHldrDesig(investor.getScndHldrDesig());
                completeTempInvVo.setScndHldrSms(investor.getScndHldrSms());
                completeTempInvVo.setScndHldrIncome(investor.getScndHldrIncome());
                completeTempInvVo.setScndHldrNet(DateUtil.dateToString(investor.getScndHldrNet(), dd_MM_yyyy));
                completeTempInvVo.setScndHldrAmt(investor.getScndHldrAmt());
                completeTempInvVo.setScndPep(investor.getScndPep());
                completeTempInvVo.setScndRpep(investor.getScndRpep());
                completeTempInvVo.setSecondHldrDependentRelation(investor.getSecondHldrDependentRelation());
                completeTempInvVo.setSecondHldrPan(investor.getSecondHldrPan() == null
                        ? EMPTY_STRING : investor.getSecondHldrPan());
                completeTempInvVo.setSecondHldrDependentUsed(investor.getSecondHldrDependentUsed() == null
                        ? EMPTY_STRING : investor.getSecondHldrDependentUsed());
            }

            completeTempInvVo.setInstrn1(investor.getInstrn1());
            completeTempInvVo.setInstrn2(investor.getInstrn2());
            completeTempInvVo.setInstrn3(investor.getInstrn3());
            completeTempInvVo.setInstrn4(investor.getInstrn4());
            completeTempInvVo.setInstrn5(investor.getInstrn5());
            completeTempInvVo.setDepoPartcpnt(investor.getDepoPartcpnt());
            completeTempInvVo.setDeponame(investor.getDeponame());
            completeTempInvVo.setBeneficiary(investor.getBeneficiary());
            completeTempInvVo.setDpId(investor.getDpId());
            completeTempInvVo.setDocEvdnc(StringUtils.hasText(investor.getDocEvdnc()) ? investor.getDocEvdnc() : "I dont want to opt for Derivative Trading");
            completeTempInvVo.setExperience(investor.getExperience());
            completeTempInvVo.setContractNote(investor.getContractNote());
            completeTempInvVo.setIntrntTrading(investor.getIntrntTrading());
            completeTempInvVo.setAlert(investor.getAlert());
            completeTempInvVo.setRelationship(investor.getRelationship());
            completeTempInvVo.setPanAddtnl(investor.getPanAddtnl());
            completeTempInvVo.setFirstHldrDependentUsed(investor.getFirstHldrDependentUsed() == null
                    ? EMPTY_STRING : investor.getFirstHldrDependentUsed());
            completeTempInvVo.setOtherInformation(investor.getOtherInformation());

            completeTempInvVo.setNomineeExist(investor.getNomineeExist() == null ? false : investor.getNomineeExist());
            completeTempInvVo.setNomCountry(investor.getNomCountry() == null ? IN : investor.getNomCountry());
            if (completeTempInvVo.isNomineeExist()) {
                completeTempInvVo.setNameNominee(investor.getNameNominee());
                completeTempInvVo.setNomineeRelation(investor.getNomineeRelation());
                completeTempInvVo.setNomineeDob(DateUtil.dateToString(investor.getNomineeDob(), dd_MM_yyyy));
                completeTempInvVo.setNominePan(investor.getNominePan());
                completeTempInvVo.setNomineAadhar(investor.getNomineeAadhar());
                completeTempInvVo.setNomineeProof(investor.getNomineeProof());
                String[] nomineAddress = investor.getNomineeAddress().split("\\|");
                completeTempInvVo.setNomineeAdrs1(nomineAddress.length > 0 && !nomineAddress[0].isEmpty()
                        ? nomineAddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setNomineeAdrs2(nomineAddress.length > 1 && !nomineAddress[1].isEmpty()
                        ? nomineAddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setNomineeLnd(nomineAddress.length > 2 && !nomineAddress[2].isEmpty()
                        ? nomineAddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                completeTempInvVo.setNomineePincode(investor.getNomineePincode());
                completeTempInvVo.setNomState(investor.getNomState());
                completeTempInvVo.setNomCity(investor.getNomCity());

                String[] nomtel = investor.getNotelephone().split("\\|");
                completeTempInvVo.setNoisd(nomtel.length > 0 && !nomtel[0].isEmpty()
                        ? nomtel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setNostd(nomtel.length > 1 && !nomtel[1].isEmpty()
                        ? nomtel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setNotelephone(nomtel.length > 2 && !nomtel[2].isEmpty()
                        ? nomtel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                String[] nomRes = investor.getNrtelephone().split("\\|");
                completeTempInvVo.setNrisd(nomRes.length > 0 && !nomRes[0].isEmpty()
                        ? nomRes[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setNrstd(nomRes.length > 1 && !nomRes[1].isEmpty()
                        ? nomRes[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setnRtelephone(nomRes.length > 2 && !nomRes[2].isEmpty()
                        ? nomRes[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                String[] nomFax = investor.getNomineeFax().split("\\|");
                completeTempInvVo.setNfstd(nomFax.length > 0 && !nomFax[0].isEmpty()
                        ? nomFax[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setNfstd(nomFax.length > 1 && !nomFax[1].isEmpty()
                        ? nomFax[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setNomineeFax(nomFax.length > 2 && !nomFax[2].isEmpty()
                        ? nomFax[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                completeTempInvVo.setNomMobile(investor.getNomMobile());
                completeTempInvVo.setNomEmail(investor.getNomEmail());
                completeTempInvVo.setNomCityOther(investor.getNomCityOther());
            }

            completeTempInvVo.setMinorExist(investor.getMinorExist() == null ? false : investor.getMinorExist());
            completeTempInvVo.setMnrCountry(investor.getMnrCountry() == null ? IN : investor.getMnrCountry());
            if (completeTempInvVo.isMinorExist()) {
                completeTempInvVo.setMinorGuard(investor.getMinorGuard());
                completeTempInvVo.setMnrReltn(investor.getMnrReltn());
                completeTempInvVo.setMnrDob(DateUtil.dateToString(investor.getMnrDob(), dd_MM_yyyy));
                completeTempInvVo.setMnrProof(investor.getMinorProof());
                completeTempInvVo.setMnrPan(investor.getMinorPan());
                completeTempInvVo.setMnrAadhar(investor.getMinorAadhar());

                String[] minorAddress = investor.getMinorAddress().split("\\|");
                completeTempInvVo.setMnrAdrs1(minorAddress.length > 0 && !minorAddress[0].isEmpty()
                        ? minorAddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMnrAdrs2(minorAddress.length > 1 && !minorAddress[1].isEmpty()
                        ? minorAddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMnrLnd(minorAddress.length > 2 && !minorAddress[2].isEmpty()
                        ? minorAddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                completeTempInvVo.setMnrState(investor.getMnrState());
                completeTempInvVo.setMnrCity(investor.getMnrCity());
                completeTempInvVo.setMnrPincode(investor.getMnrPincode());

                String[] minorOffc = investor.getMinortel().split("\\|");
                completeTempInvVo.setMoisd(minorOffc.length > 0 && !minorOffc[0].isEmpty()
                        ? minorOffc[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMostd(minorOffc.length > 1 && !minorOffc[1].isEmpty()
                        ? minorOffc[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMotel(minorOffc.length > 2 && !minorOffc[2].isEmpty()
                        ? minorOffc[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                String[] minorRes = investor.getMinrRestel().split("\\|");
                completeTempInvVo.setMrisd(minorRes.length > 0 && !minorRes[0].isEmpty()
                        ? minorRes[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMrstd(minorRes.length > 1 && !minorRes[1].isEmpty()
                        ? minorRes[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMrtel(minorRes.length > 2 && !minorRes[2].isEmpty()
                        ? minorRes[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                String[] minorFax = investor.getMinorfax().split("\\|");
                completeTempInvVo.setMfisd(minorFax.length > 0 && !minorFax[0].isEmpty()
                        ? minorFax[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMfstd(minorFax.length > 1 && !minorFax[1].isEmpty()
                        ? minorFax[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
                completeTempInvVo.setMinorfax(minorFax.length > 2 && !minorFax[2].isEmpty()
                        ? minorFax[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

                completeTempInvVo.setMnrMob(investor.getMnrMob());
                completeTempInvVo.setMnrEmail(investor.getMnrEmail());
                completeTempInvVo.setMnrCityOther(investor.getMinorCityother());
            }
            completeTempInvVo.setPanFilePath(investor.getPanFilePath() == null ? EMPTY_STRING : investor.getPanFilePath());
            completeTempInvVo.setCorsFilePath(investor.getCoresFilePath() == null ? EMPTY_STRING : investor.getCoresFilePath());
            completeTempInvVo.setPrmntFilePath(investor.getPrmntFilePath() == null ? EMPTY_STRING : investor.getPrmntFilePath());
            completeTempInvVo.setDocFilePath(investor.getDocFilePath() == null ? EMPTY_STRING : investor.getDocFilePath());

            //FATCA details
            completeTempInvVo.setUsNational(investor.getUsNational());
            completeTempInvVo.setUsResident(investor.getUsResident());
            completeTempInvVo.setUsBorn(investor.getUsBorn());
            completeTempInvVo.setUsAddress(investor.getUsAddress());
            completeTempInvVo.setUsTelephone(investor.getUsTelephone());
            completeTempInvVo.setUsStandingInstruction(investor.getUsStandingInstruction());
            completeTempInvVo.setUsPoa(investor.getUsPoa());
            completeTempInvVo.setUsMailAddress(investor.getUsMailAddress());
            completeTempInvVo.setIndividualTaxIdntfcnNmbr(investor.getIndividualTaxIdntfcnNmbr());
        }

        return completeTempInvVo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isPanNoExists(String pan) {
        boolean result = userProfileDAO.getPanNoStatus(pan);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isEmailAlreadyRegistered(String email) {
        boolean result = userProfileDAO.isEmailAlreadyRegistered(email);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void resendVerificationEmail(String email, boolean userType) {
        // TODO Auto-generated method stub
        TempRegistrationTb tempRegistrationTb = getTempRegistrationTableData(email, userType);
        FailedMailDetailsVO failedMailDetails = getMailDatails(tempRegistrationTb);
        List<FailedMailDetailsVO> failedMailList = new ArrayList<FailedMailDetailsVO>();
        failedMailList.add(failedMailDetails);
        this.reSendMail(failedMailList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean updateVerificationIfUserDetailsSubmitted(String email, String userType) {
        // TODO Auto-generated method stub
        boolean detailsSubmitted = userProfileDAO.isApplicantDetailsSubmitted(email, userType);
        if (detailsSubmitted) {
            LOGGER.log(Level.INFO, "user details submitted for email =" + email);
            userProfileDAO.updateApplicantMailVerifed(email, userType);
            LOGGER.log(Level.INFO, "updating mail verified bit for email =" + email);
            boolean advisor = userType.equals("INVESTOR") ? false : true;
            if (!advisor) {
                RegistrationVo registrationVo = getTempInvData(email);
                try {
                    master = (MasterApplicantTb) userProfileDAO.getTemporaryUserDetails(registrationVo.getEmail());
                    String url = null;
                    StringBuffer name = new StringBuffer();
                    name.append(registrationVo.getName()).append(SPACE).append(registrationVo.getMiddleName())
                            .append(SPACE).append(registrationVo.getLastName());
                    boolean mailSendStatus = sendmail(name.toString(), registrationVo.getEmail(), registrationVo.getRegId(), registrationVo.getMobileNumber(), true);
                    IMailStatusBAO iMailStatusBAO = (IMailStatusBAO) BeanLoader.getBean("iMailStatusBAO");
                    iMailStatusBAO.updateMailStatus(master.getClass().getName(),
                            master.getEmail(), mailSendStatus);
                    LOGGER.log(Level.INFO, "mailed form to email =" + email + " success");
                } catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                }
            }

            return true;
        } else {
            LOGGER.log(Level.INFO, "user details not submitted for email =" + email);
            return false;
        }

    }

    // @author sumeet 
    //method for fetching advsor data for advisor prefill
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public TempAdvVo getCompleteTempAdvData(String email) {
        TempAdvVo advisorRegistrationVo = new TempAdvVo();
        TempAdv tempmaster = (TempAdv) userProfileDAO.getTempAdvDetails(email);
        if (tempmaster != null) {
            advisorRegistrationVo.setRegNO(tempmaster.getRegistrationId());
            advisorRegistrationVo.setRemail(tempmaster.getEmail());
            advisorRegistrationVo.setRegEmail(tempmaster.getEmail());
            advisorRegistrationVo.setFname(tempmaster.getFirstname());
            advisorRegistrationVo.setMiddle_name(tempmaster.getMiddlename());
            advisorRegistrationVo.setLast_name(tempmaster.getLastname());
            advisorRegistrationVo.setSebi_regno(tempmaster.getSebi());
            advisorRegistrationVo.setSebi_validity(DateUtil.dateToString(tempmaster.getValidityReg(), dd_MM_yyyy));
            advisorRegistrationVo.setDob(DateUtil.dateToString(tempmaster.getDob(), dd_MM_yyyy));
            advisorRegistrationVo.setPan(tempmaster.getPan());
            advisorRegistrationVo.setOrganization(tempmaster.getOrganisation());
            advisorRegistrationVo.setJobTitle(tempmaster.getJobtitle());

            String[] offcaddress = tempmaster.getOffcAddress().split("\\|");
            advisorRegistrationVo.setOaddressLine1(offcaddress.length > 0 && !offcaddress[0].isEmpty()
                    ? offcaddress[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisorRegistrationVo.setOaddressLine2(offcaddress.length > 1 && !offcaddress[1].isEmpty()
                    ? offcaddress[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisorRegistrationVo.setOlandMark(offcaddress.length > 2 && !offcaddress[2].isEmpty()
                    ? offcaddress[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            advisorRegistrationVo.setOpincode(tempmaster.getOffcPincode());
            advisorRegistrationVo.setOcountry(tempmaster.getOffcCountry());
            advisorRegistrationVo.setOstate(tempmaster.getOffcState());
            advisorRegistrationVo.setOcity(tempmaster.getOffcCity());
            advisorRegistrationVo.setOmobile(tempmaster.getOffcMob());
            advisorRegistrationVo.setOemail(tempmaster.getOffcEmail());

            String[] otel = tempmaster.getOffcTel().split("\\|");
            advisorRegistrationVo.setOisd(otel.length > 0 && !otel[0].isEmpty()
                    ? otel[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisorRegistrationVo.setOstd(otel.length > 1 && !otel[1].isEmpty()
                    ? otel[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisorRegistrationVo.setOtnumber(otel.length > 2 && !otel[2].isEmpty()
                    ? otel[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            advisorRegistrationVo.setBankName(tempmaster.getBankName());
            advisorRegistrationVo.setAccountType(tempmaster.getAccountType());
            advisorRegistrationVo.setAccountNumber(tempmaster.getAccountNmbr());
            advisorRegistrationVo.setRaccountNumber(tempmaster.getReAccno());
            advisorRegistrationVo.setIfscNo(tempmaster.getIfsc());
            advisorRegistrationVo.setMicrNo(tempmaster.getMicr());

            String[] bnkaddrs = tempmaster.getBankAddrs().split("\\|");
            advisorRegistrationVo.setBaddressLine1(bnkaddrs.length > 0 && !bnkaddrs[0].isEmpty()
                    ? bnkaddrs[0].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisorRegistrationVo.setBaddressLine2(bnkaddrs.length > 1 && !bnkaddrs[1].isEmpty()
                    ? bnkaddrs[1].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);
            advisorRegistrationVo.setBlandMark(bnkaddrs.length > 2 && !bnkaddrs[2].isEmpty()
                    ? bnkaddrs[2].replaceAll("\\|", EMPTY_STRING) : EMPTY_STRING);

            advisorRegistrationVo.setBpincode(tempmaster.getBankPincode());
            advisorRegistrationVo.setBcountry(tempmaster.getBankCountry());
            advisorRegistrationVo.setBstate(tempmaster.getBankState());
            advisorRegistrationVo.setBcity(tempmaster.getBankCity());
            advisorRegistrationVo.setPqualification(tempmaster.getPrimaryQualificationDegree());

            advisorRegistrationVo.setPinsititute(tempmaster.getPrimaryQualificationInstitute());
            advisorRegistrationVo.setPyear(tempmaster.getPrimaryQualificationYear());
            advisorRegistrationVo.setSqualification(tempmaster.getSecondaryQualificationDegree());
            advisorRegistrationVo.setSinsititute(tempmaster.getSecondaryQualificationInstitute());
            advisorRegistrationVo.setSyear(tempmaster.getSecondaryQualificationYear());
            advisorRegistrationVo.setTqualification(tempmaster.getTertiaryQualificationDegree());
            advisorRegistrationVo.setTinsititute(tempmaster.getTertiaryQualificationInstitute());
            advisorRegistrationVo.setTyear(tempmaster.getTertiaryQualificationYear());
            advisorRegistrationVo.setIndvOrCprt(tempmaster.getIndvOrCprt());
            advisorRegistrationVo.setResCity(tempmaster.getResCityOther() == null ? EMPTY_STRING : tempmaster.getResCityOther());
            advisorRegistrationVo.setOffCity(tempmaster.getOffCityOther() == null ? EMPTY_STRING : tempmaster.getOffCityOther());
            advisorRegistrationVo.setBnkCity(tempmaster.getBnkCityOther() == null ? EMPTY_STRING : tempmaster.getBnkCityOther());
            advisorRegistrationVo.setAdvisorType(tempmaster.getIndvOrCprt().toString());
            advisorRegistrationVo.setSebiPath(tempmaster.getSebiPath() == null ? EMPTY_STRING : tempmaster.getSebiPath());
            advisorRegistrationVo.setAdvPicPath(tempmaster.getAdvPicPath() == null ? EMPTY_STRING : tempmaster.getAdvPicPath());
            advisorRegistrationVo.setOneLineDesc(tempmaster.getOneLineDesc() == null ? EMPTY_STRING : tempmaster.getOneLineDesc());
            advisorRegistrationVo.setAboutMe(tempmaster.getAboutMe() == null ? EMPTY_STRING : tempmaster.getAboutMe());
            advisorRegistrationVo.setMyInvestmentStrategy(tempmaster.getMyInvestmentStrategy() == null ? EMPTY_STRING : tempmaster.getMyInvestmentStrategy());
        }
        return advisorRegistrationVo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean terminateContract(UserDetailsVO userdetails) {
        boolean isContractTerminate = false;
        Integer userId = userdetails.getUserId();
        Integer relationId = userProfileDAO.getRelationIdOfUser(userId);
        if (relationId != null) {
            Integer noOfRowsUpdated = userProfileDAO.updateContractTerminateStatus(relationId);
            if (noOfRowsUpdated != null || noOfRowsUpdated != ZERO) {
                System.out.println("Status of user with " + userId + " updated");
                isContractTerminate = true;
            } else {
                System.out.println("No such relation id");
                isContractTerminate = false;
            }
        } else {
            System.out.println("No such relation id");
            isContractTerminate = false;
        }
        return isContractTerminate;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean getUserEnableterminationStatus(Integer customerId) {
        boolean enableTerminationButton = true;
        Integer relationId = userProfileDAO.getRelationIdOfUser(customerId);
        if (relationId != null) {
            CustomerAdvisorMappingTb customerAdvisorMappingTb = userProfileDAO.getTerminationDetailsOfUser(relationId);
            if (customerAdvisorMappingTb != null) {
                if (customerAdvisorMappingTb.getContractTerminateStatus() != null && customerAdvisorMappingTb.getContractTerminateStatus()) {
                    if (!enableTerminationButton) {
                        enableTerminationButton = true;
                    } else {
                        enableTerminationButton = false;
                    }
                }

            }

        }
        return enableTerminationButton;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveInvestorDocumentDetails(RegistrationVo registrationVo) {
        StringBuilder logMessage = new StringBuilder("Saving investor proof details");
        logMessage.append(registrationVo.getEmail());
        LOGGER.info(logMessage.toString());
        master = new MasterApplicantTb();
        master.setEmail(registrationVo.getEmail());
        master.setIdentityProofPath(registrationVo.getPanUploadFile() == null ? EMPTY_STRING : registrationVo.getPanUploadFile());
        master.setCorrespondenceAddressPath(registrationVo.getCorespondenceAddressFile() == null
                ? EMPTY_STRING : registrationVo.getCorespondenceAddressFile());
        master.setPermanentAddressPath(registrationVo.getPermenentAddressFile() == null
                ? EMPTY_STRING : registrationVo.getPermenentAddressFile());
        LOGGER.log(Level.INFO, "updating document details  for email =" + registrationVo.getEmail());
        userProfileDAO.saveInvestorDocumentDetails(master);
        LOGGER.log(Level.INFO, "updated document details  for email =" + registrationVo.getEmail() + "Successfully");

    }

    /**
     * @author Sumeet save bank proof details to db
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveProofFileToDb(String panFile, String proofType, String email) {
        InvProofFileDetailsTb ipfd = userProfileDAO.getInvProofFileDetails(email);
        if (ipfd == null) {
            //its new record
            ipfd = new InvProofFileDetailsTb();
        }
        //update the values
        ipfd.setBankFilePath(panFile);
        ipfd.setEmail(email);
        userProfileDAO.saveProofFileToDb(ipfd);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public InvProofFileDetailsTb getInvProofDetils(String email) {
        InvProofFileDetailsTb ipfd = userProfileDAO.getInvProofFileDetails(email);
        return ipfd;

    }
}
