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
					<input type="text" class="dfinput"  name="u.rid" value="${(u.rid)!}" />
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
					<input type="text" class="dfinput"  name="u.stat" value="${(u.stat)!}" />
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