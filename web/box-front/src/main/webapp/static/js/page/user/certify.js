$(document).ready(function(){
	
	$(".personal-menu ul li").removeClass("current");
	$(".personal-menu ul li:eq(2)").attr("class","current");
	
	
//	 //上传照片
//	jFileUpLoad({
//	     submitBtn: $("#selectPicture"),
//	     uploadurl: '/upload/uploadFile',
//	     uploadname: 'uploadFile',
//	     //上传成功后回调
//	     complete: function (response) {
//	    	 var data = $.parseJSON(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
//	    	 if(data.code == 200){
//	    		 $("#prePicture").attr("src",data.data.file_url);
//	    		 $("#idphoto").val(data.data.file_url);
//	    		 $(".ID-photo-des").removeClass("ID-photo-des-icon");
//	    	 }else{
//	    		 alert(data.message);
//	    	 }
//	     },
//	     //点击提交未上传时回调
//	     beforeUpLoad: function () {
//	    	 // 判断文件名
//	    	 var file_name = $(this)[0].value;
//	    	 var limitSize = 5 * 1024 * 1024;
//	    	 //判断文件大小
//	    	 var size = this.files[0].size;
//	    	 if(size > limitSize){
//	    		 alert("文件大小超限");
//	    		 return false;
//	    	 }
//	     },
//	     //点击提交上传后回调
//	     afterUpLoad: function () {
//	    	 
//	     }
//	});
	
	// 实名认证
	/*radio*/
	$(".gender .radioc").click(function(event) {
		$(".ps-empty").hide();
		$(this).addClass('current').siblings().removeClass('current');
		
	});
	
	
	// 手机邮箱
	$("input[name=phone-number],input[name=mailbox-number]").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00","color":"#000"});
		},
		blur:function(){
			//TODO手机号邮箱
			
			$(this).css({"border-color":"#c7c7c7"});
		}
	});
	// 姓名
	$("input[name=realname]").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00","color":"#000"});
			$("input[name=realname]").parent().siblings(".pb-empty").hide();
		},
		blur:function(){
			var real_name = $("input[name=realname]").val();
			if (real_name == "") {
				$(this).css({"border-color":"#f00"});
				$("input[name=realname]").parent().siblings(".pb-empty").show();
			} else {
				$(this).css({"border-color":"#c7c7c7"});
				$("input[name=realname]").parent().siblings(".pb-empty").hide();
			};
		}
	});	
	
	// 身份证号
	$("input[name=ID-number]").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00","color":"#000"});
			$("input[name=ID-number]").parent().siblings(".pb-error").hide();
			$("input[name=ID-number]").parent().siblings(".pb-empty").hide();
		},
		blur:function(){
			var ID_number = $("input[name=ID-number]").val();
			if (ID_number == "") {
				$(this).css({"border-color":"#f00"});
				$("input[name=ID-number]").parent().siblings(".pb-empty").show();
				$("input[name=ID-number]").parent().siblings(".pb-error").hide();			
			} else {
				var result = IDNumber(ID_number);
				if (!result) {
					$(this).css({"border-color":"#f00"});
					$("input[name=ID-number]").parent().siblings(".pb-empty").hide();
					$("input[name=ID-number]").parent().siblings(".pb-error").show();
				} else {
					$(this).css({"border-color":"#c7c7c7"});
					$("input[name=ID-number]").parent().siblings(".pb-error").hide();
					$("input[name=ID-number]").parent().siblings(".pb-empty").hide();
				}
			};
		}
	});	
	
	// 绑定手机号
	$("#go-bindphone").click(function(){
		if($(this).text()=="绑定手机号"){
			$(".pub-mask-layer,.pub-popup").show();		
			$(".sc-bind-phone").show();
		}
	});

	//提交审核
	$("#submit_btn").click(function(){
		var flag = true;
		// 真实姓名
		var real_name = $("input[name=realname]").val();
		if (real_name == "") {
			$("input[name=realname]").parent().siblings(".pb-empty").show();
			$("input[name=realname]").css({"border-color":"#f00"});
			flag = false;
		};
		// 身份ID
		var ID_number = $("input[name=ID-number]").val();
		if (ID_number == "") {
			$("input[name=ID-number]").parent().siblings(".pb-empty").show();
			$("input[name=ID-number]").parent().siblings(".pb-error").hide();
			$("input[name=ID-number]").css({"border-color":"#f00"});
			flag = false;
		} else if(!IDNumber(ID_number)){
			$("input[name=ID-number]").parent().siblings(".pb-empty").hide();
			$("input[name=ID-number]").parent().siblings(".pb-error").show();
			$("input[name=ID-number]").css({"border-color":"#f00"});
			flag = false;
		}
		
		//性别
		var sex = $("input[type='radio']:checked").val();
		if(sex!=1&&sex!=2){
			$(".ps-empty").show();
			flag = false;
		}
		
		//身份照片
		var idphoto = $("#idphoto").val();
		if(idphoto==""){
			$(".ID-photo-des").addClass("ID-photo-des-icon");
			flag = false;
		}
		
		//是否有手机
		var phone = $(".phone-number").text();
		if(phone==""||phone=="绑定手机号"){
			$("#box-alert .feedback-success-con p").text("请先绑定手机号再进行实名认证!");
			$("#box-alert").show();
			flag = false;
		}
		
		if(flag){
			$.ajax({
				url : "/usercertify/doCertify",
				type : "POST",
				dataType : "json",
				data : $('#certify').serialize(),
				async:false,
				success : function(data) {
					if (data.code == 200) {
						$("#box-alert .feedback-success-con p").text("保存成功");
						isJump = true;
					}else if(data.code == 404){
						$("#box-alert .feedback-success-con p").text("该身份证已被占用");
						$("#idphoto").val("");
						$("#prePicture").attr("src","/static/images/cf-personalcenter-personalsetting/img1.png");
						$(".ID-photo-des").addClass("ID-photo-des-icon");
						$(".ID-photo-des").removeClass("ID-photo-des-success");
					}else {
						$("#box-alert .feedback-success-con p").text("保存失败");
						$("#idphoto").val("");
						$("#prePicture").attr("src","/static/images/cf-personalcenter-personalsetting/img1.png");
						$(".ID-photo-des").addClass("ID-photo-des-icon");
						$(".ID-photo-des").removeClass("ID-photo-des-success");
					}
				}
			});
			$("#box-alert").show();
		}
	});
	
				
	//再次提交审核
	$("#submit_btn120").click(function(){
		var flag = true;
		// 真实姓名
		var real_name = $("input[name=realname]").val();
		if (real_name == "") {
			$("input[name=realname]").parent().siblings(".pb-empty").show();
			$("input[name=realname]").css({"border-color":"#f00"});
		};
		// 身份ID
		var ID_number = $("input[name=ID-number]").val();
		if (ID_number == "") {
			$("input[name=ID-number]").parent().siblings(".pb-empty").show();
			$("input[name=ID-number]").parent().siblings(".pb-error").hide();
			$("input[name=ID-number]").css({"border-color":"#f00"});
			flag = false;
		} else if(!IDNumber(ID_number)){
			$("input[name=ID-number]").parent().siblings(".pb-empty").hide();
			$("input[name=ID-number]").parent().siblings(".pb-error").show();
			$("input[name=ID-number]").css({"border-color":"#f00"});
			flag = false;
		}
		
		//性别
		var sex = $("input[type='radio']:checked").val();
		if(sex!=1&&sex!=2){
			//TODO
			//请选择性别
			$("#ps-empty2").show();
			flag = false;
		}
		
		//身份照片
		var idphoto = $("#idphoto").val();
		if(idphoto==""){
			$(".ID-photo-des").addClass("ID-photo-des-icon");
			flag = false;
		}
		
		//是否有手机
		var phone = $(".phone-number").text();
		if(phone==""||phone=="绑定手机号"){
			$("#box-alert .feedback-success-con p").text("请先绑定手机号再进行实名认证!");
			$("#box-alert").show();
			flag = false;
		}
		
		if(flag){
			$.ajax({
				url : "/usercertify/doCertify",
				type : "POST",
				dataType : "json",
				data : $('#encertify').serialize(),
				async:false,
				success : function(data) {
					$(".feedback-success .feedback-head h6").text("提示信息");
					if (data.code == 200) {
						$(".feedback-success-con p").text("保存成功");
						isJump = true;
					}else if(data.code == 404){
						$(".feedback-success-con p").text("该身份证已被占用");
						$("#idphoto").val("");
						$("#prePicture").attr("src","/static/images/cf-personalcenter-personalsetting/img1.png");
						$(".ID-photo-des").addClass("ID-photo-des-icon");
						$(".ID-photo-des").removeClass("ID-photo-des-success");
					}else {
						$(".feedback-success-con p").text("保存失败");
						$("#idphoto").val("");
						$("#prePicture").attr("src","/static/images/cf-personalcenter-personalsetting/img1.png");
						$(".ID-photo-des").addClass("ID-photo-des-icon");
						$(".ID-photo-des").removeClass("ID-photo-des-success");
					}
				}
			});
			$(".feedback-success").show();
		}
	})
	
	


	function IDNumber(gets){
		var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
		var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;		
		if (gets.length == 15) {   
			return isValidityBrithBy15IdCard(gets);   
		}else if (gets.length == 18){   
			var a_idCard = gets.split("");// 得到身份证数组   
			if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {   
				return true;   
			}   
			return false;
		}
		return false;

			function isTrueValidateCodeBy18IdCard(a_idCard) {   
				var sum = 0; // 声明加权求和变量   
				if (a_idCard[17].toLowerCase() == 'x') {   
					a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
				}   
				for ( var i = 0; i < 17; i++) {   
					sum += Wi[i] * a_idCard[i];// 加权求和   
				}   
				valCodePosition = sum % 11;// 得到验证码所位置   
				if (a_idCard[17] == ValideCode[valCodePosition]) {   
					return true;   
				}
				return false;   
			}

			function isValidityBrithBy18IdCard(idCard18){   
				var year = idCard18.substring(6,10);   
				var month = idCard18.substring(10,12);   
				var day = idCard18.substring(12,14);   
				var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
				// 这里用getFullYear()获取年份，避免千年虫问题   
				if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
					return false;   
				}
				return true;   
			}

			function isValidityBrithBy15IdCard(idCard15){   
				var year =  idCard15.substring(6,8);   
				var month = idCard15.substring(8,10);   
				var day = idCard15.substring(10,12);
				var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
				// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
				if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
					return false;   
				}
				return true;
			}
	}

})
	
	var isJump = false;
	$(".feedback-close ,.feedback-submit").click(function(event) {
		if(isJump){
			window.location.href="/usercertify/certify";
		}else{
			$(".feedback-success").hide();
		}
	});
	
	$(".popup-close").click(function(event){
		$("input[name=tellphone]").val("");
		$("input[name=dynamic-code]").val("");
		$("input[name=tellphone]").css({"border-color":"#d5d5d5"});
		$("input[name=dynamic-code]").css({"border-color":"#d5d5d5"});
		$(".pb-sign").hide();
		$(".sc-bind-phone").hide();
	});
	
	//绑定手机号
	$("input[name='tellphone']").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			$(this).parent().siblings(".pb-sign").hide();
		},
		blur:function(){
			var tellphone =aboutTrim($("input[name='tellphone']").val());
			if (tellphone == ""||tellphone=="请输入注册手机号") {
				$(this).css({"border-color":"#f00"});
				$("input[name='tellphone']").parent().siblings(".pb-sign").text("手机号不能为空!");
				$("input[name='tellphone']").parent().siblings(".pb-sign").show();	
			}else{
				$(this).css({"border-color":"#d5d5d5"});
			}
		}
	});	
	
	//验证码输入框
	$("input[name='dynamic-code']").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			$(this).parent().siblings(".pb-sign").hide();
		},
		blur:function(){
			var tellphone =aboutTrim($("input[name='dynamic-code']").val());
			if (tellphone == "") {
				$(this).css({"border-color":"#f00"});
				$("input[name='dynamic-code']").parent().siblings(".pb-sign").text("动态码不能为空!");
				$("input[name='dynamic-code']").parent().siblings(".pb-sign").show();	
			}else{
				$(this).css({"border-color":"#d5d5d5"});
			}
		}
	});	
	
	//去除空格
	function aboutTrim(thisval){
		aaa = $.trim(thisval);
		return aaa;
	};
	
	//手机号绑定时获取短信验证码
	function sc_tellphone(component) {
	    var phone = $("input[name='tellphone']").val();
	    var ashow = $(component).parent().parent().siblings(".tellphone").children(".pb-empty");
	    if($.trim(phone).length == 0){
	    	$(ashow).show().css({"color":"#f00"}).text("手机号不能为空!");
			$("input[name='tellphone']").css({"border-color":"#f00"});
	    }else {
	    	//手机号格式正确
	    	if(checkPhone(phone)){
	    		//查询手机号是否绑定
	    		$.ajax({
	    			type:"POST",
	    			url:"/verifyPhone",
	    			data:"phone="+phone,
	    			success:function(msg){
	    				if(msg.code==0){
	    					//手机号未绑定
	    					$(ashow).hide();
	    			    	SendDynamicCode(component, 60, "sc_tellphone(this);");
	    			        $.ajax({
	    			            type: "POST",
	    			            url: "/sendCode",
	    			            data:"phone=" + phone,
	    			            success: function(msg) {
	    			                if (msg) {
	    			                	
	    			                } else{
	    			                	alert("发送失败");
	    			                }
	    			            }
	    			        });
	    				}else{
	    					//手机号已绑定
	    					$(ashow).show().css({"color":"#f00"}).text("手机号已存在!");
	    					$("input[name='tellphone']").css({"border-color":"#f00"});
	    				}
	    			}
	    		})
		    	
	    	} else {
		    	$(ashow).show().css({"color":"#f00"}).text("请输入正确的手机号!");
				$("input[name='tellphone']").css({"border-color":"#f00"});
		    }
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
	
	//绑定按钮
	$("input[name='tellphone-subbtn']").click(function(event){
		var telephone = aboutTrim($("input[name='tellphone']").val());
		var dynamic_code = aboutTrim($("input[name='dynamic-code']").val());
		var isTrue = true;
		if (telephone == ""||telephone=="请输入注册手机号") {
			$("input[name='tellphone']").css({"border-color":"#f00"});
			$("input[name='tellphone']").parent().siblings(".pb-sign").text("手机号不能为空!");
			$("input[name='tellphone']").parent().siblings(".pb-sign").show();
			isTrue = false;
		}else if(!checkPhone(telephone)){
			$("input[name='tellphone']").parent().siblings(".pb-sign").text("请输入正确的手机号!");
			$("input[name='tellphone']").parent().siblings(".pb-sign").show();	
			$("input[name='tellphone']").css({"border-color":"#f00"});
			isTrue = false;
		}
		
		if(dynamic_code==""){
			$("input[name='dynamic-code']").css({"border-color":"#f00"});
			$("input[name='dynamic-code']").parent().siblings(".pb-sign").text("动态码不能为空!");
			$("input[name='dynamic-code']").parent().siblings(".pb-sign").show();
			isTrue = false;
		}
		
		if(isTrue){
			//验证手机验证码是否正确
			$.ajax({
		         type: "POST",
		         url: "/verifyCode",
		         data:"phone=" + telephone + "&code=" + dynamic_code,
		         async: false,
		         success: function(msg) {
		             if (msg.code == 11) {
		                 $.ajax({
		                     type: "POST",
		                     url: "/bindPhone",
		                     data:"phone=" + telephone,
		                     async: false,
		                     success: function(msg) {
		                         if (msg == 'true') {
		                        	 //清空原有框里的值
		                        	 var $inputPhone =  $("input[name='tellphone']");
		                        	 $inputPhone.val("");
		                        	 $("input[name='dynamic-code']").val("");
		                             //动态码倒计时停止，取消setTimeout
		                             var $button = $inputPhone.parent().parent().siblings(".dynamic-code").children().children(".settimeout");
		                             clearTimeout(t);
		                             $button.removeClass("current").text("获取短信验证码");
		                             $button.attr("onclick", "sc_tellphone(this);");
		                             $(".pub-popup-in").hide();
		                             $("#go-bindphone").text(telephone.substring(0,3)+"****"+telephone.substring(7));
		                             $(".feedback-success-con p").text("保存成功");
		                             $("#box-alert").show();
		                         }else {
		                        	 $(".pub-popup-in").hide();
		                             $(".feedback-success-con p").text("保存失败");
		                             $("#box-alert").show();
		                         }
		                     }
		                 })
		             } else {
		            	 $("input[name='dynamic-code']").parent().siblings(".pb-sign").show().css({"color":"#f00"}).text("动态码错误");
		            	 $("input[name='dynamic-code']").css({"border-color":"#f00"});
		             }
		         }
		     })
		}
	});
	
	
	// 上传封面图片
	function upload_convert(){
		$("#uploadFile").click();
	}

	function upload_convert_ajax(){
		
		// 判断文件名
		 var file_name = $("#uploadFile")[0].value;
		 if(!FileUtil.isPicture(file_name)){
			 alert("请上传合法的图片");
			 return false;
		 }
		 
		$.ajaxFileUpload({
		     url: '/upload/uploadFile2', //用于文件上传的服务器端请求地址
		     secureuri: false, //是否需要安全协议，一般设置为false
		     fileElementId: 'uploadFile', //文件上传域的ID
		     dataType: 'json', //返回值类型 一般设置为json
		     success: function (data, status) {

		    	 if(data.code == 200){
		    		 $("#prePicture").attr("src",data.data.file_url);
		    		 $("#idphoto").val(data.data.file_url);
		    		 $(".ID-photo-des").removeClass("ID-photo-des-icon");
		    		 $(".ID-photo-des").addClass("ID-photo-des-success");
//		    		 $("#upload_cover_div dt img").attr("src",data.data.file_url);
//		    		 $("#upload_cover_div dt input[name='project.cover_image']").val(data.data.file_name);
//		    		 $("#upload_cover_div dd .show_red").hide();
//		    		 $("#upload_cover_div dd .show_green").show();
//		    		 $("#upload_cover_div .cover").css({"display":"block"});
		    	 } else{
		    		 alert(data.message);
		    	 }
		     },
		     error: function (data, status, e) {
		    	 alert(e);
		     }
		});
	}
