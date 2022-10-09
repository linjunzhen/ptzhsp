<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<!-- 受理信息 -->

		<%--抵押JSON信息--%>
	 	<input type="hidden" name="YGXX_JSON" value="${serviceItem.YGXX_JSON}">
	 	<input type="hidden" name="FWXX_JSON" value="${serviceItem.FWXX_JSON}">
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
						   name="QLLX" value="预购商品房抵押预告登记"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>不动产单元号：</td>
				<td><input type="text" class="tab_text validate[required,custom[estateNum]]" style="width: 600px" onblur="checkIsAuditPass();"
						   name="ESTATE_NUM" id="ESTATE_NUM" value="${busRecord.ESTATE_NUM }" maxlength="64"/>
					<div id="bdcdyhblqk" style="color:red;"></div>
				</td>
				<td colspan='2'>
					<input type="button" value="不动产预告登记查询" class="eflowbutton" onclick="showSelectBdcYgdacx();" style="margin:0 5px;"/> 
				</td>
			</tr>
			<%-- <tr>
				<td class="tab_width">外网申请编号：</td>
				<td style="width:600px;">
				   <input type="text" class="tab_text validate[]" style="width:600px;"
					name="BDC_WWSQBH" value="${serviceItem.BDC_WWSQBH }" maxlength="32"/>	
				</td>
				<td colspan='2'>
				<a href="javascript:void(0);" id="hqsjww" onclick="getBdcWwData();" class="eflowbutton">获取数据/外网</a>
	        	</td>
	        </tr> --%>
			<tr>
				<td class="tab_width"><font class="tab_color ">*</font>申请人(单位)：</td>
				<td><input type="text" class="tab_text validate[required]" maxlength="32"
						   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
				<td class="tab_width"><font class="tab_color">*</font>持证类型：</td>
				<td style="width:430px">
					<eve:eveselect clazz="tab_text validate[required]" dataParams="CZLX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
					defaultEmptyText="选择持证类型" name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="1">
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
				<td><input type="text" class="tab_text " maxlength="32"
						   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
				<td class="tab_width">通知人电话：</td>
				<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="11"
					name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
			</tr>
			<tr>
				<td class="tab_width">备注：</td>
				<td colspan="3"><input type="text" class="tab_text validate[]" maxlength="60"
					name="REMARK" value="${busRecord.REMARK}" style="width: 72%;"  />
				</td>
			</tr>
		</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="wwcl" style="display: none;">

</table>