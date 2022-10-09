$(function(){
		if($("input[name='FINISH_GETTYPE']:checked").val()=='02'){				
			$("#addr").attr("style","");
			$("#addressee").attr("style","");
			$("#addrmobile").attr("style","");
			$("#addrprov").attr("style","");
			$("#addrcity").attr("style","");
			$("#addrpostcode").attr("style","");
			$("#addrremarks").attr("style","");
		}
	    $("input[name='FINISH_GETTYPE']").click(function(){
	    	var radio = document.getElementsByName("FINISH_GETTYPE"); 
	        for (i=0; i<radio.length; i++) { 
	            if (radio[i].checked) { 
					var str=radio[i].value;
					if("02"==str){
						$("#addr").attr("style","");
						$("#addressee").attr("style","");
						$("#addrmobile").attr("style","");
						$("#addrprov").attr("style","");
						$("#addrcity").attr("style","");
						$("#addrpostcode").attr("style","");
						$("#addrremarks").attr("style","");
					}else{	
						$("#addr").attr("style","display:none;");
						$("#addressee").attr("style","display:none;");
						$("#addrmobile").attr("style","display:none;");
						$("#addrprov").attr("style","display:none;");
						$("#addrcity").attr("style","display:none;");
						$("#addrpostcode").attr("style","display:none;");
						$("#addrremarks").attr("style","display:none;");
					}
	            } 
	        }  
		});
})