/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IBilldeskTransactionProcessorBAO;
import com.gtl.mmf.service.util.CheckSumGenerater;
import static com.gtl.mmf.service.util.IConstants.BANK_AUTH_INVALID;
import static com.gtl.mmf.service.util.IConstants.BILLDESK_ERROR;
import static com.gtl.mmf.service.util.IConstants.BILLDESK_SUCCESS;
import static com.gtl.mmf.service.util.IConstants.BILLDESK_WAITING;
import static com.gtl.mmf.service.util.IConstants.CHECKSUM_KEY;
import static com.gtl.mmf.service.util.IConstants.CHECKSUM_MISSMATCH;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.INVALID_INPUT;
import static com.gtl.mmf.service.util.IConstants.PIPE_SEPERATOR;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.TransactionResponseVo;
import org.jboss.logging.Logger;

/**
 * Class for handling Billdesk payment gateway processing.
 * @author trainee8
 */
public class BilldeskTransactionProcessor implements IBilldeskTransactionProcessorBAO {
private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.BilldeskTransactionProcessor");
   /**
    * This class process response from billdesk 
    * @param response - respone from billdesk payment gateway.PIPE(|) seperated message
    * @return TransactionResponseVo - object representation of response from billdesk
    */

public TransactionResponseVo processBillDeskResponse(String response) {
        LOGGER.log(Logger.Level.INFO, "In processBillDeskResponse");
        TransactionResponseVo transactionResponseVo = new TransactionResponseVo();
        String[] splitedResponse = response.split("\\|");

        transactionResponseVo.setMerchantID(splitedResponse[0]);
        transactionResponseVo.setCustomerID(splitedResponse[1]);
        transactionResponseVo.setTxnReferenceNo(splitedResponse[2]);
        transactionResponseVo.setBankReferenceNo(splitedResponse[3]);
        transactionResponseVo.setTxnAmount(splitedResponse[4]);
        transactionResponseVo.setBankID(splitedResponse[5]);
        transactionResponseVo.setBankMerchantID(splitedResponse[6]);
        transactionResponseVo.setTxnType(splitedResponse[7]);
        transactionResponseVo.setCurrencyName(splitedResponse[8]);
        transactionResponseVo.setItemCode(splitedResponse[9]);
        transactionResponseVo.setSecurityType(splitedResponse[10]);
        transactionResponseVo.setSecurityID(splitedResponse[11]);
        transactionResponseVo.setSecurityPassword(splitedResponse[12]);
        transactionResponseVo.setTxnDate(splitedResponse[13]);
        transactionResponseVo.setAuthStatus(splitedResponse[14]);
        transactionResponseVo.setSettlementType(splitedResponse[15]);
        transactionResponseVo.setErrorStatus(splitedResponse[23]);
        transactionResponseVo.setErrorDescription(splitedResponse[24]);
        transactionResponseVo.setCheckSum(splitedResponse[25]);
        String authStatus = transactionResponseVo.getAuthStatus();
        if (authStatus.equals(BILLDESK_SUCCESS)) {
            transactionResponseVo.setAuthStatusMsg("Success");
        } else if (authStatus.equals(BANK_AUTH_INVALID)) {
            transactionResponseVo.setAuthStatusMsg("Invalid Authentication at Bank ");
        } else if (authStatus.equals(BILLDESK_WAITING)) {
            transactionResponseVo.setAuthStatusMsg("BillDesk is waiting for Response from Bank");
        } else if (authStatus.equals(BILLDESK_ERROR )) {
            transactionResponseVo.setAuthStatusMsg("Error at BillDesk ");
        } else if (authStatus.equals(INVALID_INPUT)) {
            transactionResponseVo.setAuthStatusMsg("Invalid Input in the Request Message");
        }
        String responseExeptChksum = EMPTY_STRING;
        
         //reconsructing response msg without return URL for check sum calculation
        for (int i = 0; i < 25; i++) {
            if (i == 24) {
                responseExeptChksum += splitedResponse[i];
            } else {
                responseExeptChksum += splitedResponse[i] + PIPE_SEPERATOR;
            }
        }
        transactionResponseVo.setReturnUrlMsg(responseExeptChksum);
        String checkSumKey = PropertiesLoader.getPropertiesValue(CHECKSUM_KEY);
        String newCheckSum = CheckSumGenerater.generateCheckSum(responseExeptChksum, checkSumKey);
       
        //Comparing checksum returned from billdesk to new checksum calculted for verification
        if (newCheckSum.equals(splitedResponse[25])) {
            transactionResponseVo.setChkSumMissMatch(false);
        } else {
            transactionResponseVo.setChkSumMissMatch(true);
            transactionResponseVo.setAuthStatus(CHECKSUM_MISSMATCH); 
            transactionResponseVo.setAuthStatusMsg("CHECKSUM ERROR");
            
        }
        return transactionResponseVo;
    }

}
