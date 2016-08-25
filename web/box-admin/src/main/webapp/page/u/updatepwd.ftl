<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改密码</title>
	<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
	<script>
		jQuery(function(){
			if("${msg!}"){
				alert("${msg!}");
			}
		});
	</script>
</head>

<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">修改密码</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>修改密码</span></div>
    
    <form action="/u/updatep" method="post">
		
		<ul class="forminfo">
					<input type="hidden" name="u.id" value="${(u.id)!}" />
		
				<li>
					<label>原密码</label>
					<input type="password" class="dfinput"  name="oldp"   />
					<i>
						 
					</i>
				</li>
		
				<li>
					<label>新密码</label>
					<input type="password" class="dfinput"  name="newp"   />
					<i>
					</i>
				</li>
				
				<li>
					<label>&nbsp;</label>
					<input value="提交" type="submit" class="btn">
				</li>
				
			</ul>
	</form>
    </div>

</body>

</html>
