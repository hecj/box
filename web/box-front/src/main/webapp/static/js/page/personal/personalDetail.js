$(document).ready(function(){
	
	$(".personal-menu").find("li").eq(2).addClass("current").siblings().removeClass("current");

	/*radio*/
	$(".gender .radioc").click(function(event) {
		$(this).addClass('current').siblings().removeClass('current');
	});
	$(":radio[name='sex']:checked").parent("label").addClass('current').siblings().removeClass('current');
	
	/*table栏切换*/
	$(".pcs2-personal-setting-menu ul li a").click(function(event){
        $(this).addClass("current").parent().siblings().children("a").removeClass("current");
        var num=$(this).parent().index();
        if(num) {
        	$(".pcs2-psm"+num+"").css({"display":"block"}).siblings().css({"display":"none"});
        } else {
        	$(".pcs2-psm0").css({"display":"block"}).siblings().css({"display":"none"});
        	/*$(this).siblings(".pdm-num").css({"display":"block"});*/
        }
    });
	
	 /*用户名验证*/
	$("input[name=nickname]").css({"color":"#000"});
	$(".name-input").on({
		focus:function(){
			var aname = this.value;
			$(this).css({"border-color":"#a1de00","color":"#000"});
			if (aname == "") {
				$(this).val("");
				$(this).css({"color":"#000"});
				$(this).parent().siblings(".pb-show").show();
				$(this).parent().siblings(".pb-error").hide();
				$(this).parent().siblings(".pb-empty").hide();				
			};
		},
		blur:function(){
			var aname = this.value;			
			if (aname == "") {
				$(this).css({"border-color":"#f50","color":"#000"});
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-empty").show();
				$(this).parent().siblings(".pb-error").hide();
				return;
			} else {
				if(aname.length<4||aname.length>12){
					$(this).css({"border-color":"#f50","color":"#000"});
					$(this).parent().siblings(".pb-show").hide();
					$(this).parent().siblings(".pb-empty").show();
					$(this).parent().siblings(".pb-error").hide();
					return;
				} else if(!verifyNickname(aname)){
					$(this).css({"border-color":"#f50","color":"#000"});
					$(this).parent().siblings(".pb-show").hide();
					$(this).parent().siblings(".pb-empty").hide();
					$(this).parent().siblings(".pb-error").show();
					return;
				} else {
					$(this).css({"border-color":"#d5d5d5"});
					$(this).parent().siblings(".pb-show").hide();
					$(this).parent().siblings(".pb-empty").hide();
					$(this).parent().siblings(".pb-error").hide();
				};
			}
		}
	});  
	
	// 个人说明
	$("input[name=note]").on({
		keyup:function(){
			$(this).siblings(".pb-ph").hide();
		},
		blur:function(){
			var note = $("input[name=note]").val();
			if (note == "") {
				$(this).siblings(".pb-ph").show();
			};
		}
	})
	$("input[name=note]").siblings(".pb-ph").click(function(){
		$(this).siblings("input").focus();
	});

	/*提交验证*/
	$(".submit-btn").click(function(){
		var name_input = $(".name-input").val();
		if (name_input == "") {
			$(".name-input").parent().siblings(".pb-show").hide();
			$(".name-input").parent().siblings(".pb-error").hide();
			$(".name-input").parent().siblings(".pb-empty").show();
			$(".name-input").css({"border-color":"#f00"});
			return;
		}else {
			if(name_input.length<4||name_input.length>12){
				$(this).css({"border-color":"#f50","color":"#999"});
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-empty").show();
				$(this).parent().siblings(".pb-error").hide();
				return;
			}else if(!verifyNickname(name_input)){
				$(this).css({"border-color":"#f50","color":"#999"});
				$(this).parent().siblings(".pb-show").hide();
				$(this).parent().siblings(".pb-empty").hide();
				$(this).parent().siblings(".pb-error").show();
				$(this).attr("placeholder","");
				return;
			}else{
				$.ajax({
					url : "/personal/updatePersonalDetail",
					type : "POST",
					dataType : "json",
					data : $('#personalDetail').serialize(),
					async:false,
					success : function(data) {
						if (data.code == 200) {
							$("#box-alert .feedback-success-con p").text("保存成功");
							$("#box-alert").show();
							// 成功-关闭/确认
						}else{
							$("#box-alert .feedback-success-con p").text("保存失败");
							$("#box-alert").show();
						}
					}
				});
			};
		};
	});
	
	
	
	
	//上传头像
//	jFileUpLoad({
//	     submitBtn: $("#selectPicture"),
//	     uploadurl: '/upload/uploadFile',
//	     uploadname: 'uploadFile',
//	     //上传成功后回调
//	     complete: function (response) {
//	    	 var data = $.parseJSON(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
//	    	 if(data.code == 200){
//	    		 $("#prePicture").attr("src",data.data.file_url);
//	    		 $("#picture").val(data.data.file_url);
//	    	 }else{
//	    		 alert(data.message);
//	    	 }
//	     },
//	     //点击提交未上传时回调
//	     beforeUpLoad: function () {
//	    	 // 判断文件名
//	    	 var file_name = $(this)[0].value;
//	    	 var limitSize = 5 * 1024 * 1024;
//	    	 //判断文件大小
//	    	 var size = this.files[0].size;
//	    	 if(size > limitSize){
//	    		 alert("文件大小超限");
//	    		 return false;
//	    	 }
//	     },
//	     //点击提交上传后回调
//	     afterUpLoad: function () {
//	    	 
//	     }
//	});
//	
	// 省份change事件
	$("#province").bind("change",function(){
		var province_id = $(this).val();
		if(province_id == -1){
			// 清除citys
			$("#city").html("<option value=\"-1\">请选择</option>");
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
					$("#city").html("<option value=\"-1\">请选择</option>");
					for(var i = 0;i<list.length;i++){
						var row = list[i];
						$("#city").append("<option value=\""+row.key+"\">"+row.value+"</option>");
					}
				} else{
					alert(data.message);
				}
			}
		});
	});
	
});



