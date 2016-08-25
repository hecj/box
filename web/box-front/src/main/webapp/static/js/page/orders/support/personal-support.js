$(function() {
	$(".support-del").click(function(event) {
		$(this).parent().parent().siblings(".support-del-con").show();
		$(this).parent().parent().siblings(".support-bgc").show();
		$(this).parent().siblings().children(".support-return").unbind("mouseenter mouseleave"); 
		$(this).parent().siblings().children().children().children(".support-booking").unbind("mouseenter mouseleave");
		$(this).parent().siblings().children().children().children(".support-boking-progress").unbind("mouseenter mouseleave");
	});
	$(".support-return").hover(function() {
		$(this).css({"color":"#828282"});
	   $(this).parent().parent().siblings('.support-back-con').show();
		 $(this).parent().parent().siblings(".support-bgc").show();
	},function() {
			$(this).css({"color":"#8ab825"});
			 $(this).parent().parent().siblings(".support-bgc").hide();
			 $(this).parent().parent().siblings('.support-back-con').hide();
	});
	$(".support-booking").hover(function() {
		$(this).css({"color":"#888888"});
		$(this).parent().parent().parent().parent().siblings(".support-bgc").show();
		 $(this).parent().parent().parent().parent().siblings('.support-address').show();
	}, function() {
		$(this).css({"color":"#8ab825"});
		$(this).parent().parent().parent().parent().siblings(".support-bgc").hide();
		 $(this).parent().parent().parent().parent().siblings('.support-address').hide();
	});
	$(".support-boking-progress").hover(function() {
		$(this).css({"color":"#888888"});
			 $(this).parent().parent().parent().parent().siblings(".support-bgc").show();
		 $(this).parent().parent().parent().parent().siblings('.support-flow-con').show();
	}, function() {
		$(this).css({"color":"#ffc600"});
			 $(this).parent().parent().parent().parent().siblings(".support-bgc").hide();
		 $(this).parent().parent().parent().parent().siblings('.support-flow-con').hide();
	});
	
});