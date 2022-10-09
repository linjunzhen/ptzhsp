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
EFLOW_VARS_JSON = StringEscapeUtils.unescapeHtml3(EFLOW_VARS_JSON);
if(EFLOW_VARS_JSON != null){
	Map EFLOW_VARS = JSON.parseObject(EFLOW_VARS_JSON,Map.class);
	request.setAttribute("EFLOW_VARS", EFLOW_VARS);
}
%>
<script type="text/javascript">
function qsChange(targetName1,targetName2, value){
	$("select[name='"+targetName1+"QS']").val(value);
	FdaAppUtil.reloadSelect($("select[name='"+targetName1+"XS']"),{
		  dataParams:"{TYPE_CODE:'"+value+"',ORDER_TYPE:'ASC'}"
	  },function(){
	  });
	$("select[name='"+targetName2+"QS']").val(value);
	FdaAppUtil.reloadSelect($("select[name='"+targetName2+"XS']"),{
		  dataParams:"{TYPE_CODE:'"+value+"',ORDER_TYPE:'ASC'}"
	  },function(){
	});
}
function xsChange(targetName1,targetName2, value){
	$("select[name='"+targetName1+"XS']").val(value);
	FdaAppUtil.reloadSelect($("select[name='"+targetName1+"XZ']"),{
		  dataParams:"{TYPE_CODE:'"+value+"',ORDER_TYPE:'ASC'}"
	  },function(){
	  });
	$("select[name='"+targetName2+"XS']").val(value);
	FdaAppUtil.reloadSelect($("select[name='"+targetName2+"XZ']"),{
		  dataParams:"{TYPE_CODE:'"+value+"',ORDER_TYPE:'ASC'}"
	  },function(){
	});
}
var zsqs = "";
var jycsqs = "";
var zsxs = "";
var jycsxs = "";
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
		  //qsChange("JYCS","CCCS",typeCode);ZSXXDZ
		  var text = $(this).children("option:selected").text();
		  var zsxxdz = $("input[name='ZSXXDZ']").val();
		  if(zsqs != ""){
			  zsxxdz = zsxxdz.replace(zsqs,text);
			  $("input[name='ZSXXDZ']").val(zsxxdz);
			  zsqs = text;
		  }else{
			  if(zsxxdz.indexOf(text) <0){
				  zsxxdz = text + zsxxdz;
				  $("input[name='ZSXXDZ']").val(zsxxdz);
				  zsqs = text;
			  }
		  }
	  });
	  $("select[name='ZSXS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  //xsChange("JYCS","CCCS",typeCode);
		  FdaAppUtil.reloadSelect($("select[name='ZSXZ']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  },function(){
			  var s = $("select[name='ZSXZ'] option").size();
			  if(s==1){
				  //$("select[name='ZSXZ']").removeClass(" validate[required]");
			  }else if(s>1){
				  /* if(!($("select[name='ZSXZ']").hasClass(" validate[required]"))){
					  $("select[name='ZSXZ']").addClass(" validate[required]");
				  } */
			  }
		  });
		  var text = $(this).children("option:selected").text();
		  var zsxxdz = $("input[name='ZSXXDZ']").val();
		  if(zsxs != ""){
			  zsxxdz = zsxxdz.replace(zsxs,text);
			  $("input[name='ZSXXDZ']").val(zsxxdz);
			  zsxs = text;
		  }else{
			  if(zsxxdz.indexOf(text) <0){
				  zsxxdz = zsxxdz+text;
				  $("input[name='ZSXXDZ']").val(zsxxdz);
				  zsxs = text;
			  }
		  }
	  });
	  /**经营场所**/
	  $("select[name='JYCSQS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  $("select[name$='JYCSQS']").val(typeCode);
		  FdaAppUtil.reloadSelect($("select[name$='JYCSXS']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
		  FdaAppUtil.reloadSelect($("select[name$='JYCSXZ']"),{
			  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
		  });
		  //qsChange("ZS","CCCS",typeCode);JYCSXXDZ
		  var text = $(this).children("option:selected").text();
		  var JYCSXXDZ = $("input[name='JYCSXXDZ']").val();
		  if(jycsqs != ""){
			  JYCSXXDZ = JYCSXXDZ.replace(jycsqs,text);
			  $("input[name='JYCSXXDZ']").val(JYCSXXDZ);
			  jycsqs = text;
		  }else{
			  if(JYCSXXDZ.indexOf(text) <0){
				  JYCSXXDZ = text + JYCSXXDZ;
				  $("input[name='JYCSXXDZ']").val(JYCSXXDZ);
				  jycsqs = text;
			  }
		  }
	  });
	  $("select[name='JYCSXS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='JYCSXZ']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  },function(){
			  var s = $("select[name='JYCSXZ'] option").size();
			  /* if(s==1){
				  $("select[name='JYCSXZ']").removeClass(" validate[required]");
			  }else if(s>1){
				  if(!($("select[name='JYCSXZ']").hasClass(" validate[required]"))){
					  $("select[name='JYCSXZ']").addClass(" validate[required]");
				  }
			  } */
		  });
		  //xsChange("ZS","CCCS",typeCode);
		  var text = $(this).children("option:selected").text();
		  var JYCSXXDZ = $("input[name='JYCSXXDZ']").val();
		  if(jycsxs != ""){
			  JYCSXXDZ = JYCSXXDZ.replace(jycsxs,text);
			  $("input[name='JYCSXXDZ']").val(JYCSXXDZ);
			  jycsxs = text;
		  }else{
			  if(JYCSXXDZ.indexOf(text) <0){
				  JYCSXXDZ = JYCSXXDZ+text;
				  $("input[name='JYCSXXDZ']").val(JYCSXXDZ);
				  jycsxs = text;
			  }
		  }
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
		  //qsChange("JYCS","ZS",typeCode);
	  });
	  $("select[name='CCCSXS']").change(function(){ 
		  var typeCode = $(this).children("option:selected").val();
		  FdaAppUtil.reloadSelect($("select[name='CCCSXZ']"),{
			  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
		  });
		  //xsChange("JYCS","ZS",typeCode);
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
		  var s = $("select[name='JYCSXZ'] option").size();
		  /* if(s==1){
			  $("select[name='JYCSXZ']").removeClass(" validate[required]");
		  }else if(s>1){
			  if(!($("select[name='JYCSXZ']").hasClass(" validate[required]"))){
				  $("select[name='JYCSXZ']").addClass(" validate[required]");
			  }
		  } */
		  $("select[name='JYCSXZ']").val($("select[name='ZSXZ']").val());
	  });
	$("input[name='JYCSXXDZ']").val($("input[name='ZSXXDZ']").val());
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
	$("input[name='CCCSXXDZ']").val($("input[name='JYCSXXDZ']").val());
}
function addScdz(obj){
	if($("[name='JYCSXS']").val() == ''){
		art.dialog({
            content: '请先完善当前地址信息！',
            icon:"warning",
            ok: true
        });
		return;
	}
	$.post("foodProductionController/refreshScdzDiv.do",{
		name:"JYCS"
	}, function(responseText, status, xhr) {
		$(obj).parent().append(responseText);
		$("[name$='JYCSQS']:last").val($("[name='JYCSQS']").val());
		FdaAppUtil.reloadSelect($("select[name$='JYCSXS']:last"),{
			  dataParams:"{TYPE_CODE:'"+$("[name='JYCSQS']").val()+"',ORDER_TYPE:'ASC'}"
		  },function(){
			  $("select[name$='JYCSXS']:last").val($("[name='JYCSXS']").val());
			  FdaAppUtil.reloadSelect($("select[name$='JYCSXZ']:last"),{
				  dataParams:"{TYPE_CODE:'"+$("[name='JYCSXS']").val()+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name$='JYCSXZ']:last").val($("[name='JYCSXZ']").val());
			  });
		  });
		  
	});
}

