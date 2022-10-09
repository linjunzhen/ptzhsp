<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.fjfda.service.FdaDictionaryService"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="fda" uri="/fda-tag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
FdaDictionaryService dictionaryService = (FdaDictionaryService)AppUtil.getBean("fdaDictionaryService");
request.setAttribute("aqzdDic", dictionaryService.findByTypeCode("AQZD"));
%>
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
/**
 * 添加
 */
function addAqzdTr(){
	var AQZD_TEMPLATE_TR = $("#AQZD_TEMPLATE_TR").html();
	$("#aqzdTable").append(AQZD_TEMPLATE_TR);
}
/**
 * 删除
 */
 function delAqzdTr(o){
	$(o).closest("tr").remove();
}

function getAqzdJson(){
	var aqzdArray = [];
	$("#aqzdTable tr").each(function(i){
		var AQZD_MC = $(this).find("[name='AQZD_MC']").val();
		var AQZD_WBBH = $(this).find("[name='AQZD_WBBH']").val();
		if(AQZD_MC !="" && AQZD_MC){
			var AQZD_INFO = {};
			AQZD_INFO.AQZD_MC = AQZD_MC;
			AQZD_INFO.AQZD_WBBH = AQZD_WBBH;
			AQZD_INFO.AQZD_XH = i+1;
			aqzdArray.push(AQZD_INFO);
		}
	});
	if(aqzdArray.length>0){
		return JSON.stringify(aqzdArray);
	}else{
		return "";
	}
}
</script>
<div style="position:relative;display: none;">
	<table id="AQZD_TEMPLATE_TR">
	<tr>
		<td>
		<input type="text" name="AQZD_MC" placeholder="请输入管理制度名称" maxlength="32"
		 class="syj-input1 validate[]" /></td>
		<td><input type="text" name="AQZD_WBBH" style="width:75%;" placeholder="请输入文本编号" maxlength="32"
		class="syj-input1 validate[]" />
		 <c:if test="${EFLOW_IS_START_NODE=='true'}">
    	<a href="javascript:void(0);" 
		onclick="delAqzdTr(this);" 
		class="syj-closebtn" style="position: relative;display: table-row;float: right;right: -50px;"
	></a>
	</c:if>
		 </td>
	</tr>
	</table>
</div>
<form action="" id="AQZD_FORM"  >
	<div class="syj-title1 tmargin20">
	<c:if test="${EFLOW_IS_START_NODE=='true'}">
		<a href="javascript:void(0);" 
		onclick="addAqzdTr();"  class="syj-addbtn" 
		>添加</a></c:if> 
	   <span>安全制度清单</span></div>
	<div id="Aqzd_BaseInfo_Div">
		<div style="position:relative;" >
			<table cellpadding="0" id="aqzdTable" cellspacing="0" class="syj-table2 tmargin20">
			<tr>
				<th>管理制度名称</th>
				<th>文本编号</th>
			</tr>
			<c:if test="${empty aqzdList }">
				<c:forEach items="${ aqzdDic}" var="a" varStatus="s">
				<tr>
		<td>
		<input type="text" name="AQZD_MC" placeholder="请输入管理制度名称" maxlength="32"
		 class="syj-input1 " value="${a.DIC_NAME }"/></td>
		<td><input type="text" name="AQZD_WBBH" placeholder="请输入文本编号" style="width:75%;" maxlength="32"
		class="syj-input1 " />
			<c:if test="${s.index>0}">
    			<a href="javascript:void(0);" 
				onclick="delAqzdTr(this);" 
				class="syj-closebtn" style="position: relative;display: table-row;float: right;right: -50px;"
			></a>
		</c:if>
		</td>
	</tr>
				</c:forEach>
			
	</c:if>
	<c:forEach items="${aqzdList}" var="aqzd" varStatus="s">
	<tr>
		<td>
		<input type="text" name="AQZD_MC" placeholder="请输入管理制度名称" maxlength="32"
		 class="syj-input1 " value="${aqzd.AQZD_MC }"/></td>
		<td><input type="text" name="AQZD_WBBH" placeholder="请输入文本编号" maxlength="32"
		class="syj-input1 " style="width:75%;" value="${aqzd.AQZD_WBBH }"/>
		 
		<c:if test="${s.index>0}">
    	<a href="javascript:void(0);" 
		onclick="delAqzdTr(this);" 
		class="syj-closebtn" style="position: relative;display: table-row;float: right;right: -50px;"
	></a>
	</c:if>
		</td>
	</tr>
	</c:forEach>
			</table>
		</div>
    </div>
</form>
