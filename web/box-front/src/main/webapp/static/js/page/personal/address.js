$(function() {
	$(".pay-way-wrap").height($(document).height());
	
	// table菜单切换显示
	$(".personal-menu").find("li").eq(2).addClass("current").siblings().removeClass("current");
	

     /*js模拟placeholder*/
    $("input[name=name]").keyup(function(event) {
    	 $(".placeholder_msg1").hide();
    }).blur(function(event) {
    	var name_val=$("input[name=name]").val();
    	name_val=$.trim(name_val);
    	if (name_val=="") {
         $(".placeholder_msg1").show();
    	}
    });
	 $(".placeholder_msg1").click(function(event) {
	
	 	$("input[name=name]").focus();
	 });
	$("input[name=phone]").keyup(function(event) {
    	 $(".placeholder_msg2").hide();

    }).blur(function(event) {
    	var name_val=$("input[name=phone]").val();
    	name_val=$.trim(name_val);
    	if (name_val=="") {
         $(".placeholder_msg2").show();
    	}
    });
	 $(".placeholder_msg2").click(function(event) {
	 	$("input[name=phone]").focus();
	 });
	 $("input[name=detail_address]").keyup(function(event) {
    	 $(".placeholder_msg3").hide();
    }).blur(function(event) {
    	var name_val=$("input[name=detail_address]").val();
    	name_val=$.trim(name_val);
    	if (name_val=="") {
         $(".placeholder_msg3").show();
    	}
    });
	 $(".placeholder_msg3").click(function(event) {
	 	$("input[name=detail_address]").focus();
	 });
	/*姓名校验*/
	$("input[name=name]").blur(function(event) {
  	 	var name = $("input[name=name]").val();
  	 	name=$.trim(name);
  	 	if(name.length== 0){
	  	 	$("input[name=name]").parent().siblings(".add-input-right").hide();
	  	 	$("input[name=name]").parent().siblings('.add-input-err').show();
			$("input[name=name]").parent().siblings('.add-input-err').html("请输入收件人姓名!");
			$("input[name=name]").css({
				'border': '1px solid #ff0000'
			});
			return false;
  	 	} else if(name.length<2||name.length>5){
			$("input[name=name]").parent().siblings(".add-input-right").hide();
			$("input[name=name]").parent().siblings('.add-input-err').show();
			$("input[name=name]").parent().siblings('.add-input-err').html("此项要求2-5个字符");
			$("input[name=name]").css({
				'border': '1px solid #ff0000'
			});
			return false;
		} else {
			$("input[name=name]").parent().siblings(".add-input-right").hide();
			$("input[name=name]").parent().siblings('.add-input-err').hide();
			$("input[name=name]").css({
				'border': '1px solid #d5d5d5'
			});
		}
  	});	
	/*手机号*/
	$("input[name=phone]").blur(function(event) {
		var phone = $("input[name=phone]").val();
		phone=$.trim(phone);
		if(phone.length== 0){
		 	$("input[name=phone]").parent().siblings('.add-input-err').show();
			$("input[name=phone]").parent().siblings('.add-input-err').html("请输入手机号!");
			$("input[name=phone]").css({
				'border': '1px solid #ff0000'
			});
			return false;
		} else{
		 	$("input[name=phone]").parent().siblings('.add-input-err').hide();	
		 		$("input[name=phone]").css({
				'border': '1px solid #d5d5d5'
			});
		}
		if(!(/^1[3,4,5,7,8]\d{9}$/.test(phone))) {
			 $("input[name=phone]").parent().siblings('.add-input-err').show();
			 $("input[name=phone]").parent().siblings('.add-input-err').html("手机号格式不正确！");
			 $("input[name=phone]").css({
				'border': '1px solid #ff0000'
			});
			 return false;
		} else{
			 $("input[name=phone]").parent().siblings('.add-input-err').hide();
			 $("input[name=phone]").css({
				'border': '1px solid #d5d5d5'
			});
		}
  	}).focus(function(event) {
  		$("input[name=phone]").parent().siblings('.add-input-err').hide();
		
  	});	
  	/*详细地址*/
	$("input[name=detail_address]").focus(function(event) {
		$("input[name=detail_address]").parent().siblings(".add-input-right").show();
		$("input[name=detail_address]").parent().siblings(".add-input-err").hide();    
	}).blur(function(event) {
		var detail_address= $("input[name=detail_address]").val();
		detail_address=$.trim(detail_address);
		if(detail_address.length== 0){
			$("input[name=detail_address").parent().siblings(".add-input-right").hide();;
			$("input[name=detail_address]").parent().siblings('.add-input-err').show();
			$("input[name=detail_address]").parent().siblings('.add-input-err').html("请输入详细地址!");
			$("input[name=detail_address]").css({
				'border': '1px solid #ff0000'
			});
			return false;
		}
		else if(detail_address.length<5){
			$("input[name=detail_address]").parent().siblings(".add-input-right").hide();
			$("input[name=detail_address]").parent().siblings('.add-input-err').show();
		
			$("input[name=detail_address]").parent().siblings('.add-input-err').html("此项要求最少5个字符");
			$("input[name=detail_address]").css({
				'border': '1px solid #ff0000'
			});
			return false;
		}
		else{
			$("input[name=detail_address]").parent().siblings(".add-input-right").hide();
			$("input[name=detail_address]").parent().siblings('.add-input-err').hide();
			$("input[name=detail_address]").css({
				'border': '1px solid #d5d5d5'
			});
		}
	});
	
    //地址修改
	$("select[name=area]").change(function(event) {

		var area = $("select[name=area]").val();

		if(area == -1){
			$("select[name=area]").parent().siblings('.add-input-err').show();
			check=false;
		} else{
			$("select[name=city]").parent().siblings('.add-input-err').hide();
		}   
	});
	$("select").change(function(event) {
	   $(this).css({
	  'border': '1px solid #d5d5d5'
	   });
	});
	/*input获得焦点高亮显示*/
	$("input").each(function(index, el) {
		$(this).focus(function(event) {
			$(this).css({
			'border': '1px solid #a1de00'
			});
			$(this).parent().siblings('.add-input-right').show();
	    }).blur(function(event) {
	    	$(this).parent().siblings('.add-input-right').hide();
	    });
	});
	
	// 新增地址
	$(".personalsetting-add-address span").click(function(event) {
		$(".placeholder_msg").show();
		$(".personalsetting-box").find("h6").text("新增地址");
		$(".personalsetting-box").show();
		$(".pay-way-wrap").show();
		// 填充数据
		$("input[name=id]").val(-1);
		$("input[name=name]").val("");
		$("input[name=phone]").val("");
		$("input[name=detail_address]").val("");
		$("select[name='province']").val(-1);
		$("select[name='city']").html("<option value='-1'>请选择</option>");
	    $("select[name='area']").html("<option value='-1'>请选择</option>");
	});
	
	// 新增地址-关闭/取消
	$("[name='setting-close'],[name='setting-no']").click(function(event) {
		$(".personalsetting-box").hide();
		$(".pay-way-wrap").hide();
		$(".add-input-err").hide();
		$("select").css({
			 'border': '1px solid #d5d5d5'
			});
		$('input').css({
			 'border': '1px solid #d5d5d5'
		});
	});
	
	// 新增地址-保存
	$("[name='setting-yes']").click(function(event) {
		var params = {};
		var check =true;
		// 姓名校验
		var name = $.trim($("input[name=name]").val());
		
		if (name.length == 0) {
			$("input[name=name]").parent().siblings(".add-input-right").hide();
			$("input[name=name]").parent().siblings('.add-input-err').show();
			$("input[name=name]").parent().siblings('.add-input-err').html("请输入收件人姓名!");
			$("input[name=name]").css({
			'border': '1px solid #ff0000'
			});
			check = false;
		} else if (name.length < 2 || name.length > 5) {
			$("input[name=name]").parent().siblings(".add-input-right").hide();
			$("input[name=name]").parent().siblings('.add-input-err').show();
			$("input[name=name]").parent().siblings('.add-input-err').html("此项要求2-5个字符");
			$("input[name=name]").css({
			'border': '1px solid #ff0000'
			});
			check = false;
		} else {
			$("input[name=name]").parent().siblings(".add-input-right").hide();
			$("input[name=name]").parent().siblings('.add-input-err').hide();
			$("input[name=name]").css({
			'border': '1px solid #d5d5d5'
			});
		}
		// 手机号
		var phone = $.trim($("input[name=phone]").val());
		if (phone.length == 0) {
			$("input[name=phone]").parent().siblings('.add-input-err').show();
			$("input[name=phone]").parent().siblings('.add-input-err').html("请输入手机号!");
			$("input[name=phone]").css({
			'border': '1px solid #ff0000'
			});
			check = false;
		}
		else if (!(/^1[3,4,5,7,8]\d{9}$/.test(phone))) {
			$("input[name=phone]").parent().siblings('.add-input-err').show();
			$("input[name=phone]").parent().siblings('.add-input-err').html("手机号格式不正确！");
			$("input[name=phone]").css({
			'border': '1px solid #ff0000'
			});
			check = false;
		} else {
			$("input[name=phone]").parent().siblings('.add-input-err').hide();
			$("input[name=phone]").css({
			'border': '1px solid #d5d5d5'
			});
		}
		//城市校验
		var city = $("select[name=city]").val();
		if (city == -1) {
			$("select[name=province]").css({
			'border': '1px solid #ff0000'
			});
			$("select[name=city]").parent().siblings('.add-input-err').show();
			$("select[name=city]").css({
				'border': '1px solid #ff0000'
			});
			check = false;
		} else {
			$("select[name=city]").parent().siblings('.add-input-err').hide();
			$("select[name=city]").css({
				'border': '1px solid #d5d5d5'
			});
			$("select[name=province]").css({
				'border': '1px solid #d5d5d5'
			});
		}
		var area = $("select[name=area]").val();
		if (area == -1) {
			$("select[name=area]").parent().siblings('.add-input-err').show();
			$("select[name=area]").css({
				'border': '1px solid #ff0000'
			});
			$("select[name=province]").css({
			'border': '1px solid #ff0000'
			});
			check = false;
		} else {
			$("select[name=city]").parent().siblings('.add-input-err').hide();
			$("select[name=area]").css({
				'border': '1px solid #d5d5d5'
			});
			$("select[name=province]").css({
				'border': '1px solid #d5d5d5'
			});
		}
		//详细地址
   		var detail_address = $.trim($("input[name=detail_address]").val());
		if (detail_address.length == 0) {
			$("input[name=detail_address").parent().siblings(".add-input-right").hide();
			$("input[name=detail_address]").parent().siblings('.add-input-err').show();
			$("input[name=detail_address]").parent().siblings('.add-input-err').html("请输入详细地址!");
			$("input[name=detail_address]").css({
				'border': '1px solid #ff0000'
			});
			check = false;
		} else if (detail_address.length < 5) {
			$("input[name=detail_address]").parent().siblings(".add-input-right").hide();
			$("input[name=detail_address]").parent().siblings('.add-input-err').show();
			$("input[name=detail_address]").parent().siblings('.add-input-err').html("此项要求最少5个字符");
			$("input[name=detail_address]").css({
				'border': '1px solid #ff0000'
			});
			check = false;
		} else {
			$("input[name=detail_address]").parent().siblings(".add-input-right").hide();
			$("input[name=detail_address]").parent().siblings('.add-input-err').hide();
			$("input[name=detail_address]").css({
				'border': '1px solid #d5d5d5'
			});
		}
		if (check == false) {
			return;
		}		  
		params.id = $.trim($("input[name=id]").val());
		params.name  = name;
		params.phone = phone;
		params.province = $("select[name=province]").val();
		params.city = city;
		params.area = area;
		params.detail_address = detail_address;
		if (params.id == -1) {
			// 新增地址
			$.ajax({
				type : "post",
				url : "/receiveAddress/add",
				data : params,
				success : function(data) {
					if(data.code == 200){
						$(".personalsetting-box").hide();
						$("#box-alert").find("p").text("新增地址成功");
						$("#box-alert").show();
						$(".pay-way-wrap").show();
					} else {
						alert(data.message);
					}
				}
			});
		} else {
			// 更新地址
			$.ajax({
				type : "post",
				url : "/receiveAddress/update",
				data : params,
				success : function(data) {
					if(data.code == 200){
						$(".personalsetting-box").hide();
						$("#box-alert").find("p").text("修改地址成功");
						$("#box-alert").show();
						$(".pay-way-wrap").show();
					} else {
						alert(data.message);
					}
				}
			});
		}
	});
	// 成功-关闭/确认
	$("#box-alert .feedback-submit,.feedback-close").click(function(){
		$(".pay-way-wrap").hide();
		$("#box-alert").hide();
		window.location.reload();
	});
	
	$("select[name='province']").change(function(){
		var province_id=$(this).val();
		changeProvince(province_id,-1);
	});
	
	$("select[name='city']").change(function(){
		var city_id=$(this).val();
		changeCity(city_id,-1);
	});
	
	// 确认提示框：确定
	$("#box-confirm .feedback-submit").click(function(){
		if (deteleId < 1) {
			return;
		}
		$.ajax({
			type : "post",
			url : "/receiveAddress/del/"+deteleId,
			success : function(data) {
				if(data.code == 200){
					$("#box-alert").find("p").text("删除地址成功");
					$("#box-alert").show();
					$(".pay-way-wrap").show();
				} else {
					alert(data.message);
				}
			}
		});
	});
	// 确认提示框：取消，关闭	
	$("#box-confirm .feedback-cancel,.feedback-close").click(function(){
		$("#box-confirm").hide();
		$(".pay-way-wrap").hide();
	});
});

