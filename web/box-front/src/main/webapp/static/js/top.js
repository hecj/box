$(document).ready(function(){
	/*顶部头像划过菜单*/
	$(".top-right").mouseenter(function(){
		$(this).children(".icon,.top-more").show();
	});
	$(".top-right").mouseleave(function(){
		$(this).children(".icon,.top-more").hide();
	});
	/*关于只能显示昵称前6个字*/
	var nick_name = document.getElementById('nickname');
	if(nick_name != null){
		var nick_name_html = nick_name.innerHTML;
		if (nick_name_html.length>6) {
			nick_name.innerHTML = nick_name.innerHTML.substr(0,6)+"...";
		} else{
			nick_name.innerHTML = nick_name_html;
		};
	}
/*	
	$("body").css({"overflow":"auto"});
	
	$(".bg_wrap").css({
		    'width': '100%',
	       'margin': '0',
	       'filter':' blur(0)',
	       '-webkit-filter': 'blur(0)',
	    '-moz-filter': 'blur(0)',
	    '-ms-filter':' blur(0)',
	    'filter': 'blur(0)',
	    'filter': 'progid:DXImageTransform.Microsoft.Blur(PixelRadius=0, MakeShadow=false)'
	});*/
});