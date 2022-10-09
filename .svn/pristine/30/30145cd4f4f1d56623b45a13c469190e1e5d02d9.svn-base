<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
function addProcessingPlaceDiv(id,name,flag){
	var count = $("#"+flag+" tr").length ;
	var trHtml = "<tr>"
		+"<td style='width:30px;text-align:center;'>"+count+"</td>"
		+"<td align='center'><input type='text' class='syj-input1  validate[required]' maxlength='64' style='width:92%' name='SPLB' value='"+name+"' placeholder='请输入类别名称' />"
		+"<input type='hidden' name='LBID' value='"+id+"' /><input type='hidden' name='LBBH' value='"+flag+"' /></td>"
		+"<td><input type='text' maxlength='256'"
			+"value='' placeholder='请输入生产场点、工艺、工序名称'"
			+"class='syj-input1 validate[]' name='JGMC' style='width:92%'/></td>"
		+"<td><input type='text' maxlength='256'"
			+"value='' placeholder='请输入生产场点、工艺、工序所在地'"
			+"class='syj-input1  validate[]' name='JGSZD' style='width:75%'/>"
			+"<a href='javascript:void(0);' onclick='delTr(this);' class='syj-closebtn'"
		 		+"style='position: relative;display: table-row;float: right;right: -50px;'></a></td>"
	+"</tr>";
	$("#"+flag).append(trHtml);
}
function delTr(o){
	var $table = $(o).closest("table");
	$(o).closest("tr").remove();
	$table.find("tr").each(function(j){
		 if(j>0){
			 
			 $(this).find("td").eq(0).html(j);
		 }
	});
}
function getJGCSJson(){
	var jgcsArray = [];
	$("#ProcessingPlace_Form table").each(function(i){
		var pzmx = $(this).parent().parent().find("[data-id$='pzmx']").html();
		$(this).find("tr").each(function(j){
			var SPLB = $(this).find("[name='SPLB']").val();
			var LBID = $(this).find("[name='LBID']").val();
			var JGMC = $(this).find("[name='JGMC']").val();
			var JGSZD = $(this).find("[name='JGSZD']").val();
			var LBBH = $(this).find("[name='LBBH']").val();
			if(SPLB != undefined){
				var jgcs = {};
				jgcs.SPLB = SPLB;
				jgcs.LBID = LBID;
				jgcs.JGSZD = JGSZD;
				jgcs.JGMC = JGMC;
				jgcs.JGXH = j;
				jgcs.LBBH = LBBH;
				jgcs.PZMX = pzmx;
				jgcsArray.push(jgcs);
			}
		});
	});
	if(jgcsArray.length>0){
		return JSON.stringify(jgcsArray);
	}else{
		return "";
	}
}
</script>

<form action="" id="ProcessingPlace_Form"  >
  <div class="syj-title1"><span>加工场所</span></div>
  <div id="ProcessingPlace"  style="width: 100%">
	<c:forEach items="${jgcs.keySet()}" var="jkey">
		<div class='jgcsdiv'>
			<div data-id='${jkey }'>
				<div class='syj-title1' style="margin-top:5px;">
					<a href='javascript:void(0);' onclick="addProcessingPlaceDiv('${jgcs.get(jkey).get(0).LBID}','${jgcs.get(jkey).get(0).SPLB }','${jkey}');" 
					class='syj-addbtn'>添加</a>
					<span style='height:30px;line-height:30px'>${jgcs.get(jkey).get(0).SPLB}（品种明细：<font data-id='${jkey}pzmx'>${jgcs.get(jkey).get(0).PZMX}</font>）</span>
				</div>
				<div style='position:relative;margin-top:5px;'>
					<table cellpadding='0' cellspacing='0' class='syj-table2'
						id='${jkey}'>
						<tr>
							<th style='width:30px;text-align:center;'>序号</th>
							<th>类别名称</th>
							<th>生产场点、工艺、工序名称</th>
							<th>生产场点、工艺、工序所在地</th>
						</tr>
						<c:forEach items="${jgcs.get(jkey)}" var="j" varStatus="s">
						<tr>
							<td style='width:30px;text-align:center;'>${s.index+1 }</td>
							<td align='center'>
								<input type='text' maxlength='64' style='width:92%'
								class='syj-input1 validate[]' name="SPLB" value="${j.SPLB }" placeholder="请输入类别名称"/>
								<input type="hidden" name="LBID" value="${j.LBID }"/>
								<input type="hidden" name="LBBH" value="${jkey }"/>
							</td>
							<td><input type='text' maxlength='256' value='${j.JGMC}'
								placeholder='请输入生产场点、工艺、工序名称' 
								class='syj-input1 validate[]' name='JGMC'
								style='width:92%' /></td>
							<td><input type='text' maxlength='256' value='${j.JGSZD }'
								placeholder='请输入生产场点、工艺、工序所在地' class='syj-input1 '
								name='JGSZD' style='width:75%' />
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
<script type="text/javascript">
	
</script>
</form>