<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
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

$(function(){
	
});



/**
 * 获取产业奖补JSON
 */
function getCyjbJson(){
	var CyjbArray = [];
	$("#cyjbTable tr").each(function(i){
		var CYJB_TYPE = $(this).find("[name$='CYJB_TYPE']").val();
		var CYJB_JBJE = $(this).find("[name$='CYJB_JBJE']").val();
		var CYJB_GSNSZE = $(this).find("[name$='CYJB_GSNSZE']").val();
		var CYJB_GSDFJSS = $(this).find("[name$='CYJB_GSDFJSS']").val();
		var CYJB_DSNSZE = $(this).find("[name$='CYJB_DSNSZE']").val();
		var CYJB_DSDFJSS = $(this).find("[name$='CYJB_DSDFJSS']").val();
		var CYJB_NSZE = $(this).find("[name$='CYJB_NSZE']").val();
		var CYJB_DFJSR = $(this).find("[name$='CYJB_DFJSR']").val();
		var CYJB_GSJE = $(this).find("[name$='CYJB_GSJE']").val();
		var CYJB_JBSD = $(this).find("[name$='CYJB_JBSD']").val();
		var CYJB_BZ = $(this).find("[name$='CYJB_BZ']").val();
		
		if(CYJB_TYPE!=null&&CYJB_TYPE!=''){
			var cyjb = {};
			cyjb.CYJB_TYPE = CYJB_TYPE;
			cyjb.CYJB_JBJE = CYJB_JBJE;
			cyjb.CYJB_GSNSZE = CYJB_GSNSZE;
			cyjb.CYJB_GSDFJSS = CYJB_GSDFJSS;
			cyjb.CYJB_DSNSZE = CYJB_DSNSZE;
			cyjb.CYJB_DSDFJSS = CYJB_DSDFJSS;
			cyjb.CYJB_NSZE = CYJB_NSZE;
			cyjb.CYJB_DFJSR = CYJB_DFJSR;
			cyjb.CYJB_GSJE = CYJB_GSJE;
			cyjb.CYJB_JBSD = CYJB_JBSD;
			cyjb.CYJB_BZ = CYJB_BZ;
			CyjbArray.push(cyjb);
		}
	});
	if(CyjbArray.length>0){
		return JSON.stringify(CyjbArray);
	}else{
		return "";
	}
}

function showCyjbSelectDeparts(){
	var departId = $("input[name='CYJB_SPDEPT_ID']").val();
	$.dialog.open("departmentController/selector.do?noAuth=true&departIds="+departId+"&allowCount=1&checkCascadeY=&"
			+"checkCascadeN=", {
		title : "部门选择器",
		width:"600px",
		lock: true,
		resize:false,
		height:"500px",
		close: function () {
			var selectDepInfo = art.dialog.data("selectDepInfo");
			if(selectDepInfo){
				$("input[name='CYJB_SPDEPT_ID']").val(selectDepInfo.departIds);
				$("input[name='CYJB_SPDEPT_NAME']").val(selectDepInfo.departNames);
			}
		}
	}, false);
};
/**
* 添加
*/
function addCyjbDiv(){
	var len = $("#cyjbTable").find('tr').length;
	if(len>=6){
		art.dialog({
			content: "类型最多5项！",
			icon:"warning",
			ok: true
		});
		return;
	}
	$.post("cyjbController/refreshCyjbDiv.do",{
	}, function(responseText, status, xhr) {
		$("#cyjbTable").append(responseText);
	});
}
/**
 * 删除
 */
