app.controller('RiskProfileOutput', ['$scope', function($scope) {
	$scope.page = {
			title: 'Risk profile output'
	};
	$scope.riskScore=0;
	$scope.riskData;

	$scope.initRisk = function(){
//		console.log('Init Invoked');
		$.ajax({url: _gc_url_rp_get_riskProfileOutput, 
			success: function(result,status,xhr){
				// console.log(result);
				var jsonString = JSON.stringify(result);
				// console.log(jsonString);

				jsonString = jsonString.replace(/recommendedAssetAllocation/g, 'content');
				jsonString = jsonString.replace(/assetClassName/g, 'name');
				jsonString = jsonString.replace(/percentageAllocation/g, 'y');
				jsonString = jsonString.replace(/colourCode/g, 'color');
				
				result = JSON.parse(jsonString);
				$scope.portfolioPieChartHC = result.content;
				$scope.drawPie();
				$scope.riskData = result;
				$scope.$apply();
//				console.log(result);

				$scope.riskScore = result.riskScore;
				// console.log('Risk Score');
				// console.log(result.riskScore);

				$('#riskProfileDescription').html(result.riskProfileDescription);
				
			},
			error: function(xhr,status,error){
				console.log(status + ">>" + error)
			}
		});
	}
	var riskoMeterPointer;
	var riskoMeterChart;
	
	// console.log('controller ....');

	setTimeout(function(){ 
		displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();
		//Risk Chart
		drawRiskProfileChart($scope.riskScore,'gauge_section', 'edit_inputgraphtxt');	
		
	}, 1000);
	
	
	updateCharts = function(){
		
		$.ajax({url: _gc_url_rp_get_riskProfileOutput, 
			success: function(result,status,xhr){
				console.log(result);
				var jsonString = JSON.stringify(result);
				console.log(jsonString);

				jsonString = jsonString.replace(/recommendedAssetAllocation/g, 'content');
				jsonString = jsonString.replace(/assetClassName/g, 'name');
				jsonString = jsonString.replace(/percentageAllocation/g, 'y');
				jsonString = jsonString.replace(/colourCode/g, 'color');
				
				result = JSON.parse(jsonString);
				console.log(result);
				$scope.portfolioPieChartHC = result.content;
				$scope.drawPie();
				/*
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
				*/
				$scope.riskScore = result.riskScore;
				/*$("#rsSalarySavingsRate").roundSlider({
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
            	*/
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
	    			// $('#btnFooterDashboard').hide();
	    			// $('#btnHeaderDashboard').hide();

	    			$('#btnBack').hide();
	    			$('#btnContinue').hide();
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
  							window.location.href =_gc_url_baseUrl+"/faces/pages/v3/financialPlannerOutput.jsp?faces-redirect=true&src=rpo&rps="+ $scope.riskScore;
  						}
  						
  					}).fail(function (response){
  					alert(response);
  					});
	}

  goBackToRiskProfile=function()
  {

  	window.location.href = 'riskProfile.jsp?faces-redirect=true&rpsrc=ps'; // Removed from href /pages/v3/ and added rpsrc param
  }

  goToDashBoard=function()
  {
  	window.location.href='/faces/pages/investordashboard.xhtml?faces-redirect=true'
  }

			$scope.drawPie = function(){

       		<!-- Circle Pie Chart Script -->

			$(document).ready(function(){
			jQuery.size = jQuery(window).width(); 
			if(jQuery.size >= 768 )
			{
			Highcharts.chart('circle_chart', {
				chart: {
					height: 300,
					type: 'pie',
					plotBackgroundColor: null,
			        plotBorderWidth: null,
			        plotShadow: false,
				},
			    title: {
					text: '',//'Cash<br/>100%',
			        align: 'center',
			        verticalAlign: 'middle',
			        y: 0,
					x: -140
			    },
			    tooltip: {
					enabled: false
			    },
				plotOptions: {
					pie: {
			            dataLabels: {
			                enabled: false,
			                distance: 0,
			                style: {
			                    fontWeight: 'bold',
			                    color: 'transparent'
			                }
						},
			            borderWidth: 2,
						startAngle: 0,
			            endAngle: 360,
			            center: ['50%', '50%'],
						showInLegend: true,
			   			allowPointSelect: false,
			   			point:{
			       			events : {
			        			legendItemClick: function(e){
			            			e.preventDefault();
			        			}
			       			}
			   			}
			        },
					series: {
			            states: {
			                hover: {
			                    enabled: false
			                }
			            }
			        }
			    },
				legend: {
					enabled: true,
					layout: 'vertical',
			        align: 'right',
			        width: 260,
			        verticalAlign: 'middle',
					useHTML: true,
			        labelFormatter: function() {
			        	return '<div class="leg_blk"><span class="perc_blk" style="background: ' + this.color + '">' + this.y + '%</span><span class="perc_blk_cnt">' + this.name + '</span></div>';
			        }
				},
				series: [{
			        name: 'Cash',
			        innerSize: '67%',
			        data: $scope.portfolioPieChartHC
			    }]
			});		
			}
			else if(jQuery.size<= 767 )
			{
			Highcharts.chart('circle_chart', {
				chart: {
					height: 400,
					type: 'pie',
					plotBackgroundColor: null,
			        plotBorderWidth: null,
			        plotShadow: false,
				},
			    title: {
					text: 'Cash<br/>100%',
			        align: 'center',
			        verticalAlign: 'middle',
			        y: -105,
					x: 0
			    },
			    tooltip: {
					enabled: false
			    },
				plotOptions: {
					pie: {
			            dataLabels: {
			                enabled: false,
			                distance: 0,
			                style: {
			                    fontWeight: 'bold',
			                    color: 'transparent'
			                }
						},
			            borderWidth: 2,
						startAngle: 0,
			            endAngle: 360,
			            center: ['50%', '50%'],
						showInLegend: true,
			   			allowPointSelect: false,
			   			point:{
			       			events : {
			        			legendItemClick: function(e){
			            			e.preventDefault();
			        			}
			       			}
			   			}
			        },
					series: {
			            states: {
			                hover: {
			                    enabled: false
			                }
			            }
			        }
			    },
				legend: {
					enabled: true,
					align: 'center',
			        width: 100+'%',
					itemWidth: 100+'%',
			        verticalAlign: 'bottom',
					useHTML: true,
			        labelFormatter: function() {
			        	return '<div class="leg_blk"><span class="perc_blk" style="background: ' + this.color + '">' + this.y + '%</span><span class="perc_blk_cnt">' + this.name + '</span></div>';
			        }
				},
				series: [{
			        name: 'Cash',
			        innerSize: '67%',
			        data: $scope.portfolioPieChartHC
			    }]
			});		
			}
			});

			/*<!-- End -->*/

       } // pie draw end





}]);