app.directive('selectAdvisor', function() {
	return {
		restrict : 'E',
//		scope : {
//			info : '='
//		},
		templateUrl : 'js/directives/advisor-select.htm'

	};
});

app.directive('mmfNewHeader', function() {
	return {
		restrict : 'E',
//		scope : {
//			info : '='
//		},
//		templateUrl : 'js/directives/mmfHeaderPostLogin.htm'
		templateUrl : 'js/directives/postLoginHeaderInv.htm'
	};
});

app.directive('mmfNewFooter', function() {
	return {
		restrict : 'E',
//		scope : {
//			info : '='
//		},
		templateUrl : 'js/directives/mmfFooterPostLogin.htm'

	};
});
