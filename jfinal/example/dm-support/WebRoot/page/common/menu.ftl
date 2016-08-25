<ul class="menuson" >
    <li><cite></cite><a href="/dbs" target="rightFrame">dbs(数据源管理)</a><i></i></li>
    <li><cite></cite><a href="/u" target="rightFrame">u(用户)</a><i></i></li>
    <li><cite></cite><a href="/m" target="rightFrame">m(菜单)</a><i></i></li>
    <li><cite></cite><a href="/qo" target="rightFrame">qo(查询关联的字段选项)</a><i></i></li>
    <li><cite></cite><a href="/r" target="rightFrame">r(角色)</a><i></i></li>
    <li><cite></cite><a href="/q" target="rightFrame">q(查询管理)</a><i></i></li>
</ul> 
<style>
	.leftmenu li{
		display:none;
	}
	.leftmenu dd{
		display:none;
	}
</style>
<script>
jQuery(function(){
	//jQuery(".leftmenu li").hide();
	
	{
		<#list __ms as m>
			showM("${m.aurl}","${m.id}");
		</#list>
	}
	function showM(url,id){
		jQuery(".menuson li a[href='"+url+"']").parents("li").attr("mid",id) ;
	};
	{
		var us="${__r.ms!}".split(",");
		for(var i in us){
			jQuery(".leftmenu li[mid='"+us[i]+"']").parents("dd").show();
			jQuery(".leftmenu li[mid='"+us[i]+"']").show();
		}
	}
	 
	
	
	if("${__u.u}"=="admin"){//如果是管理员 例外
		jQuery(".leftmenu dd li").show();
		jQuery(".leftmenu dd").show();
	}
	jQuery(".leftmenu dd:visible").first().find(".menuson").show();
	/**
	{
		jQuery(".leftmenu dd").each(function(){
			jQuery(this).find("[mid]:hidden").remove();
			var s=jQuery(this).find("[mid]:visible").size();
			if(s<1){
				jQuery(this).remove();
			}
		});
	}
	**/
});
</script>