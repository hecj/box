	<ul class="forminfo">
					<input type="hidden" name="puser.id" value="${(puser.id)!}" />
		
				<li>
					<label>用户名</label>
					<input type="text" class="dfinput"  name="puser.username" value="${(puser.username)!}" />
					<!--
					<i>
						<#if (u_msg)??>
							<font color="red" class="tip_error">${u_msg!}</font>
						<#else>
							(用户名)
						</#if>
					</i>
					-->
				</li>
				<li>
					<label>密码</label>
					<input type="password" class="dfinput"  name="puser.password" value="${(puser.password)!}" />
					<!--
					<i>
						<#if (p_msg)??>
							<font color="red" class="tip_error">${p_msg!}</font>
						<#else>
							(密码)
						</#if>
					</i>
					-->
				</li>
				
		
				<li>
					<label>角色</label>
					<select  class="dfinput"  name="puser.role_id" >
						<#list rl as r>
						<option value="${r.id}"  <#if ((r.id)?? && (puser.role_id)?? && (r.id==puser.role_id))> selected="selected"</#if> >${r.n}</option>
						</#list>
					</select>
					<!--
					<i>
						<#if (rid_msg)??>
							<font color="red" class="tip_error">${rid_msg!}</font>
						<#else>
							(角色id)
						</#if>
					</i>
					-->
				</li>
				<li>
					<label>状态</label>
					<select  class="dfinput"  name="puser.status"   >
						<option value="0" <#if ((puser.status)?? && (puser.status == 0))> selected="selected"</#if> >正常</option>
						<option value="-1" <#if ((puser.status)?? && (puser.status == -1))> selected="selected"</#if> >禁用</option>
					</select>
					<!--
					<i>
						<#if (stat_msg)??>
							<font color="red" class="tip_error">${stat_msg!}</font>
						<#else>
							(状态：0正常 -1禁用)
						</#if>
					</i>
					-->
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>