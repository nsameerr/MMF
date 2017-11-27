app.directive('riskProfileOutput', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
		templateUrl : 'js/directives/riskProfileOutput.html'

	};
});

app.directive('mmfHeader', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
		templateUrl : 'js/directives/mmfHeader.html'

	};
});

app.directive('mmfFooter', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
		templateUrl : 'js/directives/mmfFooter.html'

	};
});
