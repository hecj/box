//var form_html = '<form action="/project/uploadProductPicture" method="post" enctype="multipart/form-data">'+
//		    	'<input type="hidden" name="images"/>'+
//		    	'<input type="file" name="product_images" style="display:none" onchange="app.upload_change(this)"/>'+
//			    '<div style="width:151px;height:151px;border:1px solid;margin-left:0px">'+
//			    '	<img src="" width="150" height="150">'+
//			    '</div>'+
//		    	'</form>'+
//				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
//		    	'<a onclick="app.upload_picture(this)">上传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="app.del_picture(this)">删除</a>';
var project = project || {} ;

	
$(function() {
	/*radio单选模拟*/

	// 页面加载回显数据 begin
	var province = $("select[name='project.province']").val();
	if(province != -1){
		$("select[name='project.province']").css({"color":"#000"});
	}
	var city = $("select[name='project.city']").val();
	if(city != -1){
		$("select[name='project.city']").css({"color":"#000"});
	}
	var days = $("input[name='project.days']").val();
	if(days != "1~60"){
		$("input[name='project.days']").css({"color":"#000"});
	}
	var fundgoal = $("input[name='project.fundgoal']").val();
	if(fundgoal != "不少于500元"){
		$("input[name='project.fundgoal']").css({"color":"#000"});
	}
	
	$("input[name='project.video']").css({"color":"#000"});
//	var video = $("input[name='project.video']").val();
//	if(video != "(可选)输入视频地址(支持爱奇艺，腾讯，优酷，土豆，酷6，新浪，搜狐视频)"){
//		$("input[name='project.video']").css({"color":"#000"});
//	}
	var cover_image = $("#upload_cover_div input[name='project.cover_image']").val();
	var input_images_0 = $("input[name=input_images_0]").val();
	var input_images_1 = $("input[name=input_images_1]").val();
	var input_images_2 = $("input[name=input_images_2]").val();
	var input_images_3 = $("input[name=input_images_3]").val();
	var input_images_4 = $("input[name=input_images_4]").val();
	if(cover_image != undefined && cover_image.length > 0){
		 $("#upload_cover_div .cover").css({"display":"block"});
		 $("#cover_pic").css({"display":"block"});
	}
	if(input_images_0 != undefined && input_images_0.length > 0){
		$("input[name=input_images_0]").parent().css({"display":"block"});
	}else{
		$("input[name=input_images_0]").parent().css({"display":"none"});
	}
	if(input_images_1 != undefined && input_images_1.length > 0){
		$("input[name=input_images_1]").parent().css({"display":"block"});
	}else{
		$("input[name=input_images_1]").parent().css({"display":"none"});
	}
	if(input_images_2 != undefined && input_images_2.length > 0){
		$("input[name=input_images_2]").parent().css({"display":"block"});
	}else{
		$("input[name=input_images_2]").parent().css({"display":"none"});
	}
	if(input_images_3 != undefined && input_images_3.length > 0){
		$("input[name=input_images_3]").parent().css({"display":"block"});
	}else{
		$("input[name=input_images_3]").parent().css({"display":"none"});
	}
	if(input_images_4 != undefined && input_images_4.length > 0){
		$("input[name=input_images_4]").parent().css({"display":"block"});
	}else{
		$("input[name=input_images_4]").parent().css({"display":"none"});
	}
	// end
	
	$(".title-lab .radioc").click(function(event) {
		$(this).addClass('current').siblings().removeClass('current');
	});
	/*鼠标滑过问号，提示信息.*/
	$(".default-word i[name='question']").hover(function() {
		$(this).siblings(".reminder-con").toggle();
	});

	$(".word_limit").bind("propertychange input blur",function(){
              
                var count = $(this).val().length;
				if(count>25){ 
				  var nr = $(this).val().substring(0,25); 				 
				  $(this).val(nr); 
				  count=25; 
				}
				$("#nameNum").html(count+"/25");
				
	});
	$(".desc_limit").bind("propertychange input blur",function(){       
        var count = $(this).val().length;
		if(count>40){ 
		  var nr = $(this).val().substring(0,40); 				 
		  $(this).val(nr); 
		  count=40; 
		}
		$("#descNum").html(count+"/40");
		
	});
	
	//项目标题失去焦点事件
	$("input[name='project.name']").blur(function(){
		var name = this.value;
		if(name.length == 0){
			$(this).siblings(".text_r").find(".show_red").show();
		}else{
			$(this).siblings(".text_r").find(".show_red").hide();
		}
	}).focus(function(event) {
		$(this).siblings(".text_r").find(".show_red").hide();
	});
	
	//项目描述失去焦点事件
	$("textarea[name='project.desc']").blur(function(){
		var desc = this.value;
		if(desc.length == 0){
			$(this).siblings(".text_r").find(".show_red").show();
		}else{
			$(this).siblings(".text_r").find(".show_red").hide();
		}
	}).focus(function(event) {
		$(this).siblings(".text_r").find(".show_red").hide();
	});
	
	//众筹天数失去焦点事件
	$("input[name='project.days']").blur(function(){
		
		var days = this.value;
		if(!isNaN(days)){
		
			if(days < 1 || days > 60){

				$(this).siblings(".show_green").hide();
				$(this).siblings(".show_red").hide();
				$(this).siblings(".show_red2").show();

			}else{
				
				$(this).siblings(".show_red").hide();
				$(this).siblings(".show_green").show();
			}
		}else{
			$(this).siblings(".show_green").hide();
			$(this).siblings(".show_red").hide();
			$(this).siblings(".show_red3").show();
		}
		if (days.indexOf('.')>-1){
				$(this).siblings(".show_green").hide();
				$(this).siblings(".show_red").hide();
				$(this).siblings(".show_red3").show();
		}
		if(days == ""){
			this.value = "1~60";
			$(this).css({"color":""});
			$(this).siblings(".show_green").hide();
			$(this).siblings(".show_red").hide();
			$(this).siblings(".show_red1").show();
			
		}
	}).focus(function(){
		$(this).css({"color":"#000"});
		var days = this.value;
		if(days == "1~60"){
			this.value="";
		}
			$(this).siblings(".show_green").show();
			$(this).siblings(".show_red").hide();
	});
	
	//众筹金额失去焦点事件
	$("input[name='project.fundgoal']").blur(function(){
		var fundgoal = this.value;
		if(fundgoal == ""){
	
			this.value = "不少于500元";
			$(this).css({"color":""});
			$(this).siblings(".show_green").hide();
			$(this).siblings(".show_red").hide();
			$(this).siblings(".show_red001").show();
				return false;
		}
		else if(!isNaN(fundgoal)){
			if(fundgoal < 500 || fundgoal > 100000000){
				$(this).siblings(".show_green").hide();
					$(this).siblings(".show_red").hide();
					$(this).siblings(".show_red003").show();
					return false;
			}else{
				$(this).siblings(".show_red").hide();
				
				//if(fundgoal.substring(fundgoal.length-4,fundgoal.length-1)==".00"){
					/*fundgoal = fundgoal.replace(".00","");*/
				//	alert(1);
					 fundgoal=parseInt(fundgoal);
					 fundgoal = fundgoal+".00";
					 $(this).val(fundgoal);
					/*fundgoal=fundgoal.replace(".00","");*/

				//}
				$(this).siblings(".show_green").show();
				return false;
			}
		}else{
			$(this).siblings(".show_green").hide();
			$(this).siblings(".show_red").hide();
			$(this).siblings(".show_red002").show();

		}
		if (fundgoal.indexOf('.00')<0){
				$(this).siblings(".show_green").hide();
				$(this).siblings(".show_red").hide();
				$(this).siblings(".show_red002").show();
		}
		
	}).focus(function(){
		$(this).css({"color":"#000"});
		var fundgoal = this.value;
		if(fundgoal == "不少于500元"){
			this.value="";
		}else{
			fundgoal = fundgoal.replace(".00","");
			$(this).val(fundgoal);
		}
		$(this).siblings(".show_green").show();
			$(this).siblings(".show_red").hide();
	});
	
	//视频失去焦点

$("input[name='project.video']").keyup(function(event) {
    	 $(".msg-placeholder").hide();
    }).blur(function(event) {
    	var name_val=$("input[name='project.video']").val();
    	name_val=$.trim(name_val);
    	if (name_val=="") {
         $(".msg-placeholder").show();
    	}
    });
	 $(".msg-placeholder").click(function(event) {
	 	$("input[name='project.video']").focus();
	 });

	$("input[name='project.video']")
	//地址失去焦点事件
	$("select[name='project.province']").blur(function(){
		var province = this.value;
		if(province == -1){
			$(this).parent().siblings(".show_red").show();
			$(this).css({"color":""});
		}else{
			$(this).parent().siblings(".show_red").hide();
			$(this).css({"color":"#000"});
			
			if($("select[name='project.city']").val() == -1){
				$("select[name='project.city']").css({"color":""});
			}
		}
	});
	//地址获取焦点事件
	$("select[name='project.province']").focus(function(){
		$(this).css({"color":"#000"});
	});
	$("select[name='project.city']").blur(function(){
		var city = this.value;
		if(city == -1){
			$(this).parent().siblings(".show_red").show();
			$(this).css({"color":""});
		}else{
			$(this).parent().siblings(".show_red").hide();
			$(this).css({"color":"#000"});
		}
	});
	$("select[name='project.city']").focus(function(){
		$(this).css({"color":"#000"});
	});
	
	// 封面删除事件
	$("#upload_cover_div .cover .img_one i").click(function(){
		$("#upload_cover_div .cover").css({"display":"none"});
		$("#upload_cover_div dt img").attr("src","");
		$("#upload_cover_div dt input[name='project.cover_image']").val("");
	});
	
	// 展示图片删除事件
	$("#upload_images .cover .img_one i").click(function(){
		 $(this).siblings("img").attr("src","");
		 $(this).siblings("input").val("");
		 $(this).parent().css({"display":"none"});
		
		 var arrayValue = [
		     $("input[name=input_images_0]").val(),
		     $("input[name=input_images_1]").val(),
		     $("input[name=input_images_2]").val(),
		     $("input[name=input_images_3]").val(),
		     $("input[name=input_images_4]").val()
		 ];
		 
		 var sum = 0;
		 $.each(arrayValue,function(n,value){
			var input_img = "input[name=input_images_"+n+"]";
			if(arrayValue[n].length == 0){
				$(input_img).parent().css({"display":"none"});
				sum++;
			}
		 });
		 if(sum!=0&&sum<5){
			$(".img_one_only span").text(sum);
			$(".img_one_only").show(); 
		 }else{
			$(".img_one_only").hide();  
		 }
		
	});
	
	// 省份change事件
	$(".default-word-address select[name='project.province']").bind("change",function(){

		var province_id = $(this).val();
		if(province_id == -1){
			// 清除citys
			$(".default-word-address select[name='project.city']").html("<option value=\"-1\">请选择</option>");
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
					$(".default-word-address select[name='project.city']").html("<option value=\"-1\">请选择</option>");
					for(var i = 0;i<list.length;i++){
						var row = list[i];
						$(".default-word-address select[name='project.city']").append("<option value=\""+row.key+"\">"+row.value+"</option>");
					}
				} else{
					alert(data.message);
				}
			}
		});
	});
	
	/*
	//上传封面
    jFileUpLoad({
    	 submitBtn: $("#upload_cover_div dd span.default-upload"),
         uploadurl: '/upload/uploadFile',
         uploadname: 'uploadFile',
         //上传成功后回调
         complete: function (response) {
        	 var data = $.parseJSON(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
        	 if(data.code == 200){
        		 $("#upload_cover_div dt img").attr("src",data.data.file_url);
        		 $("#upload_cover_div dt input[name='project.cover_image']").val(data.data.file_name);
        		 $("#upload_cover_div dd .show_red").hide();
        		 $("#upload_cover_div dd .show_green").show();
        		 $("#upload_cover_div .cover").css({"display":"block"});
        		 
        	 }else{
        		 alert(data.message);
        	 }
         },
         //点击提交未上传时回调
         beforeUpLoad: function () {
        	 // 判断文件名
        	 var file_name = $(this)[0].value;
        	 if(!FileUtil.isPicture(file_name)){
        		 alert("请上传合法的图片");
        		 return false;
        	 }
        	 
        	 var limitSize = 5 * 1024 * 1024;
        	 //判断文件大小
        	 var size = this.files[0].size;
        	 if(size > limitSize){
        		 alert("文件大小超限");
        		 return false;
        	 }
        	 
         },
         //点击提交上传后回调
         afterUpLoad: function () {
        	 var obj = $(this);
         }
     });
    */
	
	/*
    //上传展示图片
    jFileUpLoad({
    	 submitBtn: $("#upload_images dd span.default-upload"),
         uploadurl: '/upload/uploadFile',
         uploadname: 'uploadFile',
         //上传成功后回调
         complete: function (response) {
        	 var data = $.parseJSON(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
        	 if(data.code == 200){        		 
        		 $("#upload_images dd .show_red").hide();
     			 $("#upload_images dd .show_green").show();
     			 
     			 var arrayValue = [
     			     $("input[name=input_images_0]").val(),
     			     $("input[name=input_images_1]").val(),
     			     $("input[name=input_images_2]").val(),
     			     $("input[name=input_images_3]").val(),
     			     $("input[name=input_images_4]").val()
     			 ];
     			 
     			 //已上传图片数
     			 var hasNum = 0;
     			 $.each(arrayValue,function(n,value){
     				if(arrayValue[n].length != 0){
     					hasNum ++;
     				} 
     			 });
     			 
     			 if(hasNum>=5){
     				$("#upload_images dd .show_red span").text("说明图片最多上传5张");
     				$("#upload_images dd .show_green").hide();
     				$("#upload_images dd .show_red").show();
     				return;
     			 }
     			 
     			 $.each(arrayValue,function(n,value){
     				if(arrayValue[n].length == 0){
     					var img = "#images_"+n;
     					var input_img = "input[name=input_images_"+n+"]";
	     				$(img).attr("src", data.data.file_url);
						$(input_img).val(data.data.file_name);
						$(input_img).parent().css({"display":"block"})
						arrayValue[n] = $(input_img).val();
						return false;
     				}	
     			 });
     			 var sum = 0;
     			 $.each(arrayValue,function(n,value){
 					var input_img = "input[name=input_images_"+n+"]";
 					if(arrayValue[n].length == 0){
 						$(input_img).parent().css({"display":"none"});
 						sum++;
 					}
     			 });
     			 if(sum!=0&&sum<5){
     				$(".img_one_only span").text(sum);
        			$(".img_one_only").show(); 
     			 }else{
     				$(".img_one_only").hide();  
     			 }
        	 }else{
        		 alert(data.message);
        	 }
         },
         //点击提交未上传时回调
         beforeUpLoad: function () {
        	 
        	 // 判断文件名
        	 var file_name = $(this)[0].value;
        	 if(!FileUtil.isPicture(file_name)){
        		 alert("请上传合法的图片");
        		 return false;
        	 }
        	 
        	 var limitSize = 5 * 1024 * 1024;
        	 //判断文件大小
        	 var size = this.files[0].size;
        	 if(size > limitSize){
        		 alert("文件大小超限");
        		 return false;
        	 }
        	 
         },
         //点击提交上传后回调
         afterUpLoad: function () {
        	 var obj = $(this);
         }
     });
    */
    
    /**
	 * 富文本
	 */
	var edit ; 
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
	
	/*提交表单*/
	$(".submit-btn").click(function(event) {
		
		var subNext = true;
		
		var data_params = {};
		data_params.id = project.id;
		
		var category_id = $("input[name='project.category_id']:checked").val();
		if(category_id == undefined){
			subNext = false;
			alert("请选择类别");
		}
		data_params.category_id = category_id;
		
		var name = $("input[name='project.name']").val();
		if(name == "" || name.length == 0 ){
			//alert("请输入项目名称");
			subNext = false;
		}
		// if(name.length > 25){
		// 	//alert("项目名称不超过25个字");
		// 	subNext = false;
		// }
      subNext = name.length>25?false:true;

		data_params.name = name;
		
		var desc = $("textarea[name='project.desc']").val();
		if(desc == "" || desc.length == 0){
			//alert("项目描述不能为空");
			$("textarea[name='project.desc']").siblings(".text_r").find(".show_red").show();
			subNext = false;
		}else{
			$("textarea[name='project.desc']").siblings(".text_r").find(".show_red").hide();
		}
		if(desc.length > 200){
			//alert("项目描述不能大于200个字");
			subNext = false;
		}
		data_params.desc = desc;
		//众筹天数
		var $days = $("input[name='project.days']");
		var days = $days.val();
	/*	if(!isNaN(days)){
			if(days < 1 || days > 60){
				$($days).siblings(".show_green").hide();
				$($days).siblings(".show_red2").show();
				subNext = false;
			}else{
				$($days).siblings(".show_red").hide();
				$($days).siblings(".show_green").show();
			}
		}else{
			$($days).siblings(".show_green").hide();
			$($days).siblings(".show_red3").show();
			subNext = false;
		}*/
		if(days=="1~60"){
			$($days).siblings(".show_red1").show();
			$($days).siblings(".show_green").hide();
			subNext = false;
		}
		/*if(days < 1 || days > 60){
				$(this).siblings(".show_green").hide();
				$(this).siblings(".show_red").hide();
				$(this).siblings(".show_red2").show();

			}else{
				$(this).siblings(".show_red").hide();
				$(this).siblings(".show_green").show();
			}
		}else{
			$(this).siblings(".show_green").hide();
			$(this).siblings(".show_red3").show();
		}
		*/
		if(days == ""){
			this.value = "1~60";
			$(this).css({"color":""});
			$(this).siblings(".show_green").hide();
			$(this).siblings(".show_red").hide();
			$(this).siblings(".show_red1").show();
			
		}
		data_params.days = days;
		
		// 众筹金额
		var $fundgoal = $("input[name='project.fundgoal']");
		var fundgoal = $fundgoal.val();
	/*	if(!isNaN(fundgoal)){
			if(fundgoal < 500 || fundgoal > 100000000){
				$($fundgoal).siblings(".show_red").show();
				subNext = false;
			}else{
				$($fundgoal).siblings(".show_red").hide();
			}
		}else{
			$($fundgoal).siblings(".show_red").show();
			subNext = false;
		}*/
		if(fundgoal=="不少于500元"){
				$($fundgoal).siblings(".show_red").hide();
					$($fundgoal).siblings(".show_green").hide();
				$($fundgoal).siblings(".show_red001").show();
				subNext = false;
			}
		data_params.fundgoal = fundgoal;
		
		//省份
		var $province = $("select[name='project.province']");
		var province = $province.val();
		if(province == -1){
			$($province).parent().siblings(".show_red").show();
			subNext = false;
		}else{
			$($province).parent().siblings(".show_red").hide();
		}
		data_params.province = province;
		
		var $city = $("select[name='project.city']");
		var city = $city.val();
		if(city == -1){
			$($city).parent().siblings(".show_red").show();
			subNext = false;
		}else{
			$($city).parent().siblings(".show_red").hide();
		}
		data_params.city = city;

		//判断封面
		var $cover_image = $("#upload_cover_div input[name='project.cover_image']");
		var cover_image = $cover_image.val();
		if(cover_image.length == 0){
			$("#upload_cover_div .show_green").hide();
			$("#upload_cover_div .show_red").show();
			subNext = false;
		}else{
			$("#upload_cover_div .show_red").hide();
			$("#upload_cover_div .show_green").show();
		}
		data_params.cover_image = cover_image;
		//
		 var arrayValue = [
		     $("input[name=input_images_0]").val(),
		     $("input[name=input_images_1]").val(),
		     $("input[name=input_images_2]").val(),
		     $("input[name=input_images_3]").val(),
		     $("input[name=input_images_4]").val()
		 ];
		     			 
		 //已上传图片数
		 var hasNum = 0;
		 data_params.images = "";
		 $.each(arrayValue,function(n,value){
			if(arrayValue[n].length != 0){
				hasNum ++;
				data_params.images += ","+arrayValue[n];
			} 
		 });
		
		if(hasNum <= 1){
			$("#upload_images dd .show_green").hide();
			$("#upload_images dd .show_red span").text("说明图片至少2张");
			$("#upload_images dd .show_red").show();
			$(".img_one_only span").text(5-hasNum);
			$(".img_one_only").show(); 
			subNext = false;
		}else{
			$("#upload_images dd .show_red").hide();
			$("#upload_images dd .show_green").show();
		}
		data_params.images = data_params.images.substring(1);
		
		var video = $("input[name='project.video']").val();
		data_params.video = video;
		
		var context = edit.html();
		if(context.length == 0){
			subNext = false;
		}
		data_params.context = context;
		
		if(!subNext){
			return false;
		}
		
		$.ajax({
            type:"post",  
            url:"/projectapply/saveproject",  
            data:data_params,  
            success:function(data){  
               if(data.code == 200){
            	   $("body").append("<form id='tempForm' style='display:none' method='post' action='/projectapply/save_result'>"+
							"<input type='text' name='project_id' value='"+data.data.project_id+"'></input>"+
							"</form>");
					$("#tempForm").submit();
               }else{
            	   alert(data.message);
               }
            }  
        });
		return false;
	});
});

