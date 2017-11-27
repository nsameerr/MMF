$(document).ready(function(){
        $("#open_toggle").click(function(){
			$("#menu_column").animate({
				left: "0"
			}, 1000);
			$(".toggle_menu_block").css("right","-10px");
			$("#open_toggle").hide();
			$("#close_toggle").show();
		});
		$("#close_toggle").click(function(){
			$("#menu_column").animate({
				left: "-298px"
			}, 1000);
			$(".toggle_menu_block").css("right","70px");
			$(".toggle_menu_block_static").css("right","-10px");
			$("#close_toggle").hide();
			$("#open_toggle").show();
		});
		
		
		});

/* checkbox start  */
$(".checkbox").click(function(){
    $(this).toggleClass('checked')
});		
/* checkbox start  */