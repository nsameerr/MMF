app.directive('investorProfileView', function() {
	return {
		restrict : 'E',
		templateUrl : 'js/directives/serviceContract/investor-portfolio.htm'

	};
});

app.directive('mmfHeaderPostLoginAdvisor', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
		templateUrl : 'js/directives/mmfHeaderPostLoginAdvisor.htm'

	};
});

app.directive('mmfFooterPostLogin', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
		templateUrl : 'js/directives/mmfFooterPostLogin.htm'

	};
});

app.directive('financialPlannerTableView', function() {
	return {
		restrict : 'E',
		templateUrl : 'js/directives/financialPlanner/financialPlannerTableView.htm'
	};
});