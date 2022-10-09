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
		<td>
			<input type="text" maxlength="32" class="tab_text inputBackgroundCcc validate[required]" style="width:52%;" readonly="true" name="PRJNUM" value="${busRecord.PRJNUM}"  placeholder="自动回填"/>
		</td>
		<td class="tab_width"> <span class="tab_color">*</span>项目代码：</td>
		<td>
			<input type="text" maxlength="32" class="tab_text inputBackgroundCcc validate[required]" style="width:52%;" readonly="true" name="PROJECTCODE" value="${busRecord.PROJECTCODE}" placeholder="自动回填"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> <span class="tab_color">*</span>施工许可变更项：</td>
		<td colspan="3">
			
			<eve:radiogroup typecode="CHANGEITEM" width="130px" fieldname="CHANGEITEM" value="${busRecord.CHANGEITEM}" defaultvalue="1" onclick="changeChangeItem(this.value)"></eve:radiogroup>
		</td>
	</tr>
</table>
<br/>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="bg_table">
	<tr>
		<td class="tab_width"></td>
		<td style="text-align:center;font-weight:bold;">变更前</td>
		<td style="text-align:center;font-weight:bold;">变更后</td>
	</tr>
	<tr class="xmjlorxmzj_tr">
		<td class="tab_width">  <span class="tab_color">*</span>姓名</td>
		<td>
		  <input type="hidden" name="INDEXNUM" />
		  <select id="PERSONNAME" name="PERSONNAME" class="tab_text validate[required]" style="width:186px;"  onchange="changePersonName();">
		  </select>
		</td>
		<td>
		  <input type="text" class="tab_text" style="width:70%;" name="PERSONNAME_AFTER"  maxlength="16" value="${busRecord.PERSONNAME_AFTER}" placeholder="请输入变更后的姓名"/>
		</td>
	</tr>
	<tr class="xmjlorxmzj_tr">
		<td class="tab_width">  <span class="tab_color">*</span>证件类型</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="TBIDCARDTYPEDIC"
			dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'PERSONIDCARD');"
			defaultEmptyText="请选择证件类型" name="IDCARDTYPENUM" id="IDCARDTYPENUM"   value="1">
			</eve:eveselect>
		</td>
		<td>
			<eve:eveselect clazz="tab_text" dataParams="TBIDCARDTYPEDIC"
			dataInterface="dictionaryService.findDatasForSelect"  onchange="setSgxkZjValidate(this.value,'PERSONIDCARD_AFTER');"
			defaultEmptyText="请选择证件类型" name="IDCARDTYPENUM_AFTER" id="IDCARDTYPENUM_AFTER"   value="1">
			</eve:eveselect>
		</td>
	</tr>
	<tr class="xmjlorxmzj_tr">
		<td class="tab_width">  <span class="tab_color">*</span>证件号码</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[required]" style="width:70%;" name="PERSONIDCARD" readonly="true"  maxlength="32" value="${busRecord.PERSONIDCARD}" placeholder="自动回填"/>
		</td>
		<td>
		  <input type="text" class="tab_text" style="width:70%;" name="PERSONIDCARD_AFTER"  maxlength="32" value="${busRecord.PERSONIDCARD_AFTER}" placeholder="请输入变更后的证件号码"/>
		</td>
	</tr>
	<tr class="xmjlorxmzj_tr">
		<td class="tab_width">  <span class="tab_color">*</span>电话号码</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[required]" style="width:70%;" name="PERSONPHONE" readonly="true"  maxlength="16" value="${busRecord.PERSONPHONE}" placeholder="自动回填"/>
		</td>
		<td>
		  <input type="text" class="tab_text" style="width:70%;" name="PERSONPHONE_AFTER"  maxlength="16" value="${busRecord.PERSONPHONE_AFTER}" placeholder="请输入变更后的电话号码"/>
		</td>
	</tr>
	<tr class="qt_tr">
		<td class="tab_width"> 工程名称</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" style="width:70%;" name="PROJECT_NAME" readonly="true"  maxlength="128" value="${busRecord.PROJECT_NAME}" placeholder="自动回填"/>
		</td>
		<td>
		  <input type="text" class="tab_text validate[]" style="width:70%;" name="PROJECT_NAME_AFTER"  maxlength="128" value="${busRecord.PROJECT_NAME_AFTER}" placeholder="请输入变更后的工程名称"/>
		</td>
	</tr>
	<tr class="qt_tr">
		<td class="tab_width"> 建设地址</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" style="width:70%;" name="PROADDRESS" readonly="true"  maxlength="100" value="${busRecord.PROADDRESS}" placeholder="自动回填"/>
		</td>
		<td>
		  <input type="text" class="tab_text validate[]" style="width:70%;" name="PROADDRESS_AFTER"  maxlength="100" value="${busRecord.PROADDRESS_AFTER}" placeholder="请输入变更后的建设地址"/>
		</td>
	</tr>
	<tr class="qt_tr">
		<td class="tab_width"> 建设规模</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" style="width:70%;" name="PRJSIZE" readonly="true"  maxlength="22" value="${busRecord.PRJSIZE}" placeholder="自动回填"/>
		</td>
		<td>
		  <input type="text" class="tab_text validate[]" style="width:70%;" name="PRJSIZE_AFTER"  maxlength="22" value="${busRecord.PRJSIZE_AFTER}" placeholder="请输入变更后的建设规模" />
		</td>
	</tr>
	<tr class="qt_tr">
		<td class="tab_width"> 合同价格（万元）</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[],custom[JustNumber]" style="width:70%;" name="INVEST" readonly="true"  maxlength="16" value="${busRecord.INVEST}" placeholder="自动回填"/>
		</td>
		<td>
		  <input type="text" class="tab_text validate[],custom[JustNumber]" style="width:70%;" name="INVEST_AFTER"  maxlength="16" value="${busRecord.INVEST_AFTER}" placeholder="请输入变更后的合同价格" 
		  onblur="onlyNumber6(this);" onkeyup="onlyNumber6(this);"/>
		</td>
	</tr>
	<tr class="qt_tr">
		<td class="tab_width"> 合同工期</td>
		<td>
		  <input type="text" class="tab_text inputBackgroundCcc validate[]" style="width:70%;" name="WORKDAYS" readonly="true"  maxlength="128" value="${busRecord.WORKDAYS}" placeholder="自动回填"/>
		</td>
		<td>
		  <input type="text" class="tab_text validate[]" style="width:70%;" name="WORKDAYS_AFTER"  maxlength="128" value="${busRecord.WORKDAYS_AFTER}"  placeholder="请输入变更后的合同工期"/>
		</td>
	</tr>
	<tr>		
		<td class="tab_width"> <span class="tab_color">*</span>变更说明</td>
		<td colspan="2">
			<textarea rows="3" cols="200" name="BGSM" 
			class="tab_text validate[required],maxSize[100]"
			style="width:70%;height:100px;"  placeholder="请输入变更说明" >${busRecord.BGSM}</textarea>
		</td>	
	</tr>
</table>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->