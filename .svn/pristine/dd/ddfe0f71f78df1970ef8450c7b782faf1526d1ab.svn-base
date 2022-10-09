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
				alert("个人账号无法办理此事项，请登录法人账号!");
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
					//将提交按钮禁用,防止重复提交
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
			//清除前后空格
			$("input,textarea").on('blur', function(event) {
				$(this).val(trim($(this).val()));
			});
		});

		//清除前后空格
		function trim(str){
			return str.replace(/(^\s*)|(\s*$)/g,"");
		}
		function loadTzxmxxData(){
			var code = $("input[name='PROJECT_CODE']").val();
			if(null==code||''==code){
				art.dialog({
					content: "请填写投资项目编号",
					icon:"error",
					ok: true
				});
			}else{
				var layload = layer.load('正在提交校验中…');
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
									content: "校验失败",
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
				var enterprise_name = $(this).find("[name$='enterprise_name']").val();//单位名称
				var dwlx = $(this).find("[name$='dwlx']").val();//单位类型
				var lerep_certtype = $(this).find("[name$='lerep_certtype']").val();//证照类型
				var lerep_certno = $(this).find("[name$='lerep_certno']").val();//证件号码
				var contact_name = $(this).find("[name$='contact_name']").val();	//联系人名称
				var contact_tel = $(this).find("[name$='contact_tel']").val();//联系电话
				var contact_email = $(this).find("[name$='contact_email']").val();// 联系人邮箱
				var enterprise_place = $(this).find("[name$='enterprise_place']").val();//注册地址
				var enterprise_nature = $(this).find("[name$='enterprise_nature']").val();//单位性质
				var china_foreign_share_ratio = $(this).find("[name$='china_foreign_share_ratio']").val();//持股比例是否与资本金相同
				var business_scope = $(this).find("[name$='business_scope']").val();//主要经营范围
				var contact_phone = $(this).find("[name$='contact_phone']").val();//联系手机
				var contact_fax = $(this).find("[name$='contact_fax']").val();//传真
				var correspondence_address = $(this).find("[name$='correspondence_address']").val();//通讯地址

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
		 * 初始化表单字段值
		 * @param {} fieldValueObj
		 * @param {} elementObj
		 */
		function initFormObjValue(fieldValueObj,elementObj){
			for(var fieldName in fieldValueObj){
				//获取目标控件对象
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
				return '企业营业执照（工商注册号）';
			} else if(code == 'A05201'){
				return '组织机构代码证（企业法人）';
			} else if(code == 'A05202'){
				return '组织机构代码证（国家机关法人）';
			} else if(code == 'A05203'){
				return '组织机构代码证（事业单位法人）';
			} else if(code == 'A05204'){
				return '组织机构代码证（社会团体法人）';
			} else if(code == 'A05300'){
				return '统一社会信用代码';
			} else if(code == 'A05900'){
				return '其他';
			} else{
				return '';
			}
		}
		function getEnterpriseNatureNames(code){
			if(code == 'A00001'){
				return '中外合资企业';
			} else if(code == 'A00002'){
				return '中外合作企业';
			} else if(code == 'A00003'){
				return '国有及国有控股';
			} else if(code == 'A00004'){
				return '民营及民营控股企业';
			} else if(code == 'A00004'){
				return '外商独资企业';
			} else if(code == 'A00006'){
				return '外商投资合伙企业';
			} else if(code == 'A00007'){
				return '外商投资企业境内再投资企业';
			} else if(code == 'A00008'){
				return '其他';
			} else{
				return '';
			}
		}
		function getYesOrNoNames(code){
			if(code == '0'){
				return '与项目资本金出资结构相同';
			} else if(code == '1'){
				return '与项目资本金出资结构不同';
			} else{
				return '';
			}
		}
		function showItemSelector() {
			parent.$.dialog.open("serviceItemController/selector.do", {
				title : "事项选择器",
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
				title : "项目分类选择器",
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
		//文件删除
		function materDelete(fileId){
			AppUtil.delUploadProjectMater(fileId);
		}
	</script>
	<form id="xmdjDetailForm" method="post"
		  action="projectWebsiteApplyController.do?websiteSaveOrUpdate">
	<!-- 主体 -->
	<div class="eui-con">
		<div class="slideTxtBox eui-tab">
			<div class="hd" style="margin-bottom: 12px;">
				<ul class="eui-flex">
					<li>项目基本信息</li>
					<li>项目(法人)单位信息</li>
				</ul>
			</div>
			<div class="bd">
				<!-- 项目基本信息 -->
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
								<th><i>*</i> 投资项目编号</th>
								<td >
									<div class="eui-flex bt">
									<c:if test="${entityId == null }">
										<div class="flex1">
										<input class="eui-bh-check ipt validate[required,ajax[ajaxVerifyValueExist]]" type="text" name="PROJECT_CODE"
											   id="PROJECT_CODE" value="${projectApply.PROJECT_CODE}"/>
										</div>
										<c:if test="${empty projectApply}">
										<a class="eui-btn" href="javascript:loadTzxmxxData()">校验</a>
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
								<th> 项目分类</th>
								<td>
									<input type="text" class="ipt " id="FLOW_CATE_NAME"
										   name="FLOW_CATE_NAME" value="${projectApply.FLOW_CATE_NAME}" readonly/>
								</td>
							</tr>
							<tr>
								<th><i>*</i> 项目名称</th>
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
								<th><i>*</i> 项目类型</th>
								<td>
									<eve:eveselect clazz=" validate[required] "  dataParams="PROJECTTYPE"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_TYPE}"
												   defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th><i>*</i> 项目所属区划</th>
								<td>
									<input type="text" maxlength="6"  class="ipt validate[required]"
										   value="${projectApply.DIVISION_CODE}" name="DIVISION_CODE" />
								</td>
								<th> 投资项目目录编码</th>
								<td>
									<input type="text" maxlength="16" class="ipt validate[]"
										   value="${projectApply.PERMIT_ITEM_CODE}" name="PERMIT_ITEM_CODE" />
								</td>
							</tr>
							<tr>
								<th><i>*</i> 建设性质</th>
								<td>
									<eve:eveselect clazz="1  validate[required] " dataParams="PROJECTNATURE"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_NATURE}"
												   defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="PROJECT_NATURE">
									</eve:eveselect>
								</td>
								<th><i>*</i>建设地点详情</th>
								<td>
									<input type="text" maxlength="500" class="ipt validate[required]"
										   id="PLACE_CODE_DETAIL"  name="PLACE_CODE_DETAIL"  value="${projectApply.PLACE_CODE_DETAIL}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>拟开工时间</th>
								<td>
									<input readonly="true" id="startYear" type="text" maxlength="4" class="ipt  Wdate"
										   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR"  value="${projectApply.START_YEAR}"/>
								</td>
								<th><i>*</i>拟建成时间</th>
								<td>
									<input readonly="true" id="endYear" type="text" maxlength="4" class="ipt  Wdate"
										   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR" value="${projectApply.END_YEAR}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>总投资（万元）</th>
								<td>
									<input  id="totalMoney" type="text" maxlength="16" class="ipt validate[required]"
											name="TOTAL_MONEY"  value="${projectApply.TOTAL_MONEY}" />
								</td>
								<th><i>*</i>申报时间</th>
								<td>
									<input readonly="true" id="APPLY_DATE" type="text" maxlength="4" class="ipt  Wdate"
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"
										   name="APPLY_DATE"  value="${projectApply.APPLY_DATE}" />
								</td>
							</tr>
							<tr>
								<th>总投资额为0时说明</th>
								<td colspan="3">
												<textarea id="totalMoneyExplain" class="eui-input-w" rows="3" cols="200"
														  name="TOTAL_MONEY_EXPLAIN" >${projectApply.TOTAL_MONEY_EXPLAIN}</textarea>
								</td>
							</tr>
							<tr>
								<th><i>*</i>建设地点</th>
								<td>
									<div>
										<select id="placeCode" name="PLACE_CODE" class="ipt1  validate[required] " style="width:97%;">
											<option value="">请选择建设地点</option>
										</select>
									</div>
								</td>
								<th><i>*</i> 国标行业</th>
								<td>
									<div>
										<select id="industry" name="INDUSTRY" class="ipt1  validate[required] " style="width:97%;">
											<option value="">请选择国标行业</option>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th><i>*</i>建设规模及内容</th>
								<td colspan="3">
												<textarea id="SCALE_CONTENT" class="eui-input-w validate[required]"
														  rows="3" cols="200" name="SCALE_CONTENT" >${projectApply.SCALE_CONTENT}</textarea>
								</td>
							</tr>
							<tr>
								<th><i>*</i>项目属性</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="PROJECTATTRIBUTES"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_ATTRIBUTES}"
												   defaultEmptyText="请选择投资项目属性" name="PROJECT_ATTRIBUTES" id="PROJECT_ATTRIBUTES">
									</eve:eveselect>
								</td>
								</td>
								<th><i>*</i>产业结构指导目录</th>
								<td>
									<div>
										<select id="industryStructure" name="INDUSTRY_STRUCTURE" class="ipt1  validate[required] " style="width:97%;">
											<option value="">请选择产业结构调整指导目录</option>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th><i>*</i>土地获取方式</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="getLandMode"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.GET_LAND_MODE}"
												   defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
									</eve:eveselect>
								</td>
								<th><i>*</i> 项目投资来源</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="XMTZLY"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.XMTZLY}"
												   defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th><i>*</i> 工程分类</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="GCFL"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.GCFL}"
												   defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
									</eve:eveselect>
								</td>
								<th><i>*</i> 是否完成区域评估</th>
								<td>
									<eve:eveselect clazz="1  validate[required]" dataParams="SFWCQYPG"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.SFWCQYPG}"
												   defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th><i>*</i> 土地是否带设计方案</th>
								<td colspan="3">
									<eve:eveselect clazz="1  validate[required]" dataParams="TDSFDSJFA"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.TDSFDSJFA}"
												   defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
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
							<p>外商投资信息</p>
						</div>
						<table>
							<tr>
								<th><i>*</i>是否涉及国家安全</th>
								<td>
									<eve:eveselect clazz="  validate[required] " dataParams="YesOrNo"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.IS_COUNTRY_SECURITY}"
												   onchange="showSecurityApprovalNumber(this.value)"
												   defaultEmptyText="请选择是否涉及国家安全" name="IS_COUNTRY_SECURITY" id="IS_COUNTRY_SECURITY">
									</eve:eveselect>
								</td>
								<th>安全审查决定文号</th>
								<td>
									<input type="text" maxlength="16" class="ipt" id="SECURITY_APPROVAL_NUMBER"
										   name="SECURITY_APPROVAL_NUMBER"  value="${projectApply.SECURITY_APPROVAL_NUMBER}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>投资方式</th>
								<td>
									<eve:eveselect clazz="   validate[required]" dataParams="investmentMode"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INVESTMENT_MODE}"
												   defaultEmptyText="请选择投资方式" name="INVESTMENT_MODE" id="investmentMode" >
									</eve:eveselect>
								</td>
								<th><i>*</i>项目资本金(万元)</th>
								<td>
									<input type="text"  maxlength="18" class="ipt  validate[required,custom[numberp6plus]]"
										   id="projectCapitalMoney" name="PROJECT_CAPITAL_MONEY"
										   value="${projectApply.PROJECT_CAPITAL_MONEY}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>总投资额使用的汇率<br/>（人民币/美元）</th>
								<td>
									<input type="text" maxlength="100" class="ipt  validate[required,custom[numberp6plus]]"
										   id="totalMoneyDollarRate" name="TOTAL_MONEY_DOLLAR_RATE"
										   value="${projectApply.TOTAL_MONEY_DOLLAR_RATE}"  />
								</td>
								<th><i>*</i>总投资额折合美元<br>（万元）</th>
								<td>
									<input type="text"  maxlength="18" class="ipt  validate[required,custom[numberp6plus]]"
										   id="totalMoneyDollar" name="TOTAL_MONEY_DOLLAR"  value="${projectApply.TOTAL_MONEY_DOLLAR}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>项目资本金折合美元<br/>（万元）</th>
								<td>
									<input class="ipt validate[required,custom[numberp6plus]]" maxlength="100"
										   id="projectCapitalMoneyDollar" name="PROJECT_CAPITAL_MONEY_DOLLAR"
										   value="${projectApply.PROJECT_CAPITAL_MONEY_DOLLAR}"/>
								</td>
								<th><i>*</i>项目资本金使用汇率<br>（人名币/美元）</th>
								<td>
									<input type="text" maxlength="18" class="ipt validate[required,custom[numberp6plus]]"
										   id="projectCapitalMoneyRate" name="PROJECT_CAPITAL_MONEY_RATE"
										   value="${projectApply.PROJECT_CAPITAL_MONEY_RATE}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>产业政策条目类型</th>
								<td>
									<eve:eveselect clazz="  validate[required]" dataParams="industrialPolicyType"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INDUSTRIAL_POLICY_TYPE}"
												   defaultEmptyText="请选择产业政策条目类型" name="INDUSTRIAL_POLICY_TYPE" id="industrialPolicyType">
									</eve:eveselect>
								</td>
								<th>适用产业政策条目</th>
								<td>
									<eve:eveselect clazz=" " dataParams="industrialPolicy"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INDUSTRIAL_POLICY}"
												   defaultEmptyText="请选择产业政策条目" name="INDUSTRIAL_POLICY" id="industrialPolicy">
									</eve:eveselect>
								</td>
							</tr>
							<tr>
								<th>其他投资方式需予<br>以申报的情况</th>
								<td colspan="3">
												<textarea id="otherInvestmentApplyInfo" class="eui-input-w validate[maxSize[2000]]" rows="3" cols="200"
														  name="OTHER_INVESTMENT_APPLY_INFO" >${projectApply.OTHER_INVESTMENT_APPLY_INFO}</textarea>
								</td>
							</tr>
							<tr>
								<th>提供交易双方情况</th>
								<td colspan="3">
												<textarea id="transactionBothInfo" class="eui-input-w validate[maxSize[2000]]" rows="3"
														  cols="200" name="TRANSACTION_BOTH_INFO" >${projectApply.TRANSACTION_BOTH_INFO}</textarea>
								</td>
							</tr>
							<tr>
								<th>并购安排</th>
								<td colspan="3">
												<textarea id="mergerPlan" class="eui-input-w validate[maxSize[2000]]" rows="3"
														  cols="200" name="MERGER_PLAN" >${projectApply.MERGER_PLAN}</textarea>
								</td>
							</tr>
							<tr>
								<th>并购后经营方式<br>及经营范围</th>
								<td colspan="3">
												<textarea id="mergerManagementModeScope" class="eui-input-w validate[maxSize[2000]]" rows="3" cols="200"
														  name="MERGER_MANAGEMENT_MODE_SCOPE" >${projectApply.MERGER_MANAGEMENT_MODE_SCOPE}</textarea>
								</td>
							</tr>
							<tr>
								<th><i>*</i>总建筑面积(平方米)</th>
								<td>
									<input type="text" maxlength="100" class="ipt validate[required,custom[numberp6plus]]"
										   id="builtArea" name="BUILT_AREA" value="${projectApply.BUILT_AREA}"/>
								</td>
								<th><i>*</i>总用地面积(平方米)</th>
								<td>
									<input type="text"  maxlength="18" class="ipt validate[required,custom[numberp6plus]]"
										   id="landArea" name="LAND_AREA" value="${projectApply.LAND_AREA}" />
								</td>
							</tr>
							<tr>
								<th><i>*</i>是否新增设备</th>
								<td>
									<eve:eveselect clazz="  validate[required]" dataParams="YesOrNo"
												   dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.IS_ADD_DEVICE}"
												   defaultEmptyText="请选择是否新增设备" name="IS_ADD_DEVICE" id="IS_ADD_DEVICE">
									</eve:eveselect>
								</td>
								<th><i>*</i>进口设备数量及金额</th>
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
								<th><i>*</i>项目所在地</th>
								<td>
									<input type="text" maxlength="100" class="ipt  validate[required]"
										   id="PROJECT_SITE" name="PROJECT_SITE"  value="${projectApply.PROJECT_SITE}" />
								</td>
								<th>中方投资额(万元)</th>
								<td>
									<input type="text"  maxlength="18" class="ipt validate[required,custom[numberp6plus]]"
										   id="chinaTotalMoney" name="CHINA_TOTAL_MONEY" value="${projectApply.CHINA_TOTAL_MONEY}" />
								</td>
							</tr>
						</table>
					</div>

					<div class="eui-flex tc eui-sx-btn">
						<c:if test="${empty projectApply}">
