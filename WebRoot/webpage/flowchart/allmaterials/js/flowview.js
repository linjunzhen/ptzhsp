/**
 * 根据变更的类别加载不同流程图
 * @param tachecode
 * @param isFlowChange
 * @param lastApplyId
 * @param applyId
 */
function loadFlow(tachecode,isFlowChange,lastApplyId,applyId,svgId){
 	var flag=myDiagram.isModified;
 	var idx = document.title.indexOf("*");
	$.ajax({
		type : "post",
		url : everoot+"/allMaterialsController.do?getChangeflow",
		dataType : "json",
		data : {
			'tacheCode':tachecode,
			'isFlowChange':isFlowChange,
			'lastApplyId':lastApplyId,
			'applyId':applyId
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
					myDiagram.div.style.height=parseFloat(data.flowHeight)+36+"px";
					document.getElementById("diagramHeight").value=parseFloat(data.flowHeight)+"px";
					//document.getElementById("mySavedModel").value=data.flowInfo;
					myDiagram.model = go.Model.fromJson(data.flowInfo);
				}
				if(document.getElementById(svgId)){
					if(typeof(data.lastFlowSvg) != "undefined" &&  data.lastFlowSvg != 'null'){
						document.getElementById(svgId).innerHTML = "";
						document.getElementById(svgId).innerHTML = data.lastFlowSvg;
					}else{
						document.getElementById(svgId).innerHTML = "找不到上一个版本图或此为变更新增的环节。";
					}
				}				
			}
		}
	});
	myDiagram.isModified = false;
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
