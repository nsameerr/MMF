/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.bao.impl.UserProfileBAOImpl;
import static com.gtl.mmf.service.util.IConstants.MINUS_ONE;
import static com.gtl.mmf.service.util.IConstants.ONE;
import com.gtl.mmf.service.vo.FailedMailDetailsVO;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 11804
 */
public class RegistrationOrderMapper implements Comparator<FailedMailDetailsVO> {

    public int compare(FailedMailDetailsVO o1, FailedMailDetailsVO o2) {
        int value = 0;
        Long d1 = o1.getRegId() == null ? 0L : Long.parseLong(o1.getRegId());
        Long d2 = o2.getRegId() == null ? 0L : Long.parseLong(o2.getRegId());
        try {
            if (o1.getRegId() != null && o2.getRegId() != null) {
                if (d1 < d2) {
                    value = MINUS_ONE;
                } else {
                    value = ONE;
                }
            }
        } catch (NumberFormatException ex) { // handle your exception
            Logger.getLogger(RegistrationOrderMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

}
