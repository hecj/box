		<ul id="ops" class="forminfo" style="padding:15px;padding-left:100px;border:1px solid #cee">
			<li>创建字段后可以分析条件 创建字段条件限制</li>
		</ul>
		<br>
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
					<#if (q.id)??>
					<a href="javascript:anly()" style="color:red">分析条件</a>
					</#if>
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
					<label>ms</label>
					<input type="text" class="dfinput"  name="q.ms" value="${(q.ms)!}" />
					<i>
						<#if (file_path_msg)??>
							<font color="red" class="tip_error">${ms_msg!}</font>
						<#else>
							执行间隔(多少分钟内执行一次 单位为分钟 0标示不限制)
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
	
	
	
	<script>
		var qostr="${qostr!}";
		//分析条件
		function anly(){
			var sql=jQuery("[name=q.sql]").val();
			var fs=sql.split(/(from)|(FROM)/g)[0];
			fs=peon.tools.string.trim(fs);
			fs=fs.replace(/(select)|(SELECT)/g, "").replace(" ", "").split(",");
			console.log(fs);
			jQuery("#ops").empty();
			for(var fi in fs){
				jQuery("#ops").append("<li><a style='color:green' x='"+fs[fi]+"' href='/qo/addop/${q.id}?f="+fs[fi]+"'>创建条件字段："+fs[fi]+"</a></li>");
			}
			if(qostr){
				var qos=qostr.split(",");
				for(var ii in qos){
					jQuery("#ops").find("[x='"+qos[ii]+"']").css("color","blue").html(jQuery("#ops").find("[x='"+qos[ii]+"']").html().replace("创建","已创建,修改"));
				}
			}
			
		}
		
		
		jQuery(function(){
			if("${(q.id)!}"){
				//修改模式 主动分析
				anly();
			}
		});
	</script>