$(document).ready(function(){
	$(".pub-mask-layer").css({"height":$(window).height()});
	// 点击右上角关闭、确定关闭
	$(".popup-close, .cancel").click(function(){
		$(".pub-mask-layer,.pub-popup").hide();/*
		$(this).parent().parent().hide();*/
	});
	//输入提现金额(9位整数，并且可以保留两位小数)
	$("input[name=withdrawcash]").bind("propertychange input", function(){
		var val = $(this).val();
		substr(val, this);
	});
	function substr(val, com){
		var regex = /^(([1-9]\d{0,8})|0)(\.\d{0,2}){0,1}$/;
		if(val.length > 0 && !regex.test(val)){
			var nr = val.substring(0,val.length - 1);
			$(com).val(nr);
			substr(nr,com);
		}
	}
	// 提现金额校验
	$("input[name=withdrawcash]").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			$(this).parent().siblings(".pb-sign").addClass("show1").removeClass("show2").show().text("需在0.01元到4元之间，最多精确到2位小数");
		},
		blur:function(){
			$(this).css({"border-color":"#d5d5d5"});
			$(this).parent().siblings(".pb-sign").hide();
		}
	});
	$("input[name=withdraw-subbtn]").click(function(){
		var balance = $("#balance").text();
		var withdraw_cash = $("input[name=withdrawcash]").val();
		var ashow = $(this).parent().siblings(".withdraw-money").find(".pb-sign");
		
		if (withdraw_cash == "" || isNaN(parseFloat(withdraw_cash)) || parseFloat(withdraw_cash) == 0) {
			$(ashow).addClass("show2").removeClass("show1").show().text("提现金额输入错误，请检查");
			$("input[name=withdrawcash]").css({"border-color":"#f00"});
			return;
		} else if(parseFloat(balance) < parseFloat(withdraw_cash)){
			$(ashow).addClass("show2").removeClass("show1").show().text("提现金额不能超过余额，请检查");
			$("input[name=withdrawcash]").css({"border-color":"#f00"});
			return;
		}

		//判断余额是否合理
		$.ajax({
			type: "POST",
			url: "/withdrawals/compareBalance/",
			data:"withdrawals=" + withdraw_cash,
			success: function(msg) {
				if (msg == 'false') {
					$("input[name=withdraw-subbtn]").parent().siblings(".withdraw-money").find(".pb-sign").addClass("show2").removeClass("show1").show().text("提现金额不能超过余额，请检查");
					$("input[name=withdrawcash]").css({"border-color":"#f00"});			
				} else if(msg == 'true'){
					//	 点击申请提现确定弹窗出现
					$(".pub-mask-layer,.pub-popup").show();
					$("input[name=withdraw-subbtn]").parent().parent().show();
					$("#withdrawValue").text(parseFloat($("input[name=withdrawcash]").val()).toFixed(2));
				}else{
					$("input[name=withdraw-subbtn]").parent().siblings(".withdraw-money").find(".pb-sign").addClass("show2").removeClass("show1").show().text("提现金额输入错误，请检查");
					$("input[name=withdrawcash]").css({"border-color":"#f00"});			
				}
			}
		});
	});
	
	//确定提现
	$(".sure").click(function(){
		var amount = $("input[name=withdrawcash]").val();
		$.ajax({
			type: "POST",
			url: "/withdrawals/userWithdrawals/",
			data:"amount=" + amount,
			success: function(msg) {
				if(msg == 'true'){
					location = "/withdrawals/withdrawalProgress/";
				}else{
					alert("提现失败！");
				} 
			}
		});	 
	});
	
})