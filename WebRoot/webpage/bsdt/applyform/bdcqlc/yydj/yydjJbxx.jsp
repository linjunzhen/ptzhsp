<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>申请人姓名：</td>
		<td ><input type="text" class="tab_text validate[required]" 
			name="JBXX_NAME" value="${busRecord.JBXX_NAME}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setZjValidate(this.value,'JBXX_IDNO');"
			defaultEmptyText="选择证件类型" name="JBXX_IDTYPE" value="${busRecord.JBXX_IDTYPE}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[required]"
			name="JBXX_IDNO" value="${busRecord.JBXX_IDNO}" />
		</td>
		<td colspan="2">
		 <input type="button" class="eflowbutton" value="身份证读卡" onclick="SQRRead();"/>		
		</td>
	</tr>
	<tr>
		<td class="tab_width">起始时间：</td>
		<td>		
			<input type="text"  id="JBXX_QSSJ" name="JBXX_QSSJ" value="${busRecord.JBXX_QSSJ}"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'JBXX_JSSJ\')}'})" 
			class="tab_text Wdate validate[]" readonly="true"/>
		</td>
		<td class="tab_width">结束时间：</td>
		<td>
			<input type="text" id="JBXX_JSSJ"  name="JBXX_JSSJ" value="${busRecord.JBXX_JSSJ}"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'JBXX_QSSJ\')}'})" 
			class="tab_text Wdate validate[]" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">异议事项</td>
		<td colspan="3"><input type="text"  class="tab_text validate[]"
			name="JBXX_YYSX" value="${busRecord.JBXX_YYSX}" style="width:72%;"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">附记</td>
		<td colspan="3"><input type="text" class="tab_text" 
			name="JBXX_FJ" value="${busRecord.JBXX_FJ}" style="width: 72%;"/>
			<input type="button" class="eflowbutton" value="意见模板" onclick="AppUtil.cyyjmbSelector('bdcyydj','JBXX_FJ');">
		</td>
	</tr>		
	<tr>
		<td class="tab_width">登记原因</td>
		<td colspan="3"><input type="text"  class="tab_text validate[]"
			name="JBXX_DJYY" value="${busRecord.JBXX_DJYY}" style="width: 72%;"/>
			<input type="button" class="eflowbutton" value="意见模板" onclick="AppUtil.cyyjmbSelector('bdcyydj','JBXX_DJYY');">
		</td>
	</tr>						
</table>
</div>