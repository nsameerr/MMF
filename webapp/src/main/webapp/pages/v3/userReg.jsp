<%@ page import="java.io.*,java.util.*" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
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
<script src= "js/clevertap.js"></script>
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
	fbq('trackCustom', 'userReg');
</script>
<body ng-app="appMMF" ng-controller="UserRegistration" data-ng-init="init()">
	
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
	<div id="loadingOverlay" style="display:none">
        	<div class="rippleSpinner">
           		<img src="images/gears.svg"/> 
       		</div>
    </div>
    <%
    		Map<String, Object> storedValues =null;
    		String userFirstName = "";
			if(session != null){
				if(session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userFirstName = (String)storedValues.get("userFirstName");
				}else{
					response.sendRedirect("/faces/index.xhtml");
				}
			}
		%>
		<style type="text/css">
		.note {
		   font-size: 16px;color: red;font-style: italic;
		}
		::-webkit-input-placeholder { color: rgba(0,0,0,0.4) !important; }
		
		</style>
		


		<input type="hidden" id="userFirstName" value= "<%= userFirstName %>" ng-model="username"/>
	<div class="body_bg investor_brand">
		<!-- Header Section -->
		<post-login-header></post-login-header>
		<!-- End -->

		<div class="container-fluid middle-section content_bg_shade">
			<!-- left column start  -->

			<!-- left column end -->

			<div id="content_column">
				<div class="content_column_in account_open">
					<div class="pgh account-h">Open a free Account</div>
					<!--<div class="breadcrumb"><a href="#">Network</a>  / <a href="#">Investors</a> </div>-->
					<br>
					<!-- coursal start -->
					<div id="divContent" style="display: block">
						<div id="myCarousel" class="carousel slide" data-ride="carousel"
							data-interval="false" data-wrap="false">
							<div class="carousel-inner" role="listbox">
								<!-- Carousal item1 start -->
								<div class="item active container-fluid">
									<inv-ac-1-open></inv-ac-1-open>
								</div>

								<!-- Carousal item2 start -->
								<div class="item container-fluid">
									<inv-ac-2-personal-details></inv-ac-2-personal-details>
								</div>

								<!-- Carousal item3 start -->
								<div class="item container-fluid">
									<inv-ac-3-address-details></inv-ac-3-address-details>
								</div>

								<!-- Carousal item4 start -->
								<div class="item container-fluid">
									<inv-ac-4-professional-details></inv-ac-4-professional-details>
								</div>

								<!-- Carousal item5 start -->
								<div class="item container-fluid">
									<inv-ac-5-nominee-details></inv-ac-5-nominee-details>
								</div>

								<!-- Carousal item6 start -->
								<div class="item container-fluid">
									<inv-ac-6-nominee-minor-details></inv-ac-6-nominee-minor-details>
								</div>

								<!-- Carousal item7 start -->
								<div class="item container-fluid">
									<inv-ac-7-bank-details></inv-ac-7-bank-details>
								</div>

								<!-- Carousal item8 start -->
								<div class="item container-fluid">
									<inv-ac-8-confirmation></inv-ac-8-confirmation>
								</div>



							</div>

						</div>
					</div>
					<!-- coursal end -->
				</div>
			</div>

		</div>

		<!-- Footer Section -->
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
		<!-- End -->

	</div>
	<inv-reg-script-cam></inv-reg-script-cam>
	<!-- calendar scripts start -->

	
	<!-- calendar scripts start -->
	<script type="text/javascript">
	$( function() {
		$( ".my_calendar" ).datepicker({
			dateFormat: "dd-mm-yy" ,
			changeMonth: true,
			changeYear: true,
			yearRange: "-100:+0"	// last hundred years
			
			}).val();
	  } );
</script>
	<script>
    $(".alphaWithSpace").keypress(function (event) {
        var regex = new   RegExp("^[a-zA-Z \b]+$"); //new RegExp("^[a-zA-Z\b]+$");
          var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
          if (!regex.test(key)) {
             event.preventDefault();
             return false;
          }
      });
    
      $(".numericOnly").keypress(function (e) {
      if (String.fromCharCode(e.keyCode).match(/[^0-9]/g)) return false;
      });
	</script>
	<script type="text/javascript">
			clevertap.event.push("Account Opening Form Visited");
	</script>
	
	<!-- Modules -->
	<script src="js/app.js"></script>
	<!-- Controllers -->
	<script src="js/controllers/UserRegistration-v3.1.js?v=1" charset="UTF-8"></script>
	<!-- Directives -->
	<script src="js/directives/userRegistration.js" charset="UTF-8"></script>
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script language="JavaScript" src="//ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
	<script language="JavaScript" src="js/scriptcam.js"></script>
	
</body>
</html>
