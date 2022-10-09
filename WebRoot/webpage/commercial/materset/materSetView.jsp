<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>


<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">
		
	function formatIsNeed(val,row){
		if(val=="1"){
			return "是";
		}else if(val=="0"){
			return "否";
		}
	};
	
	function formatIsForm(val,row){
		if(val=="1"){
			return "是";
		}else if(val=="0"){
			return "否";
		}
	}
    
    function editRelatedItemMater(){
	    var entityId = AppUtil.getEditDataGridRecord("materSetGrid");    	
    	$.dialog.open("commercialSetController.do?materSetInfo&entityId=" + entityId, {
			title : "材料表单化配置",
			width: "400px",
		    height: "200px",
		    fixed: true,
			lock : true,
			resize : false
		}, false);
    }
    
    function refreshRelatedItemMater(){
    	var entityId = '${entityId}';
    	AppUtil.ajaxProgress({
			url : "commercialSetController.do?refreshRelatedItemMater",
			params : {
				relatedId : entityId
			},
			callback : function(resultJson) {
				if(resultJson.success){
				  	parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					$("#materSetGrid").datagrid("reload");
				}else{
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			}
		});
    }
</script>
<body class="eui-diabody" style="margin:0px;">
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="materSetToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="EDT_Related"
								iconcls="eicon-pencil" plain="true"
								onclick="editRelatedItemMater();">编辑</a>
							<a href="#"
								class="easyui-linkbutton" reskey="EDT_Related"
								iconcls="eicon-refresh" plain="true"
								onclick="refreshRelatedItemMater();">更新材料目录</a>
						</div>
					</div>
				</div>
			</div>
						
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="materSetGrid" fitcolumns="false" nowrap="false"
			toolbar="#materSetToolbar" method="post" idfield="RECORD_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			url="commercialSetController.do?materSetData&Q_t.related_id_EQ=${entityId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'MATER_ID',hidden:true">MATER_ID</th>
					<th data-options="field:'MATER_NAME',align:'left'" width="50%">材料名称</th>
					<th data-options="field:'MATER_ISNEED',align:'left'" width="8%" formatter="formatIsNeed">是否必须</th>
					<th data-options="field:'MATER_SN',align:'left'" width="8%" >材料排序</th>
					<th data-options="field:'IS_FORM',align:'left'" width="10%" formatter="formatIsForm">是否表单化</th>
					<th data-options="field:'FORM_NAME',align:'left'" width="20%" >表单名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
</body>