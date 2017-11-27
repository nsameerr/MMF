/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.git.framework.rv.RVFrameworkManager;
import com.git.mds.client.session.SessionManager;
import com.git.mds.common.MDSMsg;
import com.git.mds.requester.HubRequestManager;
import static com.gtl.mmf.service.util.IConstants.FETCH_MARKET_DATA_FROM;
import static com.gtl.mmf.service.util.IConstants.FLAIR_SESSION_NAME;
import static com.gtl.mmf.service.util.IConstants.LST_TRD_PRICE;
import static com.gtl.mmf.service.util.IConstants.MDS;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to communicate with MDS
 *
 * @author 07958
 */
public class MarketDataUtil {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.MarketDataUtil");

    private MarketDataUtil() {
    }

    /**
     * This method send request to MDS and return Response
     *
     * @param mdsRequest - request to send to MDS
     * @return list MDS response
     */
    public static List<MDSMsg> getMDSResponse(List<String> mdsRequest) {
        String fetchFrom = PropertiesLoader.getPropertiesValue(FETCH_MARKET_DATA_FROM).trim();
        MDSMsg msg = new MDSMsg();
        List<MDSMsg> responseList = new ArrayList<MDSMsg>();
        try {
            if (fetchFrom.equalsIgnoreCase(MDS)) {
                //fetching CMP from Market Data System
                boolean rvStatus = RVFrameworkManager.getInstance().getInitStatus();
                LOGGER.log(Level.SEVERE, "RVFrameworkManager init status [{0}]", rvStatus);
                if (!rvStatus) {
                    LOGGER.log(Level.INFO, "RVFrameworkManager starting..");
                    RVFrameworkManager.getInstance().start();
                } else {
                    if (HubRequestManager.getInstance().getService() != null) {
                        responseList = HubRequestManager.getInstance().getService() != null
                                ? (List<MDSMsg>) HubRequestManager.getInstance().getService().sendRequest(mdsRequest) : new ArrayList<MDSMsg>();
                    } else {
                        LOGGER.log(Level.INFO, "HubRequestManager getservice equal to null");
                        responseList = new ArrayList<MDSMsg>();
                    }

                }

            } else {
                //fetching CMP from Flair
                for (String topic : mdsRequest) {
                    msg.addField("_VIEWID", new Integer(0));
                    msg.addField(2, topic);
                }
                responseList = SessionManager.getInstance().getSession(PropertiesLoader.getPropertiesValue(FLAIR_SESSION_NAME).trim()) != null
                        ? SessionManager.getInstance().getSession(PropertiesLoader.getPropertiesValue(FLAIR_SESSION_NAME).trim()).getDataHandle().onSendRequestBatch(msg, 5)
                        : new ArrayList<MDSMsg>();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, "Exception occured when requesting to MDS");
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            return new ArrayList<MDSMsg>();
        }
        return responseList == null ? new ArrayList<MDSMsg>() : responseList;
    }

    /**
     * Method used to get Current market price for securities in the portfolio
     *
     * @param benchmark - benchmark selected
     * @param assetType -
     * @param ticker
     * @return
     */
    public static Double getCurrentMarketPrice(int benchmark, String assetType, String ticker) {
        SymbolRecordVO symbolRecordVO = SymbolListUtil.getSymbolRecordVO(assetType, ticker);
        String venue = BenchmarkUtil.getVenueCode(symbolRecordVO.getVenue());
        String venueScripCode = symbolRecordVO.getVenueScripCode();
        String scripCode = getScripCode(venue, venueScripCode);
        List<String> mdsRequest = new ArrayList<String>();
        mdsRequest.add(scripCode);
        List<MDSMsg> mdsResponseList = getMDSResponse(mdsRequest);
        MDSMsg mdsResponse = mdsResponseList.get(ZERO);
        return mdsResponse.getField(LST_TRD_PRICE) == null ? ZERO_POINT_ZERO
                : mdsResponse.getField(LST_TRD_PRICE).doubleValue();
    }

    /**
     * This method is used to create the code which is used to send to MDS for
     * getting market price.
     *
     * @param venue - venue code
     * @param venueScripCode - venue scrip code for the security
     * @return
     */
    public static String getScripCode(String venue, String venueScripCode) {
        StringBuilder scripCodeBuilder = new StringBuilder("1.");
        return scripCodeBuilder.append(venue).append(".1.").append(venueScripCode).toString();
    }

    /**
     * This method is used to create MDS message for getting Benchmark values
     *
     * @param venue venue name
     * @return MDS response message
     */
    public static MDSMsg getBenchmarkDetails(String venue) {
        //benchmarkCode format: NSE : "1.1.3.0" | BSE : "1.2.3.0"
        try {
            StringBuilder mdsCodeBuilder = new StringBuilder("1.");
            String benchmarkCode = mdsCodeBuilder.append(BenchmarkUtil.getVenueCode(venue)).append(".3.0").toString();
            List<String> mdsRequest = new ArrayList<String>();
            mdsRequest.add(benchmarkCode);
            List<MDSMsg> mdsResponseList = MarketDataUtil.getMDSResponse(mdsRequest);
            return mdsResponseList != null ? mdsResponseList.get(ZERO) : null;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            return null;
        }
    }

}
