app.directive('advisorPortfolio', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
		templateUrl : 'js/directives/advisorPortfolio.html'

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
