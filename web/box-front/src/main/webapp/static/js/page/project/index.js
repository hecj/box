  	var currURL ="/project?";
  	
	function loadCategory(id,status){
		currURL = currURL + "category="+id+"&status="+status;
		location = currURL;
	}
	
	function loadStatus(id,category){
		currURL = currURL + "status="+id+"&category="+category;
		location = currURL;
	}
				
			
			
			
		