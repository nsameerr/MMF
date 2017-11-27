<%@page import="com.gtl.mmf.controller.UserSessionBean"%>
<%@ page import="java.io.*,java.util.*" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html>
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Manage My Fortune</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="css/custom.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="shortcut icon" href="images/favicon.ico">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
    <![endif]-->
<!-- mmfdev -->
<link rel="stylesheet" href="css/jquery.toast.min.css" />
<link rel="stylesheet" href="css/spinner.css">
<script src="js/vendor/angular.min.js"></script>
<script src="js/vendor/jquery.toast.min.js"></script>
<script src="js/constants.js"></script>
<script src="js/main.js"></script>
<script src="js/spinner.js"></script>

<script src="https://code.highcharts.com/stock/highstock.js"></script>
    <!-- <script type="text/javascript" src="js/highcharts.js"></script> -->    
    <script src="http://code.highcharts.com/modules/exporting.js"></script>
    <script src="http://highcharts.github.io/export-csv/export-csv.js"></script> 
    <!--FinancialPlanner Output Chart Script-->
    <script src="js/financialPlannerChart.js"></script>

    <!--Risk Chart-->
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
    <script type="text/javascript" src="js/kuma-gauge.jquery.js"></script>
	<script src="js/riskProfileChart.js"></script>
	
<style type="text/css">
	.black{
	color: #333333;
    font-family: 'ProximaNova-Regular';
	}
	
	.about_profile_section {
	    background: #fff;
	    width: 100%;
	    height: auto;
	    margin: 10px 0px 0px 0px;
	    border-top-left-radius: 5px;
	    border-top-right-radius: 5px;
	    display: table;
	    border-bottom: 4px solid #a0a0a0;
	}
</style>
<!-- Facebook Pixel Code -->
		<script>
		!function(f,b,e,v,n,t,s)
		{if(f.fbq)return;n=f.fbq=function(){n.callMethod?
		n.callMethod.apply(n,arguments):n.queue.push(arguments)};
		if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
		n.queue=[];t=b.createElement(e);t.async=!0;
		t.src=v;s=b.getElementsByTagName(e)[0];
		s.parentNode.insertBefore(t,s)}(window,document,'script',
		'https://connect.facebook.net/en_US/fbevents.js');
		fbq('init', '395130707546730');
		//fbq('track', 'PageView');
		</script>
		<noscript>
		<img height="1" width="1" src="https://www.facebook.com/tr?id=395130707546730&ev=PageView&noscript=1"/>
		</noscript>
<!-- End Facebook Pixel Code -->
</head>
<script type="text/javascript">
	fbq('trackCustom', 'investorPortfolioEdit');
