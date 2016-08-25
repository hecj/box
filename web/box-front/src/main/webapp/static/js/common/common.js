/**
 * ajax登录拦截
 * by HECJ
 */
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		if (XMLHttpRequest.status == 999) {
			//location.href = "/login?back_url="+encodeURIComponent(window.location.href);
			loginApp.login(location.href);
		}
	}
});

function funSelect(){
	var value = $.trim($("#searchText").val());
	if(value.length > 0){	
		location="/project/findValue?search="+value;
	}else{
		location = "/project";
	}
}


function checkPhone(value){
	var reg = /^0?(13[0-9]|15[012356789]|18[02356789]|14[57]|17[07])[0-9]{8}$/;
	if(reg.test(value)){
		return true;
	}else{
		return false;
	}
}

function checkEmail(value){
	var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	if(reg.test(value)){
		return true;
	}else{
		return false;
	}
}


	/**
	 * 工具函数
	 */
	var common = common || {};
	
	/*
	 * 验证手机号合法
	 */
	common.isMobil = function(s) {
		var patrn = /^0?(13[0-9]|15[012356789]|18[02356789]|14[57]|17[07])[0-9]{8}$/;
		if (!patrn.test(s)) {
			return false;
		}
		return true;
	} 
	
	/*
	 * 获取url中的参数
	 */
	common.getUrlParam = function(name) {
	     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	     var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	     if (r != null) return unescape(r[2]); return null; //返回参数值
	 }
	
	/**
	 * 提示框
	common.alert = function(title,message){
		
		var text = '<div class="pay-way-wrap"></div>'+
					'<div class="prompt-wrap" id="common-alert">'+
						'<div class="prompt-head">'+
							  '<h6>'+title+'</h6>'+
							  '<a href="javascript:;" class="prompt-close">X</a>'+
						'</div>'+
						'<div class="prompt-con">'+
							'<p>'+message+'</p>'+
						'</div>'+
						'<div class="prompt-submit-wrap">'+
							  ' <input type="submit" value="确定" class="prompt-submit-btn-green prompt-submit-btn">'+
							  '<input type="submit" value="取消" class="prompt-submit-btn-gray prompt-submit-btn" style="">'+
						'</div>'+
					'</div>';
		$("body").append(text);
		$("#common-alert").show();
	}
	*/
	
	//邮箱跳转路径map
	function gotoEmail(email){
	   redirect= email.split('@')[1];
	    redirect=redirect.toLowerCase();
	    if(redirect=='163.com'){
	        return 'mail.163.com';
	    }else if(redirect=='vip.163.com'){
	        return 'vip.163.com';
	    }else if(redirect=='126.com'){
	        return 'mail.126.com';
	    }else if(redirect=='qq.com'||redirect=='vip.qq.com'||redirect=='foxmail.com'){
	        return 'mail.qq.com';
	    }else if(redirect=='gmail.com'){
	        return 'mail.google.com';
	    }else if(redirect=='sohu.com'){
	        return 'mail.sohu.com';
	    }else if(redirect=='tom.com'){
	        return 'mail.tom.com';
	    }else if(redirect=='vip.sina.com'){
	        return 'vip.sina.com';
	    }else if(redirect=='sina.com.cn'||redirect=='sina.com'){
	        return 'mail.sina.com.cn';
	    }else if(redirect=='tom.com'){
	        return 'mail.tom.com';
	    }else if(redirect=='yahoo.com.cn'||redirect=='yahoo.cn'){
	        return 'mail.cn.yahoo.com';
	    }else if(redirect=='tom.com'){
	        return 'mail.tom.com';
	    }else if(redirect=='yeah.net'){
	        return 'www.yeah.net';
	    }else if(redirect=='21cn.com'){
	        return 'mail.21cn.com';
	    }else if(redirect=='hotmail.com'){
	        return 'www.hotmail.com';
	    }else if(redirect=='sogou.com'){
	        return 'mail.sogou.com';
	    }else if(redirect=='188.com'){
	        return 'www.188.com';
	    }else if(redirect=='139.com'){
	        return 'mail.10086.cn';
	    }else if(redirect=='189.cn'){
	        return 'webmail15.189.cn/webmail';
	    }else if(redirect=='wo.com.cn'){
	        return 'mail.wo.com.cn/smsmail';
	    }else if(redirect=='139.com'){
	        return 'mail.10086.cn';
	    }else {
	       return 'mail.' + redirect;
	    }
	};
	  /*意见反馈*/
	  $(function() {
	  	   $(".pay-way-wrap").height($(document).height());
			  
		   $(".feedbackTextArea").keyup(function(event) {
		    	$(this).siblings('.user_msg01').stop().hide();
		    }).blur(function(event) {
		        var seacherVal=$(".feedbackTextArea").val();
		 
		        if (seacherVal =="" ) {
		        $(this).siblings('.user_msg01').stop().show();
		        }
		 
		    });   
		    $(".feedback-txt").keyup(function(event) {
		    	$('.user_msg02').stop().hide();
		    }).blur(function(event) {
		        var seacherVal=$(".feedback-txt").val();
		 
		        if (seacherVal =="" ) {
		        $('.user_msg02').stop().show();
		        }
		 
		    });
		    $(".feedbackTextArea").siblings('.user_msg01').click(function(event) {
		    	 $(".feedbackTextArea").focus();
		    	
		    }); 
		    $('.user_msg02').click(function(event) {
		    	 $(".feedback-txt").focus();
		    	
		    });
    });
	
