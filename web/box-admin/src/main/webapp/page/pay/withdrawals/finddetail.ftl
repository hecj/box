<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>tbc列表</title>
		<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
		<link href="/static/js/laydate/need/laydate.css" rel="stylesheet" type="text/css" />
		
	</head>
	<#include "/page/common/_paginate_new.ftl" />
	<body>
		<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
		<div class="place" style="width:120%">  
		    <span>&gt;&gt;</span>
		    <ul class="placeul">
		        <li><a href="/pay/withdrawals/list">提现信息查看</a></li>
		    </ul>
	    </div>
	    <#if withDrawals??>
	    <div>
		  	<ul class="forminfo">
		    	<li>
		    	    <cite>
			    		<label class="label">ID：</label>
			    		<label style="width:200px;">${withDrawals.id!}</label>
		    		</cite>
		   	    </li>
		   	     <li>
		    	    <cite>
			    		<label class="label">用户昵称：</label>
			    		<label style="width:200px;">${withDrawals.getUser().nickname!}</label>
		    		</cite>
		   	    </li>
		   	    <li>
		    	    <cite>
			    		<label class="label">手机号：</label>
			    		<label style="width:200px;">${withDrawals.getUser().phone!}</label>
		    		</cite>
		   	    </li>
		   	    
		   	    <!-- 
			   	    <li>
			    	    <cite>
				    		<label class="label">订单号：</label>
				    		<label style="width:200px;">${withDrawals.batch_no!}</label>
			    		</cite>
			   	    </li>
			   	    
		   	    -->
		   	    <li>
		    	    <cite>
			    		<label class="label">提现金额：</label>
			    		<label style="width:200px;">￥${withDrawals.amount!}</label>
		    		</cite>
		   	    </li>
		   	    
		   	    <!-- 
			   	    <li>
			    	    <cite>
				    		<label class="label">充值方式：</label>
				    		<label style="width:200px;">支付宝</label>
			    		</cite>
			   	    </li>
		   	    
		   	    -->
		   	    <li>
		    	    <cite>
			    		<label class="label">提现时间：</label>
			    		<label style="width:200px;">${withDrawals.create_at?number_to_datetime!}</label>
		    		</cite>
		   	    </li>
		   	    <#if withDrawals.status == 0 >
			   	    <li>
			   	    	<cite>
			   	    		<label class="label">审核状态：</label>
			   	    		<label style="width:200px;">
						   	 	<input type="radio" name="check" value="0"/>通过
						   	 	<input type="radio" name="check" value="1"/>不通过
					   	 	</label>
					   	 </div>
				   	 
			   	    	</cite>
			   	    </li>
			   	    
			   	    <li>
			   	    	<cite>
						   	<input type="button" onclick="changeStatus(${withDrawals.id})" class="btn" value="提交"/> 
			   	    	</cite>
			   	    </li>
			   	    
		   	   	</#if>
		   	   	
		   	   	
		   	 </ul>
		</div>
		<#else>
	   	 	暂查不到该记录,请刷新页面重试或联系技术人员
	   	</#if>
	   	<div id="abcddd">
	   		<input type="button" class="btn" onclick="loaddetail(1)"/ value="账户流水" style="border:1px solid #d5d5d5"/>
	   		<input type="button" class="btn" onclick="loaddrawdetail('${withDrawals.id!}','${withDrawals.batch_no!}')" value="提现记录" style="border:1px solid #d5d5d5"/>
	   	</div>
	   	
	   	<div id="detail">
	   		
		</div>
		
	    <div id="support_page" class="page-div clearfix">
	    
		</div>
	   	
	   	<div style="display:none" id="withdrwaalsUid">${withDrawals.user_id!}</div>
	</body>
	<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
	<script type="text/javascript" src="/static/uimaker/js/jquery.continuousCalendar-4.11.0-min.js"></script>
	<script type="text/javascript" src="/static/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="/static/js/page/pay/withdrawals/finddetail.js?v=20151211"></script>
	<script type="text/javascript" src="/static/js/page/full_paging.js?v=20151215"></script>
	<script type="text/javascript">
		loaddetail(1);
	</script>
</html>