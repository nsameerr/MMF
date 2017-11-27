package com.gtl.mmf.service.vo.financialplanner;

/* 
List of Life Goal Description IDs:
	0 - Others
	1 - Self Marriage
	2 - Child Marriage
	3 - Two Wheeler (Bike)
	4 - Car
	5 - House
	6 - Domestic Vacation
	7 - International Vacation
	8 - Self Higher Education
	9 - Child Education
*/

/* 
List of Frequency Description IDs:
	0 - Only Once (Default)
	1 - Once Every Year
	2 - Once in Two Years
	3 - Once in Three Years
	4 - Once in Four Years
	5 - Once in Five Years
*/

public class LifeGoalsInput {
	private int goalDescriptionId;
	private String goalDescription;
	private int yearofRealization;
	private int age;
	private int frequency;
	private String frequencyDesc;
	private String loanYesNo;
	private float estimatedAmount;
	private float inflatedAmount;
	
	
	public LifeGoalsInput(){
		// This is a default constructor
	}

	public int getGoalDescriptionId() {
		return goalDescriptionId;
	}

	public void setGoalDescriptionId(int goalDescriptionId) {
		this.goalDescriptionId = goalDescriptionId;
	}

	public String getGoalDescription() {
		return goalDescription;
	}

	public void setGoalDescription(String goalDescription) {
		this.goalDescription = goalDescription;
	}

	public int getYearofRealization() {
		return yearofRealization;
	}

	public void setYearofRealization(int yearofRealization) {
		this.yearofRealization = yearofRealization;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyDesc() {
		return frequencyDesc;
	}

	public void setFrequencyDesc(String frequencyDesc) {
		this.frequencyDesc = frequencyDesc;
	}

	public String getLoanYesNo() {
		return loanYesNo;
	}

	public void setLoanYesNo(String loanYesNo) {
		this.loanYesNo = loanYesNo;
	}

	public float getEstimatedAmount() {
		return estimatedAmount;
	}

	public void setEstimatedAmount(float estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}
	
	public float getInflatedAmount() {
		return inflatedAmount;
	}

	public void setInflatedAmount(float inflatedAmount) {
		this.inflatedAmount = inflatedAmount;
	}

	
	
}
