<%@ page import="java.io.*,java.util.*,com.gtl.mmf.controller.UserSessionBean" language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!--[if IE 8]> <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!--> 
<html> <!--<![endif]-->
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
    <link rel="stylesheet" href="css/spinner.css">
    <link rel="shortcut icon" href="images/favicon.ico">
    <script src="js/constants.js"></script>
    <script src="js/vendor/angular.min.js"></script>
    <script src= "js/spinner.js"></script>
    <script src= "js/clevertap.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
    <![endif]-->
    
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
	fbq('trackCustom', 'riskProfile');
</script>
<body class="" ng-app="appMMF" ng-controller="RiskProfile">
        <!-- Google Tag Manager -->
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
        	<h3 class="finance_pln">Risk Profile Questionnarie </h3>
        </div>
        <section class="page_body_section">
        	<div class="portfolio_section service_contract">
            	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                
                
<!-- <div class="registration_step_sec"> 

<ol class="progress" data-steps="5">
    <li class="done"> <span class="step"><span>1</span></span> </li>
    <li class="active"> <span class="step"><span>2</span></span> </li>
    <li class=""> <span class="step"><span>3</span></span> </li>
    <li> <span class="step"><span>4</span></span> </li>
    <li> <span class="step"><span>5</span></span> </li>
    <li> <span class="step"><span>6</span></span> </li>
    <li> <span class="step"><span>7</span></span> </li>
    <li> <span class="step"><span>8</span></span> </li>
    <li> <span class="step"><span>9</span></span> </li>
    <li> <span class="step"><span>10</span></span> </li>
