Date.prototype.format = function(format){
 /*
  * eg:format="YYYY-MM-dd hh:mm:ss";
  */
 var o = {
  "M+" :  this.getMonth()+1,  //month
  "d+" :  this.getDate(),     //day
  "h+" :  this.getHours(),    //hour
      "m+" :  this.getMinutes(),  //minute
      "s+" :  this.getSeconds(), //second
      "q+" :  Math.floor((this.getMonth()+3)/3),  //quarter
      "S"  :  this.getMilliseconds() //millisecond
   };
  
   if(/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
   }
 
   for(var k in o) {
    if(new RegExp("("+ k +")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    }
   }
 return format;
};


//================================================================
//jquery 扩展部分

jQuery.noop=jQuery.noop||(function(){});
/**
 * 表单提交扩展
 */
(function(jQuery) {
	 jQuery.fn.serializedom=function(mode,andself){
		 if(!mode){mode="";}
		var tar=jQuery(this).find(mode+"[name]");
		if(andself){
			tar=tar.add(jQuery(jQuery(this).selector+mode+"[name]").get());
		}
		tar=tar.get();
		var str="";
		var obj={};
		for(var i in tar){
			var n=tar[i].name;
			var v=tar[i].value;
			if(v){
				if(str!=""){
					str+="&";
				}
				str+=n+"="+v;
				obj[n]=v;
			}
		}
		return {string:str,json:obj};
	};
	 jQuery.fn.addparm=function(op){
		pstr="";
		for(var i in op){
			var oo=op[i];
			if(isArray(oo)){
				for(var ox in oo){
					pstr+="<input type='hidden' name='"+i+"' value='"+oo[ox]+"'/>";
				}
			}else{
				pstr+="<input type='hidden' name='"+i+"' value='"+oo+"'/>";
			}
		}
		return jQuery(this).append(pstr);
	 };
	jQuery.newform=jQuery.fn.newform=function(url,op){
		 return jQuery("<form method='post' action='"+url+"'></form>").appendTo("body").addparm(op);
	};	
})(jQuery);


function isArray(obj) {  
	return Object.prototype.toString.call(obj) === '[object Array]';   
}




		jQuery.baseTool={
				KEY_SPECIAL_IE_CHECK:function(keyarr,code){//ie特殊键组合
					var sk=["CTRL","ENTER"].sort().join("+");
					var kk=keyarr.sort().join("+");
					if (kk==sk) {
//						return ["CTRL","IE_CTRL_ENTER_CODE"];
						if (code==10) {
							return true;
						}
					}
					return false; 
				},
				KEY_CONSTANT:{//按钮keycode常量
					IE_CTRL_ENTER_CODE:10,//ie下ctrl和enter组合键enter值为10
					ENTER:13,
					ESC:27,
					C:67,
					c:67,
					S:83,
					s:83
					/*keycode 65 = a A
					keycode 66 = b B
					keycode 67 = c C
					keycode 68 = d D
					keycode 69 = e E EuroSign
					keycode 70 = f F
					keycode 71 = g G
					keycode 72 = h H
					keycode 73 = i I
					keycode 74 = j J
					keycode 75 = k K
					keycode 76 = l L
					keycode 77 = m M mu
					keycode 78 = n N
					keycode 79 = o O
					keycode 80 = p P
					keycode 81 = q Q at
					keycode 82 = r R
					keycode 83 = s S
					keycode 84 = t T
					keycode 85 = u U
					keycode 86 = v V
					keycode 87 = w W
					keycode 88 = x X
					keycode 89 = y Y
					keycode 90 = z Z */
					
				},
				isPress:function(keylist,e){
					if (keylist&&typeof keylist!="array") {
						keylist=keylist.toString().split("+");
					}
					var code=($.browser.msie)?e.keyCode:(e.which||e.keyCode);
				//	alert(code+"---"+e.ctrlKey+"---"+e.shiftKey+"---"+e .altKey);
					if ((!keylist)||keylist.length==0) {
						return false;
					}
					
					var rs=jQuery.baseTool.KEY_SPECIAL_IE_CHECK(keylist,code);
					if (rs) {
						return rs;
					}
					for ( var k in keylist) {
						var num=keylist[k];
//						if (/^\d+$/.test(num)) {
						if(jQuery.baseTool.KEY_CONSTANT[num]){
							num=jQuery.baseTool.KEY_CONSTANT[num];
							if (code!=num) {
								return false;
							}
						}else{
							if (!e[num.toString().toLowerCase()+"Key"]) {
								return false;
							}
						}
					}
					return true;
				},
				getEvent:function(){//获取event对象
					 if(document.all) return window.event;
					 var func=jQuery.baseTool.getEvent.caller;
					 while(func!=null){
					   var arg0=func.arguments[0];
					   if(arg0){
						  if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)){
							return arg0;
						  }
					   }
					   func=func.caller;
					 }
					 return null;
			 },
			 cancelBubble:function () 
			 {
			     var e= jQuery.baseTool.getEvent(); 
			     if(window.event){
			         //e.returnValue=false;//阻止自身行为
			         e.cancelBubble=true;//阻止冒泡
			      }else if(e.preventDefault){
			         //e.preventDefault();//阻止自身行为
			         e.stopPropagation();//阻止冒泡
			      }
			 },
			 //取得事件目标对象
             getTarget: function(event) {
                 if (event.target) {
                     return event.target;
                 } else {
                     return event.srcElement;
                 }
             },
             //阻止默认事件
             preventDefault: function(event) {
                 if (event.preventDefault) {
                     event.preventDefault();
                 } else {
                     event.returnValue = false;
                 }
             }

};

