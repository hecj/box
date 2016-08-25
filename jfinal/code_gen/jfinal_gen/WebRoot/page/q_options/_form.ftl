	<ul class="forminfo">
					<input type="hidden" name="q_options.id" value="${(q_options.id)!}" />
		
				<li>
					<label>qid</label>
					<input type="text" class="dfinput"  name="q_options.qid" value="${(q_options.qid)!}" />
					<i>
						<#if (qid_msg)??>
							<font color="red" class="tip_error">${qid_msg!}</font>
						<#else>
							(关联查询id)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>f</label>
					<input type="text" class="dfinput"  name="q_options.f" value="${(q_options.f)!}" />
					<i>
						<#if (f_msg)??>
							<font color="red" class="tip_error">${f_msg!}</font>
						<#else>
							(关联字段)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>j</label>
					<input type="text" class="dfinput"  name="q_options.j" value="${(q_options.j)!}" />
					<i>
						<#if (j_msg)??>
							<font color="red" class="tip_error">${j_msg!}</font>
						<#else>
							(关联字段对应的json情况)
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>