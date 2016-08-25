<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <#include "/page/common/base/include.ftl">
<style>
</style>
</head>  
<body>
<div class="place" style="width:135%">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/pay/ordersRefund/query?status=1">订单退款管理</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
        	订单号：
        	<input type="text" name="order_num" value="${order_num!}" style="width:200px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	支付宝交易号：
        	<input type="text" name="trade_no" value="${trade_no!}" style="width:200px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	项目名称：
        	<input type="text" name="project_name" value="${project_name!}" style="width:200px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	状态：
        	<select class="dfinput" name="status" style="width:170px;">
        		<option value="-1">--请选择--</option>
        		<#if status ?? && status = 1>
        			<option value="1" selected>等待退款</option>
        		<#else>
        			<option value="1">等待退款</option>
        		</#if>
        		
        		<#if status ?? && status =2>
        			<option value="2" selected>已退款</option>
        		<#else>
        			<option value="2">已退款</option>
        		</#if>
        		
        	</select>
        	<button class="btn" onclick="queryFun()">查询</button>
        </ul>
    </div>
    <div class="tools">
        <button class="btn" onclick="doRefundOrderFun()">批量退款</button>
    </div>
    <table class="tablelist" style="width:135%">
        <thead>
        <tr>
            <th style="text-align:center;width:2%;" title=""><input type="checkbox" name="refund_group"/></th>
            <th style="text-align:center;width:2%;" title="">Id</th>
            <th style="text-align:center;width:3%;" title="">支持者</th>
            <th style="text-align:center;width:4%;" title="">项目Id</th>
            <th style="text-align:center;width:10%;" title="">项目名称</th>
            <th style="text-align:center;width:10%;" title="">订单号</th>
            <th style="text-align:center;width:10%;" title="">支付宝交易号</th>
            <th style="text-align:center;width:6%;" title="">付款账户</th>
            <th style="text-align:center;width:4%;" title="">支付方式</th>
            <th style="text-align:center;width:4%;" title="">金额</th>
            <th style="text-align:center;width:2%;" title="">数量</th>
            <th style="text-align:center;width:4%;" title="">状态</th>
            <th style="text-align:center;width:7%;" title="">类型</th>
            <th style="text-align:center;width:10%;" title="">描述</th>
            <th style="text-align:center;width:4%;" title="">操作人</th>
            <th style="text-align:center;width:10%;" title="">退款时间</th>
            <th style="text-align:center;width:10%;" title="">付款时间</th>
        </tr>
        </thead>
        <tbody>
        <#if ordersRefundPage ??>
            <#list ordersRefundPage.list as ar>
       		<tr>
       			<td style="text-align:center;">
       				<#if (ar.status=1)>
       					<input type="checkbox" name="order_radio" value="${ar.id!}"/>
       				<#else>
       					<input type="checkbox" name="order_radio" value="${ar.id!}" disabled="disabled"/>
       				</#if>
       			</td>
       			<td>${ar.id!}</td>
       			<td>${ar.nickname!}</td>
       			<td>${ar.project_id!}</td>
       			<td>${ar.project_name!}</td>
       			<td>${ar.order_num!}</td>
       			<td>${ar.trade_no!}</td>
       			<td>${ar.buyer_email!}</td>
       			<td>
       				<#if ar.pay_type = 1>
       					余额支付
       				<#elseif ar.pay_type = 2>
       				    alipay
       				</#if>
       			
       			</td>
       			<td>${(ar.money+ar.postage)}</td>
       			<td>${ar.order_number!}</td>
       			<td>
       				<#if ar.status = 1>
       					等待退款
       				<#elseif ar.status = 2>
       				       已退款
       				<#elseif ar.status = e>
       				       退款失败
       				<#else>
       				
       				</#if>
       			</td>
       			<td>
       				<#if ar.type = 1>
       					付款异常退款
       				<#elseif ar.type = 2>
       				       系统后台退款
       				<#else>
       				
       				</#if>
       			</td>
       			<td>${ar.desc!}</td>
       			<td>${ar.operator!}</td>
       			<td>
       				<#if ar.refund_at??>
       				${ar.refund_at?number_to_datetime}
       				</#if>
       			</td>
       			<td>
       				<#if ar.pay_at??>
       				${ar.pay_at?number_to_datetime}
       				</#if>
       			</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=ordersRefundPage.pageNumber totalPage=ordersRefundPage.totalPage totalRow=ordersRefundPage.totalRow actionUrl="/pay/ordersRefund/query/" urlParas="?order_num=${order_num!}&trade_no=${trade_no!}&status=${status!}&project_name=${project_name!}" />
</div>

<script type="text/javascript" src="/static/js/page/pay/ordersRefund/index.js?v=20151215"></script>
	
</body>
</html>
