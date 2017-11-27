Threshold = getThreshold(); // Annual Rate of return (R)
DailyRate = (1+Threshold)^(1/365); // Daily rate of return for threshold (1 + r)
StartDate = getPortfolioStartDate();
CashFlows = getCashFlows(); // Array of all cashflow objects with associated dates
ExpectedReturn = CF[0]; // Initial value is same as initial investment.
EffectiveHWM = 0; // HighWaterMark, the highest market value for which the any of the past bill was charged. Initial value is same as initial investment.
BillableAmount[] = 0; // Array of all billable amounts. Initialized to 0 for begining.
Define isBillDate(); // Function to check if current date is the billing date. (should check current date against the quarter end dates)
 
if (CurrentDate == StartDate) then {
	
	HWM = CashFlow[0]

	} else
	{
		for CF in CashFlows[] {
			DaysSinceCashFlow = CurrentDate - CF.StartDate; // No.of days since the particular cashflow
			ExpectedReturns + = CF *(DailyRate ^ DaysSinceCashFlow);		// Minimum MWR return expected for the day, SUM[ CF(n) * (1+r) ^ n]						
			}
		
		// Take total of all billable amounts till last quarter (q-1), for adjusting the HWM 
		for BA in BillableAmount[0:(q-1)] {
			SumOfAllBillableAmounts += BA;		
		}		
		
		EffectiveHWM = ExpectedReturns + SumOfAllBillableAmounts; // HWM is return expected on initial investment plus any returns that are already charged for.
		
		if (MarketValue > EffectiveHWM) then {
			if (isBillDate()) then {
				BillableAmount[q] = MarketValue - EffectiveHWM;  // Insert into the billable amounts array (table in db)
				CurrentPerfFee	= BillableAmount[] * 10%;
			} 
		}else
		{
			BillableAmount[] = 0;
			CurrentPerfFee	= 0;
		}
	
}
	