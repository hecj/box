	
		<input type="hidden" name="blog.id" value="${(blog.id)!}" />

		<div>
			<span class="form_title">title</span>
			<input class="dfinput"   name="blog.title" value="${(blog.title)!}" />
			<i>
				<#if (title_msg)??>
					<font color="red" class="tip_error">${title_msg!}</font>
				<#else>
					()
				</#if>
			</i>
		</div>

		<div>
			<span class="form_title">content</span>
			<input class="dfinput"   name="blog.content" value="${(blog.content)!}" />
			<i>
				<#if (content_msg)??>
					<font color="red" class="tip_error">${content_msg!}</font>
				<#else>
					()
				</#if>
			</i>
		</div>
		<div>
			<span class="form_title">&nbsp;&nbsp;</span> 
			<input value="提交" type="submit">
		</div>

