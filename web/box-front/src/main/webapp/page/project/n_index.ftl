<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>众筹列表页</title>
    <#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 头部引用 -->
	<#include "/page/common/n_head.ftl">
	
	<!-- 内容开始 -->
	<div class="content bg_wrap" >
	    <div class="cf-list-con-wrap">
	     <div class="cf-list-con">
	   	   <div class="cf-list-top">
	   	     	<div class="cf-list-top-wrap">
			   	 	<div class="cf-list-item-top-one">
			   	 		<div class="cf-list-item-top-one_l floatL">
			   	 			<h3 class="floatL">项目筛选</h3>
			   	 			<div class="floatL cf-list-item-top-one_l_detail">
			   	 				<ul>
			   	 					 <li class="floatL" id='category-1'><a href="#" name="type" onclick="loadCategory(-1,${status!})">全部</a></li>
			   	 					 <#list projectTypeList as type>	
			   	 				     	<li class="floatL" id='category${type.id}'><a href="#" name="type" onclick="loadCategory(${type.id!},${status!})">${type.name!}</a></li>
			   	 				   	 </#list>
			   	 				     <div class="floatL cf-list-item-top-one_l-remain"></div>
			   	 				</ul>
			   	 			</div>
			   	 		</div>			   	 		
			   	 	</div>
			   	 	<div class="cf-list-item-top-one cf-list-item-top-one_bottom">
			   	 		<div class="cf-list-item-top-one_l floatL">
			   	 			<h3 class="floatL">项目进程</h3>
			   	 			<div class="floatL cf-list-item-top-one_l_detail">
			   	 				<ul>
			   	 					 <li class="floatL" id="status-1"><a href="#" name="status" onclick="loadStatus(-1,${category!})">所有项目</a></li>
			   	 					 <li class="floatL" id="status7"><a href="#" name="status" onclick="loadStatus(7,${category!})">预热</a></li>
			   	 					 <li class="floatL" id="status8"><a href="#" name="status" onclick="loadStatus(8,${category!})">众筹中</a></li>
			   	 				     <li class="floatL" id="status10"><a href="#" name="status" onclick="loadStatus(10,${category!})">已完成</a></li>			   	 				
			   	 				     <div class="floatL cf-list-item-top-one_l-remain"></div>
			   	 				</ul>			   	 		
			   	 			</div>
			   	 		</div>			   	 		
			   	 	</div>			   	 	
			   	</div>
			     <div class="click-up"></div>
			   	 <div class="click-down"><span></span></div>
			   	 <div class="click-hint">
			   	 	<span></span>
			   	 	<a href="javascript:;">更多选项</a>
			   	 </div>
	   	   </div>
		   	</div>
		   	<div class="cf-list-con-center">
		   	<div class="cf-list-top-center">
		   		 <#if category==-1>
		   	     <h3>推荐项目></h3>
		   	     <div class="scroll-only scorll">
		   	     <div class="item-show-wrap item-show-wrap-only">
		   	     	<div class="item-show">
		   	     		<ul>
		   	     			<#list recommList as recommend>
		   	     			<li class="floatL">			   	     	
		   	     				<div class="blur common-wh"><img src="${STATIC_URL!}${recommend.cover_image!}" alt=""/></div>
		   	     				<div class="item-show-one">
		   	     					 <div class="item-show-one-top">
			   	     					<a href="/project/detail/${recommend.id!}" target="_blank">
			   	     						<img src="${STATIC_URL!}${recommend.cover_image!}" alt="" />
			   	     					</a>
			   	     					<div class="show-prompt">
			   	     						<!--
			   	     					 	<div class="show-prompt-l floatL">
			   	     					 		<i></i>
			   	     					 		<span>${recommend.nickname!}</span>
			   	     					 	</div>
			   	     					 	-->
			   	     					 	<div class="show-prompt-r  floatR">
			   	     					 		<a href="/project/detail/${recommend.id!}" target="_blank">了解详情</a>
			   	     					 	</div>
			   	     					 </div>
		   	     				    </div>
		   	     				    <div class="item-show-one-bottom">
		   	     				    	<h3><a href="/project/detail/${recommend.id!}" target="_blank">${recommend.name!} </a></h3>
		   	     				    	<p>${recommend.desc!}</p>
		   	     				    	<div class="item-show-one-details">
		   	     				    		<div class="address-details floatL">
		   	     				    			<i></i>
		   	     				    			<span>${recommend.provincesName!} ${recommend.cityNames!}</span>
		   	     				    			<div class="progress">
		   	     				    				<div class="progress-in" style="width:<#if recommend.getProgress()??><#if recommend.getProgress() gte 100>100<#else>${recommend.getProgress()}</#if><#else>0</#if>%">
		   	     				    					<div class="progress-inner"></div>
		   	     				    				</div>
		   	     				    			</div>
		   	     				    		</div>
		   	     				    		<div class="time-details floatR">
		   	     				    			<i></i>
		   	     				    			<span>${recommend.beLeftDays!}天</span>
		   	     				    		</div>
		   	     				    	</div>
		   	     				    	<div class="describe">
		   	     				    		<span>已达:${recommend.progress!0}%</span>
		   	     				    		<em>已筹:¥${recommend.fundnow!}</em>
		   	     				    	</div>
		   	     				    </div>
		   	     				   
		   	     				</div>
		   	     			</li>
		   	     			</#list>
		   	     		</ul>
		   	     	</div>
		   	     </div>
		   	     	<div class="item-show-left"></div>
		   	     	<div class="item-show-right"></div>
		   	   </div>
		   	   </#if>
		    </div>
		    </div>
	    </div>
	    
	    <#if projectList.size() gt 0>
		  	<#list projectList?chunk(3) as pList>
		   
		    <div class="cf-list-con-wrap cf-list-con-wrap-list-bottom ">
		   		
		     	<div class="cf-list-con-center">
			   	<div class="cf-list-top-center">
			   	  <!-- 项目列表   -->		   	   
			   	     <div class="item-show-wrap">
			   	     	<div class="item-show">
			   	     		<#list pList as project>
			   	     		<ul> 
			   	     			<li class="floatL">
			   	     					<div class="blur common-wh"><img src="${STATIC_URL!}${project.cover_image!}" alt=""/></div>  	     					
			   	     				<div class="item-show-one">
			   	     					<div class="item-show-one-top">
				   	     					<a href="/project/detail/${project.id!}" target="_blank">
				   	     						<img src="${STATIC_URL!}${project.cover_image!}" alt=""/>
				   	     					</a>
				   	     					 <div class="show-prompt">
				   	     					 	<div class="show-prompt-r  floatR">
				   	     					 		<a href="/project/detail/${project.id!}" target="_blank">了解详情</a>
				   	     					 	</div>
				   	     					 </div>
	
			   	     				    </div>
			   	     				    <div class="item-show-one-bottom">
			   	     				    	<h3><a href="/project/detail/${project.id!}" target="_blank" >${project.name!}</a></h3>
			   	     				    	<p>${project.desc!}</p>
			   	     				    	<div class="item-show-one-details">
			   	     				    		<div class="address-details floatL">
			   	     				    			<i></i>
			   	     				    			<span>${project.provincesName!}  ${project.cityNames!}</span>
			   	     				    			<div class="progress">
			   	     				    				<div class="progress-in" style="width:<#if project.getProgress()??><#if project.getProgress() gte 100>100<#else>${project.getProgress()}</#if><#else>0</#if>%">
			   	     				    					<div class="progress-inner"></div>
			   	     				    				</div>
			   	     				    			</div>
			   	     				    		</div>
			   	     				    		<div class="time-details floatR">
			   	     				    			<i></i>
			   	     				    			<span>${project.getBeLeftDays()!}天</span>
			   	     				    		</div>
			   	     				    	</div>
			   	     				    	<div class="describe">
			   	     				    		<span>已达:${project.progress!0}%</span>
			   	     				    		<em>已筹:¥${project.fundnow!}</em>
			   	     				    	</div>
			   	     				    </div>		   	     				   
			   	     				</div>
			   	     			</li>
			   	     		</ul>
			   	     		</#list>
			   	     	</div>
			   	     </div>  
			    </div>
		        </div>
	        </div>
		   </#list>
		    <div class="page-wrap-sub">
				<div id="pagemyapply" class="page-div clearfix"></div>
			</div>
	    <#elseif selectText != "">
	    	<div class="loading-item" style="display:block">
	    		<p class="no-item-msg">很抱歉，您搜索的:<span>${selectText}</span>没有任何项目；</p>
	    	</div>
	    <#else>	
	    	<div class="loading-item" style="display:block">
	    	</div>
	    </#if>
	   
	    <!-- 分页..end -->
	    <!--尖叫社区 入口.. -->
	    <div class="wrap-entrance wrap-entrance-hide">
	    	<a href="javascript:;"></a>
	    </div>
	</div>
	<!-- 内容结束 -->
	
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
    <script type="text/javascript" src="/static/js/DD_roundies_min.js"></script>  
	<script type="text/javascript" src="/static/js/page/project/index.js"></script>
	<script type="text/javascript" src="/static/js/login_common.js"></script>
	<script type="text/javascript" src="/static/js/common.js?v=20151125"></script>
	<script type="text/javascript" src="/static/js/page/full_paging.js"></script>
	<script>
		if(${totalPage} > 1){
			$("#pagemyapply").createPage({
				pageCount:${totalPage},
				current:${pageNumber},
				backFn:function(p){
					console.log(p);
					location = "/project?page="+p+"&status="+${status}+"&category="+${category};
				}
			});
		}else{
			$(".no-item-msg").show();
		}
		
		
		var category = ${category!};
		var status = ${status!};					
		$("#category"+category).addClass("current").siblings().removeClass("current");					
	    $("#status"+status).addClass("current").siblings().removeClass("current");
		
		<!--  悬浮显示  -->
		$(".item-show-one-top").hover(function() {
			$(this).children('.show-prompt').show().parent().parent().siblings().children().children().children('.show-prompt').hide();
		}, function() {
		$(this).children('.show-prompt').hide().parent().parent().siblings().children().children().children('.show-prompt').hide();
		});

		<!-- 隔行变色  -->
		$(".cf-list-con-wrap-list-bottom:odd").css({
			background: '#fff'
		});

		$(".cf-list-con-wrap-list-bottom:even").css({
			background: '#fff'
		});
	
	</script>
    
	<script type="text/javascript">
		/*识别ie8.对ie浏览器进行*/
		var browser=navigator.appName;
		var b_version=navigator.appVersion;
		var version=b_version.split(";");
		var trim_Version=version[1].replace(/[ ]/g,"");
		if(browser=="Microsoft Internet Explorer"){
			    $(".scroll-only .item-show li").css({
			    	marginRight: '60px'	
			    });
			    $(".item-show li").css({
			    	marginRight: '60px'
			    });
			    $(".item-show-one img").css({
			    	width: '271px'
			    });
			     $(".item-show-one").css({
			    	width: '265px'
			    });
			    $(".show-prompt").css({
			    	width: '255px',
			    	height: '30px'
			    }); 
	}
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE9.0")
	 
	{
     $(".scroll-only .item-show li").css({
	    	marginRight: '60px'
	    });
	    $(".item-show li").css({
	    	marginRight: '60px'
	    });
	    $(".item-show-one img").css({
	    	width: '271px'
	    });
	     $(".item-show-one").css({
	    	width: '291px'
	    });
	   $(".show-prompt").css({
	    	width: '267px',
	    	height: '48px'
	    }); 
	}else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE10.0")
	{
     $(".scroll-only .item-show li").css({
	    	marginRight: '60px'
	    	
	    });
	    $(".item-show li").css({
	    	marginRight: '60px'
	    });
	    $(".item-show-one img").css({
	    	width: '271px'
	    });
	     $(".item-show-one").css({
	    	width: '291px'
	    });
	   $(".show-prompt").css({
	    	width: '267px',
	    	height: '48px'
	    }); 
	}
   /*对圆角进行兼容*/
	DD_roundies.addRule('.show-prompt-r a', '5px', true);
	DD_roundies.addRule('.progress-inner', '8px', true);
	
</script>
</body>
</html>