/**
 * 编辑地址
 * @param id
 */
function edit(id){
	$.ajax({
        type:"get",
        url:"/receiveAddress/query/"+id,
        data:{},
        success:function(data){

        	if(data.code == 200){
        		$(".placeholder_msg").hide();
        		$(".personalsetting-box").show();
        		$(".pay-way-wrap").show();
        		$(".personalsetting-box").find("h6").text("编辑地址");
        		// 填充数据
        		$("input[name=id]").val(data.data.id);
        		$("input[name=name]").val(data.data.name);
        		$("input[name=phone]").val(data.data.phone);
        		$("input[name=detail_address]").val(data.data.detail_address);
        		$("select[name='province']").val(data.data.province);
        		changeProvince(data.data.province,data.data.city,data.data.area);
        	} else {
				alert(data.message);
			}
        }
	});
}

/**
 * 删除地址
 * @param id
 */
var deteleId = -1;
function del(id){
	deteleId = id;
	$("#box-confirm").find("p").text("您确定要删除地址吗?删除之后将不可找回");
	$("#box-confirm").show();
	$(".pay-way-wrap").show();
}

/**
 * 设置默认地址
 * @param id
 */
function setDefault(id){
	$.ajax({
		type : "post",
		url : "/receiveAddress/setDefault/"+id,
		success : function(data) {
			if(data.code == 200){
				$("#box-alert").find("p").text("设置默认地址成功");
				$("#box-alert").show();
				$(".pay-way-wrap").show();
			} else {
				alert(data.message);
			}
		}
	});
}


