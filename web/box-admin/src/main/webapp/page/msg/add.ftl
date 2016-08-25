<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>添加msg</title>
	<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
	<script>
		jQuery(function(){
			jQuery(".tip_error").eq(0).parents("li").find(".dfinput").focus();
		});
	</script>
	
	
	<script type="text/javascript" src="/static/js/common.js"></script>
    <link  rel="Stylesheet" href="/static/js/combobox/jquery.combobox.css" />
    <script type="text/javascript" src="/static/js/combobox/jquery.combobox.js"></script>
	
	
<script type="text/javascript">
        $(function() {
            //设置该页面上所有combobox的模式
            combobox.prototype.mustSelect = false; //必须选择参数,默认为false
            combobox.prototype.fieldText = "text"; //设置数据源文本命名，默认为text
            combobox.prototype.fieldValue = "id"; //设置数据源id命名，默认为id
            combobox.prototype.maxLength = null; //自动搜索显示20项,默认为null.即不限制
            //初始化所有combobox
            //如果有多个text要改成combobox.请在text上设置class。然后id参数使用‘.class',如 combobox.prototype.init(".demos"）
            combobox.prototype.init("#demo", "/static/js/combobox/dropdown.gif");

            //绑定
            var combo = new combobox("#demo" );
            combo.dataSource = [];
            combo.onSelected=function(el){
            	 $("[name='msg.tid']").val(el.attr("id"));			
				 $("[name='msg.tu']").val(el.text());	
				 showhis(el.attr("id"));
            }
          	<#list ul as u>
          		combo.dataSource.push({"id":"${u.id!}",text:"${u.u!}"});
          	</#list>
            combo.defaultText = "${tu!}";//text的默认值，默认为text的value;
            combo.dataBind();
        });
    </script>
	
</head>

<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="/msg">msg列表</a></li>
    <li><a href="#">添加msg</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    <div class="formtitle"><span>添加msg</span></div>
    
		<#include "_form.ftl" />
    
    
    </div>
    
    <iframe id="showhis" runat="server"  width="100%" height="500" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
    
    <script>
    	var frid="${tid!}"
    	function showhis(frid){
    		if(frid){
	    		jQuery("#showhis")[0].src="/msg/history?frid="+frid;
    		}
    	}
    	jQuery(function(){
    		showhis(frid);
    	});
    </script>
    
    
    
    
    
          
    


</body>

</html>
