 	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	});
 	// 查询
 	function queryProjectFun(){
 		var url = "/project?";
 		var uPhone = jQuery("input[name=uPhone]").val();
 		if(uPhone.length > 0){
			url = url+"&uPhone="+jQuery.trim(uPhone);
 		}
 		var name = jQuery("input[name=name]").val();
 		if(name.length > 0){
 			url = url+"&name="+jQuery.trim(name);
 		}
 		var status = jQuery("select[name=status]").val();
 		if(status != -1){
 			url = url+"&status="+status;
 		}
 		location= url;
 	}
 	
 	function statusFun6(a){
		if(confirm("确定要开始预热吗")){
			location.href="/project/passstatus7/"+a+"-pass";
		}
	}
	
	function statusFun7(a){
		if(confirm("确定要结束预热吗")){
			location.href="/project/passstatus7/"+a+"-nopass";
		}
	}
	
	function projectonline(a){
		if(confirm("确定要项目上线吗")){
			location.href="/project/projectonline/"+a;
		}
	}
	
	function projectshutdown(a){
		if(confirm("确定要项目结束吗")){
			location.href="/project/projectshutdown/"+a;
		}
	}
	
	function balanceFun(project_id){
		if(confirm("确定要结算吗")){
			location.href="/project/doBalance/"+project_id;
		}
	}
 	
