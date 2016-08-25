$(document).ready(function(){
	/*新登录密码*/
	$(".newpassword-input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			$(this).parent().siblings(".show").show();
			$(this).parent().siblings(".empty").hide();
			$(this).parent().siblings(".error0").hide();
			$(this).parent().siblings(".error1").hide();
		},
		blur:function(){
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".error0").hide();
				$(this).parent().siblings(".error1").hide();
				$(this).parent().siblings(".show").hide();
				$(this).parent().siblings(".empty").show();					
			} else {
				if ((alength < 6) || (alength > 16)) {
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".error0").show();
					$(this).parent().siblings(".error1").hide();
					$(this).parent().siblings(".show").hide();
					$(this).parent().siblings(".empty").hide();						
				} else{
					if (/^\d+$/.test(aname)) {
						$(this).css({"border-color":"#f50"});
						$(this).parent().siblings(".error0").hide();
						$(this).parent().siblings(".error1").show();
						$(this).parent().siblings(".show").hide();
						$(this).parent().siblings(".empty").hide();	
					} else {
						$(this).css({"border-color":"#d5d5d5"});
						$(this).parent().siblings(".error0").hide();
						$(this).parent().siblings(".error1").hide();
						$(this).parent().siblings(".show").hide();
						$(this).parent().siblings(".empty").hide();							
					};
				};				
			};
		}
	});	
	/*确定新密码*/
	$(".surepassword-input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			$(this).parent().siblings(".show").show();
			$(this).parent().siblings(".empty").hide();
			$(this).parent().siblings(".error").hide();
		},
		blur:function(){
			$(this).css({"border-color":"#f50"});
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".error").hide();
				$(this).parent().siblings(".show").hide();
				$(this).parent().siblings(".empty").show();					
			} else {
				if ($(".newpassword-input").value != aname ) {
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".error").show();
					$(this).parent().siblings(".show").hide();
					$(this).parent().siblings(".empty").hide();							
				} else {
					$(this).css({"border-color":"#d5d5d5"});
					$(this).parent().siblings(".error").show();
					$(this).parent().siblings(".show").hide();
					$(this).parent().siblings(".empty").hide();						
				};
			};
		}
	});
	/*提交验证*/
	$(".submit-btn").click(function(){
		var newpassword_input = $(".newpassword-input").val();
		var surepassword_input = $(".surepassword-input").val();
		if (newpassword_input == "") {
			$(".newpassword-input").parent().siblings(".error0").hide();
			$(".newpassword-input").parent().siblings(".error1").hide();
			$(".newpassword-input").parent().siblings(".show").hide();
			$(".newpassword-input").parent().siblings(".empty").show();
			$(".newpassword-input").css({"border-color":"#f00"});
		};
		if (surepassword_input == "") {
			$(".surepassword-input").parent().siblings(".show").hide();
			$(".surepassword-input").parent().siblings(".error").hide();
			$(".surepassword-input").parent().siblings(".empty").show();
			$(".surepassword-input").css({"border-color":"#f00"});
		};
		if (newpassword_input != surepassword_input) {
			$(".surepassword-input").parent().siblings(".show").hide();
			$(".surepassword-input").parent().siblings(".error").show();
			$(".surepassword-input").parent().siblings(".empty").hide();
			$(".surepassword-input").css({"border-color":"#f00"});			
		};
	});
})