<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>个人中心</title>
    <#include "/page/common/base/include.ftl">
</head>
<body>
   <!-- 头部引用 -->
	<#include "/page/common/n_head.ftl">
   <!-- 内容start！ -->
	<div class="content bg_wrap content-col-f">
		<!-- 个人中心头部引用 -->	
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<div class="personal-item-menu">
				<ul>
					<li class="current"><a href="/sub/mysub">我的关注</a></li>
					<li><a href="/orders/">我的支持</a></li>
					<li><a href="/projectapply/myapply">我的发起</a></li>
			   </ul>
			</div>
			<div class="personal-item-attention-con">
				<#if (subscribeList.size()>0)>
		   	  		<ul>
				   	  	<#list subscribeList as s>
				   	  	<li>
				   	  		<div class="item-attention-con-one">
				   	  			<a href="/project/detail/${s.project_id!-1}" class="attention-one">了解详情</a>
				   	  			<a href="/project/detail/${s.project_id!-1}" target="_blank">
				   	     			<img src="${STATIC_URL}${s.cover_image!}" alt="">
				   	     		</a>
				   	     		<div class="item-attention-con-one-detalis">
				   	     			<h3><a href="javascript:;">${s.name!}</a></h3>
				   	     			<span class="cancel-attention" onclick="subscribeFun(${s.project_id!-1},2)">取消关注</span>	<p>${s.create_at!}</p>
				   	     		</div>
				   	  		</div>
				   	  	</li>
					    </#list>
			    	</ul>
		    	<#else>
		    		<!-- 无关注... -->
					<div class="personal-no-item" style="display:block;">
					    	<span class="no-item-bg no-item-bg01"></span>
							<p>您暂时没有关注的项目</p>
							<div class="personal-find-item">
								<a href="/project">去寻找目标</a>
							</div>
					</div>
		    	</#if>
			</div>
			<div class="page-wrap-sub">
		    	<div id="sub_page" class="page-div clearfix"></div>
		    </div>
		</div>
	</div>
   <!-- 内容end！ -->
  
   <!-- 页尾引用 -->
	<#include "/page/common/foot.ftl" />
</body>
	<script type="text/javascript" src="/static/js/page/sub/mysub.js?v=20151127"></script>
	<script type="text/javascript" src="/static/js/page/full_paging.js"></script>
	<script>
		if(${totalPage} > 1){
			$("#sub_page").createPage({
				pageCount:${totalPage},
				current:${pageNumber},
				backFn:function(p){
					console.log(p);
					location = "/sub/mysub/"+p;
			}});
		}
	</script>
</html>



