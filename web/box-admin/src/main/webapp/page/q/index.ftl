<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>q列表</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>

<script type="text/javascript">
jQuery(function(){
	<#if (VIEW_MSG_TYPE)?? >
	var op={msg:"${VIEW_MSG_MSG}"};
	peon.popx["${VIEW_MSG_TYPE}"](op);
	</#if>
});


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
	location="/q/deleteByIds?id="+(ids.join("&id="));
}
function checkAll(val){
	jQuery(".onecheck").attr("checked",val);
}
</script>


</head>

<#include "/page/common/_paginate_new.ftl" />
<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="javascript:top.location='/';">首页</a></li>
    <li><a href="#">q列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li onclick="location='/q/add'"><span><img src="/static/uimaker/images/t01.png" /></span>添加</li>
        <li onclick="confirm_del()"><span><img src="/static/uimaker/images/t03.png" /></span>删除</li>
        </ul>
        
        
        <ul class="toolbar1">
        <li><span><img src="/static/uimaker/images/t05.png" /></span>设置</li>
        </ul>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
    	<th ><input name="" type="checkbox" onclick="checkAll(this.checked,'')"/></th>
			<th title="">id</th>
			<th title="查询名称">n</th>
			<th title="关联数据库id">dbid</th>
			<th title="sql语句">sql</th>
			<th title="状态 0：默认启用 -1禁用">stat</th>
			<th title="类型 0：用户查询 1统计内容">type</th>
			<th title="最小执行间隔">file_path</th>
			<th title="产生静态统计文件地址">file_path</th>
			<th title="查询描述">de</th>
    	
        <th style="color:red">adminOP</th>
        </tr>
        </thead>
        <tbody>
        
        
        
       	<#list pg.getList() as q>
        <tr>
       		<td><input class="onecheck" type="checkbox" value="${q.id}" /></td>
       
			<td style="text-align:center;"><@withSub text="${q.id!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.n!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.dbid!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.sql!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.stat!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.type!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.ms!}分钟" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.file_path!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${q.de!}" len=8 /></td>
       
       
       		<td><a href="/qo/addop/${q.id}" class="tablelink">添加条件字段</a>&nbsp;<a href="/q/edit/${q.id}" class="tablelink">查看</a>&nbsp;<a href="/q/delete/${q.id}" class="tablelink"> 删除</a></td>
        </tr> 
		</#list>
          
          
            
        </tbody>
    </table>
    
   
	<@paginate currentPage=pg.pageNumber totalPage=pg.totalPage totalRow=pg.totalRow actionUrl="/q/" />
   
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100'>暂无数据</td></tr>")}
	</script>

</body>

</html>
