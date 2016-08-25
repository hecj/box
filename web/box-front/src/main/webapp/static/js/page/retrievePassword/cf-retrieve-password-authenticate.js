$(document).ready(function(){
	/*手机邮箱选择*/
	$("select[class='way-select']").change(function(){
		var way_select = jQuery("select[class=way-select]").val();
		if (way_select == "1") {
			$(".s-email").css({"display":"none"});
			$(".s-phone").css({"display":"block"});
		};
		if (way_select == "2") {
			$(".s-phone").css({"display":"none"});
			$(".s-email").css({"display":"block"});
		};		
	});
	/*倒计时*/
	$(".settimeout0").click(function(){
		$(this).hide().siblings(".settimeout1").show();
		/*$(this).siblings(".double").removeClass("settimeout0").addClass("settimeout1");*/
		var jump = document.getElementById("settimeout"); 
		var timer = 60;
		fun();
		function fun(){
			timer--;
			jump.innerHTML = timer;
			if (timer>0) {
				setTimeout(fun,1000);
			} else{
				$(".settimeout0").show().siblings(".settimeout1").hide();
				jump.innerHTML = "";
			};
		}
	});
	/*验证码*/
	$(".phone-captcha-input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			$(this).parent().siblings(".show").show();
			$(this).parent().siblings(".error").hide();
			$(this).parent().siblings(".empty").hide();
		},
		blur:function(){
			var aname = this.value;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".show").hide();
				$(this).parent().siblings(".error").hide();
				$(this).parent().siblings(".empty").show();				
			} else {
				$(this).css({"border-color":"#d5d5d5"});
				$(this).parent().siblings(".show").hide();
				$(this).parent().siblings(".error").hide();
				$(this).parent().siblings(".empty").hide();					
			};
		}		
	});
	/*验证码输入6个字符*/
	$(".phone-captcha-input").bind("propertychange input blur",function(){
		var count=$(this).val().length;
		if (count>6) {
			var nr = $(this).val().substring(0,6);
			$(this).val(nr);
		};
	});
	/*提交验证*/
	$(".cellphone-submit-btn").click(function(){
		var phone_captcha_input = $(".phone-captcha-input").val();
		if (phone_captcha_input == "") {
			$(".phone-captcha-input").parent().siblings(".show").hide();
			$(".phone-captcha-input").parent().siblings(".error").hide();
			$(".phone-captcha-input").parent().siblings(".empty").show();
			$(".phone-captcha-input").css({"border-color":"#f00"});
		};
	});
})