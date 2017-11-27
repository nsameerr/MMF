/*
 * To change this license header(), choose License Headers in Project Properties.
 * To change this template file(), choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

/**
 *
 * @author 09860
 */
public enum EnumOMSChannel {

    OTHER(0),
    FLIP_CC(1),
    FLIP_WEB(2),
    FLIP_THICK_WEB(3),
    FLIP_THICK_TRD(4),
    FLIP_WEB_LITE(5),
    FLIP_ME(6),
    DMA(8),
    FLIP_RMS(9),
    FLIP_SURV_RMS(10),
    FLIP_TOUCH(11),
    FLIPXL_WEB(12),
    FLIPXL_ME(13),
    MMF(14);

    private int channel;

    private EnumOMSChannel(int channel) {
        this.channel = channel;
    }

    public int value() {
        return this.channel;
    }

    public static boolean validType(int channel) {
        boolean flag = false;
        for (EnumOMSChannel var : values()) {
            if (var.value() == channel) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String getChannel(int channel) {
        switch (channel) {
            case 0:
                return "OTHER";
            case 1:
                return "FLIP_CC";
            case 2:
                return "FLIP_WEB";
            case 3:
                return "FLIP_THICK_WEB";
            case 4:
                return "FLIP_THICK_TRD";
            case 5:
                return "FLIP_WEB_LITE";
            case 6:
                return "FLIP_ME";
            case 8:
                return "DMA";
            case 9:
                return "FLIP_RMS";
            case 10:
                return "FLIP_SURV_RMS";
            case 11:
                return "FLIP_TOUCH";
            case 12:
                return "FLIPXL_WEB";
            case 13:
                return "FLIPXL_ME";
            case 14:
                return "MMF";
        }
        return null;
    }

}
