package com.gtl.mmf.entity;
// Generated Jul 19, 2016 1:06:41 AM by Hibernate Tools 3.4.0.CR1

/**
 * FpFrequencyidMasterTb generated by hbm2java
 */
public class FpFrequencyidMasterTb implements java.io.Serializable {

	private Integer id;
	private Integer frequencyId;
	private String frequencyDescription;

	public FpFrequencyidMasterTb() {
	}

	public FpFrequencyidMasterTb(Integer frequencyId,
			String frequencyDescription) {
		this.frequencyId = frequencyId;
		this.frequencyDescription = frequencyDescription;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFrequencyId() {
		return this.frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequencyDescription() {
		return this.frequencyDescription;
	}

	public void setFrequencyDescription(String frequencyDescription) {
		this.frequencyDescription = frequencyDescription;
	}

}