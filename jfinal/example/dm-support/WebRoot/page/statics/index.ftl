<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>通用查询</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>



  <link  rel="Stylesheet" href="/static/js/combobox/jquery.combobox.css" />
    <script type="text/javascript" src="/static/js/combobox/jquery.combobox.js"></script>


<script type="text/javascript">
        $(function() {
            //设置该页面上所有combobox的模式
            combobox.prototype.mustSelect = false; //必须选择参数,默认为false
            combobox.prototype.fieldText = "text"; //设置数据源文本命名，默认为text
            combobox.prototype.fieldValue = "id"; //设置数据源id命名，默认为id
            combobox.prototype.maxLength = 3; //自动搜索显示20项,默认为null.即不限制
            //初始化所有combobox
            //如果有多个text要改成combobox.请在text上设置class。然后id参数使用‘.class',如 combobox.prototype.init(".demos"）
            combobox.prototype.init("#demo", "/static/js/combobox/dropdown.gif");

            //绑定
            var combo = new combobox("#demo",function(x){jQuery("#radio_"+x).click();jQuery("#radio_"+x).focus();  });
            combo.dataSource = [];
            
          	<#list ql as q>
          		combo.dataSource.push({"id":"${q.id!}",text:"${q.n!}"});
          	</#list>
            
            //combo.defaultText = "xx";//text的默认值，默认为text的value;
            combo.dataBind();
        });
    </script>


<script type="text/javascript">
jQuery(function(){
	<#if (VIEW_MSG_TYPE)?? >
	var op={msg:"${VIEW_MSG_MSG}"};
	peon.popx["${VIEW_MSG_TYPE}"](op);
	</#if>
});


function delByIds(ids){
	location="/q/deleteByIds?id="+(ids.join("&id="));
}
function checkAll(val){
	jQuery(".onecheck").attr("checked",val);
}
</script>


</head>

