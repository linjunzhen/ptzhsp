<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="net.evecom.core.util.AppUtil"%>
<%@ page language="java" import="net.evecom.platform.wsbs.service.BusTypeService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<eve:resources
		loadres="easyui"></eve:resources>
<script type="text/javascript" src="<%=path %>/webpage/bsdt/applyform/js/solely1.js"></script>
<script type="text/javascript">
	var codeMap = {};
	var countryMap = {};
	var contributionModeMap = {};
	var businessTypeMap = {};
	var chinaForeignShareRatioMap = {};
	var enterpriseNatureMap = {};
	$(function(){
		$("#totalMoney").blur(function(){ totalMoney(); } );
		$("#enterpriseName").blur(function(){ changeInfo(); } );
		$("#lerepCertType").blur(function(){ changeInfo(); } );
		$("#lerepCertNo").blur(function(){ changeInfo(); } );
		$("#contactName").blur(function(){ changeInfo(); } );
		$("#contactTel").blur(function(){ changeInfo(); } );
		$("#contactEmail").blur(function(){ changeInfo(); } );
		$.ajax({
			url:"dicTypeController/dic.do",
			data:{typeCode:"LEREPCERTTYPE"},
			dataType:"json",
			type:"post",
			success:function(data){
				for	(var i = 0 ;i< data.length ;i++){
					codeMap[data[i].DIC_CODE]=data[i].DIC_NAME;
				}
			},
			error:function(data){}
		})
		$.ajax({
			url:"dicTypeController/dic.do",
			data:{typeCode:"Country"},
			dataType:"json",
			type:"post",
			success:function(data){
				for	(var i = 0 ;i< data.length ;i++){
					countryMap[data[i].DIC_CODE]=data[i].DIC_NAME;
				}
			},
			error:function(data){}
		})
		$.ajax({
			url:"dicTypeController/dic.do",
			data:{typeCode:"contributionMode"},
			dataType:"json",
			type:"post",
			success:function(data){
				for	(var i = 0 ;i< data.length ;i++){
					contributionModeMap[data[i].DIC_CODE]=data[i].DIC_NAME;
				}
			},
			error:function(data){}
		})
		$.ajax({
			url:"dicTypeController/dic.do",
			data:{typeCode:"businessType"},
			dataType:"json",
			type:"post",
			success:function(data){
				for	(var i = 0 ;i< data.length ;i++){
					businessTypeMap[data[i].DIC_CODE]=data[i].DIC_NAME;
				}
			},
			error:function(data){}
		})
		$.ajax({
			url:"dicTypeController/dic.do",
			data:{typeCode:"chinaForeignShareRatio"},
			dataType:"json",
			type:"post",
			success:function(data){
				for	(var i = 0 ;i< data.length ;i++){
					chinaForeignShareRatioMap[data[i].DIC_CODE]=data[i].DIC_NAME;
				}
			},
			error:function(data){}
		})
		$.ajax({
			url:"dicTypeController/dic.do",
			data:{typeCode:"enterpriseNature"},
			dataType:"json",
			type:"post",
			success:function(data){
				for	(var i = 0 ;i< data.length ;i++){
					enterpriseNatureMap[data[i].DIC_CODE]=data[i].DIC_NAME;
				}
			},
			error:function(data){}
		})
		var projectCode = document.getElementById("projectCode").value;
		if(projectCode == null || projectCode.length == 0 || projectCode == "null"){
			projectCode = document.getElementById("project_code").value;
		}
		if(projectCode!=null && projectCode.length>0 && projectCode != "null"){
			loadTZXMXXData();
		}
	});
	function loadTZXMXXData(){
		var code = $("input[name='PROJECTCODE']").val();
		document.getElementById("projectCode").value = code;
		if(null==code||''==code){
			art.dialog({
				content: "???????????????????????????",
				icon:"error",
				ok: true
			});
		}else{
			var layload = layer.load('????????????????????????');
			$.post( "webSiteController/loadTZXMXXData.do",{
						projectCode:code},
					function(responseText, status, xhr) {
						layer.close(layload);
						var resultJson = $.parseJSON(responseText);
						if (resultJson.result) {
							for(var key in resultJson.tzProject){
								if(key=='industry'){
									var typeCode = resultJson.tzProject[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode,path:"4028819d51cc6f280151cde6a3f00027"},
											function(responseText1, status, xhr) {
												var resultJson1 = $.parseJSON(responseText1);
												if(null!=resultJson1){
													$("#industry").combotree("setValue",resultJson1.TYPE_CODE);
													$("#industry").combotree("setText",resultJson1.TYPE_NAME);
												}
											});
								}else if(key=='placeCode'){
									var typeCode2=resultJson.tzProject[key];
									$.post("dicTypeController/placeInfo.do",{typeCode:typeCode2},
											function(responseText2,status,xhr){
												var  resultJson2=$.parseJSON(responseText2);
												if(null!=resultJson2){
													$("#placeCode").combotree("setValue",resultJson2.TYPE_CODE);
													$("#placeCode").combotree("setText",resultJson2.TYPE_NAME);
												}
											});
								}else if(key=='industryStructure'){
									var typeCode = resultJson.tzProject[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode},
											function(responseText3, status, xhr) {
												var resultJson3 = $.parseJSON(responseText3);
												if(null!=resultJson3){
													$("#industryStructure").combotree("setValue",resultJson3.TYPE_CODE);
													$("#industryStructure").combotree("setText",resultJson3.TYPE_NAME);
												}
											});
								}else if(key=='industryStructure_CD'){
									var typeCode = resultJson.tzProject[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode},
											function(responseText3, status, xhr) {
												var resultJson3 = $.parseJSON(responseText3);
												if(null!=resultJson3){
													$("#industryStructure_CD").combotree("setValue",resultJson3.TYPE_CODE);
													$("#industryStructure_CD").combotree("setText",resultJson3.TYPE_NAME);
												}
											});
								}else{
									$("#"+key).val(resultJson.tzProject[key]);
								}
							}
							 $("#tzjbxx input").attr("readonly",true);
							 $("#tzjbxx textarea").attr("readonly",true);
							 $("#tzjbxx select").attr("readonly",true);

							$("#tzjbxx input[name$=CD]").attr("readonly",false);
							$("#tzjbxx textarea[name$=CD]").attr("readonly",false);
							$("#tzjbxx select[name$=CD]").attr("readonly",false);

						} else {
							art.dialog({
								content: "????????????",
								icon:"error",
								ok: true
							});
						}
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
						lerepContributionData();
					}
			);
		}
	};

	function lerepContributionData() {
	}

	function onSelectClass(o){
		if(o==1){
			$("#deAreaName").removeClass("validate[]");
			$("#deAreaName").toggleClass('validate[required]');
		}else{
			$("#deAreaName").removeClass("validate[required]");
			$("#deAreaName").toggleClass('validate[]');
		}
	}
	function showSecurityApprovalNumber(o){
		if(o==1){
			$("#securityApprovalNumberName").attr("style","display:;");
			$("#securityApprovalNumber").attr("style","display:;");
			$("#securityApprovalNumberNameBlank").attr("style","display:none;");
			$("#securityApprovalNumberBlank").attr("style","display:none");
		}else{
			$("#securityApprovalNumberName").attr("style","display:none;");
			$("#securityApprovalNumber").attr("style","display:none;");
			$("#securityApprovalNumberNameBlank").attr("style","display:;");
			$("#securityApprovalNumberBlank").attr("style","display:");
		}
	}
	function showOther(o){
		if("A00006"==o){
			$("#otherInvestmentApplyInfoTr").attr("style","display:");
			$("#transactionBothInfoTr").attr("style","display:");
			$("#mergerPlanTr").attr("style","display:");
			$("#mergerManagementModeScopeTr").attr("style","display:");
		}else{
			$("#otherInvestmentApplyInfoTr").attr("style","display:none;");
			$("#transactionBothInfoTr").attr("style","display:none;");
			$("#mergerPlanTr").attr("style","display:none;");
			$("#mergerManagementModeScopeTr").attr("style","display:none;");
		}
	}
	$().ready(function() {
		var TYPE_CODE = "";
		var TYPE_NAME = "";
		$("#industry").combotree({
			url: 'dicTypeController/selectTree.do',
			multiple: false,
			cascadeCheck: false,
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='INDUSTRY']").parent().children('input').eq(0).toggleClass('validate[required]');
				$("input[name='INDUSTRY']").parent().css("overflow","visible");
				var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
				if(EFLOW_FLOWOBJ){
					//???????????????JSON??????
					var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
					//????????????????????????
					if(eflowObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = eflowObj.busRecord.INDUSTRY;
						}
						if(TYPE_NAME==""){
							$.post( "dicTypeController/info.do",{
										typeCode:TYPE_CODE,path:"4028819d51cc6f280151cde6a3f00027"},
									function(responseText1, status, xhr) {
										var resultJson1 = $.parseJSON(responseText1);
										$("#industry").combotree("setValue",resultJson1.TYPE_CODE);
										$("#industry").combotree("setText",resultJson1.TYPE_NAME);
										isok = false;
									});
						}else{
							$("#industry").combotree("setValue",TYPE_CODE);
							$("#industry").combotree("setText",TYPE_NAME);
						}
					}
				}
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
				lerepContributionData();
			},
			onClick : function (node){
				//?????????????????
				var??tree??=??$(this).tree;
				//??????????????????????????????????????,????????????????????????,????????????
				var??isLeaf??=??tree('isLeaf',??node.target);
				if??(!isLeaf){
					//??????????????
					$("#industry").combotree('clear');
				}else{
					TYPE_CODE= $("#industry").combotree("getValue");
					TYPE_NAME= $("#industry").combotree("getText");
					//$("INDUSTRY").val($("#INDUSTRY_TEXT").combotree("getValue"));
				}
			},
			onExpand:function(node){
				$("input[name='INDUSTRY']").parent().children('input').eq(0).toggleClass('validate[required]');
				$("input[name='INDUSTRY']").parent().css("overflow","visible");
			}

		});
	});
	$().ready(function() {
		var TYPE_CODE = "";
		var TYPE_NAME = "";
		$("#industryStructure").combotree({
			url: 'dicTypeController/selectIndustryStructureTree.do',
			multiple: false,
			cascadeCheck: false,
			id: 'INDUSTRY_STRUCTURE',
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='INDUSTRY_STRUCTURE']").parent().children('input').eq(0).toggleClass('validate[required]');
				$("input[name='INDUSTRY_STRUCTURE']").parent().css("overflow","visible");
				var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
				if(EFLOW_FLOWOBJ){
					//???????????????JSON??????
					var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
					//????????????????????????
					if(eflowObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = eflowObj.busRecord.INDUSTRY_STRUCTURE;
						}
						if(TYPE_NAME==""){
							$.post( "dicTypeController/info.do",{
										typeCode:TYPE_CODE},
									function(responseText3, status, xhr) {
										var resultJson3 = $.parseJSON(responseText3);
										$("#industryStructure").combotree("setValue",resultJson3.TYPE_CODE);
										$("#industryStructure").combotree("setText",resultJson3.TYPE_NAME);
										isok = false;
									});
						}else{
							$("#industryStructure").combotree("setValue",TYPE_CODE);
							$("#industryStructure").combotree("setText",TYPE_NAME);
						}
					}
				}
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
				lerepContributionData();
			},
			onClick : function (node){
				//?????????????????
				var??tree??=??$(this).tree;
				//??????????????????????????????????????,????????????????????????,????????????
				var??isLeaf??=??tree('isLeaf',??node.target);
				if??(!isLeaf){
					//??????????????
					$("#industryStructure").combotree('clear');
				}else{
					TYPE_CODE= $("#industryStructure").combotree("getValue");
					TYPE_NAME= $("#industryStructure").combotree("getText");
					//$("INDUSTRY").val($("#INDUSTRY_TEXT").combotree("getValue"));
				}
			},
			onExpand:function(node){
				$("input[name='INDUSTRY_STRUCTURE']").parent().children('input').eq(0).toggleClass('validate[required]');
				$("input[name='INDUSTRY_STRUCTURE']").parent().css("overflow","visible");
			}

		});
	});
	$().ready(function() {
		var TYPE_CODE = "";
		var TYPE_NAME = "";
		$("#industryStructure_CD").combotree({
			url: 'dicTypeController/selectIndustryStructureTree.do',
			multiple: false,
			cascadeCheck: false,
			id: 'INDUSTRY_STRUCTURE',
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().children('input').eq(0).toggleClass('validate[required]');
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().css("overflow","visible");
				var EFLOW_FLOWOBJ =  $("#EFLOW_FLOWOBJ").val();
				if(EFLOW_FLOWOBJ){
					//???????????????JSON??????
					var eflowObj = JSON2.parse(EFLOW_FLOWOBJ);
					//????????????????????????
					if(eflowObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = eflowObj.busRecord.INDUSTRY_STRUCTURE_CD;
						}
						if(TYPE_NAME==""){
							$.post( "dicTypeController/info.do",{
										typeCode:TYPE_CODE},
									function(responseText3, status, xhr) {
										var resultJson3 = $.parseJSON(responseText3);
										$("#industryStructure_CD").combotree("setValue",resultJson3.TYPE_CODE);
										$("#industryStructure_CD").combotree("setText",resultJson3.TYPE_NAME);
										isok = false;
									});
						}else{
							$("#industryStructure_CD").combotree("setValue",TYPE_CODE);
							$("#industryStructure_CD").combotree("setText",TYPE_NAME);
						}
					}
				}
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
				lerepContributionData();
			},
			onClick : function (node){
				//?????????????????
				var??tree??=??$(this).tree;
				//??????????????????????????????????????,????????????????????????,????????????
				var??isLeaf??=??tree('isLeaf',??node.target);
				if??(!isLeaf){
					//??????????????
					$("#industryStructure_CD").combotree('clear');
				}else{
					TYPE_CODE= $("#industryStructure_CD").combotree("getValue");
					TYPE_NAME= $("#industryStructure_CD").combotree("getText");
					//$("INDUSTRY").val($("#INDUSTRY_TEXT").combotree("getValue"));
				}
			},
			onExpand:function(node){
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().children('input').eq(0).toggleClass('validate[required]');
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().css("overflow","visible");
			}

		});
	});
	function totalMoney(){
		if($("#totalMoney").val()==0){
			$("#totalMoneyExplain").removeClass("");
			$("#totalMoneyExplain").toggleClass('validate[required]');
		}else{
			$("#totalMoneyExplain").removeClass("validate[required]");
			$("#totalMoneyExplain").toggleClass('');
		}
	}
	function changeInfo(){
		var a = $("input[name='LEREP_INFO']").val();
		var lerepInfoTxt = '{ "lerepInfo" : ' + a +'}';
		var lerepInfoObj = eval ("(" + lerepInfoTxt + ")");
		lerepInfoObj.lerepInfo[0].enterprise_name = $("#enterpriseName").val();
		lerepInfoObj.lerepInfo[0].lerep_certtype = $("#lerepCertType").val();
		lerepInfoObj.lerepInfo[0].lerep_certno = $("#lerepCertNo").val();
		lerepInfoObj.lerepInfo[0].contact_name = $("#contactName").val();
		lerepInfoObj.lerepInfo[0].contact_tel = $("#contactTel").val();
		lerepInfoObj.lerepInfo[0].contact_email = $("#contactEmail").val();
		$("#lerepInfo").val(JSON2.stringify(lerepInfoObj.lerepInfo));
		//?????????
		var a_CD = $("input[name='LEREP_INFO_CD']").val();
		var lerepInfoTxt_CD = '{ "lerepInfo_CD" : ' + a_CD +'}';
		var lerepInfoObj_CD = eval ("(" + lerepInfoTxt_CD + ")");
		lerepInfoObj_CD.lerepInfo[0].enterprise_name = $("#enterpriseName").val();
		lerepInfoObj_CD.lerepInfo[0].lerep_certtype = $("#lerepCertType").val();
		lerepInfoObj_CD.lerepInfo[0].lerep_certno = $("#lerepCertNo").val();
		lerepInfoObj_CD.lerepInfo[0].contact_name = $("#contactName").val();
		lerepInfoObj_CD.lerepInfo[0].contact_tel = $("#contactTel").val();
		lerepInfoObj_CD.lerepInfo[0].contact_email = $("#contactEmail").val();
		$("#lerepInfo_CD").val(JSON2.stringify(lerepInfoObj_CD.lerepInfo));

	};

	function changeHTIndustry(value) {
		$.post("webSiteController/getHTProject.do",{industry:value},function (data) {
			var data = JSON.parse(data);
			var str = '';
			for (var i = 0; i < data.length; i++) {
				str += '<option value="'+data[i].PROJECT_NAME+'">'+data[i].PROJECT_NAME+'</option>'
			}
			$("#HTPROJECT").html(str);
			changeHTProjectDetail(data[0].PROJECT_NAME)
		})
	}

	function changeHTProjectDetail(value){
		$.post("webSiteController/getHTProjectDetail.do",{project:value},function (data) {
			var data = JSON.parse(data);
			var str = "";
			if(data.REPORT != null) {
				str += '<option value="'+data.REPORT+'">'+data.REPORT+'</option>'
			}
			if(data.REPORT_FORM != null) {
				str += '<option value="'+data.REPORT_FORM+'">'+data.REPORT_FORM+'</option>'
			}
			if(data.REGISTRAT_FORM != null) {
				str += '<option value="'+data.REGISTRAT_FORM+'">'+data.REGISTRAT_FORM+'</option>'
			}
			$("#HTPROJECT_DETAILS").html(str)
		})
	}
