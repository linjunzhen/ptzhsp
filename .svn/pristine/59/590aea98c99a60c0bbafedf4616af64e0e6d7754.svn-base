
/**
 * 
 * @param cid环节编号
 * @return
 */
function loadFlow(tachecode){
 	var flag=myDiagram.isModified;
 	var idx = document.title.indexOf("*");
 	/**var src=document.getElementById("img_"+tachecode).src;//everoot+"/webpage/flowchart/images/";
	var pp=src.indexOf(".png");
	src=src.substr(0,pp)+"_click.png";
	document.getElementById("img_"+tachecode).src=src; 
 	if(tacheCode!=null&&tacheCode!=undefined){
 		var old=document.getElementById("img_"+tacheCode).src;//everoot+"/webpage/flowchart/images/";
 		var oldpp=old.indexOf("_click.png");
 		old=old.substr(0,oldpp)+".png";
		document.getElementById("img_"+tacheCode).src=old;
 	}  **/
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
 						centerDiagram();
					}	
				}
			}
		});
 		myDiagram.isModified = false;
 		//diagramCenter();
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
