package com.gtl.mmf.service.vo.riskprofile;

import java.util.List;
import java.util.Date;

public class RPResponseSet {
	private List<RiskProfileResponse> RPResponse;
	private Date responseDate;
	private int responseId;
	private String customerRegistrationId;
	
	public RPResponseSet() {
		// TODO Auto-generated constructor stub
		this.responseDate = new Date();
	}

	public List<RiskProfileResponse> getRPResponse() {
		return RPResponse;
	}

	public void setRPResponse(List<RiskProfileResponse> rPResponse) {
		RPResponse = rPResponse;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public String getCustomerRegistrationId() {
		return customerRegistrationId;
	}

	public void setCustomerRegistrationId(String customerRegistrationId) {
		this.customerRegistrationId = customerRegistrationId;
	}


}
