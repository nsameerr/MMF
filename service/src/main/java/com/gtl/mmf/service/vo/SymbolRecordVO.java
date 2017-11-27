package com.gtl.mmf.service.vo;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 08237
 *
 */
public class SymbolRecordVO implements Serializable {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.vo.SymbolRecordVO");

    private String venue;
    private String indexPrefix;
    private String indexType;
    private String indexName;
    private String weightage;
    private String announcement;
    private String venueScripCode;
    private String scripName;
    private String series;
    private String scripDescription;
    private String instrumentType;
    private String expirationDate;
    private double strikePrice;
    private String optionType;
    private double pivotPoint;
    private int errorCode;
    private int marketLot;
    private String extScripcode3;
    private int units;
    private double faceValue;
    private String tradingUnit;
    private String priceQuoteFactor;
    private double scripMgn;
    private String tradingUnitFactor;
    private double priceNumerator;
    private double priceDenominator;
    private double generalNumerator;
    private double generalDenominator;
    //private byte[] scripNameArabic;
    //private byte[] scripDescriptionArabic;
    private String amcCode;
    private String categoryCode;
    private double minSubScrFresh;
    private double minSubscraddl;
    private String depositoryAllowed;
    private String secAllowSell;
    private String secAllowBuy;
    private double minRedValue;
    private double minRedQty;
    private String secDepMandatory;
    private int secAllowDP;
    private double tickSize;
    private double ceilingPrice;
    private double floorPrice;
    private String isin;
    private String MISC3;
    private String MISC1;
    private String MISC2;
    private String MISC4;
    private String MISC5;
    private String MISC6;
    private String MISC7;
    private String MISC8;
    private String MISC9;
    private String MISC10;
    private String MISC11;
    private String accruedinterest;

    private String ticker;
    private String tickerWithDescription;
    private String label;

    //Added for mutual fund
    private String AmcSchemeCode;
    private String RtAgentCode;
    private String RtSchemeCode;
    private String Active;
    private String Status;
    private String FolioLength;
    private String QuantityLimit;
    private String ValueLimit;
    private String SecDividend;
    private String SecModCxl;
    private String Nav;
    private String NavDate;

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getIndexPrefix() {
        return indexPrefix;
    }

