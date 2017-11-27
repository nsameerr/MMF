/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.common;

import java.util.Map;
import java.util.HashMap;

public enum EnumQuestionnaireProfileStatus {

    Conservative(1, 0, 20),
    Coderately_Conservative(2, 21, 40),
    Moderate(3, 41, 60),
    Moderately_Aggressive(4, 61, 80),
    Aggressive(5, 80, 100),
    UndefinedStatus(6, -1, -1);

    private final int value;
    private final int minRange;
    private final int maxRange;

    private EnumQuestionnaireProfileStatus(int value, int minRange, int maxRange) {
        this.value = value;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    private static final Map<Integer, EnumQuestionnaireProfileStatus> INT_TO_TYPE_MAP = new HashMap<Integer, EnumQuestionnaireProfileStatus>();

    static {
        for (EnumQuestionnaireProfileStatus en : EnumQuestionnaireProfileStatus.values()) {
            INT_TO_TYPE_MAP.put(en.value, en);
        }
    }

    public static String fromStatus(int totalScore) {
        for (Map.Entry<Integer, EnumQuestionnaireProfileStatus> entry : INT_TO_TYPE_MAP.entrySet()) {
            EnumQuestionnaireProfileStatus en = entry.getValue();
            if (totalScore >= en.minRange && totalScore <= en.maxRange) {
                return en.toString().replace("_", " ");
            }
        }
        return UndefinedStatus.toString();
    }
}
