<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">异议登记入簿</th>
	</tr>
	<tr>
		<td class="tab_width">登记机构</td>
		<td>
			<input type="text" class="tab_text" name="YYDJRB_DJJG" value="${busRecord.YYDJRB_DJJG }"/>
		</td>
		<td class="tab_width">区县代码<font class="tab_color">（需提供）</font></td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="qxdm" value="${busRecord.YYDJRB_QXDM }"
			dataInterface="dictionaryService.findDatasForSelect" 
			defaultEmptyText="" name="YYDJRB_QXDM" id="YYDJRB_QXDM">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width">登簿人</td>
		<td>
			<input type="text" class="tab_text" name="YYDJRB_DBR" id="YYDJRB_DBR"
			value="${busRecord.YYDJRB_DBR}"/>
		</td>
		<td class="tab_width">登记时间</td>
		<td><input type="text" class="tab_text Wdate " name="YYDJRB_DJSJ" value="${busRecord.YYDJRB_DJSJ }"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true})" readonly="true" /></td>
	</tr>
	<tr>
		<td class="tab_width">不动产登记证明号</td>
		<td ><input type="text" class="tab_text " 
			name="YYDJRB_BDCDJZMH" value="${busRecord.YYDJRB_BDCDJZMH}">
		</td>
		<td colspan="2">
			<input type="button" class="eflowbutton" id="BDC_DBBTN"  style="background:red;color:#fff;"
			onclick="bdcYydjBooking();" value="确认登簿"/>
			<input type="button" class="eflowbutton"  id="BDC_QZPRINT"  style="display:none;"
			onclick="bdcDJZMprint(2)" value="打印不动产权证书"/>
		</td>
	</tr>
</table>