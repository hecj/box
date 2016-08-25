	
		<input type="hidden" name="kv_config.id" value="${(kv_config.id)!}" />

		<div>
			<span class="form_title">k</span>
			<input class="dfinput"   name="kv_config.k" value="${(kv_config.k)!}" />
			<i>
				<#if (k_msg)??>
					<font color="red" class="tip_error">${k_msg!}</font>
				<#else>
					(键)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">v</span>
			<input class="dfinput"   name="kv_config.v" value="${(kv_config.v)!}" />
			<i>
				<#if (v_msg)??>
					<font color="red" class="tip_error">${v_msg!}</font>
				<#else>
					(值)
				</#if>
			</i>
		</div>
		<div>
			<span class="form_title">&nbsp;&nbsp;</span> 
			<input value="提交" type="submit">
		</div>

