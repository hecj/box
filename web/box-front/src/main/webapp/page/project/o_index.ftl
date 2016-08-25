<!DOCTYPE html>
<html>
	<head>
	
	<style>
		li {list-style-type:none;}	
	</style>

		<#--<#include "/page/common/base/include.ftl">-->
		<script type="text/javascript">
		
			var currURL="/project?";
		
			function loadCategory(id){
				currURL = currURL + "&category="+id;
				location = currURL;
			}
			
			function loadStatus(id){
				currURL = currURL + "&status="+id;
				location = currURL;
			}
			
		</script>
	</head>
	<body style="margin-left:50px">
		<#include "/page/common/head.ftl">
		<br/><br/>
		<div>
			<div>
				<label>行业选择：</label>
				<a href="#" name="type" onclick="loadCategory(-1)">全部</a>
				<#list projectTypeList as type>	
					<a href="#" name="type" onclick="loadCategory(${type.id!})">${type.name!}</a>		
				</#list>	
			</div>
			<div>
				<label>项目进程：</label>
				<a href="#" onclick="loadStatus(-1)">所有项目</a>
				<a href="#" name="status" onclick="loadStatus(7)">预热</a>		
				<a href="#" name="status" onclick="loadStatus(8)">众筹中</a>		
				<a href="#" name="status" onclick="loadStatus(10)">已成功</a>		
			</div>
		</div>
			<#if recommList??>
			<hr>
			<div>
				推荐的项目：
				<#list recommList! as p>
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
					</tr>
				</#list>
			</div>
			</#if>
		<hr>
		<div>
			项目列表：
			<div>
				<#list projectList! as p>
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
		</div>
		<br/><br/>	
		<#include "/page/common/foot.ftl">
	</body>
</html>