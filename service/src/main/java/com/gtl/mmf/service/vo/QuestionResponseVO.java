/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.vo;

public class QuestionResponseVO {

    private int questionId;
    private String questionName;
    private int optionId;
    private String oprtionname;
    private String img_path;
    public QuestionResponseVO(int questionId, String questionName, int optionId, String oprtionname,String img_path) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.optionId = optionId;
        this.oprtionname = oprtionname;
        this.img_path = img_path;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOprtionname() {
        return oprtionname;
    }

    public void setOprtionname(String oprtionname) {
        this.oprtionname = oprtionname;
    }

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
    
    

}
