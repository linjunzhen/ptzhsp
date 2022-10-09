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
.combo-arrow{
	margin:0!important;
}
</style>
<%--面签审核界面 --%>
<div id="SIGNAUDIT"  style="display: none" >
	<jsp:include page="./signAudit.jsp" />
</div>
<%--结束面签审核界面 --%>
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4">企业基本信息</th>
	</tr>
	<tr >
		<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px" maxlength="128"
				   name="COMPANY_NAME" value="${busRecord.COMPANY_NAME }"/></td>
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
		<td class="tab_width"><font class="tab_color">*</font>统一社会信用代码：</td>
		<td>
			<input  type="text" class="tab_text  "
					name="COMPANY_CODE"  placeholder="请输入统一社会信用代码"  maxlength="32"  value="${busRecord.COMPANY_CODE}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>公司类型：</td>
		<td>
			<input type="text" class="tab_text "
				   name="COMPANY_TYPE"  placeholder="请输入公司类型"  maxlength="32"  value="${busRecord.COMPANY_TYPE}"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注册地址：</td>
		<td><input type="text" class="tab_text validate[required]" style="width:600px;"
			name="REGISTER_ADDR" maxlength="126" value="${busRecord.REGISTER_ADDR }"/></td>
		<td class="tab_width">面积：</td>
		<td><input type="text" class="tab_text" maxlength="16" style="width:50%;height:20px;"
				   name="REGISTER_SQUARE_ADDR" placeholder="请输入面积" value="${busRecord.REGISTER_SQUARE_ADDR}"/>
			<font style="font-size: 16px;margin-left: 10px;">㎡</font>
		</td>
	</tr>
	<tr>
		<td style="width: 170px;background: #fbfbfb;"><font class="tab_color">*</font>公司设立性质：</td>
		<td colspan="3">			               					
			<input name="COMPANY_SETNATURE" id="sllx" class="easyui-combobox" style="width:282px; height:26px;" 
				data-options="			                
					url:'dictionaryController/loadData.do?typeCode=gykg&orderType=asc',
					method:'post',
					valueField:'DIC_CODE',
					textField:'DIC_NAME',
					panelHeight:'auto',
					required:true,
					editable:false,
					onSelect:function(rec){	
					  
					}
			" value="${busRecord.COMPANY_SETNATURE }" />
		</td>
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
		<td class="tab_width"><font class="tab_color">*</font>经营期限：</td>
		<td>
			<input type="radio" name="OPRRATE_TERM_TYPE" value="1" <c:if test="${busRecord.OPRRATE_TERM_TYPE==1 }">checked="checked"</c:if> >年
			<input type="radio" name="OPRRATE_TERM_TYPE" value="0" <c:if test="${busRecord.OPRRATE_TERM_TYPE==0 }">checked="checked"</c:if> >长期
		</td>
		<td class="tab_width"> 经营期限年数：</td>
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
</table>
