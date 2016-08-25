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
<div class="place" style="width:100%">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/alipayRecord/query">支付宝交易记录</a></li>
    </ul>
</div>
<div class="rightinfo">
    <div class="tools">
        <ul class="toolbar">
        	订单号：
        	<input type="text" name="out_trade_no" value="${out_trade_no!}" style="width:200px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	支付宝交易号：
        	<input type="text" name="trade_no" value="${trade_no!}" style="width:220px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	支付宝帐号：
        	<input type="text" name="buyer_email" value="${buyer_email!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	<button class="btn" onclick="queryAlipayRecordFun()">查询</button>
        </ul>
    </div>
    <table class="tablelist" style="width:100%">
        <thead>
        <tr>
            <th style="text-align:center;width:5%;" title="">Id</th>
            <th style="text-align:center;width:10%;" title="">订单号</th>
            <th style="text-align:center;width:13%;" title="">支付宝交易号</th>
            <th style="text-align:center;width:5%;" title="">交易金额</th>
            <th style="text-align:center;width:10%;" title="">交易状态</th>
            <th style="text-align:center;width:10%;" title="">买家支付宝账户</th>
            <th style="text-align:center;width:5%;" title="">交易类型</th>
            <th style="text-align:center;width:10%;" title="">交易付款时间</th>
            <th style="text-align:center;width:10%;" title="">交易创建时间</th>
        </tr>
        </thead>
        <tbody>
        <#if alipayRecordPage ??>
            <#list alipayRecordPage.list as ar>
       		<tr>
       			<td>${ar.id!}</td>
       			<td>${ar.out_trade_no!}</td>
       			<td>${ar.trade_no!}</td>
       			<td>${ar.total_fee!}</td>
       			<td>${ar.trade_status!}</td>
       			<td>${ar.buyer_email!}</td>
       			<td>
       				<#if ar.trade_type == 1>
       					支付订单
       				<#elseif ar.trade_type == 2>
       				    充值
       				</#if>
       			</td>
       			<td>${ar.gmt_payment!}</td>
       			<td>${ar.gmt_create!}</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=alipayRecordPage.pageNumber totalPage=alipayRecordPage.totalPage totalRow=alipayRecordPage.totalRow actionUrl="/alipayRecord/query/" urlParas="?out_trade_no=${out_trade_no!}&trade_no=${trade_no!}&buyer_email=${buyer_email!}" />
</div>

<script type="text/javascript" src="/static/js/page/pay/alipay_query.js"></script>
	
</body>
</html>
