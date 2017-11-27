app.controller('UserProfile', ['$scope', '$compile', function($scope,$compile) {
	$scope.page = {
			title: 'User Profile'
	};
	
  $scope.userFpData = {
    gender : 'male',
    relationStatus : 'married',
    userAge : 0,
    spouseAge : 0,
    userMonthlyTakehomeSalary : 0,
    spouseMonthlyTakehomeSalary : 0,
    savingsRate : 0,
    monthlySavings : 0,
    outstandingLoans : [],
    financialAssetList : [],
    initialTotalFinancialAsset : 0,
    lifeInsuranceCover : [],
    healthInsuranceCover : [],
    lifeGoals : [],
    riskGroupId : 0
  };

  $scope.userCombinedExpense;
  $scope.loanPageVisited = true;
  $scope.goalPageVisited = true;
  $scope.insurancePageVisited = true;

  $scope.saveFpAge = function(){
    if($scope.userFpData.relationStatus == 'married'){
      if($scope.userFpData.gender == 'male'){
        $scope.userFpData.userAge = $( "#slider-range-min-age" ).slider('value');
        $scope.userFpData.spouseAge = $( "#slider-range-min-age2" ).slider('value');                    
      }
      else if($scope.userFpData.gender == 'female'){
        $scope.userFpData.userAge = $( "#slider-range-min-age3" ).slider('value');
        $scope.userFpData.spouseAge = $( "#slider-range-min-age4" ).slider('value');                    
      }
    }
    if($scope.userFpData.relationStatus == 'single'){
      if($scope.userFpData.gender == 'male'){
        $scope.userFpData.userAge = $( "#slider-range-min-ageMale" ).slider('value');
      }else if($scope.userFpData.gender == 'female'){
        $scope.userFpData.userAge = $( "#slider-range-min-ageFemale" ).slider('value');
      }
      $scope.userFpData.spouseAge = 0;
    }    
  };

  $scope.saveFpMonthlyIncome = function(){
    if($scope.userFpData.relationStatus == 'married'){
      $scope.userFpData.userMonthlyTakehomeSalary = $( "#slider-range-min-income" ).slider('value');
      $scope.userFpData.spouseMonthlyTakehomeSalary = $( "#slider-range-min-income2" ).slider('value');
    }
    if($scope.userFpData.relationStatus == 'single'){
      $scope.userFpData.userMonthlyTakehomeSalary = $( "#slider-range-min-income1" ).slider('value');
      $scope.userFpData.spouseMonthlyTakehomeSalary = 0;
    }
    $scope.userFpData.savingsRate = 0;
  }

  $scope.calculateExpense = function(){
    var combinedSalary = 0;
    $scope.userFpData.monthlySavings =  $( "#slider-range-min-save-combine" ).slider('value');
    if($scope.userFpData.relationStatus == 'married'){
      combinedSalary = $scope.userFpData.userMonthlyTakehomeSalary + $scope.userFpData.spouseMonthlyTakehomeSalary;
    }
    if($scope.userFpData.relationStatus == 'single'){
      combinedSalary = $scope.userFpData.userMonthlyTakehomeSalary;
    }    
    $scope.userFpData.savingsRate = parseFloat(Number($scope.userFpData.monthlySavings / combinedSalary * 100).toFixed(2));
    if(isNaN($scope.userFpData.savingsRate)){
    	$scope.userFpData.savingsRate = 0;
    }
    $scope.userCombinedExpense = Number(combinedSalary - $scope.userFpData.monthlySavings);
  }
  //Loans List Pre-populated
  $scope.loans = [
      {id: 'House', name: 'House'},
      {id: 'Education', name: 'Education'},
      {id: 'Vehicle', name: 'Vehicle'},
      {id: 'Personal', name: 'Personal'},
      {id: 'Other', name: 'Other'}
    ];
  //Years List populated from current Year to next 20 years
  $scope.yearsList =  [];

  var currentYear = new Date().getFullYear();
  $scope.yearStart = currentYear;
  var maxYearCount = 30;
  for(var i=currentYear;i <= currentYear+maxYearCount; i++){
    $scope.yearsList.push(i);
  }
  //Loans Add Row
  $scope.addNewLoanRow = function(){
	var maxLoanEmi = $scope.userCombinedExpense;
    var myDiv= document.getElementsByClassName("append-div");
    var wrappedMyDiv = angular.element(myDiv);
    var randNo = Math.floor((Math.random() * 100000) + 1);
    wrappedMyDiv.append($compile(
      '<div class="financial-loan-row fp-loan-main-row" id="row-id-'+randNo+'" >'+
      '<div class="financial-loan-c1"><div class="loan_mobile">Choose Loan Type </div>'+      
      '<select id="loan-type-'+randNo+'" ng-model="loanType'+randNo+'" ng-init="loanType'+randNo+' = \'Other\'"><option ng-repeat="loan in loans" value="{{loan.id}}">{{loan.name}}</option></select></div>'+
      '<div class="financial-loan-c2"><div class="loan_mobile">Loan Amount </div>'+
      '<div class="age1"><div id="sliderprice-range-loan-total-'+randNo+'" class="loan_price_bar" ></div> <i class="fa fa-inr"></i>'+
      '<input type="text" id="price-'+randNo+'" readonly > </div> </div>'+
      '<div class="financial-loan-c2"><div class="loan_mobile">Monthly  Payment (EMI)</div><div class="age1">'+
      '<div id="sliderprice-rangeMP-'+randNo+'" class="loan_price_bar" ></div> <i class="fa fa-inr"></i>'+
      '<input type="text" id="priceMP-'+randNo+'" readonly > </div></div>'+
      '<div class="financial-loan-c4"> <div class="loan_mobile">Final year of payment</div>'+    
      '<select id="final-payment-year-'+randNo+'"  ng-model="finalPaymentYear'+randNo+'" ng-init="finalPaymentYear'+randNo+' = \''+$scope.yearStart+'\'"><option ng-repeat="x in yearsList">{{x}}</option></select></div>'+
      '<div class="financial-loan-c5"> <a href="javascript:void(0)" class="delrow" alt="'+randNo+'">X</a> </div></div>'
      )($scope));
    //Monthly Payment Slider
    $( "#sliderprice-rangeMP-"+randNo ).slider({
      range: "min",
      value:0,//25000,
      min:0,//1000,
      max:maxLoanEmi,
      slide: function( event, ui ) {
        $( "#priceMP-"+randNo  ).val( format(ui.value) );
      }
    });
    $( "#priceMP-"+randNo  ).val( format($("#sliderprice-rangeMP-"+randNo).slider( "value" )));
    //Total Loan Amount Slider
    $( "#sliderprice-range-loan-total-"+randNo ).slider({
      range: "min",
      value:0,//200000,
      min:0,
      max:10000000,
      step:1000,
      slide: function( event, ui ) {
        $( "#price-"+randNo ).val(format(ui.value));
        //Set Max Range based on loan amount value
        $( "#sliderprice-rangeMP-" +randNo).slider("option","max",ui.value);
      }
    });
    $( "#price-"+randNo  ).val( format( $( "#sliderprice-range-loan-total-"+randNo ).slider( "value" ) ));
    
  }

  $scope.saveFpLoan = function(){
  	$scope.loanPageVisited = false;
    $scope.userFpData.outstandingLoans = [];
  	$(".fp-loan-main-row").each(function(index,element){
      //LoanType
      var loanTypeDescription = $(this).find('select[id^=loan-type-]').map(function(){return this.value}).get();
      //Last Year of Payment
      var loanPaymentYear = $(this).find('select[id^=final-payment-year-]').map(function(){return this.value}).get();      
      $scope.userFpData.outstandingLoans[index] = {
	        loanDescription : loanTypeDescription[0] ,
	        emi : '',
	        loanAmount : '',
	        finalYearofPayment : parseInt(loanPaymentYear[0]) 
		};    	  
    });    

  	//EMI
    $(".fp-loan-main-row[id^=row-id-]").find('.loan_price_bar[id^=sliderprice-rangeMP]').each(function(index,element){ 
    	$scope.userFpData.outstandingLoans[index].emi = $(this).slider('value');    	
    });
    
    //Loan Amount
    $(".fp-loan-main-row[id^=row-id-]").find('.loan_price_bar[id^=sliderprice-range-loan-total]').each(function(index,element){ 
    	$scope.userFpData.outstandingLoans[index].loanAmount = $(this).slider('value');    	
    });
    // console.log('Save FP Loans : '+$scope.userFpData.outstandingLoans.length);
  }
  
  $scope.saveFpNetworth = function(){
    //Re-Initializing AssetList
    $scope.userFpData.financialAssetList = [];

    var networthDescription = $('.questionnaire-sec').find('.networthValue').map(function(){
                      if($(this).is('div'))
                        return this.outerText;
                      else if($(this).is('textarea'))
                        return this.value;
                    }).get();
    
    for (var j = 0; j < networthDescription.length; j++){
      //Assigning Default Asset Description
      if(networthDescription[j] == undefined || networthDescription[j] == ''){
          networthDescription[j] = 'Other Asset '+j;
      }
      $scope.userFpData.financialAssetList[j] = {
          assetId : j,
          assetDescription : networthDescription[j],
          assetAmount : ''
      };      
    }

    var total = 0;
    $(".questionnaire-sec").find('[id^=sliderprice-range-networth]').each(function(index,element){ 
         $scope.userFpData.financialAssetList[index].assetAmount = $(this).slider('value');
         total += $(this).slider('value');     
    });

    $scope.userFpData.initialTotalFinancialAsset = total;
    
    //Remove appended ID to avoid duplicates
    $('.append-div-networth').find("[id^='row-id-networth-']").remove();
  }

  $scope.saveFpInsurance = function(){
    //Re-Initialize
    $scope.userFpData.lifeInsuranceCover = [];
    $scope.userFpData.healthInsuranceCover = [];
    $scope.insurancePageVisited = false;
    //Insurance Name
    var insuranceNameLife = $('.insuranceInfolife').map(function(){return this.value}).get();
    // console.log(insuranceNameLife);
    for (var j = 0; j < insuranceNameLife.length; j++){
      //Assigning Default Asset Description
      if(insuranceNameLife[j] == undefined || insuranceNameLife[j] == ''){
          insuranceNameLife[j] = 'Life Insurance '+j;
      }
      $scope.userFpData.lifeInsuranceCover[j] = {
          lifeInsuranceDescription : insuranceNameLife[j],
          insuranceCover : '',
          annualPremium : ''
      };      
    }

    var insuranceNameHealth = $('.insuranceInfohealth').map(function(){return this.value}).get();
    // console.log(insuranceNameHealth);
    for (var j = 0; j < insuranceNameHealth.length; j++){
      //Assigning Default Asset Description
      if(insuranceNameHealth[j] == undefined || insuranceNameHealth[j] == ''){
          insuranceNameHealth[j] = 'Health Insurance '+j;
      }
      $scope.userFpData.healthInsuranceCover[j] = {
          healthInsuranceDescription : insuranceNameHealth[j],
          insuranceCover : '',
          annualPremium : ''
      };  
    }
    //Insurance Cover
    $('[id^=sliderprice-range-cover-life]').each(function(index,element){ 
        $scope.userFpData.lifeInsuranceCover[index].insuranceCover = $(this).slider('value'); 
    });
    $('[id^=sliderprice-range-cover-health]').each(function(index,element){ 
        $scope.userFpData.healthInsuranceCover[index].insuranceCover = $(this).slider('value');   
    });
    //Annual premium
    $('[id^=sliderprice-range-premium-life]').each(function(index,element){ 
        $scope.userFpData.lifeInsuranceCover[index].annualPremium = $(this).slider('value'); 
    });
    $('[id^=sliderprice-range-premium-health]').each(function(index,element){ 
        $scope.userFpData.healthInsuranceCover[index].annualPremium = $(this).slider('value');   
    });

    //Remove appended ID to avoid duplicates
    $('.append-div-life').find("[id^='row-id-insurance-life-']").remove();
    $('.append-div-health').find("[id^='row-id-insurance-health-']").remove();
  }

  //Loans List Pre-populated
  $scope.lifeGoalsList = [
      {id: '1', name: 'Self Marriage'},
      {id: '8', name: 'Self Higher Study'},
      {id: '3', name: 'Two Wheeler'},
      {id: '4', name: 'Car'},
      {id: '5', name: 'House'},
      {id: '6', name: 'Domestic Vacation'},
      {id: '7', name: 'International Vacation'},
      {id: '9', name: 'Children Higher Study'},
      {id: '2', name: 'Children Marriage'},
      {id: '0', name: 'Other'}
    ];

  $scope.lifeGoalsYearList =  [];
  var maxYearCount2 = 80;
  for(var i=currentYear;i <= currentYear+maxYearCount2; i++){
    $scope.lifeGoalsYearList.push(i);
  }    
  //LifeGoals Frequency Pre-populated
  $scope.lifeGoalsFrequencyList = [
      {id: '0', name: 'Only once'},
      {id: '1', name: 'Once every year'},
      {id: '2', name: 'Once in two year'},
      {id: '3', name: 'Once in three year'},
      {id: '4', name: 'Once in four year'},
      {id: '5', name: 'Once in five year'}
    ];

  //Add Life Goals
  $scope.addNewLifeGoalRow = function(){

    var myDiv= document.getElementsByClassName("goals_table");
    var wrappedMyDiv = angular.element(myDiv);
    var randNo = Math.floor((Math.random() * 100000) + 1);
        
    wrappedMyDiv.append($compile(
      '<div id="added_life_goal_row'+randNo+'" class="goals_row body"><div class="column column1">'+
        '<select id="select_life_goal_row_'+randNo+'" class="goal_select goalCol1 lifeGoalOption" ng-model="lifeGoal'+randNo+'" ng-init="lifeGoal'+randNo+' = \'4\'">'+
        '<option ng-repeat="lifeGoal in lifeGoalsList" value="{{lifeGoal.id}}">{{lifeGoal.name}}</option></select>'+
        '<input type="text" class="other_input alphaSpaceOnly" maxlength="25" autofocus ng-if="lifeGoal'+randNo+' == \'0\'" ng-model="lifeGoalOther'+randNo+'" placeholder="Type Life Goal Here...">'+
        '</div><div class="column column2">'+
        '<select class="goal_select goalCol2" ng-model="lifeGoalFreq'+randNo+'" ng-init="lifeGoalFreq'+randNo+' = \'0\'" ng-if="lifeGoal'+randNo+' == \'6\' || lifeGoal'+randNo+' == \'7\'">'+
        '<option ng-repeat="lifeGoalsFrequency in lifeGoalsFrequencyList" value="{{lifeGoalsFrequency.id}}">{{lifeGoalsFrequency.name}}</option></select>'+
        '</div><div class="column column3">'+
        '<select class="goal_select goalCol3" ng-model="lifeGoalYear'+randNo+'" ng-init="lifeGoalYear'+randNo+' = \''+$scope.yearStart+'\'"><option ng-repeat="x in lifeGoalsYearList">{{x}}</option></select></div>'+
        '<div class="column column4"><div class="edit_graph_row"><div class="edit_graph_data"><div class="age1">'+
        '<label for="lifeGoalPrice'+randNo+'"><i class="fa fa-inr"></i></label><input class="estim_amt" type="text" id="lifeGoalPrice'+randNo+'" readonly style="border:0;">'+
        '<div id="sliderprice-range-goal-estimate-amount'+randNo+'" class="edit_graph_bar" ></div></div></div></div></div><div class="column column5"><div class="switch_tog">'+
        '<div class="switch tab_controls"><ul class="nav nav-tabs"><li class="active"><a data-toggle="tab">Savings</a></li>'+
        '<li><a data-toggle="tab">Loan</a></li></ul></div></div>'+
        '<div class="ed_del"><span id="delete_row'+randNo+'" class="delete deleteLifeGoal"><a><i class="fa fa-times" aria-hidden="true"></i></a></span></div>'+
        '</div></div>')($scope));

    $( "#sliderprice-range-goal-estimate-amount"+randNo+"" ).slider({
      range: "min",
      value:0,//500000,
      min:0,
      max:10000000,
      step:10000,      
      slide: function( event, ui ) {
        $( "#lifeGoalPrice"+randNo+"" ).val( format(ui.value) );
      }
    });
    modifyFpLifeGoals();
    $( "#lifeGoalPrice"+randNo+"" ).val(format($( "#sliderprice-range-goal-estimate-amount"+randNo+"" ).slider( "value" )));
    $('#delete_row'+randNo+'').click(function(){
        $('#added_life_goal_row'+randNo+'').remove();
    });

  };

  $scope.saveFpLifeGoal = function(){
  	$scope.goalPageVisited = false;
    $scope.userFpData.lifeGoals = [];
    //Life Goal Description UserInput
    var lifeGoalInputArr = $('.other_input').map(function(){return this.value}).get();
    var lifeGoalInputIndex = 0;

    //Life Goal Frequency
    var lifeGoalFreqArr = $('.goalCol2 :selected').map(function(){return this.value}).get();
    var lifeGoalFreqIndex = 0;

    //Life Goal Description
    $('.goalCol1 :selected').each(function(index,element){ 
        $scope.userFpData.lifeGoals[index] = {
            goalDescriptionId : parseInt($(this).val()),
            goalDescription : $(this).text(),
            yearofRealization : "",
            loanYesNo : "",
            frequency : 0,
            estimatedAmount : ""
        } 

        //Set Goal Description to Input field Value
        if($(this).text().includes('Other')){
          var inputVal = lifeGoalInputArr[lifeGoalInputIndex++];
          if(inputVal != undefined && inputVal.trim() != "")
            $scope.userFpData.lifeGoals[index].goalDescription = inputVal;
        }    

        //Set Frequency to Dropdown value
        if($(this).text().includes('Vacation')){
          $scope.userFpData.lifeGoals[index].frequency = parseInt(lifeGoalFreqArr[lifeGoalFreqIndex++]);
        }
    });
    
    //Life Goal Year of Realization
    $('.goalCol3 :selected').each(function(index,element){ 
        $scope.userFpData.lifeGoals[index].yearofRealization = parseInt($(this).val());
    });

    //Life Goal using Loan
    var loanArray = $("ul.nav.nav-tabs").find('li.active').map(function(){return this.innerText}).get();
    for (var j = 0; j < loanArray.length; j++){
      if(loanArray[j] == "Loan")
        $scope.userFpData.lifeGoals[j].loanYesNo = 'Yes';
      else
        $scope.userFpData.lifeGoals[j].loanYesNo = 'No';      
    }

    //Life Goal Amount     
    $('[id^=sliderprice-range-goal-estimate-amount]').each(function(index,element){ 
        $scope.userFpData.lifeGoals[index].estimatedAmount =  parseInt($(this).slider('value')); 
        // console.log($(this).slider('value'));
    });
    //console.log($scope.userFpData.lifeGoals);

    //goals_table added_life_goal_row46608
    //Remove appended ID to avoid duplicates
    $('.goals_table').find("[id^='added_life_goal_row']").remove();
  };

  $scope.submitFpData = function(){
      // console.log('FP Submit');
      showLoading();
      //Set Risk ID
      $scope.userFpData.riskGroupId = parseInt($('#riskScoreId').val());
      // console.log($scope.userFpData);      

      //Actual Processing of FP data
      makePostJson();
  };

	$scope.userData = {};
	
	var currentSlideIndex =0;
  var isUserSingle = true;
  var viwedAll = false;
	setTimeout(function(){ 
		$("#displayOnlyUserName").text($("#userFirstName").val());
        $('.carousel').each(function(){
            $(this).carousel({
                interval: false
            });
        });
   	      
      	//Bhagyashree : Change ID to class
	   	$('.btnPrev').on('click', function(event){
	   		$scope.gotoPrev();
	    });
   	
	   	$('.btnNext').on('click', function(event){
	   		$scope.gotoNext();
	    });
	   	
      $.getScript( "js/directives/financialPlanner/jquery-punch.js", function( data, textStatus, jqxhr ) {
          eval(data);
          // console.log( data ); // Data returned
          // console.log( textStatus ); // Success
          // console.log( jqxhr.status ); // 200
          // console.log( "Load was performed." );
        });
     
	   $scope.gotoNext = function(){        
	   		$('#myCarousel').carousel("next");
	        //Draw FP Age Sliders
	        if($('#myCarousel').find('.active').index() == 0)
	          drawFpAgeSlider();
	        //Draw FP Income Sliders
	        if($('#myCarousel').find('.active').index() == 1)
	          drawFpIncomeSlider();
	        //Draw FP Saving Slider        
	        if($('#myCarousel').find('.active').index() == 2)
	          drawFpSavingSlider();
	        //Draw FP Outstanding Loans Slider  
	        if($('#myCarousel').find('.active').index() == 3)
	          drawFpLoanSlider();
	        //Draw FP Combined Networth Slider  
	      	if($('#myCarousel').find('.active').index() == 4)
	      	  drawFpAssetSlider();
	        //Draw FP Insurance Slider  
	      	/*if($('#myCarousel').find('.active').index() == 5)
	      	  drawFpInsuranceSlider();*/
	        //Draw FP Life Goals Slider
	        if($('#myCarousel').find('.active').index() == 5)
	          drawFpLifeGoals();           
	        //Draw FP Return Risk Slider  
	        if($('#myCarousel').find('.active').index() == 6)
	          drawFpRiskReturnSlider();
	        $('html,body').scrollTop(0);
	   	}
	   	
	   	$scope.gotoPrev = function(){
	   		$('#myCarousel').carousel("prev");
	        //Draw FP Age Sliders
	        if($('#myCarousel').find('.active').index() == 2)
	          drawFpAgeSlider();
	        //Draw FP Income Slider
	        if($('#myCarousel').find('.active').index() == 3)
	          drawFpIncomeSlider();
	        //Draw FP Saving Slider 
	        if($('#myCarousel').find('.active').index() == 4)
	          drawFpSavingSlider();
	        //Draw FP Outstanding Loans Slider 
	        if($('#myCarousel').find('.active').index() == 5)
	          drawFpLoanSlider();
	        //Draw FP Combined Networth Slider
	      	if($('#myCarousel').find('.active').index() == 6)
	      	  drawFpAssetSlider();
	      	/*if($('#myCarousel').find('.active').index() == 7)
	      	  drawFpInsuranceSlider();*/
	        if($('#myCarousel').find('.active').index() == 7)
	          drawFpLifeGoals();
	        $('html,body').scrollTop(0);
	   	}
   	
   		$('#btnSubmit').on('click', function(event){
   			showLoading();	    	
	    	
	    	unansweredQuestions = "";
      		
	    	
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
   			var postJson = $scope.userFpData;        
   			//postJson = '{' + postJson + '}';
   			postJson = JSON.stringify(postJson);
        
   			// console.log(postJson);
   			localStorage.setItem("userDataInput", postJson);
   			// localStorage.setItem("lifeGoalsTable", $('#lifegoals').html());
   			
   			// console.log(1);
   			$.ajax({
  			  type: "POST",
  			  url: _gc_up_post_submit,
  			  data: postJson,
  			  dataType: 'json',
  			  contentType:"application/json; charset=utf-8",
  			  success: function(data){
  				  // console.log(2);
  				  //localStorage.setItem("userDataOutput", JSON.stringify(data));
  				  //window.location.href = 'financialPlannerOutput.html';
  				  gotoNextPage();
  			  },
  			  fail: function(err){
  				  // console.log(3);
  				  console.log(err);
  				  hideLoading();
  			  },
  			  error: function(jqXHR, textError, errorThrown){
  				  // console.log(4);
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
   	  				  }catch(err){
                  // console.log('Error in Conversion');
                }
   	  				  localStorage.setItem("userDataOutput", data);
	   	  				var url = window.location.href;
	   		    		var urlpart =  url.split("src=");
	   		    		var src = urlpart[1];
	   		    		if(typeof src === "undefined"){
	   		    			urlpart =  url.split("viewPlanner=");
	   		    			src = urlpart[1];
	   		    			if(typeof src === "undefined"){
	   		    				// window.location.href = 'financialPlannerOutputFD.jsp';
	   		    				window.location.href = 'financialPlannerOutput.jsp';
	   		    			}
	   		    			else if(src.startsWith("true")){
	   		    				window.location.href = 'financialPlannerOutput.jsp?faces-redirect=true&viewPlanner=true';
	   		    			}	   		    				   		    			
	   		    		} else {
	   		    			if (src.startsWith("ps")){
		   		    			window.location.href = 'financialPlannerOutput.jsp?src=ps';
		   		    		} else if (src.startsWith("upw")){
		   		    			window.location.href = 'financialPlannerOutput.jsp?src=upw';
		   		    		} else if (src.startsWith("fpw")){
		   		    			window.location.href = 'financialPlannerOutput.jsp?src=fpw';
		   		    		} else {
		   		    			window.location.href = 'financialPlannerOutput.jsp';
		   		    		}
	   		    		}
   	  			  }
   	  			});
   			}
   		}   		
   		$('#divContent').css("display","block");
   		$('#divLoading').css("display","none");
   		
   		initData();
	}, 1000);
	
	function initData(){
		showLoading();
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
  						// console.log('Error');
  					}
  					if(data.gender != ""){
					  prefillData(data);
					  $scope.userData = data;
					  // console.log('Init Data');
					  // console.log($scope.userData);
				      $scope.$apply();				      
  					}
  					hideLoading();
				}catch(e){
  					$('#divContent').css("display","block");
  					$('#divLoading').css("display","none");
				}				
			},
			error: function(e){
				console.log('error');
				console.log(e);
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	
	function prefillData(data){
	    $scope.userFpData = data;
	    // console.log('Prefill Data');
	    // console.log($scope.userFpData);   
	}

	displayUserName = function (){
    	$("#displayOnlyUserName").text($("#userFirstName").val());
    }
	
	displayUserName();


  function drawFpAgeSlider(){  
    var selfAge = 18;//46;
    var spouseAge = 18;//41;
    // $( "#slider-range-min-age" ).mySmoothSliderFunction();
    
    // AgeSelf
    $( "#slider-range-min-age" ).slider({
      range: "min",
      value:selfAge,//46,
      min:0,
      max:100,
      slide: function( event, ui ) {
        $( "#age" ).val( "" + ui.value );
      }
    });
    
    
    // AgeSpouse
    $( "#slider-range-min-age2" ).slider({
      range: "min",
      value:spouseAge,//41,
      min:0,
      max:100,
      slide: function( event, ui ) {
        $( "#age2" ).val( "" + ui.value );
      }
    });

    // AgeSelf
    $( "#slider-range-min-age3" ).slider({
      range: "min",
      value:selfAge,//46,
      min:0,
      max:100,
      slide: function( event, ui ) {
        $( "#age3" ).val( "" + ui.value );
      }
    });
    
    
    // AgeSpouse
    $( "#slider-range-min-age4" ).slider({
      range: "min",
      value:spouseAge,//41,
      min:0,
      max:100,
      slide: function( event, ui ) {
        $( "#age4" ).val( "" + ui.value );
      }
    });        

    //AgeMale
    $( "#slider-range-min-ageMale" ).slider({
      range: "min",
      value:selfAge,//46,
      min:0,
      max:100,
      slide: function( event, ui ) {
        $( "#ageMale" ).val( "" + ui.value );
      }
    });    
    
    //AgeFemale  
    $( "#slider-range-min-ageFemale" ).slider({
      range: "min",
      value:selfAge,//46,
      min:0,
      max:100,
      slide: function( event, ui ) {
        $( "#ageFemale" ).val( "" + ui.value );
      }
    });
      
    //Existing Values Re-Assigned 
    if($scope.userFpData.userAge != 0){       
      if($scope.userFpData.relationStatus == 'married'){
        if($scope.userFpData.gender == 'male'){
          $( "#slider-range-min-age" ).slider('value', $scope.userFpData.userAge);
          $( "#slider-range-min-age2" ).slider('value', $scope.userFpData.spouseAge);      
        }else if($scope.userFpData.gender == 'female'){
          $( "#slider-range-min-age3" ).slider('value', $scope.userFpData.userAge);
          $( "#slider-range-min-age4" ).slider('value', $scope.userFpData.spouseAge);      
        }
         
      }
      if($scope.userFpData.relationStatus == 'single'){
        if($scope.userFpData.gender == 'male'){
          $( "#slider-range-min-ageMale" ).slider('value',$scope.userFpData.userAge);
        }else if($scope.userFpData.gender == 'female'){
          $( "#slider-range-min-ageFemale" ).slider('value',$scope.userFpData.userAge);
        }      
      }
    }

    //Set Age Text for Input Value
    $( "#age" ).val( "" + $( "#slider-range-min-age" ).slider( "value" ) );
    $( "#age2" ).val( "" + $( "#slider-range-min-age2" ).slider( "value" ) );
    $( "#age3" ).val( "" + $( "#slider-range-min-age3" ).slider( "value" ) );
    $( "#age4" ).val( "" + $( "#slider-range-min-age4" ).slider( "value" ) );
    $( "#ageMale" ).val( "" + $( "#slider-range-min-ageMale" ).slider( "value" ) );
    $( "#ageFemale" ).val( "" + $( "#slider-range-min-ageFemale" ).slider( "value" ) );    
    

    
  }

  function drawFpIncomeSlider(){
      var selfIncome = 1000;//25000;
      var spouseIncome = 0;//25000;
      //Income1
      $( "#slider-range-min-income" ).slider({
        range: "min",
        value:selfIncome,//25000,
        min:1000,
        max:1000000,
        step: 1000,
        slide: function( event, ui ) {
          $("#income").val(format(ui.value)); 
        }
      });

      //Income2
      $( "#slider-range-min-income2" ).slider({
        range: "min",
        value:spouseIncome,//25000,
        min:0,
        max:1000000,
        step: 1000,
        slide: function( event, ui ) {
          $("#income2").val(format(ui.value)); 
        }
      });
        
      //IncomeSingle
      $( "#slider-range-min-income1" ).slider({
        range: "min",
        value:selfIncome,//25000,
        min:1000,
        max:1000000,
        step: 1000,
        slide: function( event, ui ) {
          $("#income1").val(format(ui.value)); 
        }
      });

      //Existing Values Re-Assigned 
      if($scope.userFpData.userMonthlyTakehomeSalary != 0){
        if($scope.userFpData.relationStatus == 'married'){
          $( "#slider-range-min-income" ).slider('value',$scope.userFpData.userMonthlyTakehomeSalary);
          $( "#slider-range-min-income2" ).slider('value',$scope.userFpData.spouseMonthlyTakehomeSalary);
        }
        if($scope.userFpData.relationStatus == 'single'){
          $( "#slider-range-min-income1" ).slider('value',$scope.userFpData.userMonthlyTakehomeSalary);        
        }
      }

      //Set Income Text for Input Value
      $("#income").val(format($( "#slider-range-min-income" ).slider( "value" )));  
      $("#income2").val(format($( "#slider-range-min-income2" ).slider( "value" )));    
      $("#income1").val(format($( "#slider-range-min-income1" ).slider( "value" )));

  }

 function drawFpSavingSlider(){        
        var combinedExpenseVal = 0;
        var combinedSavingRateVal = 0;
        var combinedSavingsVal= 0;

        combinedSavingsVal = Number($('#combinedSal').val());
        //MonthlySaving
        $( "#slider-range-min-save-combine" ).slider({
          range: "min",
          value: 0,//3000,
          min:0,
          max:100000,
          step: 1000,
          slide: function( event, ui ) {
          	   var savingSlideVal = Number(ui.value);
               $("#saving").val( format(savingSlideVal) );    
               combinedExpenseVal = combinedSavingsVal - savingSlideVal;
               $('#combinedExp').text( format(combinedExpenseVal));  
               combinedSavingRateVal = savingSlideVal / combinedSavingsVal * 100;
               $('#combinedSavingRate').text(Number(combinedSavingRateVal).toFixed(2) );
          }
        });

        //Set Slider Max Value
        $("#slider-range-min-save-combine").slider( "option", "max" , $('#combinedSal').val() );
        

        if($scope.userFpData.monthlySavings != 0){
        	  $("#slider-range-min-save-combine").slider( "value" , $scope.userFpData.monthlySavings);              
        }

        //Set Text Values
        combinedExpenseVal = $('#combinedSal').val() - $("#slider-range-min-save-combine").slider( "value");  
        combinedSavingRateVal = $("#slider-range-min-save-combine").slider( "value") / $('#combinedSal').val() * 100;
		$("#saving").val( format($("#slider-range-min-save-combine").slider( "value" )));
	    $('#combinedExp').text(format(combinedExpenseVal));          
    	$('#combinedSavingRate').text(Number(combinedSavingRateVal).toFixed(2));        
  }   

  function drawFpLoanSlider(){
    var maxLoanEmi = $scope.userCombinedExpense;
  	   //Loan Amount 1
		$( "#sliderprice-range-loan-total-0" ).slider({
			range: "min",
			value:0,
			min:0,
			max:100000000,     
			slide: function( event, ui ) {
			  $( "#price-amount-0" ).val(format(ui.value));
			}
		});
		$( "#price-amount-0" ).val(format($( "#sliderprice-range-loan-total-0" ).slider( "value" ) ));

	     //Loan Emi 1
	     $( "#sliderprice-rangeMP-0" ).slider({
	        range: "min",
	        value: 0,//25000,
	        min: 0,//1000,
	        max:maxLoanEmi,
	        slide: function( event, ui ) {
	          $( "#price-emi-0" ).val(format(ui.value));
	        }
	      });
	      $( "#price-emi-0" ).val(format($( "#sliderprice-rangeMP-0" ).slider( "value" ) )); 

	     //Loan Amount 2
	     $( "#sliderprice-range-loan-total-1" ).slider({
	        range: "min",
	        value:0,
	        min:0,
	        max:1000000,
	        slide: function( event, ui ) {
	          $( "#price-amount-1" ).val( format(ui.value ));
	        }
	      });
	      $( "#price-amount-1" ).val( format($("#sliderprice-range-loan-total-1").slider("value"))); 

    	//Loan EMI 2
    	$( "#sliderprice-rangeMP-1" ).slider({
	        range: "min",
	        value: 0,//5000,
	        min:0,//1000,
	        max:maxLoanEmi,
	        slide: function( event, ui ) {
	          $( "#price-emi-1" ).val( format(ui.value ));
	        }
      	});
      	$( "#price-emi-1" ).val(format($("#sliderprice-rangeMP-1").slider("value")));

      // For Auto-Populating Loan Data
      for(var loanIndex = 2; loanIndex < $scope.userFpData.outstandingLoans.length; loanIndex++){
        //LoanAmount
        $( "#sliderprice-range-loan-total-" +loanIndex ).slider({
          range: "min",
          value:0,
          min:0,
          max:10000000,
          slide: function( event, ui ) {
            $( "#price-amount-" + loanIndex ).val( format(ui.value ));
          }
        });
        $( "#price-amount-" + loanIndex).val( format($("#sliderprice-range-loan-total-" + loanIndex).slider("value")));
        //Loan EMI
        $( "#sliderprice-rangeMP-" + loanIndex).slider({
          range: "min",
          value:0,//5000,
          min:0,//1000,
          max:maxLoanEmi,
          slide: function( event, ui ) {
            $( "#price-emi-" + loanIndex).val( format(ui.value ));
          }
        });
        $( "#price-emi-" + loanIndex).val(format($("#sliderprice-rangeMP-" + loanIndex).slider("value"))); 
      }

      //Bug-Fix : Sliders are not updating read only value
      $( "[id^='sliderprice-rangeMP-']" ).each(function(index){
          var splitStr = $(this).attr('id').split("-");
          var loanIndex = splitStr[splitStr.length-1];
          
          $( "#sliderprice-rangeMP-" +loanIndex ).slider({
              range: "min",
              value:0,//5000,
              min:0,//1000,
              max:maxLoanEmi,//100000,
              slide: function( event, ui ) {
                $( "#price-emi-" + loanIndex).val( format(ui.value ));
              }
          }); 

      });

      $( "[id^='sliderprice-range-loan-total-']" ).each(function(index){
        var splitStr = $(this).attr('id').split("-");
        var loanIndex = splitStr[splitStr.length-1];
        $( "#sliderprice-range-loan-total-" +loanIndex ).slider({
                range: "min",
                value:0,
                min:0,
                max:10000000,
                slide: function( event, ui ) {
                  $( "#price-amount-" + loanIndex ).val( format(ui.value ));
                  //Set Max Range based on loan amount value
          		    //$( "#sliderprice-rangeMP-" +loanIndex ).slider("option","max",ui.value);
                }
        }); 
      });

      

      for(var loanIndex=0; loanIndex < $scope.userFpData.outstandingLoans.length; loanIndex++){
        $( "#sliderprice-range-loan-total-" +loanIndex ).slider('value',$scope.userFpData.outstandingLoans[loanIndex].loanAmount);
        $( "#price-amount-" + loanIndex).val( format($("#sliderprice-range-loan-total-" + loanIndex).slider("value")));

        $( "#sliderprice-rangeMP-" +loanIndex ).slider('value',$scope.userFpData.outstandingLoans[loanIndex].emi);
        $( "#price-emi-" + loanIndex).val(format($("#sliderprice-rangeMP-" + loanIndex).slider("value"))); 

        $("#final-payment-year-"+ loanIndex).val($scope.userFpData.outstandingLoans[loanIndex].finalYearofPayment); 
      }

      //Remove appended ID to avoid duplicates
      $('.append-div').find("[id^='row-id-']").remove();
	}

	function drawFpAssetSlider(){
		  //Cash, Bank Deposits & Liquid Debt Mutual Funds
	    $( "#sliderprice-range-networth-0" ).slider({
	      range: "min",
	      value:0,//200000,
	      min:0,
	      max:10000000,
	      step:10000,
	      slide: function( event, ui ) {
	        $("#price-networth-0").val(format(ui.value));   
	        var total = 0;
	        $("[id^=sliderprice-range-networth]").not('#sliderprice-range-networth-0').each(function(index,element){ 
	         total += $(this).slider('value');     
	        });
	        total = ui.value + total;    
	        $('#totalNetworth').val(format(total));  
	      }
	    });
	    
	    //Fixed Income (Provident Funds, Debt Mutual Funds, Bonds, KVP, NSC,etc.)
		  $("#sliderprice-range-networth-1" ).slider({
	      range: "min",
	      value:0,//300000,
	      min:0,
	      max:10000000,
	      step:10000,
	      slide: function( event, ui ) {
	        $( "#price-networth-1" ).val(format(ui.value ));
	        var total = 0;
	        $("[id^=sliderprice-range-networth]").not('#sliderprice-range-networth-1').each(function(index,element){ 
	         total += $(this).slider('value');     
	        });
	        total = ui.value + total;    
	        $('#totalNetworth').val(format(total));         
	      }
	    });
	    
	    //Gold ( ETF, Physical )
		  $("#sliderprice-range-networth-2" ).slider({
	      range: "min",
	      value:0,//300000,
	      min:0,
	      max:10000000,
	      step:10000,
	      slide: function( event, ui ) {
	        $( "#price-networth-2" ).val(format(ui.value));
	        var total = 0;
	        $("[id^=sliderprice-range-networth]").not('#sliderprice-range-networth-2').each(function(index,element){ 
	         total += $(this).slider('value');     
	        });
	        total = ui.value + total;    
	        $('#totalNetworth').val(format(total)); 
	      }
	    });
	    
	    //Equity (Mutual Funds, Shares)
		  $( "#sliderprice-range-networth-3" ).slider({
	      range: "min",
	      value:0,//330000,
	      min:0,
	      max:10000000,
	      step:10000,
	      slide: function( event, ui ) {
	        $( "#price-networth-3" ).val(format(ui.value));
	        var total = 0;
	        $("[id^=sliderprice-range-networth]").not('#sliderprice-range-networth-3').each(function(index,element){ 
	         total += $(this).slider('value');     
	        });
	        total = ui.value + total;    
	        $('#totalNetworth').val(format(total)); 
	      }
	    });  

	    //Total Real Estate
		  $("#sliderprice-range-networth-4" ).slider({
	      range: "min",
	      value:0,//600000,
	      min:0,
	      max:100000000,
	      step:100000,
	      slide: function( event, ui ) {
	        $( "#price-networth-4" ).val(format(ui.value ));
	        var total = 0;
	        $("[id^=sliderprice-range-networth]").not('#sliderprice-range-networth-4').each(function(index,element){ 
	         total += $(this).slider('value');     
	        });
	        total = ui.value + total;    
	        $('#totalNetworth').val(format(total)); 
	      }
	    });
	    
	    
		//For Auto-Populating Asset Data
	    for(var assetIndex = 5; assetIndex < $scope.userFpData.financialAssetList.length; assetIndex++){
	    	//Append Only If not already present
	    	if($(".append-div-networth").find('#row-id-networth-'+assetIndex).length == 0){
	    		$(".append-div-networth").append('<div class="questionnaire-row" id="row-id-networth-'+assetIndex+'">'+
		    		'<div class="questionnaire-c1"><textarea id="aa2-'+assetIndex+'" class="networthValue alphaSpaceOnly"></textarea></div>'+
		    		'<div class="questionnaire-c2">'+
		    			'<div class="age1">'+
		    				'<div id="sliderprice-range-networth-'+assetIndex+'" class="fpq_price_bar" ></div>'+
		    				'<i class="fa fa-inr"></i>'+
		    				'<input type="text" id="price-networth-'+assetIndex+'" readonly><label for="price-networth-'+assetIndex+'"><a href="javascript:;" class="delrowAsset" alt="networth-'+assetIndex+'">X</a></label>'+
		    			'</div>'+
		    		'</div></div>');
		    	$('#aa2-'+assetIndex).val($scope.userFpData.financialAssetList[assetIndex].assetDescription);

		    	$("#sliderprice-range-networth-" + assetIndex).slider({
				      range: "min",
				      value:600000,
				      min:0,
				      max:1000000,
				      slide: function( event, ui ) {
				        $( "#price-networth-" + assetIndex).val(format(ui.value ));
				        var total = 0;
				        $("[id^=sliderprice-range-networth]").not('#sliderprice-range-networth-' + assetIndex).each(function(index,element){ 
				         total += $(this).slider('value');     
				        });
				        total = ui.value + total;    
				        $('#totalNetworth').val(format(total)); 
				      }
				  });
	    	}		    
	    }
	    
      //Bug-Fix : Sliders are not updating read only value
      $( "[id^='sliderprice-range-networth-']" ).each(function(index){
        var splitStr = $(this).attr('id').split("-");
        var assetIndex = splitStr[splitStr.length-1];
        if(assetIndex > 4){
          $("#sliderprice-range-networth-" + assetIndex).slider({
            range: "min",
            value:600000,
            min:0,
            max:1000000,
            step:1000,
            slide: function( event, ui ) {
              $( "#price-networth-" + assetIndex).val(format(ui.value ));
              var total = 0;
              $("[id^=sliderprice-range-networth]").not('#sliderprice-range-networth-' + assetIndex).each(function(index,element){ 
               total += $(this).slider('value');     
              });
              total = ui.value + total;    
              $('#totalNetworth').val(format(total)); 
            }
          });    
        }else{
        	$( "#price-networth-" + assetIndex).val( format($("#sliderprice-range-networth-" + assetIndex).slider("value")));
        } 
      }); 

	    for(var assetIndex=0; assetIndex < $scope.userFpData.financialAssetList.length; assetIndex++){
	    	$( "#sliderprice-range-networth-" +assetIndex ).slider('value',$scope.userFpData.financialAssetList[assetIndex].assetAmount);
	        $( "#price-networth-" + assetIndex).val( format($("#sliderprice-range-networth-" + assetIndex).slider("value")));
	    }

	    getTotalNetworth();

	}

	//Calculate Total Networth 
	function getTotalNetworth(){
		  var total=0;
		  $(".questionnaire-sec").find('[id^=sliderprice-range-networth]').each(function(index,element){ 
		     total += $(this).slider('value');     
		  });
		  $('#totalNetworth').val(format(total));      
	}  

	function drawFpInsuranceSlider(){
	    //Life Insurance Cover
	    $( "#sliderprice-range-cover-life-0" ).slider({
	      range: "min",
	      value:0,//3000000,
	      min:0,
	      max:10000000,
	      slide: function( event, ui ) {
	        $( "#insurancePrice-life-0" ).val( format(ui.value ));
	      }
	    });
	    $( "#insurancePrice-life-0" ).val( format($( "#sliderprice-range-cover-life-0" ).slider( "value" )));

	    //Life Insurance Premium
		 $( "#sliderprice-range-premium-life-0" ).slider({
	      range: "min",
	      value:0,//25000,
	      min:0,
	      max:100000,
	      slide: function( event, ui ) {
	        $( "#insurancePriceMP-life-0" ).val( format( ui.value ));
	      }
	    });
	    $( "#insurancePriceMP-life-0" ).val( format($( "#sliderprice-range-premium-life-0" ).slider( "value" ))); 

	    //Health Insurance Cover
		 $( "#sliderprice-range-cover-health-0" ).slider({
	      range: "min",
	      value:0,//75000,
	      min:0,
	      max:100000,
	      slide: function( event, ui ) {
	        $( "#insurancePrice-health-0" ).val( format(ui.value));
	      }
	    });
	    $( "#insurancePrice-health-0" ).val( format($( "#sliderprice-range-cover-health-0" ).slider( "value" ))); 
	    
	    //Health Insurance Premium 
		 $( "#sliderprice-range-premium-health-0" ).slider({
	      range: "min",
	      value:0,//6500,
	      min:0,
	      max:90000,
	      slide: function( event, ui ) {
	        $( "#insurancePriceMP-health-0" ).val( format(ui.value));
	      }
	    });
	    $( "#insurancePriceMP-health-0" ).val( format($( "#sliderprice-range-premium-health-0" ).slider( "value" ))); 

	    //For Auto-Populating LifeInsurance Data
	    for(var insuranceIndex = 0; insuranceIndex < $scope.userFpData.lifeInsuranceCover.length; insuranceIndex++){
	    	//Append Only If not already present
	    	if($(".append-div-life").find('#row-id-insurance-life-'+insuranceIndex).length == 0){
	    		//Add Life Insurance Row
	    		$(".append-div-life").append($compile('<div class="financial-loan-row" id="row-id-insurance-life-'+insuranceIndex+'" ng-cloak>'+
	    			'<div class="financial-insurance-c1"><div class="loan_mobile">Insurance Name</div>'+
	    				'<input type="text" id="life_name-'+insuranceIndex+'" class="insuranceInfolife alphaSpaceOnly" autofocus></div>'+
	    			'<div class="financial-insurance-c2"><div class="loan_mobile">Insurance Cover </div>'+
	    				'<div class="age1"><div id="sliderprice-range-cover-life-'+insuranceIndex+'" class="loan_price_bar" ></div>'+
	    				'<i class="fa fa-inr"></i> <input type="text" id="insurancePrice-life-'+insuranceIndex+'" readonly ></div></div>'+
	    			'<div class="financial-insurance-c2"><div class="loan_mobile">Annual Premium</div>'+
	    				'<div class="age1"><div id="sliderprice-range-premium-life-'+insuranceIndex+'" class="loan_price_bar"></div>'+
	    				'<i class="fa fa-inr"></i> <input type="text" id="insurancePriceMP-life-'+insuranceIndex+'" readonly > </div></div>'+
	    			'<div class="financial-insurance-c4"></div>'+
	    			'<div class="financial-insurance-c5"> <a href="javascript:void(0)" class="delrowInsurance" alt="life-'+insuranceIndex+'">X</a></div></div>')($scope));

	    		//Assign Input Field Life Insurance Name
		    	$('#life_name-'+insuranceIndex).val($scope.userFpData.lifeInsuranceCover[insuranceIndex].lifeInsuranceDescription);

		    	//Life Insurance Cover
			    $( "#sliderprice-range-cover-life-"+insuranceIndex).slider({
			      range: "min",
			      value:0,//3000000,
			      min:0,
			      max:10000000,
			      slide: function( event, ui ) {
			        $( "#insurancePrice-life-" +insuranceIndex).val( format(ui.value ));
			      }
			    });
			    $( "#insurancePrice-life-" +insuranceIndex).val( format($( "#sliderprice-range-cover-life-" +insuranceIndex).slider( "value" )));

			    //Life Insurance Premium
				 $( "#sliderprice-range-premium-life-" +insuranceIndex).slider({
			      range: "min",
			      value:0,//25000,
			      min:0,
			      max:100000,
			      slide: function( event, ui ) {
			        $( "#insurancePriceMP-life-" +insuranceIndex).val( format( ui.value ));
			      }
			    });
			    $( "#insurancePriceMP-life-" +insuranceIndex).val( format($( "#sliderprice-range-premium-life-"+insuranceIndex ).slider( "value" ))); 
	    	}		    
	    }      

      //Bug-Fix : Sliders are not updating read only value
      $( "[id^='sliderprice-range-cover-life-']" ).each(function(index){
          var splitStr = $(this).attr('id').split("-");
          var insuranceIndex = splitStr[splitStr.length-1];          
          $("#sliderprice-range-cover-life-" + insuranceIndex).slider({
            range: "min",
            value:0,//3000000,
            min:0,
            max:10000000,
            slide: function( event, ui ) {
              $( "#insurancePrice-life-" +insuranceIndex).val( format(ui.value ));
            }
          });
      }); 

      //Bug-Fix : Sliders are not updating read only value
      $( "[id^='sliderprice-range-premium-life-']" ).each(function(index){
          var splitStr = $(this).attr('id').split("-");
          var insuranceIndex = splitStr[splitStr.length-1];
          
          $( "#sliderprice-range-premium-life-" +insuranceIndex).slider({
            range: "min",
            value:0,//25000,
            min:0,
            max:100000,
            slide: function( event, ui ) {
            $( "#insurancePriceMP-life-" +insuranceIndex).val( format( ui.value ));
            }
          });   
      }); 

	    for(var insuranceIndex=0; insuranceIndex < $scope.userFpData.lifeInsuranceCover.length; insuranceIndex++){
	    	$( "#sliderprice-range-cover-life-" +insuranceIndex ).slider('value',$scope.userFpData.lifeInsuranceCover[insuranceIndex].insuranceCover);
	    	$( "#sliderprice-range-premium-life-" +insuranceIndex ).slider('value',$scope.userFpData.lifeInsuranceCover[insuranceIndex].annualPremium);
	        $( "#insurancePrice-life-" + insuranceIndex).val( format($("#sliderprice-range-cover-life-" + insuranceIndex).slider("value")));
	        $( "#insurancePriceMP-life-" + insuranceIndex).val( format($("#sliderprice-range-premium-life-" + insuranceIndex).slider("value")));
	    }

	    //For AutoPopulating HealthInsurance Data
	    for(var insuranceIndex = 0; insuranceIndex < $scope.userFpData.healthInsuranceCover.length; insuranceIndex++){
	    	//Append Only If not already present
	    	if($(".append-div-health").find('#row-id-insurance-health-'+insuranceIndex).length == 0){
	    		//Add health Insurance Row
	    		$(".append-div-health").append($compile('<div class="financial-loan-row" id="row-id-insurance-health-'+insuranceIndex+'" ng-cloak>'+
	    			'<div class="financial-insurance-c1"><div class="loan_mobile">Insurance Name</div>'+
	    				'<input type="text" id="health_name-'+insuranceIndex+'" class="insuranceInfohealth alphaSpaceOnly" autofocus></div>'+
	    			'<div class="financial-insurance-c2"><div class="loan_mobile">Insurance Cover </div>'+
	    				'<div class="age1"><div id="sliderprice-range-cover-health-'+insuranceIndex+'" class="loan_price_bar" ></div>'+
	    				'<i class="fa fa-inr"></i> <input type="text" id="insurancePrice-health-'+insuranceIndex+'" readonly ></div></div>'+
	    			'<div class="financial-insurance-c2"><div class="loan_mobile">Annual Premium</div>'+
	    				'<div class="age1"><div id="sliderprice-range-premium-health-'+insuranceIndex+'" class="loan_price_bar"></div>'+
	    				'<i class="fa fa-inr"></i> <input type="text" id="insurancePriceMP-health-'+insuranceIndex+'" readonly > </div></div>'+
	    			'<div class="financial-insurance-c4"></div>'+
	    			'<div class="financial-insurance-c5"> <a href="javascript:void(0)" class="delrowInsurance" alt="health-'+insuranceIndex+'">X</a></div></div>')($scope));

	    		//Assign Input Field health Insurance Name
		    	$('#health_name-'+insuranceIndex).val($scope.userFpData.healthInsuranceCover[insuranceIndex].healthInsuranceDescription);

		    	//health Insurance Cover
			    $( "#sliderprice-range-cover-health-"+insuranceIndex).slider({
			      range: "min",
			      value:0,//3000000,
			      min:0,
			      max:10000000,
			      slide: function( event, ui ) {
			        $( "#insurancePrice-health-" +insuranceIndex).val( format(ui.value ));
			      }
			    });
			    $( "#insurancePrice-health-" +insuranceIndex).val( format($( "#sliderprice-range-cover-health-" +insuranceIndex).slider( "value" )));

			    //health Insurance Premium
				 $( "#sliderprice-range-premium-health-" +insuranceIndex).slider({
			      range: "min",
			      value:0,//25000,
			      min:0,
			      max:100000,
			      slide: function( event, ui ) {
			        $( "#insurancePriceMP-health-" +insuranceIndex).val( format( ui.value ));
			      }
			    });
			    $( "#insurancePriceMP-health-" +insuranceIndex).val( format($( "#sliderprice-range-premium-health-"+insuranceIndex ).slider( "value" ))); 
	    	}		    
	    }

      //Bug-Fix : Sliders are not updating read only value
      $( "[id^='sliderprice-range-cover-health-']" ).each(function(index){
          var splitStr = $(this).attr('id').split("-");
          var insuranceIndex = splitStr[splitStr.length-1];
          
          $( "#sliderprice-range-cover-health-" +insuranceIndex).slider({
            range: "min",
            value:0,//3000000,
            min:0,
            max:10000000,
            slide: function( event, ui ) {
                $( "#insurancePrice-health-" +insuranceIndex).val( format(ui.value ));
            }
          });   
      }); 

      //Bug-Fix : Sliders are not updating read only value
      $( "[id^='sliderprice-range-premium-health-']" ).each(function(index){
          var splitStr = $(this).attr('id').split("-");
          var insuranceIndex = splitStr[splitStr.length-1];          
          $( "#sliderprice-range-premium-health-" +insuranceIndex).slider({
            range: "min",
            value:0,//25000,
            min:0,
            max:100000,
            slide: function( event, ui ) {
                $( "#insurancePriceMP-health-" +insuranceIndex).val( format( ui.value ));
            }
          });   
      });

	    for(var insuranceIndex=0; insuranceIndex < $scope.userFpData.healthInsuranceCover.length; insuranceIndex++){
	    	$( "#sliderprice-range-cover-health-" +insuranceIndex ).slider('value',$scope.userFpData.healthInsuranceCover[insuranceIndex].insuranceCover);
	    	$( "#sliderprice-range-premium-health-" +insuranceIndex ).slider('value',$scope.userFpData.healthInsuranceCover[insuranceIndex].annualPremium);
	        $( "#insurancePrice-health-" + insuranceIndex).val( format($("#sliderprice-range-cover-health-" + insuranceIndex).slider("value")));
	        $( "#insurancePriceMP-health-" + insuranceIndex).val( format($("#sliderprice-range-premium-health-" + insuranceIndex).slider("value")));
	    }
	}

  drawFpLifeGoals = function(){
    var myDiv= document.getElementsByClassName("goals_table");
      var wrappedMyDiv = angular.element(myDiv);
      
    $( "#sliderprice-range-goal-estimate-amount-0" ).slider({
      range: "min",
      value:0,//5000000,
      min:0,
      max:100000000,
      step:500000,      
      slide: function( event, ui ) {
        $( "#lifeGoalPrice-0" ).val(format(ui.value) );
      }
    });
    $( "#lifeGoalPrice-0" ).val(format($( "#sliderprice-range-goal-estimate-amount-0" ).slider( "value" )));
  
    for(var lifeIndex=0;lifeIndex < $scope.userFpData.lifeGoals.length;lifeIndex++){
      if($(".goals_table").find('#added_life_goal_row'+lifeIndex).length == 0){
          // var randNo = Math.floor((Math.random() * 100000) + 1);
          var goalId = angular.copy($scope.userFpData.lifeGoals[lifeIndex].goalDescriptionId);
          var goalDescription = angular.copy($scope.userFpData.lifeGoals[lifeIndex].goalDescription);
          var freqId = angular.copy($scope.userFpData.lifeGoals[lifeIndex].frequency);
          var freqDescription = angular.copy($scope.userFpData.lifeGoals[lifeIndex].frequencyDesc);
          var yearofReal = angular.copy($scope.userFpData.lifeGoals[lifeIndex].yearofRealization);
          var currentAmt = ($scope.userFpData.lifeGoals[lifeIndex].estimatedAmount);
          var loan = angular.copy($scope.userFpData.lifeGoals[lifeIndex].loanYesNo);
                
          wrappedMyDiv.append($compile(
          '<div id="added_life_goal_row'+lifeIndex+'" class="goals_row body"><div class="column column1">'+
          '<select id="select_life_goal_row_'+lifeIndex+'" class="goal_select goalCol1 lifeGoalOption" ng-model="lifeGoal'+lifeIndex+'" ng-init="lifeGoal'+lifeIndex+' = \''+goalId+'\'">'+
          '<option ng-repeat="lifeGoal in lifeGoalsList" value="{{lifeGoal.id}}">{{lifeGoal.name}}</option></select>'+
          '<input type="text" class="other_input alphaSpaceOnly" maxlength="25" autofocus ng-if="lifeGoal'+lifeIndex+' == \'0\'" ng-model="lifeGoalOther'+lifeIndex+'" ng-init="lifeGoalOther'+lifeIndex+' = \''+goalDescription+'\'" placeholder="Type Life Goal Here...">'+
          '</div><div class="column column2">'+
          '<select class="goal_select goalCol2" ng-model="lifeGoalFreq'+lifeIndex+'" ng-init="lifeGoalFreq'+lifeIndex+' = \''+freqId+'\'" ng-if="lifeGoal'+lifeIndex+' == \'6\' || lifeGoal'+lifeIndex+' == \'7\'">'+
          '<option ng-repeat="lifeGoalsFrequency in lifeGoalsFrequencyList" value="{{lifeGoalsFrequency.id}}">{{lifeGoalsFrequency.name}}</option></select>'+
          '</div><div class="column column3">'+
          '<select class="goal_select goalCol3" ng-model="lifeGoalYear'+lifeIndex+'" ng-init="lifeGoalYear'+lifeIndex+' = \''+yearofReal+'\'"><option ng-repeat="x in lifeGoalsYearList">{{x}}</option></select></div>'+
          '<div class="column column4"><div class="edit_graph_row"><div class="edit_graph_data"><div class="age1">'+
          '<label for="lifeGoalPrice-'+lifeIndex+'"><i class="fa fa-inr"></i></label><input class="estim_amt" type="text" id="lifeGoalPrice-'+lifeIndex+'" readonly style="border:0;">'+
          '<div id="sliderprice-range-goal-estimate-amount-'+lifeIndex+'" class="edit_graph_bar" ></div></div></div></div></div><div class="column column5"><div class="switch_tog">'+
          '<div class="switch tab_controls"><ul class="nav nav-tabs" id="availLoan-'+lifeIndex+'"><li class="active"><a data-toggle="tab">Savings</a></li>'+
          '<li><a data-toggle="tab">Loan</a></li></ul></div></div>'+
          '<div class="ed_del"><span id="delete_row_'+lifeIndex+'" class="delete deleteLifeGoal"><a><i class="fa fa-times" aria-hidden="true"></i></a></span></div>'+
          '</div></div>')($scope));
       
          $( "#sliderprice-range-goal-estimate-amount-"+lifeIndex ).slider({
            range: "min",
            value: currentAmt,//500000,
            min:0,
            max:100000000,  //Assigning max value amongst all life goals
            step:1000,      //Assigning least step amongst all life goals
            slide: function( event, ui ) {
              $( "#lifeGoalPrice-"+lifeIndex ).val( format(ui.value) );
            }
          });
        
          $( "#sliderprice-range-goal-estimate-amount-"+lifeIndex ).slider('value',$scope.userFpData.lifeGoals[lifeIndex].estimatedAmount);
          
          $( "#lifeGoalPrice-"+lifeIndex ).val(format($( "#sliderprice-range-goal-estimate-amount-"+lifeIndex ).slider( "value" )));
          $('#delete_row'+lifeIndex).click(function(){
            $('#added_life_goal_row'+lifeIndex).remove();
          }); 
          //Toggle Loans or Savings
          if(loan == 'Yes')
            jQuery('#availLoan-'+lifeIndex+' a:last').tab('show');
      }
    }         
     //Bug-Fix : Sliders are not updating read only value
      $( "[id^='sliderprice-range-goal-estimate-amount-']" ).each(function(index){
          var splitStr = $(this).attr('id').split("-");
          var insuranceIndex = splitStr[splitStr.length-1];          
          $( "#sliderprice-range-goal-estimate-amount-"+insuranceIndex+"").slider({
            range: "min",
            value:500000,
            min:0,
            max:100000000,        
            step:1000,      
            slide: function( event, ui ) {
              $( "#lifeGoalPrice-"+insuranceIndex+"").val( format(ui.value) );              

            }
          });   

          if($scope.userFpData.lifeGoals.length != 0){
              $( "#sliderprice-range-goal-estimate-amount-"+index ).slider('value',$scope.userFpData.lifeGoals[index].estimatedAmount);
              $( "#lifeGoalPrice-"+index ).val(format($( "#sliderprice-range-goal-estimate-amount-"+index ).slider( "value" )));                
          }
      });
  }

  function drawFpRiskReturnSlider(){
      var riskGroup = ['low','moderate_low','moderate','moderate_high','high'];
      var riskGroupScore = [0,25,50,75,100];
      $( "#slider-vertical" ).slider({
          orientation: "vertical",
          range: "min",
          min: 0,
          max:100,
          value:25,
        step: 25,
        slide: function( event, ui ) {
        $('.temp').removeClass('active');
            if(ui.value == '100'){      
                $('.high').addClass('active');
                $( "#riskScoreId" ).val(5);
            }
            if(ui.value == '75'){     
                $('.moderate_high').addClass('active');
                $( "#riskScoreId" ).val(4);
            }
            if(ui.value == '50'){     
                $('.moderate').addClass('active');
                $( "#riskScoreId" ).val(3);
            }
            if(ui.value == '25'){     
                $('.moderate_low').addClass('active');
                $( "#riskScoreId" ).val(2);
            }
            if(ui.value == '0'){      
                $('.low').addClass('active');
                $( "#riskScoreId" ).val(1);
            }                           
        }
      });
      //Default Risk to be displayed is moderate_low
      $('.temp').removeClass('active');
      $('.moderate_low').addClass('active');
      $( "#riskScoreId" ).val(2);

      //For AutoPopulating Risk
      if($scope.userFpData.riskGroupId != 0){
        var tempIndex = $scope.userFpData.riskGroupId-1;
        $('.temp').removeClass('active');
        $( "#riskScoreId" ).val($scope.userFpData.riskGroupId);
        $('.'+riskGroup[tempIndex]).addClass('active');
        $( "#slider-vertical").slider('value',riskGroupScore[tempIndex]);
      }
  }
  
  function modifyFpLifeGoals(){
    $('.lifeGoalOption').on('change', function() {
        var elementId = $(this).attr('id');//
        elementId = elementId.replace('select_life_goal_row_','');
        var maxVal = 0;
        var stepVal = 0;
        if(this.value == 5){  //House
          maxVal = 100000000;
          stepVal=500000;
        }
        else if(this.value == 1|| this.value == 2)//SelfMarriage and  ChildrenMarriage
        	{
        	 maxVal = 5000000;
             stepVal=10000;
        	}
        else if(this.value == 4 ){ 
          //Car   
          maxVal = 10000000;
          stepVal=50000;
        }
    	else if(this.value == 6)//DomesticVacation
    	{
    	 maxVal = 1000000;
         stepVal=10000;
    	}
    	else if(this.value == 7)//InternationalVacation
    		{
    		maxVal = 5000000;
            stepVal=10000;
    		}
        else if(this.value == 3){ //2-wheeler
          maxVal = 5000000;
          stepVal=5000;
        }
        else if(this.value == 9 || this.value == 8){  //ChildrenHigherStudy and SelfHigherStudy
          maxVal = 10000000;
          stepVal=100000;
        }else{
          //Other
          maxVal = 1000000;
          stepVal=10000;
        }
        $( "#sliderprice-range-goal-estimate-amount"+elementId+"" ).slider({
            range: "min",
            value:0,
            min:0,
            max:maxVal,
            step:stepVal,
            slide: function( event, ui ) {
              $( "#lifeGoalPrice"+elementId+"" ).val( format(ui.value) );
            }
        });
    });    
    
  }

  $(document).ready(function(){
		$(document).on('click','.delrow',function(){
			var rowID = $(this).attr('alt');
			
			if(confirm("Are you sure you want to delete this item?") == true || navigator.platform == "iPhone") {
				$('#row-id-'+rowID).remove();			
			} 
			
		});
	
		$(document).on('click','.deleteLifeGoal',function(){
		    var delRowId = $(this).attr('id');
		    var delRowArr = delRowId.split('_');
		    var rowID = delRowArr[delRowArr.length-1];
		    if (confirm("Are you sure you want to remove this life goal?") == true  || navigator.platform == "iPhone") {
		      $('#added_life_goal_row'+rowID).remove();
		    } 
		});
		
		$(document).on('click','.delrowAsset',function(){
		    var rowIdAlt =  $(this).attr('alt');
				var rowID = rowIdAlt.replace('networth-','');
				var x;
				if (confirm("Are you sure you want to delete this item?") == true  || navigator.platform == "iPhone") {
					$('#row-id-networth-'+rowID).remove();
				} 		
		});
  });
}]);