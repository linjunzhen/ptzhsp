<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
 $(function(){
	var flowSubmitObj = FlowUtil.getFlowObj();
	if($("#PractitionersDiv").children().length==0 && typeof flowSubmitObj.EFLOW_EXEID == "undefined" ){
		addPractitionersDiv();
	}
}); 
/**
 * 添加从业人员
 */
function addPractitionersDiv(){
	$.post("foodManagementController.do?refreshPractitionersDiv",{
	}, function(responseText, status, xhr) {
		$("#PractitionersDiv").append(responseText);
	});
}
/**
 * 删除从业人员
 */
function delPractitionersDiv(o){
	$(o).closest("div").remove();
	
} 
/**
 * 获取从业人员
 */
function getPractitionersJson(){
	var PractitionersArray = [];
	$("#PractitionersDiv table").each(function(i){
		var RYXM = $(this).find("[name='RYXM']").val();
		var RYXB = $(this).find("[name$='RYXB']:checked").val();
		var RYMZ = $(this).find("[name='RYMZ']").val();
		var RYZW = $(this).find("[name='RYZW']").val();
		var RYHJDJZZ = $(this).find("[name='RYHJDJZZ']").val();
		var RYZJLX = $(this).find("[name='RYZJLX']").val();
		var RYZJH = $(this).find("[name='RYZJH']").val();
		var RYLXDH = $(this).find("[name='RYLXDH']").val();
		var RYRMDW = $(this).find("[name='RYRMDW']").val();
		var RYNL = $(this).find("[name='RYNL']").val();
		var RYJKZBH = $(this).find("[name='RYJKZBH']").val();
		var RYGZ = $(this).find("[name='RYGZ']").val();
		var RYFZDW = $(this).find("[name='RYFZDW']").val();
		var Practitioners = {};
		Practitioners.RYXM = RYXM;
		Practitioners.RYXB = RYXB;
		Practitioners.RYMZ = RYMZ;
		Practitioners.RYZW = RYZW;
		Practitioners.RYHJDJZZ = RYHJDJZZ;
		Practitioners.RYZJLX = RYZJLX;
		Practitioners.RYZJH = RYZJH;
		Practitioners.RYLXDH = RYLXDH;
		Practitioners.RYRMDW = RYRMDW;
		Practitioners.RYNL = RYNL;
		Practitioners.RYJKZBH = RYJKZBH;
		Practitioners.RYGZ = RYGZ;
		Practitioners.RYFZDW = RYFZDW;
		PractitionersArray.push(Practitioners);
	});
	if(PractitionersArray.length>0){
		return JSON.stringify(PractitionersArray);
	}else{
		return "";
	}
}
</script>
<form action="" id="EMPLOYEE_FORM"  >
<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" 
		onclick="addPractitionersDiv();"  class="syj-addbtn">添加</a>
	   <span>从业人员</span></div>
	<div id="PractitionersDiv"
		<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
		 style="width: 95%"
		 </c:if>
	>
	<c:forEach items="${practitionersList}" var="practitioners">
		
		<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font color="#ff0101">*</font>姓 名</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYXM}"
					placeholder="请输入姓名"
					class="syj-input1 validate[required]" name="RYXM" /></td>
				<th><font color="#ff0101">*</font>性 别</th>
				<td><fda:fda-exradiogroup name="${practitioners.RYXX_ID}RYXB" width="100"
						datainterface="fdaDictionaryService.findList" value="${practitioners.RYXB}"
						queryparamjson="{TYPE_CODE:'sex',ORDER_TYPE:'ASC'}"
						allowblank="false"></fda:fda-exradiogroup></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>民 族</th>
				<td><fda:fda-exselect name="RYMZ"
						datainterface="fdaDictionaryService.findList" value="${practitioners.RYMZ}"
						queryparamjson="{TYPE_CODE:'mzdm',ORDER_TYPE:'ASC'}"
						defaultemptytext="请选择民族" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
				</td>
				<%-- <th><font color="#ff0101">*</font>年龄</th>
				<td>
					<input type="text"  value="${practitioners.RYNL}"
						placeholder="请输入年龄"
						maxlength="3" class="syj-input1 validate[required,custom[integer],min[0]]" name="RYNL" />
				</td>
			</tr>
			<tr> --%>
				<th><font color="#ff0101">*</font>户籍登记住址</th>
				<td><input type="text" maxlength="200" value="${practitioners.RYHJDJZZ}"
					placeholder="请输入户籍登记地址"
					class="syj-input1 validate[required]" name="RYHJDJZZ" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>证件类型</th>
				<td><fda:fda-exselect name="RYZJLX"
						datainterface="fdaDictionaryService.findList" value="${practitioners.RYZJLX}"
						queryparamjson="{TYPE_CODE:'zjlx',ORDER_TYPE:'ASC'}"
						defaultemptytext="请选择证件类型" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
				</td>
				<th><font color="#ff0101">*</font>证件号</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYZJH}"
					placeholder="请输入证件号"
					class="syj-input1 validate[required]" name="RYZJH" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>职 务</th>
				<td><fda:fda-exselect name="RYZW" value="${practitioners.RYZW}"
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'zwdm',ORDER_TYPE:'ASC'}"
						defaultemptytext="请选择职务" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
				</td>
				<th><font color="#ff0101">*</font>联系电话</th>
				<td><input type="text" maxlength="40" value="${practitioners.RYLXDH}"
					placeholder="请输入联系电话"
					class="syj-input1 validate[required]"
					name="RYLXDH" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>任免单位</th>
				<td><input type="text" maxlength="200" value="${practitioners.RYRMDW}"
					placeholder="请输入任免单位"
					class="syj-input1 validate[required]" name="RYRMDW" /></td>
				<th>健康证编号</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYJKZBH}"
					placeholder="请输入健康证编号"
					class="syj-input1 " name="RYJKZBH" /></td>
			</tr>
			<tr>
				<th>工   种</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYGZ}"
					placeholder="请输入工种"
					class="syj-input1 " name="RYGZ" /></td>
				<th>发证单位</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYFZDW}"
					placeholder="请输入发证单位"
					class="syj-input1 " name="RYFZDW" /></td>
			</tr>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
			<tr>
				<td colspan="4">
					<input type="button" onclick="copyFRDBXX(this);" style="float: right;" class="extract-button" value="同法定代表人（负责人）">
				</td>
			</tr>
			</c:if>
		</table>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
		    <a href="javascript:void(0);" 
				onclick="delPractitionersDiv(this);" 
				class="syj-closebtn"
			></a></c:if> 
		</div>
	</c:forEach> 
    </div>
</form>