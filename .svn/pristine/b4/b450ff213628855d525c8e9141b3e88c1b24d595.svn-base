<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
  	
	/**
	 * 删除经营范围列表记录
	 */
	function deleteBusScopeGridRecord() {
		AppUtil.deleteDataGridRecord("busScopeController.do?multiDel",
				"BusScopeGrid");
	};

	/**
	 * 编辑经营范围信息记录
	 */
	function editBusScopeGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("BusScopeGrid");
		if (entityId) {
			showBusScopeWindow(entityId);
		}
	}
	
	/**
	 * 显示经营范围信息对话框
	 */
	function showBusScopeWindow(entityId) {
		$.dialog.open("busScopeController.do?info&entityId=" + entityId, {
			title : "经营范围信息",
			width : "70%",
			lock : true,
			resize : false,
			height : "80%",
		}, false);
	};
	
	//是否秒批格式化
	function formatIsMp(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>是</b></font>";
		}else if(val=="0"){
			return "<font color='#ff4b4b'><b>否</b></font>";
		}
	}
	
	//是否可用格式化
	function formatIsUsAble(val,row){
		if(val=="1"){
			return "<font color='#0368ff'><b>是</b></font>";
		}else if(val=="0"){
			return "<font color='#ff4b4b'><b>否</b></font>";
		}
	}

    /**
     * 导入行业类别
     */
    function impIndustryCatalog() {
		$.dialog.open("busScopeController.do?impIndustryView", {
			title : "材料上传",
			width : "600px",
			height : "350px",
			lock : true,
			resize : false
		}, false);
    };
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="BusScopeToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_SysRole"
								iconcls="eicon-plus" plain="true"
								onclick="showBusScopeWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_SysRole"
								iconcls="eicon-pencil" plain="true"
								onclick="editBusScopeGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_SysRole"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteBusScopeGridRecord();">删除</a>
                            <a href="#"
                               class="easyui-linkbutton l-btn l-btn-small l-btn-plain" reskey="IMP_IndustryCatalog"
                               iconcls="eicon-file-excel-o" plain="true"
                               onclick="impIndustryCatalog();">行业类别导入</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="BusscopeForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:90px;text-align:right;">经营条目代码：</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.BUSSCOPE_CODE_LIKE" /></td>
							<td style="width:90px;text-align:right;">经营条目描述：</td>
							<td style="width:135px;"><input class="eve-input"
															type="text" maxlength="30" style="width:128px;"
															name="Q_T.BUSSCOPE_NAME_LIKE" /></td>
							<td style="width:90px;text-align:right;">是否秒批：</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
								style="width:135px;" name="Q_T.IS_MP_="
								data-options="valueField: 'label',textField: 'value',
									     data: [{label: '1', value: '是'},{label: '0',value: '否'},],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td style="width:90px;text-align:right;">事项类型：</td>
							<td style="width:135px;">
								<input class="easyui-combobox"
									   style="width:135px;" name="Q_T.ITEM_TYPE_="
									   data-options="valueField: 'label',textField: 'value',
									     data: [{label: '一般事项', value: '一般事项'},{label: '前置事项',value: '前置事项'},{label: '后置事项',value: '后置事项'},],panelWidth: 128,panelHeight: 'auto' " />
							</td>
							<td colspan="4"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('BusScopeToolbar','BusScopeGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('BusscopeForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="BusScopeGrid" fitcolumns="true" toolbar="#BusScopeToolbar"
			method="post" idfield="ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="busScopeController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'ID',hidden:true">主键ID</th>
					<th data-options="field:'BUSSCOPE_CODE',align:'left'" width="15%">经营条目代码</th>
					<th data-options="field:'BUSSCOPE_NAME',align:'left'" width="30%">经营范围表述条目</th>
					<th data-options="field:'IS_MP',align:'left'" width="10%" formatter="formatIsMp">是否秒批</th>
					<th data-options="field:'IS_USABLE',align:'left'" width="10%" formatter="formatIsUsAble">是否可用</th>
					<th data-options="field:'ITEM_TYPE',align:'left'" width="20%" >事项类型</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>