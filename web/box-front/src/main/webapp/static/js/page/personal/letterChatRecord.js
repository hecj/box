var reload = false;
$(document).ready(function() {
	// table菜单切换显示
	$(".personal-menu").find("li").eq(3).addClass("current").siblings().removeClass("current");
	
	$(".pay-way-wrap").height($(document).height());
	
	$(".letter-send").keyup(function(event) {
		var count = $(this).val().length;
		if (count > 300) {
			var nr = $(this).val().substring(0, 300);
			$(this).val(nr);
			count = 300;
		}
	});
	$(".personal-letter-write-name").focus(function(event) {
		$(".chat-err-msg").hide();
		$(".personal-letter-write-name").css({
			'border' : '1px solid #b3b3b3'
		});
	});
	$(".personal-letter-write-name").keyup(function(event) {
    
      		$(".letter-placeholder-03").hide();
      	
    }).blur(function(event) {
      		 var seacherVal=$(".personal-letter-write-name").val();
      		 seacherVal=$.trim(seacherVal);
		        if (seacherVal =="" ) {
		        $('.letter-placeholder-03').stop().show();
		        }
    });
	$('.letter-placeholder-03').click(function(){
   			$(".personal-letter-write-name").focus();
    });
	// 回复
	$(".chat-sendBtn").click(function(event) {
		var sub = true;
		var writeValue = $.trim($(".personal-letter-write-name").val());
		if (writeValue == 0) {
			$(".chat-err-msg").show();
			$(".personal-letter-write-name").css({
				'border' : '1px solid #ff3f3f'
			});
			sub = false;
		} else {
			$(".personal-letter-write-name").css({
				'border' : '1px solid #b3b3b3'
			});
			$(".chat-err-msg").hide();
		}
		if (!sub) {
			return;
		}
		var params = {};
		var receiveId = $(".personal-letter-user-id").text();
		params.content = writeValue;
		$.ajax({
			url : "/personal/sendPrivateMessage/"+receiveId,
			data : params,
			dataType : "json",
			type : "post",
			success : function(data) {
				if (data.code == 200) {
					reload = true;
					window.location.reload();
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
	
});