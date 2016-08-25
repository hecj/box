	
	$(function(){
      	$("#usual1 ul").idTabs(); 
		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
	});


	function addProductFun(project_id){
		
		var params = "";
		var type = jQuery("input[name='product.type']:checked").val();
		if(type != 0 && type !=1){
			alert("请选择回报类别");
			return;
		}
		
		params += "product.type="+type;
		
		var fund = jQuery("input[name='product.fund']").val();
		if(fund.length == 0){
			alert("请输入金额");
			return;
		} else{
			if(!isNaN(fund)){
				if(fund<0.01 || fund>=100000000){
					alert("众筹金额超限");
					return;
				}
			} else{
				alert("请输入正确的金额");
				return;
			}
		}
		
		params += "&product.fund="+fund;
		
		var desc = jQuery("textarea[name='product.desc']").val();
		if(desc.length == 0){
			alert("请输入描述");
			return;
		}
		
		params += "&product.desc="+desc;
		
		var product_img = jQuery("input[name=product_img]").val();
		if(product_img.length == 0){
			alert("请上传文件");
			return;
		}
		params += "&product.picture="+product_img;
		
		var totalnum = jQuery("input[name='product.totalnum']").val();
		if(totalnum.length == 0){
			alert("请输入限定名额");
			return;
		}
		
		params += "&product.totalnum="+totalnum;
		
		var postage = jQuery("input[name='product.postage']").val();
		if(postage.length == 0){
			alert("请输入运费");
			return;
		}
		
		params += "&product.postage="+postage;
		
		/*
		var is_invoice = jQuery("input[name='product.is_invoice']:checked").val();
		if(is_invoice != 0 && is_invoice !=1){
			alert("请选择开发票");
			return;
		}
		params += "&product.is_invoice="+is_invoice;
		*/
		
		var remark = jQuery("input[name='product.remark']").val();
		params += "&product.remark="+remark;
		var send_days = jQuery("input[name='product.send_days']").val();
		if(send_days.length == 0){
			alert("请输入回报时间");
			return;
		}
		params += "&product.send_days="+send_days;
		$.ajax({
				url : "/project/addProduct/"+project_id,
				type : "POST",
				data : params,
				success : function(data) {
					if(data.code == 200){
						alert("添加成功");
						location.href="/project/productSet/"+project_id;
					}else if(data.code == -1){
						alert("筹款金额配置超限，请确认后设置");
					}else{
						alert(data.message);
					}
				}
		});
	}		
	
	
	function delProduct(project_id,product_id){
		if(confirm("确定要删除吗")){
			location.href="/project/delProduct/"+project_id+"-"+product_id;
		}
	}
	
	// 重置
	function resetFormFun(){
		jQuery("input[name='product.type']").attr("checked",false);
		jQuery("input[name='product.is_invoice']").attr("checked",false);
		jQuery("input[name='product.fund']").val("");
		jQuery("textarea[name='product.desc']").val("");
		jQuery("input[name='product.remark']").val("");
		jQuery("input[name='product.totalnum']").val(0);
		jQuery("input[name='product.postage']").val(0);
		jQuery("input[name='product.send_days']").val(0);
		jQuery("input[name=product_img]").val("");
		jQuery("#product_img").attr("src","/");
	}
	
    function uploadFileFun(){
    	$.ajaxFileUpload({
	   	     url: '/upload/uploadFile', //用于文件上传的服务器端请求地址
	   	     secureuri: false, //是否需要安全协议，一般设置为false
	   	     fileElementId: 'id_fileName', //文件上传域的ID
	   	     dataType: 'json', //返回值类型 一般设置为json
	   	     success: function (data, status) {
	
	   	    	if(data.code == 200){
					jQuery("#product_img").attr("src",data.data.image_url);
					jQuery("input[name=product_img]").val(data.data.image_name);
				}else{
					alert(data.message);
				}
	   	     },
	   	     error: function (data, status, e) {
	   	    	 alert(e);
	   	     }
	   	});
    }
    
    function uploadPictureFun(){
    	jQuery("#id_fileName").click();
    }
    
