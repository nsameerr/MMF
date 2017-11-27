<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage My Fortune</title>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script src="https://code.highcharts.com/stock/highstock.js"></script>
    <!-- <script type="text/javascript" src="js/highcharts.js"></script> -->
    
    <script src="http://code.highcharts.com/modules/exporting.js"></script>
    <script src="http://highcharts.github.io/export-csv/export-csv.js"></script> 
    <!--FinancialPlanner Output Chart Script-->
    <script src="js/financialPlannerChart.js"></script>
        
    <script src="js/jquery-ui.js"></script> 

    <script type="text/javascript" src="js/custom.js"></script>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/custom.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="shortcut icon" href="images/favicon.ico">    
    <link rel="stylesheet" href="css/spinner.css">

    <script src="js/vendor/angular.min.js"></script>
    <script src="js/constants.js"></script>
    <script src="js/main.js"></script>
    <script src= "js/spinner.js"></script>
    <script src= "js/clevertap.js"></script>
    <script src= "js/spinner.js"></script>

    <style type="text/css">
        .returnriskNew{
                float: right;
                width: 150px;
                margin-right: 10%;
        }
        .increment_section .body .edit_graph_data {
            margin: 0px;
            width: 102%;
        }

        .increment_section .salary_row .column1 {
            width: 18%;
        }
        .commonbox.button_set .save_ed:hover {
            background: #fff;
            color: #4aae3c;
            border: 1px solid #4aae3c;
        }
        .corner_button{
        float: right;
        margin-right: 10px;
        margin-bottom: 10px;
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
    fbq('trackCustom', 'financialPlannerOutput');
</script>

<body ng-app="appMMF" ng-controller="FinancialPlannerOutput">
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
            String userFirstName = "";
            if(session != null){
                if(session.getAttribute("storedValues")!= null){
                    storedValues = (Map<String, Object>) session.getAttribute("storedValues");  
                    userFirstName = (String)storedValues.get("userFirstName");
                    if(userFirstName == null){
                        UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
                        if(userSessionBean!= null){
                            userFirstName = (String)userSessionBean.getFirstName();
                        }
                    }
                }else{
                    response.sendRedirect("faces/index.xhtml");
                }
            }
        %>
    <input type="hidden" id="userFirstName" value= "<%= userFirstName %>" ng-model="username"/>
     <!-- Start Loading Gears-->
    <div id="loadingOverlay" style="display: none">
        <div class="rippleSpinner">
            <img src="img/gears.svg" />
        </div>
    </div>
    <!-- End Loading Gears-->
    <!-- Header Section -->
    <mmf-header-post-login></mmf-header-post-login>
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
            <h3 class="finance_pln">financial planner</h3>
        </div>
        <section class="page_body_section">
            <div class="life_goal_section">
                <div class="commonbox button_set">
                    <!-- <form> submit-->
                        <input type="button" name="edit_response" id="fp_edit_response" value="Edit Response" class="cancel" ng-click="editResponse()"/>
                        <input type="button" name="end_submit" id="fp_end_submit" value="Save & Continue" class="save_ed" ng-click="saveAndSubmit()"/>
                    <!-- </form> -->
                </div>
            </div>  
            <div class="chart_section">
                <!--FPChart View using Highcharts-->
                <div class="high_chart">
                    <h4 class="chart_head">Your wealth and Annual Expenditure</h4>
                    <div class="tab_controls">
                        <ul class="nav nav-tabs">
                            <li class="active"><a data-toggle="tab" href="#chart_view">Chart View</a></li>
                            <li><a data-toggle="tab" href="#list_view">List View</a></li>
                        </ul>
                        <h6 class="note">Do not let your wealthline fall to zero</h6>
                        <div class="tab-content">
                            <div id="chart_view" class="tab-pane fade in active">
                                <div id="high_chart" class="">
                                </div>
                                <!-- <div class="x_axis_head">
                                    <h5>Age</h5>
                                </div> -->
                                <div class="legend">
                                    <span class="leg leg_1">Total Wealth<!-- Wealth with MMF --></span>
                                    <!-- <span class="leg leg_2">Wealth with FD Investments</span> -->
                                    <span class="leg leg_3">Annual Expenditure</span>
                                    <span class="leg leg_4">Life Goals</span>
                                </div>
                            </div>
                            <div id="list_view" class="tab-pane fade">
                                <!-- <div class="range_block">
                                    <label>Age Range:</label>
                                    <select class="age_drop">
                                        <option>45</option>
                                        <option>46</option>
                                    </select>
                                    <label>To</label>
                                    <select class="age_drop">
                                        <option>50</option>
                                        <option>51</option>
                                    </select>
                                    <label>Years</label>
                                </div> -->
                                <!--Table View-->
                                <financial-planner-table-view></financial-planner-table-view>
                            </div>
                        </div>
                    </div>                    
                </div>                
                <div class="meter_chart">
                    <h4 class="chart_head">Edit Inputs</h4>                                         
                    <div class="slider_section">
                        <div class="returnrisk-c1"> <!-- class="returnrisk-c1"-->
                            <span style="font-family: 'ProximaNova-Regular'; width: 100%; margin: 0px; font-size: 15px; font-weight: bold; color: #636363;">Risk Profile</span><br>  <!--edit_graph_row-->
                            <div class="returnrisk-c1">  <!--edit_graph_data returnrisk-c1 returnriskNew-->
                                    <div class="rrisk" >
                                       <div class="rriskc1">  
                                            <ul>
                                                <li class="high temp riskType">High</li>
                                                <li class="moderate_high temp riskType">Moderate High</li>
                                                <li class="moderate temp riskType">Moderate </li>
                                                <li class="moderate_low temp riskType">Moderate Low</li>
                                                <li class="low temp riskType">Low</li>
                                            </ul>
                                             <input type="hidden" id="riskScoreId"><!--readonly style="border:0;" hidden=""-->      
                                        </div>
                                        <div id="slider-vertical"  class="rriskbar" style="height:170px; margin-top: 6px;"></div>
                                    </div>    
                            </div>
                        </div>  
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
            <div class="life_goal_section">
                <div class="header_block">
                    <a href="#goal_table" data-toggle="collapse"><h4>What are your Life Goals?</h4></a>
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
                        <!-- <div id="added_life_goal_row0" class="goals_row body" ng-if="userFpData.lifeGoals.length == 0" ng-cloak>
                            <div class="column column1">
                                <select class="goal_select goalCol1" ng-model="lifeGoal1" ng-init="lifeGoal1 = '5'">
                                    <option ng-repeat="lifeGoal in lifeGoalsList" value="{{lifeGoal.id}}">{{lifeGoal.name}}</option>
                                </select>
                                <input type="text" class="other_input alphaSpaceOnly" maxlength="25" autofocus ng-if="lifeGoal1 == '0'" ng-model="lifeGoalOther1" placeholder="Type Life Goal Here...">
                            </div>
                            <div class="column column2" >
                                  <select class="goal_select goalCol2" ng-model="lifeGoalFreq1" ng-init="lifeGoalFreq1 = '0'" ng-if="lifeGoal1 == '6' || lifeGoal1 == '7'">
                                    <option ng-repeat="lifeGoalsFrequency in lifeGoalsFrequencyList" value="{{lifeGoalsFrequency.id}}">{{lifeGoalsFrequency.name}}</option>
                                  </select>
                              </div>
                              <div class="column column3">
                                  <select class="goal_select goalCol3" ng-model="lifeGoalYear1" ng-init="lifeGoalYear1 = '2022'">
                                    <option ng-repeat="x in lifeGoalsYearList">{{x}}</option>
                                  </select>
                              </div>
                              <div class="column column4">
                                <div class="edit_graph_row">
                                    <div class="edit_graph_data">
                                        <div class="age1">
                                            <label for="lifeGoalPrice"><i class="fa fa-inr"></i></label><input class="estim_amt" type="text" id="lifeGoalPrice" readonly style="border:0;">
                                            <div id="sliderprice-range-goal-estimate-amount" class="edit_graph_bar" ></div>
                                        </div>
                                    </div>
                                </div>
                              </div>
                              <div class="column column5">
                                  <div class="switch_tog">
                                    <div class="switch tab_controls">
                                        <ul class="nav nav-tabs" id="availLoan-0">
                                            <li class="active"><a data-toggle="tab">Savings</a></li>
                                            <li><a data-toggle="tab">Loan</a></li>
                                        </ul>
                                    </div>
                                  </div>
                                  <div class="ed_del uneditable">
                                      <span id="delete_row_0" class="delete deleteLifeGoal"><a><i class="fa fa-times" aria-hidden="true"></i></a></span>
                                  </div>
                              </div>      
                        </div> -->
                    </div>
                    <div class="add_row_blk">
                            <a class="add_row" id="add_row_life_goal" ng-click="addNewLifeGoalRow()"> </a>
                     </div>
                    
                    <div class="commonbox button_set">
                        <!-- <form> -->
                            <!-- <input type="button" name="life_reset" id="life_reset" value="Reset" class="cancel" ng-click="resetFpLifeGoals()"/> -->
                            <input type="button" name="life_update" id="life_update" value="Apply" class="save_ed corner_button" ng-click="saveFpLifeGoal()" style="float: right;" />
                        <!-- </form> -->
                    </div>
                    
                </div>
            </div>
            <div class="advance_settings_section">
                <div class="header_block">
                    <a href="#advance_settings_block" data-toggle="collapse"><h4>Advance Settings</h4></a>
                </div>
                <div id="advance_settings_block" class="collapse in col-xs-12">
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 slider_block">
                        <div class="slider_row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h5>Life Expectancy</h5>
                            </div>
                            <div class="slider col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <div class="edit_graph_row">
                                    <div class="edit_graph_data"> 
                                        <div class="age1">           
                                            <div id="adv_sett_slide1" class="edit_graph_bar" ></div>
                                            <input type="text" id="adv_sett1" readonly style="border:0;"><label for="adv_sett1">Yrs</label>
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
                                            <div id="adv_sett_slide2" class="edit_graph_bar" ></div>
                                            <input type="text" id="adv_sett2" readonly style="border:0;"><label for="adv_sett2">%</label>
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
                                            <div id="adv_sett_slide3" class="edit_graph_bar" ></div>
                                            <input type="text" id="adv_sett3" readonly style="border:0;"><label for="adv_sett3">%</label>
                                        </div>      
                                    </div>
                                </div>  
                            </div>
                        </div>
                        <div class="slider_row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h5>Recurring  Expense level post retirement</h5>
                            </div>
                            <div class="slider col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <div class="edit_graph_row">
                                    <div class="edit_graph_data"> 
                                        <div class="age1">           
                                            <div id="adv_sett_slide4" class="edit_graph_bar" ></div>
                                            <input type="text" id="adv_sett4" readonly style="border:0;"><label for="adv_sett4">%</label>
                                        </div>      
                                    </div>
                                </div>  
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 table_block">
                        <div class="adv_table_row head">
                            <div class="column column1">
                                <h5>Loan For</h5>
                            </div>
                            <div class="column column2">
                                <h5>No. of Years</h5>
                            </div>
                            <div class="column column3">
                                <h5>Interest Rates (%)</h5>
                            </div>
                            <div class="column column4">
                                <h5>Down Payment (%)</h5>
                            </div>
                       </div>
                       <div class="adv_table_row body">
                            <div class="column column1">
                                <h5>Education</h5>
                            </div>
                            <div class="column column2">
                                <select class="adv_sett_select" ng-cloak 
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[0].loanDuration"
                                    ng-options="x for x in rangeHundred">
                                </select>
                            </div>
                            <div class="column column3">
                                <select class="adv_sett_select" ng-cloak 
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[0].interestRate"
                                    ng-options="x for x in rangeHundred">                                       
                                </select>
                            </div>
                            <div class="column column4">
                                <select class="adv_sett_select" ng-cloak 
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[0].downPaymentPercent"
                                    ng-options="x for x in rangeHundred">                                        
                                </select>
                            </div>
                       </div>
                       <div class="adv_table_row body">
                            <div class="column column1">
                                <h5>House</h5>
                            </div>
                            <div class="column column2">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[1].loanDuration"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                            <div class="column column3">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[1].interestRate"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                            <div class="column column4">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[1].downPaymentPercent"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                       </div>
                       <div class="adv_table_row body">
                            <div class="column column1">
                                <h5>Car</h5>
                            </div>
                            <div class="column column2">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[2].loanDuration"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                            <div class="column column3">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[2].interestRate"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                            <div class="column column4">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[2].downPaymentPercent"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                       </div>
                        <div class="adv_table_row body">
                            <div class="column column1">
                                <h5>Marriage<span class="sub_title">(Personal Loan)</span></h5>
                            </div>
                            <div class="column column2">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[3].loanDuration"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                            <div class="column column3">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[3].interestRate"
                                    ng-options="x for x in rangeHundred">                                    
                                </select>
                            </div>
                            <div class="column column4">
                                <select class="adv_sett_select" ng-cloak
                                    ng-model="userFpData.fpmasterassumption.fploanassumptions[3].downPaymentPercent"
                                    ng-options="x for x in rangeHundred">
                                </select>
                            </div>
                       </div>
                    </div>
                    
                    <div class="clearfix"></div>
                    <div class="advrow2"> 
                        <!-- adv row 2 start -->
                        <div class="increment_section nobox">
                            <div class="salary_inc_block heading col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h3 class="header">Salary Increments vs Age</h3>
                                <div class="salary_block">
                                    <div class="salary_row head">
                                        <div class="column column1">
                                            <h5>Age</h5>
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
                                            <h5>21 - 28 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_self_slide1" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_self1" readonly style="border:0;"><label for="salary_self1">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                        <div class="column column3">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_spouse_slide1" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_spouse1" readonly style="border:0;"><label for="salary_spouse1">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>28 - 35 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_self_slide2" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_self2" readonly style="border:0;"><label for="salary_self2">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                        <div class="column column3">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_spouse_slide2" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_spouse2" readonly style="border:0;"><label for="salary_spouse2">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>35 - 50 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_self_slide3" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_self3" readonly style="border:0;"><label for="salary_self3">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                        <div class="column column3">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_spouse_slide3" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_spouse3" readonly style="border:0;"><label for="salary_spouse3">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>50 - 60 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_self_slide4" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_self4" readonly style="border:0;"><label for="salary_self4">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                        <div class="column column3">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_spouse_slide4" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_spouse4" readonly style="border:0;"><label for="salary_spouse4">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>60 - 80 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_self_slide5" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_self5" readonly style="border:0;"><label for="salary_self5">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                        <div class="column column3">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_spouse_slide5" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_spouse5" readonly style="border:0;"><label for="salary_spouse5">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>80 Plus <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_self_slide6" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_self6" readonly style="border:0;"><label for="salary_self6">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                        <div class="column column3">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_spouse_slide6" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_spouse6" readonly style="border:0;"><label for="salary_spouse6">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="expense_inc_block heading col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <h3 class="header">Expense Increments vs Age</h3>
                                <div class="expense_block">
                                    <div class="salary_row head">
                                        <div class="column column1">
                                            <h5>Age</h5>
                                        </div>
                                        <div class="column column2">
                                            <h5>Increments in Expense</h5>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>21 - 28 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_exp_slide1" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_exp1" readonly style="border:0;"><label for="salary_exp1">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>28 - 35 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_exp_slide2" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_exp2" readonly style="border:0;"><label for="salary_exp2">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>35 - 50 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_exp_slide3" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_exp3" readonly style="border:0;"><label for="salary_exp3">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>50 - 60 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_exp_slide4" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_exp4" readonly style="border:0;"><label for="salary_exp4">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>60 - 80 <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_exp_slide5" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_exp5" readonly style="border:0;"><label for="salary_exp5">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="salary_row body">
                                        <div class="column column1">
                                            <h5>80 Plus <span>Years</span></h5>
                                        </div>
                                        <div class="column column2">
                                            <div class="edit_graph_row">
                                                <div class="edit_graph_data"> 
                                                    <div class="age1">           
                                                        <div id="salary_exp_slide6" class="edit_graph_bar" ></div>
                                                        <input type="text" id="salary_exp6" readonly style="border:0;"><label for="salary_exp6">%</label>
                                                    </div>      
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- adv row 2 end -->
                    </div>

                    <div class="advsetting_btnsec commonbox button_set">
                        <!-- <form> -->
                            <!-- <input type="button" name="adv_reset" id="adv_reset" value="Reset" class="cancel" ng-click="resetFpAdvanceSettings()"/> -->
                            <input  type="button" name="adv_update" id="adv_update" value="Apply" class="save_ed corner_button" ng-click="updateFpAdvanceSettings()" style="float: right;" />
                        <!-- </form> -->
                    </div>                                        
                </div>
            </div>            
            <div class="life_goal_section">
                <div class="commonbox button_set">
                    <!-- <form> submit-->
                        <input type="button" name="edit_response" id="fp_edit_response" value="Edit Response" class="cancel" ng-click="editResponse()"/>
                        <input type="button" name="end_submit" id="fp_end_submit" value="Save & Continue" class="save_ed" ng-click="saveAndSubmit()"/>
                    <!-- </form> -->
                </div>
            </div>                    
        </section>
    </main>
    <!-- End -->

    <!-- Footer Section --> 
    <mmf-footer-post-login></mmf-footer-post-login>
    <!-- End -->
    <script type="text/javascript">
        <!--Collapsed Advance Settings-->
        $("#advance_settings_block").collapse('hide');
        
    </script>
    
    <link rel="stylesheet" href="css/jquery-ui.css">
    <script src="js/jquery-ui.js"></script>

    <script type="text/javascript">
    function drawFpLifeGoalSlider() {
        $( "#sliderprice-range-goal-estimate-amount" ).slider({
              range: "min",
              value:5000000,
              min:0,
              max:9000000,
              step:1000,
              slide: function( event, ui ) {
                $( "#lifeGoalPrice" ).val(format(ui.value) );
              }
        });
        $( "#lifeGoalPrice" ).val(format($( "#sliderprice-range-goal-estimate-amount" ).slider( "value" )));        
    }
    $(document).ready(function(){
        drawFpLifeGoalSlider();
        //Delete Row
        $(document).on('click','.deleteLifeGoal',function(){
            var rowID = $(this).attr('id');
            var splitStr = rowID.split('_');
            rowID = splitStr[splitStr.length-1];
            // console.log(rowID);
            if (confirm("Are you sure you want to delete this item?") == true) {
                $('#added_life_goal_row'+rowID).remove();
            }             
        });

        // $('#delete_row').click(function(){
        //     $('#added_life_goal_row').remove();
        // });

        //Validate Input Field
        $( document ).on( "keypress", ".alphaSpaceOnly", function(event) {
          var regex = new RegExp("^[a-zA-Z\\-\\s]+$");
          var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
          if (!regex.test(key)) {
             event.preventDefault();
             return false;
          }
        });
    });     
    </script>
        
    <!-- CleverTap -->
    <script type="text/javascript">
        clevertap.event.push("FP Output Page Visited");         
    </script>
    <!-- Modules -->
    <script src="js/app.js"></script>

    <!-- Controllers -->
    <script src="js/controllers/FinancialPlannerOutput-v3.js" charset="UTF-8"></script>
    <script src="js/directives/financialPlannerOutput-v3.js" charset="UTF-8"></script>
    <script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
    <!-- mobile touch slider -->
    <script src="js/directives/financialPlanner/jquery-punch.js" type="text/javascript"></script>
</body>
</html>
