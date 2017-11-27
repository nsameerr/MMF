package com.gtl.mmf.dao;

import java.util.List;

/**
 * @author bhagyashree.chavan
 * 
 */
public interface IRoboAdvisorDAO {
	public List<Object> getAdvisorDetails(String advisorEmail);
	public int getRelationId(int investorId,int advisorId);	
	public int getRelationStatus(int investorId,int advisorId);
	public List<Object> getAdvisorLinkedInDetails(String advisorEmail);
	public int getRelationId(int investorId) ;
}
