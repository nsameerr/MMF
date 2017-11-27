<%@ page import="java.io.*,java.util.*" language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
        <script src="js/vendor/highcharts.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
        <script src= "js/clevertap.js"></script>
	</head>
    <body  ng-app="appMMF" ng-controller="AccountStatus">
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
				response.sendRedirect("faces/index.xhtml");
			}
		}
		%>
		<input type="hidden" id="userFirstName" value= "<%= userFirstName %>" />
	    <mmf-header></mmf-header>

	    <div class="container-fluid" >
			
			<div class="row-fluid">
				<h2 class="page-title">Account Status</h2>
			</div>
			<div id="divLoading"  style="display:none">Loading...</div>
			<div id="divContent">
				<div class="row">
					<div class="col-md-8" style="margin-bottom: 20px;margin-left: 20px;">
						<table cellpadding=10>
							<tr ng-repeat="item in statusData">
								<td ng-if="item.process_status==1" style="vertical-align: top;">
									<!-- <img src="img/statusChecked.png" style="padding-top: 12px;"> -->
									<img src="img/Checked-50.png" style="width=40px;height:40px;">
								</td>
								<td ng-if="item.process_status==0" style="vertical-align: top;">
									<!-- <img src="img/statusUnchecked.png" style="padding-top: 12px;"> -->
									<img src="img/Cancel-50.png" style="width=33px;height:33px;padding-left:4px;">
								</td>
								<td style="vertical-align: top; padding-left:10px">
									<h5>{{item.process_label}}</h5>
									<h6>{{item.rejection_Reason}}</h6>
									<h6 ng-if="item.rejection_Resolved==0 && item.process_status==1" style="color:red">Not Resolved</h6>
									<h6 ng-if="item.rejection_Resolved==1 && item.process_status==1" style="color:green">Resolved</h6>
									
								</td>
								<td ng-if="item.process_name=='email_verified' && item.process_status == 0" style="color:green">
									<img src="img/resendMail.png" style="width=35px;height:35px;padding-top:5px" onclick="javascript:resendVerification();">
										<span id="resendMailText" style="color:black" onclick="javascript:resendVerification()">Resend Verification Mail</span>
									</img>	
									<img  id="loading" src="img/greenSpin.svg" style="width=35px;height:35px;padding-top:5px; display:none;"></img>
								</td>
							</tr>
						</table>
					</div>
					
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
			</div>
		</div>
		
		<!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/AccountStatus.js" charset="UTF-8"></script>
        <script src="js/directives/accountStatus.js" charset="UTF-8"></script>
        
        <script type="text/javascript">
        	//$scope.fetchStatus();
        	clevertap.event.push("Account Status Page Visited");
        </script>
    </body>
</html>
						
						
