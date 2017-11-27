/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.util;

import java.util.UUID;

public final class UUIDUtil {

    public static String genrateUUID() {
        return UUID.randomUUID().toString();
    }
}
