<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form action="" id="PERSONNEL_FORM"  >
	
	<div class="syj-title1 ">
		<span>个体工商户定额信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">	
			<tr>
				<th ><font class="syj-color2">*</font>纳税人识别号：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required]"
					name="IDENTIFY_NO" maxlength="32"  value="${busRecord.IDENTIFY_NO}" placeholder="请输入纳税人识别号"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>定额项目：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required]"
					name="QUOTA_PROJECT" maxlength="64" value="${busRecord.QUOTA_PROJECT}" placeholder="请输入定额项目"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>资产投资总额：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="INVESTMENT" maxlength="8" value="${busRecord.INVESTMENT}" placeholder="请输入资产投资总额"/></td>
				<th ><font class="syj-color2">*</font>经营面积：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="OPERATE_AREA" maxlength="8" value="${busRecord.OPERATE_AREA}" placeholder="请输入经营面积"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>主要经营用具及台（套）数：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="TOOLANDNUM" maxlength="8" value="${busRecord.TOOLANDNUM}" placeholder="请输入主要经营用具及台（套）数"/></td>
				<th ><font class="syj-color2">*</font>月发票开具额：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="INVOICE_ISSUE" maxlength="8" value="${busRecord.INVOICE_ISSUE}" placeholder="请输入月发票开具额"/></td>
			</tr>
			
			<tr>
				<th ><font class="syj-color2">*</font>年房屋租金：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="HOUSE_RENT" maxlength="8" value="${busRecord.HOUSE_RENT}" placeholder="请输入年房屋租金"/></td>
				<th ><font class="syj-color2">*</font>仓储面积：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="STORAGE_AREA" maxlength="8" value="${busRecord.STORAGE_AREA}" placeholder="请输入仓储面积"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>所属乡镇、街道：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="TOWNSHIP" maxlength="62" value="${busRecord.TOWNSHIP}" placeholder="请输入所属乡镇、街道"/></td>
				<th ><font class="syj-color2">*</font>所属集贸市场：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="MARKET" maxlength="32" value="${busRecord.MARKET}" placeholder="请输入所属集贸市场"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>经营方式：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="BUSSINESS_MODE" maxlength="32" value="${busRecord.BUSSINESS_MODE}" placeholder="请输入经营方式"/></td>
				<th ><font class="syj-color2">*</font>代理品牌数量：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="AGENT_NUM" maxlength="4" value="${busRecord.AGENT_NUM}" placeholder="请输入代理品牌数量"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>淡季旺季情况：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="SLACK_SEASON" maxlength="32" value="${busRecord.SLACK_SEASON}" placeholder="请输入淡季旺季情况"/></td>
				<th ><font class="syj-color2">*</font>代理区域：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="AGENT_AREA" maxlength="32" value="${busRecord.AGENT_AREA}" placeholder="请输入代理区域"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>交通工具：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="TRANSPORT" maxlength="32" value="${busRecord.TRANSPORT}" placeholder="请输入交通工具"/></td>
				<th ><font class="syj-color2">*</font>所属路段：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="BELONG_ROAD" maxlength="32" value="${busRecord.BELONG_ROAD}" placeholder="请输入所属路段"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>经营年限：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="BUSINESS_YEAR" maxlength="4" value="${busRecord.BUSINESS_YEAR}" placeholder="请输入经营年限"/></td>
				<th ><font class="syj-color2">*</font>广告类别：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="AD_TYPE" maxlength="32" value="${busRecord.AD_TYPE}" placeholder="请输入广告类别"/></td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>信誉程度：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="REPUTATION" maxlength="32" value="${busRecord.REPUTATION}" placeholder="请输入信誉程度"/></td>
				<th > 其他项目：</th>
				<td><input type="text" class="syj-input1 "
					name="OTHER_PROJECT" maxlength="32" value="${busRecord.OTHER_PROJECT}" placeholder="请输入其他项目"/></td>
			</tr>
			<tr>
				<th > “其他项目”补充说明：</th>
				<td colspan="3">
					<textarea rows="3" cols="200" name="SUPPLEMENT" 
						class="eve-textarea w878 validate[[],maxSize[256]]"
						style="height:80px;"  placeholder="请输入“其他项目”补充说明">${busRecord.SUPPLEMENT }</textarea> 
				</td>
			</tr>
			<tr>
				<th ><font class="syj-color2">*</font>采集时间：</th>
				<td colspan="3"><input type="text" class="syj-input1 w878 validate[required]" disabled="disabled"
					name="COLLECT_TIME" value="${busRecord.COLLECT_TIME}" placeholder="请输入采集时间"/></td>
			</tr>
		</table>
	</div>	
</form>
