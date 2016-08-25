<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>tbc列表</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>
<script type="text/javascript" src="/static/uimaker/js/jquery.continuousCalendar-4.11.0-min.js"></script>

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
	location="/tbc/deleteByIds?id="+(ids.join("&id="));
}
function checkAll(val){
	jQuery(".onecheck").attr("checked",val);
}


<!--
 function selectByOption(){
	<!-- 获取所有标签为“input”的属性值 -->
	var info = $(":input");
	
	var uInfo = info[0].value;
	var ftime = info[1].value;
	var ltime = info[2].value;
	
	//window.alert("uInfo");
	var rurl="/selectInfoUser";
	//?uInfo="+uInfo+"&ftime="+ftime+"&ltime="+ltime;
	//alert(rurl);
	location.href=rurl;
	
}
-->
</script>
<!--<script type="text/javascript">
	$(document).ready(function(){
		jQuery(".datepicker").datepicker();
	});
</script>
-->

</head>

<#include "/page/common/_paginate_new.ftl" />
<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="javascript:top.location='/';">首页</a></li>
    <li><a href="#">用户日志</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<!--<ul class="toolbar">   </ul>   -->
     
        	<form name="frmInfoUser" action="/olu/selectInfoUser">
        		<table name="tabInfoUser" id="tabInfoUser">
		        	用户id：<input type="text" name="uInfo" id="uInfo" style="width:50px;height:30px" />
		        	起始时间：<input type="text" name="firstTime" id="firstTime" class="datepicker"/>
		        	终止时间：<input type="text" name="lastTime" id="lastTime"/>
		        	<input type="submit" value="确认" />
  				</table> 
        	</form>
        	
        
       
        
        <ul class="toolbar1">
        <li><span><></span>导出</li>
        </ul>
    
    </div>
    
    <div>
    <table class="tablelist">
    	<thead>
    	<tr>
			<th >时间</th>
			<th >操作人</th>
			<th >操作名称</th>
			<th >操作模块</th>
			<th >操作内容</th>
			<th >最后登录时间</th>
    	
        </tr>
        </thead>
        <tbody>
        
        
        
       	<#list pg.getList() as o>
        <tr>
       
			<td style="text-align:center;"> ${o.tm!} </td>
			<td style="text-align:center;"><@withSub text="${o.oids!}" len=8 /></td>
			<td style="text-align:center;"><span command="ts">${o.type!}</span></td>
			<td style="text-align:center;"><@withSub text="${o.mn!}" len=8 /></td>
			<td style="text-align:center;">  ${o.oids!} </td>
			<td style="text-align:center;"> ${o.endtm!} </td>
       
       
        </tr> 
		</#list>
          
          
            
        </tbody>
    </table>
    
   
	<@paginate currentPage=pg.pageNumber totalPage=pg.totalPage totalRow=pg.totalRow actionUrl="/olu/"/>
   
    </div>
    
    <script type="text/javascript">
    var ts={"dologin":"登录","logout":"登出","ex":"执行查询","staticex":"执行统计","save":"添加","delete":"删除","deleteByIds":"批量删除","update":"修改"};
    
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100'>暂无数据</td></tr>")}
	</script>

</body>

</html>