</script>
<body class="investor_profile_edit" ng-app="appMMF" ng-controller="InvestorProfile" data-ng-init="init()">
	
	<!-- Start Google Tag Manager -->
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
    <script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-69307705-1', 'auto');
		  ga('send', 'pageview');
	</script>
    <!-- End Google Tag Manager -->
	
	<%
    		Map<String, Object> storedValues =null;
    		String userFirstName = "", userMail;
    		String userType="",userFpSubmitted="",userRpSubmitted="";
			if(session != null && session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userMail=(String)storedValues.get("username");
					userType=(String)storedValues.get("userType");										
					UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
					if(userSessionBean!= null){
						userFirstName = (String)userSessionBean.getFirstName();
						userType = userSessionBean.getUserType();
					}
			}
			else{
				response.sendRedirect("/faces/index.xhtml");
			}			
		%>
	<input type="hidden" id="userFirstName" value="<%= userFirstName %>" />
	<input type="hidden" id="userType" value="<%= userType %>" />
	<input type="hidden" id="userFpSubmitted" value="<%= userFpSubmitted %>" />
	<input type="hidden" id="userRpSubmitted" value="<%= userRpSubmitted %>" />


	<div id="loadingOverlay" style="display: none">
		<div class="rippleSpinner">
			<img src="img/gears.svg" />
		</div>
	</div>
	<!-- Header Section -->
	<post-login-header></post-login-header>
	<!-- End -->

	<!-- Output View Page -->
	<main class="inner_page">
	<div class="scroll-to-bottom" style="display: block">
		<i class="fa fa-angle-down"></i>
	</div>
	<div class="scroll-to-top">
		<i class="fa fa-angle-up"></i>
	</div>
	<div class="page_header_section">
		<h3 class="finance_pln">Investor Profile</h3>
	</div>
	<section class="page_body_section">
		<!-- div class="breadcrumb_menu">
			<ul>
				<li><a href="">Home</a></li>
				<li><a href="">Investor Profile</a></li>
			</ul>
		</div -->
		<div class="about_profile_section" ng-cloak>
			<h4 class="about_head">About {{investor.profile.firstName}} {{investor.profile.lastName}}</h4>
			<div class="profile_block col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="column1 col-lg-4 col-md-4 col-sm-4 col-xs-12">
					<div class="profile_name">
						<figure>
							<img src="images/default-user.png" class="dp_pic" alt="Profile Pictures" id="investorProfilePic" ng-src="{{investor.pic}}" ng-show="investor.pic != '' && investor.pic != null" />
							<img src="images/default-user.png" class="dp_pic" alt="Profile Pictures" ng-show="investor.pic == '' || investor.pic == null"/>
							<div class="upload_row">
								<span id="fileselector"> <label class="upload_outer" for="investorPic">
										<div class="upload_icon">&nbsp;</div> <input id="investorPic" type="file" multiple="" onchange="angular.element(this).scope().uploadNewPic();" accept=".jpeg,.pdf,.PNG,.jpg,.PDF,.png,.JPEG,.JPG" style="display:none" data-ng-if="filterTag" >
										<div class="uploadtxt">
											Upload <span class="mobile_hide"></span>
										</div>
								</label>
								</span>
							</div>
						</figure>
						<div class="dp_names">
							<h4>{{investor.profile.firstName}} {{investor.profile.lastName}}</h4>
							<h5>{{investor.profile.dob | date : dd-MM-yyyy}}</h5>
						</div>
					</div>
				</div>
				<div class="column2 col-lg-5 col-md-5 col-sm-5 col-xs-12">
					<ul class="details">
						<li>Marital Status:<span> {{investor.profile.maritalStatus}} </span>
						</li>
						<li>Aadhar:<span> {{investor.profile.uidAadhar}} </span></li>
						<li>PAN:<span> {{investor.profile.panNo | uppercase}} </span></li>
					</ul>
				</div>
				<div class="column3 col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<a class="moderate_btn" href="" style="display: none">Moderately Aggresive</a>
				</div>
			</div>
			<div class="profile_desc col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<input class="filed_desc" type="text" id="pf_description" name="pf_description" placeholder="One Line Description" ng-model="investor.profile.masterCustomerQualificationTb.oneLineDesc">
					<textarea name="profile_summary" id="profile_summary" placeholder="Detail Summary" ng-model="investor.profile.masterCustomerQualificationTb.aboutMe"></textarea>
					
					<h5>Asset Allocated</h5>
					<!-- <h4> <i class="fa fa-inr" aria-hidden="true"></i> 50,000</h4> -->

					<div class="asset_amount">
						<div class="edit_graph_data">
							<div class="age1">
								<label for="estim_amount"><i class="fa fa-inr"></i></label>
								<!-- <input class="estim_amt" type="text" id="income" readonly style="border: 0;">
								<div id="slider-range-amount" class="edit_graph_bar"></div> -->
								{{formatInputString(investor.profile.totalFunds)}}
							</div>
						</div>
					</div>


					<span class="linked_in_prfl">View my Profile on <a href="">Linkedin</a></span>
					<input type="submit" name="profile_update" id="profile_update" value="Update" class="update_btn" ng-click="saveInvestorProfile()"/>


				</div>
				<div class="tabs_blk col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#profile_tab1">Qualifications</a></li>
						<li><a data-toggle="tab" href="#profile_tab2">Professional Details</a></li>
						<li><a data-toggle="tab" href="#profile_tab3">Bank Details</a></li>
						<li><a data-toggle="tab" href="#profile_tab4">Address</a></li>
					</ul>
					<div class="tab-content">
						<div id="profile_tab1" class="tab-pane fade in active">
							<div class="tabs_table">
								<div class="tabs_column">
									<h5>Primary Degree</h5>
									<h5 class="black">
										<input type="text" name="ivp_degree" id="ivp_degree3"  ng-model="investor.profile.masterCustomerQualificationTb.primaryQualificationDegree">
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Year</h5>
									<h5 class="black">
										<input type="text" id="ivp_year" name="ivp_year3" class="numericOnly" maxlength="4" ng-model="investor.profile.masterCustomerQualificationTb.primaryQualificationYear">
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Institute</h5>
									<h5 class="black">
										<input type="text" name="ivp_institute" id="ivp_institute3"  ng-model="investor.profile.masterCustomerQualificationTb.primaryQualificationInstitute">
									</h5>
								</div>
							</div>
							<!-- secondary -->
							<div class="tabs_table">
								<div class="tabs_column">
									<h5>Secondary Degree</h5>
									<h5 class="black">
										<input type="text" name="ivp_degree" id="ivp_degree2"  ng-model="investor.profile.masterCustomerQualificationTb.secondaryQualificationDegree">
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Year</h5>
									<h5 class="black">
										<input type="text" id="ivp_year" name="ivp_year2" class="numericOnly" maxlength="4" ng-model="investor.profile.masterCustomerQualificationTb.secondaryQualificationYear" >
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Institute</h5>
									<h5 class="black">
										<input type="text" name="ivp_institute" id="ivp_institute2"  ng-model="investor.profile.masterCustomerQualificationTb.secondaryQualificationInstitute">
									</h5>
								</div>
							</div>
							<!-- tertaruy -->
							<div class="tabs_table">
								<div class="tabs_column">
									<h5>Tertiary Degree</h5>
									<h5 class="black">
										<input type="text" name="ivp_degree" id="ivp_degree1"  ng-model="investor.profile.masterCustomerQualificationTb.tertiaryQualificationDegree">
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Year</h5>
									<h5 class="black">
										<input type="text" id="ivp_year1" name="ivp_year1" class="numericOnly" maxlength="4" ng-model="investor.profile.masterCustomerQualificationTb.tertiaryQualificationYear"> 
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Institute</h5>
									<h5 class="black">
										<input type="text" name="ivp_institute" id="ivp_institute1"  ng-model="investor.profile.masterCustomerQualificationTb.tertiaryQualificationInstitute">
									</h5>
								</div>
							</div>
						</div>
						<div id="profile_tab2" class="tab-pane fade">
							<div class="tabs_table">
								<div class="tabs_column">
									<h5>Current Company</h5>
									<h5 class="black">
										<input type="text" name="ivp_company" id="ivp_company" value="MMF" ng-model="investor.profile.workOrganization">
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Designation</h5>
									<h5 class="black">
										<input type="text" name="ivp_designation" id="ivp_designation" value="Manager" ng-model="investor.profile.jobTitle">
									</h5>
								</div>
								<div class="tabs_column" style="display:none">
									<h5>Department</h5>
									<h5 class="black">
										<input type="text" name="ivp_department" id="ivp_department" value="Finance">
									</h5>
								</div>
							</div>
						</div>
						<div id="profile_tab3" class="tab-pane fade">
							<div class="tabs_table">
								<div class="tabs_column" style="width: 50%">
									<h5>Bank Name : </h5>
									<h5 class="black">{{investor.profile.bankName}}</h5>
								</div>
								<div class="tabs_column" style="width: 50%">
									<h5>Address : </h5>
									<h5 class="black">
										<!-- <input type="text" name="ivp_bank" id="ivp_bank" value="Indian Bank"> -->
										{{investor.profile.bankBuilding}}<br>
										{{investor.profile.bankStreet}} {{investor.profile.bankArea}}<br>
										{{investor.profile.bankCity}} {{investor.profile.bankPincode}}
									</h5>
								</div>	
							</div>
							<div class="tabs_table">	
								<div class="tabs_column" style="width: 50%">
									<h5 >Account Number : </h5>
									<h5 class="black">{{investor.profile.bankAccountNo}}</h5>
								</div>
								<div class="tabs_column" style="width: 50%">
									<h5 >Account Type : </h5>
									<h5 class="black">{{investor.profile.bankSubtype | uppercase}}</h5>
								</div>
							</div>
							<div class="tabs_table">
								
								<div class="tabs_column" style="width: 50%">
									<h5 >IFSC no : </h5>
									<h5 class="black">{{investor.profile.ifscCode | uppercase}}</h5>
								</div>
								<div class="tabs_column" style="width: 50%">
									<h5 >MICR No : </h5>
									<h5 class="black">{{investor.profile.micrNumber}}</h5>
								</div>
							</div>
							
							
						</div>
						<div id="profile_tab4" class="tab-pane fade">
							<div class="tabs_table">
								<div class="tabs_column">
									<h5>Address : </h5>
									<h5 class="black">
										{{investor.profile.homeAddress1.replace("|",",")}}
									</h5>
								</div>
							</div>
							<div class="tabs_table">
								<div class="tabs_column">
									<h5>Country : </h5>
									<h5 class="black">
										{{investor.profile.homeAddressCountry}}
									</h5>
								</div>
								<div class="tabs_column">
									<h5>City : </h5>
									<h5 class="black">
										{{investor.profile.homeAddressCity}}
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Pincode</h5>
									<h5 class="black">
										{{investor.profile.homeAddressPincode}}
									</h5>
								</div>
							</div>
							<div class="tabs_table">
								<div class="tabs_column">
									<h5>Mobile : </h5>
									<h5 class="black">
										{{investor.profile.mobile}}
									</h5>
								</div>
								<div class="tabs_column">
									<h5>Email : </h5>
									<h5 class="black">
										{{investor.profile.email}}
									</h5>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="chart_section">
			<div class="high_chart">
				<h4 class="chart_head">Financial Planner Output</h4>
				<div class="tab_controls">
					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#chart_view">Chart View</a></li>
						<li><a data-toggle="tab" href="#list_view">List View</a></li>
					</ul>
					<h6 class="note">Do not let your wealthline fall to zero</h6>
					<div class="tab-content">
						<div id="chart_view" class="tab-pane fade in active">
							<div id="inv_portfolio_high_chart" class=""></div>
							<!-- div class="x_axis_head">
								<h5>Age</h5>
							</div -->
							<div class="legend">
								<span class="leg leg_1">Wealth with MMF</span> 
								<!-- span class="leg leg_2">Wealth with FD Investments</span --> 
								<span class="leg leg_3">Annual Expenditure</span> 
								<span class="leg leg_4">Life Goals</span>
							</div>
						</div>
						<div id="list_view" class="tab-pane fade">
							
							<!--Table View-->
                                <financial-planner-table-view></financial-planner-table-view>
						</div>
					</div>
				</div>
			</div>
			<div class="meter_chart">
				<h4 class="chart_head">Risk Profile Output</h4>
				<div class="gauge_section">
					<div class="edit_inputgraphtxt"></div>
				</div>
				<div class="slider_section">
					<div class="edit_graph_row">
						Saving Rate<br>
						<div class="edit_graph_data">
							<div class="age1">
								<div id="slider-range-min" class="edit_graph_bar"></div>
								<input type="text" id="age" readonly style="border: 0;">
								<label for="age">%</label>
							</div>
						</div>
					</div>
					<div class="edit_graph_row">
						Allocation to MMF<br>
						<div class="edit_graph_data">
							<div class="age1">
								<div id="slider-range-min2" class="edit_graph_bar"></div>
								<input type="text" id="age2" readonly style="border: 0;">
								<label for="age2">%</label>
							</div>
						</div>
					</div>
					<div class="edit_graph_row">
						Retirement Age<br>
						<div class="edit_graph_data">
							<div class="age1">
								<div id="slider-range-min3" class="edit_graph_bar"></div>
								<input type="text" id="age3" readonly style="border: 0;">
								<label for="age3"></label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- <div class="life_goal_section">
			<div class="header_block">
				<h4>Life Goals</h4>
			</div>
			<div id="goal_table" class="collapse in">
				<div class="goals_table">
					<div class="goals_row head">
						<div class="column column1">
							<h5>Life Goals</h5>
						</div>
						<div class="column column2">
							<h5>Frequency</h5>
						</div>
						<div class="column column3">
							<h5>Years of Realization</h5>
						</div>
						<div class="column column4">
							<h5>Current Estimated Amount</h5>
						</div>
						<div class="column column5">
							<h5>Loan</h5>
						</div>
					</div>
					<div id="added_goal_row" class="goals_row body">
						<div class="column column1">
							<select class="goal_select"><option>Internal Vacation</option>
								<option>Domestic Vacation</option></select>
						</div>
						<div class="column column2">
							<select class="goal_select"><option>Once in two year</option>
								<option>Every Month</option></select>
						</div>
						<div class="column column3">
							<select class="goal_select"><option>2019</option>
								<option>2017</option></select>
						</div>
						<div class="column column4">
							<div class="edit_graph_row">
								<div class="edit_graph_data">
									<div class="age1">
										<label for="estim_amount"><i class="fa fa-inr"></i></label>
										<input class="estim_amt" type="text" id="estim_amount" readonly style="border: 0;">
										<div id="estim_amt_slider" class="edit_graph_bar"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="column column5">
							<div class="switch_tog">
								<div class="switch tab_controls">
									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab">Savings</a></li>
										<li><a data-toggle="tab">Loan</a></li>
									</ul>
								</div>
							</div>
							<div class="ed_del uneditable">
								<span id="delete_row" class="delete"><a><i class="fa fa-times" aria-hidden="true"></i></a></span>
							</div>
						</div>
					</div>
				</div>
				<div class="add_row_blk">
					<a class="add_row" id="add_row"></a>
				</div>



			</div>
		</div> -->

