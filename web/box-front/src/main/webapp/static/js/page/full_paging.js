//分页插件
/**
2014-08-05 ch
**/
(function($){
	var ms = {
		init:function(obj,args){
			return (function(){
				ms.fillHtml(obj,args);
				ms.bindEvent(obj,args);
			})();
		},
		//填充html
		fillHtml:function(obj,args){
			return (function(){
				obj.empty();
				//首页
				if (args.current > 1) {
					obj.append('<a href="javascript:;" class="pd-icon pd-first firstPage"></a> ');
				}else{
					obj.remove('.firstPage');
					obj.append('<a href="javascript:;" class="pd-icon pd-first"></a> ');
				}
				//上一页
				if(args.current > 1){
					obj.append('<a href="javascript:;" class="pd-icon pd-previous prevPage"></a> ');
				}else{
					obj.remove('.prevPage');
					obj.append('<a href="javascript:;" class="pd-icon pd-previous"></a> ');
				}
				//中间页码
				if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a> ');
				}
				if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
					obj.append('<span>...</span> ');
				}
				var start = args.current -2,end = args.current+2;
				if((start > 1 && args.current < 4)||args.current == 1){
					end++;
				}
				if(args.current > args.pageCount-4 && args.current >= args.pageCount){
					start--;
				}
				for (;start <= end; start++) {
					if(start <= args.pageCount && start >= 1){
						if(start != args.current){
							obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a> ');
						}else{
							obj.append('<a href="javascript:;" class="current">'+ start +'</a> ');
						}
					}
				}
				if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
					obj.append('<span>...</span> ');
				}
				if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a> ');
				}
				//下一页
				if(args.current < args.pageCount){
					obj.append('<a href="javascript:;" class="pd-icon pd-next nextPage"></a> ');
				}else{
					obj.remove('.nextPage');
					obj.append('<a href="javascript:;" class="pd-icon pd-next"></a> ');
				}
				//尾页
				if (args.current < args.pageCount) {
					obj.append('<a href="javascript:;" class="pd-icon pd-last lastPage"></a> ');
				}else{
					obj.remove('.lastPage');
					obj.append('<a href="javascript:;" class="pd-icon pd-last"></a> ');
				}
				//跳转页
				obj.append('<input type="text" class="numPage"> <button class="toPage"></button> ');
			})();
		},
		//绑定事件
		bindEvent:function(obj,args){
			return (function(){
				obj.unbind();
				//中间页码
				obj.on("click","a.tcdNumber",function(){
					var current = parseInt($(this).text());
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}
				});
				//上一页
				obj.on("click","a.prevPage",function(){
					var current = parseInt(obj.children("a.current").text());
					ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current-1);
					}
				});
				//下一页
				obj.on("click","a.nextPage",function(){
					var current = parseInt(obj.children("a.current").text());
					ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current+1);
					}
				});
				//首页
				obj.on("click","a.firstPage",function(){
					ms.fillHtml(obj,{"current":1,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(1);
					}
				});
				//尾页
				obj.on("click","a.lastPage",function(){
					ms.fillHtml(obj,{"current":args.pageCount,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(args.pageCount);
					}
				});
				//跳转页
				obj.on("click","button.toPage",function(){
					var current = parseInt(obj.children("input.numPage").val());
					if (current > args.pageCount || current < 1) {
						return;
					}
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}
				});
			})();
		}
	};
	$.fn.createPage = function(options){
		var args = $.extend({
			pageCount : 10,
			current : 1,
			backFn : function(){}
		},options);
		ms.init(this,args);
	};
})(jQuery);