</script>
<style>
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
	/* .lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
	.permitIndustryStructureSelect{
		width: auto !important;
	} */
</style>
<input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
<input type="hidden" id="lerepInfo_CD" name="LEREP_INFO_CD"/>
<input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
<input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG"/>
<input type="hidden" id="theIndustry" name="THE_INDUSTRY"/>
<%--<input type="hidden" id="projectAttributes" name="PROJECT_ATTRIBUTES"/>	--%>
<%--<input type="hidden" id="industryStructure" name="INDUSTRY_STRUCTURE"/>	--%>
<div class="bsbox clearfix">
	<div class="bsboxT">
		<ul>
			<li class="on" style="background:none"><span>????????????</span></li>
		</ul>
	</div>
	<div class="bsboxC">

		<table cellpadding="0" cellspacing="0" class="bstable1" style="border: 0 dotted #a0a0a0;">
			<tr>
				<th><span class="bscolor1">*</span> ??????????????????</th>
				<td colspan="3">
					<input type="hidden"  id="project_code" name="PROJECT_CODE"  value="${projectCode}"/>
					<c:if test="${projectCode == null }">
						<input type="text" maxlength="32" class="tab_text" name="PROJECTCODE" />
						<a href="javascript:loadTZXMXXData();" class="projectBtnA">??? ???</a><font color="red">????????????????????????????????????????????????</font> <a target="_blank" href="https://fj.tzxm.gov.cn/eap/credit.reportGuide?cantcode=350128&projecttype=A00001" style="color:red;"><????????????????????????????????????></a>
					</c:if>
					<c:if test="${projectCode != null }">
						<input type="text" maxlength="32" class="tab_text" id="projectCode" name="PROJECTCODE" value="${projectCode}" readonly="true"/>
					</c:if>
				</td>
			</tr>
		</table><br/>
		<table cellpadding="0" cellspacing="0" class="bstable1"  id="tzjbxx">
			<tr>
				<th rowspan="2"><span class="bscolor1">*</span> ????????????????????????</th>
				<td>
					<input id="divisionCode" type="text" maxlength="6" class="tab_text validate[required]" readonly="true" name="DIVISION_CODE" />
				</td>

				<th rowspan="2"><span class="bscolor1">*</span> ????????????</th>
				<td >
					<input id="projectName" type="text" class="tab_text validate[required]" name="PROJECT_NAME" readonly="true"  maxlength="128"/>
				</td>
			</tr>

			<tr>
				<td>
					<input id="divisionCode_CD" type="text" maxlength="6" class="tab_text "  name="DIVISION_CODE_CD" />
					<span style="color:red;">(?????????)</span>
				</td>

				<td >
					<input id="projectName_CD" type="text" class="tab_text " name="PROJECT_NAME_CD"   maxlength="128"/>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th><span class="bscolor1">*</span> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="PROJECTTYPE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_TYPE" id="projectType">
					</eve:eveselect>
				</td>
				<th><span class="bscolor1">*</span> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="PROJECTNATURE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_NATURE" id="projectNature">
					</eve:eveselect>
				</td>
			</tr>


			<tr>
				<th> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="PROJECTTYPE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_TYPE_CD" id="projectType">
					</eve:eveselect>
					<span style="color:red;">(?????????)</span>
				</td>
				<th> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="PROJECTNATURE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_NATURE_CD" id="projectNature">
					</eve:eveselect>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th><span class="bscolor1">*</span> ????????????????????????</th>
				<td>
					<input id="enterpriseName" type="text" maxlength="128" class="tab_text validate[required]" readonly="true" name="ENTERPRISE_NAME" />
				</td>
				<th><span class="bscolor1">*</span> ????????????????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required] lerepCertTypeSelect" dataParams="LEREPCERTTYPE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????????????????" name="LEREP_CERTTYPE" id="lerepCertType">
					</eve:eveselect>
				</td>
			</tr>

			<tr>
				<th> ????????????????????????</th>
				<td>
					<input id="enterpriseName_CD" type="text" maxlength="128" class="tab_text " name="ENTERPRISE_NAME_CD" />
					<span style="color:red;">(?????????)</span>
				</td>
				<th> ????????????????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 lerepCertTypeSelect" dataParams="LEREPCERTTYPE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????????????????" name="LEREP_CERTTYPE_CD" id="lerepCertType_CD">
					</eve:eveselect>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th><span class="bscolor1">*</span> ????????????????????????</th>
				<td>
					<input id="lerepCertNo" type="text" maxlength="50" class="tab_text validate[required]" readonly="true" name="LEREP_CERTNO" />
				</td>
				<th><span class="bscolor1">*</span> ????????????</th>
				<td>
					<input id="applyDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="true" class="tab_text validate[required] Wdate" name="APPLY_DATE" />
				</td>
			</tr>

			<tr>
				<th> ????????????????????????</th>
				<td>
					<input id="lerepCertNo_CD" type="text" maxlength="50" class="tab_text "  name="LEREP_CERTNO_CD" />
					<span style="color:red;">(?????????)</span>
				</td>
				<th> ????????????</th>
				<td>
					<input id="applyDate_CD" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="tab_text Wdate" name="APPLY_DATE_CD" />
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th><span class="bscolor1">*</span> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="OPENWAY"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="OPEN_WAY" id="openWay">
					</eve:eveselect>
				</td>
				<th><span class="bscolor1">*</span> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[required]" dataParams="PROJECTATTRIBUTES"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_ATTRIBUTES" id="projectAttributes">
					</eve:eveselect>
				</td>
			</tr>

			<tr>
				<th> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 " dataParams="OPENWAY"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="OPEN_WAY_CD" id="openWay_CD">
					</eve:eveselect>
					<span style="color:red;">(?????????)</span>
				</td>
				<th> ????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280" dataParams="PROJECTATTRIBUTES"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_ATTRIBUTES_CD" id="projectAttributes_CD">
					</eve:eveselect>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>

			<tr>
				<th><span class="bscolor1">*</span> ??????????????????????????????</th>
				<td>
					<input type="text" id="industryStructure"  name="INDUSTRY_STRUCTURE"
						   class="tab_text validate[required] easyui-combotree" panelHeight="350px"/>

				</td>
				<th> ??????????????????????????????</th>
				<td>
					<input type="text" id="industryStructure_CD"  name="INDUSTRY_STRUCTURE_CD"
						   class="tab_text  easyui-combotree" panelHeight="350px"/>
					<span style="color:red;">(?????????)</span>
				</td>

			</tr>
			<tr>
				<th>????????????</th>
				<td  colspan="3">
					<textarea id="projectRemark" class="eve-textarea  w838 validate[],maxSize[2000]" rows="3" cols="200"
							  name="PROJECT_REMARK" style=" height:100px;" readonly="true"></textarea>
				</td>
			</tr>
			<tr>
				<th>????????????</th>
				<td  colspan="3">
					<textarea id="projectRemark_CD" class="eve-textarea  w838 validate[],maxSize[2000]" rows="3" cols="200"
							  name="PROJECT_REMARK_CD" style=" height:100px;"></textarea>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>
			<tr>
				<th>???????????????</th>
				<td>
					<input id="contactName" type="text" maxlength="100" class="tab_text validate[]" name="CONTACT_NAME"  readonly="true"/>
				</td>
				<th>???????????????</th>
				<td>
					<input id="contactIdCard" type="text"  maxlength="18" class="tab_text validate[],custom[vidcard]" name="CONTACT_IDCARD"  readonly="true"/>
				</td>
			</tr>
			<tr>
				<th>???????????????</th>
				<td>
					<input id="contactName_CD" type="text" maxlength="100" class="tab_text validate[]" name="CONTACT_NAME_CD"  />
					<span style="color:red;">(?????????)</span>
				</td>
				<th>???????????????</th>
				<td>
					<input id="contactIdCard_CD" type="text"  maxlength="18" class="tab_text validate[],custom[vidcard]" name="CONTACT_IDCARD_CD" />
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th>???????????????</th>
				<td>
					<input id="contactEmail" type="text" maxlength="64" class="tab_text validate[],custom[email]" name="CONTACT_EMAIL"  readonly="true"/>
				</td>
				<th>????????????</th>
				<td>
					<input id="contactTel" type="text"  maxlength="16" class="tab_text " name="CONTACT_TEL"  readonly="true"/>
				</td>
			</tr>

			<tr>
				<th>???????????????</th>
				<td>
					<input id="contactEmail_CD" type="text" maxlength="64" class="tab_text validate[],custom[email]" name="CONTACT_EMAIL_CD" />
					<span style="color:red;">(?????????)</span>
				</td>
				<th>????????????</th>
				<td>
					<input id="contactTel_CD" type="text"  maxlength="16" class="tab_text " name="CONTACT_TEL_CD"  />
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th>????????????</th>
				<td>
					<input id="contactMobile" type="text" maxlength="11" class="tab_text validate[],custom[mobilePhoneLoose]" name="CONTACT_MOBILE"  readonly="true"/>
				</td>
				<th>???????????????</th>
				<td>
					<input id="contactAddress" type="text"  maxlength="250" class="tab_text validate[]" name="CONTACT_ADDRESS"  readonly="true"/>
				</td>
			</tr>
			<tr>
				<th>????????????</th>
				<td>
					<input id="contactMobile_CD" type="text" maxlength="11" class="tab_text validate[],custom[mobilePhoneLoose]" name="CONTACT_MOBILE_CD"  />
					<span style="color:red;">(?????????)</span>
				</td>
				<th>???????????????</th>
				<td>
					<input id="contactAddress_CD" type="text"  maxlength="250" class="tab_text validate[]" name="CONTACT_ADDRESS_CD"  />
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th>???????????????</th>
				<td>
					<input id="contactPostCode" type="text" maxlength="11" class="tab_text validate[],custom[chinaZip]" name="CONTACT_POSTCODE"  readonly="true"/>
				</td>
				<th><span class="bscolor1">*</span> ????????????</th>
				<%--				<td>--%>
				<%--					<select id="placeCode" class="tab_text validate[required]" name="PLACE_CODE">--%>
				<%--						<option value="">????????????????????????</option>--%>
				<%--						<option value="350000">?????????</option>--%>
				<%--						<option value="350400">?????????</option>--%>
				<%--						<option value="350100">?????????</option>--%>
				<%--						<option value="350200">?????????</option>--%>
				<%--						<option value="350300">?????????</option>--%>
				<%--						<option value="350500">?????????</option>--%>
				<%--						<option value="350600">?????????</option>--%>
				<%--						<option value="350700">?????????</option>--%>
				<%--						<option value="350800">?????????</option>--%>
				<%--					</select>--%>
				<%--				</td>--%>
				<%--<td>
				<input  id="placeCode" type="text" maxlength="250" class="tab_text validate[required]" name="PLACE_CODE"  readonly="true"/>
				</td>
			--%>
				<td>
					<input type="text" style="width:190px;"  id="placeCode"  name="PLACE_CODE"
						   class="tab_text validate[required] easyui-combotree"  panelHeight="20px"/>
				</td>
			</tr>
			<tr>
				<th>???????????????</th>
				<td>
					<input id="contactPostCode_CD" type="text" maxlength="11" class="tab_text validate[],custom[chinaZip]" name="CONTACT_POSTCODE_CD"  />
					<span style="color:red;">(?????????)</span>
				</td>
				<th><span class="bscolor1">*</span> ????????????</th>
				<%--				<td>--%>
				<%--					<select id="placeCode" class="tab_text validate[required]" name="PLACE_CODE">--%>
				<%--						<option value="">????????????????????????</option>--%>
				<%--						<option value="350000">?????????</option>--%>
				<%--						<option value="350400">?????????</option>--%>
				<%--						<option value="350100">?????????</option>--%>
				<%--						<option value="350200">?????????</option>--%>
				<%--						<option value="350300">?????????</option>--%>
				<%--						<option value="350500">?????????</option>--%>
				<%--						<option value="350600">?????????</option>--%>
				<%--						<option value="350700">?????????</option>--%>
				<%--						<option value="350800">?????????</option>--%>
				<%--					</select>--%>
				<%--				</td>--%>
				<%--<td>
				<input  id="placeCode" type="text" maxlength="250" class="tab_text validate[required]" name="PLACE_CODE"  readonly="true"/>
				</td>
			--%>
				<td>
					<input type="text" id="placeCode_CD"  name="PLACE_CODE_CD"
						   class="tab_text  easyui-combotree"  panelHeight="20px"/>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>

			<tr>
				<th><span class="bscolor1">*</span> ??????????????????</th>
				<td  colspan="3">
					<textarea id="placeCodeDetail" class="eve-textarea w838 validate[required],maxSize[512]" rows="3" cols="200"
							  name="PLACE_CODE_DETAIL" style= height:100px;"  readonly="true"></textarea>
				</td>
			</tr>

			<tr>
				<th>??????????????????</th>
				<td  colspan="3">
					<textarea id="placeCodeDetail_CD" class="eve-textarea w838 maxSize[512]" rows="3" cols="200"
							  name="PLACE_CODE_DETAIL_CD" style=" height:100px;"  ></textarea>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th>?????????????????????</th>
				<td>
					<select id="isDeArea" onChange="onSelectClass(this.value)"  readonly="true" class="tab_text validate[]" name="IS_DE_AREA">
						<option value="">??????????????????????????????</option>
						<option value="0">???</option>
						<option value="1">???</option>
					</select>
				</td>
				<th>???????????????</th>
				<td>
					<input id="deAreaName" type="text"  maxlength="128" class="tab_text validate[]" name="DE_AREA_NAME"  readonly="true"/>
				</td>
			</tr>
			<tr>
				<th>?????????????????????</th>
				<td>
					<select id="isDeArea_CD" onChange="onSelectClass(this.value)"  class="tab_text validate[]" name="IS_DE_AREA_CD">
						<option value="">??????????????????????????????</option>
						<option value="0">???</option>
						<option value="1">???</option>
					</select>
					<span style="color:red;">(?????????)</span>
				</td>
				<th>???????????????</th>
				<td>
					<input id="deAreaName_CD" type="text"  maxlength="128" class="tab_text validate[]" name="DE_AREA_NAME_CD"  />
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>

			<tr>
				<th><span class="bscolor1">*</span> ????????????</th>
				<td>
					<input type="text" id="industry"  name="INDUSTRY"
						   class="tab_text validate[required] easyui-combotree" panelHeight="350px"/>
				</td>
				<th>????????????????????????</th>
				<td>
					<eve:eveselect clazz="tab_text validate[]  w280 permitIndustrySelect" dataParams="PERMITINDUSTRY"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????????????????" name="PERMIT_INDUSTRY" id="permitIndustry">
					</eve:eveselect>
				</td>
			</tr>

			<tr>
				<th> ????????????</th>
				<td>
					<input type="text" id="industry_CD"  name="INDUSTRY_CD"
						   class="tab_text  easyui-combotree" panelHeight="350px"/>
					<span style="color:red;">(?????????)</span>
				</td>
				<th>????????????????????????</th>
				<td>
					<eve:eveselect clazz="tab_text w280 validate[] permitIndustrySelect" dataParams="PERMITINDUSTRY"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????????????????" name="PERMIT_INDUSTRY_CD" id="permitIndustry_CD">
					</eve:eveselect>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>

			<tr>
				<th>????????????</th>
				<td>

					<eve:eveselect clazz="tab_text w280 validate[]" dataParams="PROJECTSTAGE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_STAGE" id="projectStage">
					</eve:eveselect>
				</td>
				<th><span class="bscolor1">*</span> ?????????????????????</th>
				<td>
					<input id="totalMoney" type="text"  maxlength="16" class="tab_text validate[required]" name="TOTAL_MONEY"  readonly="true"/>
				</td>
			</tr>

			<tr>
				<th>????????????</th>
				<td>
					<eve:eveselect clazz="tab_text validate[] w280" dataParams="PROJECTSTAGE"
								   dataInterface="dictionaryService.findDatasForSelect"
								   defaultEmptyText="?????????????????????" name="PROJECT_STAGE_CD" id="projectStage_CD">
					</eve:eveselect>
					<span style="color:red;">(?????????)</span>
				</td>
				<th><span class="bscolor1">*</span> ?????????????????????</th>
				<td>
					<input id="totalMoney_CD" type="text"  maxlength="16" class="tab_text " name="TOTAL_MONEY_CD"  />
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>



			<tr>
				<td class="tab_width"><font class="tab_color">*</font> ??????????????????0???????????????</td>
				<td  colspan="3">
					<textarea id="totalMoneyExplain" class="tab_text  w838" rows="3" cols="200"
							  name="TOTAL_MONEY_EXPLAIN" style=" height:150px;"  ></textarea>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> ??????????????????0???????????????</td>
				<td  colspan="3">
					<textarea id="totalMoneyExplain_CD" class="tab_text w838" rows="3" cols="200"
							  name="TOTAL_MONEY_EXPLAIN_CD" style=" height:150px;"  ></textarea>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th><span class="bscolor1">*</span> ?????????????????????</th>
				<td  colspan="3">
					<textarea id="scaleContent" class="eve-textarea validate[required],maxSize[2000] w838" rows="3" cols="200"
							  name="SCALE_CONTENT" style=" height:150px;"  readonly="true"></textarea>
				</td>
			</tr>
			<tr>
				<th> ?????????????????????</th>
				<td  colspan="3">
					<textarea id="scaleContent_CD" class="eve-textarea maxSize[2000] w838" rows="3" cols="200"
							  name="SCALE_CONTENT_CD" style=" height:150px;" ></textarea>
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>


			<tr>
				<th><span class="bscolor1">*</span> ???????????????</th>
				<td>
					<input id="startYear" type="text" maxlength="4" class="tab_text validate[required] Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})" readonly="true" name="START_YEAR" />
				</td>
				<th><span class="bscolor1">*</span> ???????????????</th>
				<td>
					<input id="endYear" type="text"  maxlength="4" class="tab_text validate[required] Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})" readonly="true" name="END_YEAR" />
				</td>
			</tr>

			<tr>
				<th>???????????????</th>
				<td>
					<input id="startYear_CD" type="text" maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR_CD" />
					<span style="color:red;">(?????????)</span>
				</td>
				<th>???????????????</th>
				<td>
					<input id="endYear_CD" type="text"  maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR_CD" />
					<span style="color:red;">(?????????)</span>
				</td>
			</tr>

		</table>
	</div>
</div>
<div id="lerepInfoTable"></div>
<div id="lerepInfoTable_CD"></div>
<div id="dnyTable"></div>
<div id="dnyTable_CD"></div>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->