<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,ztree,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		var needCheckIds = $("input[name='needCheckIds']").val();
		var setting = {
			check: {
				enable: true,
				chkboxType :{ "Y" : "ps", "N" : "s" }
			},
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "${bindTableName}",
					"idAndNameCol" : "${TreeIdAndName}",
					"targetCols" : "${TreeTargetCols}",
					"rootParentId" : "0",
					"needCheckIds":needCheckIds,
					"isShowRoot" : "true",
					"rootName" : "${bindChinese}树"
				}
			}
		};
		$.fn.zTree.init($("#${bindEntityName}SelectTree"), setting);	
	});
	function select${bindEntityName}Tree(){
		var treeObj = $.fn.zTree.getZTreeObj("${bindEntityName}SelectTree");
		var nodes = treeObj.getCheckedNodes(true);
		var checkIds = "";
		var checkNames = "";
		if(nodes!=null&&nodes.length>0){
			for(var i =0;i<nodes.length;i++){
				if(nodes[i].id!="0"){
					checkIds+=nodes[i].id+",";
					checkNames+=nodes[i].name+",";
				}
			}
			if(checkIds.length>=1){
				checkIds = checkIds.substring(0,checkIds.length-1);
				checkNames = checkNames.substring(0,checkNames.length-1);
				parent.callbackSelectSysRes(checkIds,checkNames);
			}else{
				alert("请至少选择一条记录!")
			}
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
     <input type="hidden" value="" name="needCheckIds">
     <div>
     <ul id="${bindEntityName}SelectTree" class="ztree"></ul>
     </div>
     <div class="eve_buttons">
		<input value="确定" type="button" onclick="select${bindEntityName}Tree();"
			class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
			value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
			onclick="AppUtil.closeLayer();" />
	 </div>
</body>

