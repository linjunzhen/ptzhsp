<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<form action="" id="COMPANY_FORM"  >
	<input type="hidden" name="zhqhfdz" id="zhqhfdz" value="${zhqhfdz}"/>
	<table cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
		<tr>
			<th><font class="syj-color2">*</font>企业名称（中文）：</th>
			<td>
			    <input type="hidden" id= "COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
				<input type="text" class="syj-input1 validate[required],custom[verifyCompanyNameExist]" 
					name="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称（中文）" value="${busRecord.COMPANY_NAME}"/>
			</td>
			<th>企业名称（英文）：</th>
			<td>
				<input type="text" class="syj-input1 validate[custom[onlyLetterNumber]],custom[verifyCompanyEngExist]" name="COMPANY_NAME_ENG" maxlength="128" placeholder="请输入企业名称（英文）" value="${busRecord.COMPANY_NAME_ENG}"/>
			</td>
		</tr>
		<tr>
			<th>集团名称：</th>
			<td>
				<input type="text" class="syj-input1 "
					   name="GROUP_NAME"  maxlength="256" placeholder="请输入集团名称" value="${busRecord.GROUP_NAME}"/>
			</td>
			<th>集团简称：</th>
			<td>
				<input type="text" class="syj-input1 "
					   name="GROUP_ABBRE"  maxlength="128" placeholder="请输入集团简称" value="${busRecord.GROUP_ABBRE}"/>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>企业类型：</th>
			<td>					
				<input type="radio" class="validate[required]" name="COMPANY_NATURE" value="01" <c:if test="${busRecord.COMPANY_NATURE=='01'}"> checked="checked"</c:if>>合资<br/>
				<input type="radio" class="validate[required]" name="COMPANY_NATURE" value="02" <c:if test="${busRecord.COMPANY_NATURE=='02'}"> checked="checked"</c:if>>合作<br/>			
				<input type="radio" class="validate[required]" name="COMPANY_NATURE" value="03" <c:if test="${busRecord.COMPANY_NATURE=='03'}"> checked="checked"</c:if>>独资<br/>
				<input type="radio" class="validate[required]" name="COMPANY_NATURE" value="04" <c:if test="${busRecord.COMPANY_NATURE=='04'}"> checked="checked"</c:if>>股份制			
				<span id="jointStock_span" style="display:none">:
					<input type="radio" name="JOINT_STOCK_TYPE" value="1" <c:if test="${busRecord.JOINT_STOCK_TYPE==1}"> checked="checked"</c:if>>上市
					<input type="radio" name="JOINT_STOCK_TYPE" value="0" <c:if test="${busRecord.JOINT_STOCK_TYPE==0}"> checked="checked"</c:if>>非上市
				
					<span id="unlisted_span" style="display:none">(
						<input type="radio" name="UNLISTED_TYPE" value="1" <c:if test="${busRecord.UNLISTED_TYPE==1}"> checked="checked"</c:if>>公众公司
						<input type="radio" name="UNLISTED_TYPE" value="2" <c:if test="${busRecord.UNLISTED_TYPE==2}"> checked="checked"</c:if>>其他)
					</span>
				</span>
			</td>
			<th><font class="syj-color2">*</font>国别（地区）：</th>
			<td>				
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjgb:中国"
				dataInterface="dictionaryService.findDatasForCountrySelect"
				defaultEmptyText="请选择国别" name="COMPANY_COUNTRY" value="${busRecord.COMPANY_COUNTRY}">
				</eve:eveselect>
			</td>
		</tr>
		
		<tr>
			<th><font class="syj-color2">*</font>成立方式：</th>
			<td>				
				<input type="radio" name="ESTABLISH_PATTERN" value="1"  checked="checked">新设<br/>
			</td>
			<td  colspan="2">					
				<input type="radio" name="NEW_TYPE" value="01" <c:if test="${busRecord.NEW_TYPE=='01'}"> checked="checked"</c:if>>普通新设
				<input type="radio" name="NEW_TYPE" value="02" <c:if test="${busRecord.NEW_TYPE=='02'}"> checked="checked"</c:if>>新设合并		
				<input type="radio" name="NEW_TYPE" value="03" <c:if test="${busRecord.NEW_TYPE=='03'}"> checked="checked"</c:if>>吸收合并
				<input type="radio" name="NEW_TYPE" value="04" <c:if test="${busRecord.NEW_TYPE=='04'}"> checked="checked"</c:if>>存续合并	
				<input type="radio" name="NEW_TYPE" value="05" <c:if test="${busRecord.NEW_TYPE=='05'}"> checked="checked"</c:if>>解散分立
			</td>
		</tr>
