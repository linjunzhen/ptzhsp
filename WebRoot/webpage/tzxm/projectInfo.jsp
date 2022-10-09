<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="net.evecom.platform.system.model.SysUser"%>
<%@ page import="net.evecom.core.util.AppUtil"%>
<%@ page import="net.evecom.core.util.FileUtil" %>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("webRoot", basePath);
	String projectName = request.getParameter("projectName");
	String projectId = request.getParameter("projectId");
	String projectCode = request.getParameter("projectCode");
	String userCenter = FileUtil.readProperties("conf/config.properties").getProperty("USER_CENTER");
%>
<!DOCTYPE html>
<html>
	<head>
    	<base href="<%=basePath%>">
    	<script src="<%=path%>/plug-in/layui-v2.4.5/layui/layui.all.js"></script>
		<eve:resources 
			loadres="jquery,easyui,apputil,artdialog,laydate,layer,validationegine,icheck,json2"></eve:resources>
		<!-- my97 begin -->
		<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
		
		<link rel="stylesheet"
			href="<%=path%>/plug-in/layui-v2.4.5/layui/css/font_icon.css" media="all">
		<link rel="stylesheet"
			href="<%=path%>/plug-in/layui-v2.4.5/layui/css/layui.css">
		<link rel="stylesheet"
			href="<%=path%>/plug-in/layui-v2.4.5/layui/css/marchant.css" media="all">
		<link rel="stylesheet"
			href="<%=path%>/plug-in/layui-v2.4.5/layui/css/modules/layer/default/layer.css">
		<!-- my97 end -->
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
		<title>平潭综合实验区工程建设项目管理平台</title>
		<!--新ui-->
		<link href="<%=path%>/webpage/website/newproject/css/public.css" type="text/css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/webpage/tzxm/css/public.css"/>
		<link rel="stylesheet" href="<%=path%>/webpage/tzxm/css/projectBaseInfo.css">
		<script type="text/javascript" src="<%=path%>/webpage/tzxm/js/jquery.SuperSlide.2.1.3.js"></script>
		<script type="text/javascript">
		var xmdwxxDivHtml = "";
		$(function() {
			var lerepInfo = '${projectApply.lerep_info}';
			if(lerepInfo){
				var lerepInfoList = $.parseJSON(lerepInfo);
				for(var i=0 ; i<lerepInfoList.length; i++){
					initXmdwxx(lerepInfoList[i],i)
				}			
			}
			xmdwxxDivHtml = $("#xmdwxxDiv").html();
			getXmdwxxJson();
		});
		$().ready(function() {
			var TYPE_CODE = "";
			var TYPE_NAME = "";
			var projectApply = '${projectApply}';
			$("#industry").combotree({
				url: 'dicTypeController/selectTree.do',
					multiple: false,
					cascadeCheck: false,
					onlyLeafCheck: false,
					onLoadSuccess: function () {
						$("input[name='INDUSTRY']").parent().children('input').eq(0).toggleClass('validate[required]');
						$("input[name='INDUSTRY']").parent().css("overflow","visible");
						if(projectApply){
							//初始化表单字段值
							if(TYPE_CODE==""){
								TYPE_CODE = '${projectApply.INDUSTRY}';
							}
							if(TYPE_NAME==""){								
								$.post( "dicTypeController/info.do",{
									typeCode:TYPE_CODE},
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
					},
					onClick : function (node){
						TYPE_CODE= $("#industry").combotree("getValue");
						TYPE_NAME= $("#industry").combotree("getText");
					},
					onExpand:function(node){
						$("input[name='INDUSTRY']").parent().children('input').eq(0).toggleClass('validate[required]');
						$("input[name='INDUSTRY']").parent().css("overflow","visible");
					}
			});
			var TYPE_CODE1 = "";
			var TYPE_NAME1 = "";
			$("#industryStructure").combotree({
				url: 'dicTypeController/selectIndustryStructureTree.do',
					multiple: false,
					cascadeCheck: false,
					onlyLeafCheck: true,
					onLoadSuccess: function () {
						$("input[name='INDUSTRY_STRUCTURE']").parent().children('input').eq(0).toggleClass('validate[required]');
						$("input[name='INDUSTRY_STRUCTURE']").parent().css("overflow","visible");
						if(projectApply){
							if(TYPE_CODE1==""){
								TYPE_CODE1 = '${projectApply.INDUSTRY_STRUCTURE}';
							}
							if(TYPE_NAME1==""){								
								$.post( "dicTypeController/info.do",{
									typeCode:TYPE_CODE1},
									function(responseText3, status, xhr) {
										var resultJson3 = $.parseJSON(responseText3);
										if(null!=resultJson3){	
											TYPE_CODE1 = resultJson3.TYPE_CODE;
											TYPE_NAME1 = resultJson3.TYPE_NAME;
											$("#industryStructure").combotree("setValue",TYPE_CODE1);
											$("#industryStructure").combotree("setText",TYPE_NAME1);
										}
								});
							}else{
								$("#industryStructure").combotree("setValue",TYPE_CODE1);
								$("#industryStructure").combotree("setText",TYPE_NAME1);
							}
						}
					},
					onClick : function (node){
						TYPE_CODE1= $("#industryStructure").combotree("getValue");
						TYPE_NAME1= $("#industryStructure").combotree("getText");
					},
					onExpand:function(node){
						$("input[name='INDUSTRY_STRUCTURE']").parent().children('input').eq(0).toggleClass('validate[required]');
						$("input[name='INDUSTRY_STRUCTURE']").parent().css("overflow","visible");
					}
			});
			var TYPE_CODE2 = "";
			var TYPE_NAME2 = "";
			$("#placeCode").combotree({
				url: 'dicTypeController/placeTree.do',
					multiple: false,
					cascadeCheck: false,
					onlyLeafCheck: true,
					onLoadSuccess: function () {
						$("input[name='PLACE_CODE']").parent().children('input').eq(0).toggleClass('validate[required]');
						$("input[name='PLACE_CODE']").parent().css("overflow","visible");
						if(projectApply){
							if(TYPE_CODE2==""){
								TYPE_CODE2 = '${projectApply.PLACE_CODE}';
							}
							if(TYPE_NAME2==""){								
								$.post( "dicTypeController/placeInfo.do",{
									typeCode:TYPE_CODE2},
									function(responseText3, status, xhr) {
										var resultJson3 = $.parseJSON(responseText3);
										if(null!=resultJson3){	
											TYPE_CODE2 = resultJson3.TYPE_CODE;
											TYPE_NAME2 = resultJson3.TYPE_NAME;
											$("#placeCode").combotree("setValue",TYPE_CODE2);
											$("#placeCode").combotree("setText",TYPE_NAME2);
										}
								});
							}else{
								$("#placeCode").combotree("setValue",TYPE_CODE2);
								$("#placeCode").combotree("setText",TYPE_NAME2);
							}
						}
					},
					onClick : function (node){
						TYPE_CODE2= $("#placeCode").combotree("getValue");
						TYPE_NAME2= $("#placeCode").combotree("getText");
					},
					onExpand:function(node){
						$("input[name='PLACE_CODE']").parent().children('input').eq(0).toggleClass('validate[required]');
						$("input[name='PLACE_CODE']").parent().css("overflow","visible");
					}
			});
			$("input[name='LEREP_INFO']").val('${projectApply.lerep_info}');
			$("input[name='CONTRIBUTION_INFO']").val('${projectApply.contribution_info}');
			isShowWztzxx();
		});
		
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
					}else{
						targetField.val(fieldValueObj[fieldName]);
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
		</script>
	</head>
	<body style="background: #f0f0f0;">
	<%--开始编写头部文件 --%>
	<jsp:include page="/webpage/website/newproject/head.jsp?currentNav=sy" />
	<%--结束编写头部文件 --%>
		<form id="xmdjDetailForm" method="post"
			action="projectWebsiteApplyController.do?websiteSaveOrUpdate">
			<div class="eui-main">
				<div class="eui-content">
					<div class="eui-crumbs">
						<ul>
							<li style="font-size:16px"><img src="<%=path%>/webpage/website/newproject/images/new/add.png" >当前位置：</li>
							<li><a href="${webRoot}/webpage/tzxm/index.jsp">首页</a> > </li>
							<li><a href="${webRoot}projectWebsiteApplyController/search.do">项目查询</a> > </li>
							<li><a>项目详情</a> </li>
						</ul>
					</div>
					<div class="eui-instruction">
						<div class="slideTxtBox">
							<input type="hidden" id="lerepInfo" name="LEREP_INFO"/>
							<input type="hidden" id="contributionInfo" name="CONTRIBUTION_INFO"/>
							<input type="hidden" id="foreignabroadFlag" name="FOREIGN_ABROAD_FLAG" value="${projectApply.FOREIGN_ABROAD_FLAG}"/>
							<input type="hidden" id="theIndustry" name="THE_INDUSTRY" value="${projectApply.THE_INDUSTRY}"/>
							<div class="bd">
								<div class="eui-table-input">
									<div class="eui-table">
										<p>基本信息</p>
									</div>
									<table>
										<tr>
											<td >投资项目编号</td>
											<td>
												<input class="eve-input" type="text" name="PROJECT_CODE" 
															id="PROJECT_CODE" value="${projectApply.PROJECT_CODE}" readonly/>
											</td>
											<td >项目分类</td>
											<td>
												<input class="eve-input" type="text"  name="FLOW_CATE_NAME" 
														id="FLOW_CATE_NAME" value="${projectApply.FLOW_CATE_NAME}" readonly/>
											</td>
										</tr>
										<tr>
											<td>项目名称</td>
											<td>
												<input type="text" class="eve-input" name="PROJECT_NAME" 
													value="${projectApply.PROJECT_NAME}" readonly/>
											</td>
											<td>项目类型</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input field_width" dataParams="PROJECTTYPE"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_TYPE}" 
													defaultEmptyText="请选择项目类型" name="PROJECT_TYPE" id="projectType" >
												</eve:eveselect>
											</td>
										</tr>
										<tr>
											<td>项目所属区划</td>
											<td>
												<input type="text" class="eve-input" 
													value="${projectApply.DIVISION_CODE}" name="DIVISION_CODE" readonly/>
											</td>
											<td>投资项目目录编码</td>
											<td>
												<input type="text" class="eve-input" 
													value="${projectApply.PERMIT_ITEM_CODE}" name="PERMIT_ITEM_CODE" readonly/>
											</td>
										</tr>
										<tr>
											<td>建设性质</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="PROJECTNATURE"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_NATURE}"
													defaultEmptyText="请选择建设性质" name="PROJECT_NATURE" id="PROJECT_NATURE">
												</eve:eveselect>
											</td>
											<td>建设地点详情</td>
											<td>
												<input type="text" class="eve-input" id="PLACE_CODE_DETAIL"  
													name="PLACE_CODE_DETAIL"  value="${projectApply.PLACE_CODE_DETAIL}" readonly/>
											</td>
										</tr>
										<tr>
											<td>拟开工时间</td>
											<td>
												<input id="startYear" type="text" class="eve-input"
													name="START_YEAR"  value="${projectApply.START_YEAR}" readonly/>
											</td>
											<td>拟建成时间</td>
											<td>
												<input id="endYear" type="text" class="eve-input" 
													name="END_YEAR" value="${projectApply.END_YEAR}" readonly/>
											</td>
										</tr>
										<tr>
											<td>总投资（万元）</td>
											<td>
												<input id="totalMoney" type="text" class="eve-input" 
													name="TOTAL_MONEY"  value="${projectApply.TOTAL_MONEY}" readonly/>
											</td>
											<td>申报时间</td>
											<td>
												<input id="APPLY_DATE" type="text" class="eve-input" 
													name="APPLY_DATE"  value="${projectApply.APPLY_DATE}" readonly />
											</td>
										</tr>
										<tr>
											<td>总投资额为0时说明</td>
											<td colspan="3">
												<textarea id="totalMoneyExplain" class="eui-input-w" rows="3" cols="200"  
						  							name="TOTAL_MONEY_EXPLAIN" readonly>${projectApply.TOTAL_MONEY_EXPLAIN}</textarea>
											</td>
										</tr>
										<tr>
											<td>建设地点</td>
											<td>
												<div>
													<input type="text" class="eve-input whf_input easyui-combotree" id="placeCode"  
														name="PLACE_CODE" readonly/>
												</div>
											</td>
											<td>国标行业</td>
											<td>
												<div>
													<input type="text" class="eve-input whf_input easyui-combotree" id="industry" 
														name="INDUSTRY"  readonly />
												</div>
											</td>
										</tr>
										<tr>
											<td>建设规模及内容</td>
											<td colspan="3">
												<textarea id="SCALE_CONTENT" class="eui-input-w" rows="3" cols="200"
													 name="SCALE_CONTENT" readonly>${projectApply.SCALE_CONTENT}</textarea>
											</td>
										</tr>
										<tr>
											<td>项目属性</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="PROJECTATTRIBUTES"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.PROJECT_ATTRIBUTES}"
													defaultEmptyText="请选择投资项目属性" name="PROJECT_ATTRIBUTES" id="PROJECT_ATTRIBUTES">
												</eve:eveselect>
											</td>
											</td>
											<td>产业结构指导目录</td>
											<td>
												<div>
													<input type="text" name="INDUSTRY_STRUCTURE" class="eve-input whf_input easyui-combotree" 
														id="industryStructure"  panelHeight="150px" readonly/>
												</div>
											</td>
										</tr>
										<tr>	
											<td>土地获取方式</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="getLandMode"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.GET_LAND_MODE}"
													defaultEmptyText="请选择土地获取方式" name="GET_LAND_MODE" id="getLandMode">
												</eve:eveselect>
											</td>
											<td>项目投资来源</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="XMTZLY"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.XMTZLY}"
													defaultEmptyText="请选择项目投资来源" name="XMTZLY" id="XMTZLY">
												</eve:eveselect>
											</td>
										</tr>
										<tr>	
											<td>土地是否带设计方案</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="TDSFDSJFA"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.TDSFDSJFA}"
													defaultEmptyText="请选择土地是否带设计方案" name="TDSFDSJFA" id="TDSFDSJFA">
												</eve:eveselect>
											</td>
											<td>是否完成区域评估</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="SFWCQYPG"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.SFWCQYPG}"
													defaultEmptyText="请选择是否完成区域评估" name="SFWCQYPG" id="SFWCQYPG">
												</eve:eveselect>
											</td>
										</tr>
										<tr>	
											<td>工程分类</td>
											<td colspan="3">
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="GCFL"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.GCFL}"
													defaultEmptyText="请选择工程分类" name="GCFL" id="GCFL">
												</eve:eveselect>
											</td>
										</tr>
									</table>
								</div>
								<div id="wstzxx" class="eui-table-input">
									<div class="eui-table">
										<p>外商投资信息</p>
									</div>
									<table>
										<tr>
											<td>是否涉及国家安全</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="YesOrNo"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.IS_COUNTRY_SECURITY}" 
													onchange="showSecurityApprovalNumber(this.value)" 
													defaultEmptyText="请选择是否涉及国家安全" name="IS_COUNTRY_SECURITY" id="IS_COUNTRY_SECURITY">
												</eve:eveselect>
											</td>
											<td>安全审查决定文号</td>
											<td>
												<input type="text" maxlength="16" class="eve-input" id="SECURITY_APPROVAL_NUMBER" 
													 value="${projectApply.SECURITY_APPROVAL_NUMBER}" readonly/>
											</td>
										</tr>
										<tr>
											<td>投资方式</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="investmentMode"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INVESTMENT_MODE}"
													defaultEmptyText="请选择投资方式" name="INVESTMENT_MODE" id="investmentMode" >
												</eve:eveselect>
											</td>
											<td>项目资本金(万元)</td>
											<td>
												<input type="text" class="eve-input" id="projectCapitalMoney" name="PROJECT_CAPITAL_MONEY"  
													value="${projectApply.PROJECT_CAPITAL_MONEY}" readonly/>
											</td>
										</tr>
										<tr>
											<td>总投资额使用的汇率<br/>（人民币/美元）</td>
											<td>
												<input type="text" class="eve-input" id="totalMoneyDollarRate" name="TOTAL_MONEY_DOLLAR_RATE" 
													value="${projectApply.TOTAL_MONEY_DOLLAR_RATE}"  readonly/>
											</td>
											<td>总投资额折合美元<br>（万元）</td>
											<td>
												<input type="text" class="eve-input" id="totalMoneyDollar" name="TOTAL_MONEY_DOLLAR" 
													value="${projectApply.TOTAL_MONEY_DOLLAR}" readonly/>
											</td>
										</tr>
										<tr>
											<td>项目资本金折合美元<br/>（万元）</td>
											<td>
												<input class="eve-input" id="projectCapitalMoneyDollar" name="PROJECT_CAPITAL_MONEY_DOLLAR"  
													value="${projectApply.PROJECT_CAPITAL_MONEY_DOLLAR}" readonly/>
											</td>
											<td>项目资本金使用汇率<br>（人名币/美元）</td>
											<td>
												<input type="text" id="projectCapitalMoneyRate" name="PROJECT_CAPITAL_MONEY_RATE" 
													class="eve-input"  value="${projectApply.PROJECT_CAPITAL_MONEY_RATE}" readonly />
											</td>
										</tr>
										<tr>
											<td>产业政策条目类型</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="industrialPolicyType"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INDUSTRIAL_POLICY_TYPE}"
													defaultEmptyText="请选择产业政策条目类型" name="INDUSTRIAL_POLICY_TYPE" id="industrialPolicyType">
												</eve:eveselect>
											</td>
											<td>适用产业政策条目</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="industrialPolicy"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.INDUSTRIAL_POLICY}"
													defaultEmptyText="请选择产业政策条目" name="INDUSTRIAL_POLICY" id="industrialPolicy">
												</eve:eveselect>
											</td>
										</tr>
										<tr>
											<td>其他投资方式需予<br>以申报的情况</td>
											<td colspan="3">
												<textarea id="otherInvestmentApplyInfo" class="eui-input-w" rows="3" cols="200" 
													readonly >${projectApply.OTHER_INVESTMENT_APPLY_INFO}</textarea>
											</td>
										</tr>
										<tr>
											<td>提供交易双方情况</td>
											<td colspan="3">
												<textarea id="transactionBothInfo" class="eui-input-w" rows="3"  cols="200" 
													readonly >${projectApply.TRANSACTION_BOTH_INFO}</textarea>
											</td>
										</tr>
										<tr>
											<td>并购安排</td>
											<td colspan="3">
												<textarea id="mergerPlan" class="eui-input-w" rows="3" cols="200" 
													readonly>${projectApply.MERGER_PLAN}</textarea>
											</td>
										</tr>
										<tr>
											<td>并购后经营方式<br>及经营范围</td>
											<td colspan="3">
												<textarea id="mergerManagementModeScope" class="eui-input-w" rows="3" cols="200"  
						 							readonly >${projectApply.MERGER_MANAGEMENT_MODE_SCOPE}</textarea>
											</td>
										</tr>
										<tr>
											<td>总建筑面积(平方米)</td>
											<td>
												<input type="text" class="eve-input" id="builtArea" name="BUILT_AREA"
													value="${projectApply.BUILT_AREA}" readonly/>
											</td>
											<td>总用地面积(平方米)</td>
											<td>
												<input type="text" class="eve-input" id="landArea" name="LAND_AREA" 
													value="${projectApply.LAND_AREA}" readonly/>
											</td>
										</tr>
										<tr>
											<td>是否新增设备</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="YesOrNo"
													dataInterface="dictionaryService.findDatasForSelect" value="${projectApply.IS_ADD_DEVICE}"
													defaultEmptyText="请选择是否新增设备" name="IS_ADD_DEVICE" id="IS_ADD_DEVICE">
												</eve:eveselect>
											</td>
											<td>拟进口设备数量及<br>金额</td>
											<td>
												<input id="importDeviceNumberMoney" type="text" class="eve-input" 
													value="${projectApply.IMPORT_DEVICE_NUMBER_MONEY}" readonly/>
											</td>
										</tr>
									</table>
								</div>
								<div id="jwtzxx" class="eui-table-input">
									<div class="eui-table">
										<p>境外投资信息</p>
									</div>
									<table>
										<tr>
											<td>项目所在地</td>
											<td>
												<input type="text" class="eve-input" id="PROJECT_SITE" name="PROJECT_SITE" 
													value="${projectApply.PROJECT_SITE}" readonly/>
											</td>
											<td>中方投资额(万元)</td>
											<td>
												<input type="text" class="eve-input" id="chinaTotalMoney" name="CHINA_TOTAL_MONEY" 
													value="${projectApply.CHINA_TOTAL_MONEY}" readonly/>
											</td>
										</tr>
									</table>
								</div>
								<div id="xmdwxxDiv" class="eui-table-input">
									<div class="eui-table">
										<p>项目（法人）单位信息</p>
									</div>
									<table>
										<tr>
											<td>单位名称</td>
											<td >
												<input type="text" class="eve-input" name="enterprise_name" readonly/>
											</td>
											<td>单位类型</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="DWLX"
													dataInterface="dictionaryService.findDatasForSelect"
													defaultEmptyText="请选择单位类型" name="dwlx" id="dwlx">
												</eve:eveselect>
											</td>
										</tr>
										<tr>
											<td>证照类型</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="LEREPCERTTYPE"
													dataInterface="dictionaryService.findDatasForSelect"
													defaultEmptyText="请选择证照类型" name="lerep_certtype" id="lerep_certtype">
												</eve:eveselect>
											</td>
											<td>证照号码</td>
											<td>
												<input type="text" class="eve-input" name="lerep_certno" readonly/>
											</td>
										</tr>	
										<tr>
											<td>联系人名称</td>
											<td>
												<input type="text" class="eve-input" name="contact_name" readonly/>
											</td>
											<td>联系电话</td>
											<td>
												<input type="text" class="eve-input" name="contact_tel" readonly/>
											</td>
										</tr>	
										<tr>
											<td>联系人邮箱</td>
											<td>
												<input type="text" class="eve-input" name="contact_email" readonly/>
											</td>
											<td>注册地址</td>
											<td>
												<input type="text" class="eve-input" name="enterprise_place" readonly/>
											</td>
										</tr>	
										<tr>
											<td>单位性质</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="enterpriseNature"
													dataInterface="dictionaryService.findDatasForSelect"
													defaultEmptyText="请选择单位性质" name="enterprise_nature" id="enterprise_nature">
												</eve:eveselect>
											</td>
											<td>持股比例是否与资本金相同</td>
											<td>
												<eve:eveselect clazz="eve-input1 whf_input" dataParams="chinaForeignShareRatio"
													dataInterface="dictionaryService.findDatasForSelect"
													defaultEmptyText="请选择持股比例是否与资本金相同" name="china_foreign_share_ratio" 
													id="china_foreign_share_ratio">
												</eve:eveselect>								
											</td>
										</tr>
										<tr>
											<td>主要经营范围</td>
											<td>
												<input type="text" class="eve-input" name="business_scope" readonly/>
											</td>
											<td>联系手机</td>
											<td>
												<input type="text" class="eve-input" name="contact_phone" readonly/>
											</td>
										</tr>
										<tr>
											<td>传真</td>
											<td>
												<input type="text" class="eve-input" name="contact_fax" readonly/>
											</td>
											<td>通讯地址</td>
											<td>
												<input type="text" class="eve-input" name="correspondence_address" readonly/>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%--开始编写尾部文件 --%>
				<jsp:include page="/webpage/website/newproject/foot.jsp" />
				<%--结束编写尾部文件 --%>
			</div>
		</form>
		<!--内容结束-->
	</body>	
	<script>
		$(".eui-head li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".eui-nav li").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".td1").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
		$(".td2").click(function(){
			$(this).addClass("on").siblings().removeClass("on");
		});
	</script>
</html>
