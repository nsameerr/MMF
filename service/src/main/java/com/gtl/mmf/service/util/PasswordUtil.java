/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import java.util.Random;

/**
 *
 * @author 07960
 */
public final class PasswordUtil implements IConstants {

    private PasswordUtil() {
    }

    public static String getPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = ZERO; i < RANDOM_STRING_LENGTH; i++) {
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    private static int getRandomNumber() {
        int randomInt = ZERO;
        Random random = new Random();
        randomInt = random.nextInt(CHAR_LIST.length());
        if (randomInt <= ZERO) {
            randomInt = ONE;
        }
        return randomInt;
    }
}
