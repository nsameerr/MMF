//BaseURL
_gc_url_baseUrl = '/mmf';


//riskProfile
_gc_url_rp_post_riskProfile = _gc_url_baseUrl + '/RiskProfile'
_gc_url_rp_post_riskProfile_partial = _gc_url_baseUrl + '/RiskProfilePartial'
_gc_url_rp_redirect_riskProfileoutput = _gc_url_baseUrl + '/faces/pages/v2/riskProfileOutput.jsp';

//riskProfileOutput
_gc_url_rp_get_riskProfileOutput = _gc_url_baseUrl + '/RiskProfileOutput';

//Financial Planner Output
//FinancialPlannerOutput.... editRiskScore, editSavingsRate, editAllocationToMMF, editRetirmentAge, editAssumptions, editLifeGoals,  

//GET
_gc_url_fpo_get_assetExpenditureChart = _gc_url_baseUrl + '/FinancialPlannerOutput';

_gc_url_fpo_post_editRiskScore = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_post_editSavingsRate = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_post_editAllocationToMMF = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_post_editRetirmentAge = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_post_editAssumptions = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_post_editLifeGoals = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_post_saveAndSubmit = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_up_get_userDetails = _gc_url_baseUrl + '/FinancialPlanner';
_gc_url_fpo_redirect_saveYourResponse = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_redirect_saveAndSubmit = _gc_url_baseUrl + '/FinancialPlannerOutput';
_gc_url_fpo_redirect_onRedirectIno_true = _gc_url_baseUrl + '/faces/pages/v3/userReg.jsp';
_gc_url_fpo_redirect_onRedirectIno_false = _gc_url_baseUrl + '/faces/pages/investordashboard.xhtml?faces-redirect=true';

_gc_url_as_post_saveAndSubmitFp = _gc_url_baseUrl + '/FinancialPlannerOutput';

//Userprofile
_gc_up_post_submit = _gc_url_baseUrl +'/FinancialPlanner'
	
//Home

_gc_url_home_post_investorLogin = _gc_url_baseUrl + "/faces/userLogin";
_gc_url_home_post_investorRegistration = _gc_url_baseUrl + "/faces/register";


_gc_url_home_post_validateData = _gc_url_baseUrl + "/faces/validateRegInfo";

//AdvisorProfile
_gc_url_ap_getAdvisorProfile = "json/advisorprofile/advisorProfileJson.json"
_gc_url_ap_getAdvisorPerformance = "json/advisorprofile/advisorPerformanceJson.json"
_gc_url_ap_getAdvisorTestimonials = "json/advisorprofile/advisorCustTestimonails.json"
_gc_url_ap_getAdvisorPublications = "json/advisorprofile/advisorPublications.json"
_gc_url_ap_saveAdvisorProfile = ""
_gc_url_ap_saveAdvisorPublication = ""

//Investor Profile
_gc_url_ap_getInvestorProfile =	_gc_url_baseUrl + "/faces/userProfile";
		
//Investor Registration
_gc_url_ir_post_autoSave = _gc_url_baseUrl + '/service/mmfresource/autoSaveInvCompleteService';
_gc_url_ir_post_finalSave = _gc_url_baseUrl + '/service/mmfresource/completeInvRegistration';

//LoginServletV2
_gc_url_home_post_userLogin = _gc_url_baseUrl + "/faces/userLogin";
//ValidateRegistrationData
_gc_url_home_post_validateData = _gc_url_baseUrl + "/faces/validateRegInfo";
//RegistrationServlet
_gc_url_home_post_userRegistration = _gc_url_baseUrl + "/faces/register";

_gc_url_ir_get_userDetails = _gc_url_baseUrl + '/service/mmfresource/fetchUserData';
_gc_url_ir_fileUpload_pan = _gc_url_baseUrl + '/service/mmfresource/uploadPanFileV2';
_gc_url_ir_fileUpload_sebi = _gc_url_baseUrl + '/service/mmfresource/uploadSebiFileV2';
_gc_url_ir_fileUpload_coresAddrs = _gc_url_baseUrl +'/service/mmfresource/uploadCoresAddrsFileV2';
_gc_url_ir_fileUpload_permAdrs = _gc_url_baseUrl + '/service/mmfresource/uploadPermAdrsFileV2';
_gc_url_ir_fileUpload_docEvd = _gc_url_baseUrl + '/service/mmfresource/uploadDocEvdFileV2';
_gc_url_fileUpload_adv_pic = _gc_url_baseUrl + '/service/mmfresource/uploadAdvPicV2;';
_gc_url_fileUpload_inv_pic = _gc_url_baseUrl + '/service/mmfresource/uploadInvPicV2';