</ol>                
</div> -->
                </div>
                
                <div class="contract_left_section col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div id="divContent">
                        <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false">
                            <div class="carousel-inner" role="listbox">
                                <div id="cItem0" class="item active container-fluid">
                                    <!--Q1 : -->                         
                                    <div class="risk_profile_qtsec">               
                                        <b>I have knowledge of investing in the Stock market to generate adequate returns on my investments. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q0A1" name="Q0" value="1" /> <label for="Q0A1" > &nbsp; </label>  <span>Strongly Disagree</span> <br>
                                            <input type="radio" id="Q0A2" name="Q0" value="2" /> <label for="Q0A2"> &nbsp; </label> <span>Disagree </span> <br>
                                            <input type="radio" id="Q0A3" name="Q0" value="3" /> <label for="Q0A3"> &nbsp; </label> <span>No Strong Opinion </span> <br>
                                            <input type="radio" id="Q0A4" name="Q0" value="4"/> <label for="Q0A4">  &nbsp; </label><span>Agree</span> <br>
                                            <input type="radio" id="Q0A5" name="Q0" value="5" /> <label for="Q0A5">  &nbsp; </label> <span>Strongly Agree </span> <br>    

                                            
                                        </div>              
                                    </div>
                                </div>


                                <div id="cItem1" class="item container-fluid" > 
                                    <!--Q2 -->
                                    <div class="risk_profile_qtsec">               
                                        <b>Number of years before I reach retirement age of 60. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q1A1" name="Q1" value="1"  /> <label for="Q1A1"> &nbsp; </label>  <span>More than 30 years </span> <br>
                                            <input type="radio" id="Q1A2" name="Q1" value="2" /> <label for="Q1A2"> &nbsp; </label> <span>21 to 30 years </span> <br>
                                            <input type="radio" id="Q1A3" name="Q1" value="3" /> <label for="Q1A3"> &nbsp; </label> <span>11 to 20 years </span> <br>
                                            <input type="radio" id="Q1A4" name="Q1" value="4" /> <label for="Q1A4">  &nbsp;</label> <span>Less than equal to 10 years</span> <br>
                                            <input type="radio" id="Q1A5" name="Q1" value="5" /> <label for="Q1A5">  &nbsp; </label> <span>Already Retired </span> <br>    

                                            
                                        </div>              
                                    </div>
                                </div> 


                                <div id="cItem2" class="item container-fluid" > 
                                    <!--Q3 -->
                                    <div class="risk_profile_qtsec">               
                                        <b>My expected income growth may be adequate to cover all my future expenses. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q2A1" name="Q2" value="1" /> <label for="Q2A1"> &nbsp;</label><span>Strongly Disagree</span> <br>
                                            <input type="radio" id="Q2A2" name="Q2" value="2" /> <label for="Q2A2"> &nbsp; </label> <span>Disagree </span> <br>
                                            <input type="radio" id="Q2A3" name="Q2" value="3" /> <label for="Q2A3"> &nbsp; </label> <span>No Strong Opinion </span> <br>
                                            <input type="radio" id="Q2A4" name="Q2" value="4" /> <label for="Q2A4">  &nbsp;</label> <span>Agree</span> <br>
                                            <input type="radio" id="Q2A5" name="Q2" value="5" /> <label for="Q2A5">  &nbsp; </label> <span>Strongly Agree</span> <br>    

                                            
                                        </div>              
                                    </div>
                                </div> 

                                <div id="cItem3" class="item container-fluid" > 
                                    <!--Q4-->
                                    <div class="risk_profile_qtsec">               
                                        <b>I prefer investing in Fixed Deposit or Gold rather than invest in Stocks or Mutual Funds. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q3A1" name="Q3" value="1"/> <label for="Q3A1"> &nbsp;</label><span>Strongly Disagree</span> <br>
                                            <input type="radio" id="Q3A2" name="Q3" value="2"/> <label for="Q3A2"> &nbsp; </label> <span>Disagree </span> <br>
                                            <input type="radio" id="Q3A3" name="Q3" value="3"/> <label for="Q3A3"> &nbsp; </label> <span>No Strong Opinion </span> <br>
                                            <input type="radio" id="Q3A4" name="Q3" value="4"/> <label for="Q3A4">  &nbsp;</label> <span>Agree</span> <br>
                                            <input type="radio" id="Q3A5" name="Q3" value="5"/> <label for="Q3A5">  &nbsp; </label> <span>Strongly Agree</span> <br>    

                                            
                                        </div>              
                                    </div>
                                </div> 

                                <div id="cItem4" class="item container-fluid" > 
                                    <!--Q5 -->
                                    <div class="risk_profile_qtsec">               
                                        <b>What is the level of your borrowings (e.g. loan or credit card bill outstanding) which cannot be repaid by your current savings or sale of assets you own?.</b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q4A1" name="Q4" value="1" /> <label for="Q4A1"> &nbsp; </label>  <span>Very High</span> <br>
                                            <input type="radio" id="Q4A2" name="Q4" value="2" /> <label for="Q4A2"> &nbsp; </label> <span>High </span> <br>
                                            <input type="radio" id="Q4A3" name="Q4" value="3" /> <label for="Q4A3"> &nbsp; </label> <span>Moderate </span> <br>
                                            <input type="radio" id="Q4A4" name="Q4" value="4" /> <label for="Q4A4">  &nbsp;</label> <span>Low</span> <br>
                                            <input type="radio" id="Q4A5" name="Q4" value="5" /> <label for="Q4A5">  &nbsp; </label> <span>Almost zero </span> <br>    

                                            
                                        </div>              
                                    </div>
                                </div> 

                                <div id="cItem5" class="item container-fluid" > 
                                    <!--Q6 -->
                                    <div class="risk_profile_qtsec">               
                                        <b>My future expenses will be significantly higher than my future income. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q5A1" name="Q5" value="1" /> <label for="Q5A1"> &nbsp;</label><span>Strongly Disagree</span> <br>
                                            <input type="radio" id="Q5A2" name="Q5" value="2" /> <label for="Q5A2"> &nbsp; </label> <span>Disagree </span> <br>
                                            <input type="radio" id="Q5A3" name="Q5" value="3" /> <label for="Q5A3"> &nbsp; </label> <span>No Strong Opinion </span> <br>
                                            <input type="radio" id="Q5A4" name="Q5" value="4" /> <label for="Q5A4">  &nbsp;</label> <span>Agree</span> <br>
                                            <input type="radio" id="Q5A5" name="Q5" value="5" /> <label for="Q5A5">  &nbsp; </label> <span>Strongly Agree</span> <br>      

                                            
                                        </div>              
                                    </div>
                                </div> 

                                <div id="cItem6" class="item container-fluid" > 
                                    <!--Q7 -->
                                    <div class="risk_profile_qtsec">               
                                        <b>When an Investment in which I have strong long term belief drops in value by 30%. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q6A1" name="Q6" value="1" /> <label for="Q6A1"> &nbsp; </label>  <span>I sell immediately </span> <br>
                                            <input type="radio" id="Q6A2" name="Q6" value="2" /> <label for="Q6A2"> &nbsp; </label> <span>I don't know what to do </span> <br>
                                            <input type="radio" id="Q6A3" name="Q6" value="3" /> <label for="Q6A3"> &nbsp; </label> <span>I may hold it for 1 more year </span> <br>
                                            <input type="radio" id="Q6A4" name="Q6" value="4" /> <label for="Q6A4">  &nbsp;</label> <span>I may hold it for up to 3 years</span> <br>
                                            <input type="radio" id="Q6A5" name="Q6" value="5" /> <label for="Q6A5">  &nbsp; </label> <span>I may hold it up to 5 years </span> <br>    

                                            
                                        </div>              
                                    </div>
                                </div> 

                                <div id="cItem7" class="item container-fluid" > 
                                    <!--Q8 -->
                                    <div class="risk_profile_qtsec">               
                                        <b>When the price of a Stock of a company with good prospects falls to its lowest levels, I buy more of that company's stock. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q7A1" name="Q7" value="1" /> <label for="Q7A1"> &nbsp;</label><span>Strongly Disagree</span> <br>
                                            <input type="radio" id="Q7A2" name="Q7" value="2" /> <label for="Q7A2"> &nbsp; </label> <span>Disagree </span> <br>
                                            <input type="radio" id="Q7A3" name="Q7" value="3" /> <label for="Q7A3"> &nbsp; </label> <span>No Strong Opinion </span> <br>
                                            <input type="radio" id="Q7A4" name="Q7" value="4" /> <label for="Q7A4">  &nbsp;</label> <span>Agree</span> <br>
                                            <input type="radio" id="Q7A5" name="Q7" value="5" /> <label for="Q7A5">  &nbsp; </label> <span>Strongly Agree</span> <br>      
  

                                            
                                        </div>              
                                    </div>
                                </div> 

                                <div id="cItem8" class="item container-fluid" > 
                                    <!--Q9 -->
                                    <div class="risk_profile_qtsec">               
                                        <b>I have adequate savings/assets to support my family's lifestyle in the future. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q8A1" name="Q8" value="1" /> <label for="Q8A1"> &nbsp;</label><span>Strongly Disagree</span> <br>
                                            <input type="radio" id="Q8A2" name="Q8" value="2" /> <label for="Q8A2"> &nbsp; </label> <span>Disagree </span> <br>
                                            <input type="radio" id="Q8A3" name="Q8" value="3" /> <label for="Q8A3"> &nbsp; </label> <span>No Strong Opinion </span> <br>
                                            <input type="radio" id="Q8A4" name="Q8" value="4" /> <label for="Q8A4">  &nbsp;</label> <span>Agree</span> <br>
                                            <input type="radio" id="Q8A5" name="Q8" value="5" /> <label for="Q8A5">  &nbsp; </label> <span>Strongly Agree</span> <br>   

                                            
                                        </div>              
                                    </div>
                                </div> 


                                <div id="cItem9" class="item container-fluid" > 
                                    <!--Q10-->
                                    <div class="risk_profile_qtsec">               
                                        <b>Which investment portfolio matches your requirement. </b> 
                                        <div class="radio_sec">
                                           
                                            <input type="radio" id="Q9A1" name="Q9" value="1" /> <label for="Q9A1"> &nbsp; </label>  <span>5 year returns of 10% per year with interim loss of up to -8% </span> <br>
                                            <input type="radio" id="Q9A2" name="Q9" value="2" /> <label for="Q9A2"> &nbsp; </label> <span>5 year returns of 12% per year with interim loss of up to -18% </span> <br>
                                            <input type="radio" id="Q9A3" name="Q9" value="3" /> <label for="Q9A3"> &nbsp; </label> <span>5 year returns of 15% per year with interim loss of up to -25% </span> <br>
                                            <input type="radio" id="Q9A4" name="Q9" value="4" /> <label for="Q9A4">  &nbsp;</label> <span>5 year returns of 18% per year with interim loss of up to -30%</span> <br>
                                            <input type="radio" id="Q9A5" name="Q9" value="5" /> <label for="Q9A5">  &nbsp; </label> <span>5 year returns of 25% per year with interim loss of up to -40% </span> <br>    

                                            
                                        </div>              
                                    </div>
                                </div> 


                            </div>
                        </div>



                        <div class="button_set uneditable">                 
                            <input type="submit" name="previous"  value="Previous" class="cancel btnPrev" href="#myCarousel" />
                            <input type="submit" name="next"  value="Next"   class="cancel btnNext" href="#myCarousel"/> <!--id="btnNext"-->
                            <!-- <input type="submit" name="next" id="next" value="Next" class="btnNext"> -->
                            <input type="submit" name="submit"  value="Submit" class="save_ed btnSubmit" href="#myCarousel"/>


                        </div>


                    </div>        

                    
                    
                </div>
                
                
            </div>
        </section>
    </main>
