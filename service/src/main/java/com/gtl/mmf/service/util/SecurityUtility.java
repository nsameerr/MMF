/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.util.StackTraceWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 07960
 */
public final class SecurityUtility {
     private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.SecurityUtility");
    private static SecurityUtility securityUtility;
    public static final String HASH_ALGORITHM = "SHA";
    private static MessageDigest mDigest;

    static {
        try {
            mDigest = MessageDigest.getInstance(HASH_ALGORITHM);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE,StackTraceWriter.getStackTrace(e));
        }
    }

    private SecurityUtility() {
    }

    public static SecurityUtility getInstance() {
        if (securityUtility == null) {
            securityUtility = new SecurityUtility();
        }
        return securityUtility;
    }

    public String encrypt(String str) {
        byte outbuf[];
        String encryptedString = null;
        if (mDigest == null) {
            return null;
        }
        try {
            byte inbuf[] = str.getBytes("UTF8");
            synchronized (mDigest) {
                outbuf = mDigest.digest(inbuf);
                encryptedString = Base64Codec.encode(outbuf);
                return encryptedString;

            }
        } catch (UnsupportedEncodingException ex) {
             LOGGER.log(Level.SEVERE,StackTraceWriter.getStackTrace(ex));
        }
        return null;
    }

    /**
     * Creates a new instance of SecurityUtility
     */
}
