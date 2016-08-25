// 校验数据格式
function checkData() {
	// TODO 前端校验
	return true;
}

//提交关联
function relate(formId){
	var result = checkData();
	if (!result) {
		return;
	}
	$.ajax({
		type : "POST",
		url : "/personal/saveRelate",
		data : $('#' + formId).serialize(),
		async : false,
		success : function(data) {
			alert(data.message);
			if (data.code == 200) {
				window.location.href = "/personal/getPersonalDetail";
			}
		},
		error : function(data) {
			alert(data.message);
		}
	});
}

$(document).ready(function(){
	/*账户名验证*/
	$(".name-input").on({
		focus:function(){
			var aname = this.value;
			$(this).css({"border-color":"#a1de00","color":"#000"});
			if (aname == "请输入注册手机号/邮箱" || aname == "" ) {
				this.value = "";
				$(this).css({"color":"#000"});
			};
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
				return;
			} else {
				if(verify(aname)){
					$(this).css({"border-color":"#d5d5d5"});
					$(this).parent().siblings(".pb-empty").hide();
					$(this).parent().siblings(".pb-error").hide();
					$(this).parent().siblings(".pb-error1").hide();
					return;
				}else{
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".pb-empty").hide();
					$(this).parent().siblings(".pb-error").show();
					//TODO 账户已绑定<!-- 缺乏相关需求 -->
					$(this).parent().siblings(".pb-error1").hide();
					return;
				}
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
			$(this).parent().siblings(".pb-error").hide();
			$(this).parent().siblings(".pb-error0").hide();
			$(this).parent().siblings(".pb-error1").hide();
		},
		blur:function(){
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-error0").hide();
				$(this).parent().siblings(".pb-error1").hide();
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-empty").show();					
			} else {
				if ((alength < 6) || (alength > 16)) {
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".pb-error").hide();
					$(this).parent().siblings(".pb-error0").show();
					$(this).parent().siblings(".pb-error1").hide();
					$(this).parent().siblings(".pb-show").hide();
					$(this).parent().siblings(".pb-empty").hide();						
				} else{
					if (/^\d+$/.test(aname)) {
						$(this).css({"border-color":"#f50"});
						$(this).parent().siblings(".pb-error").hide();
						$(this).parent().siblings(".pb-error0").hide();
						$(this).parent().siblings(".pb-error1").show();
						$(this).parent().siblings(".pb-show").hide();
						$(this).parent().siblings(".pb-empty").hide();	
					} else {
						$(this).css({"border-color":"#d5d5d5"});
						$(this).parent().siblings(".pb-error").hide();
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
		var flag = true;
		if (name_input == "" || name_input == "请输入注册手机号/邮箱") {
			$(".name-input").parent().siblings(".pb-error").hide();
			$(".name-input").parent().siblings(".pb-empty").show();
			$(".name-input").parent().siblings(".pb-error1").hide();
			$(".name-input").css({"border-color":"#f00"});
			flag = false;
		}else if(!verify(name_input)){
			$(".name-input").css({"border-color":"#f50"});
			$(".name-input").parent().siblings(".pb-empty").hide();
			$(".name-input").parent().siblings(".pb-error").show();
			$(".name-input").parent().siblings(".pb-error1").hide();
			flag = false;
		};
		
		if (password_input == "") {
			$(".password-input").parent().siblings(".pb-error").hide();
			$(".password-input").parent().siblings(".pb-error0").hide();
			$(".password-input").parent().siblings(".pb-error1").hide();
			$(".password-input").parent().siblings(".pb-show").hide();
			$(".password-input").parent().siblings(".pb-empty").show();
			$(".password-input").css({"border-color":"#f00"});
			flag = false;
		}else if ((password_input.length < 6) || (password_input.length > 16)) {
			$(".password-input").css({"border-color":"#f50"});
			$(".password-input").parent().siblings(".pb-error").hide();
			$(".password-input").parent().siblings(".pb-error0").show();
			$(".password-input").parent().siblings(".pb-error1").hide();
			$(".password-input").parent().siblings(".pb-show").hide();
			$(".password-input").parent().siblings(".pb-empty").hide();	
			flag = false;
		} else{
			if (/^\d+$/.test(password_input)) {
				$(".password-input").css({"border-color":"#f50"});
				$(".password-input").parent().siblings(".pb-error").hide();
				$(".password-input").parent().siblings(".pb-error0").hide();
				$(".password-input").parent().siblings(".pb-error1").show();
				$(".password-input").parent().siblings(".pb-show").hide();
				$(".password-input").parent().siblings(".pb-empty").hide();
				flag = false;
			} else {
				$(".password-input").css({"border-color":"#d5d5d5"});
				$(".password-input").parent().siblings(".pb-error").hide();
				$(".password-input").parent().siblings(".pb-error0").hide();
				$(".password-input").parent().siblings(".pb-error1").hide();
				$(".password-input").parent().siblings(".pb-show").hide();
				$(".password-input").parent().siblings(".pb-empty").hide();							
			};
			};				
		
		
		if(flag){
			//发送验证，类似登录
			$.ajax({
				type:"POST",
				url:"/personal/saveRelate",
				data:"relate_phone="+name_input+"&relate_password="+password_input,
				async:false,
				success:function(msg){
					if(msg.code==200){
						//关联成功
						var dir = $("#back_url").text();
						if(dir==""){
							dir="/";
						}
						location = " "+dir+" ";
						return true;
					}else if(msg.code==-1){
						//密码错误
						$(".password-input").parent().siblings(".pb-error").show();
						$(".password-input").parent().siblings(".pb-error0").hide();
						$(".password-input").parent().siblings(".pb-error1").hide();
						$(".password-input").parent().siblings(".pb-show").hide();
						$(".password-input").parent().siblings(".pb-empty").hide();
						$(".password-input").css({"border-color":"#f00"});
						return;
					}else if(msg.code==-2 || msg.code==-4){
						//账户格式不正确  || 账户名不存在
						$(".name-input").parent().siblings(".pb-error").show();
						$(".name-input").parent().siblings(".pb-empty").hide();
						$(".name-input").parent().siblings(".pb-error1").hide();
						$(".name-input").css({"border-color":"#f00"});
						return;
					}else if(msg.code==-3){
						//密码为空
						$(".password-input").parent().siblings(".pb-error0").hide();
						$(".password-input").parent().siblings(".pb-error1").hide();
						$(".password-input").parent().siblings(".pb-show").hide();
						$(".password-input").parent().siblings(".pb-empty").show();
						$(".password-input").css({"border-color":"#f00"});
						return;
					}else if(msg.code==-6){
						//已经绑定
						$(".name-input").css({"border-color":"#f50"});
						$(".name-input").parent().siblings(".pb-empty").hide();
						$(".name-input").parent().siblings(".pb-error").show();
						$(".name-input").parent().siblings(".pb-error1").hide();
						return;
					}else if(msg.code==-100000){
						alert("系统异常");
						return;
					}
				}
			});
		}
		return;
	});
})

function verify(val){
	//判断账户是否存在
	var flag = false;
	$.ajax({
		type:"POST",
		url:"/verifyPhoneOrEmail",
		data:"phoneOrEmail="+val,
		async:false,
		success:function(msg){
			//存在 200  不存在 0
			if(msg.code==200){
				flag = true;
			}else{
				flag = false;
			}
		}
	});
	return flag;
}