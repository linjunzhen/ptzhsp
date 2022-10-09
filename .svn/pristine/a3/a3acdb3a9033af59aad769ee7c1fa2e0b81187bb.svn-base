<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
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
	src="<%=basePath%>/webpage/bsdt/applyform/js/branch.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function downloadApply(){		
		var flowSubmitObj = FlowUtil.getFlowObj();
		var exeId = flowSubmitObj.EFLOW_EXEID;
		var itemCode = '${serviceItem.ITEM_CODE}';
		
		window.location.href=__ctxPath+"/domesticControllerController/downLoadBankApply.do?exeId="+exeId
			+"&itemCode="+itemCode;
	}
</script>
</head>

<body>
	<div class="detail_title">
		<h1>${serviceItem.ITEM_NAME}</h1>
	</div>
	<form id="T_COMMERCIAL_BRANCH" method="post">
		<%--===================重要的隐藏域内容=========== --%>
		<input type="hidden" name="ITEM_NAME" value="${serviceItem.ITEM_NAME}" />
		<input type="hidden" name="ITEM_CODE" value="${serviceItem.ITEM_CODE}" />
		<input type="hidden" name="SSBMBM" value="${serviceItem.SSBMBM}" /> 
		<input type="hidden" name="SQFS" value="${serviceItem.APPLY_TYPE}" /> 
		<input type="hidden" name="EFLOW_DEFKEY" value="${serviceItem.DEF_KEY}" />
		<input type="hidden" name="REGISTER_TYPE" value="0"/>
		<input type="hidden" name="ACCOUNTANDAGREEMENTJSON" />
		<input type="hidden" name="APPLYINVOICE_JSON" />
		<%--===================重要的隐藏域内容=========== --%>
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4">事项基本信息</th>
			</tr>
			<tr>
				<td > 审批事项：</td>
				<td >${serviceItem.ITEM_NAME}</td>
	            <td style="width: 170px;background: #fbfbfb;"> 承诺时间(工作日):</td>
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
					style="width: 70%" maxlength="60"
					name="SBMC" value="${busRecord.SBMC }"/><span style="color: red;"><strong>友情提醒：请规范填写“申报对象信息”</strong></span></td>
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

		<div id="jbxx">
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
				<td class="tab_width"><font class="tab_color">*</font>分支机构名称：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="BRANCH_NAME" value="${busRecord.BRANCH_NAME }" style="width:854px;"/>
				</td>
			</tr>

