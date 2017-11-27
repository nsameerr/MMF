<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
        img {
            color:white;         
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
	fbq('trackCustom', 'investorProfileView');
</script>
<body class="investor_profile inv_portfolio" ng-app="appMMF" ng-controller="InvestorProfile" data-ng-init="init()">
    <div id="loadingOverlay" style="display:none">
            <div class="rippleSpinner">
                <img src="images/gears.svg"/> 
            </div>
    </div>
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
    <!-- Header Section-->
    <mmf-header-post-login-advisor></mmf-header-post-login-advisor>
    <!-- Investor Profile View-->
	<investor-profile-view></investor-profile-view>  
    <!-- Footer Section -->
	<mmf-footer-post-login></mmf-footer-post-login>    
    <!-- mobile touch slider -->
    <script src="js/jquery-punch.js" language="javascript"></script>
    
    <!-- Modules -->
    <script src="js/app.js"></script>

    <!-- Controllers -->   
    <script src="js/controllers/InvestorProfileView-v3.js?v=1" charset="UTF-8"></script>
    <script src="js/controllers/AdvisorNotifications-v3.js" charset="UTF-8"></script>
    <script src="js/directives/investorProfileView-v3.js" charset="UTF-8"></script>
    <script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
</body>
</html>