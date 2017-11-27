app.directive('postLoginHeader', function() {
	return {
		restrict : 'E',
		templateUrl : 'js/directives/postLoginHeaderInv.htm'

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