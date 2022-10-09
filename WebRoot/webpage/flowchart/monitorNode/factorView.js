
/**
 * 监察点点击事件
 */
function monitorClk(flg){
	var state=0;
	if(flg=='yes'){
		state=1;
	}
	var processCode=document.getElementById("processCode").value;
	$.ajax({
			type : "post",
			url : everoot+"/monitorNodeController.do?updateNodeState",
			dataType : "json",
			data : {
				processCode : processCode,
				status:state
			},
			async : false,
			success : function(data) {
				if(flg=='yes'){
					document.getElementById("factorInfoDiv").style.display='';
					document.getElementById("monitorNodeYes").style.background='lightgray';
					document.getElementById("noMonitor").style.background='white';
					loadFactorList(processCode);
				}else{
					document.getElementById("factorInfoDiv").style.display='none';
					document.getElementById("monitorNodeYes").style.background='white';
					document.getElementById("noMonitor").style.background='lightgray';
				}
			}
	});
}


function loadFactorList(processCode){
	$.ajax({
			type : "post",
			url : everoot+"/monitorNodeController.do?getFactorInfoList",
			dataType : "json",
			data : {
				processCode : processCode
			},
			async : false,
			success : function(data) {
				var html = "";
				var num = 1;
				if(data.length>0){
					html+='<thead><tr><th style="width:35px;border-top:solid 1px lightgray;word-break:keep-all;white-space:nowrap;line-height:26px;" class="tdOver">编号 </th>';
					html+='<th style="width:120px;border-top:solid 1px lightgray;line-height:26px;" class="tdOver">要素名称</th>';
					html+='<th style="width:120px;border-top:solid 1px lightgray;line-height:26px;" class="tdOver">要素描述</th>';
					//html+='<th style="width:60px;border:solid 1px #add9c0;" class="tdOver">操作</th>';
					html+='</tr></thead>';
					for (var i = 0; i < data.length; i++){
						var desc=data[i].RULE_DESC==null?"":data[i].RULE_DESC;
						 html+='<tr style="border:solid 1px #add9c0;"><td style="width: 35px;align:center;" class="tdOver">'+num
						 		+'</td><td class="tdOver" style="width:120px;word-wrap:break-word;overflow: hidden;text-overflow:ellipsis;" >'
						 		+data[i].SUPER_ELEMENTS+'</td><td class="tdOver" style="width:120px;word-wrap: break-word; word-break:break-all;" >'+desc+'</td>'
						 		+'</tr>';
						 num++;
					} 
					//document.getElementById("factorIsTr").style.display='';
					//document.getElementById("factorInfoDiv").style.display='';
					$("#factorTable").html(html);
				}
				if(html==""){
					html+='<thead><tr><th style="width:36px;border-top:solid 1px lightgray;" class="tdOver">编号 </th>';
					html+='<th style="width:110px;border-top:solid 1px lightgray;" class="tdOver">要素名称</th>';
					html+='<th style="width:130px;border-top:solid 1px lightgray;" class="tdOver">监察类型</th>';
					html+='<th style="width:46px;border-top:solid 1px lightgray;" class="tdOver">要素描述</th>';
					html+='</tr></thead>';
					html += "<tr><td colspan='3'>暂无数据</td></tr>";
					$("#factorTable").html(html);
					//document.getElementById("factorIsTr").style.display='';
					//document.getElementById("factorInfoDiv").style.display='none';
				}
			}
		});
}

function clkYesLoad(){
	var processCode=document.getElementById("processCode").value;
	$.ajax({
			type : "post",
			url : everoot+"/monitorNodeController.do?getFactorInfoList",
			dataType : "json",
			data : {
				processCode : processCode
			},
			async : false,
			success : function(data) {
				var html = "";
				var num = 1;
				if(data.length>0){
					html+='<thead><tr><th style="width:36px;border:solid 1px #add9c0;">编号 </th>';
					html+='<th style="width:130px;border:solid 1px #add9c0;" class="tdOver">要素名称</th>';
					html+='<th style="width:150px;border:solid 1px #add9c0;" class="tdOver">要素描述</th>';
					//html+='<th style="width:60px;border:solid 1px #add9c0;" class="tdOver">操作</th>';
					html+='</tr></thead>';
					for (var i = 0; i < data.length; i++){
						var desc=data[i].RULE_DESC==null?"":data[i].RULE_DESC;
						 html+='<tr><td style="width: 36px;align:center;border:solid 1px #add9c0;" class="tdOver">'+num
						 		+'</td><td style="width:130px;border:solid 1px #add9c0;overflow: hidden;text-overflow:ellipsis;" class="tdOver">'
						 		+data[i].SUPER_ELEMENTS+'</td><td style="width:150px;border:solid 1px #add9c0;" class="tdOver">'+desc+'</td>'
						 		+'</tr>';
						 num++;
					} 
					document.getElementById("factorInfoDiv").style.display='';
					$("#factorTable").html(html);
				}
				if(html==""){
					html+='<thead><tr><th style="width:36px;border:solid 1px #add9c0;">编号 </th>';
					html+='<th style="width:110px;border:solid 1px #add9c0;">要素名称</th>';
					html+='<th style="width:130px;border:solid 1px #add9c0;">要素描述</th>';
					//html+='<th style="width:60px;border:solid 1px #add9c0;">操作</th>';
					html+='</tr></thead>';
					html += "<tr><td colspan='3'>暂无数据</td></tr>";
					$("#factorTable").html(html);
				}
			}
		});
}


