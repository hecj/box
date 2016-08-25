$(document).ready(function(){
	/*账户名验证*/
	$(".name-input").on({
		focus:function(){
			var aname = this.value;
			$(this).css({"border-color":"#a1de00","color":"#000"});
			if (aname == "请输入注册手机号/邮箱" || aname == "" ) {
				this.value = "";
				$(this).css({"color":"#000"});
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-empty").hide();				
			};
		},
		blur:function(){
			var aname = this.value;			
			if (aname == "") {
				$(this).css({"border-color":"#f50","color":"#999"});
				$(this).parent().siblings(".pb-empty").show();
				$(this).parent().siblings(".pb-error").hide();
			} else {
				$(this).css({"border-color":"#d5d5d5"});
			};
		}
	});
	/*确认密码*/
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
	/*提交验证*/
	$(".ba-cont-sure").click(function(){
		var name_input = $(".name-input").val();
		var password_input = $(".password-input").val();
		if (name_input == "" || name_input == "请输入注册手机号/邮箱") {
			$(".name-input").parent().siblings(".pb-error").hide();
			$(".name-input").parent().siblings(".pb-empty").show();
			$(".name-input").css({"border-color":"#f00"});
		};
		if (password_input == "") {
			$(".password-input").parent().siblings(".pb-error0").hide();
			$(".password-input").parent().siblings(".pb-error1").hide();
			$(".password-input").parent().siblings(".pb-show").hide();
			$(".password-input").parent().siblings(".pb-empty").show();
			$(".password-input").css({"border-color":"#f00"});
		};
	});


})