    public void setIndexPrefix(String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getWeightage() {
        return weightage;
    }

    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getVenueScripCode() {
        return venueScripCode;
    }

    public void setVenueScripCode(String venueScripCode) {
        this.venueScripCode = venueScripCode;
    }

    public String getScripName() {
        return scripName;
    }

    public void setScripName(String scripName) {
        this.scripName = scripName;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getScripDescription() {
        return scripDescription;
    }

    public void setScripDescription(String scripDescription) {
        this.scripDescription = scripDescription;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public double getPivotPoint() {
        return pivotPoint;
    }

    public void setPivotPoint(double pivotPoint) {
        this.pivotPoint = pivotPoint;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getMarketLot() {
        return marketLot;
    }

    public void setMarketLot(int marketLot) {
        this.marketLot = marketLot;
    }

    public String getExtScripcode3() {
        return extScripcode3;
    }

    public void setExtScripcode3(String extScripcode3) {
        this.extScripcode3 = extScripcode3;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(double faceValue) {
        this.faceValue = faceValue;
    }

    public String getTradingUnit() {
        return tradingUnit;
    }

    public void setTradingUnit(String tradingUnit) {
        this.tradingUnit = tradingUnit;
    }

    public String getPriceQuoteFactor() {
        return priceQuoteFactor;
    }

    public void setPriceQuoteFactor(String priceQuoteFactor) {
        this.priceQuoteFactor = priceQuoteFactor;
    }

    public double getScripMgn() {
        return scripMgn;
    }

    public void setScripMgn(double scripMgn) {
        this.scripMgn = scripMgn;
    }

    public String getTradingUnitFactor() {
        return tradingUnitFactor;
    }

    public void setTradingUnitFactor(String tradingUnitFactor) {
        this.tradingUnitFactor = tradingUnitFactor;
    }

    public double getPriceNumerator() {
        return priceNumerator;
    }

    public void setPriceNumerator(double priceNumerator) {
        this.priceNumerator = priceNumerator;
    }

    public double getPriceDenominator() {
        return priceDenominator;
    }

    public void setPriceDenominator(double priceDenominator) {
        this.priceDenominator = priceDenominator;
    }

    public double getGeneralNumerator() {
        return generalNumerator;
    }

    public void setGeneralNumerator(double generalNumerator) {
        this.generalNumerator = generalNumerator;
    }

    public double getGeneralDenominator() {
        return generalDenominator;
    }

    public void setGeneralDenominator(double generalDenominator) {
        this.generalDenominator = generalDenominator;
    }

    public String getAmcCode() {
        return amcCode;
    }

    public void setAmcCode(String amcCode) {
        this.amcCode = amcCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public double getMinSubScrFresh() {
        return minSubScrFresh;
    }

    public void setMinSubScrFresh(double minSubScrFresh) {
        this.minSubScrFresh = minSubScrFresh;
    }

    public double getMinSubscraddl() {
        return minSubscraddl;
    }

    public void setMinSubscraddl(double minSubscraddl) {
        this.minSubscraddl = minSubscraddl;
    }

    public String getDepositoryAllowed() {
        return depositoryAllowed;
    }

    public void setDepositoryAllowed(String depositoryAllowed) {
        this.depositoryAllowed = depositoryAllowed;
    }

    public String getSecAllowSell() {
        return secAllowSell;
    }

    public void setSecAllowSell(String secAllowSell) {
        this.secAllowSell = secAllowSell;
    }

    public String getSecAllowBuy() {
        return secAllowBuy;
    }

    public void setSecAllowBuy(String secAllowBuy) {
        this.secAllowBuy = secAllowBuy;
    }

    public double getMinRedValue() {
        return minRedValue;
    }

    public void setMinRedValue(double minRedValue) {
        this.minRedValue = minRedValue;
    }

    public double getMinRedQty() {
        return minRedQty;
    }

    public void setMinRedQty(double minRedQty) {
        this.minRedQty = minRedQty;
    }

    public String getSecDepMandatory() {
        return secDepMandatory;
    }

    public void setSecDepMandatory(String secDepMandatory) {
        this.secDepMandatory = secDepMandatory;
    }

    public int getSecAllowDP() {
        return secAllowDP;
    }

    public void setSecAllowDP(int secAllowDP) {
        this.secAllowDP = secAllowDP;
    }

    public double getTickSize() {
        return tickSize;
    }

    public void setTickSize(double tickSize) {
        this.tickSize = tickSize;
    }

    public double getCeilingPrice() {
        return ceilingPrice;
    }

    public void setCeilingPrice(double ceilingPrice) {
        this.ceilingPrice = ceilingPrice;
    }

    public double getFloorPrice() {
        return floorPrice;
    }

    public void setFloorPrice(double floorPrice) {
        this.floorPrice = floorPrice;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getMISC3() {
        return MISC3;
    }

    public void setMISC3(String mISC3) {
        MISC3 = mISC3;
    }

    public String getMISC1() {
        return MISC1;
    }

    public void setMISC1(String mISC1) {
        MISC1 = mISC1;
    }

    public String getMISC2() {
        return MISC2;
    }

    public void setMISC2(String mISC2) {
        MISC2 = mISC2;
    }

    public String getMISC4() {
        return MISC4;
    }

    public void setMISC4(String mISC4) {
        MISC4 = mISC4;
    }

    public String getMISC5() {
        return MISC5;
    }

    public void setMISC5(String mISC5) {
        MISC5 = mISC5;
    }

    public String getMISC6() {
        return MISC6;
    }

    public void setMISC6(String mISC6) {
        MISC6 = mISC6;
    }

    public String getMISC7() {
        return MISC7;
    }

    public void setMISC7(String mISC7) {
        MISC7 = mISC7;
    }

    public String getMISC8() {
        return MISC8;
    }

    public void setMISC8(String mISC8) {
        MISC8 = mISC8;
    }

    public String getMISC9() {
        return MISC9;
    }

    public void setMISC9(String mISC9) {
        MISC9 = mISC9;
    }

    public String getMISC10() {
        return MISC10;
    }

    public void setMISC10(String mISC10) {
        MISC10 = mISC10;
    }

    public String getMISC11() {
        return MISC11;
    }

    public void setMISC11(String mISC11) {
        MISC11 = mISC11;
    }

    public String getAccruedinterest() {
        return accruedinterest;
    }

    public void setAccruedinterest(String accruedinterest) {
        this.accruedinterest = accruedinterest;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTickerWithDescription() {
        return tickerWithDescription;
    }

    public void setTickerWithDescription(String tickerWithDescription) {
        this.tickerWithDescription = tickerWithDescription;
    }

    //Added for mutual fund
    public String getAmcSchemeCode() {
        return AmcSchemeCode;
    }

    public void setAmcSchemeCode(String amcSchemeCode) {
        AmcSchemeCode = amcSchemeCode;
    }

    public String getRtAgentCode() {
        return RtAgentCode;
    }

    public void setRtAgentCode(String rtAgentCode) {
        RtAgentCode = rtAgentCode;
    }

    public String getRtSchemeCode() {
        return RtSchemeCode;
    }

    public void setRtSchemeCode(String rtSchemeCode) {
        RtSchemeCode = rtSchemeCode;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFolioLength() {
        return FolioLength;
    }

    public void setFolioLength(String folioLength) {
        FolioLength = folioLength;
    }

    public String getQuantityLimit() {
        return QuantityLimit;
    }

    public void setQuantityLimit(String quantityLimit) {
        QuantityLimit = quantityLimit;
    }

    public String getValueLimit() {
        return ValueLimit;
    }

    public void setValueLimit(String valueLimit) {
        ValueLimit = valueLimit;
    }

    public String getSecDividend() {
        return SecDividend;
    }

    public void setSecDividend(String secDividend) {
        SecDividend = secDividend;
    }

    public String getSecModCxl() {
        return SecModCxl;
    }

    public void setSecModCxl(String secModCxl) {
        SecModCxl = secModCxl;
    }

    public String getNav() {
        return Nav;
    }

    public void setNav(String nav) {
        Nav = nav;
    }

    public String getNavDate() {
        return NavDate;
    }

    public void setNavDate(String navDate) {
        NavDate = navDate;
    }

    @Override
    public boolean equals(Object symbol) {
        boolean retVal = false;

        if (symbol instanceof SymbolRecordVO) {
            SymbolRecordVO record = (SymbolRecordVO) symbol;
            retVal = (record.getVenue().equalsIgnoreCase(this.venue)
                    && record.getVenueScripCode().equalsIgnoreCase(this.venueScripCode)
                    && record.getScripName().equalsIgnoreCase(this.scripName)
                    && record.getIsin().equalsIgnoreCase(this.isin));
            if (retVal) {
                LOGGER.log(Level.INFO, "Retrun Value for Record [{0}] ISIN [{1}] VenuScripCode [{2}] is [{3}", new Object[]{record.getScripName(), record.getIsin(), record.getVenueScripCode(), retVal});
            }
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.venue != null ? this.venue.hashCode() : 0);
        hash = 97 * hash + (this.venueScripCode != null ? this.venueScripCode.hashCode() : 0);
        hash = 97 * hash + (this.scripName != null ? this.scripName.hashCode() : 0);
        hash = 97 * hash + (this.isin != null ? this.isin.hashCode() : 0);
        return hash;
    }

}
