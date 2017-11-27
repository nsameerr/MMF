<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage My Fortune</title>
    <script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script src="js/jquery-ui.js"></script>

    <script src="js/vendor/angular.min.js"></script>
    <script src="js/constants.js"></script>
    <script src="js/main.js"></script>
    <script src= "js/spinner.js"></script>
    
    <script src="https://code.highcharts.com/stock/highstock.js"></script>
    <!-- <script type="text/javascript" src="js/highcharts.js"></script> -->
    <!-- <script type="text/javascript" src="js/exporting.js"></script> -->
    <script src="http://code.highcharts.com/modules/exporting.js"></script>
    <script src="http://highcharts.github.io/export-csv/export-csv.js"></script> 
    <!--FinancialPlanner Output Chart Script-->
    <script src="js/financialPlannerChart.js"></script>
    
    <!-- Risk Profile Chart-->
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
    <script type="text/javascript" src="js/kuma-gauge.jquery.js"></script> 
    <script src="js/riskProfileChart.js"></script>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/custom.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="stylesheet" href="css/spinner.css">

    <style type="text/css">
        .notbold{
            font-weight:normal
        }​
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
	fbq('trackCustom', 'portfolioAllocation');
</script>
<body class="investor_profile" ng-app="appMMF" ng-controller="PortfolioAllocation" data-ng-init="init()">
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

    <!-- Start Session Implementation -->
    <%
            Map<String, Object> storedValues =null;
            String userFirstName = "",userMail,userType="";
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
    <input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
    <!-- End Session Implementation -->
    
    <!-- Start Loading Gears-->
    <div id="loadingOverlay" style="display: none">
        <div class="rippleSpinner">
            <img src="img/gears.svg" />
        </div>
    </div>
    <!-- End Loading Gears-->

    <!-- Header Section-->
    <mmf-header-post-login-advisor></mmf-header-post-login-advisor>

    <!-- Output View Page -->
	<main class="inner_page">
    	<div class="scroll-to-bottom" style="display: block">
        	<i class="fa fa-angle-down"></i>
        </div>
        <div class="scroll-to-top">
        	<i class="fa fa-angle-up"></i>
        </div>
        <div class="page_header_section">
        	<h3 class="finance_pln">Portfolio Allocation</h3>
        </div>
        <section class="page_body_section">
        	<div class="portfolio_section">
            	<div class="allocate_prt_section col-lg-6 col-md-12 col-sm-12 col-xs-12">
                	<div class="section_head">
                   		<h3>Allocate Portfolio</h3>
                    </div>
                    <div class="req_prt_block">
                    	<h5 ng-cloak>Requested Portfolio: {{userRequestedPortfolio.portfolioName}} <!-- TestCust5K -->
                            <span>({{userRequestedPortfolio.riskType}}<!--  Risk Style -->)</span>
                        </h5>
                       
                    	<div class="req_entry">
                       		<div class="entry_row">
                            	<div class="labels col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            		<h6>Risk Type(Style)</h6>
                            	</div>
                                <div class="selects col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            		<!-- <select>
                                    	<option>Custom</option>
                                        <option>Select</option>
                                        <option>TestCust5K</option>
                                    </select> -->
                                    <select id="inputSelectRiskType" ng-model="selectedRiskType" 
                                        ng-options="risk.riskTypeLabel for risk in portfolioStyleMap" ng-change="onChangeRiskType()">
                                        <option value="">select</option>
                                    </select>
                            	</div>
                        	</div>
                            <div class="entry_row">
                            	<div class="labels col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            		<h6>Portfolio Size</h6>
                            	</div>
                                <div class="selects col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            		<!-- <select>
                                    	<option>Custom</option>
                                        <option>Select</option>
                                        <option>TestCust5K</option>
                                    </select> -->
                                    <select id="inputSelectPortfolioSize" ng-model="selectedPortfolioSize" 
                                        ng-options="port.portfolioSize for port in portfolioSizeArray">
                                        <option value="">select</option>
                                    </select>
                            	</div>
                        	</div>
                            <div class="entry_row">
                            	<div class="labels col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            		<h6>Assign Portfolio</h6>
                            	</div>
                                <div class="selects col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            		<!-- <select>
                                    	<option>Custom</option>
                                        <option>Select</option>
                                        <option>TestCust5K</option>
                                    </select> -->
                                    <select id="inputSelectPortfolio" ng-model="selectedPortfolio" 
                                        ng-options="port.portfolioName for port in advPortfolioList | filter : {riskType :selectedRiskType.riskTypeLabel} | filter :{portfolioSizeId :selectedPortfolioSize.portfolioSizeId}" 
                                        ng-change="onChangePortfolio()">
                                        <option value="">select</option>
                                    </select>
                            	</div>
                        	</div>
                        </div>
                    </div>
                    <div class="pie_chart_section">
                    	<h5 class="heading">Portfolio Breakup</h5>
                    	<div class="pie_chart col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    		<div id="circle_chart" class="circle_chart">
							</div>
                    	</div>
                    </div>
                    <div class="portfolio_table">
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
                        <!--Sample Portfolio Data : Start-->
                        <!-- <div class="table_row body">
                        	<div class="column column1">
                            	<h5>Equity Diversified</h5>
                            </div>
                            <div class="column column2">
                            	<h5>10 to 25%</h5>
                            </div>
                            <div class="column column3">
                            	<div class="edit_graph_row">
                                	<div class="edit_graph_data"> 
        								<div class="age1">           
            								<div id="port_tb_slide1" class="edit_graph_bar" ></div>
            								<input type="text" id="port_tb1" readonly style="border:0;"><label for="port_tb1">%</label>
        								</div>    	
    								</div>
								</div>
                            </div>
                        </div>
                        <div class="table_row body">
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
            								<div id="port_tb_slide2" class="edit_graph_bar" ></div>
            								<input type="text" id="port_tb2" readonly style="border:0;"><label for="port_tb2">%</label>
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
            								<div id="port_tb_slide3" class="edit_graph_bar" ></div>
            								<input type="text" id="port_tb3" readonly style="border:0;"><label for="port_tb3">%</label>
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
            								<div id="port_tb_slide4" class="edit_graph_bar" ></div>
            								<input type="text" id="port_tb4" readonly style="border:0;"><label for="port_tb4">%</label>
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
            								<div id="port_tb_slide5" class="edit_graph_bar" ></div>
            								<input type="text" id="port_tb5" readonly style="border:0;"><label for="port_tb5">%</label>
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
            								<div id="port_tb_slide6" class="edit_graph_bar" ></div>
            								<input type="text" id="port_tb6" readonly style="border:0;"><label for="port_tb6">%</label>
        								</div>    	
    								</div>
								</div>
                            </div>
                        </div> -->
                        <!--Sample Portfolio Data : End-->
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
                    </div>
                    <div class="button_set">
						<form>
							<input type="submit" name="cancel" id="cancel" value="Back " class="cancel" onclick="backToNetworkView()" />
							<input type="submit" name="save_edits" id="save_edits" value="Next" class="save_ed" onclick="saveAllocatedPortfolio()" />
						</form>
					</div>
                </div>
                <div class="financial_plr_section col-lg-6 col-md-12 col-sm-12 col-xs-12">
                	<div class="section_head">
                   		<h3>Financial Planner Output</h3>
                    </div>
                    <div class="high_chart_blk">
                    	<div id="inv_high_chart" class="">
                    	</div>
						<!-- <div class="x_axis_head">
							<h5>Age</h5>
						</div> -->
						<div class="legend">
							<span class="leg leg_1">Wealth with MMF</span>
							<!-- <span class="leg leg_2">Wealth with FD Investments</span> -->
							<span class="leg leg_3">Annual Expenditure</span>
							<span class="leg leg_4">Life Goals</span>
						</div>
                	</div>
                    <!--Advance Settings DIV-->
                    <advance-settings-view></advance-settings-view>
                    
                    <div class="chart_section inv_chart_section">
                    	<div class="meter_chart">
                			<h4 class="chart_head">Investor's Risk Profile</h4>	
                                <!--Risk Profile Chart-->
                				<div class="gauge_section">        																
								<div class="edit_inputgraphtxt"> <!-- Moderate --> </div>
                			</div>
                    		<div class="slider_section">
								<div class="edit_graph_row">Saving Rate<br>
    								<div class="edit_graph_data"> 
        								<div class="age1">           
            								<div id="slider-range-min" class="edit_graph_bar" ></div>
            								<input type="text" id="age" readonly style="border:0;"><label for="age">%</label>
        								</div>    	
    								</div>
								</div>
								<div class="edit_graph_row">Allocation to MMF<br>
    								<div class="edit_graph_data"> 
        								<div class="age1">           
            								<div id="slider-range-min2" class="edit_graph_bar" ></div>
            								<input type="text" id="age2" readonly style="border:0;"><label for="age2">%</label>
        								</div>    	
    								</div>
								</div>
								<div class="edit_graph_row">Retirement Age<br>
    								<div class="edit_graph_data"> 
        								<div class="age1">           
            								<div id="slider-range-min3" class="edit_graph_bar" ></div>
            								<input type="text" id="age3" readonly style="border:0;"><label for="age3"></label>
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
    <mmf-footer-post-login></mmf-footer-post-login>

    <script type="text/javascript" src="js/custom.js"></script>
    <!-- mobile touch slider -->
    <script src="js/jquery-punch.js" language="javascript"></script>
    <!-- Modules -->
    <script src="js/app.js"></script>
    <!-- Controllers -->
    <script src="js/controllers/PortfolioAllocationAdvisor-v3.js" charset="UTF-8"></script>
    <script src="js/controllers/AdvisorNotifications-v3.js" charset="UTF-8"></script>
    <script src="js/directives/portfolioAllocationAdvisor-v3.js" charset="UTF-8"></script>
    <script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
    <!--Collapsed Response-->
    <script type="text/javascript">
        $('#inv_resp_table').collapse('hide');
    </script>
</body>
</html>