app.controller('FinancialPlannerOutput', ['$scope','$compile', function($scope,$compile) {
	$scope.errorTitle= "Error";
	$scope.errorBody="Please answer the following questions";
	$scope.userFpArray;
	$scope.userFpData = {};
	$scope.defaultfploanassumptions = [];
	$scope.userLifeGoals = [];
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

    $scope.lifeGoalsFrequencyList = [
      {id: '0', name: 'Only once'},
      {id: '1', name: 'Once every year'},
      {id: '2', name: 'Once in two year'},
      {id: '3', name: 'Once in three year'},
      {id: '4', name: 'Once in four year'},
      {id: '5', name: 'Once in five year'}
    ];

    $scope.lifeGoalsYearList =  [];
    var currentYear = new Date().getFullYear();
	var maxYearCount2 = 80;
	for(var i=currentYear;i <= currentYear+maxYearCount2; i++){
		$scope.lifeGoalsYearList.push(i);
	}    

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
	        '<select class="goal_select goalCol3" ng-model="lifeGoalYear'+randNo+'" ng-init="lifeGoalYear'+randNo+' = \'2022\'"><option ng-repeat="x in lifeGoalsYearList">{{x}}</option></select></div>'+
	        '<div class="column column4"><div class="edit_graph_row"><div class="edit_graph_data"><div class="age1">'+
	        '<label for="lifeGoalPrice'+randNo+'"><i class="fa fa-inr"></i></label><input class="estim_amt" type="text" id="lifeGoalPrice'+randNo+'" readonly style="border:0;">'+
	        '<div id="sliderprice-range-goal-estimate-amount'+randNo+'" class="edit_graph_bar" ></div></div></div></div></div><div class="column column5"><div class="switch_tog">'+
	        '<div class="switch tab_controls"><ul class="nav nav-tabs"><li class="active"><a data-toggle="tab">Savings</a></li>'+
	        '<li><a data-toggle="tab">Loan</a></li></ul></div></div>'+
	        '<div class="ed_del"><span id="delete_row'+randNo+'" class="delete"><a><i class="fa fa-times" aria-hidden="true"></i></a></span></div>'+
	        '</div></div>')($scope));

	    $( "#sliderprice-range-goal-estimate-amount"+randNo+"" ).slider({
	      range: "min",
	      value:0,
	      min:0,
	      max:100000000,  //Assigning max value amongst all life goals
          step:1000,      //Assigning least step amongst all life goals
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

  	$scope.rangeHundred =  [];
    var startIndex;
	var endIndex = 100;
	for(startIndex =1 ; startIndex <= endIndex; startIndex++){
		$scope.rangeHundred.push(startIndex);
	} 

	$scope.updateFpAdvanceSettings = function(){
		//Misc Sliders
		$scope.userFpData.fpmasterassumption.fpmiscassumptions.lifeExpectancy = $( "#adv_sett_slide1").slider("value");
        $scope.userFpData.fpmasterassumption.fpmiscassumptions.rateOfGrowthOfFd = $( "#adv_sett_slide2").slider("value");
        $scope.userFpData.fpmasterassumption.fpmiscassumptions.longTermInflationExpectation = $( "#adv_sett_slide3").slider("value");
        $scope.userFpData.fpmasterassumption.fpmiscassumptions.postRetirementRecurringExpense = $( "#adv_sett_slide4").slider("value");

        //Increment Sliders
        for(var ageIndex = 0 ; ageIndex < $scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement.length; ageIndex++){
        	var tempIndex = ageIndex+1;
        	$scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement[ageIndex].selfSalaryIncreaseRate = $( "#salary_self_slide" + tempIndex).slider("value");
        	$scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement[ageIndex].spouseSalaryIncreaseRate = $( "#salary_spouse_slide" + tempIndex).slider("value");        	
        	$scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement[ageIndex].expenseIncreaseRate = $( "#salary_exp_slide" + tempIndex).slider("value");        	
        }

        var finalJson = JSON.stringify(angular.copy($scope.userFpData.fpmasterassumption));

        $.ajax({
			type: 'POST',
			url: _gc_url_fpo_post_editAssumptions,
			data: {
				fpmasterassumption: finalJson
			},
			success: function(rdata){
				// console.log(rdata);
				initFinancialPlannerData(rdata,'high_chart');
				$('html,body').scrollTop(0);
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});

		console.log('Update Advance Settings');
	};

	$scope.resetFpAdvanceSettings = function(){		
		$scope.userFpData.fpmasterassumption.fploanassumptions = angular.copy($scope.defaultfploanassumptions);
		drawFpIncrementSlider();
		drawFpMiscSlider();
		console.log('Reset Advance Settings');
	};
	
	setTimeout(function(){ 
		showLoading();
		$.getScript( "js/directives/financialPlanner/jquery-punch.js", function( data, textStatus, jqxhr ) {
          eval(data);
          // console.log( data ); // Data returned
          // console.log( textStatus ); // Success
          // console.log( jqxhr.status ); // 200
          // console.log( "Load was performed." );
        });
		$.ajax({
			type: "GET",
			url: _gc_url_fpo_get_assetExpenditureChart,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			  success: function(data){
			  	//Bhagyashree
			  	initFinancialPlannerData(data,'high_chart');
			  	
			  	$scope.userFpArray = fpArray;
			  	// console.log($scope.userFpArray);
			  	// console.log('setTimeout');			  	 
			  	$scope.userFpData = data;
			  	// console.log($scope.userFpData);
			  	$scope.defaultfploanassumptions = angular.copy($scope.userFpData.fpmasterassumption.fploanassumptions);			  	
			  	drawFpIncrementSlider();
			  	drawFpMiscSlider();
			  	drawFpRiskReturnSlider();
			  	drawFpInputSlider();
			  	drawFpLifeGoals();

				userDataOutput = data;
				userDataInput = data;
				$scope.lifeGoalsJson = userDataOutput.lifeGoals;
				$scope.$apply();
				hideLoading();
			}
		});
		
		$("#displayOnlyUserName").text($("#userFirstName").val());
		
		$('html,body').scrollTop(0);

	},0);
	
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
	
	
	
	makeJsonLifeGoals = function(){
		var finalJson = ""
   			var tempJson = ""
   			
   			var n = $('select.lgOptions').length;
   			var arrloan_description = $('select.lgOptions');
   			var arrOther = $('.lgOther');
   			var arrFrequency = $("select.lgFrequency");
   			var arrYearOfRealization = $(".lgYear");
   			var arrLoanYesNo = $(".lgLoan");
   			var arrAmount = $(".lgAmount");

   			for(i=0; i < n; i++) {   			   
   				if(arrloan_description.eq(i).val()=="" || arrloan_description.eq(i).find('option:selected').text()=="" || arrYearOfRealization.eq(i).val()=="" || arrFrequency.eq(i).val()=="" || arrAmount.eq(i).val()==""){
   					
   				}
   				else{
   					if(!tempJson==''){
   	   					tempJson =tempJson+','
   	   				}
   					
   	   				tempJson =tempJson+'{';
   	   				tempJson=tempJson + '"goalDescriptionId":' + arrloan_description.eq(i).val() + ',';
   	   				tempJson=tempJson + '"goalDescription":"' + arrloan_description.eq(i).find('option:selected').text()+'",';   			   
   	   				tempJson=tempJson + '"yearofRealization":'+arrYearOfRealization.eq(i).val()+',';   			   
   	   				tempJson=tempJson + '"loanYesNo":"'+ (arrLoanYesNo.eq(i).is(':checked')? "Yes" : "No") +'",';   			   
   	   				tempJson=tempJson + '"frequency":'+arrFrequency.eq(i).val().replace(/,/g , '')+',' ;  			   
   	   				tempJson=tempJson + '"estimatedAmount":'+arrAmount.eq(i).val().replace(/,/g , '')+',';		   
   	   				tempJson=tempJson + '"age": null,';
   	   				tempJson=tempJson + '"inflatedAmount": null';
   	   				tempJson =tempJson+'}';
   				}
   			}
   			
   			finalJson ='['+tempJson+']';
   			console.log(finalJson);
            
   			//Updating the local storage with lifeGoals
			var userDataInputNew= JSON.parse(localStorage.getItem("userDataInput")); 
			if(userDataInputNew != null){
				userDataInputNew.lifeGoals = JSON.parse(finalJson);
				window.localStorage.setItem("userDataInput", JSON.stringify(userDataInputNew));
				//console.log(localStorage.getItem("userDataInput"));
				
			}
			
			
			
            $.post(
                    _gc_url_fpo_post_editLifeGoals,
                    { lifeGoals: finalJson },
                    function(rdata) {
                    	
                        processChartData(rdata);
                        
                        //Updating the local storage with lifeGoals
                    	console.log("Life Goals Edited:");
                    	console.log(rdata);
                    	var userDataOutputNew= JSON.parse(localStorage.getItem("userDataOutput")); 
                    	userDataOutputNew.lifeGoals = rdata.lifeGoals;
                    	window.localStorage.setItem("userDataOutput", JSON.stringify(userDataOutputNew));
                    	//console.log(localStorage.getItem("userDataOutput"));
                    	//$scope.lifeGoalsJson = userDataOutputNew.lifeGoals ;
            			//$scope.$apply();
            			
            			$('html,body').scrollTop(0);
                        
                    }
            );
   			
	}
			
	
	makeJsonAdvanceSettings = function(){
		finalJson = ''
		tempJson = ''
		tempJson = '"assumptionId":null, "customerId":null, "retirementAge" : '+$('#range_RetirementAge').val()+
										', "lifeExpectancy": '+$('.lifeExpentancy').val()+
										', "amountInvestedToMmf" : '+$('#range_allocationMMF').val()+
										', "rateOfGrowthOfFd" : '+$('.rateOfGrowthOfFd').val()+
										', "longTermInflationExpectation" : '+$('.longTermInflationExpectation').val()+
										', "postRetirementRecurringExpense" : '+$('.postRetirementRecurringExpense').val()+'';
		tempJson = '"fpmiscassumptions" : {'+tempJson+'}'
		finalJson = tempJson;
		
		tempJson = ''	
		$('.lowerLimitAge').each(function(i, obj) {
			
			if(tempJson !==''){
				tempJson = tempJson+',';
			}
			tempJson = tempJson + 	'{"incrementAssumptionId": null '+
									',"assumptionId": " null '+
									'","customerId": null'+
									',"lowerLimitAge": '+$('.lowerLimitAge').eq(i).val()+
									',"upperLimitAge": '+$('.upperLimitAge').eq(i).val()+
									',"selfSalaryIncreaseRate": '+$('select[class="selfSalaryIncreaseRate"]').eq(i).val()+
									',"spouseSalaryIncreaseRate": '+$('select[class="spouseSalaryIncreaseRate"]').eq(i).val()+
									',"expenseIncreaseRate": '+$('select[class="expenseIncreaseRate"]').eq(i).val()+'}'
		});
		tempJson = '"fpsalaryexpenseincrement" : ['+tempJson+']'
		finalJson = finalJson + ','+ tempJson;
		
		
		tempJson = ''	
		$('.loanForID').each(function(i, obj) {
			
			if(tempJson !==''){
				tempJson = tempJson+',';
			}
			tempJson = tempJson + 	'{"id":null, "loanAssumptionId": '+$('.loanForID').eq(i).val()+
									',"loanTypeDescription": "'+$('.loanForDesc').eq(i).val()+
						            '","loanDuration": '+$('select[class="loanNumberOfYears"]').eq(i).val()+
						            ',"interestRate": '+$('select[class="loanIntrestRate"]').eq(i).val()+
						            ',"downPaymentPercent": '+$('select[class="loanDownpayment"]').eq(i).val()+
						            ',"assumptionId":null, "customerId":null}'
		});
		tempJson = '"fploanassumptions" : ['+tempJson+']'
		finalJson = finalJson + ','+ tempJson;
		
		
		tempJson = ''	
		
		finalJson = '{' + finalJson + '}';
		console.log(finalJson);
		
		$.ajax({
			type: 'POST',
			url: _gc_url_fpo_post_editAssumptions,
			data: {
				fpmasterassumption: finalJson
			},
			success: function(rdata){
				console.log(rdata);
				processChartData(rdata);
				$('html,body').scrollTop(0);
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}

	$scope.editResponse = function(){
		var url = window.location.href;
   		var urlpart =  url.split("src=");
   		var src = urlpart[1];
   		if(typeof src === "undefined"){
   			urlpart =  url.split("viewPlanner=");
   			src = urlpart[1];
   			if(typeof src === "undefined"){
   				window.location.href = _gc_url_baseUrl+"/faces/pages/v3/userProfile.jsp";
   			}
   			if(src.startsWith("true")){
   				window.location.href = _gc_url_baseUrl+"/faces/pages/v3/userProfile.jsp?faces-redirect=true&viewPlanner=true";
   			}   			
   		} else {
   			if (src.startsWith("ps")){
   				window.location.href = _gc_url_baseUrl+"/faces/pages/v3/userProfile.jsp?faces-redirect=true&src=ps";
   			} else if (src.startsWith("fpw")){
   				window.location.href = _gc_url_baseUrl+"/faces/pages/v3/userProfile.jsp?faces-redirect=true&src=fpw";
   			} else {
   				window.location.href = _gc_url_baseUrl+"/faces/pages/v3/userProfile.jsp";
   			}
   		}
	}
	
	$scope.saveAndSubmit = function(){
		showLoading();
		$.ajax({
			type: 'POST',
			url: _gc_url_fpo_post_saveAndSubmit,
			data: {
				saveFinancialPlanner: 1
			},
			success: function(rdata){
				console.log(rdata);
				clevertap.event.push("FP Output Saved");				
				//processChartData(rdata);
				try{
					rdata = JSON.parse(rdata);
				}catch(e){
			        console.log('response is json not a string');
				}
				
//				console.log(location.search.substr(1).split("&")[0].split("=")[1]);
				var viewPlanner = location.search.substr(1).split("&")[0].split("=")[1];//$.url.attr('viewPlanner');
				console.log("viewPlanner Parameter");
				console.log(viewPlanner);
				if(!viewPlanner){
				//if(rdata.redirectInfo){
					window.location.href = _gc_url_fpo_redirect_onRedirectIno_true;
				}
				else{
					var url = window.location.href;
   		    		var urlpart =  url.split("src=");
   		    		var src = urlpart[1];
   		    		
   		    		if(typeof src === "undefined"){
   		    			window.location.href = _gc_url_fpo_redirect_onRedirectIno_false;
   		    		} else {
   		    			if (src.startsWith("ps")){
   	   		    			window.location.href = _gc_url_portfolio_search_ui;
   	   		    		} else if (src.startsWith("fpw")){
   	   		    			var settings = {
   	   		    					"async": true,
   	   		    					"crossDomain": true,
   	   		    					"url": _gc_url_rpStatus,
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
   	   		    						if (res == "Error:rpstatusfailed" || res == "false"){
   	   		    							window.location.href = _gc_url_portfolio_search_ui;
   	   		    						} else if (res == "true") {
   	   		    							//window.location.href = _gc_url_baseUrl+"/faces/pages/v2/rpwizard.jsp?faces-redirect=true&next=upw";
   	   		    						}
   	   		    						
   	   		    					}).fail(function (response){
   	   		    					alert(response);
   	   		    					});
   	   		    		} else if (src.startsWith("rpo")){
   	   		    			window.location.href = _gc_url_baseUrl+"/faces/pages/v3/investor-portfolio-edit.jsp?faces-redirect=true&src=upw";
   	   		    		} else {
   	   		    			window.location.href = _gc_url_fpo_redirect_onRedirectIno_false;
   	   		    		}
   		    		}
					
				}
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
	
	addNewLifeGoal = function(){
		$("#lifegoals").append('<tr><td><select id="lgOptions" class="lgOptions" name="test"> <option value="1" data-iconurl="img/icon_selfMarriage.png">Self Marriage</option> <option value="8" data-iconurl="img/icon_selfStudy.png">Self Higher study</option> <option value="3" data-iconurl="img/icon_bike.png">Two Wheeler</option> <option value="4" data-iconurl="img/icon_car.png">Car</option> <option value="5" data-iconurl="img/icon_home.png">House</option> <option value="6" data-iconurl="img/icon_vacation.png">Domestic vacation</option> <option value="7" data-iconurl="img/icon_vacation.png">International vacation</option> <option value="9" data-iconurl="img/icon_selfStudy.png">Children Higher Study</option> <option value="2" data-iconurl="img/icon_childrenMarriage.png">Children Marriage</option> <option value="0" data-iconurl="img/icon_others.png">Others</option></select><input id="lgOther" class="lgOther" type="text" id="txtOthers" style="display:none"></td><td><select id="lgFrequency" class="lgFrequency" name="testx"> <option value="0" >Only once</option> <option value="1" >Once every year</option> <option value="2" >Once in two year</option> <option value="3" >Once in three year</option> <option value="4" >Once in four year</option> <option value="5" >Once in five year</option> </select></td><td><div class="form-group"><div class="input-group date"><input type="text" class="form-control lgYear calender"/></div></div></td><td width=400><input type="text" class="form-control lgAmount txtRupee" aria-describedby="basic-addon1"></td><td><input type="checkbox" class="lgLoan" name="my-checkbox"></td><td width=100><img src="img/x.png" class="btnRemoveRow" width="30px" onclick="javascript:removeThisLifeGoal(this)"></td></tr>');
        
        $("[name='my-checkbox']").bootstrapSwitch({
    		'size':'mini',
    		'onText':'Yes',
    		'offText':'No'
    	});
        $('.calender').datetimepicker({
	    	viewMode: 'years',
	    	format: 'YYYY'
	    });
        
        $(function() {
        	var selectBox = $("select").selectBoxIt();
		});
        addEventToLoanDropDown();
        $('.clFinalYear').datetimepicker();
	    $('.lgYear').datetimepicker({
	    	viewMode: 'years',
	    	format: 'YYYY'
	    });
	}
	addEventToLoanDropDown = function(){
    	//alert();
    	$('select.lgOptions').each(function( index ) {
	    	$(this).on('change', function (e) {
	    	    var optionSelected = $("option:selected", this);
	    	    var valueSelected = this.value;
	    	    toggleOthers(index,valueSelected)
	    	});
    	});
    }
	
	removeThisLifeGoal = function(element){
		$(element).parent().parent().remove();
	}

	drawFpIncrementSlider = function(){
		//Self Salary Sliders
	    $( "#salary_self_slide1" ).slider({
          range: "min",
          value:12,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_self1" ).val( "" + ui.value );
          }
        });        

        $( "#salary_self_slide2" ).slider({
          range: "min",
          value:9,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_self2" ).val( "" + ui.value );
          }
        });

        $( "#salary_self_slide3" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_self3" ).val( "" + ui.value );
          }
        });

        $( "#salary_self_slide4" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_self4" ).val( "" + ui.value );
          }
        });        

        $( "#salary_self_slide5" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_self5" ).val( "" + ui.value );
          }
        });

        $( "#salary_self_slide6" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_self6" ).val( "" + ui.value );
          }
        });

		//Spouse Salary Sliders

		$( "#salary_spouse_slide1" ).slider({
          range: "min",
          value:12,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_spouse1" ).val( "" + ui.value );
          }
        });
        
        $( "#salary_spouse_slide2" ).slider({
          range: "min",
          value:9,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_spouse2" ).val( "" + ui.value );
          }
        });

        $( "#salary_spouse_slide3" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_spouse3" ).val( "" + ui.value );
          }
        });
        
        $( "#salary_spouse_slide4" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_spouse4" ).val( "" + ui.value );
          }
        });

        $( "#salary_spouse_slide5" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_spouse5" ).val( "" + ui.value );
          }
        });
        
        $( "#salary_spouse_slide6" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_spouse6" ).val( "" + ui.value );
          }
        });

		//Expense Sliders
		$( "#salary_exp_slide1" ).slider({
          range: "min",
          value:12,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_exp1" ).val( "" + ui.value );
          }
        });

        $( "#salary_exp_slide2" ).slider({
          range: "min",
          value:9,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_exp2" ).val( "" + ui.value );
          }
        });

        $( "#salary_exp_slide3" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_exp3" ).val( "" + ui.value );
          }
        });

        $( "#salary_exp_slide4" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_exp4" ).val( "" + ui.value );
          }
        });

        $( "#salary_exp_slide5" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_exp5" ).val( "" + ui.value );
          }
        });

        $( "#salary_exp_slide6" ).slider({
          range: "min",
          value:8,
          min:0,//1,
          max:100,
          slide: function( event, ui ) {
            $( "#salary_exp6" ).val( "" + ui.value );
          }
        });        

        //AutoPopulate Values
        for(var ageIndex = 0 ; ageIndex < $scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement.length; ageIndex++){
        	var tempIndex = ageIndex+1;
        	$( "#salary_self_slide" + tempIndex).slider("value",$scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement[ageIndex].selfSalaryIncreaseRate);
        	$( "#salary_spouse_slide" + tempIndex).slider("value",$scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement[ageIndex].spouseSalaryIncreaseRate);        	
        	$( "#salary_exp_slide" + tempIndex).slider("value",$scope.userFpData.fpmasterassumption.fpsalaryexpenseincrement[ageIndex].expenseIncreaseRate);        	
        	$( "#salary_self" + tempIndex).val( "" + $( "#salary_self_slide" + tempIndex).slider( "value" ) );
        	$( "#salary_spouse" + tempIndex).val( "" + $( "#salary_spouse_slide" + tempIndex).slider( "value" ) );	
        	$( "#salary_exp" + tempIndex).val( "" + $( "#salary_exp_slide" + tempIndex).slider( "value" ) );        
        }
	}

	drawFpMiscSlider = function(){
		$( "#adv_sett_slide1" ).slider({
          range: "min",
          value:85,
          min:1,
          max:100,
          slide: function( event, ui ) {
            $( "#adv_sett1" ).val( "" + ui.value );
          }
        });
        
        $( "#adv_sett_slide2" ).slider({
          range: "min",
          value:25,
          min:1,
          max:100,
          slide: function( event, ui ) {
            $( "#adv_sett2" ).val( "" + ui.value );
          }
        });
        
        $( "#adv_sett_slide3" ).slider({
          range: "min",
          value:40,
          min:1,
          max:100,
          slide: function( event, ui ) {
            $( "#adv_sett3" ).val( "" + ui.value );
          }
        });
        
        $( "#adv_sett_slide4" ).slider({
          range: "min",
          value:75,
          min:1,
          max:100,
          slide: function( event, ui ) {
            $( "#adv_sett4" ).val( "" + ui.value );
          }
        });
        
        $( "#adv_sett_slide1").slider("value",$scope.userFpData.fpmasterassumption.fpmiscassumptions.lifeExpectancy);
        $( "#adv_sett_slide2").slider("value",$scope.userFpData.fpmasterassumption.fpmiscassumptions.rateOfGrowthOfFd);
        $( "#adv_sett_slide3").slider("value",$scope.userFpData.fpmasterassumption.fpmiscassumptions.longTermInflationExpectation);
        $( "#adv_sett_slide4").slider("value",$scope.userFpData.fpmasterassumption.fpmiscassumptions.postRetirementRecurringExpense);
        
        $( "#adv_sett1" ).val( "" + $( "#adv_sett_slide1" ).slider( "value" ) );
        $( "#adv_sett2" ).val( "" + $( "#adv_sett_slide2" ).slider( "value" ) );
        $( "#adv_sett3" ).val( "" + $( "#adv_sett_slide3" ).slider( "value" ) );
        $( "#adv_sett4" ).val( "" + $( "#adv_sett_slide4" ).slider( "value" ) );
	}

	function drawFpRiskReturnSlider(){
      var riskGroup = ['low','moderate_low','moderate','moderate_high','high'];
      var riskGroupScore = [0,25,50,75,100];
      var originalRiskGroupId = angular.copy($scope.userFpData.riskGroupId);
      

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
                $scope.userFpData.riskGroupId = 4;
            }
            if(ui.value == '50'){     
                $('.moderate').addClass('active');
                $( "#riskScoreId" ).val(3);
                $scope.userFpData.riskGroupId = 3;
            }
            if(ui.value == '25'){     
                $('.moderate_low').addClass('active');
                $( "#riskScoreId" ).val(2);
                $scope.userFpData.riskGroupId = 2;
            }
            if(ui.value == '0'){      
                $('.low').addClass('active');
                $( "#riskScoreId" ).val(1);
                $scope.userFpData.riskGroupId = 1;
            }                 
        },
        stop: function(event,ui){
        	if(ui.value == '100'){  
        		$scope.userFpData.riskGroupId = 5;    
        	}
        	if(ui.value == '75'){
        		$scope.userFpData.riskGroupId = 4;
        	}  
        	if(ui.value == '50'){     
                $scope.userFpData.riskGroupId = 3;
            }
            if(ui.value == '25'){     
                $scope.userFpData.riskGroupId = 2;
            }
            if(ui.value == '0'){      
                $scope.userFpData.riskGroupId = 1;
            }    	        	
        	var riskScoreValue = angular.copy($scope.userFpData.riskGroupId);
            $.post(
        		_gc_url_fpo_post_editRiskScore,
        		{ 
        			riskScore : riskScoreValue
        		},
        		function(rdata) {
        			initFinancialPlannerData(rdata,'high_chart');
        		}
        	);
        }
      });      
      
      if(originalRiskGroupId != 0){  
      	//For AutoPopulating Risk
      	var tempIndex = originalRiskGroupId-1;      
        $('.temp').removeClass('active');
        $( "#riskScoreId" ).val(originalRiskGroupId);
        $('.'+riskGroup[tempIndex]).addClass('active');
        $( "#slider-vertical").slider('value',riskGroupScore[tempIndex]);        
      }else{
      	//Default Risk to be displayed is moderate_low
	    $('.temp').removeClass('active');
	    $('.moderate_low').addClass('active');
	    $( "#riskScoreId" ).val(2);
      }
 	}

 	function drawFpInputSlider() {
 		var originalSavingsRate = angular.copy($scope.userFpData.savingsRate);
 		var originalAmountInvestedToMmf = angular.copy($scope.userFpData.fpmasterassumption.fpmiscassumptions.amountInvestedToMmf);
 		var originalRetirementAge = angular.copy($scope.userFpData.fpmasterassumption.fpmiscassumptions.retirementAge);
 		var userAge = angular.copy($scope.userFpData.age[0]);	//Current Age of user
 		//SavingsRate
 		$( "#slider-range-min" ).slider({
	      range: "min",
	      value: originalSavingsRate,//22,
	      min:0,//1,
	      max:100,
	      slide: function( event, ui ) {
	        $( "#age" ).val( "" + ui.value );	       
	      },
	      stop: function(event,ui){
	      	$scope.userFpData.savingsRate = ui.value;
	        var savingsRateVal = angular.copy($scope.userFpData.savingsRate);
	        $.post(
        		_gc_url_fpo_post_editSavingsRate,
        		{ savingsRate: savingsRateVal },
        		function(rdata) {
        			initFinancialPlannerData(rdata,'high_chart');
        		}
	        );
	      }
	    });
	    $( "#age" ).val( "" + $( "#slider-range-min" ).slider( "value" ) );

	    //Allocation to MMF
	    $( "#slider-range-min2" ).slider({
	      range: "min",
	      value: originalAmountInvestedToMmf,//46,
	      min:0,//1,
	      max:100,
	      slide: function( event, ui ) {
	        $( "#age2" ).val( "" + ui.value );
	       
	      },
	      stop: function(event,ui){
	      	$scope.userFpData.fpmasterassumption.fpmiscassumptions.amountInvestedToMmf = ui.value;
	        var allocationMMFVal = angular.copy($scope.userFpData.fpmasterassumption.fpmiscassumptions.amountInvestedToMmf);
	        $.post(
        		_gc_url_fpo_post_editAllocationToMMF,
        		{ allocationMMF: allocationMMFVal },
        		function(rdata) {
        			initFinancialPlannerData(rdata,'high_chart');
        		}
	        );
	      }
	    });	    
	    $( "#age2" ).val( "" + $( "#slider-range-min2" ).slider( "value" ) );
	    
	    //Retirement Age
	    $( "#slider-range-min3" ).slider({
	      range: "min",
	      value: originalRetirementAge,//60,
	      min:userAge,//1,
	      max:100,
	      slide: function( event, ui ) {
	        $( "#age3" ).val( "" + ui.value );	        
	      },
	      stop:function(event,ui){
	      	$scope.userFpData.fpmasterassumption.fpmiscassumptions.retirementAge = ui.value;
	        var retirementAgeVal = angular.copy($scope.userFpData.fpmasterassumption.fpmiscassumptions.retirementAge);
	        $.post(
        		_gc_url_fpo_post_editRetirmentAge,
        		{ retirementAge: retirementAgeVal },
        		function(rdata) {
        			initFinancialPlannerData(rdata,'high_chart');
        		}
	        );
	      }
	    });
	    $( "#age3" ).val( "" + $( "#slider-range-min3" ).slider( "value" ) );
 	}

 	drawFpLifeGoals = function(){
 		var myDiv= document.getElementsByClassName("goals_table");
	    var wrappedMyDiv = angular.element(myDiv);
	    

		for(var lifeIndex=0;lifeIndex < $scope.userFpData.lifeGoals.length;lifeIndex++){
			var randNo = Math.floor((Math.random() * 100000) + 1);
			var goalId = $scope.userFpData.lifeGoals[lifeIndex].goalDescriptionId;
			var goalDescription = $scope.userFpData.lifeGoals[lifeIndex].goalDescription;
			var freqId = $scope.userFpData.lifeGoals[lifeIndex].frequency;
			var freqDescription = $scope.userFpData.lifeGoals[lifeIndex].frequencyDesc;
			var yearofReal = $scope.userFpData.lifeGoals[lifeIndex].yearofRealization;
			var currentAmt = $scope.userFpData.lifeGoals[lifeIndex].estimatedAmount;
			var loan = $scope.userFpData.lifeGoals[lifeIndex].loanYesNo;

			wrappedMyDiv.append($compile(
	      	'<div id="added_life_goal_row'+randNo+'" class="goals_row body"><div class="column column1">'+
	        '<select id="select_life_goal_row_'+randNo+'" class="goal_select goalCol1 lifeGoalOption" ng-model="lifeGoal'+randNo+'" ng-init="lifeGoal'+randNo+' = \''+goalId+'\'">'+
	        '<option ng-repeat="lifeGoal in lifeGoalsList" value="{{lifeGoal.id}}">{{lifeGoal.name}}</option></select>'+
	        '<input type="text" class="other_input alphaSpaceOnly" maxlength="25" ng-if="lifeGoal'+randNo+' == \'0\'" ng-model="lifeGoalOther'+randNo+'" ng-init="lifeGoalOther'+randNo+' = \''+goalDescription+'\'" placeholder="Type Life Goal Here...">'+
	        '</div><div class="column column2">'+
	        '<select class="goal_select goalCol2" ng-model="lifeGoalFreq'+randNo+'" ng-init="lifeGoalFreq'+randNo+' = \''+freqId+'\'" ng-if="lifeGoal'+randNo+' == \'6\' || lifeGoal'+randNo+' == \'7\'">'+
	        '<option ng-repeat="lifeGoalsFrequency in lifeGoalsFrequencyList" value="{{lifeGoalsFrequency.id}}">{{lifeGoalsFrequency.name}}</option></select>'+
	        '</div><div class="column column3">'+
	        '<select class="goal_select goalCol3" ng-model="lifeGoalYear'+randNo+'" ng-init="lifeGoalYear'+randNo+' = \''+yearofReal+'\'"><option ng-repeat="x in lifeGoalsYearList">{{x}}</option></select></div>'+
	        '<div class="column column4"><div class="edit_graph_row"><div class="edit_graph_data"><div class="age1">'+
	        '<label for="lifeGoalPrice'+randNo+'"><i class="fa fa-inr"></i></label><input class="estim_amt" type="text" id="lifeGoalPrice-'+randNo+'" readonly style="border:0;">'+
	        '<div id="sliderprice-range-goal-estimate-amount-'+randNo+'" class="edit_graph_bar" ></div></div></div></div></div><div class="column column5"><div class="switch_tog">'+
	        '<div class="switch tab_controls"><ul class="nav nav-tabs" id="availLoan-'+randNo+'"><li class="active"><a data-toggle="tab">Savings</a></li>'+
	        '<li><a data-toggle="tab">Loan</a></li></ul></div></div>'+
	        '<div class="ed_del"><span id="delete_row_'+randNo+'" class="delete deleteLifeGoal"><a><i class="fa fa-times" aria-hidden="true"></i></a></span></div>'+
	        '</div></div>')($scope));
			 
		    $( "#sliderprice-range-goal-estimate-amount-"+randNo+"" ).slider({
		      range: "min",
		      value:currentAmt,//500000,
		      min:0,
		      max:100000000,  //Assigning max value amongst all life goals
	          step:1000,      //Assigning least step amongst all life goals
		      slide: function( event, ui ) {
		        $( "#lifeGoalPrice-"+randNo+"" ).val( format(ui.value) );
		      }
		    });
		    $( "#lifeGoalPrice-"+randNo+"" ).val(format($( "#sliderprice-range-goal-estimate-amount-"+randNo+"" ).slider( "value" )));
		    $('#delete_row'+randNo+'').click(function(){
		        $('#added_life_goal_row'+randNo+'').remove();
		    });	
		    //Toggle Loans or Savings
		    if(loan == 'Yes')
		    	jQuery('#availLoan-'+randNo+' a:last').tab('show');
		}	        
	    
	    //Bug-Fix : Sliders are not updating read only value
	      $( "[id^='sliderprice-range-goal-estimate-amount-']" ).each(function(index){
	          var splitStr = $(this).attr('id').split("-");
	          var insuranceIndex = splitStr[splitStr.length-1];          
	          $( "#sliderprice-range-goal-estimate-amount-"+insuranceIndex+"").slider({
	            range: "min",
	            value:500000,
	            min:0,
	            max:100000000,  //Assigning max value amongst all life goals
		        step:1000,      //Assigning least step amongst all life goals
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
 	
 	modifyFpLifeGoals = function(){
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
 	        }
 	        else if(this.value == 4 ) //Car   
 	        { 
 	          maxVal = 10000000;
 	          stepVal=50000;
 	        }
 	        else{
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
 	
 	$scope.saveFpLifeGoal = function(){
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
	    var loanArray = $("[id^='availLoan-']").find('li.active').map(function(){return this.innerText}).get();
	    for (var j = 0; j < loanArray.length; j++){
	      if(loanArray[j] == "Loan")
	        $scope.userFpData.lifeGoals[j].loanYesNo = 'Yes';
	      else
	        $scope.userFpData.lifeGoals[j].loanYesNo = 'No';      
	    }

	    //Life Goal Amount     
	    $('[id^=sliderprice-range-goal-estimate-amount]').each(function(index,element){ 
	        $scope.userFpData.lifeGoals[index].estimatedAmount =  parseInt($(this).slider('value')); 	        
	    });
	    // console.log($scope.userFpData.lifeGoals);
	    var finalJson = JSON.stringify(angular.copy($scope.userFpData.lifeGoals));
     	$.post(
        	_gc_url_fpo_post_editLifeGoals,
        	{ lifeGoals: finalJson },
        	function(rdata) {
        		initFinancialPlannerData(rdata,'high_chart');	    			
				$('html,body').scrollTop(0);                        
    	});

  	};

  	$scope.resetFpLifeGoals = function(){  
  		var delRowIdArr = $('.deleteLifeGoal').map(function(){
  				var rowID = this.id;
  				var splitStr = rowID.split('_');
  				rowID = splitStr[splitStr.length-1];
  				return rowID;
  			}).get();
  		for(var index=0;index < delRowIdArr.length;index++){
  			$('#added_life_goal_row'+delRowIdArr[index]).remove();
  		}

  		// $scope.userFpData.lifeGoals = [];
  		// drawFpLifeGoalSlider();
		console.log('Reset Life Goals');
	};

	$scope.getGoalDescriptionId = function(lifeGoalArr, age, goalFullfilled){
		var goalId = $.grep(lifeGoalArr, function(e){ return e.age == age; })[0].goalDescriptionId;
		var goalIconImage;		
		if(goalFullfilled)	//Show green lifegoal icon
			goalIconImage = "images/lifeGoalIcons/goal_icon_g"+goalId+".png";
		else
			goalIconImage = "images/lifeGoalIcons/goal_icon_"+goalId+".png"; 
		return goalIconImage;
	}
}]);
