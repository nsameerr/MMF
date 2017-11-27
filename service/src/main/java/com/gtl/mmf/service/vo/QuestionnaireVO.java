/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.vo;

import java.util.List;

public class QuestionnaireVO {

    private int questionId;
    private String questionName;
    private int qstoptionId;
    private int qstoptionValue;
    private String img_path;
    private List<OptionValuesVO> options;

    public QuestionnaireVO(int questionId, String questionName, int qstoptionId, int qstoptionValue, List<OptionValuesVO> options,String img_path) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.qstoptionId = qstoptionId;
        this.qstoptionValue = qstoptionValue;
        this.options = options;
        this.img_path = img_path;
    }

    public QuestionnaireVO() {
		// TODO Auto-generated constructor stub
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

    public int getQstoptionId() {
        return qstoptionId;
    }

    public void setQstoptionId(int qstoptionId) {
        this.qstoptionId = qstoptionId;
    }

    public int getQstoptionValue() {
        return qstoptionValue;
    }

    public void setQstoptionValue(int qstoptionValue) {
        this.qstoptionValue = qstoptionValue;
    }

    public List<OptionValuesVO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionValuesVO> options) {
        this.options = options;
    }

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
   
}