//锁屏-----------start
jQuery.lockScreen=jQuery.fn.lockScreen={
		 lockOver:null,
		 limit:1000000,
		 remeberLockOver:function(el){
			 if((!el)||(el.length<1)){return;}
			 var limit=this.limit;
			 el.each(function(i,o){
				 var e=jQuery(o);
				 var zidx=e.css("zIndex");
				 var zp=(!zidx)||(zidx=="auto")?0:zidx;
				 if(zp<limit){
					 e.data("zIndex",zidx);
					 e.css("zIndex",(zp)*1+limit);
				 }
			 });
		 },
		 lock:function(el,op){
			 var  defaultOp={
					 zIndex: this.limit-1,
					 position: 'fixed',
					 left: 0,
					 top: 0,
					 width: '100%',
					 height: '100%',
					 overflow: 'hidden'
			 };
			 op=jQuery.extend({},defaultOp,op);
			 if(this.lockOver){
				 this.lockOver.add(el);
			 }else{
				 this.lockOver=el;
			 }
			 this.remeberLockOver(this.lockOver);
			 var jQuerydiv=jQuery(".div_lock_screen");
			 if (jQuerydiv&&jQuerydiv.length>0) {
				 jQuerydiv.css(op).show();
			 }else{
				 jQuerydiv=jQuery("<div style='background-color:black' class='div_lock_screen'></div>").css(op).appendTo("body");
			 }
			 return jQuerydiv;
		 },
		backLockOver:function(el){
			 if((!el)||(el.length<1)){return;}
			 el.each(function(){
				 var e=jQuery(this);
				 var zidx=e.data("zIndex");
				 e.css("zIndex",zidx);
			 });
		 },
		 unlock:function(closediv){
			//this.backLockOver(this.lockOver);
			var jQuerydiv=jQuery(".div_lock_screen"); 
			if(closediv){
				jQuerydiv=closediv;
			}
			if (jQuerydiv&&jQuerydiv.length>0) {
				jQuerydiv.remove();
			}
			this.lockOver=null;
		 }
};

//锁屏-----------end






//弹出层 浮动层 jquery插件