/**
 * province_id 省份id
 * checked_city_id 默认选中城市
 */
function changeProvince(province_id,checked_city_id,checked_area_id) {

	if (province_id == -1) {
		$("select[name='city']").html("<option value='-1'>请选择</option>");
		$("select[name='area']").html("<option value='-1'>请选择</option>");
		return false;
	}

	$.ajax({
		type : "get",
		url : "/zipcode/getCitys",
		data : "province_id=" + province_id+"&time="+new Date().getTime(),
		async:true,
		success : function(data) {
			if (data.code == 200) {
				var list = data.data;
				var temp = "<option value='-1'>请选择</option>";
				for ( var i = 0; i < list.length; i++) {
					if(checked_city_id == list[i].cityid){
						temp = temp + "<option value='" + list[i].cityid + "' selected='selected'>"+ list[i].city + "</option>";
					} else{
						temp = temp + "<option value='" + list[i].cityid + "' >"+ list[i].city + "</option>";
					}
				}
				$("select[name='city']").html(temp);
				
				if(checked_area_id != undefined && checked_area_id != "" ){
					changeCity(checked_city_id,checked_area_id);
				}else{
					$("select[name='area']").html("<option value='-1'>请选择</option>");
				}
			} else {
				alert(data.message);
			}
		}
	});
}

/**
 * cityid 城市id
 * checked_area_id 默认选择地区
 */
function changeCity(city_id, checked_area_id) {
	$.ajax({
		type : "get",
		url : "/zipcode/getAreas",
		data : "cities_id=" + city_id+"&time="+new Date().getTime(),
		async:true,
		success : function(data) {
			if (data.code == 200) {
				var list = data.data;
				var temp = "<option value='-1'>请选择</option>";
				for ( var i = 0; i < list.length; i++) {
					if(checked_area_id == list[i].areaid){
						temp = temp + "<option value='" + list[i].areaid + "' selected='selected'>"+ list[i].area + "</option>";
					}else{
						temp = temp + "<option value='" + list[i].areaid + "' >"+ list[i].area + "</option>";
					}
				}
				$("select[name='area']").html(temp);
			} else {
				alert(data.message);
			}
		}
	});
}