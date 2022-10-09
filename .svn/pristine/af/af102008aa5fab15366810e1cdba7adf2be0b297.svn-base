<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>
<%
    SysUser sysUser = AppUtil.getLoginUser();
    //获取登录用户的角色CODE
    Set<String> roleCodes = sysUser.getRoleCodes();
    //获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否经营范围备注描述管理员
    boolean isJyfw = roleCodes.contains("JYFW_ALL");
    //判断是否超级管理员或经营范围备注描述管理员
    if ("__ALL".equals(resKey) || isJyfw) {
        request.setAttribute("isJyfwAll", true);
    } else {
        request.setAttribute("isJyfwAll", false);
    }
%>
<style>
.eflowbutton {
	background: #3a81d0;
	border: none;
	padding: 0 10px;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #fff;
	border-radius: 5px;
}

.eflowbutton-disabled {
	background: #94C4FF;
	border: none;
	padding: 0 10px;
	line-height: 26px;
	cursor: pointer;
	height: 26px;
	color: #E9E9E9;
	border-radius: 5px;
}

.selectType {
	margin-left: -100px;
}
</style>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">企业基本信息
		</td>
	</tr>
	<tr id="unicode"
		<c:if test="${busRecord.SOCIAL_CREDITUNICODE==null || busRecord.SOCIAL_CREDITUNICODE==''}">style="display: none;"</c:if>>
		<td class="tab_width"><font class="tab_color">*</font>社会信用代码：</td>
		<td colspan="3"><input type="text"
			class="tab_text validate[required]" name="SOCIAL_CREDITUNICODE"
			maxlength="32" value="${busRecord.SOCIAL_CREDITUNICODE }" /></td>
	</tr>
	<tr>
		<td class="tab_width">集团名称：</td>
		<td>
			<input type="text" class="tab_text"  maxlength="256"
				   name="GROUP_NAME" value="${busRecord.GROUP_NAME }"/>
		</td>
		<td class="tab_width"> 集团简称：</td>
		<td><input type="text" class="tab_text" maxlength="128"
				   name="GROUP_ABBRE" value="${busRecord.GROUP_ABBRE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
		<td colspan="3"><input type="text"
			class="tab_text validate[required]" name="COMPANY_NAME"
			maxlength="64" value="${busRecord.COMPANY_NAME }" /></td>
	</tr>
