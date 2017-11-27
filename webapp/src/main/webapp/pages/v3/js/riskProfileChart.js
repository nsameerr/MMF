//Risk Profile Chart
function drawRiskProfileChart(riskScore,riskScoreClass, riskTypeClass){
	//riskScore : should be an integer value : e.g., 43
	//riskScoreClass : should be class name where risk graph should be plotted
	//riskTypeClass : should be class name where risk type (e.g. Moderate ) should be displayed
	var riskGroup = ['Conservative','Moderately Conservative','Moderate','Moderately Agressive','Agressive'];
	var riskColor = ['#6bac18','#d3ac21','#f7900c;','#f76900;','#c75602;'];
	$('.'+riskScoreClass).kumaGauge({
	// $('.gauge_section').kumaGauge({
        value : 0,
        gaugeWidth :15,
        radius : 100                
    });
    $('.'+riskScoreClass).kumaGauge('update', {
    // $('.gauge_section').kumaGauge('update', {
        value : riskScore //43
    });

    $('.'+riskScoreClass).kumaGauge({
    // $('.gauge_section').kumaGauge({
        value : Math.floor((Math.random() * 99) + 1),
        fill : 'transparent',
        gaugeBackground : '#1E4147',
        gaugeWidth : 10,
        showNeedle : false,
        label : {
            display : true ,
            left : 'Min',
            right : 'Max',
            fontFamily : 'Helvetica',
            fontColor : '#1E4147',
            fontSize : '0',
            fontWeight : 'bold'
        }
    });
   
    if(riskScore > 100){

    }
    else if(riskScore > 80){
    	$('.edit_inputgraphtxt').text(riskGroup[4]).css({'color':riskColor[4]});
    	// #c75602;
    }else if(riskScore > 60){
    	$('.edit_inputgraphtxt').text(riskGroup[3]).css({'color':riskColor[3]});
    	// #f76900;
    }else if(riskScore > 40){
    	$('.edit_inputgraphtxt').text(riskGroup[2]).css({'color':riskColor[2]});
    	// #f7900c;
    }else if(riskScore > 20){
    	$('.edit_inputgraphtxt').text(riskGroup[1]).css({'color':riskColor[1]});
    	// #d3ac21
    }else if(riskScore > 0){
    	$('.edit_inputgraphtxt').text(riskGroup[0]).css({'color':riskColor[0]});
    	// #6bac18
    }else{

    }
}

//Risk Profile Div Sliders : Advisor View
function drawRiskProfileSlidersAdvisorView(savingsRate,allocationToMmf,retirementAge,isEditable){
	$( "#inv_slider-range-min" ).slider({
      range: "min",
      value:savingsRate,//22,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#inv_age" ).val( "" + ui.value );
      }
    });
    $( "#inv_age" ).val( "" + $( "#inv_slider-range-min" ).slider( "value" ) );
	

	$( "#inv_slider-range-min2" ).slider({
      range: "min",
      value:allocationToMmf,//46,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#inv_age2" ).val( "" + ui.value );
      }
    });
    $( "#inv_age2" ).val( "" + $( "#inv_slider-range-min2" ).slider( "value" ) );
	
    $( "#inv_slider-range-min3" ).slider({
      range: "min",
      value:retirementAge,//60,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#inv_age3" ).val( "" + ui.value );
      }
    });
    $( "#inv_age3" ).val( "" + $( "#inv_slider-range-min3" ).slider( "value" ) );
	
	if(!isEditable){
		$( "#inv_slider-range-min" ).off();
    	$( "#inv_slider-range-min2" ).off();
		$( "#inv_slider-range-min3" ).off();		
	}

}

//Risk Profile Div Sliders : Investor View
function drawRiskProfileSliders(savingsRate,allocationToMmf,retirementAge,isEditable){
	$( "#slider-range-min" ).slider({
      range: "min",
      value:savingsRate,//22,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#age" ).val( "" + ui.value );
      }
    });
    $( "#age" ).val( "" + $( "#slider-range-min" ).slider( "value" ) );

    $( "#slider-range-min2" ).slider({
      range: "min",
      value:allocationToMmf,//46,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#age2" ).val( "" + ui.value );
      }
    });
    $( "#age2" ).val( "" + $( "#slider-range-min2" ).slider( "value" ) );

    $( "#slider-range-min3" ).slider({
      range: "min",
      value:retirementAge,//60,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#age3" ).val( "" + ui.value );
      }
    });
    $( "#age3" ).val( "" + $( "#slider-range-min3" ).slider( "value" ) );
    if(!isEditable){
		$( "#slider-range-min" ).off();
    	$( "#slider-range-min2" ).off();
		$( "#slider-range-min3" ).off();		
	}
}