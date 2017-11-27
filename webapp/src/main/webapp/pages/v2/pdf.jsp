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
        	padding-top: 60px;
        	padding-bottom: 25px;
        	}
	       .tab-pane {
			    border-left: 1px solid #ddd;
			    border-right: 1px solid #ddd;
			    border-bottom: 1px solid #ddd;
			    border-radius: 0px 0px 5px 5px;
			    padding: 10px;
			}
			.nav-tabs {
			    margin-bottom: 0;
			}
        </style>
        <link rel="stylesheet" href="css/bootstrap-theme-paper.min.css">
        <link rel="stylesheet" href="css/bootstrap-wrapper.css">
        
        <link rel="stylesheet" href="css/iCheck.css" />
        <link rel="stylesheet" href="css/ion.rangeSlider.css"/>
        <link rel="stylesheet" href="css/ion.rangeSlider.skinHTML5.css"/>
        <link rel="stylesheet" href="css/jquery.dataTables.min.css"/>
        <link rel="stylesheet" href="css/roundslider.min.css"/>
        <link rel="stylesheet" href="css/bootstrap-select.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/bootstrap-switch.css" >
        
        <link rel="stylesheet" href="css/jquery-ui.css">
        <link rel="stylesheet" href="css/SelectBoxIt.css" />
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
        <link rel="stylesheet" href="css/bootstrap-submenu.min.css" />
        <link rel="stylesheet" href="css/roundslider.min.css"  />
        
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="app.css">
		<link rel="stylesheet" href="css/spinner.css">
		 
        <script src="js/vendor/jquery.min.js"></script>
        <script src="js/vendor/jquery-ui.min.js"></script>
        <script src="js/vendor/bootstrap.min.js"></script>
        <script src="js/vendor/angular.min.js"></script>
        
        
        <script src="js/vendor/iCheck.js"></script>
        <script src="js/vendor/ion.rangeSlider.min.js"></script>
        <script src="js/vendor/jquery.dataTables.min.js"></script>
        <script src="js/vendor/roundslider.min.js"></script>
        <script src="js/vendor/bootstrap-select.min.js"></script>
        <script src="js/vendor/bootstrap-switch.js"></script>
        <script src="js/vendor/jquery.selectBoxIt.min.js"></script>
        <script src="js/vendor/moment.js"></script>
        <script src="js/vendor/bootstrap-datetimepicker.min.js"></script>
        <script src="js/vendor/autoNumeric-min.js"></script>
        <script src="js/vendor/bootstrap-submenu.min.js"></script>
        <script src="https://code.highcharts.com/stock/highstock.js"></script>
		<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
        <script src= "js/spinner.js"></script>
        <script src= "js/clevertap.js"></script>
	</head>
    <body >
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
    	<mmf-header></mmf-header>
    	<%-- <%
	    	Map<String, Object> storedValues =null;
			String userFirstName = "";
			if(session != null){
				if(session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userFirstName = (String)storedValues.get("userFirstName");
				}else{
					response.sendRedirect("faces/index.xhtml");
				}
			}
		%> --%>
		<%-- <input type="hidden" id="userFirstName" value= "<%= userFirstName %>" /> --%>
		 <div id="loadingOverlay" style="display:none">
        	<div class="rippleSpinner">
           		<img src="img/gears.svg"/> 
       		</div>
    	</div>
	    <div class="container-fluid" >
			<iframe id="myFrame" src="" style="width:100%; height:500px;"></iframe>
			<form id = "confirmUserPdf" action = "" method = "post"/>
			<div class="row">
				<div class="col-md-6">
					<a href="/pages/v2/userRegistration.jsp?faces-redirect=true" class="btn-flat inverse" style="width:100%; text-align: center; height:96px; padding-top: 23px">
						<h4 style="color:#ffffff">Change your response</h4>
					</a>
				</div>
				<div class="col-md-6">
					<a href="javascript:showLoading();clevertap.event.push('PDF Confirmed');$('#confirmUserPdf').attr('action',_gc_url_reg_pdf_post_view);$('#confirmUserPdf').submit();" class="btn-flat success" style="width:100%; text-align: center; height:96px; padding-top: 23px">
						<h4 style="color:#ffffff">Confirm</h4>
					</a>	
				</div>
			</div>
			
		</div>
		<script type="text/javascript">
		
		var settings = {
		  "async": true,
		  "crossDomain": true,
		  "url": _gc_url_fetch_reg_id,
		  "method": "POST",
		  "headers": {
		    "cache-control": "no-cache",
		    "postman-token": "3f58b7bb-cb50-0bc9-1c87-450fc9005806"
		  }
		}
		
		$.ajax(settings).done(function (data) {
				$('#myFrame').attr('src',_gc_url_reg_pdf + data +'.pdf');
		}); 
		
		</script>
    </body>
</html>
