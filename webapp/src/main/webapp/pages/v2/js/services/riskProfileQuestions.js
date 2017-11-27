app.factory('riskProfileQuestions', ['$http', function($http) {
	return $http.get('json/riskProfileQuestions.json')
		.success(function(data) {
			return data; 
            }) 
            .error(function(err) { 
              return err; 
            }); 
}]);