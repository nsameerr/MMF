app.controller('RiskProfile', ['$scope', function($scope) {
	//$scope.riskProfileQuestions = data;
	$scope.errorTitle= "Error";
	$scope.errorBody="Please answer the following questions";
	
	var totalSlides = 10;
    var arrVisitedSlids = [];
    var sliderValue =0;
    var qids = [1,2,3,4,5,6,7,8,9,10];
	
    setTimeout(function(){ 
    	displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();
        
    	for(ind=0;ind<totalSlides;ind++)
		{
    		//console.log(ind);
			arrVisitedSlids[ind]=false;
		}
    	
     	arrVisitedSlids[0]=true;

     	$('#spc0').on('click', function () {$('#myCarousel').carousel(0);});
     	$('#spc1').on('click', function () {$('#myCarousel').carousel(1);});
     	$('#spc2').on('click', function () {$('#myCarousel').carousel(2);});
     	$('#spc3').on('click', function () {$('#myCarousel').carousel(3);});
     	$('#spc4').on('click', function () {$('#myCarousel').carousel(4);});
     	$('#spc5').on('click', function () {$('#myCarousel').carousel(5);});
     	$('#spc6').on('click', function () {$('#myCarousel').carousel(6);});
     	$('#spc7').on('click', function () {$('#myCarousel').carousel(7);});
     	$('#spc8').on('click', function () {$('#myCarousel').carousel(8);});
     	$('#spc9').on('click', function () {$('#myCarousel').carousel(9);});
     	$('#spc10').on('click', function () {$('#myCarousel').carousel(10);});
     	$('#spc11').on('click', function () {$('#myCarousel').carousel(11);});
     	
         
         $("#myCarousel").on('slid.bs.carousel', function () {
             //console.log($("#myCarousel").find('.active').index());
             curSlide = $("#myCarousel").find('.active');
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
             }
             
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
             
             $('#spc'+curSlide.index()).addClass('active');
             
             //$('.circle')[curSlide.index()*2].addClass('active');
             
             $('.circle').each(function () {
            	    //console.log($(this).index());
            	    
			 });
             
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
	         from:0,
	         grid: true,
	         grid_num: 5,
	         onChange: function(data){
	        	 riskoMeterChart.series[0].points[0].update(data.from);
	        	 
	        	 if(data.from > -1 && data.from <21){
	        		 sliderValue = 35
	        	 }
	        	 else if(data.from > 20 && data.from <41){
	        		 sliderValue = 36
	        	 }
	        	 else if(data.from > 40 && data.from <61){
	        		 sliderValue = 37
	        	 }
	        	 else if(data.from > 60 && data.from <81){
	        		 sliderValue = 38
	        	 }
	        	 else if(data.from > 80){
	        		 sliderValue = 39
	        	 }
	         },
	         onFinish: function(data){
	        	 riskoMeterChart.series[0].points[0].update(data.from);
	        	 
	        	 if(data.from > -1 && data.from <21){
	        		 sliderValue = 35
	        	 }
	        	 else if(data.from > 20 && data.from <41){
	        		 sliderValue = 36
	        	 }
	        	 else if(data.from > 40 && data.from <61){
	        		 sliderValue = 37
	        	 }
	        	 else if(data.from > 60 && data.from <81){
	        		 sliderValue = 38
	        	 }
	        	 else if(data.from > 80){
	        		 sliderValue = 39
	        	 }
	         }
	     });
	     sliderSalarySpouse = rangeSalarySpouse.data("ionRangeSlider");
	     $(".irs-single").css("display","block");
	     
	     
	     
    	
    	$(':radio').on('ifChecked', function(event){
        	//console.log('onclick');
            //$('#myCarousel').carousel("next");
            
            if (!$("#myCarousel").find('.active').is( ':last-child' )) 
         	{
         		//console.log('if');
         		$('#myCarousel').carousel("next");
            }
         	else
       		{
       			//console.log('else');
       		}
        });
    	
    	
    	$('#btnSubmit').on('click', function(event){
	    	//console.log('onclick');
    		showLoading();
	    	unansweredQuestions = "";
	    	
	    	var postData = "";
	    	for(i=0;i<totalSlides;i++)
     		{
            	if($('input[name=Q'+i+']').attr('type')=='radio')
            	{
            		if($('input[name=Q'+i+']:checked').val() > -1)
         			{
            			postData = postData + '{"questionId" : '+qids[i]+', "answerId" : '+$('input[name=Q'+i+']:checked').val()+'},'
            			
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
	    	postData = postData ;//+ '{"questionId" : 20, "answerId" : '+sliderValue+'}'
	    	postData = '[' +postData + ']';
	    	postData = postData.replace(',]',']');
	    	if(unansweredQuestions=="")
    		{
	    		console.log(postData);
	    		clevertap.event.push("Investor Submitted Risk Profile");
	    		var url = window.location.href;
	    		var urlpart =  url.split("rpsrc=");
	    		var rpsrc = urlpart[1];
	    		if (rpsrc.startsWith("ps") || rpsrc.startsWith("rpw") || rpsrc.startsWith("upw")){
	    			// riskprofile src is portfolio search make partial entry of riskprofile 2/5 records
	    			$.ajax({
		    			  type: "POST",
		    			  url: _gc_url_rp_post_riskProfile_partial,
		    			  data: postData,
		    			  //dataType: 'json',
		    			  contentType:"application/json; charset=utf-8",
		    			  success: function(){
		    				  if (rpsrc.startsWith("upw"))
		    					  window.location.href = _gc_url_rp_redirect_riskProfileoutput + "?next=upw";
		    				  else
		    					  window.location.href = _gc_url_rp_redirect_riskProfileoutput;
		    			  }
		    			});
	    		} else if (rpsrc == "ac") {
	    			// riskprofile src is advisory contract make complete entry of riskprofile 5/5 records
	    			$.ajax({
		    			  type: "POST",
		    			  url: _gc_url_rp_post_riskProfile,
		    			  data: postData,
		    			  //dataType: 'json',
		    			  contentType:"application/json; charset=utf-8",
		    			  success: function(){
		    				  window.location.href = _gc_url_rp_redirect_riskProfileoutput; 
		    			  }
		    			});
	    		}
	    		
    		}
	    	else
	    	{	hideLoading();
	    		$('#modalErrorTitle')[0].innerHTML = "Error"
    		    $('#modalErrorBody')[0].innerHTML = "Please answer the following questions <br> " +unansweredQuestions;
    	    	$('#modalShowError').modal('show');
	    	}
	    });
    	
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
    	
    	function changeTooltip(e) {
    	    var val = e.value, speed;
    	    if (val < 20) speed = "Slow";
    	    else if (val < 40) speed = "Normal";
    	    else if (val < 70) speed = "Speed";
    	    else speed = "Very Speed";

    	    return val + " km/h" + "<div>" + speed + "<div>";
    	}
    	
    	
    	
        
        $('#divContent').css("display","block");
        $('#divLoading').css("display","none");
        //alert();
    }, 1000);
}]);


function skip(){
	$('#modalSkip').modal('show');
}