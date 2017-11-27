<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html> 
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage My Fortune</title>

    <script type="text/javascript" src="js/jquery.min.js"></script>    
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/vendor/moment.js"></script>
    <script src="js/jquery-ui.js"></script>

    <script type="text/javascript" src="js/custom.js"></script>
    
    <script src="js/vendor/angular.min.js"></script>
    <script src="js/constants.js"></script>
    <script src="js/main.js"></script>
    <script src= "js/spinner.js"></script>
    <script src= "js/clevertap.js"></script>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
    
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" />
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/custom.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="stylesheet" href="css/spinner.css">


    <style type="text/css">
    .buttonFpNext {
        background: #ff7e00;
        /* margin-top: 20px; */
        border: 1px solid #ff7e00;
        color: #ffffff;
        text-transform: uppercase;
        font-family: 'ProximaNova-Semibold';
        border-radius: 5px;
        border: 1px solid #ff7e00;
        border-top: 0;
        height: 41px;
        line-height: 41px;
        background: #ff7e00;
        padding: 0px 30px;
        transition: all 0.3s ease 0s;
    }   
    .buttonFpBack {
        background: #7c7c7c;
        border: 1px solid #7c7c7c !important;
        /* vertical-align: middle; */
        color: #ffffff;
        text-transform: uppercase;
        font-family: 'ProximaNova-Semibold';
        border-radius: 5px;
        border: 1px solid ##7c7c7c;
        border-top: 0;
        height: 41px;
        line-height: 41px;
        background: ##7c7c7c;
        padding: 0px 30px;
        transition: all 0.3s ease 0s;
    }

    input[type="button"].buttonFpNext:hover {
        background:#ffffff; color:#ff7e00; border:1px solid #ff7e00;
    }

    input[type="button"].buttonFpBack:hover  { 
        background:#ffffff; color:#7c7c7c; border:1px solid #7c7c7c; 
    }
    .minheight {
    	min-height: 350px;
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
	fbq('trackCustom', 'userProfile');
</script>
<body ng-app="appMMF" ng-controller="UserProfile">
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
    		String userFirstName = "";
			if(session != null){
				if(session.getAttribute("storedValues")!= null){
					storedValues = (Map<String, Object>) session.getAttribute("storedValues");	
					userFirstName = (String)storedValues.get("userFirstName");
					if(userFirstName == null){
                        UserSessionBean userSessionBean = (UserSessionBean) session.getAttribute("userSession");
                        if(userSessionBean!= null){
                            userFirstName = (String)userSessionBean.getFirstName();
                        }
                    }
				}else{
					response.sendRedirect("faces/index.xhtml");
				}
			}
	%>
	<input type="hidden" id="userFirstName" value= "<%= userFirstName %>" ng-model="username"/>
    <!-- Start Loading Gears-->
    <div id="loadingOverlay" style="display: none">
        <div class="rippleSpinner">
            <img src="img/gears.svg" />
        </div>
    </div>
    <!-- End Loading Gears-->
<div class="body_bg investor_brand">

    <!-- Header Start -->
    <mmf-header-post-login></mmf-header-post-login>
    <!-- Header End -->


<div class="container-fluid middle-section content_bg_shade">
    <div id="content_column"> 
        <div class="content_column_in ">
        <div class="pgh"> <img src="images/icon-financial.png" alt=""> Financial Planner </div>

        <div id="divContent">
            <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
                <div class="carousel-inner" role="listbox">
                    <div id="cItem0" class="item active container-fluid">
                        <!--Page1 : Gender and RelationShip Status-->                         
                        <user-profile-1></user-profile-1>
                    </div>  
                    <div id="cItem1" class="item container-fluid" > 
                        <!--Page2 : Age -->
                        <user-profile-2></user-profile-2>
                    </div>  
                    <div id="cItem2" class="item container-fluid" > 
                        <!-- Page3 : Monthly Take Home Income  -->
                        <user-profile-3></user-profile-3>
                    </div>  
                    <div id="cItem3" class="item container-fluid" > 
                        <!-- Page4 : Monthly Savings  -->
                        <user-profile-4></user-profile-4>
                    </div>  
                    <div id="cItem4" class="item container-fluid" > 
                        <!-- Page5 : Outstanding Loans  -->
                        <user-profile-5></user-profile-5>
                    </div>  
                    <div id="cItem5" class="item container-fluid" > 
                        <!-- Page6 : Current Networth  -->
                        <user-profile-6></user-profile-6>
                    </div>  
                    <!-- <div id="cItem6" class="item container-fluid" > 
                        Page7 : Insurance Details 
                        <user-profile-7></user-profile-7>
                    </div>   -->
                    <div id="cItem7" class="item container-fluid" > 
                        <!-- Page8 : Life Goals  -->
                        <user-profile-8></user-profile-8>  
                    </div>
                    <div id="cItem8" class="item container-fluid" > 
                        <!-- Page9 : Returns and Risks  -->
                        <user-profile-9></user-profile-9>
                    </div>                      
                </div>
            </div>        
        </div>  <!--divContent end-->
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

<!-- Footer Start -->
	<mmf-footer-post-login></mmf-footer-post-login>
<!-- Footer End -->

</div>

<script>
$(".checkbox").click(function(){
    $(this).toggleClass('checked')
});

// $(document).ready(function(){
//     alert( $().jquery);
// });
</script>
        
        <!-- CleverTap -->
        <script type="text/javascript">
            clevertap.event.push("FP Visited");         
        </script>
        <!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/UserProfile-v3.js?v=123" charset="UTF-8"></script>
        <script src="js/directives/userProfile-v3.js" charset="UTF-8"></script>
		<script src="js/controllers/SidebarMenu.js?v=1" charset="UTF-8"></script>
        <!-- mobile touch slider -->
        <script src="js/directives/financialPlanner/jquery-punch.js" type="text/javascript"></script> <!---->        
</body>
</html>
