// 充值金额：
$(document).ready(function(){
	$(".pub-mask-layer").css({"height":$(window).height()});
	$(".personal-menu").find("li").eq(1).addClass("current").siblings().removeClass("current");
	
	// 点击右上角关闭、确定关闭
	$(".popup-close").click(function(){
		$(".pub-mask-layer,.pub-popup").hide();
		$(this).parent().parent().hide();
		// 表单归零
		$(".pb-sign").hide();
		$("input").css({"border-color":"#d5d5d5"});
	});
	
	// 点击右上角关闭、确定关闭
	$(".popup-close-submit").click(function(){
		$(".pub-mask-layer,.pub-popup").hide();
		$(this).parent().parent().hide();
		// 表单归零
		$(".pb-sign").hide();
		$("input").css({"border-color":"#d5d5d5"});
		//刷新本页面以重新获取session
		location = "/payment/exist_pay_pwd/";
	});
	
	// 输入框变色、提示消失
	$(".pub-popup-in input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			$(this).parent().siblings(".pb-sign").hide();
		}, 
		blur:function(){
			$(this).css({"border-color":"#d5d5d5"});
		}
	});
	//输入6个数字
	$(".limitinput").bind("propertychange input",function(){
		var regex = /^\d{1,6}$/;
		var val = $(this).val();
		if(!regex.test(val)){
			var nr = val.substring(0,val.length - 1);
			$(this).val(nr);
		}
	});
	//输入手机号
	$("input[name=bindphone]").bind("propertychange input",function(){
		var regex = /^\d{0,11}$/;
		var val = $(this).val();
		if(!regex.test(val)){
			var nr = val.substring(0,val.length - 1);
			$(this).val(nr);
		}
	});
	//输入充值金额(9位整数，并且可以保留两位小数)
	$("#money").bind("propertychange input", function(){
		var val = $(this).val();
		substr(val, this);
	});
	function substr(val, com){
		var regex = /^(([1-9]\d{0,8})|0)(\.\d{0,2}){0,1}$/;
		if(val.length > 0 && !regex.test(val)){
			var nr = val.substring(0,val.length - 1);
			$(com).val(nr);
			substr(nr,com);
		}
	}
	//充值金额失去焦点判断
	$("#money").bind("blur", function(){
		var val = $(this).val().trim();
		var money = parseFloat(val).toFixed(2);
		if(!isNaN(money)){			
			$(this).val(money);
		}
	});
	
	// 动态码公共非空校验
	$("input[name=dynamic-code]").blur(function(){
			var avalue = $(this).val();
			$(this).val(avalue.trim());
			var dynamic_code = $(this).val();
			var ashow = $(this).parent().siblings(".pb-empty");
			if (dynamic_code == "") {
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("验证码不能为空");
			};
	});
	// 设置支付密码
	$("input[name=paypassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("支付密码必须是6位数字，且不同于登录密码");	
		},
		blur:function(){
			var pay_password = strim(this);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (pay_password == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("支付密码必须是6位数字，且不同于登录密码");
			} else if(pay_password.length != 6) {
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("支付密码必须是6位数字，且不同于登录密码");
			}else{
				$(ashow).hide();				
			}	
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=paypassword]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=surepaypassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("请输入确认支付密码");	
		},		
		blur:function(){
			var sure_paypassword = strim(this);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (sure_paypassword == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("确认支付密码不能为空");
			} else if($("input[name=paypassword]").val().trim() != sure_paypassword) {
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("密码不一致");
			}else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=surepaypassword]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=bindphone]").on({
		blur:function(){
			var bind_phone = strim(this);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (bind_phone == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("绑定手机不能为空");
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=bindphone]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=paypassword-subbtn]").click(function(){
		var pay_password = $("input[name=paypassword]").val().trim();
		var ashow = $(this).parent().siblings(".paypassword").find(".pb-empty");
		var validResult = true;
		if (pay_password == "") {
			$(ashow).show().css({"color":"#f00"}).text("支付密码不能为空");
			$("input[name=paypassword]").css({"border-color":"#f00"});
			validResult = false;
		} else if (pay_password.length != 6) {
			$(ashow).show().css({"color":"#f00"}).text("密码长度不符，请重新输入");
			$("input[name=paypassword]").css({"border-color":"#f00"});
			validResult = false;
		} else{
			$(ashow).hide();
		};			
		
		var sure_paypassword = $("input[name=surepaypassword]").val().trim();
		var bshow = $(this).parent().siblings(".surepaypassword").find(".pb-empty");
		if (sure_paypassword == "") {
			$(bshow).show().css({"color":"#f00"}).text("确认支付密码不能为空");
			$("input[name=surepaypassword]").css({"border-color":"#f00"});			
			validResult = false;
		} else if (pay_password != sure_paypassword) {
			if(validResult){				
				$(bshow).show().css({"color":"#f00"}).text("密码不一致");
				$("input[name=surepaypassword]").css({"border-color":"#f00"});	
			}
			validResult = false;
		} else {
			$(bshow).hide();
			$("input[name=surepaypassword]").css({"border-color":"#d5d5d5"});
		}

		var bind_phone = $("#phoneval").text();
		if(bind_phone == "undefined"){
			bind_phone = $("input[name=bindphone]").val().trim();
			var cshow = $(this).parent().siblings(".bindphone").find(".pb-empty");
			if (bind_phone == "") {
				$(cshow).show().css({"color":"#f00"}).text("绑定手机不能为空");
				$("input[name=bindphone]").css({"border-color":"#f00"});
				validResult = false;
			} else if (!checkPhone(bind_phone)) {
				$(cshow).show().css({"color":"#f00"}).text("请输入正确的手机号");
				$("input[name=bindphone]").css({"border-color":"#f00"});
				validResult = false;
			} else{
				$(cshow).hide();
			};	
		}

		var dynamic_code = $(this).parent().siblings(".dynamic-code").find("input[name=dynamic-code]").val().trim();
		var dshow = $(this).parent().siblings(".dynamic-code").find(".pb-empty");
		if (dynamic_code == "") {
			$(dshow).show().css({"color":"#f00"}).text("动态码不能为空");
			$("input[name=dynamic-code]").css({"border-color":"#f00"});
			validResult = false;
		} else{
			$(dshow).hide();
		};
		
		if(validResult){
			$.ajax({
	            type: "POST",
	            url: "/personal/setPayPwd",
	            data:"phone=" + bind_phone + "&payPwd="+ pay_password + "&surePwd=" + sure_paypassword + "&code=" + dynamic_code, 
	            async: false,
	            success: function(msg) {
	                if (msg == 'true') {
	                	// 表单归零
	                	$("input[name=paypassword]").val("");
	                	$("input[name=surepaypassword]").val("");
	                	$("input[name=paypassword-subbtn]").parent().siblings(".dynamic-code").find("input[name=dynamic-code]").val("");
	                	$(".pb-sign").hide();
	                	$("input").css({"border-color":"#d5d5d5"});
	                
	                	//关闭窗口，弹出提示窗口
	            		$(".pcr-set-paypassword").hide();
	            		$(".pcf-sp-success").show();
	                }else if(msg == 'codeError'){
	                	$("input[name=paypassword-subbtn]").parent().siblings(".dynamic-code").find(".pb-empty").show().css({"color":"#f00"}).text("动态码错误");
	                }else if(msg == 'pwdNotEq'){
	                	$("input[name=paypassword-subbtn]").parent().siblings(".surepaypassword").find(".pb-empty").show().css({"color":"#f00"}).text("密码不一致");
	                }else if(msg == 'false'){
	                	feedbackSuccess("未知错误：支付密码已存在");
	                }else {
	                    feedbackSuccess("服务器异常");
	                }
	            }
	        })
		}
	});
	// 支付密码设置成功页面点击确定
	$("input[name=submit-btn]").click(function(){
		$(".pub-mask-layer,.pub-popup").hide();
		//刷新本页面以重新获取session
		location = "/payment/exist_pay_pwd/";
	});
	
	// 充值页面支付方式选择
	$(".pcr-h-payway-block").click(function(){
		$(this).addClass("current").siblings().removeClass("current");
	});
	// 充值方式页面跳到新打开页面付钱，同时弹框出现
	$("input[name=pcr-payway-subbtn]").click(function(){
		$(".pub-mask-layer, .pub-popup").show(); 
		$(".pcf-gopay").show().siblings().hide();
		
		//后跳跳转到支付宝页面
		var amount = $(".pcr-h-amount").find("span").text();
		$("input[name=amount]").val(amount);
		$("#amountForm").submit();
	});
	// 点击跳到支付完成页面
	$("input[name=pcf-gopay-over]").click(function(){
		$(".pub-mask-layer,.pub-popup").hide();
		$(".personal-cont-recharge-success").show().siblings().hide();
		$.ajax({
			type: "POST",
			url: "/payment/rechargeFinish/",
			data:"ordernum=" + $("input[name=ordernum]").val(),
			success: function(msg) {
				if (msg == 'true') {
					location = "/payment/rechargeOK/"+$("input[name=ordernum]").val();
				} else{
					alert("充值失败！");
					location = "/payment/recharge/";
				}
			}
		});
	});
	
	//支付遇到问题
	$("input[name=pcf-gopay-problem]").click(function(){
		location = "/payment/recharge/";
	});

	// 余额记录、提现记录
	$(".pcam-cont-menu a").click(function(){
		var key = $(this).parent().index();
		$(this).addClass("current").parent().siblings().find("a").removeClass("current");
		$(".pcam-cont"+key+"").show().siblings().hide();
		$(".pcam-cont-menu").show();
	});
});

