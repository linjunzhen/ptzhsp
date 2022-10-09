<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
$(function(){
	var flowSubmitObj = FlowUtil.getFlowObj();
	if($("#TechnicalPersonnelDiv").children().length==0 && typeof flowSubmitObj.EFLOW_EXEID == "undefined"){
		addTechnicalPersonnelDiv();
	}
});
/**
 * 添加技术人员
 */
function addTechnicalPersonnelDiv(){
	var jyzmc = $("input[name='JYZMC']").val();
	$.post("foodManagementController.do?refreshTechnicalPersonnelDiv",{
		jyzmc:jyzmc
	}, function(responseText, status, xhr) {
		$("#TechnicalPersonnelDiv").append(responseText);
	});
}
/**
 * 删除技术人员
 */
function delTechnicalPersonnelDiv(o){
	$(o).closest("div").remove();
	
} 

/**
 * 获取技术人员
 */
function getTechnicalPersonnelJson(){
	var TechnicalPersonnelArray = [];
	$("#TechnicalPersonnelDiv table").each(function(i){
		var RYXM = $(this).find("[name='RYXM']").val();
		var RYXB = $(this).find("[name$='RYXB']:checked").val();
		var RYMZ = $(this).find("[name='RYMZ']").val();
		var RYZW = $(this).find("[name='RYZW']").val();
		var RYHJDJZZ = $(this).find("[name='RYHJDJZZ']").val();
		var RYZJLX = $(this).find("[name='RYZJLX']").val();
		var RYZJH = $(this).find("[name='RYZJH']").val();
		var RYLXDH = $(this).find("[name='RYLXDH']").val();
		var RYRMDW = $(this).find("[name='RYRMDW']").val();
		//var RYLX = $(this).find("[name$='RYLX']:checked").val();
		var TechnicalPersonnel = {};
		TechnicalPersonnel.RYXM = RYXM;
		TechnicalPersonnel.RYXB = RYXB;
		TechnicalPersonnel.RYMZ = RYMZ;
		TechnicalPersonnel.RYZW = RYZW;
		TechnicalPersonnel.RYHJDJZZ = RYHJDJZZ;
		TechnicalPersonnel.RYZJLX = RYZJLX;
		TechnicalPersonnel.RYZJH = RYZJH;
		TechnicalPersonnel.RYLXDH = RYLXDH;
		TechnicalPersonnel.RYRMDW = RYRMDW;
		TechnicalPersonnel.RYLX = "3";
		TechnicalPersonnelArray.push(TechnicalPersonnel);
	});
	if(TechnicalPersonnelArray.length>0){
		return JSON.stringify(TechnicalPersonnelArray);
	}else{
		return "";
	}
}

$(function(){
	/**技术人员**/
	$("select[name='RYZJLX']").change(function(){ 
		var typeCode = $(this).val();
		  if(typeCode=="1"){
			  if(!$(this).closest("tr").find("input[name='RYZJH']").hasClass(",custom[vidcard]")){
				  $(this).closest("tr").find("input[name='RYZJH']").addClass(",custom[vidcard]");  
			  }
		  }else{
			  $(this).closest("tr").find("input[name='RYZJH']").attr("class","syj-input1 validate[required]");
		  }
	});
});

function copyFRDBXX(o){
	var thTab = $(o).closest("div");
	var FRXM = $("#PERSONNEL_FORM input[name='FRXM']").val();
	var FRXB = $("#PERSONNEL_FORM input[name='FRXB']:checked").val();
	var FRMZ = $("#PERSONNEL_FORM select[name='FRMZ']").val();
	var FRZW = $("#PERSONNEL_FORM select[name='FRZW']").val();
	var FRHJDJZZ = $("#PERSONNEL_FORM input[name='FRHJDJZZ']").val();
	var FRZJLX = $("#PERSONNEL_FORM select[name='FRZJLX']").val();
	var FRZJH = $("#PERSONNEL_FORM input[name='FRZJH']").val();
	var FRZJHClass = $("#PERSONNEL_FORM input[name='FRZJH']").attr("Class");
	var FRGDDH = $("#PERSONNEL_FORM input[name='FRGDDH']").val();
	var FRYDDH = $("#PERSONNEL_FORM input[name='FRYDDH']").val();
	thTab.find("[name='RYXM']").val(FRXM);
	thTab.find("[name$='RYXB'][value='"+FRXB+"']").attr("checked",true);
	thTab.find("[name='RYMZ']").val(FRMZ);
	thTab.find("[name='RYZW']").val(FRZW);
	thTab.find("[name='RYHJDJZZ']").val(FRHJDJZZ);
	thTab.find("[name='RYZJLX']").val(FRZJLX);
	thTab.find("[name='RYZJH']").val(FRZJH);
	thTab.find("[name='RYZJH']").attr("Class",FRZJHClass);
	thTab.find("[name='RYLXDH']").val("");
	if(FRGDDH!=""){
		thTab.find("[name='RYLXDH']").val(FRGDDH);
	}
	if(FRYDDH!=""){
		thTab.find("[name='RYLXDH']").val(FRYDDH);
	}
	var JYZMCVAL = $("input[name='JYZMC']").val();
	thTab.find("[name='RYRMDW']").val(JYZMCVAL);
}


