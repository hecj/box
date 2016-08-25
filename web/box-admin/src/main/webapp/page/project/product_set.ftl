<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  	<#include "/page/common/base/include.ftl">
    <script type="text/javascript" src="/static/js/common/jquery/jquery.form.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/project">项目管理</a></li>
        <li><a href="/project/productSet/${project.id!}">回报设置</a></li>
    </ul>
</div>
    
<div class="formbody">
    
    <div id="usual1" class="usual">
		<div>    	
    		项目名称：<label>${project.name!}</label>&nbsp;&nbsp;&nbsp;
    		筹款金额：<label>${project.fundgoal!}</label>&nbsp;元&nbsp;&nbsp;&nbsp;
    		发布人：<label>${proUser.nickname!}</label>&nbsp;&nbsp;&nbsp;
    		手机号：<label>${proUser.phone!}</label>
    		
    	</div>
    	<br>
    <div class="itab">
  	<ul> 
    	<li><a href="#tab1" class="selected">回报列表</a></li> 
    	<li><a href="#tab2">添加回报</a></li> 
  	</ul>
    </div> 
    
  	<div id="tab1" class="tabson">
	    <table class="tablelist">
	    	<thead>
		    	<tr>
			        <th>编号</th>
			        <th>产品Id</th>
			        <th>回报类型</th>
			        <th>描述</th>
			        <th>众筹金额（元）</th>
			        <th>总人数</th>
			        <th>邮费（元）</th>
			        <th>回报时间（天）</th>
			        <th>操作</th>
		        </tr>
	        </thead>
	        <tbody>
	        <#if productList ??>
	        <#list productList as p>
		        <tr>
			        <td>${p.id!}</td>
			        <td>${p.project_id!}</td>
			        <td>
			        	<#if p.type?? && p.type == 0>
			        		实物回报
			        	<#else>
			        		虚拟物品回报
			        	</#if>
			        </td>
			        <td title="${p.desc!}">
			        	<#if p.desc??>
	                		<#if p.desc ? length lt 20>
	                			${p.desc}
	                		<#else>
	                			 ${p.desc?substring(0,20)}...
	                		</#if>
	                	</#if>
			        </td>
			        <td>${p.fund!}</td>
			        <td>${p.totalnum!}</td>
			        <td>${p.postage!}</td>
			        <td>${p.send_days!}</td>
			        <td><a onclick="delProduct(${project.id!},${p.id!})" class="tablelink">删除</a>&nbsp;&nbsp;
			        <a href="/project/goEditProduct/${p.id}" class="tablelink">编辑</a></td>
		        </tr>
		    </#list> 
		    </#if>
	        </tbody>
	    </table>
    </div>  
    
    
  	<div id="tab2" class="tabson">
    <ul class="forminfo">
    	<li>
    		<label>回报类别<b>*</b></label>
    		<cite>
    		<input type="radio" value="0" name="product.type"/>实物回报&nbsp;&nbsp;
    		<input type="radio" value="1" name="product.type"/>虚拟物品回报（电子码等）
   	    	</cite>
   	    </li>
   
	    <li>
	    	<label>金额<b>*</b></label>
	    	<input type="text" class="dfinput" name="product.fund"/>&nbsp;&nbsp;&nbsp;元（保留2位小数）
	    </li>
	    
	    <li>
	    	<label>内容<b>*</b></label>
	    	<textarea name="product.desc" cols="" rows="" class="textinput"></textarea>
	    </li>
	    
	     <li>
	    	
	    	<form id="uploadFileForm" action="/project/uploadProductPicture" method="post" enctype="multipart/form-data">
		    	<label>说明图片<b>*</b></label>
		    	<cite>
		    		<input type="button" class="dfinput" style="width:100px" onclick="uploadPictureFun()" value="上传图片"/>
		    		<input type="file" style="display:none" id="id_fileName" name="fileName" onchange="uploadFileFun()"/>&nbsp;&nbsp;&nbsp;支持jpg,jpeg,png,gif(大小不超过500k,600*600)
		    	</cite>
	    	</form>
	    	
	     </li>
	     <li>
	    	<label>&nbsp;</label>
	    	<cite>
	    		<input type="hidden" name="product_img"/>
	    		<div style="width:151px;height:151px;border:1px solid;margin-left:85px"><img width="150" height="150" src="/" id="product_img"></div>
	     	</cite>
	     </li>
	     <li>
	    	<label>限定名额<b>*</b></label>
	    	<input type="text" class="dfinput" value="0" name="product.totalnum"/>&nbsp;&nbsp;0为不限定名额 
	     </li>
	    
	    <li>
	    	<label>运费<b>*</b></label>
	    	<input type="text" class="dfinput"  value="0" name="product.postage"/>&nbsp;&nbsp;0元为包邮
	     </li>
	    <!--
	    <li>
	    	<label>发票<b>*</b></label>
	    	<cite>
    		<input type="radio" value="0" name="product.is_invoice"/>不可开发票&nbsp;&nbsp;
    		<input type="radio" value="1" name="product.is_invoice"/>可开发票（包括个人发票和自定义抬头发票）
   	    	</cite>
	     </li>
	     -->
	     <li>	
	    	<label>请写备注信息<b></b></label>
	    	<input type="text" class="dfinput"  value="" name="product.remark"/>	
	     </li>
	     
	     <li>	
	    	<label>回报时间<b>*</b></label>
	    	项目结束后<input type="text" class="dfinput" style="width: 100px;"   value="0" name="product.send_days"/>&nbsp;&nbsp;天，将会向支持者发送回报	
	     </li>	
    
   		 <li>
   		 	<label>&nbsp;</label>
   		 	<input name="" type="button" class="btn" id="btn_product" value="保存" onclick="addProductFun(${project.id!})"/>&nbsp;&nbsp
   		 	<input name="" type="button" onclick="resetFormFun()" class="btn" value="重置"/>
   		 </li>
    </ul>
    </div> 
	</div> 
    </div>
    <script type="text/javascript" src="/static/js/page/project/product_set.js?v=20151218"></script>
    <script type="text/javascript" src="/static/js/common/jquery/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/js/common/jquery/jquery-browser.js"></script>
    
</body>

</html>