function getScdzJson(){
	var scdzJson = [];
	for(var i=0;i<$("[name$='JYCSQS']").length;i++){
		var scdz = {};
		var JYCSQS = $("[name$='JYCSQS']").eq(i).val();
		var JYCSXS = $("[name$='JYCSXS']").eq(i).val();
		var JYCSXZ = $("[name$='JYCSXZ']").eq(i).val();
		var JYXXDZ = $("[name$='JYCSXXDZ']").eq(i).val();
		if(JYCSQS !=''){
			scdz.JYCSQS =JYCSQS ;
			scdz.JYCSXS =JYCSXS ;
			scdz.JYCSXZ =JYCSXZ ;
			scdz.JYXXDZ =JYXXDZ ;
			scdzJson.push(scdz);
		}
	}
	return JSON.stringify(scdzJson);
}
function addCkdz(obj){
	if($("[name='CCCSXS']").val() == ''){
		art.dialog({
            content: '请先完善当前地址信息！',
            icon:"warning",
            ok: true
        });
		return;
	}
	$.post("foodProductionController/refreshScdzDiv.do",{
		name:"CCCS"
	}, function(responseText, status, xhr) {
		$(obj).parent().append(responseText);
		$("[name$='CCCSQS']:last").val($("[name='CCCSQS']").val());
		FdaAppUtil.reloadSelect($("select[name$='CCCSXS']:last"),{
			  dataParams:"{TYPE_CODE:'"+$("[name='CCCSQS']").val()+"',ORDER_TYPE:'ASC'}"
		  },function(){
			  $("select[name$='CCCSXS']:last").val($("[name='CCCSXS']").val());
			  FdaAppUtil.reloadSelect($("select[name$='CCCSXZ']:last"),{
				  dataParams:"{TYPE_CODE:'"+$("[name='CCCSXS']").val()+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  $("select[name$='CCCSXZ']:last").val($("[name='CCCSXZ']").val());
			  });
		  });
		  
	});
}

