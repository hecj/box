	<ul class="forminfo">
					<input type="hidden" name="r.id" value="${(r.id)!}" />
		
				<li>
					<label>n</label>
					<input type="text" class="dfinput"  name="r.n" value="${(r.n)!}" />
					<i>
						<#if (n_msg)??>
							<font color="red" class="tip_error">${n_msg!}</font>
						<#else>
							(角色名称)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>qs</label>
					<input type="text" class="dfinput"  name="r.qs" value="${(r.qs)!}" />
					<i>
						<#if (qs_msg)??>
							<font color="red" class="tip_error">${qs_msg!}</font>
						<#else>
							(通用查询权限字符逗号分隔)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>ms</label>
					<input type="text" class="dfinput"  name="r.ms" value="${(r.ms)!}" />
					<i>
						<#if (ms_msg)??>
							<font color="red" class="tip_error">${ms_msg!}</font>
						<#else>
							(菜单权限id字符 逗号分开)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>mps</label>
					<input type="text" class="dfinput"  name="r.mps" value="${(r.mps)!}" />
					<i>
						<#if (mps_msg)??>
							<font color="red" class="tip_error">${mps_msg!}</font>
						<#else>
							(菜单权限路径字符 逗号分隔)
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>