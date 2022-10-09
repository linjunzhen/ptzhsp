<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="zjgcInfoDiv" >
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>桩基工程
		</th>
	</tr>
</table>
<div style="color:red;">
	<p>注意事项：</p>
	<p>&nbsp;&nbsp;1、针对房建工程，请建设单位认真核实该项目本次申报的施工许可范围是否包括室外工程（包括“室外设施”和“附属建筑及室外环境”单位工程）； </p>
	<p>&nbsp;&nbsp;2、桩基工程仅是单位工程的一部分，若桩基先行申报，单位工程个数应按实际划分（将影响后期上部申报），且单位工程信息应填写完整，总面积应包含上部的面积，不能为0！
	</p>
</div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="zjgcTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">单位工程类别</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">结构类型</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">建筑高度/长度（m）</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">建筑面积（m2）</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">工程总造价（万元）</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${zjgcList}" var="zjgc" varStatus="s">
		<tr class="zjgcTr" id="zjgcTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${zjgc.ROW_JSON}"/>
			<td style="text-align: center;">${zjgc.SINGLEPRONAME}</td>
			<td style="text-align: center;">
				<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="PROTYPE"
				dataInterface="dictionaryService.findDatasForSelect" 
				defaultEmptyText="请选择工程类别" name="SINGLEPROMAINTYPE" id="SINGLEPROMAINTYPE" value="${zjgc.SINGLEPROMAINTYPE}">
				</eve:eveselect>
			</td>
			<td style="text-align: center;">${zjgc.STRUCTQUALTYPE}</td>
			<td style="text-align: center;">${zjgc.ARCHHEIGHT} ${zjgc.MUNILENGTH}</td>
			<td style="text-align: center;">${zjgc.ARCHAREA} ${zjgc.FLOORAREA}</td>
			<td style="text-align: center;">${zjgc.ITEMINVEST}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getZjgcInfo('zjgc','${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>