// 上传封面图片
function upload_convert(){
	$("#uploadFile").click();
}

function upload_convert_ajax(){
	
	// 判断文件名
	 var file_name = $("#uploadFile")[0].value;
	 if(!FileUtil.isPicture(file_name)){
		 alert("请上传合法的图片");
		 return false;
	 }
	 
//	 var limitSize = 5 * 1024 * 1024;
//	 //判断文件大小
//	 var size = $("#uploadFile")[0].files[0].size;;
//	 if(size > limitSize){
//		 alert("文件大小超限");
//		 return false;
//	 }
	 
	$.ajaxFileUpload({
	     url: '/upload/uploadFile2', //用于文件上传的服务器端请求地址
	     secureuri: false, //是否需要安全协议，一般设置为false
	     fileElementId: 'uploadFile', //文件上传域的ID
	     dataType: 'json', //返回值类型 一般设置为json
	     success: function (data, status) {

	    	 if(data.code == 200){
	    		 $("#upload_cover_div dt img").attr("src",data.data.file_url);
	    		 $("#upload_cover_div input[name='project.cover_image']").val(data.data.file_name);
	    		 $("#upload_cover_div dd .show_red").hide();
	    		 $("#upload_cover_div dd .show_green").show();
	    		 $("#upload_cover_div .cover").css({"display":"block"});
	    	 } else{
	    		 alert(data.message);
	    	 }
	     },
	     error: function (data, status, e) {
	    	 alert(e);
	     }
	});
}

