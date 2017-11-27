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
	fbq('trackCustom', 'accountStatus');
</script>

<body ng-app="appMMF" ng-controller="AccountStatus" data-ng-init="init()">
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
	<div id="loadingOverlay" style="display:none">
            <div class="rippleSpinner">
                <img src="img/gears.svg"/> 
            </div>
    </div>
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
					<br>
					<div class="content_box" ng-cloak>

						<div class="account_open_row">
							<h3>Account Status</h3>
						</div>
						<div class="account_open_row acc-status-hdr">
							<h3>Hi! <%= userFirstName %></h3>
							<b>You're few steps away to get started</b>
						</div>
						<div class="accfrmsec">

							<div class="accstatus_row">
								<div class="accstatus_c1" >
									<div class="round green" ng-show="email_verified_status == true">
										<span class="glyphicon glyphicon-ok"></span>
									</div>
									<div class="round red" ng-show="email_verified_status == false">
										<span class="glyphicon glyphicon-remove"></span>
									</div>
								</div>
								
								<div class="accstatus_c2">
									<div class="circle">
										<img src="images/accst-mail.png" alt="">
									</div>
								</div>
								<div class="accstatus_c3">
									<h4>Email verified</h4>
									<!-- Nunc tincidunt velit enim, ut suscipit tortor scelerisque quis. -->
									<a href="#" ng-click="resendVerification()" ng-show="email_verified_status == false">Click here to resend verification email.</a>
								</div>
							</div>

							<div class="accstatus_row" ng-class="{'disable' :form_couriered_Client_status == false}">
								<div class="accstatus_c1">
									<div class="round green" ng-show="form_couriered_Client_status == true">
										<span class="glyphicon glyphicon-ok"></span>
									</div>
								</div>
								<div class="accstatus_c2">
									<div class="circle">
										<img src="images/accst-toclient.png" alt="">
									</div>
								</div>
								<div class="accstatus_c3">
									<h4>Form Couriered to Client</h4>
									<!-- In convallis varius orci ut ullamcorper. Quisque ac interdum diam. -->
								</div>
							</div>

							<div class="accstatus_row" ng-class="{'disable' :form_received_client_status == false}">
								<div class="accstatus_c1" >
									<div class="round green" ng-show="form_received_client_status == true">
										<span class="glyphicon glyphicon-ok"></span>
									</div>
								</div>
								<div class="accstatus_c2">
									<div class="circle">
										<img src="images/accst-frm-client.png" alt="">
									</div>
								</div>
								<div class="accstatus_c3">
									<h4>Form Received from Client</h4>
									<!-- Praesent pulvinar et nunc vel scelerisque. Duis sit amet ipsum nibh. -->
								</div>
							</div>

							<div class="accstatus_row"  ng-class="{'disable': ((form_Validated_status == false && accepted_status == false) || (form_Validated_status == false && rejected_status== false && rejection_Resolved == false)) }">
								<div class="accstatus_c1">
									<div class="round green" ng-show="((form_Validated_status == true && accepted_status == true) || (form_Validated_status == true && rejected_status== true && rejection_Resolved == true)) ">
										<span class="glyphicon glyphicon-ok"></span>
									</div>
									<div class="round warning" ng-show="rejected_status== true && rejection_Resolved == false">!</div>
								</div>
								<div class="accstatus_c2">
									<div class="circle">
										<img src="images/accst-accepted.png" alt="">
									</div>
								</div>
								<div class="accstatus_c3">
									<h4>Form Validated, Accepted</h4>
									<!-- Nunc tincidunt velit enim, ut suscipit tortor scelerisque quis.<br> -->
									<span ng-show="rejected_status== true">Form Rejected Reason : {{statusData[6].rejection_Reason}}</span>
								</div>
							</div>

							<div class="accstatus_row" ng-class="{'disable' :finalStatus == false}">
								<div class="accstatus_c1">
									<div class="round green" ng-show="finalStatus == true">
										<span class="glyphicon glyphicon-ok"></span>
									</div>
								</div>
								<div class="accstatus_c2">
									<div class="circle">
										<img src="images/accst-activated.png" alt="">
									</div>
								</div>
								<div class="accstatus_c3">
									<h4>Done</h4>
									<!-- In convallis varius orci ut ullamcorper. Quisque ac interdum diam. -->
								</div>
							</div>

							<!-- <div class="accstatus_row disable">
								<div class="accstatus_c1">
									<div class="round"></div>
								</div>
								<div class="accstatus_c2">
									<div class="circle">
										<img src="images/accst-ucc-created.png" alt="">
									</div>
								</div>
								<div class="accstatus_c3">
									<h4>UCC Created</h4>
									Nunc tincidunt velit enim, ut suscipit tortor scelerisque quis.
								</div>
							</div> -->

						</div>

						<div class="account_open_row  text-center" ng-class="{'disable' :finalStatus == false}" style="display: none;">
							<span class="youre"><b>You're in</b></span> &nbsp;
							<input type="submit" name="get_started" id="get_started" value="Get Started">
							<br> <br>
						</div>

					</div>
				</div>
			</div>

		</div>

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
	
		<!-- Success Modal start -->
	  <div class="msg_box_pp success modal fade " id="modalSuccess" role="dialog">
	    <div class="modal-dialog">    
	      <!-- Modal content-->
	      <div class="modal-content">
	        <div class="modal-header">
	          <!--<button type="button" class="close" data-dismiss="modal">&times;</button>-->
	          <h4 class="modal-title"> <span class="glyphicon glyphicon-ok-sign"></span> </h4>
	        </div>
	        <div class="modal-body"> <h3>Success </h3> Verification Link Resend. </div>
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
	
	<script type="text/javascript">
        	//$scope.fetchStatus();
        	clevertap.event.push("Account Status Page Visited");
        </script>
	
<!-- Modules -->
	<script src="js/app.js"></script>
	<!-- Controllers -->
	<script src="js/controllers/AccountStatus-v3.js?v=1" charset="UTF-8"></script>
	<!-- Directives -->
	<script src="js/directives/accountStatus.js" charset="UTF-8"></script>
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></link>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script language="JavaScript" src="//ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js"></script>
	<script language="JavaScript" src="js/scriptcam.js"></script>
</body>
</html>