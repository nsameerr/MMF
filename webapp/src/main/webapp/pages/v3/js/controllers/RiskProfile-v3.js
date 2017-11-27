app.controller('RiskProfile', ['$scope', function($scope) {

                 $scope.errorTitle= "Error";
                 $scope.errorBody="Please answer the following questions";

                var totalSlides = 10;
                var arrVisitedSlids = [];
                var sliderValue =0;
                var qids = [1,2,3,4,5,6,7,8,9,10];
            
                setTimeout(function(){ 
                displayUserName = function (){
                $("#displayOnlyUserName").text($("#userFirstName").val());
                 }
                displayUserName();
        
                }, 1000);

              /*next button*/
                $('.btnNext').on('click',function(){
                    goToNextPage();
                

                });

	        /* Previous btn */
	    $('.btnPrev').on('click',function(){
	 		currentSlide = $("#myCarousel").find('.active').index();
	 		if(currentSlide == 1){
                	$('.btnNext').show();
                	$('.btnPrev').hide();
                     $('.btnSubmit').hide();
                } else if(currentSlide == 9){
                	$('.btnNext').show();
                	$('.btnPrev').show();
                    $('.btnSubmit').hide();
                } else {
                	$('.btnNext').show();
                	$('.btnPrev').show();
                     $('.btnSubmit').hide();
                }

	 		if (currentSlide == 1) {

	 			$('#myCarousel').carousel(0);
	 			currentSlide = 0;
	 		} else if (currentSlide == 2) {
	 			
	 			$('#myCarousel').carousel(1);
	 			currentSlide = 1;
	 		} else if (currentSlide == 3) {
	 			
	 			$('#myCarousel').carousel(2);
	 			currentSlide = 2;
	 		} else if (currentSlide == 4) {
	 			
	 			$('#myCarousel').carousel(3);
	 			currentSlide = 3;
	 		} else if (currentSlide == 5) {
	 			$('#myCarousel').carousel(4);
	 			currentSlide = 4;
	 		} else if (currentSlide == 6) {
	 			
	 			$('#myCarousel').carousel(5);
	 			currentSlide = 5;
	 		} else if (currentSlide == 7) {
	 			
	 			$('#myCarousel').carousel(6);
	 			currentSlide = 6;
	 		}
	 		else if (currentSlide == 8) {
	 			
	 			$('#myCarousel').carousel(7);
	 			currentSlide = 7;
	 		}
	 		else if (currentSlide == 9) {
	 			 
	 			$('#myCarousel').carousel(8);
	 			currentSlide = 8;
	 			
	 		}

	 	});


        /*Submit btn logic*/
            $('.save_ed').on('click',function(){
            currentSlide = $("#myCarousel").find('.active').index();
            if (currentSlide == 9) {
                
            $('.save_ed').show();
                
            }else{
                $('.save_ed').hide();
            }


            });
          


        $('.btnSubmit').on('click', function(event){
            //console.log('onclick');
            showLoading();
            unansweredQuestions = "";
            console.log(totalSlides);
            var postData = "";
            for(i=0;i<totalSlides;i++)
            {
                if($('input[name=Q'+i+']').attr('type')=='radio')
                {
                    if($('input[name=Q'+i+']:checked').val() > -1)
                    {
                        postData = postData + '{"questionId" : '+qids[i]+', "answerId" : '+$('input[name=Q'+i+']:checked').val()+'},'
                                            }
                    else
                    {
                        if(unansweredQuestions == "")
                        {
                            unansweredQuestions = "#" + (i+1) 
                        }
                        else
                        {
                            unansweredQuestions = unansweredQuestions + ", #" + (i+1);
                        }
                    }
                }
                else if(arrVisitedSlids[i])
                {
                    //$('#spc'+i).addClass('done');
                }
                else{
                    if(unansweredQuestions == "")
                    {
                        unansweredQuestions = "#" + (i+1) 
                    }
                    else
                    {
                        unansweredQuestions = unansweredQuestions + ", #" + (i+1);
                    }
                }
            }
            postData = postData ;//+ '{"questionId" : 20, "answerId" : '+sliderValue+'}'
            postData = '[' +postData + ']';
            console.log(postData);
            postData = postData.replace(',]',']');
            console.log(postData);
            if(unansweredQuestions=="")
            {
                console.log(postData);
                clevertap.event.push("Investor Submitted Risk Profile");
                var url = window.location.href;
                var urlpart =  url.split("rpsrc=");
                var rpsrc = urlpart[1];
                if (rpsrc.startsWith("ps") || rpsrc.startsWith("rpw") || rpsrc.startsWith("upw")){
                    // riskprofile src is portfolio search make partial entry of riskprofile 2/5 records
                    $.ajax({
                          type: "POST",
                          url: _gc_url_rp_post_riskProfile_partial,
                          data: postData,
                          //dataType: 'json',
                          contentType:"application/json; charset=utf-8",
                          success: function(){
                              if (rpsrc.startsWith("upw") || rpsrc.startsWith("rpw"))
                                  window.location.href = _gc_url_rp_redirect_riskProfileoutput + "?next=upw";
                              else
                                  window.location.href = _gc_url_rp_redirect_riskProfileoutput;
                          }
                        });
                } else if (rpsrc == "ac") {
                    // riskprofile src is advisory contract make complete entry of riskprofile 5/5 records
                    $.ajax({
                          type: "POST",
                          url: _gc_url_rp_post_riskProfile,
                          data: postData,
                          //dataType: 'json',
                          contentType:"application/json; charset=utf-8",
                          success: function(){
                              window.location.href = _gc_url_rp_redirect_riskProfileoutput; 
                          }
                        });
                }
                
            }
            else
            {   hideLoading();
                $('#modalErrorTitle')[0].innerHTML = "Error"
                $('#modalErrorBody')[0].innerHTML = "Please answer the following questions <br> " +unansweredQuestions;
                $('#modalShowError').modal('show');
            }
        });

        $(document).ready(function(){
        	  var url = window.location.href;
              var urlpart =  url.split("rpsrc=");
              var rpsrc = urlpart[1];
              if(rpsrc.startsWith("rpw")){
            	  $('#rpInfoModal').modal('show');
              }
        });	

        goToNextPage = function() {
            currentSlide = $("#myCarousel").find('.active').index();
                if (currentSlide == 0) {

                        $('#myCarousel').carousel(1);                        
                        currentSlide = 1;                                            
                } else if (currentSlide == 1) {                     
                        $('#myCarousel').carousel(2);
                        currentSlide = 2;
                    
                } else if (currentSlide == 2) {
                    
                        $('#myCarousel').carousel(3);
                        currentSlide = 3;
                    
                } else if (currentSlide == 3) {
                    
                        $('#myCarousel').carousel(4);
                        currentSlide = 4;
                    
                } else if (currentSlide == 4) {
                    
                        $('#myCarousel').carousel(5);
                        currentSlide = 5;   
                    
                } else if (currentSlide == 5) {
                    
                        $('#myCarousel').carousel(6);
                        currentSlide = 6;   
                    
                } else if (currentSlide == 6) {
                    
                        $('#myCarousel').carousel(7);
                        currentSlide = 7;
                    
                }
                else if (currentSlide == 7) {
                    
                        $('#myCarousel').carousel(8);
                        currentSlide = 8;
                    
                }
                else if (currentSlide == 8) {
                        $('#myCarousel').carousel(9);
                        currentSlide = 9;
                }

                if(currentSlide == 0){
                    $('.btnNext').show();
                    $('.btnPrev').hide();
                    $('.btnSubmit').hide();
                } else if(currentSlide == 9){
                    $('.btnNext').hide();
                    $('.btnPrev').show();
                     $('.btnSubmit').show();
                } else {
                    $('.btnNext').show();
                    $('.btnPrev').show();
                     $('.btnSubmit').hide();
                }
        }
}]);