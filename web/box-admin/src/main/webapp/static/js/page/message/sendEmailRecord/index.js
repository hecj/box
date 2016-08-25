 	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	});
 	// 查询
 	function querySendEmailRecordFun(){
 		var url = "/message/sendEmailRecord?";
 		var reciver_email = jQuery("input[name=reciver_email]").val();
 		if(reciver_email.length > 0){
			url = url+"&reciver_email="+jQuery.trim(reciver_email);
 		}
 		var reciver = jQuery("input[name=reciver]").val();
 		if(reciver.length > 0){
 			url = url+"&reciver="+jQuery.trim(reciver);
 		}
 		location= url;
 	}
