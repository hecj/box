 	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	});
 	// 查询
 	function queryAlipayRecordFun(){
 		var url = "/alipayRecord/query?";
 		var out_trade_no = jQuery("input[name=out_trade_no]").val();
 		if(out_trade_no.length > 0){
			url = url+"&out_trade_no="+jQuery.trim(out_trade_no);
 		}
 		var trade_no = jQuery("input[name=trade_no]").val();
 		if(trade_no.length > 0){
 			url = url+"&trade_no="+jQuery.trim(trade_no);
 		}
 		var buyer_email = jQuery("input[name=buyer_email]").val();
 		if(buyer_email.length > 0){
 			url = url+"&buyer_email="+jQuery.trim(buyer_email);
 		}
 		location= url;
 	}
