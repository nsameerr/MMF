/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.git.oms.api.frontend.exceptions.FrontEndException;
import com.git.oms.api.frontend.message.OMSDTO;
import com.git.oms.api.frontend.util.MessageTransformerHelper;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.OMS_BACKEND_RVDEAMON;
import static com.gtl.mmf.service.util.IConstants.OMS_BACKEND_RVNETWORK;
import static com.gtl.mmf.service.util.IConstants.OMS_BACKEND_RVSERVICE;
import static com.gtl.mmf.service.util.IConstants.OMS_LISTEN_SUBJECT;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvDispatcher;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;
import com.tibco.tibrv.TibrvQueue;
import com.tibco.tibrv.TibrvRvdTransport;
import com.tibco.tibrv.TibrvTransport;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.util.StringUtils;

/**
 *
 * @author 09860
 */
public class OMSAdapterRv implements TibrvMsgCallback {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.OMSAdapterRv");
    public TibrvTransport rvTransport;
    private TibrvListener tiblistener;
    //omslogins HashMap with key = OmsSessionId,value = HttpSession  
    public static HashMap<String, HttpSession> omslogins = new HashMap<String, HttpSession>();

    public void subscribeSubject(String userid, String omsSession, HttpSession session)
            throws Exception {
        String omsListenSub = EMPTY_STRING;
        if (!StringUtils.hasText(userid) || !StringUtils.hasText(omsSession)) {
            //For listening OMS backend service
            omsListenSub = OMS_LISTEN_SUBJECT;
            throw new Exception("Rv Listenn subject is not valid ", new Throwable());
        } else {
            //For listening OMS front end service
            omsListenSub = "OMS." + userid + ":" + omsSession;
            rvTransport = LookupDataLoader.getTibrvTransport();
            if (!omslogins.isEmpty()) {
                for (Object obj : omslogins.keySet()) {
                    if (obj.toString().startsWith(userid)) {
                        omslogins.remove(obj);
                    }
                }
                omslogins.put(omsSession, session);
            } else {
                omslogins.put(omsSession, session);
            }
        }
        TibrvQueue tibQueue = new TibrvQueue();
        this.tiblistener = new TibrvListener(tibQueue, this, rvTransport, omsListenSub, null);
        new TibrvDispatcher(tibQueue);
        LOGGER.log(Level.INFO, "Successfully added listener for the subject [{0}]", omsListenSub);
    }

    /**
     * Method to Initialize OMS backend RV
     */
    public void initBackendRv() {
        try {
            //For initializing OMS backend service
            String network = PropertiesLoader.getPropertiesValue(OMS_BACKEND_RVNETWORK);
            String daemon = PropertiesLoader.getPropertiesValue(OMS_BACKEND_RVDEAMON);
            String service = PropertiesLoader.getPropertiesValue(OMS_BACKEND_RVSERVICE);
            LOGGER.log(Level.INFO, "RV Configuration - Network [{0}] Daemon [{1}] Service [{2}]", new Object[]{network, daemon, service});
            if (!Tibrv.isValid()) {
                Tibrv.open(2);
                LOGGER.log(Level.INFO, "Tib RV opened with Native Implementation");
            }
            rvTransport = new TibrvRvdTransport(service, network, daemon);
            LOGGER.log(Level.INFO, "TibRV Transport created");
        } catch (TibrvException ex) {
            LOGGER.log(Level.INFO, "TibrvException occcured in connect of FrontEndRVTransporter", ex);
        }
    }

    public void onMsg(TibrvListener arg0, TibrvMsg tibRvMsg) {
        try {
            LOGGER.log(Level.INFO, "Received tibrv message {0}", tibRvMsg);
            Object obj = tibRvMsg.get("D");
            if ((obj instanceof byte[])) {
                // Will invoke when listening to OMS frontend service.
                OMSDTO omsdto = MessageTransformerHelper.decode((byte[]) obj);
                LOGGER.log(Level.INFO, "User:{0} logged in from other application", omsdto.getDTOHeader().getLoginID());
                OMSOrderVO orderVO = new OMSOrderVO();
                orderVO.setOmsUserid(omsdto.getDTOHeader().getLoginID());
                orderVO.setOmsSessionKey(omsdto.getDTOHeader().getSessionKey());
                for (Object session : omslogins.keySet()) {
                    if (session.equals(omsdto.getDTOHeader().getSessionKey())) {
                        HttpSession sesnId = omslogins.get(session);
                        sesnId.removeAttribute("OMSOrderVO");
                        sesnId.setAttribute("OmsUserInactive", true);
                        omslogins.remove(session);
                    }
                }
            } else {
                // Will invoke when listening to OMS backed service
                String msgType = (String) tibRvMsg.get("MessageType");
                if (msgType != null) {
                    Map msgMap = new HashMap();
                    msgMap.put("MessageType", msgType);
                    msgMap.put("omsSessionKey", (String) tibRvMsg.get("SessionKey"));
                    msgMap.put("channel", (String) tibRvMsg.get("channel"));
                    msgMap.put("userCode", (String) tibRvMsg.get("UserCode"));
                    msgMap.put("userName", (String) tibRvMsg.get("userName"));
                    msgMap.put("clientType", (String) tibRvMsg.get("clientType"));
                    LOGGER.log(Level.INFO, "received tib rv message {0}", msgMap.toString());
                    for (Object session : omslogins.keySet()) {
                        if (session.equals(msgMap.get("omsSessionKey"))) { 
                            HttpSession sesnId = omslogins.get(session);
                            sesnId.removeAttribute("OMSOrderVO");
                            sesnId.setAttribute("OmsUserInactive", true);
                            omslogins.remove(session);
                        }
                    }
                } else {
                    LOGGER.log(Level.INFO, "Msg not Recognized!!");
                }
            }
        } catch (TibrvException ex) {
            LOGGER.log(Level.SEVERE, "Error in onmsg oms DTO ", StackTraceWriter.getStackTrace(ex));
        } catch (FrontEndException ex) {
            LOGGER.log(Level.SEVERE, "Error in onmsg oms DTO ", StackTraceWriter.getStackTrace(ex));
        }
    }

    public TibrvTransport getRvTransport() {
        return rvTransport;
    }

    public void setRvTransport(TibrvTransport rvTransport) {
        this.rvTransport = rvTransport;
    }

    public static HashMap<String, HttpSession> getOmslogins() {
        return omslogins;
    }

    public static void setOmslogins(HashMap<String, HttpSession> omslogins) {
        OMSAdapterRv.omslogins = omslogins;
    }

}
