/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IUpdateUserBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.dao.IUpdateUserDAO;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.UserDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author 07958
 */
public class UpdateUserBAOImpl implements IUpdateUserBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.UpdateUserBAOImpl");
    @Autowired
    private IUpdateUserDAO updateUserDAO;

    /**
     * Method for updating invsetor details
     *
     * @param userDetails
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateInvestor(UserDetailsVO userDetails) {
        String statusVal = EMPTY_STRING;
        String password = null;
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Updating investor details with customer id ").append(userDetails.getUserId()).append(".");
        LOGGER.info(logMessage.toString());
        StringBuilder fullname = new StringBuilder();
        fullname.append(userDetails.getMasterApplicant().getName()).append(SPACE)
                .append(userDetails.getMasterApplicant().getMiddleName()).append(SPACE)
                .append(userDetails.getMasterApplicant().getLastName());
        userDetails.getMasterApplicant().setBeneficiaryName(fullname.toString());
        //Setting corresponding address for advisor and investor
        String cAddress = userDetails.getHomeAddress() + PIPE_SEPERATOR + userDetails.getHomeAddress_Line1()
                + PIPE_SEPERATOR + userDetails.getHomeAddress_Landmark();
        userDetails.getMasterApplicant().setAddress1(cAddress);
        //Setting permenent address for  investor
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
        if (userDetails.getTradingBank() != null) {
            userDetails.getMasterApplicant().setBankName(userDetails.getTradingBank());
        }
        if (userDetails.getTradingAccNo() != null) {
            userDetails.getMasterApplicant().setAccountNumber(userDetails.getTradingAccNo());
        }
        if (userDetails.getTradingIFSCNo() != null) {
            userDetails.getMasterApplicant().setIfcsNumber(userDetails.getTradingIFSCNo());
        }
        userDetails.getMasterApplicant().setPoliticalyExposed((userDetails.getPep() != null
                && userDetails.getPep().equalsIgnoreCase("1")));
        userDetails.getMasterApplicant().setPoliticalyRelated((userDetails.getRpep() != null
                && userDetails.getRpep().equalsIgnoreCase("1")));

        userDetails.getMasterApplicant().setPoliticalyExposedSecondHolder((userDetails.getPoliticalyExposedSecondHolder() != null
                && userDetails.getPoliticalyExposedSecondHolder().equalsIgnoreCase("1")));
        userDetails.getMasterApplicant().setPoliticalyRelatedSecondHolder((userDetails.getPoliticalyRelatedSecondHolder() != null
                && userDetails.getPoliticalyRelatedSecondHolder().equalsIgnoreCase("1")));

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
        String isd = null;
        if (userDetails.getMasterApplicant().getAddressCountry().equalsIgnoreCase(IN)) {
            isd = INDIA_ISD;
            userDetails.getMasterApplicant().setTelephoneIsd(isd);
        }
        String std = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephoneStd());
        userDetails.getMasterApplicant().setTelephoneStd(std);
        homeTelePhone = homeTelePhone.append(isd != null
                ? isd : userDetails.getMasterApplicant().getTelephoneIsd())
                .append(std)
                .append(userDetails.getMasterApplicant().getTelephone());
        userDetails.setHomeTelephone(homeTelePhone.toString());
        StringBuffer officeTelephone = new StringBuffer();
        String isd2 = null;
        if (userDetails.getMasterApplicant().getAddress2Country() != null && userDetails.getMasterApplicant().getAddress2Country().equalsIgnoreCase(IN)) {
            isd2 = INDIA_ISD;
            userDetails.getMasterApplicant().setTelephone2Isd(isd2);
            if (userDetails.getMasterApplicant().getFaxIsd() != null) {
                userDetails.getMasterApplicant().setFaxIsd(isd2);
            }
        }
        String std2 = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephone2Std());
        userDetails.getMasterApplicant().setTelephone2Std(std2);
        if (userDetails.getMasterApplicant().getFaxStd() != null) {
            userDetails.getMasterApplicant().setFaxStd(StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getFaxStd()));
        }
        officeTelephone = officeTelephone.append(isd2 != null
                ? isd2 : userDetails.getMasterApplicant().getTelephone2Isd())
                .append(userDetails.getMasterApplicant().getTelephone2Std())
                .append(userDetails.getMasterApplicant().getTelephone2());
        userDetails.setOfficeTelephone(officeTelephone.toString());
        MasterCustomerTb masterCustomer = userDetails.toMasterCustomerTb();
        masterCustomer.setIsActiveUser(Boolean.TRUE);
        statusVal = EnumStatus.fromInt(EnumStatus.ACTIVE.getValue()).toString();
        StringBuffer segmentsSelected = new StringBuffer();
        String[] arrySegments = new String[3];
        if (userDetails.getSelectedSegments() != null) {
            for (String segment : userDetails.getSelectedSegments()) {
                segmentsSelected.append(segment).append(PIPE_SEPERATOR);
            }
        }
        userDetails.getMasterApplicant().setTradingPreferenceSegment(segmentsSelected.toString());
        if (userDetails.getMasterApplicant().getNomineeExist() != null
                && userDetails.getMasterApplicant().getNomineeExist()) {

            if (userDetails.getNomineeDetails().getCountryNominee() != null && userDetails.getNomineeDetails().getCountryNominee().equalsIgnoreCase(IN)) {
                userDetails.setNoisd(INDIA_ISD);
                userDetails.setNostd(StringCaseConverter.checkStartingZero(userDetails.getNostd()));
                userDetails.setNrisd(INDIA_ISD);
                userDetails.setNrstd(StringCaseConverter.checkStartingZero(userDetails.getNrstd()));
                userDetails.setNfisd(INDIA_ISD);
                userDetails.setNfstd(StringCaseConverter.checkStartingZero(userDetails.getNfstd()));
            }
            String nomineeAddress = userDetails.getAddress1_Line1_Nominee() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Street_Nominee() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Landmark_Nominee();
            userDetails.getNomineeDetails().setAddressNominee(nomineeAddress);
            String minorAddress = userDetails.getAddress1_Line1_Minor() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Street_Minor() + PIPE_SEPERATOR
                    + userDetails.getAddress1_Landmark_Minor();
            userDetails.getNomineeDetails().setAddressNomineeMinor(minorAddress);

            String resNNum = userDetails.getNrisd() + PIPE_SEPERATOR + userDetails.getNrstd()
                    + PIPE_SEPERATOR + userDetails.getTelResidenceNominee()!= null ? userDetails.getTelResidenceNominee() : EMPTY_STRING;

            userDetails.getNomineeDetails().setTelResidenceNominee(resNNum);

            String offNNum = userDetails.getNoisd() + PIPE_SEPERATOR + userDetails.getNostd()
                    + PIPE_SEPERATOR + userDetails.getTelOfficeNominee()!= null ? userDetails.getTelOfficeNominee() : EMPTY_STRING;

            userDetails.getNomineeDetails().setTelOfficeNominee(offNNum);

            String faxNNum = userDetails.getNfisd() + PIPE_SEPERATOR + userDetails.getNfstd()
                    + PIPE_SEPERATOR + userDetails.getFaxNominee()!= null ? userDetails.getFaxNominee() : EMPTY_STRING;

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
                        + PIPE_SEPERATOR + userDetails.getTelResidenceNomineeMinor()!= null ? userDetails.getTelResidenceNomineeMinor() : EMPTY_STRING;

                userDetails.getNomineeDetails().setTelResidenceNomineeMinor(resMNum);

                String offMNum = userDetails.getMoisd() + PIPE_SEPERATOR + userDetails.getMostd()
                        + PIPE_SEPERATOR + userDetails.getTelOfficeNomineeMinor()!= null ? userDetails.getTelOfficeNomineeMinor() : EMPTY_STRING;

                userDetails.getNomineeDetails().setTelOfficeNomineeMinor(offMNum);

                String faxMNum = userDetails.getMfisd() + PIPE_SEPERATOR + userDetails.getMfstd()
                        + PIPE_SEPERATOR + userDetails.getFaxNomineeMinor()!= null ? userDetails.getFaxNomineeMinor() : EMPTY_STRING;

                userDetails.getNomineeDetails().setFaxNomineeMinor(faxMNum);
            }

            updateUserDAO.updateNominationDetails(userDetails.getNomineeDetails());
        }

        if (userDetails.isAdminUser()) {
            StringBuffer contact = new StringBuffer();
            contact.append(userDetails.getMandateisd() == null ? EMPTY_STRING : userDetails.getMandateisd())
                    .append(PIPE_SEPERATOR)
                    .append(userDetails.getMandatestd() == null ? EMPTY_STRING : userDetails.getMandatestd())
                    .append(PIPE_SEPERATOR)
                    .append(userDetails.getMandatePhone());
            userDetails.getMandateData().setCustomerPhone(contact.toString());

            updateUserDAO.updateMandateDetails(userDetails.getMandateData());
        }

        masterCustomer.setOmsLoginId(userDetails.getOmsLoginId() == null
                ? EMPTY_STRING : userDetails.getOmsLoginId());

        if (!userDetails.getPreviousStatus().equalsIgnoreCase(statusVal)) {
            masterCustomer.setInitLogin(ONE);
            updateUserDAO.updateInvestorDetails(masterCustomer);
            if (userDetails.getMasterApplicant().getStatus() == EnumStatus.ACTIVE.getValue()) {
                StringBuffer name = new StringBuffer();
                name.append(masterCustomer.getFirstName()).append(SPACE).append(masterCustomer.getMiddleName())
                        .append(SPACE).append(masterCustomer.getLastName());
                try {
                    MailUtil.getInstance().sendmail(masterCustomer.getEmail(), password, name.toString(), masterCustomer.getRegistrationId());
                } catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                }
            }
        } else {
            updateUserDAO.updateInvestorDetails(masterCustomer);
        }
    }

    /**
     * Method for updating advisor details
     *
     * @param userDetails
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateAdvisor(UserDetailsVO userDetails) {
        String statusVal = EMPTY_STRING;
        String password = null;
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Updating advisor details with customer id ").append(userDetails.getUserId()).append(DOT);
        LOGGER.info(logMessage.toString());

        //Setting corresponding address for advisor and investor
//        String cAddress = userDetails.getHomeAddress_Line1() + PIPE_SEPERATOR + userDetails.getHomeAddress_Street()
//                + PIPE_SEPERATOR + userDetails.getHomeAddress_Landmark() + PIPE_SEPERATOR
//                + userDetails.getMasterApplicant().getAddressPincode();
        String cAddress = userDetails.getHomeAddress() + PIPE_SEPERATOR + userDetails.getHomeAddress_Line1()
                + PIPE_SEPERATOR + userDetails.getHomeAddress_Landmark();
        userDetails.getMasterApplicant().setAddress1(cAddress);
        String oAddress = userDetails.getOfficeAddress() + PIPE_SEPERATOR + userDetails.getAddress2_Line1()
                + PIPE_SEPERATOR + userDetails.getAddress2_Landmark();
        userDetails.getMasterApplicant().setAddress2(oAddress);
        StringBuffer homeTelePhone = new StringBuffer();
        String std = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephoneStd());
        userDetails.getMasterApplicant().setTelephoneStd(std);
        if (userDetails.getMasterApplicant().getAddressCountry() != null) {
            homeTelePhone = homeTelePhone.append(userDetails.getMasterApplicant().getAddressCountry().equalsIgnoreCase(IN)
                    ? "91" : userDetails.getMasterApplicant().getTelephoneIsd())
                    .append(std)
                    .append(userDetails.getMasterApplicant().getTelephone());
            userDetails.setHomeTelephone(homeTelePhone.toString());
        }
        String isd = null;
        StringBuffer officeTelephone = new StringBuffer();
        if (userDetails.getMasterApplicant().getAddress2Country() != null && userDetails.getMasterApplicant().getAddress2Country().equalsIgnoreCase(IN)) {
            isd = "+91";
            String std2 = StringCaseConverter.checkStartingZero(userDetails.getMasterApplicant().getTelephone2Std());
            userDetails.getMasterApplicant().setTelephone2Std(std2);
        }
        officeTelephone = officeTelephone.append(isd != null
                ? isd : userDetails.getMasterApplicant().getTelephone2Isd())
                .append(userDetails.getMasterApplicant().getTelephone2Std())
                .append(userDetails.getMasterApplicant().getTelephone2());
        userDetails.setOfficeTelephone(officeTelephone.toString());

        MasterAdvisorTb masterAdvisor = userDetails.toMasterAdvisorTb();
        masterAdvisor.setIsActiveUser(Boolean.TRUE);
        statusVal = EnumStatus.fromInt(EnumStatus.ACTIVE.getValue()).toString();
        if (!userDetails.getPreviousStatus().equalsIgnoreCase(statusVal)) {
            masterAdvisor.setInitLogin(ONE);
            updateUserDAO.updateAdvisorDetails(masterAdvisor);
            if (userDetails.getMasterApplicant().getStatus() == EnumStatus.ACTIVE.getValue()) {
                StringBuffer name = new StringBuffer();
                name.append(masterAdvisor.getFirstName()).append(SPACE).append(masterAdvisor.getMiddleName())
                        .append(SPACE).append(masterAdvisor.getLastName());
                try {
                    MailUtil.getInstance().sendmail(masterAdvisor.getEmail(), password,
                            name.toString(), masterAdvisor.getRegistrationId());
                } catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                }
            }
        } else {
            updateUserDAO.updateAdvisorDetails(masterAdvisor);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateUserDetails(UserDetailsVO userDetails) {
        String userType = userDetails.getUserType();
        if (userType.equalsIgnoreCase(YES)) {
            updateAdvisor(userDetails);
        } else if (userType.equalsIgnoreCase(NO)) {
            updateInvestor(userDetails);
        }
    }

    public IUpdateUserDAO getUpdateUserDAO() {
        return updateUserDAO;
    }

    public void setUpdateUserDAO(IUpdateUserDAO updateUserDAO) {
        this.updateUserDAO = updateUserDAO;
    }

    /**
     * Deleting user data from temporary table after admin created user
     *
     * @param userDetails
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void deleteTempUser(UserDetailsVO userDetails) {
        updateUserDAO.deletTempUserDetails(userDetails.getMasterApplicant().getEmail());
    }
}
