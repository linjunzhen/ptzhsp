<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${controllerList}" var="controller" varStatus="s">
	<div style="position:relative;">
		<table  cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font class="syj-color2">*</font>姓名/名称（中文）：</th>
				<td><input type="text" class="syj-input1 validate[required]" name="${s.index}CONTROLLER_NAME" value="${controller.CONTROLLER_NAME}"  maxlength="64"  placeholder="请输入中文名称"/></td>
				<th> 姓名/名称（英文）：</th>
				<td><input type="text" class="syj-input1 validate[custom[onlyLetterNumber]]" name="${s.index}CONTROLLER_NAME_ENG"  maxlength="64"  value="${controller.CONTROLLER_NAME_ENG}" placeholder="请输入英文名称"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>国籍（或地区）/注册地：</th>
				<td>
					<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjgb"
						dataInterface="dictionaryService.findDatasForCountrySelect"
						defaultEmptyText="请选择国籍（或地区）/注册地" name="${s.index}REGISTERED_PLACE"  value="${controller.REGISTERED_PLACE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证照号码：</th>
				<td><input type="text" class="syj-input1 validate[required]" name="${s.index}CONTROLLER_IDNO"  value="${controller.CONTROLLER_IDNO}" maxlength="32"  placeholder="请输入证照号码"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>控制人类别：</th>
				<td colspan="3">
					<input type="radio" class="validate[required]" name="${s.index}CONTROLLER_TYPE" value="01" <c:if test="${controller.CONTROLLER_TYPE=='01'}"> checked="checked"</c:if>>境外上市公司
					<input type="radio" class="validate[required]" name="${s.index}CONTROLLER_TYPE" value="02" <c:if test="${controller.CONTROLLER_TYPE=='02'}"> checked="checked"</c:if>>境外自然人
					<input type="radio" class="validate[required]" name="${s.index}CONTROLLER_TYPE" value="03" <c:if test="${controller.CONTROLLER_TYPE=='03'}"> checked="checked"</c:if>>外国政府机构（含政府基金）
					<input type="radio" class="validate[required]" name="${s.index}CONTROLLER_TYPE" value="04" <c:if test="${controller.CONTROLLER_TYPE=='04'}"> checked="checked"</c:if>>国际组织
					<input type="radio" class="validate[required]" name="${s.index}CONTROLLER_TYPE" value="05" <c:if test="${controller.CONTROLLER_TYPE=='05'}"> checked="checked"</c:if>>境内上市公司
					<input type="radio" class="validate[required]" name="${s.index}CONTROLLER_TYPE" value="06" <c:if test="${controller.CONTROLLER_TYPE=='06'}"> checked="checked"</c:if>>境内自然人
					<input type="radio" class="validate[required]" name="${s.index}CONTROLLER_TYPE" value="07" <c:if test="${controller.CONTROLLER_TYPE=='07'}"> checked="checked"</c:if>>国有/集体企业
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>实际控制方式：</th>
				<td colspan="3">
					<input type="radio" class="validate[required]" name="${s.index}CONTROL_METHOD" onclick="onclickControlMethod('01','${s.index}')" value="01" <c:if test="${controller.CONTROL_METHOD=='01'}"> checked="checked"</c:if>>单独或与关联投资者共同直接或间接持有企业50%以上股份、股权、财产份额、表决权或者其他类似权益<br>
						<input type="radio" class="validate[required]" name="${s.index}CONTROL_METHOD" onclick="onclickControlMethod('02','${s.index}')" value="02" <c:if test="${controller.CONTROL_METHOD=='02'}"> checked="checked"</c:if>>单独或与关联投资者共同直接或间接持有企业股份、股权、财产份额、表决权或者其他类似权益不足50%，但所享有的表决权足以对权力机构决议产生重大影响<br>
						<input type="radio"  class="validate[required]" name="${s.index}CONTROL_METHOD" onclick="onclickControlMethod('03','${s.index}')" value="03" <c:if test="${controller.CONTROL_METHOD=='03'}"> checked="checked"</c:if>>对企业的经营决策、人事、财务、技术等有重大影响的其他情形（请详细说明）
						<input type="text" class="syj-input1"  style="width:200px;" placeholder="请输入其他方式" <c:if test="${controller.CONTROL_METHOD=='01' || controller.CONTROL_METHOD=='02'}">disabled="disabled"</c:if> name="${s.index}CONTROL_METHOD_OTHER" maxlength="128"  value="${controller.CONTROL_METHOD_OTHER}"/>
				</td>
			</tr>
		</table>
		<c:if test="${s.index>0}">
		<a  href="javascript:void(0);" onclick="javascript:delJwtzzxxDiv(this);" class="syj-closebtn"></a>
		</c:if>
	</div>
</c:forEach>