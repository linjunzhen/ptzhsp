<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>

<script type="text/javascript">
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
function grclick(){
	$('#sqr').attr('style','');
	$('#sqjg').attr('style','display: none;');
	$('input:checkbox[name="SFXSJBRXX"]').attr("checked",false);
	$("#JBRXX").attr("style","display: none;");
	$("#SFXSJBRXX").attr("style","");
}
function qyclick(){
	$('#sqjg').attr('style','');
	$('#sqr').attr('style','display: none;');
	$('input:checkbox[name="SFXSJBRXX"]').attr("checked",false);
	$("#JBRXX").attr("style","");
	$("#SFXSJBRXX").attr("style","display: none;");
}
function sfxsjbrxx(){
	var val=$('input:checkbox[name="SFXSJBRXX"]:checked').val();
	if(val==null){
		$("#JBRXX").attr("style","display: none;");
	}else{
		$("#JBRXX").attr("style","");
	}
}

function setGrBsjbr(){
	var val=$('input:checkbox[name="SFXSJBRXX"]:checked').val();
	var lxval=$('input:radio[name="BSYHLX"]:checked').val();
	if(val==null&&lxval=="1"){
	   $('input[name="JBR_NAME"]').val($('input[name="SQRMC"]').val());
	   var zjlx = $('#SQRZJLX option:selected').val();
	   $("#JBR_ZJLX").find("option[value='"+zjlx+"']").attr("selected",true);
	   $('input[name="JBR_ZJHM"]').val($('input[name="SQRSFZ"]').val());
	   $('input[name="JBR_MOBILE"]').val($('input[name="SQRSJH"]').val());
	   $('input[name="JBR_LXDH"]').val($('input[name="SQRDHH"]').val());
	   $('input[name="JBR_ADDRESS"]').val($('input[name="SQRLXDZ"]').val());
	   $('input[name="JBR_EMAIL"]').val($('input[name="SQRYJ"]').val());
	}
}

$(function(){
	if("${lineName}"!=""){
		$('input[name="SQRMC"]').val("${lineName}");
	}
	if("${lineCard }"!=""){
		$('input[name="SQRSFZ"]').val("${lineCard}");
	}
	if("${zjlx }"!=""){
		$('#SQRZJLX').val("${zjlx}");
		$("#jbrzjhm").addClass(",custom[vidcard]");
		$("#zjhm").addClass(",custom[vidcard]");
	}
});
</script>


<div class="tab-title"><span>申报对象信息 </span></div>
<table cellpadding="0" cellspacing="1" class="tab-table">

	<tr>
		<th class="tab_width"> 申报种类：</th>
		<td colspan="3"><input type="text" class="tab_text" readonly="readonly" style="border: thin;"
			name="SXXZ" value="${serviceItem.SXXZ}" /></td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>申请人类型：</th>
		<td colspan="3">
			<input type="radio" name="BSYHLX" value="1" <c:if test="${execution.BSYHLX!='2'}">checked="checked"</c:if> onclick="grclick();">个人
			<input type="radio" name="BSYHLX" value="2" <c:if test="${execution.BSYHLX=='2'}">checked="checked"</c:if> onclick="qyclick();">企业
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab-table marginTop1" id="sqr" <c:if test="${execution.BSYHLX=='2'}">style="display: none;"</c:if> >

	<tr>
		<th class="tab_width"><font class="tab_color">*</font>证件类型：</th>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择证件类型" name="SQRZJLX" id="SQRZJLX">
			</eve:eveselect>
		</td>
		<th class="tab_width"><font class="tab_color">*</font>证件号码：</th>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="30" id="zjhm"
			name="SQRSFZ" value="${execution.SQRSFZ}"  /></td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>申请人：</th>
		<td colspan="3"><input type="text" class="tab_text validate[required]" 
		    maxlength="30" style="width: 42%;"
			name="SQRMC" value="${execution.SQRMC}"  /></td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>联系手机号：</th>
		<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11"
			name="SQRSJH" value="${execution.SQRSJH}"  /></td>
		<th class="tab_width">联系电话：</th>
		<td><input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" maxlength="14"
			name="SQRDHH" value="${execution.SQRDHH}"  /></td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>联系地址：</th>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
			name="SQRLXDZ" value="${execution.SQRLXDZ}" style="width: 72%;"  />
		</td>
	</tr>
	<tr>
		<th class="tab_width">电子邮件：</th>
		<td colspan="3" >
		  <input type="text" class="tab_text validate[[],custom[email]]" maxlength="30"
			name="SQRYJ" value="${execution.SQRYJ}"  /></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab-table marginTop1" id="sqjg" <c:if test="${execution.BSYHLX!='2'}">style="display: none;"</c:if> >

	<tr>
		<th class="tab_width"><font class="tab_color">*</font>机构类型：</th>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="OrgType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择机构类型" name="SQJG_TYPE" id="SQJG_TYPE">
			</eve:eveselect>
		</td>
		<th class="tab_width"><font class="tab_color">*</font>机构代码：</th>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="18"
			name="SQJG_CODE" value="${execution.SQJG_CODE}"  /></td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>申请机构：</th>
		<td colspan="3"><input type="text" class="tab_text validate[required]" 
		    maxlength="60" style="width: 42%;"
			name="SQJG_NAME" value="${execution.SQJG_NAME}"  /></td>
	</tr>
	<tr>
		<th class="tab_width">法人代表：</th>
		<td><input type="text" class="tab_text" maxlength="14"
			name="SQJG_LEALPERSON" value="${execution.SQJG_LEALPERSON}"  /></td>
		<th class="tab_width">联系电话：</th>
		<td><input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" maxlength="14"
			name="SQJG_TEL" value="${execution.SQJG_TEL}"  /></td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>联系地址：</th>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
			name="SQJG_ADDR" value="${execution.SQJG_ADDR}" style="width: 72%;"  />
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab-table bmargin20 marginTop1" id="SFXSJBRXX" <c:if test="${execution.BSYHLX=='2'}">style="display: none;"</c:if>>
    <tr>
        <th class="tab_width">是否填写代理人：</th>
        <td ><input type="checkbox" name="SFXSJBRXX" value="1" onclick="sfxsjbrxx();">是</td>  
    </tr>
