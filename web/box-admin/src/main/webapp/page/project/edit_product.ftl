<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  	<#include "/page/common/base/include.ftl">
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/project">项目管理</a></li>
        <li><a href="/project/productSet/${product.project_id!}">回报设置</a></li>
        <li><a href="/project/goEditProduct/${product.id}">编辑回报</a></li>
    </ul>
</div>
    
<div class="formbody">
    
  	<div class="tabson">
    <ul class="forminfo">
    	<li>
    		<label>回报类别<b>*</b></label>
    		<cite>
    		<#if product.type ?? && product.type = 0 >
    			<input type="radio" value="0" name="product.type" checked/>
    		<#else>
    			<input type="radio" value="0" name="product.type"/>
    		</#if>
    		实物回报&nbsp;&nbsp;
    		<#if product.type ?? && product.type = 1 >
    			<input type="radio" value="1" name="product.type" checked/>
    		<#else>
    			<input type="radio" value="1" name="product.type"/>
    		</#if>
    		虚拟物品回报（电子码等）
   	    	</cite>
   	    </li>
   
	    <li>
	    	<label>金额<b>*</b></label>
	    	<input type="text" class="dfinput" name="product.fund" value="${product.fund!}"/>&nbsp;&nbsp;&nbsp;元（保留2位小数）
	    </li>
	    
	    <li>
	    	<label>内容<b>*</b></label>
	    	<textarea name="product.desc" cols="" rows="" class="textinput">${product.desc!}</textarea>
	    </li>
	    
	     <li>
	    	
		    	<label>说明图片<b>*</b></label>
		    	<cite>
		    		<input type="button" class="dfinput" style="width:100px" onclick="uploadPictureFun()" value="上传图片"/>
		    		<input type="file" style="display:none" id="id_fileName" name="fileName" onchange="uploadFileFun()"/>&nbsp;&nbsp;&nbsp;支持jpg,jpeg,png,gif(大小不超过500k,600*600)
		    	</cite>
	    	
	     </li>
	     
	     <li>
	    	<label>&nbsp;</label>
	    	<cite>
	    		<input type="hidden" name="product_img" value="${product.picture!}"/>
	    		<div style="width:151px;height:151px;border:1px solid;margin-left:85px"><img src="${picture_url!}" width="150" height="150" id="product_img"></div>
	     	</cite>
	     </li>
	     
	     <li>
	    	<label>限定名额<b>*</b></label>
	    	<input type="text" class="dfinput"  name="product.totalnum"  value="${product.totalnum!}"/>&nbsp;&nbsp;0为不限定名额 
	     </li>
	    
	    <li>
	    	<label>运费<b>*</b></label>
	    	<input type="text" class="dfinput"  name="product.postage"  value="${product.postage!}"/>&nbsp;&nbsp;0元为包邮
	     </li>
	    <!--
	    <li>
	    	<label>发票<b>*</b></label>
	    	<cite>
	    	<#if product.is_invoice ?? && product.is_invoice = 0>
    			<input type="radio" value="0" name="product.is_invoice" checked/>
    		<#else>
    			<input type="radio" value="0" name="product.is_invoice"/>
    		</#if>
    		不可开发票&nbsp;&nbsp;
    		<#if product.is_invoice ?? && product.is_invoice = 1>
    			<input type="radio" value="1" name="product.is_invoice" checked/>
    		<#else>
    			<input type="radio" value="1" name="product.is_invoice"/>
    		</#if>
    		可开发票（包括个人发票和自定义抬头发票）
   	    	</cite>
	     </li>
	     -->
	     <li>	
	    	<label>请写备注信息<b></b></label>
	    	<input type="text" class="dfinput"  name="product.remark"  value="${product.remark!}"/>	
	     </li>
	     
	     <li>	
	    	<label>回报时间<b>*</b></label>
	    	项目结束后<input type="text" class="dfinput" style="width: 100px;"  name="product.send_days" value="${product.send_days!}"/>&nbsp;&nbsp;天，将会向支持者发送回报	
	     </li>	
    
   		 <li>
   		 	<label>&nbsp;</label>
   		 	<input name="" type="button" class="btn" id="btn_product" value="提交" onclick="editProductFun(${product.project_id!},${product.id!})"/>&nbsp;&nbsp
   		 </li>
    </ul>
    </div> 
    
	</div> 
 
    </div>

<script type="text/javascript" src="/static/js/common/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/static/js/page/project/edit_product.js?v=20151114"></script>
<script type="text/javascript" src="/static/js/common/jquery/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/static/js/common/jquery/jquery-browser.js"></script>
</body>

</html>
