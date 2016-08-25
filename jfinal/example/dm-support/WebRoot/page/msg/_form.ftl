	<ul class="forminfo">
					<input type="hidden" name="msg.id" value="${(msg.id)!}" />
		
			 
			 
				<input type="hidden"   name="msg.tid" value="${tid!}" />
				<input type="hidden"   name="msg.tu"  value="${tu!}"/>
				
		
				<li>
					<label>发送给</label>
					<input style="width:200px; "  type="text" id="demo" value="输入接受者名称" />
					<i>
						<#if (tu_msg)??>
							<font color="red" class="tip_error">${tu_msg!}</font>
						<#else>
						</#if>
					</i>
				</li>
			 
		
				<li>
					<label>内容</label>
					<textarea  class="dfinput" style="height:80px"  name="msg.msg" >${(msg.msg)!}</textarea>
					<i>
						<#if (msg_msg)??>
							<font color="red" class="tip_error">${msg_msg!}</font>
						<#else>
						</#if>
					</i>
				</li>
				
		
		
				 
				
		
				
		
			 
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="button" class="btn" onclick="sb()">
		</li>
	</ul>
	
	<script>
		if("${msg!}"){
			alert("${msg!}");
		}
		function sb(){
			if(!jQuery("[name=msg.tid]").val()){
				alert("请先选择发送对象");
				return;
			}
			if(!jQuery("[name=msg.msg]").val()){
				alert("发送内容不能为空");
				return;
			}
			jQuery.newform("/msg/send",jQuery(".forminfo").serializedom().json).submit();
		}
	</script>