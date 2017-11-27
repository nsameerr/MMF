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
//					var form = new FormData();
//					form.append("user_login_type", $('#formInvestorRegistration #user_login_type').val());
//					form.append("firstName", $('#formInvestorRegistration #firstName').val());
//					form.append("middleName", $('#formInvestorRegistration #middleName').val());
//					form.append("lastName", $('#formInvestorRegistration #lastName').val());
//					form.append("email", $('#formInvestorRegistration #email').val());
//					form.append("phone", $('#formInvestorRegistration #phone').val());
//					form.append("password", $('#formInvestorRegistration #password').val());
					
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
//					var form = new FormData();
//					form.append("user_login_type", $('#formAdvisorRegistration #user_login_type').val());
//					form.append("firstName", $('#formAdvisorRegistration #firstName').val());
//					form.append("middleName", $('#formAdvisorRegistration #middleName').val());
//					form.append("lastName", $('#formAdvisorRegistration #lastName').val());
//					form.append("email", $('#formAdvisorRegistration #email').val());
//					form.append("phone", $('#formAdvisorRegistration #phone').val());
//					form.append("password", $('#formAdvisorRegistration #password').val());
					
					
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
//						console.log(JSON.parse(cleverProfile));
						
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
			
			 investorLogin = function(){
				showLoading(); 
				
				//var form = new FormData();
				//form.append("user_login_type", "INVESTOR");
				//form.append("userEmail", $('#formInvestorLogin #email').val());
				//form.append("userPassword", $('#formInvestorLogin #password').val());
				
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
						    icon: 'error'
						});
				 	}else {
				 		try{
					 		if(res.url != '' && res.url != null){
					 			hideLoading();
					 			window.location.href = res.url
					 			
					 			clevertap.event.push("Attempted Investor Login");
					 			
					 			var cleverUserMail=$('#formInvestorLogin #email').val();
//					 			console.log("Investor Mail : "+cleverUserMail);
					 			clevertap.onUserLogin.push({
					 				 "Site": {
					 					"Email": cleverUserMail,
					 					"Customer Type": "Investor"
					 				 }
					 			});
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
				//var form = new FormData();
				//form.append("user_login_type", "ADVISOR");
				//form.append("userEmail", $('#formAdvisorLogin #email').val());
				//form.append("userPassword", $('#formAdvisorLogin #password').val());
				
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
						    icon: 'error'
						});
				 	}else {
				 		try{
					 		if(res.url != '' && res.url != null){
					 			hideLoading();
					 			clevertap.event.push("Attempted Advisor Login");
					 			var cleverUserMail=$('#formAdvisorLogin #email').val();
					 			console.log("Advisor Mail : "+cleverUserMail);
					 			clevertap.onUserLogin.push({
					 				 "Site": {
					 					"Email": cleverUserMail,
					 					"Customer Type": "Advisor"
					 				 }
					 			});
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
			
			 validateEmail = function(inpEmail){
				//console.log(inpEmail.value);
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
						    icon: 'error'
						});
				 	}
				}).fail(function (response){
					alert(response);
				});
			}
			//sumeet start
			 validatePassword = function(rePassword){
				var password = $('#formInvestorRegistration #password').val();
				//console.log(password);
				//console.log(rePassword.value);
				if(password != rePassword.value){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error'
					});
				}
			} 
			 
			 
			validateBeforeReistration =  function (){
				
				 var fname = $('#formInvestorRegistration #firstName').val();
				
				if(fname == 'First Name'){
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter First Name.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				/*var mname = $('#formInvestorRegistration #middleName').val();
				if (mname == 'Middle Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Middle Name.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}*/
				
				var lname = $('#formInvestorRegistration #lastName').val();
				if (lname == 'Last Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Last Name.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				var number = $('#formInvestorRegistration #phone').val();
				if(number  == 'Mobile Number') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error'
					});
					return false;
				}else if (number.length != 10) {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Valid Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error'
					});
					return false;
				}
				
				var email = $('#formInvestorRegistration #email').val();
				if (email == 'Email') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Email',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				} else {
					if( !isValidEmailAddress( email ) ) { 
						$.toast({
						    heading: 'Error',
						    text: 'Invalid Email Id',
						    showHideTransition: 'fade',
						    icon: 'error'
						})
						return false;
					}

				}
				
				var pass = $('#formInvestorRegistration #password').val();
				if(pass == 'Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Password',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				var repass = $('#formInvestorRegistration #rePassword').val();
				if(pass == 'Re-enter Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Re-enter Password',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				if(pass != repass){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				return true;
			} 
			
			validateBeforeAdvRegistartion = function(){

				
				 var fname = $('#formAdvisorRegistration #firstName').val();
				
				if(fname == 'First Name'){
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter First Name.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				/*var mname = $('#formAdvisorRegistration #middleName').val();
				if (mname == 'Middle Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Middle Name.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}*/
				
				var lname = $('#formAdvisorRegistration #lastName').val();
				if (lname == 'Last Name') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Last Name.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				var number = $('#formAdvisorRegistration #phone').val();
				if(number  == 'Mobile Number') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error'
					});
					return false;
				}else if (number.length != 10) {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Valid Mobile Number',
					    showHideTransition: 'fade',
					    icon: 'error'
					});
					return false;
				}
				
				var email = $('#formAdvisorRegistration #email').val();
				if (email == 'Email') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Email',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				} else {
					if( !isValidEmailAddress( email ) ) { 
						$.toast({
						    heading: 'Error',
						    text: 'Invalid Email Id',
						    showHideTransition: 'fade',
						    icon: 'error'
						})
						return false;
					}

				}
				
				var pass = $('#formAdvisorRegistration #password').val();
				if(pass == 'Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Enter Password',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				var repass = $('#formAdvisorRegistration #rePassword').val();
				if(pass == 'Re-enter Password') {
					$.toast({
					    heading: 'Error',
					    text: 'Please Re-enter Password',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				if(pass != repass){
					$.toast({
					    heading: 'Error',
					    text: 'Password did not matched.',
					    showHideTransition: 'fade',
					    icon: 'error'
					})
					return false;
				}
				
				return true;
			
			}
			
			 isValidEmailAddress = function(emailAddress) {
			    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
			    return pattern.test(emailAddress);
			};
			
			
				
            $(document).ready(function() {
                var url = window.location.href;
                var param = url.split("?src=");
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
                
                var param1 = url.split("&vr=");
                type = param1[1];
                if (type === 'true') {
                	$.toast({
					    heading: 'Success',
					    text: 'Email Verified.',
					    showHideTransition: 'fade',
					    icon: 'success'
					});
                }
                
                //CleverTap Code: Advisor Verified Email
                var val1=param[1].charAt(0);
                var val2=param1[1];
                if(val1 === '3' && val2=== 'true')
                {
                	clevertap.event.push('Email Verified for Advisor');
                }
            });
            
}]);