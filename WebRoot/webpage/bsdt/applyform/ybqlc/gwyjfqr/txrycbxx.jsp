<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>	

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
    <th>批量变更退休人员参保信息</th>
	<tr style="height:30px" id="txryTr">
		<td style="font-weight: bold;background: none;">【查询条件】</td>
	</tr>
	<table class="dddl-contentBorder dddl_table"
		style="background-repeat:repeat;width:100%;border-collapse:collapse;" id="txryTable">
		<tbody>
			<tr style="height:40px;">
				<td style="width:120px;text-align:right;">单位保险号：</td>
				<td><input class="eve-input"
					type="text" maxlength="50" style="width:150px;"
					name="TX_DWBXH" /></td>
				<td style="width:120px;text-align:right;">单位名称：</td>
				<td><input class="eve-input"
					type="text" maxlength="50" style="width:150px;"
					name="TX_DWMC" /></td>
				<td style="width:120px;text-align:right">姓名:</td>
				<td colspan="2"><input class="eve-input"
					type="text" maxlength="50" style="width:150px;"
					name="TX_XM" />
				</td>
			</tr>
			<tr style="height:40px;">
				<td style="width:120px;text-align:right;">证件号码：</td>
				<td><input class="eve-input"
					type="text" maxlength="50" style="width:150px;"
					name="TX_ZJHM" /></td>
				<td style="width:120px;text-align:right;">参保身份：</td>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="insuredIdentity:08,07,09,04,03,05,99,02,01,06" id="tx_cbsf"
				      dataInterface="dictionaryService.findDatasForSelectIn"
				      defaultEmptyText="请选择" name="TX_CBSF">
				     </eve:eveselect>
				</td>
				<td style="width:120px;text-align:right;">参保状态:</td>
				<td>
					 <eve:eveselect clazz="tab_text" dataParams="insuredState" id="tx_cbzt"
				      dataInterface="dictionaryService.findDatasForSelect"
				      defaultEmptyText="请选择" name="TX_CSZT">
				     </eve:eveselect> 
				</td>
				<td style="width:240px;">
					<input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('txryTable','txryGrid')" />
					<input type="button" value="重置" class="eve-button" onclick="resetTxSearch()" />
				</td>
			</tr>
		</tbody>
		</table>
	
</table> 
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="txryTr1">
	<tr style="height:30px">
		<td style="font-weight: bold;background: none;" colspan="7">【查询结果】</td>
	</tr>
	<tr style="height:40px;">
		<td style="width:120px;">
			<input style="margin-left:100px" type="button" value="更改" class="eve-button" onclick="txChange()" />
		</td>
		<td style="width:120px;text-align:right;">参保身份:</td>
		<td style="width:150px;text-align:right;">
			<eve:eveselect clazz="tab_text" dataParams="insuredIdentity:02,06,08,09" 
			      dataInterface="dictionaryService.findDatasForSelectIn"
			      name="TX_XCBSF">
		    </eve:eveselect> 
		</td>
		<td style="width:120px;text-align:right;">参保日期:</td>
		<td colspan="3">
			<input type="text" id="TX_CBTIME" name="TX_CBTIME" value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
				class="tab_text Wdate validate[required]" maxlength="60"  style="width:150px" />
		</td> 			
	</tr>				
</table>
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:150px"> 
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="txryGrid" fitColumns="false" toolbar="#txryToolbar" 
			method="post" idField="TXID" checkOnSelect="true"
			selectOnCheck="true" fit="true" border="false" nowrap="false"
			url="ybGwyjfqrController.do?txdatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TXID',hidden:true">人员ID</th>
					<th data-options="field:'TX_BXH',align:'left'" width="10%">单位保险号</th>
					<th data-options="field:'TX_MC',align:'left'" width="10%">单位名称</th>
					<th data-options="field:'TX_XM',align:'left'" width="10%">姓名</th>
					<th data-options="field:'TX_HM',align:'left'" width="10%">证件号码</th>
					<th data-options="field:'TX_SF',align:'left'" width="15%">参保身份</th>
					<th data-options="field:'TX_XZ',align:'left'" width="15%">险种</th>
					<th data-options="field:'TX_CBRQ',align:'left'" width="15%">参保开始日期</th>
					<th data-options="field:'TX_ZT',align:'left'" width="15%">参保状态</th>
				</tr>
			</thead>
		</table> 
		</td>
	</tr>
</table>	
		
 