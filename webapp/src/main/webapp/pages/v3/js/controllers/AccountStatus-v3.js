app.controller('AccountStatus', [ '$scope', function($scope) {
	//$scope.riskProfileQuestions = data;
	$scope.statusData = []	;
	$scope.name = 'Whirled';
	
	$scope.init = function (){
		initCarousel();
		$scope.data = {};
		//prefill user data
		$scope.initPrefill();
		$scope().apply();	
	} //init end
	
	$scope.fetchStatus = function() {
		console.log('in function');
		$.ajax({
			type : "POST",
			url : _gc_url_as_post_getStatus,
			data : null,
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				$scope.statusData = data;
				console.log($scope.statusData);
				$scope.processResponse();
			//$scope.$apply();
			},
			error : function(jqXHR, textError, errorThrown) {
				console.log('Error :');
				console.log(jqXHR);
				console.log(textError);
				console.log(errorThrown);
			},
			fail : function(jqXHR, textError, errorThrown) {
				console.log('Fail :');
				console.log(jqXHR);
				console.log(textError);
				console.log(errorThrown);
			}
		});
	}

	setTimeout(function() {
		$("#displayOnlyUserName").text($("#userFirstName").val());
		$scope.fetchStatus();
	}, 0);

	$scope.resendVerification = function() {
		$("#loading").show();
		showLoading();
		$.ajax({
			type : "GET",
			url : _gc_url_resendVerification,
			data : null,
			success : function(data) {
				console.log(data);
				if (data == "true"){
					$("#resendMailText").text("Resend Verification Mail Success !!");
					$('#modalSuccess').modal('show');
				}
				else {
					$("#resendMailText").text("Resend Verification Mail Failed !!");
					$('#modalError').modal('show');
				}
				hideLoading();	
				$("#loading").hide();
			},
			error : function(data) {
				console.log(data);
				hideLoading();
				$('#modalError').modal('show');
				$("#resendMailText").text("Resend Verification Mail Failed !!");
				$("#loading").hide();
			}
		});
	}

	$scope.processResponse = function() {
		angular.forEach($scope.statusData, function(statusObj) {

			if (statusObj.process_name == 'email_verified') {
				$scope.email_verified = 'email_verified';
				$scope.email_verified_status = statusObj.process_status;

			} else if (statusObj.process_name == 'online_detailsubmites') {
				$scope.online_detailsubmites = 'online_detailsubmites';
				$scope.online_detailsubmites_status = statusObj.process_status;

			} else if (statusObj.process_name == 'form_couriered_Client') {
				$scope.form_couriered_Client = 'form_couriered_Client';
				$scope.form_couriered_Client_status = statusObj.process_status;

			} else if (statusObj.process_name == 'form_received_client') {
				$scope.form_received_client = 'form_received_client';
				$scope.form_received_client_status = statusObj.process_status;

			} else if (statusObj.process_name == 'form_Validated') {
				$scope.form_Validated = 'form_Validated';
				$scope.form_Validated_status = statusObj.process_status;

			} else if (statusObj.process_name == 'accepted') {
				$scope.accepted = 'accepted';
				$scope.accepted_status = statusObj.process_status;

			} else if (statusObj.process_name == 'rejected') {
				$scope.rejected = 'rejected';
				$scope.rejected_status = statusObj.process_status;
				$scope.rejection_Resolved = statusObj.rejection_Resolved;
				
			} else if (statusObj.process_name == 'account_activated') {
				$scope.account_activated = 'account_activated';
				$scope.account_activated_status = statusObj.process_status;
				
			} else if (statusObj.process_name == 'uCC_created') {
				$scope.uCC_created = 'uCC_created';
				$scope.uCC_created_status = statusObj.process_status;
			}

		});
		$scope.online_detailsubmites_status = ($scope.online_detailsubmites_status === 'undefined' ? false :$scope.online_detailsubmites_status );
		$scope.formValid = (($scope.form_Validated_status == true && $scope.accepted_status == true) || ($scope.form_Validated_status == true && $scope.rejected_status== true && $scope.rejection_Resolved == true));
		$scope.finalStatus =  $scope.email_verified_status && $scope.online_detailsubmites_status && $scope.form_couriered_Client_status && $scope.form_received_client_status && $scope.account_activated_status && $scope.uCC_created_status && $scope.formValid; 
		$scope.$apply();
	}
} ]);