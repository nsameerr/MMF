app.controller('UserRegistration', ['$scope', function($scope) {
	//$scope.riskProfileQuestions = data;
	$scope.errorTitle= "Error";
	$scope.errorBody="Please answer the following questions";
	var MAX_FILE_SIZE="2097152"; // file size 2 MB in bytes
	var totalSlides = 12;
    var arrVisitedSlids = [];
    var FINAL_SUBMIT = false;
    currentSlide = 0;
    
    function validateCurrentSlide(index){
    	console.log(1);
    	if(currentSlide==0){
    		if(validatePersonalDetails()){
    			$('#myCarousel').carousel(index);
    			$('#spc'+currentSlide).removeClass('active');
    			$('#spc'+currentSlide).addClass('done');
     			currentSlide = index;
    		}
    	}
    	else if(currentSlide==1){
    		if(validateAddressDetails()){
    			$('#myCarousel').carousel(index);
    			$('#spc'+currentSlide).removeClass('active');
    			$('#spc'+currentSlide).addClass('done');
     			currentSlide = index;
    		}
    	}
    	else if(currentSlide==2){
    		if(validateBackgroundDetails()){
    			$('#myCarousel').carousel(index);
    			$('#spc'+currentSlide).removeClass('active');
    			$('#spc'+currentSlide).addClass('done');
     			currentSlide = index;
    		}
    	}
    	else if(currentSlide==3){
    		if(validateBankDetails()){
    			$('#myCarousel').carousel(index);
    			$('#spc'+currentSlide).removeClass('active');
    			$('#spc'+currentSlide).addClass('done');
     			currentSlide = index;
    		}
    	}
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
    
    
    function validatePersonalDetails(){
    	console.log('validating validatePersonalDetails');
    	var userdob = $('#dob').val();
    	var userdobArray = userdob.split("-");
    	var userAge = getAge(userdobArray[2] + "-" + userdobArray[1] + "-" + userdobArray[0]);
    	if($('#firstname').val() == 'First Name' || $('#firstname').val() == ''){
    		showToast('Please enter your First Name');
    		return false;
    	}
    	else if($('#lastname').val() == 'Last Name' || $('#lastname').val() == ''){
    		showToast('Please enter your Last Name');
    		return false;
    	}
    	else if($('#fatherSpouse').val() == 'Father/Spouse Name' || $('#fatherSpouse').val() == ''){
    		showToast('Please enter your Father/Spouse Name');
    		return false;
    	}
    	else if($('#motherName').val() == 'Mother Name' || $('#motherName').val() == ''){
    		showToast('Please enter your Mother Name');
    		return false;
    	}
    	else if($('#dob').val() == ''){
    		showToast('Please enter your Date of Birth');
    		return false;
    	}
    	else if(userAge < 18){
    		showToast('Applicant cannot be minor');
    		return false;
    	}
    	else if($('#gender').val() == ''){
    		showToast('Please enter your gender');
    		return false;
    	}
    	else if($('#mstatus').val() == ''){
    		showToast('Please enter your Marital Status');
    		return false;
    	}
    	var resultPan = validatePanNumber($('#pan'));
    	if(!resultPan)
    		return false;
    	
    	return true;
    }
    
    function validateAddressDetails(){
    	if($('#caddressline1').val() == 'Address line 1' || $('#caddressline1').val() == ''){
    		showToast('Please enter your address');
    		return false;
    	}
    	else if($('#cpincode').val() == 'PIN code'){
    		showToast('Please enter Pin Code');
    		return false;
    	}
    	else if($('#cpincode').val().length < 6){
    		showToast('Invalid pincode');
    		return false;
    	}
    	else if($('#cstate').val() == '' || $('#cstate').val() == 'Select'){
    		showToast('Please select your state');
    		return false;
    	}
    	else if($('#ccity').val() == '' || $('#ccity').val() == null || $('#cstate').val() == 'Select'){
    		showToast('Please select your city');
    		return false;
    	}
    	else if($('#cproof').val() == ''){
    		showToast('Please select proof of address');
    		return false;
    	}
    	else if($('#cproof').val() == ''){
    		showToast('Please select proof of address');
    		return false;
    	}
    	else if($('#mobile').val() == 'Mobile Number' || $('#mobile').val()== ''){
    		showToast('Please enter your mobile number');
    		return false;
    	}
    	else if ($('#mobile').val().length != 10){
    		showToast('Please enter valid mobile number');
    		return false;
    	}
    	else if($('#email').val() == 'Email' || $('#email').val()== ''){
    		showToast('Please enter your Email Id');
    		return false;
    	}
    	else if($('#permenentAddress').is(':checked')){
    		if($('#paddressline1').val() == 'Address line 1' || $('#paddressline1').val()== ''){
        		showToast('Please enter your Permanant Address');
        		return false;
        	}
    		else if($('#ppin').val() == 'PIN code' || $('#ppin').val()== ''){
        		showToast('Please enter Pin Code for your Permanant Address ');
        		return false;
        	}
    		else if($('#ppin').val().length < 6){
        		showToast('Invalid pincode in permanant Address');
        		return false;
        	}
    		else if($('#pstate').val() == 'Select' || $('#pstate').val()== ''){
        		showToast('Please select State for your Permanant Address ');
        		return false;
        	}
    		else if($('#pcity').val() == 'Select' || $('#pcity').val()== '' || $('#pcity').val() == null){
        		showToast('Please select City for your Permanant Address ');
        		return false;
        	}
    		else if($('#pproof').val() == 'Select' || $('#pproof').val()== ''){
        		showToast('Please select Address Proof for your Permanant Address ');
        		return false;
        	}
    	}
    	return true;
    	
    }
    
    function validateBackgroundDetails(){
    	if($('#fstHldrOccup').val() == 'Select' || $('#fstHldrOccup').val()== ''){
    		showToast('Please enter your occupation');
    		return false;
    	}
    	else{
    		if($('#fstHldrOccup').val() == 'Student' || $('#fstHldrOccup').val() == 'Retired' || $('#fstHldrOccup').val() == 'Housewife' || $('#fstHldrOccup').val() == 'Agriculturist'){
    			
    		}else{
    			if($('#fstHldrOrg').val() == 'Name of your Employer/Company' || $('#fstHldrOrg').val()== ''){
    	    		showToast('Please enter your Employer/Company name');
    	    		return false;
    	    	}
    			else if($('#fstHldrDesig').val() == 'Designation' || $('#fstHldrDesig').val()== ''){
    	    		showToast('Please enter your Designation');
    	    		return false;
    	    	}
    		}
    	}
    	
    	if($('#fstHldrIncome').val() == 'Select' || $('#fstHldrIncome').val()== ''){
    		
    		if($('#fstHldrAmt').val() == 'Networth' || $('#fstHldrAmt').val()== ''){
    			showToast('Please enter Income details or Networth');
        		return false;
    		}
    		
    	}
    	if($('#experience').val() == 'Number of years of investment/Trading Experience' || $('#experience').val()== ''){
    		showToast('Please enter Number of years of investment/Trading Experience');
    		return false;
    	}
    	
    	if($('#nomineeExist').is(':checked')){
    		//validation for nomiee
    		var result  = validateNomination();
    		if(!result)
    			return false;
    	}
    	if ($("#minorExist").is(':checked')){
    		//validation for minor
    		var result =  validateGuardian();
    		if(!result)
    			return false;
    	}
    	return true;
    }
    
    function validateNomination() {
    	
    	if($('#nameNominee').val() == 'Name of Nominee' || $('#nameNominee').val() == ''){
    		showToast('Please enter the name of nomination');
    		return false;
    	}
    	else if($('#nomineeRelation').val() == 'Relationship' || $('#nomineeRelation').val() == ''){
    		showToast('Please enter the relationship with applicant.');
    		return false;
    	}
    	else if($('#nomineeDob').val() == '' ){
    		showToast('Please enter the DOB of nominee.');
    		return false;    		
    	}
    	else if($('#nomineeAdrs1').val() == 'Address Line1' || $('#').val() == ''){
    		showToast('Please enter the nominee address');
    		return false;
    	}
    	else if($('#nomineePincode').val() == 'Pin Code' || $('#nomineePincode').val() == ''){
    		showToast('Please enter the Pincode for nominee\'s address.');
    		return false;
    	}
    	else if($('#nomineePincode').val().length < 6){
    		showToast('Invalid pincode in Nominee section');
    		return false;
    	}
    	else if($('#nomCountry').val() == 'Select' || $('#nomCountry').val() == ''){
    		showToast('Please enter the country for nominee\'s address.');
    		return false;
    	}
    	else if($('#nomState').val() == 'Select' || $('#nomState').val() == ''){
    		showToast('Please enter the State for nominee\'s address.');
    		return false;
    	}
    	else if($('#nomCity').val() == 'Select' || $('#nomCity').val() == '' || $('#nomCity').val() == null){
    		showToast('Please enter the City for nominee\'s address.');
    		return false;
    	}    
    	else if($('#nomineeProof').val() == 'Select' || $('#nomineeProof').val() == ''){
    		showToast('Please select Proof of Identification for nominee.');
    		return false;
    	}
    	if($('#nomineeProof').val() == 'PAN'){
    		if ($('#nominePan').val() == 'PAN Number' || $('#nominePan').val() == '') {
    			showToast('Please enter the PAN Number of Nominee.');
    			return false;
    		}else if(!isValidPANNumber($('#nominePan').val())){
    			showToast('Invalid PAN Number of Nominee.');
    			return false;
    		}
    	}
    	if($('#nomineeProof').val() == 'Aadhar'){
    		if ($('#nomineAadhar').val() == 'UID/Aadhaar Number' || $('#nomineAadhar').val() == '') {
    			showToast('Please enter the Aadhar Number of Nominee');
    			return false;
    		}
    	}
    	if ($('#nomMobile').val() == 'Mobile' || $('#nomMobile').val() == '') {
			showToast('Please enter the Mobile Number for Nomiee');
			return false;
		}
    	else if ($('#nomMobile').val().length != 10){
			showToast('Please enter the valid Mobile Number for Nomiee');
			return false;
    	}
    	else if ($('#nomEmail').val() == 'Email' || $('#nomEmail').val() == '') {
			showToast('Please enter the Email  for nomiee.');
			return false;
		}
    	else if (!isValidEmailAddress($('#nomEmail').val())){
    		showToast('Invalid Email  for nomiee.');
			return false;
    	}
    	return true;
    }
    
    function validateGuardian() {
    	
    	if($('#minorGuard').val() == 'Name of Guardian' || $('#minorGuard').val() == '') {
    		showToast('Please enter the Name of Guardian.');
    		return false;
    	} else if($('#mnrReltn').val() == 'Relationship with Guardian' || $('#mnrReltn').val() == '') {
    		showToast('Plese enter the Relationship with nominee.');
    		return false;
    	}
		else if($('#mnrAdrs1').val() == 'Address Line1' || $('#mnrAdrs1').val() == '') {
			showToast('Please enter the address of Guardian.');
    		return false;
		}
		else if($('#mnrPincode').val() == 'Pin Code' || $('#').val() == '') {
			showToast('Please enter the Guardian\'s pincode.');
    		return false;
		}
		else if($('#mnrPincode').val().length < 6){
    		showToast('Invalid pincode in Guardian section');
    		return false;
    	}
		else if($('#mnrCountry').val() == 'Select' || $('#mnrCountry').val() == '') {
			showToast('Please enter the country for Guardian\'s Address.');
    		return false;
		}
		else if($('#mnrState').val() == 'Select' || $('#mnrState').val() == '') {
			showToast('Please enter the State for Guardian\'s Address.');
    		return false;
		}
		else if($('#mnrCity').val() == '' || $('#mnrCity').val() == '' || $('#mnrCity').val() == null) {
			showToast('Please enter the City for Guardian\'s Address.');
    		return false;
		}
		if($('#mnrProof').val() == 'PAN'){
    		if ($('#mnrPan').val() == 'PAN Number' || $('#mnrPan').val() == '') {
    			showToast('Please enter the PAN Number of Guardian.');
    			return false;
    		}else if(!isValidPANNumber($('#mnrPan').val())){
    			showToast('Invalid PAN Number of Guardian.');
    			return false;
    		}
    	}
    	if($('#mnrProof').val() == 'Aadhar'){
    		if ($('#mnrAadhar').val() == 'UID/Aadhaar Number' || $('#mnrAadhar').val() == '') {
    			showToast('Please enter the Aadhar Number of Guardian');
    			return false;
    		}
    	}
		if($('#mnrMob').val() == 'Mobile' || $('#mnrMob').val() == '') {
			showToast('Please enter the mobile number of Guardian.');
    		return false;
		}
		else if ($('#mnrMob').val().length != 10){
			showToast('Please enter the valid Mobile Number for Guardian');
			return false;
    	}
		else if($('#mnrEmail').val() == 'Email' || $('#mnrEmail').val() == '') {
			showToast('Please enter the Email  of Guardian.');
    		return false;
		}
		else if (!isValidEmailAddress($('#mnrEmail').val())){
    		showToast('Invalid Email  for Guardian.');
			return false;
    	}
		return true;
    }
    
    function validateBankDetails(){
    	var patt = new RegExp("[A-Z|a-z]{4}[0][0-9]{6}$");
   	 	var res = patt.test($('#bifsc').val());
    	if($('#bankname').val() == 'Select' || $('#bankname').val()== ''){
    		showToast('Please enter your Bank Name');
    		return false;
    	}
    	else if($('#accno').val() == 'Bank Account Number' || $('#accno').val()== ''){
    		showToast('Please enter your Bank Account Number');
    		return false;
    	}
    	else if($('#reAccno').val() == 'Re enter Bank Account Number' || $('#reAccno').val()== ''){
    		showToast('Please enter your Re enter Bank Account Number');
    		return false;
    	}
    	else if($('#bifsc').val() == 'IFSC No.' || $('#bifsc').val()== ''){
    		showToast('Please enter IFSC No.');
    		return false;
    	}
    	else if (!res){
    		showToast('Invalid IFSC No.');
    		return false;
    	}
    	else if($('#bmicr').val() == 'MICR No.' || $('#bmicr').val()== ''){
    		showToast('Please enter MICR No.');
    		return false;
    	}
    	else if($('#bmicr').val().length < 9){
    		showToast('Invalid MICR No.');
    		return false;
    	}
    	else if($('#baddressline1').val() == 'Address line 1' || $('#baddressline1').val()== ''){
    		showToast('Please enter Address line 1');
    		return false;
    	}
    	else if($('#bpincode').val() == 'PIN code' || $('#bpincode').val()== ''){
    		showToast('Please enter PIN Code');
    		return false;
    	}
    	else if($('#bpincode').val().length < 6){
    		showToast('Invalid pincode in Bank section');
    		return false;
    	}
    	else if($('#bstate').val() == 'Select' || $('#bstate').val()== ''){
    		showToast('Please enter State');
    		return false;
    	}
    	else if($('#bcity').val() == 'Select' || $('#bcity').val()== '' ||  $('#bcity').val()== null){
    		showToast('Please enter City');
    		return false;
    	}
    	 
    	return true;
    }
	
    function validateAgreement(){
    	if($('#cbSubscriberAgreement').prop("checked") == false){
    		showToast('Please accept the Agreement at bottom of page.');
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
     		else if (currentSlide == 2)
     			validateCurrentSlide(3);
//     		else if (currentSlide == 3)
//     			validateCurrentSlide(0);

     	});
     	$('#btnPrev').on('click',function(){
     		currentSlide = $("#myCarousel").find('.active').index();
     		$('#spc'+currentSlide).removeClass('active');
//     		if (currentSlide == 0) {
//     			$('#myCarousel').carousel(3);
//     			currentSlide = 3;
//     		} 
     		if (currentSlide == 1) {
     			$('#myCarousel').carousel(0);
     			currentSlide = 0;
     		} else if (currentSlide == 2) {
     			$('#myCarousel').carousel(1);
     			currentSlide = 1;
     		} else if (currentSlide == 3) {
     			$('#myCarousel').carousel(2);
     			currentSlide = 2;
     		}
     		
     	});
     	
         $("#myCarousel").on('slid.bs.carousel', function () {
        	 
             //console.log($("#myCarousel").find('.active').index());
             
             curSlide = $("#myCarousel").find('.active');
             if(curSlide.index() == 0) {
            	 //first slide - only next button active
            	 $('#btnPrev').hide();
                 $('#btnNext').show();
                 $('#btnSubmitx').hide();
            	 $('#divAggrement').css('display','none'); 
             } else if(curSlide.index() == 3) {
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
/*             if(curSlide.index()==4){
            	 $('#divAggrement').css('display','block');
             }
             else{
            	 $('#divAggrement').css('display','none');
             }
             arrVisitedSlids[curSlide.index()] = true;
             

             if(curSlide.is( ':first-child' )) {
                $('#btnPrev').hide();
             } else {
                $('#btnPrev').show();   
             }
             if (curSlide.is( ':last-child' )) {
                $('#btnNext').hide();
             } else {
                $('#btnNext').show();      
             }*/
             /*
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
             
             makePostJson();
             
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
	     
	     
    	
    	$(':radio').on('ifChecked', function(event){
            //$('#myCarousel').carousel("next");
            
            if (!$("#myCarousel").find('.active').is( ':last-child' )) 
         	{
         		$('#myCarousel').carousel("next");
            }
         	else
       		{
       			//console.log('else');
       		}
        });
    	
    	
    	$('#btnSubmit').on('click', function(event){
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
        
        
        init = function(){
			$.ajax({
				type: "POST",
				url: _gc_url_ir_get_userDetails,
				data: null,
				dataType: 'json',
				contentType:"application/json; charset=utf-8",
				success: function(data){
					try{
						//data = JSON.parse(data);
						var tempData = data.toString();
						if(tempData.indexOf("ERROR")== -1){
							data = data;
							prefillData(data);
							$('html,body').scrollTop(0);
						}
						else{
							$('html,body').scrollTop(0);
							console.log("first time user registring");
						}
						
					}
					catch(e){
						$('html,body').scrollTop(0);
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
			$('html,body').scrollTop(0);
		}
        init();
		//$('#divContent').css("display","block");
		//$('#divLoading').css("display","none");
		
		prefillData = function(data){
		try{
			if(	data.email != "") {
				$('#email').val(data.email).parent().addClass("populated");
			} 
			if(	data.firstname != "") {
				$('#firstname').val(data.firstname).parent().addClass("populated");
			} 
			if(	data.middlename != "") {
				$('#middlename').val(data.middlename).parent().addClass("populated");
			} 
			if(	data.lastname != "") {
				$('#lastname').val(data.lastname).parent().addClass("populated");
			} 
			if(	data.fatherSpouse != "") {
				$('#fatherSpouse').val(data.fatherSpouse).parent().addClass("populated");
			}
			if(	data.motherName != "") {
				$('#motherName').val(data.motherName).parent().addClass("populated");
			}
			
			$('#dob').val(data.dob);
			
			$('#nationality').val(data.nationality);
			
			$('#status').val(data.status);
			
			$('#gender').val(data.gender);
			
			$('#mstatus').val(data.mstatus);
			
			if(	data.uid != "") {
				$('#uid').val(data.uid).parent().addClass("populated");
			} 
			if(	data.pan != "") {
				$('#pan').val(data.pan).parent().addClass("populated");
			} 
			if(	data.caddressline1 != "") {
				$('#caddressline1').val(data.caddressline1).parent().addClass("populated");
			} 
			if(	data.caddressline2 != "") {
				$('#caddressline2').val(data.caddressline2).parent().addClass("populated");
			} 
			if(	data.cpincode != "") {
				$('#cpincode').val(data.cpincode).parent().addClass("populated");
			} 
			
			$('#cstate').val(data.cstate);
			if($('#cstate').val()!="" || $('#cstate').val()!="Select"||$('#cstate').val()!=null ){
				getStateWiseCitesSync('#cstate','#ccity');
			}
			$('#ccity').val(data.ccity);
			
			$('#cproof').val(data.cproof);toggleValidityVisibility('#cproof','#divValidityOne');
			
			$('#cvalidity').val(data.cvalidity);
			
			if(data.permenentAddress == "true") {
				$('#permenentAddress').bootstrapSwitch('state', true, true);
			} else {
				$('#permenentAddress').bootstrapSwitch('state', false, true);
			}
					
			//$('#permenentAddress').is(':checked');
			
			if(	data.paddressline1 != "") {
				$('#paddressline1').val(data.paddressline1).parent().addClass("populated");
			} 
			if(	data.paddressline2 != "") {
				$('#paddressline2').val(data.paddressline2).parent().addClass("populated");
			} 
			if(	data.plandmark != "") {
				$('#plandmark').val(data.plandmark).parent().addClass("populated");
			} 
			if(	data.ppin != "") {
				$('#ppin').val(data.ppin).parent().addClass("populated");
			} 
			
			$('#pcountry').val(data.pcountry);
			
			
			$('#pstate').val(data.pstate);
			if($('#pstate').val()!="" || $('#pstate').val()!="Select"||$('#pstate').val()!=null ){
				getStateWiseCitesSync('#pstate','#pcity');
			}
		
			$('#pcity').val(data.pcity);
		
		
			$('#pproof').val(data.pproof);toggleValidityVisibility('#pproof','#divValidityTwo');
		
		
			$('#pvalidity').val(data.pvalidity);
			
			if(	data.mobile != "") {
				$('#mobile').val(data.mobile).parent().addClass("populated");
			} 
			if(	data.histd != "") {
				$('#histd').val(data.histd).parent().addClass("populated");
			} 
			if(	data.hstd != "") {
				$('#hstd').val(data.hstd).parent().addClass("populated");
			} 
			if(	data.htelephone != "") {
				$('#htelephone').val(data.htelephone).parent().addClass("populated");
			} 
			if(	data.risd != "") {
				$('#risd').val(data.risd).parent().addClass("populated");
			} 
			if(	data.rstd != "") {
				$('#rstd').val(data.rstd).parent().addClass("populated");
			} 
			if(	data.rtelephone != "") {
				$('#rtelephone').val(data.rtelephone).parent().addClass("populated");
			} 
			if(	data.fisd != "") {
				$('#fisd').val(data.fisd).parent().addClass("populated");
			} 
			if(	data.fstd != "") {
				$('#fstd').val(data.fstd).parent().addClass("populated");
			} 
			if(	data.ftelphone != "") {
				$('#ftelphone').val(data.ftelphone).parent().addClass("populated");
			} 
			if(	data.bankname != "") {
				$('#bankname').val(data.bankname).parent().addClass("populated");
			} 
			if(	data.accountType != "") {
				$('#accountType').val(data.accountType).parent().addClass("populated");
			} 
			if(	data.accno != "") {
				$('#accno').val(data.accno).parent().addClass("populated");
			} 
			if(	data.reAccno != "") {
				$('#reAccno').val(data.reAccno).parent().addClass("populated");
			} 
			if(	data.bifsc != "") {
				$('#bifsc').val(data.bifsc).parent().addClass("populated");
			}
			if(	data.bmicr != "") {
				$('#bmicr').val(data.bmicr).parent().addClass("populated");
			} 
			if(	data.baddressline1 != "") {
				$('#baddressline1').val(data.baddressline1).parent().addClass("populated");
			} 
			if(	data.baddressline2 != "") {
				$('#baddressline2').val(data.baddressline2).parent().addClass("populated");
			} 
			if(	data.blandmark != "") {
				$('#blandmark').val(data.blandmark).parent().addClass("populated");
			} 
			if(	data.bpincode != "") {
				$('#bpincode').val(data.bpincode).parent().addClass("populated");
			} 
			if(	data.bcountry != "") {
				$('#bcountry').val(data.bcountry).parent().addClass("populated");
			} 
			if(	data.bstate != "") {
				$('#bstate').val(data.bstate).parent().addClass("populated");
			}
			if($('#bstate').val()!="" || $('#bstate').val()!="Select"||$('#bstate').val()!=null ){
				getStateWiseCitesSync('#bstate','#bcity');
			}
			if(	data.bcity != "") {
				$('#bcity').val(data.bcity).parent().addClass("populated");
			} 
			if(	data.cCityOther != "") {
				$('#cCityOther').val(data.cCityOther).parent().addClass("populated");
			} 
			if(	data.pCityOther != "") {
				$('#pCityOther').val(data.pCityOther).parent().addClass("populated");
			} 
			if(	data.bnkCityOther != "") {
				$('#bnkCityOther').val(data.bnkCityOther).parent().addClass("populated");
			} 
			if(	data.dp != "") {
				$('#dp').val(data.dp).parent().addClass("populated");
			} 
			if(	data.tradingtAccount != "") {
				$('#tradingtAccount').val(data.tradingtAccount).parent().addClass("populated");
			} 
			if(	data.dematAccount != "") {
				$('#dematAccount').val(data.dematAccount).parent().addClass("populated");
			} 
			if(	data.smsFacility != "") {
				$('#smsFacility').val(data.smsFacility).parent().addClass("populated");
			} 
			if(	data.fstHldrOccup != "") {
				$('#fstHldrOccup').val(data.fstHldrOccup).parent().addClass("populated");toggleEmployerData();
			} 
			if(	data.fstHldrOrg != "") {
				$('#fstHldrOrg').val(data.fstHldrOrg).parent().addClass("populated");
			} 
			if(	data.fstHldrDesig != "") {
				$('#fstHldrDesig').val(data.fstHldrDesig).parent().addClass("populated");
			} 
			if(	data.fstHldrIncome != "") {
				$('#fstHldrIncome').val(data.fstHldrIncome).parent().addClass("populated");
			} 
			if(	data.fstHldrNet != "") {
				$('#fstHldrNet').val(data.fstHldrNet).parent().addClass("populated");
			} 
			if(	data.fstHldrAmt != "") {
				$('#fstHldrAmt').val(data.fstHldrAmt).parent().addClass("populated");
			} 
			
			if(data.pep == "true") {
				$('#pep').bootstrapSwitch('state', true, true);
			} else {
				$('#pep').bootstrapSwitch('state', false, true);
			}
			//$('#pep').is(':checked');
			
			if(data.rpep == "true") {
				$('#rpep').bootstrapSwitch('state', true, true);
			} else {
				$('#rpep').bootstrapSwitch('state', false, true);
			}
			//$('#rpep').is(':checked');
			
			if(	data.scndHldrExist != "") {
				$('#scndHldrExist').val(data.scndHldrExist).parent().addClass("populated");
			} 
			if(	data.scndHldrName != "") {
				$('#scndHldrName').val(data.scndHldrName).parent().addClass("populated");
			} 
			if(	data.scndHldrOccup != "") {
				$('#scndHldrOccup').val(data.scndHldrOccup).parent().addClass("populated");
			} 
			if(	data.scndHldrOrg != "") {
				$('#scndHldrOrg').val(data.scndHldrOrg).parent().addClass("populated");
			} 
			if(	data.scndHldrDesig != "") {
				$('#scndHldrDesig').val(data.scndHldrDesig).parent().addClass("populated");
			} 
			if(	data.scndHldrSms != "") {
				$('#scndHldrSms').val(data.scndHldrSms).parent().addClass("populated");
			} 
			if(	data.scndHldrIncome != "") {
				$('#scndHldrIncome').val(data.scndHldrIncome).parent().addClass("populated");
			} 
			if(	data.scndHldrNet != "") {
				$('#scndHldrNet').val(data.scndHldrNet).parent().addClass("populated");
			} 
			if(	data.scndHldrAmt != "") {
				$('#scndHldrAmt').val(data.scndHldrAmt).parent().addClass("populated");
			} 
			if(	data.scndPep != "") {
				$('#scndPep').val(data.scndPep).parent().addClass("populated");
			} 
			if(	data.scndRpep != "") {
				$('#scndRpep').val(data.scndRpep).parent().addClass("populated");
			} 
			if(	data.instrn1 != "") {
				$('#instrn1').val(data.instrn1).parent().addClass("populated");
			} 
			if(	data.instrn2 != "") {
				$('#instrn2').val(data.instrn2).parent().addClass("populated");
			} 
			if(	data.instrn3 != "") {
				$('#instrn3').val(data.instrn3).parent().addClass("populated");
			} 
			if(	data.instrn4 != "") {
				$('#instrn4').val(data.instrn4).parent().addClass("populated");
			} 
			if(	data.instrn5 != "") {
				$('#instrn5').val(data.instrn5).parent().addClass("populated");
			} 
			if(	data.depoPartcpnt != "") {
				$('#depoPartcpnt').val(data.depoPartcpnt).parent().addClass("populated");
			} 
			if(	data.deponame != "") {
				$('#deponame').val(data.deponame).parent().addClass("populated");
			} 
			if(	data.beneficiary != "") {
				$('#beneficiary').val(data.beneficiary).parent().addClass("populated");
			} 
			if(	data.dpId != "") {
				$('#dpId').val(data.dpId).parent().addClass("populated");
			} 
			if(	data.docEvdnc != "") {
				$('#docEvdnc').val(data.docEvdnc).parent().addClass("populated");
			} 
			if(	data.experience != "") {
				$('#experience').val(data.experience).parent().addClass("populated");
			} 
			if(	data.contractNote != "") {
				$('#contractNote').val(data.contractNote).parent().addClass("populated");
			} 
			if(	data.experience != "") {
				$('#experience').val(data.experience).parent().addClass("populated");
			} 
			if(	data.alert != "") {
				$('#alert').val(data.alert).parent().addClass("populated");
			} 
			if(	data.relationship != "") {
				$('#relationship').val(data.relationship).parent().addClass("populated");
			} 
			if(	data.otherInformation != "") {
				$('#otherInformation').val(data.otherInformation).parent().addClass("populated");
			} 
			
			if (data.nomineeExist == "true" || data.nomineeExist == true) {
				$('#nomineeExist').bootstrapSwitch('state', true, true);
			} else {
				$('#nomineeExist').bootstrapSwitch('state', false, true);
			}
			//$('#nomineeExist').is(':checked');
			
			if(	data.nameNominee != "") {
				$('#nameNominee').val(data.nameNominee).parent().addClass("populated");
			} 
			if(	data.nomineeRelation != "") {
				$('#nomineeRelation').val(data.nomineeRelation).parent().addClass("populated");
			} 
			if(	data.nomineeDob != "") {
				$('#nomineeDob').val(data.nomineeDob).parent().addClass("populated");
			} 
			if(	data.nominePan != "") {
				$('#nominePan').val(data.nominePan).parent().addClass("populated");
			} 
			if(	data.nomineeAdrs1 != "") {
				$('#nomineeAdrs1').val(data.nomineeAdrs1).parent().addClass("populated");
			} 
			if(	data.nomineeAdrs2 != "") {
				$('#nomineeAdrs2').val(data.nomineeAdrs2).parent().addClass("populated");
			} 
			if(	data.nomineeLnd != "") {
				$('#nomineeLnd').val(data.nomineeLnd).parent().addClass("populated");
			} 
			if(	data.nomineePincode != "") {
				$('#nomineePincode').val(data.nomineePincode).parent().addClass("populated");
			} 
			if(	data.nomCountry != "") {
				$('#nomCountry').val(data.nomCountry).parent().addClass("populated");
			} 
			if(	data.nomState != "") {
				$('#nomState').val(data.nomState).parent().addClass("populated");
			} 
			if($('#nomState').val()!="" || $('#nomState').val()!="Select"||$('#nomState').val()!=null ){
				getStateWiseCitesSync('#nomState','#nomCity');
			}
			if(	data.nomCity != "") {
				$('#nomCity').val(data.nomCity).parent().addClass("populated");
			} 
			if(	data.noisd != "") {
				$('#noisd').val(data.noisd).parent().addClass("populated");
			} 
			if(	data.nostd != "") {
				$('#nostd').val(data.nostd).parent().addClass("populated");
			} 
			if(	data.notelephone != "") {
				$('#notelephone').val(data.notelephone).parent().addClass("populated");
			} 
			if(	data.nrisd != "") {
				$('#nrisd').val(data.nrisd).parent().addClass("populated");
			} 
			if(	data.nrstd != "") {
				$('#nrstd').val(data.nrstd).parent().addClass("populated");
			} 
			if(	data.nRtelephone != "") {
				$('#nRtelephone').val(data.nRtelephone).parent().addClass("populated");
			} 
			if(	data.nfisd != "") {
				$('#nfisd').val(data.nfisd).parent().addClass("populated");
			} 
			if(	data.nfstd != "") {
				$('#nfstd').val(data.nfstd).parent().addClass("populated");
			} 
			if(	data.nomineeFax != "") {
				$('#nomineeFax').val(data.nomineeFax).parent().addClass("populated");
			} 
			if(	data.nomMobile != "") {
				$('#nomMobile').val(data.nomMobile).parent().addClass("populated");
			} 
			if(	data.nomEmail != "") {
				$('#nomEmail').val(data.nomEmail).parent().addClass("populated");
			} 
			
			if (data.nomineeProof != "") {
				$('#nomineeProof').val(data.nomineeProof).parent().addClass("populated");nomineeProffToggle();
			}
			
			if (data.nominePan != "") {
				$('#nominePan').val(data.nominePan).parent().addClass("populated");
			}
			
			if (data.nomineAadhar != "") {
				$('#nomineAadhar').val(data.nomineAadhar).parent().addClass("populated");
			}
			
			if (data.minorExist == "true" || data.minorExist == true) {
				$('#minorExist').bootstrapSwitch('state', true, true);
			} else {
				$('#minorExist').bootstrapSwitch('state', false, true);
			}
			//$('#minorExist').is(':checked');
			
			if(	data.minorGuard != "") {
				$('#minorGuard').val(data.minorGuard).parent().addClass("populated");
			} 
			if(	data.mnrReltn != "") {
				$('#mnrReltn').val(data.mnrReltn).parent().addClass("populated");
			} 
			if(	data.mnrDob != "") {
				$('#mnrDob').val(data.mnrDob).parent().addClass("populated");
			} 
			if(	data.mnrAdrs1 != "") {
				$('#mnrAdrs1').val(data.mnrAdrs1).parent().addClass("populated");
			} 
			if(	data.mnrAdrs2 != "") {
				$('#mnrAdrs2').val(data.mnrAdrs2).parent().addClass("populated");
			} 
			if(	data.mnrLnd != "") {
				$('#mnrLnd').val(data.mnrLnd).parent().addClass("populated");
			} 
			if(	data.mnrCountry != "") {
				$('#mnrCountry').val(data.mnrCountry).parent().addClass("populated");
			} 
			if(	data.mnrState != "") {
				$('#mnrState').val(data.mnrState).parent().addClass("populated");
			}
			if($('#mnrState').val()!="" || $('#mnrState').val()!="Select"||$('#mnrState').val()!=null ){
				getStateWiseCitesSync('#mnrState','#mnrCity');
			}
			if(	data.mnrCity != "") {
				$('#mnrCity').val(data.mnrCity).parent().addClass("populated");
			} 
			if(	data.mnrPincode != "") {
				$('#mnrPincode').val(data.mnrPincode).parent().addClass("populated");
			} 
			if ( data.mnrProof != "") {
				$('#mnrProof').val(data.mnrProof).parent().addClass("populated");mnrProffToggle();
			}
			if ( data.mnrPan != "") {
				$('#mnrPan').val(data.mnrPan).parent().addClass("populated");
			}
			if ( data.mnrAadhar != "") {
				$('#mnrAadhar').val(data.mnrAadhar).parent().addClass("populated");
			}
			if(	data.moisd != "") {
				$('#moisd').val(data.moisd).parent().addClass("populated");
			} 
			if(	data.mostd != "") {
				$('#mostd').val(data.mostd).parent().addClass("populated");
			} 
			if(	data.motel != "") {
				$('#motel').val(data.motel).parent().addClass("populated");
			} 
			if(	data.mrisd != "") {
				$('#mrisd').val(data.mrisd).parent().addClass("populated");
			} 
			if(	data.mrstd != "") {
				$('#mrstd').val(data.mrstd).parent().addClass("populated");
			} 
			if(	data.mrtel != "") {
				$('#mrtel').val(data.mrtel).parent().addClass("populated");
			} 
			if(	data.mfisd != "") {
				$('#mfisd').val(data.mfisd).parent().addClass("populated");
			} 
			if(	data.mfstd != "") {
				$('#mfstd').val(data.mfstd).parent().addClass("populated");
			} 
			if(	data.minorfax != "") {
				$('#minorfax').val(data.minorfax).parent().addClass("populated");
			} 
			if(	data.mnrMob != "") {
				$('#mnrMob').val(data.mnrMob).parent().addClass("populated");
			} 
			if(	data.mnrEmail != "") {
				$('#mnrEmail').val(data.mnrEmail).parent().addClass("populated");
			} 
			if(	data.nomCityOther != "") {
				$('#nomCityOther').val(data.nomCityOther).parent().addClass("populated");
			} 
			if(	data.mnrCityOther != "") {
				$('#mnrCityOther').val(data.mnrCityOther).parent().addClass("populated");
			} 
			if(	data.docFilePath != "") {
				$('#docFilePath').val(data.docFilePath).parent().addClass("populated");
			} 
			$('#usNational').prop('checked', data.usNational);
			$('#usResident').prop('checked', data.usResident);
			$('#usBorn').prop('checked', data.usBorn);
			$('#usAddress').prop('checked', data.usAddress);
			$('#usTelephone').prop('checked', data.usTelephone);
			$('#usStandingInstruction').prop('checked', data.usStandingInstruction);
			$('#usPoa').prop('checked', data.usPoa);
			$('#usMailAddress').prop('checked', data.usMailAddress);
			
			if(	data.individualTaxIdntfcnNmbr != "") {
				$('#individualTaxIdntfcnNmbr').val(data.individualTaxIdntfcnNmbr).parent().addClass("populated");
			} 
			if(	data.secondHldrPan != "") {
				$('#secondHldrPan').val(data.secondHldrPan).parent().addClass("populated");
			} 
			if(	data.secondHldrDependentRelation != "") {
				$('#secondHldrDependentRelation').val(data.secondHldrDependentRelation).parent().addClass("populated");
			} 
			if(	data.secondHldrDependentUsed != "") {
				$('#secondHldrDependentUsed').val(data.secondHldrDependentUsed).parent().addClass("populated");
			} 
			if(	data.firstHldrDependentUsed != "") {
				$('#firstHldrDependentUsed').val(data.firstHldrDependentUsed).parent().addClass("populated");
			}
			displayFilePath('#panFilePathName',data.panFilePath);
			displayFilePath('#corsFilePathName',data.corsFilePath);
			displayFilePath('#prmntFilePathName',data.prmntFilePath);
			$('html,body').scrollTop(0);
		}
		catch(e)
		{
			console.log('Prefetch data failed.')
			console.log(e);
		}
		$('html,body').scrollTop(0);
		$('#divContent').css("display","block");
		$('#divLoading').css("display","none");
	}
        
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
        $('html,body').scrollTop(0);
    }, 1000);
    
	initSelectBox = function(){
		setSelectCountry();
		setSelectState();
		//setSelectCity();
		setSelectBank();
	}
    
    setSelectCountry = function(){
    	//var selectCountryOptions = ["Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antarctica","Antigua and Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia and Herzegowina","Botswana","Bouvet Island","Brazil","British Indian Ocean Territory","Brunei Darussalam","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic","Chad","Chile","China","Christmas Island","Cocos (Keeling) Islands","Colombia","Comoros","Congo","Congo, the Democratic Republic of the","Cook Islands","Costa Rica","Cote d'Ivoire","Croatia (Hrvatska)","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","East Timor","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands (Malvinas)","Faroe Islands","Fiji","Finland","France","France, Metropolitan","French Guiana","French Polynesia","French Southern Territories","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Heard and Mc Donald Islands","Holy See (Vatican City State)","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran (Islamic Republic of)","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, Democratic People's Republic of","Korea, Republic of","Kuwait","Kyrgyzstan","Lao People's Democratic Republic","Latvia","Lebanon","Lesotho","Liberia","Libyan Arab Jamahiriya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia, The Former Yugoslav Republic of","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Micronesia, Federated States of","Moldova, Republic of","Monaco","Mongolia","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","Northern Mariana Islands","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russian Federation","Rwanda","Saint Kitts and Nevis","Saint LUCIA","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Seychelles","Sierra Leone","Singapore","Slovakia (Slovak Republic)","Slovenia","Solomon Islands","Somalia","South Africa","South Georgia and the South Sandwich Islands","Spain","Sri Lanka","St. Helena","St. Pierre and Miquelon","Sudan","Suriname","Svalbard and Jan Mayen Islands","Swaziland","Sweden","Switzerland","Syrian Arab Republic","Taiwan, Province of China","Tajikistan","Tanzania, United Republic of","Thailand","Togo","Tokelau","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","United States Minor Outlying Islands","Uruguay","Uzbekistan","Vanuatu","Venezuela","Viet Nam","Virgin Islands (British)","Virgin Islands (U.S.)","Wallis and Futuna Islands","Western Sahara","Yemen","Yugoslavia","Zambia","Zimbabwe"];
    	//var selectCountryValues = ["AF","AL","DZ","AS","AD","AO","AI","AQ","AG","AR","AM","AW","AU","AT","AZ","BS","BH","BD","BB","BY","BE","BZ","BJ","BM","BT","BO","BA","BW","BV","BR","IO","BN","BG","BF","BI","KH","CM","CA","CV","KY","CF","TD","CL","CN","CX","CC","CO","KM","CG","CD","CK","CR","CI","HR","CU","CY","CZ","DK","DJ","DM","DO","TP","EC","EG","SV","GQ","ER","EE","ET","FK","FO","FJ","FI","FR","FX","GF","PF","TF","GA","GM","GE","DE","GH","GI","GR","GL","GD","GP","GU","GT","GN","GW","GY","HT","HM","VA","HN","HK","HU","IS","IN","ID","IR","IQ","IE","IL","IT","JM","JP","JO","KZ","KE","KI","KP","KR","KW","KG","LA","LV","LB","LS","LR","LY","LI","LT","LU","MO","MK","MG","MW","MY","MV","ML","MT","MH","MQ","MR","MU","YT","MX","FM","MD","MC","MN","MS","MA","MZ","MM","NA","NR","NP","NL","AN","NC","NZ","NI","NE","NG","NU","NF","MP","NO","OM","PK","PW","PA","PG","PY","PE","PH","PN","PL","PT","PR","QA","RE","RO","RU","RW","KN","LC","VC","WS","SM","ST","SA","SN","SC","SL","SG","SK","SI","SB","SO","ZA","GS","ES","LK","SH","PM","SD","SR","SJ","SZ","SE","CH","SY","TW","TJ","TZ","TH","TG","TK","TO","TT","TN","TR","TM","TC","TV","UG","UA","AE","GB","US","UM","UY","UZ","VU","VE","VN","VG","VI","WF","EH","YE","YU","ZM","ZW"];
    	
    	var selectCountryOptions = ["India"];
    	var selectCountryValues = ["IN"];
    	
    	$('.selectCountry').each(function(){
    		for(index in selectCountryOptions) {
    		    this.options[this.options.length] = new Option(selectCountryOptions[index], selectCountryValues[index]);
    		}
        });
    }

    setSelectState = function(){
			/*var formData = "type=emailId"+
					"&emailId="+inpEmail.value;*/
    	    
			var settings = {
			"async": true,
			"crossDomain": true,
			"url": _gc_url_statelist,
			"method": "POST",
			"headers": {
			"cache-control": "no-cache",
			"content-type": "application/x-www-form-urlencoded"
			},
			"processData": false,
			"contentType": false,
			"mimeType": "multipart/form-data"
			//"data": formData
			}
			
			$.ajax(settings).done(function (res) {
				//console.log(res);
				res = JSON.parse(res);
				var selectStateOptions = res;
				$('.selectState').each(function(){
		    		for(index in selectStateOptions) {
		    		    this.options[this.options.length] = new Option(selectStateOptions[index], selectStateOptions[index]);
		    		}
		    	});
				
			}).fail(function (response){
			alert(response);
			});
    	/*var selectStateOptions = ["Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Orissa","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"];
    	var selectStateValues = [];
    	
    	$('.selectState').each(function(){
    		for(index in selectStateOptions) {
    		    this.options[this.options.length] = new Option(selectStateOptions[index], selectStateOptions[index]);
    		}
    	});*/
    }
    
    setSelectCity = function(){
    	var selectCityOptions = ["Abhayapuri","Achabbal","Achhnera","Adalaj","Adari","Adilabad","Adityana","Adoni","Adoor","Adra, Purulia","Agartala","Agra","Ahiwara","Ahmedabad","Ahmedgarh","Ahmednagar","Aizawl","Ajmer","Akaltara","Akathiyoor","Akhnoor","Akola","Alang","Alappuzha","Aldona","Aligarh","Alipurduar","Allahabad","Almora","Along","Alwar","Amadalavalasa","Amalapuram","Amarpur","Ambagarh Chowki","Ambaji","Ambala","Ambaliyasan","Ambikapur","Amguri","Amlabad","Amli","Amravati","Amreli","Amritsar","Amroha","Anakapalle","Anand","Anandapur","Anandnagaar","Anantapur","Anantnag","Ancharakandy","Andada","Anjar","Anklav","Ankleshwar","Antaliya","Anugul","Ara","Arakkonam","Arambagh","Arambhada","Arang","Araria","Arasikere","Arcot","Areraj","Arki","Arnia","Aroor","Arrah","Aruppukkottai","Asankhurd","Asansol","Asarganj","Ashok Nagar","Ashtamichira","Asika","Asola","Assandh","Ateli","Attingal","Atul","Aurangabad","Avinissery","Awantipora","Azamgarh","Babiyal","Baddi","Bade Bacheli","Badepalle","Badharghat","Bagaha","Bahadurganj","Bahadurgarh","Baharampur","Bahraich","Bairgania","Bakhtiarpur","Balaghat","Balangir","Balasore","Baleshwar","Bali","Ballabhgarh","Ballia","Bally","Balod","Baloda Bazar","Balrampur","Balurghat","Bamra","Banda","Bandikui","Bandipore","Bangalore","Banganapalle","Banka","Bankura","Banmankhi Bazar","Banswara","Bapatla","Barahiya","Barakar","Baramati","Baramula","Baran","Barasat","Barauli","Barbigha","Barbil","Bardhaman","Bareilly","Bargarh","Barh","Baripada","Barmer","Barnala","Barpeta","Barpeta Road","Barughutu","Barwala","Basudebpur","Batala","Bathinda","Bazpur","Begusarai","Behea","Belgaum","Bellampalle","Bellary","Belpahar","Bemetra","Bethamcherla","Bettiah","Betul","Bhabua","Bhadrachalam","Bhadrak","Bhagalpur","Bhagha Purana","Bhainsa","Bharuch","Bhatapara","Bhavani","Bhavnagar","Bhawanipatna","Bheemunipatnam","Bhimavaram","Bhiwani","Bhongir","Bhopal","Bhuban","Bhubaneswar","Bhuj","Bidhan Nagar","Bihar Sharif","Bikaner","Bikramganj","Bilasipara","Bilaspur","Biramitrapur","Birgaon","Bobbili","Bodh Gaya","Bodhan","Bokaro Steel City","Bomdila","Bongaigaon","Brahmapur","Brajrajnagar","Budhlada","Burhanpur","Buxar","Byasanagar","Calcutta","Cambay","Chaibasa","Chakradharpur","Chalakudy","Chalisgaon","Chamba","Champa","Champhai","Chamrajnagar","Chandan Bara","Chandausi","Chandigarh","Chandrapura","Changanassery","Chanpatia","Charkhi Dadri","Chatra","Cheeka","Chendamangalam","Chengalpattu","Chengannur","Chennai","Cherthala","Cheruthazham","Chhapra","Chhatarpur","Chikkaballapur","Chikmagalur","Chilakaluripet","Chinchani","Chinna salem","Chinsura","Chintamani","Chirala","Chirkunda","Chirmiri","Chitradurga","Chittoor","Chittur-Thathamangalam","Chockli","Churi","Coimbatore","Colgong","Contai","Cooch Behar","Coonoor","Cuddalore","Cuddapah","Curchorem Cacora","Cuttack","Dabra","Dadri","Dahod","Dalhousie","Dalli-Rajhara","Dalsinghsarai","Daltonganj","Daman and Diu","Darbhanga","Darjeeling","Dasua","Datia","Daudnagar","Davanagere","Debagarh","Deesa","Dehradun","Dehri-on-Sone","Delhi","Deoghar","Deoria","Devarakonda","Devgarh","Dewas","Dhaka","Dhamtari","Dhanbad","Dhar","Dharampur, India","Dharamsala","Dharmanagar","Dharmapuri","Dharmavaram","Dharwad","Dhekiajuli","Dhenkanal","Dholka","Dhubri","Dhule","Dhuri","Dibrugarh","Digboi","Dighwara","Dimapur","Dinanagar","Dindigul","Diphu","Dipka","Dispur","Dombivli","Dongargarh","Dumka","Dumraon","Durg-Bhilai Nagar","Durgapur","Ellenabad 2","Eluru","Erattupetta","Erode","Etawah","Faridabad","Faridkot","Farooqnagar","Fatehabad","Fatehpur","Fatwah","Fazilka","Firozpur","Firozpur Cantt.","Forbesganj","Gadag","Gadwal","Ganaur","Gandhinagar","Gangtok","Garhwa","Gauripur","Gaya","Gharaunda","Ghatshila","Giddarbaha","Giridih","Goalpara","Gobindgarh","Gobranawapara","Godda","Godhra","Gogri Jamalpur","Gohana","Golaghat","Gomoh","Gooty","Gopalganj","Greater Noida","Gudalur","Gudivada","Gudur","Gulbarga","Gumia","Gumla","Gundlupet","Guntakal","Guntur","Gunupur","Gurdaspur","Gurgaon","Guruvayoor","Guwahati","Gwalior","Haflong","Haibat(Yamuna Nagar)","Hailakandi","Hajipur","Haldia","Haldwani","Hamirpur","Hansi","Hanuman Junction","Hardoi","Haridwar","Hassan","Hazaribag","Hilsa","Himatnagar","Hindupur","Hinjilicut","Hisar","Hisua","Hodal","Hojai","Hoshiarpur","Hospet","Howrah","Hubli","Hussainabad","Hyderabad","Ichalkaranji","Ichchapuram","Idar","Imphal","Indore","Indranagar","Irinjalakuda","Islampur","Itanagar","Itarsi","Jabalpur","Jagatsinghapur","Jagdalpur","Jagdispur","Jaggaiahpet","Jagraon","Jagtial","Jaipur","Jaisalmer","Jaitu","Jajapur","Jajmau","Jalalabad","Jalandhar","Jalandhar Cantt.","Jaleswar","Jalna","Jalore","Jamalpur","Jammalamadugu","Jammu","Jamnagar","Jamshedpur","Jamtara","Jamui","Jandiala","Jangaon","Janjgir","Jashpurnagar","Jaspur","Jatani","Jaunpur","Jehanabad","Jeypur","Jhajha","Jhajjar","Jhanjharpur","Jhansi","Jhargram","Jharsuguda","Jhumri Tilaiya","Jind","Joda","Jodhpur","Jogabani","Jogendranagar","Jorhat","Jowai","Junagadh","Kadapa","Kadi","Kadiri","Kadirur","Kagaznagar","Kailasahar","Kaithal","Kakching","Kakinada","Kalan Wali","Kalavad","Kalka","Kalliasseri","Kalol","Kalpetta","Kalpi","Kalyan","Kalyandurg","Kamareddy","Kanchipuram","Kandukur","Kanhangad","Kanjikkuzhi","Kanker","Kannur","Kanpur","Kantabanji","Kanti","Kapadvanj","Kapurthala","Karaikal","Karaikudi","Karanjia","Karimganj","Karimnagar","Karjan","Karkala","Karnal","Karoran","Kartarpur","Karungal","Karur","Karwar","Kasaragod","Kashipur","Kathua","Katihar","Katni","Kavali","Kavaratti","Kawardha","Kayamkulam","Kendrapara","Kendujhar","Keshod","Khagaria","Khambhalia","Khambhat","Khammam","Khanna","Kharagpur","Kharar","Kheda","Khedbrahma","Kheralu","Khordha","Khowai","Khunti","kichha","Kishanganj","Kochi","Kodinar","Kodungallur","Kohima","Kokrajhar","Kolar","Kolhapur","Kolkata","Kollam","Kollankodu","Kondagaon","Koothuparamba","Koraput","Koratla","Korba","Kot Kapura","Kota","Kotdwara","Kothagudem","Kothamangalam","Kothapeta","Kotma","Kottayam","Kovvur","Kozhikode","Kunnamkulam","Kurali","Kurnool","Kyathampalle","Lachhmangarh","Ladnu","Ladwa","Lahar","Laharpur","Lakheri","Lakhimpur","Lakhisarai","Lakshmeshwar","Lal Gopalganj Nindaura","Lalganj","Lalgudi","Lalitpur","Lalsot","Lanka","Lar","Lathi","Latur","Leh","Lilong","Limbdi","Lingsugur","Loha","Lohardaga","Lonar","Lonavla","Longowal","Loni","Losal","Lucknow","Ludhiana","Lumding","Lunawada","Lundi","Lunglei","Macherla","Machilipatnam","Madanapalle","Maddur","Madgaon","Madhepura","Madhubani","Madhugiri","Madhupur","Madikeri","Madurai","Magadi","Mahad","Mahalingpur","Maharajganj","Maharajpur","Mahasamund","Mahbubnagar","Mahe","Mahendragarh","Mahesana","Mahidpur","Mahnar Bazar","Mahuli","Mahuva","Maihar","Mainaguri","Makhdumpur","Makrana","Mal","Malajkhand","Malappuram","Malavalli","Malegaon","Malerkotla","Malkangiri","Malkapur","Malout","Malpura","Malur","Manasa","Manavadar","Manawar","Manchar","Mancherial","Mandalgarh","Mandamarri","Mandapeta","Mandawa","Mandi","Mandi Dabwali","Mandideep","Mandla","Mandsaur","Mandvi","Mandya","Maner","Mangaldoi","Mangalore","Mangalvedhe","Manglaur","Mangrol","Mangrulpir","Manihari","Manjlegaon","Mankachar","Manmad","Mansa","Manuguru","Manvi","Manwath","Mapusa","Margao","Margherita","Marhaura","Mariani","Marigaon","Markapur","Marmagao","Masaurhi","Mathabhanga","Mathura","Mattannur","Mauganj","Maur","Mavelikkara","Mavoor","Mayang Imphal","Medak","Medinipur","Meerut","Mehkar","Mehmedabad","Memari","Merta City","Mhaswad","Mhow Cantonment","Mhowgaon","Mihijam","Miraj","Mirganj","Miryalaguda","Modasa","Modinagar","Moga","Mogalthur","Mohali","Mokameh","Mokokchung","Monoharpur","Morena","Morinda","Morshi","Morvi","Motihari","Motipur","Mount Abu","Mudalgi","Mudbidri","Muddebihal","Mudhol","Mukerian","Mukhed","Muktsar","Mul","Mulbagal","Multai","Mumbai","Mundargi","Mungeli","Munger","Muradnagar","Murliganj","Murshidabad","Murtijapur","Murwara","Musabani","Mussoorie","Muvattupuzha","Muzaffarnagar","Muzaffarpur","Mysore","Nabadwip","Nabarangapur","Nabha","Nadbai","Nadiad","Nagaon","Nagapattinam","Nagar","Nagari","Nagarkurnool","Nagaur","Nagda","Nagercoil","Nagina","Nagla","Nagpur","Nahan","Naharlagun","Naihati","Naila Janjgir","Nainital","Nainpur","Najibabad","Nakodar","Nakur","Nalasopara","Nalbari","Namagiripettai","Namakkal","Nanded-Waghala","Nandgaon","Nandivaram-Guduvancheri","Nandura","Nandurbar","Nandyal","Nangal","Nanjangud","Nanjikottai","Nanpara","Narasapur","Narasaraopet","Naraura","Narayanpet","Nargund","Narkatiaganj","Narkhed","Narnaul","Narsinghgarh","Narsipatnam","Narwana","Nashik","Nasirabad","Natham","Nathdwara","Naugachhia","Naugawan Sadat","Nautanwa","Navalgund","Navi Mumbai","Navsari","Nawabganj","Nawada","Nawalgarh","Nawanshahr","Nawapur","Nedumangad","Neem-Ka-Thana","Neemuch","Nehtaur","Nelamangala","Nellikuppam","Nellore","Nepanagar","Neyveli","Neyyattinkara","Nidadavole","Nilanga","Nimbahera","Nipani","Nirmal","Niwai","Niwari","Nizamabad","Nohar","NOIDA","Nokha","Nongstoin","Noorpur","North Lakhimpur","Nowgong","Nowrozabad","Nuzvid","O Valley","Obra","Oddanchatram","Ongole","Orai","Osmanabad","Ottappalam","Ozar","P.N.Patti","Pachora","Pachore","Pacode","Padmanabhapuram","Padra","Padrauna","Paithan","Pakaur","Palacole","Palai","Palakkad","Palani","Palanpur","Palasa Kasibugga","Palghar","Pali","Palia Kalan","Palitana","Palladam","Pallapatti","Pallikonda","Palwal","Palwancha","Panagar","Panagudi","Panaji","Panchkula","Panchla","Pandharkaoda","Pandharpur","Pandhurna","Pandua","Panipat","Panna","Panniyannur","Panruti","Panvel","Pappinisseri","Paradip","Paramakudi","Parangipettai","Parasi","Paravoor","Parbhani","Pardi","Parlakhemundi","Parli","Parola","Partur","Parvathipuram","Pasan","Paschim Punropara","Pasighat","Patan","Pathanamthitta","Pathankot","Pathardi","Pathri","Patiala","Patna","Patran","Patratu","Pattamundai","Patti","Pattukkottai","Patur","Pauni","Pauri","Pavagada","Payyannur","Pedana","Peddapuram","Pehowa","Pen","Perambalur","Peravurani","Peringathur","Perinthalmanna","Periyakulam","Periyasemur","Pernampattu","Perumbavoor","Petlad","Phagwara","Phalodi","Phaltan","Phillaur","Phulabani","Phulera","Phulpur","Phusro","Pihani","Pilani","Pilibanga","Pilibhit","Pilkhuwa","Pindwara","Pinjore","Pipar City","Pipariya","Piro","Pithampur","Pithapuram","Pithoragarh","Pollachi","Polur","Pondicherry","Ponduru","Ponnani","Ponneri","Ponnur","Porbandar","Porsa","Port Blair","Powayan","Prantij","Pratapgarh","Prithvipur","Proddatur","Pudukkottai","Pudupattinam","Pukhrayan","Pulgaon","Puliyankudi","Punalur","Punch","Pune","Punganur","Punjaipugalur","Puranpur","Puri","Purna","Purnia","Purquazi","Purulia","Purwa","Pusad","Puttur","Qadian","Quilandy","Rabkavi Banhatti","Radhanpur","Rae Bareli","Rafiganj","Raghogarh-Vijaypur","Raghunathpur","Rahatgarh","Rahuri","Raichur","Raiganj","Raigarh","Raikot","Raipur","Rairangpur","Raisen","Raisinghnagar","Rajagangapur","Rajahmundry","Rajakhera","Rajaldesar","Rajam","Rajampet","Rajapalayam","Rajauri","Rajgarh","Rajgarh (Alwar)","Rajgarh (Churu","Rajgir","Rajkot","Rajnandgaon","Rajpipla","Rajpura","Rajsamand","Rajula","Rajura","Ramachandrapuram","Ramagundam","Ramanagaram","Ramanathapuram","Ramdurg","Rameshwaram","Ramganj Mandi","Ramnagar","Ramngarh","Rampur","Rampur Maniharan","Rampura Phul","Rampurhat","Ramtek","Ranaghat","Ranavav","Ranchi","Rangia","Rania","Ranibennur","Rapar","Rasipuram","Rasra","Ratangarh","Rath","Ratia","Ratlam","Ratnagiri","Rau","Raurkela","Raver","Rawatbhata","Rawatsar","Raxaul Bazar","Rayachoti","Rayadurg","Rayagada","Reengus","Rehli","Renigunta","Renukoot","Reoti","Repalle","Revelganj","Rewa","Rewari","Rishikesh","Risod","Robertsganj","Robertson Pet","Rohtak","Ron","Roorkee","Rosera","Rudauli","Rudrapur","Rupnagar","Sabalgarh","Sadabad","Sadalgi","Sadasivpet","Sadri","Sadulshahar","Safidon","Safipur","Sagar","Sagwara","Saharanpur","Saharsa","Sahaspur","Sahaswan","Sahawar","Sahibganj","Sahjanwa","Saidpur, Ghazipur","Saiha","Sailu","Sainthia","Sakleshpur","Sakti","Salaya","Salem","Salur","Samalkha","Samalkot","Samana","Samastipur","Sambalpur","Sambhal","Sambhar","Samdhan","Samthar","Sanand","Sanawad","Sanchore","Sandi","Sandila","Sandur","Sangamner","Sangareddy","Sangaria","Sangli","Sangole","Sangrur","Sankarankoil","Sankari","Sankeshwar","Santipur","Sarangpur","Sardarshahar","Sardhana","Sarni","Sasaram","Sasvad","Satana","Satara","Sathyamangalam","Satna","Sattenapalle","Sattur","Saunda","Saundatti-Yellamma","Sausar","Savanur","Savarkundla","Savner","Sawai Madhopur","Sawantwadi","Sedam","Sehore","Sendhwa","Seohara","Seoni","Seoni-Malwa","Shahabad","Shahabad, Hardoi","Shahabad, Rampur","Shahade","Shahbad","Shahdol","Shahganj","Shahjahanpur","Shahpur","Shahpura","Shajapur","Shamgarh","Shamli","Shamsabad, Agra","Shamsabad, Farrukhabad","Shegaon","Sheikhpura","Shendurjana","Shenkottai","Sheoganj","Sheohar","Sheopur","Sherghati","Sherkot","Shiggaon","Shikapur","Shikarpur, Bulandshahr","Shikohabad","Shillong","Shimla","Shimoga","Shirdi","Shirpur-Warwade","Shirur","Shishgarh","Shivpuri","Sholavandan","Sholingur","Shoranur","Shorapur","Shrigonda","Shrirampur","Shrirangapattana","Shujalpur","Siana","Sibsagar","Siddipet","Sidhi","Sidhpur","Sidlaghatta","Sihor","Sihora","Sikanderpur","Sikandra Rao","Sikandrabad","Sikar","Silao","Silapathar","Silchar","Siliguri","Sillod","Silvassa","Simdega","Sindgi","Sindhnur","Singapur","Singrauli","Sinnar","Sira","Sircilla","Sirhind Fatehgarh Sahib","Sirkali","Sirohi","Sironj","Sirsa","Sirsaganj","Sirsi","Siruguppa","Sitamarhi","Sitapur","Sitarganj","Sivaganga","Sivagiri","Sivakasi","Siwan","Sohagpur","Sohna","Sojat","Solan","Solapur","Sonamukhi","Sonepur","Songadh","Sonipat","Sopore","Soro","Soron","Soyagaon","Sri Madhopur","Srikakulam","Srikalahasti","Srinagar","Srinivaspur","Srirampore","Srivilliputhur","Suar","Sugauli","Sujangarh","Sujanpur","Sultanganj","Sultanpur","Sumerpur","Sunabeda","Sunam","Sundargarh","Sundarnagar","Supaul","Surandai","Surat","Suratgarh","Suri","Suriyampalayam","Suryapet","Tadepalligudem","Tadpatri","Taki","Talaja","Talcher","Talegaon Dabhade","Talikota","Taliparamba","Talode","Talwara","Tamluk","Tanda","Tandur","Tanuku","Tarakeswar","Tarana","Taranagar","Taraori","Tarikere","Tarn Taran","Tasgaon","Tehri","Tekkalakota","Tenali","Tenkasi","Tenu Dam-cum- Kathhara","Terdal","Tetri Bazar","Tezpur","Thakurdwara","Thammampatti","Thana Bhawan","Thanesar","Thangadh","Thanjavur","Tharad","Tharamangalam","Tharangambadi","Theni Allinagaram","Thirumangalam","Thirunindravur","Thiruparappu","Thirupuvanam","Thiruthuraipoondi","Thiruvalla","Thiruvallur","Thiruvananthapuram","Thiruvarur","Thodupuzha","Thoothukudi","Thoubal","Thrissur","Thuraiyur","Tikamgarh","Tilda Newra","Tilhar","Tindivanam","Tinsukia","Tiptur","Tirora","Tiruchendur","Tiruchengode","Tiruchirappalli","Tirukalukundram","Tirukkoyilur","Tirunelveli","Tirupathur","Tirupati","Tiruppur","Tirur","Tiruttani","Tiruvannamalai","Tiruvethipuram","Tirwaganj","Titlagarh","Tittakudi","Todabhim","Todaraisingh","Tohana","Tonk","Tuensang","Tuljapur","Tulsipur","Tumkur","Tumsar","Tundla","Tuni","Tura","Uchgaon","Udaipur","Udaipurwati","Udgir","Udhagamandalam","Udhampur","Udumalaipettai","Udupi","Ujhani","Ujjain","Umarga","Umaria","Umarkhed","Umarkote","Umbergaon","Umred","Umreth","Una","Unjha","Unnamalaikadai","Unnao","Upleta","Uran","Uran Islampur","Uravakonda","Urmar Tanda","Usilampatti","Uthamapalayam","Uthiramerur","Utraula","Vadakara","Vadakkuvalliyur","Vadalur","Vadgaon Kasba","Vadipatti","Vadnagar","Vadodara","Vaijapur","Vaikom","Valparai","Valsad","Vandavasi","Vaniyambadi","Vapi","Varanasi","Varkala","Vasai","Vedaranyam","Vellakoil","Vellore","Venkatagiri","Veraval","Vicarabad","Vidisha","Vijainagar","Vijapur","Vijayapura","Vijayawada","Vikramasingapuram","Viluppuram","Vinukonda","Viramgam","Virar","Virudhachalam","Virudhunagar","Visakhapatnam","Visnagar","Viswanatham","Vita","Vizianagaram","Vrindavan","Vyara","Wadgaon Road","Wadhwan","Wadi","Wai","Wanaparthy","Wani","Wankaner","Wara Seoni","Warangal","Wardha","Warhapur","Warisaliganj","Warora","Warud","Washim","Wokha","Yadgir","Yamunanagar","Yanam","Yavatmal","Yawal","Yellandu","Yemmiganur","Yerraguntla","Yevla","Zahirabad","Zaidpur","Zamania","Zira","Zirakpur","Zunheboto"];
    	var selectCityValues = [];
    	
    	$('.selectCity').each(function(){
    		for(index in selectCityOptions) {
    		    this.options[this.options.length] = new Option(selectCityOptions[index], selectCityOptions[index]);
    		}
    	});
    }

    setSelectBank = function(){
    	var selectBankOptions = ["ABHYUDAYA CO-OP BANK LTD","ABU DHABI COMMERCIAL BANK","AKOLA DISTRICT CENTRAL CO-OPERATIVE BANK","AKOLA JANATA COMMERCIAL COOPERATIVE BANK","ALLAHABAD BANK","ALMORA URBAN CO-OPERATIVE BANK LTD","ANDHRA BANK","ANDHRA PRAGATHI GRAMEENA BANK","APNA SAHAKARI BANK LTD","AUSTRALIA AND NEW ZEALAND BANKING GROUP LIMITED","AXIS BANK","BANK INTERNASIONAL INDONESIA","BANK OF AMERICA","BANK OF BAHRAIN AND KUWAIT","BANK OF BARODA","BANK OF INDIA","BANK OF CEYLON","BANK OF TOKYO-MITSUBISHI UFJ LTD","BANK OF MAHARASHTRA","BASSEIN CATHOLIC CO-OP BANK LTD","BARCLAYS BANK PLC","BNP PARIBAS","BHARATIYA MAHILA BANK LIMITED","CANARA BANK","CALYON BANK","CATHOLIC SYRIAN BANK LTD","CAPITAL LOCAL AREA BANK LTD","CHINATRUST COMMERCIAL BANK","CENTRAL BANK OF INDIA","CITIZENCREDIT CO-OPERATIVE BANK LTD","CITIBANK NA","CORPORATION BANK","CREDIT SUISSE AG","CITY UNION BANK LTD","COMMONWEALTH BANK OF AUSTRALIA","DEUTSCHE BANK","DEUTSCHE SECURITIES INDIA PRIVATE LIMITED","DBS BANK LTD","DENA BANK","DICGC","DOMBIVLI NAGARI SAHAKARI BANK LIMITED","DEVELOPMENT CREDIT BANK LIMITED","DHANLAXMI BANK LTD","GURGAON GRAMIN BANK","HDFC BANK LTD","FIRSTRAND BANK LIMITED","GOPINATH PATIL PARSIK JANATA SAHAKARI BANK LTD","IDRBT","IDBI BANK LTD","ICICI BANK LTD","HSBC","INDUSTRIAL AND COMMERCIAL BANK OF CHINA LIMITED","INDUSIND BANK LTD","INDIAN OVERSEAS BANK","INDIAN BANK","JANASEVA SAHAKARI BANK (BORIVLI) LTD","JANAKALYAN SAHAKARI BANK LTD","JALGAON JANATA SAHKARI BANK LTD","ING VYSYA BANK LTD","KALLAPPANNA AWADE ICH JANATA S BANK","JPMORGAN CHASE BANK N.A","JANATA SAHAKARI BANK LTD (PUNE)","JANASEVA SAHAKARI BANK LTD. PUNE","KOTAK MAHINDRA BANK","KURMANCHAL NAGAR SAHKARI BANK LTD","MAHANAGAR CO-OP BANK LTD","MAHARASHTRA STATE CO OPERATIVE BANK","KAPOL CO OP BANK","KARNATAKA BANK LTD","KARNATAKA VIKAS GRAMEENA BANK","KARUR VYSYA BANK","NATIONAL AUSTRALIA BANK","NEW INDIA CO-OPERATIVE BANK LTD","NKGSB CO-OP BANK LTD","NORTH MALABAR GRAMIN BANK","MASHREQBANK PSC","MIZUHO CORPORATE BANK LTD","MUMBAI DISTRICT CENTRAL CO-OP. BANK LTD","NAGPUR NAGRIK SAHAKARI BANK LTD","PRIME CO OPERATIVE BANK LTD","PRATHAMA BANK","PUNJAB AND SIND BANK","PUNJAB AND MAHARASHTRA CO-OP BANK LTD","OMAN INTERNATIONAL BANK SAOG","NUTAN NAGARIK SAHAKARI BANK LTD","PARSIK JANATA SAHAKARI BANK LTD","ORIENTAL BANK OF COMMERCE","SBERBANK","RESERVE BANK OF INDIA","SHRI CHHATRAPATI RAJARSHI SHAHU URBAN CO-OP BANK LTD","SHINHAN BANK","RABOBANK INTERNATIONAL (CCRB)","PUNJAB NATIONAL BANK","RAJKOT NAGARIK SAHAKARI BANK LTD","RAJGURUNAGAR SAHAKARI BANK LTD","STATE BANK OF INDIA","STATE BANK OF MAURITIUS LTD","STATE BANK OF BIKANER AND JAIPUR","STATE BANK OF HYDERABAD","SOUTH INDIAN BANK","STANDARD CHARTERED BANK","SOCIETE GENERALE","SOLAPUR JANATA SAHKARI BANK LTD.SOLAPUR","THANE BHARAT SAHAKARI BANK LTD","THE A.P. MAHESH CO-OP URBAN BANK LTD","SYNDICATE BANK","TAMILNAD MERCANTILE BANK LTD","STATE BANK OF TRAVANCORE","SUMITOMO MITSUI BANKING CORPORATION","STATE BANK OF MYSORE","STATE BANK OF PATIALA","THE FEDERAL BANK LTD","THE DELHI STATE COOPERATIVE BANK LTD","THE COSMOS CO-OPERATIVE BANK LTD","THE BHARAT CO-OPERATIVE BANK (MUMBAI) LTD","THE BANK OF RAJASTHAN LTD","THE BANK OF NOVA SCOTIA","THE ANDHRA PRADESH STATE COOP BANK LTD","THE AHMEDABAD MERCANTILE CO-OPERATIVE BANK LTD","THE KANGRA CENTRAL CO-OPERATIVE BANK LTD","THE KALYAN JANATA SAHAKARI BANK LTD","THE KALUPUR COMMERCIAL CO. OP. BANK LTD","THE JAMMU AND KASHMIR BANK LTD","THE JALGAON PEOPLES CO-OP BANK","THE GUJARAT STATE CO-OPERATIVE BANK LTD","THE GREATER BOMBAY CO-OP. BANK LTD","THE GADCHIROLI DISTRICT CENTRAL COOPERATIVE BANK LTD","THE RATNAKAR BANK LTD","THE RAJASTHAN STATE COOPERATIVE BANK LTD","THE SAHEBRAO DESHMUKH CO-OP. BANK LTD","THE ROYAL BANK OF SCOTLAND N.V","THE SEVA VIKAS CO-OPERATIVE BANK LTD (SVB)","THE SARASWAT CO-OPERATIVE BANK LTD","THE SURAT DISTRICT CO OPERATIVE BANK LTD","THE SHAMRAO VITHAL CO-OPERATIVE BANK LTD","THE KARAD URBAN CO-OP BANK LTD","THE KANGRA COOPERATIVE BANK LTD","THE LAKSHMI VILAS BANK LTD","THE KARNATAKA STATE APEX COOP. BANK LTD","THE MUNICIPAL CO OPERATIVE BANK LTD MUMBAI","THE MEHSANA URBAN COOPERATIVE BANK LTD","THE NASIK MERCHANTS CO-OP BANK LTD., NASHIK","THE NAINITAL BANK LIMITED","TJSB SAHAKARI BANK LTD","TUMKUR GRAIN MERCHANTS COOPERATIVE BANK LTD","UBS AG","UCO BANK","UNION BANK OF INDIA","UNITED BANK OF INDIA","UNITED OVERSEAS BANK","VASAI VIKAS SAHAKARI BANK LTD","THE SURAT PEOPLES CO-OP BANK LTD","THE SUTEX CO.OP. BANK LTD","THE TAMILNADU STATE APEX COOPERATIVE BANK LIMITED","THE THANE DISTRICT CENTRAL CO-OP BANK LTD","THE THANE JANATA SAHAKARI BANK LTD","THE VARACHHA CO-OP. BANK LTD","THE VISHWESHWAR SAHAKARI BANK LTD.,PUNE","THE WEST BENGAL STATE COOPERATIVE BANK LTD","WOORI BANK","WESTPAC BANKING CORPORATION","WEST BENGAL STATE COOPERATIVE BANK","VIJAYA BANK","ZILA SAHAKRI BANK LIMITED GHAZI","YES BANK","ZILA SAHKARI BANK LTD GHAZIABAD","YES BANK LTD"];
    	var selectBankValues = [];
    	
    	$('.selectBank').each(function(){
    		for(index in selectBankOptions ) {
    		    this.options[this.options.length] = new Option(selectBankOptions[index], selectBankOptions[index]);
    		}
        });
    }
    
    var sPanFilePath = '';
    var sUidFilePath = '';
    var sCorsFilePath = '';
    var sPrmntFilePath = '';
    
    makePostJson = function(){
    	postJson = '';
    	postJson = postJson + '"regId" : null,';
    	postJson = postJson + '"email" : "' +  $('#email').val() + '",';
    	postJson = postJson + '"firstname" : "' +  $('#firstname').val() + '",';
    	if($('#middlename').val()!= 'Middle Name'){
    		postJson = postJson + '"middlename" : "' +  $('#middlename').val() + '",';
    	}else{
    		postJson = postJson + '"middlename" : "",';
    	}
    	
    	postJson = postJson + '"lastname" : "' +  $('#lastname').val() + '",';
    	postJson = postJson + '"fatherSpouse" : "' +  $('#fatherSpouse').val() + '",';
    	postJson = postJson + '"motherName" : "' + $('#motherName').val() + '",';
    	postJson = postJson + '"dob" : "' +  $('#dob').val() + '",';
    	postJson = postJson + '"nationality" : "' +  $('#nationality').val() + '",';
    	postJson = postJson + '"status" : "' +  $('#status').val() + '",';
    	postJson = postJson + '"gender" : "' +  $('#gender').val() + '",';
    	postJson = postJson + '"mstatus" : "' +  $('#mstatus').val() + '",';
    	if($('#uid').val()!='UID/Aadhaar Number'){
    		postJson = postJson + '"uid" : "' +  $('#uid').val() + '",';
    	}else{
    		postJson = postJson + '"uid" : "",';
    	}
    	
    	postJson = postJson + '"pan" : "' +  $('#pan').val() + '",';
    	postJson = postJson + '"caddressline1" : "' +  $('#caddressline1').val() + '",';
    	if($('#caddressline2').val()!='Address line 2') {
    		postJson = postJson + '"caddressline2" : "' +  $('#caddressline2').val() + '",';
    	} else {
    		postJson = postJson + '"caddressline2" : "",';
    	}
    	
    	postJson = postJson + '"clandmark" : "",';
    	postJson = postJson + '"cpincode" : "' +  $('#cpincode').val() + '",';
    	postJson = postJson + '"country" : "IN",';
    	postJson = postJson + '"cstate" : "' +  $('#cstate').val() + '",';
    	postJson = postJson + '"ccity" : "' +  $('#ccity').val() + '",';
    	postJson = postJson + '"cproof" : "' +  $('#cproof').val() + '",';
    	if($('#cvalidity').val()!= 'Validity Date'){
    		postJson = postJson + '"cvalidity" : "' +  $('#cvalidity').val() + '",';
    	}else {
    		postJson = postJson + '"cvalidity" : "",';
    	}
    	
    	postJson = postJson + '"permenentAddress" : "' +  $('#permenentAddress').is(':checked') + '",';
    	postJson = postJson + '"paddressline1" : "' +  $('#paddressline1').val() + '",';
    	if($('#paddressline2').val()!='Address line 2') {
    		postJson = postJson + '"paddressline2" : "' +  $('#paddressline2').val() + '",';
    	} else {
    		postJson = postJson + '"paddressline2" : "",';
    	}
    	
    	//postJson = postJson + '"plandmark" : "' +  $('#plandmark').val() + '",';
    	postJson = postJson + '"plandmark" : "",';
    	postJson = postJson + '"ppin" : "' +  $('#ppin').val() + '",';
    	postJson = postJson + '"pcountry" : "' +  $('#pcountry').val() + '",';
    	postJson = postJson + '"pstate" : "' +  $('#pstate').val() + '",';
    	postJson = postJson + '"pcity" : "' +  $('#pcity').val() + '",';
    	postJson = postJson + '"pproof" : "' +  $('#pproof').val() + '",';
    	if($('#pvalidity').val()!= 'Validity Date'){
    		postJson = postJson + '"pvalidity" : "' +  $('#pvalidity').val() + '",';
    	}else {
    		postJson = postJson + '"pvalidity" : "",';
    	}
    	
    	postJson = postJson + '"mobile" : "' +  $('#mobile').val() + '",';
    	if($('#histd').val() != 'ISD'){
    		postJson = postJson + '"histd" : "' +  $('#histd').val() + '",';
    	} else {
    		postJson = postJson + '"histd" : "",';
    	}
    	
    	if($('#hstd').val() != 'STD'){
    		postJson = postJson + '"hstd" : "' +  $('#hstd').val() + '",';
    	} else {
    		postJson = postJson + '"hstd" : "",';
    	}
    	
    	if($('#htelephone').val() != 'Landline'){
    		postJson = postJson + '"htelephone" : "' +  $('#htelephone').val() + '",';
    	} else {
    		postJson = postJson + '"htelephone" : "",';
    	}
    	postJson = postJson + '"risd" : "' +  $('#risd').val() + '",';
    	postJson = postJson + '"rstd" : "' +  $('#rstd').val() + '",';
    	postJson = postJson + '"rtelephone" : "' +  $('#rtelephone').val() + '",';
    	postJson = postJson + '"fisd" : "' +  $('#fisd').val() + '",';
    	postJson = postJson + '"fstd" : "' +  $('#fstd').val() + '",';
    	postJson = postJson + '"ftelphone" : "' +  $('#ftelphone').val() + '",';
    	postJson = postJson + '"bankname" : "' +  $('#bankname').val() + '",';
    	postJson = postJson + '"accountType" : "' +  $('#accountType').val() + '",';
    	postJson = postJson + '"accno" : "' +  $('#accno').val() + '",';
    	postJson = postJson + '"reAccno" : "' +  $('#reAccno').val() + '",';
    	postJson = postJson + '"bifsc" : "' +  $('#bifsc').val() + '",';
    	postJson = postJson + '"bmicr" : "' +  $('#bmicr').val() + '",';
    	postJson = postJson + '"baddressline1" : "' +  $('#baddressline1').val() + '",';
    	if($('#baddressline2').val()!='Address line 2') {
    		postJson = postJson + '"baddressline2" : "' +  $('#baddressline2').val() + '",';
    	} else {
    		postJson = postJson + '"baddressline2" : "",';
    	}
    	
    	postJson = postJson + '"blandmark" : "' +  $('#blandmark').val() + '",';
    	postJson = postJson + '"bpincode" : "' +  $('#bpincode').val() + '",';
    	postJson = postJson + '"bcountry" : "' +  $('#bcountry').val() + '",';
    	postJson = postJson + '"bstate" : "' +  $('#bstate').val() + '",';
    	postJson = postJson + '"bcity" : "' +  $('#bcity').val() + '",';
    	postJson = postJson + '"cCityOther" : "' +  $('#cCityOther').val() + '",';
    	postJson = postJson + '"pCityOther" : "' +  $('#pCityOther').val() + '",';
    	postJson = postJson + '"bnkCityOther" : "' +  $('#bnkCityOther').val() + '",';
    	postJson = postJson + '"panFilePath" : "' +  sPanFilePath + '",';
    	postJson = postJson + '"corsFilePath" : "' +  sCorsFilePath + '",';
    	postJson = postJson + '"prmntFilePath" : "' +  sPrmntFilePath + '",';
    	postJson = postJson + '"openAccountType" : "Trading Account and NSDL Demat Account",';
    	postJson = postJson + '"dp" : "' +  $('#dp').val() + '",';
    	postJson = postJson + '"tradingtAccount" : "' +  $('#tradingtAccount').val() + '",';
    	postJson = postJson + '"dematAccount" : "' +  $('#dematAccount').val() + '",';
    	postJson = postJson + '"smsFacility" : "' +  $('#smsFacility').val() + '",';
    	postJson = postJson + '"fstHldrOccup" : "' +  $('#fstHldrOccup').val() + '",';
    	if($('#fstHldrOrg').val() != 'Name of your Employer/Company'){
    		postJson = postJson + '"fstHldrOrg" : "' +  $('#fstHldrOrg').val() + '",';
    	}else{
    		postJson = postJson + '"fstHldrOrg" : "",';
    	}
    	if($('#fstHldrDesig').val() != 'Designation') {
    		postJson = postJson + '"fstHldrDesig" : "' +  $('#fstHldrDesig').val() + '",';
    	}else {
    		postJson = postJson + '"fstHldrDesig" : "",';
    	}
    	
    	postJson = postJson + '"fstHldrIncome" : "' +  $('#fstHldrIncome').val() + '",';
    	postJson = postJson + '"fstHldrNet" : "' +  $('#fstHldrNet').val() + '",';
    	postJson = postJson + '"fstHldrAmt" : "' +  $('#fstHldrAmt').val() + '",';
    	postJson = postJson + '"pep" : "' +  $('#pep').is(':checked') + '",';
    	postJson = postJson + '"rpep" : "' +  $('#rpep').is(':checked') + '",';
    	postJson = postJson + '"scndHldrExist" : "' +  $('#scndHldrExist').val() + '",';
    	postJson = postJson + '"scndHldrName" : "' +  $('#scndHldrName').val() + '",';
    	postJson = postJson + '"scndHldrOccup" : "' +  $('#scndHldrOccup').val() + '",';
    	postJson = postJson + '"scndHldrOrg" : "' +  $('#scndHldrOrg').val() + '",';
    	postJson = postJson + '"scndHldrDesig" : "' +  $('#scndHldrDesig').val() + '",';
    	postJson = postJson + '"scndHldrSms" : "' +  $('#scndHldrSms').val() + '",';
    	postJson = postJson + '"scndHldrIncome" : "' +  $('#scndHldrIncome').val() + '",';
    	postJson = postJson + '"scndHldrNet" : "' +  $('#scndHldrNet').val() + '",';
    	postJson = postJson + '"scndHldrAmt" : "' +  $('#scndHldrAmt').val() + '",';
    	postJson = postJson + '"scndPep" : "' +  $('#scndPep').val() + '",';
    	postJson = postJson + '"scndRpep" : "' +  $('#scndRpep').val() + '",';
    	postJson = postJson + '"instrn1" : "' +  $('#instrn1').val() + '",';
    	postJson = postJson + '"instrn2" : "' +  $('#instrn2').val() + '",';
    	postJson = postJson + '"instrn3" : "' +  $('#instrn3').val() + '",';
    	postJson = postJson + '"instrn4" : "' +  $('#instrn4').val() + '",';
    	postJson = postJson + '"instrn5" : "' +  $('#instrn5').val() + '",';
    	postJson = postJson + '"depoPartcpnt" : "' +  $('#depoPartcpnt').val() + '",';
    	postJson = postJson + '"deponame" : "' +  $('#deponame').val() + '",';
    	postJson = postJson + '"beneficiary" : "' +  $('#beneficiary').val() + '",';
    	postJson = postJson + '"dpId" : "' +  $('#dpId').val() + '",';
    	postJson = postJson + '"docEvdnc" : "' +  $('#docEvdnc').val() + '",';
    	postJson = postJson + '"experience" : "' +  $('#experience').val() + '",';
    	postJson = postJson + '"contractNote" : "' +  $('#contractNote').val() + '",';
    	postJson = postJson + '"intrntTrading" : "' +  $('#intrntTrading').val() + '",';
    	postJson = postJson + '"alert" : "' +  $('#alert').val() + '",';
    	postJson = postJson + '"relationship" : "' +  $('#relationship').val() + '",';
    	postJson = postJson + '"otherInformation" : "' +  $('#otherInformation').val() + '",';
    	postJson = postJson + '"nomineeExist" : "' +  $('#nomineeExist').is(':checked') + '",';
    	postJson = postJson + '"nameNominee" : "' +  $('#nameNominee').val() + '",';
    	postJson = postJson + '"nomineeRelation" : "' +  $('#nomineeRelation').val() + '",';
    	postJson = postJson + '"nomineeDob" : "' +  $('#nomineeDob').val() + '",';
    	if($('#nominePan').val() != 'PAN Number'){
    		postJson = postJson + '"nominePan" : "' +  $('#nominePan').val() + '",';
    	} else {
    		postJson = postJson + '"nominePan" : "",';
    	}
    	if($('#nomineAadhar').val() != 'UID/Aadhaar Number'){
    		postJson = postJson + '"nomineAadhar" : "' +  $('#nomineAadhar').val() + '",';
    	} else {
    		postJson = postJson + '"nomineAadhar" : "",';
    	}
    	postJson = postJson + '"nomineeProof" : "' +  $('#nomineeProof').val() + '",';
    	postJson = postJson + '"nomineeAdrs1" : "' +  $('#nomineeAdrs1').val() + '",';
    	if($('#nomineeAdrs2').val()!='Address Line2') {
    		postJson = postJson + '"nomineeAdrs2" : "' +  $('#nomineeAdrs2').val() + '",';
    	} else {
    		postJson = postJson + '"nomineeAdrs2" : "",';
    	}
    	
    	postJson = postJson + '"nomineeLnd" : "' +  $('#nomineeLnd').val() + '",';
    	postJson = postJson + '"nomineePincode" : "' +  $('#nomineePincode').val() + '",';
    	postJson = postJson + '"nomCountry" : "' +  $('#nomCountry').val() + '",';
    	postJson = postJson + '"nomState" : "' +  $('#nomState').val() + '",';
    	postJson = postJson + '"nomCity" : "' +  $('#nomCity').val() + '",';
    	postJson = postJson + '"noisd" : "' +  $('#noisd').val() + '",';
    	postJson = postJson + '"nostd" : "' +  $('#nostd').val() + '",';
    	postJson = postJson + '"notelephone" : "' +  $('#notelephone').val() + '",';
    	postJson = postJson + '"nrisd" : "' +  $('#nrisd').val() + '",';
    	postJson = postJson + '"nrstd" : "' +  $('#nrstd').val() + '",';
    	postJson = postJson + '"nRtelephone" : "' +  $('#nRtelephone').val() + '",';
    	postJson = postJson + '"nfisd" : "' +  $('#nfisd').val() + '",';
    	postJson = postJson + '"nfstd" : "' +  $('#nfstd').val() + '",';
    	postJson = postJson + '"nomineeFax" : "' +  $('#nomineeFax').val() + '",';
    	postJson = postJson + '"nomMobile" : "' +  $('#nomMobile').val() + '",';
    	postJson = postJson + '"nomEmail" : "' +  $('#nomEmail').val() + '",';
    	postJson = postJson + '"minorExist" : "' +  $('#minorExist').is(':checked') + '",';
    	postJson = postJson + '"minorGuard" : "' +  $('#minorGuard').val() + '",';
    	postJson = postJson + '"mnrReltn" : "' +  $('#mnrReltn').val() + '",';
    	postJson = postJson + '"mnrDob" : "' +  $('#mnrDob').val() + '",';
    	postJson = postJson + '"mnrAdrs1" : "' +  $('#mnrAdrs1').val() + '",';
    	if($('#mnrAdrs2').val()!='Address Line2') {
    		postJson = postJson + '"mnrAdrs2" : "' +  $('#mnrAdrs2').val() + '",';
    	} else {
    		postJson = postJson + '"mnrAdrs2" : "",';
    	}
    	
    	postJson = postJson + '"mnrLnd" : "' +  $('#mnrLnd').val() + '",';
    	postJson = postJson + '"mnrCountry" : "' +  $('#mnrCountry').val() + '",';
    	postJson = postJson + '"mnrState" : "' +  $('#mnrState').val() + '",';
    	postJson = postJson + '"mnrCity" : "' +  $('#mnrCity').val() + '",';
    	postJson = postJson + '"mnrPincode" : "' +  $('#mnrPincode').val() + '",';
    	postJson = postJson + '"mnrProof" : "' +  $('#mnrProof').val() + '",';
    	if($('#mnrPan').val() != 'PAN Number'){
    		postJson = postJson + '"mnrPan" : "' +  $('#mnrPan').val() + '",';
    	} else {
    		postJson = postJson + '"mnrPan" : "",';
    	}
    	if($('#mnrAadhar').val() != 'UID/Aadhaar Number'){
    		postJson = postJson + '"mnrAadhar" : "' +  $('#mnrAadhar').val() + '",';
    	} else {
    		postJson = postJson + '"mnrAadhar" : "",';
    	}
    	
    	
    	postJson = postJson + '"moisd" : "' +  $('#moisd').val() + '",';
    	postJson = postJson + '"mostd" : "' +  $('#mostd').val() + '",';
    	postJson = postJson + '"motel" : "' +  $('#motel').val() + '",';
    	postJson = postJson + '"mrisd" : "' +  $('#mrisd').val() + '",';
    	postJson = postJson + '"mrstd" : "' +  $('#mrstd').val() + '",';
    	postJson = postJson + '"mrtel" : "' +  $('#mrtel').val() + '",';
    	postJson = postJson + '"mfisd" : "' +  $('#mfisd').val() + '",';
    	postJson = postJson + '"mfstd" : "' +  $('#mfstd').val() + '",';
    	postJson = postJson + '"minorfax" : "' +  $('#minorfax').val() + '",';
    	postJson = postJson + '"mnrMob" : "' +  $('#mnrMob').val() + '",';
    	postJson = postJson + '"mnrEmail" : "' +  $('#mnrEmail').val() + '",';
    	postJson = postJson + '"nomCityOther" : "' +  $('#nomCityOther').val() + '",';
    	postJson = postJson + '"mnrCityOther" : "' +  $('#mnrCityOther').val() + '",';
    	postJson = postJson + '"docFilePath" : "' +  $('#docFilePath').val() + '",';
    	postJson = postJson + '"usNational" : "' +  $('#usNational').is(':checked') + '",';
    	postJson = postJson + '"usResident" : "' +  $('#usResident').is(':checked') + '",';
    	postJson = postJson + '"usBorn" : "' +  $('#usBorn').is(':checked') + '",';
    	postJson = postJson + '"usAddress" : "' +  $('#usAddress').is(':checked') + '",';
    	postJson = postJson + '"usTelephone" : "' +  $('#usTelephone').is(':checked') + '",';
    	postJson = postJson + '"usStandingInstruction" : "' +  $('#usStandingInstruction').is(':checked') + '",';
    	postJson = postJson + '"usPoa" : "' +  $('#usPoa').is(':checked') + '",';
    	postJson = postJson + '"usMailAddress" : "' +  $('#usMailAddress').is(':checked') + '",';
    	postJson = postJson + '"individualTaxIdntfcnNmbr" : "' +  $('#individualTaxIdntfcnNmbr').val() + '",';
    	postJson = postJson + '"secondHldrPan" : "' +  $('#secondHldrPan').val() + '",';
    	postJson = postJson + '"secondHldrDependentRelation" : "' +  $('#secondHldrDependentRelation').val() + '",';
    	postJson = postJson + '"secondHldrDependentUsed" : "' +  $('#secondHldrDependentUsed').val() + '",';
    	postJson = postJson + '"firstHldrDependentUsed" : "' +  $('#firstHldrDependentUsed').val() + '"';
    	
    	postJson = postJson.split('undefined').join('');
    	
    	postJson = '{' + postJson + '}';
    	console.log();
    	console.log('auto save ......')
    	
    	$.ajax({
			  type: "POST",
			  url: _gc_url_ir_post_autoSave,
			  data: postJson,
			  dataType: 'json',
			  contentType:"application/json; charset=utf-8",
			  success: function(data){
				  
				  console.log(data);
				  if(FINAL_SUBMIT){
					  console.log("doing submit");
				    	$('#frmFinalFormSubmit').attr('action', _gc_url_ir_post_finalSave);
				    	$('#frmFinalFormSubmit').submit();
				  }
			  }
		});
    }
    
    finalSubmit = function(){
    	if(!validatePersonalDetails()){
    		$('#myCarousel').carousel(0);
    		return;
    	}
    	if(!validateAddressDetails()){
    		$('#myCarousel').carousel(1);
    		return;
    	}
    	if(!validateBackgroundDetails()){
    		$('#myCarousel').carousel(2);
    		return;
    	}
    	if(!validateBankDetails()){
    		$('#myCarousel').carousel(3);
    		return;
    	}
    	if(!validateAgreement()){
    		return;
    	}
    	showLoading();
    	clevertap.event.push("Account Opening Form Submitted");
    	canShowToast = false;
	
		canShowToast = true;
		FINAL_SUBMIT = true;
		makePostJson();
    	console.log('final save ......')
    	/*
    	$('#frmFinalFormSubmit').attr('action', _gc_url_ir_post_finalSave);
    	$('#frmFinalFormSubmit').submit();*/
    	
    }
    
    uploadPanCard = function(){
    	var fileSize = $('#panFilePath')[0].files[0].size;
    	if (fileSize <= MAX_FILE_SIZE){
        	uploadFileToServer(
        			$('#panFilePath')[0].files[0],
        			_gc_url_ir_fileUpload_pan,
        			function(responseData){
        				sPanFilePath = responseData;
        				if (responseData.indexOf("error")== -1) {
        					$('#panFilePathMsg').text('File Uploaded Successfully.');
            				$('#panFilePathMsg').css('color','green');
        				} else {
        					$('#panFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#panFilePathMsg').css('color','red');
        				}
        				//uploadUID();
        			}
        	);
    	} else {
    		showToast('File size greater than 2 MB.')
    	}

    }
    
    uploadUID = function(){
    	uploadFileToServer(
    			$('#uidFilePath')[0].files[0],
    			'',
    			function(responseData){
    				sUidFilePath = responseData;
    				//uploadCorsAdress();
    			}
    	);
    }
    
    uploadCorsAdress = function(){
    	var fileSize = $('#corsFilePath')[0].files[0].size;
    	if (fileSize <= MAX_FILE_SIZE){
        	uploadFileToServer(
        			$('#corsFilePath')[0].files[0],
        			_gc_url_ir_fileUpload_coresAddrs,
        			function(responseData){
        				sCorsFilePath = responseData;
        				//uploadPrmntAdress();
        				if (responseData.indexOf("error")== -1) {
            				$('#corsFilePathMsg').text('File Uploaded Successfully.');
            				$('#corsFilePathMsg').css('color','green');
        				} else {
        					$('#corsFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#corsFilePathMsg').css('color','red');
        				}
        			}
        	);
    	} else {
    		showToast('File size greater than 2 MB.')
    	}

    }
    
    uploadPrmntAdress = function(){
    	var fileSize = $('#prmntFilePath')[0].files[0].size;
    	if (fileSize <= MAX_FILE_SIZE){
        	uploadFileToServer(
        			$('#prmntFilePath')[0].files[0],
        			_gc_url_ir_fileUpload_permAdrs,
        			function(responseData){
        			    sPrmntFilePath = responseData;
        				//makePostJson();
        			    if (responseData.indexOf("error")== -1) {
            				$('#prmntFilePathMsg').text('File Uploaded Successfully.');
            				$('#prmntFilePathMsg').css('color','green');
        				} else {
        					$('#prmntFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#prmntFilePathMsg').css('color','red');
        				}
        			}
        	);
    	} else {
    		showToast('File size greater than 2 MB.')
    	}

    }
    
    
    var uploadFileToServer = function(filePath,url,callbackFunction){
		var form = new FormData();
		form.append("file", filePath);
		form.append("email",$("#email").val());
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
				showToast(response);
			}
		});
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
    	 if($('#reAccno').val() != $('#accno').val()){
     		showToast('Bank Account Number not matched.');
     		return false;
     	}
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
		checkIfNomineeIsMinor = function(DOB){
			var enteredAge = getAge(DOB.value);
			if( enteredAge < 18 ) {
				$('#minorExist').bootstrapSwitch('state', true, true);   
			}
			
		}
		displayFilePath = function(element,url){
			var displayUrlList =  url.split('/');
			var displayName =  displayUrlList[displayUrlList.length-1];
			$(element).val(displayName);
		}
		
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
					//console.log(res);
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
		//sync call
		getStateWiseCitesSync = function(stateElement,element){
			var state = $(stateElement).val();
			var form = new FormData();
            form.append("stateName", state);
			var settings = {
			"async": false,
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
				//console.log(res);
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
		
	//pan and email validdation
		isValidEmailAddress = function(emailAddress) {
		    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
		    return pattern.test(emailAddress);
		};				
			
			
			
		isValidPANNumber = function (panNo) {
			var pattern = /[A-Z|a-z]{3}(p|P|c|C|h|H|f|F|a|A|t|T|b|B|l|L|j|J|g|G){1}[A-Z|a-z][0-9]{4}[A-Za-z]$/;
			return pattern.test(panNo);
		}					
}]);