$(function() {
	// table菜单切换显示
	$(".personal-menu").find("li").eq(0).addClass("current").siblings().removeClass("current");
	
	// 确认提示框：确定
	$("#box-confirm .feedback-submit").click(function(){
		if (delete_id < 1) {
			return;
		}
		jQuery.ajax({
			url : "/sub/sub",
			type : "POST",
			data : "sub_type=" + 2 + "&project_id=" + delete_id + "&time="
					+ new Date().getTime(),
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

// 关注/取消关注 项目
var delete_id = -1;
function subscribeFun(project_id, sub_type) {
	if ($(".ever-attention").text()=="已关注") {
		return;
	}
	if (sub_type == 2) {
		$("#box-confirm").find("p").text("您确认取消关注此项目吗?");
		$("#box-confirm").show();
		$(".pay-way-wrap").show();
		delete_id = project_id;
		return;
	}
	jQuery.ajax({
		url : "/sub/sub",
		type : "POST",
		data : "sub_type=" + 1 + "&project_id=" + project_id + "&time="
				+ new Date().getTime(),
		success : function(data) {
			if (data.code == 200) {
				$("#subButton").removeClass("no-attention").addClass("ever-attention");
				$("#subButton").text("已关注");
				$("#subNumber").text(parseInt($("#subNumber").text())+1);
			}
		}
	});
	
}