<%--	<tr style="display: none;">--%>
<%--		<td class="tab_width"><font class="tab_color">*</font>是否已通过名称自主选用：</td>--%>
<%--		<td><input type="radio" name="IS_PREAPPROVAL_PASS" value="1"--%>
<%--			<c:if test="${busRecord.IS_PREAPPROVAL_PASS==1 }">checked="checked"</c:if>>是--%>
<%--			<input type="radio" name="IS_PREAPPROVAL_PASS" value="0"--%>
<%--			<c:if test="${busRecord.IS_PREAPPROVAL_PASS==0 }">checked="checked"</c:if>>否--%>
<%--		</td>--%>
<%--		<td class="tab_width">名称自主选用文号：</td>--%>
<%--		<td><input type="text" class="tab_text" disabled="disabled"--%>
<%--			maxlength="32" name="PREAPPROVAL_NUM"--%>
<%--			value="${busRecord.PREAPPROVAL_NUM }" /></td>--%>
<%--	</tr>--%>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业类别：</td>
		<td><eve:eveselect clazz="tab_text validate[required]"
				dataParams="hhqylb"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择企业类别" name="COMPANY_CATEGORY"
				value="${busRecord.COMPANY_CATEGORY }">
			</eve:eveselect></td>
		<td style="width: 170px;background: #fbfbfb;"><font
			class="tab_color">*</font>公司类型：</td>
		<td><input name="COMPANY_TYPE"
			class="tab_text validate[required]"
			" value="${busRecord.COMPANY_TYPE }" /></td>
	</tr>

	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注册地址：
		</th>
			<td colspan="3"><input type="text"
			class="tab_text validate[required]" style="width:740px;"
			name="REGISTER_ADDR" maxlength="128" placeholder="请输入注册地址"
			value="${busRecord.REGISTER_ADDR}" /></td>
		</tr>



	<script type="text/javascript">
		function setSendLawAddr(val) {
			if(val=='1'){
				$("input[name='LAW_SEND_ADDR']").val($("input[name='REGISTER_ADDR']").val());
				$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
			}else{
				$("input[name='LAW_SEND_ADDR']").attr("disabled","");
			}
		}
	</script>
	<tr>
		<td class="tab_width">法律文书送达地址(同注册地址)：</td>
		<td >
			<input type="radio" class="radio " name="IS_REGISTER_ADDR" value="1" onclick="setSendLawAddr('1');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
			<input type="radio" class="radio " name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
					<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
		</td>
		<td class="tab_width">法律文书送达地址: </td>
		<td >
			<input type="text" class="tab_text"
				   name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">法律文书电子送达地址：</td>
		<td colspan="3">
			邮箱：<input type="text" class="tab_text "
					  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
			<span style="margin-left: 50px;">传真：</span><input type="text" class="tab_text "
															  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
			<span style="margin-left: 50px;">即时通讯账号（如微信）：</span><input type="text" class="tab_text "
																	   name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
		</td>
	</tr>



	<tr>
			<td><font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input type="text" class="tab_text validate[required]"
			name="REGISTERADD_OWNER" placeholder="请输入房屋所有权人名称" maxlength="32"
			value="${busRecord.REGISTERADD_OWNER}" /></td>
			<td><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input type="text" class="tab_text validate[required]"
			name="REGISTERADD_TEL" placeholder="请输入房屋所有权人联系电话" maxlength="32"
			value="${busRecord.REGISTERADD_TEL}" /></td>

		</tr>
		<tr>
			<td><font class="syj-color2">*</font>是否另设生产经营地址：</td>
			<td colspan="3">
					<input type="radio" class="radio validate[required]"
			name="IS_OTHER_PLACE" value="1"
			<c:if test="${busRecord.IS_OTHER_PLACE==1}"> checked="checked"</c:if> />是
					<input type="radio" class="radio validate[required]"
			name="IS_OTHER_PLACE" value="0"
			<c:if test="${busRecord.IS_OTHER_PLACE==0}"> checked="checked"</c:if> />否
				</td>
		</tr>
		<tr>
			<td><font class="syj-color2">*</font>生产经营地址：</td>
			<td colspan="3"><input disabled="false" type="text"
			class="tab_text validate[required]" style="width:740px;"
			name="BUSINESS_PLACE" maxlength="64" placeholder="请输入生产经营地址"
			value="${busRecord.BUSINESS_PLACE}" /></td>
		</tr>
		<tr>
			<td><font class="syj-color2">*</font>房屋所有权人名称：</td>
			<td><input disabled="false" type="text"
			class="tab_text validate[required]" name="PLACE_OWNER"
			placeholder="请输入房屋所有权人名称" maxlength="32"
			value="${busRecord.PLACE_OWNER}" /></td>
			<td><font class="syj-color2">*</font>房屋所有权人联系电话：</td>
			<td><input disabled="false" type="text"
			class="tab_text validate[required]" name="PLACE_TEL"
			placeholder="请输入房屋所有权人联系电话" maxlength="32"
			value="${busRecord.PLACE_TEL}" /></td>
		</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>住所产权：</td>
		<td><eve:eveselect clazz="tab_text validate[required]"
				dataParams="propertyRight"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择住所产权" name="RESIDENCE_PROPERTY_RIGHT"
				value="${busRecord.RESIDENCE_PROPERTY_RIGHT}">
			</eve:eveselect></td>
		<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
		<td><input type="text" class="tab_text  validate[required]"
			maxlength="16" placeholder="请输入联系电话" name="CONTACT_NUMBER"
			value="${busRecord.CONTACT_NUMBER}" /></td>

		<input type="hidden" placeholder="请输入邮政编码" name="POSTAL_CODE"
			value="${busRecord.POSTAL_CODE}" />
	</tr>

	<tr>
		<td class="tab_width">网址：</td>
		<td colspan="3"><input type="text"
			class="tab_text validate[required],custom[url]" style="width:740px;"
			name="WEBSITE" maxlength="128" placeholder="请输入网址"
			value="${busRecord.WEBSITE}" /></td>
	</tr>
	<tr>
		<td class="tab_width">指定报纸：</td>
		<td colspan="3"><input type="text"
			class="tab_text validate[required]" style="width:740px;"
			name="COMPANY_NEWSPAPER" maxlength="128" placeholder="请输入指定报纸"
			value="${busRecord.COMPANY_NEWSPAPER}" /></td>
	</tr>
			
	<tr>
		<td class="tab_width">设立方式：</td>
		<td colspan="3"><input type="radio" name="ESTABLISH_MODE"
			value="1"
			<c:if test="${busRecord.ESTABLISH_MODE==1}"> checked="checked"</c:if>>发起设立
			<input type="radio" name="ESTABLISH_MODE" value="2"
			<c:if test="${busRecord.ESTABLISH_MODE==2}"> checked="checked"</c:if>>募集设立
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注册资本(万元)：</td>
		<td colspan="3"><input type="text"
			class="tab_text  validate[required],custom[JustNumber]"
			name="REGISTERED_NUM" placeholder="请输入注册资本(万元)"
			value="${busRecord.REGISTERED_NUM}" /></td>
			<!-- 
		<td class="tab_width">实收资本(万元)：</td>
		<td><input type="text"
			class="tab_text  validate[],custom[JustNumber]" name="COLLECTION_NUM"
			placeholder="请输入实收资本(万元)" value="${busRecord.COLLECTION_NUM}" /></td> -->
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>从业人数：</td>
		<td colspan="3"><input type="text"
			class="tab_text validate[required,custom[JustNumber]]"
			style="width:740px;" name="STAFF_NUM" placeholder="请输入从业人数"
			value="${busRecord.STAFF_NUM}" /></td>
	</tr>
	<!-- 
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>副本数：</td>
		<td><input type="text"
			class="tab_text  validate[required],custom[JustNumber]"
			name="REPLICAS_NUM" placeholder="请输入副本数"
			value="${busRecord.REPLICAS_NUM}" /></td>
		<td class="tab_width">电子营业执照数：</td>
		<td><input type="text"
			class="tab_text  validate[required],custom[JustNumber]"
			name="BUSINESS_LICENSES_NUM" placeholder="请输入电子营业执照数"
			value="${busRecord.BUSINESS_LICENSES_NUM}" /></td>
	</tr> -->

	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营期限：</td>
		<td><input type="radio" name="OPRRATE_TERM_TYPE" value="1"
			<c:if test="${busRecord.OPRRATE_TERM_TYPE==1 }">checked="checked"</c:if>>年
			<input type="radio" name="OPRRATE_TERM_TYPE" value="0"
			<c:if test="${busRecord.OPRRATE_TERM_TYPE==0 }">checked="checked"</c:if>>长期
		</td>
		<td class="tab_width">经营期限年数：</td>
		<td><input type="text"
			class="tab_text validate[[],custom[integerplus]]"
			name="BUSSINESS_YEARS" value="${busRecord.BUSSINESS_YEARS }" />年</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
		<td colspan="3"><textarea rows="3" cols="200"
				name="BUSSINESS_SCOPE" readonly="readonly"
				class="eve-textarea validate[validate[required],maxSize[2000]]"
				style="width:740px;height:100px;">${busRecord.BUSSINESS_SCOPE }</textarea>
			<input type="hidden" name="BUSSINESS_SCOPE_ID"
			value="${busRecord.BUSSINESS_SCOPE_ID }"> <input
			type="button" name="scopeChose" value="选择"
			onclick="showSelectJyfwNew();"> <!-- <input type="button" name="scopeChose" value="选择" onclick="choseScope()"> -->
		</td>
	</tr>
