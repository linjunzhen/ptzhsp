<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="BAXX_FORM"  >

	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addOldDsxxDiv('','',2);" class="syj-addbtn" id="addYDsxx" >添 加</a><span>原董事信息</span>
	</div>
	<div id="olddsxxDiv">
		<c:if test="${empty oldDirectorList}">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" value="${busRecord.DIRECTOR_NAME}" 
						name="DIRECTOR_NAME"  placeholder="请输入姓名" maxlength="32"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
							<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:10,11,12,20,21"
							dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.DIRECTOR_JOB}"
							defaultEmptyText="请选择职务" name="DIRECTOR_JOB">
							</eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>证件类型：</th>
					<td>
						<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="DocumentType"
						dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'DIRECTOR_IDNO');" 
						defaultEmptyText="请选择证件类型" name="DIRECTOR_IDTYPE" value="${busRecord.DIRECTOR_IDTYPE}">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证件号码：</th>
					<td><input type="text" class="syj-input1 validate[required]"
						name="DIRECTOR_IDNO"  placeholder="请输入证件号码"  maxlength="32" value="${busRecord.DIRECTOR_IDNO}"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>手机号码：</th>
					<td colspan="3">
						<input type="text" class="syj-input1 validate[required]"
						name="DIRECTOR_PHONE"  placeholder="请输入手机号码"  maxlength="32" value="${busRecord.DIRECTOR_PHONE}"/>
					</td>
				</tr>
			</table>
		</div>
		</c:if>
		<jsp:include page="./initOlddsxxDiv.jsp"></jsp:include>
	</div>
	<div id="xdsxx" style="display:none;">
		<div class="syj-title1 ">
			<a href="javascript:void(0);" onclick="javascript:addDsxxDiv(0);" class="syj-addbtn" id="addDsxx" >添 加</a><span>新董事信息</span>		
		</div>
		<div id="dsxxDiv">			
			<jsp:include page="./initDsxxDiv.jsp"></jsp:include>
		</div>
	</div>
	
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addOldJsxxDiv('','',2);" class="syj-addbtn" id="addYJsxx" >添 加</a><span>原监事信息</span>
	</div>
	<div id="oldjsxxDiv">
		<c:if test="${empty oldSupervisorList}">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="SUPERVISOR_NAME" placeholder="请输入姓名"  maxlength="8"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
						<eve:eveselect clazz="input-dropdown  w280 validate[required]" dataParams="ssdjzw:02"
						dataInterface="dictionaryService.findTwoDatasForSelect"
						defaultEmptyText="请选择职务" name="SUPERVISOR_JOB" value="10">
						</eve:eveselect>
					</td>
				</tr>
			</table>
		</div>
		</c:if>
		<jsp:include page="./initOldjsxxDiv.jsp"></jsp:include>
	</div>
	<div id="xjsxx" style="display:none;">
		<div class="syj-title1 ">
			<a href="javascript:void(0);" onclick="javascript:addJsxxDiv(1);" class="syj-addbtn" id="addJsxx" >添 加</a><span>新监事信息</span>
		</div>
		<div id="jsxxDiv">			
			<jsp:include page="./initJsxxDiv.jsp"></jsp:include>
		</div>
	</div>
	
	<div class="syj-title1 ">
		<a href="javascript:void(0);" onclick="javascript:addOldJlxxDiv('','',2);" class="syj-addbtn" id="addYJlxx" >添 加</a><span>原经理信息</span>
	</div>
	<div id="oldjlxxDiv">
		<c:if test="${empty oldMangerList}">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><font class="syj-color2">*</font>姓名：</th>
					<td><input type="text" class="syj-input1 validate[required]" 
						name="MANAGER_NAME"  placeholder="请输入姓名"  maxlength="8"/></td>
					<th><font class="syj-color2">*</font>职务：</th>
					<td>
						<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:03"
						dataInterface="dictionaryService.findTwoDatasForSelect" 
						defaultEmptyText="请选择职务" name="MANAGER_JOB">
						</eve:eveselect>
					</td>
				</tr>
			</table>
		</div>
		</c:if>
		<jsp:include page="./initOldjlxxDiv.jsp"></jsp:include>
	</div>
	<div id="xjlxx" style="display:none;">
		<div class="syj-title1 ">
			<a href="javascript:void(0);" onclick="javascript:addJlxxDiv(1);" class="syj-addbtn" id="addJlxx" >添 加</a><span>新经理信息</span>
		</div>
		<div id="jlxxDiv">			
		</div>
	</div>
</form>
