	
	function auditSumit(user_id){
		
		var params = "";
		var audit_type = jQuery("input[name=audit_type]:checked").val();
		if(audit_type != "pass" && audit_type != "nopass"){
			alert("请选择审核状态");
			return;
		}
		params += "&audit_type="+audit_type;
		
		//jQuery("#submit_btn").attr("disabled",true);
		$.ajax({
				url : "/usercertify/check/"+user_id,
				type : "POST",
				data : params,
				success : function(data) {
					if(data.code == 200){
						alert("处理成功");
						location.href="/usercertify";
					}else{
						//jQuery("#submit_btn").attr("disabled","");
						alert(data.message);
					}
				}
		});
	}		
	
