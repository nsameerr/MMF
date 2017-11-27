
app.controller('AdvisorPortfolio', ['$scope', function($scope) {
	$scope.page = {
			title: 'Advisor Portfolio'
	};
	
	$scope.advisor = {
			profile : '',
			portfolios : '',
			testimonials : '',
			publications : ''
	}
	
	
	
	
	$scope.init = function(){
		$( '.js-float-label-wrapper' ).FloatLabel();
		$scope.getAdvisorProfile();
		$scope.getAdvisorPerformance();
		$scope.getAdvisorTestimonials();
		$scope.getAdvisorPublications();
	}
	
	$scope.getAdvisorProfile = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorProfile,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					console.log(rData);
					$scope.advisor.profile = rData;
					$scope.$apply();
					$('#divRatings').raty({ 'readOnly': true,score: $scope.advisor.profile.userRating});
				}
				catch(e){
					$('#divContent').css("display","block");
					$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	
	$scope.getAdvisorPerformance = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorPerformance,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					console.log(rData);
					$scope.advisor.portfolios = rData.portfolios;
					console.log($scope.advisor.portfolios);
					$scope.$apply();
				}
				catch(e){
					//$('#divContent').css("display","block");
					//$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	

	$scope.getAdvisorTestimonials = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorTestimonials,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					console.log(rData);
					$scope.advisor.testimonials = rData.custTestimonials;
					console.log($scope.advisor.testimonials);
					$scope.$apply();
				}
				catch(e){
					//$('#divContent').css("display","block");
					//$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	
	$scope.getAdvisorPublications = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_getAdvisorPublications,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				try{
					$scope.advisor.publications = rData.publications;
					console.log($scope.advisor.publications);
					$scope.$apply();
				}
				catch(e){
					//$('#divContent').css("display","block");
					//$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
	}
	
	$scope.addPreviousOrg = function(){
		$scope.advisor.profile.previousOrg.push({$$hashKey:"",fromDate:"",orgName:"",title:"",toDate:""});
		$scope.$apply();
	}
	
	$scope.addQualification = function(){
		$scope.advisor.profile.qualificationDetails.push({degree:"",institute:"",year:""});
		$scope.$apply();
	}
	
	$scope.addPublications = function(){
		$scope.advisor.publications.push({name:"",link:""});
		$scope.$apply();
	}
	
	$scope.showEditProfile = function(){
		
		$('#divProfileReadOnly').css('display','none');
		$('#divProfileEdit').css('display','block');
	}
	
	$scope.showEditPublications = function(){
		
		$('#divPublicationsReadOnly').css('display','none');
		$('#divPublicationsEdit').css('display','block');
	}
	

	$scope.saveAdvisorProfile = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_saveAdvisorProfile,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				alert('Profile Saved');
				$('#divProfileReadOnly').css('display','block');
				$('#divProfileEdit').css('display','none');
			},
			error: function(){
				alert('Save failed');
				location.reload();
			},
			fail: function(){
				alert('Save failed');
				location.reload();
			}
		});
	}
	
	$scope.saveAdvisorPublications = function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ap_saveAdvisorPublication,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(rData){
				alert('Profile Saved');
				$('#divPublicationReadOnly').css('display','block');
				$('#divPublicationEdit').css('display','none');
			},
			error: function(){
				alert('Save failed');
				$('#divPublicationReadOnly').css('display','block');
				$('#divPublicationEdit').css('display','none');
			},
			fail: function(){
				alert('Save failed');
				$('#divPublicationReadOnly').css('display','block');
				$('#divPublicationEdit').css('display','none');
			}
		});
	}
	
	
	
}]);