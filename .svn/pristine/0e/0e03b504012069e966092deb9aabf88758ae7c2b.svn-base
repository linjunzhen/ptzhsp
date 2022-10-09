<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
/**
 * 添加产品信息
 */
function addCpxxDiv(){
	var CPXX_SPLB = $("#cpxxDiv").find("[name$='CPXX_SPLB']").eq(0).val();
	var SPLB_NAME = $("#cpxxDiv").find("[name$='CPXX_SPLB']").eq(0).children("option:selected").text();
	if(CPXX_SPLB != ''){
		$.post("foodProductionController/refreshCpxxDiv.do",{
		
		}, function(responseText, status, xhr) {
			$("#cpxxDiv").append(responseText);
			$("#cpxxDiv").find("[name$='SPLB_NAME']").val(SPLB_NAME);
			$("#cpxxDiv").find("[name$='CPXX_SPLB']:last").val(CPXX_SPLB);
			FdaAppUtil.reloadSelect($("select[name$='CPXX_LBBH']:last"),{
				dataParams:"{PARENT_ID:'"+CPXX_SPLB+"',ORDER_TYPE:'ASC'}"
			});	
		});
	}else{
		art.dialog({
            content: "请先选择食品、食品添加剂类别！",
            icon:"warning",
            ok: true
        });
	}
}
/**
 * 删除产品信息
 */
function delCpxxDiv(o){
	var trIndex = $("#cpxxDiv tr").index($(o).closest("tr"));
	$(o).closest("tr").remove();
	if($(".sbssdiv")[trIndex-1]){
		$(".sbssdiv")[trIndex-1].remove();
	}
	if($(".jgcsdiv")[trIndex-1]){
		$(".jgcsdiv")[trIndex-1].remove();
	}
} 
function appendJgcs(pId,pName,flag){
	var jgcsDiv = "<div class='syj-title1'  style='margin-top:5px;'>"
	+"<a href='javascript:void(0);' "
	+"onclick='addProcessingPlaceDiv(\""+pId+"\",\""+pName+"\",\""+flag+"\");' class='syj-addbtn'"
	+">添加</a>"
   +"<span style='height:25px;line-height:25px'>"+pName+"（品种明细：<font data-id='"+flag+"pzmx'></font>）</span></div>"
+"<div style='position:relative;margin-top:5px;'>"
	+"<table cellpadding='0' cellspacing='0' class='syj-table2' id='"+flag+"'>"
		+"<tr>"	
			+"<th style='width:30px'>序号</th>"
			+"<th>类别名称</th>"
			+"<th>生产场点、工艺、工序名称</th>"
			+"<th>生产场点、工艺、工序所在地</th>"		
		+"</tr>"
		+"<tr>"
		+"<td style='width:30px;text-align:center;'>1</td>"
			+"<td align='center'><input type='text' maxlength='64' placeholder='请输入类别名称' style='width:92%' class='syj-input1 validate[required]' name='SPLB' value='"+pName+"' />"
			+"<input type='hidden' name='LBID' value='"+pId+"' /><input type='hidden' name='LBBH' value='"+flag+"' /></td>"
			+"<td><input type='text' maxlength='256'"
				+"value='' placeholder='请输入生产场点、工艺、工序名称'"
				+"class='syj-input1 validate[]' name='JGMC' style='width:92%'/></td>"
			+"<td><input type='text' maxlength='256'"
				+"value='' placeholder='请输入生产场点、工艺、工序所在地'"
				+"class='syj-input1 validate[]' name='JGSZD' style='width:75%'/>"
				+"</td>"
		+"</tr>"
	+"</table>"
+"</div>";
	return jgcsDiv;
}
function appendSbss(pId,pName,flag){
	var sbssdiv ="<div class='syj-title1' style='margin-top:5px;'>"
			+"<a href='javascript:void(0);' onclick='addEquipmentDiv(\""+pId+"\",\""+pName+"\",\""+flag+"\");'"
			+"class='syj-addbtn'>添加</a>"
			+"<span style='height:25px;line-height:25px'>"+pName+"（品种明细：<font data-id='"+flag+"pzmx'></font>）</span>"
			+"</div>"
			+"<div style='position:relative;margin-top:5px;'>"
			+"<table cellpadding='0' cellspacing='0' class='syj-table2' id='"+flag+"Sbss' >"
			+"<tr>"
			+"<th style='width:30px'>序号</th>"
			+"<th>类别名称</th>"
			+"<th>设备设施名称</th>"
			+"<th>规格型号</th>"
			+"<th>数量</th>"
			+"<th>完好状态</th>"
			+"</tr>"
			+"<tr>"
			+"<td style='width:30px;text-align:center;'>1</td>"
			+"<td align='center'><input type='text' name='SPLB' maxlength='64' placeholder='请输入类别名称' style='width:92%' value='"+pName+"' class='syj-input1 validate[required]'/>"
			+"<input type='hidden' name='LBID' value='"+pId+"' /><input type='hidden' name='LBMC' value='"+flag+"'/></td>"
			+"<td><input type='text' maxlength='256'"
				+"value='' placeholder='请输入设备、设施名称'"
				+"class='syj-input1 validate[]' name='SBSSMC' style='width:92%'/></td>"
			+"<td><input type='text' maxlength='256'"
				+"value='' placeholder='请输入规格型号'"
				+"class='syj-input1 validate[]' name='GGXH' style='width:92%'/></td>"
			+"<td><input type='text' maxlength='256'"
				+"value='' placeholder='请输入数量'"
				+"class='syj-input1 validate[]' name='SL' style='width:92%'/></td>"
			+"<td><input type='text' maxlength='256'"
				+"value='' placeholder='请输入完好状态'"
				+"class='syj-input1 validate[]' name='WHZT' style='width:60%'/>"
				+"</td>"
		+"</tr>"
		+"</table>"
		+"</div>";
		return sbssdiv;
}
/**
 * 获取产品信息
 */
