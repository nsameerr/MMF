//BaseURL
_gc_url_baseUrl = '/mmf';


//riskProfile
_gc_url_rp_post_riskProfile = _gc_url_baseUrl + '/RiskProfile'
_gc_url_rp_post_riskProfile_partial = _gc_url_baseUrl + '/RiskProfilePartial'
//_gc_url_rp_redirect_riskProfileoutput = _gc_url_baseUrl + '/faces/pages/v2/riskProfileOutput.jsp';
_gc_url_rp_redirect_riskProfileoutput = _gc_url_baseUrl + '/faces/pages/v3/riskProfileOutput.jsp';
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
//_gc_url_fpo_redirect_onRedirectIno_true = _gc_url_baseUrl + '/faces/pages/v2/userRegistration.jsp';
_gc_url_fpo_redirect_onRedirectIno_true = _gc_url_baseUrl + '/faces/pages/v3/userReg.jsp';
_gc_url_fpo_redirect_onRedirectIno_false = _gc_url_baseUrl + '/faces/pages/v3/investor-dashboard.jsp?faces-redirect=true';

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
//_gc_url_portfolio_search_ui = _gc_url_baseUrl + '/faces/pages/v2/portfolioSearch.jsp';
_gc_url_portfolio_search_ui = _gc_url_baseUrl + '/faces/pages/v3/advisor-portfolio-search.jsp';
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
_gc_url_pa_portfolio = _gc_url_baseUrl + "/paservice/onPortfolioSelect";
_gc_url_pa_init = _gc_url_baseUrl + "/paservice/getInitData";
_gc_url_pa_saveAllocatedPortolio = _gc_url_baseUrl + "/paservice/saveAllocatedPortfolio";


//url for data file upload
_gc_url_ir_fileUpload_id_url = _gc_url_baseUrl + '/service/mmfresource/uploadIdFileV3URL';
_gc_url_ir_fileUpload_cor_addr_url = _gc_url_baseUrl + '/service/mmfresource/uploadCorAddrFileV3URL';
_gc_url_ir_fileUpload_perm_addr_url = _gc_url_baseUrl + '/service/mmfresource/uploadPerAddrFileV3URL';
_gc_url_ir_fileUpload_bank_url = _gc_url_baseUrl + '/service/mmfresource/uploadBankFileV3URL';
_gc_url_ir_fileUpload_bank = _gc_url_baseUrl + '/service/mmfresource/uploadBankFileV2';

_gc_url_ir_post_finalSave_v3 = _gc_url_baseUrl + '/service/mmfresource/completeInvRegistrationV3';

//Public Captcha for New Login Page
_gc_url_CaptchaKey = _gc_url_baseUrl + '/service/mmfresource/getPublicCaptchaKey';

//Two-factor
_gc_url_twoFactor  = _gc_url_baseUrl + '/twoFactorLogin';

//Portfolio Search : Action Text
_gc_url_portfolioInvestorAction  = _gc_url_baseUrl + '/faces/investmentAdvProfile';
_gc_url_advisorProfileInfo = _gc_url_baseUrl + '/faces/userProfile';
_gc_url_investorProfileInfo = _gc_url_baseUrl + '/faces/investorProfile';
_gc_url_investorProfileUpdate = _gc_url_baseUrl + '/faces/updateUserProfile';

_gc_url_advisorDashboard = _gc_url_baseUrl + '/advisorDashboard';
_gc_url_editPortfolio = _gc_url_baseUrl + '/editPortfolioPage';
_gc_url_createEditPortfolio = _gc_url_baseUrl + '/createEditPortfolioPage';
_gc_url_clientInvestmentPerformance = _gc_url_baseUrl + '/clientInvestmentPerformancePage';
_gc_url_investorPage = _gc_url_baseUrl + '/selectInvestor';
_gc_url_clientInvestmentPerformance_init = _gc_url_baseUrl + '/getClientInvestmentPerformance';

_gc_url_getAssetBreakDown=_gc_url_baseUrl + '/getAssetBreakDown';
_gc_url_getAllAssets=_gc_url_baseUrl + '/getAllAssets';
_gc_url_viewPortfolioBreakDown = _gc_url_baseUrl + '/getViewPortfolioBreakDown';

