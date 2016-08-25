/*众筹列表页js代码*/
	
	$(function() {
		/*左右点击轮播图start*/
	    function DY_scroll(wraper,prev,next,img,speed,or)
	    {
	    var wraper = $(wraper);
	    var prev = $(prev);
	    var next = $(next);
	    var img = $(img).find('ul');
	    var w = img.find('li').outerWidth(true);
	    var s = speed;
	    next.click(function()
	    {
	    img.animate({'margin-left':-w},function()
	    {
	    img.find('li').eq(0).appendTo(img);
	    img.css({'margin-left':0});
	    });
	    });
	    prev.click(function()
	    {
	    img.find('li:last').prependTo(img);
	    img.css({'margin-left':-w});
	    img.animate({'margin-left':0});
	    });
	    if (or == true)
	    {
	    ad = setInterval(function() { next.click();},s*1000);
	    wraper.hover(function(){clearInterval(ad);},function(){ad = setInterval(function() { next.click();},s*1000);});
	    }
	    }
       DY_scroll('.scroll-only','.item-show-left','.item-show-right','.item-show-wrap-only',4,false);// true为自动播放，不加此参数或false就默认不自动
	  /*左右点击轮播图end*/
	  /*顶部导航切换*/
/*       
	   $(".cf-list-item-top-one_l_detail li").click(function(event) {
		      
	       	   $(this).addClass('current').siblings().removeClass('current');
	    });*/
	   $(".click-up").click(function(event) {
		       $(this).hide();
		       $(".cf-list-top-wrap").hide();
	           $(".click-down").show();
	           $(".click-hint").show();
		       $(".cf-list-top").css({
		        	background: 'url(/static/images/background/silder_up.png) no-repeat',
		        	height:"120px"   
		        });
	    });
	    $(".click-down").click(function(event) {
		        $(this).hide();
		        $(".click-up").show(); 
		        $(".cf-list-top-wrap").show();
		        $(".cf-list-top").css({
		        	background: 'url(/static/images/background/silder_down.png)',
		        	height:"110px"
		        
		        });
        });
        $(".click-hint").click(function(event) {
		        $(this).hide();
		        $(".click-up").show(); 
		        $(".cf-list-top-wrap").show();
		        $(".cf-list-top").css({
		        	background: 'url(/static/images/background/silder_down.png)',
		        	height:"110px"
		        
		        });
		        $(".click-down").hide();
        });

	/*图片hover显示更多信息*/
		$(".item-show-one-top").hover(function() {
		        $(this).children('.show-prompt').show().parent().parent().siblings().children().children().children('.show-prompt').hide();
		}, function() {
			    $(this).children('.show-prompt').hide().parent().parent().siblings().children().children().children('.show-prompt').hide();
		});
		/*隔行变色*/
		$(".cf-list-con-wrap-list-bottom:odd").css({
			background: '#fff'
		});	
		$(".cf-list-con-wrap-list-bottom:even").css({
			background: '#fff'
		});

		/*点击加载多一行+分页显示+尖叫入口*/
		$(".load-more span").click(function(event) {
			$(".cf-list-con-wrap-list-bottom-display").slideDown();
			$(".page-hide , .wrap-entrance-hide").show();
			findMore();
		});
        /*点击分页对应变化*/
        $(".page li").click(function(event) {
        	$(this).addClass('current').siblings().removeClass("current");
        });
		});

  