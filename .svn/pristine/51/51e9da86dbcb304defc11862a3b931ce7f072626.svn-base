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
			url : everoot+"/flowChange.do?getflow",
			dataType : "json",
			data : {
				tacheCode : tachecode
			},
			async : false,
			success : function(data) {
				if(typeof(data.tacheId) == "undefined"){
					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
					tacheCode=tachecode;
					tacheId='';
				}else{
					if(typeof(data.flowInfo) == "undefined"){
						tacheCode=tachecode;
 						tacheId=data.tacheId;
 						busiCode=data.busiCode;
						document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    		myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
					}else{
 						tacheCode=tachecode;
 						tacheId=data.tacheId;
 						busiCode=data.busiCode;
						//document.getElementById("sample").style.height=parseFloat(data.flowHeight)+106+"px";
						myDiagram.div.style.height=parseFloat(data.flowHeight)+100+"px";
						document.getElementById("diagramHeight").value=parseFloat(data.flowHeight)+100+"px";
						document.getElementById("mySavedModel").value=data.flowInfo;
 						myDiagram.model = go.Model.fromJson(data.flowInfo);
					}	
				}
			}
		});
 		myDiagram.isModified = false;
	//setUpperNode(3);
}

function submitFactor(){
	 $.ajax({
		type : "post",
		url : everoot+"/monitorNodeController.do?submitFactor",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			busCode:busCode
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				alert("提交审核成功!");
			}else{
				alert("提交审核失败!");
			}
		},
		error:function(msg){
			alert(JSON.stringify(msg));
		}
	});
}
