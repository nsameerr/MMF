package com.gtl.mmf.bao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.gtl.mmf.service.vo.financialplanner.FPResponseSet;
import com.gtl.mmf.service.vo.financialplanner.FinancialPlannerOutput;
import com.gtl.mmf.service.vo.financialplanner.LifeGoalsInput;
import com.gtl.mmf.service.vo.financialplanner.CurrentOutstandingLoans;
import com.gtl.mmf.bao.IFpMainCalculationBAO;
import com.gtl.mmf.bao.IFploansBAO;
import com.gtl.mmf.dao.IFinancialPlannerDAO;
import com.gtl.mmf.service.vo.financialplanner.assumptions.FpMasterAssumption;
import com.gtl.mmf.bao.IFpSetAssumptionsBAO;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;

public class FpMainCalculationBAOImpl implements IFpMainCalculationBAO{
	
	   private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.fpmaincalculations");
	
       private FPResponseSet fpresponseSet; 
       private FpMasterAssumption fpMasterAssumption;
       private List<FpFinancialAssetGrowthAssumptionTb> fpfinassetgrowthassumption;
       
       @Autowired
       private IFploansBAO fpLoans;
       @Autowired
       private IFpSetAssumptionsBAO fpsetassumptions;
	   @Autowired
	   private IFinancialPlannerDAO fpDao;
	   private  FinancialPlannerOutput fpOutput;
	   
	// Declaring necessary variables
	   private float[] selfSalary;
	   private float[] spouseSalary;
	   private float[] totalSalary; // salary details
	   
	   private float[] lifeStyleExpenditure; 
	   private float[] goalBasedExpenditure;
	   private float[] outstandingLoans;
	   private float[] healthInsurance;
	   private float[] lifeInsurance;  // detailed expenditure 
	   
	   private float[] totalExpenditure;
	   private float[] annualSavings;     // gross expenditure and savings details 
	   private float[] individualAssetGrowthTemp;
	   private float[] growthofInputFinAsset;	
	   
	   private float[] totalFinAssets;
	   private float[] investmentRelatedFinAssets;
	   private float[] newRealEstateAssetContribution;
	   private float[] assetWithMMFtillRetirement;
	   private float[] MMFreturns;
	   private float[] nonMMFassetTillRetirement;
	   private float[] nonMMFreturns;
	   
	   private float[] postRetirementAssetWDV;
	   private float[] postRetirementCash_cumulCashBalance;
	   private float[] postRetirementAnnuity;
	   
	   private float[] assetWithOnlyFD;
	   
	   private String customerId;
	   
	   // Getting current Year
	   Calendar now = Calendar.getInstance();   // Gets the current date and time
	   int currentYear = now.get(Calendar.YEAR);      // The current year as an int

	   
	   @Transactional
	   public void setResponses(FPResponseSet FpResponseInput)
	   {
		   fpresponseSet = FpResponseInput;
	   }

	   
	   @Transactional
	   public void setAssumptions(FpMasterAssumption fpmasterassumption)
	   {
		   try
		   {
		   fpMasterAssumption = fpmasterassumption;
	
		   int lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   int spouseAgeInput = fpresponseSet.getSpouseAge();
		   int selfAgeInput = fpresponseSet.getUserAge();
		   
		   if(selfAgeInput >= lifeExpectancy || spouseAgeInput >= lifeExpectancy)
		   {
			   if(selfAgeInput >= spouseAgeInput)
				   fpMasterAssumption.getFpmiscassumptions().setLifeExpectancy(selfAgeInput+10);
			   
			   else if(spouseAgeInput > selfAgeInput)
				   fpMasterAssumption.getFpmiscassumptions().setLifeExpectancy(spouseAgeInput+10);	   
		   }
		   
		   //Setting Fin Asset Assumption 
		   fpfinassetgrowthassumption = fpsetassumptions.assignDefaultFpAssetGrowthAssumption();
		   
		   }catch(Exception e){
			   LOGGER.log(Level.SEVERE,"Suman code - Catch error in setAssumptions in FpMainCalculationBAOImpl", e);
		   }
		   
	   }
	   
	   @Transactional
	   public void setcustomerId(String customerRegId)
	   {
		   customerId = customerRegId;
	   }
	   
	   
	   @Transactional   
	   public void Initialization()
	   {
		    int lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		    
            selfSalary = new float [lifeExpectancy + 1];
			spouseSalary = new float [lifeExpectancy + 1];
			totalSalary = new float [lifeExpectancy + 1];	

			lifeStyleExpenditure = new float [lifeExpectancy + 1];
			goalBasedExpenditure = new float [lifeExpectancy + 1];
			outstandingLoans = new float [lifeExpectancy + 1];
			healthInsurance = new float [lifeExpectancy + 1];
			lifeInsurance = new float [lifeExpectancy + 1];
			
			totalExpenditure = new float [lifeExpectancy + 1];
			annualSavings = new float [lifeExpectancy + 1];
			
			individualAssetGrowthTemp = new float [lifeExpectancy + 1];
			growthofInputFinAsset = new float [lifeExpectancy + 1];
			
			totalFinAssets = new float [lifeExpectancy + 1];
			investmentRelatedFinAssets = new float [lifeExpectancy + 1];
			newRealEstateAssetContribution = new float [lifeExpectancy + 1];
			assetWithMMFtillRetirement = new float [lifeExpectancy + 1];
			MMFreturns = new float [lifeExpectancy + 1];
			nonMMFassetTillRetirement = new float [lifeExpectancy + 1];
			nonMMFreturns = new float [lifeExpectancy + 1];
			
			postRetirementAssetWDV = new float [lifeExpectancy + 1];
			postRetirementCash_cumulCashBalance = new float [lifeExpectancy + 1];
			postRetirementAnnuity = new float [lifeExpectancy + 1];
			
			assetWithOnlyFD = new float [lifeExpectancy + 1];
			
			
			
			// assigning zero to all the elements of the arrays as initial values
			
			for(int ageCounter=0; ageCounter <= lifeExpectancy; ageCounter++)
			{
				selfSalary[ageCounter] = 0;
				spouseSalary[ageCounter] = 0;
				totalSalary[ageCounter] = 0;	

				lifeStyleExpenditure[ageCounter] = 0;
				goalBasedExpenditure[ageCounter] = 0;
				outstandingLoans[ageCounter] = 0;
				healthInsurance[ageCounter] = 0;
				lifeInsurance[ageCounter] = 0;
			
				totalExpenditure[ageCounter] = 0;
				annualSavings[ageCounter] = 0;
				
				individualAssetGrowthTemp[ageCounter] = 0;
				growthofInputFinAsset[ageCounter] = 0; 
			
				investmentRelatedFinAssets[ageCounter] = 0;
				assetWithMMFtillRetirement[ageCounter] = 0;
				newRealEstateAssetContribution[ageCounter] = 0;
				MMFreturns[ageCounter] = 0;
				nonMMFassetTillRetirement[ageCounter] = 0;
				nonMMFreturns[ageCounter] = 0;
				
				postRetirementAssetWDV[ageCounter] = 0;
				postRetirementCash_cumulCashBalance[ageCounter] = 0;
				postRetirementAnnuity[ageCounter] = 0;
				
				assetWithOnlyFD[ageCounter] = 0;
				
			}
			
	   
	   } 
	   
