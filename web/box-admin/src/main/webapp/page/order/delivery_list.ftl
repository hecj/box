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
<div class="place" style="width:105%">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/orders/deliveryList/">发货单列表</a></li>
    </ul>
</div>
<div class="rightinfo">
	
    <div class="tools">
        <ul class="toolbar">
        	发货单流水号：
        	<input type="text" name="delivery_flow_no" value="${delivery_flow_no!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	订单号：
        	<input type="text" name="delivery_no" value="${delivery_no!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	项目名称：
        	<input type="text" name="project_name" value="${project_name!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	收货人：
        	<input type="text" name="delivery_name" value="${delivery_name!}" style="width:150px;" class="dfinput"/>
        	&nbsp;&nbsp;
        	状态：
        	<select class="dfinput" name="status" style="width:170px;">
        	         <option value="-1" >--请选择--</option>
        		    <#if status?? && (status == "0")>
       					<option value="0" selected>未发货</option>
					<#else>
						<option value="0">未发货</option>
					</#if>
					
					<#if status?? && (status == "1")>
						<option value="1" selected>已发货</option>
					<#else>
					    <option value="1" >已发货</option>
					</#if>
        	</select>
        	<button class="btn" onclick="queryDeliveryFun()">查询</button>
        </ul>
    </div>
    <table class="tablelist" style="width:105%">
        <thead>
        <tr>
            <th style="text-align:center;width:2%;" title="uid">uid</th>
            <th style="text-align:center;width:5%;" title="发货单号">发货单号</th>
            <th style="text-align:center;width:4%;" title="物流名称">物流名称</th>
            <th style="text-align:center;width:10%;" title="订单号">订单号</th>
            <th style="text-align:center;width:8%;" title="支持项目">支持项目</th>
            <th style="text-align:center;width:8%;" title="支持时间">支持时间</th>
            <th style="text-align:center;width:7%;" title="收货人">收货人</th>
            <th style="text-align:center;width:8%;" title="发货时间">发货时间</th>
            <th style="text-align:center;width:8%;">发货单状态</th>
            <th style="text-align:center;width:8%;">流水号</th>
            <th style="text-align:center;color:red;width:10%;">操作</th>
        </tr>
        </thead>
        <tbody>
        <#if deliverysPage ??>
            <#list deliverysPage.list as o>
			<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
       		<tr>
       			<td style="text-align:center;">${o.uid!}</td>
       			<td>${o.发货单号!}</td>
				<td>${o.物流名称!}</td>
				<td>${o.订单号!}</td>
				<td>${o.支持项目!}</td>
				<td style="text-align:center;">${o.支持时间?number_to_datetime}</td>
				<td style="text-align:center;">${o.收货人!}</td>
				<td style="text-align:center;">${o.发货时间?number_to_datetime}</td>
				<td style="text-align:center;">${o.发货单状态!}</td>
				<td>${o.流水号!}</td>
       			<td>
       				<#if o.发货单状态?? && o.发货单状态==0>
						<a href="/orders/lookDelivery/${o.id!}" class="tablelink">发货</a>
					</#if>
       				<a href="/orders/lookDelivery/${o.id!}" class="tablelink">查看</a>
       			</td>
       		</tr>
       		</#list>
        </#if>
        </tbody>
    </table>
    <#include "/page/common/_paginate_new.ftl" />
    <@paginate currentPage=deliverysPage.pageNumber totalPage=deliverysPage.totalPage totalRow=deliverysPage.totalRow actionUrl="/orders/deliveryList/" urlParas="?delivery_flow_no=${delivery_flow_no!}&delivery_no=${delivery_no!}&project_name=${project_name!}&delivery_name=${delivery_name!}&status=${status!}" />
    
</div>
<script type="text/javascript" src="/static/js/page/orders/deliveryList.js?v=20151201"></script>
	
</body>
</html>
