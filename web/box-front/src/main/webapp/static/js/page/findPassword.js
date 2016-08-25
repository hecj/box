	function myReload() {
		var verify = document.getElementById('createImage');
		verify.setAttribute('src', '/image?' + Math.random());
	}

	function validate(val) {
		var flag = false;
		$.ajax({
			type : "GET",
			async : false,
			url : "/image/verify?rancode=" + val,
			success : function(msg) {
				if (msg.code == 200) {
					flag = true;
				} else {
					flag = false;
				}
			}
		})
		return flag;
	} 
	
	/*提交验证*/
	$(".submit-btn").click(function(){
		var name_input = $(".name-input").val();
		var captcha_input = $(".captcha-input").val();
		
		var type = isPhoneOrEmail(name_input);
		var isTrue = validate(captcha_input);
		
		if (name_input == "" || name_input == "请输入注册手机号/邮箱") {
			$(".name-input").parent().siblings(".error").hide();
			$(".name-input").parent().siblings(".empty").show();
			$(".name-input").css({"border-color":"#f00"});
		}else if(type==2){
			if(name_input==""){
				$(".name-input").parent().siblings(".error").hide();
				$(".name-input").parent().siblings(".empty").show();
			}else{
				$(".name-input").parent().siblings(".error").show();
				$(".name-input").parent().siblings(".empty").hide();
			}
			$(".name-input").css({"border-color":"#f00"});
		};
		
		if (captcha_input == "" ) {
			$(".captcha-input").parent().siblings(".show").hide();
			$(".captcha-input").parent().siblings(".error").hide();
			$(".captcha-input").parent().siblings(".empty").show();
			$(".captcha-input").css({"border-color":"#f00"});
		}else if(!isTrue){
			$(".captcha-input").parent().siblings(".show").hide();
			$(".captcha-input").parent().siblings(".error").show();
			$(".captcha-input").parent().siblings(".empty").hide();
			$(".captcha-input").css({"border-color":"#f00"});
		};
		
		
		if(isTrue&&type!=2){
			//两个都正确
			$.ajax({
				type:"POST",
				url:"verifyPhoneOrEmail",
				async:false,
				data:"phoneOrEmail="+name_input+"&type="+type,
				success:function(msg){
					if(msg.code==200){
						location="/resetPassword?phoneAndEmail="+name_input+"&type="+type;
						return;
					}else{
						$(".name-input").css({"border-color":"#f50","color":"#999"});
						$(".name-input").parent().siblings(".show").hide();
						$(".name-input").parent().siblings(".empty").hide();
						$(".name-input").parent().siblings(".error").show();
						return;
					}
				}
			})
			return;
		}else if(isTrue&&type==2){
			//格式错误||未输入
			if($(".name-input").val()==""||$(".name-input").val()=="请输入注册手机号/邮箱"){
				$(".name-input").parent().siblings(".error").hide();
				$(".name-input").parent().siblings(".empty").show();
			}else{
				$(".name-input").parent().siblings(".error").show();
				$(".name-input").parent().siblings(".empty").hide();
			}
			$(".name-input").css({"border-color":"#f00"});
			return;
		}else if(!isTrue&&type!=2){
			//验证码校验错误
			if($(".captcha-input").val()==""){
				$(".captcha-input").parent().siblings(".show").hide();
				$(".captcha-input").parent().siblings(".error").hide();
				$(".captcha-input").parent().siblings(".empty").show();
			}else{
				$(".captcha-input").parent().siblings(".show").hide();
				$(".captcha-input").parent().siblings(".error").show();
				$(".captcha-input").parent().siblings(".empty").hide();
			}
			$(".captcha-input").css({"border-color":"#f00"});
			return;
		}else{
			//两个都错误
			//验证码
			if (captcha_input == "" ) {
				//未填
				$(".captcha-input").parent().siblings(".show").hide();
				$(".captcha-input").parent().siblings(".error").hide();
				$(".captcha-input").parent().siblings(".empty").show();
				$(".captcha-input").css({"border-color":"#f00"});
			}else {
				$(".captcha-input").parent().siblings(".show").hide();
				$(".captcha-input").parent().siblings(".error").show();
				$(".captcha-input").parent().siblings(".empty").hide();
				$(".captcha-input").css({"border-color":"#f00"});
			};
			
			//用户名
			if (name_input == "" || name_input == "请输入注册手机号/邮箱") {
				$(".name-input").parent().siblings(".error").hide();
				$(".name-input").parent().siblings(".empty").show();
				$(".name-input").css({"border-color":"#f00"});
			}else if(type==2){
				if(name_input==""){
					$(".name-input").parent().siblings(".error").hide();
					$(".name-input").parent().siblings(".empty").show();
				}else{
					$(".name-input").parent().siblings(".error").show();
					$(".name-input").parent().siblings(".empty").hide();
				}
				$(".name-input").css({"border-color":"#f00"});
			};
			
		}
	
	})
			
	
	/*判断是邮箱还是*/
	function isPhoneOrEmail(phoneAndEmail){
		var regPhone = /^0?(13[0-9]|15[012356789]|18[02356789]|14[57]|17[07])[0-9]{8}$/;
		var regEmail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		if(regPhone.test(phoneAndEmail)){
			return 0;
		}else if(regEmail.test(phoneAndEmail)){
			return 1;
		}else{
			return 2;
		}
	}
	$(document).ready(function(){
		/*生成图片验证码 */
		myReload();
		/*账号验证*/
		$(".name-input").on({
			focus:function(){
				var aname = this.value;
				$(this).css({"border-color":"#a1de00","color":"#000"});
				this.value = "";
				$(this).css({"color":"#000"});
				$(this).parent().siblings(".error").hide();
				$(this).parent().siblings(".empty").hide();				
				
			},
			blur:function(){
				var aname = this.value;			
				if (aname == "" || aname == "请输入注册手机号/邮箱" ) {
					$(this).css({"border-color":"#f50","color":"#999"});
					$(this).parent().siblings(".empty").show();
					$(this).parent().siblings(".error").hide();
				} else if(isPhoneOrEmail(aname)==2){
					//判断格式
					$(this).css({"border-color":"#f50","color":"#999"});
					$(this).parent().siblings(".empty").hide();
					$(this).parent().siblings(".error").show();
				}else{
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
				};
			}		
		});
		/*验证码输入4个字符*/
		$(".captcha-input").bind("propertychange input blur",function(){
			var count=$(this).val().length;
			if (count>4) {
				var nr = $(this).val().substring(0,4);
				$(this).val(nr);
			};
		});
	})