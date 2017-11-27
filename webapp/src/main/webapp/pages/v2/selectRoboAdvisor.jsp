<%@page import="com.gtl.mmf.controller.UserSessionBean"%>
<%@ page import="java.io.*,java.util.*,java.util.logging.*" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<!doctype html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
       	<title>Human or Robo Advisor | MMF</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
        	body{
        	padding-top: 55px;
        	padding-bottom: 200px;
        	}
        </style>
        <link rel="stylesheet" href="css/bootstrap-theme-paper.min.css">
        <link rel="stylesheet" href="css/bootstrap-wrapper.css">
        
        <link rel="stylesheet" href="css/iCheck.css" />
        <link rel="stylesheet" href="css/ion.rangeSlider.css"/>
        <link rel="stylesheet" href="css/ion.rangeSlider.skinHTML5.css"/>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.10/css/jquery.dataTables.min.css"/>
        <link href="https://cdn.jsdelivr.net/jquery.roundslider/1.0/roundslider.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link href="css/bootstrap-switch.css" rel="stylesheet">
        
        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="css/SelectBoxIt.css" />
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
        
        <link rel="stylesheet" href="css/main.css">
		<link rel="stylesheet" href="css/spinner.css">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
        <script src="js/vendor/bootstrap.min.js"></script>
        <script src="js/vendor/angular.min.js"></script>
        
        <script src="http://code.highcharts.com/stock/highstock.js"></script>
        <script src="http://code.highcharts.com/stock/highcharts-more.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script src="https://rawgithub.com/highslide-software/draggable-points/master/draggable-points.js"></script>
        
		<script src="https://code.highcharts.com/modules/exporting.js"></script>
		<script src="https://code.highcharts.com/modules/drilldown.js"></script>
        <script src="js/vendor/iCheck.js"></script>
        <script src="js/vendor/ion.rangeSlider.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.jsdelivr.net/jquery.roundslider/1.0/roundslider.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/js/bootstrap-select.min.js"></script>
        <script src="js/vendor/bootstrap-switch.js"></script>
        <script src="http://gregfranko.com/jquery.selectBoxIt.js/js/jquery.selectBoxIt.min.js"></script>
        <script src="js/vendor/moment.js"></script>
        <script src="js/vendor/bootstrap-datetimepicker.min.js"></script>
        <script src="js/vendor/autoNumeric-min.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
        <script src= "js/spinner.js"></script>
        
    </head>
    <body  ng-app="appMMF" ng-controller="SelectRoboAdvisor">
	    <%
    		Map<String, Object> storedValues =null;
    		String userFirstName = "",userMail="",userType="";
			if(session != null){
				if(session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userMail = (String)storedValues.get("username");
					userType=(String)storedValues.get("userType");
					
					UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
					if(userSessionBean!= null){
						userFirstName = (String)userSessionBean.getFirstName();
					}
				}else{
						response.sendRedirect("faces/index.xhtml");
				}			
			}
		%>
		<input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
		<mmf-header></mmf-header>
		
		<div class="container-fluid" >
			<select-robo-advisor></select-robo-advisor>
		</div>
		<!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/SelectRoboAdvisor.js" charset="UTF-8"></script>
        <script src="js/directives/selectRoboAdvisor.js" charset="UTF-8"></script>        
    </body>
</html>
