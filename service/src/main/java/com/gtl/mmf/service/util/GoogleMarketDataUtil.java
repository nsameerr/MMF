/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.linkedin.util.PropertiesLoader;
import com.gtl.mmf.service.vo.MarketDataVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionLikeType;

/**
 *
 * @author 07958
 */
public class GoogleMarketDataUtil implements IConstants {

    private GoogleMarketDataUtil() {
    }

    public static String getUrl() {
        return PropertiesLoader.getPropertiesValue(GOOGLE_API);
    }

    public static List<MarketDataVO> getMarketDataVO(String jsonResponse) {
        List<MarketDataVO> listMarketDataVO = new ArrayList<MarketDataVO>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CollectionLikeType constructCollectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, MarketDataVO.class);
            listMarketDataVO = objectMapper.readValue(jsonResponse, constructCollectionType);
        } catch (IOException ex) {
            Logger.getLogger(GoogleMarketDataUtil.class.getName()).log(Level.SEVERE,StackTraceWriter.getStackTrace(ex));
        } finally {
            return listMarketDataVO;
        }
    }
}
