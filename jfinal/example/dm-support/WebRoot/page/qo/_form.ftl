			<ul class="forminfo">
					<input type="hidden" name="qo.id" value="${(qo.id)!}" />
		
				<li>
					<label>qid</label>
				<select  class="dfinput"  name="qo.qid"   >
					<#list ql as q>
					
					<option value="${q.id}"  <#if ((q.id)?? && (qo.qid)?? && (q.id==qo.qid))> selected="selected"</#if> >${q.n}</option>
					
					</#list>
				</select>
					<i>
						<#if (qid_msg)??>
							<font color="red" class="tip_error">${qid_msg!}</font>
						<#else>
							(关联查询id)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>n</label>
					<input type="text" class="dfinput"  name="qo.n" value="${(qo.n)!}" />
					<i>
						<#if (n_msg)??>
							<font color="red" class="tip_error">${n_msg!}</font>
						<#else>
							(字段含义)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>f</label>
					<input type="text" class="dfinput"  name="qo.f" value="${(qo.f)!}" />
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
					<input type="text" class="dfinput"  name="qo.j" value='${(qo.j)!}' />
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