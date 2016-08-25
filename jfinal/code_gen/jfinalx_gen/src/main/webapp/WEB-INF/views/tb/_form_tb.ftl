	
		<input type="hidden" name="tb.id" value="${(tb.id)!}" />

		<div>
			<span class="form_title">n</span>
			<input class="dfinput"   name="tb.n" value="${(tb.n)!}" />
			<i>
				<#if (n_msg)??>
					<font color="red" class="tip_error">${n_msg!}</font>
				<#else>
					(模型名)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">cm</span>
			<input class="dfinput"   name="tb.cm" value="${(tb.cm)!}" />
			<i>
				<#if (cm_msg)??>
					<font color="red" class="tip_error">${cm_msg!}</font>
				<#else>
					(注释)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">eg</span>
			<input class="dfinput"   name="tb.eg" value="${(tb.eg)!}" />
			<i>
				<#if (eg_msg)??>
					<font color="red" class="tip_error">${eg_msg!}</font>
				<#else>
					()
				</#if>
			</i>
		</div>
		<div>
			<span class="form_title">&nbsp;&nbsp;</span> 
			<input value="提交" type="submit">
		</div>

