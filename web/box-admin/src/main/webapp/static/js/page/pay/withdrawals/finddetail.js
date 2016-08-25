	function changeStatus(id){
		var val = $("input[name=check]:checked").val();
		if(val!='1'&&val!='0'){
			alert("请选择通过与否再提交");
		}else {
			$.ajax({
				url:"/pay/withdrawals/verify",
				type:"post",
				async:false,
				data:"id="+id+"&pass="+val,
				success:function(msg){
					if(msg.code==200){
						alert("审核完成");
					}else{
						alert("系统异常");
					}
				}
			});
			window.location.reload();
		}
	}
	
	function changeType(val){
		if(val==1){
			return "充值";
		}else if(val==2){
			return "提现";
		}else if(val==3){
			return "订单支付";
		}else {
			return "订单退款";
		}
	}
	
	function changeTime(val){
		return new Date(parseInt(val)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " "); 
	}
	
	//获取账户流水
	function loaddetail(page){
		$("#detail").empty(); 
		$.ajax({
			type:"POST",
			url:"/pay/withdrawals/findAllFundRecord",
			data:"pageNumber="+page+"&pageSize="+10+"&userid="+$("#withdrwaalsUid").text(),
			async:false,
			success:function(resultJson){
				if(resultJson.code==200){
					var dataList = resultJson.data.list;
					var detail = "";	
					detail +="<table class='tablelist'>"
								+"<thead>"
									+"<tr>"
										+"<th title='ID'>编号</th>"
										+"<th title='withdrawId'>订单号</th>"
										+"<th title='time'>时间</th>"
										+"<th title='operate_type'>操作类型</th>"
										+"<th title='handle_amount'>变动金额(元)</th>"
										+"<th title='after_amount'>当前余额(元)</th>"
									+"</tr>"
								+"</thead>"
								+"<tbody>";
					for(var i=0;i<dataList.length;i++){
						detail +="<tr>"
									+"<td>"+dataList[i].id+"</td>"
									+"<td>"+dataList[i].order_num+"</td>"
									+"<td>"+changeTime(dataList[i].trad_at)+"</td>"
									+"<td>"+changeType(dataList[i].operate_type)+"</td>"
									+"<td>"+dataList[i].handle_amount+"</td>"
									+"<td>"+dataList[i].after_amount+"</td>"
								+"</tr>";
					}
					detail += "</tbody>"
						+"</table>";
					
					if(resultJson.data.totalPage > 1){
						// 填充分页
						detail += "<script>"
							+ "$('#support_page').createPage({"
								+ "pageCount:" + resultJson.data.totalPage + ","
								+ "current:" + resultJson.data.pageNumber + ","
								+ "backFn:function(p){" 
									+ "console.log(p);"
									+ "loaddetail(p);" 
								+ "}});"
							+ "</script>";
					}
				}else{
					//获取失败
				}
				$("#detail").html(detail);
			}
		});
		
	}
	
	//获取提现记录
	function loaddrawdetail(withdrawalsId,batch_no){
		$("#detail").empty(); 
		$("#support_page").empty();
		$.ajax({
			type:"POST",
			url:"/pay/withdrawals/findWithDrawals",
			data:"withdrawalsId="+withdrawalsId+"&batch_no="+batch_no,
			async:false,
			success:function(resultJson){
				if(resultJson.code==200){
					var dataList = resultJson.data;
					var detail = "";	
					detail +="<table class='tablelist'>"
								+"<thead>"
									+"<tr>"
										+"<th title='ID'>编号</th>"
										+"<th title='order_num'>订单号</th>"
										+"<th title='buyer_email'>支付宝账号</th>"
										+"<th title='tarde_no'>支付宝交易号</th>"
										+"<th title='withdrawals_success_at'>时间</th>"
										+"<th title='amount'>提现金额(元)</th>"
										+"<th title='status'>提现状态</th>"
									+"</tr>"
								+"</thead>"
								+"<tbody>";
					for(var i=0;i<dataList.length;i++){
						detail +="<tr>"
									+"<td>"+dataList[i].id+"</td>"
									+"<td>"+dataList[i].order_num+"</td>"
									+"<td>"+dataList[i].buyer_email+"</td>"
									+"<td>"+dataList[i].trade_no+"</td>"
									+"<td>"+changeTime(dataList[i].withdrawals_success_at)+"</td>"
									+"<td>"+dataList[i].amount+"</td>"
									+"<td>"+"提现成功"+"</td>"
								+"</tr>";
					}
					detail += "</tbody>"
						+"</table>";
				}else{
					//获取失败
				}
				$("#detail").html(detail);
				
			}
		});
	}