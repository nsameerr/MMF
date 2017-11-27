/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author 07960
 */
public final class IdGenarator implements IConstants {

    private static IdGenarator idGenarator = null;

    private IdGenarator() {
    }

    public static IdGenarator getInstance() {
        if (idGenarator != null) {
            idGenarator = new IdGenarator();
        }
        return idGenarator;
    }

    public static String getUniqueId() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYMMDDHHSS);
        return sdf.format(new Date());
    }
}