</table>
<div class="tab-title"><span>经办人信息 </span></div>
<table cellpadding="0" cellspacing="1" class="tab-table" id="JBRXX" <c:if test="${execution.BSYHLX!='2'&&execution.SFXSJBRXX!='1'}">style="display: none;"</c:if>>

	<tr>
		<th class="tab_width"><font class="tab_color">*</font>姓名：</th>
		<td>
			<input type="hidden" name="lineId" value="${lineId }">
			<input type="text" class="tab_text validate[required]" 
		    maxlength="30" name="JBR_NAME" value="${lineName }"/></td>
		<th class="tab_width">性别</th>
		<td>
		<eve:eveselect clazz="tab_text"
							dataParams="sex"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择性别" name="JBR_SEX" id="JBR_SEX"></eve:eveselect>
		</td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>证件类型：</th>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidatejbr(this.value);"
			defaultEmptyText="选择证件类型" name="JBR_ZJLX" id="JBR_ZJLX" value="${zjlx}">
			</eve:eveselect>
		</td>
		<th class="tab_width"><font class="tab_color">*</font>证件号码：</th>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="30" id="jbrzjhm"
			name="JBR_ZJHM" value="${lineCard }"/></td>
	</tr>
	<tr>
		<th class="tab_width"><font class="tab_color">*</font>联系手机号：</th>
		<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11"
			name="JBR_MOBILE" /></td>
		<th class="tab_width">联系电话：</th>
		<td><input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" maxlength="14"
			name="JBR_LXDH" /></td>
	</tr>
	<tr>
		<th class="tab_width">邮政编码</th>
		<td>
		<input type="text" maxlength="6" name="JBR_POSTCODE" class="tab_text validate[[],custom[chinaZip]]" />
		</td>
		<th class="tab_width">电子邮箱</th>
		<td>
		<input type="text" maxlength="30" class="tab_text validate[[],custom[email]]" name="JBR_EMAIL" />
		</td>
	</tr>
	<tr>
		<th class="tab_width">联系地址：</th>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="JBR_ADDRESS" style="width: 72%;" />
		</td>
	</tr>
	<tr>
		<th class="tab_width">备注：</th>
		<td colspan="3" >
		  <input type="text" class="tab_text" maxlength="60"
			name="JBR_REMARK" style="width: 72%;" />
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab-table bmargin20 marginTop1">
	<tr>
		<c:if test="${serviceItem.APPLY_TYPE!='1'}">
		<th class="tab_width"><font class="tab_color">*</font>窗口收件人员：</th>
		<td>
		  <c:if test="${execution.CREATOR_NAME!=null}">
		      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${execution.CREATOR_NAME}" maxlength="30" name="CKSJRYXM" />
		  </c:if>
		  <c:if test="${execution.CREATOR_NAME==null}">
		      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${sessionScope.curLoginUser.fullname}" maxlength="30" name="CKSJRYXM" />
		  </c:if>
		</td>
		</c:if>
	</tr>
</table>
