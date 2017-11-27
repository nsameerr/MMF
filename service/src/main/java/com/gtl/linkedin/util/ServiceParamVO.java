package com.gtl.linkedin.util;

public class ServiceParamVO {
    private String companyName;
    private boolean companySearchFallback;
    private String companyId;
    private String convertedIds;


    public String getCompanyName() {
            return companyName;
    }
    public void setCompanyName(String companyName) {
            this.companyName = companyName;
    }	
    public boolean isCompanySearchFallback() {
            return companySearchFallback;
    }
    public void setCompanySearchFallback(boolean companySearchFallback) {
            this.companySearchFallback = companySearchFallback;
    }
    public String getCompanyId() {
            return companyId;
    }
    public void setCompanyId(String companyId) {
            this.companyId = companyId;
    }

    public String getConvertedIds() {
        return convertedIds;
    }

    public void setConvertedIds(String convertedIds) {
        this.convertedIds = convertedIds;
    }
    
}