<%--		<tr style="display: none;">--%>
<%--			<th><font class="syj-color2">*</font>是否已通过名称自主选用：</th>--%>
<%--			<td>--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1}"> checked="checked"</c:if>>是--%>
<%--				<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS!=1}"> checked="checked"</c:if>>否--%>
<%--			</td>--%>
<%--			<th> 名称自主选用文号：</th>--%>
<%--			<td><input type="text" class="syj-input1" maxlength="32"--%>
<%--				name="PREAPPROVAL_NUM"  placeholder="请输入名称自主选用文号"  disabled="disabled" value="${busRecord.PREAPPROVAL_NUM}"/></td>--%>
<%--		</tr>--%>
		
		<tr>
			<th><font class="syj-color2">*</font>注册地址：</th>
			<td colspan="3">				
				<input type="text" class="syj-input1 validate[required]" style="width:740px;" onchange="setSendLawAddr()"
				name="REGISTER_ADDR" maxlength="117"  placeholder="请输入注册地址" value="${busRecord.REGISTER_ADDR}"/>
			</td>
		</tr>

		<tr>
			<th> <font class="syj-color2">*</font>法律文书送达地址(同注册地址)：</td>
			<td >
				<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="1"  onclick="setSendLawAddr('1');"
						<c:if test="${busRecord.IS_REGISTER_ADDR==1}"> checked="checked"</c:if>/>是
				<input type="radio" class="radio validate[required]" name="IS_REGISTER_ADDR" value="0" onclick="setSendLawAddr('0');"
						<c:if test="${busRecord.IS_REGISTER_ADDR==0}"> checked="checked"</c:if>/>否
			</td>
			<th><font class="syj-color2">*</font>法律文书送达地址：</th>
			<td><input  type="text" class="syj-input1 validate[required]"
						name="LAW_SEND_ADDR" maxlength="512"  placeholder="请输入法律文书送达地址" value="${busRecord.LAW_SEND_ADDR}"/></td>
			</td>
		</tr>
		<tr>
			<th> <font class="syj-color2">*</font>法律文书电子送达地址：</td>
			<td colspan="3">
				邮箱：<input type="text" class="syj-input1  validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR],custom[email]]"
						  name="LAW_EMAIL_ADDR" maxlength="128"  placeholder="请输入邮箱地址" value="${busRecord.LAW_EMAIL_ADDR}"/>
				传真：<input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
						  name="LAW_FAX_ADDR" maxlength="128"  placeholder="请输入传真地址" value="${busRecord.LAW_FAX_ADDR}"/>
				即时通讯账号（如微信）：<input type="text" class="syj-input1   validate[groupRequired[LAW_EMAIL_ADDR,LAW_FAX_ADDR,LAW_IM_ADDR]]"
								   name="LAW_IM_ADDR" maxlength="128"  placeholder="请输入即时通讯账号" value="${busRecord.LAW_IM_ADDR}"/>
			</td>
		</tr>

		<script type="text/javascript">
			function setSendLawAddr() {
				var val = $("[name='IS_REGISTER_ADDR']:checked").val();
				if(val=='1'){
					$("input[name='LAW_SEND_ADDR']").val($("input[name='REGISTER_ADDR']").val());
					$("input[name='LAW_SEND_ADDR']").attr("disabled","disabled");
				}else{
					$("input[name='LAW_SEND_ADDR']").removeAttr("disabled");
				}
			}
			$(function () {
				setSendLawAddr();
			})
		</script>


		<tr>
			<th><font class="syj-color2">*</font>是否自贸试验区内：</th>
			<td>				
				<input class="validate[required]" type="radio" name="FAT_TYPE" value="1" <c:if test="${busRecord.FAT_TYPE==1}"> checked="checked"</c:if>>是
				<input class="validate[required]" type="radio" name="FAT_TYPE" value="0" <c:if test="${busRecord.FAT_TYPE==0}"> checked="checked"</c:if>>否
				<input type="text" class="syj-input1 validate[required]"   style="width:215px;"
				name="FAT_AREA" maxlength="64"  placeholder="请输入自贸试验区片区"  <c:if test="${busRecord.FAT_TYPE!=1}">disabled="disabled"</c:if> value="${busRecord.FAT_AREA}"/>
			</td>
			<th><font class="syj-color2">*</font>是否国家级经济技术开发区内：</th>
			<td>								
				<!--<input class="validate[required]" type="radio" name="IS_ETDZ" value="1" <c:if test="${busRecord.IS_ETDZ==1}"> checked="checked"</c:if>>是-->
				<input class="validate[required]" type="radio" name="IS_ETDZ" value="0" checked="checked">否
				<!--<input type="text" class="syj-input1 validate[required]"   style="width:215px;"
				name="ETDZ_AREA" maxlength="64"  placeholder="请输入国家级经济技术开发区" <c:if test="${busRecord.IS_ETDZ!=1}">disabled="disabled"</c:if> value="${busRecord.ETDZ_AREA}"/>-->
			</td>
		</tr>
		<tr>
			<th rowspan="4"><font class="syj-color2">*</font>是否仅作为联络地址：</th>
			<td rowspan="4">
				<input type="radio" name="IS_ONLY_CONTACT" value="1" <c:if test="${busRecord.IS_ONLY_CONTACT!=0}"> checked="checked"</c:if>>是
				<input type="radio" name="IS_ONLY_CONTACT" value="0" <c:if test="${busRecord.IS_ONLY_CONTACT==0}"> checked="checked"</c:if>>否
			</td>
		</tr>
		<tr>
			<th>出租（借）方：</th>
			<td><input type="text" class="syj-input1"
				name="LESSOR" maxlength="32"  placeholder="请输入出租(借)方" disabled="disabled" value="${busRecord.LESSOR}"/></td>
		</tr>
		<tr>
			<th>出租（借）时间：</th>
			<td><input type="text" style="width:45%;height:20px;"
