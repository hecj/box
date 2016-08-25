$(function(){

	$(".cash-use").click(function(event) {
	    	$(this).children('i').toggleClass('cur');
	    	$(".cash-con01").toggle(400);
	    	$("input[name=coupons_code]").focus(function(event) {
	    		if ($("input[name=coupons_code]").val="输入优惠码") 
	    			{
                      $(this).val("");
	    			}
	    			$(this).css({
	    				color: '#000'
	    			});
	    			$(".cash-save-h").show();
	    	}).blur(function(event) {
	    		
	    		$(this).css({
	    				color: '#000'
	    			});
	    	});;

	 });
     $(".pay-way-wrap").height($(document).height()-610);
	 $(".pay-radio01").click(function(event) {
	 	$(".pay-parssword").toggle(400);
	 	$(".pay-way-alipay").toggleClass('pay-way-alipay-none');
	 	$(".pay-wm").toggleClass('pay-wmnone');
	 });
	 $(".pay-way-alipay .form-control").click(function(event) {
	 	
	 });
	 $(".goback").click(function(event) {
	 		$(".pay-way-wrap").hide();
	 	    $(".pay-way-jump-con").hide();
 			$(".pay-way-alipay .form-control").attr('checked', false);
	 });
	 
	 // 支付方式绑定事件
	 $("input[name=payment_type]").click(function(){
		 if(this.value == 1){
			 
			 // 判断取消和选中
			 if(this.checked){
				 $("#balance_payment_em").show();
				 $(".pay-wm").hide();
				 $(".btn_money").val("确认付款");
			 }else{
				 $("#balance_payment_em").hide();
				 $(".pay-wm").show();
				 $(".btn_money").val("确定，去付款");
			 }
			 
		 }else if(this.value == 2){
			 $("#balance_payment_em").hide();
			 $(".pay-wm").show();
			 $(".btn_money").val("确定，去付款");
		 }
	 });
	 
	 $("input[type='radio']").each(function(index, el) {
	 	$(this).css({
	 		paddingLeft: '0',
	 		
	 		border:'none'
	 	
	 	});
	 });
	 
	 
	// 提示-关闭/取消
	$("#box-alert .feedback-close,#box-alert .feedback-submit").click(function(event) {
		$(".personalsetting-box").hide();
		$("#box-alert").hide();
		$(".pay-way-wrap").hide();
	});
	 
	 /**
	  * 提交
	  */
	 $(".btn_money").click(function(){
		 
		 //付款方式
		 var payment_type = $("input[name=payment_type]:checked").val();
		 if(payment_type == undefined){
			$(".personalsetting-box").hide();
			$("#box-alert").find("p").text("请选择支付方式");
			$("#box-alert").show();
			$(".pay-way-wrap").show();
//			alert("请选择支付方式");
			return false;
		 }
		 // 余额支付
		 if(payment_type == 1){
			 
		 }else if(payment_type == 2){
			 // 支付宝支付
			 $(".pay-way-wrap").show();
			 $(".pay-way-jump-con").show();
		 }
		 return true;
	 });
	 
});

/**
 * 验证付款成功
 */
 function finish_pay(order_num){
	 
	 $.ajax({
			type : "get",
			url : "/orders/detailJSON/"+order_num,
			data : {},
			success : function(data) {
				if (data.code == 200) {
					if(data.data.status == 1){
						location.href="/payment/ordersuccess/"+order_num;
					}else{
						$(".pay-way-wrap").hide();
				 	    $(".pay-way-jump-con").hide();
					}
				}
			}
		});
 }
