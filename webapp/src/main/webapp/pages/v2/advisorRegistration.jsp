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
	        	padding-top: 80px;
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
        <script src="js/vendor/autosize.min.js"></script>
        <script src="js/vendor/jquery.toast.min.js"></script>
        
        <script src="js/constants.js"></script>
        <script src="js/main.js"></script>
        <script src= "js/spinner.js"></script>
        <script src= "js/clevertap.js"></script>
        
    </head>
    <body  ng-app="appMMF" ng-controller="AdvisorRegistration">
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
			<style>
			lable{
				margin-left: 12px !important;
			}
			item{
				padding-top:10px;
			}
			.mandatory::-webkit-input-placeholder:after {
			    content:' *';
			    color: red;
			}
			.mandatory:-moz-placeholder:after {
			    /* Firefox 18- */
			    content:' *';
			    color: red;
			}
			.mandatory::-moz-placeholder:after {
			    /* Firefox 19+ */
			    content:' *';
			    color: red;
			}
			.mandatory:-ms-input-placeholder:after {
			    content:' *';
			    color: red;
			}
			
			label.mandatory:after{
				content:' *';
			    color: red;
			}
			
			
			
			.breadcrumb{
				padding:2px !important;
				padding-left:5px  !important;
			}
			
			.breadcrumb li{
				font-weight: bold;
				font-size: 18px;
			}
			
			.txtRupee{
				background-image: url('img/icon_rupee.png');
				background-repeat: no-repeat;
				background-position: left center; 
				background-size: auto 20px;
				padding-left: 25px !important;
				padding-bottom: 30px;
			}
			.calender{
				background-image: url('img/icon_calender.png');
				background-repeat: no-repeat;
				background-position: right center; 
				background-size: auto 20px;
				padding-right: 25px !important;
				padding-bottom: 30px;
			}
			
			#tblQualification td{
				padding-right: 10px;
			}
			#tblQualification th{
				padding-right: 10px;
			}
			a:hover{
				color: #0C95BE !important
			}
			</style>
			<div class="row-fluid">
				<h2 class="page-title"> Registration </h2>
			</div>
			<div id="divLoading">Loading...</div>
			<form id='frmFinalFormSubmit' method="post"></form>
			<div id="divContent" style="display:none">
				<div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="false" data-wrap="false">
					<div class="carousel-inner" role="listbox">
						<!-- Carousal item Q0 start -->
						<div class="item active container-fluid">
							<ol class="breadcrumb">
								<li>Personal Details</li>
							</ol>
							<div class="row">
								<div class="col-md-1">
									<div class="input-group">
										<span class="input-group-addon">
											<input class="Q0" type="radio" name="Q0" ng-model="data.indvOrCprt" ng-checked="data.indvOrCprt==true" value="true"></input>
										</span>
										<label for="Q0A0"><h6>Individual</h6></label>
									</div>
								</div>
								<div class="col-md-1">
									<div class="input-group">
										<span class="input-group-addon">
											<input class="Q0" type="radio"  name="Q0" ng-model="data.indvOrCprt" ng-checked="data.indvOrCprt==false" value="false"></input>
										</span>
										<label for="Q0A1"><h6>Corporate</h6></label>
									</div>
								</div>
							</div>
							<hr>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4">
									<label  class="mandatory">First Name</label>
									<input id="fname" ng-model="data.fname" type="text" class="form-control mandatory alphaWithSpace" placeholder="First Name"  value="">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4">
									<label >Middle Name</label>
									<input id="middle_name" ng-model="data.middle_name" type="text" class="form-control alphaWithSpace" placeholder="Middle Name">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4">
									<label id="last_nameLabel"  class="mandatory">Last Name</label>
									<input id="last_name" ng-model="data.last_name" type="text" class="form-control mandatory alphaWithSpace" placeholder="Last Name">
								</div>
							</div>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label id="panLabel" class="mandatory">Individual PAN Number</label>
									<input id="pan" ng-model="data.pan"  type="text" class="form-control mandatory" maxlength="10" placeholder="Individual PAN Number" onblur="javascript:validatePanNumber(this);">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4 ">
									<label  class="mandatory">Date Of Birth</label>
									<span class="form-control-feedback" aria-hidden="true"></span>
									<input id="dob" ng-model="data.dob" type="text" class="form-control  calender DD-MM-YYYY-P mandatory" placeholder="Date of Birth" value="" onchange="javascript:validateDOB(this);" maxlength="10">
								</div>
							</div>
							
							
							
							
							<div class="row" style="margin-top: 20px">
								<div class="form-group js-float-label-wrapper col-md-4">
									<label  class="mandatory">SEBI Investment Advisor Registration No</label>
									<input id="sebi_regno" ng-model="data.sebi_regno"  type="text" class="form-control mandatory" placeholder="SEBI Investment Advisor Registration No" maxlength="16">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4 ">
									<label  class="mandatory">Validity of Registration</label>
									<span class="form-control-feedback" aria-hidden="true"></span>
									<input id="sebi_validity" ng-model="data.sebi_validity"  type="text" class="form-control  calender DD-MM-YYYY mandatory" placeholder="Validity of Registration" value="" maxlength="10">
								</div>
								<div class="input-group  col-md-4">
									<span class="input-group-btn">
										<span class="btn btn-primary btn-file">
											Attach File <input id="sebiCertFile" type="file" multiple="" onchange="javascript:uploadSebiCertificate();" accept=".jpeg,.pdf,.PNG,.jpg,.PDF,.png,.JPEG,.JPG">
										</span>
									</span>
									<input id="sebiPath" type="text" class="form-control" readonly=""  style="margin-left: 10px;" placeholder="Upload Scanned Copy of SEBI Investment Advisor Certification"  value="{{data.sebiPath.split('/')[data.sebiPath.split('/').length-1]}}">
								</div>
								<div  class="  col-md-4">
									<span id="sebiPathMsg"></span><br>
									Upload Scanned Copy of SEBI Investment Advisor Certification <br>(Max allowable size is 2MB. Supported formats- jpeg, png and pdf)
								</div>
							</div>
						</div>
						<!-- Carousal item Q0 end -->
						
						
						
						
						<!-- Carousal item Q1 start -->
						<div class="item container-fluid" >
							<ol class="breadcrumb">
								<li>Office Details</li>
							</ol>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4">
									<label id="orgLabel"  class="">Organization</label>
									<input id="organization" ng-model="data.organization"  type="text" class="form-control mandatory" placeholder="Organization">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory" >Job Title</label>
									<input id="jobTitle" ng-model="data.jobTitle"  type="text" maxlength="37" class="form-control mandatory" placeholder="Job Title">
								</div>
								
							</div>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory" >Address line 1</label>
									<input id="oaddressLine1" ng-model="data.oaddressLine1"  type="text" maxlength="37" class="form-control mandatory" placeholder="Address line 1">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label >Address line 2</label>
									<input id="oaddressLine2" ng-model="data.oaddressLine2"  type="text" maxlength="37" class="form-control" placeholder="Address line 2">
								</div>
								<div class="form-group js-float-label-wrapper col-md-2" >
									<label class="mandatory" >PIN code</label>
									<input id="opincode" ng-model="data.opincode"  type="text" maxlength="6" class="form-control numericOnly mandatory" placeholder="PIN code">
								</div>
							</div>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory">Country</label>
									<select id="ocountry"  ng-model="data.ocountry" class="form-control mandatory selectCountry" disabled="disabled"></select>
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory">State</label>
									<select id="ostate"  ng-model="data.ostate" class="form-control mandatory selectState" onchange="javascript:getStateWiseCites('#ostate','#ocity')">
										<option value="" >Select</option>
									</select>
								</div>
								<div class="form-group js-float-label-wrapper col-md-3" >
									<label class="mandatory">City</label>
									<select id="ocity"  ng-model="data.ocity" class="form-control mandatory selectCity">
										<option value="" >Select</option>
									</select>
								</div>
							</div>
							
							<h1></h1><h1></h1>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory" >Mobile Number</label>
									<input id="omobile"  ng-model="data.omobile" type="text" maxlength="10" class="form-control mandatory numericOnly" placeholder="Mobile Number">
								</div>
								<div class="form-group js-float-label-wrapper col-md-2" >
									<label class="mandatory" >Email</label>
									<input id="regEmail"  ng-model="data.regEmail" type="text" maxlength="6" class="form-control mandatory" placeholder="Email" disabled="disabled">
								</div>
							</div>
							<div class="row">
								<div class="col-md-3">
									Landline
									<table style="margin-top: 10px">
										<tr>
											<td>
												<div class="form-group js-float-label-wrapper" >
													<label  style="left:0px">ISD</label>
													<input id="oisd"  ng-model="data.oisd" type="text" size="5", maxlength="5" class="form-control numericOnly"  placeholder="">
												</div>
											</td>
											<td>&nbsp;</td>
											<td>
												<div class="form-group js-float-label-wrapper" >
													<label  style="left:0px">STD</label>
													<input id="ostd"  ng-model="data.ostd" type="text" size="5" maxlength="5" class="form-control numericOnly"  placeholder="">
												</div>
											</td>
											<td>&nbsp;</td>
											<td>
												<div class="form-group js-float-label-wrapper" >
													<label  style="left:0px">Landline Number</label>
													<input id="otnumber"  ng-model="data.otnumber" type="text" maxlength="10" class="form-control numericOnly"  placeholder="">
												</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<!-- Carousal item Q1 end -->
						
						
						
						
						
						
						
						<!-- Carousal item Q3 start -->
						<div class="item container-fluid">
							<ol class="breadcrumb">
								<li>Bank Details</li>
							</ol>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory">Bank</label>
									<select id="bankName"  ng-model="data.bankName" class="form-control selectBank">
										<option value="">Select</option>
									</select>
									
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory">Account Type</label>
									<select id="accountType"  ng-model="data.accountType" class="form-control">
										<option value="">Select</option>
										<option value="Savings Account" selected="selected">Savings Account</option>
										<option value="Current Account">Current Account</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label  class="mandatory" >Bank Account Number</label>
									<input id="accountNumber"  ng-model="data.accountNumber" type="text" class="form-control password numericOnly" placeholder="Bank Account Number"  onchange="javascript:togglePasswordType(this);" onfocus="javascript:togglePasswordType(this);" onblur="javascript:togglePasswordType(this);" onclick="javascript:togglePasswordType(this);"  maxlength="20">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label  class="mandatory" >Re enter Bank Account Number</label>
									<input id="raccountNumber"  ng-model="data.raccountNumber" type="text" class="form-control mandatory numericOnly" placeholder="Re enter Bank Account Number" onblur="javascript:reValidateAccno(this);"  maxlength="20">
								</div>
							</div>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory" >IFSC No.</label>
									<input id="ifscNo" ng-model="data.ifscNo"  type="text" class="form-control mandatory" placeholder="IFSC No." onblur="javascript:validateIFSC(this);" maxlength="11">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory" >MICR No.</label>
									<input id="micrNo" ng-model="data.micrNo"  type="text" class="form-control mandatory numericOnly" placeholder="MICR No." maxlength="9">
								</div>
							</div>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label  class="mandatory" >Address line 1</label>
									<input id="baddressLine1" ng-model="data.baddressLine1"  type="text" maxlength="37" class="form-control mandatory" placeholder="Address line 1">
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label >Address line 2</label>
									<input id="baddressLine2" ng-model="data.baddressLine2"  type="text" maxlength="37" class="form-control" placeholder="Address line 2">
								</div>
								<div class="form-group js-float-label-wrapper col-md-2" >
									<label class="mandatory" >PIN code</label>
									<input id="bpincode" ng-model="data.bpincode"  type="text" maxlength="6" class="form-control numericOnly mandatory" placeholder="PIN code">
								</div>
							</div>
							<div class="row">
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory">Country</label>
									<select id="bcountry" ng-model="data.bcountry"  class="form-control mandatory selectCountry" disabled="disabled"></select>
								</div>
								<div class="form-group js-float-label-wrapper col-md-4" >
									<label class="mandatory">State</label>
									<select id="bstate" ng-model="data.bstate"  class="form-control mandatory selectState" onchange="javascript:getStateWiseCites('#bstate','#bcity')">
										<option value="" >Select</option>
									</select>
								</div>
								<div class="form-group js-float-label-wrapper col-md-3" >
									<label class="mandatory">City</label>
									<select id="bcity" ng-model="data.bcity"  class="form-control mandatory selectCity">
										<option value="" >Select</option>
									</select>
								</div>
							</div>
						</div>
						<!-- Carousal item Q3 end -->
						
						<!-- Carousal item Q3 start -->
						<div class="itemx container-fluid" style="display:none">
							<ol class="breadcrumb">
								<li>Qualification Information</li>
							</ol>
							<table style="width: auto;" >	
								
								<tr>
									<th>Qualification</th>
									<th>Institute</th>
									<th>Year</th>
									<th>Qualification ID</th>
								</tr>
								<tbody id="tblQualification" class="tblQualification">
									<tr>
										<td>
											<input id="qQualification" type="text" class="form-control qQualification" placeholder="Please Enter Qualification">
										</td>
										<td>
											<input id="qInstitute" type="text" class="form-control qInstitute" placeholder="Please Enter Institute">
										</td>
										<td>
											<input id="qYear" type="text" class="form-control  calender YYYY mandatory qYear" placeholder="Year" value="">
										</td>
										<td>
											<input id="qQualificationId" type="text" class="form-control qQualificationId" placeholder="Please Enter Qualification ID">
										</td>
										<td width=100>
											<img src="img/x.png" id="btnRemoveRow" width="30px">
										</td>
									</tr>
								</tbody>
							</table>
							<br><img id="btnAddQualification" src="img/addRow.png" style="width:40px;min-height:40px; cursor: pointer;" data-toggle="tooltip" data-delay=0  data-trigger="hover" data-placement="left" title="Tooltip on left">
						</div>
						<!-- Carousal item Q3 end -->
						
						
						<!-- Carousal item Q3 start -->
						<div class="itemx container-fluid" style="display:none">
							<ol class="breadcrumb">
								<li>Profile Details</li>
							</ol>
							<table>
								<tbody id="tblProfileDetails" class="tblProfileDetails">
									<tr>
										<td  style="vertical-align: top"><img alt="" src="img/addphoto.png" style="width:200px;margin-right: 25px;"></td>
										<td style="vertical-align: top">
											<div class="row">
												<div class="form-group js-float-label-wrapper col-md-2" >
													<label  class="mandatory" >Minimum AUM</label>
													<input id="" type="text" class="form-control txtRupee numericOnly mandatory" placeholder="0 = No Limits">
												</div>
												
												<div class="form-group js-float-label-wrapper col-md-2" >
													<label class="mandatory">Investment Horizinon</label>
													<select class="form-control" style="width:100px">
														<option value="">1 Year</option>
														<option value="">2 Years</option>
														<option value="">3 Years</option>
														<option value="">4 Years</option>
														<option value="">5 Years</option>
														<option value="">6 Years</option>
														<option value="">7 Years</option>
														<option value="">8 Years</option>
														<option value="">9 Years</option>
														<option value="">10 Years</option>
														<option value="">11 Years</option>
														<option value="">12 Years</option>
														<option value="">13 Years</option>
														<option value="">14 Years</option>
														<option value="">15 Years</option>
														<option value="">16 Years</option>
														<option value="">17 Years</option>
														<option value="">18 Years</option>
														<option value="">19 Years</option>
														<option value="">20 Years</option>
													</select>
												</div>
												
												<div class="col-md-12">&nbsp;</div>
												<div class="form-group js-float-label-wrapper col-md-12" >
													<label  class="mandatory" > One-line profile description</label>
													<input id="" type="text" class="form-control password" placeholder=" one-line profile description">
												</div>
												<div class="col-md-12">&nbsp;</div>
												<div class="form-group js-float-label-wrapper col-md-12" >
													<label  class="mandatory" >About me</label>
													<textarea rows="1"  class="form-control mandatory" placeholder="About me"></textarea>
												</div>
												<div class="form-group js-float-label-wrapper col-md-12" >
													<label  class="mandatory" >My Investment Strategy</label>
													<textarea rows="1" class="form-control mandatory" placeholder="My Investment Strategy"></textarea>
												</div>
												<div class="col-md-12">&nbsp;</div>
												
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- Carousal item Q3 end -->
						
					</div>
				 </div>
				 
				<style>
					.stepProgress .bar {
						width: 20px;
					}
				</style>
			
				<div style="position: fixed;bottom:5px; width:100%; background-color: #ffffff">
					
					<div style="margin-right: 20px; background-color: #F5F5F5; padding:10px;border-radius: 10px;">
						<div class="stepProgress">
							<div id="spc0" class="circle active">
								<a><span class="label">1</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc1" class="circle">
								<a><span class="label">2</span></a>
							</div>
							<span class="bar"></span>
							<div id="spc2" class="circle">
								<a><span class="label">3</span></a>
							</div>
							<div style="display: none">
								<span class="bar"></span>
								<div id="spc3" class="circle">
									<a><span class="label">4</span></a>
								</div>
								<span class="bar"></span>
								<div id="spc4" class="circle">
									<a><span class="label">5</span></a>
								</div>
							</div>
						</div>
						<div style="margin-top: 36px; width: 500px; height: 35px; margin: auto; margin-left:250px">
							<h6>&nbsp;
								<span id="divAggrement" style="margin-top: -15px;text-align: center;display:none;">
									<input id="cbSubscriberAgreement" type="checkbox" name="my-checkbox">
									 I/We hereby Accept ManageMyFortune.com
									<a href="http://managemyfortune.com/faces/pages/advisor_agreement.xhtml" target="_blank">Advisor Agreement </a>
								</span>
							</h6>
						</div>
						<div class="text-right"  style="margin-right:0px !important; ">
							<div class="text-right"  style="margin-right:10px !important;margin-top: -45px; ">
					   			<button type="button" id="btnPrev" class="btn-flat inverse large" href="#myCarousel" role="button"  style="display: none">Previous</button>
						        <button type="button" id="btnNext" class="btn-flat inverse large" href="#myCarousel" role="button" >Next</button>
						   		<button type="button" id="btnSubmitx" class="btn-flat success large" href="" ng-click="finalSave();" style="display: none">Submit</button>
						    </div>
						</div>
					</div>
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
					      	<button type="button" class="btn-flat inverse" data-dismiss="modal" href="javascript:uploadPanCard();" onclick="javascript:uploadPanCard();" style="display:none">OK</button>
							<button type="button" class="btn-flat inverse" data-dismiss="modal" href="javascript:makeSubmit;" onclick="javascript:makeSubmit();">OK</button>
					      </div>
					    </div>
					</div>
				</div>
					
				<div class="modal fade" id="modalShowSubmit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1">
					<div class="modal-dialog" role="document">
					    <div class="modal-content">
					      <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					        <h4 class="modal-title" id="myModalLabel1"></h4>
					      </div>
					      <div class="modal-body">
					        <div id="divReferanceCode">
					        	<div class="container">
					        		<div class="form-group js-float-label-wrapper" style="width:200px">
										<label class=""  style="margin-left: -15px;">Reference Code</label>
										<input id="" type="text" class="form-control required numericOnly" placeholder="Reference Code">
									</div>
					        	</div>
					        </div>
					        <div><h6>&nbsp;<span id="divAggrement">&nbsp;&nbsp;&nbsp;<input id="cbSubscriberAgreement " type="checkbox"  name="my-checkbox"> I/We hereby Accept ManageMyFortune.com <a href="http://managemyfortune.com/faces/pages/subscriber_agreement.xhtml" target="_blank">Subscriber Agreement </a> </span></h6></div>
							
					      </div>
					      <div class="modal-footer">
					      	<button type="button" class="btn-flat inverse" data-dismiss="modal" href="javascript:makeSubmit();" onclick="javascript:makeSubmit();">OK</button>
					      </div>
					    </div>
					</div>
				</div>
			</div>
			
			
			<script>
				initSelectBox = function(){
					setSelectCountry();
					setSelectState();
					setSelectCity();
					setSelectBank();
				}
				
				setSelectCountry = function(){
					//var selectCountryOptions = ["Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antarctica","Antigua and Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia and Herzegowina","Botswana","Bouvet Island","Brazil","British Indian Ocean Territory","Brunei Darussalam","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic","Chad","Chile","China","Christmas Island","Cocos (Keeling) Islands","Colombia","Comoros","Congo","Congo, the Democratic Republic of the","Cook Islands","Costa Rica","Cote d'Ivoire","Croatia (Hrvatska)","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","East Timor","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands (Malvinas)","Faroe Islands","Fiji","Finland","France","France, Metropolitan","French Guiana","French Polynesia","French Southern Territories","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guadeloupe","Guam","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Heard and Mc Donald Islands","Holy See (Vatican City State)","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran (Islamic Republic of)","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea, Democratic People's Republic of","Korea, Republic of","Kuwait","Kyrgyzstan","Lao People's Democratic Republic","Latvia","Lebanon","Lesotho","Liberia","Libyan Arab Jamahiriya","Liechtenstein","Lithuania","Luxembourg","Macau","Macedonia, The Former Yugoslav Republic of","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mayotte","Mexico","Micronesia, Federated States of","Moldova, Republic of","Monaco","Mongolia","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles","New Caledonia","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","Northern Mariana Islands","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn","Poland","Portugal","Puerto Rico","Qatar","Reunion","Romania","Russian Federation","Rwanda","Saint Kitts and Nevis","Saint LUCIA","Saint Vincent and the Grenadines","Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Seychelles","Sierra Leone","Singapore","Slovakia (Slovak Republic)","Slovenia","Solomon Islands","Somalia","South Africa","South Georgia and the South Sandwich Islands","Spain","Sri Lanka","St. Helena","St. Pierre and Miquelon","Sudan","Suriname","Svalbard and Jan Mayen Islands","Swaziland","Sweden","Switzerland","Syrian Arab Republic","Taiwan, Province of China","Tajikistan","Tanzania, United Republic of","Thailand","Togo","Tokelau","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","United States Minor Outlying Islands","Uruguay","Uzbekistan","Vanuatu","Venezuela","Viet Nam","Virgin Islands (British)","Virgin Islands (U.S.)","Wallis and Futuna Islands","Western Sahara","Yemen","Yugoslavia","Zambia","Zimbabwe"];
					//var selectCountryValues = ["AF","AL","DZ","AS","AD","AO","AI","AQ","AG","AR","AM","AW","AU","AT","AZ","BS","BH","BD","BB","BY","BE","BZ","BJ","BM","BT","BO","BA","BW","BV","BR","IO","BN","BG","BF","BI","KH","CM","CA","CV","KY","CF","TD","CL","CN","CX","CC","CO","KM","CG","CD","CK","CR","CI","HR","CU","CY","CZ","DK","DJ","DM","DO","TP","EC","EG","SV","GQ","ER","EE","ET","FK","FO","FJ","FI","FR","FX","GF","PF","TF","GA","GM","GE","DE","GH","GI","GR","GL","GD","GP","GU","GT","GN","GW","GY","HT","HM","VA","HN","HK","HU","IS","IN","ID","IR","IQ","IE","IL","IT","JM","JP","JO","KZ","KE","KI","KP","KR","KW","KG","LA","LV","LB","LS","LR","LY","LI","LT","LU","MO","MK","MG","MW","MY","MV","ML","MT","MH","MQ","MR","MU","YT","MX","FM","MD","MC","MN","MS","MA","MZ","MM","NA","NR","NP","NL","AN","NC","NZ","NI","NE","NG","NU","NF","MP","NO","OM","PK","PW","PA","PG","PY","PE","PH","PN","PL","PT","PR","QA","RE","RO","RU","RW","KN","LC","VC","WS","SM","ST","SA","SN","SC","SL","SG","SK","SI","SB","SO","ZA","GS","ES","LK","SH","PM","SD","SR","SJ","SZ","SE","CH","SY","TW","TJ","TZ","TH","TG","TK","TO","TT","TN","TR","TM","TC","TV","UG","UA","AE","GB","US","UM","UY","UZ","VU","VE","VN","VG","VI","WF","EH","YE","YU","ZM","ZW"];
					
					var selectCountryOptions = ["India"];
					var selectCountryValues = ["IN"];
					
					$('.selectCountry').each(function(){
						for(index in selectCountryOptions) {
						    this.options[this.options.length] = new Option(selectCountryOptions[index], selectCountryValues[index]);
						}
				    });
				}
				
				setSelectState = function(){
					var settings = {
							"async": true,
							"crossDomain": true,
							"url": _gc_url_statelist,
							"method": "POST",
							"headers": {
							"cache-control": "no-cache",
							"content-type": "application/x-www-form-urlencoded"
							},
							"processData": false,
							"contentType": false,
							"mimeType": "multipart/form-data"
							//"data": formData
							}
							
							$.ajax(settings).done(function (res) {
								console.log(res);
								res = JSON.parse(res);
								var selectStateOptions = res;
								$('.selectState').each(function(){
						    		for(index in selectStateOptions) {
						    		    this.options[this.options.length] = new Option(selectStateOptions[index], selectStateOptions[index]);
						    		}
						    	});
							}).fail(function (response){
							alert(response);
							});
					
					/* var selectStateOptions = ["Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Orissa","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"];
					var selectStateValues = [];
					
					$('.selectState').each(function(){
						for(index in selectStateOptions) {
						    this.options[this.options.length] = new Option(selectStateOptions[index], selectStateOptions[index]);
						}
					}); */
				}
				
				setSelectCity = function(){
					var selectCityOptions = ["Abhayapuri","Achabbal","Achhnera","Adalaj","Adari","Adilabad","Adityana","Adoni","Adoor","Adra, Purulia","Agartala","Agra","Ahiwara","Ahmedabad","Ahmedgarh","Ahmednagar","Aizawl","Ajmer","Akaltara","Akathiyoor","Akhnoor","Akola","Alang","Alappuzha","Aldona","Aligarh","Alipurduar","Allahabad","Almora","Along","Alwar","Amadalavalasa","Amalapuram","Amarpur","Ambagarh Chowki","Ambaji","Ambala","Ambaliyasan","Ambikapur","Amguri","Amlabad","Amli","Amravati","Amreli","Amritsar","Amroha","Anakapalle","Anand","Anandapur","Anandnagaar","Anantapur","Anantnag","Ancharakandy","Andada","Anjar","Anklav","Ankleshwar","Antaliya","Anugul","Ara","Arakkonam","Arambagh","Arambhada","Arang","Araria","Arasikere","Arcot","Areraj","Arki","Arnia","Aroor","Arrah","Aruppukkottai","Asankhurd","Asansol","Asarganj","Ashok Nagar","Ashtamichira","Asika","Asola","Assandh","Ateli","Attingal","Atul","Aurangabad","Avinissery","Awantipora","Azamgarh","Babiyal","Baddi","Bade Bacheli","Badepalle","Badharghat","Bagaha","Bahadurganj","Bahadurgarh","Baharampur","Bahraich","Bairgania","Bakhtiarpur","Balaghat","Balangir","Balasore","Baleshwar","Bali","Ballabhgarh","Ballia","Bally","Balod","Baloda Bazar","Balrampur","Balurghat","Bamra","Banda","Bandikui","Bandipore","Bangalore","Banganapalle","Banka","Bankura","Banmankhi Bazar","Banswara","Bapatla","Barahiya","Barakar","Baramati","Baramula","Baran","Barasat","Barauli","Barbigha","Barbil","Bardhaman","Bareilly","Bargarh","Barh","Baripada","Barmer","Barnala","Barpeta","Barpeta Road","Barughutu","Barwala","Basudebpur","Batala","Bathinda","Bazpur","Begusarai","Behea","Belgaum","Bellampalle","Bellary","Belpahar","Bemetra","Bethamcherla","Bettiah","Betul","Bhabua","Bhadrachalam","Bhadrak","Bhagalpur","Bhagha Purana","Bhainsa","Bharuch","Bhatapara","Bhavani","Bhavnagar","Bhawanipatna","Bheemunipatnam","Bhimavaram","Bhiwani","Bhongir","Bhopal","Bhuban","Bhubaneswar","Bhuj","Bidhan Nagar","Bihar Sharif","Bikaner","Bikramganj","Bilasipara","Bilaspur","Biramitrapur","Birgaon","Bobbili","Bodh Gaya","Bodhan","Bokaro Steel City","Bomdila","Bongaigaon","Brahmapur","Brajrajnagar","Budhlada","Burhanpur","Buxar","Byasanagar","Calcutta","Cambay","Chaibasa","Chakradharpur","Chalakudy","Chalisgaon","Chamba","Champa","Champhai","Chamrajnagar","Chandan Bara","Chandausi","Chandigarh","Chandrapura","Changanassery","Chanpatia","Charkhi Dadri","Chatra","Cheeka","Chendamangalam","Chengalpattu","Chengannur","Chennai","Cherthala","Cheruthazham","Chhapra","Chhatarpur","Chikkaballapur","Chikmagalur","Chilakaluripet","Chinchani","Chinna salem","Chinsura","Chintamani","Chirala","Chirkunda","Chirmiri","Chitradurga","Chittoor","Chittur-Thathamangalam","Chockli","Churi","Coimbatore","Colgong","Contai","Cooch Behar","Coonoor","Cuddalore","Cuddapah","Curchorem Cacora","Cuttack","Dabra","Dadri","Dahod","Dalhousie","Dalli-Rajhara","Dalsinghsarai","Daltonganj","Daman and Diu","Darbhanga","Darjeeling","Dasua","Datia","Daudnagar","Davanagere","Debagarh","Deesa","Dehradun","Dehri-on-Sone","Delhi","Deoghar","Deoria","Devarakonda","Devgarh","Dewas","Dhaka","Dhamtari","Dhanbad","Dhar","Dharampur, India","Dharamsala","Dharmanagar","Dharmapuri","Dharmavaram","Dharwad","Dhekiajuli","Dhenkanal","Dholka","Dhubri","Dhule","Dhuri","Dibrugarh","Digboi","Dighwara","Dimapur","Dinanagar","Dindigul","Diphu","Dipka","Dispur","Dombivli","Dongargarh","Dumka","Dumraon","Durg-Bhilai Nagar","Durgapur","Ellenabad 2","Eluru","Erattupetta","Erode","Etawah","Faridabad","Faridkot","Farooqnagar","Fatehabad","Fatehpur","Fatwah","Fazilka","Firozpur","Firozpur Cantt.","Forbesganj","Gadag","Gadwal","Ganaur","Gandhinagar","Gangtok","Garhwa","Gauripur","Gaya","Gharaunda","Ghatshila","Giddarbaha","Giridih","Goalpara","Gobindgarh","Gobranawapara","Godda","Godhra","Gogri Jamalpur","Gohana","Golaghat","Gomoh","Gooty","Gopalganj","Greater Noida","Gudalur","Gudivada","Gudur","Gulbarga","Gumia","Gumla","Gundlupet","Guntakal","Guntur","Gunupur","Gurdaspur","Gurgaon","Guruvayoor","Guwahati","Gwalior","Haflong","Haibat(Yamuna Nagar)","Hailakandi","Hajipur","Haldia","Haldwani","Hamirpur","Hansi","Hanuman Junction","Hardoi","Haridwar","Hassan","Hazaribag","Hilsa","Himatnagar","Hindupur","Hinjilicut","Hisar","Hisua","Hodal","Hojai","Hoshiarpur","Hospet","Howrah","Hubli","Hussainabad","Hyderabad","Ichalkaranji","Ichchapuram","Idar","Imphal","Indore","Indranagar","Irinjalakuda","Islampur","Itanagar","Itarsi","Jabalpur","Jagatsinghapur","Jagdalpur","Jagdispur","Jaggaiahpet","Jagraon","Jagtial","Jaipur","Jaisalmer","Jaitu","Jajapur","Jajmau","Jalalabad","Jalandhar","Jalandhar Cantt.","Jaleswar","Jalna","Jalore","Jamalpur","Jammalamadugu","Jammu","Jamnagar","Jamshedpur","Jamtara","Jamui","Jandiala","Jangaon","Janjgir","Jashpurnagar","Jaspur","Jatani","Jaunpur","Jehanabad","Jeypur","Jhajha","Jhajjar","Jhanjharpur","Jhansi","Jhargram","Jharsuguda","Jhumri Tilaiya","Jind","Joda","Jodhpur","Jogabani","Jogendranagar","Jorhat","Jowai","Junagadh","Kadapa","Kadi","Kadiri","Kadirur","Kagaznagar","Kailasahar","Kaithal","Kakching","Kakinada","Kalan Wali","Kalavad","Kalka","Kalliasseri","Kalol","Kalpetta","Kalpi","Kalyan","Kalyandurg","Kamareddy","Kanchipuram","Kandukur","Kanhangad","Kanjikkuzhi","Kanker","Kannur","Kanpur","Kantabanji","Kanti","Kapadvanj","Kapurthala","Karaikal","Karaikudi","Karanjia","Karimganj","Karimnagar","Karjan","Karkala","Karnal","Karoran","Kartarpur","Karungal","Karur","Karwar","Kasaragod","Kashipur","Kathua","Katihar","Katni","Kavali","Kavaratti","Kawardha","Kayamkulam","Kendrapara","Kendujhar","Keshod","Khagaria","Khambhalia","Khambhat","Khammam","Khanna","Kharagpur","Kharar","Kheda","Khedbrahma","Kheralu","Khordha","Khowai","Khunti","kichha","Kishanganj","Kochi","Kodinar","Kodungallur","Kohima","Kokrajhar","Kolar","Kolhapur","Kolkata","Kollam","Kollankodu","Kondagaon","Koothuparamba","Koraput","Koratla","Korba","Kot Kapura","Kota","Kotdwara","Kothagudem","Kothamangalam","Kothapeta","Kotma","Kottayam","Kovvur","Kozhikode","Kunnamkulam","Kurali","Kurnool","Kyathampalle","Lachhmangarh","Ladnu","Ladwa","Lahar","Laharpur","Lakheri","Lakhimpur","Lakhisarai","Lakshmeshwar","Lal Gopalganj Nindaura","Lalganj","Lalgudi","Lalitpur","Lalsot","Lanka","Lar","Lathi","Latur","Leh","Lilong","Limbdi","Lingsugur","Loha","Lohardaga","Lonar","Lonavla","Longowal","Loni","Losal","Lucknow","Ludhiana","Lumding","Lunawada","Lundi","Lunglei","Macherla","Machilipatnam","Madanapalle","Maddur","Madgaon","Madhepura","Madhubani","Madhugiri","Madhupur","Madikeri","Madurai","Magadi","Mahad","Mahalingpur","Maharajganj","Maharajpur","Mahasamund","Mahbubnagar","Mahe","Mahendragarh","Mahesana","Mahidpur","Mahnar Bazar","Mahuli","Mahuva","Maihar","Mainaguri","Makhdumpur","Makrana","Mal","Malajkhand","Malappuram","Malavalli","Malegaon","Malerkotla","Malkangiri","Malkapur","Malout","Malpura","Malur","Manasa","Manavadar","Manawar","Manchar","Mancherial","Mandalgarh","Mandamarri","Mandapeta","Mandawa","Mandi","Mandi Dabwali","Mandideep","Mandla","Mandsaur","Mandvi","Mandya","Maner","Mangaldoi","Mangalore","Mangalvedhe","Manglaur","Mangrol","Mangrulpir","Manihari","Manjlegaon","Mankachar","Manmad","Mansa","Manuguru","Manvi","Manwath","Mapusa","Margao","Margherita","Marhaura","Mariani","Marigaon","Markapur","Marmagao","Masaurhi","Mathabhanga","Mathura","Mattannur","Mauganj","Maur","Mavelikkara","Mavoor","Mayang Imphal","Medak","Medinipur","Meerut","Mehkar","Mehmedabad","Memari","Merta City","Mhaswad","Mhow Cantonment","Mhowgaon","Mihijam","Miraj","Mirganj","Miryalaguda","Modasa","Modinagar","Moga","Mogalthur","Mohali","Mokameh","Mokokchung","Monoharpur","Morena","Morinda","Morshi","Morvi","Motihari","Motipur","Mount Abu","Mudalgi","Mudbidri","Muddebihal","Mudhol","Mukerian","Mukhed","Muktsar","Mul","Mulbagal","Multai","Mumbai","Mundargi","Mungeli","Munger","Muradnagar","Murliganj","Murshidabad","Murtijapur","Murwara","Musabani","Mussoorie","Muvattupuzha","Muzaffarnagar","Muzaffarpur","Mysore","Nabadwip","Nabarangapur","Nabha","Nadbai","Nadiad","Nagaon","Nagapattinam","Nagar","Nagari","Nagarkurnool","Nagaur","Nagda","Nagercoil","Nagina","Nagla","Nagpur","Nahan","Naharlagun","Naihati","Naila Janjgir","Nainital","Nainpur","Najibabad","Nakodar","Nakur","Nalasopara","Nalbari","Namagiripettai","Namakkal","Nanded-Waghala","Nandgaon","Nandivaram-Guduvancheri","Nandura","Nandurbar","Nandyal","Nangal","Nanjangud","Nanjikottai","Nanpara","Narasapur","Narasaraopet","Naraura","Narayanpet","Nargund","Narkatiaganj","Narkhed","Narnaul","Narsinghgarh","Narsipatnam","Narwana","Nashik","Nasirabad","Natham","Nathdwara","Naugachhia","Naugawan Sadat","Nautanwa","Navalgund","Navi Mumbai","Navsari","Nawabganj","Nawada","Nawalgarh","Nawanshahr","Nawapur","Nedumangad","Neem-Ka-Thana","Neemuch","Nehtaur","Nelamangala","Nellikuppam","Nellore","Nepanagar","Neyveli","Neyyattinkara","Nidadavole","Nilanga","Nimbahera","Nipani","Nirmal","Niwai","Niwari","Nizamabad","Nohar","NOIDA","Nokha","Nongstoin","Noorpur","North Lakhimpur","Nowgong","Nowrozabad","Nuzvid","O Valley","Obra","Oddanchatram","Ongole","Orai","Osmanabad","Ottappalam","Ozar","P.N.Patti","Pachora","Pachore","Pacode","Padmanabhapuram","Padra","Padrauna","Paithan","Pakaur","Palacole","Palai","Palakkad","Palani","Palanpur","Palasa Kasibugga","Palghar","Pali","Palia Kalan","Palitana","Palladam","Pallapatti","Pallikonda","Palwal","Palwancha","Panagar","Panagudi","Panaji","Panchkula","Panchla","Pandharkaoda","Pandharpur","Pandhurna","Pandua","Panipat","Panna","Panniyannur","Panruti","Panvel","Pappinisseri","Paradip","Paramakudi","Parangipettai","Parasi","Paravoor","Parbhani","Pardi","Parlakhemundi","Parli","Parola","Partur","Parvathipuram","Pasan","Paschim Punropara","Pasighat","Patan","Pathanamthitta","Pathankot","Pathardi","Pathri","Patiala","Patna","Patran","Patratu","Pattamundai","Patti","Pattukkottai","Patur","Pauni","Pauri","Pavagada","Payyannur","Pedana","Peddapuram","Pehowa","Pen","Perambalur","Peravurani","Peringathur","Perinthalmanna","Periyakulam","Periyasemur","Pernampattu","Perumbavoor","Petlad","Phagwara","Phalodi","Phaltan","Phillaur","Phulabani","Phulera","Phulpur","Phusro","Pihani","Pilani","Pilibanga","Pilibhit","Pilkhuwa","Pindwara","Pinjore","Pipar City","Pipariya","Piro","Pithampur","Pithapuram","Pithoragarh","Pollachi","Polur","Pondicherry","Ponduru","Ponnani","Ponneri","Ponnur","Porbandar","Porsa","Port Blair","Powayan","Prantij","Pratapgarh","Prithvipur","Proddatur","Pudukkottai","Pudupattinam","Pukhrayan","Pulgaon","Puliyankudi","Punalur","Punch","Pune","Punganur","Punjaipugalur","Puranpur","Puri","Purna","Purnia","Purquazi","Purulia","Purwa","Pusad","Puttur","Qadian","Quilandy","Rabkavi Banhatti","Radhanpur","Rae Bareli","Rafiganj","Raghogarh-Vijaypur","Raghunathpur","Rahatgarh","Rahuri","Raichur","Raiganj","Raigarh","Raikot","Raipur","Rairangpur","Raisen","Raisinghnagar","Rajagangapur","Rajahmundry","Rajakhera","Rajaldesar","Rajam","Rajampet","Rajapalayam","Rajauri","Rajgarh","Rajgarh (Alwar)","Rajgarh (Churu","Rajgir","Rajkot","Rajnandgaon","Rajpipla","Rajpura","Rajsamand","Rajula","Rajura","Ramachandrapuram","Ramagundam","Ramanagaram","Ramanathapuram","Ramdurg","Rameshwaram","Ramganj Mandi","Ramnagar","Ramngarh","Rampur","Rampur Maniharan","Rampura Phul","Rampurhat","Ramtek","Ranaghat","Ranavav","Ranchi","Rangia","Rania","Ranibennur","Rapar","Rasipuram","Rasra","Ratangarh","Rath","Ratia","Ratlam","Ratnagiri","Rau","Raurkela","Raver","Rawatbhata","Rawatsar","Raxaul Bazar","Rayachoti","Rayadurg","Rayagada","Reengus","Rehli","Renigunta","Renukoot","Reoti","Repalle","Revelganj","Rewa","Rewari","Rishikesh","Risod","Robertsganj","Robertson Pet","Rohtak","Ron","Roorkee","Rosera","Rudauli","Rudrapur","Rupnagar","Sabalgarh","Sadabad","Sadalgi","Sadasivpet","Sadri","Sadulshahar","Safidon","Safipur","Sagar","Sagwara","Saharanpur","Saharsa","Sahaspur","Sahaswan","Sahawar","Sahibganj","Sahjanwa","Saidpur, Ghazipur","Saiha","Sailu","Sainthia","Sakleshpur","Sakti","Salaya","Salem","Salur","Samalkha","Samalkot","Samana","Samastipur","Sambalpur","Sambhal","Sambhar","Samdhan","Samthar","Sanand","Sanawad","Sanchore","Sandi","Sandila","Sandur","Sangamner","Sangareddy","Sangaria","Sangli","Sangole","Sangrur","Sankarankoil","Sankari","Sankeshwar","Santipur","Sarangpur","Sardarshahar","Sardhana","Sarni","Sasaram","Sasvad","Satana","Satara","Sathyamangalam","Satna","Sattenapalle","Sattur","Saunda","Saundatti-Yellamma","Sausar","Savanur","Savarkundla","Savner","Sawai Madhopur","Sawantwadi","Sedam","Sehore","Sendhwa","Seohara","Seoni","Seoni-Malwa","Shahabad","Shahabad, Hardoi","Shahabad, Rampur","Shahade","Shahbad","Shahdol","Shahganj","Shahjahanpur","Shahpur","Shahpura","Shajapur","Shamgarh","Shamli","Shamsabad, Agra","Shamsabad, Farrukhabad","Shegaon","Sheikhpura","Shendurjana","Shenkottai","Sheoganj","Sheohar","Sheopur","Sherghati","Sherkot","Shiggaon","Shikapur","Shikarpur, Bulandshahr","Shikohabad","Shillong","Shimla","Shimoga","Shirdi","Shirpur-Warwade","Shirur","Shishgarh","Shivpuri","Sholavandan","Sholingur","Shoranur","Shorapur","Shrigonda","Shrirampur","Shrirangapattana","Shujalpur","Siana","Sibsagar","Siddipet","Sidhi","Sidhpur","Sidlaghatta","Sihor","Sihora","Sikanderpur","Sikandra Rao","Sikandrabad","Sikar","Silao","Silapathar","Silchar","Siliguri","Sillod","Silvassa","Simdega","Sindgi","Sindhnur","Singapur","Singrauli","Sinnar","Sira","Sircilla","Sirhind Fatehgarh Sahib","Sirkali","Sirohi","Sironj","Sirsa","Sirsaganj","Sirsi","Siruguppa","Sitamarhi","Sitapur","Sitarganj","Sivaganga","Sivagiri","Sivakasi","Siwan","Sohagpur","Sohna","Sojat","Solan","Solapur","Sonamukhi","Sonepur","Songadh","Sonipat","Sopore","Soro","Soron","Soyagaon","Sri Madhopur","Srikakulam","Srikalahasti","Srinagar","Srinivaspur","Srirampore","Srivilliputhur","Suar","Sugauli","Sujangarh","Sujanpur","Sultanganj","Sultanpur","Sumerpur","Sunabeda","Sunam","Sundargarh","Sundarnagar","Supaul","Surandai","Surat","Suratgarh","Suri","Suriyampalayam","Suryapet","Tadepalligudem","Tadpatri","Taki","Talaja","Talcher","Talegaon Dabhade","Talikota","Taliparamba","Talode","Talwara","Tamluk","Tanda","Tandur","Tanuku","Tarakeswar","Tarana","Taranagar","Taraori","Tarikere","Tarn Taran","Tasgaon","Tehri","Tekkalakota","Tenali","Tenkasi","Tenu Dam-cum- Kathhara","Terdal","Tetri Bazar","Tezpur","Thakurdwara","Thammampatti","Thana Bhawan","Thanesar","Thangadh","Thanjavur","Tharad","Tharamangalam","Tharangambadi","Theni Allinagaram","Thirumangalam","Thirunindravur","Thiruparappu","Thirupuvanam","Thiruthuraipoondi","Thiruvalla","Thiruvallur","Thiruvananthapuram","Thiruvarur","Thodupuzha","Thoothukudi","Thoubal","Thrissur","Thuraiyur","Tikamgarh","Tilda Newra","Tilhar","Tindivanam","Tinsukia","Tiptur","Tirora","Tiruchendur","Tiruchengode","Tiruchirappalli","Tirukalukundram","Tirukkoyilur","Tirunelveli","Tirupathur","Tirupati","Tiruppur","Tirur","Tiruttani","Tiruvannamalai","Tiruvethipuram","Tirwaganj","Titlagarh","Tittakudi","Todabhim","Todaraisingh","Tohana","Tonk","Tuensang","Tuljapur","Tulsipur","Tumkur","Tumsar","Tundla","Tuni","Tura","Uchgaon","Udaipur","Udaipurwati","Udgir","Udhagamandalam","Udhampur","Udumalaipettai","Udupi","Ujhani","Ujjain","Umarga","Umaria","Umarkhed","Umarkote","Umbergaon","Umred","Umreth","Una","Unjha","Unnamalaikadai","Unnao","Upleta","Uran","Uran Islampur","Uravakonda","Urmar Tanda","Usilampatti","Uthamapalayam","Uthiramerur","Utraula","Vadakara","Vadakkuvalliyur","Vadalur","Vadgaon Kasba","Vadipatti","Vadnagar","Vadodara","Vaijapur","Vaikom","Valparai","Valsad","Vandavasi","Vaniyambadi","Vapi","Varanasi","Varkala","Vasai","Vedaranyam","Vellakoil","Vellore","Venkatagiri","Veraval","Vicarabad","Vidisha","Vijainagar","Vijapur","Vijayapura","Vijayawada","Vikramasingapuram","Viluppuram","Vinukonda","Viramgam","Virar","Virudhachalam","Virudhunagar","Visakhapatnam","Visnagar","Viswanatham","Vita","Vizianagaram","Vrindavan","Vyara","Wadgaon Road","Wadhwan","Wadi","Wai","Wanaparthy","Wani","Wankaner","Wara Seoni","Warangal","Wardha","Warhapur","Warisaliganj","Warora","Warud","Washim","Wokha","Yadgir","Yamunanagar","Yanam","Yavatmal","Yawal","Yellandu","Yemmiganur","Yerraguntla","Yevla","Zahirabad","Zaidpur","Zamania","Zira","Zirakpur","Zunheboto"];
					var selectCityValues = [];
					
					$('.selectCity').each(function(){
						for(index in selectCityOptions) {
						    this.options[this.options.length] = new Option(selectCityOptions[index], selectCityOptions[index]);
						}
					});
				}
				
				setSelectBank = function(){
					var selectBankOptions = ["ABHYUDAYA CO-OP BANK LTD","ABU DHABI COMMERCIAL BANK","AKOLA DISTRICT CENTRAL CO-OPERATIVE BANK","AKOLA JANATA COMMERCIAL COOPERATIVE BANK","ALLAHABAD BANK","ALMORA URBAN CO-OPERATIVE BANK LTD","ANDHRA BANK","ANDHRA PRAGATHI GRAMEENA BANK","APNA SAHAKARI BANK LTD","AUSTRALIA AND NEW ZEALAND BANKING GROUP LIMITED","AXIS BANK","BANK INTERNASIONAL INDONESIA","BANK OF AMERICA","BANK OF BAHRAIN AND KUWAIT","BANK OF BARODA","BANK OF INDIA","BANK OF CEYLON","BANK OF TOKYO-MITSUBISHI UFJ LTD","BANK OF MAHARASHTRA","BASSEIN CATHOLIC CO-OP BANK LTD","BARCLAYS BANK PLC","BNP PARIBAS","BHARATIYA MAHILA BANK LIMITED","CANARA BANK","CALYON BANK","CATHOLIC SYRIAN BANK LTD","CAPITAL LOCAL AREA BANK LTD","CHINATRUST COMMERCIAL BANK","CENTRAL BANK OF INDIA","CITIZENCREDIT CO-OPERATIVE BANK LTD","CITIBANK NA","CORPORATION BANK","CREDIT SUISSE AG","CITY UNION BANK LTD","COMMONWEALTH BANK OF AUSTRALIA","DEUTSCHE BANK","DEUTSCHE SECURITIES INDIA PRIVATE LIMITED","DBS BANK LTD","DENA BANK","DICGC","DOMBIVLI NAGARI SAHAKARI BANK LIMITED","DEVELOPMENT CREDIT BANK LIMITED","DHANLAXMI BANK LTD","GURGAON GRAMIN BANK","HDFC BANK LTD","FIRSTRAND BANK LIMITED","GOPINATH PATIL PARSIK JANATA SAHAKARI BANK LTD","IDRBT","IDBI BANK LTD","ICICI BANK LTD","HSBC","INDUSTRIAL AND COMMERCIAL BANK OF CHINA LIMITED","INDUSIND BANK LTD","INDIAN OVERSEAS BANK","INDIAN BANK","JANASEVA SAHAKARI BANK (BORIVLI) LTD","JANAKALYAN SAHAKARI BANK LTD","JALGAON JANATA SAHKARI BANK LTD","ING VYSYA BANK LTD","KALLAPPANNA AWADE ICH JANATA S BANK","JPMORGAN CHASE BANK N.A","JANATA SAHAKARI BANK LTD (PUNE)","JANASEVA SAHAKARI BANK LTD. PUNE","KOTAK MAHINDRA BANK","KURMANCHAL NAGAR SAHKARI BANK LTD","MAHANAGAR CO-OP BANK LTD","MAHARASHTRA STATE CO OPERATIVE BANK","KAPOL CO OP BANK","KARNATAKA BANK LTD","KARNATAKA VIKAS GRAMEENA BANK","KARUR VYSYA BANK","NATIONAL AUSTRALIA BANK","NEW INDIA CO-OPERATIVE BANK LTD","NKGSB CO-OP BANK LTD","NORTH MALABAR GRAMIN BANK","MASHREQBANK PSC","MIZUHO CORPORATE BANK LTD","MUMBAI DISTRICT CENTRAL CO-OP. BANK LTD","NAGPUR NAGRIK SAHAKARI BANK LTD","PRIME CO OPERATIVE BANK LTD","PRATHAMA BANK","PUNJAB AND SIND BANK","PUNJAB AND MAHARASHTRA CO-OP BANK LTD","OMAN INTERNATIONAL BANK SAOG","NUTAN NAGARIK SAHAKARI BANK LTD","PARSIK JANATA SAHAKARI BANK LTD","ORIENTAL BANK OF COMMERCE","SBERBANK","RESERVE BANK OF INDIA","SHRI CHHATRAPATI RAJARSHI SHAHU URBAN CO-OP BANK LTD","SHINHAN BANK","RABOBANK INTERNATIONAL (CCRB)","PUNJAB NATIONAL BANK","RAJKOT NAGARIK SAHAKARI BANK LTD","RAJGURUNAGAR SAHAKARI BANK LTD","STATE BANK OF INDIA","STATE BANK OF MAURITIUS LTD","STATE BANK OF BIKANER AND JAIPUR","STATE BANK OF HYDERABAD","SOUTH INDIAN BANK","STANDARD CHARTERED BANK","SOCIETE GENERALE","SOLAPUR JANATA SAHKARI BANK LTD.SOLAPUR","THANE BHARAT SAHAKARI BANK LTD","THE A.P. MAHESH CO-OP URBAN BANK LTD","SYNDICATE BANK","TAMILNAD MERCANTILE BANK LTD","STATE BANK OF TRAVANCORE","SUMITOMO MITSUI BANKING CORPORATION","STATE BANK OF MYSORE","STATE BANK OF PATIALA","THE FEDERAL BANK LTD","THE DELHI STATE COOPERATIVE BANK LTD","THE COSMOS CO-OPERATIVE BANK LTD","THE BHARAT CO-OPERATIVE BANK (MUMBAI) LTD","THE BANK OF RAJASTHAN LTD","THE BANK OF NOVA SCOTIA","THE ANDHRA PRADESH STATE COOP BANK LTD","THE AHMEDABAD MERCANTILE CO-OPERATIVE BANK LTD","THE KANGRA CENTRAL CO-OPERATIVE BANK LTD","THE KALYAN JANATA SAHAKARI BANK LTD","THE KALUPUR COMMERCIAL CO. OP. BANK LTD","THE JAMMU AND KASHMIR BANK LTD","THE JALGAON PEOPLES CO-OP BANK","THE GUJARAT STATE CO-OPERATIVE BANK LTD","THE GREATER BOMBAY CO-OP. BANK LTD","THE GADCHIROLI DISTRICT CENTRAL COOPERATIVE BANK LTD","THE RATNAKAR BANK LTD","THE RAJASTHAN STATE COOPERATIVE BANK LTD","THE SAHEBRAO DESHMUKH CO-OP. BANK LTD","THE ROYAL BANK OF SCOTLAND N.V","THE SEVA VIKAS CO-OPERATIVE BANK LTD (SVB)","THE SARASWAT CO-OPERATIVE BANK LTD","THE SURAT DISTRICT CO OPERATIVE BANK LTD","THE SHAMRAO VITHAL CO-OPERATIVE BANK LTD","THE KARAD URBAN CO-OP BANK LTD","THE KANGRA COOPERATIVE BANK LTD","THE LAKSHMI VILAS BANK LTD","THE KARNATAKA STATE APEX COOP. BANK LTD","THE MUNICIPAL CO OPERATIVE BANK LTD MUMBAI","THE MEHSANA URBAN COOPERATIVE BANK LTD","THE NASIK MERCHANTS CO-OP BANK LTD., NASHIK","THE NAINITAL BANK LIMITED","TJSB SAHAKARI BANK LTD","TUMKUR GRAIN MERCHANTS COOPERATIVE BANK LTD","UBS AG","UCO BANK","UNION BANK OF INDIA","UNITED BANK OF INDIA","UNITED OVERSEAS BANK","VASAI VIKAS SAHAKARI BANK LTD","THE SURAT PEOPLES CO-OP BANK LTD","THE SUTEX CO.OP. BANK LTD","THE TAMILNADU STATE APEX COOPERATIVE BANK LIMITED","THE THANE DISTRICT CENTRAL CO-OP BANK LTD","THE THANE JANATA SAHAKARI BANK LTD","THE VARACHHA CO-OP. BANK LTD","THE VISHWESHWAR SAHAKARI BANK LTD.,PUNE","THE WEST BENGAL STATE COOPERATIVE BANK LTD","WOORI BANK","WESTPAC BANKING CORPORATION","WEST BENGAL STATE COOPERATIVE BANK","VIJAYA BANK","ZILA SAHAKRI BANK LIMITED GHAZI","YES BANK","ZILA SAHKARI BANK LTD GHAZIABAD","YES BANK LTD"];
					var selectBankValues = [];
					
					$('.selectBank').each(function(){
						for(index in selectBankOptions ) {
						    this.options[this.options.length] = new Option(selectBankOptions[index], selectBankOptions[index]);
						}
				    });
				}
				initSelectBox();
				
				$( '.js-float-label-wrapper' ).FloatLabel();
				togglePasswordType = function(obj){
					if(obj.value==''){
						obj.setAttribute('type', 'text');
					}
					else{
						obj.setAttribute('type', 'password');
					}
					return;
				}
			
				$(document).on('change', '.btn-file :file', function () {
					var input = $(this), numFiles = input.get(0).files ? input.get(0).files.length : 1, label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
					input.trigger('fileselect', [
						numFiles,
						label
					]);
				});
				
				$(document).ready(function () {
					$('.btn-file :file').on('fileselect', function (event, numFiles, label) {
						var input = $(this).parents('.input-group').find(':text'), log = numFiles > 1 ? numFiles + ' files selected' : label;
						if (input.length) {
							input.val(log);
						} else {
							if (log)
								alert(log);
						}
					});
				});
				
				$("[type='checkbox']").bootstrapSwitch({
					'size':'mini',
					'onText':'Yes',
					'offText':'No'
				});

				$('#cbSubscriberAgreement').bootstrapSwitch('destroy');
			
				$( ".datepicker" ).datepicker();
				
				function togglePermanantAddressVisibility(){
					if ($('#cbPermanantAddress').is(":checked"))
					{
						$('#divPermanantAddress').css('display','block');
					}
					else{
						$('#divPermanantAddress').css('display','none');
						
					}
				}
				
				$(".numericOnly").keypress(function (e) {
				    if (String.fromCharCode(e.keyCode).match(/[^0-9]/g)) return false;
				});
			
				$(".alphaNumericOnly").keypress(function (event) {
					var regex = new RegExp("^[a-zA-Z0-9\b]+$");
				    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				    if (!regex.test(key)) {
				       event.preventDefault();
				       return false;
				    }
				});
				$(".alphaOnly").keypress(function (e) {
					var regex = new RegExp("^[a-zA-Z\b\s]+$");
				    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				    if (!regex.test(key)) {
				       event.preventDefault();
				       return false;
				    }
				});
				$(".alphaWithSpace").keypress(function (e) {
					var regex = new RegExp("^[a-zA-Z\\-\\s]+$");
				    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				    if (!regex.test(key)) {
				       event.preventDefault();
				       return false;
				    }
				});
				
				$('.calender.YYYY').datetimepicker({
			    	viewMode: 'years',
			    	format: 'YYYY'
			    });
				$('.calender.MMDDYYYY').datetimepicker({
			    	//viewMode: 'yea',
					format: 'DD-MM-YYYY'
			    });
				$('.calender.DD-MM-YYYY').datetimepicker({
			    	//viewMode: 'yea',
			    	format: 'DD-MM-YYYY',
			    	minDate: moment()
			    });
				
				$('.calender.DD-MM-YYYY-P').datetimepicker({
			    	//viewMode: 'yea',
			    	format: 'DD-MM-YYYY',
			    	maxDate: moment(),
			    	widgetPositioning: {
		            	horizontal: 'right',
		            	vertical: 'bottom'
		        	}
			    });
				$('.calender').css('cursor','pointer');
				
				$('.txtRupee').autoNumeric('init',{vMin: '0',vMax: '99999999999'}); 
				
				$('#txtNetworth').val(moment().format('DD-MM-YYYY'));
				
				
				$("#btnAddQualification").click(function(){
			        $("#tblQualification").append('<tr><td> <input id="qQualification" type="text" class="form-control qQualification" placeholder="Please Enter Qualification"></td><td> <input id="qInstitute" type="text" class="form-control qInstitute" placeholder="Please Enter Institute"></td><td> <input id="qYear" type="text" class="form-control calender YYYY mandatory qYear" placeholder="Year" value=""></td><td> <input id="qQualificationId" type="text" class="form-control qQualificationId" placeholder="Please Enter Qualification ID"></td><td width=100> <img src="img/x.png" id="btnRemoveRow" width="30px"></td></tr>');
			
			        
			        $(function() {
						var selectBox = $("select").selectBoxIt();
					});
			        
			        
				    $('.qYear').datetimepicker({
				    	viewMode: 'years',
				    	format: 'YYYY'
				    });
			        
				});
				
				$("#tblQualification").on('click', '#btnRemoveRow', function(){
			        $(this).parent().parent().remove();
			    });
				
				autosize($('textarea'));
				
			</script>
		</div>
		
		<script type="text/javascript">
			clevertap.event.push("Advisor Registration Form Visited");
			console.log("Advisor Registration Form Visited");
		</script>
		
		<!-- Modules -->
        <script src="js/app.js"></script>
    
        <!-- Controllers -->
        <script src="js/controllers/AdvisorRegistration.js?v=1" charset="UTF-8"></script>
        <script src="js/directives/advisorRegistration.js" charset="UTF-8"></script>
    </body>
</html>
