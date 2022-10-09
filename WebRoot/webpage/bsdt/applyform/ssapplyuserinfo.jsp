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
	$("#SQJG_NAME").unbind('keyup').bind('keyup',isHaveTexta);
	$("#SQJG_NAME").unbind('blur').bind('blur',isHaveTexta);
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
	$("[name='GPYTYPE']").attr("disabled",false);
	
	
	
	var fileids=$('input[name="OPERATOR_PHOTO_FILE_ID"]').val();
	$.post("executionController.do?getResultFiles",{fileIds:fileids}, function(resultJson) {
			 if(resultJson!="websessiontimeout"){
				$("#fileListDiv").html("");
				var newhtml = "";
				var list=resultJson.rows;
				for(var i=0; i<list.length; i++){
					newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
					newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
					newhtml+=list[i].FILE_NAME+'</a>';
					newhtml+='</p>';
				}
				$("#fileListDiv").html(newhtml);
			 }
	 });
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
function setFrValidate(zjlx){
	if(zjlx=="SF"){
		$("#frzjhm").addClass(",custom[vidcard]");
	}else{
		$("#frzjhm").removeClass(",custom[vidcard]");
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
	if(sqjgCode==null||$.trim(sqjgCode)==''){
		$('#creditQueryBtn').removeClass("eflowbutton").addClass("eflowbutton-disabled");
		$('#creditQueryBtn').attr("disabled",true);
	}else{
		$('#creditQueryBtn').removeClass("eflowbutton-disabled").addClass("eflowbutton");
		$('#creditQueryBtn').attr("disabled",false);
	}
}

function isHaveTexta() {
	var sqjgName = $('input[name="SQJG_NAME"]').val();
	if (sqjgName == null || $.trim(sqjgName) == '') {
		$('#sqjgNameQueryBtn').removeClass("eflowbutton").addClass("eflowbutton-disabled");
		$('#sqjgNameQueryBtn').attr("disabled",true);
	} else {
		$('#sqjgNameQueryBtn').removeClass("eflowbutton-disabled").addClass("eflowbutton");
		$('#sqjgNameQueryBtn').attr("disabled",false);
	}
}

function getCreditInfo() {
	var sqjgCreditCode = $('input[name="SQJG_CODE"]').val();
	var itemName = $('input[name="ITEM_NAME"]').val();
	var itemCode = $('input[name="ITEM_CODE"]').val();
	if (sqjgCreditCode) {
		$.post("identifyMsgByDsjController/sqjgCreditCodeQuery.do",{sqjgCreditCode:sqjgCreditCode,itemName:itemName,itemCode:itemCode},function (data) {
			if (data) {
				var dataObj = JSON.parse(data);
				$("#LOGID").val(dataObj.logid);
				$("#SQJG_NAME").val(dataObj.cnname);
				$("#SQJG_CREDIT_CODE").val(dataObj.tyshxydm)
				$("#SQJG_LEALPERSON").val(dataObj.legalpersonname);
				$("#SQJG_TEL").val(dataObj.companyphone);
				$("#SQJG_ADDR").val(dataObj.regaddress);
				$("#REGIST_NUM").val(dataObj.gszch);
			} else {
				$("#showMsgSpan").html("未查询到主体信用结果，请输入18位机构代码");
			}
		})
	}
	// var credCodes=$("#SQJG_CODE").val();
	// var credNames=$("#SQJG_NAME").val();
	// $("#showMsgSpan").html("");
	// AppUtil.ajaxProgress({
	// 	url : "creditController.do?findCredits",
	// 	params : {
	// 		"codes" : credCodes,
	// 		"names" : credNames
	// 	},
	// 	callback : function(resultJson) {
	// 		if (resultJson.success) {
	// 			var resultStr=JSON2.parse(resultJson.jsonString);
	// 			if(resultStr.HAS_CREDIT){
	// 			    var url=encodeURI("creditController.do?info&codes="+credCodes+"&names="+credNames);
	// 				$.dialog.open(url, {
	// 					title : "企业主体信息列表",
	// 					width:"680px",
	// 					lock: true,
	// 					resize:false,
	// 					height:"550px",
	// 					close: function () {
	// 						var selectProjectInfo = art.dialog.data("creditInfo");
	// 						if(selectProjectInfo!=undefined){
	// 							$("#SQJG_CODE").val(selectProjectInfo.regNum);
	// 							$("#SQJG_NAME").val(selectProjectInfo.corpName);
	// 							$("#SQJG_TYPE").val("QY");
	// 						}
	// 					}
	// 				}, false);
	// 			}else{
	// 				$("#showMsgSpan").html("未查询到主体信用结果");
	// 			}
	// 		} else {
	// 			parent.art.dialog({
	// 				content : resultJson.msg,
	// 				icon : "error",
	// 				ok : true
	// 			});
	// 		}
	// 	}
	// });
}

function getSqjgNameInfo() {
	var sqjgName = $('input[name="SQJG_NAME"]').val();
	var itemName = $('input[name="ITEM_NAME"]').val();
	var itemCode = $('input[name="ITEM_CODE"]').val();
	if (sqjgName) {
		$.post("identifyMsgByDsjController/sqjgNameQuery.do",{sqjgName:sqjgName,itemName:itemName,itemCode:itemCode},function (data) {
			if (data) {
				var dataObj = JSON.parse(data);
				$("#LOGID").val(dataObj.logid);
				$("#SQJG_CODE").val(dataObj.tyshxydm)
				$("#SQJG_CREDIT_CODE").val(dataObj.tyshxydm)
				$("#SQJG_LEALPERSON").val(dataObj.legalpersonname);
				$("#SQJG_TEL").val(dataObj.companyphone);
				$("#SQJG_ADDR").val(dataObj.regaddress);
				$("#REGIST_NUM").val(dataObj.gszch);
			} else {
				$("#showsqjgMsgSpan").html("未查询到主体信用结果,请输入企业全称");
			}
		})
	}
}

    function chooseXXCJCtrl() {
        var gpytype = $('input[name="GPYTYPE"]:checked').val(); 
        if(gpytype==""||gpytype==undefined){
			parent.art.dialog({
				content : "请选择高拍仪类型",
				icon : "error",
				time : 3,
				ok : true
			});
        }else if(gpytype=="1"){
        	bindScanCtrlYCTB();
        }else if(gpytype=="2"){
        	bindScanCtrlYCTB();
        }else if(gpytype=="3"){
        	bindScanCtrlYCTBZT();
        }else if(gpytype=="4"){
        	bindScanCtrlYCTB();
        }
    }
    
function bindScanCtrlYCTB(){
	var onlineBusTableName = "JBPM6_EXECUTION";
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtl.jsp?uploadPath=applyform&busTableName="+onlineBusTableName+
		"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				initScanUploadMatersYCTB(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}

function bindScanCtrlYCTBZT(){
	var onlineBusTableName = "JBPM6_EXECUTION";
	//定义上传的人员的ID
	var uploadUserId = $("input[name='uploadUserId']").val();
	//定义上传的人员的NAME
	var uploadUserName = $("input[name='uploadUserName']").val();
	$.dialog.open("webpage/bsdt/applyform/videoinput/VideoInputCtlZT.jsp?uploadPath=applyform&busTableName="+onlineBusTableName+
		"&uploadUserId=" + uploadUserId + "&uploadUserName=" + encodeURI(uploadUserName), {
		title : "高拍仪",
		width:"800px",
		lock: true,
		resize:false,
		height:"620px",
		close: function () {
			var resultJsonInfo = art.dialog.data("resultJsonInfo");
			if(resultJsonInfo){
				initScanUploadMatersYCTB(resultJsonInfo);
				art.dialog.removeData("resultJsonInfo");
			}
		}
	}, false);
}
function initScanUploadMatersYCTB(resultJson){
	for(var i=0;i<resultJson.length;i++){	
		
		var fileurl=$("input[name='OPERATOR_PHOTO_FILE_URL']").val();
		var fileid=$("input[name='OPERATOR_PHOTO_FILE_ID']").val();
		if(fileurl!=""&&fileurl!=null){
			$("input[name='OPERATOR_PHOTO_FILE_URL']").val(fileurl+";"+resultJson[i].data.filePath);
			$("input[name='OPERATOR_PHOTO_FILE_ID']").val(fileid+";"+resultJson[i].data.fileId);
		}else{
			$("input[name='OPERATOR_PHOTO_FILE_URL']").val(resultJson[i].data.filePath);
			$("input[name='OPERATOR_PHOTO_FILE_ID']").val(resultJson[i].data.fileId);
		}
		//获取文件ID
        var fileId = resultJson[i].data.fileId;
        //获取文件名称
        var fileName = resultJson[i].data.fileName;
        //获取文件路径
        var filePath = resultJson[i].data.filePath;
        //获取文件的类型
        var fileType = resultJson[i].data.fileType;
		var spanHtml = "<p id=\""+fileId+"\"><a href=\"javascript:void(0);\" style=\"cursor: pointer;\"";
        spanHtml+=(" onclick=\"AppUtil.downLoadFile('"+fileId+"');\">");
        spanHtml +="<font color=\"blue\">"+fileName+"</font></a>"
        spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+fileId+"','');\" ><font color=\"red\">删除</font></a></p>"
        $("#fileListDiv").append(spanHtml); 
	}
}
/**
* 标题附件上传对话框
*/
function openXxcjFileUploadDialog(){
	//上传框类型:video-视频 attach-附件 audio-音频 flash-flash image-图片
	art.dialog.open('fileTypeController.do?openUploadDialog&attachType=image&materType=image&busTableName=JBPM6_EXECUTION', {
		title: "上传(图片)",
		width: "650px",
		height: "300px",
		lock: true,
		resize: false,
		close: function(){
			var uploadedFileInfo = art.dialog.data("uploadedFileInfo");
			if(uploadedFileInfo){
				if(uploadedFileInfo.fileId){
					var fileType = 'gif,jpg,jpeg,bmp,png';
					var attachType = 'attach';
					if(fileType.indexOf(uploadedFileInfo.fileSuffix)>-1){
						attachType = "image";
					}								
					var fileurl=$("input[name='OPERATOR_PHOTO_FILE_URL']").val();
					var fileid=$("input[name='OPERATOR_PHOTO_FILE_ID']").val();
					if(fileurl!=""&&fileurl!=null){
						$("input[name='OPERATOR_PHOTO_FILE_URL']").val(fileurl+';download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='OPERATOR_PHOTO_FILE_ID']").val(fileid+";"+uploadedFileInfo.fileId);
					}else{
						$("input[name='OPERATOR_PHOTO_FILE_URL']").val('download/fileDownload?attachId='+uploadedFileInfo.fileId+'&attachType='+attachType);
						$("input[name='OPERATOR_PHOTO_FILE_ID']").val(uploadedFileInfo.fileId);
					}					
					var spanHtml = "<p id=\""+uploadedFileInfo.fileId+"\"><a href=\""+__file_server 
					+ "download/fileDownload?attachId="+uploadedFileInfo.fileId
					+"&attachType="+attachType+"\" ";
					spanHtml+=(" style=\"color: blue;margin-right: 5px;\" target=\"_blank\">");
					spanHtml +="<font color=\"blue\">"+uploadedFileInfo.fileName+"</font></a>"
					spanHtml +="<a href=\"javascript:void(0);\"  onclick=\"delUploadFile('"+uploadedFileInfo.fileId+"');\" ><font color=\"red\">删除</font></a></p>"
					$("#fileListDiv").append(spanHtml); 
				}
			}
			art.dialog.removeData("uploadedFileInfo");
		}
	});
}
function delUploadFile(fileId,attacheKey){
	//AppUtil.delUploadMater(fileId,attacheKey);
	art.dialog.confirm("您确定要删除该文件吗?", function() {
		//移除目标span
		$("#"+fileId).remove();
		var fileurl=$("input[name='OPERATOR_PHOTO_FILE_URL']").val();
		var fileid=$("input[name='OPERATOR_PHOTO_FILE_ID']").val();
		var arrayIds=fileid.split(";");
		var arrayurls=fileurl.split(";");
		for(var i=0;i<arrayIds.length;i++){
			if(arrayIds[i]==fileId){
				arrayIds.splice(i,1); 
				arrayurls.splice(i,1); 
				break;
			}
		}
		$("input[name='OPERATOR_PHOTO_FILE_URL']").val(arrayurls.join(";"));
		$("input[name='OPERATOR_PHOTO_FILE_ID']").val(arrayIds.join(";"));
	});
}
</script>
<div  id="userinfo_div">
<div class="tab_height"></div>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro">
	<input type="hidden" name="LOGID" id="LOGID" value="${execution.LOGID}">
	<input type="hidden" name="OPERATOR_PHOTO_FILE_URL"  value="${execution.OPERATOR_PHOTO_FILE_URL}">
	<input type="hidden" name="OPERATOR_PHOTO_FILE_ID"  value="${execution.OPERATOR_PHOTO_FILE_ID}">
	<tr>
		<th colspan="4">申报对象信息</th>
	</tr>
	<tr>
		<td class="tab_width"> 申报种类：</td>
		<td colspan="3"><input type="text" class="tab_text" readonly="readonly" style="border: thin;"
			name="SXXZ" value="${serviceItem.SXXZ}" /></td>
	</tr>
	<tr>
		<td class="tab_width"> 高拍仪类型：</td>
		<td colspan="3">
			<input type="radio" name="GPYTYPE" value="1" >服务中心
			<input type="radio" name="GPYTYPE" value="2" >台胞台企
			<input type="radio" name="GPYTYPE" value="3" >苏平
			<input type="radio" name="GPYTYPE" value="4" >农商
		</td>
	</tr>
	<tr >
		<td ><span style="width: 125px;float:left;text-align:right;">经办人人脸照片：
			 </span>
		</td>
		<td colspan="3">			
			<a href="javascript:void(0);" onclick="openXxcjFileUploadDialog()">
				<img src="webpage/bsdt/applyform/images/tab_btn_sc.png"  style="width:60px;height:22px;"/>
			</a>
			<div style="float: left;">
				<a href="javascript:chooseXXCJCtrl()"><img id="${applyMater.MATER_CODE}"
				src="webpage/bsdt/ptwgform/images/scan.png" style="width:60px;height:22px;"/></a>
			</div>
		</td>
		<tr>
			<td></td>
			<td colspan="3">
				<div style="width:100%;" id="fileListDiv"></div>
			</td>
		</tr>
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
		<td colspan="3"><input type="text" class="tab_text validate[required,custom[equalsSgsStr]]" 
		    maxlength="30" style="width: 43%;"
			name="SQRMC" value="${execution.SQRMC}"  /></td>
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
		  <input type="text" class="tab_text validate[required]" maxlength="64" id="zjhm"
			name="SQRSFZ" value="${execution.SQRSFZ}"  /></td>
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
		<td class="tab_width">联系地址：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
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
		<td colspan="3"><input type="text" class="tab_text validate[required,custom[equalsSgsStr]]" 
		    maxlength="60" style="width: 43%;"  id="SQJG_NAME"
			name="SQJG_NAME" value="${execution.SQJG_NAME}"  />
			<input type="button" class="eflowbutton-disabled"  id="sqjgNameQueryBtn"
				   style="" onclick="getSqjgNameInfo();" disabled="true;" value="主体信息查询"/>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font>机构类型：</td>
		<td style="width:430px;">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="OrgType"
			dataInterface="dictionaryService.findDatasForSelect"  value="${execution.SQJG_TYPE}"
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
		<td class="tab_width"><c:if test="${serviceItem.SXXZ == '行政许可'}"><font class="tab_color">*</font></c:if>统一社会信用代码：</td>
		<td><input type="text" class="tab_text <c:if test="${serviceItem.SXXZ == '行政许可'}">validate[required]</c:if>" maxlength="18"
				   name="SQJG_CREDIT_CODE" value="${execution.SQJG_CREDIT_CODE}" id="SQJG_CREDIT_CODE" />
		</td>
		<td class="tab_width">工商注册号：</td>
		<td><input type="text" class="tab_text" maxlength="15"
				   name="REGIST_NUM" value="${execution.REGIST_NUM}" id="REGIST_NUM" />
		</td>
	</tr>
	<tr>
		<%-- <td class="tab_width"><c:if test="${serviceItem.SXXZ == '行政许可'}"><font class="tab_color">*</font></c:if>法人代表：</td>
		<td><input type="text" class="tab_text <c:if test="${serviceItem.SXXZ == '行政许可'}">validate[required]</c:if>" maxlength="14"
			name="SQJG_LEALPERSON" value="${execution.SQJG_LEALPERSON}" id="SQJG_LEALPERSON" /></td> --%>
		<td class="tab_width">法人代表：</td>
		<td><input type="text" class="tab_text validate[[],custom[equalsSgsStr]]" maxlength="14"
			name="SQJG_LEALPERSON" value="${execution.SQJG_LEALPERSON}" id="SQJG_LEALPERSON" /></td>
		<td class="tab_width">联系电话：</td>
		<td><input type="text" class="tab_text validate[]" maxlength="14"
			name="SQJG_TEL" value="${execution.SQJG_TEL}" id="SQJG_TEL" /></td>
	</tr>
	<tr>
		<td class="tab_width"> 证件类型：</td>
		<td style="width:430px">
			<eve:eveselect clazz="tab_text validate[]" dataParams="DocumentType"
			dataInterface="dictionaryService.findDatasForSelect" onchange="setFrValidate(this.value);"
			defaultEmptyText="选择证件类型" name="SQJG_LEALPERSON_ZJLX" id="SQJG_LEALPERSON_ZJLX">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 证件号码：</td>
		<td>
		  <input type="text" class="tab_text validate[]" maxlength="30" id="frzjhm" style="float: left;"
			name="SQJG_LEALPERSON_ZJHM" value="${execution.SQJG_LEALPERSON_ZJHM}"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width">联系地址：</td>
		<td colspan="3"><input type="text" class="tab_text" maxlength="60"
			name="SQJG_ADDR" value="${execution.SQJG_ADDR}" style="width: 72%;" id="SQJG_ADDR" />
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
		<th colspan="4">经办人信息</th>
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
	</tr>
</table>
</div>
