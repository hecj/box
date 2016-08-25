$(document).ready(function(){
	var jump1=document.getElementById("jump1"); 
    var timer=6;
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
	fun();
})

$("#directBtn").click(function(){
	window.location.href="/";
})
