app.controller('FinancialPlannerOutputChart', ['$scope', function($scope) {
	//$scope.riskProfileQuestions = data;
	$scope.errorTitle= "Error";
	$scope.errorBody="Please answer the following questions";
	
	setTimeout(function(){ 
		var assetPointer;
		var assetChart;
		userDataInput = JSON.parse(localStorage.getItem("userDataInput"));
		userDataOutput = JSON.parse(localStorage.getItem("userDataOutput"));

        $("#rsSalarySavingsRate").roundSlider({
            sliderType: "min-range",
            editableTooltip: false,
            radius: 105,
            width: 16,
            value: userDataInput.riskGroupId*20-10,
            handleSize: 0,
            handleShape: "square",
            circleShape: "half-top",
            startAngle: 360,
            showTooltip : false,
            tooltipFormat: "changeTooltip",
            change : function(e){
            	//console.log(e.value);
            	$.post(
            		_gc_url_fpo_post_editRiskScore,
	        		{ riskScore: (parseInt(e.value/20) + 1)  },
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
	         max:100,
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
		       
		     
	},1000);
	
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
	
	xgetSelectBox = function(containerID,minVal, maxVal, selectedVal){
		str = '<select>';
		for (i=minVal; i<=maxVal; i++){
			if(i==selectedVal){
				str = str + '<option selected>'+i+'</option>';
			}
			else{
				str = str + '<option>'+i+'</option>';
			}
		}
		str = str + '</select>';
		$(containerID).html(str);
	}
	
	getSelectBox = function(containerID, objectID, objectClass, minVal, maxVal, selectedVal){
		str = '<select id="'+objectID+'" class="'+objectClass+'">';
		for (i=minVal; i<=maxVal; i++){
			if(i==selectedVal){
				str = str + '<option selected>'+i+'</option>';
			}
			else{
				str = str + '<option>'+i+'</option>';
			}
		}
		str = str + '</select>';
		$(containerID).html(str);
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
   	   				tempJson=tempJson + '"goalDescriptionId":"' + arrloan_description.eq(i).val() + '",';
   	   				tempJson=tempJson + '"goalDescription":"' + arrloan_description.eq(i).find('option:selected').text()+'",';   			   
   	   				tempJson=tempJson + '"yearofRealization":'+arrYearOfRealization.eq(i).val()+',';   			   
   	   				tempJson=tempJson + '"loanYesNo":'+arrLoanYesNo.eq(i).is(':checked')+',';   			   
   	   				tempJson=tempJson + '"frequency":'+arrFrequency.eq(i).val().replace(/,/g , '')+',' ;  			   
   	   				tempJson=tempJson + '"estimatedAmount":'+arrAmount.eq(i).val().replace(/,/g , '')+',';		   
   	   				tempJson=tempJson + '"age": null';
   	   				tempJson =tempJson+'}';	
   				}
   			}

   			finalJson ='"lifeGoals":['+tempJson+'],';
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
			});
	}
	
	
	
	
	makeJsonAdvanceSettings = function(){
		finalJson = ''
		tempJson = ''
		tempJson = '"assumptionId":null, "retirementAge" : '+$('#range_RetirementAge').val()+
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
			tempJson = tempJson + 	'{"loanAssumptionId": '+$('.loanForID').eq(i).val()+
									',"loanTypeDescription": "'+$('.loanForDesc').eq(i).val()+
									'","loanDuration": '+$('select[class="loanNumberOfYears"]').eq(i).val()+
									',"interestRate": '+$('select[class="loanIntrestRate"]').eq(i).val()+
									',"downPaymentPercent": '+$('select[class="loanDownpayment"]').eq(i).val()+'}'
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
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
}]);

//riskScore
//fpmasterassumptionEdited
//lifeGoals