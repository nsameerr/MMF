/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.RegistrationBAO;
import com.gtl.mmf.dao.IUserProfileDAO;
import com.gtl.mmf.service.util.ConversionMethods;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.COLON;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.CROSS;
import static com.gtl.mmf.service.util.IConstants.DEPOSITORY;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.IN;
import static com.gtl.mmf.service.util.IConstants.MANDATE_FIELD_LENGTH;
import static com.gtl.mmf.service.util.IConstants.PIPE_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.REG_FIELD_LENGTH;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.dd_MM_yyyy;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.NumberToWord;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;
import org.springframework.util.StringUtils;
import static com.gtl.mmf.service.util.IConstants.INDIA_ISD;
import static com.gtl.mmf.service.util.IConstants.MANDATE_END_DATE;
import static com.gtl.mmf.service.util.IConstants.MMF_BROKER_NAME;
import static com.gtl.mmf.service.util.IConstants.STRINGONE;
import java.util.Arrays;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is used to create data for investor registration and creating PDF
 *
 * @author trainee8
 */
public class RegistrationBAOImpl implements RegistrationBAO {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.RegistrationBAO");
    @Autowired
    private IUserProfileDAO userProfileDAO;

    /**
     * Creating data for generating PDF
     *
     * @param registrationVo
     * @return registrationVo
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public RegistrationVo createRegistrationPdfData(RegistrationVo registrationVo) {
        //Getting length of various fields in the pdf.
        boolean othersType = false;
        String[] fieldLength = PropertiesLoader.getPropertiesValue(REG_FIELD_LENGTH).split(COMA);
        
        StringBuilder name = new StringBuilder();
        name.append(registrationVo.getName()).append(SPACE).append(registrationVo.getMiddleName())
                .append(SPACE).append(registrationVo.getLastName());
        registrationVo.setFirstName(name.toString());
        registrationVo.setBeneficiaryName(name.toString());
        
        registrationVo.setFirstNameArray(ConversionMethods.convertArray(registrationVo.getName(), fieldLength[0]));
        registrationVo.setMiddleNameArray(ConversionMethods.convertArray(registrationVo.getMiddleName(), fieldLength[0]));
        registrationVo.setLastNameArray(ConversionMethods.convertArray(registrationVo.getLastName(), fieldLength[0]));
        
        String[] fatheorSpousename = ConversionMethods.splitIntoThreePosition(registrationVo.getFatherOrSpouseName());
        String fsfirstname = fatheorSpousename[0] == null ? EMPTY_STRING : fatheorSpousename[0];
        String fssecondname = fatheorSpousename[1] == null ? EMPTY_STRING : fatheorSpousename[1];
        String fsthirdname = fatheorSpousename[2] == null ? EMPTY_STRING : fatheorSpousename[2];
        registrationVo.setFsFirstNameArray(ConversionMethods.convertArray(fsfirstname, fieldLength[0]));
        registrationVo.setFsMiddleNameArray(ConversionMethods.convertArray(fssecondname, fieldLength[0]));
        registrationVo.setFsLastNameArray(ConversionMethods.convertArray(fsthirdname, fieldLength[0]));
//        registrationVo.setFatherOrSpouseNameArray(ConversionMethods.convertArray(registrationVo.getFatherOrSpouseName(), fieldLength[1]));
        
        registrationVo.setDobArray(ConversionMethods.convertArray(DateUtil.dateToString(registrationVo.getDob(), dd_MM_yyyy), fieldLength[2]));
        registrationVo.setPanArray(ConversionMethods.convertArray(registrationVo.getPan(), fieldLength[3]));
        registrationVo.setStatusArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getStatus(),
                (HashMap<String, Integer>) LookupDataLoader.getResidentStatusMap()));
        registrationVo.setGenderArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getGender(),
                LookupDataLoader.getGenderDataMap()));
        registrationVo.setMaritalStatusArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getMaritalStatus(),
                LookupDataLoader.getMaritalstatusMap()));
        registrationVo.setIdProofArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getIdProof(),
                (HashMap<String, Integer>) LookupDataLoader.getProofOfIdentityMap()));

        //Constructing address by combining various address elemnts with pipe seperator
        StringBuffer cAddress = new StringBuffer();
        cAddress.append(registrationVo.getAddress1_Line1()).append(PIPE_SEPERATOR).append(registrationVo.getAddress1_Street())
                .append(PIPE_SEPERATOR).append(registrationVo.getAddress1_Landmark());
//        registrationVo.setCaddress(cAddress.toString().replaceAll(COMA, SPACE));
        registrationVo.setCaddress(cAddress.toString());

        registrationVo.setcAddressArray(ConversionMethods.convertArray(registrationVo.getCaddress().replaceAll("\\|", SPACE),
                fieldLength[4]));
        if (registrationVo.getCcity().equalsIgnoreCase("other")) {
            registrationVo.setCcityArray(ConversionMethods.convertArray(registrationVo.getCp_city_other(), fieldLength[5]));
        } else {
            registrationVo.setCcityArray(ConversionMethods.convertArray(registrationVo.getCcity(), fieldLength[5]));
        }

        String country = LookupDataLoader.getCountryList().get(registrationVo.getCcountry());
        // String countryCode = LookupDataLoader.getCountryList().get(country_code);
        registrationVo.setCcountryArray(ConversionMethods.convertArray(country, fieldLength[6]));
        registrationVo.setCstateArray(ConversionMethods.convertArray(registrationVo.getCstate(), fieldLength[7]));
        String statecode = userProfileDAO.getStateCode(registrationVo.getCstate());
        registrationVo.setCstateCodeArray(ConversionMethods.convertArray(statecode, fieldLength[5]));

        //registrationVo.setStateCodeArray(ConversionMethods.convertArray(statecode,fieldLength[5]));
        registrationVo.setCpinCodeArray(ConversionMethods.convertArray(registrationVo.getCpinCode(), fieldLength[8]));
        String[] nationality = new String[2];
        if ((registrationVo.getNationality() != null) && registrationVo.getNationality().equalsIgnoreCase(IN)) {
            nationality[0] = CROSS;
        } else {
            nationality[1] = CROSS;
        }
        registrationVo.setNationalityArray(nationality);
        registrationVo.setAddressproofArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getAddressProof(),
                (HashMap<String, Integer>) LookupDataLoader.getProofOfAddressMap()));
        int indexPosition = Arrays.asList(registrationVo.getAddressproofArray()).indexOf(CROSS);
        if (indexPosition > 4) {
            //Proof of adress comes under 'others' option 
            othersType = true;
            String proofToDisplay = LookupDataLoader.getProofOfAddressForOthers().get(indexPosition);
            registrationVo.setAddressProofOthers(ConversionMethods.convertArray(proofToDisplay, fieldLength[7]));
        }
        if (registrationVo.getPermenentAddress() != null && !registrationVo.getPermenentAddress()) {
            registrationVo.setcAddressArray(registrationVo.getcAddressArray());
            registrationVo.setCcountryArray(registrationVo.getCcountryArray());
            registrationVo.setCstateArray(registrationVo.getCstateArray());
            registrationVo.setCcityArray(registrationVo.getCcityArray());
            registrationVo.setCpinCodeArray(registrationVo.getCpinCodeArray());
        } else if (registrationVo.getPermenentAddress() != null) {
            StringBuffer pAddress = new StringBuffer();
            pAddress.append(registrationVo.getAddress2_Line1()).append(PIPE_SEPERATOR).append(registrationVo.getAddress2_Street())
                    .append(PIPE_SEPERATOR).append(registrationVo.getAddress2_Landmark());
//            registrationVo.setPaddress(pAddress.toString().replaceAll(COMA, SPACE));
            registrationVo.setPaddress(pAddress.toString());

            registrationVo.setPaddressArray(ConversionMethods.convertArray(registrationVo.getPaddress().replaceAll("\\|", SPACE),
                    fieldLength[4]));
            if (registrationVo.getPcity().equalsIgnoreCase("other")) {
                registrationVo.setPctyArray(ConversionMethods.convertArray(registrationVo.getPermanentCityOther(), fieldLength[5]));
            } else {
                registrationVo.setPctyArray(ConversionMethods.convertArray(registrationVo.getPcity(), fieldLength[5]));
            }
            String contry = LookupDataLoader.getCountryList().get(registrationVo.getPcountry());
            registrationVo.setPcountryArray(ConversionMethods.convertArray(contry, fieldLength[6]));
            registrationVo.setPstateArray(ConversionMethods.convertArray(registrationVo.getPstate(), fieldLength[7]));
            registrationVo.setPpinCodeArray(ConversionMethods.convertArray(registrationVo.getPpinCode(), fieldLength[8]));
            registrationVo.setAddress2_proofArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getAddress2_proof(),
                    (HashMap<String, Integer>) LookupDataLoader.getProofOfAddressMap()));
            int indexPosition2 = Arrays.asList(registrationVo.getAddress2_proofArray()).indexOf(CROSS);
            if (indexPosition2 > 4 && !othersType) {
                //Proof of adress comes under 'others' option 
                String proofToDisplay = LookupDataLoader.getProofOfAddressForOthers().get(indexPosition2);
                registrationVo.setAddressProofOthers(ConversionMethods.convertArray(proofToDisplay, fieldLength[7]));
            }
            registrationVo.setAddress2_validityArray(ConversionMethods.convertArray(DateUtil.dateToString(registrationVo.getAddress2_validity(),
                    dd_MM_yyyy), fieldLength[2]));
            // to display state code
            String pstatecode = userProfileDAO.getStateCode(registrationVo.getPstate());
            registrationVo.setPstateCodeArray(ConversionMethods.convertArray(pstatecode, fieldLength[5]));
            if (registrationVo.getPcountry().equalsIgnoreCase(IN)) {
                registrationVo.setPcountryCodeArray(ConversionMethods.convertArray(IN, fieldLength[5]));
            }
        } else {
            registrationVo.setPermenentAddress(false);
        }
        if (registrationVo.getCcountry().equalsIgnoreCase(IN)) {
            registrationVo.setRisd(StringUtils.hasText(registrationVo.getRisd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setRstd(StringCaseConverter.checkStartingZero(registrationVo.getRstd()));
            registrationVo.setHisd(StringUtils.hasText(registrationVo.getHisd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setHstd(StringCaseConverter.checkStartingZero(registrationVo.getHstd()));
            registrationVo.setFisd(StringUtils.hasText(registrationVo.getFisd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setFstd(StringCaseConverter.checkStartingZero(registrationVo.getFstd()));
            registrationVo.setMoisd(StringUtils.hasText(registrationVo.getMoisd()) ? INDIA_ISD : EMPTY_STRING);
            String fstd = registrationVo.getFisd() + registrationVo.getFstd();
            String hstd = registrationVo.getHisd() + registrationVo.getHstd();
            String rstd = registrationVo.getRisd() + registrationVo.getRstd();
            registrationVo.setFstdArray(ConversionMethods.convertArray(fstd, fieldLength[5]));
            registrationVo.setHstdArray(ConversionMethods.convertArray(hstd, fieldLength[5]));
            registrationVo.setRstdArray(ConversionMethods.convertArray(rstd, fieldLength[5]));
//            registrationVo.setFstdArray(ConversionMethods.convertArray(registrationVo.getFstd(), fieldLength[5]));
//            registrationVo.setHstdArray(ConversionMethods.convertArray(registrationVo.getHstd(), fieldLength[5]));
//            registrationVo.setRstdArray(ConversionMethods.convertArray(registrationVo.getRstd(), fieldLength[5]));
            registrationVo.setMisdArray(ConversionMethods.convertArray(INDIA_ISD, fieldLength[4]));
            registrationVo.setCcountryCodeArray(ConversionMethods.convertArray(IN, fieldLength[3]));
        }
        registrationVo.setHnumberArray(ConversionMethods.convertArray(registrationVo.getHnumber(), fieldLength[9]));
        registrationVo.setRnumberArray(ConversionMethods.convertArray(registrationVo.getRnumber(), fieldLength[9]));
        registrationVo.setFnumberArray(ConversionMethods.convertArray(registrationVo.getFnumber(), fieldLength[9]));
        registrationVo.setMobileNumberArray(ConversionMethods.convertArray(registrationVo.getMobileNumber(), fieldLength[10]));
        registrationVo.setEmailArray(ConversionMethods.convertArray(registrationVo.getEmail(), fieldLength[11]));
        registrationVo.setDobValidityArray(ConversionMethods.convertArray(DateUtil.dateToString(registrationVo.getDobValidity(),
                dd_MM_yyyy), fieldLength[2]));
        registrationVo.setOpenAccountTypeArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getOpenAccountType(),
                (HashMap<String, Integer>) LookupDataLoader.getOpenningAccountTypeMap()));
        registrationVo.setDpIdArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getDpId(),
                (HashMap<String, Integer>) LookupDataLoader.getDpidsMap()));
        registrationVo.setClientIdArray(ConversionMethods.convertArray(registrationVo.getClientId(),
                fieldLength[14]));
        registrationVo.setTradingtAccountArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getTradingtAccount(),
                (HashMap<String, Integer>) LookupDataLoader.getTradingAccountMap()));
        registrationVo.setDematAccountArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getDematAccount(),
                (HashMap<String, Integer>) LookupDataLoader.getDematAccountMap()));
        String[] smsFacility = new String[2];
        if (registrationVo.getTypeAlert() != null
                && (registrationVo.getTypeAlert().equalsIgnoreCase("Both")
                || registrationVo.getTypeAlert().equalsIgnoreCase("SMS"))) {
            smsFacility[0] = CROSS;
        } else {
            smsFacility[1] = CROSS;
        }
        registrationVo.setSmsFacilityArray(smsFacility);
        registrationVo.setIncomerangeArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getIncomerange(),
                (HashMap<String, Integer>) LookupDataLoader.getIncomerangeMap()));
        registrationVo.setNetWorthdateArray(ConversionMethods.convertArray(DateUtil.dateToString(registrationVo.getNetWorthdate(),
                dd_MM_yyyy), fieldLength[2]));
        registrationVo.setOccupationArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getOccupation(),
                (HashMap<String, Integer>) LookupDataLoader.getOccupationMap()));

        registrationVo.setBankSubTypeArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getBankSubType(),
                (HashMap<String, Integer>) LookupDataLoader.getBankSubTypeMap()));
        registrationVo.setbPincodeArray(ConversionMethods.convertArray(registrationVo.getBpincode(), fieldLength[8]));
        registrationVo.setMicrCodeArray(ConversionMethods.convertArray(registrationVo.getMicrCode(), fieldLength[13]));
        registrationVo.setIfscArray(ConversionMethods.convertArray(registrationVo.getIfsc(), fieldLength[12]));
        registrationVo.setNominee_adharArray(ConversionMethods.convertArray(registrationVo.getNominee_aadhar(), fieldLength[6]));
        registrationVo.setNomineeMinor_aadharArray(ConversionMethods.convertArray(registrationVo.getNomineeMinor_aadhar(), fieldLength[6]));
        registrationVo.setNomineeMinor_panArray(ConversionMethods.convertArray(registrationVo.getNomineeMinor_pan(), fieldLength[3]));
        String[] StandingInstructions = new String[10];
        /*if (registrationVo.getInstruction1() != null) {
         StandingInstructions[Integer.parseInt(registrationVo.getInstruction1())] = CROSS;
         }
         if (registrationVo.getInstruction2() != null) {
         StandingInstructions[Integer.parseInt(registrationVo.getInstruction2())] = CROSS;
         }
         if (registrationVo.getInstruction3() != null) {
         StandingInstructions[Integer.parseInt(registrationVo.getInstruction3())] = CROSS;
         }
         if (registrationVo.getInstruction4() != null) {
         StandingInstructions[Integer.parseInt(registrationVo.getInstruction4())] = CROSS;
         }
         if (registrationVo.getInstruction5() != null) {
         StandingInstructions[Integer.parseInt(registrationVo.getInstruction5())] = CROSS;
         }*/
        if (registrationVo.getInstruction1() != null && registrationVo.getInstruction1().equalsIgnoreCase(STRINGONE)) {
            StandingInstructions[0] = CROSS;
        } else {
            StandingInstructions[1] = CROSS;
        }
        if (registrationVo.getInstruction2() != null && registrationVo.getInstruction2().equalsIgnoreCase(STRINGONE)) {
            StandingInstructions[2] = CROSS;
        } else {
            StandingInstructions[3] = CROSS;
        }
        if (registrationVo.getInstruction3() != null && registrationVo.getInstruction3().equalsIgnoreCase(STRINGONE)) {
            StandingInstructions[4] = CROSS;
        } else {
            StandingInstructions[5] = CROSS;
        }
        if (registrationVo.getInstruction4() != null && registrationVo.getInstruction4().equalsIgnoreCase(STRINGONE)) {
            StandingInstructions[6] = CROSS;
        } else {
            StandingInstructions[7] = CROSS;
        }
        if (registrationVo.getInstruction5() != null && registrationVo.getInstruction5().equalsIgnoreCase(STRINGONE)) {
            StandingInstructions[8] = CROSS;
        } else {
            StandingInstructions[9] = CROSS;
        }
        registrationVo.setStaingInstructionsArray(StandingInstructions);
        if (registrationVo.isSecondHolderDetailsAvailable()) {
            String[] smsFacilitySecond = new String[2];
            if (registrationVo.getSmsFacilitySecondHolder() != null) {
                smsFacilitySecond[Integer.parseInt(registrationVo.getSmsFacilitySecondHolder())] = CROSS;
            }
            registrationVo.setSmsFacilitySecondHolderArray(smsFacilitySecond);
            registrationVo.setIncomeRangeSecondHolderArray(ConversionMethods.
                    convertToCheckBoxArray(registrationVo.getIncomeRangeSecondHolder(),
                            (HashMap<String, Integer>) LookupDataLoader.getIncomerangeMap()));
            registrationVo.setNetWorthDateSecondHolderArray(ConversionMethods.convertArray(DateUtil.
                    dateToString(registrationVo.getNetWorthDateSecondHolder(),
                            dd_MM_yyyy), fieldLength[2]));
            registrationVo.setOccupationSecondHolderArray(ConversionMethods.
                    convertToCheckBoxArray(registrationVo.getOccupationSecondHolder(),
                            (HashMap<String, Integer>) LookupDataLoader.getOccupationMap()));
        }
        registrationVo.setRbiApprovalDateArray(ConversionMethods.convertArray(DateUtil.
                dateToString(registrationVo.getRbiApprovalDate(),
                        dd_MM_yyyy), fieldLength[2]));
        StringBuffer segmentsSelected = new StringBuffer();
        String[] arrySegments = new String[5];
        if (registrationVo.getSelectedSegments() != null) {
            for (String segment : registrationVo.getSelectedSegments()) {
                segmentsSelected.append(segment).append(PIPE_SEPERATOR);
                if (segment.equalsIgnoreCase("Cash")) {
                    arrySegments[0] = CROSS;
                } else if (segment.equalsIgnoreCase("F&O")) {
                    arrySegments[1] = CROSS;
                } else if (segment.equalsIgnoreCase("Mutual Fund(MFSS)")) {
                    arrySegments[2] = CROSS;
                } else if (segment.equalsIgnoreCase("Currency Derivative")) {
                    arrySegments[3] = CROSS;
                } else if (segment.equalsIgnoreCase("Debt Market")) {
                    arrySegments[4] = CROSS;
                }
            }
        }
        registrationVo.setSelectedSegmentsArray(arrySegments);
        registrationVo.setTradingPreferenceSegment(segmentsSelected.toString());
        registrationVo.setDocumentaryEvedenceArray(ConversionMethods.
                convertToCheckBoxArray(registrationVo.getDocumentaryEvedence(),
                        (HashMap<String, Integer>) LookupDataLoader.getDocumentoryEvidence()));
        if (registrationVo.getDocumentaryEvedence() != null && !registrationVo.isDocumentaryEvedenceOther()) {
            registrationVo.setDocumentaryEvedenceOther(
                    registrationVo.getDocumentaryEvedence().equalsIgnoreCase("Others"));
        }
        registrationVo.setTypeElectronicContractArray(ConversionMethods.
                convertToCheckBoxArray(registrationVo.getTypeElectronicContract(),
                        (HashMap<String, Integer>) LookupDataLoader.getElectronicContractType()));
        registrationVo.setTypeAlertArray(ConversionMethods.
                convertToCheckBoxArray(registrationVo.getTypeAlert(),
                        (HashMap<String, Integer>) LookupDataLoader.getAlertType()));
        registrationVo.setRelationSipMobilenumberArray(ConversionMethods.
                convertToCheckBoxArray(registrationVo.getRelationSipMobilenumber(),
                        (HashMap<String, Integer>) LookupDataLoader.getRelationWithMobileNumber()));
        if (registrationVo.getRelationSipMobilenumber().equalsIgnoreCase("self")) {
            registrationVo.setPanMobileNumberArray(ConversionMethods.convertArray(registrationVo
                    .getPan(), fieldLength[3]));
        } else {
            registrationVo.setPanMobileNumberArray(ConversionMethods.convertArray(registrationVo
                    .getPanMobileNumber(), fieldLength[3]));
        }

