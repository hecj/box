$(document).ready(function(){
	  $(".no_del").click(function(){
		  $(this).parent().parent().siblings(".support-bgc").hide();
		  $(this).parent().parent().hide();
		  
		  //恢复“回报内容”的hover事件
		  var $pro = $(this).parent().parent().siblings().children().children(".support-return");
		  product_hover($pro);
		  
		  //恢复“订单详情”的hover事件
		  var $order = $(this).parent().parent().siblings().children().children().children().children(".support-booking");
		  orderDetail_hover($order);
		  
		  //恢复“物流详情”的hover事件
		  var $progress = $(this).parent().parent().siblings().children(".support-static").children().children().children(".support-boking-progress");
		  progress_hover($progress);
	  });
});

function yes_del(orderid){
	location = "/orders/delete?id=" + orderid;
}

//重新绑定“回报内容”的hover事件
function product_hover($com){
	$com.hover(function() {
		$com.css({"color":"#828282"});
	    $com.parent().parent().siblings('.support-back-con').show();
		$com.parent().parent().siblings(".support-bgc").show();
	},function() {
			$com.css({"color":"#8ab825"});
			$com.parent().parent().siblings(".support-bgc").hide();
			$com.parent().parent().siblings('.support-back-con').hide();
	});
}

//重新绑定“订单详情”的hover事件
function orderDetail_hover($com){
	$com.hover(function() {
		$com.css({"color":"#888888"});
		$com.parent().parent().parent().parent().siblings(".support-bgc").show();
		$com.parent().parent().parent().parent().siblings('.support-address').show();
	},function() {
		$com.css({"color":"#8ab825"});
		$com.parent().parent().parent().parent().siblings(".support-bgc").hide();
		$com.parent().parent().parent().parent().siblings('.support-address').hide();
	});
}

//重新绑定“物流详情”的hover事件
function progress_hover($com){	
	$com.hover(function() {
		$com.css({"color":"#888888"});
		$com.parent().parent().parent().parent().siblings(".support-bgc").show();
		$com.parent().parent().parent().parent().siblings('.support-flow-con').show();
	}, function() {
		$com.css({"color":"#ffc600"});
		$com.parent().parent().parent().parent().siblings(".support-bgc").hide();
		$com.parent().parent().parent().parent().siblings('.support-flow-con').hide();
	});
}

//去评论
function goToCommon(id) {
	var url = "/project/detail/" + id;
	window.open(url,'_blank');
};

//确认收取回报
function confirmProduct(orderid, page){
	$.ajax({
        type: "POST",
        url: "/orders/confirmProduct",
        data:"orderid=" + orderid,
        success: function(msg) {
            if (msg == 'failure') {
            	alert("系统出现并发操作，取消付款操作失败，请刷新页面后查看我的支持列表");
            } else if(msg == 'error'){
            	alert("服务器异常");
            }
        }
    });
	location = "/orders/" + page;
}

function cancelPay(orderid, page){
	if(confirm("订单取消后不可恢复，确定取消？")){		
		$.ajax({
			type: "POST",
			url: "/orders/cancelPay/",
			data:"orderid=" + orderid,
			async:false,
			success: function(msg) {
				if (msg == 'failure') {
					alert("系统出现并发操作，取消付款操作失败，请刷新页面后查看我的支持列表");
				} else if(msg == 'error'){
					alert("服务器异常");
				} else if(msg == 'false'){
					alert("订单取消失败！");
				} 
//             else if(msg == "success"){
//            	 alert("订单取消成功！");
//             }
			}
		});
		location = "/orders/" + page;
	}
}

function paginate(totalPage, pageNumber){
	paginateFn("sub_page_footer", totalPage, pageNumber);
}

function paginateFn(id, totalPage, pageNumber){
	if(totalPage > 1){
		$("#" + id).createPage({
			pageCount:totalPage,
			current:pageNumber,
			backFn:function(p){
				console.log(p);
				location = "/orders/"+p;
			}
		});
	}
}
