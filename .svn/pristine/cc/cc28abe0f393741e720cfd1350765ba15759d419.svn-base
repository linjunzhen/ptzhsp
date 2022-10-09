<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="QSZ_FORM"  >
	
	<div class="syj-title1 tmargin20">
		<span>清算组备案信息</span>
	</div>
	<div style="position:relative;">		
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th ><font class="syj-color2">*</font>清算组备案日期：</th>
				<td>
					<input type="text" class="syj-input1 Wdate validate[required]" id="QSZBARQ"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
						readonly="readonly" name="QSZBARQ"  placeholder="请输入清算完结日期" value="${busRecord.QSZBARQ}"  maxlength="16"/>	
				</td>			
				<th><font class="syj-color2">*</font>清算组成立日期：</th>
				<td>
					<input type="text" class="syj-input1 Wdate validate[required]" id="QSZCLRQ"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
						readonly="readonly" name="QSZCLRQ"  placeholder="请输入清算完结日期" value="${busRecord.QSZCLRQ}"  maxlength="16"/>		
				</td>
			</tr>
		</table>
	</div>	
	
	
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addQszcyxxDiv();" class="syj-addbtn" id="addQszcyxx">添 加</a><span>清算组成员信息</span>
	</div>
	<div id="qszcyxxDiv">	
		<c:if test="${empty qszcyxxList}">
		<div style="position:relative;">		
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="QSZ_NAME"  placeholder="请输入姓名"   maxlength="16" value="${busRecord.QSZ_NAME}"/></td>
					<th> 职务：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="QSZCYZW"
						dataInterface="dictionaryService.findDatasForSelect"
						defaultEmptyText="请选择职务" name="QSZ_JOB"   value="${busRecord.QSZ_JOB}">
						</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'QSZ_IDNO');"
						defaultEmptyText="请选择证件类型" name="QSZ_IDTYPE" id="QSZ_IDTYPE"   value="SF">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required],custom[vidcard]" 
						name="QSZ_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.QSZ_IDNO}"/></td>
				</tr>			
				<tr>
					<th><font class="syj-color2">*</font>地址：</th>
					<td colspan="3"><input type="text" class="syj-input1  validate[required]"
						name="QSZ_ADDR"  placeholder="请输入地址"  value="${busRecord.QSZ_ADDR}"  maxlength="64"/></td>
				</tr>	
				<tr>
					<th><font class="syj-color2">*</font>联系方式：</th>
					<td colspan="3"><input type="text" class="syj-input1  validate[required]"
						name="QSZ_MOBILE"  placeholder="请输入联系方式"  value="${busRecord.QSZ_MOBILE}"  maxlength="16"/></td>
				</tr>
			</table>
		</div>	
		</c:if>
		<jsp:include page="./initQszcyxxDiv.jsp"></jsp:include>
	</div>
</form>
