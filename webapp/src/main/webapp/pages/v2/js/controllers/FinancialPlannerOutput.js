app.controller('FinancialPlannerOutput', ['$scope', function($scope) {
	//$scope.riskProfileQuestions = data;
	$scope.errorTitle= "Error";
	$scope.errorBody="Please answer the following questions";
	

	//userDataInput = JSON.parse(localStorage.getItem("userDataInput"));
	/*userDataInput = JSON.parse(localStorage.getItem("userDataOutput"));
	userDataOutput = JSON.parse(localStorage.getItem("userDataOutput"));
	*/
	/*$scope.lifeGoalsJson = userDataOutput.lifeGoals;*/
	
	setTimeout(function(){ 
		$.ajax({
			type: "GET",
			url: _gc_url_fpo_get_assetExpenditureChart,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			  success: function(data){
				 /* console.log('bhagashree ->'+ data);
				  console.log(data);*/
				  userDataOutput = data;
				  userDataInput = data;
				  $scope.lifeGoalsJson = userDataOutput.lifeGoals;
				  $scope.$apply();
			}
		});
		
		$("#displayOnlyUserName").text($("#userFirstName").val());
		 $('.calender').datetimepicker({
		    	viewMode: 'years',
		    	format: 'YYYY'
	    });
        
        
        addEventToLoanDropDown();
        $('.clFinalYear').datetimepicker();
	    $('.lgYear').datetimepicker({
	    	viewMode: 'years',
	    	format: 'YYYY'
	    });
	},0);
	
	setTimeout(function(){ 
		console.log('this is in timeout');
		var assetPointer;
		var assetChart;
		
		$("[name='my-checkbox']").bootstrapSwitch({
			'size':'mini',
			'onText':'Yes',
			'offText':'No'
		});
		$('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
		
		var riskProfileIdDisplay = 0; 
		var url = window.location.href;
    	var urlpart =  url.split("rps=");
    	var next =  urlpart[1];
    	if(typeof next === "undefined"){
    		//do nothing
    		riskProfileIdDisplay = userDataInput.riskGroupId*20-10;
    	} else {
    		riskProfileIdDisplay = next;
    	}
        $("#rsSalarySavingsRate").roundSlider({
            sliderType: "min-range",
            editableTooltip: false,
            radius: 105,
            width: 16,
            value: riskProfileIdDisplay,
            handleSize: 0,
            handleShape: "square",
            circleShape: "half-top",
            startAngle: 360,
            showTooltip : false,
            tooltipFormat: "changeTooltip",
            change : function(e){
            	//console.log(e.value);
            	riskScoreValue = (parseInt(e.value/20) + 1);
            	if(riskScoreValue > 5 ) {riskScoreValue=5};
            	$.post(
            		_gc_url_fpo_post_editRiskScore,
	        		{ 
            			riskScore : riskScoreValue
            		},
	        		function(rdata) {
	        			processChartData(rdata)
	        		}
	        	);
            }
        });
 			
		var rangeSalarySpouse = $("#range_score");
		rangeSalarySpouse.ionRangeSlider({
	         min:0,
	         max:100,
	         from:Math.round(userDataInput.savingsRate),
	         grid: true,
	         grid_num: 5,
	         onChange: function(data){
	        	 //riskoMeterChart.series[0].points[0].update(data.from);
	        	 $('#value_score').html("&nbsp;" + data.from + "%");
	         },
	         onFinish: function (data) {
	        	 $.post(
	        		_gc_url_fpo_post_editSavingsRate,
	        		{ savingsRate: data.from },
	        		function(rdata) {
	        			processChartData(rdata)
	        		}
	        	 );
	         }
	     });
		$('#value_score').html(Math.round(userDataInput.savingsRate) + "%");
	    
		sliderSalarySpouse = rangeSalarySpouse.data("ionRangeSlider");
		     

	     $("#range_allocationMMF").ionRangeSlider({
	         min:0,
	         max:100,
	         from:userDataOutput.fpmasterassumption.fpmiscassumptions.amountInvestedToMmf,
	         grid: true,
	         grid_num: 5,
	         onChange: function(data){
	        	 $('#value_allocationMMF').html("&nbsp;" + data.from + "%");
	         },
	         onFinish: function (data) {
	        	 $.post(
	        		_gc_url_fpo_post_editAllocationToMMF,
	        		{ allocationMMF: data.from },
	        		function(rdata) {
	        			processChartData(rdata)
	        		}
	        	 );
	         }
	     });
	     
	     $('#value_allocationMMF').html("&nbsp;" + userDataOutput.fpmasterassumption.fpmiscassumptions.amountInvestedToMmf + "%");
	     
	     
	     $("#range_RetirementAge").ionRangeSlider({
	         min:0,
	         max:userDataOutput.fpmasterassumption.fpmiscassumptions.lifeExpectancy,
	         from:userDataOutput.fpmasterassumption.fpmiscassumptions.retirementAge,
	         grid: true,
	         grid_num: 5,
	         onChange: function(data){
	        	 //console.log('hiyaa')
	        	 //riskoMeterChart.series[0].points[0].update(data.from);
	        	 $('#value_RetirementAge').html("&nbsp;" + data.from);
	         },
	         onFinish: function (data) {
	        	 $.post(
	        		_gc_url_fpo_post_editRetirmentAge,
	        		{ retirementAge: data.from },
	        		function(rdata) {
	        			processChartData(rdata)
	        		}
	        	 );
	         }
	     });
	     $('#value_RetirementAge').html("&nbsp;" + userDataOutput.fpmasterassumption.fpmiscassumptions.retirementAge);
	     $(".irs-single").css("display","block");		     
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
   			   // use .eq() within a jQuery object to navigate it by Index
   				//if(arrloan_description.eq(i).val() == ""){
   				//	goalDescriptionId = 1
   				//}
   				//else if(arrloan_description.eq(i).val() == ""){
   				//	
   				//}
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

   			/*finalJson ='"lifeGoals":['+tempJson+'],';
   			console.log(finalJson);
			$.ajax({
				type: 'POST',
				url: _gc_url_fpo_post_editLifeGoals,
				data: finalJson,
				success: function(result){
					//console.log(result);
					processChartData(result);
				},
				error: function(xhr, textStatus, error){
					console.log(xhr.statusText);
					console.log(textStatus);
					console.log(error);
				}
			});*/
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
	editResponse = function(){
		var url = window.location.href;
   		var urlpart =  url.split("src=");
   		var src = urlpart[1];
   		if(typeof src === "undefined"){
   			urlpart =  url.split("viewPlanner=");
   			src = urlpart[1];
   			if(typeof src === "undefined"){
   				window.location.href = "/faces/pages/v2/userProfile.jsp";
   			}
   			if(src.startsWith("true")){
   				window.location.href = "/faces/pages/v2/userProfile.jsp?faces-redirect=true&viewPlanner=true";
   			}   			
   		} else {
   			if (src.startsWith("ps")){
   				window.location.href = "/faces/pages/v2/userProfile.jsp?faces-redirect=true&src=ps";
   			} else if (src.startsWith("fpw")){
   				window.location.href = "/faces/pages/v2/userProfile.jsp?faces-redirect=true&src=fpw";
   			} else {
   				window.location.href = "/faces/pages/v2/userProfile.jsp";
   			}
   		}
	}
	saveAndSubmit = function(){
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
   	   		    							window.location.href = "/faces/pages/v2/rpwizard.jsp?faces-redirect=true&next=upw";
   	   		    						}
   	   		    						
   	   		    					}).fail(function (response){
   	   		    					alert(response);
   	   		    					});
   	   		    		} else if (src.startsWith("rpo")){
   	   		    			window.location.href = "/faces/pages/v2/invProfileWizard.jsp?faces-redirect=true";
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
}]);

//riskScore
//fpmasterassumptionEdited
//lifeGoals