</script>

<form action="" id="SECURITY_PERSONNEL_FORM"  >
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" 
		onclick="addTechnicalPersonnelDiv();"  class="syj-addbtn" 
		>添加</a>
	   <span>食品安全管理人员</span></div>
	<div id="TechnicalPersonnelDiv"
	<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
	 style="width: 95%"
	 </c:if>
	 >
	<c:forEach items="${technicalPersonnelList}" var="technicalPersonnel">
		
		<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th><font color="#ff0101">*</font>姓 名</th>
				<td><input type="text" maxlength="100" value="${technicalPersonnel.RYXM}"
					placeholder="请输入姓名"
					class="syj-input1 validate[required]" name="RYXM" /></td>
				<th><font color="#ff0101">*</font>性 别</th>
				<td><fda:fda-exradiogroup name="${technicalPersonnel.RYXX_ID}RYXB" width="100" value="${technicalPersonnel.RYXB}"
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'sex',ORDER_TYPE:'ASC'}"
						allowblank="false"></fda:fda-exradiogroup></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>民 族</th>
				<td><fda:fda-exselect name="RYMZ" value="${technicalPersonnel.RYMZ}"
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'mzdm',ORDER_TYPE:'ASC'}"
						defaultemptytext="请选择民族" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
				</td>
				<th><font color="#ff0101">*</font>职 务</th>
				<td><fda:fda-exselect name="RYZW" value="${technicalPersonnel.RYZW}"
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'zwdm',ORDER_TYPE:'ASC'}"
						defaultemptytext="请选择职务" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
				</td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>户籍登记住址</th>
				<td colspan="3"><input type="text" maxlength="50" value="${technicalPersonnel.RYHJDJZZ}"
					placeholder="请输入户籍登记地址"
					class="syj-input1 validate[required]" name="RYHJDJZZ" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>证件类型</th>
				<td><fda:fda-exselect name="RYZJLX" value="${technicalPersonnel.RYZJLX}"
						datainterface="fdaDictionaryService.findList"
						queryparamjson="{TYPE_CODE:'zjlx',ORDER_TYPE:'ASC'}"
						defaultemptytext="请选择证件类型" allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
				</td>
				<th><font color="#ff0101">*</font>证件号</th>
				<td><input type="text" maxlength="100" value="${technicalPersonnel.RYZJH}"
					placeholder="请输入证件号"
					class="syj-input1 validate[required]<c:if test="${technicalPersonnel.RYZJLX=='1'}">,custom[vidcard]</c:if>" name="RYZJH" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>联系电话</th>
				<td><input type="text" maxlength="40" value="${technicalPersonnel.RYLXDH}"
					class="syj-input1 validate[required]"
					placeholder="请输入联系电话"
					name="RYLXDH" /></td>
				<th><font color="#ff0101">*</font>任免单位</th>
				<td><input type="text" maxlength="200" value="${technicalPersonnel.RYRMDW}"
					placeholder="请输入任免单位"
					class="syj-input1 validate[required]" name="RYRMDW" /></td>
			</tr>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
			<tr>
				<td colspan="4">
					<input type="button" onclick="copyFRDBXX(this);" style="float: right;" class="extract-button" value="同法定代表人（负责人）">
				</td>
			</tr>
			</c:if>
			<%-- <tr>
				<th><font color="#ff0101">*</font>人员类型</th>
				<td colspan="3"><fda:fda-exradiogroup name="${technicalPersonnel.RYXX_ID}RYLX" width="400"
						datainterface="fdaDictionaryService.findList" value="${technicalPersonnel.RYLX}"
						queryparamjson="{TYPE_CODE:'RYLX',ORDER_TYPE:'ASC'}"
						allowblank="false"></fda:fda-exradiogroup></td>
			</tr> --%>
		</table>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
		    <a href="javascript:void(0);" 
				onclick="delTechnicalPersonnelDiv(this);" 
				class="syj-closebtn"
			></a></c:if> 
		</div>
	</c:forEach>	
    </div>
</form>
