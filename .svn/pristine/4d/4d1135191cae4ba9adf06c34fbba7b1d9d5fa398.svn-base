/**
 * 
 * @param cid环节编号
 * @return
 */
function loadFlow(tachecode){
 	var flag=myDiagram.isModified;
 	var idx = document.title.indexOf("*");
 		$.ajax({
			type : "post",
			url : everoot+"/flowChartController.do?getflow",
			dataType : "json",
			data : {
				tacheCode : tachecode
			},
			async : false,
			success : function(data) {
				if(typeof(data.flowInfo) == "undefined"){
					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
				}else{
					myDiagram.div.style.height=parseFloat(data.flowHeight)+100+"px";
					document.getElementById("diagramHeight").value=parseFloat(data.flowHeight)+100+"px";
					document.getElementById("mySavedModel").value=data.flowInfo;
					myDiagram.model = go.Model.fromJson(data.flowInfo);
				}
			}
		});
 		myDiagram.isModified = false;
}

