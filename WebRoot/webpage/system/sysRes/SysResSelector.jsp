<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

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
					"tableName" : "T_MSJW_SYSTEM_RES",
					"idAndNameCol" : "RES_ID,RES_NAME",
					"targetCols" : "RES_KEY,PARENT_ID,RES_URL,RES_TYPE",
					"rootParentId" : "0",
					"needCheckIds":needCheckIds,
					"isShowRoot" : "true",
					"rootName" : "系统资源树"
				}
			}
		};
		$.fn.zTree.init($("#sysResSelectTree"), setting);	
	});
	function submitInfo(){
		var treeObj = $.fn.zTree.getZTreeObj("sysResSelectTree");
		var nodes = treeObj.getCheckedNodes(true);
		var checkIds = [];
		if(nodes!=null&&nodes.length>0){
			for(var i =0;i<nodes.length;i++){
				if(nodes[i].id!="0"){
					checkIds.push(nodes[i].id);
				}
			}
		}
		if(checkIds.length>0){
			var roleId = $("input[name='roleId']").val();
			AppUtil.ajaxProgress({
				   url:"sysResController.do?grantRole",
				   params:{
					   checkResIds:checkIds,
					   roleId:roleId
				   },
				   callback:function(resultJson){
					  parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
							ok: true
						});
					   AppUtil.closeLayer();
				   }
			});
		}else{
			alert("请至少授权一个资源!")
		}
		
		
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
     <input type="hidden" value="${roleId}" name="roleId">
     <input type="hidden" value="${needCheckIds}" name="needCheckIds">
     <div>
     <ul id="sysResSelectTree" class="ztree"></ul>
     </div>
     <div class="eve_buttons">
		<input value="确定" type="button" onclick="submitInfo();"
			class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
			value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
			onclick="AppUtil.closeLayer();" />
	 </div>
</body>

