<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>
<script src="http://highcharts.github.io/export-csv/export-csv.js"></script>
<!-- <script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/exporting.js"></script> -->
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
	fbq('trackCustom', 'investorAllocation-iv');
</script>
<body class="" ng-app="appMMF" ng-controller="PortfolioAllocation" data-ng-init="init()">

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
  
	<!-- Session Code start -->
	<%
    		Map<String, Object> storedValues =null;
    		String userFirstName = "";
    		boolean investorUnavailable =false;
			if(session != null){
				UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
				if(userSessionBean!= null){
					userFirstName = (String)userSessionBean.getFirstName();
					//investorUnavailable = userSessionBean.getInvestorWithAdvisor();
				}else{
					response.sendRedirect("/faces/index.xhtml");
				}
			}
		%>
	<input type="hidden" id="userFirstName" value="<%= userFirstName %>" />
	<input type="hidden" id="userUnavailable" value="<%= investorUnavailable %>" />
	<div id="loadingOverlay" style="display: none">
		<div class="rippleSpinner">
			<img src="img/gears.svg" />
		</div>
	</div>
	<!-- Session Code end -->
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
		<h3 class="finance_pln">investor portfolio</h3>
	</div>
	<section class="page_body_section">
		<div class="portfolio_section">
			<div class="allocate_prt_section col-lg-6 col-md-12 col-sm-12 col-xs-12">
				<div class="section_head">
					<h3>Allocate Portfolio</h3>
				</div>
				<div class="req_prt_block">
					<h5>Requested Protfolio: {{userRequestedPortfolio.portfolioName}} ({{userRequestedPortfolio.riskType}})</h5>
					<div class="req_entry">
						<div class="entry_row">
							<div class="labels col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h6>Risk Type(Style)</h6>
							</div>
							<div class="selects col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<select id="inputSelectRiskType" ng-model="selectedRiskType" ng-options="risk.riskTypeLabel for risk in portfolioStyleMap" ng-change="onChangeRiskType()">
									<option value="">select</option>
								</select>
							</div>
						</div>
						<div class="entry_row">
							<div class="labels col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h6>Portfolio Size</h6>
							</div>
							<div class="selects col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<select id="inputSelectPortfolioSize" ng-model="selectedPortfolioSize" ng-options="port.portfolioSize for port in portfolioSizeArray">
									<option value="">select</option>
								</select>
							</div>
						</div>
						<div class="entry_row">
							<div class="labels col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h6>Assign Protfolio</h6>
							</div>
							<div class="selects col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<select id="inputSelectPortfolio" ng-model="selectedPortfolio"
									ng-options="port.portfolioName for port in advPortfolioList | filter : {riskType :selectedRiskType.riskTypeLabel} | filter :{portfolioSizeId :selectedPortfolioSize.portfolioSizeId}"
									ng-change="onChangePortfolio()">
									<option value="">select</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			<!-- </div> -->
			<div class="pie_chart_section">
				<h5 class="heading">Portfolio Breakup</h5>
				<div class="pie_chart col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div id="circle_chart" class="circle_chart"></div>
				</div>
			</div>
			<div class="portfolio_table uneditable">
				<div class="table_row head">
					<div class="column column1">
						<h5>Asset Class</h5>
					</div>
					<div class="column column2">
						<h5>Range</h5>
					</div>
					<div class="column column3">
						<h5>Current Allocation</h5>
					</div>
				</div>
				<div class="table_row body"  ng-repeat = "item in portfolioPieChart">
					<div class="column column1">
						<h5>{{item.label}}</h5>
					</div>
					<div class="column column2">
						<h5>{{ getAssetClassRange(item.label) }}</h5>
					</div>
					<div class="column column3">
						<input type="hidden" id="acr{{$index}}" value="{{item.data}}">
						<div class="edit_graph_row">
							<div class="edit_graph_data">
								<div class="age1">
									<div id="ro_port_tb_slide{{$index}}" class="edit_graph_bar"></div>
									<input type="text" id="ro_port_tb{{$index}}" readonly style="border: 0;">
									<label for="ro_port_tb{{$index}}">%</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- <div class="table_row body">
					<div class="column column1">
						<h5>Index</h5>
					</div>
					<div class="column column2">
						<h5>5 to 15%</h5>
					</div>
					<div class="column column3">
						<div class="edit_graph_row">
							<div class="edit_graph_data">
								<div class="age1">
									<div id="ro_port_tb_slide2" class="edit_graph_bar"></div>
									<input type="text" id="ro_port_tb2" readonly style="border: 0;">
									<label for="ro_port_tb2">%</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="table_row body">
					<div class="column column1">
						<h5>Midcap</h5>
					</div>
					<div class="column column2">
						<h5>3 to 25%</h5>
					</div>
					<div class="column column3">
						<div class="edit_graph_row">
							<div class="edit_graph_data">
								<div class="age1">
									<div id="ro_port_tb_slide3" class="edit_graph_bar"></div>
									<input type="text" id="ro_port_tb3" readonly style="border: 0;">
									<label for="ro_port_tb3">%</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="table_row body">
					<div class="column column1">
						<h5>International</h5>
					</div>
					<div class="column column2">
						<h5>9 to 14%</h5>
					</div>
					<div class="column column3">
						<div class="edit_graph_row">
							<div class="edit_graph_data">
								<div class="age1">
									<div id="ro_port_tb_slide4" class="edit_graph_bar"></div>
									<input type="text" id="ro_port_tb4" readonly style="border: 0;">
									<label for="ro_port_tb4">%</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="table_row body">
					<div class="column column1">
						<h5>Gold</h5>
					</div>
					<div class="column column2">
						<h5>8 to 13%</h5>
					</div>
					<div class="column column3">
						<div class="edit_graph_row">
							<div class="edit_graph_data">
								<div class="age1">
									<div id="ro_port_tb_slide5" class="edit_graph_bar"></div>
									<input type="text" id="ro_port_tb5" readonly style="border: 0;">
									<label for="ro_port_tb5">%</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="table_row body">
					<div class="column column1">
						<h5>Dept</h5>
					</div>
					<div class="column column2">
						<h5>0 to 4%</h5>
					</div>
					<div class="column column3">
						<div class="edit_graph_row">
							<div class="edit_graph_data">
								<div class="age1">
									<div id="ro_port_tb_slide6" class="edit_graph_bar"></div>
									<input type="text" id="ro_port_tb6" readonly style="border: 0;">
									<label for="ro_port_tb6">%</label>
								</div>
							</div>
						</div>
					</div>
				</div> -->
			</div>
			<div class="button_set uneditable">
				<form>
					<input type="submit" name="cancel" id="cancel" value="Accept" class="save_ed" onclick="javascript:saveAllocatedPortfolio()"/>
					<input type="submit" name="save_edits" id="save_edits" value="Rejects" class="cancel" style="display: none;"/>
				</form>
			</div>
		</div>
		<div class="financial_plr_section col-lg-6 col-md-12 col-sm-12 col-xs-12">
			<div class="section_head">
				<h3>Financial Planner Output</h3>
			</div>
			<div class="high_chart_blk">
				<div id="inv_high_chart" class=""></div>
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
			<!-- advance settings start -->
			<advance-settings-view></advance-settings-view>
			<!-- <div class="advance_settings_section inv_advance_settings_section">
				<div class="header_block">
					<a href="#advance_settings_block" data-toggle="collapse"><h4>Advance Settings</h4></a>
				</div>
				<div id="advance_settings_block" class="collapse in col-xs-12">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 slider_block">
						<div class="slider_row col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h5>Life Expectancy</h5>
							</div>
							<div class="slider col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<div class="edit_graph_row">
									<div class="edit_graph_data">
										<div class="age1">
											<div id="adv_sett_slide1" class="edit_graph_bar"></div>
											<input type="text" id="adv_sett1" readonly style="border: 0;">
											<label for="adv_sett1">Yrs</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="slider_row col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h5>Rate of which non invested amount grows</h5>
							</div>
							<div class="slider col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<div class="edit_graph_row">
									<div class="edit_graph_data">
										<div class="age1">
											<div id="adv_sett_slide2" class="edit_graph_bar"></div>
											<input type="text" id="adv_sett2" readonly style="border: 0;">
											<label for="adv_sett2">%</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="slider_row col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h5>What is your long term inflation expectation</h5>
							</div>
							<div class="slider col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<div class="edit_graph_row">
									<div class="edit_graph_data">
										<div class="age1">
											<div id="adv_sett_slide3" class="edit_graph_bar"></div>
											<input type="text" id="adv_sett3" readonly style="border: 0;">
											<label for="adv_sett3">%</label>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="slider_row col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<h5>Recurring Expense level post retirement</h5>
							</div>
							<div class="slider col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<div class="edit_graph_row">
									<div class="edit_graph_data">
										<div class="age1">
											<div id="adv_sett_slide4" class="edit_graph_bar"></div>
											<input type="text" id="adv_sett4" readonly style="border: 0;">
											<label for="adv_sett4">%</label>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 table_block">
						<div class="adv_table_row head">
							<div class="column column1">
								<h5>Loan For</h5>
							</div>
							<div class="column column2">
								<h5>No. of Years</h5>
							</div>
							<div class="column column3">
								<h5>Interest Rates</h5>
							</div>
							<div class="column column4">
								<h5>Down payment</h5>
							</div>
						</div>
						<div class="adv_table_row body">
							<div class="column column1">
								<h5>Education</h5>
							</div>
							<div class="column column2">
								<select class="adv_sett_select">
									<option>10</option>
									<option>20</option>
									<option>5</option>
								</select>
							</div>
							<div class="column column3">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
							<div class="column column4">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
						</div>
						<div class="adv_table_row body">
							<div class="column column1">
								<h5>
									Marriage<span class="sub_title">(Personal Loan)</span>
								</h5>
							</div>
							<div class="column column2">
								<select class="adv_sett_select">
									<option>10</option>
									<option>20</option>
									<option>5</option>
								</select>
							</div>
							<div class="column column3">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
							<div class="column column4">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
						</div>
						<div class="adv_table_row body">
							<div class="column column1">
								<h5>House</h5>
							</div>
							<div class="column column2">
								<select class="adv_sett_select">
									<option>10</option>
									<option>20</option>
									<option>5</option>
								</select>
							</div>
							<div class="column column3">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
							<div class="column column4">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
						</div>
						<div class="adv_table_row body">
							<div class="column column1">
								<h5>Car</h5>
							</div>
							<div class="column column2">
								<select class="adv_sett_select">
									<option>10</option>
									<option>20</option>
									<option>5</option>
								</select>
							</div>
							<div class="column column3">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
							<div class="column column4">
								<select class="adv_sett_select">
									<option>12%</option>
									<option>10%</option>
									<option>13%</option>
								</select>
							</div>
						</div>
					</div>
					<div class="increment_section nobox">
						<div class="salary_inc_block heading col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<h3 class="header">Salary Increments vs Age</h3>
							<div class="salary_block">
								<div class="salary_row head">
									<div class="column column1">
										<h5>Years</h5>
									</div>
									<div class="column column2">
										<h5>Increments In Self Salary</h5>
									</div>
									<div class="column column3">
										<h5>Increments In Spouse Salary</h5>
									</div>
								</div>
								<div class="salary_row body">
									<div class="column column1">
										<h5>
											21 - 28 <span>Years</span>
										</h5>
									</div>
									<div class="column column2">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_self_slide1" class="edit_graph_bar"></div>
													<input type="text" id="salary_self1" readonly style="border: 0;">
													<label for="salary_self1">%</label>
												</div>
											</div>
										</div>
									</div>
									<div class="column column3">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_spouse_slide1" class="edit_graph_bar"></div>
													<input type="text" id="salary_spouse1" readonly style="border: 0;">
													<label for="salary_spouse1">%</label>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="salary_row body">
									<div class="column column1">
										<h5>
											28 - 35 <span>Years</span>
										</h5>
									</div>
									<div class="column column2">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_self_slide2" class="edit_graph_bar"></div>
													<input type="text" id="salary_self2" readonly style="border: 0;">
													<label for="salary_self2">%</label>
												</div>
											</div>
										</div>
									</div>
									<div class="column column3">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_spouse_slide2" class="edit_graph_bar"></div>
													<input type="text" id="salary_spouse2" readonly style="border: 0;">
													<label for="salary_spouse2">%</label>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="salary_row body">
									<div class="column column1">
										<h5>
											35 - 50 <span>Years</span>
										</h5>
									</div>
									<div class="column column2">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_self_slide3" class="edit_graph_bar"></div>
													<input type="text" id="salary_self3" readonly style="border: 0;">
													<label for="salary_self3">%</label>
												</div>
											</div>
										</div>
									</div>
									<div class="column column3">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_spouse_slide3" class="edit_graph_bar"></div>
													<input type="text" id="salary_spouse3" readonly style="border: 0;">
													<label for="salary_spouse3">%</label>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="expense_inc_block heading col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<h3 class="header">Expense Increments vs Age</h3>
							<div class="expense_block">
								<div class="salary_row head">
									<div class="column column1">
										<h5>Years</h5>
									</div>
									<div class="column column2">
										<h5>Increments in Expense</h5>
									</div>
								</div>
								<div class="salary_row body">
									<div class="column column1">
										<h5>
											21 - 28 <span>Years</span>
										</h5>
									</div>
									<div class="column column2">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_exp_slide1" class="edit_graph_bar"></div>
													<input type="text" id="salary_exp1" readonly style="border: 0;">
													<label for="salary_exp1">%</label>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="salary_row body">
									<div class="column column1">
										<h5>
											28 - 35 <span>Years</span>
										</h5>
									</div>
									<div class="column column2">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_exp_slide2" class="edit_graph_bar"></div>
													<input type="text" id="salary_exp2" readonly style="border: 0;">
													<label for="salary_exp2">%</label>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="salary_row body">
									<div class="column column1">
										<h5>
											35 - 50 <span>Years</span>
										</h5>
									</div>
									<div class="column column2">
										<div class="edit_graph_row">
											<div class="edit_graph_data">
												<div class="age1">
													<div id="salary_exp_slide3" class="edit_graph_bar"></div>
													<input type="text" id="salary_exp3" readonly style="border: 0;">
													<label for="salary_exp3">%</label>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div> -->
			<!-- advance setting end -->

			<div class="chart_section inv_chart_section">
				<div class="meter_chart">
					<h4 class="chart_head">Edit Risk Profile</h4>
					<div class="gauge_section">

						<!-- <script>
										$('.gauge_section').kumaGauge({
											value : 0,
											gaugeWidth :15,
											radius : 100				
										});
										$('.gauge_section').kumaGauge('update', {
											value : 43
										});
										$('.gauge_section').kumaGauge({
											value : Math.floor((Math.random() * 99) + 1),
											fill : 'transparent',
											gaugeBackground : '#1E4147',
											gaugeWidth : 10,
											showNeedle : false,
											label : {
		            							display : true ,
		            							left : 'Min',
		            							right : 'Max',
		            							fontFamily : 'Helvetica',
		            							fontColor : '#1E4147',
		            							fontSize : '0',
		            							fontWeight : 'bold'
		        							}
										});
								</script> -->
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

			<div class="life_goal_section inv_resp_blk">
				<div class="header_block">
					<a href="#inv_resp_table" data-toggle="collapse"><h4>Investor's Response to Questionaire</h4></a>
				</div>
				<div id="inv_resp_table" class="collapse in">
					<ol class="resp_list">
						<!-- <li>I am more concerned with maximizing returns than minimizing losses.<span class="descp">Strongly Agree</span></li>
						<li>I consider myself an experienced stock market Investor.<span class="descp">Agree</span></li>
						<li>When I think of the word risk I asociate it with the word opportunity.<span class="descp">Somewhat Agree</span></li>
						<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla a vulputate elit. In suscipit purus libero, non laoreet eros luctus.<span class="descp">Strongly Agree</span></li>
						<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at pulvinar lacus.<span class="descp">Agree</span></li>
						<li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus vel enim et libero varius fringilla non quis magna. Duis at libero quis magna vestibulum rhoncus porta vel lectus. Donec
							consequat.<span class="descp">Somewhat Agree</span>
						</li> -->
						<li ng-repeat=" q in questionResponse">
							{{q.questionName}}
							<span class="descp">{{q.oprtionname}}</span>
						</li>
					</ol>
				</div>
			</div>
		</div>
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
	<script type="text/javascript" src="js/custom.js"></script>
	<!-- mobile touch slider -->
	<script src="js/jquery-punch.js" language="javascript"></script>
	<!-- mobile touch slider -->

	<!-- risk profile dial  -->
	<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
	<script type="text/javascript" src="js/kuma-gauge.jquery.js"></script>
	<!-- Modules -->
	<script src="js/app.js"></script>
	<!-- financial planner chart js -->
	<script src="js/financialPlannerChart.js"></script>
	<!-- risk profile dial  -->
	<script src="js/riskProfileChart.js"></script>
	<!-- Controllers -->
	<script src="js/controllers/PortfolioAllocation-iv-v3.js?v=1" charset="UTF-8"></script>
	<script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
	<!-- Directives -->
	<script src="js/directives/portfolioAllocation-iv-v3.js" charset="UTF-8"></script>
	<script src="js/controllers/InvestorNotification-v3.js" charset="UTF-8"></script>
	<script>
		$('#advance_settings_block').collapse('hide');
		$('#inv_resp_table').collapse('hide');
	</script>
</body>
</html>



