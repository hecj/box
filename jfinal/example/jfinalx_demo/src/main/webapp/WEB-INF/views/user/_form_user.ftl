	
		<input type="hidden" name="user.id" value="${(user.id)!}" />

		<div>
			<span class="form_title">email</span>
			<input class="dfinput"   name="user.email" value="${(user.email)!}" />
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
			<input class="dfinput"   name="user.username" value="${(user.username)!}" />
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
			<input class="dfinput"   name="user.vipStatus" value="${(user.vipStatus)!}" />
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
			<input class="dfinput"   name="user.authStep" value="${(user.authStep)!}" />
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
			<input class="dfinput"   name="user.enable" value="${(user.enable)!}" />
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
			<input class="dfinput"   name="user.password" value="${(user.password)!}" />
			<i>
				<#if (password_msg)??>
					<font color="red" class="tip_error">${password_msg!}</font>
				<#else>
					()
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">dealpwd</span>
			<input class="dfinput"   name="user.dealpwd" value="${(user.dealpwd)!}" />
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
			<input class="dfinput"   name="user.mobilePhone" value="${(user.mobilePhone)!}" />
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
			<input class="dfinput"   name="user.refferee" value="${(user.refferee)!}" />
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
			<input class="dfinput"   name="user.rating" value="${(user.rating)!}" />
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
			<input class="dfinput"   name="user.creditrating" value="${(user.creditrating)!}" />
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
			<input class="dfinput"   name="user.lastIP" value="${(user.lastIP)!}" />
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
			<input class="dfinput"   name="user.lastDate" value="${(user.lastDate)!}" />
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
			<input class="dfinput"   name="user.vipCreateTime" value="${(user.vipCreateTime)!}" />
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
			<input class="dfinput"   name="user.creditLimit" value="${(user.creditLimit)!}" />
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
			<input class="dfinput"   name="user.headImg" value="${(user.headImg)!}" />
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
			<input class="dfinput"   name="user.createTime" value="${(user.createTime)!}" />
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
			<input class="dfinput"   name="user.content" value="${(user.content)!}" />
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
			<input class="dfinput"   name="user.usableSum" value="${(user.usableSum)!}" />
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
			<input class="dfinput"   name="user.freezeSum" value="${(user.freezeSum)!}" />
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
			<input class="dfinput"   name="user.dueinSum" value="${(user.dueinSum)!}" />
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
			<input class="dfinput"   name="user.dueoutSum" value="${(user.dueoutSum)!}" />
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
			<input class="dfinput"   name="user.kefuId" value="${(user.kefuId)!}" />
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
			<input class="dfinput"   name="user.adminId" value="${(user.adminId)!}" />
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
			<input class="dfinput"   name="user.groupId" value="${(user.groupId)!}" />
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
			<input class="dfinput"   name="user.usableCreditLimit" value="${(user.usableCreditLimit)!}" />
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
			<input class="dfinput"   name="user.creditlimtor" value="${(user.creditlimtor)!}" />
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
			<input class="dfinput"   name="user.vipFee" value="${(user.vipFee)!}" />
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
			<input class="dfinput"   name="user.feeStatus" value="${(user.feeStatus)!}" />
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
			<input class="dfinput"   name="user.loginCount" value="${(user.loginCount)!}" />
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
			<input class="dfinput"   name="user.lockTime" value="${(user.lockTime)!}" />
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
			<input class="dfinput"   name="user.cashStatus" value="${(user.cashStatus)!}" />
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
			<input class="dfinput"   name="user.xmax" value="${(user.xmax)!}" />
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
			<input class="dfinput"   name="user.x" value="${(user.x)!}" />
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
			<input class="dfinput"   name="user.xmin" value="${(user.xmin)!}" />
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
			<input class="dfinput"   name="user.isFirstVip" value="${(user.isFirstVip)!}" />
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
			<input class="dfinput"   name="user.vipType" value="${(user.vipType)!}" />
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
			<input class="dfinput"   name="user.portMerBillNo" value="${(user.portMerBillNo)!}" />
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
			<input class="dfinput"   name="user.pIpsBillNo" value="${(user.pIpsBillNo)!}" />
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
			<input class="dfinput"   name="user.source" value="${(user.source)!}" />
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
			<input class="dfinput"   name="user.rebateSum" value="${(user.rebateSum)!}" />
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
			<input class="dfinput"   name="user.totalRebateSum" value="${(user.totalRebateSum)!}" />
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
			<input class="dfinput"   name="user.rebateCount" value="${(user.rebateCount)!}" />
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
			<input class="dfinput"   name="user.channel" value="${(user.channel)!}" />
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
			<input class="dfinput"   name="user.planDueinSum" value="${(user.planDueinSum)!}" />
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

