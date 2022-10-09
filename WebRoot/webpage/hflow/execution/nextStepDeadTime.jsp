<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<script type="text/javascript">
function changeQxzdfs(){
	var qxzdfsValue = AppUtil.getCheckRadioTagValue("EFLOW_QXZDFS","value");
	if(qxzdfsValue=="1"){
		$("#EFLOW_DEADLINETIME_TD").attr("style","display:none;");
		$("input[name='EFLOW_DEADLINETIME']").attr("class","");
		$("#EFLOW_WORKDAYLIMIT_TD").attr("style","");
		$("input[name='EFLOW_WORKDAYLIMIT']").attr("class","eve-input validate[required,min[1],max[100],custom[integer]]");
	}else{
		$("#EFLOW_WORKDAYLIMIT_TD").attr("style","display:none;");
		$("input[name='EFLOW_WORKDAYLIMIT']").attr("class","");
		$("#EFLOW_DEADLINETIME_TD").attr("style","");
		$("input[name='EFLOW_DEADLINETIME']").attr("class","laydate-icon validate[required]");
	}
}
</script>

<c:if test="${nodeConfig.IS_ASSIGNDEADTIME==1}">
	<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
		<tr>
			<th colspan="1">指定办理期限</th>
		</tr>
		<tr>
			<td><span style="width: 100px;float:left;text-align:right;">指定方式：</span>
				<eve:radiogroup typecode="FlowQxzdfs" width="130px"
					fieldname="EFLOW_QXZDFS" defaultvalue="1" onclick="changeQxzdfs();"></eve:radiogroup>
			</td>
		</tr>
		<tr>
			<td id="EFLOW_WORKDAYLIMIT_TD"><span
				style="width: 100px;float:left;text-align:right;">工作日数量：</span> <input
				type="text" style="width:150px;float:left;" value="0"
				class="eve-input validate[required,min[0],max[100],custom[integer]]"
				name="EFLOW_WORKDAYLIMIT" maxlength="3" /> <font color="red">提示:如果工作日数量设置为0,代表对办理期限无限制</font>
			</td>
			<td id="EFLOW_DEADLINETIME_TD" style="display: none;"><span
				style="width: 100px;float:left;text-align:right;">截止时间：</span> <input
				type="text" style="width:150px;float:left;" readonly="readonly"
				onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm',min: laydate.now()})"
				class="laydate-icon validate[required]" name="EFLOW_DEADLINETIME" />
			</td>
		</tr>
	</table>
</c:if>