function getCkdzJson(){
	var ckdzJson = [];
	for(var i=0;i<$("[name$='CCCSQS']").length;i++){
		var ckdz = {};
		var CCCSQS = $("[name$='CCCSQS']").eq(i).val();
		var CCCSXS = $("[name$='CCCSXS']").eq(i).val();
		var CCCSXZ = $("[name$='CCCSXZ']").eq(i).val();
		var CCXXDZ = $("[name$='CCCSXXDZ']").eq(i).val();
		if(CCCSQS != '' && CCXXDZ !=''){
			ckdz.CCCSQS =CCCSQS ;
			ckdz.CCCSXS =CCCSXS ;
			ckdz.CCCSXZ =CCCSXZ ;
			ckdz.CCXXDZ =CCXXDZ ;
			ckdzJson.push(ckdz);
		}
	}
	return JSON.stringify(ckdzJson);
}

function xxdzOnfocus(obj){
	var oTop = $("#txsm").position().top-40;
	var oLeft = $("#txsm").position().left;
	var infoHtml = "<div id='xxdzInfo' class='form-validation-field-0formError formError' "
		+"style='opacity: 0.80;position: absolute;top: "+oTop+"px;left: "+oLeft+"px;margin-top: -38px;'>"
		+"<div class='formErrorContent'>* 所有的详细地址为证照上要打印<br>的地址，请填写完整地址！选择项<br>的市区县不显示在证照上。</div>"
		+"<div class='formErrorArrow'><div class='line10'><!-- --></div><div class='line9'>"
		+"<!-- --></div><div class='line8'><!-- --></div><div class='line7'><!-- -->"
		+"</div><div class='line6'><!-- --></div><div class='line5'><!-- -->"
		+"</div><div class='line4'><!-- --></div><div class='line3'><!-- -->"
		+"</div><div class='line2'><!-- --></div><div class='line1'><!-- --></div></div>"
	+"</div>";
	$(obj).parent().append(infoHtml);
}
function xxdzOnblur(){
	$("#xxdzInfo").remove();
}
</script>
<tr>
			<th><font color="#ff0101">*</font>住所</th>
			<td colspan="3">
					<fda:fda-exselect name="ZSQS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]" 
					    	 queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}" defaultemptytext="请选择区市" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSQS}"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="ZSXS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]"
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.ZSQS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择县市" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSXS }"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="ZSXZ" style="width:150px;" datainterface="fdaDictionaryService.findList" clazz="input-dropdown validate[]"
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.ZSXS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择乡镇/街道" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSXZ}"
							  ></fda:fda-exselect>
					<label >详细地址:</label><input type="text" style="width: 25%;" placeholder="请输入详细地址"
						maxlength="200" class="syj-input1 validate[required]" name="ZSXXDZ" value="${EFLOW_VARS.EFLOW_BUSRECORD.ZSXXDZ}" onblur="xxdzOnblur();" onfocus="xxdzOnfocus(this);"/>
						<a href="javascript:void(0);" onclick="txsm(this);" 
			title="所有的详细地址为证照上要打印<br>的地址，请填写完整地址！选择项<br>的市区县不显示在证照上。" id="txsm"
			style="width: 20%;color: #0C83D3;">填写说明</a>	
			</td>
		</tr>
		<tr>
			<th><font color="#ff0101">*</font>生产地址</th>
			<td colspan="3">
					<fda:fda-exselect name="JYCSQS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]" 
					    	 queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}" defaultemptytext="请选择区市" value="${EFLOW_VARS.EFLOW_BUSRECORD.scdzList[0].JYCSQS }"
							  ></fda:fda-exselect>
					<fda:fda-exselect name="JYCSXS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[required]" 
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.scdzList[0].JYCSQS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择县市" value="${EFLOW_VARS.EFLOW_BUSRECORD.scdzList[0].JYCSXS }"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="JYCSXZ" style="width:150px;" datainterface="fdaDictionaryService.findList" clazz="input-dropdown validate[]"
					queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.scdzList[0].JYCSXS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择乡镇/街道" value="${EFLOW_VARS.EFLOW_BUSRECORD.scdzList[0].JYCSXZ }"
					    	  ></fda:fda-exselect>
					<label >详细地址:</label><input type="text" style="width: 25%;" placeholder="请输入详细地址"
						maxlength="200" class="syj-input1 validate[required]" name="JYCSXXDZ" value="${EFLOW_VARS.EFLOW_BUSRECORD.scdzList[0].JYXXDZ }"/>
						<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
						<input type="button" onclick="copyZS();" class="extract-button" value="同上">
						<a href="javascript:void(0);" onclick="addScdz(this);"
						style="width: 20%;color: #0C83D3;">新增</a>	
						</c:if>
					
					<c:forEach var="scdz" items="${EFLOW_VARS.EFLOW_BUSRECORD.scdzList }" begin="1" varStatus="s">
						<div style="">
