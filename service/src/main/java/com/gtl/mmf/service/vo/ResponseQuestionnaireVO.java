/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author 07662
 */
package com.gtl.mmf.service.vo;

import java.util.Date;

public class ResponseQuestionnaireVO {

    private int relationid;
    private int questionid;
    private int optionid;
    private int optionvalue;
    private Date responsedate;

    public ResponseQuestionnaireVO() {
    }

    public int getRelationid() {
        return relationid;
    }

    public void setRelationid(int relationid) {
        this.relationid = relationid;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public int getOptionid() {
        return optionid;
    }

    public void setOptionid(int optionid) {
        this.optionid = optionid;
    }

    public int getOptionvalue() {
        return optionvalue;
    }

    public void setOptionvalue(int optionvalue) {
        this.optionvalue = optionvalue;
    }

    public Date getResponsedate() {
        return responsedate;
    }

    public void setResponsedate(Date responsedate) {
        this.responsedate = responsedate;
    }

}
