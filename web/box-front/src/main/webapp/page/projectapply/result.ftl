
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <title>商品详情</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <#include "/page/common/base/include.ftl">
    <style type="text/css">
        table{ font-family: verdana,arial,sans-serif; font-size:11px; color:#333333; border-width: 1px; border-color: #666666; border-collapse: collapse; }
        table th { border-width: 1px; padding: 8px; border-style: solid; border-color: #666666; background-color: #dedede; }
        table td { border-width: 1px; padding: 8px; border-style: solid; border-color: #666666; background-color: #ffffff; }
    </style>
</head>
<body>

<#include "/page/common/head.ftl" />

<br /><br /><br /><br />

   <h1>${result?string('成功', '失败')}</h1>
   <h1>${msg!''}</h1>

<hr />
<#include "/page/common/foot.ftl" />

</body>
</html>


