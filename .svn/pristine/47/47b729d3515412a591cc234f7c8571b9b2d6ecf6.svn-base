<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.yb.service.YbCommonService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String ywId = request.getParameter("SB_YWID");
	request.setAttribute("ywId",ywId);
	String exeId = request.getParameter("exeId");
	request.setAttribute("exeId",exeId);
	
%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<style>
.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
</style>
<div class="tab_height"></div>
<%--<table cellpadding="0" cellspacing="1" class="tab_tk_pro">--%>
<%--	<tr>--%>
<%--		<th colspan="6">养老保险减员登记</th>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td class="tab_width">个人编号：</td>--%>
<%--		<td>--%>
<%--			<input type="text" class="tab_text validate[]"  name="" /> 					--%>
<%--		</td>		--%>
<%--		<td class="tab_width">社会保障号码：</td>--%>
<%--		<td>--%>
<%--			<input type="text" class="tab_text validate[]" name="" />--%>
<%--		</td>--%>
<%--		<td class="tab_width"> 姓名：</td>--%>
<%--		<td>--%>
<%--			<input type="text" class="tab_text" name="" />--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td class="tab_width">身份证号：</td>--%>
<%--		<td>--%>
<%--			<input type="text" class="tab_text validate[]"  name="" />--%>
<%--		</td>		--%>
<%--		<td colspan='4'>--%>
<%--			<input type="button" value="查询" class="eflowbutton" onclick="search()" />						--%>
<%--			<input type="button" value="重置" class="eflowbutton" onclick="reset()" /> 	--%>
<%--		 	<!-- <a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="dwjbxxcx();">查询</a> -->	 		--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--</table>--%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">信息变更记录</th>
	</tr>
	<tr>
		<td class="tab_width">个人编号：</td>
		<td>
			<input type="text" class="tab_text validate[]"  name="QYJY_GRBH" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="personInsureSelector('aac001','QYJY_GRBH');"></a>
		</td>		
		<td class="tab_width"><font class="tab_color">*</font>社会保障号码：</td>
		<td>
			<input type="text" class="tab_text validate[required]" name="QYJY_SHBZHM" />
			<a href="javascript:void(0);"  class="easyui-linkbutton" iconcls="eicon-search" plain="true" onclick="personInsureSelector('aac002','QYJY_SHBZHM');"></a>
		</td>
		<td class="tab_width">姓名：</td>
		<td>
			<input type="text" class="tab_text" name="QYJY_XM" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">性别：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="sex"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="--请选择--" name="QYJY_XB">
			</eve:eveselect>
		</td>	
		<td class="tab_width">出生日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="QYJY_CSRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>	
		<td class="tab_width">参加工作日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="QYJY_GZRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>		
	</tr>
	<tr>
		<td class="tab_width">单位管理码：</td>
		<td>			
			<input type="text" class="tab_text" name="QYJY_DWGLM" />
		</td>	
		<td class="tab_width">单位名称：</td>
		<td>
			<input type="text" class="tab_text" name="QYJY_DWMC" />
		</td>	
		<td class="tab_width">单位类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SBDWLX"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYJY_DWLX">
			</eve:eveselect>
		</td>		
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">参保信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>中断日期：</td>
		<td>
			<input type="text" class="tab_text Wdate" name="QYJY_ZDRQ" 
				onclick="WdatePicker({dateFmt:'yyyyMMdd',isShowClear:true})" readonly="true" />
		</td>
		<td class="tab_width"><font class="tab_color">*</font>停保原因：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SBTBYY"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="--请选择--" name="QYJY_TBYY">
			</eve:eveselect>
		</td>				
		<td class="tab_width">备注：</td>
		<td>
			<input type="text" class="tab_text" name="QYJY_BZ" />
		</td>		
	</tr>
</table>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="6">参保险种信息</th>
	</tr>
</table>	
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:150px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="xzConfGrid" fitcolumns="true" checkonselect="true" 
			selectoncheck="true" fit="true" border="false"
			data-options="autoSave:true,method:'post',url:'sbQyzjyController.do?xzxxJson&ywId=${ywId}&exeId=${exeId}'"
			>
			<thead>
				<tr>
					<th data-options="field:'ck',align:'left',checkbox:true"></th>
					<th data-options="field:'aae140',width:'23%',align:'center',formatter:commonFormat">险种类型</th>
					<th data-options="field:'aac049',width:'20%',align:'center'">首次参保年月</th>
					<th data-options="field:'aae030',width:'20%',align:'center'">本次参保日期</th>
					<th data-options="field:'aac008',width:'20%',align:'center',formatter:commonFormat">人员参保状态</th>
					<th data-options="field:'aac031',width:'20%',align:'center',formatter:commonFormat">个人缴费状态</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table>	