//充值按钮
function recharge(){
	//如果支付密码不存在，弹出支付密码设置窗口
	location = "/payment/exist_pay_pwd/";
}

//提现按钮
function withdraw(){
	//切换到提现页面
	location = "/withdrawals/";
}

//确定，去付款(充值主页面输入框校验)
function gotopay(exist_pay_pwd){
	if(exist_pay_pwd != 1){
		$(".pub-mask-layer,.pub-popup").show();
		$(".pcr-set-paypassword").show();
		return;
	}else{		
		var recharge = $("#money").val();
		var ashow = $("#money").parent().siblings(".pb-empty");
		if (recharge=='' || parseFloat(recharge) == 0) {
			$(ashow).show().css({"color":"#f00"}).text("充值金额不能为空或为0");
			$("#money").css({"border-color":"#f00"});
			return;
		} 
		$("input[name=amount]").val(recharge);
		$("#amountForm").submit();
	}
}

//确定去付款，校验、跳到充值方式页面
$("input[name=recharge]").on({
	focus:function(){
		$(this).css({"border-color":"#a1de00"});
		$(this).parent().siblings(".pb-empty").hide();
	},
	blur:function(){
		$(this).css({"border-color":"#d5d5d5"});
	}
});

//手机号绑定时获取短信验证码
function sc_tellphone(component, exist_phone) {
	var bind_phone;
	var validResult = true;
	if(exist_phone == 1){
		bind_phone = $("#phoneval").text();
		if(!checkPhone(bind_phone)){
			validResult = false;
			feedbackSuccess("手机号码不能为空，请刷新页面后重试,\n或致电客服热线400-885-7027");
		}								
	}else if(exist_phone == 0){		
		bind_phone = $("input[name=bindphone]").val().trim();
		var cshow = $(this).parent().siblings(".bindphone").find(".pb-empty");
		if (bind_phone == "") {
			$(cshow).show().css({"color":"#f00"}).text("绑定手机不能为空");
			$("input[name=bindphone]").css({"border-color":"#f00"});
			validResult = false;
		} else if (!checkPhone(bind_phone)) {
			$(cshow).show().css({"color":"#f00"}).text("请输入正确的手机号");
			$("input[name=bindphone]").css({"border-color":"#f00"});
			validResult = false;
		} else{
			$(cshow).hide();
		};	
	}
	if(validResult){		
		$.ajax({
			type: "POST",
			url: "/sendCode",
			data:"phone=" + bind_phone,
			success: function(msg) {
				if (msg) {
					SendDynamicCode(component, 60, "sc_tellphone(this);");
				} else{
					feedbackSuccess("发送失败");
				}
			}
		});
	}
};

//发送验证码后倒计时
function SendDynamicCode(component, interval, fun) {
    if (interval == 0) {
        component.value = "获取动态码";
        $(component).removeClass("current").text("获取短信验证码");
        $(component).attr("onclick", fun);
    } else {
    	$(component).attr("onclick", null);
    	$(component).addClass("current").text(""+interval+"秒后重新获取");
        interval--;
        t = setTimeout(function() {
            SendDynamicCode(component, interval, fun);
        }, 1000)
    }
};

function strim(com){
	var avalue = $(com).val().trim();
	$(com).val(avalue);
	return avalue;
}

String.prototype.trim = function (){
	return this.replace(/^\s*|\s*$/g, "");
}