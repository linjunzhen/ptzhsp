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
/**
 * 添加
 */
function addJyyqTr(){
	var JYYQ_TEMPLATE_TR = $("#JYYQ_TEMPLATE_TR").html();
	$(".zxjyTable").append(JYYQ_TEMPLATE_TR);
	setTimeout(function(){
		$(".zxjyTable .laydate-icon").each(function(index){
			$(this).attr("id","yxq"+index)
			initDate("yxq"+index);
		});
	},0);
}
/**
 * 删除
 */
 function delJyyqTr(o){
	$(o).closest("tr").remove();
}
 function initDate(id){
	 laydate.reset();
	 var YXQ = {
				elem : "#"+id,
				format : "YYYY年MM月DD日",
				event: 'click', //触发事件
				istime : false,
				choose : function(datas) {
					
				}
			};
	 laydate(YXQ);
}
function getJyyqJson(){
	var jyyqArray = [];
	var SPLB_MC = $("[name='CPXX_LBMC']").val();
	var SPLB_ID = $("[name='CPXX_SPLB']").val();
	$(".zxjyTable tr").each(function(i){
		var JYYQ_LX = $(this).find("[name='JYYQ_LX']").val();
		var JYYQ_MC = $(this).find("[name='JYYQ_MC']").val();
		var JYYQ_XHGG = $(this).find("[name='JYYQ_XHGG']").val();
		var JYYQ_JDDD = $(this).find("[name='JYYQ_JDDD']").val();
		var JYYQ_SL = $(this).find("[name='JYYQ_SL']").val();
		var JYYQ_YXQZ = $(this).find("[name='JYYQ_YXQZ']").val();
		if(JYYQ_MC != undefined){
			var JYYQ_INFO = {};
			JYYQ_INFO.JYYQ_LX = JYYQ_LX;
			JYYQ_INFO.JYYQ_MC = JYYQ_MC;
			JYYQ_INFO.JYYQ_XHGG = JYYQ_XHGG;
			JYYQ_INFO.JYYQ_JDDD = JYYQ_JDDD;
			JYYQ_INFO.JYYQ_SL = JYYQ_SL;
			JYYQ_INFO.JYYQ_YXQZ = JYYQ_YXQZ;
			JYYQ_INFO.JYYQ_XH = i-1;
			JYYQ_INFO.SPLB_MC = SPLB_MC;
			JYYQ_INFO.SPLB_ID = SPLB_ID;
			//JYYQ_INFO.PZMX = pzmx;
			jyyqArray.push(JYYQ_INFO);
		}
	});
	if(jyyqArray.length>0){
		return JSON.stringify(jyyqArray);
	}else{
		return "";
	}
}
$(function(){
	$("#zxjyTable .laydate-icon").each(function(index){
		$(this).attr("id","yxq"+index)
		initDate("yxq"+index);
	});
});
</script>
<div style="position:relative;display: none;">
	<table id="JYYQ_TEMPLATE_TR">
	<tr>
		<td>
		<input type="hidden" name="JYYQ_LX" value="0" />
		<input type="text" name="JYYQ_MC" style="width:80%;"placeholder="请输入名称" maxlength="32"
		 class="syj-input1 " /></td>
		<!-- <td><input type="text" name="JYYQ_XHGG" style="width:150px;" placeholder="请输入型号规格" maxlength="32"
		class="syj-input1 "/></td> -->
		<td><input type="text" name="JYYQ_JDDD" style="width:80%;" placeholder="请输入精度等级" maxlength="128"
		 class="syj-input1 " /></td>
		<!--  <td><input type="text" name="JYYQ_YXQZ" style="width:150px;"  placeholder="请输入检定有效截止期" maxlength="32"
		 class="syj-input1 validate[]  laydate-icon" /></td> -->
		<td><input type="text" name="JYYQ_SL"  style="width:70%;" placeholder="请输入数量" maxlength="32"
		class="syj-input1 "/>
    	<a href="javascript:void(0);" 
		onclick="delJyyqTr(this);" 
		class="syj-closebtn" style="position: relative;display: table-row;float: right;right: -50px;"
		></a>
		 </td>
	</tr>
	</table>
</div>
<form action="" id="JYYQ_FORM"  >
	<div id="Jyyq_BaseInfo_Div">
	<div class="syj-title1 tmargin20">
	<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true' ||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
		<a href="javascript:void(0);" 
		onclick="addJyyqTr();"  class="syj-addbtn" 
		>添加</a></c:if> 
	   <span>检验仪器</span><a href="javascript:void(0);" onclick="txsm(this);" title="<div style='width:700px;'>1.同一大类食品涉及多个类别名称或多个序号的品种明细食品的，全部检验使用的仪器均填写在本表。<br />
2.委托检验时应提交委托检验合同（合同时间不短于许可证有效期）、有效的承检机构资质证明及承检项目资质明细。</div>" style="width: 20%;color: #0C83D3;">填写说明 </a></div>
		<div style="position:relative;" >
			<table cellpadding="0" id="" cellspacing="0" class="syj-table2 zxjyTable">
			<tr>
				<th>名称</th>
				<th>精度等级</th>
				<th>数量</th>
			</tr>
			<c:if test="${empty jyyqList }">
				<tr>
				<td>
		<input type="hidden" name="JYYQ_LX" value="0" />
		<input type="text" name="JYYQ_MC" placeholder="请输入名称" style="width:80%;" maxlength="32"
		 class="syj-input1 " value="${jyyq.JYYQ_MC }"/></td>
		<td><input type="text" name="JYYQ_JDDD" placeholder="请输入精度等级" style="width:80%;" maxlength="128"
		 class="syj-input1" value="${jyyq.JYYQ_JDDD }"/></td>
		<td><input type="text" name="JYYQ_SL" placeholder="请输入数量" style="width:70%;" maxlength="32" style="width:63%;"
		class="syj-input1" value="${jyyq.JYYQ_SL }"/>
		 </td>
			</tr>
			</c:if>
			<c:forEach items="${jyyqList }" var="jyyq" varStatus="s">
			<c:if test="${jyyq.JYYQ_LX=='0' }">
				<tr>
				<td>
		<input type="hidden" name="JYYQ_LX" value="0" />
		<input type="text" name="JYYQ_MC" placeholder="请输入名称" style="width:80%;" maxlength="32"
		 class="syj-input1 " value="${jyyq.JYYQ_MC }"/></td>
		<td><input type="text" name="JYYQ_JDDD" placeholder="请输入精度等级" style="width:80%;" maxlength="128"
		 class="syj-input1" value="${jyyq.JYYQ_JDDD }"/></td>
		<td><input type="text" name="JYYQ_SL" placeholder="请输入数量" style="width:70%;" maxlength="32" style="width:63%;"
		class="syj-input1" value="${jyyq.JYYQ_SL }"/>
		 	<c:if test="${s.index>0}">
    	<a href="javascript:void(0);" 
		onclick="delJyyqTr(this);" 
		class="syj-closebtn" style="position: relative;display: table-row;float: right;right: -50px;"
	></a>
	</c:if>
		 </td>
			</tr>
			</c:if>
			</c:forEach>
			</table>
		</div>
    </div>
</form>
