var model = function(){
		var x = {"hi" : "wassup"};
};


angular.module('MyApp', [])
    .controller('MainController', ['$scope', function($scope) {

        $scope.rows = ['Row 1', 'Row 2'];

        $scope.counter = 3;

        $scope.addRow = function() {

            $scope.rows.push('Row ' + $scope.counter);
            $scope.counter++;
        }
        $scope.removeRow = function(ind) {
            $scope.rows.splice(ind, 1);
        }
    }]);

console.log(model.x);