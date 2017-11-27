app.controller('UserProfile', ['$scope', function($scope) {
	$scope.page = {
			title: 'User Profile'
	};
	
	$scope.userData = {	}
	
	var currentSlideIndex =0;
    var isUserSingle = true;
    var viwedAll = false;
	setTimeout(function(){ 
		$("#displayOnlyUserName").text($("#userFirstName").val());
//		disabled the on click event on bubble 		
//     	$('#spc0').on('click', function () {$('#myCarousel').carousel(0);});
//     	$('#spc1').on('click', function () {$('#myCarousel').carousel(1);});
//     	$('#spc2').on('click', function () {$('#myCarousel').carousel(2);});
//     	$('#spc3').on('click', function () {$('#myCarousel').carousel(3);});
//     	$('#spc4').on('click', function () {$('#myCarousel').carousel(4);});
//     	$('#spc5').on('click', function () {$('#myCarousel').carousel(5);});
//     	$('#spc6').on('click', function () {$('#myCarousel').carousel(6);});
//     	$('#spc7').on('click', function () {$('#myCarousel').carousel(7);});
//     	$('#spc8').on('click', function () {$('#myCarousel').carousel(8);});
//     	$('#spc9').on('click', function () {$('#myCarousel').carousel(9);});
//     	$('#spc10').on('click', function () {$('#myCarousel').carousel(10);});
//     	$('#spc11').on('click', function () {$('#myCarousel').carousel(11);});
//     	$('#spc12').on('click', function () {$('#myCarousel').carousel(12);});
        
		
     	$("#rsSalarySavingsRate").roundSlider({
            sliderType: "min-range",
            editableTooltip: false,
            radius: 105,
            width: 16,
            value: 0,
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
     	
        $(':radio').on('ifChecked', function(event){

    		if(this.name == "Q0")
  			{
    			if(this.value == "male")
  				{
    				$("#statusSingle").attr("src", "img/singleMale.png");
    				$("#yourAge").attr("src", "img/age_male.png");
    				$("#spouseAge").attr("src", "img/age_female.png");
  				}
    			else{
    				$("#statusSingle").attr("src", "img/singleFemale.png");
    				$("#yourAge").attr("src", "img/age_female.png");
    				$("#spouseAge").attr("src", "img/age_male.png");
    			}
  			}
    		else if(this.name == "Q2")
  			{
    			if(this.value == "married")
  				{
    				isUserSingle = false;
    				$('.marriedOptions').css('display', 'inline-block');
    				$( '.marriedSlide' ).each(function( index ) {
			    	  
			    	  $(this).css("display","none");
			    	  $(this).addClass('item');
			    	  $(this).removeClass('active');
			    	});
    				$('#divTotalAssets').html('What is your combined(your + spouse) total net worth?');
  				}
    			else{
    				isUserSingle = true;
    				$('.marriedOptions').css('display', 'none');
    				$( '.marriedSlide' ).each(function( index ) {
			    	  
			    	  $(this).css("display","none");
			    	  $(this).removeClass('item');
			    	  $(this).removeClass('active');
			    	});
    				$('#divTotalAssets').html('What is your total current networth?');
    			}
    			
    			$('.item .questionBullet').each(function(index) {
		    	  if(index<9){
		    		  $(this).html("0" + (index+1));
		    	  }
		    	  else{
		    		  $(this).html(index+1);
		    	  }
    				
		    	});
  			}
        	
    		if (!$("#myCarousel").find('.active').is( ':last-child' )) 
        	{
        		$('#myCarousel').carousel("next");
        	}
         	else
      		{
      			//console.log('else');
      		}
        });
        
        
        $("#myCarousel").on('slid.bs.carousel', function () {
            //console.log($("#myCarousel").find('.active').index());
            curSlide = $("#myCarousel").find('.active');
            //console.log('this is slide ' + curSlide.index());
            
            $( '.item' ).each(function( index ) {
            	$(this).css("display","none");
            	
	    	});
            $( '.item.active' ).each(function( index ) {
            	$(this).css("display","block");
	    	});
            
            
            currentSlideIndex = curSlide.index();
            //console.log(currentSlideIndex);
            
            $('.item .itemVissitedFlag').each(function(index) {
            	if(isUserSingle && curSlide.index() > 3){
            		if(index == (curSlide.index()-2)){
                		$(this).val('true');
                	}
            	}
            	else if(index == curSlide.index()){
            		$(this).val('true');
            	}
	    	});
            
            //console.log('on slid.bs.carousel >>');
            

            if(curSlide.is( ':first-child' )) {
               $('#btnPrev').hide();
            } else {
               $('#btnPrev').show();   
            }
            
            
        	if (curSlide.is( ':last-child' )) {
            	$('#btnNext').hide();
            	$('#btnSubmit').show();
            	viwedAll = true;
            } else {
            	$('#btnNext').show();
            	$('#btnSubmit').hide();
            }
            
            
            
            $('.circle').removeClass('active');
            $('.circle').removeClass('done');
            
           
           
           	$('.item .itemVissitedFlag').each(function(index) {
	           	if($('input[name=Q'+index+']').attr('type')=='radio')
	           	{
	           		if($('input[name=Q'+index+']:checked').val() == undefined)
	        			{
	        			}
	    	    		else
	        			{
	    	    			if(isUserSingle && index > 3){
	            				$('#spc'+(index-2)).addClass('done');
	                        }
	                        else{
	                        	$('#spc'+index).addClass('done');
	                        }
	        			}
	           	}
	           	else
	           	{
            		if($(this).val() == 'true'){
            			
            			if(isUserSingle && index > 3){
            				$('#spc'+(index-2)).addClass('done');
                        }
                        else{
                        	$('#spc'+index).addClass('done');
                        }
            		}
	           	}
	    	});

           	if(curSlide.index() <=3){
           		$('#spc'+curSlide.index()).addClass('active');
           	}
           	
            if(isUserSingle && curSlide.index() > 3){
            	$('#spc'+(curSlide.index()-2)).addClass('active');
            	var i = 3,j = curSlide.index()-2;
            	while(i<j){
            		$('#spc'+i).addClass('done');
            		i++;
            	}
            }
            else{
            	$('#spc'+curSlide.index()).addClass('active');
            }
            
            /*
            if(isUserSingle && currentSlideIndex==7){
            	$('#spc4').addClass('done');
            }*/
            
            //$('.circle')[curSlide.index()*2].addClass('active');
            
            $('.circle').each(function () {
           	    //console.log($(this).index());
           	    
			 });
            
            if(viwedAll){
            	if((isUserSingle && curSlide.index())>3 ){
            		$('#spc'+curSlide.index()-2).addClass('active');
            	}else{
            		$('#spc'+curSlide.index()).addClass('active');
            	}
            	
            	var i = 0,j = 12;
            	if(isUserSingle){
            		i=0;j=9;
            	} else {
            		i=0;j=11;
            	}
            	
            	while(i<=j){
            		$('#spc'+i).addClass('done');
            		i++;
            	}
            }
            
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
	   	
	   	$(':radio').on('ifChecked', function(event){

    		if(this.name == "Q0")
  			{
    			if(this.value == "male")
  				{
    				$("#statusSingle").attr("src", "img/singleMale.png");
    				$("#yourAge").attr("src", "img/age_male.png");
    				$("#spouseAge").attr("src", "img/age_female.png");
  				}
    			else{
    				$("#statusSingle").attr("src", "img/singleFemale.png");
    				$("#yourAge").attr("src", "img/age_female.png");
    				$("#spouseAge").attr("src", "img/age_male.png");
    			}
  			}
    		else if(this.name == "Q2")
  			{
    			if(this.value == "married")
  				{
    				isUserSingle = false;
    				$('.marriedOptions').css('display', 'inline-block');
    				$( '.marriedSlide' ).each(function( index ) {
			    	  
			    	  $(this).css("display","none");
			    	  $(this).addClass('item');
			    	  $(this).removeClass('active');
			    	});
    				$('#divTotalAssets').html('What is your combined(your + spouse) total net worth?');
    				$('#divMonthlySavings').html('What is your combined(your + spouse) monthly savings? (Post loan EMIs & recuring monthly expenditure)');
    				$('#divOutstandingLoan').html('What are your combined(your + spouse) current outstanding loans ?');
    				
  				}
    			else{
    				isUserSingle = true;
    				$('.marriedOptions').css('display', 'none');
    				$( '.marriedSlide' ).each(function( index ) {
			    	  
			    	  $(this).css("display","none");
			    	  $(this).removeClass('item');
			    	  $(this).removeClass('active');
			    	});
    				$('#divTotalAssets').html('What is your total current networth?');
    				$('#divMonthlySavings').html('What is your monthly savings? (Post loan EMIs & recuring monthly expenditure)');
    				$('#divOutstandingLoan').html('What are your current outstanding loans ?');
    				sliderSavings.update({max: (sliderSalary.result.from)});
    			}
    			
    			$('.item .questionBullet').each(function(index) {
		    	  if(index<9){
		    		  $(this).html("0" + (index+1));
		    	  }
		    	  else{
		    		  $(this).html(index+1);
		    	  }
    				
		    	});
  			}
        	
    		if (!$("#myCarousel").find('.active').is( ':last-child' )) 
        	{
        		$('#myCarousel').carousel("next");
        	}
         	else
      		{
      			//console.log('else');
      		}
        });
   	
	   	$('#btnPrev').on('click', function(event){
	   		gotoPrev();
	    });
   	
	   	$('#btnNext').on('click', function(event){
	   		gotoNext();
	    });
	   	
	   	function gotoNext()
	   	{
	   		$('#myCarousel').carousel("next");
	   	}
	   	
	   	function gotoPrev()
	   	{
	   		$('#myCarousel').carousel("prev");
	   	}
   	
   		$('#btnSubmit').on('click', function(event){
   			showLoading();
	    	unansweredQuestions = "";
	    	
	    	/*$('.item .itemVissitedFlag').each(function(index) {
	    		checkIndex=index;
	    		
	           	if($('input[name=Q'+index+']').attr('type')=='radio')
	           	{
	           		if($('input[name=Q'+index+']:checked').val() == undefined)
        			{
	           			if(unansweredQuestions == "")
		   				{
		    				unansweredQuestions = "#" + (index+1) 
		   				}
		    			else
		   				{
		    				unansweredQuestions = unansweredQuestions + ", #" + (index+1);
		   				}
        			}
	           	}
           		else if ($(this).val() == 'false'){
           			if(unansweredQuestions == "")
	   				{
	    				unansweredQuestions = "#" + (index+1) 
	   				}
	    			else
	   				{
	    				unansweredQuestions = unansweredQuestions + ", #" + (index+1);
	   				}
           		}
	    	});
	    	
	    	*/
	    	
	    	unansweredQuestions = "";
    		if($('input[name=Q0]:checked').val() == undefined)
			{
       			unansweredQuestions = unansweredQuestions + "<br> #1 What is your gender? "
			}
    		
    		if($('input[name=Q2]:checked').val() == undefined)
			{
    			unansweredQuestions = unansweredQuestions + "<br> #3 What is your relationship status? "
			}
    		if($('input[name=Q11]:checked').val() == undefined)
			{
    			unansweredQuestions = unansweredQuestions + "<br> #12 What kind of returns & risk are you willing to tolerate? "
			}
	    	
	    	
	    	if(unansweredQuestions=="")
	   		{
	    		makePostJson();
		    	//window.location.href = 'financialPlannerOutput.html';
	   		}
	    	else
	    	{
	    		hideLoading();
	    		$('.modal-title')[0].innerHTML = "Error"
	   		    $('.modal-body')[0].innerHTML = "Please answer the following questions <br> " +unansweredQuestions;
	   	    	$('#modalShowError').modal('show');
	    	}
	    });
   		
   		makePostJson = function(){
   			var postJson = ""
   			var tempJson = ""
   			
   			var n = $('select.lgOptions').length;
   			var arrloan_description = $('select.lgOptions');
   			var arrOther = $('.lgOther');
   			var arrFrequency = $("select.lgFrequency");
   			var arrYearOfRealization = $(".lgYear");
   			var arrLoanYesNo = $(".lgLoan");
   			var arrAmount = $(".lgAmount");

   			for(i=0; i < n; i++) {
   			   // use .eq() within a jQuery object to navigate it by Index
   				//if(arrloan_description.eq(i).val() == ""){
   				//	goalDescriptionId = 1
   				//}
   				//else if(arrloan_description.eq(i).val() == ""){
   				//	
   				//}
   				if(arrloan_description.eq(i).val()=='select'|| arrloan_description.eq(i).val()=='Select' || arrloan_description.eq(i).val()==-1 || arrloan_description.eq(i).val()=="" || arrloan_description.eq(i).find('option:selected').text()=="" || arrYearOfRealization.eq(i).val()=="" || arrFrequency.eq(i).val()=="" || arrAmount.eq(i).val()==""){
   					
   				}
   				else{
   					if(!tempJson==''){
   	   					tempJson =tempJson+','
   	   				}
   					
   	   				
   	   				tempJson =tempJson+'{';
   	   				tempJson=tempJson + '"goalDescriptionId":' + arrloan_description.eq(i).val() + ',';
   	   				tempJson=tempJson + '"goalDescription":"' + arrloan_description.eq(i).find('option:selected').text()+'",';   			   
   	   				tempJson=tempJson + '"yearofRealization":'+arrYearOfRealization.eq(i).val()+',';   			   
   	   				tempJson=tempJson + '"loanYesNo":"'+(arrLoanYesNo.eq(i).is(':checked')?'Yes': 'No')+'",';   			   
   	   				tempJson=tempJson + '"frequency":'+(arrFrequency.eq(i).val().replace(/,/g , '')=='select'? 0:  (arrFrequency.eq(i).val().replace(/,/g , '')))+',' ;  			   
   	   				tempJson=tempJson + '"estimatedAmount":'+arrAmount.eq(i).val().replace(/,/g , '')+',';		   
   	   				tempJson=tempJson + '"age": 0';
   	   				tempJson =tempJson+'}';	
   				}
   			}

   			postJson ='"lifeGoals":['+tempJson+'],';
   			tempJson='';
   			
   			
   			n = $('select.clLoanDescription').length;
   			var arrLoanDescription = $('.clLoanDescription :selected');
   			var arrAmount = $(".clAmount");
   			var arrEMI = $(".clEMI");
   			var arrFinalYear = $(".clFinalYear");

   			for(i=0; i < n; i++) {
   			   // use .eq() within a jQuery object to navigate it by Index
   				if(arrEMI.eq(i).val()=="" || arrAmount.eq(i).val()=="" || arrFinalYear.eq(i)=="")
				{
				
				}
   				else
				{
   					if(!tempJson==''){
   	   					tempJson =tempJson+','
   	   				}
   					tempJson =tempJson+'{';
   	   				tempJson=tempJson + '"loanDescription":"'+arrLoanDescription.eq(i).text()+'",';   			   
   	   				tempJson=tempJson + '"emi":'+arrEMI.eq(i).val().replace(/,/g , '')+',';   			     			   
   	   				tempJson=tempJson + '"loanAmount":'+arrAmount.eq(i).val().replace(/,/g , '')+',';   			   
   	   				tempJson=tempJson + '"finalYearofPayment":'+arrFinalYear.eq(i).val()+''; 
   	   				tempJson =tempJson+'}';	
				}
   			}
   			postJson =postJson + '"outstandingLoans":['+tempJson+'],';
   			tempJson='';
   			
   			
   			
   			
   			n = $('.liName').length;
   			var arrliName = $('.liName');
   			var arrliCover = $(".liCover");
   			var arrliMonthlyPremium = $(".liMonthlyPremium");
   			var arrliFinalYear = $(".liFinalYear");

   			for(i=0; i < n; i++) {
   			   // use .eq() within a jQuery object to navigate it by Index
   				if(arrliName.eq(i).val()=="" || arrliCover.eq(i).val()=="" || arrliMonthlyPremium.eq(i).val()=="" || arrliFinalYear.eq(i).val()==""){
   					
   				}
   				else{
   					if(!tempJson==''){
   	   					tempJson =tempJson+','
   	   				}
   					
   					tempJson =tempJson+'{';
   	   				tempJson=tempJson + '"lifeInsuranceDescription":"'+arrliName.eq(i).val()+'",';   			   
   	   				tempJson=tempJson + '"insuranceCover":'+arrliCover.eq(i).val().replace(/,/g , '')+',';   			     			   
   	   				tempJson=tempJson + '"annualPremium":'+arrliMonthlyPremium.eq(i).val().replace(/,/g , '')+',';   			   
   	   				tempJson=tempJson + '"finalYearofPayment":'+arrliFinalYear.eq(i).val().replace(/,/g , '')+''; 
   	   				tempJson =tempJson+'}';
   				}

   			}
   			postJson =postJson + '"lifeInsuranceCover":['+tempJson+'],';
   			tempJson='';
   			

   			
   			n = $('.hiName').length;
   			var arrhiName = $('.hiName');
   			var arrhiCover = $(".hiCover");
   			var arrhiMonthlyPremium = $(".hiMonthlyPremium");

   			for(i=0; i < n; i++) {
   			   // use .eq() within a jQuery object to navigate it by Index
   				if(arrhiName.eq(i).val()=="" || arrhiCover.eq(i).val()=="" || arrhiMonthlyPremium.eq(i).val()=="" ){

   				}
   				else{
   					if(!tempJson==''){
   	   					tempJson =tempJson+','
   	   				}
   					tempJson =tempJson+'{';
   	   				tempJson=tempJson + '"healthInsuranceDescription":"'+arrhiName.eq(i).val()+'",';   			   
   	   				tempJson=tempJson + '"insuranceCover":'+arrhiCover.eq(i).val().replace(/,/g , '')+',';   			     			   
   	   				tempJson=tempJson + '"annualPremium":'+arrhiMonthlyPremium.eq(i).val().replace(/,/g , '')+'';
   	   				tempJson =tempJson+'}';   	   				
   				}
   			}
   			postJson =postJson + '"healthInsuranceCover":['+tempJson+'],';
   			tempJson='';
   			
	   			if($('#totalAssets_1').val()!==""){
	   				tempJson = tempJson + '{"assetId" : 1, "assetDescription" : "Cash, Bank Deposits & Liquid Debt Mutual Funds", "assetAmount" : '+$('#totalAssets_1').val().replace(/,/g, '')+'}';
	   			}
	   			
	   			if($('#totalAssets_2').val()!==""){
	   				if(tempJson!==''){tempJson=tempJson+','}
	   				tempJson = tempJson + '{"assetId" : 2, "assetDescription" : "Fixed Income(Provident Fund, Debt Mutual Funds, Bonds, KVP, NSC, etc..)", "assetAmount" : '+$('#totalAssets_2').val().replace(/,/g, '')+'}';
	   			}
	   			
	   			if($('#totalAssets_3').val()){
	   				if(tempJson!==''){tempJson=tempJson+','}
	   				tempJson = tempJson + '{"assetId" : 3, "assetDescription" : "Gold (ETF, Physical)", "assetAmount" : '+$('#totalAssets_3').val().replace(/,/g, '')+'}';	
	   			}
	   			
	   			if($('#totalAssets_4').val()){
	   				if(tempJson!==''){tempJson=tempJson+','}
	   				tempJson = tempJson + '{"assetId" : 4, "assetDescription" : "Equity (Mutual Funds, Shares)", "assetAmount" : '+$('#totalAssets_4').val().replace(/,/g, '')+'}';	
	   			}
	   			
	   			if($('#totalAssets_5').val()){
	   				if(tempJson!==''){tempJson=tempJson+','}
	   				tempJson = tempJson + '{"assetId" : 5, "assetDescription" : "Total Real Estate Value", "assetAmount" : '+$('#totalAssets_5').val().replace(/,/g, '')+'}';
	   			}
   			
   			
   			postJson =postJson + '"financialAssetList":['+tempJson+'],';
   			tempJson='';
   			
   			postJson = postJson + '"gender":"'+$('input[name=Q0]:checked').val()+'",';
   			postJson = postJson + '"userAge":'+sliderAge.old_from+',';

   			postJson = postJson + '"relationStatus":"'+$('input[name=Q2]:checked').val()+'",';
   			postJson = postJson + '"userMonthlyTakehomeSalary":'+sliderSalary.old_from+',';
   			postJson = postJson + '"spouseAge":'+sliderAgeSpouse.old_from+',';
   			postJson = postJson + '"spouseMonthlyTakehomeSalary":'+sliderSalarySpouse.old_from+',';
   			postJson = postJson + '"monthlySavings":'+$('#range_savings').val()+',';
   			postJson = postJson + '"savingsRate":'+$('#spanSavingrate').text().replace('%' , '')+',';
   			postJson = postJson + '"initialTotalFinancialAsset":'+$('#tdTotalAssets').text().replace(/,/g , '')+',';
   			postJson = postJson + '"riskGroupId":'+$("input[name='Q11']:checked").val()+',';
   			postJson = postJson + '"responseid":0';
   			postJson = '{' + postJson + '}';
   			
   			console.log(postJson);
   			localStorage.setItem("userDataInput", postJson);
   			localStorage.setItem("lifeGoalsTable", $('#lifegoals').html());
   			
   			console.log(1)
   			$.ajax({
  			  type: "POST",
  			  url: _gc_up_post_submit,
  			  data: postJson,
  			  dataType: 'json',
  			  contentType:"application/json; charset=utf-8",
  			  success: function(data){
  				  console.log(2);
  				  //localStorage.setItem("userDataOutput", JSON.stringify(data));
  				  //window.location.href = 'financialPlannerOutput.html';
  				  gotoNextPage();
  			  },
  			  fail: function(err){
  				  console.log(3);
  				  console.log(err);
  				  hideLoading();
  			  },
  			  error: function(jqXHR, textError, errorThrown){
  				  console.log(4);
  				  console.log(jqXHR);
  				  console.log(textError);
  				  console.log(errorThrown);
  				  hideLoading();
  			  }
  			});
   			
   			function gotoNextPage(){
   				$.ajax({
   	  			  type: "GET",
   	  			  url: _gc_url_fpo_get_assetExpenditureChart,
   	  			  data: null,
   	  			  dataType: 'json',
   	  			  contentType:"application/json; charset=utf-8",
   	  			  success: function(data){
   	  				  try{
   	  					data = JSON.stringify(data);
   	  				  }catch(err){}
   	  				  localStorage.setItem("userDataOutput", data);
	   	  				var url = window.location.href;
	   		    		var urlpart =  url.split("src=");
	   		    		var src = urlpart[1];
	   		    		if(typeof src === "undefined"){
	   		    			urlpart =  url.split("viewPlanner=");
	   		    			src = urlpart[1];
	   		    			if(typeof src === "undefined"){
	   		    				window.location.href = 'financialPlannerOutputFD.jsp';
	   		    			}
	   		    			if(src.startsWith("true")){
	   		    				window.location.href = 'financialPlannerOutputFD.jsp?faces-redirect=true&viewPlanner=true';
	   		    			}	   		    				   		    			
	   		    		} else {
	   		    			if (src.startsWith("ps")){
		   		    			window.location.href = 'financialPlannerOutputFD.jsp?src=ps';
		   		    		} else if (src.startsWith("upw")){
		   		    			window.location.href = 'financialPlannerOutputFD.jsp?src=upw';
		   		    		} else if (src.startsWith("fpw")){
		   		    			window.location.href = 'financialPlannerOutputFD.jsp?src=fpw';
		   		    		} else {
		   		    			window.location.href = 'financialPlannerOutputFD.jsp';
		   		    		}
	   		    		}
   	  			  }
   	  			});
   			}
   		}
   		
   		$('#divContent').css("display","block");
   		$('#divLoading').css("display","none");
        
	}, 1000);
	
	function initData(){
		$("#displayOnlyUserName").text($("#userFirstName").val());
		$.ajax({
			type: "GET",
			url: _gc_url_up_get_userDetails,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(data){
				try{
					try{
						data = JSON.parse(data);
					}catch(e){
						console.log(e);
					}
					
					prefillData(data)
				}
				catch(e){
					$('#divContent').css("display","block");
					$('#divLoading').css("display","none");
				}
				/*$('.lgYear').datetimepicker({
					viewMode: 'years',
					format: 'YYYY',
					minDate: moment()
				});
				$('.clFinalYear').datetimepicker({
			    	viewMode: 'years',
			    	format: 'YYYY',
			    	minDate: moment()
			    });
				$('.liFinalYear').datetimepicker({
			    	viewMode: 'years',
			    	format: 'YYYY',
			    	minDate: moment()
			    	//defaultDate : "",
			    });*/
				$scope.userData = data;
				console.log('hello');
				console.log($scope.userData);
				$scope.$apply();
				$("select").selectBoxIt();
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
				$('.lgYear').datetimepicker({
					viewMode: 'years',
					format: 'YYYY',
					minDate: moment()
				});
				$('.clFinalYear').datetimepicker({
			    	viewMode: 'years',
			    	format: 'YYYY',
			    	minDate: moment()
			    });
				$('.liFinalYear').datetimepicker({
			    	viewMode: 'years',
			    	format: 'YYYY',
			    	minDate: moment()
			    	//defaultDate : "",
			    });
				//fix for select date for datepicker 

				$('.clFinalYear').each(function( index ) {
				  $(this).data('DateTimePicker').date("'"+$(this).attr('value') +"'");
				});

				$('.liFinalYear').each(function( index ) {
				  $(this).data('DateTimePicker').date("'"+$(this).attr('value') +"'");
				});

				$('.lgYear').each(function( index ) {
				  $(this).data('DateTimePicker').date("'"+$(this).attr('value') +"'");
				});
				$(".numericOnly").keypress(function (e) {
				    if (String.fromCharCode(e.keyCode).match(/[^0-9]/g)) return false;
				});
	
		        $('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
		        //$('.irs-grid-text').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
		        //$('.irs-single').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
		        $(".totalAssets").on('change keyup paste', function() {
			        var total = 0;
			        $( '.totalAssets' ).each(function( index ) {
		
			        	thisVal = $(this).val().replace(/,/g, '');
			        	
			        	if(thisVal != '')
		        		{
				        	thisVal = parseInt(thisVal);
			        		total = total+ thisVal;
		        		}
			      	});
			        
			        $('#tdTotalAssets').html(total.toLocaleString());
			    });
		        $(".alphaWithSpace").keypress(function (event) {
					var regex = new RegExp("^[a-zA-Z\\-\\s]+$");
				    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				    if (!regex.test(key)) {
				       event.preventDefault();
				       return false;
				    }
				});
				
				$("[name='my-checkbox']").bootstrapSwitch({
					'size':'mini',
					'onText':'Yes',
					'offText':'No'
				});
        addEventToLoanDropDown();
			}
		});
	}
	
	function prefillData(data){
		console.log(data);
		try{
			$('input:radio[name="Q0"][value="'+data.gender+'"]').prop('checked',true);
			sliderAge.update({from:data.userAge});
			$('input:radio[name="Q2"][value="'+data.relationStatus+'"]').prop('checked',true);
			if(data.relationStatus == "married")
			{
				isUserSingle = false;
				$('.marriedOptions').css('display', 'inline-block');
				$( '.marriedSlide' ).each(function( index ) {
		    	  
		    	  $(this).css("display","none");
		    	  $(this).addClass('item');
		    	  $(this).removeClass('active');
		    	});
				$('#divTotalAssets').html('What is your combined(your + spouse) total net worth?');
			}
			else{
				isUserSingle = true;
				$('.marriedOptions').css('display', 'none');
				$( '.marriedSlide' ).each(function( index ) {
		    	  
		    	  $(this).css("display","none");
		    	  $(this).removeClass('item');
		    	  $(this).removeClass('active');
		    	});
				$('#divTotalAssets').html('What is your total current networth?');
			}
			$('.item .questionBullet').each(function(index) {
		    	  if(index<9){
		    		  $(this).html("0" + (index+1));
		    	  }
		    	  else{
		    		  $(this).html(index+1);
		    	  }
  				
		    	});
			sliderSalary.update({from:data.userMonthlyTakehomeSalary,max:500000});
			sliderAgeSpouse.update({from:data.spouseAge});
			sliderSalarySpouse.update({from:data.spouseMonthlyTakehomeSalary,max:500000});
			var totalSalary = data.spouseMonthlyTakehomeSalary + data.userMonthlyTakehomeSalary;
			sliderSavings.update({from:data.monthlySavings,max:totalSalary});
			$('#tdTotalAssets').text(data.initialTotalFinancialAsset);
			$('input:radio[name="Q11"][value="'+data.riskGroupId+'"]').prop('checked',true);
			$('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'});
			

		}catch(e){
			console.log(e);
		}
		
		
		addEventToLoanDropDown();
	}
	initData();
	displayUserName = function (){
    	$("#displayOnlyUserName").text($("#userFirstName").val());
    }
	displayUserName();
}]);