// 上传说明图片
function upload_images(){
	$("#upload_images input[name=uploadFile]").click();
}
function upload_images_ajax(){
	// 判断文件名
	 var file_name = $("#upload_images input[name=uploadFile]")[0].value;
	 if(!FileUtil.isPicture(file_name)){
		 alert("请上传合法的图片");
		 return false;
	 }
	 
//	 var limitSize = 5 * 1024 * 1024;
//	 //判断文件大小
//	 var size = $("#uploadFile")[0].files[0].size;;
//	 if(size > limitSize){
//		 alert("文件大小超限");
//		 return false;
//	 }
	 
	$.ajaxFileUpload({
	     url: '/upload/uploadFile2', //用于文件上传的服务器端请求地址
	     secureuri: false, //是否需要安全协议，一般设置为false
	     fileElementId: 'upload_images_File', //文件上传域的ID
	     dataType: 'json', //返回值类型 一般设置为json
	     success: function (data, status) {

	    	 $("#upload_images dd .show_red").hide();
 			 $("#upload_images dd .show_green").show();
 			 
 			 var arrayValue = [
 			     $("input[name=input_images_0]").val(),
 			     $("input[name=input_images_1]").val(),
 			     $("input[name=input_images_2]").val(),
 			     $("input[name=input_images_3]").val(),
 			     $("input[name=input_images_4]").val()
 			 ];
 			 
 			 //已上传图片数
 			 var hasNum = 0;
 			 $.each(arrayValue,function(n,value){
 				if(arrayValue[n].length != 0){
 					hasNum ++;
 				} 
 			 });
 			 
 			 if(hasNum>=5){
 				$("#upload_images dd .show_red span").text("说明图片最多上传5张");
 				$("#upload_images dd .show_green").hide();
 				$("#upload_images dd .show_red").show();
 				return;
 			 }
 			 
 			 $.each(arrayValue,function(n,value){
 				if(arrayValue[n].length == 0){
 					var img = "#images_"+n;
 					var input_img = "input[name=input_images_"+n+"]";
     				$(img).attr("src", data.data.file_url);
					$(input_img).val(data.data.file_name);
					$(input_img).parent().css({"display":"block"})
					$("#cover_pic").css({"display":"block"});
					arrayValue[n] = $(input_img).val();
					return false;
 				}	
 			 });
 			 var sum = 0;
 			 $.each(arrayValue,function(n,value){
					var input_img = "input[name=input_images_"+n+"]";
					if(arrayValue[n].length == 0){
						$(input_img).parent().css({"display":"none"});
						sum++;
					}
 			 });
 			 if(sum!=0&&sum<5){
 				$(".img_one_only span").text(sum);
    			$(".img_one_only").show(); 
 			 }else{
 				$(".img_one_only").hide();  
 			 }

	     },
	     error: function (data, status, e) {
	    	 alert(e);
	     }
	});
}

