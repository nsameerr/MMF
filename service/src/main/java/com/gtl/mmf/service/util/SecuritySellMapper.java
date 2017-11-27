/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.MINUS_ONE;
import static com.gtl.mmf.service.util.IConstants.ONE;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import java.util.Comparator;

public class SecuritySellMapper implements Comparator<PortfolioSecurityVO> {

    public int compare(PortfolioSecurityVO psvo1, PortfolioSecurityVO psvo2) {
        if (psvo1.getBuysellStatusValue() < psvo2.getBuysellStatusValue()) {
            return ONE;
        } else {
            return MINUS_ONE;
        }
    }
}
