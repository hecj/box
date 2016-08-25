	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	});
 	// 查询
 	function queryDeliveryFun(){
 		var url = "/orders/deliveryList?";
 		var delivery_flow_no = $.trim($("input[name=delivery_flow_no]").val());
 		if(delivery_flow_no.length > 0){
			url = url+"&delivery_flow_no="+delivery_flow_no;
 		}
 		var delivery_no = $.trim($("input[name=delivery_no]").val());
 		if(delivery_no.length > 0){
 			url = url+"&delivery_no="+delivery_no;
 		}
 		var project_name = $.trim($("input[name=project_name]").val());
 		if(project_name.length > 0){
 			url += "&project_name="+project_name;
 		}
 		
 		var delivery_name = $.trim($("input[name=delivery_name]").val());
 		if(delivery_name.length > 0){
 			url = url+"&delivery_name="+delivery_name;
 		}
 		var status = $("select[name=status]").val();
 		if(status != -1){
 			url = url+"&status="+status;
 		}
 		location= url;
 	}
