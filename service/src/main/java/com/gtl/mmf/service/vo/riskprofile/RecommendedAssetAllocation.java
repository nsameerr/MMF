package com.gtl.mmf.service.vo.riskprofile;

public class RecommendedAssetAllocation {
	
	private int assetClassId;
	private String assetClassName;
	private float percentageAllocation;
	private String colourCode;
	
	public int getAssetClassId() {
		return assetClassId;
	}
	
	public void setAssetClassId(int assetClassId) {
		this.assetClassId = assetClassId;
	}
	
	public String getAssetClassName() {
		return assetClassName;
	}
	
	public void setAssetClassName(String assetClassName) {
		this.assetClassName = assetClassName;
	}
	
	public float getPercentageAllocation() {
		return percentageAllocation;
	}
	
	public void setPercentageAllocation(float percentageAllocation) {
		this.percentageAllocation = percentageAllocation;
	}
	
	public String getColourCode() {
		return colourCode;
	}
	
	public void setColourCode(String colourCode) {
		this.colourCode = colourCode;
	}
	

}
