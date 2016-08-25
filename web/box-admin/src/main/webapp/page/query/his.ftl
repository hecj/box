<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>qh列表</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>

<script type="text/javascript">
jQuery(function(){
	<#if (VIEW_MSG_TYPE)?? >
	var op={msg:"${VIEW_MSG_MSG}"};
	peon.popx["${VIEW_MSG_TYPE}"](op);
	</#if>
});


function confirm_del(){
	var ids=[];
	var checkIds=jQuery(".onecheck:checked");
	if(checkIds.length==0){
		peon.popx.warn({msg:"请选择要删除的项！"});
		return;
	}
	checkIds.each(function(){
		ids.push(this.value);
	});
	var str='        <p>是否确认要删除当前选中的记录 ？</p>'+
			'        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>';
	var op={
		title:"操作提示",
		msg:str,
		confirm:function(v){v&&delByIds(ids);}
	};
	peon.popx.confirm(op);
}
function delByIds(ids){
	location="/qh/deleteByIds?id="+(ids.join("&id="));
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
    <li><a href="#">查询历史列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
        
        
        <ul class="toolbar1">
        <li><span><img src="/static/uimaker/images/t05.png" /></span>设置</li>
        </ul>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
    	<th ><input name="" type="checkbox" onclick="checkAll(this.checked,'')"/></th>
			<th title="">id</th>
			<th title="查询id">qid</th>
			<th title="查询执行时间">dt</th>
			<th   >参数</th>
			<th title="类型 0通用查询 1统计查询">t</th>
			<th title="查询完成时间">edt</th>
			<th title="生成文件路径">生成文件url</th>
    	
        </tr>
        </thead>
        <tbody>
        
        
        
       	<#list pg.getList() as qh>
        <tr>
       		<td><input class="onecheck" type="checkbox" value="${qh.id}" /></td>
       
			<td style="text-align:center;"><@withSub text="${qh.id!}" len=28 /></td>
			<td style="text-align:center;"><@withSub text="${qh.qid!}" len=28 /></td>
			<td style="text-align:center;"><@withSub text="${qh.dt!}" len=28 /></td>
			<td style="text-align:center;" ><span >${qh.pms}</span></td>
			<td style="text-align:center;" ><span command="qs">${qh.t}</span></td>
			<td style="text-align:center;"><@withSub text="${qh.edt!}" len=28 /></td>
			<td style="text-align:center;"  ><span command="sf" y='${qh.url!}' x='${qh.id!}'>${qh.t}</span><@withSub text="${qh.url!}" len=28 /></td>
       
       
        </tr> 
		</#list>
          
          
            
        </tbody>
    </table>
    
   
	<@paginate currentPage=pg.pageNumber totalPage=pg.totalPage totalRow=pg.totalRow actionUrl="/query/his?qid=${qid!}&page=" />
   
    </div>
    
    <script type="text/javascript">
    var qs={"0":"通用查询","1":"统计查询"};
    var sf={"0":"<a href='javascript:void(0);' onclick='rendrs2(this)'>查看结果</a>","1":"<a href='javascript:void(0);' onclick='rendrs(this)'>查看结果</a>"};
    function rendrs(el){
    	var id=jQuery(el).parent("span").attr("x");
    	jQuery.post("/query/getStaticUrl", {id:id} ,function(d){
		
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
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100'>暂无数据</td></tr>")}
	</script>


	<script>
		 function rendrs2(el){
	    	var nr=jQuery(el).parent("span").attr("y");
	    	
	    	if(!nr){
	    		alert("生成结果丢失！");return;
	    	}
	    	
	    	if(nr.msg){
	    		alert(nr.msg);return;
	    	}
	    	
	    	if(!nr.startsWith("{")){
	    		alert("结果内容因为超过长度 没有被存储!");return;
	    	}
	    	
			
			var d=eval("("+nr+")");
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
				
				$('html,body').animate({scrollTop: '600px'}, 800);
				
	    }
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
	 <div>


</body>

</html>