//昵称失去焦点之后
function verifyNickname(val) {
	var nickname = val;
	var flag = false;
	$.ajax({
		type : "POST",
		url : "/personal/verifyNickname",
		data: "nickname="+nickname,
		async:false,
		success : function(msg) {
			if (msg.code == 200) {
				flag = true;
			} else {
				flag = false;
			}
		}
	})
	return flag;
}



function findDay(year,month,day){
	$("#birthDay").empty();
	// 判断是否为闰年
	var isLear = isLearYear(year);
	var count = MonthDayNum(isLear,month);
	
	var str = "";
	for(var i=1;i<=count;i++){
		str +="<option value="+i+">"+i+"日</option>";
	}
	if(day==null||day==""){
		day=1;
	}
	$("#birthDay").append(str);
	$("#birthDay").find("option[value="+day+"]").attr("selected",true);
}

//判断是不是闰年
function isLearYear(year){
	if((year%400==0)||(year%4==0&&year%100!=0)){
		return true;
	}else{
		return false;
	}
}
//获取每个月天数
function MonthDayNum(isLear,month){
	var count = 30;
	if(isLear){
		if(month==2){
			count = 29;
		}else if(month == 4|| month == 6 || month == 9 || month ==11){
			count = 30;
		}else{
			count = 31;
		}
	}else{
		if(month==2){
			count = 28;
		}else if(month == 4|| month == 6 || month == 9 || month ==11){
			count = 30;
		}else{
			count = 31;
		}
	}
	return count;
}

//上传头像
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
	 
	$.ajaxFileUpload({
	     url: '/upload/uploadFile2', //用于文件上传的服务器端请求地址
	     secureuri: false, //是否需要安全协议，一般设置为false
	     fileElementId: 'uploadFile', //文件上传域的ID
	     dataType: 'json', //返回值类型 一般设置为json
	     success: function (data, status) {

	    	 if(data.code == 200){
	    		 $("#prePicture").attr("src",data.data.file_url);
	    		 $("#picture").val(data.data.file_url);
//	    		 $("#upload_cover_div dt img").attr("src",data.data.file_url);
//	    		 $("#upload_cover_div dt input[name='project.cover_image']").val(data.data.file_name);
//	    		 $("#upload_cover_div dd .show_red").hide();
//	    		 $("#upload_cover_div dd .show_green").show();
//	    		 $("#upload_cover_div .cover").css({"display":"block"});
	    	 } else{
	    		 alert(data.message);
	    	 }
	     },
	     error: function (data, status, e) {
	    	 alert(e);
	     }
	});
}


