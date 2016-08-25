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
<div class="place" style="width:120%">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/orders">支持列表</a></li>
    </ul>
</div>
<div class="rightinfo">
	
    <div class="tools">
        <ul class="toolbar">
        	订单号：
        	<input type="text" name="order_num" value="${order_num!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	<!--
        	项目Id：
        	-->
        	<input type="text" name="project_id" value="${project_id!}" style="width:60px;display:none" class="dfinput"/>
        	&nbsp;&nbsp;
        	项目名称：
        	<input type="text" name="project_name" value="${project_name!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	状态：
        	<select class="dfinput" name="status" style="width:120px;">
        	         <option value="-1" >--请选择--</option>
        		    <#if status?? && (status == 0)>
       					<option value="0" selected>未付款</option>
					<#else>
						<option value="0">未付款</option>
					</#if>
					
					<#if status?? && (status == 1)>
						<option value="1" selected>已付款</option>
					<#else>
					    <option value="1" >已付款</option>
					</#if>
					
					<#if status?? && (status == 2)>
					    <option value="2" selected>交易关闭</option>
					<#else>
					     <option value="2" >交易关闭</option>
					</#if>
					
					<#if status?? && (status == 3)>
					    <option value="3" selected>交易失效</option>
					<#else>
					   <option value="3" >交易失效</option>
					</#if>
					
					<#if status?? && (status == 4)>
					    <option value="4" selected>已发货</option>
					<#else>
					    <option value="4" >已发货</option>
					</#if>
					
					<#if status ?? && (status == 5)>
					    <option value="5" selected>已收货</option>
					<#else>
					    <option value="5">已收货</option>
					</#if>
					
					<#if status ?? && (status == 6)>
					   <option value="6" selected>等待退款</option>
					<#else>
					   <option value="6">等待退款</option>
					</#if>
					
					<#if status?? && (status == 7)>
					   <option value="7" selected>已退款</option>
					<#else>
					   <option value="7">已退款</option>
					</#if>
        			
        	</select>
        	<button class="btn" onclick="queryOrderFun()">查询</button>
        </ul>
        
        
    </div>
    <div class="tools">
        <button class="btn" onclick="addRefundOrderFun()">添加批量退款</button>
    </div>
    <table class="tablelist" style="width:120%"  text-overflow:ellipsis>
        <thead>
        <tr>
        	<th style="text-align:center;width:2%;" title=""><input type="checkbox" name="refund_checkbox_group" /></th>
            <th style="text-align:center;width:3%;" title="">用户Id</th>
            <th style="text-align:center;width:5%;" title="支持者">支持者</th>
            <th style="text-align:center;width:4%;" title="项目编号">项目Id</th>
            <th style="text-align:center;width:10%;" title="支持项目">支持项目</th>
            <th style="text-align:center;width:2%;" title="数量">数量</th>
            <th style="text-align:center;width:5%;" title="支持金额">支持金额</th>
            <th style="text-align:center;width:7%;" title="支持途径">支持途径</th>
            <th style="text-align:center;width:8%;" title="付款时间">付款时间</th>
            <th style="text-align:center;width:10%;">订单号</th>
            <th style="text-align:center;width:10%;">流水号</th>
            <th style="text-align:center;width:4%;">状态</th>
            <th style="text-align:center;width:8%;" title="">订单时间</th>
            <th style="text-align:left;color:red;width:3%;">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if ordersPage ??>
            <#list ordersPage.list as o>
       		<tr>
       			<td style="text-align:center;">
       				<#if (o.project_status=9) && (o.status=1)>
       					<input type="checkbox" name="refund_checkbox" value="${o.id!}"/>
       				<#else>
       					<input type="checkbox" name="refund_checkbox" value="${o.id!}" disabled="disabled"/>
       				</#if>
       			</td>
       			<td style="text-align:center;">${o.user_id!}</td>
				<td style="text-align:center;">${o.nickname!}</td>
				<td>${o.project_id!}</td>
				<td>${o.project_name!}</td>
				<td style="text-align:center;">${o.number!}</td>
				<td>${o.money!}</td>
       			<td style="text-align:center;">
       				<#if o.pay_type ??>
       				<#if (o.pay_type == 1)>
       					余额
					<#elseif (o.pay_type == 2)>
						支付宝(alipay)
					</#if>
					</#if>
       			</td>
       			<td style="text-align:center;">
					<#if o.pay_at ?? && (o.pay_at > 0)>
					    ${o.pay_at?number_to_datetime}
					</#if>
       			</td>
       			<td style="text-align:center;">${o.order_num!}</td>
       			<td style="text-align:center;">${o.pay_num!}</td>
       			<td style="text-align:center;"> 
       				<#if (o.status == 0)>
       					未付款
					<#elseif (o.status == 1)>
						已付款
					<#elseif (o.status == 2)>
					       交易关闭
					<#elseif (o.status == 3)>
					        交易失效
					<#elseif (o.status == 4)>
					        已发货
					<#elseif (o.status == 5)>
					       已收货
					<#elseif (o.status == 6)>
						等待退款
					<#elseif (o.status == 7)>
						已退款
					</#if>
       			</td>
       			<td style="text-align:center;">
					<#if o.create_at ?? && (o.create_at > 0)>
					    ${o.create_at?number_to_datetime}
					</#if>
       			</td>
       			<td style="text-align:left;"><a href="/orders/detail/${o.id!}" class="tablelink">查看</a></td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=ordersPage.pageNumber totalPage=ordersPage.totalPage totalRow=ordersPage.totalRow actionUrl="/orders/" urlParas="?order_num=${order_num!}&project_id=${project_id!}&status=${status!}&project_name=${project_name!}" />
</div>

<script type="text/javascript" src="/static/js/page/orders/index.js?v=20151125">
</script>
<script>
	$(parent.frames["leftFrame"].document).find(".menuson li.active").removeClass("active");
	$(parent.frames["leftFrame"].document).find(".ordersList").addClass("active");
</script>
	
</body>
</html>
