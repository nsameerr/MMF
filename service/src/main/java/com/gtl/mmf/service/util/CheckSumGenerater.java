/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * Class used to create checksum for Bill-desk transaction
 *
 * @author trainee8
 */
public class CheckSumGenerater {

    public static String generateCheckSum(String message, String secret) {
        MessageDigest md = null;
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte raw[] = sha256_HMAC.doFinal(message.getBytes());

            StringBuffer ls_sb = new StringBuffer();
            for (int i = 0; i < raw.length; i++) {
                ls_sb.append(char2hex(raw[i]));
            }
            return ls_sb.toString(); //step 6
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String char2hex(byte x) {
        char arr[] = {
            '0', '1', '2', '3',
            '4', '5', '6', '7',
            '8', '9', 'A', 'B',
            'C', 'D', 'E', 'F'
        };

        char c[] = {arr[(x & 0xF0) >> 4], arr[x & 0x0F]};
        return (new String(c));
    }
}