	   @Transactional
	   public float getIncrementRate(int ageCounter, int flag){
	
		   float incrementRate = 0;
		   int lowerLimit, upperLimit;
		   
		   try
		   {
		   for(int i=0; i<fpMasterAssumption.getFpsalaryexpenseincrement().size(); i++)
		   {
			   lowerLimit = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getLowerLimitAge();
			   upperLimit = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getUpperLimitAge();
			   
			   if(ageCounter >= lowerLimit && ageCounter < upperLimit)
			   {
				   if(flag == 1) // 1 -> selfSalaryIncrementRate
					   incrementRate = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getSelfSalaryIncreaseRate();
				   
				   else if(flag == 2) // 1 -> spouseSalaryIncrementRate 
					   incrementRate = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getSpouseSalaryIncreaseRate();
					   
				   else if(flag == 3)  // 1 -> expenseIncrementRate
					   incrementRate = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getExpenseIncreaseRate();   
			   
				   break;
			   }
		   }//end of for
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in getIncrementRate in FpmaincalculationBAOImpl", e);
			}
		   
		   return incrementRate;
	   }
	   
	   
	   @Transactional 
	   public void setSelfSalary()
	   {
		   float incrementRate = 0, selfTakeHomeSalary = 0;
		   int ageCounter, selfAgeInput = 0, retirementAge = 0, lifeExpectancy = 0;
		   
		   try
		   {
		   selfTakeHomeSalary = fpresponseSet.getUserMonthlyTakehomeSalary(); 
		   selfAgeInput = fpresponseSet.getUserAge();
		   retirementAge = fpMasterAssumption.getFpmiscassumptions().getRetirementAge();
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		  
		   selfSalary[selfAgeInput] = selfTakeHomeSalary * 12;
		   
		   for(ageCounter = selfAgeInput+1; ageCounter <= retirementAge; ageCounter++)
		   {
			   
              incrementRate = getIncrementRate(ageCounter, 1);
               	     	
              selfSalary[ageCounter] = (1+(incrementRate/100)) * selfSalary[ageCounter - 1]; 
			   				   
		   }
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setSelfSalary in FpmaincalculationBAOImpl", e);
			}
		   
		   // printing selfSalary values to log
		   /*
		   for(ageCounter = selfAgeInput; ageCounter <= lifeExpectancy; ageCounter++)
		   {
			   LOGGER.log(Level.INFO,"Suman code - check self age   " + ageCounter + "  salaryincrementrate  " + incrementRate + "  selfsalary  " +selfSalary[ageCounter]);
		   }*/
		   
	   }
	   
	   @Transactional
	   public void setSpouseSalary()
	   {
		   float incrementRate = 0, spouseTakeHomeSalary = 0;
		   int ageCounter = 0, spouseAgeInput = 0, retirementAge = 0, lifeExpectancy = 0;
		   
		   try
		   {
		   spouseTakeHomeSalary =fpresponseSet.getSpouseMonthlyTakehomeSalary(); 
		   spouseAgeInput = fpresponseSet.getSpouseAge();
		  //int selfAgeInput = fpresponseSet.getUserAge();
		   retirementAge = fpMasterAssumption.getFpmiscassumptions().getRetirementAge();
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   
		   spouseSalary[spouseAgeInput] = spouseTakeHomeSalary * 12;
		   //int arrayCounter = selfAgeInput + 1;
			
           for(ageCounter = spouseAgeInput+1; ageCounter <= retirementAge; ageCounter++)
           {
			    incrementRate = getIncrementRate(ageCounter, 2);
			    spouseSalary[ageCounter] = (1+(incrementRate/100)) * spouseSalary[ageCounter - 1];
                //spouseSalary[arrayCounter] = (1+(incrementRate/100)) * spouseSalary[arrayCounter - 1];
               // arrayCounter++; 				   
		   }	
           
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setSpouseSalary in FpmaincalculationBAOImpl", e);
			}
		   
		   // printing selfSalary values to log
		   /*
           for(ageCounter = spouseAgeInput; ageCounter <= lifeExpectancy; ageCounter++)
           {
        	   LOGGER.log(Level.INFO,"Suman code - check spouseAge  " +ageCounter + "  incrementrate  " + incrementRate + "  spousesalary  " +spouseSalary[ageCounter]);
           }*/
           
	   }
	   
	   
	  @Transactional
	   public void setTotalSalary()
	   {
		  int lifeExpectancy = 0, spouseAgeInput = 0; 
		  int selfAgeInput = 0, retirementAge = 0;
		  
		  try
		  {
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   spouseAgeInput = fpresponseSet.getSpouseAge();
		   selfAgeInput = fpresponseSet.getUserAge();
		   retirementAge = fpMasterAssumption.getFpmiscassumptions().getRetirementAge();
		   
		   // Insert selfSalary into TotalSalary
		   for(int ageCounter=0; ageCounter <= retirementAge ; ageCounter++)
		   {
			   totalSalary[ageCounter] = totalSalary[ageCounter] + selfSalary[ageCounter];  
			   //totalSalary[ageCounter] = selfSalary[ageCounter] + spouseSalary[ageCounter];
			   //LOGGER.log(Level.INFO,"Suman code - check Age  " +ageCounter + "  totalsalary  " +totalSalary[ageCounter]);
		   }
		   
		   // Insert spouseSalary into TotalSalary
		   int arrayCounter = selfAgeInput;
		   for(int ageCounter = spouseAgeInput; ageCounter <= retirementAge; ageCounter++)
		   {
			   if(arrayCounter > lifeExpectancy)
			   {
				   break;
			   }
			   totalSalary[arrayCounter] = totalSalary[arrayCounter] + spouseSalary[ageCounter];
			   arrayCounter++;
		   }
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setTotalSalary in FpmaincalculationBAOImpl", e);
			}
		   
		   //printing TotalSalary
		  /*
		   for(int ageCounter=0; ageCounter <= lifeExpectancy ; ageCounter++)
		   {
			   LOGGER.log(Level.INFO,"Suman code - check Age  " +ageCounter + "  totalsalary  " +totalSalary[ageCounter]);
		   }*/
		   
	   }
	   

	   @Transactional
	   public void totalOutstandingLoans()
	   {
		   int finalYear, ageCounter, selfAgeInput = 0, lifeExpectancy = 0;
		   float annualAmount;
		   
		   try
		   {
		   selfAgeInput = fpresponseSet.getUserAge();
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   
		   for(int i = 0; i < fpresponseSet.getOutstandingLoans().size(); i++)
		   {
			   finalYear = fpresponseSet.getOutstandingLoans().get(i).getFinalYearofPayment();
			   annualAmount = 12 * fpresponseSet.getOutstandingLoans().get(i).getemi();
			   ageCounter = selfAgeInput;
			   
			   for(int yearCounter = currentYear; yearCounter <= finalYear; yearCounter++)
			   {
				   if(ageCounter > lifeExpectancy)
				   {
					   break;
				   }
				   outstandingLoans[ageCounter] = outstandingLoans[ageCounter] + annualAmount;
				   ageCounter++;
			   }
		   }
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in totalOutstandingLoans in FpmaincalculationBAOImpl", e);
		   }
		   
		   
		   
		   // For Printing
		   //for(int  counter = 0; counter <= lifeExpectancy; counter++)
			   //LOGGER.log(Level.INFO,"Suman code - check Age  " +counter + "  outstandingLoans  " + outstandingLoans[counter]);
	   
	   }
	   
