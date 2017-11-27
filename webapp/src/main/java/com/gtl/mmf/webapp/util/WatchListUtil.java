/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.webapp.util;

import com.gtl.mmf.service.util.BenchmarkUtil;
import com.gtl.mmf.service.util.MarketDataUtil;
import com.git.mds.requester.HubRequestManager;
import com.git.mds.requester.MDSHubRequestService;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author 07958
 */
public class WatchListUtil {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.webapp.util.WatchListUtil");
    private static MDSHubRequestService mdsHubRequestService = HubRequestManager.getInstance().getService();
    private Map<String, String> symbolsWatch = new HashMap<String, String>();
    private Map<String, Double> previousMarketPrice;
    private List<String> subjectsList;

    public WatchListUtil() {
        previousMarketPrice = new HashMap<String, Double>();
        subjectsList = new ArrayList<String>();
    }

//    public String getMarketData(Set<SymbolRecordVO> watches) {
//        getSubjectsList(watches);
//        for (String sub : symbolsWatch.keySet()) {
//            subjectsList.add(sub);
//        }
//        return generateJSONMarketData();
//    }

//    private String generateJSONMarketData() {
//        Map<String, Double> marketPriceMap = new HashMap<String, Double>();
//        try {
//            List<MDSMsg> responseMDSMsg = (List<MDSMsg>) mdsHubRequestService.sendRequest(subjectsList);
//            // Get market price.
//            if (responseMDSMsg != null && !responseMDSMsg.isEmpty()) {
//                for (MDSMsg mdsMsg : responseMDSMsg) {
//                    String scripCode = mdsMsg.getField(_SEND_SUBJECT).stringValue();
//                    Double marketPrice = mdsMsg.getField(LST_TRD_PRICE) != null ? mdsMsg.getField(LST_TRD_PRICE).doubleValue() : null;
//                    marketPriceMap.put(scripCode, marketPrice);
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Market data loading from MDS failed. {0}", e.getMessage());
//        }
//        // Create JSON output.
//        StringBuilder marketData = new StringBuilder("{\"Empty_Price\":\" \",");
//        for (String scripCode : marketPriceMap.keySet()) {
//            String scripName = symbolsWatch.get(scripCode);
//            Double marketPrice = marketPriceMap.get(scripCode);
//            Double previosMarketPrice = previousMarketPrice.get(scripName);
//            // For style change.
//            int priceStatus = (previosMarketPrice == null) ? 0
//                    : ((marketPrice - previosMarketPrice > 0) ? 1 : (marketPrice == previosMarketPrice) ? 0 : -1);
//            marketData.append("\"").append(scripName).append("_Price\":").append(marketPrice == null ? "\" \"" : marketPrice).append(",")
//                    .append("\"").append(scripName).append("_CMPStyle\":").append(priceStatus).append(",");
//            previousMarketPrice.put(scripName, marketPrice);
//        }
//        marketData.append("}");
//        symbolsWatch.clear();
//        subjectsList.clear();
//        return marketData.toString().replace(",}", "}");
//    }

    private void getSubjectsList(Set<SymbolRecordVO> watches) {
        for (SymbolRecordVO symbolRecordVO : watches) {
            String venue = BenchmarkUtil.getVenueCode(symbolRecordVO.getVenue());
            String scripName = symbolRecordVO.getLabel();
            String scripCode = MarketDataUtil.getScripCode(venue, symbolRecordVO.getVenueScripCode());
            symbolsWatch.put(scripCode, scripName);
        }
    }
}
