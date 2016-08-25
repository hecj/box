	
		<input type="hidden" name="t_user.id" value="${(t_user.id)!}" />

		<div>
			<span class="form_title">email</span>
			<input class="dfinput"   name="t_user.email" value="${(t_user.email)!}" />
			<i>
				<#if (email_msg)??>
					<font color="red" class="tip_error">${email_msg!}</font>
				<#else>
					(用户email)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">username</span>
			<input class="dfinput"   name="t_user.username" value="${(t_user.username)!}" />
			<i>
				<#if (username_msg)??>
					<font color="red" class="tip_error">${username_msg!}</font>
				<#else>
					(帐号)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">vipStatus</span>
			<input class="dfinput"   name="t_user.vipStatus" value="${(t_user.vipStatus)!}" />
			<i>
				<#if (vipStatus_msg)??>
					<font color="red" class="tip_error">${vipStatus_msg!}</font>
				<#else>
					(VIP会员状态(1 默认 非VIP 2 VIP 3 待续费VIP))
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">authStep</span>
			<input class="dfinput"   name="t_user.authStep" value="${(t_user.authStep)!}" />
			<i>
				<#if (authStep_msg)??>
					<font color="red" class="tip_error">${authStep_msg!}</font>
				<#else>
					(认证步骤(默认是1  个人详细信息 2  工作认证 3申请vip 4绑定账号 5上传 资料))
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">enable</span>
			<input class="dfinput"   name="t_user.enable" value="${(t_user.enable)!}" />
			<i>
				<#if (enable_msg)??>
					<font color="red" class="tip_error">${enable_msg!}</font>
				<#else>
					(是否禁用 1.启用 2.禁用 3.黑名单 4.锁定 默认1)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">password</span>
			<input class="dfinput"   name="t_user.password" value="${(t_user.password)!}" />
			<i>
				<#if (password_msg)??>
					<font color="red" class="tip_error">${password_msg!}</font>
				<#else>
					(用户密码)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">dealpwd</span>
			<input class="dfinput"   name="t_user.dealpwd" value="${(t_user.dealpwd)!}" />
			<i>
				<#if (dealpwd_msg)??>
					<font color="red" class="tip_error">${dealpwd_msg!}</font>
				<#else>
					(交易密码(初始密码为用户密码))
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">mobilePhone</span>
			<input class="dfinput"   name="t_user.mobilePhone" value="${(t_user.mobilePhone)!}" />
			<i>
				<#if (mobilePhone_msg)??>
					<font color="red" class="tip_error">${mobilePhone_msg!}</font>
				<#else>
					(用户移动电话)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">refferee</span>
			<input class="dfinput"   name="t_user.refferee" value="${(t_user.refferee)!}" />
			<i>
				<#if (refferee_msg)??>
					<font color="red" class="tip_error">${refferee_msg!}</font>
				<#else>
					(推荐人)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">rating</span>
			<input class="dfinput"   name="t_user.rating" value="${(t_user.rating)!}" />
			<i>
				<#if (rating_msg)??>
					<font color="red" class="tip_error">${rating_msg!}</font>
				<#else>
					(网站积分)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">creditrating</span>
			<input class="dfinput"   name="t_user.creditrating" value="${(t_user.creditrating)!}" />
			<i>
				<#if (creditrating_msg)??>
					<font color="red" class="tip_error">${creditrating_msg!}</font>
				<#else>
					(信用积分)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">lastIP</span>
			<input class="dfinput"   name="t_user.lastIP" value="${(t_user.lastIP)!}" />
			<i>
				<#if (lastIP_msg)??>
					<font color="red" class="tip_error">${lastIP_msg!}</font>
				<#else>
					(最后登录IP)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">lastDate</span>
			<input class="dfinput"   name="t_user.lastDate" value="${(t_user.lastDate)!}" />
			<i>
				<#if (lastDate_msg)??>
					<font color="red" class="tip_error">${lastDate_msg!}</font>
				<#else>
					(最后登录时间)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">vipCreateTime</span>
			<input class="dfinput"   name="t_user.vipCreateTime" value="${(t_user.vipCreateTime)!}" />
			<i>
				<#if (vipCreateTime_msg)??>
					<font color="red" class="tip_error">${vipCreateTime_msg!}</font>
				<#else>
					(VIP创建时间)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">creditLimit</span>
			<input class="dfinput"   name="t_user.creditLimit" value="${(t_user.creditLimit)!}" />
			<i>
				<#if (creditLimit_msg)??>
					<font color="red" class="tip_error">${creditLimit_msg!}</font>
				<#else>
					(信用额度)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">headImg</span>
			<input class="dfinput"   name="t_user.headImg" value="${(t_user.headImg)!}" />
			<i>
				<#if (headImg_msg)??>
					<font color="red" class="tip_error">${headImg_msg!}</font>
				<#else>
					(头像)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">createTime</span>
			<input class="dfinput"   name="t_user.createTime" value="${(t_user.createTime)!}" />
			<i>
				<#if (createTime_msg)??>
					<font color="red" class="tip_error">${createTime_msg!}</font>
				<#else>
					(帐号创建时间)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">content</span>
			<input class="dfinput"   name="t_user.content" value="${(t_user.content)!}" />
			<i>
				<#if (content_msg)??>
					<font color="red" class="tip_error">${content_msg!}</font>
				<#else>
					(用户vip申请会员时候填写的备注)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">usableSum</span>
			<input class="dfinput"   name="t_user.usableSum" value="${(t_user.usableSum)!}" />
			<i>
				<#if (usableSum_msg)??>
					<font color="red" class="tip_error">${usableSum_msg!}</font>
				<#else>
					(可用金额)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">freezeSum</span>
			<input class="dfinput"   name="t_user.freezeSum" value="${(t_user.freezeSum)!}" />
			<i>
				<#if (freezeSum_msg)??>
					<font color="red" class="tip_error">${freezeSum_msg!}</font>
				<#else>
					(冻结金额)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">dueinSum</span>
			<input class="dfinput"   name="t_user.dueinSum" value="${(t_user.dueinSum)!}" />
			<i>
				<#if (dueinSum_msg)??>
					<font color="red" class="tip_error">${dueinSum_msg!}</font>
				<#else>
					(待收金额)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">dueoutSum</span>
			<input class="dfinput"   name="t_user.dueoutSum" value="${(t_user.dueoutSum)!}" />
			<i>
				<#if (dueoutSum_msg)??>
					<font color="red" class="tip_error">${dueoutSum_msg!}</font>
				<#else>
					(待还金额)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">kefuId</span>
			<input class="dfinput"   name="t_user.kefuId" value="${(t_user.kefuId)!}" />
			<i>
				<#if (kefuId_msg)??>
					<font color="red" class="tip_error">${kefuId_msg!}</font>
				<#else>
					(客服Id)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">adminId</span>
			<input class="dfinput"   name="t_user.adminId" value="${(t_user.adminId)!}" />
			<i>
				<#if (adminId_msg)??>
					<font color="red" class="tip_error">${adminId_msg!}</font>
				<#else>
					(后台审核员id)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">groupId</span>
			<input class="dfinput"   name="t_user.groupId" value="${(t_user.groupId)!}" />
			<i>
				<#if (groupId_msg)??>
					<font color="red" class="tip_error">${groupId_msg!}</font>
				<#else>
					(组ID)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">usableCreditLimit</span>
			<input class="dfinput"   name="t_user.usableCreditLimit" value="${(t_user.usableCreditLimit)!}" />
			<i>
				<#if (usableCreditLimit_msg)??>
					<font color="red" class="tip_error">${usableCreditLimit_msg!}</font>
				<#else>
					(可用信用额度)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">creditlimtor</span>
			<input class="dfinput"   name="t_user.creditlimtor" value="${(t_user.creditlimtor)!}" />
			<i>
				<#if (creditlimtor_msg)??>
					<font color="red" class="tip_error">${creditlimtor_msg!}</font>
				<#else>
					(额度审核审核员id)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">vipFee</span>
			<input class="dfinput"   name="t_user.vipFee" value="${(t_user.vipFee)!}" />
			<i>
				<#if (vipFee_msg)??>
					<font color="red" class="tip_error">${vipFee_msg!}</font>
				<#else>
					(vip会费)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">feeStatus</span>
			<input class="dfinput"   name="t_user.feeStatus" value="${(t_user.feeStatus)!}" />
			<i>
				<#if (feeStatus_msg)??>
					<font color="red" class="tip_error">${feeStatus_msg!}</font>
				<#else>
					(费用扣除状态( 1 待扣 2 已扣))
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">loginCount</span>
			<input class="dfinput"   name="t_user.loginCount" value="${(t_user.loginCount)!}" />
			<i>
				<#if (loginCount_msg)??>
					<font color="red" class="tip_error">${loginCount_msg!}</font>
				<#else>
					(登录次数)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">lockTime</span>
			<input class="dfinput"   name="t_user.lockTime" value="${(t_user.lockTime)!}" />
			<i>
				<#if (lockTime_msg)??>
					<font color="red" class="tip_error">${lockTime_msg!}</font>
				<#else>
					(锁定时间)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">cashStatus</span>
			<input class="dfinput"   name="t_user.cashStatus" value="${(t_user.cashStatus)!}" />
			<i>
				<#if (cashStatus_msg)??>
					<font color="red" class="tip_error">${cashStatus_msg!}</font>
				<#else>
					(提现状态(默认1 启动 2 禁止))
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">xmax</span>
			<input class="dfinput"   name="t_user.xmax" value="${(t_user.xmax)!}" />
			<i>
				<#if (xmax_msg)??>
					<font color="red" class="tip_error">${xmax_msg!}</font>
				<#else>
					(最大待收本金)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">x</span>
			<input class="dfinput"   name="t_user.x" value="${(t_user.x)!}" />
			<i>
				<#if (x_msg)??>
					<font color="red" class="tip_error">${x_msg!}</font>
				<#else>
					(系数)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">xmin</span>
			<input class="dfinput"   name="t_user.xmin" value="${(t_user.xmin)!}" />
			<i>
				<#if (xmin_msg)??>
					<font color="red" class="tip_error">${xmin_msg!}</font>
				<#else>
					(最小值)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">isFirstVip</span>
			<input class="dfinput"   name="t_user.isFirstVip" value="${(t_user.isFirstVip)!}" />
			<i>
				<#if (isFirstVip_msg)??>
					<font color="red" class="tip_error">${isFirstVip_msg!}</font>
				<#else>
					(是否首次成为VIP(默认 1 是 2 否))
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">vipType</span>
			<input class="dfinput"   name="t_user.vipType" value="${(t_user.vipType)!}" />
			<i>
				<#if (vipType_msg)??>
					<font color="red" class="tip_error">${vipType_msg!}</font>
				<#else>
					(1.普通会员 2.普通vip 3.白金vip 4.钻石vip)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">portMerBillNo</span>
			<input class="dfinput"   name="t_user.portMerBillNo" value="${(t_user.portMerBillNo)!}" />
			<i>
				<#if (portMerBillNo_msg)??>
					<font color="red" class="tip_error">${portMerBillNo_msg!}</font>
				<#else>
					(IPS账号)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">pIpsBillNo</span>
			<input class="dfinput"   name="t_user.pIpsBillNo" value="${(t_user.pIpsBillNo)!}" />
			<i>
				<#if (pIpsBillNo_msg)??>
					<font color="red" class="tip_error">${pIpsBillNo_msg!}</font>
				<#else>
					(自动投标签约协议号)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">source</span>
			<input class="dfinput"   name="t_user.source" value="${(t_user.source)!}" />
			<i>
				<#if (source_msg)??>
					<font color="red" class="tip_error">${source_msg!}</font>
				<#else>
					(注册来源)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">rebateSum</span>
			<input class="dfinput"   name="t_user.rebateSum" value="${(t_user.rebateSum)!}" />
			<i>
				<#if (rebateSum_msg)??>
					<font color="red" class="tip_error">${rebateSum_msg!}</font>
				<#else>
					(返利余额)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">totalRebateSum</span>
			<input class="dfinput"   name="t_user.totalRebateSum" value="${(t_user.totalRebateSum)!}" />
			<i>
				<#if (totalRebateSum_msg)??>
					<font color="red" class="tip_error">${totalRebateSum_msg!}</font>
				<#else>
					(累计返利金额)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">rebateCount</span>
			<input class="dfinput"   name="t_user.rebateCount" value="${(t_user.rebateCount)!}" />
			<i>
				<#if (rebateCount_msg)??>
					<font color="red" class="tip_error">${rebateCount_msg!}</font>
				<#else>
					(推荐人数)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">channel</span>
			<input class="dfinput"   name="t_user.channel" value="${(t_user.channel)!}" />
			<i>
				<#if (channel_msg)??>
					<font color="red" class="tip_error">${channel_msg!}</font>
				<#else>
					(知道渠道)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">planDueinSum</span>
			<input class="dfinput"   name="t_user.planDueinSum" value="${(t_user.planDueinSum)!}" />
			<i>
				<#if (planDueinSum_msg)??>
					<font color="red" class="tip_error">${planDueinSum_msg!}</font>
				<#else>
					(理财计划冻结金额)
				</#if>
			</i>
		</div>
		<div>
			<span class="form_title">&nbsp;&nbsp;</span> 
			<input value="提交" type="submit">
		</div>

