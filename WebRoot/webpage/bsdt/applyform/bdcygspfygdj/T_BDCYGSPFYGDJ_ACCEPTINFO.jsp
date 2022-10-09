<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%--预购商品房预告登记 受理信息 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">受理信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>登记类型：</td>
				<td><input type="text" class="tab_text " disabled="disabled" 
					name="CATALOG_NAME" value="${serviceItem.CATALOG_NAME }"/></td>
				<td class="tab_width"><font class="tab_color">*</font>权利类型：</td>
				<td><input type="text" class="tab_text " disabled="disabled"
						   name="ITEM_NAME" value="${serviceItem.ITEM_NAME }"/></td>
	        </tr>
				<td class="tab_width"><font class="tab_color" color="red">*</font>不动产单元号：</td>
				<td style="width:600px;">
				   <input type="text" class="tab_text validate[required,custom[estateNum]]" style="width:600px;"
					name="BDCDYH" value="${serviceItem.BDCDYH }" />	
				</td>
				<td colspan='2'>
				<a href="javascript:void(0);" class="eflowbutton"  onclick="showSelectFdchtbacx();">房地产合同备案信息查询</a>
	        	</td>
	        </tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
				    name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
				<td><input type="text" class="tab_text validate[required]"
						   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>坐落：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60" style="width: 72%;"
					name="ZL" value="${busRecord.ZL}" />
				</td>
			</tr>
			<tr>
				<td class="tab_width">通知人姓名：</td>
				<td><input type="text" class="tab_text "
						   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
				<td class="tab_width">通知人电话：</td>
				<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="16"
					name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
			</tr>
			<tr>
		<%-- 		<td class="tab_width">受理人员：</td>
				<td><input type="text" class="tab_text " maxlength="32"
						   name="SLRY" value="${busRecord.SLRY}"/></td> --%>
				<td class="tab_width">备注：</td>
				<td colspan='3'><input type="text" class="tab_text validate[]" maxlength="60"
					name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
				</td>
			</tr>
		</table>