<%--			<tr >--%>
<%--				<td class="tab_width"><font class="tab_color">*</font>是否已通过名称自主选用：</td>--%>
<%--				<td>--%>
<%--					<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1 }">checked="checked"</c:if> >是--%>
<%--					<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==0 }">checked="checked"</c:if> >否--%>
<%--				</td>--%>
<%--				<td class="tab_width"> 名称自主选用文号：</td>--%>
<%--				<td><input type="text" class="tab_text" disabled="disabled" maxlength="32"--%>
<%--					name="PREAPPROVAL_NUM" value="${busRecord.PREAPPROVAL_NUM }"/></td>--%>
<%--			</tr>--%>
			<%-- <tr>
				<td class="tab_width"><font class="tab_color">*</font>营业场所：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="BUSINESS_PLACE" value="${busRecord.BUSINESS_PLACE }" style="width:854px;"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width">生产经营地址：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="PREMISES" value="${busRecord.PREMISES }" style="width:854px;"/>
				</td>
			</tr> --%>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>注册地址：</td>
				<td colspan="2"><input type="text" class="tab_text validate[required]" style="width:600px;"
					name="REGISTER_ADDR" maxlength="126" value="${busRecord.REGISTER_ADDR }"/></td>
				<td colspan="1"><font class="tab_color">*</font>面积：
					<input type="text" class="tab_text" maxlength="16" style="width:15%;height:20px;"
						   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
					<font style="font-size: 16px;margin-left: 10px;">㎡</font>
				</td>
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
				<td class="tab_width"><font class="tab_color">*</font>房屋所有权人名称：</td>
				<td><input  type="text" class="tab_text  "
							name="PLACE_REGISTER_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_REGISTER_OWNER}"/></td>
				<td class="tab_width"><font class="tab_color">*</font>房屋所有权人联系电话：</td>
				<td><input type="text" class="tab_text "
						   name="PLACE_REGISTER_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_REGISTER_TEL}"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>取得方式：</td>
				<td colspan="3" style="width:500px;">
					<eve:eveselect clazz="tab_text" dataParams="wayOfGet"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择取得方式" name="RESIDENCE_REGISTER_WAYOFGET"  value="${busRecord.RESIDENCE_REGISTER_WAYOFGET}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>是否属于军队房产：</td>
				<td >
					<eve:eveselect clazz="tab_text" dataParams="armyHourse"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择军队房产情况" name="ARMY_REGISTER_HOURSE"  value="${busRecord.ARMY_REGISTER_HOURSE}">
					</eve:eveselect>
				</td>
				<td class="tab_width">其他证明：</td>
				<td >
					<input type="text" class="tab_text"
						   name="ARMYHOURSE_REGISTER_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REGISTER_REMARKS}"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width" >是否有生产经营地址：</td>
				<td colspan="3">
					<input type="radio" name="IS_BUSSINESS_ADDR" value="1" <c:if test="${busRecord.IS_BUSSINESS_ADDR==1 }">checked="checked"</c:if> >是
					<input type="radio" name="IS_BUSSINESS_ADDR" value="0" <c:if test="${busRecord.IS_BUSSINESS_ADDR!=1 }">checked="checked"</c:if> >否
				</td>
			</tr>
		
			<c:if test="${busRecord.IS_BUSSINESS_ADDR==1}">
			<tr>
				<td class="tab_width">生产经营地址：</td>
				<td colspan="2"><input type="text" class="tab_text " style="width:600px;"
					name="BUSSINESS_ADDR" maxlength="128" value="${busRecord.BUSSINESS_ADDR }"/></td>
				<td colspan="1">面积：
					<input type="text"  maxlength="16" style="width:15%;height:20px;"
						   name="BUSSINESS_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.BUSSINESS_SQUARE_ADDR}"/>
					<font style="font-size: 16px;margin-left: 10px;">㎡</font>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>房屋所有权人名称：</td>
				<td><input  type="text" class="tab_text  "
							name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
				<td class="tab_width"><font class="tab_color">*</font>房屋所有权人联系电话：</td>
				<td><input type="text" class="tab_text "
						   name="PLACE_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_TEL}"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>取得方式：</td>
				<td colspan="3" style="width:500px;">
					<eve:eveselect clazz="tab_text" dataParams="wayOfGet"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择取得方式" name="RESIDENCE_WAYOFGET"  value="${busRecord.RESIDENCE_WAYOFGET}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>是否属于军队房产：</td>
				<td >
					<eve:eveselect clazz="tab_text" dataParams="armyHourse"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="请选择军队房产情况" name="ARMY_HOURSE"  value="${busRecord.ARMY_HOURSE}">
					</eve:eveselect>
				</td>
				<td class="tab_width">其他证明：</td>
				<td >
					<input type="text" class="tab_text"
						   name="ARMYHOURSE_REMARKS"  placeholder="请输入其他证明"  maxlength="256"  value="${busRecord.ARMYHOURSE_REMARKS}"/>
				</td>
			</tr>
			</c:if>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
				<td colspan="3">
					<input type="text" class="tab_text" name="PHONE" value="${busRecord.PHONE }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
				<td>
					<input type="text" class="tab_text" name="POSTCODE" value="${busRecord.POSTCODE }"/>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>从业人数：</td>
				<td>
					<input type="text" class="tab_text" name="EMPLOYED_NUM" value="${busRecord.EMPLOYED_NUM }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
				<td colspan="3">
					<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" readonly="readonly"
						class="eve-textarea"
					 	style="width:854px;height:100px;" >${busRecord.BUSSINESS_SCOPE }</textarea>
						<input type="hidden" name="BUSSINESS_SCOPE_ID"
						value="${busRecord.BUSSINESS_SCOPE_ID }"> <input
						type="button" name="scopeChose" value="选择"
						onclick="showSelectJyfwNew();"> 
				</td>
			</tr>
		</table>
		</div>
		
		<div id="subordinateInfo">
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4" >隶属企业</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
				<td colspan="3">
					<input type="text" class="tab_text" style="width:854px;" name="SUBORDINATE_NAME" value="${busRecord.SUBORDINATE_NAME }"/>
				</td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>注册号/统一社会信用代码：</td>
				<td colspan="3"><input type="text" class="tab_text validate[required]"
					name="SUBORDINATE_CREDITUNICODE" value="${busRecord.SUBORDINATE_CREDITUNICODE }"/></td>
			</tr>


			<tr>
				<td class="tab_width"><font class="tab_color">*</font>企业类型：</td>
				<td>
					<input type="text" class="tab_text"  maxlength="60" name="MAIN_COMPANYTYPE" value="${busRecord.MAIN_COMPANYTYPE }"/>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>登记机关：</td>
				<td>
					<input type="text" class="tab_text" name="MAIN_REGISAUTHOR" value="${busRecord.MAIN_REGISAUTHOR }"/>
				</td>
			</tr>

			<tr>
				<td class="tab_width"><font class="tab_color">*</font>营业期限：</td>
				<td colspan="3">
					<input type="text" class="tab_text Wdate validate[required]" name="MAIN_BUSSINESSTIME"
						   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"
						   value="${busRecord.MAIN_BUSSINESSTIME }" />
				</td>
			</tr>
		</table>
		</div>
		<%--开始引入经办人和财务负责人和办税人信息界面 --%>
		<div id="ryxx" >
		<jsp:include page="./branch/T_COMMERCIAL_OPERATORS.jsp" />
		</div>
		<%--结束引入经办人和财务负责人和办税人信息界面 --%>
		
		<%--开始引入联络员信息界面 --%>
		<div id="lxyxx" >
		<jsp:include page="./T_COMMERCIAL_LIAISON.jsp" />		
		</div>
		<%--结束引入联络员信息界面 --%>
		
		<div id="fzrxx" >
		<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
			<tr>
				<th colspan="4" >负责人信息</th>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
				<td><input type="text" class="tab_text" 
					name="LEGAL_NAME" maxlength="8" value="${busRecord.LEGAL_NAME }"/></td>				
				<td class="tab_width"> 电子邮箱：</td>
				<td><input type="text" class="tab_text"
					name="LEGAL_EMAIL" maxlength="32" value="${busRecord.LEGAL_EMAIL }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
				<td><input type="text" class="tab_text "
					name="LEGAL_MOBILE" value="${busRecord.LEGAL_MOBILE }"/></td>
				<td class="tab_width"> 固定电话：</td>
				<td><input type="text" class="tab_text "
					name="LEGAL_FIXEDLINE" value="${busRecord.LEGAL_FIXEDLINE }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" value="${busRecord.LEGAL_IDTYPE }">
					</eve:eveselect>
				</td>
				<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
				<td><input type="text" class="tab_text "
					name="LEGAL_IDNO"  value="${busRecord.LEGAL_IDNO }"/></td>
			</tr>
			<tr>
				<td class="tab_width"><font class="tab_color">*</font>负责人国别：</td>
				<td>
					<eve:eveselect clazz="tab_text" dataParams="ssdjgb"
								   dataInterface="dictionaryService.findDatasForCountrySelect"
								   defaultEmptyText="请选择证件类型" name="LEGAL_COMPANYCOUNTRY" value="${busRecord.LEGAL_COMPANYCOUNTRY }">
					</eve:eveselect>
				</td>
			</tr>
		</table>
		</div>
		
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
		<%--开始引入邮寄信息界面 --%>
		<div  >
			<jsp:include page="./T_COMMERCIAL_EMAIL.jsp" />
		</div>
		<%--结束引入邮寄信息界面 --%>

		<%--开始引入社会保险信息界面 --%>
		<c:if test="${busRecord.ISSOCIALREGISTER == '1'}">
		<div id="sbxx" >
		<jsp:include page="./T_COMMERCIAL_INSURANCE.jsp" />
		</div>
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
		
		<%--开始引入银行开户信息界面 --%>
		<div id="bank" >
		<jsp:include page="./T_COMMERCIAL_BANK.jsp" />
		</div>
		<%--结束引入银行开户信息界面 --%>	
		<%--开始引入附件信息界面 --%>
		<div id="uploadFile" >
			<jsp:include page="./T_COMMERCIAL_NEW_FILE.jsp" />
		</div>
		<%--结束引入附件信息界面 --%>
		
		<%--开始引入其他信息界面 --%>
		<div id="qtxx" >
		<jsp:include page="./T_COMMERCIAL_OTHER.jsp" />
		</div>
		<%--结束引入其他信息界面 --%>	
		
		<%--开始引入商事材料下载--%>
		<div id="clxx" >
		<jsp:include page="./commercialMaterList.jsp" />
		</div>
		<%--结束引入商事材料下载 --%>
		
		<%--开始引入关联事项材料 --%>
		<div id="relatedItemMater"></div>
		<%--结束引入关联事项材料 --%>
		
		<%--开始引入多证合一对象--%>
		<jsp:include page="./common/T_COMMERCIAL_MULTIPLE_BRANCH.jsp" />
		<%--结束引入多证合一对象 --%>
		
		
		<%--开始引入公用申报对象--%>
		<jsp:include page="./ssapplyuserinfo.jsp" />
		<%--结束引入公用申报对象 --%>
	</form>
</body>
</html>
