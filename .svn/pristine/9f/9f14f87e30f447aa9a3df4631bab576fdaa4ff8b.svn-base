<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
.textbox{
	width: 280px!important;
	height: 40px!important;
	border: none!important;
}
.textbox .textbox-text{
	width: 280px!important;
	height: 40px!important;
	line-height: 40px!important;
	font-size: 16px!important;
	color: #000000!important;
	padding: 0 10px!important;
	box-sizing: border-box!important;
	border: 1px solid #c9deef!important;
}
</style>
<form action="" id="COMPANY_FORM"  >
	<input type="hidden" id= "COMPANY_ID" name="COMPANY_ID" value="${busRecord.COMPANY_ID}"/>
	<div class="syj-title1 ">
		<span>基本信息</span>
	</div>
	<input type="hidden" id= "CANCEL_TYPE" name="CANCEL_TYPE" value="${requestParams.COMPANY_TYPECODE}"/>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>注销类型：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 inputBackgroundCcc validate[required]" readonly="readonly"
						name="CANCEL_TYPENAME" id="CANCEL_TYPENAME" maxlength="64" placeholder="请输入注销类型" value="${requestParams.COMPANY_TYPE}"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>企业名称：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 w878 validate[required]" 
						name="COMPANY_NAME" id="COMPANY_NAME" maxlength="64" placeholder="请输入企业名称" value="${busRecord.COMPANY_NAME}"/>						
						<a href="javascript:void(0);" class="eflowbutton" id="showEnterpriseInfo" onclick="showEnterpriseInfo();">查询</a>
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " >
			<tr>
				<th ><font class="syj-color2">*</font>统一社会信用代码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="COMPANY_CODE" maxlength="32" value="${busRecord.COMPANY_CODE}" placeholder="请输入统一社会信用代码"/></td>			
				<th><font class="syj-color2">*</font>公司类型：</th>
				<td>
					<input type="hidden" id= "COMPANY_TYPECODE" name="COMPANY_TYPECODE" value="${busRecord.COMPANY_TYPECODE}"/>
					<input name="COMPANY_TYPE" id="COMPANY_TYPE" class="easyui-combobox" style="width:280px;height:40px;border: 1px solid #c9deef!important;"
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
				<th ><font class="syj-color2">*</font>住所：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[required]"
					name="REGISTER_ADDR" maxlength="128" value="${busRecord.REGISTER_ADDR}" placeholder="请输入住所"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>注销原因：</th>
				<td colspan="3">
					<input name="CANCEL_REASON" id="cancelReason" class="syj-input1 easyui-combobox"
					  value="${busRecord.CANCEL_REASON}"/>
					<input type="text" class="syj-input1" name="CANCEL_REASON_OTHER" 
					maxlength="32" value="${busRecord.CANCEL_REASON_OTHER}" placeholder="请输入其他情形"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>国家企业信用信息公示系统公告日期：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 Wdate validate[required]" id="NOTICE_START_DATE" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'NOTICE_END_DATE\',{d:-20})||\'%y-%M-{%d-20}\'}'})"
					readonly="readonly" name="NOTICE_START_DATE"  placeholder="请输入开始时间" value="${busRecord.NOTICE_START_DATE}"  maxlength="16"/> 至
					<input type="text" class="syj-input1 Wdate validate[required]" id="NOTICE_END_DATE" readonly="readonly" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'NOTICE_START_DATE\',{d:20})}',maxDate:'%y-%M-%d'})"
					name="NOTICE_END_DATE"  placeholder="请输入结束时间" value="${busRecord.NOTICE_END_DATE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>适用情形：</th>
				<td colspan="3">
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="SYQX"
					dataInterface="dictionaryService.findDatasForSelect" onchange="setSyqxValidate(this.value,'SYQX2');"
					defaultEmptyText="请选择适用情形" name="SYQX1"  value="${busRecord.SYQX1}">
					</eve:eveselect>
					
					<eve:eveselect clazz="input-dropdown w280" dataParams="SYQX2"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择债务情况" name="SYQX2"  value="${busRecord.SYQX2}">
					</eve:eveselect>
				</td>
			</tr>
		</table>
	</div>
	<div  class="ybzx">
	<jsp:include page="/webpage/website/applyforms/ssqcwb/cancel/ybzxxx.jsp" />
	</div>
	<div class="syj-title1 ">
		<span>法定代表人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>法定代表人姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="LEGAL_NAME"  placeholder="请输入法定代表人姓名"   maxlength="16" value="${busRecord.LEGAL_NAME}"/></td>
				<th> 职务：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="ssdjzw:02,03,10,11,12,21"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择职务" name="LEGAL_JOB"  value="${busRecord.LEGAL_JOB}">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td colspan="3"><input type="text" class="syj-input1  validate[required,custom[mobilePhone]]"
					name="LEGAL_MOBILE"  placeholder="请输入移动电话"  value="${busRecord.LEGAL_MOBILE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LEGAL_IDNO');"
					defaultEmptyText="请选择证件类型" name="LEGAL_IDTYPE" id="LEGAL_IDTYPE"   value="${busRecord.LEGAL_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="LEGAL_IDNO"  placeholder="请输入证件号码"  maxlength="32"   value="${busRecord.LEGAL_IDNO}"/></td>
			</tr>

		</table>
	</div>
	
	<div class="syj-title1 ">
		<span>经办人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><font class="syj-color2">*</font>指定或委托的有效期限：</th>
				<td colspan="3">
					<input type="text" class="syj-input1 Wdate validate[required]" id="VALIDITY_START_DATE" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'VALIDITY_END_DATE\')}'})"
					readonly="readonly" name="VALIDITY_START_DATE"  placeholder="请输入开始时间" value="${busRecord.VALIDITY_START_DATE}"  maxlength="16"/>至
					<input type="text" class="syj-input1 Wdate validate[required]" id="VALIDITY_END_DATE" readonly="readonly" 
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{M:1})||\'%y-{%M+1}-%d\'}'
					,maxDate:'#F{$dp.$D(\'VALIDITY_START_DATE\',{y:1})||\'{%y+1}-%M-%d\'}'})"
					name="VALIDITY_END_DATE"  placeholder="请输入结束时间" value="${busRecord.VALIDITY_END_DATE}"  maxlength="16"/></td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="syj-table2 " >
			<tr>
				<th><font class="syj-color2">*</font>经办人（被委托人）</br>姓名：</th>
				<td><input type="text" class="syj-input1 validate[required]" 
					name="OPERATOR_NAME"  placeholder="请输入经办人（被委托人）姓名"  maxlength="8"  value="${busRecord.OPERATOR_NAME}"/></td>
				<th><font class="syj-color2">*</font>移动电话：</th>
				<td><input type="text" class="syj-input1  validate[required,custom[mobilePhone]]"
					name="OPERATOR_MOBILE"  placeholder="请输入移动电话" value="${busRecord.OPERATOR_MOBILE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th> 固定电话：</th>
				<td colspan="3"><input type="text" class="syj-input1 validate[]"
					name="OPERATOR_FIXEDLINE" placeholder="请输入固定电话" value="${busRecord.OPERATOR_FIXEDLINE}"  maxlength="16"/></td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>证件类型：</th>
				<td>
					<eve:eveselect clazz="input-dropdown w280 validate[required]" dataParams="DocumentType"
					dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'OPERATOR_IDNO');"
					defaultEmptyText="请选择证件类型" name="OPERATOR_IDTYPE" value="${busRecord.OPERATOR_IDTYPE}">
					</eve:eveselect>
				</td>
				<th><font class="syj-color2">*</font>证件号码：</th>
				<td><input type="text" class="syj-input1 validate[required]"
					name="OPERATOR_IDNO" placeholder="请输入证件号码" maxlength="32" value="${busRecord.OPERATOR_IDNO}" /></td>
			</tr>
		</table>
	</div>
	<c:if test="${busRecord.ISEMAIL==1||requestParams.ISEMAIL==1}">
	<div class="syj-title1 ">
		<span>邮寄营业执照</span>
	</div>
	<div style="position:relative;display:none">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th> 是否邮寄营业执照：</th>
				<td>
					<input type="radio" name="ISEMAIL" value="1" <c:if test="${busRecord.ISEMAIL==1}"> checked="checked"</c:if>/>是
					<input type="radio" name="ISEMAIL" value="0" <c:if test="${busRecord.ISEMAIL!=1}"> checked="checked"</c:if>/>否
				</td>
			</tr>
		</table>
	</div>
	<div style="position:relative;display: none;" id="emailInfo">

		<table cellpadding="0" cellspacing="0" class="syj-table2 ">

			<tr>
				<th><font class="syj-color2">*</font>收件人姓名：</th>
				<td>
					<input type="text" class="syj-input1 " maxlength="16" placeholder="请输入收件人姓名"
						   name="EMS_RECEIVER"  value="${busRecord.EMS_RECEIVER}"/>
				</td>
				<th><font class="syj-color2">*</font>收件人电话：</th>
				<td>
					<input type="text" class="syj-input1 validate[custom[mobilePhone]]" maxlength="16"
						   name="EMS_PHONE"  value="${busRecord.EMS_PHONE}"  placeholder="请输入收件人电话"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>寄送地址：</th>
				<td colspan="3" >
					<input type="text" class="syj-input1  w878" maxlength="100"
						   name="EMS_ADDRESS"  value="${busRecord.EMS_ADDRESS}" placeholder="请输入寄送地址"/>
				</td>
			</tr>
			<tr>
				<th><font class="syj-color2">*</font>寄送城市：</th>
				<td colspan="3" >
					<input type="text" class="syj-input1  w878" maxlength="64" placeholder="请输入寄送城市"
						   name="EMS_CITY"  value="${busRecord.EMS_CITY}"/>
				</td>
			</tr>
		</table>
	</div>
	</c:if>
</form>
