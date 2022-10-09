<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<style>
.eflowbutton{
	  background: #3a81d0;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #fff;
	  border-radius: 5px;
	  
}
.eflowbutton-disabled{
	  background: #94C4FF;
	  border: none;
	  padding: 0 10px;
	  line-height: 26px;
	  cursor: pointer;
	  height:26px;
	  color: #E9E9E9;
	  border-radius: 5px;
	  
}
.selectType{
	margin-left: -100px;
}
</style>
<script type="text/javascript">
	$(function () {
		setChangeSelection();
	});
	
	function setChangeSelection(){
		$.post("enterpriseChangeController/findChangeOption.do",{
		}, function(responseText, status, xhr) {
			var obj = JSON.parse(responseText);
			var options = eval('(' + obj.jsonString + ')');
			for(var i=0;i<options.length;i++){
				var html = "";
				if(options[i].DIC_NAME=="监事备案"){
					html+="<tr id='jsba'><td style='text-align: center;'><input type='checkbox' name='CHANGE_OPTIONS' value='"+options[i].DIC_CODE+"'></td>";
					html+="<td>"+options[i].DIC_NAME+"</td>";
					html+="<td id='content_"+options[i].DIC_CODE+"'></td>";
					html+="<td id='change_"+options[i].DIC_CODE+"'></td></tr>";
				}else if(options[i].DIC_CODE!="09"){
					html+="<tr><td style='text-align: center;'><input type='checkbox' name='CHANGE_OPTIONS' value='"+options[i].DIC_CODE+"'></td>";
					html+="<td>"+options[i].DIC_NAME+"</td>";
					html+="<td id='content_"+options[i].DIC_CODE+"'></td>";
					html+="<td id='change_"+options[i].DIC_CODE+"'></td></tr>";
				}
				
				$("#changeOptions").append(html);
					
			}
			//设置变更选项选择框点击事件
			//setClickEvent();
			
			//变更选项回填
			$('input[name="CHANGE_OPTIONS"]').each(function() {
				var checked = '${busRecord.CHANGE_OPTIONS}';
				if(checked.indexOf($(this).val())>=0){
			        $(this).attr('checked', true);
			        $(this).attr('disabled', true);
				}
			});
			
			if('${busRecord.CHANGE_ID}'){
				//变更前内容
				$("#content_01").html('${busRecord.COMPANY_NAME}');
				$("#content_02").html('${busRecord.REGISTER_ADDR}');
				$("#content_03").html('${busRecord.LEGAL_NAME}');
				$("#content_04").html('${busRecord.COMPANY_TYPE_NAME}');					
				$("#content_05").html('${busRecord.INVESTMENT}'+" 万元");
				$("#content_06").html('${busRecord.YEAR_FROM}'+" 至 "+'${busRecord.YEAR_TO}');
				$("#content_08").html('${busRecord.BUSSINESS_SCOPE}');
				var holderJson = '${busRecord.HOLDER_JSON}';
				var holers =  eval('(' + holderJson + ')');
				var html_07="";
				var html_09="";
				for(var i=0;i<holers.length;i++){
					html_07+=holers[i].SHAREHOLDER_NAME+"  证照号码："+holers[i].LICENCE_NO+"<br>";
					html_09+=holers[i].SHAREHOLDER_NAME+"<br>";	
				}				
				$("#content_07").html(html_07);
				$("#content_09").html(html_09);
				
				var html_10="";
				var html_11="";
				var html_12="";				
				<c:forEach items="${directorList}" var="director">
					html_10 += "${director.DIRECTOR_JOB_OLD_NAME}：${director.DIRECTOR_NAME_OLD}<br>";
				</c:forEach>
				<c:forEach items="${supervisorList}" var="supervisor">					
					html_11 += "${supervisor.SUPERVISOR_JOB_OLD_NAME}：${supervisor.SUPERVISOR_NAME_OLD}<br/>";
				</c:forEach>
				<c:forEach items="${managerList}" var="manager">					
					html_12 += "${manager.MANAGER_JOB_OLD_NAME}：${manager.MANAGER_NAME_OLD}<br/>";
				</c:forEach>			
				$("#content_10").html(html_10);
				$("#content_11").html(html_11);
				$("#content_12").html(html_12);
				
				//变更后内容			
				$("#change_01").html('${busRecord.COMPANY_NAME_CHANGE}');
				$("#change_02").html('${busRecord.REGISTER_ADDR_CHANGE}');
				$("#change_03").html('${busRecord.LEGAL_NAME_CHANGE}');
				$("#change_04").html('${busRecord.COMPANY_TYPE_NAME_CHANGE}');
				var investmentChange = Number('${busRecord.INVESTMENT_CHANGE}');
				if(investmentChange>0){
					$("#change_05").html(investmentChange+"万元");
				}else{
					$("#change_05").html("");
				}	
				
				//是否有进行经营期限变更
				var checked = '${busRecord.CHANGE_OPTIONS}';
				if(checked.indexOf("06")>=0){
					if('${busRecord.OPRRATE_TERM_TYPE_CHANGE}'=="1"){
						$("#change_06").html('${busRecord.BUSSINESS_YEARS_CHANGE}'+"年");
					}else{
						$("#change_06").html("长期");
					}
				}else{
					$("#change_06").html("");
				} 
				
				$("#change_08").html('${busRecord.BUSSINESS_SCOPE_CHANGE}');
				var newhtml_07="";
				var newhtml_09="";				
				<c:forEach items="${newHolderList}" var="newHolder">		
					newhtml_07+="${newHolder.SHAREHOLDER_NAME}  证照号码： ${newHolder.LICENCE_NO}<br>";
					newhtml_09+="${newHolder.SHAREHOLDER_NAME} <br>";
				</c:forEach>
				var newhtml_10="";
				var newhtml_11="";
				var newhtml_12="";
								
				<c:forEach items="${directorChangeList}" var="directorChange">		
					newhtml_10 += "${directorChange.DIRECTOR_JOB_NAME}：${directorChange.DIRECTOR_NAME}<br>";
				</c:forEach>
				<c:forEach items="${supervisorChangeList}" var="supervisorChange">					
					newhtml_11 += "${supervisorChange.SUPERVISOR_JOB_NAME}：${supervisorChange.SUPERVISOR_NAME}<br>";
				</c:forEach>
				<c:forEach items="${managerChangeList}" var="managerChange">					
					newhtml_12 += "${managerChange.MANAGER_JOB_NAME}：${managerChange.MANAGER_NAME}<br>";
				</c:forEach>
				$("#change_10").html(newhtml_10);
				$("#change_11").html(newhtml_11);
				$("#change_12").html(newhtml_12);
				
				if("${fn:length(supervisorList)}">1){
					//隐藏监事备案选择项
				    $("#jsba").attr("style","display:none");
				} 
				
			    var checked = '${busRecord.CHANGE_OPTIONS}';
			    if(checked.indexOf("07")>=0){
			    	$("#change_07").html(newhtml_07);
			    }else {
			    	$("#change_07").html("");
			    }
			    
				if(checked.indexOf("09")>=0){
			    	$("#change_09").html(newhtml_09);
			    }else {
			    	$("#change_09").html("");
			    }
			}
		});
	}
	
</script>

<input type="hidden" name="CHANGE_OPTIONS"  value="${busRecord.CHANGE_OPTIONS }">	
<div class="tab_height"></div>
<!--变更事项信息  -->
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">变更事项信息</th>
	</tr>
	<tr>
	<%-- 	<td class="tab_width"><font class="tab_color">*</font>变更事项：</td>
		<td colspan="3">
			<eve:excheckbox
				dataInterface="extraDictionaryService.findDatasForSelect"
				name="CHANGE_OPTIONS" width="90%;" clazz="checkbox"
				dataParams="changeItem" value="${busRecord.CHANGE_OPTIONS}">
			</eve:excheckbox>
		</td> --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2"  id="changeOptions">							
				<tr>
					<th style="width:10%;color:red" >选择变更事项</th>
					<th style="width:15%">变更事项</th>
					<th style="width:45%">变更前内容</th>
					<th style="width:30%">变更后内容</th>
				</tr>
		</table>
	</tr>
</table>

