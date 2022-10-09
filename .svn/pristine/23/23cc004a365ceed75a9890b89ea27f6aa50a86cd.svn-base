<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String EFLOW_VARS_JSON = request.getParameter("EFLOW_VARS_JSON");
if(EFLOW_VARS_JSON != null){
	EFLOW_VARS_JSON = StringEscapeUtils.unescapeHtml3(EFLOW_VARS_JSON);
	Map EFLOW_VARS = JSON.parseObject(EFLOW_VARS_JSON,Map.class);
	request.setAttribute("EFLOW_VARS", EFLOW_VARS);
}
%>
<script type="text/javascript">
$(function(){
	/**住所**/
	  $("select[name='ZSQS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='ZSXS']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
		  FdaAppUtil.reloadSelect($("select[name='ZSXZ']"),{
			  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
		  });
	  });
	  $("select[name='ZSXS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='ZSXZ']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
	  });
	  /**经营场所**/
	  $("select[name='JYCSQS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='JYCSXS']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
		  FdaAppUtil.reloadSelect($("select[name='JYCSXZ']"),{
			  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
		  });
	  });
	  $("select[name='JYCSXS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='JYCSXZ']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
		  
	  });
	  /**仓储场所**/
	  $("select[name='CCCSQS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='CCCSXS']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
		  FdaAppUtil.reloadSelect($("select[name='CCCSXZ']"),{
			  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
		  });
	  });
	  $("select[name='CCCSXS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='CCCSXZ']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
	  });
})

function copyZS(){
	$("select[name='JYCSQS']").val($("select[name='ZSQS']").val());
	FdaAppUtil.reloadSelect($("select[name='JYCSXS']"),{
		  dataParams:"{TYPE_CODE:'"+$("select[name='ZSQS']").val()+"',ORDER_TYPE:'ASC'}"
	  },function(){
		  $("select[name='JYCSXS']").val($("select[name='ZSXS']").val());
	  });
	FdaAppUtil.reloadSelect($("select[name='JYCSXZ']"),{
		  dataParams:"{TYPE_CODE:'"+$("select[name='ZSXS']").val()+"',ORDER_TYPE:'ASC'}"
	  },function(){
		  $("select[name='JYCSXZ']").val($("select[name='ZSXZ']").val());
	  });
	$("input[name='JYXXDZ']").val($("input[name='ZSXXDZ']").val());
	$("input[name='JYXXDZ']").focus();
}
function copyYYCS(){
	$("select[name='CCCSQS']").val($("select[name='JYCSQS']").val());
	FdaAppUtil.reloadSelect($("select[name='CCCSXS']"),{
		  dataParams:"{TYPE_CODE:'"+$("select[name='JYCSQS']").val()+"',ORDER_TYPE:'ASC'}"
	  },function(){
		  $("select[name='CCCSXS']").val($("select[name='JYCSXS']").val());
	  });
	FdaAppUtil.reloadSelect($("select[name='CCCSXZ']"),{
		  dataParams:"{TYPE_CODE:'"+$("select[name='JYCSXS']").val()+"',ORDER_TYPE:'ASC'}"
	  },function(){
		  $("select[name='CCCSXZ']").val($("select[name='JYCSXZ']").val());
	  });
	$("input[name='CCXXDZ']").val($("input[name='JYXXDZ']").val());
}

function jycsbgsm(){
	art.dialog({
 		lock : true,
 		width:"600px",
 		title:'变更说明',
 		content: '经营场所地址不允许变更。若是经营者地址门牌号变更的（实际物理位置没有变化），<br/>需提供变更后的工商营业执照等主体证明材料复印件和相关部门出具的核准证明.'
	});
}
</script>
<tr>
			<th><font color="#ff0101">*</font>住所</th>
			<td colspan="3">
					<fda:fda-exselect name="ZSQS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]" 
					    	 queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}" defaultemptytext="请选择区市" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSQS}"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="ZSXS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]"
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.ZSQS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择县市" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSXS}"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="ZSXZ" style="width:150px;" datainterface="fdaDictionaryService.findList" clazz="input-dropdown "
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.ZSXS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择乡镇/街道" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSXZ}"
							  ></fda:fda-exselect>
					<label >详细地址:</label><input type="text" style="width: 34%;" placeholder="请输入详细街道地址,请勿输入‘福建省XX市XX县(区)’" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSXXDZ}"
						maxlength="150" class="syj-input1 validate[required]" name="ZSXXDZ" />
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>经营场所
			<c:if test="${ITEM.ITEM_CODE=='001XK00103'}">
			<br/><a href="javascript:void(0);" onclick="jycsbgsm();" title="经营场所地址不允许变更。若是经营者地址门牌号变更的（实际物理位置没有变化），需提供变更后的工商营业执照等主体证明材料复印件和相关部门出具的核准证明." style="color: #0C83D3;">变更说明</a>
			</c:if>
			</th>
			<td colspan="3">
					<fda:fda-exselect name="JYCSQS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]"
					    	 queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}" defaultemptytext="请选择区市"
					    	value="${EFLOW_VARS.EFLOW_BUSRECORD.JYCSQS}"
							  ></fda:fda-exselect>
					<fda:fda-exselect name="JYCSXS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]"
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.JYCSQS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择县市" value="${EFLOW_VARS.EFLOW_BUSRECORD.JYCSXS}"
							 ></fda:fda-exselect>
					<fda:fda-exselect id="JYCSXZ" name="JYCSXZ" style="width:150px;" datainterface="fdaDictionaryService.findList" clazz="input-dropdown"
					queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.JYCSXS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择乡镇/街道" value="${EFLOW_VARS.EFLOW_BUSRECORD.JYCSXZ}"
					    	  ></fda:fda-exselect>
					
					<label >详细地址:</label><input type="text" style="width: 34%;" placeholder="请输入详细街道地址,请勿输入‘福建省XX市XX县(区)’"
						maxlength="150" class="syj-input1 validate[required]" name="JYXXDZ" value="${EFLOW_VARS.EFLOW_BUSRECORD.JYXXDZ}"/>
						<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
						<input type="button" onclick="copyZS();" id="JYCSTSAN" style="padding-left: 0px; padding-right: 0px;" class="extract-button" value="同上">
						</c:if>
			</td>
		</tr>
		<tr>
			<th>仓库地址(如有)</th>
			<td colspan="3">
					<fda:fda-exselect name="CCCSQS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown"
					    	 queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}" defaultemptytext="请选择区市" value="${EFLOW_VARS.EFLOW_BUSRECORD.CCCSQS}"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="CCCSXS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown"
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.CCCSQS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择县市" value="${EFLOW_VARS.EFLOW_BUSRECORD.CCCSXS}"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="CCCSXZ" style="width:150px;" datainterface="fdaDictionaryService.findList" clazz="input-dropdown"
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.CCCSXS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择乡镇/街道" value="${EFLOW_VARS.EFLOW_BUSRECORD.CCCSXZ}"
							  ></fda:fda-exselect>
					<label >详细地址:</label><input type="text" style="width: 34%;" placeholder="请输入详细街道地址,请勿输入‘福建省XX市XX县(区)’"
						maxlength="150" class="syj-input1" name="CCXXDZ" value="${EFLOW_VARS.EFLOW_BUSRECORD.CCXXDZ}"/>
						<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
						<input type="button" onclick="copyYYCS();" style="padding-left: 0px; padding-right: 0px;" class="extract-button" value="同上">
						</c:if>
			</td>
		</tr> 
