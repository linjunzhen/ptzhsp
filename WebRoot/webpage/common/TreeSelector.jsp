<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,json2,layer,ztree"></eve:resources>
<script type="text/javascript">
	$(function() {
		var eveTreeDataConfig = art.dialog.data("EveTreeDataConfig");
		var needCheckIds = eveTreeDataConfig.needCheckIds;
		var checkCascadeY = eveTreeDataConfig.checkCascadeY;
		var checkCascadeN = eveTreeDataConfig.checkCascadeN;
		var allowCount = eveTreeDataConfig.allowCount;
		$("input[name='allowCount']").val(allowCount);
		var checkTypeObj = {
				"Y":checkCascadeY,
				"N":checkCascadeN
		};
		var check = {
			enable: true,
			chkboxType :checkTypeObj
		};
		if(allowCount==1){
			check.chkStyle = "radio";
			check.radioType = "all";
		}
		var setting = {
			check:check,
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			async : {
				enable : true,
				url : eveTreeDataConfig.url,
				otherParam : eveTreeDataConfig.otherParam
			}
		};
		$.fn.zTree.init($("#CommonSelectTree"), setting);	
	});
	
	function selectCommonTree(){
		var allowCount = $("input[name='allowCount']").val();
		var treeObj = $.fn.zTree.getZTreeObj("CommonSelectTree");
		var nodes = treeObj.getCheckedNodes(true);
		var checkIds = "";
		var checkNames = "";
		var checkRecords = [];
		if(nodes!=null&&nodes.length>0){
			for(var i =0;i<nodes.length;i++){
				if(nodes[i].id!="0"){
					checkIds+=nodes[i].id+",";
					checkNames+=nodes[i].name+",";
					checkRecords.push(nodes[i].id);
				}
			}
			if(checkRecords.length>=1){
				if(allowCount!=0){
					if(checkRecords.length>allowCount){
						alert("最多只能选择"+allowCount+"条记录!");
						return;
					}
				}
				checkIds = checkIds.substring(0,checkIds.length-1);
				checkNames = checkNames.substring(0,checkNames.length-1);
				art.dialog.data("selectTreeInfo", {
					checkIds:checkIds,
					checkNames:checkNames
				});// 存储数据
			    AppUtil.closeLayer();
			}
		}else{
			alert("请至少选择一条记录!")
		}
	}
	
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<div class="easyui-layout" fit="true" style="margin:0px;background-color: #f7f7f7;">
 <div region="center"> 
	<input type="hidden" name="allowCount">
	<div id="treeDiv">
		<ul id="CommonSelectTree" class="ztree"></ul>
	</div>
	
	<div class="eve_buttons">
		<input value="确定" type="button" onclick="selectCommonTree();"
			class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
			value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
			onclick="AppUtil.closeLayer();" />
	</div>
</body>


