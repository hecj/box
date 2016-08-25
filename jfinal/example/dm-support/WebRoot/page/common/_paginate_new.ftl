<#macro paginate currentPage totalPage actionUrl totalRow urlParas="">
	<#if (totalPage <= 0) || (currentPage > totalPage)><#return></#if>
	<#local startPage = currentPage - 4>
	<#if (startPage < 1)><#local startPage = 1></#if>
	
	<#local endPage = currentPage + 4>
	<#if (endPage > totalPage)><#local endPage = totalPage></#if>
	
	<div class="pagin">
			<#if (currentPage <= 8)>
				<#local startPage = 1>
			</#if>
			<#if ((totalPage - currentPage) < 8)>
				<#local endPage = totalPage>
			</#if>
			
			<div class="message">共<i class="blue">${totalRow}</i>条记录，当前显示第&nbsp;<i class="blue">${currentPage}&nbsp;</i>页</div>
			<ul class="paginList">
				
				<#if (currentPage == 1)>
				  	<li class="paginItem" style="background: #FFF;"><a><span class="pagepre prev_page disabled"></span></a></li>
				<#else>
				  	<li class="paginItem"><a href="${actionUrl}#{currentPage - 1}${urlParas!}" ><span class="pagepre prev_page"></span></a></li>
				</#if>
				
				<#if (currentPage > 8)>
				 	<li class="paginItem"><a href="${actionUrl}#{1}${urlParas!}">#{1}</a></li>
				 	<li class="paginItem"><a href="${actionUrl}#{2}${urlParas!}">#{2}</a></li>
				 	<li class="paginItem more"><a href="javascript:void(0);">...</a></li>
				</#if>
				
				<#list startPage..endPage as i>
					<#if currentPage == i>
						 <li class="paginItem current"><a><span class="current">#{i}</span></a></li>
						
					<#else>
						 <li class="paginItem"><a href="${actionUrl}#{i}${urlParas!}"><span class="">#{i}</span></a></li>
					</#if>
				</#list>
				
				<#if ((totalPage - currentPage) >= 8)>
					<li class="paginItem more"><a href="javascript:void(0);">...</a></li>
					
					<li class="paginItem"><a href="${actionUrl}#{totalPage - 1}${urlParas!}">#{totalPage - 1}</a></li>
					<li class="paginItem"><a href="${actionUrl}#{totalPage}${urlParas!}">#{totalPage}</a></li>
					
				</#if>
				
				<#if (currentPage == totalPage)>
				 	<li class="paginItem" style="background: #FFF;"><a><span class="pagenxt next_page disabled"></span></a></li>
				<#else>
				 	<li class="paginItem"><a href="${actionUrl}#{currentPage + 1}${urlParas!}" class="next_page" rel="next"><span class="pagenxt next_page disabled"></span></a></li>
				</#if>
			
			
			
			
			</ul>
			
			
	</div>
</#macro>

<#macro withSub text,len>
	<#if ((len!=-1)&&(text?length>len))>
		<span class="text_sub">${text?substring(0,len)}<a href="javascript:void(0);" onclick="jQuery(this).parents('.text_sub').hide();jQuery(this).parents('.text_sub').next().show();" style="color:green">..↙</a></span>
		<span style="display:none;">${text}</span>
	<#else>
		${text}
	</#if>
</#macro>
 



