package com.gtl.linkedin.util;

import static com.gtl.linkedin.util.ILinkedinConstants.API_LINKEDIN_COM_V1_PEOPLE;
import static com.gtl.linkedin.util.ILinkedinConstants.AUTH2_ACCESS_TOKEN;
import static com.gtl.linkedin.util.ILinkedinConstants.FORMAT;
import static com.gtl.linkedin.util.ILinkedinConstants.POSITIONS;
import static com.gtl.linkedin.util.ILinkedinConstants.RESPONSE_FORMAT;
import static com.gtl.linkedin.util.ServiceTypeEnum.CONNECTIONS;
import static com.gtl.linkedin.util.ServiceTypeEnum.FULL_PROFILE;
import static com.gtl.mmf.service.util.IConstants.CONTEXT_ROOT;
import com.gtl.mmf.service.util.LookupDataLoader;

public class PrepareURL implements ILinkedinConstants {

    public static PrepareURL prepareURL = null;

    private PrepareURL() {
    }

    public static PrepareURL getInstance() {
        if (prepareURL == null) {
            prepareURL = new PrepareURL();
        }
        return prepareURL;
    }

    public String prepare(String accessCode, ServiceParamVO serParamVO,
            ServiceTypeEnum typeEnum) {
        StringBuilder url = new StringBuilder();
        switch (typeEnum) {
            case ACCESS_TOKEN:
                url.append(PropertiesLoader.getPropertiesValue(LINKEDIN_UASAUTH2))
                        .append(PropertiesLoader.getPropertiesValue(ACCSS_TOKEN_PROP))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(REDIRECT_URI_STG))
                        .append(PropertiesLoader.getPropertiesValue(REDIRECT_URI_PROP)
                                .replaceAll(CONTEXT_ROOT, LookupDataLoader.getContextRoot()))
                        .append(PropertiesLoader.getPropertiesValue(CLIENT_ID_STG))
                        .append(PropertiesLoader.getPropertiesValue(API_KEY_PROP))
                        .append(PropertiesLoader.getPropertiesValue(CLIENT_SCRT_KEY_STG))
                        .append(PropertiesLoader.getPropertiesValue(SECRET_KEY));
                break;
            case BASIC_PROFILE:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(TILDE_AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case FULL_PROFILE:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(ID_FIRST_NAME_LAST_NAME))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case SEARCH_PEOPLE_DIST:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1))
                        .append(PropertiesLoader.getPropertiesValue(SEARCH_PEOPLE_VALUE_ONE))
                        .append(serParamVO.getConvertedIds())
                        .append(PropertiesLoader.getPropertiesValue(SEARCH_PEOPLE_VALUE_TWO))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode).append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case EMAILID:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(EMAIL_ADDRESS))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case COMPANY_SEARCH:
                if (serParamVO.isCompanySearchFallback() && accessCode.isEmpty()) {
                    url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1))
                            .append(PropertiesLoader.getPropertiesValue(COMPANY_SEARCH))
                            .append(serParamVO.getCompanyName())
                            .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN_AMPRESENT))
                            .append(PropertiesLoader.getPropertiesValue(PUBLIC_ACESS_TOKEN))
                            .append(FORMAT)
                            .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                } else {
                    url.append(PropertiesLoader.getPropertiesValue(LINKEDIN_COM_TA))
                            .append(serParamVO.getCompanyName());
                }
                break;
            case COMPANY_DETAILS:
                if (accessCode.isEmpty()) {
                    url.append(PropertiesLoader.getPropertiesValue(LINKEDIN_COM_V1_COMPANIES))
                            .append(serParamVO.getCompanyId())
                            .append(PropertiesLoader.getPropertiesValue(ID_COMPANY_TYPE))
                            .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                            .append(PropertiesLoader.getPropertiesValue(PUBLIC_ACESS_TOKEN))
                            .append(PropertiesLoader.getPropertiesValue(FORMAT))
                            .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                } else {
                    url.append(PropertiesLoader.getPropertiesValue(LINKEDIN_COM_V1_COMPANIES))
                            .append(serParamVO.getCompanyId())
                            .append(PropertiesLoader.getPropertiesValue(ID_COMPANY_TYPE))
                            .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                            .append(accessCode)
                            .append(PropertiesLoader.getPropertiesValue(FORMAT))
                            .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                }
                break;
            case CONNECTIONS:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(CONNECTIONS_STG))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case CONNECTIONS_LEVEL:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(CONNECTION_ID_DISTANCE))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case CONTACT_INFO:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(TILDE_AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case NETWORK_UPDATES:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(NW_UPDATES))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case GROUP_DISCUSSION:
                url.append(PropertiesLoader.getPropertiesValue(LINKEDIN_COM_V1_GROUPS))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case MESSAGES:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(MAILBOX))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case POSITIONS:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(PropertiesLoader.getPropertiesValue(POSITIONS))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            case COMPANY_DETAILS_FULL:
                if (accessCode.isEmpty()) {
                    url.append(PropertiesLoader.getPropertiesValue(LINKEDIN_COM_V1_COMPANIES))
                            .append(serParamVO.getCompanyId())
                            .append(PropertiesLoader.getPropertiesValue(ID_COMPANY_TYPE_WEBSITE_URL))
                            .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                            .append(PropertiesLoader.getPropertiesValue(PUBLIC_ACESS_TOKEN))
                            .append(PropertiesLoader.getPropertiesValue(FORMAT))
                            .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                } else {
                    url.append(PropertiesLoader.getPropertiesValue(LINKEDIN_COM_V1_COMPANIES))
                            .append(serParamVO.getCompanyId())
                            .append(PropertiesLoader.getPropertiesValue(ID_COMPANY_TYPE_WEBSITE_URL))
                            .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                            .append(accessCode)
                            .append(PropertiesLoader.getPropertiesValue(FORMAT))
                            .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                }
                break;
            case SHARED_CONNECTIONS:
                url.append(PropertiesLoader.getPropertiesValue(API_LINKEDIN_COM_V1_PEOPLE))
                        .append(serParamVO.getConvertedIds())
                        .append(PropertiesLoader.getPropertiesValue(RELATION_TO_VIEWER))
                        .append(PropertiesLoader.getPropertiesValue(AUTH2_ACCESS_TOKEN))
                        .append(accessCode)                        
                        .append(PropertiesLoader.getPropertiesValue(FORMAT))
                        .append(PropertiesLoader.getPropertiesValue(RESPONSE_FORMAT));
                break;
            default:
                throw new UnsupportedOperationException(UNSUPPORTED_ACCESS_CODE);
        }
        return url.toString();
    }
}
