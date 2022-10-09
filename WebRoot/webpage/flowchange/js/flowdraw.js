/**
 * 
 * @param cid环节编号
 * @return
 */
function loadFlow(tachecode){
 	var flag=myDiagram.isModified;
 	var idx = document.title.indexOf("*");
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
 	
 	if(flag&&idx>0&&confirm("要保存当前流程图操作吗？")){
 		cancelSelected();
 		saveflow(tachecode);
 	}else{
 		var applyId=document.getElementById("applyId").value;
 		$.ajax({
			type : "post",
			url : everoot+"/flowChange.do?getflow",
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
 			    	//document.getElementById("sample").style.height=parseFloat(screen.availHeight)-100+"px";
					tacheCode=tachecode;
					tacheId='';
				}else{
					if(typeof(data.flowInfo) == "undefined"){
						tacheCode=data.tacheCode;
 						tacheId=data.tacheId;
 						busCode=data.busCode;
						document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    		myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    		//document.getElementById("sample").style.height=parseFloat(screen.availHeight)+"px";
 			    		document.getElementById("diagramHeight").value=parseFloat(screen.availHeight)+"px";
 			    		myDiagram.div.style.height=parseFloat(screen.availHeight)-10+"px";
					}else{
						tacheCode=data.tacheCode;
						tacheId=data.tacheId;
						busCode=data.busCode;
						//document.getElementById("sample").style.height=parseFloat(data.flowHeight)+106+"px";
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
	//setUpperNode(3);
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

/**
 * 检查节点是否有ID 
 * @return
 */
function checkNodeId(){
	var jsonstr=myDiagram.model.toJson();
	var model = myDiagram.model;
	var jsonObj=eval('('+jsonstr+')');
	var nodes=jsonObj.nodeDataArray;
	var tcode=tacheCode;//$("#tacheCode").val();
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].id==null||nodes[i].id==undefined){
			if(nodes[i].category=="start"){
				var node=myDiagram.findNodeForKey(nodes[i].key);
				model.startTransaction("setNodeId");
				model.setDataProperty(node.data, "id",tcode+".0");
				model.commitTransaction("setNodeId");
			}else if(nodes[i].category=="end"){
				var node=myDiagram.findNodeForKey(nodes[i].key);
				model.startTransaction("setNodeId");
				model.setDataProperty(node.data, "id",tcode+".-1");
				model.commitTransaction("setNodeId");
			}else{
				return false;
			}
		}
	}
	return true;
}
 	
function saveflow(tachecode){
		  handleNode();
		  handleLink();
 		 var height=document.getElementById("diagramHeight").value;//myDiagram.div.style.height;
 		 var applyId=document.getElementById("applyId").value;
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
			url : everoot+"/flowChange.do?saveflow",
			dataType : "json",
			data : {
				tacheCode : tacheCode,
				tacheId:tacheId,
				flowInfo:flowInfo,
				flowSvg:flowSvg,
				nextTacheCode:tachecode,
				flowHeight:height,
				applyId:applyId,
				isAutoCreate:isAutoCreate
			},
			async : false,
			success : function(data) {
				if(typeof(data.tacheId) == "undefined"){
					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    	//document.getElementById("sample").style.height="520px";
					tacheCode=tachecode;
					tacheId='';
				}else{
				if(typeof(data.flowInfo) == "undefined"){
 			    	tacheCode=tachecode;
					tacheId=data.tacheId;
					busCode=data.busCode;
					document.getElementById("mySavedModel").value=document.getElementById("mySavedModelBak").value;
 			    	myDiagram.model = go.Model.fromJson(document.getElementById("mySavedModel").value);
 			    	///document.getElementById("sample").style.height="520px";
 			    	document.getElementById("diagramHeight").value=parseFloat(screen.availHeight)+"px";
				}else{
 					tacheCode=tachecode;
					tacheId=data.tacheId;
					busCode=data.busCode;
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
 }

function tempSaveFlow(){
	 handleNode();
	 handleLink();
	 var height=document.getElementById("diagramHeight").value;//myDiagram.div.style.height;
	 var applyId=document.getElementById("applyId").value;
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
		url : everoot+"/flowChange.do?tempSave",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			tacheId:tacheId,
			flowInfo:flowInfo,
			flowSvg:flowSvg,
			flowHeight:height,
			applyId:applyId,
			isAutoCreate:isAutoCreate
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				//alert("保存成功!");
				$.messager.alert("系统提示", "暂存成功", "info");
			}else{
				//alert("暂存失败!");
				$.messager.alert("系统提示", "暂存失败", "error");
			}
		},
		error:function(msg){
			alert(JSON.stringify(msg));
		}
	});
	myDiagram.isModified = false;
	document.getElementById("tempSave").disabled=true;
}

function submitAudit(){
	 handleNode();
	 handleLink();
	 var height=document.getElementById("diagramHeight").value;//myDiagram.div.style.height;
	 var applyId=document.getElementById("applyId").value;
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
		url : everoot+"/flowChange.do?tempSave",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			tacheId:tacheId,
			flowInfo:flowInfo,
			flowSvg:flowSvg,
			flowHeight:height,
			applyId:applyId,
			isAutoCreate:isAutoCreate
		},
		async : false,
		success : function(data) {//保存成功，则提交；
			if(data== "success"){
				busCode=$("#busCode").val();
				 $.ajax({
					type : "post",
					url : everoot+"/flowChange.do?submitAudit",
					dataType : "text",
					data : {
						tacheCode : tacheCode,
						busCode:busCode,
						applyId:applyId
					},
					async : false,
					success : function(data) {
						if(data== "success"){
							$.messager.alert("系统提示", "提交成功", "info",function(){
								window.opener.reloadChangeBusSpList();
								window.close();
							});
						}else{
							$.messager.alert("系统提示", "提交失败", "info");
						}
					},
					error:function(msg){
						alert(JSON.stringify(msg));
					}
				});
			}else{
				$.messager.alert("系统提示", "保存失败", "error");
			}
		}
	});
}
function submitAuditOld(){
	busCode=$("#busCode").val();
	var applyId=document.getElementById("applyId").value;
	 $.ajax({
		type : "post",
		url : everoot+"/flowChange.do?submitAudit",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			busCode:busCode,
			applyId:applyId
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				$.messager.alert("系统提示", "提交成功", "info",function(){
					window.opener.reloadChangeBusSpList();
					window.close();
				});
			}else{
				$.messager.alert("系统提示", "提交失败", "info");
			}
		},
		error:function(msg){
			alert(JSON.stringify(msg));
		}
	});
}

