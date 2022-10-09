<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>

<table cellpacing="1" class="tab_tk_pro2">
    <th >批量变更在职人员参保信息</th>
	<tr style="height:30px" id='zzryTr'>
		<td style="font-weight: bold;background: none;">【查询条件】</td>
	</tr>
	<table class="dddl-contentBorder dddl_table"
		style="background-repeat:repeat;width:100%;border-collapse:collapse;" id="zzryTable">
		<tbody>
			<tr style="height:40px;">
				<td style="width:120px;text-align:right;">单位保险号：</td>
				<td><input class="eve-input"
					type="text" maxlength="50" style="width:186px;"
					name="ZZ_DWBXH" /></td>
				<td style="width:120px;text-align:right;">单位名称：</td>
				<td><input class="eve-input"
					type="text" maxlength="50" style="width:186px;"
					name="ZZ_DWMC" /></td>
				<td style="width:120px;text-align:right">姓名:</td>
				<td colspan="2"><input class="eve-input"
					type="text" maxlength="50" style="width:186px;"
					name="ZZ_XM" />
				</td>
			</tr>
			<tr style="height:40px;">
				<td style="width:120px;text-align:right;">证件号码：</td>
				<td><input class="eve-input"
					type="text" maxlength="50" style="width:186px;"
					name="ZZ_ZJHM" /></td>
				<td style="width:120px;text-align:right;">参保身份：</td>
				<td>
				    <eve:eveselect clazz="tab_text" dataParams="insuredIdentity:08,07,09,04,03,05,99,02,01,06" id="zz_cbsf"
				      dataInterface="dictionaryService.findDatasForSelectIn"
				      defaultEmptyText="请选择" name="ZZ_CBSF">
				     </eve:eveselect>                        
				</td>
				<td style="width:120px;text-align:right;">参保状态:</td>
				<td>
					 <eve:eveselect clazz="tab_text" dataParams="insuredState" id="zz_cbzt"
				      dataInterface="dictionaryService.findDatasForSelect"
				      defaultEmptyText="请选择" name="ZZ_CSZT">
				     </eve:eveselect>  
				</td>
				<td style="width:240px;">
					<input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('zzryTable','zzryGrid')" />
					<input type="button" value="重置" class="eve-button" onclick="resetZzSearch()" />
				</td>
			</tr>
		</tbody>
	</table>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id='zzryTr1'>
	<tr style="height:30px">
		<td style="font-weight: bold;background: none;" colspan="7">【查询结果】</td>
	</tr>
	<tr style="height:40px;">
		<td style="width:120px;">
			<input style="margin-left:100px" type="button" value="更改" class="eve-button" onclick="zzChange()" />
		</td>
		<td style="width:120px;text-align:right;">参保身份:</td>
		<td style="width:186px;text-align:right;">
              <eve:eveselect clazz="tab_text" dataParams="insuredIdentity:01,03,04,05,07,99" 
			      dataInterface="dictionaryService.findDatasForSelectIn"
			      name="ZZ_XCBSF">
		     </eve:eveselect> 
		</td>
		<td style="width:120px;text-align:right;">参保日期:</td>
		<td colspan="3">
			<input type="text" id="ZZ_CBTIME" name="ZZ_CBTIME" value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" 
				class="tab_text Wdate validate[]" maxlength="60"  style="width:186px" />
		</td> 			
	</tr>				
</table> 
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:150px">	
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
				id="zzryGrid" fitColumns="false" toolbar="#zzryToolbar" 
				method="post" idField="DWID" checkOnSelect="true"
				selectOnCheck="true" fit="true" border="false" nowrap="false"
				url="ybGwyjfqrController.do?zzdatagrid">
				<thead>
					<tr>
						<th field="ck" checkbox="true"></th>
						<th data-options="field:'DWID',hidden:true">单位ID</th>
						<th data-options="field:'ZZ_BXH',align:'left'" width="10%">单位保险号</th>
						<th data-options="field:'ZZ_MC',align:'left'" width="10%">单位名称</th>
						<th data-options="field:'ZZ_XM',align:'left'" width="10%">姓名</th>
						<th data-options="field:'ZZ_HM',align:'left'" width="10%">证件号码</th>
						<th data-options="field:'ZZ_SF',align:'left'" width="15%">参保身份</th>
						<th data-options="field:'ZZ_XZ',align:'left'" width="15%">险种</th>
						<th data-options="field:'ZZ_CBRQ',align:'left'" width="15%">参保开始日期</th>
						<th data-options="field:'ZZ_ZT',align:'left'" width="15%">参保状态</th>
					</tr>
				</thead>
			</table> 
		</td>
	</tr>
</table>
 






	

	
			
		
