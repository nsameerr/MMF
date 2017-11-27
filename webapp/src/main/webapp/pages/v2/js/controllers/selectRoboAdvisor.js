app.controller('SelectRoboAdvisor', ['$scope', function($scope) {
	
	$('#advisorRobo').click(function(event){
		if(confirm("You have selected Robo Advisor. Click OK to continue.")){
			console.log('Robo Advisor Selected');
			showLoading();
			return true;
		}else{
			event.preventDefault();
			return false;
		}	
	});
	
	setTimeout(function(){
		$("#displayOnlyUserName").text($("#userFirstName").val());		
	},0);
}]);