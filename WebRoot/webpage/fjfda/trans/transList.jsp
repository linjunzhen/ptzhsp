<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function mutilUpdateStatus(){
		var $dataGrid = $("#FjfdaTransGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var entityId = "";
		if(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length > 0){
		    var colName = $dataGrid.datagrid('options').idField;
			var selectColNames = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					selectColNames+=",";
				}
				selectColNames+=eval('rowsData[i].'+colName);
			}
			entityId = selectColNames;
			AppUtil.ajaxProgress({
				url : "fjfdaTransController.do?mutilUpdateStatus",
				params : {
					entityIds:entityId
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
						    ok: true
						});
						$("#FjfdaTransGrid").datagrid("reload");
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
	
	function matersReUpload(){
		var $dataGrid = $("#FjfdaTransGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var entityId = "";
		if(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length > 0){
		    var colName = $dataGrid.datagrid('options').idField;
			var selectColNames = "";
			for ( var i = 0; i < rowsData.length; i++) {
				if(i>0){
					selectColNames+=",";
				}
				selectColNames+=eval('rowsData[i].'+colName);
			}
			entityId = selectColNames;
			AppUtil.ajaxProgress({
				url : "fjfdaTransController.do?multiMatersUpload",
				params : {
					entityIds:entityId
				},
				callback : function(resultJson) {
					if (resultJson.success) {
						parent.art.dialog({
							content: resultJson.msg,
							icon:"succeed",
							time:3,
						    ok: true
						});
						$("#FjfdaTransGrid").datagrid("reload");
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
	
	/**
	* ??????????????????
	*/
	function createItemReUpload(){
		var $dataGrid = $("#FjfdaTransGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var entityId = "";
		if(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length > 0){
			var colName = $dataGrid.datagrid('options').idField;
			if(rowsData.length == 1){
				entityId = rowsData[0].YW_ID;
				var url = "fjfdaTransController.do?transInfo&entityId="+entityId;
				$.dialog.open(url , {
					title : "????????????????????????",
					width : "700px",
					height : "500px",
					lock : true,
					resize : false
				}, false);
			}else{
			    var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=",";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				entityId = selectColNames;
				AppUtil.ajaxProgress({
					url : "fjfdaTransController.do?multiSubmitUpload",
					params : {
						entityIds:entityId
					},
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							$("#FjfdaTransGrid").datagrid("reload");
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
	}
	
	function showAuditInfo(){
		var $dataGrid = $("#FjfdaTransGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var entityId = "";
		if(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length > 0){
			if(rowsData.length == 1){
				entityId = rowsData[0].FJFDA_EVEID;
				if(rowsData[0].TRANS_STATE =='1'){
					var url = "fjfdaTransController.do?auditInfo&fjfdaeveId="+entityId;
					$.dialog.open(url , {
						title : "???????????????????????????",
						width : "950px",
						height : "500px",
						lock : true,
						resize : false
					}, false);
				}else{
					art.dialog({
						content: "?????????????????????????????????????????????????????????????????????????????????!",
						icon:"warning",
					    ok: true
					});
				}
			}else{
				art.dialog({
					content: "????????????????????????????????????????????????????????????",
					icon:"warning",
				    ok: true
				});
			}
		}else{
			art.dialog({
				content: "????????????????????????",
				icon:"warning",
			    ok: true
			});
		}
	}
	
	/*$(document).ready(function() {
		AppUtil.initAuthorityRes("TransToolbar");
	}); */
	
	function formatExeId(val,row){
		//?????????????????????
		var href = "<a href='";
		href += "executionController.do?goDetail&exeId="+val;
		href += "' target='_blank'><span style='text-decoration:underline;color:#0368ff;'>"+val+"</span></a>";
	    return href;
	}
	function formatState(val,row){
		//?????????????????????
		if(val=="0"){
			return "<font color='#0368ff'><b>?????????</b></font>";
		}else if(val=="-1"){
			return "<font color='#ff4b4b'><b>??????</b></font>";
		}else if(val=="1"){
			return "<font color='green'><b>??????</b></font>";
		}
	}

	//????????????????????????
	$('#FjfdaTransGrid').datagrid({
		onLoadSuccess: function(data){
			if(data.total==0){
				var dc = $(this).data('datagrid').dc;
		        var header2Row = dc.header2.find('tr.datagrid-header-row');
		        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
	        }
		}
	});
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================??????????????????========================= -->
		<div id="TransToolbar">
			<!--====================?????????????????????============== -->
			<!--====================?????????????????????============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-rotate-right" plain="true"
								onclick="createItemReUpload();">????????????</a> 
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-rotate-left" plain="true"
								onclick="matersReUpload();">????????????</a> 
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-refresh" plain="true"
								onclick="mutilUpdateStatus();">????????????</a> 
							<a href="#" class="easyui-linkbutton" 
								iconcls="eicon-eye" plain="true"
								onclick="showAuditInfo();">??????????????????</a> 								
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="TransToolSearchForm">
			<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;" > 
			     <tbody>
			      <tr style="height:28px;"> 
			        <td style="width:70px;text-align:right;">???????????????</td> 
			        <td style="width:156px;"> 
						<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.BUS_EVEID_LIKE" />
				    </td> 
				    <td style="width:80px;text-align:right;">??????????????????</td> 
			        <td style="width:156px;"> 
						<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.FJFDA_EVEID_LIKE" />
				    </td>
				    <td style="width:70px;text-align:right;">???????????????</td> 
				    <td style="width:100px;"> 
						<select defaultemptytext="?????????????????????" class="eve-input" name="Q_T.TRANS_STATE_=">
							<option value="">?????????????????????</option>
							<option value="1">??????</option>
							<option value="0">?????????</option>
							<option value="-1">??????</option>
						</select>		
				    </td>	   
				   <td colspan="2"><input type="button" value="??????" class="eve-button" onclick="AppUtil.gridDoSearch('TransToolbar','FjfdaTransGrid');" />
				       <input type="button" value="??????" class="eve-button" onclick="AppUtil.gridSearchReset('TransToolSearchForm')" />
				    </td> 
			      </tr> 
			     </tbody>
			    </table> 
			</form> 
		</div>
		<!-- =========================??????????????????========================= -->
		
		<!-- =========================????????????==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="FjfdaTransGrid" fitcolumns="true" method="post" idfield="YW_ID" checkonselect="true"  fit="true"
			  singleSelect="true" selectoncheck="false" border="false" toolbar="#TransToolbar"
			  nowrap="false" url="fjfdaTransController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'YW_ID',hidden:true">YW_ID</th>
					<th data-options="field:'BUS_RECORDID',hidden:true">BUS_RECORDID</th>
					<th data-options="field:'BUS_EVEID',align:'left'" width="12%" formatter="formatExeId">???????????????</th>
					<th data-options="field:'ITEM_CODE',hidden:true">?????????????????????</th>
					<th data-options="field:'BUS_ITEMCODE',hidden:true">????????????</th>
					<th data-options="field:'BUS_ITEMNAME',align:'left'" width="20%">????????????</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15%">??????</th>
					<th data-options="field:'IS_MATERS_UPLOAD',align:'left'" width="8%" formatter="formatState">????????????</th>
					<th data-options="field:'TRANS_STATE',align:'left'" width="8%" formatter="formatState">????????????</th>					
					<th data-options="field:'FJFDA_EVEID',align:'left'" width="16%">?????????????????????</th>
					<th data-options="field:'RUN_STATUS',align:'left'" width="8%">????????????</th>
					<th data-options="field:'RESP_INFO',align:'left'" width="30%">??????????????????</th>
				</tr>
			</thead>
		</table>
		<!-- =========================????????????==========================-->
	</div>
</div>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               