<%@ page import="java.io.*,java.util.*" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
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
        	padding-top: 55px;
        	padding-bottom: 60px;
        	}
        </style>
        
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        
        <link rel="stylesheet" href="css/bootstrap-theme-paper.min.css">
        <link rel="stylesheet" href="css/bootstrap-wrapper.css">
        <link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
  
        
        <link rel="stylesheet" href="css/iCheck.css" />
        <link rel="stylesheet" href="css/ion.rangeSlider.css"/>
        <link rel="stylesheet" href="css/ion.rangeSlider.skinHTML5.css"/>
		<link href="css/bootstrap-switch.css" rel="stylesheet">
        <link rel="stylesheet" href="css/jquery.FloatLabel.css"/>
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
        <link rel="stylesheet" href="css/jquery.toast.min.css" />
        <link rel="stylesheet" href="css/jasny-bootstrap.min.css" />
        
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/spinner.css">

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script src="js/vendor/bootstrap.min.js"></script>
        <script src="js/vendor/angular.min.js"></script>
        
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="http://code.highcharts.com/highcharts-more.js"></script>
		<script src="https://code.highcharts.com/modules/exporting.js"></script>
		<script src="https://code.highcharts.com/modules/drilldown.js"></script>
        <script src="js/vendor/iCheck.js"></script>
        <script src="js/vendor/ion.rangeSlider.min.js"></script>
		<script src="js/vendor/bootstrap-switch.js"></script>
        <script src="js/vendor/jquery.FloatLabel.js"></script>
        <script src="js/vendor/moment.js"></script>
        <script src="js/vendor/bootstrap-datetimepicker.min.js"></script>
        <script src="js/vendor/autoNumeric-min.js"></script>
        <script src="js/vendor/jquery.maskedinput.min.js"></script>
        <script src="js/vendor/jquery.toast.min.js"></script>
        <script src="js/vendor/jasny-bootstrap.min.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
        <script src= "js/spinner.js"></script>
        <script src= "js/clevertap.js"></script>
    </head>
    <body  ng-app="appMMF" ng-controller="UserRegistration">
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
				if(session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userFirstName = (String)storedValues.get("userFirstName");
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
			<user-registration></user-registration>
		</div>
		
		<script type="text/javascript">
			clevertap.event.push("Account Opening Form Visited");
		</script>
		<!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/UserRegistration.js?v=1" charset="UTF-8"></script>
        <script src="js/directives/userRegistration.js" charset="UTF-8"></script>
    </body>
</html>
