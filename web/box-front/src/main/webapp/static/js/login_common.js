var stanow = " <div class='login-bgc' onclick='hideFilter()'></div> "
				+ " <div class='login-bg-op'></div>"
				+ " <div class='login-wrap'>"
				+ "<div class='erweima'></div>"
					+ "<div class='romateBox'>"
						+ "<div class='login-con'>"
							+ "<div class='login-head'>"
								+ "<ul>"
									+ "<li><a href='javascript:;' class='login_fc'>登录</a></li>"
									+ "<li>｜</li>"
									+ " <li><a href='javascript:;' class='register-open'>注册</a></li>"
								+ "</ul>"
							+ "</div>"
							+ "<div class='login-list'>"
								+ "<div class='login-formgroup'>"
								+ " <label>账号:</label>"
								+ "<span class='login-error-msg' id='msg_username'>账号不存在，请重新输入</span>"
								+"<div class='position_rel'>"
								+ "<input type='text' id='username'/>"
								+"<span class='login_msg01 login_msgP'>请输入手机号／邮箱</span>"
								+"</div>"
							+ "</div>"
							+ "<div class='login-formgroup'>"
								+ "<label>密码:</label>"
								+ "<span class='login-error-msg' id='msg_password'>请输入密码</span>"
								+"<div class='position_rel'>"
								+ "<input type='password' id='password'/>"
								+"<span class='login_msg02 login_msgP'>请输入密码</span>"
								+"</div>"
							+ "</div>"
							+ "<div class='login-choseway'>"
								+ "<div class='lohin-btn-checkbox'>"
								+ "<input type='checkbox' id='autoLogin'>"
								+ "<span>记住密码自动登录</span>"
							+ "</div>"
							+ "<a href='/findPassword'>忘记密码？</a>"
						+ "</div>"
							+ "<div class='login-btn login-sbtn' id='login_btn'>登录</div>"
					+ "</div>"
				+ "</div>"
			+ "</div>"
		+"<div class='login-con-one-code'>"
			+ "<div class='login-head'>"
				+ "<ul>"
					+ "<li class='login_fc'>二维码登录</li>"
				+ "</ul>"
			+ "</div>"
			+ "<div id='wechatDiv'></div>"
			/*+ "<p>请使用微信扫描二维码登录</p>"*/
			/*+ "<p class='login-con-one-code-success'><i class='login-icon'></i><span>登录成功</span></p>"
			+ "<p class='login-con-one-code-error'><i class='login-icon'></i><span>您已取消本次登录</span></p>"*/
		+ "</div>"
		
		+ "<div class='romateBox1'>"
			+ "<div class='login-success'>"
				+ "<div class='login-head'>"
					+ "<ul>"
						+ "<li class='login_fc'>登录成功</li>"
					+ "</ul>"
				+ " </div>"
			+"<div class='login-hp'>"
			+ " <img src='' alt='' id='userhp'>"
		+ " </div>"
		
		+ "<p class='login-name' id='nickname'></p>"
	+ "</div>"
