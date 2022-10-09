<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,artdialog,layer,ztree,json2"></eve:resources>

<script type="text/javascript"
	src="plug-in/ztree-3.5/js/jquery.ztree.exhide-3.5.js"></script>
<script type="text/javascript">
	function initModuleTree(){
    	var curUserResKeys = $("input[name='curUserResKeys']").val();
    	var needCheckModuleIds = $("input[name='needCheckModuleIds']").val();
		var setting = {
			check : {
				enable : true,
				chkboxType : {
					"Y" : "ps",
					"N" : "s"
				}
			},
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback: {
				onAsyncSuccess:function(event, treeId, treeNode, msg){
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
					if (curUserResKeys != "__ALL") {
					}
				}
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_CMS_ARTICLE_MODULE",
					"idAndNameCol" : "MODULE_ID,MODULE_NAME",
					"targetCols" : "PARENT_ID,PATH",
					"rootParentId" : "0",
					"needCheckIds" : needCheckModuleIds,
					"isShowRoot" : "false",
					"rootName" : "栏目树"
				}
			}
		};
		$.fn.zTree.init($("#moduleSelectTree"), setting);	
		
    }
    function initDataTree(){
    	var curUserResKeys = $("input[name='curUserResKeys']").val();
    	var needCheckDataIds = $("input[name='needCheckDataIds']").val();
		var setting = {
			check : {
				enable : true,
				chkboxType : {
					"Y" : "ps",
					"N" : "s"
				}
			},
			edit : {
				enable : false,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback: {
				onAsyncSuccess:function(event, treeId, treeNode, msg){
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
					if (curUserResKeys != "__ALL") {
						var loginUserInfoJson = $("input[name='loginUserInfoJson']").val();
						var loginUser = JSON2.parse(loginUserInfoJson);
						//获取授权的区划代码数组
						var authDepCodeArray = loginUser.authDepCodes.split(",");
						var nodes = treeObj.transformToArray(treeObj.getNodes());
						for (var i = 0; i < nodes.length; i++) {
							var child = nodes[i];
							if(!AppUtil.isContain(authDepCodeArray,child.DEPART_CODE)){
								child.nocheck = true;
								treeObj.updateNode(child);
							}
						}
					}
				}
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_MSJW_SYSTEM_DEPARTMENT",
					"idAndNameCol" : "DEPART_ID,DEPART_NAME",
					"targetCols" : "PARENT_ID,PATH,DEPART_CODE",
					"rootParentId" : "0",
					"needCheckIds" : needCheckDataIds,
					"isShowRoot" : "false",
					"rootName" : "组织机构树"
				}
			}
		};
		$.fn.zTree.init($("#dataSelectTree"), setting);	
		
    }

	function initResTree() {
		var curUserResKeys = $("input[name='curUserResKeys']").val();
		var needCheckResIds = $("input[name='needCheckResIds']").val();
		var resSetting = {
			check : {
				enable : true,
				chkboxType : {
					"Y" : "ps",
					"N" : "s"
				}
			},
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					if (curUserResKeys != "__ALL") {
						var nodes = treeObj
								.transformToArray(treeObj.getNodes());
						for (var i = 0; i < nodes.length; i++) {
							var child = nodes[i];
							var resKey = child.RES_KEY;
							var id = child.id;
							if (id != "0") {
								if (curUserResKeys.indexOf(resKey) == -1) {
									treeObj.hideNode(child);
								}
							}
						}
					}

				}
			},
			async : {
				enable : true,
				url : "baseController.do?tree",
				otherParam : {
					"tableName" : "T_MSJW_SYSTEM_RES",
					"idAndNameCol" : "RES_ID,RES_NAME",
					"targetCols" : "RES_KEY,PARENT_ID,RES_URL,RES_TYPE",
					"rootParentId" : "0",
					"needCheckIds" : needCheckResIds,
					"isShowRoot" : "true",
					"rootName" : "系统资源树"
				}
			}
		};
		$.fn.zTree.init($("#sysResSelectTree"), resSetting);
	}

	$(function() {
		//初始化系统资源树
		initResTree();
		//初始化数据授权资源树
		initDataTree();
		
		//初始化栏目树
		initModuleTree();
	});

	function submitInfo() {
		//获取系统资源树授权
		var sysResSelectTreeObj = $.fn.zTree.getZTreeObj("sysResSelectTree");
		var sysResCheckedNodes = sysResSelectTreeObj.getCheckedNodes(true);
		var checkResIds = [];
		if (sysResCheckedNodes != null && sysResCheckedNodes.length > 0) {
			for (var i = 0; i < sysResCheckedNodes.length; i++) {
				if (sysResCheckedNodes[i].id != "0") {
					checkResIds.push(sysResCheckedNodes[i].id);
				}
			}
		}
		//获取数据授权的值
		var dataSelectTree = $.fn.zTree.getZTreeObj("dataSelectTree");
		var dataCheckNodes = dataSelectTree.getCheckedNodes(true);
		var checkDataIds = [];
		var checkDataCodes = [];
		if (dataCheckNodes != null && dataCheckNodes.length > 0) {
			for (var i = 0; i < dataCheckNodes.length; i++) {
				checkDataIds.push(dataCheckNodes[i].id);
				checkDataCodes.push(dataCheckNodes[i].DEPART_CODE);
			}
		}
		//获取栏目授权的值
		var moduleSelectTree = $.fn.zTree.getZTreeObj("moduleSelectTree");
		var moduleCheckNodes = moduleSelectTree.getCheckedNodes(true);
		var checkModuleIds = [];
		if (moduleCheckNodes != null && moduleCheckNodes.length > 0) {
			for (var i = 0; i < moduleCheckNodes.length; i++) {
				checkModuleIds.push(moduleCheckNodes[i].id);
			}
		}
		
		if (checkResIds.length == 0) {
			alert("请至少授权一个操作资源!");
			$("#SysRoleGrantTab").tabs("select", "操作授权");
		} else if(checkDataIds.length==0){
			alert("请进行数据授权!");
			$("#SysRoleGrantTab").tabs("select", "数据授权");
		} else {
			var roleId = $("input[name='roleId']").val();
			AppUtil.ajaxProgress({
				url : "sysRoleController.do?grantRole",
				async : false,
				params : {
					checkResIds : checkResIds,
					checkDataIds :checkDataIds,
					checkDataCodes:checkDataCodes,
					checkModuleIds:checkModuleIds,
					roleId : roleId
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
						    ok: true
						});
						AppUtil.closeLayer();
					} else {
						parent.art.dialog({
							content: resultJson.msg,
							icon:"error",
						    ok: true
						});
					}
				}
			});
		}
	}