<fda:fda-exselect name="${scdz.DZXX_ID}JYCSQS" style="width:100px;"
	datainterface="fdaDicTypeService.findList" 
	clazz="input-dropdown validate[required]" value="${scdz.JYCSQS }"
	queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}"
	defaultemptytext="请选择区市"></fda:fda-exselect>
<fda:fda-exselect name="${scdz.DZXX_ID}JYCSXS" style="width:100px;"
	datainterface="fdaDicTypeService.findList" value="${scdz.JYCSXS }"
	clazz="input-dropdown validate[required]"
	queryparamjson="{TYPE_CODE:'${scdz.JYCSQS}',ORDER_TYPE:'ASC'}"
	defaultemptytext="请选择县市"></fda:fda-exselect>
<fda:fda-exselect name="${scdz.DZXX_ID}JYCSXZ" style="width:150px;"
	datainterface="fdaDictionaryService.findList" value="${scdz.JYCSXZ }"
	clazz="input-dropdown validate[]"
	queryparamjson="{TYPE_CODE:'${scdz.JYCSXS}',ORDER_TYPE:'ASC'}"
	defaultemptytext="请选择乡镇/街道"></fda:fda-exselect>
<label>详细地址:</label><input type="text" style="width: 25%;" placeholder="请输入详细地址" value="${scdz.JYXXDZ }"
	maxlength="200" class="syj-input1 validate[required]" name="${scdz.DZXX_ID}JYCSXXDZ" />
	<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
	<a href="javascript:void(0);"
		 name="${scdz.DZXX_ID }delScdz" class="syj-closebtn" style="position: relative;top:11px"></a>
