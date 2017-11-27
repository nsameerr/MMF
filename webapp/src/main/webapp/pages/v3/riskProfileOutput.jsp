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
    <script type="text/javascript" src="js/highcharts.js"></script>
    <script type="text/javascript" src="js/exporting.js"></script>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/custom.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="stylesheet" href="css/spinner.css">
    <link rel="shortcut icon" href="images/favicon.ico">
    
    <script src="js/vendor/angular.min.js"></script>
    <script src="js/constants.js"></script>
    <script src="js/main.js"></script>    
    <script src= "js/spinner.js"></script>
	<script src= "js/clevertap.js"></script>
    <!--RiskProfile Chart-->
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
    <script type="text/javascript" src="js/kuma-gauge.jquery.js"></script>
    <script type="text/javascript" src="js/riskProfileChart.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
    <![endif]-->
    
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
	fbq('trackCustom', 'riskProfileOutput');
</script>
<body class="" ng-app="appMMF" ng-controller="RiskProfileOutput" data-ng-init="initRisk()">
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
            if(session != null){
                UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
                if(userSessionBean!= null){
                    userFirstName = (String)userSessionBean.getFirstName();
                }else{
                    response.sendRedirect("/faces/index.xhtml");
                }
            }
        %>
        <input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
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
            <h3 class="finance_pln">Risk Profile</h3>
        </div>
        <section class="page_body_section">
            <div class="portfolio_section">
              <div class="risk_profile_c1 col-lg-6 col-md-12 col-sm-12 col-xs-12">
                    <div class="section_head">
                        <h3>Your Risk Score</h3>
                    </div>
                    
                    <div class="risk_profile_chart">
                        
                    <div class="gauge_section">
                        <!-- <script>
                            $('.gauge_section').kumaGauge({
                                value : 0,
                                gaugeWidth :15,
                                radius : 100                
                            });
                            $('.gauge_section').kumaGauge('update', {
                                value : 60
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
                        <div class="edit_inputgraphtxt"> <!-- Moderate --> </div>
                    </div>
                    </div>
                   
                </div>
                
                
                <div class="risk_profile_c2 col-lg-6 col-md-12 col-sm-12 col-xs-12">
                    <div class="section_head">
                        <h3>Recommended Portfolio</h3>
                    </div>                                     
                    <div class="pie_chart_section">                     
                        <div class="pie_chart col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div id="circle_chart" class="circle_chart">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
<div class="life_goal_section">

    <div class="risk_profile_description"> 
        <div class="section_head"><h3>Description</h3></div>
        <div id="riskProfileDescription"></div>
        <!-- {{riskData.riskProfileDescription}} -->
    <!-- There are a total of five groups. Conservative Group is for people who are extremely risk-averse and Aggressive Group is for people who are extremely comfortable with risk. You are in the Moderately Aggressive Group. --> 
    </div>

    <div class="commonbox button_set">
        <form>
            <input type="submit" name="back" id="btnBack" value="Edit Response" class="cancel" onclick="goBackToRiskProfile()" />
            <input type="submit" name="continue" id="btnContinue" value="Continue" class="save_ed" onclick="goToDashBoard()" />
             <input type="submit" id="btnNext" value="Next" class="save_ed" onclick="next()" style="display:none;"/>
        </form>
    </div>
</div>            
            
            
        </section>
    </main>
<!-- End -->

<!-- Footer Section -->
     <mmf-footer-post-login></mmf-footer-post-login>
<!-- End -->


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

        <script type="text/javascript" src="js/custom.js"></script>
        <!-- mobile touch slider -->
        <script src="js/jquery-punch.js" language="javascript"></script>

        <!-- Modules -->
        <script src="js/app.js"></script>

        <!-- Controllers -->
        <script src="js/controllers/RiskProfileOutput-v3.js?v=1" charset="UTF-8"></script>
        <script src="js/directives/riskProfileOutput.js" charset="UTF-8"></script>

        <script type="text/javascript">
            // updateCharts();
        </script>
</body>
</html>
