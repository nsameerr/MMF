app.directive('serviceContractAdvisorView', function() {
	return {
		restrict : 'E',
		templateUrl : 'js/directives/serviceContract/advisory-service-contract.htm'

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