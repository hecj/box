	<ul class="forminfo">
					<input type="hidden" name="user.id" value="${(user.id)!}" />
		
				<li>
					<label>用户名</label>
					<input type="text" class="dfinput"  name="user.username" value="${(user.username)!}" />
					<i>
						<#if (u_msg)??>
							<font color="red" class="tip_error">${u_msg!}</font>
						<#else>
							(用户名)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>密码</label>
					<input type="text" class="dfinput"  name="user.password" value="${(user.password)!}" />
					<i>
						<#if (p_msg)??>
							<font color="red" class="tip_error">${p_msg!}</font>
						<#else>
							(密码)
						</#if>
					</i>
				</li>
				
				<li>
					<label>手机号</label>
					<input type="text" class="dfinput"  name="user.phone" value="${(user.phone)!}" />
					<i>
						<#if (phone_msg)??>
							<font color="red" class="tip_error">${phone_msg!}</font>
						<#else>
							(手机号)
						</#if>
					</i>
				</li>
				
				<li>
					<label>邮箱</label>
					<input type="text" class="dfinput"  name="user.email" value="${(user.email)!}" />
					<i>
						<#if (email_msg)??>
							<font color="red" class="tip_error">${email_msg!}</font>
						<#else>
							(邮箱)
						</#if>
					</i>
				</li>
				
				<li>
					<label>性别</label>
					<label><input type="radio" name="user.sex" value="男" <#if user??&&user.sex='男'>checked="checked"</#if>/>男</label>
					<label><input type="radio" name="user.sex" value="女" <#if user??&&user.sex='女'>checked="checked"</#if>/>女</label>
					<i>
						<#if (sex_msg)??>
							<font color="red" class="tip_error">${sex_msg!}</font>
						<#else>
							(性别)
						</#if>
					</i>
				</li>
				<#--
				<li>
					<label>个人说明</label>
					<input type="text" class="dfinput"  name="user.password" value="${(user.password)!}" />
					<i>
						<#if (per_msg)??>
							<font color="red" class="tip_error">${per_msg!}</font>
						<#else>
							(个人说明)
						</#if>
					</i>
				</li>
				
				-->
				<li>
					<label>真实姓名</label>
					<input type="text" class="dfinput" name="user.realname" value="${(user.realname)!}" />
					<i>
						<#if (real_msg)??>
							<font color="red" class="tip_error">${real_msg!}</font>
						<#else>
							(真实姓名)
						</#if>
					</i>
				</li>
				
				<li>
					<label>身份证id</label>
					<input type="text" class="dfinput"  name="user.idno" value="${(user.idno)!}" />
					<i>
						<#if (idno_msg)??>
							<font color="red" class="tip_error">${idno_msg!}</font>
						<#else>
							(身份证)
						</#if>
					</i>
				</li>
				
				
				<li>
					<label>昵称</label>
					<input type="text" class="dfinput"  name="user.nickname" value="${(user.nickname)!}" />
					<i>
						<#if (n_msg)??>
							<font color="red" class="tip_error">${n_msg!}</font>
						<#else>
							(昵称)
						</#if>
					</i>
				</li>
		
				
				
		
				<li>
					<label>状态</label>
					<select  class="dfinput"  name="user.status"   >
						<option value="0" <#if ((user.status)?? && (user.status == 0))> selected="selected"</#if> >正常</option>
						<option value="-1" <#if ((user.status)?? && (user.status == 1))> selected="selected"</#if> >禁用</option>
					</select>
					<i>
						<#if (stat_msg)??>
							<font color="red" class="tip_error">${stat_msg!}</font>
						<#else>
							(状态：0正常 -1禁用)
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>