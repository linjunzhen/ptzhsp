<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
.bdcdydacxTrRed{
	color:red;
}
.zbdwSelect{
	width:100px !important;
}
</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>建设单位（变更前）
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jsdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${jsdwList}" var="jsdw" varStatus="s">
		<tr class="jsdwTr" id="jsdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${jsdw.ROW_JSON}"/>
			<td style="text-align: center;">${jsdw.CORPNAME}</td>
			<td style="text-align: center;">${jsdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${jsdw.ORGCODE}</td>
			<td style="text-align: center;">${jsdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${jsdw.PERSONNAME}</td>
			<td style="text-align: center;">${jsdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('jsdw','jsdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>建设单位（变更后）
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jsdwAfterTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${jsdwAfterList}" var="jsdw" varStatus="s">
		<tr class="jsdwAfterTr" id="jsdwAfterTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${jsdw.ROW_JSON}"/>
			<td style="text-align: center;">${jsdw.CORPNAME}</td>
			<td style="text-align: center;">${jsdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${jsdw.ORGCODE}</td>
			<td style="text-align: center;">${jsdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${jsdw.PERSONNAME}</td>
			<td style="text-align: center;">${jsdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton afterUpdate" onclick="addDwInfo('jsdw','jsdwAfterTr_${s.index}');" style="display:none">编 辑</a>
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('jsdw','jsdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>


<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>施工单位（变更前）
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="sgdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目经理</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${sgdwList}" var="sgdw" varStatus="s">
		<tr class="sgdwTr" id="sgdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${sgdw.ROW_JSON}"/>
			<td style="text-align: center;">${sgdw.CORPNAME}</td>
			<td style="text-align: center;">${sgdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${sgdw.ORGCODE}</td>
			<td style="text-align: center;">${sgdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${sgdw.PERSONNAME}</td>
			<td style="text-align: center;">${sgdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('sgdw','sgdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>施工单位（变更后）
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="sgdwAfterTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目经理</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${sgdwAfterList}" var="sgdw" varStatus="s">
		<tr class="sgdwAfterTr" id="sgdwAfterTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${sgdw.ROW_JSON}"/>
			<td style="text-align: center;">${sgdw.CORPNAME}</td>
			<td style="text-align: center;">${sgdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${sgdw.ORGCODE}</td>
			<td style="text-align: center;">${sgdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${sgdw.PERSONNAME}</td>
			<td style="text-align: center;">${sgdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton afterUpdate" onclick="addDwInfo('sgdw','sgdwAfterTr_${s.index}');" style="display:none">编 辑</a>
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('sgdw','sgdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>监理单位（变更前）
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jldwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">总监理工程师</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${jldwList}" var="jldw" varStatus="s">
		<tr class="jldwTr" id="jldwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${jldw.ROW_JSON}"/>
			<td style="text-align: center;">${jldw.CORPNAME}</td>
			<td style="text-align: center;">${jldw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${jldw.ORGCODE}</td>
			<td style="text-align: center;">${jldw.LEGAL_NAME}</td>
			<td style="text-align: center;">${jldw.PERSONNAME}</td>
			<td style="text-align: center;">${jldw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('jldw','jldwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>监理单位（变更后）
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jldwAfterTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">总监理工程师</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${jldwAfterList}" var="jldw" varStatus="s">
		<tr class="jldwAfterTr" id="jldwAfterTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${jldw.ROW_JSON}"/>
			<td style="text-align: center;">${jldw.CORPNAME}</td>
			<td style="text-align: center;">${jldw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${jldw.ORGCODE}</td>
			<td style="text-align: center;">${jldw.LEGAL_NAME}</td>
			<td style="text-align: center;">${jldw.PERSONNAME}</td>
			<td style="text-align: center;">${jldw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton afterUpdate" onclick="addDwInfo('jldw','jldwAfterTr_${s.index}');" style="display:none">编 辑</a>
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('jldw','jldwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>
