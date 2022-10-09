<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>受理信息</th>
	</tr>
	<tr>
	   <td style="padding: 10px;">
	      <table cellpadding="1" cellspacing="1" class="tab_tk_pro2">		    
		    <tr>
		        <td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
		        <td><input type="text" class="tab_text " disabled="disabled" 
		            name="CATALOG_NAME" value="${serviceItem.CATALOG_NAME }"/></td>
		        <td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
		        <td><%-- <input type="text" class="tab_text " disabled="disabled"
		                   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/> --%>
		            <eve:eveselect clazz="tab_text validate[required]" dataParams="ZDQLLX"
		                    dataInterface="dictionaryService.findDatasForSelect" value=""
		                    defaultEmptyText="选择权利类型" name="HF_QLLX" id="HF_QLLX">
		            </eve:eveselect>
		        </td>
		    </tr>
		    <tr>
		        <td class="tab_width"><font class="tab_color ">*</font>不动产单元号：</td>
		        <td><input type="text" class="tab_text validate[required,custom[estateNum]]"
		                   name="ESTATE_NUM" value="${busRecord.ESTATE_NUM }"/></td>
		        <td class="tab_width">定着物类型：</td>
		        <td>
		            <eve:eveselect clazz="tab_text validate[]" dataParams="dzwlx"
		            dataInterface="dictionaryService.findDatasForSelect" 
		            defaultEmptyText="选择定着物类型" name="DZWLX" id="DZWLX" value="${busRecord.DZWLX}">
		            </eve:eveselect>
		        </td>
		    </tr>
		    <tr>
		        <td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
		        <td><input type="text" class="tab_text validate[required]"
		                   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
		        <td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
		        <td>
		            <eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
		                dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
		                defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
		            </eve:eveselect>
		        </td>
		    </tr>
		    <tr>
		        <td class="tab_width"><font class="tab_color ">*</font>坐落：</td>
		        <td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
		            name="LOCATED" value="${busRecord.LOCATED}" style="width: 72%;"  />
		        </td>
		    </tr>
		    <tr>
		        <td class="tab_width">通知人姓名：</td>
		        <td><input type="text" class="tab_text "
		                   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
		        <td class="tab_width">通知人电话：</td>
		        <td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11"
		            name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
		    </tr>
		    <tr>
		        <td class="tab_width">受理人员：</td>
		        <td><input type="text" class="tab_text "
		                   name="SLRYMC" value="${busRecord.SLRYMC }" readonly="readonly"/></td> 
		        <td></td>
		        <td></td>
		    </tr>
		    <tr>
		        <td class="tab_width">备注：</td>
		        <td colspan="3"><input type="text" class="tab_text " maxlength="60"
		            name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
		        </td>
		    </tr>
		</table>
	   </td>
	</tr>
</table>