$(document).ready(function(){
	/*账号验证*/
	$(".name-input").on({
		focus:function(){
			var aname = this.value;
			$(this).css({"border-color":"#a1de00","color":"#000"});
			if (aname == "请输入注册手机号/邮箱" || aname == "" ) {
				this.value = "";
				$(this).css({"color":"#000"});
				$(this).parent().siblings(".error").hide();
				$(this).parent().siblings(".empty").hide();				
			};
		},
		blur:function(){
			var aname = this.value;			
			if (aname == "") {
				$(this).css({"border-color":"#f50","color":"#999"});
				$(this).parent().siblings(".empty").show();
				$(this).parent().siblings(".error").hide();
			} else {
				$(this).css({"border-color":"#d5d5d5"});
			};
		}
	});
	/*验证码*/
	$(".captcha-input").on({
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
	/*验证码输入5个字符*/
	$(".captcha-input").bind("propertychange input blur",function(){
		var count=$(this).val().length;
		if (count>5) {
			var nr = $(this).val().substring(0,5);
			$(this).val(nr);
		};
	});
	/*提交验证*/
	$(".submit-btn").click(function(){
		var name_input = $(".name-input").val();
		var captcha_input = $(".captcha-input").val();
		if (name_input == "" || name_input == "请输入注册手机号/邮箱") {
			$(".name-input").parent().siblings(".error").hide();
			$(".name-input").parent().siblings(".empty").show();
			$(".name-input").css({"border-color":"#f00"});
		};
		if (captcha_input == "") {
			$(".captcha-input").parent().siblings(".show").hide();
			$(".captcha-input").parent().siblings(".error").hide();
			$(".captcha-input").parent().siblings(".empty").show();
			$(".captcha-input").css({"border-color":"#f00"});
		};
	})


})