// 全局变量
var projectId = $("#projectId").text();

// 加载数据-项目介绍
function loadContext() {
	$.ajax({
		url : '/projectDetail/getContext/' + projectId,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data.code == 200) {
				var video = data.data.video;
				var content = data.data.content;
				var progress = data.data.projectProgress;
				
				// 项目视频
				if (video != null && video != "") {
					var html = '<embed src="'+video+'" type="" wmode="transparent" width="600" height="480">';
					$("#introduce_video").html(html);
					$("#introduce_video").show();
				} else {
					$("#introduce_video").hide();
				}
				
				// 项目介绍
				if (content != null && content != "") {
					$('#introduce_word').html(content);
				}

				// 项目最新动态
				if (progress != null) {
					var schedule = '<a href="/project/detail/'+projectId+'?table=2">'+
					'<div class="dynamic-in b-radius-8">'+
						'<b>项目最新动态：'+progress.info+'</b><br>'+
						'<span>消息发布时间：'+progress.progress_at+'</span>'+
					'</div>'+
					'</a>'
					$('#introduce_dynamic').html(schedule);
				}
			}
		}
	});
}

// 项目项目介绍-评论
function postFast() {
	var params = {};
	params.content = $.trim($("#introduce_textarea").val());
	$.ajax({
		url : '/projectDetail/post/' + projectId,
		data : params,
		type : "POST",
		dataType : "json",
		success : function(data) {
			$("#box-alert").find("p").text(data.message);
			$("#box-alert").show();
			$(".pay-way-wrap").show();
			if (data.code == 200) {
				$("#introduce_textarea").val("");
				var comment = $("#project-detail-menu-in").find("li").eq(2).find("strong");
				comment.text(parseInt(comment.text()) + 1);
			}
		}
	});
}