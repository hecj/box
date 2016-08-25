<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>收货地址</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
   <!-- 头部引用 -->
	<#include "/page/common/n_head.ftl" />
   <!-- 内容start！ -->
	<div class="content bg_wrap content-col-f">
		<!-- 个人中心头部引用 -->
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<div class="pcs2-personal-setting-menu">
					<ul>
						<li><a href="/personal/getPersonalDetail">基本资料</a></li>
						<li><a href="/personal/security">安全中心</a></li>
						<li><a href="/usercertify/certify">实名认证</a></li>
						<li class="last"><a href="/personal/receiveAddress" class="current">收货地址</a></li>
					</ul>
			</div>
		   <div class="personalsetting-address-con">
		   	  <div class="personalsetting-address-title">
		   	  	<ul>
		   	  		<li class="personalsetting-address-name"><span>收货人</span></li>
		   	  		<li class="personalsetting-address-one"><span>所在地</span></li>	
		   	  		<li class="personalsetting-address-two"><span>详细地址</span></li>	
		   	  		<li class="personalsetting-address-phone">联系方式</li>
		   	  	</ul>
		   	  </div>
		   	  <#if receiveAddressList ?? && defaultAddressList ??>
				<#list defaultAddressList as default>
				<div class="personalsetting-address-con-one">
			   	  	<ul>
			   	  		<li class="personalsetting-address-name"><i class="current"></i><span>${default.name!}</span></li>
			   	  		<li class="personalsetting-address-one">
			   	  			<span>${default.provinceName!}${default.cityName!}${default.areaName!}</span>
			   	  		</li>	
			   	  		<li class="personalsetting-address-two">
			   	  			<span>${default.detail_address!}</span>
			   	  		</li>	
			   	  		<li class="personalsetting-address-phone">
			   	  			<i></i>${default.phone!}</li>
			   	  		<li class="operation">
			   	  			<span>默认地址</span>
			   	  			<i onclick="edit(${default.id!-1})"></i>
			   	  			<b onclick="del(${default.id!-1})"></b>
			   	  		</li>
			   	  	</ul>
				</div>
				</#list>
				<#list receiveAddressList as receive>
					<div class="personalsetting-address-con-one">
				   	  	<ul>
				   	  		<li class="personalsetting-address-name"><i></i><span>${receive.name!}</span></li>
				   	  		<li class="personalsetting-address-one">
				   	  			<span>${receive.provinceName!}${receive.cityName!}${receive.areaName!}</span>
				   	  		</li>	
				   	  		<li class="personalsetting-address-two">
				   	  			<span>${receive.detail_address!}</span>
				   	  		</li>	
				   	  		<li class="personalsetting-address-phone">
				   	  			<i></i>${receive.phone!}</li>
				   	  		<li class="operation">
				   	  			<span class="color-blue" onclick="setDefault(${receive.id!-1})">设为默认</span>
				   	  			<i onclick="edit(${receive.id!-1})"></i>
				   	  			<b onclick="del(${receive.id!-1})"></b>
				   	  		</li>
				   	  	</ul>
			   	  	</div>
				</#list>
			</#if>
			
		   	  <div class="personalsetting-add-address">
		   	  	<span>新增地址</span>
		   	  </div>
		   	  <!-- 新增地址 -->
		   	  <div class="personalsetting-box">
					<div class="pub-popup-top">
						   <h6>新增地址</h6>
							<a href="javascript:;" class="popup-close" name="setting-close"></a>
					</div>
					<div class="pub-popup-cont">
						<dl class="paypassword">
							<dt>收件人</dt>
							<dd class="position_rel">
								<input type="hidden" name="id" value="-1"/>
								<input type="text" name="name">
								<span class="placeholder_msg1 placeholder_msg">请输入收件人</span>
							</dd>
							<p class="add-input-right">此项要求2-5个字符</p>
							<p class="add-input-err">请输入收件人姓名</p>
						</dl>
						<dl class="surepaypassword">
						   <dt>手机号码</dt>
						   <dd class="position_rel">
						   	<input type="text" name="phone">
						   	<span class="placeholder_msg2 placeholder_msg">请输入手机号码</span>
						   </dd>
						   <p class="add-input-err">请输入手机号码</p>
						   <p class="add-input-err">手机号码不能为空</p>
						</dl>
						<dl class="bindphone">
							<dt>城市</dt>
							<dd class="personal-add-city">
								<select name="province">
							    	<option value="-1">请选择</option>
									<#list provincesList as provinces>
								    	<option value="${provinces.provinceid!}">${provinces.province!}</option>
							    	</#list>
							    </select>
							    <select name="city">
							    	<option value="-1">请选择</option>
							    </select>
							     <select name="area">
							    	<option value="-1">请选择</option>
							    </select>
							</dd>
							<p class="add-input-err">请选择收货地址!</p>
						</dl>
						<dl class="bindphone">
							<dt>详细地址</dt>
							<dd class="personal-add-city position_rel">
							   <input type="text" name="detail_address" maxlength="300" />
							   <span class="placeholder_msg3 placeholder_msg">请输入详细地址</span>
							</dd>
							<p class="add-input-right">此项要求最少5个字符</p>
							<p class="add-input-err">请输入详细地址</p>
						</dl>																
						<dl class="personal-add-submit">
							<input type="button" name="setting-yes" value="确定" class="submit-green">
							<input type="button" name="setting-no" value="取消"></dl>
					</div>
			  </div>
		   	  <!-- 新增地址.. -->
			  <div class="personalsetting-box-success">
					<div class="pub-popup-top">
						   <h6>提示信息</h6>
							<a href="javascript:;" class="popup-close" name="success-close"></a>
					</div>
					<div class="pub-popup-cont">
						 <p>**********成功</p>
		                 <dl class="submit paypassword-submit">
		                 	<input type="button" name="success-yes" class="submit-green" value="确定">
		                 </dl> 
					</div>
			  </div>
		   </div>
		</div> 
	</div>
   <!-- 内容end！ -->
  
   <!-- 页尾引用 -->
	<#include "/page/common/foot.ftl" />
    <div class="pay-way-wrap"></div>
</body>
	<script type="text/javascript" src="/static/js/page/personal/address.js"></script>
</html>

