$(document).ready(function(){
	/*新登录密码*/
	$(".newpassword-input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			$(this).parent().siblings(".show").show();
			$(this).parent().siblings(".empty").hide();
			$(this).parent().siblings(".error0").hide();
			$(this).parent().siblings(".error1").hide();
		},
		blur:function(){
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".error0").hide();
				$(this).parent().siblings(".error1").hide();
				$(this).parent().siblings(".show").hide();
				$(this).parent().siblings(".empty").show();					
			} else {
				if ((alength < 6) || (alength > 16)) {
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".error0").show();
					$(this).parent().siblings(".error1").hide();
					$(this).parent().siblings(".show").hide();
					$(this).parent().siblings(".empty").hide();						
				} else{
					if (substr("",aname,1)==1) {
						$(this).css({"border-color":"#f50"});
						$(this).parent().siblings(".error0").hide();
						$(this).parent().siblings(".error1").show();
						$(this).parent().siblings(".show").hide();
						$(this).parent().siblings(".empty").hide();	
					} else {
						$(this).css({"border-color":"#d5d5d5"});
						$(this).parent().siblings(".error0").hide();
						$(this).parent().siblings(".error1").hide();
						$(this).parent().siblings(".show").hide();
						$(this).parent().siblings(".empty").hide();							
					};
				};				
			};
		}
	});	
	/*确定新密码*/
	$(".surepassword-input").on({
		focus:function(){
			$(this).css({"border-color":"#a1de00"});
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			$(this).parent().siblings(".show").show();
			$(this).parent().siblings(".empty").hide();
			$(this).parent().siblings(".error").hide();
		},
		blur:function(){
			$(this).css({"border-color":"#f50"});
			var atype = this.type;
			var aname = this.value;
			var alength = this.value.length;
			if (aname == "") {
				$(this).css({"border-color":"#f50"});
				$(this).parent().siblings(".error").hide();
				$(this).parent().siblings(".show").hide();
				$(this).parent().siblings(".empty").show();					
			}else if($(".newpassword-input").val()==""||$(".newpassword-input").val().length<=0){
				$(this).css({"border-color":"#d5d5d5"});
				$(this).parent().siblings(".error0").hide();
				$(this).parent().siblings(".error1").hide();
				$(this).parent().siblings(".show").hide();
				$(this).parent().siblings(".empty").hide();	
			}else {
				if ($(".newpassword-input").val() != aname ) {
					$(this).css({"border-color":"#f50"});
					$(this).parent().siblings(".error").show();
					$(this).parent().siblings(".show").hide();
					$(this).parent().siblings(".empty").hide();							
				} else {
					$(this).css({"border-color":"#d5d5d5"});
					$(this).parent().siblings(".error").hide();
					$(this).parent().siblings(".show").hide();
					$(this).parent().siblings(".empty").hide();						
				};
			};
		}
	});
	/*提交验证*/
	$(".submit-btn").click(function(){
		var newpassword_input = $(".newpassword-input").val();
		var surepassword_input = $(".surepassword-input").val();
		var flag = true;
		if (newpassword_input == "") {
			$(".newpassword-input").parent().siblings(".error0").hide();
			$(".newpassword-input").parent().siblings(".error1").hide();
			$(".newpassword-input").parent().siblings(".show").hide();
			$(".newpassword-input").parent().siblings(".empty").show();
			$(".newpassword-input").css({"border-color":"#f00"});
			flag = false;
		}else {
			if((newpassword_input.length < 6) || (newpassword_input.length > 16)) {
				$(".newpassword-input").css({"border-color":"#f50"});
				$(".newpassword-input").parent().siblings(".error0").show();
				$(".newpassword-input").parent().siblings(".error1").hide();
				$(".newpassword-input").parent().siblings(".show").hide();
				$(".newpassword-input").parent().siblings(".empty").hide();	
				flag = false;
			} else if (substr("",newpassword_input,1)==1) {
				$(".newpassword-input").css({"border-color":"#f50"});
				$(".newpassword-input").parent().siblings(".error0").hide();
				$(".newpassword-input").parent().siblings(".error1").show();
				$(".newpassword-input").parent().siblings(".show").hide();
				$(".newpassword-input").parent().siblings(".empty").hide();	
				flag = false;
			} else {
				$(".newpassword-input").css({"border-color":"#d5d5d5"});
				$(".newpassword-input").parent().siblings(".error0").hide();
				$(".newpassword-input").parent().siblings(".error1").hide();
				$(".newpassword-input").parent().siblings(".show").hide();
				$(".newpassword-input").parent().siblings(".empty").hide();							
			};
		};
		if (surepassword_input == "") {
			$(".surepassword-input").parent().siblings(".show").hide();
			$(".surepassword-input").parent().siblings(".error").hide();
			$(".surepassword-input").parent().siblings(".empty").show();
			$(".surepassword-input").css({"border-color":"#f00"});
			flag = false;
		}else{
			if(newpassword_input==""||newpassword_input.length<=0){
				$(".newpassword-input").parent().siblings(".error0").hide();
				$(".newpassword-input").parent().siblings(".error1").hide();
				$(".newpassword-input").parent().siblings(".show").hide();
				$(".newpassword-input").parent().siblings(".empty").show();
				$(".newpassword-input").css({"border-color":"#f00"});
				flag = false;
			}else if(newpassword_input != surepassword_input){
				$(".surepassword-input").parent().siblings(".show").hide();
				$(".surepassword-input").parent().siblings(".error").show();
				$(".surepassword-input").parent().siblings(".empty").hide();
				$(".surepassword-input").css({"border-color":"#f00"});	
				flag = false;
			}
		};
		
		if(flag){
			$.ajax({
				type:"POST",
				url:"/resetPwd",
				data:"id="+$("#id").val()+"&password="+newpassword_input+"&token="+$("#uuid").val(),
				async:false,
				success:function(msg){
					if(msg.code==200){
						location="/resetSucc";
					}else{
						$(".surepassword-input").parent().siblings(".show").hide();
						$(".surepassword-input").parent().siblings(".error").show();
						$(".surepassword-input").parent().siblings(".empty").hide();
						$(".surepassword-input").css({"border-color":"#f00"});
					}
				}
			});
			return;
		}else if(surepassword_input.length==0){
			$(".surepassword-input").parent().siblings(".show").hide();
			$(".surepassword-input").parent().siblings(".error").hide();
			$(".surepassword-input").parent().siblings(".empty").show();
			$(".surepassword-input").css({"border-color":"#f00"});
			return;
		}else if(newpassword-input.length==0){
			$(".newpassword-input").parent().siblings(".show").hide();
			$(".newpassword-input").parent().siblings(".error").show();
			$(".newpassword-input").parent().siblings(".empty").hide();
			$(".newpassword-input").css({"border-color":"#f00"});
			return;
		}else if(newpassword-input!=surepassword_input){
			$(".surepassword-input").parent().siblings(".show").hide();
			$(".surepassword-input").parent().siblings(".error").hide();
			$(".surepassword-input").parent().siblings(".empty").show();
			$(".surepassword-input").css({"border-color":"#f00"});
			return;
		}
	});
})

	//输入6-16位字母加数字
	$(".newpassword-input ,.surepassword-input").bind("propertychange input", function(){
		var val = $(this).val();
		var istrue = substr(this,val,0);
	});

	//校验是否是字母或者数字   不允许输入特殊字符  (flag  0 普通校验  1 校验是否由两种字符 )
	function substr(com, val, flag){
		var regNum = /^\d$/;
		var regStr = /^[a-zA-Z]$/;
		var array = new Array();
		array = val;
		if(flag==0){
			for(var i=0;i<array.length;i++){
				var temp = array[i];
				if(regNum.test(temp)||regStr.test(temp)){
					console.log("true");
				}else{
					var nr = val.substring(0,val.length - 1);
					$(com).val(nr);
				}
			}
			return false;
		}else{
			var isDouble = 0;
			var isStr = 0;
			for(var i=0;i<array.length;i++){
				var temp = array[i];
				if(regNum.test(temp)){
					isDouble = 1;
				}else if(regStr.test(temp)){
					isStr = 1;
				}
			}
			if(isDouble&&isStr){
				return 0;
			}else{
				return 1;
			}
		}
	}
	