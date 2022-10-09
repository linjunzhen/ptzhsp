<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
    <eve:resources loadres="jquery,apputil,artdialog,json2,ztree"></eve:resources>
    <script type="text/javascript">
    
	    /**
	     * artdialog回调事件
	     */
	    function onArtDialogCallback(){
	    	var returnObj = null;
	    	var treeObj = $.fn.zTree.getZTreeObj("FileTypeSelectTree");
			var nodes = treeObj.getCheckedNodes(true);
			var checkIds = "";
			var checkNames = "";
			var checkRecords = [];
			if(nodes != null && nodes.length > 0){
				for(var i = 0;i < nodes.length; i++){
					if(nodes[i].id != "0"){
						checkIds+=nodes[i].id+",";
						checkNames+=nodes[i].name+",";
						checkRecords.push(nodes[i].id);
					}
				}
				if(checkRecords.length >= 1){
					checkIds = checkIds.substring(0,checkIds.length-1);
					checkNames = checkNames.substring(0,checkNames.length-1);
					returnObj = {
						checkIds: checkIds,
						checkNames: checkNames
					};
				}else{
					alert("请至少选择一个多媒体类别~");
				}
			}else{
				alert("请至少选择一个多媒体类别~");
			}
			return returnObj;
	    }
	    
		function fileTypeShow(node, needCheckIds){
			if(node.children && node.children.length > 0){
				node.nocheck = false;
				for(var i = 0; i < node.children.length; i++){
					fileTypeShow(node.children[i], needCheckIds);
				}
			}else{
				if(needCheckIds == node.TYPE_ID){
					node.checked = true;
				}
			}
		}
        
        $(document).ready(function(){
        	var fileTypeTreeSetting = {
				check: {
	   				enable: true,
	   				chkStyle: "radio",
	   				radioType: "all"
	   			},
				edit: {
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				},
				view: {
					selectedMulti: false
				},
				async: {
					enable: true,
					url: "fileTypeController/tree.do",
					dataFilter: function(treeId, parentNode, responseData){
					    if(responseData){
						    responseData.nocheck = false;
						    for(var i = 0; i < responseData.children.length; i++){
						    	fileTypeShow(responseData.children[i], '${needCheckIds}');
						    }
						}
					    return responseData;
					},
					otherParam : {
						"tableName": "T_FILES_TYPE",
	   					"idAndNameCol": "TYPE_ID,TYPE_NAME",
	   					"targetCols": "TREE_PATH,PARENT_ID",
	   					"rootParentId": "0",
	   					"isShowRoot": "true",
	   					"rootName": "多媒体类别树"
					}
				}
			};
			$.fn.zTree.init($("#FileTypeSelectTree"), fileTypeTreeSetting);
        });
    </script>
</head>
<body style="margin:0px;background-color: #f7f7f7;">	
	<div>
	    <ul id="FileTypeSelectTree" class="ztree"></ul>
	</div>
</body>