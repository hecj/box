	<ul class="forminfo">
					<input type="hidden" name="db.id" value="${(db.id)!}" />
		
				<li>
					<label>url</label>
					<input type="text" class="dfinput"  name="db.url" value="${(db.url)!}" />
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
					<input type="text" class="dfinput"  name="db.u" value="${(db.u)!}" />
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
					<input type="text" class="dfinput"  name="db.p" value="${(db.p)!}" />
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