<div class="life_goal_section">
                <div class="header_block">
                    <h4>Life Goals</h4>
                </div>
                <div class="collapse in">
                    <div class="goals_table">
                        <div class="goals_row head">
                            <div class="column column1">
                                <h5>Life Goals</h5>
                            </div>
                            <div class="column column2">
                                <h5>Frequency</h5>
                            </div>
                            <div class="column column3">
                                <h5>Years of Realization</h5>
                            </div>
                            <div class="column column4">
                                <h5>Current Estimated Amount</h5>
                            </div>
                            <div class="column column5">
                                <h5>Loan/Savings</h5>
                            </div>
                        </div>
                        
                        <div id="added_goal_row" class="goals_row body" ng-repeat="lg in investor.financialPlanner.lifeGoals | orderBy:'-yearofRealization':true" ng-cloak>
                            <div class="column column1">
                                <h5>{{lg.goalDescription}}<!-- Domestic Vacation --></h5>
                            </div>
                            <div class="column column2">
                                <h5><span class="head_mobile">Frequency </span>{{lg.frequencyDesc}} <!-- Once in two year --></h5>
                            </div>
                            <div class="column column3">
                                <h5><span class="head_mobile">Years of Realization </span>{{lg.yearofRealization}} <!-- 2017 --></h5>
                            </div>
                            <div class="column column4">
                                <h5><span class="head_mobile">Estimated Amount </span> <i class="fa fa-inr"></i> {{formatInputString(lg.estimatedAmount) }}<!-- 5000000 --></h5>
                            </div>
                            <div class="column column5">
                                <h5 ng-class=" lg.loanYesNo == 'Yes' ? 'loan' : 'savings' "><!-- Loan -->{{lg.loanYesNo == 'Yes' ? 'Loan' : 'Savings'}}</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="button_set" ng-if="actionText.length != 0">
                    <!-- <form> -->
                        <input type="submit" id="button-0" value="Reject" class="cancel" ng-click="callToAction(investor.profile.customerId, 0)"/>
                        <input type="submit" id="button-1" value="Accept" class="save_ed" ng-click="callToAction(investor.profile.customerId, 1)"/>
                    <!-- </form> -->
                </div>
                <!-- Response Message Div -->
                <!-- <div class="row" ></div>   -->
                <div class="msg_box error" style="display:none;" ng-attr-id="{{'div-'+investor.profile.customerId}}"> <span class="glyphicon glyphicon-remove"></span><!--  Error message --> </div>
            </div>
	</section>
	</main>
	<!-- End -->

	<!-- Footer Section -->
	<footer class="inner_class">
		<div class="footer">
			<div class="social_icons">
				<a class="facebook" href="#"><i class="fa fa-facebook"></i></a><a class="twitter" href="#"><i class="fa fa-twitter"></i></a><a class="google_plus" href="#"><i class="fa fa-google-plus"></i></a>
			</div>
			<ul class="footer_links">
				<li><a href="#">About Us</a></li>
				<li><a href="#">Contact Us</a></li>
				<li><a href="#">Legal Terms</a></li>
				<li><a href="#">Privacy Policy</a></li>
			</ul>
			<h6 class="copyright">
				&copy;
				<script type="text/javascript"> var now = new Date(); var d = now.getFullYear(); document.write(d);</script>
				MMF Investor Registration Pvt. Ltd.
			</h6>
		</div>
	</footer>
	<!-- End -->
	
	<!-- Success Modal start -->
	  <div class="msg_box_pp success modal fade " id="modalSuccess" role="dialog">
	    <div class="modal-dialog">    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header">
	          <!--<button type="button" class="close" data-dismiss="modal">&times;</button>-->
	          <h4 class="modal-title"> <span class="glyphicon glyphicon-ok-sign"></span> </h4>
	        </div>
	        <div class="modal-body"> <h3>Success </h3> Profile updated successfully. </div>
			<div class="modal-footer">
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
	      </div>      
	    </div>
	  </div>                    
	 <!-- Success Modal end -->
	  <!-- Alert Modal start -->
	  <div class="msg_box_pp warning modal fade " id="modalWarning" role="dialog">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <h4 class="modal-title"><span class="glyphicon glyphicon-alert"></span></h4>
	        </div>
	        <div class="modal-body"> <h3>Alert Message Title </h3> Alert message body content </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div> 
	      </div>      
	    </div>
	  </div>                    
	 <!-- Alert Modal end -->
	  
	 <!-- Error Modal start -->
	  <div class="msg_box_pp error modal fade " id="modalError" role="dialog">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header"><h4 class="modal-title"><span class="glyphicon glyphicon-remove"></span> </h4></div>
	        <div class="modal-body"> <h3>Error </h3>  Error in updating profile details. </div>
	          <div class="modal-footer">
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
	      </div>      
	    </div>
	  </div>                    
	 <!-- Error Modal end -->      
	 
	 <!-- Modal -->
	  <div class="modal fade" id="rpInfoModal" role="dialog">
	    <div class="modal-dialog">
	    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header" style="background:#4aae3c ; color:white">
	          <button type="button" class="close" data-dismiss="modal">&times;</button>
	          <h4 class="modal-title"> <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> Profile Details</h4>
	        </div>
	        <div class="modal-body">
	          <p style="padding:5%;text-align:center">
	          		You are Almost there !<br>Please Update for Profile Details <br> As it Help us understand you better.
	          </p>
	        </div>
	        <div class="modal-footer" style="display:none;">
	          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
	      </div>
	      
	    </div>
	  </div>
	<!-- modal end --> 
	
	
	<script>
  $( function() {
<!-- Investor Amount -->
    $( "#slider-range-amount" ).slider({
      range: "min",
      value:50000,
      min:10000,
      max:200000,
      slide: function( event, ui ) {
		   $("#income").val(format(ui.value)); 
      }
    });
     $("#income").val(format($( "#slider-range-amount" ).slider( "value" ))); 
<!-- Investor Amount -->
	  } );	  
