<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<!doctype html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
        	body{
        		padding-top: 60px;
        	}
        </style>
        <link rel="stylesheet" href="css/bootstrap-theme-paper.min.css">
        <link rel="stylesheet" href="css/bootstrap-wrapper.css">
        
        <link rel="stylesheet" href="css/iCheck.css" />
        <link rel="stylesheet" href="css/ion.rangeSlider.css"/>
        <link rel="stylesheet" href="css/ion.rangeSlider.skinHTML5.css"/>
        <link href="https://cdn.jsdelivr.net/jquery.roundslider/1.0/roundslider.min.css" rel="stylesheet" />
        
        <link rel="stylesheet" href="css/main.css">

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="js/vendor/bootstrap.min.js"></script>
        <script src="js/vendor/angular.min.js"></script>
        
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="http://code.highcharts.com/highcharts-more.js"></script>
		<script src="https://code.highcharts.com/modules/exporting.js"></script>
		<script src="https://code.highcharts.com/modules/drilldown.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/d3/3.4.4/d3.min.js"></script>
        <script src="js/vendor/iCheck.js"></script>
        <script src="js/vendor/d3pie.min.js"></script>
        <script src="js/vendor/ion.rangeSlider.min.js"></script>
        <script src="https://cdn.jsdelivr.net/jquery.roundslider/1.0/roundslider.min.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
    </head>
    <body  ng-app="appMMF" ng-controller="RiskProfileOutput">
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
		<div id="loadingOverlay" style="display:none">
        	<div class="rippleSpinner">
           		<img src="img/gears.svg"/> 
        	</div>
    	</div>
	    <mmf-header></mmf-header>

	    <div class="container-fluid" >
			<risk-profile-output></risk-profile-output>
		</div>
		<!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/RiskProfileOutput.js?v=1" charset="UTF-8"></script>
        <script src="js/directives/riskProfileOutput.js" charset="UTF-8"></script>
    </body>
</html>
