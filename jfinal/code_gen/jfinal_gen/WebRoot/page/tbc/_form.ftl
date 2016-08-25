	<ul class="forminfo">
					<input type="hidden" name="tbc.id" value="${(tbc.id)!}" />
		
				<li>
					<label>tbid</label>
					<input type="text" class="dfinput"  name="tbc.tbid" value="${(tbc.tbid)!}" />
					<i>
						<#if (tbid_msg)??>
							<font color="red" class="tip_error">${tbid_msg!}</font>
						<#else>
							(模型id)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>cn</label>
					<input type="text" class="dfinput"  name="tbc.cn" value="${(tbc.cn)!}" />
					<i>
						<#if (cn_msg)??>
							<font color="red" class="tip_error">${cn_msg!}</font>
						<#else>
							(属性名称)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>ty</label>
					<input type="text" class="dfinput"  name="tbc.ty" value="${(tbc.ty)!}" />
					<i>
						<#if (ty_msg)??>
							<font color="red" class="tip_error">${ty_msg!}</font>
						<#else>
							(数据类型)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>de</label>
					<input type="text" class="dfinput"  name="tbc.de" value="${(tbc.de)!}" />
					<i>
						<#if (de_msg)??>
							<font color="red" class="tip_error">${de_msg!}</font>
						<#else>
							(默认值)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>fl</label>
					<input type="text" class="dfinput"  name="tbc.fl" value="${(tbc.fl)!}" />
					<i>
						<#if (fl_msg)??>
							<font color="red" class="tip_error">${fl_msg!}</font>
						<#else>
							(标记(是否是主|空|增))
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>cm</label>
					<input type="text" class="dfinput"  name="tbc.cm" value="${(tbc.cm)!}" />
					<i>
						<#if (cm_msg)??>
							<font color="red" class="tip_error">${cm_msg!}</font>
						<#else>
							(注释)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>egg</label>
					<input type="text" class="dfinput"  name="tbc.egg" value="${(tbc.egg)!}" />
					<i>
						<#if (egg_msg)??>
							<font color="red" class="tip_error">${egg_msg!}</font>
						<#else>
							(示例数据)
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>