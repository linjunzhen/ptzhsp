<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>
	.field_width{width:192px;}
	.lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
	.inputBackgroundCcc{
		background-color:#ccc;
	}
</style>
<input type="hidden" id= "YW_ID" value="${busRecord.YW_ID}"/>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="4">施工许可证信息</th>
	</tr>
	<tr>
		<td class="tab_width"> <span class="tab_color">*</span>施工许可证编号：</td>
		<td colspan="3">
		  <input type="text" maxlength="32" class="tab_text validate[required]"  style="width:40%;" name="CONSTRNUM" />
		  <a id="loadData" onclick="loadData();" class="eflowbutton" style="padding: 3px 10px;">校 验</a>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> <span class="tab_color">*</span>报建编号：</td>
		<td colspan="3">
			<input type="text" maxlength="32" class="tab_text inputBackgroundCcc validate[required]" style="width:52%;" readonly="true" name="PRJNUM" value="${busRecord.PRJNUM}"  placeholder="自动回填"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> <span class="tab_color">*</span>工程项目名称：</td>
		<td>
			<input type="text" maxlength="32" class="tab_text inputBackgroundCcc validate[required]" style="width:52%;" readonly="true" name="PROJECT_NAME" value="${busRecord.PROJECT_NAME}"  placeholder="自动回填"/>
		</td>
		<td class="tab_width"> <span class="tab_color">*</span>工程项目代码：</td>
		<td>
			<input type="text" maxlength="32" class="tab_text inputBackgroundCcc validate[required]" style="width:52%;" readonly="true" name="PROJECTCODE" value="${busRecord.PROJECTCODE}" placeholder="自动回填"/>
		</td>
	</tr>
	<tr>		
		<td class="tab_width"> <span class="tab_color">*</span>废止说明</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="CANCELREASON" 
			class="tab_text validate[required],maxSize[128]"
			style="width:70%;height:100px;"  placeholder="请输入废止说明" >${busRecord.CANCELREASON}</textarea>
		</td>	
	</tr>
</table>
<br/>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->