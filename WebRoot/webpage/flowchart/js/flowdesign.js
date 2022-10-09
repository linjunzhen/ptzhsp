/**
 * 
 * @param cid环节编号
 * @return
 */
function loadFlow(tachecode){
 	var flag=myDiagram.isModified;
 	var idx = document.title.indexOf("*");
 	//document.getElementById("span_"+index).style.color="red";
 	/**
 	var src=document.getElementById("img_"+tachecode).src;//everoot+"/webpage/flowchart/images/";
	var pp=src.indexOf(".png");
	src=src.substr(0,pp)+"_click.png";
	document.getElementById("img_"+tachecode).src=src; 
 	if(tacheCode!=null&&tacheCode!=undefined){
 		var old=document.getElementById("img_"+tacheCode).src;//everoot+"/webpage/flowchart/images/";
 		var oldpp=old.indexOf("_click.png");
 		old=old.substr(0,oldpp)+".png";
		document.getElementById("img_"+tacheCode).src=old;
 	} **/

 	if((templateFlag||(flag&&idx>0))&&confirm("要保存当前流程图操作吗？")){
 		cancelSelected();
 		saveflow(tachecode);
 	}else{
 		$.ajax({
			type : "post",
			url : everoot+"/flowChartController.do?getflow",
			dataType : "json",
			data : {
				tacheCode : tachecode
			},
			async : false,
			success : function(data) {
				myDiagram.isModified = false;
				templateFlag=false;
				if(data==null||data==undefined){
					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModelBak").value);
					tacheCode=tachecode;
					$("#tacheCode").val(tachecode);
					tacheId='';
				}else{
					if(typeof(data.flowInfo) == "undefined"){
						tacheCode=data.tacheCode;
 						tacheId=data.tacheId;
 						busCode=data.busCode;
 						$("#tacheCode").val(data.tacheCode);
						document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    		myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    		document.getElementById("diagramHeight").value=parseFloat(screen.availHeight)+"px";
 			    		myDiagram.div.style.height=parseFloat(screen.availHeight)-10+"px";
					}else{
						tacheCode=data.tacheCode;
						tacheId=data.tacheId;
						busCode=data.busCode;
						$("#tacheCode").val(data.tacheCode);
						myDiagram.div.style.height=parseFloat(data.flowHeight)+50+"px";
						document.getElementById("diagramHeight").value=parseFloat(data.flowHeight)+"px";
						document.getElementById("mySavedModel").value=data.flowInfo;
 						myDiagram.model = go.Model.fromJson(data.flowInfo);
 						//document.getElementById("mySvg").innerHTML=data.flowSvg;
					}	
				}
			}
		});
 		/**
 			var src=everoot+"/webpage/flowchart/images/";
 			if(flag=='1'){
 				src+="arrow_start_click.png";
 			}else{
 				src+="arrow_go_click.png";
 			}
 			//alert($("#img_"+code).attr("src"));
 			document.getElementById("img_"+tachecode).src=src; **/
	}
 	myDiagram.isModified = false;
	templateFlag=false;
	//diagramCenter();
	//var mc=myDiagram.computeBounds();
	//myDiagram.initialPosition.x=300;
}
/**
 * 检查是否有孤立点
 * @return
 */
function checkNode(){
	var nodeArray=myDiagram.model.nodeDataArray;
   	for(var i=0;i<nodeArray.length;i++){
   		var cat=nodeArray[i].category;
   		var node=myDiagram.findNodeForKey(nodeArray[i].key);
   		var it=node.findLinksConnected();
    	if(it.count==0&&cat==""){
    		return true;
    	}
   	}
   	return false;
}

 	
function saveflow(tachecode){
		  handleNode();
		  handleLink();
 		 var height=document.getElementById("diagramHeight").value;//myDiagram.div.style.height;
 		 var isAutoCreate=$("input[name='isAutoCreate']:checked").val();
 		 if(isAutoCreate=="2"){
			 if(!checkNodeId()){
				 alert("存在节点没有设置过程编码，请确认全部设置!");
				 return;
			 }
	     }
 		var flowInfo = myDiagram.model.toJson();
 		makeSVG("mySvg");
 		var flowSvg=document.getElementById("mySvg").innerHTML;
	    	 $.ajax({
	 			type : "post",
	 			url : everoot+"/flowChartController.do?saveflow",
	 			dataType : "json",
	 			data : {
	 				tacheCode : tacheCode,
	 				tacheId:tacheId,
	 				flowInfo:flowInfo,
	 				flowSvg:flowSvg,
	 				nextTacheCode:tachecode,
	 				flowHeight:height,
	 				isAutoCreate:isAutoCreate
	 			},
	 			async : false,
	 			success : function(data) {
	 				myDiagram.isModified = false;
	 				templateFlag=false;
	 				if(data==null||data==undefined||data==""){
	 					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
	  			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
	 					tacheCode=tachecode;
	 					$("#tacheCode").val(tachecode);
	 					tacheId='';
	 				}else{
	 				if(typeof(data.flowInfo) == "undefined"){
	  			    	tacheCode=tachecode;
	 					tacheId=data.tacheId;
	 					busCode=data.busCode;
	 					$("#tacheCode").val(data.tacheCode);
	 					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
	  			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
	  			    	document.getElementById("diagramHeight").value=parseFloat(screen.availHeight)+"px";
	 				}else{
	  					tacheCode=tachecode;
	 					tacheId=data.tacheId;
	 					busiCode=data.busCode;
	 					$("#tacheCode").val(tachecode);
	 					//document.getElementById("sample").style.height=parseFloat(data.flowHeight)+106+"px";
	 					myDiagram.div.style.height=parseFloat(data.flowHeight)+60+"px";
	 					document.getElementById("diagramHeight").value=parseFloat(data.flowHeight)+"px";
	 					document.getElementById("mySavedModel").value=data.flowInfo;
	  					myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
	 				}	
	 				}
	 			},
	 			error:function(msg){
	 				//alert(JSON.stringify(msg));
	 			}
	 		});
	  		myDiagram.isModified = false; 
	  		templateFlag=false;
 }

