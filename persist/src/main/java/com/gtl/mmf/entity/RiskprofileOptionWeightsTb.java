package com.gtl.mmf.entity;
// Generated Sep 26, 2016 6:44:40 PM by Hibernate Tools 5.1.0.Beta1

/**
 * RiskprofileOptionWeightsTb generated by hbm2java
 */
@SuppressWarnings("serial")
public class RiskprofileOptionWeightsTb implements java.io.Serializable {

	private Integer id;
	private int QId;
	private int AId;
	private int weight;

	public RiskprofileOptionWeightsTb() {
	}

	public RiskprofileOptionWeightsTb(int QId, int AId, int weight) {
		this.QId = QId;
		this.AId = AId;
		this.weight = weight;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getQId() {
		return this.QId;
	}

	public void setQId(int QId) {
		this.QId = QId;
	}

	public int getAId() {
		return this.AId;
	}

	public void setAId(int AId) {
		this.AId = AId;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
