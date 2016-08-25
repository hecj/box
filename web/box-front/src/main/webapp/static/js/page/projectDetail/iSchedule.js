// 全局变量
var projectId = $("#projectId").text();

// 加载数据-项目进展
function loadSchedule(){
	$.ajax({
		url : '/projectDetail/getSchedule/'+projectId,
		type : "POST",
	    dataType : "json",
	    success : function(data) {
	    	if (data.code == 200) {
	    		//更新动态
				$(".project-detail-menu").find(".current").siblings(".pdm-num").find("strong").text(data.data.projectProgressDynamic);
				
				// 无数据时的处理
				if (data.data.projectProgressList.length < 1) {
					$('#schedule_progress').empty();
					return;
				}
				
				//schedule_progress 进展
	        	var schedule = '';
	        	for(var i=0; i<data.data.projectProgressList.length;i++){
	        		schedule += '<dl class="pds1-progress-block">'+
	        						'<dt class="time">'+data.data.projectProgressList[i].progress_at+'</dt>'+
	        						'<dd class="icon"></dd>'+
	        						'<dd class="cont">'+data.data.projectProgressList[i].info+'</dd>'+
	        					'</dl>';
	        	}
	        	$('#schedule_progress').html(schedule);
	        }
	    }
	});
}