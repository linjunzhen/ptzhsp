<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
.pdl{
padding-left: 20px;
}
.extract-button {
    background: #0c83d3 none repeat scroll 0 0;
    border: 1px solid #0c83d3;
    border-radius: 3px;
    color: #fff;
    cursor: pointer;
    height: 26px;
    padding: 0 20px;
}
</style>
<script type="text/javascript">
$(function(){
	//var EFLOW_VARS = FlowUtil.getFlowObj();
	if($("#FoodSafetyDiv").children().length==0){
		addFoodSafetyDiv();
	}
});
/**
 * 添加
 */
function addFoodSafetyDiv(){
	var newhtml = "<div style=\"position:relative;\">";
	newhtml += "<table cellpadding=\"0\" cellspacing=\"0\" class=\"syj-table2 tmargin20\">";
	newhtml += "<tr><th><font color=\"#ff0101\">*</font>设备名称：</th><td><input type=\"text\" name=\"SBMC\" placeholder=\"请输入设备名称\" maxlength=\"100\" class=\"syj-input1 validate[required]\" /></td>";
	newhtml += "<th><font color=\"#ff0101\">*</font>数量：</th><td><input type=\"text\" name=\"SL\" placeholder=\"请输入数量\" maxlength=\"6\" class=\"syj-input1 validate[required,custom[integer],min[0]] \" /></td></tr>";
    newhtml += "<tr><th>位置：</th><td><input type=\"text\" name=\"WZ\" placeholder=\"请输入位置\" class=\"syj-input1\" maxlength=\"500\" /></td>";
    newhtml += "<th>备注：</th><td><input type=\"text\" name=\"BZ\" placeholder=\"请输入备注\" class=\"syj-input1\" maxlength=\"500\" /></td></tr>";
    newhtml += "</table><a href=\"javascript:void(0);\" onclick=\"delFoodSafetyDiv(this);\" class=\"syj-closebtn\"></a></div>";
	$("#FoodSafetyDiv").append(newhtml);
}
/**
 * 删除
 */
 function delFoodSafetyDiv(o){
	$(o).closest("div").remove();
}

function getFacilityEquipmentJson(){
	var facilityEquipmentArray = [];
	$("#FoodSafetyDiv table").each(function(i){
		var sbmc = $(this).find("[name='SBMC']").val();
		var sl = $(this).find("[name='SL']").val();
		var wz = $(this).find("[name='WZ']").val();
		var bz = $(this).find("[name='BZ']").val();
		var facilityEquipment = {};
		facilityEquipment.SBMC = sbmc;
		facilityEquipment.SL = sl;
		facilityEquipment.WZ = wz;
		facilityEquipment.BZ = bz;
		facilityEquipmentArray.push(facilityEquipment);
	});
	if(facilityEquipmentArray.length>0){
		return JSON.stringify(facilityEquipmentArray);
	}else{
		return "";
	}
}
</script>

<form action="" id="FACILITYEQUIPMENT_FORM"  >
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" 
		onclick="addFoodSafetyDiv();"  class="syj-addbtn" >添加</a>
	   <span>食品安全设施设备</span></div>
	<div id="FoodSafetyDiv"
	<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
	 style="width: 95%"
	 </c:if> 
	 >
		<c:forEach items="${facilityEquipmentList}" var="facilityEquipment">
			<div style="position:relative;">
			<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font color="#ff0101">*</font>设备名称</td>
				<td><input type="text" name="SBMC" placeholder="请输入设备名称" maxlength="100"
				 class="syj-input1 validate[required]" value="${facilityEquipment.SBMC}" /></td>
				<th><font color="#ff0101">*</font>数量</td>
				<td><input type="text" name="SL" placeholder="请输入数量" maxlength="6"
				class="syj-input1 validate[required,custom[integer],min[0]] " value="${facilityEquipment.SL}" /></td>
			</tr>
		    <tr>
		    	<th>位置</td>
		    	<td><input type="text" name="WZ" placeholder="请输入位置" maxlength="500"
		    	class="syj-input1" value="${facilityEquipment.WZ}"/></td>
		    	<th>备注</td>
		    	<td><input type="text" name="BZ" placeholder="请输入备注" maxlength="500"
		    	class="syj-input1" value="${facilityEquipment.BZ}"/></td>
		    </tr>
		    </table>
		    <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
		    <a href="javascript:void(0);" 
				onclick="delFoodSafetyDiv(this);" 
				class="syj-closebtn"
			></a></c:if> 
			
		</div>
		</c:forEach>
    </div>
</form>
