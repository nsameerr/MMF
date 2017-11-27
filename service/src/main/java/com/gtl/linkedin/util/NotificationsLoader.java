/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.linkedin.util;

import java.util.ResourceBundle;

/**
 *
 * @author 07958
 */
public class NotificationsLoader {

    private static final String PROPERTIES_FILE = "notification";
    private static ResourceBundle reBundle;

    private NotificationsLoader() {
    }

    static {
        reBundle = ResourceBundle.getBundle(PROPERTIES_FILE);
    }

    public static String getPropertiesValue(String key) {
        return reBundle.getString(key);
    }
}
