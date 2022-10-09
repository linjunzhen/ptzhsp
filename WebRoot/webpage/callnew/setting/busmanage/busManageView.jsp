<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.gridtable {
	width: 100%;
	height: 100%
}

.gridtable .datagrid-htable {
	border-top: 1px solid #ccc;
	border-left: 1px solid #ccc
}

.gridtable .datagrid-btable {
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc
}

.gridtable .datagrid-header-row td {
	border-width: 0;
	border-left: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
}

.gridtable .datagrid-header-row td:last-child {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
}

.gridtable .datagrid-body td {
	border-width: 0;
	border-right: 1px solid #ccc;
	border-top: 1px solid #ccc;
}
</style>
<script type="text/javascript">

	$(document).ready(
		function() {
		});

	$('#busManageGrid').datagrid({
		pagination : true,
		onLoadSuccess : function(data) {
			if (data.rows.length > 0) {
				//调用mergeCellsByField()合并单元格
				mergeCellsByField("busManageGrid", "DEPART_NAME,BELONG_ROOMNAME");
			}
		}
	});
	/**
	* EasyUI DataGrid根据字段动态合并单元格
	* 参数 tableID 要合并table的id
	* 参数 colList 要合并的列,用逗号分隔(例如："name,department,office");
	*/
	function mergeCellsByField(tableID, colList) {
		var ColArray = colList.split(",");
		var tTable = $("#" + tableID);
		var TableRowCnts = tTable.datagrid("getRows").length;
		var tmpA;
		var tmpB;
		var PerTxt = "";
		var CurTxt = "";
		var alertStr = "";
		for (j = ColArray.length - 1; j >= 0; j--) {
			PerTxt = "";
			tmpA = 1;
			tmpB = 0;

			for (i = 0; i <= TableRowCnts; i++) {
				if (i == TableRowCnts) {
					CurTxt = "";
				} else {
					CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
				}
				if (PerTxt == CurTxt) {
					tmpA += 1;
				} else {
					tmpB += tmpA;

					tTable.datagrid("mergeCells", {
						index : i - tmpA,
						field : ColArray[j], //合并字段
						rowspan : tmpA,
						colspan : null
					});
					tTable.datagrid("mergeCells", { //根据ColArray[j]进行合并
						index : i - tmpA,
						field : "Ideparture",
						rowspan : tmpA,
						colspan : null
					});

					tmpA = 1;
				}
				PerTxt = CurTxt;
			}
		}
	}
	
	/**
	 * 编辑列表记录
	 */
	function delBusGridRecord(index) {
		art.dialog.confirm("您确定要删除该记录吗?", function() {
			var row = $('#busManageGrid').datagrid('getData').rows[index];
			$.post("callSetController.do?delBus", {
				selectColNames : row.DATA_ID
			}, function(responseText, status, xhr) {
				var resultJson = $.parseJSON(responseText);
				if (resultJson.success == true) {
					art.dialog({
						content : resultJson.msg,
						icon : "succeed",
						time : 3,
						ok : true
					});
					$('#busManageGrid').datagrid('reload');
				} else {
					$('#busManageGrid').datagrid('reload');
					art.dialog({
						content : resultJson.msg,
						icon : "error",
						ok : true
					});
				}
			});
		});
	} 
	
	/**
	 * 编辑列表记录
	 */
	function editBusGridRecord(index) {
		var row = $('#busManageGrid').datagrid('getData').rows[index];
		showBusGridRecord(row.DATA_ID);
	}

	/**
	 * 显示配置信息对话框
	 */
	function showBusGridRecord(entityId) {
		$.dialog.open("callSetController.do?busManageinfo&entityId=" + entityId, {
			title : "业务信息",
			width : "850px",
			height : "350px",
			lock : true,
			resize : false
		}, false);
	};
	
	function rowformater(value,row,index){
		if(value=='1'){
			return "<font style='color:#0368ff;'>启用</font>";
		}else if(value=='0'){
			return "<font style='color:#ff4b4b;'>停用</font>";
		}
	}
	
	function appointformater(value,row,index){
		if(value=='1'){
			return "<font style='color:#0368ff;'>是</font>";
		}else{
			return "<font style='color:#ff4b4b;'>否</font>";
		}
	}
	
	function changeBusGridRecord(index,status){		
		var str = "启用"
		if(status == "0"){
			str = "停用";
		}
		art.dialog.confirm("您确定要"+str+"该业务吗?", function() {
			var row = $('#busManageGrid').datagrid('getData').rows[index];
			AppUtil.ajaxProgress({
				url : "callSetController.do?busUse",
				params : {
					"entityIds" : row.DATA_ID,
					"statu" : status
				},
				callback : function(resultJson) {
					parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					AppUtil.gridDoSearch("busManageToolBar","busManageGrid");
				}
			});
		
		});
	}
	
	function operformater(value,row,index){
		var rtn = "<a href='#' onclick='editBusGridRecord("+index+")'><font style='color:blue;'>【编辑】</font></a>  ";
		rtn += "<a href='#' onclick='delBusGridRecord("+index+")' ><font style='color:blue;'>【删除】</font></a>";
		rtn += "<a href='#' onclick='changeBusGridRecord("+index+",1)' ><font style='color:blue;'>【启用】</font></a>";
		rtn += "<a href='#' onclick='changeBusGridRecord("+index+",0)' ><font style='color:blue;'>【停用】</font></a>";
		var departId = row.DEPART_ID;
		if(departId == '2c90b38a67a6266d0167ab958b94619b' || departId == '402801336f54e413016f5615914b3cc6'){//一窗通办时配置事项
			rtn += "<a href='#' onclick='yctbAccept("+index+")' ><font style='color:blue;'>【事项配置】</font></a>";
		}
		return rtn;
	}
	
	function showQueryDeparts(){
    	var departId = $("input[name='Q_t.DEPART_ID_=']").val();
    	parent.$.dialog.open("departmentController/selector.do?departIds="+departId+"&allowCount=1&checkCascadeY=&"
   				+"checkCascadeN=", {
    		title : "组织机构选择器",
            width:"600px",
            lock: true,
            resize:false,
            height:"500px",
            close: function () {
    			var selectDepInfo = art.dialog.data("selectDepInfo");
    			if(selectDepInfo){
    				$("#busManageToolBar input[name='Q_t.DEPART_ID_=']").val(selectDepInfo.departIds);
        			$("#busManageToolBar input[name='DEPART_NAME']").val(selectDepInfo.departNames);
    				art.dialog.removeData("selectDepInfo");
    			}
    		}
    	}, false);
    }
	
	//一窗通办事项绑定
	function yctbAccept(index) {
		var yctbsl = 1;
		var row = $('#busManageGrid').datagrid('getData').rows[index];
		if (row) {
			$.dialog.open("departServiceItemController.do?yctbSelector&businessCode="+row.BUSINESS_CODE, {
				title : "事项选择器",
				width : "1200px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						var itemCodes = selectItemInfo.itemCodes;	
						$.post("departServiceItemController.do?updateItembusinessCode", {
							selectColNames : itemCodes,
							businessCode : row.BUSINESS_CODE
						}, function(responseText, status, xhr) {
							var resultJson = $.parseJSON(responseText);
							if (resultJson.success == true) {
								art.dialog({
									content : resultJson.msg,
									icon : "succeed",
									time : 3,
									ok : true
								});
								$('#busManageGrid').datagrid('reload');
							} else {
								$('#busManageGrid').datagrid('reload');
								art.dialog({
									content : resultJson.msg,
									icon : "error",
									ok : true
								});
							}
						});
						art.dialog.removeData("selectItemInfo");
					}
				}
			}, false);
		}else{
			parent.art.dialog({
				content : "当前无可配置记录",
				icon : "warning",
				time : 3,
				ok : true
			});
		}
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="busManageToolBar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="Q_t.DEPART_ID_=">
			<!--====================结束编写隐藏域============== -->			
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Business"
								iconcls="eicon-plus" plain="true" style="margin-top: 4px;"
								onclick="showBusGridRecord();">新增</a>
						</div>
					</div>
				</div>
			</div>	
			<form action="#" name="busManageForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">所属大厅</td>
							<td style="width:135px;padding-left:4px;"><input class="easyui-combobox"
								style="width:128px;" name="Q_t.BELONG_ROOM_="
								data-options="
	url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=roomNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:68px;text-align:right;">业务名称</td>
							<td style="width:135px;"><input type="text" class="eve-input"
								style="width:150px;float:left;" name="Q_t.BUSINESS_NAME_LIKE" /></td>
							<td style="width:68px;text-align:right;">部门</td>
							<td style="width:135px;"><input type="text" class="eve-input" readonly="readonly"
								style="width:150px;float:left;" name="DEPART_NAME" onclick="showQueryDeparts()" /></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('busManageToolBar','busManageGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="$('#busManageToolBar input[name=\'Q_t.DEPART_ID_=\']').val('');AppUtil.gridSearchReset('busManageForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<div class="gridtable">
			<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true" pageSize="15" pageList="[15,30,50]"
				id="busManageGrid" fitcolumns="false" toolbar="#busManageToolBar"
				method="post" idfield="DATA_ID" checkonselect="false" nowrap="false"
				selectoncheck="false" fit="true" border="false"
				url="callSetController.do?busManageData">
				<thead>
					<tr>
						<th data-options="field:'DATA_ID',hidden:true">DATA_ID</th>
						<th data-options="field:'DEPART_ID',hidden:true">DEPART_ID</th>
						<th data-options="field:'BELONG_ROOMNAME',align:'center'" width="10%">所属大厅</th>
						<th data-options="field:'DEPART_NAME'" width="15%">单位/部门名称</th>
						<th data-options="field:'BUSINESS_NAME'" width="20%">业务名称</th>
						<th data-options="field:'BUSINESS_CODE',align:'center'" width="8%">业务编码</th>
						<th data-options="field:'SERVICE_STATUS',align:'center',formatter:rowformater" width="8%">状态</th>
						<th data-options="field:'IS_APPOINT',align:'center',formatter:appointformater" width="10%">是否可预约</th>
						<th data-options="field:'oper',align:'center',formatter:operformater" width="27%">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- =========================表格结束==========================-->
	</div>
</div>