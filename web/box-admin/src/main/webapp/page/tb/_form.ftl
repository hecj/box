	<ul class="forminfo">
					<input type="hidden" name="tb.id" value="${(tb.id)!}" />
		
				<li>
					<label>n</label>
					<input type="text" class="dfinput"  name="tb.n" value="${(tb.n)!}" />
					<i>
						<#if (n_msg)??>
							<font color="red" class="tip_error">${n_msg!}</font>
						<#else>
							(模型名)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>cm</label>
					<input type="text" class="dfinput"  name="tb.cm" value="${(tb.cm)!}" />
					<i>
						<#if (cm_msg)??>
							<font color="red" class="tip_error">${cm_msg!}</font>
						<#else>
							(注释)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>eg</label>
					<input type="text" class="dfinput"  name="tb.eg" value="${(tb.eg)!}" />
					<i>
						<#if (eg_msg)??>
							<font color="red" class="tip_error">${eg_msg!}</font>
						<#else>
							()
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>