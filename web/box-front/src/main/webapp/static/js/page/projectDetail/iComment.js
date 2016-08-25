// 全局变量
var projectId = $("#projectId").text();
var commentPage = 1;
var loginId = -1;
var loginNcikname = "";

// 加载数据-一级评论
function loadComment(page,mode) {
	if(mode == undefined){
		mode = "";
	}
	commentPage = page;
	$.ajax({
		url : '/projectDetail/getComment/'+ projectId +'-'+ page,
		type : "POST",
		dataType : "json",
		data : {mode:mode},
		success : function(data) {
			if (data.code == 200) {
				// 更新动态
				$(".project-detail-menu").find(".current").siblings(".pdm-num").find("strong").text(data.data.commentDynamic);
				
				// 无数据时的处理
				if (data.data.commentList.length < 1) {
					$('#comment_area').empty();
					$('#comment_page').empty();
					return;
				}
				
				// 获取当前登录用户
				if (data.data.loginUser!=null) {
					loginId = data.data.loginUser.id;
					loginNcikname = data.data.loginUser.nickname==null?"":data.data.loginUser.nickname;
				}
				
				// 填充评论区
				$('#comment_area').empty();
				for ( var i = 0; i < data.data.commentList.length; i++) {
					var id = data.data.commentList[i].id;
					var userId = data.data.commentList[i].user_id;
					var nickname = data.data.commentList[i].nickname==null?"":data.data.commentList[i].nickname;
					var picture = data.data.commentList[i].picture;
					var content = data.data.commentList[i].content;
					var create_at = data.data.commentList[i].create_at;
					var support_num = data.data.commentList[i].support_num;
					var reply_num =  data.data.commentList[i].reply_num;
					var del_lable = userId == loginId ? '<a class="cbc-rc-delete delete1" href="javascript:;" onclick="del('+id+')">删除</a> ' : '' ;
					var comment = '<div class="comment-block">'
								+'<div class="comment-block-head">'
									+'<img src="'+picture+'" alt="">'
									+'<a href="javascript:;"></a>'
								+'</div>'
								+'<div class="comment-block-cont">'
									+'<h6 class="clearfix"><span class="comment-people">'+nickname+'</span></h6>'
									+'<p class="cbc-cont">'+content+'</p>'
									+'<div class="cbc-respond">'
										+'<div class="cbc-respond-cont clearfix">'
											+'<em class="cbc-rc-time">'+create_at+'</em>'
											+ del_lable
											+'<a class="cbc-rc-speak respond2" href="javascript:;" onclick="showReply(this)">评论<em>(&nbsp;'+reply_num+'&nbsp;)</em></a>'
											+'<a class="cbc-rc-like" href="javascript:;" id="support-'+id+'" onclick="support('+id+')">赞<em> ( '+support_num+' )</em></a>'
											// 回复框
								   			+'<div class="two-level-respond clearfix">'
												+'<textarea id="reply_textarea-'+id+'" class="two-level cbc-rcc-text" maxlength="150" onkeyup="twoLevelHideP(this)" onblur="twoLevelBlur(this)"></textarea>'
												+'<span class="pb-ph unique3" onclick="twoLevelFocus(this)">'+loginNcikname+'&nbsp;回复</span>'
												+'<button id="respond2-'+id+'" class="two-level-btn cbc-rcc-btn" onclick="reply('+id+','+id+',0)">发布</button>'
												+'<span class="two-level-word">您提交的太快了，喝杯茶休息下吧！</span>'
											+'</div>'
										+'</div>'
											// 回复
											+'<div id="reply-'+id+'" class="cbc-respond-block"></div>'
									+'</div>'
								+'</div>'
							+'</div>';
					$('#comment_area').append(comment);
					
					// 获取回复
					loadReply(id,1);
				}

				// 填充分页 
				if (data.data.totalPage>1) {
					var page = '<script>'
						+ '$("#comment_page").createPage({'
							+ 'pageCount:' + data.data.totalPage + ','
							+ 'current:' + data.data.pageNumber + ','
							+ 'backFn:function(p){' 
								+ 'console.log(p);'
								+ 'loadComment(p);'
						+ '}});'
						+ '</script>';
					$('#comment_page').html(page);
				}
			}
		}
	});
}

