//app.controller('RiskProfileChart', ['$scope', 'riskProfileQuestions', function($scope, riskProfileQuestions) {
//  riskProfileQuestions.success(function(data) {/
//	    $scope.riskoMeter = data.riskoMeter;
//	    $scope.investmentSplit = data.investmentSplit;
//  });
//}]);


app.controller('RiskProfileChart', ['$scope', function($scope) {
	$scope.page = {
			title: 'Risk profile output'
	};  
}]);