;(function (jQuery, window, undefined) {
	//floatdiv------------------------start
	/*任意位置浮动固定层*/
	/*没剑(http://regedit.cnblogs.com) 08-03-11*/
	/*说明：可以让指定的层浮动到网页上的任何位置，当滚动条滚动时它会保持在当前位置不变，不会产生闪动*/
	
	/*2008-4-1修改：当自定义right位置时无效，这里加上一个判断
	有值时就不设置，无值时要加18px已修正层位置在ie6下的问题
	*/
	/*调用：
	1 无参数调用：默认浮动在右下角
	jQuery("#id").floatdiv();
	
	2 内置固定位置浮动
	//右下角
	jQuery("#id").floatdiv("rightbottom");
	//左下角
	jQuery("#id").floatdiv("leftbottom");
	//右下角
	jQuery("#id").floatdiv("rightbottom");
	//左上角
	jQuery("#id").floatdiv("lefttop");
	//右上角
	jQuery("#id").floatdiv("righttop");
	//居中
	jQuery("#id").floatdiv("middle");
	
	3 自定义位置浮动
	jQuery("#id").floatdiv({left:"10px",top:"10px"});
	以上参数，设置浮动层在left 10个像素,top 10个像素的位置
	
	
	
	*/
	;jQuery.fn.floatdiv=function(location,op){
		var defaultOp={
//				fixed:true//是否试用修正模式
		};
		if(!op){op={};};
		if(( typeof(op.fixed)=="undefined")){
			op.fixed=true;
		}else if(op.fixed!=="false") {
			op.fixed=false;
		}else{
			op.fixed=true;
		}
		op=jQuery.extend({},defaultOp,op);
//		if(op.fixed&&op.fixed===true){
//			op.fixed=true;
//		}
		//ie6要隐藏纵向滚动条
		var isIE6=false;
		if(jQuery.browser.msie && jQuery.browser.version=="6.0"){
			//jQuery("html").css("overflow-x","auto").css("overflow-y","hidden");
			isIE6=true;
		};
	/*	jQuery("body").css({margin:"0px",padding:"0 10px 0 10px",
			border:"0px",
			height:"100%",
			overflow:"auto"
		});*/
		return this.each(function(){
			var loc;//层的绝对定位位置
			if(location==undefined || location.constructor == String){
				switch(location){
					case("rightbottom")://右下角
						loc={right:"0px",bottom:"0px"};
						break;
					case("leftbottom")://左下角
						loc={left:"0px",bottom:"0px"};
						break;	
					case("lefttop")://左上角
						loc={left:"0px",top:"0px"};
						break;
					case("righttop")://右上角
						loc={right:"0px",top:"0px"};
						break;
					case("middle")://居中
						var l=0;//居左
						var t=0;//居上
						var windowWidth=0,windowHeight=0;//窗口的高和宽
						//取得窗口的高和宽
						if (self.innerHeight) {
							windowWidth=self.innerWidth;
							windowHeight=self.innerHeight;
						}else if (document.documentElement&&document.documentElement.clientHeight) {
							windowWidth=document.documentElement.clientWidth;
							windowHeight=document.documentElement.clientHeight;
						} else if (document.body) {
							windowWidth=document.body.clientWidth;
							windowHeight=document.body.clientHeight;
						}
						l=windowWidth/2-jQuery(this).width()/2;
						t=windowHeight/2-jQuery(this).height()/2;
						loc={left:l+"px",top:t+"px"};
						break;
					default://默认为右下角
						loc={right:"0px",bottom:"0px"};
						break;
				}
			}else{
				loc=location;
			}
			if(op.fixed){
				jQuery(this).css(loc).css("position","fixed");
			}else{
				jQuery(this).css(loc).css("position","absolute");
			}
			if(isIE6){
				if(loc.right!=undefined){
					//2008-4-1修改：当自定义right位置时无效，这里加上一个判断
					//有值时就不设置，无值时要加18px已修正层位置
					if(jQuery(this).css("right")==null || jQuery(this).css("right")==""){
						jQuery(this).css("right","18px");
					}
				}
				var loc_l=loc.left;
				if(loc_l){
					loc.left=loc_l+( $(document).scrollLeft())*1;
				}
				var loc_r=loc.right;
				if(loc_r){
					loc.left=loc_r-( $(document).scrollLeft())*1;
				}
				var loc_t=loc.top;
				if(loc_t){
					loc.top=loc_t+( $(document).scrollTop())*1;
				}
				var loc_b=loc.botom;
				if(loc_b){
					loc.left=loc_b-( $(document).scrollTop())*1;
				}
				jQuery(this).css(loc).css("position","absolute");
			}
		});
	};
	//floatdiv------------------------end
})(jQuery);	
	
	
	//移动插件-----start
	jQuery.extend({
		dragAble: function(op,el) { 
	
				var defaultOp={
					hand:el,
					startDrag:jQuery.noop,//开始拖动
					endDrag:jQuery.noop,//结束拖动
					moveDrag:jQuery.noop//拖动中
				};
				op=jQuery.extend({},defaultOp,op);
				
				var posX=0;
				var posY=0;
				var tar = el;
				var hand=op["hand"];
	
				var jQueryw=jQuery(window);
				var ww=jQueryw.width();
				var wh=jQueryw.height();
				var lastX=0;//最近一次鼠标的x坐标
				var lastY=0;//最近一次鼠标的y坐标
				
				var fixed=false;
				
				 
				//var wl=jQuery(window).scrollLeft();
			//	var wt=jQuery(window).scrollTop();
				var mousemove = function(e){
						var tw=tar.width();
						var th=tar.height();
						
	
						var maxX=ww-tw;//最大右边值
						var maxY=wh-th;//最大下边值
	
						e=jQuery.baseTool.getEvent(); 
						var flag_move=true;
						var value_button = e.button;
						if (jQuery.browser.msie) {//浏览器有差异
							if (value_button!=1) { 
								event_mouseUp(e);return;
							}
						}else{
							if (value_button!=0){
								event_mouseUp(e);return;
							}
						}
						if(!fixed) {
							maxX=jQuery(window).scrollLeft()+ww;
							maxY=jQuery(window).scrollTop()+wh;
						}
						
						 //  debug.append("<div>"+"ex:"+e.clientX+" ey:"+e.clientY+" lx:"+lastX+" ly:"+lastY+"</div>");
						var peon=tar.css("left");
						var zbj=0;//左边界
						var ybj=ww;//右边界
						if(!fixed) {
							peon=tar.offset().left; 
							zbj=jQuery(window).scrollLeft()+0;
							ybj=jQuery(window).scrollLeft()+ww;
						}
						if(peon<=zbj){//超出左边边界
							//tar.offset({left:1 ,top:tar.offset().top});
							if(e.clientX<=lastX){//仍然往边界方向
								flag_move=false;
							}
							
							
						}
						if(peon+tw>=ybj){//超出右边边界
							//tar.offset({left:ww-tw-11 ,top:tar.offset().top});
							if(e.clientX>=lastX){//仍然往边界方向
								flag_move=false;
							}
							 
							 
						}
						var tct=tar.css("top");
						var sbj=0;//上边界
						var xbj=ww;//下边界
						if(!fixed) {
							tct=tar.offset().top;
							sbj=jQuery(window).scrollTop()+0;
							xbj=jQuery(window).scrollTop()+wh;
						}
					 
						if(tct<=sbj){//超出上边界
							//tar.offset({left:tar.offset().left ,top:1});
							if(e.clientY<=lastY){//仍然往边界方向
								flag_move=false;
							}
							 
							 
						}
						if(tct+th>=xbj){//超出下边界
							//tar.offset({left:tar.offset().left ,top:wh-th-11});
							if(e.clientY>=lastY){//仍然往边界方向
								flag_move=false;
	//							jQuery(window).scrollTop(jQuery(window).scrollTop()+5);
							}
							 
							 
						}
						 
						if(flag_move){
							if(fixed){
	//							alert((e.clientX +"****"+ (posX))+"****"+maxX);
	//							alert((e.clientY +"****"+ (posY))+"****"+maxY);
								var rx=Math.max(0,Math.min((e.clientX - (posX)),maxX));
								var ry=Math.max(0,Math.min((e.clientY - (posY)),maxY));
								//debug.append("<div>"+posX+"++"+posY+"**"+rx+"++"+ry+"**"+jQuery(window).scrollLeft()+"++"+jQuery(window).scrollTop()+"=="+(posY-jQuery(window).scrollTop())+"</div>");
								tar.css({left:rx ,top:ry});
							}else{
								var rx=Math.max(0,Math.min((e.clientX - (posX)),maxX));
								var ry=Math.max(0,Math.min((e.clientY - (posY)),maxY));
								//debug.append("<div>"+posX+"++"+posY+"**"+rx+"++"+ry+"**"+jQuery(window).scrollLeft()+"++"+jQuery(window).scrollTop()+"=="+(posY-jQuery(window).scrollTop())+"</div>");
								tar.offset({left:rx ,top:ry});
							}
						}else{
							fresh_pos(e);
						}
						/**
						if(flag_move){
							//debug.append("<div>**"++"---"+jQuery(window).width()+"</div>");
							tar.offset({left:(e.clientX+(jQuery(window).scrollLeft()|0)  - posX) ,top:(e.clientY+(jQuery(window).scrollTop()|0)+  - posY)});
						}*/
						//拖动中
						op.moveDrag(tar,e);
					
				};
	
				var fresh_pos = function(e,flag){//flag是否刷新posX ，posY
						e=jQuery.baseTool.getEvent(); 
						if(flag){
							if(fixed){
								var tl= parseInt(tar.css("left"));
								tl=(tl>=0)?tl:tl||(jQuery(window).width()-tar.width()-parseInt(tar.css("right")));
								var tt=parseInt(tar.css("top"));
								tt=(tt>=0)?tt:tt||(jQuery(window).height()-tar.height()-parseInt(tar.css("bottom")));
								posX = e.clientX -tl;
								posY = e.clientY - tt;
							}else{
								
								posX = e.clientX - parseInt(tar.offset().left);
								posY = e.clientY - parseInt(tar.offset().top);
							}
						}
	
						lastX=e.clientX;//最近一次鼠标的x坐标
						lastY=e.clientY;//最近一次鼠标的y坐标
						//每次移动后 重新计算窗体宽高
						ww=jQueryw.width();
						wh=jQueryw.height();
						
				};
	
				/*jQuery(window).bind("scroll",function() {
					wl=jQuery(window).scrollLeft();
					wt=jQuery(window).scrollTop();
				});*/
				
				var changeZindex=function(){//更改当前鼠标选中的对象的层次
					var currentZIndex=peon.pop.currentZIndex*1;
					var tzidx=tar.css("z-index")||0;
					if(!currentZIndex){
						peon.pop.currentZIndex=9;
					}
					var hascover=false;//是否在锁屏之上
					if(tzidx*1>1000000){
						hascover=true;
					}
					var cshould=tzidx*1-(hascover?1000000:0);
					if(cshould<=peon.pop.currentZIndex*1){
							peon.pop.currentZIndex +=1;
							tar.css("z-index",peon.pop.currentZIndex*1+(hascover?1000000:0));
					}else{
						peon.pop.currentZIndex=cshould;
					}
					
				};
				
				hand.bind("mousedown",function(e) {
						changeZindex();
						
						fixed=(tar.css("position")=="fixed");
						fresh_pos(e,true);
						jQuery(document).bind("mousemove", mousemove);
						
						jQuery("body")[0].onselectstart=function(){ return false;};
						jQuery("body").css("-moz-user-select","none");
						tar.css("-moz-user-select","none");
						//开始拖动
						op.startDrag(tar,e);
				});
	
				var event_mouseUp= function(e){
					jQuery(document).unbind("mousemove", mousemove);
					
					jQuery("body")[0].onselectstart=function(){ return true;};
					jQuery("body").css("-moz-user-select","");
					tar.css("-moz-user-select","");
					//结束拖动
					op.endDrag(tar,e);
				};
				
				jQuery(document).bind("mouseup" ,event_mouseUp);
	
		}
	});
	
	
	jQuery.fn.extend({
		dragAble: function(op) { 
			return this.each(function(){
				jQuery.dragAble(op,jQuery(this)); 
			});
		}
	});

