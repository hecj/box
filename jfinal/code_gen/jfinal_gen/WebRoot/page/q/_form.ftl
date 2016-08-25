	<ul class="forminfo">
					<input type="hidden" name="q.id" value="${(q.id)!}" />
		
				<li>
					<label>n</label>
					<input type="text" class="dfinput"  name="q.n" value="${(q.n)!}" />
					<i>
						<#if (n_msg)??>
							<font color="red" class="tip_error">${n_msg!}</font>
						<#else>
							(查询名称)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>dbid</label>
					<input type="text" class="dfinput"  name="q.dbid" value="${(q.dbid)!}" />
					<i>
						<#if (dbid_msg)??>
							<font color="red" class="tip_error">${dbid_msg!}</font>
						<#else>
							(关联数据库id)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>sql</label>
					<input type="text" class="dfinput"  name="q.sql" value="${(q.sql)!}" />
					<i>
						<#if (sql_msg)??>
							<font color="red" class="tip_error">${sql_msg!}</font>
						<#else>
							(sql语句)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>stat</label>
					<input type="text" class="dfinput"  name="q.stat" value="${(q.stat)!}" />
					<i>
						<#if (stat_msg)??>
							<font color="red" class="tip_error">${stat_msg!}</font>
						<#else>
							(状态 0：默认启用 -1禁用)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>type</label>
					<input type="text" class="dfinput"  name="q.type" value="${(q.type)!}" />
					<i>
						<#if (type_msg)??>
							<font color="red" class="tip_error">${type_msg!}</font>
						<#else>
							(类型 0：用户查询 1统计内容)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>file_path</label>
					<input type="text" class="dfinput"  name="q.file_path" value="${(q.file_path)!}" />
					<i>
						<#if (file_path_msg)??>
							<font color="red" class="tip_error">${file_path_msg!}</font>
						<#else>
							(产生静态统计文件地址)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>de</label>
					<input type="text" class="dfinput"  name="q.de" value="${(q.de)!}" />
					<i>
						<#if (de_msg)??>
							<font color="red" class="tip_error">${de_msg!}</font>
						<#else>
							(查询描述)
						</#if>
					</i>
				</li>
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>