 	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	
 		// 绑定comment_checkbox_group事件
 		$("input[name=comment_checkbox_group]").click(function(){
 			if(this.checked){
 				$("input[name=comment_checkbox]").each(function(){
 					this.checked=true;
 				});
 			}else{
 				$("input[name=comment_checkbox]").each(function(){
 					this.checked=false;
 				});
 			}
 			
 		});
 	
 	});
 	// 查询
 	function queryFun(){
 		var url = "/message/comment/?";
 		var content = jQuery("input[name=content]").val();
 		if(content.length > 0){
			url = url+"&content="+jQuery.trim(content);
 		}
 		var project_name = jQuery("input[name=project_name]").val();
 		if(project_name.length > 0){
 			url = url+"&project_name="+jQuery.trim(project_name);
 		}
 		location= url;
 	}
 	
 	// 单个隐藏评论
 	function hiddenCommnent(comment_id){
 		if(comment_id.length == 0){
 			alert("id不能为空");
 			return;
 		}
 		$.ajax({
 		    type: 'POST',
 		    url: '/message/comment/hidden' ,
 		    data: "comment_id="+comment_id ,
 		    success: function(data){
 		    	if(data.code == 200){
 		    		alert("隐藏成功");
 		    		location.href=location.href;
 		    	}else{
 		    		alert("处理失败");
 		    	}
 		    }
 		});
 	}
 	// 单个显示评论
 	function showCommnent(comment_id){
 		if(comment_id.length == 0){
 			alert("id不能为空");
 			return;
 		}
 		$.ajax({
 		    type: 'POST',
 		    url: '/message/comment/show' ,
 		    data: "comment_id="+comment_id ,
 		    success: function(data){
 		    	if(data.code == 200){
 		    		alert("显示成功");
 		    		location.href=location.href;
 		    	}else{
 		    		alert("处理失败");
 		    	}
 		    }
 		});
 	}
 	
    // 单个删除评论
 	function deleteCommnent(comment_id){
 		if(comment_id.length == 0){
 			alert("id不能为空");
 			return;
 		}
 		
 		if(confirm("确定要移除评论吗？")){
 		 
	 		$.ajax({
	 		    type: 'POST',
	 		    url: '/message/comment/delete' ,
	 		    data: "comment_id="+comment_id ,
	 		    success: function(data){
	 		    	if(data.code == 200){
	 		    		location.href=location.href;
	 		    	}else{
	 		    		alert("处理失败");
	 		    	}
	 		    }
	 		});
 		
 		}
 	}
 	
 	/**
 	 * 操作方式  
 	 */
 	function operatorFun(){
 		
 		if($("input[name=comment_checkbox]:checked").size() == 0){
 			alert("请选择评论");
 			return ;
 		}
 		var data = "";
 		$("input[name=comment_checkbox]").each(function(){
 			if(this.checked){
 				data = data+"&comment_id="+this.value;
 			}
 		});
 		
 		var operator_type = $("select[name=operator_type]").val();
 		var url = "";
 		var tip_msg = "";
 		if(operator_type == "1"){
 			// 允许显示
 			url = "/message/comment/show";
 			tip_msg = "确定要显示评论吗？";
 		}else if(operator_type == "2"){
 			// 禁止显示
 			url = "/message/comment/hidden";
 			tip_msg = "确定要隐藏评论吗？";
 		}else if(operator_type == "3"){
 			// 删除评论
 			url = "/message/comment/delete";
 			tip_msg = "确定要删除评论吗？";
 		}
 		
 		if(confirm(tip_msg)){
 	 		 
	 		$.ajax({
	 		    type: 'POST',
	 		    url: url ,
	 		    data: data ,
	 		    success: function(data){
	 		    	if(data.code == 200){
	 		    		location.href=location.href;
	 		    	}else{
	 		    		alert("处理失败");
	 		    	}
	 		    }
	 		});
 		
 		}
 		
 	}
