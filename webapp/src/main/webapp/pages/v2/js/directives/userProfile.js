app.directive('userProfile', function() { 
  return { 
    restrict: 'E', 
    scope: { 
      info: '=' 
    }, 
    templateUrl: 'js/directives/userProfile.html' 
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
