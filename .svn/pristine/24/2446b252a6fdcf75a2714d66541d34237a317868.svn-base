<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="COMPANY_FORM"  >
<div class="syj-title1 ">
	<span>企业基本信息</span>
</div>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th ><font class="syj-color2">*</font>名称：</th>
			<td colspan="3">
			    <input type="hidden" id= "SOLELY_ID" value="${busRecord.SOLELY_ID}"/>
				<input type="text" class="syj-input1 w878 validate[required],custom[verifyCompanyNameExist]"
					name="COMPANY_NAME" id="COMPANY_NAME" maxlength="32" placeholder="请输入名称"  value="${busRecord.COMPANY_NAME}"/>
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>备选名称1：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 w878 validate[required]"
					name="SPARE_NAME1" maxlength="32"  placeholder="请输入备选名称1"  value="${busRecord.SPARE_NAME1}"/>
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>备选名称2：</th>
			<td colspan="3">
				<input type="text" class="syj-input1 w878 validate[required]"
					name="SPARE_NAME2" maxlength="32"  placeholder="请输入备选名称2"   value="${busRecord.SPARE_NAME2}"/>
			</td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>名称预先核准文号/注册号/统一社会信用代码：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]" maxlength="32"
				name="CREDIT_CODE"   placeholder="请输入名称预先核准文号/注册号/统一社会信用代码" value="${busRecord.CREDIT_CODE}"/></td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>企业住所：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
				name="COMPANY_ADDR" maxlength="64" placeholder="请输入企业住所" value="${busRecord.COMPANY_ADDR}"/></td>
		</tr>
		<tr>
			<th ><font class="syj-color2">*</font>生产经营地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
				name="BUSINESS_ADDR" maxlength="64" placeholder="请输入生产经营地址"  value="${busRecord.BUSINESS_ADDR}"/></td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="syj-table2 " >
		<tr>
			<th ><font class="syj-color2">*</font>联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required,custom[fixPhoneWithAreaCode]]"
				name="PHONE" maxlength="16" placeholder="请输入联系电话"  value="${busRecord.PHONE}"/></td>
			
			<th ><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 validate[required,custom[chinaZip]]"
				name="POST_CODE" maxlength="6"  placeholder="请输入邮政编码" value="${busRecord.POST_CODE}"/></td>
		</tr>
	</table>
</div>
<div class="syj-title1 ">
	<span>设立</span>
</div>
<div style="position:relative;">
	<table cellpadding="0" cellspacing="0" class="syj-table2 ">
		<tr>
			<th><font class="syj-color2">*</font>出资额（万元）：</th>
			<td>
				<input type="text" class="syj-input1 validate[required,custom[JustNumber]]"
					name="INVESTMENT" maxlength="16"  placeholder="请输入出资额" onblur="onlyNumber(this);" onkeyup="onlyNumber(this);"
					value="${busRecord.INVESTMENT}"/>
			</td>
			<th><font class="syj-color2">*</font>从业人数：</th>
			<td>
				<input type="text" class="syj-input1 validate[required,custom[numberplus]]"
					name="EMPLOYE_NUM" maxlength="4"  placeholder="请输入从业人数" value="${busRecord.EMPLOYE_NUM}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>出资方式：</th>
			<td >
				<input type="radio" name="INVEST_TYPE" value="1" <c:if test="${busRecord.INVEST_TYPE==1}"> checked="checked"</c:if> class="validate[required]" >以个人财产出资<br/>
				<input type="radio" name="INVEST_TYPE" value="2" <c:if test="${busRecord.INVEST_TYPE==2}"> checked="checked"</c:if> class="validate[required]" >以家庭共有财产作为个人出资
			</td>
			<th><font class="syj-color2">*</font>申领电子营业执照：</th>
			<td>
				<input type="radio" name="LICENSE_APPLY" value="1" <c:if test="${busRecord.LICENSE_APPLY==1}"> checked="checked"</c:if> class="validate[required]">是
				<input type="radio" name="LICENSE_APPLY" value="0" <c:if test="${busRecord.LICENSE_APPLY==0}"> checked="checked"</c:if> class="validate[required]">否
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>申领纸质营业执照：</th>
			<td>
				<input type="radio" name="LICENSE_PAPER_APPLY" value="1" <c:if test="${busRecord.LICENSE_PAPER_APPLY==1}"> checked="checked"</c:if> class="validate[required]">是
				<input type="radio" name="LICENSE_PAPER_APPLY" value="0" <c:if test="${busRecord.LICENSE_PAPER_APPLY==0}"> checked="checked"</c:if> class="validate[required]">否
			</td>
			<th><font class="syj-color2">*</font>申领纸质营业执照数量：</th>
			<td>
				<input type="text" class="syj-input1 validate[required,custom[numberplus]]"
					name="LICENSE_PAPER_NUM" maxlength="4"  placeholder="请输入申领纸质营业执照数量" value="${busRecord.LICENSE_PAPER_NUM}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>出资人的家庭成员：</th>
			<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]"
				name="INVEST_FAMILY" maxlength="64" disabled="disabled" placeholder="请输入出资人的家庭成员"  value="${busRecord.INVEST_FAMILY}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>经营范围：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
					class="eve-textarea w878 validate[required],maxSize[2000]" readonly="readonly"
					style="height:100px;"  placeholder="请选择经营范围">${busRecord.BUSSINESS_SCOPE}</textarea> 					
				<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID}">
				<a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a>
				<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
				<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
				中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
				可以参照政策文件、行业习惯或者专业文献等提出申请</div>
			</td>
		</tr>		
		<!--<tr>
			<th> 经营范围备注描述：</th>
			<td colspan="3"><input type="text" class="syj-input1" style="width:740px;"
				name="SCOPE_REMARK" maxlength="128" placeholder="请输入经营范围备注描述" value="${busRecord.SCOPE_REMARK}"/></td>
		</tr>-->
	</table>
</div>
</form>