<%--				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'LEASE_END_DATE\',{y:-1})}'})"--%>
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
				readonly="true" id="LEASE_START_DATE"  
				name="LEASE_START_DATE" disabled="disabled"  value="${busRecord.LEASE_START_DATE}"/>至
				<input type="text" 
<%--				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'LEASE_START_DATE\',{y:1})}'})" --%>
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" 
				style="width:45%;height:20px;" id="LEASE_END_DATE" 
				name="LEASE_END_DATE" readonly="true"  disabled="disabled"   value="${busRecord.LEASE_END_DATE}"/></td>
		</tr>
		<tr>
			<th>租赁合同签订时间：</th>
			<td><input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" id="sign" readonly="readonly"
				name="SINGING_TIME"  style="width:45%;height:20px;" disabled="disabled" value="${busRecord.SINGING_TIME}"/></td>
		</tr>
		
		
		<tr>
			<th><font class="syj-color2">*</font>生产经营地址：</th>
			<td colspan="3"><input type="text" class="syj-input1 validate[required]" style="width:740px;"
				name="BUSSINESS_ADDR" maxlength="128"  placeholder="请输入生产经营地址"  value="${busRecord.BUSSINESS_ADDR}"/></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>企业联系电话：</th>
			<td><input type="text" class="syj-input1 validate[required]"
				name="CONTACT_PHONE" placeholder="请输入企业联系电话" maxlength="16" value="${busRecord.CONTACT_PHONE}"/></td>
			<th><font class="syj-color2">*</font>邮政编码：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc" readonly="readonly"
				name="POST_CODE" value="350400"  placeholder="请输入邮政编码"/></td>

		</tr>
		<tr>
			<th><font class="syj-color2">*</font>公司类型：</th>
			<td>
				<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
					name="COMPANY_TYPE"  value="${requestParams.COMPANY_TYPE}"/>
				<input type="hidden" name="COMPANY_TYPECODE" value="${requestParams.COMPANY_TYPECODE}"/>
				<input type="hidden" name="COMPANY_SETTYPE" value="${requestParams.COMPANY_SETTYPE}"/>	
				<input type="hidden" name="COMPANY_SETNATURE" value="${requestParams.COMPANY_SETNATURE}"/>				
			</td>
			<th><font class="syj-color2">*</font>企业规模：</th>
			<td>
				<eve:eveselect clazz="input-dropdown validate[required]" dataParams="ssdjqyfl"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择企业规模" name="ENTERPRISE_STATEMENT"  value="${busRecord.ENTERPRISE_STATEMENT}">
				</eve:eveselect> 
				&nbsp;<a target="_blank" class="answer" href="<%=path%>/contentController/view.do?contentId=996" title="企业规模帮助"></a>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>经营范围：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
					class="eve-textarea validate[required],maxSize[2000]" readonly="readonly"
					style="width:90%;height:100px;"  placeholder="请选择经营范围" onclick="showSelectJyfwNew();">${busRecord.BUSSINESS_SCOPE}</textarea> 					
				<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID}">
				<a href="javascript:showSelectJyfwNew();" class="btn1">选 择</a>
				<div style="color:red;width:90%;">友情提示：<br/>㈠，经营范围选择大项之后，不需要选择大项下的小项！
				<br/>㈡，申请人应当参照《国民经济行业分类》选择一种或多种小类、
				中类或者大类自主提出经营范围登记申请。对《国民经济行业分类》中没有规范的新兴行业或者具体经营项目，
				可以参照政策文件、行业习惯或者专业文献等提出申请</div>
				<div style="margin-top: 5px;margin-bottom: 5px;">
				<input type="radio" name="NO_ACCESS_MANAGE" value="1" <c:if test="${busRecord.NO_ACCESS_MANAGE==1}"> checked="checked"</c:if>>经营范围不涉及国家规定实施的准入特别管理措施
				</div>
			</td>
		</tr>
		<tr>
			<th> 投资行业：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="INVEST_INDUSTRY" readonly="readonly"
					class="eve-textarea validate[ validate[],maxSize[2000]]"
					style="width:90%;height:100px;"  placeholder="选择经营范围后投资行业自动回填" >${busRecord.INVEST_INDUSTRY}</textarea> 					
				<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID}">
				<!--<a href="javascript:showSelectTzhy();" class="btn1">选 择</a>-->
			</td>
		</tr>
		<tr>
			<th> 行业编码：</th>
			<td colspan="3"><input type="text" class="syj-input1 inputBackgroundCcc"  placeholder="选择经营范围后行业编码自动回填" readonly="readonly"
				name="INDUSTRY_CODE"   style="width:740px;" value="${busRecord.INDUSTRY_CODE}"/></td>
		</tr>
		<tr>
			<th> 主营行业：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="MAIN_INDUSTRY" readonly="readonly"
					class="eve-textarea validate[ validate[],maxSize[2000]]"
					style="width:90%;height:100px;"  onclick="showSelectZyhy();" placeholder="选择主营行业" >${busRecord.MAIN_INDUSTRY}</textarea> 					
				<input type="hidden" name="MAIN_INDUSTRY_ID" value="${busRecord.MAIN_INDUSTRY_ID}">
				<a href="javascript:showSelectZyhy();" class="btn1">选 择</a>
			</td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>业务类型：</th>
			<td  colspan="3">					
				<div style="margin-bottom: 5px;"><input type="radio" name="BUSINESS_TYPE" value="01" <c:if test="${busRecord.BUSINESS_TYPE=='01'}"> checked="checked"</c:if>>高新技术企业</div>
				
				<div><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="02" <c:if test="${busRecord.BUSINESS_TYPE=='02'}"> checked="checked"</c:if>>研发中心
				（
				<input type="radio" disabled="disabled" name="RESEARCH_TYPE" value="01" <c:if test="${busRecord.RESEARCH_TYPE=='01'}"> checked="checked"</c:if>>独立法人研发中心
				<input type="radio" disabled="disabled" name="RESEARCH_TYPE" value="02" <c:if test="${busRecord.RESEARCH_TYPE=='02'}"> checked="checked"</c:if>>非独立法人研发中心
				）</div>				
				<div><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="03" <c:if test="${busRecord.BUSINESS_TYPE=='03'}"> checked="checked"</c:if>>功能性机构
				（
				<input type="radio" disabled="disabled" name="FUNCTION_TYPE" value="01" <c:if test="${busRecord.FUNCTION_TYPE=='01'}"> checked="checked"</c:if>>地区总部
				<input type="radio" disabled="disabled" name="FUNCTION_TYPE" value="02" <c:if test="${busRecord.FUNCTION_TYPE=='02'}"> checked="checked"</c:if>>采购中心
				<input type="radio" disabled="disabled" name="FUNCTION_TYPE" value="03" <c:if test="${busRecord.FUNCTION_TYPE=='03'}"> checked="checked"</c:if>>财务管理中心
				<input type="radio" disabled="disabled" name="FUNCTION_TYPE" value="04" <c:if test="${busRecord.FUNCTION_TYPE=='04'}"> checked="checked"</c:if>>结算中心
				<input type="radio" disabled="disabled" name="FUNCTION_TYPE" value="05" <c:if test="${busRecord.FUNCTION_TYPE=='05'}"> checked="checked"</c:if>>销售中心
				<input type="radio" disabled="disabled" name="FUNCTION_TYPE" value="06" <c:if test="${busRecord.FUNCTION_TYPE=='06'}"> checked="checked"</c:if>>分拨中心
				<input type="radio" disabled="disabled" name="FUNCTION_TYPE" value="07" <c:if test="${busRecord.FUNCTION_TYPE=='07'}"> checked="checked"</c:if>>其他
				<input type="text" class="syj-input1" name="FUNCTION_OTHER"   maxlength="64"  style="width: 100px;" placeholder="请输入其他机构" disabled="disabled"  value="${busRecord.FUNCTION_OTHER}"/>
				）</div>
				
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="04" <c:if test="${busRecord.BUSINESS_TYPE=='04'}"> checked="checked"</c:if>>投资性公司</div>
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="05" <c:if test="${busRecord.BUSINESS_TYPE=='05'}"> checked="checked"</c:if>>创业投资企业</div>		
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="06" <c:if test="${busRecord.BUSINESS_TYPE=='06'}"> checked="checked"</c:if>>创业投资管理企业</div>
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="07" <c:if test="${busRecord.BUSINESS_TYPE=='07'}"> checked="checked"</c:if>>股权投资企业</div>
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="08" <c:if test="${busRecord.BUSINESS_TYPE=='08'}"> checked="checked"</c:if>>股权投资管理企业</div>
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="09" <c:if test="${busRecord.BUSINESS_TYPE=='09'}"> checked="checked"</c:if>>金融资产管理公司</div>
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="10" <c:if test="${busRecord.BUSINESS_TYPE=='10'}"> checked="checked"</c:if>>境内居民返程投资	</div>		
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="11" <c:if test="${busRecord.BUSINESS_TYPE=='11'}"> checked="checked"</c:if>>投资性公司投资</div>
				<div style="margin-bottom: 5px;"><input type="radio" class="validate[required]" name="BUSINESS_TYPE" value="12" <c:if test="${busRecord.BUSINESS_TYPE=='12'}"> checked="checked"</c:if>>创业投资企业投</div>			
				<div><input type="radio"  class="validate[required]" name="BUSINESS_TYPE" value="13" <c:if test="${busRecord.BUSINESS_TYPE=='13'}"> checked="checked"</c:if>>不涉及以上各类型</div>
			</td>
		</tr>
		<!--<tr>
			<th> 经营范围备注描述：</th>
			<td colspan="3"><input type="text" class="syj-input1" style="width:740px;"
				name="SCOPE_REMARK" maxlength="128" placeholder="请输入经营范围备注描述" value="${busRecord.SCOPE_REMARK}"/></td>
		</tr>-->
		
		<tr>
			<th>负面清单（属限制类企业选填）：</th>
			<td colspan="3">
				<textarea rows="3" cols="200" name="NEGATIVELIST_NAMES" 
					class="eve-textarea validate[],maxSize[2000]" readonly="readonly"
					style="width:90%;height:100px;"  placeholder="请选择负面清单"  onclick="showSelectFmqd();">${busRecord.NEGATIVELIST_NAMES}</textarea> 					
				<input type="hidden" name="NEGATIVELIST_CODES" value="${busRecord.NEGATIVELIST_CODES}">
				<a href="javascript:showSelectFmqd();" class="btn1">选 择</a>
			</td>
		</tr>
		<tr class="fmqd_tr" style="display: none;">
			<th><font class="syj-color2">*</font>批准证书编号：</th>
			<td><input  class="syj-input1" placeholder="请输入批准证书编号" name="CERTIFICATE_NO" type="text" maxlength="16"  value="${busRecord.CERTIFICATE_NO}"></td>
			<th><font class="syj-color2">*</font>进出口企业代码：</th>
			<td><input  class="syj-input1" placeholder="请输入进出口企业代码" name="IMPANDEXP_CODE" type="text" maxlength="16" value="${busRecord.IMPANDEXP_CODE}"></td>
		</tr>
		<tr class="fmqd_tr" style="display: none;">
			<th><font class="syj-color2">*</font>批准文号：</td>
			<td colspan="3">
				<input type="text" class="syj-input1" name="APPROVAL_NO"  placeholder="请输入批准文号" style="width: 278px;" maxlength="16" value="${busRecord.APPROVAL_NO}"/>
			</td>
		</tr>

		<tr>
			<th><font class="syj-color2">*</font>经营期限（年）：</td>
			<td>
				<input type="text" class="syj-input1 validate[required],custom[numberplus]" 
				name="BUSSINESS_YEARS"  placeholder="请输入经营期限年数" style="width: 278px;"  maxlength="4"  value="${busRecord.BUSSINESS_YEARS}"/>
				<input type="hidden" name="OPRRATE_TERM_TYPE" value="1">
			</td>
			<th><font class="syj-color2">*</font>在华投资计划描述：</td>
			<td>
				<input type="text" class="syj-input1 validate[required]" 
				name="INVEST_PLAN_DESC"  placeholder="请输入在华投资计划描述" style="width: 278px;"  maxlength="256"  value="${busRecord.INVEST_PLAN_DESC}"/>
			</td>
		</tr>
		<tr>
			<th> 计划雇佣员工数：</th>
			<td><input  class="syj-input1 validate[],custom[numberplus]"
				placeholder="请输入计划雇佣员工数" name="PLAN_EMPLOYMENT" type="text"  maxlength="8" value="${busRecord.PLAN_EMPLOYMENT}"></td>
			<th><font class="syj-color2">*</font>外籍人数：</th>
			<td><input  class="syj-input1 validate[required],custom[numberplus]"
				placeholder="请输入外籍人数" name="FOREIGN_NUM" type="text"  maxlength="8"  value="${busRecord.FOREIGN_NUM}"></td>
		</tr>
		<tr>
			<th><font class="syj-color2">*</font>职工人数/从业人数：</th>
			<td><input type="text" class="syj-input1 validate[required],custom[numberplus]"
				name="STAFF_NUM"  placeholder="请输入职工人数/从业人数"  maxlength="8"  value="${busRecord.STAFF_NUM}"/></td>
			<th> 所在地行政区：</th>
			<td><input type="text" class="syj-input1 inputBackgroundCcc" name="LOCAL_REGION" value="福州市平潭县"   placeholder="请输入所在地行政区"
				readonly="readonly"/></td>
		</tr>
	</table>
	
	<table  style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
	
	
	<tr>
		<th><font class="syj-color2">*</font>是否属于国家规定的进口设备减免税范围：</th>
		<td style="width:289.5px">				
			<input type="radio" class="validate[required]" name="IS_DUTY_FREE" value="1"  <c:if test="${busRecord.IS_DUTY_FREE=='1'}"> checked="checked"</c:if>>是
			<input type="radio" class="validate[required]" name="IS_DUTY_FREE" value="0"  <c:if test="${busRecord.IS_DUTY_FREE=='0'}"> checked="checked"</c:if>>否
		</td>
		<td colspan="2">					
			<input type="radio" name="DUTY_FREE_TYPE" value="1" disabled="disabled" <c:if test="${busRecord.DUTY_FREE_TYPE=='1'}"> checked="checked"</c:if>><span style="margin-right:28px;">国家鼓励外商投资的产业</span>
			<input type="text" class="syj-input1" name="NATIOINEN_INDUSTRY"   maxlength="64"  style="width: 238px;margin-bottom: 5px;"  placeholder="请输入国家鼓励外商投资的产业" disabled="disabled"  value="${busRecord.NATIOINEN_INDUSTRY}"/><br/>
			<input type="radio" name="DUTY_FREE_TYPE" value="2" disabled="disabled" <c:if test="${busRecord.DUTY_FREE_TYPE=='2'}"> checked="checked"</c:if>>中西部地区外商投资优势产业			
			<input type="text" class="syj-input1" name="MIDWEST_INDUSTRY"  maxlength="64" style="width: 238px;" placeholder="请输入中西部地区外商投资优势产业" disabled="disabled"  value="${busRecord.MIDWEST_INDUSTRY}"/>
		</td>
	</tr>
	</table>
	<div class="syj-title1 tmargin20">
		<a href="javascript:void(0);" onclick="javascript:addQysjkzrDiv();" class="syj-addbtn">添 加</a><span>外商投资企业最终实际控制人信息</span>
	</div>
	<div id="qysjkzrDiv">
		<c:if test="${empty controllerList}">
		<div style="position:relative;" >
			<table  cellpadding="0" cellspacing="0" class="syj-table2 tmargin20">
				<tr>
					<th><font class="syj-color2">*</font>姓名/名称（中文）：</th>
					<td><input type="text" class="syj-input1 validate[required]" name="CONTROLLER_NAME"   maxlength="64"  placeholder="请输入中文名称"/></td>
					<th> 姓名/名称（英文）：</th>
					<td><input type="text" class="syj-input1 validate[[],custom[onlyLetterNumber]]" name="CONTROLLER_NAME_ENG"  maxlength="64" placeholder="请输入英文名称"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>国籍（或地区）/注册地：</th>
					<td>
						<eve:eveselect clazz="input-dropdown  validate[required]" dataParams="ssdjgb"
							dataInterface="dictionaryService.findDatasForCountrySelect"
							defaultEmptyText="请选择国籍（或地区）/注册地" name="REGISTERED_PLACE">
						</eve:eveselect>
					</td>
					<th><font class="syj-color2">*</font>证照号码：</th>
					<td><input type="text" class="syj-input1 validate[required]" name="CONTROLLER_IDNO"  maxlength="32"  placeholder="请输入证照号码"/></td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>控制人类别：</th>
					<td colspan="3">
						<input type="radio" class="validate[required]" name="CONTROLLER_TYPE" value="01" <c:if test="${busRecord.CONTROLLER_TYPE=='01'}"> checked="checked"</c:if>>境外上市公司
						<input type="radio" class="validate[required]" name="CONTROLLER_TYPE" value="02" <c:if test="${busRecord.CONTROLLER_TYPE=='02'}"> checked="checked"</c:if>>境外自然人
						<input type="radio" class="validate[required]" name="CONTROLLER_TYPE" value="03" <c:if test="${busRecord.CONTROLLER_TYPE=='03'}"> checked="checked"</c:if>>外国政府机构（含政府基金）
						<input type="radio" class="validate[required]" name="CONTROLLER_TYPE" value="04" <c:if test="${busRecord.CONTROLLER_TYPE=='04'}"> checked="checked"</c:if>>国际组织
						<input type="radio" class="validate[required]" name="CONTROLLER_TYPE" value="05" <c:if test="${busRecord.CONTROLLER_TYPE=='05'}"> checked="checked"</c:if>>境内上市公司
						<input type="radio" class="validate[required]" name="CONTROLLER_TYPE" value="06" <c:if test="${busRecord.CONTROLLER_TYPE=='06'}"> checked="checked"</c:if>>境内自然人
						<input type="radio" class="validate[required]" name="CONTROLLER_TYPE" value="07" <c:if test="${busRecord.CONTROLLER_TYPE=='07'}"> checked="checked"</c:if>>国有/集体企业
					</td>
				</tr>
				<tr>
					<th><font class="syj-color2">*</font>实际控制方式：</th>
					<td colspan="3">
						<input type="radio" class="validate[required]" name="CONTROL_METHOD" value="01" <c:if test="${busRecord.CONTROL_METHOD=='01'}"> checked="checked"</c:if>>单独或与关联投资者共同直接或间接持有企业50%以上股份、股权、财产份额、表决权或者其他类似权益<br>
							<input type="radio" class="validate[required]" name="CONTROL_METHOD" value="02" <c:if test="${busRecord.CONTROL_METHOD=='02'}"> checked="checked"</c:if>>单独或与关联投资者共同直接或间接持有企业股份、股权、财产份额、表决权或者其他类似权益不足50%，但所享有的表决权足以对权力机构决议产生重大影响<br>
							<input type="radio"  class="validate[required]" name="CONTROL_METHOD" value="03" <c:if test="${busRecord.CONTROL_METHOD=='03'}"> checked="checked"</c:if>>对企业的经营决策、人事、财务、技术等有重大影响的其他情形（请详细说明）
							<input type="text" class="syj-input1"  style="width:200px;" placeholder="请输入其他方式" disabled="disabled" name="CONTROL_METHOD_OTHER" maxlength="128"/>
					</td>
				</tr>
			</table>		
		</div>
		</c:if>		
		<jsp:include page="./initQysjkzrDiv.jsp"></jsp:include>
	</div>
</form>
