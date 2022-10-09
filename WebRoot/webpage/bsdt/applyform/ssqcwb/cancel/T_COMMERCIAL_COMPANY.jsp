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
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注销类型：</td>
		<td colspan="3">
			<input type="text" class="tab_text validate[required]"  maxlength="32"
				   name="CANCEL_TYPENAME" value="${busRecord.CANCEL_TYPENAME }"/>
		</td>
	</tr>
	<tr >
		<td class="tab_width"><font class="tab_color">*</font>企业名称：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="128"
				   name="COMPANY_NAME" value="${busRecord.COMPANY_NAME }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>统一社会信用代码：</td>
		<td>
			<input  type="text" class="tab_text  "
					name="COMPANY_CODE"  placeholder="请输入统一社会信用代码"  maxlength="32"  value="${busRecord.COMPANY_CODE}"/></td>
		<td class="tab_width"><font class="tab_color">*</font>公司类型：</td>
		<td>
			<!--<input type="text" class="tab_text "
				   name="COMPANY_TYPE"  placeholder="请输入公司类型"  maxlength="32"  value="${busRecord.COMPANY_TYPE}"/>-->
			<input name="COMPANY_TYPE" id="COMPANY_TYPE" class="easyui-combobox" style="width:300px;height:30px;"
						data-options="
							url:'dicTypeController/load.do?parentTypeCode=yxzrgssl',
							method:'post',
							valueField:'TYPE_CODE',
							textField:'TYPE_NAME',
							panelHeight:'auto',
							required:true,
							editable:false
					" value="${busRecord.COMPANY_TYPE }" />		
		</td>
	</tr>
	<tr  class="ybzx">
		<td class="tab_width"><font class="tab_color">*</font>住所：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" style="width:600px;"
			name="REGISTER_ADDR" maxlength="126" value="${busRecord.REGISTER_ADDR }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>注销原因：</td>
		<td colspan="3" style="width:500px;">			
			<input name="CANCEL_REASON" id="cancelReason" class="easyui-combobox validate[required]"
			style="width:360px; height:30px;"  value="${busRecord.CANCEL_REASON}"/>&nbsp;&nbsp;
			<input type="text" class="tab_text" name="CANCEL_REASON_OTHER" style="width:282px;display:none;"
			maxlength="32" value="${busRecord.CANCEL_REASON_OTHER}" placeholder="请输入其他情形"/>
		</td>
	</tr>	
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>国家企业信用信息公示系统公告日期：</td>
		<td colspan="3">
			<input type="text" class="Wdate validate[required]" id="NOTICE_START_DATE" style="height: 28px;  line-height: 28px;"			
			readonly="readonly" name="NOTICE_START_DATE"  placeholder="请输入开始时间" value="${busRecord.NOTICE_START_DATE}"  maxlength="16"/>至
			<input type="text" class="Wdate validate[required]" id="NOTICE_END_DATE" readonly="readonly" style="height: 28px;  line-height: 28px;"
			name="NOTICE_END_DATE"  placeholder="请输入结束时间" value="${busRecord.NOTICE_END_DATE}"  maxlength="16"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>适用情形：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="SYQX"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setSyqxValidate(this.value,'SYQX2');"
			defaultEmptyText="请选择适用情形" name="SYQX1"  value="${busRecord.SYQX1}">
			</eve:eveselect>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<eve:eveselect clazz="tab_text input-dropdown" dataParams="SYQX2"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择债务情况" name="SYQX2"  value="${busRecord.SYQX2}">
			</eve:eveselect>
		</td>
	</tr>
</table>


	<div  class="ybzx">
	<jsp:include page="./ybzxxx.jsp" />
	</div>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >法定代表人基本信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>法定代表人姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="LEGAL_NAME" maxlength="8" value="${busRecord.LEGAL_NAME }"/></td>
		<td class="tab_width"> 职务：</td>
		<td>
			<eve:eveselect clazz="tab_text input-dropdown validate[required]" dataParams="ssdjzw:02,03,10,11,12,21"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择职务" name="LEGAL_JOB"  value="${busRecord.LEGAL_JOB}">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>移动电话：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]"
			name="LEGAL_MOBILE" value="${busRecord.LEGAL_MOBILE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" value="${busRecord.LEGAL_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]" onblur="checkDifferent(this.name);"
			name="LEGAL_IDNO" maxlength="32" value="${busRecord.LEGAL_IDNO }"/></td>
	</tr>
</table>

<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >经办人信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>指定或委托的有效期限：</td>
		<td colspan="3"><input type="text" class="laydate-icon validate[required]" id="vstart" readonly="readonly"
			name="VALIDITY_START_DATE"  value="${busRecord.VALIDITY_START_DATE }"/>至
			<input type="text" class="laydate-icon validate[required]" id="vend" readonly="readonly"
			name="VALIDITY_END_DATE"  value="${busRecord.VALIDITY_END_DATE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>经办人（被委托人）姓名：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="OPERATOR_NAME" maxlength="8" value="${busRecord.OPERATOR_NAME }"/></td>
		<td class="tab_width"><font class="tab_color">*</font>联系电话：</td>
		<td><input type="text" class="tab_text validate[required]" 
			name="OPERATOR_MOBILE" value="${busRecord.OPERATOR_MOBILE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"> 固定电话：</td>
		<td colspan="3"><input type="text" class="tab_text"
			name="OPERATOR_FIXEDLINE" value="${busRecord.OPERATOR_FIXEDLINE }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择证件类型" name="OPERATOR_IDTYPE" value="${busRecord.OPERATOR_IDTYPE }">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td><input type="text" class="tab_text validate[required]"
			name="OPERATOR_IDNO" maxlength="32" value="${busRecord.OPERATOR_IDNO }"/></td>
	</tr>
</table>


<c:if test="${busRecord.ISEMAIL==1}">
<table cellpadding="0" cellspacing="1" class="tab_tk_pro2">
	<tr>
		<th colspan="4" >邮寄营业执照信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 收件人姓名：</td>
		<td >
			<input type="text" class="tab_text"
				   name="EMS_RECEIVER"  value="${busRecord.EMS_RECEIVER }"/>
		</td>
		<td class="tab_width"> 收件人电话：</td>
		<td >
			<input type="text" class="tab_text"
				   name="EMS_PHONE"  value="${busRecord.EMS_PHONE }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 寄送地址：</td>
		<td  colspan="3">
			<input type="text" class="tab_text"  style="width: 700px;"
				   name="EMS_ADDRESS"  value="${busRecord.EMS_ADDRESS }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 寄送城市：</td>
		<td  colspan="3">
			<input type="text" class="tab_text"
				   name="EMS_CITY"  value="${busRecord.EMS_CITY }"/>
		</td>
	</tr>
</table>
</c:if>