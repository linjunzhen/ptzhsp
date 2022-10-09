    var ${startDateName} = {
	    elem: "#${startDateId}",
	    format: "${dateFormat}",
	    istime: ${istime},
	    choose: function(datas){
	        var beginTime = $("input[name='${startDateQueryName}']").val();
	    	var endTime = $("input[name='${endDateQueryName}']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime);
	    		var end = new Date(endTime);
	    		if(start>end){
	    			alert("开始时间必须小于等于结束时间,请重新输入!");
	    			$("input[name='${startDateQueryName}']").val("");
	    		}
	    	}
	    }
	};
	var ${endDateName} = {
	    elem: "#${endDateId}",
	    format: "${dateFormat}",
	    istime: ${istime},
	    choose: function(datas){
	        var beginTime = $("input[name='${startDateQueryName}']").val();
	    	var endTime = $("input[name='${endDateQueryName}']").val();
	    	if(beginTime!=""&&endTime!=""){
	    		var start = new Date(beginTime);
	    		var end = new Date(endTime);
	    		if(start>end){
	    			alert("结束时间必须大于等于开始时间,请重新输入!");
	    			$("input[name='${endDateQueryName}']").val("");
	    		}
	    	}
	    }
	};
	laydate(${startDateName});
	laydate(${endDateName});