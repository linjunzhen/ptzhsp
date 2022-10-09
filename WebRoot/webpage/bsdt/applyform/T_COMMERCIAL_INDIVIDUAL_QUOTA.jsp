<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">个体工商户定额信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>纳税人识别号：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]"
			name="IDENTIFY_NO" maxlength="32"  value="${busRecord.IDENTIFY_NO}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>定额项目：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]"
			name="QUOTA_PROJECT" maxlength="64" value="${busRecord.QUOTA_PROJECT}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>资产投资总额：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="INVESTMENT" maxlength="8" value="${busRecord.INVESTMENT}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>经营面积：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="OPERATE_AREA" maxlength="8" value="${busRecord.OPERATE_AREA}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>主要经营用具及台（套）数：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="TOOLANDNUM" maxlength="8" value="${busRecord.TOOLANDNUM}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>月发票开具额：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="INVOICE_ISSUE" maxlength="8" value="${busRecord.INVOICE_ISSUE}"/></td>
	</tr>
	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>年房屋租金：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="HOUSE_RENT" maxlength="8" value="${busRecord.HOUSE_RENT}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>仓储面积：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="STORAGE_AREA" maxlength="8" value="${busRecord.STORAGE_AREA}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>所属乡镇、街道：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="TOWNSHIP" maxlength="62" value="${busRecord.TOWNSHIP}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>所属集贸市场：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="MARKET" maxlength="32" value="${busRecord.MARKET}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营方式：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="BUSSINESS_MODE" maxlength="32" value="${busRecord.BUSSINESS_MODE}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>代理品牌数量：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="AGENT_NUM" maxlength="4" value="${busRecord.AGENT_NUM}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>淡季旺季情况：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="SLACK_SEASON" maxlength="32" value="${busRecord.SLACK_SEASON}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>代理区域：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="AGENT_AREA" maxlength="32" value="${busRecord.AGENT_AREA}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>交通工具：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="TRANSPORT" maxlength="32" value="${busRecord.TRANSPORT}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>所属路段：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="BELONG_ROAD" maxlength="32" value="${busRecord.BELONG_ROAD}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营年限：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="BUSINESS_YEAR" maxlength="4" value="${busRecord.BUSINESS_YEAR}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>广告类别：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="AD_TYPE" maxlength="32" value="${busRecord.AD_TYPE}"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>信誉程度：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="REPUTATION" maxlength="32" value="${busRecord.REPUTATION}"/></td>
		<td class="tab_width"> 其他项目：</td>
		<td><input type="text" class="tab_text "
			name="OTHER_PROJECT" maxlength="32" value="${busRecord.OTHER_PROJECT}"/></td>
	</tr>
	<tr>
		<td class="tab_width"> “其他项目”补充说明：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="SUPPLEMENT" readonly="readonly"
				class="eve-textarea validate[[],maxSize[256]]"
			 	style="width:740px;height:80px;" >${busRecord.SUPPLEMENT }</textarea> 
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>采集时间：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" disabled="disabled"
			name="COLLECT_TIME" value="${busRecord.COLLECT_TIME}"/></td>
	</tr>
</table>
