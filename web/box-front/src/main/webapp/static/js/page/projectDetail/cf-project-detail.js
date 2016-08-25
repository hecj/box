/*轮播图*/
$(function(){
  var bannerSlider = new Slider($('#slider'), {
    time: 3000,
    delay: 400,
    event: 'hover',
    auto: true,
    mode: 'fade',
    controller: $('#slider-circle'),
    activeControllerCls: 'active'
  });
  // 初始化轮播图原点
  $("#slider-circle").find("li").eq(0).addClass("active");
  // 一级评论清空、项目介绍评论清空
  $("#comment_textarea,#introduce_textarea").val("");
});


/*众筹项目详情页js代码*/
$(document).ready(function(){
	var projectId = $("#projectId").text();
	var projectUserId = $("#projectUserId").text();
	var table = $("#table").text();
	// 从cookie取tab索引
	var table_temp = $.cookie('cookie_project_detail_'+projectId);
	if(table_temp != null && !isNaN(table_temp)){
		table = table_temp;
	}
	
	/*初始化数据加载*/
	if (table == 1) {
		loadContext();	// 加载数据-项目介绍
	}
	if (table == 2) {
		loadSchedule();	// 加载数据-项目进展
	}
	if (table == 3) {
		loadComment(1);	// 加载数据-项目评论
	}
	if (table == 4) {
		loadSupport(1);	// 加载数据-项目支持
	}
	if (table != 1) {
		$(document).scrollTop($('#project-detail-menu-in').offset().top);
	}
	$(".project-detail-menu ul li a").eq(table-1).addClass("current").parent().siblings().children("a").removeClass("current");
	$(".pd-subfield-"+(table-1)+"").css({"display":"block"}).siblings().css({"display":"none"});
	
	/*table栏切换*/
	$(".project-detail-menu ul li a").click(function(event){
		$(document).scrollTop($('#project-detail-menu-in').offset().top);
        $(this).addClass("current").parent().siblings().children("a").removeClass("current");
        var num=$(this).parent().index();
        if(num) {
        	$(".pd-subfield-"+num+"").css({"display":"block"}).siblings().css({"display":"none"});
        } else {
        	$(".pd-subfield-0").css({"display":"block"}).siblings().css({"display":"none"});
        }
        if(num==0){
        	loadContext();	// 加载数据-项目介绍
        }
        if(num==1){
        	loadSchedule();	// 加载数据-项目进展
        }
        if(num==2){
        	loadComment(1);	// 加载数据-项目评论
        }
        if(num==3){
        	loadSupport(1);	// 加载数据-项目支持
        }
        $.cookie('cookie_project_detail_'+projectId, num+1);
        
	});
	
    /*屏幕滚动菜单栏fixed*/
    $(window).scroll(function(){
    	var pdnum=$(document).scrollTop();
    	if(pdnum>=660) {
    		$(".project-detail-menu").addClass("pdm-fixed");
    		$(".project-detail-content").css({"padding-top":"50px"});
    	} else {
    		$(".project-detail-menu").removeClass("pdm-fixed");
    		$(".project-detail-content").css({"padding-top":0});
    	}
    });
    
    /*支持分页JS*/
    $(".page-div a").click(function(){
        $(this).addClass("current").siblings("a").removeClass("current");
    });
    
    /*项目关注*/
    $("#subButton").click(function(){
    	subscribeFun(projectId,1);
    });

	/*进度条*/
    var progress_num = $("#progress-num").html();
    if (progress_num.substring(0,progress_num.length-1) > 100) {
    	progress_num = "100%";	
	}
    $("#progress-bar").css({"width":progress_num});
    $("#progress-circle").css({"background-position":progress_num +"0"});
    
    /*二维码*/
    $(".QRcodeout").mouseenter(function(){
        $(this).parent().parent().siblings(".QRcode").show();
    });
    $(".QRcodeout").mouseleave(function(){
        $(this).parent().parent().siblings(".QRcode").hide();
    });
    
    /*个性签名字数控制显示*/
    var p_signature = document.getElementById('p-signature');
    var p_signature_html = p_signature.innerHTML;
    if (p_signature_html.length>14) {
        p_signature.innerHTML = p_signature.innerHTML.substr(0,14)+"...";
    } else{
        p_signature.innerHTML = p_signature_html;
    };
    
    /*私信框弹出*/	
    $(".privateletter").click(function(){
		$(".private-letter").css({"display":"block"});
		$(".pay-way-wrap").css({"display":"block","height":$(window).height()});
		$(".private-letter-cont-textarea").val("");
	});
	$("#close").click(function(){
		$(".private-letter").hide();
		$(".pay-way-wrap").hide();
	});
	// 私信placeholder
	$("#message").on({
		focus:function(){
			$(this).siblings(".pb-empty").hide();
		},
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		},
		blur:function(){
			var aval = $(this).val();
			if (aval == "") {
				$(this).siblings(".pb-ph").show();
			};
		}
	});
	$("#message").siblings(".pb-ph").click(function(){
		$(this).siblings("#message").focus();
	});	
	/*私信发送*/
	$("#message_button").click(function(){
		var params = {};
		params.content = $.trim($("#message").val());
		if (params.content == "") {
			$(this).siblings(".pb-empty").show();
			return;
		};
		$.ajax({
			url : "/personal/sendPrivateMessage/" + projectUserId,
			data : params,
			dataType : "json",
			type : "post",
			success : function(data) {
				$("#box-alert").find("p").text(data.message);
				$("#box-alert").show();
				$(".pay-way-wrap").show();
				$(".private-letter").hide();
				if (data.code == 200) {
					$(".private-letter").hide();
				}
			}
		});
	});

	/*快速评论*/
	$("#introduce_button").click(function(){
		postFast(projectId);
	});
    
    // 支持自己项目时提示
    $("[name='no-support']").click(function(){
    	$("#box-alert").find("p").text("不能支持自己的项目");
		$("#box-alert").show();
		$(".pay-way-wrap").show();
	});
    
    // 提示框：确认、关闭
	$("#box-alert .feedback-submit,.feedback-close").click(function(){
		$("#box-alert").hide();
		$(".pay-way-wrap").hide();
	});
});


/**
 * 粉丝关注
 * sub_type = 1  关注
 * sub_type = 2  取消关注
 */
function addFans(promoderid, sub_type){
	if ($(".fans-attention").text()=="已关注") {
		sub_type = 2;
	}else if($(".fans-attention").text()=="关注"){
		sub_type = 1;
	}else{
		return;
	}
	jQuery.ajax({
		url : "/projectDetail/sub/",
		type : "POST",
		data : "sub_type=" + sub_type + "&promoderid=" + promoderid,
		success : function(data) {
			if (data.code != 200) {
				alert(data.message);
			}
			if (data.code == 200 && sub_type == 1) {
				$(".fans-attention").text("已关注");
				$(".fans i").text(parseInt($(".fans").text())+1);
			}
			if (data.code == 200 && sub_type == 2) {
				$(".fans-attention").text("关注");
				$(".fans i").text(parseInt($(".fans").text()) - 1);
			}
		}
	});
}

