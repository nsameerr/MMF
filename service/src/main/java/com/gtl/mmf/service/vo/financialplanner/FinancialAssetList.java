package com.gtl.mmf.service.vo.financialplanner;

public class FinancialAssetList {
	private int assetId;
	private String assetDescription;
	private float assetAmount;
	
	public FinancialAssetList(){
		// This is a default constructor
	}
	
	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public float getAssetAmount() {
		return assetAmount;
	}

	public void setAssetAmount(float assetAmount) {
		this.assetAmount = assetAmount;
	}

	
}