	   @Transactional
	   public float getFinAssetGrowthRate(int assetId)
	   {
		   float growthRate = 0;
		   
		   try
		   {
		   for(int i = 0; i < fpfinassetgrowthassumption.size(); i++)
		   {
			   if(assetId == fpfinassetgrowthassumption.get(i).getFinancialAssetId())
				   growthRate = fpfinassetgrowthassumption.get(i).getGrowthRate();
			   
		   }
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in getFinAssetGrowthRate in FpmaincalculationBAOImpl", e);
		   }
		   
		   return growthRate;
	   }
	   
	   
	   @Transactional 
	   public void inputFinAssetGrowth()
	   {
		   
		   int selfAgeInput = 0,lifeExpectancy = 0,assetId;
		   float growthRate = 0;
		   
		   try
		   {
		   selfAgeInput = fpresponseSet.getUserAge();
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   
		   for(int i = 0; i < fpresponseSet.getFinancialAssetList().size(); i++)
		   {
			   assetId = fpresponseSet.getFinancialAssetList().get(i).getAssetId();
			   
			   //Setting individualAssetGrowthTemp array to zero
			   for(int counter = 0; counter <= lifeExpectancy; counter++)
				   individualAssetGrowthTemp[counter] = 0;
			   
			   if(assetId != 1)  // assetId = 1 is for Cash Assets
			   {
				   individualAssetGrowthTemp[selfAgeInput] = fpresponseSet.getFinancialAssetList().get(i).getAssetAmount();
				   growthofInputFinAsset[selfAgeInput] = growthofInputFinAsset[selfAgeInput] + individualAssetGrowthTemp[selfAgeInput];
				   growthRate = getFinAssetGrowthRate(assetId);
				   LOGGER.log(Level.INFO,"Suman code - check growthRate FinAsset" + growthRate + "  assetid  "+assetId);
			   }  
			   
			   for(int ageCounter = selfAgeInput+1; ageCounter <= lifeExpectancy; ageCounter++)
			   {
				   individualAssetGrowthTemp[ageCounter] =  individualAssetGrowthTemp[ageCounter-1] * (1+(growthRate/100));
				   growthofInputFinAsset[ageCounter] = growthofInputFinAsset[ageCounter] + individualAssetGrowthTemp[ageCounter];   		     
			   }
			  
		   }// end of for loop of 'i'
		   
		    }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in inputFinAssetGrowth in FpmaincalculationBAOImpl", e);   
		   }
		   
	   
	   } //end of function
	   
	   
	   @Transactional
	   public void setLifestyleExpenditure()   
	   {
		   String relation = "";
		   int selfAgeInput = 0, spouseAgeInput = 0, retirementAge = 0, lifeExpectancy = 0;
		   float postRetirementRecurringExpense = 0, incrementRate = 0;
		   float savingsRate = 0, expenseRate = 0, totalSalaryInitial = 0;
		   float initialAnnualExpenditure = 0, initialLifeStyleExpenditure = 0;
				   
		   try
		   {
		   relation = fpresponseSet.getRelationStatus();
		   selfAgeInput = fpresponseSet.getUserAge(); 
		   spouseAgeInput = fpresponseSet.getSpouseAge();
		   retirementAge =  fpMasterAssumption.getFpmiscassumptions().getRetirementAge();
		   lifeExpectancy =  fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   postRetirementRecurringExpense = fpMasterAssumption.getFpmiscassumptions().getPostRetirementRecurringExpense(); 
		   incrementRate = 0;
		  
		   savingsRate = fpresponseSet.getSavingsRate();
		   expenseRate = 100 - savingsRate;
		   //totalSalaryInitial = selfSalary[selfAgeInput] + spouseSalary[selfAgeInput];
		   totalSalaryInitial = totalSalary[selfAgeInput];
		   initialAnnualExpenditure = (expenseRate/100) * totalSalaryInitial;  
		   initialLifeStyleExpenditure = initialAnnualExpenditure - outstandingLoans[selfAgeInput] - healthInsurance[selfAgeInput] - lifeInsurance[selfAgeInput];
		   //LOGGER.log(Level.INFO,"Suman code - check expensRate  " +expenseRate + "  initialannualexpense  " +initialAnnualExpenditure);
		   
		   lifeStyleExpenditure[selfAgeInput] = initialLifeStyleExpenditure;
		   
		   int spouseAgeCounter = spouseAgeInput;
		   
		   for(int ageCounter=selfAgeInput+1; ageCounter <= lifeExpectancy; ageCounter++)
		   {
			   
			    incrementRate = getIncrementRate(ageCounter, 3);
	    			
				lifeStyleExpenditure[ageCounter] = (1+(incrementRate/100)) * lifeStyleExpenditure[ageCounter-1];

                // just after retirement reducing the expense to a level as entered by the user - using postRetirementRecurringExpense variable	

				if(relation.equalsIgnoreCase("Single"))
				{
					if(ageCounter == (retirementAge + 1))
						lifeStyleExpenditure[ageCounter] = (postRetirementRecurringExpense/100) * lifeStyleExpenditure[ageCounter];
				}
				
				else if(relation.equalsIgnoreCase("Married")) 
				{
					if(ageCounter == (retirementAge + 1) || spouseAgeCounter == (retirementAge + 1))
						lifeStyleExpenditure[ageCounter] = (postRetirementRecurringExpense/100) * lifeStyleExpenditure[ageCounter];
				}
                    					
                //LOGGER.log(Level.INFO,"Suman code - check Age  " +ageCounter + "  Expenseincrementrate  " + incrementRate + "  lifestyleexpense  " +lifeStyleExpenditure[ageCounter]);
                spouseAgeCounter++;
                
		   }// end of for loop
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setLifestyleExpenditure in FpmaincalculationBAOImpl", e);  
		   }
		   
		// For Printing
		   //for(int  counter = 0; counter <= lifeExpectancy; counter++)
			   //LOGGER.log(Level.INFO,"Suman code - check Age  " +counter + "  lifeStyleExpenditure  " + lifeStyleExpenditure[counter]);
		   
	   }

	   @Transactional
	   public void setHealthInsuranceExpenditure()
	   {
		   int startYear = 0, finalYear = 0;
		   float annualAmountPayable;
		   
		   try
		   {
		   startYear = fpresponseSet.getUserAge();
		   finalYear = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   
		   
		   for(int i=0; i<fpresponseSet.getHealthInsuranceCover().size(); i++)
		   {
			   annualAmountPayable = fpresponseSet.getHealthInsuranceCover().get(i).getAnnualPremium();
			   
			   for(int ageCounter=startYear; ageCounter <= finalYear; ageCounter++)
			   {
				   healthInsurance[ageCounter] = healthInsurance[ageCounter] + annualAmountPayable;
			   }
				   
		   }
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setLifestyleExpenditure in FpmaincalculationBAOImpl", e);   
		   }
		   
		   // For Printing
		   //for(int  counter = 0; counter <= finalYear; counter++)
			   //LOGGER.log(Level.INFO,"Suman code - check Age  " +counter + "  healthInsuranceExpense  " + healthInsurance[counter]);
	   }
	   
	   @Transactional
	   public void setLifeInsuranceExpenditure()
	   {
		   int finalYear = 0, ageCounter = 0, lifeExpectancy = 0;
		   float annualAmountPayable = 0;
		   
		   try
		   {
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
				   
		   for(int i=0; i<fpresponseSet.getLifeInsuranceCover().size(); i++)
		   {
			   finalYear = fpresponseSet.getLifeInsuranceCover().get(i).getFinalYearofPayment();
			   annualAmountPayable = fpresponseSet.getLifeInsuranceCover().get(i).getAnnualPremium();
			   ageCounter = fpresponseSet.getUserAge();
			   
			   for(int yearCounter = currentYear; yearCounter <= finalYear; yearCounter++)
			   {
				   if(ageCounter > lifeExpectancy)
				   {
					   break;
				   }
				   
				   lifeInsurance[ageCounter] = lifeInsurance[ageCounter] + annualAmountPayable;
				   ageCounter++;
			   }
				   
		   }
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setLifeInsuranceExpenditure in FpmaincalculationBAOImpl", e);   
		   }
		   
		  //For Printing
		   //for(int  counter = 0; counter <= lifeExpectancy; counter++)
			   //LOGGER.log(Level.INFO,"Suman code - check Age  " +counter + "  lifeInsuranceExpense  " + lifeInsurance[counter]);
	   }
	   
	   @Transactional
	   public void calculateRealEstateAssetContribution(float estimatedAmountInflated, float interestRate, int loanDuration, float annualEMI, float loanDownpayment, int startAgeofEMIpayment)
	   {
		   float[] principalOwnership;
		   float[] returns;
		   float[] ppmt;
		   
		   principalOwnership = new float [loanDuration];
		   returns = new float [loanDuration];
		   ppmt = new float [loanDuration];
		   
		   // position 4 is for Total Real Estate Value
		   float growthRate = fpfinassetgrowthassumption.get(4).getGrowthRate();
		   
		   //Property principal ownership and returns array
		   principalOwnership[0] = estimatedAmountInflated;
		   returns[0] = (growthRate/100) * 	principalOwnership[0];	   
		   for(int counter=1; counter<loanDuration; counter++)
		   {
			   principalOwnership[counter] = principalOwnership[counter-1] + returns[counter-1];
			   returns[counter] = (growthRate/100) * 	principalOwnership[counter];		   
		   }

		   // PPMT calculation
		   float tempVar, term1, term2, monthlyPPMT, annualPPMT;
		   float loanPrincipal = estimatedAmountInflated - loanDownpayment;
		   float monthlyEMI = annualEMI/12;
		   float monthlyR = interestRate/(12*100);
		   float onePlusR = 1 + monthlyR;

		   for(int counter=0; counter<loanDuration; counter++)
		   {
			   annualPPMT = 0;
			   
			   for(int month=1; month<=12; month++)
			   {
				   tempVar = (float) Math.pow(onePlusR,month-1);
				   term1 = (monthlyEMI * (tempVar - 1)) / monthlyR;
				   term2 = loanPrincipal * tempVar;
				   monthlyPPMT = monthlyEMI + ( (term1 + term2) * monthlyR );
				   annualPPMT = annualPPMT + monthlyPPMT;
			   }
			   
			   ppmt[counter] = annualPPMT;
		   }
		   
		   // Populating the final data
		   int finalAgeofEMIpayment = startAgeofEMIpayment + loanDuration - 1;
		   int counter2 = 0;
		   for(int counter = startAgeofEMIpayment; counter <= finalAgeofEMIpayment; counter++)
		   {
			   newRealEstateAssetContribution[counter] = newRealEstateAssetContribution[counter] + returns[counter2] + ppmt[counter2];
			   counter2++;
		   }
	   }
	   
	   @Transactional
	   public void setGoalsExpenditure()	 
	   {
		   int yearOfRealization = 0, startAgeofEMIpayment, finalAgeofEMIpayment = 0;
		   int goalID, loanId, loanDuration = 0, frequencyId = 0, ageCounter, displayInflatedAmount; 
		   float downpaymentRate = 0, downpayment, estimatedAmount=0; 
		   float principal, annualEMI = 0, estimatedAmountInflated = 0;
		   float interestRate = 0, inflationRate = 0;
		   String loanYesNo;
		   int noOfgoals = 0, userAge = 0, lifeExpectancy = 0, retirementAge = 0; 
				   
		   try
		   {
		   inflationRate = fpMasterAssumption.getFpmiscassumptions().getLongTermInflationExpectation();
		   noOfgoals = fpresponseSet.getLifeGoals().size();
		   userAge = fpresponseSet.getUserAge();
		   retirementAge =  fpMasterAssumption.getFpmiscassumptions().getRetirementAge();
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		  // LOGGER.log(Level.INFO,"Suman code - check userAge" + userAge);
		 //  LOGGER.log(Level.INFO,"Suman code - check LifeExpectancy" + lifeExpectancy);	
		   
		   for(int i=0; i<noOfgoals; i++)
		   {
			   goalID = fpresponseSet.getLifeGoals().get(i).getGoalDescriptionId();
			   
			  // LOGGER.log(Level.INFO,"Suman code - check goalId" + goalID);
			  // LOGGER.log(Level.INFO,"Suman code - check goalIname" + fpresponseSet.getLifeGoals().get(i).getGoalDescription());
			   
			   loanYesNo = fpresponseSet.getLifeGoals().get(i).getLoanYesNo();
			   estimatedAmount = fpresponseSet.getLifeGoals().get(i).getEstimatedAmount();
			   yearOfRealization = fpresponseSet.getLifeGoals().get(i).getYearofRealization();
			   estimatedAmountInflated = fpLoans.getFutureInflatedAmount(estimatedAmount, inflationRate, yearOfRealization);
			   displayInflatedAmount = (int)(estimatedAmountInflated/1000);
			   displayInflatedAmount = displayInflatedAmount * 1000;
			   fpresponseSet.getLifeGoals().get(i).setInflatedAmount((float)displayInflatedAmount);
			   frequencyId = fpresponseSet.getLifeGoals().get(i).getFrequency();
			   fpresponseSet.getLifeGoals().get(i).setFrequencyDesc(fpDao.getFrequencyDescription(frequencyId));
			   
			  // LOGGER.log(Level.INFO,"Suman code" + estimatedAmount + "yoR:"+yearOfRealization + "estimatedInflated: " + estimatedAmountInflated);
			   
			   
			   /************************ Calculating the Down-payment Amount *****************/
			   /*****************************************************************************/
			   
			   loanId = fpDao.getGoalLoanMappingFromTb(goalID);
			   //LOGGER.log(Level.INFO,"Suman code - check loanId" + loanId);
			   
			  for(int counter=0; counter < fpMasterAssumption.getFploanassumptions().size(); counter++ )
			   {
				   if(fpMasterAssumption.getFploanassumptions().get(counter).getLoanAssumptionId() == loanId)
				   {
					   downpaymentRate = fpMasterAssumption.getFploanassumptions().get(counter).getDownPaymentPercent(); 
					   break;
				   }
			   }
			
			   //LOGGER.log(Level.INFO,"Suman code - check downpaymentRate" + downpaymentRate);
			   
			   downpayment = fpLoans.getDownpayment(loanYesNo, estimatedAmountInflated, downpaymentRate);
			   //LOGGER.log(Level.INFO,"Suman code - check downpayment" + downpayment);
			   
			   
			   /************************** Calculating Annual EMI ******************/
			   /*******************************************************************/
			   
			   startAgeofEMIpayment = fpLoans.getStartAgeofEMIpayment(yearOfRealization, userAge);
			   fpresponseSet.getLifeGoals().get(i).setAge(startAgeofEMIpayment);
			   //LOGGER.log(Level.INFO,"Suman code - check startAgeofEMIpayment" + startAgeofEMIpayment);
			   
			   if(loanYesNo.equalsIgnoreCase ("Yes") || loanYesNo.equalsIgnoreCase ("true"))
			   {
				   principal = estimatedAmountInflated - downpayment;
				   //LOGGER.log(Level.INFO,"Suman code - check principalAmount" + principal);
				
				   for(int counter=0; counter < fpMasterAssumption.getFploanassumptions().size(); counter++ )
				   {
					   if(fpMasterAssumption.getFploanassumptions().get(counter).getLoanAssumptionId() == loanId)
					   {
						   interestRate = fpMasterAssumption.getFploanassumptions().get(counter).getInterestRate();
						   loanDuration = fpMasterAssumption.getFploanassumptions().get(counter).getLoanDuration();
						   break;
					   }
						   
				   }
				   
				  
				   finalAgeofEMIpayment = startAgeofEMIpayment + loanDuration - 1;
				   
				   // Not allowing loan to go beyond retirementAge
				   if(finalAgeofEMIpayment > retirementAge)
				   {
					   finalAgeofEMIpayment = retirementAge;
					   loanDuration = retirementAge - startAgeofEMIpayment + 1;
				   }
				   
				   //LOGGER.log(Level.INFO,"Suman code - check interestRate" + interestRate);
				   //LOGGER.log(Level.INFO,"Suman code - check loanDuration" + loanDuration);
				   
				   annualEMI = fpLoans.getAnnualEMI(principal, interestRate, loanDuration);
				   
				   //LOGGER.log(Level.INFO,"Suman code - check annualEMI" + annualEMI);
				   
			   }
			   
			   else if(loanYesNo.equalsIgnoreCase("No") || loanYesNo.equalsIgnoreCase ("false"))
			   {
				   annualEMI = 0;
				   finalAgeofEMIpayment = startAgeofEMIpayment;
			   }
			   
			   //LOGGER.log(Level.INFO,"Suman code - check finalAgeofEMIpayment" + finalAgeofEMIpayment);
			   
			   //**************** Asset Value Due to Real Estate Bought *****************//
			   
			   //if(goalID == 5)  // goalId 5 is for House
			   //{
				   //LOGGER.log(Level.INFO,"Suman code - check EMI" + annualEMI +"  downpayment  "+downpayment);
				   //calculateRealEstateAssetContribution(estimatedAmountInflated, interestRate, loanDuration, annualEMI, downpayment, startAgeofEMIpayment);
			   //}
			   //**************** Populating Loans Expenditure Array *******************//
			 
			   if(frequencyId == 0)
			   {
				   goalBasedExpenditure[startAgeofEMIpayment] = goalBasedExpenditure[startAgeofEMIpayment] + downpayment;
				   
				   for(ageCounter=startAgeofEMIpayment; ageCounter<=finalAgeofEMIpayment; ageCounter++)
				   {
					   if(ageCounter > lifeExpectancy)
					   {
						   break;
					   }
					   
					   goalBasedExpenditure[ageCounter] = goalBasedExpenditure[ageCounter] + annualEMI;
				   }
			   }
			   
			   else
			   {
				   goalBasedExpenditure[startAgeofEMIpayment] = goalBasedExpenditure[startAgeofEMIpayment] + downpayment;
				   
				   for(ageCounter=startAgeofEMIpayment+1; ageCounter<=lifeExpectancy; ageCounter++)
				   {
					   int tempVar = (ageCounter-startAgeofEMIpayment) % frequencyId;
					   if(tempVar == 0)
					   {
						   goalBasedExpenditure[ageCounter] = goalBasedExpenditure[ageCounter] + downpayment; 
					   }
					   
					   //ageCounter = ageCounter + frequencyId; 
				   }
			   }//end of else
			  
		   }// end of for loop for goals
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setGoalsExpenditure in FpmaincalculationBAOImpl", e);   
		   }
		   
		  //for(int i=0; i<=lifeExpectancy;i++)
			 //LOGGER.log(Level.INFO,"Suman code Print goal expenditure - age" + i+"  goalBasedExpenditure  "+goalBasedExpenditure[i]);
		   
		   
	   }// end of function
	   
	   @Transactional
	   public void setTotalExpenditure()
	   {
		   int lifeExpectancy = 0;
		   
		   try
		   {
		   lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
				   
		   for(int ageCounter=0; ageCounter <= lifeExpectancy; ageCounter++)
		   {
			   totalExpenditure[ageCounter] = lifeStyleExpenditure[ageCounter] + outstandingLoans[ageCounter] + goalBasedExpenditure[ageCounter] + healthInsurance[ageCounter] + lifeInsurance[ageCounter];
			   //LOGGER.log(Level.INFO,"Suman code - check Age  " + ageCounter+ "  totalExpenditure  " + totalExpenditure[ageCounter]);
		   }
		   
		   }catch(Exception e){
				LOGGER.log(Level.SEVERE,"Suman code - Catch error in setTotalExpenditure in FpmaincalculationBAOImpl", e);   
		   }
		   
	   }
	   
	  
	  /* public void setAnnualSavings() 
	   {
		   int lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   
		   for(int ageCounter=0; ageCounter <= lifeExpectancy; ageCounter++)
		   {
			  annualSavings[ageCounter] = totalSalary[ageCounter] - totalExpenditure[ageCounter];
			  LOGGER.log(Level.INFO,"Suman code - check Age  " + ageCounter+ "  annualSavings  " + annualSavings[ageCounter]);
		   }  
		  
	   } */
	   
	   @Transactional
	   public void calculateAssets()
	   {
		   int selfAgeInput = fpresponseSet.getUserAge();
		   int retirementAge = fpMasterAssumption.getFpmiscassumptions().getRetirementAge();  
		   int lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   
		   float investmentToMMF = fpMasterAssumption.getFpmiscassumptions().getAmountInvestedToMmf();
		   float nonMMFInvestment = 100 - investmentToMMF;
		   
		   //LOGGER.log(Level.INFO,"Suman code - check investmentToMMF  " + investmentToMMF);
		   //LOGGER.log(Level.INFO,"Suman code - check nonMMFInvestment " + nonMMFInvestment);
		   
		   int riskprofileId = fpresponseSet.getRiskGroupId();
		   float MMFreturnPercentage = fpDao.getMMFReturns(riskprofileId);
		   float nonMMFreturnPercentage = fpMasterAssumption.getFpmiscassumptions().getRateOfGrowthOfFd();
		 		   
		   // Post Retirement Annuity Return Percent
		   int conservativeRiskProfileId = 1;
		   float postRetirementAnnuityReturn = fpDao.getMMFReturns(conservativeRiskProfileId);
		   // Reducing returns to 75% to consider post-tax returns
		   postRetirementAnnuityReturn = (float)0.75 * postRetirementAnnuityReturn; 
		   float initialCashBalance = 0;
		   float MMFAssetTempVar, nonMMFAssetTempVar, postRetirementAnnuityAmount, retirementCorpus;
		   //int annuityDuration;
		   
		   LOGGER.log(Level.INFO,"Suman code - check MMF Return  " + MMFreturnPercentage);
		   LOGGER.log(Level.INFO,"Suman code - check Non MMF Return  " + nonMMFreturnPercentage);
		   //LOGGER.log(Level.INFO,"Suman code - check Post Retirement Annuity Return  " + postRetirementAnnuityReturn);	
		   
		   // Getting initial Cash Bank Balance
		   for(int i = 0; i< fpresponseSet.getFinancialAssetList().size(); i++)
		   {
			   if(fpresponseSet.getFinancialAssetList().get(i).getAssetId() == 1)
			   {
				   initialCashBalance = fpresponseSet.getFinancialAssetList().get(i).getAssetAmount(); 
			   }
			   		     
		    }
		   
		   //LOGGER.log(Level.INFO,"Suman code - check initialCashBalance  " + initialCashBalance);

		   // ASSIGNING INITIAL CONDITIONS 
		   annualSavings[selfAgeInput] = totalSalary[selfAgeInput] - totalExpenditure[selfAgeInput];
		   
		   investmentRelatedFinAssets[selfAgeInput] = initialCashBalance + annualSavings[selfAgeInput];
		   totalFinAssets[selfAgeInput] = investmentRelatedFinAssets[selfAgeInput] + growthofInputFinAsset[selfAgeInput];
		   
		   //calculate 80% of all
		   MMFAssetTempVar = ((investmentToMMF/100)*initialCashBalance) + ((investmentToMMF/100)*annualSavings[selfAgeInput]);
		   
		   if(MMFAssetTempVar > 0)
				assetWithMMFtillRetirement[selfAgeInput] = MMFAssetTempVar;
			
		   else
                assetWithMMFtillRetirement[selfAgeInput] = 0;
			
		 //calculate 80% of all for FD
		   nonMMFAssetTempVar = ( (nonMMFInvestment/100)*initialCashBalance ) + ( (nonMMFInvestment/100)*annualSavings[selfAgeInput] );
		   
		   if(nonMMFAssetTempVar > 0)
			    nonMMFassetTillRetirement[selfAgeInput] = nonMMFAssetTempVar;
			
		   else
                nonMMFassetTillRetirement[selfAgeInput] = 0;			   
			
			
			// CALCULATION TILL RETIREMENT
			
			for(int ageCounter = selfAgeInput+1; ageCounter <= retirementAge; ageCounter++)
			{
				
				/* *******************  Savings Calculation ***********************************
				 *************************************************************************** */
				annualSavings[ageCounter] = totalSalary[ageCounter] - totalExpenditure[ageCounter];
				
				/* *******************  MMF Return Calculation ********************************
				   ****************************************************************************/ 
				
				if(assetWithMMFtillRetirement[ageCounter-1] > 0)
					MMFreturns[ageCounter] = (MMFreturnPercentage/100) * assetWithMMFtillRetirement[ageCounter-1];
				
				else
					MMFreturns[ageCounter] = 0;
				
				
				/* ******************** Non MMF Return Calculation ****************************
				   ****************************************************************************/
				
				if(nonMMFassetTillRetirement[ageCounter-1] > 0)
					nonMMFreturns[ageCounter] = (nonMMFreturnPercentage/100) * nonMMFassetTillRetirement[ageCounter-1];
				
				else
					nonMMFreturns[ageCounter] = 0;
				
				
				/* ********************** INVESTMENT TO MMF ***********************************
				   *************** ASSET WITH MMF TILL RETIREMENT *****************************/
				
				assetWithMMFtillRetirement[ageCounter] = assetWithMMFtillRetirement[ageCounter-1] + MMFreturns[ageCounter] + ((investmentToMMF/100)*annualSavings[ageCounter]);
				
				
				/* ********************** NON MMF INVESTMENT **********************************
				   **************** ASSET NON MMF TILL RETIREMENT *****************************/
				
				nonMMFassetTillRetirement[ageCounter] = nonMMFassetTillRetirement[ageCounter-1] + nonMMFreturns[ageCounter] + ( (nonMMFInvestment/100)*annualSavings[ageCounter] );
                 			
				investmentRelatedFinAssets[ageCounter] = assetWithMMFtillRetirement[ageCounter] + nonMMFassetTillRetirement[ageCounter];
				totalFinAssets[ageCounter] = investmentRelatedFinAssets[ageCounter] + growthofInputFinAsset[ageCounter] + newRealEstateAssetContribution[ageCounter];
				
				postRetirementAssetWDV[ageCounter] = investmentRelatedFinAssets[ageCounter-1];
				
				
			}
			
			
			/* ********************  POST RETIREMENT CALCULATIONS ***************************** 
			   ******************************************************************************** */
			   
			   //retirementCorpus = investmentRelatedFinAssets[retirementAge];
			   //annuityDuration = lifeExpectancy - retirementAge;
			   //postRetirementAnnuityAmount = fpLoans.getAnnualEMI(retirementCorpus, postRetirementAnnuityReturn, annuityDuration);
			   
			   //LOGGER.log(Level.INFO,"Suman code - check retirementCorpus  " + retirementCorpus);
			   //LOGGER.log(Level.INFO,"Suman code - check annuityDuration  " + annuityDuration);
			   //LOGGER.log(Level.INFO,"Suman code - check postRetirementAnnuityAmount  " + postRetirementAnnuityAmount);
			   
			   for(int ageCounter = retirementAge+1; ageCounter <= lifeExpectancy; ageCounter++)
			   {
				   //postRetirementAnnuity[ageCounter] = postRetirementAnnuityAmount;
				   
				   //totalSalary[ageCounter] = selfSalary[ageCounter] + spouseSalary[ageCounter] + postRetirementAnnuityAmount;
				   //annualSavings[ageCounter] = totalSalary[ageCounter] - totalExpenditure[ageCounter];
					
				   //postRetirementAssetWDV[ageCounter] = (postRetirementAssetWDV[ageCounter-1] - postRetirementAnnuity[ageCounter]) * (1 + (postRetirementAnnuityReturn/100));
				   
				   //postRetirementCash_cumulCashBalance[ageCounter] = postRetirementCash_cumulCashBalance[ageCounter-1] + annualSavings[ageCounter];
				   
				   investmentRelatedFinAssets[ageCounter] = ( (investmentRelatedFinAssets[ageCounter-1]+totalSalary[ageCounter]) * (1 + (postRetirementAnnuityReturn/100)) ) - totalExpenditure[ageCounter];
				   totalFinAssets[ageCounter] = investmentRelatedFinAssets[ageCounter] + growthofInputFinAsset[ageCounter] + newRealEstateAssetContribution[ageCounter];
				   
			   }
			   
			   
			   // for printing
			  /* for(int ageCounter = 0; ageCounter <= lifeExpectancy; ageCounter++)
			   {
				   LOGGER.log(Level.INFO,"Suman code - check age "+ageCounter+ "  totalsalary  " + totalSalary[ageCounter]+"  savings  "+annualSavings[ageCounter]);
			   }
			   
			   for(int ageCounter = 0; ageCounter <= lifeExpectancy; ageCounter++)
			   {
				   LOGGER.log(Level.INFO,"Suman code - check age "+ageCounter+ "  assetWithMMFtillRetirement  " + assetWithMMFtillRetirement[ageCounter]+"  nonMMFassetTillRetirement  " + nonMMFassetTillRetirement[ageCounter]);
				   LOGGER.log(Level.INFO,"Suman code - check age "+ageCounter+ "  MMFreturns  " + MMFreturns[ageCounter]+"  nonMMFreturns  " + nonMMFreturns[ageCounter]);
				   LOGGER.log(Level.INFO,"Suman code - check age "+ageCounter+ "  postRetirementAssetWDV  " + postRetirementAssetWDV[ageCounter]);
				   LOGGER.log(Level.INFO,"Suman code - check age "+ageCounter+ "  postRetirementCash_cumulCashBalance  " + postRetirementCash_cumulCashBalance[ageCounter]);
				   LOGGER.log(Level.INFO,"Suman code - check age "+ageCounter+ "  postRetirementAnnuity  " + postRetirementAnnuity[ageCounter]);
			   }*/
			  		   
	   } // End of function
	   
	  // checks if the user has edited the default assumptions or not
	   
	   @Transactional
	   public void calculateAssetsWithOnlyFDInvestment()
	   {
		   int selfAgeInput = fpresponseSet.getUserAge();
		   int retirementAge = fpMasterAssumption.getFpmiscassumptions().getRetirementAge();  
		   int lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   float investmentToMMF = 0;
		   float nonMMFInvestment = 100 - investmentToMMF;

		   int riskprofileId = fpresponseSet.getRiskGroupId();
		   float MMFreturnPercentage = fpDao.getMMFReturns(riskprofileId);
		   float nonMMFreturnPercentage = fpMasterAssumption.getFpmiscassumptions().getRateOfGrowthOfFd();
		 		   
		   // Post Retirement Annuity Return Percent
		   int conservativeRiskProfileId = 1;
		   float postRetirementAnnuityReturn = fpDao.getMMFReturns(conservativeRiskProfileId);
		   // Reducing returns to 75% to consider post-tax returns
		   postRetirementAnnuityReturn = (float)0.75 * postRetirementAnnuityReturn; 
		   float initialCashBalance = 0;
		   float MMFAssetTempVar, nonMMFAssetTempVar;
		  
		   // Getting initial Cash Bank Balance
		   for(int i = 0; i< fpresponseSet.getFinancialAssetList().size(); i++)
		   {
			   if(fpresponseSet.getFinancialAssetList().get(i).getAssetId() == 1)
			   {
				   initialCashBalance = fpresponseSet.getFinancialAssetList().get(i).getAssetAmount(); 
			   }
			   		     
		    }
		

		   // ASSIGNING INITIAL CONDITIONS 
		   annualSavings[selfAgeInput] = totalSalary[selfAgeInput] - totalExpenditure[selfAgeInput];
		   
		   investmentRelatedFinAssets[selfAgeInput] = initialCashBalance + annualSavings[selfAgeInput];
		   assetWithOnlyFD[selfAgeInput] = investmentRelatedFinAssets[selfAgeInput] + growthofInputFinAsset[selfAgeInput] ;
		   
		   
		   MMFAssetTempVar = ((investmentToMMF/100)*initialCashBalance) + ((investmentToMMF/100)*annualSavings[selfAgeInput]);
		   
		   if(MMFAssetTempVar > 0)
				assetWithMMFtillRetirement[selfAgeInput] = MMFAssetTempVar;
			
		   else
                assetWithMMFtillRetirement[selfAgeInput] = 0;
			
			
		   nonMMFAssetTempVar = ( (nonMMFInvestment/100)*initialCashBalance ) + ( (nonMMFInvestment/100)*annualSavings[selfAgeInput] );
		   
		   if(nonMMFAssetTempVar > 0)
			    nonMMFassetTillRetirement[selfAgeInput] = nonMMFAssetTempVar;
			
		   else
                nonMMFassetTillRetirement[selfAgeInput] = 0;			   
			
			
			// CALCULATION TILL RETIREMENT
			
			for(int ageCounter = selfAgeInput+1; ageCounter <= retirementAge; ageCounter++)
			{
				
				/* *******************  Savings Calculation ***********************************
				 *************************************************************************** */
				annualSavings[ageCounter] = totalSalary[ageCounter] - totalExpenditure[ageCounter];
				
				/* *******************  MMF Return Calculation ********************************
				   ****************************************************************************/ 
				
				if(assetWithMMFtillRetirement[ageCounter-1] > 0)
					MMFreturns[ageCounter] = (MMFreturnPercentage/100) * assetWithMMFtillRetirement[ageCounter-1];
				
				else
					MMFreturns[ageCounter] = 0;
				
				
				/* ******************** Non MMF Return Calculation ****************************
				   ****************************************************************************/
				
				if(nonMMFassetTillRetirement[ageCounter-1] > 0)
					nonMMFreturns[ageCounter] = (nonMMFreturnPercentage/100) * nonMMFassetTillRetirement[ageCounter-1];
				
				else
					nonMMFreturns[ageCounter] = 0;
				
				
				/* ********************** INVESTMENT TO MMF ***********************************
				   *************** ASSET WITH MMF TILL RETIREMENT *****************************/
				
				assetWithMMFtillRetirement[ageCounter] = assetWithMMFtillRetirement[ageCounter-1] + MMFreturns[ageCounter] + ((investmentToMMF/100)*annualSavings[ageCounter]);
				
				
				/* ********************** NON MMF INVESTMENT **********************************
				   **************** ASSET NON MMF TILL RETIREMENT *****************************/
				
				nonMMFassetTillRetirement[ageCounter] = nonMMFassetTillRetirement[ageCounter-1] + nonMMFreturns[ageCounter] + ( (nonMMFInvestment/100)*annualSavings[ageCounter] );
                 			
				investmentRelatedFinAssets[ageCounter] = assetWithMMFtillRetirement[ageCounter] + nonMMFassetTillRetirement[ageCounter];
				assetWithOnlyFD[ageCounter] = investmentRelatedFinAssets[ageCounter] + growthofInputFinAsset[ageCounter] + newRealEstateAssetContribution[ageCounter];
				
				postRetirementAssetWDV[ageCounter] = investmentRelatedFinAssets[ageCounter-1];
				
				
			}
			
			
			/* ********************  POST RETIREMENT CALCULATIONS ***************************** 
			   ******************************************************************************** */
			   
			   
			   for(int ageCounter = retirementAge+1; ageCounter <= lifeExpectancy; ageCounter++)
			   {
				   investmentRelatedFinAssets[ageCounter] = ( (investmentRelatedFinAssets[ageCounter-1]+totalSalary[ageCounter]) * (1 + (postRetirementAnnuityReturn/100)) ) - totalExpenditure[ageCounter];
				   assetWithOnlyFD[ageCounter] = investmentRelatedFinAssets[ageCounter] + growthofInputFinAsset[ageCounter] + newRealEstateAssetContribution[ageCounter];
			   }
			   
			   // for printing
			  /* 
			   for(int ageCounter = 0; ageCounter <= lifeExpectancy; ageCounter++)
			   {
				   LOGGER.log(Level.INFO,"Suman code - check age "+ageCounter+ "  assetWithOnlyFD  " + assetWithOnlyFD[ageCounter]);
			   }*/
	   }
	   
	  @Transactional
	  public void defaultAssumptionsEditCheck()
	  {
		  int flag, size;
		  
		  // Check for fp_misc_assumptions_tb
		  FpMiscAssumptionsTb fpmiscassumptionstb_temp;
		  fpmiscassumptionstb_temp = fpsetassumptions.assignDefaultFpMiscAssumptions();
		  while(true)
		  {
			  flag = 0;
			  
			  if(fpMasterAssumption.getFpmiscassumptions().getRetirementAge() != fpmiscassumptionstb_temp.getRetirementAge())
			  {
				  flag = 1;
				  break;
			  }
			  
			  if(fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy() != fpmiscassumptionstb_temp.getLifeExpectancy())
			  {
				  flag = 1;
				  break;
			  }
			  
			  if(fpMasterAssumption.getFpmiscassumptions().getAmountInvestedToMmf() != fpmiscassumptionstb_temp.getAmountInvestedToMmf())
			  {
				  flag = 1;
				  break;
			  }
			  
			  if(fpMasterAssumption.getFpmiscassumptions().getRateOfGrowthOfFd() != fpmiscassumptionstb_temp.getRateOfGrowthOfFd())
			  {
				  flag = 1;
				  break;
			  }
			  
			  if(fpMasterAssumption.getFpmiscassumptions().getLongTermInflationExpectation() != fpmiscassumptionstb_temp.getLongTermInflationExpectation())
			  {
				  flag = 1;
				  break;
			  }
			  
			  if(fpMasterAssumption.getFpmiscassumptions().getPostRetirementRecurringExpense() != fpmiscassumptionstb_temp.getPostRetirementRecurringExpense())
			  {
				  flag = 1;
				  break;
			  }
			  
			  break;
			    
		  }// end of while loop
		  
		  if(flag == 1) // flag = 1 means Assumptions Edited
			  fpDao.saveFpMiscAssumptionsToDatabase(fpMasterAssumption.getFpmiscassumptions(), customerId, flag);
		  
		  //else if(flag == 0) // flag = 0 means No Edit
			  //fpDao.saveFpMiscAssumptionsToDatabase(fpmiscassumptionstb_temp, customerId, flag);
		 
				  
		 
		  //Re-setting flag = 0 for check again
		  flag = 0;
		  
		  //Check for fp_loan_related_assumption_tb 
		  List<FpLoanRelatedAssumptionsTb> fploanrelatedassumptionstb_temp;
		  fploanrelatedassumptionstb_temp = fpsetassumptions.assignDefaultFpLoanAssumption();
		  size = fpMasterAssumption.getFploanassumptions().size();
		  
		  for(int i=0; i<size; i++)
		  {
			  int loanId = fpMasterAssumption.getFploanassumptions().get(i).getLoanAssumptionId();
			  int loanDuration = fpMasterAssumption.getFploanassumptions().get(i).getLoanDuration();
			  float interestRate = fpMasterAssumption.getFploanassumptions().get(i).getInterestRate();
			  float downpaymentPerent = fpMasterAssumption.getFploanassumptions().get(i).getDownPaymentPercent();
			  
			  if( loanId == fploanrelatedassumptionstb_temp.get(i).getLoanAssumptionId())
			  {
				  if(loanDuration != fploanrelatedassumptionstb_temp.get(i).getLoanDuration())
				  {
					  flag = 1;
					  break;
				  }
				  
				  if(interestRate != fploanrelatedassumptionstb_temp.get(i).getInterestRate())
				  {
					  flag = 1;
					  break;
				  }
				  
				  if(downpaymentPerent != fploanrelatedassumptionstb_temp.get(i).getDownPaymentPercent())
				  {
					  flag = 1;
					  break;
				  }
			  }
		  }
		  
		  if(flag == 1) // flag = 1 means Assumptions Edited
			  fpDao.saveFpLoanAssumptionsToDatabase(fpMasterAssumption.getFploanassumptions(), customerId);
		  
		  //else if(flag == 0) // flag = 0 means No Edit
			  //fpDao.saveFpLoanAssumptionsToDatabase(fploanrelatedassumptionstb_temp, customerId);
		  
		  
		  //Re-setting flag = 0 for check again
		  flag = 0;
		  
		  //Check for fp_salary_expense_increment_assumption_tb 
		  List<FpSalaryExpenseIncrementAssumptionsTb> fpsalaryexpenseincrementassumptionstb_temp;
		  fpsalaryexpenseincrementassumptionstb_temp = fpsetassumptions.assignDefaultFpSalaryExpenseIncrementAssumption();
		  size = fpMasterAssumption.getFpsalaryexpenseincrement().size();
		  
		  for(int i=0; i<size; i++)
		  {
			  int lowerLimitAge = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getLowerLimitAge();
			  int upperLimitAge = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getUpperLimitAge();
			  float selfSalaryIncreaseRate = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getSelfSalaryIncreaseRate();
			  float spouseSalaryIncreaseRate = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getSpouseSalaryIncreaseRate();
			  float expenseIncreaseRate = fpMasterAssumption.getFpsalaryexpenseincrement().get(i).getExpenseIncreaseRate();
		      
			  if(lowerLimitAge == fpsalaryexpenseincrementassumptionstb_temp.get(i).getLowerLimitAge() && upperLimitAge == fpsalaryexpenseincrementassumptionstb_temp.get(i).getUpperLimitAge())
			  {
				  if(selfSalaryIncreaseRate != fpsalaryexpenseincrementassumptionstb_temp.get(i).getSelfSalaryIncreaseRate())
				  {
					  flag = 1;
					  break;
				  }
				  
				  if(spouseSalaryIncreaseRate != fpsalaryexpenseincrementassumptionstb_temp.get(i).getSpouseSalaryIncreaseRate())
				  {
					  flag = 1;
					  break;
				  }
				  
				  if(expenseIncreaseRate != fpsalaryexpenseincrementassumptionstb_temp.get(i).getExpenseIncreaseRate())
				  {
					  flag = 1;
					  break;
				  }
			  }
		  }
		  
		  
		  if(flag == 1) // flag = 1 means Assumptions Edited
			  fpDao.saveFpSalaryExpenseIncrementAssumptionsToDatabase(fpMasterAssumption.getFpsalaryexpenseincrement(), customerId);
		  
		  //else if(flag == 0) // flag = 0 means No Edit
			  //fpDao.saveFpSalaryExpenseIncrementAssumptionsToDatabase(fpsalaryexpenseincrementassumptionstb_temp, customerId);
		 
	 
	  }//end of function
	   
	   @Transactional
	   public FinancialPlannerOutput getFinancialPlannerOutput()
	   { 
		   int ageCounter, arraySize;
		   int userAge = fpresponseSet.getUserAge();
		   int lifeExpectancy = fpMasterAssumption.getFpmiscassumptions().getLifeExpectancy();
		   int [] ageOutput;
		   float [] totalFinAssetsOutput;
		   float [] totalFinAssetsWithOnlyFDOutput;
		   float [] totalLiabilitiesOutput;
		   int[] statusCheck;
		   int[] statusCheckforAssetLineWithFDOnly;
		   int[] year;
		   
		   arraySize = lifeExpectancy - userAge + 1;
		   
		   //LOGGER.log(Level.INFO,"Suman code - arraySize is " + arraySize);
		   //LOGGER.log(Level.INFO,"Suman code - userAge is " + userAge); 
		   // Calling functions one by one to calculate the Fp output
		   Initialization();
		   setSelfSalary();
		   setSpouseSalary();
		   setTotalSalary();
		   totalOutstandingLoans();
		   setHealthInsuranceExpenditure();
		   setLifeInsuranceExpenditure();
		   setLifestyleExpenditure();
		   setGoalsExpenditure();
		   setTotalExpenditure();
		   inputFinAssetGrowth();
		   //setAnnualSavings();
		   calculateAssets();
		   calculateAssetsWithOnlyFDInvestment();
		   
		   //Saving calculated data to new arrays to be passed on to controller
		
		   
		   ageOutput = new int [arraySize];
		   totalFinAssetsOutput = new float [arraySize];
		   totalFinAssetsWithOnlyFDOutput = new float [arraySize];
		   totalLiabilitiesOutput = new float [arraySize];
		   statusCheck = new int [arraySize];
		   statusCheckforAssetLineWithFDOnly = new int [arraySize]; 
		   year = new int [arraySize];
	
		   ageCounter = userAge;
		   
		   for(int counter=0; counter<arraySize; counter++)
		   {
			   //LOGGER.log(Level.INFO,"Suman code - ageCounter is " + ageCounter);
			   if(counter == 0)
			   {
				   Calendar current = Calendar.getInstance();
				   year[counter] = current.get(Calendar.YEAR);
			   }
			   else
				   year[counter] = year[counter-1] + 1;
			   
			   ageOutput[counter] = ageCounter;
			   totalFinAssetsOutput[counter] = totalFinAssets[ageCounter] + totalExpenditure[ageCounter];
			   totalFinAssetsWithOnlyFDOutput[counter] = assetWithOnlyFD[ageCounter] + totalExpenditure[ageCounter];
			   totalLiabilitiesOutput[counter] = totalExpenditure[ageCounter];
			   
			   if(totalFinAssetsOutput[counter] > totalLiabilitiesOutput[counter])
				   statusCheck[counter] = 1;
			   
			   else
				   statusCheck[counter] = 0;
			   
			   //Status Check for Asset Line with Only FD Investments
			   if(totalFinAssetsWithOnlyFDOutput[counter] > totalLiabilitiesOutput[counter])
				   statusCheckforAssetLineWithFDOnly[counter] = 1;
			   
			   else
				   statusCheckforAssetLineWithFDOnly[counter] = 0;
			   
			  //LOGGER.log(Level.INFO,"Suman code Asset Check - ageOutput is " + ageOutput[counter] +  "  finasset without exp " + totalFinAssets[counter]);   
			  //LOGGER.log(Level.INFO,"Suman code Asset Check- ageOutput is " + ageOutput[counter] +  "  finasset  " + totalFinAssetsOutput[counter] + "  Liability  " + totalLiabilitiesOutput[counter]);
			  //LOGGER.log(Level.INFO,"Suman code - ageOutput is " + ageOutput[counter] +  "  year  " + year[counter]);
			   ageCounter++;
					   
		   }
		   
		   fpOutput = new FinancialPlannerOutput(arraySize); 
		   //fpOutput.setName(fpDao.getNameFromTempInvTbUsingRegId(customerId));
		   fpOutput.setName("");
		   fpOutput.setAge(ageOutput);
		   fpOutput.setYear(year);
		   fpOutput.setTotalFinAsset(totalFinAssetsOutput);
		   fpOutput.setTotalFinAssetOnlyFD(totalFinAssetsWithOnlyFDOutput);
		   fpOutput.setTotalLiabilities(totalLiabilitiesOutput);
		   fpOutput.setStatusCheck(statusCheck);
		   fpOutput.setStatusCheckforAssetLineWithFDOnly(statusCheckforAssetLineWithFDOnly);
		   fpOutput.setLifeGoals(fpresponseSet.getLifeGoals());
		   fpOutput.setFpmasterassumption(fpMasterAssumption);
		   fpOutput.setSavingsRate(fpresponseSet.getSavingsRate());
		   fpOutput.setRiskGroupId(fpresponseSet.getRiskGroupId());
		   
		   //false = Redirect to Investor Dashboard
		   //true = Redirect to userRegistration
		   fpOutput.setRedirectInfo(fpDao.checkUserEntryExistsInMasterCustomerTb(customerId));
		   
		   return fpOutput;
		   
	   }

	@Transactional   
	public boolean getUserFpStatus(String regId) {
		boolean result = fpDao.getUserFpStatus(regId);
		return result;
	}
	   
}    