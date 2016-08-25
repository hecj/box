<ul class="menuson">
	<#list root.tablesMap?keys as key >
    <li><cite></cite><a href="/${root.tablesMap[key].name}" target="rightFrame">${root.tablesMap[key].name}(${root.tablesMap[key].comment?if_exists})</a><i></i></li>
	</#list>
</ul> 
