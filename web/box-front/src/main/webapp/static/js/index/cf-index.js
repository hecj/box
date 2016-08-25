$(function() {
	/*众筹首页js效果*/
	$(".cf-index-reminder01").hover(function() { 
        $(".recommend-news01").stop().hide();
		$(".shade-01").fadeOut();
		$(".recommend-details01").fadeIn(100);
		$(".cf-index-reminder-img01").animate({"width":"500px","height":"200px","marginLeft":"0px","marginTop":"0px"},0)
		.animate({"width":"550px","height":"250px","marginLeft":"-25px","marginTop":"-25px"},300);
		}, function() {
		$(".recommend-news01").stop().show();
		$(".shade-01").fadeIn();
		$(".recommend-details01").fadeOut(100);
		$(".cf-index-reminder-img01").animate({"width":"550px","height":"250px","marginLeft":"-25px","marginTop":"-25px"},0)
		.animate({"width":"500px","height":"200px","marginLeft":"0px","marginTop":"0px"},300);
      
    });
	$(".recommend-details a").hover(function() {
		    $(this).css({
		    	background: 'none',
		    	color: '#fff'
		    });
	    }, function() {
	    	 $(this).css({
		    	background: '#fff',
		    	color: ' #9c9c9c'
		    });
	});
		
	/*众筹底部js效果*/
	$(".cf-index-row-item02").hover(function() {
		$(this).children(".shade-02").fadeOut(100);	
		$(this).children().children(".itemB-detils-wrap").fadeOut(100);
		$(this).children().children().children(".recommend-details02").fadeIn(100);
		$(this).children('img').animate({"width":"250px","height":"200px","marginLeft":"0px","marginTop":"0px"},0)
		.animate({"width":"300px","height":"250px","marginLeft":"-25px","marginTop":"-25px"},300);
	}, function() {
        $(this).children(".shade-02").fadeIn(100);
        $(this).children().children(".itemB-detils-wrap").fadeIn(100);
        $(this).children().children().children(".recommend-details02").fadeOut(100);
        $(this).children('img').animate({"width":"300px","height":"250px","marginLeft":"-25px","marginTop":"-25px"},0)
		.animate({"width":"250px","height":"200px","marginLeft":"0px","marginTop":"0px"},300);
	});
	$(".cf-index-row-item:eq(2)").hover(function() {
		$(this).children().children('.cf-index-item-title').children('h2').children('a').css({
			color: '#fff'
		});
	}, function() {
		$(this).children().children('.cf-index-item-title').children('h2').children('a').css({
			color: '#424242'
		});
	});
	/*表单验证*/

    $(".top-search input").keyup(function(event) {
    	$(this).siblings('span').hide();
    }).blur(function(event) {
        var seacherVal=$(".top-search input").val();
           seacherVal=$.trim(seacherVal)
        if (seacherVal =="" ) {
         $(".top-search input").siblings('span').show();

        }
 
    });
   $(".top-search input").siblings('span').click(function(event) {
   	$(".top-search input").focus();
   });
   /*意见反馈*/
   $(".feedbackTextArea").keyup(function(event) {
    	$('.user_msg01').stop().hide();
    }).blur(function(event) {
        var seacherVal=$(".feedbackTextArea").val();
 
        if (seacherVal =="" ) {
        $('.user_msg01').stop().show();
        }
 
    });   
    $(".feedback-txt").keyup(function(event) {
    	$('.user_msg02').stop().hide();
    }).blur(function(event) {
        var seacherVal=$(".feedback-txt").val();
        seacherVal=$.trim(seacherVal);
        if (seacherVal =="" ) {
        $('.user_msg02').stop().show();
        }
 
    });
    $(".feedbackTextArea").siblings('.user_msg01').click(function(event) {
    	 $(".feedbackTextArea").focus();
    	
    }); 
    $('.user_msg02').click(function(event) {
    	 $(".feedback-txt").focus();
    	
    });
    $(".address-02 .address-details:eq(0)").css({
    	"paddingTop":"10px"
    });
});