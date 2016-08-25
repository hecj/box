<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>t_user列表</title>
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
	location="/t_user/deleteByIds?id="+(ids.join("&id="));
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
    <li><a href="#">t_user列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li onclick="location='/t_user/add'"><span><img src="/static/uimaker/images/t01.png" /></span>添加</li>
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
			<th title="用户ID">id</th>
			<th title="用户email">email</th>
			<th title="帐号">username</th>
			<th title="VIP会员状态(1 默认 非VIP 2 VIP 3 待续费VIP)">vipStatus</th>
			<th title="认证步骤(默认是1  个人详细信息 2  工作认证 3申请vip 4绑定账号 5上传 资料)">authStep</th>
			<th title="是否禁用 1.启用 2.禁用 3.黑名单 4.锁定 默认1">enable</th>
			<th title="用户密码">password</th>
			<th title="交易密码(初始密码为用户密码)">dealpwd</th>
			<th title="用户移动电话">mobilePhone</th>
			<th title="推荐人">refferee</th>
			<th title="网站积分">rating</th>
			<th title="信用积分">creditrating</th>
			<th title="最后登录IP">lastIP</th>
			<th title="最后登录时间">lastDate</th>
			<th title="VIP创建时间">vipCreateTime</th>
			<th title="信用额度">creditLimit</th>
			<th title="头像">headImg</th>
			<th title="帐号创建时间">createTime</th>
			<th title="用户vip申请会员时候填写的备注">content</th>
			<th title="可用金额">usableSum</th>
			<th title="冻结金额">freezeSum</th>
			<th title="待收金额">dueinSum</th>
			<th title="待还金额">dueoutSum</th>
			<th title="客服Id">kefuId</th>
			<th title="后台审核员id">adminId</th>
			<th title="组ID">groupId</th>
			<th title="可用信用额度">usableCreditLimit</th>
			<th title="额度审核审核员id">creditlimtor</th>
			<th title="vip会费">vipFee</th>
			<th title="费用扣除状态( 1 待扣 2 已扣)">feeStatus</th>
			<th title="登录次数">loginCount</th>
			<th title="锁定时间">lockTime</th>
			<th title="提现状态(默认1 启动 2 禁止)">cashStatus</th>
			<th title="最大待收本金">xmax</th>
			<th title="系数">x</th>
			<th title="最小值">xmin</th>
			<th title="是否首次成为VIP(默认 1 是 2 否)">isFirstVip</th>
			<th title="1.普通会员 2.普通vip 3.白金vip 4.钻石vip">vipType</th>
			<th title="IPS账号">portMerBillNo</th>
			<th title="自动投标签约协议号">pIpsBillNo</th>
			<th title="注册来源">source</th>
			<th title="返利余额">rebateSum</th>
			<th title="累计返利金额">totalRebateSum</th>
			<th title="推荐人数">rebateCount</th>
			<th title="知道渠道">channel</th>
			<th title="理财计划冻结金额">planDueinSum</th>
    	
        <th style="color:red">adminOP</th>
        </tr>
        </thead>
        <tbody>
        
        
        
       	<#list pg.getList() as t_user>
        <tr>
       		<td><input class="onecheck" type="checkbox" value="${t_user.id}" /></td>
       
			<td style="text-align:center;"><@withSub text="${t_user.id!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.email!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.username!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.vipStatus!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.authStep!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.enable!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.password!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.dealpwd!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.mobilePhone!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.refferee!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.rating!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.creditrating!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.lastIP!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.lastDate!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.vipCreateTime!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.creditLimit!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.headImg!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.createTime!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.content!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.usableSum!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.freezeSum!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.dueinSum!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.dueoutSum!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.kefuId!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.adminId!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.groupId!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.usableCreditLimit!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.creditlimtor!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.vipFee!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.feeStatus!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.loginCount!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.lockTime!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.cashStatus!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.xmax!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.x!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.xmin!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.isFirstVip!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.vipType!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.portMerBillNo!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.pIpsBillNo!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.source!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.rebateSum!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.totalRebateSum!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.rebateCount!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.channel!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${t_user.planDueinSum!}" len=8 /></td>
       
       
       		<td><a href="/t_user/edit/${t_user.id}" class="tablelink">查看</a>&nbsp;<a href="/t_user/delete/${t_user.id}" class="tablelink"> 删除</a></td>
        </tr> 
		</#list>
          
          
            
        </tbody>
    </table>
    
   
	<@paginate currentPage=pg.pageNumber totalPage=pg.totalPage totalRow=pg.totalRow actionUrl="/t_user/" />
   
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100'>暂无数据</td></tr>")}
	</script>

</body>

</html>
