
 var form_html = '<form action="/project/uploadProductPicture" method="post" enctype="multipart/form-data">'+
		    	'<input type="hidden" name="images"/>'+
		    	'<input type="file" name="product_images" style="display:none" onchange="app.upload_change(this)"/>'+
			    '<div style="width:151px;height:151px;border:1px solid;margin-left:0px">'+
			    '	<img src="" width="150" height="150">'+
			    '</div>'+
		    	'</form>'+
				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
		    	'<a onclick="app.upload_picture(this)">上传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="app.del_picture(this)">删除</a>';

var app = {
		
		project_id : ""
		,
		/**
		 * 提交
		 */
		auditSumit : function (){
			
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
					url : "/project/passproject/"+this.project_id,
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
		,
		/**
		 * 修改项目信息
		 */
		updateSumit : function (){
			var params = "";

			var name = jQuery("input[name='project.name']").val();
			if(name.length == 0){
				alert("请输入项目名称");
				return;
			}
			params += "&project.name="+name;

			var desc = jQuery("textarea[name='project.desc']").val();
			if(desc.length == 0){
				alert("请输入项目描述");
				return;
			}
			params += "&project.desc="+desc;
			var fundgoal = jQuery("input[name='project.fundgoal']").val();
			if(fundgoal.length == 0){
				alert("请输入众筹金额");
				return;
			}else{
				if(!isNaN(fundgoal)){
					if(fundgoal<=0 || fundgoal>=100000000){
						alert("众筹金额超限");
						return;
					}
				} else{
					alert("请输入正确的金额");
					return;
				}
			}
			
			params += "&project.fundgoal="+fundgoal;
			var days = jQuery("input[name='project.days']").val();
			if(days.length == 0){
				alert("请输入众筹天数");
				return;
			}else{
				if(isNaN(days)){
					alert("请输入正确的众筹天数");
					return;
				}else{
					if(days < 1 || days > 60){
						alert("众筹天数5~60天");
						return;
					}
				}
			}
			params += "&project.days="+days;

			var category_id = jQuery("input[name='project.category_id']:checked").val();
			params += "&project.category_id="+category_id;
			//地址
			var province = $("select[name='project.province']").val();
			if(province == -1){
				alert("请选择省份");
				return;
			}
			params += "&project.province="+province;
			
			var city = $("select[name='project.city']").val();
			if(city == -1){
				alert("请选择城市");
				return;
			}
			params += "&project.city="+city;
			
			var cover_image = jQuery("input[name='project.cover_image']").val();
			if(cover_image.length == 0){
				alert("请上传封面图片");
				return;
			}
			params += "&project.cover_image="+cover_image;
			
			//图片
			var images_count = 0;
			jQuery("#picture_table tr:first td").each(function(o){
				var images = jQuery(this).find("form input[name=images]").val();
				if(images.length > 0 ){
					images_count++;
					params += "&images="+images;
				}
			});
			if(images_count < 2 || images_count > 5 ){
				alert("图片个数2-5张");
				return ;
			}

			var video = jQuery("input[name='project.video']").val();
			params += "&project.video="+video;
			
			var context = edit.html();
			if(context.length == 0){
				alert("请输入项目详情");
				return;
			}
			params += "&project.context="+context;
			
			
			jQuery.ajax({
					url : "/project/editProject/"+this.project_id,
					type : "POST",
					data : params,
					success : function(data) {
						if(data.code == 200){
							alert("修改成功");
							location.href="/project";
						}else{
							alert(data.message);
						}
					}
			});
		}	
		,
		/**
		 * 重置信息
		 */
		resetInfo : function (){
			location.href="/project/toPassproject/"+this.project_id;
		}
		,
		/**
		 * 添加图片
		 */
		addPicture : function (){
			jQuery("#picture_table tr:first").append("<td width='170px'>"+form_html+"</td>");
		}
		,
		/**
		 * 删除图片
		 */
		del_picture : function (o){
			jQuery(o).parent().remove();
		}
		,
		/**
		 * 触发上传事件
		 */
		upload_picture : function (o){
			jQuery(o).parent().find("input[type=file]").click();
		}
		,
		/**
		 * 上传图片
		 */
		upload_change : function (o){
			jQuery(o).parent().ajaxSubmit({
				success: function (data) {
					if(data.code == 200){
						jQuery(o).parent().find("img").attr("src",data.data.image_url);
						jQuery(o).parent().find("input[name=images]").val(data.data.image_name);
					}else{
						alert(data.message);
					}
		        },
		        beforeSubmit:function(data){
		        	var file_size = data[0].value.size;
		        	if(file_size>500*1024){
		        		alert("上传文件超限，文件最大500kb");
		        		return false;
		        	}
		        	return true;
		        }
			});
		}
};

var edit ; 

$(function(){
	
	/**
     * ======================================
     * Trumbowyg Config
     * ======================================
     */
	/*
    $.trumbowyg.upload.serverPath = "/upload/fuWenBenUploadFile";
    $("#context").trumbowyg({
        lang: 'zh_cn',
        closable: false,
        fixedBtnPane: false,
        btnsDef: {
            // Customizables dropdowns
            align: {
                dropdown: ['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull'],
                ico: 'justifyLeft'
            },
            image: {
                dropdown: ['insertImage', 'upload', 'base64'],
                ico: 'insertImage'
            }
        },
        btns: ['viewHTML',
            '|', 'formatting',
            '|', 'btnGrp-design',
            '|', 'link',
            '|', 'upload',
            '|', 'btnGrp-justify',
            '|', 'btnGrp-lists',
            '|', 'horizontalRule',
            '|', 'image',
            '|', 'align'],
        btnsAdd: ['foreColor', 'backColor'],
    });
    */
	
	
	/**
	 * 富文本
	 */
	KindEditor.ready(function(K) {
		edit = K.create('#context', {
			height : "352px",
			filterMode: true,
			resizeMode:1,
			resizeType:0,
			allowImageUpload : true, 
			uploadJson :"/upload/kindEditorUploadFile",
			cssPath : [],
			items : ['source',"undo","redo","image","preview","justifyleft","justifycenter",
			         "justifyright","justifyfull","insertorderedlist","insertunorderedlist",
			         "indent","outdent","quickformat","selectall","hr","bold","fontname","fontsize","underline"]
		});
	});
	
    
    // 省份change事件
	$("select[name='project.province']").bind("change",function(){
		var province_id = $(this).val();
		if(province_id == -1){
			// 清除citys
			$("select[name='project.city']").html("<option value=\"-1\">请选择</option>");
			return false;
		}
		// 加载城市
		$.ajax({
			type : "GET",
			url : "/zipcode/findCitys",
			data : "province_id=" + province_id,
			success : function(data) {
				if(data.code == 200){
					var list = data.data;
					$("select[name='project.city']").html("<option value=\"-1\">请选择</option>");
					for(var i = 0;i<list.length;i++){
						var row = list[i];
						$("select[name='project.city']").append("<option value=\""+row.key+"\">"+row.value+"</option>");
					}
				} else{
					alert(data.message);
				}
			}
		});
	});
	
});


	var options = {
	    success: function (data) {
	    	
			if(data.code == 200){
				jQuery("#cover_image").attr("src",data.data.image_url);
				jQuery("input[name='project.cover_image']").val(data.data.image_name);
			}else{
				alert(data.message);
			}
	    },
        beforeSubmit:function(data){
        	var file_size = data[0].value.size;
        	if(file_size>500*1024){
        		alert("上传文件超限，文件最大500kb");
        		return false;
        	}
        	return true;
        }
	};
	
	function uploadFileFun(){
		jQuery("#uploadFileForm").ajaxSubmit(options);
	}
	
	function uploadPictureFun(o){
		jQuery(o).parent().find("input[name=product_images]").click();
	}

