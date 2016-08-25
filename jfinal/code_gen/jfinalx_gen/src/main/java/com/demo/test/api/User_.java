package com.demo.test.api;

import java.util.Map;

import cn.peon.jfinal.core.TMapUtil;

/**
	user<br>
	<br>
	
	将表结构放在此，消除记忆负担
	以“_”结尾为了防止和实体混淆
 */
public class User_ {
	/**
	 * 表名称
	 */
	public final static String _table_name="user";
	
	/** 主键:*/
	public final static String id="id"; 
	/** 用户email */
	public final static String email="email";
	/** 帐号 */
	public final static String username="username";
	/** VIP会员状态(1 默认 非VIP 2 VIP 3 待续费VIP) */
	public final static String vipStatus="vipStatus";
	/** 认证步骤(默认是1  个人详细信息 2  工作认证 3申请vip 4绑定账号 5上传 资料) */
	public final static String authStep="authStep";
	/** 是否禁用 1.启用 2.禁用 3.黑名单 4.锁定 默认1 */
	public final static String enable="enable";
	/**  */
	public final static String password="password";
	/** 交易密码(初始密码为用户密码) */
	public final static String dealpwd="dealpwd";
	/** 用户移动电话 */
	public final static String mobilePhone="mobilePhone";
	/** 推荐人 */
	public final static String refferee="refferee";
	/** 网站积分 */
	public final static String rating="rating";
	/** 信用积分 */
	public final static String creditrating="creditrating";
	/** 最后登录IP */
	public final static String lastIP="lastIP";
	/** 最后登录时间 */
	public final static String lastDate="lastDate";
	/** VIP创建时间 */
	public final static String vipCreateTime="vipCreateTime";
	/** 信用额度 */
	public final static String creditLimit="creditLimit";
	/** 头像 */
	public final static String headImg="headImg";
	/** 帐号创建时间 */
	public final static String createTime="createTime";
	/** 用户vip申请会员时候填写的备注 */
	public final static String content="content";
	/** 可用金额 */
	public final static String usableSum="usableSum";
	/** 冻结金额 */
	public final static String freezeSum="freezeSum";
	/** 待收金额 */
	public final static String dueinSum="dueinSum";
	/** 待还金额 */
	public final static String dueoutSum="dueoutSum";
	/** 客服Id */
	public final static String kefuId="kefuId";
	/** 后台审核员id */
	public final static String adminId="adminId";
	/** 组ID */
	public final static String groupId="groupId";
	/** 可用信用额度 */
	public final static String usableCreditLimit="usableCreditLimit";
	/** 额度审核审核员id */
	public final static String creditlimtor="creditlimtor";
	/** vip会费 */
	public final static String vipFee="vipFee";
	/** 费用扣除状态( 1 待扣 2 已扣) */
	public final static String feeStatus="feeStatus";
	/** 登录次数 */
	public final static String loginCount="loginCount";
	/** 锁定时间 */
	public final static String lockTime="lockTime";
	/** 提现状态(默认1 启动 2 禁止) */
	public final static String cashStatus="cashStatus";
	/** 最大待收本金 */
	public final static String xmax="xmax";
	/** 系数 */
	public final static String x="x";
	/** 最小值 */
	public final static String xmin="xmin";
	/** 是否首次成为VIP(默认 1 是 2 否) */
	public final static String isFirstVip="isFirstVip";
	/** 1.普通会员 2.普通vip 3.白金vip 4.钻石vip */
	public final static String vipType="vipType";
	/** IPS账号 */
	public final static String portMerBillNo="portMerBillNo";
	/** 自动投标签约协议号 */
	public final static String pIpsBillNo="pIpsBillNo";
	/** 注册来源 */
	public final static String source="source";
	/** 返利余额 */
	public final static String rebateSum="rebateSum";
	/** 累计返利金额 */
	public final static String totalRebateSum="totalRebateSum";
	/** 推荐人数 */
	public final static String rebateCount="rebateCount";
	/** 知道渠道 */
	public final static String channel="channel";
	/** 理财计划冻结金额 */
	public final static String planDueinSum="planDueinSum";



	/** user表对应的数据过滤器*/
	public static Map<String, Class<?>> getBase() {
		Map<String, Class<?>> base = TMapUtil.getEmpty();
		
		base.put(id, java.lang.Long.class);
		base.put(email, java.lang.String.class);
		base.put(username, java.lang.String.class);
		base.put(vipStatus, java.lang.Integer.class);
		base.put(authStep, java.lang.Integer.class);
		base.put(enable, java.lang.Integer.class);
		base.put(password, java.lang.String.class);
		base.put(dealpwd, java.lang.String.class);
		base.put(mobilePhone, java.lang.String.class);
		base.put(refferee, java.lang.String.class);
		base.put(rating, java.lang.Integer.class);
		base.put(creditrating, java.lang.Integer.class);
		base.put(lastIP, java.lang.String.class);
		base.put(lastDate, java.sql.Timestamp.class);
		base.put(vipCreateTime, java.sql.Timestamp.class);
		base.put(creditLimit, java.lang.Long.class);
		base.put(headImg, java.lang.String.class);
		base.put(createTime, java.sql.Timestamp.class);
		base.put(content, java.lang.String.class);
		base.put(usableSum, java.math.BigDecimal.class);
		base.put(freezeSum, java.math.BigDecimal.class);
		base.put(dueinSum, java.math.BigDecimal.class);
		base.put(dueoutSum, java.math.BigDecimal.class);
		base.put(kefuId, java.lang.Long.class);
		base.put(adminId, java.lang.Long.class);
		base.put(groupId, java.lang.Long.class);
		base.put(usableCreditLimit, java.lang.Long.class);
		base.put(creditlimtor, java.lang.Long.class);
		base.put(vipFee, java.math.BigDecimal.class);
		base.put(feeStatus, java.lang.Long.class);
		base.put(loginCount, java.lang.Long.class);
		base.put(lockTime, java.sql.Timestamp.class);
		base.put(cashStatus, java.lang.Integer.class);
		base.put(xmax, java.math.BigDecimal.class);
		base.put(x, java.math.BigDecimal.class);
		base.put(xmin, java.math.BigDecimal.class);
		base.put(isFirstVip, java.lang.Integer.class);
		base.put(vipType, java.lang.Integer.class);
		base.put(portMerBillNo, java.lang.String.class);
		base.put(pIpsBillNo, java.lang.String.class);
		base.put(source, java.lang.String.class);
		base.put(rebateSum, java.math.BigDecimal.class);
		base.put(totalRebateSum, java.math.BigDecimal.class);
		base.put(rebateCount, java.lang.Integer.class);
		base.put(channel, java.lang.Integer.class);
		base.put(planDueinSum, java.math.BigDecimal.class);
		
		return base;
	}
}
