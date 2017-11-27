app.controller('InvestorProfile', ['$scope', function($scope) {
    
	$scope.page = {
            title: 'Investor Profile'
    };
    
	$scope.actionText = [];
	$scope.filterTag = ( $('#userType').val() == 'INVESTOR' ) ? true : false;
	
	$scope.lifeGoalsJson = '';

    $scope.investor = {
            profile : '',
            financialPlanner : '',
            linkedinProfile : '',
            pic : ''
            	
    }
    
	var MAX_FILE_SIZE="2097152"; // file size 2 MB in bytes

    $scope.init = function(){        
        $scope.getInvestorProfile();
        $scope.getLinkedInProfile();
        $( '.js-float-label-wrapper' ).FloatLabel();        
    }
    
    function toDataUrl(url, callback) {
    	  var xhr = new XMLHttpRequest();
    	  xhr.responseType = 'blob';
    	  xhr.open('GET', url);
    	  xhr.onload = function() {
    	    var reader = new FileReader();
    	    reader.onloadend = function() {
    	      callback(reader.result);
    	    }
    	    reader.readAsDataURL(xhr.response);
    	  };
    	  xhr.send();
    }
    
    setTimeout(function(){ 
        $("#displayOnlyUserName").text($("#userFirstName").val());                 
    },100);
    
    $scope.getFinancialPlaner = function(){
		if($scope.filterTag){
			//Logged in as investor
			/*if(location.search.length == 0){*/
				//Investor View
				$.ajax({
					type: "GET",
					url: _gc_url_fpo_get_assetExpenditureChart,
					data: null,
					dataType: 'json',
					contentType:"application/json; charset=utf-8",
					  success: function(data){
		
					  $scope.investor.financialPlanner = data;
					  $scope.userFpData = data;
					  $scope.lifeGoalsJson = data.lifeGoals;
					  $scope.$apply();					 		
					//$scope.processChartData(data);
					  initFinancialPlannerData(data,'inv_portfolio_high_chart');
					  $scope.userFpArray = fpArray;
					  try{
						  
						  drawRiskProfileSliders(
								  $scope.investor.financialPlanner.savingsRate,
								  $scope.investor.financialPlanner.fpmasterassumption.fpmiscassumptions.amountInvestedToMmf,
								  $scope.investor.financialPlanner.fpmasterassumption.fpmiscassumptions.retirementAge,false); 
					  }catch(err){
						  console.log(err);
					  }
					  
					}
				});						
			/*}*/			
		}else{
			//Logged in as advisor
			if(location.search.length != 0){
				var baseurl = window.location.href;
				var paramsurl =  baseurl.split('?');
				var paramSub = paramsurl[1];
				var params = paramSub.split('&');
				var viewOnly;
				var customerID;
				for(var i = 0;i<params.length;i++){
					if(params[i].indexOf('viewOnly') == 0 ){
						viewOnly = params[i].split('=')[1]; 
					} else if(params[i].indexOf('id') == 0 ){
						customerID = params[i].split('=')[1]; 				
					} 
				}
				
				if(viewOnly && $scope.filterTag == false){					
					var regId = $scope.investor.profile.registrationId;
					//Advisor View
					$.ajax({
						type: "GET",
						url: _gc_url_fpo_get_assetExpenditureChart,
						data: "id="+regId,
						dataType: 'json',
						contentType:"application/json; charset=utf-8",
						  success: function(data){
						  $scope.investor.financialPlanner = data;
						  $scope.userFpData = data;
						  $scope.lifeGoalsJson = data.lifeGoals;
						  $scope.$apply();
						  //$scope.processChartData(data);
						  initFinancialPlannerData(data,'inv_portfolio_high_chart');
						  $scope.userFpArray = fpArray;
						  drawRiskProfileSlidersAdvisorView(
							  $scope.investor.financialPlanner.savingsRate,
							  $scope.investor.financialPlanner.fpmasterassumption.fpmiscassumptions.amountInvestedToMmf,
							  $scope.investor.financialPlanner.fpmasterassumption.fpmiscassumptions.retirementAge,false);
						}
					});
				}  				
			}
		}
    }
    
    $scope.getInvestorProfile = function(){
    	if($scope.filterTag){
			//Logged in as Investor
			$.ajax({
				type: "POST",
				url: _gc_url_ap_getInvestorProfile,
				data: null,
				dataType: 'json',
				contentType:"application/json; charset=utf-8",
				success: function(rData){
					try{
//						console.log(rData);
						$scope.investor.profile = rData;						
						$scope.$apply();
						$scope.getFinancialPlaner();
						//Draw Chart
						if($scope.investor.profile.customerRiskProfileResultTbs != null){
							//$scope.drawRiskChart($scope.investor.profile.customerRiskProfileResultTbs.riskScore);
							drawRiskProfileChart($scope.investor.profile.customerRiskProfileResultTbs.riskScore,'gauge_section','edit_inputgraphtxt');
						}
						
						//Set Investor Pic
						if($scope.investor.profile.masterCustomerQualificationTb != null && $scope.investor.profile.masterCustomerQualificationTb.invPicPath != null ){
							$scope.investor.pic  = !$scope.investor.profile.masterCustomerQualificationTb.invPicPath.match('http') ? '../../images?src='+$scope.investor.profile.masterCustomerQualificationTb.invPicPath :  $scope.investor.profile.masterCustomerQualificationTb.invPicPath;
							toDataUrl($scope.investor.pic, function(base64Img) {
								$scope.investor.pic = base64Img;  
								$scope.$apply();
							});																
						}	
					}
					catch(e){
						$('#divContent').css("display","block");
						$('#divLoading').css("display","none");
						console.log(e);
					}
				},
				error: function(){
					console.log('error');
				},
				fail: function(){
					console.log('fail');
				}
			});
		}else{
			//Logged in as Advisor
			if(location.search.length == 0){
				//Advisor trying invalid direct access to investorProfile.jsp
				location.href = _gc_url_baseUrl+"/faces/pages/v3/advisorDashboard.jsp?faces-redirect=true";
			}
			else{
				//Check URL for Parameter : viewOnly
				var baseurl = window.location.href;
				var paramsurl =  baseurl.split('?');
				var paramSub = paramsurl[1];
				var params = paramSub.split('&');
				var viewOnly;
				var customerID;
				for(var i = 0;i<params.length;i++){
					if(params[i].indexOf('viewOnly') == 0 ){
						viewOnly = params[i].split('=')[1]; 
					} else if(params[i].indexOf('id') == 0 ){
						customerID = params[i].split('=')[1]; 				
					} 
				}
				if(viewOnly){
					//Advisor View
					var settings = {
					  "async": true,
					  "crossDomain": true,
					  "url":  "/faces/userProfile",
					  "method": "POST",
					  "headers": {
						"cache-control": "no-cache",
						"content-type": "application/x-www-form-urlencoded"
					  },
					  "processData": false,
					  "contentType": false,
					  "mimeType": "multipart/form-data",
					  "data": "viewOnly=true&id="+customerID+"&userType=INVESTOR"
					}
					$.ajax(settings).done(function (rData) {
						rData = JSON.parse(rData);
//						console.log(rData);
						$scope.investor.profile = rData;
						$scope.$apply();
						$scope.getFinancialPlaner();
						//Draw Chart
						if($scope.investor.profile.customerRiskProfileResultTbs != null){
							$scope.drawRiskChart($scope.investor.profile.customerRiskProfileResultTbs.riskScore);
						}
						
						//Set Investor Pic
						if($scope.investor.profile.masterCustomerQualificationTb != null  && $scope.investor.profile.masterCustomerQualificationTb.invPicPath != null){
							$scope.investor.pic = !$scope.investor.profile.masterCustomerQualificationTb.invPicPath.match('http') ? '../../images?src='+$scope.investor.profile.masterCustomerQualificationTb.invPicPath :  $scope.investor.profile.masterCustomerQualificationTb.invPicPath;							 
							toDataUrl($scope.investor.pic, function(base64Img) {
								$scope.investor.pic = base64Img;  
								$scope.$apply();
							});
						}	
						
						//Get Action Text
						$scope.getActionText($scope.investor.profile.customerId);

					}).fail(function (response){
						console.log('fail');
						console.log(response);
					});
				}
			}
		}		
    }
    
	$scope.saveInvestorProfile = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_investorProfileUpdate,//'/faces/updateUserProfile',
			data: "data="+encodeURIComponent(JSON.stringify($scope.investor.profile)),
			contentType:"application/x-www-form-urlencoded",
			success: function(rData){
				//alert('Profile Saved');
				$('#modalSuccess').modal('show');
				$('#divProfileReadOnly').css('display','block');
				$('#divProfileEdit').css('display','none');
			},
			error: function(){
				//alert('Save failed');
				$('#modalError').modal('show');
				location.reload();
			},
			fail: function(){
				//alert('Save failed');
				$('#modalError').modal('show');
				location.reload();
			}
		});
	}
    
	$scope.drawRiskChart = function(riskScore){        
		$("#rsSalarySavingsRate").roundSlider({
            sliderType: "min-range",
            editableTooltip: false,
            readOnly: true,
            radius: 105,
            width: 16,
            value: riskScore,
            handleSize: 0,
            handleShape: "square",
            circleShape: "half-top",
            startAngle: 360,
            tooltipFormat: "changeTooltip",
            showTooltip : false,
            drag : function(e){
            	if(e.value < 10){
            		$('#spanRiskScore').html('0' + e.value);
            	}
            	else{
            		$('#spanRiskScore').html(e.value);
            	}
            	
            },
        });
    }
	
	$scope.getActionText = function(invId){
//		console.log("ActionText : "+invId);
		
		var settings = {
		  "async": true,
          "crossDomain": true,
          "url": "/faces/investorProfile",
          "method": "GET",
          "headers": {
            "cache-control": "no-cache",
            "Access-Control-Allow-Origin" : "*"
          },
          "processData": false,
          "contentType": false,
          "mimeType": "multipart/form-data",
          "data":"fetch=true&invId="+invId
		}
		
		$.ajax(settings).done(function (res) {
			try{
				// console.log(res);
				res = JSON.parse(res);
				// console.log(res);
				if(res.length != 0) {					
					//Assign the array of action Text only if it has accept and reject
					angular.forEach(res, function(value, key) {
				    	if(res[key] === "Accept" || res[key] === "Reject"){
				    		$scope.actionText.push(res[key]);
				    	}
					});
//					console.log($scope.actionText);
					$scope.$apply();
				}				
			}catch(e){
				console.log('Error');
			}
		}).fail(function (response){
			console.log(response);
		});
				
	}
	
	$scope.callToAction = function(invId,buttonId){
		var text=$('#button-'+buttonId).text();
		console.log("Call Action : "+text+": Investor : "+invId);
		//Disable the buttons
		$('[id^=button-]').attr("disabled","disabled").css('opacity',0.5);
		showLoading();
		var settings = {
		  "async": true,
          "crossDomain": true,
          "url": "/faces/investorProfile",
          "method": "GET",
          "headers": {
            "cache-control": "no-cache",
            "Access-Control-Allow-Origin" : "*"
          },
          "processData": false,
          "contentType": false,
          "mimeType": "multipart/form-data",
          "data":"action="+text+"&invId="+invId
		}
		
		$.ajax(settings).done(function (res) {
			try{
				console.log(res);
				res = JSON.parse(res);
				console.log(res);	
				if(res != null && res.redirectUrl != null){
					//Redirect to Another Page
					window.location.href = res.redirectUrl;
				}else if(res != null && res.msg != null){
					//Display The Response
//					$('[id^=button-]').hide();
					$("#div-"+invId).text(res.msg).css({"text-align":"center","font-weight": "bold","font-size":"18px"}).show();
					hideLoading();
				}				
			}catch(e){
				console.log('Error');
				hideLoading();
			}
		}).fail(function (response){
			console.log(response);
			hideLoading();
		});

	}
	
    $scope.getLinkedInProfile = function(){
    	if($scope.filterTag){
			//Logged in as Investor
			$.ajax({
	            type: "POST",
	            url: _gc_url_linkedIn_basicProfile,
	            data: null,
	            dataType: 'json',
	            contentType:"application/json; charset=utf-8",
	            success: function(rData){
	                try{
//	                	console.log(rData);
	                    $scope.investor.linkedinProfile = rData;
	                    $scope.$apply();
	                }
	                catch(e){
	                    $('#divContent').css("display","block");
	                    $('#divLoading').css("display","none");
	                }
	            },
	            error: function(){
	                console.log('error in fetching linked in profile');
	            },
	            fail: function(){
	                console.log('fail');
	            }
	        });
    	}
    }
    
    $scope.uploadNewPic = function(){
		var fileSize = $('#investorPic')[0].files[0].size;
    	if (fileSize <= MAX_FILE_SIZE){
    		$scope.uploadFileToServer(
        			$('#investorPic')[0].files[0],
        			_gc_url_fileUpload_inv_pic,
        			function(responseData){
        				console.log(responseData);
        				if (responseData.indexOf("error")== -1) {
        					if($scope.investor.profile.masterCustomerQualificationTb != null){
        						$scope.investor.profile.masterCustomerQualificationTb.invPicPath = responseData;
        					}
        					else{
        						$scope.investor.profile.masterCustomerQualificationTb = { 
        								"invPicPath" : responseData,
        								"registrationId" : $scope.investor.profile.registrationId
        						};
        					
        					}
        					$scope.investor.pic = _gc_url_baseUrl+"/faces/images?src="+responseData;
        					$('#investorPicMsg').text('File Uploaded Successfully.');
            				$('#investorPicMsg').css('color','green');

            				$scope.$apply();
            				toDataUrl($scope.investor.pic, function(base64Img) {
								$scope.investor.pic = base64Img;  
								$scope.$apply();
							});
            				
            				//Save image
            				$scope.saveInvestorProfile();

        				} else {
        					$('#investorPicMsg').text('File Upload Failed. Please Try Again.');
            				$('#investorPicMsg').css('color','red');
        				}        				
        			}
        	);
    	} else {
    		showErrorToast('File size greater than 2 MB.');
    	}
	}    
        
	$scope.uploadFileToServer = function(filePath,url,callbackFunction){
		var form = new FormData();
		form.append("file", filePath);
		form.append("email",$scope.investor.profile.email);
	
		var settings = {
		  "async": true,
		  "crossDomain": true,
		  "url": url,
		  "method": "POST",
		  "headers": {
		    "cache-control": "no-cache",
		    "Access-Control-Allow-Origin" : "*"
		  },
		  "processData": false,
		  "contentType": false,
		  "mimeType": "multipart/form-data",
		  "data": form
		}

		$.ajax(settings).done(function (response) {
			console.log(response);
			if(response.indexOf("error")==-1)
				callbackFunction(response);
			else {
				showErrorToast(response);
			}
		});
	}
	
	$scope.showEditProfile = function(){		
		$('#divProfileReadOnly').css('display','none');
		$('#divProfileEdit').css('display','block');
		$( '.js-float-label-wrapper' ).FloatLabel();
	}

	$scope.processChartData = function(data){
		$('#financialOutputChart').html('');
		//$scope.initData(data);
		initData(data);
    }    
	
	//Returns Input Text in Comma Separated Values Ex : 10,000
    $scope.formatInputString = function( text){
    	// console.log(text);
    	if(typeof text != 'undefined'){
    		return format(text);	
    	}else{
    		return "";	
    	}
    	
    }
    
  //Set Icons in Table View : FP
    $scope.getGoalDescriptionId = function(lifeGoalArr, age, goalFullfilled){
		var goalId = $.grep(lifeGoalArr, function(e){ return e.age == age; })[0].goalDescriptionId;
		var goalIconImage;		
		if(goalFullfilled)	//Show green lifegoal icon
			goalIconImage = "images/lifeGoalIcons/goal_icon_g"+goalId+".png";
		else
			goalIconImage = "images/lifeGoalIcons/goal_icon_"+goalId+".png"; 
		return goalIconImage;
	}
    
    $(document).ready(function(){
  	  var url = window.location.href;
        var urlpart =  url.split("src=");
        var rpsrc = urlpart[1];
        if(typeof rpsrc === "undefined"){
        	
        }
        else if(rpsrc.startsWith("upw")){
      	  $('#rpInfoModal').modal('show');
        }
      
        $('.numericOnly').keypress(function(key) {
        	if(key.charCode < 48 || key.charCode > 57) 
        		return false;
    	});
  });	
}]);