        String[] facilityInternetTrading = new String[2];
        if (registrationVo.getFacilityInternetTrading() != null) {
            facilityInternetTrading[Integer.parseInt(registrationVo.getFacilityInternetTrading())] = CROSS;
        }
        registrationVo.setFacilityInternetTradingArray(facilityInternetTrading);

        StringBuffer nomineeAddress = new StringBuffer();
        nomineeAddress.append(registrationVo.getAddress1_Line1_Nominee() == null ? EMPTY_STRING : registrationVo.getAddress1_Line1_Nominee())
                .append(PIPE_SEPERATOR)
                .append(registrationVo.getAddress1_Street_Nominee() == null ? EMPTY_STRING : registrationVo.getAddress1_Street_Nominee())
                .append(PIPE_SEPERATOR)
                .append(registrationVo.getAddress1_Landmark_Nominee() == null ? EMPTY_STRING : registrationVo.getAddress1_Landmark_Nominee());
        registrationVo.setAddressNominee(nomineeAddress.toString().replaceAll("\\|", COMA));
        registrationVo.setDobNomineeArray(ConversionMethods.convertArray(DateUtil.dateToString(registrationVo.getDobNominee(),
                dd_MM_yyyy), fieldLength[2]));
        registrationVo.setDobNomineeStrng(DateUtil.dateToString(registrationVo.getDobNominee(), dd_MM_yyyy));
        registrationVo.setMobileNomineeArray(ConversionMethods.convertArray(registrationVo.getMobileNominee(),
                fieldLength[10]));
        registrationVo.setEmailNomineeArray(ConversionMethods.convertArray(registrationVo.getEmailNominee(),
                fieldLength[15]));
        registrationVo.setNominePanArray(ConversionMethods.convertArray(registrationVo.getNominePan(),
                fieldLength[3]));

