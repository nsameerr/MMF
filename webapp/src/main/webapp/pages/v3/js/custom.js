// Written By Developer 1

/* checkbox start  */
$(".checkbox").click(function(){
    $(this).toggleClass('checked')
});		
/* checkbox start  */

/* Rupees format start here */

var format = function(num){
	var x=num;
x=x.toString();
var lastThree = x.substring(x.length-3);
var otherNumbers = x.substring(0,x.length-3);
if(otherNumbers != '')
    lastThree = ',' + lastThree;
return res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree;
};
	
/* Rupees format end here */

// Written by Developer 2 

<!-- Hamber Toggle Menu -->

$(document).ready(function(){
	$('#open_menu_nav').click(function(){
		$('#hamber_menu_section').animate({
			left: '0px'
		}, 1000);
		$('#open_menu_nav').hide();
		$('#close_menu_nav').show();
	});
	$('#close_menu_nav').click(function(){
		$('#hamber_menu_section').animate({
			left: '-92px'
		}, 1000);
		$('#open_menu_nav').show();
		$('#close_menu_nav').hide();
	});
});

<!-- End -->

<!-- O/P Page Life Goal Script -->

$(document).ready(function(){
	$( "#estim_amt_slider" ).slider({
      range: "min",
      value:500000,
      min:0,
      max:1000000,
      slide: function( event, ui ) {
        $( "#estim_amount" ).val( "" + ui.value );
      }
    });
    $( "#estim_amount" ).val( "" + $( "#estim_amt_slider" ).slider( "value" ) );
	$('#delete_row').click(function(){
		$('#added_goal_row').remove();
	});
	$('#add_row').click(function(){
	 	var $i = Math.floor((Math.random() * 100000) + 1);
	 	$('.goals_table').append('<div id="added_goal_row'+$i+'" class="goals_row body"><div class="column column1"><select class="goal_select"><option>Internal Vacation</option><option>Domestic Vacation</option></select></div><div class="column column2"><select class="goal_select"><option>Once in two year</option><option>Every Month</option></select></div><div class="column column3"><select class="goal_select"><option>2019</option><option>2017</option></select></div><div class="column column4"><div class="edit_graph_row"><div class="edit_graph_data"><div class="age1"><label for="estim_amount'+$i+'"><i class="fa fa-inr"></i></label><input class="estim_amt" type="text" id="estim_amount'+$i+'" readonly style="border:0;"><div id="estim_amt_slider'+$i+'" class="edit_graph_bar" ></div></div></div></div></div><div class="column column5"><div class="switch_tog"><div class="switch tab_controls"><ul class="nav nav-tabs"><li class="active"><a data-toggle="tab">Savings</a></li><li><a data-toggle="tab">Loan</a></li></ul></div></div><div class="ed_del"><span id="delete_row'+$i+'" class="delete"><a><i class="fa fa-times" aria-hidden="true"></i></a></span></div></div></div>');
		$( "#estim_amt_slider"+$i+"" ).slider({
      		range: "min",
      		value:500000,
      		min:0,
      		max:1000000,
      		slide: function( event, ui ) {
				$( "#estim_amount"+$i+"" ).val( "" + ui.value );
      		}
    	});
    	$( "#estim_amount"+$i+"" ).val( "" + $( "#estim_amt_slider"+$i+"" ).slider( "value" ) );
		$('#delete_row'+$i+'').click(function(){
			$('#added_goal_row'+$i+'').remove();
		});
	});
});

<!-- End -->

<!-- Popover Box Script -->

$(document).ready(function(){
    $('[data-toggle="popover"]').popover(); 
});

<!-- End -->
<!-- Portfolio Table Slider Script -->