function showMonitor(processCode){
	var processCode=document.getElementById("processCode").value;
	$.ajax({
			type : "post",
			url : everoot+"/monitorNodeController.do?getNodeState",
			dataType : "json",
			data : {
				processCode : processCode
			},
			async : false,
			success : function(data) {
				if(data.isMonitorNode=="1"){
					document.getElementById("factorInfoDiv").style.display='';
					//document.getElementById("factorIsTr").style.display='';
					document.getElementById("monitorNodeYes").style.background='lightgray';
					document.getElementById("monitorNodeYes").checked='checked';
					document.getElementById("noMonitor").style.background='white';
					loadFactorList(processCode);
				}else{
					document.getElementById("factorInfoDiv").style.display='none';
					document.getElementById("monitorNodeYes").style.background='white';
					document.getElementById("noMonitor").style.background='lightgray';
					document.getElementById("noMonitor").checked='checked';
					//document.getElementById("factorIsTr").style.display='';
				}
			}
	});
}
/**
* 编辑列表记录
*/
function editFactorGridRecord(nodeId) {
	var entityId =nodeId;// AppUtil.getEditDataGridRecord("monitorNodeGrid");
	if (entityId) {
		showFactorWindow(entityId);
	}
}

/**
 * 显示要素信息对话框
 */
function showFactorWindow(entityId) {
	if(entityId){
		$.dialog.open("monitorNodeController.do?factorInfo&entityId=" + entityId, {
    		title : "要素信息",
            width:"500px",
            lock: true,
            resize:false,
            height:"200px",
    	}, false);
	}else{
		var processCode =document.getElementById("processCode").value;
		var processName=document.getElementById("nodeName").value;
		//var tacheCode=document.getElementById("tacheCode").value;
		if (processCode){
			var url = everoot+"/monitorNodeController.do?factorInfo&entityId="+entityId
			+"&processCode="+processCode+"&processName="+processName+"&tacheCode="+tacheCode;
			$.dialog.open(url, {
	    		title : "要素信息",
	            width:"500px",
	            lock: true,
	            resize:false,
	            height:"200px",
	    	}, false);
		}else{
			alert('请选择过程节点!');
		}			
	}		
}

function delFactorInfo(nodeId,nodeCode){
	art.dialog.confirm("您确定要删除掉该记录吗?", function() {
		var url="monitorNodeController.do?delFactorInfo&RULE_ID="+nodeId;
		$.post(url,{
			   RULE_ID:nodeId
		    }, function(responseText, status, xhr) {
		    	var resultJson = $.parseJSON(responseText);
				if(resultJson.success == true){
					art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					loadFactorList(nodeCode);
				}else{
					art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
		});
	});
}

function submitFactor(flg){
	 $.ajax({
		type : "post",
		url : everoot+"/monitorNodeController.do?submitAudit",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			busCode:busCode,
			flag:flg
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				alert("提交审核成功!");
				window.opener.reloadNodeList();
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

function backDraw(){
	 $.ajax({
		type : "post",
		url : everoot+"/monitorNodeController.do?backDraw",
		dataType : "text",
		data : {
			busCode:busCode
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				alert("返回绘图成功!");
				window.opener.reloadNodeList();
				window.close();
			}else{
				alert("返回绘图失败!");
			}
		},
		error:function(msg){
			alert(JSON.stringify(msg));
		}
	});
}
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
			url : everoot+"/monitorNodeController.do?getNodeflow",
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
 						refreshBase();
					}	
				}
			}
		});
 		myDiagram.isModified = false;
 		//diagramCenter();
	//setUpperNode(3);
}

function refreshBase(){
	document.getElementById("nodeName").value="";
	document.getElementById("processCode").value="";
	document.getElementById("factorInfoDiv").style.display='none';
	//document.getElementById("noMonitor").checked='checked';
	document.getElementById("monitorNodeYes").style.background='white';
	document.getElementById("noMonitor").style.background='lightgray';
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