// 加载数据-二三级评论
function loadReply(commentId, page) {
	$.ajax({
		url : "/projectDetail/getReply/" + commentId +'-'+ page,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data.code == 200) {
				// 无数据时的处理
				if (data.data.replayList.length < 1) {
					return;
				}
				
				// 填充二三级评论
				var reply ='<em class="arrow"></em>'
						  +'<div class="cbc-rb-block">';
				for ( var i = 0; i < data.data.replayList.length; i++) {
					var id = data.data.replayList[i].id;
					var userId = data.data.replayList[i].user_id;
					var nickname = data.data.replayList[i].nickname==null?"":data.data.replayList[i].nickname;
					var s_nickname = data.data.replayList[i].s_nickname==null?"":data.data.replayList[i].s_nickname;
					var content = data.data.replayList[i].content;
					var create_at = data.data.replayList[i].create_at;
					var level = data.data.replayList[i].level;
					var show_lable = level == 3 ? 
							'<span class="name comment-people">'+nickname+':&nbsp</span>回复 <span class="name respond-people">'+s_nickname+':&nbsp</span>' :
							'<span class="name respond-people">'+nickname+':&nbsp</span>';
					var delete_lable = userId == loginId ? 
							'<a class="delete2" href="javascript:;" onclick="del('+id+')">删除</a>' : 
							level < 3 ? '<a class="respond3" href="javascript:;" onclick="showReply(this)">回复</a>' : '';
					
					reply += '<div class="cbc-rbb-cont clearfix" onmouseover="replayEnter(this)" onmouseout="replayLeavl(this)">'
						   + show_lable
						   + content
						   + '<span class="time">' + create_at + '</span>'
						   + delete_lable
						   + '<div class="three-level-respond clearfix">'
						   	+ '<textarea id="reply_textarea-'+id+'" class="three-level" maxlength="150" onkeyup="threeLevelHideP(this)" onblur="threeLevelBlur(this)"></textarea>'
						   	+'<span class="pb-ph unique3" onclick="threeLevelFocus(this)">'+loginNcikname+'&nbsp;回复</span>'
						   	+ '<button class="two-level-btn three-level-btn" onclick="reply('+id+','+commentId+','+userId+')">发布</button>'
						   	+ '<span class="three-level-word">提交过于频繁，请稍后再试!</span>'
						   + '</div>'
						  + '</div>';
				}
				reply += '</div>'
				   	   + '<a class="cbc-rbb-cont-more" href="javascript:;" onclick="showMore(this)">显示更多</a>'
				   	   + '<div class="page-div1 clearfix">'
		    		  	+ '<div id="replay_page-'+commentId+'" class="page-div1-in"></div>'
		    		  	+ '<a class="close" href="javascript:;" onclick="moreClose(this)"></a>'
		    		   + '</div>';
			   if (data.data.totalPage > 1) {
				   reply += '<script>'
						 + '$("#replay_page-'+commentId+'").createPageForReplay({'
							 + 'pageCount:' + data.data.totalPage + ','
							 + 'current:' + data.data.pageNumber + ','
							 + 'backFn:function(p){' 
								 + 'console.log(p);'
								 + 'loadReply('+commentId+', p);'
						 + '}});'
						 + '</script>';
			   }
			   $("#reply-"+commentId).html(reply);

			   if (data.data.pageNumber == 1) {
				   // 隐藏其他回复
				   if (data.data.replayList.length > 2) {
					   $("#reply-"+commentId).find(".cbc-rbb-cont-more").css({"display":"block"});
					   $("#reply-"+commentId).find(".page-div1").hide();
					   $("#reply-"+commentId).find(".cbc-rb-block").css({"padding-bottom":0});
					   for ( var i = 2; i < data.data.replayList.length; i++) {
						   $("#reply-"+commentId).children().eq(1).children().eq(i).hide();
					   }
				   } else {
					   $("#reply-"+commentId).find(".cbc-rbb-cont-more").hide();
					   $("#reply-"+commentId).find(".page-div1").hide();
				   }
			   } else {
				   $("#reply-"+commentId).find(".cbc-rbb-cont-more").hide();
				   $("#reply-"+commentId).find(".close").hide();
			   }
			}
		}
	});
}

