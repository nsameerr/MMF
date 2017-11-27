app.directive('postLoginHeader', function() {
	return {
		restrict : 'E',
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