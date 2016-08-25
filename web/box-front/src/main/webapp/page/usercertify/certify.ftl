<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>个人中心</title>
    <#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<div class="content bg_wrap content-col-f">
		<#include "/page/common/personal_head.ftl" />
	<div class="cf-personal-center-content">
	<div class="pcenter-subfield">	
	<div class="pcenter-subfield-2 pcs2-personal-setting">
		<div class="pcs2-personal-setting-menu">
			<ul>
				<li><a href="/personal/getPersonalDetail">基本资料</a></li>
				<li><a href="/personal/security">安全中心</a></li>
				<li><a class="current" href="/usercertify/certify">实名认证</a></li>
				<li class="last"><a href="/personal/receiveAddress">收货地址</a></li>
			</ul>
		</div>
		
		<div class="pcs2-personal-setting-cont">
			<!-- 实名认证 -->
			<div class="real-name-authentication pcs2-psm2" style="display:block">
				<#if certify_now==0>
				<div class="real-name-authentication-in" >
					<p class="basic-data-prompt"><em>提示：</em>发布项目需要<em>身份认证</em></p>
					<div class="basic-data-form real-name-authentication-form">
						<form method="post" id="certify">
							<dl class="clearfix">
								<dt>真实姓名</dt>
								<dd><input class="name-input" name="realname" type="text"/></dd>
								<dd class="gender">
									<span class="gender-tittle">性别</span>
									<label class="radioc">
										<input class="gender-input" type="radio" name="sex" value="1" />
										<span class="radio-bg"></span>
										<span>男</span>
									</label>
									<label class="radioc gender-female">
	  									<input class="gender-input" type="radio" name="sex" value="2" />
	  									<span class="radio-bg"></span>
	  									<span>女</span>
	  								</label>
								</dd>
								<span class="pb-empty" style="display:none">请输入您的姓名!</span>
								<span class="ps-empty" style="display:none">请选择您的性别!</span>
							</dl>
							<dl>
								<dt>身份证号</dt>
								<dd><input class="ID-number" type="text" name="ID-number" id="ID-number" onkeyup="value=value.replace(/\s/g,'')"/></dd>
								<span class="pb-error" style="display:none">身份证号码不正确!</span>
								<span class="pb-empty" style="display:none">请输入您的身份证号!</span>
							</dl>
							<dl class="ID-photo">
								<dt>
									<input type="hidden" id="idphoto" name="idphoto" value=""/>
									<span class="ID-photo-tittle">上传手持身份证照片</span>
									<div class="position_rel fl-left">
										<span class="ID-photo-btn">选择上传照片</span>
									
										<input type="file" id="uploadFile" class="chose-pic" name="uploadFile" onchange="upload_convert_ajax()"/>
									</div>
									 
								</dt>
								<dd>
									<span class="ID-photo-des"><em></em>请手持身份证包含肩部以上人员头部信息</span>
									<div class="ID-photo-img">
										<img src="/static/images/cf-personalcenter-personalsetting/img1.png" alt="" id="prePicture" />
									</div>
								</dd>
							</dl>								
							<dl>
								<dt>手机号码</dt>
									<dd><span class="phone-number" id="go-bindphone" onkeyup="value=value.replace(/\s/g,'')"></span>
							</dl>
							<dl class="submit"><input type="button" class="submit-btn" id="submit_btn" name="realname-submit" value="提交审核" /></dl>
						</form>
					</div>
				</div>	
				<!--   审核中  -->
				<#elseif certify_now==1 >
				<div class="real-name-authentication-in">
					<span class="real-name-auditimg"></span>
					<p class="real-name-audit">信息正在审核中</p>
					<p class="real-name-audit-information">
						<span class="name floatL">认证姓名：${userreal!}</span>
						<span class="number floatR">认证号码：${useridno!}</span>
					</p>
				</div>	
			
			
				<!--  成功 -->
				<#elseif certify_now==2>
				<div class="real-name-authentication-in">
					<span class="real-name-successimg"></span>
					<p class="real-name-audit real-name-audit-color">您已完成实名认证！</p>
					<p class="real-name-audit-information real-name-audit-color1">
						<span class="name floatL">认证姓名：${userreal!}</span>
						<span class="number floatR">认证号码：${useridno!}</span>
					</p>							
				</div>
				<!-- 失败-->
				<#else>
				<div class="real-name-authentication-in">
					<p class="real-name-failure"><em></em>实名认证审核退回</p>
					<p class="real-name-failure-information">提示：请按照要求重新提交真实信息(你的真实信息盒子尖叫不会提供给第三方)</p>
					<div class="basic-data-form real-name-authentication-form">
						<form method="post" id="encertify">
							<dl class="clearfix">
								<dt>真实姓名</dt>
								<dd><input class="name-input" name="realname" type="text" value=${user.realname!}></dd>
								<dd class="gender">
									<span class="gender-tittle">性别</span>
									<label class="radioc">
										<input class="gender-input" type="radio" name="sex" value="1" <#if user.sex==1> checked="checked"</#if>/>
										<span class="radio-bg"></span>
										<span>男</span>
									</label>
									<label class="radioc gender-female">
	  									<input class="gender-input" type="radio" name="sex" value="2" <#if user.sex==2> checked="checked"</#if>/>
	  									<span class="radio-bg"></span>
	  									<span>女</span>
	  								</label>
  								</dd>
								<span class="pb-empty" style="display:none">请输入您的姓名!</span>
								<span class="ps-empty" style="display:none">请选择您的性别!</span>
							</dl>
							<dl>
								<dt>身份证号</dt>
								<dd><input class="ID-number" type="text" name="ID-number" id="ID-number" value=${user.idno!}></dd>
								<span class="pb-error" style="display:none">身份证号码不正确!</span>
								<span class="pb-empty" style="display:none">请输入您的身份证号!</span>
							</dl>
							<dl class="ID-photo">
								<dt>
									<input type="hidden" id="idphoto" name="idphoto" value=""/>
									<span class="ID-photo-tittle">上传手持身份证照片</span>
									<input class="ID-photo-btn" type="button" value="选择上传照片" id="selectPicture" onclick="upload_convert()"/>
								</dt>
								<dd>
									<span class="ID-photo-des"><em></em>请手持身份证包含肩部以上人员头部信息</span>
									<div class="ID-photo-img">
										<img src="<#if user.idphoto!="">${user.idphoto}<#else>/static/images/cf-personalcenter-personalsetting/img1.png</#if>" alt="" id="prePicture" />
									</div>
									<!-- 隐藏file标签 -->  
									<input type="file" style="display:none;" id="uploadFile" name="uploadFile" onchange="upload_convert_ajax()"/> 
								</dd>
							</dl>									
							<dl>
								<dt>手机号码</dt>
								<dd><span class="phone-number" id="go-bindphone" onkeyup="value=value.replace(/\s/g,'')"></span>
							</dl>
																		
							<dl class="submit"><input type="button" class="submit-btn" id="submit_btn120" name="realname-submit" value="提交审核" /></dl>
						</form>
					</div>							
				</div>	
				</#if>
				<div class="pub-mask-layer"></div>
					<div class="pub-popup">
						<!-- 绑定手机 -->
						<div class="pub-popup-in sc-bind-phone">
							<div class="pub-popup-top">
								<h6>绑定手机号</h6>
								<a href="javascript:;" class="popup-close"></a>
							</div>
							<div class="pub-popup-cont">
								<dl class="tellphone">
									<dt>手机号</dt>
									<dd><input type="text" name="tellphone" placeholder="请输入注册手机号" /></dd>
									<span class="pb-sign pb-empty"></span>
								</dl>
								<dl class="dynamic-code">
									<dt>动态码</dt>
									<dd><input type="text" name="dynamic-code"  maxlength="6" onkeyup="value=value.replace(/[^\w]|_/ig,'')" /></dd>
									<dd><span class="settimeout" onclick="sc_tellphone(this)">获取短信验证码</span></dd>
									<span class="pb-sign pb-empty"></span> 
								</dl>
								<dl class="submit phone-submit"><input type="button" name="tellphone-subbtn" value="绑定" /></dl>
							</div>
						</div>
					</div>			
			</div> 
			<!-- 提示信息 -->
			
		</div>
	</div>
	</div>
	</div>
	</div>
	
	<div id="certity_now">${certity_now!}</div>
	<div id="static_url">${STATIC_URL!}</div>
	<!-- 引用底部开始 --> 
	<#include "/page/common/foot.ftl" />
	<!-- 引用底部结束 -->
	<script type="text/javascript" src="/static/js/common/util/FileUtil.js"></script>
	<script type="text/javascript" src="/static/js/login_common.js"></script>
	<script type="text/javascript" src="/static/js/page/user/certify.js?v=20151217"></script>
	<script type="text/javascript" src="/static/js/common/util/CommonUtil.js"></script>	
	<script type="text/javascript" src="/static/js/common/jquery/jquery-browser.js?v=20151209"></script>
	<script type="text/javascript" src="/static/js/common/upload/ajaxfileupload.js?v=20151204"></script>
	<script type="text/javascript">
		$('input:radio:checked').parent().addClass('current');
		<#if user.phone??&&user.phone!="">
			var phone = ${user.phone!}+"";
			phone = phone.substring(0,3)+"****"+phone.substring(7);
			$("#go-bindphone").text(phone);
		<#else>
			$("#go-bindphone").text("绑定手机号");
		</#if>
		
		<#if user.idphoto?? && user.idphoto != "">
			$("#idphoto").val("${user.idphoto}");
			$(".ID-photo-des").removeClass("ID-photo-des-icon");
		    $(".ID-photo-des").addClass("ID-photo-des-success");
		</#if>
	</script>	
</body>
</html>