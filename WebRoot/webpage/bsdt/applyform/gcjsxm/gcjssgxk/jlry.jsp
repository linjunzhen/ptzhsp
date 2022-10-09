<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>监理人员
		</th>
	</tr>
</table>
<c:forEach items="${jldwList}" var="jldw" varStatus="s">		

	<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="${s.index}jlryTable">
		<tr>
			<th colspan="7" style="background-color: #FFD39B;">监理单位名称：${jldw.CORPNAME}</th>
		</tr>
		<tr>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">姓名</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">身份证</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">工作岗位</td>
			<td class="tab_width1" style="width: 18%;color: #000; font-weight: bold;text-align: center;">职称</td>
			<td class="tab_width1" style="width: 16%;color: #000; font-weight: bold;text-align: center;">专业</td>
			<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">证书编号</td>
			<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
		</tr>
		<c:forEach items="${jldw.JLRYLIST}" var="jlry" varStatus="s1">
			<tr class="jlryxxTr" id="jlryTr_${s.index}_${s1.index}">
				<input type="hidden" name="jlryxx" value="${jlry.JLRY}"/>
				<td style="text-align: center;">${jlry.STATION}</td>
				<td style="text-align: center;">${jlry.PERSONIDENTITY}</td>
				<td style="text-align: center;">${jlry.PERSONNAME}</td>
				<td style="text-align: center;">${jlry.PERSONTITLE}</td>
				<td style="text-align: center;">${jlry.PERSONPROF}</td>
				<td style="text-align: center;">${jlry.PERSONCERTID}</td>
				<td style="text-align: center;">
				<a href="javascript:void(0);" class="eflowbutton" onclick="getJlry('jlryTr_${s.index}_${s1.index}');">查 看</a>
				
				</td>
			</tr>
		</c:forEach>
	</table>
</c:forEach>