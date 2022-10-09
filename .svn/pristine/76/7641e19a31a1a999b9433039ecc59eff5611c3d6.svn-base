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
	<tr>
		<td class="tab_width"> <font class="tab_color">*</font>名称自主选用文号：</td>
		<td colspan="3"><input type="text" class="tab_text  validate[required]"  maxlength="32"
			name="PREAPPROVAL_NUM" value="${busRecord.PREAPPROVAL_NUM }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>股东姓名/名称：</td>
		<td><input type="text" class="tab_text validate[required]"
				   name="SHAREHOLDER_NAME" value="${busRecord.SHAREHOLDER_NAME }"/></td>

		<td class="tab_width"><font class="tab_color">*</font>法定代表人姓名：</td>
		<td><input type="text" class="tab_text validate[required]"
				   name="LEGAL_NAME" value="${busRecord.LEGAL_NAME }" /></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>住所：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
			name="REGISTER_ADDR" maxlength="126" value="${busRecord.REGISTER_ADDR }"/></td>
	</tr>

	<tr>
		<td class="tab_width"><font class="tab_color">*</font>生产经营地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:740px;"
			name="BUSSINESS_ADDR" maxlength="128" value="${busRecord.BUSSINESS_ADDR }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="CONTACT_PHONE" value="${busRecord.CONTACT_PHONE }"/></td>

		<td class="tab_width"><font class="tab_color">*</font>邮政编码：</td>
		<td><input type="text" class="tab_text" disabled="disabled"
				   name="POST_CODE" value="350400" /></td>
	</tr>
	<tr>

		<td class="tab_width"><font class="tab_color">*</font>从业人数：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required],custom[numberplus]"
				   name="STAFF_NUM" value="${busRecord.STAFF_NUM}"/></td>
	</tr>

</table>
