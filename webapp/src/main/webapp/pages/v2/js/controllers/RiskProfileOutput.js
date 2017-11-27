app.controller('RiskProfileOutput', ['$scope', function($scope) {
	$scope.page = {
			title: 'Risk profile output'
	};
	$scope.riskScore=0;
	var riskoMeterPointer;
	var riskoMeterChart;
	
	setTimeout(function(){ 
		displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();
		/*$(function () {
			$.get("[getRiskProfileUrl]", function(responseData, status){
		        if(status == "success"){
		        	
		        }
		    });
		});*/
		
/*		$("#rsSalarySavingsRate").roundSlider({
            sliderType: "min-range",
            editableTooltip: false,
            readOnly: true,
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
        });*/
		
		
		
		
	}, 1000);
	
	
	updateCharts = function(){
		
		$.ajax({url: _gc_url_rp_get_riskProfileOutput, 
			success: function(result,status,xhr){
				console.log(result);
				var jsonString = JSON.stringify(result);
				console.log(jsonString);

				jsonString = jsonString.replace(/recommendedAssetAllocation/g, 'content');
				jsonString = jsonString.replace(/assetClassName/g, 'label');
				jsonString = jsonString.replace(/percentageAllocation/g, 'value');
				jsonString = jsonString.replace(/colourCode/g, 'colourCode');
				
				result = JSON.parse(jsonString);
				console.log(result);
				
				var pie = new d3pie("divIRecommendedPortfolio", {
					"size": {
						"canvasHeight": 280,
						"canvasWidth": 500,
						"pieOuterRadius": "80%"
					},
					"data": {
						"content": result.content
					},
					"labels": {
						"outer": {
							"pieDistance": 27
						},
						"mainLabel": {
							"fontSize": 11
						},
						"percentage": {
							"color": "#ffffff",
							"decimalPlaces": 0
						},
						"value": {
							"color": "#adadad",
							"fontSize": 11
						},
						"lines": {
							"enabled": true
						},
						"truncation": {
							"enabled": true
						}
					},
					"effects": {
						"pullOutSegmentOnClick": {
							"effect": "linear",
							"speed": 400,
							"size": 8
						}
					}
				});
				
				$scope.riskScore = result.riskScore;
				$("#rsSalarySavingsRate").roundSlider({
		            sliderType: "min-range",
		            editableTooltip: false,
		            readOnly: true,
		            radius: 105,
		            width: 16,
		            value: result.riskScore,
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
				
				if(result.riskScore < 10){
            		$('#spanRiskScore').html('0' + result.riskScore);
            	}
            	else{
            		$('#spanRiskScore').html(result.riskScore);
            	}
				$('#riskProfileDescription').html(result.riskProfileDescription);
				
			},
			error: function(xhr,status,error){
				console.log(status + ">>" + error)
			}
		});
	}
	
	
	$(document).ready(function(){
		
		setTimeout(function(){ 
			var url = window.location.href;
	    	var urlpart =  url.split("next=");
	    	var next =  urlpart[1];
	    	if(typeof next === "undefined"){
	    		//do nothing
	    	} else {
	    		if (next.startsWith("upw")){
	    			$('#btnFooterDashboard').hide();
	    			$('#btnHeaderDashboard').hide();
	    			$('#btnNext').show();
	    		}
	    	}
		},100);
		
	});	
	
	next = function(){
		//window.location.href = "/faces/pages/v2/invProfileWizard.jsp?faces-redirect=true";
		var settings = {
  					"async": true,
  					"crossDomain": true,
  					"url": _gc_url_update_fp_using_rp,
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
  						if (res == "Error:fpupdatefailed" || res == "false"){
  							window.location.href = _gc_url_portfolio_search_ui;
  						} else if (res == "true") {
  							//window.location.href ="/faces/pages/v2/financialPlannerOutputFD.jsp?faces-redirect=true&src=rpo&rps="+ $scope.riskScore;
  							window.location.href ="/faces/pages/v3/financialPlannerOutput.jsp?faces-redirect=true&src=rpo&rps="+ $scope.riskScore;
  						}
  						
  					}).fail(function (response){
  					alert(response);
  					});
	}
}]);
