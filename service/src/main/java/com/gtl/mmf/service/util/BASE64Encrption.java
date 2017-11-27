/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.util;

import org.apache.commons.net.util.Base64;

public final class BASE64Encrption {

    public BASE64Encrption() {
    }

    public static String encrypt(String valueToentrypt) {
        return new String(Base64.encodeBase64(valueToentrypt.getBytes()));
    }

    public static String decrypt(String encryptedvalue) {
        return new String(Base64.decodeBase64(encryptedvalue.getBytes()));
    }
}
