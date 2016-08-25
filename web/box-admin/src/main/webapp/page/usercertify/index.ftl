<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>u列表</title>
    <#include "/page/common/base/include.ftl">
</head>
<style>
	
	.tablelist tr th {
		text-align:center;
	}
</style>
<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/usercertify">实名审核</a></li>
    </ul>
</div>
<div class="rightinfo">

    <div class="tools">
    	<ul class="toolbar">
        	手机号：
        	<input type="text" name="uPhone" value="${uPhone!}" style="width:150px;" class="dfinput"/>
        	<button class="btn" onclick="queryFun()">查询</button>
        </ul>
    </div>

    <table class="tablelist">
        <thead>
        <tr>
            <th title="">id</th>
            <th title="用户名">用户名</th>
            <th title="真实姓名">真实姓名</th>
            <th title="性别">性别</th>
            <th title="身份证号">身份证号</th>
            <th title="身份证">身份证照片</th>
            <th title="手机号">手机号</th>
            <th title="邮箱">邮箱</th> 
            <th title="审核状态">审核状态</th> 
            <th style="color:red">操作</th>
        </tr>
        </thead>
        <tbody>

        <#list page.list as u>
        <tr>
            <td style="text-align:center;">${u.id!}</td>
            <td style="text-align:center;">${u.username!}</td>
            <td style="text-align:center;">${u.realname!}</td>
            <td style="text-align:center;">
            	<#if u.sex ?? && u.sex == 1>
            		男
            	<#elseif u.sex ?? && u.sex == 2>	
            		女
            	</#if>
            </td>
            <td style="text-align:center;">${u.idno!}</td>
            <td style="text-align:center;">
            	<img src="${u.idphoto!}" width="60px" height="60px">
            </td>
            <td style="text-align:center;">${u.phone!}</td>
            <td style="text-align:center;">${u.email!}</td>
            <td style="text-align:center;">
            	<#if u.certify ?? && u.certify == 0>
            		未认证
            	<#elseif u.certify ?? && u.certify == 1>	
            		申请中
            	<#elseif u.certify ?? && u.certify == 2>	
            		认证通过
            	</#if>
            </td>
            <td style="text-align:center;">
            	<#if u.certify ?? && u.certify == 1>
            		<a href="/usercertify/toCertifyApply/${u.id!}" class="tablelink">审核</a>
            	</#if>
				<#-- 
            	<a href="/usercertify/check/${u.id!}-pass" class="tablelink">通过</a>&nbsp;
                <a href="/usercertify/check/${u.id!}-nopass" class="tablelink">不通过</a>
                -->
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
 <#include "/page/common/_paginate_new.ftl" />
<@paginate currentPage=page.pageNumber totalPage=page.totalPage totalRow=page.totalRow actionUrl="/usercertify/" urlParas="?uPhone=${uPhone!}"/>
</div>

<script type="text/javascript">
	$(function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	});	
 	
 	
 	function queryFun(){
 		var url = "/usercertify?";
 		var uPhone = jQuery("input[name=uPhone]").val();
 		if(uPhone.length > 0){
			url = url+"&uPhone="+jQuery.trim(uPhone);
 		}
 		location= url;
 	}
 	
 	
</script>

</body>
</html>