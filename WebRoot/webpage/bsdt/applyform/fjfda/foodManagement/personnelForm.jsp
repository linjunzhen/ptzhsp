<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
/**
 * 添加技术人员

function addTechnicalPersonnelDiv(){
	$.post("foodManagementController.do?refreshTechnicalPersonnelDiv",{
	}, function(responseText, status, xhr) {
		$("#TechnicalPersonnelDiv").append(responseText);
	});
} */
/**
 * 删除技术人员

function delTechnicalPersonnelDiv(o){
	$(o).closest("div").remove();
	
}  */

/**
 * 添加从业人员

function addPractitionersDiv(){
	$.post("foodManagementController.do?refreshPractitionersDiv",{
	}, function(responseText, status, xhr) {
		$("#PractitionersDiv").append(responseText);
	});
} */
/**
 * 删除从业人员

function delPractitionersDiv(o){
	$(o).closest("div").remove();
	
}  */
/**
 * 获取技术人员

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
		var RYLX = $(this).find("[name$='RYLX']:checked").val();
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
		TechnicalPersonnel.RYLX = RYLX;
		TechnicalPersonnelArray.push(TechnicalPersonnel);
	});
	if(TechnicalPersonnelArray.length>0){
		return JSON.stringify(TechnicalPersonnelArray);
	}else{
		return "";
	}
} */
/**
 * 获取从业人员

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
} */

$(function(){
	/**法人身份证**/
	  $("select[name='FRZJLX']").change(function(){ 
		  var typeCode = $(this).val();
		  if(typeCode=="1"){
			  $("input[name='FRZJH']").attr("class","syj-input1 validate[required],custom[vidcard]");
		  }else{
			  $("input[name='FRZJH']").attr("class","syj-input1 validate[required]");
		  }
	  });
	  if($("select[name='FRZJLX']").val()=="1"){
		  $("input[name='FRZJH']").attr("class","syj-input1 validate[required],custom[vidcard]");
	  }else{
		  $("input[name='FRZJH']").attr("class","syj-input1 validate[required]");
	  }
	/**技术人员**/
	 /** $("select[name='RYZJLX']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  if(typeCode=="1"){
			  $(this).closest("tr").find("input[name='RYZJH']").addClass(",validate[custom[vidcard]]");
		  }else{
			  $(this).closest("tr").find("input[name='RYZJH']").removeClass(",validate[custom[vidcard]]");
		  }
	  });**/
	  /**法人户籍地址**/
	  $("select[name='FRSQZZZM']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='FRXZZXXJS']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
		  FdaAppUtil.reloadSelect($("select[name='FRXZJD']"),{
			  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
		  });
	  });
	  $("select[name='FRXZZXXJS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='FRXZJD']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  },function(){
			  var s = $("select[name='FRXZJD'] option").size();
			  if(s==1){
				  $("select[name='FRXZJD']").removeClass(" validate[required]");
			  }else if(s>1){
				  if(!($("select[name='FRXZJD']").hasClass(" validate[required]"))){
					  $("select[name='FRXZJD']").addClass(" validate[required]");
				  }
			  }
		  });
	  });
	  
	 var FRGDDHVal = $("#PERSONNEL_FORM input[name='FRGDDH']").val();
	 var FRYDDHVal = $("#PERSONNEL_FORM input[name='FRYDDH']").val();
	  if(FRGDDHVal!=""){
		  changeFRYDDH($("#FRGDDHID"));
	  }
	  if(FRYDDHVal!=""){
		  changeFRGDDH($("#FRYDDHID"));
	  }
});
function changeFRYDDH(o){
	if($(o).val()!=""){
			$("#FRYDDHID").attr("class","syj-input1 validate[custom[mobilePhoneLoose]]");
			$("#FRYDDHFONT").attr("style","display:none;");
	}else{
		  $("#FRYDDHID").attr("class","syj-input1 validate[required],custom[mobilePhoneLoose]");
		  $("#FRYDDHFONT").attr("style","");
	}
}
function changeFRGDDH(o){
	if($(o).val()!=""){
			$("#FRGDDHID").attr("class","syj-input1 validate[custom[fixPhoneWithAreaCode]]");
			$("#FRGDDHFONT").attr("style","display:none;");
	}else{
		  $("#FRGDDHID").attr("class","syj-input1 validate[required],custom[fixPhoneWithAreaCode]");
		  $("#FRGDDHFONT").attr("style","");
	}
}
</script>

