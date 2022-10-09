<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
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
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">企业基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]"  style="width:740px;"
				name="COMPANY_NAME" maxlength="64"  value="${busRecord.COMPANY_NAME }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 企业名称（英文）：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[[],custom[onlyLetterNumber]]"  style="width:740px;"
				name="COMPANY_NAME_ENG" maxlength="64"  value="${busRecord.COMPANY_NAME_ENG }"/>
		</td>
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
		<td class="tab_width"><font class="tab_color">*</font>国别：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjgb"
			dataInterface="dictionaryService.findDatasForSelectPinyinOrder"
			defaultEmptyText="请选择国别" name="COMPANY_COUNTRY" value="${busRecord.COMPANY_COUNTRY }">
			</eve:eveselect>
		</td>
	</tr>
<%--	<tr>--%>
<%--		<td class="tab_width"><font class="tab_color">*</font>是否已通过名称自主选用：</td>--%>
<%--		<td>--%>
<%--			<input type="radio" name="IS_PREAPPROVAL_PASS" value="1" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==1 }">checked="checked"</c:if> >是--%>
<%--			<input type="radio" name="IS_PREAPPROVAL_PASS" value="0" <c:if test="${busRecord.IS_PREAPPROVAL_PASS==0 }">checked="checked"</c:if> >否--%>
<%--		</td>--%>
<%--		<td class="tab_width"> 名称自主选用文号：</td>--%>
<%--		<td><input type="text" class="tab_text" disabled="disabled" maxlength="32"--%>
<%--			name="PREAPPROVAL_NUM"  value="${busRecord.PREAPPROVAL_NUM }"/></td>--%>
<%--	</tr>--%>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>（住所）注册地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
			name="REGISTER_ADDR" maxlength="126" value="${busRecord.REGISTER_ADDR }" onblur="checkFat(this.value);"/></td>
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
		<td class="tab_width"><font class="tab_color">*</font>是否自贸区：</td>
		<td >
			<input type="radio" name="FAT_TYPE" value="1" <c:if test="${busRecord.FAT_TYPE==1 }">checked="checked"</c:if> >是
			<input type="radio" name="FAT_TYPE" value="0" <c:if test="${busRecord.FAT_TYPE==0 }">checked="checked"</c:if> >否
		</td>
		<td class="tab_width"> 所属自贸区片区：</td>
		<td >
			<input type="text" class="tab_text" disabled="disabled" maxlength="62"
					name="FAT_AREA"  value="${busRecord.FAT_AREA }" />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>是否位于国家级经济技术开发区内：</td>
		<td >
			<%-- <input type="radio" name="IS_ETDZ" value="1" <c:if test="${busRecord.IS_ETDZ==1 }">checked="checked"</c:if> >是 --%>
			<input type="radio" name="IS_ETDZ" value="0" <c:if test="${busRecord.IS_ETDZ==0 }">checked="checked"</c:if> >否
		</td>
		<td class="tab_width"><font class="tab_color">*</font>企业类型：</td>
		<td >
			<input type="radio" name="COMPANY_NATURE" value="01" <c:if test="${busRecord.COMPANY_NATURE==01 }">checked="checked"</c:if> >合资<br>
			<input type="radio" name="COMPANY_NATURE" value="02" <c:if test="${busRecord.COMPANY_NATURE==02 }">checked="checked"</c:if> >合作<br>
			<input type="radio" name="COMPANY_NATURE" value="03" <c:if test="${busRecord.COMPANY_NATURE==03 }">checked="checked"</c:if> >独资<br>
			<input type="radio" name="COMPANY_NATURE" value="04" <c:if test="${busRecord.COMPANY_NATURE==04 }">checked="checked"</c:if> >股份制
			
			<span id="stock" style="display: none;">
				：<input type="radio" name="JOINT_STOCK_TYPE" value="1" <c:if test="${busRecord.JOINT_STOCK_TYPE==1 }">checked="checked"</c:if> >上市
				<input type="radio" name="JOINT_STOCK_TYPE" value="0" <c:if test="${busRecord.JOINT_STOCK_TYPE==0 }">checked="checked"</c:if> >非上市
				<span id="unlist" style="display: none;">
					(<input type="radio" name="UNLISTED_TYPE" value="1" <c:if test="${busRecord.UNLISTED_TYPE==1 }">checked="checked"</c:if> >公众公司
					<input type="radio" name="UNLISTED_TYPE" value="2" <c:if test="${busRecord.UNLISTED_TYPE==2 }">checked="checked"</c:if> >其他)
				</span>
			</span>
		</td>
		<%-- <td class="tab_width"> 所属经开区片区：</td>
		<td >
			<input type="text" class="tab_text" disabled="disabled" maxlength="62"
					name="ETDZ_AREA"  value="${busRecord.ETDZ_AREA }" />
		</td> --%>
	</tr>
	<tr>
		<td class="tab_width" rowspan="4"><font class="tab_color">*</font>是否仅作为联络地址：</td>
		<td rowspan="4">
			<input type="radio" name="IS_ONLY_CONTACT" value="1" <c:if test="${busRecord.IS_ONLY_CONTACT==1 }">checked="checked"</c:if> >是
			<input type="radio" name="IS_ONLY_CONTACT" value="0" <c:if test="${busRecord.IS_ONLY_CONTACT==0 }">checked="checked"</c:if> >否
		</td>
	</tr>
	<tr>
		<td class="tab_width">出租（借）方：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="LESSOR" maxlength="32" value="${busRecord.LESSOR }"/></td>
	</tr>
	<tr>
		<td class="tab_width">出租（借）时间：</td>
		<td><input type="text" class="laydate-icon" id="start" disabled="disabled" readonly="readonly"
			name="LEASE_START_DATE" value="${busRecord.LEASE_START_DATE }"/>至
			<input type="text" class="laydate-icon" id="end" disabled="disabled" readonly="readonly"
			name="LEASE_END_DATE" value="${busRecord.LEASE_END_DATE }"/></td>
	</tr>
	<tr>
		<td class="tab_width">租赁合同签订时间：</td>
		<td><input type="text" class="laydate-icon" id="sign" disabled="disabled" readonly="readonly"
			name="SINGING_TIME" value="${busRecord.SINGING_TIME }"/></td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>成立方式：</td>
		<td>
			<input type="radio" name="ESTABLISH_PATTERN" value="1" <c:if test="${busRecord.ESTABLISH_PATTERN==1 }">checked="checked"</c:if> >新设
		</td>
		<td colspan="2">
			<input type="radio" name="NEW_TYPE" value="01" <c:if test="${busRecord.NEW_TYPE=='01' }">checked="checked"</c:if> >普通新设<br>
			<input type="radio" name="NEW_TYPE" value="02" <c:if test="${busRecord.NEW_TYPE=='02' }">checked="checked"</c:if> >新设合并
			<input type="radio" name="NEW_TYPE" value="03" <c:if test="${busRecord.NEW_TYPE=='03' }">checked="checked"</c:if> >吸收合并<br>
			<input type="radio" name="NEW_TYPE" value="04" <c:if test="${busRecord.NEW_TYPE=='04' }">checked="checked"</c:if> >存续分立
			<input type="radio" name="NEW_TYPE" value="05" <c:if test="${busRecord.NEW_TYPE=='05' }">checked="checked"</c:if> >解散分立<br>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>生产经营地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
			name="BUSSINESS_ADDR" maxlength="128" value="${busRecord.BUSSINESS_ADDR }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业联系电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="CONTACT_PHONE" value="${busRecord.CONTACT_PHONE }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="POST_CODE" value="350400" /></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>公司类型：</td>
		<td>
			<input type="text" class="tab_text validate[required]" disabled="disabled"
				name="COMPANY_TYPE"  value="${busRecord.COMPANY_TYPE }"/>			
		</td>
		<td class="tab_width"><font class="tab_color">*</font>企业规模：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="ssdjqyfl"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择企业声明" name="ENTERPRISE_STATEMENT" value="${busRecord.ENTERPRISE_STATEMENT }">
			</eve:eveselect>
			<img src="webpage/bsdt/applyform/images/question.png" style="cursor: pointer;" onclick="window.open('<%=path%>/contentController/view.do?contentId=996');">
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" 
				class="eve-textarea validate[validate[required],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.BUSSINESS_SCOPE }</textarea> 
			<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID }">
			<input type="button" name="scopeChose" value="选择" onclick="showSelectJyfwNew();">
			<!-- <input type="button" name="scopeChose" value="选择" onclick="choseScope()"> -->
			<br>
			<input type="checkbox"  class="checkbox" 
			name="NO_ACCESS_MANAGE" value="1" <c:if test="${busRecord.NO_ACCESS_MANAGE==1 }">checked="checked"</c:if> />经营范围不涉及国家规定实施的准入特别管理措施
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
	<tr>
		<td class="tab_width"> 投资行业：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="INVEST_INDUSTRY" 
				class="eve-textarea validate[validate[],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.INVEST_INDUSTRY }</textarea>  					
			<input type="hidden" name="INVEST_INDUSTRY_ID" value="${busRecord.INVEST_INDUSTRY_ID }">
			<!-- <input type="button" name="industryChose" value="选择" onclick="showSelectTzhy()"> -->
			<!-- <input type="button" name="industryChose" value="选择" onclick="choseIndustry()"> -->
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 行业编码：</td>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;" disabled="disabled"
			name="INDUSTRY_CODE" value="${busRecord.INDUSTRY_CODE }"/></td>
	</tr>
	
	<tr>
		<td class="tab_width"> 主营行业：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="MAIN_INDUSTRY" 
				class="eve-textarea validate[validate[],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.MAIN_INDUSTRY }</textarea>  					
			<input type="hidden" name="MAIN_INDUSTRY_ID" value="${busRecord.MAIN_INDUSTRY_ID }">
			<input type="button" name="mainIndustryChose" value="选择" onclick="showSelectZyhy()">
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>业务类型：</td>
		<td colspan="3">
			<input type="radio" name="BUSINESS_TYPE" value="01" <c:if test="${busRecord.BUSINESS_TYPE=='01' }">checked="checked"</c:if> >高新技术企业<br>
			<input type="radio" name="BUSINESS_TYPE" value="02" <c:if test="${busRecord.BUSINESS_TYPE=='02' }">checked="checked"</c:if> >研发中心
			<span id="research" style="display: none;">
				(<input type="radio" name="RESEARCH_TYPE" value="01" <c:if test="${busRecord.RESEARCH_TYPE=='01' }">checked="checked"</c:if> >独立法人研发中心
				<input type="radio" name="RESEARCH_TYPE" value="02" <c:if test="${busRecord.RESEARCH_TYPE=='02' }">checked="checked"</c:if> >非独立法人研发中心)
			</span>
			<br>
			<input type="radio" name="BUSINESS_TYPE" value="03" <c:if test="${busRecord.BUSINESS_TYPE=='03' }">checked="checked"</c:if> >功能性机构
			<span id="function" style="display: none;">
				(<input type="radio" name="FUNCTION_TYPE" value="01" <c:if test="${busRecord.FUNCTION_TYPE=='01' }">checked="checked"</c:if> >地区总部
				<input type="radio" name="FUNCTION_TYPE" value="02" <c:if test="${busRecord.FUNCTION_TYPE=='02' }">checked="checked"</c:if> >采购中心
				<input type="radio" name="FUNCTION_TYPE" value="03" <c:if test="${busRecord.FUNCTION_TYPE=='03' }">checked="checked"</c:if> >财务管理中心
				<input type="radio" name="FUNCTION_TYPE" value="04" <c:if test="${busRecord.FUNCTION_TYPE=='04' }">checked="checked"</c:if> >结算中心
				<input type="radio" name="FUNCTION_TYPE" value="05" <c:if test="${busRecord.FUNCTION_TYPE=='05' }">checked="checked"</c:if> >销售中心
				<input type="radio" name="FUNCTION_TYPE" value="06" <c:if test="${busRecord.FUNCTION_TYPE=='06' }">checked="checked"</c:if> >分拨中心
				<input type="radio" name="FUNCTION_TYPE" value="07" <c:if test="${busRecord.FUNCTION_TYPE=='07' }">checked="checked"</c:if> >其他
				<input type="text" class="tab_text" disabled="disabled" name="FUNCTION_OTHER" value="${busRecord.FUNCTION_OTHER }"/>)
			</span>			
			<br>
			<input type="radio" name="BUSINESS_TYPE" value="04" <c:if test="${busRecord.BUSINESS_TYPE=='04' }">checked="checked"</c:if> >投资性公司
			<input type="radio" name="BUSINESS_TYPE" value="05" <c:if test="${busRecord.BUSINESS_TYPE=='05' }">checked="checked"</c:if> >创业投资企业
			<input type="radio" name="BUSINESS_TYPE" value="06" <c:if test="${busRecord.BUSINESS_TYPE=='06' }">checked="checked"</c:if> >创业投资管理企业
			<input type="radio" name="BUSINESS_TYPE" value="07" <c:if test="${busRecord.BUSINESS_TYPE=='07' }">checked="checked"</c:if> >股权投资企业
			<input type="radio" name="BUSINESS_TYPE" value="08" <c:if test="${busRecord.BUSINESS_TYPE=='08' }">checked="checked"</c:if> >股权投资管理企业
			<input type="radio" name="BUSINESS_TYPE" value="09" <c:if test="${busRecord.BUSINESS_TYPE=='09' }">checked="checked"</c:if> >金融资产管理公司<br>
			<input type="radio" name="BUSINESS_TYPE" value="10" <c:if test="${busRecord.BUSINESS_TYPE=='10' }">checked="checked"</c:if> >境内居民返程投资
			<input type="radio" name="BUSINESS_TYPE" value="11" <c:if test="${busRecord.BUSINESS_TYPE=='11' }">checked="checked"</c:if> >投资性公司投资
			<input type="radio" name="BUSINESS_TYPE" value="12" <c:if test="${busRecord.BUSINESS_TYPE=='12' }">checked="checked"</c:if> >创业投资企业投资<br>
			<input type="radio" name="BUSINESS_TYPE" value="13" <c:if test="${busRecord.BUSINESS_TYPE=='13' }">checked="checked"</c:if> >不涉及以上各类型
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 负面清单（属限制类企业选填）：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="NEGATIVELIST_NAMES" 
				class="eve-textarea validate[[],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.NEGATIVELIST_NAMES }</textarea> 
			<input type="hidden" name="NEGATIVELIST_CODES" value="${busRecord.NEGATIVELIST_CODES }">
			<input type="button" name="fmqdChose" value="选择" onclick="showSelectFmqd();">
		</td>
	</tr>
	<tr style="display: none;" id="fmqdtr1">
		<td class="tab_width"><font class="tab_color">*</font>批准证书编号：</td>
		<td><input type="text" class="tab_text validate[required]" disabled="disabled"
			name="CERTIFICATE_NO" value="${busRecord.CERTIFICATE_NO }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>进出口企业代码：</td>
		<td><input type="text" class="tab_text validate[required]" disabled="disabled"
			name="IMPANDEXP_CODE" value="${busRecord.IMPANDEXP_CODE }"/></td>
	</tr>
	<tr style="display: none;" id="fmqdtr2">
		<td class="tab_width"><font class="tab_color">*</font>批准文号：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" disabled="disabled"
			name="APPROVAL_NO" value="${busRecord.APPROVAL_NO }"/></td>
	</tr>
	<!-- <tr>
		<td class="tab_width"> 经营范围备注描述：</td>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			name="SCOPE_REMARK" maxlength="128"/></td>
	</tr> -->
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>营业期限：</td>
		<td colspan="3"><input type="text" class="tab_text validate[[],custom[integerplus]]" 
			name="BUSSINESS_YEARS" value="${busRecord.BUSSINESS_YEARS }"/>年</td>
	</tr>
	<tr>
		<td class="tab_width"> 计划雇佣员工数：</td>
		<td><input type="text" class="tab_text"
			name="PLAN_EMPLOYMENT" value="${busRecord.PLAN_EMPLOYMENT }"/>人</td>
		<td class="tab_width"><font class="tab_color">*</font>外籍人数：</td>
		<td><input type="text" class="tab_text validate[required,custom[integerplus]]"
			name="FOREIGN_NUM" value="${busRecord.FOREIGN_NUM }"/>人</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>职工人数/从业人数：</td>
		<td><input type="text" class="tab_text validate[required,custom[integerplus]]"
			name="STAFF_NUM" value="${busRecord.STAFF_NUM }"/>人</td>
		<td class="tab_width"> 所在地行政区：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
			name="LOCAL_REGION" value="福州市平潭县" /></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>是否属于国家规定的进口设备减免税范围：</td>
		<td >
			<input type="radio" name="IS_DUTY_FREE" value="1" <c:if test="${busRecord.IS_DUTY_FREE=='1' }">checked="checked"</c:if> />是
			
			<input type="radio" name="IS_DUTY_FREE" value="0" <c:if test="${busRecord.IS_DUTY_FREE=='0' }">checked="checked"</c:if> />否
		</td>
		<td colspan="2">
				<input type="radio" disabled="disabled" name="DUTY_FREE_TYPE" value="1" <c:if test="${busRecord.DUTY_FREE_TYPE==1 }">checked="checked"</c:if> >国家鼓励外商投资的产业　　
				<input type="text" class="tab_text" disabled="disabled" maxlength="62"
					name="NATIOINEN_INDUSTRY"  value="${busRecord.NATIOINEN_INDUSTRY }" />
				<br>
				<input type="radio" disabled="disabled" name="DUTY_FREE_TYPE" value="2" <c:if test="${busRecord.DUTY_FREE_TYPE==2 }">checked="checked"</c:if> >中西部地区外商投资优势产业
				<input type="text" class="tab_text" disabled="disabled" maxlength="62"
					name="MIDWEST_INDUSTRY"  value="${busRecord.MIDWEST_INDUSTRY }" />
		</td>
	</tr>
	
	<tr>
		<td class="tab_width"> 在华投资计划描述：</td>
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"
			name="INVEST_PLAN_DESC" maxlength="128" value="${busRecord.INVEST_PLAN_DESC }"/></td>
	</tr>
</table>
