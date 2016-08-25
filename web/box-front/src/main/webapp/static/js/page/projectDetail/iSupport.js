// 全局变量
var projectId = $("#projectId").text();

// 加载数据-项目支持
function loadSupport(page) {
	$.ajax({
		url : '/projectDetail/getSupport/' + projectId +'-'+ page,
		type : "POST",
		dataType : "json",
		success : function(data) {
			if (data.code == 200) {
				//更新动态
				$(".project-detail-menu").find(".current").siblings(".pdm-num").find("strong").text(data.data.ordersDynamic);
				
				// 无数据时的处理
				if (data.data.orderList.length < 1) {
					$('#supportDiv').empty();
					return;
				}
				
				// 填充支持区
				var content = '';
				for ( var i = 0; i < data.data.orderList.length; i++) {
					var nickname = data.data.orderList[i].nickname;
					var picture = data.data.orderList[i].picture;
					var pay_at = data.data.orderList[i].pay_at;
					var money = data.data.orderList[i].money.toFixed(2);
					
					content += '<div class="support-block b-radius-8">'
								+ '<div class="head">'
									+ '<img src="'+picture+'" alt="">'
									+ '<a href="javascript:;"></a>'
								+ '</div>'
								+ '<div class="support-cont clearfix">'
									+ '<h6>' + nickname + '</h6>' 
									+ '<p>支持时间：'+ pay_at + '</p>'
									+ '<span>￥'+ money+ '</span>' 
								+ '</div>' 
							+ '</div>';
				}
				if (data.data.totalPage>1) {
					// 填充分页
					content += '<script>'
						+ '$("#support_page").createPage({'
						+ 'pageCount:' + data.data.totalPage + ','
						+ 'current:' + data.data.pageNumber + ','
						+ 'backFn:function(p){' 
						+ 'console.log(p);'
						+ 'loadSupport(p);' 
						+ '}});'
						+ '</script>';
				}
				$('#support_area').html(content);
			}
		}
	});
}