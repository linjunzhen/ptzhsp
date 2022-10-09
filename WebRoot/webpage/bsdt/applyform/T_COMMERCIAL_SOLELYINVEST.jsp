<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@page import="net.evecom.core.util.AppUtil"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
	SysUser sysUser = AppUtil.getLoginUser();
	//获取登录用户的角色CODE
	Set<String> roleCodes = sysUser.getRoleCodes();
	//获取菜单KEY
    String resKey = sysUser.getResKeys();
    //判断是否经营范围备注描述管理员
    boolean isJyfw = roleCodes.contains("JYFW_ALL");
	//判断是否超级管理员或经营范围备注描述管理员
	if("__ALL".equals(resKey)||isJyfw){
	    request.setAttribute("isJyfwAll",true);
	}else{
	    request.setAttribute("isJyfwAll",false);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<eve:resources
	loadres="jquery,easyui,apputil,laydate,validationegine,artdialog,swfupload,layer"></eve:resources>
<script type="text/javascript"
	src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/json-2.0/json2.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/common/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/webpage/bsdt/applyform/css/applyform.css" />
<script type="text/javascript"
	src="<%=basePath%>/webpage/bsdt/applyform/js/solely.js"></script>

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
</style>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_COMMERCIAL_SOLELYINVEST_FORM" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="SUPERVISOR_JSON" />
		<input type="hidden" name="MANAGER_JSON" />
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td > 审批事项：</td>
				<td >${serviceItem.ITEM_NAME}</td>
	            <td style="width: 170px;background: #fbfbfb;"> 承诺时间(工作日)：</td>
	            <td >${serviceItem.CNQXGZR}</td>
	        </tr>
			<tr>
				<td > 牵头部门：</td>
				<td >${serviceItem.SSBMMC}</td>	
				<td style="width: 170px;background: #fbfbfb;"> 审批类型：</td>
				<td>${serviceItem.SXLX}</td>
			</tr>
			
			<tr>
				<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font> 申报名称：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					style="width: 70%" maxlength="120"
					name="SBMC"  value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
			</tr>
			<tr>
				<td style="width: 170px;background: #fbfbfb;"> 申报号：</td>
				<td id = "EXEID" colspan="3"></td>
			</tr>
			<tr id="jjgl" style="display:none;">
				<td style="width: 170px;background: #fbfbfb;"> 1+N证合一事项：</td>
				<td colspan="3" id="relatedItem"></td>
			</tr>			
		</table>
		<div class="tab_height"></div>		
		<div id="commercial">
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">企业基本信息</th>
			</tr>
			<tr id="unicode" <c:if test="${busRecord.SOCIAL_CREDITUNICODE==null || busRecord.SOCIAL_CREDITUNICODE==''}">style="display: none;"</c:if>>
				<td class="tab_width"><font class="tab_color">*</font>社会信用代码：</td>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" 
						name="SOCIAL_CREDITUNICODE" maxlength="32" value="${busRecord.SOCIAL_CREDITUNICODE }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>名称：</td>
				<td colspan="3">
					<input type="text" class="tab_text validate[required]" style="width:740px;"
						name="COMPANY_NAME" maxlength="32" value="${busRecord.COMPANY_NAME }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> 备选名称1：</td>
				<td colspan="3">
					<input type="text" class="tab_text" style="width:740px;"
						name="SPARE_NAME1" maxlength="32" value="${busRecord.SPARE_NAME1 }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> 备选名称2：</td>
				<td colspan="3">
					<input type="text" class="tab_text" style="width:740px;"
						name="SPARE_NAME2" maxlength="32" value="${busRecord.SPARE_NAME2 }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>名称预先核准文号/注册号/统一社会信用代码：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="32"
					name="CREDIT_CODE" value="${busRecord.CREDIT_CODE }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>企业住所：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
					name="COMPANY_ADDR" maxlength="64"  value="${busRecord.COMPANY_ADDR }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>生产经营地址：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
					name="BUSINESS_ADDR" maxlength="64"  value="${busRecord.BUSINESS_ADDR }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="PHONE"  value="${busRecord.PHONE }"/></td>
				
				<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
				<td><input type="text" class="tab_text validate[required,custom[chinaZip]]"
					name="POST_CODE"  value="${busRecord.POST_CODE }"/></td>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">设立</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>出资额：</td>
				<td>
					<input type="text" class="tab_text validate[required,custom[numberp6plus]]"
						name="INVESTMENT" maxlength="8" value="${busRecord.INVESTMENT }"/>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>从业人数：</td>
				<td>
					<input type="text" class="tab_text validate[required,custom[integerplus]]"
						name="EMPLOYE_NUM" maxlength="4"  value="${busRecord.EMPLOYE_NUM }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> 出资方式：</td>
				<td >
					<input type="radio" name="INVEST_TYPE" value="1" <c:if test="${busRecord.INVEST_TYPE=='1' }">checked="checked"</c:if> >以个人财产出资
					<input type="radio" name="INVEST_TYPE" value="2" <c:if test="${busRecord.INVEST_TYPE=='2' }">checked="checked"</c:if> >以家庭共有财产作为个人出资
				</td>
				<td class="tab_width"> 申领电子营业执照：</td>
				<td>
					<input type="radio" name="LICENSE_APPLY" value="1" <c:if test="${busRecord.LICENSE_APPLY=='1' }">checked="checked"</c:if> >是
					<input type="radio" name="LICENSE_APPLY" value="0" <c:if test="${busRecord.LICENSE_APPLY=='0' }">checked="checked"</c:if> >否
				</td>
			</tr>
			<tr>
				<td class="tab_width"> 申领纸质营业执照：</td>
				<td>
					<input type="radio" name="LICENSE_PAPER_APPLY" value="1" <c:if test="${busRecord.LICENSE_PAPER_APPLY=='1' }">checked="checked"</c:if> >是
					<input type="radio" name="LICENSE_PAPER_APPLY" value="0" <c:if test="${busRecord.LICENSE_PAPER_APPLY=='0' }">checked="checked"</c:if> >否
				</td>
				<td class="tab_width"><font class="tab_color">*</font>申领纸质营业执照数量：</td>
				<td>
					<input type="text" class="tab_text validate[custom[integerplus]]"
						name="LICENSE_PAPER_NUM" maxlength="4"  value="${busRecord.LICENSE_PAPER_NUM }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width">出资人的家庭成员：</td>
				<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
					name="INVEST_FAMILY" maxlength="64" disabled="disabled" value="${busRecord.INVEST_FAMILY }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
				<td colspan="3">
					<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" readonly="readonly"
						class="eve-textarea validate[validate[required],maxSize[2000]]"
					 	style="width:740px;height:100px;" >${busRecord.BUSSINESS_SCOPE }</textarea> 
					<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID }">
					<input type="button" name="scopeChose" value="选择" onclick="showSelectJyfwNew();">
					
				</td>
			</tr>
			<tr>
				<td class="tab_width">常用限定用语</td>
				<td colspan="3">
				<div>
					<eve:eveselect clazz="tab_text validate[]" dataParams="yjfwcyxdyy"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setJyfw(this.value)"
					defaultEmptyText="选择常用限定用语" name="yjfwcyxdyy" value="${busRecord.ENTERPRISE_STATEMENT }">
					</eve:eveselect>
				</div>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> 经营范围备注描述：</td>
				<td colspan="3"><input type="text" class="tab_text" style="width:740px;" <c:if test="${isJyfwAll == false}">disabled="disabled"</c:if>
					name="SCOPE_REMARK" maxlength="128" value="${busRecord.SCOPE_REMARK }"/>
					<c:if test="${isJyfwAll == true}"><a class="eflowbutton" onclick="saveScopeRemark();" style="height: 28px;float: none;">保存</a></c:if></td>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">投资人信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
				<td>
					<input type="text" class="tab_text validate[required]"
						name="INVESTOR_NAME" maxlength="16" value="${busRecord.INVESTOR_NAME }"/>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>性别：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="sex"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择性别" name="INVESTOR_SEX" value="${busRecord.INVESTOR_SEX }">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>出生日期：</td>
				<td>
					<input type="text" class="laydate-icon" readonly="readonly" id="birthday"
						name="INVESTOR_BIRTHDAY" value="${busRecord.INVESTOR_BIRTHDAY }"/>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>民族：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="nation"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择民族" name="INVESTOR_NATION" value="${busRecord.INVESTOR_NATION }">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>文化程度：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="degree"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择文化程度" name="INVESTOR_DEGREE" value="${busRecord.INVESTOR_DEGREE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>政治面貌：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="political"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择政治面貌" name="INVESTOR_POLITICAL" value="${busRecord.INVESTOR_POLITICAL }">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
				<td><input type="text" class="tab_text validate[required]" maxlength="11"
					name="INVESTOR_MOBILE" value="${busRecord.INVESTOR_MOBILE }"/></td>
				<td class="tab_width"> 电子邮箱：</td>
				<td><input type="text" class="tab_text validate[[],custom[email]]" maxlength="32"
					name="INVESTOR_EMAIL" value="${busRecord.INVESTOR_EMAIL }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="INVESTOR_IDTYPE" value="${busRecord.INVESTOR_IDTYPE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="INVESTOR_IDCARD" maxlength="30" value="${busRecord.INVESTOR_IDCARD }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>居所：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
					name="INVESTOR_ADDR" maxlength="64" value="${busRecord.INVESTOR_ADDR }"/></td>
			</tr>
			<tr>
				
				<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required,custom[chinaZip]]"
					name="INVESTOR_POSTCODE" value="${busRecord.INVESTOR_POSTCODE }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>申请前职业状况：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
					name="INVESTOR_JOB" maxlength="64" value="${busRecord.INVESTOR_JOB }"/></td>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4" >联络员信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
				<td><input type="text" class="tab_text validate[required]" 
					name="LIAISON_NAME" maxlength="8" value="${busRecord.LIAISON_NAME }"/></td>
				<td class="tab_width"> 固定电话：</td>
				<td><input type="text" class="tab_text"
					name="LIAISON_FIXEDLINE" value="${busRecord.LIAISON_FIXEDLINE }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="LIAISON_MOBILE" value="${busRecord.LIAISON_MOBILE }"/></td>
				<td class="tab_width"> 电子邮箱：</td>
				<td><input type="text" class="tab_text validate[[],custom[email]]"
					name="LIAISON_EMAIL" maxlength="32" value="${busRecord.LIAISON_EMAIL }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="LIAISON_IDTYPE" value="${busRecord.LIAISON_IDTYPE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="LIAISON_IDNO" value="${busRecord.LIAISON_IDNO }"/></td>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4" >财务负责人信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
				<td><input type="text" class="tab_text validate[required]" 
					name="FINANCE_NAME" maxlength="8" value="${busRecord.FINANCE_NAME }"/></td>
				<td class="tab_width"> 固定电话：</td>
				<td><input type="text" class="tab_text"
					name="FINANCE_FIXEDLINE" value="${busRecord.FINANCE_FIXEDLINE }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="FINANCE_MOBILE" value="${busRecord.FINANCE_MOBILE }"/></td>
				<td class="tab_width"> 电子邮箱：</td>
				<td><input type="text" class="tab_text validate[[],custom[email]]"
					name="FINANCE_EMAIL" maxlength="32" value="${busRecord.FINANCE_EMAIL }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="FINANCE_IDTYPE" value="${busRecord.FINANCE_IDTYPE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="FINANCE_IDNO" value="${busRecord.FINANCE_IDNO }"/></td>
			</tr>
		</table>
		<div class="tab_height"></div>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4" >办税人信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
				<td><input type="text" class="tab_text validate[required]" 
					name="TAXMAN_NAME" maxlength="8" value="${busRecord.TAXMAN_NAME }"/></td>
				<td class="tab_width"> 固定电话：</td>
				<td><input type="text" class="tab_text"
					name="TAXMAN_MOBILE"  value="${busRecord.TAXMAN_MOBILE }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="TAXMAN_FIXEDLINE"  value="${busRecord.TAXMAN_FIXEDLINE }"/></td>
				<td class="tab_width"> 电子邮箱：</td>
				<td><input type="text" class="tab_text validate[[],custom[email]]"
					name="TAXMAN_EMAIL" maxlength="32" value="${busRecord.TAXMAN_EMAIL }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="TAXMAN_IDTYPE" value="${busRecord.TAXMAN_IDTYPE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td><input type="text" class="tab_text validate[required]"
					name="TAXMAN_IDNO" value="${busRecord.TAXMAN_IDNO }"/></td>
			</tr>
		</table>
		
		<%--开始引入印章信息界面 --%>
		<div id="yzxx" >	
			<c:if test="${empty execution.IS_FREE_ENGRAVE_SEAL}">
				<jsp:include page="./T_COMMERCIAL_SEAL.jsp" />
			</c:if>
			<c:if test="${!empty execution.IS_FREE_ENGRAVE_SEAL && execution.IS_FREE_ENGRAVE_SEAL==1}">
				<jsp:include page="./T_COMMERCIAL_NEW_SEAL.jsp" />
			</c:if>
		</div>
		<%--结束引入印章信息界面 --%>
		
		<%--开始引入附件信息界面 --%>
		<div id="uploadFile" >
			<jsp:include page="./T_COMMERCIAL_NEW_FILE.jsp" />
		</div>
		<%--结束引入附件信息界面 --%>
		<%--开始引入邮寄信息界面 --%>
		<div  >
			<jsp:include page="./T_COMMERCIAL_EMAIL.jsp" />
		</div>
		<%--结束引入邮寄信息界面 --%>

		<%--开始引入社会保险信息界面 --%>
		<c:if test="${busRecord.ISSOCIALREGISTER == '1'}">
		<jsp:include page="./T_COMMERCIAL_INSURANCE.jsp" />
		</c:if>
		<%--结束引入社会保险信息界面 --%>

		<%--开始引入医疗保险信息界面 --%>
		<div id="ybxx" >
			<jsp:include page="./T_COMMERCIAL_MEDICAL.jsp" />
		</div>
		<%--结束引入医疗保险信息界面 --%>

		<%--开始引入公积金登记信息界面 --%>
		<div id="gjjxx" >
			<jsp:include page="./T_COMMERCIAL_FUNDS.jsp" />
		</div>
		<%--结束引入公积金登记信息界面 --%>

		<%--开始引入新办企业税务套餐登记信息界面 --%>
		<div id="taxinfo" >
			<jsp:include page="./T_COMMERCIAL_TAX.jsp" />
		</div>
		<%--结束引入新办企业税务套餐登记信息界面 --%>
		
		<%--开始引入商事材料下载--%>
		<jsp:include page="./commercialMaterList.jsp" />
		<%--结束引入商事材料下载 --%>
		</div>
		
		<%--开始引入银行开户信息界面 --%>
		<div id="bank" >
		<jsp:include page="./T_COMMERCIAL_BANK.jsp" />
		</div>
		<%--结束引入银行开户信息界面 --%>	
		
		<%--开始引入关联事项材料 --%>
		<div id="relatedItemMater"></div>
		<%--结束引入关联事项材料 --%>
		
		<%--开始引入多证合一对象--%>
		<jsp:include page="./common/T_COMMERCIAL_MULTIPLE.jsp" />
		<%--结束引入多证合一对象 --%>
		
		<%--开始引入公用申报对象--%>
		<jsp:include page="./ssapplyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
	</form>
</body>
</html>