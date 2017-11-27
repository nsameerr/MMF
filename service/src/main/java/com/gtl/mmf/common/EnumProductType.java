/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

/**
 *
 * @author 09860
 */
public enum EnumProductType {

    CASH(1),
    INTRADAY(2),
    BTST(5),
    MTF(6),
    FAO_NORMAL(7),
    FAO_INTRADAY(8),
    MF(9),
    SMARTPLUS(15),
    STRATEGY(16),
    OCO(17),
    TSL(18),
    GTD(19),
    ETO(20);

    private int productTypes;

    EnumProductType(int ProductTypeEnum) {
        this.productTypes = productTypes;
    }

    public int value() {
        return productTypes;
    }

    public static boolean validType(int productType) {
        boolean flag = false;
        for (EnumProductType var : EnumProductType.values()) {
            if (var.value() == productType) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String getProductType(int productType) {
        switch (productType) {
            case 1:
                return "CASH";
            case 2:
                return "INTRADAY";
            case 5:
                return "BTST";
            case 6:
                return "MTF";
            case 7:
                return "FAO_NORMAL";
            case 8:
                return "FAO_INTRADAY";
            case 9:
                return "MF";
            case 15:
                return "SMARTPLUS";
            case 16:
                return "STRATEGY";
            default:
                return null;
        }
    }
    
    

}
