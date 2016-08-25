<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <title>协议条款</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <#include "/page/common/base/include.ftl">
    <script type="text/javascript">
        $(document).ready(function () {
            $('#agree').bind('click', function () {
                if ($("#isagree").prop('checked')) {
                    location.href = '/projectapply/createapply';
                } else {
                    alert("您需要同意服务款项");
                }
            });
        });
    </script>
</head>

<body>

<#include "/page/common/head.ftl" />


<br/><br/><br/><br/>


<input type="checkbox" id="isagree" value="yes"> 阅读并同意盒子尖叫的《服务协议》
<input type="submit" id="agree" value="发布我的梦想"/>


<hr/>
<#include "/page/common/foot.ftl" />

</body>
</html>