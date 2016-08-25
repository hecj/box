			
			/*提交验证*/
			$(".cellphone-submit-btn").click(function(){
				var phone_captcha_input = $(".phone-captcha-input").val();
				if (phone_captcha_input == "") {
					$(".phone-captcha-input").parent().siblings(".show").hide();
					$(".phone-captcha-input").parent().siblings(".error").hide();
					$(".phone-captcha-input").parent().siblings(".empty").show();
					$(".phone-captcha-input").css({"border-color":"#f00"});
					return;
				};
				
				if(verifyCode(phone_captcha_input)){
					location ="/newPwd?id="+$("#hidden_phone").text();
					return;
				}else{
					$(".phone-captcha-input").parent().siblings(".show").hide();
					$(".phone-captcha-input").parent().siblings(".error").show();
					$(".phone-captcha-input").parent().siblings(".empty").hide();
					$(".phone-captcha-input").css({"border-color":"#f00"});
					return;
				}
			});
			
			//验证短信
			function verifyCode(val){
				var flag = false;
				if($("#hidden_phone").text().length==11){
					$.ajax({
						type:"POST",
						url:"/verifyCode",
						data:"phone="+$("#hidden_phone").text()+"&code="+val,
						async:false,
						success:function(msg){
							if(msg.code==11){
								flag = true;
							}else{
								flag = false;
							}
						}
					})
				}else{
					alert("手机号错误!");
					flag = false;
				}
				return flag;
			}
			
			/*倒计时*/
			function timedelete(){
				$(".settimeout0").hide().siblings(".settimeout1").show();
				/*$(this).siblings(".double").removeClass("settimeout0").addClass("settimeout1");*/
				var jump = document.getElementById("settimeout"); 
				var timer = 60;
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
			
			//发送短信js
			$(".settimeout0").click(function(){
				if($("#hidden_phone").text().length==11){
					timedelete();
					
					$.ajax({
						type:"POST",
						url:"/sendCode",
						data:"phone="+$("#hidden_phone").text()+"&type=1",
						async:false,
						success:function(msg){
							if(msg.code==200){
								return;
							}else{
								return;
							}
						}
					})
					return;
				}else{
					alert("手机号错误!");
					return;
				}
			});
			
		
			
			//发送邮件js
			jQuery(function(){				
				$("#submit-email").click(function(){
					$.ajax({
						type:"POST",
						async:false,
						url:"/verifyEmail",
						data:"email="+$("#hidden_email").text(),
						success:function(msg){
							if(msg.code==200){
								$(".rp2-form").hide();
								$(".rp2-send").show();
								$.ajax({
									type:"POST",
									async:false,
									url:"/sendEmail",
									data:"email="+$("#hidden_email").text(),
									success:function(message){
										if(message!='true'){
											return;
										}
									}
								})
							}else{
								alert("邮箱验证失败");
								return;
							}
						}
					})
				})
			});
			
			$(document).ready(function(){
				//获取输入类型
				if($("#type").text()==0){
					$(".s-email").css({"display":"none"});
					$(".s-phone").css({"display":"block"});
				}else{
					$(".s-phone").css({"display":"none"});
					$(".s-email").css({"display":"block"});
				}
				/*手机邮箱选择*/
				$("select[class='way-select']").change(function(){
					var way_select = jQuery("select[class=way-select]").val();
					if (way_select == "1") {
						$(".s-email").css({"display":"none"});
						$(".s-phone").css({"display":"block"});
					};
					if (way_select == "2") {
						$(".s-phone").css({"display":"none"});
						$(".s-email").css({"display":"block"});
					};		
				});
				
				/*验证码*/
				$(".phone-captcha-input").on({
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
							$(this).parent().siblings(".show").hide();
							$(this).parent().siblings(".error").hide();
							$(this).parent().siblings(".empty").hide();					
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
			})
			
			/*查看验证邮件跳转到相应的邮箱登录页面*/
			$("#btnSendEmail").click(function(){
				var subString = $("#hidden_email").text().split("@");
				var type = "mail."+subString[1];
				window.location.href="http://"+type;
			});
			