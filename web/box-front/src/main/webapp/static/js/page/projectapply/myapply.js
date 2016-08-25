	function findExecute(val,status){
		$.ajax({
			type:"POST",
			url:"/projectapply/findExecute",
			data:"project_id="+val+"&status="+status,
			async:false,
			success:function(msg){
				$("#"+val).html(msg.message);
				$(".sponsor-static-hover").siblings('#'+val).toggle();
			}
		})
	}
	
	function displayExecute(val){
		$("#"+val).hide();
	}
	
	var delete_id = -1;
	function deleteById(val){
		$(".pay-way-wrap").show();
		$("#box-confirm").find("p").text("项目删除后将无法恢复，请确认是否删除？");
		$("#box-confirm").show();
		delete_id = val;
		return;
	}
	
	// 确认提示框：确定
	$("#box-confirm .feedback-submit").click(function(){
		if (delete_id < 1) {
			return;
		}
		window.location.href="/projectapply/deleteapply/"+delete_id;
	});
	// 确认提示框：取消，关闭	
	$("#box-confirm .feedback-cancel,.feedback-close").click(function(){
		$(".pay-way-wrap").hide();
		$("#box-confirm").hide();
	});
	
	
	
	