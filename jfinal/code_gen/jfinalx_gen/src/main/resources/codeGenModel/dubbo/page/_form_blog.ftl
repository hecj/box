	<#list root.columnlist as col>
		<#if col.ispk>
		<input type="hidden" name="${root.name}.${col.name}" value="${"$"}{(${root.name}.${col.name})!}" />
		<#else>  
		<div>
			<span class="form_title">${col.name}</span>
			<input class="dfinput"   name="${root.name}.${col.name}" value="${"$"}{(${root.name}.${col.name})!}" />
			<i>
				<${"#"}if (${col.name}_msg)??>
					<font color="red" class="tip_error">${"$"}{${col.name}_msg!}</font>
				<${"#"}else>
					(${col.comment?if_exists})
				</${"#"}if>
			</i>
		</div>
		</#if> 
	</#list>
		<div>
			<span class="form_title">&nbsp;&nbsp;</span> 
			<input value="提交" type="submit">
		</div>