<%--						<a class="eui-btn" href="javascript:void(0)" onclick="document:xmdjDetailForm.submit()">提 交</a>--%>
						<input   value="提交" type="submit" class=" eui-btn" />
						</c:if>
					</div>
				</div>
				<!-- 项目(法人)单位信息 -->
				<div class=""   >
					<div    class="eui-table-info" id="xmdwxxDiv">
					<table>
						<tr>
							<th><i>*</i> 单位名称</th>
							<td>
								<input type="text" maxlength="100"
									   class="ipt validate[required]" name="enterprise_name" />
							</td>
							<th><i>*</i> 单位类型</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input validate[required]" dataParams="DWLX"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="请选择单位类型" name="dwlx" id="dwlx">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th><i>*</i> 证照类型</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input validate[required]" dataParams="LEREPCERTTYPE"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="请选择证照类型" name="lerep_certtype" id="lerep_certtype">
								</eve:eveselect>
							</td>
							<th><i>*</i> 证照号码</th>
							<td>
								<input type="text" maxlength="32"
									   class="ipt validate[required]" name="lerep_certno" />
							</td>
						</tr>
						<tr>
							<th><i>*</i> 联系人名称</th>
							<td>
								<input type="text" maxlength="16"
									   class="ipt validate[required]" name="contact_name" />
							</td>
							<th><i>*</i> 联系电话</th>
							<td>
								<input type="text" maxlength="16"
									   class="ipt validate[required]" name="contact_tel" />
							</td>
						</tr>
						<tr>
							<th>联系人邮箱</th>
							<td>
								<input type="text"
									   class="ipt validate[]" name="contact_email" />
							</td>
							<th>注册地址</th>
							<td>
								<input type="text"  maxlength="128"
									   class="ipt" name="enterprise_place" />
							</td>
						</tr>
						<tr>
							<th>持股比例是否相同</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input" dataParams="chinaForeignShareRatio"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="请选择持股比例是否与资本金相同" name="china_foreign_share_ratio" id="china_foreign_share_ratio">
								</eve:eveselect>
							</td>
							<th>单位性质</th>
							<td>
								<eve:eveselect clazz="ipt1 whf_input" dataParams="enterpriseNature"
											   dataInterface="dictionaryService.findDatasForSelect"
											   defaultEmptyText="请选择单位性质" name="enterprise_nature" id="enterprise_nature">
								</eve:eveselect>
							</td>
						</tr>
						<tr>
							<th>主要经营范围</th>
							<td>
								<input type="text" maxlength="512"
									   class="ipt" name="business_scope" />
							</td>
							<th>联系手机</th>
							<td>
								<input type="text" maxlength="16"
									   class="ipt" name="contact_phone" />
							</td>
						</tr>
						<tr>
							<th>传真</th>
							<td>
								<input type="text"
									   class="ipt validate[]" name="contact_fax" />
							</td>
							<th>通讯地址</th>
							<td>
								<input type="text" maxlength="64"
									   class="ipt validate[]" name="correspondence_address" />
							</td>
						</tr>
					</table>
					</div>

					<c:if test="${empty projectApply}">
					<div class="eui-flex tc eui-sx-btn">
						<input   value="提交" type="submit" class=" eui-btn" />
					</div>
					</c:if>
				</div>
			</div>
		</div>

	</div>
	<!-- 主体 end -->
	</form>
	<!-- 底部 -->
	<div class="eui-footer">
		<iframe frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no" allowtransparency="true" src="<%=basePath%>/webpage/website/project/foot.html"></iframe>
	</div>
	<!-- 底部 end -->
</div>

<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/jquery.SuperSlide.2.1.3.js"></script>
<%--<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/Select.js"></script>--%>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/webpage/website/project/js/totop.js"></script>
<script type="text/javascript">
	$(function() {
		// banner切换
		jQuery(".slideBox").slide({titCell:".hd ul",mainCell:".bd ul",effect:"fold",autoPlay:true,autoPage:true,interTime:4000});
		// 选项卡切换
		jQuery(".slideTxtBox").slide({targetCell:".more a",delayTime:0,triggerTime: 0,trigger:"click"});
	});
</script>

</body>
</html>