<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
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
<script type="text/javascript" src="js/highcharts.js"></script>
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
<script src= "js/spinner.js"></script>
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
	fbq('trackCustom', 'advisorSelect');
</script>
<body ng-app="appMMF" ng-controller="SelectRoboAdvisor" data-ng-init="init()">
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
        <script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-69307705-1', 'auto');
		  ga('send', 'pageview');
		
		</script>
	
	
	<div id="loadingOverlay" style="display:none">
        	<div class="rippleSpinner">
           		<img src="images/gears.svg"/> 
       		</div>
    </div>
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
 		<input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
		<input type="hidden" id="userUnavailable" value= "<%= investorUnavailable %>" />


	<!-- <div class="body_bg investor_brand"></div> -->
<!-- Header Section -->
		<mmf-new-header></mmf-new-header>
<!-- Header Section -->
<!-- body -->
		<select-advisor></select-advisor>
<!-- body -->
<!-- Footer-->
		<mmf-new-footer></mmf-new-footer>
<!-- Footer-->
		<!-- Footer Section 
		<footer class="inner_class">
			<div class="footer">
				<div class="social_icons">
					<a class="facebook" href="#"><i class="fa fa-facebook"></i></a><a
						class="twitter" href="#"><i class="fa fa-twitter"></i></a><a
						class="google_plus" href="#"><i class="fa fa-google-plus"></i></a>
				</div>
				<ul class="footer_links">
					<li><a href="#">About Us</a></li>
					<li><a href="#">Contact Us</a></li>
					<li><a href="#">Legal Terms</a></li>
					<li><a href="#">Privacy Policy</a></li>
				</ul>
				<h6 class="copyright">
					©
					<script type="text/javascript">
						var now = new Date();
						var d = now.getFullYear();
						document.write(d);
					</script>
					MMF Investor Registration Pvt. Ltd.
				</h6>
			</div>
		</footer>
		 End -->

	
	<!-- Modules -->
    <script src="js/app.js"></script>
    
    <!-- Controllers -->
    <script src="js/controllers/SelectRoboAdvisor.js" charset="UTF-8"></script>
    <script src="js/directives/selectRoboAdvisor.js" charset="UTF-8"></script> 
	<script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
	<script src="js/controllers/InvestorNotification-v3.js" charset="UTF-8"></script>
</body>
</html>
