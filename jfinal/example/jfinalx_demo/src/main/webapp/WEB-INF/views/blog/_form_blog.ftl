	<input type="hidden" name="blog.id" value="${(blog.id)!}" />
	<div>
		<span class="form_title">标题</span>
		<input type="text" name="blog.title" value="${(blog.title)!}" />${titleMsg!}
	</div>
	<div>
		<span class="form_title">内容</span>
		<textarea name="blog.content" cols="80" rows="10">${(blog.content)!}</textarea>${contentMsg!}
	</div>
	<div>
		<span class="form_title">&nbsp;&nbsp;</span> 
		<input value="提交" type="submit">
	</div>
