<%@ page import="java.io.*,java.util.*" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<!doctype html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
        	body{
        	padding-top: 55px;
        	padding-bottom: 200px;
        	}
        </style>
        <link rel="stylesheet" href="css/bootstrap-theme-paper.min.css">
        <link rel="stylesheet" href="css/bootstrap-wrapper.css">
        
        <link rel="stylesheet" href="css/iCheck.css" />
        <link rel="stylesheet" href="css/ion.rangeSlider.css"/>
        <link rel="stylesheet" href="css/ion.rangeSlider.skinHTML5.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css"/>
        <link href="https://cdn.jsdelivr.net/jquery.roundslider/1.0/roundslider.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link href="css/bootstrap-switch.css" rel="stylesheet">
        
        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="css/SelectBoxIt.css" />
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
        
        <link rel="stylesheet" href="css/main.css">
		<link rel="stylesheet" href="css/spinner.css">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
        <script src="js/vendor/bootstrap.min.js"></script>
        <script src="js/vendor/angular.min.js"></script>
        
        <script src="http://code.highcharts.com/stock/highstock.js"></script>
        <script src="http://code.highcharts.com/stock/highcharts-more.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script src="https://rawgithub.com/highslide-software/draggable-points/master/draggable-points.js"></script>
        
		<script src="https://code.highcharts.com/modules/exporting.js"></script>
		<script src="https://code.highcharts.com/modules/drilldown.js"></script>
        <script src="js/vendor/iCheck.js"></script>
        <script src="js/vendor/ion.rangeSlider.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.jsdelivr.net/jquery.roundslider/1.0/roundslider.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/js/bootstrap-select.min.js"></script>
        <script src="js/vendor/bootstrap-switch.js"></script>
        <script src="http://gregfranko.com/jquery.selectBoxIt.js/js/jquery.selectBoxIt.min.js"></script>
        <script src="js/vendor/moment.js"></script>
        <script src="js/vendor/bootstrap-datetimepicker.min.js"></script>
        <script src="js/vendor/autoNumeric-min.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
        <script src= "js/spinner.js"></script>
        <script src= "js/clevertap.js"></script>
    </head>
    <body  ng-app="appMMF" ng-controller="UserProfile">
        <noscript>
          <iframe src="//www.googletagmanager.com/ns.html?id=GTM-WV7G82"
                  height="0" width="0" style="display:none;visibility:hidden">
            </iframe>
        </noscript>
        <script>
          (function(w, d, s, l, i) {
              w[l] = w[l] || [];
              w[l].push({'gtm.start':
              new Date().getTime(), event: 'gtm.js'});
              var f = d.getElementsByTagName(s)[0],
              j = d.createElement(s), dl = l !== 'dataLayer' ? '&amp;l=' + l : '';
              j.async = true;
              j.src = '//www.googletagmanager.com/gtm.js?id=' + i + dl;
              f.parentNode.insertBefore(j, f);
          })(window, document, 'script', 'dataLayer', 'GTM-WV7G82');        
        </script>
        <!-- End Google Tag Manager -->    
	    <mmf-header></mmf-header>
		<%
    		Map<String, Object> storedValues =null;
    		String userFirstName = "";
			if(session != null){
				if(session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userFirstName = (String)storedValues.get("userFirstName");
				}else{
					response.sendRedirect("faces/index.xhtml");
				}
			}
		%>
		<input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
		<div id="loadingOverlay" style="display:none">
        	<div class="rippleSpinner">
           		<img src="img/gears.svg"/> 
        	</div>
    	</div>	
	    <div class="container-fluid" >
			<style>
				table{
					width: 100%;
				}
				
				.carousel.item{
					min-height: 500px
				}
				
				.txtRupee{
					background-image: url('img/icon_rupee.png');
					background-repeat: no-repeat;
					background-position: left center; 
					background-size: auto 20px;
					padding-left: 25px !important;
					padding-bottom: 30px;
				}
				.calender{
					background-image: url('img/icon_calender.png');
					background-repeat: no-repeat;
					background-position: right center; 
					background-size: auto 20px;
					padding-right: 25px !important;
					padding-bottom: 30px;
				}
				
				.item{
					min-height: 600px !important;
				}
				
				.clFinalYear{
					width: 160px;
			   		text-align: center;
				}
				
				#tableTotalAssets td{
					padding-right:10px !important;
					font-size: 16px;
				}
				
				
				#tblLifeInsuaranceDetails td{
					padding-right:10px !important;
					font-size: 16px;
				}
				#tblLifeInsuaranceDetails th{
					padding-right:10px !important;
					font-size: 16px;
					border-bottom: 1px solid black
				}
				
				
				#tblHealthInsuaranceDetails td{
					padding-right:10px !important;
					font-size: 16px;
				}
				#tblHealthInsuaranceDetails th{
					padding-right:10px !important;
					font-size: 16px;
					border-bottom: 1px solid black
				}
				
				#tblTolarate{
					width: 400px !important;
					border-spacing: 10px;
			    	border-collapse: separate;
			    	margin-left: 40px;
				}
				
				#tblTolarate th{
					padding:5px !important;
					font-size: 16px;
				}
				
				#tblTolarate td{
					padding:5px !important;
					font-size: 16px;
				}
				
				
				
				#divRiskProfile{
					background-image:url('img/riskometer_bg.png');
					background-size: 500px;
					background-repeat: no-repeat;
					background-position: center;
				}	
					
				#rsSalarySavingsRate .rs-handle  {
				    background-color: transparent;
				    border: 8px solid transparent;
				    border-right-color: black;
				    margin: -6px 0px 0px 14px !important;
				    border-width: 6px 104px 6px 4px;
				}
				#rsSalarySavingsRate .rs-handle:before  {
				    display: block;
				    content: " ";
				    position: absolute;
				    height: 22px;
				    width: 22px;
				    background: black;
				    right: -11px;
				    bottom: -11px;
				    border-radius: 100px;
				}
				#rsSalarySavingsRate .rs-tooltip  {
				    top: 120%;
				    font-size: 20px;
				}
				#rsSalarySavingsRate .rs-tooltip div  {
				    text-align: center;
				    background: orange;
				    color: white;
				    border-radius: 4px;
				    padding: 1px 5px 2px;
				    margin-top: 4px;
				}
				#rsSalarySavingsRate .rs-range-color  {
				    background-color: #DB5959;
				}
				#rsSalarySavingsRate .rs-path-color  {
				    background-color: #F0C5C5;
				}
				
				#rsSalarySavingsRate{
					margin-top:9px;
				}
				
				.rs-range-color{
					background-color: #7BDB7B !important;
				}
				.rs-path-color{
					background-color: #ECECEC !important;
				}
				
				.rs-control{
					display: inline-block;
				}
				
				.rs-inner {
				display:none !important;
					border:0px !important;
				}
				
				.rs-container{
					background-color: rgba(0,0,0,0)  !important;
					border:0px !important;
				}
				
				.rs-inne{
				
					background-color: rgba(0,0,0,0)  !important;
					border:0px !important;
				}
				.rs-block{
					background-color: rgba(0,0,0,0)  !important;
					border:0px !important;
				}
				
				.rs-path{
				
					background-color: rgba(0,0,0,0)  !important;
					border:0px !important;
				}
				
				.rs-handle{
					margin-top: 20px !important
				}
				
				.rs-seperator{
					border: 0px;
				}
						
			</style>
			
			<div class="row-fluid">
				<h2 class="page-title"> Financial Planner Questionnaire <span class="pull-right"><h6 style="display:none"><a href="userRegistration.html">skip</a></h6></span></h2>
			</div>
			<div id="divContent">
				<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
					<div class="carousel-inner" role="listbox">
						<!-- Carousal item Q0 start -->
						<div id="cItem0" class="item active container-fluid" >
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="true">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet "></span>
								</div>
								<div class="block">
									<div class="question">What is your gender?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<table style="width:200px;">
										<tr>
											<td>
												<div class="input-group">
													<span class="input-group-addon"><input type="radio"  id="Q0A0" name="Q0" value="male" ng-checked="userData.gender=='male'"></span>
													<label for="Q0A0"><img alt="" src="img/gender_male.png"></label>
												</div>
											</td>
											<td>
												<div class="input-group">
													<span class="input-group-addon"><input type="radio"  id="Q0A1" name="Q0" value="female" ng-checked="userData.gender=='female'"></span>
													<label for="Q0A1"><img alt="" src="img/gender_female.png"></label>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						
						<!-- Carousal item Q0 end -->
						
						<!-- Carousal item Q1 start -->
						<div id="cItem1" class="item container-fluid">
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet "></span>
								</div>
								<div class="block">
									<div class="question">What is your age?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<div style="text-align: center;"><img alt="" id="yourAge" src="img/age_male.png" style="min-height: auto !important; height: 150px;"><br></div>
									<div style="margin-left: 200px; margin-right:200px"><input id="range_age"/></div>
									<script type="text/javascript">
										var rangeAge = $("#range_age");
										rangeAge.ionRangeSlider({
									         min:0,
									         max:100,
									         from:18,
									         prefix: "Age : ",
									         postfix: " years",
									         grid: true,
									         onStart: function (data) {
									             //console.log('onStart');
									         },
									         onChange: function (data) {
									             //console.log('onChange');
									             
									         },
									         onFinish: function (data) {
									             //console.log('onFinish');
									         },
									         onUpdate: function (data) {
									             //console.log('onUpdate');
									         }
									     });
									     sliderAge = rangeAge.data("ionRangeSlider");
									     $(".irs-single").css("display","block");
									</script>
								</div>
							</div>
						</div>
						<!-- Carousal item Q1 end -->
						
						<!-- Carousal item Q2 start -->
						<div id="cItem2" class="item container-fluid" >
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet "></span>
								</div>
								<div class="block">
									<div class="question">What is your relationship status?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<table style="width:200px;">
										<tr>
											<td>
												<div class="input-group">
													<span class="input-group-addon"><input type="radio"  id="Q2A0" name="Q2" value="married" ng-checked="userData.relationStatus=='married'"></span>
													<label for="Q2A0"><img alt="" src="img/married.png" style="height:150px !important"></label>
												</div>
											</td>
											<td>
												<div class="input-group">
													<span class="input-group-addon"><input type="radio"  id="Q2A1" name="Q2" value="single" ng-checked="userData.relationStatus=='single'"></span>
													<label for="Q2A1"><img alt="" id="statusSingle" src="img/singleMale.png"  style="height:150px !important"></label>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<!-- Carousal item Q2 end -->
						
						<!-- Carousal item Q3 start -->
						<div  id="cItem3" class="item container-fluid">
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet "></span>
								</div>
								<div class="block">
									<div class="question">What is your monthly take-home income?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<div style="text-align: center;"><img alt="" src="img/money.png" style="    min-height: auto !important; height: 150px;"><br></div>
									<div style="margin-left: 200px; margin-right:200px"><input id="range_salary"/></div>
									
									
									<script type="text/javascript">
										var rangeSalary = $("#range_salary");
										rangeSalary.ionRangeSlider({
									         min:0,
									         max:500000,
									         prefix: "&#8377; ",
									         from:1,
									         grid: true,
									         step: 1000,
									         grid_num: 10,
									         prettify_enabled: true,
									         prettify_separator: ",",
									         /*prettify: function (num) {
									             return num.toLocaleString();
									         },*/
									         onStart: function (data) {
									             //console.log('onStart');
									         },
									         onChange: function (data) {
									             //console.log('onChange');
									             
									         },
									         
									         onFinish: function (data) {
									             if((data.from/this.max)*100 > 95 && this.max<5000000)
									           	 {
													maxSal=this.max;
									            	if(this.max==500000){maxSal=1000000;}
									            	else if(this.max==1000000){maxSal= 1500000;}
									            	else if(this.max==1500000){maxSal= 2500000;}
									            	else if(this.max==2500000){maxSal= 5000000;}
									            	sliderSalary.update({max: maxSal});
									            	sliderSalary.reset();
									           	 }
									             
									             if((data.from/this.max)*100 < 10 && this.max>500000)
									           	 {
													maxSal=this.max;
									            	if(this.max==1000000){maxSal=500000;}
									            	else if(this.max==1500000){maxSal= 1000000;}
									            	else if(this.max==2500000){maxSal= 1500000;}
									            	else if(this.max==5000000){maxSal= 2500000;}
									            	sliderSalary.update({max: maxSal});
									            	sliderSalary.reset();
									           	 }
									              
									             sliderSavings.update({max: (data.from + sliderSalarySpouse.result.from)});
									         },
									         onUpdate: function (data) {
									             //console.log('onUpdate');
									         }
									     });
									     sliderSalary = rangeSalary.data("ionRangeSlider");
									     
									     
									     $(".irs-single").css("display","block");
									</script>
								</div>
							</div>
						</div>
						<!-- Carousal item Q3 end -->
						
						<!-- Carousal item Q4 start -->
						<div id="cItem4" class="item container-fluid marriedSlide" >
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet "></span>
								</div>
								<div class="block">
									<div class="question">What is your spouse's age?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<div style="text-align: center;"><img alt="" id="spouseAge" src="img/age_female.png" style="    min-height: auto !important; height: 150px;"><br></div>
									<div style="margin-left: 200px; margin-right:200px"><input id="range_ageSpouse"/></div>
									<script type="text/javascript">
										var rangeAgeSpouse = $("#range_ageSpouse");
										rangeAgeSpouse.ionRangeSlider({
									         min:0,
									         max:100,
									         prefix: "Age : ",
									         postfix: " years",
									         from:18,
									         grid: true,
									         onStart: function (data) {
									             //console.log('onStart');
									         },
									         onChange: function (data) {
									             //console.log('onChange');
									         },
									         onFinish: function (data) {
									             //console.log('onFinish');
									         },
									         onUpdate: function (data) {
									             //console.log('onUpdate');
									         }
									     });
									     sliderAgeSpouse = rangeAgeSpouse.data("ionRangeSlider");
									     $(".irs-single").css("display","block");
									 </script>
								</div>
							</div>
						</div>
						<!-- Carousal item Q4 end -->
						
						<!-- Carousal item Q5 start -->
						<div  id="cItem5" class="item container-fluid marriedSlide" >
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet "></span>
								</div>
								<div class="block">
									<div class="question">What is your spouse's monthly take-home income?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<div style="text-align: center;"><img alt="" src="img/money.png" style="    min-height: auto !important; height: 150px;"><br></div>
									<div style="margin-left: 200px; margin-right:200px"><input id="range_salarySpouse"/></div>
									
									<script type="text/javascript">
										var rangeSalarySpouse = $("#range_salarySpouse");
										rangeSalarySpouse.ionRangeSlider({
									         min:0,
									         max:500000,
									         prefix: "&#8377; ",
									         from:0,
									         step: 1000,
									         grid: true,
									         grid_num: 10,
											 prettify_enabled: true,
									         prettify_separator: ",",	
									         /*prettify: function (num) {
									             return num;
									         },*/
									         onStart: function (data) {
									             
									         },
									         onChange: function (data) {
									             
									             
									         },
									         onFinish: function (data) {
									        	 if((data.from/this.max)*100 > 95 && this.max<5000000)
									           	 {
													maxSal=this.max;
									            	if(this.max==500000){maxSal=1000000;}
									            	else if(this.max==1000000){maxSal= 1500000;}
									            	else if(this.max==1500000){maxSal= 2500000;}
									            	else if(this.max==2500000){maxSal= 5000000;}
									            	sliderSalarySpouse.update({max: maxSal});
									            	sliderSalarySpouse.reset();
									           	 }
									             if((data.from/this.max)*100 < 10 && this.max>500000)
									           	 {
													maxSal=this.max;
									            	if(this.max==1000000){maxSal=500000;}
									            	else if(this.max==1500000){maxSal= 1000000;}
									            	else if(this.max==2500000){maxSal= 1500000;}
									            	else if(this.max==5000000){maxSal= 2500000;}
									            	sliderSalarySpouse.update({max: maxSal});
									            	sliderSalarySpouse.reset();
									           	 }
									             sliderSavings.update({max: (data.from + sliderSalary.result.from)});
									         },
									         onUpdate: function (data) {
									             
									         }
									     });
									     sliderSalarySpouse = rangeSalarySpouse.data("ionRangeSlider");
									     $(".irs-single").css("display","block");
									</script>
								</div>
							</div>
						</div>
						<!-- Carousal item Q5 end -->
						
						
						
						<!-- Carousal item Q6 start -->
						<div  id="cItem6" class="item container-fluid">
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet "></span>
								</div>
								<div class="block">
									<div class="question" id="divMonthlySavings">What is your monthly savings? (Post loan EMIs & recuring monthly expenditure)</div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<div style="text-align: center;"><img alt="" src="img/piggyBank.png" style="    min-height: auto !important; height: 150px;"><br></div>
									<div style="margin-left: 200px; margin-right:200px"><input id="range_savings"/></div>
									<script type="text/javascript">
										var rangeSavings = $("#range_savings");
										rangeSavings.ionRangeSlider({
											min:0,
									         max:500000,
									         prefix: "&#8377; ",
									         from:0,
									         step: 1000,
									         grid: true,
									         grid_num: 10,
									         prettify_enabled: true,
									         prettify_separator: ",",
									          /*prettify: function (num) {
									             return num;
									         },*/
											onStart: function (data) {
									        	 
									         },
									         onChange: function (data) {
										         $('#spanSavingrate').html(parseFloat((data.from/this.max)*100).toFixed( 2 )  + '%');
									             $('#spanMonthlyExpenditure').html(new Number(this.max-data.from + "").toLocaleString());
									         },
									         onFinish: function (data) {
									        	 if(this.max == 0)
										             $('#spanSavingrate').html(parseFloat(0).toFixed( 2 )  + '%');
										         else
										             $('#spanSavingrate').html(parseFloat((data.from/this.max)*100).toFixed( 2 )  + '%');
									             $('#spanMonthlyExpenditure').html(new Number(this.max-data.from + "").toLocaleString());
									         },
									         onUpdate: function (data) {
									        	 if(this.max == 0)
										             $('#spanSavingrate').html(parseFloat(0).toFixed( 2 )  + '%');
										         else
										             $('#spanSavingrate').html(parseFloat((data.from/this.max)*100).toFixed( 2 )  + '%');
									             $('#spanMonthlyExpenditure').html(new Number(this.max-data.from + "").toLocaleString());
									         }
									     });
									     sliderSavings = rangeSavings.data("ionRangeSlider");
									     $(".irs-single").css("display","block");
									</script>
								</div>
								<div class="col-md-11">
									<div style="margin-left: 250px; margin-right:250px; margin-top: 10px;">
										<table style="width: 310px !important; margin: auto	">
											<tr>
												<td style="text-align: center;/* margin: 2px; */ background-color: #0C95BE; border-radius: 100px; height: 150px; width: 150px; color: #ffffff; vertical-align: middle; font-size:17px">
													Saving Rate : <br><span id="spanSavingrate" style="font-size: 25px;">0</span>
													<input type="hidden" id="savingRate">
												</td>
												<td style="width:10px"></td>
												<td style="text-align: center;/* margin: 2px; */ background-color: #0C95BE; border-radius: 100px; height: 150px; width: 150px; color: #ffffff; vertical-align: middle; font-size:17px">
													Monthly<br>Expenditure:<br> <span style="font-size: 25px;">&#8377;</span><span id="spanMonthlyExpenditure" style="font-size: 25px;">5,00,000</span>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!-- Carousal item Q6 end -->
						
						
						
						
						<!-- Carousal item Q7 Start -->
						<div  id="cItem7" class="item container-fluid">
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet"></span>
								</div>
								<div class="block">
									<div class="question" id="divOutstandingLoan">What are your current outstanding loans ?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<br>
									<table class="currentLoan" id="currentLoan" style="width: auto;" >
										<tr>
											<th>Loan Description</th>
											<th>Loan Amount</th>
											<th>Monthly payment (EMI)</th>
											<th>Final Year of Payment</th>
										</tr>
										<tr style="margin-bottom:5px;" ng-repeat="(i, ol) in userData.outstandingLoans track by $index">
											<td>
												<select id="clLoanDescription" class="clLoanDescription" style="width: 100%">
												    <option value="-1">Select</option>
												    <option value="5" data-iconurl="img/icon_home.png" selected="selected" ng-selected="ol.loanDescription=='House'">House</option>
												    <option value="9" data-iconurl="img/icon_selfStudy.png"  ng-selected="ol.loanDescription=='Education'">Education</option>
												    <option value="4" data-iconurl="img/icon_car.png"  ng-selected="ol.loanDescription=='Vehicle'">Vehicle</option>
												    <option value="4" data-iconurl="img/icon_vacation.png" ng-selected="ol.loanDescription=='Personal'">Personal</option>
												    <option value="0" data-iconurl="img/icon_others.png" ng-selected="ol.loanDescription=='Others'">Others</option>
												</select>
												<input id="clLoanOther" class="clLoanOther" type="text" style="display:none; width:100%" placeholder="Please Specify" ng-show="ol.loanDescription=='Others'">
						  					</td>
											<td>
												<input id="clAmount" type="text" class="form-control clAmount txtRupee" placeholder="Please Enter Amount" value="{{ol.loanAmount}}">
											</td>
											<td>
												<input id="clEMI" type="text" class="form-control clEMI txtRupee" placeholder="Please Enter Amount" value="{{ol.emi}}">
											</td>
											<td>
												<div class="form-group"><div class="input-group date"><input id="clFinalYear" type="text" class="form-control clFinalYear calender numericOnly"  placeholder="YYYY" onkeydown="return false" value="{{ol.finalYearofPayment}}"></div></div>
												
											</td>
											<td width=100>
												<img src="img/x.png" id="btnRemoveRow" width="30px">
											</td>
										</tr>
									</table>
									<h1></h1>
									<br><img id="btnAddLoan" src="img/addRow.png" width="40px" style="cursor: pointer;" data-toggle="tooltip" data-delay=0  data-trigger="hover" data-placement="left" title="Tooltip on left">
								</div>
							</div>
						</div>
						
						<!-- Carousal item Q6 End -->
						
						
						<!-- Carousal item Q7 Start -->
						<div  id="cItem8" class="item container-fluid">
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet"></span>
								</div>
								<div class="block">
									<div class="question" id="divTotalAssets">What is your total current networth?<br><br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-5">
									<table id="tableTotalAssets" style="width:1000px">
										<tbody id="tableTotalAssetsBody" class="tableTotalAssetsBody">
											<tr ng-repeat="(i, fal) in userData.financialAssetList track by $index">
												<td style="width: 600px">{{fal.assetDescription}}</td>
												<td style="width: 300px"><input id="totalAssets_{{fal.assetId}}" type="text" class="form-control  txtRupee totalAssets" aria-describedby="basic-addon1" placeholder="0" value="{{fal.assetAmount}}"></td>
												<td style="width: 100px"></td>
											</tr>
										</tbody>
										<tfoot>
											<tr>
												<td>&nbsp;<input type="hidden" id="totalFinancialAssets" value="0"></td>
											</tr>
											<tr>
												<td><b>Total Assets</b></td>
												<td id="tdTotalAssets" class="txtRupee" style="padding-bottom: 0px; font-weight: bold; font-size: medium;">{{userData.initialTotalFinancialAsset}}</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td><img id="btnAddRowNetworth" src="img/addRow.png" width="30px" style="cursor: pointer;" data-toggle="tooltip" data-delay=0  data-trigger="hover" data-placement="left" title="Add Networth"> ADD</td>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</div>
						
						
						<!-- Carousal item Q6 End -->
						
						
						
						<!-- Carousal item Q7 Start -->
						<div  id="cItem9" class="item container-fluid" style="overflow: visibility; min-height: 500px">
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet"></span>
								</div>
								<div class="block">
									<div class="question" id="">Insurance Details<br><br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								
								<div class="col-md-11 container-fluid row">
									<div class="col-md-12">
										<table style="width: auto" class="tblLifeInsuaranceDetails" id="tblLifeInsuaranceDetails">
											<tr>
												<th>Life Insurance Name</th>
												<th>Insurance cover </th>
												<th>Annual Premium</th>
												<th>Final Year of Payment</th>
											</tr>
											<tr  ng-repeat="(i, li) in userData.lifeInsuranceCover track by $index">
												<td>
													<input type="text" class="liName form-control alphaWithSpace" aria-describedby="basic-addon1" value="{{li.lifeInsuranceDescription}}">
												</td>
												<td>
													<input type="text" class="liCover form-control txtRupee" aria-describedby="basic-addon1" value="{{li.insuranceCover}}">
												</td>
												<td>
													<input type="text" class="form-control liMonthlyPremium  txtRupee"  value="{{li.annualPremium}}">
												</td>
												<td>
													<input type="text" class="liFinalYear form-control calender" aria-describedby="basic-addon1" value="{{li.finalYearofPayment}}">
												</td>
												<td>
													<img src="img/x.png" id="btnRemoveRow" width="30px" style="cursor: pointer;">
												</td>
											</tr>
										</table>
										<br><img id="btnLifeInsuaranceDetails" src="img/addRow.png" width="40px" style="cursor: pointer;" data-toggle="tooltip" data-delay=0  data-trigger="hover" data-placement="left" title="Tooltip on left">
									</div>
									<div class="col-md-12"><br><br></div>
									<div class="col-md-12">
										<table style="width: auto" class="tblHealthInsuaranceDetails" id="tblHealthInsuaranceDetails">
											<tr>
												<th>Health Insurance Name</th>
												<th>Insurance cover </th>
												<th>Annual Premium</th>
											</tr>
											<tr   ng-repeat="(i, hi) in userData.healthInsuranceCover track by $index">
												<td>
													<input type="text" class="hiName form-control alphaWithSpace" aria-describedby="basic-addon1" value="{{hi.healthInsuranceDescription}}">
												</td>
												<td>
													<input type="text" class="hiCover form-control txtRupee" aria-describedby="basic-addon1" value="{{hi.insuranceCover}}">
												</td>
												<td>
													<input type="text" class="hiMonthlyPremium form-control txtRupee" aria-describedby="basic-addon1" value="{{hi.annualPremium}}">
												</td>
												<td>
													<img src="img/x.png" id="btnRemoveRow" width="30px" style="cursor: pointer;">
												</td>
											</tr>
										</table>
										<br><img id="btnAddHealthInsuaranceDetails" src="img/addRow.png" width="40px" style="cursor: pointer;" data-toggle="tooltip" data-delay=0  data-trigger="hover" data-placement="left" title="Tooltip on left">
									</div>
								</div>
							</div>
						</div>
						<!-- Carousal item Q7 End -->
						
						<!-- Carousal item Q7 Start -->
						<div  id="cItem10" class="item container-fluid" style="overflow: visibility; min-height: 500px">
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet"></span>
								</div>
								<div class="block">
									<div class="question" id="">What are your Life Goals?<br><br></div>
								</div>
							</div>
							<div class="row">
								<div class="answerIndent pull-left">
								</div>
								<div class="col-md-11">
									<table style="width: auto" class="lifegoals" id="lifegoals">
										<tr>
											<th>&nbsp;&nbsp;Life&nbsp;Goals&nbsp;&nbsp;&nbsp;&nbsp;</th>
											<th>Frequency&nbsp;&nbsp;&nbsp;&nbsp;</th>
											<th>Year&nbsp;of&nbsp;Realization&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
											<th>Current&nbsp;Estimated&nbsp;Amount&nbsp;&nbsp;&nbsp;</th>
											<th>Loan&nbsp;(Y/N)&nbsp;&nbsp;&nbsp;&nbsp;</th>
											<td style="width:100%"></td>
										</tr>
										<tr ng-repeat="(i, lg) in userData.lifeGoals track by $index">
											<td>
												<select id="lgOptions" class="lgOptions" name="test">
												    <option value="select">Select Goal</option>
												    <option value="1" ng-selected="lg.goalDescriptionId==1" data-iconurl="img/icon_selfMarriage.png">Self Marriage</option>
												    <option value="8" ng-selected="lg.goalDescriptionId==8" data-iconurl="img/icon_selfStudy.png">Self Higher study</option>
												    <option value="3" ng-selected="lg.goalDescriptionId==3" data-iconurl="img/icon_bike.png">Two Wheeler</option>
												    <option value="4" ng-selected="lg.goalDescriptionId==4" data-iconurl="img/icon_car.png">Car</option>
												    <option value="5" ng-selected="lg.goalDescriptionId==5" data-iconurl="img/icon_home.png">House</option>
												    <option value="6" ng-selected="lg.goalDescriptionId==6" data-iconurl="img/icon_vacation.png">Domestic vacation</option>
												    <option value="7" ng-selected="lg.goalDescriptionId==7" data-iconurl="img/icon_vacation.png">International vacation</option>
												    <option value="9" ng-selected="lg.goalDescriptionId==9" data-iconurl="img/icon_selfStudy.png">Children Higher Study</option>
												    <option value="2" ng-selected="lg.goalDescriptionId==2" data-iconurl="img/icon_childrenMarriage.png">Children Marriage</option>
												    <option value="0" ng-selected="lg.goalDescriptionId==0" data-iconurl="img/icon_others.png">Others</option>
												</select>
												<input id="lgOther" class="lgOther" type="text" id="txtOthers" ng-show="lg.goalDescriptionId==0" value="{{lg.goalDescription}}" placeholder="Please Specify" style="width:100%">
											</td> 
											<td>
												<select id="lgFrequency" class="lgFrequency" name="testx" style="display:none" ng-disabled="{{(lg.goalDescriptionId==6 || lg.goalDescriptionId==7) == false}}">
													<option value="select" >Select Frequency</option>
												    <option value="0" ng-selected="lg.frequency==0">Only once</option>
												    <option value="1" ng-selected="lg.frequency==1" >Once every year</option>
												    <option value="2" ng-selected="lg.frequency==2" >Once in two year</option>
												    <option value="3" ng-selected="lg.frequency==3" >Once in three year</option>
												    <option value="4" ng-selected="lg.frequency==4" >Once in four year</option>
												    <option value="5" ng-selected="lg.frequency==5" >Once in five year</option>
												 </select>
											</td>
											<td>
												<div class="form-group"><div class="input-group date"><input type="text" class="form-control lgYear calender" value="{{lg.yearofRealization}}" aria-describedby="basic-addon1" onkeydown="return false"></div></div>
											</td>
											<td width=400>
												<input type="text" class="form-control lgAmount txtRupee" value="{{lg.estimatedAmount}}"  aria-describedby="basic-addon1">
											</td>
											<td style="padding-top: 10px;">
												<input type="checkbox" class="lgLoan" name="my-checkbox" ng-checked="{{(lg.loanYesNo | uppercase)=='YES'}}">
											</td>
											<td width=100>
												<img src="img/x.png" id="btnRemoveRow" width="30px" style="cursor: pointer;">
											</td>
										</tr>
									</table>
									<br><img id="btnAddRow" src="img/addRow.png" width="40px" style="cursor: pointer;" data-toggle="tooltip" data-delay=0  data-trigger="hover" data-placement="left" title="Tooltip on left">
								</div>
							</div>
						</div>
						
						<!-- Carousal item Q7 End -->
						
						<!-- Carousal item Q11 start -->
						<div   id="cItem11" class="item container-fluid" >
							<input type="hidden" id="itemVissitedFlag" class="itemVissitedFlag" value="false">
							<input id="slideVisited"  class="slideVisited" type="hidden">
							<div class="row">
								<div class="pull-left">
									<span class="questionBullet ">12</span>
								</div>
								<div class="block">
									<div class="question">What kind of returns & risk are you willing to tolerate?<br></div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12" style="display: none">
									<div style="margin-top:50px; font-size: 20px;width: 100%; text-align: center;margin-bottom: 10px">(Drag the needle on the chart to change the value)</div>
									<div id="divRiskProfile" style="height: 285px; padding-top: 100px; width: 100%; text-align: center; margin-top:-50px">
										<div id="rsSalarySavingsRate" style="height: 320px !important; width:500px !important; "></div>
										<div style="margin-top:20px; font-size: 20px"><b>Your Risk Score : <span id="spanRiskScore" style="background-color: #2E709F !important; color: #ffffff; border-radius: 25px;height: 50px; width: 50px;padding:10px">00</span></b></div>
									</div>	
								</div>
								<div class="col-md-12"><h1></h1></div>
								<div class="col-md-12">
									<table id="tblTolarate" class="tblTolarate" cellspacing="5">
										<tr style="border-bottom: 2px solid #DDDDDD ">
											<th></th>
											<th>Category</th>
											<th>Average&nbsp;Returns</th>
											<th>Interim&nbsp;Losses</th>
										</tr>
										<tr onclick="javascript:setRiskScoreID(1);" style="cursor: pointer;">
											<td style="border-right : 5px solid #107E66"><input type="radio"  id="Q11A0" name="Q11" value="1"></td>
											<td>Low</lable></td>
											<td style="color:green; text-align: center;">11%</td>
											<td style="color:red; text-align: center;">-13%</td>
										</tr>
										<tr onclick="javascript:setRiskScoreID(2);" style="cursor: pointer;">
											<td style="border-right : 5px solid #7BDB7B"><input type="radio"  id="Q11A1" name="Q11" value="2"></td>
											<td>Moderate&nbsp;Low</td>
											<td style="color:green; text-align: center;">13%</td>
											<td style="color:red; text-align: center;">-19%</td>
										</tr>
										<tr onclick="javascript:setRiskScoreID(3);" style="cursor: pointer;">
											<td style="border-right : 5px solid #FFFD4B"><input type="radio"  id="Q11A2" name="Q11" value="3"></td>
											<td>Moderate</td>
											<td style="color:green; text-align: center;">14%</td>
											<td style="color:red; text-align: center;">-24%</td>
										</tr>
										<tr onclick="javascript:setRiskScoreID(4);" style="cursor: pointer;">
											<td style="border-right : 5px solid #FD752C"><input type="radio"  id="Q11A3" name="Q11" value="4"></td>
											<td>Moderate&nbsp;High</td>
											<td style="color:green; text-align: center;">15%</td>
											<td style="color:red; text-align: center;">-33%</td>
										</tr>
										<tr onclick="javascript:setRiskScoreID(5);" style="cursor: pointer;">
											<td style="border-right : 5px solid #EB3E42"><input type="radio"  id="Q11A4" name="Q11" value="5"></td>
											<td>High</td>
											<td style="color:green; text-align: center;">16%</td>
											<td style="color:red; text-align: center;">-37%</td>
										</tr>
									</table>
									<script>
										function setRiskScoreID(id){
											$("input[name=Q11][value='"+id+"']").prop("checked",true);
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
										}
									</script>
								</div>
							</div>
						</div>
						<!-- Carousal item Q11 end -->
					</div>
				 </div>
				 
				<style>
					.irs-single{
						display: block;
					}
				</style>
				<style>
					.stepProgress .bar {
						width: 20px;
					}
				</style>
				 
				<div style="position: fixed;bottom:5px; width:100%; background-color: #ffffff">
					<div style="margin-right: 20px; background-color: #F5F5F5; padding:10px;border-radius: 10px;">
						<div class="stepProgress">
							<div id="spc0" class="circle active">
								<a><span class="label">1</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc1" class="circle">
								<a><span class="label">2</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc2" class="circle">
								<a><span class="label">3</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc3" class="circle">
								<a><span class="label">4</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc4" class="circle">
								<a><span class="label">5</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc5" class="circle">
								<a><span class="label">6</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc6" class="circle">
								<a><span class="label">7</span></a>
							</div>
							<span id="apb7" class="bar"></span>
							<div id="spc7" class="circle">
								<a><span class="label" id="spl7">8</span></a>
							</div>
							<span id="apb8" class="bar"></span>
							<div id="spc8" class="circle">
								<a><span class="label" id="spl8">9</span></a>
							</div>
							<span id="apb9" class="bar"></span>
							<div id="spc9" class="circle">
								<a><span class="label" id="spl9">10</span></a>
							</div>
							<span id="apb10" class="bar marriedOptions"></span>
							<div id="spc10" class="circle marriedOptions">
								<a><span class="label" id="spl9">11</span></a>
							</div>
							<span id="apb11" class="bar marriedOptions"></span>
							<div id="spc11" class="circle marriedOptions">
								<a><span class="label" id="spl9">12</span></a>
							</div>
							<script>
								$('.marriedOptions').css('display', 'none');
							</script>
						</div>
						<div class="text-right"  style="margin-right:0px !important; ">
				   			<button type="button" id="btnPrev" class="btn-flat inverse large"  style="display: none">Previous</button>
					        <button type="button" id="btnNext" class="btn-flat inverse large"  >Next</button>
					   		<button type="button" id="btnSubmit" class="btn-flat success large" href="#myCarousel" style="display:none;">Submit</button>
					    </div>
					</div>
				</div>
				
				
				<!-- Modal -->
				<div class="modal fade" id="modalShowError" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="myModalLabel"></h4>
				      </div>
				      <div class="modal-body">
				        this is modalbody
				      </div>
				      <div class="modal-footer">
				      	<button type="button" class="btn-flat inverse" data-dismiss="modal">OK</button>
				      </div>
				    </div>
				  </div>
				</div>
			</div>
			
			<script>
				$(function() {
					var selectBox = $("select").selectBoxIt();
				});
				
				/*$("[name='my-checkbox']").bootstrapSwitch({
					'size':'mini',
					'onText':'Yes',
					'offText':'No'
				});*/
			</script>
			<script type="text/javascript">
				var index=1;
				$(document).ready(function(){
					$("#btnAddRow").click(function(){		
				        $("#lifegoals").append('<tr><td><select id="lgOptions" class="lgOptions" name="test"> <option value="select" >Select Goal</option> <option value="1" data-iconurl="img/icon_selfMarriage.png">Self Marriage</option> <option value="8" data-iconurl="img/icon_selfStudy.png">Self Higher study</option> <option value="3" data-iconurl="img/icon_bike.png">Two Wheeler</option> <option value="4" data-iconurl="img/icon_car.png">Car</option> <option value="5" data-iconurl="img/icon_home.png">House</option> <option value="6" data-iconurl="img/icon_vacation.png">Domestic vacation</option> <option value="7" data-iconurl="img/icon_vacation.png">International vacation</option> <option value="9" data-iconurl="img/icon_selfStudy.png">Children Higher Study</option> <option value="2" data-iconurl="img/icon_childrenMarriage.png">Children Marriage</option> <option value="0" data-iconurl="img/icon_others.png">Others</option></select><input id="lgOther" class="lgOther" type="text" id="txtOthers" style="display:none; width:100%" placeholder="Please Specify"></td><td><select id="lgFrequency" class="lgFrequency" name="testx" style="display:none" disabled="disabled"><option value="select" >Select Frequency</option> <option value="0" >Only once</option> <option value="1" >Once every year</option> <option value="2" >Once in two year</option> <option value="3" >Once in three year</option> <option value="4" >Once in four year</option> <option value="5" >Once in five year</option> </select></td><td><div class="form-group"><div class="input-group date"><input type="text" class="form-control lgYear calender"/></div></div></td><td width=400><input type="text" class="form-control lgAmount txtRupee" aria-describedby="basic-addon1"></td><td style="padding-top: 10px;"><input type="checkbox" class="lgLoan" name="my-checkbox"></td><td width=100><img src="img/x.png" id="btnRemoveRow" width="30px" style="cursor: pointer;"></td></tr>');
				        
				        $("[name='my-checkbox']").bootstrapSwitch({
				    		'size':'mini',
				    		'onText':'Yes',
				    		'offText':'No'
				    	});
				        
				        $("select").selectBoxIt();
				        
				        $('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
				        
				        addEventToLoanDropDown();
				        $('.clFinalYear').datetimepicker({
					    	viewMode: 'years',
					    	format: 'YYYY',
					    	minDate: moment()
					    	
					    });
					    $('.lgYear').datetimepicker({
					    	viewMode: 'years',
					    	format: 'YYYY',
					    	minDate: moment()
					    });
					});
					
					addEventToLoanDropDown = function(){
				    	//alert();
				    	$('select.lgOptions').each(function( index ) {
					    	$(this).on('change', function (e) {
					    		console.log(this);
					    	    var optionSelected = $("option:selected", this);
					    	    var valueSelected = this.value;
					    	    console.log(index);
					    	    console.log(valueSelected);
					    	    toggleOthers(index,valueSelected,this);
					    	});
				    	});
				    	
				    	$('select.clLoanDescription').each(function( index ) {
					    	$(this).on('change', function (e) {
					    	    var optionSelected = $("option:selected", this);
					    	    var valueSelected = this.value;
					    	    toggleOthers(index,valueSelected,this)
					    	});
				    	});
				    	
				    	$('select.lgFrequency').each(function( index ) {
					    	$(this).on('change', function (e) {
					    	    var optionSelected = $("option:selected", this);
					    	    var valueSelected = this.value;
					    	    if(valueSelected == 'select'){
					    	    	$('.lgLoan').eq(index).bootstrapSwitch('disabled', true, true);
					    	    	$('.lgLoan').eq(index).bootstrapSwitch('disabled', false, true);
					    	    }
					    	    else{
					    	    	$('.lgLoan').eq(index).bootstrapSwitch('state', false, true);
					    	    	$('.lgLoan').eq(index).bootstrapSwitch('disabled', true, true);
					    	    }
					    	});
				    	});
				    	
				    }
				    addEventToLoanDropDown();
				    
				    
				    toggleOthers = function(opIndex,valueSelected,thisObj){
				    	
				    	if(valueSelected==6 || valueSelected==7 ){
				    		$(thisObj).parent().siblings().find('.lgFrequency').prop('disabled',true);
				    		$(thisObj).parent().siblings().find('.lgFrequency').selectBoxIt().data('selectBoxIt').disable();
				    		$(thisObj).parent().siblings().find('.lgFrequency').selectBoxIt().data('selectBoxIt').enable()
				    	}
				    	else{
				    		$(thisObj).parent().siblings().find('.lgFrequency').prop('disabled',false);
				    		$(thisObj).parent().siblings().find('.lgFrequency').selectBoxIt().data('selectBoxIt').selectOption(0);
				    		$(thisObj).parent().siblings().find('.lgFrequency').selectBoxIt().data('selectBoxIt').disable();
				    	}
				    	
				    	
				    	$('.lgOther').each(function(index) {
			    	    	if(opIndex == index){
			    	    		if(valueSelected == 0){
			    	    	    	$(this).css('display','block');
			    	    	    }
			    	    	    else{
			    	    	    	$(this).css('display','none');
			    	    	    }
			    	    	}
			        	});
				    	
				    	$('.clLoanOther').each(function(index) {
			    	    	if(opIndex == index){
			    	    		if(valueSelected == 0){
			    	    	    	$(this).css('display','block');
			    	    	    }
			    	    	    else{
			    	    	    	$(this).css('display','none');
			    	    	    }
			    	    	}
			        	});
				    	
				    	
				    }
				    
				    
				    
				    $("#btnAddRowNetworth").click(function(){
				        $("#tableTotalAssetsBody").append('<tr><td style="width:500px"><input type="text" class="form-control assetOthers" aria-describedby="basic-addon1" placeholder="Mention Asset Name"></td><td style="width:300px"><input type="text" class="form-control lgAmount txtRupee totalAssets" aria-describedby="basic-addon1" placeholder="0"></td><td width=100> <img src="img/x.png" id="btnRemoveRow" width="30px"></td></tr>');
				        $('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
				        
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
					        $('#totalFinancialAssets').val(total);
					    });
				        
					});
				    
				    $("#btnAddHealthInsuaranceDetails").click(function(){
				        $("#tblHealthInsuaranceDetails").append('<tr><td> <input class="alphaWithSpace" type="text" class="form-control" aria-describedby="basic-addon1"></td><td> <input type="text" class="form-control txtRupee" aria-describedby="basic-addon1"></td><td> <input type="text" class="form-control txtRupee" aria-describedby="basic-addon1"></td><td> <img src="img/x.png" id="btnRemoveRow" width="30px" style="cursor: pointer;"></td></tr>');
				        $('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
				        $(".alphaWithSpace").keypress(function (event) {
							var regex = new RegExp("^[a-zA-Z\\-\\s]+$");
						    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
						    if (!regex.test(key)) {
						       event.preventDefault();
						       return false;
						    }
						});
					});
				    
				    $("#btnLifeInsuaranceDetails").click(function(){
				        $("#tblLifeInsuaranceDetails").append('<tr><td><input type="text" class="liName form-control alphaWithSpace" aria-describedby="basic-addon1"></td><td><input type="text" class="liCover form-control txtRupee" aria-describedby="basic-addon1"></td><td><input type="text" class="form-control liMonthlyPremium  txtRupee" ></td><td><input type="text" class="liFinalYear form-control calender" aria-describedby="basic-addon1"></td><td><img src="img/x.png" id="btnRemoveRow" width="30px" style="cursor: pointer;"></td></tr>');
				        $('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
				        $('.liFinalYear').datetimepicker({
					    	viewMode: 'years',
					    	format: 'YYYY',
					    	minDate: moment(),
					    	defaultDate : "",
					    });
				        $(".alphaWithSpace").keypress(function (event) {
							var regex = new RegExp("^[a-zA-Z\\-\\s]+$");
						    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
						    if (!regex.test(key)) {
						       event.preventDefault();
						       return false;
						    }
						});
					});
				    
					$("#btnAddLoan").click(function(){
				        $("#currentLoan").append('<tr style="margin-bottom:5px"><td><select id="clLoanDescription" class="clLoanDescription" style="width: 100%"><option value="-1">Select</option><option value="5" data-iconurl="img/icon_home.png">House</option><option value="9" data-iconurl="img/icon_selfStudy.png">Education</option><option value="4" data-iconurl="img/icon_car.png">Vehicle</option><option value="4" data-iconurl="img/icon_vacation.png">Personal</option><option value="0" data-iconurl="img/icon_others.png" selected="selected">Others</option></select><input class="clLoanDescription" type="text" id="txtOthers" style="display:none; width:100%" placeholder="Please Specify"></td><td> <input id="clAmount" type="text" class="form-control clAmount txtRupee" placeholder="Please Enter Amount"></td>   <td> <input id="clEMI" type="text" class="form-control clEMI txtRupee" placeholder="Please Enter Amount"></td><td><div class="form-group"><div class="input-group date"><input id="clFinalYear" type="text" class="form-control clFinalYear calender numericOnly" placeholder="YYYY" onkeydown="return false"></div></div></td>	<td width=100> <img src="img/x.png" id="btnRemoveRow" width="30px"></td></tr>');
			
				        addEventToLoanDropDown();
				        $("[name='my-checkbox']").bootstrapSwitch({
				    		'size':'mini',
				    		'onText':'Yes',
				    		'offText':'No'
				    	});
				        
				        $(function() {
							var selectBox = $("select").selectBoxIt();
						});
				        
				        $('.clFinalYear').datetimepicker({
					    	viewMode: 'years',
					    	format: 'YYYY',
					    	minDate: moment()
					    });
				        
					    $('.clYear').datetimepicker({
					    	viewMode: 'years',
					    	format: 'YYYY',
					    	defaultDate: ""
					    });
					    $(".numericOnly").keypress(function (e) {
						    if (String.fromCharCode(e.keyCode).match(/[^0-9]/g)) return false;
						});
			
				        $('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
				        
					});
			
				    $("#tableTotalAssetsBody").on('click', '#btnRemoveRow', function(){
				        $(this).parent().parent().remove();
				    });
				    
				    $("#lifegoals").on('click', '#btnRemoveRow', function(){
				        $(this).parent().parent().remove();
				    });
				    
				    $("#tblHealthInsuaranceDetails").on('click', '#btnRemoveRow', function(){
				        $(this).parent().parent().remove();
				    });
				    
				    $("#tblLifeInsuaranceDetails").on('click', '#btnRemoveRow', function(){
				        $(this).parent().parent().remove();
				    });
				    
				    $("#currentLoan").on('click', '#btnRemoveRow', function(){
				        $(this).parent().parent().remove();
				    });
				    
				    $('')
				    $( '.marriedSlide' ).each(function( index ) {
			    	  $(this).removeClass('item');
			    	  $(this).css("display","none");
			    	});
				    
				    $( '.item .questionBullet' ).each(function( index ) {
				    	 if(index<9){
				    		  $(this).html("0" + (index+1));
				    	  }
				    	  else{
				    		  $(this).html(index+1);
				    	  }
			    	});
				    
				    $('.clFinalYear').datetimepicker({
				    	viewMode: 'years',
				    	format: 'YYYY',
				    	minDate: moment(),
				    	defaultDate : ""
				    });
				    
			
				    $('.liFinalYear').datetimepicker({
				    	viewMode: 'years',
				    	format: 'YYYY',
				    	minDate: moment(),
				    	defaultDate : "",
				    });
				    
				    
				    $('.lgYear').datetimepicker({
				    	viewMode: 'years',
				    	format: 'YYYY',
				    	minDate: moment(),
				    	defaultDate:moment()
				    });
				    
				    $(".numericOnly").keypress(function (e) {
					    if (String.fromCharCode(e.keyCode).match(/[^0-9]/g)) return false;
					});
				    $('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
				    $('.irs-grid-text').autoNumeric('init',{vMin: '0',vMax: '9999999999'});
				    
				    
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
				});
				
				
			</script>

		</div>
		<!-- MixPanel -->
		<script type="text/javascript">
			clevertap.event.push("FP Visited");			
		</script>
		<!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/UserProfile.js?v=1" charset="UTF-8"></script>
        <script src="js/directives/userProfile.js" charset="UTF-8"></script>
    </body>
</html>
