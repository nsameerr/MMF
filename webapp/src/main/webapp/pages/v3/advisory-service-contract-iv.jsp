<%@page import="java.math.BigDecimal"%>
<%@page import="com.gtl.mmf.service.util.LookupDataLoader"%>
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
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/exporting.js"></script>
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
<script src="js/clevertap.js"></script>
<style type="text/css">
	
	.advMsg {
		margin-left: 30px;
	}
	.invMsg {
		margin-left: 0px;
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
	fbq('trackCustom', 'advisorServiceContract-iv');
</script>
<body class="" ng-app="appMMF" ng-controller="AdvisoryServiceContract" data-ng-init="init()">
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
    		String userType="";
			
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

			//userFirstName="TestAdvisor";
			//userType="ADVISOR";
			
			//userFirstName="TestInvestor";
			//userType="INVESTOR";
		%>
	<input type="hidden" id="userFirstName" value="<%= userFirstName %>" />
	<input type="hidden" id="userType" value="<%= userType %>" />

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
		<h3 class="finance_pln">Advisory Service Contract</h3>
	</div>
	<section class="page_body_section">
		<div class="portfolio_section service_contract">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="section_head investor">
					<h3>Service Contract</h3>
				</div>
			</div>

			<div class="contract_left_section col-lg-8 col-md-8 col-sm-6 col-xs-12">

				<div class="msg_box success" style="display: none">
					<span class="glyphicon glyphicon-ok"></span> Success message
				</div>
				<div class="msg_box alert" style="display: none">
					<span> ! </span> Alert message
				</div>
				<div class="msg_box error" style="display: none">
					<span class="glyphicon glyphicon-remove"></span> Error message
				</div>

				<div class="contract_row">
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-6 value">
						<h4>Portfolio Assigned</h4>
						{{contractData.allocatedPortfolio.portfolioName}}
					</div>
					<div class="col-lg-4 col-md-4 col-sm-6 col-xs-6 value">
						<h4>Allocated Investment</h4>
						<i class="fa fa-inr"></i> {{contractData.allocatedFunds}}
					</div>
				</div>

				<div class="contract_row smtxt" ng-show="contractData.mgmtFeeTypeVariable == 'variable'">
					<h4>Management Fee</h4>
					<b>Variable</b>
					<div class="clearfix"></div>
					<br>
					<div>
						<b>{{mgmtFeeVariableAmount}} </b> AUM Per Annum, Payable Proportionately on a <select ng-model="varAUMPayableFrequency" ng-options="value as key for (key, value) in aumPayableFrequencyList"
							disabled="disabled"></select> Basis
					</div>
				</div>

				<div class="contract_row smtxt" ng-show="contractData.mgmtFeeTypeVariable == 'fixed'">
					<h4>Management Fee</h4>
					<b>Fixed</b>
					<div class="clearfix"></div>
					<br>
					<div>
						<b>{{mgmtFeeFixedAmount}} </b> per annum, payable proportionately on a <select ng-model="fixAUMPayableFrequency" ng-options="value as key for (key, value) in aumPayableFrequencyList"
							disabled="disabled"></select> Basis
					</div>
				</div>


				<div class="contract_row smtxt">
					<h4>Performance Fee</h4>
					<b>{{performanceFeePercent}}</b> % of profits exceeding <b>{{performanceFeeThreshold}} </b> % Returns Per Annum, Payable on a <select ng-model="pFeeFrequency"
						ng-options="value as key for (key, value) in aumPayableFrequencyList" disabled="disabled"></select> Basis
				</div>


				<div class="contract_row smtxt">
					<h4>Exit Load</h4>
					<b>{{exitLoadFeePercent}}</b> % of AUM of portfolio redeemed before <b>{{exitLoadDuration}}</b> months
				</div>


				<div class="contract_row">
					<div class="clm1">
						<h4>Duration</h4>
						<b> {{ContractDurationCount}} <select id="cDurationFreq" ng-model="ContractDuration" ng-options="value as key for (key, value) in contractDurationFrequencyList" disabled="disabled"
							style="width: 100px;" ng-change="changeExitLoadDuration()" ng-disabled="filterTag"></select></b>
					</div>
					<!-- <div class="clm1 text-center">
                            <h4></h4> 
							<b>2014</b>
                         </div> -->
					<div class="clm2">
						<h4>Contract Start Date</h4>
						<b> {{contractData.startDate}}</b>
					</div>
				</div>

				<div class="contract_row investor_brand">

					<div class="col-lg-9 col-md-10 col-sm-12 col-xs-12 nopadding" ng-show="hideReview== false">
						<textarea name="comments" id="comments" placeholder="{{reviewCommentPlaceholder}}" ng-model="reviewComment"></textarea>
					</div>

					<!-- <div class="investor">
						<input type="checkbox" name="advisorInvestorAgreement" id="advisorInvestorAgreement" value="">
						<label for="advisorInvestorAgreement"></label> I / We hereby agree to <a href="/pages/advisor_investor_agreement.xhtml?faces-redirect=true" target="_blank">Advisor - Investor Agreement</a> <br>
						<br>
					</div> -->

				</div>
				<div class="contract_row investor_brand">

					<!-- <div class="col-lg-9 col-md-10 col-sm-12 col-xs-12 nopadding" ng-show="hideReview== false">
						<textarea name="comments" id="comments" placeholder="{{reviewCommentPlaceholder}}" ng-model="$parent.reviewComment"></textarea>
					</div> -->

					<div class="investor">
						<input type="checkbox" name="advisorInvestorAgreement" id="advisorInvestorAgreement" value="">
						<label for="advisorInvestorAgreement"></label> I / We hereby agree to <a href="/pages/advisor_investor_agreement.xhtml?faces-redirect=true" target="_blank">Advisor - Investor Agreement</a> <br>
						<br>
					</div>

				</div>



				<div class="button_set uneditable">
					<!-- button for human advisor  accept-0  review -1 decline-2  -->
					<form ng-show="hideReview==false">
						<input type="submit" name="submit" id="button-action-0" value="Accept" class="save_ed" ng-click="callToAction(0)"/>
						<input type="submit" name="review" id="button-action-1" value="Review" class="cancel" ng-click="callToAction(1)"/>
						<input type="submit" name="review" id="button-action-2" value="Decline" class="cancel" ng-click="callToAction(2)"/>
						
					</form>
					<!-- button for robo advisor back-0 accept -1 decline-2  -->
					<form ng-show="hideReview==true">
						<input type="submit" name="review" id="button-action-3" value="Back" class="cancel" ng-click="callToAction(3)"/>
						<input type="submit" name="submit" id="button-action-4" value="Accept" class="save_ed" ng-click="callToAction(4)"/>
						<input type="submit" name="review" id="button-action-5" value="Decline" class="cancel" ng-click="callToAction(5)"/>
						
					</form>
				</div>
			</div>

			<div class="conversation_section col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="sectionhead" ng-show="hideReview== false">
					<h4>Conversation</h4>
				</div>
				<div class="conversation_area" ng-show="hideReview== false">
					<div class="conversation_row" ng-repeat="item in reviewConversationDisplay" ng-class="{'advMsg': item.isAdv ==true}"  >
					<!-- ng-class="{'advMsg': item.isAdv ==true}  ng-class="item.isAdv == false ? 'advMsg' : 'invMsg' " ng-class="{'invMsg': item.isAdv ==false}"-->
						<div class="clm1" >
							<div class="conphoto" style="background: #4aae3c;padding: 5px" ng-show="item.isAdv == false">
								<!-- <img src="images/conversation_user1.png" alt=""> -->
								<span class="round glyphicon glyphicon-user" style="color:white"></span>
							</div>
							<div class="conphoto" style="background: #3295c9;padding: 5px" ng-show="item.isAdv == true">
								<!-- <img src="images/conversation_user1.png" alt=""> -->
								<span class="round glyphicon glyphicon-user" style="color:white" ></span>
							</div>
						</div>
						<div class="clm2">
							<dt>{{item.name}}</dt>
							{{item.msg}}
						</div>
					</div>

<!-- 					<div class="conversation_row">
						<div class="clm1">
							<div class="conphoto">
								<img src="images/conversation_user2.png" alt="">
							</div>
						</div>
						<div class="clm2">
							<dt>12 Feb 2017</dt>
							Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu eros elementum, dapibus tellus ac, tincidunt libero.
						</div>
					</div>

					<div class="conversation_row">
						<div class="clm1">
							<div class="conphoto">
								<img src="images/conversation_user1.png" alt="">
							</div>
						</div>
						<div class="clm2">
							<dt>12 Feb 2017</dt>
							Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu eros elementum, dapibus tellus ac, tincidunt libero.
						</div>
					</div>

					<div class="conversation_row">
						<div class="clm1">
							<div class="conphoto">
								<img src="images/conversation_user2.png" alt="">
							</div>
						</div>
						<div class="clm2">
							<dt>12 Feb 2017</dt>
							Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu eros elementum, dapibus tellus ac, tincidunt libero.
						</div>
					</div> -->


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
	<!-- Modules -->
	<script src="js/app.js"></script>

	<!-- Controllers -->
	<script src="js/controllers/AdvisoryServiceContract-iv-v3.js" charset="UTF-8"></script>
	<script src="js/directives/advisoryServiceContract-iv-v3.js" charset="UTF-8"></script>
	<script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
	<script src="js/controllers/InvestorNotification-v3.js" charset="UTF-8"></script>
</body>
</html>
