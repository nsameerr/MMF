package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.AdvisorDetailsVO;;
public interface IRoboAdvisorBAO {
	public AdvisorDetailsVO getAdvisorDetails(String email);
	public int getRelationId(int investorId,int advisorId);
	public int getRelationStatus(int investorId,int advisorId);
	public String getAdvisorFullName(String email);
	public AdvisorDetailsVO getAdvisorLinkedInDetails(AdvisorDetailsVO roboAdvisorProfile);
	public int getRelationId(int investorId);
}
