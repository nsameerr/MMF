app.controller('AdvisoryServiceContract', ['$scope', function($scope) {
	$scope.filterTag = ( $('#userType').val() == 'INVESTOR' ) ? true : false;
	$scope.mappingStatusTag = 0;
	$scope.aumPayableFrequencyList = '';
	$scope.mgmtFeeVariableAmountList = '';
	$scope.contractDurationFrequencyList = '';
	$scope.contractDurationList = '';
	$scope.contractData = {};
	$scope.actionText = [];
	$scope.reviewCommentPlaceholder = '';
	$scope.hideReview = false;
	$scope.reviewComment = '';
	$scope.reviewConversation = '';
	
	$scope.init = function(){
		getAUMPayableFrequencyList();
    	getMgmtFeeVariableAmountList();
		getContractDurationFrequencyList();
		getDurationList();
		$scope.getMappingInfo();
		$scope.getActionText(); 
	}
	
	$scope.getMappingInfo = function(){
		$.ajax({
			type: 'POST',
			url : _gc_url_CustomerAdvisorMapping,
			success: function(rdata){				
				try{
					rdata = JSON.parse(rdata);
					$scope.contractData = rdata;
					console.log(rdata);
                    $scope.mappingStatusTag = rdata.relationStatus;
                    $scope.hideReview = rdata.allocatedPortfolio.isRoboAdvisor;
                    console.log($scope.hideReview);
                    $scope.getActionText();
                    $scope.setActionUsingStatus();
					$scope.$apply();   
                   
				}catch(e){
					console.log('error');
				}
								
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
	
	$scope.setActionUsingStatus = function(){
		//Check Mapping Status 
		console.log("STATUS :"+$scope.mappingStatusTag);
        if( $scope.mappingStatusTag == 3){
            //Invite Accepted
            
        }
        if( $scope.mappingStatusTag == 4 || $scope.mappingStatusTag == 10){
            //Contract Sent or Received
        	
        	//Populate the contract details
        	$("input[name=mfee][value='"+$scope.contractData.mgmtFeeTypeVariable+"']").prop("checked",true);
        	if($scope.contractData.mgmtFeeTypeVariable === "fixed"){
        		$scope.mgmtFeeFixedAmount = $scope.contractData.fixedManagementFee;
        	}
        	if($scope.contractData.mgmtFeeTypeVariable === "variable"){
        		$scope.mgmtFeeVariableAmount =  $scope.mgmtFeeVariableAmountList[$scope.contractData.variableManagementFee.toString()];
        	}
        	$scope.performanceFeePercent = $scope.contractData.performanceFeePercent;
			$scope.performanceFeeThreshold = $scope.contractData.performanceFeeThreshold;
			$scope.exitLoadFeePercent = $scope.contractData.exitLoadFeePercent;
			$scope.exitLoadDuration = $scope.contractData.exitLoadDuration;
    		$scope.ContractDurationCount = $scope.contractData.durationCount.toString();
    		$scope.ContractDuration = $scope.contractData.durationFrequency.toString();
    		$("#contractDate").val($scope.contractData.startDate);
    		$scope.reviewConversation = $scope.contractData.previousReviews;
    		$scope.reviewConversation = $scope.reviewConversation.replace(/###/g,'\n').trim();    		
        }
        if( $scope.mappingStatusTag == 10 ){
        	//Investor is viewing the contract Page when status is Review
        	if($scope.filterTag){
	        	//Disable the buttons
	    		$('[id^=button-action-]').attr("disabled","disabled").css('opacity',0.5);
	    		$('#contractReviewMessage').show();
        	}
        }
    	if( $scope.mappingStatusTag == 4 ){
        	//Disable the input fields
        	$("input[type=radio]").attr('disabled', true);
        	$("#mfeeVarAmount").attr('disabled', true);
        	$("#mfeeFixedAmount").attr('disabled', true);
        	$("#pFeePercent").attr('disabled', true);
        	$("#pFeeThreshold").attr('disabled', true);
        	$("#eLFeePercent").attr('disabled', true);
        	$("#eLDuration").attr('disabled', true);
        	$("#cDurationCount").attr('disabled', true);
        	$("#cDurationFreq").attr('disabled', true);
        	$("#contractDate").attr('disabled', true);
        	
        	//For Advisor
        	if($scope.filterTag == false){
        		//Populate and Disable the checkbox 
        		$("#advisorInvestorAgreement").attr("checked",true);
        		$("#advisorInvestorAgreement").attr('disabled', true);
        		//Disable the buttons other than back
        		$('[id^=button-action-]').attr("disabled","disabled").css('opacity',0.5);
        		$('button:contains("Back")').attr("disabled", false).css('opacity',1);	
        	}
        }
	}
	
	$scope.changeExitLoadDuration = function(){
		$scope.exitLoadDuration = $scope.ContractDuration * $scope.ContractDurationCount;
		$scope.$apply();
	}

	canShowToast = true;
    function showToast(msg){
    	if(canShowToast){
    		$.toast({
    		    heading: 'Error',
    		    text: msg,
    		    hideAfter: 4000,
    		    position : 'mid-center',
    		    icon: 'error'
    		})
    	}
    	
    }

	getAUMPayableFrequencyList = function (){
		$.ajax({
			type: 'POST',
			url : _gc_url_AUMPayableFrequencyList,
			success: function(rdata){
				// console.log('AUM : ');
				rdata = JSON.parse(rdata);
//				console.log(rdata);
				$scope.aumPayableFrequencyList = rdata;
				//Assigning quarterly in dropdown
				$scope.fixAUMPayableFrequency = $scope.aumPayableFrequencyList["Quarterly"];
				$scope.varAUMPayableFrequency = $scope.aumPayableFrequencyList["Quarterly"];
				$scope.pFeeFrequency = $scope.aumPayableFrequencyList["Quarterly"];
				$scope.$apply();
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
	
	getMgmtFeeVariableAmountList = function (){
		$.ajax({
			type: 'POST',
			url : _gc_url_MgmtFeeVariableAmountList,
			success: function(rdata){
				// console.log('MGMT Fee');
				rdata = JSON.parse(rdata);
				// console.log(rdata);
				$scope.mgmtFeeVariableAmountList = rdata;
				//Assigning 0.5 in dropdown
				$scope.mgmtFeeVariableAmount = $scope.mgmtFeeVariableAmountList["0.5"];
				$scope.$apply();
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
	
	getContractDurationFrequencyList = function (){
		$.ajax({
			type: 'POST',
			url : _gc_url_ContractDurationFreqList,
			success: function(rdata){
				// console.log('Duration : ');
				rdata = JSON.parse(rdata);
				// console.log(rdata);
				$scope.contractDurationFrequencyList = rdata;
				//Assigning Year in dropdown
				$scope.ContractDuration = $scope.contractDurationFrequencyList["Year"];
				$scope.$apply();
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
	
	getDurationList = function (){
		$.ajax({
			type: 'POST',
			url : _gc_url_DurationList,
			success: function(rdata){
				// console.log('DurationList : ');
				rdata = JSON.parse(rdata);
				// console.log(rdata);
				$scope.contractDurationList = rdata;
				//Assigning 1 in dropdown
				$scope.ContractDurationCount = $scope.contractDurationList["1"];
				$scope.$apply();
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
	
	setTimeout(function(){
		displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();    	
	}, 10);

    $scope.getActionText = function(){
    	if($scope.filterTag){
			//Set Button Text for Investor
    		if($scope.hideReview){
    			//hide the Review Action if connected to Robo-Advisor
    			$scope.actionText = [ "Back", "Accept", "Decline"];     			
    		}else{
    			//Show the Review Action if connected to Human-Advisor
    			$scope.actionText = [ "Accept" , "Review", "Decline"];
    		}    		
    		//Disable Radio Inputs
    		$("input[type=radio]").attr('disabled', true);
    		//Set Review Comment Placeholder
    		$scope.reviewCommentPlaceholder = "If you want to review this contract, enter your review comments and press Review button else press Accept button";            
    	}else{
			//Set button text for advisor
    		$scope.actionText = [ "Back","Submit"];// , "Withdraw"];
    		//Set Review Comment Placeholder
    		$scope.reviewCommentPlaceholder = "Enter your comments";    		            
    	}    	
    }
    
    $scope.callToAction = function(buttonId){
    	var text=$('#button-action-'+buttonId).text();
    	console.log("Invoking Action : "+text);    	
    	if($scope.filterTag){
			//Investor clicks accept
    		if(typeof text != undefined && text != null && text.toLowerCase() === "accept")
        	{
    			var reviewCommentText = $scope.reviewComment;
    			if($.trim(reviewCommentText).length != 0){
	    			showToast('Please click review button if you have any review.');		
	    			return false;
	    		}
    			
    			if (confirm("Are you sure you want to continue?")) {
    				//Input Validation
    				//License Agreement
            		var agreementAccepted = $('#advisorInvestorAgreement').is(':checked');
            		if(!$('#advisorInvestorAgreement').is(':checked')){
        	    		showToast('Please accept the Agreement in order to proceed');
        	    		return false;
            		}
            		$scope.contractData.agreementAccepted = agreementAccepted;
            		
    				//Actual Process of Accept Contract
            		clevertap.event.push("Investor Accepts Contract");
    				showLoading();
            		$.ajax({
    					type: "POST",
    					url : _gc_url_ServiceContract,
    					data: "actionText="+text,
    					success: function(res){
    						  try{
        						  console.log('ACCEPT');
        						  console.log(res);        						  
        						  res = JSON.parse(res);    							  
        						  if(res != null && (res.redirectUrl != null || res.redirectUrl != "")){
        							  //Redirect to Another Page
        							  window.location.href = res.redirectUrl;
								  }else if(res != null && res.msg != null){
									  console.log(res.msg);
								  }
    						  }catch(e){
    							  console.log('Error');
    							  hideLoading();
    						  }
    					},
    					error: function(xhr, textStatus, error){
    						console.log(xhr.statusText);
    						console.log(textStatus);
    						console.log(error);
    					}
    				});
            		
    			}else{
        			console.log("Cancelled Action : "+text);
        		}
        	}
    		//Investor clicks review
    		if(typeof text != undefined && text != null && text.toLowerCase() === "review")
        	{
    			var reviewCommentText = $scope.reviewComment;
    			if($.trim(reviewCommentText).length == 0){
	    			showToast('Please write your review.');		
	    			return false;
	    		}
    			if($scope.contractData.mgmtFeeTypeVariable === "variable"){
    				$scope.contractData.mgmtFeeTypeVariable = true;
    			}
    			if($scope.contractData.mgmtFeeTypeVariable === "fixed"){
    				$scope.contractData.mgmtFeeTypeVariable = false;
    			}
    			$scope.contractData.investorReview = $.trim(reviewCommentText);
    			console.log($scope.contractData.investorReview);
				console.log($scope.contractData);
				console.log(JSON.stringify($scope.contractData));
				//Actual Process of Review Contract
				showLoading();
				$.ajax({
					type: "POST",
					url : _gc_url_ServiceContract,
					data: "actionText="+text+"&contract="+encodeURIComponent(JSON.stringify($scope.contractData)),
					success: function(res){
						  try{
							  console.log('REVIEW');
    						  console.log(res);        						  
    						  res = JSON.parse(res);    							  
    						  if(res != null && (res.redirectUrl != null || res.redirectUrl != "")){
    							  //Redirect to Another Page
    							  window.location.href = res.redirectUrl;
							  }else if(res != null && res.msg != null){
								  console.log(res.msg);
							  }
						  }catch(e){
							  console.log('Error');
							  hideLoading();
						  }
					},
					error: function(xhr, textStatus, error){
						console.log(xhr.statusText);
						console.log(textStatus);
						console.log(error);
					}
				});
        	}
    		//Investor clicks decline
    		if(typeof text != undefined && text != null && text.toLowerCase() === "decline")
        	{
    			$.ajax({
					type: "POST",
					url : _gc_url_ServiceContract,
					data: "actionText="+text,
					success: function(res){
						  console.log('DECLINE');
						  console.log(res);
						  res = JSON.parse(res);
						  if(res != null && (res.redirectUrl != null || res.redirectUrl != "")){
							  //Redirect to Another Page
							  window.location.href = res.redirectUrl;
						  }else if(res != null && res.msg != null){
							  console.log(res.msg);
						  }
					},
					error: function(xhr, textStatus, error){
						console.log(xhr.statusText);
						console.log(textStatus);
						console.log(error);
					}
				});
        	}
    	}else{
    		//Advisor clicks Submit
    		if(typeof text != undefined && text != null && text.toLowerCase() === "submit")
        	{
        		if (confirm("Are you sure you want to continue?")) {
            		//Input validation
            		//Management Fee
            		if (!$("input[name='mfee']:checked").val()) {
        		       showToast('Please select Management Fee');
        		       return false;
        		    }else{
        		    	var mfeeVal = $("input[name='mfee']:checked").val();
        		    	//Fixed Management Fee
        		    	if(mfeeVal === "fixed"){
        		    		if($.trim($("#mfeeFixedAmount").val()).length == 0){
        		    			showToast('Please enter fixed Management Fee');		
        		    			return false;
        		    		}
        		    		if($("#mfeeFixedAmount").val() <0 || $("#mfeeFixedAmount").val() > 99999){
        		    			showToast('Please enter a valid number for fixed Management Fee');		
        		    			return false;	
        		    		}
        		    		$scope.contractData.mgmtFeeTypeVariable = false;
        		    		$scope.contractData.fixedManagementFee = parseInt($scope.mgmtFeeFixedAmount);
        		    		$scope.contractData.mgmtFeeFixedPayableFrequecy = parseInt($scope.fixAUMPayableFrequency);
        		    	}
        		    	//Variable Management Fee
        		    	if(mfeeVal === "variable"){
        		    		$scope.contractData.mgmtFeeTypeVariable = true;
        		    		$scope.contractData.variableManagementFee = parseFloat($scope.mgmtFeeVariableAmount);
        		    		$scope.contractData.mgmtFeeVariablePayableFrequecy = parseInt($scope.varAUMPayableFrequency);	    				    		
        		    	}
        		    }

        		    //Performance Fee
        		    if( ($("#pFeePercent").val()).length == 0){
        		    	showToast('Please enter Performance Fee Percentage');		
        		    	return false;
        			}
        			var perFeePercent = parseFloat(parseFloat($("#pFeePercent").val()).toFixed(2));
        			if(perFeePercent <= 0 || perFeePercent > 100){
        				showToast('Please enter a valid number for Performance Management Fee');		
        		    	return false;
        			}
        			
        			if( ($("#pFeeThreshold").val()).length == 0){
        		    	showToast('Please enter Performance Fee Threshold');		
        		    	return false;
        			}
        			var perFeeThreshold = parseFloat(parseFloat($("#pFeeThreshold").val()).toFixed(2));
        			if(perFeeThreshold <= 0 || perFeeThreshold > 100){
        				showToast('Please enter a valid number for Performance Management Threshold');		
        		    	return false;
        			}
        			$scope.contractData.performanceFeePercent = perFeePercent;
        			$scope.contractData.performanceFeeThreshold = perFeeThreshold;
        			$scope.contractData.performanceFeeFrequency = parseInt($scope.pFeeFrequency);
        			
        			//Exit Load
        		    if( ($("#eLFeePercent").val()).length == 0){
        		    	showToast('Please enter Exit Load Fee');		
        		    	return false;
        			}
        			var exitLFeePercent = parseFloat(parseFloat($("#eLFeePercent").val()).toFixed(2));
        			if(exitLFeePercent <= 0 || exitLFeePercent > 100){
        				showToast('Please enter a valid number for Exit Load Fee');		
        		    	return false;
        			}
        			
        			if( ($("#eLDuration").val()).length == 0){
        		    	showToast('Please enter Exit Load Duration');		
        		    	return false;
        			}
        			var exitLoadDuration= parseInt($("#eLDuration").val());
        			if($("#eLDuration").val() <= 0 || $("#eLDuration").val() > 60){
        		    		showToast('Please enter a valid number for Exit Load Duration');		
        		    		return false;	
        		    }
        			$scope.contractData.exitLoadFeePercent = exitLFeePercent;
        			$scope.contractData.exitLoadDuration = exitLoadDuration;
        			
        		    //Duration
        			var contractStartDate = $('#contractDate').val();
            		if($('#contractDate').val() == ''){
        	    		showToast('Please enter contract start date');
        	    		return false;
            		}
            		$scope.contractData.durationCount = parseInt($scope.ContractDurationCount);
        			$scope.contractData.durationFrequency = parseInt($scope.ContractDuration);
            		$scope.contractData.startDate = contractStartDate;	// DATE sent as string 16-02-2017
            		
            		//License Agreement
            		var agreementAccepted = $('#advisorInvestorAgreement').is(':checked');
            		if(!$('#advisorInvestorAgreement').is(':checked')){
        	    		showToast('Please accept the Agreement in order to proceed');
        	    		return false;
            		}
            		$scope.contractData.agreementAccepted = agreementAccepted;
            		
            		//Review Comment - If any
            		
            		var reviewCommentText = $scope.reviewComment;
        			$scope.contractData.investorReview = $.trim(reviewCommentText);
        			console.log($scope.contractData.investorReview);
    				console.log($scope.contractData);
    				console.log(JSON.stringify($scope.contractData));
        			
            		//Actual Process of Submit Contract
            		showLoading();
            		$.ajax({
    					type: "POST",
    					url : _gc_url_ServiceContract,
    					data: "actionText="+text+"&contract="+encodeURIComponent(JSON.stringify($scope.contractData)),
    					success: function(res){
    						  try{
        						  console.log('SUBMIT');
        						  console.log(res);        						  
        						  res = JSON.parse(res);    							  
        						  if(res != null && (res.redirectUrl != null || res.redirectUrl != "")){
        							  //Redirect to Another Page
        							  window.location.href = res.redirectUrl;
								  }else if(res != null && res.msg != null){
									  console.log(res.msg);
								  }
    						  }catch(e){
    							  console.log('Error');
    							  hideLoading();
    						  }
    					},
    					error: function(xhr, textStatus, error){
    						console.log(xhr.statusText);
    						console.log(textStatus);
    						console.log(error);
    					}
    				});
        		}else{
        			console.log("Cancelled Action : "+text);
        		}
        	}
    		//Advisor Clicks Withdraw
    		if(typeof text != undefined && text != null && text.toLowerCase() === "withdraw")
        	{
    			$.ajax({
					type: "POST",
					url : _gc_url_ServiceContract,
					data: "actionText="+text,
					success: function(data){
						  console.log('WITHDRAW');
						  console.log(data);
					},
					error: function(xhr, textStatus, error){
						console.log(xhr.statusText);
						console.log(textStatus);
						console.log(error);
					}
				});
        	}
    		//Advisor Clicks Back To View PortfolioAllocation
    		if(typeof text != undefined && text != null && text.toLowerCase() === "back")
        	{
    			window.location.href="/faces/pages/v2/portfolioAllocation.jsp?faces-redirect=true";
        	}
    	}    	
    }
    
    function toDate(dateStr) {
        var parts = dateStr.split("-");
        return new Date(parts[2], parts[1] - 1, parts[0]);
    }
    
}]);