</c:if>
</div>					
<script type="text/javascript">
	
	$(function(){
		$("[name='${scdz.DZXX_ID }delScdz']").click(function(){
			$(this).parent().remove();
		});
		  $("select[name='${scdz.DZXX_ID}JYCSQS']").change(function(){ 
			  var typeCode = $(this).children("option:selected").val();
			  FdaAppUtil.reloadSelect($("select[name='${scdz.DZXX_ID}JYCSXS']"),{
				  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
			  });
			  FdaAppUtil.reloadSelect($("select[name='${scdz.DZXX_ID}JYCSXZ']"),{
				  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
			  });
		  });
		  $("select[name='${scdz.DZXX_ID}JYCSXS']").change(function(){ 
			  var typeCode = $(this).children("option:selected").val();
			  FdaAppUtil.reloadSelect($("select[name='${scdz.DZXX_ID}JYCSXZ']"),{
				  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
			  },function(){
				  var s = $("select[name='${scdz.DZXX_ID}JYCSXZ'] option").size();
				 /*  if(s==1){
					  $("select[name='${scdz.DZXX_ID}JYCSXZ']").removeClass(" validate[required]");
				  }else if(s>1){
					  if(!($("select[name='${scdz.DZXX_ID}JYCSXZ']").hasClass(" validate[required]"))){
						  $("select[name='${scdz.DZXX_ID}JYCSXZ']").addClass(" validate[required]");
					  }
				  } */
			  });
			  
		  });
	});
