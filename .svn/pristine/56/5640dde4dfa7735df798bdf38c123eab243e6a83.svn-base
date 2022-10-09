<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%--抵押权注销登记 受理信息 --%>

<div class="bsbox clearfix" id="slxxInfo">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>受理信息</span></li>
		</ul>
	</div>
	<div style="padding: 25px 30px">
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th><span class="bscolor1">*</span>登记类型：</th>
				<td><input type="text" class="tab_text" disabled="disabled" 
					name="CATALOG_NAME" value="注销登记"/></td>
				<th><span class="bscolor1">*</span>权利类型：</th>
				<!-- <input type="text" class="tab_text" disabled="disabled"
						   name="ZX_QLLX" value="抵押权"/> -->			     
			    <td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="ZDQLLX"
					dataInterface="dictionaryService.findDatasForSelect" value="91"
					defaultEmptyText="选择权利类型" name="ZX_QLLX" id="ZX_QLLX">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span>持证类型：</th>
				<td>
					<div class="" style="width: 280px;">
						<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="CZLX"
						dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
						name="TAKECARD_TYPE" id="TAKECARD_TYPE" value="${busRecord.TAKECARD_TYPE}">
						</eve:eveselect>
					</div>
				</td>
				<th><span class="bscolor1">*</span>申请人(单位)：</th>
				<td><input type="text" class="tab_text validate[required]"
						   name="APPLICANT_UNIT" value="${busRecord.APPLICANT_UNIT }"/></td>
			</tr>
		    <tr>
                <th class="tab_width"><font class="tab_color ">*</font>申请方式：</th>
                <td colspan="3">
                    <input type="radio" name="APPLICANT_TYPE" value="1" checked="checked">单方申请
                    <input type="radio" name="APPLICANT_TYPE" value="2" >双方申请
                </td>
            </tr>
			<tr>
				<th><span class="bscolor1">*</span>坐落：</th>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
					name="LOCATED" style="width: 850px" value="${busRecord.LOCATED}" />
				</td>
			</tr>
			<tr>
				<th>通知人姓名：</th>
				<td><input type="text" class="tab_text "
						   name="NOTIFY_NAME" value="${busRecord.NOTIFY_NAME }"/></td>
				<th>通知人电话：</th>
				<td><input type="text" class="tab_text validate[custom[mobilePhoneLoose]]" maxlength="16"
					name="NOTIFY_TEL" value="${busRecord.NOTIFY_TEL}"  /></td>
			</tr>
			<tr>
				<th>受理人员：</th>
				<td><input type="text" class="tab_text " maxlength="32"
						   name="SLRY" value="${busRecord.SLRY}"/></td>
				<th>备注：</th>
				<td><input type="text" class="tab_text validate[]" maxlength="60"
					name="REMARK" value="${busRecord.REMARK}" />
				</td>
			</tr>
		</table>
	</div>
</div>

