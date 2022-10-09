<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="COMPANY_FORM"  >
<div class="syj-title1 tmargin20">
	<span>企业基本信息</span>
</div>
<div style="position:relative;">
	<%-- <table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th ><font class="syj-color2">*</font>分支机构名称：</th>
			<td colspan="3">
			    <input type="hidden" id="BRANCH_ID" value="${busRecord.BRANCH_ID}"/>
				<input type="text" class="syj-input1 validate[required]" style="width:740px;"
					name="BRANCH_NAME" id="BRANCH_NAME" maxlength="60" placeholder="请输入分支机构名称"  value="${busRecord.BRANCH_NAME}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>是否已通过名称自主选用：</th>
			<td>
				<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1}"> checked="checked"</c:if>>是
				<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS!=1}"> checked="checked"</c:if>>否
			</td>
			<th style="width: 160px;"> 名称自主选用文号：</th>
			<td><input type="text" class="syj-input1" maxlength="32"
				name="PREAPPROVAL_NUM"  placeholder="请输入名称自主选用文号"  disabled="disabled" value="${busRecord.PREAPPROVAL_NUM}"/></td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>营业场所：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]" style="width:740px;"
					name="BUSINESS_PLACE" maxlength="60"  placeholder="请输入营业场所（仅限平潭区域）"   value="${busRecord.BUSINESS_PLACE}"/>
			</td>
		</tr>
	</table> --%>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
	     <tr>
			<th ><font class="syj-color2">*</font>分支机构名称：</th>
			<td colspan="3">
			    <input type="hidden" id="BRANCH_ID" value="${busRecord.BRANCH_ID}"/>
				<input type="text" class="syj-input1 validate[required]" style="width:740px;"
					name="BRANCH_NAME" id="BRANCH_NAME" maxlength="60" placeholder="请输入分支机构名称"  value="${busRecord.BRANCH_NAME}"/>
			</td>
		</tr>
<%--		<tr>--%>
<%--			<th><font class="syj-color2">*</font>是否已通过名称自主选用：</th>--%>
<%--			<td>--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1}"> checked="checked"</c:if>>是--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS!=1}"> checked="checked"</c:if>>否--%>
<%--			</td>--%>
<%--			<th style="width: 160px;"> 名称自主选用文号：</th>--%>
<%--			<td><input type="text" class="syj-input1" maxlength="32"--%>
<%--				name="PREAPPROVAL_NUM"  placeholder="请输入名称自主选用文号"  disabled="disabled" value="${busRecord.PREAPPROVAL_NUM}"/></td>--%>
<%--		</tr>--%>
		<tr>
			<th ><font class="syj-color2">*</font>营业场所：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]" style="width:740px;"
					name="BUSINESS_PLACE" maxlength="60"  placeholder="请输入营业场所（仅限平潭区域）"   value="${busRecord.BUSINESS_PLACE}"/>
			</td>
		</tr>
		<%-- <tr>
			<th ><font class="syj-color2">*</font>住所产权：</th>
			<td>				
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="propertyRight"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择住所产权" name="PLACE_PROPERTY_RIGHT" value="${busRecord.PLACE_PROPERTY_RIGHT}">
				</eve:eveselect>
			</td>
			
			<th > 电子邮箱：</th>
			<td><input type="text" class="syj-input1 validate[[],custom[email]]"
				name="BRANCH_EMAIL" maxlength="32"  placeholder="请输入电子邮箱" value="${busRecord.BRANCH_EMAIL}"/></td>
		</tr> --%>
		<tr>
			<th ><font class="syj-color2">*</font>联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="PHONE" maxlength="16" placeholder="请输入联系电话"  value="${busRecord.PHONE}"/></td>
			
			<th ><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc validate[required,custom[chinaZip]]" readonly="readonly"
				name="POSTCODE" maxlength="6"  placeholder="请输入邮政编码" value="350400"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>从业人数：</th>
			<td>
				<input type="text" class="syj-input1 validate[required,custom[numberplus]]"
					name="EMPLOYED_NUM" maxlength="4"  placeholder="请输入从业人数" value="${busRecord.EMPLOYED_NUM}"/>
			</td>
			<%-- <th><font class="syj-color2">*</font>副本数：</th>
			<td>
				<input type="text" class="syj-input1 validate[required,custom[numberplus]]"
					name="LICENSE_COPY_NUM" maxlength="4"  placeholder="请输入副本数" value="${busRecord.LICENSE_COPY_NUM}"/>
			</td> --%>
			<td colspan="2"></td>
		</tr>
		<%-- <tr>
			<th><font class="syj-color2">*</font>电子营业执照数：</th>
			<td>
				<input type="text" class="syj-input1 validate[required,custom[numberplus]]"
					name="LICENSE_NUM" maxlength="4"  placeholder="请输入电子营业执照数" value="${busRecord.LICENSE_NUM}"/>
			</td>
			<td colspan="2"></td>
		</tr> --%>
		<tr>
			<th ><font class="syj-color2">*</font>生产经营地址：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 validate[required]" style="width:740px;"
					name="PREMISES" maxlength="60"  placeholder="请输入经营场所（仅限平潭区域）"   value="${busRecord.PREMISES}"/>
			</td>
		</tr>
		
		<%-- <tr>
			<th><font class="syj-color2">*</font>经营期限：</th>
			<td>
				<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
				<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
			</td>
			<th> 经营期限年数：</td>
			<td><input type="text" class="syj-input1 validate[required],custom[numberplus]" 
				name="BUSSINESS_YEARS"  placeholder="请输入经营期限年数"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/></td>
		</tr> --%>
		<tr>
			<th><font class="syj-color2">*</font>经营范围：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
					class="eve-textarea validate[required],maxSize[2000]" readonly="readonly"
					style="width:90%;height:100px;"  placeholder="请选择经营范围">${busRecord.BUSSINESS_SCOPE}</textarea> 					
				<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID}">
				<a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a>
				<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
				<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
				中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
				可以参照政策文件、行业习惯或者专业文献等提出申请</div>
				<input type="radio" name="IS_SCOPEINREGULATION" value="1" <c:if test="${busRecord.IS_SCOPEINREGULATION==1}"> checked="checked"</c:if>>经营范围不涉及国家规定实施的准入特别管理措施
			</td>
		</tr>
		<tr style="display: none;">
			<th> 投资行业：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
					class="eve-textarea validate[ validate[],maxSize[2000]]"
					style="width:90%;height:100px;"  placeholder="选择经营范围后投资行业自动回填"  >${busRecord.INVEST_INDUSTRY}</textarea> 					
				<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID}">
			</td>
		</tr>
		<tr style="display: none;">
			<th>行业编码：</th>
			<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc"  placeholder="选择经营范围后行业编码自动回填" readonly="readonly"
				name="INDUSTRY_CODE"   style="width:740px;" value="${busRecord.INDUSTRY_CODE}"/></td>
		</tr>
		<%-- <tr>
			<th> 备注：</th>
			<td colspan="3"><input type="text" class="syj-input1" style="width:740px;"
				name="SCOPE_REMARK" maxlength="128" placeholder="请输入备注" value="${busRecord.SCOPE_REMARK}"/></td>
		</tr> --%>
	</table>
</div>
</form>
