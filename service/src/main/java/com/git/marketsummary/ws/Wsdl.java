
package com.git.marketsummary.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "wsdl", targetNamespace = "http://ws.marketsummary.git.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Wsdl {


    /**
     * 
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.BannedScrips>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBannedScrips", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBannedScrips")
    @ResponseWrapper(localName = "getBannedScripsResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBannedScripsResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getBannedScripsRequest", output = "http://ws.marketsummary.git.com/wsdl/getBannedScripsResponse")
    public List<BannedScrips> getBannedScrips();

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.BulkDeal>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBulkDeal", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBulkDeal")
    @ResponseWrapper(localName = "getBulkDealResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBulkDealResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getBulkDealRequest", output = "http://ws.marketsummary.git.com/wsdl/getBulkDealResponse")
    public List<BulkDeal> getBulkDeal(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param frmDate
     * @param venueCode
     * @param security
     * @param toDate
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.FiiTrading>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getFIITrading", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetFIITrading")
    @ResponseWrapper(localName = "getFIITradingResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetFIITradingResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getFIITradingRequest", output = "http://ws.marketsummary.git.com/wsdl/getFIITradingResponse")
    public List<FiiTrading> getFIITrading(
        @WebParam(name = "venueCode", targetNamespace = "")
        String venueCode,
        @WebParam(name = "frmDate", targetNamespace = "")
        String frmDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param frmDate
     * @param venueCode
     * @param security
     * @param toDate
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.TotalTurnOver>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTotalTurnOver", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetTotalTurnOver")
    @ResponseWrapper(localName = "getTotalTurnOverResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetTotalTurnOverResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getTotalTurnOverRequest", output = "http://ws.marketsummary.git.com/wsdl/getTotalTurnOverResponse")
    public List<TotalTurnOver> getTotalTurnOver(
        @WebParam(name = "venueCode", targetNamespace = "")
        String venueCode,
        @WebParam(name = "frmDate", targetNamespace = "")
        String frmDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param frmDate
     * @param venueCode
     * @param security
     * @param toDate
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.AdvancesDeclines>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAdvancesDeclines", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetAdvancesDeclines")
    @ResponseWrapper(localName = "getAdvancesDeclinesResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetAdvancesDeclinesResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getAdvancesDeclinesRequest", output = "http://ws.marketsummary.git.com/wsdl/getAdvancesDeclinesResponse")
    public List<AdvancesDeclines> getAdvancesDeclines(
        @WebParam(name = "venueCode", targetNamespace = "")
        String venueCode,
        @WebParam(name = "frmDate", targetNamespace = "")
        String frmDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.IndexMovement>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getIndexMovement", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetIndexMovement")
    @ResponseWrapper(localName = "getIndexMovementResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetIndexMovementResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getIndexMovementRequest", output = "http://ws.marketsummary.git.com/wsdl/getIndexMovementResponse")
    public List<IndexMovement> getIndexMovement(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.HolidayCalender>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getHolidayCalender", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetHolidayCalender")
    @ResponseWrapper(localName = "getHolidayCalenderResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetHolidayCalenderResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getHolidayCalenderRequest", output = "http://ws.marketsummary.git.com/wsdl/getHolidayCalenderResponse")
    public List<HolidayCalender> getHolidayCalender(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param corpDateType
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.CorpAction>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCorpAction", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCorpAction")
    @ResponseWrapper(localName = "getCorpActionResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCorpActionResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getCorpActionRequest", output = "http://ws.marketsummary.git.com/wsdl/getCorpActionResponse")
    public List<CorpAction> getCorpAction(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security,
        @WebParam(name = "corpDateType", targetNamespace = "")
        String corpDateType);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.CorperateAnnouncements>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCorperateAnnouncements", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCorperateAnnouncements")
    @ResponseWrapper(localName = "getCorperateAnnouncementsResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCorperateAnnouncementsResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getCorperateAnnouncementsRequest", output = "http://ws.marketsummary.git.com/wsdl/getCorperateAnnouncementsResponse")
    public List<CorperateAnnouncements> getCorperateAnnouncements(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.BoardMeetings>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBoardMeetings", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBoardMeetings")
    @ResponseWrapper(localName = "getBoardMeetingsResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBoardMeetingsResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getBoardMeetingsRequest", output = "http://ws.marketsummary.git.com/wsdl/getBoardMeetingsResponse")
    public List<BoardMeetings> getBoardMeetings(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.CategoryTurnOver>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCategoryTurnOver", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCategoryTurnOver")
    @ResponseWrapper(localName = "getCategoryTurnOverResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCategoryTurnOverResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getCategoryTurnOverRequest", output = "http://ws.marketsummary.git.com/wsdl/getCategoryTurnOverResponse")
    public List<CategoryTurnOver> getCategoryTurnOver(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param futOpt
     * @param security
     * @param toDate
     * @param venue
     * @param noOfSecurities
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.TopNSecurities>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTopNSecurities", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetTopNSecurities")
    @ResponseWrapper(localName = "getTopNSecuritiesResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetTopNSecuritiesResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getTopNSecuritiesRequest", output = "http://ws.marketsummary.git.com/wsdl/getTopNSecuritiesResponse")
    public List<TopNSecurities> getTopNSecurities(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security,
        @WebParam(name = "noOfSecurities", targetNamespace = "")
        String noOfSecurities,
        @WebParam(name = "futOpt", targetNamespace = "")
        String futOpt);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.MutualFund>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMutualFund", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetMutualFund")
    @ResponseWrapper(localName = "getMutualFundResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetMutualFundResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getMutualFundRequest", output = "http://ws.marketsummary.git.com/wsdl/getMutualFundResponse")
    public List<MutualFund> getMutualFund(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param toDate
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.WorldMarket>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getWorldMarket", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetWorldMarket")
    @ResponseWrapper(localName = "getWorldMarketResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetWorldMarketResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getWorldMarketRequest", output = "http://ws.marketsummary.git.com/wsdl/getWorldMarketResponse")
    public List<WorldMarket> getWorldMarket(
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns com.git.marketsummary.ws.CorpConsolidate
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCorpConsolidate", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCorpConsolidate")
    @ResponseWrapper(localName = "getCorpConsolidateResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetCorpConsolidateResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getCorpConsolidateRequest", output = "http://ws.marketsummary.git.com/wsdl/getCorpConsolidateResponse")
    public CorpConsolidate getCorpConsolidate(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.ScripEOD>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getScripEOD", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetScripEOD")
    @ResponseWrapper(localName = "getScripEODResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetScripEODResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getScripEODRequest", output = "http://ws.marketsummary.git.com/wsdl/getScripEODResponse")
    public List<ScripEOD> getScripEOD(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param symbol
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.SupportResistence>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSupportResistence", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetSupportResistence")
    @ResponseWrapper(localName = "getSupportResistenceResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetSupportResistenceResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getSupportResistenceRequest", output = "http://ws.marketsummary.git.com/wsdl/getSupportResistenceResponse")
    public List<SupportResistence> getSupportResistence(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "symbol", targetNamespace = "")
        String symbol);

    /**
     * 
     * @param year
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.MarketCapitalization>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMarketCapitalization", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetMarketCapitalization")
    @ResponseWrapper(localName = "getMarketCapitalizationResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetMarketCapitalizationResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getMarketCapitalizationRequest", output = "http://ws.marketsummary.git.com/wsdl/getMarketCapitalizationResponse")
    public List<MarketCapitalization> getMarketCapitalization(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "year", targetNamespace = "")
        String year);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns com.git.marketsummary.ws.CommonObject
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getWsAnalysis", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetWsAnalysis")
    @ResponseWrapper(localName = "getWsAnalysisResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetWsAnalysisResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getWsAnalysisRequest", output = "http://ws.marketsummary.git.com/wsdl/getWsAnalysisResponse")
    public CommonObject getWsAnalysis(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.BannedScrips>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getNRIBannedScrips", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetNRIBannedScrips")
    @ResponseWrapper(localName = "getNRIBannedScripsResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetNRIBannedScripsResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getNRIBannedScripsRequest", output = "http://ws.marketsummary.git.com/wsdl/getNRIBannedScripsResponse")
    public List<BannedScrips> getNRIBannedScrips(
        @WebParam(name = "venue", targetNamespace = "")
        String venue);

    /**
     * 
     * @param fromDate
     * @param security
     * @param toDate
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.BlockDeal>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getBlockDeal", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBlockDeal")
    @ResponseWrapper(localName = "getBlockDealResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetBlockDealResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getBlockDealRequest", output = "http://ws.marketsummary.git.com/wsdl/getBlockDealResponse")
    public List<BlockDeal> getBlockDeal(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate,
        @WebParam(name = "security", targetNamespace = "")
        String security);

    /**
     * 
     * @param orderingusercode
     * @param enteringusercode
     * @param venuecode
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "generateTradeData", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GenerateTradeData")
    @ResponseWrapper(localName = "generateTradeDataResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GenerateTradeDataResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/generateTradeDataRequest", output = "http://ws.marketsummary.git.com/wsdl/generateTradeDataResponse")
    public List<String> generateTradeData(
        @WebParam(name = "enteringusercode", targetNamespace = "")
        String enteringusercode,
        @WebParam(name = "orderingusercode", targetNamespace = "")
        String orderingusercode,
        @WebParam(name = "venuecode", targetNamespace = "")
        String venuecode);

    /**
     * 
     * @param fromDate
     * @param interval
     * @param scripCode
     * @param toDate
     * @param allOrPeriod
     * @param venuecode
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.SecurityMovements>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSecuritymovements", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetSecuritymovements")
    @ResponseWrapper(localName = "getSecuritymovementsResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetSecuritymovementsResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getSecuritymovementsRequest", output = "http://ws.marketsummary.git.com/wsdl/getSecuritymovementsResponse")
    public List<SecurityMovements> getSecuritymovements(
        @WebParam(name = "venuecode", targetNamespace = "")
        String venuecode,
        @WebParam(name = "scripCode", targetNamespace = "")
        String scripCode,
        @WebParam(name = "interval", targetNamespace = "")
        int interval,
        @WebParam(name = "allOrPeriod", targetNamespace = "")
        int allOrPeriod,
        @WebParam(name = "fromDate", targetNamespace = "")
        String fromDate,
        @WebParam(name = "toDate", targetNamespace = "")
        String toDate);

    /**
     * 
     * @param condition
     * @param venue
     * @return
     *     returns java.util.List<com.git.marketsummary.ws.DynamicWatch>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getDynamicWatch", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetDynamicWatch")
    @ResponseWrapper(localName = "getDynamicWatchResponse", targetNamespace = "http://ws.marketsummary.git.com/", className = "com.git.marketsummary.ws.GetDynamicWatchResponse")
    @Action(input = "http://ws.marketsummary.git.com/wsdl/getDynamicWatchRequest", output = "http://ws.marketsummary.git.com/wsdl/getDynamicWatchResponse")
    public List<DynamicWatch> getDynamicWatch(
        @WebParam(name = "venue", targetNamespace = "")
        String venue,
        @WebParam(name = "condition", targetNamespace = "")
        int condition);

}
