<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta charset="UTF-8">
	<title>支付页面--提交订单</title>
	<#include "/page/common/base/include.ftl">
<body>
	<!-- 引用顶部开始 --> 
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="pay-content-wrap bg_wrap">
	    <div class="static-tiltle"><span></span></div>
		<!-- 订单one.. -->
		<div class="pay-content-one">
			<div class="pay-itemone">
				<div class="pay-state"></div>
				<div class="pay-itemone-left">
					<input type="hidden" name="product_id" value="${product.id!}"/>
					<input type="hidden" name="order_num" value="${order_num!}"/>
					<input type="hidden" name="product_type" value="${product.type!}"/>
					<a href="/project/detail/${project.id!}" target="_blank"><img src="${STATIC_URL!}${product.picture!}" alt=""></a>
				</div>
				<div class="pay-itemone-right">

					<div class="return return-ml">
						  <div class="return-title">回报内容</div>
                           <hr class="pay-bar">
						  <div class="return-con">
						  	<p>${product.desc!}</p>
						  </div>	
						   <hr class="pay-bar">
					</div>
					<div class="return pay-money">
						  <div class="return-title">支付金额</div>
                           <hr class="pay-bar">
						  <div class="return-con">
						  	<p>¥${product.fund!}</p>
						  </div>	
						   <hr class="pay-bar">
					</div>
					<div class="return pay-money">
						  <div class="return-title">配送费用</div>
                           <hr class="pay-bar">
						  <div class="return-con">
						  	<p>
							  	<#if (product.postage == 0)>
									免费
								<#else>
									¥${product.postage!}
								</#if>
							</p>
						  </div>	
						   <hr class="pay-bar">
					</div>
					<div class="return pay-endday">
						  <div class="return-title">预计回报时间</div>
                           <hr class="pay-bar">
						  <div class="return-con">
						  	<p>项目结束后${product.send_days!}天</p>
						  </div>	
						   <hr class="pay-bar">
					</div>
				</div>
			</div>
		</div>
		<#if (product.type == 0)>
		<!--提交订单第二次进入-->
		 <div class="border-dashed"></div>
		 <div class="pay-content-one">
				<div class="pay-itemone pay-itemaddress">
					<div class="pay-state pay-state02"></div>
					<div class="pay-address">
				  	<div class="pay-address-row">
				  		<div class="address-title-first">
				  			<h3>收货人信息</h3>
				  			<div class="add-address-first">+新增收货地址</div>
				  		</div>
				  		<!--默认地址-->
				  		<#if defaultAddressList ?? && (defaultAddressList?size>0)>
				  		<#list defaultAddressList as ra>
				  		<div class="pay-address-one pay-address-tow" id="address_${ra.id!}">
				  			  <div class="form-group form-group-name">
								    <label class="pay-radio">
										<input type="radio" class="form-control" checked="checked" name="receiveAddress" value="${ra.id!}" />
								    	${ra.name!}
								    </label>
							  </div>
							   <div class="form-group form-group-address form-group-addressall">
								  <ul>
								  	<li>${ra.provinceName!}&nbsp;&nbsp;${ra.cityName!}&nbsp;&nbsp;${ra.areaName!}&nbsp;&nbsp;${ra.detail_address!}&nbsp;&nbsp; ${ra.phone!} </li>
							
								  </ul>
							  </div>	
					    	  <div class="form-group form-group-address form-group-change">
								  <ul>
								  	<a href="javascript:;">默认地址</a>
								  </ul>
							  </div>  
							  <div class="form-group form-group-address form-group-change02 modify">
							       <ul>
									    <a href="javascript:;">修改</a>
							       </ul>
							  </div>
				  		</div>
				  		</#list>
				  		</#if>
				  		<!--默认地址end-->
				  		
				  		<!--其他收货地址-->
				  		<#if receiveAddressList ?? && (receiveAddressList?size>0)>
				  		<#list receiveAddressList as ra>
				  		<div class="pay-address-one others-address pay-address-tow" id="address_${ra.id!}" style="display: none;">
				  			  <div class="form-group form-group-name">
								    <label class="pay-radio">
									    <input type="radio" class="form-control" name="receiveAddress" value="${ra.id!}" />
								    	${ra.name!}
								    </label>
							  </div>
							   <div class="form-group form-group-address form-group-addressall">
								  <ul>
								  	<li>${ra.provinceName!}&nbsp;&nbsp;${ra.cityName!}&nbsp;&nbsp;${ra.areaName!}&nbsp;&nbsp;${ra.detail_address!}&nbsp;&nbsp;${ra.phone!}</li>
								  </ul>
							  </div>	
							  <div class="form-group form-group-address form-group-change02 modify" style="display:none">
								  <ul>
										<a href="javascript:;">修改</a>
								  </ul>
							  </div>
				  		</div>
				  		</#list>
				  		</#if>
				  		<!--其他地址end-->
				  		
				  		<#if receiveAddressList ?? && (receiveAddressList?size>0)>
				  			<p class="add-others-address">
								<a href="javascript:;" class="add-others-address-btn">
						  			<span class="add-others-address-btn-one" style="display: block;">使用其他地址</span>
						  			<span class="add-others-address-btn-two" style="display: none;">收起地址</span>
						  			<i class="btn-down"></i>
						  		</a>
				  			</p>
				  		</#if>
				  	 
				  	</div>
				 </div>
			</div>
		</div> 
		</#if>
		<!--提交订单第二次进入 -->
		<!-- 应付金额. -->	
	     <div class="pay-money-p">
		 		<div class="pay-itemone-money">
		 			<p>应付金额: 
		 			    <span class="color_money">¥
		 					${(product.fund+product.postage)?string("#.##")}
		 				</span>
		 				<!--<em>.00</em>-->
		 			</p>
		 			<p><input type="submit" class="btn_money" value="提交订单" /></p>
		 		</div>
		 </div>
		 <!-- 应付金额. -->	
	</div>
	
	<!-- 新增收货地址 -->
    <div class="personalsetting-box">
					<div class="pub-popup-top">
						   <h6>新增地址</h6>
							<a href="javascript:;" class="popup-close" name="setting-close"></a>
					</div>
					<div class="pub-popup-cont address-heightauto">
						<dl class="paypassword">
							<dt>收件人</dt>
							<dd class="position_rel">

								<input type="hidden" name="id" value="-1">
								<input type="text" name="name">
								<span class="placeholder_msg1 placeholder_msg">请输入收件人</span>
							</dd>
							<p class="add-input-right">此项要求2-5个字符</p>
							<p class="add-input-err" style="display: none;">请输入收件人姓名</p>
						</dl>
						<dl class="surepaypassword">
						   <dt>手机号码</dt>
						   <dd class="position_rel">
						   	<input type="text" name="phone">
						   	<span class="placeholder_msg2 placeholder_msg">请输入手机号码</span>
						   </dd>
						   <p class="add-input-err" style="display: none;">请输入手机号码</p>
						   <p class="add-input-err" style="display: none;">手机号码不能为空</p>
						</dl>
						<dl class="bindphone">
							<dt>城市</dt>
							<dd class="personal-add-city">
								<select name="province">
							    	<option value="-1">请选择</option>
								    <#if provincesList ??>
   									<#list provincesList as pl>
								    	<option value="${pl.provinceid!}">${pl.province!}</option>
								    </#list>
   									</#if>
							    </select>
							    <select name="city">
							    	<option value="-1">请选择</option>
								</select>
							    <select name="area">
							    	<option value="-1">请选择</option>
							    </select>
							</dd>
							<p class="add-input-err">请选择收货地址</p>
						</dl>	
						<dl class="bindphone">
							<dt>详细地址</dt>
							<dd class="personal-add-city position_rel">
							   <input type="text" name="detail_address">
							   <span class="placeholder_msg3 placeholder_msg">请输入详细地址</span>
							</dd>
							<p class="add-input-right">此项要求最少5个字符</p>
							<p class="add-input-err">请输入详细地址</p>
						</dl>																
						<dl class="personal-add-submit">
							<input type="button" name="setting-yes" value="确定" class="submit-green">
							<input type="button" name="setting-no" value="取消" >
						</dl>
					</div>
	</div>
    <!-- 新增收货地址。。 -->
    
    <!-- 操作成功。。 -->
    <div class="personalsetting-box-success personalsetting-box-success02">
					<div class="pub-popup-top">
						   <h6>提示信息</h6>
							<a href="javascript:;" class="popup-close" name="success-close"></a>
					</div>
					<div class="pub-popup-cont">
						 <p></p>
		                 <dl class="submit paypassword-submit">
		                 	<input type="button" name="success-yes" class="submit-green" value="确定">
		                 </dl> 
					</div>
	</div>
    <!-- 操作成功。。 -->
    <div class="pay-way-wrap"></div>
	

	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
	<script type="text/javascript">
	 var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
    (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
    (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
    //以下进行测试
    if (Sys.ie) document.write('IE: ' + Sys.ie);
    if (Sys.firefox) document.write('Firefox: ' + Sys.firefox);
    if (Sys.chrome) document.write('Chrome: ' + Sys.chrome);
    if (Sys.opera) document.write('Opera: ' + Sys.opera);
    if (Sys.safari) document.write('Safari: ' + Sys.safari);
    if(Sys.ie == '8.0'){
    $(".show_red .reminder-con span").height("28px");
      
 };
  </script>
  
  <script type="text/javascript">
     /*使用其他地址显示方式*/
	  $(".add-others-address-btn").click(function(event) {
	     $(this).children('.add-others-address-btn-one').toggle();    
	      $(this).children('.add-others-address-btn-two').toggle();
	      $(this).children('i').toggleClass('btn-down');
	      
	      $(".others-address").each(function(){
			$(this).toggle();
		  });
	      
	  });
	  $(".pay-way-wrap").height($(document).height());
	 
	  /*关闭按钮、取消按钮点击 */
	  $(".popup-close , input[name=setting-no]").click(function(event) {
	  	  $(".personalsetting-box").hide();
	      $(".pay-way-wrap").hide();
	      $(".personalsetting-box-success").hide();
	  });
	 
</script>
  <script type="text/javascript" src="/static/js/page/orders/generate.js?v=20151128"></script>
  
</body>
</html>