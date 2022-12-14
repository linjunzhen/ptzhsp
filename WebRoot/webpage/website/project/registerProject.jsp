<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="eve" uri="/evetag" %>
<%@ page import="net.evecom.core.util.FileUtil" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="pragma" content="no-cache">
	<meta name="renderer" content="webkit">
	<title></title>
	<!-- CSS -->
<%--	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/Select.css">--%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/website/project/css/eui.css">
</head>
<body>
<script type="text/javascript" src="<%=path%>/webpage/website/project/js/jquery.min.js"></script>
<eve:resources loadres="easyui,artdialog"></eve:resources>
<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
<script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>

<link rel="stylesheet"
	  href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
<link rel="stylesheet"
	  href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
<link rel="stylesheet"
	  href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
<!-- my97 end -->
<base href="<%=basePath%>">

<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>


<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
<div class="eui-main">
	<jsp:include page="head.jsp"></jsp:include>
	<jsp:include page="banner.jsp"></jsp:include>

	<script type="text/javascript">
		var userType = '${sessionScope.curLoginMember.USER_TYPE}';
		var xmdwxxDivHtml = "";
		$(function() {
			if(userType!='2'){
				alert("?????????????????????????????????????????????????????????!");
				location.href = "<%=path%>/userInfoController/mztLogin.do?returnUrl=projectWebsiteController/registerProject.do";
			}
			var lerepInfo = '${projectApply.lerep_info}';
			if(lerepInfo){
				var lerepInfoList = $.parseJSON(lerepInfo);
				for(var i=0 ; i<lerepInfoList.length; i++){
					initXmdwxx(lerepInfoList[i],i)
				}
			}
			xmdwxxDivHtml = $("#xmdwxxDiv").html();
			AppUtil.initWindowForm("xmdjDetailForm", function(form, valid) {
				if (valid) {

					getXmdwxxJson();
					$('#xmdjDetailForm').find('input,textarea').prop("disabled", false);
					$('#xmdjDetailForm').find('select').prop("disabled", false);
					//?????????????????????,??????????????????
					$("input[type='submit']").attr("disabled", "disabled");
					var formData = $("#xmdjDetailForm").serialize();
					var url = $("#xmdjDetailForm").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if (resultJson.success) {
								art.dialog({
									content : resultJson.msg,
									icon : "succeed",
									lock : true,
									ok:function(){
										window.location.href = "<%=path%>/projectWebsiteController.do?myProjectView";
									},
									close: function(){
										window.location.href = "<%=path%>/projectWebsiteController.do?myProjectView";
									}
								});
							} else {
								parent.art.dialog({
									content: resultJson.msg,
									icon:"error",
									ok: true
								});
								$("input[type='submit']").attr("disabled", false);
							}
						}
					});
				}
			}, "SPGL_XMJBXXB");


		});

		function onSelectClass(o){
			if(o==1){
				$("#resultcontent_tr").show();
				$("#resultcontent").attr("disabled",false);
			}else{
				$("#resultcontent_tr").hide();
				$("#resultcontent").attr("disabled",true);
			}
		}
		$().ready(function() {
			var projectApply = '${projectApply.PROJECT_CODE}';

			if(projectApply==null){
				loadTzxmxxData();
			}
			$("input[name='LEREP_INFO']").val('${projectApply.lerep_info}');
			$("input[name='CONTRIBUTION_INFO']").val('${projectApply.contribution_info}');
			isShowWztzxx();
			//??????????????????
			$("input,textarea").on('blur', function(event) {
				$(this).val(trim($(this).val()));
			});
		});

		//??????????????????
		function trim(str){
			return str.replace(/(^\s*)|(\s*$)/g,"");
		}
		function loadTzxmxxData(){
			var code = $("input[name='PROJECT_CODE']").val();
			if(null==code||''==code){
				art.dialog({
					content: "???????????????????????????",
					icon:"error",
					ok: true
				});
			}else{
				var layload = layer.load('????????????????????????');
				$.post("projectApplyController.do?loadTzxmxxData",{
							projectCode:code},
						function(responseText, status, xhr) {
							layer.close(layload);
							var resultJson = $.parseJSON(responseText);
							if (resultJson.result) {
								for(var key in resultJson.datas){
									//console.log(key + " : "+ resultJson.datas[key]);
									$("[name='"+key.toUpperCase()+"']").prop("readonly", true);
									$("[name='"+key.toUpperCase()+"']").prop("disabled", "disabled");
									if(key=='industry'){
										var typeCode = resultJson.datas[key];
										$.post( "dicTypeController/info.do",{
													typeCode:typeCode,path:"4028819d51cc6f280151cde6a3f00027"},
												function(responseText1, status, xhr) {
													var resultJson1 = $.parseJSON(responseText1);
													if(null!=resultJson1 && null!=resultJson1.TYPE_CODE && ''!=resultJson1.TYPE_CODE){
														$("#industry").html('<option value="'+resultJson1.TYPE_CODE+'" selected="selected">'+resultJson1.TYPE_NAME+'</option>')
														//$("[name='INDUSTRY']").selectMatch();
													} else{
														$("#industry").html('<option value="'+typeCode+'" selected="selected">'+typeCode+'</option>')
														//$("[name='INDUSTRY']").selectMatch();
													}
												});
									}else if(key=='place_code'){
										var typeCode2=resultJson.datas[key];
										$.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
												function(responseText2,status,xhr){
													var  resultJson2=$.parseJSON(responseText2);
													if(null!=resultJson2){
														$("#placeCode").html('<option value="'+resultJson2.TYPE_CODE+'" selected="selected">'+resultJson2.TYPE_NAME+'</option>')
													} else{
														$("#placeCode").html('<option value="'+typeCode2+'" selected="selected">'+typeCode2+'</option>')
													}
													//$("[name='PLACE_CODE']").selectMatch();
												});
									}else if(key=='industry_structure'){
										var typeCode3 = resultJson.datas[key];
										$.post( "dicTypeController/info.do",{
													typeCode:typeCode3},
												function(responseText3, status, xhr) {
													var resultJson3 = $.parseJSON(responseText3);
													if(null!=resultJson3){
														$("#industryStructure").html('<option value="'+resultJson3.TYPE_CODE+'" selected="selected">'+resultJson3.TYPE_NAME+'</option>')
														//$("[name='INDUSTRY_STRUCTURE']").selectMatch();
													}else{
														$("#industryStructure").html('<option value="'+typeCode3+'" selected="selected">'+typeCode3+'</option>')
														//$("[name='INDUSTRY_STRUCTURE']").selectMatch();
													}
												});
									}else if(key=='lerep_info'){
										//setTpl(JSON.stringify(resultJson.datas[key]));
										var lerepInfoList = resultJson.datas[key];
										for(var j=0;j<lerepInfoList.length;j++){
											initXmdwxx(lerepInfoList[j],j);
										}

										$("[name='LEREP_INFO']").val(JSON.stringify(resultJson.datas[key]));
									}else if(key=='contribution_info'){
										$("[name='CONTRIBUTION_INFO']").val(JSON.stringify(resultJson.datas[key]));
									}else if(key=='get_land_mode'){
										if(null != resultJson.datas[key] && '' != resultJson.datas[key]){
											$("[name='GET_LAND_MODE']").val(resultJson.datas[key]);
										} else{
											$("[name='"+key.toUpperCase()+"']").prop("readonly", false);
											$("[name='"+key.toUpperCase()+"']").prop("disabled", "");
										}
									}else{
										$("[name='"+key.toUpperCase()+"']").val(resultJson.datas[key]);
										//$("select[name='" + key.toUpperCase() + "']").selectMatch();
									}
								}
								$("#tzjbxx input").attr("readonly",false);
								$("#tzjbxx textarea").attr("readonly",false);
								$("#tzjbxx select").attr("readonly",false);
							} else {
								art.dialog({
									content: "????????????",
									icon:"error",
									ok: true
								});
							}
							isShowWztzxx();
							$("[name='PROJECT_CODE']").prop("disabled", "");
							$("[name='PROJECT_NAME']").prop("disabled", "");
						}
				);
			}
		};
		function getXmdwxxJson(){
			var infoArray = [];
			$("#xmdwxxDiv table").each(function(i){
				var enterprise_name = $(this).find("[name$='enterprise_name']").val();//????????????
				var dwlx = $(this).find("[name$='dwlx']").val();//????????????
				var lerep_certtype = $(this).find("[name$='lerep_certtype']").val();//????????????
				var lerep_certno = $(this).find("[name$='lerep_certno']").val();//????????????
				var contact_name = $(this).find("[name$='contact_name']").val();	//???????????????
				var contact_tel = $(this).find("[name$='contact_tel']").val();//????????????
				var contact_email = $(this).find("[name$='contact_email']").val();// ???????????????
				var enterprise_place = $(this).find("[name$='enterprise_place']").val();//????????????
				var enterprise_nature = $(this).find("[name$='enterprise_nature']").val();//????????????
				var china_foreign_share_ratio = $(this).find("[name$='china_foreign_share_ratio']").val();//????????????????????????????????????
				var business_scope = $(this).find("[name$='business_scope']").val();//??????????????????
				var contact_phone = $(this).find("[name$='contact_phone']").val();//????????????
				var contact_fax = $(this).find("[name$='contact_fax']").val();//??????
				var correspondence_address = $(this).find("[name$='correspondence_address']").val();//????????????

				var info = {};
				info.enterprise_name = enterprise_name;
				info.dwlx = dwlx;
				info.lerep_certtype = lerep_certtype;
				info.lerep_certno = lerep_certno;
				info.contact_name = contact_name;
				info.contact_tel = contact_tel;
				info.contact_email = contact_email;
				info.enterprise_place = enterprise_place;
				info.enterprise_nature = enterprise_nature;
				info.china_foreign_share_ratio = china_foreign_share_ratio;
				info.business_scope = business_scope;
				info.contact_phone = contact_phone;
				info.contact_fax = contact_fax;
				info.correspondence_address = correspondence_address;

				infoArray.push(info);
			});
			$("[name='LEREP_INFO']").val(JSON.stringify(infoArray));
		}
		function initXmdwxx(dwxx,i){
			if(i>0){
				$("#xmdwxxDiv").html(xmdwxxDivHtml);
			}
			initFormObjValue(dwxx,$("#xmdwxxDiv table").eq(i));
		}
		/**
		 * ????????????????????????
		 * @param {} fieldValueObj
		 * @param {} elementObj
		 */
		function initFormObjValue(fieldValueObj,elementObj){
			for(var fieldName in fieldValueObj){
				//????????????????????????
				var targetFields = elementObj.find("[name$='"+fieldName+"']");
				targetFields.each(function(){
					var targetField = $(this);
					var type = targetField.attr("type");
					var tagName = targetField.get(0).tagName;
					var fieldValue = fieldValueObj[fieldName];

					if(type=="radio"){
						var radioValue = targetField.val();
						if(radioValue==fieldValue){
							$(this).attr("checked","checked");
						}
					}else if(type=="checkbox"){
						var checkBoxValue = targetField.val();
						var isChecked = AppUtil.isContain(fieldValue.split(","),checkBoxValue);
						if(isChecked){
							$(this).attr("checked","checked");
						}
					}else if(tagName=="SELECT"){
						targetField.children("option[value='"+fieldValueObj[fieldName]+"']")
								.attr("selected", "selected");
						//targetField.selectMatch();
					}else{
						targetField.val(fieldValueObj[fieldName]);
					}
					if(fieldName!='dwlx'){
						targetField.prop("readonly", true);
						targetField.prop("disabled", "disabled");
					}
				});
			}
		}
		function isShowWztzxx(){
			if($("#foreignabroadFlag").val()==1){
				$("#jwtzxx").attr("style","display:none;");
				$("#wstzxx").attr("style","display:;");
			}else if($("#foreignabroadFlag").val()==2){
				$("#wstzxx").attr("style","display:none;");
				$("#jwtzxx").attr("style","display:;");
			}else{
				$("#wstzxx").attr("style","display:none;");
				$("#jwtzxx").attr("style","display:none;");
			}
			if($("#totalMoney").val()==0){
				$("#totalMoneyExplain").removeClass("");
				$("#totalMoneyExplain").toggleClass('validate[required]');
			}else{
				$("#totalMoneyExplain").removeClass("validate[required]");
				$("#totalMoneyExplain").toggleClass('');
			}
		}
		function getDicNames(typeCode,dicCodes){
			$.post( "dictionaryController/textname.do",
					{
						typeCode:typeCode,dicCodes:dicCodes
					},
					function(responseText3, status, xhr) {
						return responseText3;
					});
		}
		function getLerepcertTypeNames(code){
			if(code == 'A05100'){
				return '???????????????????????????????????????';
			} else if(code == 'A05201'){
				return '???????????????????????????????????????';
			} else if(code == 'A05202'){
				return '?????????????????????????????????????????????';
			} else if(code == 'A05203'){
				return '?????????????????????????????????????????????';
			} else if(code == 'A05204'){
				return '?????????????????????????????????????????????';
			} else if(code == 'A05300'){
				return '????????????????????????';
			} else if(code == 'A05900'){
				return '??????';
			} else{
				return '';
			}
		}
		function getEnterpriseNatureNames(code){
			if(code == 'A00001'){
				return '??????????????????';
			} else if(code == 'A00002'){
				return '??????????????????';
			} else if(code == 'A00003'){
				return '?????????????????????';
			} else if(code == 'A00004'){
				return '???????????????????????????';
			} else if(code == 'A00004'){
				return '??????????????????';
			} else if(code == 'A00006'){
				return '????????????????????????';
			} else if(code == 'A00007'){
				return '???????????????????????????????????????';
			} else if(code == 'A00008'){
				return '??????';
			} else{
				return '';
			}
		}
		function getYesOrNoNames(code){
			if(code == '0'){
				return '????????????????????????????????????';
			} else if(code == '1'){
				return '????????????????????????????????????';
			} else{
				return '';
			}
		}
		function showItemSelector() {
			parent.$.dialog.open("serviceItemController/selector.do", {
				title : "???????????????",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectItemInfo = art.dialog.data("selectItemInfo");
					if (selectItemInfo) {
						$("input[name='ITEM_NAME']").val(selectItemInfo.itemNames);
						$("input[name='ITEM_CODE']").val(selectItemInfo.itemCodes);
						art.dialog.removeData("selectItemInfo");
					}
				}
			}, false);
		}
		function showXmflSelector() {
			parent.$.dialog.open("projectApplyController/selector.do?allowCount=1", {
				title : "?????????????????????",
				width : "1000px",
				lock : true,
				resize : false,
				height : "500px",
				close : function() {
					var selectXmflInfo = art.dialog.data("selectXmflInfo");
					if (selectXmflInfo) {
						$("input[name='FLOW_CATE_ID']").val(selectXmflInfo.ids);
						$("input[name='FLOW_CATE_NAME']").val(selectXmflInfo.names);
						art.dialog.removeData("selectXmflInfo");
					}
				}
			}, false);
		}
		//????????????
		function materDelete(fileId){
			AppUtil.delUploadProjectMater(fileId);
		}
	</script>
	<form id="xmdjDetailForm" method="post"
		  action="projectWebsiteApplyController.do?websiteSaveOrUpdate">
	<!-- ?????? -->
	<div class="eui-con">
		<div class="slideTxtBox eui-tab">
			<div class="hd" style="margin-bottom: 12px;">
				<ul class="eui-flex">
					<li>??????????????????</li>
					<li>??????(??????)????????????</li>
				</ul>
			</div>
			<div class="bd">
				<!-- ?????????????????? -->
				<div class="" id="projectInfo">
					<div    class="eui-table-info">
						<input type="hidden" name="ID" value="${entityId}">
						<input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
						<input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
						<input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG" value="${projectApply.FOREIGN_ABROAD_FLAG}"/>
						<input type="hidden" id="theIndustry" name="THE_INDUSTRY" value="${projectApply.THE_INDUSTRY}"/>
						<input type="hidden" id="FLOW_CATE_ID" name="FLOW_CATE_ID" value="${projectApply.FLOW_CATE_ID}"/>
						<table>
							<colgroup>
								<col>
								<col width="200">
								<col>
								<col width="200">
							</colgroup>
							<tr>
								<th><i>*</i> ??????????????????</th>
								<td >
									<div class="eui-flex bt">
									<c:if test="${entityId == null }">
										<div class="flex1">
										<input class="eui-bh-check ipt validate[required,ajax[ajaxVerifyValueExist]]" type="text" name="PROJECT_CODE"
											   id="PROJECT_CODE" value="${projectApply.PROJECT_CODE}"/>
										</div>
										<c:if test="${empty projectApply}">
										<a class="eui-btn" href="javascript:loadTzxmxxData()">??????</a>
										</c:if>
									</c:if>
									<c:if test="${entityId != null }">
									<div>
										<input class="ipt validate[required]" type="text" name="PROJECT_CODE"
											   id="PROJECT_CODE" value="${projectApply.PROJECT_CODE}" readonly/>
									</div>
									</c:if>
									</div>
								</td>
								<th> ????????????</th>
								<td>
									<input type="text" class="ipt " id="FLOW_CATE_NAME"
										   name="FLOW_CATE_NAME" value="${projectApply.FLOW_CATE_NAME}" readonly/>
								</td>
							</tr>
							<tr>
								<th><i>*</i> ????????????</th>
								<td>
									<c:if test="${empty projectApply.PROJECT_NAME}">
										<input type="text" class="ipt validate[required,ajax[ajaxVerifyValueExist]]" id="PROJECT_NAME"  maxlength="64"
											   name="PROJECT_NAME"  value="${projectApply.PROJECT_NAME}" />
									</c:if>
									<c:if test="${!empty projectApply.PROJECT_NAME}">
										<input type="text" class="ipt validate[required]" id="PROJECT_NAME"  maxlength="64"
											   name="PROJECT_NAME"  value="${projectApply.PROJECT_NAME}" readonly/>
									</c:if>
								</td>
								<th><i>*</i> ????????????</th>
								<td>
									<eve:eveselect clazz=" validate[required] "  dataParams="PROJECTTYPE"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_TYPE}"
												   defaultEmptyText="?????????????????????" name="PROJECT_TYPE" id="projectType">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th><i>*</i> ??????????????????</th>
								<td>
									<input type="text" maxlength="6"  class="ipt validate[required]"
										   value="${projectApply.DIVISION_CODE}" name="DIVISION_CODE" />
								</td>
								<th> ????????????????????????</th>
								<td>
									<input type="text" maxlength="16" class="ipt validate[]"
										   value="${projectApply.PERMIT_ITEM_CODE}" name="PERMIT_ITEM_CODE" />
								</td>
							</tr>
							<tr>
								<th><i>*</i> ????????????</th>
								<td>
									<eve:eveselect clazz="1  validate[required] " dataParams="PROJECTNATURE"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_NATURE}"
												   defaultEmptyText="?????????????????????" name="PROJECT_NATURE" id="PROJECT_NATURE">
									</eve:eveselect>
								</td>
								<th><i>*</i>??????????????????</th>
								<td>
									<input type="text" maxlength="500" class="ipt validate[required]"
										   id="PLACE_CODE_DETAIL"  name="PLACE_CODE_DETAIL"  value="${projectApply.PLACE_CODE_DETAIL}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>???????????????</th>
								<td>
									<input readonly="true" id="startYear" type="text" maxlength="4" class="ipt  Wdate"
										   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR"  value="${projectApply.START_YEAR}"/>
								</td>
								<th><i>*</i>???????????????</th>
								<td>
									<input readonly="true" id="endYear" type="text" maxlength="4" class="ipt  Wdate"
										   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR" value="${projectApply.END_YEAR}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>?????????????????????</th>
								<td>
									<input  id="totalMoney" type="text" maxlength="16" class="ipt validate[required]"
											name="TOTAL_MONEY"  value="${projectApply.TOTAL_MONEY}" />
								</td>
								<th><i>*</i>????????????</th>
								<td>
									<input readonly="true" id="APPLY_DATE" type="text" maxlength="4" class="ipt  Wdate"
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"
										   name="APPLY_DATE"  value="${projectApply.APPLY_DATE}" />
								</td>
							</tr>
							<tr>
								<th>???????????????0?????????</th>
								<td colspan="3">
												<textarea id="totalMoneyExplain" class="eui-input-w" rows="3" cols="200"
														  name="TOTAL_MONEY_EXPLAIN" >${projectApply.TOTAL_MONEY_EXPLAIN}</textarea>
								</td>
							</tr>
							<tr>
								<th><i>*</i>????????????</th>
								<td>
									<div>
										<select id="placeCode" name="PLACE_CODE" class="ipt1  validate[required] " style="width:97%;">
											<option value="">?????????????????????</option>
										</select>
									</div>
								</td>
								<th><i>*</i> ????????????</th>
								<td>
									<div>
										<select id="industry" name="INDUSTRY" class="ipt1  validate[required] " style="width:97%;">
											<option value="">?????????????????????</option>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th><i>*</i>?????????????????????</th>
								<td colspan="3">
												<textarea id="SCALE_CONTENT" class="eui-input-w validate[required]"
														  rows="3" cols="200" name="SCALE_CONTENT" >${projectApply.SCALE_CONTENT}</textarea>
								</td>
							</tr>
							<tr>
								<th><i>*</i>????????????</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="PROJECTATTRIBUTES"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_ATTRIBUTES}"
												   defaultEmptyText="???????????????????????????" name="PROJECT_ATTRIBUTES" id="PROJECT_ATTRIBUTES">
									</eve:eveselect>
								</td>
								</td>
								<th><i>*</i>????????????????????????</th>
								<td>
									<div>
										<select id="industryStructure" name="INDUSTRY_STRUCTURE" class="ipt1  validate[required] " style="width:97%;">
											<option value="">???????????????????????????????????????</option>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th><i>*</i>??????????????????</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="getLandMode"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.GET_LAND_MODE}"
												   defaultEmptyText="???????????????????????????" name="GET_LAND_MODE" id="getLandMode">
									</eve:eveselect>
								</td>
								<th><i>*</i> ??????????????????</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="XMTZLY"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.XMTZLY}"
												   defaultEmptyText="???????????????????????????" name="XMTZLY" id="XMTZLY">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th><i>*</i> ????????????</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="GCFL"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.GCFL}"
												   defaultEmptyText="?????????????????????" name="GCFL" id="GCFL">
									</eve:eveselect>
								</td>
								<th><i>*</i> ????????????????????????</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="SFWCQYPG"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.SFWCQYPG}"
												   defaultEmptyText="?????????????????????????????????" name="SFWCQYPG" id="SFWCQYPG">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th><i>*</i> ???????????????????????????</th>
								<td colspan="3">
									<eve:eveselect clazz="1  validate[required]" dataParams="TDSFDSJFA"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.TDSFDSJFA}"
												   defaultEmptyText="????????????????????????????????????" name="TDSFDSJFA" id="TDSFDSJFA">
									</eve:eveselect>
								</td>
							</tr>
						</table>
					</div>
					<div id="wstzxx" class="eui-table-info"  style="display: none">
						<colgroup>
							<col>
							<col width="200">
							<col>
							<col width="200">
						</colgroup>
						<div class="eui-table">
							<p>??????????????????</p>
						</div>
						<table>
							<tr>
								<th><i>*</i>????????????????????????</th>
								<td>
									<eve:eveselect clazz="  validate[required] " dataParams="YesOrNo"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.IS_COUNTRY_SECURITY}"
												   onchange="showSecurityApprovalNumber(this.value)"
												   defaultEmptyText="?????????????????????????????????" name="IS_COUNTRY_SECURITY" id="IS_COUNTRY_SECURITY">
									</eve:eveselect>
								</td>
								<th>????????????????????????</th>
								<td>
									<input type="text" maxlength="16" class="ipt" id="SECURITY_APPROVAL_NUMBER"
										   name="SECURITY_APPROVAL_NUMBER"  value="${projectApply.SECURITY_APPROVAL_NUMBER}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>????????????</th>
								<td>
									<eve:eveselect clazz="   validate[required]" dataParams="investmentMode"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INVESTMENT_MODE}"
												   defaultEmptyText="?????????????????????" name="INVESTMENT_MODE" id="investmentMode" >
									</eve:eveselect>
								</td>
								<th><i>*</i>???????????????(??????)</th>
								<td>
									<input type="text"  maxlength="18" class="ipt  validate[required,custom[numberp6plus]]"
										   id="projectCapitalMoney" name="PROJECT_CAPITAL_MONEY"
										   value="${projectApply.PROJECT_CAPITAL_MONEY}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>???????????????????????????<br/>????????????/?????????</th>
								<td>
									<input type="text" maxlength="100" class="ipt  validate[required,custom[numberp6plus]]"
										   id="totalMoneyDollarRate" name="TOTAL_MONEY_DOLLAR_RATE"
										   value="${projectApply.TOTAL_MONEY_DOLLAR_RATE}"  />
								</td>
								<th><i>*</i>????????????????????????<br>????????????</th>
								<td>
									<input type="text"  maxlength="18" class="ipt  validate[required,custom[numberp6plus]]"
										   id="totalMoneyDollar" name="TOTAL_MONEY_DOLLAR"  value="${projectApply.TOTAL_MONEY_DOLLAR}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>???????????????????????????<br/>????????????</th>
								<td>
									<input class="ipt validate[required,custom[numberp6plus]]" maxlength="100"
										   id="projectCapitalMoneyDollar" name="PROJECT_CAPITAL_MONEY_DOLLAR"
										   value="${projectApply.PROJECT_CAPITAL_MONEY_DOLLAR}"/>
								</td>
								<th><i>*</i>???????????????????????????<br>????????????/?????????</th>
								<td>
									<input type="text" maxlength="18" class="ipt validate[required,custom[numberp6plus]]"
										   id="projectCapitalMoneyRate" name="PROJECT_CAPITAL_MONEY_RATE"
										   value="${projectApply.PROJECT_CAPITAL_MONEY_RATE}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>????????????????????????</th>
								<td>
									<eve:eveselect clazz="  validate[required]" dataParams="industrialPolicyType"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INDUSTRIAL_POLICY_TYPE}"
												   defaultEmptyText="?????????????????????????????????" name="INDUSTRIAL_POLICY_TYPE" id="industrialPolicyType">
									</eve:eveselect>
								</td>
								<th>????????????????????????</th>
								<td>
									<eve:eveselect clazz=" " dataParams="industrialPolicy"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INDUSTRIAL_POLICY}"
												   defaultEmptyText="???????????????????????????" name="INDUSTRIAL_POLICY" id="industrialPolicy">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th>????????????????????????<br>??????????????????</th>
								<td colspan="3">
												<textarea id="otherInvestmentApplyInfo" class="eui-input-w validate[maxSize[2000]]" rows="3" cols="200"
														  name="OTHER_INVESTMENT_APPLY_INFO" >${projectApply.OTHER_INVESTMENT_APPLY_INFO}</textarea>
								</td>
							</tr>
							<tr>
								<th>????????????????????????</th>
								<td colspan="3">
												<textarea id="transactionBothInfo" class="eui-input-w validate[maxSize[2000]]" rows="3"
														  cols="200" name="TRANSACTION_BOTH_INFO" >${projectApply.TRANSACTION_BOTH_INFO}</textarea>
								</td>
							</tr>
							<tr>
								<th>????????????</th>
								<td colspan="3">
												<textarea id="mergerPlan" class="eui-input-w validate[maxSize[2000]]" rows="3"
														  cols="200" name="MERGER_PLAN" >${projectApply.MERGER_PLAN}</textarea>
								</td>
							</tr>
							<tr>
								<th>?????????????????????<br>???????????????</th>
								<td colspan="3">
												<textarea id="mergerManagementModeScope" class="eui-input-w validate[maxSize[2000]]" rows="3" cols="200"
														  name="MERGER_MANAGEMENT_MODE_SCOPE" >${projectApply.MERGER_MANAGEMENT_MODE_SCOPE}</textarea>
								</td>
							</tr>
							<tr>
								<th><i>*</i>???????????????(?????????)</th>
								<td>
									<input type="text" maxlength="100" class="ipt validate[required,custom[numberp6plus]]"
										   id="builtArea" name="BUILT_AREA" value="${projectApply.BUILT_AREA}"/>
								</td>
								<th><i>*</i>???????????????(?????????)</th>
								<td>
									<input type="text"  maxlength="18" class="ipt validate[required,custom[numberp6plus]]"
										   id="landArea" name="LAND_AREA" value="${projectApply.LAND_AREA}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>??????????????????</th>
								<td>
									<eve:eveselect clazz="  validate[required]" dataParams="YesOrNo"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.IS_ADD_DEVICE}"
												   defaultEmptyText="???????????????????????????" name="IS_ADD_DEVICE" id="IS_ADD_DEVICE">
									</eve:eveselect>
								</td>
								<th><i>*</i>???????????????????????????</th>
								<td>
									<input id="importDeviceNumberMoney" type="text" maxlength="100" class="ipt validate[required]"
										   name="IMPORT_DEVICE_NUMBER_MONEY"  value="${projectApply.IMPORT_DEVICE_NUMBER_MONEY}"/>
								</td>
							</tr>
						</table>
					</div>
					<div id="jwtzxx" class="eui-table-info" style="display: none">
						<table>
							<colgroup>
								<col>
								<col width="200">
								<col>
								<col width="200">
							</colgroup>
							<tr>
								<th><i>*</i>???????????????</th>
								<td>
									<input type="text" maxlength="100" class="ipt  validate[required]"
										   id="PROJECT_SITE" name="PROJECT_SITE"  value="${projectApply.PROJECT_SITE}" />
								</td>
								<th>???????????????(??????)</th>
								<td>
									<input type="text"  maxlength="18" class="ipt validate[required,custom[numberp6plus]]"
										   id="chinaTotalMoney" name="CHINA_TOTAL_MONEY" value="${projectApply.CHINA_TOTAL_MONEY}" />
								</td>
							</tr>
						</table>
					</div>

					<div class="eui-flex tc eui-sx-btn">
						<c:if test="${empty projectApply}">
