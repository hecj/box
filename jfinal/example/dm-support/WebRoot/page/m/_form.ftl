	<ul class="forminfo">
					<input type="hidden" name="m.id" value="${(m.id)!}" />
		
				<li>
					<label>n</label>
					<input type="text" class="dfinput"  name="m.n" value="${(m.n)!}" />
					<i>
						<#if (n_msg)??>
							<font color="red" class="tip_error">${n_msg!}</font>
						<#else>
							(名称)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>aurl</label>
					<input type="text" class="dfinput"  name="m.aurl" value="${(m.aurl)!}" />
					<i>
						<#if (aurl_msg)??>
							<font color="red" class="tip_error">${aurl_msg!}</font>
						<#else>
							(action路径)
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>