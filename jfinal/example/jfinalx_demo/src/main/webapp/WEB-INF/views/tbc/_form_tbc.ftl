	
		<input type="hidden" name="tbc.id" value="${(tbc.id)!}" />

		<div>
			<span class="form_title">tbid</span>
			<input class="dfinput"   name="tbc.tbid" value="${(tbc.tbid)!}" />
			<i>
				<#if (tbid_msg)??>
					<font color="red" class="tip_error">${tbid_msg!}</font>
				<#else>
					(模型id)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">cn</span>
			<input class="dfinput"   name="tbc.cn" value="${(tbc.cn)!}" />
			<i>
				<#if (cn_msg)??>
					<font color="red" class="tip_error">${cn_msg!}</font>
				<#else>
					(属性名称)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">ty</span>
			<input class="dfinput"   name="tbc.ty" value="${(tbc.ty)!}" />
			<i>
				<#if (ty_msg)??>
					<font color="red" class="tip_error">${ty_msg!}</font>
				<#else>
					(数据类型)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">de</span>
			<input class="dfinput"   name="tbc.de" value="${(tbc.de)!}" />
			<i>
				<#if (de_msg)??>
					<font color="red" class="tip_error">${de_msg!}</font>
				<#else>
					(默认值)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">fl</span>
			<input class="dfinput"   name="tbc.fl" value="${(tbc.fl)!}" />
			<i>
				<#if (fl_msg)??>
					<font color="red" class="tip_error">${fl_msg!}</font>
				<#else>
					(标记(是否是主|空|增))
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">cm</span>
			<input class="dfinput"   name="tbc.cm" value="${(tbc.cm)!}" />
			<i>
				<#if (cm_msg)??>
					<font color="red" class="tip_error">${cm_msg!}</font>
				<#else>
					(注释)
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">egg</span>
			<input class="dfinput"   name="tbc.egg" value="${(tbc.egg)!}" />
			<i>
				<#if (egg_msg)??>
					<font color="red" class="tip_error">${egg_msg!}</font>
				<#else>
					(示例数据)
				</#if>
			</i>
		</div>
		<div>
			<span class="form_title">&nbsp;&nbsp;</span> 
			<input value="提交" type="submit">
		</div>