+ "</div>"
		+ "<div class='register-con'>"
			+ "<div class='login-head'>"
				+ "<ul>"
					+ "<li><a href='javascript:;' class='login-open'>登录</a></li>"
					+ "<li>｜</li>"
					+ "<li><a href='javascript:;' class='login_fc'>注册</a></li>"
				+ "</ul>"
			+ "</div>"
		+ "<div class='login-list'>"
			+ "<div class='login-formgroup'>"
				+ " <label>手机号:</label>"
					+ " <span class='login-error-msg' id='msg_phoneEmail'>请输入手机号</span>"
					+"<div class='position_rel'>"
					+ "<input type='text' id='phoneEmail'/>"
					+"<span class='login_msg03 login_msgP'>请输入手机号</span>"
					+"</div>"
			+ "</div>"
			+ "<div class='login-formgroup register-code'>"
				+ "<label>验证码:</label>"
				+ "<span class='login-error-msg' id='msg_register-code01'>请输入验证码</span>"
				+"<div class='position_rel'>"
				+ "<input type='text' class='register-code01' id='register-code01'/>"
				+"<span class='login_msg04 login_msgP'>请输入验证码</span>"
				+"</div>"
				+ "<span class='register-code02' id='sendCode' onclick='sendSMSCode(this)'>获取短信验证码</span>"
			+ "</div>"
		+ " <div class='login-formgroup'>"
			+ "<label>设置密码:</label>"
			+ "<span class='login-error-msg' id='msg_passwordS'>请设置密码</span>"
			+"<div class='position_rel'>"
			+ " <input type='password' id='passwordS'/>"
			+"<span class='login_msg05 login_msgP'>由6-16位字母加数字组成，区分大小写</span>"
			+"</div>"
		+ "</div>"
		+ "<div class='login-formgroup'>"
			+ "<label>确认密码:</label>"
			+ " <span class='login-error-msg' id='msg_passwordE'>请输入确认密码</span>"
			+"<div class='position_rel'>"
			+ "<input type='password' id='passwordE'/>"
			+"<span class='login_msg06 login_msgP'>请再次输入密码</span>"
			+"</div>"
		+ " </div>"
		+ "<div class='login-choseway register-choseway'>"
			+ "<div class='lohin-btn-checkbox lohin-btn-checkbox-mes'>"
				+ "<span class='login-error-msg login-error-msg-noposition' id='msg_checkbox'>请勾选注册并同意《用户注册服务协议》</span>"
				+ "<input type='checkbox' id='checkbox' checked='checked' class='login-msg-pd34'>"
				+ "<span>注册并同意<a href='javascript:;' target='_blank' class='no-fllh'>《用户注册服务协议》</a></span>"
			+ "</div>"
		+ "</div>"
		+ "<div class='login-btn' id='register_btn'>注册</div>"
	+ "</div>   "
+ "</div>"
		+ "<div class='login-foot-wrap'>"
			+ "<div class='login-footer-01'>"
				+ "<div class='login-foot-con'>"
				+ " <ul>"
					+ "<li>使用合作账号登录</li>"
					+ "<li class='login-otherway01'><a href='/OpenUserForQQ/login'></a></li>"
					+ "<li class='login-otherway02'><a href='/OpenUserForWeiBo/login'></a></li>"
					+ "<li class='login-otherway03'><a href='/OpenUserForWeChat/login'></a></li>"
				+ "</ul>" +
		"</div> "  + "</div>" + "</div>"
		+ "</div>";

