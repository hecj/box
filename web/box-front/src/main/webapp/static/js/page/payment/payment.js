
function paginateFn(id, totalPage, pageNumber, otherPageNumber){
	if(totalPage > 1){
		$("#" + id).createPage({
			pageCount:totalPage,
			current:pageNumber,
			backFn:function(p){
				console.log(p);
				//如果是充值分页按钮
				if(id == 'sub_page_footer'){
					location = "/payment/recharge/"+ p + "-" + otherPageNumber + "-l" ;
				}
				
				//如果是提现分页按钮
				if(id == 'sub_page_footer_withdrawals'){
					location = "/payment/recharge/"+ otherPageNumber + "-" + p  + "-r" ;
				}
			}
		});
		$(".page-wrap-sub").show();
	}
}