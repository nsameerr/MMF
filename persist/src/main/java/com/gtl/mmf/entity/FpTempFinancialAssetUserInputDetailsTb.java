package com.gtl.mmf.entity;
// Generated Jul 31, 2016 1:04:40 AM by Hibernate Tools 3.4.0.CR1

/**
 * FpTempFinancialAssetUserInputDetailsTb generated by hbm2java
 */
public class FpTempFinancialAssetUserInputDetailsTb implements
		java.io.Serializable {

	private Integer id;
	private String customerId;
	private Integer financialAssetId;
	private String financialAssetName;
	private Integer amount;

	public FpTempFinancialAssetUserInputDetailsTb() {
	}

	public FpTempFinancialAssetUserInputDetailsTb(String customerId,
			Integer financialAssetId, String financialAssetName, Integer amount) {
		this.customerId = customerId;
		this.financialAssetId = financialAssetId;
		this.financialAssetName = financialAssetName;
		this.amount = amount;
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

	public Integer getFinancialAssetId() {
		return this.financialAssetId;
	}

	public void setFinancialAssetId(Integer financialAssetId) {
		this.financialAssetId = financialAssetId;
	}

	public String getFinancialAssetName() {
		return this.financialAssetName;
	}

	public void setFinancialAssetName(String financialAssetName) {
		this.financialAssetName = financialAssetName;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
