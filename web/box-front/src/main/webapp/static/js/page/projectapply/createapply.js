/**
 * 初始化
 * by HECJ
 */
$(function(){
	createapply.init();
	/*
	//实例化一个plupload上传对象
    var uploader = new plupload.Uploader({
        browse_button : 'upload_btn', //触发文件选择对话框的按钮，为那个元素id
        url : '/upload/uploadFile', //服务器端的上传页面地址
        file_data_name:'uploadFile'
    });    

    //在实例对象上调用init()方法进行初始化
    uploader.init();

    //绑定各种事件，并在事件监听函数中做你想做的事
    uploader.bind('FilesAdded',function(uploader,files){
        //每个事件监听函数都会传入一些很有用的参数，
        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
    	this.start();
    });
    uploader.bind('UploadProgress',function(uploader,file){
        //每个事件监听函数都会传入一些很有用的参数，
        //我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
    });
    uploader.bind('UploadComplete',function(uploader,file){
    	//每个事件监听函数都会传入一些很有用的参数，
    	//我们可以利用这些参数提供的信息来做比如更新UI，提示上传进度等操作
    });
    uploader.bind('FileUploaded',function(uploader,file,responseObject){
    	$("input[name=show_afile]").val(file.name);
    	if(responseObject.status == 200){
    		var response = responseObject.response;
    		var data = $.parseJSON(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
    		if(data.code == 200){
       		 	$("input[name=show_afile]").css({"color":""});
	       		$("input[name=afile]").val(data.data.file_name);
	       	}else{
	       		 alert(data.message);
	       	}
    	}
    });
    */
});

