<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <#include "/page/common/base/include.ftl">
    <link href="/static/js/laydate/need/laydate.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/static/js/laydate/laydate.js"></script>
    
<style>
	.tablelist td{
		text-align:center;
	}
</style>
</head>  
<body>
<div class="place" style="width:100%">
    <span>位置：</span>
    <ul class="placeul no-bor">
        <li ><a href="/balanceUser/">充值列表</a></li>
        <li class="cur"><a href="/pay/withdrawals/list">提现列表</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
        	用户名：
        	<input type="text" name="nickname" value="${nickname!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	手机号：
        	<input type="text" name="phone" value="${phone!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	金额：
        	<input type="text" name="amount" value="${amount!}" style="width:80px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	申请提现时间：
		    <input type="text" name="start_time" value="${start_time!}" readonly="readonly" style="width:120px;" class="inline laydate-icon" id="start" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
		    &nbsp;-&nbsp;
		    <input type="text" name="end_time" value="${end_time!}" readonly="readonly" style="width:120px;" class="inline laydate-icon" id="end" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"/>
        	 &nbsp;&nbsp;状态： &nbsp;
        	<select class="dfinput" name="status" style="width:170px;">
        		<option value="-1">--请选择--</option>
        		<#if status ?? && status = 0>
        			<option value="0" selected>待审核</option>
        		<#else>
        			<option value="0">待审核</option>
        		</#if>
        		
        		<#if status ?? && status =1>
        			<option value="1" selected>审核通过</option>
        		<#else>
        			<option value="1">审核通过</option>
        		</#if>
        		
        		<#if status ?? && status =2>
        			<option value="2" selected>未通过</option>
        		<#else>
        			<option value="2">未通过</option>
        		</#if>
        		
        		<#if status ?? && status =3>
        			<option value="3" selected>提现中</option>
        		<#else>
        			<option value="3">提现中</option>
        		</#if>
        		
        		<#if status ?? && status =4>
        			<option value="4" selected>提现成功</option>
        		<#else>
        			<option value="4">提现成功</option>
        		</#if>
        		
        		<#if status ?? && status =5>
        			<option value="5" selected>提现失败</option>
        		<#else>
        			<option value="5">提现失败</option>
        		</#if>
        		
        	</select> &nbsp;&nbsp;
        	<button class="btn" onclick="queryWithdrawalsFun()">查询</button>
        </ul>
    </div>
    <table class="tablelist" style="width:100%">
        <thead>
        <tr>
            <th style="text-align:center;width:5%;" title="">UID</th>
            <th style="text-align:center;width:20%;" title="">用户名</th>
            <th style="text-align:center;width:20%;" title="">手机号</th>
            <th style="text-align:center;width:10%;" title="">提现金额</th>
            <th style="text-align:center;width:20%;" title="">申请提现时间</th>
            <th style="text-align:center;width:5%;" title="">提现状态</th>
            <th style="text-align:center;width:20%;" title="">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if withdrawalsPage ??>
            <#list withdrawalsPage.list as w>
       		<tr>
       			<td>${w.id!}</td>
       			<td>${w.nickname!}</td>
       			<td>${w.phone!}</td>
       			<td>${w.amount!}</td>
       			<td>
       				<#if w.create_at??>
       				${w.create_at?number_to_datetime}
       				</#if>
       			</td>
       			<td>
       				<#if w.status == 0>
       					待审核
       				<#elseif w.status == 1>
       				    审核通过
       				<#elseif w.status == 2>
       				    未通过
       				<#elseif w.status == 3>
       				    提现中
       				<#elseif w.status == 4>
       				    提现成功
       				<#elseif w.status == 5>
       				    提现失败
       				</#if>
       			</td>
       			<td>
       				<a href="/pay/withdrawals/finddetail/${w.id!}" class="tablelink">查看</a>
       			</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=withdrawalsPage.pageNumber totalPage=withdrawalsPage.totalPage totalRow=withdrawalsPage.totalRow actionUrl="/pay/withdrawals/list/" urlParas="?phone=${phone!}&status=${status!}&nickname=${nickname!}&amount=${amount!}&end_time=${end_time!}&start_time=${start_time!}" />
</div>

<script type="text/javascript" src="/static/js/page/pay/withdrawals_list.js"></script>
	
</body>
</html>