$( function() {
	$( "#port_tb_slide1" ).slider({
      range: "min",
      value:25,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#port_tb1" ).val( "" + ui.value );
      }
    });
    $( "#port_tb1" ).val( "" + $( "#port_tb_slide1" ).slider( "value" ) );
	$( "#port_tb_slide2" ).slider({
      range: "min",
      value:15,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#port_tb2" ).val( "" + ui.value );
      }
    });
    $( "#port_tb2" ).val( "" + $( "#port_tb_slide2" ).slider( "value" ) );
	$( "#port_tb_slide3" ).slider({
      range: "min",
      value:25,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#port_tb3" ).val( "" + ui.value );
      }
    });
    $( "#port_tb3" ).val( "" + $( "#port_tb_slide3" ).slider( "value" ) );
	$( "#port_tb_slide4" ).slider({
      range: "min",
      value:14,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#port_tb4" ).val( "" + ui.value );
      }
    });
    $( "#port_tb4" ).val( "" + $( "#port_tb_slide4" ).slider( "value" ) );
	$( "#port_tb_slide5" ).slider({
      range: "min",
      value:13,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#port_tb5" ).val( "" + ui.value );
      }
    });
    $( "#port_tb5" ).val( "" + $( "#port_tb_slide5" ).slider( "value" ) );
	$( "#port_tb_slide6" ).slider({
      range: "min",
      value:40,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#port_tb6" ).val( "" + ui.value );
      }
    });
    $( "#port_tb6" ).val( "" + $( "#port_tb_slide6" ).slider( "value" ) );
});

<!-- End -->

<!-- Portfolio Table Slider Read Only Script -->

$( function() {
	$( "#ro_port_tb_slide1" ).slider({
      range: "min",
      value:25,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#port_tb1" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb1" ).val( "" + $( "#ro_port_tb_slide1" ).slider( "value" ) );
	$( "#ro_port_tb_slide1" ).off();
	$( "#ro_port_tb_slide2" ).slider({
      range: "min",
      value:15,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#ro_port_tb2" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb2" ).val( "" + $( "#ro_port_tb_slide2" ).slider( "value" ) );
	$( "#ro_port_tb_slide2" ).off();
	$( "#ro_port_tb_slide3" ).slider({
      range: "min",
      value:25,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#ro_port_tb3" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb3" ).val( "" + $( "#ro_port_tb_slide3" ).slider( "value" ) );
	$( "#ro_port_tb_slide3" ).off();
	$( "#ro_port_tb_slide4" ).slider({
      range: "min",
      value:14,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#ro_port_tb4" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb4" ).val( "" + $( "#ro_port_tb_slide4" ).slider( "value" ) );
	$( "#ro_port_tb_slide4" ).off();
	$( "#ro_port_tb_slide5" ).slider({
      range: "min",
      value:13,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#ro_port_tb5" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb5" ).val( "" + $( "#ro_port_tb_slide5" ).slider( "value" ) );
	$( "#ro_port_tb_slide5" ).off();
	$( "#ro_port_tb_slide6" ).slider({
      range: "min",
      value:40,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#ro_port_tb6" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb6" ).val( "" + $( "#ro_port_tb_slide6" ).slider( "value" ) );
	$( "#ro_port_tb_slide6" ).off();
	
	$( "#ro_port_tb_slide7" ).slider({
      range: "min",
      value:49,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#ro_port_tb7" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb7" ).val( "" + $( "#ro_port_tb_slide7" ).slider( "value" ) );
	$( "#ro_port_tb_slide7" ).off();
	
	$( "#ro_port_tb_slide8" ).slider({
      range: "min",
      value:55,
      min:1,
      max:100,
      slide: function( event, ui ) {
        $( "#ro_port_tb8" ).val( "" + ui.value );
      }
    });
    $( "#ro_port_tb8" ).val( "" + $( "#ro_port_tb_slide8" ).slider( "value" ) );
	$( "#ro_port_tb_slide8" ).off();
	
});

<!-- End -->

<!-- Page Scroll Down -->

$(document).ready(function(){
	$(".scroll-to-bottom").click(function() {
		$('html,body').animate({
			scrollTop: $("footer").offset().top},'slow');
		$(".scroll-to-bottom").fadeOut()
	});    
	$(window).scroll(function(){
		$(this).scrollTop()>100?$(".scroll-to-top").fadeIn():$(".scroll-to-top").fadeOut()}),
		$(".scroll-to-top").click(function(){
			$(".scroll-to-bottom").fadeIn();
			return $("html, body").animate({scrollTop:0},1000),!1});
			$(window).load(function(){
				$(".scroll-to-bottom").fadeIn();
			});
		$(window).scroll(function(){
		$(this).scrollTop()>100?$(".scroll-to-bottom").fadeOut():$(".scroll-to-bottom").fadeIn()
	});
});

<!-- End -->