var createapply = createapply|| {
	/*
	 * 绑定事件
	 */
	init:function(){
		
		$(".submit input[type=button]").attr("onclick","createapply.submit()");
		
		// $("#aname").on("valuechange", function(e, previous) {
		// 	var aname =$.trim($(this).val());
		// 	var len = aname.length;
		// 	if(len>25){
		// 		$(this).val(aname.substring(0,25));
		// 	}
		// 	$(".title .word-num e").text(len<0?0:len);
		// });
		// $("#descript").on("valuechange", function(e, previous) {
		// 	$(".description").children(".empty2").css({"display":"none"});
		// 	var acontext =$.trim($(this).val());
		// 	var len = acontext.length;
		// 	if(len>200){
		// 		$(this).val(acontext.substring(0,200));
		// 	}
		// 	$(".description .word-num e").text(len<0?0:len);
		// });
		$("#aname").bind("propertychange input blur",function(){
          	var aname =$.trim($(this).val());
          	var len = aname.length;
			if(len>25){ 
			  var nr = $(this).val().substring(0,25); 				 
			  $(this).val(nr); 
			  len=25; 
			}
			$("#name-num").html(len+"/25");
			
		});
		$("#descript").bind("propertychange input blur",function(){   
			$(".description").children(".empty2").css({"display":"none"});    
	        var acontext =$.trim($(this).val());
	        var len = acontext.length;
			if(len>200){ 
			  var nr = $(this).val().substring(0,200); 				 
			  $(this).val(nr); 
			  len=200; 
			}
			$("#desc-num").html(len+"/200");		
		});

		// 标题
		$("#aname").on({
			focus:function(){
				$(".title").children(".empty1").css({"display":"none"});
			},
			blur:function(){
				var aname = this.value;
				if (aname == "") {
					$(this).siblings(".pb-ph").show();
				} else if (aname.length <= 0 || aname.length > 25 ) {
					$(".title").children(".empty1").css({"display":"block"});
				} else {
					$(".title").children(".empty1").css({"display":"none"});
				}
			},
			keyup:function(){
				$(this).siblings(".pb-ph").hide();
			}
		});
		$("#aname").siblings(".pb-ph").click(function(){
			$(this).siblings("input").focus();
		});
		// 描述
		$("#descript").on({
			focus:function(){
				$(".description").children(".empty2").css({"display":"none"});
			},
			blur:function(){
				var acontext = this.value;
				if (acontext == "") {
					$(this).siblings(".pb-ph").show();
				} else if(acontext.length <= 0 || acontext.length > 200 ) {
					$(".description").children(".empty2").css({"display":"block"});
				}else{
					$(".description").children(".empty2").css({"display":"none"});
				}	
			},
			keyup:function(){
				$(this).siblings(".pb-ph").hide();
			}
		});
		$("#descript").siblings(".pb-ph").click(function(){
			$(this).siblings("textarea").focus();
		});
		//联系方式 获取焦点
		$("#aphone").focus(function() {
			$(".contact").children(".empty3").css({"display":"none"});
			$(".contact").children(".empty4").css({"display":"none"});
			$(".contact").css({"margin-bottom":"35px"});
		});
		// 联系方式  失去焦点事件
		$("#aphone").blur(function(){
			var aphone = this.value;
			if(aphone.length == 0){
				$(".contact").children(".empty3").css({"display":"block"});
				$(".contact").children(".empty4").css({"display":"none"});
				$(".contact").css({"margin-bottom":"0"});
				return false;
			}else{
					if(!(/^1[3,4,5,7,8]\d{9}$/).test(aphone) || aphone.length != 11 ){
						$(".contact").children(".empty3").css({"display":"none"});
						$(".contact").children(".empty4").css({"display":"block"});
						$(".contact").css({"margin-bottom":"0"});
						return false;
					}else{
						$(".contact").children(".empty3").css({"display":"none"});
						$(".contact").children(".empty4").css({"display":"none"});
						$(".contact").css({"margin-bottom":"35px"});
					}
			}
		});
		
		$(".ptc-form .title input").blur(function(){
			if (this.value.length <= 0 || this.value.length > 25) {
				$(".title").children(".empty1").css({"display":"block"});
			} else{
				$(".title").children(".empty1").css({"display":"none"});
			}
		});
		
		$(".ptc-form .description textarea").blur(function(){
			if (this.value.length <= 0 || this.value.length > 200) {
				$(".description").children(".empty2").css({"display":"block"});
			}else{
				$(".description").children(".empty2").css({"display":"none"});
			}
		});
	    
	},
	/*
	 * 提交 
	 */
	submit:function(){
		var nextSub = true;
		var params = {};
		var aname = $("#aname").val();
		if (aname.length <= 0 || aname.length > 25) {
			$(".title").children(".empty1").css({"display":"block"});
			nextSub = false;
		} else{
			$(".title").children(".empty1").css({"display":"none"});
		}
		params.aname = aname;
		
		var acontext = $("#descript").val();
		if (acontext.length <= 0 || acontext.length > 200) {
			$(".description").children(".empty2").css({"display":"block"});
			nextSub = false;
		}else{
			$(".description").children(".empty2").css({"display":"none"});
		}
		params.acontext=acontext;

		var aphone = $("#aphone").val();
		
		if(aphone.length == 0){
			$(".contact").children(".empty3").css({"display":"block"});
			$(".contact").children(".empty4").css({"display":"none"});
			$(".contact").css({"margin-bottom":"0"});
			nextSub = false;
		}else{
			if (!(/^1[3,4,5,7,8]\d{9}$/).test(aphone) || aphone.length != 11 ) {
				$(".contact").children(".empty3").css({"display":"none"});
				$(".contact").children(".empty4").css({"display":"block"});
				$(".contact").css({"margin-bottom":"0"});
				nextSub = false;
			}else{
				$(".contact").children(".empty3").css({"display":"none"});
				$(".contact").children(".empty4").css({"display":"none"});
				$(".contact").css({"margin-bottom":"35px"});
			}
		}
		

		params.aphone=aphone;

		var afile = $("input[name=afile]").val();
		params.afile=afile;

		if(!nextSub){
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "/projectapply/saveapply",
			data : params,
			async : true,
			success : function(data) {
				if(data.code == 200){
					$("body").append("<form id='tempForm' style='display:none' method='post' action='/projectapply/apply_result'>"+
							"<input type='text' name='project_id' value='"+data.data.project_id+"'></input>"+
							"</form>");
					$("#tempForm").submit();
				}else{
					alert(data.message);
				}
			}
		});
	}
};


function upload_attr_ajax(){
	$("input[name=show_afile]").val($("#uploadAttrFile").val());
	$.ajaxFileUpload({
	     url: '/upload/uploadAttrFile', //用于文件上传的服务器端请求地址
	     secureuri: false, //是否需要安全协议，一般设置为false
	     fileElementId: 'uploadAttrFile', //文件上传域的ID
	     dataType: 'json', //返回值类型 一般设置为json
	     success: function (data, status) {

	    	 if(data.code == 200){
	    		 $("input[name=show_afile]").css({"color":""});
		       	 $("input[name=afile]").val(data.data.file_name);
	    	 } else{
	    		 alert(data.message);
	    	 }
	     },
	     error: function (data, status, e) {
	    	 alert(e);
	     }
	});
}
