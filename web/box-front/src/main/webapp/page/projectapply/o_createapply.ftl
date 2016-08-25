<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <title>创建初审</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <#include "/page/common/base/include.ftl">
    <script type="text/javascript" src="/static/js/common/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="/static/js/productapply/createapply.js"></script>
</head>
<body>
	<#include "/page/common/head.ftl" />
<h1>
</h1>
<h1>创建初审表单</h1>
<div id="info">
	    项目标题: <input type="text" name="name" id="name"/> <br/>
	    项目描述: <textarea cols="20" rows="5" name="content" id="content"></textarea> <br/>
	    联系方式: <input type="text" name="phone" id="phone"/> <br/>
	    添加附件:
	    <input type="button" id="selectUploadButton" name="selectUploadButton" value="选择附件" />
		<form id="uploadFileForm" action="/upload/uploadFile" method="post" enctype="multipart/form-data">
	   		<input type="file" style="display:none" name="uploadFile"/>
	    </form>
	    <p id="downloadfile"></p>
	    <br/>
	    <input type="hidden" id="uploadFile" name="uploadFile"/>
	    <button type="button" id="btnSub">确认申请</button>
</div>
  	<div id="tongzhi" hidden="true">
		您的申请已提交成功,请等待,客服人员正在处理
		<a href="/personal">返回个人中心</a>
		<a href="/">返回首页</a>
	</div>
<hr/>
<#include "/page/common/foot.ftl" />
</body>
</html>