_gc_url_investorDashboardMenu = _gc_url_baseUrl + '/service/mmfresource/sidebarMenuInv';
_gc_url_investorDashboard_init =  _gc_url_baseUrl + '/invDashboard/init';
//Header notifications
_gc_url_headerController_init = _gc_url_baseUrl+ '/headerController/init';
_gc_url_advisorNotification = _gc_url_baseUrl + '/headerController/advNotification';
_gc_url_investorNotification = _gc_url_baseUrl + '/headerController/invNotification';
_gc_url_investorTodaysOrder = _gc_url_baseUrl + '/headerController/todaysOrder';
_gc_url_investorTodaysTrade = _gc_url_baseUrl + '/headerController/todaysTrade';
_gc_url_investorPortfolioSummary = _gc_url_baseUrl + '/headerController/portfolioSummary';
_gc_url_investorRejectionReport = _gc_url_baseUrl + '/headerController/rejectionReport';
_gc_url_investorSyncBuyingPower = _gc_url_baseUrl + '/headerController/syncBuyingPower';
_gc_url_investorFundsTransferToGBNPP = _gc_url_baseUrl + '/headerController/fundsTransfer';

//Investor Sell/Redeem Portfolio
_gc_url_investorSellPortfolio_url = _gc_url_baseUrl + '/getAddRedeemFunds';
_gc_url_investorSell_recalculate_url = _gc_url_baseUrl + '/getFundsRecalculate';
_gc_url_investorSell_portfolioExecuted_url =_gc_url_baseUrl +'/getPortfolioExecuted';

//Create and Edit Portfolio
_gc_url_createPortfolioController_init = _gc_url_baseUrl + '/ceportfolio/init';
_gc_url_createPortfolio_loadTemplate = _gc_url_baseUrl + '/ceportfolio/loadTemplate';
_gc_url_createPortfolio_completeTicker =  _gc_url_baseUrl + '/ceportfolio/completeTicker';
_gc_url_createPortfolio_completeCMP =  _gc_url_baseUrl + '/ceportfolio/completeCMP';
_gc_url_createPortfolio_updateMarketPrice =  _gc_url_baseUrl + '/ceportfolio/updateMarketPrice';
_gc_url_createPortfolio_newAllocationChange =  _gc_url_baseUrl + '/ceportfolio/newAllocationChange';
_gc_url_createPortfolio_savePortfolio =  _gc_url_baseUrl + '/ceportfolio/savePortfolio';
_gc_url_createPortfolio_editPortfolio =  _gc_url_baseUrl + '/ceportfolio/editPortfolio';
_gc_url_createPortfolio_removeSecurity =  _gc_url_baseUrl + '/ceportfolio/removeSecurity';

//Password
_gc_url_getForgetPassword=_gc_url_baseUrl + '/getForgetPassword';
_gc_url_getChangePassword=_gc_url_baseUrl + '/getChangePassword';

//Rate Advisor
_gc_url_getAdvisorRating = _gc_url_baseUrl +'/getAdvisorRating';
_gc_url_setAdvisorRating = _gc_url_baseUrl +'/setAdvisorRating';

//Portfolio Details
_gc_url_portfolioDetails_init = _gc_url_baseUrl + '/portfolioDetails/init';
_gc_url_getSelectedAssetClass = _gc_url_baseUrl + '/getSelectedAssetClass';

// Investor Rebalance Portfolio
_gc_url_getRebalancePortfolioData = _gc_url_baseUrl + '/getRebalancePortfolioData';
_gc_url_executeRebalancePortfolio = _gc_url_baseUrl +'/executeRebalancePortfolio';

_gc_url_getBankDetailsUsingIFSC = _gc_url_baseUrl +'/getBankDetailsUsingIFSC';

//Admin Side Pages
_gc_url_getAdminNewUsersList = _gc_url_baseUrl +'/newUserInit';
_gc_url_getAdminSelectedUserData = _gc_url_baseUrl +'/adminUserSelect';