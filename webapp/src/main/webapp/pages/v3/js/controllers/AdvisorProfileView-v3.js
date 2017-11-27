
app.controller('AdvisorProfileView', ['$scope', function($scope) {
	$scope.page = {
			title: 'Advisor Portfolio'
	};
	$scope.advProfilePic;
	$scope.fetchActionFilter =( $('#userUnavailable').val() == 'true') ? true : false;
	$scope.riskProfileNotCompleted = false;
	$scope.fpNotCompleted = false;
	$scope.advisor = {
			profile : '',
			portfolios : '',
			testimonials : '',
			publications : ''
	}
	$scope.selectedPortfolio;

	$scope.init = function(){
		// $( '.js-float-label-wrapper' ).FloatLabel();
		/*$scope.getAdvisorProfile();
		$scope.getAdvisorPerformance();
		$scope.getAdvisorTestimonials();
		$scope.getAdvisorPublications();*/
		$scope.getProfileCompletionStatus();
		$scope.test();
	}
	
	$scope.getProfileCompletionStatus = function (){
		//FP
		$.ajax({
			type: 'POST',
			url : _gc_url_fpStatus,
			success: function(rdata){
				console.log("FP : "+rdata);
				$scope.fpNotCompleted = ( rdata == 'true') ? true : false;
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
		//RP
		$.ajax({
			type: 'POST',
			url : _gc_url_rpStatus,
			success: function(rdata){
				console.log("RP : "+rdata);
				$scope.riskProfileNotCompleted = ( rdata == 'true') ? true : false;
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
		//total funds
		$.ajax({
			type: 'POST',
			url : _gc_url_getTotalFunds,
			success: function(rdata){
				console.log("TF : "+rdata);
				$scope.totalFunds = ( rdata == 'Error:userFunds') ? 0 : new Number(rdata);
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
	
	$scope.getPortfolio = function (port,id){
		if(port !=null){
			var form = new FormData();
	        form.append("portolio", port);
	        form.append("userId", id);
			var settings = {
			"async": true,
	          "crossDomain": true,
	          "url": _gc_url_selectedPortfolio,
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
			
			$.ajax(settings).done(function (res) {
				//console.log(res);
				res = JSON.parse(res);
				// console.log('Requested:');
				// console.log(res);
				$scope.item = res.portfolios[0];
				$scope.selectedPortfolio = res.portfolios[0];
				if($scope.fetchActionFilter){
					//User Unavailable
		    		//Set Default Text
					$scope.item.actionText  = 'Invite';
				}
				else{
//					$scope.item.actionText=$scope.getActionText(port,id);
					$scope.getActionText(port,id,$scope.item.portfolioId);
				}
				$scope.$apply();
			}).fail(function (response){
				alert(response);
			});
		}		
	}
	
	$scope.getAdvisorPortfolios = function (id,port){
		var form = new FormData();
        form.append("userId", id);
        form.append("port",port);
		var settings = {
		"async": true,
          "crossDomain": true,
          "url": _gc_url_advisorPortfolios,
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
		
		$.ajax(settings).done(function (res) {
//			console.log('Advisor Portfolios');
			res = JSON.parse(res);
			// console.log('ALL');
			// console.log(res);
			$scope.advisor.portfolios = res.portfolios;
			
			//Assign ActionText
			angular.forEach($scope.advisor.portfolios,function(value,index){
				if($scope.fetchActionFilter){
					//User Unavailable
		    		//Set Default Text
					$scope.advisor.portfolios[index].actionText  = 'Invite';
				}else{
					//$scope.advisor.portfolios[index].actionText =  $scope.getActionText($scope.advisor.portfolios[index].portfolioName,$scope.advisor.portfolios[index].advId);
					$scope.getActionText($scope.advisor.portfolios[index].portfolioName,$scope.advisor.portfolios[index].advId,$scope.advisor.portfolios[index].portfolioId);
				}				
			});
			$scope.$apply();
		}).fail(function (response){
			alert(response);
		});
	}

	$scope.test = function (){
		var baseurl = window.location.href;
		var paramsurl =  baseurl.split('?');
		var paramSub = paramsurl[1];
		var params = paramSub.split('&');
		var advId ;
		var portfolio;
		for(var i = 0;i<params.length;i++){
//			console.log(params[i]);
			if(params[i].indexOf('advId') == 0 ){
				advId = params[i].split('=')[1]; 
			} else if(params[i].indexOf('id') == 0 ){
				advId = params[i].split('=')[1]; 				
			} else if(params[i].indexOf('port')==0){
				portfolio = params[i].split('=')[1]; 
			}
		}
//		console.log(advId);
//		console.log(portfolio);
		if(portfolio != null){
				portfolio = decodeURIComponent(portfolio);
//				console.log(portfolio);
		}
		var settings = {
				  "async": true,
				  "crossDomain": true,
				  "url":  _gc_url_advisorProfileInfo,//"/faces/userProfile",
				  "method": "POST",
				  "headers": {
				    "cache-control": "no-cache",
					"content-type": "application/x-www-form-urlencoded"
				  },
				  "processData": false,
				  "contentType": false,
				  "mimeType": "multipart/form-data",
				  "data": "viewOnly=true&id="+advId+"&userType=ADVISOR"
				}

				$.ajax(settings).done(function (rData) {
//					console.log(rData);
					rData = JSON.parse(rData);
				 	//console.log(rData);
					$scope.advisor = rData;

					if($scope.advisor.masterAdvisorQualificationTb != null){
						if($scope.advisor.masterAdvisorQualificationTb.advPicPath != null && $scope.advisor.masterAdvisorQualificationTb.advPicPath != "")
						{
							//Set localhost path
							$scope.advProfilePic = "../../images?src="+$scope.advisor.masterAdvisorQualificationTb.advPicPath+"";
						}else{
							//Set default pic
							$scope.advProfilePic = "images/advisor_profile_photo.png";
						}
					}

					$scope.$apply();
					$scope.getPortfolio(portfolio,advId);
					$scope.getAdvisorPortfolios(advId,''); //portfolio name is not passed, need all advisor portfolio
				}).fail(function (response){
					alert(response);
				});
	}

	$scope.getAdvisorProfile = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorProfile,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					console.log(rData);
					$scope.advisor.profile = rData;
					$scope.$apply();
					$('#divRatings').raty({ 'readOnly': true,score: $scope.advisor.profile.userRating});
				}
				catch(e){
					$('#divContent').css("display","block");
					$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	
	$scope.getAdvisorPerformance = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorPerformance,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					console.log(rData);
					$scope.advisor.portfolios = rData.portfolios;
					console.log($scope.advisor.portfolios);
					$scope.$apply();
				}
				catch(e){
					//$('#divContent').css("display","block");
					//$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	

	$scope.getAdvisorTestimonials = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorTestimonials,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					console.log(rData);
					$scope.advisor.testimonials = rData.custTestimonials;
					console.log($scope.advisor.testimonials);
					$scope.$apply();
				}
				catch(e){
					//$('#divContent').css("display","block");
					//$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	
	$scope.getAdvisorPublications = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorPublications,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					$scope.advisor.publications = rData.publications;
					console.log($scope.advisor.publications);
					$scope.$apply();
				}
				catch(e){
					//$('#divContent').css("display","block");
					//$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	
	$scope.addPreviousOrg = function(){
		$scope.advisor.profile.previousOrg.push({$$hashKey:"",fromDate:"",orgName:"",title:"",toDate:""});
		$scope.$apply();
	}
	
	$scope.addQualification = function(){
		$scope.advisor.profile.qualificationDetails.push({degree:"",institute:"",year:""});
		$scope.$apply();
	}
	
	$scope.addPublications = function(){
		$scope.advisor.publications.push({name:"",link:""});
		$scope.$apply();
	}
	
	$scope.showEditProfile = function(){
		
		$('#divProfileReadOnly').css('display','none');
		$('#divProfileEdit').css('display','block');
	}
	
	$scope.showEditPublications = function(){
		
		$('#divPublicationsReadOnly').css('display','none');
		$('#divPublicationsEdit').css('display','block');
	}
	

	$scope.saveAdvisorProfile = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_saveAdvisorProfile,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				alert('Profile Saved');
				$('#divProfileReadOnly').css('display','block');
				$('#divProfileEdit').css('display','none');
			},
			error: function(){
				alert('Save failed');
				location.reload();
			},
			fail: function(){
				alert('Save failed');
				location.reload();
			}
		});
	}
	
	$scope.saveAdvisorPublications = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_saveAdvisorPublication,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				alert('Profile Saved');
				$('#divPublicationReadOnly').css('display','block');
				$('#divPublicationEdit').css('display','none');
			},
			error: function(){
				alert('Save failed');
				$('#divPublicationReadOnly').css('display','block');
				$('#divPublicationEdit').css('display','none');
			},
			fail: function(){
				alert('Save failed');
				$('#divPublicationReadOnly').css('display','block');
				$('#divPublicationEdit').css('display','none');
			}
		});
	}
	
	setTimeout(function(){
		displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();
		}, 10);
	
	$scope.callToAction = function(portfolio,avdId,portId,minInvest){
		//Only if FP and RP complete, then proceed with action
    	console.log("FP : "+ $scope.riskProfileNotCompleted);
    	console.log("RP : "+ $scope.fpNotCompleted);
    	if($scope.riskProfileNotCompleted == true || $scope.fpNotCompleted == true){
    		var msg = "Profile Completion Check";
    		if($scope.fpNotCompleted == true && $scope.riskProfileNotCompleted == true){
    			msg = "Please complete your Financial Planner and Risk Profile Questionnaire to Proceed";
			}else if($scope.fpNotCompleted == true){
    			msg = "Please complete your Financial Planner to Proceed";
    		}
    		else if($scope.riskProfileNotCompleted == true)
    		{
    			msg = "Please complete your Risk Profile Questionnaire to Proceed";
    		}
    		alert(msg);
    		console.log("Profile Incomplete");
    	} else if ($scope.totalFunds < minInvest) {
    		msg = "You have insufficient funds.\n To take this portfolio Please transfer more funds in your MMF Account.";
    		alert(msg);
    		console.log("Profile Incomplete");
    	}
    	else{
    		//Disable the button
        	$("#button-"+avdId+"-"+portfolio).attr("disabled","disabled").css('opacity',0.5);
    		//Disable other buttons for same advisor
//    		$('[id^="button-'+avdId+'-"]').attr("disabled","disabled").css('opacity',0.5);
        	//Hide the old result msg divs
        	$('[id^="div-'+avdId+'-"]').hide();
        	showLoading();
        	var actionText=$("#button-"+avdId+"-"+portId).val().trim(); //text
        	if(actionText == ''){
        		$("#button-"+avdId+"-"+portId).val('Cancel'); //text
        	}
        	console.log("Invoking Action : "+actionText+" for Portfolio :"+portfolio+" of Advisor "+ avdId);
        	if (actionText === "Invite Advisor"){
        		clevertap.event.push("Invite Advisor");
        	}
            var settings = {
        	  "async": true,
      		  "crossDomain": true,
      		  "url": _gc_url_portfolioInvestorAction,//"/faces/investmentAdvProfile",
      		  "method": "POST",
      		  "headers": {
      			    "cache-control": "no-cache",
      				"content-type": "application/x-www-form-urlencoded"
      		  },
              "processData": false,
              "contentType": false,
              "mimeType": "multipart/form-data",
              "data":"action="+actionText+"&portfolio="+portfolio+"&advId="+avdId
    		}
    		
    		$.ajax(settings).done(function (res) {
    			try{
    	//			console.log(res);
    				res = JSON.parse(res);			
    				console.log(res);
    				if(res != null && res.redirectUrl != null){
    					if(res.redirectUrl.includes('contract')  || res.redirectUrl.includes('portfolioAllocation')){
    						//Redirect to another page
    						window.location.href = res.redirectUrl;						
    					}else if(res.redirectUrl.includes('investordashboard')){
    						$("#div-"+avdId+"-"+portId).text("You already signed contract with an advisor").css({"text-align":"center","font-weight": "bold","font-size":"18px"}).show();
    					}
    				}else if(res != null && res.msg != null){
    					//Show status message
    					$("#div-"+avdId+"-"+portId).text(res.msg).css({"text-align":"center","font-weight": "bold","font-size":"18px"}).show();				
    					if(res.msg.includes('success')){
    						//Change button texts, enable current portfolio and disable other portfolios of same advisor 
    						if (actionText === "Invite Advisor") {
    							$('[id^="button-'+avdId+'-"]').val('Invite Advisor').attr("disabled","disabled").css('opacity',0.5).show();
    							$("#button-"+avdId+"-"+portId).val('Withdraw').attr("disabled", false).css('opacity',1);							
    					    } else if (actionText === "Withdraw") {
    					    	$('[id^="button-'+avdId+'-"]').val('Invite Advisor').attr("disabled",false).css('opacity',1).show();
    					    }
    					}
    				}
    				hideLoading();
    			}catch(e){
    				console.log('Error');
    				hideLoading();
    			}
    			
    		}).fail(function (response){
    			console.log(response);
    		});	
    	}
    	
    	
        
    }
    
    $scope.getActionText = function(portfolio,advId,portId){
    	if(portfolio != null && advId != null){
        	var settings = {
      		  "async": true,
    		  "crossDomain": true,
    		  "url": _gc_url_portfolioInvestorAction,//"/faces/investmentAdvProfile",
    		  "method": "POST",
    		  "headers": {
    			    "cache-control": "no-cache",
    				"content-type": "application/x-www-form-urlencoded"
    		  },
      		  "processData": false,
      		  "contentType": false,
      		  "mimeType": "multipart/form-data",
      		  "data":"fetch=true&advId="+advId+"&portfolio="+portfolio
          	}
    	    
    		$.ajax(settings).done(function (res) {
    			try{
//    				console.log(res);
    				res = JSON.parse(res);
//    				console.log(res);
    				if(res.length != 0) {					
//    					resultDesc=res[0];
//    					$("#button-"+advId+"-"+portfolio).text(res[0]);		
    					var indexInvite = res.indexOf("Invite Advisor");
    					var indexWithdraw = res.indexOf("Withdraw");
    					if(indexInvite == -1 && indexWithdraw == -1){
    						//Hide the button
    						$("#button-"+advId+"-"+portId).hide();
    					}else{
    						if(indexInvite != -1){
    							//Set the button text to Invite
    							$("#button-"+advId+"-"+portId).val(res[indexInvite]); //text
    						}	
    						if( indexWithdraw != -1){
    							//Set the button text to Withdraw
    							$("#button-"+advId+"-"+portId).val(res[indexWithdraw]); //text
    						}				
    					}
    				}				
    			}catch(e){
    				console.log('Error');
    			}
    		}).fail(function (response){
    			console.log(response);
    		});    		
    	}
    }

}]);