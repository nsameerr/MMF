app.directive('signin', function() { 
  return { 
    restrict: 'E', 
    scope: { 
      info: '=' 
    }, 
    templateUrl: 'js/directives/signin.html' 
  }; 
});


app.directive('mmfHeaderHome', function() {
	return {
		restrict : 'E',
		scope : {
			info : '='
		},
		templateUrl : 'js/directives/mmfHeaderHome.html'

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
