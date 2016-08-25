/*倒计时*/
	function setTime(bid,val){
		$(".settimeout0").hide().siblings(".settimeout1").show();
		var jump = document.getElementById("settimeout"); 
		var timer = val;
		
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
		fun();
	};	
	

// 发送验证码
function sendCode(buttonId, phoneId) {
	if (phoneId == '') {
		$(".phone-input").css({"border-color":"#f50","color":"#999"});
		$(".phone-input").parent().siblings(".pb-empty").show();
		$(".phone-input").parent().siblings(".pb-error").hide();
		$(".phone-input").parent().siblings(".pb-error1").hide();
		return ;
	}else if(!common.isMobil(phoneId) || phoneId.length != 11 ){
		$(".phone-input").css({"border-color":"#f50"});
		$(".phone-input").parent().siblings(".pb-empty").hide();
		$(".phone-input").parent().siblings(".pb-error").show();
		$(".phone-input").parent().siblings(".pb-error1").hide();
		return ;
	}else if(!verifyExist(phoneId)){
		$(".phone-input").css({"border-color":"#f50"});
		$(".phone-input").parent().siblings(".pb-empty").hide();
		$(".phone-input").parent().siblings(".pb-error1").show();
		$(".phone-input").parent().siblings(".pb-error").hide();
		return ;
	}else{
		$(".phone-input").css({"border-color":"#d5d5d5"});
		$(".phone-input").parent().siblings(".pb-empty").hide();
		$(".phone-input").parent().siblings(".pb-error1").hide();
		$(".phone-input").parent().siblings(".pb-error").hide();
		
		$(".phone-captcha-input").parent().siblings(".pb-show1").show();
		$(".phone-captcha-input").parent().siblings(".pb-show").hide();
		$(".phone-captcha-input").parent().siblings(".pb-error").hide();
		$(".phone-captcha-input").parent().siblings(".pb-empty").hide();
	}
	
	setTime("", 60);
	
	$.ajax({
		url : "/sendCode",
		type : "POST",
		data : "phone="+phoneId,
		async:false,
		success : function(data) {
			
		}
	});
}

	
	

