/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import java.util.ArrayList;



/**
 *
 * @author trainee8
 */
public interface RegistrationBAO {
   public RegistrationVo createRegistrationPdfData(RegistrationVo registrationVo);
   public MandateVo createMandateFormData(MandateVo mandateVo, String regId);
}
