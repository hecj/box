<#macro paginate currentPage totalPage href_str >
	<#if (totalPage <= 0) || (currentPage > totalPage)><#return></#if>
	<#local startPage = currentPage - 4>
	<#if (startPage < 1)><#local startPage = 1></#if>
	
	<#local endPage = currentPage + 4>
	<#if (endPage > totalPage)><#local endPage = totalPage></#if>
	
	<div class="pagination">
			<#if (currentPage <= 8)>
				<#local startPage = 1>
			</#if>
			<#if ((totalPage - currentPage) < 8)>
				<#local endPage = totalPage>
			</#if>
			
			<#if (currentPage == 1)>
				<span class="disabled prev_page">上页</span>
			<#else>
				<a href="javascript:${href_str}(#{currentPage - 1})" class="prev_page">上页</a>
			</#if>
			
			<#if (currentPage > 8)>
				<a href="javascript:${href_str}(#{1})">#{1}</a>
				<a href="javascript:${href_str}(#{2})">#{2}</a>
				<span class="gap">…</span>
			</#if>
			
			<#list startPage..endPage as i>
				<#if currentPage == i>
					<span class="current">#{i}</span>
				<#else>
					<a href="javascript:${href_str}(#{i})">#{i}</a>
				</#if>
			</#list>
			
			<#if ((totalPage - currentPage) >= 8)>
				<span class="gap">…</span>
				<a href="javascript:${href_str}(#{totalPage - 1})">#{totalPage - 1}</a>
				<a href="javascript:${href_str}(#{totalPage})">#{totalPage}</a>
			</#if>
			
			<#if (currentPage == totalPage)>
				<span class="disabled next_page">下页</span>
			<#else>
				<a href="javascript:${href_str}(#{currentPage + 1})" class="next_page" rel="next">下页</a>
			</#if>
	</div>
</#macro>