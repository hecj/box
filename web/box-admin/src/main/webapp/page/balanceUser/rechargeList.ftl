<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>tbc列表</title>
		<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
		<link href="/static/js/laydate/need/laydate.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
		<script type="text/javascript" src="/static/js/common.js"></script>
		<script type="text/javascript" src="/static/uimaker/js/jquery.continuousCalendar-4.11.0-min.js"></script>
		<script type="text/javascript" src="/static/js/laydate/laydate.js"></script>
	</head>
	<#include "/page/common/_paginate_new.ftl" />
	<body>
		<div class="place" style="width:120%">
		    <span>&gt;&gt;</span>
		    <ul class="placeul">
		        <li><a href="/balanceUser/${currentpage!}">充值信息查看</a></li>
		    </ul>
	    </div>
	    <div>
		     <ul class="forminfo">
		    	<li>
		    	    <cite>
			    		<label class="label">ID：</label>
			    		<label style="width:200px;">${rdp.id!}</label>
		    		</cite>
		   	    </li>
		   	     <li>
		    	    <cite>
			    		<label class="label">用户昵称：</label>
			    		<label style="width:200px;">${rdp.u_nickname!}</label>
		    		</cite>
		   	    </li>
		   	    <li>
		    	    <cite>
			    		<label class="label">手机号：</label>
			    		<label style="width:200px;">${rdp.u_phone!}</label>
		    		</cite>
		   	    </li>
		   	    <li>
		    	    <cite>
			    		<label class="label">订单号：</label>
			    		<label style="width:200px;">${rdp.order_num!}</label>
		    		</cite>
		   	    </li>
		   	    <li>
		    	    <cite>
			    		<label class="label">充值金额：</label>
			    		<label style="width:200px;">${rdp.amount!}</label>
		    		</cite>
		   	    </li>
		   	    <li>
		    	    <cite>
			    		<label class="label">充值方式：</label>
			    		<label style="width:200px;">支付宝</label>
		    		</cite>
		   	    </li>
		   	    <li>
		    	    <cite>
			    		<label class="label">充值时间：</label>
			    		<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
			    		<label style="width:200px;">${rdp.create_at?number_to_datetime}</label>
		    		</cite>
		   	    </li>
		   	    <li>
		    	    <cite>
			    		<label class="label">到款状态：</label>
			    		<label style="width:200px;">
			    			<#if rdp.status?? && (rdp.status == 1)>
								未成功
							<#elseif rdp.status?? && (rdp.status == 2)>
								充值成功
							<#elseif rdp.status?? && (rdp.status == 3)>
								充值失败
							</#if>
			    		</label>
		    		</cite>
		   	    </li>
		   	 </ul>
		</div>
	</body>
</html>