<form action="" id="PERSONNEL_FORM"  >
	<div class="syj-title1"><span>法人信息</span></div>
	<table cellpadding="0" cellspacing="0" class="syj-table2">
		 <tr>
			<th><font color="#ff0101">*</font>姓　　名</th>
			<td>
			<input type="text" placeholder="请输入姓名"
			maxlength="60" class="syj-input1 validate[required]" name="FRXM" />
			</td>
			<th><font color="#ff0101">*</font>性　　别</th>
			<td>
				 <fda:fda-exradiogroup name="FRXB" width="100" datainterface="fdaDictionaryService.findList"
				queryparamjson="{TYPE_CODE:'sex',ORDER_TYPE:'ASC'}" allowblank="false"></fda:fda-exradiogroup>
			</td>
		</tr>
		<tr>
			<th>民　　族</th>
			<td>
				<fda:fda-exselect name="FRMZ" datainterface="fdaDictionaryService.findList" 
				queryparamjson="{TYPE_CODE:'mzdm',ORDER_TYPE:'ASC'}"
				defaultemptytext="请选择民族" value="${EFLOW_VARS.EFLOW_BUSRECORDID ==null?'01': EFLOW_VARS.FRMZ}"
				allowblank="true" clazz="input-dropdown"></fda:fda-exselect>
			</td>
			<th><font color="#ff0101">*</font>职　　务</th>
			<td>
				<fda:fda-exselect name="FRZW" datainterface="fdaDictionaryService.findList" 
				queryparamjson="{TYPE_CODE:'zwdm',ORDER_TYPE:'ASC'}"
				defaultemptytext="请选择职务" value="490A"
				allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>户籍登记住址</th>
			<td colspan="3">
			<input type="text" placeholder="请输入户籍登记住址"
						maxlength="50" class="syj-input1 validate[required]" name="FRHJDJZZ" />
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>证件类型</th>
			<td>
			<fda:fda-exselect name="FRZJLX" datainterface="fdaDictionaryService.findList" 
				queryparamjson="{TYPE_CODE:'zjlx',ORDER_TYPE:'ASC'}"
				defaultemptytext="请选择证件类型" value="1"
				allowblank="false" clazz="input-dropdown"></fda:fda-exselect>
			</td>
			<th><font color="#ff0101">*</font>证件号</th>
			<td>
			<input type="text" placeholder="请输入证件号"  style="width:180px;"
			maxlength="100" class="syj-input1 validate[required],custom[vidcard]" name="FRZJH" />
			<%-- <input style="border-color:red;background-color: red;float:right;" type="button" onclick="queryFailureRecord('FRZJH','FRXM','2','${ITEM.ITEM_NAME}');" class="extract-button" value="查询失信黑名单记录"> --%>
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101" id="FRGDDHFONT">*</font>固定电话</th>
			<td>
			<input type="text" placeholder="请输入固定电话" onblur="changeFRYDDH(this)" id="FRGDDHID"
			maxlength="40" class="syj-input1 validate[required],custom[fixPhoneWithAreaCode]" name="FRGDDH" />
			</td>
			<th><font color="#ff0101" id="FRYDDHFONT">*</font>移动电话</th>
			<td>
			<input type="text" placeholder="请输入移动电话" onblur="changeFRGDDH(this)" id="FRYDDHID"
			maxlength="11" class="syj-input1 validate[required],custom[mobilePhoneLoose]" name="FRYDDH" />
			</td>
		</tr> 
		<tr>
			<td colspan="4"><font color="#ff0101" >说明:固定电话和移动电话至少必填一项!</font></td>
		</tr>
	</table>
	<%-- <div class="syj-title1 tmargin20">
		<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
		<a href="javascript:void(0);" 
		onclick="addTechnicalPersonnelDiv();"  class="syj-addbtn" 
		>添加</a></c:if> 
	   <span>食品安全专业技术人员、食品安全管理人员</span></div>
	<div id="TechnicalPersonnelDiv"
	<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
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
				<td colspan="3"><input type="text" maxlength="200" value="${technicalPersonnel.RYHJDJZZ}"
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
					class="syj-input1 validate[required]" name="RYZJH" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>移动电话</th>
				<td><input type="text" maxlength="11" value="${technicalPersonnel.RYLXDH}"
					class="syj-input1 validate[required],custom[mobilePhoneLoose]"
					placeholder="请输入移动电话"
					name="RYLXDH" /></td>
				<th><font color="#ff0101">*</font>任免单位</th>
				<td><input type="text" maxlength="200" value="${technicalPersonnel.RYRMDW}"
					placeholder="请输入任免单位"
					class="syj-input1 validate[required]" name="RYRMDW" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>人员类型</th>
				<td colspan="3"><fda:fda-exradiogroup name="${technicalPersonnel.RYXX_ID}RYLX" width="400"
						datainterface="fdaDictionaryService.findList" value="${technicalPersonnel.RYLX}"
						queryparamjson="{TYPE_CODE:'RYLX',ORDER_TYPE:'ASC'}"
						allowblank="false"></fda:fda-exradiogroup></td>
			</tr>
		</table>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
		    <a href="javascript:void(0);" 
				onclick="delTechnicalPersonnelDiv(this);" 
				class="syj-closebtn"
			></a></c:if> 
		</div>
	</c:forEach>
	
    </div>
    <div class="syj-title1 tmargin20">
		<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
		<a href="javascript:void(0);" 
		onclick="addPractitionersDiv();"  class="syj-addbtn" 
		>添加</a></c:if> 
	   <span>从业人员</span></div>
	<div id="PractitionersDiv"
		<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
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
				<th><font color="#ff0101">*</font>年龄</th>
				<td>
					<input type="text"  value="${practitioners.RYNL}"
						placeholder="请输入年龄"
						maxlength="3" class="syj-input1 validate[required,custom[integer],min[0]]" name="RYNL" />
				</td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>户籍登记住址</th>
				<td colspan="3"><input type="text" maxlength="200" value="${practitioners.RYHJDJZZ}"
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
				<th><font color="#ff0101">*</font>移动电话</th>
				<td><input type="text" maxlength="11" value="${practitioners.RYLXDH}"
					placeholder="请输入移动电话"
					class="syj-input1 validate[required],custom[mobilePhoneLoose]"
					name="RYLXDH" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>任免单位</th>
				<td><input type="text" maxlength="200" value="${practitioners.RYRMDW}"
					placeholder="请输入任免单位"
					class="syj-input1 validate[required]" name="RYRMDW" /></td>
				<th><font color="#ff0101">*</font>健康证编号</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYJKZBH}"
					placeholder="请输入健康证编号"
					class="syj-input1 validate[required]" name="RYJKZBH" /></td>
			</tr>
			<tr>
				<th><font color="#ff0101">*</font>工   种</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYGZ}"
					placeholder="请输入工种"
					class="syj-input1 validate[required]" name="RYGZ" /></td>
				<th><font color="#ff0101">*</font>发证单位</th>
				<td><input type="text" maxlength="100" value="${practitioners.RYFZDW}"
					placeholder="请输入发证单位"
					class="syj-input1 validate[required]" name="RYFZDW" /></td>
			</tr>
		</table>
			<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
		    <a href="javascript:void(0);" 
				onclick="delPractitionersDiv(this);" 
				class="syj-closebtn"
			></a></c:if> 
		</div>
	</c:forEach> 
    </div>--%>
</form>