<%--						<a class="eui-btn" href="javascript:void(0)" onclick="document:xmdjDetailForm.submit()">??? ???</a>--%>
						<input   value="??????" type="submit" class=" eui-btn" />
						</c:if>
					</div>
				</div>
				<!-- ??????(??????)???????????? -->
				<div class=""   >
					<div    class="eui-table-info" id="xmdwxxDiv">
					<table>
						<tr>
							<th><i>*</i> ????????????</th>
							<td>
								<input type="text" maxlength="100"
									   class="ipt validate[required]" name="enterprise_name" />
							</td>
							<th><i>*</i> ????????????</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input validate[required]" dataParams="DWLX"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="?????????????????????" name="dwlx" id="dwlx">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th><i>*</i> ????????????</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input validate[required]" dataParams="LEREPCERTTYPE"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="?????????????????????" name="lerep_certtype" id="lerep_certtype">
								</eve:eveselect>
							</td>
							<th><i>*</i> ????????????</th>
							<td>
								<input type="text" maxlength="32"
									   class="ipt validate[required]" name="lerep_certno" />
							</td>
						</tr>
						<tr>
							<th><i>*</i> ???????????????</th>
							<td>
								<input type="text" maxlength="16"
									   class="ipt validate[required]" name="contact_name" />
							</td>
							<th><i>*</i> ????????????</th>
							<td>
								<input type="text" maxlength="16"
									   class="ipt validate[required]" name="contact_tel" />
							</td>
						</tr>
						<tr>
							<th>???????????????</th>
							<td>
								<input type="text"
									   class="ipt validate[]" name="contact_email" />
							</td>
							<th>????????????</th>
							<td>
								<input type="text"  maxlength="128"
									   class="ipt" name="enterprise_place" />
							</td>
						</tr>
						<tr>
							<th>????????????????????????</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input" dataParams="chinaForeignShareRatio"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="?????????????????????????????????????????????" name="china_foreign_share_ratio" id="china_foreign_share_ratio">
								</eve:eveselect>
							</td>
							<th>????????????</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input" dataParams="enterpriseNature"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="?????????????????????" name="enterprise_nature" id="enterprise_nature">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th>??????????????????</th>
							<td>
								<input type="text" maxlength="512"
									   class="ipt" name="business_scope" />
							</td>
							<th>????????????</th>
							<td>
								<input type="text" maxlength="16"
									   class="ipt" name="contact_phone" />
							</td>
						</tr>
						<tr>
							<th>??????</th>
							<td>
								<input type="text"
									   class="ipt validate[]" name="contact_fax" />
							</td>
							<th>????????????</th>
							<td>
								<input type="text" maxlength="64"
									   class="ipt validate[]" name="correspondence_address" />
							</td>
						</tr>
					</table>
					</div>

					<c:if test="${empty projectApply}">
					<div class="eui-flex tc eui-sx-btn">
						<input   value="??????" type="submit" class=" eui-btn" />
					</div>
					</c:if>
				</div>
			</div>
		</div>

	</div>
	<!-- ?????? end -->
	</form>
	<!-- ?????? -->
	<div class="eui-footer">
		<iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
	</div>
	<!-- ?????? end -->
</div>

<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
<%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/Select.js"></script>--%>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
<script type="text/javascript">
	$(function() {
		// banner??????
		jQuery(".slideBox").slide({titCell:".hd ul",mainCell:".bd ul",effect:"fold",autoPlay:true,autoPage:true,interTime:4000});
		// ???????????????
		jQuery(".slideTxtBox").slide({targetCell:".more a",delayTime:0,triggerTime: 0,trigger:"click"});
	});
</script>

</body>
</html>