var noIdNode="";
/**
 * 检查节点是否有ID 
 * @return
 */
function checkNodeId(){
	var jsonstr=myDiagram.model.toJson();
	var model = myDiagram.model;
	var jsonObj=eval('('+jsonstr+')');
	var nodes=jsonObj.nodeDataArray;
	var tcode=$("#tacheCode").val();
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].id==null||nodes[i].id==undefined){
			if(nodes[i].category=="start"||nodes[i].category=="end"){/**
				var node=myDiagram.findNodeForKey(nodes[i].key);
				model.startTransaction("setNodeId");
				model.setDataProperty(node.data, "id",tcode+".0");
				model.commitTransaction("setNodeId"); **/
			}else{
				noIdNode=JSON.stringify(nodes[i].text);
				return false;
			}
		}
	}
	return true;
}
function tempSaveFlow(){
	 handleNode();
	 handleLink();
	 var height=document.getElementById("diagramHeight").value;//myDiagram.div.style.height;
	 //tacheCode=$("#tacheCode").val();
	 var isAutoCreate=$("input[name='isAutoCreate']:checked").val();
	 if(isAutoCreate=="2"){
		if(!checkNodeId()){
			//alert("存在节点没有设置过程编码，请确认全部设置!");
			alert("节点"+noIdNode+"没有设置过程编码，请确认全部设置!");
			return;
		}
	  }
	 cancelSelected();
	 var flowInfo = myDiagram.model.toJson();
		makeSVG("mySvg");
		var flowSvg=document.getElementById("mySvg").innerHTML;
		  $.ajax({
				type : "post",
				url : everoot+"/flowChartController.do?tempSave",
				dataType : "text",
				data : {
					tacheCode : tacheCode,
					tacheId:tacheId,
					flowInfo:flowInfo,
					flowSvg:flowSvg,
					flowHeight:height,
					isAutoCreate:isAutoCreate
				},
				async : false,
				success : function(data) {
					if(data== "success"){
						//alert("保存成功!");
						$.messager.alert("系统提示", "暂存成功", "info");
						myDiagram.isModified = false;
						templateFlag=false;
					}else{
						//alert("暂存失败!");
						$.messager.alert("系统提示", "暂存失败", "error");
					}
				},
				error:function(msg){
					alert(JSON.stringify(msg));
				}
			});
			document.getElementById("tempSave").disabled=true;
}

function submitAudit(){
	 handleNode();
	 handleLink();
	 var height=document.getElementById("diagramHeight").value;//myDiagram.div.style.height;
	 //tacheCode=$("#tacheCode").val();
	 var isAutoCreate=$("input[name='isAutoCreate']:checked").val();
	 if(isAutoCreate=="2"){
		if(!checkNodeId()){
			//alert("存在节点没有设置过程编码，请确认全部设置!");
			//alert("节点"+noIdNode+"没有设置过程编码，请确认全部设置!");
			$.messager.alert("系统提示", "存在节点没有设置过程编码，请确认全部设置!", "info");
			return;
		}
	  }
	 cancelSelected();
	 var flowInfo = myDiagram.model.toJson();
		makeSVG("mySvg");
		var flowSvg=document.getElementById("mySvg").innerHTML;
		  $.ajax({
				type : "post",
				url : everoot+"/flowChartController.do?tempSave",
				dataType : "text",
				data : {
					tacheCode : tacheCode,
					tacheId:tacheId,
					flowInfo:flowInfo,
					flowSvg:flowSvg,
					flowHeight:height,
					isAutoCreate:isAutoCreate
				},
				async : false,
				success : function(data) {
					if(data== "success"){
						$.ajax({
							type : "post",
							url : everoot+"/flowChartController.do?submitAudit",
							dataType : "text",
							data : {
								tacheCode : tacheCode,
								busCode:busCode
							},
							async : false,
							success : function(data) {
								if(data== "success"){
									$.messager.alert("系统提示", "提交成功", "info",function(){
										window.opener.reloadBusSpList();
										window.close();
									});
									//alert("提交成功!");
								}else{
									$.messager.alert("系统提示", "提交失败", "error");
								}
							},
							error:function(msg){
								alert(JSON.stringify(msg));
							}
						});
					}else{
						//alert("暂存失败!");
						$.messager.alert("系统提示", "提交失败!", "error");
					}
				}
			});
}
function submitAuditOld(){
	tempSaveFlow();
	 $.ajax({
		type : "post",
		url : everoot+"/flowChartController.do?submitAudit",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			busCode:busCode
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				$.messager.alert("系统提示", "提交成功", "info",function(){
					window.opener.reloadBusSpList();
					window.close();
				});
				//alert("提交成功!");
			}else{
				$.messager.alert("系统提示", "提交失败", "error");
			}
		},
		error:function(msg){
			alert(JSON.stringify(msg));
		}
	});
}

function loadTemplate(){
	$.ajax({
		type : "post",
		url : everoot+"/flowChartController.do?getTemplate",
		dataType : "json",
		data : {
		},
		async : false,
		success : function(data) {
			if(typeof(data.TEMPLATE_CONTENT)!= "undefined"){
				document.getElementById("mySavedModel").value=data.TEMPLATE_CONTENT;
			    myDiagram.model = go.Model.fromJson(data.TEMPLATE_CONTENT);
			    document.getElementById("diagramHeight").value=parseFloat(data.FLOW_HEIGHT)+"px";
			}
		}
	});
}

