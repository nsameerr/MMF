app.directive('userProfile1', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-planner.htm' 
  }; 
});

app.directive('userProfile2', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-planner-age.htm'     
  }; 
});

app.directive('userProfile3', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-monthly-income.htm'     
  }; 
});

app.directive('userProfile4', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-savings.htm' 
  }; 
});

app.directive('userProfile5', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-combined-outstanding-loans.htm' 
  }; 
});

app.directive('userProfile6', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-combined-total-networth.htm' 
  }; 
});

app.directive('userProfile7', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-add-insurance-details.htm' 
  }; 
});

app.directive('userProfile8', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-life-goals.htm' 
  }; 
});

app.directive('userProfile9', function() { 
  return { 
    restrict: 'E', 
    templateUrl: 'js/directives/financialPlanner/financial-planner-returns-risk.htm' 
  }; 
});

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