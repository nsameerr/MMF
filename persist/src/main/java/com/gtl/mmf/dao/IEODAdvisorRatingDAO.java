/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IEODAdvisorRatingDAO {

    public List<Integer> getActiveAdvisors(Integer activeStatus);

    public List<Integer> getCustomerAdvisorMappingRelations(List<Short> activeRelationStatuses, Date currentDate);
    
    public Integer updateAdvisorRating(Integer relationId, Date currentDate);

    public Integer updateAdvisorDetais(Integer advisorId, Date currentDate);

    public List<Integer> updateAdvisorRatingList(List<Short> activeRelationStatuses, Date currentDate, Date lastRateDate);
}
