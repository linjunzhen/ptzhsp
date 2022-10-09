<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
	String ywId = request.getParameter("YB_YWID");
	request.setAttribute("ywId",ywId);
	String exeId = request.getParameter("exeId");
	request.setAttribute("exeId",exeId);
	
%>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>	
<script type="text/javascript">
	//重置按钮
	function reset(){
		$('input[name="ZJHM"]').val();
		$('input[name="NAME"]').val();
	}
	
	
</script>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<th>居民查询建账</th>
</table>
<div id="cxjzToolbar">	
<table class="dddl-contentBorder dddl_table"
style="background-repeat:repeat;width:100%;border-collapse:collapse;">		
	<tr style="height: 40px;line-height:30px;">	
		<td class="tab_width" style="text-align:center">证件号码</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZJHM" value="" style="width: 180px;" />
		</td>
		<td class="tab_width">姓名</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="NAME" value="" style="width: 180px;" />
			<input type="button" value="查询" class="eve-button" onclick="search()" />
			<input type="button" value="重置" class="eve-button" onclick="reset()" />
		</td>
	</tr>
	<tr style="height: 40px;line-height: 30px;">	
		<td class="tab_width" style="text-align:center"><font class="tab_color ">*</font>年度</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="MONTH" value="" style="width: 180px;" />
		</td>
		<td colspan="2">
			<input type="checkbox" name="yes" value="" />是否9-12月份账目
			<input type="button" value="保存" class="eve-button" onclick="save()" />
			</td>	
		</tr>	
	</table>
</div>
<table cellpadding="0" cellspacing="1" style="width:100%" >
	<tr>
		<td style="height:200px">
			<table class="easyui-datagrid" rownumbers="true" pagination="false"
			id="cxjzGrid" fitColumns="false" toolbar="#cxjzToolbar" style="height:200px"
			method="post" idField="YWH" checkOnSelect="true"
			selectOnCheck="true" fit="true" border="false" nowrap="false"
			url="">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'yes',hidden:true"></th>
					<th data-options="field:'YXBS',align:'left'" width="10%">居民单位ID</th>
					<th data-options="field:'CQZT',align:'left'" width="10%">居民单位编号</th>
					<th data-options="field:'ZH',align:'left'" width="10%">居民单位名称</th>
					<th data-options="field:'HH',align:'left'" width="10%">人员编号</th>
					<th data-options="field:'BDCQZH',align:'left'" width="10%">证件号码</th>
					<th data-options="field:'BDCDYH',align:'left'" width="10%">姓名</th>
					<th data-options="field:'QLRMC',align:'left'" width="10%">参保开始日期</th>
					<th data-options="field:'QSZT',align:'left'" width="15%">参保身份</th>
					<th data-options="field:'FWBM',align:'left'" width="15%">险种</th>
				</tr>
			</thead>
			</table>
		</td>
	</tr>
</table> 



	

	
			
		
