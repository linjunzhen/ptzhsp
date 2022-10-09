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
		<th>建设单位
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
		<th>代建、工程总承包（EPC）、PPP等单位
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="djdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${djdwList}" var="djdw" varStatus="s">
		<tr class="djdwTr" id="djdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${djdw.ROW_JSON}"/>
			<td style="text-align: center;">${djdw.CORPNAME}</td>
			<td style="text-align: center;">${djdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${djdw.ORGCODE}</td>
			<td style="text-align: center;">${djdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${djdw.PERSONNAME}</td>
			<td style="text-align: center;">${djdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('djdw','djdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>


<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>施工单位
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
		<th>监理单位
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
		<th>勘察单位
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="kcdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${kcdwList}" var="kcdw" varStatus="s">
		<tr class="kcdwTr" id="kcdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${kcdw.ROW_JSON}"/>
			<td style="text-align: center;">${kcdw.CORPNAME}</td>
			<td style="text-align: center;">${kcdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${kcdw.ORGCODE}</td>
			<td style="text-align: center;">${kcdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${kcdw.PERSONNAME}</td>
			<td style="text-align: center;">${kcdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('kcdw','kcdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>设计单位
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="sjdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${sjdwList}" var="sjdw" varStatus="s">
		<tr class="sjdwTr" id="sjdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${sjdw.ROW_JSON}"/>
			<td style="text-align: center;">${sjdw.CORPNAME}</td>
			<td style="text-align: center;">${sjdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${sjdw.ORGCODE}</td>
			<td style="text-align: center;">${sjdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${sjdw.PERSONNAME}</td>
			<td style="text-align: center;">${sjdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('sjdw','sjdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>施工图审查单位
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="sgtscdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">施工图审查人</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${sgtscdwList}" var="sgtscdw" varStatus="s">
		<tr class="sgtscdwTr" id="sgtscdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${sgtscdw.ROW_JSON}"/>
			<td style="text-align: center;">${sgtscdw.CORPNAME}</td>
			<td style="text-align: center;">${sgtscdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${sgtscdw.ORGCODE}</td>
			<td style="text-align: center;">${sgtscdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${sgtscdw.PERSONNAME}</td>
			<td style="text-align: center;">${sgtscdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('sgtscdw','sgtscdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>控制价（预算价）计价文件编制单位
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="kzjdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">造价工程师</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${kzjdwList}" var="kzjdw" varStatus="s">
		<tr class="kzjdwTr" id="kzjdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${kzjdw.ROW_JSON}"/>
			<td style="text-align: center;">${kzjdw.CORPNAME}</td>
			<td style="text-align: center;">${kzjdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${kzjdw.ORGCODE}</td>
			<td style="text-align: center;">${kzjdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${kzjdw.PERSONNAME}</td>
			<td style="text-align: center;">${kzjdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('kzjdw','kzjdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>检测单位
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="jcdwTable">
	<tr>
		<td class="tab_width1" style="width: 24%;color: #000; font-weight: bold;text-align: center;">单位名称</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 14%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 12%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${jcdwList}" var="jcdw" varStatus="s">
		<tr class="jcdwTr" id="jcdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${jcdw.ROW_JSON}"/>
			<td style="text-align: center;">${jcdw.CORPNAME}</td>
			<td style="text-align: center;">${jcdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${jcdw.ORGCODE}</td>
			<td style="text-align: center;">${jcdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${jcdw.PERSONNAME}</td>
			<td style="text-align: center;">${jcdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('jcdw','jcdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>


<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th>招标代理单位
		</th>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="zbdwTable">
	<tr>
		<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">招标类型</td>
		<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">招标方式</td>
		<td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">中标单位</td>
		<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">中标金额（万元）</td>
		<td class="tab_width1" style="width: 15%;color: #000; font-weight: bold;text-align: center;">招标代理单位</td>
		<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">统一社会信用代码</td>
		<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">组织机构代码</td>
		<td class="tab_width1" style="width: 8%;color: #000; font-weight: bold;text-align: center;">法人代表</td>
		<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">项目负责人</td>
		<td class="tab_width1" style="width: 6%;color: #000; font-weight: bold;text-align: center;">联系电话</td>
		<td class="tab_width1" style="width: 10%;color: #000; font-weight: bold;text-align: center;">操作</td>
	</tr>	
	<c:forEach items="${zbdwList}" var="zbdw" varStatus="s">
		<tr class="zbdwTr" id="zbdwTr_${s.index}">
			<input type="hidden" name="ROW_JSON" value="${zbdw.ROW_JSON}"/>
			<td style="text-align: center;">
				
				<eve:eveselect clazz="tab_text zbdwSelect validate[required]" dataParams="TENDERCLASSNUM"
				dataInterface="dictionaryService.findDatasForSelect"  value="${zbdw.TENDERCLASSNUM}"
				defaultEmptyText="请选择招标类型" name="TENDERCLASSNUM">
				</eve:eveselect>
			</td>
			<td style="text-align: center;">				
				<eve:eveselect clazz="tab_text zbdwSelect validate[required]" dataParams="TENDERTYPENUM"
				dataInterface="dictionaryService.findDatasForSelect" value="${zbdw.TENDERTYPENUM}"
				defaultEmptyText="请选择招标方式" name="TENDERTYPENUM">
				</eve:eveselect>
			</td>
			<td style="text-align: center;">${zbdw.BIDDINGUNITNAME}</td>
			<td style="text-align: center;">${zbdw.TENDERMONEY}</td>
			<td style="text-align: center;">${zbdw.CORPNAME}</td>
			<td style="text-align: center;">${zbdw.CORPCREDITCODE}</td>
			<td style="text-align: center;">${zbdw.ORGCODE}</td>
			<td style="text-align: center;">${zbdw.LEGAL_NAME}</td>
			<td style="text-align: center;">${zbdw.PERSONNAME}</td>
			<td style="text-align: center;">${zbdw.PERSONPHONE}</td>
			<td style="text-align: center;">
			<a href="javascript:void(0);" class="eflowbutton" onclick="getDwInfo('zbdw','zbdwTr_${s.index}');">查 看</a>
			</td>
		</tr>
	</c:forEach>
</table>
