<fieldset class="solid">
	<legend>创建${root.name}</legend>
	

	<#list root.columnlist as col>
	<#if col.ispk>
	<input type="hidden" name="${root.name}.${col.name}" value="${"$"}{(${root.name}.${col.name})!}" />
	<#else>  
		<div>
			<label>${col.name}(${col.comment?if_exists})</label>
			<input type="text" name="${root.name}.${col.name}" value="${"$"}{(${root.name}.${col.name})!}" />${"$"}{${col.name}_msg!}
		</div>
	</#if> 
	</#list>
	
	<div>
		<label>&nbsp;</label>
		<input value="提交" type="submit">
	</div>
</fieldset>