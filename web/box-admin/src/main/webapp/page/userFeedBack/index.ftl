<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    	<title>用户反馈信息</title>
    	<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
		<script type="text/javascript" src="/static/js/common.js"></script>
	</head>
		<#include "/page/common/_paginate_new.ftl" />
	<body>
		<div class="place">
		    <span>位置：</span>
		    <ul class="placeul">
		        <li><a href="javascript:top.location='/';">首页</a></li>
		        <li><a href="/userFeedBack">用户反馈信息</a></li>
		    </ul>
		</div>
		<div class="rightinfo">
			<div class="tools">
		    	<ul class="toolbar">
			        <li onclick="exportAll()"><span><img src="/static/uimaker/images/t01.png" /></span>导出</li>
			        <li onclick="confirm_del()"><span><img src="/static/uimaker/images/t03.png" /></span>删除</li>
		        </ul>
	    	</div>
	    
		
			<table class="tablelist">
		    	<thead>
			    	<tr>
			    		<th><input name="" type="checkbox" onclick="checkAll(this.checked,'')"/></th>
						<th title="id" style="text-align:center;">id</th>
						<th title="用户id" style="text-align:center;">用户id</th>
						<th title="用户昵称" style="text-align:center;">用户昵称</th>
						<th title="反馈信息" style="text-align:center;">反馈信息</th>
						<th title="联系方式" style="text-align:center;">联系方式</th>
						<th title="反馈时间" style="text-align:center;">反馈时间</th>
			        	<th style="color:red">操作</th>
			        </tr>
		        </thead>
		        <tbody>
		        	<#setting datetime_format="yyyy-MM-dd  HH:mm:ss"/>
			       	<#list userFeedBackList.getList() as userfeedback>
			        <tr>
			       		<td><input class="onecheck" type="checkbox" value="${userfeedback.id!}" /></td>
			       		<td style="text-align:center;">${userfeedback.id!}</td>
						<td style="text-align:center;">${userfeedback.user_id!}</td>
						<td style="text-align:center;">${userfeedback.nickname!}</td>
						<td style="text-align:center;">${userfeedback.content!}</td>
						<td style="text-align:center;"><span command="rl" >${userfeedback.remark!}</span></td>
						<td style="text-align:center;">${(userfeedback.create_at?number_to_datetime)!}</td>
			       		<td><a href="/userFeedBack/detail/${userfeedback.id!}" class="tablelink">查看</a>&nbsp;
			       			<a href="javascript:;" onclick="confirm_delete(${userfeedback.id!})" class="tablelink"> 删除</a>
			       		</td>
			        </tr> 
					</#list>
	        	</tbody>
	   	   </table>
	   	   <@paginate currentPage=userFeedBackList.pageNumber totalPage=userFeedBackList.totalPage totalRow=userFeedBackList.totalRow actionUrl="/userFeedBack/" />
	   	</div>
	   	<script type="text/javascript">
			$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100'>暂无数据</td></tr>")};
			
			jQuery(function(){
				<#if (VIEW_MSG_TYPE)?? >
				var op={msg:"${VIEW_MSG_MSG}"};
				peon.popx["${VIEW_MSG_TYPE}"](op);
				</#if>
			});
			
			function confirm_delete(val){
				if(val==""){
					peon.popx.warn({msg:"请选择要删除的项！"});
					return;
				}
				
				var str='        <p>是否确认要删除当前选中的记录 ？</p>'+
						'        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>';
				var op={
					title:"操作提示",
					msg:str,
					confirm:function(v){v&&deleteSingle(val);}
				};
				peon.popx.confirm(op);
			}
			
			function exportAll(){
				var ids=[];
				var checkIds=jQuery(".onecheck:checked");
				if(checkIds.length==0){
					peon.popx.warn({msg:"请选择要导出的反馈信息！"});
					return;
				}
				checkIds.each(function(){
					ids.push(this.value);
				});
				var str='        <p>是否确认要导出当前选中的记录 ？</p>'+
						'        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>';
				var op={
					title:"操作提示",
					msg:str,
					confirm:function(v){v&&export_checked(ids);}
				};
				peon.popx.confirm(op);
			}

			function confirm_del(){
				var ids=[];
				var checkIds=jQuery(".onecheck:checked");
				if(checkIds.length==0){
					peon.popx.warn({msg:"请选择要删除的项！"});
					return;
				}
				checkIds.each(function(){
					ids.push(this.value);
				});
				var str='        <p>是否确认要删除当前选中的记录 ？</p>'+
						'        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>';
				var op={
					title:"操作提示",
					msg:str,
					confirm:function(v){v&&delByIds(ids);}
				};
				peon.popx.confirm(op);
			}
			
			function delByIds(ids){
				location="/userFeedBack/deleteByIds?id="+(ids.join("&id="));
			}
			
			function deleteSingle(val){
				location="/userFeedBack/delete?id="+val;
			}
			
			function checkAll(val){
				jQuery(".onecheck").attr("checked",val);
			}
			
			function export_checked(ids){
				location="/userFeedBack/export?id="+(ids.join("&id="));
			}
			
			
		</script>
	   	
	</body>
</html>