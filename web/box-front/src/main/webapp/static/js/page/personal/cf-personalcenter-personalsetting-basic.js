$(document).ready(function(){			
	// 安全中心	
	// 弹出对应窗口
	$(".pub-mask-layer").css({"height":$(window).height()});
	$(".sc-cont-go").click(function(){
		var key = $(this).parent().parent().index();  // alert(key); 返回0到3
		var word = $(this).text();
		switch(key){
		case 0:
			// 手机号立即绑定，已绑定
			if ( word == "立即绑定") {
				$(".pub-mask-layer,.pub-popup").show();		
				$(".pub-popup-in").eq(key).show().siblings().hide();				
			} else{
				return false;
			}break;
		case 1:
			// 登录密码立即设置，修改
			if(!isBindPhone()){
				feedbackSuccess("请先绑定手机号");
			}else{
				if ( word == "立即设置") {
					$(".pub-mask-layer,.pub-popup").show();		
					$(".pub-popup-in").eq(key).show().siblings().hide();				
				} else if ( word == "修改") {
					$(".pub-mask-layer,.pub-popup").show();		
					$(".pub-popup-in").eq(key+3).show().siblings().hide();
				}
			}break;
		case 2:
			if(!isBindPhone()){
				feedbackSuccess("请先绑定手机号");
			}else{
				// 邮箱立即绑定，立即验证/修改邮箱，已发送/激活邮箱，已绑定
				var email = $(this).parent().siblings(".sc-cont-explain").text();
				if ( word == "立即绑定") {
					$(".pub-mask-layer,.pub-popup").show();		
					$(".pub-popup-in").eq(key).show().siblings().hide();				
				} else if ( word == "立即验证") {
					$(".sc-cont-mailbox-go").hide();
					$(".sc-cont-mailbox-last").css({"display":"block"});
					//发送邮件js
					$.ajax({
						type:"POST",
						async:false,
						url:"/sendBindEmail",
						data:"email="+email,
						success:function(msg){
							if(msg == 'errorType'){
								showSuccess("邮箱格式不正确");
							}else if(msg == 'registed'){
								showSuccess("该邮箱已被注册");
							}else if(msg == 'false'){
								feedbackSuccess("验证邮箱发送失败");
							}else if(msg != 'true'){								
								feedbackSuccess("服务器异常");
							}
						}
					});
				} else if ( word == "修改邮箱") {
					$(".pub-mask-layer,.pub-popup").show();		
					$(".pub-popup-in").eq(key).show().siblings().hide();						
				} else if ( word == "激活邮箱") {
					window.open("http://"+gotoEmail(email));
				}
			}break;
		case 3:
			if(!isBindPhone()){
				feedbackSuccess("请先绑定手机号");
			}else{
				// 支付密码立即设置，修改
				if ( word == "立即设置") {
					$(".pub-mask-layer,.pub-popup").show();		
					$(".pub-popup-in").eq(key).show().siblings().hide();				
				} else{
					if ( word == "修改") {
						$(".pub-mask-layer,.pub-popup").show();		
						$(".pub-popup-in").eq(key+2).show().siblings().hide();
					} 
				}
			}break;
		}
	});
	// 点击右上角关闭、确定关闭
	$(".popup-close-o").click(function(){
		$(".pub-mask-layer,.pub-popup").hide();
		$(this).parent().parent().hide();
		// 表单归零
		$(".pb-sign").hide();
		$("input").css({"border-color":"#d5d5d5"});
		window.location.href = "/personal/security";
	});
	
	$(".popup-close-p").click(function(){
		$(".pub-mask-layer,.pub-popup").hide();
		$(this).parent().parent().hide();
		// 表单归零
		$(".pb-sign").hide();
		$("input").css({"border-color":"#d5d5d5"});
		window.location.href = "/logOut";
	});
	// 安全等级图片
	var sc_grade = $(".sc-tittle-percent").html();
	var sc_grade_word = sc_grade.substring(0,sc_grade.length-1);
	$(".sc-tittle-img").addClass("sc-tittle-img"+sc_grade_word+"");
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
	/*动态码输入6个字符*/
	$("input[name=dynamic-code]").bind("propertychange input blur",function(){
		var count=$(this).val().length;
		if (count>6) {
			var nr = $(this).val().substring(0,6);
			$(this).val(nr);
		};
	});
	// 动态码公共非空校验
	$("input[name=dynamic-code]").blur(function(){
			var dynamic_code = $(this).val();
			dynamic_code = aboutTrim(dynamic_code);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (dynamic_code == "") {
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("动态码不能为空");
			};
	});
	// 验证码公共非空校验 绑定邮箱
	$("input[name=verification-code]").blur(function(){
			var verification_code = $(this).val();
			verification_code = aboutTrim(verification_code);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (verification_code == "") {
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("验证码不能为空");
			};
	});
	//输入6个数字 设置支付密码跟修改支付密码
	$("input[name=paypassword],input[name=surepaypassword],input[name=n-paypassword],input[name=c-paypassword],input[name=c-surepaypassword]").bind("propertychange input",function(){
		var regex = /^\d{1,6}$/;
		var val = $(this).val();
		if(!regex.test(val)){
			var nr = val.substring(0,val.length - 1);
			$(this).val(nr);
		}
	});
	//输入6-16数字和字母 区分大小写 设置登录密码跟修改登录密码
	$("input[name=password],input[name=surepassword],input[name=n-password],input[name=c-password],input[name=c-surepassword]").bind("propertychange input",function(){
		var regex = /^\w{1,16}$/;
		var val = $(this).val();
		if(!regex.test(val)){
			var nr = val.substring(0,val.length - 1);
			$(this).val(nr);
		}
	});
	// 绑定手机号
	$("input[name=tellphone]").on({
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		},
		blur:function(){
			var tell_phone = $("input[name=tellphone]").val();
			tell_phone = aboutTrim(tell_phone);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (tell_phone == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("手机号不能为空");
			};
		}
	});
	$("input[name=tellphone]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=tellphone-subbtn]").click(function(){
		var tell_phone = $("input[name=tellphone]").val();
		tell_phone = aboutTrim(tell_phone);
		var ashow = $(this).parent().siblings(".tellphone").find(".pb-empty");
		if (tell_phone == "") {
			$(ashow).show().css({"color":"#f00"}).text("手机号不能为空");
			$("input[name=tellphone]").css({"border-color":"#f00"});
		} else { 
			if (!checkPhone(tell_phone)) {
				$(ashow).show().css({"color":"#f00"}).text("请输入正确的手机号");
				$("input[name=tellphone]").css({"border-color":"#f00"});
			} else{
				$(ashow).hide();
			};
		};
		var binput = $(this).parent().siblings(".dynamic-code").find("input[name=dynamic-code]")
		var dynamic_code = $(this).parent().siblings(".dynamic-code").find("input[name=dynamic-code]").val();
		dynamic_code = aboutTrim(dynamic_code);
		var bshow = $(this).parent().siblings(".dynamic-code").find(".pb-empty");
		if (dynamic_code == "") {
			$(bshow).show().css({"color":"#f00"}).text("动态码不能为空");
			$(binput).css({"border-color":"#f00"});
		} else{
			$(bshow).hide();
		};
	});
	// 登录密码
	$("input[name=password]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("由6-16位字母加数字组成，区分大小写");	
		},
		blur:function(){
			var pass_word = $("input[name=password]").val();
			pass_word = aboutTrim(pass_word);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (pass_word == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("密码不能为空");
			} else {
				$(ashow).hide();
			};			
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=password]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=surepassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("请再次输入密码");	
		},		
		blur:function(){
			var sure_password = $("input[name=surepassword]").val();
			sure_password = aboutTrim(sure_password);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (sure_password == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("确认密码不能为空");
			} else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=surepassword]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=password-subbtn]").click(function(){
		var pass_word = $("input[name=password]").val();
		pass_word = aboutTrim(pass_word);
		var ashow = $(this).parent().siblings(".password").find(".pb-empty");
		var validResult = true;
		if (pass_word == "") {
			$(ashow).show().css({"color":"#f00"}).text("密码不能为空");
			$("input[name=password]").css({"border-color":"#f00"});
			validResult = false;
		} else if (pass_word.length < 6 || pass_word.length > 16) {
			$(ashow).show().css({"color":"#f00"}).text("密码长度不符，请重新输入");
			$("input[name=password]").css({"border-color":"#f00"});
			validResult = false;
		} else if(/^(\d+|[a-zA-Z]+)$/.test(pass_word)){
			$(ashow).show().css({"color":"#f00"}).text("请设置有6-16位数字加字母组成的复杂密码");
			$("input[name=password]").css({"border-color":"#f00"});
			validResult =  false;
		} else if(!pwdCom(pass_word)){
			$(ashow).show().css({"color":"#f00"}).text("由6-16位字母加数字组成，区分大小写");
			$("input[name=password]").css({"border-color":"#f00"});
			validResult = false;
		}else{
			$(ashow).hide();
		};			

		var sure_password = $("input[name=surepassword]").val();
		sure_password = aboutTrim(sure_password);
		var bshow = $(this).parent().siblings(".surepassword").find(".pb-empty");
		if (sure_password == "") {
			$(bshow).show().css({"color":"#f00"}).text("确认密码不能为空");
			$("input[name=surepassword]").css({"border-color":"#f00"});
			validResult = false;
		} else if(pass_word != sure_password) {
			if(validResult){				
				$(bshow).show().css({"color":"#f00"}).text("密码不一致");
				$("input[name=surepassword]").css({"border-color":"#f00"});
			}
			validResult = false;
		} else {
			$(bshow).hide();
			$("input[name=surepassword]").css({"border-color":"#d5d5d5"});
		}
		
		if(validResult){			
			$.ajax({
				type: "POST",
				url: "/user/resetPwd",
				data:"phone=" + $("#phoneval").text() + "&password=" + pass_word,
				async: false,
				success: function(msg) {
					if (msg == 'true') {
						//清空原有框里的值
						$("input[name=password]").val("");
						$("input[name=surepassword]").val("");
						
						//关闭窗口，弹出提示窗口，刷新页面
						$("input[name=password]").parent().parent().parent().parent().hide();
						showPwdSuccess("登录密码设置成功");
					}else if(msg == 'phoneError'){
						feedbackSuccess("手机号错误");
					}else {
						feedbackSuccess("服务器异常");
					}
				}
			})
		}
	})
	// 绑定邮箱
	$("input[name=mailbox]").on({
		blur:function(){
			var mail_box = $("input[name=mailbox]").val();
			mail_box = aboutTrim(mail_box);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (mail_box == "") {
					$(this).siblings(".pb-ph").show();
					$(this).css({"border-color":"#f00"});
					$(ashow).show().css({"color":"#f00"}).text("邮箱不能为空");			
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=mailbox]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=mailbox-subbtn]").click(function(){
		var mail_box = $("input[name=mailbox]").val();
		mail_box = aboutTrim(mail_box);
		var ashow = $(this).parent().siblings(".mailbox").find(".pb-empty");
		var validResult = true;
		if (mail_box == "") {
			$(ashow).show().css({"color":"#f00"}).text("邮箱不能为空");
			$("input[name=mailbox]").css({"border-color":"#f00"});
			validResult = false;
		} else if (!checkEmail(mail_box)) {
			$(ashow).show().text("请输入正确的邮箱");
			$("input[name=mailbox]").css({"border-color":"#f00"});
			validResult = false;
		} else{
			$(ashow).hide();
		}
		
		var binput = $(this).parent().siblings(".dynamic-code").find("input[name=verification-code]")
		var verification_code = $(this).parent().siblings(".dynamic-code").find("input[name=verification-code]").val();
		verification_code = aboutTrim(verification_code);
		var bshow = $(this).parent().siblings(".dynamic-code").find(".pb-empty");
		if (verification_code == "") {
			$(bshow).show().css({"color":"#f00"}).text("验证码不能为空");
			$(binput).css({"border-color":"#f00"});
			validResult = false;
		} else{
			$(bshow).hide();
		};		
		
		if(validResult){
			//正在发送请稍候
			var $com = $("input[name=mailbox-subbtn]").parent().siblings(".dynamic-code").find(".pb-empty");
			var num = 1;
			$com.show().css({"color":"#000"}).text("正在发送，请稍候");
			var my_interval = setInterval(function () {
				if(num == 0)$com.show().css({"color":"#000"}).text("正在发送，请稍候");
				else if(num == 1)$com.show().css({"color":"#000"}).text("正在发送，请稍候.");
				else if(num == 2)$com.show().css({"color":"#000"}).text("正在发送，请稍候..");
				else if(num == 3)$com.show().css({"color":"#000"}).text("正在发送，请稍候...");
				if(num++ > 3) num = 0;
	        }, 500);
			$.ajax({
				type: "POST",
				url: "/user/bindValidEmail",
				data:"email=" + mail_box + "&code=" + verification_code,
				async: false,
				success: function(msg) {
					clearInterval(my_interval);
					if (msg == 'true') {
						//弹出提示窗口，关闭窗口，刷新页面
						$(".sent").show().children(".sent-word1").text($("input[name=mailbox]").val());
						$(".not-sent").hide();
						$(".sent").click(function(){
							window.open("http://"+gotoEmail(email));
						});

						//清空原有框里的值
						$("input[name=mailbox]").val("");
						$("input[name=mailbox-subbtn]").parent().siblings(".dynamic-code").find("input[name=verification-code]").val("");
						
						//三秒后自动关闭窗口，在本窗口的关闭事件中要只执行刷新事件
//						$("input[name=mailbox-subbtn]").parent().parent().parent().hide();
					}else if(msg == 'codeNotEq'){
						$("input[name=mailbox-subbtn]").parent().siblings(".dynamic-code").find(".pb-empty").show().css({"color":"#f00"}).text("验证码错误");
					}else if(msg == 'registed'){
						$("input[name=mailbox-subbtn]").parent().siblings(".mailbox").find(".pb-empty").show().text("该邮箱已注册");
					}else if(msg == 'errorType'){
						$("input[name=mailbox-subbtn]").parent().siblings(".mailbox").find(".pb-empty").show().text("邮箱格式不正确");
					}else if(msg == 'false'){
						$("input[name=mailbox-subbtn]").parent().siblings(".mailbox").find(".pb-empty").show().text("验证邮箱发送失败");
					}else {
						feedbackSuccess("服务器异常");
					}
				}
			})
		}
	})
	
	// 保存支付密码
	$("input[name=paypassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("支付密码为6位数字");	
		},
		blur:function(){
			var pay_password = $("input[name=paypassword]").val();
			pay_password = aboutTrim(pay_password);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (pay_password == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("支付密码不能为空");
			} else {
				$(ashow).hide();
			};	
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
			var sure_paypassword = $("input[name=surepaypassword]").val();
			sure_paypassword = aboutTrim(sure_paypassword);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (sure_paypassword == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("确认支付密码不能为空");
			} else {
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
			var bind_phone = $("input[name=bindphone]").val();
			bind_phone = aboutTrim(bind_phone);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (bind_phone == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("绑定手机不能为空");
			} else {
				$(ashow).hide();
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
		var pay_password = $("input[name=paypassword]").val();
		pay_password = aboutTrim(pay_password);
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
		
		var sure_paypassword = $("input[name=surepaypassword]").val();
		sure_paypassword = aboutTrim(sure_paypassword);
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

		var dynamic_code = $(this).parent().siblings(".dynamic-code").find("input[name=dynamic-code]").val();
		dynamic_code = aboutTrim(dynamic_code);
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
	            data:"phone=" + $("#phoneval").text() + "&payPwd="+ pay_password + "&surePwd=" + sure_paypassword + "&code=" + dynamic_code, 
	            async: false,
	            success: function(msg) {
	                if (msg == 'true') {
	                	//清空原有框里的值
	                	$("input[name=paypassword]").val("");
	                	$("input[name=surepaypassword]").val("");
	                	$("input[name=paypassword]").parent().parent().siblings(".dynamic-code").find("input[name=dynamic-code]").val("");
	                	
						//关闭窗口，弹出提示窗口，刷新页面
						$("input[name=paypassword]").parent().parent().parent().parent().hide();
						showSuccess("支付密码设置成功");
	                }else if(msg == 'codeError'){
	                	$("input[name=paypassword]").parent().parent().siblings(".dynamic-code").children(".pb-empty").show().css({"color":"#f00"}).text("动态码错误");
	                	$("input[name=paypassword]").parent().parent().siblings(".dynamic-code").find("input[name=dynamic-code]").css({"border-color":"#f00"});
	                }else if(msg == 'pwdNotEq'){
	                	var bshow = $("input[name=paypassword-subbtn]").parent().siblings(".surepaypassword").find(".pb-empty");
	                	bshow.show().css({"color":"#f00"}).text("密码不一致");
	        			$("input[name=surepaypassword]").css({"border-color":"#f00"});	
	                }else if(msg == 'false'){
	                	feedbackSuccess("未知错误：支付密码已存在");
	                }else {
	                    feedbackSuccess("服务器异常");
	                }
	            }
	        })
		}

	})	
	// 修改登录密码
	$("input[name=n-password]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			// $(ashow).show().css({"color":"#000"}).text("由6-16位字母加数字组成，区分大小写");	
		},
		blur:function(){
			var n_password = $("input[name=n-password]").val();
			n_password = aboutTrim(n_password);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (n_password == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("当前密码不能为空");
			} else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=n-password]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=c-password]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("由6-16位字母加数字组成，区分大小写");	
		},
		blur:function(){
			var c_password = $("input[name=c-password]").val();
			c_password = aboutTrim(c_password);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (c_password == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("密码不能为空");
			} else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=c-password]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=c-surepassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
		},		
		blur:function(){
			var c_surepassword = $("input[name=c-surepassword]").val();
			c_surepassword = aboutTrim(c_surepassword);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (c_surepassword == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("确认密码不能为空");
			} else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=c-surepassword]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=c-password-subbtn]").click(function(){
		var n_password = $("input[name=n-password]").val();
		n_password = aboutTrim(n_password);
		var ashow = $(this).parent().siblings(".now-password").find(".pb-empty");
		var validResult = true;
		if (n_password == "") {
			$(ashow).show().css({"color":"#f00"}).text("当前密码不能为空");
			$("input[name=n-password]").css({"border-color":"#f00"});
			validResult =  false;
		} else {
			$(ashow).hide();
		};

		var c_password = $("input[name=c-password]").val();
		c_password = aboutTrim(c_password);
		var bshow = $(this).parent().siblings(".c-password").find(".pb-empty");
		if (c_password == "") {
			$(bshow).show().css({"color":"#f00"}).text("密码不能为空");
			$("input[name=c-password]").css({"border-color":"#f00"});
			validResult =  false;
		} else if (c_password.length < 6 || c_password.length > 16) {
			$(bshow).show().css({"color":"#f00"}).text("密码长度不符，请重新输入");
			$("input[name=c-password]").css({"border-color":"#f00"});
			validResult =  false;
		} else if(/^(\d+|[a-zA-Z]+)$/.test(c_password)){
			$(bshow).show().css({"color":"#f00"}).text("请设置有6-16位数字加字母组成的复杂密码");
			$("input[name=c-password]").css({"border-color":"#f00"});
			validResult =  false;
		}  else if(!pwdCom(c_password)){
			$(bshow).show().css({"color":"#f00"}).text("由6-16位字母加数字组成，区分大小写");
			$("input[name=c-password]").css({"border-color":"#f00"});
			validResult = false;
		} else{
			$(bshow).hide();
		}	
		
		var c_surepassword = $("input[name=c-surepassword]").val();
		c_surepassword = aboutTrim(c_surepassword);
		var cshow = $(this).parent().siblings(".c-surepassword").find(".pb-empty");
		if (c_surepassword == "") {
			$(cshow).show().css({"color":"#f00"}).text("确认密码不能为空");
			$("input[name=c-surepassword]").css({"border-color":"#f00"});			
			validResult =  false;
		} 
		/*
		else if (c_password != c_surepassword) {
			$(cshow).show().css({"color":"#f00"}).text("密码不一致");
			$("input[name=c-surepassword]").css({"border-color":"#f00"});	
			validResult =  false;
		} 
		*/
		else {
			$(cshow).hide();
		}
		//修改登录密码
		if(validResult){
			$.ajax({
				type: "POST",
				url: "/user/changePwd?oldPassword=" + n_password + "&newPassword=" + c_password + "&surepassword=" + c_surepassword,
				async: false,
				success: function(msg) {
					if(msg == 'noEqual'){
						$("input[name=c-password-subbtn]").parent().siblings(".c-surepassword").find(".pb-empty").show().css({"color":"#f00"}).text("密码不一致");
						$("input[name=c-surepassword]").css({"border-color":"#f00"});
					}else if(msg == 'false') {
						$("input[name=c-password-subbtn]").parent().siblings(".now-password").find(".pb-empty").show().css({"color":"#f00"}).text("当前密码错误");
					}else	if (msg == 'true') {
						//清空原有框里的值
						$("input[name=n-password]").val("");
						$("input[name=c-password]").val("");
						$("input[name=c-surepassword]").val("");
						//关闭窗口，弹出提示窗口，刷新页面
						$("input[name=n-password]").parent().parent().parent().parent().hide();
						showPwdSuccess("登录密码修改成功");
					}else{
						feedbackSuccess("服务器异常");
					}
				}
			})
		}
	})
	
	
	// 修改支付密码
	$("input[name=n-paypassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("支付密码为6位数字");	
		},
		blur:function(){
			var n_paypassword = $("input[name=n-paypassword]").val();
			n_paypassword = aboutTrim(n_paypassword);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (n_paypassword == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("当前支付密码不能为空");
			} else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=n-paypassword]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=c-paypassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
			$(ashow).show().css({"color":"#000"}).text("支付密码为6位数字");	
		},
		blur:function(){
			var c_paypassword = $("input[name=c-paypassword]").val();
			c_paypassword = aboutTrim(c_paypassword);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (c_paypassword == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("新的支付密码不能为空");
			} else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=c-paypassword]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=c-surepaypassword]").on({
		focus:function(){
			var ashow = $(this).parent().siblings(".pb-empty");			
		},		
		blur:function(){
			var c_surepaypassword = $("input[name=c-surepaypassword]").val();
			c_surepaypassword = aboutTrim(c_surepaypassword);
			var ashow = $(this).parent().siblings(".pb-empty");
			if (c_surepaypassword == "") {
				$(this).siblings(".pb-ph").show();
				$(this).css({"border-color":"#f00"});
				$(ashow).show().css({"color":"#f00"}).text("确定新支付密码不能为空");
			} else {
				$(ashow).hide();
			};
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		}
	});
	$("input[name=c-surepaypassword]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});
	$("input[name=c-paypassword-subbtn]").click(function(){
		var n_paypassword = $("input[name=n-paypassword]").val();
		n_paypassword = aboutTrim(n_paypassword);
		var ashow = $(this).parent().siblings(".now-paypassword").find(".pb-empty");
		if (n_paypassword == "") {
			$(ashow).show().css({"color":"#f00"}).text("当前支付密码不能为空");
			$("input[name=n-paypassword]").css({"border-color":"#f00"});
		} else{
			$(ashow).hide();
		};
		var c_paypassword = $("input[name=c-paypassword]").val();
		c_paypassword = aboutTrim(c_paypassword);
		var ashow = $(this).parent().siblings(".c-paypassword").find(".pb-empty");
		var validResult = true;
		if (c_paypassword == "") {
			$(ashow).show().css({"color":"#f00"}).text("新的支付密码不能为空");
			$("input[name=c-paypassword]").css({"border-color":"#f00"});
			validResult = false;
		} else if (c_paypassword.length != 6) {
			$(ashow).show().css({"color":"#f00"}).text("密码长度不符，请重新输入");
			$("input[name=c-paypassword]").css({"border-color":"#f00"});
			validResult = false;
		} else if(!pwdCom(c_paypassword)){
			$(ashow).show().css({"color":"#f00"}).text("由6-16位字母加数字组成，区分大小写");
			$("input[name=c-paypassword]").css({"border-color":"#f00"});
			validResult = false;
		} else{
			$(ashow).hide();
		};		
		var c_surepaypassword = $("input[name=c-surepaypassword]").val();
		c_surepaypassword = aboutTrim(c_surepaypassword);
		var bshow = $(this).parent().siblings(".c-surepaypassword").find(".pb-empty");
		if (c_surepaypassword == "") {
			$(bshow).show().css({"color":"#f00"}).text("确认新支付密码不能为空");
			$("input[name=c-surepaypassword]").css({"border-color":"#f00"});		
			validResult = false;
		} else if (c_paypassword != c_surepaypassword) {
			if(validResult){				
				$(bshow).show().css({"color":"#f00"}).text("密码不一致");
				$("input[name=c-surepaypassword]").css({"border-color":"#f00"});	
			}
			validResult = false;
		} else {
			$(bshow).hide();
		}
		
		//修改支付密码
		if(validResult){
			$.ajax({
	            type: "POST",
	            url: "/personal/resetPayPwd",
	            data:"currentpayPwd=" + n_paypassword + "&payPwd="+ c_paypassword + "&surePwd=" + c_surepaypassword , 
	            async: false,
	            success: function(msg) {
	                if (msg == 'true') {
	                	//清空原有框里的值
	                	$("input[name=n-paypassword]").val("");
	                	$("input[name=c-paypassword]").val("");
	                	$("input[name=c-surepaypassword]").val("");
	                	
						//关闭窗口，弹出提示窗口，刷新页面
						
						$("input[name=c-paypassword]").parent().parent().parent().parent().hide();
						showSuccess("支付密码修改成功");
	                }else if(msg == 'codeError'){
	                	$("input[name=c-paypassword-subbtn]").parent().siblings(".now-paypassword").find(".pb-empty").show().css({"color":"#f00"}).text("当前支付密码错误");
	                	$("input[name=n-paypassword]").css({"border-color":"#f00"});
	                }else if(msg == 'pwdNotEq'){
	                	var bshow = $("input[name=c-paypassword-subbtn]").parent().siblings(".c-surepaypassword").find(".pb-empty");
	                	bshow.show().css({"color":"#f00"}).text("密码不一致");
	        			$("input[name=c-surepaypassword]").css({"border-color":"#f00"});	
	                }else if(msg == 'pp'){
	                	feedbackSuccess("修改支付密码失败。当前没有支付密码，请先设置支付密码。");
	                }else if(msg == 'false'){
	                	feedbackSuccess("服务器异常");
	                }
	            }
	        })
		}
	});
})

//由6-16位字母加数字组成，区分大小写
function pwdCom(pwd){
	if(/^[0-9a-zA-Z]{6,16}$/.test(pwd)){
		return true;
	}else{
		return false;
	}
}

function validityCode(com) {
	$(com).parent().siblings().children(".code-img").attr("src", '/image?' + Math.random());
}

function isBindPhone(){	
	var bind_phone = $("#phoneval").text();
	if(checkPhone(bind_phone)){
		return true;
	}else{
		return false;
	}
}

function aboutTrim(thisval){
		aaa = $.trim(thisval);
		return aaa;
};