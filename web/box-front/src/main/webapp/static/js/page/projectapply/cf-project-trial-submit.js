/*初审、复审页面提交定时器*/
$(function(){
	/*参数demo、timer*/
	var jump1=document.getElementById("jump1"); 
    var timer=6;
   	fun();
	function fun(){
		timer--;
		jump1.innerHTML=timer;
		if(timer>0)
		{
			setTimeout(fun,1000);
		}
		else
		{
			window.location.href="/";
		}
	}
})