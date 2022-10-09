<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="net.evecom.platform.wsbs.service.ApplyMaterService"%>
<%@page import="net.evecom.platform.wsbs.service.ServiceItemService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="com.alibaba.fastjson.JSON"%>
<%@ page language="java" import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@ page language="java" import="net.evecom.platform.hflow.service.MaterConfigService"%>
<%@ page language="java" import="net.evecom.platform.hflow.model.FlowNextStep"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<%
	String flowSubmitInfoJson = request.getParameter("flowSubmitInfoJson");
	String infoJson = StringEscapeUtils.unescapeHtml3(flowSubmitInfoJson);
	Map<String,Object> flowSubmitInfo = JSON.parseObject(infoJson,Map.class);
	String itemCode = (String)flowSubmitInfo.get("ITEM_CODE");
	ServiceItemService serviceItemService =(ServiceItemService) AppUtil.getBean("serviceItemService");
	Map<String,Object> serviceItem = serviceItemService.getItemAndDefInfoNew(itemCode);
	String itemId = (String) serviceItem.get("ITEM_ID");
	String exeId = (String) flowSubmitInfo.get("EFLOW_EXEID");
	String currentNodeName = (String) flowSubmitInfo.get("EFLOW_CURUSEROPERNODENAME");
	ApplyMaterService applyMaterService =(ApplyMaterService) AppUtil.getBean("applyMaterService");
	//获取材料信息列表
    List<Map<String,Object>> applyMaters = applyMaterService.findByItemId(itemId,exeId);
    List<Map<String,Object>> filterApplyMaters = new ArrayList<Map<String,Object>>();
    if (applyMaters != null && applyMaters.size() > 0) {
        filterApplyMaters.addAll(applyMaters);
    }
    if (StringUtils.isNotEmpty(currentNodeName)
            && StringUtils.isNotEmpty(exeId) && applyMaters != null
            && applyMaters.size() > 0) {
        String sysUserName = AppUtil.getLoginUser().getUsername();
        List<Map<String, Object>> filterMater = applyMaterService
                .findFilterMater(sysUserName, currentNodeName, exeId);
        if (filterMater != null && filterMater.size() > 0) {
            applyMaters.clear();
            for (int i = 0; i < filterMater.size(); i++) {
                Map<String, Object> m = filterMater.get(i);
                for (int j = 0; j < filterApplyMaters.size(); j++) {
                    Map<String, Object> fmap = filterApplyMaters.get(j);
                    if (m.get("MATER_ID").toString()
                            .equals(fmap.get("MATER_ID").toString())) {
                        applyMaters.add(fmap);
                    }
                }
            }
        }
        List<Map<String, Object>> againMater = applyMaterService
                .findAgainMater(sysUserName, currentNodeName, exeId);
        List<Map<String, Object>> againFZ=new ArrayList<Map<String,Object>>();
        if(againMater!=null&& againMater.size() > 0){
        	for (int i = 0; i < againMater.size(); i++) {
                Map<String, Object> m = againMater.get(i);
                for (int j = 0; j < applyMaters.size(); j++) {
                    Map<String, Object> fmap = applyMaters.get(j);
                    if (m.get("MATER_ID")!=null&&m.get("MATER_ID").toString()
                            .equals(fmap.get("MATER_ID").toString())) {
                        m.put("MATER_CODE", fmap.get("MATER_CODE"));
                        againFZ.add(m);
                    }
                }
            }
        	String againMaterJson = JSON.toJSONString(againFZ);
        	request.setAttribute("againMaterJson", againMaterJson);
        }
    }
    String applyMatersJson = JSON.toJSONString(applyMaters);
    applyMatersJson = StringEscapeUtils.escapeHtml3(applyMatersJson);
    request.setAttribute("applyMatersJson", applyMatersJson);
    request.setAttribute("applyMaters", applyMaters);
	request.setAttribute("flowSubmitInfo", flowSubmitInfo);
%>

<script type="text/javascript">
function getAgainbjcllbJson(){
    var bjcllbList = [];
    var applyMatersJson = $("input[name='applyMatersJson']").val();
    var applyMatersObj = JSON2.parse(applyMatersJson);
    for(var index in applyMatersObj){
        //获取材料编码
        var MATER_CODE = applyMatersObj[index].MATER_CODE;
        //var selectValue = $("#SFBJ_"+MATER_CODE).val();
        var selectValue = $("input[name='SFTG_"+MATER_CODE+"']:checked ").val();
        var bjcllb = {};
        bjcllb.MATER_ID = $("#mi_"+MATER_CODE).val();
        bjcllb.SFTG = selectValue;
        bjcllb.BTGYJ = $("#btgyq_"+MATER_CODE).val();;
        bjcllbList.push(bjcllb);
     } 
    if(bjcllbList.length==0){
    	return "";
    }
    return JSON2.stringify(bjcllbList);
}

</script>
<style>
.materBtnA {
    background: #62a1cf none repeat scroll 0 0;
    color: #fff;
    display: inline-block;
    height: 26px;
    left: -1px;
    line-height: 26px;
    margin-left: 5px;
    position: relative;
    text-align: center;
    top: 1px;
    width: 80px;
}
</style>


<div id="againDiv">
<input type="hidden" name="applyMatersJson" value="${applyMatersJson}" />
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2" >
	<tr>
		<th colspan="3">二次审核材料列表</th>
	</tr>
	<tr>
			<td>材料名称</td>
			<td style="width: 100px;">是否通过</td>
			<td style="width: 300px;">不通过意见</td>
	</tr>
	<c:forEach items="${applyMaters}" var="applyMater"
		varStatus="varStatus">
		<tr>
			<td>${applyMater.MATER_NAME}
			<input type="hidden" id="mi_${applyMater.MATER_CODE}" value="${applyMater.MATER_ID}"/>
			 </td>
			<%-- <td>${applyMater.MATER_CODE}---${applyMater.MATER_ID}</td> --%>
			<td>
				<eve:radiogroup typecode="YesOrNo" width="130px"
						fieldname="SFTG_${applyMater.MATER_CODE}" defaultvalue="1"></eve:radiogroup>
			</td>
			<td><input type="text" id="btgyq_${applyMater.MATER_CODE}"
										class="eve-input" maxlength="60" style="width:95%;" /></td>
		</tr>
	</c:forEach>
</table>
</div>
<script>
	function setAgainMatter(){
    var againMaterJson = '<%=request.getAttribute("againMaterJson")%>';
    var againMatersObj = JSON2.parse(againMaterJson);
    for(var index in againMatersObj){
    	var MATER_CODE = againMatersObj[index].MATER_CODE;
    	//var val=$("input[name='SFTG_"+MATER_CODE+"']:checked ").val();
       var rObj = document.getElementsByName("SFTG_"+MATER_CODE);
       var sftg=againMatersObj[index].SFTG;
       if(sftg=="0"){
       	  rObj[1].checked ="checked";
       }
        $("#btgyq_"+MATER_CODE).val(againMatersObj[index].BTGYJ);
     } 
}
setAgainMatter();
</script>