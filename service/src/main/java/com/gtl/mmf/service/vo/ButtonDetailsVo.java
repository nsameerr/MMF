/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author 07958
 */
public class ButtonDetailsVo {

    private String buttonText;
    private String buttonStyleClass;
    private boolean immediate;
    private boolean confirm;

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonStyleClass() {
        return buttonStyleClass;
    }

    public void setButtonStyleClass(String buttonStyleClass) {
        this.buttonStyleClass = buttonStyleClass;
    }

    public boolean isImmediate() {
        return immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
