	<ul class="forminfo">
					<input type="hidden" name="u.id" value="${(u.id)!}" />
		
				<li>
					<label>u</label>
					<input type="text" class="dfinput"  name="u.u" value="${(u.u)!}" />
					<i>
						<#if (u_msg)??>
							<font color="red" class="tip_error">${u_msg!}</font>
						<#else>
							(用户名)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>p</label>
					<input type="text" class="dfinput"  name="u.p" value="${(u.p)!}" />
					<i>
						<#if (p_msg)??>
							<font color="red" class="tip_error">${p_msg!}</font>
						<#else>
							(密码)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>rid</label>
					<select  class="dfinput"  name="u.rid"   >
						<#list rl as r>
						<option value="${r.id}"  <#if ((r.id)?? && (u.rid)?? && (r.id==u.rid))> selected="selected"</#if> >${r.n}</option>
						
						</#list>
					</select>
					<i>
						<#if (rid_msg)??>
							<font color="red" class="tip_error">${rid_msg!}</font>
						<#else>
							(角色id)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>stat</label>
					<select  class="dfinput"  name="u.stat"   >
						<option value="0" <#if ((u.stat)?? && (u.stat == 0))> selected="selected"</#if> >正常</option>
						<option value="-1" <#if ((u.stat)?? && (u.stat == -1))> selected="selected"</#if> >禁用</option>
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