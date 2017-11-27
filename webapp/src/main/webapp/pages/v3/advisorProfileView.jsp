<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!--> 
<html> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage My Fortune</title>
    <script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script src="js/jquery-ui.js"></script>
<!-- 	<script type="text/javascript" src="js/highcharts.js"></script>
    <script type="text/javascript" src="js/exporting.js"></script>
 -->    <!-- <script type="text/javascript" src="js/custom.js"></script> -->
    <script src="js/constants.js"></script>
    <script src="js/vendor/angular.min.js"></script>
    <script src= "js/spinner.js"></script>
    <script src= "js/clevertap.js"></script>        

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/custom.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="stylesheet" href="css/spinner.css">
    <link rel="shortcut icon" href="images/favicon.ico">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .portfolio_list_section .pf_table .pflist_row_selected .column5 .actionButton {
            border: 1px solid #ff7e00;
            background: #ff7e00;
            color: #fff;
        }

        .portfolio_list_section .pf_table .column5 .actionButton {
            border: 1px solid #9f9f9f;
            padding: 7px 10px;
            border-radius: 3px;
            font-size: 13px;
        }

        .portfolio_list_section .pf_table .column5 .actionButton:hover {
            border: 1px solid #ff7e00;
            border-radius: 3px;
            background: #ff7e00;
            color: #fff;
        }

        .actionButton:hover, .actionButton:focus, .actionButton:active {
            outline: none;
        }

        .actionButton, .actionButton:hover, .actionButton:focus {
            outline: none;
            color: #676767;
            text-decoration: none;
        }

        .actionButton {
            background-color: transparent;
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
	fbq('trackCustom', 'advisorProfileView');
</script>

<body class="advisor_profile" ng-app="appMMF" ng-controller="AdvisorProfileView"  data-ng-init="init()">
		 <!-- Google Tag Manager -->
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
            boolean investorUnavailable =false;
            if(session != null){
                UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
                if(userSessionBean!= null){
                    userFirstName = (String)userSessionBean.getFirstName();
                    investorUnavailable = userSessionBean.getInvestorWithAdvisor();
                }else{
                     response.sendRedirect("/faces/index.xhtml");
                }
            }
        %>
        <input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
        <input type="hidden" id="userUnavailable" value= "<%= investorUnavailable %>" />
        <div id="loadingOverlay" style="display:none">
            <div class="rippleSpinner">
                <img src="img/gears.svg"/> 
            </div>
        </div>


<!-- Header Section -->
	<post-login-header></post-login-header>
<!-- End -->

<!-- Output View Page -->
	<main class="inner_page" ng-cloak>
    	<div class="scroll-to-bottom" style="display: block">
        	<i class="fa fa-angle-down"></i>
        </div>
        <div class="scroll-to-top">
        	<i class="fa fa-angle-up"></i>
        </div>
        <div class="page_header_section">
        	<h3 class="finance_pln">Portfolio</h3>
        </div>
        <section class="page_body_section">
        	<div class="breadcrumb_menu"> &nbsp;
            	<!--<ul>
                	<li><a href="">Home</a></li>
                    <li><a href="">Investor Profile</a></li>
                </ul>-->
            </div>


            <div class="about_profile_section">                               
                <div>
                    <div class="col-lg-6 col-md-6 col-xs-6" style="padding-left:0;"> <h4 class="about_head">Advisor Profile</h4>  </div>
                    <div class="col-lg-6 col-md-6 col-xs-6">
                        <div class="back_portfolio"> 
                            <a href="javascript:window.location.href='advisor-portfolio-search.jsp?faces-redirect=true'">Back to Portfolio Listing 
                            <span class="glyphicon glyphicon-chevron-left"></span> </a> 
                            <!-- /faces/pages/v3/ -->
                        </div>
                    </div>                   
                </div>
                <!--Message Shown when RP or FP not submitted -->
                <div style="float: left; width: 100%;">                     
                    <div class="msg_box alert" ng-if="riskProfileNotCompleted == true"> <span> You haven't completed your <strong>RISK PROFILE QUESTIONNAIRE </strong>.
                          <a href="riskProfile.jsp?faces-redirect=true&rpsrc=ps" >Click Here</a> to get started. </span>
                          <!-- /faces/pages/v3/ -->
                    </div>
                    <div class="msg_box alert" ng-if="fpNotCompleted == true"> <span>  You haven't completed your <strong>FINANCIAL PLANNER </strong>.
                          <a href="userProfile.jsp?faces-redirect=true&src=ps" >Click Here</a> to get started. </span>
                          <!-- /faces/pages/v3/ -->
                    </div>                    
                </div>   


            	<div class="profile_block col-lg-12 col-md-12 col-sm-12 col-xs-12">
                	<div class="column1 col-lg-4 col-md-4 col-sm-4 col-xs-12">
                    	<div class="profile_name">
                        	<figure >
                                <!-- <img ng-if="item.advPicPath==null"  src="" class="dp_pic" alt="Profile Pictures" /> -->
                            	<img src="{{advProfilePic}}" class="dp_pic" alt="Profile Pictures" />
                            </figure>
                            <div class="dp_names">
                            	<h4 >{{advisor.firstName}} {{advisor.middleName}} {{advisor.lastName}}</h4>
                                <h5 ng-if="item.indvOrCprt == true">Individual Advisor, {{item.city}}</h5> 
                                <h5 ng-if="item.indvOrCprt == false">Corporate Advisor, {{item.city}}</h5>                                 
                                <img src="images/star-on.png" alt=""> <img src="images/star-on.png" alt=""> <img src="images/star-on.png" alt=""> <img src="images/star-on.png" alt=""> <img src="images/star-off.png" alt="">  (In 365 votes)
                            </div>
                        </div>
                    </div>
                    <div class="column2 col-lg-8 col-md-8 col-sm-8 col-xs-12">
                    	<ul class="details">
                        	<li>SEBI RIA Certification No:<span>{{advisor.sebiCertificationNo}}</span></li>
                            <li>Validity:<span>{{advisor.masterAdvisorQualificationTb.validitySebiCertificate}}</span></li>
                            <li>Current Job Title:<span>{{advisor.jobTitle}}</span></li>	
                            <li>Current Organization:<span>{{advisor.workOrganization}}</span></li>	
                        </ul>
                    </div>
                </div>
                <div class="profile_desc col-lg-12 col-md-12 col-sm-12 col-xs-12">
                	<div class="content col-lg-6 col-md-6 col-sm-6 col-xs-12">
                    	<h4>About {{advisor.firstName}} {{advisor.middleName}} {{advisor.lastName}} </h4> 
                    	<p > {{advisor.masterAdvisorQualificationTb.aboutMe}}</p>
                        
                        <div class="qualification_tbl">
                        <b>Qualifications:</b>
                        	<div class="clm1"> <span>Degree</span>  
                            {{advisor.masterAdvisorQualificationTb.primaryQualificationDegree}} <br>{{advisor.masterAdvisorQualificationTb.secondaryQualificationDegree}}<br>{{advisor.masterAdvisorQualificationTb.tertiaryQualificationDegree}}
                            </div>
                            <div class="clm2"> <span>Year</span> {{advisor.masterAdvisorQualificationTb.primaryQualificationYear}}
                            <br>{{advisor.masterAdvisorQualificationTb.secondaryQualificationYear}}<br>{{advisor.masterAdvisorQualificationTb.tertiaryQualificationYear}}
                             </div>
                            <div class="clm3"> <span>Institute</span> {{advisor.masterAdvisorQualificationTb.primaryQualificationInstitute}} <br>{{advisor.masterAdvisorQualificationTb.secondaryQualificationInstitute}}<br>{{advisor.masterAdvisorQualificationTb.tertiaryQualificationInstitute}}
                            </div>
                        </div>                        
                        

                        <span class="linked_in_prfl">View my Profile on <a href="{{advisor.profile.linkedInProfileLink}}">Linkedin</a></span>
                    </div>	
                    <div class="invst_policy col-lg-6 col-md-6 col-sm-6 col-xs-12">

                    	<h4>Investment Policy </h4> 
                        <p>{{advisor.masterAdvisorQualificationTb.myInvestmentStrategy}}</p>
                        
                    </div>                                         
                </div>               
            </div>
                    

            <div class="portfolio_list_section" >
            	<div class="header_block">
                	<h4>Portfolio List</h4>
            	</div>
                <div class="collapse in">
                	<div class="pf_table"  >
                		<div class="pflist_row head">
                    		<div class="column column1">
                        		<h5>Portfolio Name/ Type</h5>
                        	</div>
                        	<div class="column column2">
                        		<h5>Risk Rating</h5>
                        	</div>
                        	<div class="column column3">
                        		<h5>Min Investment</h5>
                        	</div>
                        	<div class="column column4">
                        		<h5>Returns</h5>
                        	</div>
                        	<div class="column column5">
                        		<!--<h5>Invite Status</h5>-->
                        	</div>
                    	</div>
                    	
                        <div class="monthsec"> 
                        	<div class="col1">1 Month </div>
                            <div class="col1">3 Months </div>
                            <div class="col1">6 Months </div>      
                            <div class="col2">Since Start </div>                        
                        </div>
                        <div ng-repeat="item in advisor.portfolios">                        
                            <!--Add if-->
                            <div class="selportfolio" ng-if="selectedPortfolio.portfolioName == item.portfolioName"> 
                                <span class="glyphicon glyphicon-ok"></span> &nbsp;Your Selected Portfolio</div>
                            <div id="pf_row2" ng-class="selectedPortfolio.portfolioName == item.portfolioName ? 'pflist_row body pflist_row_selected':'pflist_row body' " > 
                                <div class="column column1"> 
                                    <h5>{{item.portfolioName}}</h5> {{item.portfolioType}}
                                </div>
                                <div class="column column2">
                                    <h5><span class="head_mobile">Risk Rating -</span> {{item.riskType}}</h5>
                                </div>
                                <div class="column column3">
                                    <h5><span class="head_mobile">Min Investment  -</span> <i class="fa fa-inr"></i> {{item.minInvest | number}} </h5>  
                                </div>
                                <div class="column column4">
                                    <!-- <h5><span class="head_mobile">One Month Returns - {{item.returns.oneMonth}}%</span> </h5>
                                    <h5><span class="head_mobile">Three Month Returns - {{item.returns.threeMonth}}%</span> </h5>
                                    <h5><span class="head_mobile">Six Month Returns - {{item.returns.sixMonth}}%</span> </h5>-->
                                    <h5><span class="head_mobile">Returns Since Inception - {{item.returns.sinceInception}}%</span> </h5>
                                    <div class="monthsec_val">
                                        <div class="col1">{{item.returns.oneMonth}}% </div>
                                        <div class="col1">{{item.returns.threeMonth}}%</div>
                                        <div class="col1">{{item.returns.sixMonth}}%</div>      
                                        <div class="col2">{{item.returns.sinceInception}}%</div> 
                                     </div>                       
                                </div>
                                <div class="column column5">
                                    <input type="button" 
                                        ng-attr-id="{{'button-'+item.advId+'-'+item.portfolioId}}"
                                        ng-click="callToAction(item.portfolioName,item.advId,item.portfolioId,item.minInvest)" 
                                        ng-disabled="fetchActionFilter"
                                        value="{{item.actionText}}"
                                        class="actionButton" >                                        
                                    <!-- <a href="#" >Invite Advisor</a> -->
                                </div>                                
                            </div>
                            <!--Show Status of Action-->
                            <div class="msg_box success" ng-attr-id="{{'div-'+item.advId+'-'+item.portfolioId}}" style="display:none;"> <span class="glyphicon glyphicon-ok"></span> Success message </div>                                            
                            <!-- <div class="msg_box error"> <span class="glyphicon glyphicon-remove"></span> Error message </div> -->
                        </div>
                        <!-- <div id="pf_row1" class="pflist_row body">
                        	
                    		<div class="column column1">
                        		<h5>Custom25KPlus</h5> Equity Only
                        	</div>
                        	<div class="column column2">
                        		<h5><span class="head_mobile">Risk Rating -</span> Custom </h5>
                        	</div>
                        	<div class="column column3">
                        		<h5><span class="head_mobile">Min Investment -</span> <i class="fa fa-inr"></i> 25,000 </h5>
                        	</div>
                        	<div class="column column4">
                        		<h5><span class="head_mobile">Avg Returns - 4%</span> </h5>
                                <div class="monthsec_val">
                                    <div class="col1">1% </div>
                                    <div class="col1">2%</div>
                                    <div class="col1">4%</div>      
                                    <div class="col2">8% </div> 
                                 </div>                       
                        	</div>
                        	<div class="column column5">
                        		 <a href="#">Invite Advisor</a>
                        	</div>
                    	</div>

                        <div class="selportfolio"> <span class="glyphicon glyphicon-ok"></span> &nbsp;Your Selected Portfolio</div>
                        <div id="pf_row2" class="pflist_row body pflist_row_selected">
                    		<div class="column column1"> 
                        		<h5>Custom25KPlus</h5> {{item.portfolioType}}
                        	</div>
                        	<div class="column column2">
                        		<h5><span class="head_mobile">Risk Rating -</span> {{item.riskType}}</h5>
                        	</div>
                        	<div class="column column3">
                        		<h5><span class="head_mobile">Min Investment  -</span> <i class="fa fa-inr"></i> {{item.minInvest}} </h5>  
                        	</div>
                        	<div class="column column4">
                        		<h5><span class="head_mobile">Avg Returns - 5%</span> </h5>
                                <div class="monthsec_val">
                                    <div class="col1">{{item.returns.oneMonth}} </div>
                                    <div class="col1">{{item.returns.threeMonth}}</div>
                                    <div class="col1">{{item.returns.sixMonth}}</div>      
                                    <div class="col2">{{item.returns.sinceInception}} </div> 
                                 </div>                       
                        	</div>
                        	<div class="column column5">
                        		<a href="#">Invite Advisor</a>
                        	</div>
                    	</div>
                        
                        
                        <div id="pf_row3" class="pflist_row body">
                    		<div class="column column1"> 
                        		<h5>Moderate25K</h5> Hybrid 
                        	</div>
                        	<div class="column column2">
                        		<h5><span class="head_mobile">Risk Rating -</span> Custom</h5>
                        	</div>
                        	<div class="column column3">
                        		<h5><span class="head_mobile">Min Investment  -</span> <i class="fa fa-inr"></i> 72,000 </h5>  
                        	</div>
                        	<div class="column column4">
                        		<h5><span class="head_mobile">Avg Returns - 7%</span> </h5>
                                <div class="monthsec_val">
                                    <div class="col1">8% </div>
                                    <div class="col1">2%</div>
                                    <div class="col1">4%</div>      
                                    <div class="col2">3% </div> 
                                 </div>                       
                        	</div>
                        	<div class="column column5">
                        		<a href="#">Invite Advisor</a>
                        	</div>
                    	</div>
                         -->
                        
                	</div>
            	</div>
                
            </div>
            
        
<div class="publications_section">
    <h4>Publications</h4> 
    <b ng-if="advisor.masterAdvisorPublicationTb.length==0">No Publications</b> 
    <b ng-repeat="item in advisor.masterAdvisorPublicationTb"><a href="//{{item.link}}" target="_blank">{{item.title}}<br> </a></b> 
</div>
            
            
        </section>
    </main>
<!-- End -->

<!-- Footer Section -->
	<mmf-footer-post-login></mmf-footer-post-login>
<!-- End -->

<!-- Modules -->
<script src="js/app.js"></script>
<script type="text/javascript" src="js/custom.js"></script>
<!-- Controllers -->
<script src="js/controllers/AdvisorProfileView-v3.js" charset="UTF-8"></script>
<script src="js/directives/advisorProfileView-v3.js" charset="UTF-8"></script> 
<script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
<script src="js/controllers/InvestorNotification-v3.js" charset="UTF-8"></script>
<!-- mobile touch slider -->
<script src="js/jquery-punch.js" language="javascript"></script>
<!-- mobile touch slider -->

</body>
</html>
