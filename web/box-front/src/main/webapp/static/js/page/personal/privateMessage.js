var reload = false;
var delete_id = -1;

$(document).ready(function(){
	// table菜单切换显示
	$(".personal-menu").find("li").eq(3).addClass("current").siblings().removeClass("current");
	
	$(".pay-way-wrap").height($(document).height());
	
	// 回复框
	$(".return-letterBtn").click(function(event) {
		$(".personalsetting-send .letter-send").val("");
		$(".letter-placeholder").show();
		var nickname = $(this).parent().parent().find("b").text();
		$(".personalsetting-send").find("h6").text("回复给 "+nickname);
		
		var id = $(this).parent().parent().find(".personal-letter-user-id").text();
		$(".personalsetting-send").find("[name=receive-id]").val(id);
		
		$(".pay-way-wrap").show();
		$(".personalsetting-send").show();
	});
	// 回复框-关闭
	$(".popup-close").click(function(event) {
		$(".pay-way-wrap").hide();
		$(".personalsetting-send").hide();
	});
	
	// 字数长度控制
	$(".letter-send").keyup(function(event) {
		var count = $(this).val().length; 
		if(count>300){ 
			var nr = $(this).val().substring(0,300); 
			$(this).val(nr); 
			count=300; 
		}
    });
    /*jsmoni*/
    $(".letter-send").keyup(function(event) {
    
      		$(".letter-placeholder").hide();
      		$(".null-tip").hide();
      	
      	}).blur(function(event) {
      		 var seacherVal=$(".letter-send").val();
      		 seacherVal=$.trim(seacherVal);
		        if (seacherVal =="" ) {
		        $('.letter-placeholder').stop().show();
		        }
      	});
	   $('.letter-placeholder').click(function(){
   			$(".letter-send").focus();
	   });
	// 取消发送
	$(".close-letter").click(function(event) {
		$(".pay-way-wrap").hide();
		$(".personalsetting-send").hide();
	});
	// 回复私信-发送
	$(".submit-green").click(function(event){

		var sendVal=$(".letter-send").val();
		sendVal=$.trim(sendVal);
		if (sendVal=="") {
		$(".null-tip").show();
          return false;
		}
		var params = {};
		var userId = $("input[name=receive-id]").val();
		params.content = $.trim($(".letter-send").val());
		$.ajax({
			url : "/personal/sendPrivateMessage/" + userId,
			data : params,
			dataType : "json",
			type : "post",
			success : function(data) {
				$(".personalsetting-send").hide();
				/*$("#box-alert").find("p").text(data.message);*/
				$("#box-alert").show();
				$(".pay-way-wrap").show();
				if (data.code == 200) {
					/*reload = true;*/
				}
			}
		});
	
	});
	
	// 提示框：确认、关闭
	$("#box-alert .feedback-submit,.feedback-close").click(function(){
		$(".pay-way-wrap").hide();
		$("#box-alert").hide();
		if (reload) {
			reload = false;
			window.location.reload();
		}
	});
	
	// 删除会话
	$(".del-personal-letter").click(function(){
		$("#box-confirm").find("p").text("您确认要删除么？");
		$("#box-confirm").show();
		$(".pay-way-wrap").show();
		delete_id = $(this).parent().find(".personal-letter-dialog-id").text();
	});
	// 确认提示框：确定
	$("#box-confirm .feedback-submit").click(function(){
		if (delete_id < 1) {
			return;
		}
		$.ajax({
			url : "/personal/delPrivateMessage/" + delete_id,
			dataType : "json",
			type : "post",
			success : function(data) {
				if (data.code == 200) {
					window.location.reload();
				}
			}
		});
	});
	// 确认提示框：取消，关闭	
	$("#box-confirm .feedback-cancel,.feedback-close").click(function(){
		$("#box-confirm").hide();
		$(".pay-way-wrap").hide();
	});
});

//全局变量
var page_window = 1;




