app.controller('AccountStatus', ['$scope', function($scope) {
	//$scope.riskProfileQuestions = data;
	$scope.statusData = []	;
	$scope.name = 'Whirled';
	
	$scope.fetchStatus = function(){
		console.log('in function');
		$.ajax({
			  type: "POST",
			  url: _gc_url_as_post_getStatus,
			  data: null,
			  dataType: 'json',
			  contentType:"application/json; charset=utf-8",
			  success: function(data){
				  $scope.statusData = data;
				  $scope.$apply();
			  },
			  error : function(jqXHR,textError,errorThrown){
				  console.log('Error :');
				  console.log(jqXHR);
				  console.log(textError);
				  console.log(errorThrown);
			  },
			  fail : function(jqXHR,textError,errorThrown){
				  console.log('Fail :');
				  console.log(jqXHR);
				  console.log(textError);
				  console.log(errorThrown);
			  }
			});
	}
	
	setTimeout(function(){
		$("#displayOnlyUserName").text($("#userFirstName").val());
		$scope.fetchStatus();
	},0);
	
	resendVerification = function(){
		$("#loading").show();
		$.ajax({
			  type: "GET",
			  url: _gc_url_resendVerification,
			  data: null,
			  success: function(data){
				  console.log(data);
				  if(data == "true")
					  $("#resendMailText").text("Resend Verification Mail Success !!");
				  else
					  $("#resendMailText").text("Resend Verification Mail Failed !!");
				  $("#loading").hide();
			  },
			  error: function(data){
				  console.log(data);
				  $("#resendMailText").text("Resend Verification Mail Failed !!");
				  $("#loading").hide();
			  }
			});
	}
}]);