<!-- End -->

<!-- Footer Section -->
<!-- 	<footer class="inner_class">
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
        	<h6 class="copyright">&copy;<script type="text/javascript"> var now = new Date(); var d = now.getFullYear(); document.write(d);</script> MMF Investor Registration Pvt. Ltd.</h6>
        </div>	
    </footer> -->
            <mmf-footer-post-login></mmf-footer-post-login>
    <!-- Footer End -->
    

    <!-- Modal -->
    <div class="modal fade" id="modalShowError" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="modalErrorTitle"></h4>
          </div>
          <div class="modal-body" id="modalErrorBody">
            this is modalbody
          </div>
          <div class="modal-footer">
            <button type="button" class="btn-flat inverse" data-dismiss="modal">OK</button>
          </div>
        </div>
      </div>
    </div>
    
    <!--Modal End-->
	<!-- Modal -->
  <div class="modal fade" id="rpInfoModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="background:#4aae3c ; color:white">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"> <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> Risk Profile Questionnaire</h4>
        </div>
        <div class="modal-body">
          <p style="padding:5%;text-align:center">
          		<strong>Risk assessment</strong> is a crucial component of any financial planning process.<br>						<strong>Risk Profile</strong> Help Advisors to get detail insight about your financial Profile,which advisors will use as input for creating your investment plan.
          </p>
        </div>
        <div class="modal-footer" style="display:none;">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
	

     <script type="text/javascript">
        $(document).ready(function(){
            currentSlide = $("#myCarousel").find('.active').index();
            if (currentSlide == 0) {
                
                $('.btnPrev').hide();
                $('.btnSubmit').hide();  
            }

            $('input[type=radio]').change(goToNextPage);
            
    });
    </script> 

    <!-- Modules -->
    <script src="js/app.js"></script>

	<script type="text/javascript" src="js/custom.js"></script>
    <script type="text/javascript" src="js/controllers/RiskProfile-v3.js"></script>
    <script src="js/directives/riskProfile.js" charset="UTF-8"></script>
</body>
</html>
