	$("#username").blur(
		function(){
			loseBlur($("#username").val());
		}
	);
	function loseBlur(username) {
		var isCheck = isPhoneOrEmail(username);
		var flag = false;
		if (isCheck != 2) {
			$.ajax({
				type : "POST",
				async: false,
				url : "/verifyPhoneOrEmail",
				data:"phoneOrEmail="+username+"&type="+isCheck,
				success : function(msg) {
					if (msg.code == 200) {
						flag = true;
					} else {
						flag = false;
						alert("用户不存在,请注册!");
					}
				}
			})
		} else {
			alert("输入手机或邮箱格式不正确");
			flag = false;
		}
		return flag;
	}
function check() {

	if ($("#username").val().length <= 0) {
		alert("请输入手机号/邮箱");
		return false;
	}

	if ($("#password").val().length <= 0) {
		alert("请输入密码");
		return false;
	}
	return loseBlur($("#username").val());
}

var flag = 1;
function isPhoneOrEmail(phoneAndEmail) {
	var regPhone = /^0?(13[0-9]|15[012356789]|18[02356789]|14[57]|17[07])[0-9]{8}$/;
	var regEmail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	if (regPhone.test(phoneAndEmail)) {
		return 0;
	} else if (regEmail.test(phoneAndEmail)) {
		return 1;
	} else {
		return 2;
	}
}


$(function() {
	$(".login-bgc").height($(document).height());
	/*二维码滑过事件*/
	$(".erweima").hover(function() {
		$(this).addClass('erweima-hover');
	    $(".login-con").hide();
	    $(".login-con-one-code").show();
	}, function() {
	    $(this).removeClass('erweima-hover');
	    $(".login-con").show();
	    $(".login-con-one-code").hide();

	});
	/*登录注册页面切换*/
	/*$(".register-con")*/
	$(".register-open").click(function(event) {
		$(".login-con").hide();
		 $(".login-wrap").addClass('register-wrap');
		 $(".romateBox1").hide();
		$(".login-bg-op").height("460px")
		$(".register-con").stop().show();

		$(".erweima").hide();	
		
	});
	$(".login-open").click(function(event) {

		$(".login-con").show();
		 $(".login-wrap").removeClass('register-wrap');
		 $(".login-bg-op").height("308px")
		$(".register-con").stop().hide();
			$(".erweima").show();
	});
	$(".login-sbtn").click(function(event) {
		
		$(".romateBox .login-con").css({
			"transform":"rotateY(90deg)"
			// "opacity":"0"
		})
		
		setTimeout(function(){
			$(".login-con").css({
				width:"0",
				height:"0"
			})
			$(".romateBox").css({
				height:"0"
			})
			
			$(".romateBox1 .login-success").css({
			"transform":"rotateY(360deg)",
			"opacity":"1"
		})
		},1000)

		// $(".login-con").animate({width:"0",height:"0"},500,"swing",function(){
		// 	$(".login-success").show();
		// 	$(".login-success").css("width","0px");
		// 	$(".login-success").css("height","0px");
		// 	$(".login-success").animate({width:"100%",height: "254px"},500,"swing");
	       
		// });

        /*$(".register-con").stop().hide();*/
		$(".erweima").hide();
		
		
	});

	/*input框样式*/
	$(".login-wrap input").each(function(index, el) {
		$(this).focus(function(event) {
			$(this).css({
				'border': '1px solid #a1de00',
				'boxShadow': 'inset 0 1px 1px rgba(0,0,0,.075)'
			});
		}).blur(function(event) {
			$(this).css({
				'border': '1px solid #d5d5d5'
			
			});
		});
	});
	/*错误input框样式*/
	/*	$(".login-wrap input").each(function(index, el) {
			$(this).focus(function(event) {
				$(this).css({
					'border': '1px solid #a1de00',
					'boxShadow': 'inset 0 1px 1px rgba(0,0,0,.075)'
				});
			}).blur(function(event) {
				$(this).css({
					'border': '1px solid #ff0000'
				
				});
			});
		});*/
});
