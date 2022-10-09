<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>

	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/pin-1.1/jquery.pin.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.slimscroll.js"></script>
	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			AppUtil.initWindowForm("RDGTAPPLY_FORM", function(form, valid) {
				if (valid&&purchaseSubmit()&&purchasePlanSubmit()&&currentSubmit()&&employeesSubmit()) {
					purchaseSubmit();
					purchasePlanSubmit();
					currentSubmit();
					employeesSubmit();
					//将提交按钮禁用,防止重复提交
					var formData = $("#RDGTAPPLY_FORM").serialize();
					var url = $("#RDGTAPPLY_FORM").attr("action");
					AppUtil.ajaxProgress({
						url : url,
						params : formData,
						callback : function(resultJson) {
							if(resultJson.success){
							  	parent.art.dialog({
									content: resultJson.msg,
									icon:"succeed",
									time:3,
									ok: true
								});
								art.dialog.data("backFormInfo",{
									formName : '${materForm.formName }',
				    				recordId:resultJson.jsonString
								});
								AppUtil.closeLayer();
							}else{
								parent.art.dialog({
									content: resultJson.msg,
									icon:"error",
									ok: true
								});
							}
						}
					});
				}
			},null);
			
			$('#RDGTAPPLY_FORM').find('input,textarea').prop("readonly", true);
			$('#RDGTAPPLY_FORM').find('select').prop("disabled", "disabled");
			$('#RDGTAPPLY_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#RDGTAPPLY_FORM').find(".laydate-icon").attr('disabled',"disabled");
			
			if(${materForm.RECORD_ID!=null }){
				if('${materForm.PURCHASE_JSON }'!=null&&'${materForm.PURCHASE_JSON }'!=''){
					var purchaseJson = '${materForm.PURCHASE_JSON }';
					var purchase = eval(purchaseJson);
					var jsonstr = '{"total":'+purchase.length+',"rows":'+purchaseJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#purchaseGrid').datagrid('loadData',data);
				} 
				if('${materForm.PURCHASE_PLAN_JSON }'!=null&&'${materForm.PURCHASE_PLAN_JSON }'!=''){
					var purchasePlanJson = '${materForm.PURCHASE_PLAN_JSON }';
					var purchasePlan = eval(purchasePlanJson);
					var jsonstr = '{"total":'+purchasePlan.length+',"rows":'+purchasePlanJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#purchasePlanGrid').datagrid('loadData',data);
				}  
				if('${materForm.CURR_JSON }'!=null&&'${materForm.CURR_JSON }'!=''){
					var currentJson = '${materForm.CURR_JSON }';
					var current = eval(currentJson);
					var jsonstr = '{"total":'+current.length+',"rows":'+currentJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#currentGrid').datagrid('loadData',data);
				} 
				if('${materForm.PLAN_EMPLOYEES }'!=null&&'${materForm.PLAN_EMPLOYEES }'!=''){
					var employeesJson = '${materForm.PLAN_EMPLOYEES }';
					var employees = eval(employeesJson);
					var jsonstr = '{"total":'+employees.length+',"rows":'+employeesJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#employeesGrid').datagrid('loadData',data);
				}
				if('${materForm.APPLY_TYPE }'=='1'){
					$("#expand").find("input,textarea").prop("disabled", "disabled");
					$('#expand').find('input,textarea').val('');
					$('#expand').find(":checkbox").attr('checked',false);
				}else{
					$("#first").find("input,textarea").prop("disabled", "disabled");
					$('#first').find('input,textarea').val('');
					$('#first').find(":checkbox").attr('checked',false);
				}
			}
		});
		
		function downloadDoc(){
            var recordId = '${materForm.RECORD_ID }';
            var fornName = '${materForm.formName }';
            window.location.href=__ctxPath+"/domesticControllerController/downLoadRelatedMater.do?recordId="+recordId
            +"&fornName="+fornName;
        }
	</script>
