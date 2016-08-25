 	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	});
 	// 查询
 	function querySendSmsRecordFun(){
 		var url = "/message/sendSmsRecord?";
 		var phone = jQuery("input[name=phone]").val();
 		if(phone.length > 0){
			url = url+"&phone="+jQuery.trim(phone);
 		}
 		location= url;
 	}
