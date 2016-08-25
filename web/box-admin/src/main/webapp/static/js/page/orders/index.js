 	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	
 		// 绑定comment_checkbox_group事件
 		$("input[name=refund_checkbox_group]").click(function(){
 			if(this.checked){
 				$("input[name=refund_checkbox]").each(function(){
 					var ds = $(this).attr("disabled");
 					if(ds != "disabled"){
 						this.checked=true;
 					}
 				});
 			}else{
 				$("input[name=refund_checkbox]").each(function(){
 					this.checked=false;
 				});
 			}
 			
 		});
 	
 	});
 	// 查询
 	function queryOrderFun(){
 		var url = "/orders?";
 		var order_num = jQuery("input[name=order_num]").val();
 		if(order_num.length > 0){
			url = url+"&order_num="+jQuery.trim(order_num);
 		}
 		var project_id = jQuery("input[name=project_id]").val();
 		if(project_id.length > 0){
 			url = url+"&project_id="+jQuery.trim(project_id);
 		}
 		var project_name = jQuery("input[name=project_name]").val();
 		if(project_name.length > 0){
 			url = url+"&project_name="+jQuery.trim(project_name);
 		}
 		var status = jQuery("select[name=status]").val();
 		if(status != -1){
 			url = url+"&status="+status;
 		}
 		location= url;
 	}
 	
 	/**
 	 * 添加退款订单
 	 */
 	function addRefundOrderFun(){
 		if($("input[name=refund_checkbox]:checked").size() == 0){
 			alert("请选择需要退款的订单");
 			return ;
 		}
 		var data = "";
 		$("input[name=refund_checkbox]").each(function(){
 			if(this.checked){
 				data = data+"&order_id="+this.value;
 			}
 		});
 		
 		if(confirm("确定要添加退款订单吗？")){
 	 		 
	 		$.ajax({
	 		    type: 'POST',
	 		    url: '/pay/ordersRefund/addRefundOrder' ,
	 		    data: data ,
	 		    success: function(data){
	 		    	if(data.code == 200){
	 		    		alert(data.message);
	 		    		location.href=location.href;
	 		    	}else{
	 		    		alert("处理失败");
	 		    	}
	 		    }
	 		});
 		
 		}
 		
 	}