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
	$scope.portfolioSearchFunc = function()
	{
		if($scope.advisor=="human")
			{
				console.log("Human advisor selected")
				window.location.href=_gc_url_baseUrl +"/faces/pages/v3/advisor-portfolio-search.jsp?robo=false";
			}
		else if($scope.advisor=="robo")
			{
				console.log("Robo advisor selected")
				window.location.href=_gc_url_baseUrl +"/faces/pages/v3/advisor-portfolio-search.jsp?robo=true";
			}
	}
	
	
	setTimeout(function(){
		$("#displayOnlyUserName").text($("#userFirstName").val());		
	},100);
	
	
	//notification popups
	 $scope.notificationCount;
	 $scope.investorNotificationspoup;
	 $scope.investorNotifications;
	 $scope.init = function(){
			console.log('hi');
			$scope.initHeaderController();
			//$scope.initInvDashboard();
			
		}
	 
	 /* init header controller*/
		$scope.initHeaderController = function(){
			$.ajax({
					type: 'POST',
					url : _gc_url_headerController_init,//"http://localhost:8080/headerController/init",
					success: function(rdata){
						rdata =  JSON.parse(rdata);
						console.log(rdata);
						$scope.investorNotifications = rdata.investorNotifications;
						$scope.investorNotificationspoup = rdata.investorNotificationspoup;
						$scope.notificationCount =  rdata.investorNotificationspoup.length;
						$scope.chartData = rdata.chartData;
	    				$scope.chartDataHC =  rdata.chartDataHC;
	    				console.log($scope.investorNotificationspoup);
					    console.log($scope.investorNotifications);
					    console.log($scope.chartData);
					    console.log($scope.chartDataHC);
						$scope.$apply();
					},
					error: function(rdata){
						console.error(rdata);
					}
				});
		}
		
		
		/* CTA on click of notification */
		$scope.notificationCTA = function(param){
			alert(param);
			var url1 = _gc_url_investorNotification;//'http://localhost:8080/headerController/invNotification';						
				$.post(
					url1,
					{
						notificationIndex : param
					},function(rdata) {
						console.log(rdata);
						
											
					}
				);
				
		}
}]);