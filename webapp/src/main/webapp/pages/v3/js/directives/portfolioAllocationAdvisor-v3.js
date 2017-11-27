app.directive('advanceSettingsView', function() {
	return {
		restrict : 'E',
		templateUrl : 'js/directives/serviceContract/advanceSettingsView.htm'
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