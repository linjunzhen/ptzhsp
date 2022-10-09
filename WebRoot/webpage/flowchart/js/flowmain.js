function submitAudit(){
	 handleNode();
	 handleLink();
	 handleNodeKey(tacheCode);
	 var flowInfo = myDiagram.model.toJson();
	 $.ajax({
		type : "post",
		url : everoot+"/flowChartController.do?submitAudit",
		dataType : "json",
		data : {
			tacheCode : tacheCode,
			busiCode:busiCode
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				alert("保存成功!");
			}else{
				alert("暂存失败!");
			}
		},
		error:function(msg){
			alert(JSON.stringify(msg));
		}
	});
	myDiagram.isModified = false;
	window.top.frames["topFrame"].document.getElementById("tempSave").disabled=true;
	window.top.frames["topFrame"].document.getElementById("submitAudit").disabled=true;
}