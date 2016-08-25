$(function(){
	
	// 发送通知
	$("#sendBtn").click(function(){
		
		var params = {};
		// 手机号
		var phones = $("input[name=phones]").val();
		// 用户
		var userIds = $("input[name=userIds]").val();
		// 用户类型
		var user_type = $("select[name=user_type]").val();
		// 判断输入条件
		var select_row = 0;
		if(phones.length != 0){
			params.phones = phones;
			select_row++;
		}
		if(userIds.length != 0){
			params.userIds = userIds;
			select_row++;
		}
		if(user_type != -1){
			params.user_type = user_type;
			select_row++;
		}
		
		if(select_row == 0){
			alert("请输入或选择发送对象");
			return false;
		}else if(select_row > 1){
			alert("通知发送对象只能为一种类型");
			return false;
		}
		
		// 通知标题
		var title = $("input[name=title]").val();
		params.title = title;
		// 通知内容
		var content = $("textarea[name=content]").val();
		if(content.length == 0){
			alert("请输入通知内容");
			return false;
		}
		if(content.length > 250){
			alert("通知内容最多250个字符");
			return false;
		}
		params.content = content;
		
		// 通知类型
		if($("input[name=message_type]:checked").size() == 0){
			alert("请选择通知类型");
			return false;
		}
		params.message_type = ""; 
		$("input[name=message_type]").each(function(){
			if(this.checked){
				params.message_type+=this.value+",";
			}
		});
		
		
		if(confirm("确定要发送通知吗？")){
			$.ajax({
				url:"/message/taskMessageQueue/doAddTask",
				type:"post",
				data:params,
				success:function(data){
					if(data.code == 200){
						alert("成功，通知任务正在排队执行。");
						location.href="/message/taskMessageQueue/taskList/";
					}else{
						alert(data.message);
					}
				}
			});
		}
	});
	
});