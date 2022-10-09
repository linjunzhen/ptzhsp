<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.bsfw.service.BdcApplyService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    BdcApplyService bdcApplyService = (BdcApplyService)AppUtil.getBean("bdcApplyService");
	String ywId = request.getParameter("BDC_SUB_YWID");
	String hftype = request.getParameter("hftype");
	if(ywId != null && hftype != null){
		Map<String,Object> record = bdcApplyService.getSubRecordInfo(hftype, ywId);
		request.setAttribute("recordinfo", record);
	}
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
	<th>城乡居民缴费账目发送地税</th>
</table>
<div id="fsdsToolbar">	
<table class="dddl-contentBorder dddl_table"
style="background-repeat:repeat;width:100%;border-collapse:collapse;">		
	<tr style="height: 40px;line-height: 30px;">	
		<td class="tab_width" style="text-align:center">证件号码</td>
		<td>	
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="ZJHM" value="" style="width: 180px;" />
		</td>
		<td class="tab_width">姓名</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
				name="NAME" value="" style="width: 180px;" />
		</td>
		<td class="tab_width" style="text-align:center"><font class="tab_color ">*</font>年度</td>
		<td>
			<input type="text" class="tab_text validate[]" maxlength="64"
			name="MONTH" value="" style="width: 180px;" />
		</td>
		<td>
			<input type="button" value="查询" class="eve-button" onclick="bdcdaxxcxSearch()" />
			<input type="button" value="重置" class="eve-button" onclick="reset()" />
		</td>
	</tr>
	<tr>
		<td colspan="7">
			<input style="margin-left:100px" type="button" value="发送地税" class="eve-button" onclick="sendDs()" />
		</td>
	</tr>	
	</table>
</div>
<table class="easyui-datagrid" rownumbers="true" pagination="false"
id="fsdsGrid" fitColumns="false" toolbar="#fsdsToolbar"
method="post" idField="YWH" checkOnSelect="false"
selectOnCheck="false" fit="true" border="false" nowrap="false"
url="bdcDyqscdjController.do?bdcdaxxcxDatagrid">
<thead>
	<tr>
		<th field="ck" checkbox="true"></th>
		<th data-options="field:'yes',hidden:true"></th>
		<th data-options="field:'YXBS',align:'left'" width="10%">发送税务标志</th>
		<th data-options="field:'CQZT',align:'left'" width="10%">发送操作人</th>
		<th data-options="field:'ZH',align:'left'" width="10%">发送时间</th>
		<th data-options="field:'HH',align:'left'" width="10%">姓名</th>
		<th data-options="field:'BDCQZH',align:'left'" width="10%">证件号码</th>
		<th data-options="field:'BDCDYH',align:'left'" width="10%">个人保险号</th>
		<th data-options="field:'QLRMC',align:'left'" width="10%">居民单位保险号</th>
		<th data-options="field:'QSZT',align:'left'" width="10%">单位名称</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">应缴类型</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">险种类型</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">冲销标志</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">建账年月</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">账目年度</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">起始账目年月</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">到账来源</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">到账标志</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">征收方式</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">参保身份</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">截止账目日期</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">缴费月数</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">个人应缴</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">划拨金额总计</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">到账日期</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">登账人</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">建账人</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">建账日期</th>
		<th data-options="field:'FWBM',align:'left'" width="10%">分中心</th>
	</tr>
</thead>
</table> 




	

	
			
		
