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
 	function queryWithdrawalsFun(){
 		var url = "/pay/withdrawals/manager?";
 		var nickname = jQuery("input[name=nickname]").val();
 		if(nickname.length > 0){
			url = url+"&nickname="+jQuery.trim(nickname);
 		}
 		var phone = jQuery("input[name=phone]").val();
 		if(phone.length > 0){
 			url = url+"&phone="+jQuery.trim(phone);
 		}
 		var amount = jQuery("input[name=amount]").val();
 		if(amount.length > 0){
 			url = url+"&amount="+jQuery.trim(amount);
 		}
 		var status = jQuery("select[name=status]").val();
 		if(status != -1){
 			url = url+"&status="+jQuery.trim(status);
 		}
 		var start_time = jQuery("input[name=start_time]").val();
 		if(start_time.length > 0){
 			url = url+"&start_time="+jQuery.trim(start_time);
 		}
 		var end_time = jQuery("input[name=end_time]").val();
 		if(end_time.length > 0){
 			url = url+"&end_time="+jQuery.trim(end_time);
 		}
 		location= url;
 	}

 	/**
 	 * 提交提现
 	 */
 	function doWithdrawalsFun(){
 		if($("input[name=order_radio]:checked").size() == 0){
 			alert("请选择提现的订单");
 			return ;
 		}
 		var withdrawals_ids = "";
 		$("input[name=order_radio]").each(function(){
 			if(this.checked){
 				withdrawals_ids = withdrawals_ids+","+this.value;
 			}
 		});
 		if(confirm("确定要批量提现吗？")){
 			
			$("body").append("<form id='tempForm' style='display:none' method='post' action='/pay/withdrawals/doWithdrawals' target='_blank'>"+
						"<input type='text' name='withdrawals_ids' value='"+withdrawals_ids+"'></input>"+
				 		"</form>");
			$("#tempForm").submit();
 			
 		}
 		
 	}