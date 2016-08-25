	
	function auditSumit(project_id){
		
		var params = "";
		var audit_type = jQuery("input[name=audit_type]:checked").val();
		if(audit_type != "pass" && audit_type != "nopass"){
			alert("请选择审核状态");
			return;
		}
		params += "&audit_type="+audit_type;
		
		var message = jQuery("textarea[name=message]").val();
		params += "&message="+message;
		
		$.ajax({
				url : "/project/passapply/"+project_id,
				type : "POST",
				data : params,
				success : function(data) {
					if(data.code == 200){
						alert("审核成功");
						location.href="/project";
					}else{
						alert(data.message);
					}
				}
		});
	}		
	
