<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<meta charset="utf-8"/>
		<title>盒子众筹-中国最具影响力的创业众筹融资平台</title>
		<meta name="keywords" content="众筹,融资,众筹平台,创业融资"/>
		<#include "/page/common/base/include.ftl">
		<meta name="description" content="盒子众筹是中国最具影响力的创业众筹融资平台，专业为有梦想、有创意、有项目的朋友提供募资、投资、孵化、运营一站式综合众筹服务，协助您实现自己的创业梦想。好平台，好起点，尽在盒子众筹！"/>
		
		<script type="text/javascript">
			function detail(id){
				var id = id;
				location = "/project/detail/"+id;
			}
			
			function funcCate(categord_id){
				window.alert(categord_id);
				var status = -1;	
				location = "/project/select?categord_id="+categord_id+"&status="+status; 
			}
		</script>
		<style>
			li {list-style-type:none;}	
		</style>
	</head>
	<body>
		<#include "/page/common/head.ftl">
		
		<#if user??>
	        <div>
	            <h1><a href="/personal">个人中心</a></h1>
			</div>
		</#if>
		
		<br><br>
		
		<div class="pic">
			轮播图
			<ul>
			<#list configList  as config>
				<li>标题: ${config.key}+${config.value}</li><br>
			</#list>
			</ul>
			<a href="/projectapply/agreement">发起一个新项目</a>
		</div>
		
		<hr><br><br>
		
		<div>
			本周推荐
			<ul>
				<li><a href="/project/detail/${recommendProject.id!}"><h4>标题: ${recommendProject.name!}</a></h4></li>
				<li>发布人: ${recommendProject.nickname!}</li>
				<li>描述:${recommendProject.desc!}</li>
				<li>状态:
						<#if recommendProject.status=7>
							预热中
						<#elseif recommendProject.status=8>
							众筹中
						<#elseif recommendProject.status=10>
							已成功
						<#else>
							未知
						</#if>
				</li>
				<li>地理位置:${recommendProject.province!} -${recommendProject.city!}</li>
				<li>进度:${recommendProject.progress!}%</li>
				<li>已筹:￥${recommendProject.fundnow!}（总额：${recommendProject.fundgoal!}）</li>
				<li>剩余天数:${recommendProject.surplus_days!}天</li>
			</ul>
		</div>
		
		<br><br><hr><br><br>
			
		<div>
			<label>行业选择：</label>
			<a href="#" name="type" onclick="loadCategory(-1)">全部</a>
			<#list projectTypeList as type>	
				<a href="#" name="type" onclick="loadCategory(${type.id!})">${type.name!}</a>		
			</#list>	
		</div>
		
		<br><br>
		
		<div>
			最新上线  <a id="" name="" href="/project">查看全部</a><br><br>
			<#list threeProjectlist! as p>
				<ul>
					<li><a href="/project/detail/${p.id!}"><h4>标题: ${p.name!}</a></h4></li>
					<li>发布人: ${p.nickname!}</li>
					<li>描述:${p.desc!}</li>
					<li>状态:
							<#if p.status=7>
								预热中
							<#elseif p.status=8>
								众筹中
							<#elseif p.status=10>
								已成功
							<#else>
								未知
							</#if>
					</li>
					<li>地理位置:${p.province!} -${p.city!}</li>
					<li>进度:${p.progress!}%</li>
					<li>已筹:￥${p.fundnow!}（总额：${p.fundgoal!}）</li>
					<li>剩余天数:${p.surplus_days!}天</li>
				</ul>
			</#list>
		</div>
		
		<#include "/page/common/foot.ftl">
	</body>
</html>