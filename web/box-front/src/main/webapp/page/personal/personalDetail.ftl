<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>个人设置</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl" />
	<!-- 引用顶部结束 -->
	<div class="bg_wrap">
		<#include "/page/common/personal_head.ftl" />
		<div class="cf-personal-center-content">
			<div class="pcenter-subfield">
				<!-- 个人设置 -->
				<div class="pcenter-subfield-2 pcs2-personal-setting" style="display:block">
					<div class="pcs2-personal-setting-menu">
						<ul>
							<li><a class="current" href="/personal/getPersonalDetail">基本资料</a></li>
							<li><a href="/personal/security">安全中心</a></li>
							<li><a href="/usercertify/certify">实名认证</a></li>
							<li class="last"><a href="/personal/receiveAddress">收货地址</a></li>
						</ul>
					</div>
					<div class="pcs2-personal-setting-cont">
						<!-- 基本信息 -->
						<div class="basic-data pcs2-psm0" style="display:block">
							<p class="basic-data-prompt">您好，<em>${user.nickname!}</em>请如实填写以下内容，让酷友们可以找到和关注你。</p>
							<div class="basic-data-form">
								<form method="post" id="personalDetail">
									<dl class="head-portrait">
										<dt>
											<span></span>
											<input type="hidden" id="picture" name="picture" value="${user.picture!}"/>
											<img id="prePicture" src="<#if user.picture?? && user.picture!="">${user.picture!}<#else>${WEBROOT_URL}static/images/top-footer/userphoto.png</#if>" />
											<!-- 隐藏file标签 -->  
										 
										</dt>
										<dd class="position_rel ml-20">
											<span class="modify-avatar" >修改头像</span>
											<!-- <input type="button" id="selectPicture" class="modify-pic" value="" onclick="upload_convert()"/> -->	
											<input type="file" id="uploadFile" class="modify-pic" name="uploadFile" onchange="upload_convert_ajax()"/>
											
										</dd>
									</dl>						
									<dl class="clearfix">
										<dt>用户名</dt>
										<dd><input class="name-input" type="text" value="<#if user.nickname!=''>${user.nickname!}<#else>请输入有效昵称</#if>" name="nickname" id="nickname"></dd>
										<span class="pb-show" style="display:none">昵称只允许输入4-12位字符</span>
										<span class="pb-error" style="display:none">用户名已存在</span>
										<span class="pb-empty" style="display:none">请输入有效昵称</span>			
									</dl>
									<dl>
										<dt>电话</dt>
										<dd><span class="phone">${user.phone!}</span></dd>
									</dl>
									<dl>
										<dt>性别</dt>
										<dd class="gender">
											<label class="radioc">
												<input class="gender-input" type="radio" name="sex" value="1" <#if user.sex==1>checked="checked"</#if>/>
												<span class="radio-bg"></span>
												<span>男</span>
											</label>
											<label class="radioc">
			  									<input class="gender-input" type="radio" name="sex" value="2" <#if user.sex==2>checked="checked"</#if>/>
			  									<span class="radio-bg"></span>
			  									<span>女</span>
				  							</label>
			  								<label class="radioc">
			  									<input class="gender-input" type="radio" name="sex" value="3" <#if user.sex==3>checked="checked"</#if>/>
			  									<span class="radio-bg"></span>
			  									<span>保密</span>
			  								</label>																		
		  								</dd>
									</dl>
									<dl>
										<dt>生日</dt>
										<dd>
											<select id="birthYear" name="birthYear">
												<script>
													 for(i=1900;i<2050;i++) {
													 	document.write("<option value="+i+">"+i+"年</option>");
													 }
													 $("#birthYear").find("option[value="+${birthYear}+"]").attr("selected",true);
												</script>
											</select>
											<select id="birthMonth" name="birthMonth" onclick="findDay($('#birthYear').val(),$('#birthMonth').val(),'')">
												<script>
													 for(i=1;i<13;i++){
													 	document.write("<option value="+i+">"+i+"月</option>");
													 }
													 $("#birthMonth").find("option[value="+${birthMonth}+"]").attr("selected",true);
												 </script>
											</select>
											<select id="birthDay" name="birthDay" onclick="findDay($('#birthYear').val(),$('#birthMonth').val(),$('#birthDay').val())">
												<script>
													for(i=1;i<31;i++){
														document.write("<option value="+i+">"+i+"日</option>");
													}
													 $("#birthDay").find("option[value="+${birthDay}+"]").attr("selected",true);
												</script>
											</select>
										</dd>
									</dl>
									<dl>
										<dt>城市</dt>
										<dd>
											<select id="province" name="province">
												<#if provincesList??>
				            						<option value="-1" selected>请选择</option>
					            					<#list provincesList as p>
					            						<#if user.province ?? && (p.provinceid == user.province)>
					            							<option value="${p.provinceid!}" selected>${p.province!}</option>
					            						<#else>
					            							<option value="${p.provinceid!}">${p.province!}</option>
					            						</#if>
					            					</#list>
					            				</#if>
											</select>
											<select id="city" name="city">										
												<#if citiesList??>
				            						<option value="-1" selected>请选择</option>
					            					<#list citiesList as c>
					            						<#if c.areaid??>
						            						<#if user.city ?? && (c.areaid == user.city)>
						            							<option value="${c.areaid!}" selected>${c.area!}</option>
						            						<#else>
						            							<option value="${c.areaid!}">${c.area!}</option>
						            						</#if>
					            						<#else>
						            						<#if user.city ?? && (c.cityid == user.city)>
						            							<option value="${c.cityid!}" selected>${c.city!}</option>
						            						<#else>
						            							<option value="${c.cityid!}">${c.city!}</option>
						            						</#if>
					            						</#if>
					            					</#list>
												</#if>
											</select>
										</dd>
									</dl>
									<dl>
										<dt>个人说明</dt>
										<dd class="for-ph">
											<input class="personal-note" maxlength="20" type="text" id="note" name="note" value="">
											<span class="pb-ph unique1">写几句个人介绍，让大家了解你吧！</span>
										</dd>
									</dl>							
									<dl class="submit"><input type="button" class="submit-btn" /></dl>
								</form>
							</div>
						</div>
						<!-- 安全中心 -->
						<div class="security-center pcs2-psm1" style="display:none"></div>
						<!-- 实名认证 -->
						<div class="real-name-authentication pcs2-psm2" style="display:none"></div>
						<!-- 收货地址 -->
						<div class="harvest-address pcs2-psm3" style="display:none"></div>
					</div>
				</div>
				
			</div>
		</div>
	  </div>
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
	<script type="text/javascript" src="/static/js/common/jquery/jquery-browser.js"></script>
	<script type="text/javascript" src="/static/js/common/upload/jupload-0.1.min.js"></script>
	<script type="text/javascript" src="/static/js/common/util/FileUtil.js"></script>
	<script type="text/javascript" src="/static/js/page/personal/personalDetail.js?v=20151216"></script>
	<script type="text/javascript" src="/static/js/common/upload/ajaxfileupload.js?v=20151204"></script>
	<script type="text/javascript">
		<#if user.note ?? && user.note != "">
			$("#note").val("${user.note}");
			$(".unique1").hide();
		<#else>
			$(".unique1").show();
		</#if>
	</script>
</html>