//	$("#feedback").click(function() {
//		alert(123);
//		$(".pay-way-wrap").show();
//		$(".feedback-box").show();
//
//	});
//	
	
	$(function(){
		$(".pay-way-wrap").height($(document).height());
		
		$(".feedback").click(function() {
			$(".pay-way-wrap").show();
			$(".user_msg01").show()
			$(".feedback-box").show();
	        $(".user_msg01").show();
	        $(".feedback-err-msg").hide();
		});
		$(".cf-list-con-wrap:last").css({
			'paddingBottom': '60px'
		});
		$(".feedback-close").click(function(event) {
			$(".feedbackTextArea").val("");
			$(".feedback-txt").val("");
			$(".pay-way-wrap").hide();
			$(".feedback-box").hide();
		});
		
		$(".feedbackTextArea").focus(function(event) {
			$(".feedback-err-msg").hide();
		});
		
		
		$("#btn_submit").click(function(event) {
			var txtVal=$(".feedbackTextArea").val();
			txtVal = $.trim(txtVal);
			if (txtVal.length==0) {
				$(".feedback-err-msg").text("请输入意见反馈内容！");
				$(".feedback-err-msg").show();
			} else if(txtVal.length > 1000){
				$(".feedback-err-msg").text("意见反馈不能超过1000字！");
				$(".feedback-err-msg").show();
			} else {
				$.ajax({
					type:"POST",
					url:"/userFeedBack/saveFeedBack",
					data:"content="+txtVal+"&remark="+$(".feedback-txt").val(),
					async:false,
					success:function(msg){
						if(msg.code==200){
							$(".feedback-err-msg").hide();
							$(".feedback-box").hide();
							$(".feedback-success").show();
						}else{
							$(".feedback-err-msg").hide();
							$(".feedback-box").hide();
							$(".feedback-success").show();
						}
					}
				});
			};
		});
		$(".feedback-close-small , .feedback-submit-small").click(function(event) {
			$(".feedback-success").hide();
			$(".pay-way-wrap").hide();
			$(".feedbackTextArea").val("");
			$(".feedback-txt").val("");
		});
		
		$("#box-alert .feedback-submit-wrap .feedback-submit").click(function(event){
			if($("#box-alert .feedback-success-con p").text()=="请勾选注册并同意《用户注册服务协议》"||$("#box-alert .feedback-success-con p").text()=="该身份证已被占用"){
				return null;
			}else{
				window.location.reload();
			}
		});
		
	});
	//确定页面的提示
	function feedbackSuccess(txt){
		$("#box-alert").find("p").text(txt);
		$("#box-alert").show();
		$(".pay-way-wrap").show();
	}
