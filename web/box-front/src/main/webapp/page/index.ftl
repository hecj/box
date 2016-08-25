<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta http-equiv="X-UA-Compatible" content="edge" />
	<title>盒子众筹-中国最具影响力的创业众筹融资平台</title>
	<!--腾讯QQ验证标签-->  <meta property="qc:admins" content="${qq_lable!}" />
	<!--新浪微博验证标签--><meta property="wb:webmaster" content="${weibo_lable!}" />
	<#include "/page/common/base/include.ftl">
</head>
<body class="bg_color">
   <!-- 头部引用  -->
	<div class="top">
		<div class="top-in">
			<ul class="top-left">
				<li>
					<a class="top-logo" href="#"><img src="static/images/icon/logo.png" alt="" />
					</a>
				</li>
				<!-- <li><a class="top-font top-list" href="#">盒子·众筹</a></li> -->
				<li><a class="logo-left" href="/project">众筹项目</a></li>
			</ul>
			
			<#if user??>
				<div class="top-right">
					<a href="/sub/mysub" class="top-p">
						<img src="${user.picture!}" alt="" class="top-photo" />
						<span class="top-circle"></span>
						<#if (user.messageCount>0) >
							<a href="/personal/mySystemMessage">
								<i class="top-photo-num">${user.messageCount!0}</i>
							</a>
						</#if>
					</a>
					<span class="icon"></span>
					<ul class="top-more">
						<li class="nickname"><a href="/sub/mysub" id="nickname">${user.nickname!}</a></li>
						<li><a href="/orders/">我的支持</a></li>
						<li><a href="/projectapply/myapply">我的发起</a></li>
						<li><a href="/personal/getPersonalDetail">账号设置</a></li>
						<li><a href="javascript:;" class="feedback">意见反馈</a></li>
						<li class="exitsign"><a href="/logOut">退出登录</a></li>
					</ul>
				</div>
			<#else>
				<div class="cf-login">
					<i></i>
					<a href="javascript:;" onclick="loginApp.login('/')">登录</a>
				</div>
			</#if>
			
		</div>
	</div>
   <!-- 内容start！ -->
	<div class="content bg_wrap">
		<div class="cf-index-con-wrap">
		    <div class="cf-index-con">
		    	<div class="cf-index-row">
			       <div class="cf-index-row-first">
			       	    <div class="cf-index-row-first-l floatL">
			       		 <div id="banner_tabs" class="flexslider">
					      <ul class="slides">
					      		<#if messageConfigList ??>
					      		<#list messageConfigList as mc>
						        <li>
						          <#if mc.action ??>
						          	<a href="${mc.action!}" target="_blank">
						          		<!--  title title="${mc.title!}".. -->
						    			<img alt="" src="${mc.logo!}" />
						          	</a>
						          <#else>
						          	<a href="" >
						          		<!-- title="${mc.title!}" -->
						    			<img alt="" src="${mc.logo!}" />
						          	</a>
						          </#if>
						        </li>
						        </#list>
						        </#if>
					      </ul>
					      <ul class="flex-direction-nav">
						        <li><a class="flex-prev" href="javascript:;">Previous</a></li>
						        <li><a class="flex-next" href="javascript:;">Next</a></li>
					      </ul>
					      <ol id="bannerCtrl" class="flex-control-nav flex-control-paging">
					      	<#if messageConfigList ??>
					      		<#list messageConfigList as mc>
						        <li><a></a></li>
						        </#list>
						    </#if>
					      </ol>
					        </div>
			       	    </div>	
				       	<div class="cf-index-row-first-r floatL">
				       	  <div class="cf-index-row-first-r-top cf-index-row-first-r-item">
				       	  	  <h1>
				       	  	  	<a href="javascript:;">
				       	  	  		<img src="static/images/icon/cf-index-logo.png" alt="" />
				       	  	  	</a>
				       	  	  </h1>
				       	  	  <p><a href="javascript:;">点击去社区转转</a></p>
				       	  </div> 			       	  
				       	  <div class="cf-index-row-first-r-bottom cf-index-row-first-r-item">
					       	  	<div class="cf-index-release">
					       	  		<p><a href="javascript:;" onclick ="loginApp.verifyLogin('/projectapply/agreement')" >众筹有我</a></p>
					       	  		<p><a href="javascript:;" onclick ="loginApp.verifyLogin('/projectapply/agreement')" >发布项目</a></p>
					       	  	</div>
				       	  </div> 
				       	</div>
			       </div>
			       <div class="cf-index-row-second">
				       	<div class="cf-index-row-second-l cf-index-reminder01 floatL">
				       		<img src="${STATIC_URL!}<#if recommProject??>${recommProject.cover_image!}</#if>" alt="" class="cf-index-reminder-img01" />
				       		<div class="shade-01"></div>
				       		<div class="recommend recommend01 ">
				       			<div class="recommend-title">
				       				<span>推荐</span>
				       				<a href="/project/detail/<#if recommProject??>${recommProject.id!}</#if>"  target="_blank"><#if recommProject??>${recommProject.name!}</#if></a>
				       				<div class="recommend-details recommend-details01">
				       					<a href="/project/detail/<#if recommProject??>${recommProject.id!}</#if>"  target="_blank">点击查看</a>
				       				</div>
				       			</div>
				       			<div class="recommend-news recommend-news01">
				       				<div class="item-show-one-details">
			   	     				    
			   	     				    	<div class="address-02">
			   	     				    		<div class="address-details floatL">
				   	     				    		<i></i>
				   	     				    		<span class="address-big"><#if recommProject??>${recommProject.provincesName!}</#if></span>
			   	     				    	    </div>
			   	     				    	    <div class="time-details floatR">
			   	     				    		  <i></i>
			   	     				    		
			   	     				    	    </div>
			   	     				    	</div>	
			   	     				    	<div class="address-02 address-02-mt">
			   	     				    		<div class="address-details floatL">
				   	     				    		<div class="progress">
				   	     				    			<div class="progress-in" style="width:<#if recommProject??>${recommProject.progress!}</#if>%">
				   	     				    				<div class="progress-inner"></div>
				   	     				    			</div>
			   	     				    	        </div> 
			   	     				    	    </div>
			   	     				    	    <div class="time-details floatR">
			   	     				    		  <span><#if recommProject??>${recommProject.beLeftDays!}</#if>天</span>		   	     				    		
			   	     				    	    </div>
			   	     				    	</div>
			   	     				</div>
			   	     				<div class="describe">
			   	     				    		<span><b>已达</b><#if recommProject??>${recommProject.progress!}</#if>%</span>
			   	     				    		<em><b>已筹</b>¥<#if recommProject??>${recommProject.fundnowFormat!}</#if></em>
			   	     				</div>
				       			</div>
				       		</div>
				       	</div>
				       	<div class="cf-index-row-second-r floatL">
				       		<div class="top-search">
								<input type="text" id="searchText"/>
								<span>搜索...</span>
								<button id="btnSearch" onclick="funSelect()">Go</button>
								<i></i>
							</div>
							<div class="other-item">
								<ul>
									<#if projectTypeList ??>
										<#list projectTypeList as pt>
											<#if projectTypeList?size == (pt_index+1) >
												<li class="floatL"><a href="/project?category=${pt.id!}">${pt.name!}&nbsp;&nbsp;</a></li>
											<#else>
												<li class="floatL"><a href="/project?category=${pt.id!}">${pt.name!}&nbsp;&nbsp;|</a></li>
											</#if>
										</#list>
									</#if>
								</ul>
							</div>
				       	</div>
			       </div>
			     
		       </div>
		    </div>
		    <div class="last-itemrow">
		    	
		  		<div class="cf-index-row-third">
		  			 <#if newProjectList ??>
		  			   	  <#list newProjectList as pl>
				       	  <div class="cf-index-row-item cf-index-row-item02 floatL">
				       	  	<img src="${STATIC_URL!}${pl.cover_image!}" alt="" />
				       	  	
				       	  	<!--判断加载的动画效果-->
				       	  	<#if pl_index == 0 >
				       	  		<div class="shade-02"></div>
				       	  	<#elseif pl_index == 1>
				       	  		<div class="shade-02 shade-03"></div>
				       	  	<#elseif pl_index == 2>
				       	  		<div class="shade-02 shade-04"></div>
				       	  	<#elseif pl_index == 3>
				       	  		<div class="shade-02 shade-05"></div>
				       	  	</#if>
				       	  	
				       	  	<#if pl_index == 2 >
				       	  	<div class="cf-index-item-details item-details-color">
				       	  	<#else>
				       	  	<div class="cf-index-item-details">
				       	  	</#if>
					   	  	 	<div class="cf-index-item-title">
					      	  		<h2><a href="/project/detail/${pl.id!}"  target="_blank">${pl.name!}&nbsp;</a></h2>
					      	  			<div class="recommend-details recommend-details02" >
					      				     <a href="/project/detail/${pl.id!}"  target="_blank"> 点击查看</a>
					      				</div>
					            </div>
					            <div class="itemB-detils-wrap">
					            	<div class="address-02">
				   	     				    		<div class="address-details floatL">
					   	     				    		<i></i>
					   	     				    		<span class="address-small">${pl.provincesName!}</span>
				   	     				    	    </div>
				   	     				    	    <div class="time-details floatR">
				   	     				    		  <i></i>
				   	     				    		
				   	     				    	    </div>
				   	     			</div>
				   	     			<div class="address-02 address-02-mt">
				   	     				    		<div class="address-details floatL">
					   	     				    		<div class="progress">
					   	     				    			<div class="progress-in" style="width:${pl.progress!}%">
					   	     				    				<div class="progress-inner"></div>
					   	     				    			</div>
				   	     				    	        </div> 
				   	     				    	    </div>
				   	     				    	    <div class="time-details floatR">
				   	     				    		  <span>${pl.beLeftDays!}天</span>		   	     				    		
				   	     				    	    </div>
				   	     			</div>
				   	     			<div class="describe describe-notop">
				   	     				    		<span><b>已达</b>${pl.progress!}%</span>
				   	     				    		<em><b>已筹</b>¥${pl.fundnowFormat!}</em>
				   	     				</div>
					            </div>
				            </div>
				       	  </div>
				       	  </#list>
					</#if>
				</div>
			
		  </div> 
	    </div>
	</div>

	<div class="pay-way-wrap" style="display:none"></div>
	<div class="feedback-box" style="display:none">
		<div class="feedback-head">
			   <h6>意见反馈</h6>
			   <a class="feedback-close"></a>
		</div>
		<div class="feedback-con">
			<textarea class="feedbackTextArea"></textarea>
			<span class="user_msg01">有什么不好用的地方，请告诉我们，我们会努力做的更好！</span>
			<div class="position_rel">
				<input type="text" class="feedback-txt"/>
				<span class="user_msg02">您的联系方式，电话／qq／邮箱（选填）</span>
			</div>
			<p class="feedback-err-msg">请输入意见反馈内容！</p>
			<div class="feedback-submit-wrap">
			   <input type="submit" value="提交" class="feedback-submit" id="btn_submit"/>
			    <div class="feedback-submit-hover"></div>
		    </div>
		</div>
	</div>
	
	
	<div class="feedback-success" style="display:none">
		<div class="feedback-head">
			   <h6>提示</h6>
			   <a href="javascript:;" class="feedback-close feedback-close-small">X</a>
		</div>
		<div class="feedback-success-con">
			<p>反馈提交成功！</p>
		</div>
		<div class="feedback-submit-wrap">
			<input type="submit" id="up_submit" value="确定" class="feedback-submit feedback-submit-small">
		</div>
	</div>
		
	<div id="WEBROOT_URL" style="display:none">${WEBROOT_URL!}</div>
	<#include "/page/common/foot.ftl">
	<script type="text/javascript" src="/static/js/DD_roundies_min.js"></script>
	<script type="text/javascript" src="/static/js/index/slider.js"></script>
	<script type="text/javascript" src="/static/js/index/index.js"></script>
	<script type="text/javascript" src="/static/js/index/sHover.min.js"></script>
	<script type="text/javascript" src="/static/js/index/tipso.min.js"></script>
	<script type="text/javascript" src="/static/js/index/cf-index.js"></script>
	<!-- <script type="text/javascript" src="/static/js/page/login.js"></script> -->
	<script type="text/javascript">
		DD_roundies.addRule('border-raduis', '100%', true);
	</script>
</body>
</html>