
function replaceParamVal(oldUrl, paramName, replaceWith) {
    var re = eval('/(' + paramName + '=)([^&]*)/gi');
    var nUrl = oldUrl.replace(re, paramName + '=' + replaceWith);
    return nUrl;
}
function hasParameter(name){
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
        return null;
}
/**
 * 选中的地址
 */
var select_address_id = -1;

$(function(){
	
    // 提示框：确认、关闭
	$("#box-alert .feedback-submit,.feedback-close").click(function(){
		$("#box-alert").hide();
		$(".pay-way-wrap").hide();
	});
	
	// 默认选中地址
	var default_address_id = common.getUrlParam("default_address_id");
	if(default_address_id != null && default_address_id.length >0 ){
		
		$(".add-others-address-btn").click();
		
		$(".pay-address-tow").each(function(){
			var idStr = $(this).attr("id");
			var id = idStr.split("_")[1];
			if(id == default_address_id){
				$(this).find("input[name=receiveAddress]").attr("checked","checked");
				$('.modify').hide(); 
				$(this).find('.modify').show();
				return;
			}
		});
	}
	
	$("select[name='province']").change(function(){
		var province_id=$(this).val();
		changeProvince(province_id,-1);

	});
	
	$("select[name='city']").change(function(){
		var city_id=$(this).val();
		changeCity(city_id,-1);
	});

	/*
	// 显示其他收货地址 隐藏添加收货地址
	$(".add-others-address").click(function(){
		$(".others-address").each(function(){
			$(this).show();
		});
		$(this).hide();
		$(".reset-address").hide();
		resetAddress();
	});
	*/
	
	$(".pay-address-tow input[type='radio']").click(function(event) {
		$('.modify').hide(); 
		$(this).parent().parent().siblings('.modify').show();
	
	});
	$("input[type='radio']").each(function(index, el) {
	 	$(this).css({
	 		paddingLeft: '0',
	 		border:'none'
	 	});
	});
	/*
	// 点击收货地址隐藏添加收货地址
	$("input[name=receiveAddress]").click(function(){
		$(".reset-address").hide();
	});
	*/
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
	
	 /*新增地址点击*/
	 $(".add-address-first").click(function(event) {
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
			'border' : '1px solid #d5d5d5'
		});
		$('input').css({
			'border' : '1px solid #d5d5d5'
		});
	});
	 
	// 添加收货地址提交
	$("input[name=setting-yes]").click(function(event) {
		var params = {};
		var check =true;
		params.id = $("input[name=id]").val();
		params.name  = $("input[name=name]").val();
		params.phone = $("input[name=phone]").val();
		params.province = $("select[name=province]").val();
		params.city = $("select[name=city]").val();
		params.area = $("select[name=area]").val();
		params.detail_address = $("input[name=detail_address]").val();
		// 姓名校验
		var name = $("input[name=name]").val();
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
		var phone = $("input[name=phone]").val();
		phone=$.trim(phone);
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
   		var detail_address = $("input[name=detail_address]").val();
   		detail_address=$.trim(detail_address);
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
				
		if (params.id == -1) {
			// 新增地址
			$.ajax({
				type : "post",
				url : "/receiveAddress/add",
				data : params,
				success : function(data) {
					if(data.code == 200){
						$(".placeholder_msg").show();
						$(".personalsetting-box").hide();
						$("#box-alert").find("p").text("新增地址成功");
						$("#box-alert").show();
						$(".pay-way-wrap").show();
						// 新增地址的Id
						select_address_id = data.data.id;
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
						$(".placeholder_msg").hide();
						$(".personalsetting-box").hide();
						$("#box-alert").find("p").text("修改地址成功");
						$("#box-alert").show();
						$(".pay-way-wrap").show();
						// 编辑地址的Id
						select_address_id = data.data.id;
					} else {
						alert(data.message);
					}
				}
			});
		}
	});
	
	// 成功-关闭/确认
	$("#box-alert .feedback-close,#box-alert .feedback-submit").click(function(event) {
		var order_num = common.getUrlParam("order_num");
		var default_address_id = common.getUrlParam("default_address_id");
		if(order_num == null){
			if(default_address_id != null && default_address_id.length > 0){
				location.href = replaceParamVal(location.href,"default_address_id",select_address_id);
			} else{
				location.href = location.href+"?default_address_id="+select_address_id;
			}
		} else{
			if(default_address_id != null && default_address_id.length > 0){
				location.href = replaceParamVal(location.href,"default_address_id",select_address_id);
			} else{
				location.href = location.href+"&default_address_id="+select_address_id;
			}
		}
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
	        		$(".personalsetting-box").show();
	        		$(".pay-way-wrap").show();
	        		$(".placeholder_msg").hide();
	        		$(".personalsetting-box").find("h6").text("修改地址");
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
	
	// 修改收货地址
	$(".form-group-change02").click(function(){
		var idStr = $(this).parent().attr("id");
		var id = idStr.split("_")[1];
		$.ajax({
	        type:"get",
	        url:"/receiveAddress/query/"+id,
	        data:{},
	        success:function(data){
	        	if(data.code == 200){
	        		$(".personalsetting-box").show();
	        		$(".pay-way-wrap").show();
	        		$(".placeholder_msg").hide();
	        		$(".personalsetting-box").find("h6").text("修改地址");
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
	});

	$("select").each(function(index, el) {
		$(this).focus(function(event) {
			$(this).css({
			
				'color': '#000'
			});
		}).change(function(event) {
			$(this).css({
				// "background": '#fff',
				'color': '#000'
			});
			if($(this).val()==""){
				$(this).siblings('.show_red').show();
				return false;
			}
			else{
				$(this).siblings('.show_red').hide();

			}
			var province = this.value;
		if(province == -1){
			$(this).siblings(".show_red").show();
			$(this).css({"color":""});
		}else{
			$(this).siblings(".show_red").hide();
			$(this).css({"color":"#000"});
			
			
		}
		}).change(function(event) {
			if($(this).val()=="请选择"){
				$(this).siblings('.show_red').show();
				$(this).css({"color":""});
				return false;
			}
			else{
				$(this).siblings('.show_red').hide();
				$(this).css({"color":"#000"});
			}

		});
	});
    /*联系方式的校验*/
	$("input[name=phone]").blur(function(event) {
		 var name = $("input[name=phone]").val();
		 if(name==""){
			
					$("input[name=phone]").siblings('.show_red').children().children('span').html("请输入手机号!");

					$("input[name=phone]").siblings('.show_red').show();
					return false;
		  }
		if (!(/^1[3,4,5,7,8]\d{9}$/.test($("input[name=phone]").val()))) {
			   $("input[name=phone]").siblings('.show_red').show();
			    $("input[name=phone]").siblings('.show_red').children().children('span').html("手机号格式不正确！");
				return false;
	    }
	    else{
	    	   $("input[name=phone]").siblings('.show_red').hide();
	    }
	   
	});

    /*收件人的校验*/
    $("input[name=name]").focus(function(event) {
   
		    	$("input[name=name]").siblings(".show_green_01").show();
		    	$("input[name=name]").siblings(".show_red").hide();
		    
          }).blur(function(event) {
	            var name = $("input[name=name]").val();
				if(name.length== 0){
					$("input[name=name]").siblings(".show_green_01").hide();
					$("input[name=name]").siblings('.show_red').children().children('span').html("收件人不能为空!");

					$("input[name=name]").siblings('.show_red').show();
					return false;
				}
				else if(name.length<2||name.length>5){
					$("input[name=name]").siblings(".show_green_01").hide();
					$("input[name=name]").siblings('.show_red').show();

					$("input[name=name]").siblings('.show_red').children().children('span').html("此项要求2-5个字符");
					return false;
				}
				else{
					$("input[name=name]").siblings(".show_green_01").hide();
					$("input[name=name]").siblings('.show_red').hide();

				}
    });
    /*详细地址的校验*/
    $("input[name=detail_address]").focus(function(event) {
		    	$("input[name=detail_address]").siblings(".show_green_01").show();
		    	$("input[name=detail_address]").siblings(".show_red").hide();    
           }).blur(function(event) {
       	   		var name = $("input[name=detail_address]").val();
				if(name.length== 0){
					$("input[name=detail_address").siblings(".show_green_01").hide();;
					$("input[name=detail_address]").siblings('.show_red').show();
					$("input[name=detail_address]").siblings('.show_red').children().children('span').html("请输入详细地址!");

					return false;
				}
				else if(name.length<5){
					$("input[name=detail_address]").siblings(".show_green_01").hide();
					$("input[name=detail_address]").siblings('.show_red').show();

					$("input[name=detail_address]").siblings('.show_red').children().children('span').html("此项要求最少5个字符");
					return false;
				}
				else{
					$("input[name=detail_address]").siblings(".show_green_01").hide();
					$("input[name=detail_address]").siblings('.show_red').hide();
				}
    });
	// 提交订单
	$(".btn_money").click(function(){
		
		// 产品id
		var product_id = $("input[name=product_id]").val();
		
		// 产品类型（0：实物，1：虚物）
		var product_type = $("input[name='product_type']").val();
		if(product_type == 1){
			orderSumit(product_id,-1);
		} else{
			// 选择地址
			var receiveAddressList = $("input[name=receiveAddress]:checked");
			var address_id = receiveAddressList.val();
			orderSumit(product_id,address_id);
		}
		return true;
	});
	
	});
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
	
	/**
	 * 重置收货地址
	 
	function resetAddress(){
		// 重置地址
		$("input[name=curr_address_id]").val("");
		$("input[name=name]").val("");
		$("input[name=phone]").val($("input[name=default_phone]").val());
		$("select[name='province']").val(-1);
		$("select[name='city']").html("<option value='-1'>请选择</option>");
	    $("select[name='area']").html("<option value='-1'>请选择</option>");
	    $("input[name=detail_address]").val("");
	    $("select").each(function(index, el) {
	    	$(this).css({
	    		color: '#b0b0b0'
	    	});
	    });
	}
	*/
	
	/**
	 * 订单提交
	 */
	function orderSumit(product_id,address_id){
		var action_url = "";
		var order_num = $("input[name=order_num]").val();
		if(order_num.length == 0){
			// 新增订单
			action_url = "/orders/checkout/"+product_id;
		} else{
			// 修改订单
			action_url = "/orders/checkout_up/"+product_id;
		}
		
		
		$("body").append("<form id='tempForm' style='display:none' method='post' action='"+action_url+"'>"+
				"<input type='text' name='receive_address_id' value='"+address_id+"'></input>"+
				"<input type='text' name='order_num' value='"+order_num+"'></input>"+
				"</form>");
		$("#tempForm").submit();
	}
