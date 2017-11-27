/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.ICreateUserBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.dao.ICreateUserDAO;
import com.gtl.mmf.dao.IUpdateUserDAO;
import com.gtl.mmf.entity.AdvisorDetailsTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
@Service
public class CreateUserBAOImpl implements ICreateUserBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.CreateUserBAOImpl");
    @Autowired
    private ICreateUserDAO iCreateUserDAO;
    @Autowired
    private IUpdateUserDAO updateUserDAO;

    /**
     * This method is used to create new investor details for saving in DB
     *
     * @param userDetails -Contains investor information
     * @return int investor id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int createNewInvestor(UserDetailsVO userDetails) {
        String password = null;
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Inserting new investor details with email id ").append(userDetails.getMasterApplicant().getEmail()).append(".");
        LOGGER.info(logMessage.toString());
        StringBuilder fullname = new StringBuilder();
        fullname.append(userDetails.getMasterApplicant().getName()).append(SPACE)
                .append(userDetails.getMasterApplicant().getMiddleName()).append(SPACE)
                .append(userDetails.getMasterApplicant().getLastName());
        userDetails.getMasterApplicant().setBeneficiaryName(fullname.toString());
        //Setting corresponding address for and investor it is a pipe(|) seperated string
        String cAddress = userDetails.getHomeAddress() + PIPE_SEPERATOR + userDetails.getHomeAddress_Line1()
                + PIPE_SEPERATOR + userDetails.getHomeAddress_Landmark();
        userDetails.getMasterApplicant().setAddress1(cAddress);

        //Setting permenent address for  investor it is a pipe(|) seperated string
        String pAddress = userDetails.getOfficeAddress() + PIPE_SEPERATOR + userDetails.getAddress2_Line1()
                + PIPE_SEPERATOR + userDetails.getAddress2_Landmark();
        userDetails.getMasterApplicant().setAddress2(pAddress);

        if (userDetails.getMasterApplicant().getTypeAlert()!= null) {
            userDetails.getMasterApplicant().setSmsFacility(userDetails.getMasterApplicant().getTypeAlert().equalsIgnoreCase("Both") || userDetails.getMasterApplicant().getTypeAlert().equalsIgnoreCase("SMS"));
        }
        if (userDetails.getSmsFacilitySecondHolder() != null) {
            userDetails.getMasterApplicant().setSmsFacilitySecondHolder(userDetails.getSmsFacilitySecondHolder().equalsIgnoreCase("YES"));
        }
        if (userDetails.getFacilityInternetTrading() != null) {
            userDetails.getMasterApplicant().setFacilityInternetTrading(userDetails.getFacilityInternetTrading().equalsIgnoreCase("YES"));
        }

        userDetails.getMasterApplicant().setPoliticalyExposed((userDetails.getPep() != null
                && userDetails.getPep().equalsIgnoreCase("YES")));
        userDetails.getMasterApplicant().setPoliticalyRelated((userDetails.getRpep() != null
                && userDetails.getRpep().equalsIgnoreCase("YES")));

        userDetails.getMasterApplicant().setPoliticalyExposedSecondHolder((userDetails.getPoliticalyExposedSecondHolder() != null
                && userDetails.getPoliticalyExposedSecondHolder().equalsIgnoreCase("YES")));
        userDetails.getMasterApplicant().setPoliticalyRelatedSecondHolder((userDetails.getPoliticalyRelatedSecondHolder() != null
                && userDetails.getPoliticalyRelatedSecondHolder().equalsIgnoreCase("YES")));

        if (userDetails.getInstruction1() != null) {
            userDetails.getMasterApplicant().setInstruction1(userDetails.getInstruction1());
        }
        if (userDetails.getInstruction2() != null) {
            userDetails.getMasterApplicant().setInstruction2(userDetails.getInstruction2());
        }
        if (userDetails.getInstruction3() != null) {
            userDetails.getMasterApplicant().setInstruction3(userDetails.getInstruction3());
        }
        if (userDetails.getInstruction4() != null) {
            userDetails.getMasterApplicant().setInstruction4(userDetails.getInstruction4());
        }
        if (userDetails.getInstruction5() != null) {
            userDetails.getMasterApplicant().setAddressForCommunication(userDetails.getInstruction5());
        }
        StringBuffer homeTelePhone = new StringBuffer();
        String std = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephoneStd());
        userDetails.getMasterApplicant().setTelephoneStd(std);
        homeTelePhone = homeTelePhone.append(userDetails.getMasterApplicant().getAddressCountry().equalsIgnoreCase(IN)
                ? INDIA_ISD : userDetails.getMasterApplicant().getTelephoneIsd())
                .append(std)
                .append(userDetails.getMasterApplicant().getTelephone());
        userDetails.setHomeTelephone(homeTelePhone.toString());
        StringBuffer officeTelephone = new StringBuffer();
        String isd = null;
        if (userDetails.getMasterApplicant().getAddressCountry() != null && userDetails.getMasterApplicant().getAddressCountry().equalsIgnoreCase(IN)) {
            isd = INDIA_ISD;
            String std2 = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephone2Std());
            userDetails.getMasterApplicant().setTelephone2Std(std2);
            if (userDetails.getMasterApplicant().getFaxIsd() != null) {
                userDetails.getMasterApplicant().setTelephone2Isd(isd);
            }
        }
        officeTelephone = officeTelephone.append(isd != null ? isd : userDetails.getMasterApplicant().getTelephone2Isd())
                .append(userDetails.getMasterApplicant().getTelephone2Std())
                .append(userDetails.getMasterApplicant().getTelephone2());
        userDetails.setOfficeTelephone(officeTelephone.toString());

        if (userDetails.getMasterApplicant().getFaxStd() != null) {
            userDetails.getMasterApplicant().setFaxStd(StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getFaxStd()));
        }
        MasterCustomerTb masterCustomer = userDetails.toMasterCustomerTb();
        masterCustomer.setCustomerId(null);

        StringBuffer segmentsSelected = new StringBuffer();
        String[] arrySegments = new String[3];
        if (userDetails.getSelectedSegments() != null) {
            for (String segment : userDetails.getSelectedSegments()) {
                segmentsSelected.append(segment).append(PIPE_SEPERATOR);
            }
        }
        userDetails.getMasterApplicant().setTradingPreferenceSegment(segmentsSelected.toString());
        //Reading the password for the newly created user from temporary registration table
        password = updateUserDAO.getPasswordForUser(masterCustomer.getEmail(), "INVESTOR");
        masterCustomer.setPassword(password);
        masterCustomer.setInitLogin(ZERO);
        masterCustomer.setIsActiveUser(Boolean.TRUE);

        /**
         * Adding allocation fund for investor for order execution.
         * Only for development and preproduction test 
         */
        String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
        if (!environment.equalsIgnoreCase(PRODUCTION)) {
            masterCustomer.setTotalFunds(100000.00);
        }
        //..............end ....

        //Inserting investor in DB
        if (userDetails.getMasterApplicant().getNomineeExist() != null
                && userDetails.getMasterApplicant().getNomineeExist()) {
            String nomineeAddress = userDetails.getAddress1_Line1_Nominee() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Street_Nominee() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Landmark_Nominee();
            userDetails.getNomineeDetails().setAddressNominee(nomineeAddress);
            String minorAddress = userDetails.getAddress1_Line1_Minor() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Street_Minor() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Landmark_Minor();
            userDetails.getNomineeDetails().setAddressNomineeMinor(minorAddress);
            if (userDetails.getNomineeDetails().getCountryNominee() != null && userDetails.getNomineeDetails().getCountryNominee().equalsIgnoreCase(IN)) {
                userDetails.setNoisd(INDIA_ISD);
                userDetails.setNostd(StringCaseConverter.checkStartingZero(userDetails.getNostd()));
                userDetails.setNrisd(INDIA_ISD);
                userDetails.setNrstd(StringCaseConverter.checkStartingZero(userDetails.getNrstd()));
                userDetails.setNfisd(INDIA_ISD);
                userDetails.setNfstd(StringCaseConverter.checkStartingZero(userDetails.getNfstd()));
            }
            String resNNum = userDetails.getNrisd() + PIPE_SEPERATOR + userDetails.getNrstd()
                    + PIPE_SEPERATOR + userDetails.getTelResidenceNominee();

            userDetails.getNomineeDetails().setTelResidenceNominee(resNNum);

            String offNNum = userDetails.getNoisd() + PIPE_SEPERATOR + userDetails.getNostd()
                    + PIPE_SEPERATOR + userDetails.getTelOfficeNominee();

            userDetails.getNomineeDetails().setTelOfficeNominee(offNNum);

            String faxNNum = userDetails.getNfisd() + PIPE_SEPERATOR + userDetails.getNfstd()
                    + PIPE_SEPERATOR + userDetails.getFaxNominee();

            userDetails.getNomineeDetails().setFaxNominee(faxNNum);

            if (userDetails.getNomineeDetails().getNomineeMinor()) {
                if (userDetails.getNomineeDetails().getCountryNomineeMinor() != null && userDetails.getNomineeDetails().getCountryNomineeMinor().equalsIgnoreCase(IN)) {
                    userDetails.setMoisd(INDIA_ISD);
                    userDetails.setMostd(StringCaseConverter.checkStartingZero(userDetails.getMostd()));
                    userDetails.setMrisd(INDIA_ISD);
                    userDetails.setMrstd(StringCaseConverter.checkStartingZero(userDetails.getMrstd()));
                    userDetails.setMfisd(INDIA_ISD);
                    userDetails.setMfstd(StringCaseConverter.checkStartingZero(userDetails.getMfstd()));
                }
                String resMNum = userDetails.getMrisd() + PIPE_SEPERATOR + userDetails.getMrstd()
                        + PIPE_SEPERATOR + userDetails.getTelResidenceNomineeMinor();

                userDetails.getNomineeDetails().setTelResidenceNomineeMinor(resMNum);

                String offMNum = userDetails.getMoisd() + PIPE_SEPERATOR + userDetails.getMostd()
                        + PIPE_SEPERATOR + userDetails.getTelOfficeNomineeMinor();

                userDetails.getNomineeDetails().setTelOfficeNomineeMinor(offMNum);

                String faxMNum = userDetails.getMfisd() + PIPE_SEPERATOR + userDetails.getMfstd()
                        + PIPE_SEPERATOR + userDetails.getFaxNomineeMinor();

                userDetails.getNomineeDetails().setFaxNomineeMinor(faxMNum);
            }

            updateUserDAO.updateNominationDetails(userDetails.getNomineeDetails());
        }

        StringBuffer contact = new StringBuffer();
        contact.append(userDetails.getMandateisd() == null ? EMPTY_STRING : userDetails.getMandateisd())
                .append(PIPE_SEPERATOR)
                .append(userDetails.getMandatestd() == null ? EMPTY_STRING : userDetails.getMandatestd())
                .append(PIPE_SEPERATOR)
                .append(userDetails.getMandatePhone());
        userDetails.getMandateData().setCustomerPhone(contact.toString());

        //Inserting investor in DB
        Integer newCustomerId = iCreateUserDAO.insertNewInvestor(masterCustomer, userDetails.getMandateData());
        StringBuffer name = new StringBuffer();
        name.append(masterCustomer.getFirstName()).append(SPACE).append(masterCustomer.getMiddleName())
                .append(SPACE).append(masterCustomer.getLastName());
        if (newCustomerId != null && userDetails.getUserStatus() == EnumStatus.ACTIVE.getValue()) {
            try {
                MailUtil.getInstance().sendmail(masterCustomer.getEmail(), password, name.toString(), masterCustomer.getRegistrationId());
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        }
        return newCustomerId;
    }

    /**
     * This method is used to create new Advisor details for saving in DB
     *
     * @param userDetails -Contains Advisor information
     * @return int advisor id
     */
    private int createNewAdvisor(UserDetailsVO userDetails) {
        String password = null;
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Inserting new advisor details with email id ").append(userDetails.getMasterApplicant().getEmail()).append(".");
        LOGGER.info(logMessage.toString());

        //Setting corresponding address for advisor 
        String cAddress = userDetails.getHomeAddress() + PIPE_SEPERATOR + userDetails.getHomeAddress_Line1()
                + PIPE_SEPERATOR + userDetails.getHomeAddress_Landmark();
        userDetails.getMasterApplicant().setAddress1(cAddress);
        String oAddress = userDetails.getOfficeAddress() + PIPE_SEPERATOR + userDetails.getAddress2_Line1()
                + PIPE_SEPERATOR + userDetails.getAddress2_Landmark();
        userDetails.getMasterApplicant().setAddress2(oAddress);
        StringBuffer homeTelePhone = new StringBuffer();
        String std = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephoneStd());
        userDetails.getMasterApplicant().setTelephoneStd(std);
        if (userDetails.getMasterApplicant().getAddressCountry()!= null) {
            homeTelePhone = homeTelePhone.append(userDetails.getMasterApplicant().getAddressCountry().equalsIgnoreCase(IN)
                    ? INDIA_ISD : userDetails.getMasterApplicant().getTelephoneIsd())
                    .append(std)
                    .append(userDetails.getMasterApplicant().getTelephone());
            userDetails.setHomeTelephone(homeTelePhone.toString());
        }

        if (!userDetails.getMasterApplicant().getPermanentAddress()) {
            StringBuffer officeTelephone = new StringBuffer();
            String isd = null;
            if (userDetails.getMasterApplicant().getAddress2Country() != null && userDetails.getMasterApplicant().getAddress2Country().equalsIgnoreCase(IN)) {
                isd = INDIA_ISD;
                String std2 = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephone2Std());
                userDetails.getMasterApplicant().setTelephone2Std(std2);
            }
            officeTelephone = officeTelephone.append(isd != null
                    ? INDIA_ISD : userDetails.getMasterApplicant().getTelephone2Isd())
                    .append(userDetails.getMasterApplicant().getTelephone2Std())
                    .append(userDetails.getMasterApplicant().getTelephone2());
            userDetails.setOfficeTelephone(officeTelephone.toString());
        }

        String userType = userDetails.getUserType();
        MasterAdvisorTb masterAdvisor = userDetails.toMasterAdvisorTb();
        masterAdvisor.setAdvisorId(null);

        //Reading the password for the newly created user from temporary registration table
        password = updateUserDAO.getPasswordForUser(masterAdvisor.getEmail(), "ADVISOR");
        masterAdvisor.setPassword(password);
        masterAdvisor.setInitLogin(ZERO);
        masterAdvisor.setIsActiveUser(Boolean.TRUE);
        Integer newAdvisorId = iCreateUserDAO.insertNewAdvisor(masterAdvisor);
        if (newAdvisorId != null) {
            AdvisorDetailsTb newAdvisorDetailsTb = this.getNewAdvisorDetailsTb(newAdvisorId, masterAdvisor.getMasterApplicantTb());
            iCreateUserDAO.insertNewAdvisorDetailsTb(newAdvisorDetailsTb);
            if (userDetails.getUserStatus() == EnumStatus.ACTIVE.getValue()) {
                StringBuffer name = new StringBuffer();
                name.append(masterAdvisor.getFirstName()).append(SPACE).append(masterAdvisor.getMiddleName())
                        .append(SPACE).append(masterAdvisor.getLastName());
                try {
                    MailUtil.getInstance().sendmail(masterAdvisor.getEmail(), password, masterAdvisor.getFirstName(), masterAdvisor.getRegistrationId());
                } catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                }
            }
        }
        // Create entry in advisor_details table
        return newAdvisorId;
    }

    /**
     * This method is used to redirect the flow of saving investor/advisor
     *
     * @param userDetails - contain investor/advisor details
     * @return int investor/advisor id
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int saveNewUserDetails(UserDetailsVO userDetails) {
        int newUserId = ZERO;

        //Redirecting investor/advisor details insertion
        String userType = userDetails.getUserType();
        if (userType.equalsIgnoreCase(YES)) {
            newUserId = createNewAdvisor(userDetails);
        } else if (userType.equalsIgnoreCase(NO)) {
            newUserId = createNewInvestor(userDetails);
        }
        return newUserId;
    }

    private AdvisorDetailsTb getNewAdvisorDetailsTb(int advisorId, MasterApplicantTb masterApplicantTb) {
        AdvisorDetailsTb advisorDetailsTb = new AdvisorDetailsTb();
        advisorDetailsTb.setAdvisorId(advisorId);
        advisorDetailsTb.setRegistrationId(masterApplicantTb.getRegistrationId());
        advisorDetailsTb.setEmail(masterApplicantTb.getEmail());
        advisorDetailsTb.setOutperformance(BigDecimal.ZERO);
        advisorDetailsTb.setOutperformanceCount(ZERO);
        advisorDetailsTb.setOutperformanceCountCompleted(ZERO);
        advisorDetailsTb.setOutperformanceCountInprogress(ZERO);
        return advisorDetailsTb;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public BankVo getbankDetails(String ifsc_code){
        BankVo bvo = new BankVo();
        IfcMicrMappingTb ifcMicrMappingTb = iCreateUserDAO.getBankdetailsOfUser(ifsc_code);
        bvo.setBank(ifcMicrMappingTb.getBank() == null ? EMPTY_STRING : ifcMicrMappingTb.getBank());
        bvo.setIfsc(ifcMicrMappingTb.getIfsc() == null ? EMPTY_STRING : ifcMicrMappingTb.getIfsc());
        bvo.setMicr(ifcMicrMappingTb.getMicr() == null ? EMPTY_STRING : ifcMicrMappingTb.getMicr());
        bvo.setAddress(ifcMicrMappingTb.getAddress() == null ? EMPTY_STRING : ifcMicrMappingTb.getAddress());
        bvo.setState(ifcMicrMappingTb.getState() == null ? EMPTY_STRING : ifcMicrMappingTb.getState());
        bvo.setCity(ifcMicrMappingTb.getCity() == null ? EMPTY_STRING : ifcMicrMappingTb.getCity());
        return bvo;
    }
}
