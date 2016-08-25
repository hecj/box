			var flag = false;
			$("#ensureBtn").click(function check(){
				
				if($("#phone").val().length==0){
					alert("请输入手机号");
					flag = false;
					return;
				}		
				
				if($("#code").val().length==0){
					alert("请输入验证码");
					flag = false;
					return;
				}
				
				if($("#password").val().length==0){
					alert("请输入密码");
					flag = false;
					return;
				}
				
				if($("#password2").val().length==0){
					alert("请输入确认密码");
					flag = false;
					return;
				}
				
				if($("#password").val()!=$("#password2").val()){
					alert("两次密码输入不一致");
					flag = false;
				}
				
				if(flag){
					$.ajax({
						type:"POST",
						url:"/verifyCode",
						data:"phone="+$("#phone").val()+"&code="+$("#code").val(),
						async:false,
						success:function(msg){
							if(msg.code==00){
								alert("信息超时");
							}else if(msg.code==01){
								alert("验证码错误");
							}else{
								$.ajax({
									type:"POST",
									url:"/save",
									data:"phone="+$("#phone").val()+"&password="+$("#password").val(),
									async:false,
									success:function(msg){
										if(msg.code==00){
											alert("服务器繁忙,请稍后重试");
											location = "/";
										}else{
											alert("注册成功");
											location = "/personal/getPersonalDetail";
										}
									}
								})
							}
						}
					})
				}

			})
			
			jQuery(function(){				
				$("#sendCode").click(function(){
					if(flag&&$("#phone").val().length==11){
						$.ajax({
							type:"POST",
							async:false,
							url:"/sendCode",
							data:"phone="+$("#phone").val(),
							success:function(msg){
								if(msg.code==200){
									alert(msg.message);
								}else{
									alert(msg.message);	
								}
							}
						})
						return;
					}else if(flag&&$("#phone").val().length!=11){
						alert("手机号错误!");
						return;
					}else{
						alert("该手机号已被注册,请登录");
						return;
					}
				});
			});
			
			//设置1分钟
			function settime(val, countdown){
				if($("#phone").val().length==11&&flag){
					if(countdown==0){
						val.removeAttribute("disabled");
						val.value="免费获取验证码";
						countdown = 60;
					}else{
						val.setAttribute("disabled",true);
						val.value="重新发送("+countdown+")";
						countdown--;
						setTimeout(function() {
							settime(val, countdown)
						},
						1000)
					}					
				}
			}
			//手机号 失去焦点事件
			$("#phone").blur(function(){
				var phone = this.value;
				var isCheck = checkPhone(phone);
				if(isCheck){
					$.ajax({
						type:"POST",
						url:"verifyPhone",
						async:false,
						data:"phone="+phone,
						success:function(msg){
							if(msg.code==00){
								flag = true;
								return;
							}else{
								alert("该手机号已被注册,请登录");
								flag = false;
								return;
							}
							
						}
					})
				}
			});
			
			function checkPhone(phone){
				var reg = /^0?(13[0-9]|15[012356789]|18[02356789]|14[57]|17[07])[0-9]{8}$/;
				if(reg.test(phone)){
					return true;
				}else{
					alert("手机号不合法");
					return false;
				}
			}
		