$(document).ready(function(){
	/*手机号验证*/
	$(".phone-input").on({
		focus:function(){
			var aname = this.value;
			$(this).css({"border-color":"#a1de00","color":"#000"});
			$(this).parent().siblings(".pb-error").hide();
			$(this).parent().siblings(".pb-empty").hide();
			$(this).parent().siblings(".pb-error1").hide();
		},
		blur:function(){
			var aname = this.value;			
			if (aname == "") {
				$(this).css({"border-color":"#f50","color":"#999"});
				$(this).parent().siblings(".pb-empty").show();
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-error1").hide();
				return false;
			} else {
					if(!common.isMobil(aname) || aname.length != 11 ){
						$(this).css({"border-color":"#f50"});
						$(this).parent().siblings(".pb-empty").hide();
						$(this).parent().siblings(".pb-error").show();
						$(this).parent().siblings(".pb-error1").hide();
						return false;
					}else{
						//格式验证正确之后判断是否存在该用户
						var flag = verifyExist(aname);
						if(flag){
							$(this).css({"border-color":"#d5d5d5"});
							$(this).parent().siblings(".pb-empty").hide();
							$(this).parent().siblings(".pb-error").hide();
							$(this).parent().siblings(".pb-error1").hide();
							$(".settimeout0").removeAttr("disabled");
							return flag;
						}else{
							$(this).css({"border-color":"#f50"});
							$(this).parent().siblings(".pb-empty").hide();
							$(this).parent().siblings(".pb-error1").show();
							$(this).parent().siblings(".pb-error").hide();
							$(".settimeout0").attr("disabled","disabled");
							return flag;
						}
					}
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
			$(this).parent().siblings(".pb-show1").hide();
		},
		blur:function(){
			var aname = this.value;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-empty").show();
				$(this).parent().siblings(".pb-show1").hide();
			} else {
				$(this).css({"border-color":"#d5d5d5"});
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-empty").hide();
				$(this).parent().siblings(".pb-show1").hide();
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
					if (substr("",aname,1)==1) {
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
				
				if ($(".password-input").val() != aname ) {
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
		var checkbox_input = document.getElementById("checkbox-input");
		var flag = true;
		
		//手机号检验
		if (phone_input == "") {
			$(".phone-input").parent().siblings(".pb-error").hide();
			$(".phone-input").parent().siblings(".pb-empty").show();
			$(".phone-input").parent().siblings(".pb-error1").hide();
			$(".phone-input").css({"border-color":"#f00"});
			flag = false;
		}else if(!common.isMobil(phone_input) || phone_input.length != 11 ){
			$(".phone-input").css({"border-color":"#f50"});
			$(".phone-input").parent().siblings(".pb-empty").hide();
			$(".phone-input").parent().siblings(".pb-error").show();
			$(".phone-input").parent().siblings(".pb-error1").hide();
			flag = false;
		}else{	//格式验证正确之后判断是否存在该用户
			var isExist = verifyExist(phone_input);
			if(isExist){
				$(".phone-input").css({"border-color":"#d5d5d5"});
				$(".phone-input").parent().siblings(".pb-empty").hide();
				$(".phone-input").parent().siblings(".pb-error").hide();
				$(".phone-input").parent().siblings(".pb-error1").hide();
				flag = true;
			}else{
				$(".phone-input").css({"border-color":"#f50"});
				$(".phone-input").parent().siblings(".pb-empty").hide();
				$(".phone-input").parent().siblings(".pb-error1").show();
				$(".phone-input").parent().siblings(".pb-error").hide();
				flag = false;
			}
		};
		
		//验证码校验
		if (phone_captcha_input == "") {
			$(".phone-captcha-input").parent().siblings(".pb-show").hide();
			$(".phone-captcha-input").parent().siblings(".pb-show1").hide();
			$(".phone-captcha-input").parent().siblings(".pb-error").hide();
			$(".phone-captcha-input").parent().siblings(".pb-empty").show();
			$(".phone-captcha-input").css({"border-color":"#f00"});
			flag = false;
		};
		//密码
		if (password_input == "") {
			$(".password-input").parent().siblings(".pb-error0").hide();
			$(".password-input").parent().siblings(".pb-error1").hide();
			$(".password-input").parent().siblings(".pb-show").hide();
			$(".password-input").parent().siblings(".pb-empty").show();
			$(".password-input").css({"border-color":"#f00"});
			flag = false;
		}else if((password_input.length < 6) || (password_input.length > 16)){
			$(".password-input").parent().siblings(".pb-error0").show();
			$(".password-input").parent().siblings(".pb-error1").hide();
			$(".password-input").parent().siblings(".pb-show").hide();
			$(".password-input").parent().siblings(".pb-empty").hide();
			$(".password-input").css({"border-color":"#f00"});
			flag = false;
		}else if(substr("",password_input,1)==1){
			$(".password-input").parent().siblings(".pb-error0").hide();
			$(".password-input").parent().siblings(".pb-error1").show();
			$(".password-input").parent().siblings(".pb-show").hide();
			$(".password-input").parent().siblings(".pb-empty").hide();
			$(".password-input").css({"border-color":"#f00"});
			flag = false;
		}
		//确认密码
		if (surepassword_input == "") {
			$(".surepassword-input").parent().siblings(".pb-show").hide();
			$(".surepassword-input").parent().siblings(".pb-error").hide();
			$(".surepassword-input").parent().siblings(".pb-empty").show();
			$(".surepassword-input").css({"border-color":"#f00"});
			flag = false;
		}else{
			if(password_input==""){
				//若原密码为空 则只提示原密码
				$(".password-input").parent().siblings(".pb-error0").hide();
				$(".password-input").parent().siblings(".pb-error1").hide();
				$(".password-input").parent().siblings(".pb-show").hide();
				$(".password-input").parent().siblings(".pb-empty").show();
				$(".password-input").css({"border-color":"#f00"});
				
				$(".surepassword-input").parent().siblings(".pb-show").hide();
				$(".surepassword-input").parent().siblings(".pb-error").hide();
				$(".surepassword-input").parent().siblings(".pb-empty").hide();
				$(".surepassword-input").css({"border-color":"#d5d5d5"});
				flag = false;
			}else if (password_input != surepassword_input) {
				$(".surepassword-input").parent().siblings(".pb-show").hide();
				$(".surepassword-input").parent().siblings(".pb-error").show();
				$(".surepassword-input").parent().siblings(".pb-empty").hide();
				$(".surepassword-input").css({"border-color":"#f00"});
				flag = false;
			}else{
				$(".surepassword-input").css({"border-color":"#a1de00"});
				$(".surepassword-input").parent().siblings(".pb-show").hide();
				$(".surepassword-input").parent().siblings(".pb-empty").hide();
				$(".surepassword-input").parent().siblings(".pb-error").hide();
			};
		}
		if (!checkbox_input.checked) {
			$("#box-alert .feedback-success-con p").text("请勾选注册并同意《用户注册服务协议》");
			$("#box-alert").show();
			flag = false;
		};
		
		if(flag){
			$.ajax({
				type : "POST",
				url : "/verifyCode",
				data:"code="+phone_captcha_input+"&phone="+phone_input,
				async : false,
				success : function(data) {
					if (data.code == 11) {
						//验证正确 保存数据
						$.ajax({
							type:"POST",
							url:"/personal/saveBind",
							data:"bind_phone="+phone_input+"&bind_password="+password_input+"&bind_repassword="+surepassword_input+"&back_url="+$("#back_url").text(),
							async:false,
							success:function(msg){
								if(msg.code==-2){
									//手机号格式不正确
									$(".phone-input").css({"border-color":"#f50"});
									$(".phone-input").parent().siblings(".pb-empty").hide();
									$(".phone-input").parent().siblings(".pb-error").show();
									$(".phone-input").parent().siblings(".pb-error1").hide();
									//TODO
									return false;
								}else if(msg.code==-3){
									//密码不能为空
									$(".password-input").parent().siblings(".pb-error0").hide();
									$(".password-input").parent().siblings(".pb-error1").hide();
									$(".password-input").parent().siblings(".pb-show").hide();
									$(".password-input").parent().siblings(".pb-empty").show();
									$(".password-input").css({"border-color":"#f00"});
									return false;
								}else if(msg.code==-4){
									//确认密码不能为空
									$(".surepassword-input").parent().siblings(".pb-show").hide();
									$(".surepassword-input").parent().siblings(".pb-error").hide();
									$(".surepassword-input").parent().siblings(".pb-empty").show();
									$(".surepassword-input").css({"border-color":"#f00"});
									return false;
								}else if(msg.code==-5){
									//两次密码不一致
									$(".surepassword-input").parent().siblings(".pb-show").hide();
									$(".surepassword-input").parent().siblings(".pb-error").show();
									$(".surepassword-input").parent().siblings(".pb-empty").hide();
									$(".surepassword-input").css({"border-color":"#f00"});
									return false;
								}else if(msg.code==-6){
									//手机号已注册,请更换手机号
									$(".phone-input").css({"border-color":"#f50"});
									$(".phone-input").parent().siblings(".pb-empty").hide();
									$(".phone-input").parent().siblings(".pb-error").hide();
									$(".phone-input").parent().siblings(".pb-error1").show();
									return false;
								}else if(msg.code == -100000){
									alert("系统异常，绑定失败");
									return false;
								}else {
									var dir = $("#back_url").text();
									if(dir==""){
										dir="/";
									}
									location = " "+dir+" ";
									return true;
								}
							}
						});
						
					} else {
						if (phone_captcha_input == "") {
							$(".phone-captcha-input").parent().siblings(".pb-show").hide();
							$(".phone-captcha-input").parent().siblings(".pb-show1").hide();
							$(".phone-captcha-input").parent().siblings(".pb-error").hide();
							$(".phone-captcha-input").parent().siblings(".pb-empty").show();
							$(".phone-captcha-input").css({"border-color":"#f00"});
							return;
						}else{
							$(".phone-captcha-input").parent().siblings(".pb-show").hide();
							$(".phone-captcha-input").parent().siblings(".pb-show1").hide();
							$(".phone-captcha-input").parent().siblings(".pb-error").show();
							$(".phone-captcha-input").parent().siblings(".pb-empty").hide();
							$(".phone-captcha-input").css({"border-color":"#f00"});
							return;
						}
					}
				},
			});
		}else{
			return ;
		}
	})	
})

function verifyExist(val){
	if(val==""||val==null){
		return false;
	}else{
		var flag = false;
		$.ajax({
			type:"POST",
			url:"/verifyPhone",
			data:"phone="+val,
			async:false,
			success:function(msg){
				//返回值为0 不存在 可以使用
				if(msg.code==0){
					flag = true;
				}else{
					flag = false;
				}
			}
		});
		return flag;
	}
}

$(".feedback-close ,.feedback-submit").click(function(event) {
	$(".feedback-success").hide();
	return;
});


//输入6-16位字母加数字
$(".password-input").bind("propertychange input", function(){
	var val = $(this).val();
	var istrue = substr(this,val,0);
});

//校验是否是字母或者数字   不允许输入特殊字符  (flag  0 普通校验  1 校验是否由两种字符 )
function substr(com, val, flag){
	var regNum = /^\d$/;
	var regStr = /^[a-zA-Z]$/;
	var array = new Array();
	array = val;
	if(flag==0){
		for(var i=0;i<array.length;i++){
			var temp = array[i];
			if(regNum.test(temp)||regStr.test(temp)){
				console.log("true");
			}else{
				var nr = val.substring(0,val.length - 1);
				$(com).val(nr);
			}
		}
		return false;
	}else{
		var isDouble = 0;
		var isStr = 0;
		for(var i=0;i<array.length;i++){
			var temp = array[i];
			if(regNum.test(temp)){
				isDouble = 1;
			}else if(regStr.test(temp)){
				isStr = 1;
			}
		}
		if(isDouble&&isStr){
			return 0;
		}else{
			return 1;
		}
	}
}
