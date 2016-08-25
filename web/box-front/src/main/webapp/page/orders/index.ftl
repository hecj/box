<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>个人中心-- 我的支持</title>
    <#include "/page/common/base/include.ftl">
</head>
<body>
   <#include "/page/common/n_head.ftl" />
   <!-- 内容start！ -->
	<div class="content bg_wrap content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<div class="personal-item-menu">
				<ul>
					<li><a href="/sub/mysub">我的关注</a></li>
					<li class="current"><a href="/orders/">我的支持</a></li>
					<li><a href="/projectapply/myapply">我的发起</a></li>
			   </ul>
			</div>
		     <div class="personal-support-con">
		     <#if orderList?? && (orderList.size() > 0)>
		     	<ul>
		     		<!-- li 循环开始 -->
				    <#list orderList as order>
				     		<li>
								<div class="support-one-con">
				     				<a href="/project/detail/${order.project_id!}" target="_blank">
					   	     			<img src="${STATIC_URL}${order.pj_cover_image!}" alt="">			
					   	     		</a>
					   	     		<div class="support-title">
					   	     			<h3><a href="javascipt:;">${order.pj_name}</a></h3>
					   	     			<#if order.pj_status == 9>
					   	     				<span class="err">${order.projectStatus}</span>
					   	     			<#else>
					   	     				<span class="pass">${order.projectStatus}</span>
					   	     			</#if>
					   	     		</div>
					   	     		<p>订单编号&nbsp;&nbsp;:&nbsp;&nbsp;${order.order_num!}</p>
					   	     		<p>预计回报时间&nbsp;&nbsp;:&nbsp;&nbsp;${order.pj_expirestime!} <span
					   	     			class="support-return support-color-green">回报内容</span></p>
					   	     		<p>支付金额</p>
						   	     	<p><i>${order.money!}</i>元<#if order.showDelete?? && order.showDelete==1><span class="support-del"><b></b><i>删除</i></span></#if></p>
		                            <div class="support-static">
										<#if order.payStatus??>
			                             	<#if order.payStatus=="已失效" || order.payStatus=="等待退款" || order.payStatus=="交易关闭">
			                             	<span class="support-color-r support-color-red">${order.payStatus}</span>
											<#elseif order.payStatus=="已退款" >
			                             	<span class="support-color-r support-color-green">${order.payStatus}</span>
			                             	<#elseif order.payStatus=="已付款">
			                             	<span class="support-color-r support-color-green">${order.payStatus}<font style="font-size:14px">(${order.orderStatus})</font></span>
			                             	<#else>
			                             	<span class="support-color-r support-color-orange">${order.payStatus}</span>
			                             	</#if>
		                             	</#if>
		                             	<ul>
		                             		<#if order.delivery_delivery_no??>
		                             			<li><a href="javascript:;" class="support-boking-progress support-color-orange">查看物流 >></a></li>
		                             		</#if>
		                             		<li><a href="javascript:;" class="support-color-green support-booking">订单详情 >></a></li>
		                             	</ul>
		                             </div>
				     			</div>
				     			<!-- 我的支持.背景start. -->
				     			<div class="support-bgc">
				     			</div>
				     			<!-- 我的支持.背景end. -->
				     		
				     			<!-- 回报内容.. -->
				     			<div class="support-back-con">
				     				<h3>回报</h3>
				     				<p>${order.pd_desc!}</p>
				     			</div>
				     			<!-- 回报内容.. -->
				     			<!-- 收货信息.. -->
				     			<div class="support-address">
				     			   <h3>收货信息</h3> 
				     			   	<div>
				     			   		<ul>
				     			   			<div class="support-name-phone">
				     			   				<div class="support-name">
				     			   					<#if order.oa_name??><i>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;：${order.oa_name} </i></#if>
													<#if order.oa_phone??><span>联系电话：${order.oa_phone}</span></#if>
				     			   				</div>
				     			   			</div>
				     			   			<#if order.p_pro??><li>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;&nbsp;：${order.p_pro}<#if order.c_cit??>&nbsp;${order.c_cit}</#if><#if order.a_are??>&nbsp;${order.a_are}</#if></li></#if>
				     			   			<li>下单时间&nbsp;&nbsp;：${order.create_at}</li>
				     			   			<li>订单编号：${order.order_num!}</li>
				     			   			<li>付款方式：<#if order.pay_type==2>支付宝<#elseif order.pay_type==1>余额支付<#else>未知</#if></li>
				     			   		</ul>
				     			   	</div>
				     			</div>
								<!-- 收货信息.. -->	
								<!-- 删除弹框.. -->
								<div class="support-del-con">
								 	<span></span>
								 	<p>是否删除项目</p>
								 	<p><b onclick="yes_del(${order.id}, ${pageNumber!});">是</b><i class="no_del">否</i></p>
								 </div>	
								<!-- 删除弹框.. -->	
								<!-- 物流详情.. -->
								<#if order.delivery_delivery_no??>
								<div class="support-flow-con">
									<p>承运公司：${order.md_shipmode!}</p>
									<p>运单编号：${order.delivery_delivery_no!}</p>
								</div>
								</#if>
								<!-- 物流详情.. -->
								<div class="support-btn">
										<!-- 立即支付  取消付款-->
					     				<#if order.immediate_pay?? && order.immediate_pay==1>
					     					<a href="/orders/confirm/${order.order_num!}" target="_blank">
							     			<div class="support-btn-l support-btn-color-green">
							     				<div class="support-btn-hover"></div>
							     				<h4>立即付款</h4>
							     			
							     			</div>
							     			</a>
						     			<#elseif order.immediate_pay?? && order.immediate_pay==0>
						     				<div class="support-btn-l support-btn-color-grey">
						     					<div class="support-btn-hover"></div>
							     				<h4>立即付款</h4>
							     				
							     			</div>
						     			</#if>
						     			<#if order.cancel_pay?? && order.cancel_pay==1>
							     			<div class="support-btn-r support-btn-color-green" onclick="cancelPay(${order.id}, ${pageNumber})">
							     				<div class="support-btn-hover"></div>
							     				<h4>取消订单</h4>
							     				
							     			</div>
							     		<#elseif  order.cancel_pay?? && order.cancel_pay==0>
							     			<div class="support-btn-r support-btn-color-grey">
							     				<div class="support-btn-hover"></div>
							     				<h4>取消订单</h4>
							     			</div>
						     			</#if>

										<!-- 确认收取回报  项目评论-->
										<#if order.confirm_product?? && order.confirm_product==1>
							     			<div class="support-btn-l support-btn-color-green" onclick="confirmProduct(${order.id}, ${pageNumber})">
							     				<div class="support-btn-hover"></div>
							     				<h4>确认收取回报</h4>
							     				
							     			</div>
						     			<#elseif order.confirm_product?? && order.confirm_product==0>
						     				<div class="support-btn-l support-btn-color-grey">
						     					<div class="support-btn-hover"></div>
							     				<h4>确认收取回报</h4>
							     				
							     			</div>
						     			</#if>
						     			<#if order.common?? && order.common==1>
							     			<div class="support-btn-r support-btn-color-blue" onclick="goToCommon(${order.project_id})">
							     				<div class="support-btn-hover"></div>
							     				<h4>项目评论</h4>
							     			
							     			</div>
							     		<#elseif  order.common?? && order.common==0>
							     			<div class="support-btn-r support-btn-color-grey">
							     				<div class="support-btn-hover"></div>
							     				<h4>项目评论</h4>
							     				
							     			</div>
						     			</#if>
				     		     </div>
				     		</li>
					</#list>  
		     	</ul>
		    <#else>
			   	 <div class="personal-no-item" style="display:block;">
				    	<span class="no-item-bg no-item-bg03"></span>
						<p>您暂时还没有订单</p>
						<div class="personal-find-item">
							<a href="/project">去寻找机会</a>
						</div>
				  </div>
			</#if>	
		     </div>
		     <div class="page-wrap-sub"><div id="sub_page_footer" class="page-div clearfix"></div></div>
		</div>
	</div>

   <!-- 内容end！ -->
   <!-- 页尾引用 -->
	<#include "/page/common/foot.ftl" />
</body>
	<script type="text/javascript" src="/static/js/page/orders/support/personal-support.js"></script>
	<script type="text/javascript" src="/static/js/page/full_paging.js"></script>
	<script type="text/javascript" src="/static/js/page/orders/index.js"></script>
	<script type="text/javascript">
		paginate(${totalPage}, ${pageNumber});
	</script>
	<script type="text/javascript" src="/static/js/DD_roundies_min.js"></script> 
	<script type="text/javascript">
	
		DD_roundies.addRule('.border-radiusBtn', '50%', true); 
	</script>
</html>