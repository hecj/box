			function check(){
				var flag=true;
				if($("#paypwd").val()!=$("#paypwd2").val()){
					window.alert("两次输入密码不一致");
					return false;
				};
				var phone = $("#phone").val();
				var code = $("#code").val();				
				$.ajax({
					type:"POST",
					url:"/verifyCode",
					data:"phone="+phone+"&code="+code,
					async: false,
					success:function(msg){							
						if(msg.code==00){
							alert("信息超时");
							flag = false;
						}else if(msg.code==01){
							alert("验证码错误");
							flag = false;
						}else{
							alert("验证成功");
							flag = true;
						}
					}
				});	
				return flag;
			};
			
			jQuery(function(){			
				$("#sendCode").click(function(){
					$.ajax({
						type:"POST",
						url:"/sendCode",
						data:"phone="+$("#phone").val(),
						success:function(msg){
							if(msg){
								alert("已发送");
							}else{
								alert("发送失败");	
							}
						}
					})
					
				});
			});
				
			var countdown = 60;
			
			function settime(val){
				if(countdown==0){
					val.removeAttribute("disabled");
					val.value="免费获取验证码";
					countdown = 60;
				}else{
					val.setAttribute("disabled",true);
					val.value="重新发送("+countdown+")";
					countdown--;
					setTimeout(function() {
               			settime(val)
            		},
           			1000)
				}	
				
			}