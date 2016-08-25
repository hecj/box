
jQuery(function(){
	$('.tablelist tbody tr:odd').addClass('odd');if($('.tablelist tbody tr').length<=0){$('.tablelist tbody ').append("<tr><td colspan='100' style='text-align:center'>暂无数据</td></tr>")}
	recomm.init();
});

/**
 * by HECJ
 */
var recomm  = {
	init:function(){
		jQuery("select[name=type]").change(function(){recomm.changeType();});
	}
};

/**
 * 推荐操作提交
 * by HECJ
 */
recomm.submit = function(project_id,postion){
	
	var msg = "";
	switch(postion){
		case -1:
			postion = "N1";
			msg = "确定要撤销推荐吗";
			break;
		case 0:
			msg = "确定要推荐吗";
			break;
		case 1:
			msg = "确定要推荐至首页吗";
			break;
	}
	if(confirm(msg)){
		location.href="/project/projectRecommSub/"+project_id+"-"+postion ;
	}
}

/**
 * query
 * by HECJ
 */
recomm.query = function(){
	
	var currURL = "/project/toProjectRecommList?";
	
	var type = jQuery("select[name=type]").val();
	var search_name = jQuery("input[name=search_name]").val();
	
	if(type == "aphone"){
		currURL += "&aphone="+search_name;
	}else if(type == "name"){
		currURL += "&name="+search_name;
	}
	
	var status = jQuery("select[name=status]").val();
	if(status != -1){
		currURL += "&status="+status;
	}
	location.href = currURL;
}

/**
 * 下拉框切换
 * by HECJ
 */
recomm.changeType = function(){
	jQuery("input[name=search_name]").val("");
}

/**
 * 推荐查询
 */
recomm.recommQuery = function(){
	location.href="/project/toProjectRecommList?recommQuery=recommQuery";
}