$(document).ready(function(){
	// table菜单切换显示
	$(".personal-menu").find("li").eq(2).addClass("current").siblings().removeClass("current");
});

//手机号绑定时获取短信验证码
function sc_tellphone(component) {
    var phone = $("input[name='tellphone']").val();
    var ashow = $(component).parent().parent().siblings(".tellphone").children(".pb-empty");
    if($.trim(phone).length == 0){
    	$(ashow).show().css({"color":"#f00"}).text("手机号不能为空");
		$("input[name=tellphone]").css({"border-color":"#f00"});
    }else if(checkPhone(phone)) {
    	$(ashow).hide();
        $.ajax({
            type: "POST",
            url: "/sendCode",
            data:"phone=" + phone,
            success: function(msg) {
                if (msg) {
                	SendDynamicCode(component, 60, "sc_tellphone(this);");
                } else{
                	feedbackSuccess("发送失败");
                }
            }
        });
    } else {
    	$(ashow).show().css({"color":"#f00"}).text("请输入正确的手机号");
		$("input[name=tellphone]").css({"border-color":"#f00"});
    }
};

//手机号绑定操作
$("input[name=tellphone-subbtn]").click(function(){
	//验证手机号
	 var phone = $("input[name='tellphone']").val();
	 var ashow = $(this).parent().siblings(".tellphone").children(".pb-empty");
	 if($.trim(phone).length == 0){
		 $(ashow).show().css({"color":"#f00"}).text("手机号不能为空");
		 $("input[name=tellphone]").css({"border-color":"#f00"});
	 }else if(!checkPhone(phone)) {
		 $(ashow).show().css({"color":"#f00"}).text("请输入正确的手机号");
		 $("input[name=tellphone]").css({"border-color":"#f00"});
	 } else {
		 $(ashow).hide();
	 }
	
	 //验证动态码
	 var binput = $(this).parent().siblings(".dynamic-code").find("input[name=dynamic-code]")
	 var dynamic_code = binput.val();
	 var bshow = $(this).parent().siblings(".dynamic-code").find(".pb-empty");
	 if ($.trim(dynamic_code).length == 0) {
		 $(bshow).show().css({"color":"#f00"}).text("动态码不能为空");
		 $(binput).css({"border-color":"#f00"});
		 return;
	 }else if($.trim(dynamic_code).length != 6){
		 $(bshow).show().css({"color":"#f00"}).text("动态码错误");
		 $(binput).css({"border-color":"#f00"});
		 return;
	 }else{
		 $(bshow).hide();
	 }
	 
     $.ajax({
         type: "POST",
         url: "/verifyCode",
         data:"phone=" + phone + "&code=" + dynamic_code,
         async: true,
         success: function(msg) {
             if (msg.code == 11) {
                 $.ajax({
                     type: "POST",
                     url: "/bindPhone",
                     data:"phone=" + phone,
                     async: true,
                     success: function(msg) {
                         if (msg == 'true') {
                        	 //清空原有框里的值
                        	 var $inputPhone =  $("input[name='tellphone']");
                        	 $inputPhone.val("");
                             binput.val("");
                             
                             //动态码倒计时停止，取消setTimeout
                             var $button = $inputPhone.parent().parent().siblings(".dynamic-code").children().children(".settimeout");
                             clearTimeout(t);
                             $button.removeClass("current").text("获取短信验证码");
                             $button.attr("onclick", "sc_tellphone(this);");
                             
                             //关闭窗口，弹出提示窗口，刷新页面
                             $inputPhone.parent().parent().parent().parent().hide();
                             showSuccess("绑定手机成功");
                         }else if(msg == 'exist'){
                        	 
                        	 $("input[name=tellphone]").parent().siblings(".pb-empty") .css({"color":"#f00"});
                        	 $("input[name=tellphone]").parent().siblings(".pb-empty").text("手机号已被占用");
                    		 $("input[name=tellphone]").css({"border-color":"#f00"});
                    		 $("input[name=tellphone]").parent().siblings(".pb-empty").css("display","block");
                         }else {
                             feedbackSuccess("服务器异常");
                         }
                     }
                 })
             } else {
        		 $(bshow).show().css({"color":"#f00"}).text("动态码错误");
        		 $(binput).css({"border-color":"#f00"});
             }
         }
     })
});

function showSuccess(txt){
	$(".other-success").children(".pub-popup-cont").children("span").text(txt);
    $(".other-success").show();
}
function showPwdSuccess(txt){
	$(".password-success").children(".pub-popup-cont").children("span").text(txt);
    $(".password-success").show();
}

$("input[name=submit-btn-os]").click(function(){
	window.location.href = "/personal/security";
});

//密码修改成功后提示窗口点击事件
$("input[name=submit-btn-ps]").click(function(){
	window.location.href = "/logOut";
});

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

function sc_pay_tellphone(component, phone){
	$.ajax({
	    type: "POST",
	    url: "/sendCode",
	    data:"phone=" + phone,
	    success: function(msg) {
	        if (msg) {
	        	SendDynamicCode(component, 60, "sc_pay_tellphone(this, "+phone+")");
	        } else{
	        	feedbackSuccess("发送失败");
	        }
	    }
	});
};

function openEmail(){
	window.open("http://" + gotoEmail($(".sent-word1").text()));
}
