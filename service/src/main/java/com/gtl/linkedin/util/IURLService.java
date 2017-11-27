package com.gtl.linkedin.util;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IURLService {
    String getURL(String accessCode, ServiceParamVO serParamVO,ServiceTypeEnum typeEnum);
    
    String getResponseJSON(String reqUrl) throws MalformedURLException, IOException;
    
    LinkedInVO getLinkedInVO(String jsonResponse);
}
