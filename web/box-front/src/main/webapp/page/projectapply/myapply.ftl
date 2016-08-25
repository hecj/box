<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>个人中心-- 我的发起</title>
	
    <#include "/page/common/base/include.ftl">
</head>
<body>
   <!-- 头部引用 -->
	<#include "/page/common/n_head.ftl">
   <!-- 内容start！ -->
	<div class="content bg_wrap content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<div class="personal-item-menu">
				<ul>
				<li><a href="/sub/mysub">我的关注</a></li>
				<li><a href="/orders/">我的支持</a></li>
				<li class="current"><a href="/projectapply/myapply">我的发起</a></li>
				
			   </ul>
			</div>
		    <div class="personal-sponsor-con">
		    	<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
        		<#if pageProject ?? && pageProject.getList().size() gt 0>
		    		<ul>
        				<#list pageProject.getList() as p>
				    		<li>
				    			<div class="sponsor-one">
				    				<div class="sponsor-img">
				    					<#if p.cover_image??>
				    						<img src="${STATIC_URL}${p.cover_image!}" alt="" />
				    					<#else>
				    						<img src="${WEBROOT_URL}static/images/icon/loading.png" alt="" />
				    					</#if>
				    				</div>
				    				<div class="sponsor-item-time">
				    					<h3>${p.name!}</h3>
				    					<p>添加时间:</p>
				    					<p>${(p.createtime?number_to_datetime)!}</p>
				    					
			    						<#if p.id??>
						            		<#if p.status == 2||p.status == 5 || p.status == 9>
						            			<div class="sponsor-static sponsor-static-two">${p.statusName!}</div>
						            		<#else>
						            			<div class="sponsor-static sponsor-static-one">${p.statusName!}</div>
						            		</#if>
						            	</#if>
				    				</div>
				    				<div class="sponsor-item-progress">
				    					<h3>${p.progress!}%</h3>
				    					<p>SCHEDULE</p>
				    					<span>${p.fundnow!}/${p.fundgoal!}</span>
				    					<div class="clear"></div>
				    					<b>筹募金额</b>
				    					<i>
				    						<#if p.id??>
							            		<#if p.status == 10 && p.settlement == 0>
							            			<!-- 
							            			<a href="javascript:;" onclick="alert('您的申请已提交')">申请结算</a>
							            			-->
							            			结算中
							            		<#elseif p.status == 10 && p.settlement == 1>
							            			已结算
							            		<#else>
							            			不可结算
							            		</#if>
							            	</#if>
										</i>
				    				</div>
				    				<div class="sponsor-item-operation">
				    					<#if p.id??>
				    						<!-- 查看  --> 
						                    <#if p.status == 1 || p.status == 4 >
						                    	<a href="javascript:;" >查看驳回原因</a>
				    							<a href="javascript:;" onclick="deleteById(${p.id})" class="sponsor-item-a">删除</a>
						                    </#if>
						                    
						                    <#if p.status == 2 >
						                     	<a onmouseover="findExecute(${p.id!},${p.status!})" onmouseout="displayExecute(${p.id!})" class="sponsor-static-two-color sponsor-static-hover">查看驳回原因</a>
						                        <a href="javascript:;" onclick="deleteById(${p.id})" class="sponsor-item-a sponsor-static-two-color">删除</a>	 
						                        <div class="sponsor-static-two-con" id="${p.id}">
						    						<p></p>
												</div>                      
						                    </#if>
						                    
						                    
						                    <!-- 继续填复审表/回报表/提交申请 -->
						                    <#if (p.status == 3 ) &&(isCertify==2)>
						                        <a href="/projectapply/editproject/${p.id}" class="sponsor-static-two-color sponsor-item-a" target="_blank">补充资料</a>
						                    <#elseif (p.status==3)&&(isCertify==0||isCertify==3||isCertify==1)>
						                   		<a href="/usercertify/certify" class="sponsor-static-two-color sponsor-item-a" target="_blank">验证身份</a>
						                    </#if>
						                    
						                    <#if (p.status == 6)&&(isCertify==2)>
						                    	<span>等待上线</span>
						                    </#if>
						                    <#if (p.status == 5)&&(isCertify==2)>
						                   		<a onmouseover="findExecute(${p.id!},${p.status!})" onmouseout="displayExecute(${p.id!})" class="sponsor-static-two-color sponsor-static-hover">查看驳回原因</a>
						                        <a href="/projectapply/editproject/${p.id}" class="sponsor-item-a sponsor-static-two-color" target="_blank">编辑</a>
						                        <div class="sponsor-static-two-con" id="${p.id}">
						    						<p></p>
												</div>
						                    </#if>
						                    
						                    <!-- 预览 -->
						                    <#if ((p.status gte 7) && (p.status lte 8) &&(isCertify==2))||((isCertify==2)&&(p.status==10)&&(p.settlement==0))>
						                        <a href="/project/detail/${p.id}" class="sponsor-static-two-color" target="_blank">预览</a>
						                    </#if>
						                    
						                     <#if (p.status == 9) &&(isCertify==2)> 
						                        <a href="javascript:;" onclick="deleteById(${p.id})" class="sponsor-item-a sponsor-static-two-color">删除</a>
						                    </#if>
						                    
						                     <#if (p.status == 10)  &&(isCertify==2) &&(p.settlement==1)>
						                     	 <a href="/project/detail/${p.id}" class="sponsor-static-two-color sponsor-static-hover" target="_blank">预览</a>
						                        <a href="javascript:;" onclick="deleteById(${p.id})" class="sponsor-item-a sponsor-static-two-color">删除</a>
						                    </#if>
						                </#if>
				    				</div>
				    			</div>
				    		</li>
				    	</#list>
				    </ul>
			    <#else>
			    	<!-- 无发起... -->
			    	<div class="personal-no-item" style="display:block;">
				    	<span class="no-item-bg no-item-bg03"></span>
						<p>您暂时没有发起的项目</p>
						<div class="personal-find-item">
							<a href="/projectapply/agreement">去发起项目</a>
						</div>
					</div>
	    		</#if>
		    	
		    </div>
		</div>
		<div class="page-wrap-sub">
			<div id="pagemyapply" class="page-div clearfix"></div>
		</div>
	</div>
	
   <!-- 内容end！ -->
   
   <!-- 页尾引用 -->
	<#include "/page/common/foot.ftl" />
	
	<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>   
	<script type="text/javascript" src="/static/js/DD_roundies_min.js"></script>
	<script type="text/javascript" src="/static/js/slider.js"></script>
	<script type="text/javascript" src="/static/js/index.js"></script>
	<script type="text/javascript" src="/static/js/sHover.min.js"></script>
	<script type="text/javascript" src="/static/js/tipso.min.js"></script>
	<script type="text/javascript" src="/static/js/page/full_paging.js"></script>
	<script type="text/javascript" src="/static/js/page/projectapply/myapply.js?v=20151210"></script>
	<!-- <script type="text/javascript" src="/static/js/cf-index.js"></script> -->
	<script type="text/javascript">
		
		DD_roundies.addRule('.border-radiusBtn', '50%', true); 
	
		if(${totalPage} > 1){
			$("#pagemyapply").createPage({
				pageCount:${totalPage},
				current:${pageNumber},
				backFn:function(p){
					console.log(p);
					location = "/projectapply/myapply/"+p;
				}
			});
		}
	</script>
	</body>
</html>



