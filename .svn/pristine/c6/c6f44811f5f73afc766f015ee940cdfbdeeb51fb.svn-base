<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<style>
	.field_width{width:192px;}
	.lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
	.inputBackgroundCcc{
		background-color:#ccc;
	}
</style>
<input type="hidden" id= "YW_ID" value="${busRecord.YW_ID}"/>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">	
	<tr>
		<th colspan="4">项目基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 投资项目编号</td>
		<td colspan="3">
			<input type="text" maxlength="32" id="PROJECT_CODE" value="${busRecord.PROJECT_CODE}"
					class="tab_text validate[required]" name="PROJECT_CODE" />
			<input type="button" value="校验" class="eve-button" id="loadData" onclick="loadTzxmxxData()" style="margin-top: 3px;margin-left: 5px;">
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 省投资项目编号</td>
		<td>
				<input type="text" maxlength="32" id="STZXM_PROJECT_CODE" readonly="readonly"
				class="tab_text validate[required]" name="STZXM_PROJECT_CODE"  value="${busRecord.PROJECT_CODE}"/>
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 项目名称</td>
		<td>
			<input type="text" maxlength="64" 
			class="tab_text validate[required]" value="${busRecord.PROJECT_NAME}" name="PROJECT_NAME" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"> 产权证明</td>
		<td>
				<input type="text" maxlength="32" id="CQZM"
				class="tab_text validate[]" name="CQZM"  value="${busRecord.CQZM}"/>
		</td>
		<td class="tab_width"> 红线图</td>
		<td>
			<input type="text" maxlength="64" 
			class="tab_text validate[]" value="${busRecord.HXT}" name="HXT" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"> 建设工程规划许可证</td>
		<td>
				<input type="text" maxlength="32"
				class="tab_text validate[]" name="PROJECTPLANNUM"  value="${busRecord.PROJECTPLANNUM}"/>
		</td>
		<td class="tab_width"> 建设用地规划许可证</td>
		<td>
			<input type="text" maxlength="32" 
			class="tab_text validate[]" value="${busRecord.BULDPLANNUM}" name="BULDPLANNUM" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 项目资金属性</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required]" dataParams="PROJECTATTRIBUTES"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PROJECT_ATTRIBUTES}"
			defaultEmptyText="请选择项目资金属性" name="PROJECT_ATTRIBUTES" id="PROJECT_ATTRIBUTES">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 项目所属区划</td>
		<td>
			<input type="text" maxlength="6" 
			class="tab_text validate[required]" value="${busRecord.DIVISION_CODE}" name="DIVISION_CODE" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 立项类型</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required] field_width" dataParams="PROJECTTYPE"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PROJECT_TYPE}"
				defaultEmptyText="请选择立项类型" name="PROJECT_TYPE" id="projectType">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 建设性质</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required] " dataParams="PROJECTNATURE"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PROJECT_NATURE}"
				defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="PROJECT_NATURE">
			</eve:eveselect>
		</td>
	</tr>		
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 拟开工时间</td>
		<td>
				<input readonly="true" id="startYear" type="text"  class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR"  value="${busRecord.START_YEAR}"/>
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 拟建成时间</td>
		<td>
			<input readonly="true" id="endYear" type="text" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR" value="${busRecord.END_YEAR}" />
		</td>
	</tr>		
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 国标行业</td>
		<td>
			<select id="industry" name="INDUSTRY" class="tab_text whf_input validate[required] " style="width:186px;">
			<option value="">请选择国标行业</option>
			</select>
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 国标行业代码发布年代</td>
		<td>
			<input type="text" maxlength="16" readonly="readonly"
			class="tab_text validate[]" value="2017" name="GBHYDMFBND" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 所属行业</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required]" dataParams="PERMITINDUSTRY"
			dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.PERMIT_INDUSTRY}"
			defaultEmptyText="请选择所属行业" name="PERMIT_INDUSTRY" id="PERMIT_INDUSTRY">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 是否外商投资/境外投资</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required] " dataParams="YesOrNo"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.FOREIGN_ABROAD_FLAG}"
				defaultEmptyText="请选择" name="FOREIGN_ABROAD_FLAG" id="FOREIGN_ABROAD_FLAG">
			</eve:eveselect>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 总投资额（万元）</td>
		<td>								
			<input  id="totalMoney" type="text"
			class="tab_text validate[required]" maxlength="16" name="TOTAL_MONEY"  value="${busRecord.TOTAL_MONEY}" />	
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 申报时间</td>
		<td>
			<input readonly="true" id="APPLY_DATE" type="text" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"  name="APPLY_DATE"  value="${busRecord.APPLY_DATE}" />
		</td>
	</tr>					
	<tr>
		<td class="tab_width"> 总投资额为“0”时说明</td>
		<td  colspan="3">
			<textarea id="totalMoneyExplain" class="tab_text validate[required],maxSize[512]" rows="3" cols="200"   
			  name="TOTAL_MONEY_EXPLAIN"  style="width:70%;height:150px;"  >${busRecord.TOTAL_MONEY_EXPLAIN}</textarea>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 建设地点</td>
		<td>	
			<select id="placeCode" name="PLACE_CODE" class="tab_text whf_input validate[required] ">
				<option value="">请选择建设地点</option>
			</select>
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 建设地点详情</td>
		<td>
			<input id="PLACE_CODE_DETAIL" type="text" maxlength="16" 
			class="tab_text validate[required]"   name="PLACE_CODE_DETAIL"  value="${busRecord.PLACE_CODE_DETAIL}" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 项目建设地点X坐标</td>
		<td>								
			<input  id="LATITUDE" type="text"
			class="tab_text validate[],custom[JustNumber]" maxlength="32" name="LATITUDE"  value="${busRecord.LATITUDE}" />	
		</td>
		<td class="tab_width"> 项目建设地点Y坐标</td>
		<td>
			<input  id="LONGITUDE" type="text"
			class="tab_text validate[],custom[JustNumber]" maxlength="32" name="LONGITUDE"  value="${busRecord.LONGITUDE}" />	
		</td>
	</tr>						
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 项目地址</td>
		<td  colspan="3">
			<input id="PROJECT_SITE" type="text" maxlength="64" style="width:70%;"
			class="tab_text validate[required]"   name="PROJECT_SITE"  value="${busRecord.PROJECT_SITE}" />
		</td>
	</tr>				
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 建设规模及内容</td>
		<td  colspan="3">
			<textarea id="SCALE_CONTENT" class="tab_text validate[required],maxSize[512]" rows="3" cols="200"  
			  name="SCALE_CONTENT"  style="width:70%;height:150px;">${busRecord.SCALE_CONTENT}</textarea>
		</td>
	</tr>		
	<tr>	
		<td class="tab_width"> 项目类型</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[]" dataParams="XMTZLY"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.XMTZLY}"
				defaultEmptyText="请选择项目类型" name="XMLX" id="XMLX">
			</eve:eveselect>
		</td>	
		<td class="tab_width"> 是否完成区域评估</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[]" dataParams="SFWCQYPG"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.SFWCQYPG}"
				defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
			</eve:eveselect>
		</td>	
	</tr>	
	<tr>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 总用地面积（平方米）</td>
		<td>								
			<input  id="LAND_AREA" type="text"
			class="tab_text validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" maxlength="16" name="LAND_AREA"  value="${busRecord.LAND_AREA}" />	
		</td>
		<td class="tab_width"><font class="dddl_platform_html_requiredFlag">*</font> 总建筑面积（平方米）</td>
		<td>
			<input  id="BUILT_AREA" type="text"
			class="tab_text validate[required],custom[JustNumber]" onblur="onlyNumber3(this);" onkeyup="onlyNumber3(this);" maxlength="16" name="BUILT_AREA"  value="${busRecord.BUILT_AREA}" />	
		</td>
	</tr>
	<tr>	
		<td class="tab_width"><font class="tab_color">*</font> 投资来源</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required]" dataParams="XMTZLY"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.XMTZLY}"
				defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
			</eve:eveselect>
		</td>	
		<td class="tab_width"> 工程分类</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[]" dataParams="GCFL"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.GCFL}"
				defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
			</eve:eveselect>
		</td>	
	</tr>		
	<tr>	
		<td class="tab_width"> 是否重点项目</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[]" dataParams="YesOrNo"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.ISKEYPRO}"
				defaultEmptyText="请选择土地是否带设计方案" name="ISKEYPRO" id="ISKEYPRO">
			</eve:eveselect>
		</td>	
		<td class="tab_width"> 公开方式</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[]" dataParams="OPENWAY"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.OPEN_WAY}"
				defaultEmptyText="请选择是否完成区域评估" name="OPEN_WAY" id="OPEN_WAY">
			</eve:eveselect>
		</td>	
	</tr>	
	<tr>	
		<td class="tab_width"> 土地获取方式</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[]" dataParams="getLandMode"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.GET_LAND_MODE}"
				defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
			</eve:eveselect>
		</td>	
		<td class="tab_width"> 土地是否带设计方案</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[]" dataParams="TDSFDSJFA"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.TDSFDSJFA}"
				defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
			</eve:eveselect>
		</td>	
	</tr>		
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="xmdwxxTable">	
	<tr>
		<th colspan="4">业主单位信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 单位名称</td>
		<td >
			<input type="text" maxlength="100" 
			class="tab_text validate[required]" name="ENTERPRISE_NAME"  value="${busRecord.ENTERPRISE_NAME}"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 单位类型</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required]" dataParams="DWLX"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.DWLX}"
				defaultEmptyText="请选择单位类型" name="DWLX" id="DWLX">
			</eve:eveselect>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 证件类型</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required]" dataParams="LEREPCERTTYPE"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.LEREP_CERTTYPE}"
				defaultEmptyText="请选择证件类型" name="LEREP_CERTTYPE" id="LEREP_CERTTYPE">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 证件号码</td>
		<td>
			<input type="text" maxlength="32" 
			class="tab_text validate[required]" name="LEREP_CERTNO"  value="${busRecord.LEREP_CERTNO}"/>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 经办人姓名</td>
		<td>
			<input type="text" maxlength="16" 
			class="tab_text validate[required]" name="CONTACT_NAME"  value="${busRecord.CONTACT_NAME}"/>
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 经办人手机</td>
		<td>
			<input type="text" maxlength="16" 
			class="tab_text validate[required]" name="CONTACT_PHONE"  value="${busRecord.CONTACT_PHONE}"/>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 经办人证件类型</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect" value="${busRecord.CONTACT_CERTTYPE}" 
				onchange="setZjValidate(this.value,'CONTACT_CERTNO');"
				defaultEmptyText="请选择证件类型" name="CONTACT_CERTTYPE" id="CONTACT_CERTTYPE">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 经办人证件号码</td>
		<td>
			<input type="text" maxlength="32"  value="${busRecord.CONTACT_CERTNO}"
			class="tab_text validate[required]" name="CONTACT_CERTNO" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"> 经办人邮箱</td>
		<td>
			<input type="text" maxlength="32"  value="${busRecord.CONTACT_EMAIL}"
			class="tab_text validate[]" name="CONTACT_EMAIL" />
		</td>
		<td class="tab_width"> 单位联系电话</td>
		<td>
			<input type="text" maxlength="16"  value="${busRecord.CONTACT_TEL}"
			class="tab_text validate[]" name="CONTACT_TEL" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 法人代表</td>
		<td colspan="3">
			<input type="text" maxlength="100"   value="${busRecord.LEREP_REPRESENTATIVE}"
			class="tab_text validate[required]" name="LEREP_REPRESENTATIVE" />
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 法人证件类型</td>
		<td>
			<eve:eveselect clazz="tab_text whf_input validate[required]" dataParams="DocumentType"
				dataInterface="dictionaryService.findDatasForSelect"  value="${busRecord.LEREP_REPRESENTATIVE_CERTTYPE}"  onchange="setZjValidate(this.value,'LEREP_REPRESENTATIVE_CERTNO');"
				defaultEmptyText="请选择证件类型" name="LEREP_REPRESENTATIVE_CERTTYPE" id="LEREP_REPRESENTATIVE_CERTTYPE">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 法人证件号码</td>
		<td>
			<input type="text" maxlength="32"  value="${busRecord.LEREP_REPRESENTATIVE_CERTNO}"
			class="tab_text validate[required]" name="LEREP_REPRESENTATIVE_CERTNO" />
		</td>
	</tr>
</table>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->