package com.gtl.mmf.common;

public enum EnumBuySellStatus {
	
	BUY("B"), 
	SELL("S"), 
	NOT_APPLICABLE("N");
	
	private final String statusCode;

	private EnumBuySellStatus(String status) {
		this.statusCode = status;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
}