function endAudit(){
	busCode=$("#busCode").val();
	var applyId=document.getElementById("applyId").value;
	 $.ajax({
		type : "post",
		url : everoot+"/flowChange.do?flowPassAudit",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			busCode:busCode,
			applyId:applyId
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				alert("提交审核成功!");
				window.opener.reloadChangeBusSpList();
				window.close();
			}else{
				alert("提交审核失败!");
			}
		},
		error:function(msg){
			alert(JSON.stringify(msg));
		}
	});
}

function loadSvg(evt) {
	svgdoc = evt.target.ownerDocument;
	var node=svgdoc.documentElement;
	var first=document.getElementById("firstTache").value;
	loadFlow(first);
	var p=svgdoc.getElementById("poly_"+first);
	//var pl=document.getElementById("poly_"+first);
	p.setAttribute("fill", "blue");
}
function clkPloy(evt,code){
	loadFlow(code);
	var curid="poly_"+code;
	//var imageTarget = evt.target;
	//var theFill = imageTarget.getAttribute("fill");
	svgdoc = evt.target.ownerDocument;
	var node=svgdoc.documentElement;
	var polygons = node.getElementsByTagName("polygon");
	for(var i=0;i<polygons.length;i++){
		var id= polygons[i].getAttribute("id");
		if(id==curid){
			polygons[i].setAttribute("fill", "blue");
		}else{
			polygons[i].setAttribute("fill", "gray");
		}
	}
} 



