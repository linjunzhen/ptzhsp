<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
$(function(){
	var userType= '${sessionScope.curLoginMember.USER_TYPE}';
	if(userType==1){
		//$("#sqjg").attr("style","display:none;");
	}else{
		//$("#sqr").attr("style","display:none;");
	}
	var sqrzjlx=$("#sqrzjlx").val();
	var jbrzjlx=$("#jbrzjlx").val();
	setValidate(sqrzjlx);
	setValidatejbr(jbrzjlx);
})

function setValidate(zjlx){
	if(zjlx=="SF"){
		$("#zjhm").addClass(",custom[vidcard]");
	}else{
		$("#zjhm").removeClass(",custom[vidcard]");
	}
}
function setValidatejbr(jbrzjlx){
	if(jbrzjlx=="SF"){
		$("#jbrzjhm").addClass(",custom[vidcard]");
	}else{
		$("#jbrzjhm").removeClass(",custom[vidcard]");
	}
}
</script>
<form action="" id="USERINFO_FORM"  >
    <input type="hidden" name="BSYHLX" value="${sessionScope.curLoginMember.USER_TYPE}">
<div class="syj-title1 ">
		<span>申报人信息</span>
	</div>
	<div style="position:relative;">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th>申报种类</th>
				<td style="border-right-style: none;"><input type="text" class="syj-input1 w878" readonly="readonly" style="border: thin;"
					name="SXXZ" value="${serviceItem.SXXZ}" /></td>
				<th colspan="1" style="background-color: white;border-left-style: none;border-right-style: none;"></th>
							<td style="border-left-style: none;"></td>
			</tr>
		</table>
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 " id="sqr" >	
			<tr>
				<th><span class="syj-color2">*</span> 申请人</th>
				<td>
				<input type="text" maxlength="30" name="SQRMC" class="syj-input1 validate[required,custom[equalsSgsStr]]" value="${sessionScope.curLoginMember.YHMC}"/>
				</td>
				<th> 性别</th>
				<td>
				<eve:eveselect clazz="input-dropdown sel w280"
									dataParams="sex" value="${sessionScope.curLoginMember.YHXB}"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="====选择性别====" name="SQRXB"></eve:eveselect>
				<%-- <input type="text" maxlength="30" name="JBR_SEX" class="syj-input1" value="${sessionScope.curLoginMember.YHXB}"/> --%>
				</td>
			</tr>
			<tr>			    
				<th><span class="syj-color2">*</span> 证件类型</th>
				<td>
				<eve:eveselect clazz="input-dropdown w280 sel validate[required]" onchange="setValidate(this.value);"
									dataParams="DocumentType" value="${sessionScope.curLoginMember.ZJLX}"
									dataInterface="dictionaryService.findDatasForSelect" id="sqrzjlx"
									defaultEmptyText="====选择证件类型====" name="SQRZJLX"></eve:eveselect>
				<%-- <input type="text" name="JBR_ZJLX" class="syj-input1" value="${sessionScope.curLoginMember.ZJLX}"/> --%>
				</td>
				<th><span class="syj-color2">*</span> 证件号码</th>
				<td>
				<input type="text" maxlength="30" name="SQRSFZ" id="zjhm" class="syj-input1 validate[required]" value="${sessionScope.curLoginMember.ZJHM}"/>
				</td>
			</tr>
				<th><span class="syj-color2">*</span> 联系手机号</th>
				<td>
				<input type="text" maxlength="11" name="SQRSJH" class="syj-input1 validate[required,custom[mobilePhoneLoose]]" value="${sessionScope.curLoginMember.SJHM}"/>
				</td>
				<th>电话号码</th>
				<td>
				<input type="text" maxlength="14" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="SQRDHH" value="${sessionScope.curLoginMember.DHHM}"/>
				</td>
			<tr>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 联系地址</th>
				<td colspan="3">
				<input type="text" maxlength="60" class="syj-input1 validate[required] w878px" name="SQRLXDZ" value="${sessionScope.curLoginMember.ZTZZ}" />
				
				</td>
			</tr>
			<tr>
				<th> 电子邮箱</th>
				<td colspan="3">
				<input type="text" maxlength="30" class="syj-input1 validate[[],custom[email]] w878px" name="SQRYJ" value="${sessionScope.curLoginMember.DZYX}"/>
				</td>
			</tr>
		</table>
		</c:if>
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
		<table cellpadding="0" cellspacing="0" class="syj-table2 " id="sqjg" style="table-layout: auto;">	
			<tr>
				<th><span class="syj-color2">*</span> 申请机构</th>
				<td colspan="3">
					<input class="w878" type="hidden" maxlength="30" name="SQRMC" value="${sessionScope.curLoginMember.YHMC}"/>
					<input type="text" maxlength="60" name="SQJG_NAME" class="syj-input1 w878 validate[required,custom[equalsSgsStr]]" value="${sessionScope.curLoginMember.YHMC}"/>
				</td>
			</tr>
			<tr>			    
				<th><span class="syj-color2">*</span> 机构类型</th>
				<td>
				<eve:eveselect clazz="input-dropdown w280 sel validate[required]"
									dataParams="OrgType" value="${sessionScope.curLoginMember.JGLX}"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="====选择机构类型====" name="SQJG_TYPE"></eve:eveselect>
				<%-- <input type="text" name="JBR_ZJLX" class="syj-input1" value="${sessionScope.curLoginMember.ZJLX}"/> --%>
				</td>
				<th><span class="syj-color2">*</span> 机构代码</th>
				<td>
				<input type="text" maxlength="14" name="SQJG_CODE" class="syj-input1 validate[required]" value="${sessionScope.curLoginMember.ZZJGDM}"/>
				</td>
			</tr>
			<tr>
				<th> <span class="syj-color2">*</span>统一社会信用代码</th>
				<td>
				<input type="text" maxlength="18" name="SQJG_CREDIT_CODE" class="syj-input1  validate[required]" value="${sessionScope.curLoginMember.ZZJGDM}"/>
				</td>
				<th>工商注册号</th>
				<td>
				<input type="text" maxlength="15" class="syj-input1" name="REGIST_NUM" />
				</td>
			</tr>
			<tr>
				<th> 法人代表</th>
				<td>
				<input type="text" maxlength="14" name="SQJG_LEALPERSON" class="syj-input1 validate[[],custom[equalsSgsStr]]" value="${sessionScope.curLoginMember.FRDB}"/>
				</td>
				<th>联系电话</th>
				<td>
				<input type="text" maxlength="14" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="SQJG_TEL" />
				</td>
			</tr>
			<tr>
				<th> 联系地址</th>
				<td colspan="3">
				<input type="text" maxlength="60" class="syj-input1 w878" name="SQJG_ADDR" value="${sessionScope.curLoginMember.DWDZ}" />
				</td>
			</tr>
		</table>
		</c:if>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><span class="syj-color2">*</span>查询码</th>
				<td >
					<input type="text" maxlength="8"  style="width: 663px;" name="BJCXMM"  value="${busRecord.BJCXMM}" class="syj-input1 validate[required]"  placeholder="请输入查询密码"/>
					<span style="color:red;"> 请自行设置数字查询码</span>
				</td>
			</tr>
		</table>
	</div>
	<div class="syj-title1 ">
		<span>经办人信息</span>
	</div>
	<div style="position:relative;">
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
			<table cellpadding="0" cellspacing="0" class="syj-table2 ">
				<tr>
					<th><span class="syj-color2">*</span> 姓名</th>
					<td>
						<input type="text" maxlength="30" name="JBR_NAME" class="syj-input1 validate[required]" value="${sessionScope.curLoginMember.YHMC}"/>
					</td>
					<th> 性别</th>
					<td>
					<eve:eveselect clazz="input-dropdown w280 sel"
										dataParams="sex" value="${sessionScope.curLoginMember.YHXB}"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="====选择性别====" name="JBR_SEX"></eve:eveselect>
					</td>
				</tr>
				<tr>			    
					<th><span class="syj-color2">*</span> 证件类型</th>
					<td>				
					<eve:eveselect clazz="input-dropdown w280 sel validate[required]" onchange="setValidatejbr(this.value);"
										dataParams="DocumentType" value="${sessionScope.curLoginMember.ZJLX}"
										dataInterface="dictionaryService.findDatasForSelect" id="jbrzjlx"
										defaultEmptyText="====选择证件类型====" name="JBR_ZJLX"></eve:eveselect>
					</td>
					<th><span class="syj-color2">*</span> 证件号码</th>
					<td>
					<input type="text" maxlength="30" name="JBR_ZJHM" id="jbrzjhm" class="syj-input1 validate[required]" value="${sessionScope.curLoginMember.ZJHM}"/>
					</td>
				</tr>
				<tr>
					<th><span class="syj-color2">*</span> 手机号码</th>
					<td>
					<input type="text" maxlength="11" name="JBR_MOBILE" class="syj-input1 validate[required,custom[mobilePhoneLoose]]" value="${sessionScope.curLoginMember.SJHM}"/>
					</td>
					<th> 联系电话</th>
					<td>
					<input type="text" maxlength="14" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="JBR_LXDH" value="${sessionScope.curLoginMember.DHHM}"/>
					</td>
				</tr>
				<tr>
					<th>邮政编码</th>
					<td>
					<input type="text" maxlength="6" name="JBR_POSTCODE" class="syj-input1 validate[[],custom[chinaZip]]" value="${sessionScope.curLoginMember.YZBM}"/>
					</td>
					<th>电子邮箱</th>
					<td>
					<input type="text" maxlength="30" class="syj-input1 validate[[],custom[email]]" name="JBR_EMAIL" value="${sessionScope.curLoginMember.DZYX}"/>
					</td>
				</tr>
				<tr>
					<th>联系地址</th>
					<td colspan="3">
					<input type="text" maxlength="60" class="syj-input1 w878" name="JBR_ADDRESS" value="${sessionScope.curLoginMember.ZTZZ}" />
					</td>
				</tr>
				<tr>
					<th> 备注</th>
					<td colspan="3">
					<input type="text" maxlength="126" class="syj-input1 w878" name="JBR_REMARK" />
					
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
		<table cellpadding="0" cellspacing="0" class="syj-table2 ">
			<tr>
				<th><span class="syj-color2">*</span> 姓名</th>
				<td>
				<input type="text" maxlength="30" name="JBR_NAME" class="syj-input1 validate[required]" value="${sessionScope.curLoginMember.JBRXM}"/>
				</td>
				<th> 性别</th>
				<td>
				<eve:eveselect clazz="input-dropdown w280 sel"
									dataParams="sex"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="====选择性别====" name="JBR_SEX"></eve:eveselect>
				</td>
			</tr>
			<tr>			    
				<th><span class="syj-color2">*</span> 证件类型</th>
				<td>				
				<eve:eveselect clazz="input-dropdown w280 sel validate[required]" onchange="setValidatejbr(this.value);"
									dataParams="DocumentType" value="SF"
									dataInterface="dictionaryService.findDatasForSelect" id="jbrzjlx"
									defaultEmptyText="====选择证件类型====" name="JBR_ZJLX"></eve:eveselect>
				</td>
				<th><span class="syj-color2">*</span> 证件号码</th>
				<td>
				<input type="text" maxlength="30" name="JBR_ZJHM" id="jbrzjhm" class="syj-input1 validate[required]" value="${sessionScope.curLoginMember.JBRSFZ}"/>
				</td>
			</tr>
			<tr>
				<th><span class="syj-color2">*</span> 手机号码</th>
				<td>
				<input type="text" maxlength="11" name="JBR_MOBILE" class="syj-input1 validate[required,custom[mobilePhoneLoose]]" />
				</td>
				<th> 联系电话</th>
				<td>
				<input type="text" maxlength="14" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="JBR_LXDH" />
				</td>
			</tr>
			<tr>
				<th>邮政编码</th>
				<td>
				<input type="text" maxlength="6" name="JBR_POSTCODE" class="syj-input1 validate[[],custom[chinaZip]]" />
				</td>
				<th>电子邮箱</th>
				<td>
				<input type="text" maxlength="30" class="syj-input1 validate[[],custom[email]]" name="JBR_EMAIL" value="${sessionScope.curLoginMember.JBRYXDZ}"/>
				</td>
			</tr>
			<tr>
				<th>联系地址</th>
				<td colspan="3">
				<input type="text" maxlength="60" class="syj-input1 w878" name="JBR_ADDRESS" value="${sessionScope.curLoginMember.DWDZ}" />
				</td>
			</tr>
			<tr>
				<th> 备注</th>
				<td colspan="3">
				<input type="text" maxlength="126" class="syj-input1 w878" name="JBR_REMARK" />
				</td>
			</tr>
		</table>
		</c:if>
	</div>
</form>
