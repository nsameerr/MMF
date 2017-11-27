/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.RateAdvisorVO;

/**
 *
 * @author trainee3
 */
public interface IRateAdvisorBAO {

    public RateAdvisorVO getAdvisorDetails(Integer relationId);

    public Integer updateAdvisorRating(RateAdvisorVO rateAdvisorVO);

}
