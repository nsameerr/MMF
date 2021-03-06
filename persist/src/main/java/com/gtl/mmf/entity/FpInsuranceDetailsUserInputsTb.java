package com.gtl.mmf.entity;
// Generated Jul 15, 2016 5:29:15 PM by Hibernate Tools 3.4.0.CR1

/**
 * FpInsuranceDetailsUserInputsTb generated by hbm2java
 */
public class FpInsuranceDetailsUserInputsTb implements java.io.Serializable {

	private Integer id;
	private String customerId;
	private String insuranceType;
	private String insuranceName;
	private Integer insuranceCover;
	private Integer annualPremium;
	private Integer finalYearOfPayment;

	public FpInsuranceDetailsUserInputsTb() {
	}

	public FpInsuranceDetailsUserInputsTb(String customerId,
			String insuranceType, String insuranceName, Integer insuranceCover,
			Integer annualPremium, Integer finalYearOfPayment) {
		this.customerId = customerId;
		this.insuranceType = insuranceType;
		this.insuranceName = insuranceName;
		this.insuranceCover = insuranceCover;
		this.annualPremium = annualPremium;
		this.finalYearOfPayment = finalYearOfPayment;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getInsuranceType() {
		return this.insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getInsuranceName() {
		return this.insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public Integer getInsuranceCover() {
		return this.insuranceCover;
	}

	public void setInsuranceCover(Integer insuranceCover) {
		this.insuranceCover = insuranceCover;
	}

	public Integer getAnnualPremium() {
		return this.annualPremium;
	}

	public void setAnnualPremium(Integer annualPremium) {
		this.annualPremium = annualPremium;
	}

	public Integer getFinalYearOfPayment() {
		return this.finalYearOfPayment;
	}

	public void setFinalYearOfPayment(Integer finalYearOfPayment) {
		this.finalYearOfPayment = finalYearOfPayment;
	}

}
