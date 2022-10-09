<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<eve:resources
	loadres="jquery,easyui,apputil,artdialog,layer,ztree,swfupload,json2,validationegine"></eve:resources>
<link rel="stylesheet" href="webpage/main/css/fonticon.css">
<link rel="stylesheet" href="webpage/wsbs/serviceitem/css/ystep.css">
<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">

  	//确定操作
	function doSelectRows(){
		var checkRows = $("#BdcOnlineChargeGrid").datagrid("getChecked");
		var rows = $("#BdcOnlineChargeGrid").datagrid("getRows");
		var allowCount = $("input[name='allowCount']").val();
		if(rows.length>allowCount && allowCount!="0"){
			art.dialog({
				content: "最多只能选择"+allowCount+"条记录!",
				icon:"warning",
			    ok: true
			});
			return;
		}else if(rows.length < allowCount){
			art.dialog({
				content: "至少选择一条记录进行操作！",
				icon:"warning",
			    ok: true
			});
			return;
		}else if(checkRows.length!=rows.length){
		   art.dialog({
				content: "请勾选所有收费科目",
				icon:"warning",
			    ok: true
			});
			return;
		}	
		art.dialog.data("sssbSfxxInfo", rows);	
		art.dialog.data("sssbSfTotalPrice", $("input[name='OrderItemTotalPrice']").val());	
		AppUtil.closeLayer();
	}
	
	
	/**
	 * 删除收费科目信息列表记录()
	 */
	function deleteBdcOnlineChargeGridRecord() {
	    var exeId = $('input[name="exeId"]').val();
		var url = "bdcGyjsjfwzydjController.do?multiDelOnineCharge&EXE_ID="+exeId;		
		var rowsData = $("#BdcOnlineChargeGrid").datagrid('getChecked');
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被删除的记录!",
				icon:"warning",
			    ok: true
			});
		}else{
			art.dialog.confirm("您确定要删除该记录吗?", function() {
				var layload = layer.load('正在提交请求中…');
				var colName = $("#BdcOnlineChargeGrid").datagrid('options').idField;  
				var selectColNames = "";
				for ( var i = 0; i < rowsData.length; i++) {
					if(i>0){
						selectColNames+=";";
					}
					selectColNames+=eval('rowsData[i].'+colName);
				}
				$.post(url,{
					   selectColNames:selectColNames
				    }, function(responseText, status, xhr) {
				    	layer.close(layload);
				    	var resultJson = $.parseJSON(responseText);
						if(resultJson.success == true){
							art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
							    ok: true
							});
							$('#BdcOnlineChargeGrid').datagrid({
									onLoadSuccess: function(data){
										//刷新收费总金额
								    	var rows = $("#BdcOnlineChargeGrid").datagrid("getRows");
								    	var  totalPrice = 0;
								    	if(rows.length>0){
								    	   for (var i = 0; i < rows.length; i++) {
												totalPrice += Number(rows[i].OrderItemPrice);
											}
								        }
								        //精确到小数点后两位
								        $("input[name='OrderItemTotalPrice']").val(Math.ceil(Number(totalPrice)*100)/100);
									}
							});
							$("#BdcOnlineChargeGrid").datagrid('clearSelections').datagrid('clearChecked');
							
						}else{
							$("#BdcOnlineChargeGrid").datagrid('reload');
							art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
						}
				});
			});
		}
	};

	/**
	 * 编辑测收费科目信息记录
	 */
	function editBdcOnlineChargeGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("BdcOnlineChargeGrid");
		if (entityId) {
			showBdcOnlineChargeWindow(entityId);
		}
	}
	
	/**
	 * 显示收费科目信息对话框
	 */
	function showBdcOnlineChargeWindow(entityId) {
	    var exeId = $('input[name="exeId"]').val();
		$.dialog.open("bdcGyjsjfwzydjController.do?onlineChargeInfo&entityId=" + entityId+"&exeId="+exeId, {
			title : "收费科目信息",
			width : "500px",
			lock : true,
			resize : false,
			height : "450px",
			close:function(){
			   $('#BdcOnlineChargeGrid').datagrid({
					onLoadSuccess: function(data){
						//刷新收费总金额
				    	var rows = $("#BdcOnlineChargeGrid").datagrid("getRows");
				    	var  totalPrice = 0;
				    	if(rows.length>0){
				    	   for (var i = 0; i < rows.length; i++) {
								totalPrice += Number(rows[i].OrderItemPrice);
							}
				        }
				        //精确到小数点后两位
				        $("input[name='OrderItemTotalPrice']").val(Math.ceil(Number(totalPrice)*100)/100);
					}
			   });
		    }
		}, false);
	};


	//数据格式化
	function dataFormat(value,json){
		var data = JSON.parse(json);
		var rtn = "";
		$.each(data, function(idx, dic) {
			if(value==dic.DIC_CODE){
				rtn = dic.DIC_NAME;
				return false;
			}
		});
		return rtn;
	}
	
	
	//票据种类代码格式化
	var pjdmObj = $.ajax({
	    method:'post',
	    url:'dictionaryController.do?load',
	    async:false,
	    dataType:'json',
	    data:{typeCode:'bdcPjzldm'}
	});
	function formatPjdm(value){
		var json = pjdmObj.responseText;
		return dataFormat(value,json);
	}
	
	
	//科目编码格式化
	var kmbmObj = $.ajax({
	    method:'post',
	    url:'dictionaryController.do?load',
	    async:false,
	    dataType:'json',
	    data:{typeCode:'bdcSfKmbm'}
	});
	function formatKmbm(value){
		var json = kmbmObj.responseText;
		return dataFormat(value,json);
	}
	
	//科目名称格式化
	var kmmcObj = $.ajax({
	    method:'post',
	    url:'dictionaryController.do?load',
	    async:false,
	    dataType:'json',
	    data:{typeCode:'bdcSfKmmc'}
	});
	function formatKmmc(value){
		var json = kmmcObj.responseText;
		return dataFormat(value,json);
	}
	
	//收费标准格式化
	var sfbzObj = $.ajax({
	    method:'post',
	    url:'dictionaryController.do?load',
	    async:false,
	    dataType:'json',
	    data:{typeCode:'bdcSfBz'}
	});
	function formatSfbz(value){
		var json = sfbzObj.responseText;
		return dataFormat(value,json);
	}
	
	//计量单位格式化
	var jldwObj = $.ajax({
	    method:'post',
	    url:'dictionaryController.do?load',
	    async:false,
	    dataType:'json',
	    data:{typeCode:'bdcSfJldw'}
	});
	function formatJldw(value){
		var json = jldwObj.responseText;
		return dataFormat(value,json);
	}
	
	/* $(document).ready(function() {
		AppUtil.initAuthorityRes("BdcSurveyToolbar");
	}); */
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<table style="width:100%;border-collapse:collapse;"
						   class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="1" class="dddl-legend"><div class="eui-dddltit"><a>收费科目</a></div></td>
			</tr>
			<tr class="sfsfC">
				<td><span style="width: 120px;float:left;text-align:right;">收费总金额：（元）</span>
					<input type="text" style="width:400px;float:left;"
						   maxlength="64" class="eve-input" value="${OrderItemTotalPrice}"
						   name="OrderItemTotalPrice" /></td>
			</tr>
		</table>
		<!-- =========================查询面板开始========================= -->
		<div id="BdcOnlineChargeToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="exeId" value="${exeId}">
			<input type="hidden" name="allowCount" value="${allowCount}">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_BdcOnlineCharge"
								iconcls="eicon-plus" plain="true"
								onclick="showBdcOnlineChargeWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_BdcOnlineCharge"
								iconcls="eicon-pencil" plain="true"
								onclick="editBdcOnlineChargeGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_BdcOnlineCharge"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteBdcOnlineChargeGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BdcOnlineChargeGrid" fitcolumns="true" toolbar="#BdcOnlineChargeToolbar"
			method="post" idfield="OrderItemCode" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="bdcGyjsjfwzydjController.do?bdcOnlineChargedatagrid&exeId=${exeId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<!-- <th data-options="field:'OrderItemCodeId',hidden:true">科目ID</th> -->
					<th data-options="field:'OrderBillCode',align:'left'" width="20%" formatter="formatPjdm">票据种类代码</th>
					<th data-options="field:'OrderItemCode',align:'left'" width="20%" formatter="formatKmbm">科目编码</th>
					<th data-options="field:'OrderItemName',align:'left'" width="20%" formatter="formatKmmc">科目名称</th>
					<th data-options="field:'OrderItemRule',align:'left'" width="20%" formatter="formatSfbz">收费标准</th>
					<th data-options="field:'OrderItemUnit',align:'left'" width="10%" formatter="formatJldw">计量单位</th>
					<th data-options="field:'OrderItemNum',align:'left'" width="10%" >数量</th>
					<th data-options="field:'OrderItemPrice',align:'left'" width="20%" >收费金额</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
	<div data-options="region:'south'" style="height:46px;">
			<div class="eve_buttons" style="text-align: right;">
				<input value="提交" type="button" onclick="doSelectRows();"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
	</div>
</div>