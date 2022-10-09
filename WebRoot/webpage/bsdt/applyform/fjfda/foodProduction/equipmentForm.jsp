<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
function addEquipmentDiv(id,name,flag){
	var count = $("#"+flag+"Sbss tr").length;
	var trHtml = "<tr>"
		+"<td style='width:30px;text-align:center;'>"+count+"</td>"
		+"<td align='center'><input type='text' maxlength='64' placeholder='请输入类别名称' style='width:92%' class='syj-input1 validate[required]' name='SPLB' value='"+name+"' />"
		+"<input type='hidden' name='LBID' value='"+id+"' /><input type='hidden' name='LBMC' value='"+flag+"' /></td>"
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
			+"class='syj-input1  validate[]' name='WHZT' style='width:60%'/>"
			+"<a href='javascript:void(0);' onclick='delTr(this);' class='syj-closebtn'"
		 		+"style='position: relative;display: table-row;float: right;right: -50px;'></a></td>"
	+"</tr>";
	$("#"+flag+"Sbss").append(trHtml);
}
$(function(){
	
});

function getSbssJson(){
	var sbssArray = [];
	$("#EQUIPMENT_FORM table").each(function(i){
		var pzmx = $(this).parent().parent().find("[data-id$='pzmx']").html();
		$(this).find("tr").each(function(j){
			var SPLB = $(this).find("[name='SPLB']").val();
			var LBID = $(this).find("[name='LBID']").val();
			var LBMC = $(this).find("[name='LBMC']").val();
			var SBSSMC = $(this).find("[name='SBSSMC']").val();
			var GGXH = $(this).find("[name='GGXH']").val();
			var SL = $(this).find("[name='SL']").val();
			var WHZT = $(this).find("[name='WHZT']").val();
			if(SPLB != undefined){
				var sbss = {};
				sbss.SPLB = SPLB;
				sbss.LBID = LBID;
				sbss.LBMC = LBMC;
				sbss.SBSSMC = SBSSMC;
				sbss.GGXH = GGXH;
				sbss.SBXH = j;
				sbss.SL = SL;
				sbss.PZMX = pzmx;
				sbss.WHZT = WHZT;
				sbssArray.push(sbss);
			}
		});
	});
	if(sbssArray.length>0){
		return JSON.stringify(sbssArray);
	}else{
		return "";
	}
}
</script>

<form action="" id="EQUIPMENT_FORM"  >
  <div class="syj-title1"><span>设备、设施</span></div>
	<div id="EQUIPMENT">
	<c:forEach items="${sbss.keySet()}" var="jkey">
		<c:set var="SPLB" value="${sbss.get(jkey).get(0).SPLB }"></c:set>
		<c:set var="LBID" value="${sbss.get(jkey).get(0).LBID }"></c:set>
		<c:set var="PZMX" value="${sbss.get(jkey).get(0).PZMX }"></c:set>
					<div class='sbssdiv'>
						<div data-id='${jkey }'>
							<div class='syj-title1' style="margin-top:5px;">
							<c:if test="${EFLOW_VARS.EFLOW_IS_START_NODE=='true'||EFLOW_VARS.EFLOW_FROMHISTROY=='true'}">
								<a href='javascript:void(0);' onclick="addEquipmentDiv('${LBID}','${SPLB }','${jkey }');" 
								class='syj-addbtn'>添加</a>
								</c:if>
								<span style='height:30px;line-height:30px'>${SPLB }（品种明细：<font data-id='${jkey}pzmx'>${PZMX} </font>）</span>
							</div>
							<div style='position:relative;margin-top:5px;'>
								<table cellpadding='0' cellspacing='0' class='syj-table2'
									id='${jkey}Sbss'>
									<tr>
										<th style='width:30px;text-align:center;'>序号</th>
										<th>类别名称</th>
										<th>设备设施名称</th>
										<th>规格型号</th>
										<th>数量</th>
										<th>完好状态</th>
									</tr>
									<c:forEach items="${sbss.get(jkey)}" var="j" varStatus="s">
									<tr>
										<td style='width:30px;text-align:center;'>${s.index+1 }</td>
										<td align='center'>
											<input type="hidden" name="LBMC" value="${j.LBMC }"/>
											<input type="hidden" name="LBID" value="${j.LBID }"/>
											<input type="text" class='syj-input1 validate[]' style='width:92%'
											 name="SPLB" placeholder='请输入类别名称'  value="${j.SPLB }"/>
										</td>
										<td><input type='text' maxlength='256' value='${j.SBSSMC}'
											placeholder='请输入设备设施名称' 
											class='syj-input1 validate[]' name='SBSSMC'
											style='width:92%' /></td>
										<td><input type='text' maxlength='256' value='${j.GGXH}'
											placeholder='请输入规格型号' 
											class='syj-input1 validate[]' name='GGXH'
											style='width:92%' /></td>
										<td><input type='text' maxlength='256' value='${j.SL}'
											placeholder='请输入数量' 
											class='syj-input1 validate[]' name='SL'
											style='width:92%' /></td>
										<td><input type='text' maxlength='256' value='${j.WHZT }'
											placeholder='请输入完好状态' class='syj-input1 validate[]'
											name='WHZT' style='width:60%' />
											<a href='javascript:void(0);' onclick='delTr(this);' class='syj-closebtn del-tr' style='position: relative;display: table-row;float: right;right: -50px;'></a>
										</td>
									</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
	</c:forEach>
	</div>
</form>