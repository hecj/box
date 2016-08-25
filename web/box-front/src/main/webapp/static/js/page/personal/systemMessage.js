$(document).ready(function(){
	// table菜单切换显示
	$(".personal-menu").find("li").eq(3).addClass("current").siblings().removeClass("current");
	
	$(".pay-way-wrap").height($(document).height());
	
	$(".newCenter-newOne:last").css({
		border: 'none'
	});
	// 切换按钮效果 
	$(".newCenter-btn li").click(function(event){
		$(this).find("span").addClass("green").parent().siblings().find("span").removeClass("green");
	});
	// 获取消息
	getSystemMessage(0,1);
});

// 全局变量
var mode_window = 0;
var page_window = 1;

// 获取系统消息
function getSystemMessage(mode, page) {
	mode_window = mode;
	page_window = page;
	$.ajax({
		url : "/personal/getSystemMessage/" + mode +'-'+ page,
		dataType : "json",
		type : "post",
		success : function(data) {
			if (data.code == 200) {
				// 无数据时
				if (data.data.messageList.length < 1) {
					$("#system-message").html("<p class='personal-no-massages'>暂无消息</p>");
					return;
				}
				
				// 填充数据
				var content = "";
				for ( var i = 0; i < data.data.messageList.length; i++) {
					var id = data.data.messageList[i].id;
					var message = data.data.messageList[i].message;
					var send_at = data.data.messageList[i].send_at;
					var is_read = data.data.messageList[i].is_read;
					if (is_read == 0) {
						content += '<div class="newCenter-newOne current"> <i class="border-radiusBtn"></i>'
									+ '<h4><a href="javascript:;">'+message+'</a></h4>'
									+ '<span>'+send_at+'</span>'
									+ '<a href="javascript:;" class="newCenter-know" onclick="readSystemMessage('+id+')">知道了</a>'
									+ '<b onclick="delSystemMessage('+id+')"></b>'
								+ '</div>';
					} else {
						content += '<div class="newCenter-newOne"> <i class="border-radiusBtn"></i>'
									+ '<h4><a href="javascript:;">'+message+'</a></h4>'
									+ '<span>'+send_at+'</span>'
									+ '<b onclick="delSystemMessage('+id+')"></b>'
								+ '</div>';
					}
				}
				$("#system-message").html(content);
				
				// 填充分页 
				$('#system-message-page').empty();
				if (data.data.totalPage>1) {
					var page = '<script>'
							 + '$("#system-message-page").createPage({'
								+ 'pageCount:' + data.data.totalPage + ','
								+ 'current:' + data.data.pageNumber + ','
								+ 'backFn:function(p){' 
									+ 'console.log(p);'
									+ 'getSystemMessage('+mode+', p);'
							 + '}});'
							 + '</script>';
					$('#system-message-page').html(page);
				}
				
				// 绑定事件
				$(".newCenter-newOne").hover(function() {
					$(this).find(".newCenter-know").toggle();
				});
			}
		}
	});
}

// 标记已读
function readSystemMessage(id){
	$.ajax({
		url : "/personal/readSystemMessage/" + id,
		dataType : "json",
		type : "post",
		success : function(data) {
			if (data.code == 200) {
				// 更新消息数量
				updateMessageCount(data.data.privateMessageCount,data.data.systemMessageCount);
				
				getSystemMessage(mode_window, page_window);
			} else {
				alert(data.message);
			}
		}
	});
}

// 删除系统消息
var delete_id = -1;
function delSystemMessage(id){
	$("#box-confirm").find("p").text("您确认要删除么？");
	$("#box-confirm").show();
	$(".pay-way-wrap").show();
	delete_id = id;
}
// 确认提示框：确定
$("#box-confirm .feedback-submit").click(function(){
	if (delete_id < 1) {
		return;
	}
	$.ajax({
		url : "/personal/delSystemMessage/" + delete_id,
		dataType : "json",
		type : "post",
		success : function(data) {
			$("#box-confirm").hide();
			$(".pay-way-wrap").hide();
			if (data.code == 200) {
				// 更新消息数量
				updateMessageCount(data.data.privateMessageCount,data.data.systemMessageCount);
				getSystemMessage(mode_window, page_window);
			}
		}
	});
});
// 确认提示框：取消，关闭	
$("#box-confirm .feedback-cancel,.feedback-close").click(function(){
	$("#box-confirm").hide();
	$(".pay-way-wrap").hide();
});



// 更新消息显示
function updateMessageCount(privateCount,systemCount){
	$(".top-right").find("i").text(privateCount+systemCount);
	$(".personal-menu").find("span").text(privateCount+systemCount);
	$(".personal-item-menu").find("span").eq(0).text(systemCount);
	$(".personal-item-menu").find("span").eq(1).text(privateCount);
	if (systemCount < 1) {
		$(".personal-item-menu").find("span").eq(0).remove();

	}
	if (privateCount < 1) {
		$(".personal-item-menu").find("span").eq(1).remove();
	}
	if (systemCount + privateCount < 1) {
		$(".top-right").find("i").remove();
		$(".personal-menu").find("span").remove();
	}
}

