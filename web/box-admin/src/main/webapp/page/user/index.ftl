<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
    <link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="/static/css/common.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
    <script type="text/javascript" src="/static/uimaker/js/jquery.idTabs.min.js"></script>
    <script type="text/javascript" src="/static/uimaker/js/select-ui.min.js"></script>
    <script type="text/javascript" src="/static/uimaker/editor/kindeditor.js"></script>
    <script type="text/javascript" src="/static/js/common.js"></script>
<script type="text/javascript">
jQuery(function(){
	<#if (VIEW_MSG_TYPE)?? >
	var op={msg:"${VIEW_MSG_MSG}"};
	peon.popx["${VIEW_MSG_TYPE}"](op);
	</#if>
});

	// 查询
	function queryUserFun(){
		var url = "/user?";
 		var uPhone = jQuery("input[name=uPhone]").val();
 		if(uPhone.length > 0){
			url = url+"&uPhone="+jQuery.trim(uPhone);
 		}
 		var email = jQuery("input[name=email]").val();
 		if(email.length > 0){
			url = url+"&email="+jQuery.trim(email);
 		}
 		var certify = jQuery("select[name=certify]").val();
 		if(certify != -1){
 			url = url+"&certify="+certify;
 		}
 		location= url;
	}


function confirm_reset(username){
	var id=[];
	var checkId=jQuery(".onecheck:checked");
	
	checkId.each(function(){
		id.push(this.value);
	});
	if(username==id[0]){
	
		var str1=' <p>是否确认要重置当前用户的密码 ？</p>'+
			' <cite>如果是请点击确定按钮 ，否则请点取消。</cite>';
		var opt={
			title:"操作提示",
			msg:str1,
			confirm:function(v){		
				v&&resetUserPwd(id[0]);
				}
			};
		peon.popx.confirm(opt);
	}else{
		peon.popx.warn({msg:"操作失误,请核实！"});
		return;
	}
}
</script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="javascript:top.location='/';">首页</a></li>
    <li><a href="#">用户列表</a></li>
    </ul>
    </div>
    <div class="rightinfo">
    <div class="tools">
    	<ul class="toolbar">
    			手机号：<input type="text" name="uPhone" value="${uPhone!}" style="width:150px;" class="dfinput"/>
    			&nbsp;&nbsp;
    			邮箱：<input type="text" name="email" value="${email!}" style="width:150px;" class="dfinput"/>
    			&nbsp;&nbsp;
    			认证状态：
    			<select name="certify" class="dfinput" style="width:100px;" >
    				<option value="-1" >--请选择--</option>
    				<#if certify ?? && certify == "0">
        				<option value="0" selected>未认证</option>
        			<#else>
        				<option value="0">未认证</option>
        			</#if>
        			
        			<#if certify ?? && certify == "1">
        				<option value="1" selected>申请中</option>
        			<#else>
        				<option value="1">申请中</option>
        			</#if>
        			
        			<#if certify ?? && certify == "2">
        				<option value="2" selected>认证通过</option>
        			<#else>
        				<option value="2">认证通过</option>
        			</#if>
    			</select>
    			&nbsp;&nbsp;
        		<button class="btn" onclick="queryUserFun()">查询</button>
        </ul>
    </div>
    <div>
    <table class="tablelist">
    	<thead>
    	<tr>
			<th title="id">id</th>
			<th title="昵称">昵称</th>
			<th title="手机号">手机号</th>
			<th title="邮箱" />邮箱</th>
			<th title="性别">性别</th>
			<th title="真实姓名">真实姓名</th>
			<th title="身份证">身份证</th>
			<th title="余额">余额</th>
			<th title="认证状态">认证状态</th>
			<th title="注册时间">注册时间</th>
        	<th style="color:red">操作</th>
        </tr>
        </thead>
        <tbody>
	       	<#list recordList.list as u>
	        <tr>
				<td >${u.id!} </td>
				<td >${u.nickname!}</td>
				<td >${u.phone!}</td>
				<td >${u.email!}</td>
				<td >
					<#if u.sex ?? && u.sex = 1>男</#if>
					<#if u.sex ?? && u.sex = 2>女</#if>
				</td>
				<td >${u.realname!}</td>
				<td >${u.idno!}</td>
				<td >${u.balance!}</td>
				<td >
					<#if u.certify ?? && u.certify = 0><font color="red">未认证</font></#if>
					<#if u.certify ?? && u.certify = 1>申请中</#if>
					<#if u.certify ?? && u.certify = 2>认证通过</#if>
				</td>
				<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
				<td >
					<#if u.create_at ??>
						${u.create_at?number_to_datetime}
					</#if>
				</td>
	       		<td>
	       			<a href="/user/detail/${u.id!}" class="tablelink">查看</a>&nbsp;
	       		</td>
	        </tr> 
			</#list>
        </tbody>
    </table>
   	<#include "/page/common/_paginate_new.ftl" />
	<@paginate currentPage=recordList.pageNumber totalPage=recordList.totalPage totalRow=recordList.totalRow actionUrl="/user/" urlParas="?uPhone=${uPhone!}&email=${email!}&certify=${certify!}"/>
</div>
 
<script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
</script>

</body>
</html>