function getProductCpxxJson(){
	var productCpxxArray = [];
	$("#cpxxDiv tr").each(function(i){
		var CPXX_SPLB = $(this).find("[name$='CPXX_SPLB']").val();
		var CPXX_LBBH = $(this).find("[name$='CPXX_LBBH']").val();
		var CPXX_PZMX = $(this).find("[name$='CPXX_PZMX']").val();
		var SPLB_NAME = $(this).find("[name$='SPLB_NAME']").val();
		var CPXX_LBMC = $(this).find("[name$='CPXX_LBMC']").val();
		var CPXX_CPZXBZWH = $(this).find("[name$='CPXX_CPZXBZWH']").val();
		var CPXX_DEPTID = $(this).find("[name$='CPXX_DEPTID']").val();		
		var CPXX_DEPTNAME = $(this).find("[name$='CPXX_DEPTNAME']").val();
		var CPXX_BZ = $(this).find("[name$='CPXX_BZ']").val();
		var CPXX_PZMXID = $(this).find("[name$='CPXX_PZMXID']").val();
		var CPXX_ISPROVINCE = $(this).find("[name$='CPXX_ISPROVINCE']").val();
		var LBBH = $(this).find("[name$='LBBH_CODE']").val();
		var CPXX_STATUS = $(this).find("[name$='CPXX_STATUS']").val();
		if(CPXX_SPLB!=null&&CPXX_SPLB!=''){
			var productCpxx = {};
			productCpxx.CPXX_SPLB = CPXX_SPLB;
			productCpxx.CPXX_LBMC = CPXX_LBMC;
			productCpxx.SPLB_NAME = SPLB_NAME;
			productCpxx.CPXX_LBBH = CPXX_LBBH;
			productCpxx.CPXX_PZMX = CPXX_PZMX;
			productCpxx.CPXX_PZMXID = CPXX_PZMXID;
			productCpxx.CPXX_DEPTID = CPXX_DEPTID;
			productCpxx.CPXX_DEPTNAME = CPXX_DEPTNAME;
			productCpxx.CPXX_CPZXBZWH = CPXX_CPZXBZWH;
			productCpxx.CPXX_BZ = CPXX_BZ;
			productCpxx.CPXX_ISPROVINCE = CPXX_ISPROVINCE;
			productCpxx.LBBH = LBBH;
			productCpxx.CPXH = LBBH +"_"+ i;
			if(CPXX_STATUS){
				productCpxx.CPXX_STATUS = CPXX_STATUS;
			}
			productCpxxArray.push(productCpxx);			
		}
	});
	if(productCpxxArray.length>0){
		return JSON.stringify(productCpxxArray);
	}else{
		return "";
	}
}
$(function(){
	$("[name$='CPXX_PZMX']").blur(function(){
		var trIndex = $("#cpxxDiv tr").index($(this).closest("tr"));
		var LBBH_CODE = $("[name$='LBBH_CODE']").eq(trIndex-1).val();
		if($("#ProcessingPlace").find("[data-id$='pzmx']").eq(trIndex-1)){
			$("#ProcessingPlace").find("[data-id$='pzmx']").eq(trIndex-1).html($(this).val());
			$("#EQUIPMENT").find("[data-id$='pzmx']").eq(trIndex-1).html($(this).val());
		}else{
			
		}
	});
	$("select[id='APPLY_ALL_SELECT']").change(function(){ 
		var allSelectVal = $(this).val();
		$("select[name$='CPXX_STATUS']").each(function(){
			$(this).val(allSelectVal);
		});
	});
	
	$("select[name='CPXX_SPLB']").change(function(){ 
		resetPzmx('');
		var JYCSXS = $("select[name='JYCSXS']").val();
		if(JYCSXS!=null&&JYCSXS!=""){			  
			var pId = $(this).children("option:selected").val();
			$("[name$=SPLB_NAME]").val($(this).children("option:selected").text());
			FdaAppUtil.reloadSelect($("select[name$='CPXX_LBBH']"),{
				dataParams:"{PARENT_ID:'"+pId+"',ORDER_TYPE:'ASC'}"
			});		  
			for(var i=1;i<$("select[name$='CPXX_SPLB']").length;i++){
				var otherSPLB = $("select[name$='CPXX_SPLB']").eq(i);
				otherSPLB.val(pId);
				otherSPLB.trigger("change");
			}
		}else{
			art.dialog({
	            content: "请先填写生产地址！",
	            icon:"warning",
	            ok: true
	        });
		   $("select[name='CPXX_SPLB']").val('');
		}
	});
	$("select[name='CPXX_LBBH']").change(function(){ 
		resetPzmx('');
		var pId = $(this).children("option:selected").val();
		var pName = $(this).children("option:selected").text();
		var JYCSQS = $("select[name='JYCSQS']").val();
		var JYCSXS = $("select[name='JYCSXS']").val();
		$("[name='CPXX_LBMC']").val(pName);
		var trIndex = $("#cpxxDiv tr").index($(this).closest("tr"));
		$.post("foodProductionController/getCpxxDept.do",{
			typeId:pId,
			JYCSQS:JYCSQS,
			JYCSXS:JYCSXS
		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			var flag = resultJson.TYPE_CODE+"_"+trIndex;
			if($(".sbssdiv")[trIndex-1]){
				$(".sbssdiv").eq(trIndex-1).html(appendSbss(pId,pName,flag));
			}else{
				$("#EQUIPMENT").append("<div class='sbssdiv'>"+appendSbss(pId,pName,flag)+"</div>");
			}
			if($(".jgcsdiv")[trIndex-1]){
				$(".jgcsdiv").eq(trIndex-1).html(appendJgcs(pId,pName,flag));
			}else{
				$("#ProcessingPlace").append("<div class='jgcsdiv'>"+appendJgcs(pId,pName,flag)+"</div>");
			}
			$("[name='CPXX_ISPROVINCE']").val(resultJson.ISPROVINCE);
			$("[name='ISPROVINCE']").val(resultJson.ISPROVINCE);
			$("[name='CPXX_DEPTID']").val(resultJson.DEPART_ID);
			$("[name='CPXX_DEPTNAME']").val(resultJson.DEPART_NAME);
			$("[name='LBBH_CODE']").val(resultJson.TYPE_CODE);
			$("[name='SLJG']").html("受理机构："+resultJson.DEPART_NAME);
		});
	});
});
function showSelectBusTypes(obj){
	var typeIds = $("input[name='"+obj+"CPXX_PZMXID']").val();
	var parentId = $("[name='"+obj+"CPXX_LBBH']").val();
	var LBBH_CODE = $("[name='"+obj+"LBBH_CODE']").val();
	var trIndex = $("#cpxxDiv tr").index($("[name='"+obj+"LBBH_CODE']").closest("tr"));
	if(parentId==null||parentId==""){	
		art.dialog({
            content: "请先选择类别！",
            icon:"warning",
            ok: true
        });
		return;
	}
	parent.$.dialog.open("fdaBusTypeController/selector.do?parentId="+parentId+"&typeIds="+typeIds+"&noAuth=true&allowCount=100&checkCascadeY=ps&"
			+"checkCascadeN=ps", {
		title : "品种明细",
		width:"600px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var selectBusTypeInfo = art.dialog.data("selectBusTypeInfo");
			if(selectBusTypeInfo){
				$("[name='"+obj+"CPXX_PZMXID']").val(selectBusTypeInfo.typeIds);
				$("[name='"+obj+"CPXX_PZMX']").val(selectBusTypeInfo.typeNames);
				$("[name='"+obj+"CPXX_PZMX']").blur();
				//$("[data-id='"+LBBH_CODE+"_"+trIndex+"pzmx']").html(selectBusTypeInfo.typeNames);
				$("#ProcessingPlace").find("[data-id$='pzmx']").eq(trIndex-1).html(selectBusTypeInfo.typeNames);
				$("#EQUIPMENT").find("[data-id$='pzmx']").eq(trIndex-1).html(selectBusTypeInfo.typeNames);
				art.dialog.removeData("selectBusTypeInfo");
			}
		}
	}, false);
}
function resetPzmx(obj){
	$("[name='"+obj+"CPXX_PZMXID']").val('');
	$("[name='"+obj+"CPXX_PZMX']").val('');
	$("[name='"+obj+"CPXX_DEPTID']").val('');
	$("[name='"+obj+"CPXX_DEPTNAME']").val('');
	$("[name='"+obj+"LBBH_CODE']").val('');
	$("[name='"+obj+"SLJG']").html('');
}
</script>

