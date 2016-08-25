<#include "../common/_layout.ftl"/>
<@layout>
<h3>t_user列表
</h3>
<a href="/t_user/ajax_t_user_list">t_user ajax列表</a>
<a href="/t_user/add">新增t_user</a>
<div class="table_box">
	<table class="list">
		<tbody>
			<tr>
				<th width="10%">id(用户ID)</th>
				<th width="10%">email(用户email)</th>
				<th width="10%">username(帐号)</th>
				<th width="10%">vipStatus(VIP会员状态(1 默认 非VIP 2 VIP 3 待续费VIP))</th>
				<th width="10%">authStep(认证步骤(默认是1  个人详细信息 2  工作认证 3申请vip 4绑定账号 5上传 资料))</th>
				<th width="10%">enable(是否禁用 1.启用 2.禁用 3.黑名单 4.锁定 默认1)</th>
				<th width="10%">password(用户密码)</th>
				<th width="10%">dealpwd(交易密码(初始密码为用户密码))</th>
				<th width="10%">mobilePhone(用户移动电话)</th>
				<th width="10%">refferee(推荐人)</th>
				<th width="10%">rating(网站积分)</th>
				<th width="10%">creditrating(信用积分)</th>
				<th width="10%">lastIP(最后登录IP)</th>
				<th width="10%">lastDate(最后登录时间)</th>
				<th width="10%">vipCreateTime(VIP创建时间)</th>
				<th width="10%">creditLimit(信用额度)</th>
				<th width="10%">headImg(头像)</th>
				<th width="10%">createTime(帐号创建时间)</th>
				<th width="10%">content(用户vip申请会员时候填写的备注)</th>
				<th width="10%">usableSum(可用金额)</th>
				<th width="10%">freezeSum(冻结金额)</th>
				<th width="10%">dueinSum(待收金额)</th>
				<th width="10%">dueoutSum(待还金额)</th>
				<th width="10%">kefuId(客服Id)</th>
				<th width="10%">adminId(后台审核员id)</th>
				<th width="10%">groupId(组ID)</th>
				<th width="10%">usableCreditLimit(可用信用额度)</th>
				<th width="10%">creditlimtor(额度审核审核员id)</th>
				<th width="10%">vipFee(vip会费)</th>
				<th width="10%">feeStatus(费用扣除状态( 1 待扣 2 已扣))</th>
				<th width="10%">loginCount(登录次数)</th>
				<th width="10%">lockTime(锁定时间)</th>
				<th width="10%">cashStatus(提现状态(默认1 启动 2 禁止))</th>
				<th width="10%">xmax(最大待收本金)</th>
				<th width="10%">x(系数)</th>
				<th width="10%">xmin(最小值)</th>
				<th width="10%">isFirstVip(是否首次成为VIP(默认 1 是 2 否))</th>
				<th width="10%">vipType(1.普通会员 2.普通vip 3.白金vip 4.钻石vip)</th>
				<th width="10%">portMerBillNo(IPS账号)</th>
				<th width="10%">pIpsBillNo(自动投标签约协议号)</th>
				<th width="10%">source(注册来源)</th>
				<th width="10%">rebateSum(返利余额)</th>
				<th width="10%">totalRebateSum(累计返利金额)</th>
				<th width="10%">rebateCount(推荐人数)</th>
				<th width="10%">channel(知道渠道)</th>
				<th width="10%">planDueinSum(理财计划冻结金额)</th>
			 
				<th width="12%">操作</th>
			</tr>
			
			<#list t_user_page.getList() as x>
			<tr>
					<td style="text-align:left;">${x.id}</td>
					<td style="text-align:left;">${x.email}</td>
					<td style="text-align:left;">${x.username}</td>
					<td style="text-align:left;">${x.vipStatus}</td>
					<td style="text-align:left;">${x.authStep}</td>
					<td style="text-align:left;">${x.enable}</td>
					<td style="text-align:left;">${x.password}</td>
					<td style="text-align:left;">${x.dealpwd}</td>
					<td style="text-align:left;">${x.mobilePhone}</td>
					<td style="text-align:left;">${x.refferee}</td>
					<td style="text-align:left;">${x.rating}</td>
					<td style="text-align:left;">${x.creditrating}</td>
					<td style="text-align:left;">${x.lastIP}</td>
					<td style="text-align:left;">${x.lastDate}</td>
					<td style="text-align:left;">${x.vipCreateTime}</td>
					<td style="text-align:left;">${x.creditLimit}</td>
					<td style="text-align:left;">${x.headImg}</td>
					<td style="text-align:left;">${x.createTime}</td>
					<td style="text-align:left;">${x.content}</td>
					<td style="text-align:left;">${x.usableSum}</td>
					<td style="text-align:left;">${x.freezeSum}</td>
					<td style="text-align:left;">${x.dueinSum}</td>
					<td style="text-align:left;">${x.dueoutSum}</td>
					<td style="text-align:left;">${x.kefuId}</td>
					<td style="text-align:left;">${x.adminId}</td>
					<td style="text-align:left;">${x.groupId}</td>
					<td style="text-align:left;">${x.usableCreditLimit}</td>
					<td style="text-align:left;">${x.creditlimtor}</td>
					<td style="text-align:left;">${x.vipFee}</td>
					<td style="text-align:left;">${x.feeStatus}</td>
					<td style="text-align:left;">${x.loginCount}</td>
					<td style="text-align:left;">${x.lockTime}</td>
					<td style="text-align:left;">${x.cashStatus}</td>
					<td style="text-align:left;">${x.xmax}</td>
					<td style="text-align:left;">${x.x}</td>
					<td style="text-align:left;">${x.xmin}</td>
					<td style="text-align:left;">${x.isFirstVip}</td>
					<td style="text-align:left;">${x.vipType}</td>
					<td style="text-align:left;">${x.portMerBillNo}</td>
					<td style="text-align:left;">${x.pIpsBillNo}</td>
					<td style="text-align:left;">${x.source}</td>
					<td style="text-align:left;">${x.rebateSum}</td>
					<td style="text-align:left;">${x.totalRebateSum}</td>
					<td style="text-align:left;">${x.rebateCount}</td>
					<td style="text-align:left;">${x.channel}</td>
					<td style="text-align:left;">${x.planDueinSum}</td>
				<td style="text-align:left;">
					&nbsp;&nbsp;<a href="/t_user/delete/${x.id}" onclick="return confirm('您正在执行删除操作，是否继续？')">删除</a>
					&nbsp;&nbsp;<a href="/t_user/edit/${x.id}">修改</a>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#include "../common/_paginate.ftl" />
	<@paginate currentPage=t_user_page.pageNumber totalPage=t_user_page.totalPage actionUrl="/t_user/" />
</div>
</@layout>