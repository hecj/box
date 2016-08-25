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
    <span>位置：</span>
    <ul class="placeul no-bor">
        <li class="cur"><a href="/balanceUser/">充值列表</a></li>
        <li><a href="/pay/withdrawals/list">提现列表</a></li>
    </ul>
	</div>
	    <div class="rightinfo current">
	    	 <div class="tools">
		        <ul class="toolbar inline">
		        	手机号/UID：
		        	<input type="text" name="usernameORuid" value="${usernameORuid!}" style="width:150px;" class="dfinput"/>
		        	&nbsp;&nbsp;
		        	金额：
		        	<input type="text" name="amount" value="${amount!}" style="width:150px;" class="dfinput"/>
		        	&nbsp;&nbsp;
		        	充值时间：
		        	<input type="text" name="regTimeS" value="${regTimeS!}" style="width:150px;" class="inline laydate-icon" id="start" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
		        	&nbsp;-&nbsp;
		        	<input type="text" name="regTimeE" value="${regTimeE!}" style="width:150px;" class="inline laydate-icon" id="end" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"/>
		        	&nbsp;&nbsp;
		        	支付方式：
		        	<select class="dfinput" name="payType" style="width:120px;">
		        	         <option value="-1" >--请选择--</option>
		        		    <#if payType?? && (payType == 1)>
		       					<option value="1" selected>支付宝</option>
							<#else>
								<option value="1">支付宝</option>
							</#if>
							
							<#if payType?? && (payType == 2)>
		       					<option value="2" selected>网银在线</option>
							<#else>
								<option value="2">网银在线</option>
							</#if>
		        	</select>
		        	&nbsp;&nbsp;
		        	到款状态：
		        	<select class="dfinput" name="payStatus" style="width:120px;">
		        	         <option value="-1" >--请选择--</option>
		        		    <#if payStatus?? && (payStatus == 1)>
		       					<option value="1" selected>未成功</option>
		       				<#else>
		       					<option value="1">未成功</option>
		       				</#if>

							<#if payStatus?? && (payStatus == 2)>
								<option value="2" selected>充值成功</option>
							<#else>
								<option value="2">充值成功</option>
		       				</#if>

							<#if payStatus?? && (payStatus == 3)>
								<option value="3" selected>充值失败</option>
							<#else>
								<option value="3">充值失败</option>
							</#if>
		        	</select>
		        	<button class="btn" onclick="queryOrderFun()">查询</button>
		        	<button class="btn" onclick="deleteByIds()">删除</button>
		        </ul>        
    		</div>
		    <table class="tablelist">
		    	<thead>
		    	<tr>
		    		<th ><input  name="refund_checkbox_group" type="checkbox" /></th>
					<th title="UID">UID</th>
					<th title="username" />手机号</th>
					<th title="nickname" />用户昵称</th>
					<th title="recharge">充值金额</th>
					<th title="way">充值途径</th>
					<th title="regtime" />充值时间</th>
					<th title="regstatus" />到款状态</th>
		         	<th style="color:red">操作</th>
		        </tr>
		        </thead>
		        <tbody>
				<#if rechargePage?? && rechargePage.getList().size() gt 0>
		       	<#list rechargePage.getList() as bal>
		        <tr>
		       		<td><input class="onecheck" name="refund_checkbox" type="checkbox" value="${bal.id}" /></td>
					<td >${bal.u_id!} </td>
					<td >${bal.u_phone!} </td>
					<td >${bal.u_nickname!}</td>
					<td >${bal.amount!}</td>
					<td >支付宝</td>
					<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
					<td >${bal.create_at?number_to_datetime}</td>
					<td >
						 <#if bal.status?? && (bal.status == 1)>
								未成功
						<#elseif bal.status?? && (bal.status == 2)>
								充值成功
						<#elseif bal.status?? && (bal.status == 3)>
								充值失败
						</#if>
					</td>
		       		<td><a href="/balanceUser/look/${bal.id!}-${rechargePage.pageNumber!}" class="tablelink">查看</a>&nbsp;
		       			<a href="javascript:void(0)" class="tablelink" onclick="deleteById(${bal.id!})">删除</a>&nbsp;
		       		</td>
		        </tr> 
				</#list>
				</#if>
		     </tbody>
		    </table>
	     <#include "/page/common/_paginate_new.ftl" />
		 <@paginate currentPage=rechargePage.pageNumber totalPage=rechargePage.totalPage totalRow=rechargePage.totalRow actionUrl="/balanceUser/" urlParas="?usernameORuid=${usernameORuid!}&amount=${amount!}&regTimeS=${regTimeS!}&regTimeE=${regTimeE!}&payType=${payType!}&payStatus=${payStatus!}" />
		</div> 
		
		<!--这里写提现的内容-->
		<div class="rightinfo">
		
		</div>
	<script type="text/javascript">
	!function(){
		laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
		//laydate({elem: '#demo'});//绑定元素
	}();
	
	//日期范围限制
	var start = {
	    elem: '#start',
	    format: 'YYYY-MM-DD',
	    min: laydate.now(), //设定最小日期为当前日期
	    max: '2099-06-16', //最大日期
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	         end.min = datas; //开始日选好后，重置结束日的最小日期
	         end.start = datas //将结束日的初始值设定为开始日
	    }
	};
	var end = {
	    elem: '#end',
	    format: 'YYYY-MM-DD',
	    min: laydate.now(),
	    max: '2099-06-16',
	    istime: true,
	    istoday: false,
	    choose: function(datas){
	        start.max = datas; //结束日选好后，充值开始日的最大日期
	    }
	};
	laydate(start);
	laydate(end);
		
	function queryOrderFun(){
		var url = "/balanceUser?";
		var usernameORuid = jQuery("input[name=usernameORuid]").val();
		if(usernameORuid.length > 0){
			url = url+"&usernameORuid="+jQuery.trim(usernameORuid);
		}
		var amount = jQuery("input[name=amount]").val();
		if(amount.length > 0){
			url = url+"&amount="+jQuery.trim(amount);
		}
		var regTimeS = jQuery("input[name=regTimeS]").val();
		if(regTimeS.length > 0){
			url = url+"&regTimeS="+jQuery.trim(regTimeS);
		}
		var regTimeE = jQuery("input[name=regTimeE]").val();
		if(regTimeE.length > 0){
			url = url+"&regTimeE="+jQuery.trim(regTimeE);
		}
		var payType = jQuery("select[name=payType]").val();
		if(payType != -1){
			url = url+"&payType="+payType;
		}
		var payStatus = jQuery("select[name=payStatus]").val();
		if(payStatus != -1){
			url = url+"&payStatus="+payStatus;
		}
		location= url;
	}
	$(".placeul li").click(function(){
		$(this).addClass("cur").siblings().removeClass("cur");
		$(".rightinfo").hide();
	    $(".rightinfo").eq($(this).index()).show().sibilings().hide();
	});
	// 绑定comment_checkbox_group事件
	$("input[name=refund_checkbox_group]").click(function(){
		if(this.checked){
			$("input[name=refund_checkbox]").each(function(){
				this.checked=true;
			});
		}else{
			$("input[name=refund_checkbox]").each(function(){
				this.checked=false;
			});
		}
	});
	
	function deleteById(id){
		if(confirm("确认要删除该条数据吗？")){
			location = "/balanceUser/delete/" + id + "-${rechargePage.pageNumber!}";
		}
	}
 	function deleteByIds(){
 		if(confirm("确认要删除选中数据吗 ？")){
	 		var ids = ""; 
	 		$("input[name=refund_checkbox]").each(function(){
					if(this.checked){
						ids += this.value + ",";
					}
			});
			if(ids.length > 0){
				location = "/balanceUser/deleteByIds/" + ids + "-${rechargePage.pageNumber!}";
			}
 		}
 	}
	!function(){
 		$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
 	}();
	</script>
	</body>
</html>