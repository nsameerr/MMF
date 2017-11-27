app.directive('mmfHeaderPostLogin', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
//		templateUrl : 'js/directives/mmfHeaderPostLogin.htm'
		templateUrl : 'js/directives/postLoginHeader.htm'	
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