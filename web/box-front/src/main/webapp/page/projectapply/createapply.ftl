<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>项目初审</title>
	<#include "/page/common/base/include.ftl">
<body>
	<!-- 头部引用 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="project-trial-content bg_wrap">
		<div class="flow-chart"></div>
		<div class="pt-content-in">
			<div class="ptc-form">
					<dl class="title clearfix">
						<dt>项目标题：</dt>
						<dd>
							<input type="text" name="aname" id="aname" />
							<span class="pb-ph unique1">填写项目名称不超过25个字</span>
						</dd>
						<span class="empty empty1">
							<strong>标题不能为空</strong><em></em>							
						</span>
						<span class="word-num" id="name-num">0/25</span>						
					</dl>
					<dl class="description clearfix">
						<dt>项目描述：</dt>
						<dd>
							<textarea id="descript" name="acontext"></textarea>
							<span class="pb-ph unique1">填写项目描述不超过200个字</span>
						</dd>
						<span class="empty empty2">
							<strong>描述不能为空</strong><em></em>							
						</span>
						<span class="word-num" id="desc-num">0/200</span>						
					</dl>
					<dl class="contact clearfix">
						<dt>联系方式：</dt>
						<dd><input type="text" name="aphone" id="aphone" value="${user.phone!}" onkeyup="value=value.replace(/\s/g,'')"/></dd>
						<span class="empty empty3">
							<strong>请填写联系方式</strong><em></em>							
						</span>
						<span class="empty empty4">
							<strong>请输入正确的手机号</strong><em></em>							
						</span>
					</dl>					
					<dl class="enclosure clearfix">
						<dt>项目附件：</dt>
						<dd>
							<input type="text" name="show_afile" readonly="readonly" value="未选择文件"/>
							<input type="hidden" name="afile"/>
						</dd>
						<dd class="enclosure-dd-input">
							<button id="upload_btn"></button>
							<input type="file" id="uploadAttrFile" name="uploadFile"  onchange="upload_attr_ajax()"/> 
						</dd>
					</dl>
					<dl class="submit clearfix">
						<dt>
							<input class="left" type="button" value="">
						</dt>
						<!--
						<dd><input class="right" type="button" value=""></dd>
						-->
					</dl>
			</div>
		</div>		
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
	<!-- 
	<script type="text/javascript" src="/static/js/common/upload/plupload.full.min.js"></script>
	-->
	<script type="text/javascript" src="/static/js/common/jquery/jquery-browser.js"></script>
	<script type="text/javascript" src="/static/js/common/upload/ajaxfileupload.js"></script>
	<script type="text/javascript" src="/static/js/common/event/value.change.js"></script>
	<script type="text/javascript" src="/static/js/page/projectapply/createapply.js?v=20151216"></script>
</body>
</html>