<!-- 
	<tr>
		<td class="tab_width">经营范围备注描述：</td>
		<td colspan="3"><input type="text" class="tab_text"
			style="width:740px;"
			<c:if test="${isJyfwAll == false}">disabled="disabled"</c:if>
			name="SCOPE_REMARK" maxlength="128"
			value="${busRecord.SCOPE_REMARK }" /> <c:if
				test="${isJyfwAll == true}">
				<a class="eflowbutton" onclick="saveScopeRemark();"
					style="height: 28px;float: none;">保存</a>
			</c:if></td>
	</tr> -->
	<tr>
		<td class="tab_width">投资行业：</td>
		<td colspan="3"><textarea rows="3" cols="200"
				name="INVEST_INDUSTRY" readonly="readonly"
				class="eve-textarea validate[validate[],maxSize[2000]]"
				style="width:740px;height:100px;">${busRecord.INVEST_INDUSTRY }</textarea>
			<input type="hidden" name="INVEST_INDUSTRY_ID"
			value="${busRecord.INVEST_INDUSTRY_ID }"> 
			<!-- <input type="button" name="industryChose" value="选择" onclick="showSelectTzhy()"> -->
			<!-- <input type="button" name="industryChose" value="选择" onclick="choseIndustry()"> -->
		</td>
	</tr>
	<tr>
		<td class="tab_width">行业编码：</td>
		<td colspan="3"><input type="text" class="tab_text "
			style="width:740px;" readonly="readonly" name="INDUSTRY_CODE"
			value="${busRecord.INDUSTRY_CODE }" /></td>
	</tr>
</table>
