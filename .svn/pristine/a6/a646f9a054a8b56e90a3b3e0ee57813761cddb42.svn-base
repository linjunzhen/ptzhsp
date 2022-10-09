<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" id="controller">
	<tr>
		<th colspan="4" style="background-color: #FFD39B;">外商投资企业最终实际控制人信息</th>
	</tr>
	<tr id="controller_1">
		<td>
			<table class="tab_tk_pro2">
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>姓名/名称：</td>
					<td colspan="3">
						<input type="text" class="tab_text validate[required]" style="width:600px;"
							name="CONTROLLER_NAME"  maxlength="62" />
					</td>
				</tr>
				<tr>
					<td class="tab_width"> 姓名/名称(英文)：</td>
					<td colspan="3">
						<input type="text" class="tab_text validate[[],custom[onlyLetterNumber]]" style="width:600px;"
							name="CONTROLLER_NAME_ENG"  maxlength="126" />
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>国籍（或地区）/注册地：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
						name="REGISTERED_PLACE"  maxlength="62"/></td>
				</tr>
				<tr>
					<%-- <td class="tab_width"><font class="tab_color">*</font>主体资格证明/护照：</td>
					<td>
						<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择证照类型" name="INVESTOR_IDTYPE">
						</eve:eveselect>
					</td> --%>
					<td class="tab_width"><font class="tab_color">*</font>证照号码：</td>
					<td colspan="3"><input type="text" class="tab_text validate[required]"
						name="CONTROLLER_IDNO" maxlength="32"/></td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>类别：</td>
					<td colspan="3">
						<input type="radio" name="CONTROLLER_TYPE_1" value="01" >境外上市公司
						<input type="radio" name="CONTROLLER_TYPE_1" value="02" >境外自然人
						<input type="radio" name="CONTROLLER_TYPE_1" value="03" >外国政府机构（含政府基金）
						<input type="radio" name="CONTROLLER_TYPE_1" value="04" >国际组织
						<input type="radio" name="CONTROLLER_TYPE_1" value="05" >境内上市公司
						<input type="radio" name="CONTROLLER_TYPE_1" value="06" >境内自然人
						<input type="radio" name="CONTROLLER_TYPE_1" value="07" >国有/集体企业
					</td>
				</tr>
				<tr>
					<td class="tab_width"><font class="tab_color">*</font>实际控制方式：</td>
					<td colspan="3">
						<input type="radio" name="CONTROL_METHOD_1" value="01" >单独或与关联投资者共同直接或间接持有企业50%以上股份、股权、财产份额、表决权或者其他类似权益<br>
						<input type="radio" name="CONTROL_METHOD_1" value="02" >单独或与关联投资者共同直接或间接持有企业股份、股权、财产份额、表决权或者其他类似权益不足50%，但所享有的表决权足以对权力机构决议产生重大影响<br>
						<input type="radio" name="CONTROL_METHOD_1" value="03" >对企业的经营决策、人事、财务、技术等有重大影响的其他情形（请详细说明）
						<input type="text" class="tab_text"  style="width:600px;"
								name="CONTROL_METHOD_OTHER" maxlength="128"/>
					</td>
				</tr>
			</table>
			<div class="tab_height2"></div>
		</td>
		<td>
			<input type="button" name="deleteOneController" onclick="deleteController('1');" value="删除行">
			<br>
			<br>
			<input type="button" name="addOneController" value="增加一行" onclick="addController();">
		</td>
	</tr>
</table>