</script>
					</c:forEach>
			</td>
		</tr>
		<tr>
			<th>外设仓库地址<br /><a href="javascript:void(0);" onclick="txsm(this);"
			title="外设仓库指申请人设在生产场所外，与生产场所位于同一县（市、区），<br />用于贮存已经进货查验或者即将投料生产的原辅材料、未经出厂检验或者经检查合格待售、待周转成品的场所。"
			style="width: 20%;color: #0C83D3;">填写说明</a>	</th>
			<td colspan="3">
					<fda:fda-exselect name="CCCSQS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[]" 
					    	 queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}" defaultemptytext="请选择区市" value="${EFLOW_VARS.EFLOW_BUSRECORD.ckdzList[0].JYCSQS }"
							  ></fda:fda-exselect>
					<fda:fda-exselect name="CCCSXS" style="width:100px;" datainterface="fdaDicTypeService.findList" clazz="input-dropdown validate[]" 
					    	 queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.ckdzList[0].JYCSQS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择县市" value="${EFLOW_VARS.EFLOW_BUSRECORD.ckdzList[0].JYCSXS }"
							 ></fda:fda-exselect>
					<fda:fda-exselect name="CCCSXZ" style="width:150px;" datainterface="fdaDictionaryService.findList" clazz="input-dropdown validate[]"
					queryparamjson="{TYPE_CODE:'${EFLOW_VARS.EFLOW_BUSRECORD.ckdzList[0].JYCSXS}',ORDER_TYPE:'ASC'}" defaultemptytext="请选择乡镇/街道" value="${EFLOW_VARS.EFLOW_BUSRECORD.ckdzList[0].JYCSXZ }"
					    	  ></fda:fda-exselect>
					<label >详细地址:</label><input type="text" style="width: 25%;" placeholder="请输入详细地址"
						maxlength="200" class="syj-input1" name="CCCSXXDZ" value="${EFLOW_VARS.EFLOW_BUSRECORD.ckdzList[0].JYXXDZ }"/>
						<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
						<input type="button" onclick="copyYYCS();" class="extract-button" value="同上">
						<a href="javascript:void(0);" onclick="addCkdz(this);"
						style="width: 20%;color: #0C83D3;">新增</a>
						</c:if>
					
					<c:forEach var="ckdz" items="${EFLOW_VARS.EFLOW_BUSRECORD.ckdzList }" begin="1" varStatus="s">
						<div style="">
							<fda:fda-exselect name="${ckdz.DZXX_ID}CCCSQS" style="width:100px;"
								datainterface="fdaDicTypeService.findList" 
								clazz="input-dropdown" value="${ckdz.JYCSQS }"
								queryparamjson="{TYPE_CODE:'350000',ORDER_TYPE:'ASC'}"
								defaultemptytext="请选择区市"></fda:fda-exselect>
							<fda:fda-exselect name="${ckdz.DZXX_ID}CCCSXS" style="width:100px;"
								datainterface="fdaDicTypeService.findList" value="${ckdz.JYCSXS }"
								clazz="input-dropdown"
								queryparamjson="{TYPE_CODE:'${ckdz.JYCSQS}',ORDER_TYPE:'ASC'}"
								defaultemptytext="请选择县市"></fda:fda-exselect>
							<fda:fda-exselect name="${ckdz.DZXX_ID}CCCSXZ" style="width:150px;"
								datainterface="fdaDictionaryService.findList" value="${ckdz.JYCSXZ }"
								clazz="input-dropdown validate[]"
								queryparamjson="{TYPE_CODE:'${ckdz.JYCSXS}',ORDER_TYPE:'ASC'}"
								defaultemptytext="请选择乡镇/街道"></fda:fda-exselect>
							<label>详细地址:</label><input type="text" style="width: 25%;" placeholder="请输入详细地址" value="${ckdz.JYXXDZ }"
							maxlength="200" class="syj-input1" name="${ckdz.DZXX_ID}CCCSXXDZ" />
							<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">
							<a href="javascript:void(0);"
								 name="${ckdz.DZXX_ID }delckdz" class="syj-closebtn" style="position: relative;top:11px"></a>
							</c:if>
						</div>					
						<script type="text/javascript">
							
							$(function(){
								$("[name='${ckdz.DZXX_ID }delckdz']").click(function(){
									$(this).parent().remove();
								});
								  $("select[name='${ckdz.DZXX_ID}CCCSQS']").change(function(){ 
									  var typeCode = $(this).children("option:selected").val();
									  FdaAppUtil.reloadSelect($("select[name='${ckdz.DZXX_ID}CCCSXS']"),{
										  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
									  });
									  FdaAppUtil.reloadSelect($("select[name='${ckdz.DZXX_ID}CCCSXZ']"),{
										  dataParams:"{TYPE_CODE:'',ORDER_TYPE:'ASC'}"
									  });
								  });
								  $("select[name='${ckdz.DZXX_ID}CCCSXS']").change(function(){ 
									  var typeCode = $(this).children("option:selected").val();
									  FdaAppUtil.reloadSelect($("select[name='${ckdz.DZXX_ID}CCCSXZ']"),{
										  dataParams:"{TYPE_CODE:'"+typeCode+"',ORDER_TYPE:'ASC'}"
									  },function(){
										  var s = $("select[name='${ckdz.DZXX_ID}CCCSXZ'] option").size();
										 /*  if(s==1){
											  $("select[name='${ckdz.DZXX_ID}CCCSXZ']").removeClass(" validate[required]");
										  }else if(s>1){
											  if(!($("select[name='${ckdz.DZXX_ID}CCCSXZ']").hasClass(" validate[required]"))){
												  $("select[name='${ckdz.DZXX_ID}CCCSXZ']").addClass(" validate[required]");
											  }
										  } */
									  });
									  
								  });
							});
						</script>
					</c:forEach>
			</td>
		</tr> 