function delCycjDiv(o){
	$(o).closest("tr").remove();
	
} 
function setValidateCyjb(o){
	$(o).parent().parent().find("input").val($(o).val());
}
function onlyNumber(obj){       
	//得到第一个字符是否为负号  
	var t = obj.value.charAt(0);    
	//先把非数字的都替换掉，除了数字和.   
	obj.value = obj.value.replace(/[^\d\.]/g,'');     
	//必须保证第一个为数字而不是.     
	obj.value = obj.value.replace(/^\./g,'');     
	//保证只有出现一个.而没有多个.     
	obj.value = obj.value.replace(/\.{2,}/g,'.');     
	//保证.只出现一次，而不能出现两次以上     
	obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');  
	//只能输入小数点后两位
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
	//如果第一位是负号，则允许添加  
	if(t == '-'){  
		obj.value = '-'+obj.value;  
	}   
}  
//sid取mid,tid值的和
function sumNumber(mid,tid,sid){
	var m = $("input[name='"+mid+"']").val();
	var t = $("input[name='"+tid+"']").val();
	 $("input[name='"+sid+"']").val(Number(m)+Number(t));
}
function sumSpjeNumber(){
	var sum = 0;
	$("#cyjbTable tr").each(function(i){
		var CYJB_JBJE = $(this).find("[name$='CYJB_JBJE']").val();
		if(null!=CYJB_JBJE&&CYJB_JBJE!=''){
			sum = Number(sum)+Number(CYJB_JBJE);
		}
	});
	 $("input[name='CYJB_SPJE']").val(sum);
}
function saveCyjb(){
	var flowSubmitObj = FlowUtil.getFlowObj();
	if(flowSubmitObj.busRecord){
		var CyjbJson = getCyjbJson();
		$.post("cyjbController.do?saveCyjb",{
			YW_ID:flowSubmitObj.busRecord.BUS_RECORDID,
			CyjbJson:CyjbJson,
			CYJB_SPJE: $("input[name='CYJB_SPJE']").val(),
			CYJB_SPDEPT_NAME:$("input[name='CYJB_SPDEPT_NAME']").val(),
			CYJB_CZSJ:$("input[name='CYJB_CZSJ']").val(),
			CYJB_SPDEPT_ID:$("input[name='CYJB_SPDEPT_ID']").val()
		}, function(responseText, status, xhr) {
			var resultJson = $.parseJSON(responseText);
			if(resultJson.success){
				art.dialog({
					content: resultJson.msg,
					icon:"succeed",
					time:3,
					ok: true
				});
			}else{
				art.dialog({
					content: resultJson.msg,
					icon:"error",
					ok: true
				});
			}
		}); 
	}
}
</script>
<div id="cyjb" style="display: none;">
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="4">产业奖补信息&nbsp;<a class="eflowbutton" id="saveCyjb" onclick="saveCyjb();" style="height: 28px;display: none;">保存</a></th>
		</tr>
		<tr>
			<td class="tab_width">审批金额(元)：</td>
			<td style="width:430px">
				<input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="18" 
				name="CYJB_SPJE" onkeyup="onlyNumber(this)" onblur="onlyNumber(this)" />
			</td>
			<td class="tab_width">审核部门：</td>
			<td>
				<input type="hidden" name="CYJB_SPDEPT_ID" />
				<input readonly="true" type="text" class="tab_text validate[]" maxlength="128" 
				name="CYJB_SPDEPT_NAME"  onclick="showCyjbSelectDeparts();"/></td>
				
		</tr>
		<tr>
			
			<td class="tab_width">出账时间：</td>
			<td colspan="3"><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="tab_text validate[] Wdate" maxlength="32"
				name="CYJB_CZSJ"  style="border: 1px solid #bbb;height: 24px;"/></td>
		</tr>
	</table>
	<div class="tab_height"></div>
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="cyjbTable">
		<tr>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">奖补类型</td>
			<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">奖补金额<br/>(元)</td>
			<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">国税纳税总额<br/>(元)</td>
			<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">国税地方级税收<br/>(元)</td>
			<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">地税纳税总额<br/>(元)</td>
			<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">地税地方级税收<br/>(元)</td>
			<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">纳税总额<br/>(元)</td>
			<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">地方级收入<br/>(元)</td>
			<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">个税金额<br/>(元)</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">奖补时段</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">备注</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		</tr>
		
		<c:if test="${empty cyjbList}">
		<tr>
			<td>
				<div style="position:relative;">
				<span id = "ss" style="margin-left:100px;width:18px;overflow:hidden;">
				<eve:eveselect clazz="tab_text selectType validate[]" dataParams="jblx"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setValidateCyjb(this)"
				defaultEmptyText="选择奖补类型" name="SELECTTYPE">
				</eve:eveselect>
				</span>
				<input name="CYJB_TYPE" class="validate[]" style="position: absolute; left: 0px; width: 160px; top: 0px; height: 20px; border-width: 0px; margin-top: 1px; margin-left: 1px;"/>
				</div>
			</td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
				name="CYJB_JBJE"   style="width: 60px;" onkeyup="onlyNumber(this);sumSpjeNumber();"
				onblur="onlyNumber(this);sumSpjeNumber();"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="CYJB_GSNSZE" style="width: 60px;" onkeyup="onlyNumber(this);sumNumber('CYJB_GSNSZE','CYJB_DSNSZE','CYJB_NSZE');"
					onblur="onlyNumber(this);sumNumber('CYJB_GSNSZE','CYJB_DSNSZE','CYJB_NSZE');"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="CYJB_GSDFJSS" style="width: 60px;" onkeyup="onlyNumber(this);sumNumber('CYJB_GSDFJSS','CYJB_DSDFJSS','CYJB_DFJSR');"
					onblur="onlyNumber(this);sumNumber('CYJB_GSDFJSS','CYJB_DSDFJSS','CYJB_DFJSR');"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="CYJB_DSNSZE" style="width: 60px;"  onkeyup="onlyNumber(this);sumNumber('CYJB_GSNSZE','CYJB_DSNSZE','CYJB_NSZE');"
					onblur="onlyNumber(this);sumNumber('CYJB_GSNSZE','CYJB_DSNSZE','CYJB_NSZE');"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="CYJB_DSDFJSS" style="width: 60px;" onkeyup="onlyNumber(this);sumNumber('CYJB_GSDFJSS','CYJB_DSDFJSS','CYJB_DFJSR');"
					onblur="onlyNumber(this);sumNumber('CYJB_GSDFJSS','CYJB_DSDFJSS','CYJB_DFJSR');"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="18"
					name="CYJB_NSZE"  style="width: 60px;" onkeyup="onlyNumber(this)" onblur="onlyNumber(this)"/></td>
			<td>
				<input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="18"
					name="CYJB_DFJSR" style="width: 60px;"  onkeyup="onlyNumber(this)" onblur="onlyNumber(this)"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="CYJB_GSJE" style="width: 60px;" onkeyup="onlyNumber(this);" onblur="onlyNumber(this)"/></td>
			<td><input type="text" class="tab_text" maxlength="128" name="CYJB_JBSD" style="width: 120px;"  /></td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="500"
					name="CYJB_BZ" style="width: 120px;"  /></td>
			<td><a href="javascript:void(0);" onclick="addCyjbDiv();"  class="syj-addbtn" >添加类型</a></td>
		</tr>	
		</c:if>
		<c:forEach items="${cyjbList}" var="cyjbList"  varStatus="s">
		<tr>
			<td>
				<div style="position:relative;">
				<span id = "ss" style="margin-left:100px;width:18px;overflow:hidden;">
				<eve:eveselect clazz="tab_text selectType validate[]" dataParams="jblx"
				dataInterface="dictionaryService.findDatasForSelect" onchange="setValidateCyjb(this)"
				defaultEmptyText="选择奖补类型" name="SELECTTYPE">
				</eve:eveselect>
				</span>
				<input name="${cyjbList.CYJB_ID}CYJB_TYPE"  class="validate[required]" value="${cyjbList.CYJB_TYPE}" 
				style="position: absolute; left: 0px; width: 160px; top: 0px; height: 20px; border-width: 0px; margin-top: 1px; margin-left: 1px;"/>
				</div>
			</td>
			
			<td><input type="text" class="tab_text validate[[required],custom[numberplus]]" maxlength="16"
				name="${cyjbList.CYJB_ID}CYJB_JBJE"  style="width: 60px;"   value="${cyjbList.CYJB_JBJE}" 
				onkeyup="onlyNumber(this);sumSpjeNumber();"
				onblur="onlyNumber(this);sumSpjeNumber();"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="${cyjbList.CYJB_ID}CYJB_GSNSZE" value="${cyjbList.CYJB_GSNSZE}"  style="width: 60px;"
					 onkeyup="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSNSZE','${cyjbList.CYJB_ID}CYJB_DSNSZE','${cyjbList.CYJB_ID}CYJB_NSZE');"
					 onblur="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSNSZE','${cyjbList.CYJB_ID}CYJB_DSNSZE','${cyjbList.CYJB_ID}CYJB_NSZE');"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="${cyjbList.CYJB_ID}CYJB_GSDFJSS" value="${cyjbList.CYJB_GSDFJSS}"  style="width: 60px;" 
					 onkeyup="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSDFJSS','${cyjbList.CYJB_ID}CYJB_DSDFJSS','${cyjbList.CYJB_ID}CYJB_DFJSR');"
					 onblur="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSDFJSS','${cyjbList.CYJB_ID}CYJB_DSDFJSS','${cyjbList.CYJB_ID}CYJB_DFJSR');" /></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="${cyjbList.CYJB_ID}CYJB_DSNSZE" value="${cyjbList.CYJB_DSNSZE}" style="width: 60px;" 
					 onkeyup="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSNSZE','${cyjbList.CYJB_ID}CYJB_DSNSZE','${cyjbList.CYJB_ID}CYJB_NSZE');"
					 onblur="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSNSZE','${cyjbList.CYJB_ID}CYJB_DSNSZE','${cyjbList.CYJB_ID}CYJB_NSZE');"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="${cyjbList.CYJB_ID}CYJB_DSDFJSS" value="${cyjbList.CYJB_DSDFJSS}" style="width: 60px;"  
					 onkeyup="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSDFJSS','${cyjbList.CYJB_ID}CYJB_DSDFJSS','${cyjbList.CYJB_ID}CYJB_DFJSR');"
					 onblur="onlyNumber(this);sumNumber('${cyjbList.CYJB_ID}CYJB_GSDFJSS','${cyjbList.CYJB_ID}CYJB_DSDFJSS','${cyjbList.CYJB_ID}CYJB_DFJSR');"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="18"
					name="${cyjbList.CYJB_ID}CYJB_NSZE"  value="${cyjbList.CYJB_NSZE}" style="width: 60px;"
					 onkeyup="onlyNumber(this)" onblur="onlyNumber(this)" /></td>
			<td>
				<input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="18"
					name="${cyjbList.CYJB_ID}CYJB_DFJSR"  value="${cyjbList.CYJB_DFJSR}" style="width: 60px;"  
					 onkeyup="onlyNumber(this)" onblur="onlyNumber(this)"/></td>
			<td><input type="text" class="tab_text validate[[],custom[numberplus]]" maxlength="16"
					name="${cyjbList.CYJB_ID}CYJB_GSJE" value="${cyjbList.CYJB_GSJE}"  style="width: 60px;"
					 onkeyup="onlyNumber(this)" onblur="onlyNumber(this)"/></td>
			<td><input type="text" class="tab_text" maxlength="128" name="${cyjbList.CYJB_ID}CYJB_JBSD"  value="${cyjbList.CYJB_JBSD}" style="width: 120px;"  /></td>
			<td>
				<input type="text" class="tab_text validate[]" maxlength="500"
					name="${cyjbList.CYJB_ID}CYJB_BZ" value="${cyjbList.CYJB_BZ}" style="width: 120px;" /></td>
			<td>
			<c:if test="${s.index==0}">
				<a href="javascript:void(0);" onclick="addCyjbDiv();"  class="syj-addbtn" >添加类型</a>
			</c:if>
			<c:if test="${s.index>0}">
				<a href="javascript:void(0);" onclick="delCycjDiv(this);" class="syj-closebtn"></a>
			</c:if>
			</td>
		</tr>	
		</c:forEach>
	</table>
</div>

<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->