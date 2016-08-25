$(document).ready(function(){
	/*评论框弹出*/
	$(".respond2").click(function(){
		$(this).siblings(".two-level-respond").css({"display":"block"});
	});
	$(".respond3").click(function(){
		$(this).siblings(".three-level-respond").css({"display":"block"});
	});
	/*评论输入300个字符*/
	$(".opinion-textarea,.one-level,.two-level,three-level,#message").bind("propertychange input blur",function(){
		var count=$(this).val().length;
		if (count>300) {
			var nr = $(this).val().substring(0,300);
			$(this).val(nr);
		};
	});
	// 介绍页评论placeholder
	$("#introduce_textarea").on({
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
	$("#introduce_textarea").siblings(".pb-ph").click(function(){
		$(this).siblings().focus();
	});
	// 评论页一级评论placeholder
	$("#comment_textarea").on({
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
	$("#comment_textarea").siblings(".pb-ph").click(function(){
		$(this).siblings().focus();
	});
	// 评论页二级评论placeholder
	twoLevelHideP=function(thisbtn){
		$(thisbtn).siblings(".pb-ph").hide();
	};
	twoLevelBlur=function(thisbtn){
			var aval = $(thisbtn).val();
			if (aval == "") {
				$(thisbtn).siblings(".pb-ph").show();
			};		
	};
	twoLevelFocus=function(thisbtn){
		$(thisbtn).siblings(".two-level").focus();
	};
	// 评论页三级评论placeholder
	threeLevelHideP=function(thisbtn){
		$(thisbtn).siblings(".pb-ph").hide();
	};
	threeLevelBlur=function(thisbtn){
			var aval = $(thisbtn).val();
			if (aval == "") {
				$(thisbtn).siblings(".pb-ph").show();
			};		
	};
	threeLevelFocus=function(thisbtn){
		$(thisbtn).siblings(".three-level").focus();
	};
	/*点击查看所有或者发起人*/
	$(".comment-title a").click(function(){
        $(this).addClass("current").siblings().removeClass("current");
	});
	/*页面刷新回复排版*/
	var one_level=$(".comment").find(".comment-block").length; /*一级回复的个数*/ /*alert(one_level); 1开始*/
	$(".comment").find(".comment-block").eq(one_level-1).css({"border-bottom":"none"});
	var comment_block=$(".comment").find(".comment-block");  /*一级回复的变量*/	
	for (var i = 0; i <= one_level-1; i++) {
		var num=comment_block.eq(i).find(".cbc-rbb-cont").length;  /*二级回复的个数*/
		if(num<=2) {
			$(comment_block).eq(i).find(".cbc-rbb-cont-more").hide();
			$(comment_block).eq(i).find(".page-div1").hide();
		}
		else {
			$(comment_block).eq(i).find(".cbc-rbb-cont-more").css({"display":"block"});
			$(comment_block).eq(i).find(".page-div1").hide();
			$(comment_block).eq(i).find(".cbc-rb-block").css({"padding-bottom":0});
			for (var j = num - 1; j >= 2; j--) {
				$(comment_block).eq(i).find(".cbc-rb-block").children().eq(j).hide();
			};		
		}
	};
	/*点击显示更多回复*/
    showMore = function (thisbtn){
    	$(thisbtn).hide();
    	$(thisbtn).siblings(".page-div1").show();
    	var two_level=$(thisbtn).siblings(".cbc-rb-block").children(".cbc-rbb-cont").length;
    	for (var i = 0 ; i < 10 ; i++) {
    		$(thisbtn).siblings(".cbc-rb-block").children().eq(i).show();
       	};
    	/*二级回复的个数*/ /*alert(two_level); 1开始*/
    	/*alert(two_level);*/
/*    	if (two_level<=10) {
    		$(thisbtn).siblings(".page-div1").show();
    		$(thisbtn).siblings(".page-div1").addClass("page-div1-condition5");
    		for (var i = 0 ; i < two_level ; i++) {
    			$(thisbtn).siblings(".cbc-rb-block").children().eq(i).show();
       		};	    		
    	} else{
    		if ( two_level <= 20 ) {
    			$(thisbtn).siblings(".page-div1").show();
    			$(thisbtn).siblings(".page-div1").addClass("page-div1-condition1");
    			for (var i = 0 ; i < 10; i++) {
    				$(thisbtn).siblings(".cbc-rb-block").children().eq(i).show();
    			};
    		} else{
    			if (two_level > 20 ) {
    				$(thisbtn).siblings(".page-div1").show();
    				$(thisbtn).siblings(".page-div1").addClass("page-div1-condition2");
    				for (var i = 0; i < 10; i++) {
    					$(thisbtn).siblings(".cbc-rb-block").children().eq(i).show();
    				};
    			};
    		}
    	}*/
    };
	/*点击收回更多回复*/
	moreClose = function (thisbtn){
		$(thisbtn).parent(".page-div1").hide();
		$(thisbtn).parent().siblings(".cbc-rbb-cont-more").css({"display":"block"});
		var two_level=$(thisbtn).parent().siblings(".cbc-rb-block").children(".cbc-rbb-cont").length;
		for (var i = 2 ; i < two_level; i++) {
			$(thisbtn).parent().siblings(".cbc-rb-block").children().eq(i).hide();
		};
	}
	/*回复分页点击*/
	$(".third,.fourth,.fifth").bind("click",function(){
		$(this).addClass("current").parent().siblings().find("a").removeClass("current");
	});
	/*回复或者删除*/
	$(".cbc-rbb-cont").on({
		mouseenter:function(){
			$(this).find(".delete2").css({"display":"block"});
			$(this).find(".respond3").css({"display":"block"});
		},
		mouseleave:function(){
			$(this).find(".delete2").css({"display":"none"});
			$(this).find(".respond3").css({"display":"none"});
		}
	});
	/*评论60秒间隔*/
	commentIntervalTwoLevel = function (thisbtn){
		var timer = 60;
		fun();
		$(thisbtn).click(function(){
			if (timer > 0) {
				$(this).siblings(".two-level-word").show();
			};
		});
		function fun(){
			timer--;
			if(timer>0){
				setTimeout(fun,1000);
			};
		};	
	};
	commentIntervalThreeLevel = function (thisbtn){
		var timer = 60;
		fun();
		$(thisbtn).click(function(){
			if (timer > 0) {
				$(this).siblings(".three-level-word").show();
			};
		});
		function fun(){
			timer--;
			if(timer>0){
				setTimeout(fun,1000);
			};
		};	
	};

})