</head>
<body>
	<div class="container">
		<div class="syj-sbmain2 tmargin20">
			<div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
				<div class="bd margin20">
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="RDGTAPPLY_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="PURCHASE_JSON" value="${materForm.PURCHASE_JSON }"/>
						<input type="hidden" name="PURCHASE_PLAN_JSON" value="${materForm.PURCHASE_PLAN_JSON }"/>
						<input type="hidden" name="CURR_JSON" value="${materForm.CURR_JSON }"/>
						<input type="hidden" name="PLAN_EMPLOYEES" value="${materForm.PLAN_EMPLOYEES }"/>
						<div class="syj-title1">
						    <a href="javascript:void(0);" onclick="javascript:downloadDoc();" class="syj-addbtn" >下 载</a>
							<span>申请人基本信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="border-bottom: none;"><font class="syj-color2">*</font>申请人名称：</th>
									<td colspan="3" style="border-bottom: none;"><input type="text"
										class="syj-input1 validate[required]" name="APPLICANT_NAME" value="${materForm.APPLICANT_NAME }"
										placeholder="要求填写企业（公司）全称、企业预先核准全称" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>负责人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="RESPO_NAME" value="${materForm.RESPO_NAME }"
										placeholder="请输入负责人姓名" maxlength="16" /></td>
									<th><font class="syj-color2">*</font>经办人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="请输入经办人姓名" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>通信地址：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="MAILING_ADDR" value="${materForm.MAILING_ADDR }"
										placeholder="请输入通信地址" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>邮编：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[chinaZip]]" name="POST_CODE" value="${materForm.POST_CODE }"
										placeholder="请输入邮编" maxlength="6" /></td>
									<th> 电话：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="APPLY_PHONE" value="${materForm.APPLY_PHONE }"
										placeholder="请输入电话" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>手机：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="APPLY_MOBILE" value="${materForm.APPLY_MOBILE }"
										placeholder="请输入手机号码" maxlength="11" /></td>
									<th> 电子邮箱：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="APPLY_EMAIL" value="${materForm.APPLY_EMAIL }"
										placeholder="请输入电子邮箱" maxlength="32" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>申请许可：</th>
									<td colspan="3">
										<input type="radio" name="APPLY_TYPE" value="1" <c:if test="${materForm.APPLY_TYPE!=2 }">checked="checked"</c:if> >首次申请道路危险货物运输经营
										<input type="radio" name="APPLY_TYPE" value="2" <c:if test="${materForm.APPLY_TYPE==2 }">checked="checked"</c:if> >申请扩大道路危险货物运输经营范围
									</td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>申请许可内容（首次申请道路危险货物运输经营的填写）</span>
						</div>
						<div style="position:relative;" id="first">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>类别：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CONTENT_CATEGORY" width="650px;" clazz="checkbox validate[]"
											dataParams="rdgtCatalog" value="${materForm.CONTENT_CATEGORY }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th >项别(剧毒化学品除外)：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CONTENT_ITEM" width="650px;" clazz="checkbox validate[]"
											dataParams="rdgtItem" value="${materForm.CONTENT_ITEM }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th >品名：</th>
									<td>
										<textarea rows="5" cols="120" maxlength="256" name="CONTENT_PRODUCT">${materForm.CONTENT_PRODUCT }</textarea>
										<span>【如是剧毒化学品，应在品名后括号标注“剧毒”，例如“液氯（剧毒）”】</span>
									</td>
								</tr>
							</table>
							<span style="color: red">注：1、勾选某类经营范围的，不必再勾选该类内的项别，反之亦然；按品名申请的，不必勾选该品名对应的类别或项别。
							2、如许可内容没有剧毒化学品，要在《道路运输经营许可证》经营范围内标注“剧毒化学品除外”。</span>
						</div>
						<div class="syj-title1 tmargin20">
							<span>申请许可内容（申请扩大道路危险货物运输经营范围的填写）</span>
						</div>
						<div style="position:relative;" id="expand">
							<span>1、现从事的道路危险货物运输经营范围</span>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th >类别：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CONTENT_CATEGORY_CURR" width="650px;" clazz="checkbox validate[]"
											dataParams="rdgtCatalog" value="${materForm.CONTENT_CATEGORY_CURR }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th >项别(剧毒化学品除外)：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CONTENT_ITEM_CURR" width="650px;" clazz="checkbox validate[]"
											dataParams="rdgtItem" value="${materForm.CONTENT_ITEM_CURR }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th >品名：</th>
									<td>
										<textarea rows="5" cols="120" maxlength="256" name="CONTENT_PRODUCT_CURR">${materForm.CONTENT_PRODUCT_CURR }</textarea>
										<span>【如是剧毒化学品，应在品名后括号标注“剧毒”，例如“液氯（剧毒）”】</span>
									</td>
								</tr>
							</table>
							<span><br>2、拟申请的道路危险货物运输经营范围</span>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th >类别：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CONTENT_CATEGORY_PLAN" width="650px;" clazz="checkbox validate[]"
											dataParams="rdgtCatalog" value="${materForm.CONTENT_CATEGORY_PLAN }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th >项别(剧毒化学品除外)：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CONTENT_ITEM_PLAN" width="650px;" clazz="checkbox validate[]"
											dataParams="rdgtItem" value="${materForm.CONTENT_ITEM_PLAN }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th >品名：</th>
									<td>
										<textarea rows="5" cols="120" maxlength="256" name="CONTENT_PRODUCT_PLAN">${materForm.CONTENT_PRODUCT_PLAN }</textarea>
										<span>【如是剧毒化学品，应在品名后括号标注“剧毒”，例如“液氯（剧毒）”】</span>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>危险货物运输车辆信息</span>
						</div>
						<div class="syj-title1 tmargin20">
							<span>已购置危险货物运输车辆情况</span>
						</div>
						<div id="purchaseDiv">
							<div style="position:relative;">
								<div id="purchaseToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addPurchase();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removePurchase();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#purchaseToolbar" id="purchaseGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post'"
									allowHeaderWrap="true" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'BRAND_MODEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="14%">厂牌型号</th>
											<th data-options="field:'AMOUNT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">数量</th>
											<th data-options="field:'CAR_TYPE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="7%">车辆类型</th>
											<th data-options="field:'TECH_LEVEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="9%">车辆技术等级</th>
											<th data-options="field:'CAR_WEIGHT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">总质量<br>（吨）</th>
											<th data-options="field:'DEAD_WEIGHT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">核定载<br>质量<br>（吨）</th>
											<th data-options="field:'AXLE_NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">车轴数</th>
											<th data-options="field:'OUT_SIZE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="13%">车辆外廓长宽高</th>
											<th data-options="field:'VOLUME',align:'center',editor:{type:'numberbox',options:{required:true,min:0,precision:2}}"
												width="6%">罐体容积</th>
											<th data-options="field:'NAMEANDDES',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="14%">拟用罐式车辆运输的危<br>险货物中密度最大的货<br>物品名及密度</th>
											<th data-options="field:'COMM_EQUIP',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'是'},  
												{'id':'0','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '是';
											        if(value=='0') return '否';
												}"
												width="8%">是否配备有<br>效通讯工具</th>
											<th data-options="field:'RUN_RECORD',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'是'},  
												{'id':'0','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '是';
											        if(value=='0') return '否';
												}"
												width="8%">是否安装具<br>有行驶记录<br>功能的卫星<br>定位装置</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<div class="syj-title1 tmargin20">
							<span>拟购置危险货物运输车辆情况</span>
						</div>
						<div id="purchasePlanDiv">
							<div style="position:relative;">
								<div id="purchasePlanToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addPurchasePlan();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removePurchasePlan();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#purchasePlanToolbar" id="purchasePlanGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post'" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'BRAND_MODEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="14%">厂牌型号</th>
											<th data-options="field:'AMOUNT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">数量</th>
											<th data-options="field:'CAR_TYPE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="7%">车辆类型</th>
											<th data-options="field:'TECH_LEVEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="9%">车辆技术等级</th>
											<th data-options="field:'CAR_WEIGHT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">总质量<br>（吨）</th>
											<th data-options="field:'DEAD_WEIGHT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">核定载<br>质量<br>（吨）</th>
											<th data-options="field:'AXLE_NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">车轴数</th>
											<th data-options="field:'OUT_SIZE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="13%">车辆外廓长宽高</th>
											<th data-options="field:'VOLUME',align:'center',editor:{type:'numberbox',options:{required:true,min:0,precision:2}}"
												width="6%">罐体容积</th>
											<th data-options="field:'NAMEANDDES',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="14%">拟用罐式车辆运输的危<br>险货物中密度最大的货<br>物品名及密度</th>
											<th data-options="field:'COMM_EQUIP',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'是'},  
												{'id':'0','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '是';
											        if(value=='0') return '否';
												}"
												width="8%">是否配备有<br>效通讯工具</th>
											<th data-options="field:'RUN_RECORD',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'是'},  
												{'id':'0','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '是';
											        if(value=='0') return '否';
												}"
												width="8%">是否安装具<br>有行驶记录<br>功能的卫星<br>定位装置</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>现有危险货物运输车辆情况</span>
						</div>
						<div id="currentDiv">
							<div style="position:relative;">
								<div id="currentToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addCurrent();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeCurrent();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#currentToolbar" id="currentGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post'" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'TRAN_CERT',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="10%">道路运输证号</th>
											<th data-options="field:'BRAND_MODEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="10%">厂牌型号</th>
											<th data-options="field:'AMOUNT',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="5%">车牌号</th>
											<th data-options="field:'CAR_TYPE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="7%">车辆类型</th>
											<th data-options="field:'TECH_LEVEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="7%">车辆技术<br>等级</th>
											<th data-options="field:'CAR_WEIGHT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">总质量<br>（吨）</th>
											<th data-options="field:'DEAD_WEIGHT',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">核定载<br>质量<br>（吨）</th>
											<th data-options="field:'AXLE_NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">车轴数</th>
											<th data-options="field:'OUT_SIZE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="10%">车辆外廓长宽高</th>
											<th data-options="field:'VOLUME',align:'center',editor:{type:'numberbox',options:{required:true,min:0,precision:2}}"
												width="6%">罐体容积</th>
											<th data-options="field:'NAMEANDDES',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="13%">拟用罐式车辆运输的<br>危险货物中密度最大<br>的货物品名及密度</th>
											<th data-options="field:'COMM_EQUIP',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'是'},  
												{'id':'0','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '是';
											        if(value=='0') return '否';
												}"
												width="8%">是否配备有<br>效通讯工具</th>
											<th data-options="field:'RUN_RECORD',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'是'},  
												{'id':'0','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '是';
											        if(value=='0') return '否';
												}"
												width="8%">是否安装具<br>有行驶记录<br>功能的卫星<br>定位装置</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<div class="syj-title1 tmargin20">
							<span>设备情况</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="width:20%"></th>
									<th style="width:5%"></th>
									<th style="width:50%">配备情况</th>
									<th style="width:25%">所有权</th>
								</tr>
								<tr>
									<td rowspan="2">环境保护设备：</td>
									<td>1</td>
									<td><input type="text"
										class="syj-input1 validate[]" name="EPQ_FIR_EQUIP" value="${materForm.EPQ_FIR_EQUIP }"
										placeholder="" maxlength="16" /></td>
									<td><input type="text"
										class="syj-input1 validate[]" name="EPQ_FIR_BELONG" value="${materForm.EPQ_FIR_BELONG }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<td>2</td>
									<td><input type="text"
										class="syj-input1 validate[]" name="EPQ_SEC_EQUIP" value="${materForm.EPQ_SEC_EQUIP }"
										placeholder="" maxlength="16" /></td>
									<td><input type="text"
										class="syj-input1 validate[]" name="EPQ_SEC_BELONG" value="${materForm.EPQ_SEC_BELONG }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<td colspan="2">消防设备：</td>
									<td><input type="text"
										class="syj-input1 validate[]" name="FFE_EQUIP" value="${materForm.FFE_EQUIP }"
										placeholder="" maxlength="16" /></td>
									<td><input type="text"
										class="syj-input1 validate[]" name="FFE_BELONG" value="${materForm.FFE_BELONG }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<td rowspan="4">安全防护设备：</td>
									<td>1</td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_FIR_EQUIP" value="${materForm.SPE_FIR_EQUIP }"
										placeholder="" maxlength="16" /></td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_FIR_BELONG" value="${materForm.SPE_FIR_BELONG }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<td>2</td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_SEC_EQUIP" value="${materForm.SPE_SEC_EQUIP }"
										placeholder="" maxlength="16" /></td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_SEC_BELONG" value="${materForm.SPE_SEC_BELONG }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<td>3</td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_THR_EQUIP" value="${materForm.SPE_THR_EQUIP }"
										placeholder="" maxlength="16" /></td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_THR_BELONG" value="${materForm.SPE_THR_BELONG }"
										placeholder="" maxlength="16" /></td>
								</tr>
								<tr>
									<td>4</td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_FOUR_EQUIP" value="${materForm.SPE_FOUR_EQUIP }"
										placeholder="" maxlength="16" /></td>
									<td><input type="text"
										class="syj-input1 validate[]" name="SPE_FOUR_BELONG" value="${materForm.SPE_FOUR_BELONG }"
										placeholder="" maxlength="16" /></td>
								</tr>
							</table>
							<span style="color: red">“安全防护设备”栏内填写与车辆有关的安全设备</span>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>停车场地情况</span>
						</div>
						
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>停车场地面积(㎡)：</th>
									<td><input type="text" maxlength="8"
										class="syj-input1 validate[required,custom[number2plus]]" name="PARKING_AREA" value="${materForm.PARKING_AREA }"
										placeholder="请输入停车场地面积" /></td>
									<th><font class="syj-color2">*</font>所有权：</th>
									<td>
										<input type="radio" name="PARKING_BELONG" value="1" onclick="$('#lease').attr('disabled',true);$('#lease').val('');" <c:if test="${materForm.PARKING_BELONG!=0}"> checked="checked"</c:if>>自有
										<input type="radio" name="PARKING_BELONG" value="0" onclick="$('#lease').attr('disabled',false);" <c:if test="${materForm.PARKING_BELONG==0}"> checked="checked"</c:if>>租借：期限
										<input type="text" id="lease" <c:if test="${materForm.PARKING_BELONG!=0}">disabled="disabled"</c:if>
										class="syj-input1 validate[required]" name="PARKING_LEASETERM" value="${materForm.PARKING_LEASETERM }"
										/>
									</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>停车场地地址：</th>
									<td colspan="3"><input type="text" maxlength="30"
										class="syj-input1 validate[required]" name="PARKING_ADDR" value="${materForm.PARKING_ADDR }"
										placeholder="请输入停车场地地址" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">								
								<tr>
									<th style="width:17.5%" rowspan="2">拟投入车辆数量</th>
									<th style="width:17%">剧毒化学品专用车辆</th>
									<th style="width:17%">爆炸品专用车辆</th>
									<th style="width:16.5%">罐式专用车辆</th>
									<th style="width:16%">其它</th>
									<th style="width:16%">合计</th>
								</tr>
								<tr>
									<td><input type="te xt" style="width:93%;" onblur="settotal()"
										class="syj-input1 validate[[],custom[integerplus]]" name="SVFTC_NUM" value="${materForm.SVFTC_NUM }"
										placeholder="" maxlength="8" /></td>
									<td><input type="text" style="width:93%;" onblur="settotal()"
										class="syj-input1 validate[[],custom[integerplus]]" name="ESV_NUM" value="${materForm.ESV_NUM }"
										placeholder="" maxlength="8" /></td>
									<td><input type="text" style="width:93%;" onblur="settotal()"
										class="syj-input1 validate[[],custom[integerplus]]" name="TSV_NUM" value="${materForm.TSV_NUM }"
										placeholder="" maxlength="8" /></td>
									<td><input type="text" style="width:93%;" onblur="settotal()"
										class="syj-input1 validate[[],custom[integerplus]]" name="OTHER_NUM" value="${materForm.OTHER_NUM }"
										placeholder="" maxlength="8" /></td>
									<td><input type="text" style="width:93%;" readonly="readonly"
										class="syj-input1 " name="TOTAL_NUM" value="${materForm.TOTAL_NUM }"
										placeholder="" maxlength="8" /></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>拟聘用专职安全管理人员和从业人员情况<font color="red">(注：非驾驶人员不填写“取得相应驾驶证时间”栏)</font></span>
						</div>
						<div id="employeesDiv">
							<div style="position:relative;">
								<div id="employeesToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addEmployees();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeEmployees();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#employeesToolbar" id="employeesGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post'" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'NAME',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">姓名</th>
											<th data-options="field:'SEX',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'男'},  
												{'id':'2','text':'女'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '男';
											        if(value=='2') return '女';
												}"
												width="10%">性别</th>
											<th data-options="field:'AGE',align:'left',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="10%">年龄</th>
											<th data-options="field:'JOBTYPE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">岗位（工种）</th>
											<th data-options="field:'TAKE_TIME',align:'left',editor:{type:'datebox',options:{required:true,editable:false}}"
												width="15%">取得相应驾驶证时间</th>
											<th data-options="field:'CERT_NO',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">从业资格证号</th>
											<th data-options="field:'CERT_TYPE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="19%">从业资格证类型</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
        <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#RDGTAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>
</body>