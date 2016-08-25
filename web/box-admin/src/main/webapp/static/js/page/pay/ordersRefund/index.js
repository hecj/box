 	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	
 	    // 绑定refund_group事件
 		$("input[name=refund_group]").click(function(){
 			if(this.checked){
 				$("input[name=order_radio]").each(function(){
 					var ds = $(this).attr("disabled");
 					if(ds != "disabled"){
 						this.checked=true;
 					}
 				});
 			}else{
 				$("input[name=order_radio]").each(function(){
 					this.checked=false;
 				});
 			}
 			
 		});
 	
 	});
 	// 查询
 	function queryFun(){
 		var url = "/pay/ordersRefund/query?";
 		var order_num = jQuery("input[name=order_num]").val();
 		if(order_num.length > 0){
			url = url+"&order_num="+jQuery.trim(order_num);
 		}
 		var trade_no = jQuery("input[name=trade_no]").val();
 		if(trade_no.length > 0){
			url = url+"&trade_no="+jQuery.trim(trade_no);
 		}
 		var status = jQuery("select[name=status]").val();
 		if(status != -1){
			url = url+"&status="+jQuery.trim(status);
 		}
 		var project_name = jQuery("input[name=project_name]").val();
 		if(project_name != -1){
			url = url+"&project_name="+jQuery.trim(project_name);
 		}
 		location= url;
 	}

 	/**
 	 * 提交退款订单
 	 */
 	function doRefundOrderFun(){
 		if($("input[name=order_radio]:checked").size() == 0){
 			alert("请选择需要退款的订单");
 			return ;
 		}
 		var order_refund_ids = "";
 		$("input[name=order_radio]").each(function(){
 			if(this.checked){
 				order_refund_ids = order_refund_ids+","+this.value;
 			}
 		});
 		
 		if(confirm("确定要批量退款吗？")){
 			
 			// 先进行余额退款
 			jQuery.ajax({
				url : "/pay/ordersRefund/doBalanceRefund",
				type : "POST",
				data : {"order_refund_ids":order_refund_ids},
				success : function(data) {
					if(data.code == 200){
						if(data.data.length > 0){
							// 再进行支付宝退款
							$("body").append("<form id='tempForm' style='display:none' method='post' action='/pay/ordersRefund/doOrdersRefund' target='_blank'>"+
				 					"<input type='text' name='order_refund_ids' value='"+data.data+"'></input>"+
				 					"</form>");
				 			$("#tempForm").submit();
						} else{
							alert("订单批量退款成功");
							location.href = "/pay/ordersRefund/query?status=1";
						}
					}else{
						alert(data.message);
					}
				}
 			});
 			
 		}
 		
 	}