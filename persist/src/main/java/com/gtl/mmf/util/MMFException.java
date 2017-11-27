/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */

package com.gtl.mmf.util;

import java.util.logging.Logger;


public final class MMFException extends Throwable{
    
    private static  final Logger LOGGER = Logger.getLogger("com.gtl.mmf.util.MMFException");
    
    private String errorMessage;
    private String jobStatusMessage;
    private Throwable throwable;

    public MMFException(String message, Throwable throwable) {
        this.errorMessage = message;
        this.throwable = throwable;
    }

    public MMFException(String jobStatusMessage, String message, Throwable throwable) {
        this.errorMessage = message;
        this.throwable = throwable;
        this.jobStatusMessage = jobStatusMessage;
    }
    
    public String getAllStackTrace(){
        return StackTraceWriter.getStackTrace(throwable);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getJobStatusMessage() {
        return jobStatusMessage;
    }

    public void setJobStatusMessage(String jobStatusMessage) {
        this.jobStatusMessage = jobStatusMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