<#include "/page/common/_paginate_new.ftl" />
<body>

	
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="javascript:top.location='/';">首页</a></li>
    <li><a href="#">通用查询</a></li>
    </ul>
    </div>
    
    <div class="forminfo" style=" padding: 8px;">
    
     <span>输入/选择 查询名称: <span style="color:red">注意：所有查询都做了最多100条的限制,如果当前进行批量的查询 数据可能不准，原则上不允许批量查询!!!</span></span> 
    
       <input style="width:325px; "  type="text" id="demo" value="输入/选择 查询名称" />
    </div>
    
    
    <div class="rightinfo" style="max-height:450px;overflow-y: scroll;">
 
    
   
    
 
    
    <table class="tablelist" >
    	<thead>
    	<tr>
			<th title="">id</th>
	        <th style="color:red">操作</th>
			<th title="">历史</th>
			<th title="查询描述">de</th>
    	
        </tr>
        </thead>
        <tbody>
        
        
        
       	<#list ql as q>
        <tr>
       
			<td style="text-align:center;"><@withSub text="${q.id!}" len=8 /></td>
       		<td>
       		<input type="radio" name="_query_id" onclick="prex(this,'${q.n!}','${q.id!}')" id="radio_${q.id!}">
       		<a href="javascript:void(0);" onclick="jQuery(this).prev().click();" class="tablelink">${q.n!}</a>
       		</td>
       		<td><a href="/query/his?qid=${q.id!}" target="_blank">查看执行历史（已执行${q.clc!}次）</a></td>
			<td style="display:none;">
				<span style="display:none;" class="target_sql">${q.sql!}</span>
			</td>
			<td style="text-align:center;"><@withSub text="${q.de!}" len=8 /></td>
       
       
        </tr> 
		</#list>
          
          
            
        </tbody>
    </table>
    
   
   
    </div>
    
   <div id="pre_div" style="display:none;">
	   <div class="place">
	    <span>请填写执行条件：</span><span id="pre_name" style="color:red; "></span> <span id="pre_sql_id" style="color:red;display:none;"></span>
	   </div>
       
       
       
       	<ul class="forminfo" id="pre_con">
				 
				
		
				
		</ul>
	 
       <li>
			<label>&nbsp;</label>
			<input value="提交" class="btn" type="submit" onclick="ex(this);">
		</li>
       
    <div>
    
    
    
    <li id="pre_one" style="display:none;"><label class="pre_lable" style="width: 200px;">n</label><input class="dfinput" name="demo" value="" type="text">
		<i class="pre_tip">(角色名称)</i>
	</li>
    
    
    
    <br>
    <br>
    
    
    
 
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100'>暂无数据</td></tr>")}
	
	
	function prex(el,n,id){
		jQuery("#pre_con").empty();
		var sql=jQuery(el).parents("tr").find(".target_sql").html();
		jQuery("#pre_div").show();
		n=n+"||"+sql;
		jQuery("#pre_name").html(n);
		jQuery("#pre_sql_id").html(id);
		
		
		var wh=sql.split(/where/ig)[1];
		var ns=wh.split(/and/ig);
		for(var i in ns){
			var o=ns[i];
			o=o.split("=")[0];
			o=peon.tools.string.trim(o);
			
			if(o.indexOf("?")==-1&&o.indexOf(" ")==-1){
				var tip=""
				var xx=jQuery("#pre_one").clone().show().removeAttr("id");
				xx.find(".dfinput").attr("name","where."+o);
				xx.find(".pre_lable").html(o);
				xx.find(".pre_tip").html(tip);
				jQuery("#pre_con").append(xx);
				
			}
		}
		var data={id:id};
		jQuery.post("/query/prewhere", data ,function(d){
			var qol=d.qol;	
			
			for(var qoi in qol){
				var qo=qol[qoi];
				var inp=jQuery("#pre_con .dfinput[name='where."+qo.f+"']");
				
				console.log("#pre_con .dfinput[name='where."+qo.f+"']");
				inp.prev().html(qo.f+"("+qo.n+")");
				
				var selx=jQuery("<select  class='dfinput' name='where."+qo.f+"'></select>");
				
				if(!qo.j){
					break;
				}
				
				var xx=eval("("+qo.j+")");
				for(var xi in xx){
					selx.append("<option value='"+xi+"'>"+xx[xi]+"</option>");
				}
				
				inp.replaceWith(selx);
				 
			}
			
			
		});
				
	}
	
	function ex(){
		$("#cxjg").css("color","black");
		var id=jQuery("#pre_sql_id").html();
		var inps=jQuery("#pre_con .dfinput[name]").get();
		var sx=[];//记录参数顺序
		for(var ii in inps){
			var o=inps[ii];
			if(o.value+""<=0){
				alert("提交查询前必须先给定:"+jQuery(o).parents("li").find(".pre_lable").text()+"的值!");
				return;
			}
			sx.push(jQuery(o).parents("li").find(".dfinput").attr("name").replace("where.",""));
		}
		
		var data=jQuery("#pre_con").serializedom().json;
		data.id=id;
		data.sx=sx.join(",");
		jQuery.post("/query/staticex", data ,function(d){
		
		if(d.msg){
				alert(d.msg);
			}
		
		
			$("#cxjg").css("color","red");
			
			var sql_fs=d.sql_fs;
			var q_rl=d.q_rl;
			var qol=d.qol;
			
			jQuery("#ex_res").show();
			jQuery("#ex_res_title").empty();
			jQuery("#ex_res_record").empty();
			
			for(var i in sql_fs){
				
				var ff=sql_fs[i];
				
				for(var qoi in qol){
					var qo=qol[qoi];
					if(ff==qo.f){
						ff+="("+qo.n+")"
					}
				}
				
				jQuery("#ex_res_title").append('<th title="">'+ff+'</th>');
				
					//ex_res_record">
					//<td style="text-align:center;">  </td>
			}
			for(var x in q_rl){
				var o = q_rl[x];
				var obb=jQuery("<tr></tr>");
				for(var i in sql_fs){
					var v=o[sql_fs[i]+""];
					if(sql_fs[i] == "id"){
						v=o["id"];
					}
					
					
					var ff=sql_fs[i];
					var jsons="";
					for(var qoi in qol){
						var qo=qol[qoi];
						
						if(!qo.j){
							break;
						}
						
						if(ff==qo.f){
							console.log(qo.j)
							var xx=eval("("+qo.j+")");
							jsons=qo.j;
							v=v+"("+xx[v]+")";
						}
					}
					
					var xo=jQuery("<td>"+v+"</td>");
					xo.attr("title",jsons);
					xo.appendTo(obb);
				}
				
				
				jQuery("#ex_res_record").append(obb);
			}
			
		});
		
		$('html,body').animate({scrollTop: '600px'}, 800);
	}
	
	
	jQuery(function(){
	
		jQuery("[name='_query_id']").first().click();//初始化点击第一个
	});
	</script>






		<div class="place" id="cxjg">
	    <span>查询结果：</span>
	    <ul class="placeul">
	    </ul>
	    </div>
	    
	    <div class="rightinfo" id="ex_res" style="display:none;">
	 
	   
	    <table class="tablelist" >
	    	<thead>
	    	<tr id="ex_res_title">
	        </tr>
	        </thead>
	        <tbody id="ex_res_record">
	        
	          
	          
	            
	        </tbody>
	    </table>
	    	
   
    </div>





</body>

</html>
