
function genCode(missionId){
	AppUtil.ajaxProgress({
	   url:"codeMissionController.do?genCode",
	   params:{
	   	  missionId:missionId
	   },
	   callback:function(resultJson){
	   	   alert("生成成功!")
	   }
	});
}

function previewCode(missionId){
	window.open("codeMissionController.do?preview&missionId="+missionId, "_blank");
}


function addHoverDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if (treeNode.id != "-1"&&treeNode.isControll==true) {
		if ($("#editHref_"+treeNode.id).length>0) return;
		var editStr = "<a id='editHref_" +treeNode.id+ "'  style='margin:0 0 0 5px;'>创建</a>";
		aObj.append(editStr);
		$("#editHref_" + treeNode.id).bind("click", function() {
			 invokeControlConfigWindow(treeNode);
		});
	} 
}

function removeHoverDom(treeId, treeNode) {
	$("#editHref_"+treeNode.id).unbind().remove();
}

function addControlTreeHoverDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if (treeNode.id != "0") {
		if ($("#addItemHref_"+treeNode.id).length>0) return;
		if ($("#editItemHref_"+treeNode.id).length>0) return;
		if ($("#delItemHref_"+treeNode.id).length>0) return;
		var operateStr = "<a id='addItemHref_" +treeNode.id+ "' title='创建子控件' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_add.png' /></a>";
		operateStr+="<a id='editItemHref_" +treeNode.id+ "' title='编辑' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_edit.png' /></a>";
		operateStr+="<a id='delItemHref_" +treeNode.id+ "' title='删除' style='margin:0 0 0 2px;'><img src='plug-in/easyui-1.4/themes/icons/note_delete.png' /></a>";
		aObj.append(operateStr);
		$("#addItemHref_" + treeNode.id).bind("click", function() {
			showControlConfWindow(treeNode);
		});
		$("#editItemHref_" + treeNode.id).bind("click", function() {
			
		});
		$("#delItemHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId:"controlConfigTree",
				url:"controlConfigController.do?multiDel",
				treeNode:treeNode,
				callback:function(resultJson){
					window.location.reload();
				}
			});
		});
	} 
}

function removeControlTreeHoverDom(treeId, treeNode) {
	$("#addItemHref_"+treeNode.id).unbind().remove();
	$("#editItemHref_"+treeNode.id).unbind().remove();
	$("#delItemHref_"+treeNode.id).unbind().remove();
}
/**
 * 显示控件配置窗口
 * @param {} treeNode
 */
function showControlConfWindow(treeNode){
	var missionId = $("#missionId").val();
	$.layer({
		type : 2,
		maxmin : false,
		title : "配置控件",
		area : ["600px", "450px"],
		iframe : {
			src : "controlConfigController.do?goControlConf&missionId="+missionId+"&PARENT_ID="+treeNode.id
		}
	})
}


/**
 * 显示配置layout窗口
 */
function showLayoutConfWindow(treeNode){
	var missionId = $("#missionId").val();
	$.layer({
		type : 2,
		maxmin : false,
		title : "配置Layout控件",
		area : ["600px", "450px"],
		iframe : {
			src : "codeMissionController.do?goConfig&missionId="+missionId+"&viewName=layoutControl&PARENT_ID=0&CONTROL_NAME="+treeNode.name
		}
	});
}

/**
 * 调用配置窗口
 * @param {} treeNode
 */
function invokeControlConfigWindow(treeNode){
	var missionId = $("#missionId").val();
	if(treeNode.id=="2"){
		showLayoutConfWindow(treeNode);
	}else if(treeNode.id=="11"){
		$.layer({
			type : 2,
			maxmin : false,
			title : "配置表单布局",
			area : ["600px", "200px"],
			iframe : {
				src : "codeMissionController.do?goConfig&missionId="+missionId+"&viewName=formLayoutConfig&PARENT_ID=0&CONTROL_NAME="+treeNode.name
			}
		});
	}
	else if(treeNode.id=="14"){
		$.layer({
			type : 2,
			maxmin : false,
			title : "配置上传控件",
			area : ["600px", "250px"],
			iframe : {
				src : "codeMissionController.do?goConfig&missionId="+missionId+"&viewName=tableUploadConfig&PARENT_ID=0&CONTROL_NAME="+treeNode.name
			}
		});
	}
	else if(treeNode.id=="19"){
		$.layer({
			type : 2,
			maxmin : false,
			title : "配置树与列表代码模版",
			area : ["600px", "250px"],
			iframe : {
				src : "codeMissionController.do?goConfig&missionId="+missionId+"&viewName=treeListConfig&PARENT_ID=0&CONTROL_NAME="+treeNode.name
			}
		});
	}
	else if(treeNode.id=="20"){
		$.layer({
			type : 2,
			maxmin : false,
			title : "树形选择器代码模版",
			area : ["600px", "250px"],
			iframe : {
				src : "codeMissionController.do?goConfig&missionId="+missionId+"&viewName=TreeSelectorConfig&PARENT_ID=0&CONTROL_NAME="+treeNode.name
			}
		});
	}
	else if(treeNode.id=="21"){
		$.layer({
			type : 2,
			maxmin : false,
			title : "TAB组件代码模版",
			area : ["600px", "150px"],
			iframe : {
				src : "codeMissionController.do?goConfig&missionId="+missionId+"&viewName=tabConfig&PARENT_ID=0&CONTROL_NAME="+treeNode.name
			}
		});
	}
}

function onClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		var currentURL = window.location;  
		window.location.href=currentURL;
	}
}



var setting = {
	edit: {
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	view: {
		addHoverDom: addHoverDom,
		selectedMulti: false,
		removeHoverDom : removeHoverDom
	},
	async : {
		enable : true,
		url : "webpage/developer/codeMission/js/controllTree.json"
	},
	callback : {
		onClick : onClick
	}
};

$(document).ready(function(){
	var controlConfTreesetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		view : {
			addHoverDom: addControlTreeHoverDom,
			selectedMulti: false,
			removeHoverDom : removeControlTreeHoverDom
		},
		async : {
			enable : true,
			url : "baseController.do?tree",
			otherParam : {
				"tableName" : "T_MSJW_DEVELOPER_CONTROLCONFIG",
				"idAndNameCol" : "CONFIG_ID,CONTROL_NAME",
				"targetCols" : "PATH,CONTROL_TYPE,PARENT_ID",
				"rootParentId" : "0",
				"isShowRoot" : "true",
				"Q_MISSION_ID_=":$("#missionId").val(),
				"rootName":"已配置控件树"
			}
		}
	};
	$.fn.zTree.init($("#controlTree"), setting);
	$.fn.zTree.init($("#controlConfigTree"), controlConfTreesetting);
});