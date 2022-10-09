/**
 * 
 * @param cid环节编号
 * @return
 */
function loadFlow(tachecode){
 	$.ajax({
			type : "post",
			url : everoot+"/flowChartController.do?getflow",
			dataType : "json",
			data : {
				tacheCode : tachecode
			},
			async : false,
			success : function(data) {
				if(typeof(data.tacheId) == "undefined"){
					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    	document.getElementById("sample").style.height=parseFloat(screen.availHeight)-100+"px";
					tacheCode=tachecode;
					tacheId='';
				}else{
					if(typeof(data.flowInfo) == "undefined"){
						tacheCode=data.tacheCode;
 						tacheId=data.tacheId;
 						busCode=data.busCode;
						document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    		myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    		document.getElementById("sample").style.height=parseFloat(screen.availHeight)+"px";
 			    		document.getElementById("diagramHeight").value=parseFloat(screen.availHeight)+"px";
 			    		myDiagram.div.style.height=parseFloat(screen.availHeight)-10+"px";
					}else{
						tacheCode=data.tacheCode;
						tacheId=data.tacheId;
						busCode=data.busCode;
						document.getElementById("sample").style.height=parseFloat(data.flowHeight)+106+"px";
						//document.getElementById("newPanel").style.height=parseFloat(data.flowHeight)+106+"px";
						myDiagram.div.style.height=parseFloat(data.flowHeight)+50+"px";
						document.getElementById("diagramHeight").value=parseFloat(data.flowHeight)+"px";
						document.getElementById("mySavedModel").value=data.flowInfo;
 						myDiagram.model = go.Model.fromJson(data.flowInfo);
					}	
				}
			}
		});
 		myDiagram.isModified = false;
}

/**
 * 加载旧的流程图
 * @param cid环节编号
 * @return
 */
function loadFlowOld(tachecode){
 	var applyId=document.getElementById("applyId").value;
 	$.ajax({
			type : "post",
			url : everoot+"/flowChange.do?getflowForView",
			dataType : "json",
			data : {
				tacheCode : tachecode,
				applyId:applyId
			},
			async : false,
			success : function(data) {
				if(typeof(data.tacheId) == "undefined"){
					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    	document.getElementById("sample").style.height=parseFloat(screen.availHeight)-100+"px";
					tacheCode=tachecode;
					tacheId='';
				}else{
					if(typeof(data.flowInfo) == "undefined"){
						tacheCode=data.tacheCode;
 						tacheId=data.tacheId;
 						busCode=data.busCode;
						document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    		myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    		document.getElementById("sample").style.height=parseFloat(screen.availHeight)+"px";
 			    		//document.getElementById("diagramHeightOld").value=parseFloat(screen.availHeight)+"px";
 			    		myDiagram.div.style.height=parseFloat(screen.availHeight)-10+"px";
					}else{
						tacheCode=data.tacheCode;
						tacheId=data.tacheId;
						busCode=data.busCode;
						document.getElementById("sample").style.height=parseFloat(data.flowHeight)+106+"px";
						//document.getElementById("newPanel").style.height=parseFloat(data.flowHeight)+106+"px";
						myDiagram.div.style.height=parseFloat(data.flowHeight)+50+"px";
						//document.getElementById("diagramHeight").value=parseFloat(data.flowHeight)+"px";
						document.getElementById("mySavedModel").value=data.flowInfo;
 						myDiagram.model = go.Model.fromJson(data.flowInfo);
					}	
				}
			}
		});
 		myDiagram.isModified = false;
}

 	