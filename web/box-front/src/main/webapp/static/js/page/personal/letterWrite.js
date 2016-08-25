$(document).ready(function(){
	// table菜单切换显示
	$(".personal-menu").find("li").eq(3).addClass("current").siblings().removeClass("current");
	
	$(".pay-way-wrap").height($(document).height());
	
	$(".wantSpeak-word").keyup(function(event) {
		var count = $(this).val().length;
		if (count > 300) {
			var nr = $(this).val().substring(0, 300);
			$(this).val(nr);
			count = 300;
		}
	});
	
	$(".personal-letter-write-name").focus(function(event) {
		$(".write-error-msg").hide();
		$(".personal-letter-write-name").css({
			'border' : '1px solid #b3b3b3'
		});
	});
	
	$(".wantSpeak-word").focus(function(event) {
		$(".write-error-msg02").hide();
		$(".wantSpeak-word").css({
			'border' : '1px solid #b3b3b3'
		});
	});
	  /*jsmoni*/
   $(".wantSpeak-word").keyup(function(event) {
    
      		$(".letter-placeholder-02").hide();
      	
    }).blur(function(event) {
      		 var seacherVal=$(".wantSpeak-word").val();
      		 seacherVal=$.trim(seacherVal);
		        if (seacherVal =="" ) {
		        $('.letter-placeholder-02').stop().show();
		        }
    });
	$('.letter-placeholder-02').click(function(){
   			$(".wantSpeak-word").focus();
    });
    $(".personal-letter-write-name").keyup(function(event) {
  
      		$(".letter-placeholder-01").hide();
      	
    }).blur(function(event) {
      		 var seacherVal=$(".personal-letter-write-name").val();
      		  seacherVal=$.trim(seacherVal);
		        if (seacherVal =="" ) {

		        $('.letter-placeholder-01').stop().show();
		        }
    });
	$('.letter-placeholder-01').click(function(){
   			$(".personal-letter-write-name").focus();
    });
	$(".sendemailBtn").click(function(event) {
		var sub = true;
		var nameValue = $.trim($(".personal-letter-write-name").val());
		if (nameValue == 0) {
			$(".write-error-msg").show();
			$(".write-error-msg").html("收件人用户名不能为空");
			$(".personal-letter-write-name").css({
				'border' : '1px solid #ff3f3f'
			});
			sub = false;
		} else {
			$(".personal-letter-write-name").css({
				'border' : '1px solid #b3b3b3'
			});
			$(".write-error-msg").hide();
		}
		var writeValue = $.trim($(".wantSpeak-word").val());
		if (writeValue == 0) {
			$(".write-error-msg02").show();
			$(".write-error-msg02").html("私信内容不能为空");
			$(".wantSpeak-word").css({
				'border' : '1px solid #ff3f3f'
			});
			sub = false;
		};
		if (!sub) {
			return;
		}
		var params = {};
		params.receiveUserName = nameValue;
		params.content = writeValue;
		$.ajax({
			url : "/personal/sendPrivateMessage/",
			data : params,
			dataType : "json",
			type : "post",
			success : function(data) {
				$("#box-alert").find("p").text(data.message);
				$("#box-alert").show();
				$(".pay-way-wrap").show();
				if (data.code == 200) {
					// 清空输入框
					$(".personal-letter-write-name").val("");
					$(".wantSpeak-word").val("");
				}
			}
		});
	});
	
	// 提示框：确认、关闭
	$("#box-alert .feedback-submit,.feedback-close").click(function(){
		$("#box-alert").hide();
		$(".pay-way-wrap").hide();
	});
});


