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
			<td class="tab_width"><font class="tab_color">*</font>注册地址：</th>
			<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
				name="REGISTER_ADDR" maxlength="128"  placeholder="请输入注册地址" value="${busRecord.REGISTER_ADDR}"/></td>
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
			<td class="tab_width"> 房屋所有权人名称：</td>
			<td><input type="text" class="tab_text validate[required]" 
				name="REGISTERADD_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.REGISTERADD_OWNER}"/></td>
			<td class="tab_width"> 房屋所有权人联系电话：</td>
			<td><input type="text" class="tab_text validate[required]" 
				name="REGISTERADD_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.REGISTERADD_TEL}"/></td>

		</tr>
		<tr>
			<td class="tab_width">是否另设生产经营地址：</td>
			<td colspan="3">
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="1" 
					<c:if test="${busRecord.IS_OTHER_PLACE==1}"> checked="checked"</c:if>/>是
					<input type="radio" class="radio validate[required]" name="IS_OTHER_PLACE" value="0" 
					<c:if test="${busRecord.IS_OTHER_PLACE==0}"> checked="checked"</c:if>/>否
				</td>
		</tr>
		<tr>
			<td class="tab_width">生产经营地址：</td>
			<td colspan="3"><input disabled="false" type="text" class="tab_text validate[required]" style="width:740px;"
				name="BUSINESS_PLACE" maxlength="64"  placeholder="请输入生产经营地址" value="${busRecord.BUSINESS_PLACE}"/></td>
		</tr>
		<tr>
			<td class="tab_width"> 房屋所有权人名称：</td>
			<td><input disabled="false" type="text" class="tab_text validate[required]" 
				name="PLACE_OWNER"  placeholder="请输入房屋所有权人名称"  maxlength="32"  value="${busRecord.PLACE_OWNER}"/></td>
			<td class="tab_width"> 房屋所有权人联系电话：</td>
			<td><input disabled="false" type="text" class="tab_text validate[required]" 
				name="PLACE_TEL"  placeholder="请输入房屋所有权人联系电话"  maxlength="32"  value="${busRecord.PLACE_TEL}"/></td>

		</tr>
	<tr>
			
		<td class="tab_width"><font class="tab_color">*</font>住所产权：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="propertyRight"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择住所产权" name="PLACE_PROPERTY_RIGHT" value="${busRecord.PLACE_PROPERTY_RIGHT }">
			</eve:eveselect>		
		</td>
		<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
		<td><input disabled="false" type="text" class="tab_text validate[required],custom[chinaZip]"
			name="POST_CODE" value="350400"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>企业联系电话：</td>
		<td colspan="3"><input type="text" class="tab_text" 
			name="CONTACT_PHONE" value="${busRecord.CONTACT_PHONE}" /></td>
		
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>出资额：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:500px;"
			name="CAPITAL_CONTRIBUTION" maxlength="128" value="${busRecord.CAPITAL_CONTRIBUTION }"/>万元</td>
	</tr>
	

	<tr>
		<td class="tab_width"><font class="tab_color">*</font>合伙企业类型：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]" disabled="disabled"
				name="COMPANY_TYPE" value="${busRecord.COMPANY_TYPE }"/>		
		</td>
			

	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>合伙期限：</td>
		<td>
			<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE!=0}"> checked="checked"</c:if>>年
			<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0}"> checked="checked"</c:if>>长期
		</td>
		<td class="tab_width"> 合伙期限年数：</td>
		<td><input type="text" class="tab_text validate[[],custom[integerplus]]" 
			name="BUSSINESS_YEARS" value="${busRecord.BUSSINESS_YEARS }"/>年</td>
	</tr>
	
	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经营范围：</td>
		<td colspan="3">
			<textarea rows="3" cols="200" name="BUSSINESS_SCOPE" readonly="readonly"
				class="eve-textarea validate[validate[required],maxSize[2000]]"
			 	style="width:740px;height:100px;" >${busRecord.BUSSINESS_SCOPE }</textarea> 
			<input type="hidden" name="BUSSINESS_SCOPE_ID" value="${busRecord.BUSSINESS_SCOPE_ID }">
			<input type="button" name="scopeChose" value="选择" onclick="showSelectJyfwNew();">
			<!-- <input type="button" name="scopeChose" value="选择" onclick="choseScope()"> -->
			<div style="margin-top: 5px;margin-bottom: 5px;">
			<input type="radio" name="NO_ACCESS_MANAGE" value="1" <c:if test="${busRecord.NO_ACCESS_MANAGE==1}"> checked="checked"</c:if>>经营范围不涉及国家规定实施的准入特别管理措施
			</div>
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
		<td colspan="3"><input type="text" class="tab_text" style="width:740px;"  <c:if test="${isJyfwAll == false}">disabled="disabled"</c:if>
			name="SCOPE_REMARK" maxlength="128" value="${busRecord.SCOPE_REMARK }"/>
			<c:if test="${isJyfwAll == true}"><a class="eflowbutton" onclick="saveScopeRemark();" style="height: 28px;float: none;">保存</a></c:if></td>
	</tr>

</table>
