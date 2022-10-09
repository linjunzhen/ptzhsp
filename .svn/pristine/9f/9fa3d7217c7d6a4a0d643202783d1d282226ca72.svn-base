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
<link rel="stylesheet" type="text/css" href="<%=path%>/plug-in/eveflowdesign-1.0/css/design.css" />
<script type="text/javascript" src="<%=path %>/webpage/bsdt/applyform/js/solely1.js"></script>
<eve:resources
		loadres="easyui"></eve:resources>

<script type="text/javascript">
	function loadTZXMXXData(){
		var code = $("input[name='PROJECTCODE']").val();
		if(null==code||''==code){
			art.dialog({
				content: "请填写投资项目编号",
				icon:"error",
				ok: true
			});
		}else{
			var layload = layer.load('正在提交校验中…');
			$.post( "webSiteController/loadTZXMXXData.do",{
						projectCode:code},
					function(responseText, status, xhr) {
						layer.close(layload);
						var resultJson = $.parseJSON(responseText);
						if (resultJson.result) {
							for(var key in resultJson.tzProject){
								if(key=='industry'){
									var typeCode1 = resultJson.tzProject[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode1,path:"4028819d51cc6f280151cde6a3f00027"},
											function(responseText1, status, xhr) {
												var resultJson1 = $.parseJSON(responseText1);
												if(null!=resultJson1){
													$("#industry").combotree("setValue",resultJson1.TYPE_CODE);
													$("#industry").combotree("setText",resultJson1.TYPE_NAME);
												}
											});
								}else if(key=='industry_CD'){
									var typeCode1 = resultJson.tzProject[key];
									if(typeCode1!=""){
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode1,path:"4028819d51cc6f280151cde6a3f00027"},
											function(responseText1, status, xhr) {
												var resultJson1 = $.parseJSON(responseText1);
												if(null!=resultJson1){
													$("#industry_CD").combotree("setValue",resultJson1.TYPE_CODE);
													$("#industry_CD").combotree("setText",resultJson1.TYPE_NAME);
												}
											});
									}
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
									var typeCode3 = resultJson.tzProject[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode3},
											function(responseText3, status, xhr) {
												var resultJson3 = $.parseJSON(responseText3);
												if(null!=resultJson3){
													$("#industryStructure").combotree("setValue",resultJson3.TYPE_CODE);
													$("#industryStructure").combotree("setText",resultJson3.TYPE_NAME);
												}
											});
								}else if(key=='placeCode_CD'){
									var typeCode2 = resultJson.tzProject[key];
									if (typeCode2 != "") {
										$.post("dicTypeController/info.do", {typeCode: typeCode2},
												function (responseText2, status, xhr) {
													var resultJson2 = $.parseJSON(responseText2);
													if (null != resultJson2) {
														$("#placeCode_CD").combotree("setValue", resultJson2.TYPE_CODE);
														$("#placeCode_CD").combotree("setText", resultJson2.TYPE_NAME);
													}
												});
									}
								}else if(key=='industryStructure_CD'){
									var typeCode3 = resultJson.tzProject[key];
									$.post( "dicTypeController/info.do",{
												typeCode:typeCode3},
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
							// $("#tzjbxx input").attr("readonly",false);
							// $("#tzjbxx textarea").attr("readonly",false);
							// $("#tzjbxx select").attr("readonly",false);
						} else {
							art.dialog({
								content: "校验失败",
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
	function lerepContributionData(){
		var a = $("input[name='LEREP_INFO']").val();
		var b = $("input[name='CONTRIBUTION_INFO']").val();
		if(a==null||a==""){

		}else{
			var lerepInfoTxt = '{ "lerepInfo" : ' + a +'}';
			var lerepInfoObj = eval ("(" + lerepInfoTxt + ")");
			var contributionInfoTxt = '{ "contributionInfo" : ' + b +'}';
			var contributionInfoObj = eval ("(" + contributionInfoTxt + ")");
			$("#enterpriseName").val(lerepInfoObj.lerepInfo[0].enterprise_name);
			$("#lerepCertType").val(lerepInfoObj.lerepInfo[0].lerep_certtype);
			$("#lerepCertNo").val(lerepInfoObj.lerepInfo[0].lerep_certno);
			$("#contactName").val(lerepInfoObj.lerepInfo[0].contact_name);
			$("#contactTel").val(lerepInfoObj.lerepInfo[0].contact_tel);
			$("#contactEmail").val(lerepInfoObj.lerepInfo[0].contact_email);

			if(lerepInfoObj.lerepInfo.length>0){
				var lerepInfoTableStr="<table cellpadding='0' cellspacing='1' class='tab_tk_pro'  id='tzczqkxx'>"
				lerepInfoTableStr+="<tr>"
				lerepInfoTableStr+="<th colspan='14'>项目（法人）单位信息</th>"
				lerepInfoTableStr+="</tr>"
				lerepInfoTableStr+="<tr>"
				lerepInfoTableStr+="<td class='tab_width1' width='80px'>单位名称</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='100px'>证照类型</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>证照号码</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>联系人名称</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>联系电话</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>联系人邮箱</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>注册地址</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>性质</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>持股比例是否与资本金相同</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>主要经营范围</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>联系手机</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>传真</td>"
				lerepInfoTableStr+="<td class='tab_width1' width='200px'>通讯地址</td>"
				lerepInfoTableStr+="</tr>"
				for(var i = 0; i<lerepInfoObj.lerepInfo.length;i++){
					var certtype;
					if(null==lerepInfoObj.lerepInfo[i].lerep_certtype||
							''==lerepInfoObj.lerepInfo[i].lerep_certtype){
						certtype = lerepInfoObj.lerepInfo[i].lerep_certtype;
					}else{
						certtype = codeMap[lerepInfoObj.lerepInfo[i].lerep_certtype]
					}
					var nature;
					if(null==lerepInfoObj.lerepInfo[i].enterprise_nature||
							''==lerepInfoObj.lerepInfo[i].enterprise_nature){
						nature = lerepInfoObj.lerepInfo[i].enterprise_nature
					}else{
						nature = enterpriseNatureMap[lerepInfoObj.lerepInfo[i].enterprise_nature]
					}
					var ratio;
					if(null==lerepInfoObj.lerepInfo[i].china_foreign_share_ratio||
							''==lerepInfoObj.lerepInfo[i].china_foreign_share_ratio){
						ratio = lerepInfoObj.lerepInfo[i].china_foreign_share_ratio
					}else{
						ratio = chinaForeignShareRatioMap[lerepInfoObj.lerepInfo[i].china_foreign_share_ratio]
					}
					lerepInfoTableStr+="<tr>"
					lerepInfoTableStr+="<td class='tab_width1' width='80px'>"+ lerepInfoObj.lerepInfo[i].enterprise_name +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='100px'>"+ certtype +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].lerep_certno +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_name +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_tel +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_email +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].enterprise_place +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ nature +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ ratio +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].business_scope +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_phone +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_fax +"</td>"
					lerepInfoTableStr+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].correspondence_address +"</td>"
					lerepInfoTableStr+="</tr>"
				}
				lerepInfoTableStr+="</table>";

				var lerepInfoTableStr_CD="<table cellpadding='0' cellspacing='1' class='tab_tk_pro'  id='tzczqkxx'>"
				lerepInfoTableStr_CD+="<tr>"
				lerepInfoTableStr_CD+="<th colspan='14' style='color:red;'>项目（法人）单位信息变更后</th>"
				lerepInfoTableStr_CD+="</tr>"
				lerepInfoTableStr_CD+="<tr>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='80px'>单位名称</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='100px'>证照类型</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>证照号码</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>联系人名称</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>联系电话</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>联系人邮箱</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>注册地址</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>性质</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>持股比例是否与资本金相同</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>主要经营范围</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>联系手机</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>传真</td>"
				lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>通讯地址</td>"
				lerepInfoTableStr_CD+="</tr>"
				for(var i = 0; i<lerepInfoObj.lerepInfo.length;i++){
					var certtype;
					if(null==lerepInfoObj.lerepInfo[i].lerep_certtype_CD||
							''==lerepInfoObj.lerepInfo[i].lerep_certtype_CD){
						certtype = lerepInfoObj.lerepInfo[i].lerep_certtype_CD
					}else{
						certtype = codeMap[lerepInfoObj.lerepInfo[i].lerep_certtype_CD]
					}
					var nature;
					if(null==lerepInfoObj.lerepInfo[i].enterprise_nature_CD||
							''==lerepInfoObj.lerepInfo[i].enterprise_nature_CD){
						nature = lerepInfoObj.lerepInfo[i].enterprise_nature_CD
					}else{
						nature = enterpriseNatureMap[lerepInfoObj.lerepInfo[i].enterprise_nature_CD]
					}
					var ratio;
					if(null==lerepInfoObj.lerepInfo[i].china_foreign_share_ratio_CD||
							''==lerepInfoObj.lerepInfo[i].china_foreign_share_ratio_CD){
						ratio = lerepInfoObj.lerepInfo[i].china_foreign_share_ratio_CD
					}else{
						ratio = chinaForeignShareRatioMap[lerepInfoObj.lerepInfo[i].china_foreign_share_ratio_CD]
					}
					lerepInfoTableStr_CD+="<tr>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='80px'>"+ lerepInfoObj.lerepInfo[i].enterprise_name_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='100px'>"+ certtype +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].lerep_certno_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_name_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_tel_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_email_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].enterprise_place_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ nature +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ ratio +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].business_scope_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_phone_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].contact_fax_CD +"</td>"
					lerepInfoTableStr_CD+="<td class='tab_width1' width='200px'>"+ lerepInfoObj.lerepInfo[i].correspondence_address_CD +"</td>"
					lerepInfoTableStr_CD+="</tr>"
				}
				lerepInfoTableStr_CD+="</table>";

				var lerepInfoTable=lerepInfoTableStr+lerepInfoTableStr_CD;

				lerepInfoTable=lerepInfoTable.replace(new RegExp('undefined', 'g'),"");
				$("#lerepInfoTable").html(lerepInfoTable);
			}
			if(contributionInfoObj.contributionInfo.length>0&&
					(null != contributionInfoObj.contributionInfo[0].investment_id && "" != contributionInfoObj.contributionInfo[0].investment_id)){
				var
						dnyTableStr="<table cellpadding='0' cellspacing='1' class='tab_tk_pro'  id='tzczqkxx'>"
				dnyTableStr+="<tr>"
				dnyTableStr+="<th colspan='9'>出资情况信息</th>"
				dnyTableStr+="</tr>"
				dnyTableStr+="<tr>"
				dnyTableStr+="<td class='tab_width1' width='80px'>类型</td>"
				dnyTableStr+="<td class='tab_width1' width='100px'>项目代码</td>"
				dnyTableStr+="<td class='tab_width1' width='200px'>投资者名称</td>"
				dnyTableStr+="<td class='tab_width1' width='200px'>注册国别地区</td>"
				dnyTableStr+="<td class='tab_width1' width='200px'>出资额(万元)</td>"
				dnyTableStr+="<td class='tab_width1' width='200px'>出资比例%</td>"
				dnyTableStr+="<td class='tab_width1' width='200px'>出资方式</td>"
				dnyTableStr+="</tr>"
				for(var i = 0; i<contributionInfoObj.contributionInfo.length;i++){
					var type;
					if(null==contributionInfoObj.contributionInfo[i].business_type||
							''==contributionInfoObj.contributionInfo[i].business_type){
						type = contributionInfoObj.contributionInfo[i].business_type
					}else{
						type = businessTypeMap[contributionInfoObj.contributionInfo[i].business_type]
					}
					var country;
					if(null==contributionInfoObj.contributionInfo[i].reg_country||
							''==contributionInfoObj.contributionInfo[i].reg_country){
						country = contributionInfoObj.contributionInfo[i].reg_country
					}else{
						country = countryMap[contributionInfoObj.contributionInfo[i].reg_country]
					}
					var mode;
					if(null==contributionInfoObj.contributionInfo[i].contribution_mode||
							''==contributionInfoObj.contributionInfo[i].contribution_mode){
						mode = contributionInfoObj.contributionInfo[i].contribution_mode
					}else{
						mode = contributionModeMap[contributionInfoObj.contributionInfo[i].contribution_mode]
					}
					dnyTableStr+="<tr>"
					dnyTableStr+="<td class='tab_width1' width='80px'>"+ type +"</td>"
					dnyTableStr+="<td class='tab_width1' width='100px'>"+ contributionInfoObj.contributionInfo[i].project_code +"</td>"
					dnyTableStr+="<td class='tab_width1' width='200px'>"+ contributionInfoObj.contributionInfo[i].investment_name +"</td>"
					dnyTableStr+="<td class='tab_width1' width='200px'>"+ country +"</td>"
					dnyTableStr+="<td class='tab_width1' width='200px'>"+ contributionInfoObj.contributionInfo[i].contribution_limit +"</td>"
					dnyTableStr+="<td class='tab_width1' width='200px'>"+ contributionInfoObj.contributionInfo[i].contribution_ratio +"</td>"
					dnyTableStr+="<td class='tab_width1' width='200px'>"+ mode +"</td>"
					dnyTableStr+="</tr>"
				}
				dnyTableStr+="</table>"
				$("#dnyTable").html(dnyTableStr);
			}
		}
	}
	function onSelectClass(o){
		if(o==1){
			$("#deAreaName").removeClass("");
			$("#deAreaName").toggleClass('validate[required]');
		}else{
			$("#deAreaName").removeClass("validate[required]");
			$("#deAreaName").toggleClass('');
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
				var flowSubmitObj = FlowUtil.getFlowObj();
				if(flowSubmitObj){
					if(flowSubmitObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = flowSubmitObj.busRecord.INDUSTRY;
						}
						if(TYPE_NAME==""){
							$.post( "dicTypeController/info.do",{
										typeCode:TYPE_CODE,path:"4028819d51cc6f280151cde6a3f00027"},
									function(responseText1, status, xhr) {
										var resultJson1 = $.parseJSON(responseText1);
										if(null!=resultJson1){
											TYPE_CODE = resultJson1.TYPE_CODE;
											TYPE_NAME = resultJson1.TYPE_NAME;
											$("#industry").combotree("setValue",TYPE_CODE);
											$("#industry").combotree("setText",TYPE_NAME);
										}
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
				// 返回树对象
				var tree = $(this).tree;
				// 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf){
					// 清除选中
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
		$("#placeCode_CD").combotree({
			url: 'dicTypeController/placeTree.do',
			multiple: false,
			cascadeCheck: false,
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='PLACE_CODE_CD']").parent().children('input').eq(0).toggleClass('validate[]');
				$("input[name='PLACE_CODE_CD']").parent().css("overflow","visible");
				var flowSubmitObj = FlowUtil.getFlowObj();
				if(flowSubmitObj){
					if(flowSubmitObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = flowSubmitObj.busRecord.PLACE_CODE_CD;
						}
						if(TYPE_NAME==""&&typeof (TYPE_CODE)!="undefined"){
							$.post("dicTypeController/info.do", {typeCode: TYPE_CODE},
									function (responseText2, status, xhr) {
										var resultJson2 = $.parseJSON(responseText2);
										if (null != resultJson2) {
											$("#placeCode_CD").combotree("setValue", resultJson2.TYPE_CODE);
											$("#placeCode_CD").combotree("setText", resultJson2.TYPE_NAME);
										}
									});
						}else{
							$("#placeCode_CD").combotree("setValue",TYPE_CODE);
							$("#placeCode_CD").combotree("setText",TYPE_NAME);
						}
					}
				}

			},
			onClick: function (node) {
				TYPE_CODE2 = $("#placeCode_CD").combotree("getValue");
				TYPE_NAME2 = $("#placeCode_CD").combotree("getText");
			},
			onExpand: function (node) {
				$("input[name='PLACE_CODE_CD']").parent().children('input').eq(0).toggleClass('');
				$("input[name='PLACE_CODE_CD']").parent().css("overflow", "visible");
			}
		});
	});

	$().ready(function() {
		var TYPE_CODE = "";
		var TYPE_NAME = "";
		var flowSubmitObjexe = FlowUtil.getFlowObj();
		$("#industryStructure").combotree({
			url: 'dicTypeController/selectIndustryStructureTree.do',
			multiple: false,
			cascadeCheck: false,
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='INDUSTRY_STRUCTURE']").parent().children('input').eq(0).toggleClass('validate[]');
				$("input[name='INDUSTRY_STRUCTURE']").parent().css("overflow","visible");
				var flowSubmitObj = FlowUtil.getFlowObj();
				if(flowSubmitObj){
					//初始化表单字段值
					if(flowSubmitObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = flowSubmitObj.busRecord.INDUSTRY_STRUCTURE;
						}
						if(TYPE_NAME==""){
							$.post( "dicTypeController/info.do",{
										typeCode:TYPE_CODE},
									function(responseText3, status, xhr) {
										var resultJson3 = $.parseJSON(responseText3);
										if(null!=resultJson3){
											TYPE_CODE = resultJson3.TYPE_CODE;
											TYPE_NAME = resultJson3.TYPE_NAME;
											$("#industryStructure").combotree("setValue",TYPE_CODE);
											$("#industryStructure").combotree("setText",TYPE_NAME);
										}
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
					$("#totalMoneyExplain").toggleClass('validate[]');
				}else{
					$("#totalMoneyExplain").removeClass("validate[]");
					$("#totalMoneyExplain").toggleClass('');
				}
				lerepContributionData();
			},
			onClick : function (node){
				// 返回树对象
				var tree = $(this).tree;
				// 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf){
					// 清除选中
					$("#industryStructure").combotree('clear');
				}else{
					TYPE_CODE= $("#industryStructure").combotree("getValue");
					TYPE_NAME= $("#industryStructure").combotree("getText");
				}
			},
			onExpand:function(node){
				$("input[name='INDUSTRY_STRUCTURE']").parent().children('input').eq(0).toggleClass('validate[required]');
				$("input[name='INDUSTRY_STRUCTURE']").parent().css("overflow","visible");
			}
		});
	});

     //变更后
	$().ready(function() {
		var TYPE_CODE = "";
		var TYPE_NAME = "";
		$("#industry_CD").combotree({
			url: 'dicTypeController/selectTree.do',
			multiple: false,
			cascadeCheck: false,
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='INDUSTRY_CD']").parent().children('input').eq(0).toggleClass('validate[]');
				$("input[name='INDUSTRY_CD']").parent().css("overflow","visible");
				var flowSubmitObj = FlowUtil.getFlowObj();
				if(flowSubmitObj){
					if(flowSubmitObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = flowSubmitObj.busRecord.INDUSTRY_CD;
						}
						if(TYPE_NAME==""&&typeof (TYPE_CODE)!="undefined"){
							$.post( "dicTypeController/info.do",{
										typeCode:TYPE_CODE,path:"4028819d51cc6f280151cde6a3f00027"},
									function(responseText1, status, xhr) {
										var resultJson1 = $.parseJSON(responseText1);
										if(null!=resultJson1){
											TYPE_CODE = resultJson1.TYPE_CODE;
											TYPE_NAME = resultJson1.TYPE_NAME;
											$("#industry_CD").combotree("setValue",TYPE_CODE);
											$("#industry_CD").combotree("setText",TYPE_NAME);
										}
									});
						}else{
							$("#industry_CD").combotree("setValue",TYPE_CODE);
							$("#industry_CD").combotree("setText",TYPE_NAME);
						}
					}
				}
			},
			onClick : function (node){
				// 返回树对象
				var tree = $(this).tree;
				// 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf){
					// 清除选中
					$("#industry").combotree('clear');
				}else{
					TYPE_CODE= $("#industry_CD").combotree("getValue");
					TYPE_NAME= $("#industry_CD").combotree("getText");
					//$("INDUSTRY").val($("#INDUSTRY_TEXT").combotree("getValue"));
				}
			},
			onExpand:function(node){
				$("input[name='INDUSTRY_CD']").parent().children('input').eq(0).toggleClass('validate[]');
				$("input[name='INDUSTRY_CD']").parent().css("overflow","visible");
			}
		});
	});
	$().ready(function() {
		var TYPE_CODE = "";
		var TYPE_NAME = "";
		var flowSubmitObjexe = FlowUtil.getFlowObj();
		$("#industryStructure_CD").combotree({
			url: 'dicTypeController/selectIndustryStructureTree.do',
			multiple: false,
			cascadeCheck: false,
			onlyLeafCheck: true,
			onLoadSuccess: function () {
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().children('input').eq(0).toggleClass('validate[]');
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().css("overflow","visible");
				var flowSubmitObj = FlowUtil.getFlowObj();
				if(flowSubmitObj){
					//初始化表单字段值
					if(flowSubmitObj.busRecord){
						if(TYPE_CODE==""){
							TYPE_CODE = flowSubmitObj.busRecord.INDUSTRY_STRUCTURE_CD;
						}
						if(TYPE_NAME==""){
							$.post( "dicTypeController/info.do",{
										typeCode:TYPE_CODE},
									function(responseText3, status, xhr) {
										var resultJson3 = $.parseJSON(responseText3);
										if(null!=resultJson3){
											TYPE_CODE = resultJson3.TYPE_CODE;
											TYPE_NAME = resultJson3.TYPE_NAME;
											$("#industryStructure_CD").combotree("setValue",TYPE_CODE);
											$("#industryStructure_CD").combotree("setText",TYPE_NAME);
										}
									});
						}else{
							$("#industryStructure_CD").combotree("setValue",TYPE_CODE);
							$("#industryStructure_CD").combotree("setText",TYPE_NAME);
						}
					}
				}
			},
			onClick : function (node){
				// 返回树对象
				var tree = $(this).tree;
				// 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf){
					// 清除选中
					$("#industryStructure_CD").combotree('clear');
				}else{
					TYPE_CODE= $("#industryStructure_CD").combotree("getValue");
					TYPE_NAME= $("#industryStructure_CD").combotree("getText");
				}
			},
			onExpand:function(node){
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().children('input').eq(0).toggleClass('validate]');
				$("input[name='INDUSTRY_STRUCTURE_CD']").parent().css("overflow","visible");
			}
		});
	});
	function totalMoney(){
		if($("#totalMoney").val()==0){
			$("#totalMoneyExplain").removeClass("");
			$("#totalMoneyExplain").toggleClass('validate[]');
		}else{
			$("#totalMoneyExplain").removeClass("validate[]");
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
	};
	$(function(){
		$("#tzjbxx input").attr("readonly", "readonly");
		$("#tzjbxx textarea").attr("readonly", "readonly");
		$("#tzjbxx select").attr("readonly", "readonly");
		$("#tzjbxx textarea").attr("readonly", "readonly");
	});
</script>
<style>
	.lerepCertTypeSelect{
		width: 210px !important;
	}
	.permitIndustrySelect{
		width: auto !important;
	}
</style>
<input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
<input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
<input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG"/>
<input type="hidden" id="theIndustry" name="THE_INDUSTRY"/>
<%--<input type="hidden" id="projectAttributes" name="PROJECT_ATTRIBUTES"/>	--%>
<%--<input type="hidden" id="industryStructure" name="INDUSTRY_STRUCTURE"/>	--%>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro"  id="tzjbxx">
	<tr>
		<th colspan="4">基本信息</th>
	</tr>
	<tr id="projectCodeTr">
		<td class="tab_width"><font class="tab_color">*</font> 投资项目编号：</td>
		<td colspan="1">
			<input type="text" maxlength="32" class="tab_text" name="PROJECTCODE" />
		</td>
		<td class="tab_width"> 申报号：</td>
		<td id = "EXEID"></td>
	</tr>
	<tr>
		<td rowspan="2" class="tab_width"> 项目名称：</td>
		<td colspan="3">
			<input style="width: 750px;" readonly="true" id="projectName" type="text" class="tab_text validate[required]" name="PROJECT_NAME"   maxlength="128"/>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<input style="width: 750px;" readonly="true" id="projectName_CD" type="text" class="tab_text validate[]" name="PROJECT_NAME_CD"   maxlength="128"/>
			<font class="tab_color">变更后</font>
		</td>
	</tr>
	<tr>
		<td  rowspan="2" class="tab_width"><font class="tab_color">*</font> 项目类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="PROJECTTYPE"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
			</eve:eveselect>
		</td>
		<td rowspan="2" class="tab_width"><font class="tab_color">*</font> 项目所属行政区划：</td>
		<td>
			<input readonly="true" id="divisionCode" type="text" maxlength="6" class="tab_text validate[required]"  name="DIVISION_CODE" />
		</td>
	</tr>

	<tr>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="PROJECTTYPE"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择项目类型" name="PROJECT_TYPE_CD" id="projectType_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<input readonly="true" id="divisionCode_CD" type="text" maxlength="6" class="tab_text validate[]"  name="DIVISION_CODE_CD" />
			<font class="tab_color">变更后</font>
		</td>
	</tr>
	<tr>
		<td rowspan="2" class="tab_width"><font class="tab_color">*</font> 投资项目目录编码：</td>
		<td>
			<input readonly="true"  type="text"  class="tab_text "  name="PERMIT_ITEM_CODE" />
		</td>
		<td  rowspan="2" class="tab_width"><font class="tab_color">*</font> 建设性质：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="PROJECTNATURE"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="projectNature">
			</eve:eveselect>
		</td>

	</tr>

	<tr>
		<td>
			<input readonly="true"  type="text"  class="tab_text "  name="PERMIT_ITEM_CODE_CD" />
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="PROJECTNATURE"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择建设性质" name="PROJECT_NATURE_CD" id="projectNature_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
	</tr>
	<tr>
		<td rowspan="2" class="tab_width"> 建设地点详情：</td>
		<td colspan="3">
			<input style="width: 700px;" readonly="true" id="placeCodeDetail" type="text" class="tab_text validate[required]" name="PLACE_CODE_DETAIL"   maxlength="128"/>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<input style="width: 700px;" readonly="true" id="placeCodeDetail_CD" type="text" class="tab_text validate[]" name="PLACE_CODE_DETAIL_CD"   maxlength="128"/>
			<font class="tab_color">变更后</font>
		</td>
	</tr>
	<tr>
		<td rowspan="2" class="tab_width"><font class="tab_color">*</font> 拟开工时间：</td>
		<td>
			<input readonly="true" id="startYear" type="text" maxlength="4" class="tab_text validate[required] Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR" />
		</td>
		<td rowspan="2" class="tab_width"><font class="tab_color">*</font> 拟建成时间：</td>
		<td >
			<input readonly="true" id="endYear" type="text"  maxlength="4" class="tab_text validate[required] Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR" />
		</td>
	</tr>
	<tr>
		<td>
			<input readonly="true" id="startYear_CD" type="text" maxlength="4" class="tab_text validate[] Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR_CD" />
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<input readonly="true" id="endYear_CD" type="text"  maxlength="4" class="tab_text validate[] Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR_CD" />
			<font class="tab_color">变更后</font>
		</td>
	</tr>

	<tr>
		<td rowspan="2" class="tab_width"><font class="tab_color">*</font> 申报日期：</td>
		<td>
			<input readonly="true" id="applyDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false})"  class="tab_text validate[required] Wdate" name="APPLY_DATE" />
		</td>
		<td rowspan="2" class="tab_width"><font class="tab_color">*</font> 总投资（万元）：</td>
		<td>
			<input  readonly="true" id="totalMoney" type="text"  maxlength="16" class="tab_text validate[]" name="TOTAL_MONEY"  />
		</td>
	</tr>

	<tr>
		<td>
			<input readonly="true" id="applyDate_CD" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="tab_text validate[] Wdate" name="APPLY_DATE_CD" />
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<input  readonly="true" id="totalMoney_CD" type="text"  maxlength="16" class="tab_text validate[]" name="TOTAL_MONEY_CD"  />
			<font class="tab_color">变更后</font>
		</td>
	</tr>

	<tr>
		<td rowspan="2" class="tab_width"><font class="tab_color">*</font> 总投资额为“0”时说明：</td>
		<td  colspan="3">
			<textarea id="totalMoneyExplain" class="tab_text " rows="3" cols="200"
					  name="TOTAL_MONEY_EXPLAIN" style="width:860px;height:150px;"  ></textarea>
		</td>
	</tr>

	<tr>
		<td  colspan="3">
			<textarea id="totalMoneyExplain_CD" class="tab_text " rows="3" cols="200"
					  name="TOTAL_MONEY_EXPLAIN_CD" style="width:860px;height:150px;"  ></textarea>
			<font class="tab_color">变更后</font>
		</td>
	</tr>

	<tr>
		<td  rowspan="2" class="tab_width"><font class="tab_color">*</font> 建设地点：</td>
		<td>
			<input type="text" style="width:190px;"  id="placeCode"  name="PLACE_CODE"
				   class="tab_text validate[required] easyui-combotree"  panelHeight="20px"/>
		</td>
		<td rowspan="2"  class="tab_width"><font class="tab_color">*</font> 国标行业：</td>
		<td>
			<input type="text" style="width:250px;"  id="industry"  name="INDUSTRY"
				   class="tab_text validate[required] easyui-combotree"  panelHeight="350px"/>
		</td>
	</tr>


	<tr>
		<td>
			<input type="text" style="width:190px;"  id="placeCode_CD"  name="PLACE_CODE_CD"
				   class="tab_text validate[] easyui-combotree"  panelHeight="220px"/>
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<input type="text" style="width:250px;"  id="industry_CD"  name="INDUSTRY_CD"
				   class="tab_text validate[] easyui-combotree"  panelHeight="350px"/>
			<font class="tab_color">变更后</font>
		</td>
	</tr>
	<tr>
		<td  rowspan="2" class="tab_width"><font class="tab_color">*</font> 建设规模及内容：</td>
		<td  colspan="3">
			<textarea  id="scaleContent" class="eve-textarea validate[required],maxSize[2000]" rows="3" cols="200"
					   name="SCALE_CONTENT" style="width:860px;height:150px;"  ></textarea>
		</td>
	</tr>

	<tr>
		<td  colspan="3">
			<textarea  id="scaleContent_CD" class="eve-textarea validate[],maxSize[2000]" rows="3" cols="200"
					   name="SCALE_CONTENT_CD" style="width:860px;height:150px;"  ></textarea>
			<font class="tab_color">变更后</font>
		</td>
	</tr>
	<tr>
		<td rowspan="2" class="tab_width"><span class="tab_color">*</span> 项目属性：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="PROJECTATTRIBUTES"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择项目属性" name="PROJECT_ATTRIBUTES" id="projectAttributes">
			</eve:eveselect>
		</td>
		<td rowspan="2" class="tab_width"><span class="tab_color">*</span> 产业结构调整指导目录：</td>
		<td>
			<input type="text"   id="industryStructure"  name="INDUSTRY_STRUCTURE"
				   class="tab_text validate[required] easyui-combotree" panelHeight="350px"/>
		</td>
	</tr>

	<tr>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="PROJECTATTRIBUTES"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择项目属性" name="PROJECT_ATTRIBUTES_CD" id="projectAttributes_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<input type="text"   id="industryStructure_CD"  name="INDUSTRY_STRUCTURE_CD"
				   class="tab_text validate[] easyui-combotree" panelHeight="350px"/>
			<font class="tab_color">变更后</font>
		</td>
	</tr>

	<tr>
		<td rowspan="2" class="tab_width"><span class="tab_color">*</span> 土地获取方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="getLandMode"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
			</eve:eveselect>
		</td>
		<td rowspan="2" class="tab_width"><span class="tab_color">*</span> 项目投资来源：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="XMTZLY"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
			</eve:eveselect>
		</td>
	</tr>

	<tr>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="getLandMode"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE_CD" id="getLandMode_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="XMTZLY"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择项目投资来源" name="XMTZLY_CD" id="XMTZLY_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
	</tr>


	<tr>
		<td rowspan="2" class="tab_width"><span class="tab_color">*</span> 工程分类：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="GCFL"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
			</eve:eveselect>
		</td>
		<td rowspan="2" class="tab_width"><span class="tab_color">*</span> 是否完成区域评估：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="SFWCQYPG"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
			</eve:eveselect>
		</td>
	</tr>

	<tr>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="GCFL"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择工程分类" name="GCFL_CD" id="GCFL_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="SFWCQYPG"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG_CD" id="SFWCQYPG_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
	</tr>

	<tr>
		<td rowspan="2" class="tab_width"><span class="tab_color">*</span> 土地是否带设计方案：</td>
		<td colspan="3">
			<eve:eveselect clazz="tab_text validate[required]" dataParams="TDSFDSJFA"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
			</eve:eveselect>
		</td>

	</tr>

	<tr>
		<td  colspan="3">
			<eve:eveselect clazz="tab_text validate[]" dataParams="TDSFDSJFA"
						   dataInterface="dictionaryService.findDatasForSelect"
						   defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA_CD" id="TDSFDSJFA_CD">
			</eve:eveselect>
			<font class="tab_color">变更后</font>
		</td>
	</tr>

</table>
<div id="lerepInfoTable"></div>
<div id="dnyTable"></div>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->