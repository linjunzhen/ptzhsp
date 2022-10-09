<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
/**
 * 添加技术人员
 */
function addJsryTechnicalPersonnelDiv(){
	$.post("foodProductionController/refreshTechnicalPersonnelDiv.do",{
	}, function(responseText, status, xhr) {
		$("#JsryTechnicalPersonnelDiv").append(responseText);
	});
}
/**
 * 删除技术人员
 */
function delJsryTechnicalPersonnelDiv(o){
	$(o).closest("div").remove();
	
} 


/**
 * 获取技术人员
 */
function getTechnicalPersonnelJson(){
	var TechnicalPersonnelArray = [];
	$("#JsryTechnicalPersonnelDiv table").each(function(i){
		var JSRY_XM = $(this).find("[name='JSRY_XM']").val();
		var JSRY_XB = $(this).find("[name$='JSRY_XB']:checked").val();
		var JSRY_SFZHM = $(this).find("[name='JSRY_SFZHM']").val();
		var JSRY_NL = $(this).find("[name='JSRY_NL']").val();
		var JSRY_ZW = $(this).find("[name='JSRY_ZW']").val();
		var JSRY_ZC = $(this).find("[name='JSRY_ZC']").val();
		var JSRY_WHCDZY = $(this).find("[name='JSRY_WHCDZY']").val();
		var JSRY_PXKHQK = $(this).find("[name='JSRY_PXKHQK']").val();
		
		var TechnicalPersonnel = {};
		TechnicalPersonnel.JSRY_XM = JSRY_XM;
		TechnicalPersonnel.JSRY_XB = JSRY_XB;
		TechnicalPersonnel.JSRY_SFZHM = JSRY_SFZHM;
		TechnicalPersonnel.JSRY_NL = JSRY_NL;
		TechnicalPersonnel.JSRY_ZW = JSRY_ZW;
		TechnicalPersonnel.JSRY_ZC = JSRY_ZC;
		TechnicalPersonnel.JSRY_WHCDZY = JSRY_WHCDZY;
		TechnicalPersonnel.JSRY_PXKHQK = JSRY_PXKHQK;
		if(JSRY_XM != undefined && JSRY_XM!=''){
			TechnicalPersonnelArray.push(TechnicalPersonnel);
		}
	});
	if(TechnicalPersonnelArray.length>0){
		return JSON.stringify(TechnicalPersonnelArray);
	}else{
		return "";
	}
}


$(function(){
	//addJsryTechnicalPersonnelDiv();
});

</script>