</script>

	<!-- Year only scripts start -->
	<script>
  //$( function() {
   // $( "#calendar" ).datepicker({ dateFormat:'M dd, yy' });
	//$( "#ivp_year" ).datepicker({ dateFormat:'yy', changeYear:true, yearRange:'1970:2016', changeMonth:true, showButtonPanel: true, });
  //});
  
   $(function(){
    /* $('.calendar').datepicker({
        changeMonth: false,
        changeYear: true,
        showButtonPanel: false,
        //yearRange: '1970:2013', // Optional Year Range
        dateFormat: 'yy',
        onClose: function(dateText, inst) { 
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker('setDate', new Date(year, 0, 1));
        }}); */
    });  
</script>
	<style>
.ui-datepicker-calendar, .ui-datepicker-month {
	display: none;
}
 /* Style to hide Dates / Months */
</style>
	<!-- Year only scripts end -->

	<script type="text/javascript" src="js/custom_scripts.js"></script>
	<!-- mobile touch slider -->
	<script src="js/jquery-punch.js" language="javascript"></script>
	<!-- mobile touch slider -->
	<!-- Modules -->
	<script src="js/app.js"></script>

	<!-- Controllers -->
	
	<script src="js/controllers/InvestorProfile-v3.js?v=1" charset="UTF-8"></script>
	<!-- script src="js/directives/investorProfile.js" charset="UTF-8"></script> -->

	<script type="text/javascript">
        app.directive('postLoginHeader', function() {
        	return {
        		restrict : 'E',
        		templateUrl : 'js/directives/postLoginHeader.htm'

        	};
        });
        app.directive('financialPlannerTableView', function() {
        	return {
        		restrict : 'E',
        		templateUrl : 'js/directives/financialPlanner/financialPlannerTableView.htm'
        	};
        });
		</script>

	<script>
        $(document).ready(function(){
        	initBtnLabel =  function(){
        		var url = window.location.href;
		    	var urlpart =  url.split("src=");
		    	var src = urlpart[1];
		    	if(typeof src === "undefined"){
		    		$('#invProfileCTA').text("Dashboard");
		    	} else {
		    		$('#invProfileCTA').text("Next");
		    	}
        	}
        	initBtnLabel();
        	invProfileCTAfn = function (){
        		var url = window.location.href;
		    	var urlpart =  url.split("src=");
		    	var src = urlpart[1];
		    	if(typeof src === "undefined"){
		    		window.location.href = _gc_url_baseUrl+"/faces/pages/investordashboard.xhtml?faces-redirect=true";
		    	} else {
		    		window.location.href = _gc_url_baseUrl+"/faces/pages/select_robo_advisor.xhtml?faces-redirect=true";
		    	}
            }
        });       
        </script>
        
        <script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
</body>
</html>
