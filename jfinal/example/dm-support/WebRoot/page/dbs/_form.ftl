	<ul class="forminfo">
					<input type="hidden" name="dbs.id" value="${(dbs.id)!}" />
		
				<li>
					<label>url</label>
					<input type="text" class="dfinput"  name="dbs.url" value="${(dbs.url)!}" />
					<i>
						<#if (url_msg)??>
							<font color="red" class="tip_error">${url_msg!}</font>
						<#else>
							(数据库)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>u</label>
					<input type="text" class="dfinput"  name="dbs.u" value="${(dbs.u)!}" />
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
					<input type="text" class="dfinput"  name="dbs.p" value="${(dbs.p)!}" />
					<i>
						<#if (p_msg)??>
							<font color="red" class="tip_error">${p_msg!}</font>
						<#else>
							(密码)
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>