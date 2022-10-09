
/**
 * 监察点点击事件
 */
function monitorClk(flg){
	var state=0;
	if(flg=='yes'){
		state=1;
	}
	var processCode=document.getElementById("processCode").value;
	var applyId=document.getElementById("applyId").value;
	$.ajax({
			type : "post",
			url : everoot+"/processChange.do?updateNodeState",
			dataType : "json",
			data : {
				processCode : processCode,
				status:state,
				applyId:applyId
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
	var applyId=document.getElementById("applyId").value;
	$.ajax({
			type : "post",
			url : everoot+"/processChange.do?getFactorInfoList",
			dataType : "json",
			data : {
				processCode : processCode,
				applyId:applyId
			},
			async : false,
			success : function(data) {
				var html = "";
				var num = 1;
				if(data.length>0){
					html+='<thead><tr><th style="width:35px;border-top:solid 1px lightgray;word-break:keep-all;white-space:nowrap;line-height:26px;" class="tdOver">编号 </th>';
					html+='<th style="width:120px;border-top:solid 1px lightgray;line-height:26px;" class="tdOver">要素名称</th>';
					html+='<th style="width:55px;border-top:solid 1px lightgray;word-break:keep-all;white-space:nowrap;line-height:26px;" class="tdOver">监察类型</th>';
					html+='<th style="width:120px;border-top:solid 1px lightgray;line-height:26px;" class="tdOver">要素描述</th>';
					html+='</tr></thead>';
					for (var i = 0; i < data.length; i++){
						 
						/**
						 html+='<tr><td style="width: 36px;align:center;" class="tdOver">'+num
					 		+'</td><td style="width:110px;" class="tdOver">'
					 		+data[i].SUPER_ELEMENTS+'</td><td style="width:120px;" class="tdOver">'
					 		+data[i].DIC_NAME+'</td>'
					 		+'<td style="width:60px;" class="tdOver">'
					 		//+' <a href="javascript:void(0);" class="easyui-linkbutton" onclick="addFactorChange(\''+data[i].CHANGE_ID+'\');">修改</a>'
					 		+' <a href="javascript:void(0);" class="easyui-linkbutton"'
					 		+' onclick="delFactorInfo(\''+data[i].CHANGE_ID+'\',\''+data[i].PROCESS_CODE+'\');">删除</a>'
					 		**/
						var desc=data[i].RULE_DESC==null?"":data[i].RULE_DESC;
						html+='<tr>'
						+'<td style="width: 35px;align:center;" class="tdOver">'+num+'</td>'
				 		+'<td class="tdOver" style="width:120px;word-wrap:break-word;overflow: hidden;text-overflow:ellipsis;" >'
				 		+data[i].SUPER_ELEMENTS+'</td><td style="width:55px;word-wrap:break-word;word-break:break-all;" class="tdOver">'
				 		+data[i].DIC_NAME+'</td>'
				 		+'<td class="tdOver" style="width:120px;word-wrap: break-word; word-break:break-all;" >'
				 		+desc
					 		+'</td></tr>';
						 num++;
					} 
					//document.getElementById("factorIsTr").style.display='';
					//document.getElementById("factorInfoDiv").style.display='';
					$("#factorTable").html(html);
					console.log(html);
				}
				if(html==""){
					html+='<thead><tr><th style="width:36px;border-top:solid 1px lightgray;" class="tdOver">编号 </th>';
					html+='<th style="width:110px;border-top:solid 1px lightgray;" class="tdOver">要素名称</th>';
					html+='<th style="width:120px;border-top:solid 1px lightgray;" class="tdOver">监察类型</th>';
					html+='<th style="width:60px;border-top:solid 1px lightgray;" class="tdOver">操作</th>';
					html+='</tr></thead>';
					html += "<tr><td colspan='4'>暂无数据</td></tr>";
					$("#factorTable").html(html);
					//document.getElementById("factorIsTr").style.display='';
					//document.getElementById("factorInfoDiv").style.display='none';
				}
			}
		});
}

function clkYesLoad(){
	var processCode=document.getElementById("processCode").value;
	var applyId=document.getElementById("applyId").value;
	$.ajax({
			type : "post",
			url : everoot+"/processChange.do?getFactorInfoList",
			dataType : "json",
			data : {
				processCode : processCode,
				applyId:applyId
			},
			async : false,
			success : function(data) {
				var html = "";
				var num = 1;
				if(data.length>0){
					html+='<thead><tr><th style="width:36px;border-top:solid 1px lightgray;" class="tdOver">编号 </th>';
					html+='<th style="width:110px;border-top:solid 1px lightgray;" class="tdOver">要素名称</th>';
					html+='<th style="width:130px;border-top:solid 1px lightgray;" class="tdOver">监察类型</th>';
					html+='<th style="width:60px;border-top:solid 1px lightgray;" class="tdOver">操作</th>';
					html+='</tr></thead>';
					for (var i = 0; i < data.length; i++){
						var desc=data[i].RULE_DESC==null?"":data[i].RULE_DESC;
						 html+='<tr><td style="width: 36px;align:center;" class="tdOver">'+num
						 		+'</td><td style="width:110px;overflow: hidden;text-overflow:ellipsis;" class="tdOver">'
						 		+data[i].SUPER_ELEMENTS+'</td><td style="width:130px;" class="tdOver">'
						 		+data[i].DIC_NAME+'</td>'
						 		+'<td style="width:60px;" class="tdOver">'
						 		//+' <a href="javascript:void(0);" class="easyui-linkbutton" onclick="addFactorChange(\''+data[i].CHANGE_ID+'\');">修改</a>'
						 		+' <a href="javascript:void(0);" class="easyui-linkbutton"'
						 		+' onclick="delFactorInfo(\''+data[i].CHANGE_ID+'\',\''+data[i].PROCESS_CODE+'\');">删除</a>'
						 		+'</td></tr>';
						 num++;
					} 
					document.getElementById("factorInfoDiv").style.display='';
					$("#factorTable").html(html);
				}
				if(html==""){
					html+='<thead><tr><th style="width:36px;border-top:solid 1px lightgray;" class="tdOver">编号 </th>';
					html+='<th style="width:110px;border-top:solid 1px lightgray;" class="tdOver">要素名称</th>';
					html+='<th style="width:130px;border-top:solid 1px lightgray;" class="tdOver">监察类型</th>';
					html+='<th style="width:60px;border-top:solid 1px lightgray;" class="tdOver">操作</th>';
					html+='</tr></thead>';
					html += "<tr><td colspan='4'>暂无数据</td></tr>";
					$("#factorTable").html(html);
				}
			}
		});
}


function showMonitor(processCode){
	var processCode=document.getElementById("processCode").value;
	var applyId=document.getElementById("applyId").value;
	$.ajax({
			type : "post",
			url : everoot+"/processChange.do?getNodeState",
			dataType : "json",
			data : {
				processCode : processCode,
				applyId:applyId
			},
			async : false,
			success : function(data) {
				if(data.isMonitorNode=="1"){
					document.getElementById("factorInfoDiv").style.display='';
					document.getElementById("monitorNodeYes").style.background='lightgray';
					document.getElementById("noMonitor").style.background='white';
					document.getElementById("monitorNodeYes").checked=true;
					loadFactorList(processCode);
				}else{
					document.getElementById("noMonitor").checked=true;
					document.getElementById("factorInfoDiv").style.display='none';
					document.getElementById("monitorNodeYes").style.background='white';
					document.getElementById("noMonitor").style.background='lightgray';
				}
			}
	});
}

/**
 * 显示要素信息对话框
 */
/**
 * 显示要素信息对话框
 */
function addFactorChange() {
	var applyId=document.getElementById("applyId").value;
	var processCode =document.getElementById("processCode").value;
	if (processCode){
			var url = everoot+"/processChange.do?factorInfo&applyId="+applyId
			+"&processCode="+processCode+"&tacheCode="+tacheCode;
			$.dialog.open(url, {
	    		title : "要素信息",
	            width:"680px",
	            lock: true,
	            resize:false,
	            height:"460px",
	    	}, false);
		}else{
			alert('请选择过程节点!');
		}				
}
function addFactorChangeOld() {
	var applyId=document.getElementById("applyId").value;
	/**if(entityId){
		$.dialog.open("processChange.do?factorInfo&entityId=" + entityId+"&applyId="+applyId, {
    		title : "要素信息",
            width:"500px",
            lock: true,
            resize:false,
            height:"220px",
    	}, false);
	}else{ **/
		var processCode =document.getElementById("processCode").value;
		var processName=document.getElementById("nodeName").value;
		//var tacheCode=document.getElementById("tacheCode").value;
		if (processCode){
			var url = everoot+"/processChange.do?factorInfo&applyId="+applyId
			+"&processCode="+processCode+"&processName="+processName+"&tacheCode="+tacheCode;
			$.dialog.open(url, {
	    		title : "要素信息",
	            width:"680px",
	            lock: true,
	            resize:false,
	            height:"460px",
	    	}, false);
		}else{
			alert('请选择过程节点!');
		}			
	//}		
}

function delFactorInfo(nodeId,nodeCode){
	art.dialog.confirm("您确定要删除掉该记录吗?", function() {
		var url="processChange.do?delFactorInfo&CHANGE_ID="+nodeId;
		$.post(url,{
			   CHANGE_ID:nodeId
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
	var applyId=document.getElementById("applyId").value;
	 $.ajax({
		type : "post",
		url : everoot+"/processChange.do?submitAudit",
		dataType : "text",
		data : {
			tacheCode : tacheCode,
			busCode:busCode,
			flag:flg,
			applyId:applyId
		},
		async : false,
		success : function(data) {
			if(data== "success"){
				$.messager.alert("系统提示", "提交成功", "info",function(){
					window.opener.reloadNodeList();
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

