<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除信息列表记录
	 */
	function deletecommercialSealGridRecord() {
		AppUtil.deleteDataGridRecord("commercialSealController.do?multiDel",
				"commercialSealGrid");
	};
	/**
	 * 编辑信息列表记录
	 */
	function editcommercialSealGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("commercialSealGrid");
		if (entityId) {
			showcommercialSealWindow(entityId);
		}
	}

	function toUrl(url,code){
		var ssoForm=$("<form action='"+url+"' method='post' target='_blank'></form>");	
		var codeInput="<input name='PROJECTCODE' type='hidden' value='"+code+"' />";
		$("#commercialSealToolbar").append(ssoForm);
		ssoForm.append(codeInput);
		ssoForm.submit();		
	}
	/**
	 * 显示信息信息对话框
	 */
	function showcommercialSealWindow(entityId) {
		$.dialog.open("commercialSealController.do?info&isEdit=1&entityId=" + entityId, {
			title : "印章信息",
			width : "1200px",
			height : "610px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		AppUtil.initAuthorityRes("commercialSealToolbar");
	});
	function reloadcommercialSealList(){
		$("#commercialSealGrid").datagrid("reload");
	}
	function formatStatus(val,row){
		if(val=="0"){
			return "<font color='#ff4b4b'>未提交</font>";
		}else if(val=="1"){
			return "<font color='#0368ff'>刻章中</font>";
		}else if(val=="2"){
			return "<font color='#0368ff'><b>已交付</b></font>";
		}else if(val=="3"){
			return "<font><b>已办结</b></font>";
		}
	}
	function formatIsOvertime(val,row){
		if(val=="0"){
			return "<font>否</font>";
		}else{
			var REMARK = row.REMARK;
			var html  = "<font color='red'>是";
			if(REMARK){
				html+="("+REMARK+")";
			}
			html+="</font>";
			return html;
		}
	}
	function formatSealPackage(val,row){
		if(val=="1"){
			return "<font>漳州市盾安印章制作服务有限公司</font>";
		}else if(val=="2"){
			return "<font>平潭综合实验区鑫楚印章制作有限公司</font>";
		}
	}
	
		
	$(document).ready(function() {
		var start1 = {
			elem : "#SEAL.CREATE_TIME_BEGIN",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("开始时间必须小于等于结束时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_>=']").val("");
					}
				}
			}
		};
		var end1 = {
			elem : "#SEAL.CREATE_TIME_END",
			format : "YYYY-MM-DD",
			istime : false,
			choose : function(datas) {
				var beginTime = $("input[name='Q_T.CREATE_TIME_>=']").val();
				var endTime = $("input[name='Q_T.CREATE_TIME_<=']").val();
				if (beginTime != "" && endTime != "") {
					var start = new Date(beginTime);
					var end = new Date(endTime);
					if (start > end) {
						alert("结束时间必须大于等于开始时间,请重新输入!");
						$("input[name='Q_T.CREATE_TIME_<=']").val("");
					}
				}
			}
		};
		laydate(start1);
		laydate(end1);
	});
	function payCommercialSealGridRecord(){
		var $dataGrid = $("#commercialSealGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var colName = $dataGrid.datagrid("options").idField; 
		var selectColNames = "";
		for ( var i = 0; i < rowsData.length; i++) {
			if(i>0){
				selectColNames+=",";
			}
			selectColNames+=eval('rowsData[i].'+colName);
			
			if(rowsData[i].STATUS!=1){				
				art.dialog({
					content: "请选择状态为“刻章中”的记录!",
					icon:"warning",
					ok: true
				});
				return;
			}
		}
		if(selectColNames==""){
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
				ok: true
			});
		}else{
			art.dialog.confirm("您确定要更改状态为已交付吗?", function() {			
				var layload = layer.load('正在提交请求中…');
				$.post("commercialSealController.do?updateStatus",{
				   selectColNames:selectColNames,
				   status:2
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
						AppUtil.gridDoSearch('commercialSealToolbar','commercialSealGrid')
					}else{
						art.dialog({
							content: resultJson.msg,
							icon:"error",
							ok: true
						});
					}
				});
			});
		}
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="commercialSealToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_COMMERCIALSEAL"
								iconcls="eicon-plus" plain="true"
								onclick="showcommercialSealWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_COMMERCIALSEAL"
								iconcls="eicon-pencil" plain="true"
								onclick="editcommercialSealGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="PAY_COMMERCIALSEAL"
								iconcls="eicon-check" plain="true"
								onclick="payCommercialSealGridRecord();">已交付</a>
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_COMMERCIALSEAL"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletecommercialSealGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="commercialSealForm"> 
				<table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
				 <tbody>
				  <tr style="height:28px;">  
						<td style="width:100px;text-align:right;">申报号：</td> 
						<td style="width:156px;"> 
							<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.EXE_ID_LIKE"/>
						</td> 
						<td style="width:100px;text-align:right;">企业名称：</td> 
						<td style="width:156px;"> 
							<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.COMPANY_NAME_LIKE"/>
						</td> 
						<td style="width:100px;text-align:right;">统一社会信用代码：</td> 
						<td style="width:156px;"> 
							<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.SOCIAL_CREDITUNICODE_LIKE"/>
						</td> 
						<td style="width:68px;text-align:right;">状态：</td>
						<td style="width:135px;">
							<input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_T.STATUS_="  data-options="
				url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=SEALSTATUS',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
						</td>
				  </tr> 
				  <tr style="height:28px;"> 
						<td style="width:68px;text-align:right;">申请日期从：</td>
						<td style="width:135px;"><input type="text"
							style="width:108px;float:left;" class="laydate-icon"
							id="SEAL.CREATE_TIME_BEGIN" name="Q_T.CREATE_TIME_&gt;=" /></td>
						<td style="width:68px;text-align:right;">申请日期至：</td>
						<td style="width:135px;"><input type="text"
							style="width:108px;float:left;" class="laydate-icon"
							id="SEAL.CREATE_TIME_END" name="Q_T.CREATE_TIME_&lt;=" /></td>
					   
						<td style="width:68px;text-align:right;">备注（是否超时）：</td>
						<td style="width:135px;">
							<input class="easyui-combobox" type="text" maxlength="20" style="width:78px;" name="Q_T.IS_OVERTIME_="  data-options="
				url:'dictionaryController.do?load&amp;defaultEmpty=true&amp;typeCode=YesOrNo',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 74,panelHeight: 'auto' " />
						</td>
					   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('commercialSealToolbar','commercialSealGrid')" />
						   <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('commercialSealForm')" />
						</td> 
				  </tr> 
				 </tbody>
				</table> 
			</form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="commercialSealGrid" fitcolumns="true" toolbar="#commercialSealToolbar"
			method="post" idfield="SEAL_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="commercialSealController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SEAL_ID',hidden:true">SEAL_ID</th>	
					<th data-options="field:'EXE_ID',align:'left'" width="10%">申报号</th>		
					<th data-options="field:'COMPANY_NAME',align:'left'" width="20%">企业名称</th>	
					<th data-options="field:'SOCIAL_CREDITUNICODE',align:'left'" width="14%">统一社会信用代码</th>	
					<th data-options="field:'SEAL_PACKAGE',align:'left'" width="18%" formatter="formatSealPackage">供应商</th>			
					<th data-options="field:'CREATE_TIME',align:'left'" width="12%">申请时间</th>
					<th data-options="field:'STATUS',align:'left'" width="6%" formatter="formatStatus">状态</th>	
					<th data-options="field:'IS_OVERTIME',align:'left'" width="15%" formatter="formatIsOvertime">备注（是否超时）</th>	
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
