package com.gtl.linkedin.util;

import static com.gtl.mmf.service.util.IConstants.PROXY_ENABLED;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class URLServiceImpl implements IURLService {

    private static final String HTTPS_PROXYHOST = "https.proxyHost";
    private static final String HTTPS_PROXYPORT = "https.proxyPort";

    public String getURL(String accessCode, ServiceParamVO serParamVO,
            ServiceTypeEnum typeEnum) {
        return PrepareURL.getInstance().prepare(accessCode, serParamVO, typeEnum);
    }

    public String getResponseJSON(String reqUrl) throws IOException {
        Boolean proxyEnabled = Boolean.parseBoolean(com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(PROXY_ENABLED));
        if (proxyEnabled) {
            System.setProperty(HTTPS_PROXYHOST, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYHOST));
            System.setProperty(HTTPS_PROXYPORT, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYPORT));
        }
        URL url = new URL(reqUrl);
        URLConnection urlConnection = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String data = null;
        StringBuilder sb = new StringBuilder();
        while ((data = br.readLine()) != null) {
            sb.append(data);
        }
        return sb.toString();
    }

    public LinkedInVO getLinkedInVO(String jsonResponse) {
        LinkedInVO linkedInVO = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            linkedInVO = objectMapper.readValue(jsonResponse, LinkedInVO.class);
        } catch (IOException ex) {
            Logger.getLogger(URLServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linkedInVO;
    }

}
