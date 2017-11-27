app.controller('Home', ['$scope', function($scope) {
	
	$scope.val = 'wassup';
	
	$scope.page = {
			title: 'User Profile'
			
	};  
	
	$scope.investorLogin = {
			advisor : false,
			email : 'suhas',
			password : ''
	};
	
	$scope.advisorLogin = {
			advisor : true,
			email : '',
			password : ''
	};
	

	$scope.investorRegistration = {
			advisor : false,
			firstName : '',
			middleName : '',
			lastName : '',
			email: '',
			phone:'',
			password : '',
			rePassword : ''
	}
	
	$scope.advisorRegistration = {
			advisor : true,
			firstName : '',
			middleName : '',
			lastName : '',
			email: '',
			phone:'',
			password : '',
			rePassword : ''
	}
	investorRegistration = function(){
		
				var result = validateBeforeReistration();
				if (result) {
					showLoading();

					var formData = "advisor=false" +
					"&firstName=" + $('#formInvestorRegistration #firstName').val() +
					"&middleName=" + $('#formInvestorRegistration #middleName').val() +
					"&lastName=" + $('#formInvestorRegistration #lastName').val() +
					"&email=" + $('#formInvestorRegistration #email').val() +
					"&phone=" + $('#formInvestorRegistration #phone').val() +
					"&password=" + $('#formInvestorRegistration #password').val()

					var settings = {
					  "url": _gc_url_home_post_userRegistration,
					  "method": "POST",
					  "headers": {
					    "cache-control": "no-cache",
						"content-type": "application/x-www-form-urlencoded"
					  },
					  "processData": false,
					  "contentType": false,
					  "mimeType": "multipart/form-data",
					  "data": formData
					}

					$.ajax(settings).done(function (res) {
					 	console.log(res);
					 	clevertap.event.push("Attempted Investor Sign-Up");
					 					 	
					 	res = JSON.parse(res);
					 	var cleverUserFullName=$('#formInvestorRegistration #firstName').val()+" "+
			 			$('#formInvestorRegistration #middleName').val()+" "+ 
			 			$('#formInvestorRegistration #lastName').val();
					 	var cleverUserMail= $('#formInvestorRegistration #email').val();
					 	var cleverUserMobile ="+91"+$('#formInvestorRegistration #phone').val(); 
			 			
					 	var cleverProfile = {
					 		"Site": {
					 		   //CleverTap fields
					 		   "Name"  : cleverUserFullName,
			 				   "Email" : cleverUserMail,
			 				   "Phone" : cleverUserMobile,
			 				   "Identity": new Number(res.regId),
			 				   // optional fields. controls whether the user will be sent email, push etc.
			 				   "MSG-email": true, 
			 				   "MSG-push": true,  
			 				   "MSG-sms": true    
			 			}};
					 	clevertap.onUserLogin.push(cleverProfile);						

					 	if(res.status==false){
					 		hideLoading();
					 		$.toast(res.errorMsg);
					 	}else{
					 		try{
						 		if(res.url != '' && res.url != null){
						 			//hideLoading();
						 			window.location.href = res.url
							 	}	
						 	}
						 	catch(err){
						 		hideLoading();
						 		console.log(err);
						 	}
					 	}
					 	
					 	
					}).fail(function (res){
						console.log(res);
					});

				} else {
					console.log("validation before registration failed");
				}
								
			}
			
	advisorRegistration = function(){
				var result = validateBeforeAdvRegistartion();
				if (result){
					showLoading();
					
					
					var formData = "advisor=true" +
									"&firstName=" + $('#formAdvisorRegistration #firstName').val() +
									"&middleName=" + $('#formAdvisorRegistration #middleName').val() +
									"&lastName=" + $('#formAdvisorRegistration #lastName').val() +
									"&email=" + $('#formAdvisorRegistration #email').val() +
									"&phone=" + $('#formAdvisorRegistration #phone').val() +
									"&password=" + $('#formAdvisorRegistration #password').val()

					var settings = {
					  "url": _gc_url_home_post_userRegistration,
					  "method": "POST",
					  "headers": {
					    "cache-control": "no-cache",
						"content-type": "application/x-www-form-urlencoded"
					  },
					  "processData": false,
					  "contentType": false,
					  "mimeType": "multipart/form-data",
					  "data": formData
					}

					$.ajax(settings).done(function (res) {
					 	console.log(res);
					 	clevertap.event.push("Attempted Advisor Sign-Up");					 	
						
					 	res = JSON.parse(res);
					 	
					 	var cleverUserFullName=$('#formAdvisorRegistration #firstName').val()+" "+
							$('#formAdvisorRegistration #middleName').val()+" "+ 
							$('#formAdvisorRegistration #lastName').val();
						var cleverUserMail= $('#formAdvisorRegistration #email').val();
						var cleverUserMobile ="+91"+$('#formAdvisorRegistration #phone').val(); 
						
						var cleverProfile = {
							"Site": {
							   //CleverTap fields
							   "Name"  : cleverUserFullName,
							   "Email" : cleverUserMail,
							   "Phone" : cleverUserMobile,
			 				   "Identity": new Number(res.regId),
							// optional fields. controls whether the user will be sent email, push etc.
			 				   "MSG-email": true, 
			 				   "MSG-push": true,  
			 				   "MSG-sms": true   
						}};
						console.log("AdvisorRegistration")

						
						clevertap.onUserLogin.push(cleverProfile);

					 	if(res.status==false){
					 		hideLoading();
					 		$.toast(res.errorMsg);
					 	}else{
					 		try{
						 		if(res.url != '' && res.url != null){
						 			//hideLoading();
							 		window.location.href = res.url
							 	}	
						 	}
						 	catch(err){
						 		hideLoading();
						 		console.log(err);
						 	}
					 	}
					 	
					 	
					}).fail(function (res){
						hideLoading();
						console.log(res);
					});
				}				
			}
			
	
	cleverTapLogin = function(userMail,userType){
				clevertap.event.push("Attempted "+userType+" Login");
	 			clevertap.onUserLogin.push({
	 				 "Site": {
	 					"Email": userMail,
	 					"Customer Type": userType
	 				 }
	 			});
			}
	//Shubhada 22-02-2017 TwoFactorLogin start
	// /twoFactorLogin
	validateTwoFactor = function(){
		
		if($('#loginDobForm #calendar').val()== 'DD-MM-YYYY'||$('#loginDobForm #calendar').val()==''||$('#loginDobForm #calendar').val()==null)
		{
		$.toast({
		heading: 'Error',
			text: 'Please Enter Date of Birth.',
			showHideTransition: 'fade',
			icon: 'error',
                        hideAfter : 10000
		});
		return false;
		}
		showLoading(); 
		var formData =  "calendar=" + $('#loginDobForm #calendar').val();
		
		var settings = {
		  "url": _gc_url_twoFactor,
		  "method": "POST",
		  "headers": {
		    "cache-control": "no-cache",
			"content-type": "application/x-www-form-urlencoded"
		  },
		  "processData": false,
		  "contentType": false,
		  "mimeType": "multipart/form-data",
		  "data": formData
		}
		$.ajax(settings).done(function (res) {
		 	res = JSON.parse(res);
								
		if(res.url.indexOf('login_linkedin_investor')!= -1){	
				res.url="/faces/pages/login_linkedin_investor.xhtml?faces-redirect=true";
				window.location.href = _gc_url_baseUrl+  res.url;				
		}
		else if(res.url.indexOf('login_linkedin_advisor')!= -1){
			res.url = "/faces/pages/login_linkedin_advisor.xhtml?faces-redirect=true";
			window.location.href = _gc_url_baseUrl+ res.url;
		}
		else if(res.url.indexOf('advisor_network_view')!= -1)
		{	
			res.url="/faces/pages/advisor_network_view.xhtml?faces-redirect=true";
			window.location.href = _gc_url_baseUrl+ res.url;
		}
		else if(res.url.indexOf('investor_network_view')!= -1)
		{	
			//res.url="/faces/pages/investor_network_view.xhtml?faces-redirect=true";
			res.url="/faces/pages/v3/advisor-select.jsp?faces-redirect=true";
			window.location.href = _gc_url_baseUrl+ res.url;
		}
		//Dashboard Links for LinkedIn Connected Users
		else if(res.url.indexOf('advisordashboard')!= -1)
		{	
			//res.url="/faces/pages/advisordashboard.xhtml?faces-redirect=true";
			res.url="/faces/pages/v3/advisorDashboard.jsp?faces-redirect=true";
			window.location.href = _gc_url_baseUrl+ res.url;
		}
		else if(res.url.indexOf('investordashboard')!= -1)
		{	
			//res.url="/faces/pages/investordashboard.xhtml?faces-redirect=true";
			res.url="/faces/pages/v3/investor-dashboard.jsp?faces-redirect=true";
			window.location.href = _gc_url_baseUrl+ res.url;
		}
		else if(res.url.indexOf('jsp')!= -1)
		{	
			//console.log(res.url);
			window.location.href = _gc_url_baseUrl+ res.url;
		}
		else if(res.status=false)
			{
				console.log("status :"+res.status);
				console.log(res.url+' : '+res.errorMsg);
				//hideLoading();
				$.toast({
					heading: 'Error',
					text: res.errorMsg,
					showHideTransition: 'fade',
					icon: 'error',
                                        hideAfter : 10000
				});
//				$("#divLogin").show();
//				$("#divDob").hide();
			}
		else
		{
			console.log("status :"+res.status);
			console.log(res.url+' : '+res.errorMsg);
			//hideLoading();
			$.toast({
				heading: 'Error',
				text: res.errorMsg+" cause unknown",
				showHideTransition: 'fade',
				icon: 'error',
                                hideAfter : 10000
			});
//			$("#divLogin").show();
//			$("#divDob").hide();
		}
	
		}).fail(function (res){
			console.log(res);
		});				
	}

	
	userLogin = function(){
				showLoading(); 
				if( validateCaptcha() == false){
					return;

				}				
				var formData =  "&userEmail=" + $('#loginForm #email').val() +
						"&userPassword=" + $('#loginForm #password').val();

				var settings = {
				  "url": _gc_url_home_post_userLogin,
				  "method": "POST",
				  "headers": {
				    "cache-control": "no-cache",
					"content-type": "application/x-www-form-urlencoded"
				  },
				  "processData": false,
				  "contentType": false,
				  "mimeType": "multipart/form-data",
				  "data": formData
				}
				$.ajax(settings).done(function (res) {
				 	console.log(res);
				 	
					res = JSON.parse(res);
					//console.log(res+" : ajax block");
					if(res.status==false){
						//console.log(res+" :if block");
						hideLoading();
				 		$.toast({
						    heading: 'Error',
						    text: res.errorMsg,
						    showHideTransition: 'fade',
						    icon: 'error',
                                                    hideAfter: 10000
						});
						$("#divLogin").show();
						$("#divDob").hide();
				 	}else {
				 		try{
				 			if (res.url.indexOf('/twofactor_login2.xhtml') == -1){
				 				window.location.href = res.url;
				 			} else {
				 				//is 2 factor
				 				if(res.url != '' && res.url != null){
						 			hideLoading();
						 			cleverTapLogin($('#loginForm #email').val(),"Investor");
						 			//window.location.href = res.url;
									//console.log(res+" :try block");
						 			$("#divLogin").hide();
									$("#divDob").show();					 								 			
							 	}
				 			}
				 			
					 			
					 	}
					 	catch(err){
					 		hideLoading();
					 		//console.log(err);
					 		//console.log(res+" :catch block");
					 		$("#divLogin").show();
							$("#divDob").hide();
					 	}
				 	}
				}).fail(function (res){
					hideLoading();
					//console.log(res+" :fail block");
					$("#divLogin").show();
					$("#divDob").hide();
				});				
			}

			

    investorLogin = function(){
				showLoading(); 
				var formData = "user_login_type=INVESTOR" + 
						"&userEmail=" + $('#formInvestorLogin #email').val() +
						"&userPassword=" + $('#formInvestorLogin #password').val();

				var settings = {
				  "url": _gc_url_home_post_userLogin,
				  "method": "POST",
				  "headers": {
				    "cache-control": "no-cache",
					"content-type": "application/x-www-form-urlencoded"
				  },
				  "processData": false,
				  "contentType": false,
				  "mimeType": "multipart/form-data",
				  "data": formData
				}

				$.ajax(settings).done(function (res) {
//				 	console.log(res);
				 	
					res = JSON.parse(res);
					if(res.status==false){
						hideLoading();
				 		$.toast({
						    heading: 'Error',
						    text: res.errorMsg,
						    showHideTransition: 'fade',
						    icon: 'error',
                                                    hideAfter: 10000
						});
				 	}else {
				 		try{
					 		if(res.url != '' && res.url != null){
					 			hideLoading();
					 			cleverTapLogin($('#formInvestorLogin #email').val(),"Investor");
//					 			clevertap.event.push("Attempted Investor Login");
//					 			
//					 			var cleverUserMail=$('#formInvestorLogin #email').val();
//					 			console.log("Investor Mail : "+cleverUserMail);
//					 			clevertap.onUserLogin.push({
//					 				 "Site": {
//					 					"Email": cleverUserMail,
//					 					"Customer Type": "Investor"
//					 				 }
//					 			});
					 			window.location.href = res.url;					 								 			
						 	}	
					 	}
					 	catch(err){
					 		hideLoading();
					 		console.log(err);
					 	}
				 	}
				}).fail(function (res){
					hideLoading();
					console.log(res);
				});
			}
			
			 advisorLogin = function(){
				 showLoading();
				var formData = "user_login_type=ADVISOR" + 
								"&userEmail=" + $('#formAdvisorLogin #email').val() +
								"&userPassword=" + $('#formAdvisorLogin #password').val();

				var settings = {
				  "url": _gc_url_home_post_userLogin,
				  "method": "POST",
				  "headers": {
				    "cache-control": "no-cache",
					"content-type": "application/x-www-form-urlencoded"
				  },
				  "processData": false,
				  "contentType": false,
				  "mimeType": "multipart/form-data",
				  "data": formData
				}

				$.ajax(settings).done(function (res) {
				 	console.log(res);
					res = JSON.parse(res);
					if(res.status==false){
						hideLoading();
				 		$.toast({
						    heading: 'Error',
						    text: res.errorMsg,
						    showHideTransition: 'fade',
						    icon: 'error',
                                                    hideAfter : 10000
						});
				 	}else {
				 		try{
					 		if(res.url != '' && res.url != null){
					 			hideLoading();
					 			cleverTapLogin($('#formAdvisorLogin #email').val(),"Advisor");
//					 			clevertap.event.push("Attempted Advisor Login");
//					 			var cleverUserMail=$('#formAdvisorLogin #email').val();
//					 			console.log("Advisor Mail : "+cleverUserMail);
//					 			clevertap.onUserLogin.push({
//					 				 "Site": {
//					 					"Email": cleverUserMail,
//					 					"Customer Type": "Advisor"
//					 				 }
//					 			});
					 			window.location.href = res.url;
						 	}	
					 	}
					 	catch(err){
					 		hideLoading();
					 		console.log(err);
					 	}
				 	}
				}).fail(function (res){
					hideLoading();
					console.log(res);
				});
			}

			 validateEmail = function(inpEmail){
				//console.log(inpEmail.value);
				 
			   if(!isValidEmailAddress(inpEmail.value)){
					 $.toast({
					 heading: 'Error',
					 text: 'Invalid Email Address',
					 showHideTransition: 'fade',
					 icon: 'error',
                                         hideAfter : 10000
					 });
					 }
				var form = new FormData();
				form.append("type", "emailId");
				form.append("emailId", inpEmail.value);
				var formData = "type=emailId"+
								"&emailId="+inpEmail.value;
				var settings = {
				  "async": true,
				  "crossDomain": true,
				  "url": _gc_url_home_post_validateData,
				  "method": "POST",
				  "headers": {
				    "cache-control": "no-cache",
					"content-type": "application/x-www-form-urlencoded"
				  },
				  "processData": false,
				  "contentType": false,
				  "mimeType": "multipart/form-data",
				  "data": formData
				}

				$.ajax(settings).done(function (res) {
					console.log(res);
					res = JSON.parse(res);
				 	if(res.status==true){
				 		$.toast({
						    heading: 'Error',
						    text: res.errorMsg,
						    showHideTransition: 'fade',
						    icon: 'error',
                                                    hideAfter : 10000
						});
				 	}
				}).fail(function (response){
					alert(response);
				});
			}
			
			//snehal
		 	 validateLoginEmail = function(inpEmail){   
		 		//console.log(inpEmail.value);
				if(!isValidEmailAddress(inpEmail.value)){
					 $.toast({
					 heading: 'Error',
					 text: 'Invalid Email Address',
					 showHideTransition: 'fade',
					 icon: 'error',
                                         hideAfter : 10000
					 });
					 }
			} 
			//snehal
			
			//sumeet start
			 validateInvPassword = function(rePassword){
				var password = $('#formInvestorRegistration #password').val();
            	        var patt = new RegExp("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&'()*+,-./:;<=>?@\[\\]^_`{|}~]).{6,20})");
		        var res = patt.test(password);
                        console.log(res);
				console.log('pass :'+password);
				console.log('rePass :'+rePassword.value);
				if (!res) {
    				$scope.alertMessage='Password must have minimum 6 characters and atleast one number, uppercase letter, lowercase letter and special character.';
    				$.toast({
					    heading: 'Error',
					    text: $scope.alertMessage,
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
    			}
				if(password != rePassword.value){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
				}
			} 
			validateAdvPassword = function(rePassword){
				var password = $('#formAdvisorRegistration #password').val();
            	        var patt = new RegExp("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&'()*+,-./:;<=>?@\[\\]^_`{|}~]).{6,20})");
		        var res = patt.test(password);
				// console.log('pass :'+password);
				if (!res) {
    				$scope.alertMessage='Password must have minimum 6 characters and atleast one number, uppercase letter, lowercase letter and special character.';
    				$.toast({
					    heading: 'Error',
					    text: $scope.alertMessage,
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
    			}
				// console.log('rePass :'+rePassword.value);
				if(password != rePassword.value && res){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
				}
			}
			 
			 
			validateBeforeReistration =  function()
			{	
				//changes start
				splitFullName();
				//changes end
				 var fname = $('#formInvestorRegistration #firstName').val();
				if(fname== null || fname.trim() == ''||fname == 'First Name')
				{
				$.toast({
				heading: 'Error',
					text: 'Please Enter First Name.',
					showHideTransition: 'fade',
					icon: 'error',
                                        hideAfter : 10000
				});
				return false;
				}
				
				/*var mname = $('#formInvestorRegistration #middleName').val();
				if (mname == 'Middle Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Middle Name.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}*/
				
				var lname = $('#formInvestorRegistration #lastName').val();
				if (lname== null || lname.trim() == ''|| lname == 'Last Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Last Name.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
				}
				
				var number = $('#formInvestorRegistration #phone').val();
				if(number  == 'Mobile Number') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000

					});
					return false;
				}else if (number.length != 10) {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Valid Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
				}
				
				var email = $('#formInvestorRegistration #email').val();
				if (email== null || email.trim() == ''|| email == 'Email') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Email',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				} else {
					if( !isValidEmailAddress( email ) ) { 
						$.toast({
						    heading: 'Error',
						    text: 'Invalid Email Id',
						    showHideTransition: 'fade',
						    icon: 'error',
                                                    hideAfter : 10000
						})
						return false;
					}

				}
				
				// var pass = $('#formInvestorRegistration #password').val();
				var password = $('#formInvestorRegistration #password').val();
				var repass = $('#formInvestorRegistration #rePassword').val();
            	                var patt = new RegExp("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&'()*+,-./:;<=>?@\[\\]^_`{|}~]).{6,20})");
		        var res = patt.test(password);
				// console.log('pass :'+password);
				if (!res) {
    				$scope.alertMessage='Password must have minimum 6 characters and atleast one number, uppercase letter, lowercase letter and special character.';
    				$.toast({
					    heading: 'Error',
					    text: $scope.alertMessage,
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
    			}
				// console.log('rePass :'+rePassword.value);
				if(password != rePassword.value && res){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
				}
				if(password == 'Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Password',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}
				
				
				if(repass == 'Re-enter Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Re-enter Password',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}
				
				if(password != repass){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}
				
				return true;
			} 
			
			validateBeforeAdvRegistartion = function()
			{
				//changes start
				splitFullName();
				//changes end
				 var fname = $('#formAdvisorRegistration #firstName').val();
				
				if(fname== null || fname.trim() == ''||fname == 'First Name'){
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter First Name.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
				}
				
				/*var mname = $('#formAdvisorRegistration #middleName').val();
				if (mname == 'Middle Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Middle Name.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}*/
				
				var lname = $('#formAdvisorRegistration #lastName').val();
				if (lname== null || lname.trim() == ''|| lname == 'Last Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Last Name.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
				}
				
				var number = $('#formAdvisorRegistration #phone').val();
				if(number  == 'Mobile Number') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
				}else if (number.length != 10) {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Valid Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
				}
				
				var email = $('#formAdvisorRegistration #email').val();
				if (email== null || email.trim() == ''|| email == 'Email') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Email',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				} else {
					if( !isValidEmailAddress( email ) ) { 
						$.toast({
						    heading: 'Error',
						    text: 'Invalid Email Id',
						    showHideTransition: 'fade',
						    icon: 'error',
                                                    hideAfter : 10000
						})
						return false;
					}

				}
				
				var pass = $('#formAdvisorRegistration #password').val();
				var repass = $('#formAdvisorRegistration #rePassword').val();
            	                var patt = new RegExp("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%&'()*+,-./:;<=>?@\[\\]^_`{|}~]).{6,20})");
		        var res = patt.test(pass);
		        if (!res) {
    				$scope.alertMessage='Password must have minimum 6 characters and atleast one number, uppercase letter, lowercase letter and special character.';
    				$.toast({
					    heading: 'Error',
					    text: $scope.alertMessage,
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					});
					return false;
    			}
				if(pass == 'Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Password',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}
				
				// var repass = $('#formAdvisorRegistration #rePassword').val();
				if(repass == 'Re-enter Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Re-enter Password',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}
				
				if(pass != repass){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error',
                                            hideAfter : 10000
					})
					return false;
				}
				
				return true;
			
			}
			
			 isValidEmailAddress = function(emailAddress) {
			    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
			    return pattern.test(emailAddress);
			};
			
		splitFullName = function(){
		//var nameVar = $('#formAdvisorRegistration #fullName').val();
		var nameVar= document.getElementById("fullName").value;
		nameVar = nameVar.replace(/  +/g, ' ');
		nameVar = nameVar.trim();
		var arr = nameVar.split(' ');
		var fname=arr[0];
		var mname="";
		var lname="";
		// console.log(arr.length);
		if(arr.length > 1){
			if(arr.length == 2){
				lname = arr[1];
			}
			else{
				mname=arr[1];        
				var text = "";
				for (i = 2; i < arr.length; i++) {
					text += arr[i].trim() + " ";
				}
				lname = text.trim();    		
			}
			//console.log(fname+":"+mname+":"+lname);
		}
	   document.getElementById("firstName").value = fname;
	   document.getElementById("middleName").value = mname;
	   document.getElementById("lastName").value = lname;

	}
				
            $(document).ready(function() {
                var url = window.location.href;
                var param = url.split("?src=");
                if(param.length != 1){
                    var type = param[1].charAt(0);
                    if (type === '1') {
                    	//common screen
                    	//$('#carousel-example-vertical').carousel(1);
                    	$("#divTwo").parent().addClass("active");
                    } else if (type === '2') {
                    	//investor login reg screen
                    	//$('#carousel-example-vertical').carousel(2);
                    	$("#divThree").parent().addClass("active");
                    } else if (type === '3') {
                    	//advisor login reg screen
                    	//$('#carousel-example-vertical').carousel(2);
                    	$("#divFour").parent().addClass("active");
                    }
                }
                
                var param1 = url.split("&vr=");
                if(param1.length != 1){
                    type = param1[1];
                    if (type === 'true') {
                    	$.toast({
    					    heading: 'Success',
    					    text: 'Email Verified.',
    					    showHideTransition: 'fade',
    					    icon: 'success',
                                            hideAfter : 10000
    					});
                    }
                    
                    //CleverTap Code: Advisor Verified Email
                    var val1=param[1].charAt(0);
                    var val2=param1[1];
                    if(val1 === '3' && val2=== 'true')
                    {
                    	clevertap.event.push('Email Verified for Advisor');
                    }
                }

            });
            
}]);
