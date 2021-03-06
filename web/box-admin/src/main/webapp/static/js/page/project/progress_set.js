
	$(function(){
        $("#usual1 ul").idTabs(); 
		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
	});

	function addProgressFun(project_id){
		
		var params = "";
		
		var info = jQuery("input[name='progress.info']").val();
		if(info.length == 0){
			alert("请输入进展信息");
			return;
		}
		params += "&progress.info="+info;
		
		var progress_at = jQuery("input[name='progress.progress_at']").val();
		
		if(progress_at.length == 0){
			alert("请输入进展时间");
			return;
		}
		
		progress_at = progress_at.replace(/-/g,"/"); 
		
		var progress_time = new Date(progress_at).getTime();
		
		var date_temp = new Date().format("yyyy-MM-dd");
		// 当天开始时间
		var begin_time = new Date(date_temp+" 00:00:00").getTime();
		// 当天结束时间
		var end_time = begin_time+24*60*60*1000;
		if(progress_time < begin_time || progress_time >= end_time ){
			alert("项目进展只能选择当天");
			return;
		}
		
		params += "&progress.progress_at="+progress_time;
		
		$.ajax({
				url : "/project/addProgress/"+project_id,
				type : "POST",
				data : params,
				success : function(data) {
					if(data.code == 200){
						alert("添加成功");
						location.href="/project/progress/"+project_id;
					}else{
						alert(data.message);
					}
				}
		});
	}		
	
	function delProgress(project_id,progress_id){
		if(confirm("确定要删除吗")){
			location.href="/project/delProgress/"+project_id+"-"+progress_id;
		}
	}
	
	// 重置
	function resetFormFun(){
		jQuery("input[name='progress.info']").val("");
		jQuery("input[name='progress.progress_at']").val("");
	}
