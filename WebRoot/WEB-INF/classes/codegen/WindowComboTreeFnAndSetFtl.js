function showSelect${showControlName}Tree() {
	var treeObj = $("#${showControlName}");
	var treeOffset = $("#${showControlName}").offset();
	$("#${showControlName}TreeContent").css({
				left : ${treeOffLeft},
				top : ${treeOffTop}
			}).slideDown("fast");
	$("body").bind("mousedown", on${showControlName}TreeBodyDown);
}

function onSelect${showControlName}TreeClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("${showControlName}Tree");
	var selectNode = zTree.getSelectedNodes()[0];
	$("#${showControlName}").attr("value",selectNode.name);
	hide${showControlName}SelectTree();
}

function on${showControlName}TreeBodyDown(event) {
	if (!(event.target.id == "${showControlName}" || event.target.id == "${showControlName}TreeContent" || $(event.target)
			.parents("#${showControlName}TreeContent").length > 0)) {
		hide${showControlName}SelectTree();
	}
}

function hide${showControlName}SelectTree() {
	$("#${showControlName}TreeContent").fadeOut("fast");
	$("body").unbind("mousedown", on${showControlName}TreeBodyDown);
}

var ${showControlName}Setting = {
	view : {
		dblClickExpand : false,
		selectedMulti: false
	},
	async : {
		enable : true,
		url : "baseController.do?tree",
		otherParam : {
			"tableName" : "${bindTableName}",
			"idAndNameCol" : "${idAndNameCol}",
			"targetCols" : "${targetCols}",
			"rootParentId" : "0",
			"dicTypeCode":"${dicTypeCode}",
			"isShowRoot" : "false",
			"rootName":"${rootName}"
		}
	},
	callback : {
		onClick : onSelect${showControlName}TreeClick
	}
};