</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<input type="hidden" value="${roleId}" name="roleId">
	<input type="hidden" value="${sessionScope.curLoginUser.loginUserInfoJson}" name="loginUserInfoJson">
	<input type="hidden" value="${sessionScope.curLoginUser.resKeys}" name="curUserResKeys">
	<input type="hidden" value="${needCheckResIds}" name="needCheckResIds">
	<input type="hidden" value="${needCheckDataIds}" name="needCheckDataIds">
	<input type="hidden" value="${needCheckModuleIds}" name="needCheckModuleIds">

	<div id="SysRoleGrantFormDiv" class="easyui-layout" fit="true" >
		<div data-options="region:'center'" >
			<div class="easyui-tabs eve-tabs eui-evetabplus" id="SysRoleGrantTab" fit="true">
				<div title="操作授权" style="padding:10px">
					<ul id="sysResSelectTree" class="ztree"></ul>
				</div>
				<div title="数据授权" style="padding:10px">
					<ul id="dataSelectTree" class="ztree"></ul>
				</div>
				<div title="栏目授权" style="padding:10px">
					<ul id="moduleSelectTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		
		<div data-options="region:'south'"  style="height:46px;">
			<div class="eve_buttons">
				<input value="确定" type="button" onclick="submitInfo();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</div>

	
</body>