//移动插件---------end

	
	//-------------键盘事件---------------------------start
	jQuery.fn.extend({
		
		keyAware: function(fun,action) {
			var proxy=function(){
				var e=jQuery.baseTool.getEvent(); 
				var code=($.browser.msie)?e.keyCode:e.which;
				fun&&(typeof fun=="function")&&fun(code,e);
			};
			this.addKeyAwareList(this.get(0), proxy,action);
		},
		addKeyAwareList:function(el,proxy,action){//注册
			var list=jQuery.keyAwareMap[action||'keydown'];
			if(!list){list=[];}
			list.push({el:el,proxy:proxy});
			jQuery.keyAwareMap[action||'keydown']=list;
		},
		removeKeyAwareList:function(el,proxy,action){//移除
			var list=jQuery.keyAwareMap[action||'keydown'];
			if(!list){list=[];}
			var flag=false;
			var index;
			for(var i in list){
				var o=list[i];
				if((o.el==el)&&(o.proxy==proxy)){
					index=i;
					flag=true;
				}
			}
			var rs=list.slice(0,index).cancat(list.slice(index+1,list.length));
			jQuery.keyAwareMap[action||'keydown']=rs;
		},
		keyAwarePress:function(list){
			var proxyfun=function(code,event){
				list.length||(list=[list]);
				
				jQuery.each(list,function(i,n){
					if(jQuery.baseTool.isPress(n.key,event)){
						var fun=n.success;
						fun&&(typeof fun=="function")&&fun(code,event);
					}else{
						var fun=n.fail;
						fun&&(typeof fun=="function")&&fun(code,event);
					}
				});
			};
			this.keyAware(proxyfun);
		},
		keyAwareEnterPress:function(fun,fun2){
			this.keyAwarePress([{key:"ENTER",success:fun,fail:fun2}]);
		}
	});
	
	jQuery(function(){
		jQuery.currentFocusElment=null;
		jQuery.keyAwareMap={};
		function checkKeyMap(type){
			var tar=jQuery.currentFocusElment;
			var map=jQuery.keyAwareMap;
			if(map){
				var list=map[type];
				if(list&&(list.length>0)){
					for(var i in list){
						var o=list[i];
						var el=o.el;
						var proxy=o.proxy;
						if(tar&&el){
							var inner=(el==tar)?true:(jQuery.contains(el,tar));
							if(inner){//如果找到列表中的事件
								proxy();break;
							}
							
						}
					}
				}
			}
		}
		jQuery(document).mousedown(function(event){
			event = jQuery.baseTool.getEvent();
			var tar=jQuery.baseTool.getTarget(event);
			jQuery.currentFocusElment=tar;
		});
		jQuery(document).keydown(function(event){
			checkKeyMap("keydown");
		});
		jQuery(document).keypress(function(event){
			checkKeyMap("keypress");
		});
		jQuery(document).keyup(function(event){
			checkKeyMap("keyup");
		});
	});
	//-------------键盘事件---------------------------end
	
	
	
	
	peon={};
	peon.tools={//工具对象
		log: function(message) {
			if (typeof console != 'undefined' && console.log) console.log(message);
		},
		date:{
			jsonDateFormat:function (jsonDate) {
			    try {
			        var date = new Date(parseInt(jsonDate.replace("/Date(", "").replace(")/", ""), 10));
			        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
			        var date = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
			        var hours = date.getHours();
			        var minutes = date.getMinutes();
			        var seconds = date.getSeconds();
			        var milliseconds = date.getMilliseconds();
			        return date.getFullYear() + "-" + month + "-" + date + " " + hours + ":" + minutes + ":" + seconds + "." + milliseconds;
			    } catch (ex) {
			        return "";
			    }
			},
			nowTimeSeconds:  new Date().getTime(),
			getTimesMark:function(flag){
				return !flag?dslx.tools.date.nowTimeSeconds:(new Date().getTime());
			},
			format:function(d,f){
				if(f=="short") {f="yyyy-MM-dd";}
				f=f||"yyyy-MM-dd hh:mm:ss";
				return new Date(d).format(f);
			},
			getYMD:function(d){
				return this.format(d).split(" ")[0];
			},
			getHMS:function(d){
				var dz=this.format(d).split(" ");
				return (dz.length>1)?dz[1]:"";
			},
			getLeftTime:function(fd,td){
				return this.parseDate(td)-this.parseDate(fd);
			},
			getLeftTimeToNow:function(d){//计算某个时间还有多少毫秒到当前时间
				return new Date()-this.parseDate(d);
			},
			getLeftTimeNowToSMTime:function(d){
				return d-new Date();
			},
			parseDate:function(d){
				return Date.parse(this.format(d).split("-").join("/"));
			},
			formatMinutes: function (minutes){
		        var day = parseInt(Math.floor(minutes / 1440));
		        var hour = day >0
		                       ?Math.floor((minutes - day*1440)/60)
		                       :Math.floor(minutes/60);
		        var minute = hour > 0
		                          ? Math.floor(minutes -day*1440 - hour*60)
		                          :minutes;
		        var time="";
		        if (day > 0) time += day + "天";
		        if (hour > 0) time += hour + "小时";
		        if (minute > 0) time += minute + "分钟";
		        return time;
		    },
			formatSecondsSite:function(seconds){
				seconds=Math.round(seconds);
				if(seconds >0){
		            var minutes = Math.floor(seconds/60);
		            seconds = seconds - minutes * 60;
		              var day = parseInt(Math.floor(minutes / 1440));
				        var hour = day >0
				                       ?Math.floor((minutes - day*1440)/60)
				                       :Math.floor(minutes/60);
				        var minute = hour > 0
				                          ? Math.floor(minutes -day*1440 - hour*60)
				                          :minutes;
				        var time="";
				        if (day > 0) {time += day + "天"; return time;}
				        if (hour > 0) {time += hour + "小时";return time;}
				        if (minute > 0) {time += minute + "分钟";return time;}
				        if (seconds > 0){ time += seconds + "秒";return time;}
		        }else {
		        	return 0+"秒";
				}
			},
		    //格式化秒数为时分秒
			formatSeconds:function (seconds) {
				seconds=Math.round(seconds);
		        if(seconds >0){
		            var minutes = Math.floor(seconds/60);
		            seconds = seconds - minutes * 60;
		            return this.formatMinutes(minutes) + (seconds > 0 ? seconds + "秒" : "");
		        }else {
		        	return 0+"秒";
				}
		    },
		    //获得当前时间 之前或之后的指定时间 -d :当前时间之前的某天时间 ，d ：当前时间之后的某天时间
		    getLeftRightTime:function(d,days){
		    	var datetiem = new Date();
		    	if(!isNaN(days))
		    		datetiem.setTime(this.parseDate(d)+days*24*60*60*1000);
		    	else
		    		datetiem.setTime(this.parseDate(d));
		    	return datetiem.format("yyyy-MM-dd");
		    }
		},
		number:{
			change2Char:function(n){
				return "零一二三四五六七八九"[n]||"";
			}
		},
		string:{//字符串处理 
			Reg_Characters:/[\u4e00-\u9fa5]/ig,
			trim:function(str){
				return str.replace(/(^[\s]*)|([\s]*$)/g, "");
			},
			trimleft:function(str){
				return str.replace(/(^[\s]*)/g, "");
			},
			trimright:function(str){
				return str.replace(/([\s]*$)/g, "");
			},
			trimcrlf:function(str){
				return str.replace(/\r/g, "").replace(/\n/g, "");
			},
			subif:function(str,len){
				str=str||"";
				var tar=str.substring(0,len);
				if (str.length>len) {
					tar=tar+"..";
				}
				return tar;
			},
			subcontent:function(str,len){
				var len=dslx.conf.echo[len]||len;
				return this.filterHtml(this.subif(str,len||dslx.conf.echo.content));
			},
			subtitle:function(str,len){
				var len=dslx.conf.echo[len]||len;
				return this.filterHtml(this.subif(str,len||dslx.conf.echo.title));
			},
			doubleFilterHtml:function(str){//双转义
				return this.filterHtml(str).replace(/&/ig,"&amp;");
			},
			cancelAt:function(str){
				str= str.replace(/&amp;/g,"&").replace(/&gt;/g,">").replace(/&lt;/g,"<");
				str=str.replace(/&#x22;/g,"\"").replace(/&#x27;/g,"\'");
				return str;
			},
			filterNull:function(str,def){
				return str?str:((typeof def=="undefined")?"":((typeof def !="undefined")?def:""));
			},
			filterHtml:function(str){
				str=str||"";
				str=str.toString();
				var rs = str.replace(/\'/ig,"&#x27;").replace(/\"/ig,"&#x22;");
				//&#x3c;&#x3e;
				return rs.replace(/\</ig,"&lt;").replace(/\>/ig,"&gt;");
			},
			filterSpecialChar:function(str){//在post提交前转义特殊字符
			/*	1. +  URL 中+号表示空格 %2B
				2. 空格 URL中的空格可以用+号或者编码 %20
				3. /  分隔目录和子目录 %2F 
				4. ?  分隔实际的 URL 和参数 %3F 
				5. % 指定特殊字符 %25 
				6. # 表示书签 %23 
				7. & URL 中指定的参数间的分隔符 %26 
				8. = URL 中指定参数的值 %3D*/
				
				  //先替换其中的 % ，因为其他的字符会转义成%xx
				   var rs=str.replace(/\%/g,"%25");
				   rs=rs.replace(/\#/g,"%23");
				   rs=rs.replace(/\&/g,"%26"); 
				   rs=rs.replace(/\+/g,"%2B");
				      
				   rs=rs.replace(/\//g,"%2F");
				   rs=rs.replace(/\=/g,"%3D");
				   rs=rs.replace(/\?/g,"%3F");
				   rs=rs.replace(/\s/g,"%20");
				   return rs;
			}
		}
	};
		
		
	
	peon.pop={
			currentZIndex:9,
			idgen:{
				time:function(){
					return new Date().getTime();
				}
			},
			confirm:function(op,call){
				var mode="set";//自带设置模式
				if(typeof op =="string"){
					 mode="defined";
					op={msg:op};
					op.confirm=call||jQuery.noop;
				}
				op.btns=op.btns||[{txt:"确定",btnclass:"btn",value:"1"},{txt:"取消",btnclass:"btn",value:""}];
				op.id="alert_pop_"+this.idgen.time();
				var ct=op.autoclose;
				if(ct){
					op.finished=function(body,op){
						var xt=setTimeout(function(){peon.pop.close(body.find(".close").get(0),op);},ct);
						body.click(function(){
							clearTimeout(xt);
						});
					};
				}
				op.yoff=0;
				if(typeof op.islock =="undefined"){
					op.islock=false;
				}
				op.css=op.css||{width:"485px","min-height":"100px","z-index":1000000+(peon.pop.currentZIndex*1||0)};
				op.title=op.title||"消息提示";
				var tipmsg=mode=="defined"?"<p class='txtcenter' style='padding-left:0'>"+op.msg+"<p>":op.msg;
				var tipbtn=[];
				jQuery.each(op.btns,function(i,itm){
					var prox=jQuery('<span class="sure" value="'+itm.value+'" >'+itm.txt+'</span>').addClass(itm.btnclass||"btn").click(function(){
						peon.pop.close(this);
						op.confirm(jQuery(this).attr("value"));
					});
					tipbtn.push(prox);
				});
				op.msg=tipmsg;
				op.btns=tipbtn;
				peon.pop.pop(op);
				
			},
			tip:function(op,showtime){
				var msg="<div class='tip_ou'><span class='tip_wz'><span class='tip_pop' style='background-image: url(\"/static/Public/png/512/"+op.type+".png\");'></span><span  >"+op.msg+"</span></span></div>";
	    		op.msg=msg;
	    		op.fixed="false";
	    		jQuery(".tip_ou").hide();
				peon.pop.showTip(op,2000);
				
			},
			showTip:function(op,showtime){
				var defshowtime=5000;
				if(typeof op =="string"){
					op={msg:op,showtime:showtime||defshowtime};
				}
				op.fxtime=op.fxtime||1000;
				op.defshowtime=op.defshowtime||defshowtime;
				var msg= op.msg ;
				var tar=jQuery(msg);
				if(op.targetel){
					if(typeof op.targetel =="string"){op.targetel=jQuery(op.targetel);}
				}else{
					op.targetel=jQuery("body");
				}
				if (op.targetel.css("type")=="hidden") {
					op.targetel=op.targetel.parent();
				}
				var l=op.targetel.offset().left+(op.targetel.width()*0.7)+(op.offsetl||0);
				var r=op.targetel.offset().top+(op.offsett||0);;//*1-(tar.height()||35);
		/*		var l=Math.max((jQuery(window).width()-tar.width())/2,0);
				var r=Math.max((jQuery(window).height()-tar.height())/2,0);*/
				var closeTip=function(){
					tar.fadeOut(op.fxtime);
					tar.remove();
				};
				tar.click(function(){
					closeTip();
				});
				tar.hide().appendTo("body").css({"z-index":1000000+(peon.pop.currentZIndex*1||0)}).floatdiv( {left:l,top:r},{fixed:(op.fixed?"true":"false")}).fadeIn(op.fxtime);
				setTimeout(closeTip,op.defshowtime);
			},
			bigTip:function(op,showtime){
				var defshowtime=5000;
				var tipclass={
						ok:"instanttishi",
						no:"warntishi"
				};
				if(typeof op =="string"){
					op={msg:op,showtime:showtime||defshowtime};
				}
				op.fxtime=op.fxtime||1000;
				op.defshowtime=op.defshowtime||defshowtime;
				var cls=tipclass[op.type||"ok"];
				var msg='<div class="'+cls+'"><h1 class="font16cu">'+op.msg+'</h1></div>';
				var tar=jQuery(msg);
				
				var l=Math.max((jQuery(window).width()-tar.width())/2,0);
				var r=Math.max((jQuery(window).height()-tar.height())/2,0);
				var closeTip=function(){
					tar.fadeOut(op.fxtime);
					tar.remove();
				};
				tar.click(function(){
					closeTip();
				});
				tar.hide().appendTo("body").css({"z-index":1000000+(peon.pop.currentZIndex*1||0)}).floatdiv( {left:l,top:r}).fadeIn(op.fxtime);
				setTimeout(closeTip,op.defshowtime);
			},
			alert:function(op,islock,autoclose){
				op=op||{};
				if(typeof op =="string"){
					op={msg:"<p class='txtcenter' style='padding-left:0'>"+op+"</p>"};
					op.islock=islock;
					op.autoclose=op.autoclose||autoclose;
				}
				op.id="alert_pop_"+this.idgen.time();
				var ct=op.autoclose;
				if(ct){
					op.finished=function(body,op){
						var xt=setTimeout(function(){peon.pop.close(body.find(".close").get(0),op);},ct);
						body.click(function(){
							clearTimeout(xt);
						});
					};
				}
				op.yoff=0;
				if(typeof op.islock =="undefined"){
					op.islock=false;
				}
				op.css={width:"485px","min-height":"100px","z-index":1000000+(peon.pop.currentZIndex*1||0)};
				op.title=op.title||"消息提示";
				return peon.pop.pop(op);
			},
			close:function(el,op){
				var body=jQuery(el).parents(".mesWindow");
				if(body.size()==0){
					body=jQuery(".mesWindow");
				}
				if(op){
					var b=true;
					b=op.beforeclose&&op.beforeclose(body);
					if(b){
						body.hide();
						op.afterclose(body);
					}
				}else{
					body.hide();
				}
				
				var closediv=body.data("closediv")||{length:0};
				jQuery.lockScreen.unlock(closediv);
				return body;
			},
			remove:function(el,op){
				this.close(el,op).remove();
			},
			pop:function(op){
				
				var defaultOp={
						id:"popSingleBox_only_one",
						isonly:true,
						title:"标题",
						msg:"内容",
						css:{},
						concss:{},
						suportEsc:true,//是否支持键盘esc关闭
						//minw:300,//最小宽度计算
						minh:200,//最小高度计算
						yoff:-80,//y轴偏移
						xoff:0,//x轴偏移
						finished:jQuery.noop,//完成后回调
						dragAble:true,
						btnstyle:{height:"30px;"},//按钮父div的样式
						btnsclass:"",
						btns:"",//按钮内容
						beforeclose:function(body){
			    			//alert("关闭");
			    			return true;
						},
						afterclose:function(body){
			    			//alert("关闭le");
			    		},
			    		islock:true,
						loc:function(m,op){
							var mh=op.minh;
							mh=Math.max(m.height(),mh);
							var l=Math.max((jQuery(window).width()-m.width()+op.xoff*1)/2,0);
							var r=Math.max((jQuery(window).height()-mh+op.yoff*1)/2,0);
							return {left:l,top:r};
						},//初始位置
						isfloat:true,//是否浮动
						suportEsc:true//是否支持esc关闭
				};
				
				op=jQuery.extend({},defaultOp,op);
				op.concss=jQuery.extend({},{"max-height":jQuery(window).height()-120+"px","overflow":"auto"},op.concss);
				var defTemplate='<div class="mesWindow" style="display:none;margin:0 auto;">'+
		        '<div class="mesWindowTop"><div class="msg_title tiptop" style="width:auto;"><span class="titleText"></span><a class="close a_close" style="float:right" href="javascript:void(0)" onclick="return false;"></a></div></div>'+
		        //内容部分
		        '<div class="mesWindowContent" style="height:auto;min-height:50px;">'+
		        '</div>'+
		        //按钮部分
		        (op.btns.length>0?'<dd class="mesWindowbtn tipbtn'+(op.btnsclass||"")+'"></dd>':"")+
		        '</div>';
				var body=jQuery(defTemplate);
				body.css(op.css);
				if(op.isonly){var bef=jQuery("#"+op.id);body.attr("id",op.id);bef.remove();}
				body.find("a.close").bind("click",function(){
					peon.pop.close(this,op);
				});
				body.find(".mesWindowContent").css(op.concss).html(jQuery(op.msg).show());
				if(op.btns.length>0){
					if(typeof op.btns =="string"){
						body.find(".mesWindowbtn").css(op.btnstyle).append(op.btns);
					}else{
						jQuery.each(op.btns,function(i,t){
							body.find(".mesWindowbtn").css(op.btnstyle).append(t);
						});
					}
				}
				body.find(".msg_title").find(".titleText").html(op.title);
				body.appendTo("body");
				if(op.isfloat){
					body.floatdiv(op.loc(body,op));
				}else{
					body.offset(op.loc(body,op));
				}
				if(op.islock){
					var cdiv=jQuery.lockScreen.lock(body,{opacity:0.7});
					body.data("closediv",cdiv);
				}
				body.fadeIn(0,function(){op.suportEsc&&peon.pop.adaptKey(body,op);op.finished&&op.finished(body,op);});
				if(op.dragAble){
					var hand;
					if(op.hand){
						hand=op.hand(body);
					}else{
						hand=body.find(".mesWindowTop").eq(0);
					}
					body.dragAble({hand:hand});
					hand.css("cursor","move");
				}
				return body;
			},
			adaptKey:function(body,op){
				jQuery.currentFocusElment=body[0];
				body.keyAwarePress([
	        		{
	        			key:"ESC",
	        			success:function(){
	        				peon.pop.close(body.find(".close").get(0),op);
	        			}
	    			}
	    			]
	    		);
			}
			
			
		};
	
	
	peon.popx={
			warn:function(op){
				var str='<div class="tipinfo">'+
				'	<span><div class="tip_icon_normal tip_icon_warn1"/></span>'+
				'    <div class="tipright">'+
				'        <p class="tipinfo_text">'+op.msg+'</p>'+
				'    </div>'+
				'</div>';
				op.msg=str;
				var bo=peon.pop.alert(op);
				//bo.find(".tipinfo_text").html(op.msg);
			},
			tip:function(op){
				var str='<div class="tipinfo">'+
				'	<span><div class="tip_icon_normal tip_icon_ok1"/></span>'+
				'    <div class="tipright">'+
				'        <p class="tipinfo_text">'+op.msg+'</p>'+
				'    </div>'+
				'</div>';
				op.msg=str;
				var bo=peon.pop.alert(op);
				//bo.find(".tipinfo_text").html(op.msg);
			},
			error:function(op){
				var str='<div class="tipinfo">'+
				'	<span><div class="tip_icon_normal tip_icon_error1"/></span>'+
				'    <div class="tipright">'+
				'        <p class="tipinfo_text">'+op.msg+'</p>'+
				'    </div>'+
				'</div>';
				op.msg=str;
				var bo=peon.pop.alert(op);
				//bo.find(".tipinfo_text").html(op.msg);
			},
			confirm:function(op){
				var str='<div class="tipinfo">'+
				'	<span><div class="tip_icon_normal tip_icon_edit1"/></span>'+
				'    <div class="tipright">'+
				'        <p class="tipinfo_text">'+op.msg+'</p>'+
				'    </div>'+
				'</div>';
				op.msg=str;
				peon.pop.confirm(op);
			}
			
	};
	
	
	
	
	//命令解析
	jQuery(function(){
		jQuery("[command]").each(function(){
			var command=jQuery(this).attr("command");
			jQuery(this).html( window[command][jQuery(this).text()] );
		});
	});
	//命令解析
	jQuery(function(){
		jQuery("[command_list]").each(function(){
			var commands=jQuery(this).attr("command_list");
			var vs=jQuery(this).text().split(",");
			var sz=[];
			for(var v in vs){
				sz.push( window[commands][vs[v]] );
			}
			jQuery(this).html(sz.join(" | "));
		});
	});
	
	
	/**
	 * by HECJ
	 */
	jQuery.ajaxSetup({  
	    contentType : "application/x-www-form-urlencoded;charset=utf-8",  
	    complete : function(XMLHttpRequest, textStatus) { 
	        if (XMLHttpRequest.status == 999) {  
				window.top.location = "/login";
	        }
	    }
	});

	