<form action="" id="PERSONNEL_FORM"  >
	 <div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" 
		onclick="addJsryTechnicalPersonnelDiv();"  class="syj-addbtn" 
		>添加</a>
	   <span>食品安全管理及专业技术人员</span></div>
	<div id="JsryTechnicalPersonnelDiv"
	<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true' ||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
	 style="width: 95%"
	 </c:if>
	>
		<c:if test="${empty jsryList}">
		<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr>
					<th>姓 名</th>
					<td><input type="text" maxlength="20"
						value="" placeholder="请输入姓名"
						class="syj-input1 validate[]" name="JSRY_XM" /></td>
					<th>性 别</th>
					<td><fda:fda-exradiogroup name="${currentTime}JSRY_XB"
							width="100" value=""
							datainterface="fdaDictionaryService.findList"
							queryparamjson="{TYPE_CODE:'sex',ORDER_TYPE:'ASC'}"
							allowblank="true"></fda:fda-exradiogroup></td>
				</tr>
				<tr>
					<th>身份证件号</th>
					<td><input type="text" maxlength="18"
						value="" placeholder="请输入身份证件号"
						class="syj-input1 custom[vidcard]" name="JSRY_SFZHM" /></td>
					<!-- <th>年 龄</th>
					<td><input type="text" maxlength="3"
						value="" placeholder="请输入年龄"
						class="syj-input1 validate[required],custom[onlyNumberSp],min[0],max[100]" name="JSRY_NL" /></td> -->
					<th>文化程度、专业</th>
					<td><input type="text" maxlength="128"
						value="" placeholder="请输入文化程度、专业"
						class="syj-input1" name="JSRY_WHCDZY" /></td>
				</tr>
				
				<tr>
					<th>职 务</th>
					<td><input type="text" maxlength="32"
						value="" placeholder="请输入职务"
						class="syj-input1 validate[]" name="JSRY_ZW" />
					</td>
					<th>职 称</th>
					<td><input type="text" maxlength="32"
						value="" placeholder="请输入职称"
						class="syj-input1" name="JSRY_ZC" />
					</td>
				</tr>
				<!-- <tr>
					<th>文化程度、专业</th>
					<td colspan="3"><input type="text" maxlength="128"
						value="" placeholder="请输入文化程度、专业"
						class="syj-input1 validate[required]" name="JSRY_WHCDZY" /></td>
				</tr> -->
				<tr>
					<th>食品安全能力适岗培训、考核情况</th>
					<td colspan="3">
						<textarea name="JSRY_PXKHQK" cols="80" rows="5" class="validate[maxSize[500]]"></textarea></td>
				</tr>
			</table>
		</div>
		</c:if>
		<c:forEach items="${jsryList}" var="jsryList" varStatus="s">
			<div style="position:relative;">
				<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
					<tr>
						<th>姓 名</th>
						<td><input type="text" maxlength="20" placeholder="请输入姓名"
							class="syj-input1 validate[]" name="JSRY_XM" value="${jsryList.JSRY_XM}"/></td>
						<th>性 别</th>
						<td><fda:fda-exradiogroup name="${jsryList.JSRY_ID}JSRY_XB"
								width="100" value="${jsryList.JSRY_XB}"
								datainterface="dictionaryService.findList"
								queryparamjson="{TYPE_CODE:'sex',ORDER_TYPE:'ASC'}"
								allowblank="true"></fda:fda-exradiogroup></td>
					</tr>
					<tr>
						<th>身份证件号</th>
						<td><input type="text" maxlength="18" placeholder="请输入身份证件号"
							class="syj-input1 custom[vidcard]" name="JSRY_SFZHM" value="${jsryList.JSRY_SFZHM}"/></td>
						<%-- <th>年 龄</th>
						<td><input type="text" maxlength="3" placeholder="请输入年龄"
							class="syj-input1 validate[required],custom[onlyNumberSp],min[0],max[100]" name="JSRY_NL" value="${jsryList.JSRY_NL}"/></td> --%>
							<th>文化程度、专业</th>
					<td><input type="text" maxlength="128"
						 placeholder="请输入文化程度、专业"
						class="syj-input1" name="JSRY_WHCDZY"  value="${jsryList.JSRY_WHCDZY}"/></td>
					</tr>
					
					<tr>
						<th>职 务</th>
						<td><input type="text" maxlength="32" placeholder="请输入职务"
							class="syj-input1" name="JSRY_ZW"  value="${jsryList.JSRY_ZW}"/>
						</td>
						<th>职 称</th>
						<td><input type="text" maxlength="32" placeholder="请输入职称"
							class="syj-input1" name="JSRY_ZC"  value="${jsryList.JSRY_ZC}"/>
						</td>
					</tr>
					<%-- <tr>
						<th>文化程度、专业</th>
						<td colspan="3"><input type="text" maxlength="128" placeholder="请输入文化程度、专业"
							class="syj-input1 validate[required]" name="JSRY_WHCDZY"  value="${jsryList.JSRY_WHCDZY}"/></td>
					</tr> --%>
					<tr>
						<th>食品安全能力适岗培训、考核情况</th>
						<td colspan="3">
							<textarea name="JSRY_PXKHQK" cols="80" rows="5" class="validate[maxSize[500]]">${jsryList.JSRY_PXKHQK}</textarea></td>
					</tr>
				</table>
				
				<c:if test="${s.index>0}"><a href="javascript:void(0);"
					onclick="delJsryTechnicalPersonnelDiv(this);" class="syj-closebtn" ></a></c:if>
			</div>
			
		</c:forEach>
    </div>
</form>
