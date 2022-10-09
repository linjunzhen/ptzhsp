<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" href="<%=path%>/webpage/ui20211220/css/public.css">
<link rel="stylesheet" href="<%=path%>/webpage/ui20211220/css/jbxx.css">

<script type="text/javascript">
$(function(){
	var userType= '${sessionScope.curLoginMember.USER_TYPE}';
	if(userType==1){
		$("#sqjg").attr("style","display:none;");
		var idcard = $("input[name='SQRSFZ']").val();
		personCreditHandle(idcard);
	}else{
		$("#sqr").attr("style","display:none;");
		var idcard = $("input[name='SQJG_CODE']").val();
		personCreditHandle(idcard);
	}
	var sqrzjlx=$("#sqrzjlx").val();
	var jbrzjlx=$("#jbrzjlx").val();
	setValidate(sqrzjlx);
	setValidatejbr(jbrzjlx);

	//身份证号码转大写
	$("input[name='SQRSFZ']").val($("input[name='SQRSFZ']").val().toUpperCase());
	$("input[name='SQRSFZ']").on('blur', function(event) { 
		var ZJLX = $("[name='SQRZJLX']").val();
		if(ZJLX=='SF'){			
			$(this).val($(this).val().toUpperCase());
		}
	});
	$("input[name='JBR_ZJHM']").val($("input[name='JBR_ZJHM']").val().toUpperCase());
	$("input[name='JBR_ZJHM']").on('blur', function(event) { 
		var ZJLX = $("[name='JBR_ZJLX']").val();
		if(ZJLX=='SF'){			
			$(this).val($(this).val().toUpperCase());
		}
	});

	var busTableName = $("input[name='EFLOW_BUSTABLENAME']").val();
	if (busTableName && busTableName.indexOf("T_BDCQLC_") != -1) {
		$("#jbrTip").hide();
	}
	
	
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

//信用评级
function personCreditHandle(idCard){
	AppUtil.ajaxNoProgress({
		url : "personCreditController.do?queryPersonCreditLevel",
		params : {
			ID_CARD : idCard
		},
		callback : function(resultJson) {
			var creditlevel = resultJson.jsonString;
			$("input[name='creditlevel']").val(creditlevel);
			if(creditlevel.indexOf('A')>=0){
				<c:forEach items="${applyMaters}" var="applyMater" varStatus="status" >
			        var PERSON_CREDIT_USE = '${applyMater.PERSON_CREDIT_USE}';
			        if(PERSON_CREDIT_USE.indexOf('A')>=0){
			        	var materCode = '${applyMater.MATER_CODE}';
			        	var options="<option value='3' selected='true'>信用免交</option>";
			        	$("#SQFS_"+materCode).append(options);
			        }
			        
			    </c:forEach>
			}
		}
	});
}
</script>
<div  id="userinfo_div">
<div class="bsbox clearfix">
    <%-- <input type="hidden" name="SQRMC" value="${sessionScope.curLoginMember.YHMC}"/>
    <input type="hidden" name="SQRSJH" value="${sessionScope.curLoginMember.SJHM}"/>
    <input type="hidden" name="SQRSFZ" value="${sessionScope.curLoginMember.ZJHM}"/> --%>
    <input type="hidden" name="BSYHLX" value="${sessionScope.curLoginMember.USER_TYPE}">
	<input type="hidden" name="creditlevel">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>申报对象信息</span><font color="red">（申请人若为法人，请退出登陆后用法人账号登陆申请。）</font></li>
		</ul>
	</div>
	<div class="bsboxC">
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th> 申报种类</th>
				<td style="border-right-style: none;"><input type="text" class="tab_text" readonly="readonly" style="border: thin;"
					name="SXXZ" value="${serviceItem.SXXZ}" /></td>
				<th colspan="1" style="background-color: white;border-left-style: none;border-right-style: none;"></th>
				<td style="border-left-style: none;"></td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="bstable1" id="sqr" >	
			<tr>
				<th><span class="bscolor1">*</span> 申请人</th>
				<td>
				<input type="text" maxlength="30" name="SQRMC" class="tab_text validate[required,custom[equalsSgsStr]]" value="${sessionScope.curLoginMember.YHMC}"/>
				</td>
				<th><span class="bscolor1">*</span> 性别</th>
				<td>
				<eve:eveselect clazz="tab_text w280 sel validate[required]"
									dataParams="sex" value="${sessionScope.curLoginMember.YHXB}"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="====选择性别====" name="SQRXB"></eve:eveselect>
				<%-- <input type="text" maxlength="30" name="JBR_SEX" style="width:280px" class="tab_text" value="${sessionScope.curLoginMember.YHXB}"/> --%>
				</td>
			</tr>
			<tr>			    
				<th><span class="bscolor1">*</span> 证件类型</th>
				<td>
				<eve:eveselect clazz="tab_text w280 sel validate[required]" onchange="setValidate(this.value);"
									dataParams="DocumentType" value="${sessionScope.curLoginMember.ZJLX}"
									dataInterface="dictionaryService.findDatasForSelect" id="sqrzjlx"
									defaultEmptyText="====选择证件类型====" name="SQRZJLX"></eve:eveselect>
				<%-- <input type="text" name="JBR_ZJLX" style="width:280px"  class="tab_text" value="${sessionScope.curLoginMember.ZJLX}"/> --%>
				</td>
				<th><span class="bscolor1">*</span> 证件号码</th>
				<td>
				<input type="text" maxlength="30" name="SQRSFZ" id="zjhm" class="tab_text validate[required]" value="${sessionScope.curLoginMember.ZJHM}" onchange="personCreditHandle(this.value)"/>
				</td>
			</tr>
				<th><span class="bscolor1">*</span> 联系手机号</th>
				<td>
				<input type="text" maxlength="11" name="SQRSJH" class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${sessionScope.curLoginMember.SJHM}"/>
				</td>
				<th>电话号码</th>
				<td>
				<input type="text" maxlength="14" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" name="SQRDHH" value="${sessionScope.curLoginMember.DHHM}"/>
				</td>
			<tr>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span> 联系地址</th>
				<td colspan="3">
				<input type="text" maxlength="60" class="tab_text validate[required]" style="width: 850px;" name="SQRLXDZ" value="${sessionScope.curLoginMember.ZTZZ}" />
				</td>
			</tr>
			<tr>
				<th> 电子邮箱</th>
				<td colspan="3">
				<input type="text" maxlength="30" class="tab_text validate[[],custom[email]]" style="width: 850px;" name="SQRYJ" value="${sessionScope.curLoginMember.DZYX}"/>
				</td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="bstable1" id="sqjg" >	
			<tr>
				<th><span class="bscolor1">*</span> 申请机构</th>
				<td>
				<input style="width:280px;" type="text" maxlength="60" name="SQJG_NAME" class="tab_text validate[required,custom[equalsSgsStr]]" value="${sessionScope.curLoginMember.YHMC}"/>
				</td>	    
				<th><span class="bscolor1">*</span> 机构类型</th>
				<td>
				<eve:eveselect clazz="tab_text w280 sel validate[required]"
									dataParams="OrgType" value="${sessionScope.curLoginMember.JGLX}"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="====选择机构类型====" name="SQJG_TYPE"></eve:eveselect>
				</td>
			</tr>
			<tr>		
				<th><span class="bscolor1">*</span> 机构代码</th>
				<td>
				<input type="text" maxlength="18" name="SQJG_CODE" class="tab_text validate[required]" value="${sessionScope.curLoginMember.ZZJGDM}"/>
				</td>
				<th><span class="bscolor1">*</span>统一社会信用代码</th>
				<td>
				<input type="text" maxlength="18" name="SQJG_CREDIT_CODE" class="tab_text validate[required]"  value="${sessionScope.curLoginMember.ZZJGDM}"/>
				</td>
			</tr>
			<tr>
				<th> 法人代表</th>
				<td>
				<input type="text" maxlength="14" name="SQJG_LEALPERSON" class="tab_text validate[[],custom[equalsSgsStr]]" value="${sessionScope.curLoginMember.FRDB}"/>
				</td>
				<th>联系电话</th>
				<td>
				<input type="text" maxlength="14" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" name="SQJG_TEL" />
				</td>
			</tr>
			<tr>
				<th> 联系地址</th>
				<td colspan="3">
				<input type="text" maxlength="60" class="tab_text" style="width: 850px;" name="SQJG_ADDR" value="${sessionScope.curLoginMember.DWDZ}" />
				
				</td>
			</tr>
		</table>
		<table style="margin-top: -1px;" cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th><span class="bscolor1">*</span>查询码</th>
				<td >
				<input type="text" maxlength="8" style="width:685px" name="BJCXMM" class="tab_text validate[required]" />
				<span style="color:red;"> 请自行设置数字查询码</span>
				</td>
			</tr>
		</table>
	</div>	
	
</div>
<div class="bsbox clearfix" >
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>经办人信息</span><font color="red" id="jbrTip">（去现场办理的人员必须为经办人本人或申请人本人，并提供本人有效身份证件给窗口。）</font></li>
		</ul>
	</div>	
	<div class="bsboxC">
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='1'}">
			<table cellpadding="0" cellspacing="0" class="bstable1">
				<tr>
					<th><span class="bscolor1">*</span> 姓名</th>
					<td>
						<input type="text" maxlength="30" name="JBR_NAME" class="tab_text validate[required]" value="${sessionScope.curLoginMember.YHMC}"/>
					</td>
					<th><span class="bscolor1">*</span> 性别</th>
					<td>
					<eve:eveselect clazz="tab_text w280 sel validate[required]"
										dataParams="sex" value="${sessionScope.curLoginMember.YHXB}"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="====选择性别====" name="JBR_SEX"></eve:eveselect>
					</td>
				</tr>
				<tr>
					<th><span class="bscolor1">*</span> 证件类型</th>
					<td>
					<eve:eveselect clazz="tab_text w280 sel validate[required]" onchange="setValidatejbr(this.value);"
										dataParams="DocumentType" value="${sessionScope.curLoginMember.ZJLX}"
										dataInterface="dictionaryService.findDatasForSelect" id="jbrzjlx"
										defaultEmptyText="====选择证件类型====" name="JBR_ZJLX"></eve:eveselect>
					</td>
					<th><span class="bscolor1">*</span> 证件号码</th>
					<td>
					<input type="text" maxlength="30" name="JBR_ZJHM" id="jbrzjhm" class="tab_text validate[required]" value="${sessionScope.curLoginMember.ZJHM}"/>
					</td>
				</tr>
				<tr>
					<th><span class="bscolor1">*</span> 手机号码</th>
					<td>
					<input type="text" maxlength="11" name="JBR_MOBILE" class="tab_text validate[required,custom[mobilePhoneLoose]]" value="${sessionScope.curLoginMember.SJHM}"/>
					</td>
					<th> 联系电话</th>
					<td>
					<input type="text" maxlength="14" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" name="JBR_LXDH" value="${sessionScope.curLoginMember.DHHM}"/>
					</td>
				</tr>
				<tr>
					<th>邮政编码</th>
					<td>
					<input type="text" maxlength="6" name="JBR_POSTCODE" class="tab_text validate[[],custom[chinaZip]]" value="${sessionScope.curLoginMember.YZBM}"/>
					</td>
					<th><span class="bscolor1">*</span> 电子邮箱</th>
					<td>
					<input type="text" maxlength="30" class="tab_text validate[[],required,custom[email]]" name="JBR_EMAIL" value="${sessionScope.curLoginMember.DZYX}"/>
					</td>
				</tr>
				<tr>
					<th>联系地址</th>
					<td colspan="3">
					<input type="text" maxlength="60" class="tab_text" style="width: 850px;" name="JBR_ADDRESS" value="${sessionScope.curLoginMember.ZTZZ}" />
					</td>
				</tr>
				<tr>
					<th> 备注</th>
					<td colspan="3">
					<input type="text" maxlength="126" class="tab_text" style="width: 850px;" name="JBR_REMARK" />
					
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${sessionScope.curLoginMember.USER_TYPE=='2'}">
		<table cellpadding="0" cellspacing="0" class="bstable1">
			<tr>
				<th><span class="bscolor1">*</span> 姓名</th>
				<td>
				<input type="text" maxlength="30" name="JBR_NAME" class="tab_text" value="${sessionScope.curLoginMember.JBRXM}"/>
				</td>
				<th><span class="bscolor1">*</span> 性别</th>
				<td>
				<eve:eveselect clazz="tab_text sel w280 validate[required]"
									dataParams="sex"
									dataInterface="dictionaryService.findDatasForSelect"
									defaultEmptyText="====选择性别====" name="JBR_SEX"></eve:eveselect>
				</td>
			</tr>
			<tr>			    
				<th><span class="bscolor1">*</span> 证件类型</th>
				<td>				
				<eve:eveselect clazz="tab_text w280 sel validate[required]" onchange="setValidatejbr(this.value);"
									dataParams="DocumentType" value="SF"
									dataInterface="dictionaryService.findDatasForSelect" id="jbrzjlx"
									defaultEmptyText="====选择证件类型====" name="JBR_ZJLX"></eve:eveselect>
				</td>
				<th><span class="bscolor1">*</span> 证件号码</th>
				<td>
				<input type="text" maxlength="30" name="JBR_ZJHM" id="jbrzjhm" class="tab_text validate[required]" value="${sessionScope.curLoginMember.JBRSFZ}"/>
				</td>
			</tr>
			<tr>
				<th><span class="bscolor1">*</span> 手机号码</th>
				<td>
				<input type="text" maxlength="11" name="JBR_MOBILE" class="tab_text validate[required,custom[mobilePhoneLoose]]" />
				</td>
				<th> 联系电话</th>
				<td>
				<input type="text" maxlength="14" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" name="JBR_LXDH" />
				</td>
			</tr>
			<tr>
				<th>邮政编码</th>
				<td>
				<input type="text" maxlength="6" name="JBR_POSTCODE" class="tab_text validate[[],custom[chinaZip]]" />
				</td>
				<th>电子邮箱</th>
				<td>
				<input type="text" maxlength="30" class="tab_text validate[[],custom[email]]" name="JBR_EMAIL" value="${sessionScope.curLoginMember.JBRYXDZ}"/>
				</td>
			</tr>
			<tr>
				<th>联系地址</th>
				<td colspan="3">
				<input type="text" maxlength="60" class="tab_text" style="width: 850px;" name="JBR_ADDRESS" value="${sessionScope.curLoginMember.DWDZ}" />
				</td>
			</tr>
			<tr>
				<th> 备注</th>
				<td colspan="3">
				<input type="text" maxlength="126" class="tab_text" style="width: 850px;" name="JBR_REMARK" />
				</td>
			</tr>
		</table>
		</c:if>
	</div>
</div>
</div>
