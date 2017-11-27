/*document ready start*/
$(document).ready(function(){

	/* page name constant */
	var USER_PROFILE_EDIT_PAGE = "investor-portfolio-edit.jsp";
	var FINANCIAL_PLANNER_PAGE = "userProfile.jsp";
	var FINANCIAL_PLANNER_OUTPUT_PAGE = "financialPlannerOutput.jsp";
	var INVESTOR_DASHBOARD_PAGE = "investor-dashboard.jsp";
	var ASSET_BREAKDOWN_PAGE = "assetBreakDown.jsp";
	var INVESTOR_SELL_PORTFOLIO_PAGE = "investor-sell-portfolio.jsp";
	var RATE_ADVISOR_PAGE = "rateAdvisor.jsp";
	var PORTFOLIO_DETAILS_PAGE = "portfolioDetails.jsp";
	
	//Portfolio Rebalance Page
	var INVESTOR_REBALANCE_PORTFOLIO_PAGE ="investor-rebalance-portfolio.jsp";
	
	/* ADVISOR REGISTRATION PAGES */
	var ADVISOR_REGISTRATION_PAGE = "advisorReg.jsp";
	var ADVISOR_REGISTRATION_SUCCESS_PAGE = "advisorRegSuccess.jsp";
	
	/* ADVISOR DASHBOARD RELATED PAGES */
	var ADVISOR_DASHBOARD_PAGE = "advisorDashboard.jsp";
	var ADVISOR_CLIENT_PERFORMANCE_PAGE = "advisorClientPerformance.jsp";
	var ADVISOR_PORTFOLIO_PERFORMANCE_PAGE = "advisorPortfolioPerformance.jsp";
	var ADVISOR_CLIENT_RELATION_STATUS_PAGE = "advisorClientRelationshipStatus.jsp";
	var ADVISOR_PROFILE_UPDATE_PAGE = "advisorProfile.jsp";
	var CLIENT_INVESTMENT_PERFORMANCE_PAGE ="clientInvestmentPerformance.jsp";
	/* ADVISOR CONTRACT PAGES */
	var INVESTOR_PROFILE_VIEW_PAGE = "investorProfileView.jsp";
	var ADVISOR_PORTFOLIO_ALLOCATION_PAGE = "portfolioAllocation.jsp";
	var ADVISOR_CONTRACT_PAGE = "advisoryServiceContract.jsp";
	var ADVISOR_CREATE_PORTFOLIO_PAGE = "createEditPortfolio.jsp";
	
	/* menu array */
	var MENU_DEFAULT = ['#home'];
	var MENU_USER_PROFILE_EDIT_PAGE_SRC_UPW = ['#search'];
	var MENU_USER_PROFILE_EDIT_PAGE_SRC_DASHBOARD = ['#home'];
	var MENU_FINANCIAL_PLANNER_PAGE_DASHBOARD = ['#home'];
	var MENU_FINANCIAL_PLANNER_PAGE_DEFAULT = [];
	var MENU_INVESTOR_DASHBOARD = ['#home','#search','#my_port'];
	var MENU_ADVISOR_DASHBOARD = ['#home','#search','#my_port'];
	setTimeout(function(){ 
        //$("#displayOnlyUserName").text($("#userFirstName").val());
		var page = window.location.href;
		
		if (page.indexOf(USER_PROFILE_EDIT_PAGE) >= 0){
			sidebarMenusForUserProfileEditPage();
		}else if((page.indexOf(FINANCIAL_PLANNER_PAGE) >= 0)){
			sidebarMenusForFinancialPlannerPage();
		}else if((page.indexOf(FINANCIAL_PLANNER_OUTPUT_PAGE) >= 0)){
			sidebarMenusForFinancialPlannerPage();
		}else if(page.indexOf(INVESTOR_DASHBOARD_PAGE) >= 0 ||
				page.indexOf(ASSET_BREAKDOWN_PAGE) >= 0 ||
				page.indexOf(INVESTOR_SELL_PORTFOLIO_PAGE) >=0 ||
				page.indexOf(RATE_ADVISOR_PAGE) >=0 ||
				page.indexOf(PORTFOLIO_DETAILS_PAGE)>=0 ||
				page.indexOf(INVESTOR_SELL_PORTFOLIO_PAGE) >=0 ||
				page.indexOf(INVESTOR_REBALANCE_PORTFOLIO_PAGE) >=0){
			sidebarMenusForInvestorDashboardPage();
			$('#investorSetting').show();
		}else if(page.indexOf(ADVISOR_DASHBOARD_PAGE) >= 0 ||
				page.indexOf(ADVISOR_CLIENT_PERFORMANCE_PAGE) >= 0 ||
				page.indexOf(ADVISOR_PORTFOLIO_PERFORMANCE_PAGE) >= 0 ||
				page.indexOf(ADVISOR_CLIENT_RELATION_STATUS_PAGE) >= 0 ||
				page.indexOf(INVESTOR_PROFILE_VIEW_PAGE) >= 0 ||
				page.indexOf(ADVISOR_PORTFOLIO_ALLOCATION_PAGE) >= 0 ||
				page.indexOf(ADVISOR_CONTRACT_PAGE) >= 0 ||
				page.indexOf(ADVISOR_CREATE_PORTFOLIO_PAGE) >=0 ||
				page.indexOf(ADVISOR_PROFILE_UPDATE_PAGE) >=0 ||
				page.indexOf(CLIENT_INVESTMENT_PERFORMANCE_PAGE) >=0 ){
			sidebarMenusForAdvisorDashboardPage();
			//Show Settings List
			$('#advisorSetting').show();	
		}else if((page.indexOf(ADVISOR_REGISTRATION_PAGE) >= 0)){
			showMenus(MENU_FINANCIAL_PLANNER_PAGE_DEFAULT);	//No menus
		}else if((page.indexOf(ADVISOR_REGISTRATION_SUCCESS_PAGE) >= 0)){
			showMenus(MENU_FINANCIAL_PLANNER_PAGE_DEFAULT);	//No menus
		}else {
			showMenus(MENU_DEFAULT);
		}
    },100);
	
	function sidebarMenusForFinancialPlannerPage(){
		var url = window.location.href;
    	var urlpart =  url.split("viewPlanner=");
    	var src = urlpart[1];
    	if(typeof src === "undefined"){
    		showMenus(MENU_FINANCIAL_PLANNER_PAGE_DEFAULT);	//No menus 
    	} else {
    		showMenus(MENU_FINANCIAL_PLANNER_PAGE_DASHBOARD);	//Only Dashboard
    	}
	}
	
	/*start*/
	function sidebarMenusForUserProfileEditPage(){
		var url = window.location.href;
    	var urlpart =  url.split("src=");
    	var src = urlpart[1];
    	if(typeof src === "undefined"){
    		//window.location.href = "/faces/pages/investordashboard.xhtml?faces-redirect=true";
    		showMenus(MENU_USER_PROFILE_EDIT_PAGE_SRC_DASHBOARD);
    	} else {
    		//window.location.href = "/faces/pages/select_robo_advisor.xhtml?faces-redirect=true";
    		showMenus(MENU_USER_PROFILE_EDIT_PAGE_SRC_UPW);
    	}
	}
	/*end*/
		
	function showMenus(menus){
		$(menus[0]).addClass('active');
		for(var i = 0;i<menus.length;i++){
			$(menus[i]).css('display','block');
		}
	}
	
	function sidebarMenusForInvestorDashboardPage(){
		showMenus(MENU_INVESTOR_DASHBOARD);
	}
	
	function sidebarMenusForAdvisorDashboardPage(){
		showMenus(MENU_ADVISOR_DASHBOARD);
	}
});
/*document ready end*/
