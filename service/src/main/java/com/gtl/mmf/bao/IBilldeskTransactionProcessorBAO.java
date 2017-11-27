/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.TransactionResponseVo;

/**
 *
 * @author trainee8
 */
public interface IBilldeskTransactionProcessorBAO {
     public TransactionResponseVo processBillDeskResponse(String response);
}
