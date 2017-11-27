<%@page import="com.gtl.mmf.controller.UserSessionBean"%>
<%@ page import="java.io.*,java.util.*" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%><!doctype html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
        	body{
	        	padding-top: 60px;
	        	padding-bottom: 25px;
        	}
	       .tab-pane {
			    border-left: 1px solid #ddd;
			    border-right: 1px solid #ddd;
			    border-bottom: 1px solid #ddd;
			    border-radius: 0px 0px 5px 5px;
			    padding: 10px;
			}
			.nav-tabs {
			    margin-bottom: 0;
			}
        </style>
        <link rel="stylesheet" href="css/bootstrap-theme-paper.min.css">
        <link rel="stylesheet" href="css/bootstrap-wrapper.css">
        
        <link rel="stylesheet" href="css/iCheck.css" />
        <link rel="stylesheet" href="css/ion.rangeSlider.css"/>
        <link rel="stylesheet" href="css/ion.rangeSlider.skinHTML5.css"/>
        <link rel="stylesheet" href="css/jquery.dataTables.min.css"/>
        <link rel="stylesheet" href="css/roundslider.min.css"/>
        <link rel="stylesheet" href="css/bootstrap-select.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/bootstrap-switch.css" >
        
        <link rel="stylesheet" href="css/jquery-ui.css">
        <link rel="stylesheet" href="css/SelectBoxIt.css" />
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
        <link rel="stylesheet" href="css/bootstrap-submenu.min.css" />
        <link rel="stylesheet" href="css/roundslider.min.css"  />
        
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="app.css">

        <script src="js/vendor/jquery.min.js"></script>
        <script src="js/vendor/jquery-ui.min.js"></script>
        <script src="js/vendor/bootstrap.min.js"></script>
        <script src="js/vendor/angular.min.js"></script>
        
        
        <script src="js/vendor/iCheck.js"></script>
        <script src="js/vendor/ion.rangeSlider.min.js"></script>
        <script src="js/vendor/jquery.dataTables.min.js"></script>
        <script src="js/vendor/roundslider.min.js"></script>
        <script src="js/vendor/bootstrap-select.min.js"></script>
        <script src="js/vendor/bootstrap-switch.js"></script>
        <script src="js/vendor/jquery.selectBoxIt.min.js"></script>
        <script src="js/vendor/moment.js"></script>
        <script src="js/vendor/bootstrap-datetimepicker.min.js"></script>
        <script src="js/vendor/autoNumeric-min.js"></script>
        <script src="js/vendor/bootstrap-submenu.min.js"></script>
        <script src="js/vendor/underscore-min.js"></script>
        <script src="https://code.highcharts.com/stock/highstock.js"></script>
		<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
        <script src= "js/clevertap.js"></script>        
	</head>
    <body  ng-app="appMMF" ng-controller="FinancialPlannerOutput">
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
					UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
					if(userSessionBean!= null){
						userFirstName = (String)userSessionBean.getFirstName();
					}
				}else{
					response.sendRedirect("faces/index.xhtml");
				}
			}
		%>
		<input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
	    <div class="container-fluid" >
			<style>
			lable{
				margin-left: 12px !important;
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
			
			.dataTables_scroll
			{
			    overflow:auto; 
			    width: 100%;
			}
			
			.dataTables_filter{
				display: none !important;
			}
			
			.highcharts-range-selector-buttons{
				display: none !important;
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
			
			#datatable thead tr th {
			background-image:url('img/thumbs.png');
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
			
			
			#divRiskProfile{
				background-image:url('img/riskometer_bg.png');
				background-size: 500px;
				background-repeat: no-repeat;
				background-position: center;
			}
			
			
			
			.rs-seperator{
				border: 0px;
			}
			
			select{
				background-color: rgba(0,0,0,0);
				padding:5px;
				margin:5px;
			}
			
			td{
				padding-right :5px;
			}
			th{
				padding-right :5px;
			}
			
			.txtRupee{
				background-image: url('img/icon_rupee.png');
				background-repeat: no-repeat;
				background-position: left center; 
				background-size: auto 20px;
				padding-left: 25px !important;
				padding-bottom: 30px;
			}
			
			.rs-tooltip-text{
				margin-left: -180px !important;
			    margin-top: -170px !important;
			    padding: 5px !important;
			    background-color: #2E709F !important;
			    border-radius: 25px;
			    color: white;
			    height: 50px;
			    width: 50px;
			}
			
			.irs-single {
			    display: none !important;
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
				
				table.dataTable.no-footer {
			    border-bottom: 0px solid #111 !important;
			}
			
			.nav-tabs li a {
			    color: gray;
			}
			
			
			.active a {
			    font-weight: bold;
			}
			
			
			svg {
				font: 10px sans-serif;
			}
			
			.area {
				fill: rgba(244, 93, 76, 0.8);
				clip-path: url(#clip);
			}
			
			.areaAsset {
				fill: rgba(161, 219, 178, 0.8);
				clip-path: url(#clip);
			}
			
			.axis path,
			.axis line {
				fill: none;
				stroke: #000;
				shape-rendering: crispEdges;
			}
			.axis {
			    background: #000;
			}
			
			.axis line, .axis path {
			    fill: none;
			    stroke: #333;
			}
			
			.brush .extent {
				stroke: #fff;
				fill-opacity: .125;
				shape-rendering: crispEdges;
			}
			
			.lolipop.marker.goalDesc{
				position:absolute;
				background-color: #ffffff;
				border: 1px solid black;
				padding: 10px;
				display: none;
				box-shadow: 1px 1px 3px #888;
				border-radius: 5px;
			}
			</style>
			<script>

			var userDataInput = localStorage.getItem("userDataOutput");
			userDataInput = JSON.parse(userDataInput);
			console.log(userDataInput);
			
			var userDataOutput = localStorage.getItem("userDataOutput");
			userDataOutput = JSON.parse(userDataOutput);
			console.log(userDataOutput);
			
			var scoreRate = userDataOutput;
			
			
			getSelectBox = function(containerID,minVal, maxVal, selectedVal){
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
			
			</script>
			<div class="row-fluid">
				<h2 class="page-title">Financial Planner Output</h2>
			</div>
			<div id="divLoading"  style="display:none">Loading...</div>
			<div id="divContent">
				<div class="row">
					 
					<div class="col-md-8" style="margin-bottom: 20px;">
						<div class="panel panel-default container-fluid">
							<div class="panel-heading row" style="padding: 0px 0px 0px 15px !important; text-align: center;">
								<b><h5>Your Wealth vs Annual Expenditure</h5></b>
							</div>
							<div class="panel-body">
								<div style="text-align: center; color:red; font-weight: bold;">DO NOT LET YOUR WEALTH LINE FALL TO ZERO</div>
								<div id="chartRangeFilter_dashboard_div" style="border: 0px solid #ccc; text-align: left;">
									<table class="columns" style="width:100%">
							            <tr>
							            	<td style="text-align: center; padding:auto">
							            		<table style=" margin-left: auto;margin-top: -30px; display:none">
							            			<tr>
							            				<td style="border-left: 20px solid #C8F1C8;">&nbsp;Assets</td>
							            				<td style="width:10px"></td>
							            				<td style="border-left: 20px solid #FBBEBC;">&nbsp;Expenditure</td>
							            			</tr>
							            		</table>
							            	</td>
							            </tr>
							            <tr>
							            	<td id="box">
								            	<div id="financialOutputChart" style="width: 100%; height: 380px;"></div>
								            	<div id="financialOutputChartText" style="font-weight: bold;font-size: 15px; position:absolute; ; top:100px !important; left:150px !important">CLICK & DRAG to zoom</div>
							            	</td>
							            </tr>
							        </table>
						        </div>
							</div>
						</div>
					</div>
					
					<div class="col-md-4">
						<div class="panel panel-default container-fluid">
							<div class="panel-heading row" style="padding: 0px 0px 0px 15px !important;">
								<b><h5>Edit Inputs</h5></b>
							</div>
							<div class="panel-body">
								<div id="divRiskProfile" style="height: 285px; padding-top: 100px; width: 100%; text-align: center; margin-top:-50px">
									<div id="rsSalarySavingsRate" style="height: 320px !important; width:500px !important;"></div>
								</div>
								<div style="text-align: left;margin-top:-50px;">Savings Rate</div>
								<div style="margin-right: 20px; margin-top:-30px; margin-bottom:10px;">
									<input id="range_score"/>
									<span id="value_score" class="pull-right" style="width:45px;margin-left:35px;margin-right:-48px;margin-top:-50px;text-align:center;background-color:#0C95BE;border-radius:20px;color:#ffffff;padding:2px">0%</span>
								</div>
								<div style="text-align: left;">Allocation to ManageMyFortune</div>
								<div style="margin-right: 20px; margin-top:-30px; margin-bottom:10px;">
									<input id="range_allocationMMF"/>
									<span id="value_allocationMMF" class="pull-right" style="width:45px;margin-left:35px;margin-right:-48px;margin-top:-50px;text-align:center;background-color:#0C95BE;border-radius:20px;color:#ffffff;padding:2px">100%</span>
								</div>
								<div style="text-align: left;">Retirement Age</div>
								<div style="margin-right: 20px; margin-top:-30px; margin-bottom:-10px;">
									<input id="range_RetirementAge"/>
									<span id="value_RetirementAge" class="pull-right" style="width:45px;margin-left:35px;margin-right:-48px;margin-top:-50px;text-align:center;background-color:#0C95BE;border-radius:20px;color:#ffffff;padding:2px">60</span>
								</div>
							</div>
						</div>
					</div>
					
					<div class="col-md-12">
						<div class="panel panel-default container-fluid">
							<div class="panel-heading row" style="padding: 0px 19px 12px 0px !important;cursor:pointer !important" onClick="javascript:toggleCollaps('#iconLifeGoals','#divLifeGoals');">
								<h5>
									<div class="col-md-11">Life Goals</div>
									<div class="col-md-1  pull-right"  style="width:52px;!important"><a href="" class="btn btn-success"><span id="iconLifeGoals" class="glyphicon glyphicon-chevron-down pull-right" style="right:0px"></span></a></div>
								</h5>
							</div>
							<div class="panel-body" id="divLifeGoals"  >
								<table style="width: auto" class="lifegoals" id="lifegoals">
									<tr>
										<th>&nbsp;&nbsp;Life&nbsp;Goals&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<th>Frequency&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<th>Year&nbsp;of&nbsp;realization&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<th>Current&nbsp;Estimated&nbsp;Amount&nbsp;&nbsp;&nbsp;</th>
										<th>Loan&nbsp;(Y/N)&nbsp;&nbsp;&nbsp;&nbsp;</th>
										<td style="width:100%"></td>
									</tr>
									<tr ng-view ng-repeat="(i, lg) in lifeGoalsJson track by $index" repeat-done="layoutDone()">
										<td>
											<select id="lgOptions" class="lgOptions" name="test" ng-model="lg.goalDescriptionId" ngChange="updateLifeGoals()">
											    <option value="1" data-iconurl="img/icon_selfMarriage.png" ng-selected="lg.goalDescriptionId==1">Self Marriage</option>
											    <option value="8" data-iconurl="img/icon_selfStudy.png" ng-selected="lg.goalDescriptionId==8">Self Higher study</option>
											    <option value="3" data-iconurl="img/icon_bike.png" ng-selected="lg.goalDescriptionId==3">Two Wheeler</option>
											    <option value="4" data-iconurl="img/icon_car.png" ng-selected="lg.goalDescriptionId==4">Car</option>
											    <option value="5" data-iconurl="img/icon_home.png" ng-selected="lg.goalDescriptionId==5">House</option>
											    <option value="6" data-iconurl="img/icon_vacation.png" ng-selected="lg.goalDescriptionId==6" ng-selected="updateLifeGoals()">Domestic vacation</option>
											    <option value="7" data-iconurl="img/icon_vacation.png" ng-selected="lg.goalDescriptionId==7">International vacation</option>
											    <option value="9" data-iconurl="img/icon_selfStudy.png" ng-selected="lg.goalDescriptionId==9">Children Higher Study</option>
											    <option value="2" data-iconurl="img/icon_childrenMarriage.png" ng-selected="lg.goalDescriptionId==2">Children Marriage</option>
											    <option value="0" data-iconurl="img/icon_others.png" ng-selected="lg.goalDescriptionId==0">Others</option>
											</select>
											<input id="lgOther" class="lgOther" type="text" id="txtOthers" ng-show="lg.goalDescriptionId==0">
										</td>
										<td>
											<select id="lgFrequency" class="lgFrequency" name="testx">
											    <option value="0" ng-selected="lg.frequency==0">Only once</option>
											    <option value="1" ng-selected="lg.frequency==1">Once every year</option>
											    <option value="2" ng-selected="lg.frequency==2">Once in two year</option>
											    <option value="3" ng-selected="lg.frequency==3">Once in three year</option>
											    <option value="4" ng-selected="lg.frequency==4">Once in four year</option>
											    <option value="5" ng-selected="lg.frequency==5">Once in five year</option>
											 </select>
										</td>
										<td>
											<div class="form-group">
								                <div class='input-group date'>
								                    <input type='text' class="form-control lgYear calender" ng-model="lg.yearofRealization"/>
								                </div>
							            	</div>
										</td>
										<td width=400>
											<input type="text" class="form-control lgAmount txtRupee" aria-describedby="basic-addon1"  ng-model="lg.estimatedAmount">
										</td>
										<td style="padding-top:20px">
											<input type="checkbox" class="lgLoan" name="my-checkbox" ng-checked="(lg.loanYesNo | uppercase)=='YES'">
										</td>
										<td width=100>
											<img src="img/x.png" id="btnRemoveRow" width="30px" style="cursor:pointer;" onclick="javascript:removeThisLifeGoal(this)">
										</td>
									</tr>
								</table>
								<br><img id="btnAddRow" src="img/addRow.png" width="30px"  style="margin-right:5px" style="cursor: pointer;" onclick="javascript:addNewLifeGoal();"> Add<br>
								<br><button type="button" class="btn-flat inverse "  onclick="javascript:location.reload();">Reset</button>
								<button type="button" class="btn-flat inverse  "  style="margin-right:10px; margin-left: 5px;" onclick="javascript:makeJsonLifeGoals();">Update</button>
								
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="panel panel-default container-fluid">
							<div class="panel-heading row" style="padding: 0px 19px 12px 0px !important;cursor:pointer !important" href="#" onClick="javascript:toggleCollaps('#iconFATCA','#divFATCA');">
								<h5>
									<div class="col-md-11">Advance Settings</div>
									<div class="col-md-1  pull-right"  style="width:52px;!important"><a href="" class="btn btn-success"><span id="iconFATCA" class="glyphicon glyphicon-chevron-down pull-right" style="right:0px"></span></a></div>
								</h5>
							</div>
							<div class="panel-body" id="divFATCA"  style="display:none;padding-left: 0px;padding-right: 0px;">
											<table>
												<tr>
													<td style="width:50%; vertical-align: top;" class="well">
														<table  class="well">
															<tr style="margin:10px">
																<th>Life&nbsp;Expectancy</th>
																<td id="as1"><script>getSelectBox('#as1','lifeExpentancy','lifeExpentancy',userDataOutput.fpmasterassumption.fpmiscassumptions.retirementAge,100,userDataOutput.fpmasterassumption.fpmiscassumptions.lifeExpectancy);</script></td>
															</tr>
															<tr style="margin:10px">
																<th style="width:150px;">Rate&nbsp;at&nbsp;which&nbsp;non&nbsp;invested&nbsp;amount&nbsp;grows&nbsp;(%)</th>
																<td id="as2"><script>getSelectBox('#as2','rateOfGrowthOfFd','rateOfGrowthOfFd',0,100,userDataOutput.fpmasterassumption.fpmiscassumptions.rateOfGrowthOfFd);</script></td>
																<td style="width: 100%"></td>
															</tr>
															<tr style="margin:10px">
																<th>What&nbsp;is&nbsp;your&nbsp;long&nbsp;term&nbsp;inflation&nbsp;expectation&nbsp;(%)</th>
																<td id="as4"><script>getSelectBox('#as4','longTermInflationExpectation','longTermInflationExpectation',0,100,userDataOutput.fpmasterassumption.fpmiscassumptions.longTermInflationExpectation);</script></td>
															</tr>
															<tr style="margin:10px">
																<th>Recurring&nbsp;Expense&nbsp;level&nbsp;post&nbsp;retirement&nbsp;(%)</th>
																<td id="as5"><script>getSelectBox('#as5','postRetirementRecurringExpense','postRetirementRecurringExpense',0,100,userDataOutput.fpmasterassumption.fpmiscassumptions.postRetirementRecurringExpense);</script></td>
															</tr>
														</table>
													</td>
													<td style="width:10px;"></td>
													<td style="width:50%; vertical-align: top;" class="well">
														<table>
															<tr>
																<th>Loan For</th>
																<th>Number of Years</th>
																<th>Interest Rates</th>
																<th>Downpayment (in %)</th>
															</tr>
															<tr>
																<td>Education 
																	<input type="hidden" class="loanForID" value="1">
																	<input type="hidden" class="loanForDesc" value="Education Loan">
																</td>
																<td id="lf1"><script>getSelectBox('#lf1','loanNumberOfYears','loanNumberOfYears',0,100,userDataOutput.fpmasterassumption.fploanassumptions[0].loanDuration);</script></td>
																<td id="lf2"><script>getSelectBox('#lf2','loanIntrestRate','loanIntrestRate',0,100,userDataOutput.fpmasterassumption.fploanassumptions[0].interestRate);</script></td>
																<td id="lf3"><script>getSelectBox('#lf3','loanDownpayment','loanDownpayment',0,100,userDataOutput.fpmasterassumption.fploanassumptions[0].downPaymentPercent);</script></td>
															</tr>
															<tr>
																<td>Marriage (Personal Loan) 
																	<input type="hidden" class="loanForID" value="4">
																	<input type="hidden" class="loanForDesc" value="Personal Loan">
																</td>
																<td id="lf4"><script>getSelectBox('#lf4','loanNumberOfYears','loanNumberOfYears',0,100,userDataOutput.fpmasterassumption.fploanassumptions[1].loanDuration);</script></td>
																<td id="lf5"><script>getSelectBox('#lf5','loanIntrestRate','loanIntrestRate',0,100,userDataOutput.fpmasterassumption.fploanassumptions[1].interestRate);</script></td>
																<td id="lf6"><script>getSelectBox('#lf6','loanDownpayment','loanDownpayment',0,100,userDataOutput.fpmasterassumption.fploanassumptions[1].downPaymentPercent);</script></td>
															</tr>
															<tr>
																<td>House 
																	<input type="hidden" class="loanForID" value="2">
																	<input type="hidden" class="loanForDesc" value="House Loan">
																</td>
																<td id="lf7"><script>getSelectBox('#lf7','loanNumberOfYears','loanNumberOfYears',0,100,userDataOutput.fpmasterassumption.fploanassumptions[2].loanDuration);</script></td>
																<td id="lf8"><script>getSelectBox('#lf8','loanIntrestRate','loanIntrestRate',0,100,userDataOutput.fpmasterassumption.fploanassumptions[2].interestRate);</script></td>
																<td id="lf9"><script>getSelectBox('#lf9','loanDownpayment','loanDownpayment',0,100,userDataOutput.fpmasterassumption.fploanassumptions[2].downPaymentPercent);</script></td>
															</tr>
															<tr>
																<td>Car 
																	<input type="hidden" class="loanForID" value="2">
																	<input type="hidden" class="loanForDesc" value="Car Loan">
																</td>
																<td id="lf10"><script>getSelectBox('#lf10','loanNumberOfYears','loanNumberOfYears',0,100,userDataOutput.fpmasterassumption.fploanassumptions[3].loanDuration);</script></td>
																<td id="lf11"><script>getSelectBox('#lf11','loanIntrestRate','loanIntrestRate',0,100,userDataOutput.fpmasterassumption.fploanassumptions[3].interestRate);</script></td>
																<td id="lf12"><script>getSelectBox('#lf12','loanDownpayment','loanDownpayment',0,100,userDataOutput.fpmasterassumption.fploanassumptions[3].downPaymentPercent);</script></td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
											<br>
											<table>
												<tr>
													<td style="width:50%; vertical-align: top;" class="well">
														<table cellspacing=10>
												      		<tr>
												      			<th>
												      				Salary Increment vs Age
												      			</th>
												      			<th>
												      				21-28
												      				<input type="hidden" class="lowerLimitAge" value="21">
												      				<input type="hidden" class="upperLimitAge" value="28">
												      			</th>
												      			<th>
												      				28-35
												      				<input type="hidden" class="lowerLimitAge" value="28">
												      				<input type="hidden" class="upperLimitAge" value="35">
												      			</th>
												      			<th>
												      				35-50
												      				<input type="hidden" class="lowerLimitAge" value="35">
												      				<input type="hidden" class="upperLimitAge" value="50">
												      			</th>
												      			<th>
												      				50-60
												      				<input type="hidden" class="lowerLimitAge" value="50">
												      				<input type="hidden" class="upperLimitAge" value="60">
												      			</th>
												      			<th>
												      				60-80
												      				<input type="hidden" class="lowerLimitAge" value="60">
												      				<input type="hidden" class="upperLimitAge" value="80">
												      			</th>
												      			<th>
												      				80+
												      				<input type="hidden" class="lowerLimitAge" value="80">
												      				<input type="hidden" class="upperLimitAge" value="150">
												      			</th>
												      		</tr>
												      		<tr>
												      			<th>Increments In Self Salary (%)</th>
												      			<td id="td1"><script>getSelectBox('#td1','selfSalaryIncreaseRate','selfSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[0].selfSalaryIncreaseRate);</script></td>
												      			<td id="td2"><script>getSelectBox('#td2','selfSalaryIncreaseRate','selfSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[1].selfSalaryIncreaseRate);</script></td>
												      			<td id="td3"><script>getSelectBox('#td3','selfSalaryIncreaseRate','selfSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[2].selfSalaryIncreaseRate);</script></td>
												      			<td id="td4"><script>getSelectBox('#td4','selfSalaryIncreaseRate','selfSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[3].selfSalaryIncreaseRate);</script></td>
												      			<td id="td5"><script>getSelectBox('#td5','selfSalaryIncreaseRate','selfSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[4].selfSalaryIncreaseRate);</script></td>
												      			<td id="td6"><script>getSelectBox('#td6','selfSalaryIncreaseRate','selfSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[5].selfSalaryIncreaseRate);</script></td>
												      		</tr>
												      		<tr>
												      			<th>Increments In Spouse Salary (%)</th>
												      			<td id="td7"><script>getSelectBox('#td7','spouseSalaryIncreaseRate','spouseSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[0].spouseSalaryIncreaseRate);</script></td>
												      			<td id="td8"><script>getSelectBox('#td8','spouseSalaryIncreaseRate','spouseSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[1].spouseSalaryIncreaseRate);</script></td>
												      			<td id="td9"><script>getSelectBox('#td9','spouseSalaryIncreaseRate','spouseSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[2].spouseSalaryIncreaseRate);</script></td>
												      			<td id="td10"><script>getSelectBox('#td10','spouseSalaryIncreaseRate','spouseSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[3].spouseSalaryIncreaseRate);</script></td>
												      			<td id="td11"><script>getSelectBox('#td11','spouseSalaryIncreaseRate','spouseSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[4].spouseSalaryIncreaseRate);</script></td>
												      			<td id="td12"><script>getSelectBox('#td12','spouseSalaryIncreaseRate','spouseSalaryIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[5].spouseSalaryIncreaseRate);</script></td>
												      		</tr>
												      	</table>
													</td>
													<td style="width:10px;"></td>
													<td style="width:50%; vertical-align: top;" class="well">
														<table cellspacing=10>
												      		<tr>
												      			<th>
												      				Expense Increment vs Age
												      			</th>
												      			<th>21-28</th>
												      			<th>28-35</th>
												      			<th>35-50</th>
												      			<th>50-60</th>
												      			<th>60-80</th>
												      			<th>80+</th>
												      		</tr>
												      		<tr>
												      			<th>Increments In Expense (%)</th>
												      			<td id="td13"><script>getSelectBox('#td13','expenseIncreaseRate','expenseIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[0].expenseIncreaseRate);</script></td>
												      			<td id="td14"><script>getSelectBox('#td14','expenseIncreaseRate','expenseIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[1].expenseIncreaseRate);</script></td>
												      			<td id="td15"><script>getSelectBox('#td15','expenseIncreaseRate','expenseIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[2].expenseIncreaseRate);</script></td>
												      			<td id="td16"><script>getSelectBox('#td16','expenseIncreaseRate','expenseIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[3].expenseIncreaseRate);</script></td>
												      			<td id="td17"><script>getSelectBox('#td17','expenseIncreaseRate','expenseIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[4].expenseIncreaseRate);</script></td>
												      			<td id="td18"><script>getSelectBox('#td18','expenseIncreaseRate','expenseIncreaseRate',0,100,userDataOutput.fpmasterassumption.fpsalaryexpenseincrement[5].expenseIncreaseRate);</script></td>
												      		</tr>
												      		<tr><td>&nbsp;</td></tr>
												      		<tr><td>&nbsp;</td></tr>
												      	</table>
													</td>
												</tr>
											</table>
									      	<br>
											<button type="button" class="btn-flat inverse" onclick="javascript:location.reload();;">Reset</button>
											<button type="button" class="btn-flat inverse" onclick="javascript:makeJsonAdvanceSettings();">Update</button>
										
									
								</div>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-6">
							<a href="javascript:editResponse()" class="btn-flat inverse" style="width:100%; text-align: center;">
								<h4 style="color:#ffffff">Edit your response</h4>
							</a>
						</div>
						<div class="col-md-6">
							<a href="javascript:saveAndSubmit()" class="btn-flat success" style="width:100%; text-align: center;">
								<h4 style="color:#ffffff">Save and Submit</h4>
							</a>
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
					sliderRetirmentAge;
				
					$(function() {
					    var selectBox = $("select").selectBoxIt();
					});
					
					var index=1;
					$(document).ready(function(){
						$("#btnAddRow").click(function(){
					        $("#lifegoals").append('<tr><td><select id="lgOptions" class="lgOptions" name="test"> <option value="1" data-iconurl="img/icon_selfMarriage.png">Self Marriage</option> <option value="8" data-iconurl="img/icon_selfStudy.png">Self Higher study</option> <option value="3" data-iconurl="img/icon_bike.png">Two Wheeler</option> <option value="4" data-iconurl="img/icon_car.png">Car</option> <option value="5" data-iconurl="img/icon_home.png">House</option> <option value="6" data-iconurl="img/icon_vacation.png">Domestic vacation</option> <option value="7" data-iconurl="img/icon_vacation.png">International vacation</option> <option value="9" data-iconurl="img/icon_selfStudy.png">Children Higher Study</option> <option value="2" data-iconurl="img/icon_childrenMarriage.png">Children Marriage</option> <option value="0" data-iconurl="img/icon_others.png">Others</option></select><input id="lgOther" class="lgOther" type="text" id="txtOthers" style="display:none"></td><td><select id="lgFrequency" class="lgFrequency" name="testx"> <option value="0" >Only once</option> <option value="1" >Once every year</option> <option value="2" >Once in two year</option> <option value="3" >Once in three year</option> <option value="4" >Once in four year</option> <option value="5" >Once in five year</option> </select></td><td><div class="form-group"><div class="input-group date"><input type="text" class="form-control lgYear calender"/></div></div></td><td width=400><input type="text" class="form-control lgAmount txtRupee" aria-describedby="basic-addon1"></td><td><input type="checkbox" class="lgLoan" name="my-checkbox"></td><td width=100><img src="img/x.png" class="btnRemoveRow" width="30px" style="cursor:pointer;" onclick="javascript:removeThisLifeGoal(this)"></td></tr>');
					        
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
						});
						
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
					    addEventToLoanDropDown();
					    
					    
					    toggleOthers = function(opIndex,valueSelected){
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
					    }
					    
					    if(localStorage.getItem("lifeGoalsTable") != null){
							//$('#lifegoals').html('');
							//$('#lifegoals').html(localStorage.getItem("lifeGoalsTable"));
						}
					    
					    
					    $("#lifegoals").on('click', '#btnRemoveRow', function(){
					        $(this).parent().parent().remove();
					    });
					    
					    $('.calender').datetimepicker({
					    	viewMode: 'years',
					    	format: 'YYYY'
					    });
					    $('.clFinalYear').datetimepicker();
					    $('.lgYear').datetimepicker({
					    	viewMode: 'years',
					    	format: 'YYYY'
					    });
					});
					
					$('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '9999999999'}); 
					$('[data-submenu]').submenupicker();
				</script>
			
			<script>
			
			
			var rdata;
			var	assetJson;
			var	assetJsonFDOnly;
			var	expenditureJson;
			var	xStart;
			var	xEnd;
			//userDataInput = JSON.parse(localStorage.getItem("userDataOutput"));
			//userDataOutput = JSON.parse(localStorage.getItem("userDataOutput"));
			
			//initData(userDataOutput);
			
			$.ajax({
  			  type: "GET",
  			  url: _gc_url_fpo_get_assetExpenditureChart,
  			  data: null,
  			  dataType: 'json',
  			  contentType:"application/json; charset=utf-8",
  			  success: function(data){
  				  //console.log(data);
  					//processChartData(data);
  					console.log('jsp');
  					userDataInput = data;
  					userDataOutput = data;
  					initData(userDataOutput);
  			  }
  			});
			
			function processChartData(data)
			{
				$('#financialOutputChart').html('');
				initData(data);
				
			}
			
			function initData(data){
				try {
					data = JSON.parse(data);
			    } catch (e) {
			        console.log('response is json not a string');
			    }
				rdata = data;
				//console.log(data);
				xStart = data.age[0];
				xEnd = data.age[data.age.length-1];
				
				assetJson = "";
				assetJsonFDOnly = "";
				expenditureJson = "";
				ligoalsJson = "";
				
				for(i=0; i<data.age.length; i++){
					if(assetJson!=""){
						assetJson = assetJson + ",";
						assetJsonFDOnly = assetJsonFDOnly + ",";
						expenditureJson = expenditureJson + ",";
					}
					
					tempAsset = data.totalFinAsset[i];
					if(tempAsset<0){
						tempAsset=0;
					}
					
					tempAssetFDOnly = data.totalFinAssetOnlyFD[i];
					
					//tempAssetFDOnly = tempAssetFDOnly/2;
					if(tempAssetFDOnly<0){
						tempAssetFDOnly=1000;
					}
					
					
					assetJsonFDOnly  = assetJsonFDOnly + '{"x": ' + data.age[i] + ', "y" : ' + tempAssetFDOnly + ',"asset" : ' + tempAsset + ',"expense" : ' + data.totalLiabilities[i] + ',  "goal": null, "name": "Torstein worked alone", "image": "Torstein", "text": "Highcharts version 1.0 released", "title": "1.0"}';
					assetJson = assetJson + '{"x": ' + data.age[i] + ', "y" : ' + tempAsset + ',"asset" : ' + tempAsset + ',"expense" : ' + data.totalLiabilities[i] + ',  "goal": null, "name": "Torstein worked alone", "image": "Torstein", "text": "Highcharts version 1.0 released", "title": "1.0"}';
					expenditureJson = expenditureJson + '{"x": ' + data.age[i] + ', "y" : ' + data.totalLiabilities[i] + ', "asset" : ' + tempAsset + ',"expense" : '+ data.totalLiabilities[i] + ', "goal": null, "name": "Torstein worked alone", "image": "Torstein", "text": "Highcharts version 1.0 released", "title": "1.0"}';
				}
				assetJson = JSON.parse("["+assetJson+"]");
				assetJsonFDOnly = JSON.parse("["+assetJsonFDOnly+"]");
				expenditureJson = JSON.parse("["+expenditureJson+"]");
				console.log("assetJson :");
				console.log(assetJson);
				console.log("assetJsonFDOnly :");
				console.log(assetJsonFDOnly);
				console.log("expenditureJson : ");
				console.log(expenditureJson);
				
				ligoalsJson = "";
				for(i=0; i<data.lifeGoals.length; i++){
					if(ligoalsJson!=""){
						ligoalsJson = ligoalsJson + ",";
					}
					ligoalsJson = ligoalsJson + '{"y": 32, "shape": "url(img/goal_'+data.lifeGoals[i].goalDescriptionId+'.png)", "x": ' + data.lifeGoals[i].age + ', "titlex":"G", "frequency": '+data.lifeGoals[i].frequency+',"goalDescriptionId": '+data.lifeGoals[i].goalDescriptionId+',  "goalDescription" : "'+data.lifeGoals[i].goalDescription+'", "loanYesNo" : "'+data.lifeGoals[i].loanYesNo+'", "yearofRealization": '+data.lifeGoals[i].yearofRealization+', "inflatedAmount": "' +data.lifeGoals[i].inflatedAmount+ '" }';
				}
				ligoalsJson = JSON.parse("["+ligoalsJson+"]");
				console.log(ligoalsJson);
				something();
			} 
			
			function something(){
				var options = {
				        chart: {
				            events: {
				                //load: onChartLoad
				            },
				            zoomType : 'x',
				            	
				            	renderTo:'financialOutputChart'
				        },
				        legend: {
				            enabled: true,
				            floating: true,
				            verticalAlign: 'bottom',
				            align:'right',
				            padding: -10
				        },
				        rangeSelector: {
			                selected: 1
			            },
				        credits : false,
				        exporting : {
				        	enabled : false
				        },
			
				        xAxis: {
				            type: 'linear',
				            allowDecimals: false,
				            lineColor: '#000',
				            lineWidth: 1,
				            tickWidth: 1,
				            tickColor: '#000',
				            minTickInterval: 1,
				            title: {
				                text: 'Age'
				            },
				            labels: {
				                align: 'left'
				            },
				            plotBands: [
				        				{
				        	                from: 0,
				        	                to: 28,
				        	                color: 'rgba(255,172,129,0.2)',
				        	                label: {
				        	                    text: '0-28',
				        	                    style: {
				        	                        color: '#999999'
				        	                    },
				        	                    y: 50
				        	                }
				        	            },
				        				{
				        	                from: 28,
				        	                to: 35,
				        	                color: 'rgba(255,146,139,0.2)',
				        	                label: {
				        	                    text: '29-35',
				        	                    style: {
				        	                        color: '#999999'
				        	                    },
				        	                    y: 50
				        	                }
				        	            },
				        				{
				        	                from: 35,
				        	                to: 50,
				        	                color: 'rgba(254,195,166,0.2)',
				        	                label: {
				        	                    text: '36-50',
				        	                    style: {
				        	                        color: '#999999'
				        	                    },
				        	                    y: 50
				        	                }
				        	            },
				        				{
				        	                from: 50,
				        	                to: 60,
				        	                color: 'rgba(239,233,174,0.2)',
				        	                label: {
				        	                    text: '51-60',
				        	                    style: {
				        	                        color: '#999999'
				        	                    },
				        	                    y: 50
				        	                }
				        	            },
				        				{
				        	                from: 60,
				        	                to: 80,
				        	                color: 'rgba(231,223,198,0.2)',
				        	                label: {
				        	                    text: '61-80',
				        	                    style: {
				        	                        color: '#999999'
				        	                    },
				        	                    y: 50
				        	                }
				        	            },
				        				{
				        	                from: 80,
				        	                to: 86,
				        	                color: 'rgba(205,234,192,0.2)',
				        	                fillOpacity: 0.1,
				        	                label: {
				        	                    text: '80+',
				        	                    style: {
				        	                        color: '#999999'
				        	                    },
				        	                    y: 50
				        	                }
				        	            }]
				        },
			
				        title: {
				            text: ''
				        },
			
				        tooltip: {
				            style: {
				                width: '250px'
				            }
				        },
			
				        yAxis: [{
				            allowDecimals: false,
				            //max: 100,
				            lineColor: '#000',
				            lineWidth: 1,
				            tickWidth: 1,
				            tickColor: '#000',
				            labels: {
				                formatter: function() {
				                    //return this.value + ' %';
				                    if(this.value > 9999999)
				                    {
				                    	return(this.value/10000000 + ' cr') 
				                    }
				                    else if(this.value > 99999){
				                    	return(this.value/100000 + ' Lakh') 
				                    }
				                    
				                }
				            },
				            
				            title: {
				                text: ''
				            },
				            //opposite: true,
				            gridLineWidth: 0
				        }],
			
				        plotOptions: {
				            series: {
				                marker: {
				                    enabled: false,
				                    symbol: 'circle',
				                    radius: 2
				                },
				                fillOpacity: 0.5
				            },
				            flags: {
				                tooltip: {
				                    xDateFormat: '%B %e, %Y'
				                }
				            }
				        },
			
				        series: [{
				            yAxis: 0,
				            name: 'Wealth with MMF',
				            id: 'employees',
				            type: 'spline',
				            linecap: "round",
				            //step: 'left',
				            color: '#7BDB7B',
				            radius: 2,
				            tooltip: {
				                headerFormat: '<b>Age : </b>{point.x}<br>',
				                pointFormat: '<b>Assets : </b>{point.asset}<br><b>Expenses : </b>{point.expense}<br>',
				                valueSuffix: ' '
				            },
				            data: assetJson
				        },
				        {
				            yAxis: 0,
				            name: 'Wealth with FD Investments',
				            id: 'tempLine',
				            type: 'spline',
				            linecap: "round",
				            //step: 'left',
				            color: '#0C95BE',
				            radius: 2,
				            tooltip: {
				                headerFormat: '<b>Age : </b>{point.x}<br>',
				                pointFormat: '<b>Assets : </b>{point.asset}<br><b>Expenses : </b>{point.expense}<br>',
				                valueSuffix: ' '
				            },
				            data: assetJsonFDOnly
				        },
				        {
				        	yAxis: 0,
				            id: 'seriesExpenditure',
				            type: 'column',
				            linecap: "round",
				            name: 'Annual Expenditure',
				            data: expenditureJson,
				            color: 'rgba(235,62,66,0.5)',
				            radius: 2,
				            tooltip: {
				            	headerFormat: '<b>Age : </b>{point.x}<br>',
				                pointFormat: '<b>Assets : </b>{point.asset}<br><b>Expenses : </b>{point.expense}<br>',
				                valueSuffix: ' '
				            }
				        },{
				        	type: 'flags',
				            name: 'Highcharts',
				            color: '#333333',
				            shape: 'circlepin',
				            tooltip: {
				                headerFormat: '<span style="font-size: 11px;color:#666"><b>Age : </b>{point.x}</span><br>',
				                pointFormat: '<b>Goal : </b>{point.goalDescription}<br></b><b>Frequency : </b>{point.frequency}<br></b><b>Loan : </b>{point.loanYesNo}<br></b><b>Goal Year : </b>{point.yearofRealization}<br> <b>Future Amount : </b>{point.inflatedAmount}</b>',
				                //pointFormat: getGoalToolTip(this),
				                //valueSuffix: '',
				                
				            },
				            onSeries: 'seriesExpenditure',
				            data: ligoalsJson,
				            showInLegend: false
				        }]
				    };
			
				    //chartx = $('#financialOutputChart').highcharts(options);
				    chartx = new Highcharts.Chart(options);
				    chartx.xAxis[0].setExtremes(
				    		xStart-1,
				    		xEnd+1
				    );
			}
			
			function getGoalToolTip(point){
				//'<b>Goal : </b>{point.goalDescription}<br></b><b>Frequency : </b>{point.frequency}<br></b><b>Loan : </b>{point.loanYesNo}<br></b><b>Goal Year : </b>{point.yearofRealization}<br> <b>Future Amount : </b>{point.inflatedAmount}</b>',
				console.log('point :');
				console.log(point);
				return ''
			}
			var chartx;
			function onChartLoad() {
			
			    var centerX = 140,
			        centerY = 110,
			        path = [],
			        angle,
			        radius,
			        badgeColor = Highcharts.Color(Highcharts.getOptions().colors[0]).brighten(-0.2).get(),
			        spike,
			        empImage,
			        big5,
			        label,
			        left,
			        right,
			        years,
			        renderer;
			
			    if (this.chartWidth < 530) {
			        return;
			    }
			
			    // Draw the spiked disc
			    for (angle = 0; angle < 2 * Math.PI; angle += Math.PI / 24) {
			        radius = spike ? 80 : 70;
			        path.push(
			            'L',
			            centerX + radius * Math.cos(angle),
			            centerY + radius * Math.sin(angle)
			        );
			        spike = !spike;
			    }
			    path[0] = 'M';
			    path.push('z');
			    this.renderer.path(path)
			        .attr({
			            fill: badgeColor,
			            zIndex: 6
			        })
			        .add();
			
			    // Employee image overlay
			    empImage = this.renderer.path(path)
			        .attr({
			            zIndex: 7,
			            opacity: 0,
			            stroke: badgeColor,
			            'stroke-width': 1
			        })
			        .add();
			
			    // Big 5
			    big5 = this.renderer.text('50')
			        .attr({
			            zIndex: 6
			        })
			        .css({
			            color: 'white',
			            fontSize: '100px',
			            fontStyle: 'italic',
			            fontFamily: '\'Brush Script MT\', sans-serif'
			        })
			        .add();
			    big5.attr({
			        x: centerX - big5.getBBox().width / 2,
			        y: centerY + 14
			    });
			
			    // Draw the label
			    label = this.renderer.text('Highcharts Anniversary')
			        .attr({
			            zIndex: 6
			        })
			        .css({
			            color: '#FFFFFF'
			        })
			        .add();
			
			    left = centerX - label.getBBox().width / 2;
			    right = centerX + label.getBBox().width / 2;
			
			    label.attr({
			        x: left,
			        y: centerY + 44
			    });
			
			    // The band
			    left = centerX - 90;
			    right = centerX + 90;
			    this.renderer
			        .path([
			            'M', left, centerY + 30,
			            'L', right, centerY + 30,
			            right, centerY + 50,
			            left, centerY + 50,
			            'z',
			            'M', left, centerY + 40,
			            'L', left - 20, centerY + 40,
			            left - 10, centerY + 50,
			            left - 20, centerY + 60,
			            left + 10, centerY + 60,
			            left, centerY + 50,
			            left + 10, centerY + 60,
			            left + 10, centerY + 50,
			            left, centerY + 50,
			            'z',
			            'M', right, centerY + 40,
			            'L', right + 20, centerY + 40,
			            right + 10, centerY + 50,
			            right + 20, centerY + 60,
			            right - 10, centerY + 60,
			            right, centerY + 50,
			            right - 10, centerY + 60,
			            right - 10, centerY + 50,
			            right, centerY + 50,
			            'z'
			        ])
			        .attr({
			            fill: badgeColor,
			            stroke: '#FFFFFF',
			            'stroke-width': 1,
			            zIndex: 5
			        })
			        .add();
			
			    // 2009-2014
			    years = this.renderer.text('2009-2014')
			        .attr({
			            zIndex: 6
			        })
			        .css({
			            color: '#FFFFFF',
			            fontStyle: 'italic',
			            fontSize: '10px'
			        })
			        .add();
			    years.attr({
			        x: centerX - years.getBBox().width / 2,
			        y: centerY + 62
			    });
			
			
			    // Prepare mouseover
			    renderer = this.renderer;
			    if (renderer.defs) { // is SVG
			        $.each(this.get('employees').points, function () {
			            var point = this,
			                pattern;
			            if (point.image) {
			                pattern = renderer.createElement('pattern').attr({
			                    id: 'pattern-' + point.image,
			                    patternUnits: 'userSpaceOnUse',
			                    width: 400,
			                    height: 400
			                }).add(renderer.defs);
			                renderer.image(
			                    'https://www.highcharts.com/images/employees2014/' + point.image + '.jpg',
			                    centerX - 80,
			                    centerY - 80,
			                    160,
			                    213
			                ).add(pattern);
			
			                Highcharts.addEvent(point, 'mouseOver', function () {
			                    empImage
			                        .attr({
			                            fill: 'url(#pattern-' + point.image + ')'
			                        })
			                        .animate({ opacity: 1 }, { duration: 500 });
			                });
			                Highcharts.addEvent(point, 'mouseOut', function () {
			                    empImage.animate({ opacity: 0 }, { duration: 500 });
			                });
			            }
			        });
			    }
			}
			$('.calender').datetimepicker({
		    	viewMode: 'years',
		    	format: 'YYYY'
		    });
		    $('.clFinalYear').datetimepicker();
		    $('.lgYear').datetimepicker({
		    	viewMode: 'years',
		    	format: 'YYYY'
		    });
			//FinancialPlannerOutput.... editRiskScore, editSavingsRate, editAllocationToMMF, editRetirmentAge, editAssumptions, editLifeGoals,  
			</script>
				
		</div>
		
    	<div ng-repeat="n in _.range(1,100)">
    		{{n}}
    	</div>

		<!-- MixPanel -->
		<script type="text/javascript">
			clevertap.event.push("FP Response Submitted");			
		</script>
		
		<!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/FinancialPlannerOutput.js?v=1" charset="UTF-8"></script>
        <script src="js/directives/financialPlannerOutput.js" charset="UTF-8"></script>
    </body>
</html>

<!-- statusCheckForAssetLineWithFDOnly, saveFinancialPlanner -->