//advisor Registration
_gc_url_ar_get_advisorDetails = _gc_url_baseUrl + '/service/mmfresource/fetchAdvUserData';
_gc_url_ar_post_autosave = _gc_url_baseUrl + '/service/mmfresource/autoSaveAdvCompleteService';
_gc_url_ar_post_finalsave = _gc_url_baseUrl + '/service/mmfresource/completeAdvRegistration';

//AccountStatus
_gc_url_as_post_getStatus = _gc_url_baseUrl + '/faces/accountStatus';
_gc_url_resendVerification = _gc_url_baseUrl + '/faces/resendVMail';

//Logout
_gc_url_logout = _gc_url_baseUrl + '/faces/logoutUser';

//Registration view pdf
_gc_url_reg_pdf = _gc_url_baseUrl + '/resources/registration/'

//fetch reg Id
_gc_url_fetch_reg_id = _gc_url_baseUrl + '/service/mmfresource/fetchRegId';

//Registration after pdf
_gc_url_reg_pdf_post_view = _gc_url_baseUrl + '/service/mmfresource/completeInvRegistrationAfterShowPdf';

//getBankList
_gc_url_banklist = _gc_url_baseUrl + '/service/mmfresource/getBankList';
//getCityList
_gc_url_citylist = _gc_url_baseUrl + '/service/mmfresource/getCityList';
//getStateList
_gc_url_statelist = _gc_url_baseUrl + '/service/mmfresource/getStateList';

//forgotPassword
_gc_url_forgotpassword = _gc_url_baseUrl + '/faces/pages/forgot_password.xhtml'; 
//portfolio search
_gc_url_portfolioSearch = _gc_url_baseUrl + '/portfolioSearchV2';
//preview image
_gc_url_previewImage = _gc_url_baseUrl + '/faces/images?src=';
//Selected Portfolio 
_gc_url_selectedPortfolio = _gc_url_baseUrl + '/service/mmfresource/getSelectedPortfolio';
//LinkedIn Profile
_gc_url_linkedIn_basicProfile = _gc_url_baseUrl + "/faces/userProfileLinkedIn?viewData=true";
//Advisor Portfolios List
_gc_url_advisorPortfolios = _gc_url_baseUrl + '/service/mmfresource/getAdvisorPortfolios';
//portfolio Search UI
_gc_url_portfolio_search_ui = _gc_url_baseUrl + '/faces/pages/v2/portfolioSearch.jsp';
// fp status
_gc_url_fpStatus = _gc_url_baseUrl + '/service/mmfresource/getUserFpStatus';
// rp status
_gc_url_rpStatus = _gc_url_baseUrl + '/service/mmfresource/getUserRpStatus';

_gc_url_update_fp_using_rp = _gc_url_baseUrl + '/service/mmfresource/updateFpUsingRp';
//Advisory Service Contract
_gc_url_AUMPayableFrequencyList = _gc_url_baseUrl + '/service/mmfresource/getAUMPayableFrequencyList';
_gc_url_MgmtFeeVariableAmountList = _gc_url_baseUrl + '/service/mmfresource/getMgmtFeeVariableAmountList';
_gc_url_ContractDurationFreqList = _gc_url_baseUrl + '/service/mmfresource/getContractDurationFreqList';
_gc_url_DurationList = _gc_url_baseUrl + '/service/mmfresource/getDurationList';
_gc_url_CustomerAdvisorMapping = _gc_url_baseUrl + '/paservice/getCustomerAdvisorMapping';
_gc_url_ServiceContract = _gc_url_baseUrl + '/servicecontract';

_gc_url_getTotalFunds = _gc_url_baseUrl + '/service/mmfresource/getUserTotalFunds';

//portfolio allocation screen
_gc_url_pa_portfolioChange = _gc_url_baseUrl + "/paservice/onPortfolioSelectChange";
_gc_url_pa_init = _gc_url_baseUrl + "/paservice/getInitData";
_gc_url_pa_saveAllocatedPortolio = _gc_url_baseUrl + "/paservice/saveAllocatedPortfolio";

_gc_url_ir_fileUpload_pan_url = _gc_url_baseUrl + '/service/mmfresource/uploadPanFileV2URL';