<form action="" id="PRODUCT_FORM"  >
	<div class="syj-title1 tmargin20">
		<%-- <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true' ||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}"> --%>
		<a href="javascript:void(0);" 
		onclick="addCpxxDiv();"  class="syj-addbtn" 
		>添加</a>
		<%-- </c:if> --%> 
	   <span>产品信息    </span> <a href="javascript:void(0);" onclick="txsm(this);" title="<div style='width:700px;'>1.食品、食品添加剂类别，类别编号，类别名称，品种明细等的界定详见总局《关于公布食品生产许可分类目录的公告》（2016年第23号）中的《食品生产许可分类目录》。
<br />2.申请食品许可，同一大类食品涉及多个类别名称或多个序号的品种明细的，每个序号的品种明细应当分栏分别填写；“品种明细”填写“其他”的，还应当在备注中填写具体产品名称。同一类别名称的多个序号的品种名细产品，加工工艺、执行标准相同的，可以合并在同一栏填写。
<br />3.申请食品添加剂许可，类别编号为“3201”的，如涉及多个品种食品添加剂，每个品种分栏分别填写；类别编号为“3202”的，按液体、乳化、浆(膏)状、粉末(拌和、胶囊)进行归类，并分栏分别填写；类别编号为“3203”的，按配方分栏填写。
<br />4.申请需注册、备案的产品时，备注栏需填写相应的注册批准文号或者备案登记号。</div>
" style="width: 20%;color: #0C83D3;">填写说明 </a></div>
	<div 
	<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true' ||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
	 style="width: 95%"
	 </c:if>
	>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20" id="cpxxDiv">
			<tr>	
				<th>食品、食品添加剂类别</th>		
				<th>类别名称</th>
				<th>类别编号</th>	
				<th>品种明细</th>	
				<th>产品执行标准文号</th>	
				<th>备注</th>	
			</tr>
			<c:if test="${empty cpxxList}">
			<tr>	
				<td>
					<input type="hidden" name="SPLB_NAME" value=""/>
					<input type="hidden" name="CPXX_LBMC" value=""/>
                   	<fda:fda-exselect name="CPXX_SPLB"  style="width: 97%;" clazz="input-dropdown validate[required]" 
								allowblank="false" datainterface="fdaBusTypeService.findListForSelect" 
								defaultemptytext="=请选择类别=" 
					    	 queryparamjson="{PARENT_ID:'402848df54e0c4970154e0c63aa30002',ORDER_TYPE:'ASC'}">
					 </fda:fda-exselect>
				</td>				
				<td>
					<fda:fda-exselect name="CPXX_LBBH"  style="width: 97%;" clazz="input-dropdown validate[required]" 
							allowblank="false" datainterface="fdaBusTypeService.findListForSelect" 
							defaultemptytext="=请选择类别名称=" 
						 queryparamjson="{PARENT_ID:'',ORDER_TYPE:'ASC'}">
					</fda:fda-exselect>
				</td>	
				<td style="text-align: center;"><input type="hidden" name="CPXX_DEPTID">
					<input name="CPXX_ISPROVINCE" type="hidden" value="0"/>
					<input type="hidden" name="CPXX_DEPTID" value=""/>
					<input type="hidden" maxlength="128" placeholder="请选择单位"  style="width:92%"
					class="syj-input1 validate[]" name="CPXX_DEPTNAME" readonly="readonly"/>
					<input type="text" class="syj-input1 validate[required]" placeholder="类别编号" name="LBBH_CODE"  style="width:50%"/>
					<%-- <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}"> --%>
						<input type="button" style="width:60%" onclick="showSelectBusTypes('');" class="extract-button2" value="选择品种明细">
					<%-- </c:if> --%>
				</td>									
				<td style="text-align: center;">
					<input type="hidden" name="CPXX_PZMXID">		
					<textarea name="CPXX_PZMX" <%-- <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}">onclick="showSelectBusTypes('');"</c:if> --%>
					style="width: 95%" class="eve-textarea validate[required]" cols="5" rows="5" ></textarea>					
				</td>	
				<td><textarea type="text" maxlength="256" cols="5" rows="5"
					value="" placeholder="请输入产品执行标准文号"
					class="eve-textarea validate[required]" name="CPXX_CPZXBZWH" style="width:92%"></textarea></td>
				<td><textarea type="text" maxlength="256"
					value="" placeholder="请输入备注" cols="3" rows="3"
					class="eve-textarea " name="CPXX_BZ" style="width:90%"></textarea>
					<span style="line-height:14px;font-size:12px;" name="SLJG"></span>
					</td>
			</tr>
			</c:if>
			<c:forEach items="${cpxxList}" var="cpxxList"  varStatus="s">
				<tr>	
					<td>
					<input type="hidden" name="CPXX_LBMC" value="${cpxxList.CPXX_LBMC }"/>
						<input type="hidden" name="SPLB_NAME" value="${cpxxList.SPLB_NAME }"/>
						<c:if test="${s.index == 0 }">
						<fda:fda-exselect name="CPXX_SPLB" style="width: 97%;" clazz="input-dropdown validate[required]" 
									allowblank="false" datainterface="fdaBusTypeService.findListForSelect" 
									defaultemptytext="=请选择类别="  value="${cpxxList.CPXX_SPLB}"
								 queryparamjson="{PARENT_ID:'402848df54e0c4970154e0c63aa30002',ORDER_TYPE:'ASC'}">
						 </fda:fda-exselect>
						</c:if>
						<c:if test="${s.index > 0 }">
						<fda:fda-exselect name="${cpxxList.CPXX_ID}CPXX_SPLB" disabled="true"  style="width: 97%;" clazz="input-dropdown validate[required]" 
									allowblank="false" datainterface="fdaBusTypeService.findListForSelect" 
									defaultemptytext="=请选择类别="  value="${cpxxList.CPXX_SPLB}"
								 queryparamjson="{PARENT_ID:'402848df54e0c4970154e0c63aa30002',ORDER_TYPE:'ASC'}">
						 </fda:fda-exselect>
						</c:if>
					</td>				
					<td>
						<fda:fda-exselect name="${cpxxList.CPXX_ID}CPXX_LBBH" id="${cpxxList.CPXX_LBBH}" style="width: 97%;" clazz="input-dropdown validate[required]" 
								allowblank="false" datainterface="fdaBusTypeService.findListForSelect" 
								defaultemptytext="=请选择类别名称="  value="${cpxxList.CPXX_LBBH}"
							 queryparamjson="{PARENT_ID:'${cpxxList.CPXX_SPLB}',ORDER_TYPE:'ASC'}">
						</fda:fda-exselect>
					</td>
					<td style="text-align: center;">
						<input name="${cpxxList.CPXX_ID}CPXX_ISPROVINCE" type="hidden" value="${ cpxxList.CPXX_ISPROVINCE}"/>
						<input type="hidden" name="${cpxxList.CPXX_ID}CPXX_DEPTID" value="${cpxxList.CPXX_DEPTID}">
						<input type="hidden" maxlength="128" placeholder="请选择单位" value="${cpxxList.CPXX_DEPTNAME}" style="width:92%"
							class="syj-input1 validate[]" name="${cpxxList.CPXX_ID}CPXX_DEPTNAME" readonly="readonly"/>
					<input type="text" class="syj-input1 validate[required]" value="${cpxxList.LBBH}"
					 readonly="readonly" placeholder="类别编号" name="${cpxxList.CPXX_ID}LBBH_CODE"  style="width:50%"/>
					 <c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'}"><input type="button" style="width:60%" onclick="showSelectBusTypes('${cpxxList.CPXX_ID}');" class="extract-button2" value="选择品种明细"></c:if>
					</td>	
					<td style="text-align: center;">	
						<input type="hidden" name="${cpxxList.CPXX_ID}CPXX_PZMXID" value="${cpxxList.CPXX_PZMXID}">					
						<textarea name="${cpxxList.CPXX_ID}CPXX_PZMX" <%-- readonly="readonly"<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true' ||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">onclick="showSelectBusTypes('${cpxxList.CPXX_ID}');"</c:if> --%>
						style="width: 95%" class="eve-textarea" cols="5" rows="5">${cpxxList.CPXX_PZMX}</textarea>
					</td>					
					<td><textarea type="text" maxlength="128" cols="5" rows="5" placeholder="请输入产品执行标准文号" style="width:92%"
						class="eve-textarea validate[required]" name="CPXX_CPZXBZWH" >${cpxxList.CPXX_CPZXBZWH}</textarea>
						</td>
					<td><textarea type="text" maxlength="256"
					value="" placeholder="请输入备注" cols="3" rows="3"
					class="eve-textarea " name="${cpxxList.CPXX_ID}CPXX_BZ" style="width:90%">${cpxxList.CPXX_BZ}</textarea>
					<span style="line-height:14px;font-size:12px;" name="${cpxxList.CPXX_ID}SLJG">受理机构：${cpxxList.CPXX_DEPTNAME }</span>
					<%-- <input type="text" maxlength="128" placeholder="请输入备注" style="width:92%"
						class="syj-input1" name="CPXX_BZ" value="${cpxxList.CPXX_BZ}"/> --%>
						<c:if test="${s.index>0&&(EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true')}"><a href="javascript:void(0);" onclick="delCpxxDiv(this);" class="syj-closebtn" 
						 style="position: relative;right:-30%;float: right;"></c:if></a>
						</td>
				</tr>
				<script type="text/javascript">
				$(function(){
					 $("select[name='${cpxxList.CPXX_ID}CPXX_SPLB']").change(function(){ 
						var JYCSXS = $("select[name='JYCSXS']").val();
						if(JYCSXS!=null&&JYCSXS!=""){	
							  var pId = $(this).children("option:selected").val();
							  $("[name$=SPLB_NAME]").val($(this).children("option:selected").text());
							  FdaAppUtil.reloadSelect($("select[name='${cpxxList.CPXX_ID}CPXX_LBBH']"),{
								  dataParams:"{PARENT_ID:'"+pId+"',ORDER_TYPE:'ASC'}"
							  });
						}else{
							art.dialog({
					            content: "请先填写生产地址！",
					            icon:"warning",
					            ok: true
					        });
						   $("select[name='${cpxxList.CPXX_ID}CPXX_SPLB']").val('');
						}
					});  
						$("select[name='${cpxxList.CPXX_ID}CPXX_LBBH']").change(function(){
						    var pId = $(this).children("option:selected").val();
						    var pName = $(this).children("option:selected").text();
						    $("[name='${cpxxList.CPXX_ID}CPXX_LBMC']").val(pName);
						    var trIndex = $("#cpxxDiv tr").index($(this).closest("tr"));
							var JYCSQS = $("select[name='JYCSQS']").val();
							var JYCSXS = $("select[name='JYCSXS']").val();
							/* var repeat = false;
							$("select[name$='CPXX_LBBH']").each(function(index){
								if($(this).attr("name") != "${cpxxList.CPXX_ID}CPXX_LBBH"){
									if(pId == $(this).children("option:selected").val()){
										repeat = true;
									}
								}
							});
							if(repeat){
								art.dialog({
				            		content: "您好，您选择的类别名称已存在，请重新选择！",
				            		icon:"warning",
				            		ok: true
				        		});
								$(this).val("");
								return;
							} */
							$.post("foodProductionController/getCpxxDept.do",{
								typeId:pId,
								JYCSQS:JYCSQS,
								JYCSXS:JYCSXS
							}, function(responseText, status, xhr) {
								var resultJson = $.parseJSON(responseText);
								resetPzmx('${cpxxList.CPXX_ID}');								
								var diff =false;
								$("input[name$='CPXX_DEPTID']").each(function(){
									if($(this).val() !="" &&$(this).val() != resultJson.DEPART_ID){
										diff = true;
									}
								});
								if(diff){
									art.dialog({
					            		content: "您好，您申报的产品类型属于不同机构受理，请分别提交申请",
					            		icon:"warning",
					            		ok: true
					        		});
									$("[name='${cpxxList.CPXX_ID}CPXX_LBBH']").val('');
									return;
								}
								var flag = resultJson.TYPE_CODE+"_"+trIndex;
								if($(".sbssdiv")[trIndex-1]){
									$(".sbssdiv").eq(trIndex-1).html(appendSbss(pId,pName,flag));
								}else{
									$("#EQUIPMENT").append("<div class='sbssdiv'>"+appendSbss(pId,pName,flag)+"</div>");
								}
								if($(".jgcsdiv")[trIndex-1]){
									$(".jgcsdiv").eq(trIndex-1).html(appendJgcs(pId,pName,flag));
								}else{
									$("#ProcessingPlace").append("<div class='jgcsdiv'>"+appendJgcs(pId,pName,flag)+"</div>");
								}
								$("[name='${cpxxList.CPXX_ID}CPXX_DEPTID']").val(resultJson.DEPART_ID);
								$("[name='${cpxxList.CPXX_ID}CPXX_ISPROVINCE']").val(resultJson.ISPROVINCE);
								$("[name='ISPROVINCE']").val(resultJson.ISPROVINCE);
								$("[name='${cpxxList.CPXX_ID}CPXX_DEPTNAME']").val(resultJson.DEPART_NAME);
								$("[name='${cpxxList.CPXX_ID}LBBH_CODE']").val(resultJson.TYPE_CODE);
								$("[name='${cpxxList.CPXX_ID}SLJG']").html("受理机构："+resultJson.DEPART_NAME);
							});
						});
				});
				</script>
			</c:forEach>
		</table>
	</div>
		
    </div>
</form>
