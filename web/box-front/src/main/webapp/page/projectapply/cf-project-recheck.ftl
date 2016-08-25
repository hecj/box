<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>项目复审</title>
	<link rel="stylesheet" type="text/css" href="/static/css/public.css?v=20151112" />
	<link rel="stylesheet" type="text/css" href="/static/css/content.css?v=20151124" />
	<!--此处引入1.7.1为了解决上传图片不兼容IE9,IE10问题-->
	<script type="text/javascript" src="/static/js/common/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/static/js/common/common.js?v=20151202"></script>
	<script type="text/javascript" src="/static/js/top.js"></script>
	<script type="text/javascript" src="/static/js/login_common.js?v=20151123"></script>
	<script type="text/jaavscript" src="/static/js/common/jquery/jquery.cookie.js"></script>
	<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
	
	<script type="text/javascript">
		var webApp = webApp || {};
		webApp.WEBROOT_URL = "${WEBROOT_URL!}";
		webApp.STATIC_URL = "${STATIC_URL!}";
		webApp.wechat_AppID = "${wechat_AppID!}";
		webApp.wechat_redirect_URI = "${wechat_redirect_URI!}";
	</script>

<body>
<style>
   .ke-container{
   width:570px!important;}
</style>
	<!-- 头部引用 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="project-trial-content bg_wrap">
		<div class="flow-chart flow-chart01"></div>
		<div class="pt-content-in">
			<div class="ptc-form ptc-form2">
				<form method="post" action="">
					<dl class="title title-lab clearfix">
						<dt>选择类别：</dt>
						<#if projectCategoryList ??>
						<#list projectCategoryList as pc>
							 <#if project ?? && project.category_id ?? && project.category_id == pc.id>
								<label class="floatL current radioc">
						          	<input type="radio" name="project.category_id" value="${pc.id!}" checked />
						          	<span class="radio-color"></span>
						          	<span>${pc.name!}</span>
						        </label>
					        <#else>
					        	<label class="floatL radioc">
						          	<input type="radio" name="project.category_id" value="${pc.id!}" />
						          	<span class="radio-color"></span>
						          	<span>${pc.name!}</span>
						        </label>
					        </#if>
				        </#list>
				       </#if>
					</dl>
					
					<dl class="title clearfix">
						<dt>项目标题：</dt>
						<dd>
							<!--填写项目名称不超过25个字-->
							<#--
							<#if project.status == 3>
								<input type="text" value="${project.name!}" name="project.name" class="word_limit" style="color:#000" />
							<#else>
								<input type="text" value="${project.name!}" name="project.name" class="word_limit" style="color:#000" />
							</#if>
							-->
							<input type="text" value="${project.name!}" name="project.name" class="word_limit" style="color:#000"/>
							<div class="text_r">
								<div class="show_red">
							     <i></i>
							     <div class="reminder-con"><b></b><span>项目标题不能为空</span><em></em></div>
								</div>
							<span id="nameNum">
							<#if project.status == 3>
								${project.aname?length}/25
							<#else>
								${project.name?length}/25
							</#if>
							</span>
							</div>
						</dd>
					</dl>
					
					<dl class="description clearfix">
						<dt>项目描述：</dt>
						<dd class="description-min-height">
							<#if project.status == 3>
								<textarea maxlength="40" name="project.desc" class="desc_limit" ></textarea>
							<#else>
								<textarea maxlength="40" name="project.desc" class="desc_limit">${project.desc!}</textarea>
							</#if>
							<div class="text_r">
								<div class="show_red">
							     <i></i>
							     <div class="reminder-con"><b></b><span>项目描述不能为空</span><em></em></div>
								</div>
								<span id="descNum">
								<#if project.status == 3>
									0/40
								<#else>
									<#if project.desc ??>
										${project.desc?length}/40
									<#else>
										0/40
									</#if>
								</#if>
								</span>
							</div>
						</dd>
					</dl>
					
					<dl class="default-word clearfix">
						<dt class="floatL">筹款天数：</dt>
						<dd class="error-msg">
							<#if project.days ?? && project.days == 0>
								<input type="text" name="project.days" class="floatL error-show-input" value="1~60" />
							<#else>
								<input type="text" name="project.days" class="floatL error-show-input" value="${project.days!}" />
							</#if>
							<span>天</span>
							<div class="show_green">
								<i name="question"></i>
								<div class="reminder-con"><b></b><span>可填写1-60天，30天效果最佳，我们推荐30-50天!</span><em></em></div>
							</div>	
						
							<div class="show_red show_red1">
							     <i></i>
							     <div class="reminder-con"><b></b><span>筹款天数不能为空</span><em></em></div>
							</div>
							<div class="show_red show_red2">
							     <i></i>
							    <div class="reminder-con"><b></b><span>筹款天数为1~60天</span><em></em></div>
							</div>
							<div class="show_red show_red3">
							     <i></i>
							   
							      <div class="reminder-con"><b></b><span>请填写正确的筹款金额天数,只支持整数</span><em></em></div>
							</div>	
						</dd>
					</dl>	
					<dl class="default-word clearfix default-word-money">
						<dt class="floatL">筹款金额：</dt>
						<dd class="error-msg">
							<#if project.fundgoal ?? && project.fundgoal == 0>
								<input type="text" class="floatL money-input" name="project.fundgoal" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" value="不少于500元" />
							<#else>
								<input type="text" class="floatL money-input" name="project.fundgoal" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" value="${project.fundgoal!}" />
							</#if>
							<span>元</span>
							<div class="show_green">
								<i name="question"></i>
								<div class="reminder-con"><b></b><span>筹款金额不能少于500元,不能高于1亿元</span><em></em></div>
							</div>
							<div class="show_red show_red001">
							     <i></i>
							     <div class="reminder-con"><b></b><span>筹款金额不能为空</span><em></em></div>
							</div>
							<div class="show_red show_red002">
							     <i></i>
							     <div class="reminder-con"><b></b><span>请填写正确的筹款金额,只支持整数</span><em></em></div>
							</div>
                             <div class="show_red show_red003">
							     <i></i>
							     <div class="reminder-con"><b></b><span>筹款金额不能少于500元,不能高于1亿元</span><em></em></div>
							</div>
						</dd>
					</dl>	
					<dl class="default-word clearfix default-word-address">
						<dt class="floatL">项目地点：</dt>
						<dd>
							<select name="project.province">
								<option value="-1">请选择省份</option>
								<#if provincesList??>								
								<#list provincesList as pv>
									<#if project.province ?? && pv.provinceid = project.province>
									<option value="${pv.provinceid!}" selected = "selected">${pv.province!}</option>
									<#else>
									<option value="${pv.provinceid!}">${pv.province!}</option>
									</#if>
								</#list>
								</#if>
							</select>
							<select name="project.city" id="">
								<option value="-1">请选择</option>
								<#if citiesList??>	
								<#list citiesList as ct>
									<#if project.city ?? && ct.key = project.city>
										<option value="${ct.key!}" selected = "selected">${ct.value!}</option>
									<#else>
										<option value="${ct.key!}">${ct.value!}</option>
									</#if>
								</#list>
								</#if>
							</select>
						</dd>
						<div class="show_red">
							<i></i>
							<div class="reminder-con"><b></b><span>请选择项目地点</span><em></em></div>
						</div>
					</dl>	
					<dl class="default-word clearfix">
						<dt class="floatL">项目封面：</dt>
						<div class="floatL default-img" id="upload_cover_div">
							<dd class="position_rel">
								<span class="default-upload" onclick="upload_convert()">
									<em></em>
									上传封面</span>
									<input type="hidden" name="project.cover_image" value="${project.cover_image!}" />
									<!-- 隐藏file标签 -->  
									<input type="file" id="uploadFile" name="uploadFile" onchange="upload_convert_ajax()"/> 
								<div class="show_green">
									<i name="question"></i>
									<div class="reminder-con"><b></b><span>支持JPEG/PNG,大小不超过5M,最多上传1张<!-- 支持JPEG/PNG,大小不超过5M.建议尺寸:270pxX270px 最多上传1张 --></span><em></em></div>
								</div>
								<div class="show_red">
							     	<i></i>
							     	<div class="reminder-con"><b></b><span>请上传展示封面</span><em></em></div>
						        </div>
							</dd>
							<dt class="cover" style="display:none">
								<div class="img_one">
									<img src="${STATIC_URL!}${project.cover_image!}" alt=""/>
									
									<i></i>
								</div>
							</dt>
							
					    </div>
					</dl>	
					<dl class="default-word clearfix">
						<dt class="floatL">说明图片：
							
									
						</dt>
						<div class="floatL default-img" id="upload_images"/>
							<dd class="position_rel">
								<span class="default-upload" onclick="upload_images()">
									<em></em>
									上传说明图片</span>
									<input type="file" id="upload_images_File"  name="uploadFile" onchange="upload_images_ajax()"/>
								<div class="show_green">
									<i name="question"></i>
									<div class="reminder-con"><b></b><span>支持JPEG/PNG,大小不超过5M,最少2张,最多上传5张<!-- 支持JPEG/PNG,大小不超过5M.建议尺寸:270pxX270px 最多上传3张 --></span><em></em></div>
								</div>
								<div class="show_red">
								     <i></i>
								     <div class="reminder-con"><b></b><span>说明图片至少2张</span><em></em></div>
						        </div>
							</dd>
							
							<dt class="cover " id="cover_pic" style="display:none">
								<div class="img_one">
									<img src="${STATIC_URL!}${images0!}" alt="" id="images_0"/>
									<input type="hidden" name="input_images_0" value="${images0!}"/>
									<i></i>
								</div>
								<div class="img_one">
									<img src="${STATIC_URL!}${images1!}" alt="" id="images_1"/>
									<input type="hidden" name="input_images_1" value="${images1!}"/>
									<i></i>
								</div>
								<div class="img_one">
									<img src="${STATIC_URL!}${images2!}" alt="" id="images_2"/>
									<input type="hidden" name="input_images_2" value="${images2!}"/>
									<i></i>
								</div>
								<div class="img_one">
									<img src="${STATIC_URL!}${images3!}" alt="" id="images_3"/>
									<input type="hidden" name="input_images_3" value="${images3!}"/>
									<i></i>
								</div>
								<div class="img_one">
									<img src="${STATIC_URL!}${images4!}" alt="" id="images_4"/>
									<input type="hidden" name="input_images_4" value="${images4!}"/>
									<i></i>
								</div>
								<div class="img_one_only">
									 <p>还能再添加<span></span>张图片</p>
								</div>
							</dt>
					    </div>
					</dl>	
					
					
					<dl class="title clearfix title-video default-word ">
						<dt>项目介绍视频：</dt>
						<dd>
							<#if project.video ?? && project.video!="">
							<div class="position_rel">
							<input type="text" name="project.video" value="${project.video!}"/>
							<span class="msg-placeholder">(可选)输入视频地址(支持爱奇艺,腾讯,优酷,土豆,酷6,新浪,搜狐视频)仅支持.swf格式</span>
							</div>
							<#else>
							<div class="position_rel">
							<input type="text" name="project.video" value=""/>
							<span class="msg-placeholder">(可选)输入视频地址(支持爱奇艺,腾讯,优酷,土豆,酷6,新浪,ss搜狐视频)仅支持.swf格式</span>
							</div>
							</#if>
						</dd>
					</dl>
					<!-- 
					<dl class="title clearfix title-video default-word ">
						<dt>项目介绍视频：</dt>
						<dd><input type="text" name="project.video" value="${project.video!}"/></dd>
					</dl>
					-->
					<dl class="default-word clearfix default-word-money">
						<dt class="floatL">项目介绍：</dt>
						<dd class="unconfirmed" style="height:352px;border:none;margin:0px;margin-top:0px;">
							<div id="context" name="context" class="context" style="height:400px;background-color: white;color: black">
								<#if project.status == 3>
									<#include "/page/projectapply/content_template.ftl">
								<#else>
									${project.context!}
								</#if>
							</div>
						</dd>
						
					</dl>
					<dl class="submit clearfix">
						<dt><input class="left submit-btn" type="submit" value="" /></dt>
					</dl>
				</form>
			</div>
		</div>
	</div>

	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="/static/js/DD_roundies_min.js"></script>
    <script type="text/javascript" src="/static/js/common/upload/jupload-0.1.min.js"></script>
    <script type="text/javascript" src="/static/js/common/upload/ajaxfileupload.js?v=20151204"></script>
    <script type="text/javascript" src="/static/js/page/projectapply/cf-project-recheck.js?v=20151212"></script>
    <script type="text/javascript" src="/static/js/common/util/FileUtil.js"></script>
    <script type="text/javascript" src="/static/js/common.js?v=20151125"></script>
    <script type="text/javascript" src="/static/js/common/util/CommonUtil.js"></script>
    <link rel="stylesheet" href="/static/js/common/kindeditor-4.1.10/themes/default/default.css" />
    <script type="text/javascript" src="/static/js/common/kindeditor-4.1.10/kindeditor-all-min-plus.js"></script>
    <script charset="utf-8" src="/static/js/common/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script type="text/javascript">
		project.id = ${project.id!};
		$(".img_one_only").hide();
		
	</script>  
</body>
</html>