// 项目评论-一级评论
function post() {
	if (getCookie("boxamazing-respond1") != null) {
		alert("您提交的太快了，喝杯茶休息下吧！");
	} else{
		var params = {};
		params.content = $.trim($("#comment_textarea").val());
		$.ajax({
			url : "/projectDetail/post/" + projectId,
			data : params,
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.code == 200) {
					$("#comment_textarea").val("");
					loadComment(1);
					setCookie("boxamazing-respond1","1min");
				} else {
					$("#box-alert").find("p").text(data.message);
					$("#box-alert").show();
					$(".pay-way-wrap").show();
				}
			}
		});
	}
}

// 项目评论-二三级评论
function reply(id, parentId, secondUserId) {
	if (getCookie("boxamazing-respond2-"+id) != null) {
		$("#respond2-"+id).siblings(".two-level-word").show();
		$("#respond2-"+id).attr("disabled","disabled");
	} else{
		var params = {};
		params.content = $.trim($("#reply_textarea-" + id).val());
		$.ajax({
			url : "/projectDetail/reply/" + projectId + "-" + parentId + "-" + secondUserId + "-" + id,
			data : params,
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.code == 200) {
					loadComment(commentPage);
					setCookie("boxamazing-respond2-"+id,"1min");
					fun(60);
				} else {
					$("#box-alert").find("p").text(data.message);
					$("#box-alert").show();
					$(".pay-way-wrap").show();
				}
			}
		});
	};
	function fun(timer){
		timer--;
		if(timer>0){
			setTimeout(function() {fun(timer);},1000);
		} else {
			$("#respond2-"+id).siblings(".two-level-word").hide();
			$("#respond2-"+id).removeAttr("disabled");
		};
	};
}

// 项目评论-赞
function support(commentId) {
	$.ajax({
		url : "/projectDetail/support/" + commentId,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data.code == 200) {
				$("#support-" + commentId).html("赞<em> ( "+data.data+" )</em>");
			}
		}
	});
}

// 项目评论-删除
var delete_id = -1;
function del(commentId){
	delete_id = commentId;
	$("#box-confirm").find("p").text("请确认是否删除？");
	$("#box-confirm").show();
	$(".pay-way-wrap").show();
}

// 确认提示框：确认
$("#box-confirm .feedback-submit").click(function(){
	if (delete_id < 1) {
		return;
	}
	$.ajax({
		url : "/projectDetail/del/" + delete_id,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data.code == 200) {
				loadComment(commentPage);
			}
		}
	});
});
//确认提示框：取消、关闭
$("#box-confirm .feedback-cancel").click(function(){
	$("#box-confirm").hide();
	$(".pay-way-wrap").hide();
});

//显示/隐藏回复框
function showReply(obj) {
	$(obj).siblings(".two-level-respond").toggle();
	$(obj).siblings(".three-level-respond").toggle();
}

/*显示回复或者删除*/
function replayEnter(obj){
	$(obj).find(".delete2").css({"display":"block"});
	$(obj).find(".respond3").css({"display":"block"});
}
function replayLeavl(obj){
	$(obj).find(".delete2").css({"display":"none"});
	$(obj).find(".respond3").css({"display":"none"});
}


// 操作cookie
function setCookie(name, value) {
	var time = new Date();
	time.setSeconds(time.getSeconds() + 1);
	document.cookie = name + "=" + escape(value) + ";expires=" + time.toGMTString();
}
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1)
				c_end = document.cookie.length;
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return null;
}