var loginApp = {
	login : function(back_url) {

		var num = $(".login-bgc").length;
		var str = "";
		if (num == 0) {
			str += stanow;
			$("body").append(str);
		} else if ((num != 0) && ($(".login-bgc").is(":hidden"))) {
			$(".login-con").show();
			$(".login-bg-op").height("308px")
			$(".register-con").hide();
			$("#username").css({
				"border" : "none"
			})
			$("#password").css({
				"border" : "none"
			})
			$("#phoneEmail").css({
				"border" : "none"
			})
			$("#register-code01").css({
				"border" : "none"
			})
			$("#passwordS").css({
				"border" : "none"
			})
			$("#passwordE").css({
				"border" : "none"
			})
			$(".login-bgc , .login-bg-op , .login-wrap").show();

		} else {
			return;
		}

		// 模糊
		$(".bg_wrap").addClass('bg_wrap_02');
		$("body").css({
			"overflow" : "auto"
		});

		// 格式
		$(".login-bgc").height($(document).height());

		$(".login-bgc").show();

		// 二维码生成js
		var obj = new WxLogin(
		{
			id : "wechatDiv",
			appid : webApp.wechat_AppID,
			scope : "snsapi_login",
			redirect_uri : webApp.wechat_redirect_URI,
			state : "",
			style : "",
			href : ""
			//href : webApp.WEBROOT_URL+"static/css/wechat.css"
		});
    
		/* 二维码滑过事件 */
		$(".erweima").hover(function() {
			$(this).addClass('erweima-hover');
			$(".login-con").hide();
			$(".login-con-one-code").show();
			$(".login-bg-op").height("438px");
			$(".login-foot-wrap").hide();
		}, function() {
			$(this).removeClass('erweima-hover');
			$(".login-con").show();
			$(".login-con-one-code").hide();
			$(".login-bg-op").height("308px");
			$(".login-foot-wrap").show();
		});
		/* 登录注册页面切换 */
		/* $(".register-con") */
		$(".register-open").click(function(event) {
			$(".login-con").hide();
			$(".login-wrap").addClass('register-wrap');
			$(".romateBox1").hide();
			$(".login-bg-op").height("460px")
			$(".register-con").stop().show();

			$(".erweima").hide();
			$(".login-foot-wrap").css({
				"bottom" : "0"
			});
		});
		$(".login-open").click(function(event) {

			$(".login-con").show();
			$(".login-wrap").removeClass('register-wrap');
			$(".login-bg-op").height("308px")
			$(".register-con").stop().hide();
			$(".erweima").show();
			$(".login-foot-wrap").css({
				"bottom" : "-50px"
			});
		});

		/* input框样式 */
		$(".login-wrap input").each(function(index, el) {
			$(this).focus(function(event) {
				$(this).css({
					'border' : '1px solid #a1de00',
					'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			}).blur(function(event) {
				$(this).css({
					'border' : '1px solid #d5d5d5'
				});
			});
		});
        /*js模拟placceholder*/
        $("#username").keyup(function(event) {
        	$(".login_msg01").hide();
        }).blur(function(event) {
        	var login_msgval=$("#username").val();
        	if (login_msgval=="") {
			$(".login_msg01").show();
        	}

        });
        $(".login_msg01").click(function(event) {
        	 $("#username").focus();
        });
        $("#password").keyup(function(event) {
        	$(".login_msg02").hide();
        }).blur(function(event) {
        	var login_msgval=$("#password").val();
        	if (login_msgval=="") {
			$(".login_msg02").show();
        	}

        });
        $(".login_msg02").click(function(event) {
        	 $("#password").focus();
        });
        $("#phoneEmail").keyup(function(event) {
        	$(".login_msg03").hide();
        }).blur(function(event) {
        	var login_msgval=$("#phoneEmail").val();
        	if (login_msgval=="") {
			$(".login_msg03").show();
        	}

        });
        $(".login_msg03").click(function(event) {
        	 $("#phoneEmail").focus();
        });
        $("#register-code01").keyup(function(event) {
        	$(".login_msg04").hide();
        }).blur(function(event) {
        	var login_msgval=$("#register-code01").val();
        	if (login_msgval=="") {
			$(".login_msg04").show();
        	}

        });
        $(".login_msg04").click(function(event) {
        	 $("#register-code01").focus();
        });
        $("#passwordS").keyup(function(event) {
        	$(".login_msg05").hide();
        }).blur(function(event) {
        	var login_msgval=$("#passwordS").val();
        	if (login_msgval=="") {
			$(".login_msg05").show();
        	}

        });
        $(".login_msg05").click(function(event) {
        	 $("#passwordS").focus();
        });
        $("#passwordE").keyup(function(event) {
        	$(".login_msg06").hide();
        }).blur(function(event) {
        	var login_msgval=$("#passwordE").val();
        	if (login_msgval=="") {
			$(".login_msg06").show();
        	}

        });
        $(".login_msg06").click(function(event) {
        	 $("passwordE").focus();
        });
		// 登陆时用户名

		$("#username").focus(function() {
			var aname = this.value;
			if (aname == "请输入注册手机号/邮箱" || aname == "") {
				$(this).css({
					'border' : '1px solid #a1de00',
					'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			}
			;
			$("#msg_username").hide();
		});

		$("#username").blur(function() {
			var aname = this.value;
			if (aname == "请输入注册手机号/邮箱" || aname == "") {
				$(this).css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_username").html("请输入账号");
				$("#msg_username").show();
			}
		});

		// 登陆时密码
		$("#password").focus(function() {
			var aname = this.value;
			if (aname == "" || aname == "请输入密码") {
				$(this).css({
					'border' : '1px solid #a1de00',
					'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			};
			
			$("#msg_password").hide();
		});

		$("#password").blur(function() {
			var aname = this.value;
			if (aname == "" || aname == "请输入密码") {
				$(this).css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_password").html("请输入密码");
				$("#msg_password").show();
			}
		});
		//登录时记住密码
		var autoLogin = false;
		$("#autoLogin").click(function() {
			if (!autoLogin) {
				autoLogin = true;
				$("#autoLogin").prop("checked",true);
			} else {
				autoLogin = false;
				$("#autoLogin").prop("checked",false);
			}
		});


		// 绑定登陆事件
		$("#login_btn").click(function() {
			loginForm();
		});
		// 绑定键盘登录
		$(document).keypress(function(event) {
			if (event.keyCode == 13) {

				if ($(".login-con").is(":hidden")) {
					// 注册的输入框都失去焦点
					register();
				} else {
					// 登录的输入框要失去焦点
					loginForm();
				}
			}
		});
		var browser=navigator.appName;
		var b_version=navigator.appVersion;
		var version=b_version.split(";");
		var trim_Version=version[1].replace(/[ ]/g,"");
	
		function loginForm() {
			// 非空校验
			pwdVerify = $("#password").val().length > 0 ? true : false;
			unameVerify = $("#username").val().length > 0 ? true : false;
			if (pwdVerify && unameVerify) {
				// 发送ajax请求
						$.ajax({
							type : "POST",
							async : false,
							url : "/doLogin",
							data : "username=" + $("#username").val()
									+ "&password=" + $("#password").val()
									+ "&isAutoLogin="+autoLogin,
							success : function(msg) {
								if (msg.code == 200) {
									$("#userhp").css("display","inline-block");
									$("#userhp").show();
									if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0"){

											$(".romateBox .login-con").hide();
											$(".romateBox1 .login-success").show();
								      
									}else{
									$(".romateBox .login-con").css({
										"transform" : "rotateY(90deg)",
										"-ms-transform":"rotateY(90deg)",	/* IE 9 */
											"-moz-transform":"rotateY(90deg)", 	/* Firefox */
											"-webkit-transform":"rotateY(90deg)", /* Safari 和 Chrome */
											"-o-transform":"rotateY(90deg)", 

									});
									}
									
									var picture = msg.data.picture;
									if(picture==""||picture==null){
										picture = $("#WEBROOT_URL").text()+"static/images/top-footer/userphoto.png";
									}
									
									$("#userhp").attr("src", picture);
									$("#nickname").text(msg.data.nickname);

									setTimeout(function() {
									
										$(".romateBox1 .login-success").css({
											"transform" : "rotateY(360deg)",
											"-ms-transform":"rotateY(360deg)",	/* IE 9 */
											"-moz-transform":"rotateY(360deg)", 	/* Firefox */
											"-webkit-transform":"rotateY(360deg)", /* Safari 和 Chrome */
											"-o-transform":"rotateY(360deg)", 
											"opacity" : "1"
										})	
										
										$(".login-con").css({
											width : "0",
											height : "0"
										})
										$(".romateBox").css({
											height : "0"
										})
									}, 0)
								
								
									$(".erweima").hide();
									// 3秒之后跳转
									var timer = 3;

									function fun() {
										timer--;
										if (timer > 0) {
											setTimeout(fun, 1000);
										} else {
											if (back_url == undefined) {
												window.location.href = "/";
											} else if(window.location.href.indexOf("findPassword")==-1||window.location.href.indexOf("resetPassword")==-1||window.location.href.indexOf("newPwd")==-1){
												window.location.href = "/";
											}else{	
												window.location.href = back_url;
											}
											return;
										}
									}
									fun();
									return;
								} else if (msg.code == 12) {
									// 密码错误
									$("#password").css({
										'border' : '1px solid #ff0000'
									});
									$("#msg_password").html("账号与密码不匹配，请重新输入");
									$("#msg_password").show();
									return;
								} else if (msg.code == 14) {
									// 用户名不对
									$("username").css({
										'border' : '1px solid #ff0000'
									});
									$("#msg_username").html("账号不存在,请重新输入");
									$("#msg_username").show();
									return;
								} else if (msg.code == 13) {
									// 用户名密码错误
									$("username").css({
										'border' : '1px solid #ff0000'
									});
									$("#msg_username").html("该账号已被锁定");
									$("#msg_username").show();
									return;
								}
							}
						});
			} else if (!pwdVerify && unameVerify) {
				// 密码未写
				$("#password").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_password").html("请输入密码");
				$("#msg_password").show();
			} else if (pwdVerify && !unameVerify) {
				// 用户名未写
				$("username").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_username").html("请输入账号");
				$("#msg_username").show();
			} else {
				// 两个都没写
				$("#password").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_password").html("请输入密码");
				$("#msg_password").show();
				// 用户名未写
				$("#username").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_username").html("请输入账号");
				$("#msg_username").show();
			}
		}
		
		/* 注册检验 */

		// 用户名
		$("#phoneEmail").focus(function() {
			var aname = this.value;
			if (aname == "请输入手机号" || aname == "") {
				$(this).css({
					'border' : '1px solid #a1de00',
					'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			};
			$("#msg_phoneEmail").hide();
		});
		var phoneEmail = false;

		$("#phoneEmail").blur(
				function() {
					var aname = this.value;
					if (aname == "") {
						$(this).css({
							'border' : '1px solid #ff0000'
						});
						$("#msg_phoneEmail").html("请输入手机号");
						$("#msg_phoneEmail").show();
						return;
					} else if (!(/^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\d{8}$/)
							.test(aname)
							|| aname.length != 11) {
						$(this).css({
							'border' : '1px solid #ff0000'
						});
						$("#msg_phoneEmail").html("请输入正确的手机号");
						$("#msg_phoneEmail").show();
						return;

					} else if (!verify(aname)) {
						$(this).css({
							'border' : '1px solid #a1de00',
							'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
						});
						phoneEmail = true;
					} else {
						$(this).css({
							'border' : '1px solid #ff0000'
						});
						$("#msg_phoneEmail").html("该手机号已被占用");
						$("#msg_phoneEmail").show();
						return;
					}
					;
				});

		// 验证码
		$("#register-code01").focus(function() {
			var aname = this.value;
			if (aname == "请输入验证码" || aname == "") {
				$("#register-code01").css({
					'border' : '1px solid #a1de00',
					'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			}
			;
			$("#msg_register-code01").hide();
		});

		var register_code01 = false;
		$("#register-code01").blur(function() {
			var aname = this.value;
			if (aname == "" || aname == "请输入验证码") {
				$("#register-code01").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_register-code01").html("请输入手机验证码");
				$("#msg_register-code01").show();
				return;
			} else if (aname.length != 6 || !/^\d+$/.test(aname)) {
				$("#register-code01").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_register-code01").html("手机验证码为6位数字");
				$("#msg_register-code01").show();
				return;
			} else {
				register_code01 = true;
			}
			;
		});

		// 密码
		$("#passwordS").focus(function() {
			var aname = this.value;
			if (aname == "由6-16位字母加数字组成，区分大小写" || aname == "") {
				$("#passwordS").css({
					'border' : '1px solid #a1de00',
					'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			}
			;
			$("#msg_passwordS").hide();
		});
		
		//输入6-16位字母加数字
		$("#passwordS,#passwordE").bind("propertychange input", function(){
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

		var passwordS = false;
		$("#passwordS").blur(function() {
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "" || aname == "由6-16位字母加数字组成，区分大小写") {
				$("#passwordS").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_passwordS").html("请输入密码");
				$("#msg_passwordS").show();
				return;
			} else if ((alength < 6) || (alength > 16)) {
				// 密码太短
				$("#passwordS").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_passwordS").html("密码长度不符，请重新输入");
				$("#msg_passwordS").show();
				return;
			} else if (substr("",aname,1)==1) {
				// 不能纯数字
				$("#passwordS").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_passwordS").html("由6-16位字母加数字组成，区分大小写");
				$("#msg_passwordS").show();
				return;
			} else {
				passwordS = true;
			}
			;
		});

		// 确认密码
		$("#passwordE").focus(function() {
			var aname = this.value;
			if (aname == "请输入确认密码" || aname == "") {
				$("#passwordE").css({
					'border' : '1px solid #a1de00',
					'boxShadow' : 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			}
			;
			$("#msg_passwordE").hide();
		});

		var passwordE = false;
		$("#passwordE").blur(function() {
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "" || aname == "请输入确认密码") {
				$("#passwordE").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_passwordE").html("请输入确认密码");
				$("#msg_passwordE").show();
				return;
			} 
			//else if ((alength < 6) || (alength > 16)) {
				// 密码太短
//				$("#passwordE").css({
//					'border' : '1px solid #ff0000'
//				});
//				$("#msg_passwordE").html("密码长度为6-16位");
//				$("#msg_passwordE").show();
//				return;
//			} else if (/^\d+$/.test(aname)) {
//				// 不能纯数字
//				$("#passwordE").css({
//					'border' : '1px solid #ff0000'
//				});
//				$("#msg_passwordE").html("密码不能为纯数字");
//				$("#msg_passwordE").show();
//				return;
			 else if(aname != $("#passwordS").val()) {
				// 确认密码和密码不一致
				$("#passwordE").css({
					'border' : '1px solid #ff0000'
				});
				$("#msg_passwordE").html("密码不一致");
				$("#msg_passwordE").show();
			} else {
				passwordE = true;
			}
			;
		});

		// 同意协议click事件
		var isCheck = true;
		$("#checkbox").click(function() {
			if (isCheck) {
				isCheck = false;
				$("#msg_checkbox").show();
			} else {
				isCheck = true;
				$("#msg_checkbox").hide();
			}
		});

		// 注册提交
		$("#register_btn").click(function() {
			register();
		});

		function register() {
			if (passwordE && passwordS && register_code01 && phoneEmail
					&& isCheck) {
				// 全部正确，提交
						$.ajax({
							type : "POST",
							url : "verifyCode",
							data : "code=" + $("#register-code01").val()
									+ "&phone=" + $("#phoneEmail").val(),
							aysnc : false,
							success : function(msg) {
								if (msg.code == 11) {
									// 验证正确
											$.ajax({
												tytype : "POST",
												url : "/save",
												data : "phone="
														+ $("#phoneEmail")
																.val()
														+ "&password="
														+ $("#passwordS").val()
														+ "&passwordE="
														+ $("#passwordE").val(),
												async : false,
												success : function(msg) {
													if (msg.code == 00) {
														alert("服务器繁忙,请稍后重试");
														return;
													} else if (msg.code == -100000) {
														$("#passwordE")
																.css(
																		{
																			'border' : '1px solid #ff0000'
																		});
														$("#msg_passwordE")
																.html("两次输入不一致");
														$("#msg_passwordE")
																.show();
														return;
													} else {
														location = "/personal/getPersonalDetail";
													}

												}
											});
								} else {
									$("#register-code01").css({
										'border' : '1px solid #ff0000'
									});
									$("#msg_register-code01").html("手机验证码输入错误");
									$("#msg_register-code01").show();
									return;
								}
							}
						});
			} else {
				var arrayNum = [ phoneEmail, register_code01, passwordS,
						passwordE, isCheck ];
				$.each(arrayNum, function(n, value) {
					if (value == false) {
						if (n == 0) {
							$("#phoneEmail").css({
								'border' : '1px solid #ff0000'
							});
							if(verify($("#phoneEmail").val())){
								$("#msg_phoneEmail").html("该手机号已被占用");
							}else if($("#phoneEmail").val() == "请输入注册手机号/邮箱" || $("#phoneEmail").val() == ""){
								$("#msg_phoneEmail").html("请输入手机号");
							}else{
								$("#msg_phoneEmail").html("请输入正确的手机号");
							}
							$("#msg_phoneEmail").show();
						} else if (n == 1) {
							$("#register-code01").css({
								'border' : '1px solid #ff0000'
							});
							if($("#register-code01").val.length!=6 && $("#register-code01").val()!="请输入验证码" && $("#register-code01").val()!= ""){
								$("#msg_register-code01").html("手机验证码为6位数字");
							}else if($("#register-code01").val() == "请输入验证码" || $("#register-code01").val() == ""){
								$("#msg_register-code01").html("请输入手机验证码");
							}else{
								$("#msg_register-code01").html("手机验证码输入错误");
							}
							$("#msg_register-code01").show();
						} else if (n == 2) {
							$("#passwordS").css({
								'border' : '1px solid #ff0000'
							});
							if(($("#passwordS").val().length<6||$("#passwordS").val().length>16)&&($("#passwordS").val()!="" && $("#passwordS").val()!="由6-16位字母加数字或符号组成,区分大小写")){
								$("#msg_passwordS").html("密码长度不符，请重新输入");
								$("#msg_passwordS").show();
								return;
							}else if($("#passwordS").val()==""||$("#passwordS").val()=="密码只能由数字加字母组成"){
								$("#msg_passwordS").html("请输入密码");
								$("#msg_passwordS").show();
								return;
							}else{
								$("#msg_passwordS").html("由6-16位字母加数字组成，区分大小写");
								$("#msg_passwordS").show();
								return;
							}
							
						} else if (n == 3) {
							$("#passwordE").css({
								'border' : '1px solid #ff0000'
							});
							if($("#passwordE").val()==""||$("#passwordE").val()=="请输入确认密码"){
								$("#msg_passwordE").html("请输入确认密码");
							}else if($("#passwordE").val()!=$("#passwordS").val()){
								$("#msg_passwordE").html("密码不一致");
							}
							$("#msg_passwordE").show();
						} else {
							$("#msg_checkbox").show();
						}
					}
				});
			}
		}
	},

	verifyLogin : function(back_url) {
		// 发送ajax请求
		$.ajax({
			type : "POST",
			url : "/isSession",
			async : false,
			success : function(msg) {
				if (msg.code == 11) {
					window.location.href = back_url;
				} else {
					// window.location.href="/projectapply/agreement";
					// 没有session,加载登录js
					// $.getScript("/static/js/login_common.js",function(){
					// loginApp.login(back_url);
					// });

					loginApp.login(back_url);
					return;
				}
			}
		});
	}
}

function hideFilter() {
	$(".bg_wrap").removeClass('bg_wrap_02');
	$("#username").val("");
	$("#password").val("");
	$("#phoneEmail").val("");
	$("#register-code01").val("");
	$("#passwordS").val("");
	$("#passwordE").val("");
	$(".login-con").hide();
	$(".register-con").hide();
	$("#msg_username").hide();
	$("#msg_password").hide();
	$("#msg_phoneEmail").hide()
	$("#msg_register-code01").hide()
	$("#msg_passwordS").hide()
	$("#msg_passwordE").hide()
	$(".login-bgc , .login-bg-op , .login-wrap").hide();
};

function verify(val) {
	// 判断账户是否存在
	var flag = false;
	$.ajax({
		type : "POST",
		url : "/verifyPhoneOrEmail",
		data : "phoneOrEmail=" + val,
		async : false,
		success : function(msg) {
			if (msg.code == 200) {
				flag = true;
			} else {
				flag = false;
			}
		}
	});
	return flag;
};


/**
 * 处理nologin非法访问
 */
 $(function(){
	var login = common.getUrlParam("login");
	if(login == "nologin"){
		var back_url = common.getUrlParam("back_url");
		if(back_url != null && back_url.length > 0){
			loginApp.login(back_url);
		}
	}
 });

	

 

 
//发送验证码
	function sendSMSCode(component) {
		
		phoneId = $("#phoneEmail").val();
		if (phoneId == '' ) {
			$("#phoneEmail").css({
				'border' : '1px solid #ff0000'
			});
			$("#msg_phoneEmail").html("请输入手机号");
			$("#msg_phoneEmail").show();
			return;
		}else if(!checkPhone(phoneId)){
			$("#phoneEmail").css({
				'border' : '1px solid #ff0000'
			});
			$("#msg_phoneEmail").html("请输入正确的手机号");
			$("#msg_phoneEmail").show();
			return;
			
		}else if(verify(phoneId)){
			$("#phoneEmail").css({
				'border' : '1px solid #ff0000'
			});
			$("#msg_phoneEmail").html("该手机号已被占用");
			$("#msg_phoneEmail").show();
			return;
		}
		
		$.ajax({
			url : "/sendCode",
			type : "POST",
			data : "phone=" + phoneId,
			async : false,
			success : function(data) {
//				// 判断发送结果
				if (data.code == 200) {
					//发送成功
					$(component).attr("onclick", null);
				}else{
					$(component).attr("onclick", func);
				}
			}
		});
		/* 倒计时 */
		function setTime(bid, val , func) {
			var jump = $("#sendCode");
			var timer = val;
			fun();
			function fun() {
				timer--;
				jump.html(timer + "秒后重新获取");
				if (timer > 0) {
					setTimeout(fun, 1000);
					 
				} else {
					jump.html("获取短信验证码");
					jump.attr("onclick", func);
				}
				;
			}
		};
		if($("#sendCode").text()=="获取短信验证码"){
			setTime("", 60 , "sendSMSCode(this);");
		}
	};

/***
 * 
 * function setCookie(name, value) {
	var time = new Date();
	time.setSeconds(time.getSeconds() + 1);
	document.cookie = name + "=" + escape(value) + ";expires=" + time.toGMTString();
}
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1)
				c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return null;
}

 * 
 * 
 */
	


