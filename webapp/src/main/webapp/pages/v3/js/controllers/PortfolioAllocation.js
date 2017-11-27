app.controller('PortfolioAllocation', ['$scope', function($scope) {
	$scope.page = {
			title: 'Portfolia Allocation'
	};
	$scope.customerAdvisorMap;
	$scope.userRequestedPortfolio;
	$scope.userRiskType;
	$scope.portfolioStyleMap;
	$scope.advPortfolioList;
	$scope.questionResponse;
	$scope.portfolioPieChart;
	$scope.selectedRiskType;
	$scope.selectedPortfolio;
	$scope.portfolioTypeVO;
	$scope.invFpOp;
	$scope.updateWarningShown = false;
	$scope.showPieFlag = true;
	$scope.showPieTableFlag = true;
	$scope.portfolioSizeArray;
	$scope.assetClassRange;
	$scope.userType;
	$scope.init = function (){
		showLoading();
		getInitData();
	}
	
	//change event listener for risktype
	$scope.onChangeRiskType = function(){
		if ($scope.updateWarningShown == false) {
			alert("You are modifying the user's requested Portfolio \n These might affect his life goals");
			$scope.updateWarningShown = true;
		}
		console.log($scope.selectedRiskType);
		var rs = 0;
		if ($scope.selectedRiskType.riskTypeId == 1){
			rs=5;
		} else if ($scope.selectedRiskType.riskTypeId == 2){
			rs=4;
		} else if ($scope.selectedRiskType.riskTypeId == 3){
			rs=3;
		} else if ($scope.selectedRiskType.riskTypeId == 4){
			rs=2;
		} else if ($scope.selectedRiskType.riskTypeId == 5){
			rs=1;
		} else {
			rs = $scope.selectedRiskType.riskTypeId;
		}
		if (rs != 6){
			$.post(
	        		_gc_url_fpo_post_editRiskScore,
	        		{ 
	        			riskScore : rs
	        		},
	        		function(rdata) {
	        			processChartData(rdata)
	        		}
	        	);
		}
		
	}
	
	//change event listener for portfolios
	$scope.onChangePortfolio = function() {
		console.log($scope.selectedPortfolio);
		if ($scope.selectedPortfolio == null){
			$scope.showPieFlag = false;
			$scope.showPieTableFlag = false;
			//$scope.$apply();
		} else {
			var portfolioIdParam = $scope.selectedPortfolio.portfolioId;
			$.post(
					//"http://localhost:8080/paservice/onPortfolioSelectChange",
					_gc_url_pa_portfolioChange,
					{
						portfolioId :portfolioIdParam,
						portfolioStyleId:$scope.selectedRiskType.riskTypeId
					},function(rdata) {
						console.log(rdata);
						var d = $.parseJSON(rdata);
						$scope.showPieFlag = true;
						$scope.showPieTableFlag = true;
						$scope.portfolioPieChart = d.portfolioPieChart;
						$scope.portfolioTypeVO = d.portfolioTypeVO;
						$scope.$apply();
						plotPie($scope.portfolioPieChart);
						
					}
			);
		}
		
		

	}
	// get fp data
	getFpData = function(){
		$.post(
				_gc_url_fpo_get_assetExpenditureChart,
				{
					userType : $scope.userType
				},function(rdata) {
					console.log(rdata);
					try{
						initData(rdata);
						hideLoading();
					}catch(err){
						console.log('error in displaying fp data');
					}
										
				}
			);
	}
	
	// get all initalization data
	getInitData = function(){

		$.ajax({
			type: 'POST',
			//url : "http://localhost:8080/paservice/getInitData",
			url : _gc_url_pa_init,
			success: function(rdata){
				console.log(rdata);
				var d = $.parseJSON(rdata);
				$scope.portfolioStyleMap = d.portfolioStyleMap;
				$scope.advPortfolioList = d.advPortfolioList;
				//$scope.advPortfolioList = [{"actionText":"","portfolioType":"Hybrid","riskType":"Conservative","minInvest":100000,"advId":2,"returns":{"oneMonth":0,"sixMonth":0,"sinceInception":0,"threeMonth":0},"portfolioName":"testPortfolio"},{"actionText":"","portfolioType":"Hybrid","riskType":"Aggressive","minInvest":100000,"advId":2,"returns":{"oneMonth":0,"sixMonth":0,"sinceInception":0,"threeMonth":0},"portfolioName":"testPortfolio2"},{"actionText":"","portfolioType":"Hybrid","riskType":"Aggressive","minInvest":100000,"advId":2,"returns":{"oneMonth":0,"sixMonth":0,"sinceInception":0,"threeMonth":0},"portfolioName":"testingPortfolio"}];
				$scope.questionResponse = d.questionResponse;
				$scope.portfolioPieChart = d.portfolioPieChart;
				$scope.portfolioTypeVO = d.portfolioTypeVO;
				$scope.customerAdvisorMap = d.customerAdvisorMap;
				$scope.userRequestedPortfolio = $scope.customerAdvisorMap.preferredPortfolio;
				$scope.userAllocatedPortfolio = $scope.customerAdvisorMap.allocatedPortfolio;
				//$scope.invFpOp = d.invFpOp;
				$scope.userType = d.userType;
				$scope.portfolioSizeArray = d.portfolioSizeArray;
				// testing
				//$scope.userRequestedPortfolio = {"actionText":"","portfolioType":"Hybrid","riskType":"Aggressive","minInvest":100000,"advId":2,"returns":{"oneMonth":0,"sixMonth":0,"sinceInception":0,"threeMonth":0},"portfolioName":"testPortfolio"};	
				// populate risktype
				$.each($scope.portfolioStyleMap,function(index,obj){
					if (typeof $scope.userAllocatedPortfolio.riskType == 'undefined'){
						// portfolio not allocated
						if (obj.riskTypeLabel == $scope.userRequestedPortfolio.riskType){
							$scope.selectedRiskType = obj;
							return false;		
						}
					} else {
						// portfolio allocated
						if (obj.riskTypeLabel == $scope.userAllocatedPortfolio.riskType){
							$scope.selectedRiskType = obj;
							return false;		
						}
					}
					
				});
				//populate portfolio Size
				$.each($scope.portfolioSizeArray,function(index,obj){
					if (typeof $scope.userAllocatedPortfolio.portfolioName == 'undefined'){
						// portfolio not allocated
						if(obj.portfolioSizeId == $scope.userRequestedPortfolio.portfolioSizeId){
							$scope.selectedPortfolioSize = obj;
							return false;
						}
					} else {
						// portfolio allocated
						if(obj.portfolioSizeId == $scope.userAllocatedPortfolio.portfolioSizeId){
							$scope.selectedPortfolioSize = obj;
							return false;
						}
					}
				});
				//populate portfolio name
				$.each($scope.advPortfolioList, function(index,obj){
					if (typeof $scope.userAllocatedPortfolio.portfolioName == 'undefined'){
						// portfolio not allocated
						if(obj.portfolioName == $scope.userRequestedPortfolio.portfolioName){
							$scope.selectedPortfolio = obj;
							return false;
						}
					} else {
						// portfolio allocated
						if(obj.portfolioName == $scope.userAllocatedPortfolio.portfolioName){
							$scope.selectedPortfolio = obj;
							return false;
						}
					}
					
				});
				//initData($scope.invFpOp);
				plotPie($scope.portfolioPieChart);
				prepareAssetClassRange();
				$scope.$apply();
				
				//disabled the inputs if its viewmode
				disableInputs();
				
				//get fp data
				getFpData();
				
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});

	}
	// getinitdata end

	//set time out start
	setTimeout(function (){
		
		//display user name
		displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();
    	
    	
	});
	//set time out end
	      plotPie = function(data){
    	  $.plot('#recommended-donut', data, {
              series: {
                  pie: {
                      innerRadius: 0.5,
                      radius: 0.65,
                      show: true,
                      label: {
                          show: true,
                          radius: 0.99,
                          formatter: labelFormatter,
                          background: {
                              opacity: 1,
                              color: 'transparent'
                          },
                          threshold: 0
                      }
                  }
              },
              grid: {
                  hoverable: true
              }
          });  
      }
      function labelFormatter(label, series) {
           return '<div class="donut-label">' + label + '<br/>' + Math.round(series.percent) + '%</div>';
       }

       prepareAssetClassRange = function(){

       		$scope.assetClassRange = {};
 
			$scope.assetClassRange["Equity Diversified"] = $scope.portfolioTypeVO.range_min_diversifiedEquity + "% to " +$scope.portfolioTypeVO.range_max_diversifiedEquity +"%" ;

			$scope.assetClassRange["Large Caps"] = $scope.portfolioTypeVO.range_min_equity +"% to "  +$scope.portfolioTypeVO.range_max_equity +"%" ;

			$scope.assetClassRange["Mid &amps; Small Caps"] = $scope.portfolioTypeVO.range_min_midcap +"% to "  +$scope.portfolioTypeVO.range_max_midcap +"%";

			$scope.assetClassRange["International"] = $scope.portfolioTypeVO.range_min_international +"% to "  +$scope.portfolioTypeVO.range_max_international +"%";

			$scope.assetClassRange["Gold"] = $scope.portfolioTypeVO.range_min_gold +"% to "  +$scope.portfolioTypeVO.range_max_gold +"%";

			$scope.assetClassRange["Debt-Medium/Long"] = $scope.portfolioTypeVO.range_min_debt +"% to "  +$scope.portfolioTypeVO.range_max_debt +"%";

			$scope.assetClassRange["Debt-Liquid/Short"] = $scope.portfolioTypeVO.range_min_debt_liquid +"% to "  +$scope.portfolioTypeVO.range_max_debt_liquid +"%";

			$scope.assetClassRange["Cash"] = $scope.portfolioTypeVO.range_min_cash +"% to "  +$scope.portfolioTypeVO.range_max_cash +"%";


       }

       $scope.getAssetClassRange = function(label) {

			if (label =="Equity Diversified")
				return $scope.assetClassRange["Equity Diversified"];
			else if (label =="Large Caps")
				return $scope.assetClassRange["Large Caps"];
			else if (label =="Mid & Small Caps")
				return $scope.assetClassRange["Mid &amps; Small Caps"];
			else if (label =="International")
				return $scope.assetClassRange["International"];
			else if (label =="Gold")
				return $scope.assetClassRange["Gold"];
			else if (label =="Debt-Medium/Long")
				return $scope.assetClassRange["Debt-Medium/Long"];
			else if (label =="Debt-Liquid/Short")
				return $scope.assetClassRange["Debt-Liquid/Short"];
			else if (label =="Cash")
				return $scope.assetClassRange["Cash"];
       }
       saveAllocatedPortfolio = function(){
    	   console.log('save allocated portfolio called');
    	   console.log($scope.selectedRiskType);
    	   console.log($scope.selectedPortfolioSize);
    	   console.log($scope.selectedPortfolio);
    	   // validate inputs
    	   var validateOK = true;
    	   var invalidRT = false;
    	   var invalidPS = false;
    	   var invalidP = false;
    	   if (typeof $scope.selectedRiskType == 'undefined' || $scope.selectedRiskType  == null || $scope.selectedRiskType == "" || $scope.selectedRiskType == 'select') {
    	   		invalidRT = true;
    	   		validateOK = false;
    	   } else if (typeof $scope.selectedPortfolioSize == 'undefined' || $scope.selectedPortfolioSize == null || $scope.selectedPortfolioSize == "" || $scope.selectedPortfolioSize == 'select') {
    	   		invalidPS = true;
    	   		validateOK = false;
    	   } else if (typeof $scope.selectedPortfolio == 'undefined' || $scope.selectedPortfolio == null || $scope.selectedPortfolio == "" || $scope.selectedPortfolio == 'select') {
    	   		invalidP = true;	
    	   		validateOK = false;
    	   }

    	   if (validateOK){
    	   	// valid inputs
    	   		//JSON.stringify(obj);
    	   		 $.post(
					_gc_url_pa_saveAllocatedPortolio,
					{
						selectedRiskType :JSON.stringify($scope.selectedRiskType),
						selectedPortfolioSize:JSON.stringify($scope.selectedPortfolioSize),
						selectedPortfolio :JSON.stringify($scope.selectedPortfolio)
					},function(rdata) {
						console.log(rdata);
						
						if (rdata.startsWith("error")){
							alert("Error occurred while allocating portfolio.\n Please try after some time.");
						} else {
							//var d = $.parseJSON(rdata);
							if (rdata != "")
								window.location.href = rdata;
						}
						
					}
				);
    	   } else {
    	   		if (invalidRT) {
    	   			alert("Please select valid Risk Type");
    	   			return;
    	   		} else if (invalidPS) {
    	   			alert("Please select valid Portfolio Size");
    	   			return;
    	   		} else if (invalidP) {
    	   			alert("Please select valid Portfolio ");
    	   			return;
    	   		}
    	   }
    	   
       }
       
       disableInputs = function(){
    	   if($scope.customerAdvisorMap.relationStatus == 4 && $scope.userRequestedPortfolio.isRoboAdvisor == false){
    		   $('#inputSelectRiskType').prop('disabled', 'disabled');
    		   $('#inputSelectPortfolioSize').prop('disabled', 'disabled');
    		   $('#inputSelectPortfolio').prop('disabled', 'disabled');
    	   } else {
    		   console.log('edit mode :' + $scope.customerAdvisorMap.relationStatus);
    	   }
       }
}]);