        registrationVo.setDobMinorArray(ConversionMethods.convertArray(DateUtil.
                dateToString(registrationVo.getDobMinor(),
                        dd_MM_yyyy), fieldLength[2]));
        StringBuffer nomineeAddressMinor = new StringBuffer();
        nomineeAddressMinor.append(registrationVo.getAddress1_Line1_Minor() == null ? EMPTY_STRING : registrationVo.getAddress1_Line1_Minor())
                .append(PIPE_SEPERATOR)
                .append(registrationVo.getAddress1_Street_Minor() == null ? EMPTY_STRING : registrationVo.getAddress1_Street_Minor())
                .append(PIPE_SEPERATOR)
                .append(registrationVo.getAddress1_Landmark_Minor() == null ? EMPTY_STRING : registrationVo.getAddress1_Landmark_Minor());
        if (registrationVo.isNomineeExist() && (registrationVo.getCountryNominee() != null && registrationVo.getCountryNominee().equalsIgnoreCase("IN"))) {
            registrationVo.setNoisd(StringUtils.hasText(registrationVo.getNostd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setNostd(StringCaseConverter.checkStartingZero(registrationVo.getNostd()));
            registrationVo.setNrisd(StringUtils.hasText(registrationVo.getNrstd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setNrstd(StringCaseConverter.checkStartingZero(registrationVo.getNrstd()));
            registrationVo.setNfisd(StringUtils.hasText(registrationVo.getNfstd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setNfstd(StringCaseConverter.checkStartingZero(registrationVo.getNfstd()));

            StringBuffer tel_nominee = new StringBuffer();
            tel_nominee.append(registrationVo.getNoisd() != null ? registrationVo.getNoisd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getNostd() != null ? registrationVo.getNostd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getTelOfficeNominee() != null ? registrationVo.getTelOfficeNominee().length() > 8
                    ? registrationVo.getTelOfficeNominee().split(SPACE)[2] : registrationVo.getTelOfficeNominee() : EMPTY_STRING);
            registrationVo.setTelOfficeNominee(tel_nominee.toString().replaceAll("\\|", SPACE));
            registrationVo.setTelOfficeNominee1(tel_nominee.toString());

            StringBuffer tel_res_nominee = new StringBuffer();
            tel_res_nominee.append(registrationVo.getNrisd() != null ? registrationVo.getNrisd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getNrstd() != null ? registrationVo.getNrstd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getTelResidenceNominee() != null ? registrationVo.getTelResidenceNominee().length() > 8
                    ? registrationVo.getTelResidenceNominee().split(SPACE)[2] : registrationVo.getTelResidenceNominee() : EMPTY_STRING);
            registrationVo.setTelResidenceNominee(tel_res_nominee.toString().replaceAll("\\|", SPACE));
            registrationVo.setTelResidenceNominee1(tel_res_nominee.toString());

            StringBuffer fax_nominee = new StringBuffer();
            fax_nominee.append(registrationVo.getNfisd() != null ? registrationVo.getNfisd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getNfstd() != null ? registrationVo.getNfstd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getFaxNominee() != null ? registrationVo.getFaxNominee().length() > 8
                    ? registrationVo.getFaxNominee().split(SPACE)[2] : registrationVo.getFaxNominee() : EMPTY_STRING);
            registrationVo.setFaxNominee(fax_nominee.toString().replaceAll("\\|", SPACE));
            registrationVo.setFaxNominee1(fax_nominee.toString());
            registrationVo.setPinArrayNominee(ConversionMethods.convertArray(registrationVo.getPincodeNominee(), fieldLength[3]));
            if (registrationVo.getNominee_proof() != null || !registrationVo.getNominee_proof().isEmpty()) {
                if (registrationVo.getNominee_proof().equalsIgnoreCase("PAN")) {
                    registrationVo.setProofOfIdentification(registrationVo.getNominePan());
                } else if (registrationVo.getNominee_proof().equalsIgnoreCase("Aadhar")) {
                    registrationVo.setProofOfIdentification(registrationVo.getNominee_aadhar());
                }
            }

        }
        if (registrationVo.isNomineeMinor() && (registrationVo.getCountryNomineeMinor() != null && registrationVo.getCountryNomineeMinor().equalsIgnoreCase("IN"))) {
            registrationVo.setMoisd(StringUtils.hasText(registrationVo.getMostd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setMostd(StringCaseConverter.checkStartingZero(registrationVo.getMostd()));
            registrationVo.setMrisd(StringUtils.hasText(registrationVo.getMrstd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setMrstd(StringCaseConverter.checkStartingZero(registrationVo.getMrstd()));
            registrationVo.setMfisd(StringUtils.hasText(registrationVo.getMfstd()) ? INDIA_ISD : EMPTY_STRING);
            registrationVo.setMfstd(StringCaseConverter.checkStartingZero(registrationVo.getMfstd()));

            StringBuffer off_minor = new StringBuffer();
            off_minor.append(registrationVo.getMoisd() != null ? registrationVo.getMoisd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getMostd() != null ? registrationVo.getMostd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getTelOfficeNomineeMinor() != null ? registrationVo.getTelOfficeNomineeMinor().length() > 8
                    ? registrationVo.getTelOfficeNomineeMinor().split(SPACE)[2] : registrationVo.getTelOfficeNomineeMinor() : EMPTY_STRING);
            registrationVo.setTelOfficeNomineeMinor(off_minor.toString().replaceAll("\\|", SPACE));
            registrationVo.setTelOfficeNomineeMinor1(off_minor.toString());
            StringBuffer tel_res_minor = new StringBuffer();
            tel_res_minor.append(registrationVo.getMrisd() != null ? registrationVo.getMrisd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getMrstd() != null ? registrationVo.getMrstd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getTelResidenceNomineeMinor() != null ? registrationVo.getTelResidenceNomineeMinor().length() > 8
                    ? registrationVo.getTelResidenceNomineeMinor().split(SPACE)[2] : registrationVo.getTelResidenceNomineeMinor() : EMPTY_STRING);
            registrationVo.setTelResidenceNomineeMinor(tel_res_minor.toString().replaceAll("\\|", SPACE));
            registrationVo.setTelResidenceNomineeMinor1(tel_res_minor.toString());
            StringBuffer fax_minor = new StringBuffer();
            fax_minor.append(registrationVo.getMfisd() != null ? registrationVo.getMfisd() : EMPTY_STRING).append(PIPE_SEPERATOR)
                    .append(registrationVo.getMfstd() != null ? registrationVo.getMfstd() : EMPTY_STRING)
                    .append(PIPE_SEPERATOR).append(registrationVo.getFaxNomineeMinor() != null ? registrationVo.getFaxNomineeMinor().length() > 8
                    ? registrationVo.getFaxNomineeMinor().split(SPACE)[2] : registrationVo.getFaxNomineeMinor() : EMPTY_STRING);
            registrationVo.setFaxNomineeMinor(fax_minor.toString().replaceAll("\\|", SPACE));
            registrationVo.setFaxNomineeMinor1(fax_minor.toString());
            registrationVo.setPincodeNomineeMinor(registrationVo.getPincodeNomineeMinor() == null ? EMPTY_STRING : registrationVo.getPincodeNomineeMinor());
            registrationVo.setPincodeNomineeMinorArray(ConversionMethods.convertArray(registrationVo.getPincodeNomineeMinor(), fieldLength[3]));
            /* Display guardian details only nominee is a minor changes on 26-10-2016*/
            registrationVo.setNameGuardianNominee(registrationVo.getNameGuardianNominee() == null ? EMPTY_STRING : registrationVo.getNameGuardianNominee());
            registrationVo.setAddressNomineeMinor(registrationVo.getAddressNomineeMinor() == null ? EMPTY_STRING : registrationVo.getAddressNomineeMinor());

            registrationVo.setPincodeNomineeMinor(registrationVo.getPincodeNomineeMinor() == null ? EMPTY_STRING : registrationVo.getPincodeNomineeMinor());
            registrationVo.setPincodeNomineeMinorArray(ConversionMethods.convertArray(registrationVo.getPincodeNomineeMinor(), fieldLength[3]));
            /* Display guardian details only nominee is a minor changes on 26-10-2016*/
            registrationVo.setNameGuardianNominee(registrationVo.getNameGuardianNominee() == null ? EMPTY_STRING : registrationVo.getNameGuardianNominee());
            registrationVo.setAddressNomineeMinor(registrationVo.getAddressNomineeMinor() == null ? EMPTY_STRING : registrationVo.getAddressNomineeMinor());
            if (registrationVo.getNomineeMinor_proof() != null || !registrationVo.getNomineeMinor_proof().isEmpty()) {
                if (registrationVo.getNomineeMinor_proof().equalsIgnoreCase("PAN")) {
                    registrationVo.setProofOfIdentificationMinor(registrationVo.getNomineeMinor_pan());
                } else if (registrationVo.getNomineeMinor_proof().equalsIgnoreCase("Aadhar")) {
                    registrationVo.setProofOfIdentificationMinor(registrationVo.getNomineeMinor_aadhar());
                }
            }
        }

        registrationVo.setNomineCountryKey(registrationVo.getCountryNominee());
        registrationVo.setNomineMinorCountryKey(registrationVo.getCountryNomineeMinor());
        String nomineCountry = LookupDataLoader.getCountryList().get(registrationVo.getCountryNominee());
        registrationVo.setCountryNominee(nomineCountry);
        String nomineCountryMinor = LookupDataLoader.getCountryList().get(registrationVo.getCountryNomineeMinor());
        registrationVo.setCountryNomineeMinor(nomineCountryMinor);
        registrationVo.setAddressNomineeMinor(nomineeAddressMinor.toString().replaceAll("\\|", COMA));
        registrationVo.setMobileNomineeArrayMinor(ConversionMethods.convertArray(registrationVo.getMobileNomineeMinor(), fieldLength[10]));
        registrationVo.setEmailNomineeArrayMinor(ConversionMethods.convertArray(registrationVo.getEmailNomineeMinor(), fieldLength[15]));
        String[] dpname = PropertiesLoader.getPropertiesValue(DEPOSITORY).split(COLON.trim());
        registrationVo.setDepositoryName(dpname[0]);
        registrationVo.setDpIdDepository(dpname[1]);
        String[] FACTAQuestionaireArray = new String[16];
        /*
         if (registrationVo.getUsNational() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsNational())] = CROSS;
         }
         if (registrationVo.getUsResident() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsResident())] = CROSS;
         }
         if (registrationVo.getUsBorn() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsBorn())] = CROSS;
         }
         if (registrationVo.getUsAddress() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsAddress())] = CROSS;
         }
         if (registrationVo.getUsTelephone() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsTelephone())] = CROSS;
         }
         if (registrationVo.getUsStandingInstruction() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsStandingInstruction())] = CROSS;
         }
         if (registrationVo.getUsPoa() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsPoa())] = CROSS;
         }
         if (registrationVo.getUsMailAddress() != null) {
         FACTAQuestionaireArray[Integer.parseInt(registrationVo.getUsMailAddress())] = CROSS;
         }*/
        if (registrationVo.getUsNational() != null && (registrationVo.getUsNational()).equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[0] = CROSS;//Tick Yes
        } else {
            FACTAQuestionaireArray[1] = CROSS;//Tick No
        }
        if (registrationVo.getUsResident() != null && (registrationVo.getUsResident()).equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[2] = CROSS;
        } else {
            FACTAQuestionaireArray[3] = CROSS;
        }
        if (registrationVo.getUsBorn() != null && registrationVo.getUsBorn().equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[4] = CROSS;
        } else {
            FACTAQuestionaireArray[5] = CROSS;
        }
        if (registrationVo.getUsAddress() != null && registrationVo.getUsAddress().equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[6] = CROSS;
        } else {
            FACTAQuestionaireArray[7] = CROSS;
        }
        if (registrationVo.getUsTelephone() != null && registrationVo.getUsTelephone().equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[8] = CROSS;
        } else {
            FACTAQuestionaireArray[9] = CROSS;
        }
        if (registrationVo.getUsStandingInstruction() != null && registrationVo.getUsStandingInstruction().equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[10] = CROSS;
        } else {
            FACTAQuestionaireArray[11] = CROSS;
        }
        if (registrationVo.getUsPoa() != null && registrationVo.getUsPoa().equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[12] = CROSS;
        } else {
            FACTAQuestionaireArray[13] = CROSS;
        }
        if (registrationVo.getUsMailAddress() != null && registrationVo.getUsMailAddress().equalsIgnoreCase(STRINGONE)) {
            FACTAQuestionaireArray[14] = CROSS;
        } else {
            FACTAQuestionaireArray[15] = CROSS;
        }

        registrationVo.setFatcaUsStatusArray(FACTAQuestionaireArray);
        if (StringUtils.hasText(registrationVo.getIndividualTaxIdntfcnNmbr())) {
            registrationVo.setIndividualTINArray(ConversionMethods.convertArray(registrationVo.getIndividualTaxIdntfcnNmbr(), fieldLength[13]));
        }
        if (registrationVo.isSecondHolderDetailsAvailable()) {
            if (registrationVo.getSecondHldrDependentRelation().equalsIgnoreCase("self")) {
                registrationVo.setSecondHldrPanArray(ConversionMethods.convertArray(registrationVo.getPan(), fieldLength[3]));
            } else {
                registrationVo.setSecondHldrPanArray(ConversionMethods.convertArray(registrationVo.getSecondHldrPan(), fieldLength[3]));
            }
            registrationVo.setSecondHldrDependentRelationArray(ConversionMethods.
                    convertToCheckBoxArray(registrationVo.getSecondHldrDependentRelation(),
                            (HashMap<String, Integer>) LookupDataLoader.getRelationWithMobileNumber()));
        }
        registrationVo.setMmf_brokers_name(ConversionMethods.convertArray(MMF_BROKER_NAME, fieldLength[13]));
        registrationVo.setDeclarationDate(ConversionMethods.convertArray(DateUtil.dateToString(new Date(), dd_MM_yyyy), fieldLength[4]));
        String[] mothersname = ConversionMethods.splitIntoThreePosition(registrationVo.getMotherName());
        String mothersNameFirst = mothersname[0] == null ? EMPTY_STRING : mothersname[0];
        String mothersNameMid = mothersname[1] == null ? EMPTY_STRING : mothersname[1];
        String mothersNameLst = mothersname[2] == null ? EMPTY_STRING : mothersname[2];
        registrationVo.setMotherNameArray(ConversionMethods.convertArray(mothersNameFirst, fieldLength[0]));
        registrationVo.setMotherSecondNameArray(ConversionMethods.convertArray(mothersNameMid, fieldLength[0]));
        registrationVo.setMotherThirdNameArray(ConversionMethods.convertArray(mothersNameLst, fieldLength[0]));
        //Address type in admin console
        registrationVo.setAddressTypeArray(ConversionMethods.convertToCheckBoxArray(registrationVo.getAddressType(),
                LookupDataLoader.getAddressTypeList()));
        registrationVo.setPanUploadFile(registrationVo.getPanUploadFile() == null 
                ? EMPTY_STRING : registrationVo.getPanUploadFile());
        registrationVo.setCorespondenceAddressFile(registrationVo.getCorespondenceAddressFile() == null 
                ? EMPTY_STRING : registrationVo.getCorespondenceAddressFile() );
        registrationVo.setPermenentAddressFile(registrationVo.getPermenentAddressFile() == null 
                ? EMPTY_STRING : registrationVo.getPermenentAddressFile());
        return registrationVo;
    }

    /**
     * *
     * Creating data for Mandate form PDF generation
     *
     * @param mandateVo
     * @param regId
     * @return mandateVo
     */
    public MandateVo createMandateFormData(MandateVo mandateVo, String regId) {
        String[] fieldLength = PropertiesLoader.getPropertiesValue(MANDATE_FIELD_LENGTH).split(COMA);
        String[] utility = fieldLength[5].split(COLON.trim());
        String[] sponsercode = fieldLength[6].split(COLON.trim());
        mandateVo.setUtilityCode(utility[1]);
        mandateVo.setCustomerBankCode(sponsercode[1]);

        mandateVo.setUmrnArray(ConversionMethods.convertArray(mandateVo.getUmrn(), fieldLength[0]));
        mandateVo.setCustomerAccountNumberArray(ConversionMethods.convertArray(mandateVo.getCustomerAccountNumber(), fieldLength[1]));
        mandateVo.setFromDateArray(ConversionMethods.convertArray(DateUtil.dateToString(mandateVo.getFromDate(), dd_MM_yyyy), fieldLength[4]));
        /* if (mandateVo.isSelectToDate()) {
         mandateVo.setToDateArray(ConversionMethods.convertArray(DateUtil.dateToString(mandateVo.getToDate(), dd_MM_yyyy), fieldLength[4]));
         mandateVo.setCancelDate(EMPTY_STRING);
         } else {
         mandateVo.setCancelDate(CROSS);
         mandateVo.setToDateArray(null);
         }*/
        mandateVo.setDate(new Date());
        mandateVo.setDateArray(ConversionMethods.convertArray(DateUtil.dateToString(mandateVo.getDate(), dd_MM_yyyy), fieldLength[4]));
        mandateVo.setCustomerDebitAccountArray(ConversionMethods.convertToCheckBoxArray(mandateVo.getCustomerDebitAccount(),
                (HashMap<String, Integer>) LookupDataLoader.getDebitAccountMap()));
        mandateVo.setCustomerDebitTypeArray(ConversionMethods.convertToCheckBoxArray(mandateVo.getCustomerDebitType(),
                (HashMap<String, Integer>) LookupDataLoader.getDebitAccountTypemap()));
        mandateVo.setFrequencyArray(ConversionMethods.convertToCheckBoxArray(mandateVo.getFrequency(),
                (HashMap<String, Integer>) LookupDataLoader.getFrequencyMap()));
        mandateVo.setActionArray(ConversionMethods.convertToCheckBoxArray(mandateVo.getAction(),
                (HashMap<String, Integer>) LookupDataLoader.getMandateActionMap()));
        mandateVo.setIfscArray(ConversionMethods.convertArray(mandateVo.getIfscNumber(), fieldLength[2]));
        mandateVo.setMicrArray(ConversionMethods.convertArray(mandateVo.getMicrNumber(), fieldLength[3]));
        mandateVo.setReg_id(regId);
        if (StringUtils.hasText(mandateVo.getAmount())) {
            mandateVo.setAmountInWords(NumberToWord.convertToWord(Double.parseDouble(mandateVo.getAmount())));
        }
        /*To display end date 31-12-2099 n mandate form*/
        String date = PropertiesLoader.getPropertiesValue(MANDATE_END_DATE);
        mandateVo.setToDateArray(ConversionMethods.convertArray(date, fieldLength[4]));
        return mandateVo;
    }

}
