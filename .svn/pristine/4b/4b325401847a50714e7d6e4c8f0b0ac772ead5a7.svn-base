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
<style>
.field_width{width:192px;}
</style>

<script type="text/javascript">
	var codeMap = {};
	var countryMap = {};
	var contributionModeMap = {};
	var businessTypeMap = {};
	var chinaForeignShareRatioMap = {};
	var enterpriseNatureMap = {};
var isHide=true;
	$(function(){
		if(!${EFLOW_IS_START_NODE}){
			$("#projectCodeA").hide();	
			$("#projectCodeFont").hide();			
			$("#tzjbxx input").attr("readonly",false);
			$("#tzjbxx textarea").attr("readonly",false);		
			$("#tzjbxx select").attr("readonly",false);	
			$("#tzjbxx input[name='PROJECTCODE']").attr("readonly",true);
		}
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
		
		$(".isCanHide").attr("style","display:none;");	
		isHide=true;

		$("#totalMoney").blur(function(){ totalMoney(); } );
		$("#enterpriseName").blur(function(){ changeInfo(); } );
		$("#lerepCertType").blur(function(){ changeInfo(); } );
		$("#lerepCertNo").blur(function(){ changeInfo(); } );
		$("#contactName").blur(function(){ changeInfo(); } );
		$("#contactTel").blur(function(){ changeInfo(); } );
		$("#contactEmail").blur(function(){ changeInfo(); } );
	});
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
							}else{							
								$("#"+key).val(resultJson.tzProject[key]);
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
			if(lerepInfoObj.lerepInfo.length>0&& 
					(null != lerepInfoObj.lerepInfo[0].enterprise_id && "" != lerepInfoObj.lerepInfo[0].enterprise_id)){
				var
				lerepInfoTableStr="<table cellpadding='0' cellspacing='1' class='tab_tk_pro'  id='tzczqkxx'>"
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
							certtype = lerepInfoObj.lerepInfo[i].lerep_certtype
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
				lerepInfoTableStr+="</table>"
				$("#lerepInfoTable").html(lerepInfoTableStr);
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
	function showInfos(obj){
		if(isHide){
			isHide=false;
			$(".isCanHide").attr("style","display:black;");	
			obj.innerHTML="隐藏项目更多详细信息";
		}else{
			isHide=true;
			$(".isCanHide").attr("style","display:none;");	
			obj.innerHTML="显示项目更多详细信息";
		}
	}
	$().ready(function() {
		var TYPE_CODE = "";
		var TYPE_NAME = "";
		var flowSubmitObjexe = FlowUtil.getFlowObj();
		$("#EXEID").append(flowSubmitObjexe.EFLOW_EXEID);
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
						//初始化表单字段值
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
		var flowSubmitObjexe = FlowUtil.getFlowObj();
		$("#industryStructure").combotree({
			url: 'dicTypeController/selectIndustryStructureTree.do',
				multiple: false,
				cascadeCheck: false,
				onlyLeafCheck: true,
				onLoadSuccess: function () {
					$("input[name='INDUSTRY_STRUCTURE']").parent().children('input').eq(0).toggleClass('validate[required]');
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
	};
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
		<td class="tab_width"> 投资项目编号：</td>
		<td colspan="1">
		  <input type="text" maxlength="32" class="tab_text" name="PROJECTCODE" />
		  <a id="projectCodeA" onclick="loadTZXMXXData();" class="eflowbutton" style="padding: 3px 10px;">校 验</a><font id="projectCodeFont" color="red">（请先输入投资项目编号进行校验）</font><a target="_blank" href="https://fj.tzxm.gov.cn/eap/credit.reportGuide?cantcode=350128&projecttype=A00001" style="color:red;"><省级投资项目申报登记入口></a>
		</td>
		<td class="tab_width"> 申报号：</td>
		<td id = "EXEID"></td>
	</tr>
	<tr>
		<td class="tab_width"> 项目所属行政区划：</td>
		<td width="35%">
		  <input readonly="true" id="divisionCode" type="text" maxlength="6" class="tab_text validate[required]"  name="DIVISION_CODE" />
		</td>
		<td class="tab_width"> 项目名称：</td>
		<td>
		  <input readonly="true" id="projectName" type="text" class="tab_text validate[required]" name="PROJECT_NAME"   maxlength="128"/>
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width"> 项目类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="PROJECTTYPE"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType">
			</eve:eveselect>
		</td>
		<td class="tab_width"> 建设性质：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width" dataParams="PROJECTNATURE"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="projectNature">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 项目（法人）单位：</td>
		<td>
			<input readonly="true" id="enterpriseName" type="text" maxlength="128" class="tab_text validate[required]"  name="ENTERPRISE_NAME" />
		</td>
		<td class="tab_width"> 项目法人证照类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required] field_width"  dataParams="LEREPCERTTYPE"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择项目法人证照类型" name="LEREP_CERTTYPE" id="lerepCertType">
			</eve:eveselect>
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width"> 项目法人证照号码：</td>
		<td>
		<input readonly="true" id="lerepCertNo" type="text" maxlength="50" class="tab_text validate[required]"  name="LEREP_CERTNO" />
		</td>
		<td class="tab_width">项目阶段：</td>
		<td>
			<eve:eveselect clazz="tab_text field_width" dataParams="PROJECTSTAGE"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择项目阶段" name="PROJECT_STAGE" id="projectStage">
			</eve:eveselect>
		</td>
	</tr>
	<tr>
		<td class="tab_width"> 申报日期：</td>
		<td>
		<input readonly="true" id="applyDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"  class="tab_text validate[required] Wdate" name="APPLY_DATE" />
		</td>
		<td class="tab_width"> 总投资（万元）：</td>
		<td>
		<input  readonly="true" id="totalMoney" type="text"  maxlength="16" class="tab_text " name="TOTAL_MONEY"  />
		</td>
	</tr>

			<tr>
				<td class="tab_width"> <span class="bscolor1">*</span>公开方式：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="OPENWAY"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择公开方式" name="OPEN_WAY" id="openWay">
					</eve:eveselect>
				</td>
				<td class="tab_width"> <span class="bscolor1">*</span>项目属性：</td>
				<td>
					<eve:eveselect clazz="tab_text validate[required]" dataParams="PROJECTATTRIBUTES"
					dataInterface="dictionaryService.findDatasForSelect"
					defaultEmptyText="请选择项目属性" name="PROJECT_ATTRIBUTES" id="projectAttributes">
					</eve:eveselect>
				</td>
			</tr>
			<tr>
				<td class="tab_width"> <span class="bscolor1">*</span>产业结构调整指导目录：</td>
				<td>
					<input type="text" style="width:250px;"  id="industryStructure"  name="INDUSTRY_STRUCTURE" 
					class="tab_text validate[required] easyui-combotree" panelHeight="350px"/>
					
				</td>
			</tr>
				
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 总投资额为“0”时说明：</td>
		<td  colspan="3">
			<textarea id="totalMoneyExplain" class="tab_text " rows="3" cols="200"  
			  name="TOTAL_MONEY_EXPLAIN" style="width:860px;height:150px;"  ></textarea>
		</td>
	</tr>
	<tr>
		<td class="tab_width">联系人名称：</td>
		<td>
		<input readonly="true" id="contactName" type="text" maxlength="100" class="tab_text " name="CONTACT_NAME"  />
		</td>
		<td class="tab_width">联系手机：</td>
		<td>
		<input readonly="true" id="contactMobile" type="text" maxlength="11" class="tab_text " name="CONTACT_MOBILE"  /><span style="width:40px;display:inline-block;"></span>
		<span ><a href="javascript:void(0);" onclick="showInfos(this);" style="color:red;">显示项目更多详细信息</a></span>
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width">身份证号码：</td>
		<td>
		<input readonly="true" id="contactIdCard" type="text"  maxlength="18" class="tab_text validate[custom[vidcard]]" name="CONTACT_IDCARD"  />
		</td>
		<td class="tab_width">联系电话：</td>
		<td>
		<input readonly="true" id="contactTel" type="text"  maxlength="16" class="tab_text " name="CONTACT_TEL"  />
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width">联系人邮编：</td>
		<td>
		<input readonly="true" id="contactPostCode" type="text" maxlength="6" class="tab_text ,validate[custom[chinaZip]]" name="CONTACT_POSTCODE"  />
		</td>
		<td class="tab_width">联系人地址：</td>
		<td>
		<input readonly="true" id="contactAddress" type="text"  maxlength="250" class="tab_text " name="CONTACT_ADDRESS"  />
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width">联系人邮箱：</td>
		<td>
		<input readonly="true" id="contactEmail" type="text" maxlength="64" class="tab_text " name="CONTACT_EMAIL"  />
		</td>
		<td class="tab_width"> 建设地点：</td>
<%--		<td>--%>
<%--			<select id="placeCode" class="tab_text validate[required]" name="PLACE_CODE">--%>
<%--				<option value="">请选择建设地点详</option>--%>
<%--				<option value="350000">福州市</option>--%>
<%--				<option value="350400">平潭县</option>--%>
<%--				<option value="350100">闽侯县</option>--%>
<%--				<option value="350200">长乐市</option>--%>
<%--				<option value="350300">福清市</option>--%>
<%--				<option value="350500">连江县</option>--%>
<%--				<option value="350600">罗源县</option>--%>
<%--				<option value="350700">永泰县</option>--%>
<%--				<option value="350800">闽清县</option>--%>
<%--			</select>--%>
<%--		</td>--%>
		<%--<td>
		<input readonly="true" id="placeCode" type="text" maxlength="250" class="tab_text " name="PLACE_CODE"  />
		</td>
	--%>
	   <td>     
			<input type="text" style="width:190px;"  id="placeCode"  name="PLACE_CODE" 
			class="tab_text validate[required] easyui-combotree"  panelHeight="20px"/>
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width"> 拟开工时间：</td>
		<td>
		<input readonly="true" id="startYear" type="text" maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="START_YEAR" />
		</td>
		<td class="tab_width"> 拟建成时间：</td>
		<td>
		<input readonly="true" id="endYear" type="text"  maxlength="4" class="tab_text  Wdate"  onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false})"  name="END_YEAR" />
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width">是否开发区项目：</td>
		<td>
			<select id="isDeArea" onChange="onSelectClass(this.value)" class="tab_text field_width" name="IS_DE_AREA">
				<option value="">请选择是否开发区项目</option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>
		<td class="tab_width">开发区名称：</td>
		<td>
			<input readonly="true" id="deAreaName" type="text"  maxlength="128" class="tab_text " name="DE_AREA_NAME"  />
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width"> 国标行业：</td>
		<td>					
			<input type="text" style="width:250px;"  id="industry"  name="INDUSTRY" class="tab_text validate[required] easyui-combotree"  panelHeight="350px"/>
		</td>
		<td class="tab_width">投资项目行业分类：</td>
		<td>	
			<eve:eveselect clazz="tab_text  permitIndustrySelect" dataParams="PERMITINDUSTRY"
			dataInterface="dictionaryService.findDatasForSelect"
			defaultEmptyText="请选择投资项目行业分类" name="PERMIT_INDUSTRY" id="permitIndustry">
			</eve:eveselect>
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width"> 建设地点详情：</td>
		<td  colspan="3">
			<textarea readonly="true" id="placeCodeDetail" class="eve-textarea maxSize[512]" rows="3" cols="200"  
			  name="PLACE_CODE_DETAIL" style="width:860px;height:100px;"  ></textarea>
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width"> 建设规模及内容：</td>
		<td  colspan="3">
			<textarea  readonly="true" id="scaleContent" class="eve-textarea maxSize[2000]" rows="3" cols="200"  
			  name="SCALE_CONTENT" style="width:860px;height:150px;"  ></textarea>
		</td>
	</tr>
	<tr class="isCanHide">
		<td class="tab_width">项目备注：</td>
		<td  colspan="3">
			<textarea readonly="true" id="projectRemark" class="eve-textarea maxSize[2000]" rows="3" cols="200"  
			  name="PROJECT_REMARK" style="width:860px;height:100px;" ></textarea>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro" id="wstzxx" style="display:" >
	<tr>
		<th colspan="4">外商投资信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 是否涉及国家安全：</td>
		<td>
			<select id="isCountrySecurity" onChange="showSecurityApprovalNumber(this.value)"   class="tab_text validate[required]" name="IS_COUNTRY_SECURITY">
				<option value="">请选择是否涉及国家安全</option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>
		<td class="tab_width" id="securityApprovalNumberName" style="display: ">安全审查决定文号：</td>
		<td>
		<input id="securityApprovalNumber" type="text"  maxlength="18" class="tab_text " name="SECURITY_APPROVAL_NUMBER"  style="display: "/>
		</td>
		<td id="securityApprovalNumberNameBlank" style="display:none"></td>
		<td id="securityApprovalNumberBlank" style="display:none"></td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 投资方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="investmentMode"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择投资方式" name="INVESTMENT_MODE" id="investmentMode" 
				onchange="showOther(this.value)">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 总投资额折合美元(万元)：</td>
		<td>
		<input id="totalMoneyDollar" type="text"  maxlength="18" 
		class="tab_text validate[required,custom[numberp6plus]]" name="TOTAL_MONEY_DOLLAR"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 总投资额使用的汇率（人民币/美元）：</td>
		<td>
		<input id="totalMoneyDollarRate" type="text" maxlength="100" 
		class="tab_text validate[required,custom[numberp6plus]]" name="TOTAL_MONEY_DOLLAR_RATE"  />
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 项目资本金(万元)：</td>
		<td>
		<input id="projectCapitalMoney" type="text"  maxlength="18" 
		class="tab_text validate[required,custom[numberp6plus]]" name="PROJECT_CAPITAL_MONEY"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 项目资本金折合美元(万元)：</td>
		<td>
		<input id="projectCapitalMoneyDollar" class="tab_text validate[required,custom[numberp6plus]]" 
		maxlength="100" class="tab_text " name="PROJECT_CAPITAL_MONEY_DOLLAR"  />
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 项目资本金使用的汇率（人民币/美元）：</td>
		<td>
		<input id="projectCapitalMoneyRate" type="text"  maxlength="18" 
		class="tab_text validate[required,custom[numberp6plus]]" name="PROJECT_CAPITAL_MONEY_RATE"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 适用产业政策条目类型：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="industrialPolicyType"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产业政策条目类型" name="INDUSTRIAL_POLICY_TYPE" id="industrialPolicyType">
			</eve:eveselect>
		</td>
		<td class="tab_width">适用产业政策条目：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[]" dataParams="industrialPolicy"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择产业政策条目" name="INDUSTRIAL_POLICY" id="industrialPolicy">
			</eve:eveselect>
		</td>
	</tr>
	<tr id="otherInvestmentApplyInfoTr" style="display: ">
		<td class="tab_width">其他投资方式需予以申报的情况：</td>
		<td  colspan="3">
		<textarea id="otherInvestmentApplyInfo" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
		 name="OTHER_INVESTMENT_APPLY_INFO"  style="width:860px;height:100px;" ></textarea>
		</td>
	</tr>
	<tr id="transactionBothInfoTr" style="display: ">
		<td class="tab_width">提供交易双方情况：</td>
		<td  colspan="3">
		<textarea id="transactionBothInfo" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
		 name="TRANSACTION_BOTH_INFO"  style="width:860px;height:100px;" ></textarea>
		</td>
	</tr>
	<tr id="mergerPlanTr" style="display: ">
		<td class="tab_width">并购安排：</td>
		<td  colspan="3">
		<textarea id="mergerPlan" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
		 name="MERGER_PLAN"  style="width:860px;height:100px;" ></textarea>
		</td>
	</tr>
	<tr id="mergerManagementModeScopeTr" style="display: ">
		<td class="tab_width">并购后经营方式及经营范围：</td>
		<td  colspan="3">
		<textarea id="mergerManagementModeScope" class="eve-textarea validate[maxSize[2000]]" rows="3" cols="200"  
		 name="MERGER_MANAGEMENT_MODE_SCOPE"  style="width:860px;height:100px;" ></textarea>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 土地获取方式 ：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="getLandMode"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
			</eve:eveselect>
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 总用地面积(平方米)：</td>
		<td>
		<input id="landArea" type="text"  maxlength="18" 
		class="tab_text validate[required,custom[numberp6plus]]" name="LAND_AREA"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 总建筑面积（平方米）：</td>
		<td>
		<input id="builtArea" type="text" maxlength="100" 
		class="tab_text validate[required,custom[numberp6plus]]" name="BUILT_AREA"  />
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 是否新增设备：</td>
		<td>
			<select id="isAddDevice" onChange=""   class="tab_text validate[required]" name="IS_ADD_DEVICE">
				<option value="">请选择是否新增设备</option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 拟进口设备数量及金额：</td>
		<td>
		<input id="importDeviceNumberMoney" type="text" maxlength="100" class="tab_text  validate[required]" name="IMPORT_DEVICE_NUMBER_MONEY"  />
		</td>
		<td class="tab_width"></td>
		<td>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="1" class="tab_tk_pro"  id="jwtzxx" style="display: none;" >
	<tr>
		<th colspan="4">境外投资信息</th>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 项目所在地：</td>
		<td>
		<input id="projectSite" type="text" maxlength="100" class="tab_text validate[required]" name="PROJECT_SITE"  />
		</td>
		<td class="tab_width"><font class="tab_color">*</font> 中方投资额(万元)：</td>
		<td>
		<input id="chinaTotalMoney" type="text"  maxlength="18" 
		class="tab_text validate[required,custom[numberp6plus]]" name="CHINA_TOTAL_MONEY"  />
		</td>
	</tr>
	<tr>
		<td class="tab_width"><font class="tab_color">*</font> 投资方式：</td>
		<td>
			<eve:eveselect clazz="tab_text validate[required]" dataParams="investmentMode"
				dataInterface="dictionaryService.findDatasForSelect"
				defaultEmptyText="请选择投资方式" name="INVESTMENT_MODE_JW" id="investmentModeJw">
			</eve:eveselect>
		</td>
		<td class="tab_width"></td>
		<td>
		</td>
	</tr>
</table>
<div id="lerepInfoTable"></div>
<div id="dnyTable"></div>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->