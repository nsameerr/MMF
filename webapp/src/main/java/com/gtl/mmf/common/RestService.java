/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gtl.mmf.bao.IFpMainCalculationBAO;
import com.gtl.mmf.bao.IPortfolioSearchBAO;
import com.gtl.mmf.bao.IRiskProfileBAO;
import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.controller.CreateUserBean;
import com.gtl.mmf.controller.UserRegistrationBean2;
import com.gtl.mmf.controller.UserSessionBean;
import com.gtl.mmf.entity.CitiesTb;
import com.gtl.mmf.entity.InvProofFileDetailsTb;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.IdGenarator;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.AdvisorRegistrationVo;
import com.gtl.mmf.service.vo.CompleteTempInvVo;
import com.gtl.mmf.service.vo.InvestorTempRegistrationVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.TempAdvVo;
import com.gtl.mmf.service.vo.TempInvNextPageVo;
import com.gtl.mmf.service.vo.TempInvVo;
import com.gtl.mmf.webapp.util.FileUploadUtil;
import com.lowagie.text.pdf.codec.Base64;

@Path("mmfresource")
public class RestService implements IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.common.RestService");
    private static final String TEMP_INVESTOR_ONE = "TempInvVo";
    private static final String TEMP_INVESTOR_TWO = "TempInvNextPageVo";
    private InvestorTempRegistrationVo tempRegistrationVo;
    private static final String TEMP_ADVISOR = "TempAdvVo";
    private static final String EMAIL_ID = "emailId";
    private static final String COMPLETE_INVESTOR = "CompleteTempInvVo";
    private UserRegistrationBean2 userRegistrationBean2;

    @Context
    private HttpServletRequest httRequest;
    @Context
    private HttpServletResponse httpresponse;
    @Context
    private UriInfo context;
    ServletContext servletContext;
    private static final String allowedCrossDomain = LookupDataLoader.getAllowedOrigin();

    /**
     * Advisor Registration 5 min auto save call.
     *
     * @param abstractJson
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/autoSaveService")
    public String autoSaveService(String abstractJson) {
        LOGGER.log(Level.INFO, "JSON ;- {0}", abstractJson);
        modifyResponse();
        HttpSession session = httRequest.getSession();
        TempAdvVo advisorRegistrationVo = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            advisorRegistrationVo = objectMapper.readValue(abstractJson, TempAdvVo.class);
            IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
            session.setAttribute(TEMP_ADVISOR, advisorRegistrationVo);
            userProfileBAO.saveAdvTemp(advisorRegistrationVo);
        } catch (IOException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Investor Registration page1 5min auto save call.
     *
     * @param abstractJson
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/autoSaveInvService")
    public String autoSaveInvService(String abstractJson) {
        LOGGER.log(Level.INFO, "JSON ;- {0}", abstractJson);
        modifyResponse();
        HttpSession session = httRequest.getSession();
        tempRegistrationVo = new InvestorTempRegistrationVo();
        if ((TempInvNextPageVo) session.getAttribute(TEMP_INVESTOR_TWO) != null) {
            tempRegistrationVo.setInvestorRegPage2((TempInvNextPageVo) session.getAttribute(TEMP_INVESTOR_TWO));
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TempInvVo investorRegPage1 = objectMapper.readValue(abstractJson, TempInvVo.class);
            tempRegistrationVo.setInvestorRegPage1(investorRegPage1);
            IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
            session.setAttribute(TEMP_INVESTOR_ONE, investorRegPage1);
            userProfileBAO.saveInvTemp(tempRegistrationVo);
        } catch (IOException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Investor Registration page2 5min auto save call.
     *
     * @param abstractJson
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/autoSaveNextPageService")
    public String autoSaveNextPageService(String abstractJson) {
        LOGGER.log(Level.INFO, "JSON ;- {0}", abstractJson);
        modifyResponse();
        TempInvNextPageVo investorRegPage2 = null;
        HttpSession session = httRequest.getSession();
        tempRegistrationVo = new InvestorTempRegistrationVo();
        if ((TempInvVo) session.getAttribute(TEMP_INVESTOR_ONE) != null) {
            tempRegistrationVo.setInvestorRegPage1((TempInvVo) session.getAttribute(TEMP_INVESTOR_ONE));
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            investorRegPage2 = objectMapper.readValue(abstractJson, TempInvNextPageVo.class);
            tempRegistrationVo.setInvestorRegPage2(investorRegPage2);
            IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
            session.setAttribute(TEMP_INVESTOR_TWO, investorRegPage2);
            userProfileBAO.saveInvTemp(tempRegistrationVo);
        } catch (IOException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public InvestorTempRegistrationVo getTempRegistrationVo() {
        return tempRegistrationVo;
    }

    public void setTempRegistrationVo(InvestorTempRegistrationVo tempRegistrationVo) {
        this.tempRegistrationVo = tempRegistrationVo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/publishRegistrationData")
    public String publishRegistrationData(@QueryParam("kitId") String kitId) {
        httpresponse.setHeader("Access-Control-Allow-Origin", "*");
        LOGGER.log(Level.INFO, "service called fro kit:-{0}", kitId);

        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        RegistrationVo regvo = userProfileBAO.getRegistraionData(Integer.parseInt(kitId));

        Gson gson = new Gson();
        JSONObject json = new JSONObject();
        if (regvo != null && regvo.getKitNumber() != null) {
            String jon = gson.toJson(regvo);
            JsonParser parser = new JsonParser();
            JsonObject o = parser.parse(jon).getAsJsonObject();
            o.addProperty("response status", "100");
            try {
                json.put(regvo.getKitNumber().toString(), o);
                LOGGER.info("********************************************************");
                LOGGER.log(Level.INFO, "json string{0}", json.toString());
            } catch (JSONException ex) {
                Logger.getLogger(CreateUserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JSONObject json1 = new JSONObject();
            try {
                json1.put("response status", "0");
                json.put(kitId, json1);
                LOGGER.info("********************************************************");
                LOGGER.log(Level.INFO, "json string{0}", json.toString());
            } catch (JSONException ex) {
                Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return json.toString();
    }

    /**
     * Service for uploading Advisor SEBI
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadSebiFile")
    public Response uploadSebiFile(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("regId") String regId) {
        modifyResponse();
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, SEBI_FILE_PATH, SEBI_CERTIFICATE, fis);
        if (panFile == null) {
            return Response.status(Status.CONFLICT).build();
        } else {
            return Response.status(Status.OK).build();
        }
    }

    /**
     * Service for uploading Investor PAN
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadPanFile")
    public Response uploadPanFile(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("regId") String regId) {
        modifyResponse();
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, IDENTITY_PROOF, PAN_SUBMITTED_NAME, fis);
        if (panFile == null) {
            return Response.status(Status.CONFLICT).build();
        } else {
            return Response.status(Status.OK).build();
        }
    }

    /**
     * Service for uploading Investor correspondence address proof
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadCoresAddrsFile")
    public Response uploadCoresAddrsFile(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("regId") String regId) {
        modifyResponse();
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME,
                fis);
        if (panFile == null) {
            return Response.status(Status.CONFLICT).build();
        } else {
            return Response.status(Status.OK).build();
        }
    }

    /**
     * Service for uploading Investor permanent address proof
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadPermAdrsFile")
    public Response uploadPermAdrsFile(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("regId") String regId) {
        modifyResponse();
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, VALIDITY_PERMENENT, PERMENENT_NAME, fis);
        if (panFile == null) {
            return Response.status(Status.CONFLICT).build();
        } else {
            return Response.status(Status.OK).build();
        }
    }

    /**
     * Service for uploading Investor Finance Document file For derivative
     * trading
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadDocEvdFile")
    public Response uploadDocEvdFile(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("regId") String regId) {
        modifyResponse();
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME, fis);
        if (panFile == null) {
            return Response.status(Status.CONFLICT).build();
        } else {
            return Response.status(Status.OK).build();
        }
    }

    public void modifyResponse() {
        int allowedOrgIndx = -1;
        List<String> allowedOrigins = Arrays.asList(allowedCrossDomain.trim().split(COMA));
        String clientOrigin = httRequest.getHeader(ORIGIN);
        if (clientOrigin != null) {
            allowedOrgIndx = allowedOrigins.indexOf(clientOrigin);
        }
        if (allowedOrgIndx != -1) {
            httpresponse.setHeader("Access-Control-Allow-Origin", clientOrigin);
        }
    }

    /**
     * @author Sumeet.pol Investor Registration page1 5min auto save call.
     *
     * @param abstractJson
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/autoSaveInvCompleteService")
    public String autoSaveInvCompleteService(String abstractJson) {
        LOGGER.log(Level.INFO, "JSON ;- {0}", abstractJson);
        HttpSession session = httRequest.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
        // String EMAIL_ID = "emailId";
        String email = (String) storedValues.get(EMAIL_ID);

        if (email != null) {
            tempRegistrationVo = new InvestorTempRegistrationVo();
            if ((TempInvNextPageVo) session.getAttribute(TEMP_INVESTOR_TWO) != null) {
                tempRegistrationVo.setInvestorRegPage2((TempInvNextPageVo) session.getAttribute(TEMP_INVESTOR_TWO));
            }

            // TempInvNextPageVo investorRegPage2 = null;
            tempRegistrationVo = new InvestorTempRegistrationVo();
            if ((TempInvVo) session.getAttribute(TEMP_INVESTOR_ONE) != null) {
                tempRegistrationVo.setInvestorRegPage1((TempInvVo) session.getAttribute(TEMP_INVESTOR_ONE));
            }
            try {
                //ObjectMapper objectMapper = new ObjectMapper();
            	com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                CompleteTempInvVo completeTempInvVo = objectMapper.readValue(abstractJson, CompleteTempInvVo.class);
                completeTempInvVo.setEmail(email);

                // hardcode values
                completeTempInvVo.setOpenAccountType("Trading Account and NSDL Demat Account");
                completeTempInvVo.setDp("NSDL:IN300239");
                completeTempInvVo.setTradingtAccount("Ordinary Resident");
                completeTempInvVo.setDematAccount("Ordinary Resident");
                completeTempInvVo.setSmsFacility("YES");
                completeTempInvVo.setDepoPartcpnt("Geojit BNP Paribas Financial Services Ltd");
                completeTempInvVo.setOtherInformation("");
                completeTempInvVo.setInstrn1("1");
                completeTempInvVo.setInstrn2("1");
                completeTempInvVo.setInstrn3("1");
                completeTempInvVo.setInstrn4("1");
                completeTempInvVo.setInstrn5("0");

                // facta values
                if (completeTempInvVo.getUsNational().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsNational("0");
                } else {
                    completeTempInvVo.setUsNational("1");
                }

                if (completeTempInvVo.getUsResident().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsResident("2");
                } else {
                    completeTempInvVo.setUsResident("3");
                }

                if (completeTempInvVo.getUsBorn().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsBorn("4");
                } else {
                    completeTempInvVo.setUsBorn("5");
                }

                if (completeTempInvVo.getUsAddress().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsAddress("6");
                } else {
                    completeTempInvVo.setUsAddress("7");
                }

                if (completeTempInvVo.getUsTelephone().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsTelephone("8");
                } else {
                    completeTempInvVo.setUsTelephone("9");
                }

                if (completeTempInvVo.getUsStandingInstruction().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsStandingInstruction("10");
                } else {
                    completeTempInvVo.setUsStandingInstruction("11");
                }

                if (completeTempInvVo.getUsPoa().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsPoa("12");
                } else {
                    completeTempInvVo.setUsPoa("13");
                }

                if (completeTempInvVo.getUsMailAddress().equalsIgnoreCase("true")) {
                    completeTempInvVo.setUsMailAddress("14");
                } else {
                    completeTempInvVo.setUsMailAddress("15");
                }

                IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
                RegistrationVo regVo = userProfileBAO.getTempInvData(email);
                String regId = "";

                boolean firsTimeAutoSave = false;

                if (regVo.getRegId() == null) {
                    regId = IdGenarator.getUniqueId();
                    firsTimeAutoSave = true;
                } else {
                    regId = regVo.getRegId();
                }

                completeTempInvVo.setRegId(regId);
                LOGGER.log(Level.INFO, email + " regId " + regId);
				// setting marker that there is complete page 1 and page 2
                // session.setAttribute(TEMP_INVESTOR_ONE, investorRegPage1);
                // session.setAttribute(TEMP_INVESTOR_TWO, investorRegPage2);
                session.setAttribute(COMPLETE_INVESTOR, completeTempInvVo);
                tempRegistrationVo.setCompleteRegPage(completeTempInvVo);
                userProfileBAO.saveCompleteInvTemp(tempRegistrationVo);
                LOGGER.log(Level.INFO, "autosave success.");

            } catch (IOException ex) {
                Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            LOGGER.log(Level.INFO, "inside autoSaveInvCompleteService no email id found. redirecting to home.html");
        }

        return null;

    }

    @POST
    @Path("/completeInvRegistration")
    public String completeInvRegistration() {

        LOGGER.log(Level.INFO, "inside completeInvRegistration");
        try {
            userRegistrationBean2 = (UserRegistrationBean2) BeanLoader.getBean("userRegistrationBean2");
            HttpSession session = httRequest.getSession();
            @SuppressWarnings("unchecked")
            Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
            if (storedValues != null) {
                // stored values contains user information
                LOGGER.log(Level.INFO,
                        "completing investor registration for user = " + storedValues.get(EMAIL_ID).toString());
                userRegistrationBean2.doInvRegistration(httRequest, httpresponse);
            } else {
                // error missing stored values
                LOGGER.log(Level.INFO,
                        "inside completeInvRegistration registration aborted due to missing session values");
            }

        } catch (Exception e) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, "completeInvRegistration rest Url Method",
                    e);
            try {
                httpresponse.sendRedirect(httRequest.getContextPath() + "/faces/pages/v2/home.html");
            } catch (IOException e1) {
                Logger.getLogger(RestService.class.getName()).log(Level.SEVERE,
                        "completeInvRegistration rest Url Method", e1);
            }
        }

        return null;

    }

    @POST
    @Path("/completeInvRegistrationAfterShowPdf")
    public String completeInvRegistrationAfterShowPdf() {
        LOGGER.log(Level.INFO, "inside completeInvRegistrationAfterShowPdf");
        try {
            userRegistrationBean2 = (UserRegistrationBean2) BeanLoader.getBean("userRegistrationBean2");
            HttpSession session = httRequest.getSession();
            @SuppressWarnings("unchecked")
            Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
            if (storedValues != null) {
				// stored values contains user information
                // this method does complete saving
                LOGGER.log(Level.INFO, "completing investor registration for user After pdf = "
                        + storedValues.get(EMAIL_ID).toString());
                userRegistrationBean2.doInvRegistrationAfterShowPdf(httRequest, httpresponse);
            } else {
                // error missing stored values
                LOGGER.log(Level.INFO,
                        "inside completeInvRegistrationAfterShowPdf registration aborted due to missing session values");
            }

        } catch (Exception e) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, "completeInvRegistration rest Url Method",
                    e);

        }

        return null;

    }

    @POST
    @Path("/completeAdvRegistration")
    public String completeAdvRegistration() {
        LOGGER.log(Level.INFO, "inside completeAdvRegistration");

        HttpSession session = null;
        try {
            session = httRequest.getSession();
            Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
            String email = (String) storedValues.get(EMAIL_ID);
            storedValues.put(IS_ADVISOR, true);
            storedValues.put(EXPIRE_IN, false);
            userRegistrationBean2 = (UserRegistrationBean2) BeanLoader.getBean("userRegistrationBean2");
            //session = httRequest.getSession();
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE,
                    "context --->" + session.getServletContext().getContextPath().toString());
			// for testing purpose code
            // String EMAIL_ID = "emailId";
            // create dummy hashmap
//			Map<String, Object> storedValues = new HashMap<String, Object>();
//			storedValues.put(IS_ADVISOR, true);
//			storedValues.put(EMAIL_ID, "testspa3@yopmail.com");
//			storedValues.put(EXPIRE_IN, false);

            session.setAttribute(STORED_VALUES, storedValues);
            // this method does complete saving
            userRegistrationBean2.doAdvisorRegistration(httRequest, httpresponse);
        } catch (Exception e) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, "completeInvRegistration rest Url Method",
                    e);

        }
        return null;

    }

    /**
     * Service for uploading Investor PAN
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadPanFileV2")
    public String uploadPanFileV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        RegistrationVo regVo = userProfileBAO.getTempInvData(email);
        String regId = "";
        if (regVo.getRegId() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegId();
        }
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, IDENTITY_PROOF, PAN_SUBMITTED_NAME, fis);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }

    /**
     * fetch partial data saved by investor
     */
    @POST
    @Path("/fetchUserData")
    public String fetchUserRegistrationData() {

        HttpSession session = httRequest.getSession();
        Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
        String email = (String) storedValues.get(EMAIL_ID);
        if (email != null) {
            // String email = "testsp3@yopmail.com";
            IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
            CompleteTempInvVo completeTempInvVo = userProfileBAO.getCompleteInvData(email);

            /*if (completeTempInvVo.getRegId() == null) {
             // TODO fetch little data from session
             }*/
            // RegistrationVo regVo = userProfileBAO.getTempInvData(email);
            try {
                if (completeTempInvVo.getStatus() == null) {
                    completeTempInvVo.setStatus("Resident Individual");
                }
                if (completeTempInvVo.getRelationship() == null) {
                    completeTempInvVo.setRelationship("Self");
                }
                if (completeTempInvVo.getBcountry() == null) {
                    completeTempInvVo.setBcountry("IN");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                String result = objectMapper.writeValueAsString(completeTempInvVo);
                result = result.toString();
                LOGGER.log(Level.INFO, "object from db 1: " + result);
                result = result.replaceAll("null", "");
                LOGGER.log(Level.INFO, "object from db 2: " + result);
                result =addInvProofFileDetils(result,userProfileBAO,email);
                return result;
            } catch (Exception e) {
                Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, "ERROR:IN GETTING JSON FROM OBJECT", e);
            }
            return "ERROR:IN GETTING JSON FROM OBJECT";
        } else {
            return "ERROR:IN GETTING JSON FROM OBJECT";
        }

    }

    private String addInvProofFileDetils(String result, IUserProfileBAO userProfileBAO, String email) {
		InvProofFileDetailsTb ipdf = userProfileBAO.getInvProofDetils(email);
		try {
			JSONObject temp = new JSONObject(result);
			if(ipdf == null){
				//no record found
				temp.put("bankFilePath", "");
			} else {
				temp.put("bankFilePath", ipdf.getBankFilePath());
			}
			return temp.toString();
		} catch (JSONException e) {
			LOGGER.log(Level.SEVERE, "error in new invProofDetailsAPI", e);
		}
		
		return result;
	}

    @POST
    @Path("/fetchRegId")
    public String fetchUserRegistrationId() {

        HttpSession session = httRequest.getSession();
        Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
        String email = (String) storedValues.get(EMAIL_ID);
        // String email = (String) session.getAttribute("username");
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        CompleteTempInvVo completeTempInvVo = userProfileBAO.getCompleteInvData(email);

        if (completeTempInvVo.getRegId() == null) {
            // TODO fetch little data from session
        }

        LOGGER.log(Level.INFO, "fetchRegId email = " + email + " " + completeTempInvVo.getRegId());
        return completeTempInvVo.getRegId();

    }

    /**
     * Service for uploading Advisor SEBI
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadSebiFileV2")
    public String uploadSebiFileV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        AdvisorRegistrationVo regVo = userProfileBAO.getTempAdvData(email);
        String regId = "";
        if (regVo.getRegNO() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegNO();
        }
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, SEBI_FILE_PATH, SEBI_CERTIFICATE, fis);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }

    /**
     * Service for uploading Investor correspondence address proof
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadCoresAddrsFileV2")
    public String uploadCoresAddrsFileV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        RegistrationVo regVo = userProfileBAO.getTempInvData(email);
        String regId = "";
        if (regVo.getRegId() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegId();
        }
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, VALIDITY_CORRESPONDENCE, CORRESPONDENCE_NAME,
                fis);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }

    /**
     * Service for uploading Investor permanent address proof
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadPermAdrsFileV2")
    public String uploadPermAdrsFileV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        RegistrationVo regVo = userProfileBAO.getTempInvData(email);
        String regId = "";
        if (regVo.getRegId() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegId();
        }
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, VALIDITY_PERMENENT, PERMENENT_NAME, fis);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }

    /**
     * Service for uploading Investor Finance Document file For derivative
     * trading
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadDocEvdFileV2")
    public String uploadDocEvdFileV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        RegistrationVo regVo = userProfileBAO.getTempInvData(email);
        String regId = "";
        if (regVo.getRegId() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegId();
        }
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, DOCUMENTARY_EVIDENCE, DOC_EVIDENCE_NAME, fis);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }

    /**
     * @author sumeet.pol Advisor Registration auto save call.
     *
     * @param abstractJson
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/autoSaveAdvCompleteService")
    public String autoSaveAdvCompleteService(String abstractJson) {
        LOGGER.log(Level.INFO, "JSON ;- {0}", abstractJson);
        modifyResponse();
        TempAdvVo advisorRegistrationVo = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            advisorRegistrationVo = objectMapper.readValue(abstractJson, TempAdvVo.class);
            IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
            userProfileBAO.saveAdvTemp(advisorRegistrationVo);
        } catch (IOException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @author sumeet.pol fetch partial data saved by advisor
     */
    @POST
    @Path("/fetchAdvUserData")
    public String fetchAdvUserRegistrationData() {

        HttpSession session = httRequest.getSession();
        Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
        String email = (String) storedValues.get(EMAIL_ID);
        //String email  = "testspa3@yopmail.com";
        if (email != null) {
            // String email = "testsp3@yopmail.com";
            IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
            TempAdvVo tempAdv = userProfileBAO.getCompleteTempAdvData(email);//CompleteTempInvVo completeTempInvVo = userProfileBAO.getCompleteInvData(email);

            if (tempAdv.getRegNO() == null) {
                // TODO fetch little data from session
            }
            // RegistrationVo regVo = userProfileBAO.getTempInvData(email);
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                String result = objectMapper.writeValueAsString(tempAdv);
                result = result.toString();
                LOGGER.log(Level.INFO, "object from db 1: " + result);
				//result = result.replaceAll("null", "");
                //LOGGER.log(Level.INFO, "object from db 2: " + result);
                return result;
            } catch (Exception e) {
                Logger.getLogger(RestService.class.getName()).log(Level.SEVERE, "ERROR:IN GETTING JSON FROM OBJECT", e);
            }
            return "ERROR:IN GETTING JSON FROM OBJECT";
        } else {
            return "ERROR:IN GETTING JSON FROM OBJECT";
        }

    }

    /**
     * Service for uploading Advisor Profile Picture
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadAdvPicV2")
    public String uploadAdvPicV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        AdvisorRegistrationVo regVo = userProfileBAO.getTempAdvData(email);
        String regId = "";
        if (regVo.getRegNO() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegNO();
        }
        String fileName = fdcd.getFileName();
        LOGGER.log(Level.INFO, "uploadAdvPicV2: RegID: " + regId+"File Name: "+fileName);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, ADVISOR_PIC_PATH, ADVISOR_PIC_NAME, fis);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }
    
    /**
     * Service for uploading Investor Profile Picture
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadInvPicV2")
    public String uploadInvPicV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        RegistrationVo regVo = userProfileBAO.getTempInvData(email);
        String regId = "";
        if (regVo.getRegId() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegId();
        }
        String fileName = fdcd.getFileName();
        LOGGER.log(Level.INFO, "uploadInvPicV2: RegID: " + regId+"File Name: "+fileName);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, INVESTOR_PIC_PATH, INVESTOR_PIC_NAME, fis);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }
    
    /**
     * Service for uploading Testing upload for scriptcam
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/testpicupload")
    public String testpicupload(@FormDataParam("base64image") String imageStr, @FormDataParam("dataUrl") String dataUrl) {
        modifyResponse();

        String regId = IdGenarator.getUniqueId();
        try {
            LOGGER.log(Level.INFO, "base64image :" + imageStr);
            LOGGER.log(Level.INFO, "data url to : " + dataUrl);
            String uploadFile = ADVISOR_PIC_PATH + regId + ADVISOR_PIC_NAME + ".png";
            imageStr = dataUrl.substring(dataUrl.indexOf(",") + 1);
            byte imgBytes[] = Base64.decode(imageStr);
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
            File imgOutFile = new File(uploadFile);
            ImageIO.write(bufImg, "png", imgOutFile);
            LOGGER.log(Level.INFO, "1 done: ");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "error at upload image", e);
        }
        return "";
    }

    /**
     * Service for uploading Advisor Profile Picture
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/testpicupload2")
    public String testpicupload2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd) {
        modifyResponse();

        String regId = "";

        regId = IdGenarator.getUniqueId();

        String fileName = "dummy.png";
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, ADVISOR_PIC_PATH, ADVISOR_PIC_NAME, fis);
        LOGGER.log(Level.INFO, "2 done ");
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }

    /**
     * state list
     *
     * @return 
	 *
     */
    @POST
    @Path("/getStateList")
    public String getStateList() {
        JSONArray stateNameArray = new JSONArray();
        Map<String, String> stateMap = LookupDataLoader.getStateListLookup();
        for (Map.Entry<String, String> entry : stateMap.entrySet()) {
            //LOGGER.log(Level.INFO,entry.getKey() + "/" + entry.getValue());
            stateNameArray.put(entry.getValue().toString());
        }
        return stateNameArray.toString();
    }

    /**
     * city list
     *
     * @return 
	 *
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/getCityList")
    public String getCityList(@FormDataParam("stateName") String stateName) {
        JSONArray cityNameArray = new JSONArray();
        List<CitiesTb> citymaster = LookupDataLoader.getMasterCities();
        for (CitiesTb city : citymaster) {
            if (city.getStateCode().equals(stateName)) {
                cityNameArray.put(city.getCityName());
                //  LOGGER.log(Level.INFO,city.getCityName().toString());
            }
        }
        return cityNameArray.toString();
    }

    /**
     * bank list
     *
     * @return 
	 *
     */
    @POST
    @Path("/getBankList")
    public String getBankList() {
        JSONArray bankNameArray = new JSONArray();
        Map<Integer, String> bankMap = LookupDataLoader.getBankListLookup();
        for (Map.Entry<Integer, String> entry : bankMap.entrySet()) {
            //LOGGER.log(Level.INFO,entry.getKey() + "/" + entry.getValue());
            bankNameArray.put(entry.getValue().toString());
        }
        return bankNameArray.toString();
    }
    
   
    
    
    
    @POST
    @Path("/getUserFpStatus")
    public String getUserFpStatus() {
    	HttpSession httpSession = httRequest.getSession();
    	UserSessionBean userSessionBean = (UserSessionBean) httpSession.getAttribute("userSession");
    	IFpMainCalculationBAO fpBAO = (IFpMainCalculationBAO) BeanLoader.getBean("FpMainCalculationBAO");
    	if(userSessionBean != null ){
    		boolean fpNotCompleted = fpBAO.getUserFpStatus(userSessionBean.getRegId());
    		return String.valueOf(fpNotCompleted);
    	}
    	return "Error:fpstatusfailed";
    }
    
    
    @POST
    @Path("/getAUMPayableFrequencyList")
    public String getAUMPayableFrequencyList() {
    	JSONObject aumFreqArray = new JSONObject();
		Map<String,Integer>	aumFreqList = LookupDataLoader.getAUMPayableFrequencyList();
		
		try{
			for(Map.Entry<String, Integer> entry : aumFreqList.entrySet()){
					aumFreqArray.put(entry.getKey().toString(),entry.getValue().toString());				
			}
	        return aumFreqArray.toString();
	    }catch (JSONException e) {
	        return "Error:AUMPayableFrequencyListFailed";
		}
    }
    
    @POST
    @Path("/getMgmtFeeVariableAmountList")
    public String getAumFreqList() {
    	JSONObject mgmtFeeVarArray = new JSONObject();
		Map<String,BigDecimal>	mgmtFeeVarList = LookupDataLoader.getMgmtFeeVariableAmountList();
		try{
			for(Map.Entry<String, BigDecimal> entry : mgmtFeeVarList.entrySet()){
				mgmtFeeVarArray.put(entry.getKey().toString(),entry.getValue().toString());
			}
	    }catch (JSONException e) {
	        return "Error:MgmtFeeVariableAmountListFailed";
		}
        return mgmtFeeVarArray.toString();
    }    
    
    @POST
    @Path("/getContractDurationFreqList")
    public String getContractDurationFreqList() {
    	JSONObject contractDurationFreqArray = new JSONObject();
		Map<String, Integer>	contractDurationFreqList = LookupDataLoader.getContractDurationFreqList();
		try{
			for(Entry<String, Integer> entry : contractDurationFreqList.entrySet()){
				contractDurationFreqArray.put(entry.getKey().toString(),entry.getValue().toString());
			}
	    }catch (JSONException e) {
	        return "Error:ContractDurationFreqListFailed";
		}
        return contractDurationFreqArray.toString();
    }    
    
    @POST
    @Path("/getDurationList")
    public String getDurationList() {
    	JSONObject contractDurationArray = new JSONObject();
		Map<String, Integer>	contractDurationList = LookupDataLoader.getDurationList();
		try{
			for(Entry<String, Integer> entry : contractDurationList.entrySet()){
				contractDurationArray.put(entry.getKey().toString(),entry.getValue().toString());
			}
	    }catch (JSONException e) {
	        return "Error:DurationListFailed";
		}
        return contractDurationArray.toString();
    }
    
   
    
    @POST
    @Path("/getPublicCaptchaKey")
    public String getPublicCaptchaKey() {
    	return LookupDataLoader.getPublicCaptchaKey();
    }

	/**
	 * @author Sumeet 22-02-2017 Service for uploading Investor ID PROOF
	 * @param data
	 *            url of img
	 * @param regId
	 * @return filename saved on server
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/uploadIdFileV3URL")
	public String uploadPanFileV3URL(@FormDataParam("file") String fileName, @FormDataParam("email") String email) {
		modifyResponse();
		IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
		RegistrationVo regVo = userProfileBAO.getTempInvData(email);
		String regId = "";
		if (regVo.getRegId() == null) {
			regId = IdGenarator.getUniqueId();
		} else {
			regId = regVo.getRegId();
		}
		// String fileName = fdcd.getFileName();
		System.out.println("File Name: " + regId);
		String panFile = FileUploadUtil.WsUploadFile(regId, fileName, IDENTITY_PROOF, ID_PROOF, null);
		if (panFile == null) {
			return "Error in File Upload.";
		} else if (panFile.startsWith("error")) {
			return panFile;
		} else {
			return panFile;
		}
	}
	
	/**
	 * @author Sumeet 22-02-2017 Service for uploading Investor CORS ADDR PROOF
	 * @param data
	 *            url of img
	 * @param regId
	 * @return filename saved on server
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/uploadCorAddrFileV3URL")
	public String uploadCorAddrFileV3URL(@FormDataParam("file") String fileName, @FormDataParam("email") String email) {
		modifyResponse();
		IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
		RegistrationVo regVo = userProfileBAO.getTempInvData(email);
		String regId = "";
		if (regVo.getRegId() == null) {
			regId = IdGenarator.getUniqueId();
		} else {
			regId = regVo.getRegId();
		}
		// String fileName = fdcd.getFileName();
		System.out.println("File Name: " + regId);
		String panFile = FileUploadUtil.WsUploadFile(regId, fileName,VALIDITY_CORRESPONDENCE , CORRESPONDENCE_NAME, null);
		if (panFile == null) {
			return "Error in File Upload.";
		} else if (panFile.startsWith("error")) {
			return panFile;
		} else {
			return panFile;
		}
	}
	
	/**
	 * @author Sumeet 22-02-2017 Service for uploading Investor PERMN ADDR PROOF
	 * @param data
	 *            url of img
	 * @param regId
	 * @return filename saved on server
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/uploadPerAddrFileV3URL")
	public String uploadPerAddrFileV3URL(@FormDataParam("file") String fileName, @FormDataParam("email") String email) {
		modifyResponse();
		IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
		RegistrationVo regVo = userProfileBAO.getTempInvData(email);
		String regId = "";
		if (regVo.getRegId() == null) {
			regId = IdGenarator.getUniqueId();
		} else {
			regId = regVo.getRegId();
		}
		// String fileName = fdcd.getFileName();
		System.out.println("File Name: " + regId);
		String panFile = FileUploadUtil.WsUploadFile(regId, fileName,VALIDITY_PERMENENT , PERMENENT_NAME, null);
		if (panFile == null) {
			return "Error in File Upload.";
		} else if (panFile.startsWith("error")) {
			return panFile;
		} else {
			return panFile;
		}
	}
	
	/**
	 * @author Sumeet 22-02-2017 Service for uploading Investor BANK PROOF
	 * @param data
	 *            url of img
	 * @param regId
	 * @return filename saved on server
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/uploadBankFileV3URL")
	public String uploadBankFileV3URL(@FormDataParam("file") String fileName, @FormDataParam("email") String email) {
		modifyResponse();
		IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
		RegistrationVo regVo = userProfileBAO.getTempInvData(email);
		String regId = "";
		if (regVo.getRegId() == null) {
			regId = IdGenarator.getUniqueId();
		} else {
			regId = regVo.getRegId();
		}
		// String fileName = fdcd.getFileName();
		System.out.println("File Name: " + regId);
		String panFile = FileUploadUtil.WsUploadFile(regId, fileName,BANK_DETAILS_PROOF , BANK_NAME_POSTFIX, null);
		userProfileBAO.saveProofFileToDb(panFile,"bank",email);
		if (panFile == null) {
			return "Error in File Upload.";
		} else if (panFile.startsWith("error")) {
			return panFile;
		} else {
			return panFile;
		}
	}
	
	/**
     * Service for uploading Investor permanent address proof
     *
     * @param fis
     * @param fdcd
     * @param regId
     * @return
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/uploadBankFileV2")
    public String uploadBankFileV2(@FormDataParam("file") InputStream fis,
            @FormDataParam("file") FormDataContentDisposition fdcd, @FormDataParam("email") String email) {
        modifyResponse();
        IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
        RegistrationVo regVo = userProfileBAO.getTempInvData(email);
        String regId = "";
        if (regVo.getRegId() == null) {
            regId = IdGenarator.getUniqueId();
        } else {
            regId = regVo.getRegId();
        }
        String fileName = fdcd.getFileName();
        System.out.println("File Name: " + regId);
        String panFile = FileUploadUtil.WsUploadFile(regId, fileName, BANK_DETAILS_PROOF, BANK_NAME_POSTFIX, fis);
        userProfileBAO.saveProofFileToDb(panFile,"bank",email);
        if (panFile == null) {
            return "Error in File Upload.";
        } else if (panFile.startsWith("error")) {
            return panFile;
        } else {
            return panFile;
        }
    }

}
