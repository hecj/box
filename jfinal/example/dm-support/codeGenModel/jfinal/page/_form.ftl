	<ul class="forminfo">
		<#list root.columnlist as col>
			<#if col.ispk>
			<input type="hidden" name="${root.name}.${col.name}" value="${"$"}{(${root.name}.${col.name})!}" />
			<#else>  
		
				<li>
					<label>${col.name}</label>
					<input type="text" class="dfinput"  name="${root.name}.${col.name}" value="${"$"}{(${root.name}.${col.name})!}" />
					<i>
						<${"#"}if (${col.name}_msg)??>
							<font color="red" class="tip_error">${"$"}{${col.name}_msg!}</font>
						<${"#"}else>
							(${col.comment?if_exists})
						</${"#"}if>
					</i>
				</li>
				
			</#if> 
		</#list>
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>