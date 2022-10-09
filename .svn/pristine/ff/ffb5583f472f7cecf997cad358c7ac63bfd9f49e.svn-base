<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="COMPANY_FORM"  >

	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>企业名称：</th>
			<td colspan="3">
			    <input type="hidden" id= "COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
				<input type="text" class="syj-input1 validate[required],custom[verifyCompanyNameExist]" style="width:740px;"
					name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称" value="${busRecord.COMPANY_NAME}"/>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" style="margin-top: -1px;">
<%--		<tr style="display: none;">--%>
<%--			<th><font class="syj-color2">*</font>是否已通过名称自主选用：</th>--%>
<%--			<td>--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1}"> checked="checked"</c:if>>是--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS!=1}"> checked="checked"</c:if>>否--%>
<%--			</td>--%>
<%--			<th> 名称自主选用文号：</th>--%>
<%--			<td><input type="text" class="syj-input1" maxlength="32"--%>
<%--				name="PREAPPROVAL_NUM"  placeholder="请输入名称自主选用文号"  disabled="disabled" value="${busRecord.PREAPPROVAL_NUM}"/></td>--%>
<%--		</tr>--%>
		<tr>
			<th><font class="syj-color2">*</font>主要经营场所：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required]" style="width:740px;"
				name="REGISTER_ADDR" maxlength="117"  placeholder="请输入注册地址" value="${busRecord.REGISTER_ADDR}"/></td>
		</tr>
		<tr>
			<th> <font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="REGISTERADD_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.REGISTERADD_OWNER}"/></td>
			<th> <font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input type="text" class="syj-input1 validate[required]" 
				name="REGISTERADD_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.REGISTERADD_TEL}"/></td>

		</tr>
		<tr>
			<th><font class="syj-color2">*</font>是否另设生产经营地址：</th>
			<td colspan="3">
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="1" 
					<c:if test="${busRecord.IS_OTHER_PLACE==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="0" 
					<c:if test="${busRecord.IS_OTHER_PLACE==0}"> checked="checked"</c:if>/>否
				</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>生产经营地址：</th>
			<td colspan="3"><input disabled="false" type="text" class="syj-input1 validate[required]" style="width:740px;"
				name="BUSINESS_PLACE" maxlength="64"  placeholder="请输入生产经营地址" value="${busRecord.BUSINESS_PLACE}"/></td>
		</tr>
		<tr>
			<th> <font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input disabled="false" type="text" class="syj-input1 validate[required]" 
				name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
			<th> <font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input disabled="false" type="text" class="syj-input1 validate[required]" 
				name="PLACE_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_TEL}"/></td>

		</tr>	
		<tr>
			<th><font class="syj-color2">*</font>企业联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required]" maxlength="32" 
				name="CONTACT_PHONE" placeholder="请输入企业联系电话" value="${busRecord.CONTACT_PHONE}"/></td>
			<th><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" disabled="false" class="syj-input1 validate[required],custom[chinaZip]"
				name="POST_CODE" value="350400" maxlength="6"   placeholder="请输入邮政编码"/></td>

		</tr>
		
		<tr>
			<th><font class="syj-color2">*</font>出资额：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required],custom[JustNumber]" style="width:500px;"
					name="CAPITAL_CONTRIBUTION" maxlength="16"  placeholder="请输入出资额"  value="${busRecord.CAPITAL_CONTRIBUTION}"/>万元
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>企业类型：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="COMPANY_TYPE"  value="${requestParams.COMPANY_TYPE}"/>
				<input type="hidden" name="COMPANY_TYPECODE" value="${requestParams.COMPANY_TYPECODE}"/>
				<input type="hidden" name="COMPANY_SETTYPE" value="${requestParams.COMPANY_SETTYPE}"/>	
				<input type="hidden" name="COMPANY_SETNATURE" value="${requestParams.COMPANY_SETNATURE}"/>				
			</td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>				
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb:中国"
				dataInterface="dictionaryService.findDatasForCountrySelect"
				defaultEmptyText="请选择国别" name="COMPANY_COUNTRY" value="${busRecord.COMPANY_COUNTRY}">
				</eve:eveselect>
			</td>
			<!--<th><font class="syj-color2">*</font>企业类别：</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="hhqylb"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择企业类别" name="COMPANY_NATURE"  value="${busRecord.COMPANY_NATURE}">
				</eve:eveselect>				
			</td>-->
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>合伙期限：</th>
			<td>
				<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
				<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
			</td>
			<th> 合伙期限年数：</td>
			<td><input type="text" class="syj-input1 validate[required],custom[numberplus]" 
				name="BUSSINESS_YEARS"  placeholder="请输入经营期限年数"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>经营范围：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
					class="eve-textarea validate[required],maxSize[2000]" readonly="readonly"
					style="width:90%;height:100px;"  placeholder="请选择经营范围"  onclick="showSelectJyfwNew();">${busRecord.BUSSINESS_SCOPE}</textarea> 					
				<input type="hidden" name="BUSSINESS_SCOPE_ID"  value="${busRecord.BUSSINESS_SCOPE_ID}">
				<input type="hidden" name="FLOW_DEFID"  value="${EFLOW_FLOWDEF.DEF_KEY }">
				<a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a>
				<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
				<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
				中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
				可以参照政策文件、行业习惯或者专业文献等提出申请
				<br/>㈢，第一项经营范围必须与名称中的行业相一致</div>
				<div style="margin-top: 5px;margin-bottom: 5px;">
				<input type="radio" name="NO_ACCESS_MANAGE" value="1" <c:if test="${busRecord.NO_ACCESS_MANAGE==1}"> checked="checked"</c:if>>经营范围不涉及国家规定实施的准入特别管理措施
				</div>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>职工人数/从业人数：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required],custom[numberplus]"
				name="STAFF_NUM"  placeholder="请输入职工人数/从业人数"  maxlength="8" value="${busRecord.STAFF_NUM}"/></td>
		</tr>
	</table>
</form>
