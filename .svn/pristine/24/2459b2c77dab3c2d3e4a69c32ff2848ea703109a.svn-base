<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="eve" uri="/evetag"%>
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
	.readCardButton {
		background: #3a81d0;
		border: none;
		padding: 5px 11px;
		line-height: 28px;
		cursor: pointer;
		height: 28px;
		color: #fff;
		border-radius: 6px;
		margin-left: 30px;
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
</style>
<script type="text/javascript">

$(function(){
	$("#SQJG_CODE").unbind('keyup').bind('keyup',isHaveText);
	$("#SQJG_CODE").unbind('blur').bind('blur',isHaveText);
	$("#SQJG_NAME").unbind('keyup').bind('keyup',isHaveText);
	$("#SQJG_NAME").unbind('blur').bind('blur',isHaveText);
	if("${lineName}"!=""){
		$('input[name="SQRMC"]').val("${lineName}");
		$('input[name="JBR_NAME"]').val("${lineName}");
	}
	if("${lineCard }"!=""){
		$('input[name="SQRSFZ"]').val("${lineCard}");
		$('input[name="JBR_ZJHM"]').val("${lineCard}");
	}
	if("${zjlx }"!=""){
		$('#SQRZJLX').val("${zjlx}");
		$('#JBR_ZJLX').val("${zjlx}");
		if("${zjlx }"=="SF"){
			$("#jbrzjhm").addClass(",custom[vidcard]");
			$("#zjhm").addClass(",custom[vidcard]");
		}
	}
	var lineAddress = '${lineAddress}';
	if (lineAddress && lineAddress != "") {
		$("input[name='SQRLXDZ']").val(lineAddress);
	}
});
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
	$('#sqjg1').attr('style','display: none;');
	$('input:checkbox[name="SFXSJBRXX"]').attr("checked",false);
	$("#JBRXX").attr("style","display: none;");
	$("#SFXSJBRXX").attr("style","");
}
function qyclick(){
	$('#sqjg1').attr('style','');
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
function isHaveText(){
	var sqjgCode=$('input[name="SQJG_CODE"]').val();
	var sqjgName=$('input[name="SQJG_NAME"]').val();
	if((sqjgCode==null||$.trim(sqjgCode)=='')&&(sqjgName==null||$.trim(sqjgName)=='')){
		$('#creditQueryBtn').removeClass("eflowbutton").addClass("eflowbutton-disabled");
		$('#creditQueryBtn').attr("disabled",true);
	}else{
		$('#creditQueryBtn').removeClass("eflowbutton-disabled").addClass("eflowbutton");
		$('#creditQueryBtn').attr("disabled",false);
	}
}
function getCreditInfo() {
	var credCodes=$("#SQJG_CODE").val();
	var credNames=$("#SQJG_NAME").val();
	$("#showMsgSpan").html("");
	AppUtil.ajaxProgress({
		url : "creditController.do?findCredits",
		params : {
			"codes" : credCodes,
			"names" : credNames
		},
		callback : function(resultJson) {
			if (resultJson.success) {
				var resultStr=JSON2.parse(resultJson.jsonString);
				if(resultStr.HAS_CREDIT){
				    var url=encodeURI("creditController.do?info&codes="+credCodes+"&names="+credNames);
					$.dialog.open(url, {
						title : "企业主体信息列表",
						width:"680px",
						lock: true,
						resize:false,
						height:"550px",
						close: function () {
							var selectProjectInfo = art.dialog.data("creditInfo");
							if(selectProjectInfo!=undefined){
								$("#SQJG_CODE").val(selectProjectInfo.regNum);
								$("#SQJG_NAME").val(selectProjectInfo.corpName);
								$("#SQJG_TYPE").val("QY");
							}
						}
					}, false);
				}else{
					$("#showMsgSpan").html("未查询到主体信用结果");
				}
			} else {
				parent.art.dialog({
					content : resultJson.msg,
					icon : "error",
					ok : true
				});
			}
		}
	});
	
	
}
function showTemplate(typeId,TemplatePath,TemplateName){
    var exeId="${execution.EXE_ID}";
    var dateStr = "";
    dateStr +="&EFLOW_EXEID="+exeId;
    dateStr +="&typeId="+typeId;
    dateStr +="&TemplatePath="+TemplatePath;
    dateStr +="&TemplateName="+TemplateName;
    var url=encodeURI("printAttachController.do?printQueryApply"+dateStr);
    //打印模版
    $.dialog.open(url, {
        title : "打印模版",
        width: "100%",
        height: "100%",
        left: "0%",
        top: "0%",
        fixed: true,
        lock : true,
        resize : false
    }, false);

}

function queryTypeFn(){
	var val = $('input[name="QUERY_TYPE"]:checked').val(); 
	if(val == '1'){
        $("#div123").attr("style","");
		$("#DATA_QUERY_1").attr("style","");
		$("#DATA_QUERY_2").attr("style","display:none;");
		$("#QUERY_RESULT_TR").attr("style","display:none;");
		$("select[name='QUERY_RESULT']").removeClass("validate[required]");
		$("select[name='APPLYPEOPLE_TYPE']").removeClass("unselect");
	}else if(val == '2'){
        $("#div123").attr("style","display:none;");
		$("#DATA_QUERY_1").attr("style","display:none;");
		$("#DATA_QUERY_2").attr("style","");
		$("#QUERY_RESULT_TR").attr("style","");
		$("select[name='QUERY_RESULT']").addClass("validate[required]");
		$("select[name='APPLYPEOPLE_TYPE']").addClass("unselect");
		$("select[name='APPLYPEOPLE_TYPE']").find("option[value='1']").attr("selected",true);
		fLoadTable();
	}
}
</script>
<input type="hidden" name="ACCEPTWAY" value="${execution.ACCEPTWAY}" />
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<th colspan="4">申报对象信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 申报种类：</td>
		<td  colspan="3"><input type="text" class="tab_text" readonly="readonly" style="border: thin;"
			name="SXXZ" value="${serviceItem.SXXZ}" />
			<a href="javascript:void(0);" style="display: none" onclick="showTemplate(3,'bdcquery.doc','不动产查询申请表')" id="pringApplyForm"
			   class="readCardButton">打印申请表</a></td>

	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>申请人类型：</td>
		<td colspan="3">
			<input type="radio" name="BSYHLX" value="1" <c:if test="${execution.BSYHLX!='2'}">checked="checked"</c:if> onclick="grclick();">个人
			<input type="radio" name="BSYHLX" value="2" <c:if test="${execution.BSYHLX=='2'}">checked="checked"</c:if> onclick="qyclick();">法人
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="sqr" <c:if test="${execution.BSYHLX=='2'}">style="display: none;"</c:if> >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>申请人：</td>
		<td><input type="text" class="tab_text validate[required]"
		    maxlength="30" id="SQRMC"
			name="SQRMC" value="${execution.SQRMC}"  /></td>
		<td class="tab_width"><font class="tab_color">*</font>申请人性别</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]"
						   dataParams="sex" value="${execution.SQRXB}"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="选择性别" name="SQRXB" id="SQRXB"></eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td style="width:430px">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidate(this.value);"
			defaultEmptyText="选择证件类型" name="SQRZJLX" id="SQRZJLX" value="${execution.SQRZJLX}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="30" id="zjhm"
			name="SQRSFZ" value="${execution.SQRSFZ}"  />
			<a href="javascript:void(0);" onclick="applyOfReadCard();" class="readCardButton">身份证读卡</a>
				<a href="webpage/bsdt/applyform/idcard/print.jsp" target="_blank" style="margin-top:10px;">
					<img src="webpage/bsdt/ptwgform/images/print.png" />
				</a>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>联系手机号：</td>
		<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11"
			name="SQRSJH" value="${execution.SQRSJH}"  /></td>
		<td class="tab_width">联系电话：</td>
		<td><input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" maxlength="14"
			name="SQRDHH" value="${execution.SQRDHH}"  /></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>联系地址：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" maxlength="60"
			name="SQRLXDZ" value="${execution.SQRLXDZ}" style="width: 72%;"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">电子邮件：</td>
		<td colspan="3" >
		  <input type="text" class="tab_text validate[[],custom[email]]" maxlength="30"
			name="SQRYJ" value="${execution.SQRYJ}"  /></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="sqjg1" <c:if test="${execution.BSYHLX!='2'}">style="display: none;"</c:if> >
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>申请机构：</td>
		<td colspan="3"><input type="text" class="tab_text validate[required]" 
		    maxlength="60" style="width: 43%;"  id="SQJG_NAME"
			name="SQJG_NAME" value="${execution.SQJG_NAME}"  /></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>机构类型：</td>
		<td style="width:430px;">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="OrgType"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="选择机构类型" name="SQJG_TYPE" id="SQJG_TYPE">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>机构代码：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="18" id="SQJG_CODE"
			name="SQJG_CODE" value="${execution.SQJG_CODE}"  /><span style='width:15px;display:inline-block;'></span>
			<input type="button" class="eflowbutton-disabled"  id="creditQueryBtn"
			style="" onclick="getCreditInfo();" disabled="true;" value="主体信息查询"/>
			<span style='width:15px;display:inline-block;'></span>
			<span id='showMsgSpan' style='width:260px;display:inline-block;color:red;'></span></td>
	</tr>
	<tr>
		<td class="tab_width">法人代表：</td>
		<td><input type="text" class="tab_text" maxlength="14"
			name="SQJG_LEALPERSON" value="${execution.SQJG_LEALPERSON}"  /></td>
		<td class="tab_width">联系电话：</td>
		<td><input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" maxlength="14"
			name="SQJG_TEL" value="${execution.SQJG_TEL}"  /></td>
	</tr>
	<tr>
		<td class="tab_width">联系地址：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="SQJG_ADDR" value="${execution.SQJG_ADDR}" style="width: 72%;"  />
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="SFXSJBRXX" <c:if test="${execution.BSYHLX=='2'}">style="display: none;"</c:if>>
    <tr>
        <td class="tab_width">是否填写代理人：</td>
        <td ><input type="checkbox" name="SFXSJBRXX" value="1" <c:if test="${execution.SFXSJBRXX==1 }">checked="checked"</c:if> onclick="sfxsjbrxx();">是</td>  
    </tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="JBRXX" <c:if test="${execution.BSYHLX!='2'&&execution.SFXSJBRXX!='1'}">style="display: none;"</c:if>>
	<tr>
		<th colspan="4">代理人信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>姓名：</td>
		<td style="width:430px">
			<input type="hidden" name="lineId" value="${lineId }">
			<input type="hidden" name="itemdetail_id" value="${itemdetail_id }">
			<input type="text" class="tab_text validate[required]" 
		    maxlength="30" name="JBR_NAME" value="${execution.JBR_NAME}"/></td>
		<td class="tab_width">性别:</td>
		<td>
		<eve:eveselect clazz="tab_text"
							dataParams="sex" value="${execution.JBR_SEX}"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择性别" name="JBR_SEX" id="JBR_SEX"></eve:eveselect>
			<a href="javascript:void(0);" onclick="jbrOfReadCard();" class="readCardButton">身份证读卡</a>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>证件类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setValidatejbr(this.value);"
			defaultEmptyText="选择证件类型" name="JBR_ZJLX" id="JBR_ZJLX" value="${execution.JBR_ZJLX}">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font>证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[required]" maxlength="30" id="jbrzjhm"
			name="JBR_ZJHM" value="${execution.JBR_ZJHM }"/></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>联系手机号：</td>
		<td><input type="text" class="tab_text validate[required,custom[mobilePhoneLoose]]" maxlength="11"
			name="JBR_MOBILE" value="${execution.JBR_MOBILE }"/></td>
		<td class="tab_width">联系电话：</td>
		<td><input type="text" class="tab_text validate[[],custom[fixPhoneWithAreaCode]]" maxlength="14"
			name="JBR_LXDH" value="${execution.JBR_LXDH }"/></td>
	</tr>
	<tr>
		<td class="tab_width">邮政编码:</td>
		<td>
		<input type="text" maxlength="6" name="JBR_POSTCODE" value="${execution.JBR_POSTCODE }" class="tab_text validate[[],custom[chinaZip]]" />
		</td>
		<td class="tab_width">电子邮箱:</td>
		<td>
		<input type="text" maxlength="30" class="tab_text validate[[],custom[email]]" name="JBR_EMAIL" value="${execution.JBR_EMAIL }" />
		</td>
	</tr>
	<tr>
		<td class="tab_width">联系地址：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="JBR_ADDRESS" style="width: 72%;" value="${execution.JBR_ADDRESS }"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width">备注：</td>
		<td colspan="3" >
		  <input type="text" class="tab_text" maxlength="60"
			name="JBR_REMARK" value="${execution.JBR_REMARK }" style="width: 72%;" />
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<tr>
		<c:if test="${serviceItem.APPLY_TYPE!='1'}">
		<td class="tab_width"><font class="tab_color">*</font>窗口收件人员：</td>
		<td>
		  <c:if test="${execution.CREATOR_NAME!=null}">
		      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${execution.CREATOR_NAME}" maxlength="30" name="CKSJRYXM" />
		  </c:if>
		  <c:if test="${execution.CREATOR_NAME==null}">
		      <input type="text" readonly="readonly" class="tab_text validate[required]" value="${sessionScope.curLoginUser.fullname}" maxlength="30" name="CKSJRYXM" />
		  </c:if>
		</td>
		</c:if>
		<c:if test="${serviceItem.APPLY_TYPE!='1'}">
			<td class="tab_width"><font class="tab_color">*</font>经办日期：</td>
			<td>
				<input type="text"  class="tab_text validate[required]"  value="${busRecord.HAND_TIME}"  disabled="disabled" maxlength="30" name="HAND_TIME" />
			</td>
		</c:if>
	</tr>
	<tr>
		<td>类型:</td>
		<td colspan="3">
			<eve:radiogroup  typecode="queryType" width="630px"  defaultvalue="1"
				onclick="queryTypeFn();"	value="${busRecord.QUERY_TYPE}" fieldname="QUERY_TYPE" >
			</eve:radiogroup>
		</td>
	</tr>
</table>
