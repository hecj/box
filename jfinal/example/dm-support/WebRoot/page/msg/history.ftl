<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>msg列表</title>
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
	location="/msg/deleteByIds?id="+(ids.join("&id="));
}
function checkAll(val){
	jQuery(".onecheck").attr("checked",val);
}
</script>


</head>

<#include "/page/common/_paginate_new.ftl" />
<body>

	
    <div class="place">
    <span>消息历史:</span>
    
    </div>
    
    <div class="rightinfo">
    
 
    
    <table class="tablelist">
    	<thead>
    	<tr>
			<th title="">id</th>
			<th title="发送者姓名">发送人</th>
			<th title="内容">内容</th>
			<th title="发送时间">发送时间</th>
        </tr>
        </thead>
        <tbody>
        
        
        
       	<#list pg.getList() as msg>
        <tr>
       
			<td style="text-align:center;"><@withSub text="${msg.id!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${msg.fu!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${msg.msg!}" len=28 /></td>
			<td style="text-align:center;"> ${msg.ft!} </td>
       
       
        </tr> 
		</#list>
          
            
        </tbody>
    </table>
    
   
	<@paginate currentPage=pg.pageNumber totalPage=pg.totalPage totalRow=pg.totalRow actionUrl="/msg/history?frid=${frid}&page=" />
   
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100'>暂无数据</td></tr>")}
	</script>

</body>

</html>
