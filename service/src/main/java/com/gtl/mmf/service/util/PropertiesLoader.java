/**
 *
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.gtl.mmf.service.util.IConstants.SYSTEM;

/**
 * @author 08237
 *
 */
public final class PropertiesLoader {

    private static final String PROPERTIES_FILE_MMF = "mmf";
    private static final String PROPERTIES_FILE_OMS_FE = "FrontEndMsgBundle";
    private static final String PROPERTIES_FILE_OMS_ER = "ErrorMessageBundle";
    private static ResourceBundle reBundle1;
    private static ResourceBundle reBundle2;
    private static ResourceBundle reBundle3;
    private static Properties properties;

   

    private PropertiesLoader() {
    }

    static {
        reBundle1 = ResourceBundle.getBundle(PROPERTIES_FILE_MMF);
        reBundle2 = ResourceBundle.getBundle(PROPERTIES_FILE_OMS_FE);
        reBundle3 = ResourceBundle.getBundle(PROPERTIES_FILE_OMS_ER);
    }

    public static String getPropertiesValue(String key) {
        if (LookupDataLoader.getConfigOption().equalsIgnoreCase(SYSTEM) && properties == null) {
            FileInputStream in = null;
            properties = new Properties();
            try {
                in = new FileInputStream(LookupDataLoader.getMmfProprty());
                try {
                    properties.load(in);
                } catch (IOException ex) {
                    Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        }if(properties !=null){
            return properties.getProperty(key);
        } else {
            return reBundle1.getString(key);
        }
    }

    public static String getPropertiesValue(String key, String filename) {

        String value = EMPTY_STRING;
        if (filename.equals(PROPERTIES_FILE_OMS_FE)) {
            value = reBundle2.getString(key);
        } else if (filename.equals(PROPERTIES_FILE_OMS_ER)) {
            value = reBundle3.getString(key);
        }
        return value;
    }
}
