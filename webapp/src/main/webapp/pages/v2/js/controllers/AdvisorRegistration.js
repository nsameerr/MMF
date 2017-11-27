app.controller('AdvisorRegistration', ['$scope', function($scope) {
	//$scope.riskProfileQuestions = data;
	$scope.errorTitle= "Error";
	$scope.errorBody="Please answer the following questions";
	$scope.data = {};
	var MAX_FILE_SIZE="2097152"; // file size 2 MB in bytes

	
	
	var totalSlides = 12;
    var arrVisitedSlids = [];
    currentSlide = $("#myCarousel").find('.active').index();
    function validateCurrentSlide(index){
    	if(currentSlide==0){
    		if(validatePersonalDetails()){
    			$('#myCarousel').carousel(index);
    			$('#spc'+currentSlide).removeClass('active');
    			$('#spc'+currentSlide).addClass('done');
    			currentSlide = index;
    		}
    	} else if (currentSlide == 1){
    		if(validateOfficeDetails()){
    			$('#myCarousel').carousel(index);
    			$('#spc'+currentSlide).removeClass('active');
    			$('#spc'+currentSlide).addClass('done');
    			currentSlide = index;
    		}
    	} else if (currentSlide == 2) {
    		if(validateBankDetails()){
    			$('#myCarousel').carousel(index);
    			$('#spc'+currentSlide).removeClass('active');
    			$('#spc'+currentSlide).addClass('done');
    			currentSlide = index;
    		}
    	}
    	//$scope.autoSave();
    }
    
    function validatePersonalDetails(){
    	var advisorType = $("input[name='Q0']:checked").val();
    	var dobElement = $('#dob');
    	var userdob = $('#dob').val();
    	var userdobArray = userdob.split("-");
    	var userAge = getAge(userdobArray[2] + "-" + userdobArray[1] + "-" + userdobArray[0]);
    	if(advisorType == null || advisorType == 'null'){
    		showErrorToast('Please select advisor type.');
    		return false;
    	} else if ($('#fname').val() == 'First Name' || $('#fname').val() == '') {
    		showErrorToast('Please enter first name.');
    		return false;
    	}/* else if ($('#middle_name').val() == 'Middle Name' || $('#middle_name').val() == ''){
    		showErrorToast('Please enter middle name.');
    		return false;
    	}*/ else if ($('#last_name').val() == 'Last Name' || $('#last_name').val() == ''){
    		showErrorToast('Please enter last name');
    		return false;
    	} 
    	
    	var result =  $('input[name=Q0]:checked').val();
    	if(result == "true") {
    		//individual
    		if ($('#pan').val() == 'Individual PAN Number' || $('#pan').val() == ''){
        		showErrorToast('Please enter PAN number');
        		return false;
        	}
    	} else {
    		//corporate
    		if ($('#pan').val() == 'Corporate PAN Number' || $('#pan').val() == ''){
        		showErrorToast('Please enter PAN number');
        		return false;
        	}
    	}
    	
    	if ($('#dob').val() == 'Date of Birth' || $('#dob').val() == ''){
    		showErrorToast('Please enter Date of birth');
    		return false;
    	}
    	else if (userAge < 18) {
    		showErrorToast('Advisor cannot be minor');
    		return false;
    	}
    	else if ($('#sebi_regno').val() == 'SEBI Investment Advisor Registration No' || $('#sebi_regno').val() == ''){
    		showErrorToast('Please enter registration number of SEBI Certificate.');
    		return false;
    	} else if ($('#sebi_validity').val() == 'Validity of Registration' || $('#sebi_validity').val() == '' ){
    		showErrorToast('Please enter validity till date of SEBI Certificate.');
    		return false;
    	} else if ($('#sebiPath').val() == 'Upload Scanned Copy of SEBI Investment Advisor Certification' || $('#sebiPath').val() == '' ) {
    		showErrorToast('Please Upload Image of  SEBI Certificate.');	
    		return false;
    	}
    	

    	var resultPan = validatePanNumber($('#pan'));
    	if(!resultPan)
    		return false;
    	
    	return true;
    }
    
    function validateOfficeDetails(){
		var number  = $('#omobile').val();
    	var result =  $('input[name=Q0]:checked').val();
    	if (result == "false") {
    		//corporate
    		if($('#organization').val() == 'Organization' || $('#organization').val() == '' ){
    			showErrorToast('Please enter Organization name.');
    			return false;
    		}
    		
    	}
    	
    	if($('#jobTitle').val() == 'Job Title' || $('#jobTitle').val() == '') {
    		showErrorToast('Please enter Job title .');
			return false;
    	}
    	else if($('#oaddressLine1').val() == 'Address line 1' || $('#oaddressLine1').val() == '') {
    		showErrorToast('Please enter Address');
			return false;
    	}
    	else if($('#opincode').val() == 'PIN code' || $('#opincode').val() == '') {
    		showErrorToast('Please enter PINCODE');
			return false;
    	}
    	else if($('#opincode').val().length < 6) {
    		showErrorToast('invalid PINCODE');
			return false;
    	}
    	else if($('#ocountry').val() == 'Select' || $('#ocountry').val() == '') {
    		showErrorToast('Please enter country ');
			return false;
    	}
    	else if($('#ostate').val() == 'Select' || $('#ostate').val() == '') {
    		showErrorToast('Please enter state.');
			return false;
    	}
    	else if($('#ocity').val() == 'Select' || $('#ocity').val() == '') {
    		showErrorToast('Please enter city');
			return false;
    	}
    	else if($('#omobile').val() == 'Mobile Number' || $('#omobile').val() == '') {
    		showErrorToast('Please enter Mobile Number');
			return false;
    	} else if(number.length !=10) {
    		showErrorToast('Please enter valid Mobile Number');
    		return false;
    	}else if($('#regEmail').val() == 'Email' || $('#').val() == '') {
    		showErrorToast('Please enter Email Id');
			return false;
    	}
    	return true;
    }
    
    function validateBankDetails() {
    	var patt = new RegExp("[A-Z|a-z]{4}[0][0-9]{6}$");
      	var res = patt.test($('#ifscNo').val());
      	
    	if($('#bankName').val() == 'Select' || $('#bankName').val()== ''){
    		showErrorToast('Please enter your Bank Name');
    		return false;
    	}
    	else if($('#accountType').val() == 'Select' || $('#accountType').val()== ''){
    		showErrorToast('Please select account type.');
    		return false;
    	}
    	else if($('#accountNumber').val() == 'Bank Account Number' || $('#accountNumber').val()== ''){
    		showErrorToast('Please enter your Bank Account Number');
    		return false;
    	}
    	else if($('#raccountNumber').val() == 'Re enter Bank Account Number' || $('#raccountNumber').val()== ''){
    		showErrorToast('Please enter your Re enter Bank Account Number');
    		return false;
    	}
    	else if($('#ifscNo').val() == 'IFSC No.' || $('#ifscNo').val()== ''){
    		showErrorToast('Please enter IFSC No.');
    		return false;
    	}else if (!res) {
    		showErrorToast('Invalid IFSC Code');
    		return false;
    	}
    	else if($('#micrNo').val() == 'IFSC No.' || $('#micrNo').val()== ''){
    		showErrorToast('Please enter MICR No.');
    		return false;
    	}
    	else if($('#micrNo').val().length < 9){
    		showErrorToast('invalid MICR No.');
    		return false;
    	}
    	else if($('#baddressLine1').val() == 'Address line 1' || $('#baddressline1').val()== ''){
    		showErrorToast('Please enter Address line 1');
    		return false;
    	}
    	else if($('#bpincode').val() == 'PIN code' || $('#bpincode').val()== ''){
    		showErrorToast('Please enter PIN Code');
    		return false;
    	}
    	else if($('#bpincode').val().length < 6) {
    		showErrorToast('invalid PINCODE');
			return false;
    	}
    	else if($('#bstate').val() == 'Select' || $('#bstate').val()== ''){
    		showErrorToast('Please enter State');
    		return false;
    	}
    	else if($('#bcity').val() == 'Select' || $('#bcity').val()== ''){
    		showErrorToast('Please enter City');
    		return false;
    	}
    	 
    	return true;
    
    }
    
    function validateAgreement(){
    	if($('#cbSubscriberAgreement').prop("checked") == false){
    		showErrorToast('Please accept the Agreement at bottom of page.');
    		return false;
    	}
    	return true;
    }
    setTimeout(function(){ 
    	displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();
    	for(ind=0;ind<totalSlides;ind++)
		{
			arrVisitedSlids[ind]=false;
		}
    	
     	arrVisitedSlids[0]=true;
     	
     	/*
     	$('#spc0').on('click', function () {validateCurrentSlide(0);});
     	$('#spc1').on('click', function () {validateCurrentSlide(1);});
     	$('#spc2').on('click', function () {validateCurrentSlide(2);});
     	$('#spc3').on('click', function () {validateCurrentSlide(3);});
     	$('#spc4').on('click', function () {validateCurrentSlide(4);});
     	$('#spc5').on('click', function () {validateCurrentSlide(5);});
     	$('#spc6').on('click', function () {validateCurrentSlide(6);});
     	$('#spc7').on('click', function () {validateCurrentSlide(7);});
     	$('#spc8').on('click', function () {validateCurrentSlide(8);});
     	$('#spc9').on('click', function () {validateCurrentSlide(9);});
     	$('#spc10').on('click', function () {validateCurrentSlide(10);});
     	$('#spc11').on('click', function () {validateCurrentSlide(11);});
     	*/
     	
     	$('#btnNext').on('click',function(){
     		currentSlide = $("#myCarousel").find('.active').index();
     		if (currentSlide == 0)
     			validateCurrentSlide(1); //go to next slide
     		else if (currentSlide == 1)
     			validateCurrentSlide(2);
     		//else if (currentSlide == 2)
     			//validateCurrentSlide(0);
     		
     	});
     	
     	$('#btnPrev').on('click',function(){
     		currentSlide = $("#myCarousel").find('.active').index();
     		$('#spc'+currentSlide).removeClass('active');
     		/*if (currentSlide == 0) {
     			$('#myCarousel').carousel(2);
     			currentSlide = 2;
     		}*/ 
     		if (currentSlide == 1) {
     			$('#myCarousel').carousel(0);
     			currentSlide = 0;
     		} else if (currentSlide == 2) {
     			$('#myCarousel').carousel(1);
     			currentSlide = 1;
     		}
     		
     	});
     	 
        $("#myCarousel").on('slid.bs.carousel', function (e) {
        	$scope.autoSave();
             curSlide = $("#myCarousel").find('.active');
             if(curSlide.index() == 0) {
            	 //first slide - only next button active
            	 $('#btnPrev').hide();
                 $('#btnNext').show();
                 $('#btnSubmitx').hide();
            	 $('#divAggrement').css('display','none'); 
             } else if(curSlide.index() == 2) {
            	 //last slide - only prev and submit
            	 $('#btnPrev').show();
                 $('#btnNext').hide();
            	 $('#btnSubmitx').show();
            	 $('#divAggrement').css('display','block');
             } else {
            	 $('#btnPrev').show();
                 $('#btnNext').show();
                 $('#btnSubmitx').hide();
            	 $('#divAggrement').css('display','none');
             }
             
             
//             if(curSlide.index()==4){
//            	 $('#divAggrement').css('display','block');
//             }
//             else{
//            	 $('#divAggrement').css('display','none');
//             }
//             arrVisitedSlids[curSlide.index()] = true;
//             
//
//             if(curSlide.is( ':first-child' )) {
//                $('#btnPrev').hide();
//             } else {
//                $('#btnPrev').show();   
//             }
//             if (curSlide.is( ':last-child' )) {
//                $('#btnNext').hide();
//                $('#btnPrev').show();
//                $('#btnSubmitx').show();
//             } else {
//                $('#btnNext').show();
//                $('#btnPrev').show();
//             }
             

             /* 
              * 
             $('.circle').removeClass('active');
             $('.circle').removeClass('done');
            for(i=0;i<totalSlides;i++)
     		{
            	if($('input[name=Q'+i+']').attr('type')=='radio')
            	{
            		if($('input[name=Q'+i+']:checked').val() > -1)
         			{
            			$('#spc'+i).addClass('done');
         			}
     	    		else
         			{
     	    			
         			}
            	}
            	else if(arrVisitedSlids[i])
            	{
            		$('#spc'+i).addClass('done');
            	}
     		}
             */
             $('#spc'+curSlide.index()).addClass('active');
             
             //$('.circle')[curSlide.index()*2].addClass('active');
             
             $('.circle').each(function () {
            	    //console.log($(this).index());
            	    
			 });
             $('html,body').scrollTop(0);
             
         });
         
         $('.carousel').each(function(){
             $(this).carousel({
                 interval: false
             });
         });
    	
    	$(':radio').each(function(){
        	//console.log('hi');
            var self = $(this);
            label = self.next();
            label_text = label.text();
            //label.remove();
            self.iCheck({
            //checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green'
            });
            
        });
    	
    	var riskoMeterPointer;
		var riskoMeterChart;
		$(function () {
		    $('#divRiskoMeter').highcharts({
		    	credits: {
			          enabled: false
			        },
			        exporting: {
			          enabled: false
			        },
			        chart: {
			            type: 'gauge',
			            plotBorderWidth: 0,
			            plotBackgroundImage : 'img/riskometer_bg.png',
			            backgroundColor : 'rgba(0, 0, 0, 0)'
			        },

			        title: {
			            text: '',
			          enabled: false
			        },
			        
			        pane: [{
			            startAngle: -90,
			            endAngle: 90,
			            background: null,
			            center: ['50%', '207'],
			            size: 220
			        }],

			        tooltip: {
			            enabled: false
			        },
			        
  			    plotOptions: {
  		            series: {
  		                dataLabels: {
  		                    enabled: false,
  		                    backgroundColor: 'rgba(0, 0, 0, 0)',
  		                    borderWidth: 0,
  		                  	format: ''
  		                }
  		            }
  		        },

			        yAxis: [{
			            visible : false,
			            min: 0,
			            max: 100
			        }],
			        series: [{
			            name: 'Channel A',
			            data: [50],
			            yAxis: 0
			        }]

		    },
		    // Add some life
		    function (chart) {
		        riskoMeterChart = chart;
		    });
		});
		var rangeSalarySpouse = $("#range_score");
		rangeSalarySpouse.ionRangeSlider({
	         min:0,
	         max:100,
	         from:50,
	         grid: true,
	         grid_num: 5,
	         onChange: function(data){
	        	 riskoMeterChart.series[0].points[0].update(data.from);
	         },
	         onFinish: function(data){
	        	 riskoMeterChart.series[0].points[0].update(data.from);
	         }
	     });
	     sliderSalarySpouse = rangeSalarySpouse.data("ionRangeSlider");
	     $(".irs-single").css("display","block");
	     
	     
	     
    	
    	
    	
    	
    	$('#btnSubmit').on('click', function(event){
	    	//console.log('onclick');
	    	unansweredQuestions = "";
	    	
	    	
	    	for(i=0;i<totalSlides;i++)
     		{
            	if($('input[name=Q'+i+']').attr('type')=='radio')
            	{
            		if($('input[name=Q'+i+']:checked').val() > -1)
         			{
            			
         			}
     	    		else
         			{
     	    			if(unansweredQuestions == "")
        				{
    	    				unansweredQuestions = "#" + (i+1) 
        				}
    	    			else
        				{
    	    				unansweredQuestions = unansweredQuestions + ", #" + (i+1);
        				}
         			}
            	}
            	else if(arrVisitedSlids[i])
            	{
            		//$('#spc'+i).addClass('done');
            	}
            	else{
            		if(unansweredQuestions == "")
    				{
	    				unansweredQuestions = "#" + (i+1) 
    				}
	    			else
    				{
	    				unansweredQuestions = unansweredQuestions + ", #" + (i+1);
    				}
            	}
     		}
	    	
	    	if(unansweredQuestions=="")
    		{
	    		window.location.href = 'riskProfileOutput.html';
    		}
	    	else
	    	{
	    		$('.modal-title')[0].innerHTML = "Error"
    		    $('.modal-body')[0].innerHTML = "Please answer the following questions <br> " +unansweredQuestions;
    	    	$('#modalShowError').modal('show');
	    	}
	    	
	    });
        
        $('#divContent').css("display","block");
        $('#divLoading').css("display","none");
        //alert();
        
        toggleCollaps = function(iconID,divID){
    		
    		if($(divID).css('display') == 'none')
    		{
    			$(divID).css('display', 'block');
    			$(iconID).removeClass('glyphicon-chevron-down');
    			$(iconID).addClass('glyphicon-chevron-up');
    		}
    		else{
    			$(divID).css('display', 'none');
    			$(iconID).removeClass('glyphicon-chevron-up');
    			$(iconID).addClass('glyphicon-chevron-down');
    		}
    	}
        
    }, 1000);
    
    $scope.moveNextSlide = function(){
    	console.log('wassup');
    	currSlide = ("#myCarousel").find('.active').index()
    	if(validateSlides(currSlide)){
    		
    	}
    }
    
    $scope.validateSlides = function(slideIndex){
    	return true
    }
    
    $scope.autoSave = function(){
		//console.log($scope.data);
		//console.log(JSON.stringify($scope.data));
    	if ($('#middle_name').val() == 'Middle Name' || $('#middle_name').val() == ''){
    		$scope.data.middle_name= "";
    	} 
    	$.ajax({
			type: "POST",
			url: _gc_url_ar_post_autosave,
			data: JSON.stringify($scope.data),
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				console.log('success');
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
    }
    
    $scope.finalSave = function(){
    	if(!validatePersonalDetails()){
    		$('#myCarousel').carousel(0);
    		return;
    	}
    	if(!validateOfficeDetails()){
    		$('#myCarousel').carousel(1);
    		return;
    	}
    	if(!validateBankDetails()){
    		$('#myCarousel').carousel(2);
    		return;
    	}
    	if(!validateAgreement()){
    		return;
    	}
    	if ($('#middle_name').val() == 'Middle Name' || $('#middle_name').val() == ''){
    		$scope.data.middle_name= "";
    	} 
    	showLoading();
    	clevertap.event.push("Advisor Registration Form Submitted");
    	console.log("Advisor Registration Form Submitted");
    	$.ajax({
			type: "POST",
			url: _gc_url_ar_post_autosave,
			data: JSON.stringify($scope.data),
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				console.log('success');
				$('#frmFinalFormSubmit').attr('action', _gc_url_ar_post_finalsave);
		    	$('#frmFinalFormSubmit').submit();
			},
			error: function(){
				console.log('error');
				hideLoading();
			},
			fail: function(){
				console.log('fail');
				hideLoading();
			}
		});
    	

    }
    
    
    init = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ar_get_advisorDetails,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					console.log(rData);
					//data = JSON.parse(data);
					var tempData = rData.toString();
					if(tempData.indexOf("ERROR")== -1){
						$scope.data = rData;
						$scope.$apply();
						prefillData();
					}
					else{
						console.log("first time user registring");
					}
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
    
    prefillData = function(){
	    console.log($scope.data);
	    $(".form-group").addClass("populated");
	    if($('#ostate').val()!="" || $('#ostate').val()!="Select"||$('#ostate').val()!=null ){
			getStateWiseCites('#ostate','#ocity');
		}
	    if($('#bstate').val()!="" || $('#bstate').val()!="Select"||$('#bstate').val()!=null ){
			getStateWiseCites('#bstate','#bcity');
		}
    }
    
    init();
    
    validateIFSC = function(ifsc) {
   	 var patt = new RegExp("[A-Z|a-z]{4}[0][0-9]{6}$");
   	 var res = patt.test(ifsc.value);
   	 if(!res){
   		 $.toast({
				    heading: 'Error',
				    text: "Invalid IFSC Code.",
				    showHideTransition: 'fade',
				    icon: 'error'
				});
   	 }
    }
    
    reValidateAccno = function(reAccno) {
   	 if($('#raccountNumber').val() != $('#accountNumber').val()){
    		$.toast({
			    heading: 'Error',
			    text: 'Bank Account Number not matched.',
			    showHideTransition: 'fade',
			    icon: 'error'
			})
    		return false;
    	}
    }
    
    validatePanNumber = function(input){
     	//console.log(inpEmail.value);
    	var patt = new RegExp("[A-Z|a-z]{3}(p|P|c|C|h|H|f|F|a|A|t|T|b|B|l|L|j|J|g|G){1}[A-Z|a-z][0-9]{4}[A-Za-z]$");
     	     	var panValue = "";
      	if( input.value === undefined)
      		panValue = input.val();
      	else 
      		panValue = input.value;
      	var res = patt.test(panValue);
     	if(res) {
     		var form = new FormData();
     		form.append("type", "panNumber");
     		form.append("panNumber", panValue);
     		var formData = "type=panNumber"+
     						"&panNumber="+panValue;
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
     		if( input.value === undefined) {
     			settings.async = false;
     			var result = false;
     			$.when($.ajax(settings)).done(function (res) {
 	    			console.log(res);
 	    			res = JSON.parse(res);
 	    		 	if(res.status==true){
 	    		 		$.toast({
 	    				    heading: 'Error',
 	    				    text: res.errorMsg,
 	    				    showHideTransition: 'fade',
 	    				    icon: 'error'
 	    				});
     		 			result = false;
     		 		} else {
     		 			result = true;
     		 		}
     			}).fail(function (response){
     				alert(response);
     				result = false;
     			});	
     			return result;
     		} else {
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
     		 		return false;
     		 		} else {
     		 		return true;
     		 		}
     			}).fail(function (response){
     				alert(response);
     				return false;
     			});	
     		}
     		
     	} else {
     		$.toast({
 			    heading: 'Error',
 			    text: 'Please enter valid PAN number',
 			    showHideTransition: 'fade',
 			    icon: 'error'
 			});
     		return false;
     	}

    }
    
    
    uploadSebiCertificate = function(){
    	var fileSize = $('#sebiCertFile')[0].files[0].size;
    	if (fileSize <= MAX_FILE_SIZE){
    		//alert($('#sebiCertFile')[0].files[0].size);
        	uploadFileToServer(
        			$('#sebiCertFile')[0].files[0],
        			_gc_url_ir_fileUpload_sebi,
        			function(responseData){
        				console.log(responseData);
        				if (responseData.indexOf("error")== -1) {
        					$scope.data.sebiPath = responseData;
            				$('#sebiPathMsg').text('File Uploaded Successfully.');
            				$('#sebiPathMsg').css('color','green');
        				} else {
        					$('#sebiPathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#sebiPathMsg').css('color','red');
        				}
        				
        			}
        	);
    	} else {
    		showErrorToast('File size greater than 2 MB.');
    	}
    	
    }
    
    
    var uploadFileToServer = function(filePath,url,callbackFunction){
		var form = new FormData();
		form.append("file", filePath);
		form.append("email",$("#regEmail").val());
		//form.append("regId", "123");

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
    
	 getAge =  function(DOB){
	    var today = new Date();
	    var birthDate = new Date(DOB);
	    var age = today.getFullYear() - birthDate.getFullYear();
	    var m = today.getMonth() - birthDate.getMonth();
	    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
	        age--;
	    }    
	    return age;
	}
	
	validateDOB = function(DOB){
		var enteredAge = getAge(DOB.value);
		if( enteredAge < 18 ) {
			showErrorToast('Advisor cannot be minor.');
		    return false;
		}
		return true;
	} 
	
	validateSebiCertDate = function(DOC) {
		
			var certDate = getAge(DOC.value);
			if (certDate > 0){
				showErrorToast('SEBI Certificate validity expired');
			} else if (certDate < -5) {
				showErrorToast('SEBI Certificate validity cannot be future date');
			} else {
				//console.log('valid');
				return true;
			}
			return false;
		
	}
	
	canShowToast = true;
    function showErrorToast(msg){
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
    
    $('input[name=Q0]').on('ifChecked', function(event){
    	  var result =  $('input[name=Q0]:checked').val();
    	  if(result == "true"){
    	  	//individual
    	  	$('#panLabel').text("Individual PAN Number");
    	  	$('#pan').attr('placeholder','Individual PAN Number');
    	  	$('#last_nameLabel').addClass('mandatory');
    	  	$('#orgLabel').removeClass('mandatory');
    	  	$scope.data.indvOrCprt = true;
    	  } else {
    	  	//corporate
    	  	$('#panLabel').text("Corporate PAN Number");
    	  	$('#pan').attr('placeholder','Corporate PAN Number');
    	  	$('#last_nameLabel').removeClass('mandatory');
    	  	$('#orgLabel').addClass('mandatory');
    	  	$scope.data.indvOrCprt = false;
    	  }
    	});
    
    $("#dob").on("dp.change", function() {
    	getAge(this);
        $scope.data.dob = $("#dob").val();
    });
    $("#sebi_validity").on("dp.change", function() {
        $scope.data.sebi_validity = $("#sebi_validity").val();
    });
    
  //select c state change

	getStateWiseCites = function(stateElement,element){
			var state = $(stateElement).val();
			var form = new FormData();
            form.append("stateName", state);
			var settings = {
			"async": true,
              "crossDomain": true,
              "url": _gc_url_citylist,
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
				console.log(res);
				res = JSON.parse(res);
				var selectCityOptions = res;
				$(element +' option').remove();
				$(element).append('<option value="" selected="selected">Select</option>');
				$(element).each(function(){
		    		for(index in selectCityOptions) {
		    		    this.options[this.options.length] = new Option(selectCityOptions[index], selectCityOptions[index]);
		    		}
		    	});
			}).fail(function (response){
			alert(response);
			});
	}
}]);