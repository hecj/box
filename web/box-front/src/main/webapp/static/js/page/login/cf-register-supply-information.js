$(document).ready(function(){
	/*手机号验证*/
	$(".phone-input").on({
		focus:function(){
			var aname = this.value;
			$(this).css({"border-color":"#a1de00","color":"#000"});
			$(this).parent().siblings(".pb-error").hide();
			$(this).parent().siblings(".pb-empty").hide();			
		},
		blur:function(){
			var aname = this.value;			
			if (aname == "") {
				$(this).css({"border-color":"#f50","color":"#999"});
				$(this).parent().siblings(".pb-empty").show();
				$(this).parent().siblings(".pb-error").hide();
				return false;
			} else {
					if(!(/^1[3,4,5,7,8]\d{9}$/).test(aname) || aname.length != 11 ){
						$(this).css({"border-color":"#f50"});
						$(this).parent().siblings(".pb-empty").hide();
						$(this).parent().siblings(".pb-error").show();
						return false;
					}else{
						$(this).css({"border-color":"#d5d5d5"});
						$(this).parent().siblings(".pb-empty").hide();
						$(this).parent().siblings(".pb-error").hide();
					}
			};
		}
	});
	/*倒计时*/
	$(".settimeout0").click(function(){
		$(this).hide().siblings(".settimeout1").show();
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
			$(this).parent().siblings(".pb-show").show();
			$(this).parent().siblings(".pb-error").hide();
			$(this).parent().siblings(".pb-empty").hide();
		},
		blur:function(){
			var aname = this.value;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-empty").show();				
			} else {
				$(this).css({"border-color":"#d5d5d5"});
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-empty").hide();					
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
	/*密码*/
	$(".password-input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			$(this).parent().siblings(".pb-show").show();
			$(this).parent().siblings(".pb-empty").hide();
			$(this).parent().siblings(".pb-error0").hide();
			$(this).parent().siblings(".pb-error1").hide();
		},
		blur:function(){
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".pb-error0").hide();
				$(this).parent().siblings(".pb-error1").hide();
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-empty").show();					
			} else {
				if ((alength < 6) || (alength > 16)) {
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".pb-error0").show();
					$(this).parent().siblings(".pb-error1").hide();
					$(this).parent().siblings(".pb-show").hide();
					$(this).parent().siblings(".pb-empty").hide();						
				} else{
					if (/^\d+$/.test(aname)) {
						$(this).css({"border-color":"#f50"});
						$(this).parent().siblings(".pb-error0").hide();
						$(this).parent().siblings(".pb-error1").show();
						$(this).parent().siblings(".pb-show").hide();
						$(this).parent().siblings(".pb-empty").hide();	
					} else {
						$(this).css({"border-color":"#d5d5d5"});
						$(this).parent().siblings(".pb-error0").hide();
						$(this).parent().siblings(".pb-error1").hide();
						$(this).parent().siblings(".pb-show").hide();
						$(this).parent().siblings(".pb-empty").hide();							
					};
				};				
			};
		}
	});	
	/*确认密码*/
	$(".surepassword-input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			var aname = this.value;
			$(this).parent().siblings(".pb-show").show();
			$(this).parent().siblings(".pb-empty").hide();
			$(this).parent().siblings(".pb-error").hide();
		},
		blur:function(){
			$(this).css({"border-color":"#f50"});
			var aname = this.value;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-empty").show();					
			} else {
				if ($(".password-input").value != $(this).value ) {
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".pb-error").show();
					$(this).parent().siblings(".pb-show").hide();
					$(this).parent().siblings(".pb-empty").hide();							
				} else {
					$(this).css({"border-color":"#d5d5d5"});
					$(this).parent().siblings(".pb-error").hide();
					$(this).parent().siblings(".pb-show").hide();
					$(this).parent().siblings(".pb-empty").hide();						
				};
			};
		}
	});
	/*提交验证*/
	$(".rsics-btn").click(function(){
		var phone_input = $(".phone-input").val();
		var phone_captcha_input = $(".phone-captcha-input").val();
		var password_input = $(".password-input").val();
		var surepassword_input = $(".surepassword-input").val();
		var checkbox_input = document.getElementById("checkbox-input")
		if (phone_input == "") {
			$(".phone-input").parent().siblings(".pb-error").hide();
			$(".phone-input").parent().siblings(".pb-empty").show();
			$(".phone-input").css({"border-color":"#f00"});
		};
		if (phone_captcha_input == "") {
			$(".phone-captcha-input").parent().siblings(".pb-show").hide();
			$(".phone-captcha-input").parent().siblings(".pb-error").hide();
			$(".phone-captcha-input").parent().siblings(".pb-empty").show();
			$(".phone-captcha-input").css({"border-color":"#f00"});
		};
		if (password_input == "") {
			$(".password-input").parent().siblings(".pb-error0").hide();
			$(".password-input").parent().siblings(".pb-error1").hide();
			$(".password-input").parent().siblings(".pb-show").hide();
			$(".password-input").parent().siblings(".pb-empty").show();
			$(".password-input").css({"border-color":"#f00"});
		};
		if (surepassword_input == "") {
			$(".surepassword-input").parent().siblings(".pb-show").hide();
			$(".surepassword-input").parent().siblings(".pb-error").hide();
			$(".surepassword-input").parent().siblings(".pb-empty").show();
			$(".surepassword-input").css({"border-color":"#f00"});
		};
		if (password_input != surepassword_input) {
			$(".surepassword-input").parent().siblings(".pb-show").hide();
			$(".surepassword-input").parent().siblings(".pb-error").show();
			$(".surepassword-input").parent().siblings(".pb-empty").hide();
			$(".surepassword-input").css({"border-color":"#f00"});			
		};
		if (!checkbox_input.checked) {
			alert(1);
		};
	})	

})